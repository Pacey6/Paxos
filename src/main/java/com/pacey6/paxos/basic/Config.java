/*
 * Copyright (c) 2020. Pacey Lau.
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
public class Config {
    private int total;
    private int no;
    private int majority;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getMajority() {
        return majority;
    }

    public void setMajority(int majority) {
        this.majority = majority;
    }

    public static class Builder {
        private int total;
        private int no;
        private int majority;

        public Builder no(int no) {
            this.no = no;
            return this;
        }

        public Builder majority(int majority) {
            this.majority = majority;
            return this;
        }

        public Builder proposerCount(int proposerCount) {
            this.total = proposerCount;
            return this;
        }

        public Builder acceptorCount(int acceptorCount) {
            this.majority = (acceptorCount >> 1) + 1;
            return this;
        }

        public Config build() {
            if (0 >= total) throw new IllegalArgumentException("0 >= total");
            if (0 >= majority) throw new IllegalArgumentException("0 >= majority");
            Config config = new Config();
            config.setTotal(total);
            config.setNo(no % total);
            config.setMajority(majority);
            return config;
        }
    }
}
