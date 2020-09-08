package com.lulan.shincolle.entity.battleship;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.unitclass.Dist4d;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.EmotionHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TeamHelper;
import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import java.util.List;

/**
 * model state:
 *   0:equip, 1:head, 2:hair, 3:ahoke
 */
public class EntityBBKongou extends BasicEntityShipSmall
{

	public EntityBBKongou(World world)
	{
		super(world);
		this.setSize(0.6F, 1.875F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.BATTLESHIP);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.BBKongou);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.BATTLESHIP);
		this.setStateMinor(ID.M.NumState, 4);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.BB]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.BB]);
		this.ModelPos = new float[] {0F, 25F, 0F, 40F};
		
		//set attack type
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		
		//misc
		this.setFoodSaturationMax(19);
		
		this.postInit();
	}

	//equip type: 1:cannon+misc 2:cannon+airplane+misc 3:airplane+misc
	@Override
	public int getEquipType()
	{
		return 1;
	}
	
	@Override
	public void setAIList()
	{
		super.setAIList();

		//use range attack (light)
		this.tasks.addTask(11, new EntityAIShipRangeAttack(this));
	}
	
    //check entity state every tick
  	@Override
  	public void onLivingUpdate()
  	{
  		super.onLivingUpdate();
  		
  		//client side
  		if (this.world.isRemote)
  		{
  			if (this.ticksExisted % 4 == 0)
  			{
  				//生成裝備冒煙特效
  				if (EmotionHelper.checkModelState(0, this.getStateEmotion(ID.S.State)) && !isSitting() && !getStateFlag(ID.F.NoFuel))
  				{
  					//計算煙霧位置
  					float[] partPos = CalcHelper.rotateXZByAxis(-0.7F, 0F, (this.renderYawOffset % 360) * Values.N.DIV_PI_180, 1F);
  	  				ParticleHelper.spawnAttackParticleAt(posX+partPos[1], posY + 1.17D, posZ+partPos[0], 1D, 0D, 0D, (byte)43);
  	  				partPos = CalcHelper.rotateXZByAxis(-0.45F, 0F, (this.renderYawOffset % 360) * Values.N.DIV_PI_180, 1F);
	  				ParticleHelper.spawnAttackParticleAt(posX+partPos[1], posY + 1.32D, posZ+partPos[0], 1D, 0D, 0D, (byte)43);
				}
  			}//end 4 ticks
  		}//end client side
  		//server side
  		else
  		{
  			if (this.ticksExisted % 128 == 0)
  			{
  				//married effect: apply health boost buff to nearby ships
  				if (getStateFlag(ID.F.IsMarried) && getStateFlag(ID.F.UseRingEffect) &&
  					getStateMinor(ID.M.NumGrudge) > 0)
  				{
  					List<BasicEntityShip> shiplist = this.world.getEntitiesWithinAABB(BasicEntityShip.class, this.getEntityBoundingBox().expand(16D, 16D, 16D));
  	  	  			
  					if (shiplist != null && shiplist.size() > 0)
  					{
  						for (BasicEntityShip s : shiplist)
  						{
  							if (TeamHelper.checkSameOwner(this, s))
  							{
  								//potion effect: id, time, level
  								s.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST , 140+getStateMinor(ID.M.ShipLevel), getStateMinor(ID.M.ShipLevel) / 120, false, false));
  							}
  						}
  					}
  				}//end married buff
  			}
  		}//end server
  	}
  	
	@Override
	public void calcShipAttributesAddEffect()
	{
		super.calcShipAttributesAddEffect();
		this.AttackEffectMap.put(18, new int[] {(int)(this.getLevel() / 60), 100+this.getLevel(), this.getLevel()});
	}
	
	@Override
	public double getMountedYOffset()
	{
		if (this.isSitting())
		{
			if (EmotionHelper.checkModelState(1, this.getStateEmotion(ID.S.State)))
			{
				return this.height * 0.42F;
			}
			else
			{
				if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
				{
					return 0F;
	  			}
	  			else
	  			{
	  				return this.height * 0.35F;
	  			}
			}
  		}
  		else
  		{
  			return this.height * 0.75F;
  		}
	}
	
	@Override
	public void applyParticleAtAttacker(int type, Entity target, Dist4d distVec)
	{
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
  		switch (type)
  		{
  		case 1:  //light cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 19, this.posX, this.posY+0.3D, this.posZ, distVec.x, 1F, distVec.z, true), point);
  		break;
		default: //melee
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
		break;
  		}
  	}
	
	
}