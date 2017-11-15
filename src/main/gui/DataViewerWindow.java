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
	private JTable table;
	private JTable table_2;
	private JTable table_3;
	private JTable table_4;
	private JTable table_6;
	private JTable table_5;
	private JTable table_13;
	private JTable table_1;
	private JTable table_7;
	private JTable table_9;
	private JTable table_10;

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
		setTitle("Data Viewer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1169, 748);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelProcessor0 = new JPanel();
		panelProcessor0.setBounds(10, 11, 1133, 267);
		contentPane.add(panelProcessor0);
		panelProcessor0.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Procesor 0", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelProcessor0.setLayout(null);
		
		JPanel panelCore0 = new JPanel();
		panelCore0.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Core 0", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCore0.setBounds(10, 21, 323, 235);
		panelProcessor0.add(panelCore0);
		panelCore0.setLayout(null);
		
		JPanel panelCache00 = new JPanel();
		panelCache00.setBorder(new TitledBorder(null, "Data Cache", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCache00.setBounds(10, 70, 146, 154);
		panelCore0.add(panelCache00);
		panelCache00.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setWheelScrollingEnabled(false);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 21, 126, 122);
		panelCache00.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"", null, null, null},
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
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		
		JLabel lblCurrentThread0 = new JLabel("Current thread:");
		lblCurrentThread0.setBounds(10, 46, 83, 14);
		panelCore0.add(lblCurrentThread0);
		
		JLabel label = new JLabel("<thread>");
		label.setBounds(103, 45, 53, 14);
		panelCore0.add(label);
		
		JLabel lblCurrentCycle0 = new JLabel("Current cycle:");
		lblCurrentCycle0.setBounds(10, 21, 83, 14);
		panelCore0.add(lblCurrentCycle0);
		
		JLabel label_1 = new JLabel("<cycle>");
		label_1.setBounds(103, 21, 46, 14);
		panelCore0.add(label_1);
		
		JPanel panelRegisters00 = new JPanel();
		panelRegisters00.setBounds(166, 11, 146, 213);
		panelCore0.add(panelRegisters00);
		panelRegisters00.setBorder(new TitledBorder(null, "CPU Registers", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelRegisters00.setLayout(null);
		
		table_2 = new JTable();
		table_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		table_2.setBounds(10, 74, 126, 128);
		panelRegisters00.add(table_2);
		table_2.setModel(new DefaultTableModel(
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
				true, true, true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		JLabel lblPcRegister0 = new JLabel("PC Register:");
		lblPcRegister0.setBounds(10, 33, 71, 14);
		panelRegisters00.add(lblPcRegister0);
		
		JLabel label_10 = new JLabel("<pc>");
		label_10.setBounds(91, 33, 46, 14);
		panelRegisters00.add(label_10);
		table_2.getColumnModel().getColumn(3).setResizable(false);
		
		JPanel panelDirectory0 = new JPanel();
		panelDirectory0.setBorder(new TitledBorder(null, "CPU Directory", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDirectory0.setBounds(676, 21, 147, 235);
		panelProcessor0.add(panelDirectory0);
		panelDirectory0.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_2.setBounds(10, 22, 126, 202);
		panelDirectory0.add(scrollPane_2);
		
		table_3 = new JTable();
		table_3.setRowHeight(22);
		table_3.setModel(new DefaultTableModel(
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
		table_3.getColumnModel().getColumn(0).setResizable(false);
		table_3.getColumnModel().getColumn(1).setResizable(false);
		table_3.getColumnModel().getColumn(2).setResizable(false);
		table_3.getColumnModel().getColumn(3).setResizable(false);
		scrollPane_2.setViewportView(table_3);
		
		JPanel panelMemory0 = new JPanel();
		panelMemory0.setLayout(null);
		panelMemory0.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Shared data memory", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelMemory0.setBounds(833, 21, 290, 235);
		panelProcessor0.add(panelMemory0);
		
		table_6 = new JTable();
		table_6.setRowHeight(26);
		table_6.setModel(new DefaultTableModel(
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
		table_6.getColumnModel().getColumn(0).setResizable(false);
		table_6.getColumnModel().getColumn(1).setResizable(false);
		table_6.getColumnModel().getColumn(2).setResizable(false);
		table_6.getColumnModel().getColumn(3).setResizable(false);
		table_6.getColumnModel().getColumn(4).setResizable(false);
		table_6.getColumnModel().getColumn(5).setResizable(false);
		table_6.setBorder(new LineBorder(new Color(0, 0, 0)));
		table_6.setBounds(10, 21, 270, 203);
		panelMemory0.add(table_6);
		
		JPanel panelCore1 = new JPanel();
		panelCore1.setLayout(null);
		panelCore1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Core 1", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCore1.setBounds(343, 21, 323, 235);
		panelProcessor0.add(panelCore1);
		
		JPanel panelCache01 = new JPanel();
		panelCache01.setLayout(null);
		panelCache01.setBorder(new TitledBorder(null, "Data Cache", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCache01.setBounds(10, 70, 146, 154);
		panelCore1.add(panelCache01);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setWheelScrollingEnabled(false);
		scrollPane_4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_4.setBounds(10, 21, 126, 122);
		panelCache01.add(scrollPane_4);
		
		table_13 = new JTable();
		table_13.setModel(new DefaultTableModel(
			new Object[][] {
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
		table_13.getColumnModel().getColumn(0).setResizable(false);
		table_13.getColumnModel().getColumn(1).setResizable(false);
		table_13.getColumnModel().getColumn(2).setResizable(false);
		table_13.getColumnModel().getColumn(3).setResizable(false);
		scrollPane_4.setViewportView(table_13);
		
		JLabel label_13 = new JLabel("Current thread:");
		label_13.setBounds(10, 46, 83, 14);
		panelCore1.add(label_13);
		
		JLabel label_14 = new JLabel("<thread>");
		label_14.setBounds(103, 45, 53, 14);
		panelCore1.add(label_14);
		
		JLabel label_15 = new JLabel("Current cycle:");
		label_15.setBounds(10, 21, 83, 14);
		panelCore1.add(label_15);
		
		JLabel label_16 = new JLabel("<cycle>");
		label_16.setBounds(103, 21, 46, 14);
		panelCore1.add(label_16);
		
		JPanel panelRegisters01 = new JPanel();
		panelRegisters01.setLayout(null);
		panelRegisters01.setBorder(new TitledBorder(null, "CPU Registers", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelRegisters01.setBounds(166, 11, 146, 213);
		panelCore1.add(panelRegisters01);
		
		table_5 = new JTable();
		table_5.setModel(new DefaultTableModel(
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
		table_5.getColumnModel().getColumn(0).setResizable(false);
		table_5.getColumnModel().getColumn(1).setResizable(false);
		table_5.getColumnModel().getColumn(2).setResizable(false);
		table_5.getColumnModel().getColumn(3).setResizable(false);
		table_5.setBorder(new LineBorder(new Color(0, 0, 0)));
		table_5.setBounds(10, 74, 126, 128);
		panelRegisters01.add(table_5);
		
		JLabel label_17 = new JLabel("PC Register:");
		label_17.setBounds(10, 33, 71, 14);
		panelRegisters01.add(label_17);
		
		JLabel label_18 = new JLabel("<pc>");
		label_18.setBounds(91, 33, 46, 14);
		panelRegisters01.add(label_18);
		
		JPanel panelProcesor1 = new JPanel();
		panelProcesor1.setBounds(10, 289, 1133, 267);
		contentPane.add(panelProcesor1);
		panelProcesor1.setLayout(null);
		panelProcesor1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Procesor 1", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JPanel panelCore2 = new JPanel();
		panelCore2.setLayout(null);
		panelCore2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Core 2", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCore2.setBounds(10, 21, 323, 235);
		panelProcesor1.add(panelCore2);
		
		JPanel panelCache1 = new JPanel();
		panelCache1.setLayout(null);
		panelCache1.setBorder(new TitledBorder(null, "Data Cache", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCache1.setBounds(10, 70, 146, 154);
		panelCore2.add(panelCache1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setWheelScrollingEnabled(false);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(10, 21, 126, 122);
		panelCache1.add(scrollPane_1);
		
		table_7 = new JTable();
		table_7.setModel(new DefaultTableModel(
			new Object[][] {
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
		table_7.getColumnModel().getColumn(0).setResizable(false);
		table_7.getColumnModel().getColumn(1).setResizable(false);
		table_7.getColumnModel().getColumn(2).setResizable(false);
		table_7.getColumnModel().getColumn(3).setResizable(false);
		scrollPane_1.setViewportView(table_7);
		
		JLabel label_2 = new JLabel("Current thread:");
		label_2.setBounds(10, 46, 83, 14);
		panelCore2.add(label_2);
		
		JLabel label_3 = new JLabel("<thread>");
		label_3.setBounds(103, 45, 53, 14);
		panelCore2.add(label_3);
		
		JLabel label_4 = new JLabel("Current cycle:");
		label_4.setBounds(10, 21, 83, 14);
		panelCore2.add(label_4);
		
		JLabel label_5 = new JLabel("<cycle>");
		label_5.setBounds(103, 21, 46, 14);
		panelCore2.add(label_5);
		
		JPanel panelRegisters1 = new JPanel();
		panelRegisters1.setLayout(null);
		panelRegisters1.setBorder(new TitledBorder(null, "CPU Registers", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelRegisters1.setBounds(166, 11, 146, 213);
		panelCore2.add(panelRegisters1);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
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
		table_1.getColumnModel().getColumn(0).setResizable(false);
		table_1.getColumnModel().getColumn(1).setResizable(false);
		table_1.getColumnModel().getColumn(2).setResizable(false);
		table_1.getColumnModel().getColumn(3).setResizable(false);
		table_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		table_1.setBounds(10, 74, 126, 128);
		panelRegisters1.add(table_1);
		
		JLabel label_11 = new JLabel("PC Register:");
		label_11.setBounds(10, 33, 71, 14);
		panelRegisters1.add(label_11);
		
		JLabel label_12 = new JLabel("<pc>");
		label_12.setBounds(91, 33, 46, 14);
		panelRegisters1.add(label_12);
		
		JPanel panelDirectory1 = new JPanel();
		panelDirectory1.setLayout(null);
		panelDirectory1.setBorder(new TitledBorder(null, "CPU Directory", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDirectory1.setBounds(677, 21, 147, 235);
		panelProcesor1.add(panelDirectory1);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_3.setBounds(10, 22, 126, 202);
		panelDirectory1.add(scrollPane_3);
		
		table_9 = new JTable();
		table_9.setModel(new DefaultTableModel(
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
		table_9.getColumnModel().getColumn(0).setResizable(false);
		table_9.getColumnModel().getColumn(1).setResizable(false);
		table_9.getColumnModel().getColumn(2).setResizable(false);
		table_9.getColumnModel().getColumn(3).setResizable(false);
		table_9.setRowHeight(22);
		scrollPane_3.setViewportView(table_9);
		
		JPanel panelMemory1 = new JPanel();
		panelMemory1.setLayout(null);
		panelMemory1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Shared data memory", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelMemory1.setBounds(834, 21, 290, 235);
		panelProcesor1.add(panelMemory1);
		
		table_10 = new JTable();
		table_10.setModel(new DefaultTableModel(
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
		table_10.getColumnModel().getColumn(0).setResizable(false);
		table_10.getColumnModel().getColumn(1).setResizable(false);
		table_10.getColumnModel().getColumn(2).setResizable(false);
		table_10.getColumnModel().getColumn(3).setResizable(false);
		table_10.setRowHeight(26);
		table_10.setBorder(new LineBorder(new Color(0, 0, 0)));
		table_10.setBounds(10, 21, 270, 203);
		panelMemory1.add(table_10);
		
		JPanel panelSharedMemory = new JPanel();
		panelSharedMemory.setBounds(10, 567, 1133, 99);
		contentPane.add(panelSharedMemory);
		panelSharedMemory.setBorder(new TitledBorder(null, "Shared memory area", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelSharedMemory.setLayout(null);
		
		table_4 = new JTable();
		table_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		table_4.setModel(new DefaultTableModel(
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
		table_4.getColumnModel().getColumn(0).setResizable(false);
		table_4.getColumnModel().getColumn(1).setResizable(false);
		table_4.getColumnModel().getColumn(2).setResizable(false);
		table_4.getColumnModel().getColumn(3).setResizable(false);
		table_4.getColumnModel().getColumn(4).setResizable(false);
		table_4.getColumnModel().getColumn(5).setResizable(false);
		table_4.getColumnModel().getColumn(6).setResizable(false);
		table_4.getColumnModel().getColumn(7).setResizable(false);
		table_4.getColumnModel().getColumn(8).setResizable(false);
		table_4.getColumnModel().getColumn(9).setResizable(false);
		table_4.getColumnModel().getColumn(10).setResizable(false);
		table_4.getColumnModel().getColumn(11).setResizable(false);
		table_4.getColumnModel().getColumn(12).setResizable(false);
		table_4.getColumnModel().getColumn(13).setResizable(false);
		table_4.getColumnModel().getColumn(14).setResizable(false);
		table_4.getColumnModel().getColumn(15).setResizable(false);
		table_4.getColumnModel().getColumn(16).setResizable(false);
		table_4.getColumnModel().getColumn(17).setResizable(false);
		table_4.getColumnModel().getColumn(18).setResizable(false);
		table_4.getColumnModel().getColumn(19).setResizable(false);
		table_4.getColumnModel().getColumn(20).setResizable(false);
		table_4.getColumnModel().getColumn(21).setResizable(false);
		table_4.getColumnModel().getColumn(22).setResizable(false);
		table_4.getColumnModel().getColumn(23).setResizable(false);
		table_4.setBounds(10, 21, 1113, 64);
		panelSharedMemory.add(table_4);
		
		JButton btnNewButton = new JButton("Stop");
		btnNewButton.setBounds(1054, 676, 89, 23);
		contentPane.add(btnNewButton);
	}
}
