package com.lulan.shincolle.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.EnumHandSide;

/**
 * model base for basic emotion
 */
abstract public class ShipModelBase extends ModelBase implements IModelEmotion
{
	
	
	public ShipModelBase() {}
	
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    /** get hand model for held item rendering */
    abstract public ModelRenderer getArmForSide(EnumHandSide side);
    
    
}
