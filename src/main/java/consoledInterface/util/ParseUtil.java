package consoledInterface.util;

import consoledInterface.ApplicationInit;
import consoledInterface.controller.sub.input.Cin;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static consoledInterface.controller.sub.output.Cout.cout;

public class ParseUtil {
    public Cin cin;
    public Thread thread;

    public ParseUtil(Cin cin) {
        this.cin = cin;
    }

    public int getParsedInt() {
        AtomicBoolean shouldKeepCycle = new AtomicBoolean(true);
        AtomicInteger atomicInteger = new AtomicInteger(-1);
        CountDownLatch latch = new CountDownLatch(1);
        thread = new Thread(() -> {
            do {
                try {
                    int temp = Integer.parseInt(cin.getLine());

                    ConcurrentUtil.awaitTillCinIsActive(latch, cin);

                    atomicInteger.set(temp);
                    shouldKeepCycle.set(false);
                    latch.countDown();

                } catch (NumberFormatException e) {
                    cout("Please enter a valid digit!", ApplicationInit.textColor); // Сообщение об ошибке
                }
            } while (shouldKeepCycle.get());
        });
        thread.start();

        ConcurrentUtil.await(latch);

        return atomicInteger.get();
    }

    public int getParsedInt(int maxValue, int minValue) {
        AtomicBoolean shouldKeepCycle = new AtomicBoolean(true);
        AtomicInteger atomicInteger = new AtomicInteger(-1);
        CountDownLatch latch = new CountDownLatch(1);
        thread = new Thread(() -> {
            do {
                try {
                    int temp = Integer.parseInt(cin.getLine());

                    ConcurrentUtil.awaitTillCinIsActive(latch, cin);

                    if (temp > maxValue || temp < minValue) {
                        cout("Please enter a digit lower than " + maxValue + " and bigger than " + minValue + "!", ApplicationInit.textColor);
                    } else {
                        atomicInteger.set(temp);
                        shouldKeepCycle.set(false);
                        latch.countDown();
                    }
                } catch (NumberFormatException e) {

                    cout("Please enter a valid digit!", ApplicationInit.textColor); // Сообщение об ошибке
                }
            } while (shouldKeepCycle.get());
        });

        thread.start();

        ConcurrentUtil.await(latch);

        return atomicInteger.get();
    }

    public int getParsedIntByStringValue(String value) {
        AtomicReference<String> parsedValue = new AtomicReference<>(value);
        int temp;

        do {
            CountDownLatch latch = new CountDownLatch(1);

            parsedValue.set(cin.getLine());

            ConcurrentUtil.awaitTillCinIsActiveInOtherThread(latch, cin);

            try {
                // Ждем завершения ввода
                ConcurrentUtil.await(latch);

                temp = Integer.parseInt(parsedValue.get());

                return temp; // Возвращаем число, если все в порядке
            } catch (NumberFormatException e) {
                cout("Please enter a valid digit!", ApplicationInit.textColor); // Сообщение об ошибке
            }

        } while (true);
    }

    public double getParsedDouble() {
        AtomicBoolean shouldKeepCycle = new AtomicBoolean(true);
        AtomicReference<Double> atomicDouble = new AtomicReference<>(-1.0);
        CountDownLatch latch = new CountDownLatch(1);

        thread = new Thread(() -> {
            do {
                try {
                    double temp = Double.parseDouble(cin.getLine());
                    atomicDouble.set(temp);
                    shouldKeepCycle.set(false);
                    latch.countDown();

                } catch (NumberFormatException e) {

                    cout("Please enter a valid digit!", ApplicationInit.textColor); // Сообщение об ошибке
                }
            } while (shouldKeepCycle.get());
        });

        thread.start();

        ConcurrentUtil.await(latch);

        return atomicDouble.get();
    }
}
