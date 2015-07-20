package table;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import objects.Horse;
import objects.Rider;



public class TableSystem extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] columns;
	private Object[][] values;
	private int collSize = 0;
	
	
	public TableSystem(List<List> lists, String[] collName){
		columns = collName;
		
		collSize = lists.size();
		
		values = new Object[lists.get(0).size()][lists.size()];
		for(int i = 0; i < lists.get(0).size(); i++){
			for(int y = 0; y < lists.size(); y++ ){
				values[i][y] = lists.get(y).get(i);
			}
		}
	}
	
		
	public Object[][] getValues(){
		return values;
	}
	
	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return values.length;
	}

	@Override
	public Object getValueAt(int r, int c) {
		return values[r][c];
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == collSize) return Double.class;
		return super.getColumnClass(columnIndex);
	}

	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		values[rowIndex][columnIndex] = aValue;
		fireTableCellUpdated(rowIndex, columnIndex);
	}

	public void addRow(String name, String lastName) {
		values = Arrays.copyOf(values, values.length + 1);
		values[values.length - 1] = new Object[] { name, lastName};
		fireTableDataChanged();
	}
	
	public void removeRow(int index){
		Object[][] val = new Object[values.length - 1][2];
		boolean sw = false;
		for(int i = 0; i < values.length; i++ ){
			if(i != index){
				if(sw)
					val[i-1] = values[i];
				else
					val[i] = values[i];
			}
			else
				sw = true;
		}
		values = val;
		fireTableDataChanged();
	}
}

