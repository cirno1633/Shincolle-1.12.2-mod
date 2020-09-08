package com.lulan.shincolle.ai;

import com.lulan.shincolle.entity.BasicEntityShip;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

/**SIT AI FOR SHIP
 * 可以在液體中坐下
 */
public class EntityAIShipSit extends EntityAIBase
{
    private BasicEntityShip host;
    private EntityLivingBase owner;

    public EntityAIShipSit(BasicEntityShip entity)
    {
        this.host = entity;
        this.setMutexBits(7);
    }

    @Override
	public boolean shouldExecute()
    {
//    	LogHelper.info("DEBUG : exec sitting "+(this.owner == null));
        return this.host.isSitting();
    }

    @Override
	public void startExecuting()
    {
    	this.host.setSitting(true);
    	this.host.setJumping(false);
    }
    
    @Override
	public void updateTask()
    {
//    	LogHelper.info("DEBUG : exec sitting");
    	this.host.getNavigator().clearPath();
        this.host.setAttackTarget(null);
        this.host.setEntityTarget(null);
    }

    @Override
	public void resetTask()
    {
        this.host.setSitting(false);
    }
    
    
}