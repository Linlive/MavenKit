package com.tanl.kitserver.util.common;

import java.io.File;

/**
 * 上传商品辅助类
 * Created by Administrator on 2016/5/24.
 */
public class DirectoryCreate {

	/**
	 * 根据指定的路径创建目录
	 * @param dirNamePath
	 * @return 目录路径
	 */
	public static String createDir (String dirNamePath) {

		File f = new File(dirNamePath);
		if (!f.exists() && f.mkdirs()) {
			return "./root/";
		}
		return dirNamePath;
	}
}
