package intgo.skillsacademy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Danie on 2017/02/27.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "Sasol_Skills_Academy";

    private static final String TABLE_TEST_RESULTS = "Test_Results";
    private static final String TABLE_INDUCTION_TEST_RESULTS = "Induction_Test_Results";
    private static final String TABLE_SYN_FUEL_TEST_RESULTS = "Synfuel_Test_Results";
    private static final String TABLE_SYN_FUEL_TEST = "SynFuel_Test";


    private static final String TABLE_TEST_QUESTIONS = "Electric_Questions";
    private static final String TABLE_BS_INDUCTION_TEST_QUESTIONS = "Brandspruit_Induction_Questions";
    private static final String TABLE_SEC_INDUCTION_TEST_QUESTIONS = "Secunda_Induction_Questions";
    private static final String TABLE_SSB_INDUCTION_TEST_QUESTIONS = "Sasolburg_Induction_Questions";
    private static final String TABLE_LOCATIONS = "Locations";
    private static final String TABLE_COURSES = "Courses";

    private static final String TABLE_USERS = "Users";
    private static final String TABLE_SYN_FUEL_USERS = "SynFuel_users";
    private static final String TABLE_SYN_SUPERVISOR = "Synfuel_Supervisor";

    private static final String KEY_ID = "id";
    private static final String KEY_ID_NUM = "id_num";
    private static final String KEY_NAME = "Name";
    private static final String KEY_SURNAME = "Surname";
    private static final String KEY_TITLE = "Title";
    private static final String KEY_DISCIPLINE = "Discipline";
    private static final String KEY_DEPARTMENT = "Department";
    private static final String KEY_ATTEMPTS = "Attempts";
    private static final String KEY_EMPLOYEE = "Employee_number";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_PASSWORD = "Password";
    private static final String KEY_USER_TYPE = "User_type";


    private static final String KEY_DATE_TESTED = "Date_tested";
    private static final String KEY_LEVEL = "Level";
    private static final String KEY_QUESTION_1 = "Question_1";
    private static final String KEY_QUESTION_2 = "Question_2";
    private static final String KEY_QUESTION_3 = "Question_3";
    private static final String KEY_QUESTION_4 = "Question_4";
    private static final String KEY_QUESTION_5 = "Question_5";
    private static final String KEY_QUESTION_6 = "Question_6";
    private static final String KEY_QUESTION_7 = "Question_7";
    private static final String KEY_QUESTION_8 = "Question_8";
    private static final String KEY_QUESTION_9 = "Question_9";
    private static final String KEY_QUESTION_10 = "Question_10";

    private static final String KEY_ANSWER_1 = "Answer_1";
    private static final String KEY_ANSWER_2 = "Answer_2";
    private static final String KEY_ANSWER_3 = "Answer_3";
    private static final String KEY_ANSWER_4 = "Answer_4";
    private static final String KEY_ANSWER_5 = "Answer_5";
    private static final String KEY_ANSWER_6 = "Answer_6";
    private static final String KEY_ANSWER_7 = "Answer_7";
    private static final String KEY_ANSWER_8 = "Answer_8";
    private static final String KEY_ANSWER_9 = "Answer_9";
    private static final String KEY_ANSWER_10 = "Answer_10";

    private static final String KEY_CORRECT_ANSWER_1 = "Correct_Answer_1";
    private static final String KEY_CORRECT_ANSWER_2 = "Correct_Answer_2";
    private static final String KEY_CORRECT_ANSWER_3 = "Correct_Answer_3";
    private static final String KEY_CORRECT_ANSWER_4 = "Correct_Answer_4";
    private static final String KEY_CORRECT_ANSWER_5 = "Correct_Answer_5";
    private static final String KEY_CORRECT_ANSWER_6 = "Correct_Answer_6";
    private static final String KEY_CORRECT_ANSWER_7 = "Correct_Answer_7";
    private static final String KEY_CORRECT_ANSWER_8 = "Correct_Answer_8";
    private static final String KEY_CORRECT_ANSWER_9 = "Correct_Answer_9";
    private static final String KEY_CORRECT_ANSWER_10 = "Correct_Answer_10";

    private static final String KEY_QUESTION = "Question";
    private static final String KEY_QUESTION_IMAGE = "Question_Image";
    private static final String KEY_CORRECT_ANSWER = "Correct_Answer";

    private static final String KEY_LOCATION = "Location";
    private static final String KEY_COURSE = "Course";
    private static final String KEY_QUESTION_TAG = "Question_Tag";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance  
    }

    // Creating Tables  
    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);

        insertAdminUser(db);
        insertSpinnerOptions(db);

        insertBSInductionQuestions(db);
        insertSECInductionQuestions(db);
        insertSSBInductionQuestions(db);
        insertSynFuelTest(db);
    }

    private void createTables(SQLiteDatabase db) {
        String CREATE_RESULTS_TABLE = "CREATE TABLE " + TABLE_TEST_RESULTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_DATE_TESTED + " TEXT NOT NULL,"
                + KEY_EMPLOYEE + " TEXT,"
                + KEY_LEVEL + " TEXT,"
                + KEY_QUESTION_1 + " TEXT,"
                + KEY_QUESTION_2 + " TEXT,"
                + KEY_QUESTION_3 + " TEXT,"
                + KEY_QUESTION_4 + " TEXT,"
                + KEY_QUESTION_5 + " TEXT,"
                + KEY_QUESTION_6 + " TEXT,"
                + KEY_QUESTION_7 + " TEXT,"
                + KEY_QUESTION_8 + " TEXT,"
                + KEY_QUESTION_9 + " TEXT,"
                + KEY_QUESTION_10 + " TEXT,"
                + KEY_ANSWER_1 + " TEXT,"
                + KEY_ANSWER_2 + " TEXT,"
                + KEY_ANSWER_3 + " TEXT,"
                + KEY_ANSWER_4 + " TEXT,"
                + KEY_ANSWER_5 + " TEXT,"
                + KEY_ANSWER_6 + " TEXT,"
                + KEY_ANSWER_7 + " TEXT,"
                + KEY_ANSWER_8 + " TEXT,"
                + KEY_ANSWER_9 + " TEXT,"
                + KEY_ANSWER_10 + " TEXT,"
                + KEY_CORRECT_ANSWER_1 + " TEXT,"
                + KEY_CORRECT_ANSWER_2 + " TEXT,"
                + KEY_CORRECT_ANSWER_3 + " TEXT,"
                + KEY_CORRECT_ANSWER_4 + " TEXT,"
                + KEY_CORRECT_ANSWER_5 + " TEXT,"
                + KEY_CORRECT_ANSWER_6 + " TEXT,"
                + KEY_CORRECT_ANSWER_7 + " TEXT,"
                + KEY_CORRECT_ANSWER_8 + " TEXT,"
                + KEY_CORRECT_ANSWER_9 + " TEXT,"
                + KEY_CORRECT_ANSWER_10 + " TEXT"
                + ")";
        db.execSQL(CREATE_RESULTS_TABLE);

        String CREATE_INDUCTION_RESULTS_TABLE = "CREATE TABLE " + TABLE_INDUCTION_TEST_RESULTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_DATE_TESTED + " TEXT NOT NULL,"
                + KEY_EMPLOYEE + " TEXT,"
                + KEY_LEVEL + " TEXT,"
                + KEY_QUESTION_1 + " TEXT,"
                + KEY_QUESTION_2 + " TEXT,"
                + KEY_QUESTION_3 + " TEXT,"
                + KEY_QUESTION_4 + " TEXT,"
                + KEY_QUESTION_5 + " TEXT,"
                + KEY_ANSWER_1 + " TEXT,"
                + KEY_ANSWER_2 + " TEXT,"
                + KEY_ANSWER_3 + " TEXT,"
                + KEY_ANSWER_4 + " TEXT,"
                + KEY_ANSWER_5 + " TEXT,"
                + KEY_CORRECT_ANSWER_1 + " TEXT,"
                + KEY_CORRECT_ANSWER_2 + " TEXT,"
                + KEY_CORRECT_ANSWER_3 + " TEXT,"
                + KEY_CORRECT_ANSWER_4 + " TEXT,"
                + KEY_CORRECT_ANSWER_5 + " TEXT"
                + ")";
        db.execSQL(CREATE_INDUCTION_RESULTS_TABLE);

        String CREATE_SYN_FUEL_RESULTS_TABLE = "CREATE TABLE " + TABLE_SYN_FUEL_TEST_RESULTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_DATE_TESTED + " TEXT,"
                + KEY_ID_NUM + " TEXT,"
                + KEY_ATTEMPTS + " TEXT,"
                + KEY_QUESTION_1 + " TEXT,"
                + KEY_QUESTION_2 + " TEXT,"
                + KEY_QUESTION_3 + " TEXT,"
                + KEY_QUESTION_4 + " TEXT,"
                + KEY_QUESTION_5 + " TEXT,"
                + KEY_ANSWER_1 + " TEXT,"
                + KEY_ANSWER_2 + " TEXT,"
                + KEY_ANSWER_3 + " TEXT,"
                + KEY_ANSWER_4 + " TEXT,"
                + KEY_ANSWER_5 + " TEXT,"
                + KEY_CORRECT_ANSWER_1 + " TEXT,"
                + KEY_CORRECT_ANSWER_2 + " TEXT,"
                + KEY_CORRECT_ANSWER_3 + " TEXT,"
                + KEY_CORRECT_ANSWER_4 + " TEXT,"
                + KEY_CORRECT_ANSWER_5 + " TEXT"
                + ")";
        db.execSQL(CREATE_SYN_FUEL_RESULTS_TABLE);

        String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + TABLE_TEST_QUESTIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_QUESTION + " TEXT,"
                + KEY_ANSWER_1 + " TEXT,"
                + KEY_ANSWER_2 + " TEXT,"
                + KEY_ANSWER_3 + " TEXT,"
                + KEY_ANSWER_4 + " TEXT,"
                + KEY_CORRECT_ANSWER + " TEXT"
                + ")";
        db.execSQL(CREATE_QUESTIONS_TABLE);

        String CREATE_BS_INDUCTION_QUESTIONS_TABLE = "CREATE TABLE " + TABLE_BS_INDUCTION_TEST_QUESTIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_QUESTION_TAG + " TEXT,"
                + KEY_QUESTION + " TEXT,"
                + KEY_ANSWER_1 + " TEXT,"
                + KEY_ANSWER_2 + " TEXT,"
                + KEY_ANSWER_3 + " TEXT,"
                + KEY_ANSWER_4 + " TEXT,"
                + KEY_CORRECT_ANSWER + " TEXT,"
                + KEY_QUESTION_IMAGE + " TEXT"
                + ")";
        db.execSQL(CREATE_BS_INDUCTION_QUESTIONS_TABLE);

        String CREATE_SEC_INDUCTION_QUESTIONS_TABLE = "CREATE TABLE " + TABLE_SEC_INDUCTION_TEST_QUESTIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_QUESTION_TAG + " TEXT,"
                + KEY_QUESTION + " TEXT,"
                + KEY_ANSWER_1 + " TEXT,"
                + KEY_ANSWER_2 + " TEXT,"
                + KEY_ANSWER_3 + " TEXT,"
                + KEY_ANSWER_4 + " TEXT,"
                + KEY_CORRECT_ANSWER + " TEXT,"
                + KEY_QUESTION_IMAGE + " TEXT"
                + ")";
        db.execSQL(CREATE_SEC_INDUCTION_QUESTIONS_TABLE);

        String CREATE_SSB_INDUCTION_QUESTIONS_TABLE = "CREATE TABLE " + TABLE_SSB_INDUCTION_TEST_QUESTIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_QUESTION_TAG + " TEXT,"
                + KEY_QUESTION + " TEXT,"
                + KEY_ANSWER_1 + " TEXT,"
                + KEY_ANSWER_2 + " TEXT,"
                + KEY_ANSWER_3 + " TEXT,"
                + KEY_ANSWER_4 + " TEXT,"
                + KEY_CORRECT_ANSWER + " TEXT,"
                + KEY_QUESTION_IMAGE + " TEXT"
                + ")";
        db.execSQL(CREATE_SSB_INDUCTION_QUESTIONS_TABLE);

        String CREATE_LOCATION_TABLE = "CREATE TABLE " + TABLE_LOCATIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_LOCATION + " TEXT"
                + ")";
        db.execSQL(CREATE_LOCATION_TABLE);

        String CREATE_COURSE_TABLE = "CREATE TABLE " + TABLE_COURSES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_COURSE + " TEXT"
                + ")";
        db.execSQL(CREATE_COURSE_TABLE);

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_EMPLOYEE + " TEXT NOT NULL UNIQUE,"
                + KEY_EMAIL + " TEXT,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_USER_TYPE + " TEXT,"
                + KEY_LOCATION + " TEXT,"
                + KEY_COURSE + " TEXT,"
                + KEY_LEVEL + " TEXT"
                + ")";
        db.execSQL(CREATE_USER_TABLE);

        String CREATE_SYN_SUPER_TABLE = "CREATE TABLE " + TABLE_SYN_SUPERVISOR + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT"
                + ")";
        db.execSQL(CREATE_SYN_SUPER_TABLE);

        String CREATE_SYN_TEST_TABLE = "CREATE TABLE " + TABLE_SYN_FUEL_TEST + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_QUESTION_TAG + " TEXT,"
                + KEY_QUESTION + " TEXT,"
                + KEY_ANSWER_1 + " TEXT,"
                + KEY_ANSWER_2 + " TEXT,"
                + KEY_CORRECT_ANSWER + " TEXT,"
                + KEY_QUESTION_IMAGE + " TEXT"
                + ")";
        db.execSQL(CREATE_SYN_TEST_TABLE);

        String CREATE_SYN_USER_TABLE = "CREATE TABLE " + TABLE_SYN_FUEL_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_ID_NUM + " TEXT NOT NULL UNIQUE,"
                + KEY_NAME + " TEXT,"
                + KEY_SURNAME + " TEXT,"
                + KEY_TITLE + " TEXT,"
                + KEY_DISCIPLINE + " TEXT,"
                + KEY_DEPARTMENT + " TEXT,"
                + KEY_ATTEMPTS + " INTEGER NOT NULL"
                + ")";
        db.execSQL(CREATE_SYN_USER_TABLE);
    }

    private void insertAdminUser(SQLiteDatabase db) {
        String tmpPass = md5("Rian-Yse");
        db.execSQL("INSERT INTO " + TABLE_USERS + "(Employee_number, Email, Password, User_type, Location, Course, Level) " +
                "   VALUES ('Admin', 'rian.ucs@netactive.co.za','" + tmpPass + "','Admin','All','All','All')");
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

    public String getSynSuperEmail(String name) {
        String client = "";

        String proc = "SELECT  * FROM " + TABLE_SYN_SUPERVISOR +
                " WHERE Name = '" + name + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(proc, null);

        if (cursor.moveToFirst()) {
            do {
                client = cursor.getString(2);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close(); // Closing database connection
        return client;
    }

    private void insertSpinnerOptions(SQLiteDatabase db) {


        db.execSQL("INSERT INTO " + TABLE_LOCATIONS + "(Location) VALUES ('BrandSpruit')");
        db.execSQL("INSERT INTO " + TABLE_LOCATIONS + "(Location) VALUES ('Sasolburg')");
        db.execSQL("INSERT INTO " + TABLE_LOCATIONS + "(Location) VALUES ('Secunda')");

        db.execSQL("INSERT INTO " + TABLE_COURSES + "(Course) VALUES ('Electrical')");
        db.execSQL("INSERT INTO " + TABLE_COURSES + "(Course) VALUES ('Fitting')");
        db.execSQL("INSERT INTO " + TABLE_COURSES + "(Course) VALUES ('Mechanical')");

        db.execSQL("INSERT INTO " + TABLE_SYN_SUPERVISOR + "(Name, Email) VALUES ('Danie van der Bank','vanderbankdanie@gmail.com')");
        db.execSQL("INSERT INTO " + TABLE_SYN_SUPERVISOR + "(Name, Email) VALUES ('Test Officer 2','rian.ucs@netactive.co.za')");
    }

    private void insertSynFuelTest(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TABLE_SYN_FUEL_TEST + "(Question_Tag, Question, Answer_1, Answer_2, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'SYN 1'," +
                " 'Activity is defined as a single step that with other steps make up the total Task?'," +
                " 'true'," +
                " 'false'," +
                " 'true'" +
                "," + " 'NA'"
                + ")");
        db.execSQL("INSERT INTO " + TABLE_SYN_FUEL_TEST + "(Question_Tag, Question, Answer_1, Answer_2, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'SYN 2'," +
                " 'Classified areas are Locations where fire or explosion hazards may exist due to flammable gases or vapours, flammable liquids, combustible dust, or ignitable fibres?'," +
                " 'true'," +
                " 'false'," +
                " 'true'" +
                "," + " 'NA'"
                + ")");
        db.execSQL("INSERT INTO " + TABLE_SYN_FUEL_TEST + "(Question_Tag, Question, Answer_1, Answer_2, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'SYN 3'," +
                " 'Pressure is the force exerted by a liquid, gas or vapour on the sides of its container?'," +
                " 'true'," +
                " 'false'," +
                " 'false'" +
                "," + " 'NA'"
                + ")");
        db.execSQL("INSERT INTO " + TABLE_SYN_FUEL_TEST + "(Question_Tag, Question, Answer_1, Answer_2, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'SYN 4'," +
                " 'Risk assessment is the overall process of risk evaluation?'," +
                " 'true'," +
                " 'false'," +
                " 'true'" +
                "," + " 'NA'"
                + ")");
        db.execSQL("INSERT INTO " + TABLE_SYN_FUEL_TEST + "(Question_Tag, Question, Answer_1, Answer_2, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'SYN 5'," +
                " 'A task is one activity to be performed on a specific, uniquely identified, item of equipment or system that will achieve a desired result?'," +
                " 'true'," +
                " 'false'," +
                " 'false'" +
                "," + " 'NA'"
                + ")");
        db.execSQL("INSERT INTO " + TABLE_SYN_FUEL_TEST + "(Question_Tag, Question, Answer_1, Answer_2, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'SYN 6'," +
                " 'A work permit is an authorisation to perform work safely on, in or around specific equipment, operational facilities or work environment prescribing what predetermined precautionary measures need to be followed?'," +
                " 'true'," +
                " 'false'," +
                " 'false'" +
                "," + " 'NA'"
                + ")");
    }

    private void insertBSInductionQuestions(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TABLE_BS_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'IBS1'," +
                " 'What Act (as amended from time to time) governs all activities within the Sasol South Africa working environment?'," +
                " 'Occupational Safety and Health Act (OHSA)'," +
                " 'General Rules and Regulations of the Maintenance Skills Academy'," +
                " 'NA'," +
                " 'NA'," +
                " 'Occupational Safety and Health Act (OHSA)'" +
                "," + " 'NA'"
                + ")");

        db.execSQL("INSERT INTO " + TABLE_BS_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'IBS2'," +
                " 'Who is responsible to provide a safe and healthy working environment for all Sasol personnel?'," +
                " 'Supervisor'," +
                " 'Management'," +
                " 'NA'," +
                " 'NA'," +
                " 'Management'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_BS_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'IBS3'," +
                " 'Who is co-responsible to issue legal instructions concerning health and safety'," +
                " 'Supervisor'," +
                " 'Management'," +
                " 'NA'," +
                " 'NA'," +
                " 'Supervisor'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_BS_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'IBS4'," +
                " 'What is the correct ambulance point number of the Maintenance Skills Academy Suildin ?'," +
                " '93'," +
                " '39'," +
                " 'NA'," +
                " 'NA'," +
                " '93'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_BS_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'IBS5'," +
                " 'At what telephone number should all incidents be reported to?'," +
                " '10-2555'," +
                " '10-4444'," +
                " 'NA'," +
                " 'NA'," +
                " '10-4444'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_BS_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'IBS6'," +
                " 'It is a serious offence not to wear/use appropriate PPE for a specific task?'," +
                " 'true'," +
                " 'false'," +
                " 'NA'," +
                " 'NA'," +
                " 'true'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_BS_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'IBS7'," +
                " 'When should any / all accidents / incidents be reported ?'," +
                " 'Immediately'," +
                " 'End of Shift'," +
                " 'Never'," +
                " 'NA'," +
                " 'Immediately'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_BS_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'IBS9.1'," +
                " 'Should a person receive a telephonic bomb threat, The receiver should ask as many questions ?'," +
                " 'true'," +
                " 'false'," +
                " 'NA'," +
                " 'NA'," +
                " 'false'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_BS_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'IBS9.2'," +
                " 'Should a person receive a telephonic bomb threat, The receiver should complete a bomb threat questionnaire as complete as possible during the conversation ?'," +
                " 'true'," +
                " 'false'," +
                " 'NA'," +
                " 'NA'," +
                " 'true'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_BS_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'IBS10'," +
                " 'Name the core of Sasol?'," +
                " 'Safety'," +
                " 'Health'," +
                " 'NA'," +
                " 'NA'," +
                " 'Safety'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_BS_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'IBS8.1'," +
                " 'What Alarm is Shown below?'," +
                " 'Fire'," +
                " 'Gas'," +
                " 'False Alarm'," +
                " 'All Clear'," +
                " 'Gas'" +
                "," + " 'gasimage.png'" + ")");

        db.execSQL("INSERT INTO " + TABLE_BS_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'IBS8.2'," +
                " 'What Alarm is Demonstrated below?'," +
                " 'Fire'," +
                " 'Gas'," +
                " 'False Alarm'," +
                " 'All Clear'," +
                " 'Fire'" +
                "," + " 'fireimage.png'" + ")");
    }

    private void insertSECInductionQuestions(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TABLE_SEC_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'ISEC1'," +
                " 'What Act (as amended from time to time) governs all activities within the Sasol South Africa working environment?'," +
                " 'Occupational Safety and Health Act (OHSA)'," +
                " 'General Rules and Regulations of the Maintenance Skills Academy'," +
                " 'NA'," +
                " 'NA'," +
                " 'Occupational Safety and Health Act (OHSA)'" +
                "," + " 'NA'"
                + ")");

        db.execSQL("INSERT INTO " + TABLE_SEC_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'ISEC2'," +
                " 'Who is responsible to provide a safe and healthy working environment for all Sasol personnel?'," +
                " 'Supervisor'," +
                " 'Management'," +
                " 'NA'," +
                " 'NA'," +
                " 'Management'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_SEC_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'ISEC3'," +
                " 'Who is co-responsible to issue legal instructions concerning health and safety'," +
                " 'Supervisor'," +
                " 'Management'," +
                " 'NA'," +
                " 'NA'," +
                " 'Supervisor'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_SEC_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'ISEC4'," +
                " 'What is the correct ambulance point number of the Maintenance Skills Academy Suildin ?'," +
                " '30'," +
                " '39'," +
                " 'NA'," +
                " 'NA'," +
                " '30'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_SEC_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'ISEC5'," +
                " 'At what telephone number should all incidents be reported to?'," +
                " '10-2555'," +
                " '10-4444'," +
                " 'NA'," +
                " 'NA'," +
                " '10-4444'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_SEC_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'ISEC6'," +
                " 'It is a serious offence not to wear/use appropriate PPE for a specific task?'," +
                " 'true'," +
                " 'false'," +
                " 'NA'," +
                " 'NA'," +
                " 'true'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_SEC_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'ISEC7'," +
                " 'When should any / all accidents / incidents be reported ?'," +
                " 'Immediately'," +
                " 'End of Shift'," +
                " 'Never'," +
                " 'NA'," +
                " 'Immediately'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_SEC_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'ISEC9.1'," +
                " 'Should a person receive a telephonic bomb threat, The receiver should ask as many questions ?'," +
                " 'true'," +
                " 'false'," +
                " 'NA'," +
                " 'NA'," +
                " 'false'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_SEC_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'ISEC9.2'," +
                " 'Should a person receive a telephonic bomb threat, The receiver should complete a bomb threat questionnaire as complete as possible during the conversation ?'," +
                " 'true'," +
                " 'false'," +
                " 'NA'," +
                " 'NA'," +
                " 'true'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_SEC_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'ISEC10'," +
                " 'Name the core of Sasol?'," +
                " 'Safety'," +
                " 'Health'," +
                " 'NA'," +
                " 'NA'," +
                " 'Safety'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_SEC_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'ISEC8.1'," +
                " 'What Alarm is Shown below?'," +
                " 'Fire'," +
                " 'Gas'," +
                " 'False Alarm'," +
                " 'All Clear'," +
                " 'Gas'" +
                "," + " 'gasimage.png'" + ")");

        db.execSQL("INSERT INTO " + TABLE_SEC_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'ISEC8.2'," +
                " 'What Alarm is Demonstrated below?'," +
                " 'Fire'," +
                " 'Gas'," +
                " 'False Alarm'," +
                " 'All Clear'," +
                " 'Fire'" +
                "," + " 'fireimage.png'" + ")");
    }

    private void insertSSBInductionQuestions(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TABLE_SSB_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'SSB1'," +
                " 'What Act (as amended from time to time) governs all activities within the Sasol South Africa working environment?'," +
                " 'Occupational Safety and Health Act (OHSA)'," +
                " 'General Rules and Regulations of the Maintenance Skills Academy'," +
                " 'NA'," +
                " 'NA'," +
                " 'Occupational Safety and Health Act (OHSA)'" +
                "," + " 'NA'"
                + ")");

        db.execSQL("INSERT INTO " + TABLE_SSB_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'SSB2'," +
                " 'Who is responsible to provide a safe and healthy working environment for all Sasol personnel?'," +
                " 'Supervisor'," +
                " 'Management'," +
                " 'NA'," +
                " 'NA'," +
                " 'Management'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_SSB_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'SSB3'," +
                " 'Who is co-responsible to issue legal instructions concerning health and safety'," +
                " 'Supervisor'," +
                " 'Management'," +
                " 'NA'," +
                " 'NA'," +
                " 'Supervisor'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_SSB_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'SSB4'," +
                " 'What is the correct ambulance point number of the Maintenance Skills Academy Suildin ?'," +
                " '10/3'," +
                " '3/10'," +
                " 'NA'," +
                " 'NA'," +
                " '10/3'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_SSB_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'SSB5'," +
                " 'At what telephone number should all incidents be reported to?'," +
                " '60-3331'," +
                " '60-3111'," +
                " 'NA'," +
                " 'NA'," +
                " '60/3111'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_SSB_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'SSB6'," +
                " 'It is a serious offence not to wear/use appropriate PPE for a specific task?'," +
                " 'true'," +
                " 'false'," +
                " 'NA'," +
                " 'NA'," +
                " 'true'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_SSB_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'SSB7'," +
                " 'When should any / all accidents / incidents be reported ?'," +
                " 'Immediately'," +
                " 'End of Shift'," +
                " 'Never'," +
                " 'NA'," +
                " 'Immediately'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_SSB_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'SSB9.1'," +
                " 'Should a person receive a telephonic bomb threat, The receiver should ask as many questions ?'," +
                " 'true'," +
                " 'false'," +
                " 'NA'," +
                " 'NA'," +
                " 'false'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_SSB_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'SSB9.2'," +
                " 'Should a person receive a telephonic bomb threat, The receiver should complete a bomb threat questionnaire as complete as possible during the conversation ?'," +
                " 'true'," +
                " 'false'," +
                " 'NA'," +
                " 'NA'," +
                " 'true'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_SSB_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'SSB10'," +
                " 'Name the core of Sasol?'," +
                " 'Safety'," +
                " 'Health'," +
                " 'NA'," +
                " 'NA'," +
                " 'Safety'" +
                "," + " 'NA'" + ")");

        db.execSQL("INSERT INTO " + TABLE_SSB_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'SSB8.1'," +
                " 'What Alarm is Shown below?'," +
                " 'Fire'," +
                " 'Gas'," +
                " 'False Alarm'," +
                " 'All Clear'," +
                " 'Gas'" +
                "," + " 'gasimage.png'" + ")");

        db.execSQL("INSERT INTO " + TABLE_SSB_INDUCTION_TEST_QUESTIONS + "(Question_Tag, Question, Answer_1, Answer_2, Answer_3, Answer_4, Correct_Answer, Question_Image) VALUES " +
                "(" +
                " 'SSB8.2'," +
                " 'What Alarm is Demonstrated below?'," +
                " 'Fire'," +
                " 'Gas'," +
                " 'False Alarm'," +
                " 'All Clear'," +
                " 'Fire'" +
                "," + " 'fireimage.png'" + ")");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEST_RESULTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BS_INDUCTION_TEST_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEC_INDUCTION_TEST_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SSB_INDUCTION_TEST_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEST_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INDUCTION_TEST_RESULTS);
        // Create tables again
        onCreate(db);
    }

    void AddSynfuelTestResult(SynfuelResult res) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_DATE_TESTED, res.getDateTested());
        values.put(KEY_ID_NUM, res.getIDNum());
        values.put(KEY_ATTEMPTS, res.getAttempt());

        values.put(KEY_QUESTION_1, res.getQ1());
        values.put(KEY_QUESTION_2, res.getQ2());
        values.put(KEY_QUESTION_3, res.getQ3());
        values.put(KEY_QUESTION_4, res.getQ4());
        values.put(KEY_QUESTION_5, res.getQ5());

        values.put(KEY_ANSWER_1, res.getA1());
        values.put(KEY_ANSWER_2, res.getA2());
        values.put(KEY_ANSWER_3, res.getA3());
        values.put(KEY_ANSWER_4, res.getA4());
        values.put(KEY_ANSWER_5, res.getA5());

        values.put(KEY_CORRECT_ANSWER_1, res.getCA1());
        values.put(KEY_CORRECT_ANSWER_2, res.getCA2());
        values.put(KEY_CORRECT_ANSWER_3, res.getCA3());
        values.put(KEY_CORRECT_ANSWER_4, res.getCA4());
        values.put(KEY_CORRECT_ANSWER_5, res.getCA5());

        db.insert(TABLE_SYN_FUEL_TEST_RESULTS, null, values);
        db.close(); // Closing database connection
    }

    void AddInductionTestResult(InductionResult res) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_DATE_TESTED, res.getDateTested());
        values.put(KEY_EMPLOYEE, res.getEmployee());
        values.put(KEY_LEVEL, res.getLevel());

        values.put(KEY_QUESTION_1, res.getQ1());
        values.put(KEY_QUESTION_2, res.getQ2());
        values.put(KEY_QUESTION_3, res.getQ3());
        values.put(KEY_QUESTION_4, res.getQ4());
        values.put(KEY_QUESTION_5, res.getQ5());

        values.put(KEY_ANSWER_1, res.getA1());
        values.put(KEY_ANSWER_2, res.getA2());
        values.put(KEY_ANSWER_3, res.getA3());
        values.put(KEY_ANSWER_4, res.getA4());
        values.put(KEY_ANSWER_5, res.getA5());

        values.put(KEY_CORRECT_ANSWER_1, res.getCA1());
        values.put(KEY_CORRECT_ANSWER_2, res.getCA2());
        values.put(KEY_CORRECT_ANSWER_3, res.getCA3());
        values.put(KEY_CORRECT_ANSWER_4, res.getCA4());
        values.put(KEY_CORRECT_ANSWER_5, res.getCA5());

        db.insert(TABLE_INDUCTION_TEST_RESULTS, null, values);
        db.close(); // Closing database connection
    }

    void AddUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_EMPLOYEE, user.getNumber());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_LEVEL, user.getLevel());
        values.put(KEY_LOCATION, user.getLocation());
        values.put(KEY_COURSE, user.getCourse());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_USER_TYPE, user.getUser());
        db.insert(TABLE_USERS, null, values);
        db.close(); // Closing database connection

    }

    void AddSynFuel(SynUser user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ID_NUM, user.getIDNum());
        values.put(KEY_NAME, user.getName());
        values.put(KEY_SURNAME, user.getSurname());
        values.put(KEY_TITLE, user.getTitle());
        values.put(KEY_DISCIPLINE, user.getDiscipline());
        values.put(KEY_DEPARTMENT, user.getDepartment());
        values.put(KEY_ATTEMPTS, user.getAttempt());
        db.insert(TABLE_SYN_FUEL_USERS, null, values);
        db.close(); // Closing database connection

    }

    void AddQuestion(Question question) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_QUESTION, question.getQuestion());
        values.put(KEY_ANSWER_1, question.getAnswer1());
        values.put(KEY_ANSWER_2, question.getAnswer2());
        values.put(KEY_ANSWER_3, question.getAnswer3());
        values.put(KEY_ANSWER_4, question.getAnswer4());
        values.put(KEY_CORRECT_ANSWER, question.getCorrectAnswer());
        values.put(KEY_QUESTION_IMAGE, question.getQuestionImage());

        db.insert(TABLE_TEST_QUESTIONS, null, values);
        db.close(); // Closing database connection

    }

    public List<String> getAllTests() {
        List<String> tests = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INDUCTION_TEST_RESULTS + " "
                + "ORDER BY Date_tested DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                tests.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return tests;
    }

    public List<String> getAllSynUsers() {
        List<String> users = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SYN_FUEL_USERS + " "
                + "GROUP BY id_num";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                users.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return users;
    }

    public List<String> getAllLocations() {
        List<String> loc = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LOCATIONS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                loc.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return loc;
    }

    public List<String> getAllCourses() {
        List<String> course = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_COURSES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                course.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return course;
    }

    public ArrayList<String> getAllBSInductionQuestions() {
        ArrayList<String> questions = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BS_INDUCTION_TEST_QUESTIONS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                questions.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return questions;
    }

    public ArrayList<String> getAllSECInductionQuestions() {
        ArrayList<String> questions = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SEC_INDUCTION_TEST_QUESTIONS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                questions.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return questions;
    }

    public ArrayList<String> getAllSSBInductionQuestions() {
        ArrayList<String> questions = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SSB_INDUCTION_TEST_QUESTIONS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                questions.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return questions;
    }

    public ArrayList<String> getAllSynFuelQuestions() {
        ArrayList<String> questions = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SYN_FUEL_TEST;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                questions.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return questions;
    }

    public ArrayList<String> getAllSynSupervisors() {
        ArrayList<String> questions = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SYN_SUPERVISOR;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                questions.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return questions;
    }

    public List<Result> getAllData(String dateT) {
        List<Result> resultList = new ArrayList<Result>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TEST_RESULTS +
                " WHERE Date_tested = '" + dateT + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Result res = new Result();
                res.setID(Integer.parseInt(cursor.getString(0)));
                res.setDate_Tested(cursor.getString(1));
                res.setEmployee(cursor.getString(2));
                res.setLevel(cursor.getString(3));

                res.setq1(cursor.getString(4));
                res.setq2(cursor.getString(5));
                res.setq3(cursor.getString(6));
                res.setq4(cursor.getString(7));
                res.setq5(cursor.getString(8));
                res.setq6(cursor.getString(9));
                res.setq7(cursor.getString(10));
                res.setq8(cursor.getString(11));
                res.setq9(cursor.getString(12));
                res.setq10(cursor.getString(13));

                res.seta1(cursor.getString(14));
                res.seta2(cursor.getString(15));
                res.seta3(cursor.getString(16));
                res.seta4(cursor.getString(17));
                res.seta5(cursor.getString(18));
                res.seta6(cursor.getString(19));
                res.seta7(cursor.getString(20));
                res.seta8(cursor.getString(21));
                res.seta9(cursor.getString(22));
                res.seta10(cursor.getString(23));

                res.setca1(cursor.getString(24));
                res.setca2(cursor.getString(25));
                res.setca3(cursor.getString(26));
                res.setca4(cursor.getString(27));
                res.setca5(cursor.getString(28));
                res.setca6(cursor.getString(29));
                res.setca7(cursor.getString(30));
                res.setca8(cursor.getString(31));
                res.setca9(cursor.getString(32));
                res.setca10(cursor.getString(33));

                Log.d("Employee:", res.getEmployee());
                Log.d("Level:", res.getLevel());

                resultList.add(res);
            } while (cursor.moveToNext());
        }
// closing connection
        cursor.close();
        db.close();
        return resultList;
    }

    public List<InductionResult> getAllInductionData(String dateT) {
        List<InductionResult> resultList = new ArrayList<InductionResult>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_INDUCTION_TEST_RESULTS +
                " WHERE Date_tested = '" + dateT + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                InductionResult res = new InductionResult();
                res.setID(Integer.parseInt(cursor.getString(0)));
                res.setDate_Tested(cursor.getString(1));
                res.setEmployee(cursor.getString(2));
                res.setLevel(cursor.getString(3));

                res.setq1(cursor.getString(4));
                res.setq2(cursor.getString(5));
                res.setq3(cursor.getString(6));
                res.setq4(cursor.getString(7));
                res.setq5(cursor.getString(8));

                res.seta1(cursor.getString(9));
                res.seta2(cursor.getString(10));
                res.seta3(cursor.getString(11));
                res.seta4(cursor.getString(12));
                res.seta5(cursor.getString(13));

                res.setca1(cursor.getString(14));
                res.setca2(cursor.getString(15));
                res.setca3(cursor.getString(16));
                res.setca4(cursor.getString(17));
                res.setca5(cursor.getString(18));

                Log.d("Employee:", res.getEmployee());
                Log.d("Level:", res.getLevel());

                resultList.add(res);
            } while (cursor.moveToNext());
        }
// closing connection
        cursor.close();
        db.close();
        return resultList;
    }

    public List<Question> getAllBSInductionQuestionData(String question) {
        List<Question> questionList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BS_INDUCTION_TEST_QUESTIONS +
                " WHERE Question = '" + question + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Question res = new Question();
                res.setID(Integer.parseInt(cursor.getString(0)));
                res.setQuestionTag(cursor.getString(1));
                res.setQuestion(cursor.getString(2));
                res.setAnswer1(cursor.getString(3));
                res.setAnswer2(cursor.getString(4));
                res.setAnswer3(cursor.getString(5));
                res.setAnswer4(cursor.getString(6));
                res.setCorrectAnswer(cursor.getString(7));
                res.setQuestionImage(cursor.getString(8));

                questionList.add(res);
            } while (cursor.moveToNext());
        }
// closing connection
        cursor.close();
        db.close();
        return questionList;
    }

    public List<Question> getAllSECInductionQuestionData(String question) {
        List<Question> questionList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SEC_INDUCTION_TEST_QUESTIONS +
                " WHERE Question = '" + question + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Question res = new Question();
                res.setID(Integer.parseInt(cursor.getString(0)));
                res.setQuestionTag(cursor.getString(1));
                res.setQuestion(cursor.getString(2));
                res.setAnswer1(cursor.getString(3));
                res.setAnswer2(cursor.getString(4));
                res.setAnswer3(cursor.getString(5));
                res.setAnswer4(cursor.getString(6));
                res.setCorrectAnswer(cursor.getString(7));
                res.setQuestionImage(cursor.getString(8));

                questionList.add(res);
                System.err.println(res.getQuestion());
                System.err.println(res.getQuestionTag());

                System.err.println(res.getAnswer1());
                System.err.println(res.getAnswer2());
                System.err.println(res.getAnswer3());
                System.err.println(res.getAnswer4());

                System.err.println(res.getCorrectAnswer());
                System.err.println(res.getQuestionImage());

            } while (cursor.moveToNext());
        }

// closing connection
        cursor.close();
        db.close();
        return questionList;
    }

    public List<Question> getAllSSBInductionQuestionData(String question) {
        List<Question> questionList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SSB_INDUCTION_TEST_QUESTIONS +
                " WHERE Question = '" + question + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Question res = new Question();
                res.setID(Integer.parseInt(cursor.getString(0)));
                res.setQuestionTag(cursor.getString(1));
                res.setQuestion(cursor.getString(2));
                res.setAnswer1(cursor.getString(3));
                res.setAnswer2(cursor.getString(4));
                res.setAnswer3(cursor.getString(5));
                res.setAnswer4(cursor.getString(6));
                res.setCorrectAnswer(cursor.getString(7));
                res.setQuestionImage(cursor.getString(8));

                questionList.add(res);
            } while (cursor.moveToNext());
        }
// closing connection
        cursor.close();
        db.close();
        return questionList;
    }

    public List<SynFuelQuestion> getAllSynFuelQuestionData(String question) {
        List<SynFuelQuestion> questionList = new ArrayList<SynFuelQuestion>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SYN_FUEL_TEST +
                " WHERE Question = '" + question + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SynFuelQuestion res = new SynFuelQuestion();
                res.setID(Integer.parseInt(cursor.getString(0)));
                res.setQuestionTag(cursor.getString(1));
                res.setQuestion(cursor.getString(2));
                res.setAnswer1(cursor.getString(3));
                res.setAnswer2(cursor.getString(4));
                res.setCorrectAnswer(cursor.getString(5));
                res.setQuestionImage(cursor.getString(6));

                questionList.add(res);
            } while (cursor.moveToNext());
        }
// closing connection
        cursor.close();
        db.close();
        return questionList;
    }

    public List<User> getAllUserData(String user) {
        List<User> userList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERS +
                " WHERE Employee_number = '" + user + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                User res = new User();
                res.setID(Integer.parseInt(cursor.getString(0)));
                res.setNumber(cursor.getString(1));
                res.setEmail(cursor.getString(2));
                res.setPassword(cursor.getString(3));
                res.setUser(cursor.getString(4));
                res.setLocation(cursor.getString(5));
                res.setCourse(cursor.getString(6));
                res.setLevel(cursor.getString(7));

                userList.add(res);
            } while (cursor.moveToNext());
        }
// closing connection
        cursor.close();
        db.close();
        return userList;
    }

    public void updateUserLevel(String emp_num, String level) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_USERS + " SET " + KEY_LEVEL + " = '" + level
                + "' WHERE " + KEY_EMPLOYEE + " = '" + emp_num + "'");

        Log.d("level update Complete", "success");

        db.close(); // Closing database connection
    }

    public void updateUserAttempt(String id_num, String attempt) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_SYN_FUEL_USERS + " SET " + KEY_ATTEMPTS + " = '" + attempt
                + "' WHERE " + KEY_ID_NUM + " = '" + id_num + "'");

        Log.d("attempt update Complete", "success");

        db.close(); // Closing database connection
    }

}
