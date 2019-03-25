package intgo.skillsacademy;

/**
 * Created by danie on 2017/02/28.
 */

public class Result {
    int id;
    String date_tested;
    String employee;
    String level;
    String q1,q2,q3,q4,q5,q6,q7,q8,q9,q10;
    String a1,a2,a3,a4,a5,a6,a7,a8,a9,a10;
    String ca1,ca2,ca3,ca4,ca5,ca6,ca7,ca8,ca9,ca10;

    public Result(){   }

    public Result(int id, String date, String employee, String level,
                  String q1, String q2, String q3, String q4, String q5, String q6, String q7, String q8, String q9, String q10,
                  String a1, String a2, String a3, String a4, String a5, String a6, String a7, String a8, String a9, String a10,
                  String ca1, String ca2, String ca3, String ca4, String ca5, String ca6, String ca7, String ca8, String ca9, String ca10){

        this.id = id;
        this.date_tested = date;
        this.employee = employee;
        this.level = level;
        
        this.q1 = q1;this.q2 = q2;this.q3 = q3;this.q4 = q4;this.q5 = q5;
        this.q6 = q6;this.q7 = q7;this.q8 = q8;this.q9 = q9;this.q10 = q10;

        this.a1 = a1;this.a2 = a2;this.a3 = a3;this.a4 = a4;this.a5 = a5;
        this.a6 = a6;this.a7 = a7;this.a8 = a8;this.a9 = a9;this.a10 = a10;

        this.ca1 = ca1;this.ca2 = ca2;this.ca3 = ca3;this.ca4 = ca4;this.ca5 = ca5;
        this.ca6 = ca6;this.ca7 = ca7;this.ca8 = ca8;this.ca9 = ca9;this.ca10 = ca10;
        
    }

    public Result(String date, String employee, String level,
                  String q1, String q2, String q3, String q4, String q5, String q6, String q7, String q8, String q9, String q10,
                  String a1, String a2, String a3, String a4, String a5, String a6, String a7, String a8, String a9, String a10,
                  String ca1, String ca2, String ca3, String ca4, String ca5, String ca6, String ca7, String ca8, String ca9, String ca10){

        this.date_tested = date;
        this.employee = employee;
        this.level = level;

        this.q1 = q1;this.q2 = q2;this.q3 = q3;this.q4 = q4;this.q5 = q5;
        this.q6 = q6;this.q7 = q7;this.q8 = q8;this.q9 = q9;this.q10 = q10;

        this.a1 = a1;this.a2 = a2;this.a3 = a3;this.a4 = a4;this.a5 = a5;
        this.a6 = a6;this.a7 = a7;this.a8 = a8;this.a9 = a9;this.a10 = a10;

        this.ca1 = ca1;this.ca2 = ca2;this.ca3 = ca3;this.ca4 = ca4;this.ca5 = ca5;
        this.ca6 = ca6;this.ca7 = ca7;this.ca8 = ca8;this.ca9 = ca9;this.ca10 = ca10;

    }

    public int getID(){
        return this.id;
    }
    public String getDateTested(){
        return this.date_tested;
    }
    public String getEmployee(){
        return this.employee;
    }
    public String getLevel(){
        return this.level;
    }
    public String getQ1(){
        return this.q1;
    }
    public String getQ2(){
        return this.q2;
    }
    public String getQ3(){
        return this.q3;
    }
    public String getQ4(){
        return this.q4;
    }
    public String getQ5(){
        return this.q5;
    }
    public String getQ6(){
        return this.q6;
    }
    public String getQ7(){
        return this.q7;
    }
    public String getQ8(){
        return this.q8;
    }
    public String getQ9(){
        return this.q9;
    }
    public String getQ10(){
        return this.q10;
    }

    public String getA1(){
        return this.a1;
    }
    public String getA2(){
        return this.a2;
    }
    public String getA3(){
        return this.a3;
    }
    public String getA4(){
        return this.a4;
    }
    public String getA5(){
        return this.a5;
    }
    public String getA6(){
        return this.a6;
    }
    public String getA7(){
        return this.a7;
    }
    public String getA8(){
        return this.a8;
    }
    public String getA9(){
        return this.a9;
    }
    public String getA10(){
        return this.a10;
    }

    public String getCA1(){
        return this.ca1;
    }
    public String getCA2(){
        return this.ca2;
    }
    public String getCA3(){
        return this.ca3;
    }
    public String getCA4(){
        return this.ca4;
    }
    public String getCA5(){
        return this.ca5;
    }
    public String getCA6(){
        return this.ca6;
    }
    public String getCA7(){
        return this.ca7;
    }
    public String getCA8(){
        return this.ca8;
    }
    public String getCA9(){
        return this.ca9;
    }
    public String getCA10(){
        return this.ca10;
    }

    public void setID(int id){
        this.id = id;
    }

    public void setDate_Tested(String item){
        this.date_tested = item;
    }
    public void setEmployee(String item){
        this.employee = item;
    }
    public void setLevel(String item){
        this.level = item;
    }
    
    public void setq1(String item){
        this.q1 = item;
    }
    public void setq2(String item){
        this.q2 = item;
    }
    public void setq3(String item){
        this.q3 = item;
    }
    public void setq4(String item){
        this.q4 = item;
    }
    public void setq5(String item){
        this.q5 = item;
    }
    public void setq6(String item){
        this.q6 = item;
    }
    public void setq7(String item){
        this.q7 = item;
    }
    public void setq8(String item){
        this.q8 = item;
    }
    public void setq9(String item){
        this.q9 = item;
    }
    public void setq10(String item){
        this.q10 = item;
    }

    public void seta1(String item){
        this.a1 = item;
    }
    public void seta2(String item){
        this.a2 = item;
    }
    public void seta3(String item){
        this.a3 = item;
    }
    public void seta4(String item){
        this.a4 = item;
    }
    public void seta5(String item){
        this.a5 = item;
    }
    public void seta6(String item){
        this.a6 = item;
    }
    public void seta7(String item){
        this.a7 = item;
    }
    public void seta8(String item){
        this.a8 = item;
    }
    public void seta9(String item){
        this.a9 = item;
    }
    public void seta10(String item){
        this.a10 = item;
    }

    public void setca1(String item){
        this.ca1 = item;
    }
    public void setca2(String item){
        this.ca2 = item;
    }
    public void setca3(String item){
        this.ca3 = item;
    }
    public void setca4(String item){
        this.ca4 = item;
    }
    public void setca5(String item){
        this.ca5 = item;
    }
    public void setca6(String item){
        this.ca6 = item;
    }
    public void setca7(String item){
        this.ca7 = item;
    }
    public void setca8(String item){
        this.ca8 = item;
    }
    public void setca9(String item){
        this.ca9 = item;
    }
    public void setca10(String item){
        this.ca10 = item;
    }

}
