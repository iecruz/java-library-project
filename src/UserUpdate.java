import javafx.fxml.FXMLLoader;

import javax.swing.*;

public class UserUpdate {
    public javafx.scene.control.TextField firstNameField;
    public javafx.scene.control.TextField lastNameField;
    public javafx.scene.control.TextField usernameField;
    public javafx.scene.control.PasswordField passwordField;
    public javafx.scene.control.PasswordField confirmPasswordField;

    User user;

    public void initialize(User user) {
        this.user = user;
        firstNameField.setText(user.firstName);
        lastNameField.setText(user.lastName);
        usernameField.setText(user.username);
        passwordField.setText(user.password);
        confirmPasswordField.setText(user.password);
    }

    public void onUpdate() {
        if(!passwordField.getText().equals(confirmPasswordField.getText())) {
            JOptionPane.showMessageDialog(null, "Password did not match");
            return;
        }

        Database.execute(String.format("update users set first_name = '%s', last_name = '%s', username = '%s', password = '%s' where id = %d",
                firstNameField.getText(),
                lastNameField.getText(),
                usernameField.getText(),
                passwordField.getText(),
                user.id
        ));

        onCancel();
    }

    public void onCancel() {
        try {
            FXMLLoader bookAdd = new FXMLLoader(getClass().getResource("main_menu.fxml"));

            Main.setRoot(bookAdd.load());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
