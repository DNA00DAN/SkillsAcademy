package intgo.skillsacademy;

/**
 * Created by danie on 2017/03/01.
 */

public class Question {
    int id;
    String questionTag, question;
    String location;
    String answer1,answer2,answer3,answer4;
    String correct_answer;
    String question_image;

    public Question(){}

    public Question(int id, String location, String questionTag, String question, String answer1, String answer2, String answer3, String answer4, String correct_answer, String question_image){
        this.id = id;
        this.location = location;
        this.questionTag = questionTag;
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.correct_answer = correct_answer;
        this.question_image = question_image;
    }

    public Question(String location, String questionTag, String question, String answer1, String answer2, String answer3, String answer4, String correct_answer, String question_image){
        this.location = location;
        this.question = question;
        this.questionTag = questionTag;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.correct_answer = correct_answer;
        this.question_image = question_image;
    }

    public int getID(){
        return this.id;
    }
    public String getQuestion(){
        return this.question;
    }
    public String getQuestionTag(){
        return this.questionTag;
    }
    public String getAnswer1(){
        return this.answer1;
    }
    public String getAnswer2(){
        return this.answer2;
    }
    public String getAnswer3(){
        return this.answer3;
    }
    public String getAnswer4(){
        return this.answer4;
    }
    public String getCorrectAnswer(){
        return this.correct_answer;
    }
    public String getQuestionImage(){
        return this.question_image;
    }

    public void setID(int id){
        this.id = id;
    }
    public void setQuestion(String item){
        this.question = item;
    }
    public void setQuestionTag(String item){
        this.questionTag = item;
    }
    public void setAnswer1(String item){
        this.answer1 = item;
    }
    public void setAnswer2(String item){
        this.answer2 = item;
    }
    public void setAnswer3(String item){
        this.answer3 = item;
    }
    public void setAnswer4(String item){
        this.answer4 = item;
    }
    public void setCorrectAnswer(String item){
        this.correct_answer = item;
    }
    public void setQuestionImage(String item){
        this.question_image = item;
    }

}
