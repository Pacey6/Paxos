package com.pacey6.paxos.basic;

/**
 * Created by pacey6 on 2019-12-28.
 * Homepage:http://pacey6.com/
 * Mail:support@pacey6.com
 */
public abstract class BaseLearner<Q, A> implements Learner<Q, A> {
    @Override
    public void onChosen(Q question, A answer) {
        if (null == getAnswer(question)) {
            setAnswer(question, answer);
        }
    }

    public abstract A getAnswer(Q question);

    public abstract void setAnswer(Q question, A answer);
}
