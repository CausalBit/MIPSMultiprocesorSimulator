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
	private JTable tableSharedMemory0;
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
	private JTable tableSharedMemory1;

	/**
	 * Create the frame.
	 */
	public ResultsWindow() {
		registersContent = new HashMap<String, int[]>();

		setTitle("Results");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 918, 586);
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
		panelDirectory0.setBounds(517, 11, 147, 315);
		contentPane.add(panelDirectory0);

		JScrollPane scrollDirectory0 = new JScrollPane();
		scrollDirectory0.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollDirectory0.setBounds(10, 22, 127, 282);
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
		panelDirectory1.setBounds(517, 337, 147, 187);
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

		JPanel panelSharedMemory0 = new JPanel();
		panelSharedMemory0.setLayout(null);
		panelSharedMemory0.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Full shared memory area", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelSharedMemory0.setBounds(10, 209, 500, 161);
		contentPane.add(panelSharedMemory0);

		tableSharedMemory0 = new JTable();
		tableSharedMemory0.setModel(new DefaultTableModel(
				new Object[][] {
						{null, null, null, null, null, null, null, null},
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
					false, false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableSharedMemory0.getColumnModel().getColumn(0).setResizable(false);
		tableSharedMemory0.getColumnModel().getColumn(1).setResizable(false);
		tableSharedMemory0.getColumnModel().getColumn(2).setResizable(false);
		tableSharedMemory0.getColumnModel().getColumn(3).setResizable(false);
		tableSharedMemory0.getColumnModel().getColumn(4).setResizable(false);
		tableSharedMemory0.getColumnModel().getColumn(5).setResizable(false);
		tableSharedMemory0.getColumnModel().getColumn(6).setResizable(false);
		tableSharedMemory0.getColumnModel().getColumn(7).setResizable(false);
		tableSharedMemory0.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableSharedMemory0.setBounds(10, 21, 480, 128);
		panelSharedMemory0.add(tableSharedMemory0);

		JPanel panelProgramFiles = new JPanel();
		panelProgramFiles.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Program files information", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelProgramFiles.setBounds(674, 11, 229, 379);
		contentPane.add(panelProgramFiles);
		panelProgramFiles.setLayout(null);

		JPanel panelRegisters = new JPanel();
		panelRegisters.setBounds(10, 177, 210, 191);
		panelProgramFiles.add(panelRegisters);
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
		panelProgramFiles.add(comboBoxThread);

		JLabel lblExecutedInProcessor = new JLabel("Executed in processor No.:");
		lblExecutedInProcessor.setBounds(10, 77, 130, 14);
		panelProgramFiles.add(lblExecutedInProcessor);

		JLabel lblStaringClockValue = new JLabel("Staring clock value:");
		lblStaringClockValue.setBounds(10, 127, 128, 14);
		panelProgramFiles.add(lblStaringClockValue);

		JLabel lblEndingClockValue = new JLabel("Ending clock value:");
		lblEndingClockValue.setBounds(10, 152, 128, 14);
		panelProgramFiles.add(lblEndingClockValue);

		dinLblProcessor = new JLabel("N/A");
		dinLblProcessor.setBounds(150, 77, 70, 14);
		panelProgramFiles.add(dinLblProcessor);

		dinLblStart = new JLabel("N/A");
		dinLblStart.setBounds(150, 127, 57, 14);
		panelProgramFiles.add(dinLblStart);

		dinLblEnd = new JLabel("N/A");
		dinLblEnd.setBounds(150, 152, 72, 14);
		panelProgramFiles.add(dinLblEnd);

		JLabel lblExecutedInCore = new JLabel("Executed in core No.:");
		lblExecutedInCore.setBounds(10, 102, 130, 14);
		panelProgramFiles.add(lblExecutedInCore);

		dinLblCore = new JLabel("N/A");
		dinLblCore.setBounds(150, 102, 70, 14);
		panelProgramFiles.add(dinLblCore);

		JLabel lblTotalCycles = new JLabel("Total cycles");
		lblTotalCycles.setBounds(10, 52, 128, 14);
		panelProgramFiles.add(lblTotalCycles);

		dinLblCycles = new JLabel("N/A");
		dinLblCycles.setBounds(150, 52, 72, 14);
		panelProgramFiles.add(dinLblCycles);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnOk.setBounds(813, 524, 89, 23);
		contentPane.add(btnOk);

		JPanel panelSharedMemory1 = new JPanel();
		panelSharedMemory1.setLayout(null);
		panelSharedMemory1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Full shared memory area", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelSharedMemory1.setBounds(10, 428, 500, 96);
		contentPane.add(panelSharedMemory1);

		tableSharedMemory1 = new JTable();
		tableSharedMemory1.setModel(new DefaultTableModel(
				new Object[][] {
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
					false, false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableSharedMemory1.getColumnModel().getColumn(0).setResizable(false);
		tableSharedMemory1.getColumnModel().getColumn(1).setResizable(false);
		tableSharedMemory1.getColumnModel().getColumn(2).setResizable(false);
		tableSharedMemory1.getColumnModel().getColumn(3).setResizable(false);
		tableSharedMemory1.getColumnModel().getColumn(4).setResizable(false);
		tableSharedMemory1.getColumnModel().getColumn(5).setResizable(false);
		tableSharedMemory1.getColumnModel().getColumn(6).setResizable(false);
		tableSharedMemory1.getColumnModel().getColumn(7).setResizable(false);
		tableSharedMemory1.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableSharedMemory1.setBounds(10, 21, 480, 64);
		panelSharedMemory1.add(tableSharedMemory1);
	}

	public void saveFinalRegisters(HashMap<String,int[]> registers){
		this.registersContent = registers;
		this.fillNamesComboBox();
	}

	private void fillNamesComboBox(){
		String[] names = new String[registersContent.size()+1];
		names[0] = "Select a program file";
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
		for(int i = 0; i < this.tableSharedMemory0.getRowCount(); i++){
			for(int j = 0; j < this.tableSharedMemory0.getColumnCount(); j++){
				this.tableSharedMemory0.setValueAt(fullMemory[index], i, j);
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

	public void writeCacheData(ArrayList<ArrayList<int[]>> cache0, ArrayList<ArrayList<int[]>> cache1,
							   ArrayList<ArrayList<int[]>> cache2, int[][] states){

		this.writeSpecificCache(this.getFlatCache(cache0, states[0]), 0);
		this.writeSpecificCache(this.getFlatCache(cache1, states[1]), 1);
		this.writeSpecificCache(this.getFlatCache(cache2, states[2]), 2);
	}

	private void writeSpecificCache(int[] cacheData, int id){
		JTable cacheTable = null;

		switch (id){
			case 0:
				cacheTable = this.tableCache0;
				break;
			case 1:
				cacheTable = this.tableCache1;
				break;
			case 2:
				cacheTable = this.tableCache2;
				break;
		}

		int index = 0;
		for(int j = 0; j < cacheTable.getColumnCount(); j++){
			for(int k = 0; k < cacheTable.getRowCount()-2; k++){
				cacheTable.setValueAt(cacheData[index],k,j);
				index++;
			}

			if(cacheData[index] == -1){
				cacheTable.setValueAt("N/A", cacheTable.getRowCount()-2, j);
			}else{
				cacheTable.setValueAt(cacheData[index], cacheTable.getRowCount()-2, j);
			}
			index++;

			char state = '0';
			switch (cacheData[index]){
				case Constant.I:
					state = 'I';
					break;
				case Constant.M:
					state = 'M';
					break;
				case Constant.C:
					state = 'C';
					break;
			}
			cacheTable.setValueAt(state, cacheTable.getRowCount()-1, j);
			index++;
		}
	}

	private int[] getFlatCache(ArrayList<ArrayList<int[]>> cache, int[] states){
		int[] cacheData = new int[24];
		int index = 0;
		int info = 0;

		for(int i = 0; i < cache.size(); i++){
			for(int j = 0; j < cache.get(i).size(); j++){
				for(int k = 0; k < cache.get(i).get(j).length; k++){
					cacheData[index] = cache.get(i).get(j)[k];
					index++;
				}
			}
			cacheData[index] = states[info];
			cacheData[index+1] = states[info+1];
			index += 2;
			info+=2;
		}
		return cacheData;
	}

	public void writeDirectories(ArrayList<int[]> directory0, ArrayList<int[]> directory1){
		this.writeSpecificDirectory(this.getFlatDirectory(directory0, 0), 0);
		this.writeSpecificDirectory(this.getFlatDirectory(directory1, 1), 1);
	}

	private int[] getFlatDirectory(ArrayList<int[]> directory, int id){
		int size = 0;
		switch (id){
			case 0:
				size = 64;
				break;
			case 1:
				size = 32;
				break;
		}
		int[] directoryData = new int[size];
		int index = 0;

		for(int i = 0; i < directory.size(); i++){
			int[] directoryRow = directory.get(i);
			for(int j = 0; j < directoryRow.length; j++){
				directoryData[index] = directoryRow[j];
				index++;
			}
		}

		return directoryData;
	}

	private void writeSpecificDirectory(int[] directory, int id){
		JTable directoryTable = null;

		switch (id){
			case 0:
				directoryTable = this.tableDirectory0;
				break;
			case 1:
				directoryTable = this.tableDirectory1;
				break;
		}

		int index = 0;
		for(int i = 0; i < directoryTable.getRowCount(); i++){
			char state = '0';
			switch (directory[index]){
				case Constant.U:
					state = 'U';
					break;
				case Constant.M:
					state = 'M';
					break;
				case Constant.C:
					state = 'C';
					break;
			}
			directoryTable.setValueAt(state, i, 0);
			index++;
			for(int j = 1; j < directoryTable.getColumnCount(); j++){
				directoryTable.setValueAt(directory[index],i,j);
				index++;
			}
		}
	}
}
