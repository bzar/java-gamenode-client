package fi.iki.bzar.gamenode;

public interface ConnectHandler {
	void onConnect();
	
	static final ConnectHandler EMPTY_HANDLER = new ConnectHandler() {
		@Override
		public void onConnect() {
		}
		
	};
}
