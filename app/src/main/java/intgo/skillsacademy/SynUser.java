package intgo.skillsacademy;

/**
 * Created by danie on 2017/03/07.
 */
public class SynUser {
    int id, attempts;
    String id_num, name, surname, title, discipline, department;

    public SynUser(){}

    public SynUser(int id, String id_num, String name, String surname, String title, String discipline, String department, int attempts){
        this.id = id;
        this.id_num = id_num;
        this.name = name;
        this.surname = surname;
        this.title = title;
        this.discipline = discipline;
        this.department = department;
        this.attempts = attempts;
    }

    public SynUser(String id_num, String name, String surname, String title, String discipline, String department, int attempts){
        this.id_num = id_num;
        this.name = name;
        this.surname = surname;
        this.title = title;
        this.discipline = discipline;
        this.department = department;
        this.attempts = attempts;
    }

    public int getID(){
        return this.id;
    }
    public String getIDNum(){
        return this.id_num;
    }
    public String getName(){
        return this.name;
    }
    public String getSurname(){
        return this.surname;
    }
    public String getTitle(){
        return this.title;
    }
    public String getDiscipline(){
        return this.discipline;
    }
    public String getDepartment(){
        return this.department;
    }
    public int getAttempt(){
        return this.attempts;
    }

    public void setID(int id){
        this.id = id;
    }
    public void setIDNum(String item){
        this.id_num = item;
    }
    public void setName(String item){
        this.name = item;
    }
    public void setSurname(String item){
        this.surname = item;
    }
    public void setTitle(String item){
        this.title = item;
    }
    public void setDiscipline(String item){
        this.discipline = item;
    }
    public void setDepartment(String item){
        this.department = item;
    }
    public void setAttempt(int item){
        this.attempts = item;
    }
}