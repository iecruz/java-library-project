import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;

public class UserManager {
    public TableView<User> userTable;
    public javafx.scene.control.TableColumn<User, String> idColumn;
    public javafx.scene.control.TableColumn<User, String> firstNameColumn;
    public javafx.scene.control.TableColumn<User, String> lastNameColumn;
    public javafx.scene.control.TableColumn<User, String> usernameColumn;

    public void initialize() {
        userTable.setItems(Database.getUserMany());

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    }

    public void onAdd() {
        try {
            FXMLLoader view = new FXMLLoader(getClass().getResource("user_add.fxml"));

            Main.setRoot(view.load());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void onUpdate() {
        if(userTable.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No record selected");
            return;
        }

        try {
            FXMLLoader view = new FXMLLoader(getClass().getResource("user_update.fxml"));

            Main.setRoot(view.load());

            ((UserUpdate) view.getController()).initialize(Database.getUser(userTable.getSelectionModel().getSelectedItem().getId()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void onDelete() {
        if(userTable.getSelectionModel().getSelectedItem().getId() == 1) {
            JOptionPane.showMessageDialog(null, "Admin account cannot be deleted");
            return;
        }

        if(userTable.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No record selected");
            return;
        }

        if(JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Delete Record", JOptionPane.WARNING_MESSAGE) == 2) {
            return;
        }

        String query = String.format("delete users where id = %d",
                userTable.getSelectionModel().getSelectedItem().getId());
        Database.execute(query);

        ObservableList<User> allItems = userTable.getItems();
        User selectedItems = userTable.getSelectionModel().getSelectedItem();
        allItems.removeAll(selectedItems);
    }
}
