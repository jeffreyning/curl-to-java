# curl-to-java
php curl lib for java implement
php curl类库的java版实现
支持php curl类库的以下方法
```
curl_init
curl_setopt
curl_exec
curl_getinfo
curl_errno
curl_error
```

其中设置curl_setopt时支持以下参数，含义与php中一致
```
CURLOPT_CONNECTTIMEOUT
CURLOPT_TIMEOUT
CURLOPT_HTTP_VERSION
CURLOPT_RETURNTRANSFER
CURLOPT_POST
CURLOPT_URL
CURLOPT_POSTFIELDS
CURLOPT_SSL_VERIFYPEER
CURLOPT_SSLCERT
CURLOPT_HTTPHEADER
CURLOPT_FOLLOWLOCATION
CURLOPT_MAXREDIRS
CURLOPT_CUSTOMREQUEST
```

根据java特性拓展了curl_setopt的以下参数
```
JAVA_RET_STREAM //返回java的stream对象
JAVA_POOLCONN_TIMEOUT //httpclient连接池获取连接的超时时间
JAVA_POOL_MAXTOTAL //httpclient连接池最大连接数
JAVA_POOL_MAXPERROUTE
JAVA_POOL_TIMETOLIVE //httpclient连接池中连接有效时间
```

demo示例
```
    public Object curl(String url, Object postData, String method) {

        CurlLib curl = CurlFactory.getInstance("default");
        ch = curl.curl_init();
        curl.curl_setopt(ch, CurlOption.CURLOPT_CONNECTTIMEOUT, 1000);//建立连接超时时间s
        curl.curl_setopt(ch, CurlOption.CURLOPT_TIMEOUT, 5000);//请求超时时间s
        curl.curl_setopt(ch, CurlOption.CURLOPT_SSL_VERIFYPEER, false);//不检查https证书
        curl.curl_setopt(ch, CurlOption.CURLOPT_SSL_VERIFYHOST, false);//不检查https证书
        String postDataStr = "key1=v1";

        curl.curl_setopt(ch, CurlOption.CURLOPT_CUSTOMREQUEST, "POST");
        curl.curl_setopt(ch, CurlOption.CURLOPT_POSTFIELDS, postDataStr);
        curl.curl_setopt(ch, CurlOption.CURLOPT_URL, "https://xxxx.com/yyy");
        Object html = curl.curl_exec(ch);
        Object httpCode = curl.curl_getinfo(ch, CurlInfo.CURLINFO_HTTP_CODE);
        if (httpCode != null && 200 == Integer.valueOf(httpCode.toString())) {
            return null;
        }
        return html;
    }
```