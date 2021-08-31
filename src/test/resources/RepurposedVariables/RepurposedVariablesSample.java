
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
	
}
