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

package com.pacey6.android.paxos;

import android.content.Context;

import androidx.annotation.NonNull;

import com.pacey6.paxos.basic.Acceptor;
import com.pacey6.paxos.basic.Config;
import com.pacey6.paxos.basic.Learner;
import com.pacey6.paxos.basic.Proposer;
import com.pacey6.paxos.converter.Factory;

import java.io.IOException;
import java.net.InetAddress;

/**
 * @author Pacey Lau
 */
public class AndroidPaxos extends MulticastPaxos<String, String> {
    private AndroidProposer proposer;
    private AndroidAcceptor acceptor;

    protected AndroidPaxos(@NonNull Context context, @NonNull InetAddress address, int port) throws IOException {
        super(address, port);
        acceptor.init(context);
    }

    @Override
    public Factory createFactory() {
        return new GsonFactory();
    }

    @Override
    public Proposer<String, String> createProposer(Acceptor<String, String> acceptor, Learner<String, String> learner) {
        Config config = new Config.Builder()
                .no(0)
                .proposerCount(1)
                .acceptorCount(1)
                .build();
        return proposer = new AndroidProposer(config, acceptor, learner);
    }

    @Override
    public Acceptor<String, String> createAcceptor(Proposer<String, String> proposer) {
        return acceptor = new AndroidAcceptor(proposer);
    }

    @Override
    public Learner<String, String> createLearner() {
        return new AndroidLearner();
    }

    public void propose(@NonNull String question, @NonNull String answer) {
        if (null != proposer) {
            proposer.propose(question, answer);
        }
    }
}
