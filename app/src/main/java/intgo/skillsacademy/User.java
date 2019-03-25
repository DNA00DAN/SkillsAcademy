package intgo.skillsacademy;

/**
 * Created by danie on 2017/03/07.
 */
public class User {
    int id;
    String number, password, level, location, course, user, email;

    public User(){}

    public User(int id, String number, String password,String level, String location, String course, String user, String email){
        this.id = id;
        this.number = number;
        this.password = password;
        this.level = level;
        this.location = location;
        this.course = course;
        this.email = email;
        this.user = user;
    }

    public User(String number, String password,String level, String location, String course, String user, String email){
        this.number = number;
        this.password = password;
        this.level = level;
        this.location = location;
        this.course = course;
        this.email = email;
        this.user = user;
    }

    public int getID(){
        return this.id;
    }
    public String getNumber(){
        return this.number;
    }
    public String getPassword(){
        return this.password;
    }
    public String getLevel(){
        return this.level;
    }
    public String getLocation(){
        return this.location;
    }
    public String getCourse(){
        return this.course;
    }
    public String getEmail(){
        return this.email;
    }
    public String getUser(){
        return this.user;
    }

    public void setID(int id){
        this.id = id;
    }
    public void setNumber(String item){
        this.number = item;
    }
    public void setPassword(String item){
        this.password = item;
    }
    public void setLevel(String item){
        this.level = item;
    }
    public void setLocation(String item){
        this.location = item;
    }
    public void setCourse(String item){
        this.course = item;
    }
    public void setEmail(String item){
        this.email = item;
    }
    public void setUser(String item){
        this.user = item;
    }
}