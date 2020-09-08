package com.lulan.shincolle.entity.destroyer;

import com.lulan.shincolle.ai.EntityAIShipPickItem;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.entity.IShipSummonAttack;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.entity.other.EntityRensouhou;
import com.lulan.shincolle.entity.other.EntityRensouhouS;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.unitclass.Dist4d;
import com.lulan.shincolle.reference.unitclass.MissileData;
import com.lulan.shincolle.utility.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/**
 * model state:
 *   0:rensouhou type, 1:cannon, 2:hair anchor, 3:hat1, 4:hat2, 5:hat3
 *   if 3 & 4 false = no hat
 */
public class EntityDestroyerShimakaze extends BasicEntityShipSmall implements IShipSummonAttack
{

	public int numRensouhou;
	
	
	public EntityDestroyerShimakaze(World world)
	{
		super(world);
		this.setSize(0.5F, 1.6F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.DESTROYER);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.DDShimakaze);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.DESTROYER);
		this.setStateMinor(ID.M.NumState, 6);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.DD]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.DD]);
		this.ModelPos = new float[] {0F, 25F, 0F, 45F};
		
		this.numRensouhou = 6;
		
		//set attack type
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		this.StateFlag[ID.F.CanPickItem] = true;
		
		this.postInit();
	}
	
	//for morph
	@Override
	public float getEyeHeight()
	{
		return 1.5F;
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
		
		//pick item
		this.tasks.addTask(20, new EntityAIShipPickItem(this, 4F));
	}
    
    //check entity state every tick
  	@Override
  	public void onLivingUpdate()
  	{
  		super.onLivingUpdate();
  		
  		if (!world.isRemote)
  		{
  			//add aura to master every 128 ticks
  			if (this.ticksExisted % 128 == 0 && !this.isMorph)
  			{
  				//add num of rensouhou
  				if (this.numRensouhou < 6) numRensouhou++;
  				
  				EntityPlayer player = EntityHelper.getEntityPlayerByUID(this.getPlayerUID());
  				if (getStateFlag(ID.F.IsMarried) && getStateFlag(ID.F.UseRingEffect) &&
  					getStateMinor(ID.M.NumGrudge) > 0 && player != null && getDistanceSq(player) < 256D)
  				{
  					//potion effect: id, time, level
  	  	  			player.addPotionEffect(new PotionEffect(MobEffects.SPEED , 80+getStateMinor(ID.M.ShipLevel), getStateMinor(ID.M.ShipLevel) / 35 + 1, false, false));
  				}
  			}
  		}    
  	}
  	
  	//招喚連裝砲進行攻擊
  	@Override
  	public boolean attackEntityWithAmmo(Entity target)
  	{
  		//light ammo--
        if (!decrAmmoNum(0, 4 * this.getAmmoConsumption())) return false;
        
  		//check num rensouhou
  		if  (this.numRensouhou <= 0)
  		{
  			return false;
  		}
  		else
  		{
  			this.numRensouhou--;
  		}
        
        //experience++
  		addShipExp(ConfigHandler.expGain[1] * 2);
  		
  		//grudge--
  		decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.LAtk] * 4);
  		
  		//morale--
  		decrMorale(1);
  		setCombatTick(this.ticksExisted);
  		
        //play attack sound
		if (this.rand.nextInt(10) > 7)
		{
			this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
        }

        //發射者煙霧特效 (招喚連裝砲不使用特效, 但是要發送封包來設定attackTime)
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 32D);
		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
  		
		//spawn rensouhou
    	if (EmotionHelper.checkModelState(0, this.getStateEmotion(ID.S.State)))
    	{
    		EntityRensouhouS rensoho2 = new EntityRensouhouS(this.world);
    		rensoho2.initAttrs(this, target, 0);
            this.world.spawnEntity(rensoho2);
    	}
    	else
    	{
    		EntityRensouhou rensoho1 = new EntityRensouhou(this.world);
    		rensoho1.initAttrs(this, target, 0);
            this.world.spawnEntity(rensoho1);
    	}
    	
    	//show emotes
    	applyEmotesReaction(3);
    	
    	if (ConfigHandler.canFlare) this.flareTarget(target);
    	
        return true;
	}
  	
  	@Override
  	public boolean attackEntityWithHeavyAmmo(BlockPos target)
	{
  		//get attack value
		float atk = this.getAttackBaseDamage(2, null) * 0.3F;
		float kbValue = 0.15F;
		
		//missile type
		float launchPos = (float) posY + height * 0.7F;
		int moveType = CombatHelper.calcMissileMoveType(this, target.getY(), 2);
		if (moveType == 1) moveType = 0;
		
        //calc dist to target
        Dist4d distVec = CalcHelper.getDistanceFromA2B(this.getPosition(), target);
        
		//experience++
		addShipExp(ConfigHandler.expGain[2]);
		
		//grudge--
		decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.HAtk]);
		
  		//morale--
		decrMorale(2);
  		setCombatTick(this.ticksExisted);
	
		//play cannon fire sound at attacker
  		applySoundAtAttacker(2, this);
  		this.playSound(ModSounds.SHIP_FIREHEAVY, ConfigHandler.volumeFire, this.getSoundPitch() * 0.8F);
  		this.playSound(ModSounds.SHIP_FIREHEAVY, ConfigHandler.volumeFire, this.getSoundPitch() * 0.9F);
        
        //heavy ammo--
        if(!decrAmmoNum(1, this.getAmmoConsumption())) return false;
        
	    float tarX = (float) target.getX();
	    float tarY = (float) target.getY();
	    float tarZ = (float) target.getZ();
	    
	    //if too close, extend target position
	    if (distVec.d < 6D)
	    {
	    	tarX += distVec.x * (6D - distVec.d);
	    	tarY += distVec.y * (6D - distVec.d);
	    	tarZ += distVec.z * (6D - distVec.d);
	    }
	    
	    //calc miss rate
        if (this.rand.nextFloat() <= CombatHelper.calcMissRate(this, (float)distVec.d))
        {
        	tarX = tarX - 5F + this.rand.nextFloat() * 10F;
        	tarY = tarY + this.rand.nextFloat() * 5F;
        	tarZ = tarZ - 5F + this.rand.nextFloat() * 10F;
        	
        	ParticleHelper.spawnAttackTextParticle(this, 0);  //miss particle
        }
        
        //發射者煙霧特效 (不使用特效, 但是要發送封包來設定attackTime)
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 32D);
		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
		
        //spawn missile
		MissileData md = this.getMissileData(2);
		float[] data = new float[] {atk, kbValue, launchPos, tarX, tarY+0.2F, tarZ, 160, 0.25F, md.vel0, md.accY1, md.accY2};
		EntityAbyssMissile missile1 = new EntityAbyssMissile(this.world, this, md.type, moveType, data);
		data = new float[] {atk, kbValue, launchPos, tarX+3.5F, tarY+0.2F, tarZ+3.5F, 160, 0.25F, md.vel0, md.accY1, md.accY2};
		EntityAbyssMissile missile2 = new EntityAbyssMissile(this.world, this, md.type, moveType, data);
		data = new float[] {atk, kbValue, launchPos, tarX+3.5F, tarY+0.2F, tarZ-3.5F, 160, 0.25F, md.vel0, md.accY1, md.accY2};
		EntityAbyssMissile missile3 = new EntityAbyssMissile(this.world, this, md.type, moveType, data);
		data = new float[] {atk, kbValue, launchPos, tarX-3.5F, tarY+0.2F, tarZ+3.5F, 160, 0.25F, md.vel0, md.accY1, md.accY2};
		EntityAbyssMissile missile4 = new EntityAbyssMissile(this.world, this, md.type, moveType, data);
		data = new float[] {atk, kbValue, launchPos, tarX-3.5F, tarY+0.2F, tarZ-3.5F, 160, 0.25F, md.vel0, md.accY1, md.accY2};
		EntityAbyssMissile missile5 = new EntityAbyssMissile(this.world, this, md.type, moveType, data);
		
        this.world.spawnEntity(missile1);
        this.world.spawnEntity(missile2);
        this.world.spawnEntity(missile3);
        this.world.spawnEntity(missile4);
        this.world.spawnEntity(missile5);
        
        //show emotes
      	applyEmotesReaction(3);
      	
      	if (ConfigHandler.canFlare) flareTarget(target);
      	
        return true;
	}
  	
  	//五連裝酸素魚雷
  	@Override
  	public boolean attackEntityWithHeavyAmmo(Entity target)
  	{	
		//get attack value
		float atk = this.getAttackBaseDamage(2, target) * 0.3F;
		float kbValue = 0.15F;
		
		//missile type
		float launchPos = (float) posY + height * 0.7F;
		int moveType = CombatHelper.calcMissileMoveType(this, target.posY, 2);
		if (moveType == 1) moveType = 0;
		
        //calc dist to target
        Dist4d distVec = CalcHelper.getDistanceFromA2B(this, target);
        
		//experience++
		addShipExp(ConfigHandler.expGain[2]);
		
		//grudge--
		decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.HAtk]);
		
  		//morale--
		decrMorale(2);
  		setCombatTick(this.ticksExisted);
	
		//play cannon fire sound at attacker
  		applySoundAtAttacker(2, target);
  		this.playSound(ModSounds.SHIP_FIREHEAVY, ConfigHandler.volumeFire, this.getSoundPitch() * 0.8F);
  		this.playSound(ModSounds.SHIP_FIREHEAVY, ConfigHandler.volumeFire, this.getSoundPitch() * 0.9F);
        
        //heavy ammo--
        if(!decrAmmoNum(1, this.getAmmoConsumption())) return false;
        
	    float tarX = (float) target.posX;
	    float tarY = (float) target.posY;
	    float tarZ = (float) target.posZ;
	    
	    //if too close, extend target position
	    if (distVec.d < 6D)
	    {
	    	tarX += distVec.x * (6D - distVec.d);
	    	tarY += distVec.y * (6D - distVec.d);
	    	tarZ += distVec.z * (6D - distVec.d);
	    }
	    
	    //calc miss rate
        if (this.rand.nextFloat() <= CombatHelper.calcMissRate(this, (float)distVec.d))
        {
        	tarX = tarX - 5F + this.rand.nextFloat() * 10F;
        	tarY = tarY + this.rand.nextFloat() * 5F;
        	tarZ = tarZ - 5F + this.rand.nextFloat() * 10F;
        	
        	ParticleHelper.spawnAttackTextParticle(this, 0);  //miss particle
        }
        
        //發射者煙霧特效 (不使用特效, 但是要發送封包來設定attackTime)
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 32D);
		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
		
        //spawn missile
		MissileData md = this.getMissileData(2);
		float[] data = new float[] {atk, kbValue, launchPos, tarX, tarY+target.height*0.1F, tarZ, 160, 0.25F, md.vel0, md.accY1, md.accY2};
		EntityAbyssMissile missile1 = new EntityAbyssMissile(this.world, this, md.type, moveType, data);
		data = new float[] {atk, kbValue, launchPos, tarX+3.5F, tarY+target.height*0.1F, tarZ+3.5F, 160, 0.25F, md.vel0, md.accY1, md.accY2};
		EntityAbyssMissile missile2 = new EntityAbyssMissile(this.world, this, md.type, moveType, data);
		data = new float[] {atk, kbValue, launchPos, tarX+3.5F, tarY+target.height*0.1F, tarZ-3.5F, 160, 0.25F, md.vel0, md.accY1, md.accY2};
		EntityAbyssMissile missile3 = new EntityAbyssMissile(this.world, this, md.type, moveType, data);
		data = new float[] {atk, kbValue, launchPos, tarX-3.5F, tarY+target.height*0.1F, tarZ+3.5F, 160, 0.25F, md.vel0, md.accY1, md.accY2};
		EntityAbyssMissile missile4 = new EntityAbyssMissile(this.world, this, md.type, moveType, data);
		data = new float[] {atk, kbValue, launchPos, tarX-3.5F, tarY+target.height*0.1F, tarZ-3.5F, 160, 0.25F, md.vel0, md.accY1, md.accY2};
		EntityAbyssMissile missile5 = new EntityAbyssMissile(this.world, this, md.type, moveType, data);
		
        this.world.spawnEntity(missile1);
        this.world.spawnEntity(missile2);
        this.world.spawnEntity(missile3);
        this.world.spawnEntity(missile4);
        this.world.spawnEntity(missile5);
        
        //show emotes
      	applyEmotesReaction(3);
      	
      	if (ConfigHandler.canFlare) flareTarget(target);
      	
        return true;
	}
  	
  	@Override
	public void calcShipAttributesAddEquip()
	{
		super.calcShipAttributesAddEquip();
		
		MissileData md = this.getMissileData(2);
		
		md.vel0 += 0.2F;
		md.accY1 += 0.025F;
		md.accY2 += 0.025F;
	}
  	
  	@Override
	public int getNumServant()
  	{
		return this.numRensouhou;
	}

	@Override
	public void setNumServant(int num)
	{
		this.numRensouhou = num;
	}

	@Override
	public double getMountedYOffset()
	{
		if (this.isSitting())
		{
			if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
			{
				return this.height * -0.04F;
  			}
  			else
  			{
  				return this.height * 0.16F;
  			}
  		}
  		else
  		{
  			return this.height * 0.67F;
  		}
	}
	
	
}