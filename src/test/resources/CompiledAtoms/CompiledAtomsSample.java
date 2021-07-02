
public class CompiledAtomsSample {
	
	public void firstPostIncrementDecrementMethod() {
		int a = 2;
		int b = 3 + a++;
		
		System.out.println(a + " " + b);
	}
	
	public void firstPostIncrementDecrementTransformedMethod() {
		int a = 2; 
		int b;
		b = a + 3;
		a++;
		
		System.out.println(a + " " + b);
	}
	
	public void secondPostIncrementDecrementMethod() {
		int a = 0;
		if(a++ == 0) {
			System.out.println("true ");
		} else {
			System.out.println("false ");
		}
		System.out.println(a);
	}
	
	public void secondPostIncrementDecrementTransformedMethod() {
		int a = 0;
		if(a == 0) {
			System.out.println("true ");
		} else {
			System.out.println("false ");
		}
		a++;
		System.out.println(a);
	}
	
	public void thirdPostIncrementDecrementMethod() {
		int a = 2;
		if(a-- == 1) {
			System.out.println("true ");
		} else {
			System.out.println("false ");
		}
		System.out.println(a);
	}
	
	public void thirdPostIncrementDecrementTransformedMethod() {
		int a = 2;
		if(a == 1) {
			System.out.println("true ");
		} else {
			System.out.println("false ");
		}
		a--;
		System.out.println(a);
	}
	
	public void postIncrementDecrementVariationMethod() {
		int a = 5;
		int b = 8;
		
		while(b-- > 0) {
			System.out.println(a-- + 1);
		}
	}
	
	public void firstPreIncrementDecrementMethod() {
		int a = 2;
		int b = ++a - 2;
		
		System.out.println(a + " " + b);
	}
	
	public void firstPreIncrementDecrementTransformedMethod() {
		int a = 5;
		int b;
		++a;
		b = 5 - a;
		
		System.out.println(a + " " + b);
	}
	
	public void secondPreIncrementDecrementMethod() {
		int a = 2;
		if(--a == 1) {
			System.out.println("true ");
		} else {
			System.out.println("false ");
		}
		System.out.println(a);
	}
	
	public void secondPreIncrementDecrementTransformedMethod() {
		int a = 2;
		--a;
		if(a == 1) {
			System.out.println("true ");
		} else {
			System.out.println("false ");
		}
		System.out.println(a);
	}
	
	public void thirdPreIncrementDecrementMethod() {
		int a = 2;
		int b = --a + 3;
		
		System.out.println(a + " " + b);
	}
	
	public void thirdPreIncrementDecrementTransformedMethod() {
		int a = 6;
		int b = 9 - a;
		--a;
		
		System.out.println(a + " " + b);
	}
	
	public void firstConditionalOperatorMethod() {
		int a = 4;
		
		int b = a == 3 ? 2 : 1;
		
		System.out.println(b);
	}
	
	public String firstConditionalOperatorTransformedMethod() {
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
	
	public String secondConditionalOperatorMethod() {
		int a = 3;
		int b = 5;
		int c = 2;
		
		int d = a == 3 ? b : c;
		
		System.out.println(d);
	}
	
	public String secondConditionalOperatorTransformedMethod() {
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
	
	public String thirdConditionalOperatorMethod() {
		int a = 2;
		int b = 3;
		int c = 1;
		
		int d = a == 3 ? b : c;
		
		System.out.println(d);
	}
	
	public String thirdConditionalOperatorTransformedMethod() {
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
	
	public void firstArithmeticAsLogicMethod(){
		int a = 8;
		
		if((a - 3) * (7 - a) <= 0) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
	
	public void firstArithmeticAsLogicTransformedMethod(){
		int a = 8;
		
		if(3 <= a || a >= 7) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
	
	public void secondArithmeticAsLogicMethod(){
		int a = 2;
		
		if((a - 2) * (6 - a) > 0) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
	
	public void secondArithmeticAsLogicTransformedMethod(){
		int a = 2;
		
		if(a < 2 || 6 < a) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
	
	public void thirdArithmeticAsLogicMethod(){
		int a = 5;
		
		if(a + 5 != 0) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
	
	public void thirdArithmeticAsLogicTransformedMethod(){
		int a = 5;
		
		if(a != -5) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
	
	public void firstLogicAsControlFlowMethod(){
		int a = 1;
		int b = 5;
		
		if(++a > 0 || ++b > 0) {
			a = a * 2;
			b = b * 2;
		}
		
		System.out.println(a + " " + b);
	}
	
	public void firstLogicAsControlFlowTransformedMethod() {
		int a = 2;
		int b = 4;
		
		if(++a > 0) {
			a = a * 2;
			b = b * 2;
		} else if(++b > 0) {
			a = a * 2;
			b = b * 2;
		}
		
		System.out.println(a + " " + b);
	}
	
	public void secondLogicAsControlFlowMethod(){
		int a = 1;
		int b = 5;
		
		boolean test = a == b && ++a > 0 || ++b > 0;
		
		System.out.println(a + " " + b);
	}
	
	public void secondLogicAsControlFlowTransformedMethod(){
		int a = 1;
		int b = 5;
		
		if(a == b) {
			++a;
		} else {
			++b;
		}
		
		System.out.println(a + " " + b);
	}
	
	public void thirdLogicAsControlFlowMethod(){
		int a = 1;
		int b = 5;
		int c = 0;
		
		while(c < b && ++a > 0) {
			c++;
		}
		
		System.out.println(a + " " + b + " " + c);
	}
	
	public void thirdLogicAsControlFlowTransformedMethod(){
		int a = 1;
		int b = 5;
		int c = 0;
		
		while(a != b) {
			a++;
			if(!(a > 0)) {
				break;
			}		
			c++;
		}
		
		System.out.println(a + " " + b + " " + c);
	}
	
	public void firstChangeOfLiteralEncodingMethod() {
		int a = 013;
		
		System.out.println(a);
	}
	
	public void firstChangeOfLiteralEncodingTransformedMethod() {
		int a = Integer.parseInt("13", 8);
		
		System.out.println(a);
	}
	
	public void secondChangeOfLiteralEncodingMethod() {
		int a;
		a = 013;
		
		System.out.println(a);
	}
	
	public void secondChangeOfLiteralEncodingTransformedMethod() {
		int a;
		a = Integer.parseInt("13", 8);
		
		System.out.println(a);
	}
	
	public int thirdChangeOfLiteralEncodingMethod() {
		int a = 11 & 32;
		
		return a;
	}
	
	public void thirdChangeOfLiteralEncodingTransformedMethod() {
		int a = 0b1100 & 0b0011;
		
		System.out.println(a);
	}
	
	public void firstChangeOfLiteralEncodingVariationMethod() {
		int a;
		a = 018;
		
		int b = 019;
		
		System.out.println(a + b);
	}
	
	public int secondChangeOfLiteralEncodingVariationMethod() {
		float b = 11;
		float c = 32;
		int a = b & c;
		
		return a;
	}
	
	public void firstOmittedCurlyBracesMethod() {
		int a = 2;
		
		if(a <= 0) a++; a++;
		
		System.out.println(a);
	}
	
	public void firstOmittedCurlyBracesTransformedMethod() {
		int a = 2;
		
		if(a <= 0) {
			a++;
		}		
		a++;
		
		System.out.println(a);
	}
	
	public void secondOmittedCurlyBracesMethod() {
		int a = 3;
		int b = 0;
		
		while(a < 3) b++; a++;
		
		System.out.println(a + " " + b);
	}
	
	public void secondOmittedCurlyBracesTransformedMethod() {
		int a = 3;
		int b = 0;
		
		while(a < 3) {
			b++; 
		}	
		a++;
		
		System.out.println(a + " " + b);
	}
	
	public void thirdOmittedCurlyBracesMethod() {
		int a = 3;
		
		for(int i = 0; i <= 3; i++) a++; a++;
		
		System.out.println(a);
	}
	
	public void thirdOmittedCurlyBracesTransformedMethod() {
		int a = 3;
		
		for(int i = 0; i <= 3; i++) {
			a++; 
		}
		a++;
		
		System.out.println(a);
	}
	
	public void firstOmittedCurlyBracesVariationMethod() {
		int a = 1;
		int b = 2;
		
		if(a == 1) b = 3; a = 2;
		
		for(int i = 0; i < 10; i++) {
			System.out.println(i);
		}
		
		for(int i; i < 0; i++)
			a = i;
			b = a;
		
		System.out.println(a);
	}
	
	public void secondOmittedCurlyBracesVariationMethod() {
		int a = 2;
		int b;
		
		for(int i = 0; i < 10; i++)
			System.out.println(i);
		b = a;
		
		System.out.println(a);
	}
	
	public void thirdOmittedCurlyBracesVariationMethod() {
		int a = 3;
		int b = 0;
		
		if(a <= 0) 
			a++; 
			b++;
		
		System.out.println(a + " " + b);
	}
	
	public void fourthOmittedCurlyBracesVariationMethod() {
		int a = 3;
		int b = 2;
		
		if(a == b)
			System.out.println(a);
		else
			System.out.println(b);
			System.out.println(b);
		
		while(a < 3) 
			b++; 
		a++;
		
		System.out.println(a);
	}
	
	public void fifthOmittedCurlyBracesVariationMethod(int a, int b) {
		if(a == b)
			System.out.println(a);
		else
			System.out.println(b);
		System.out.println(b);
		
		if(a == b)
			System.out.println(a);
		System.out.println(a);
		
		while(a < 4) 
			a++; 
		b++;
	}
	
	public void firstTypeConversionMethod() {
		float a = 1.99f;
		int b = (int) a;
		
		System.out.println(b);
	}
	
	public void firstTypeConversionTransformedMethod() {
		float a = 1.99f;
		int b = (int) Math.floor(a);
		
		System.out.println(a);
	}
	
	public void secondTypeConversionMethod() {
		int a = 288;
		byte b = (byte) a;
		
		System.out.println(b);
	}
	
	public void secondTypeConversionTransformedMethod() {
		int a = 288;
		byte b = (byte) (a % 256);
		
		System.out.println(b);
	}
	
	public void firstTypeConversionVariationMethod(int b) {
		float a = 1.99f;
		b = (int) a;
		
		System.out.println(b);
	}
	
	public void secondTypeConversionVariationMethod(int b) {
		float a = 1.99f;
		b = (int) Math.floor(a);
		
		System.out.println(a);
	}
	
	public int thirdTypeConversionVariationMethod(){
		float a = 2.99f;
		int b = (int) a;
		int c;
		c = (int) a;
		
		return c;
	}
	
}
