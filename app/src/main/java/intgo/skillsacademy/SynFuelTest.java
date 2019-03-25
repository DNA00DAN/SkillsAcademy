package intgo.skillsacademy;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SynFuelTest extends AppCompatActivity {
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    private Button submitBtn;

    private CheckBox opA, opB, opC, opD;
    private String answer;
    private String randomQuestion, status = "FAIL";
    private String emailT = "", name = "", surname = "", id = "", title = "", discipline = "", department = "";
    private int attempt = 0;
    private TextView testQuestion;
    private ArrayList<String> answers, questions, c_answers, test_questions, questionTags;
    private String correctAnswer, questionImage, testDate;
    private int questionNr = 1, score = 0, questionPos = 0;
    private boolean exit = false;
    String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"};

    int permsRequestCode = 1;

    private String questionTag;
    ImageView qImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }

        setContentView(R.layout.activity_syn_fuel_test);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initializeComponents();
        getPerm();
        resetSelection();
        initializeArrays();
        loadQuestions();
        loadAnswers();
        hideNavbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {

            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        }

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveQuestionResult();
            }
        });


        opA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!opA.isChecked()) {
                    answer = opA.getText().toString();
                    opB.setChecked(false);
                    opC.setChecked(false);
                    opD.setChecked(false);
                } else {
                    //opA.setChecked(false);
                    opB.setChecked(false);
                    opC.setChecked(false);
                    opD.setChecked(false);
                    answer = "";
                }
            }
        });

        opB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!opB.isChecked()) {
                    answer = opB.getText().toString();
                    opA.setChecked(false);
                    opC.setChecked(false);
                    opD.setChecked(false);
                } else {
                    //opB.setChecked(false);
                    opA.setChecked(false);
                    opC.setChecked(false);
                    opD.setChecked(false);
                    answer = "";
                }
            }
        });

        opC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!opC.isChecked()) {
                    answer = opC.getText().toString();
                    opA.setChecked(false);
                    opB.setChecked(false);
                    opD.setChecked(false);
                } else {
                    //opC.setChecked(false);
                    opA.setChecked(false);
                    opB.setChecked(false);
                    opD.setChecked(false);
                    answer = "";
                }
            }
        });

        opD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!opD.isChecked()) {
                    answer = opD.getText().toString();
                    opA.setChecked(false);
                    opB.setChecked(false);
                    opC.setChecked(false);
                } else {
                    //opD.setChecked(false);
                    opA.setChecked(false);
                    opB.setChecked(false);
                    opC.setChecked(false);
                    answer = "";
                }
            }
        });

    }

    private void getPerm() {
        if (shouldAskPermission()) {
            if (hasPermission(perms[0]) && hasPermission(perms[1])) {

            } else {
                if (!hasPermission(perms[0]) && !hasPermission(perms[1])) {
                    requestPermissions(perms, permsRequestCode);
                }
            }
        }
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


    private void validatePossibleAnswers() {
        if (opA.getText().toString().equals("NA")) {
            opA.setVisibility(View.GONE);
        }
        if (opB.getText().toString().equals("NA")) {
            opB.setVisibility(View.GONE);
        }
        if (opC.getText().toString().equals("NA") || opC.getText().toString().equals("Option C")) {
            opC.setVisibility(View.GONE);
        }
        if (opD.getText().toString().equals("NA") || opD.getText().toString().equals("Option D")) {
            opD.setVisibility(View.GONE);
        }
        if (!questionImage.equals("NA")) {
            try {
                qImage.setVisibility(View.VISIBLE);
                Drawable d = Drawable.createFromStream(getAssets().open("Question_Images/SynFuel/" + questionImage), null);
                qImage.setBackground(d);
            } catch (IOException e) {
                Log.w("EL", e);
            }
        } else {
            qImage.setBackground(null);
            qImage.setVisibility(View.GONE);
        }
    }

    private void saveQuestionResult() {
        String tmpQ = testQuestion.getText().toString();
        getAnswer();
        String tmpA = answer;

        if (questionNr <= 5) {
            if (answer.equals("")) {
                Toast.makeText(SynFuelTest.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                return;
            } else {
                questionTags.add(questionTag);
                answers.add(tmpA);
                test_questions.add(tmpQ);
                c_answers.add(correctAnswer);
                saveResult();
                questionNr++;
                questionPos++;
                Log.d("answer", answer);
                Log.d("question", testQuestion.getText().toString());
                resetSelection();
                if (questions.size() > 0) {
                    randomizeQuestion();
                    loadAnswers();
                }
            }
        }
    }

    private void getAnswer() {
        if (opA.isChecked()) {
            answer = opA.getText().toString();
        } else if (opB.isChecked()) {
            answer = opB.getText().toString();
        } else if (opC.isChecked()) {
            answer = opC.getText().toString();
        } else if (opD.isChecked()) {
            answer = opD.getText().toString();
        }
    }

    private void saveResult() {
        if (questionNr == 5) {
            calculateScore();
            savePDFReport();
            updateUserDetails();
            Toast.makeText(SynFuelTest.this, "Test has been completed", Toast.LENGTH_LONG).show();
            questionNr = 1;
            questionPos = 0;
            initializeArrays();
            resetSelection();
            loadReport();
        }
    }

    private void calculateScore() {
        score = 0;
        for (int i = 0; i < c_answers.size(); i++) {
            if (answers.get(i).toString().equals(c_answers.get(i).toString())) {
                score = score + 20;
            }
        }
        if (score >= 80) {
            status = "PASS";
            attempt++;
        } else {
            status = "FAIL";
            attempt++;
        }
        System.err.println("Status: " + status);
        System.err.println("Score: " + score);
        System.err.println("Attempt: " + attempt);
    }

    private void updateUserDetails() {
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        saveToDB(id, "" + attempt);
        db.updateUserAttempt(id, "" + attempt);
    }

    private void saveToDB(String id, String attempt) {
        DatabaseHandler db = new DatabaseHandler(this);
        db.AddSynfuelTestResult(new SynfuelResult(testDate, id, attempt,

                test_questions.get(0),
                test_questions.get(1),
                test_questions.get(2),
                test_questions.get(3),
                test_questions.get(4),

                answers.get(0),
                answers.get(1),
                answers.get(2),
                answers.get(3),
                answers.get(4),

                c_answers.get(0),
                c_answers.get(1),
                c_answers.get(2),
                c_answers.get(3),
                c_answers.get(4)
        ));
    }

    private void savePDFReport() {

        try {

            File fol = new File(Environment
                    .getExternalStorageDirectory()
                    + "/SYN/");
            fol.mkdirs();

            File fol2 = new File(Environment
                    .getExternalStorageDirectory()
                    + "/SYN/Reports/");
            fol2.mkdirs();


            File pdfFile = new File(Environment
                    .getExternalStorageDirectory()
                    + "/SYN/Reports/" + testDate + "-" + id + "-" + attempt + ".pdf");

            Document document = new Document(PageSize.A4);

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFile.toString()));

            document.open();


            Paragraph header = new Paragraph(getHeaderFont("Sasol Synthetic Fuels", 40));
            document.add(header);


            Bitmap bmp2 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.sasollogo);
            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
            bmp2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
            Image image2 = Image.getInstance(stream2.toByteArray());
            image2.setAbsolutePosition(475, 715);
            image2.setSpacingAfter(15);
            document.add(image2);

            document.add(new Paragraph(getSubHeaderFont("Date Tested: " + testDate, 30)));

            PdfPTable table = new PdfPTable(2);
            table.setWidths(new int[]{1, 2});
            table.setSpacingBefore(20);


            table.addCell(getNormalCell("Employee number/ID:", 20));
            table.addCell(getNormalCell(id, 20));
            table.addCell(getNormalCell("Surname:", 20));
            table.addCell(getNormalCell(surname, 20));
            table.addCell(getNormalCell("Attempt(s):", 20));
            table.addCell(getNormalCell("" + attempt, 20));

            int q = 1;
            for (int po = 0; po < 5; po++) {
                table.addCell(getNormalCell("Question " + q + ":", 16));
                table.addCell(getNormalCell(questionTags.get(po), 14));
                questionImage = "NA";
                loadQImage(test_questions.get(po));

                if (!questionImage.equals("NA")) {
                    InputStream ims = getAssets().open("Question_Images/Synfuels/" + questionImage);
                    Bitmap bmp = BitmapFactory.decodeStream(ims);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    Image image = Image.getInstance(stream.toByteArray());
                    image.setSpacingBefore(5);
                    image.scalePercent(30, 45);
                    table.addCell(getCellImage(image));
                }

                table.addCell(getNormalCell(questionTags.get(po) + ":", 14));
                table.addCell(getAnswerCell(answers.get(po), po));
                q++;
            }
            table.addCell(getNormalCell("Evaluation:", 16));
            table.addCell(getFinalCell("" + score + " % (" + status + ")", 14));
            table.setTotalWidth(PageSize.A4.getWidth() - 50);
            table.setLockedWidth(true);
            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Paragraph getHeaderFont(String string, float size)
            throws DocumentException, IOException {
        if (string != null && "".equals(string)) {
            return new Paragraph();
        }
        // = getAssets().open("Fonts/gustanmedium.ttf");
        //Font bmp = FontFactory.register(ims,"gus");
        Font f = FontFactory.getFont(getAssets().open("Fonts/gustanmedium.ttf").toString());
        f.setColor(new BaseColor(0, 56, 98));
        f.setSize(size);
        Paragraph cell = new Paragraph(new Phrase(string, f));
        return cell;
    }

    public Paragraph getSubHeaderFont(String string, float size)
            throws DocumentException, IOException {
        if (string != null && "".equals(string)) {
            return new Paragraph();
        }
        // = getAssets().open("Fonts/gustanmedium.ttf");
        //Font bmp = FontFactory.register(ims,"gus");
        Font f = FontFactory.getFont(getAssets().open("Fonts/gustanmedium.ttf").toString());
        f.setColor(new BaseColor(195, 212, 221));
        f.setSize(size);
        Paragraph cell = new Paragraph(new Phrase(string, f));
        return cell;
    }

    public PdfPCell getNormalCell(String string, float size)
            throws DocumentException, IOException {
        if (string != null && "".equals(string)) {
            return new PdfPCell();
        }
        // = getAssets().open("Fonts/gustanmedium.ttf");
        //Font bmp = FontFactory.register(ims,"gus");
        Font f = FontFactory.getFont(getAssets().open("Fonts/gustanmedium.ttf").toString());
        if (size < 0) {
            size = -size;
        }
        f.setSize(size);
        PdfPCell cell = new PdfPCell(new Phrase(string, f));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);

        return cell;
    }

    public PdfPCell getFinalCell(String string, float size)
            throws DocumentException, IOException {
        if (string != null && "".equals(string)) {
            return new PdfPCell();
        }
        // = getAssets().open("Fonts/gustanmedium.ttf");
        //Font bmp = FontFactory.register(ims,"gus");
        Font f = FontFactory.getFont(getAssets().open("Fonts/gustanmedium.ttf").toString());
        if (size < 0) {
            f.setColor(BaseColor.RED);
            size = -size;
        }
        f.setSize(size);
        BaseColor col;
        if (string.contains("PASS")) {
            f.setColor(BaseColor.WHITE);
            col = new BaseColor(0, 56, 98);
        } else {
            f.setColor(new BaseColor(0, 56, 98));
            col = BaseColor.RED;
        }
        PdfPCell cell = new PdfPCell(new Phrase(string, f));
        cell.setBackgroundColor(col);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);

        return cell;
    }

    public PdfPCell getAnswerCell(String string, int po)
            throws DocumentException, IOException {
        if (string != null && "".equals(string)) {
            return new PdfPCell();
        }

        Font f = FontFactory.getFont(getAssets().open("Fonts/gustanmedium.ttf").toString());
        BaseColor col;
        if (answers.get(po).equals(c_answers.get(po))) {
            f.setColor(BaseColor.WHITE);
            col = new BaseColor(0, 56, 98);
        } else {
            f.setColor(new BaseColor(0, 56, 98));
            col = BaseColor.RED;
        }
        f.setSize(14);
        PdfPCell cell = new PdfPCell(new Phrase(string, f));
        cell.setBackgroundColor(col);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        return cell;
    }

    public PdfPCell getCellImage(Image image)
            throws DocumentException, IOException {
        PdfPCell cell = new PdfPCell(image);
        cell.setColspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingBottom(5);
        return cell;
    }

    private void loadReport() {
        finish();
        Intent ii = new Intent(getApplicationContext(), SynFuelReport.class);
        ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ii.putExtra("status", status);
        ii.putExtra("score", "" + score);
        ii.putExtra("id", id);
        ii.putExtra("name", name);
        ii.putExtra("surname", surname);
        ii.putExtra("title", title);
        ii.putExtra("discipline", discipline);
        ii.putExtra("department", department);
        ii.putExtra("attempt", "" + attempt);
        ii.putExtra("email", emailT);
        startActivity(ii);
    }

    private void loadAnswers() {
        opA.setVisibility(View.VISIBLE);
        opB.setVisibility(View.VISIBLE);
        opC.setVisibility(View.VISIBLE);
        opD.setVisibility(View.VISIBLE);

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        List<SynFuelQuestion> questions = db.getAllSynFuelQuestionData(testQuestion.getText().toString());

        for (SynFuelQuestion res : questions) {

            opA.setText(res.getAnswer1());
            opB.setText(res.getAnswer2());
            questionTag = res.getQuestionTag();
            correctAnswer = res.getCorrectAnswer();
            questionImage = res.getQuestionImage();

        }
        validatePossibleAnswers();
    }

    private void loadQImage(String que) {

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        List<Question> questions = db.getAllBSInductionQuestionData(que);
        for (Question res : questions) {
            questionImage = res.getQuestionImage();
        }
    }

    private void loadQuestions() {
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        questions = db.getAllSynFuelQuestions();
        randomQuestion = questions.get(questionPos);
        testQuestion.setText(randomQuestion);
    }

    private void randomizeQuestion() {
        randomQuestion = questions.get(questionPos);
        testQuestion.setText(randomQuestion);
    }

    private void initializeArrays() {
        answers = new ArrayList<String>();
        c_answers = new ArrayList<String>();
        questions = new ArrayList<String>();
        test_questions = new ArrayList<String>();
        questionTags = new ArrayList<String>();
    }

    private void resetSelection() {
        opA.setChecked(false);
        opB.setChecked(false);
        opC.setChecked(false);
        opD.setChecked(false);
        answer = "";
    }

    private void initializeComponents() {
        submitBtn = (Button) findViewById(R.id.submitBtn);

        opA = (CheckBox) findViewById(R.id.selectionA);
        opB = (CheckBox) findViewById(R.id.selectionB);
        opC = (CheckBox) findViewById(R.id.selectionC);
        opD = (CheckBox) findViewById(R.id.selectionD);
        testQuestion = (TextView) findViewById(R.id.testQuestion);
        qImage = (ImageView) findViewById(R.id.questionImage);
        TextView tv1 = (TextView) findViewById(R.id.tv1);
        TextView tv2 = (TextView) findViewById(R.id.tv2);

        Typeface myTypeFace = Typeface.createFromAsset(getAssets(), "Fonts/gustanmedium.ttf");
        opA.setTypeface(myTypeFace);
        opB.setTypeface(myTypeFace);
        opC.setTypeface(myTypeFace);
        opD.setTypeface(myTypeFace);
        submitBtn.setTypeface(myTypeFace);
        testQuestion.setTypeface(myTypeFace);
        tv1.setTypeface(myTypeFace);
        tv2.setTypeface(myTypeFace);

        opA.setVisibility(View.VISIBLE);
        opB.setVisibility(View.VISIBLE);
        opC.setVisibility(View.VISIBLE);
        opD.setVisibility(View.VISIBLE);
        qImage.setVisibility(View.GONE);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        testDate = sdf.format(c.getTime()).substring(0, 10);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        surname = intent.getStringExtra("surname");
        title = intent.getStringExtra("title");
        discipline = intent.getStringExtra("discipline");
        department = intent.getStringExtra("department");
        emailT = intent.getStringExtra("email");
        String tmpAttempt = intent.getStringExtra("attempt");
        attempt = Integer.parseInt(tmpAttempt);

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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);
        //if (!hasFocus)
        //    Login();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {

            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {
                startService(new Intent(SynFuelTest.this, FloatingIcon.class));
                finish();
            } else { //Permission is not available
                Toast.makeText(this,
                        "Draw over other app permission not available. Closing the application",
                        Toast.LENGTH_SHORT).show();

                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, FloatingIcon.class));
    }

}
