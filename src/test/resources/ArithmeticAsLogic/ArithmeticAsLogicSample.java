import java.util.ArrayList;
import java.util.Iterator;

public class ArithmeticAsLogicSample {
	
	public void firstMethod(){
		int v1 = 1;
		
		if((v1 - 3) * (7 - v1) <= 0) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
	
	public void secondMethod(){
		int v1 = 1;
		
		if(v1 + 5 != 0) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
	
}
