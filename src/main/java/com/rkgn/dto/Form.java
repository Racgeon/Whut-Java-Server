package com.rkgn.dto;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;

public class Form {
    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this, JSONConfig.create().setIgnoreNullValue(true));
    }
}
