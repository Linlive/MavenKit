package com.tanl.kitserver.controller;

import com.tanl.kitserver.util.ServerCode;
import com.tanl.kitserver.util.ServerRootPath;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 用户下载apk文件
 * Created by Administrator on 2016/6/7.
 */
@Controller
@RequestMapping(value = "/")
public class DownloadApk {

	@RequestMapping(value = "/downloadApk")
	public void downloadByUrl (HttpServletRequest request, HttpServletResponse response) {

		String root = ServerRootPath.getRootPath(request);
		File apk = new File(root, "/Travel-kit.apk");
		if (!apk.exists()) {
			try {
				response.sendError(ServerCode.APK_DOES_NOT_EXSIST_ERROR);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		try {
			//设置文件MIME类型
			response.setContentType(request.getServletContext().getMimeType(apk.getName()));
			//设置Content-Disposition
			response.setHeader("Content-Disposition", "attachment;filename=" + apk.getName());

			InputStream fi = new FileInputStream(apk);
			OutputStream os = response.getOutputStream();
			//写文件
			int r;
			while ((r = fi.read()) != -1) {
				os.write(r);
			}
			fi.close();
			os.flush();
			os.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/showTwoDimension")
	public void showTwoDimension(HttpServletResponse response) throws IOException {
		response.sendRedirect(ServerCode.SERVER_ADDRESS + "/TravelKit.jpg");
	}
}
