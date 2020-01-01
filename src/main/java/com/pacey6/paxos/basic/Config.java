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
