package fi.iki.bzar.gamenode;

import java.util.ArrayList;
import java.util.List;

public interface GamenodeSkeleton {
	List<String> getMethodList();
	Object call(String method, Object... params) throws NoSuchMethodException;
	
	static final GamenodeSkeleton EMPTY_SKELETON = new GamenodeSkeleton() {

		@Override
		public List<String> getMethodList() {
			return new ArrayList<String>();
		}

		@Override
		public Object call(String method, Object... params)
				throws NoSuchMethodException {
			throw new NoSuchMethodException();
		}
		
	};
}
