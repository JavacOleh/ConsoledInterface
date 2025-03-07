package consoledInterface.controller.sub.input;

import consoledInterface.controller.sub.output.OutputController;
import consoledInterface.util.System;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

import static consoledInterface.controller.sub.output.Cout.cout;
import static consoledInterface.util.System.system;

@Getter
@Setter
public class InputController {
    private final Cin cin;
    private Button enterButton;
    private TextField enterTextField;
    private ScrollPane consoleScrollPane;
    private StackPane consoleStackPane;
    private final OutputController outputController;

    private boolean ignoreCommand;
    private final Color inputColor;

    public InputController(OutputController outputController) {
        this.outputController = outputController;
        cin = new Cin(outputController);
        inputColor = Color.BLACK;
        ignoreCommand = true;
        System.inputController = this;
    }

    public void inputAction() {
        if (!enterTextField.getText().isEmpty()) {
            cout(enterTextField.getText(), inputColor);

            if (!ignoreCommand) {
                system(enterTextField.getText());
            }
        }
        enterTextField.setText("");
    }
}
