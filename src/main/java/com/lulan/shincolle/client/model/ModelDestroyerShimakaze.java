package com.lulan.shincolle.client.model;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.EmotionHelper;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelDestroyerShimakaze - PinkaLulan 2015/3/27
 * Created using Tabula 4.1.1
 */
public class ModelDestroyerShimakaze extends ShipModelBaseAdv
{
	
    public ModelRenderer BodyMain;
    public ModelRenderer NeckCloth;
    public ModelRenderer ArmLeft;
    public ModelRenderer ArmRight;
    public ModelRenderer Butt;
    public ModelRenderer EquipBase;
    public ModelRenderer Head;
    public ModelRenderer NeckTie;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer Ahoke;
    public ModelRenderer HairL01;
    public ModelRenderer HairR01;
    public ModelRenderer HairL02;
    public ModelRenderer HairAnchor;
    public ModelRenderer HairR02;
    public ModelRenderer HairMidL01;
    public ModelRenderer HairMidR01;
    public ModelRenderer EarBase;
    public ModelRenderer HairMidL02;
    public ModelRenderer HairMidR02;
    public ModelRenderer EarL01;
    public ModelRenderer EarL02;
    public ModelRenderer EarR01;
    public ModelRenderer EarR02;
    public ModelRenderer LegRight;
    public ModelRenderer LegLeft;
    public ModelRenderer Skirt;
    public ModelRenderer ShoesR;
    public ModelRenderer ShoesL;
    public ModelRenderer EquipHead;
    public ModelRenderer EquipT01;
    public ModelRenderer EquipT02;
    public ModelRenderer EquipT03;
    public ModelRenderer EquipT04;
    public ModelRenderer EquipT05;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeckCloth;
    public ModelRenderer GlowHead;
    
    
    public ModelDestroyerShimakaze()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.offsetItem = new float[] {-0.16F, 1.24F, -0.03F};
        this.offsetBlock = new float[] {-0.16F, 1.24F, -0.03F};
        
        this.setDefaultFaceModel();
        
        this.Head = new ModelRenderer(this, 24, 101);
        this.Head.setRotationPoint(0.0F, -1.5F, 0.0F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.HairMain = new ModelRenderer(this, 23, 61);
        this.HairMain.setRotationPoint(0.0F, -15.0F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 9, 10, 0.0F);
        this.Hair = new ModelRenderer(this, 24, 80);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.0F);
        this.Hair.addBox(-8.0F, -7.5F, -8.0F, 16, 12, 8, 0.0F);
        this.HairMidR01 = new ModelRenderer(this, 42, 40);
        this.HairMidR01.mirror = true;
        this.HairMidR01.setRotationPoint(-2.5F, 9.0F, 2.5F);
        this.HairMidR01.addBox(-4.5F, 0.0F, 0.0F, 9, 13, 8, 0.0F);
        this.setRotateAngle(HairMidR01, 0.13962634015954636F, -0.08726646259971647F, 0.2617993877991494F);
        this.HairMidL02 = new ModelRenderer(this, 46, 21);
        this.HairMidL02.setRotationPoint(0.0F, 12.0F, 3.0F);
        this.HairMidL02.addBox(-4.5F, 0.0F, 0.0F, 9, 14, 5, 0.0F);
        this.setRotateAngle(HairMidL02, 0.13962634015954636F, 0.0F, -0.13962634015954636F);
        this.HairMidL01 = new ModelRenderer(this, 42, 40);
        this.HairMidL01.setRotationPoint(2.5F, 9.0F, 2.5F);
        this.HairMidL01.addBox(-4.5F, 0.0F, 0.0F, 9, 13, 8, 0.0F);
        this.setRotateAngle(HairMidL01, 0.13962634015954636F, 0.08726646259971647F, -0.2617993877991494F);
        this.HairMidR02 = new ModelRenderer(this, 46, 21);
        this.HairMidR02.mirror = true;
        this.HairMidR02.setRotationPoint(0.0F, 12.0F, 3.0F);
        this.HairMidR02.addBox(-4.5F, 0.0F, 0.0F, 9, 14, 5, 0.0F);
        this.setRotateAngle(HairMidR02, 0.13962634015954636F, 0.0F, 0.13962634015954636F);
        this.Skirt = new ModelRenderer(this, 50, 0);
        this.Skirt.setRotationPoint(0.0F, 5.5F, 0.0F);
        this.Skirt.addBox(-8.5F, 0.0F, -6.0F, 17, 6, 9, 0.0F);
        this.setRotateAngle(Skirt, -0.17453292519943295F, 0.0F, 0.0F);     
        this.HairL02 = new ModelRenderer(this, 103, 1);
        this.HairL02.setRotationPoint(-0.2F, 8.5F, 0.5F);
        this.HairL02.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 3, 0.0F);
        this.setRotateAngle(HairL02, 0.2617993877991494F, 0.0F, 0.17453292519943295F);
        this.EarBase = new ModelRenderer(this, 80, 113);
        this.EarBase.setRotationPoint(-2.0F, -2.0F, 2.0F);
        this.EarBase.addBox(0.0F, 0.0F, 0.0F, 4, 3, 4, 0.0F);
        this.EquipT05 = new ModelRenderer(this, 85, 65);
        this.EquipT05.setRotationPoint(-8.1F, -8.0F, 1.0F);
        this.EquipT05.addBox(0.0F, 0.0F, 0.0F, 3, 31, 3, 0.0F);
        this.HairR01 = new ModelRenderer(this, 102, 0);
        this.HairR01.setRotationPoint(-5.5F, 0.0F, -3.0F);
        this.HairR01.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 4, 0.0F);
        this.setRotateAngle(HairR01, -0.2617993877991494F, 0.17453292519943295F, 0.2617993877991494F);
        this.EquipT01 = new ModelRenderer(this, 85, 65);
        this.EquipT01.setRotationPoint(5.1F, -8.0F, 1.0F);
        this.EquipT01.addBox(0.0F, 0.0F, 0.0F, 3, 31, 3, 0.0F);
        this.EarR01 = new ModelRenderer(this, 83, 113);
        this.EarR01.setRotationPoint(0.0F, 2.5F, 2.0F);
        this.EarR01.addBox(-1.5F, -10.0F, -1.0F, 3, 10, 2, 0.0F);
        this.EarR02 = new ModelRenderer(this, 82, 113);
        this.EarR02.setRotationPoint(0.0F, -9.0F, 0.0F);
        this.EarR02.addBox(-2.0F, -13.0F, -1.0F, 4, 13, 2, 0.0F);
        this.EarL01 = new ModelRenderer(this, 83, 113);
        this.EarL01.setRotationPoint(4.0F, 2.5F, 2.0F);
        this.EarL01.addBox(-1.5F, -10.0F, -1.0F, 3, 10, 2, 0.0F);
        this.EarL02 = new ModelRenderer(this, 82, 113);
        this.EarL02.setRotationPoint(0.0F, -9.0F, 0.0F);
        this.EarL02.addBox(-2.0F, -13.0F, -1.0F, 4, 13, 2, 0.0F);  
        this.NeckCloth = new ModelRenderer(this, 0, 0);
        this.NeckCloth.setRotationPoint(0.0F, -10.0F, 0.0F);
        this.NeckCloth.addBox(-7.5F, -1.5F, -4.5F, 15, 12, 8, 0.0F);
        this.Ahoke = new ModelRenderer(this, 65, 88);
        this.Ahoke.setRotationPoint(0.0F, -14.0F, -4.0F);
        this.Ahoke.addBox(0.0F, 0.0F, -12.0F, 0, 13, 12, 0.0F);
        this.setRotateAngle(Ahoke, 0.0F, 0.5235987755982988F, 0.0F);
        this.ShoesR = new ModelRenderer(this, 88, 15);
        this.ShoesR.setRotationPoint(0.0F, 19.0F, -0.2F);
        this.ShoesR.addBox(-3.5F, 0.0F, -3.5F, 7, 7, 7, 0.0F);
        this.HairL01 = new ModelRenderer(this, 102, 0);
        this.HairL01.setRotationPoint(5.5F, 0.0F, -3.0F);
        this.HairL01.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 4, 0.0F);
        this.setRotateAngle(HairL01, -0.2617993877991494F, -0.17453292519943295F, -0.2617993877991494F);
        this.ShoesL = new ModelRenderer(this, 88, 15);
        this.ShoesL.setRotationPoint(0.0F, 19.0F, -0.2F);
        this.ShoesL.addBox(-3.5F, 0.0F, -3.5F, 7, 7, 7, 0.0F);
        this.EquipT04 = new ModelRenderer(this, 85, 65);
        this.EquipT04.setRotationPoint(-4.8F, -8.0F, 1.0F);
        this.EquipT04.addBox(0.0F, 0.0F, 0.0F, 3, 31, 3, 0.0F);
        this.Butt = new ModelRenderer(this, 0, 22);
        this.Butt.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Butt.addBox(-8.0F, 4.0F, -5.4F, 16, 8, 7, 0.0F);
        this.setRotateAngle(Butt, 0.2617993877991494F, 0.0F, 0.0F);
        this.EquipT03 = new ModelRenderer(this, 85, 65);
        this.EquipT03.setRotationPoint(-1.5F, -8.0F, 1.0F);
        this.EquipT03.addBox(0.0F, 0.0F, 0.0F, 3, 31, 3, 0.0F);
        this.EquipBase = new ModelRenderer(this, 76, 33);
        this.EquipBase.setRotationPoint(2.0F, -5.0F, 7.0F);
        this.EquipBase.addBox(-7.0F, 0.0F, -3.7F, 14, 8, 12, 0.0F);
        this.setRotateAngle(EquipBase, 0.13962634015954636F, 0.0F, 0.5235987755982988F);
        this.EquipHead = new ModelRenderer(this, 77, 29);
        this.EquipHead.setRotationPoint(0.0F, -3.0F, -0.3F);
        this.EquipHead.addBox(-9.0F, 0.0F, 0.0F, 18, 17, 7, 0.0F);
        this.LegRight = new ModelRenderer(this, 0, 96);
        this.LegRight.setRotationPoint(-4.5F, 9.5F, -3.0F);
        this.LegRight.addBox(-3.0F, 0.0F, -3.0F, 6, 19, 6, 0.0F);
        this.setRotateAngle(LegRight, -0.2617993877991494F, 0.0F, -0.05235987755982988F);
        this.HairAnchor = new ModelRenderer(this, 112, 7);
        this.HairAnchor.setRotationPoint(0.2F, 8.0F, -1.0F);
        this.HairAnchor.addBox(-1.5F, 0.0F, 0.0F, 2, 5, 6, 0.0F);
        this.setRotateAngle(HairAnchor, 0.08726646259971647F, 0.0F, 0.136659280431156F);
        this.EquipT02 = new ModelRenderer(this, 85, 65);
        this.EquipT02.setRotationPoint(1.8F, -8.0F, 1.0F);
        this.EquipT02.addBox(0.0F, 0.0F, 0.0F, 3, 31, 3, 0.0F);
        this.LegLeft = new ModelRenderer(this, 0, 96);
        this.LegLeft.mirror = true;
        this.LegLeft.setRotationPoint(4.5F, 9.5F, -3.0F);
        this.LegLeft.addBox(-3.0F, 0.0F, -3.0F, 6, 19, 6, 0.0F);
        this.setRotateAngle(LegLeft, -0.2617993877991494F, 0.0F, 0.05235987755982988F);
        this.BodyMain = new ModelRenderer(this, 0, 37);
        this.BodyMain.setRotationPoint(0.0F, -12.0F, 0.0F);
        this.BodyMain.addBox(-7.0F, -11.0F, -4.0F, 14, 17, 7, 0.0F);
        this.NeckTie = new ModelRenderer(this, 39, 0);
        this.NeckTie.setRotationPoint(0.0F, 2.5F, -4.7F);
        this.NeckTie.addBox(-3.5F, 0.0F, 0.0F, 7, 7, 0, 0.0F);
        this.setRotateAngle(NeckTie, -0.13962634015954636F, 0.0F, 0.0F);
        this.ArmRight = new ModelRenderer(this, 0, 61);
        this.ArmRight.setRotationPoint(-7.0F, -10.5F, 0.0F);
        this.ArmRight.addBox(-2.5F, 0.0F, -2.5F, 5, 22, 5, 0.0F);
        this.setRotateAngle(ArmRight, 0.0F, 0.0F, 0.4363323129985824F);
        this.ArmLeft = new ModelRenderer(this, 0, 61);
        this.ArmLeft.mirror = true;
        this.ArmLeft.setRotationPoint(7.0F, -10.5F, 0.0F);
        this.ArmLeft.addBox(-2.5F, 0.0F, -2.5F, 5, 22, 5, 0.0F);
        this.setRotateAngle(ArmLeft, 0.0F, 0.0F, -0.3490658503988659F);
        this.HairR02 = new ModelRenderer(this, 103, 1);
        this.HairR02.setRotationPoint(0.2F, 8.5F, 0.5F);
        this.HairR02.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 3, 0.0F);
        this.setRotateAngle(HairR02, 0.17453292519943295F, 0.0F, -0.17453292519943295F);
        this.HairMain.addChild(this.HairMidR01);
        this.HairMain.addChild(this.HairMidL01);
        this.EarL01.addChild(this.EarL02);
        this.EarR01.addChild(this.EarR02);
        this.HairL01.addChild(this.HairL02);
        this.HairMain.addChild(this.EarBase);
        this.EquipBase.addChild(this.EquipT05);
        this.Hair.addChild(this.HairR01);
        this.EquipBase.addChild(this.EquipT01); 
        this.BodyMain.addChild(this.NeckCloth);
        this.Hair.addChild(this.Ahoke);
        this.LegRight.addChild(this.ShoesR);
        this.Hair.addChild(this.HairL01);
        this.LegLeft.addChild(this.ShoesL);
        this.EquipBase.addChild(this.EquipT04);
        this.BodyMain.addChild(this.Butt);
        this.EquipBase.addChild(this.EquipT03);
        this.BodyMain.addChild(this.EquipBase);
        this.NeckCloth.addChild(this.Head);
        this.Head.addChild(this.HairMain);
        this.EquipBase.addChild(this.EquipHead);
        this.Butt.addChild(this.LegRight);
        this.HairL02.addChild(this.HairAnchor);
        this.EquipBase.addChild(this.EquipT02);
        this.Butt.addChild(this.LegLeft);
        this.NeckCloth.addChild(this.NeckTie);
        this.EarBase.addChild(this.EarL01);
        this.EarBase.addChild(this.EarR01);
        this.BodyMain.addChild(this.ArmRight);
        this.BodyMain.addChild(this.ArmLeft);
        this.HairR01.addChild(this.HairR02);
        this.Butt.addChild(this.Skirt);   
        this.Head.addChild(this.Hair);
        this.HairMidL01.addChild(this.HairMidL02);
        this.HairMidR01.addChild(this.HairMidR02);
        
        //發光支架
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, -12.0F, 0.0F);
        this.GlowNeckCloth = new ModelRenderer(this, 0, 0);
        this.GlowNeckCloth.setRotationPoint(0.0F, -10.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -1.5F, 0.0F);
        
        this.GlowBodyMain.addChild(this.GlowNeckCloth);
        this.GlowNeckCloth.addChild(this.GlowHead);
        this.GlowHead.addChild(this.Face0);
        this.GlowHead.addChild(this.Face1);
        this.GlowHead.addChild(this.Face2);
        this.GlowHead.addChild(this.Face3);
        this.GlowHead.addChild(this.Face4);
        this.GlowHead.addChild(this.Mouth0);
        this.GlowHead.addChild(this.Mouth1);
        this.GlowHead.addChild(this.Mouth2);
        this.GlowHead.addChild(this.Flush0);
        this.GlowHead.addChild(this.Flush1);
        
     	//for held item rendering
        this.armMain = new ModelRenderer[] {this.BodyMain, this.ArmRight};
        this.armOff = new ModelRenderer[] {this.BodyMain, this.ArmLeft};
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
    	//FIX: head rotation bug while riding
    	if (f3 <= -180F) { f3 += 360F; }
    	else if (f3 >= 180F) { f3 -= 360F; }
    	
    	switch (((IShipEmotion)entity).getScaleLevel())
    	{
    	case 3:
    		scale = 1.64F;
        	offsetY = -0.58F;
		break;
    	case 2:
    		scale = 1.23F;
        	offsetY = -0.27F;
		break;
    	case 1:
    		scale = 0.82F;
        	offsetY = 0.35F;
		break;
    	default:
    		scale = 0.41F;
        	offsetY = 2.17F;
		break;
    	}
    	
    	GlStateManager.pushMatrix();
    	GlStateManager.enableBlend();
    	GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
    	GlStateManager.scale(scale, scale, scale);
    	GlStateManager.translate(0F, offsetY, 0F);
    	
    	//main body
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	this.BodyMain.render(f5);
    	
    	//light part
    	GlStateManager.disableLighting();
    	GlStateManager.enableCull();
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
    	this.GlowBodyMain.render(f5);
    	GlStateManager.disableCull();
    	GlStateManager.enableLighting();
    	
    	GlStateManager.disableBlend();
    	GlStateManager.popMatrix();
    }

	@Override
	public void showEquip(IShipEmotion ent)
	{
		int state = ent.getStateEmotion(ID.S.State);
		
		boolean flag = !EmotionHelper.checkModelState(1, state);	//cannon
		this.EquipBase.isHidden = flag;
		
		flag = !EmotionHelper.checkModelState(2, state);			//hair
		this.HairAnchor.isHidden = flag;
				
		boolean fh1 = EmotionHelper.checkModelState(3, state);		//ear state 1
		boolean fh2 = EmotionHelper.checkModelState(4, state);		//ear state 2
		boolean fh3 = EmotionHelper.checkModelState(5, state);		//ear state 3
		
		if (fh1 || fh2 || fh3)
		{
			this.EarBase.isHidden = false;
		}
		else
		{
			this.EarBase.isHidden = true;
		}
	}

	@Override
	public void syncRotationGlowPart()
	{
		this.GlowBodyMain.rotateAngleX = this.BodyMain.rotateAngleX;
		this.GlowBodyMain.rotateAngleY = this.BodyMain.rotateAngleY;
		this.GlowBodyMain.rotateAngleZ = this.BodyMain.rotateAngleZ;
		this.GlowNeckCloth.rotateAngleX = this.NeckCloth.rotateAngleX;
		this.GlowNeckCloth.rotateAngleY = this.NeckCloth.rotateAngleY;
		this.GlowNeckCloth.rotateAngleZ = this.NeckCloth.rotateAngleZ;
		this.GlowHead.rotateAngleX = this.Head.rotateAngleX;
		this.GlowHead.rotateAngleY = this.Head.rotateAngleY;
		this.GlowHead.rotateAngleZ = this.Head.rotateAngleZ;
	}

	@Override
	public void applyDeadPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
    	GlStateManager.translate(0F, 0.55F + 0.26F * ent.getScaleLevel(), 0F);
    	this.setFaceHungry(ent);
    	
  	    //ear
  	    this.EarL01.rotateAngleX = 1F;
  	    this.EarL01.rotateAngleY = -0.4F;
  	    this.EarL01.rotateAngleZ = 0F;
	    this.EarR01.rotateAngleX = 1F;
	    this.EarR01.rotateAngleY = 1.0472F;
	    this.EarR01.rotateAngleZ = 0F;
  	    this.EarL02.rotateAngleX = -0.8F;
  	    this.EarL02.rotateAngleY = 0F;
  	    this.EarL02.rotateAngleZ = 0F;
  	    this.EarR02.rotateAngleX = -0.2F;
  	    this.EarR02.rotateAngleY = -0.2F;
  	    this.EarR02.rotateAngleZ = 0F;
		//equip
		this.EquipBase.rotateAngleZ = 0.52F;
		//body
    	this.Head.rotateAngleX = 0F;
    	this.Head.rotateAngleY = 0F;
    	this.Head.rotateAngleZ = 0F;
    	this.Ahoke.rotateAngleY = 0.5236F;
	  	this.BodyMain.rotateAngleY = 0F;
    	this.BodyMain.rotateAngleX = 1.4835F;
    	this.HairMidL01.rotateAngleX = -0.05F;
    	this.HairMidR01.rotateAngleX = -0.05F;
    	this.HairMidL02.rotateAngleX = -0.1F;
    	this.HairMidR02.rotateAngleX = -0.1F;
    	//arm
    	this.ArmLeft.rotateAngleX = -0.12F;
    	this.ArmLeft.rotateAngleZ = -0.2F;
    	this.ArmRight.rotateAngleX = -0.12F;
    	this.ArmRight.rotateAngleZ = 0.2F;
    	//leg
    	this.LegLeft.rotateAngleX = -0.2618F;
    	this.LegRight.rotateAngleX = -0.2618F;
    	this.LegLeft.rotateAngleY = 0F;
		this.LegRight.rotateAngleY = 0F;
    	this.LegLeft.rotateAngleZ = 0.03F;
    	this.LegRight.rotateAngleZ = -0.03F;
	}

	@Override
	public void applyNormalPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
  		float angleX = MathHelper.cos(f2*0.08F);
  		float angleX1 = MathHelper.cos(f2*0.08F + 0.3F + f * 0.5F);
  		float angleRun = MathHelper.cos(f * 1.5F) * f1;
  		float addk1 = 0;
  		float addk2 = 0;
  		
  		//水上漂浮
  		if (ent.getShipDepth(0) > 0D)
  		{
  			GlStateManager.translate(0F, angleX * 0.05F + 0.025F, 0F);
    	}
  		
  		//leg move parm
  		addk1 = MathHelper.cos(f * 0.7F) * f1 - 0.21F;
	  	addk2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1 - 0.11F;

  	    //移動頭部使其看人
	  	this.Head.rotateAngleX = f4 * 0.014F + 0.1F;
	  	this.Head.rotateAngleY = f3 * 0.01F;
  	    //ear
	  	int state = ent.getStateEmotion(ID.S.State);
		boolean fh1 = EmotionHelper.checkModelState(3, state);
		boolean fh2 = EmotionHelper.checkModelState(4, state);
		boolean fh3 = EmotionHelper.checkModelState(5, state);
		boolean fh4 = fh1 && fh2;
		boolean fh5 = fh1 && fh3;
		boolean fh6 = fh2 && fh3;
		boolean fh7 = fh1 && fh2 && fh3;
	  	
		if (fh7)
		{
			this.EarL01.rotateAngleX = angleX * 0.075F + 0.6F;
	  	    this.EarL01.rotateAngleY = -0.5F;
	  	    this.EarL01.rotateAngleZ = 0F;
		    this.EarR01.rotateAngleX = angleX * 0.075F + 1.1F;
		    this.EarR01.rotateAngleY = 0.5F;
		    this.EarR01.rotateAngleZ = 0F;
	  	    this.EarL02.rotateAngleX = angleX1 * 0.1F + 0.7F;
	  	    this.EarL02.rotateAngleY = 0.1F;
	  	    this.EarL02.rotateAngleZ = 0F;
	  	    this.EarR02.rotateAngleX = angleX1 * 0.1F + 1.0F;
	  	    this.EarR02.rotateAngleY = -0.1F;
	  	    this.EarR02.rotateAngleZ = 0F;
		}
		else if (fh6)
		{
			this.EarL01.rotateAngleX = angleX * 0.075F + 1.1F;
	  	    this.EarL01.rotateAngleY = -0.5F;
	  	    this.EarL01.rotateAngleZ = 0F;
		    this.EarR01.rotateAngleX = angleX * 0.075F + 1.1F;
		    this.EarR01.rotateAngleY = 0.5F;
		    this.EarR01.rotateAngleZ = 0F;
	  	    this.EarL02.rotateAngleX = angleX1 * 0.1F + 1.0F;
	  	    this.EarL02.rotateAngleY = 0.1F;
	  	    this.EarL02.rotateAngleZ = 0F;
	  	    this.EarR02.rotateAngleX = angleX1 * 0.1F + 1.0F;
	  	    this.EarR02.rotateAngleY = -0.1F;
	  	    this.EarR02.rotateAngleZ = 0F;
		}
		else if (fh5)
		{
			this.EarL01.rotateAngleX = angleX * 0.075F - 1.1F;
	    	this.EarL01.rotateAngleY = 0.5F;
	    	this.EarL01.rotateAngleZ = 0F;
	    	this.EarR01.rotateAngleX = angleX1 * 0.075F - 1.1F;
	    	this.EarR01.rotateAngleY = -0.5F;
	    	this.EarR01.rotateAngleZ = 0F;
	    	this.EarL02.rotateAngleX = angleX * 0.075F - 0.8F;
	    	this.EarL02.rotateAngleY = 0F;
	    	this.EarL02.rotateAngleZ = -0.5F;
	    	this.EarR02.rotateAngleX = angleX1 * 0.075F - 0.8F;
	    	this.EarR02.rotateAngleY = 0F;
	    	this.EarR02.rotateAngleZ = 0.5F;
		}
		else if (fh4)
		{
			this.EarL01.rotateAngleX = angleX * 0.075F + 0.6F;
	  	    this.EarL01.rotateAngleY = -0.5F;
	  	    this.EarL01.rotateAngleZ = 0F;
		    this.EarR01.rotateAngleX = angleX * 0.075F + 0.6F;
		    this.EarR01.rotateAngleY = 0.5F;
		    this.EarR01.rotateAngleZ = 0F;
	  	    this.EarL02.rotateAngleX = angleX1 * 0.1F + 0.7F;
	  	    this.EarL02.rotateAngleY = 0.1F;
	  	    this.EarL02.rotateAngleZ = 0F;
	  	    this.EarR02.rotateAngleX = angleX1 * 0.1F + 0.7F;
	  	    this.EarR02.rotateAngleY = -0.1F;
	  	    this.EarR02.rotateAngleZ = 0F;
		}
		else if (fh3)
		{
			this.EarL01.rotateAngleX = angleX * 0.075F + 0.3F;
	  	    this.EarL01.rotateAngleY = -0.8F;
	  	    this.EarL01.rotateAngleZ = 0F;
		    this.EarR01.rotateAngleX = angleX * 0.075F + 0.9F;
		    this.EarR01.rotateAngleY = 0.6F;
		    this.EarR01.rotateAngleZ = 0F;
	  	    this.EarL02.rotateAngleX = angleX1 * 0.1F + 0.6F;
	  	    this.EarL02.rotateAngleY = 0.1F;
	  	    this.EarL02.rotateAngleZ = 0F;
	  	    this.EarR02.rotateAngleX = angleX1 * 0.1F + 1F;
	  	    this.EarR02.rotateAngleY = -0.1F;
	  	    this.EarR02.rotateAngleZ = 0F;
		}
		else if (fh2)
		{
			this.EarL01.rotateAngleX = angleX * 0.075F + 0.2F;
	  	    this.EarL01.rotateAngleY = -0.4F;
	  	    this.EarL01.rotateAngleZ = 0.4F;
		    this.EarR01.rotateAngleX = angleX * 0.075F + 0.2F;
		    this.EarR01.rotateAngleY = 0.4F;
		    this.EarR01.rotateAngleZ = -0.4F;
	  	    this.EarL02.rotateAngleX = angleX1 * 0.1F + 0.2F;
	  	    this.EarL02.rotateAngleY = 0F;
	  	    this.EarL02.rotateAngleZ = -0.3F;
	  	    this.EarR02.rotateAngleX = angleX1 * 0.1F + 0.2F;
	  	    this.EarR02.rotateAngleY = 0F;
	  	    this.EarR02.rotateAngleZ = 0.3F;
		}
		else if (fh1)
		{
			this.EarL01.rotateAngleX = angleX * 0.075F + -0.1F;
	  	    this.EarL01.rotateAngleY = 0.2F;
	  	    this.EarL01.rotateAngleZ = 0.4F;
		    this.EarR01.rotateAngleX = angleX * 0.075F + 0F;
		    this.EarR01.rotateAngleY = 0.2F;
		    this.EarR01.rotateAngleZ = -0.55F;
	  	    this.EarL02.rotateAngleX = angleX1 * 0.1F + 0.4F;
	  	    this.EarL02.rotateAngleY = 0F;
	  	    this.EarL02.rotateAngleZ = -0.1F;
	  	    this.EarR02.rotateAngleX = angleX1 * 0.1F + 0.9F;
	  	    this.EarR02.rotateAngleY = 0.5F;
	  	    this.EarR02.rotateAngleZ = 0F;
		}

  	    //hair
  	    this.HairMidL01.rotateAngleX = angleX * 0.07F + 0.14F;
  	    this.HairMidL02.rotateAngleX = -angleX1 * 0.2F + 0.14F;
  	    this.HairMidR01.rotateAngleX = this.HairMidL01.rotateAngleX;
  	    this.HairMidR02.rotateAngleX = this.HairMidL02.rotateAngleX;
  	    this.HairMidL01.rotateAngleZ = -0.2618F;
	    this.HairMidL02.rotateAngleZ = -0.14F;
	    this.HairMidR01.rotateAngleZ = 0.2618F;
	    this.HairMidR02.rotateAngleZ = 0.14F;
  	    this.HairL01.rotateAngleX = angleX * 0.06F - 0.2618F;
	    this.HairL02.rotateAngleX = -angleX1 * 0.1F + 0.2618F;
	    this.HairR01.rotateAngleX = angleX * 0.06F - 0.2618F;
	    this.HairR02.rotateAngleX = -angleX1 * 0.1F + 0.2618F;
	    this.HairL01.rotateAngleZ = -0.2618F;
	    this.HairL02.rotateAngleZ = 0.1745F;
	    this.HairR01.rotateAngleZ = 0.2618F;
	    this.HairR02.rotateAngleZ = -0.1745F;
	  	//Body
  	    this.Ahoke.rotateAngleY = angleX * 0.25F + 0.5236F;
	  	this.BodyMain.rotateAngleX = -0.1F;
	  	this.BodyMain.rotateAngleY = 0F;
	    //arm 
	  	this.ArmLeft.rotateAngleX = 0.15F;
	    this.ArmLeft.rotateAngleZ = angleX * 0.1F - 0.5236F;
	    this.ArmRight.rotateAngleX = 0F;
	    this.ArmRight.rotateAngleY = 0F;
		this.ArmRight.rotateAngleZ = -angleX * 0.1F + 0.5236F;
		//leg
		this.LegLeft.rotateAngleY = 0F;
		this.LegLeft.rotateAngleZ = 0.05F;
		this.LegRight.rotateAngleY = 0F;
		this.LegRight.rotateAngleZ = -0.05F;
		//equip
		this.EquipBase.rotateAngleZ = 0.52F;

	    if (ent.getIsSprinting() || f1 > 0.6F)
	    {	//奔跑動作
	    	setFace(3);
	    	//body
	    	this.Head.rotateAngleX -= 0.2618F;
	    	this.BodyMain.rotateAngleX = 0.2618F;
	    	this.HairMidL01.rotateAngleX += 0.5F;
	    	this.HairMidR01.rotateAngleX += 0.5F;
	    	this.HairMidL02.rotateAngleX += 0.5F;
	    	this.HairMidR02.rotateAngleX += 0.5F;
	    	//arm
	    	this.ArmLeft.rotateAngleX = 0.7F;
	    	this.ArmLeft.rotateAngleZ = -1.0472F;
	    	this.ArmRight.rotateAngleX = 0.7F;
	    	this.ArmRight.rotateAngleZ = 1.0472F;
	    	//leg
	    	addk1 = MathHelper.cos(f * 2F) * f1 * 1.5F - 0.5F;
		  	addk2 = MathHelper.cos(f * 2F + 3.1415927F) * f1 * 1.5F - 0.5F;
	    	this.LegLeft.rotateAngleY = 0F;
	    	this.LegLeft.rotateAngleZ = 0.05F;
	    	this.LegRight.rotateAngleY = 0F;
	    	this.LegRight.rotateAngleZ = -0.05F;
	    	//ear
	    	this.EarL01.rotateAngleX = -angleRun * 0.08F - 0.8727F;
	    	this.EarL01.rotateAngleY = 0.5F;
	    	this.EarL01.rotateAngleZ = 0F;
	    	this.EarR01.rotateAngleX = angleRun * 0.08F - 0.8727F;
	    	this.EarR01.rotateAngleY = -0.5F;
	    	this.EarR01.rotateAngleZ = 0F;
	    	this.EarL02.rotateAngleX = -angleRun * 0.1F - 0.5F;
	    	this.EarL02.rotateAngleY = 0F;
	    	this.EarL02.rotateAngleZ = -0.5F;
	    	this.EarR02.rotateAngleX = angleRun * 0.1F - 0.5F;
	    	this.EarR02.rotateAngleY = 0F;
	    	this.EarR02.rotateAngleZ = 0.5F;
  		}

	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
	    
	    if (ent.getIsSneaking())
	    {	//潛行, 蹲下動作
	    	//body
	    	this.Head.rotateAngleX -= 0.7854F;
	    	this.BodyMain.rotateAngleX = 0.7854F;
	    	//arm
	    	this.ArmLeft.rotateAngleZ = -0.5F;
	    	this.ArmRight.rotateAngleZ = 0.5F;
	    	//leg
	    	addk1 -= 0.8F;
	    	addk2 -= 0.8F;
  		}//end if sneaking
  		
	    if (ent.getIsSitting() || ent.getIsRiding())
	    {	
	    	//騎乘動作
	    	if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
	    	{	
	    		GlStateManager.translate(0F, 0.575F, 0F);
		    	//body
		    	this.Head.rotateAngleX = -1.48F;
		    	this.Head.rotateAngleY = 0F;
		    	this.Head.rotateAngleZ = 0F;
		    	this.BodyMain.rotateAngleX = 1.4835F;
		    	//arm
		    	this.ArmLeft.rotateAngleX = -3.0543F;
		    	this.ArmLeft.rotateAngleZ = -0.7F;
		    	this.ArmRight.rotateAngleX = -2.8F;
		    	this.ArmRight.rotateAngleZ = 0.35F;
		    	//leg
		    	addk1 = 0F;
		    	addk2 = -0.2618F;
		    	this.LegLeft.rotateAngleZ = 0.1745F;
		    	this.LegRight.rotateAngleZ = -0.35F;
	    	}
	    	else
	    	{
	    		GlStateManager.translate(0F, 0.45F, 0F);
		    	//body
		    	this.Head.rotateAngleX -= 0.7F;
		    	this.BodyMain.rotateAngleX = 0.5236F;
		    	//hair
		    	this.HairL01.rotateAngleX -= 0.2F;
		    	this.HairL02.rotateAngleX -= 0.2F;
		    	this.HairR01.rotateAngleX -= 0.2F;
		    	this.HairR02.rotateAngleX -= 0.2F;
		    	//arm
		    	this.ArmLeft.rotateAngleX = -0.5236F;
		    	this.ArmLeft.rotateAngleZ = 0.3146F;
		    	this.ArmRight.rotateAngleX = -0.5236F;
		    	this.ArmRight.rotateAngleZ = -0.3146F;
		    	//leg
		    	addk1 = -2.2689F;
		    	addk2 = -2.2689F;
		    	this.LegLeft.rotateAngleY = -0.3491F;
		    	this.LegRight.rotateAngleY = 0.3491F;
	    	}
  		}//end if sitting
	    
	    //攻擊動作    
	    if (ent.getAttackTick() > 20)
	    {
	    	GlStateManager.translate(0F, 0.14F + ent.getScaleLevel() * 0.07F, 0F);
	    	//body
	    	this.Head.rotateAngleX = -0.8727F;
	    	this.Head.rotateAngleY = 1.0472F;
	    	this.Head.rotateAngleZ = -0.7F;
	    	this.BodyMain.rotateAngleX = 1.3F;
	    	this.BodyMain.rotateAngleY = -1.57F;
	    	//arm
	    	this.ArmLeft.rotateAngleX = 0F;
	    	this.ArmLeft.rotateAngleZ = -0.5F;
	    	this.ArmRight.rotateAngleX = 0F;
	    	this.ArmRight.rotateAngleZ = 1.57F;
	    	//leg
	    	addk1 = -1.75F;
	    	addk2 = -1.92F;
	    	//equip
	    	this.EquipBase.rotateAngleZ = 1.57F;
	    }
	    
	    //swing arm
	  	float f6 = ent.getSwingTime(f2 - (int)f2);
	  	if (f6 != 0F)
	  	{
	  		float f7 = MathHelper.sin(f6 * f6 * (float)Math.PI);
	        float f8 = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI);
	        this.ArmRight.rotateAngleX = -0.4F;
	        this.ArmRight.rotateAngleY = 0F;
	        this.ArmRight.rotateAngleZ = -0.2F;
	        this.ArmRight.rotateAngleX += -f8 * 80.0F * Values.N.DIV_PI_180;
	        this.ArmRight.rotateAngleY += -f7 * 20.0F * Values.N.DIV_PI_180 + 0.2F;
	        this.ArmRight.rotateAngleZ += -f8 * 20.0F * Values.N.DIV_PI_180;
	  	}
	  	
	  	//鬢毛調整
	    float headX = this.Head.rotateAngleX * -0.5F;
	    float headZ = this.Head.rotateAngleZ * -0.5F;
	    this.HairMidL01.rotateAngleX += headX;
	    this.HairMidL01.rotateAngleZ += headZ;
	    this.HairMidL02.rotateAngleX += headX * 0.5F;
	    this.HairMidL02.rotateAngleZ += headZ * 0.5F;
	    this.HairMidR01.rotateAngleX += headX;
	    this.HairMidR01.rotateAngleZ += headZ;
	    this.HairMidR02.rotateAngleX += headX * 0.5F;
	    this.HairMidR02.rotateAngleZ += headZ * 0.5F;
	  	this.HairL01.rotateAngleZ += headZ;
	  	this.HairL02.rotateAngleZ += headZ;
	  	this.HairR01.rotateAngleZ += headZ;
	  	this.HairR02.rotateAngleZ += headZ;
		this.HairL01.rotateAngleX += headX;
	  	this.HairL02.rotateAngleX += headX;
	  	this.HairR01.rotateAngleX += headX;
	  	this.HairR02.rotateAngleX += headX;
	    
	    //leg motion
	    this.LegLeft.rotateAngleX = addk1;
	    this.LegRight.rotateAngleX = addk2;
	}

    
}