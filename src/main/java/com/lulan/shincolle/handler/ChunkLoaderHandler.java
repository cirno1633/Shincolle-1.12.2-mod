package com.lulan.shincolle.handler;

import com.lulan.shincolle.utility.LogHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

import java.util.List;

public class ChunkLoaderHandler implements LoadingCallback
{
	
	
	public ChunkLoaderHandler() {}

	//when world reloaded, manage chunk loader tickets here
	@Override
	public void ticketsLoaded(List<Ticket> tickets, World world)
	{
		LogHelper.debug("DEBUG : release all saved chunk loader tickets");
		//release all tickets
  		if (tickets != null)
  		{
  			for(Ticket t : tickets)
  			{
  				ForgeChunkManager.releaseTicket(t);
  			}
  		}
	}
	

}
