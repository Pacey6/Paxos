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

import com.pacey6.paxos.basic.Proposer;

import static com.pacey6.paxos.Packet.Event.*;

/**
 * Created by pacey6 on 3/20/21.
 * Homepage:https://pacey6.com/
 * Mail:support@pacey6.com
 */
public class ProposerSerializer<Q, A> extends Serializer<Q, A> implements Proposer<Q, A> {
    public ProposerSerializer(Paxos<Q, A> paxos) {
        super(paxos);
    }

    @Override
    public void onPromised(int number, Q question, int acceptedNumber, A acceptedAnswer) {
        Packet<Q, A> packet = new Packet<>();
        packet.setEvent(PROMISED);
        packet.setNumber(number);
        packet.setQuestion(question);
        packet.setAcceptedNumber(acceptedNumber);
        packet.setAcceptedAnswer(acceptedAnswer);
        sendPacket(packet);
    }

    @Override
    public void onAccepted(int number, Q question) {
        Packet<Q, A> packet = new Packet<>();
        packet.setEvent(ACCEPTED);
        packet.setNumber(number);
        packet.setQuestion(question);
        sendPacket(packet);
    }

    @Override
    public void onRejected(int number, Q question, int promisedNumber) {
        Packet<Q, A> packet = new Packet<>();
        packet.setEvent(REJECTED);
        packet.setNumber(number);
        packet.setQuestion(question);
        packet.setPromisedNumber(promisedNumber);
        sendPacket(packet);
    }
}
