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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		
		initListeners();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	private void initListeners() {
		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if(selectedCircle != null) {
					selectedCircle.setFrame(e.getX() - radius, e
								.getY() - radius, radius * 2, radius * 2);
					repaint();
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
			@Override
			public void mouseReleased(MouseEvent e) {
				if(selectedCircle != null) {
					selectedCircle.setFrame(e.getX() - radius, e
								.getY() - radius, radius * 2, radius * 2);
					selectedCircle = null;
					repaint();
				} else{
					boolean contained = false;
					if(null != getOverlappingCircle(e,(radius*2)))
						contained = true;
					if (!contained) {
						nodeLocations.add(new Ellipse2D.Double(e.getX() - radius, e
								.getY() - radius, radius * 2, radius * 2));
						graph.addNode(nodeLocations.size());
						repaint();
					}
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Ellipse2D circ = getOverlappingCircle(e, 1);
				if(null != circ) {
					selectedCircle = circ;
				}
			}
		});
	}
	
	private Ellipse2D getOverlappingCircle(MouseEvent e, int extra) {
		for (Ellipse2D circ : nodeLocations) {
			//radius*3 to keep some distance between nodes
			if (circ.intersects(e.getX() - (radius+extra), e.getY() - (radius+extra), (radius+extra) * 2, (radius+extra) * 2)) {
				return circ;
			}
		}
		return null;
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
		System.out.println(graph);
	}
}
