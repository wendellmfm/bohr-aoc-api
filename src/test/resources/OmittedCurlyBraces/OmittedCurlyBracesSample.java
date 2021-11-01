
public class OmittedCurlyBracesSample {

	public OmittedCurlyBracesSample() {

	}

	public void methodOne() {
		int a = 2;
		
		if(a <= 0) a++; a++;
		
		if(a <= 0) 
			a++; a++;
		
		System.out.println(a);
	}
	
	public void methodOneTransformed() {
		int a = 2;
		int b = 3;
		
		if(a <= 0) {
			a++;
		}		
		b++;
		
		if(a <= 0) { a++;} b++;
		
		if(a <= 0) {
			a++;} b++;
			
		if(a <= 0) 
			a++; 
			b++;
		
		System.out.println(a);
	}
	
	public void methodTwo() {
		int a = 3;
		int b = 0;
		
		while(a < 3) b++; a++;
		
		while(a < 3) 
			b++; a++;
		
		System.out.println(a + " " + b);
	}
	
	public void methodTwoTransformed() {
		int a = 3;
		int b = 0;
		
		while(a < 3) {
			b++; 
		}	
		a++;
		
		while(a < 3) { b++;} a++;
		
		while(a < 3) {
			b++;} a++;
		
		System.out.println(a + " " + b);
	}
	
	
	public void methodThree(int a, int b) {
		int a = 3;
		
		for(int i = 0; i <= 3; i++) a++; a++;
		
		for(int i = 0; i <= 3; i++) 
			a++; a++;
		
		System.out.println(a);
	}
	
	public void methodThreeTransformed() {
		int a = 3;
		
		for(int i = 0; i <= 3; i++) {
			a++; 
		}
		a++;
		
		for(int i = 0; i <= 3; i++) {a++;} a++;
		
		for(int i = 0; i <= 3; i++) {
			a++;} a++;
		
		System.out.println(a);
	}
	
	public void methodFour() {
		int a = 1;
		int b = 2;
		int c = 3;
		
		if(a == 1
				|| b == 2
				|| c == 3) b = 3; a = 2;
		
		if(a == 1
				|| b == 2
				|| c == 3) 
			b = 3; a = 2;
	}
	
	public void methodFourTransformed() {
		int a = 1;
		int b = 2;
		int c = 3;
			
		if(a == 1
				&& b == 2
				&& c == 3) {b = 3;} a = 2;
			
		if(a == 1
				&& b == 2
				&& c == 3) {
			b = 3;} a = 2;
	}
	
	public void methodFive() {
		int a = 1;
		int b = 2;
		int c = 3;
		
		if(a == 1
				|| b == 2
				|| c == 3) 
			b = 3;
		else if(a == 1
				|| b == 2
				|| c == 3)
			a = 2; c = 4;
	}
	
	public void methodFiveTransformed() {
		int a = 1;
		int b = 2;
		int c = 3;
		
		if(a == 1
				|| b == 2
				|| c == 3) 
			b = 3;
		else if(a == 1
				|| b == 2
				|| c == 3) {
			a = 2; } c = 4;	
	}
	
	public void methodSix() {
		int a = 1;
		int b = 2;
		
		for(int i; 
				i < 0; 
				i++) a = i; b = a;
		
		for(int i; 
				i < 0; 
				i++)
			a = i; b = a;
	}
	
	public void methodSixTransformed() {
		int a = 1;
		int b = 2;
		
		for(int i; 
				i < 0; 
				i++) {a = i;} b = a;
				
		for(int i; 
				i < 0; 
				i++) {
			a = i;} b = a;
	}
	
	public void methodSeven() {
		int a = 1;
		int b = 2;
		int c = 3;
		
		while(a < 1
				&& b < 2
				&& c < 3) b++; a++;
		
		while(a < 1
				&& b < 2
				&& c < 3) 
			b++; a++;
	}
	
	public void methodSevenTransformed() {
		int a = 1;
		int b = 2;
		int c = 3;
		
		while(a < 1
				&& b < 2
				&& c < 3) {	b++;} a++;
			
		while(a < 1
				&& b < 2
				&& c < 3) {
			b++;} a++;
	}
	
	
	public void methodEight() {
		int a = 2;
		int b = 3;
		
		while(b < 2)
			while(b < 3)
				while(b < 2)
					while(b < 3)
						System.out.println(a); b = a;
	}
	
	public void methodEightTransformed() {
		int a = 2;
		int b = 3;
		
		while(b < 2)
			while(b < 3)
				while(b < 2)
					while(b < 3) {System.out.println(a);} b = a;
	}
	
	public void methodNine() {
		int a = 2;
		int b;
		
		for(int i = 0; i < 10; i++)
			for(int i = 0; i < 10; i++)
				for(int i = 0; i < 10; i++)
					System.out.println(i); b = a;
	}
	
	public void methodNineTransformed() {
		int a = 2;
		int b;
		
		for(int i = 0; i < 10; i++)
			for(int i = 0; i < 10; i++)
				for(int i = 0; i < 10; i++) {System.out.println(i);} b = a;
	}
	
	public void methodTen() {
		int a = 0;
		List<String> arrayList = new ArrayList<>();
		arrayList.add("String 1");
		arrayList.add("String 2");
		
		for (String string : arrayList)
			System.out.println(string); a = a + 2;
	}
	
	public void methodEleven() {
		int a = 0;
		List<String> arrayList = new ArrayList<>();
		arrayList.add("String 1");
		arrayList.add("String 2");
		
		for (String string : arrayList)
			for (String string : arrayList)
				for (String string : arrayList)
					System.out.println(string); a = a + 2;
	}

}
