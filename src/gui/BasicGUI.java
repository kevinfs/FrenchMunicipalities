package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import municipalities.MunicipalitiesRecord;
import municipalities.Municipality;
import municipalities.NoSuchDirectoryException;
import municipalities.NoSuchMunicipalityException;

/**
 * {@link BasicGUI} displays a basic interface to navigate the restricted set of
 * data, limited to one province.
 * 
 */
public class BasicGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	protected MunicipalitiesRecord record;
	protected JList list;

	protected String dir;

	private JPanel panel0;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	private JPanel panel5;

	protected JLabel statusLabel = new JLabel("");

	protected JLabel nameLabel = new JLabel(" Town Name");
	protected JLabel nameLabelField = new JLabel();
	protected JLabel inseeLabel = new JLabel(" INSEE ID");
	protected JLabel inseeLabelField = new JLabel();
	protected JLabel postalLabel = new JLabel(" Postal Code");
	protected JLabel postalLabelField = new JLabel();
	protected JLabel phoneLabel = new JLabel(" Phone");
	protected JLabel phoneLabelField = new JLabel();
	protected JLabel emailLabel = new JLabel(" Email");
	protected JButton emailLabelButton = new JButton();
	protected JLabel webLabel = new JLabel(" Website");
	protected JButton webLabelButton = new JButton();

	protected JLabel nameSearchLabel = new JLabel("Search by name : ");
	protected JTextField nameSearchField = new JTextField(20);
	protected JLabel inseeSearchLabel = new JLabel("by insee code : ");
	protected JTextField inseeSearchField = new JTextField(5);
	protected JLabel postalSearchLabel = new JLabel("by postal code : ");
	protected JTextField postalSearchField = new JTextField(5);

	/**
	 * Constructs a {@link BasicGUI} by loading the restricted set of data and
	 * displaying the gui.
	 * 
	 * @param title {@link String} title of the window
	 * @param dir {@link String} used to initialize the {@link AdvancedGUI} correctly
	 */
	public BasicGUI(String title, String dir) {
		super(title);
		this.dir = dir;

		record = new MunicipalitiesRecord();
		try {

			record.loadRestrictedSet("jeu_de_donnees_reduit.d");

		} catch (NoSuchDirectoryException nsde) {
			System.out.println(nsde.getMessage());
		}

		list = new JList(record.getNamesList());
		updateStatus();

		initStyle();
		initLayout();
		initActions();

	}

	protected void initActions() {
		list.addListSelectionListener(new ListSelectionDisplay());
		nameSearchField.addKeyListener(new nameSearchFieldCompletion());
		inseeSearchField.addKeyListener(new inseeSearchFieldCompletion());
		postalSearchField.addKeyListener(new postalSearchFieldCompletion());
		emailLabelButton.addActionListener(new emailLabelAction());
		webLabelButton.addActionListener(new webLabelAction());
	}

	protected void initLayout() {

		getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT));

		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel2.add(nameSearchLabel);
		panel2.add(nameSearchField);
		panel2.setPreferredSize(new Dimension(500, 50));
		getContentPane().add(panel2);

		panel3 = new JPanel();
		panel3.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel3.add(inseeSearchLabel);
		panel3.add(inseeSearchField);
		panel3.setPreferredSize(new Dimension(250, 50));
		getContentPane().add(panel3);

		panel4 = new JPanel();
		panel4.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel4.add(postalSearchLabel);
		panel4.add(postalSearchField);
		panel4.setPreferredSize(new Dimension(250, 50));
		getContentPane().add(panel4);

		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setPreferredSize(new Dimension(500, 200));

		panel1 = new JPanel();
		panel1.setLayout(new GridLayout(6, 2));
		panel1.add(nameLabel);
		panel1.add(nameLabelField);
		panel1.add(inseeLabel);
		panel1.add(inseeLabelField);
		panel1.add(postalLabel);
		panel1.add(postalLabelField);
		panel1.add(phoneLabel);
		panel1.add(phoneLabelField);
		panel1.add(emailLabel);
		panel1.add(emailLabelButton);
		panel1.add(webLabel);
		panel1.add(webLabelButton);
		panel1.setPreferredSize(new Dimension(500, 200));

		panel0 = new JPanel();
		panel0.setLayout(new GridLayout(1, 2));
		panel0.add(scrollPane);
		panel0.add(panel1);
		panel0.setPreferredSize(new Dimension(1000, 200));
		getContentPane().add(panel0);

		panel5 = new JPanel();
		panel5.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel5.add(statusLabel);
		panel5.setPreferredSize(new Dimension(1000, 30));
		getContentPane().add(panel5);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setPreferredSize(new Dimension(1020, 298));
		pack();
		// setSize(1000, 800);
		setVisible(true);
	}

	protected void initStyle() {
		statusLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
		webLabelButton.setForeground(Color.BLUE);
		webLabelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		webLabelButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		webLabelButton.setBorderPainted(false);
		webLabelButton.setHorizontalAlignment(SwingConstants.LEFT);
		emailLabelButton.setForeground(Color.BLUE);
		emailLabelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		emailLabelButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		emailLabelButton.setBorderPainted(false);
		emailLabelButton.setHorizontalAlignment(SwingConstants.LEFT);
	}

	/**
	 * Displays the informations contained in the {@link Municipality} selected
	 * 
	 */
	private class ListSelectionDisplay implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (list.getSelectedIndex() != -1) {
				String name = String.valueOf(list.getSelectedValue());
				try {
					Municipality municipality = record
							.getMunicipalityByName(name);
					nameLabelField.setText(municipality.getName());
					inseeLabelField.setText(municipality.getInseeId());
					postalLabelField.setText(municipality.getPostalCode());
					phoneLabelField.setText(municipality.getPhoneNumber());
					emailLabelButton.setText(municipality.getEmail());
					webLabelButton.setText(municipality.getWebsite());
				} catch (NoSuchMunicipalityException nsme) {
					updateError(nsme.getMessage());
				}
			}
		}
	}

	/**
	 * Updates the list of {@link Municipality} when the user is typing a name
	 * in the search field.
	 * 
	 */
	private class nameSearchFieldCompletion implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			String text = nameSearchField.getText();
			list.removeAll();
			if (text.isEmpty()) {
				list.setListData(record.getNamesList());
			} else {
				list.setListData(MunicipalitiesRecord
						.extractNamesFromList(record
								.searchMunicipalitiesByName(text)));
			}
			updateStatus();
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
		}

	}

	/**
	 * Updates the list of {@link Municipality} when the user is typing an insee
	 * code in the search field.
	 * 
	 */
	private class inseeSearchFieldCompletion implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			String text = inseeSearchField.getText();
			list.removeAll();
			if (text.isEmpty()) {
				list.setListData(record.getNamesList());
			} else {
				list.setListData(MunicipalitiesRecord
						.extractNamesFromList(record
								.searchMunicipalitiesByInsee(text)));
			}
			updateStatus();
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
		}

	}

	/**
	 * Updates the list of {@link Municipality} when the user is typing a postal
	 * code in the search field.
	 * 
	 */
	private class postalSearchFieldCompletion implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			String text = postalSearchField.getText();
			list.removeAll();
			if (text.isEmpty()) {
				list.setListData(record.getNamesList());
			} else {
				list.setListData(MunicipalitiesRecord
						.extractNamesFromList(record
								.searchMunicipalitiesByPostal(text)));
			}
			updateStatus();
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
		}

	}

	/**
	 * Launches the user's default browser when clicking a website url.
	 * 
	 */
	private class webLabelAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Desktop.getDesktop().browse(
						new URL(webLabelButton.getText()).toURI());
			} catch (Exception ex) {

			}
		}

	}

	/**
	 * Launches the user's default mail client when clicking an email address.
	 * 
	 */
	private class emailLabelAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Desktop.getDesktop()
						.mail(new URL("mailto:" + emailLabelButton.getText())
								.toURI());
			} catch (Exception ex) {

			}
		}

	}

	/**
	 * Updates the status bar with the number of {@link Municipality} actually
	 * displayed.
	 */
	protected void updateStatus() {
		int size = list.getModel().getSize();
		if (size == 0) {
			statusLabel.setText("No element matches your query, sorry !");
		} else if (size == 1) {
			statusLabel.setText("1 municipality listed");
		} else {
			statusLabel.setText(size + " municipalities listed");
		}
	}

	/**
	 * Displays an error message
	 * 
	 * @param text
	 *            error message
	 */
	protected void updateError(String text) {
		statusLabel.setText(text);
	}

	public static void main(String[] args) {
		new BasicGUI(
				"Basic Version - Showing only Val d'Oise's Municipalities",
				"jeu_de_donnees_reduit.d/95");
	}

}
