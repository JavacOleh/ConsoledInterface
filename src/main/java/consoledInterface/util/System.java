package consoledInterface.util;

import consoledInterface.ApplicationInit;
import consoledInterface.controller.sub.input.InputController;
import consoledInterface.controller.sub.output.OutputController;
import javafx.application.Platform;

import static consoledInterface.controller.sub.output.Cout.cout;

public class System {
    public static OutputController outputController;
    public static InputController inputController;

    public static void system(String args) {
        if(outputController == null) {
            java.lang.System.out.println("Init consoleController please!");
            return;
        }

        switch (args) {
            case "cls" -> Platform.runLater(() -> outputController.clearConsole());
            case "pause" -> pause();
        }
    }

    public static void pause() {
        if(inputController == null) {
            java.lang.System.out.println("Init inputController please!");
            return;
        }

        cout("Press any key to continue...", ApplicationInit.textColor);
        inputController.getCin().getLine();
        cout("\n");
    }
}
