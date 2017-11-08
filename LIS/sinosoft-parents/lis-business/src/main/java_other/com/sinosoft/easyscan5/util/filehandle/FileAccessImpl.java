package com.sinosoft.easyscan5.util.filehandle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.sinosoft.easyscan5.util.FDate;

public class FileAccessImpl implements IFileAccess {

	private ResultInfo result;
	private String saveType;
	private String basePath;
	private String relativePath;
	private String filenetUrl;
	private final Log logger = LogFactory.getLog(this.getClass());
	Logger log = Logger.getLogger(FileAccessImpl.class);

	public FileAccessImpl() {
		Properties pro = getProperties();
		saveType = pro.getProperty("savetype");
		basePath = pro.getProperty("basepath");
		relativePath = pro.getProperty("relativepath");
		filenetUrl = pro.getProperty("filenet");
		result = new ResultInfo();
		result.setSuccee(true);
		result.setMsg("操作成功");
		result.setItemID("");
		result.setSavePath("");
		result.setInputStream(null);
	}

	public ResultInfo findFile(String filePath, String itemID) {

		if (filePath == null || "".equals(filePath)) {
			// filePath和itemID同时为空时，参数错误
			result.setSuccee(false);
			result.setMsg("参数错误：filePath和itemID不能同时为空");
			logger.error("参数错误：filePath和itemID不能同时为空");
		} else {
			try {
				File file = new File(basePath + relativePath + filePath);
				if (!file.exists()) {
					result.setSuccee(false);
					result.setMsg("找不到文件：" + filePath);
					logger.error("找不到文件：" + filePath);
					return result;
				}
				FileInputStream fin = new FileInputStream(file);
				result.setInputStream(fin);
			} catch (FileNotFoundException e) {
				logger.error("找不到文件：" + e.getMessage());
				e.printStackTrace();
			}
		}

		return result;
	}

	public ResultInfo saveFile(InputStream inputStream, FileInfo fileInfo,
			HashMap propMap) {
		// 获取存储位置
		if (saveType == null || "".equals(saveType)) {
			result.setSuccee(false);
			result.setMsg("文件存储位置没有配置");
			logger.error("文件存储位置没有配置");
			return result;
		} else if ("0".equals(saveType)) {// 存储磁盘
			if (basePath == null || "".equals(basePath)) {
				result.setSuccee(false);
				result.setMsg("文件存储路径没有配置");
				logger.error("文件存储路径没有配置");
				return result;
			}
		}

		// 检查输入参数
		if (!checkInput(inputStream, fileInfo, saveType)) {
			return result;
		}

		// 计算存储位置
		String filePath = fileInfo.getFilePath() + fileInfo.getFileName();
		if (StringUtils.isBlank(filePath)) {
			filePath = this.getFilePath(fileInfo);
		}

		// 保存文件
//		if ("0".equals(saveType)) {
//			saveToFile(inputStream, basePath + relativePath + filePath);
//			result.setItemID("");
//			result.setSavePath(filePath);
//		} else if ("1".equals(saveType)) {
//			// 调用FileNet接口
//			String filename = fileInfo.getFileName();
//			String itemid = this.saveToFileNet(inputStream,
//					fileInfo.getFilePath(), filename, propMap);
//			result.setItemID(itemid);
//		} else if ("2".equals(saveType)) {
//			// 调用CM接口
//			result.setSuccee(false);
//			result.setMsg("系统错误，暂不支持CM");
//			logger.error("系统错误，暂不支持CM");
//		}
		saveToFile(inputStream, basePath + relativePath + filePath);
		result.setItemID("");
		result.setSavePath(filePath);

		return result;
	}

	/*
	 * 保存文件
	 */
	private boolean saveToFile(InputStream in, String filePath) {
		boolean saveFlag = true;
//		String dir = FilenameUtils.getFullPath(filePath);
		File dirFile = new File(filePath);
		if (!dirFile.exists()) {
			boolean mkflag = dirFile.mkdirs();
			if (!mkflag) {
				result.setSuccee(false);
				result.setMsg("文件夹创建失败");
				logger.error("文件夹创建失败");
				saveFlag = false;
				return saveFlag;
			}
		}
		File file = new File(filePath);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			int len = 0;
			byte[] buffer = new byte[30 * 1024];
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			result.setSuccee(false);
			result.setMsg("文件路径错误");
			logger.error("文件路径错误" + e.getMessage());
			saveFlag = false;
			e.printStackTrace();
		} catch (IOException e) {
			result.setSuccee(false);
			result.setMsg("文件写入失败");
			logger.error("文件写入失败" + e.getMessage());
			saveFlag = false;
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				logger.error("文件流关闭失败" + e.getMessage());
			}
		}
		return saveFlag;
	}

	/*
	 * 计算文件存储路径
	 */
	private String getFilePath(FileInfo fileInfo) {
		String path = "";
		Date createDate = fileInfo.getCreateDate();
		path += fileInfo.getCompany();
		path += "/" + FDate.formatDate(createDate, "yyyy/MM/dd");
		return path;
	}

	/*
	 * 检查输入项
	 */
	private boolean checkInput(InputStream inputStream, FileInfo fileInfo,
			String saveType) {
		boolean tmp = true;
		if (inputStream == null) {
			result.setSuccee(false);
			result.setMsg("文件输入流为空");
			logger.error("文件输入流为空");
			tmp = false;
		}
		if ("0".equals(saveType)) {
			if (StringUtils.isNotBlank(fileInfo.getFilePath()
					+ fileInfo.getFileName())) {
				return tmp;
			}
			if (fileInfo.getCompany() == null
					|| "".equals(fileInfo.getCompany())
					|| fileInfo.getCreateDate() == null) {
				result.setSuccee(false);
				result.setMsg("文件信息不完整");
				logger.error("文件信息不完整");
			}
		}

		return tmp;

	}

	/*
	 * 读取配置文件
	 */
	private Properties getProperties() {
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("fileAccess.properties");
		Properties p = new Properties();
		try {
			p.load(inputStream);
		} catch (IOException e1) {
			log.error("读取fileAccess.properties文件失败", e1);
			e1.printStackTrace();
		}
		return p;
	}

	public String getSaveType() {
		return saveType;
	}

	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getRelativePath() {
		return relativePath;
	}

}
