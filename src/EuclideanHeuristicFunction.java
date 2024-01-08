

public class EuclideanHeuristicFunction implements HeuristicFucntion {

	private final DiagraphCoordinates coordinates;
	public double getEstimate(DiagraphNode node1 ,DiagraphNode node2) {

		return coordinates.get(node1).distance(coordinates.get(node2));
	}
	
	public EuclideanHeuristicFunction(DiagraphCoordinates coordinates) {
		this.coordinates = coordinates;

	}
	

	
}
