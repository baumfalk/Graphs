import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class GraphGUI extends JFrame {

	
	private JPanel contentPane;
	private int x;
	private int y;
	private final int radius = 5;

	ArrayList<Ellipse2D> nodeLocations;
	GraphUnweighted<Integer> graph;
	protected boolean circleClicked;
	protected Ellipse2D selectedCircle;
	protected State state;
	protected int nodeId;
	protected Point currPoint;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraphGUI frame = new GraphGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GraphGUI() {
		
		
		nodeLocations = new ArrayList<Ellipse2D>();
		graph = new GraphUnweighted<Integer>();
		state = State.DEFAULT;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		
		initListeners();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	private void initListeners() {
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.isControlDown() && state == State.DEFAULT) {
					state = State.POSSIBLE_DRAWING_LINE;
				}
				else if(e.isControlDown()) {
					state = State.DEFAULT;
					nodeId = -1;
					currPoint = null;
					repaint();
				}
			}
			
			public void keyReleased(KeyEvent e) {
				if(!e.isControlDown() && state == State.POSSIBLE_DRAWING_LINE) {
					state = State.SELECT_NODE_FOR_LINE;

				}
				
			}
		});
		
		this.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				switch(state) {
				case DRAGGING_NODE:
					selectedCircle.setFrame(e.getX() - radius, e
								.getY() - radius, radius * 2, radius * 2);
					repaint();
					break;
				case DRAWING_LINE:
					currPoint = new Point(e.getX(), e.getY());
					repaint();
					break;
				default:
					break;
				
				}
			}
		});
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				repaint();
			}

		});
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				switch(state) {
				case DEFAULT:
					boolean nodeClicked = (-1 != getOverlappingCircleId(e.getX(),e.getY(),(radius*2)));
					if (!nodeClicked) {
						nodeLocations.add(new Ellipse2D.Double(e.getX() - radius, e
								.getY() - radius, radius * 2, radius * 2));
						graph.addNode(nodeLocations.size()-1);
						repaint();
					} else {
						int id =  getOverlappingCircleId(e.getX(),e.getY(), 1);
						Ellipse2D circ = nodeLocations.get(id);
						selectedCircle = circ;
						state = State.DRAGGING_NODE;
					}
					break;
				case DRAGGING_NODE:
					selectedCircle.setFrame(e.getX() - radius, e.getY() - radius, radius * 2, radius * 2);
					selectedCircle = null;
					state = State.DEFAULT;
					repaint();
					break;
				case DRAWING_LINE:
					int id = getOverlappingCircleId(e.getX(),e.getY(),1);
					if(-1 != id) {
						graph.addBiEdge(nodeId, id);
						state = State.SELECT_NODE_FOR_LINE;
						nodeId = -1;
						currPoint = null;
						repaint();
					}
				case SELECT_NODE_FOR_LINE:
					id =  getOverlappingCircleId(e.getX(),e.getY(), 1);
					if(-1 != id) {
						state = State.DRAWING_LINE;
						nodeId = id;
					}
					break;
				default:
					break;
				}
			}
		});
	}
	
	private int getOverlappingCircleId(int x, int y, int extra) {
		for (int i=0;i<nodeLocations.size();i++) {
			Ellipse2D circ = nodeLocations.get(i);
			//radius*3 to keep some distance between nodes
			if (circ.intersects(x - (radius+extra), y - (radius+extra), (radius+extra) * 2, (radius+extra) * 2)) {
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public void paint(Graphics g) {
		g.clearRect(g.getClipBounds().x, g.getClipBounds().y,
				g.getClipBounds().width, g.getClipBounds().height);
		for (int i = 0; i < nodeLocations.size(); i++) {
			g.drawOval((int) nodeLocations.get(i).getX(), (int) nodeLocations
					.get(i).getY(), (int) nodeLocations.get(i).getWidth(),
					(int) nodeLocations.get(i).getHeight());
		}
		for(int i =0; i < graph.getNodes().size(); i++) {
			for(int j : graph.getEdges(i)) {
				g.drawLine((int)nodeLocations.get(i).getCenterX(), (int)nodeLocations.get(i).getCenterY(),
						(int)nodeLocations.get(j).getCenterX(),  (int)nodeLocations.get(j).getCenterY());
			}
		}
		switch(state){

		case DRAWING_LINE:
			if(currPoint != null)
				g.drawLine((int)nodeLocations.get(nodeId).getCenterX(),(int)nodeLocations.get(nodeId).getCenterY(),currPoint.x,currPoint.y);
			break;
	
		default:
			break;
		
		}
		
		System.out.println(graph);
	}
}
