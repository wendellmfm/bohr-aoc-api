
public class ChangeOfLiteralEncodingSample {

	public ChangeOfLiteralEncodingSample() {

	}

	public void firstMethod() {
		int a = 013;
		
		System.out.println(a);
	}
	
	public void firstTransformedMethod() {
		int a = Integer.parseInt("13", 8);
		
		System.out.println(a);
	}
	
	public void secondMethod() {
		int a;
		a = 013;
		
		System.out.println(a);
	}
	
	public void secondTransformedMethod() {
		int a;
		a = Integer.parseInt("13", 8);
		
		System.out.println(a);
	}
	
	public int thirdMethod() {
		int a = 11 & 32;
		
		return a;
	}
	
	public void thirdTransformedMethod() {
		int a = 0b1100 & 0b0011;
		
		System.out.println(a);
	}
	
	public void firstVariationMethod() {
		int a;
		a = 018;
		
		int b = 019;
		
		System.out.println(a + b);
	}
	
	public int secondVariationMethod() {
		float b = 11;
		float c = 32;
		int a = b & c;
		
		return a;
	}
	
}
