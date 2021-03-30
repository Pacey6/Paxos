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

import com.pacey6.paxos.basic.Acceptor;

/**
 * Created by pacey6 on 3/20/21.
 * Homepage:https://pacey6.com/
 * Mail:support@pacey6.com
 */
public class AcceptorDeserializer<Q, A> implements Deserializer<Q, A> {
    private final Acceptor<Q, A> acceptor;

    public AcceptorDeserializer(Acceptor<Q, A> acceptor) {
        this.acceptor = acceptor;
    }

    @Override
    public boolean handle(Packet<Q, A> packet) {
        switch (packet.getEvent()) {
            case PREPARED:
                if (null != acceptor) {
                    acceptor.onPrepared(packet.getNumber(), packet.getQuestion());
                }
                return true;
            case COMMITTED:
                if (null != acceptor) {
                    acceptor.onCommitted(packet.getNumber(), packet.getQuestion(), packet.getAnswer());
                }
                return true;
            default:
                return false;
        }
    }
}
