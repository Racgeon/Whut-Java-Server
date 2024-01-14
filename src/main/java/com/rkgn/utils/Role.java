package com.rkgn.utils;

import java.util.HashMap;

public class Role {
    public static final String ADMINISTRATOR = "administrator";
    public static final String OPERATOR = "operator";
    public static final String BROWSER = "browser";
    public static final HashMap<String, String> translateMap = new HashMap<>() {{
        put("administrator", "系统管理员");
        put("系统管理员", "administrator");
        put("browser", "档案浏览员");
        put("档案浏览员", "browser");
        put("operator", "档案录入员");
        put("档案录入员", "operator");
    }};

}
