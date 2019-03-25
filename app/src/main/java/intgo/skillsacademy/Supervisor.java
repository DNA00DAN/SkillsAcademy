package intgo.skillsacademy;

/**
 * Created by 1-Finad on 10/6/2015.
 */
public class Supervisor {
    String number, password, location, course, email;
    int index;

    public Supervisor(String number, String password, int index, String email, String location, String course){
        this.number = number;
        this.password = password;
        this.index = index;
        this.email = email;
        this.location = location;
        this.course = course;
    }
}