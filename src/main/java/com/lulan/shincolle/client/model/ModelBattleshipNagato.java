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
 * ModelBattleshipNagato - PinkaLulan 2015/4/2
 * Created using Tabula 4.1.1
 */
public class ModelBattleshipNagato extends ShipModelBaseAdv
{
	
    public ModelRenderer BodyMain;
    public ModelRenderer Neck;
    public ModelRenderer BoobR;
    public ModelRenderer BoobL;
    public ModelRenderer ArmLeft01;
    public ModelRenderer ArmRight01;
    public ModelRenderer Butt;
    public ModelRenderer Cloth;
    public ModelRenderer Head;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer HeadEquip;
    public ModelRenderer HeadEquip05;
    public ModelRenderer Ahoke;
    public ModelRenderer HairMidL01;
    public ModelRenderer HairMidL02;
    public ModelRenderer HeadEquip01;
    public ModelRenderer HeadEquip03;
    public ModelRenderer HeadEquip02;
    public ModelRenderer HeadEquip04;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ArmRight02;
    public ModelRenderer LegRight;
    public ModelRenderer LegLeft;
    public ModelRenderer Skirt;
    public ModelRenderer ShoesR;
    public ModelRenderer ShoesL;
    public ModelRenderer SkirtEquip;
    public ModelRenderer EquipBase;
    public ModelRenderer EquipL01;
    public ModelRenderer EquipR01;
    public ModelRenderer EquipBaseM01;
    public ModelRenderer EquipBaseM02;
    public ModelRenderer EquipBaseM03;
    public ModelRenderer EquipL02;
    public ModelRenderer EquipL03;
    public ModelRenderer EquipR04;
    public ModelRenderer EquipLCBase01;
    public ModelRenderer EquipLC2Base01;
    public ModelRenderer EquipLC2Base02;
    public ModelRenderer EquipLC201;
    public ModelRenderer EquipLC203;
    public ModelRenderer EquipLC202;
    public ModelRenderer EquipLC204;
    public ModelRenderer EquipLCBase02;
    public ModelRenderer EquipLC01;
    public ModelRenderer EquipLC03;
    public ModelRenderer EquipLCRadar;
    public ModelRenderer EquipLC02;
    public ModelRenderer EquipLC04;
    public ModelRenderer EquipR02;
    public ModelRenderer EquipR03;
    public ModelRenderer EquipRCBase01;
    public ModelRenderer EquipR04_1;
    public ModelRenderer EquipRCBase02;
    public ModelRenderer EquipRC01;
    public ModelRenderer EquipRC03;
    public ModelRenderer EquipRCRadar;
    public ModelRenderer EquipRC02;
    public ModelRenderer EquipRC04;
    public ModelRenderer EquipRC2Base01;
    public ModelRenderer EquipRC2Base02;
    public ModelRenderer EquipRC201;
    public ModelRenderer EquipRC203;
    public ModelRenderer EquipRC202;
    public ModelRenderer EquipRC204;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowHead;
    
    
    public ModelBattleshipNagato()
    {
        this.textureWidth = 256;
        this.textureHeight = 128;
        this.offsetItem = new float[] {0F, 0.92F, 0F};
        this.offsetBlock = new float[] {0F, 0.92F, 0F};
        
        this.setDefaultFaceModel();
        
        this.LegRight = new ModelRenderer(this, 0, 80);
        this.LegRight.setRotationPoint(-4.5F, 9.5F, -3.0F);
        this.LegRight.addBox(-3.0F, 0.0F, -3.0F, 6, 19, 6, 0.0F);
        this.setRotateAngle(LegRight, -0.2618F, 0.0F, -0.05235987755982988F);
        this.Cloth = new ModelRenderer(this, 96, 16);
        this.Cloth.setRotationPoint(0.0F, -11.5F, -5.0F);
        this.Cloth.addBox(-5.5F, 0.0F, 0.0F, 11, 2, 9, 0.0F);
        this.EquipL02 = new ModelRenderer(this, 128, 0);
        this.EquipL02.setRotationPoint(11.5F, 0.0F, 0.6F);
        this.EquipL02.addBox(0.0F, 0.0F, 0.0F, 10, 12, 5, 0.0F);
        this.setRotateAngle(EquipL02, 0.0F, 0.5235987755982988F, 0.0F);
        this.Neck = new ModelRenderer(this, 46, 14);
        this.Neck.setRotationPoint(0.0F, -10.0F, 0.0F);
        this.Neck.addBox(-7.0F, -0.5F, -4.5F, 14, 12, 8, 0.0F);
        this.Hair = new ModelRenderer(this, 50, 75);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.0F);
        this.Hair.addBox(-8.0F, -8.0F, -7.2F, 16, 16, 8, 0.0F);
        this.EquipBaseM01 = new ModelRenderer(this, 128, 0);
        this.EquipBaseM01.setRotationPoint(0.0F, 0.0F, 12.0F);
        this.EquipBaseM01.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 8, 0.0F);
        this.setRotateAngle(EquipBaseM01, -0.6981317007977318F, 0.0F, 0.0F);
        this.EquipRC03 = new ModelRenderer(this, 128, 117);
        this.EquipRC03.setRotationPoint(-4.0F, 2.0F, -4.0F);
        this.EquipRC03.addBox(-1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(EquipRC03, 0.0F, 0.20943951023931953F, 0.0F);
        this.BoobR = new ModelRenderer(this, 0, 70);
        this.BoobR.setRotationPoint(-3.7F, -9.0F, -3.5F);
        this.BoobR.addBox(-3.5F, 0.0F, 0.0F, 7, 5, 4, 0.0F);
        this.setRotateAngle(BoobR, -0.7853981633974483F, -0.13962634015954636F, -0.08726646259971647F);
        this.ShoesR = new ModelRenderer(this, 22, 70);
        this.ShoesR.setRotationPoint(0.0F, 19.0F, -0.2F);
        this.ShoesR.addBox(-3.5F, 0.0F, -3.5F, 7, 9, 7, 0.0F);
        this.EquipL03 = new ModelRenderer(this, 128, 26);
        this.EquipL03.setRotationPoint(5.3F, 0.0F, 1.3F);
        this.EquipL03.addBox(0.0F, 0.0F, -14.0F, 6, 18, 14, 0.0F);
        this.setRotateAngle(EquipL03, 0.0F, -0.6981317007977318F, 0.0F);
        this.EquipRCBase01 = new ModelRenderer(this, 128, 0);
        this.EquipRCBase01.setRotationPoint(0.0F, 1.0F, -7.0F);
        this.EquipRCBase01.addBox(-7.0F, -5.5F, -10.0F, 7, 11, 10, 0.0F);
        this.setRotateAngle(EquipRCBase01, 0.0F, -0.08726646259971647F, 0.0F);
        this.EquipLC04 = new ModelRenderer(this, 132, 113);
        this.EquipLC04.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipLC04.addBox(-1.0F, -1.0F, -13.0F, 2, 2, 13, 0.0F);
        this.HairMain = new ModelRenderer(this, 48, 56);
        this.HairMain.setRotationPoint(0.0F, -15.0F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 9, 10, 0.0F);
        this.BoobL = new ModelRenderer(this, 0, 70);
        this.BoobL.mirror = true;
        this.BoobL.setRotationPoint(3.7F, -9.0F, -3.5F);
        this.BoobL.addBox(-3.5F, 0.0F, 0.0F, 7, 5, 4, 0.0F);
        this.setRotateAngle(BoobL, -0.7853981633974483F, 0.13962634015954636F, 0.08726646259971647F);
        this.EquipRC2Base01 = new ModelRenderer(this, 128, 0);
        this.EquipRC2Base01.setRotationPoint(-3.0F, -1.0F, -10.0F);
        this.EquipRC2Base01.addBox(-4.5F, 0.0F, -10.0F, 9, 10, 10, 0.0F);
        this.EquipRC204 = new ModelRenderer(this, 132, 113);
        this.EquipRC204.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipRC204.addBox(-1.0F, -1.0F, -13.0F, 2, 2, 13, 0.0F);
        this.Skirt = new ModelRenderer(this, 0, 0);
        this.Skirt.setRotationPoint(0.0F, 7.0F, -2.0F);
        this.Skirt.addBox(-8.5F, 0.0F, -4.5F, 17, 6, 9, 0.0F);
        this.setRotateAngle(Skirt, -0.136659280431156F, 0.0F, 0.0F);
        this.SkirtEquip = new ModelRenderer(this, 71, 0);
        this.SkirtEquip.setRotationPoint(0.0F, -3.0F, 0.2F);
        this.SkirtEquip.addBox(-9.0F, 0.0F, -5.0F, 18, 3, 10, 0.0F);
        this.HeadEquip03 = new ModelRenderer(this, 128, 0);
        this.HeadEquip03.setRotationPoint(-7.5F, 0.0F, 9.0F);
        this.HeadEquip03.addBox(-4.0F, 0.0F, -2.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(HeadEquip03, 0.0F, 0.7853981633974483F, 0.17453292519943295F);
        this.EquipRC04 = new ModelRenderer(this, 132, 113);
        this.EquipRC04.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipRC04.addBox(-1.0F, -1.0F, -13.0F, 2, 2, 13, 0.0F);
        this.HairMidL02 = new ModelRenderer(this, 0, 32);
        this.HairMidL02.setRotationPoint(0.0F, 9.0F, 1.8F);
        this.HairMidL02.addBox(-7.0F, 0.0F, 0.0F, 14, 14, 7, 0.0F);
        this.setRotateAngle(HairMidL02, -0.17453292519943295F, 0.0F, 0.0F);
        this.HeadEquip04 = new ModelRenderer(this, 92, 30);
        this.HeadEquip04.setRotationPoint(-4.0F, 1.0F, 0.0F);
        this.HeadEquip04.addBox(-10.0F, -1.0F, -1.0F, 10, 2, 2, 0.0F);
        this.setRotateAngle(HeadEquip04, 0.0F, 0.0F, 0.08726646259971647F);
        this.EquipR02 = new ModelRenderer(this, 128, 0);
        this.EquipR02.setRotationPoint(-11.5F, 0.0F, 0.6F);
        this.EquipR02.addBox(-10.0F, 0.0F, 0.0F, 10, 12, 5, 0.0F);
        this.setRotateAngle(EquipR02, 0.0F, -0.5235987755982988F, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 24, 53);
        this.ArmLeft01.mirror = true;
        this.ArmLeft01.setRotationPoint(8.5F, -10.0F, 0.0F);
        this.ArmLeft01.addBox(-2.5F, 0.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmLeft01, 0F, 0.0F, -0.15707963267948966F);
        this.LegLeft = new ModelRenderer(this, 0, 80);
        this.LegLeft.mirror = true;
        this.LegLeft.setRotationPoint(4.5F, 9.5F, -3.0F);
        this.LegLeft.addBox(-3.0F, 0.0F, -3.0F, 6, 19, 6, 0.0F);
        this.setRotateAngle(LegLeft, -0.2618F, 0.0F, 0.05235987755982988F);
        this.ShoesL = new ModelRenderer(this, 22, 70);
        this.ShoesL.mirror = true;
        this.ShoesL.setRotationPoint(0.0F, 19.0F, -0.2F);
        this.ShoesL.addBox(-3.5F, 0.0F, -3.5F, 7, 9, 7, 0.0F);
        this.EquipBaseM03 = new ModelRenderer(this, 128, 92);
        this.EquipBaseM03.setRotationPoint(0.0F, 0.0F, 9.0F);
        this.EquipBaseM03.addBox(-3.0F, -14.0F, 0.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(EquipBaseM03, -0.4363323129985824F, 0.0F, 0.0F);
        this.EquipLC2Base01 = new ModelRenderer(this, 128, 0);
        this.EquipLC2Base01.setRotationPoint(3.0F, -1.0F, -10.0F);
        this.EquipLC2Base01.addBox(-4.5F, 0.0F, -10.0F, 9, 10, 10, 0.0F);
        this.EquipLC201 = new ModelRenderer(this, 128, 117);
        this.EquipLC201.setRotationPoint(-2.0F, -4.0F, -8.0F);
        this.EquipLC201.addBox(-1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(EquipLC201, -0.13962634015954636F, 0.0F, 0.0F);
        this.EquipRC02 = new ModelRenderer(this, 132, 113);
        this.EquipRC02.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipRC02.addBox(-1.0F, -1.0F, -13.0F, 2, 2, 13, 0.0F);
        this.EquipRC203 = new ModelRenderer(this, 128, 117);
        this.EquipRC203.setRotationPoint(2.0F, -4.0F, -8.0F);
        this.EquipRC203.addBox(-1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(EquipRC203, -0.17453292519943295F, 0.0F, 0.0F);
        this.EquipR04_1 = new ModelRenderer(this, 128, 60);
        this.EquipR04_1.setRotationPoint(0.0F, 11.0F, -13.0F);
        this.EquipR04_1.addBox(-6.0F, 0.0F, -10.0F, 6, 7, 10, 0.0F);
        this.setRotateAngle(EquipR04_1, 0.0F, -0.17453292519943295F, 0.0F);
        this.ArmRight01 = new ModelRenderer(this, 24, 53);
        this.ArmRight01.setRotationPoint(-8.5F, -10.0F, 0.0F);
        this.ArmRight01.addBox(-2.5F, 0.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmRight01, 0F, 0.0F, 0.15707963267948966F);
        this.EquipBaseM02 = new ModelRenderer(this, 128, 0);
        this.EquipBaseM02.setRotationPoint(0.0F, -1.5F, 11.0F);
        this.EquipBaseM02.addBox(-3.5F, 0.0F, 0.0F, 7, 7, 7, 0.0F);
        this.EquipLC204 = new ModelRenderer(this, 132, 113);
        this.EquipLC204.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipLC204.addBox(-1.0F, -1.0F, -13.0F, 2, 2, 13, 0.0F);
        this.EquipLC01 = new ModelRenderer(this, 128, 117);
        this.EquipLC01.setRotationPoint(4.0F, -2.0F, -4.0F);
        this.EquipLC01.addBox(-1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(EquipLC01, 0.0F, -0.2617993877991494F, 0.0F);
        this.Ahoke = new ModelRenderer(this, 33, 87);
        this.Ahoke.setRotationPoint(-1.0F, -10.0F, -5.0F);
        this.Ahoke.addBox(0.0F, -3.0F, -10.0F, 0, 12, 12, 0.0F);
        this.setRotateAngle(Ahoke, 0.0F, 0.5235987755982988F, 0.0F);
        this.HeadEquip02 = new ModelRenderer(this, 92, 30);
        this.HeadEquip02.mirror = true;
        this.HeadEquip02.setRotationPoint(4.0F, 1.0F, 0.0F);
        this.HeadEquip02.addBox(0.0F, -1.0F, -1.0F, 10, 2, 2, 0.0F);
        this.setRotateAngle(HeadEquip02, 0.0F, 0.0F, -0.08726646259971647F);
        this.EquipR04 = new ModelRenderer(this, 128, 60);
        this.EquipR04.setRotationPoint(0.0F, 11.0F, -13.0F);
        this.EquipR04.addBox(0.0F, 0.0F, -10.0F, 6, 7, 10, 0.0F);
        this.setRotateAngle(EquipR04, 0.0F, 0.17453292519943295F, 0.0F);
        this.Butt = new ModelRenderer(this, 0, 17);
        this.Butt.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Butt.addBox(-8.0F, 4.0F, -5.5F, 16, 8, 7, 0.0F);
        this.setRotateAngle(Butt, 0.2617993877991494F, 0.0F, 0.0F);
        this.HairMidL01 = new ModelRenderer(this, 48, 34);
        this.HairMidL01.setRotationPoint(0.0F, 9.0F, 1.5F);
        this.HairMidL01.addBox(-7.5F, 0.0F, 0.0F, 15, 13, 9, 0.0F);
        this.setRotateAngle(HairMidL01, 0.3490658503988659F, 0.0F, 0.0F);
        this.EquipR03 = new ModelRenderer(this, 128, 26);
        this.EquipR03.setRotationPoint(-5.3F, 0.0F, 1.3F);
        this.EquipR03.addBox(-6.0F, 0.0F, -14.0F, 6, 18, 14, 0.0F);
        this.setRotateAngle(EquipR03, 0.0F, 0.6981317007977318F, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 0, 53);
        this.ArmRight02.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.ArmRight02.addBox(-2.5F, 0.0F, -2.5F, 5, 12, 5, 0.0F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 0, 53);
        this.ArmLeft02.mirror = true;
        this.ArmLeft02.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.ArmLeft02.addBox(-2.5F, 0.0F, -2.5F, 5, 12, 5, 0.0F);
        this.HeadEquip05 = new ModelRenderer(this, 128, 0);
        this.HeadEquip05.setRotationPoint(0.0F, -11.5F, -1.0F);
        this.HeadEquip05.addBox(-16.0F, 0.0F, 0.0F, 32, 1, 1, 0.0F);
        this.EquipLC202 = new ModelRenderer(this, 132, 113);
        this.EquipLC202.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipLC202.addBox(-1.0F, -1.0F, -13.0F, 2, 2, 13, 0.0F);
        this.HeadEquip = new ModelRenderer(this, 128, 0);
        this.HeadEquip.setRotationPoint(0.0F, -8.0F, -1.0F);
        this.HeadEquip.addBox(-9.5F, 0.0F, 0.0F, 19, 4, 11, 0.0F);
        this.setRotateAngle(HeadEquip, 0.10471975511965977F, 0.0F, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 105);
        this.BodyMain.setRotationPoint(0.0F, -14.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -10.0F, -4.0F, 13, 15, 7, 0.0F);
        this.EquipLC2Base02 = new ModelRenderer(this, 128, 0);
        this.EquipLC2Base02.setRotationPoint(0.0F, 0.0F, -1.0F);
        this.EquipLC2Base02.addBox(-5.0F, -5.0F, -10.0F, 10, 5, 14, 0.0F);
        this.setRotateAngle(EquipLC2Base02, 0.05235987755982988F, 0.0F, 0.0F);
        this.EquipLC203 = new ModelRenderer(this, 128, 117);
        this.EquipLC203.setRotationPoint(2.0F, -4.0F, -8.0F);
        this.EquipLC203.addBox(-1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(EquipLC203, -0.08726646259971647F, 0.0F, 0.0F);
        this.EquipLCRadar = new ModelRenderer(this, 128, 0);
        this.EquipLCRadar.setRotationPoint(5.2F, 0.0F, 5.5F);
        this.EquipLCRadar.addBox(0.0F, -7.5F, 0.0F, 1, 15, 5, 0.0F);
        this.EquipRC01 = new ModelRenderer(this, 128, 117);
        this.EquipRC01.setRotationPoint(-4.0F, -2.0F, -4.0F);
        this.EquipRC01.addBox(-1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(EquipRC01, 0.0F, 0.2617993877991494F, 0.0F);
        this.EquipL01 = new ModelRenderer(this, 128, 0);
        this.EquipL01.setRotationPoint(0.0F, -3.0F, 8.0F);
        this.EquipL01.addBox(0.0F, 0.0F, 0.0F, 14, 10, 5, 0.0F);
        this.setRotateAngle(EquipL01, -0.20943951023931953F, 0.0F, 0.0F);
        this.HeadEquip01 = new ModelRenderer(this, 128, 0);
        this.HeadEquip01.setRotationPoint(7.5F, 0.0F, 9.0F);
        this.HeadEquip01.addBox(0.0F, 0.0F, -2.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(HeadEquip01, 0.0F, -0.7853981633974483F, -0.17453292519943295F);
        this.EquipRC2Base02 = new ModelRenderer(this, 128, 0);
        this.EquipRC2Base02.setRotationPoint(0.0F, 0.0F, -1.0F);
        this.EquipRC2Base02.addBox(-5.0F, -5.0F, -10.0F, 10, 5, 14, 0.0F);
        this.setRotateAngle(EquipRC2Base02, 0.05235987755982988F, 0.0F, 0.0F);
        this.EquipLC03 = new ModelRenderer(this, 128, 117);
        this.EquipLC03.setRotationPoint(4.0F, 2.0F, -4.0F);
        this.EquipLC03.addBox(-1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(EquipLC03, 0.0F, -0.13962634015954636F, 0.0F);
        this.EquipBase = new ModelRenderer(this, 128, 0);
        this.EquipBase.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.EquipBase.addBox(-2.5F, 0.0F, 0.0F, 5, 4, 8, 0.0F);
        this.setRotateAngle(EquipBase, 0.17453292519943295F, 0.0F, 0.0F);
        this.EquipLCBase02 = new ModelRenderer(this, 128, 0);
        this.EquipLCBase02.setRotationPoint(7.0F, 0.0F, -6.5F);
        this.EquipLCBase02.addBox(0.0F, -5.0F, -4.0F, 5, 10, 14, 0.0F);
        this.setRotateAngle(EquipLCBase02, -0.17453292519943295F, 0.05235987755982988F, 0.0F);
        this.EquipRC202 = new ModelRenderer(this, 132, 113);
        this.EquipRC202.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipRC202.addBox(-1.0F, -1.0F, -13.0F, 2, 2, 13, 0.0F);
        this.EquipRCRadar = new ModelRenderer(this, 128, 0);
        this.EquipRCRadar.setRotationPoint(-5.2F, 0.0F, 5.5F);
        this.EquipRCRadar.addBox(-1.0F, -7.5F, 0.0F, 1, 15, 5, 0.0F);
        this.EquipRC201 = new ModelRenderer(this, 128, 117);
        this.EquipRC201.setRotationPoint(-2.0F, -4.0F, -8.0F);
        this.EquipRC201.addBox(-1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(EquipRC201, -0.08726646259971647F, 0.0F, 0.0F);
        this.EquipR01 = new ModelRenderer(this, 128, 0);
        this.EquipR01.setRotationPoint(0.0F, -3.0F, 8.0F);
        this.EquipR01.addBox(-14.0F, 0.0F, 0.0F, 14, 10, 5, 0.0F);
        this.setRotateAngle(EquipR01, -0.20943951023931953F, 0.0F, 0.0F);
        this.EquipLCBase01 = new ModelRenderer(this, 128, 0);
        this.EquipLCBase01.setRotationPoint(0.0F, 1.0F, -7.0F);
        this.EquipLCBase01.addBox(0.0F, -5.5F, -10.0F, 7, 11, 10, 0.0F);
        this.setRotateAngle(EquipLCBase01, 0.0F, 0.08726646259971647F, 0.0F);
        this.EquipLC02 = new ModelRenderer(this, 132, 113);
        this.EquipLC02.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipLC02.addBox(-1.0F, -1.0F, -13.0F, 2, 2, 13, 0.0F);
        this.EquipRCBase02 = new ModelRenderer(this, 128, 0);
        this.EquipRCBase02.setRotationPoint(-7.0F, 0.0F, -6.5F);
        this.EquipRCBase02.addBox(-5.0F, -5.0F, -4.0F, 5, 10, 14, 0.0F);
        this.setRotateAngle(EquipRCBase02, -0.17453292519943295F, -0.05235987755982988F, 0.0F);
        this.Butt.addChild(this.LegRight);
        this.BodyMain.addChild(this.Cloth);
        this.EquipL01.addChild(this.EquipL02);
        this.BodyMain.addChild(this.Neck);
        this.Head.addChild(this.Hair);
        this.EquipBase.addChild(this.EquipBaseM01);
        this.EquipRCBase02.addChild(this.EquipRC03);
        this.BodyMain.addChild(this.BoobR);
        this.LegRight.addChild(this.ShoesR);
        this.EquipL02.addChild(this.EquipL03);
        this.EquipR03.addChild(this.EquipRCBase01);
        this.EquipLC03.addChild(this.EquipLC04);
        this.Head.addChild(this.HairMain);
        this.BodyMain.addChild(this.BoobL);
        this.EquipR04_1.addChild(this.EquipRC2Base01);
        this.EquipRC203.addChild(this.EquipRC204);
        this.Butt.addChild(this.Skirt);
        this.Skirt.addChild(this.SkirtEquip);
        this.HeadEquip.addChild(this.HeadEquip03);
        this.EquipRC03.addChild(this.EquipRC04);
        this.HairMidL01.addChild(this.HairMidL02);
        this.HeadEquip03.addChild(this.HeadEquip04);
        this.EquipR01.addChild(this.EquipR02);
        this.BodyMain.addChild(this.ArmLeft01);
        this.Butt.addChild(this.LegLeft);
        this.LegLeft.addChild(this.ShoesL);
        this.EquipBase.addChild(this.EquipBaseM03);
        this.EquipR04.addChild(this.EquipLC2Base01);
        this.EquipLC2Base02.addChild(this.EquipLC201);
        this.EquipRC01.addChild(this.EquipRC02);
        this.EquipRC2Base02.addChild(this.EquipRC203);
        this.EquipR03.addChild(this.EquipR04_1);
        this.BodyMain.addChild(this.ArmRight01);
        this.EquipBase.addChild(this.EquipBaseM02);
        this.EquipLC203.addChild(this.EquipLC204);
        this.EquipLCBase02.addChild(this.EquipLC01);
        this.Hair.addChild(this.Ahoke);
        this.HeadEquip01.addChild(this.HeadEquip02);
        this.EquipL03.addChild(this.EquipR04);
        this.BodyMain.addChild(this.Butt);
        this.HairMain.addChild(this.HairMidL01);
        this.EquipR02.addChild(this.EquipR03);
        this.ArmRight01.addChild(this.ArmRight02);
        this.Neck.addChild(this.Head);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.Head.addChild(this.HeadEquip05);
        this.EquipLC201.addChild(this.EquipLC202);
        this.Head.addChild(this.HeadEquip);
        this.EquipLC2Base01.addChild(this.EquipLC2Base02);
        this.EquipLC2Base02.addChild(this.EquipLC203);
        this.EquipLCBase02.addChild(this.EquipLCRadar);   
        this.EquipRCBase02.addChild(this.EquipRC01);
        this.EquipBase.addChild(this.EquipL01);
        this.HeadEquip.addChild(this.HeadEquip01);
        this.EquipRC2Base01.addChild(this.EquipRC2Base02);
        this.EquipLCBase02.addChild(this.EquipLC03);
        this.SkirtEquip.addChild(this.EquipBase);
        this.EquipLCBase01.addChild(this.EquipLCBase02);
        this.EquipRC201.addChild(this.EquipRC202);
        this.EquipRCBase02.addChild(this.EquipRCRadar);
        this.EquipRC2Base02.addChild(this.EquipRC201);
        this.EquipBase.addChild(this.EquipR01);
        this.EquipL03.addChild(this.EquipLCBase01);
        this.EquipLC01.addChild(this.EquipLC02);
        this.EquipRCBase01.addChild(this.EquipRCBase02);
        
        //發光支架
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, -14.0F, 0.0F);
        this.GlowNeck = new ModelRenderer(this, 0, 0);
        this.GlowNeck.setRotationPoint(0.0F, -10.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -1.0F, 0.0F);
        
        this.GlowBodyMain.addChild(this.GlowNeck);
        this.GlowNeck.addChild(this.GlowHead);
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
        this.armMain = new ModelRenderer[] {this.BodyMain, this.ArmRight01, this.ArmRight02};
        this.armOff = new ModelRenderer[] {this.BodyMain, this.ArmLeft01, this.ArmLeft02};
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
    		scale = 2F;
        	offsetY = -0.73F;
		break;
    	case 2:
    		scale = 1.5F;
        	offsetY = -0.48F;
		break;
    	case 1:
    		scale = 1F;
        	offsetY = 0.02F;
		break;
    	default:
    		scale = 0.5F;
        	offsetY = 1.51F;
		break;
    	}
    	
    	GlStateManager.pushMatrix();
    	GlStateManager.enableBlend();
    	GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
    	GlStateManager.scale(scale, scale, scale);
    	GlStateManager.translate(0F, offsetY, 0F);
    	
    	//main body
    	this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
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
		
		boolean flag = !EmotionHelper.checkModelState(0, state);
		this.HeadEquip.isHidden = flag;
		this.HeadEquip05.isHidden = flag;
		
		flag = !EmotionHelper.checkModelState(1, state);
		this.EquipBase.isHidden = flag;
	}

	@Override
	public void syncRotationGlowPart()
	{
		this.GlowBodyMain.rotateAngleX = this.BodyMain.rotateAngleX;
		this.GlowBodyMain.rotateAngleY = this.BodyMain.rotateAngleY;
		this.GlowBodyMain.rotateAngleZ = this.BodyMain.rotateAngleZ;
		this.GlowNeck.rotateAngleX = this.Neck.rotateAngleX;
		this.GlowNeck.rotateAngleY = this.Neck.rotateAngleY;
		this.GlowNeck.rotateAngleZ = this.Neck.rotateAngleZ;
		this.GlowHead.rotateAngleX = this.Head.rotateAngleX;
		this.GlowHead.rotateAngleY = this.Head.rotateAngleY;
		this.GlowHead.rotateAngleZ = this.Head.rotateAngleZ;
	}

	@Override
	public void applyDeadPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
    	GlStateManager.translate(0F, 0.73F + 0.28F * ent.getScaleLevel(), 0F);
    	this.setFaceHungry(ent);
    	
    	//移動頭部使其看人
	  	this.Head.rotateAngleX = 0F;
	  	this.Head.rotateAngleY = 0F;
	    //胸部
  	    this.BoobL.rotateAngleX = -0.7854F;
  	    this.BoobR.rotateAngleX = -0.7854F;
	  	//Body
  	    this.Ahoke.rotateAngleY = 0.5236F;
	    //arm 
	  	this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmRight01.rotateAngleY = 0F;
    	//Body
	  	this.BodyMain.rotateAngleX = 1.48F;
	  	//hair
	  	this.HairMidL01.rotateAngleX = 0.2F;
	  	this.HairMidL02.rotateAngleX = -0.3F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = -2.97F;
	  	this.ArmLeft01.rotateAngleZ = 0.26F;
		this.ArmRight01.rotateAngleX = -2.8F;
		this.ArmRight01.rotateAngleZ = -1.3F;
		this.ArmRight02.rotateAngleZ = -0.9F;
		//leg
		this.LegLeft.rotateAngleX = -0.26F;
		this.LegRight.rotateAngleX = -0.26F;
		this.LegLeft.rotateAngleY = 0F;
		this.LegRight.rotateAngleY = 0F;
		this.LegLeft.rotateAngleZ = -0.14F;
		this.LegRight.rotateAngleZ = 0.14F;
		//equip
		this.EquipBase.isHidden = true;
	}

	@Override
	public void applyNormalPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
  		float angleX = MathHelper.cos(f2*0.08F);
  		float angleX1 = MathHelper.cos(f2*0.08F + 0.3F + f * 0.5F);
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1;
  		float addk1 = 0;
  		float addk2 = 0;
  		boolean showCannon = EmotionHelper.checkModelState(1, ent.getStateEmotion(ID.S.State));
  		
  		//水上漂浮
  		if (ent.getShipDepth(0) > 0D)
  		{
  			GlStateManager.translate(0F, angleX * 0.05F + 0.025F, 0F);
    	}
  		
  		//leg move parm
  		addk1 = angleAdd1 - 0.2118F;
	  	addk2 = angleAdd2 - 0.1118F;

  	    //移動頭部使其看人
	  	this.Head.rotateAngleX = f4 * 0.014F + 0.1F;
	  	this.Head.rotateAngleY = f3 * 0.01F;
	    
	    //正常站立動作
	    //胸部
  	    this.BoobL.rotateAngleX = angleX * 0.06F - 0.7854F;
  	    this.BoobR.rotateAngleX = angleX * 0.06F - 0.7854F;
	  	//Body
  	    this.Ahoke.rotateAngleY = angleX * 0.25F + 0.5236F;
	  	this.BodyMain.rotateAngleX = -0.1F;
	  	//hair
	  	this.HairMidL01.rotateAngleX = angleX * 0.06F + 0.2F;
	  	this.HairMidL02.rotateAngleX = -angleX1 * 0.09F - 0.17F;
	  	this.HairMidL01.rotateAngleZ = 0F;
	  	this.HairMidL02.rotateAngleZ = 0F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = angleAdd2 * 0.6F + 0.15F;
	  	this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmLeft01.rotateAngleZ = angleX * 0.1F - 0.26F;
	    this.ArmRight01.rotateAngleX = angleAdd1 * 0.6F;
	    this.ArmRight01.rotateAngleY = 0F;
		this.ArmRight01.rotateAngleZ = -angleX * 0.1F + 0.26F;
		this.ArmRight02.rotateAngleX = 0F;
		this.ArmRight02.rotateAngleY = 0F;
		this.ArmRight02.rotateAngleZ = 0F;
		//leg
		this.LegLeft.rotateAngleY = 0F;
		this.LegLeft.rotateAngleZ = 0.05F;
		this.LegRight.rotateAngleY = 0F;
		this.LegRight.rotateAngleZ = -0.05F;
		//cannon
		if (showCannon)
		{
			this.EquipBase.rotateAngleX = 0.17F;
			
			if (this.Head.rotateAngleX <= 0F)
			{
				this.EquipLC201.rotateAngleX = this.Head.rotateAngleX * 0.9F;
				this.EquipLC203.rotateAngleX = this.Head.rotateAngleX * 1.2F;
				this.EquipRC201.rotateAngleX = this.Head.rotateAngleX * 1.1F;
				this.EquipRC203.rotateAngleX = this.Head.rotateAngleX * 0.85F;
			}
			
			this.EquipLCBase02.rotateAngleX = this.Head.rotateAngleX;
			this.EquipLC2Base01.rotateAngleX = 0F;
			this.EquipLC2Base02.rotateAngleY = this.Head.rotateAngleY;
			this.EquipLC01.rotateAngleY = angleX * 0.1F - 0.26F;
			this.EquipLC03.rotateAngleY = -angleX * 0.08F - 0.15F;

			
			this.EquipRCBase02.rotateAngleX = this.Head.rotateAngleX;
			this.EquipRC2Base01.rotateAngleX = 0F;
			this.EquipRC2Base02.rotateAngleY = this.Head.rotateAngleY;
			this.EquipRC01.rotateAngleY = angleX * 0.08F + 0.2F;
			this.EquipRC03.rotateAngleY = -angleX * 0.1F + 0.1F;
		}


	    if (ent.getIsSprinting() || f1 > 0.9F)
	    {	//奔跑動作
	    	//Body
	    	this.Head.rotateAngleX -= 0.35F;
		  	this.BodyMain.rotateAngleX = 0.5236F;
		  	//hair
		  	this.HairMidL01.rotateAngleX += 0.3F;
		  	this.HairMidL02.rotateAngleX += 0.3F;
		    //arm 
		  	this.ArmLeft01.rotateAngleX = angleAdd2 * 1.4F - 0.1F;
		    this.ArmRight01.rotateAngleX = angleAdd1 * 1.4F - 0.1F;
		    this.ArmLeft01.rotateAngleZ = angleX * 0.1F - 0.4F;
			this.ArmRight01.rotateAngleZ = -angleX * 0.1F + 0.4F;
			//leg
			addk1 -= 0.55F;
			addk2 -= 0.55F;
			this.LegLeft.rotateAngleY = 0F;
			this.LegRight.rotateAngleY = 0F;
			this.LegLeft.rotateAngleZ = 0F;
			this.LegRight.rotateAngleZ = 0F;
			//cannon
			if (showCannon)
			{
				this.EquipLCBase02.rotateAngleX -= 0.45F;
//				this.EquipLC201.rotateAngleX -= 0.5F;
//				this.EquipLC203.rotateAngleX -= 0.55F;
				
				this.EquipRCBase02.rotateAngleX -= 0.5F;
//				this.EquipRC201.rotateAngleX -= 0.6F;
//				this.EquipRC203.rotateAngleX -= 0.5F;
			}
  		}

	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
	    
	    if (ent.getIsSneaking())
	    {	//潛行, 蹲下動作
	    	//Body
	    	this.Head.rotateAngleX -= 0.35F;
		  	this.BodyMain.rotateAngleX = 0.5236F;
		    //arm 
		    this.ArmLeft01.rotateAngleZ = angleX * 0.1F - 0.4F;
			this.ArmRight01.rotateAngleZ = -angleX * 0.1F + 0.4F;
			//leg
			addk1 -= 0.55F;
			addk2 -= 0.55F;
			this.LegLeft.rotateAngleY = 0F;
			this.LegRight.rotateAngleY = 0F;
			this.LegLeft.rotateAngleZ = 0F;
			this.LegRight.rotateAngleZ = 0F;
			//cannon
			if (showCannon)
			{
				this.EquipLCBase02.rotateAngleX -= 0.45F;
//				this.EquipLC201.rotateAngleX -= 0.5F;
//				this.EquipLC203.rotateAngleX -= 0.55F;
				
				this.EquipRCBase02.rotateAngleX -= 0.5F;
//				this.EquipRC201.rotateAngleX -= 0.6F;
//				this.EquipRC203.rotateAngleX -= 0.5F;
			}
  		}//end if sneaking
  		
	    if (ent.getIsSitting() || ent.getIsRiding())
	    {  //騎乘動作
	    	if (showCannon)
	    	{
	    		GlStateManager.translate(0F, 0.42F, 0F);
		    	//Body
			  	this.BodyMain.rotateAngleX = -0.09F;
			    //arm 
			  	this.ArmLeft01.rotateAngleX = 0.52F;
			  	this.ArmLeft01.rotateAngleZ = -1.04F;
				this.ArmRight01.rotateAngleX = 0.52F;
				this.ArmRight01.rotateAngleZ = 1.04F;
				//leg
				addk1 = -1.4F;
				addk2 = -1.4F;
				this.LegLeft.rotateAngleY = -0.14F;
				this.LegRight.rotateAngleY = 0.14F;
				this.LegLeft.rotateAngleZ = 0F;
				this.LegRight.rotateAngleZ = 0F;
				//cannon
				this.EquipLCBase02.rotateAngleX = 1.57F;
				this.EquipLC2Base01.rotateAngleX = 0.8F;
				this.EquipLC01.rotateAngleY = 0F;
				this.EquipLC03.rotateAngleY = 0F;
				this.EquipLC201.rotateAngleX = 0F;
				this.EquipLC203.rotateAngleX = 0F;
				
				this.EquipRCBase02.rotateAngleX = 1.57F;
				this.EquipRC2Base01.rotateAngleX = 0.8F;
				this.EquipRC01.rotateAngleY = 0F;
				this.EquipRC03.rotateAngleY = 0F;
				this.EquipRC201.rotateAngleX = 0F;
				this.EquipRC203.rotateAngleX = 0F;
			}
	    	else if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
	    	{
	    		GlStateManager.translate(0F, 0.71F, 0F);
	    		this.setFaceHungry(ent);
		    	//Body
			  	this.BodyMain.rotateAngleX = 1.48F;
			  	//hair
			  	this.HairMidL01.rotateAngleX = 0.2F;
			  	this.HairMidL02.rotateAngleX = -0.3F;
			    //arm 
			  	this.ArmLeft01.rotateAngleX = -2.97F;
			  	this.ArmLeft01.rotateAngleZ = 0.26F;
				this.ArmRight01.rotateAngleX = -2.8F;
				this.ArmRight01.rotateAngleZ = -1.3F;
				this.ArmRight02.rotateAngleZ = -0.9F;
				//leg
				addk1 = -0.26F;
				addk2 = -0.26F;
				this.LegLeft.rotateAngleY = 0F;
				this.LegRight.rotateAngleY = 0F;
				this.LegLeft.rotateAngleZ = -0.14F;
				this.LegRight.rotateAngleZ = 0.14F;
	    	}
	    	else
	    	{
	    		GlStateManager.translate(0F, 0.52F, 0F);
		    	//Body
			  	this.BodyMain.rotateAngleX = -0.09F;
			    //arm 
			  	this.ArmLeft01.rotateAngleX = -0.63F;
			  	this.ArmLeft01.rotateAngleZ = 0.14F;
				this.ArmRight01.rotateAngleX = -0.63F;
				this.ArmRight01.rotateAngleZ = -0.14F;
				//leg
				addk1 = -1.75F;
				addk2 = -1.75F;
				this.LegLeft.rotateAngleY = -0.14F;
				this.LegRight.rotateAngleY = 0.14F;
				this.LegLeft.rotateAngleZ = 0F;
				this.LegRight.rotateAngleZ = 0F;
	    	}
  		}//end if sitting
	    
	    //攻擊動作    
	    if (ent.getAttackTick() > 20)
	    {
	    	switch (ent.getStateEmotion(ID.S.Phase))
	    	{
	    	case 0:		//heavy atk phase 0
	    	case 2:		//heavy atk phase 2
	    		GlStateManager.translate(0F, scale * 0.35F + 0F, 0F);
	    	    //Body
	    	    this.Head.rotateAngleX -= 1.22F;
	    	  	this.BodyMain.rotateAngleX = 1.75F;
	    	  	//hair
	    	  	this.HairMidL01.rotateAngleX += 0.3F;
	    	  	this.HairMidL02.rotateAngleX += 0.6F;
	    	    //arm 
	    	  	this.ArmLeft01.rotateAngleX = -1.75F;
	    	  	this.ArmLeft01.rotateAngleY = 0F;
	    	  	this.ArmLeft01.rotateAngleZ = 0F;
	    		this.ArmRight01.rotateAngleX = -1.05F;
	    		this.ArmRight01.rotateAngleY = 2.62F;
	    		this.ArmRight01.rotateAngleZ = 0.7F;
	    		this.ArmRight02.rotateAngleZ = -0.79F;
	    		//leg
	    		addk1 = -1.75F;
	    		addk2 = -2.27F;
	    		this.LegLeft.rotateAngleY = -0.44F;
	    		this.LegRight.rotateAngleY = 0.44F;
	    		this.LegLeft.rotateAngleZ = 0F;
	    		this.LegRight.rotateAngleZ = 0F;
	    		//equip
	    		this.EquipBase.rotateAngleX = -1.22F;	
	    		this.EquipLCBase02.rotateAngleX -= 0.5F;
//	    		this.EquipLC201.rotateAngleX -= 0.5F;
//	    		this.EquipLC203.rotateAngleX -= 0.5F;   		
	    		this.EquipRCBase02.rotateAngleX -= 0.5F;
//	    		this.EquipRC201.rotateAngleX -= 0.5F;
//	    		this.EquipRC203.rotateAngleX -= 0.5F;
	    		break;
	    	default:	//cannon or heavy atk phase 1,3
//	    		setFace(3);
	    		//Body
			  	this.BodyMain.rotateAngleX = -0.17F;
			    //arm 
			  	this.ArmLeft01.rotateAngleX = -1.57F;
			  	this.ArmLeft01.rotateAngleY = -0.26F;
			  	this.ArmLeft01.rotateAngleZ = 0F;
				this.ArmRight01.rotateAngleX = 0F;
				this.ArmRight01.rotateAngleZ = 0.87F;
				this.ArmRight02.rotateAngleZ = -1.57F;
				//leg
				addk1 += 0.2618F;
				addk2 += 0.2618F;
				this.LegLeft.rotateAngleY = 0F;
				this.LegRight.rotateAngleY = 0F;
				this.LegLeft.rotateAngleZ = -0.17F;
				this.LegRight.rotateAngleZ = 0.17F;
	    		break;
	    	}
	    }
	    
	    //swing arm
	  	float f6 = ent.getSwingTime(f2 - (int)f2);
	  	if (f6 != 0F)
	  	{
	  		float f7 = MathHelper.sin(f6 * f6 * (float)Math.PI);
	        float f8 = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI);
	        this.ArmRight01.rotateAngleX = -0.4F;
	        this.ArmRight01.rotateAngleY = 0F;
	        this.ArmRight01.rotateAngleZ = -0.2F;
	        this.ArmRight01.rotateAngleX += -f8 * 80.0F * Values.N.DIV_PI_180;
	        this.ArmRight01.rotateAngleY += -f7 * 20.0F * Values.N.DIV_PI_180 + 0.2F;
	        this.ArmRight01.rotateAngleZ += -f8 * 20.0F * Values.N.DIV_PI_180;
	        this.ArmRight02.rotateAngleX = 0F;
	        this.ArmRight02.rotateAngleY = 0F;
	        this.ArmRight02.rotateAngleZ = 0F;
	  	}
	  	
	  	//鬢毛調整
	    float headX = this.Head.rotateAngleX * -0.5F;
	    float headZ = this.Head.rotateAngleZ * -0.5F;
	    this.HairMidL01.rotateAngleX += headX;
	    this.HairMidL01.rotateAngleZ += headZ;
	    this.HairMidL02.rotateAngleX += headX * 0.5F;
	    this.HairMidL02.rotateAngleZ += headZ * 0.5F;

	    
	    //leg motion
	    this.LegLeft.rotateAngleX = addk1;
	    this.LegRight.rotateAngleX = addk2;
	}
    
    
}