package com.hengyu.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FTPUtils {
	/** FTP地址 **/
	@Value("${ftp.address}")
	private String FTP_ADDRESS;

	/** FTP端口 **/
	private static final int FTP_PORT = 21;

	/** FTP用户名 **/
	private static final String FTP_USERNAME = "test";

	/** FTP密码 **/
	@Value("${ftp.password}")
	private String FTP_PASSWORD;

	/** FTP基础目录 **/
	private static final String BASE_PATH = "pub/";

	/** 本地字符编码 **/
	private static String localCharset = "GBK";

	/** FTP协议里面，规定文件名编码为iso-8859-1 **/
	private static String serverCharset = "ISO-8859-1";

	/** UTF-8字符编码 **/
	private static final String CHARSET_UTF8 = "UTF-8";

	/** OPTS UTF8字符串常量 **/
	private static final String OPTS_UTF8 = "OPTS UTF8";

	/** 设置缓冲区大小4M **/
	private static final int BUFFER_SIZE = 1024 * 1024 * 4;

	/** FTPClient对象 **/
	private static FTPClient ftpClient = null;

	/**
	 * 本地文件上传到FTP服务器
	 * 
	 * @param ftpPath
	 *            FTP服务器文件相对路径，例如：test/123
	 * @param savePath
	 *            本地文件路径，例如：D:/test/123/test.txt
	 * @param fileName
	 *            上传到FTP服务的文件名，例如：666.txt
	 * @return boolean 成功返回true，否则返回false
	 */
	public boolean uploadLocalFile(String ftpPath, String savePath, String fileName) {
		// 登录
		login(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD);
		boolean flag = false;
		if (ftpClient != null) {
			File file = new File(savePath);
			try (FileInputStream fis = new FileInputStream(file)) {
				ftpClient.setBufferSize(BUFFER_SIZE);
				// 设置编码：开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）
				if (FTPReply.isPositiveCompletion(ftpClient.sendCommand(OPTS_UTF8, "ON"))) {
					localCharset = CHARSET_UTF8;
				}
				ftpClient.setControlEncoding(localCharset);
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				String path = changeEncoding(BASE_PATH + ftpPath);
				// 目录不存在，则递归创建
				if (!ftpClient.changeWorkingDirectory(path)) {
					this.createDirectorys(path);
				}
				// 设置被动模式，开通一个端口来传输数据
				ftpClient.enterLocalPassiveMode();
				// 上传文件
				flag = ftpClient.storeFile(new String(fileName.getBytes(localCharset), serverCharset), fis);
			} catch (Exception e) {
				log.error("本地文件上传FTP失败", e);
			} finally {
				closeConnect();
			}
		}
		return flag;
	}

	/**
	 * MultipartFile上传到FTP服务器
	 * 
	 * @param ftpPath
	 *            FTP服务器文件相对路径，例如：test/123
	 * @param logoFile
	 *            前端传递的文件流
	 * @param fileName
	 *            上传到FTP服务的文件名，例如：666.txt
	 * @return boolean 成功返回true，否则返回false
	 */
	public String uploadMultipartFile(String ftpPath, MultipartFile logoFile, String fileName) {
		// 获取原始名称
		String name = logoFile.getOriginalFilename();
		log.info("name:{}", name);
		// 获取后缀
		String suffix = name.substring(name.lastIndexOf("."));
		InputStream inputStream;
		try {
			inputStream = logoFile.getInputStream();
			fileName = fileName + suffix;
			uploadInputStream(ftpPath, inputStream, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	/**
	 * InputStream上传到FTP服务器
	 * 
	 * @param ftpPath
	 *            FTP服务器文件相对路径，例如：test/123
	 * @param inputStream
	 *            前端传递的文件流
	 * @param fileName
	 *            上传到FTP服务的文件名，例如：666.txt
	 * @return boolean 成功返回true，否则返回false
	 */
	public String uploadInputStream(String ftpPath, InputStream inputStream, String fileName) {
		// 登录
		login(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD);
		boolean flag = false;
		if (ftpClient != null) {
			try (InputStream fis = inputStream;) {
				ftpClient.setBufferSize(BUFFER_SIZE);
				// 设置编码：开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）
				if (FTPReply.isPositiveCompletion(ftpClient.sendCommand(OPTS_UTF8, "ON"))) {
					localCharset = CHARSET_UTF8;
				}
				ftpClient.setControlEncoding(localCharset);
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				String path = changeEncoding(BASE_PATH+ftpPath);
				// 目录不存在，则递归创建
				if (!ftpClient.changeWorkingDirectory(path)) {
					this.createDirectorys(path);
				}
				// 设置被动模式，开通一个端口来传输数据
				ftpClient.enterLocalPassiveMode();
				// 上传文件
				flag = ftpClient.storeFile(new String(fileName.getBytes(localCharset), serverCharset), fis);
				return fileName;
			} catch (Exception e) {
				log.error("文件流上传到FTP失败", e);
			} finally {
				closeConnect();
			}
		}
		return null;
	}

	/**
	 * 远程文件上传到FTP服务器
	 * 
	 * @param ftpPath
	 *            FTP服务器文件相对路径，例如：test/123
	 * @param remotePath
	 *            远程文件路径，例如：http://www.baidu.com/xxx/xxx.jpg
	 * @param fileName
	 *            上传到FTP服务的文件名，例如：test.jpg
	 * @return boolean 成功返回true，否则返回false
	 */
	public boolean uploadRemoteFile(String ftpPath, String remotePath, String fileName) {
		// 登录
		login(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD);
		boolean flag = false;
		if (ftpClient != null) {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse response = null;
			try {
				// 远程获取文件输入流
				HttpGet httpget = new HttpGet(remotePath);
				response = httpClient.execute(httpget);
				HttpEntity entity = response.getEntity();
				InputStream input = entity.getContent();
				ftpClient.setBufferSize(BUFFER_SIZE);
				// 设置编码：开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）
				if (FTPReply.isPositiveCompletion(ftpClient.sendCommand(OPTS_UTF8, "ON"))) {
					localCharset = CHARSET_UTF8;
				}
				ftpClient.setControlEncoding(localCharset);
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				String path = changeEncoding(BASE_PATH + ftpPath);
				// 目录不存在，则递归创建
				if (!ftpClient.changeWorkingDirectory(path)) {
					this.createDirectorys(path);
				}
				// 设置被动模式，开通一个端口来传输数据
				ftpClient.enterLocalPassiveMode();
				// 上传文件
				flag = ftpClient.storeFile(new String(fileName.getBytes(localCharset), serverCharset), input);
			} catch (Exception e) {
				log.error("远程文件上传FTP失败", e);
			} finally {
				closeConnect();
				try {
					httpClient.close();
				} catch (IOException e) {
					log.error("关闭流失败", e);
				}
				if (response != null) {
					try {
						response.close();
					} catch (IOException e) {
						log.error("关闭流失败", e);
					}
				}
			}
		}
		return flag;
	}

	/**
	 * 下载指定文件到本地
	 * 
	 * @param ftpPath
	 *            FTP服务器文件相对路径，例如：test/123
	 * @param fileName
	 *            要下载的文件名，例如：test.txt
	 * @param savePath
	 *            保存文件到本地的路径，例如：D:/test
	 * @return 成功返回true，否则返回false
	 */
	public boolean downloadFile(String ftpPath, String fileName, String savePath) {
		// 登录
		login(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD);
		boolean flag = false;
		if (ftpClient != null) {
			try {
				String path = changeEncoding(BASE_PATH + ftpPath);
				// 判断是否存在该目录
				if (!ftpClient.changeWorkingDirectory(path)) {
					log.error(BASE_PATH + ftpPath + "该目录不存在");
					return flag;
				}
				ftpClient.enterLocalPassiveMode(); // 设置被动模式，开通一个端口来传输数据
				String[] fs = ftpClient.listNames();
				// 判断该目录下是否有文件
				if (fs == null || fs.length == 0) {
					log.error(BASE_PATH + ftpPath + "该目录下没有文件");
					return flag;
				}
				for (String ff : fs) {
					String ftpName = new String(ff.getBytes(serverCharset), localCharset);
					if (ftpName.equals(fileName)) {
						File file = new File(savePath + '/' + ftpName);
						try (OutputStream os = new FileOutputStream(file)) {
							flag = ftpClient.retrieveFile(ff, os);
						} catch (Exception e) {
							log.error(e.getMessage(), e);
						}
						break;
					}
				}
			} catch (IOException e) {
				log.error("下载文件失败", e);
			} finally {
				closeConnect();
			}
		}
		return flag;
	}

	/**
	 * 下载该目录下所有文件到本地
	 * 
	 * @param ftpPath
	 *            FTP服务器上的相对路径，例如：test/123
	 * @param savePath
	 *            保存文件到本地的路径，例如：D:/test
	 * @return 成功返回true，否则返回false
	 */
	public boolean downloadFiles(String ftpPath, String savePath) {
		// 登录
		login(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD);
		boolean flag = false;
		if (ftpClient != null) {
			try {
				String path = changeEncoding(BASE_PATH + ftpPath);
				// 判断是否存在该目录
				if (!ftpClient.changeWorkingDirectory(path)) {
					log.error(BASE_PATH + ftpPath + "该目录不存在");
					return flag;
				}
				ftpClient.enterLocalPassiveMode(); // 设置被动模式，开通一个端口来传输数据
				String[] fs = ftpClient.listNames();
				// 判断该目录下是否有文件
				if (fs == null || fs.length == 0) {
					log.error(BASE_PATH + ftpPath + "该目录下没有文件");
					return flag;
				}
				for (String ff : fs) {
					String ftpName = new String(ff.getBytes(serverCharset), localCharset);
					File file = new File(savePath + '/' + ftpName);
					try (OutputStream os = new FileOutputStream(file)) {
						ftpClient.retrieveFile(ff, os);
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				}
				flag = true;
			} catch (IOException e) {
				log.error("下载文件失败", e);
			} finally {
				closeConnect();
			}
		}
		return flag;
	}

	/**
	 * 获取该目录下所有文件,以字节数组返回
	 * 
	 * @param ftpPath
	 *            FTP服务器上文件所在相对路径，例如：test/123
	 * @return Map<String, Object> 其中key为文件名，value为字节数组对象
	 */
	public Map<String, byte[]> getFileBytes(String ftpPath) {
		// 登录
		login(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD);
		Map<String, byte[]> map = new HashMap<>();
		if (ftpClient != null) {
			try {
				String path = changeEncoding(BASE_PATH + ftpPath);
				// 判断是否存在该目录
				if (!ftpClient.changeWorkingDirectory(path)) {
					log.error(BASE_PATH + ftpPath + "该目录不存在");
					return map;
				}
				ftpClient.enterLocalPassiveMode(); // 设置被动模式，开通一个端口来传输数据
				String[] fs = ftpClient.listNames();
				// 判断该目录下是否有文件
				if (fs == null || fs.length == 0) {
					log.error(BASE_PATH + ftpPath + "该目录下没有文件");
					return map;
				}
				for (String ff : fs) {
					try (InputStream is = ftpClient.retrieveFileStream(ff)) {
						String ftpName = new String(ff.getBytes(serverCharset), localCharset);
						ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
						byte[] buffer = new byte[BUFFER_SIZE];
						int readLength = 0;
						while ((readLength = is.read(buffer, 0, BUFFER_SIZE)) > 0) {
							byteStream.write(buffer, 0, readLength);
						}
						map.put(ftpName, byteStream.toByteArray());
						ftpClient.completePendingCommand(); // 处理多个文件
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				}
			} catch (IOException e) {
				log.error("获取文件失败", e);
			} finally {
				closeConnect();
			}
		}
		return map;
	}

	/**
	 * 根据名称获取文件，以字节数组返回
	 * 
	 * @param ftpPath
	 *            FTP服务器文件相对路径，例如：test/123
	 * @param fileName
	 *            文件名，例如：test.xls
	 * @return byte[] 字节数组对象
	 */
	public byte[] getFileBytesByName(String ftpPath, String fileName) {
		// 登录
		login(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD);
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		if (ftpClient != null) {
			try {
				String path = changeEncoding(BASE_PATH + ftpPath);
				// 判断是否存在该目录
				if (!ftpClient.changeWorkingDirectory(path)) {
					log.error(BASE_PATH + ftpPath + "该目录不存在");
					return byteStream.toByteArray();
				}
				ftpClient.enterLocalPassiveMode(); // 设置被动模式，开通一个端口来传输数据
				String[] fs = ftpClient.listNames();
				// 判断该目录下是否有文件
				if (fs == null || fs.length == 0) {
					log.error(BASE_PATH + ftpPath + "该目录下没有文件");
					return byteStream.toByteArray();
				}
				for (String ff : fs) {
					String ftpName = new String(ff.getBytes(serverCharset), localCharset);
					if (ftpName.equals(fileName)) {
						try (InputStream is = ftpClient.retrieveFileStream(ff);) {
							byte[] buffer = new byte[BUFFER_SIZE];
							int len = -1;
							while ((len = is.read(buffer, 0, BUFFER_SIZE)) != -1) {
								byteStream.write(buffer, 0, len);
							}
						} catch (Exception e) {
							log.error(e.getMessage(), e);
						}
						break;
					}
				}
			} catch (IOException e) {
				log.error("获取文件失败", e);
			} finally {
				closeConnect();
			}
		}
		return byteStream.toByteArray();
	}

	/**
	 * 获取该目录下所有文件,以输入流返回
	 * 
	 * @param ftpPath
	 *            FTP服务器上文件相对路径，例如：test/123
	 * @return Map<String, InputStream> 其中key为文件名，value为输入流对象
	 */
	public Map<String, InputStream> getFileInputStream(String ftpPath) {
		// 登录
		login(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD);
		Map<String, InputStream> map = new HashMap<>();
		if (ftpClient != null) {
			try {
				String path = changeEncoding(BASE_PATH + ftpPath);
				// 判断是否存在该目录
				if (!ftpClient.changeWorkingDirectory(path)) {
					log.error(BASE_PATH + ftpPath + "该目录不存在");
					return map;
				}
				ftpClient.enterLocalPassiveMode(); // 设置被动模式，开通一个端口来传输数据
				String[] fs = ftpClient.listNames();
				// 判断该目录下是否有文件
				if (fs == null || fs.length == 0) {
					log.error(BASE_PATH + ftpPath + "该目录下没有文件");
					return map;
				}
				for (String ff : fs) {
					String ftpName = new String(ff.getBytes(serverCharset), localCharset);
					InputStream is = ftpClient.retrieveFileStream(ff);
					map.put(ftpName, is);
					ftpClient.completePendingCommand(); // 处理多个文件
				}
			} catch (IOException e) {
				log.error("获取文件失败", e);
			} finally {
				closeConnect();
			}
		}
		return map;
	}

	/**
	 * 根据名称获取文件，以输入流返回
	 * 
	 * @param ftpPath
	 *            FTP服务器上文件相对路径，例如：test/123
	 * @param fileName
	 *            文件名，例如：test.txt
	 * @return InputStream 输入流对象
	 */
	public InputStream getInputStreamByName(String ftpPath, String fileName) {
		// 登录
		login(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD);
		InputStream input = null;
		if (ftpClient != null) {
			try {
				String path = changeEncoding(BASE_PATH + ftpPath);
				// 判断是否存在该目录
				if (!ftpClient.changeWorkingDirectory(path)) {
					log.error(BASE_PATH + ftpPath + "该目录不存在");
					return input;
				}
				ftpClient.enterLocalPassiveMode(); // 设置被动模式，开通一个端口来传输数据
				String[] fs = ftpClient.listNames();
				// 判断该目录下是否有文件
				if (fs == null || fs.length == 0) {
					log.error(BASE_PATH + ftpPath + "该目录下没有文件");
					return input;
				}
				for (String ff : fs) {
					String ftpName = new String(ff.getBytes(serverCharset), localCharset);
					if (ftpName.equals(fileName)) {
						input = ftpClient.retrieveFileStream(ff);
						break;
					}
				}
			} catch (IOException e) {
				log.error("获取文件失败", e);
			} finally {
				closeConnect();
			}
		}
		return input;
	}

	/**
	 * 删除指定文件
	 * 
	 * @param filePath
	 *            文件相对路径，例如：test/123/test.txt
	 * @return 成功返回true，否则返回false
	 */
	public boolean deleteFile(String filePath) {
		// 登录
		login(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD);
		boolean flag = false;
		if (ftpClient != null) {
			try {
				String path = changeEncoding(BASE_PATH + filePath);
				flag = ftpClient.deleteFile(path);
			} catch (IOException e) {
				log.error("删除文件失败", e);
			} finally {
				closeConnect();
			}
		}
		return flag;
	}

	/**
	 * 删除目录下所有文件
	 * 
	 * @param dirPath
	 *            文件相对路径，例如：test/123
	 * @return 成功返回true，否则返回false
	 */
	public boolean deleteFiles(String dirPath) {
		// 登录
		login(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD);
		boolean flag = false;
		if (ftpClient != null) {
			try {
				ftpClient.enterLocalPassiveMode(); // 设置被动模式，开通一个端口来传输数据
				String path = changeEncoding(BASE_PATH + dirPath);
				String[] fs = ftpClient.listNames(path);
				// 判断该目录下是否有文件
				if (fs == null || fs.length == 0) {
					log.error(BASE_PATH + dirPath + "该目录下没有文件");
					return flag;
				}
				for (String ftpFile : fs) {
					ftpClient.deleteFile(ftpFile);
				}
				flag = true;
			} catch (IOException e) {
				log.error("删除文件失败", e);
			} finally {
				closeConnect();
			}
		}
		return flag;
	}

	/**
	 * 连接FTP服务器
	 * 
	 * @param address 地址，如：127.0.0.1
	 * @param port 端口，如：21
	 * @param username 用户名，如：root
	 * @param password 密码，如：root
	 */
	private void login(String address, int port, String username, String password) {
		ftpClient = new FTPClient();
		try {
			ftpClient.connect(address, port);
			ftpClient.login(username, password);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				closeConnect();
				log.error("FTP服务器连接失败");
			}
		} catch (Exception e) {
			log.error("FTP登录失败", e);
		}
	}

	/**
	 * 关闭FTP连接
	 * 
	 */
	private void closeConnect() {
		if (ftpClient != null && ftpClient.isConnected()) {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException e) {
				log.error("关闭FTP连接失败", e);
			}
		}
	}

	/**
	 * FTP服务器路径编码转换
	 * 
	 * @param ftpPath
	 *            FTP服务器路径
	 * @return String
	 */
	private static String changeEncoding(String ftpPath) {
		String directory = null;
		try {
			if (FTPReply.isPositiveCompletion(ftpClient.sendCommand(OPTS_UTF8, "ON"))) {
				localCharset = CHARSET_UTF8;
			}
			directory = new String(ftpPath.getBytes(localCharset), serverCharset);
		} catch (Exception e) {
			log.error("路径编码转换失败", e);
		}
		return directory;
	}

	/**
	 * 在服务器上递归创建目录
	 * 
	 * @param dirPath
	 *            上传目录路径
	 * @return
	 */
	private void createDirectorys(String dirPath) {
		try {
			if (!dirPath.endsWith("/")) {
				dirPath += "/";
			}
			String directory = dirPath.substring(0, dirPath.lastIndexOf("/") + 1);
			ftpClient.makeDirectory("/");
			int start = 0;
			int end = 0;
			if (directory.startsWith("/")) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf("/", start);
			while (true) {
				String subDirectory = new String(dirPath.substring(start, end));
				if (!ftpClient.changeWorkingDirectory(subDirectory)) {
					if (ftpClient.makeDirectory(subDirectory)) {
						ftpClient.changeWorkingDirectory(subDirectory);
					} else {
						log.info("创建目录失败");
						return;
					}
				}
				start = end + 1;
				end = directory.indexOf("/", start);
				// 检查所有目录是否创建完毕
				if (end <= start) {
					break;
				}
			}
		} catch (Exception e) {
			log.error("上传目录创建失败", e);
		}
	}

	/**
	 * 根据文件路径和名称获取文件内容以String返回
	 * 
	 * @param dirPath
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public String getFileByName(String dirPath, String name) {
		try (InputStream inputStream = getInputStreamByName(dirPath, name); StringWriter writer = new StringWriter();) {
			if(Objects.isNull(inputStream)) {
				return null;
			}
			IOUtils.copy(inputStream, writer, StandardCharsets.UTF_8.name());
			String str = writer.toString();
			return str;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 生成随机文件名
	 * 
	 * @return
	 */
	public String randomFileName() {
		Random random = new Random();
		int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
		return FastDateFormat.getInstance("yyyyMMddHHmmss").format(new Date()) + rannum;
	}
}
