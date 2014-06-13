package org.cerberus.scenario;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.cerberus.config.ServerInfo;
import org.cerberus.event.collection.MotionCollector;
import org.cerberus.util.ServerIpUtil;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

public class NetworkMotionStream implements AbstractMotionStream {

	private static String testScenarioId = "";
	
	private List<MotionVO> motionList = new ArrayList<MotionVO>();
	
	@Override
	public void sendData(MotionVO data) {
//		System.out.println(data + ".add");
		
		Log.d("cerberus", "add Motion " + data);
		
		motionList.add(data);
		
	}
	@Override
	public void updateData(MotionVO data) {
		System.out.println(data + ".update");
		motionList.get(motionList.size() -1 ).setSleep(data.getSleep()); ;
		motionList.get(motionList.size() -1 ).setParam(data.getParam()); ;
		
		Log.d("cerberus", "update Motion " + data);
		
//		System.out.println(motionList.toString());
	}
	

	public List<MotionVO> getMotionList() {
		return motionList;
	}



	public void setMotionList(List<MotionVO> motionList) {
		this.motionList = motionList;
	}



	public void sendNetworkData() {
		
		System.out.println("network Send......." + motionList.toString() );
		
		new AsyncTask() {

			@Override
			protected Object doInBackground(Object... arg0) {
				try {
					
					Gson gson = new Gson();
					
					HttpClient client = new DefaultHttpClient();
					
					String uri = ServerInfo.ServerIp + "/api/v1/motion_events";
					
					HttpPost post = new HttpPost(uri);
					
					List params = new ArrayList();
					
					params.add(new BasicNameValuePair("test_scenario_id", testScenarioId ));
					
					 List motionList = new ArrayList();
				        for(MotionVO motion : ((NetworkMotionStream)MotionCollector.getInstance().getStream()).getMotionList()) {
				        	
				        	Map motionMap = new HashMap();
				        	
				        	motionMap.put("activity_class", motion.getActivity_class());
				        	motionMap.put("param", motion.getParam());
				        	motionMap.put("view", motion.getView());
				        	motionMap.put("sleep", motion.getSleep());
				        	motionMap.put("action_type", motion.getAction_type());
				        	motionMap.put("client_timestamp", motion.getTime_stamp());
				        	
				        	motionList.add(motionMap);
				        }
					
					params.add(new BasicNameValuePair("motion_events", gson.toJson(motionList) ));
					
					UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
					
					post.setEntity(ent);
					
					HttpResponse response = client.execute(post);
					
					Log.d("test", "send Data...");
					
					if(response.getEntity() != null) {
						Log.i("test", EntityUtils.toString(response.getEntity()));
					}
					
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return null;
			}

			@Override
			protected void onPostExecute(Object result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				
				android.os.Process.killProcess(android.os.Process.myPid());
			}
			
			
			
		}.execute();
	}
	
	
	public void getScenarioId(final String apkKey) {

		System.out.println("get Scenario id/....");
		
		new AsyncTask() {

			@Override
			protected Object doInBackground(Object... arg0) {
				try {
					
					Gson gson = new Gson();
					
					HttpClient client = new DefaultHttpClient();
					
					String uri = ServerIpUtil.getServerIp() + "/api/v1/test_scenarios";
					
					HttpPost post = new HttpPost(uri);
					
					List params = new ArrayList();
					params.add(new BasicNameValuePair("project_id", apkKey ));
					params.add(new BasicNameValuePair("activity_name", "mainActivity"));
					params.add(new BasicNameValuePair("package_name", "com.test.test"));
					
					
					UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
					
					post.setEntity(ent);
					
					HttpResponse response = client.execute(post);
					
					Log.d("test", "send Data...");
					
					if(response.getEntity() != null) {
						String result = EntityUtils.toString(response.getEntity()); 
						Log.i("test", result);
						result = result.split(":")[2].replaceAll(" ", "");
						result = result.substring(0, result.length()-2);
						testScenarioId = result;
						Log.i("test", result);
					}
//					{"response":"test_scenario_id: 2"}

					
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return null;
			}
			
		}.execute();
		
		
		System.out.println("finish get Scenario Id");
	}



	
	
	
}
