package com.socialcrap.api.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

public class FileUtil {

	@SuppressWarnings("unchecked")
	public static String uploadFile(MultipartFile multipartFile) throws Exception {
		Cloudinary cloudinary = new Cloudinary(setCloudinaryProperties());
		File file = getFileToUpload(multipartFile);
		Map<String, ?> map = cloudinary.uploader().upload(file, getCloudinaryResourceType(multipartFile));
		file.delete();
		return (String) map.get("url");
	}

	public static String getFileType(MultipartFile file) {
		String type = file.getOriginalFilename().split("\\.")[1].toUpperCase();
		return type;
	}

	public static File getFileToUpload(MultipartFile multipartFile) throws Exception {
		File directory = new File(System.getProperty("java.io.tmpdir") + "/uploads/tmp");
		if (!directory.exists()) {
			directory.mkdirs();
		}
		File file = new File(directory + "/" + multipartFile.getOriginalFilename());
		multipartFile.transferTo(file);
		return file;
	}

	public static File[] getFilesToUpload(MultipartFile[] multipartFiles) throws Exception {
		File[] files = new File[multipartFiles.length];
		for (int i = 0; i < multipartFiles.length; i++) {
			files[i] = getFileToUpload(multipartFiles[i]);
		}
		return files;
	}

	private static Map<String, String> setCloudinaryProperties() {
		Map<String, String> properties = new HashMap<>();
		properties.put("cloud_name", "duqrupa4a");
		properties.put("api_key", "252564291693762");
		properties.put("api_secret", "YCC2s73xaSxMUArg2MgSPdJQ158");
		return properties;
	}

	private static Map<String, String> getCloudinaryResourceType(MultipartFile file) {
		Map<String, String> params = new HashMap<>();
		params.put("resource_type", "video");
		if (getImageFormats().contains(getFileType(file))) {
			params.put("resource_type", "image");
			return params;
		}
		return params;
	}

	public static List<String> getImageFormats() {
		return new ArrayList<>(Arrays.asList("JPEG", "JPG", "PNG", "GIF", "PSD"));
	}

	public static List<String> getVideoFormats() {
		return new ArrayList<>(
				Arrays.asList("MPEG4", "MP4", "AVI", "WMV", "MPEGPS", "FLV", "3GPP", "3GP", "WebM", "HEVC"));
	}
}
