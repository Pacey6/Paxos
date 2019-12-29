package com.pacey6.paxos.basic;

/**
 * Created by pacey6 on 2019-12-28.
 * Homepage:http://pacey6.com/
 * Mail:support@pacey6.com
 */
public class Proposal {
    private int number;
    private int inheritedNumber;
    private int promisedCount;
    private int acceptedCount;
    private String question;
    private String answer;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getInheritedNumber() {
        return inheritedNumber;
    }

    public void setInheritedNumber(int inheritedNumber) {
        this.inheritedNumber = inheritedNumber;
    }

    public int getPromisedCount() {
        return promisedCount;
    }

    public void setPromisedCount(int promisedCount) {
        this.promisedCount = promisedCount;
    }

    public int getAcceptedCount() {
        return acceptedCount;
    }

    public void setAcceptedCount(int acceptedCount) {
        this.acceptedCount = acceptedCount;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
