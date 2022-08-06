package Iris;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Perceptron {
    public List<Record> records;
    protected List<Double> weights = new ArrayList<>();
    protected double deviation = 0.2;
    protected double rate;

    public Perceptron (List<Record> records, double rate){
        this.records = records;
        this.rate = rate;
        //losujemy wagi początkowe
        for(int i = 0; i < records.size(); i++)
            weights.add(new Random().nextDouble());
    }
    //aktualizacja wag
    public void updateWeights (Record record){
        List<Double> newWeights = new ArrayList<>();

        for(int i = 0; i < record.values.size(); i++)
            newWeights.add(weights.get(i) + rate * (record.output - getY(record)) * record.values.get(i));

        this.weights = newWeights;
    }
    //aktualizacja odchylenia
    public void updateDeviation (Record record){
        deviation = deviation - rate * (record.output - getY(record));
    }
    //ustalamy wartość wyjściową y
    public double getY (Record record) {
        double inValue = 0, outValue;

        for (int i = 0; i < record.values.size(); i++)
            inValue += record.values.get(i) * weights.get(i);

        outValue = inValue - deviation;
        if (outValue >= 0)
            return 1;
        else return 0;
    }

    public double error() {
        double error = 0.0, out;
        for (Record r : this.records)
            error += Math.pow((r.output - getY(r)), 2);

        out = error / records.size();
        return out;
    }

}
