import java.util.List;

public class DijkstraPathFinder {
	
	public static List<DiagraphNode> search(DiagraphNode source,
			DiagraphNode target,DiagraphWeightFucntion weightFunction){
		return AstarPathFinder.search(source, target, weightFunction, new ZeroHeuristic());
	}
	}
		


