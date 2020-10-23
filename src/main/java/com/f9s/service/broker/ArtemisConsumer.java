package com.f9s.service.broker;

import com.f9s.service.websocket.TopicInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ArtemisConsumer {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;


    @JmsListener(destination = "${jms.topic.destination}")
    public void receive(Message msg) throws IOException {
        Calendar.getInstance();
        List<String> destList = f9sTopicDestinations.getDistinctDestinations();
        JsonArray jsonParsed = new JsonParser().parse(msg.getPayload().toString()).getAsJsonArray();
        jsonParsed.forEach(x -> filterMessage(x, destList));
    }

    public void sendMessage(String topic, String msg) {
        messagingTemplate.convertAndSend(topic, msg);
    }

    public void filterMessage(JsonElement jsonParsedRow, List<String> dest) {
        Integer result = 0;
        if (jsonParsedRow.getAsJsonObject().has("interval")) {
            String polCode = jsonParsedRow.getAsJsonObject().get("polCode").toString().replaceAll("[^a-zA-Z0-9_-]", "");
            String podCode = jsonParsedRow.getAsJsonObject().get("podCode").toString().replaceAll("[^a-zA-Z0-9_-]", "");
            String baseYearWeek = jsonParsedRow.getAsJsonObject().get("baseYearWeek").toString().replaceAll("[^a-zA-Z0-9_-]", "");
            String interval = jsonParsedRow.getAsJsonObject().get("interval").toString().replaceAll("[^a-zA-Z0-9_-]", "");
            String filterTopic = "/topic/01_01_01_" + polCode + "_" + podCode + "_" + baseYearWeek + "_01_" + interval;
            JsonArray Cell = new JsonArray();
            JsonObject tmp = new JsonObject();
            dest.forEach(x -> {
                if (x.contains(filterTopic)) {
                    System.out.println(jsonParsedRow.toString());
                        Cell.add(arrangeChart(new JsonParser().parse(x.toString()).getAsJsonObject()));
                        tmp.addProperty("marketTypeCode", jsonParsedRow.getAsJsonObject().get("marketTypeCode").toString().replaceAll("[^a-zA-Z0-9_-]", ""));
                        tmp.addProperty("rdTermCode", jsonParsedRow.getAsJsonObject().get("rdTermCode").toString().replaceAll("[^a-zA-Z0-9_-]", ""));
                        tmp.addProperty("containerTypeCode", jsonParsedRow.getAsJsonObject().get("containerTypeCode").toString().replaceAll("[^a-zA-Z0-9_-]", ""));
                        tmp.addProperty("paymentTermCode", jsonParsedRow.getAsJsonObject().get("paymentTermCode").toString().replaceAll("[^a-zA-Z0-9_-]", ""));
                        tmp.addProperty("polCode", jsonParsedRow.getAsJsonObject().get("polCode").toString().replaceAll("[^a-zA-Z0-9_-]", ""));
                        tmp.addProperty("podCode", jsonParsedRow.getAsJsonObject().get("podCode").toString().replaceAll("[^a-zA-Z0-9_-]", ""));
                        tmp.addProperty("qtyUnit", jsonParsedRow.getAsJsonObject().get("qtyUnit").toString().replaceAll("[^a-zA-Z0-9_-]", ""));
                        tmp.addProperty("baseYearWeek", jsonParsedRow.getAsJsonObject().get("baseYearWeek").toString().replaceAll("[^a-zA-Z0-9_-]", ""));
                        tmp.addProperty("interval", jsonParsedRow.getAsJsonObject().get("interval").toString().replaceAll("[^a-zA-Z0-9_-]", ""));
                        tmp.addProperty("Cell", Cell.toString());
                    System.out.println(tmp.toString().replace("\\", ""));
                    sendMessage(x, jsonParsedRow.toString().replace("\\", ""));
                }
            });
        } else if (jsonParsedRow.getAsJsonObject().has("baseYearWeek")) {
            String polCode = jsonParsedRow.getAsJsonObject().get("polCode").toString().replaceAll("[^a-zA-Z0-9_-]", "");
            String podCode = jsonParsedRow.getAsJsonObject().get("podCode").toString().replaceAll("[^a-zA-Z0-9_-]", "");
            String baseYearWeek = jsonParsedRow.getAsJsonObject().get("baseYearWeek").toString().replaceAll("[^a-zA-Z0-9_-]", "");
            String filterTopic = "/topic/01_01_01_" + polCode + "_" + podCode + "_" + baseYearWeek + "_01";
            dest.forEach(x -> {
                if (x.contains(filterTopic)) {
                    System.out.println(jsonParsedRow.toString());
                    sendMessage(x, jsonParsedRow.toString());
                }
            });
        } else {
            String polCode = jsonParsedRow.getAsJsonObject().get("polCode").toString().replaceAll("[^a-zA-Z0-9_-]", "");
            String podCode = jsonParsedRow.getAsJsonObject().get("podCode").toString().replaceAll("[^a-zA-Z0-9_-]", "");
            String filterTopic = "/topic/01_01_01_" + polCode + "_" + podCode + "_01";
            dest.forEach(x -> {
                if (x.contains(filterTopic)) {
                    System.out.println(jsonParsedRow.toString());
                    sendMessage(x, jsonParsedRow.toString());
                }
            });
        }
    }

    public JsonObject arrangeChart(JsonElement jsonParsedRow) {
        JsonElement interval = jsonParsedRow.getAsJsonObject().get("interval");
        String minXAxis = "";
        ArrayList<String> list = new ArrayList<>();
        ArrayList<Double> open = new ArrayList<>();
        ArrayList<Double> low = new ArrayList<>();
        ArrayList<Double> high = new ArrayList<>();
        ArrayList<Double> close = new ArrayList<>();
        ArrayList<Integer> volume = new ArrayList<>();
        ArrayList<Double> changeValue = new ArrayList<>();
        ArrayList<Double> changeRate = new ArrayList<>();

        jsonParsedRow.getAsJsonObject().get("Cell").getAsJsonArray().forEach(x -> {
                    list.add(x.getAsJsonObject().get("xAxis").toString().replaceAll("[^a-zA-Z0-9_-]", ""));
                    open.add(x.getAsJsonObject().get("open").getAsDouble());
                    low.add(x.getAsJsonObject().get("low").getAsDouble());
                    high.add(x.getAsJsonObject().get("high").getAsDouble());
                    close.add(x.getAsJsonObject().get("close").getAsDouble());
                    volume.add(x.getAsJsonObject().get("volume").getAsInt());
                    changeValue.add(x.getAsJsonObject().get("changeValue").getAsDouble());
                    changeRate.add(x.getAsJsonObject().get("changeRate").getAsDouble());
                }
        );
        Integer max = list.stream().mapToInt(Integer::parseInt).max().orElseThrow(NoSuchElementException::new);
        JsonElement currentDate = new JsonParser().parse("");


        Calendar now = Calendar.getInstance();

        ArrayList<String> idx = new ArrayList<>();
        ArrayList<String> xAxis = new ArrayList<>();
        ArrayList<String> timestamp = new ArrayList<>();

        switch (interval.toString().replaceAll("[^a-zA-Z0-9_-]", "")) {
            case "daily":
                idx = ChartXAxis.dayIdx;
                xAxis = ChartXAxis.dayXAxis;
                timestamp = ChartXAxis.dayTimestamp;
                currentDate = new JsonParser().parse(String.valueOf(now.get(Calendar.YEAR) * 10000 + (now.get(Calendar.MONTH) + 1) * 100 + now.get(Calendar.DAY_OF_MONTH)));
                break;
            case "weekly":
                idx = ChartXAxis.weekIdx;
                xAxis = ChartXAxis.weekXAxis;
                timestamp = ChartXAxis.weekTimestamp;
                currentDate = new JsonParser().parse(String.valueOf(now.get(Calendar.YEAR) * 100 + now.get(Calendar.WEEK_OF_YEAR)));
                break;
            case "monthly":
                idx = ChartXAxis.monthIdx;
                xAxis = ChartXAxis.monthXAxis;
                timestamp = ChartXAxis.monthTimestamp;
                currentDate = new JsonParser().parse(String.valueOf(now.get(Calendar.YEAR) * 100 + now.get(Calendar.MONTH) + 1));
                break;
            case "yearly":
                idx = ChartXAxis.yearIdx;
                xAxis = ChartXAxis.yearXAxis;
                timestamp = ChartXAxis.yearTimestamp;
                currentDate = new JsonParser().parse(String.valueOf(now.get(Calendar.YEAR)));
                break;
        }

        int minIdx = xAxis.indexOf(String.valueOf(max));
        int maxIdx = xAxis.indexOf(currentDate.toString());
        if (minIdx == maxIdx) {
            ++maxIdx;
        }
        List<String> listXAxis = xAxis.subList(minIdx, maxIdx);
        List<String> listTimestamp = timestamp.subList(minIdx, maxIdx);

        JsonObject result = new JsonObject();
        listTimestamp.forEach(x -> result.addProperty("intervalTimestamp", x + "010000000000"));
        listXAxis.forEach(x -> result.addProperty("xAxis", x));
        for (int i = 0; i < listXAxis.size(); i++) {
            if (list.contains(listXAxis.get(i))) {
                result.addProperty("open", open.get(i));
                result.addProperty("low", low.get(i));
                result.addProperty("high", high.get(i));
                result.addProperty("close", close.get(i));
                result.addProperty("volume", volume.get(i));
                result.addProperty("changeValue", changeValue.get(i));
                result.addProperty("changeRate", changeRate.get(i));
            } else {
                result.addProperty("open", Double.parseDouble("0.0"));
                result.addProperty("low", Double.parseDouble("0.0"));
                result.addProperty("high", Double.parseDouble("0.0"));
                result.addProperty("close", Double.parseDouble("1000.0"));
                result.addProperty("volume", Integer.parseInt("0"));
                result.addProperty("changeValue", Double.parseDouble("0.0"));
                result.addProperty("changeRate", Double.parseDouble("0.0"));
            }
        }

        return result;
    }
}
