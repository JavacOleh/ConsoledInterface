package consoledInterface.controller;

import consoledInterface.controller.sub.input.Cin;
import consoledInterface.controller.sub.input.InputController;
import consoledInterface.controller.sub.output.OutputController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextFlow;
import lombok.Getter;
import lombok.Setter;

import static consoledInterface.util.System.system;

@Getter
@Setter
public class IOcontroller {
    @FXML
    private ScrollPane consoleScrollPane;
    @FXML
    private StackPane consoleStackPane;
    @FXML
    private Button enterButton;
    @FXML
    private Button clearButton;
    @FXML
    private TextField enterTextField;
    @FXML
    private TextFlow textArrayInside;

    private InputController inputController;
    private OutputController outputController;
    private Runnable enterAction;
    private Runnable clearAction;
    //private TaskExectuor taskExectuor;
    private boolean hasUserSentText;

    @FXML
    public void initialize() {
        outputController = new OutputController();
        outputController.setConsoleScrollPane(consoleScrollPane);
        outputController.setConsoleStackPane(consoleStackPane);
        outputController.setTextFlow(textArrayInside);

        inputController = new InputController(outputController);
        inputController.setEnterButton(enterButton);
        inputController.setEnterTextField(enterTextField);

        enterAction = inputController::inputAction;
        clearAction = () -> system("cls");

        //executeTasks();
    }

    @FXML
    public void onEnterButtonClick() {
        Cin cin = inputController.getCin();
        if(cin != null) {
            if(cin.isCinActive()) {
                cin.setText(enterTextField.getText());
                cin.setCinActive(false);
            }
        }
        hasUserSentText = true;
        enterAction.run();
    }
    @FXML
    public void onClearButtonClick() {
        clearAction.run();
    }

    @FXML
    public void onEnterKeyPress() {
        enterTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                Cin cin = inputController.getCin();
                if(cin != null) {
                    if(cin.isCinActive()) {
                        cin.setText(enterTextField.getText());
                        cin.setCinActive(false);
                    }
                }
                hasUserSentText = true;
                enterAction.run();

            }
        });
    }

    /*
    private void executeTasks() {
        taskExectuor = new TaskExectuor();
        taskExectuor.main();
    }
    */
}
