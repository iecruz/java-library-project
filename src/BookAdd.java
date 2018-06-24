import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.awt.*;

public class BookAdd {
    public javafx.scene.control.TextField titleField;
    public javafx.scene.control.TextField authorField;
    public javafx.scene.control.TextArea descriptionField;

    public void onAdd() {
        Database.execute(String.format("insert into books(id, title, author, description) values(%d, '%s', '%s', '%s')",
                Database.count("books") + 1,
                titleField.getText(),
                authorField.getText(),
                descriptionField.getText()
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
