package com.student.course.registration.base.interceptors.requestPath;

public class RequestContextHolder {
    private static final ThreadLocal<String> requestPath = new ThreadLocal<>();

    public static void setPath(String path) {
        requestPath.set(path);
    }

    public static String getPath() {
        return requestPath.get();
    }

    public static void clear() {
        requestPath.remove();
    }
}