package Service;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import Data.Chanel;

/**
 * Created by Raushan on 10/25/2016.
 */

public class YahooWeatherService {
    private weatherServiceCallBack callBack;
    private String location;
    private Exception error;

    public YahooWeatherService(weatherServiceCallBack callBack) {
        this.callBack = callBack;
    }

    public String getLocation() {
        return location;
    }

    public void refreshWeather(final String location){

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {

                String YQL =String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"@S\")",location);

                String endpoint =String.format("https://query.yahooapis.com/v1/public/yql?q=@s&format=json", Uri.encode(YQL));
                try {
                    URL url = new URL(endpoint);
                    URLConnection connection = url.openConnection();

                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader =new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result =new StringBuilder();
                    String line;

                    while ((line= reader.readLine())!=null){
                        result.append(line);
                    }

                    return result.toString();


                } catch (Exception e) {
                    error =e;

                }


                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if (s== null && error!= null){
                    callBack.serviceFailure(error);

                    return;
                }

                try {
                    JSONObject data =new JSONObject(s);
                    JSONObject queryResults = data.optJSONObject("query");
                    int count = queryResults.optInt("count");


                    if(count== 0){
                        callBack.serviceFailure(new LocationonWeatherException("No weather information found for"+ location));
                        return;
                    }

                    Chanel chanel =new Chanel();
                    chanel.populate(queryResults.optJSONObject("result").optJSONObject("chanel"));


                    callBack.serviceSuccess(chanel);


                } catch (JSONException e) {
                    callBack.serviceFailure(e);
                    e.printStackTrace();
                }

            }
        }.execute(location);

    }
    public class LocationonWeatherException extends Exception{
        public LocationonWeatherException(String detailMessage) {
            super(detailMessage);
        }
    }

}
