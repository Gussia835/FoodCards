package ru.mealcard.service;

import lombok.Getter;
import ru.mealcard.Base;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ShedulerService extends Base {

    private final ScheduledExecutorService sheduler = Executors.newScheduledThreadPool(1);
    @Getter
    private static final ShedulerService instance = new ShedulerService();

    private ShedulerService() {
    }

    public void shedule(LocalDateTime sheduledTime, Runnable task) {
        long diff = Duration.between(LocalDateTime.now(ZoneId.of(getConfig().getZone())), sheduledTime).toMillis();

        if (diff > 0) {
            sheduler.schedule(task, diff, TimeUnit.MILLISECONDS);
        } else {
            task.run();
        }
    }



}
