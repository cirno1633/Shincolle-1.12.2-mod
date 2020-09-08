package com.lulan.shincolle.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ShipModelRenderer extends ModelRenderer
{
	
	//額外設定的大小值, 位移值, 旋轉值
	public boolean tweakModel;
	public float scale2x, scale2y, scale2z, trans2x, trans2y, trans2z, rotat2x, rotat2y, rotat2z;

	
	public ShipModelRenderer(ModelBase model)
	{
		super(model);
		tweakModel = false;
		scale2x = 0F;
		scale2y = 0F;
		scale2z = 0F;
		trans2x = 0F;
		trans2y = 0F;
		trans2z = 0F;
		rotat2x = 0F;
		rotat2y = 0F;
		rotat2z = 0F;
	}
	
	public ShipModelRenderer(ModelBase model, String modelName)
	{
		super(model, modelName);
		tweakModel = false;
		scale2x = 0F;
		scale2y = 0F;
		scale2z = 0F;
		trans2x = 0F;
		trans2y = 0F;
		trans2z = 0F;
		rotat2x = 0F;
		rotat2y = 0F;
		rotat2z = 0F;
	}
	 
	public ShipModelRenderer(ModelBase model, int textureOffX, int textureOffY)
	{
		super(model, textureOffX, textureOffY);
		tweakModel = false;
		scale2x = 0F;
		scale2y = 0F;
		scale2z = 0F;
		trans2x = 0F;
		trans2y = 0F;
		trans2z = 0F;
		rotat2x = 0F;
		rotat2y = 0F;
		rotat2z = 0F;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void render(float scale)
	{
		if (tweakModel)
		{
			GlStateManager.pushMatrix();
			GlStateManager.scale(scale2x, scale2y, scale2z);
			GlStateManager.rotate(rotat2x, 1F, 0F, 0F);
			GlStateManager.rotate(rotat2y, 0F, 1F, 0F);
			GlStateManager.rotate(rotat2z, 0F, 0F, 1F);
			GlStateManager.translate(trans2x, trans2y, trans2z);
			super.render(scale);
			GlStateManager.popMatrix();
		}
		else
		{
			super.render(scale);
		}
    }
	
	
}