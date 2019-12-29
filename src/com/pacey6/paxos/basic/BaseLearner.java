package com.pacey6.paxos.basic;

/**
 * Created by pacey6 on 2019-12-28.
 * Homepage:http://pacey6.com/
 * Mail:support@pacey6.com
 */
public abstract class BaseLearner implements Learner {
    @Override
    public void onChosen(String question, String answer) {
        if (null == getAnswer(question)) {
            setAnswer(question, answer);
        }
    }

    public abstract String getAnswer(String question);

    public abstract void setAnswer(String question, String answer);
}
