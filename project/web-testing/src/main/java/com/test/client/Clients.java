package com.test.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Hashtable;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.axis.components.net.JSSESocketFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

public class Clients {

    /**
     * 
     * 使用HttpUrlConnection对象发送报文
     * @param postUrl
     * @param xmlData
     * @return byte[]
     * @throws Exception 
     * @see {@link java.net.HttpURLConnection}
     */
    public byte[] sendHttpUrlConnReq(String postUrl, byte[] xmlData) throws Exception {
        byte[] respData = null;
        HttpURLConnection conn = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            URL url = new URL(postUrl);
            conn = (HttpURLConnection)url.openConnection();
//          conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
//          conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);//如果通过post提交数据，必须设置允许对外输出数据
            
            out = conn.getOutputStream();  
            out.write(xmlData); //写入请求的字符串  
            out.flush();  
            
            respData = null;
            if(conn.getResponseCode() == 200) {
                System.out.println("---->报文请求成功，开始读取返回数据：");
                in = conn.getInputStream();
                respData = new byte[in.available()];
                in.read(respData);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (out != null) {
                out.close();
                out = null;
            }
            if (in != null) {
                in.close();
                in = null;
            }
        }
        
        return respData;
    }
    
    /**
     * 使用httpclient发送报文，4.3，4.4版本的写法
     * @param postUrl
     * @param xmlData
     * @return 
     * String
     */
    public String sendHttpClient4_3Req(String postUrl, String xmlData) {
        // 新的默认HttpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String respStrData = null;
        try {
            HttpPost httpPost = new HttpPost(postUrl);
            HttpEntity reqEntity = new StringEntity(xmlData, "UTF-8");
//            httpPost.setHeader("Content-Type", "text/xml");
            httpPost.setEntity(reqEntity);
            
//            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//            nvps.add(new BasicNameValuePair("cmtokenid", "JSHDC-ASPIRE-9d8cabec-139d-43cd-b156-c9fab559ea40"));
//            nvps.add(new BasicNameValuePair("productid", "aspire_order_cp_n"));
//            nvps.add(new BasicNameValuePair("transid", "181"));
//            nvps.add(new BasicNameValuePair("orderid", "181"));
//            nvps.add(new BasicNameValuePair("money", "35"));
//            nvps.add(new BasicNameValuePair("appid", "aspire_order"));
//            nvps.add(new BasicNameValuePair("paymode", "2"));
//            nvps.add(new BasicNameValuePair("productunit", "个"));
//            nvps.add(new BasicNameValuePair("notifyurl", "https://www.baidu.com"));
//            nvps.add(new BasicNameValuePair("backUrl", "https://www.baidu.com"));
//            nvps.add(new BasicNameValuePair("outProductName", "美人鱼"));
//            nvps.add(new BasicNameValuePair("BusiType", "1"));
//            nvps.add(new BasicNameValuePair("spCode", "1"));
//            nvps.add(new BasicNameValuePair("token", "0bb9a991dca28d606dd7664033b165aaf1bd02e0a6d948e0e8555dd8b146d41d"));
//            //设置表单提交编码为UTF-8，表单中的参数会被调用一次UrlEncdoe.encode(param, "UTF-8");，也就是说含有url的特殊字符会被自动转码
//            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
//            HttpResponse response = httpClient.execute(httpPost);
            
            // 配置代理。在RequestConfig这个类中可以集中设置请求的参数，比如代理，超时等等
            HttpHost proxy = new HttpHost("localhost", 8888, "http"); 
            RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
            httpPost.setConfig(config);
            
            // 新的默认HttpResponse
            CloseableHttpResponse resp = httpClient.execute(httpPost);
            try {
                HttpEntity respEntity = resp.getEntity();
                
                //EntityUtils是专门处理HttpEntity的工具类
                byte[] respByteData = EntityUtils.toByteArray(respEntity);
                respStrData = new String(respByteData);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //必须进行关闭，以释放系统资源。否则，该连接就不能被重用，直到被资源被耗尽或者被连接管理器关闭.
                resp.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respStrData;
    }
    
    /**
     * 使用httpclient发送报文，4.1版本的写法
     * @param postUrl
     * @param xmlData
     * @return 
     * String
     * @throws Exception 
     */
    public String sendHttpClient4_1Req(String postUrl, String xmlData) throws Exception {
        HttpClient client = null;
        HttpPost post = null;
        String respStr = null;
        try {
            client = new DefaultHttpClient();
            if (postUrl.startsWith("https")) {
                client = httpsClient4_1(client);
            }
            // 最大连接时间
            int reqTime = 30000;
            // 最大响应时间
            int respTime = 30000;
            // 最大重试次数
            int retryTimes = 5;

            client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, reqTime);
            client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, respTime);
            
            HttpHost proxy = new HttpHost("localhost", 8888);
            client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
            
            post = new HttpPost(new String(postUrl.getBytes("UTF-8"), "UTF-8"));
//            post.setHeader(key, value);
            StringEntity requestEntity = new StringEntity(xmlData, "text/html", "UTF-8");
            post.setEntity(requestEntity);
            
            HttpResponse httpResponse = null;
            for  (int i = 0; i <= retryTimes; i++) {
                long start = System.currentTimeMillis();
                try {
                    httpResponse = client.execute(post);
                    break;
                } catch (ConnectTimeoutException cte) {
                    if (i >= retryTimes) {
                        System.out.println("第" + (i + 1) + "次链接到" + xmlData + "发生异常！");
                        throw cte;
                    }
                } finally {
                    long end = System.currentTimeMillis();
                }
            }
                        
            respStr = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            if (respStr == null) {
                throw new Exception("往" + postUrl + "发送消息时出现异常");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != post) {
                post.abort();
            }
            if (null != client) {
                client.getConnectionManager().shutdown();
            }
        }
        return respStr;
    }
    
    /** 
     * 
     * @param client
     * @return 
     * HttpClient 
     */
    private HttpClient httpsClient4_1(HttpClient client) {
        ThreadSafeClientConnManager mgr = null;
        try {
            SSLContext ctx = getSslContext();
            SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
            mgr = new ThreadSafeClientConnManager(registry);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DefaultHttpClient(mgr, client.getParams());
    }

    private HttpClient httpsClient4_3() {
        CloseableHttpClient httpClient = null;
        try {
            SSLContext ctx = getSslContext();
            SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(ctx);
            httpClient = HttpClients.custom().setSSLSocketFactory(ssf).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpClient;
    }

    private SSLContext getSslContext() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext ctx = SSLContext.getInstance("TLS");
        TrustManager tm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        ctx.init(null, new TrustManager[] { tm }, null);
        return ctx;
    }

    public class EmptyTrustSocketFactory extends JSSESocketFactory {
        
        public EmptyTrustSocketFactory(Hashtable attributes) {
            super(attributes);
        }

        protected void initFactory() {
            try {
                SSLContext ctx = getSslContext();
                super.sslFactory = ctx.getSocketFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
