import java.util.ArrayList;
import java.util.List;

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

    public int[] analyze()
    {
        int daysGrowth = 0;
        int daysDecrease = 0;
        int[] answer;

        for (int i = 1; i < values.size(); i++) 
        {
            if(values.get(i).getValue() > values.get(i - 1).getValue())
                daysDecrease++;
            else
                daysGrowth++;
        }

        answer = new int[] {daysGrowth, daysDecrease};

        return answer;
    }
}
