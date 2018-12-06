package cs.jokeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

import static com.android.volley.Request.Method.GET;

public class RandomJoke extends AppCompatActivity {

    TextView jokeArea = (TextView) findViewById(R.id.jokeArea);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_joke);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        configureJokeButton();
        configureHomeButton();
    }

    /**
     * This configures the home button and is pretty self explanatory on what it does,
     * but it'll take you back to the home page.
     */
    private void configureHomeButton() {
        Button homerandom = (Button) findViewById(R.id.backrandom);
        homerandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RandomJoke.this, MainActivity.class));
            }
        });
    }

    /**
     * This sets up the random joke button, it calls the get joke function and that should work
     * itself out.
     */
    private void configureJokeButton() {
        Button randomJoke = (Button) findViewById(R.id.randomjoke);
        randomJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJoke();
            }
        });
    }

    /**
     * This class gets the joke, it sets up the whole object request and volley system
     * I'm not sure this is the best way to do it, I saw some people make separate
     * java classes for this like a background function, but this should work...
     *
     * After it makes the request it calls the setText() method and passes it the joke
     * (I don't know how parsing or the json file works just yet, this is in theory)
     */
    private void getJoke() {
        String URL = "https://api.icndb.com";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET, //Request type
                URL, //URL string
                null, //put parameters here
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Joke Site", "It's responding!");
                        setText("This is a good response!");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Joke Site", "It's coming up with an error...");
                        setText("This is a bad response... but still a response nontheless!");
                    }
                }
        );
        requestQueue.add(objectRequest);
    }

    /**
     * This should set the text to whatever is passed to it.
     * @param joke This is the text passed to it, preferably a joke.
     */
    private void setText(String joke) {
        jokeArea.setText(joke);
    }
}