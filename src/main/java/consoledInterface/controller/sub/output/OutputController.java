package consoledInterface.controller.sub.output;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import lombok.Getter;
import consoledInterface.util.System;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OutputController {
    @Setter
    private ScrollPane consoleScrollPane;
    @Setter
    private StackPane consoleStackPane;
    @Setter
    private TextFlow textFlow;
    private final List<Text> textHistory;

    public OutputController() {
        System.outputController = this;
        Cout.outputController = this;
        textHistory = new ArrayList<>();
    }

    public void updateStackPane() {
        textFlow.getChildren().clear();
        textFlow.getChildren().addAll(textHistory);
    }

    public void clearConsole() {
        textHistory.clear();
        textFlow.getChildren().clear();
    }
}
