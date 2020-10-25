package com.socialcrap.api.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	public Map<String,String> uploadFile(MultipartFile file);
	public List<String> attachFiles(MultipartFile[] files);
}
