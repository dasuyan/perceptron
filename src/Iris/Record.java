package Iris;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Record {
    protected List<Double> values;
    protected String type;
    protected int output;

    public Record(List<String> list) {
        List<String> org = new ArrayList<>(list);
        type = list.get(list.size() - 1);
        org.remove(list.size() - 1);
        this.values = convertStringListToDoubleList(org, Double::parseDouble);
    }

    public Record(String data) {
        String[] x = data.split(",");
        List<Double> list = new ArrayList<>();
        for (String s : x) {
            list.add(Double.parseDouble(s));
        }
        this.values = list;
    }

    public void setOutput(int output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "Record{" +
                "values=" + values +
                ", type='" + type + '\'' +
                ", output=" + output +
                '}';
    }

    public static <T, U> List<U>  convertStringListToDoubleList(List<T> listOfString, Function<T, U> function) {
        return listOfString.stream().map(function).collect(Collectors.toList());
    }
}
