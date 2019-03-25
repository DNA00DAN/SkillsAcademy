package intgo.skillsacademy;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class InductionTestResults extends AppCompatActivity {

    private boolean exit = false;
    private Button btnLogin, btnCancel;
    private Spinner test_result;
    private List<String> tests;
    private String testDate = "";
    private TextView employeeTxt, levelTxt;
    private TextView q1Txt, q2Txt, q3Txt, q4Txt, q5Txt;
    private TextView a1Txt, a2Txt, a3Txt, a4Txt, a5Txt;
    private TextView ca1Txt, ca2Txt, ca3Txt, ca4Txt, ca5Txt;
    private LinearLayout q1Ans, q2Ans, q3Ans, q4Ans, q5Ans;
    EditText txtUsername, txtPassword;
    LocalSupervisor supervisorLocalStore;
    LocalUser userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_results);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initializeComponents();

        Intent intent = getIntent();
        String status = intent.getStringExtra("status");
        if (status.equals("PASS")) {
            Toast.makeText(this, "You can now apply for the next level", Toast.LENGTH_LONG).show();
        }
        loadTestResults();
        Login();


        test_result.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //Performing action onItemSelected and onNothing selected
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                //Log.d("date:",test_result.getSelectedItem().toString());
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                testDate = tests.get(position);
                List<InductionResult> results = db.getAllInductionData(testDate);
                for (InductionResult res : results) {
                    employeeTxt.setText(res.getEmployee());
                    levelTxt.setText(res.getLevel());

                    q1Txt.setText(res.getQ1());
                    q2Txt.setText(res.getQ2());
                    q3Txt.setText(res.getQ3());
                    q4Txt.setText(res.getQ4());
                    q5Txt.setText(res.getQ5());


                    a1Txt.setText(res.getA1());
                    a2Txt.setText(res.getA2());
                    a3Txt.setText(res.getA3());
                    a4Txt.setText(res.getA4());
                    a5Txt.setText(res.getA5());


                    ca1Txt.setText(res.getCA1());
                    ca2Txt.setText(res.getCA2());
                    ca3Txt.setText(res.getCA3());
                    ca4Txt.setText(res.getCA4());
                    ca5Txt.setText(res.getCA5());

                }

                Toast.makeText(getApplicationContext(), tests.get(position), Toast.LENGTH_SHORT).show();

                evaluateResults(a1Txt, ca1Txt, q1Ans);
                evaluateResults(a2Txt, ca2Txt, q2Ans);
                evaluateResults(a3Txt, ca3Txt, q3Ans);
                evaluateResults(a4Txt, ca4Txt, q4Ans);
                evaluateResults(a5Txt, ca5Txt, q5Ans);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        }); // (optional)
    }

    private void Login() {
        // Create Object of Dialog class
        final Dialog login = new Dialog(this);
        // Set GUI of Login screen
        login.setContentView(R.layout.activity_login_dialog);
        login.setTitle(Html.fromHtml("<h2><font color='#033b61'>Supervisor Login</font></h2>"));
        login.setCancelable(false);

        // Init Components of Login GUI
        btnLogin = (Button) login.findViewById(R.id.btnLogin);
        btnCancel = (Button) login.findViewById(R.id.btnCancel);
        txtUsername = (EditText) login.findViewById(R.id.txtUsername);
        txtPassword = (EditText) login.findViewById(R.id.txtPassword);

        Typeface myTypeFace = Typeface.createFromAsset(getAssets(), "Fonts/gustanmedium.ttf");
        txtPassword.setTypeface(myTypeFace);
        txtUsername.setTypeface(myTypeFace);
        btnCancel.setTypeface(myTypeFace);
        btnLogin.setTypeface(myTypeFace);
        TextView tv1 = (TextView) login.findViewById(R.id.tv1);
        TextView tv2 = (TextView) login.findViewById(R.id.tv2);
        tv1.setTypeface(myTypeFace);
        tv2.setTypeface(myTypeFace);

        txtPassword.setSingleLine();
        txtUsername.setSingleLine();

        txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        btnCancel.setVisibility(View.GONE);

        hideNavbar();
        // Attached listener for Login GUI button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLocalStore = new LocalUser(getApplicationContext());
                supervisorLocalStore = new LocalSupervisor(getApplicationContext());
                String num = txtUsername.getText().toString().trim();
                String pass = txtPassword.getText().toString().trim();
                pass = md5(pass);
                String email = supervisorLocalStore.getEmail();
                String emp_num = userLocalStore.getNumber();
                String emp_lev = userLocalStore.getLevel();


                if (num.length() == 0 || pass.length() == 0) {
                    if (num.length() == 0) {
                        txtUsername.requestFocus();
                        txtUsername.setError("FIELD CANNOT BE EMPTY");
                    }
                    if (pass.length() == 0) {
                        txtPassword.requestFocus();
                        txtPassword.setError("FIELD CANNOT BE EMPTY");
                    }
                }
                else
                if (pass.length() < 8) {
                    txtPassword.requestFocus();
                    txtPassword.setError("MIN LENGTH 8");

                }
                else if (num.equals(supervisorLocalStore.getNumber())) {
                    if (pass.equals(supervisorLocalStore.getPassword())) {


                        login.dismiss();
                        sendReport(email, emp_num, emp_lev);

                    } else {
                        txtPassword.requestFocus();
                        txtPassword.setText("");
                        txtPassword.setError("PASSWORD  IS INCORRECT");
                    }
                } else {
                    txtUsername.requestFocus();
                    txtUsername.setText("");
                    txtUsername.setError("PERSONNEL NUMBER IS INCORRECT");
                }

            }

        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.dismiss();
            }
        });


        // Make dialog box visible.
        login.show();
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

    private void sendReport(String super_email, String emp_num, String emp_lev) {

        File file = new File("/sdcard/SKA/Reports/" + testDate + ".pdf");

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{super_email});
        //email.putExtra(Intent.EXTRA_CC, new String[]{ to});
        //email.putExtra(Intent.EXTRA_BCC, new String[]{to});
        email.putExtra(Intent.EXTRA_SUBJECT, emp_num + " - " + emp_lev + " Test - " + testDate);
        email.putExtra(Intent.EXTRA_TEXT, "Find Report Sheet Attached for Employee : " + emp_num + "\n" +
                "Completed on : " + testDate);

        email.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        email.setType("application/pdf");
        email.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file.getAbsolutePath()));

        //need this to prompts email client only
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose an Email client :"));

    }

    private static final String md5(final String password) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    private void evaluateResults(TextView ans, TextView cAns, LinearLayout layout) {
        if (ans.getText().toString().equals(cAns.getText().toString())) {
            ans.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            layout.setVisibility(View.GONE);
        } else {
            ans.setTextColor(Color.parseColor("#ee2c2c"));
            layout.setVisibility(View.VISIBLE);
        }
    }


    private void loadTestResults() {
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        tests = db.getAllTests();
        Typeface myTypeFace = Typeface.createFromAsset(getAssets(), "Fonts/gustanmedium.ttf");
        CustomResultAdapter customAdapter = new CustomResultAdapter(getApplicationContext(), tests, myTypeFace);
        test_result.setAdapter(customAdapter);
    }

    private void initializeComponents() {
        test_result = (Spinner) findViewById(R.id.dateSpinner);
        employeeTxt = (TextView) findViewById(R.id.employeeTxt);
        levelTxt = (TextView) findViewById(R.id.levelTxt);

        q1Txt = (TextView) findViewById(R.id.q1Txt);
        q2Txt = (TextView) findViewById(R.id.q2Txt);
        q3Txt = (TextView) findViewById(R.id.q3Txt);
        q4Txt = (TextView) findViewById(R.id.q4Txt);
        q5Txt = (TextView) findViewById(R.id.q5Txt);


        a1Txt = (TextView) findViewById(R.id.a1Txt);
        a2Txt = (TextView) findViewById(R.id.a2Txt);
        a3Txt = (TextView) findViewById(R.id.a3Txt);
        a4Txt = (TextView) findViewById(R.id.a4Txt);
        a5Txt = (TextView) findViewById(R.id.a5Txt);


        ca1Txt = (TextView) findViewById(R.id.ca1Txt);
        ca2Txt = (TextView) findViewById(R.id.ca2Txt);
        ca3Txt = (TextView) findViewById(R.id.ca3Txt);
        ca4Txt = (TextView) findViewById(R.id.ca4Txt);
        ca5Txt = (TextView) findViewById(R.id.ca5Txt);

        TextView qTxt1 = (TextView) findViewById(R.id.qTxt1);
        TextView qTxt2 = (TextView) findViewById(R.id.qTxt2);
        TextView qTxt3 = (TextView) findViewById(R.id.qTxt3);
        TextView qTxt4 = (TextView) findViewById(R.id.qTxt4);
        TextView qTxt5 = (TextView) findViewById(R.id.qTxt5);

        TextView aTxt1 = (TextView) findViewById(R.id.aTxt1);
        TextView aTxt2 = (TextView) findViewById(R.id.aTxt2);
        TextView aTxt3 = (TextView) findViewById(R.id.aTxt3);
        TextView aTxt4 = (TextView) findViewById(R.id.aTxt4);
        TextView aTxt5 = (TextView) findViewById(R.id.aTxt5);

        TextView caTxt1 = (TextView) findViewById(R.id.caTxt1);
        TextView caTxt2 = (TextView) findViewById(R.id.caTxt2);
        TextView caTxt3 = (TextView) findViewById(R.id.caTxt3);
        TextView caTxt4 = (TextView) findViewById(R.id.caTxt4);
        TextView caTxt5 = (TextView) findViewById(R.id.caTxt5);

        TextView tv1 = (TextView) findViewById(R.id.tv1);
        TextView tv2 = (TextView) findViewById(R.id.tv2);
        TextView tv3 = (TextView) findViewById(R.id.tv3);

        Typeface myTypeFace = Typeface.createFromAsset(getAssets(), "Fonts/gustanmedium.ttf");
        employeeTxt.setTypeface(myTypeFace);
        levelTxt.setTypeface(myTypeFace);
        q1Txt.setTypeface(myTypeFace);
        q2Txt.setTypeface(myTypeFace);
        q3Txt.setTypeface(myTypeFace);
        q4Txt.setTypeface(myTypeFace);
        q5Txt.setTypeface(myTypeFace);

        a1Txt.setTypeface(myTypeFace);
        a2Txt.setTypeface(myTypeFace);
        a3Txt.setTypeface(myTypeFace);
        a4Txt.setTypeface(myTypeFace);
        a5Txt.setTypeface(myTypeFace);

        ca1Txt.setTypeface(myTypeFace);
        ca2Txt.setTypeface(myTypeFace);
        ca3Txt.setTypeface(myTypeFace);
        ca4Txt.setTypeface(myTypeFace);
        ca5Txt.setTypeface(myTypeFace);

        qTxt1.setTypeface(myTypeFace);
        qTxt2.setTypeface(myTypeFace);
        qTxt3.setTypeface(myTypeFace);
        qTxt4.setTypeface(myTypeFace);
        qTxt5.setTypeface(myTypeFace);

        aTxt1.setTypeface(myTypeFace);
        aTxt2.setTypeface(myTypeFace);
        aTxt3.setTypeface(myTypeFace);
        aTxt4.setTypeface(myTypeFace);
        aTxt5.setTypeface(myTypeFace);

        caTxt1.setTypeface(myTypeFace);
        caTxt2.setTypeface(myTypeFace);
        caTxt3.setTypeface(myTypeFace);
        caTxt4.setTypeface(myTypeFace);
        caTxt5.setTypeface(myTypeFace);

        tv1.setTypeface(myTypeFace);
        tv2.setTypeface(myTypeFace);
        tv3.setTypeface(myTypeFace);

        q1Ans = (LinearLayout) findViewById(R.id.q1Ans);
        q2Ans = (LinearLayout) findViewById(R.id.q2Ans);
        q3Ans = (LinearLayout) findViewById(R.id.q3Ans);
        q4Ans = (LinearLayout) findViewById(R.id.q4Ans);
        q5Ans = (LinearLayout) findViewById(R.id.q5Ans);

    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish();
            Intent b = new Intent(getApplicationContext(), Module.class);
            b.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(b);
        }
        else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }

}
