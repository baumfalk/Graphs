import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GraphUnweighted<T> {
	
	Graph<T> g;
	public GraphUnweighted() {
		g = new Graph<T>();
	}
	public boolean addNode(T node) {
		return g.addNode(node);
	}
	
	
	public void addEdge(T node1, T node2) {
		g.addEdge(node1, node2, 1);
	}
	
	public void addBiEdge(T node1, T node2) {
		g.addBiEdge(node1, node2, 1);
	}

	public boolean removeNode(T node) {
		return g.removeNode(node);
	}
	
	public boolean removeEdge(T node1, T node2) {
		return g.removeEdge(node1, node2);
	}
	
	public boolean removeBiEdge(T node1, T node2) {
		return g.removeBiEdge(node1, node2);
	}
	
	public List<T> getNodes() {
		return g.getNodes();
	}

	public double getCost(T node1, T node2) {
		return g.getCost(node1,node2);
	}

	public List<T> getEdges(T node1) {
		 List<T> list = new ArrayList<T>();
		 for(Pair<T,Double> p : g.getEdges(node1)) {
			 list.add(p.x);
		 }
		 return list;
	}
	
	public String toString() {
		StringBuilder sb= new StringBuilder();
		sb.append("nodes:\n");
		for(T node : g.getNodes()) {
			sb.append(node+"\n");
			sb.append("\t neighbors:\n");
			for(T neighbor : getEdges(node)) {
				sb.append("\t "+neighbor +"\n");
			}
		}
		
		return sb.toString();
	}
}
