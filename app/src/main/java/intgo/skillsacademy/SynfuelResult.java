package intgo.skillsacademy;

/**
 * Created by danie on 2017/02/28.
 */

public class SynfuelResult {
    int id;
    String date_tested;
    String id_num;
    String attempt;
    String q1,q2,q3,q4,q5;
    String a1,a2,a3,a4,a5;
    String ca1,ca2,ca3,ca4,ca5;

    public SynfuelResult(){   }

    public SynfuelResult(int id, String date, String id_num, String attempt,
                         String q1, String q2, String q3, String q4, String q5,
                         String a1, String a2, String a3, String a4, String a5,
                         String ca1, String ca2, String ca3, String ca4, String ca5){

        this.id = id;
        this.date_tested = date;
        this.id_num = id_num;
        this.attempt = attempt;

        this.q1 = q1;this.q2 = q2;this.q3 = q3;this.q4 = q4;this.q5 = q5;

        this.a1 = a1;this.a2 = a2;this.a3 = a3;this.a4 = a4;this.a5 = a5;

        this.ca1 = ca1;this.ca2 = ca2;this.ca3 = ca3;this.ca4 = ca4;this.ca5 = ca5;

    }

    public SynfuelResult(String date, String id_num, String attempt,
                         String q1, String q2, String q3, String q4, String q5,
                         String a1, String a2, String a3, String a4, String a5,
                         String ca1, String ca2, String ca3, String ca4, String ca5){

        this.date_tested = date;
        this.id_num = id_num;
        this.attempt = attempt;

        this.q1 = q1;this.q2 = q2;this.q3 = q3;this.q4 = q4;this.q5 = q5;

        this.a1 = a1;this.a2 = a2;this.a3 = a3;this.a4 = a4;this.a5 = a5;

        this.ca1 = ca1;this.ca2 = ca2;this.ca3 = ca3;this.ca4 = ca4;this.ca5 = ca5;

    }

    public int getID(){
        return this.id;
    }
    public String getDateTested(){
        return this.date_tested;
    }
    public String getIDNum(){
        return this.id_num;
    }
    public String getAttempt(){
        return this.attempt;
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

    public void setID(int id){
        this.id = id;
    }
    public void setDate_Tested(String item){
        this.date_tested = item;
    }
    public void setIDNum(String item){
        this.id_num = item;
    }
    public void setAttempt(String item){
        this.attempt = item;
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

}
