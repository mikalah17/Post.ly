package View.admin.components;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public abstract class AdminTableModel<T> extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
    protected List<T> data;
    protected final String[] columnNames;

    public AdminTableModel(List<T> data, String[] columnNames) {
        this.data = data;
        this.columnNames = columnNames;
    }

    public void updateData(List<T> newData) {
        this.data = newData;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public T getItemAt(int rowIndex) {
        return data.get(rowIndex);
    }
}