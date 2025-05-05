package View.admin.components;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.util.List;
import model.admin.AdminPost;

public class PostTable extends JTable {
    private final PostTableModel model;
    private final TableRowSorter<PostTableModel> sorter;

    public PostTable(List<AdminPost> posts) {
        this.model = new PostTableModel(posts);
        this.sorter = new TableRowSorter<>(model);
        
        setModel(model);
        setRowSorter(sorter);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setAutoCreateRowSorter(true);
    }

    public void updateData(List<AdminPost> posts) {
        model.updateData(posts);
    }

    public AdminPost getSelectedPost() {
        int row = getSelectedRow();
        return row >= 0 ? model.getItemAt(convertRowIndexToModel(row)) : null;
    }

    private static class PostTableModel extends AdminTableModel<AdminPost> {
        public PostTableModel(List<AdminPost> posts) {
            super(posts, new String[]{"ID", "Content", "Author", "Date", "Reports", "Status"});
        }

        @Override
        public Object getValueAt(int row, int col) {
            AdminPost post = data.get(row);
            switch (col) {
                case 0: return post.getID();
                case 1: return post.getContent().length() > 50 ? 
                       post.getContent().substring(0, 50) + "..." : post.getContent();
                case 2: return post.getUser().getusername();
                case 3: return post.getDateToString();
                case 4: return post.getReportCount();
                case 5: return post.isFlagged() ? "Flagged" : "Clean";
                default: return null;
            }
        }
    }
}