
public class TypeConversionSample {

	public TypeConversionSample() {

	}
	
	public void shortToByteMethod() {
		short a = 288;
		byte b = (byte) a;
		
		System.out.println(b);
	}
	
	public void shortToByteTransformedMethod() {
		short a = 288;
		byte b = (byte) (a % 256);
		
		System.out.println(b);
	}
	
	public void intToByteMethod() {
		int a = 288;
		byte b = (byte) a;
		
		System.out.println(b);
	}
	
	public void intToByteTransformedMethod() {
		int a = 288;
		byte b = (byte) (a % 256);
		
		System.out.println(b);
	}
	
	public void longToByteMethod() {
		long a = 288;
		byte b = (byte) a;
		
		System.out.println(b);
	}
	
	public void longToByteTransformedMethod() {
		long a = 288;
		byte b = (byte) (a % 256);
		
		System.out.println(b);
	}
	
	public void floatToByteMethod() {
		float a = 288;
		byte b = (byte) a;
		
		System.out.println(b);
	}
	
	public void floatToByteTransformedMethod() {
		float a = 288;
		byte b = (byte) (a % 256);
		
		System.out.println(b);
	}
	
	public void doubleToByteMethod() {
		double a = 288;
		byte b = (byte) a;
		
		System.out.println(b);
	}
	
	public void doubleToByteTransformedMethod() {
		double a = 288;
		byte b = (byte) (a % 256);
		
		System.out.println(b);
	}
	
	public void intToShortMethod() {
		int a = 32768;
		short b = (short) a;
		
		System.out.println(b);
	}
	
	public void intToShortTransformedMethod() {
		int a = 32768;
		short b = (short) (a % 65536);
		
		System.out.println(b);
	}
	
	public void longToShortMethod() {
		long a = 32768;
		short b = (short) a;
		
		System.out.println(b);
	}
	
	public void longToShortTransformedMethod() {
		long a = 32768;
		short b = (short) (a % 65536);
		
		System.out.println(b);
	}
	
	public void floatToShortMethod() {
		float a = 32768;
		short b = (short) a;
		
		System.out.println(b);
	}
	
	public void floatToShortTransformedMethod() {
		float a = 32768;
		short b = (short) (a % 65536);
		
		System.out.println(b);
	}
	
	public void doubleToShortMethod() {
		double a = 32768;
		short b = (short) a;
		
		System.out.println(b);
	}
	
	public void doubleToShortTransformedMethod() {
		double a = 32768;
		short b = (short) (a % 65536);
		
		System.out.println(b);
	}
	
	public void floatToIntMethod() {
		float a = 1.99f;
		int b = (int) a;
		
		System.out.println(b);
	}
	
	public void floatToIntTransformedMethod() {
		float a = 1.99f;
		int b = (int) Math.floor(a);
		
		System.out.println(a);
	}
	
	public void longToIntMethod() {
		long a = 1.99f;
		int b = (int) a;
		
		System.out.println(b);
	}
	
	public void longToIntTransformedMethod() {
		long a = 1.99f;
		int b = (int) Math.floor(a);
		
		System.out.println(a);
	}
	
	public void doubleToIntMethod() {
		double a = 1.99f;
		int b = (int) a;
		
		System.out.println(b);
	}
	
	public void doubleToIntTransformedMethod() {
		double a = 1.99f;
		int b = (int) Math.floor(a);
		
		System.out.println(a);
	}
	
	public void floatToLongMethod() {
		double a = 1.99f;
		int b = (int) a;
		
		System.out.println(b);
	}
	
	public void floatToLongTransformedMethod() {
		double a = 1.99f;
		int b = (int) Math.floor(a);
		
		System.out.println(a);
	}
	
	public void doubleToLongMethod() {
		double a = 1.99f;
		int b = (int) a;
		
		System.out.println(b);
	}
	
	public void doubleToLongTransformedMethod() {
		double a = 1.99f;
		int b = (int) Math.floor(a);
		
		System.out.println(a);
	}
	
	
}
