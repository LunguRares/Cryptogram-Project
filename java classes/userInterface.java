import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class userInterface {
	public static void main(String[] args) {
		userInterface UI = new userInterface();
	}

	private JFrame frame;
	private JLabel filenameLabel;
	private JButton undoButton;

	public userInterface() {
		makeFrame();
	}

	private void makeFrame() {
		frame = new JFrame("Cryptogram");
		JPanel contentPane = (JPanel) frame.getContentPane();
		contentPane.setBorder(new EmptyBorder(100, 200, 100, 200));
		contentPane.setLayout(new BorderLayout(50, 50));

		filenameLabel = new JLabel();
		contentPane.add(filenameLabel, BorderLayout.NORTH);

		JMenuBar menubar = new JMenuBar();
		frame.setJMenuBar(menubar);
		JMenu menu;
		JMenuItem item;

		menu = new JMenu("File");
		menubar.add(menu);
		item = new JMenuItem("Save Cryptogram");
		menu.add(item);
		item = new JMenuItem("Quit");
		item.addActionListener(e -> System.exit(0));
		menu.add(item);

		menu = new JMenu("Help");
		menubar.add(menu);
		item = new JMenuItem("About");
		menu.add(item);

		undoButton = new JButton("Undo");
		frame.add(undoButton);

		frame.pack();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(d.width / 2 - frame.getWidth() / 2, d.height / 2 - frame.getHeight() / 2);
		frame.setVisible(true);
	}
}