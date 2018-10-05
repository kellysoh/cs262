package so37.cs262.calvin.edu.lab05;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String>{
    EditText mBookInput;
    TextView mTitleText;
    TextView mAuthorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialize variables
        mBookInput = findViewById(R.id.bookInput);
        mTitleText = findViewById(R.id.titleText);
        mAuthorText = findViewById(R.id.authorText);
    }

        //create method of searching books
        public void searchBooks(View v) {
            String queryString = mBookInput.getText().toString();

            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);

            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected() && queryString.length()!=0) {
                mAuthorText.setText("");
                mTitleText.setText(R.string.loading);
                Bundle queryBundle = new Bundle();
                queryBundle.putString("queryString", queryString);
                getSupportLoaderManager().restartLoader(0, queryBundle,this);
            }
            else {
                if (queryString.length() == 0) {
                    mAuthorText.setText("");
                    mTitleText.setText("Please enter a search term");
                } else {
                    mAuthorText.setText("");
                    mTitleText.setText("Please check your network connection and try again.");
                }
            }
            if(getSupportLoaderManager().getLoader(0)!=null){
                getSupportLoaderManager().initLoader(0,null,this);
            }
        }


    @Override
    public Loader<String> onCreateLoader(int i, Bundle args) {
        return new BookLoader(this, args.getString("queryString"));
    }

    @Override
    public void onLoadFinished( Loader<String> loader, String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            //Iterate through the results
            for(int i = 0; i<itemsArray.length(); i++){
                JSONObject book = itemsArray.getJSONObject(i); //Get the current item
                String title=null;
                String authors=null;
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (Exception e){
                    e.printStackTrace();
                }

                //If both a title and author exist, update the TextViews and return
                if (title != null && authors != null){
                    mTitleText.setText(title);
                    mAuthorText.setText(authors);
                    return;
                } else {
                    mTitleText.setText(R.string.no_results);
                    mAuthorText.setText("");
                }
            }
        }
        catch (Exception e) {
            mTitleText.setText("No Results Found");
            mAuthorText.setText("");
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset( Loader<String> loader) {

    }
}


