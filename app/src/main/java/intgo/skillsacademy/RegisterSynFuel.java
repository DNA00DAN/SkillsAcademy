package intgo.skillsacademy;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class RegisterSynFuel extends AppCompatActivity {
    private boolean exit = false;

    Button Reg;
    Spinner supervisor;
    EditText EDid, EDname, EDsurname, EDtitle, EDdiscipline, EDdepartment;

    List<String> supervisors, allStudents;
    String emailT = "", trainer = "", name = "", surname = "", id = "", title = "", discipline = "", department = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user_syn_fuels);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initializeComponents();
        populateSpinners();

        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popVariables();

            }

        });

        supervisor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //Performing action onItemSelected and onNothing selected
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                trainer = supervisors.get(position).toString();
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                emailT = db.getSynSuperEmail(trainer);
                System.err.println("email:  " + emailT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        }); // (optional)

    }

    private void saveStudent() {
        DatabaseHandler db = new DatabaseHandler(this);
        db.AddSynFuel(new SynUser(id, name, surname, title, discipline, department, 0));
        System.err.println("Syn Fuel user saved");
        allStudents = db.getAllSynUsers();
    }

    private void sendRegEmail() {
        Intent email = new Intent(Intent.ACTION_SEND);
        // This email becomes Rian's email
        //email.putExtra(Intent.EXTRA_EMAIL, new String[]{emailT});
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"rian.ucs@netactive.co.za"});
        //email.putExtra(Intent.EXTRA_CC, new String[]{ to});
        //email.putExtra(Intent.EXTRA_BCC, new String[]{to});
        email.putExtra(Intent.EXTRA_SUBJECT, "Student Count");
        email.putExtra(Intent.EXTRA_TEXT, "Total Registered Students on this tablet:    "+allStudents.size());

        email.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        email.setType("application/pdf");

        //need this to prompts email client only
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Select Gmail then Press the White Arrow in the Top Right Corner"));
    }

    private void initializeComponents() {
        Reg = (Button) findViewById(R.id.bRegister);
        supervisor = (Spinner) findViewById(R.id.supervisorSp);

        EDid = (EditText) findViewById(R.id.etNewNumber);
        EDname = (EditText) findViewById(R.id.etName);
        EDsurname = (EditText) findViewById(R.id.etSurname);
        EDtitle = (EditText) findViewById(R.id.etJobTitle);
        EDdiscipline = (EditText) findViewById(R.id.etDicipline);
        EDdepartment = (EditText) findViewById(R.id.etDepartment);

        EDid.setSingleLine();
        EDname.setSingleLine();
        EDsurname.setSingleLine();
        EDtitle.setSingleLine();
        EDdiscipline.setSingleLine();
        EDdepartment.setSingleLine();
    }

    private void popVariables() {
        if(EDid.getText().toString().trim().length() > 0 ||
                EDname.getText().toString().trim().length() > 0 ||
                EDsurname.getText().toString().trim().length() > 0){

            id = EDid.getText().toString();
            name = EDname.getText().toString();
            surname = EDsurname.getText().toString();

            if (!EDtitle.getText().toString().equals("")) {
                title = EDtitle.getText().toString();
            } else {
                title = "NA";
            }
            if (!EDdiscipline.getText().toString().equals("")) {
                discipline = EDdiscipline.getText().toString();
            } else {
                discipline = "NA";
            }
            if (!EDdepartment.getText().toString().equals("")) {
                department = EDdepartment.getText().toString();
            } else {
                department = "NA";
            }

            saveStudent();

            Intent i = new Intent(getApplicationContext(), SynFuelTest.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            i.putExtra("id", id);
            i.putExtra("name", name);
            i.putExtra("surname", surname);
            i.putExtra("title", title);
            i.putExtra("discipline", discipline);
            i.putExtra("department", department);
            i.putExtra("attempt", ""+0);
            i.putExtra("email", emailT);

            startActivity(i);
            finish();
            sendRegEmail();
        }
        else {
            if (EDid.getText().toString().length() < 1) {
                EDid.requestFocus();
                EDid.setError("FIELD CANNOT BE EMPTY");
            }
            if (EDname.getText().toString().length() < 1) {
                EDname.requestFocus();
                EDname.setError("FIELD CANNOT BE EMPTY");
            }
            if (EDsurname.getText().toString().length() < 1) {
                EDsurname.requestFocus();
                EDsurname.setError("FIELD CANNOT BE EMPTY");
            }
        }
    }

    private void populateSpinners() {
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        supervisors = db.getAllSynSupervisors();
        Typeface myTypeFace = Typeface.createFromAsset(getAssets(), "Fonts/gustanmedium.ttf");
        CustomResultAdapter customAdapter = new CustomResultAdapter(getApplicationContext(), supervisors, myTypeFace);
        supervisor.setAdapter(customAdapter);
    }


    @Override
    public void onBackPressed() {
        if (exit) {
            finish();
            Intent b = new Intent(getApplicationContext(), Home.class);
            b.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(b);
        } else {
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
