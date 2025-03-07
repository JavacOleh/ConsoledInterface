module consoledInterface {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    opens consoledInterface to javafx.fxml;
    exports consoledInterface;
    exports consoledInterface.controller;
    opens consoledInterface.controller to javafx.fxml;
    exports consoledInterface.controller.sub.input;
    opens consoledInterface.controller.sub.input to javafx.fxml;
    exports consoledInterface.controller.sub.output;
    opens consoledInterface.controller.sub.output to javafx.fxml;
}