package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * {@link IndexGUI} displays a choosing window offering to select between the
 * {@link BasicGUI} and the {@link AdvancedGUI}. If the {@link AdvancedGUI} is
 * selected, a {@link JFileChooser} asks the user for the data directory.
 * 
 */
public class IndexGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	JPanel panel1;

	JTextArea explicationLabel = new JTextArea();
	JTextArea exampleLabel = new JTextArea();
	JFileChooser chooser = new JFileChooser();
	JLabel errorLabel = new JLabel();
	JButton openChooser = new JButton("Advanced Version - All Provinces");
	JButton basicButton = new JButton("Basic Version - Only Val d'Oise");

	public IndexGUI(String title) {
		super(title);

		initLayout();
		initActions();
	}

	protected void initActions() {
		openChooser.addActionListener(new openAction());
		basicButton.addActionListener(new basicAction());
	}

	protected void initLayout() {
		getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT));

		explicationLabel.setPreferredSize(new Dimension(500, 50));
		explicationLabel.setEditable(false);
		explicationLabel
				.setText("Hello !\nYou can choose between two versions, one basic and one advanced.");
		getContentPane().add(explicationLabel);

		exampleLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 13));
		exampleLabel.setPreferredSize(new Dimension(500, 100));
		exampleLabel.setEditable(false);
		exampleLabel
				.setText("Please note that if you choose the advanced version, you will have to select \nthe 'organismes' directory in which you store the complete set of data.\n\nExample : your/path/to/all_20131104/organismes/");
		getContentPane().add(exampleLabel);

		basicButton.setPreferredSize(new Dimension(500, 50));
		getContentPane().add(basicButton);

		openChooser.setPreferredSize(new Dimension(500, 50));
		getContentPane().add(openChooser);

		errorLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
		errorLabel.setPreferredSize(new Dimension(500, 30));
		getContentPane().add(errorLabel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setPreferredSize(new Dimension(510, 310));
		pack();
		setVisible(true);
	}

	/**
	 * Used to launch the {@link BasicGUI}
	 * 
	 */
	private class basicAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			errorLabel.setText("Basic Version Opened...");
			new BasicGUI(
					"Basic Version - Showing only Val d'Oise's Municipalities",
					"jeu_de_donnees_reduit.d");
		}

	}

	/**
	 * Used to launch the {@link AdvancedGUI}. Verifies first if the directory
	 * is valid.
	 * 
	 */
	private class openAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = chooser.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();

				if (isCorrectDirectoryInput(file)) {
					errorLabel.setText("Opening: " + file.getPath());
					new AdvancedGUI("Advanced Version", file.getPath());
				} else {
					errorLabel
							.setText("Directory "
									+ file.getName()
									+ " does not contain any compatible xml file. Please try again.");
				}
			} else {
				errorLabel.setText("You cancelled the opening command !");
			}
		}

	}

	/**
	 * Checks if the given directory is containing sub-directories representing
	 * provinces, and if any of these provinces contains a valid xml file.
	 * 
	 * @param dir
	 *            {@link File} the directory to check.
	 * @return <code>true</code> or <code>false</code>
	 */
	private boolean isCorrectDirectoryInput(File dir) {

		if (!dir.isDirectory()) {
			return false;
		}
		File[] dirList = dir.listFiles();
		for (File file : dirList) {
			if (file.isDirectory()) {
				File[] fileList = file.listFiles();
				for (File file2 : fileList) {
					if (file2.isFile()
							&& file2.getName().matches("^mairie-(.*).xml$")) {
						return true;
					}
				}
			}
		}
		return false;

	}

	public static void main(String[] args) {
		new IndexGUI("Choose a version");
	}
}
