package com.lulan.shincolle.entity.submarine;

import com.lulan.shincolle.ai.EntityAIShipPickItem;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.entity.IShipInvisible;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.unitclass.Dist4d;
import com.lulan.shincolle.reference.unitclass.MissileData;
import com.lulan.shincolle.utility.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * model state:
 *   0:equip, 1:cloth
 */
public class EntitySubmYo extends BasicEntityShipSmall implements IShipInvisible
{
	

	public EntitySubmYo(World world)
	{
		super(world);
		this.setSize(0.6F, 1.8F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.SUBMARINE);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.SSYO);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.SUBMARINE);
		this.setStateMinor(ID.M.NumState, 2);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.SS]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.SS]);
		this.ModelPos = new float[] {0F, 25F, 0F, 45F};
		
		//set attack type
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		this.StateFlag[ID.F.CanPickItem] = true;
		
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
		
		//use range attack
		this.tasks.addTask(11, new EntityAIShipRangeAttack(this));
		
		//pick item
		this.tasks.addTask(20, new EntityAIShipPickItem(this, 4F));
	}

    //check entity state every tick
  	@Override
  	public void onLivingUpdate()
  	{
  		super.onLivingUpdate();
  		
  		if (!this.world.isRemote)
  		{
  			//every 128 ticks
  			if (this.ticksExisted % 128 == 0)
  			{
  				if (getStateFlag(ID.F.UseRingEffect) && getStateMinor(ID.M.NumGrudge) > 0)
  				{
  					//owner invisible
  					if (getStateFlag(ID.F.IsMarried))
  					{
  						EntityPlayerMP player = (EntityPlayerMP) EntityHelper.getEntityPlayerByUID(this.getPlayerUID());
  	  	  				if (player != null && getDistanceSq(player) < 256D)
  	  	  				{
  	  	  					//potion effect: id, time, level
  	  	  	  	  			player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 40+getLevel(), 0, false, false));
  	  	  				}
  					}
  					
  					//self invisible
  					this.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 40+getLevel(), 0, false, false));
  				}
  			}//end 128 ticks
  		}//end server
  		//client side
  		else
  		{
  			if(this.ticksExisted % 4 ==  0)
  			{
    			//若顯示裝備時, 則生成眼睛煙霧特效 (client only)
    			if (EmotionHelper.checkModelState(0, this.getStateEmotion(ID.S.State)) && !getStateFlag(ID.F.NoFuel) &&
    				(isSitting() && getStateEmotion(ID.S.Emotion) != ID.Emotion.BORED || !isSitting()))
    			{
    				//set origin position
    				float[] eyePosL;
    				float[] eyePosR;
    				float radYaw = this.renderYawOffset * Values.N.DIV_PI_180;
    				float radPitch = this.rotationPitch * Values.N.DIV_PI_180;
    				
    				//坐下位置計算
    				if (this.isSitting())
    				{
    					eyePosL = new float[] {0.35F, 1.5F, -0.5F};
        				eyePosR = new float[] {-0.35F, 1.5F, -0.5F};
    				}
    				else
    				{
    					eyePosL = new float[] {0.35F, 1.8F, -0.35F};
        				eyePosR = new float[] {-0.35F, 1.8F, -0.35F};
    				}

    				//依照新位置, 繼續旋轉Y軸
    				eyePosL = CalcHelper.rotateXYZByYawPitch(eyePosL[0], eyePosL[1], eyePosL[2], radYaw, 0F, 1F);
    				eyePosR = CalcHelper.rotateXYZByYawPitch(eyePosR[0], eyePosR[1], eyePosR[2], radYaw, 0F, 1F);		
    				
    				//旋轉完三軸, 生成特效
    				ParticleHelper.spawnAttackParticleAt(this.posX+eyePosL[0], this.posY+eyePosL[1], this.posZ+eyePosL[2], 
                    		0D, 0.05D, 0.5D, (byte)16);
    				
    				ParticleHelper.spawnAttackParticleAt(this.posX+eyePosR[0], this.posY+eyePosR[1], this.posZ+eyePosR[2], 
                    		0D, 0.05D, 0.5D, (byte)16);
    			}
    		}//end every 8 ticks
  		}//end client side
  	}
  	
  	@Override
	public double getMountedYOffset()
  	{
  		if (EmotionHelper.checkModelState(0, this.getStateEmotion(ID.S.State)))
  		{
  			if (this.isSitting())
  	  		{
  				if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
  				{
  					return this.height * 0.55F;
  	  			}
  	  			else
  	  			{
  	  				return this.height * 0.42F;
  	  			}
  	  		}
  	  		else
  	  		{
  	  			return this.height * 0.58F;
  	  		}
  		}
  		else
  		{
  			if (this.isSitting())
  	  		{
  				if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
  				{
  					return height * 0.48F;
  	  			}
  	  			else
  	  			{
  	  				return 0F;
  	  			}
  	  		}
  	  		else
  	  		{
  	  			return height * 0.69F;
  	  		}
  		}
	}

	@Override
	public float getInvisibleLevel()
	{
		return 0.2F;
	}
	
	@Override
	public void setInvisibleLevel(float level) {}
	
	//潛艇的輕攻擊一樣使用飛彈
  	@Override
  	public boolean attackEntityWithAmmo(Entity target)
  	{	
		//ammo--
        if (!decrAmmoNum(0, this.getAmmoConsumption())) return false;
        
		//experience++
		addShipExp(ConfigHandler.expGain[1]);
		
		//grudge--
		decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.LAtk]);
		
  		//morale--
  		decrMorale(1);
  		setCombatTick(this.ticksExisted);
  		
        //calc dist to target
        Dist4d distVec = CalcHelper.getDistanceFromA2B(this, target);
        
        //play sound and particle
        applySoundAtAttacker(2, target);
	    applyParticleAtAttacker(2, target, distVec);
		
	    float tarX = (float) target.posX;
	    float tarY = (float) target.posY;
	    float tarZ = (float) target.posZ;
	    
	    //calc miss rate
        if (this.rand.nextFloat() <= CombatHelper.calcMissRate(this, (float)distVec.d))
        {
        	tarX = tarX - 5F + this.rand.nextFloat() * 10F;
        	tarY = tarY + this.rand.nextFloat() * 5F;
        	tarZ = tarZ - 5F + this.rand.nextFloat() * 10F;
        	
        	ParticleHelper.spawnAttackTextParticle(this, 0);  //miss particle
        }
        
        //get attack value
  		float atk = getAttackBaseDamage(1, target);
  		
  		//spawn missile
  		summonMissile(1, atk, tarX, tarY, tarZ, 1F);
  		
        //play target effect
        applySoundAtTarget(2, target);
        applyParticleAtTarget(2, target, distVec);
        applyEmotesReaction(3);
        
        if (ConfigHandler.canFlare) flareTarget(target);
        
        applyAttackPostMotion(1, this, true, atk);
        
        return true;
  	}
  	
  	@Override
  	public float getAttackBaseDamage(int type, Entity target)
  	{
  		switch (type)
  		{
  		case 1:  //light cannon
  			return this.shipAttrs.getAttackDamage();
  		case 2:  //heavy cannon
  			return this.shipAttrs.getAttackDamageHeavy();
  		case 3:  //light aircraft
  			return this.shipAttrs.getAttackDamageAir();
  		case 4:  //heavy aircraft
  			return this.shipAttrs.getAttackDamageAirHeavy();
		default: //melee
			return this.shipAttrs.getAttackDamage() * 0.125F;
  		}
  	}
  	
	//apply additional missile value
	@Override
	public void calcShipAttributesAddEquip()
	{
		super.calcShipAttributesAddEquip();
		
		MissileData md = this.getMissileData(1);
		
		md.vel0 += 0.3F;
		md.accY1 += 0.06F;
		md.accY2 += 0.06F;
	}
  	
	@Override
	public double getShipFloatingDepth()
	{
		return 1D;
	}
	

}