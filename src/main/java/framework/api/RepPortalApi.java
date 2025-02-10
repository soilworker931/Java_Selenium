package framework.api;

import framework.utils.Logger;
import framework.utils.ReadProperties;
import framework.utils.enums.PropertiesEnum;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class RepPortalApi {
    private static final String URL = ReadProperties.readFromConfig(PropertiesEnum.CONFIG, "api");
    private static final String VARIANT = "1";
    private static final String GET_TOKEN = "/token/get";
    private static final String GET_PROJECT_TESTS = "/test/get/json";

    public static String getToken() {
        Logger.info("Getting Variant token");
        String responseBody = "";
        try {
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(URL + GET_TOKEN);
            List<NameValuePair> params = new ArrayList<>(1);
            params.add(new BasicNameValuePair("variant", VARIANT));
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            responseBody = EntityUtils.toString(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.info("Token: " + responseBody);
        return responseBody;
    }

    public static JSONArray getProjectTests(String projectNum) {
        Logger.info("Getting tests for project: " + projectNum);
        JSONArray responseBody = null;
        try {
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(URL + GET_PROJECT_TESTS);
            List<NameValuePair> params = new ArrayList<NameValuePair>(1);
            params.add(new BasicNameValuePair("projectId", projectNum));
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            responseBody = new JSONArray(EntityUtils.toString(entity));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBody;
    }
}
