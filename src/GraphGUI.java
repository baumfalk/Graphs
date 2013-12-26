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

@SuppressWarnings("serial")
public class GraphGUI extends JFrame {

	private JPanel contentPane;
	private int x;
	private int y;
	private final int radius = 5;

	ArrayList<Ellipse2D> nodeLocations;
	GraphUnweighted<Integer> graph;

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
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {

				boolean contained = false;
				for (Ellipse2D circ : nodeLocations) {
					if (circ.intersects(e.getX() - (radius + 1), e.getY() - (radius + 1), (radius + 1) * 2, (radius + 1) * 2)) {
						contained = true;
						break;
					}
				}
				if (!contained) {
					nodeLocations.add(new Ellipse2D.Double(e.getX() - radius, e
							.getY() - radius, radius * 2, radius * 2));
					graph.addNode(nodeLocations.size());
					repaint();
				}

			}
		});
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
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
