import java.util.ArrayList;
import java.util.Iterator;

public class LogicAsControlFlowSample {
	
	public void methodOne(){
		int a = 1;
		int b = 5;
		
		if(++a > 0 && ++b > 0) {
			a = a * 2;
			b = b * 2;
		}
		
		System.out.println(a + " " + b);
	}
	
	public void methodOneTransformed() {
		int a = 1;
		int b = 5;
		
		if(++a > 0) {
			a = a * 2;
			b = b * 2;
		} else if(++b > 0) {
			a = a * 2;
			b = b * 2;
		}
		
		System.out.println(a + " " + b);
	}
	
	public void methodTwo(){
		int a = 1;
		int b = 5;
		
		boolean test = a == b && ++a > 0 || ++b > 0;
		
		System.out.println(a + " " + b);
	}
	
	public void methodTwoTransformed(){
		int a = 1;
		int b = 5;
		
		if(a == b) {
			++a;
		} else {
			++b;
		}
		
		System.out.println(a + " " + b);
	}
	
	public void methodThree(){
		int a = 1;
		int b = 5;
		int c = 0;
		
		while(c < b && ++a > 0) {
			c++;
		}
		
		System.out.println(a + " " + b + " " + c);
	}
	
	public void methodThreeTransformed(){
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
	
	public void methodFour(){
		int a = 1;
		
		if(a > 0 && methodThree()) {
			a = a * 2;
		}
		
		System.out.println(a + " " + b);
	}
	
	public void methodFive(){
		int a = 0;
		int b = 4;
		
		for(int i = 0; b == 4 && (a = i) < 2; i++) {
			System.out.println(a);
		}
		
		System.out.println(a + " " + b);
	}
	
	public void methodFiveTransformed(){
		int a = 0;
		int b = 4;
		
		for(int i = 0; b == 4; i++) {
			a = i;
			if(a >= 2) {
				break;
			}
			System.out.println(a);
		}
		
		System.out.println(a + " " + b);
	}
}
