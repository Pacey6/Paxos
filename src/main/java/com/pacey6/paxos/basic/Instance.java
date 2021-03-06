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
public class Instance<Q, A> {
    private int promisedNumber;
    private int acceptedNumber;
    private Q question;
    private A acceptedAnswer;

    public int getPromisedNumber() {
        return promisedNumber;
    }

    public void setPromisedNumber(int promisedNumber) {
        this.promisedNumber = promisedNumber;
    }

    public int getAcceptedNumber() {
        return acceptedNumber;
    }

    public void setAcceptedNumber(int acceptedNumber) {
        this.acceptedNumber = acceptedNumber;
    }

    public Q getQuestion() {
        return question;
    }

    public void setQuestion(Q question) {
        this.question = question;
    }

    public A getAcceptedAnswer() {
        return acceptedAnswer;
    }

    public void setAcceptedAnswer(A acceptedAnswer) {
        this.acceptedAnswer = acceptedAnswer;
    }
}
