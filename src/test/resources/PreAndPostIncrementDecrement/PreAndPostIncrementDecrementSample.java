
public class PreAndPostIncrementDecrementSample {

	public PreAndPostIncrementDecrementSample() {

	}
	
	public void firstPostIncrementDecrementMethod() {
		int a = 2;
		int b = 3 + a++;
		
		System.out.println(a + " " + b);
	}
	
	public void firstPostIncrementDecrementTransformedMethod() {
		int a = 2; 
		int b;
		b = a + 3;
		a++;
		
		System.out.println(a + " " + b);
	}
	
	public void secondPostIncrementDecrementMethod() {
		int a = 0;
		if(a++ == 0) {
			System.out.println("true ");
		} else {
			System.out.println("false ");
		}
		System.out.println(a);
	}
	
	public void secondPostIncrementDecrementTransformedMethod() {
		int a = 0;
		if(a == 0) {
			System.out.println("true ");
		} else {
			System.out.println("false ");
		}
		a++;
		System.out.println(a);
	}
	
	public void thirdPostIncrementDecrementMethod() {
		int a = 2;
		if(a-- == 1) {
			System.out.println("true ");
		} else {
			System.out.println("false ");
		}
		System.out.println(a);
	}
	
	public void thirdPostIncrementDecrementTransformedMethod() {
		int a = 2;
		if(a == 1) {
			System.out.println("true ");
		} else {
			System.out.println("false ");
		}
		a--;
		System.out.println(a);
	}
	
	public void postIncrementDecrementVariationMethod() {
		int a = 5;
		int b = 8;
		
		while(b-- > 0) {
			System.out.println(a-- + 1);
		}
	}
	
	public void firstPreIncrementDecrementMethod() {
		int a = 2;
		int b = ++a - 2;
		
		System.out.println(a + " " + b);
	}
	
	public void firstPreIncrementDecrementTransformedMethod() {
		int a = 5;
		int b;
		++a;
		b = 5 - a;
		
		System.out.println(a + " " + b);
	}
	
	public void secondPreIncrementDecrementMethod() {
		int a = 2;
		if(--a == 1) {
			System.out.println("true ");
		} else {
			System.out.println("false ");
		}
		System.out.println(a);
	}
	
	public void secondPreIncrementDecrementTransformedMethod() {
		int a = 2;
		--a;
		if(a == 1) {
			System.out.println("true ");
		} else {
			System.out.println("false ");
		}
		System.out.println(a);
	}
	
	public void thirdPreIncrementDecrementMethod() {
		int a = 2;
		int b = --a + 3;
		
		System.out.println(a + " " + b);
	}
	
	public void thirdPreIncrementDecrementTransformedMethod() {
		int a = 6;
		int b = 9 - a;
		--a;
		
		System.out.println(a + " " + b);
	}
}
