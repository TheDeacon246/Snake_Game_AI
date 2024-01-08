

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class AstarPathFinder {

	
	private static final class HeapEntry implements Comparable<HeapEntry>{
		private final DiagraphNode node;
		private final double distance;

		HeapEntry(DiagraphNode node , double distance){
		
			this.node = node;
			this.distance =  distance;
		}
		
		DiagraphNode getNode() {
			return node;
		}

		@Override
		public int compareTo(HeapEntry o) {
			// TODO Auto-generated method stub
			return Double.compare(distance, o.distance);
		}
	}
	
	
	public static List<DiagraphNode> search(DiagraphNode source,
			DiagraphNode target,
			DiagraphWeightFucntion weightFunction,
			HeuristicFucntion heuristicFunction){
		
		PriorityQueue<HeapEntry> OPEN = new PriorityQueue<>();
		Set<DiagraphNode> CLOSED = new HashSet<>();
		Map <DiagraphNode, Double> DISTANCE = new HashMap<>();
		Map<DiagraphNode, DiagraphNode>PARENTS = new HashMap<>();
		
		OPEN.add(new HeapEntry(source,0.0));
		DISTANCE.put(source, 0.0);
		PARENTS.put(source, null);
		
		while(!OPEN.isEmpty()) {
			
			DiagraphNode currentNode = OPEN.remove().getNode();
			
			if(currentNode.equals(target)) {
				return tracebackPath(currentNode, PARENTS);
			}
			
			if(CLOSED.contains(currentNode)) {
				continue;
			}
			
			for(DiagraphNode childNode: currentNode.getChildren()) {
				if(CLOSED.contains(currentNode)) {
					continue;
				}
				
				double tentativeDistance =  DISTANCE.get(currentNode) + weightFunction.get(currentNode, childNode);
				
				if(!DISTANCE.containsKey(childNode) || DISTANCE.get(childNode) > tentativeDistance){
					DISTANCE.put(childNode, tentativeDistance);
					PARENTS.put(childNode, currentNode);
					OPEN.add(new HeapEntry(
							childNode,
							tentativeDistance + 
							heuristicFunction.getEstimate(childNode, target)));
				}
			}
		}
		return new ArrayList<>();
	}
	
	private static List<DiagraphNode> tracebackPath(DiagraphNode target,
			Map<DiagraphNode,DiagraphNode>PARENTS){
	List<DiagraphNode>path = new ArrayList();
	DiagraphNode currentNode = target;
	
	while(currentNode != null) {
		path.add(currentNode);
		currentNode = PARENTS.get(currentNode);
		
	}
	
	Collections.reverse(path);
	return path;
	}
}
