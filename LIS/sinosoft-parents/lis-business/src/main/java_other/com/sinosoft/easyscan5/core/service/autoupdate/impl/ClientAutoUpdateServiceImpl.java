package com.sinosoft.easyscan5.core.service.autoupdate.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sinosoft.easyscan5.util.PubFun;
import com.sinosoft.utility.VData;

public class ClientAutoUpdateServiceImpl {
	private final Log logger = LogFactory.getLog(getClass());
	private String currenVersion;
	private String zipFilePath;

	/**
	 *获取自动更新信息
	 * 
	 * @param Operator
	 * @param manageCom
	 * @param version
	 * @param path
	 * @return
	 */
	public VData getAutoUpdate(String Operator, String manageCom, String version, String path) {
		String ver = "";
		String url = "";
		if (getCurrentVersion(path)) {
			logger.info("autoUpdate--Operator = " + Operator);
			logger.info("autoUpdate--manageCom = " + manageCom);
			logger.info("autoUpdate--version = " + version + ",currentVersion = " + currenVersion);
			if (currenVersion != null && !currenVersion.equals(version)) {
				url = path + "/easyscan5/Update/" + zipFilePath;
				ver = currenVersion;
			} else {
				ver = version;
			}
		}
		VData vData = new VData();
		vData.add(0, ver);
		vData.add(1, "true");
		vData.add(2, url);
		return vData;
	}

	/*
	 * 获取当前最新版本信息
	 */
	private boolean getCurrentVersion(String path) {
		try {
			String updateFilePath = PubFun.getServerBasePath() + "/easyscan5/Update/updateversion.properties";
			InputStream inputStream = new FileInputStream(updateFilePath);
			Properties p = new Properties();
			p.load(inputStream);
			currenVersion = p.getProperty("version");
			zipFilePath = p.getProperty("zipFileName");
		} catch (IOException e1) {
			logger.error("获取版本信息失败", e1);
			return false;
		}
		return true;
	}
}
