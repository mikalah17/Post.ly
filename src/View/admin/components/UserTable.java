package View.admin.components;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import model.admin.AdminUser;

public class UserTable extends JTable {
    private final UserTableModel model;
    
    public UserTable(List<AdminUser> users) {
        this.model = new UserTableModel(users);
        setModel(model);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    public void updateData(List<AdminUser> users) {
        model.setUsers(users);
    }
    
    public int getSelectedUserId() {
        int row = getSelectedRow();
        return row >= 0 ? model.getUserIdAt(row) : -1;
    }
    
    private static class UserTableModel extends AbstractTableModel {
        private final String[] columnNames = {"ID", "Username", "Posts", "Status"};
        private List<AdminUser> users;
        
        public UserTableModel(List<AdminUser> users) {
            this.users = users;
        }
        
        public void setUsers(List<AdminUser> users) {
            this.users = users;
            fireTableDataChanged();
        }
        
        public int getUserIdAt(int row) {
            return users.get(row).getID();
        }
        
        @Override public int getRowCount() { return users.size(); }
        @Override public int getColumnCount() { return columnNames.length; }
        @Override public String getColumnName(int col) { return columnNames[col]; }
        
        @Override
        public Object getValueAt(int row, int col) {
            AdminUser user = users.get(row);
            switch (col) {
                case 0: return user.getID();
                case 1: return user.getusername();
                case 2: return user.getPostCount();
                case 3: return user.isBanned() ? "Banned" : "Active";
                default: return null;
            }
        }
    }
}