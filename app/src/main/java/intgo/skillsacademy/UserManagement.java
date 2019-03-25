package intgo.skillsacademy;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserManagement extends AppCompatActivity {
    Button update;
    EditText emp_num, emp_pass, emp_confirm, emp_email;
    String emp_Num, emp_Pass, emp_Confirm, emp_Email, new_lev, emp_location, emp_course, userType;
    LocalUser userLocalBase;
    private boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        initializeComponents();
        getUserData();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popVariables();
                if (emp_Pass.length() < 7 || emp_Confirm.length() < 7) {
                    if (emp_Pass.length() < 7) {
                        emp_pass.requestFocus();
                        emp_pass.setError("MINIMUM 8 CHAR PASSWORD");
                    }
                    if (emp_Confirm.length() < 7) {
                        emp_confirm.requestFocus();
                        emp_confirm.setError("MINIMUM 8 CHAR PASSWORD");
                    }
                } else if (!emp_Pass.equals(emp_Confirm)) {
                    emp_pass.requestFocus();
                    emp_pass.setText("");
                    emp_confirm.setText("");
                    emp_pass.setError("PASSWORDS DO NOT MATCH");
                } else if (emp_Email.length() < 5 || !emp_Email.contains("@") || !emp_Email.contains(".")) {
                    emp_email.requestFocus();
                    emp_email.setError("INVALID EMAIL");
                } else if (emp_Pass.equals(emp_Confirm)) {
                    updateStudent();
                    finish();
                    Intent b = new Intent(getApplicationContext(), MainMenu.class);
                    b.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(b);
                }
            }

        });
    }

    private void popVariables() {
        emp_Num = emp_num.getText().toString().trim();
        emp_Email = emp_email.getText().toString().trim();

        if (emp_pass.getText().toString().trim().length() == 0
                && emp_confirm.getText().toString().trim().length() == 0) {
            emp_Pass = userLocalBase.getPassword();
            emp_Confirm = userLocalBase.getPassword();
        } else {
            emp_Pass = emp_pass.getText().toString().trim();
            emp_Confirm = emp_confirm.getText().toString().trim();
            emp_Pass = md5(emp_Pass);
            emp_Confirm = md5(emp_Confirm);
        }
        System.err.println("Password:   " + emp_Pass);
        System.err.println("Confirm:    " + emp_Confirm);
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

    private void updateStudent() {
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        db.updateUserLevel(emp_Num, new_lev);
        User NewUser = new User(emp_Num, emp_Pass, new_lev, emp_location, emp_course, userType, emp_Email);
        userLocalBase.storeUserData(NewUser);
    }

    private void getUserData() {
        userLocalBase = new LocalUser(getApplicationContext());
        emp_num.setText(userLocalBase.getNumber());
        emp_email.setText(userLocalBase.getEmail());
        new_lev = userLocalBase.getLevel();
        emp_location = userLocalBase.getLocation();
        userType = userLocalBase.getUser();
        emp_course = userLocalBase.getCourse();
    }

    private void initializeComponents() {
        update = (Button) findViewById(R.id.bUpdate);
        emp_num = (EditText) findViewById(R.id.etNewNumberU);
        emp_pass = (EditText) findViewById(R.id.etPassU);
        emp_confirm = (EditText) findViewById(R.id.etConfirmU);
        emp_email = (EditText) findViewById(R.id.etEmailU);
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish();
            Intent b = new Intent(getApplicationContext(), MainMenu.class);
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
