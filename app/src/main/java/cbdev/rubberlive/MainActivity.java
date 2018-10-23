package cbdev.rubberlive;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import static cbdev.rubberlive.R.*;



public class MainActivity extends AppCompatActivity {

    Button btnKot, btnKoc, btnBan, btnKua;
    private boolean checkInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        startAlarm(true,true);
        checkInternet = isNetworkStatusAvialable(this);

        if (checkInternet == false) {
            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
        }


            if (getIntent().getBooleanExtra("closeornot", false)) {
                finish();

        }
        setContentView(R.layout.main_activity);



        btnKot = findViewById(id.btnKottayam);
        btnKoc = findViewById(id.btnKochi);
        btnBan = findViewById(id.btnBangkok);
        btnKua = findViewById(id.btnKualalumpur);

        btnKot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kottayam = new Intent(getApplicationContext(), priceKottayam.class);
                startActivity(kottayam);
            }
        });

        btnKoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kochi = new Intent(getApplicationContext(), priceKochi.class);
                startActivity(kochi);
            }
        });

        btnBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bangkok = new Intent(getApplicationContext(), priceBangkok.class);
                startActivity(bangkok);
            }
        });

        btnKua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kualalumpur = new Intent(getApplicationContext(), priceKualalumpur.class);
                startActivity(kualalumpur);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case id.action_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Fed up of waiting and turning the pages of the newspaper for  Rubber prices??? Rubber live is your solution to all your elastic problems designed especially for the rubber planters and dealers.\n" +
                        "\n" +
                        "Download https://play.google.com/store/apps/details?id=cbdev.rubberlive ";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "RubberLive");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;
//            case id.lang:
//                Intent intent1 = new Intent(this, MainActivity_mala.class);
//                this.startActivity(intent1);
//                break;
            case id.action_about:
                Intent intent = new Intent(this, AboutActivity.class);
                this.startActivity(intent);
                break;
            case id.action_feedback:
                intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback Of App RubberLive");
                intent.putExtra(Intent.EXTRA_TEXT, "My Friend\n       I am very pleased to be able to use your app RubberLive and I look forward to give you a feedback on the same.");
                intent.setData(Uri.parse("mailto:geocheeramkuzhy@gmail.com")); // or just "mailto:" for blank
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }





    public static boolean isNetworkStatusAvialable (Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

    }
    public void onBackPressed() {
        AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
        ab.setTitle("Rate us");
        ab.setMessage("Are you sure want to exit?");
        ab.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //if you want to kill app . from other then your main avtivity.(Launcher)
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);

                //if you want to finish just current activity

            }
        });
        ab.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        ab.show();
    }


//    private void startAlarm(boolean isNotification, boolean isRepeat) {
//        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        Intent myIntent;
//        PendingIntent pendingIntent;
//
//        // SET TIME HERE
//        Calendar calendar= Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY,03);
//        calendar.set(Calendar.MINUTE,52);
//
//
//        myIntent = new Intent(MainActivity.this,AlarmNotificationReceiver.class);
//        pendingIntent = PendingIntent.getBroadcast(this,0,myIntent,0);
//        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent);
//    }
}









