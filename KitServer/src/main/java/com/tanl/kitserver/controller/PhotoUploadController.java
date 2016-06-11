package com.tanl.kitserver.controller;

import com.tanl.kitserver.model.bean.GoodsDo;
import com.tanl.kitserver.service.GoodsImgsService;
import com.tanl.kitserver.service.GoodsService;
import com.tanl.kitserver.util.ServerRootPath;
import com.tanl.kitserver.util.ServiceResult;
import com.tanl.kitserver.util.common.GenerateRandomString;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.Log4JLogger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/5/19.
 */
@Controller
@RequestMapping(value = "/")
public class PhotoUploadController {

	@Resource(name = "goodsImgsService")
	GoodsImgsService imgsService;

	@Resource(name = "goodsService")
	GoodsService goodsService;


	private static String rootPath;

	private HashMap<String, String> photoInfoMap = new HashMap<String, String>();
	private HashMap<String, String> userInfoMap = new HashMap<String, String>();
	private String goodsId = "";

	private ServiceResult<Integer> goodsInsertResult;
	private ServiceResult goodsImageInsertResult;

	/**
	 * 用户上传的文件均在upload下
	 *
	 * @param request request
	 * @param response response
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadGoods")
	public void uploadHandle (HttpServletRequest request, HttpServletResponse response) throws IOException {

		request.setCharacterEncoding("UTF-8");

		//获得磁盘文件条目工厂。
		DiskFileItemFactory factory = new DiskFileItemFactory();

		rootPath = ServerRootPath.getRootPath(request);

		createRootDirs(rootPath);
		//避免文件过大在内存中
		factory.setRepository(new File(rootPath, "tmp/"));
		//设置缓存的大小，当上传文件的容量超过缓存时，就放到暂时存储室。
		factory.setSizeThreshold(1024 * 1024);

		ServletFileUpload upload = new ServletFileUpload(factory);
		boolean firstPhoto = true;
		try {
			//多个part上传
			List<FileItem> list = upload.parseRequest(request);
			goodsId = GenerateRandomString.generateString();
			for (FileItem item : list) {
				//获取表单属性名字。
				String name = item.getFieldName();
				//如果获取的表单信息是普通的文本信息。即通过页面表单形式传递来的字符串。
				if (item.isFormField()) {
					handleTextInfo(request, item);
				}
				//如果传入的是非简单字符串，而是图片，音频，视频等二进制文件。
				else {
					String dbPath = saveFile(request, item, firstPhoto);
					firstPhoto = false;
					// 插入图片
					imgsService.addGoodsSingleImgToDB(goodsId, dbPath);
				}
			}
			//插入商品表
			insertGoodsToDB();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/getPhoto/**")
	public void getFile(HttpServletRequest request, HttpServletResponse response) throws IOException{

		if(null == rootPath){
			rootPath = ServerRootPath.getRootPath(request);
		}
		File f = new File(rootPath, "/images/");
		if(!f.exists() && !f.mkdirs()){
			Log4JLogger logger = new Log4JLogger("getPhotoErrorLog");
			logger.error("could not create dir");
		}
	}

	/**
	 * 插入数据库
	 */
	private void insertGoodsToDB(){

		GoodsDo goodsDo = new GoodsDo();
		goodsDo.setGoodsId(goodsId);
		goodsDo.setGoodsName(photoInfoMap.get("name"));

		setBasicInfo(goodsDo);
//      设置商品类别等信息的另一种方式
//		goodsDo.setGoodsBrandValue(photoInfoMap.get("brand"));
//		goodsDo.setGoodsTypeValue(photoInfoMap.get("type"));
//		goodsDo.setGoodsSizeValue(photoInfoMap.get("size"));
//		goodsDo.setGoodsColorValue(photoInfoMap.get("color"));

		goodsDo.setGoodsExtras(photoInfoMap.get("extraInfo"));
		goodsDo.setGoodsPlace(photoInfoMap.get("place"));
		goodsDo.setGoodsPrice(Float.valueOf(photoInfoMap.get("price")));
		goodsDo.setGoodsRepertory(Integer.valueOf(photoInfoMap.get("repertory")));
		goodsDo.setShopKeeperId(userInfoMap.get("userId"));

		goodsService.addGoods(goodsDo);
	}

	/**
	 * 设置商品的基本信息。
	 * 可以直接设置为string，为了系统的后续类型的增加或
	 * 更改，以免进行不必要的更新操作。
	 * @param goodsDo 商品实例
	 */
	private void setBasicInfo(GoodsDo goodsDo){
		ServiceResult<Integer> brand;
		brand = goodsService.findBrand(photoInfoMap.get("brand"));
		if (brand.isSuccess()) {
			goodsDo.setGoodsBrand(brand.getData());
		}
		brand = goodsService.findType(photoInfoMap.get("type"));
		if(brand.isSuccess()){
			goodsDo.setGoodsType(brand.getData());
		}
		brand = goodsService.findSize(photoInfoMap.get("size"));
		if(brand.isSuccess()){
			goodsDo.setGoodsSize(brand.getData());
		}
		brand = goodsService.findColor(photoInfoMap.get("color"));
		if(brand.isSuccess()){
			goodsDo.setGoodsColor(brand.getData());
		}
	}

	/**
	 * 处理post上传的文字表单数据
	 *
	 * @param request
	 * @param item
	 */
	private void handleTextInfo(HttpServletRequest request, FileItem item) {
		String name = item.getFieldName();
		String value = item.getString();
		request.setAttribute(name, value);

		if("userId".equals(name)){
			setUserInfo(item);
			return;
		}
		setMap(item);
	}
	/**
	 * 获取表单map数据
	 * @param item 表单
	 */
	private void setMap (FileItem item) {
		String key = item.getFieldName();
		String value = item.getString();
		photoInfoMap.put(key, value);

	}
	private void setUserInfo (FileItem item){

		userInfoMap.put("userId", item.getString());
	}

	/**
	 * 保存文件到服务器上
	 * @param request 请求
	 * @param item 文件对象
	 * @param first 主图
	 * @return 保存的图片的路径
	 */
	private String saveFile (HttpServletRequest request, FileItem item, boolean first) {
		//获取路径名
		String value = item.getName();
		//取到最后一个反斜杠。
		int start = -1;
		if (value.contains("\\")) {
			start = value.lastIndexOf("\\");
		} else if (value.contains("/")) {
			start = value.lastIndexOf("/");
		}
		String filename = value.substring(start + 1);
		request.setAttribute(item.getFieldName(), filename);

		///*第三方提供的方法直接写到文件中。
		//item.write(new File(path,filename));

		OutputStream out = null;
		InputStream in = null;
		String parentPath = createDir(rootPath + "/img/" + request.getAttribute("userId") + "/" + goodsId + "/");
		String tmp = filename.substring(0, filename.lastIndexOf('.'));
		File f = new File(parentPath, tmp + goodsId + ".jpg");
		try {

			out = new FileOutputStream(f);
			in = item.getInputStream();

			int length = 0;
			byte[] buf = new byte[1024];

			while ((length = in.read(buf)) != -1) {
				out.write(buf, 0, length);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(null != in) in.close();
				if(null != out) out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		int startIndex = f.getAbsolutePath().lastIndexOf("KitServer");

		return f.getAbsolutePath().substring(startIndex + "KitServer".length());
	}

	/**
	 * 创建图片的主文件夹
	 * /tmp
	 * /img
	 * /upload
	 *
	 * @param rootPath 根目录
	 */
	private void createRootDirs (String rootPath){
		File rootDir = new File(rootPath);
		File tmpDir = new File(rootDir, "tmp/");
		File imgDir = new File(rootDir, "img/");
		File photoDir = new File(rootDir, "upload/");
		Log log = new Log4JLogger();
		if(!tmpDir.exists() && !tmpDir.mkdirs()){
			log.error("can not create dir :" + tmpDir);
			createDir(rootPath + "/tmp/");
		}
		if(!imgDir.exists() && !imgDir.mkdirs()){
			log.error("can not create dir :" + imgDir);
			createDir(rootPath + "/img/");
		}
		if(!photoDir.exists() && !photoDir.mkdirs()){
			log.error("can not create dir :" + photoDir);
			createDir(rootPath + "/upload/");
		}
	}
	private String createDir(String dirPath){
		String path = dirPath.replace("/", "\\");
		File dir = new File(path);
		if(!dir.exists() && !dir.mkdirs()){
			return rootPath;
		}
		return path;
	}
}
