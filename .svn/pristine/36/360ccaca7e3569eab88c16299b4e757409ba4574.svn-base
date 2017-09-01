package com.iycharge.server.admin.message;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.notification.Notification;



class Obj {
	String address;
//	String name;
	Obj(String address) {
		this.address = address;
	}
}
public class SendCloudSmtp {
	private static final String API_USER="qyjsyf_test_sDLlHx";
	private static final String API_KEY="JdSwljfVq1OgB38n";
	public static String convert(List<Obj> dataList) {
		JSONObject ret = new JSONObject();
		JSONArray to = new JSONArray();
//		JSONArray names = new JSONArray();
		for (Obj a : dataList) {
			to.put(a.address);
//			names.put(a.name);
		}
		JSONObject sub = new JSONObject();
//		sub.put("%name%", names);
		ret.put("to", to);
		ret.put("sub", sub);
		return ret.toString();
	}
	public static void send_common(Notification notification,String rcpt_to) throws IOException {
		final String url = "http://api.sendcloud.net/apiv2/mail/send";
		final String apiUser = API_USER;
		final String apiKey = API_KEY;
		List<Account> accounts = notification.getSenders();
		String subject = notification.getTitle();
		String html = notification.getContent();
		
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("apiUser", apiUser));
		params.add(new BasicNameValuePair("apiKey", apiKey));
		params.add(new BasicNameValuePair("to", rcpt_to));
		params.add(new BasicNameValuePair("from", "server@charge.com"));
		params.add(new BasicNameValuePair("fromName", "充电大亨"));
		params.add(new BasicNameValuePair("subject", subject));
		params.add(new BasicNameValuePair("html", html));
		params.add(new BasicNameValuePair("respEmailId", "true"));

		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		HttpResponse response = httpClient.execute(httpPost);

		// 处理响应
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// 正常返回, 解析返回数据
			System.out.println(EntityUtils.toString(response.getEntity()));
		} else {
			System.err.println("error");
		}
		httpPost.releaseConnection();
	}
	//使用模板带参数发送
	public static void send_template(Notification notification,List<Account> senders) throws ClientProtocolException, IOException {

		final String url = "http://api.sendcloud.net/apiv2/mail/sendtemplate";

		final String apiUser = API_USER;
		final String apiKey = API_KEY;
		
		
		List<Obj> dataList = new ArrayList<Obj>();
		if(null!=senders){
			for(Account account : senders){
				if(account.getEmail()!=null){
					dataList.add(new Obj(account.getEmail()));
				}
			}
		}
		final String xsmtpapi = convert(dataList);
		//4.4新实现类CloseableHttpClient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("apiUser", apiUser));
		params.add(new BasicNameValuePair("apiKey", apiKey));
		params.add(new BasicNameValuePair("xsmtpapi", xsmtpapi));
		params.add(new BasicNameValuePair("templateInvokeName", notification.getTempName()));
		params.add(new BasicNameValuePair("from", "server@charge.com"));
		params.add(new BasicNameValuePair("fromname", "充电大亨"));
		params.add(new BasicNameValuePair("subject", notification.getTitle()));

		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		HttpResponse response = httpClient.execute(httpPost);

		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
			System.out.println(EntityUtils.toString(response.getEntity()));
		} else {
			System.err.println("error");
		}
		httpPost.releaseConnection();
	}

	public static void send_template_maillist(Notification notification) throws ClientProtocolException, IOException {
		final String url = "http://api.sendcloud.net/apiv2/mail/sendtemplate";
		final String apiUser = API_USER;
		final String apiKey = API_KEY;
		List<Account> senders = notification.getSenders();
	    String to = "";
		if(null!=senders){
			for(Account account : senders){
				if(account.getEmail()!=null){
					to+=account.getEmail();
				}
			}
		}
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("apiUser", apiUser));
		params.add(new BasicNameValuePair("apiKey", apiKey));
		params.add(new BasicNameValuePair("to", to));
		params.add(new BasicNameValuePair("template_invoke_name", notification.getTempName()));
		params.add(new BasicNameValuePair("from", "server@charge.com"));
		params.add(new BasicNameValuePair("fromname", "充电大亨"));
		params.add(new BasicNameValuePair("subject", notification.getTitle()));
		params.add(new BasicNameValuePair("use_maillist", "true"));
		params.add(new BasicNameValuePair("resp_email_id", "true"));
		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		HttpResponse response = httpClient.execute(httpPost);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
			System.out.println(EntityUtils.toString(response.getEntity()));
		} else {
			System.err.println("error");
		}
		httpPost.releaseConnection();
	}

	
	
	
}
