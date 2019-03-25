package intgo.skillsacademy;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by danie on 2017/03/07.
 */
public class LocalSupervisor {
    public static final String SP_NAME = "SupervisorDetails";

    SharedPreferences supervisorLocalDatabase;
    public LocalSupervisor(Context context) {
        supervisorLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeSupervisorData(Supervisor user) {
        SharedPreferences.Editor userLocalDatabaseEditor = supervisorLocalDatabase.edit();
        userLocalDatabaseEditor.putString("number", user.number);
        userLocalDatabaseEditor.putString("password", user.password);
        userLocalDatabaseEditor.putInt("index", user.index);
        userLocalDatabaseEditor.putString("email", user.email);
        userLocalDatabaseEditor.putString("location", user.location);
        userLocalDatabaseEditor.putString("course", user.course);
        userLocalDatabaseEditor.apply();
    }
    public String getNumber(){
        return supervisorLocalDatabase.getString("number", "");
    }

    public String getPassword(){
        return supervisorLocalDatabase.getString("password", "");
    }

    public String getEmail(){
        return supervisorLocalDatabase.getString("email", "");
    }

    public String getLocation(){
        return supervisorLocalDatabase.getString("location", "");
    }

    public String getCourse(){
        return supervisorLocalDatabase.getString("course", "");
    }

    public int getIndex(){
        return supervisorLocalDatabase.getInt("index", 0);
    }

}
