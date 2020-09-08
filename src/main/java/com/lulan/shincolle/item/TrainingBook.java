package com.lulan.shincolle.item;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.proxy.CommonProxy;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class TrainingBook extends BasicItem
{
	
	private static final String NAME = "TrainingBook";
	
	public TrainingBook()
	{
		super();
		this.setTranslationKey(NAME);
	}
	
	//display equip information
    @Override
    public void addInformation(ItemStack itemstack, World world, List list, ITooltipFlag par4)
    {  	
    	list.add(TextFormatting.GOLD + I18n.format("gui.shincolle:trainingbook"));
    }
    
	//start use item
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
		if (CommonProxy.activeMetamorph && ConfigHandler.enableMetamorphSkill && hand == EnumHand.MAIN_HAND)
        {
            player.setActiveHand(hand);
            return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItemMainhand());
        }
        else
        {
            return new ActionResult(EnumActionResult.FAIL, player.getHeldItem(hand));
        }
    }
    
    @Override
	public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.EAT;
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
    {
        if (CommonProxy.activeMetamorph && ConfigHandler.enableMetamorphSkill)
        {
        	return 80;
        }
        else
        {
        	return 0;
        }
    }
	
    @Override
	@Nullable
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase host)
    {
		if (host instanceof EntityPlayer && world != null && !world.isRemote &&
			CommonProxy.activeMetamorph && ConfigHandler.enableMetamorphSkill)
		{
			EntityPlayer player = (EntityPlayer) host;
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
			
			if (capa != null && capa.morphEntity instanceof BasicEntityShip)
			{
				BasicEntityShip ship = (BasicEntityShip) capa.morphEntity;
				
				//level up when use finish
				if (ship.getLevel() < 150)
				{
					int lv = ship.getLevel() + 5 + ship.getRNG().nextInt(6);
					if (lv > 150) lv = 150;
					
					ship.setShipLevel(lv, true);
					
					//level up sound
					ship.playSound(ModSounds.SHIP_LEVEL, 0.75F, 1F);
					ship.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_LEVELUP, ship.getSoundCategory(), 0.75F, 1F);
					
					//item--
					if (!player.capabilities.isCreativeMode) stack.shrink(1);
				}
				
			}
		}
		
        return stack;
    }
	
    
}