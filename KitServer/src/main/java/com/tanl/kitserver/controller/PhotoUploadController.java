package com.tanl.kitserver.controller;

import com.tanl.kitserver.model.bean.GoodsDo;
import com.tanl.kitserver.service.GoodsImgsService;
import com.tanl.kitserver.service.GoodsService;
import com.tanl.kitserver.util.ServiceResult;
import com.tanl.kitserver.util.common.GenerateGoodsId;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.Log4JLogger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
	@RequestMapping(value = "/uploadDetail")
	public void uploadHandle (HttpServletRequest request, HttpServletResponse response) throws IOException {

		request.setCharacterEncoding("UTF-8");

		//获得磁盘文件条目工厂。
		DiskFileItemFactory factory = new DiskFileItemFactory();
		rootPath = request.getSession().getServletContext().getRealPath("/");
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
			goodsId = GenerateGoodsId.generateString();
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

	private void insertGoodsToDB(){

		GoodsDo goodsDo = new GoodsDo();
		goodsDo.setGoodsId(goodsId);
		goodsDo.setGoodsName("名称-");

		setBasicInfo(goodsDo);

//		goodsDo.setGoodsBrandValue(photoInfoMap.get("brand"));
//		goodsDo.setGoodsTypeValue(photoInfoMap.get("type"));
//		goodsDo.setGoodsSizeValue(photoInfoMap.get("size"));
//		goodsDo.setGoodsColorValue(photoInfoMap.get("color"));

		goodsDo.setGoodsPlace(photoInfoMap.get("place"));
		goodsDo.setGoodsPrice(Float.valueOf(photoInfoMap.get("price")));
		goodsDo.setGoodsRepertory(Integer.valueOf(photoInfoMap.get("repertory")));
		goodsDo.setShopKeeperId(userInfoMap.get("userId"));

		goodsService.addGoods(goodsDo);
	}

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

	ApplicationContext context;

	@Before
	public void before() {

		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		goodsService = (GoodsService) context.getBean("goodsService");
	}
	@Test
	public void test(){
		goodsService.findSize("L");
	}

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

		File f = new File(createDir(rootPath + "/img/" + request.getAttribute("userId") + "/" + goodsId + "/"), goodsId + filename);
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
		return f.getAbsolutePath();
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
		}
		if(!imgDir.exists() && !imgDir.mkdirs()){
			log.error("can not create dir :" + imgDir);
		}
		if(!photoDir.exists() && !photoDir.mkdirs()){
			log.error("can not create dir :" + photoDir);
		}
	}
	private String createDir(String dirPath){
		File dir = new File(dirPath);
		if(!dir.exists() && !dir.mkdirs()){
			return rootPath;
		}
		return dirPath;
	}
}
