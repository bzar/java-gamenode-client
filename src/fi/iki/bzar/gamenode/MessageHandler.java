package fi.iki.bzar.gamenode;

public interface MessageHandler {
	void onMessage(Object message);
	
	static final MessageHandler EMPTY_HANDLER = new MessageHandler() {
		@Override
		public void onMessage(Object message) {
		}
		
	};

}
