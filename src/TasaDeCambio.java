import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class TasaDeCambio {
    @SerializedName("conversion_rates")
    private Map<String, Double> tasasDeConversion;

    public Map<String, Double> getTasasDeConversion() {
        return tasasDeConversion;
    }
}
