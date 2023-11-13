package com.mycompany.app.Controllers;

import com.mycompany.app.Alert.Alerts;
import com.mycompany.app.Person;
import com.mycompany.app.myApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.mycompany.app.myApplication.*;

public class loginController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private PasswordField forgot_answer;

    @FXML
    private Button forgot_back;

    @FXML
    private AnchorPane forgot_form;

    @FXML
    private Button forgot_proceed;

    @FXML
    private ComboBox<String> forgot_selectQuestion;

    @FXML
    private TextField forgot_username;

    @FXML
    private Hyperlink login_forgotPassword;

    @FXML
    private AnchorPane login_form;

    @FXML
    private Button login_login;

    @FXML
    private PasswordField login_password;

    @FXML
    private CheckBox login_showPassword;

    @FXML
    private Button login_signUp;

    @FXML
    private TextField login_username;

    @FXML
    private PasswordField pass_CPassword;

    @FXML
    private Button pass_back;

    @FXML
    private AnchorPane pass_form;

    @FXML
    private PasswordField pass_password;

    @FXML
    private Button pass_proceed;

    @FXML
    private PasswordField signUp_CPassword;

    @FXML
    private PasswordField signUp_answer;

    @FXML
    private AnchorPane signUp_form;

    @FXML
    private Button signUp_login;

    @FXML
    private PasswordField signUp_password;

    @FXML
    private ComboBox<String> signUp_selectQuestion;

    @FXML
    private TextField signUp_username;

    @FXML
    private TextField login_showpass;

    @FXML
    private Button signup_bt;
    private Alerts alerts = new Alerts();
    private final String[] questionList = {"What is your favourite food?", "Where are you from?", "What subject do you like?"};

    public void registerClear() {
        signUp_username.clear();
        signUp_password.clear();
        signUp_CPassword.clear();
        signUp_selectQuestion.getSelectionModel().clearSelection();
        signUp_answer.clear();
    }

    public void register() {
        if (signUp_username.getText().isEmpty() || signUp_password.getText().isEmpty() || signUp_CPassword.getText().isEmpty()
                || signUp_selectQuestion.getSelectionModel().getSelectedItem() == null ||
                signUp_answer.getText().isEmpty()) {
            alerts.showAlertWarning("Error message" , "All fields are necessary to be filled");
        } else if (!signUp_password.getText().equals(signUp_CPassword.getText())) {
            alerts.showAlertWarning("Error message" , "Passwords do not match");
        } else if (signUp_password.getText().length() < 8) {
            alerts.showAlertWarning("Error message" , "Password must be at least 8 characters");
        } else {
            String checkUser = signUp_username.getText();
            int index = checkUserExists(checkUser);
            if (index >= 0) {
                alerts.showAlertWarning("Error message","User already exists");
            } else {
                List<String> tmp = new ArrayList<>() ;
                for ( int i = 0 ; i < 10 ; i++){
                    tmp.add("0 0");
                }
                personList.add(new Person(signUp_username.getText(), signUp_password.getText(),
                        signUp_selectQuestion.getSelectionModel().getSelectedItem(), signUp_answer.getText() ,"0 0" , "0 0",tmp));
                alerts.showAlertInfo("Information message","Registration successful");
                registerClear();
                signUp_form.setVisible(false);
                login_form.setVisible(true);
            }
        }
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == signUp_login || event.getSource() == forgot_back) {
            switchForms(false, true, false, false);
        } else if (event.getSource() == login_signUp) {
            switchForms(true, false, false, false);
        } else if (event.getSource() == login_forgotPassword || event.getSource() == pass_back) {
            switchForms(false, false, true, false);
        }
    }

    public void forgotProceed() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        if (forgotFieldsEmpty()) {
            alert.setContentText("All fields are necessary to be filled");
            alert.showAndWait();
        } else {
            int index = checkUserExists(forgot_username.getText());
            if (index < 0) {
                alert.setContentText("Incorrect userName");
                alert.showAndWait();
            } else {
                if (forgotQuestionAndAnswerCorrect(index)) {
                    switchForms(false, false, false, true);
                } else {
                    alert.setContentText("Incorrect question or answer");
                    alert.showAndWait();
                }
            }
        }
    }

    public void passProceed() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        if (passwordFieldsEmpty()) {
            alert.setContentText("All fields are necessary to be filled");
            alert.showAndWait();
        } else {
            if (passPasswordLengthInvalid()) {
                alert.setContentText("Password must be at least 8 characters");
                alert.showAndWait();
            } else {
                if (!passPasswordsMatch()) {
                    alert.setContentText("Passwords do not match");
                    alert.showAndWait();
                } else {
                    int index = checkUserExists(forgot_username.getText());
                    personList.get(index).setPassword(pass_password.getText());
                    switchForms(false, true, false, false);
                }
            }
        }
    }

    public void login() throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        if (loginFieldsEmpty()) {
            alert.setContentText("All fields are necessary to be filled");
            alert.showAndWait();
        } else {
            String checkUser = login_username.getText();
            String checkPass = login_password.getText();
            int index = checkUserExists(checkUser);
            if (index >= 0) {
                if (checkPass.equals(personList.get(index).getPassword())) {
                    personIndex = index;
                    Stage stage = (Stage) borderPane.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(myApplication.class.getResource("/Views/startingView.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setTitle("Dictionary");
                    stage.setScene(scene);
                    stage.show();
                } else {
                    alert.setContentText("Incorrect password");
                    alert.showAndWait();
                }
            } else {
                alert.setContentText("User not exist");
                alert.showAndWait();
            }
        }
    }

    public void showPassword() {
        if (login_showPassword.isSelected()) {
            login_showpass.setText(login_password.getText());
            togglePasswordVisibility(true);
        } else {
            login_showpass.setText(login_showpass.getText());
            togglePasswordVisibility(false);
        }
    }

    public void questions() {
        ObservableList<String> observableList = FXCollections.observableArrayList(questionList);
        signUp_selectQuestion.setItems(observableList);
        forgot_selectQuestion.setItems(observableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questions();
    }
    private int checkUserExists(String userName) {
        for (int i = 0; i < personList.size(); i++) {
            if (userName.equals(personList.get(i).getUsername())) {
                return i;
            }
        }
        return -1;
    }

    private void switchForms(boolean signUpForm, boolean loginForm, boolean forgotForm, boolean passForm) {
        signUp_form.setVisible(signUpForm);
        login_form.setVisible(loginForm);
        forgot_form.setVisible(forgotForm);
        pass_form.setVisible(passForm);
    }

    private boolean forgotFieldsEmpty() {
        return forgot_username.getText().isEmpty() || forgot_selectQuestion.getSelectionModel().getSelectedItem() == null ||
                forgot_answer.getText().isEmpty();
    }

    private boolean forgotQuestionAndAnswerCorrect(int index) {
        return forgot_selectQuestion.getSelectionModel().getSelectedItem().equals(personList.get(index).getQuestion())
                && forgot_answer.getText().equals(personList.get(index).getAnswer());
    }

    private boolean passwordFieldsEmpty() {
        return pass_password.getText().isEmpty() || pass_CPassword.getText().isEmpty();
    }

    private boolean passPasswordLengthInvalid() {
        return pass_password.getText().length() < 8;
    }

    private boolean passPasswordsMatch() {
        return pass_password.getText().equals(pass_CPassword.getText());
    }

    private boolean loginFieldsEmpty() {
        return login_username.getText().isEmpty() || login_password.getText().isEmpty();
    }

    private void togglePasswordVisibility(boolean showPassword) {
        login_password.setVisible(!showPassword);
        login_showpass.setVisible(showPassword);
    }
}
