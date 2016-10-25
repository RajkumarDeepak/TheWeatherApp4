package Data;

import org.json.JSONObject;

/**
 * Created by Raushan on 10/25/2016.
 */

public class Chanel implements JSONPopulator {
    private Units units;
    private Item item;

    public Units getUnits() {
        return units;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public void populate(JSONObject data) {


        units = new Units();
        units.populate(data.optJSONObject("units"));

        item = new Item();
        item.populate(data.optJSONObject("item"));

    }
}
