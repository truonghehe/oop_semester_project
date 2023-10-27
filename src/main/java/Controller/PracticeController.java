package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.ResourceBundle;

public class PracticeController implements Initializable {
    @FXML
    private ListView<String> listView;
    @FXML
    private WebView webView;
    @FXML
    private TextField findWord;
    @FXML
    private TextField addWord;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
