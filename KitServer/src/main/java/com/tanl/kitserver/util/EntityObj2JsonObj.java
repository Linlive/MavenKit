package com.tanl.kitserver.util;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/3.
 */
public class EntityObj2JsonObj<O> {
	private Map mMap;
	private O mDataDo;
	Class<?> clazz;
	Method[] methods;
	Field[] fields;
	private EntityObj2JsonObj(){}

	public EntityObj2JsonObj(O obj){
		mDataDo = obj;
		clazz = mDataDo.getClass();
		methods = clazz.getMethods();
		fields = clazz.getDeclaredFields();
	}
	private void getMethods(){
		methods = clazz.getMethods();
	}
	//private void s

	public O change(JSONObject jObject) {

		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for (Field f : fields) {
			System.out.println("fields = " + f.getName() + " type = " + f.getType());
			sb.append(f.getName()).append(":").append(f.getType()).append(",");
		}
		sb.append("}");



		return mDataDo;
	}
}
