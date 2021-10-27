
public class InfixOperatorPrecedenceSample {

	public InfixOperatorPrecedenceSample() {

	}

	public void methodOne() {
		int a;
		
		a = 2 - 4 / 2;
		
		System.out.println(a);
	}
	
	public void methodOneTransformed() {
		int a;
		
		a = 2 - (4 / 2);
		
		System.out.println(a);
	}
	
	public void methodTwo() {
		int a = 2 * 4 + 2;
		
		System.out.println(a);
	}
	
	public void methodTwoTransformed() {
		int a = (2 * 4) + 2;
		
		System.out.println(a);
	}
	
	public void methodThree() {
		String line = "The cat is black";
		
		boolean a = line.contains("dog");
		boolean b = line.contains("cat");
		boolean c = line.contains("black");
		
		if(a && b || c) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
	
	public void methodThreeTransformed() {
		String line = "The cat is black";
		
		boolean a = line.contains("dog");
		boolean b = line.contains("cat");
		boolean c = line.contains("black");
		
		if(a && (b || c)) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
	
	public void stringConcatenationMethod() {
		int a = 1;
		int b = 2;
		int c = 3;
		int d = 4;
		
		System.out.println(a * b + " mixed threads posted " + c + " events each in "
                + d + "ms");
	}
}
