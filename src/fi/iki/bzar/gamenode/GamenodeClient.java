package fi.iki.bzar.gamenode;

import java.io.IOException;

import org.json.JSONException;

public interface GamenodeClient {
	void connect(String url) throws IOException;
	void disconnect();
	
	GamenodeSkeleton getSkeleton();
	GamenodeStub getStub();

	void onConnect(ConnectHandler handler);
	void onDisconnect(DisconnectHandler handler);
	void onMessage(MessageHandler handler);
	void onError(ErrorHandler handler);
	
	void sendMessage(Object content) throws IOException, JSONException;
	void sendResponse(long id, Object content) throws IOException, JSONException;
	void sendError(Object content) throws IOException, JSONException;
	void sendMethodList() throws IOException, JSONException;
	void sendMethodCall(String method, Object params, long id) throws IOException, JSONException;
}
