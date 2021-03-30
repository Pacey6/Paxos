/*
 * MIT License
 *
 * Copyright (c) 2021 Pacey Lau
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

package com.pacey6.paxos;

/**
 * Created by pacey6 on 3/20/21.
 * Homepage:https://pacey6.com/
 * Mail:support@pacey6.com
 */
public class Packet<Q, A> {
    private Event event;
    private Integer number;
    private Q question;
    private A answer;
    private Integer promisedNumber;
    private Integer acceptedNumber;
    private A acceptedAnswer;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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

    public Integer getPromisedNumber() {
        return promisedNumber;
    }

    public void setPromisedNumber(Integer promisedNumber) {
        this.promisedNumber = promisedNumber;
    }

    public Integer getAcceptedNumber() {
        return acceptedNumber;
    }

    public void setAcceptedNumber(Integer acceptedNumber) {
        this.acceptedNumber = acceptedNumber;
    }

    public A getAcceptedAnswer() {
        return acceptedAnswer;
    }

    public void setAcceptedAnswer(A acceptedAnswer) {
        this.acceptedAnswer = acceptedAnswer;
    }

    public enum Event {
        PREPARED, PROMISED, COMMITTED, ACCEPTED, REJECTED, CHOSEN
    }
}
