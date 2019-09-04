package com.zws.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Iterator;
import java.util.Map;

import static java.nio.charset.Charset.forName;

/**
 *
 * YYYHL
 * HttpClient工具类
 */
public  class httpClientUtils {

    private static Logger logger = LoggerFactory.getLogger(httpClientUtils.class);

    /**
     * get请求
     * @return
     * @throws Exception
     */
    public static String doGet(String url) {
        // 1.使用默认的配置的httpclient  此方法需要验证https
                CloseableHttpClient client = HttpClients.createDefault();
        //此方法忽略https
//        CloseableHttpClient client = null;
//        try {
//            client = SSLClientCustom.getHttpClinet();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // 2.使用get方法
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-type", "application/json");
        CloseableHttpResponse response = null;

        try
        {
            // 3.执行请求，获取响应
            response = client.execute(httpGet);
            // 看请求是否成功，这儿打印的是http状态码
            System.out.println(response.getStatusLine().getStatusCode());
            // 4.获取响应的实体内容，就是我们所要抓取得网页内容
            HttpEntity entity = response.getEntity();

            // 5.将其打印到控制台上面
            // 方法一：使用EntityUtils
            if (entity != null)
            {
                String json_String = EntityUtils.toString(entity, forName("UTF-8"));
                return json_String;
            }
            EntityUtils.consume(entity);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * del请求
     * @return
     * @throws Exception
     */
    public static String doDel(String url) {
        // 1.使用默认的配置的httpclient
        CloseableHttpClient client = HttpClients.createDefault();

//        //此方法忽略https
//        CloseableHttpClient client = null;
//        try {
//            client = SSLClientCustom.getHttpClinet();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // 2.使用get方法
        HttpDelete httpdel = new HttpDelete(url);
        httpdel.setHeader("Content-type", "application/json");
        CloseableHttpResponse response = null;
        try
        {
            // 3.执行请求，获取响应
            response = client.execute(httpdel);
            // 看请求是否成功，这儿打印的是http状态码
            System.out.println(response.getStatusLine().getStatusCode());
            // 4.获取响应的实体内容，就是我们所要抓取得网页内容
            HttpEntity entity = response.getEntity();
            // 5.将其打印到控制台上面
            // 方法一：使用EntityUtils
            if (entity != null)
            {
                String json_String = EntityUtils.toString(entity, forName("UTF-8"));
                return json_String;
            }
            EntityUtils.consume(entity);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * post请求(用于key-value格式的参数)
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, Map params){

        BufferedReader in = null;
        try {
            // 定义HttpClient
            CloseableHttpClient client = HttpClients.createDefault();
            //此方法忽略https
//            CloseableHttpClient client = SSLClientCustom.getHttpClinet();

            // 实例化HTTP方法
            HttpPost request = new HttpPost();
            request.setURI(new URI(url));
            request.setHeader("Content-type", "application/json");
            JSONObject jsonObject = new JSONObject();
            for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String value = String.valueOf(params.get(name));
                jsonObject.put(name,value);
            }
            request.setEntity(new StringEntity(jsonObject.toString(),forName("UTF-8")));

            HttpResponse response = client.execute(request);
            int code = response.getStatusLine().getStatusCode();
            if(code == 200){	//请求成功
                in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),forName("UTF-8")));
                StringBuffer sb = new StringBuffer("");
                String line = "";
                String NL = System.getProperty("line.separator");
                while ((line = in.readLine()) != null) {
                    sb.append(line + NL);
                }
                in.close();
                return sb.toString();
            } else{	//
                return null;
            }
        }
        catch(Exception e){
            e.printStackTrace();

            return null;
        }
    }

    /**
     * post请求  无参数
     * @param url
     * @return
     */
    public static String doPost(String url){

        BufferedReader in = null;
        try {
            // 定义HttpClient
            CloseableHttpClient client = HttpClients.createDefault();
            //此方法忽略https
//            CloseableHttpClient client = SSLClientCustom.getHttpClinet();


            // 实例化HTTP方法
            HttpPost request = new HttpPost();
            request.setURI(new URI(url));
            request.setHeader("Content-type", "application/json");
            HttpResponse response = client.execute(request);
            int code = response.getStatusLine().getStatusCode();
            if(code == 200){	//请求成功
                in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),forName("UTF-8")));
                StringBuffer sb = new StringBuffer("");
                String line = "";
                String NL = System.getProperty("line.separator");
                while ((line = in.readLine()) != null) {
                    sb.append(line + NL);
                }
                in.close();
                return sb.toString();
            } else{	//
                return null;
            }
        }
        catch(Exception e){
            e.printStackTrace();

            return null;
        }
    }


    /**
     * put  map传参
     * @param url
     * @param params
     * @return
     */
    public static String doput(String url,Map params){
        BufferedReader in = null;
        try {
            // 定义HttpClient
            CloseableHttpClient client = HttpClients.createDefault();
            //此方法忽略https
//            CloseableHttpClient client = SSLClientCustom.getHttpClinet();

            // 实例化HTTP方法
            HttpPut request = new HttpPut();
            request.setURI(new URI(url));
            request.setHeader("Content-type", "application/json");

            //设置参数
            JSONObject jsonObject = new JSONObject();
            for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String value = String.valueOf(params.get(name));
                jsonObject.put(name,value);
            }
            request.setEntity(new StringEntity(jsonObject.toString(),forName("UTF-8")));

            HttpResponse response = client.execute(request);
            int code = response.getStatusLine().getStatusCode();
            if(code == 200){	//请求成功
                in = new BufferedReader(new InputStreamReader(response.getEntity()
                        .getContent(),"UTF-8"));
                StringBuffer sb = new StringBuffer("");
                String line = "";
                String NL = System.getProperty("line.separator");
                while ((line = in.readLine()) != null) {
                    sb.append(line + NL);
                }

                in.close();

                return sb.toString();
            }
            else{	//
                System.out.println("状态码：" + code);
                return null;
            }
        }
        catch(Exception e){
            e.printStackTrace();

            return null;
        }
    }

}
