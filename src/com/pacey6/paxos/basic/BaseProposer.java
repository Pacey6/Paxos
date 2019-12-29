package com.pacey6.paxos.basic;

/**
 * Created by pacey6 on 2019-12-28.
 * Homepage:http://pacey6.com/
 * Mail:support@pacey6.com
 */
public abstract class BaseProposer implements Proposer {
    private Config config;
    private Acceptor acceptor;
    private Learner learner;

    public BaseProposer(Config config, Acceptor acceptor, Learner learner) {
        this.config = config;
        this.acceptor = acceptor;
        this.learner = learner;
    }

    public void propose(String question, String answer) {
        Proposal proposal = getProposal(question);
        int number = createNumber(null == proposal ? 0 : proposal.getNumber());
        propose(number, question, answer);
    }

    @Override
    public void onPromised(int number, String question, int acceptedNumber, String acceptedAnswer) {
        Proposal proposal = getProposal(question);
        if (null != proposal && proposal.getNumber() == number) {
            proposal.setPromisedCount(proposal.getPromisedCount() + 1);
            if (null != acceptedAnswer && acceptedNumber > proposal.getInheritedNumber()) {
                proposal.setInheritedNumber(acceptedNumber);
                proposal.setAnswer(acceptedAnswer);
            }
            if (config.getMajority() == proposal.getPromisedCount()) {
                acceptor.onCommitted(number, question, proposal.getAnswer());
            }
        }
    }

    @Override
    public void onAccepted(int number, String question) {
        Proposal proposal = getProposal(question);
        if (null != proposal && proposal.getNumber() == number) {
            proposal.setAcceptedCount(proposal.getAcceptedCount() + 1);
            if (config.getMajority() == proposal.getAcceptedCount()) {
                learner.onChosen(question, proposal.getAnswer());
            }
        }
    }

    @Override
    public void onRejected(int number, String question, int promisedNumber) {
        Proposal proposal = getProposal(question);
        if (null != proposal && proposal.getNumber() == number) {
            propose(createNumber(promisedNumber), question, proposal.getAnswer());
        }
    }

    public abstract Proposal getProposal(String question);

    public abstract void setProposal(Proposal proposal);

    private int createNumber(int lastNumber) {
        int number = lastNumber - lastNumber % config.getTotal() + config.getNo();
        if (number <= lastNumber) number += config.getTotal();
        return number;
    }

    private void propose(int number, String question, String answer) {
        Proposal proposal = new Proposal();
        proposal.setNumber(number);
        proposal.setQuestion(question);
        proposal.setAnswer(answer);
        setProposal(proposal);
        acceptor.onPrepared(number, question);
    }
}
