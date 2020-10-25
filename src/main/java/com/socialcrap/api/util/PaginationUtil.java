package com.socialcrap.api.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import com.socialcrap.api.model.response.dto.PaginationResponse;

public class PaginationUtil {

	public static <T> void setPageData(Integer limit, Integer offset, PaginationResponse<T> response, Long count,
			HttpServletRequest request) {
		if (!StringUtils.isEmpty(response)) {
			response.setTotalCount(count);
			if (offset > 1 && limit < count) {
				response.setPreviousPage(getPageUrl(request, getPreviousPage(offset), limit));
			}
			response.setCurrentPage(getPageUrl(request, getCurrentPage(offset), limit));
			if (limit * offset < count) {
				response.setNextPage(getPageUrl(request, getNextPage(offset), limit));
			}
			response.setLastPage(getPageUrl(request, getLastPage(limit, offset, count), limit));
		}
	}

	private static String getPageUrl(HttpServletRequest request, Integer pageNumber, Integer pageSize) {
		StringBuilder url = new StringBuilder(request.getRequestURL());
		if (pageSize != null || pageNumber != null) {
			url.append("?");
		}
		if (pageSize != null) {
			url.append("limit=" + pageSize);
		}
		if (pageNumber != null) {
			url.append("&");
			url.append("offset=" + pageNumber);
		}
		return url.toString();
	}

	public static int getOffset(int limit, int offset) {
		offset = (offset - 1) * limit;
		return offset;
	}

	private static int getPreviousPage(Integer offset) {
		return (offset - 1);
	}

	private static int getCurrentPage(Integer offset) {
		return offset;
	}

	private static Integer getNextPage(Integer offset) {
		return offset + 1;
	}

	private static Integer getLastPage(Integer limit, Integer offset, Long count) {
		int pageNumber = (int) Math.ceil((double) count / limit);
		if(pageNumber==0) {
			return 1;
		}
		return pageNumber;
	}

}
