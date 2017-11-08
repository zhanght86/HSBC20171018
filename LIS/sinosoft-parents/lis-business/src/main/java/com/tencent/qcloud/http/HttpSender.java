package com.tencent.qcloud.http;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.tencent.qcloud.api.ErrorCode;

public class HttpSender {
	// 连接池维护的最大连接数
    private static final int DEFAULT_MAX_TOTAL_CONNECTION = 500;
    // 每一个路由关联的最大连接数
    private static final int DEFAULT_MAX_ROUTE_CONNECTION = 500;
    // Http客户端
    private static org.apache.http.client.HttpClient cosHttpClient = initHttpClient();
    // 监控空闲线程
    private static IdleConnectionMonitorThread idleMonitor;
    
    private static org.apache.http.client.HttpClient initHttpClient() {
    	org.apache.http.impl.conn.PoolingHttpClientConnectionManager connectionManager =
                new org.apache.http.impl.conn.PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTION);
        connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_ROUTE_CONNECTION);
        idleMonitor = new IdleConnectionMonitorThread(connectionManager);
        idleMonitor.start();
        return org.apache.http.impl.client.HttpClients.custom().setConnectionManager(connectionManager).build();
    }
    
    /**
     * 打印HTTP返回码非200的时候的错误信息
     * @param methondName
     * @param url
     * @param responseStatus
     * @return
     */
    private static String getErrorHttpResponseMsg(String methondName, String url,
           org.apache.http.StatusLine responseStatus) {
        String errMsg = new StringBuilder(methondName).append(" to url:").append(url)
                .append(" get error response, protocol:")
                .append(responseStatus.getProtocolVersion().toString()).append(", code:")
                .append(responseStatus.getStatusCode()).append(", reason:")
                .append(responseStatus.getReasonPhrase()).toString();
        return errMsg;
    }
    
    /**
     * Get请求函数
     * 
     * @param url
     * @param headers 额外添加的Http头部
     * @param params GET请求的参数
     * @param timeout  Socket与连接超时时间，单位为秒
     * @return Cos服务器返回的字符串
     * @throws Exception
     */
    public static String sendGetRequest(String url, Map<String, String> headers,
            Map<String, String> params, int timeout) throws Exception {

        HttpGet httpGet = null;
        try {
            URIBuilder urlBuilder = new URIBuilder(url);
            if (params != null) {
                for (String paramKey : params.keySet()) {
                    urlBuilder.addParameter(paramKey, params.get(paramKey));
                }
            }

            httpGet = new HttpGet(urlBuilder.build());
        } catch (URISyntaxException e) {
            throw e;
        }

        setTimeout(httpGet, timeout);
        setHeaders(httpGet, headers);

        String responseStr = null;
        try {
            HttpResponse httpResponse = cosHttpClient.execute(httpGet);;
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 200 || statusCode == 400) {
                responseStr = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            } else {
                String errMsg = getErrorHttpResponseMsg("sendGetRequest", url, httpResponse.getStatusLine());
                JSONObject errorRet = new JSONObject();
                errorRet.put(ResponseBodyKey.CODE, ErrorCode.NETWORK_ERROR);
                errorRet.put(ResponseBodyKey.MESSAGE, errMsg);
                return errorRet.toString();
            }
        } catch (ParseException | IOException e) {
            JSONObject errorRet = new JSONObject();
            errorRet.put(ResponseBodyKey.CODE, ErrorCode.NETWORK_ERROR);
            errorRet.put(ResponseBodyKey.MESSAGE, e.toString());
            responseStr = errorRet.toString();
        } finally {
            httpGet.releaseConnection();
        }
        return responseStr;
    }
    

    /**
     * 封装发送JSON类型的POST请求，此类请求的Content-type为application/json
     * 
     * @param url url地址
     * @param headers 额外添加的Http头部
     * @param params 封装在包体中的k-v对
     * @param timeout Socket与连接超时时间，单位为秒
     * @return 服务器端返回的HTTP应答
     * @throws Exception
     */
    public static String sendJsonRequest(String url, Map<String, String> headers,
            Map<String, String> params, int timeout) throws Exception {
        HttpPost httpPost = new HttpPost(url);

        setTimeout(httpPost, timeout);
        setHeaders(httpPost, headers);

        ContentType utf8TextPlain =
                org.apache.http.entity.ContentType.create("text/plain", Consts.UTF_8);
        String postJsonStr = new JSONObject(params).toString();
        StringEntity stringEntity = new StringEntity(postJsonStr, utf8TextPlain);
        httpPost.setEntity(stringEntity);

        String responseStr = null;
        try {
            HttpResponse httpResponse = cosHttpClient.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 200 || statusCode == 400) {
                responseStr = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            } else {
                String errMsg = getErrorHttpResponseMsg("sendJsonRequest", url, httpResponse.getStatusLine());
                JSONObject errorRet = new JSONObject();
                errorRet.put(ResponseBodyKey.CODE, ErrorCode.NETWORK_ERROR);
                errorRet.put(ResponseBodyKey.MESSAGE, errMsg);
                return errorRet.toString();
            }
        } catch (ParseException | IOException e) {
            JSONObject errorRet = new JSONObject();
            errorRet.put(ResponseBodyKey.CODE, ErrorCode.NETWORK_ERROR);
            errorRet.put(ResponseBodyKey.MESSAGE, e.toString());
            responseStr = errorRet.toString();
        } finally {
            httpPost.releaseConnection();
        }
        return responseStr;
    }
    
    /**
     * 封装发送文件的POST请求，此类请求的Content-type为multipart/form-data
     * 
     * @param url url地址
     * @param headers 额外添加的Http头部
     * @param params 封装在包体中的k-v对
     * @param fileContent 要发送的文件内容
     * @param timeout Socket与连接超时时间，单位为秒
     * @return Cos服务器端返回的Http应答
     * @throws Exception
     */
    public static String sendFileRequest(String url, Map<String, String> headers,
            Map<String, String> params, byte[] fileContent, int timeout) throws Exception {
        HttpPost httpPost = new HttpPost(url);

        setTimeout(httpPost, timeout);
        setHeaders(httpPost, headers);

        org.apache.http.entity.mime.MultipartEntityBuilder entityBuilder = org.apache.http.entity.mime.MultipartEntityBuilder.create();
        if (fileContent != null) {
            entityBuilder.addBinaryBody(RequestBodyKey.FILE_CONTENT, fileContent);
        }

        ContentType utf8TextPlain =
                org.apache.http.entity.ContentType.create("text/plain", Consts.UTF_8);
        if (params != null) {
            for (String paramKey : params.keySet()) {
                entityBuilder.addTextBody(paramKey, params.get(paramKey), utf8TextPlain);
            }
        }

        httpPost.setEntity(entityBuilder.build());
        String responseStr = null;
        try {
            HttpResponse httpResponse = cosHttpClient.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 200 || statusCode == 400) {
                responseStr = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            } else {
                String errMsg = getErrorHttpResponseMsg("sendFileRequest", url, httpResponse.getStatusLine());
                JSONObject errorRet = new JSONObject();
                errorRet.put(ResponseBodyKey.CODE, ErrorCode.NETWORK_ERROR);
                errorRet.put(ResponseBodyKey.MESSAGE, errMsg);
                return errorRet.toString();
            }
        } catch (ParseException | IOException e) {
            JSONObject errorRet = new JSONObject();
            errorRet.put(ResponseBodyKey.CODE, ErrorCode.NETWORK_ERROR);
            errorRet.put(ResponseBodyKey.MESSAGE, e.toString());
            responseStr = errorRet.toString();
        } finally {
            httpPost.releaseConnection();
        }
        return responseStr;
    }
    
    /**
     * 设置超时时间
     * 
     * @param httpRequest http请求
     * @param timeout Socket与连接超时时间，单位为秒
     */
    private static void setTimeout(HttpRequestBase httpRequest, int timeout) {
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout * 1000)
                .setConnectTimeout(timeout * 1000).build();
        httpRequest.setConfig(requestConfig);
    }
    
    /**
     * 设置Http头部，同时添加上公共的类型，长连接，COS SDK标识
     * 
     * @param message HTTP消息
     * @param headers 用户额外添加的HTTP头部
     */
    private static void setHeaders(HttpMessage message, Map<String, String> headers) {
        message.setHeader(RequestHeaderKey.ACCEPT, RequestHeaderValue.Accept.ALL);
        message.setHeader(RequestHeaderKey.CONNECTION, RequestHeaderValue.Connection.KEEP_ALIVE);;
        message.setHeader(RequestHeaderKey.USER_AGENT, RequestHeaderValue.UserAgent.COS_FLAG);

        if (headers != null) {
            for (String headerKey : headers.keySet()) {
                message.setHeader(headerKey, headers.get(headerKey));
            }
        }
    }
    
    /**
     * 关闭后台扫描线程
     */
    public static void shutdown() {
        idleMonitor.shutdown();
    }
}
