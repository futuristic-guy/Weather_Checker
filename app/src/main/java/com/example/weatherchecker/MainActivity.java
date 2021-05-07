package com.example.weatherchecker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

 private ConstraintLayout constraintLayout;
 private Button button;
 private EditText editText;
 private ImageView weatherImage;
 private TextView textView;
 private View view;
 private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        editText = findViewById(R.id.editTextTextPersonName);
        view = findViewById(R.id.view);
        weatherImage = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView2);
        view.setVisibility(View.GONE);
        constraintLayout = findViewById(R.id.constraintLayout);
        constraintLayout.setBackground(getDrawable(R.drawable.background));
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.start();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = editText.getText().toString();
                if(TextUtils.isEmpty(city)){
                    editText.setError("Please Enter City");
                    return;
                }

                getWeatherData(city);
            }
        });


    }//OnCreate Ends


    public void getWeatherData(String city){
        requestQueue = VolleySingleton.getmInstance(getApplicationContext()).getRequestQueue();
        String url = "http://api.openweathermap.org/data/2.5/weather?q=";
        String appId = "&appid=5d2019582a736b2323e5ae971940074a";
        String readyUrl = url + city + appId;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, readyUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        JSONArray arr;
                        try {
                            int code = response.getInt("cod");


                            if(code == 200)
                            {
                                arr = response.getJSONArray("weather");
                                JSONObject weatherObj = arr.getJSONObject(0);
                                String weather = weatherObj.getString("main");
                                String icon = weatherObj.getString("icon");
                                String iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";
                                view.setVisibility(View.VISIBLE);
                                weatherImage.setVisibility(View.VISIBLE);
                                Picasso.with(getApplicationContext()).load(iconUrl).into(weatherImage);
                                textView.setText(weather);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                          view.setVisibility(View.VISIBLE);
                          weatherImage.setVisibility(View.GONE);
                          textView.setText("City Not Found");

                    }
                }
        ); // request Ends

        requestQueue.add(request);

    }

}