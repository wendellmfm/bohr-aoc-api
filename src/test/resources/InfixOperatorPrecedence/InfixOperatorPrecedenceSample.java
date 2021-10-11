
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
	
	public void secondTransformedMethod() {
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
}
