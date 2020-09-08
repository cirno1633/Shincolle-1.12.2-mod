package com.lulan.shincolle.ai;

import java.util.Random;

import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.utility.BlockHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;

/**AIRCRAFT ATTACK AI
 * entity必須實作attackEntityWithAmmo, attackEntityWithHeavyAmmo 兩個方法
 */
public class EntityAIShipAircraftAttack extends EntityAIBase
{
	
	private Random rand = new Random();
    private BasicEntityAirplane host;  	//entity with AI
    private Entity target;  			//entity of target
    private int atkDelay = 0;			//attack delay (attack when time <= 0)
    private int maxDelay = 0;			//attack max delay (calc from attack speed)  
    private float attackRange = 16F;	//attack range
    private float rangeSq;				//attack range square
    private double[] randPos = new double[3];		//random position
    
    //直線前進用餐數
    private double distSq, distX, distY, distZ, motX, motY, motZ;	//跟目標的直線距離(的平方)    
    private double distRanSqrt, distRanX, distRanY, distRanZ, ranX, ranY, ranZ;	//隨機找的目的地
    
    
    public EntityAIShipAircraftAttack(BasicEntityAirplane host)
    {
        if (!(host instanceof BasicEntityAirplane))
        {
            throw new IllegalArgumentException("AircraftAttack AI requires BasicEntityAirplane");
        }
        else
        {
            this.host = host;
            this.setMutexBits(3);
        }
    }

    //check ai start condition
    @Override
	public boolean shouldExecute()
    {
    	Entity target = this.host.getEntityTarget();
    	
    	//no ammo, go home
    	if (!this.host.canFindTarget()) return false;

        if (this.host.ticksExisted > 20 && target != null && target.isEntityAlive() && 
        	((this.host.useAmmoLight() && this.host.hasAmmoLight()) || 
        	(this.host.useAmmoHeavy() && this.host.hasAmmoHeavy())))
        {   
        	this.target = target;

            return true;
        }
        return false;
    }
    
    //init AI parameter, call once every target
    @Override
    public void startExecuting()
    {
    	this.maxDelay = (int)(ConfigHandler.baseAttackSpeed[4] / (this.host.getAttrs().getAttackSpeed())) + ConfigHandler.fixedAttackDelay[4];
        this.attackRange = this.host.useAmmoHeavy() ? 16F : 6F;
        this.rangeSq = this.attackRange * this.attackRange;
        distSq = distX = distY = distZ = motX = motY = motZ = 0D;
        
        //AI移動設定
        randPos[0] = target.posX;
        randPos[1] = target.posX;
        randPos[2] = target.posX;
    }

    //判定是否繼續AI： 有target就繼續, 或者已經移動完畢就繼續
    @Override
	public boolean shouldContinueExecuting()
    {
    	//no ammo, go home
    	if (!this.host.canFindTarget()) return false;
    	
    	//跑should exec, 若false則檢查是否還在移動中, 若無法移動則結束
        return this.shouldExecute()  || (target != null && target.isEntityAlive() && !this.host.getShipNavigate().noPath());
    }

    //重置AI方法
    @Override
	public void resetTask()
    {
        this.target = null;
        
        //keep moving, do not stop in air
        if (this.host.useAmmoHeavy())
        {
        	this.randPos = BlockHelper.findRandomPosition(this.host, this.host, 12D, 4D, 2);
        }
        else
        {
        	this.randPos = BlockHelper.findRandomPosition(this.host, this.host, 4.5D, 1.5D, 2);
        }
        
        this.host.getShipNavigate().tryMoveToXYZ(randPos[0], randPos[1], randPos[2], 1D);
    }

    //進行AI
    @Override
	public void updateTask()
    {
    	boolean onSight = false;	//判定直射是否無障礙物
    	
    	if (this.target != null)
    	{
            onSight = this.host.getEntitySenses().canSee(this.target);
            
            //目標距離計算
            this.distX = this.target.posX - this.host.posX;
    		this.distY = this.target.posY+2D - this.host.posY;
    		this.distZ = this.target.posZ - this.host.posZ;	
    		this.distSq = distX*distX + distY*distY + distZ*distZ;

        	if ((this.host.ticksExisted & 15) == 0)
        	{
        		//keep moving
                if (this.host.useAmmoHeavy())
                {
                	this.randPos = BlockHelper.findRandomPosition(this.host, this.target, 12D, 4D, 2);
                }
                else
                {
                	this.randPos = BlockHelper.findRandomPosition(this.host, this.target, 4.5D, 1.5D, 2);
                }
                
	        	//目標在射程外, 則100%速度前進
	        	if (this.distSq > this.rangeSq)
	        	{
		        	this.host.getShipNavigate().tryMoveToXYZ(randPos[0], randPos[1], randPos[2], 1D);
	        	}
	        	//目標在射程內, 則緩速移動
	        	else
	        	{
		        	this.host.getShipNavigate().tryMoveToXYZ(randPos[0], randPos[1], randPos[2], 0.4D);
	        	}
        	}
	        
	        //delay time decr
	        this.atkDelay--;
	        
	        onSight = this.host.getEntitySenses().canSee(this.target);

	        //若attack delay倒數完了且瞄準時間夠久, 則開始攻擊
	        if (this.atkDelay <= 0 && onSight && this.distSq < this.rangeSq)
	        {
	        	//由於艦載機只會輕 or 重其中一種攻擊, 因此AI這邊共用cooldown
	        	if (this.host.useAmmoLight() && this.host.hasAmmoLight())
	        	{
		            //attack method
		            this.host.attackEntityWithAmmo(this.target);
		            this.atkDelay = this.maxDelay;
	        	}
	        	
	        	if (this.host.useAmmoHeavy() && this.host.hasAmmoHeavy())
	        	{
		            //attack method
		            this.host.attackEntityWithHeavyAmmo(this.target);
		            this.atkDelay = this.maxDelay;
	        	}	
	        }
    	}//end attack target != null
    }
    
    
}