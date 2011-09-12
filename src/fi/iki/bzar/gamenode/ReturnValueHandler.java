package fi.iki.bzar.gamenode;

public class ReturnValueHandler {
	private Callback callback = Callback.DO_NOTHING;
	
	public void onReturn(Callback myCallback) {
		callback = myCallback;
	}
	
	public Callback getCallback() {
		return callback;
	}
}
