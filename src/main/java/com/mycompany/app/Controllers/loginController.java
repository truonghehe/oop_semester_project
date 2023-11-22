package com.mycompany.app.Controllers;

import static com.mycompany.app.myApplication.personIndex;
import static com.mycompany.app.myApplication.personList;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.mycompany.app.Person;
import com.mycompany.app.myApplication;
import com.mycompany.app.Alert.Alerts;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The loginController class manages user authentication and registration within the application.
 */
public class loginController implements Initializable {

    @FXML
    private AnchorPane thisPane;
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

    private final Alerts alerts = new Alerts();
    private final String[] questionList = {"What is your favourite food?", "Where are you from?", "What subject do you like?"};

    /**
     * Clears the registration form fields.
     */
    public void registerClear() {
        signUp_username.clear();
        signUp_password.clear();
        signUp_CPassword.clear();
        signUp_selectQuestion.getSelectionModel().clearSelection();
        signUp_answer.clear();
    }

    /**
     * Clears the login form fields.
     */
    public void loginClear() {
        login_username.clear();
        login_password.clear();
    }

    /**
     * Clears the forgot form fields.
     */
    public void forgotClear() {
        forgot_username.clear();
        forgot_selectQuestion.getSelectionModel().clearSelection();
        forgot_answer.clear();
    }

    /**
     * Handles user registration.
     */
    public void register() {
        if (signUp_username.getText().isEmpty() || signUp_password.getText().isEmpty() || signUp_CPassword.getText().isEmpty()
                || signUp_selectQuestion.getSelectionModel().getSelectedItem() == null ||
                signUp_answer.getText().isEmpty()) {
            alerts.showAlertWarning("Error message", "All fields are necessary to be filled");
        } else if (!signUp_password.getText().equals(signUp_CPassword.getText())) {
            alerts.showAlertWarning("Error message", "Passwords do not match");
        } else if (signUp_password.getText().length() < 8) {
            alerts.showAlertWarning("Error message", "Password must be at least 8 characters");
        } else {
            String checkUser = signUp_username.getText();
            int index = checkUserExists(checkUser);
            if (index >= 0) {
                alerts.showAlertWarning("Error message", "User already exists");
            } else {
                List<String> tmp = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    tmp.add("0 0");
                }
                personList.add(new Person(signUp_username.getText(), signUp_password.getText(),
                        signUp_selectQuestion.getSelectionModel().getSelectedItem(), signUp_answer.getText(), "0 0", "0 0", tmp));
                alerts.showAlertInfo("Information message", "Registration successful");
                registerClear();
                signUp_form.setVisible(false);
                login_form.setVisible(true);
            }
        }
    }

    /**
     * Switches between login, registration, and password recovery forms.
     */
    public void switchForm(ActionEvent event) {
        if (event.getSource() == signUp_login || event.getSource() == forgot_back) {
            switchForms(false, true, false, false);
            loginClear();
        } else if (event.getSource() == login_signUp) {
            switchForms(true, false, false, false);
            registerClear();
        } else if (event.getSource() == login_forgotPassword || event.getSource() == pass_back) {
            switchForms(false, false, true, false);
            forgotClear();
        }
    }

    /**
     * Handles the password recovery process.
     */
    public void forgotProceed() {
        if (forgotFieldsEmpty()) {
            alerts.showAlertWarning("Error Message", "All fields are necessary to be filled");
        } else {
            int index = checkUserExists(forgot_username.getText());
            if (index < 0) {
                alerts.showAlertWarning("Error Message", "Incorrect userName");
            } else {
                if (forgotQuestionAndAnswerCorrect(index)) {
                    switchForms(false, false, false, true);
                } else {
                    alerts.showAlertWarning("Error Message", "Incorrect question or answer");
                }
            }
        }
    }

    /**
     * Handles the password recovery process.
     */
    public void passProceed() {
        if (passwordFieldsEmpty()) {
            alerts.showAlertWarning("Error Message", "All fields are necessary to be filled");
        } else {
            if (passPasswordLengthInvalid()) {
                alerts.showAlertWarning("Error Message", "Password must be at least 8 characters");
            } else {
                if (!passPasswordsMatch()) {
                    alerts.showAlertWarning("Error Message", "Passwords do not match");
                } else {
                    int index = checkUserExists(forgot_username.getText());
                    personList.get(index).setPassword(pass_password.getText());
                    switchForms(false, true, false, false);
                }
            }
        }
    }

    /**
     * Handles the user login process.
     */
    public void login() throws IOException {
        if (loginFieldsEmpty()) {
            alerts.showAlertWarning("Error Message", "All fields are necessary to be filled");
        } else {
            String checkUser = login_username.getText();
            String checkPass = login_password.getText();
            int index = checkUserExists(checkUser);
            if (index >= 0) {
                if (checkPass.equals(personList.get(index).getPassword())) {
                    personIndex = index;
                    Stage stage = (Stage) thisPane.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(myApplication.class.getResource("/Views/startingView.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setTitle("Welcome to our application!");
                    stage.setWidth(800);
                    stage.setHeight(500);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    alerts.showAlertWarning("Error Message", "Incorrect password");
                }
            } else {
                alerts.showAlertWarning("Error Message", "User not exist");
            }
        }
    }

    /**
     * Toggles the visibility of the password in the login form.
     */
    public void showPassword() {
        if (login_showPassword.isSelected()) {
            login_showpass.setText(login_password.getText());
            togglePasswordVisibility(true);
        } else {
            login_showpass.setText(login_showpass.getText());
            togglePasswordVisibility(false);
        }
    }

    /**
     * Initializes the question dropdown lists.
     */
    public void questions() {
        ObservableList<String> observableList = FXCollections.observableArrayList(questionList);
        signUp_selectQuestion.setItems(observableList);
        forgot_selectQuestion.setItems(observableList);
    }

    /**
     * Initializes the controller.
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questions();
        login_password.textProperty().addListener((observable, oldValue, newValue) -> {
            login_showpass.setText(newValue);
        });
        login_showpass.textProperty().addListener((observable, oldValue, newValue) -> {
            login_password.setText(newValue);
        });
    }

    /**
     * Handles the "Enter" key press in the login form.
     *
     * @param event The key event.
     * @throws IOException If an error occurs while handling the login process.
     */
    @FXML
    private void login_enter(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            login();
        }
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
