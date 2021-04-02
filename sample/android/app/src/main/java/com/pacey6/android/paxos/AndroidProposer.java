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

import com.pacey6.paxos.basic.Acceptor;
import com.pacey6.paxos.basic.BaseProposer;
import com.pacey6.paxos.basic.Config;
import com.pacey6.paxos.basic.Learner;
import com.pacey6.paxos.basic.Proposal;
import com.pacey6.util.CacheMap;

import java.util.Map;

/**
 * @author Pacey Lau
 */
public class AndroidProposer extends BaseProposer<String, String> {
    private final Map<String, Proposal<String, String>> cacheMap = new CacheMap<>(16);

    public AndroidProposer(Config config, Acceptor<String, String> acceptor, Learner<String, String> learner) {
        super(config, acceptor, learner);
    }

    @Override
    public Proposal<String, String> getProposal(String question) {
        return cacheMap.get(question);
    }

    @Override
    public void setProposal(Proposal<String, String> proposal) {
        cacheMap.put(proposal.getQuestion(), proposal);
    }
}
