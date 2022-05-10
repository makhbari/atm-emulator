package com.asanpardakht.atmemulator.util;

import javax.servlet.http.HttpServletRequest;

public class HttpRequestUtil {
    private static final String IP_HEADER = "X-Forwarded-For";

    public static String getIpAddress(HttpServletRequest request) {
        String ip = "";
        if (request.getHeader(IP_HEADER) != null && !request.getHeader(IP_HEADER).isEmpty()) {
            if (request.getHeader(IP_HEADER).contains(",")) {
                String[] ipAddresses = request.getHeader(IP_HEADER).split(",");
                ip = ipAddresses[ipAddresses.length - 1];
            } else {
                ip = request.getHeader(IP_HEADER);
            }
        }
        return ip;
    }
}
