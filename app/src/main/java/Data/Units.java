package Data;

import org.json.JSONObject;

/**
 * Created by Raushan on 10/25/2016.
 */

public class Units implements JSONPopulator {

    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {

        temperature = data.optString("temperature");
    }
}
