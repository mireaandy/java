import java.util.ArrayList;
import java.util.List;

public class Rates {
    public String base;
    public String currency;
    private List<Amount> values;

    public Rates(String baseString, String currencyString)
    {
        this.base = baseString;
        this.currency = currencyString;
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

    public void printValues()
    {
        System.out.println("EUR - RON exchange rates by day");

        for (Amount value : values) 
        {
            System.out.println(value.getDate() + " " + String.valueOf(value.getValue()));
        }
    }

    public void analyze()
    {
        int daysGrowth = 0;
        int daysDecrease = 0;

        for (int i = 1; i < values.size(); i++) 
        {
            if(values.get(i).getValue() > values.get(i - 1).getValue())
                daysDecrease++;
            else
                daysGrowth++;
        }

        System.out.println(
            "Number of growths" + String.valueOf(daysGrowth) + " \n"
            + "Number of decreases " + String.valueOf(daysDecrease)
        );
    }
}
