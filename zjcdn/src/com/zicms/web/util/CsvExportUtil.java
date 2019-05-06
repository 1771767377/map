package com.zicms.web.util;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvExportUtil {

	private static final Logger logger = LoggerFactory.getLogger(CsvExportUtil.class);

	/** CSV文件列分隔符 */
	private static final String CSV_COLUMN_SEPARATOR = ",";

	/** CSV文件行分隔符 */
	private static final String CSV_RN = "\r\n";

	/**
	 * 
	 * @param dataList 集合数据
	 * @param colNames 表头部数据
	 * @param mapKey 查找的对应数据
	 * @param response 返回结果
	 */
	public static boolean doExport(List<Map<String, Object>> dataList, String colNames, String mapKey, OutputStream os) {
		try {
			StringBuffer buf = new StringBuffer();
			String[] colNamesArr = null;
			String[] mapKeyArr = null;
			colNamesArr = colNames.split(",");
			mapKeyArr = mapKey.split(",");
			// 完成数据csv文件的封装
			// 输出列头
			for (int i = 0; i < colNamesArr.length; i++) {
				buf.append(colNamesArr[i]).append(CSV_COLUMN_SEPARATOR);
			}
			buf.append(CSV_RN);
			if (null != dataList) { // 输出数据
				for (int i = 0; i < dataList.size(); i++) {
					for (int j = 0; j < mapKeyArr.length; j++) {
						buf.append(dataList.get(i).get(mapKeyArr[j])).append(CSV_COLUMN_SEPARATOR);
					}
					buf.append(CSV_RN);
				}
			}
			// 写出响应
			os.write(buf.toString().getBytes("GBK"));
			os.flush();
			return true;
		} catch (Exception e) {
			logger.error("doExport错误...", e);
		}
		return false;
	}

	/**
	 * @throws UnsupportedEncodingException
	 * 
	 *         setHeader
	 */
	public static void responseSetProperties(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
		// 设置文件后缀
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fn = fileName + sdf.format(new Date()).toString() + ".csv";
		// 读取字符编码
		String utf = "UTF-8";

		// 设置响应
		response.setContentType("application/ms-txt.numberformat:@");
		// //之前成功代码
		// response.setContentType("application/octet-stream");//成功代码
		// response.setContentType("application/force-download"); // 测试代码
		response.setCharacterEncoding(utf);
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=30");
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fn, utf));
	}

	/**
	 * 设置让浏览器弹出下载对话框的Header. 根据浏览器的不同设置不同的编码格式 防止中文乱码
	 * 
	 * @param fileName 下载后的文件名.
	 */
	public static void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
		try {
			// 中文文件名支持
			String encodedfileName = null;
			String agent = request.getHeader("USER-AGENT");
			if (null != agent && -1 != agent.indexOf("MSIE")) {// IE
				encodedfileName = java.net.URLEncoder.encode(fileName, "UTF-8");
			} else if (null != agent && -1 != agent.indexOf("Mozilla")) {
				encodedfileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			} else {
				encodedfileName = java.net.URLEncoder.encode(fileName, "UTF-8");
			}
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
