import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;


public class MainProgram 
{
	
	public static void main(String[] args) 
	{
		int cacheSizeKB = 32;
		int cacheLineSizeB = 32;
		int associativity = 4;
 		String inputPath = "sample9.in";
		String outputFileName = "Sample11.out";
		Cache cache = new Cache(cacheSizeKB, cacheLineSizeB, associativity);
		
		try
		{
           
            FileInputStream fileInputStream = new FileInputStream(inputPath);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(dataInputStream));
            
            String line;
            while((line = bufferReader.readLine()) != null)
            {
        		
        		if(!line.equals(""))
        		{
        			String[] parts = line.split(" ");
        			parts[1] = parts[1].substring(0, parts[1].length()-1);
        			
        			cache.accessCache(parts[0], parts[1], parts[2]);
        		}
            }
            
            FileWriter filewriter = new FileWriter(outputFileName);
            BufferedWriter bufferedWriter = new BufferedWriter(filewriter);
            
            
            System.out.println("Load-Hits: " + cache.getLoadHitCount() + ", " + cache.getLoadHitPercent() + "%");
            System.out.println("Load-Misses: " + cache.getLoadMissCount() + ", " + cache.getLoadMissPercent() + "%");
            System.out.println("Load-Accesses: " + cache.getLoadCount() + ", " + cache.getLoadPercent() + "%\n");
            
            System.out.println("Store-Hits: " + cache.getStoreHitCount() + ", " + cache.getStoreHitPercent() + "%");
            System.out.println("Store-Misses: " + cache.getStoreMissCount() + ", " + cache.getStoreMissPercent() + "%");
            System.out.println("Store-Accesses: " + cache.getStoreCount() + ", " + cache.getStorePercent() + "%\n");
            
            System.out.println("Total-Hits: " + cache.getTotalHitCount() + ", " + cache.getTotalHitPercent() + "%");
            System.out.println("Total-Misses: " + cache.getTotalMIssCount() + ", " + cache.getTotalMissPercent() + "%");
            System.out.println("Total-Accesses: " + cache.getTotalCount() + ", " + cache.getTotalPercent() + "%\n");
            
            /******** write to file *********/
            bufferedWriter.write("Load-Hits: " + cache.getLoadHitCount() + ", " + cache.getLoadHitPercent() + "%\n");
            bufferedWriter.write("Load-Misses: " + cache.getLoadMissCount() + ", " + cache.getLoadMissPercent() + "%\n");
            bufferedWriter.write("Load-Accesses: " + cache.getLoadCount() + ", " + cache.getLoadPercent() + "%\n\n");
            
            bufferedWriter.write("Store-Hits: " + cache.getStoreHitCount() + ", " + cache.getStoreHitPercent() + "%\n");
            bufferedWriter.write("Store-Misses: " + cache.getStoreMissCount() + ", " + cache.getStoreMissPercent() + "%\n");
            bufferedWriter.write("Store-Accesses: " + cache.getStoreCount() + ", " + cache.getStorePercent() + "%\n\n");
            
            bufferedWriter.write("Total-Hits: " + cache.getTotalHitCount() + ", " + cache.getTotalHitPercent() + "%\n");
            bufferedWriter.write("Total-Misses: " + cache.getTotalMIssCount() + ", " + cache.getTotalMissPercent() + "%\n");
            bufferedWriter.write("Total-Accesses: " + cache.getTotalCount() + ", " + cache.getTotalPercent() + "%\n");
            
            bufferedWriter.close();
        }
		catch (Exception e)
		{
            System.out.println("Error: MainProgram.main() -> " + e.getMessage());
        }
		
	}
}
