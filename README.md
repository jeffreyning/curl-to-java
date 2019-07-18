# curl-to-java
php curl lib for java implement
php curl����java��ʵ��
֧��php curl�������·���
```
curl_init
curl_setopt
curl_exec
curl_getinfo
curl_errno
curl_error
```

��������curl_setoptʱ֧�����²�����������php��һ��
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

����java������չ��curl_setopt�����²���
```
JAVA_RET_STREAM //����java��stream����
JAVA_POOLCONN_TIMEOUT //httpclient���ӳػ�ȡ���ӵĳ�ʱʱ��
JAVA_POOL_MAXTOTAL //httpclient���ӳ����������
JAVA_POOL_MAXPERROUTE
JAVA_POOL_TIMETOLIVE //httpclient���ӳ���������Чʱ��
```

demoʾ��
```
    public Object curl(String url, Object postData, String method) {

        CurlLib curl = CurlFactory.getInstance("default");
        ch = curl.curl_init();
        curl.curl_setopt(ch, CurlOption.CURLOPT_CONNECTTIMEOUT, 1000);//�������ӳ�ʱʱ��s
        curl.curl_setopt(ch, CurlOption.CURLOPT_TIMEOUT, 5000);//����ʱʱ��s
        curl.curl_setopt(ch, CurlOption.CURLOPT_SSL_VERIFYPEER, false);//�����https֤��
        curl.curl_setopt(ch, CurlOption.CURLOPT_SSL_VERIFYHOST, false);//�����https֤��
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