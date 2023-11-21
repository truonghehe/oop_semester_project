package com.mycompany.app.Controllers.DictionaryControllers;

import static com.mycompany.app.myApplication.textToSpeech;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.speech.AudioException;
import javax.speech.EngineException;

import com.mycompany.app.DictionaryManagement;
import com.mycompany.app.Word;
import com.mycompany.app.Alert.Alerts;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;


public class searchController implements Initializable {
    public static String selectedItem;
    @FXML
    private ListView<String> listView;

    @FXML
    private ObservableList<String> observableList = FXCollections.observableArrayList();

    @FXML
    private ObservableList<String> filteredList = FXCollections.observableArrayList();

    @FXML
    private TextField searchField;

    @FXML
    private WebView webView;

    @FXML
    private Button btDelete;
    @FXML
    private Button btVoice;

    @FXML
    private Button btChange;
    @FXML
    private Button btSearch;
    @FXML
    private Alerts alerts = new Alerts();

    @FXML
    private Tooltip tooltip1;

    @FXML
    private Tooltip tooltip2;

    @FXML
    private Tooltip tooltip3;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addToObservableList();
        observableList.sort(String::compareTo);
        listView.setItems(observableList);
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedItem = newValue;
                String word_explainExplain = DictionaryManagement.data.get(newValue).getWord_explain();
                webView.getEngine().loadContent(word_explainExplain, "text/html");
            }
        });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.clear();
            for (String item : observableList) {
                if (item.toLowerCase().contains(newValue.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            listView.setItems(filteredList);
        });
        tooltip1.setShowDelay(Duration.seconds(0.5));
        tooltip2.setShowDelay(Duration.seconds(0.5));
        tooltip3.setShowDelay(Duration.seconds(0.5));
        btChange.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    update();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        btDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteWord();
            }
        });
        btVoice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (selectedItem != null) {
                    try {
                        textToSpeech.speak(selectedItem);
                    } catch (AudioException | EngineException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        btSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchWord();
            }
        });
    }

    public void deleteWord() {
        if (selectedItem != null) {
            selectedItem = listView.getSelectionModel().getSelectedItem();
            Alert alertWarning = alerts.alertWarning("Delete", "Bạn chắc chắn muốn xoá từ này?");
            alertWarning.getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> optional = alertWarning.showAndWait();
            if (optional.get() == ButtonType.OK) {
                observableList.remove(selectedItem);
                filteredList.remove(selectedItem);
                Word selectedWord = DictionaryManagement.data.get(selectedItem);
                DictionaryManagement.dictionary.remove(selectedWord);
                alerts.showAlertInfo("Information", "Xoá thành công");
            } else alerts.showAlertInfo("Information", "Xoá thất bại");
        }
    }

    public void update() throws IOException {
        if (selectedItem != null) {
            Stage secondaryStage = new Stage();
            FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource("/Views/updateWord.fxml"));
            Scene secondaryScene = new Scene(secondaryLoader.load());
            secondaryStage.setScene(secondaryScene);
            secondaryStage.setTitle("Update " + selectedItem);
            secondaryStage.setOnCloseRequest(windowEvent -> {
                webView.getEngine().loadContent(DictionaryManagement.data.get(selectedItem).getWord_explain());
            });
            secondaryStage.show();
        }
    }

    private void addToObservableList() {
        observableList.clear();
        for (int i = 0; i < DictionaryManagement.dictionary.size(); i++) {
            observableList.add(DictionaryManagement.dictionary.get(i).getWord_target());
        }
    }

    private void searchWord() {
        String word = searchField.getText();
        int lo = 0, hi = observableList.size();
        int ans = search(lo, hi, word);
        filteredList.clear();
        if (ans == -1) {
            alerts.showAlertWarning("Warning!", "Word doesn't exist!");
        } else {
            filteredList.add(observableList.get(ans));
            String word_explainExplain = DictionaryManagement.data.get(word).getWord_explain();
            selectedItem = word;
            webView.getEngine().loadContent(word_explainExplain, "text/html");
        }
    }

    private int search(int lo, int hi, String word) {
        int mid = (lo + hi) / 2;
        while (lo <= hi) {
            int cmp = observableList.get(mid).compareTo(word);
            if (cmp > 0) return search(lo, mid - 1, word);
            else if (cmp < 0) return search(mid + 1, hi, word);
            else return mid;
        }
        return -1;
    }
}
