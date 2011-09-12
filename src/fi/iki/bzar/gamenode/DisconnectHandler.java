package fi.iki.bzar.gamenode;

public interface DisconnectHandler {
	void onDisconnect();
	
	static final DisconnectHandler EMPTY_HANDLER = new DisconnectHandler() {
		@Override
		public void onDisconnect() {
		}
		
	};

}
