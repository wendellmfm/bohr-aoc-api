
public class ConditionalOperatorSample {

	public ConditionalOperatorSample() {

	}

	public void methodOne() {
		int a = 4;
		
		int b = a == 3 ? 2 : 1;
		
		System.out.println(b);
	}
	
	public void methodOneTransformed() {
		int a = 4;
		int b = 3;
		int c;
		
		if(a == 3) {
			c = 2;
		} else {
			c = 1;
		}
		
		System.out.println(c);
	}

	public void methodTwo() {
		int a = 3;
		int b = 5;
		int c = 2;
		
		int d = a == 3 ? b : c;
		
		System.out.println(d);
	}

	public void methodTwoTransformed() {
		int a = 3;
		int b = 5;
		int c = 2;
		
		int d;
		if(a == 2) {
			d = b;
		} else {
			d = c;
		}
		
		System.out.println(d);
	}
	
	public void methodThree() {
		int a = 2;
		int b = 3;
		int c = 1;
		
		int d = a == 3 ? b : c;
		
		System.out.println(d);
	}
	
	public void methodThreeTransformed() {
		int a = 2;
		int b = 3;
		int c = 1;
		
		int d;
		if(a == 3) {
			d = b;
		} else {
			d = c;
		}
		
		System.out.println(d);
	}
	
	public int methodFive(int i) {
		int a = i;
		int b = 2;
		int c = 3;
		
		return  a == 3 ? b : c;
	}
	
	public void methodSix() {
		int a = 2;
		int b = 3;
		int c = 1;
		
		methodFive(a == 3 ? b : c);
	}
	
}
