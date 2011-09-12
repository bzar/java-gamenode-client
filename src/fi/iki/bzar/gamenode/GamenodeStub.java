package fi.iki.bzar.gamenode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

public interface GamenodeStub {
	Callback getCallback(long id);
	ReturnValueHandler call(String method, Object... params) throws NoSuchMethodException, IOException, JSONException;
	List<String> methodList();
	
	static final GamenodeStub EMPTY_STUB = new GamenodeStub() {

		@Override
		public Callback getCallback(long id) {
			return Callback.DO_NOTHING;
		}

		@Override
		public ReturnValueHandler call(String method, Object... params)
				throws NoSuchMethodException {
			throw new NoSuchMethodException();
		}

		@Override
		public List<String> methodList() {
			return new ArrayList<String>();
		}
		
	};
}
