
public class OmittedCurlyBracesSample {

	public OmittedCurlyBracesSample() {

	}

	public void firstMethod() {
		int a = 2;
		
		if(a <= 0) a++; a++;
		
		System.out.println(a);
	}
	
	public void secondMethod() {
		int a = 3;
		int b = 0;
		
		while(a < 3) b++; a++;
		
		System.out.println(a + " " + b);
	}
	
	public void thirdMethod(int a, int b) {
		int a = 3;
		
		for(int i = 0; i <= 3; i++) a++; a++;
		
		System.out.println(a);
	}
	
}
