package com.zicms.web.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class OutputUtil {

	public static void printData(HttpServletResponse res, String json)
			throws IOException {
		PrintWriter writer = res.getWriter();
		writer.print(json);
		writer.flush();
		writer.close();
	}
}
