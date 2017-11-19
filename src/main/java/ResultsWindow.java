import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class ResultsWindow extends JFrame {
	private JPanel contentPane;
	private JTable tableRegisters;
	private JTable tableCache0;
	private JTable tableCache1;
	private JTable tableCache2;
	private JTable tableSharedMemory;
	private JTable tableDirectory0;
	private JTable tableDirectory1;
	private JLabel dinLblPc;
	private JLabel dinLblCore;
	private JLabel dinLblProcessor;
	private JLabel dinLblStart;
	private JLabel dinLblEnd;
	private JLabel dinLblCycles;
	private JComboBox comboBoxThread;
	private HashMap<String, int[]> registersContent;

	/**
	 * Create the frame.
	 */
	public ResultsWindow() {
		registersContent = new HashMap<String, int[]>();

		setTitle("Results");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 918, 466);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panelProcessor0 = new JPanel();
		panelProcessor0.setLayout(null);
		panelProcessor0.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Processor 0", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelProcessor0.setBounds(10, 11, 323, 187);
		contentPane.add(panelProcessor0);

		JPanel panelCache0 = new JPanel();
		panelCache0.setBounds(10, 21, 146, 155);
		panelProcessor0.add(panelCache0);
		panelCache0.setLayout(null);
		panelCache0.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Core 0 data cache", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		JScrollPane scrollCache0 = new JScrollPane();
		scrollCache0.setWheelScrollingEnabled(false);
		scrollCache0.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollCache0.setBounds(10, 21, 126, 122);
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
		panelCache1.setBounds(166, 21, 146, 155);
		panelProcessor0.add(panelCache1);
		panelCache1.setLayout(null);
		panelCache1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Core 1 data cache", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		JScrollPane scrollCache1 = new JScrollPane();
		scrollCache1.setWheelScrollingEnabled(false);
		scrollCache1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollCache1.setBounds(10, 21, 126, 122);
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
		panelProcessor1.setBounds(343, 11, 167, 187);
		contentPane.add(panelProcessor1);

		JPanel panelCache2 = new JPanel();
		panelCache2.setBounds(10, 21, 146, 155);
		panelProcessor1.add(panelCache2);
		panelCache2.setLayout(null);
		panelCache2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Core 2 data cache", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		JScrollPane scrollCache2 = new JScrollPane();
		scrollCache2.setWheelScrollingEnabled(false);
		scrollCache2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollCache2.setBounds(10, 21, 126, 122);
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
		panelSharedMemory.setBounds(10, 229, 500, 161);
		contentPane.add(panelSharedMemory);

		tableSharedMemory = new JTable();
		tableSharedMemory.setModel(new DefaultTableModel(
				new Object[][] {
						{null, null, null, null, null, null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null, null, null, null, null, null},
				},
				new String[] {
						"New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column"
				}
		) {
			boolean[] columnEditables = new boolean[] {
					false, false, false, false, false, false, false, false, false, false, false, false
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
		tableSharedMemory.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableSharedMemory.setBounds(10, 21, 480, 128);
		panelSharedMemory.add(tableSharedMemory);

		JPanel panelThreads = new JPanel();
		panelThreads.setBorder(new TitledBorder(null, "Threads information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelThreads.setBounds(674, 11, 229, 379);
		contentPane.add(panelThreads);
		panelThreads.setLayout(null);

		JPanel panelRegisters = new JPanel();
		panelRegisters.setBounds(10, 177, 210, 191);
		panelThreads.add(panelRegisters);
		panelRegisters.setLayout(null);
		panelRegisters.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Core registers", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		tableRegisters = new JTable();
		tableRegisters.setModel(new DefaultTableModel(
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
		tableRegisters.getColumnModel().getColumn(0).setResizable(false);
		tableRegisters.getColumnModel().getColumn(1).setResizable(false);
		tableRegisters.getColumnModel().getColumn(2).setResizable(false);
		tableRegisters.getColumnModel().getColumn(3).setResizable(false);
		tableRegisters.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableRegisters.setBounds(10, 52, 190, 128);
		panelRegisters.add(tableRegisters);

		JLabel lblPcRegister0 = new JLabel("PC Register:");
		lblPcRegister0.setBounds(10, 21, 117, 14);
		panelRegisters.add(lblPcRegister0);

		dinLblPc = new JLabel("N/A");
		dinLblPc.setBounds(139, 21, 46, 14);
		panelRegisters.add(dinLblPc);

		comboBoxThread = new JComboBox();
		comboBoxThread.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = comboBoxThread.getSelectedIndex();
				if((selected != 0)){
					fillRegistersTable(comboBoxThread.getItemAt(selected).toString());
				}else{
					clearRegistersTable();
				}
			}
		});


		comboBoxThread.setBounds(10, 21, 128, 20);
		panelThreads.add(comboBoxThread);

		JLabel lblExecutedInProcessor = new JLabel("Executed in processor No.:");
		lblExecutedInProcessor.setBounds(10, 77, 130, 14);
		panelThreads.add(lblExecutedInProcessor);

		JLabel lblStaringClockValue = new JLabel("Staring clock value:");
		lblStaringClockValue.setBounds(10, 127, 128, 14);
		panelThreads.add(lblStaringClockValue);

		JLabel lblEndingClockValue = new JLabel("Ending clock value:");
		lblEndingClockValue.setBounds(10, 152, 128, 14);
		panelThreads.add(lblEndingClockValue);

		dinLblProcessor = new JLabel("N/A");
		dinLblProcessor.setBounds(150, 77, 70, 14);
		panelThreads.add(dinLblProcessor);

		dinLblStart = new JLabel("N/A");
		dinLblStart.setBounds(150, 127, 57, 14);
		panelThreads.add(dinLblStart);

		dinLblEnd = new JLabel("N/A");
		dinLblEnd.setBounds(150, 152, 72, 14);
		panelThreads.add(dinLblEnd);

		JLabel lblExecutedInCore = new JLabel("Executed in core No.:");
		lblExecutedInCore.setBounds(10, 102, 130, 14);
		panelThreads.add(lblExecutedInCore);

		dinLblCore = new JLabel("N/A");
		dinLblCore.setBounds(150, 102, 70, 14);
		panelThreads.add(dinLblCore);

		JLabel lblTotalCycles = new JLabel("Total cycles");
		lblTotalCycles.setBounds(10, 52, 128, 14);
		panelThreads.add(lblTotalCycles);

		dinLblCycles = new JLabel("N/A");
		dinLblCycles.setBounds(150, 52, 72, 14);
		panelThreads.add(dinLblCycles);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnOk.setBounds(813, 404, 89, 23);
		contentPane.add(btnOk);
	}

	public void saveFinalRegisters(HashMap<String,int[]> registers){
		this.registersContent = registers;
		this.fillNamesComboBox();
	}

	private void fillNamesComboBox(){
		String[] names = new String[registersContent.size()+1];
		names[0] = "Select a thread (program)";
		int index = 1;
		for(Map.Entry<String, int[]> entry : registersContent.entrySet()){
			names[index] = entry.getKey();
			index++;
		}
		this.comboBoxThread.setModel(new DefaultComboBoxModel(names));
	}

	private void fillRegistersTable(String name){
		int[] register = registersContent.get(name);

		int index = 0;

		for(int i = 0; i < this.tableRegisters.getRowCount(); i++){
			for(int j = 0; j < this.tableRegisters.getColumnCount(); j++){
				if(register[index] != -1) {
					this.tableRegisters.setValueAt(register[index], i, j);
				}else{
					this.tableRegisters.setValueAt(" ", i, j);
				}
				index++;
			}
		}

		this.dinLblPc.setText(""+register[32]);
		this.dinLblCore.setText(""+register[33]);
		this.dinLblCycles.setText(""+register[34]);
		this.dinLblEnd.setText(""+register[35]);
		this.dinLblStart.setText(""+(register[35]-register[34]));

		if((register[33] == 0) || (register[33] == 1)){
			this.dinLblProcessor.setText("0");
		}else{
			this.dinLblProcessor.setText("1");
		}
	}

	private void clearRegistersTable() {
		for (int i = 0; i < this.tableRegisters.getRowCount(); i++) {
			for (int j = 0; j < this.tableRegisters.getColumnCount(); j++) {
				this.tableRegisters.setValueAt(" ", i, j);
			}
		}

		this.dinLblPc.setText("N/A");
		this.dinLblCore.setText("N/A");
		this.dinLblCycles.setText("N/A");
		this.dinLblEnd.setText("N/A");
		this.dinLblStart.setText("N/A");
		this.dinLblProcessor.setText("N/A");
	}

	public void writeMemoryData(ArrayList<int[]> p0Memory, ArrayList<int[]> p1Memory){
		int[] fullMemory = new int[96];
		int index = 0;
		this.setFullMemory(p0Memory, fullMemory, index);
		index = 64;
		this.setFullMemory(p1Memory, fullMemory, index);

		index = 0;
		for(int i = 0; i < this.tableSharedMemory.getRowCount(); i++){
			for(int j = 0; j < this.tableSharedMemory.getColumnCount(); j++){
				this.tableSharedMemory.setValueAt(fullMemory[index], i, j);
				index++;
			}
		}
	}

	private void setFullMemory(ArrayList<int[]> pMemory, int[] fullMemory, int index){
		for(int i = 0; i < pMemory.size(); i++){
			int[] currentArray = pMemory.get(i);
			for(int j = 0; j < currentArray.length; j++){
				fullMemory[index] = currentArray[j];
				index++;
			}
		}
	}
}
