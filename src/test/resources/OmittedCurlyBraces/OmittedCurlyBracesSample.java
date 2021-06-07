
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
}
