
public class ConditionalOperatorSample {

	public ConditionalOperatorSample() {

	}

	public void firstMethod() {
		int a = 4;
		
		int b = a == 3 ? 2 : 1;
		
		System.out.println(b);
	}
	
	public String secondMethod(int a, int b) {
		int a = 3;
		int b = 5;
		int c = 2;
		
		int d = a == 3 ? b : c;
		
		System.out.println(d);
	}
	
	public String thirdMethod(int a, int b) {
		int a = 2;
		int b = 3;
		int c = 1;
		
		int d = a == 3 ? b : c;
		
		System.out.println(d);
	}
}
