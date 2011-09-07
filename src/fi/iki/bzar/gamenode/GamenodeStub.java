package fi.iki.bzar.gamenode;

public interface GamenodeStub {
	Callback getCallback(long id);
	void call(String method, Object params, Callback callback) throws NoSuchMethodException;
	
	static final GamenodeStub emptyStub = new GamenodeStub() {

		@Override
		public Callback getCallback(long id) {
			return Callback.doNothing;
		}

		@Override
		public void call(String method, Object params, Callback callback)
				throws NoSuchMethodException {
			throw new NoSuchMethodException();
		}
		
	};
}
