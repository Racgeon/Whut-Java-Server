package com.rkgn.utils;

import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;

public class IdUtils {
    private static final IdGeneratorOptions options;

    static {
        options = new IdGeneratorOptions();
        YitIdHelper.setIdGenerator(options);
    }

    public static long nextId() {
        return YitIdHelper.nextId();
    }
}
