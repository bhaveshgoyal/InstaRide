package app.instaride.com.instaride;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;




public class NetworkHelper extends Thread {

    private String LOG_TAG = "NetworkHelperThread";
    private final String USER_AGENT = "Mozilla/5.0";

    // 0 = http
    // 1 = https
    private int requestURLType = -1;
    // URL to send request to
    private URL requestURL = null;
    // 0 = GET
    // 1 = POST
    private int requestType = -1;
    private String requestParams;

    public NetworkHelper(String urlString, int urlType, int reqType, String reqParams) {
        try {
            this.requestURL = new URL(urlString);
        } catch (Exception e) {
            Log.d(LOG_TAG, ""+e);
        }
        this.requestURLType = urlType;
        this.requestType = reqType;
        this.requestParams = reqParams;
    }

    public void run() {
        int responseCode = -1;
        StringBuffer response = new StringBuffer();
        String tmp;
        HttpURLConnection urlConnection = null;
        BufferedReader in;

        if(this.requestURLType == -1 || this.requestURL == null || this.requestType == -1) {
            Log.d(LOG_TAG, "NetworkHelper called before full initialization!!");
            return;
        }

        try {
            if(this.requestURLType == 0) {
                urlConnection = (HttpURLConnection) this.requestURL.openConnection();
            }
            else if(this.requestURLType == 1) {
                // TODO Implement Https stuff
                // urlConnection = (HttpsURLConnection) this.requestURL.openConnection();
            }
            urlConnection.setRequestProperty("User-Agent", USER_AGENT);
            if(requestType == 0) {
                urlConnection.setRequestMethod("GET");

            } else if(requestType == 1) {
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

                urlConnection.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());
                out.writeBytes(this.requestParams);
                out.flush();
                out.close();
            }

            responseCode = urlConnection.getResponseCode();
            Log.d(LOG_TAG, "Request response code: " + responseCode);
            in = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));
            while((tmp = in.readLine()) != null) {
                response.append(tmp);
            }
            in.close();
        } catch (Exception e) {
            Log.d(LOG_TAG, ""+e);
        } finally {
            urlConnection.disconnect();
        }
    }
}
