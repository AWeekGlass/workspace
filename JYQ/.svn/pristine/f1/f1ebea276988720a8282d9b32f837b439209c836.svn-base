package com.hengyu.common.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.hengyu.common.enums.ExceptionEnum;
import com.hengyu.common.exception.UploadFileTypeException;
import com.hengyu.common.exception.UploadNotSupportTypeException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileUtils {

	@Autowired
	private FTPUtils ftpUtils;
	
	private static final String PREFIX = "ftp/";

	/**
	 * 上传文件
	 * 
	 * @param logoFile
	 *            文件流
	 * @param uploadPath
	 *            本地文件路径
	 * @return
	 */
	public String uploadLocalFile(MultipartFile logoFile, String uploadPath) {
		String contentType = logoFile.getContentType();
		log.info("contentType:{}", contentType);

		String fileName = logoFile.getOriginalFilename();
		log.info("fileName:{}", fileName);
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		log.info("suffix:{}", suffix);

		File file = new File(uploadPath);
		if (!file.exists()) {
			file.mkdirs();
		}

		String logoUrl = uploadPath + File.separator + getRandomFileName() + suffix;
		log.info("logoUrl:{}", logoUrl);

		try {
			logoFile.transferTo(new File(logoUrl));
		} catch (IllegalStateException | IOException e) {
			log.error(e.getMessage(), e);
			throw new UploadFileTypeException(ExceptionEnum.FILE_UPLOAD_ERROR_CODE.getDescription());
		}
		return logoUrl;
	}

	/**
	 * 上传文件
	 * 
	 * @param MultipartFile
	 *            文件流
	 * @param uploadPath
	 *            文件路径
	 * @return
	 */
	public String uploadFtpFile(MultipartFile logoFile, String uploadPath) {
		// 获取上传后的文件名
		String name = ftpUtils.uploadMultipartFile(uploadPath, logoFile, getRandomFileName());
		// 返回文件路径
		String logoUrl = uploadPath + name;
		log.info("logoUrl:{}", logoUrl);
		return logoUrl;
	}

	/**
	 * 上传文件
	 * 
	 * @param logoFile
	 *            文件流
	 * @param uploadPath
	 *            文件路径
	 * @return
	 */
	public String uploadFtpFile(InputStream inputStream, String uploadPath, String fileName) {
		log.info("初始fileName:{}", fileName);
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		log.info("suffix:{}", suffix);
		String name = ftpUtils.uploadInputStream(uploadPath, inputStream, getRandomFileName() + suffix);
		log.info("上传后文件名:{}", name);
		String logoUrl = uploadPath + name;
		log.info("logoUrl:{}", logoUrl);
		return logoUrl;
	}

	/**
	 * 上传图片
	 * 
	 * @param logoFile
	 * @param uploadPath
	 * @return
	 */
	public String uploadLocalIMG(MultipartFile logoFile, String uploadPath) {
		String contentType = logoFile.getContentType();
		log.info("contentType:{}", contentType);

		List<String> imgContentTypes = new ArrayList<String>() {
			private static final long serialVersionUID = 8909226489492717935L;
			{
				add("image/png");
				add("image/jpeg");
			}
		};

		if (!imgContentTypes.contains(contentType)) {
			throw new UploadNotSupportTypeException(ExceptionEnum.FILE_NOT_SUPPORT_ERROR_CODE.getDescription());
		}

		String fileName = logoFile.getOriginalFilename();
		log.info("fileName:{}", fileName);
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		log.info("suffix:{}", suffix);

		File file = new File(uploadPath);
		if (!file.exists()) {
			file.mkdirs();
		}

		String logoUrl = uploadPath + File.separator + getRandomFileName() + suffix;
		log.info("logoUrl:{}", logoUrl);

		try {
			logoFile.transferTo(new File(logoUrl));
		} catch (IllegalStateException | IOException e) {
			log.error(e.getMessage(), e);
			throw new UploadFileTypeException(ExceptionEnum.FILE_UPLOAD_ERROR_CODE.getDescription());
		}
		return logoUrl;
	}

	/**
	 * 上传图片
	 * 
	 * @param logoFile
	 *            MultipartFile文件流
	 * @param uploadPath
	 *            文件路径
	 * @return
	 */
	public String uploadFtpImg(MultipartFile logoFile, String uploadPath) {
		String contentType = logoFile.getContentType();
		log.info("contentType:{}", contentType);
		List<String> imgContentTypes = new ArrayList<String>() {
			private static final long serialVersionUID = 8909226489492717935L;
			{
				add("image/png");
				add("image/jpeg");
			}
		};

		if (!imgContentTypes.contains(contentType)) {
			throw new UploadNotSupportTypeException(ExceptionEnum.FILE_NOT_SUPPORT_ERROR_CODE.getDescription());
		}
		String name = ftpUtils.uploadMultipartFile(uploadPath, logoFile, getRandomFileName());
		String logoUrl = uploadPath + name;
		log.info("logoUrl:{}", logoUrl);
		return logoUrl;
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 *            ftp文件路径
	 * @return
	 */
	public boolean deleteFTPFile(String filePath) {
		boolean flag = ftpUtils.deleteFile(filePath);
		return flag;
	}

	/**
	 * 根据名称获取文件,以String类型返回文件内容
	 * 
	 * @param ftpPath
	 *            FTP服务器文件相对路径，例如：test/123
	 * @param fileName
	 *            文件名，例如：test.xls
	 * @return String
	 * @throws IOException
	 */
	public String getFileByName(String ftpPath, String fileName) throws IOException {
		return ftpUtils.getFileByName(ftpPath, fileName);
	}

	/**
	 * 生成随机文件名
	 * 
	 * @return
	 */
	private String getRandomFileName() {
		Random random = new Random();
		int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
		return FastDateFormat.getInstance("yyyyMMddHHmmss").format(new Date()) + rannum;
	}

	/**
	 * 获取文件大小
	 * 
	 * @param size
	 *            文件大小单位b
	 * @return B,KB,MB,GB
	 */
	public String getPrintSize(long size) {
		// 如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
		if (size < 1024) {
			return String.valueOf(size) + "B";
		} else {
			size = size / 1024;
		}
		// 如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
		// 因为还没有到达要使用另一个单位的时候
		// 接下去以此类推
		if (size < 1024) {
			return String.valueOf(size) + "KB";
		} else {
			size = size / 1024;
		}
		if (size < 1024) {
			// 因为如果以MB为单位的话，要保留最后1位小数，
			// 因此，把此数乘以100之后再取余
			size = size * 100;
			return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "MB";
		} else {
			// 否则如果要以GB为单位的，先除于1024再作同样的处理
			size = size * 100 / 1024;
			return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "GB";
		}
	}

	/**
	 * 获取文件大小
	 * @param size
	 * @return K
	 */
	public  String getFileSize(long size) {
		return String.valueOf(size / 1024);
	}
}
