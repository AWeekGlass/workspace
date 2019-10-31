package com.hengyu.common.web;

import static com.hengyu.common.constant.CommonConstants.TOKEN;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.hengyu.common.jwt.IJWTInfo;
import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.JWTUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BaseController {
	@Value("${file.path}")
	private String fileUrl;

	@Autowired
	protected JWTUtils jwtUtils;

	@GetMapping("companyName")
	public RestResponse<Map<String, Object>> companyName(@RequestHeader(TOKEN) String token) throws Exception {
		IJWTInfo info = jwtUtils.getInfoFromToken(token);
		Map<String, Object> map = new HashMap<>();
		map.put("companyId", info.getCompanyId());
		map.put("companyName", info.getCompanyName());
		return new RestResponse<Map<String, Object>>().rel(true).data(map);
	}

	@GetMapping("download")
	protected ResponseEntity<byte[]> download(String filePath, String fileName) throws IOException, URISyntaxException {
		log.info("fileName:{},filePath:{}", fileName, filePath);

		String path = fileUrl + filePath;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/html; charset=UTF-8");
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(3 * 1000);// 防止屏蔽程序抓取而返回403错误
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		try {
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
			return new ResponseEntity<>(IOUtils.toByteArray(conn.getInputStream()), headers, HttpStatus.CREATED);
		} catch (IOException e) {
			headers.clear();
			headers.add("Content-Type", "application/json; charset=UTF-8");
			headers.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
			headers.setLocation(url.toURI());
			headers.set("file_not_found", fileName);
			return new ResponseEntity<>("文件路径不正确！".getBytes(), headers, HttpStatus.NOT_FOUND);
		}
	}
}
