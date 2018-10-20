package so37.cs262.calvin.edu.hw2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.support.v4.content.Loader;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
            implements LoaderManager.LoaderCallbacks<String>{
    EditText mPlayerInput;
    TextView mInfoText;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize variables
        mPlayerInput = findViewById(R.id.PlayerInput);
        mInfoText = findViewById(R.id.InfoText);
        mButton = findViewById(R.id.button);

        if(getSupportLoaderManager().getLoader(0)!=null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    public void searchPlayers(View v) {

        String queryString = mPlayerInput.getText().toString();


        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(mButton.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("mPlayerInput", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle,this);
        }
        else {
                mInfoText.setText("Please check your network connection and try again.");
        }

    }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle args) {
        return new PlayerLoader(this, args.getString("mPlayerInput"));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
        try {
        JSONObject jsonObject = new JSONObject(s);
        JSONArray itemsArray = jsonObject.getJSONArray("items");
        //Iterate through the results
        for(int i = 0; i<itemsArray.length(); i++){
            JSONObject player = itemsArray.getJSONObject(i); //Get the current item
            String playerID = null;
            String email = null;
            String name = null;
            try {

                playerID =  player.getString("id");
                email = player.getString("emailAddress");
            } catch (Exception e){
                e.printStackTrace();
            }
            if (player.getString("name") == null ){
                name = "No name";
            }
            else {
                name = player.getString("name");
            }
            if (email != null && playerID != null){
                mInfoText.setText(playerID + ", " + name + ", " + email);
            } else {
                mInfoText.setText(R.string.noresult);
            }
        }
    }
        catch (Exception e) {
        mInfoText.setText("No Results Found");
        e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset( Loader<String> loader) {

    }
}
