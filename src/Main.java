import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class Main extends Application {

    static ObservableList<Packet> packets = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception{

        // load
        Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));
        primaryStage.setTitle("SharkApp");
        Scene scene=new Scene(root);

        // code

        packets.addAll(
                new Packet("abc","def","9","http"),
                new Packet("ghi","jkl","100","udp")
        );



        // show
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static TableView<Packet> table;
    static void referTable(TableView<Packet> tbl) {
        table=tbl;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
