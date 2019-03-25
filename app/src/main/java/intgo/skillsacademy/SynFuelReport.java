package intgo.skillsacademy;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SynFuelReport extends AppCompatActivity {

    String attempt, testDate;
    TextView TxtStatus, TxtScore;
    Button retry, complete;
    private String emailT = "", name = "", surname = "", id = "", title = "", discipline = "", department = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syn_fuel_report);

        retry = (Button) findViewById(R.id.bRetry);
        complete = (Button) findViewById(R.id.bComplete);
        TxtStatus = (TextView) findViewById(R.id.tvStatus);
        TxtScore = (TextView) findViewById(R.id.tvScore);

        Intent intent = getIntent();
        String status = intent.getStringExtra("status");
        String score = intent.getStringExtra("score");
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        surname = intent.getStringExtra("surname");
        title = intent.getStringExtra("title");
        discipline = intent.getStringExtra("discipline");
        department = intent.getStringExtra("department");
        attempt = intent.getStringExtra("attempt");
        emailT = intent.getStringExtra("email");

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        testDate = sdf.format(c.getTime()).substring(0, 10);

        if (status.equals("PASS")) {
            TxtStatus.setTextColor(getResources().getColor(R.color.colorSasolPrimary));
            complete.setVisibility(View.VISIBLE);
            retry.setVisibility(View.GONE);
        } else if (Integer.parseInt(attempt) == 3 && status.equals("FAIL")) {
            TxtStatus.setText("REBOOK TRAINING REQUIRED");
        } else {
            TxtStatus.setTextColor(Color.RED);
            retry.setVisibility(View.VISIBLE);
            complete.setVisibility(View.GONE);
        }

        TxtStatus.setText(status);
        TxtScore.setText(score);

        hideNavbar();
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "You selected " + answer, Toast.LENGTH_SHORT).show();
                finish();
                Intent i = new Intent(getApplicationContext(), SynFuelTest.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("id", id);
                i.putExtra("name", name);
                i.putExtra("surname", surname);
                i.putExtra("title", title);
                i.putExtra("discipline", discipline);
                i.putExtra("department", department);
                i.putExtra("attempt", attempt);
                i.putExtra("email", emailT);
                startActivity(i);
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "You selected " + answer, Toast.LENGTH_SHORT).show();
                finish();
                Intent i = new Intent(getApplicationContext(), Home.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                sendReportEmail();
            }
        });
    }

    private void hideNavbar() {
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        getWindow().getDecorView().setSystemUiVisibility(flags);

        // Code below is to handle presses of Volume up or Volume down.
        // Without this, after pressing volume buttons, the navigation bar will
        // show up and won't hide

        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {

            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(flags);
                }
            }
        });
    }

    private void sendReportEmail() {
        File file = new File("/sdcard/SYN/Reports/" + testDate + "-" + id + "-" + attempt + ".pdf");
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{emailT});
        //email.putExtra(Intent.EXTRA_CC, new String[]{ to});
        //email.putExtra(Intent.EXTRA_BCC, new String[]{to});
        email.putExtra(Intent.EXTRA_SUBJECT, id + " - " + surname);
        email.putExtra(Intent.EXTRA_TEXT, "Find Report Sheet Attached for Student : " + id + "\n" +
                "Completed on : " + testDate);

        email.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        email.setType("application/pdf");
        email.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file.getAbsolutePath()));

        //need this to prompts email client only[[
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Select Gmail then Press the White Arrow in the Top Right Corner"));
    }

    @Override
    public void onBackPressed() {

    }
}
