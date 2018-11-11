package com.mustr.common.utils;

import java.util.HashMap;

public class Res extends HashMap<String, Object> {
    private static final long serialVersionUID = -8788542805169597461L;

    public Res() {
    }

    public static Res succ() {
        return res("0", "操作成功!");
    }

    public static Res error() {
        return res("1", "操作失败!");
    }

    public static Res error(String msg) {
        return res("500", msg);
    }

    public static Res msg(String msg) {
        Res r = new Res();
        r.put("msg", msg);
        return r;
    }

    public static Res res(String code, String msg) {
        Res r = new Res();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    @Override
    public Object put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
