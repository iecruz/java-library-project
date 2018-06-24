import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;

public class BookBorrow {
    public TableView<Book> bookTable;
    public javafx.scene.control.TableColumn<Book, String> idColumn;
    public javafx.scene.control.TableColumn<Book, String> titleColumn;
    public javafx.scene.control.TableColumn<Book, String> authorColumn;

    User user;

    public void initialize() {
        this.user = Main.user;
        bookTable.setItems(Database.getBookOnShelf());

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
    }

    public void onBorrow() {
        if(bookTable.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No record selected", "Record Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

//        if(JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Delete Record", JOptionPane.WARNING_MESSAGE) == 2) {
//            return;
//        }

        String query = String.format("update books set status = 'Borrowed' where id = %d",
                bookTable.getSelectionModel().getSelectedItem().getId());
        Database.execute(query);

        query = String.format("insert into transactions(id, user_id, book_id, description) values(%d, %d, %d, 'Borrowed')",
                Database.count("transactions") + 1,
                user.id,
                bookTable.getSelectionModel().getSelectedItem().getId()
        );
        Database.execute(query);

        ObservableList<Book> allItems = bookTable.getItems();
        Book selectedItems = bookTable.getSelectionModel().getSelectedItem();
        allItems.removeAll(selectedItems);
    }

    public void onLogout() {
        try {
            FXMLLoader view = new FXMLLoader(getClass().getResource("login.fxml"));

            Main.setRoot(view.load());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void onExit() {
        Main.exit();
    }
}
