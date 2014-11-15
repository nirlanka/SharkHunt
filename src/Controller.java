import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    public
    @FXML    TableView<Packet> tbl;
    @FXML    TableColumn<Packet,String>
                col_source, col_destination, col_size, col_type;

    @Override
    @FXML    public void initialize(URL location, ResourceBundle resources) {

        col_source.setCellValueFactory(
                new PropertyValueFactory<Packet, String>("source")
        );
        col_destination.setCellValueFactory(
                new PropertyValueFactory<Packet, String>("destination")
        );
        col_size.setCellValueFactory(
                new PropertyValueFactory<Packet, String>("size")
        );
        col_type.setCellValueFactory(
                new PropertyValueFactory<Packet, String>("type")
        );

        tbl.setItems(Main.packets);

        Main.referTable(tbl);

        tbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                System.out.println("clicked!!");
            }
        });
    }
}
