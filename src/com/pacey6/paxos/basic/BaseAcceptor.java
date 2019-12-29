package com.pacey6.paxos.basic;

/**
 * Created by pacey6 on 2019-12-28.
 * Homepage:http://pacey6.com/
 * Mail:support@pacey6.com
 */
public abstract class BaseAcceptor implements Acceptor {
    private Proposer proposer;

    public BaseAcceptor(Proposer proposer) {
        this.proposer = proposer;
    }

    @Override
    public void onPrepared(int number, String question) {
        Subject subject = getSubject(question);
        if (null == subject) subject = createSubject(question);
        if (number > subject.getPromisedNumber()) {
            subject.setPromisedNumber(number);
            setSubject(subject);
            proposer.onPromised(number, question, subject.getAcceptedNumber(), subject.getAcceptedAnswer());
        } else {
            proposer.onRejected(number, question, subject.getPromisedNumber());
        }
    }

    @Override
    public void onCommitted(int number, String question, String answer) {
        Subject subject = getSubject(question);
        if (null == subject) subject = createSubject(question);
        if (number >= subject.getPromisedNumber()) {
            subject.setPromisedNumber(number);
            subject.setAcceptedNumber(number);
            subject.setAcceptedAnswer(answer);
            setSubject(subject);
            proposer.onAccepted(number, question);
        } else {
            proposer.onRejected(number, question, subject.getPromisedNumber());
        }
    }

    public abstract Subject getSubject(String question);

    public abstract void setSubject(Subject subject);

    private Subject createSubject(String question) {
        Subject subject = new Subject();
        subject.setQuestion(question);
        return subject;
    }
}
