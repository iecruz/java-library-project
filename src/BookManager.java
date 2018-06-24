import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;

public class BookManager {
    public TableView<Book> bookTable;
    public javafx.scene.control.TableColumn<Book, String> idColumn;
    public javafx.scene.control.TableColumn<Book, String> titleColumn;
    public javafx.scene.control.TableColumn<Book, String> authorColumn;
    public javafx.scene.control.TableColumn<Book, String> statusColumn;

    User user;

    public void initialize() {
        this.user = Main.user;
        bookTable.setItems(Database.getBookMany());

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void onAdd() {
        try {
            FXMLLoader view = new FXMLLoader(getClass().getResource("book_add.fxml"));

            Main.setRoot(view.load());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void onUpdate() {
        if(bookTable.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No record selected", "Record Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            FXMLLoader view = new FXMLLoader(getClass().getResource("book_update.fxml"));

            Main.setRoot(view.load());

            ((BookUpdate) view.getController()).initialize(Database.getBook(bookTable.getSelectionModel().getSelectedItem().getId()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void onReturn() {
        if(bookTable.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No record selected", "Record Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

//        if(JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Delete Record", JOptionPane.WARNING_MESSAGE) == 2) {
//            return;
//        }

        String query = String.format("update books set status = 'On shelf' where id = %d",
                bookTable.getSelectionModel().getSelectedItem().getId());
        Database.execute(query);


        query = String.format("insert into transactions(id, user_id, book_id, description) values(%d, %d, %d, 'Returned')",
                Database.count("transactions") + 1,
                user.id,
                bookTable.getSelectionModel().getSelectedItem().getId()
        );
        Database.execute(query);

        bookTable.setItems(Database.getBookMany());
    }

    public void onDelete() {
        if(bookTable.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No record selected", "Record Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Delete Record", JOptionPane.WARNING_MESSAGE) == 2) {
            return;
        }

        String query = String.format("delete books where id = %d",
                bookTable.getSelectionModel().getSelectedItem().getId());
        Database.execute(query);

        ObservableList<Book> allItems = bookTable.getItems();
        Book selectedItems = bookTable.getSelectionModel().getSelectedItem();
        allItems.removeAll(selectedItems);
    }
}
