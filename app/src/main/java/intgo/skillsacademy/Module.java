package intgo.skillsacademy;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Module extends AppCompatActivity {
    Button ibLevel2, ibMech, ibFit, ibElec, ibLevel3, ibLevel4, induction;
    private boolean exit = false;
    TextView tv1, tv2;
    String level, course;
    LocalUser userLocalStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //LoadActionBar();
        intializeComponents();

        induction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!level.equals("Induction")) {
                    hideCourse();
                    Toast.makeText(getApplicationContext(), "You have already completed this module", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                    Intent i = new Intent(getApplicationContext(), MainMenu.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            }
        });

        ibLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (level.equals("Level 3") || level.equals("Level 4")) {
                    hideCourse();
                    Toast.makeText(getApplicationContext(), "You have already completed this module", Toast.LENGTH_SHORT).show();
                } else if (level.equals("Induction")) {
                    hideCourse();
                    Toast.makeText(getApplicationContext(), "You do not qualify for Level 2", Toast.LENGTH_SHORT).show();
                } else {
                    int level2Pressed = ibMech.getVisibility();
                    if (level2Pressed == 4) {
                        viewCourse();
                    } else {
                        hideCourse();
                    }
                }
            }
        });

        ibLevel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (level.equals("Level 4")) {
                    hideCourse();
                    Toast.makeText(getApplicationContext(), "You have already completed this module", Toast.LENGTH_SHORT).show();
                } else if (level.equals("Induction") || level.equals("Level 2")) {
                    hideCourse();
                    Toast.makeText(getApplicationContext(), "You do not qualify for Level 3", Toast.LENGTH_SHORT).show();
                } else {
                    int level3Pressed = ibElec.getVisibility();
                    //Toast.makeText(getApplicationContext(),""+level3Pressed,Toast.LENGTH_SHORT).show();
                    if (level3Pressed == 4) {
                        viewCourse();
                    } else {
                        hideCourse();
                    }
                }
            }
        });

        ibLevel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!level.equals("Level 4")) {
                    hideCourse();
                    Toast.makeText(getApplicationContext(), "You do not qualify for Level 4", Toast.LENGTH_SHORT).show();
                } else {
                    int level4Pressed = ibFit.getVisibility();
                    if (level4Pressed == 4) {
                        viewCourse();
                    } else {
                        hideCourse();
                    }
                }
            }
        });

        ibMech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (course.equals("Mechanical")) {

                } else {
                    Toast.makeText(getApplicationContext(), "Course not allowed", Toast.LENGTH_SHORT).show();
                }
                //finish();
                //Intent i = new Intent(getApplicationContext(), Mechanical.class);
                //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(i);
            }
        });

        ibElec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (course.equals("Electrical")) {
                    finish();
                    Intent i = new Intent(getApplicationContext(), MainMenu.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Course not allowed", Toast.LENGTH_SHORT).show();
                }
                //finish();
                //Intent i = new Intent(getApplicationContext(), Mechanical.class);
                //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(i);
            }
        });

        ibFit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (course.equals("Fitting")) {

                } else {
                    Toast.makeText(getApplicationContext(), "Course not allowed", Toast.LENGTH_SHORT).show();
                }
                //finish();
                //Intent i = new Intent(getApplicationContext(), Mechanical.class);
                //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(i);
            }
        });
    }

    private void viewCourse() {
        ibMech.setVisibility(View.VISIBLE);
        ibElec.setVisibility(View.VISIBLE);
        ibFit.setVisibility(View.VISIBLE);
    }

    private void hideCourse() {
        ibMech.setVisibility(View.INVISIBLE);
        ibElec.setVisibility(View.INVISIBLE);
        ibFit.setVisibility(View.INVISIBLE);
    }

    private void intializeComponents() {
        ibLevel2 = (Button) findViewById(R.id.ibLevel2);
        ibLevel3 = (Button) findViewById(R.id.ibLevel3);
        ibLevel4 = (Button) findViewById(R.id.ibLevel4);
        ibMech = (Button) findViewById(R.id.ibMechanical);
        ibElec = (Button) findViewById(R.id.ibElectrical);
        ibFit = (Button) findViewById(R.id.ibFitting);
        induction = (Button) findViewById(R.id.inductionBtn);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);

        Typeface myTypeFace = Typeface.createFromAsset(getAssets(), "Fonts/gustanmedium.ttf");
        tv1.setTypeface(myTypeFace);
        tv2.setTypeface(myTypeFace);
        induction.setTypeface(myTypeFace);
        ibLevel2.setTypeface(myTypeFace);
        ibLevel3.setTypeface(myTypeFace);
        ibLevel4.setTypeface(myTypeFace);
        ibElec.setTypeface(myTypeFace);
        ibMech.setTypeface(myTypeFace);
        ibFit.setTypeface(myTypeFace);

        hideCourse();

        userLocalStore = new LocalUser(getApplicationContext());
        level = userLocalStore.getLevel();
        course = userLocalStore.getCourse();

        ibLevel2.setEnabled(false);
        ibLevel3.setEnabled(false);
        ibLevel4.setEnabled(false);

        ibElec.setEnabled(false);
        ibMech.setEnabled(false);
        ibFit.setEnabled(false);
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

    @Override
    public void onBackPressed() {
        if (exit) {
            finish();
            Intent b = new Intent(getApplicationContext(), Login.class);
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
