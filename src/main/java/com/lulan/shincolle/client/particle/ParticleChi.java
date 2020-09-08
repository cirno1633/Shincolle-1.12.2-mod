package com.lulan.shincolle.client.particle;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.CalcHelper;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;


/**CHI PARTICLE
 * 氣彈環繞特效, entity必須為IShipEmotion
 */
@SideOnly(Side.CLIENT)
public class ParticleChi extends Particle
{

	private Entity host;
	private int particleType;
	private float radChi;
	
	
    public ParticleChi(World world, Entity host, float scale, int type)
    {
        super(world, host.posX, host.posY+host.height*0.55D, host.posZ);
        this.setSize(0F, 0F);
        this.host = host;
        this.motionX = 0D;
        this.motionZ = 0D;
        this.motionY = 0D;
        this.particleScale = scale;
        this.particleType = type;
        this.canCollide = false;	//can clip = false
        
        switch (type)
        {
        case 1:		//nagato
        	this.particleRed = 1F;
        	this.particleGreen = 1F;
        	this.particleBlue = 1F;
        	this.particleAlpha = 1F;
        	this.particleMaxAge = 40;
        	this.radChi = scale * 12F;
        break;
        }
    }

    @Override
    public void renderParticle(BufferBuilder render, Entity entity, float ptick, float cosYaw, float cosPitch, float sinYaw, float sinYawsinPitch, float cosYawsinPitch)
    {
    	if (this.particleAge <= 1) return;
    	
    	GlStateManager.pushMatrix();
    	GlStateManager.depthMask(true);
    	GlStateManager.enableBlend();
    	GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
    	GlStateManager.disableLighting();
    	GlStateManager.disableTexture2D();	//NO texture
		
		//particle是以玩家視野來render, 因此座標要扣掉interpPos轉換為玩家視野座標
    	float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)ptick - interpPosX);
    	float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)ptick - interpPosY);
    	float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)ptick - interpPosZ);
      
        //start
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
        render.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        
        //注意4個點形成的面只有正面會貼上貼圖, 若玩家在該面背面會看不到正面貼圖, 因此要畫兩面共8個點
        //要使玩家看到正面, 4個座標add順序必須為: 右下 -> 右上 -> 左上 -> 左下
        //chi的形狀為八面體, 總共4個菱形6個頂點
        //若該面起點為y較低的點, 面會呈現內凹, 若起點選較高的點, 該面會外凸 (若四頂點不再同一平面上時)
        //face1
        render.pos(f11, f12, f13+particleScale).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(f11, f12+particleScale, f13).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(f11+particleScale, f12, f13).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(f11, f12-particleScale, f13).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        //face2
        render.pos(f11+particleScale, f12, f13).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(f11, f12+particleScale, f13).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(f11, f12, f13-particleScale).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(f11, f12-particleScale, f13).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        //face3
        render.pos(f11, f12, f13-particleScale).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(f11, f12+particleScale, f13).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(f11-particleScale, f12, f13).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(f11, f12-particleScale, f13).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        //face4
        render.pos(f11-particleScale, f12, f13).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(f11, f12+particleScale, f13).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(f11, f12, f13+particleScale).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(f11, f12-particleScale, f13).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        
        //draw
        Tessellator.getInstance().draw();
        
        //半透明外殼
        float parAlpha2 = this.particleAlpha * 0.5F;
        render.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        
        //face1
        render.pos(f11, f12, f13+particleScale*1.3).color(this.particleRed, this.particleGreen, this.particleBlue, parAlpha2).endVertex();
        render.pos(f11, f12+particleScale*1.3, f13).color(this.particleRed, this.particleGreen, this.particleBlue, parAlpha2).endVertex();
        render.pos(f11+particleScale*1.3, f12, f13).color(this.particleRed, this.particleGreen, this.particleBlue, parAlpha2).endVertex();
        render.pos(f11, f12-particleScale*1.3, f13).color(this.particleRed, this.particleGreen, this.particleBlue, parAlpha2).endVertex();
        //face2
        render.pos(f11+particleScale*1.3, f12, f13).color(this.particleRed, this.particleGreen, this.particleBlue, parAlpha2).endVertex();
        render.pos(f11, f12+particleScale*1.3, f13).color(this.particleRed, this.particleGreen, this.particleBlue, parAlpha2).endVertex();
        render.pos(f11, f12, f13-particleScale*1.3).color(this.particleRed, this.particleGreen, this.particleBlue, parAlpha2).endVertex();
        render.pos(f11, f12-particleScale*1.3, f13).color(this.particleRed, this.particleGreen, this.particleBlue, parAlpha2).endVertex();
        //face3
        render.pos(f11, f12, f13-particleScale*1.3).color(this.particleRed, this.particleGreen, this.particleBlue, parAlpha2).endVertex();
        render.pos(f11, f12+particleScale*1.3, f13).color(this.particleRed, this.particleGreen, this.particleBlue, parAlpha2).endVertex();
        render.pos(f11-particleScale*1.3, f12, f13).color(this.particleRed, this.particleGreen, this.particleBlue, parAlpha2).endVertex();
        render.pos(f11, f12-particleScale*1.3, f13).color(this.particleRed, this.particleGreen, this.particleBlue, parAlpha2).endVertex();
        //face4
        render.pos(f11-particleScale*1.3, f12, f13).color(this.particleRed, this.particleGreen, this.particleBlue, parAlpha2).endVertex();
        render.pos(f11, f12+particleScale*1.3, f13).color(this.particleRed, this.particleGreen, this.particleBlue, parAlpha2).endVertex();
        render.pos(f11, f12, f13+particleScale*1.3).color(this.particleRed, this.particleGreen, this.particleBlue, parAlpha2).endVertex();
        render.pos(f11, f12-particleScale*1.3, f13).color(this.particleRed, this.particleGreen, this.particleBlue, parAlpha2).endVertex();
        
        //draw
        Tessellator.getInstance().draw();
        
    	GlStateManager.enableLighting();
    	GlStateManager.disableBlend();
    	GlStateManager.depthMask(false);
    	GlStateManager.enableTexture2D();
    	GlStateManager.popMatrix();
    }
    
    //layer: 0:particle 1:terrain 2:items 3:custom?
    @Override
    public int getFXLayer()
    {
        return 3;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
	public void onUpdate()
    {
		this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        
        float[] newPos = CalcHelper.rotateXZByAxis(radChi, 0F, 6.283185F / this.particleMaxAge * this.particleAge, 1F);

        if (this.host != null)
        {
        	this.posX = this.host.posX + newPos[0];
            this.posZ = this.host.posZ + newPos[1];
        }
        
        int phase = ((IShipEmotion)host).getStateEmotion(ID.S.Phase);
        
        if (this.particleAge++ > this.particleMaxAge)
        {
            this.setExpired();
        }
        else if (phase == 0 || phase == 2)
       {
        	this.setExpired();
        }
        
    }
    
    
}