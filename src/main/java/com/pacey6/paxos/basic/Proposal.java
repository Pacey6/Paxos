/*
 * MIT License
 *
 * Copyright (c) 2020 Pacey Lau
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
public class Proposal<Q, A> {
    private int number;
    private int inheritedNumber;
    private int promisedBallot;
    private int acceptedBallot;
    private Q question;
    private A answer;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getInheritedNumber() {
        return inheritedNumber;
    }

    public void setInheritedNumber(int inheritedNumber) {
        this.inheritedNumber = inheritedNumber;
    }

    public int getPromisedBallot() {
        return promisedBallot;
    }

    public void setPromisedBallot(int promisedBallot) {
        this.promisedBallot = promisedBallot;
    }

    public int getAcceptedBallot() {
        return acceptedBallot;
    }

    public void setAcceptedBallot(int acceptedBallot) {
        this.acceptedBallot = acceptedBallot;
    }

    public Q getQuestion() {
        return question;
    }

    public void setQuestion(Q question) {
        this.question = question;
    }

    public A getAnswer() {
        return answer;
    }

    public void setAnswer(A answer) {
        this.answer = answer;
    }
}
