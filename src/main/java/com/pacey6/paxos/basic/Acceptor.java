package com.pacey6.paxos.basic;

/**
 * Created by pacey6 on 2019-12-28.
 * Homepage:http://pacey6.com/
 * Mail:support@pacey6.com
 */
public interface Acceptor<Q, A> {
    void onPrepared(int number, Q question);
    void onCommitted(int number, Q question, A answer);
}
