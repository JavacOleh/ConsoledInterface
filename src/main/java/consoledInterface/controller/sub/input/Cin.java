package consoledInterface.controller.sub.input;

import consoledInterface.controller.sub.output.OutputController;
import consoledInterface.util.ConcurrentUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class Cin {
    private final OutputController outputController;
    private ExecutorService executorService;
    private boolean isCinActive = false;
    private String text;

    public Cin(OutputController outputController) {
        this.outputController = outputController;
        this.executorService = Executors.newSingleThreadExecutor();
        text = "";
    }

    public String getLine() {
        //Если пул потоков завершён, пересоздаём новый пул
        if (executorService.isShutdown() || executorService.isTerminated()) {
            executorService = Executors.newSingleThreadExecutor();
        }

        //Создаём CountDownLatch для синхронизации
        CountDownLatch latch = new CountDownLatch(1);
        isCinActive = true;

        // В потоке, отвечающем за ввод, ожидаем пока пользователь не введет текст
        executorService.submit(() -> {
            // Ожидаем, пока пользователь введет текст и нажмёт кнопку Enter или клавишу Enter
            do {
                try {
                    Thread.sleep(100);
                    if(!isCinActive) {
                        latch.countDown();
                        break;
                    }

                }catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }while (isCinActive);

        });

        // Ожидаем, пока пользователь не завершит ввод
        ConcurrentUtil.await(latch);

        shutdown();
        return text;
    }

    public void shutdown() {
        executorService.shutdownNow();
        try {
            // Ожидаем завершения всех задач
            if (!executorService.awaitTermination(30, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

}
