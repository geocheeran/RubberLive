package cbdev.rubberlive;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class priceKottayam extends AppCompatActivity {

    Button btnGetKott;
    TextView tvR4InKott, tvR4UsKott, tvR5InKott, tvR5UsKott;
    TextView tvIsInKott, tvIsUsKott, tvLaInKott, tvLaUsKott;
    String tv;
    String R4InKott, R4UsKott, R5InKott, R5UsKott;
    String IsInKott, IsUsKott, LaInKott, LaUsKott;

    ProgressBar progressBar;
    LinearLayout linear;
    TextView detail;
     SharedPreferences s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_kottayam);
        boolean checkInternet = isNetworkStatusAvialable(this);

        if (checkInternet == false) {
            Toast toast = Toast.makeText(this, "No internet connection!\nTurn ON Internet for Latest Price", Toast.LENGTH_SHORT);
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            if( v != null) v.setGravity(Gravity.CENTER);
            toast.show();        }
         s= getSharedPreferences("rubber", MODE_PRIVATE);

        btnGetKott = findViewById(R.id.btnGetKottayam);

        tvR4InKott = findViewById(R.id.tvR4InKott);
        tvR4UsKott = findViewById(R.id.tvR4UsKott);
        tvR5InKott = findViewById(R.id.tvR5InKott);
        tvR5UsKott = findViewById(R.id.tvR5UsKott);

        tvIsInKott = findViewById(R.id.tvIsInKott);
        tvIsUsKott = findViewById(R.id.tvIsUsKott);
        tvLaInKott = findViewById(R.id.tvLaInKott);
        tvLaUsKott = findViewById(R.id.tvLaUsKott);

        detail = findViewById(R.id.details);

        progressBar = findViewById(R.id.progress);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.MULTIPLY);
        progressBar.setVisibility(View.GONE);
        linear = findViewById(R.id.linear);
//        linear.setVisibility(View.INVISIBLE);
//        btnGetKott.setVisibility(View.INVISIBLE);
//        detail.setVisibility(View.INVISIBLE);
//        run();

        if(s.getString("tvR4InKott","a").equals("a")){
            progressBar.setVisibility(View.VISIBLE);
            progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.MULTIPLY);
            linear.setVisibility(View.INVISIBLE);
            btnGetKott.setVisibility(View.INVISIBLE);
          detail.setVisibility(View.INVISIBLE);
           run();

        }
        else {


            tvR4InKott.setText(s.getString("tvR4InKott", "a"));
            tvR4UsKott.setText(s.getString("tvR4UsKott", "a"));
            tvR5InKott.setText(s.getString("tvR5InKott", "a"));
            tvR5UsKott.setText(s.getString("tvR5UsKott", "a"));
            tvIsInKott.setText(s.getString("tvIsInKott", "a"));
            tvIsUsKott.setText(s.getString("tvIsUsKott", "a"));
            tvLaInKott.setText(s.getString("tvLaInKott", "a"));
            tvLaUsKott.setText(s.getString("tvLaUsKott", "a"));
            run2();

        }

        btnGetKott.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkInternet = isNetworkStatusAvialable(getApplicationContext());

                if (checkInternet == false) {
                    Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
                }
                if (checkInternet == true) {
                    btnGetKott.setVisibility(View.INVISIBLE);
                    linear.setVisibility(View.INVISIBLE);
                    detail.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.MULTIPLY);
                    run();
                }
            }
        });

    }


    private void run() {
            final StringBuilder builder = new StringBuilder();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        try {
                            Document doc = Jsoup.connect("http://rubberboard.org.in/public").get();
                            Elements tables = doc.select("table.table.price-table");
                            Element table = tables.get(0);
                            Elements cells = table.select("td");
                            for (Element a : cells) {
                                builder.append(a.text());
                                builder.append(" ");
                            }
                            tv = builder.toString();
                            String[] words = tv.split(" ");
                            Log.e("array", tv);

                            try {
                                R4InKott = words[1];
                                R4UsKott = words[2];
                            } catch (Exception e) {
                                R4InKott = "NA";
                                R4UsKott = "NA";

                            }
                            try {
                                R5InKott = words[4];
                                R5UsKott = words[5];
                            } catch (Exception e) {
                                R5InKott = "NA";
                                R5UsKott = "NA";
                            }

                            try {
                                IsInKott = words[7];
                                IsUsKott = words[8];

                            } catch (Exception e) {
                                IsInKott = "NA";
                                IsUsKott = "NA";

                            }

                            try {
                                LaInKott = words[10];
                                LaUsKott = words[11];
                            } catch (Exception e) {
                                LaInKott = "NA";
                                LaUsKott = "NA";
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        R4InKott = "NA";
                        R4UsKott = "NA";
                        R5InKott = "NA";
                        R5UsKott = "NA";
                        IsInKott = "NA";
                        IsUsKott = "NA";
                        LaInKott = "NA";
                        LaUsKott = "NA";

                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            tvR4InKott.setText(R4InKott);
                            tvR4UsKott.setText(R4UsKott);
                            tvR5InKott.setText(R5InKott);
                            tvR5UsKott.setText(R5UsKott);

                            tvIsInKott.setText(IsInKott);
                            tvIsUsKott.setText(IsUsKott);
                            tvLaInKott.setText(LaInKott);
                            tvLaUsKott.setText(LaUsKott);

                            s.edit().putString("tvR4InKott", R4InKott).commit();
                            s.edit().putString("tvR4UsKott", R4UsKott).commit();
                            s.edit().putString("tvR5InKott", R5InKott).commit();
                            s.edit().putString("tvR5UsKott", R5UsKott).commit();
                            s.edit().putString("tvIsInKott", IsInKott).commit();
                            s.edit().putString("tvIsUsKott", IsUsKott).commit();
                            s.edit().putString("tvLaInKott", LaInKott).commit();
                            s.edit().putString("tvLaUsKott", LaUsKott).commit();

                            progressBar.setVisibility(View.GONE);
                            linear.setVisibility(View.VISIBLE);
                            btnGetKott.setVisibility(View.VISIBLE);
                            detail.setVisibility(View.VISIBLE);

                        }
                    });
                }
            }).start();


        }




    private void run2() {
        boolean checkInternet = isNetworkStatusAvialable(getApplicationContext());
        if (checkInternet == true) {
            final StringBuilder builder = new StringBuilder();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        try {
                            Document doc = Jsoup.connect("http://rubberboard.org.in/public").get();
                            Elements tables = doc.select("table.table.price-table");
                            Element table = tables.get(0);
                            Elements cells = table.select("td");
                            for (Element a : cells) {
                                builder.append(a.text());
                                builder.append(" ");
                            }
                            tv = builder.toString();
                            String[] words = tv.split(" ");
                            Log.e("array", tv);

                            try {
                                R4InKott = words[1];
                                R4UsKott = words[2];
                            } catch (Exception e) {
                                R4InKott = "NA";
                                R4UsKott = "NA";

                            }
                            try {
                                R5InKott = words[4];
                                R5UsKott = words[5];
                            } catch (Exception e) {
                                R5InKott = "NA";
                                R5UsKott = "NA";
                            }

                            try {
                                IsInKott = words[7];
                                IsUsKott = words[8];

                            } catch (Exception e) {
                                IsInKott = "NA";
                                IsUsKott = "NA";

                            }

                            try {
                                LaInKott = words[10];
                                LaUsKott = words[11];
                            } catch (Exception e) {
                                LaInKott = "NA";
                                LaUsKott = "NA";
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        R4InKott = "NA";
                        R4UsKott = "NA";
                        R5InKott = "NA";
                        R5UsKott = "NA";
                        IsInKott = "NA";
                        IsUsKott = "NA";
                        LaInKott = "NA";
                        LaUsKott = "NA";

                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            tvR4InKott.setText(R4InKott);
                            tvR4UsKott.setText(R4UsKott);
                            tvR5InKott.setText(R5InKott);
                            tvR5UsKott.setText(R5UsKott);

                            tvIsInKott.setText(IsInKott);
                            tvIsUsKott.setText(IsUsKott);
                            tvLaInKott.setText(LaInKott);
                            tvLaUsKott.setText(LaUsKott);

                            s.edit().putString("tvR4InKott", R4InKott).commit();
                            s.edit().putString("tvR4UsKott", R4UsKott).commit();
                            s.edit().putString("tvR5InKott", R5InKott).commit();
                            s.edit().putString("tvR5UsKott", R5UsKott).commit();
                            s.edit().putString("tvIsInKott", IsInKott).commit();
                            s.edit().putString("tvIsUsKott", IsUsKott).commit();
                            s.edit().putString("tvLaInKott", LaInKott).commit();
                            s.edit().putString("tvLaUsKott", LaUsKott).commit();

                            progressBar.setVisibility(View.GONE);
                            linear.setVisibility(View.VISIBLE);
                            btnGetKott.setVisibility(View.VISIBLE);
                            detail.setVisibility(View.VISIBLE);

                        }
                    });
                }
            }).start();


        }
    }
    public static boolean isNetworkStatusAvialable (Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

    }



}



