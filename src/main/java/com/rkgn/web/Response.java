package com.rkgn.web;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Response {
    private final Socket clientSocket;
    private final PrintWriter pw;

    public Response(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            OutputStream out = clientSocket.getOutputStream();
            pw = new PrintWriter(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void fail(String errorMsg) {
        Result result = new Result(false, errorMsg, null, null);
        String resultJson = JSONUtil.toJsonStr(result, JSONConfig.create().setIgnoreNullValue(true));
        pw.println(resultJson);
        pw.flush();
    }

    public void ok() {
        Result result = new Result(true, null, null, null);
        String resultJson = JSONUtil.toJsonStr(result, JSONConfig.create().setIgnoreNullValue(true));
        pw.println(resultJson);
        pw.flush();
    }

    public void ok(Object data) {
        Result result = new Result(true, null, data, null);
        String resultJson = JSONUtil.toJsonStr(result, JSONConfig.create().setIgnoreNullValue(true));
        pw.println(resultJson);
        pw.flush();
    }
}
