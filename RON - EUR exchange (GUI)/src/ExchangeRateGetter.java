import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class ExchangeRateGetter {
    private HttpClient client;

    private void createHttpClient() {
        this.client = HttpClient.newHttpClient();
    }

    public ExchangeRateGetter() {
        this.createHttpClient();
    }

    public String getRate(String day, Rates values) throws IOException, InterruptedException, Exception
    {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.exchangeratesapi.io/"+ day + "?symbols="
            + values.getSymbol() +"&base=" + values.getBase())).build();
        HttpResponse<String> response =  this.client.send(request, HttpResponse.BodyHandlers.ofString()); 
        JSONObject responeJsonObject = new JSONObject(response.body());
        
        if(responeJsonObject.has("error"))
            throw new Exception("No data in ECB data sets.");
        
        return responeJsonObject.getJSONObject("rates").get(values.getSymbol()).toString() + "_" + responeJsonObject.get("date").toString();
    }

    
}
