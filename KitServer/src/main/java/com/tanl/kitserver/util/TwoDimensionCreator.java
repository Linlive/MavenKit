package com.tanl.kitserver.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

/**
 * 二维码生成器
 * Created by Administrator on 2016/6/8.
 */
public class TwoDimensionCreator {

	public static void main (String[] args) throws WriterException {

		String url = ServerCode.SERVER_ADDRESS + "/downloadApk";
		int width = 300;
		int height = 300;
		String format = "png";
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		//hints.put(EncodeHintType.MARGIN, 1);//设置二维码边的空度，非负数

		BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);
		File outputFile = new File("./KitServer/target/KitServer/TravelKit.jpg");
		try {
			MatrixToImageWriter.writeToStream(bitMatrix, format, new FileOutputStream(outputFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
