import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class ResultsWindow extends JFrame {

	private JPanel contentPane;
	private JTable tableRegisters0;
	private JTable tableRegisters1;
	private JTable tableRegisters2;
	private JTable tableCache0;
	private JTable tableCache1;
	private JTable tableCache2;
	private JTable tableSharedMemory;
	private JTable tableDirectory0;
	private JTable tableDirectory1;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ResultsWindow frame = new ResultsWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public ResultsWindow() {
		setTitle("Results");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1057, 795);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelProcessor0 = new JPanel();
		panelProcessor0.setLayout(null);
		panelProcessor0.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Processor 0", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelProcessor0.setBounds(10, 11, 323, 379);
		contentPane.add(panelProcessor0);
		
		JPanel panelCache0 = new JPanel();
		panelCache0.setBounds(10, 21, 146, 347);
		panelProcessor0.add(panelCache0);
		panelCache0.setLayout(null);
		panelCache0.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Core 0 data cache", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JScrollPane scrollCache0 = new JScrollPane();
		scrollCache0.setWheelScrollingEnabled(false);
		scrollCache0.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollCache0.setBounds(10, 21, 126, 315);
		panelCache0.add(scrollCache0);
		
		tableCache0 = new JTable();
		tableCache0.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"0", "1", "2", "3"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableCache0.getColumnModel().getColumn(0).setResizable(false);
		tableCache0.getColumnModel().getColumn(1).setResizable(false);
		tableCache0.getColumnModel().getColumn(2).setResizable(false);
		tableCache0.getColumnModel().getColumn(3).setResizable(false);
		scrollCache0.setViewportView(tableCache0);
		
		JPanel panelCache1 = new JPanel();
		panelCache1.setBounds(166, 21, 146, 347);
		panelProcessor0.add(panelCache1);
		panelCache1.setLayout(null);
		panelCache1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Core 1 data cache", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JScrollPane scrollCache1 = new JScrollPane();
		scrollCache1.setWheelScrollingEnabled(false);
		scrollCache1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollCache1.setBounds(10, 21, 126, 315);
		panelCache1.add(scrollCache1);
		
		tableCache1 = new JTable();
		tableCache1.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"0", "1", "2", "3"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableCache1.getColumnModel().getColumn(0).setResizable(false);
		tableCache1.getColumnModel().getColumn(1).setResizable(false);
		tableCache1.getColumnModel().getColumn(2).setResizable(false);
		tableCache1.getColumnModel().getColumn(3).setResizable(false);
		scrollCache1.setViewportView(tableCache1);
		
		JPanel panelProcessor1 = new JPanel();
		panelProcessor1.setLayout(null);
		panelProcessor1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Processor 1", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelProcessor1.setBounds(343, 11, 167, 379);
		contentPane.add(panelProcessor1);
		
		JPanel panelCache2 = new JPanel();
		panelCache2.setBounds(10, 21, 146, 345);
		panelProcessor1.add(panelCache2);
		panelCache2.setLayout(null);
		panelCache2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Core 2 data cache", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JScrollPane scrollCache2 = new JScrollPane();
		scrollCache2.setWheelScrollingEnabled(false);
		scrollCache2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollCache2.setBounds(10, 21, 126, 313);
		panelCache2.add(scrollCache2);
		
		tableCache2 = new JTable();
		tableCache2.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"0", "1", "2", "3"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableCache2.getColumnModel().getColumn(0).setResizable(false);
		tableCache2.getColumnModel().getColumn(1).setResizable(false);
		tableCache2.getColumnModel().getColumn(2).setResizable(false);
		tableCache2.getColumnModel().getColumn(3).setResizable(false);
		scrollCache2.setViewportView(tableCache2);
		
		JPanel panelDirectory0 = new JPanel();
		panelDirectory0.setLayout(null);
		panelDirectory0.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Processor 0 directory", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelDirectory0.setBounds(517, 11, 147, 187);
		contentPane.add(panelDirectory0);
		
		JScrollPane scrollDirectory0 = new JScrollPane();
		scrollDirectory0.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollDirectory0.setBounds(10, 22, 127, 154);
		panelDirectory0.add(scrollDirectory0);
		
		tableDirectory0 = new JTable();
		tableDirectory0.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"S", "C0", "C1", "C2"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableDirectory0.getColumnModel().getColumn(0).setResizable(false);
		tableDirectory0.getColumnModel().getColumn(1).setResizable(false);
		tableDirectory0.getColumnModel().getColumn(2).setResizable(false);
		tableDirectory0.getColumnModel().getColumn(3).setResizable(false);
		scrollDirectory0.setViewportView(tableDirectory0);
		
		JPanel panelDirectory1 = new JPanel();
		panelDirectory1.setLayout(null);
		panelDirectory1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Processor 1 directory", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelDirectory1.setBounds(517, 203, 147, 187);
		contentPane.add(panelDirectory1);
		
		JScrollPane scrollDirectory1 = new JScrollPane();
		scrollDirectory1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollDirectory1.setBounds(10, 22, 126, 154);
		panelDirectory1.add(scrollDirectory1);
		
		tableDirectory1 = new JTable();
		tableDirectory1.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"S", "C0", "C1", "C2"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableDirectory1.getColumnModel().getColumn(0).setResizable(false);
		tableDirectory1.getColumnModel().getColumn(1).setResizable(false);
		tableDirectory1.getColumnModel().getColumn(2).setResizable(false);
		tableDirectory1.getColumnModel().getColumn(3).setResizable(false);
		scrollDirectory1.setViewportView(tableDirectory1);
		
		JPanel panelSharedMemory = new JPanel();
		panelSharedMemory.setLayout(null);
		panelSharedMemory.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Full shared memory area", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelSharedMemory.setBounds(674, 11, 367, 379);
		contentPane.add(panelSharedMemory);
		
		tableSharedMemory = new JTable();
		tableSharedMemory.setRowHeight(21);
		tableSharedMemory.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableSharedMemory.getColumnModel().getColumn(0).setResizable(false);
		tableSharedMemory.getColumnModel().getColumn(1).setResizable(false);
		tableSharedMemory.getColumnModel().getColumn(2).setResizable(false);
		tableSharedMemory.getColumnModel().getColumn(3).setResizable(false);
		tableSharedMemory.getColumnModel().getColumn(4).setResizable(false);
		tableSharedMemory.getColumnModel().getColumn(5).setResizable(false);
		tableSharedMemory.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableSharedMemory.setBounds(10, 27, 347, 336);
		panelSharedMemory.add(tableSharedMemory);
		
		JPanel panelThreads = new JPanel();
		panelThreads.setBorder(new TitledBorder(null, "Threads information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelThreads.setBounds(10, 401, 1031, 318);
		contentPane.add(panelThreads);
		panelThreads.setLayout(null);
		
		JPanel panelRegisters0 = new JPanel();
		panelRegisters0.setBounds(10, 146, 330, 163);
		panelThreads.add(panelRegisters0);
		panelRegisters0.setLayout(null);
		panelRegisters0.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Core 0 registers", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		tableRegisters0 = new JTable();
		tableRegisters0.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableRegisters0.getColumnModel().getColumn(0).setResizable(false);
		tableRegisters0.getColumnModel().getColumn(1).setResizable(false);
		tableRegisters0.getColumnModel().getColumn(2).setResizable(false);
		tableRegisters0.getColumnModel().getColumn(3).setResizable(false);
		tableRegisters0.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableRegisters0.setBounds(10, 21, 310, 128);
		panelRegisters0.add(tableRegisters0);
		
		JPanel panelRegisters1 = new JPanel();
		panelRegisters1.setBounds(350, 146, 330, 163);
		panelThreads.add(panelRegisters1);
		panelRegisters1.setLayout(null);
		panelRegisters1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Core 1 registers", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		tableRegisters1 = new JTable();
		tableRegisters1.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableRegisters1.getColumnModel().getColumn(0).setResizable(false);
		tableRegisters1.getColumnModel().getColumn(1).setResizable(false);
		tableRegisters1.getColumnModel().getColumn(2).setResizable(false);
		tableRegisters1.getColumnModel().getColumn(3).setResizable(false);
		tableRegisters1.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableRegisters1.setBounds(10, 21, 310, 128);
		panelRegisters1.add(tableRegisters1);
		
		JPanel panelRegisters2 = new JPanel();
		panelRegisters2.setBounds(690, 146, 330, 163);
		panelThreads.add(panelRegisters2);
		panelRegisters2.setLayout(null);
		panelRegisters2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Core 2 registers", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		tableRegisters2 = new JTable();
		tableRegisters2.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableRegisters2.getColumnModel().getColumn(0).setResizable(false);
		tableRegisters2.getColumnModel().getColumn(1).setResizable(false);
		tableRegisters2.getColumnModel().getColumn(2).setResizable(false);
		tableRegisters2.getColumnModel().getColumn(3).setResizable(false);
		tableRegisters2.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableRegisters2.setBounds(10, 24, 310, 128);
		panelRegisters2.add(tableRegisters2);
		
		JLabel lblSelectAThread = new JLabel("Select a thread:");
		lblSelectAThread.setBounds(10, 21, 77, 14);
		panelThreads.add(lblSelectAThread);
		
		JComboBox comboBoxThread = new JComboBox();
		comboBoxThread.setBounds(97, 18, 41, 20);
		panelThreads.add(comboBoxThread);
		
		JLabel lblTotalCycles = new JLabel("Total cycles");
		lblTotalCycles.setBounds(10, 46, 128, 14);
		panelThreads.add(lblTotalCycles);
		
		JLabel lblExecutedInProcessor = new JLabel("Executed in processor No.:");
		lblExecutedInProcessor.setBounds(10, 71, 130, 14);
		panelThreads.add(lblExecutedInProcessor);
		
		JLabel lblStaringClockValue = new JLabel("Staring clock value:");
		lblStaringClockValue.setBounds(10, 96, 128, 14);
		panelThreads.add(lblStaringClockValue);
		
		JLabel lblEndingClockValue = new JLabel("Ending clock value:");
		lblEndingClockValue.setBounds(10, 121, 128, 14);
		panelThreads.add(lblEndingClockValue);
		
		JLabel dinLblCycles = new JLabel("<cycles>");
		dinLblCycles.setBounds(197, 46, 72, 14);
		panelThreads.add(dinLblCycles);
		
		JLabel dinLblProcessor = new JLabel("<processor>");
		dinLblProcessor.setBounds(197, 71, 70, 14);
		panelThreads.add(dinLblProcessor);
		
		JLabel dinLblStart = new JLabel("<start>");
		dinLblStart.setBounds(197, 96, 72, 14);
		panelThreads.add(dinLblStart);
		
		JLabel dinLblEnd = new JLabel("<end>");
		dinLblEnd.setBounds(197, 121, 72, 14);
		panelThreads.add(dinLblEnd);
		
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(952, 733, 89, 23);
		contentPane.add(btnOk);
	}
}
