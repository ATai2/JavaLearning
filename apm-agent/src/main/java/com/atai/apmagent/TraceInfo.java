package com.atai.apmagent;

public class TraceInfo {
    long time;
    Object[] args;

    public TraceInfo(long l, Object[] args) {
        this.time=l;
        this.args = args;
    }
}
