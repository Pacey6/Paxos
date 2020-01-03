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
public abstract class BaseProposer<Q, A> implements Proposer<Q, A> {
    private Config config;
    private Acceptor<Q, A> acceptor;
    private Learner<Q, A> learner;

    public BaseProposer(Config config, Acceptor<Q, A> acceptor, Learner<Q, A> learner) {
        this.config = config;
        this.acceptor = acceptor;
        this.learner = learner;
    }

    public void propose(Q question, A answer) {
        Proposal<Q, A> proposal = getProposal(question);
        int number = createNumber(null == proposal ? 0 : proposal.getNumber());
        propose(number, question, answer);
    }

    @Override
    public void onPromised(int number, Q question, int acceptedNumber, A acceptedAnswer) {
        Proposal<Q, A> proposal = getProposal(question);
        if (null != proposal && proposal.getNumber() == number) {
            proposal.setPromisedBallot(proposal.getPromisedBallot() + 1);
            if (null != acceptedAnswer && acceptedNumber > proposal.getInheritedNumber()) {
                proposal.setInheritedNumber(acceptedNumber);
                proposal.setAnswer(acceptedAnswer);
            }
            if (config.getMajority() == proposal.getPromisedBallot()) {
                acceptor.onCommitted(number, question, proposal.getAnswer());
            }
        }
    }

    @Override
    public void onAccepted(int number, Q question) {
        Proposal<Q, A> proposal = getProposal(question);
        if (null != proposal && proposal.getNumber() == number) {
            proposal.setAcceptedBallot(proposal.getAcceptedBallot() + 1);
            if (config.getMajority() <= proposal.getPromisedBallot() &&
                    config.getMajority() == proposal.getAcceptedBallot()) {
                learner.onChosen(question, proposal.getAnswer());
            }
        }
    }

    @Override
    public void onRejected(int number, Q question, int promisedNumber) {
        Proposal<Q, A> proposal = getProposal(question);
        if (null != proposal && proposal.getNumber() == number) {
            propose(createNumber(promisedNumber), question, proposal.getAnswer());
        }
    }

    public abstract Proposal<Q, A> getProposal(Q question);

    public abstract void setProposal(Proposal<Q, A> proposal);

    private int createNumber(int lastNumber) {
        int number = lastNumber - lastNumber % config.getTotal() + config.getNo();
        if (number <= lastNumber) number += config.getTotal();
        return number;
    }

    private void propose(int number, Q question, A answer) {
        Proposal<Q, A> proposal = new Proposal<>();
        proposal.setNumber(number);
        proposal.setQuestion(question);
        proposal.setAnswer(answer);
        setProposal(proposal);
        acceptor.onPrepared(number, question);
    }
}
