import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Transactions {
    public TableView transactionTable;
    public javafx.scene.control.TableColumn<Book, String> idColumn;
    public javafx.scene.control.TableColumn<Book, String> bookColumn;
    public javafx.scene.control.TableColumn<Book, String> userColumn;
    public javafx.scene.control.TableColumn<Book, String> descriptionColumn;

    public void initialize(){
        transactionTable.setItems(Database.getTransactionMany());

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookColumn.setCellValueFactory(new PropertyValueFactory<>("book"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }
}
