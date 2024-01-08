
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class DiagraphNode {

	
	private int id;
	
	private final Set<DiagraphNode> children = new HashSet<>();
	
	public DiagraphNode(int id) {
		
		this.id = id;
	}

	public Set<DiagraphNode> getChildren(){
		return Collections.unmodifiableSet(children);
	}
	


	
	@Override
	
	public boolean equals(Object o) {

		if(o == null || !getClass().equals(o.getClass())) {
			return false;
		}
		else {
			return id == ((DiagraphNode) o).id;
		}

	}
	public void addNode(DiagraphNode child) {

		children.add(child);
	}

	@Override
	public int hashCode() {
			return id;
		}
}
