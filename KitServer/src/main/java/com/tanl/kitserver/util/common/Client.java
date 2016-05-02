package com.tanl.kitserver.util.common;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;

/**
 * 与客户端的数据传输
 * Created by Administrator on 2016/5/1.
 */
public class Client {

	public static JSONObject readFromClient(BufferedReader reader) throws IOException {

		StringBuilder sb = new StringBuilder();
		String tmp;
		while ((tmp = reader.readLine()) != null){
			sb.append(tmp);
		}
		if(0 == sb.length()){
			return null;
		}
		return new JSONObject(sb.toString());
	}
	public static boolean writeToClient(Writer writer, JSONObject object) {

		try {
			writer.write(object.toString());
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
