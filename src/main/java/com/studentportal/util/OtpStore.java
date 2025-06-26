package com.studentportal.util;

import java.util.HashMap;
import java.util.Map;

public class OtpStore {
    private static final Map<String, String> otpMap = new HashMap<>();

    public static void storeOtp(String mobile, String otp) {
        otpMap.put(mobile, otp);
    }

    public static String getOtp(String mobile) {
        return otpMap.get(mobile);
    }

    public static void removeOtp(String mobile) {
        otpMap.remove(mobile);
    }
}
