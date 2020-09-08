package com.lulan.shincolle.utility;

import com.lulan.shincolle.handler.ConfigHandler;

public class GuiHelper
{

	//hard coded button position (x1,y1,x2,y2)
	private static final int[][][][] BUTTON =
	{
		{//gui 0: ship inventory
		 //0: page0          1: page1          2: page2
		 {{133,18,142,52},  {133,53,142,88},  {133,89,142,125},		//0: all
		 //3: AI op0         4: AI op1         5: AI op2
		  {173,131,237,143},{173,144,237,156},{173,157,237,169},
		 //6: AI op3         7: AI op4		   8: AI op5
		  {173,170,237,182},{173,183,237,195},{173,196,237,208},
		 //9: AI page0       10: AI page1      11: AI page2
		  {239,131,245,142},{239,144,245,155},{239,157,245,168},
		 //12: AI page3      13: AI page4      14: AI page5
		  {239,170,245,181},{239,183,245,194},{239,196,245,208},
		 //15: inv page0     16: inv page1     17: inv page2
		  {61,18,70,52},    {61,53,70,88},    {61,89,70,125},
		 //18: AI page6      19: AI page7      20: AI page8
		  {246,131,253,142},{246,144,253,155},{246,157,253,168},
		 //21: AI page9      22: AI page10     23: AI page11
		  {246,170,253,181},{246,183,253,194},{246,196,253,208}},
		 //0: attack
		  {{73,18,132,40}},											//1: attribute
		 //0: AI page2 bar0  1: AI page2 bar1  2: AI page3 bar2
		  {{187,145,238,154},{187,169,238,178},{187,193,238,202}}	//2: AI control
		},
		{//gui 1: small shipyard
		 //0:ship button    1:equip button
		 {{122,16,141,36}, {142,16,162,36}}							//0: all
		},
		{//gui 2: large shipyard
		 //0:ship button    1:equip button   2:inventory mode
		 {{157,24,175,42}, {177,24,195,42}, {23,93,48,112},			//0: all
		 //3:grudge         4:abyssium       5:ammo
		  {27,14,45,32},   {27,33,45,51},   {27,52,45,70},
		 //6:polymetal      7:grudge num     8:abyss num
		  {27,71,45,89},   {51,19,97,27},   {51,38,97,46},
		 //9:ammo num       10:poly num
		  {51,57,97,65},   {51,76,97,84}},
		 //0:grud +1k    1:grud +100   2:grud +10    3:grud +1
		 {{50,8,62,18}, {62,8,74,18}, {74,8,86,18},	{86,8,98,18},	//1: grudge
		 //4:grud -1k    5:grud -100   6:grud -10    7:grud -1    
		  {50,28,62,38},{62,28,74,38},{74,28,86,38},{86,28,98,38}},
		 //0:abyss +1k   1:abyss +100  2:abyss +10   3:abyss +1
		 {{50,27,62,37},{62,27,74,37},{74,27,86,37},{86,27,98,37},	//2: abyss
		 //4:abyss -1k   5:abyss -100  6:abyss -10   7:abyss -1    
		  {50,47,62,57},{62,47,74,57},{74,47,86,57},{86,47,98,57}},
		 //0:ammo +1k    1:ammo +100   2:ammo +10    3:ammo +1 
		 {{50,46,62,56},{62,46,74,56},{74,46,86,56},{86,46,98,56},	//3: ammo
		 //4:ammo -1k    5:ammo -100   6:ammo -10    7:ammo -1    
		  {50,66,62,76},{62,66,74,76},{74,66,86,76},{86,66,98,76}},
		 //0:poly +1k    1:poly +100   2:poly +10    3:poly +1 
		 {{50,65,62,75},{62,65,74,75},{74,65,86,75},{86,65,98,75},	//4: poly
		 //4:poly -1k    5:poly -100   6:poly -10    7:poly -1    
		  {50,85,62,95},{62,85,74,95},{74,85,86,95},{86,85,98,95}}
		},
		{//gui 3: admiral desk
		 //0:radar btn  1:book btn	  2:team btn   	3:target btn	//0: all
		 {{3,2,19,18}, {22,2,38,18}, {41,2,57,18}, {60,2,76,18}},
		 //0:radar scale	 1:ship slot 0     2:ship slot 1		//1: radar
		 {{7,158,55,170},   {140,23,252,54},  {140,55,252,86},
		 //3:ship slot 2     4:ship slot 3     5:ship slot 4
		  {140,87,252,118}, {140,119,252,150},{140,151,252,187},
		 //6:openGUI 
		  {7,172,55,184}},
		 //0:left page       1:right page	   2:chap 0				//2: book
		 {{0,25,122,193},   {123,25,240,193}, {243,34,256,45},
		 //3:chap 1          4:chap 2          5:chap 3
		  {243,46,256,59},  {243,60,256,71},  {243,72,256,82},
		 //6:chap 4          7:chap 5          8:chap 6
		  {243,83,256,96},  {243,97,256,109}, {243,110,256,121}},
		 //0:left top   	 1:team slot 0     2:team slot 1		//3: team
		 {{7,158,55,170},   {140,23,252,54},  {140,55,252,86},
		 //3:team slot 2     4:team slot 3     5:team slot 4
		  {140,87,252,118}, {140,119,252,150},{140,151,252,187},
		 //6:left bottom     7:right top       8:right bottom
		  {7,172,55,184},   {86,158,134,170}, {86,172,135,184},
		 //9:left slot 0     10:left slot 1    11:left slot 2 
		  {7,61,134,91},    {7,92,134,122},   {7,123,134,153}},
		 //0:target remove	 1:slot 0          2:slot 1				//4: target
		 {{7,158,55,170},   {140,23,252,37},  {140,38,252,49},
		 //3:slot 2	         4:slot 3          5:slot 4  
		  {140,50,252,61},  {140,62,252,73},  {140,74,252,85},
		 //6:slot 5          7:slot 6          8:slot 7 
		  {140,86,252,97},  {140,98,252,109}, {140,110,252,121},
		 //9:slot 8          10:slot 9         11:slot 10 
		  {140,122,252,133},{140,134,252,145},{140,146,252,157},
		 //12:slot 11        13:slot 12        
		  {140,158,252,169},{140,170,252,183}},
		 //0:model       	 1:sit	       	   2:run			    //5: book chap 4/5
		 {{18,45,110,157},  {22,158,30,166},  {33,158,41,166},
		 //3:attack          4:emotion         5:model st1
		  {22,169,30,177},  {33,169,41,177},  {44,159,52,167},
		 //6:model st2       7:model st3       8:model st4
		  {44,168,52,176},  {53,159,61,167},  {53,168,61,176},
		 //9:model st5       10:model st6      11:model st7
		  {62,159,70,167},  {62,168,70,176},  {71,159,79,167},
		 //12:model st8      13:model st9      14:model st10
		  {71,168,79,176},  {80,159,88,167},  {80,168,88,176},
		 //15:model st11     16:model st12     17:model st13
		  {89,159,97,167},  {89,168,97,176},  {98,159,106,167},
		 //18:model st14     19:model st15     20:model st16
		  {98,168,106,176}, {107,159,115,167},{107,168,115,176}}
		},
		{//gui 4: formation
		 //0:no format       1:LineAhead       2:DoubleLine
		 {{17,148,34,165},  {35,148,52,165},  {53,148,70,165},
		 //3:diamond         4:echelon         5:LineAbreast
		  {71,148,88,165},  {89,148,106,165}, {107,148,124,165},
		 //6:team 0          7:team 1          8:team 2
		  {17,166,28,179},  {29,166,40,179},  {41,166,52,179},
		 //9:team 3          10:team 4         11:team 5
		  {53,166,64,179},  {65,166,76,179},  {77,166,88,179},
		 //12:team 6         13:team 7         14:team 8
		  {89,166,100,179}, {101,166,112,179},{113,166,124,179},
		 //15:list 0         16:list 1         17:list 2
		  {142,5,250,32},   {142,33,250,59},  {142,60,250,86},
		 //18:list 3         19:list 4         20:list 5
		  {142,87,250,113}, {142,114,250,140},{142,141,250,167},
		 //21:btn DOWN       22:btn UP         23:gui
		  {159,170,189,180},{203,170,233,180},{46,180,94,192}}
		},
		{//gui 5: crane
		 //0:power           1:mode            2:metadata
		 {{7,6,20,19},      {23,6,90,19},     {22,21,35,34},
		 //3:ore dict        4:loading         5:unloading
		  {36,21,49,34},    {7,52,80,63},     {7,83,80,94},
		 //6:nbt             7:red signal      8:liquid mode
		  {50,21,63,34},    {64,21,77,34},    {23,36,36,49},
		 //9:energy mode
		  {39,36,52,49}
		 }
		},
		{//gui 6: vol core
		 //0:power
		 {{7,6,20,19}
		 }
		},
		{//gui 7: morph ship inventory
		 {//0:+ammoL         1:+ammoH          2:grudge+
		  {7,18,25,35},     {7,36,25,53},     {7,54,25,71}
		 }
		}
	};
	
	public GuiHelper() {}
	
	//get button in gui and page
	public static int getButton(int gui, int page, int x, int y)
	{  
		//match button
		for (int i = 0; i < BUTTON[gui][page].length; i++)
		{
			if (x >= BUTTON[gui][page][i][0] && y >= BUTTON[gui][page][i][1] &&
				x <= BUTTON[gui][page][i][2] && y <= BUTTON[gui][page][i][3])
			{
//				LogHelper.info("DEBUG : GUI get button: gui "+gui+" page "+page+" x "+x+" y "+y+" button "+i);
				return i;
			}
		}
		
//		LogHelper.info("DEBUG : GUI get no button: gui "+gui+" page "+page+" x "+x+" y "+y);
		return -1;
	}
	
	//ship bonus point to text color: white -> yellow -> orange -> red
	public static int getBonusPointColor(int level)
	{
		/**
		 * bonus color:
		 *   white -> yellow -> red
		 * = RGB255 -> RG255 -> R255
		 * = 16711680 + 65280 + 255 -> 16711680 + 65280 -> 16711680
		 * 
		 * 0~50% = blue--
		 * 51~100% = green--
		 */
		int max = ConfigHandler.modernLimit;
		int color = 0;
		float flv = (float)level / (float)max - 0.5F;
		
		if (flv >= 0.5F)
		{
			return 16711680;
		}
		else if (flv >= 0F)
		{
			color = (int) (255F * (1F - flv * 2F)) * 256 + 16711680;
		}
		else
		{
			flv += 0.5F;
			color = (int) (255F * (1F - flv * 2F)) + 16776960;
		}

		return color;
	}
	
	/** get darker color, ex: dark = 0.5F = RGB * 0.5F */
	public static int getDarkerColor(int color, float dark)
	{
		float b = (color & 255) * dark;
		color >>= 8;
		float g = (color & 255) * dark;
		color >>= 8;
		float r = color * dark;
		
		return ((int)r << 16) + ((int)g << 8) + (int)b;
	}
	
	
}