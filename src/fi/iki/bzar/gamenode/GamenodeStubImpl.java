package fi.iki.bzar.gamenode;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONException;

public class GamenodeStubImpl implements GamenodeStub {

	private Map<Long, ReturnValueHandler> callbacks;
	private List<String> methods;
	private GamenodeClient client;
	
	public GamenodeStubImpl(List<String> myMethods, GamenodeClient myClient) {
		callbacks = new TreeMap<Long, ReturnValueHandler>();
		methods = myMethods;
		client = myClient;
	}
	
	@Override
	public Callback getCallback(long id) {
		ReturnValueHandler handler = callbacks.remove(id);
		if(handler == null) {
			return Callback.DO_NOTHING;
		} else {
			return handler.getCallback();
		}
		
	}

	@Override
	public ReturnValueHandler call(String method, Object... params)
			throws NoSuchMethodException, IOException, JSONException {
		if(!methods.contains(method)) {
			throw new NoSuchMethodException();
		}
		
		long id = System.currentTimeMillis(); 
		
		ReturnValueHandler handler = new ReturnValueHandler();
		callbacks.put(id, handler);
		client.sendMethodCall(method, params, id);
		return handler;
	}

	@Override
	public List<String> methodList() {
		return methods;
	}
}
