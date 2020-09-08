package com.lulan.shincolle.utility;

import com.google.common.base.Predicate;
import com.lulan.shincolle.ai.path.ShipMoveHelper;
import com.lulan.shincolle.ai.path.ShipPath;
import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.ai.path.ShipPathPoint;
import com.lulan.shincolle.capability.CapaInventory;
import com.lulan.shincolle.capability.CapaShipInventory;
import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.crafting.ShipCalc;
import com.lulan.shincolle.entity.*;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.Enums;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.reference.unitclass.Attrs;
import com.lulan.shincolle.server.CacheDataPlayer;
import com.lulan.shincolle.server.CacheDataShip;
import com.lulan.shincolle.tileentity.ITileWaypoint;
import com.lulan.shincolle.tileentity.TileEntityCrane;
import com.lulan.shincolle.tileentity.TileEntityWaypoint;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.*;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/** helper about entity
 * 
 */
public class EntityHelper
{
	
	private static Random rand = new Random();

	
	public EntityHelper() {}

	/**check entity is in (stand on, y+0D) liquid (not air or solid block) */
	public static boolean checkEntityIsInLiquid(Entity entity)
	{
		IBlockState block = entity.world.getBlockState(new BlockPos(MathHelper.floor(entity.posX), (int)(entity.getEntityBoundingBox().minY), MathHelper.floor(entity.posZ)));
		return BlockHelper.checkBlockIsLiquid(block);
	}
	
	/**check entity is free to move (stand in, y+0.5D) the block */
	public static boolean checkEntityIsFree(Entity entity)
	{
		return BlockHelper.checkBlockSafe(entity.world, MathHelper.floor(entity.posX), (int)(entity.getEntityBoundingBox().minY + 0.5D), MathHelper.floor(entity.posZ));
	}
	
	/**check entity is air or underwater mob, return 0:default 1:air 2:water */
	public static int checkEntityMovingType(Entity entity)
	{
		if (entity instanceof IShipAttackBase)
		{
			switch (((IShipAttackBase) entity).getDamageType())
			{
			case ID.ShipDmgType.AIRPLANE:
				return 1;
			case ID.ShipDmgType.SUBMARINE:
				return 2;
			default:	//default type
				return 0;
			}
		}
		else if (entity instanceof EntityWaterMob || entity instanceof EntityGuardian)
		{
			return 2;
		}
		else if (entity instanceof EntityBlaze || entity instanceof EntityWither ||
				 entity instanceof EntityDragon || entity instanceof EntityBat ||
				 entity instanceof EntityFlying)
		{
			return 1;
		}
		
		return 0;
	}
	
	/**replace isInWater, check water block with NO extend AABB */
	public static void checkDepth(IShipFloating host)
	{
		Entity host2 = (Entity) host;
		World w = host2.world;
		int px = MathHelper.floor(host2.posX);
		int py = MathHelper.floor(host2.getEntityBoundingBox().minY);
		int pz = MathHelper.floor(host2.posZ);
		BlockPos pos = new BlockPos(px, py, pz);
		IBlockState state = w.getBlockState(pos);
		double depth = 0;
		
		if (BlockHelper.checkBlockIsLiquid(state))
		{
			depth = 1;

			for (int i = 1; py + i < 255D; i++)
			{
				pos = new BlockPos(px, py + i, pz);
				state = w.getBlockState(pos);
				
				//若為液體類方塊, 則深度+1
				if (BlockHelper.checkBlockIsLiquid(state))
				{
					depth++;
				}
				//若碰到非液體方塊, 判定可否上浮
				else
				{
					//最上面碰到空氣類方塊才可以上浮, 否則不上浮
					if (state.getMaterial() == Material.AIR)
					{
						host.setStateFlag(ID.F.CanFloatUp, true);
					}
					else
					{
						host.setStateFlag(ID.F.CanFloatUp, false);
					}
					break;
				}
			}
			
			depth = depth - (host2.posY - (int)host2.posY);
		}
		else
		{
			depth = 0;	
			host.setStateFlag(ID.F.CanFloatUp, false);
		}
		
		host.setShipDepth(depth);
	}
	
	/** check ship is out of combat */
	public static boolean checkShipOutOfCombat(BasicEntityShip ship)
	{
		if (ship != null && ship.ticksExisted - ship.getCombatTick() > 128)
		{
			return true;
		}
		
		return false;
	}
	
	/** check ship is in colled list */
	public static boolean checkShipColled(int classID, CapaTeitoku capa)
	{
		if (capa != null && capa.getColleShipList() != null && capa.getColleShipList().contains(classID))
		{
			return true;
		}
		
		return false;
	}
	
	/** check ship is in colled list */
	public static boolean checkEquipColled(int equipID, CapaTeitoku capa)
	{
		if (capa != null && capa.getColleEquipList() != null && capa.getColleEquipList().contains(equipID))
		{
			return true;
		}
		
		return false;
	}
	
	/** check player is OP or creative mode */
	public static boolean checkOP(EntityPlayer player)
	{
		if (player != null)
		{
			if (player.capabilities.isCreativeMode) return true;
			
			//server side
			if (!player.world.isRemote)
			{
				return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().canSendCommands(player.getGameProfile());
			}
			//is single player
			else if (!CommonProxy.isMultiplayer)
			{
				return true;
			}
			//is multiplayer and client side, check permission level >= 2 (4 = OP, 2 = allow command block)
			else
			{
				return player.canUseCommandBlock();
			}
		}
		
		return false;
	}
	
	/** get player name */
	public static String getOwnerName(BasicEntityShip ship)
	{
		if (ship == null) return "";
		
		String name = "";
		
		//1. get name from ship
		if (ship.ownerName != null && ship.ownerName.length() > 0)
		{
			name = ship.ownerName;
		}
		else
		{
			//2. get name from playerUID -> server cache
			EntityPlayer player = EntityHelper.getEntityPlayerByUID(ship.getPlayerUID());
			
			if (player != null)
			{
				name = player.getName();
			}
			else
			{
				//3. get name from getOwner()
				Entity ent = ship.getOwner();
				
				if (ent instanceof EntityPlayer)
				{
					name = ((EntityPlayer) ent).getName();
				}
			}
		}

		return name;
	}
	
	/** get (loaded) entity by entity ID */
	public static Entity getEntityByID(int entityID, int worldID, boolean isClient)
	{
		World world;
		
		if (isClient)
		{
			world = ClientProxy.getClientWorld();
		}
		else
		{
			world = ServerProxy.getServerWorld(worldID);
		}
		
		if (world != null && entityID > 0)
		{
			return world.getEntityByID(entityID);
		}
			
		return null;
	}
	
	/** get ship entity by ship UID, server side only */
	public static BasicEntityShip getShipByUID(int sid)
	{
		if (sid > 0)
		{
			CacheDataShip data = ServerProxy.getShipWorldData(sid);
			
			if (data != null)
			{
				Entity getEnt = getEntityByID(data.entityID, data.worldID, false);
				
				if (getEnt instanceof BasicEntityShip)
				{
					return (BasicEntityShip) getEnt;
				}
			}
		}
		else
		{
			return null;
		}
		
		return null;
	}
	
	/** get (online) player by entity ID */
	public static EntityPlayer getEntityPlayerByID(int entityID, int worldID, boolean isClient)
	{
		World world;
		
		if (isClient)
		{
			world = ClientProxy.getClientWorld();
		}
		else
		{
			world = ServerProxy.getServerWorld(worldID);
		}
		
		if (world != null && entityID > 0)
		{
			for (EntityPlayer p : world.playerEntities)
			{
				if (p != null && p.getEntityId() == entityID)
				{
					return p;
				}
			}
		}
		
		return null;
	}
	
	/** get (online) player by player name, SERVER SIDE ONLY */
	public static EntityPlayer getEntityPlayerByName(String name)
	{
		if (name != null)
		{
			//get all worlds
			World[] worlds = ServerProxy.getServerWorld();
			
			try
			{
				//check world list
				for (World w : worlds)
				{
					//check entity list
					for (EntityPlayer p : w.playerEntities)
					{
						//check player name
						if (p != null && p.getDisplayNameString().equals(name))
						{
							return p;
						}
					}
				}
			}
			catch (Exception e)
			{
				LogHelper.info("EXCEPTION: get EntityPlayer by name fail: "+e);
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	/** get cached player entity by player UID, no world id check, SERVER SIDE ONLY */
	public static EntityPlayer getEntityPlayerByUIDAtClient(int uid)
	{
		if (uid > 0)
		{
			//get player entity
			try
			{
				//get client worlds
				World w = ClientProxy.getClientWorld();
				
				//check player entity list
				for (EntityPlayer p : w.playerEntities)
				{
					CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(p);
					if (capa.getPlayerUID() == uid) return p;
				}
			}
			catch (Exception e)
			{
				LogHelper.info("EXCEPTION: get EntityPlayer by UID at client fail: "+e);
				e.printStackTrace();
				return null;
			}
		}
		
		return null;
	}
	
	/** get cached player entity by player UID, no world id check, SERVER SIDE ONLY */
	public static EntityPlayer getEntityPlayerByUID(int uid)
	{
		if (uid > 0)
		{
			//get player entity
			try
			{
				//get all worlds
				World[] worlds = ServerProxy.getServerWorld();
				
				//get player data
				int peid = getPlayerEID(uid);
				
				//check all world
				for (World w : worlds)
				{
					//check player entity list
					for (EntityPlayer p : w.playerEntities)
					{
						if (p != null && p.getEntityId() == peid)
						{
							return p;
						}
					}
				}
			}
			catch (Exception e)
			{
				LogHelper.info("EXCEPTION: get EntityPlayer by name fail: "+e);
				e.printStackTrace();
				return null;
			}
		}
		
		return null;
	}
	
	/** get cached player entity id by player UID, SERVER SIDE ONLY */
	public static int getPlayerEID(int uid)
	{
		if (uid > 0)
		{
			//從server proxy抓出player uid cache
			CacheDataPlayer pdata = ServerProxy.getPlayerWorldData(uid);
			
			//get data
			if (pdata != null)
			{
				return pdata.entityID;
			}
		}
		
		return -1;
	}
	
	/** get player UID by entity */
	public static int getPlayerUID(Entity ent)
	{
		//player entity
		if(ent instanceof EntityPlayer)
		{
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapability((EntityPlayer) ent);
			if (capa != null) return capa.getPlayerUID();
		}
		
		//shincolle entity
		if (ent instanceof IShipOwner)
		{
			return ((IShipOwner) ent).getPlayerUID();
		}
		
		//tameable entity
		if (ent instanceof IEntityOwnable)
		{
			Entity owner = ((IEntityOwnable) ent).getOwner();
			
			//get player UID
			if (owner instanceof EntityPlayer)
			{
				CapaTeitoku capa = CapaTeitoku.getTeitokuCapability((EntityPlayer) owner);
				if (capa != null) return capa.getPlayerUID();
			}
		}
		
		return -1;
	}
	
	/** get player is opening a GUI, SERVER SIDE ONLY */
	public static ArrayList<EntityPlayer> getEntityPlayerUsingGUI()
	{
		ArrayList<EntityPlayer> plist = new ArrayList();
	
		for (World w : ServerProxy.getServerWorld())
		{
			if (w != null)
			{
				for (EntityPlayer p : w.playerEntities)
				{
					CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(p);
					
					if (capa != null && capa.isOpeningGUI())
					{
						plist.add(p);
					}
				}
			}
		}
		
		return plist;
	}
	
	/** get player uuid */
	public static String getPetPlayerUUID(EntityTameable pet)
	{
		if (pet != null)
		{
			if (pet.getOwnerId() != null)
			{
			    return pet.getOwnerId().toString();
			}
			else
			{
				return "00000000-0000-0000-0000-000000000000"; //dummy uuid on special condition
			}
		}
		
		return null;
	}
	
	/** set player UID for pet, SERVER SIDE ONLY */
	public static void setPetPlayerUID(EntityPlayer player, IShipOwner pet)
	{
		setPetPlayerUID(getPlayerUID(player), pet);
	}
	
	/** set player UID for pet */
	public static void setPetPlayerUID(int pid, IShipOwner pet)
	{
		if (pet != null && pid > 0)
		{
			pet.setPlayerUID(pid);
		}
	}
	
	/** set owner uuid for pet by player UID and pet entity, SERVER SIDE ONLY */
	public static void setPetPlayerUUID(int pid, EntityTameable pet)
	{
		if (pet != null)
		{
			EntityPlayer owner = getEntityPlayerByUID(pid);
			setPetPlayerUUID(owner, pet);
		}
	}
	
	/** set owner uuid for pet by player entity and pet entity */
	public static void setPetPlayerUUID(EntityPlayer player, EntityTameable pet)
	{
		if (player != null)
		{
			setPetPlayerUUID(player.getUniqueID(), pet);
		}
	}
	
	/** set owner uuid for pet by player uuid and pet entity */
	public static void setPetPlayerUUID(UUID uuid, EntityTameable pet)
	{
		if (pet != null)
		{
			pet.setOwnerId(uuid);
		}
	}
	
	/** add player ship list data */
	public static void addPlayerColledShip(int classID, EntityPlayer player)
	{
		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
		
		if (capa != null)
		{
			capa.setColleShip(classID);
		}
	}
	
	/** add player ship list data */
	public static void addPlayerColledEquip(int equipID, EntityPlayer player)
	{
		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
		
		if (capa != null)
		{
			capa.setColleEquip(equipID);
		}
	}
	
	/** update ship path navigator */
	public static void updateShipNavigator(IShipAttackBase entity)
	{
		//null check
		if (entity == null) return;
		
		EntityLiving entity2 = (EntityLiving) entity;
		ShipPathNavigate pathNavi = entity.getShipNavigate();
		ShipMoveHelper moveHelper = entity.getShipMoveHelper();
		
		//若有ship path, 則更新ship navigator
        if (pathNavi != null && moveHelper != null && !pathNavi.noPath())
        {
        	//若同時有官方ai的路徑, 則清除官方ai路徑
        	if (!entity2.getNavigator().noPath())
        	{
        		entity2.getNavigator().clearPath();
        	}

        	//若坐下或綁住, 則清除路徑
        	if (entity.getIsSitting() || entity.getIsLeashed())
        	{
        		entity.getShipNavigate().clearPath();
        	}
        	else
        	{
        		//用particle顯示path point
    			if (ConfigHandler.debugMode && entity2.ticksExisted % 16 == 0)
    			{
    				sendPathParticlePacket(entity.getShipNavigate().getPath(), new TargetPoint(entity2.dimension, entity2.posX, entity2.posY, entity2.posZ, 48D));
    			}
        	}

        	//update movement
        	pathNavi.onUpdateNavigation();
	        moveHelper.onUpdateMoveHelper();
	        
	        //apply movement
	        entity2.travel(entity2.moveStrafing, 0f, entity2.moveForward);
		}//end ship path

        //若有vanilla path, 則用特效顯示出path
        if (!entity2.getNavigator().noPath())
        {
        	//若entity處於水中, 則消去vanilla path
        	if (checkEntityIsInLiquid(entity2))
        	{
        		entity2.getNavigator().clearPath();
        	}
        	
			//用particle顯示path point
        	if (ConfigHandler.debugMode && entity2.ticksExisted % 16 == 0)
        	{
        		sendPathParticlePacket(entity2.getNavigator().getPath(), new TargetPoint(entity2.dimension, entity2.posX, entity2.posY, entity2.posZ, 64D));
			}
        }//end vanilla path
	}
	
	//send  path indicator particle for vanilla path or ship path
	private static <T> void sendPathParticlePacket(T path, TargetPoint target)
	{
		int parType;
		int len = 0;
		int[] points = null;
		
		if (path instanceof ShipPath)
		{
			ShipPath p = (ShipPath) path;
			ShipPathPoint temp;
			
			parType = 0;
			len = p.getCurrentPathLength();
			points = new int[len * 3 + 1];
			
			//add current path index (target point)
			points[0] = p.getCurrentPathIndex();
			
			//add path points
			for (int i = 0; i < len; i++)
			{
				temp = p.getPathPointFromIndex(i);
				points[i * 3 + 1] = temp.xCoord;
				points[i * 3 + 2] = temp.yCoord;
				points[i * 3 + 3] = temp.zCoord;
			}
		}
		else if (path instanceof Path)
		{
			Path p = (Path) path;
			PathPoint temp;
			
			parType = 1;
			len = p.getCurrentPathLength();
			points = new int[len * 3 + 1];
			
			//add current path index (target point)
			points[0] = p.getCurrentPathIndex();
			
			//add path points
			for (int i = 0; i < len; i++)
			{
				temp = p.getPathPointFromIndex(i);
				points[i * 3 + 1] = temp.x;
				points[i * 3 + 2] = temp.y;
				points[i * 3 + 3] = temp.z;
			}
		}
		else
		{
			return;
		}
		
		if (points != null)
		{
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(parType, points), target);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static RayTraceResult getPlayerMouseOverEntity(double dist, float duringTicks, List<Entity> exlist, boolean ignoreInvulnerable, boolean ignoreInvisible)
	{
		return getMouseOverEntity(ClientProxy.getMineraft().getRenderViewEntity(), dist, duringTicks, exlist, ignoreInvulnerable, ignoreInvisible);
	}
	
	@SideOnly(Side.CLIENT)
	public static RayTraceResult getPlayerMouseOverEntity(double dist, float duringTicks, List<Entity> exlist)
	{
		return getMouseOverEntity(ClientProxy.getMineraft().getRenderViewEntity(), dist, duringTicks, exlist, true, true);
	}
	
	@SideOnly(Side.CLIENT)
	public static RayTraceResult getPlayerMouseOverEntity(double dist, float duringTicks)
	{
		return getMouseOverEntity(ClientProxy.getMineraft().getRenderViewEntity(), dist, duringTicks, null, true, true);
	}

	/**
	 * ray trace for entity: 
	 * 1. get the farest block
	 * 2. create collision box (rectangle, diagonal vertex = player and farest block)
	 * 3. get entity in this box
	 * 4. check entity on player~block sight line
	 * 5. return nearest entity
	 * 
	 * 修改自: EntityRenderer.getMouseOver
	 */
	@SideOnly(Side.CLIENT)
	public static RayTraceResult getMouseOverEntity(Entity viewer, double dist, float parTick, @Nullable List<Entity> exlist, boolean ignoreInvulnerable, boolean ignoreInvisible)
	{
		RayTraceResult lookBlock = null;
		
        if (viewer != null && viewer.world != null)
        {
            Vec3d vec3 = null;
            
            //change viewer if on ship's mounts
            if (viewer.getRidingEntity() instanceof BasicEntityMount && !ClientProxy.isViewPlayer)
            {
            	Entity ship = ((BasicEntityMount)viewer.getRidingEntity()).getHostEntity();
            	if (ship != null) viewer = ship;
            }
            
            //test partial tick TODO
            parTick = ClientProxy.getMineraft().getRenderPartialTicks();
            
            lookBlock = BlockHelper.getMouseOverBlock(viewer, dist, parTick, false, true, true);
            vec3 = viewer.getPositionEyes(parTick);
            
            //null check
            if (vec3 == null) return null;

            //若有抓到方塊, 則d1改為抓到方塊的距離
            double d1 = dist;
            if (lookBlock != null)
            {
                d1 = lookBlock.hitVec.distanceTo(vec3);
            }

            //計算entity視線的方向向量 * 距離
            Vec3d vec31 = viewer.getLook(parTick);
            double vec3x = vec31.x * dist;
            double vec3y = vec31.y * dist;
            double vec3z = vec31.z * dist;
            Vec3d vec32 = vec3.add(vec3x, vec3y, vec3z);
            Vec3d vec33 = null;
            RayTraceResult lookEntity = null;
            Entity pointedEntity = null;
            
            //從玩家到目標方塊之間, 做出擴展1格的方形collision box, 抓出其中碰到的entity
            List<Entity> list = viewer.world.getEntitiesWithinAABBExcludingEntity(viewer, viewer.getEntityBoundingBox().grow(vec3x, vec3y, vec3z).expand(1D, 1D, 1D));
            double d2 = d1;

            //檢查抓到的entity, 是否在玩家~目標方塊的視線上
            for (Entity entity : list)
            {
            	//check target in exlist
            	if (exlist != null && exlist.size() > 0)
            	{
            		boolean exequal = false;
            		
            		for (Entity ex : exlist)
            		{
            			if (entity != null && entity.equals(ex))
            			{
            				exequal = true;
            				break;
            			}
            		}
            		
            		if (exequal) continue;
            	}
            	
            	//check invulnerable entity
            	if (ignoreInvulnerable && TargetHelper.isEntityInvulnerable(entity)) continue;
            	
            	//check invisible
            	if (ignoreInvisible && entity.isInvisible()) continue;
            	
                if (entity.canBeCollidedWith())
                {
                	//檢查entity大小是否在視線上碰撞到
                    double f2 = entity.getCollisionBorderSize();
                    AxisAlignedBB targetBox = entity.getEntityBoundingBox().expand(f2, f2, f2);
                    RayTraceResult getObj = targetBox.calculateIntercept(vec3, vec32);

                    //若viewer完全塞在目標的box裡面
                    if (targetBox.contains(vec3))
                    {
                        if (d2 >= 0D)
                        {
                        	pointedEntity = entity;
                        	//抓到位置玩家位置或者目標box位置
                            vec33 = (getObj == null ? vec3 : getObj.hitVec);
                            //抓到距離改為0
                            d2 = 0D;
                        }
                    }
                    //其他有抓到目標的情況
                    else if (getObj != null)
                    {
                        double d3 = vec3.distanceTo(getObj.hitVec);	//抓到距離

                        //若抓到距離在dist之內, 則判定為抓到目標
                        if (d3 < d2 || d2 == 0D)
                        {
                        	//若抓到的是玩家自己的座騎, 且屬於不能互動的座騎
                            if (entity.getLowestRidingEntity() == viewer.getLowestRidingEntity() && !entity.canRiderInteract())
                            {
                                //若dist設為0D, 才會抓到自己的座騎, 否則都無視座騎
                            	if (d2 == 0D)
                            	{
                                    pointedEntity = entity;
                                    vec33 = getObj.hitVec;
                                }
                            }
                            //其他非座騎entity
                            else
                            {
                                pointedEntity = entity;
                                vec33 = getObj.hitVec;
                                d2 = d3;
                            }
                        }
                    }
                }
            }

            //若有抓到entity, 且抓到距離在視線碰到的最遠方塊之內, 才算有抓到entity
            if (pointedEntity != null && (d2 < d1 || lookBlock == null))
            {
            	lookBlock = new RayTraceResult(pointedEntity, vec33);
            }
        }
        
        return lookBlock;
    }
	
	/** get mouseover target
	 * 
	 *  client/server both side
	 *  calc the look vector by eye height and pitch, less accuracy
	 *  
	 *  par1: check liquid block
	 *  par2: ignore collide check
	 *  par3: always return the last target hit
	 *  
	 *  修改自Item.rayTrace
	 */
	public static RayTraceResult getMouseoverTarget(World world, EntityPlayer player, double dist, boolean onLiquid, boolean ignoreNoAABB, boolean alwaysLastHit)
	{
		float f = 1.0F;
        float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double)f;
        double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double)f + (double)(world.isRemote ? player.getEyeHeight() - player.getDefaultEyeHeight() : player.getEyeHeight()); // isRemote check to revert changes to ray trace position due to adding the eye height clientside and player yOffset differences
        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)f;
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        
        Vec3d vec3 = new Vec3d(d0, d1, d2);
        Vec3d vec31 = vec3.add((double)f7 * dist, (double)f6 * dist, (double)f8 * dist);
        
        return world.rayTraceBlocks(vec3, vec31, onLiquid, ignoreNoAABB, alwaysLastHit);
	}
	
	/** set emotes to all nearby hostile ships */
	public static void applyShipEmotesAOEHostile(World world, double x, double y, double z, double range, int emotesType)
	{
		//server side only
		if (!world.isRemote)
		{
			//get ship entity
            AxisAlignedBB box = new AxisAlignedBB(x-range, y-range, z-range, x+range, y+range, z+range);
            List<BasicEntityShipHostile> slist = world.getEntitiesWithinAABB(BasicEntityShipHostile.class, box);
            
            if (slist != null)
            {
                for (BasicEntityShipHostile s : slist)
                {
                	if (s.isEntityAlive()) s.applyEmotesReaction(emotesType);
                }
            }
		}
	}
	
	/** set emotes to all nearby ships
	 * 
	 *  emotes type:
	 *  0: caress head (owner)
  	 *  1: caress head (other)
  	 *  2: damaged
  	 *  3: attack
  	 *  4: idle
  	 *  5: command
  	 *  6: shock
	 */
	public static void applyShipEmotesAOE(World world, double x, double y, double z, double range, int emotesType)
	{
		//server side only
		if (!world.isRemote)
		{
			//get ship entity
            AxisAlignedBB box = new AxisAlignedBB(x-range, y-range, z-range, x+range, y+range, z+range);
            List<BasicEntityShip> slist = world.getEntitiesWithinAABB(BasicEntityShip.class, box);
            
            if (slist != null)
            {
                for (BasicEntityShip s : slist)
                {
                	if (s.isEntityAlive()) s.applyEmotesReaction(emotesType);
                }
            }
		}
	}
	
	/** set emotes to all nearby ships with owner checking
	 * 
	 *  emotes type:
	 *  0: caress head (owner)
  	 *  1: caress head (other)
  	 *  2: damaged
  	 *  3: attack
  	 *  4: idle
  	 *  5: command
  	 *  6: shock
	 */
	public static void applyShipEmotesAOECheckOwner(World world, double x, double y, double z, double range, int emotesType, int ownerUID) {
		//server side only
		if (!world.isRemote)
		{
			//get ship entity
            AxisAlignedBB box = new AxisAlignedBB(x-range, y-range, z-range, x+range, y+range, z+range);
            List<BasicEntityShip> slist = world.getEntitiesWithinAABB(BasicEntityShip.class, box);
            
            if (slist != null)
            {
                for (BasicEntityShip s : slist)
                {
                	if (s.isEntityAlive() && s.getPlayerUID() == ownerUID)
                	{
                		s.applyEmotesReaction(emotesType);
                	}
                }
            }
		}
	}
	
	/** apply emotes to all entity in list */
	public static void applyEmotesAOE(List entlist, int emotes)
	{
		if (entlist != null && !entlist.isEmpty())
		{
			Entity s = (Entity) entlist.get(0);
			TargetPoint point = new TargetPoint(s.dimension, s.posX, s.posY, s.posZ, 48D);
			
			for (Object o : entlist)
			{
				if (s instanceof Entity)
				{
					CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle((Entity) o, 36, ((Entity) o).height * 0.6F, 0, emotes), point);
				}
			}
		}
	}
	
	/** apply waypoint moving
  	 * 
  	 *  1. if guard position = waypoint block, get next waypoint
  	 *  2. if next waypoint = ship's last waypoint, get block's last waypoint (backwards mode)
  	 *  3. if no next/last waypoint, stop
  	 */
	public static boolean updateWaypointMove(IShipGuardian entity)
	{
		boolean updatePos = false;
		
  		//in guard block mode
  		if (!entity.getStateFlag(ID.F.CanFollow) && entity.getGuardedPos(1) > 0 && !entity.getIsSitting() && !entity.getIsLeashed() && !entity.getIsRiding())
  		{
  			//calc distance
  			float dx = (float) (entity.getGuardedPos(0) + 0.5D - ((Entity)entity).posX);
  			float dy = (float) (entity.getGuardedPos(1) - ((Entity)entity).posY);
			float dz = (float) (entity.getGuardedPos(2) + 0.5D - ((Entity)entity).posZ);
			dx = dx * dx;
			dy = dy * dy;
			dz = dz * dz;
  			double distsq = dx + dy + dz;
  			
  			//get target block
  			BlockPos pos = new BlockPos(entity.getGuardedPos(0), entity.getGuardedPos(1), entity.getGuardedPos(2));
	  		TileEntity tile = ((Entity) entity).world.getTileEntity(pos);
	  		
  			//is waypoint block
  			if (tile instanceof TileEntityCrane)
  			{
  				//ship is ready for craning (xz < 2 blocks, y < 5 blocks)
  				if (distsq < 25D)
				{
  					if (entity.getStateMinor(ID.M.CraneState) == 0)
  					{
  						entity.setStateMinor(ID.M.CraneState, 1);
  						
  						//若騎乘者為BasicEntityShip, 則解除騎乘並開始裝卸
  						if (!((Entity)entity).getPassengers().isEmpty())
  						{
  							Entity rider = ((Entity)entity).getPassengers().get(0);
  							
  							if (rider instanceof BasicEntityShip)
  							{
  								if (entity instanceof BasicEntityMount)
  								{
  									((BasicEntityShip) rider).setStateMinor(ID.M.CraneState, 1);
  								}
  								
  								rider.dismountRidingEntity();
  							}
  						}
  					}
				}
  				else
  				{
  					if (entity.getStateMinor(ID.M.NumGrudge) > 0)
  					{
  						//go to the crane below
  	  	  				entity.getShipNavigate().tryMoveToXYZ(entity.getGuardedPos(0) + 0.5D, entity.getGuardedPos(1) - 2D, entity.getGuardedPos(2) + 0.5D, 1D);
  					}
  				}
  			}
  			else
  			{
  				//cancel craning
  				entity.setStateMinor(ID.M.CraneState, 0);
  				
  				//cancel rider craning
				if (!((Entity)entity).getPassengers().isEmpty())
				{
					Entity rider = ((Entity)entity).getPassengers().get(0);
					
					if (rider instanceof BasicEntityShip)
					{
						((BasicEntityShip) rider).setStateMinor(ID.M.CraneState, 0);
					}
				}
  			}
  			
  			//is waypoint block
  			if (tile instanceof TileEntityWaypoint)
  			{
  				//if is in formation mode, only update moving by flag ship (formatPos = 0)
  				if (entity.getStateMinor(ID.M.FormatType) > 0 &&
  					entity.getStateMinor(ID.M.FormatPos) > 0) return false;
  				
  	  			if (distsq < 9D)
  	  			{
	  	  			try
  	  				{
  	  					updatePos = applyNextWaypoint((TileEntityWaypoint) tile, entity, true, 16);
	  	  				
	  	  				//set follow dist
	  	  				if (updatePos && entity.getStateMinor(ID.M.NumGrudge) > 0)
	  	  				{
	  	  					entity.setStateMinor(ID.M.FollowMin, 2);
	  	  					entity.getShipNavigate().tryMoveToXYZ(entity.getGuardedPos(0) + 0.5D, entity.getGuardedPos(1), entity.getGuardedPos(2) + 0.5D, 1D);
	  	  					
	  	  					//if ship is flag ship, apply same moving to other ships
		  	  				if (entity.getStateMinor(ID.M.FormatType) > 0 &&
		  	    				entity.getStateMinor(ID.M.FormatPos) == 0)
		  	  				{
		  	  					if (entity instanceof BasicEntityMount)
		  	  					{
		  	  						BasicEntityShip host = (BasicEntityShip) ((BasicEntityMount) entity).getHostEntity();
		  	  						if (host != null) applyMovingToShipTeam((BasicEntityShip) host, host.getGuardedPos(0) + 0.5D, host.getGuardedPos(1), host.getGuardedPos(2) + 0.5D);
		  	  					}
		  	  					else
		  	  					{
		  	  						applyMovingToShipTeam((BasicEntityShip) entity, entity.getGuardedPos(0) + 0.5D, entity.getGuardedPos(1), entity.getGuardedPos(2) + 0.5D);
		  	  					}
		  	  				}
	  	  				}
	  	  				
	  	  				return updatePos;
  	  				}
  	  				catch (Exception e)
  	  				{
  	  					e.printStackTrace();
  	  				}
  	  			}
  	  			else
  	  			{
  	  				//move back to waypoint position every 128 ticks
  	  				if ((entity.getTickExisted() & 127) == 0 && entity.getStateMinor(ID.M.NumGrudge) > 0)
  	  				{
  	  					entity.getShipNavigate().tryMoveToXYZ(entity.getGuardedPos(0) + 0.5D, entity.getGuardedPos(1), entity.getGuardedPos(2) + 0.5D, 1D);
  	  				}
  	  			}
  			}
  		}//end in guard mode
  		
  		return updatePos;
  	}
	
	public static void applyMovingToShipTeam(BasicEntityShip ship, double gx, double gy, double gz)
	{
		//get player data
		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(ship.getPlayerUID());
		if (capa == null) return;
		
		//get ship team id and flag ship
		int[] teamslot = capa.checkIsInFormation(ship.getShipUID());
		if (teamslot == null || teamslot[0] < 0 || teamslot[1] != 0) return;
		
		//get all ship in team
		ArrayList<BasicEntityShip> ships = capa.getShipEntityAllList(teamslot[0]);
		if (ships == null || ships.size() <= 0) return;
		
		//apply moving
		FormationHelper.applyFormationMoving(ships, ship.getStateMinor(ID.M.FormatType), MathHelper.floor(gx), (int)gy, MathHelper.floor(gz), false);
	
		for (BasicEntityShip s : ships)
		{
			if (s != null && s.isEntityAlive() && s.getStateMinor(ID.M.NumGrudge) > 0)
			{
				s.getShipNavigate().tryMoveToXYZ(s.getGuardedPos(0) + 0.5D, s.getGuardedPos(1), s.getGuardedPos(2) + 0.5D, 1D);
			}
		}
	}
	
	/** get all ship entity in the team */
	public static BasicEntityShip[] getAllShipInTheTeam(CapaTeitoku capa, int tid)
	{
		if (capa != null)
		{
			return capa.getShipEntityAll(tid);
		}
		
		return null;
	}
	
	/** set next waypoint by checking last and next waypoint, return true if changed */
	public static boolean applyNextWaypoint(ITileWaypoint tile, IShipGuardian entity, boolean checkWpStay, int checkDelay)
	{
		boolean changed = false;
		
		boolean timeout = !checkWpStay;
		BlockPos next = tile.getNextWaypoint();
		BlockPos last = tile.getLastWaypoint();
		BlockPos shiplast = entity.getLastWaypoint();
		
		//check waypoint stay time
		int wpstay = entity.getWpStayTime();
		
		//stay until timeout
		if (checkWpStay)
		{
			//check wp tile stay time and ship wp stay time, get the longer one
			int staytimemax = Math.max(entity.getWpStayTimeMax(), tile.getWpStayTime());
			
			if(wpstay < staytimemax)
			{
				entity.setWpStayTime(wpstay + checkDelay);
			}
			else
			{
				timeout = true;
			}
		}
		
		//timeout, go to next wp
		if (timeout)
		{
			entity.setWpStayTime(0);
			
			//if next == last, inverse moving
			if (next.getY() > 0 && next.getX() == shiplast.getX() && next.getY() == shiplast.getY() && next.getZ() == shiplast.getZ())
			{
				//if no last waypoint, go to next waypoint
				if (last.getY() <= 0)
				{
					//go to next waypoint
					if (next.getY() > 0)
					{
						setGuardedPos((Entity) entity, next.getX(), next.getY(), next.getZ());
						changed = true;
					}
				}
				//get last waypoint, go to last waypoint
				else
				{
					//go to last waypoint (backwards mode)
					setGuardedPos((Entity) entity, last.getX(), last.getY(), last.getZ());
					changed = true;
				}
			}
			//go to next waypoint
			else if (next.getY() > 0)
			{
				setGuardedPos((Entity) entity, next.getX(), next.getY(), next.getZ());
				changed = true;
			}
			
			//set last waypoint
			entity.setLastWaypoint(((TileEntity)tile).getPos());
			
			if (!((Entity)entity).getPassengers().isEmpty())
			{
				Entity rider = ((Entity)entity).getPassengers().get(0);
				
				if (rider instanceof BasicEntityShip)
				{
					((BasicEntityShip) rider).setLastWaypoint(((TileEntity)tile).getPos());
				}
			}
		}
		
		return changed;
	}
	
	private static void setGuardedPos(Entity host, int x, int y, int z)
	{
		((IShipGuardian) host).setGuardedPos(x, y, z, host.dimension, 1);
		
		if (!host.getPassengers().isEmpty())
		{
			Entity rider = host.getPassengers().get(0);
			
			if (rider instanceof BasicEntityShip)
			{
				((BasicEntityShip) rider).setGuardedPos(x, y, z, host.dimension, 1);
			}
		}
	}
	
	/** count entity number in the world
	 *  type: 0:boss ship, 1:mob ship, 2:all hostile ship
	 */
	public static int getEntityNumber(int type, World world)
	{
		int count = 0;
		
		if (world != null)
		{
			for (Entity ent: world.loadedEntityList)
			{
				switch(type)
				{
				case 1:   //boss ship 
					if (!ent.isNonBoss())
					{
						count++;
					}
					break;
				case 2:   //all hostile ship
					if (ent instanceof BasicEntityShipHostile)
					{
						count++;
					}
					break;
				default:  //small or large mob ship
					if (ent instanceof BasicEntityShipHostile && ent.isNonBoss())
					{
						count++;
					}
					break;
				}
			}
		}//end world not null
		
		return count;
	}
	
	//clear seat2
  	public static void clearMountSeat(EntityLiving host)
  	{
  		//清空座騎, 若騎乘ship mount, 則順便清空ship mount
  		if (host.getRidingEntity() != null)
  		{
  			if (host.getRidingEntity() instanceof BasicEntityMount)
  			{
	  			((BasicEntityMount) host.getRidingEntity()).clearRider();
  			}
  			
  			host.dismountRidingEntity();
  		}
  		
  		//清空乘客
  		for (Entity p : host.getPassengers())
  		{
  			p.dismountRidingEntity();
  		}
  	}
  	
  	/** get entity with specific class/interface
  	 */
  	public static <T> ArrayList getEntitiesWithinAABB(World world, Class <? extends T > cls, AxisAlignedBB aabb, @Nullable Predicate filter)
    {
  		//get all entity within AABB
  		ArrayList<Entity> list = (ArrayList<Entity>) world.getEntitiesWithinAABB(Entity.class, aabb);
  		ArrayList<T> list2 = new ArrayList<T>();
  		
  		//check entity class
        for (Entity entity : list)
        {
            if (cls.isAssignableFrom(entity.getClass()) && filter.apply((T) entity))
            {
            	list2.add((T) entity);
            }
        }

        return list2;
    }
  	
  	/**
  	 * custom entity moving method
  	 * 1. floating on water/lava/liquid block
  	 * 2. change movSpeed in water
  	 */
  	public static <T extends EntityLivingBase & IShipNavigator> void moveEntityWithHeading(T host, float strafe, float vertical, float forward)
	{
        double d0;
        
        //飛行狀態
        if (host.canFly())
        {
            d0 = host.posY;
            host.moveRelative(strafe, vertical, forward, host.getMoveSpeed() * 0.4F); //水中的速度計算(含漂移效果)
            host.move(MoverType.SELF, host.motionX, host.motionY, host.motionZ);
            
            //空中阻力
            host.motionX *= 0.91D;
            host.motionY *= 0.91D;
            host.motionZ *= 0.91D;
            
            //水中撞到東西會上升
            if (host.collidedHorizontally &&
            	host.isOffsetPositionInLiquid(host.motionX, host.motionY + 0.6D - host.posY + d0, host.motionZ))
            {
                host.motionY = 0.3D;
            }
        }
        //液體中移動
        else if (EntityHelper.checkEntityIsInLiquid(host))
        {
            d0 = host.posY;
            host.moveRelative(strafe, vertical, forward, host.getMoveSpeed() * 0.4F); //水中的速度計算(含漂移效果)
            host.move(MoverType.SELF, host.motionX, host.motionY, host.motionZ);
            
            //水中阻力
            host.motionX *= 0.8D;
            host.motionY *= 0.8D;
            host.motionZ *= 0.8D;
            
            //水中撞到東西會上升
            if (host.collidedHorizontally &&
            	host.isOffsetPositionInLiquid(host.motionX, host.motionY + 0.6D - host.posY + d0, host.motionZ))
            {
                host.motionY = 0.3D;
            }
        }
        //非液體中移動
        else
        {
            float f6 = 0.91F;
            BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain(host.posX, host.getEntityBoundingBox().minY - 1D, host.posZ);

            //取得地面磨擦係數
            if (host.onGround)
            {
                f6 = host.world.getBlockState(blockpos$pooledmutableblockpos).getBlock().slipperiness * 0.91F;
            }

            //計算地面磨擦力效果
            float f7 = 0.16277136F / (f6 * f6 * f6);
            float f8;

            //move speed on ground
            if (host.onGround)
            {
                f8 = host.getMoveSpeed() * f7;
            }
            //move speed in air
            else
            {
                f8 = host.jumpMovementFactor;
            }
            
            //計算跳躍Y速度值
            if (host.isJumping())
            {
        		host.motionY += host.getMoveSpeed() * host.getJumpSpeed() * 0.1D;
            }

            //計算實際XZ速度值
            host.moveRelative(strafe, vertical, forward, f8);
            
            //再次判定entity是否還站在地面, 重取地面摩擦係數
            f6 = 0.91F;

            if (host.onGround)
            {
                f6 = host.world.getBlockState(blockpos$pooledmutableblockpos.setPos(host.posX, host.getEntityBoundingBox().minY - 1D, host.posZ)).getBlock().slipperiness * 0.91F;
            }

            //判定是否在樓梯移動狀態
            if (host.isOnLadder())
            {
                float f9 = 0.15F;
                host.motionX = MathHelper.clamp(host.motionX, (double)(-f9), (double)f9);
                host.motionZ = MathHelper.clamp(host.motionZ, (double)(-f9), (double)f9);
                host.fallDistance = 0F;

                if (host.motionY < -0.15D)
                {
                    host.motionY = -0.15D;
                }
            }

            //實際移動entity
            host.move(MoverType.SELF, host.motionX, host.motionY, host.motionZ);

            //若移動方向為衝撞樓梯, 則給予上升值
            if (host.collidedHorizontally && host.isOnLadder())
            {
                host.motionY = 0.4D;
            }

            //漂浮藥水效果 or 獲得飛行能力時, 新增往上漂浮移動值
            if (host.isPotionActive(MobEffects.LEVITATION))
            {
                host.motionY += (0.05D * (double)(host.getActivePotionEffect(MobEffects.LEVITATION).getAmplifier() + 1) - host.motionY) * 0.2D;
            }
            //無漂浮效果, 計算自然掉落
            else
            {
                blockpos$pooledmutableblockpos.setPos(host.posX, 0.0D, host.posZ);

                if (!host.world.isRemote || host.world.isBlockLoaded(blockpos$pooledmutableblockpos) && host.world.getChunk(blockpos$pooledmutableblockpos).isLoaded())
                {
                    if (!host.hasNoGravity())
                    {
                    	host.motionY -= 0.08D;
                    }
                }
                else if (host.posY > 0.0D)
                {
                	host.motionY = -0.1D;
                }
                else
                {
                	host.motionY = 0.0D;
                }
            }

            //計算三方向阻力效果
            host.motionY *= 0.98D;
            host.motionX *= f6;
            host.motionZ *= f6;
            blockpos$pooledmutableblockpos.release();
        }
        
        //計算四肢擺動值
        host.prevLimbSwingAmount = host.limbSwingAmount;
        double d5 = host.posX - host.prevPosX;
        double d7 = host.posZ - host.prevPosZ;
        float f10 = MathHelper.sqrt(d5 * d5 + d7 * d7) * 4F;

        if (f10 > 1F)
        {
            f10 = 1F;
        }

        host.limbSwingAmount += (f10 - host.limbSwingAmount) * 0.4F;
        host.limbSwing += host.limbSwingAmount;
    }
  	
  	/**
  	 * check passenger is specific class
  	 * parms: mount, target class, id
  	 * id: -1:none, 0~N: check only at Nth passenger
  	 */
  	public static boolean checkPassengerClass(Entity mount, Class cls)
  	{
  		return checkPassengerClass(mount, cls, -1);
  	}
  	
  	public static boolean checkPassengerClass(Entity mount, Class cls, int id)
  	{
  		if (mount != null && cls != null)
  		{
  			List<Entity> list = mount.getPassengers();
  			
  			if (list.size() > 0 && list.size() > id)
  			{
  				//no id checking
  				if (id < 0)
  				{
  	  				for (int i = 0; i < list.size(); i++)
  	  				{
  	  					if (cls.isAssignableFrom(list.get(i).getClass())) return true;
  	  				}
  				}
  				//apply id checking
  				else
  				{
  					if (cls.isAssignableFrom(list.get(id).getClass())) return true;
  				}
  			}//end list not empty
  		}//end null check
  		
  		return false;
  	}
  	
  	/**
  	 * get selected or offhand PointerItem
  	 */
  	public static ItemStack getPointerInUse(EntityPlayer player)
  	{
  		ItemStack pointer = ItemStack.EMPTY;
  		
  		if (player != null)
  		{
  			ItemStack itemMain = player.inventory.getCurrentItem();
  			NonNullList<ItemStack> itemOff = player.inventory.offHandInventory;

  			//check main hand
  			if (!itemMain.isEmpty() && itemMain.getItem() == ModItems.PointerItem)
  			{
  				pointer = itemMain;
  			}
  			//check off hand
  			else
  			{
  	  			for (ItemStack i : itemOff)
  	  			{
  	  	  			if (!i.isEmpty() && i.getItem() == ModItems.PointerItem)
  	  	  			{
  	  	  				pointer = i;
  	  	  				break;
  	  	  			}
  	  			}
  			}
  		}
  		
  		return pointer;
  	}
  	
  	/**
  	 * change team id of pointer item in key event, CLIENT SIDE ONLY
  	 */
  	public static void handlePointerKeyInput()
  	{
  		EntityPlayer player = ClientProxy.getClientPlayer();
		GameSettings keySet = ClientProxy.getGameSetting();
		ItemStack pointer = EntityHelper.getPointerInUse(player);
		
		if (!pointer.isEmpty())
		{
			int meta = pointer.getMetadata();
			int getKey = -1;
			int orgCurrentItem = player.inventory.currentItem;
			
			//若按住ctrl (sprint key)
			if (keySet.keyBindSprint.isKeyDown())	//注意持續偵測類按鍵必須使用isKeyDown
			{
				//若按住hotbar 1~9, 則切換隊伍, 但是避免數字按鍵將hotbar位置改變 (固定current item)
				for (int i = 0; i < keySet.keyBindsHotbar.length; i++)
				{
					if (keySet.keyBindsHotbar[i].isPressed())
					{
						getKey = i;
						break;
					}
				}
				
				LogHelper.debug("DEBUG: key input: pointer set team: "+getKey+" currItem: "+orgCurrentItem);
				//send key input packet
				if (getKey >= 0)
				{
					//change team id
					CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetShipTeamID, getKey, orgCurrentItem));
				}
			}
			//change pointer mode to caress head mode (meta + 3)
			//current item must be PointerItem (NO OFFHAND!)
			else if (!player.inventory.getCurrentItem().isEmpty() &&
					 player.inventory.getCurrentItem().getItem() == ModItems.PointerItem &&
					 keySet.keyBindPlayerList.isPressed())
			{
				//switch caress head mode
				switch (meta)
				{
				case 1:
				case 2:
					meta += 3;
					break;
				case 3:
				case 4:
				case 5:
					meta -= 3;
					break;
				default:
					meta = 3;
					break;
				}
				
				player.inventory.getCurrentItem().setItemDamage(meta);
				
				//send sync packet to server
				CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SyncPlayerItem, meta));
			}
		}
  	}
  	
    //check entity is not host or launcher
    public static boolean isNotHost(IShipOwner host, Entity target)
    {
    	//null check
    	if (host == null || target == null) return false;
    	
    	//not self
    	if (target.equals(host)) return false;
    	
		if (host.getHostEntity() != null)
		{
			Entity host2 = host.getHostEntity();
			
			//not launcher
			if (target.equals(host2)) return false;
			
			//not host's mounts
			if (target.equals(host2.getRidingEntity())) return false;
			
			//not riders
			for (Entity rider : host2.getPassengers())
			{
				if (target.equals(rider)) return false;
			}
		}
		
		return true;
	}
    
  	/** morale level
  	 *  0:excited, 1:happy, 2:normal, 3:tired, 4:exhausted
  	 */
  	public static int getMoraleLevel(int m)
  	{
  		if (m > ID.Morale.L_Excited)
  		{  //excited
			return ID.Morale.Excited;
		}
		else if (m > ID.Morale.L_Happy)
		{
			return ID.Morale.Happy;
		}
		else if (m > ID.Morale.L_Normal)
		{
			return ID.Morale.Normal;
		}
		else if (m > ID.Morale.L_Tired)
		{
			return ID.Morale.Tired;
		}
		else
		{
			return ID.Morale.Exhausted;
		}
  	}
  	
  	/** check body cube height array, return arrayIndex of body cube */
  	public static int getBodyArrayIDFromHeight(int heightPercent, BasicEntityShip ship)
  	{
  		int hit = -1;
  		
  		if (ship != null)
  		{
  	  		byte[] heightArray;
  	  		
  	  		//check stand or sit
  			if (ship.isSitting()) heightArray = ship.getBodyHeightSit();
  			else heightArray = ship.getBodyHeightStand();
  			
  			//check height
  			for (int i = 0; i < heightArray.length; i++)
  			{
  				if (heightPercent > heightArray[i])
  				{
  					hit = i;
  					break;
  				}
  			}
  		}
  		
  		return hit;
  	}
  	
  	/** get body cube range from ray trace height(%) */
  	public static int[] getBodyRangeFromHeight(int heightPercent, BasicEntityShip ship)
  	{
  		if (ship != null)
  		{
  	  		int hit = getBodyArrayIDFromHeight(heightPercent, ship);
  	  		byte[] heightArray;
  	  		
  	  		//check stand or sit
  			if (ship.isSitting()) heightArray = ship.getBodyHeightSit();
  			else heightArray = ship.getBodyHeightStand();
  			
  			//get body id
  	  		switch (hit)
  	  		{
  	  		case 0:
  	  			return new int[] {120, heightArray[0]};
  	  		case 1:
  	  		case 2:
  	  		case 3:
  	  		case 4:
  	  		case 5:
  	  			return new int[] {heightArray[hit-1], heightArray[hit]};
  	  		default:
  	  			return new int[] {heightArray[5], -20};
  	  		}
  		}
  		
  		return new int[] {1, 0};
  	}
  	
  	/** hit body part by hit height level
  	 *  default:
  	 *  height:        part:
  	 *  >100           top
  	 *  100~80         head
  	 *  80~70          neck
  	 *  70~45          back
  	 *  45~35          belly
  	 *  35~30          ubelly
  	 *  <30            leg
  	 */
  	public static Enums.BodyHeight getBodyIDFromHeight(int heightPercent, BasicEntityShip ship)
  	{
  		if (ship != null)
  		{
  	  		int hit = getBodyArrayIDFromHeight(heightPercent, ship);
  			
  			//get body id
  	  		switch (hit)
  	  		{
  	  		case 0:
  	  			return Enums.BodyHeight.TOP;
  	  		case 1:
  	  			return Enums.BodyHeight.HEAD;
  	  		case 2:
  	  			return Enums.BodyHeight.NECK;
  	  		case 3:
  	  			return Enums.BodyHeight.CHEST;
  	  		case 4:
  	  			return Enums.BodyHeight.BELLY;
  	  		case 5:
  	  			return Enums.BodyHeight.UBELLY;
  	  		default:
  	  			return Enums.BodyHeight.LEG;
  	  		}
  		}
  		
  		return Enums.BodyHeight.LEG;
  	}
  	
  	/** get hit side by hit angle (angle always positive)
  	 *  may change on different ship
  	 *  
  	 *  default:
  	 *  angle:         part:
  	 *  0 ~ -70        back
  	 *  290 ~ 360
  	 *  250 ~ 290      right
  	 *  110 ~ 250      front
  	 *  70 ~ 110       left
  	 */
  	public static Enums.BodySide getHitAngleID(int angle)
  	{
  		//right
		if (angle >= 250 && angle < 290)
		{
  			return Enums.BodySide.RIGHT;
		}
		//front
		else if (angle >= 110 && angle < 250)
		{
			return Enums.BodySide.FRONT;
		}
		//left
		else if (angle >= 70 && angle < 110)
		{
			return Enums.BodySide.LEFT;
		}
		//back
		else
		{
			return Enums.BodySide.BACK;
		}
  	}
  	
  	public static int getHitBodyID(BasicEntityShip ship)
  	{
  		return getHitBodyID(ship.getBodyIDFromHeight(), ship.getHitAngleID());
  	}
  	
  	/** hit body part by hit height level
  	 * 
  	 *  (default)      back   right    front    left
  	 *  height \ angle  0     -90      -180     -270
  	 *  Top            top     top      top      top
  	 *  Head           head    head     face     head
  	 *  Neck           neck    neck     neck     neck
  	 *  Chest          back    arm      chest    arm
  	 *  Belly          butt    arm      belly    arm
  	 *  UBelly         butt    butt     ubelly   butt
  	 *  Leg            leg     leg      leg      leg
  	 */
  	public static int getHitBodyID(Enums.BodyHeight h, Enums.BodySide s)
  	{
  		switch (h)
  		{
  		case TOP:
  			return ID.Body.Top;
  		case HEAD:
  			if (s == Enums.BodySide.FRONT)
  			{
  				return ID.Body.Face;
  			}
  			else
  			{
  				return ID.Body.Head;
  			}
  		case NECK:
  			return ID.Body.Neck;
  		case CHEST:
  			switch (s)
  			{
  			case FRONT:
  				return ID.Body.Chest;
  			case BACK:
  				return ID.Body.Back;
  			default:
  				return ID.Body.Arm;
  			}
  		case BELLY:
  			switch (s)
  			{
  			case FRONT:
  				return ID.Body.Belly;
  			case BACK:
  				return ID.Body.Butt;
  			default:
  				return ID.Body.Arm;
  			}
  		case UBELLY:
  			if (s == Enums.BodySide.FRONT)
  			{
  				return ID.Body.UBelly;
  			}
  			else
  			{
  				return ID.Body.Butt;
  			}
		default:  //leg
			return ID.Body.Leg;
  		}
  	}
  	
  	/** create ship entity, nbt can be null */
  	public static Entity createShipEntity(World world, int classID, NBTTagCompound nbt, double px, double py, double pz, boolean updateUID)
  	{
		//create new ship entity
		Entity ent = EntityList.createEntityByIDFromName(new ResourceLocation(Reference.MOD_ID, ShipCalc.getEntityToSpawnName(classID)), world);

		if (ent != null)
		{
			//set pos
			ent.motionX = 0D;
			ent.motionY = 0D;
			ent.motionZ = 0D;
			ent.setPosition(px, py, pz);

			//spawn entity
			world.spawnEntity(ent);
		}
		else
		{
			return null;
		}

		//set ship attrs
		if (ent instanceof BasicEntityShip)
		{
			BasicEntityShip ship = (BasicEntityShip) ent;

			//set alive
			ship.setHealth(ship.getMaxHealth());
			ship.isDead = false;
			ship.deathTime = 0;

			//read entity nbt data
			if (nbt != null)
			{
				ship.readFromNBT(nbt);

				if (ship.getHealth() < 1F) ship.setHealth(1F);
				ship.setStateFlag(ID.F.CanDrop, true);
				ship.isDead = false;
				ship.deathTime = 0;
			}

			//update ship cache
			if (updateUID) ship.updateShipCacheDataWithoutNewID();
		}

		return ent;
  	}
  	
	/** show name tag on head, CLIENT SIDE ONLY */
  	public static void showNameTag(BasicEntityShip ship)
  	{
		if (ship == null || ship.unitNames == null) return;

		//only show name tag if config enabled or player nearby or player press SPRINT key
		if (ConfigHandler.showTag ||
			ClientProxy.getGameSetting().keyBindSprint.isKeyDown() ||
			ship.getDistanceSq(ClientProxy.getClientPlayer()) < (ConfigHandler.nameTagDist * ConfigHandler.nameTagDist))
		{
			String str;
			
			//is owner
			if (ship.getPlayerUID() == EntityHelper.getPlayerUID(ClientProxy.getClientPlayer()))
			{
				str = TextFormatting.YELLOW + "";
			}
			//not owner
			else
			{
				str = TextFormatting.GOLD + "";
			}
			
			int strLen = 1;
			int strH = 0;
			int strLenTemp = 0;
			
			if (ship.unitNames.size() > 0)
			{
				//add unit names
				for (String s : ship.unitNames)
				{
					if (s != null && s.length() > 1)
					{
						strH++;
						strLenTemp = ClientProxy.getMineraft().getRenderManager().getFontRenderer().getStringWidth(s);
						if (strLenTemp > strLen) strLen = strLenTemp;
						
						str += s + "\n";
					}
				}
			}
			
			//add ship UID if debug mode
			if (ConfigHandler.debugMode)
			{
				String uids = TextFormatting.GREEN + "UID " + ship.getShipUID();
				
				strH++;
				str += uids;
				strLenTemp = ClientProxy.getMineraft().getRenderManager().getFontRenderer().getStringWidth(uids);
				if (strLenTemp > strLen) strLen = strLenTemp;
			}
			
			ParticleHelper.spawnAttackParticleAt(str, 0D, ship.height + 0.8D, 0D, (byte)1, strH, strLen+1, ship.getEntityId());
		}//end can show tag
	}
  	
  	/** update name tag data, SERVER SIDE ONLY */
  	public static void updateNameTag(BasicEntityShip ship)
  	{
  		if (ship == null) return;
  		
  		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(ship.getPlayerUID());
  		
  		if (capa != null)
  		{
  			ArrayList<Integer> tid = capa.getShipTeamIDArray(ship.getShipUID());
  			ArrayList<String> tname = new ArrayList<String>();
  			
  			for (int t : tid)
  			{
  				tname.add(capa.getUnitName(t));
  			}
  			
  			ship.unitNames = tname;
  			ship.sendSyncPacketUnitName();
  		}
  	}
  	
    /**
     *  teleport host entity
     *  if host = mount, dismount and teleport host's host only
     *  if host = ship, dismount and teleport host only
     *  
     *  dist: if > 1024D, dismount host
     *  tpPos: teleport target
     *  
     *  return true if teleport successfully
     */
  	public static boolean applyTeleport(IShipNavigator host, double dist, Vec3d tpPos)
    {
  		if (!ConfigHandler.canTeleport) return false;
  		if (host == null) return false;
  		
  		//check chunk loaded
  		try
  		{
  			Chunk c = ((Entity) host).world.getChunkProvider().getLoadedChunk(
  						(MathHelper.floor(tpPos.x) >> 4),
  						(MathHelper.floor(tpPos.z) >> 4));
  			
  			if (c == null) return false;  //chunk isn't loaded
  		}
  		catch (Exception e)
  		{
  			//teleport checking fail: entity null, world null or other ...
  			return false;
  		}
  		
  		//teleport mount entity
    	if (host instanceof BasicEntityMount)
    	{
    		BasicEntityShip hostHost = (BasicEntityShip) ((BasicEntityMount) host).getHostEntity();
    		
    		//too far away, dismount
    		if (dist > 1024D)
    		{
    			clearMountSeat((BasicEntityMount) host);
    			clearMountSeat(hostHost);
    		}
    		
    		//clear mount
    		((BasicEntityMount) host).setDead();
    		
    		//teleport rider
    		hostHost.setPositionAndUpdate(tpPos.x, tpPos.y, tpPos.z);
    		sendPositionSyncPacket(hostHost);
    		
    		return true;
    	}
    	//teleport normal entity
    	else if (host instanceof EntityLiving)
    	{
    		EntityLiving host2 = (EntityLiving) host;
    		
    		//too far away, dismount
    		if (dist > 1024D)
    		{
    			clearMountSeat(host2);
    		}

    		//teleport host
    		host.getShipNavigate().clearPath();
    		host2.setPositionAndUpdate(tpPos.x, tpPos.y, tpPos.z);
    		sendPositionSyncPacket(host2);
    		
    		return true;
    	}
    	
    	return false;
    }
  	
	/** sync position and rotation to client */
	public static void sendPositionSyncPacket(Entity ent)
	{
		//for other player, send ship state for display
		TargetPoint point = new TargetPoint(ent.dimension, ent.posX, ent.posY, ent.posZ, 256D);
		CommonProxy.channelE.sendToAllAround(new S2CEntitySync(ent, 0, S2CEntitySync.PID.SyncEntity_PosRot), point);
	}
	
	/** save ship data to nbt for spawn egg
	 * 
	 *  parms: punish: apply death punishment
	 */
	public static NBTTagCompound saveShipDataToNBT(BasicEntityShip ship, boolean punish)
	{
		//get ship data
    	NBTTagCompound nbt = new NBTTagCompound();
    	CapaShipInventory shipInv = ship.getCapaShipInventory();
    	String ownerUUID = EntityHelper.getPetPlayerUUID(ship);
    	String name = EntityHelper.getOwnerName(ship);
    	Attrs attrs = ship.getAttrs();
    	
    	int[] save1 = new int[7];
    	
    	//apply death punishment
    	if (punish)
    	{
    		//level - 1
        	if (ship.getLevel() > 1) save1[0] = ship.getLevel() - 1;
        	else save1[0] = 1;
    	}
    	
    	save1[1] = attrs.getAttrsBonus(ID.AttrsBase.HP);
    	save1[2] = attrs.getAttrsBonus(ID.AttrsBase.ATK);
    	save1[3] = attrs.getAttrsBonus(ID.AttrsBase.DEF);
    	save1[4] = attrs.getAttrsBonus(ID.AttrsBase.SPD);
    	save1[5] = attrs.getAttrsBonus(ID.AttrsBase.MOV);
    	save1[6] = attrs.getAttrsBonus(ID.AttrsBase.HIT);
    	
    	//get ship misc data 2
    	int[] save2 = new int[7];
    	
    	save2[0] = ship.getStateEmotion(ID.S.State);
//    	save2[1] = ship.getStateEmotion(ID.S.State2);	//NO USE
    	save2[2] = ship.getStateMinor(ID.M.FollowMin);
    	save2[3] = ship.getStateMinor(ID.M.FollowMax);
    	save2[4] = ship.getStateMinor(ID.M.FleeHP);
    	save2[5] = ship.getStateMinor(ID.M.WpStay);
    	save2[6] = ship.getStateMinor(ID.M.UseCombatRation);
    	
    	//get ship flag data
    	byte[] save3 = new byte[16];
    	
    	save3[0] = ship.getStateFlag(ID.F.IsMarried) ? (byte)1 : (byte)0;
    	save3[1] = ship.getStateFlag(ID.F.UseMelee) ? (byte)1 : (byte)0;
    	save3[2] = ship.getStateFlag(ID.F.UseAmmoLight) ? (byte)1 : (byte)0;
    	save3[3] = ship.getStateFlag(ID.F.UseAmmoHeavy) ? (byte)1 : (byte)0;
    	save3[4] = ship.getStateFlag(ID.F.UseAirLight) ? (byte)1 : (byte)0;
    	save3[5] = ship.getStateFlag(ID.F.UseAirHeavy) ? (byte)1 : (byte)0;
    	save3[6] = ship.getStateFlag(ID.F.UseRingEffect) ? (byte)1 : (byte)0;
    	save3[7] = ship.getStateFlag(ID.F.OnSightChase) ? (byte)1 : (byte)0;
    	save3[8] = ship.getStateFlag(ID.F.PVPFirst) ? (byte)1 : (byte)0;
    	save3[9] = ship.getStateFlag(ID.F.AntiAir) ? (byte)1 : (byte)0;
    	save3[10] = ship.getStateFlag(ID.F.AntiSS) ? (byte)1 : (byte)0;
    	save3[11] = ship.getStateFlag(ID.F.PassiveAI) ? (byte)1 : (byte)0;
    	save3[12] = ship.getStateFlag(ID.F.TimeKeeper) ? (byte)1 : (byte)0;
    	save3[13] = ship.getStateFlag(ID.F.PickItem) ? (byte)1 : (byte)0;
    	save3[14] = ship.getStateFlag(ID.F.ShowHeldItem) ? (byte)1 : (byte)0;
    	save3[15] = ship.getStateFlag(ID.F.AutoPump) ? (byte)1 : (byte)0;
    	
    	//save ship attributes
    	nbt.setString("owner", ownerUUID);									//owner UUID
    	nbt.setString("ownername", name);									//owner name
    	nbt.setInteger("PlayerID", ship.getStateMinor(ID.M.PlayerUID));		//owner UID
    	nbt.setTag(CapaInventory.InvName, shipInv.serializeNBT());			//inventory data
    	nbt.setIntArray("Attrs", save1);									//misc data
    	nbt.setIntArray("Attrs2", save2);									//misc data2
    	nbt.setByteArray("Flags", save3);									//flag data
    	nbt.setInteger("ShipID", ship.getStateMinor(ID.M.ShipUID));			//ship UID
    	nbt.setString("customname", ship.getCustomNameTag());				//custom name
    	
    	return nbt;
	}
	
	/** spawn mob ticking
	 * 
	 *  ConfigHandler.mobSpawn[]
	 *  0: Max number in the world
	 *  1: Spawn prob every X ticks
	 *  2: #groups each spawn
	 *  3: #min each group
	 *  4: #max each group
	 */
	public static void spawnMobShip(EntityPlayer player, CapaTeitoku capa)
	{
		//null check
		if (player == null || capa == null || player.world.getDifficulty() == EnumDifficulty.PEACEFUL)
		{
			return;
		}
		
		boolean canSpawn = false;
		int blockX = (int) player.posX;
		int blockZ = (int) player.posZ;
		int spawnX, spawnY, spawnZ = 0;
		Biome biome = player.world.getBiomeForCoordsBody(new BlockPos(blockX, 0, blockZ));
		World w = player.world;
		Random rng = player.getRNG();
		
		//check ring
		if (ConfigHandler.checkRing)
		{
			if (capa.hasRing()) canSpawn = true;
		}
		else
		{
			canSpawn = true;
		}
		
		//check biome
		if (canSpawn)
		{
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.WATER) ||
				BiomeDictionary.hasType(biome, BiomeDictionary.Type.BEACH)) {}
			else
			{
				canSpawn = false;
			}
		}

		//apply spawn
		if (canSpawn)
		{
			int entNum = EntityHelper.getEntityNumber(0, w);
			
			//check ship number in the world
			if (entNum <= ConfigHandler.mobSpawn[0])
			{
				//roll spawn
				if (rng.nextInt(100) <= ConfigHandler.mobSpawn[1])
				{
					//get spawn position
					int groups = ConfigHandler.mobSpawn[2];
					int loop = 30 + groups * 30;
					
					while (groups > 0 && loop > 0)
					{
						int offX = rng.nextInt(30) + 20;
						int offZ = rng.nextInt(30) + 20;
						
						switch (rng.nextInt(4))
						{
						case 1:
							spawnX = blockX - offX;
							spawnZ = blockZ - offZ;
							break;
						case 2:
							spawnX = blockX + offX;
							spawnZ = blockZ - offZ;
							break;
						case 3:
							spawnX = blockX - offX;
							spawnZ = blockZ + offZ;
							break;
						default:
							spawnX = blockX + offX;
							spawnZ = blockZ + offZ;
							break;
						}

						IBlockState blockY = w.getBlockState(new BlockPos(spawnX, w.provider.getAverageGroundLevel() - 2, spawnZ));
						LogHelper.debug("DEBUG: spawn mob ship: group: "+groups+
								" get block: "+blockY.getBlock().getLocalizedName()+" "+spawnX+
								" "+(w.provider.getAverageGroundLevel()-2)+" "+spawnZ);
						
						//spawn on water
						if (blockY.getMaterial() == Material.WATER)
						{
							groups--;
							
							//get top water block
							spawnY = BlockHelper.getToppestWaterHeight(w, spawnX, w.provider.getAverageGroundLevel() - 3, spawnZ);
							
							//get spawn number
							int shipNum = Math.max(1, ConfigHandler.mobSpawn[3]);
							int ranMax = ConfigHandler.mobSpawn[4] - ConfigHandler.mobSpawn[3];
							
							if (ranMax > 0)
							{
								shipNum = ConfigHandler.mobSpawn[3] + rng.nextInt(ranMax + 1);
							}
							
							//spawn mob
							for (int i = 0; i < shipNum; i++)
							{
								//get random mob
				            	Entity mobToSpawn = EntityList.createEntityByIDFromName(new ResourceLocation(Reference.MOD_ID, ShipCalc.getRandomMobToSpawnName()), w);
				            	
				            	//spawn mob
				            	if (mobToSpawn instanceof BasicEntityShipHostile)
				            	{
				            		((BasicEntityShipHostile) mobToSpawn).initAttrs(rng.nextInt(10) > 7 ? 1 : 0);
				            		mobToSpawn.setPosition(spawnX + rng.nextDouble(), spawnY + 0.5D, spawnZ + rng.nextDouble());
									w.spawnEntity(mobToSpawn);
				            	}
							}
						}//end get water block
						
						loop--;
					}//end spawn while
				}//end roll spawn
			}//end check mob number
		}//end can spawn
			
	}
	
	/** spawn boss ticking */
	public static void spawnBossShip(EntityPlayer player, CapaTeitoku capa)
	{
		//null check
		if (player == null || capa == null || player.world.getDifficulty() == EnumDifficulty.PEACEFUL)
		{
			return;
		}
				
		int blockX = (int) player.posX;
		int blockZ = (int) player.posZ;
		int spawnX, spawnY, spawnZ = 0;
		Biome biome = player.world.getBiomeForCoordsBody(new BlockPos(blockX, 0, blockZ));
		
		//boss cooldown--
		if ((BiomeDictionary.hasType(biome, BiomeDictionary.Type.WATER) ||
			 BiomeDictionary.hasType(biome, BiomeDictionary.Type.BEACH)) && capa.hasRing())
		{
			capa.setBossCooldown(capa.getBossCooldown() - 1);
		}
		
		//cooldown = 0, roll spawn
		if (capa.getBossCooldown() <= 0)
		{
			World w = player.world;
			Random rng = player.getRNG();
			
			capa.setBossCooldown(ConfigHandler.bossCooldown);
			
			int rolli = rng.nextInt(4);
			LogHelper.debug("DEBUG: spawn boss: roll spawn "+rolli);
			if (rolli == 0)
			{
				//尋找20次地點, 找到一個可生成地點即生成後跳出loop
				int i;
				for (i = 0; i < 20; i++)
				{
					int offX = rng.nextInt(32) + 32;
					int offZ = rng.nextInt(32) + 32;
					
					switch (rng.nextInt(4))
					{
					case 1:
						spawnX = blockX - offX;
						spawnZ = blockZ - offZ;
						break;
					case 2:
						spawnX = blockX + offX;
						spawnZ = blockZ - offZ;
						break;
					case 3:
						spawnX = blockX - offX;
						spawnZ = blockZ + offZ;
						break;
					default:
						spawnX = blockX + offX;
						spawnZ = blockZ + offZ;
						break;		
					}
					
					IBlockState blockY = w.getBlockState(new BlockPos(spawnX, w.provider.getAverageGroundLevel() - 2, spawnZ));
					
					LogHelper.debug("DEBUG: spawn boss: check block: "+blockY.getBlock().getLocalizedName()+
							" "+spawnX+" "+(w.provider.getAverageGroundLevel() - 2)+" "+spawnZ);
					//生成在水面
					if (blockY.getMaterial() == Material.WATER)
					{
						//get top water block
						spawnY = BlockHelper.getToppestWaterHeight(w, spawnX, 63, spawnZ);
						
						//check 64x64 range
						AxisAlignedBB aabb = new AxisAlignedBB(spawnX-48D, spawnY-48D, spawnZ-48D, spawnX+48D, spawnY+48D, spawnZ+48D);
						List<BasicEntityShipHostile> listBoss = w.getEntitiesWithinAABB(BasicEntityShipHostile.class, aabb);
						int bossNum = 0;
						
						//check boss in list
						for(BasicEntityShipHostile mob : listBoss)
						{
							if (!mob.isNonBoss())
							{
								bossNum++;
							}
						}
						LogHelper.debug("DEBUG: spawn boss: check existed boss: "+bossNum+" all mob: "+listBoss.size());
						
						//若範圍內不到2隻boss, 則可以再生成新boss
			            if (bossNum < 2)
			            {
			            	//roll生成mob
			            	Entity mobToSpawn;
			            	
			            	//roll boss ship
			            	int j;
			            	for (j = 0; j < ConfigHandler.spawnBossNum; j++)
			            	{
			            		mobToSpawn = EntityList.createEntityByIDFromName(new ResourceLocation(Reference.MOD_ID, ShipCalc.getRandomMobToSpawnName()), w);
			            		
				            	//spawn mob
				            	if (mobToSpawn instanceof BasicEntityShipHostile)
				            	{
				            		((BasicEntityShipHostile) mobToSpawn).initAttrs(rng.nextInt(100) > 65 ? 3 : 2);
				            		mobToSpawn.setPosition(spawnX + rng.nextInt(3), spawnY + 0.5D, spawnZ + rng.nextInt(3));
				            		w.spawnEntity(mobToSpawn);
				            	}
			            	}
			            	
			            	//roll small ship
			            	for (j = 0; j < ConfigHandler.spawnMobNum; j++)
			            	{
			            		mobToSpawn = EntityList.createEntityByIDFromName(new ResourceLocation(Reference.MOD_ID, ShipCalc.getRandomMobToSpawnName()), w);
			            		
				            	//spawn mob
				            	if (mobToSpawn instanceof BasicEntityShipHostile)
				            	{
				            		((BasicEntityShipHostile) mobToSpawn).initAttrs(rng.nextInt(2));
				            		mobToSpawn.setPosition(spawnX + rng.nextInt(3), spawnY + 0.5D, spawnZ + rng.nextInt(3));
				            		w.spawnEntity(mobToSpawn);
				            	}
			            	}
			            	
			            	//發出spawn msg
			            	TextComponentTranslation spawnText = null;
			            	if (rng.nextInt(2) == 0)
			            	{
			            		spawnText = new TextComponentTranslation("chat.shincolle:bossspawn1");
			            	}
			            	else
			            	{
			            		spawnText = new TextComponentTranslation("chat.shincolle:bossspawn2");
			            	}
			            	
			            	ServerProxy.getServer().sendMessage(new TextComponentString(""+TextFormatting.YELLOW+spawnText+
			            			TextFormatting.AQUA+" "+spawnX+" "+spawnY+" "+spawnZ));
			            	
			            	LogHelper.debug("DEBUG: spawn fleet "+spawnX+" "+spawnY+" "+spawnZ);
							break;
			            }//end if nearby boss < 2	
					}//end get water block
				}//end roll 20 times
			}//end roll spawn boss
		}//end boss cooldown <= 0
	}
	
  	
}