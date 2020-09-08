package com.lulan.shincolle.entity.destroyer;

import com.lulan.shincolle.ai.EntityAIShipPickItem;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.entity.IShipRiderType;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.EmotionHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

/**
 * 六驅單縱陣合體特性: 詳見EntityDestroyerAkatsuki.class
 * 
 * model state:
 *   0:cannon, 1:armor, 2:hat1, 3:hat2, 4:hat3
 *   if 2 & 3 & 4 false = no hat
 */
public class EntityDestroyerHibiki extends BasicEntityShipSmall implements IShipRiderType
{
	
	/**
	 * 六驅合體狀態: 0:none, 1:只有響, 2:只有電, 4:只有雷
	 * 可合計, ex: 3:有響跟雷, 6:有雷跟電, 7:有響雷電
	 */
	private int riderType;
	private int ridingState;

	
	public EntityDestroyerHibiki(World world)
	{
		super(world);
		this.setSize(0.5F, 1.5F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.DESTROYER);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.DDHibiki);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.DESTROYER);
		this.setStateMinor(ID.M.NumState, 5);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.DD]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.DD]);
		this.ModelPos = new float[] {0F, 25F, 0F, 50F};
		
		//set attack type
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		this.StateFlag[ID.F.CanPickItem] = true;
		
		this.riderType = 0;
		this.ridingState = 0;
		
		this.postInit();
	}
	
	//for morph
	@Override
	public float getEyeHeight()
	{
		return 1.4F;
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
  		
  		//server side
  		if (!world.isRemote)
  		{
  			if (this.ticksExisted % 32 == 0 && !this.isMorph)
  			{
  				this.checkRiderType();
  				this.checkRidingState();
  				
  				if (this.ticksExisted % 128 == 0)
  	  			{
	  				EntityPlayer player = EntityHelper.getEntityPlayerByUID(this.getPlayerUID());
	  				if (getStateFlag(ID.F.IsMarried) && getStateFlag(ID.F.UseRingEffect) &&
	  					getStateMinor(ID.M.NumGrudge) > 0 && player != null && getDistanceSq(player) < 256D)
	  				{
	  					//potion effect: id, time, level
	  	  	  			player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST , 80+getStateMinor(ID.M.ShipLevel), getStateMinor(ID.M.ShipLevel) / 45 + 1, false, false));
	  				}
  	  			}//end 128 ticks
  			}//end 32 ticks
  		}
  		//client side
  		else
  		{
  			if (this.ticksExisted % 4 == 0)
  			{
  				if (EmotionHelper.checkModelState(0, this.getStateEmotion(ID.S.State)) &&
  					!isSitting() && !getStateFlag(ID.F.NoFuel) && this.riderType < 2)
  				{
  					double smokeY = posY + 1.4D;
  					
  					//計算煙霧位置
  	  				float[] partPos = CalcHelper.rotateXZByAxis(-0.42F, 0F, (this.renderYawOffset % 360) * Values.N.DIV_PI_180, 1F);
  	  				//生成裝備冒煙特效
  	  				ParticleHelper.spawnAttackParticleAt(posX+partPos[1], smokeY, posZ+partPos[0], 0D, 0D, 0D, (byte)20);
  				}
  				
  	  			if (this.ticksExisted % 16 == 0)
  	  			{
  	  				this.checkRiderType();
  	  				this.checkRidingState();
  	  			}//end 16 ticks
  			}//end 4 ticks
  		}
  		
  		//sync rotate when gattai
  		if (this.getRidingEntity() instanceof EntityDestroyerAkatsuki)
		{
  			((EntityDestroyerAkatsuki) this.getRidingEntity()).syncRotateToRider();
		}
  	}
  	
	/**
	 * 合體狀態: 0:none, 1:有響, 2:有雷, 4:有電
	 * 可合計: ex: 3:有響跟雷, 7:有響雷電
	 */
  	public void checkRiderType()
  	{
  		this.riderType = 0;
  		
  		if (this.getRidingEntity() instanceof EntityDestroyerAkatsuki)
  		{
  			this.riderType = ((EntityDestroyerAkatsuki) this.getRidingEntity()).getRiderType();
  		}
  	}
  	
  	/**
  	 * state: 0:無騎乘, 1:騎乘曉, 2:騎乘曉+曉為六驅合體狀態
  	 */
  	public void checkRidingState()
  	{
  		if (this.riderType > 1)
  		{
  			this.ridingState = 2;
  		}
  		else if (this.riderType == 1)
  		{
  			this.ridingState = 1;
  		}
  		else
  		{
  			this.ridingState = 0;
  		}
  	}
  	
  	@Override
  	protected void updateFuelState(boolean nofuel)
	{
  		if (nofuel && this.getRidingEntity() instanceof EntityDestroyerAkatsuki)
  		{
  			((EntityDestroyerAkatsuki) this.getRidingEntity()).dismountAllRider();
  		}
  		
  		super.updateFuelState(nofuel);
	}
  	
  	//增加閃避30%
  	@Override
  	public void calcShipAttributesAddRaw()
  	{
  		super.calcShipAttributesAddRaw();
  		
  		this.getAttrs().setAttrsRaw(ID.Attrs.DODGE, this.getAttrs().getAttrsRaw(ID.Attrs.DODGE) + 0.3F);
  	}
  	
  	@Override
	public double getMountedYOffset()
  	{
		if (this.isSitting())
		{
			if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
			{
				return this.height * -0.07F;
  			}
  			else
  			{
  				return this.height * 0.26F;
  			}
  		}
  		else
  		{
  			return this.height * 0.64F;
  		}
	}
	
  	@Override
    public boolean attackEntityFrom(DamageSource attacker, float atk)
  	{
  		if (this.world.isRemote) return false;

		boolean dd = super.attackEntityFrom(attacker, atk);
		
		if (dd)
		{
			//cancel gattai
			if (this.getRidingEntity() instanceof EntityDestroyerAkatsuki)
			{
				((EntityDestroyerAkatsuki) this.getRidingEntity()).dismountAllRider();
			}
		}
		
		return dd;
	}
  	
	@Override
	public int getRiderType()
	{
		return this.riderType;
	}

	@Override
	public void setRiderType(int type)
	{
		this.riderType = type;
	}
	
  	@Override
  	public int getRidingState()
  	{
  		return this.ridingState;
  	}
  	
	@Override
	public void setRidingState(int state)
	{
		this.ridingState = state;
	}
	

}