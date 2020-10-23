package com.f9s.service.broker;

import com.f9s.service.F9SService2Application;
import lombok.Synchronized;
import lombok.experimental.UtilityClass;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

@UtilityClass
public class ChartXAxis {
    static ArrayList<String> dayIdx = new ArrayList<String>();
    static ArrayList<String> dayTimestamp = new ArrayList<String>();
    static ArrayList<String> dayXAxis = new ArrayList<String>();
    static ArrayList<String> weekIdx = new ArrayList<String>();
    static ArrayList<String> weekTimestamp = new ArrayList<String>();
    static ArrayList<String> weekXAxis = new ArrayList<String>();
    static ArrayList<String> monthIdx = new ArrayList<String>();
    static ArrayList<String> monthTimestamp = new ArrayList<String>();
    static ArrayList<String> monthXAxis = new ArrayList<String>();
    static ArrayList<String> yearIdx = new ArrayList<String>();
    static ArrayList<String> yearTimestamp = new ArrayList<String>();
    static ArrayList<String> yearXAxis = new ArrayList<String>();

    @Synchronized
    public ArrayList<String> getTable(String tableName) throws IOException {
        ArrayList<String> result = new ArrayList<>();
        switch (tableName) {
            case "dayIdx":
//                setTable("dayIdx.csv");
                return dayIdx;
            case "dayTimestamp":
//                setTable("dayTimestamp.csv");
                return dayTimestamp;
            case "dayXAxis":
//                setTable("dayXAxis.csv");
                return dayXAxis;
            case "weekIdx":
//                setTable("weekIdx.csv");
                return weekIdx;
            case "weekTimestamp":
//                setTable("weekTimestamp.csv");
                return weekTimestamp;
            case "weekXAxis":
//                setTable("weekXAxis.csv");
                return weekXAxis;
            case "monthIdx":
//                setTable("monthIdx.csv");
                return monthIdx;
            case "monthTimestamp":
//                setTable("monthTimestamp.csv");
                return monthTimestamp;
            case "monthXAxis":
//                setTable("monthXAxis.csv");
                return monthXAxis;
            case "yearIdx":
//                setTable("yearIdx.csv");
                return yearIdx;
            case "yearTimestamp":
//                setTable("yearTimestamp.csv");
                return yearTimestamp;
            case "yearXAxis":
//                setTable("yearXAxis.csv");
                return yearXAxis;
        }
        ;
        return result;
    }

    @Synchronized
    public void setTable(String targetTable) throws IOException {

        try (InputStream inputStream = F9SService2Application.class.getResourceAsStream("/" + targetTable);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            Scanner input = new Scanner(reader);
            input.useDelimiter(",");

            System.out.println("File Loaded:" + reader.toString());

            switch (targetTable) {
                case "dayIdx.csv":
                    input.forEachRemaining(x -> dayIdx.add(x));
                    break;
                case "dayTimestamp.csv":
                    input.forEachRemaining(x -> dayTimestamp.add(x));
                    break;
                case "dayXAxis.csv":
                    input.forEachRemaining(x -> dayXAxis.add(x));
                    break;
                case "weekIdx.csv":
                    input.forEachRemaining(x -> weekIdx.add(x));
                    break;
                case "weekTimestamp.csv":
                    input.forEachRemaining(x -> weekTimestamp.add(x));
                    break;
                case "weekXAxis.csv":
                    input.forEachRemaining(x -> weekXAxis.add(x));
                    break;
                case "monthIdx.csv":
                    input.forEachRemaining(x -> monthIdx.add(x));
                    break;
                case "monthTimestamp.csv":
                    input.forEachRemaining(x -> monthTimestamp.add(x));
                    break;
                case "monthXAxis.csv":
                    input.forEachRemaining(x -> monthXAxis.add(x));
                    break;
                case "yearIdx.csv":
                    input.forEachRemaining(x -> yearIdx.add(x));
                    break;
                case "yearTimestamp.csv":
                    input.forEachRemaining(x -> yearTimestamp.add(x));
                    break;
                case "yearXAxis.csv":
                    input.forEachRemaining(x -> yearXAxis.add(x));
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;
    }


}
