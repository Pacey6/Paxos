package com.pacey6.paxos.basic;

/**
 * Created by pacey6 on 2019-12-28.
 * Homepage:http://pacey6.com/
 * Mail:support@pacey6.com
 */
public interface Proposer {
    void onPromised(int number, String question, int acceptedNumber, String acceptedAnswer);
    void onAccepted(int number, String question);
    void onRejected(int number, String question, int promisedNumber);
}
