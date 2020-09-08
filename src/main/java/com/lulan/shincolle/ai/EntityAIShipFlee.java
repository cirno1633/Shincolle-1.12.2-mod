package com.lulan.shincolle.ai;

import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.Vec3d;

/**FLEE AI
 * if ship's HP is below fleeHP, ship will stop attack and try to flee
 */
public class EntityAIShipFlee extends EntityAIBase
{
	
	private BasicEntityShip host;
	private EntityLivingBase owner;
	private ShipPathNavigate ShipPathfinder;
	private float distSq, distSqrt;
	private float fleehp;		//flee HP (percent)
	private int findCooldown;	//find path cooldown
	//直線前進用餐數
    private double distX, distY, distZ, motX, motY, motZ;	//跟目標的直線距離(的平方)
    private float rotYaw;

	public EntityAIShipFlee(BasicEntityShip entity)
	{
        this.host = entity;
        this.ShipPathfinder = entity.getShipNavigate();
        this.setMutexBits(7);
    }
	
	@Override
	public boolean shouldExecute()
	{
		this.fleehp = (float)host.getStateMinor(ID.M.FleeHP) * 0.01F;
		
		//血量低於fleeHP 且不是坐下也不是綁住的狀態才執行flee AI
		if (!host.isSitting() && !host.getLeashed() && 
			(host.getHealth() / host.getMaxHealth()) <= this.fleehp &&
			host.getStateMinor(ID.M.NumGrudge) > 0)
		{
			EntityLivingBase OwnerEntity = (EntityLivingBase) host.getHostEntity();
			
			if (OwnerEntity != null)
			{
				owner = OwnerEntity;
				
				//計算直線距離
		    	this.distX = this.owner.posX - this.host.posX;
				this.distY = this.owner.posY - this.host.posY;
				this.distZ = this.owner.posZ - this.host.posZ;
		    	this.distSq = (float) (this.distX*this.distX + this.distY*this.distY + this.distZ*this.distZ);

		    	if(distSq > 6F && distSq < 3600F) return true;
			}
		}
		
		return false;
	}
	
    @Override
	public boolean shouldContinueExecuting()
    {
    	return shouldExecute();
    }

    @Override
	public void startExecuting()
    {
    	this.findCooldown = 0;
    }

    @Override
	public void resetTask()
    {
        this.owner = null;
        this.ShipPathfinder.clearPath();
    }
    
    @Override
	public void updateTask()
    {
    	this.findCooldown--;
    	this.motY = 0D;
    	
    	//設定頭部轉向
        this.host.getLookHelper().setLookPositionWithEntity(this.owner, 10.0F, this.host.getVerticalFaceSpeed());

    	//每cd到找一次路徑
    	if (this.findCooldown <= 0)
    	{
			this.findCooldown = 16;
			boolean canMove = false;
			
			if (host.isRiding() && host.getRidingEntity() instanceof BasicEntityMount)
			{
				canMove = ((BasicEntityMount)host.getRidingEntity()).getShipNavigate().tryMoveToEntityLiving(this.owner, 1.2D);
			}
			else
			{
				canMove = this.ShipPathfinder.tryMoveToEntityLiving(this.owner, 1.2D);
			}
			
			//move failed, teleport entity
			if (!canMove)
			{
        		//check config
        		if (!ConfigHandler.canTeleport) return;
        		
        		if (this.distSq > 100F)
        		{
        			LogHelper.debug("DEBUG: flee AI: moving fail, teleport entity "+this.host);
        			
        			//相同dim才傳送
        			if (this.host.dimension == this.owner.dimension)
        			{
        				EntityHelper.applyTeleport(this.host, this.distSq, new Vec3d(this.owner.posX, this.owner.posY + 0.5D, this.owner.posZ));
                        return;
        			}
                }
            }//end !try move to owner
        }//end path find cooldown
    }
	
    
}