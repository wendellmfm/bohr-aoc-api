import java.util.ArrayList;
import java.util.Iterator;

public class LogicAsControlFlowSample {
	
	public void firstMethod(){
		int v1 = 1;
		int v2 = 5;
		
		if(++v1 > 0 && ++v2 > 0) {
			v1 = v1 * 2;
			v2 = v2 * 2;
		}
	}
	
	public void secondMethod(){
		int v1 = 1;
		int v2 = 5;
		
		boolean test = v1 == v2 && ++v1 > 0 || ++v2 > 0;
	}
	
}
