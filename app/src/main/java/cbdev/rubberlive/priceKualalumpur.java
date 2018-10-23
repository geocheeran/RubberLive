package cbdev.rubberlive;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class priceKualalumpur extends AppCompatActivity {

    Button btnGetKualalumpur;
    TextView tvSmInKua, tvSmUsKua, tvLaInKua, tvLaUsKua;
    String SmInKua, SmUsKua, LaInKua, LaUsKua;
    String tv;

    ProgressBar progressBar;
    LinearLayout linear;
    TextView detail;
    SharedPreferences s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean checkInternet = isNetworkStatusAvialable(this);

        if (checkInternet == false) {
            Toast toast = Toast.makeText(this, "No internet connection!\nTurn ON Internet for Latest Price", Toast.LENGTH_SHORT);
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            if( v != null) v.setGravity(Gravity.CENTER);
            toast.show();
        }
        s= getSharedPreferences("rubber", MODE_PRIVATE);
        setContentView(cbdev.rubberlive.R.layout.activity_price_kualalumpur);

        btnGetKualalumpur = findViewById(cbdev.rubberlive.R.id.btnGetKualalumpur);

        tvSmInKua = findViewById(cbdev.rubberlive.R.id.tvSmInKua);
        tvSmUsKua = findViewById(cbdev.rubberlive.R.id.tvSmUsKua);
        tvLaInKua = findViewById(cbdev.rubberlive.R.id.tvLaInKua);
        tvLaUsKua = findViewById(cbdev.rubberlive.R.id.tvLaUsKua);
        detail = findViewById(cbdev.rubberlive.R.id.details);

        progressBar = findViewById(cbdev.rubberlive.R.id.progress);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.MULTIPLY);
        progressBar.setVisibility(View.GONE);
        linear=findViewById(cbdev.rubberlive.R.id.linear);
//        linear.setVisibility(View.INVISIBLE);
//        btnGetKualalumpur.setVisibility(View.INVISIBLE);
//        detail.setVisibility(View.INVISIBLE);
//        run();

        if(s.getString("tvSmInKua","a").equals("a")){
            progressBar.setVisibility(View.VISIBLE);
            progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.MULTIPLY);
            linear.setVisibility(View.INVISIBLE);
            btnGetKualalumpur.setVisibility(View.INVISIBLE);
            detail.setVisibility(View.INVISIBLE);
            run();

        }
        else {
            tvSmInKua.setText(s.getString("tvSmInKua", "a"));
            tvSmUsKua.setText(s.getString("tvSmUsKua", "a"));
            tvLaInKua.setText(s.getString("tvLaInKua", "a"));
            tvLaUsKua.setText(s.getString("tvLaUsKua", "a"));
            run2();

        }

        btnGetKualalumpur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkInternet = isNetworkStatusAvialable(getApplicationContext());

                if (checkInternet == false) {
                    Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
                }
                if (checkInternet == true) {
                    btnGetKualalumpur.setVisibility(View.INVISIBLE);
                    linear.setVisibility(View.INVISIBLE);
                    detail.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.MULTIPLY);
                    run();
                }
            }
        });

    }

    private void run(){

        final StringBuilder builder = new StringBuilder();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        Document doc = Jsoup.connect("http://rubberboard.org.in/public").get();
                        Elements tables = doc.select("table.table.price-table");
                        Element table = tables.get(3);
                        Elements cells = table.select("td");

                        for (Element a : cells) {
                            builder.append(a.text());
                            builder.append(" ");
                        }

                        tv = builder.toString();
                        String[] words = tv.split(" ");

                        try {
                            SmInKua = words[1];
                            SmUsKua = words[2];
                        } catch (Exception e) {
                            SmInKua = "NA";
                            SmUsKua = "NA";
                        }
                        try {
                            LaInKua = words[4];
                            LaUsKua = words[5];
                        } catch (Exception e) {
                            LaInKua = "NA";
                            LaUsKua = "NA";
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }catch (Exception e) {
                SmInKua = "NA";
                SmUsKua = "NA";
                LaInKua = "NA";
                LaUsKua = "NA";

            }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        tvSmInKua.setText(SmInKua);
                        tvSmUsKua.setText(SmUsKua);
                        tvLaInKua.setText(LaInKua);
                        tvLaUsKua.setText(LaUsKua);

                        s.edit().putString("tvSmInKua", SmInKua).commit();
                        s.edit().putString("tvSmUsKua", SmUsKua).commit();
                        s.edit().putString("tvLaInKua", LaInKua).commit();
                        s.edit().putString("tvLaUsKua", LaUsKua).commit();

                        progressBar.setVisibility(View.GONE);
                        linear.setVisibility(View.VISIBLE);
                        btnGetKualalumpur.setVisibility(View.VISIBLE);
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
                            Element table = tables.get(3);
                            Elements cells = table.select("td");

                            for (Element a : cells) {
                                builder.append(a.text());
                                builder.append(" ");
                            }

                            tv = builder.toString();
                            String[] words = tv.split(" ");

                            try {
                                SmInKua = words[1];
                                SmUsKua = words[2];
                            } catch (Exception e) {
                                SmInKua = "NA";
                                SmUsKua = "NA";
                            }
                            try {
                                LaInKua = words[4];
                                LaUsKua = words[5];
                            } catch (Exception e) {
                                LaInKua = "NA";
                                LaUsKua = "NA";
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        SmInKua = "NA";
                        SmUsKua = "NA";
                        LaInKua = "NA";
                        LaUsKua = "NA";

                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            tvSmInKua.setText(SmInKua);
                            tvSmUsKua.setText(SmUsKua);
                            tvLaInKua.setText(LaInKua);
                            tvLaUsKua.setText(LaUsKua);

                            s.edit().putString("tvSmInKua", SmInKua).commit();
                            s.edit().putString("tvSmUsKua", SmUsKua).commit();
                            s.edit().putString("tvLaInKua", LaInKua).commit();
                            s.edit().putString("tvLaUsKua", LaUsKua).commit();

                            progressBar.setVisibility(View.GONE);
                            linear.setVisibility(View.VISIBLE);
                            btnGetKualalumpur.setVisibility(View.VISIBLE);
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


