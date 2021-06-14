
public class OmittedCurlyBracesSample {

	public OmittedCurlyBracesSample() {

	}

	public void firstMethod(int a, int b) {
		if(a == b)
			System.out.println(a);
		else
			System.out.println(b);
		System.out.println(b);
		
		if(a == b)
			System.out.println(a);
		System.out.println(a);
	}
	
	public void secondMethod(int a, int b) {
		if(a == b) {
			System.out.println(a);
		} else {
			System.out.println(b);
		}
	}
	
	public void thirdMethod(int a, int b) {
		int a = 1;
		int b = 5;
		
		while(a < 3) a++; b++;

		while(a < 3) {
			a++; 
		}
		b++;
	}
	
	public void fourthMethod(int a, int b) {
		int a = 1;
		
		for (int i = 0; i < 3; i++) a++; a++;
		
		for (int i = 0; i < 3; i++) {
			a++; 
		}
		a++;
	}
}
