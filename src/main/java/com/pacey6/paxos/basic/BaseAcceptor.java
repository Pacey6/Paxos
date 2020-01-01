package com.pacey6.paxos.basic;

/**
 * Created by pacey6 on 2019-12-28.
 * Homepage:http://pacey6.com/
 * Mail:support@pacey6.com
 */
public abstract class BaseAcceptor<Q, A> implements Acceptor<Q, A> {
    private Proposer<Q, A> proposer;

    public BaseAcceptor(Proposer<Q, A> proposer) {
        this.proposer = proposer;
    }

    @Override
    public void onPrepared(int number, Q question) {
        Subject<Q, A> subject = getSubject(question);
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
    public void onCommitted(int number, Q question, A answer) {
        Subject<Q, A> subject = getSubject(question);
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

    public abstract Subject<Q, A> getSubject(Q question);

    public abstract void setSubject(Subject<Q, A> subject);

    private Subject<Q, A> createSubject(Q question) {
        Subject<Q, A> subject = new Subject<>();
        subject.setQuestion(question);
        return subject;
    }
}
