package com.nh.php.curl.impl;

/**
 * Created by ninghao on 2019/6/26.
 */

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import javax.net.ssl.X509TrustManager;

public class MyX509TrustManager implements X509TrustManager {
    private Boolean checkFlag = false;
    private String cerPath = "";

    public String getCerPath() {
        return cerPath;
    }

    public void setCerPath(String cerPath) {
        this.cerPath = cerPath;
    }

    public Boolean getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(Boolean checkFlag) {
        this.checkFlag = checkFlag;
    }

    /* (non-Javadoc)
        * @see javax.net.ssl.X509TrustManager#checkClientTrusted(java.security.cert.X509Certificate[], java.lang.String)
        */
    public void checkClientTrusted(X509Certificate[] arg0, String arg1)
            throws CertificateException {

    }

    /* (non-Javadoc)
     * @see javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.X509Certificate[], java.lang.String)
     */
    public void checkServerTrusted(X509Certificate[] arg0, String arg1)
            throws CertificateException {
        if (checkFlag == null || checkFlag == false) {
            return;
        }
        Map cerMap = CertManager.showCertInfo4file(cerPath);
        if (arg0 != null) {
            for (X509Certificate cer : arg0) {
                Map tempMap = CertManager.showCertInfo(cer);
                if (tempMap.equals(cerMap)) {
                    return;
                }
            }
        }
        throw new CertificateException();

    }

    /* (non-Javadoc)
     * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
     */
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}
