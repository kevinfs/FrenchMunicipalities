package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import municipalities.Municipality;
import municipalities.NoSuchDirectoryException;

/**
 * {@link AdvancedGUI} displays a slightly better interface to navigate the
 * complete set of data, by choosing either a specific province, or the complete
 * country.
 * 
 */
public class AdvancedGUI extends BasicGUI {

	private static final long serialVersionUID = 1L;

	private JPanel panel0;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	private JPanel panel5;
	private JPanel panel6;

	private JComboBox provincesList;
	private JLabel provincesLabel;
	private JButton allProvinces;

	/**
	 * Constructs a {@link AdvancedGUI} by loading a restricted set of data and
	 * displaying the gui, which allows to modify the restricted set or choose
	 * the complete set.
	 * 
	 * @param title
	 *            {@link String} title of the window
	 * @param dir
	 *            {@link String} directory in which the data is stored
	 */
	public AdvancedGUI(String title, String dir) {
		super(title, dir);

		try {

			record.removeAll();
			record.loadRestrictedSet(dir + "/95");

			list.setListData(record.getNamesList());
			list.scrollRectToVisible(new Rectangle(0, 0));
			updateStatus();

		} catch (NoSuchDirectoryException e) {
			updateError(e.getMessage());
		}

	}

	protected void initActions() {
		super.initActions();
		provincesList.addActionListener(new ChooseProvince());
		allProvinces.addActionListener(new allProvincesAction());
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

		panel6 = new JPanel();
		panel6.setLayout(new FlowLayout(FlowLayout.LEFT));
		provincesLabel = new JLabel("Choose province : ");
		panel6.add(provincesLabel);
		String[] ess = AdvancedGUI.getProvincesList(dir);
		provincesList = new JComboBox(ess);
		if (provincesList.getModel().getSize() > 95) {
			provincesList.setSelectedIndex(95);
		}
		panel6.add(provincesList);
		allProvinces = new JButton("Display all");
		allProvinces.setToolTipText("This may take a while, be patient!");
		panel6.add(allProvinces);
		panel6.setPreferredSize(new Dimension(1000, 50));
		getContentPane().add(panel6);

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
		getContentPane().setPreferredSize(new Dimension(1020, 350));
		pack();
		// setSize(1000, 800);
		setVisible(true);
	}

	/**
	 * Updates the list of {@link Municipality} when the user is selecting a
	 * different province.
	 * 
	 */
	private class ChooseProvince implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String province = String.valueOf(provincesList.getSelectedItem());
			record.removeAll();
			try {
				record.loadRestrictedSet(dir + "/" + province);
			} catch (NoSuchDirectoryException e1) {
				System.err.println(e1.getMessage());
			}
			list.setListData(record.getNamesList());
			list.scrollRectToVisible(new Rectangle(0, 0));
			updateStatus();
		}

	}

	/**
	 * Displays the complete list of the country's {@link Municipality} when the
	 * user wishes so.
	 * 
	 */
	private class allProvincesAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			record.removeAll();
			try {
				record.loadCompleteSet(dir);
				list.setListData(record.getNamesList());
				list.scrollRectToVisible(new Rectangle(0, 0));
				updateStatus();
			} catch (NoSuchDirectoryException e1) {
				System.err.println(e1.getMessage());
			}
		}

	}

	public static String[] getProvincesList(String dir) {

		File directory = new File(dir);
		String[] list = directory.list();
		Arrays.sort(list);
		return list;
	}

	public static void main(String[] args) {
		new AdvancedGUI("Advanced Version", "all_20131104/organismes/");
	}

}
