package com.example.demo.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import java.util.ArrayList;
import java.util.List;

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


//    @Scheduled(fixedDelay = 2000)
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


    //@Scheduled(fixedDelay = 2000)
    public void test4() {
//        String sql="Select count(*), SeLeCt COUNT(1) from your_table where your_condition;";
        String sql="SELECT count(*) AS result FROM etl_heronsdb.account_detail from aaa WHERE acc_area IS NOT NULL";

//        Pattern pattern = Pattern.compile("(?i)\\bselect\\s+count\\(.*?\\)\\b");
//        Matcher matcher = pattern.matcher(sql);

        String regex = "\\bselect\\s+count\\(.*?\\)(?s).*?from";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sql);

        // 逐个匹配替换
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(result, "SELECT * from");
        }
        matcher.appendTail(result);

        System.out.println(result.toString());
    }


//    @Scheduled(fixedDelay = 2000)
    public void test5() {

        List<Temp> list = new ArrayList<>();
        Temp temp1=Temp.builder()
                .code("a")
                .desc("A")
                .build();
        Temp temp2=Temp.builder()
                .code("a,b,c")
                .desc("BC")
                .build();
        list.add(temp1);
        list.add(temp2);

        // 使用 flatMap() 将逗号分隔的字符串拆分成多个部分，并组成一个列表
        List<List<String>> splitWords = list.stream()
                .map(s -> Arrays.asList(s.getCode().split(",")))
                .collect(Collectors.toList());
        System.out.println("Split words: " + splitWords);

        // 如果需要将拆分后的部分合并成一个单一的列表，可以再次使用 flatMap()
        List<String> flatSplitWords = list.stream()
                .map(s -> Arrays.asList(s.getCode().split(",")))
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Flat split words: " + flatSplitWords);
    }


    @Scheduled(fixedDelay = 2000)
    public void test6() {
        String jsonString = "{\n" +
                "  \"name\": \"example\",\n" +
                "  \"version\": 1,\n" +
                "  \"data\": [\n" +
                "    [{\"a\": 1, \"b\": 2, \"c\": 3}],\n" +
                "    [{\"a\": 4, \"b\": 5, \"c\": 6}],\n" +
                "    [{\"a\": 7, \"b\": 8, \"c\": 9}]\n" +
                "  ],\n" +
                "  \"description\": \"This is a test JSON\"\n" +
                "}";

        // 解析 JSON 字符串
        JSONObject jsonObject = JSON.parseObject(jsonString);

        // 提取二维数组
        JSONArray dataArray = jsonObject.getJSONArray("data");

        // 使用fastjson的flatten方法平铺二维数组为一维数组
        JSONArray oneDimArray = new JSONArray();
        for (int i = 0; i < dataArray.size(); i++) {
            oneDimArray.addAll(dataArray.getJSONArray(i));
        }

        // 将一维数组放回到 JSON 对象中
        jsonObject.put("data", oneDimArray);

        // 输出结果
        System.out.println(JSON.toJSONString(jsonObject, true));
    }

}
