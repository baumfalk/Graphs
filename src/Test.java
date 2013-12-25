import java.util.List;


public class Test {
	public static void main(String[] args) {
		Graph<String> g = new Graph<String>();
		String[] steden= {"Amersfoort","Utrecht","Amsterdam","Haarlem","Breda","Nijmegen","Groningen"};
		g.addBiEdge(steden[0],steden[1],25 );
		g.addBiEdge(steden[1],steden[2],27 );
		g.addBiEdge(steden[0],steden[2],40);
		g.addBiEdge(steden[2],steden[3],15 );
		g.addBiEdge(steden[1],steden[4],40 );
		g.addBiEdge(steden[1], steden[5], 60);
		g.addBiEdge("Groningen","Haarlem",150);
		g.addBiEdge("Groningen","Nijmegen",100);
		g.addBiEdge("Nijmegen","Breda",120);
		for(int i = 0; i < steden.length;i++) {
			for (int j = 0; j < steden.length; j++) {
				
				g.addBiEdge(steden[i], steden[j], Double.POSITIVE_INFINITY);
			}
		}
		System.out.println(g);
		TSP<String> tsp = new TSP<String>(g, steden[0]);
		
		List<String> list = tsp.shortestPath();
		System.out.println(list);
	}
}
