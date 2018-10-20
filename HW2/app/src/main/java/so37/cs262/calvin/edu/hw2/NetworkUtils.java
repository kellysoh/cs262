package so37.cs262.calvin.edu.hw2;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    private static final String Player_BASE_URL =  "https://calvincs262-monopoly.appspot.com/monopoly/v1/players?"; // Base URI for the Books API

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    static String getPlayerInfo(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String playerJSONString = null;

        Uri builtURI;
        try {

            if(queryString == "") {
                builtURI = Uri.parse(Player_BASE_URL.concat("s"));
            }
            else {
                builtURI = Uri.parse(Player_BASE_URL.concat("/").concat(queryString));
            }

            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader (new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0 ) {
                return null;
            }
            playerJSONString = buffer.toString();

        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            if (urlConnection != null ) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(LOG_TAG, playerJSONString);
        return playerJSONString;
    }
}
