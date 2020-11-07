public class Amount {
    private String date;
    private Double value;

    public Amount(String dateNew, Double valueNew) {
        this.date = dateNew;
        this.value = valueNew;
    }

    public String getDate() {
        return this.date;
    }

    public Double getValue() {
        return this.value;
    }
}
