package com.tanl.kitserver.util.common;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/3.
 */
public class ParamType {
	public static JSONObject toJsonObject(String json){

		JSONObject obj;
		try{
			obj = new JSONObject(json);
		} catch (JSONException e){
			return null;
		}
		return obj;
	}
}
