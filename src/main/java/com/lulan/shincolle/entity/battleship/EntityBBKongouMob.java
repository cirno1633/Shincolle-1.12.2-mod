package com.lulan.shincolle.entity.battleship;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.unitclass.Dist4d;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.EmotionHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import net.minecraft.entity.Entity;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import java.util.HashMap;

/**
 * model state:
 *   0:equip, 1:head, 2:hair, 3:ahoke
 */
public class EntityBBKongouMob extends BasicEntityShipHostile
{
	
	private float smokeX1, smokeY1, smokeX2, smokeY2;
	
	
	public EntityBBKongouMob(World world)
	{
		super(world);
		
		//init values
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.BBKongou);
        this.smokeX1 = 0F;
        this.smokeY1 = 0F;
        this.smokeX2 = 0F;
        this.smokeY2 = 0F;
        
		//model display
		this.setStateEmotion(ID.S.State, 7 + (this.rand.nextInt(2) == 0 ? 0 : 8), false);
	}
	
	@Override
	protected void setSizeWithScaleLevel()
	{
		switch (this.getScaleLevel())
		{
		case 3:
			this.setSize(2.4F, 7.5F);
			this.smokeX1 = -1.64F;
			this.smokeY1 = 4.74F;
			this.smokeX2 = -2.61F;
			this.smokeY2 = 4.27F;
		break;
		case 2:
			this.setSize(1.8F, 5.625F);
			this.smokeX1 = -1.25F;
			this.smokeY1 = 3.57F;
			this.smokeX2 = -1.98F;
			this.smokeY2 = 3.18F;
		break;
		case 1:
			this.setSize(1.2F, 3.75F);
			this.smokeX1 = -0.82F;
			this.smokeY1 = 2.47F;
			this.smokeX2 = -1.33F;
			this.smokeY2 = 2.21F;
		break;
		default:
			this.setSize(0.6F, 1.875F);
			this.smokeX1 = -0.7F;
			this.smokeY1 = 1.17F;
			this.smokeX2 = -0.45F;
			this.smokeY2 = 1.32F;
		break;
		}
	}

	@Override
	protected void setBossInfo()
	{
		this.bossInfo = new BossInfoServer(this.getDisplayName(), BossInfo.Color.WHITE, BossInfo.Overlay.NOTCHED_10);
	}

	//setup AI
	@Override
	protected void setAIList()
	{
		super.setAIList();

		//use range attack
		this.tasks.addTask(1, new EntityAIShipRangeAttack(this));
	}
	
	@Override
	public void initAttrsServerPost()
	{
		super.initAttrsServerPost();
		
		//add attack effects
		if (this.AttackEffectMap == null) this.AttackEffectMap = new HashMap<Integer, int[]>();
		this.AttackEffectMap.put(18, new int[] {(int)(this.getScaleLevel() / 1.5), 80+this.getScaleLevel()*50, 25+this.getScaleLevel()*25});
	}
	
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
  				if (EmotionHelper.checkModelState(0, this.getStateEmotion(ID.S.State)))
  				{
  					//計算煙霧位置
  					float[] partPos = CalcHelper.rotateXZByAxis(this.smokeX1, 0F, (this.renderYawOffset % 360) * Values.N.DIV_PI_180, 1F);
  	  				ParticleHelper.spawnAttackParticleAt(posX+partPos[1], posY + this.smokeY1, posZ+partPos[0], 1D + this.scaleLevel * 1D, 0D, 0D, (byte)43);
  	  				partPos = CalcHelper.rotateXZByAxis(this.smokeX2, 0F, (this.renderYawOffset % 360) * Values.N.DIV_PI_180, 1F);
	  				ParticleHelper.spawnAttackParticleAt(posX+partPos[1], posY + this.smokeY2, posZ+partPos[0], 1D + this.scaleLevel * 1D, 0D, 0D, (byte)43);
				}
  			}//end 4 ticks
  		}
  	}
  	
	@Override
	public void applyParticleAtAttacker(int type, Entity target, Dist4d distVec)
	{
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
  		switch (type)
  		{
  		case 1:  //light cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 5, 0.9D*(this.scaleLevel+1), 1D*(this.scaleLevel+1), 1.1D*(this.scaleLevel+1)), point);
  		break;
		default: //melee
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
		break;
  		}
  	}

	@Override
	public int getDamageType()
	{
		return ID.ShipDmgType.BATTLESHIP;
	}
	
	
}