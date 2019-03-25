package intgo.skillsacademy;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by danie on 2017/03/07.
 */
public class LocalUser {
    public static final String SP_NAME = "userDetails";

    SharedPreferences userLocalDatabase;
    public LocalUser(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putString("number", user.number);
        userLocalDatabaseEditor.putString("password", user.password);
        userLocalDatabaseEditor.putString("level", user.level);
        userLocalDatabaseEditor.putString("location", user.location);
        userLocalDatabaseEditor.putString("course", user.course);

        userLocalDatabaseEditor.putString("email", user.email);
        userLocalDatabaseEditor.putString("user_type", user.user);
        userLocalDatabaseEditor.apply();
    }

    public String getNumber(){
        return userLocalDatabase.getString("number", "");
    }

    public String getPassword(){
        return userLocalDatabase.getString("password", "");
    }

    public String getLevel(){
        return userLocalDatabase.getString("level", "");
    }

    public String getLocation(){
        return userLocalDatabase.getString("location", "");
    }

    public String getCourse(){
        return userLocalDatabase.getString("course", "");
    }

    public String getUser(){
        return userLocalDatabase.getString("user_type", "");
    }

    public String getEmail(){
        return userLocalDatabase.getString("email", "");
    }


}
