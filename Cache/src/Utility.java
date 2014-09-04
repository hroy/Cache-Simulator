
public class Utility 
{
	public static long hexToInt(String s) 
	{
		String digits = "0123456789ABCDEF";
		s = s.toUpperCase();
		long val = 0;
		for (int i = 0; i < s.length(); i++) 
		{
			char c = s.charAt(i);
			int d = digits.indexOf(c);
			val = 16 * val + d;
		}
		return val;
	}

	public static String intToHex(String s) 
	{
		long no = Long.parseLong(s);
		String hex = Long.toHexString(no);
		while (hex.length() < 8) 
		{
			hex = "0" + hex;
		}
		
		return hex;
	}
	

	public static String hexToBinary(String s) 
	{
		String digits = "0123456789ABCDEF";
		String[] binaryArray = { "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111" };
		String result = "";
		s = s.toUpperCase();
		for (int i = 0; i < s.length(); i++) 
		{
			char c = s.charAt(i);
			result += binaryArray[digits.indexOf(c)];
		}
		
		return result;
	}

	public static long binaryToInt(String s) 
	{
		String digits = "01";
		long val = 0;
		for (int i = 0; i < s.length(); i++) 
		{
			char c = s.charAt(i);
			int d = digits.indexOf(c);
			val = 2 * val + d;
		}
		
		return val;
	}

	public static String binaryToHex(String s) 
	{
		long integer = binaryToInt(s);
		return Long.toHexString(integer);
	}

	public static String intToHex(long integer) 
	{
		String hexString;
		hexString = Long.toHexString(integer);
		while (hexString.length() < 8) 
		{
			hexString = "0" + hexString;
		}

		return hexString;
	}

	public static String intToBinary(long integer) {
		String temp = intToHex(integer);
		return hexToBinary(temp);
	}
}
