package com.jero.filter.wrapper;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.util.Arrays;
import java.util.Calendar;

/**
 * @Description 添加过期时间的响应头拓展
 * @Author lixuetao
 * @Date 2020/3/24
 **/
public class ExpiresHeaderResponse extends HttpServletResponseWrapper {

    private static final String[] CACHEABLE_CONTENT_TYPES = new String[]{
            "text/css",
            "text/javascript",
            "application/javascript",
            "image/png",
            "image/jpeg",
            "image/gif",
            "image/jpg"
    };

    static {
        Arrays.sort(CACHEABLE_CONTENT_TYPES);
    }

    /**
     * maxAge
     */
    private long maxAge = 0;

    public ExpiresHeaderResponse(HttpServletResponse response, long maxAge) {
        super(response);
        this.maxAge = maxAge;
    }

    @Override
    public void setContentType(String contentType) {
        if (contentType != null && Arrays.binarySearch(CACHEABLE_CONTENT_TYPES, contentType) > -1) {
            Calendar inTwoMonths = Calendar.getInstance();
            inTwoMonths.add(Calendar.MONTH, 2);

            super.setDateHeader("Expires", inTwoMonths.getTimeInMillis());
            super.setHeader("Cache-Control", "max-age=" + maxAge);
        } else {
            super.setHeader("Expires", "-1");
            super.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        }
        super.setContentType(contentType);
    }

}
