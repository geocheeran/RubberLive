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

public class priceKochi extends AppCompatActivity {

    Button btnGetKoch;
    TextView tvR4InKoch, tvR4UsKoch, tvR5InKoch, tvR5UsKoch;
    String R4InKoch, R4UsKoch, R5InKoch, R5UsKoch;
    String tv;

    ProgressBar progressBar;
    LinearLayout linear;
    TextView detail;
    SharedPreferences s;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_kochi);
        boolean checkInternet = isNetworkStatusAvialable(this);

        if (checkInternet == false) {
            Toast toast = Toast.makeText(this, "No internet connection!\nTurn ON Internet for Latest Price", Toast.LENGTH_SHORT);
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            if( v != null) v.setGravity(Gravity.CENTER);
            toast.show();        }

        s= getSharedPreferences("rubber", MODE_PRIVATE);
        btnGetKoch = findViewById(R.id.btnGetKochi);

        tvR4InKoch = findViewById(R.id.tvR4InKoch);
        tvR4UsKoch = findViewById(R.id.tvR4UsKoch);
        tvR5InKoch = findViewById(R.id.tvR5InKoch);
        tvR5UsKoch = findViewById(R.id.tvR5UsKoch);
        detail = findViewById(R.id.details);

        progressBar = findViewById(R.id.progress);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.MULTIPLY);
        progressBar.setVisibility(View.GONE);
        linear=findViewById(R.id.linear);
//        linear.setVisibility(View.INVISIBLE);
//        btnGetKoch.setVisibility(View.INVISIBLE);
//        detail.setVisibility(View.INVISIBLE);
//        run();


        if(s.getString("tvR4InKoch","a").equals("a")){
            progressBar.setVisibility(View.VISIBLE);
            progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.MULTIPLY);
            linear.setVisibility(View.INVISIBLE);
            btnGetKoch.setVisibility(View.INVISIBLE);
            detail.setVisibility(View.INVISIBLE);
            run();

        }
        else {


            tvR4InKoch.setText(s.getString("tvR4InKoch", "a"));
            tvR4UsKoch.setText(s.getString("tvR4UsKoch", "a"));
            tvR5InKoch.setText(s.getString("tvR5InKoch", "a"));
            tvR5UsKoch.setText(s.getString("tvR5UsKoch", "a"));
            run2();

        }

        btnGetKoch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkInternet = isNetworkStatusAvialable(getApplicationContext());

                if (checkInternet == false) {
                    Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
                }
                if (checkInternet == true) {
                    btnGetKoch.setVisibility(View.INVISIBLE);
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

      final StringBuilder  builder = new StringBuilder();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                try {
                    Document doc = Jsoup.connect("http://rubberboard.org.in/public").get();
                    Elements tables = doc.select("table.table.price-table");
                    Element table = tables.get(1);
                    Elements cells = table.select("td");
                    for (Element a : cells) {
                        builder.append(a.text());
                        builder.append(" ");
                    }
                    tv = builder.toString();
                    String[] words = tv.split(" ");

                    try {
                        R4InKoch = words[1];
                        R4UsKoch = words[2];

                    } catch (Exception e) {
                        R4InKoch = "NA";
                        R4UsKoch = "NA";

                    }
                    try {
                        R5InKoch = words[4];
                        R5UsKoch = words[5];
                    } catch (Exception e) {
                        R5InKoch = "NA";
                        R5UsKoch = "NA";

                    }



                } catch (IOException e) {
                    e.printStackTrace();
                }
                }catch (Exception e) {
                    R4InKoch = "NA";
                    R4UsKoch = "NA";
                    R5InKoch = "NA";
                    R5UsKoch = "NA";

                }




        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                tvR4InKoch.setText(R4InKoch);
                tvR4UsKoch.setText(R4UsKoch);
                tvR5InKoch.setText(R5InKoch);
                tvR5UsKoch.setText(R5UsKoch);

                s.edit().putString("tvR4InKoch", R4InKoch).commit();
                s.edit().putString("tvR4UsKoch", R4UsKoch).commit();
                s.edit().putString("tvR5InKoch", R5InKoch).commit();
                s.edit().putString("tvR5UsKoch", R5UsKoch).commit();

                progressBar.setVisibility(View.GONE);
                linear.setVisibility(View.VISIBLE);
                btnGetKoch.setVisibility(View.VISIBLE);
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
                            Element table = tables.get(1);
                            Elements cells = table.select("td");
                            for (Element a : cells) {
                                builder.append(a.text());
                                builder.append(" ");
                            }
                            tv = builder.toString();
                            String[] words = tv.split(" ");

                            try {
                                R4InKoch = words[1];
                                R4UsKoch = words[2];

                            } catch (Exception e) {
                                R4InKoch = "NA";
                                R4UsKoch = "NA";

                            }
                            try {
                                R5InKoch = words[4];
                                R5UsKoch = words[5];
                            } catch (Exception e) {
                                R5InKoch = "NA";
                                R5UsKoch = "NA";

                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        R4InKoch = "NA";
                        R4UsKoch = "NA";
                        R5InKoch = "NA";
                        R5UsKoch = "NA";

                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            tvR4InKoch.setText(R4InKoch);
                            tvR4UsKoch.setText(R4UsKoch);
                            tvR5InKoch.setText(R5InKoch);
                            tvR5UsKoch.setText(R5UsKoch);

                            s.edit().putString("tvR4InKoch", R4InKoch).commit();
                            s.edit().putString("tvR4UsKoch", R4UsKoch).commit();
                            s.edit().putString("tvR5InKoch", R5InKoch).commit();
                            s.edit().putString("tvR5UsKoch", R5UsKoch).commit();

                            progressBar.setVisibility(View.GONE);
                            linear.setVisibility(View.VISIBLE);
                            btnGetKoch.setVisibility(View.VISIBLE);
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
