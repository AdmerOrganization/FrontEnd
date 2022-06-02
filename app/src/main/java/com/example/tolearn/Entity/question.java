package com.example.tolearn.Entity;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class question {
    int question_num;

    String id;

    String question;

    String answer1;

    String answer2;

    String answer3;

    String answer4;

    String righ_ans;

    public question (JsonObject jsonObject)
    {
        this.id = jsonObject.get("id").toString();
        this.question = jsonObject.get("question").toString();
        String options =  jsonObject.get("options").toString();
        options = options.replace("\"","");
        options = options.replace("[","");
        options = options.replace("]","");
        options = options.replace("'","");
        String [] opstionsArr = options.split(",");
        this.answer1 = opstionsArr[0];
        this.answer2 = opstionsArr[1];
        this.answer3 = opstionsArr[2];
        this.answer4 = opstionsArr[3];
        righ_ans = jsonObject.get("correct_answer").toString();
    }

    public question(String id, String question, String answer1, String answer2, String answer3, String answer4, String righ_ans) {
        this.id = id;
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.righ_ans = righ_ans;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getRigh_ans() {
        return righ_ans;
    }

    public void setRigh_ans(String righ_ans) {
        this.righ_ans = righ_ans;
    }
}
