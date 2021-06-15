import java.util.ArrayList;
import java.util.Iterator;

public class ArithmeticAsLogicSample {
	
	public ArithmeticAsLogicSample() {

	}
	
	public void firstMethod(){
		int a = 8;
		
		if((a - 3) * (7 - a) <= 0) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
	
	public void secondMethod(){
		int a = 2;
		
		if((a - 2) * (6 - a) > 0) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
	
	public void thirdMethod(){
		int a = 5;
		
		if(a + 5 != 0) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
	
}
