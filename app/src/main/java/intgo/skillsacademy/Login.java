package intgo.skillsacademy;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


public class Login extends AppCompatActivity {
    private boolean exit = false;
    Button bLogin;
    EditText etPersonnelNumber, etPassword;
    TextView Sill, Login, t3, t4, pass;

    LocalUser userLocalStore;
    // SharedPreferences userLocalDatabase;

    String Number, Password;
    String admin_user, admin_password;
    String emp_number, emp_password, userType, emp_course, emp_level, emp_location, emp_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        testHiddenFile();
        initializeComponents();

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserLogin();
            }
        });
    }

    private void testHiddenFile() {
        try {
            File f = new File("/mnt/sdcard/.SKA");
            f.mkdir();
            f = new File("/mnt/sdcard/.SKA/Reports");
            f.mkdir();
            f = new File(f, "test.pdf");
            String path = f.toString();

            Document document = new Document(PageSize.A4);


            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));


            document.open();
            Paragraph header = new Paragraph("hello");
            document.add(header);
            document.close();

        }catch (Exception e) {
                e.printStackTrace();
            }
    }

    private void checkUserLogin() {
        initUserVariables();
        loadUserDetails();
        validateLogin();
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

    private void validateLogin() {
        if (Number.length() == 0 || Password.length() == 0) {
            if (Number.length() == 0) {
                etPersonnelNumber.requestFocus();
                etPersonnelNumber.setError("FIELD CANNOT BE EMPTY");
            }
            if (Password.length() == 0) {
                etPassword.requestFocus();
                etPassword.setError("FIELD CANNOT BE EMPTY");
            }
        } else if (Password.length() < 8) {
            etPassword.requestFocus();
            etPassword.setError("MIN LENGTH 8");

        } else if (Number.equals(emp_number)) {
            if (Password.equals(emp_password)) {
                if (userType.equals("Admin")) {
                    finish();
                    Intent b = new Intent(getApplicationContext(), RegisterUser.class);
                    b.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(b);
                } else if (userType.equals("Student")) {
                    userLocalStore = new LocalUser(getApplicationContext());
                    User user = new User(emp_number, emp_password, emp_level, emp_location, emp_course, userType, emp_email);
                    userLocalStore.storeUserData(user);
                    if (emp_level.equals("Induction")) {
                        finish();
                        Intent b = new Intent(getApplicationContext(), MainMenu.class);
                        b.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(b);
                    } else {
                        finish();
                        Intent b = new Intent(getApplicationContext(), Module.class);
                        b.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(b);
                    }
                }
            } else {
                etPassword.requestFocus();
                etPassword.setText("");
                etPassword.setError("PASSWORD  IS INCORRECT");
            }
        } else {
            etPersonnelNumber.requestFocus();
            etPersonnelNumber.setText("");
            etPersonnelNumber.setError("PERSONNEL NUMBER IS INCORRECT");
        }
    }

    private void initializeComponents() {

        admin_user = "Admin";
        admin_password = "1234";

        Typeface myTypeFace = Typeface.createFromAsset(getAssets(), "Fonts/gustanmedium.ttf");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        bLogin = (Button) findViewById(R.id.bLogin);

        etPersonnelNumber = (EditText) findViewById(R.id.etPNumber);
        etPassword = (EditText) findViewById(R.id.etPassword);


        Sill = (TextView) findViewById(R.id.tvSill);
        Login = (TextView) findViewById(R.id.tvLogin);
        t3 = (TextView) findViewById(R.id.tv3);
        t4 = (TextView) findViewById(R.id.tv4);
        pass = (TextView) findViewById(R.id.tv5);

        bLogin.setTypeface(myTypeFace);
        Sill.setTypeface(myTypeFace);
        Login.setTypeface(myTypeFace);
        t3.setTypeface(myTypeFace);
        t4.setTypeface(myTypeFace);
        pass.setTypeface(myTypeFace);

        etPassword.setTypeface(myTypeFace);
        etPersonnelNumber.setTypeface(myTypeFace);

        etPersonnelNumber.setHint("Enter Employee Number");
        etPassword.setHint("Enter Password");

        etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }


    private void initUserVariables() {
        Number = etPersonnelNumber.getText().toString();
        Password = etPassword.getText().toString();
        Password = md5(Password);
        System.err.println("Password:   " + Password);
    }

    private void loadUserDetails() {

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        List<User> users = db.getAllUserData(etPersonnelNumber.getText().toString());
        for (User res : users) {

            emp_number = res.getNumber();
            emp_password = res.getPassword();
            emp_level = res.getLevel();
            emp_location = res.getLocation();
            emp_email = res.getEmail();
            emp_course = res.getCourse();
            userType = res.getUser();

        }
    }


    @Override
    public void onBackPressed() {
        if (exit)
            finish();
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
