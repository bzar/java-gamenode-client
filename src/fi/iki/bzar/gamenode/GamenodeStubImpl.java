package fi.iki.bzar.gamenode;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONException;

public class GamenodeStubImpl implements GamenodeStub {

	private Map<Long, Callback> callbacks;
	private List<String> methods;
	private GamenodeClient client;
	
	public GamenodeStubImpl(List<String> myMethods, GamenodeClient myClient) {
		callbacks = new TreeMap<Long, Callback>();
		methods = myMethods;
		client = myClient;
	}
	
	@Override
	public Callback getCallback(long id) {
		Callback callback = callbacks.remove(id);
		if(callback == null) {
			callback = Callback.doNothing;
		}
		return callback;
	}

	@Override
	public void call(String method, Object params, Callback callback)
			throws NoSuchMethodException {
		if(!methods.contains(method)) {
			throw new NoSuchMethodException();
		}
		
		long id = System.currentTimeMillis(); 
		
		try {
			client.sendMethodCall(method, params, id);
			callbacks.put(id, callback);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
