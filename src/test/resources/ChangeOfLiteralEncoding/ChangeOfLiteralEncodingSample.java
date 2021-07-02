
public class ChangeOfLiteralEncodingSample {

	public ChangeOfLiteralEncodingSample() {

	}

	public void firstMethod() {
		int a = 013;
		
		System.out.println(a);
	}
	
	public void secondMethod() {
		int a;
		a = 013;
		
		System.out.println(a);
	}
	
	public void thirdMethod() {
		int a = Integer.parseInt("13", 8);
		
		System.out.println(a);
	}
	
	public void fourthMethod() {
		int a = 11 & 32;
		
		System.out.println(a);
	}
	
	public void fifthMethod() {
		int a = 0b1100 & 0b0011;
		
		System.out.println(a);
	}
	
	public void sixthMethod() {
		float b = 11;
		float c = 32;
		int a = b & c;
		
		System.out.println(a);
	}
	
}
