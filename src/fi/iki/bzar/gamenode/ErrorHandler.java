package fi.iki.bzar.gamenode;

public interface ErrorHandler {
	void onError(Object error);
	
	static final ErrorHandler EMPTY_HANDLER = new ErrorHandler() {
		@Override
		public void onError(Object error) {
		}
		
	};

}
