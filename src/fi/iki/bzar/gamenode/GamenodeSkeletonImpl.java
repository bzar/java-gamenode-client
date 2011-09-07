package fi.iki.bzar.gamenode;

import java.util.List;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class GamenodeSkeletonImpl implements GamenodeSkeleton {

	private Object skeletonInterface = null;
	
	public GamenodeSkeletonImpl(Object mySkeletonInterface) {
		skeletonInterface = mySkeletonInterface;
	}
	
	@Override
	public List<String> getMethodList() {
		List<String> methodNames = new ArrayList<String>();
		for(Method m : skeletonInterface.getClass().getDeclaredMethods()) {
			methodNames.add(m.getName());
		}
		return methodNames;
	}

	@Override
	public Object call(String method, Object params) throws NoSuchMethodException {
		Object returnValue = null;
		try {
			returnValue = skeletonInterface.getClass().getDeclaredMethod(method, Object.class).invoke(skeletonInterface, params);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} 
		
		return returnValue;
	}

}
