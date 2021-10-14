public class RepurposedVariablesSample {

	public RepurposedVariablesSample() {

	}

	public void firstMethod() {
		int a = 3;
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; i < 2; i++) {
				a = 4 * i + j;
			}
		}
		
		System.out.println(a);
	}
	
	public void firstMethodTransformed() {
		int a = 3;
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 2; j++) {
				a = 4 * i + j;
			}
		}
		
		System.out.println(a);
	}
	
	public void secondMethod() {
		int a = 3;
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; i < 2; i+=2) {
				a = 4 * i + j;
			}
		}
		
		System.out.println(a);
	}
	
	public void secondMethodTransformed() {
		int a = 3;
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 2; j+=2) {
				a = 4 * i + j;
			}
		}
		
		System.out.println(a);
	}
}