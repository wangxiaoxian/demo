package com.google.sever;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;



public class SearchSever {
	
//	public void getPictureMessage(String path) throws ClientProtocolException, IOException{
////		Picture pic=new Picture();
////		pic.setFilePath("f:\\test");
////		pic.setFilePath(path);
////		File file = new File(pic.getFilePath());
////		File[] files=file.listFiles();
////		for(int i=0;i<files.length;i++){
//////			file.getName();
//////						System.out.println();
////			String html=getPicHtml(files[i]);
////			//	        System.out.println(html);
////			String url=getUrl(html);
////			//	        System.out.println(url);
////			html=getPicMessageHtml(url);
//////			wirteFile(html);
////		    searchPicMessage(html,files[i],path);
////		}
//
//	}

	public String[] searchPicMessage(String html, File file, String path){
		Document doc = Jsoup.parse(html);
		Elements picDescribe = doc.getElementsByClass("_gUb");
		String describe = picDescribe.text();
		System.out.println("线程" + Thread.currentThread().getName()+" 对该图片 "+file.getName()+" 的最佳猜测："+describe);
		
		Elements ele = doc.getElementsByClass("r");
		if(!ele.hasText()) return null;
		
		String des1 = ele.get(0).text();
		System.out.println(file.getName()+"  "+des1);
		if(ele.size() > 1){
			String des2 = ele.get(1).text();
			System.out.println(file.getName()+"  "+des2);
		    return new String[]{file.getName(),describe,des1,des2};
		} else
			return new String[]{file.getName(),describe,des1,""};

	}

	public String getUrl(String html){
		Document doc = Jsoup.parse(html);
		Elements ele = doc.getElementsByTag("a");
		//		System.out.println(ele);
		String url = ele.attr("href");
		return url;
	}

	public String getPicHtml(File file) {
//		CloseableHttpClient httpclient = HttpClients.createDefault();
	    CloseableHttpClient httpclient = this.createHttpsClient();
		
		try {
			HttpPost httppost = new HttpPost("https://www.google.com/searchbyimage/upload");

			this.isSetProxy(httppost);
			
			//	            FileBody bin = new FileBody(new File(file.getPath()));
			StringBody comment = new StringBody(file.getName(), ContentType.TEXT_PLAIN);
			StringBody hl=new StringBody("zh_CN",ContentType.TEXT_PLAIN);
			HttpEntity reqEntity = MultipartEntityBuilder.create()
					.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
					.addBinaryBody("encoded_image", file, ContentType.DEFAULT_BINARY,file.getName())
					.addPart("filename", comment)
					.addPart("hl",hl)
					.build();

			httppost.setEntity(reqEntity);

			httppost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			httppost.setHeader("Accept-Encoding", "gzip, deflate, br");
			httppost.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
			httppost.setHeader("Connection", "keep-alive");
			httppost.setHeader("Cookie", "NID=77=uR-zsBylNYWvOaCTm6InjEOSrosON10xnE2pMFDz1P3Kk0HxsRBk3s9moOn4blYOa1v3R" +
					"lV2m6mj7ZOsL706hnmQMTRMZ2tK8zBYXJdXX1iFbE3WIrUE-XMA4lKFK3KIz41YXWq6a43gW7QDKhzG");
			httppost.setHeader("Host", "www.google.com");
			httppost.setHeader("Referer", "https://www.google.com");
			httppost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");

//			System.out.println(httppost.getEntity().getContent());
//			System.out.println("executing request " + httppost.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
//				System.out.println("----------------------------------------");
				System.out.println("第一步搜索。线程" + Thread.currentThread().getName()+" 搜索文件"+file.getName()+" 返回状态码"+response.getStatusLine());
				if(response.getStatusLine().getStatusCode()!=302){
					return "false";
				}
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					//	                    System.out.println("Response content length: " + resEntity.getContentLength());
					//你看你程序中解析html时候的编码跟服务器返回给你的html页面编码是否一致。比如，服务器返回给你的html页面编码是GBK。你得到响应后，解析的时候用UTF-8。这样会乱码的。
					String html=EntityUtils.toString(resEntity,"GBK").trim();
					//		                System.out.println(html);
					return html;
				}
				EntityUtils.consume(resEntity);
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		    e.printStackTrace();
			return "false";
		} catch (IOException e) {
			// TODO Auto-generated catch block
		    e.printStackTrace();
			return "false";
        } finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return "false";
			}
		}
		return "false";
	}

	public String getPicMessageHtml(String url) {
//		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpClient httpclient = this.createHttpsClient();
		try {
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			httpget.setHeader("Accept-Encoding", "gzip, deflate, br");
			httpget.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
			httpget.setHeader("Connection", "keep-alive");
			httpget.setHeader("Cookie", "NID=77=jgG8hkFLtLN7eagIHpQB5PVhSWBTCpZKyBuwFtHTVpp1Y-hQxBuT8_tbF2_YjOK4ogtddi1Hl48bHGudUZvDJgau76pfHC1zX7p7nvMsIOQ_muyDzCwZVHe7NJunnDjKQFSQFT0hbMXiR2G_ckWf1Q");
			httpget.setHeader("Host", "www.google.com");
			httpget.setHeader("Referer", "https://www.google.com");
			httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
//			System.out.println("Executing request " + httpget.getURI());
			
			this.isSetProxy(httpget);
			
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
//				System.out.println("----------------------------------------");
				System.out.println("线程" + Thread.currentThread().getName()+" 返回状态码"+response.getStatusLine());
				if(response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK){
					return "false";
				}
				//	                System.out.println("Response content length: " + response.getEntity().getContentLength());
				HttpEntity entity = response.getEntity();
				String html=EntityUtils.toString(entity,"GBK").trim();
				//	                String html = EntityUtils.toString(entity,    ContentType.getOrDefault(entity).getCharset().toString());
//				System.out.println(html.length());
				return html;
				// Do not feel like reading the response body
				// Call abort on the request object
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		    e.printStackTrace();
			return "false";
		} catch (IOException e) {
			// TODO Auto-generated catch block
		    e.printStackTrace();
			return "false";
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			    e.printStackTrace();
				return "false";
			}
		}
	}
	
	/**
	 * 创建https请求
	 * @return 
	 * CloseableHttpClient
	 */
	private CloseableHttpClient createHttpsClient() {
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
	 * 读取配置文件，是否设置代理
	 * @param req 
	 * void
	 */
	private void isSetProxy(HttpRequestBase req) {
	    Properties prop = null;
	    String configPath = System.getProperty("user.dir") + "\\config\\system.properties";
	    try {
            prop = new Properties();
            InputStream is = new FileInputStream(configPath);
            prop.load(is);
        } catch (FileNotFoundException e) {
            System.out.println("找不到系统配置文件:" + configPath);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("系统配置文件解析出错:" + configPath);
            e.printStackTrace();
        }
	    
	    if ("true".equalsIgnoreCase(prop.getProperty("isSetProxy").trim())) {
	        String host = prop.getProperty("proxy.host").trim();
	        String port = prop.getProperty("proxy.port").trim();
	        String scheme = prop.getProperty("proxy.scheme").trim();
	        
	        HttpHost proxy = new HttpHost(host, Integer.parseInt(port), scheme); 
	        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
	        req.setConfig(config);
        }
	}
}
