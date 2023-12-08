package com.example.demo.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Component
public class ScheduledTasks {

    private final Executor asyncExecutor = Executors.newCachedThreadPool();

    //    @Async
//    @Scheduled(fixedDelay = 2000)
    public void test1() {
        System.out.println("aaa   " + LocalDateTime.now());
    }

    //    @Async
//    @Scheduled(fixedDelay = 2000)
    public void test2() {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(10000); // 休眠时间更长
                System.out.println("bbb   " + LocalDateTime.now());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, asyncExecutor);
    }


    @Scheduled(fixedDelay = 2000)
    public void test3() {
        List<String> originalList = new ArrayList<>();
        originalList.add("Apple");
        originalList.add("Banana");
        originalList.add("Cherry");

        // 从原始 List 中筛选出包含字符 "a" 的元素
        Optional<String> first = originalList.stream()
                .filter(s -> s.contains("z")).findFirst();

        first.ifPresent(item -> {
            System.out.println(item);
        });
        String ss = first.orElseGet(() -> {
            originalList.add("啥也没有");
            System.out.println("啥也没有");
            return "啥也没有";
        });
        System.out.println(originalList);
    }
}
