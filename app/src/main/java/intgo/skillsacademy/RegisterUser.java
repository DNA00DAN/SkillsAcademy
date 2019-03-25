package intgo.skillsacademy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class RegisterUser extends AppCompatActivity {
    private boolean exit = false;

    EditText newNumber, Pass, Confirm;
    Button Reg;
    String Password1, Password2, SasolNum;
    LocalUser userLocalStore;
    LocalSupervisor supervisorLocalStore;
    RadioGroup radio;
    RadioButton supervisorRadio, userRadio;
    LinearLayout studentLayout;
    Spinner studentLocation, studentCourse;
    List<String> locations, courses;
    String location = "", course = "";

    TextView textEmail;
    EditText etEmail;
    String email = "", userType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initializeComponents();
        populateSpinners();

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.studentRadio:
                        studentLayout.setVisibility(View.VISIBLE);
                        userType = "Student";
                        break;
                    case R.id.supervisorRadio:
                        studentLayout.setVisibility(View.VISIBLE);
                        userType = "Supervisor";
                        break;
                }
            }
        });

        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userRadio.isChecked() || supervisorRadio.isChecked()) {
                    initUserVariables();
                    validateStudent();
                } else {
                    Toast.makeText(getApplicationContext(), "Select User type", Toast.LENGTH_SHORT).show();
                }


            }

        });

        studentLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //Performing action onItemSelected and onNothing selected
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                location = locations.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        }); // (optional)

        studentCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //Performing action onItemSelected and onNothing selected
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                course = courses.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        }); // (optional)


    }

    private void populateSpinners() {
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        locations = db.getAllLocations();
        courses = db.getAllCourses();
        Typeface myTypeFace = Typeface.createFromAsset(getAssets(), "Fonts/gustanmedium.ttf");
        CustomResultAdapter customAdapter = new CustomResultAdapter(getApplicationContext(), locations, myTypeFace);
        CustomResultAdapter customAdapter2 = new CustomResultAdapter(getApplicationContext(), courses, myTypeFace);

        studentLocation.setAdapter(customAdapter);
        studentCourse.setAdapter(customAdapter2);
    }

    private void validateStudent() {
        if (Password1.length() == 0 || Password2.length() == 0 || SasolNum.length() == 0) {
            if (SasolNum.length() == 0) {
                newNumber.requestFocus();
                newNumber.setError("FIELD CANNOT BE EMPTY");
            }
            if (Password1.length() == 0) {
                Pass.requestFocus();
                Pass.setError("FIELD CANNOT BE EMPTY");
            }
            if (Password2.length() == 0) {
                Confirm.requestFocus();
                Confirm.setError("FIELD CANNOT BE EMPTY");
            }
        }
        else
        if (Password1.length() < 7 || Password2.length() < 7) {
            if (Password1.length() < 7) {
                Pass.requestFocus();
                Pass.setError("MINIMUM 8 CHAR PASSWORD");
            }
            if (Password2.length() < 7) {
                Confirm.requestFocus();
                Confirm.setError("MINIMUM 8 CHAR PASSWORD");
            }
        }
        else if (!(Password1.equals(Password2))) {
            Pass.requestFocus();
            Pass.setText("");
            Confirm.setText("");
            Pass.setError("PASSWORDS DO NOT MATCH");
        } else if (email.length() < 5 || !email.contains("@") || !email.contains(".")) {
            etEmail.requestFocus();
            etEmail.setError("INVALID EMAIL");
        } else if (Password1.equals(Password2)) {
            saveStudent();
        }
    }

    private void validateSupervisor() {
        if (Password1.length() == 0 || Password2.length() == 0 || SasolNum.length() == 0) {
            if (SasolNum.length() == 0) {
                newNumber.requestFocus();
                newNumber.setError("FIELD CANNOT BE EMPTY");
            }
            if (Password1.length() == 0) {
                Pass.requestFocus();
                Pass.setError("FIELD CANNOT BE EMPTY");
            }
            if (Password2.length() == 0) {
                Confirm.requestFocus();
                Confirm.setError("FIELD CANNOT BE EMPTY");
            }
        }
        else
        if (Password1.length() < 8 || Password2.length() < 8) {
            if (Password1.length() < 8) {
                Pass.requestFocus();
                Pass.setError("MIN LENGTH 8");
            }
            if (Password2.length() < 8) {
                Confirm.requestFocus();
                Confirm.setError("MIN LENGTH 8");
            }
        }
        else if (!(Password1.equals(Password2))) {
            Pass.requestFocus();
            Pass.setText("");
            Confirm.setText("");
            Pass.setError("PASSWORDS DO NOT MATCH");
        } else if (email.length() < 5 || !email.contains("@") || !email.contains(".")) {
            etEmail.requestFocus();
            etEmail.setError("INVALID EMAIL");
        } else if (Password1.equals(Password2)) {
            saveSupervisor();
        }
    }

    private void saveStudent() {
        Log.d("Location", location);
        Log.d("Course", course);
        String tmpPass = md5(Password1);
        String level = "";

        if (userType.equals("Supervisor")) {
            level = "All";
            Supervisor Suser = new Supervisor(SasolNum, tmpPass, -1, email, location, course);
            supervisorLocalStore.storeSupervisorData(Suser);
        } else {
            level = "Induction";
            User user = new User(SasolNum, tmpPass, level, location, course, userType, email);
            userLocalStore.storeUserData(user);
        }

        DatabaseHandler db = new DatabaseHandler(this);
        db.AddUser(new User(SasolNum, tmpPass, level, location, course, userType, email));
//Toast.makeText(getApplicationContext(), "User Added", Toast.LENGTH_LONG).show();
        promptAction();
    }

    private void promptAction() {
        new AlertDialog.Builder(this)
                .setMessage("Add Another User?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                Intent i = new Intent(getApplicationContext(), RegisterUser.class);
                                startActivity(i);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }
        }).show();
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

    private void saveSupervisor() {
        Log.d("Location", location);
        Log.d("Course", course);
        Supervisor user = new Supervisor(SasolNum, Password1, -1, email, location, course);
        supervisorLocalStore.storeSupervisorData(user);
        Intent i = new Intent(getApplicationContext(), Login.class);
        startActivity(i);
    }

    private void initializeComponents() {
        newNumber = (EditText) findViewById(R.id.etNewNumber);
        Pass = (EditText) findViewById(R.id.etPass);
        Confirm = (EditText) findViewById(R.id.etConfirm);
        Reg = (Button) findViewById(R.id.bRegister);
        supervisorRadio = (RadioButton) findViewById(R.id.supervisorRadio);
        userRadio = (RadioButton) findViewById(R.id.studentRadio);
        studentLayout = (LinearLayout) findViewById(R.id.locationLayout);
        studentLocation = (Spinner) findViewById(R.id.locationSp);
        studentCourse = (Spinner) findViewById(R.id.courseSp);
        radio = (RadioGroup) findViewById(R.id.radioGroup);

        textEmail = (TextView) findViewById(R.id.textEmail);
        etEmail = (EditText) findViewById(R.id.etEmail);

        studentLayout.setVisibility(View.GONE);


    }

    private void initUserVariables() {
        Password1 = Pass.getText().toString();
        Password2 = Confirm.getText().toString();
        SasolNum = newNumber.getText().toString();
        email = etEmail.getText().toString();
        userLocalStore = new LocalUser(this);
        supervisorLocalStore = new LocalSupervisor(this);
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish();
            Intent b = new Intent(getApplicationContext(), Login.class);
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
