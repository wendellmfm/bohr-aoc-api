
public class ConditionalOperatorSample {

	public ConditionalOperatorSample() {

	}

	public void firstMethod(int a, int b) {
		System.out.println(a == b ? "foo" : "bar");
	}
	
	public String secondMethod(int a, int b) {
		String result = a == b ? "foo" : "bar";
		return result;
	}
}
