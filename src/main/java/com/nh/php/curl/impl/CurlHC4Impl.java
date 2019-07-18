package com.nh.php.curl.impl;


import com.nh.php.curl.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.SchemePortResolver;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.nio.charset.Charset;
import java.security.*;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * Created by ninghao on 2019/6/24.
 */
public class CurlHC4Impl extends CurlLib {

    private static Logger logger = LoggerFactory.getLogger(CurlHC4Impl.class);

    @Override
    public String curl_version() {
        return "httpClient4.5";
    }

    public HttpClientConnectionManager httpClientConnectionManager = null;

    @Override
    public Pointer curl_init() {
        HC4Pointer pointer = new HC4Pointer();
        return pointer;
    }

    @Override
    public void curl_close(Pointer curl) {
        HC4Pointer pointer = (HC4Pointer) curl;
        if (pointer.getHttpClient() != null) {
            try {
                pointer.getHttpClient().close();
                pointer.setHttpClient(null);
            } catch (IOException e) {
                logger.error("curl_close error", e);
            }
        }
    }

    public HttpClientConnectionManager getPoolInstance(HC4Pointer pointer) {

        if (httpClientConnectionManager != null) {
            return httpClientConnectionManager;
        }
        synchronized (this) {
            if (httpClientConnectionManager != null) {
                return httpClientConnectionManager;
            }
            httpClientConnectionManager = createPool(pointer);
            return httpClientConnectionManager;
        }

    }

    private PoolingHttpClientConnectionManager createPool(HC4Pointer pointer) {
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = null;
        Registry<ConnectionSocketFactory> registryBuilder = null;
        try {
            boolean checkSsl = false;
            if (pointer.CURLOPT_SSL_VERIFYPEER != null || pointer.CURLOPT_SSL_VERIFYHOST != null) {
                checkSsl = true;
            }
            if (checkSsl == true) {

                SSLContext sslContext = getSSLContext(pointer);
                HostnameVerifier hostFlag = NoopHostnameVerifier.INSTANCE;
                if (pointer.CURLOPT_SSL_VERIFYHOST != null && pointer.CURLOPT_SSL_VERIFYHOST == true) {
                    hostFlag = new DefaultHostnameVerifier();
                }
                SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new String[]{"SSLv2Hello", "SSLv3", "TLSv1","TLSv1.1", "TLSv1.2"}, null, hostFlag);
                registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("HTTP", new PlainConnectionSocketFactory())
                        .register("HTTPS", sslConnectionSocketFactory)
                        .build();
            } else {
                registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("HTTP", new PlainConnectionSocketFactory())
                        .build();
            }

            if (pointer.JAVA_POOL_TIMETOLIVE != null) {
                poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(registryBuilder, (HttpConnectionFactory) null, (SchemePortResolver) null, null, pointer.JAVA_POOL_TIMETOLIVE, TimeUnit.MILLISECONDS);
            } else {
                poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(registryBuilder);
            }
            if (pointer.JAVA_POOL_MAXTOTAL != null) {
                poolingHttpClientConnectionManager.setMaxTotal(pointer.JAVA_POOL_MAXTOTAL);
                poolingHttpClientConnectionManager.setDefaultMaxPerRoute(pointer.JAVA_POOL_MAXTOTAL);
            }
            if (pointer.JAVA_POOL_MAXPERROUTE != null) {
                poolingHttpClientConnectionManager.setDefaultMaxPerRoute(pointer.JAVA_POOL_MAXPERROUTE);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return poolingHttpClientConnectionManager;
    }

    private SSLContext getSSLContext(HC4Pointer pointer) {
        try {
            SSLContext sslContext = null;
            KeyManager[] keyManagers = null;
            boolean checkKstore = false;
            if (pointer.CURLOPT_SSLKEY != null) {
                checkKstore = true;
            }
            if (checkKstore == true) {
                KeyStore kstore = null;
                if (pointer.CURLOPT_SSLKEYTYPE != null) {
                    kstore = KeyStore.getInstance(pointer.CURLOPT_SSLKEYTYPE);
                } else {
                    kstore = KeyStore.getInstance("PKCS12");
                }

                String sslKeyStorePassword = "";
                if (pointer.CURLOPT_SSLKEYPASSWD != null) {
                    sslKeyStorePassword = pointer.CURLOPT_SSLKEYPASSWD;
                }
                KeyManagerFactory keyFactory = KeyManagerFactory.getInstance("sunx509");
                String sslKeyStorePath = "";
                sslKeyStorePath = pointer.CURLOPT_SSLKEY;
                kstore.load(new FileInputStream(sslKeyStorePath), sslKeyStorePassword.toCharArray());
                keyFactory.init(kstore, sslKeyStorePassword.toCharArray());
                keyManagers = keyFactory.getKeyManagers();
            }

            TrustManager[] tm = null;
            if (pointer.CURLOPT_SSL_VERIFYPEER != null) {
                MyX509TrustManager trustManager = new MyX509TrustManager();
                trustManager.setCheckFlag(pointer.CURLOPT_SSL_VERIFYPEER);
                trustManager.setCerPath(pointer.CURLOPT_SSLCERT);
                tm = new TrustManager[]{trustManager};
            }

            String sslVersion = "TLS";
            if (pointer.CURLOPT_SSLVERSION != null) {
                sslVersion = pointer.CURLOPT_SSLVERSION;
            }
            sslContext = SSLContext.getInstance(sslVersion);
            sslContext.init(keyManagers, tm, null);
            return sslContext;
        } catch (Exception e) {
            logger.error("create SSLContext error", e);
            return null;
        }

    }

    private void setHttpVersion(Pointer curl, HttpRequestBase http) {
        if (curl.CURLOPT_HTTP_VERSION != null) {

            ProtocolVersion version = HttpVersion.HTTP_1_1;
            if (curl.CURLOPT_HTTP_VERSION.equals("1.0")) {
                version = HttpVersion.HTTP_1_0;
            }
            if (curl.CURLOPT_HTTP_VERSION.equals("1.1")) {
                version = HttpVersion.HTTP_1_1;
            }
            http.setProtocolVersion(version);
        }
    }

    private void setHttpHeader(Pointer curl, HttpRequestBase http) {
        if (curl.CURLOPT_HTTPHEADER != null) {
            if (curl.CURLOPT_HTTPHEADER instanceof Map) {
                Map temp = (Map) curl.CURLOPT_HTTPHEADER;
                Set<Map.Entry> eset = temp.entrySet();
                for (Map.Entry entry : eset) {
                    http.addHeader((String) entry.getKey(), (String) entry.getValue());
                }
            } else if (curl.CURLOPT_HTTPHEADER instanceof List) {
                List<String> temp = (List<String>) curl.CURLOPT_HTTPHEADER;
                for (String row : temp) {
                    int start = row.indexOf(":");
                    if (start > 0) {
                        String key = row.substring(0, start);
                        String val = row.substring(start + 1);
                        http.addHeader(key, val);
                    }
                }
            }
        }
    }

    private String getRespCharset(HttpResponse httpResponse) {

        String charset = "utf-8";
        ContentType respCT = ContentType.getOrDefault(httpResponse.getEntity());
        if (respCT == null) {
            Charset ch = respCT.getCharset();
            if (ch != null) {
                charset = ch.toString();
            }
        }
        return charset;
    }

    @Override
    public Object curl_exec(Pointer curl) {
        CloseableHttpClient client = null;
        try {
            HC4Pointer pointer = (HC4Pointer) curl;

            //all in pool
            HttpClientBuilder clientBuilder = HttpClients.custom();
            clientBuilder.setConnectionManager(getPoolInstance(pointer));
            clientBuilder.setConnectionManagerShared(true);

            client = clientBuilder.build();

            //保留指针
            pointer.setHttpClient(client);

            //获取configbuilder对象
            RequestConfig.Builder configBuilder = RequestConfig.custom();
            //建立连接时间
            if (curl.CURLOPT_CONNECTTIMEOUT != null) {
                configBuilder.setConnectTimeout(Integer.valueOf(curl.CURLOPT_CONNECTTIMEOUT));
            }
            //请求时间
            if (curl.CURLOPT_TIMEOUT != null) {
                configBuilder.setSocketTimeout(Integer.valueOf(curl.CURLOPT_TIMEOUT));
            }

            //连接池获取连接时间
            if (curl.JAVA_POOLCONN_TIMEOUT != null) {
                configBuilder.setConnectionRequestTimeout(Integer.valueOf(curl.JAVA_POOLCONN_TIMEOUT));
            }

            //跳转设置
            if (curl.CURLOPT_FOLLOWLOCATION != null) {
                configBuilder.setRedirectsEnabled(curl.CURLOPT_FOLLOWLOCATION);
                if (curl.CURLOPT_MAXREDIRS != null) {
                    configBuilder.setMaxRedirects(curl.CURLOPT_MAXREDIRS);
                }
            }
            //创建requestconfig
            RequestConfig requestConfig = configBuilder.build();
            //post请求
            if ((curl.CURLOPT_POST != null && curl.CURLOPT_POST == true && curl.CURLOPT_CUSTOMREQUEST == null) || "POST".equals(curl.CURLOPT_CUSTOMREQUEST)) {
                //设置url
                String url = "";
                if (curl.CURLOPT_URL != null) {
                    url = curl.CURLOPT_URL;
                }
                HttpPost httpPost = new HttpPost(url);
                httpPost.setConfig(requestConfig);

                //设置http版本
                setHttpVersion(curl, httpPost);

                //设置postdata
                if (curl.CURLOPT_POSTFIELDS != null) {
                    //string类型参数
                    if (curl.CURLOPT_POSTFIELDS instanceof String) {
                        HttpEntity strEntity = new StringEntity((String) curl.CURLOPT_POSTFIELDS, ContentType.create("application/x-www-form-urlencoded", "UTF-8"));
                        httpPost.setEntity(strEntity);
                    } else if (curl.CURLOPT_POSTFIELDS instanceof Map) {//map类型参数
                        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                        Map map = (Map) curl.CURLOPT_POSTFIELDS;
                        if (map != null) {
                            Iterator iter = map.entrySet().iterator();
                            while (iter.hasNext()) {
                                Map.Entry entry = (Map.Entry) iter.next();
                                String key = (String) entry.getKey();
                                Object value = entry.getValue();
                                if (value instanceof String) {
                                    multipartEntityBuilder.addTextBody(key, (String) value);
                                } else if (value instanceof File) {
                                    multipartEntityBuilder.addBinaryBody(key, (File) value);
                                } else if (value instanceof InputStream) {
                                    multipartEntityBuilder.addBinaryBody(key, (InputStream) value);
                                } else if (value instanceof byte[]) {
                                    multipartEntityBuilder.addBinaryBody(key, (byte[]) value);
                                }
                            }
                        }
                        HttpEntity multiEntity = multipartEntityBuilder.build();
                        httpPost.setEntity(multiEntity);
                    }
                }

                //设置httpheader
                setHttpHeader(curl, httpPost);

                //执行http请求
                CloseableHttpResponse httpResponse = null;
                try {
                    httpResponse = client.execute(httpPost);
                    pointer.info.httpCode = httpResponse.getStatusLine().getStatusCode();
                    pointer.info.contentType = httpResponse.getEntity().getContentType().getValue();
                    String charset = getRespCharset(httpResponse);
                    if (pointer.JAVA_RET_STREAM == null || pointer.JAVA_RET_STREAM == false) {
                        String responseMsg = EntityUtils.toString(httpResponse.getEntity(), charset);
                        return responseMsg;
                    } else {
                        return httpResponse.getEntity().getContent();
                    }
                } catch (Exception e) {
                    logger.error("client execute error", e);
                    pointer.ERROR_CODE = CurlCode.CURLE_OBSOLETE16;
                    return Boolean.valueOf(false);
                } finally {
                    if (pointer.JAVA_RET_STREAM == null || pointer.JAVA_RET_STREAM == false) {
                        if (httpResponse != null) {
                            httpResponse.close();
                        }
                    }
                }

            } else {//get请求
                String url = "";
                if (curl.CURLOPT_URL != null) {
                    url = curl.CURLOPT_URL;
                }
                HttpGet httpGet = new HttpGet(url);
                httpGet.setConfig(requestConfig);
                //设置http版本
                setHttpVersion(curl, httpGet);
                //设置content-type
                httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
                //设置httpheader
                setHttpHeader(curl, httpGet);
                CloseableHttpResponse httpResponse = null;
                try {
                    httpResponse = client.execute(httpGet);
                    pointer.info.httpCode = httpResponse.getStatusLine().getStatusCode();
                    pointer.info.contentType = httpResponse.getEntity().getContentType().getValue();
                    String charset = getRespCharset(httpResponse);

                    if (pointer.JAVA_RET_STREAM == null || pointer.JAVA_RET_STREAM == false) {
                        String responseMsg = EntityUtils.toString(httpResponse.getEntity(), charset);
                        return responseMsg;
                    } else {
                        return httpResponse.getEntity().getContent();
                    }

                } catch (Exception e) {
                    logger.error("client execute error", e);
                    pointer.ERROR_CODE = CurlCode.CURLE_OBSOLETE16;
                    return Boolean.valueOf(false);
                } finally {
                    if (pointer.JAVA_RET_STREAM == null || pointer.JAVA_RET_STREAM == false) {
                        httpResponse.close();
                    }
                }
            }
        } catch (Exception e1) {
            if (client != null) {
                try {
                    client.close();
                    client = null;
                } catch (IOException e) {
                    logger.error("close client error", e);
                }
            }
        } finally {
            if (curl.JAVA_RET_STREAM == null || curl.JAVA_RET_STREAM == false) {
                if (client != null) {
                    try {
                        client.close();
                        client = null;
                    } catch (IOException e) {
                        logger.error("close client error", e);
                    }
                }
            }
        }
        return Boolean.valueOf(false);
    }


}
