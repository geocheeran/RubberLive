package cbdev.rubberlive;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.appcompat.app.AppCompatActivity;
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

public class priceBangkok extends AppCompatActivity {

    String tv;
    Button btnGetBangkok;
    TextView tvR1InBang, tvR1UsBang, tvR2InBang, tvR2UsBang;
    TextView tvR3InBang, tvR3UsBang, tvR4InBang, tvR4UsBang;
    TextView tvR5InBang, tvR5UsBang;
    String R1InBang, R1UsBang, R2InBang, R2UsBang;
    String R3InBang, R3UsBang, R4InBang, R4UsBang;
    String R5InBang, R5UsBang;

    ProgressBar progressBar;
    LinearLayout linear;
    TextView detail;
    SharedPreferences s;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_bangkok);

        btnGetBangkok = findViewById(R.id.btnGetBangkok);

        boolean checkInternet = isNetworkStatusAvialable(this);

        if (checkInternet == false) {
            Toast toast = Toast.makeText(this, "No internet connection!\nTurn ON Internet for Latest Price", Toast.LENGTH_SHORT);
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            if( v != null) v.setGravity(Gravity.CENTER);
            toast.show();
        }
        s= getSharedPreferences("rubber", MODE_PRIVATE);
        tvR1InBang = findViewById(R.id.tvR1InBang);
        tvR1UsBang = findViewById(R.id.tvR1UsBang);
        tvR2InBang = findViewById(R.id.tvR2InBang);
        tvR2UsBang = findViewById(R.id.tvR2UsBang);

        tvR3InBang = findViewById(R.id.tvR3InBang);
        tvR3UsBang = findViewById(R.id.tvR3UsBang);
        tvR4InBang = findViewById(R.id.tvR4InBang);
        tvR4UsBang = findViewById(R.id.tvR4UsBang);

        tvR5InBang = findViewById(R.id.tvR5InBang);
        tvR5UsBang = findViewById(R.id.tvR5UsBang);
        detail = findViewById(R.id.details);


        progressBar = findViewById(R.id.progress);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.MULTIPLY);
        linear=findViewById(R.id.linear);
        progressBar.setVisibility(View.GONE);
//        linear.setVisibility(View.INVISIBLE);
//        btnGetBangkok.setVisibility(View.INVISIBLE);
//        detail.setVisibility(View.INVISIBLE);
//        run();


        if(s.getString("tvR1InBang","a").equals("a")){
            progressBar.setVisibility(View.VISIBLE);
            progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.MULTIPLY);
            linear.setVisibility(View.INVISIBLE);
            btnGetBangkok.setVisibility(View.INVISIBLE);
            detail.setVisibility(View.INVISIBLE);
            run();

        }
        else {


            tvR1InBang.setText(s.getString("tvR1InBang", "a"));
            tvR1UsBang.setText(s.getString("tvR1UsBang", "a"));
            tvR2InBang.setText(s.getString("tvR2InBang", "a"));
            tvR2UsBang.setText(s.getString("tvR2UsBang", "a"));

            tvR3InBang.setText(s.getString("tvR3InBang", "a"));
            tvR3UsBang.setText(s.getString("tvR3UsBang", "a"));
            tvR4InBang.setText(s.getString("tvR4InBang", "a"));
            tvR4UsBang.setText(s.getString("tvR4UsBang", "a"));

            tvR5InBang.setText(s.getString("tvR5InBang", "a"));
            tvR5UsBang.setText(s.getString("tvR5UsBang", "a"));
            run2();

        }



        btnGetBangkok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkInternet = isNetworkStatusAvialable(getApplicationContext());

                if (checkInternet == false) {
                    Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
                }
                if (checkInternet == true) {
                btnGetBangkok.setVisibility(View.INVISIBLE);
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
                        Element table = tables.get(2);

                        tv = table.toString();
                        Elements cells = table.select("td");
                        for (Element a : cells) {
                            builder.append(a.text());
                            builder.append(" ");
                        }
                        tv = builder.toString();
                        String[] words = tv.split(" ");

                        try {
                            R1InBang = words[1];
                            R1UsBang = words[2];
                        } catch (Exception e) {
                            R1InBang = "NA";
                            R1UsBang = "NA";

                        }
                        try {
                            R2InBang = words[4];
                            R2UsBang = words[5];
                        } catch (Exception e) {
                            R2InBang = "NA";
                            R2UsBang = "NA";
                        }

                        try {
                            R3InBang = words[7];
                            R3UsBang = words[8];
                        } catch (Exception e) {
                            R3InBang = "NA";
                            R3UsBang = "NA";
                        }

                        try {
                            R4InBang = words[10];
                            R4UsBang = words[11];
                        } catch (Exception e) {
                            R4InBang = "NA";
                            R4UsBang = "NA";
                        }

                        try {
                            R5InBang = words[13];
                            R5UsBang = words[14];

                        } catch (Exception e) {
                            R5InBang = "NA";
                            R5UsBang = "NA";

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }catch (Exception e) {
                    R1InBang = "NA";
                    R1UsBang = "NA";
                    R2InBang = "NA";
                    R2UsBang = "NA";
                    R3InBang = "NA";
                    R3UsBang = "NA";
                    R4InBang = "NA";
                    R4UsBang = "NA";
                    R5InBang = "NA";
                    R5UsBang = "NA";

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        tvR1InBang.setText(R1InBang);
                        tvR1UsBang.setText(R1UsBang);
                        tvR2InBang.setText(R2InBang);
                        tvR2UsBang.setText(R2UsBang);

                        tvR3InBang.setText(R3InBang);
                        tvR3UsBang.setText(R3UsBang);
                        tvR4InBang.setText(R4InBang);
                        tvR4UsBang.setText(R4UsBang);

                        tvR5InBang.setText(R5InBang);
                        tvR5UsBang.setText(R5UsBang);

                        s.edit().putString("tvR1InBang", R1InBang).commit();
                        s.edit().putString("tvR1UsBang", R1UsBang).commit();
                        s.edit().putString("tvR2InBang", R2InBang).commit();
                        s.edit().putString("tvR2UsBang", R2UsBang).commit();
                        s.edit().putString("tvR3InBang", R3InBang).commit();
                        s.edit().putString("tvR3UsBang", R3UsBang).commit();
                        s.edit().putString("tvR4InBang", R4InBang).commit();
                        s.edit().putString("tvR4UsBang", R4UsBang).commit();
                        s.edit().putString("tvR5InBang", R5InBang).commit();
                        s.edit().putString("tvR5UsBang", R5UsBang).commit();

                        progressBar.setVisibility(View.GONE);
                        linear.setVisibility(View.VISIBLE);
                        btnGetBangkok.setVisibility(View.VISIBLE);
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
                            Element table = tables.get(2);

                            tv = table.toString();
                            Elements cells = table.select("td");
                            for (Element a : cells) {
                                builder.append(a.text());
                                builder.append(" ");
                            }
                            tv = builder.toString();
                            String[] words = tv.split(" ");

                            try {
                                R1InBang = words[1];
                                R1UsBang = words[2];
                            } catch (Exception e) {
                                R1InBang = "NA";
                                R1UsBang = "NA";

                            }
                            try {
                                R2InBang = words[4];
                                R2UsBang = words[5];
                            } catch (Exception e) {
                                R2InBang = "NA";
                                R2UsBang = "NA";
                            }

                            try {
                                R3InBang = words[7];
                                R3UsBang = words[8];
                            } catch (Exception e) {
                                R3InBang = "NA";
                                R3UsBang = "NA";
                            }

                            try {
                                R4InBang = words[10];
                                R4UsBang = words[11];
                            } catch (Exception e) {
                                R4InBang = "NA";
                                R4UsBang = "NA";
                            }

                            try {
                                R5InBang = words[13];
                                R5UsBang = words[14];

                            } catch (Exception e) {
                                R5InBang = "NA";
                                R5UsBang = "NA";

                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        R1InBang = "NA";
                        R1UsBang = "NA";
                        R2InBang = "NA";
                        R2UsBang = "NA";
                        R3InBang = "NA";
                        R3UsBang = "NA";
                        R4InBang = "NA";
                        R4UsBang = "NA";
                        R5InBang = "NA";
                        R5UsBang = "NA";

                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            tvR1InBang.setText(R1InBang);
                            tvR1UsBang.setText(R1UsBang);
                            tvR2InBang.setText(R2InBang);
                            tvR2UsBang.setText(R2UsBang);

                            tvR3InBang.setText(R3InBang);
                            tvR3UsBang.setText(R3UsBang);
                            tvR4InBang.setText(R4InBang);
                            tvR4UsBang.setText(R4UsBang);

                            tvR5InBang.setText(R5InBang);
                            tvR5UsBang.setText(R5UsBang);

                            s.edit().putString("tvR1InBang", R1InBang).commit();
                            s.edit().putString("tvR1UsBang", R1UsBang).commit();
                            s.edit().putString("tvR2InBang", R2InBang).commit();
                            s.edit().putString("tvR2UsBang", R2UsBang).commit();
                            s.edit().putString("tvR3InBang", R3InBang).commit();
                            s.edit().putString("tvR3UsBang", R3UsBang).commit();
                            s.edit().putString("tvR4InBang", R4InBang).commit();
                            s.edit().putString("tvR4UsBang", R4UsBang).commit();
                            s.edit().putString("tvR5InBang", R5InBang).commit();
                            s.edit().putString("tvR5UsBang", R5UsBang).commit();

                            progressBar.setVisibility(View.GONE);
                            linear.setVisibility(View.VISIBLE);
                            btnGetBangkok.setVisibility(View.VISIBLE);
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
