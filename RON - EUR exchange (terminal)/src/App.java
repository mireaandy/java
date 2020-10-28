import java.util.Scanner;

public class App 
{
    private static Scanner scan = new Scanner(System.in);
    private static ExchangeRateGetter rateGetter;
    private static Rates values;

    public static void main(String[] args) throws Exception 
    {
        System.out.println("Please enter a year (2000 - 2019) to analyze :");

        values = new Rates("EUR", "RON");
        int year = 0;
        
        while(year < 1999 || year > 2019)
        {
            year = scan.nextInt();
            
            if(year < 1999 || year > 2019)
                System.out.println("Wrong input.");
        }

        rateGetter = new ExchangeRateGetter(year, values);

        rateGetter.getRatesDayByDay();

        values.analyze();

    }
}
