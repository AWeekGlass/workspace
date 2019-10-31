package com.hengyu.system.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hengyu.common.msg.RestResponse;
import com.hengyu.common.util.FileUtils;

import io.swagger.annotations.Api;

@Api(value = "FileController", tags = "文件Controller")
@RestController
@RequestMapping("/file")
public class FileController {
	
	@Autowired
	private FileUtils fileUtils;
	
	public RestResponse<List<String>> uploadFile(@RequestParam(value = "files", required = true) MultipartFile[] files,String path){
		if (Objects.nonNull(files) && files.length > 0) {
			List<String> paths = new ArrayList<>();
			for (MultipartFile file : files) {
				String filePath = fileUtils.uploadFtpFile(file, path);
				paths.add(filePath);
			}
			return new RestResponse<List<String>>().rel(true).data(paths);
		}
		return new RestResponse<List<String>>().rel(false).message("上传失败");
	}

}
