package fi.iki.bzar.gamenode;

public interface Callback {
	void exec(Object params);

	static final Callback doNothing = new Callback() {
		public void exec(Object params) {
			
		}
	};
}
