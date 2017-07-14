package com.google.common;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

    public static String sendGet(String url) {
        return sendGet(url, null);
    }
    
    public static String sendGet(String url, Header[] headers) {
        CloseableHttpClient httpClient = createHttpsClient();
        
        HttpGet req = new HttpGet(url);
        if (headers != null && headers.length != 0) {
            req.setHeaders(headers);
        }
        
        isSetProxy(req);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(req);
            System.out.println(response.getStatusLine());
            if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                return "-1";
            }
            HttpEntity respEntity = response.getEntity();
            if (respEntity != null) {
                String content = EntityUtils.toString(respEntity).trim();
                EntityUtils.consume(respEntity);
                return content;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    /**
     * 追加参数到url后面
     * @param url
     * @param params
     * @return 
     * String
     */
    public static String appendQueryParam(String url, Map<String, Object> params) {
        StringBuffer result = new StringBuffer(url);
        
        if (result.indexOf("?") == -1) {
            result.append("?");
        }
        
        for (Map.Entry<String, Object> entry : params.entrySet()) { 
        	String key = entry.getKey();
        	Object val = entry.getValue();
        	if (val instanceof ArrayList) {
				List<String> listParams = (ArrayList<String>) val;
				for (String s : listParams) {
					result.append(key).append("=").append(s).append("&");
				}
			} else {
				result.append(key).append("=").append(val).append("&");
			}
        }
        return result.substring(0, result.length() - 1);
    }
    
    /**
     * 创建https请求
     * @return 
     * CloseableHttpClient
     */
    private static CloseableHttpClient createHttpsClient() {
        // 创建https请求
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();  
        ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();  
        registryBuilder.register("http", plainSF);  
        //指定信任密钥存储对象和连接套接字工厂  
        try {  
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());  
            //信任任何链接  
            TrustStrategy anyTrustStrategy = new TrustStrategy() {  
                @Override  
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {  
                    return true;  
                }  
            };  
            SSLContext sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, anyTrustStrategy).build();  
            LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
            registryBuilder.register("https", sslSF);  
        } catch (KeyStoreException e) {  
            throw new RuntimeException(e);  
        } catch (KeyManagementException e) {  
            throw new RuntimeException(e);  
        } catch (NoSuchAlgorithmException e) {  
            throw new RuntimeException(e);  
        }  
        Registry<ConnectionSocketFactory> registry = registryBuilder.build();  
        //设置连接管理器  
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);  
//      connManager.setDefaultConnectionConfig(connConfig);  
//      connManager.setDefaultSocketConfig(socketConfig);  
        //构建客户端  
        CloseableHttpClient httpclient = HttpClientBuilder.create().setConnectionManager(connManager).build();  
        return httpclient;
    }
    
    /**
     * 是否使用代理
     * @param req 
     * void
     */
    private static void isSetProxy(HttpRequestBase req) {
        
        if ("true".equalsIgnoreCase(SystemConfig.getProperty("isSetProxy").trim())) {
            String host = SystemConfig.getProperty("proxy.host").trim();
            String port = SystemConfig.getProperty("proxy.port").trim();
            String scheme = SystemConfig.getProperty("proxy.scheme").trim();
            
            HttpHost proxy = new HttpHost(host, Integer.parseInt(port), scheme); 
            RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
            req.setConfig(config);
        }
    }
}
