package com.cmcc.zysoft.util;

import java.util.UUID;

public class UUIDUtil {
	
	public static String generate() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
}
