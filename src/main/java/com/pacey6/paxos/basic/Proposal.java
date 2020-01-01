package com.pacey6.paxos.basic;

/**
 * Created by pacey6 on 2019-12-28.
 * Homepage:http://pacey6.com/
 * Mail:support@pacey6.com
 */
public class Proposal<Q, A> {
    private int number;
    private int inheritedNumber;
    private int promisedBallot;
    private int acceptedBallot;
    private Q question;
    private A answer;

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

    public int getPromisedBallot() {
        return promisedBallot;
    }

    public void setPromisedBallot(int promisedBallot) {
        this.promisedBallot = promisedBallot;
    }

    public int getAcceptedBallot() {
        return acceptedBallot;
    }

    public void setAcceptedBallot(int acceptedBallot) {
        this.acceptedBallot = acceptedBallot;
    }

    public Q getQuestion() {
        return question;
    }

    public void setQuestion(Q question) {
        this.question = question;
    }

    public A getAnswer() {
        return answer;
    }

    public void setAnswer(A answer) {
        this.answer = answer;
    }
}
