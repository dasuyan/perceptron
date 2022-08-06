package Iris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Train {
    public static String train;
    public static String test;

    public static List<Record> trainSet;
    public static List<Record> testSet;

    private static String class0;
    private static String class1;

    public Train(String train, String test) {
        Train.train = train;
        Train.test = test;
        trainSet = createListOfRecords(Objects.requireNonNull(readFile(train)));
        testSet = createListOfRecords(Objects.requireNonNull(readFile(test)));
    }

    public static void Process() {
        //wybieramy współczynnik uczenia
        Scanner scan = new Scanner(System.in);
        System.out.println("Set the learning rate");
        double rate = scan.nextDouble();

        Perceptron perceptron = new Perceptron(trainSet, rate);

        int count = 0;
        while (perceptron.error() == 0.0 || count < 1000){
            for (Record r : perceptron.records){
                perceptron.updateWeights(r);
                perceptron.updateDeviation(r);
            }
            count++;
        }
        //sprawdzamy dokładność klasyfikacji
        double accuracy = 0.0;
        for (Record r : testSet)
            if (perceptron.getY(r) == getOutputValue(perceptron.records, r.type))
                accuracy++;

        System.out.println("Deviation: "+ perceptron.deviation);
        System.out.println("Final weights: " + perceptron.weights);
        System.out.println("Train-set size: " + testSet.size());
        System.out.println("Correctly classified: " + (int) accuracy);
        System.out.println("Accuracy: " + accuracy / testSet.size() * 100 + "%");

        System.out.println("****************************************************************************************");
        System.out.println("Type \"exit\" to stop the application");
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter your vector");
            String data = input.nextLine();

            if ("exit".equals(data)) {
                break;
            } else {
                Record inputRecord = new Record(data);
                System.out.println(inputRecord);
                String result;
                if (perceptron.getY(inputRecord) == 0)
                    result = class0;
                else result = class1;
                System.out.println("Classified as: " + result);
            }
        }
    }

    public static List<List<String>> readFile(String fileName)  {
        BufferedReader reader;
        List<List<String>> list = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                List<String> row = List.of(line.split(","));
                list.add(row);
                line = reader.readLine();
            }
            reader.close();
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Record> createListOfRecords (List<List<String>> data) {
        List<Record> records = new ArrayList<>();
        for (List<String> e : data) {
            Record record = new Record(e);
            records.add(record);
        }
        //Ustalamy wartość wyjściową y
        class0 = records.get(0).type;
        for (Record r : records) {
            r.setOutput(getOutputValue(records, r.type));
            if (!r.type.equals(class0))
                class1 = r.type;
        }
        return records;
    }

    public static int getOutputValue(List<Record> records, String type){
        List<String> types = new ArrayList<>();
        for(Record r : records)
            types.add(r.type);

        types =  types.stream().distinct().collect(Collectors.toList());
        return types.indexOf(type);
    }
}
