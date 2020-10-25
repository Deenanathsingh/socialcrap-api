package com.socialcrap.api.common.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.socialcrap.api.common.enums.UploadTag;
import com.socialcrap.api.common.service.FileService;
import com.socialcrap.api.util.FileUtil;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public Map<String, String> uploadFile(MultipartFile file) {

		String uploadedFileType = FileUtil.getFileType(file);

		String uploadedFileUrl = null;
		if (FileUtil.getVideoFormats().contains(uploadedFileType)
				|| FileUtil.getImageFormats().contains(uploadedFileType)) {
			try {
				uploadedFileUrl = FileUtil.uploadFile(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Map<String, String> data = new HashMap<>();
		data.put("url", uploadedFileUrl);
		data.put("tag", UploadTag.UNKNOWN.toString());

		if (FileUtil.getVideoFormats().contains(uploadedFileType)) {
			data.put("tag", UploadTag.VIDEO.toString());
		}
		if (FileUtil.getImageFormats().contains(uploadedFileType)) {
			data.put("tag", UploadTag.PHOTO.toString());
		}

		return data;
	}

	@Override
	public List<String> attachFiles(MultipartFile[] files) {
		if (files.length > 0) {
			List<String> attachments = new ArrayList<>();
			try {
				File[] fileList = FileUtil.getFilesToUpload(files);
				for (File file : fileList) {
					attachments.add(file.getAbsolutePath());
				}
				return attachments;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
