
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Calendar;
import org.json.JSONObject;

public class ExchangeRateGetter {
    private Calendar year;
    private HttpClient client;
    private Rates values;

    private int getYear() {
        return this.year.get(Calendar.YEAR);
    }

    private void setYear(int newYear) {
        this.year.set(newYear, 0, 1);
    }

    private void createHttpClient() {
        this.client = HttpClient.newHttpClient();
    }

    public ExchangeRateGetter(int inputYear, Rates outputValues) {
        this.year = Calendar.getInstance();
        this.values = outputValues;

        this.setYear(inputYear);
        this.createHttpClient();
    }

    private void getRate(String day) throws IOException, InterruptedException
    {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.exchangeratesapi.io/"+ day + "?symbols=RON&base=EUR")).build();
        HttpResponse<String> response = this.client.send(request, HttpResponse.BodyHandlers.ofString());

        try {
            JSONObject responJsonObject = new JSONObject(response.body());
            Double rate = Double.parseDouble(responJsonObject.getJSONObject("rates").get("RON").toString());
            String date = responJsonObject.get("date").toString();
        
            this.values.addValue(new Amount(date, rate));
        } catch (Exception e) {
            Thread.sleep(1);
        }
    }

    public void getRatesDayByDay() throws InterruptedException, IOException
    {
        int contor = 0;

        while(true)
        {
            String day = String.valueOf(year.get(Calendar.YEAR)) + '-' + String.valueOf(year.get(Calendar.MONTH) + 1) + '-' +  String.valueOf(year.get(Calendar.DAY_OF_MONTH));
            
            this.getRate(day);
            
            contor ++;
            if(contor % 31 == 0)
                System.out.print('-');

            year.roll(Calendar.DAY_OF_MONTH, true);

            if(year.get(Calendar.DAY_OF_MONTH) == 1)
                year.roll(Calendar.MONTH, true);
            
            if((year.get(Calendar.MONTH) == 11) && (year.get(Calendar.DAY_OF_MONTH) == 31))
                {
                    this.getRate(String.valueOf(this.getYear()) + "-12-31");
                    System.out.print('-');
                    System.out.println();
                    this.year.set(this.getYear(), 0, 1);
                    break;
                }
        }
    }
}
