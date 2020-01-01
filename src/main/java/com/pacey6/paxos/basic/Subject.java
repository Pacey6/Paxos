package com.pacey6.paxos.basic;

/**
 * Created by pacey6 on 2019-12-28.
 * Homepage:http://pacey6.com/
 * Mail:support@pacey6.com
 */
public class Subject<Q, A> {
    private int promisedNumber;
    private int acceptedNumber;
    private Q question;
    private A acceptedAnswer;

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

    public Q getQuestion() {
        return question;
    }

    public void setQuestion(Q question) {
        this.question = question;
    }

    public A getAcceptedAnswer() {
        return acceptedAnswer;
    }

    public void setAcceptedAnswer(A acceptedAnswer) {
        this.acceptedAnswer = acceptedAnswer;
    }
}
