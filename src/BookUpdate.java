import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class BookUpdate {
    public javafx.scene.control.TextField titleField;
    public javafx.scene.control.TextField authorField;
    public javafx.scene.control.TextArea descriptionField;

    Book book;

    public void initialize(Book book) {
        this.book = book;

        titleField.setText(book.title);
        authorField.setText(book.author);
        descriptionField.setText(book.description);
    }

    public void onUpdate() {
        Database.execute(String.format("update books set title = '%s', author = '%s', description = '%s' where id = %d",
                titleField.getText(),
                authorField.getText(),
                descriptionField.getText(),
                book.id
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
