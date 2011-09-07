package fi.iki.bzar.gamenode;

import java.io.IOException;

import org.json.JSONException;

public interface GamenodeClient {
	void connect(String url) throws IOException;
	
	GamenodeSkeleton getSkeleton();
	GamenodeStub getStub();

	void onConnect(Callback callback);
	void onDisconnect(Callback callback);
	void onMessage(Callback callback);
	void onError(Callback callback);
	
	void sendMessage(Object content) throws IOException, JSONException;
	void sendResponse(long id, Object content) throws IOException, JSONException;
	void sendError(Object content) throws IOException, JSONException;
	void sendMethodList() throws IOException, JSONException;
	void sendMethodCall(String method, Object params, long id) throws IOException, JSONException;
}
