
import java.util.HashMap;
import java.util.*;

public class DiagraphWeightFucntion {

	private final Map<DiagraphNode, Map<DiagraphNode, Double>>map = new HashMap<>();


	public double get(DiagraphNode tail, DiagraphNode head) {
		return map.get(tail).get(head);
	}
	public void set(DiagraphNode tail,DiagraphNode head, double weight ) {
		map.putIfAbsent(tail, new HashMap<>());
		map.get(tail).put(head, weight);
		
	}
	

}
