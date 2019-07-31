package com.nh.php.curl.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ninghao on 2019/6/28.
 */
public class CertManager {
    private static Logger logger = LoggerFactory.getLogger(CertManager.class);

    public static Map showCertInfo(X509Certificate oCert) {
        Map infoMap = new HashMap();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        String info = null;
        //获得证书版本
        info = String.valueOf(oCert.getVersion());
        infoMap.put("version", info);

        //获得证书序列号
        info = oCert.getSerialNumber().toString(16);
        infoMap.put("serialNumber", info);

        //获得证书有效期
        Date beforedate = oCert.getNotBefore();
        info = dateformat.format(beforedate);
        infoMap.put("beforedate", info);

        Date afterdate = oCert.getNotAfter();
        infoMap.put("afterdate", info);
        info = dateformat.format(afterdate);

        //获得证书主体信息
        info = oCert.getSubjectDN().getName();
        infoMap.put("subjectDN", info);

        //获得证书颁发者信息
        info = oCert.getIssuerDN().getName();
        infoMap.put("issuerDN", info);

        //获得证书签名算法名称
        info = oCert.getSigAlgName();
        infoMap.put("sigAlgName", info);
        return infoMap;
    }

    public static Map showCertInfo4file(String cerPath) {
        Map infoMap = new HashMap();
        InputStream inStream = null;
        try {
            //读取证书文件
            URL url = CertManager.class.getClassLoader().getResource(cerPath);
            URI uri = url.toURI();

            File file = new File(uri);
            inStream = new FileInputStream(file);
            //创建X509工厂类
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            //创建证书对象
            X509Certificate oCert = (X509Certificate) cf.generateCertificate(inStream);
            inStream.close();
            infoMap = showCertInfo(oCert);

        } catch (Exception e) {
            logger.error("show cert error", e);
        } finally {
            if(inStream!=null){
                try {
                    inStream.close();
                    inStream=null;
                } catch (IOException e) {
                    logger.error("show cert error", e);
                }
            }
        }
        return infoMap;
    }

}
