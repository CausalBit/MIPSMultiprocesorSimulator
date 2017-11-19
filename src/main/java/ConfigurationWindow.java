import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigurationWindow extends JFrame {

	private JPanel contentPane;
	private JTextField txtProgramFiles;
	private JTextField txtQuantum;
	private JButton btnRun;

	/**
	 * Create the frame.
	 */
	public ConfigurationWindow() {
		setResizable(false);
		setTitle("Simulation setup");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 243);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panelProgramFiles = new JPanel();
		panelProgramFiles.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Program files selection", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelProgramFiles.setBounds(10, 11, 424, 78);
		contentPane.add(panelProgramFiles);
		panelProgramFiles.setLayout(null);

		JLabel lblProgramFiles = new JLabel("Write the program files you want to run (numbers separated by commas):");
		lblProgramFiles.setBounds(10, 21, 339, 14);
		panelProgramFiles.add(lblProgramFiles);

		txtProgramFiles = new JTextField();
		txtProgramFiles.setBounds(10, 46, 404, 20);
		panelProgramFiles.add(txtProgramFiles);
		txtProgramFiles.setColumns(10);

		JPanel panelParameters = new JPanel();
		panelParameters.setBorder(new TitledBorder(null, "Simulation parameters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelParameters.setBounds(10, 100, 424, 70);
		contentPane.add(panelParameters);
		panelParameters.setLayout(null);

		JLabel lblQuantum = new JLabel("Cores quantum for every program file:");
		lblQuantum.setBounds(10, 21, 200, 14);
		panelParameters.add(lblQuantum);

		JLabel lblSlowMode = new JLabel("Slow mode:");
		lblSlowMode.setBounds(10, 46, 154, 14);
		panelParameters.add(lblSlowMode);

		JRadioButton rdbtnActivated = new JRadioButton("Activated");
		rdbtnActivated.setBounds(255, 42, 71, 23);
		panelParameters.add(rdbtnActivated);

		JRadioButton rdbtnDeactivated = new JRadioButton("Deactivated");
		rdbtnDeactivated.setBounds(328, 42, 86, 23);
		panelParameters.add(rdbtnDeactivated);

		txtQuantum = new JTextField();
		txtQuantum.setBounds(255, 18, 159, 20);
		panelParameters.add(txtQuantum);
		txtQuantum.setColumns(10);

		btnRun = new JButton("Run!");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		btnRun.setBounds(345, 181, 89, 23);
		contentPane.add(btnRun);
	}

	public int getQuantum(){
		return Integer.parseInt(this.txtQuantum.getText());
	}
}
