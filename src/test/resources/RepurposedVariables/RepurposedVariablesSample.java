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
	
	private void thirdMethod() {
		int v1[] = new int[5];
		v1[4] = 3;
		
		while (v1[4] > 0) {
			v1[3 - v1[4]] = v1[4];
			v1[4] = v1[4] - 1;
		}
		System.out.println(v1[1] + " " + v1[4]);
	}
	
	private void thirdMethodTransformed() {
		int v1[] = new int[5];
		int a = 5;
		
		while (a > 0) {
			v1[3 - v1[4]] = v1[4];
			a = a - 1;
		}
		System.out.println(v1[1] + " " + v1[4]);
	}
	
	private void firstVariationMethod() {
		int v1[] = new int[5];
		int v2[] = new int[5];
		v1[4] = 3;
		
		while (v1[4] > 0 || v2[4] > 0) {
			v2[3 - v1[3]] = v1[2];
			v2[3] = v1[4] - 1;
		}
		System.out.println(v1[1] + " " + v1[4]);
	}
	
	private void secondVariationMethod() {
		int v1[] = new int[5];
		int v2[] = new int[5];
		int a = 5;
		int b = 5;
		
		while (a > 0 || b > 0) {
			v2[3 - v1[3]] = v1[2];
			a = a - 1;
			b = b - 1;
		}
		System.out.println(v1[1] + " " + v1[4]);
	}
	
	private void thirdVariationMethod() {
		int v1[] = new int[5];
		v1[4] = 3;
		for (int i = 0; v1[i] > 0; i++) {
			v1[3 - v1[4]] = v1[4];
			v1[4] = v1[4] - 1;
		}
		System.out.println(v1[1] + " " + v1[4]);
	}
	
	private void fourthMethod() {
		int v1[] = new int[5];

		for (int i = 0; i > 0; i++) {
			v1[3 - v1[4]] = v1[4];
			v1[4] = v1[4] - 1;
		}
		System.out.println(v1[1] + " " + v1[4]);
	}
}