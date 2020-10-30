import java.util.ArrayList;
import java.util.List;
import javafx.scene.chart.XYChart;

public class Rates {
    private String base;
    private String symbol;
    private List<Amount> values;

    public Rates()
    {
        this.base = new String();
        this.symbol = new String();
        this.values = new ArrayList<Amount>();
    }

    public void addValue(Amount newValue)
    {
        boolean isNot = true;
        for (Amount value : values) 
        {
            if (value.getDate().compareTo(newValue.getDate()) == 0)
                isNot = false;
        }

        if(isNot)
            this.values.add(newValue);
    }

    public boolean isEmpty()
    {
        return this.values.isEmpty();
    }
    
    public String getBase()
    {
        return this.base;
    }

    public void setBase(String basesymbol)
    {
        this.base = basesymbol;
    }

    public String getSymbol()
    {
        return this.symbol;
    }

    public void setSymbol(String symbolsymbol)
    {
        this.symbol = symbolsymbol;
    }

    public double[] analyze(XYChart.Series<String, Number> rateSeries)
    {
        double daysGrowth = 0;
        double daysDecrease = 0;
        double min = values.get(1).getValue();
        double max = 0;
        double[] answer;

        for (int i = 1; i < values.size(); i++) 
        {
            min = min > values.get(i).getValue() ? values.get(i).getValue() : min;
            max = max < values.get(i).getValue() ? values.get(i).getValue() : max;
            rateSeries.getData().add(new XYChart.Data<>(values.get(i).getDate(), values.get(i).getValue()));
            if(values.get(i).getValue() > values.get(i - 1).getValue())
                daysDecrease++;
            else
                daysGrowth++;
        }

        answer = new double[] {daysGrowth, daysDecrease, min, max};

        return answer;
    }
}
