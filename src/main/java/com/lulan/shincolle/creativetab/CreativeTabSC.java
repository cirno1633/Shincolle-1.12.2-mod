package com.lulan.shincolle.creativetab;

import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

//建立mod的creative tab
public class CreativeTabSC
{
		
	public static final CreativeTabs SC_TAB = new CreativeTabs(Reference.MOD_ID)
	{
		
		@Override
		public ItemStack createIcon()
		{
			return new ItemStack(ModItems.Grudge);
		}	
		//tab顯示的名稱會自動找語系檔的字串填入  不必使用getTranslatedTabLabel
		//只要在語系檔中加入  itemGroup.MOD名稱=要顯示的tab名稱  即可
	};

	
}
