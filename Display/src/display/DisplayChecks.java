package display;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DisplayChecks {
	
	private static final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
	private static final int redMask   = 0b1111100000000000;
	private static final int greenMask = 0b0000011111100000;
	private static final int blueMask  = 0b0000000000011111;
	private static final String hstring = "0123456789ABCDEF";  
	
	public static void main(String args[]) {
		String topLeft = "0x0000";
		String topRight = "0xFFFF";
		String bottomLeft;
		String bottomRight;
		if(args.length >2) {
			bottomLeft =args[2];
			if(args.length >3) {
				 bottomRight = args[3];
			}else {
				 bottomRight = topRight;
			}
		}else {
			bottomLeft = "0x0000";//topLeft;
			bottomRight ="0xFFFF";//topRight;
		}
		int topLeftInteger =  convertToDecimal(topLeft);
		int topRightInteger = convertToDecimal(topRight);
		int bottomLeftInteger = convertToDecimal(bottomLeft);
		int bottomRightInteger = convertToDecimal(bottomRight);
		
		int width = 16;
		int height = 9;
		
		RGB topLeftRGB = extractRGB(topLeftInteger);
		
		RGB topRightRGB = extractRGB(topRightInteger);
		RGB bottomLeftRGB = extractRGB(bottomLeftInteger);
		RGB bottomRightRGB = extractRGB(bottomRightInteger);
		
		int[] leftCorner = new int[height];
		int[] rightCorner = new int[height];
		
		if(topLeftInteger != bottomLeftInteger) {
			//System.out.println("Top left Integer -"+topLeftInteger+"Bottom Left Integer is "+ bottomLeftInteger +"top Left RGB"+ topLeftRGB +"Bottom Left RGB is "+ bottomLeftRGB);
			for(int i=0;i<height;i++) {
				Double fraction = Double.valueOf(i)/Double.valueOf(height-1);
				int nextValue = calculatePixel(topLeftRGB,bottomLeftRGB,fraction);
				leftCorner[i] = nextValue;
				
			}
		}else {
			for(int i=0;i<height;i++) {
				leftCorner[i] = topLeftInteger;
			}
		}
		if(topRightInteger != bottomRightInteger) {
			//System.out.println("Top Right Integer -"+topRightInteger+"Bottom Right Integer is "+ bottomRightInteger);
			
			for(int i=0;i<height;i++) {
				Double fraction = Double.valueOf(i)/Double.valueOf(height-1);
				int nextValue = calculatePixel(topRightRGB,bottomRightRGB,fraction);
				rightCorner[i] = nextValue;
				//System.out.println(nextValue);
			}
		} else {
			for(int i=0;i<height;i++) {
				rightCorner[i] = bottomRightInteger;
				//System.out.println(rightCorner[i]);
			}
		}
		for(int j=0;j<height;j++) {
			int leftRGBDecimal = leftCorner[j];
			int rightRGBDecimal = rightCorner[j];
			RGB leftRGB = extractRGB(leftRGBDecimal);
			RGB rightRGB = extractRGB(rightRGBDecimal);
			//System.out.println("Left RGB"+leftRGB+"Right RGB"+ rightRGB);
		//pixel with array size of width
		for(int i=0;i<width;i++) {
			
			Double fraction = Double.valueOf(i)/Double.valueOf(width-1);
			
			Double nextRed = ((rightRGB.getRed() - leftRGB.getRed()) * fraction) + leftRGB.getRed();
			Double nextBlue = ((rightRGB.getBlue() - leftRGB.getBlue()) * fraction) + leftRGB.getBlue();
			Double nextGreen = ((rightRGB.getGreen() - leftRGB.getGreen()) * fraction) + leftRGB.getGreen();
			int nextRedInt = (int) Math.round(nextRed);
			int nextBlueInt = (int) Math.round(nextBlue);
			int nextGreenInt = (int) Math.round(nextGreen);
			RGB nextrgb = new RGB(nextRedInt,nextGreenInt,nextBlueInt);
			int nextRGBInDecimal = extractIntFromRGB(nextrgb);
		
			System.out.println(decToHexa(nextRGBInDecimal));
			
			}
		
		}
		
		
		 //System.out.println("red value is "+rgb.getRed() + " green value is "+ rgb.getGreen() + " blue value is "+ rgb.getBlue());
	      
	}

	public static int convertToDecimal(String arg) {
		if(isNumeric(arg)) {
			return Integer.parseInt(arg); 
		} else {
			return hexToDecimal(arg);
		}
	}

	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false; 
	    }
	    return pattern.matcher(strNum).matches();
	}
	//https://www.geeksforgeeks.org/program-decimal-hexadecimal-conversion/
	static String decToHexa(int n)
    {
        // char array to store hexadecimal number
        char[] hexaDeciNum = new char[100];
 
        // counter for hexadecimal number array
        int i = 0;
        while (n != 0) {
            // temporary variable to store remainder
            int temp = 0;
 
            // storing remainder in temp variable.
            temp = n % 16;
 
            // check if temp < 10
            if (temp < 10) {
            	
                hexaDeciNum[i] = (char)(temp + 48);
                i++;
            }
            else {
            	
                hexaDeciNum[i] = (char)(temp + 55);
                i++;
            }
 
            n = n / 16;
        }
 
        // printing hexadecimal number array in reverse
        // order
        String reversedValue = String.valueOf(hexaDeciNum);
        return "0x"+new StringBuilder(reversedValue).reverse().toString().trim();
        
    }
	
	public static int hexToDecimal(String hexnum){  
		hexnum = hexnum.substring(2,hexnum.length());// Removing 0x -- can put a condition only to do it if present
		hexnum = hexnum.toUpperCase();  
		int num = 0;  
		for (int i = 0; i < hexnum.length(); i++)  
		{  
			char ch = hexnum.charAt(i);  
			int n = hstring.indexOf(ch);  
			num = 16*num + n;  
		}  
		return num;  
	   }  
	
	public static RGB extractRGB(int hexAsInteger) {
		//int hexAsInteger = Integer.parseInt(hexaValue.substring(2,hexaValue.length()), 16); // convert the Hex value to int
      
	       int red = (hexAsInteger & redMask) >> 11; // keep only red bits
	       int green = (hexAsInteger & greenMask) >> 5; // keep only green bits
	       int blue = hexAsInteger & blueMask; // keep only blue bits
           RGB rgb = new RGB(red,green,blue);
	      
		return rgb;
	}
	
	public static int extractIntFromRGB(RGB rgb) {
		int redValue = rgb.getRed()<<11;
		int greenValue = rgb.getGreen()<<5;
		return redValue+greenValue+rgb.getBlue();
	}
 
	public static int calculatePixel( RGB color1, RGB color2, double fraction) {
		Double nextRed = ((color2.getRed() - color1.getRed()) * fraction) + color1.getRed();
		Double nextBlue = ((color2.getBlue() - color1.getBlue()) * fraction) + color1.getBlue();
		Double nextGreen = ((color2.getGreen() - color1.getGreen()) * fraction) + color1.getGreen();
		int nextRedInt = (int) Math.round(nextRed);
		int nextBlueInt = (int) Math.round(nextBlue);
		int nextGreenInt = (int) Math.round(nextGreen);
		RGB nextrgb = new RGB(nextRedInt,nextGreenInt,nextBlueInt);
		
		int nextRGBInDecimal = extractIntFromRGB(nextrgb);
		//System.out.print("next RGB is "+ nextrgb +"nextRGB In Decimal is "+ nextRGBInDecimal);
		return nextRGBInDecimal;
		
	}
}

