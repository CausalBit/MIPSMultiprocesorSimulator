import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.border.CompoundBorder;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.JTabbedPane;
import javax.swing.JButton;

public class DataViewerWindow extends JFrame {

	private JPanel contentPane;
	private JTable tableChache0;
	private JTable tableRegisters0;
	private JTable tableDirectory0;
	private JTable tableSharedMemory;
	private JTable tableMemory0;
	private JTable tableCache1;
	private JTable tableRegisters2;
	private JTable tableCache2;
	private JTable tableDirectory1;
	private JTable tableMemory1;
	private JTable tableRegisters1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataViewerWindow frame = new DataViewerWindow();
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
	public DataViewerWindow() {
		setResizable(false);
		setTitle("Data Viewer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1057, 842);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelProcessor0 = new JPanel();
		panelProcessor0.setBounds(10, 11, 676, 458);
		contentPane.add(panelProcessor0);
		panelProcessor0.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Processor 0", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelProcessor0.setLayout(null);
		
		JPanel panelCore0 = new JPanel();
		panelCore0.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Core 0", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCore0.setBounds(10, 21, 323, 428);
		panelProcessor0.add(panelCore0);
		panelCore0.setLayout(null);
		
		JPanel panelCache0 = new JPanel();
		panelCache0.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Data cache", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCache0.setBounds(10, 70, 146, 347);
		panelCore0.add(panelCache0);
		panelCache0.setLayout(null);
		
		JScrollPane scrollCache0 = new JScrollPane();
		scrollCache0.setWheelScrollingEnabled(false);
		scrollCache0.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollCache0.setBounds(10, 21, 126, 315);
		panelCache0.add(scrollCache0);
		
		tableChache0 = new JTable();
		scrollCache0.setViewportView(tableChache0);
		tableChache0.setModel(new DefaultTableModel(
			new Object[][] {
				{"", null, null, null},
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
		tableChache0.getColumnModel().getColumn(0).setResizable(false);
		tableChache0.getColumnModel().getColumn(1).setResizable(false);
		tableChache0.getColumnModel().getColumn(2).setResizable(false);
		tableChache0.getColumnModel().getColumn(3).setResizable(false);
		
		JLabel lblCurrentThread0 = new JLabel("Current thread:");
		lblCurrentThread0.setBounds(10, 46, 83, 14);
		panelCore0.add(lblCurrentThread0);
		
		JLabel dinLblThread0 = new JLabel("<thread>");
		dinLblThread0.setBounds(103, 45, 53, 14);
		panelCore0.add(dinLblThread0);
		
		JLabel lblCurrentCycle0 = new JLabel("Current cycle:");
		lblCurrentCycle0.setBounds(10, 21, 83, 14);
		panelCore0.add(lblCurrentCycle0);
		
		JLabel dinLblCycle0 = new JLabel("<cycle>");
		dinLblCycle0.setBounds(103, 21, 46, 14);
		panelCore0.add(dinLblCycle0);
		
		JPanel panelRegisters0 = new JPanel();
		panelRegisters0.setBounds(166, 11, 146, 406);
		panelCore0.add(panelRegisters0);
		panelRegisters0.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Core registers", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelRegisters0.setLayout(null);
		
		tableRegisters0 = new JTable();
		tableRegisters0.setRowHeight(19);
		tableRegisters0.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableRegisters0.setBounds(10, 91, 126, 304);
		panelRegisters0.add(tableRegisters0);
		tableRegisters0.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				"New column", "New column"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableRegisters0.getColumnModel().getColumn(1).setResizable(false);
		
		JLabel lblPcRegister0 = new JLabel("PC register:");
		lblPcRegister0.setBounds(10, 33, 71, 14);
		panelRegisters0.add(lblPcRegister0);
		
		JLabel dinLblPc0 = new JLabel("<pc>");
		dinLblPc0.setBounds(90, 33, 46, 14);
		panelRegisters0.add(dinLblPc0);
		
		JPanel panelCore1 = new JPanel();
		panelCore1.setLayout(null);
		panelCore1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Core 1", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCore1.setBounds(343, 21, 323, 428);
		panelProcessor0.add(panelCore1);
		
		JPanel panelCache1 = new JPanel();
		panelCache1.setLayout(null);
		panelCache1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Data cache", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCache1.setBounds(10, 70, 146, 347);
		panelCore1.add(panelCache1);
		
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
		
		JLabel lblCurrentThread1 = new JLabel("Current thread:");
		lblCurrentThread1.setBounds(10, 46, 83, 14);
		panelCore1.add(lblCurrentThread1);
		
		JLabel dinLblThread1 = new JLabel("<thread>");
		dinLblThread1.setBounds(103, 45, 53, 14);
		panelCore1.add(dinLblThread1);
		
		JLabel lblCurrentCycle1 = new JLabel("Current cycle:");
		lblCurrentCycle1.setBounds(10, 21, 83, 14);
		panelCore1.add(lblCurrentCycle1);
		
		JLabel dinLblCycle1 = new JLabel("<cycle>");
		dinLblCycle1.setBounds(103, 21, 46, 14);
		panelCore1.add(dinLblCycle1);
		
		JPanel panelRegisters1 = new JPanel();
		panelRegisters1.setLayout(null);
		panelRegisters1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Core registers", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelRegisters1.setBounds(166, 11, 146, 406);
		panelCore1.add(panelRegisters1);
		
		JLabel lblPcRegister1 = new JLabel("PC register:");
		lblPcRegister1.setBounds(10, 33, 71, 14);
		panelRegisters1.add(lblPcRegister1);
		
		JLabel dinLblPc1 = new JLabel("<pc>");
		dinLblPc1.setBounds(91, 33, 46, 14);
		panelRegisters1.add(dinLblPc1);
		
		tableRegisters1 = new JTable();
		tableRegisters1.setRowHeight(19);
		tableRegisters1.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				"New column", "New column"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableRegisters1.getColumnModel().getColumn(0).setResizable(false);
		tableRegisters1.getColumnModel().getColumn(1).setResizable(false);
		tableRegisters1.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableRegisters1.setBounds(10, 91, 126, 304);
		panelRegisters1.add(tableRegisters1);
		
		JPanel panelProcesor1 = new JPanel();
		panelProcesor1.setBounds(696, 11, 345, 458);
		contentPane.add(panelProcesor1);
		panelProcesor1.setLayout(null);
		panelProcesor1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Processor 1", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JPanel panelCore2 = new JPanel();
		panelCore2.setLayout(null);
		panelCore2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Core 2", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCore2.setBounds(10, 21, 323, 426);
		panelProcesor1.add(panelCore2);
		
		JPanel panelCache2 = new JPanel();
		panelCache2.setLayout(null);
		panelCache2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Data cache", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCache2.setBounds(10, 70, 146, 345);
		panelCore2.add(panelCache2);
		
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
		
		JLabel lblCurrentThread2 = new JLabel("Current thread:");
		lblCurrentThread2.setBounds(10, 46, 83, 14);
		panelCore2.add(lblCurrentThread2);
		
		JLabel dinLblThread2 = new JLabel("<thread>");
		dinLblThread2.setBounds(103, 45, 53, 14);
		panelCore2.add(dinLblThread2);
		
		JLabel lblCurrentCycle2 = new JLabel("Current cycle:");
		lblCurrentCycle2.setBounds(10, 21, 83, 14);
		panelCore2.add(lblCurrentCycle2);
		
		JLabel dinLblCycle2 = new JLabel("<cycle>");
		dinLblCycle2.setBounds(103, 21, 46, 14);
		panelCore2.add(dinLblCycle2);
		
		JPanel panelRegisters2 = new JPanel();
		panelRegisters2.setLayout(null);
		panelRegisters2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Core registers", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelRegisters2.setBounds(166, 11, 146, 404);
		panelCore2.add(panelRegisters2);
		
		tableRegisters2 = new JTable();
		tableRegisters2.setRowHeight(19);
		tableRegisters2.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				"New column", "New column"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableRegisters2.getColumnModel().getColumn(0).setResizable(false);
		tableRegisters2.getColumnModel().getColumn(1).setResizable(false);
		tableRegisters2.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableRegisters2.setBounds(10, 89, 126, 304);
		panelRegisters2.add(tableRegisters2);
		
		JLabel lblPcRegister2 = new JLabel("PC register:");
		lblPcRegister2.setBounds(10, 33, 71, 14);
		panelRegisters2.add(lblPcRegister2);
		
		JLabel dinLblPc2 = new JLabel("<pc>");
		dinLblPc2.setBounds(91, 33, 46, 14);
		panelRegisters2.add(dinLblPc2);
		
		JButton btnStop = new JButton("Stop");
		btnStop.setBounds(952, 780, 89, 23);
		contentPane.add(btnStop);
		
		JPanel panelDirectory1 = new JPanel();
		panelDirectory1.setBounds(167, 480, 147, 187);
		contentPane.add(panelDirectory1);
		panelDirectory1.setLayout(null);
		panelDirectory1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Processor 1 directory", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
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
		
		JPanel panelMemory1 = new JPanel();
		panelMemory1.setBounds(687, 480, 354, 187);
		contentPane.add(panelMemory1);
		panelMemory1.setLayout(null);
		panelMemory1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Processor 1 shared data memory", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		tableMemory1 = new JTable();
		tableMemory1.setModel(new DefaultTableModel(
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
		tableMemory1.getColumnModel().getColumn(0).setResizable(false);
		tableMemory1.getColumnModel().getColumn(1).setResizable(false);
		tableMemory1.getColumnModel().getColumn(2).setResizable(false);
		tableMemory1.getColumnModel().getColumn(3).setResizable(false);
		tableMemory1.setRowHeight(19);
		tableMemory1.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableMemory1.setBounds(10, 21, 334, 152);
		panelMemory1.add(tableMemory1);
		
		JPanel panelSharedMemory = new JPanel();
		panelSharedMemory.setBounds(10, 678, 1031, 99);
		contentPane.add(panelSharedMemory);
		panelSharedMemory.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Full shared memory area", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelSharedMemory.setLayout(null);
		
		tableSharedMemory = new JTable();
		tableSharedMemory.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableSharedMemory.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
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
		tableSharedMemory.getColumnModel().getColumn(6).setResizable(false);
		tableSharedMemory.getColumnModel().getColumn(7).setResizable(false);
		tableSharedMemory.getColumnModel().getColumn(8).setResizable(false);
		tableSharedMemory.getColumnModel().getColumn(9).setResizable(false);
		tableSharedMemory.getColumnModel().getColumn(10).setResizable(false);
		tableSharedMemory.getColumnModel().getColumn(11).setResizable(false);
		tableSharedMemory.getColumnModel().getColumn(12).setResizable(false);
		tableSharedMemory.getColumnModel().getColumn(13).setResizable(false);
		tableSharedMemory.getColumnModel().getColumn(14).setResizable(false);
		tableSharedMemory.getColumnModel().getColumn(15).setResizable(false);
		tableSharedMemory.getColumnModel().getColumn(16).setResizable(false);
		tableSharedMemory.getColumnModel().getColumn(17).setResizable(false);
		tableSharedMemory.getColumnModel().getColumn(18).setResizable(false);
		tableSharedMemory.getColumnModel().getColumn(19).setResizable(false);
		tableSharedMemory.getColumnModel().getColumn(20).setResizable(false);
		tableSharedMemory.getColumnModel().getColumn(21).setResizable(false);
		tableSharedMemory.getColumnModel().getColumn(22).setResizable(false);
		tableSharedMemory.getColumnModel().getColumn(23).setResizable(false);
		tableSharedMemory.setBounds(10, 21, 1011, 64);
		panelSharedMemory.add(tableSharedMemory);
		
		JPanel panelMemory0 = new JPanel();
		panelMemory0.setBounds(324, 480, 354, 187);
		contentPane.add(panelMemory0);
		panelMemory0.setLayout(null);
		panelMemory0.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Processor 0 shared data memory", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		tableMemory0 = new JTable();
		tableMemory0.setRowHeight(19);
		tableMemory0.setModel(new DefaultTableModel(
			new Object[][] {
				{"", null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableMemory0.getColumnModel().getColumn(0).setResizable(false);
		tableMemory0.getColumnModel().getColumn(1).setResizable(false);
		tableMemory0.getColumnModel().getColumn(2).setResizable(false);
		tableMemory0.getColumnModel().getColumn(3).setResizable(false);
		tableMemory0.getColumnModel().getColumn(4).setResizable(false);
		tableMemory0.getColumnModel().getColumn(5).setResizable(false);
		tableMemory0.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableMemory0.setBounds(10, 21, 334, 152);
		panelMemory0.add(tableMemory0);
		
		JPanel panelDirectory0 = new JPanel();
		panelDirectory0.setBounds(10, 480, 147, 187);
		contentPane.add(panelDirectory0);
		panelDirectory0.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Processor 0 directory", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelDirectory0.setLayout(null);
		
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
	}
}
