package fi.iki.bzar.gamenode;

public interface Callback {
	void exec(Object params);

	static final Callback DO_NOTHING = new Callback() {
		public void exec(Object params) {
			
		}
	};
}
