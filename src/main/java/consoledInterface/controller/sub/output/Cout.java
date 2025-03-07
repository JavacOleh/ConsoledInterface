package consoledInterface.controller.sub.output;

import consoledInterface.ApplicationInit;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Cout {
    public static OutputController outputController;

    public static void cout(String text, Color textColor) {
        if(outputController == null) {
            System.out.println("Init OutputController first!");
            return;
        }

        Platform.runLater(() -> {
            if (!text.isEmpty()) {
                Text temp = new Text(text + "\n");
                temp.setFill(textColor);
                outputController.getTextHistory().add(temp);
                outputController.updateStackPane();
            }
        });
    }
    public static void cout(String text) {
        cout(text, ApplicationInit.textColor);
    }
}
