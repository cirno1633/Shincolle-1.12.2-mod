package com.lulan.shincolle.capability;

import com.lulan.shincolle.client.gui.inventory.ContainerShipInventory;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;



/** inventory capability for ships
 *
 */
public class CapaShipInventory extends CapaInventory<BasicEntityShip> implements IInventory
{

	public static final int SlotPages = 3;
	public static final int SlotMax = 6 + 18 * 3;   	//6 equip + 18 inv * 3 page
	private int inventoryPage = 0;						//current inventory page id
	
	
	public CapaShipInventory(int size, BasicEntityShip host)
	{
		super(size, host);
	}
	
	public int getInventoryPage()
	{
		return this.inventoryPage;
	}
	
	public void setInventoryPage(int par1)
	{
		if (par1 >= 0 && par1 < 3)
		{
			this.inventoryPage = par1;
		}
		else
		{
			this.inventoryPage = 0;
		}
		
		this.host.sendSyncPacketGUI();
	}

	@Override
	public String getName()
	{
		return null;
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return null;
	}

	@Override
	public int getSizeInventory()
	{
		return ContainerShipInventory.SLOTS_PLAYERINV;
	}

	@Override
	public boolean isEmpty()
	{
		return false;
	}

	//inventory size including all enabled pages
	public int getSizeInventoryPaged()
	{
		if (this.host != null)
		{
			return ContainerShipInventory.SLOTS_PLAYERINV + this.host.getStateMinor(ID.M.DrumState) * 18;
		}
		
		return ContainerShipInventory.SLOTS_PLAYERINV;
	}
	
	@Override
	public ItemStack getStackInSlot(int i)
	{
		validateSlotIndex(i);
		return stacks.get(i);
	}
	
	/** get itemstack in slot with page limit check */
	public ItemStack getStackInSlotWithPageCheck(int i)
	{
		if (i < 0 || i >= this.getSizeInventoryPaged() || i >= stacks.size())
            throw new RuntimeException("Slot " + i + " not in valid range - [0," + stacks.size() + ")");
		return stacks.get(i);
	}

	/** note:
	 *  這裡id是已經有page轉換過, 最大值為this.stacks.length
	 */
	@Override
	public ItemStack decrStackSize(int id, int count)
	{
  		try
		{
  			if (id >= 0 && id < getSlots() && !getStackInSlot(id).isEmpty() && count > 0)
  	        {
  	            ItemStack itemstack = getStackInSlot(id).splitStack(count);
  	            
  	            if (getStackInSlot(id).getCount() == 0)
  	            {
  	            	setStackInSlot(id, ItemStack.EMPTY);
  	            }

  	            return itemstack;
  	        }
  	        else
  	        {
  	            return ItemStack.EMPTY;
  	        }
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ItemStack.EMPTY;
		}
	}

	//NO USE: 用於GUI關閉時移除特定slot中的物品, 使其掉落出來, 目前沒有需要此方法
	@Override
	public ItemStack removeStackFromSlot(int id)
	{
		return ItemStack.EMPTY;
	}

	/** note:
	 *  這裡的id是page轉換前, 範圍最大到24 (6裝備格+一頁18格)
	 *  若id傳入大小超過24格, 則視為對全部page存取, 此時範圍最大到this.stacks.length
	 */
	@Override
	public void setInventorySlotContents(int i, ItemStack stack)
	{
		setStackInSlot(i, stack);
		
		//若手上物品超過該格子限制數量, 則只能放進限制數量
		if (!stack.isEmpty() && stack.getCount() > getInventoryStackLimit())
		{
			stack.setCount(getInventoryStackLimit());
		}
		
		//change equip slot
		if (!this.host.world.isRemote && i < 6)
		{
			this.host.calcShipAttributes(2, true);  //update equip and attribute value
		}
	}
	
	/** set itemstack to slot with page limit check */
	public void setInventorySlotWithPageCheck(int i, ItemStack stack)
	{
		//check slot id
		if (i < 0 || i >= this.getSizeInventoryPaged() || i >= stacks.size())
            throw new RuntimeException("Slot " + i + " not in valid range - [0," + stacks.size() + ")");
        
		//set itemstack
		if (!ItemStack.areItemStacksEqual(this.stacks.get(i), stack))
		{
	        this.stacks.set(i, stack);
	        onContentsChanged(i);
		}
		
		//if change equip slot
		if (!this.host.world.isRemote && i < 6)
		{
			this.host.calcShipAttributes(2, true);  //update equip and attribute value
		}
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public void markDirty() {}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return true;
	}

	@Override
	public void clear()
	{
	}
	
	/** check slot id with inventory page */
	public boolean isSlotAvailable(int slotid)
	{
		//has 0~1 page
		if (this.host.getInventoryPageSize() < 2)
		{
			if (slotid >= 42)
			{
				return false;
			}
			//has 0 page
			else if (this.host.getInventoryPageSize() < 1)
			{
				if (slotid >= 24)
				{
  					return false;
  				}
  			}
		}//end page check
		
		return true;
	}
	
	/** get first empty slots */
  	public int getFirstSlotForItem()
  	{
  		for (int i = 6; i < this.getSlots(); i++)
  		{
  			//stop loop if no available slot
  			if (!isSlotAvailable(i)) return -1;
  			
  			//get empty slot
  			if (getStackInSlot(i).isEmpty()) return i;
  		}
  		
  		return -1;
  	}
  	
  	//get first item with same metadata and has space for stack
    private int getFirstSlotStackable(ItemStack stack)
    {
        for (int i = 6; i < this.getSlots(); ++i)
        {
        	//stop loop if no available slot
  			if (!isSlotAvailable(i)) return -1;
        	
  			//check same item, same meta, same nbt tag and has stack space
  			ItemStack slotstack = getStackInSlot(i);
  			
            if (!slotstack.isEmpty() && slotstack.getItem() == stack.getItem() &&
            	slotstack.isStackable() && slotstack.getCount() < slotstack.getMaxStackSize() &&
            	slotstack.getCount() < this.getInventoryStackLimit() &&
                (!slotstack.getHasSubtypes() || slotstack.getItemDamage() == stack.getItemDamage()) &&
                 ItemStack.areItemStackTagsEqual(slotstack, stack))
            {
                return i;
            }
        }

        return -1;
    }
  	
  	/** vanilla method
     *  This function stores as many items of an ItemStack as possible in a matching slot and returns the quantity of
     *  leftover items.
     */
    private int storePartialItemStack(ItemStack stack)
    {
        Item item = stack.getItem();
        int i = stack.getCount();
        int j;

        //non stackable item
        if (stack.getMaxStackSize() == 1)
        {
        	//check empty slot
            j = getFirstSlotForItem();

            //no space
            if (j < 0)
            {
                return i;
            }
            else
            {
            	setStackInSlot(j, stack.copy());
                return 0;
            }
        }
        //stackable item
        else
        {
        	//check exists stack
            j = getFirstSlotStackable(stack);

            //no same stack, check empty slot
            if (j < 0)
            {
                j = getFirstSlotForItem();
            }

            //no empty slot, return
            if (j < 0)
            {
                return i;
            }
            //get empty slot
            else
            {
            	//add item to slot
            	ItemStack slotstack = getStackInSlot(j);
            	
                if (slotstack.isEmpty())
                {
                	ItemStack copyitem = new ItemStack(item, 0, stack.getItemDamage());
                	
                    if (stack.hasTagCompound())
                    {
                    	copyitem.setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
                    }
                    
                	setStackInSlot(j, copyitem);
                }

                //calc leftover stack size
                int k = i;

                //check the item max stack size
                slotstack = getStackInSlot(j);
                
                if (i > slotstack.getMaxStackSize() - slotstack.getCount())
                {
                    k = slotstack.getMaxStackSize() - slotstack.getCount();
                }

                //check the slot max stack size
                if (k > this.getInventoryStackLimit() - slotstack.getCount())
                {
                    k = this.getInventoryStackLimit() - slotstack.getCount();
                }

                //no space for item
                if (k == 0)
                {
                    return i;
                }
                //get space for item
                else
                {
                    i -= k;
                    slotstack.grow(k);
                    slotstack.setAnimationsToGo(5);
                    return i;
                }
            }
        }
    }
  	
  	/** add itemstack to ship inventory */
  	public boolean addItemStackToInventory(final ItemStack stack)
  	{
        if (!stack.isEmpty() && stack.getCount() != 0 && stack.getItem() != null)
        {
            try
            {
                int i;

                //item with meta != 0
                if (stack.isItemDamaged())
                {
                    i = this.getFirstSlotForItem();

                    //add item to slot
                    if (i >= 0)
                    {
                    	ItemStack copyitem = stack.copy();
                    	copyitem.setAnimationsToGo(5);
                    	setStackInSlot(i, copyitem);
                        stack.setCount(0);
                        return true;
                    }
                    //add fail
                    else
                    {
                        return false;
                    }
                }
                //item without meta value
                else
                {
                	//add item to slot with stackable check
                    do
                    {
                        i = stack.getCount();
                        stack.setCount(this.storePartialItemStack(stack));
                    }
                    while (stack.getCount() > 0 && stack.getCount() < i);
                    
                    return stack.getCount() < i;
                }
            }
            catch (Exception e)
            {
            	LogHelper.info("EXCEPTION : add item to ship's inventory fail: "+e+" "+stack);
            	return false;
            }
        }
        else
        {
            return false;
        }
    }
    
  	/**
  	 * get/setField為GUI container更新用
  	 * 使資料可以只用相同方法取值, 不用每個資料用各自方法取值
  	 * 方便for loop撰寫
  	 * 
  	 */
	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{
	}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	
}