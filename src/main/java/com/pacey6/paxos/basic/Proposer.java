package com.pacey6.paxos.basic;

/**
 * Created by pacey6 on 2019-12-28.
 * Homepage:http://pacey6.com/
 * Mail:support@pacey6.com
 */
public interface Proposer<Q, A> {
    void onPromised(int number, Q question, int acceptedNumber, A acceptedAnswer);
    void onAccepted(int number, Q question);
    void onRejected(int number, Q question, int promisedNumber);
}
