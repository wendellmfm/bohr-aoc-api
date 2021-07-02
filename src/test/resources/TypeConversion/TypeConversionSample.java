
public class TypeConversionSample {

	public TypeConversionSample() {

	}
	
	public void firstMethod(int a, int b) {
		float a = 1.99f;
		int b = (int) a;
		
		System.out.println(b);
	}
	
	public void secondMethod() {
		float a = 1.99f;
		int b = (int) Math.floor(a);
		
		System.out.println(a);
	}
	
	public void thirdMethod() {
		int a = 288;
		byte b = (byte) a;
		
		System.out.println(b);
	}
	
	public void fourthMethod() {
		int a = 288;
		byte b = (byte) (a % 256);
		
		System.out.println(b);
	}
	
}
