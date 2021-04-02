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

import androidx.annotation.NonNull;

import com.pacey6.net.MultiDatagramPacket;
import com.pacey6.net.MultiDatagramSocket;
import com.pacey6.paxos.Paxos;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * @author Pacey Lau
 */
public abstract class MulticastPaxos<Q, A> extends Paxos<Q, A> implements Closeable {
    @NonNull
    private final InetAddress address;
    private final int port;
    private final MultiDatagramSocket<MulticastSocket> multiDatagramSocket;

    protected MulticastPaxos(@NonNull InetAddress address, int port) throws IOException {
        this.address = address;
        this.port = port;
        MulticastSocket multicastSocket = new MulticastSocket(port);
        multicastSocket.joinGroup(address);
        this.multiDatagramSocket = new MultiDatagramSocket<>(multicastSocket);
    }

    @Override
    public void send(String s) throws IOException {
        this.multiDatagramSocket.send(new MultiDatagramPacket(s.getBytes(), address, port));
    }

    @Override
    public void close() throws IOException {
        this.multiDatagramSocket.getDatagramSocket().leaveGroup(address);
        this.multiDatagramSocket.close();
    }

    public void receive() throws IOException {
        MultiDatagramPacket multiDatagramPacket = this.multiDatagramSocket.receive();
        onReceived(new String(multiDatagramPacket.getData()));
    }
}
