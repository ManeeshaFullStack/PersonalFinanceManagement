package com.personalfinance.utility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtils {

    // Retrieves the existing session; returns null if no session exists
    public static HttpSession getSession(HttpServletRequest request) {
        return request.getSession(false);
    }

    // Retrieves the existing session; creates one if it doesn't exist
    public static HttpSession getOrCreateSession(HttpServletRequest request) {
        return request.getSession(true);
    }

    // Retrieves an attribute from the session
    public static Object getAttribute(HttpServletRequest request, String key) {
        HttpSession session = getSession(request);
        return (session != null) ? session.getAttribute(key) : null;
    }

    // Sets an attribute in the session
    public static void setAttribute(HttpServletRequest request, String key, Object value) {
        HttpSession session = getOrCreateSession(request);
        session.setAttribute(key, value);
    }

    // Removes an attribute from the session
    public static void removeAttribute(HttpServletRequest request, String key) {
        HttpSession session = getSession(request);
        if (session != null) {
            session.removeAttribute(key);
        }
    }

    // Invalidates the session
    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = getSession(request);
        if (session != null) {
            session.invalidate();
        }
    }
}
