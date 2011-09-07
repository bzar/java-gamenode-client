package fi.iki.bzar.gamenode;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

import com.clwillingham.socket.io.*;

public class GamenodeClientImpl implements GamenodeClient {

	private Callback connectCallback = Callback.doNothing;
	private Callback disconnectCallback = Callback.doNothing;
	private Callback messageCallback = Callback.doNothing;
	private Callback errorCallback = Callback.doNothing;
	
	private GamenodeSkeleton skeleton = GamenodeSkeleton.emptySkeleton;
	private GamenodeStub stub = GamenodeStub.emptyStub;

	private IOSocket socket;
	
	private class CallbackHandler implements MessageCallback {
		private GamenodeClientImpl client;
		
		public CallbackHandler(GamenodeClientImpl myClient) {
			client = myClient;
		}
		
		@Override
		public void onOpen() {
			try {
				client.sendMethodList();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onMessage(String message) {
			try {
				JSONObject msg = new JSONObject(message); 
				String type = msg.getString("type");
				if(type.equals("message")) {
					client.getOnMessage().exec(msg.get("content"));
				} else if(type.equals("response")) {
					long id = msg.getLong("id");
					Object content = msg.get("content");
					GamenodeStub stub = client.getStub();
					stub.getCallback(id).exec(content);
				} else if(type.equals("call")) {
					long id = msg.getLong("id");
					String method = msg.getString("method");
					Object params = msg.get("params");
					GamenodeSkeleton skel = client.getSkeleton();
					try {
						Object returnValue = skel.call(method, params);
						client.sendResponse(id, returnValue);
					} catch (NoSuchMethodException e) {
						client.sendError("Unknown method: " + method);
					} 
				} else if(type.equals("error")) {
					String content = msg.getString("content");
					client.getOnError().exec(content);
				} else if(type.equals("methodList")) {
					List<String> methods = new ArrayList<String>();
					JSONArray methodList = msg.getJSONArray("content");
					for(int i = 0; i < methodList.length(); ++i) {
						String methodName = methodList.getString(i);
						if(methodName != null) {
							methods.add(methodName);
						}
					}
					GamenodeStub stub = new GamenodeStubImpl(methods, client);
					client.setStub(stub);
					client.getOnConnect().exec(null);
				}
			} catch (JSONException e) {
				System.err.println("ERROR: Invalid message: " + message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void on(String event, String data) {
			System.out.println("EVENT " + event + ": " + data);
		}
	}
	public GamenodeClientImpl(GamenodeSkeleton mySkeleton) {
		skeleton = mySkeleton;
	}
	
	@Override
	public void connect(String url) throws IOException {
		socket = new IOSocket(url, new CallbackHandler(this));
		socket.connect();
	}

	@Override
	public GamenodeSkeleton getSkeleton() {
		return skeleton;
	}

	@Override
	public GamenodeStub getStub() {
		return stub;
	}

	public void setStub(GamenodeStub newStub) {
		stub = newStub;
	}
	
	@Override
	public void onConnect(Callback callback) {
		connectCallback = callback;
	}

	@Override
	public void onDisconnect(Callback callback) {
		disconnectCallback = callback;
	}

	@Override
	public void onMessage(Callback callback) {
		messageCallback = callback;
	}
	
	public void onError(Callback callback) {
		errorCallback = callback;
	}
	
	public Callback getOnConnect() {
		return connectCallback;
	}

	public Callback getOnDisconnect() {
		return disconnectCallback;
	}

	public Callback getOnMessage() {
		return messageCallback;
	}
	
	public Callback getOnError() {
		return errorCallback;
	}

	private void send(String content) throws IOException, JSONException {
		IOMessage msg = new IOMessage(IOMessage.MESSAGE, -1, "", content);
		socket.getWebSocket().send(msg.toString());
	}
	
	public void sendMessage(Object content) throws IOException, JSONException {
		StringWriter result = new StringWriter();
		new JSONWriter(result)
			.object()
				.key("type").value("message")
				.key("content").value(content)
			.endObject();
		send(result.toString());
	}
	
	public void sendResponse(long id, Object content) throws IOException, JSONException {
		StringWriter result = new StringWriter();
		new JSONWriter(result)
			.object()
				.key("type").value("response")
				.key("id").value(id)
				.key("content").value(content)
			.endObject();
		send(result.toString());
	}
	
	public void sendError(Object content) throws IOException, JSONException {
		StringWriter result = new StringWriter();
		new JSONWriter(result)
			.object()
				.key("type").value("error")
				.key("content").value(content)
			.endObject();
		send(result.toString());
	}
	
	public void sendMethodList() throws IOException, JSONException {
		List<String> methods = skeleton.getMethodList();
		StringWriter result = new StringWriter();
		new JSONWriter(result)
			.object()
				.key("type").value("methodList")
				.key("methodList").value(new JSONArray(methods))
			.endObject();
		send(result.toString());
	}

	@Override
	public void sendMethodCall(String method, Object params, long id) throws IOException, JSONException {
		StringWriter result = new StringWriter();
		new JSONWriter(result)
			.object()
				.key("type").value("call")
				.key("method").value(method)
				.key("params").value(params)
				.key("id").value(id)
			.endObject();
		send(result.toString());
	}
}
