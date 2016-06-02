package com.tanl.kitserver.util.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tanl.kitserver.util.ServerCode;
import com.tanl.kitserver.util.ServiceResult;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;

/**
 * 与客户端的数据传输
 * Created by Administrator on 2016/5/1.
 */
public class Client {

	public static String readFromClient (BufferedReader reader) throws IOException {

		StringBuilder sb = new StringBuilder();
		String tmp;
		while ((tmp = reader.readLine()) != null) {
			sb.append(tmp);
		}
		if (0 == sb.length()) {
			return null;
		}
		reader.close();
		return sb.toString();
	}

	public static void writeOkToClient (HttpServletResponse response) throws IOException {
		writeOkToClient(response.getWriter());
	}
	public static void writeOkToClient (Writer writer) throws IOException {
		JSONObject out = new JSONObject();
		out.put("status", true);
		writeToClient(writer, out);
	}
	public static boolean writeToClient (Writer writer, Object object) throws IOException {
		if(object == null){
			return false;
		}
		Gson g = new Gson();
		return writeToClient(writer, g.toJson(object));
	}

	/**
	 *
	 * @param writer
	 * @param objectString
	 * @return
	 * @throws IOException
	 */
	public static boolean writeToClient (Writer writer, String objectString) throws IOException {
		writer.write(objectString);
		writer.close();
		return true;
	}

	/**
	 * 传输Json
	 *
	 * @param writer
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static boolean writeToClient (Writer writer, JSONObject object) throws IOException {

		return writeToClient(writer, object.toString());
	}
	public static boolean writeToClient (Writer writer, JsonObject object) throws IOException {

		return writeToClient(writer, object.toString());
	}

	/**
	 * 传输 Gson 对象
	 * @param writer
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static boolean writeToClient (Writer writer, Gson object) throws IOException {

		return writeToClient(writer, object.toString());
	}
	public static boolean writeToClient (Writer writer, ClientInfoObj object) throws IOException {

		Gson g = new Gson();
		String a = g.toJson(object);
		System.out.println("\n\n" + a);
		return writeToClient(writer, a);
	}

	public static boolean handleError (ServiceResult result, HttpServletResponse response) throws IOException {

		if (null == result) {
			response.sendError(ServerCode.DATABASE_OUT_NULL);
			return true;
		}
		if (!result.isSuccess()) {
			response.sendError(ServerCode.DATABASE_EXCEPTION);
			return true;
		}
		return false;
	}
}
