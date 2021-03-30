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
import com.pacey6.paxos.basic.Learner;
import com.pacey6.paxos.basic.Proposer;
import com.pacey6.paxos.converter.Converter;
import com.pacey6.paxos.converter.Factory;
import com.pacey6.paxos.generic.ResolvableClass;
import com.pacey6.paxos.generic.ResolvableParameterizedType;
import com.pacey6.paxos.generic.ResolvableType;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by pacey6 on 3/20/21.
 * Homepage:https://pacey6.com/
 * Mail:support@pacey6.com
 */
public abstract class Paxos<Q, A> {
    private final ProposerDeserializer<Q, A> proposerDeserializer;
    private final AcceptorDeserializer<Q, A> acceptorDeserializer;
    private final LearnerDeserializer<Q, A> learnerDeserializer;
    private final Converter<Packet<Q, A>, String> serializeConverter;
    private final Converter<String, Packet<Q, A>> deserializeConverter;

    @SuppressWarnings("unchecked")
    public Paxos() {
        Factory factory = createFactory();
        Type type = getPacketType();
        serializeConverter = (Converter<Packet<Q, A>, String>) factory.serializeConverter(type);
        deserializeConverter = (Converter<String, Packet<Q, A>>) factory.deserializeConverter(type);
        ProposerSerializer<Q, A> proposerSerializer = new ProposerSerializer<>(this);
        AcceptorSerializer<Q, A> acceptorSerializer = new AcceptorSerializer<>(this);
        LearnerSerializer<Q, A> learnerSerializer = new LearnerSerializer<>(this);
        Proposer<Q, A> proposer = createProposer(acceptorSerializer, learnerSerializer);
        Acceptor<Q, A> acceptor = createAcceptor(proposerSerializer);
        Learner<Q, A> learner = createLearner();
        proposerDeserializer = new ProposerDeserializer<>(proposer);
        acceptorDeserializer = new AcceptorDeserializer<>(acceptor);
        learnerDeserializer = new LearnerDeserializer<>(learner);
    }

    public abstract Factory createFactory();

    public abstract Proposer<Q, A> createProposer(Acceptor<Q, A> acceptor, Learner<Q, A> learner);

    public abstract Acceptor<Q, A> createAcceptor(Proposer<Q, A> proposer);

    public abstract Learner<Q, A> createLearner();

    public abstract void send(String data) throws IOException;

    public void sendPacket(Packet<Q, A> packet) {
        try {
            String data = serializeConverter.convert(packet);
            if (null != data) {
                send(data);
            }
        } catch (IOException ignored) {
            // Even though no io exception, other paxos may failed to receive message.
        }
    }

    public void onReceived(String data) {
        Packet<Q, A> body = null;
        try {
            body = deserializeConverter.convert(data);
        } catch (IOException ignored) {
            // Even though no io exception, other paxos may failed to send message.
        }
        if (null != body) {
            if (proposerDeserializer.handle(body)) return;
            if (acceptorDeserializer.handle(body)) return;
            learnerDeserializer.handle(body);
        }
    }

    private Type getPacketType() {
        ResolvableType<?> resolvableType = new ResolvableClass(getClass());
        while (null != resolvableType && !resolvableType.equalsClass(Paxos.class)) {
            resolvableType = resolvableType.getSuperType();
        }
        if (!(resolvableType instanceof ResolvableParameterizedType)) {
            throw new ClassCastException();
        }
        Type[] actualTypeArguments = ((ResolvableParameterizedType) resolvableType).getType().getActualTypeArguments();
        return new ResolvableParameterizedType(Packet.class, actualTypeArguments).getType();
    }
}
