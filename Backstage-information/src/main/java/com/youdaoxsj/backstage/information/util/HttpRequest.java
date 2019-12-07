package com.youdaoxsj.backstage.information.util;

import com.youdaoxsj.backstage.information.bean.IccidMsg;
import jodd.json.JsonParser;
import org.apache.commons.httpclient.util.HttpURLConnection;

import java.io.*;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpRequest {


    /**
     * 向指定URL发送GET方法的请求
     * //     * @param url：发送请求的URL
     * //     * @param param：请求参数，请求参数应该是 name1=value1&name2=value2&name3=value3 的形式。
     * //     * @return String[result]：所代表远程资源的响应结果
     */
    public static IccidMsg sendGet(String iccid) {

        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = "https://api.10646.cn/rws/api/v1/devices/" + iccid + "/ctdUsages";
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Basic bGl1Y29uZzk2ODE6OWJjNjUzZjItMDVhYi00Mjg5LWE1NWMtYTQ5YmNjN2Q2Y2Qx");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            IccidMsg iccidMsg = (new JsonParser().parse(result, IccidMsg.class));
            return iccidMsg;
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            IccidMsg iccidMsg = new IccidMsg();
            return iccidMsg;
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void sendPost(String[] args) {
        final String encode = "utf-8";
        final String website = "http://et.airchina.com.cn";
        final int connectTimeOut = 15000;
        final int readDataTimeOut = 50000;
        HttpURLConnection httpConn = null;
        String htmlContent = null;
        String requestCookie = null;
        String reqUrl = "/InternetBooking/AirLowFareSearchExt.do";

        try {
            // 第一次请求【POST】
            // 1、创建连接
            URL url = new URL(website + reqUrl);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            httpConn.setUseCaches(false);
            httpConn.setConnectTimeout(connectTimeOut);
            httpConn.setReadTimeout(readDataTimeOut);
            // 2、设置请求头
            httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727)");
            httpConn.setRequestProperty("Host","sms.qiniuapi.com");
            httpConn.setRequestProperty("Authorization","Qiniu 7O7hf7Ld1RrC_fpZdFvU8aCgOPuhw2K4eapYOdII:PGTUV-oRxWAIl6mdneJPSJieyyQ=");
            httpConn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            httpConn.setRequestProperty("Accept-Encoding","gzip");

            System.out.println("request url : " + reqUrl);

            // 3、连接
            httpConn.setRequestMethod("POST");
            httpConn.connect();

            // 4、设置请求参数
            OutputStream outStream = httpConn.getOutputStream();
            String postData = "templateId=1202791626972340224&mobiles=17695796277&parameters=";
            outStream.write(postData.getBytes());
            outStream.flush();
            outStream.close();

            // 5、获取响应结果
            // 获取响应头信息
            Map<String, List<String>> resHeaderMap = httpConn.getHeaderFields();
            if (null != resHeaderMap
                    && false == resHeaderMap.isEmpty()) {
                for (Map.Entry<String, List<String>> entry : resHeaderMap.entrySet()) {
                    String key = entry.getKey();
                    String value = java.util.Arrays.toString(entry.getValue().toArray());
                    if (null != key
                            && "Set-Cookie".equals(key.trim())) {
                        requestCookie = value;
                        requestCookie = requestCookie.replace("[", "");
                        requestCookie = requestCookie.replace("]", "");
                    }

                    System.out.println(key + " : " + value);
                }
            }

            System.out.println("\n\nresponse cookie : " + requestCookie);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}








