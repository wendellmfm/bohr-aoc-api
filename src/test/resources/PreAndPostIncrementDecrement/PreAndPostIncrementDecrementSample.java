
public class PreAndPostIncrementDecrementSample {

	public PreAndPostIncrementDecrementSample() {

	}
	
	public void firstMethod() {
		int a = 1;
		int b = 3 + a++;
		
		System.out.println(a + " " + b);
	}
	
	public void secondMethod() {
		int a = 0;
		if(a++ == 0) {
			System.out.println("true ");
		} else {
			System.out.println("false ");
		}
		System.out.println(a);
	}
	
	public void thirdMethod() {
		int a = 2;
		if(a-- == 1) {
			System.out.println("true ");
		} else {
			System.out.println("false ");
		}
		System.out.println(a);
	}
	
	public void fourthMethod() {
		int a = 2;
		int b = ++a - 2;
		
		System.out.println(a + " " + b);
	}
	
	public void fifthMethod() {
		int a = 2;
		if(--a == 1) {
			System.out.println("true ");
		} else {
			System.out.println("false ");
		}
		System.out.println(a);
	}
	
	public void sixthMethod() {
		int a = 2;
		int b = --a + 3;
		
		System.out.println(a + " " + b);
	}
	
}
