
public class OmittedCurlyBracesSample {

	public OmittedCurlyBracesSample() {

	}

	public void firstMethod() {
		int a = 2;
		
		if(a <= 0) a++; a++;
		
		System.out.println(a);
	}
	
	public void firstTransformedMethod() {
		int a = 2;
		
		if(a <= 0) {
			a++;
		}		
		a++;
		
		System.out.println(a);
	}
	
	public void secondMethod() {
		int a = 3;
		int b = 0;
		
		while(a < 3) b++; a++;
		
		System.out.println(a + " " + b);
	}
	
	public void secondTransformedMethod() {
		int a = 3;
		int b = 0;
		
		while(a < 3) {
			b++; 
		}	
		a++;
		
		System.out.println(a + " " + b);
	}
	
	
	public void thirdMethod(int a, int b) {
		int a = 3;
		
		for(int i = 0; i <= 3; i++) a++; a++;
		
		System.out.println(a);
	}
	
	public void thirdTransformedMethod() {
		int a = 3;
		
		for(int i = 0; i <= 3; i++) {
			a++; 
		}
		a++;
		
		System.out.println(a);
	}
	
	public void firstVariationMethod() {
		int a = 1;
		int b = 2;
		
		if(a == 1) b = 3; a = 2;
		
		for(int i = 0; i < 10; i++) {
			System.out.println(i);
		}
		
		for(int i; i < 0; i++)
			a = i;
			b = a;
		
		System.out.println(a);
	}
	
	public void secondVariationMethod() {
		int a = 2;
		int b;
		
		for(int i = 0; i < 10; i++)
			System.out.println(i);
		b = a;
		
		System.out.println(a);
	}
	
	public void thirdVariationMethod() {
		int a = 3;
		int b = 0;
		
		if(a <= 0) 
			a++; 
			b++;
		
		System.out.println(a + " " + b);
	}
	
	public void fourthVariationMethod() {
		int a = 3;
		int b = 2;
		
		if(a == b)
			System.out.println(a);
		else
			System.out.println(b);
			System.out.println(b);
		
		while(a < 3) 
			b++; 
		a++;
		
		System.out.println(a);
	}
	
	public void fifthVariationMethod(int a, int b) {
		if(a == b)
			System.out.println(a);
		else
			System.out.println(b);
		System.out.println(b);
		
		if(a == b)
			System.out.println(a);
		System.out.println(a);
		
		while(a < 4) 
			a++; 
		b++;
	}
	
}
