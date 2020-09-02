package com.billing.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class JavaReflection {

	
	public static void copyMyObject(Object source, Object target) {
		
		Field[] sourceFields = source.getClass().getDeclaredFields();
		Field[] targetFields = target.getClass().getDeclaredFields();
		
		for (Field sourceField :sourceFields) {
			
			sourceField.setAccessible(true);
			if(!sourceField.getName().equals("serialVersionUID")) {
				for (Field targetField :targetFields) {
					
					targetField.setAccessible(true);
					
					if(sourceField.getName().equals(targetField.getName())){
						
						Object iWantThis;
						try {
							iWantThis = (Object) sourceField.get(source);
							if(sourceField.getType().equals(targetField.getType())) {
								targetField.set(target, iWantThis);
							} else {
								Method sourceMethod = source.getClass().getMethod("set"+sourceField.getName()
							    .replaceFirst(sourceField.getName().substring(0, 1), sourceField.getName()
							    .substring(0, 1).toUpperCase()),sourceField.getType());
							     
								try{
							    	Method meth = target.getClass().getMethod(sourceMethod.getName(), sourceField.getType());
							    	meth.invoke(target, iWantThis);
								}catch(Exception e){
							    	break;
							    }
								
							}
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
					}
	
				}
			    
			}
		}
		
	}
}
