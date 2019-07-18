package com.nh.php.curl;


import java.util.Map;
import java.util.Set;

public abstract class CurlLib {


    abstract public String curl_version();

    public Pointer curl_init() {
        return new Pointer();
    }

    public void curl_easy_reset(Pointer curl) {
        curl.reset();
    }

    //Pointer curl_easy_duphandle(Pointer curl);

    abstract public void curl_close(Pointer curl);

    public boolean curl_setopt_array(Pointer curl, Map options) {
        Set<Map.Entry> entrySet = options.entrySet();
        for (Map.Entry entry : entrySet) {
            curl_setopt(curl, (Integer) entry.getKey(), entry.getValue());
        }
        return true;
    }

    public boolean curl_setopt(Pointer curl, int option, Object obj) {
        if (option == CurlOption.CURLOPT_CONNECTTIMEOUT) {
            curl.CURLOPT_CONNECTTIMEOUT = (Integer) obj;
        } else if (option == CurlOption.CURLOPT_TIMEOUT) {
            curl.CURLOPT_TIMEOUT = (Integer) obj;
        } else if (option == CurlOption.CURLOPT_HTTP_VERSION) {
            curl.CURLOPT_HTTP_VERSION = (String) obj;
        } else if (option == CurlOption.CURLOPT_RETURNTRANSFER) {

        } else if (option == CurlOption.CURLOPT_POST) {
            curl.CURLOPT_POST = (Boolean) obj;
        } else if (option == CurlOption.CURLOPT_URL) {
            curl.CURLOPT_URL = (String) obj;
        } else if (option == CurlOption.CURLOPT_POSTFIELDS) {
            curl.CURLOPT_POSTFIELDS = obj;
        } else if (option == CurlOption.CURLOPT_SSL_VERIFYPEER) {
            curl.CURLOPT_SSL_VERIFYPEER = (Boolean) obj;
        } else if (option == CurlOption.CURLOPT_SSLCERT) {
            curl.CURLOPT_SSLCERT = (String) obj;
        } else if (option == CurlOption.CURLOPT_HTTPHEADER) {
            curl.CURLOPT_HTTPHEADER = obj;
        } else if (option == CurlOption.CURLOPT_FOLLOWLOCATION) {
            curl.CURLOPT_FOLLOWLOCATION = (Boolean) obj;
        } else if (option == CurlOption.CURLOPT_MAXREDIRS) {
            curl.CURLOPT_MAXREDIRS = (Integer) obj;
        } else if (option == CurlOption.CURLOPT_CUSTOMREQUEST) {
            curl.CURLOPT_CUSTOMREQUEST = (String) obj;
        }

        //java
        else if (option == CurlOption.JAVA_RET_STREAM) {
            curl.JAVA_RET_STREAM = (Boolean) obj;
        } else if (option == CurlOption.JAVA_POOLCONN_TIMEOUT) {
            curl.JAVA_POOLCONN_TIMEOUT = (Integer) obj;
        } else if (option == CurlOption.JAVA_POOL_MAXTOTAL) {
            curl.JAVA_POOL_MAXTOTAL = (Integer) obj;
        } else if (option == CurlOption.JAVA_POOL_MAXPERROUTE) {
            curl.JAVA_POOL_MAXPERROUTE = (Integer) obj;
        } else if (option == CurlOption.JAVA_POOL_TIMETOLIVE) {
            curl.JAVA_POOL_TIMETOLIVE = (Integer) obj;
        }
        return true;
    }

    public Object curl_getinfo(Pointer curl, int info) {
        if (info == CurlInfo.CURLINFO_HTTP_CODE) {
            return curl.info.httpCode;
        }
        if (info == CurlInfo.CURLINFO_CONTENT_TYPE) {
            return curl.info.contentType;
        }
        return null;
    }

    public Object curl_getinfo(Pointer curl) {
        if (curl == null) {
            return null;
        }
        return curl.info;
    }

    public Integer curl_errno(Pointer curl) {
        return curl.ERROR_CODE;
    }

    public String curl_error(Pointer curl) {
        return curl.ERROR_MSG;
    }

    public abstract Object curl_exec(Pointer curl);

}
