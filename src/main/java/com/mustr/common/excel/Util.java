package com.mustr.common.excel;

import java.util.Collection;

public class Util {

	public static boolean isNotEmpty(Collection<?> coll) {
		return coll != null && !coll.isEmpty();
	}
}
