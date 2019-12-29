package com.pacey6.paxos.basic;

/**
 * Created by pacey6 on 2019-12-28.
 * Homepage:http://pacey6.com/
 * Mail:support@pacey6.com
 */
public class Subject {
    private int promisedNumber;
    private int acceptedNumber;
    private String question;
    private String acceptedAnswer;

    public int getPromisedNumber() {
        return promisedNumber;
    }

    public void setPromisedNumber(int promisedNumber) {
        this.promisedNumber = promisedNumber;
    }

    public int getAcceptedNumber() {
        return acceptedNumber;
    }

    public void setAcceptedNumber(int acceptedNumber) {
        this.acceptedNumber = acceptedNumber;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAcceptedAnswer() {
        return acceptedAnswer;
    }

    public void setAcceptedAnswer(String acceptedAnswer) {
        this.acceptedAnswer = acceptedAnswer;
    }
}
