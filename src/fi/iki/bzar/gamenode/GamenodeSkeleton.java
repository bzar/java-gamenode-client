package fi.iki.bzar.gamenode;

import java.util.List;

public interface GamenodeSkeleton {
	List<String> getMethodList();
	Object call(String method, Object params) throws NoSuchMethodException;
}
