package consoledInterface;

import consoledInterface.controller.IOcontroller;
import consoledInterface.util.ConcurrentUtil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ApplicationInit extends Application {
    public static final Color textColor = Color.BLACK;
    public static final String mainView = "console-view.fxml";
    public static final String consoleTitle = "ConsoleApp";
    private static final IOcontroller ioController = new IOcontroller();
    private static Stage theStage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationInit.class.getResource(mainView));

        fxmlLoader.setController(ioController);

        theStage = stage;
        Scene scene = new Scene(fxmlLoader.load(), 640, 390);
        stage.setResizable(false);
        stage.setTitle(consoleTitle);
        stage.setScene(scene);

        var icon = new Image(Objects.requireNonNull(ApplicationInit.class.getResourceAsStream("windowIcon.png")));

        stage.getIcons().add(icon);

        stage.setOnCloseRequest((event) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit Confirmation");
            alert.setHeaderText("Are you sure you want to exit?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {

                    onClose();
                } else {
                    event.consume();
                }
            });
        });

        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

    private static void interruptAllThreads() {
        registerThreads().forEach(s -> {
            if(s.isAlive())
                s.interrupt();
        });
    }

    public static void onClose() {
        if(theStage == null) {
            System.out.println("theStage is null!!");
            return;
        }

        interruptAllExecutorServices();

        interruptAllThreads();

        theStage.close();

        Platform.exit();
        System.exit(130);
    }

    private static void interruptAllExecutorServices() {
        ArrayList<ExecutorService> executorServices = registerExecutorServices();
        executorServices.forEach(s -> {
            if(!s.isTerminated()) {
                s.shutdownNow();
                try {
                    // Ожидаем завершения всех задач
                    if (!s.awaitTermination(30, TimeUnit.MILLISECONDS)) {
                        s.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    s.shutdownNow();
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    public static IOcontroller getIO() {
        return ioController;
    }

    public static ArrayList<ExecutorService> registerExecutorServices() {
        var executorServices = new ArrayList<ExecutorService>();
        executorServices.add(ioController.getInputController().getCin().getExecutorService());
        //executorServices.add(ioController.getTaskExectuor().getExecutorService());

        return executorServices;
    }

    public static ArrayList<Thread> registerThreads() {
        var threads = new ArrayList<Thread>();

        var temp = ConcurrentUtil.thread;
        if(temp != null)
            threads.add(temp);

        /*
        var temp = ioController.getTaskExectuor().parseUtil.thread;
        if(temp != null)
            threads.add(temp);
        */

        return threads;
    }
}