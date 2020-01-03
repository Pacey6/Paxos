/*
 * Copyright (c) 2020. Pacey Lau.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
        Instance<Q, A> instance = getInstance(question);
        if (null == instance) instance = createInstance(question);
        if (number > instance.getPromisedNumber()) {
            instance.setPromisedNumber(number);
            setInstance(instance);
            proposer.onPromised(number, question, instance.getAcceptedNumber(), instance.getAcceptedAnswer());
        } else {
            proposer.onRejected(number, question, instance.getPromisedNumber());
        }
    }

    @Override
    public void onCommitted(int number, Q question, A answer) {
        Instance<Q, A> instance = getInstance(question);
        if (null == instance) instance = createInstance(question);
        if (number >= instance.getPromisedNumber()) {
            instance.setPromisedNumber(number);
            instance.setAcceptedNumber(number);
            instance.setAcceptedAnswer(answer);
            setInstance(instance);
            proposer.onAccepted(number, question);
        } else {
            proposer.onRejected(number, question, instance.getPromisedNumber());
        }
    }

    public abstract Instance<Q, A> getInstance(Q question);

    public abstract void setInstance(Instance<Q, A> instance);

    private Instance<Q, A> createInstance(Q question) {
        Instance<Q, A> instance = new Instance<>();
        instance.setQuestion(question);
        return instance;
    }
}
