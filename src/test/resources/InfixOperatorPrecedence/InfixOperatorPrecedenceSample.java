
public class InfixOperatorPrecedenceSample {

	public InfixOperatorPrecedenceSample() {

	}

	public void firstMethod() {
		int a;
		
		a = 2 - 4 / 2;
		
		System.out.println(a);
	}
	
	public void firstTransformedMethod() {
		int a;
		
		a = 2 - (4 / 2);
		
		System.out.println(a);
	}
	
	public void secondMethod() {
		int a = 2 * 4 + 2;
		
		System.out.println(a);
	}
	
	public void secondTransformedMethod() {
		int a = (2 * 4) + 2;
		
		System.out.println(a);
	}
	
	public void thirdMethod() {
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
	
	public void thirdransformedMethod() {
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
