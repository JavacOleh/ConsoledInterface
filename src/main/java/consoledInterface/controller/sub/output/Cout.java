package consoledInterface.controller.sub.output;

import consoledInterface.ApplicationInit;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Arrays;

public class Cout {
    public static OutputController outputController;

    public static void cout(String text, Color textColor) {
        if(outputController == null) {
            System.out.println("Init OutputController first!");
            return;
        }

        Platform.runLater(() -> {
            if (!text.isEmpty()) {
                Text temp = new Text(text);
                temp.setFill(textColor);
                outputController.getTextHistory().add(temp);
                outputController.updateStackPane();
            }
        });
    }

    public static void cout(Text text) {
        if(outputController == null) {
            System.out.println("Init OutputController first!");
            return;
        }
        Platform.runLater(() -> {
            if (!text.getText().isEmpty()) {
                outputController.getTextHistory().add(text);
                outputController.updateStackPane();
            }
        });
    }

    public static void cout(Text... texts) {
        if(outputController == null) {
            System.out.println("Init OutputController first!");
            return;
        }
        Platform.runLater(() -> {
            if(texts.length > 0) {
                Arrays.stream(texts).forEach(text -> {
                    if (!text.getText().isEmpty()) {
                        outputController.getTextHistory().add(text);
                        outputController.updateStackPane();
                    }
                });
            }
        });
    }

    public static void cout(String text) {
        cout(text, ApplicationInit.textColor);
    }

}
