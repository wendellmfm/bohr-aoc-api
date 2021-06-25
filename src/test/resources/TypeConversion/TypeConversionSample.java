
public class TypeConversionSample {

	public TypeConversionSample() {

	}
	
	public void firstMethod(int a, int b) {
		float a = 1.99f;
		int b = (int) a;
		
		System.out.println(b);
	}

	
	public void secondMethod() {
		float a = 1.99f;
		int b = (int) Math.floor(a);
		
		System.out.println(a);
	}
	
}
