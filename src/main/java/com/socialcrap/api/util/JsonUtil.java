package com.socialcrap.api.util;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	private static ObjectMapper objectMapper = null;

	static {
		objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.setVisibility(PropertyAccessor.GETTER, Visibility.ANY);
	}

	public static <T> String getJson(T data) {
		try {
			return objectMapper.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T convertToPojo(String json, Class<T> type) {
		try {
			return objectMapper.readValue(json, type);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> List<T> convertToListPojo(String json, Class<T> type) {
		try {
			return objectMapper.readValue(json, new TypeReference<List<T>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void writeValue(Writer w, Object data) {
		try {
			objectMapper.writeValue(w, data);
		} catch (JsonGenerationException e) {

		} catch (JsonMappingException e) {

		} catch (IOException e) {

		}
	}

}
