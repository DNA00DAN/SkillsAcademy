package intgo.skillsacademy;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class MainMenu extends AppCompatActivity {

    private boolean exit = false;
    TextView tv1, tv2;
    Button btnGuide, btnTest;
    EditText txtUsername, txtPassword;
    String Number, Password;
    String emp_number, emp_password, userType, emp_course, emp_level, emp_location, emp_email;
    LocalSupervisor supervisorLocalStore;
    TextView txtUser, txtLevel, txtCourse;
    TextView tUser, tLevel, tCourse;
    LocalUser userLocalBase;
    CoordinatorLayout coordinatorLayout;
    String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"};

    int permsRequestCode = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        initializeComponents();
        LoadActionBar();


        userLocalBase = new LocalUser(getApplicationContext());
        txtUser.setText(userLocalBase.getNumber());
        txtLevel.setText(userLocalBase.getLevel());
        txtCourse.setText(userLocalBase.getCourse());

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        btnGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadManual();
            }
        });

        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Welcome " + userLocalBase.getNumber(), Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });
        snackbar.show();

    }


    public void LoadActionBar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);

        View view = getSupportActionBar().getCustomView();

        ImageButton imageButton = (ImageButton) view.findViewById(R.id.action_bar_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent b = new Intent(getApplicationContext(), UserManagement.class);
                b.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(b);
            }
        });
    }

    private void loadManual() {
        String filename = "";
        if (userLocalBase.getLevel().equals("Induction")) {
            if (userLocalBase.getLocation().equals("BrandSpruit")) {
                filename = "Induction Learning Guide Brandspruit 2017.Rev1.pdf";
            } else if (userLocalBase.getLocation().equals("Secunda")) {
                filename = "Induction Learning Guide Secunda 2017.Rev1.pdf";
            } else {
                filename = "Induction Learning Guide Sasolburg 2017.Rev1.pdf";
            }
        }

        if (shouldAskPermission()) {
            if (hasPermission(perms[0]) && hasPermission(perms[1])) {

            } else {
                if (!hasPermission(perms[0]) && !hasPermission(perms[1])) {
                    requestPermissions(perms, permsRequestCode);
                }
            }
        }

        File tmpPa = new File(Environment
                .getExternalStorageDirectory()
                + "/SKA/Guides/");
        File tmpPa1 = new File(Environment
                .getExternalStorageDirectory()
                + "/SKA/");

        if (!tmpPa1.exists()) {
            tmpPa1.mkdir();
        }
        if (!tmpPa.exists()) {
            tmpPa.mkdir();
        }

        System.out.println("Folders created");

        // copy images from assets to sdcard
        AppUtils.AssetFileCopy(this, tmpPa + "/" + filename, filename, false);

        File file = new File(tmpPa + "/" + filename);
        Log.d("Path 2:", file.toString());

        openManual(file);

    }

    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {

        switch (permsRequestCode) {
            case 1:
                boolean writeAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                break;
        }

    }

    private boolean hasPermission(String permission) {

        if (canMakeSnores()) {
            return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }

    private boolean canMakeSnores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    private boolean shouldAskPermission() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
    }

    private void openManual(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }


    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    private void Login() {
        // Create Object of Dialog class
        final Dialog login = new Dialog(this);
        // Set GUI of Login screen
        login.setContentView(R.layout.activity_login_dialog);
        login.setTitle(Html.fromHtml("<h2><font color='#033b61'>Supervisor Login</font></h2>"));

        // Init Components of Login GUI
        Button btnLogin = (Button) login.findViewById(R.id.btnLogin);
        Button btnCancel = (Button) login.findViewById(R.id.btnCancel);
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
        // Attached listener for Login GUI button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeSupervisor();
                supervisorLocalStore = new LocalSupervisor(getApplicationContext());
                if (Number.length() == 0 || Password.length() == 0) {
                    if (Number.length() == 0) {
                        txtUsername.requestFocus();
                        txtUsername.setError("FIELD CANNOT BE EMPTY");
                    }
                    if (Password.length() == 0) {
                        txtPassword.requestFocus();
                        txtPassword.setError("FIELD CANNOT BE EMPTY");
                    }
                } else if (Password.length() < 8) {
                    txtPassword.requestFocus();
                    txtPassword.setError("MIN LENGTH 8");

                } else if (Number.equals(supervisorLocalStore.getNumber())) {
                    if (Password.equals(supervisorLocalStore.getPassword()) && userType.equals("Supervisor")) {
                        Supervisor Suser = new Supervisor(emp_number, emp_password, -1, emp_email, emp_location, emp_course);
                        supervisorLocalStore.storeSupervisorData(Suser);
                        login.dismiss();
                        finish();
                        Intent i = new Intent(getApplicationContext(), InductionTest.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

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

    private void initializeSupervisor() {
        Number = txtUsername.getText().toString().trim();
        Password = txtPassword.getText().toString().trim();
        Password = md5(Password);
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        List<User> users = db.getAllUserData(Number);
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


    private void initializeComponents() {
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        txtUser = (TextView) findViewById(R.id.txtUser);
        txtLevel = (TextView) findViewById(R.id.txtLevel);
        txtCourse = (TextView) findViewById(R.id.txtCourse);
        tUser = (TextView) findViewById(R.id.tUser);
        tLevel = (TextView) findViewById(R.id.tLevel);
        tCourse = (TextView) findViewById(R.id.tCourse);
        btnGuide = (Button) findViewById(R.id.btnManual);
        btnTest = (Button) findViewById(R.id.btnTest);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.snackbarPosition);

        Typeface myTypeFace = Typeface.createFromAsset(getAssets(), "Fonts/gustanmedium.ttf");
        tv1.setTypeface(myTypeFace);
        tv2.setTypeface(myTypeFace);
        btnGuide.setTypeface(myTypeFace);
        btnTest.setTypeface(myTypeFace);

        txtUser.setTypeface(myTypeFace);
        txtCourse.setTypeface(myTypeFace);
        txtLevel.setTypeface(myTypeFace);
        tUser.setTypeface(myTypeFace);
        tLevel.setTypeface(myTypeFace);
        tCourse.setTypeface(myTypeFace);

    }

    @Override
    public void onBackPressed() {
        if (exit) {
            removeGuides();
            finish();
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

    private void removeGuides() {
        File dir = new File("/mnt/sdcard/SKA/Guides");
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                new File(dir, children[i]).delete();
            }
        }
    }
}
