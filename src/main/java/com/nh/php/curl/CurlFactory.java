package com.nh.php.curl;

import com.nh.php.curl.impl.CurlHC4Impl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ninghao on 2019/6/27.
 */
public class CurlFactory {
    private static Map holdMap = new HashMap();


    public static CurlLib getInstance() {
        return getInstance("default");
    }

    public static CurlLib getInstance(String name) {
        CurlLib lib = (CurlLib) holdMap.get(name);
        if (lib != null) {
            return lib;
        }
        synchronized (CurlFactory.class) {
            if (holdMap.get(name) == null) {
                CurlLib hc4Lib = new CurlHC4Impl();
                holdMap.put(name, hc4Lib);
                return hc4Lib;

            } else {
                holdMap.put(name, null);
                return null;
            }
        }

    }
}
