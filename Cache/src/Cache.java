import java.lang.reflect.Array;
import java.util.Arrays;


public class Cache 
{
	String[] cache;
	int[] setIndex;
	
	int cacheSizeKB;
	
	int cacheLineSizeB;
	int cacheLineIndexLower;
	int cacheLineIndexUpper;
	
	int associativity;
	
	int setCount;
	int setBit;
	
	int blockCount;
	int blockOffsetBit;
	
	int tagBit;
	
	int loadHit;
	int loadMiss;
	
	int storeHit;
	int storeMiss;
	
	public Cache(int cacheSizeKB, int cacheLineSizeB, int associativity)
	{
		this.cacheSizeKB = cacheSizeKB;
		this.cacheLineSizeB = cacheLineSizeB;
		this.associativity = associativity;
		
		this.blockCount = cacheSizeKB * 1024 / cacheLineSizeB;
		this.blockOffsetBit = (int)(Math.log(cacheLineSizeB) / Math.log(2));
		
		this.setCount = cacheSizeKB * 1024 / (cacheLineSizeB * associativity);
		this.setBit = (int)(Math.log(setCount) / Math.log(2));
		
		this.tagBit = 32 - setBit - blockOffsetBit; 
				
		//System.out.println("Set Count: " + setCount + ", Block count: " + blockCount);
		//System.out.println("Set Bit: " + setBit + ", Block Offset Bit: " + blockOffsetBit + " Tag Bit: " + tagBit);
		
		cache = new String[blockCount];
		setIndex = new int[setCount];
		
		Arrays.fill(cache, "-1");
		Arrays.fill(setIndex, associativity - 1);
		
	}
	
	public void accessCache(String Operation, String memAddress, String size)
	{
		long memAddressLong = Long.parseLong(memAddress);
		long sizeLong = Long.parseLong(size);
		long memAddressStart;
		
		String memAddressBinary;
		
		String tagBinary;
		String tagString;
		
		String setIndexBinary;
		int setIndexInt;
		
		cacheLineIndexLower = (int) (memAddressLong / (long)cacheLineSizeB);
		cacheLineIndexUpper = (int) ((memAddressLong+sizeLong-1) / (long)cacheLineSizeB);
		
		//System.out.print(Operation + " " + memAddress + ", " + size + "=> ");
		
		int tempHit = 0;
		int tempMiss = 0;
				
		for(int i = cacheLineIndexLower; i <= cacheLineIndexUpper; i++ )
		{
			memAddressStart = (long)i * (long)cacheLineSizeB;
			memAddressBinary = Utility.intToBinary(memAddressStart);
			
			tagBinary = memAddressBinary.substring(0, tagBit);
			setIndexBinary = memAddressBinary.substring(tagBit, tagBit+setBit);
			
			tagString = String.valueOf(Utility.binaryToInt(tagBinary));
			setIndexInt = (int)(Utility.binaryToInt(setIndexBinary));
			
			boolean found = false;
			for(int j = 0; j < associativity; j++)
			{
				if(cache[setIndexInt*associativity + j].equals(tagString))
				{
					found = true;
					tempHit++;
					
					break;
				}
			}
			
			if(found == false)
			{
				
				//System.out.print(" evicting -> " + cache[setIndexInt*associativity + setIndex[setIndexInt]]);
				/*
				if(memAddress.equals("3087004500") || memAddress.equals("3087004508"))
				{
					System.out.println("in else");
					System.out.println("memAddressStart: " + memAddressStart);
					System.out.println("Address: " + memAddress + ", Binary: " + memAddressBinary + ", setBinry: " + setIndexBinary);
					System.out.println("Address: " + memAddress + ", Set: " + setIndexInt + ", Tag: " + tagString);
					System.out.println("Cache Index Lower: " + cacheLineIndexLower + ", Cache Index Uper: " + cacheLineIndexUpper);
					
				}
				*/
				cache[setIndexInt*associativity + setIndex[setIndexInt]] = tagString;
				
				setIndex[setIndexInt] = setIndex[setIndexInt] - 1 >= 0 ? setIndex[setIndexInt] - 1 : associativity-1;
				tempMiss++;
				
			}
		}
		//System.out.println("");
		if(Operation.equals("ST"))
		{
			if(tempMiss > 0)
			{
				storeMiss++;
				//System.out.println("MISS");
			}
			else
			{
				storeHit++;
				//System.out.println("HIT");
			}
		}
		else
		{
			if(tempMiss > 0)
			{
				loadMiss++;
				//System.out.println("MISS");
			}
			else
			{
				loadHit++;
				//System.out.println("HIT");
			}
		}
		
	}

	
	/****** counter retrival ******/
	
	public int getLoadHitCount()
	{
		return this.loadHit;
	}
	
	public float getLoadHitPercent()
	{
		return 100*(float)loadHit/((float)loadHit+(float)loadMiss);
	}
	
	public int getLoadMissCount()
	{
		return this.loadMiss;
	}
	
	public float getLoadMissPercent()
	{
		return 100*(float)loadMiss/((float)loadHit+(float)loadMiss);
	}
	
	public int getLoadCount()
	{
		return this.loadHit+this.loadMiss;
	}
	
	public float getLoadPercent()
	{
		return 100*((float)loadHit+(float)loadMiss)/((float)loadHit+(float)loadMiss);
	}
	
	public int getStoreHitCount()
	{
		return this.storeHit;
	}
	
	public float getStoreHitPercent()
	{
		return 100*(float)storeHit/((float)storeHit+(float)storeMiss);
	}
	
	public int getStoreMissCount()
	{
		return this.storeMiss;
	}

	public float getStoreMissPercent()
	{
		return 100*(float)storeMiss/((float)storeHit+(float)storeMiss);
	}
	
	public int getStoreCount()
	{
		return this.storeHit+this.storeMiss;
	}
	
	public float getStorePercent()
	{
		return 100*((float)storeHit+(float)storeMiss)/((float)storeHit+(float)storeMiss);
	}

	public int getTotalHitCount()
	{
		return loadHit + storeHit;
	}

	public float getTotalHitPercent()
	{
		return 100*(float)(loadHit + storeHit)/(float)(loadHit + storeHit + loadMiss + storeMiss);
	}
	
	public int getTotalMIssCount()
	{
		return loadMiss + storeMiss;
	}
	
	public float getTotalMissPercent()
	{
		return 100*(float)(loadMiss + storeMiss)/(float)(loadHit + storeHit + loadMiss + storeMiss);
	}

	public int getTotalCount()
	{
		return loadHit+loadMiss+storeHit+storeMiss;
	}
	
	public float getTotalPercent()
	{
		return 100*(loadHit+loadMiss+storeHit+storeMiss)/(loadHit+loadMiss+storeHit+storeMiss);
	}

}

