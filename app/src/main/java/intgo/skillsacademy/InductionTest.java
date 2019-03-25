package intgo.skillsacademy;

import android.app.Dialog;
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

public class InductionTest extends AppCompatActivity {
    private Button submitBtn;
    private CheckBox opA, opB, opC, opD;
    private String answer;
    private String[] testQuestions;
    private String randomQuestion, status = "FAIL";
    private TextView testQuestion;
    private ArrayList<String> answers, questions, c_answers, test_questions, questionTags;
    private ArrayList<Integer> cols;
    private String correctAnswer, questionImage, testDate;
    private int questionNr = 1, score = 0, total = 0;
    private boolean exit = false;
    String emp_number, emp_password, userType, emp_course, emp_level, emp_location, emp_email;
    LocalSupervisor supervisorLocalStore;
    private EditText txtUsername, txtPassword;

    private String Number, Password, questionTag;
    LocalUser userLocalStore;
    ImageView qImage;
    Button btnLogin, btnCancel;
    public static final String FONT = "resources/fonts/FreeSans.ttf";
    String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"};

    int permsRequestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_induction_test);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initializeComponents();
        resetSelection();
        initializeArrays();
        loadQuestions();
        randomizeQuestion();
        loadAnswers();
        hideNavbar();


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "You selected " + answer, Toast.LENGTH_SHORT).show();
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


    private void validatePossibleAnswers() {
        if (opA.getText().toString().equals("NA")) {
            opA.setVisibility(View.GONE);
        }
        if (opB.getText().toString().equals("NA")) {
            opB.setVisibility(View.GONE);
        }
        if (opC.getText().toString().equals("NA")) {
            opC.setVisibility(View.GONE);
        }
        if (opD.getText().toString().equals("NA")) {
            opD.setVisibility(View.GONE);
        }
        if (!questionImage.equals("NA")) {
            try {
                qImage.setVisibility(View.VISIBLE);
                Drawable d = Drawable.createFromStream(getAssets().open("Question_Images/Induction/" + questionImage), null);
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
                Toast.makeText(InductionTest.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                return;
            } else {
                questionTags.add(questionTag);
                answers.add(tmpA);
                test_questions.add(tmpQ);
                c_answers.add(correctAnswer);
                saveResult();
                questionNr++;
                Log.d("answer", answer);
                Log.d("question", testQuestion.getText().toString());
                resetSelection();
                if (questions.size() > 0) {
                    randomizeQuestion();
                    loadAnswers();
                }
            }
        } else {

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
            userLocalStore = new LocalUser(this);
            String employee = userLocalStore.getNumber();
            String level = userLocalStore.getLevel();

            saveToDB(employee, level);
            savePDFReport();
            if (status.equals("PASS")) {
                updateUserDetails();
            }

            Toast.makeText(InductionTest.this, "Test has been completed", Toast.LENGTH_LONG).show();
            questionNr = 1;
            initializeArrays();

            resetSelection();
            //Login();
            loadReport();
        }
    }

    private void updateUserDetails() {
        userLocalStore = new LocalUser(getApplicationContext());
        Password = userLocalStore.getPassword();
        String emp_num = userLocalStore.getNumber();
        String emp_pass = userLocalStore.getPassword();
        String emp_lev = userLocalStore.getLevel();
        String emp_location = userLocalStore.getLocation();
        String emp_course = userLocalStore.getCourse();
        String userType = userLocalStore.getUser();
        String emp_email = userLocalStore.getEmail();
        String new_lev = "";

        if (emp_lev.equals("Induction")) {
            new_lev = "Level 2";
        } else if (emp_lev.equals("Level 2")) {
            new_lev = "Level 3";
        } else if (emp_lev.equals("Level 3")) {
            new_lev = "Level 4";
        } else if (emp_lev.equals("Level 4")) {
            new_lev = "Level 4";
        }

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        db.updateUserLevel(emp_num, new_lev);
        User NewUser = new User(emp_num, emp_pass, new_lev, emp_location, emp_course, userType, emp_email);
        userLocalStore.storeUserData(NewUser);
    }

    private void saveToDB(String employee, String level) {
        DatabaseHandler db = new DatabaseHandler(this);
        db.AddInductionTestResult(new InductionResult(testDate, employee, level,

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
            if (shouldAskPermission()) {
                if (hasPermission(perms[0]) && hasPermission(perms[1])) {

                } else {
                    if (!hasPermission(perms[0]) && !hasPermission(perms[1])) {
                        requestPermissions(perms, permsRequestCode);
                    }
                }
            }

            File fol = new File(Environment
                    .getExternalStorageDirectory()
                    + "/SKA/");
            fol.mkdirs();

            File pdfFile = new File(Environment
                    .getExternalStorageDirectory()
                    + "/SKA/" + testDate + ".pdf");

            Document document = new Document(PageSize.A4);

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFile.toString()));

            document.open();


            Paragraph header = new Paragraph(getHeaderFont("Sasol Skills Academy", 40));
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


            table.addCell(getNormalCell("Employee Number:", 20));
            table.addCell(getNormalCell(userLocalStore.getNumber(), 20));
            table.addCell(getNormalCell("Test level:", 20));
            table.addCell(getNormalCell(userLocalStore.getLevel(), 20));

            int q = 1;
            for (int po = 0; po < 5; po++) {
                table.addCell(getNormalCell("Question " + q + ":", 16));
                table.addCell(getNormalCell(questionTags.get(po), 14));
                questionImage = "NA";
                loadQImage(test_questions.get(po));

                if (!questionImage.equals("NA")) {
                    InputStream ims = getAssets().open("Question_Images/Induction/" + questionImage);
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
            Double perc = (Double.parseDouble("" + score) * 100.0) / Double.parseDouble("" + total);
            status = "FAIL";
            if (perc > 99.9) {
                status = "PASS";
            }
            table.addCell(getFinalCell("" + score + "/" + "" + total + " (" + perc + "%) " + status, 14));
            table.setTotalWidth(PageSize.A4.getWidth() - 50);
            table.setLockedWidth(true);
            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
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


    public PdfPCell getNormalCell2(String string, float size)
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
        PdfPCell cell = new PdfPCell(new Phrase(string, f));
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
            score++;
        } else {
            f.setColor(new BaseColor(0, 56, 98));
            col = BaseColor.RED;
        }
        total++;
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
        Intent ii = new Intent(getApplicationContext(), InductionTestResults.class);
        ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ii.putExtra("status", status);
        startActivity(ii);
    }

    private void sendreport(String super_email) {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{super_email});
        //email.putExtra(Intent.EXTRA_CC, new String[]{ to});
        //email.putExtra(Intent.EXTRA_BCC, new String[]{to});
        email.putExtra(Intent.EXTRA_SUBJECT, "Test Subject");
        email.putExtra(Intent.EXTRA_TEXT, "Hello world");
        File file = new File("/sdcard/DCIM/te/test.jpg");

        email.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        email.setType("image/jpeg");
        email.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file.getAbsolutePath()));

        //need this to prompts email client only
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }


    private void loadAnswers() {
        opA.setVisibility(View.VISIBLE);
        opB.setVisibility(View.VISIBLE);
        opC.setVisibility(View.VISIBLE);
        opD.setVisibility(View.VISIBLE);

        userLocalStore = new LocalUser(this);
        String loc = userLocalStore.getLocation();

        if (loc.equals("BrandSpruit")) {
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            List<Question> questions = db.getAllBSInductionQuestionData(testQuestion.getText().toString());

            for (Question res : questions) {

                opA.setText(res.getAnswer1());
                opB.setText(res.getAnswer2());
                opC.setText(res.getAnswer3());
                opD.setText(res.getAnswer4());
                questionTag = res.getQuestionTag();
                correctAnswer = res.getCorrectAnswer();
                questionImage = res.getQuestionImage();

            }
        } else if (loc.equals("Secunda")) {
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            List<Question> questions = db.getAllSECInductionQuestionData(testQuestion.getText().toString());

            for (Question res : questions) {

                opA.setText(res.getAnswer1());
                opB.setText(res.getAnswer2());
                opC.setText(res.getAnswer3());
                opD.setText(res.getAnswer4());
                questionTag = res.getQuestionTag();
                correctAnswer = res.getCorrectAnswer();
                questionImage = res.getQuestionImage();

            }
        } else {
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            List<Question> questions = db.getAllSSBInductionQuestionData(testQuestion.getText().toString());

            for (Question res : questions) {

                opA.setText(res.getAnswer1());
                opB.setText(res.getAnswer2());
                opC.setText(res.getAnswer3());
                opD.setText(res.getAnswer4());
                questionTag = res.getQuestionTag();
                correctAnswer = res.getCorrectAnswer();
                questionImage = res.getQuestionImage();

            }
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
        userLocalStore = new LocalUser(this);
        String loc = userLocalStore.getLocation();
        if (loc.equals("BrandSpruit")) {
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            questions = db.getAllBSInductionQuestions();
        } else if (loc.equals("Secunda")) {
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            questions = db.getAllSECInductionQuestions();
        } else if (loc.equals("Sasolburg")) {
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            questions = db.getAllSSBInductionQuestions();
        }

    }

    private void randomizeQuestion() {
        randomQuestion = questions.get(new Random().nextInt(questions.size()));
        testQuestion.setText(randomQuestion);
        questions.remove(randomQuestion);
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

    }

    private void Login() {
        // Create Object of Dialog class
        final Dialog login = new Dialog(this);
        // Set GUI of Login screen
        login.setContentView(R.layout.activity_login_dialog);
        login.setTitle(Html.fromHtml("<h2><font color='#033b61'>Supervisor Login</font></h2>"));
        login.setCancelable(false);

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
        btnCancel.setVisibility(View.GONE);
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


    @Override
    public void onBackPressed() {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus)
            Login();
    }


}
