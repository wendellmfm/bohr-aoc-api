import java.util.ArrayList;
import java.util.Iterator;

public class ArithmeticAsLogicSample {
	
	public ArithmeticAsLogicSample() {

	}
	
	public void methodOne(){
		int a = 8;
		
		if((a - 3) * (7 - a) == 0) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
	
	public void methodOneTransformed(){
		int a = 8;
		
		if(3 == a || a == 7) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
	
	public void methodTwo(){
		int a = 2;
		
		if((a - 2) * (6 - a) != 0) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
	
	public void methodTwoTransformed(){
		int a = 2;
		
		if(a < 2 || 6 < a) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
	
	public void methodThree(){
		int a = 5;
		
		if(a + 5 != 0) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
	
	public void methodThreeTransformed(){
		int a = 5;
		
		if(a != -5) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
	
	public void methodFour(){
		int a = 2;
		
		if(0 == (a - 2) * (6 - a)) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}

	public void methodFive(){
		int a = 5;
		
		if(0 != a + 5) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
	
}
