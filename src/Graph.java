import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class Graph<T> {
	private ArrayList<T> nodes;
	private HashMap<T,ArrayList<Pair<T,Double>>> edges;
	
	public Graph() {
		nodes = new ArrayList<T>();
		edges = new HashMap<T, ArrayList<Pair<T,Double>>>();
	}
	
	public boolean addNode(T node) {
		boolean added = !nodes.contains(node);
		if(added) {
			nodes.add(node);
			ArrayList<Pair<T,Double>> edgeList = new ArrayList<Pair<T,Double>>();
			edges.put(node, edgeList);
		}
		return added;
	}
	
	
	public void addEdge(T node1, T node2, double cost) {
		addNode(node1);
		addNode(node2);
		
		boolean edgeExists = false;
		for(int i = 0; i < edges.get(node1).size(); i++) {
			if(node2.equals(edges.get(node1).get(i).x)) {
				edgeExists = true;
			}
		}
		if(!edgeExists)
		edges.get(node1).add(new Pair<T,Double>(node2,cost));
		
	}
	
	public void addBiEdge(T node1, T node2, double cost) {
		addEdge(node1,node2,cost);
		addEdge(node2,node1,cost);
	}

	public boolean removeNode(T node) {
		boolean removed = nodes.remove(node);
		if(removed) {
			edges.remove(node);
			removeNodeFromEdges(node);
		}
		return removed;
	}
	
	public boolean removeEdge(T node1, T node2) {
		boolean removed = false;
		for(int i = 0; i < edges.get(node1).size(); i++) {
			if(node2.equals(edges.get(node1).get(i).x)) {
				removed = edges.get(node1).remove(edges.get(node1).get(i));
			}
		}
		return removed;
	}
	
	public boolean removeBiEdge(T node1, T node2) {
		return removeEdge(node1, node2) && removeEdge(node2, node1);
	}
	public List<T> getNodes() {
		return Collections.unmodifiableList(nodes);
	}

	public double getCost(T node1, T node2) {
		for(Pair<T,Double> edge : edges.get(node1)) {
			if(edge.x.equals(node2))
				return edge.y;
		}
		return Double.POSITIVE_INFINITY;
	}

	public List<Pair<T, Double>> getEdges(T node1) {
		return Collections.unmodifiableList(edges.get(node1));
	}
	
	public String toString() {
		StringBuilder sb= new StringBuilder();
		sb.append("nodes:\n");
		for(T node : nodes) {
			sb.append(node+"\n");
			sb.append("\t neighbors:\n");
			for(Pair<T, Double> edge : edges.get(node)) {
				sb.append("\t "+edge.x+" ("+edge.y+")\n");
			}
		}
		
		return sb.toString();
	}

	private void removeNodeFromEdges(T node) {
		for(ArrayList<Pair<T,Double>> edgeList : edges.values()) {
			for(int i = 0; i < edgeList.size(); i++) {
				if(node.equals(edgeList.get(i).x)) {
					edgeList.remove(edgeList.get(i));
				}
			}
		}
	}
}
