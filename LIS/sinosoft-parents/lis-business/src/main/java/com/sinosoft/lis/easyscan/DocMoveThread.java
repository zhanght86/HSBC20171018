/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.04
 * @date     : 2006-12-27
 * @direction: 影像迁移传输和日志记录
 * @Modified By QianLy on 2007-01-10,2007-01-15,2007-01-22,2007-02-07
 ******************************************************************************/

package com.sinosoft.lis.easyscan;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.sinosoft.lis.db.ES_DOC_PAGESDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ES_DOCMOVE_LOGSchema;
import com.sinosoft.lis.schema.ES_DOCMOVE_TASKSchema;
import com.sinosoft.lis.schema.ES_DOC_PAGESSchema;
import com.sinosoft.lis.schema.ES_DOC_PAGES_BSchema;
import com.sinosoft.lis.vschema.ES_DOCMOVE_LOGSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGESSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGES_BSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.FileDeal;
import com.sinosoft.utility.MD5Tool;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class DocMoveThread implements Runnable {
private static Logger logger = Logger.getLogger(DocMoveThread.class);
	public DocMoveThread() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// public DocMoveThread() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 参数数据
	protected GlobalInput rGlobalInput = new GlobalInput();
	protected String sFtpHostName = null;
	protected String sFtpUserName = null;
	protected String sFtpPassword = null;
	protected String sOldManageCom = null;
	protected String sNewManageCom = null;
	protected String sStartDate = null;
	protected String sEndDate = null;
	protected boolean isDeleteOnFinish = false;
	// 私有变量
	protected String sMoveTaskID = null;
	protected String sNewHostName = null;
	protected String sNewServerIP = null;
	protected String sNewBasePath = null;
	// 日期时间
	protected String sCurrentDate = PubFun.getCurrentDate();
	protected String sCurrentTime = PubFun.getCurrentTime();
	// 允许传输文件格式
	protected String[] fileFilter = { "GIF", "TIF", "JPG", "JPEG", "PNG", "BMP" };
	protected MD5Tool mt = new MD5Tool();
	protected Hashtable tHashtable = new Hashtable();
	protected Vector vct = new Vector();

	// ==========================================================================

	/**
	 * 设置操作员机构信息
	 * 
	 * @param GlobalInput
	 */
	public void setGlobalInput(GlobalInput tGlobalInput) {
		this.rGlobalInput = tGlobalInput;
	} // function setGlobalInput end

	// ==========================================================================

	/**
	 * 获取操作员机构信息
	 * 
	 * @retutn GlobalInput
	 */
	public GlobalInput getGlobalInput() {
		return this.rGlobalInput;
	} // function getGlobalInput end

	// ==========================================================================

	/**
	 * 设置原 FTP 服务器地址
	 * 
	 * @param String
	 */
	public void setFtpHostName(String sHostName) {
		this.sFtpHostName = sHostName;
	} // function setFtpHostName end

	// ==========================================================================

	/**
	 * 获取原 FTP 服务器地址
	 * 
	 * @retutn String
	 */
	public String getFtpHostName() {
		return this.sFtpHostName;
	} // function getFtpHostName end

	// ==========================================================================

	/**
	 * 设置 FTP 登录用户名
	 * 
	 * @param String
	 */
	public void setFtpUserName(String sUserName) {
		this.sFtpUserName = sUserName;
	} // function setFtpUserName end

	// ==========================================================================

	/**
	 * 获取 FTP 登录用户名
	 * 
	 * @retutn String
	 */
	public String getFtpUserName() {
		return this.sFtpUserName;
	} // function getFtpUserName end

	// ==========================================================================

	/**
	 * 设置 FTP 登录密码
	 * 
	 * @param String
	 */
	public void setFtpPassword(String sPassword) {
		this.sFtpPassword = sPassword;
	} // function setFtpPassword end

	// ==========================================================================

	/**
	 * 获取 FTP 登录密码
	 * 
	 * @retutn String
	 */
	public String getFtpPassword() {
		return this.sFtpPassword;
	} // function getFtpPassword end

	// ==========================================================================

	/**
	 * 设置迁出机构
	 * 
	 * @param String
	 */
	public void setOldManageCom(String sManageCom) {
		this.sOldManageCom = sManageCom;
	} // function setOldManageCom end

	// ==========================================================================

	/**
	 * 获取迁出机构
	 * 
	 * @retutn String
	 */
	public String getOldManageCom() {
		return this.sOldManageCom;
	} // function getOldManageCom end

	// ==========================================================================

	/**
	 * 设置迁入机构
	 * 
	 * @param String
	 */
	public void setNewManageCom(String sManageCom) {
		this.sNewManageCom = sManageCom;
	} // function setNewManageCom end

	// ==========================================================================

	/**
	 * 获取迁入机构
	 * 
	 * @retutn String
	 */
	public String getNewManageCom() {
		return this.sNewManageCom;
	} // function getNewManageCom end

	// ==========================================================================

	/**
	 * 设置迁移起始时间
	 * 
	 * @param String
	 */
	public void setStartDate(String sDate) {
		this.sStartDate = sDate;
	} // function setStartDate end

	// ==========================================================================

	/**
	 * 获取迁移起始时间
	 * 
	 * @retutn String
	 */
	public String getStartDate() {
		return this.sStartDate;
	} // function getStartDate end

	// ==========================================================================

	/**
	 * 设置迁移结束时间
	 * 
	 * @param String
	 */
	public void setEndDate(String sDate) {
		this.sEndDate = sDate;
	} // function setEndDate end

	// ==========================================================================

	/**
	 * 获取迁移结束时间
	 * 
	 * @retutn String
	 */
	public String getEndDate() {
		return this.sEndDate;
	} // function getEndDate end

	// ==========================================================================

	/**
	 * 设置传输完成之后是否删除远程文件
	 * 
	 * @param boolean
	 */
	public void setDeleteOnFinish(boolean isDelete) {
		this.isDeleteOnFinish = isDelete;
	} // function setDeleteOnFinish end

	// ==========================================================================

	/**
	 * 获取传输完成之后是否删除远程文件
	 * 
	 * @retutn boolean
	 */
	public boolean getDeleteOnFinish() {
		return this.isDeleteOnFinish;
	} // function getDeleteOnFinish end

	/**
	 * 设置迁移工作流水号
	 * 
	 * @param GlobalInput
	 */
	public void setMoveTaskID(String moveid) {
		this.sMoveTaskID = moveid;
	} // function setGlobalInput end

	// ==========================================================================

	/**
	 * 获取迁移工作流水号
	 * 
	 * @retutn GlobalInput
	 */
	public String getMoveTaskID() {
		return this.sMoveTaskID;
	} // function getGlobalInput end

	// ==========================================================================

	// 对本地文件计算MD5,读取内存中Hashtable中其对应MD5,进行比较
	// 参数 filename 是文件名,如 d:/a/b/c/d.gif
	protected boolean compareByMD5(String filename) {
		filename = filename.replaceAll("\\\\", "/");
		if (tHashtable == null || tHashtable.size() < 1) {
			String fpath = filename.substring(0, filename.lastIndexOf("/"));
			logger.debug("\t DocMoveThread.java -> error: 目录 " + fpath
					+ " 下的MD5对应的哈希表丢失!");
			return false;
		}
		File f = new File(filename);
		if (!f.exists()) {
			logger.debug("\t DocMoveThread.java -> error: 文件 " + filename
					+ " 不存在");
			return false;
		}
		String imageName = filename.substring(filename.lastIndexOf("/") + 1);
		String getmd5 = (String) tHashtable.get(imageName);
		String newMd5 = mt.getFileMD(filename);
		if (getmd5.toUpperCase().equals(newMd5.toUpperCase())) {
			return true;
		}
		return false;
	}

	// 得到保存图片MD5的文件及它的校验文件,进行校验确保得到的图片MD5文件准确
	protected boolean getAndCheckMD5File(FTPClient tFTPClient, String filepath) {
		String im = "IMAGE.MD5";
		String mm = "MD5.MD5";
		String fullim = filepath + im;
		String fullmm = filepath + mm;
		try {
			FileOutputStream tFileOutputStream = new FileOutputStream(fullim);
			tFTPClient.retrieveFile(im, tFileOutputStream);
			tFileOutputStream.close();
			FileOutputStream ttFileOutputStream = new FileOutputStream(fullmm);
			tFTPClient.retrieveFile(mm, ttFileOutputStream);
			ttFileOutputStream.close();

			File f = new File(fullim);
			if (!f.exists()) {
				logger.debug("\t DocMoveThread.java -> error: 图片文件的MD5文件 "
								+ fullim + " 传送错误!");
				return false;
			}
			File ff = new File(fullmm);
			if (!ff.exists()) {
				logger.debug("\t DocMoveThread.java -> error: MD5文件的MD5文件 "
								+ fullmm + " 传送错误!");
				return false;
			}
			// 这个地方读出来的最后有一个\n,比较时候要注意
			String getmd5 = FileDeal.readText(fullmm);
			getmd5 = getmd5.substring(0, getmd5.lastIndexOf("\n"));
			String newMd5 = mt.getFileMD(fullim);
			if (getmd5.toUpperCase().equals(newMd5.toUpperCase())) {
				String imageMD5 = FileDeal.readText(fullim);
				if (!fillHashTable(tHashtable, imageMD5)) {
					logger.debug("\t DocMoveThread.java -> error: 解析MD5文件 "
									+ fullim + " 有误!");
					return false;
				}
				return true;
			} else {
				logger.debug("\t DocMoveThread.java -> error: IMAGE.MD5 "
						+ fullim + " 与其MD5文件不一致!");
				return false;
			}
		} catch (Exception ex) {
			logger.debug("\t DocMoveThread.java -> error: moveScanedImages() : 文件 "
							+ fullim + " 传送失败！");
			logger.debug("\t DocMoveThread.java -> error: 引发错误的目录是："
					+ filepath);
			// logTransferError(filepath, "");
		}
		return false;
	}

	// 将一个记录MD5的字符串解析到Hash表中
	protected boolean fillHashTable(Hashtable ht, String md5str) {
		ht.clear();
		try {
			String[] s = md5str.split("\n");
			for (int i = 0; i < s.length; i++) {
				String[] ss = s[i].split(":");
				String n = ss[0];
				String m = ss[1];
				Object v = ht.get(n);
				if (v == null) {
					ht.put(n, m);
				}
			}
		} catch (Exception e) {
			logger.debug("\t DocMoveThread.java -> error: 解析MD5到Hash表异常！");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// 将图片文件名添加到向量vct中,存在就不重复添加了
	// 参数 filename 形如：F1234.gif
	protected boolean addToVector(String filename) {
		filename = filename.substring(0, filename.lastIndexOf("."));
		if (!vct.contains(filename)) {
			vct.addElement(filename);
		}
		return true;
	}

	// 按图片文件名在向量vct中查找是否已经存在
	// 参数 filename 姓如 F1234.gif
	protected boolean findInVector(String filename) {
		filename = filename.substring(0, filename.lastIndexOf("."));
		return vct.contains(filename);
	}

	/**
	 * 获取指定机构的名称
	 * 
	 * @param String
	 * @retutn String
	 */
	protected String getManageComHostName(String sManageCom) {
		String sHostName = null;
		if (sManageCom != null && !sManageCom.trim().equals("")) {
			String QuerySQL = "select HostName " + "from ES_COM_SERVER "
					+ "where ManageCom = '" + "?sManageCom?" + "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(QuerySQL);
			sqlbv1.put("sManageCom", sManageCom);
			ExeSQL tExeSQL = new ExeSQL();
			try {
				sHostName = tExeSQL.getOneValue(sqlbv1);
			} catch (Exception ex) {
			}
			tExeSQL = null;
		}
		return sHostName;
	} // function getManageComHostName end

	// ==========================================================================

	/**
	 * 获取指定机构的 IP 地址
	 * 
	 * @param String
	 * @retutn String
	 */
	protected String getManageComServerIP(String sManageCom) {
		String sServerIP = null;
		if (sManageCom != null && !sManageCom.trim().equals("")) {
			String QuerySQL = "select ServerPort " + "from ES_SERVER_INFO "
					+ "where HostName = "
					+ "(select HostName from ES_COM_SERVER where ManageCom = '"
					+ "?sManageCom?" + "')";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(QuerySQL);
			sqlbv2.put("sManageCom", sManageCom);
			ExeSQL tExeSQL = new ExeSQL();
			try {
				sServerIP = tExeSQL.getOneValue(sqlbv2);
			} catch (Exception ex) {
			}
			tExeSQL = null;
			if (sServerIP != null && !sServerIP.trim().equals("")) {
				if (sServerIP.indexOf(":") != -1) {
					sServerIP = sServerIP.substring(0, sServerIP.indexOf(":"));
				}
			}
		}

		return sServerIP;
	} // function getManageComServerIP end

	// ==========================================================================

	/**
	 * 获取指定机构图片保存路径
	 * 
	 * @param String
	 * @retutn String
	 */
	protected String getManageComBasePath(String sManageCom) {
		String sBasePath = null;
		if (sManageCom != null && !sManageCom.trim().equals("")) {
			String QuerySQL = "select ServerBasePath " + "from ES_SERVER_INFO "
					+ "where HostName = "
					+ "(select HostName from ES_COM_SERVER where ManageCom = '"
					+ "?sManageCom?" + "')";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(QuerySQL);
			sqlbv3.put("sManageCom", sManageCom);
			ExeSQL tExeSQL = new ExeSQL();
			try {
				sBasePath = tExeSQL.getOneValue(sqlbv3);
			} catch (Exception ex) {
			}
			tExeSQL = null;
			if (sBasePath != null && !sBasePath.endsWith("/")) {
				sBasePath += "/";
			}
		}

		return sBasePath;
	} // function getManageComBasePath end

	// ==========================================================================

	/**
	 * 本类的核心业务处理过程
	 */
	public void run() {
		logger.debug("\t DocMoveThread.java -> DocMoveThread.run() 迁移线程开始");

		if (!chkRunParameters())
			return;
		if (!moveScanedImages())
			return;

		logger.debug("\t DocMoveThread.java -> DocMoveThread.run() 迁移线程结束");
	} // function run end

	// ==========================================================================

	/**
	 * 检查本类运行所必需的参数
	 * 
	 * @return boolean
	 */
	private boolean chkRunParameters() {
		if (rGlobalInput == null) {
			rGlobalInput = new GlobalInput();
			rGlobalInput.Operator = "001";
			rGlobalInput.ManageCom = "86";
		}

		if (sOldManageCom == null || sOldManageCom.trim().equals("")) {
			CError.buildErr(this, "需要迁移的迁出管理机构不能为空！");
			logger.debug("\t DocMoveThread.java -> error: 需要迁移的迁出管理机构不能为空");
			return false;
		}

		if (sNewManageCom == null || sNewManageCom.trim().equals("")) {
			CError.buildErr(this, "需要迁移的迁入管理机构不能为空！");
			logger.debug("\t DocMoveThread.java -> error: 需要迁移的迁入管理机构不能为空");
			return false;
		}

		sNewHostName = getManageComHostName(sNewManageCom);
		if (sNewHostName == null || sNewHostName.trim().equals("")) {
			CError.buildErr(this, "迁入管理机构的机构名称不能为空！");
			logger.debug("\t DocMoveThread.java -> error: 迁入管理机构的机构名称不能为空");
			return false;
		}

		sNewServerIP = getManageComServerIP(sNewManageCom);
		if (sNewServerIP == null || sNewServerIP.trim().equals("")) {
			CError.buildErr(this, "迁入管理机构的 IP 地址不能为空！");
			logger.debug("\t DocMoveThread.java -> error: 迁入管理机构的 IP 地址不能为空");
			return false;
		}

		sNewBasePath = getManageComBasePath(sNewManageCom);
		if (sNewBasePath == null || sNewBasePath.trim().equals("")) {
			CError.buildErr(this, "迁入管理机构的图片路径不能为空！");
			logger.debug("\t DocMoveThread.java -> error: 迁入管理机构的图片路径不能为空");
			return false;
		}

		sMoveTaskID = sCurrentDate.replaceAll("-", "") + "_"
				+ PubFun1.CreateMaxNo("MOVEID", 1);
		if (sMoveTaskID == null || sMoveTaskID.trim().equals("")) {
			CError.buildErr(this, "计算迁移任务批次号失败！");
			logger.debug("\t DocMoveThread.java -> error: 计算迁移任务批次号失败");
			return false;
		}

		return true;
	} // function chkRunParameters end

	// ==========================================================================

	/**
	 * 迁移已经扫描的影像资料
	 * 
	 * @return boolean
	 */
	private boolean moveScanedImages() {
		// 查询迁移总数
		String QuerySQL = new String("");
		QuerySQL = "select count(*) " + "from ES_DOC_PAGES a " + "where 1 = 1 "
				+ "and exists "
				+ "(select 'X' from ES_DOC_MAIN where DocID = a.DocID)";
		if (sStartDate != null && !sStartDate.trim().equals("")) {
			QuerySQL += " and a.MakeDate >= '" + "?sStartDate?" + "'";
		}
		if (sEndDate != null && !sEndDate.trim().equals("")) {
			QuerySQL += " and a.MakeDate <= '" + "?sEndDate?" + "'";
		}
		QuerySQL += " and a.ManageCom like concat(?sOldManageCom?,'%')";
		// logger.debug(QuerySQL);

		// ----------------------------------------------------------------------

		// 计算迁移总数
		int nTotalTaskImages = 0;
		// 统计成功数目
		int nTotalSuccImages = 0;
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(QuerySQL);
		sqlbv4.put("sStartDate", sStartDate);
		sqlbv4.put("sEndDate", sEndDate);
		sqlbv4.put("sOldManageCom", sOldManageCom);
		ExeSQL tExeSQL = new ExeSQL();
		try {
			String sTotalTaskImages = tExeSQL.getOneValue(sqlbv4);
			if (sTotalTaskImages != null && !sTotalTaskImages.trim().equals("")) {
				nTotalTaskImages = Integer.parseInt(sTotalTaskImages);
				// 实际影像文件可能不仅存在 .tif 和 .gif 两种格式, 还可能有其他格式
				// 因为对应关系不是确定，所以翻倍显得没有必要，也不准确
				// Deleted By QianLy on 2007-02-07
				// nTotalTaskImages = 2 * nTotalTaskImages;
			}
			logger.debug("\t DocMoveThread.java -> moveScanedImages() : 需要迁移的影像总数是 "
							+ nTotalTaskImages);
		} catch (Exception ex) {
			CError.buildErr(this, "查询计算需要迁移的影像总数出现异常！");
			logger.debug("\t DocMoveThread.java -> error: 查询计算需要迁移的影像总数出现异常");
			return false;
		}
		if (nTotalTaskImages <= 0) {
			CError.buildErr(this, "机构 " + sOldManageCom + " 下没有需要迁移的影像！");
			logger.debug("\t DocMoveThread.java -> error: 机构 "
					+ sOldManageCom + " 下没有需要迁移的影像");
			return false;
		}

		// ----------------------------------------------------------------------

		// 查询存放路径
		QuerySQL = "select distinct(PicPath) " + "from ES_DOC_PAGES a "
				+ "where 1 = 1 " + "and exists "
				+ "(select 'X' from ES_DOC_MAIN where DocID = a.DocID)";
		if (sStartDate != null && !sStartDate.trim().equals("")) {
			QuerySQL += " and a.MakeDate >= '" + "?sStartDate?" + "'";
		}
		if (sEndDate != null && !sEndDate.trim().equals("")) {
			QuerySQL += " and a.MakeDate <= '" + "?sEndDate?" + "'";
		}
		QuerySQL += " and a.ManageCom like concat(?sOldManageCom?,'%')";

		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(QuerySQL);
		sqlbv5.put("sStartDate", sStartDate);
		sqlbv5.put("sEndDate", sEndDate);
		sqlbv5.put("sOldManageCom", sOldManageCom);
		SSRS tSSRS = new SSRS();
		try {
			tSSRS = tExeSQL.execSQL(sqlbv5);
		} catch (Exception ex) {
			CError.buildErr(this, "查询远程服务器影像存放路径出现异常！");
			logger.debug("\t DocMoveThread.java -> error: 查询远程服务器影像存放路径出现异常");
			return false;
		}

		// ----------------------------------------------------------------------
		// 建立 FTP 连接(将这一步放在查询后面是防止因为查询时间过长而导致FTP超时（确实发生过）)
		FTPClient tFTPClient = new FTPClient();
		try {
			logger.debug("\t@> DocMoveThread.moveScanedImages() : Connecting to FTP server "
							+ sFtpHostName + " ...");
			tFTPClient.connect(sFtpHostName);
			logger.debug("\t@> DocMoveThread.moveScanedImages() : "
					+ tFTPClient.getReplyString());
			if (!FTPReply.isPositiveCompletion(tFTPClient.getReplyCode())) {
				tFTPClient.disconnect();
				CError.buildErr(this, "尝试连接 FTP 服务器 " + sFtpHostName + " 被拒绝。");
				logger.debug("\t DocMoveThread.java -> error: 尝试连接 FTP 服务器 "
								+ sFtpHostName + " 被拒绝。");
				return false;
			}
			if (!tFTPClient.login(sFtpUserName, sFtpPassword)) {
				CError.buildErr(this, "尝试登录 FTP 服务器 " + sFtpHostName + " 被拒绝。");
				logger.debug("\t DocMoveThread.java -> error: 尝试登录 FTP 服务器 "
								+ sFtpHostName + " 被拒绝。");
				return false;
			}
			tFTPClient.setFileType(FTP.BINARY_FILE_TYPE); // 设置文件传输格式
			logger.debug("\t@> DocMoveThread.moveScanedImages() : "
					+ tFTPClient.getReplyString());
			tFTPClient.enterLocalPassiveMode(); // 设置为被动模式
			logger.debug("\t@> DocMoveThread.moveScanedImages() : "
					+ tFTPClient.getReplyString());
		} catch (Exception ex) {
			CError.buildErr(this, "尝试登录 FTP 服务器 " + sFtpHostName
					+ " 失败。可能的原因是：" + ex.toString());
			logger.debug("\t DocMoveThread.java -> error: 尝试登录 FTP 服务器 "
					+ sFtpHostName + " 失败。可能的原因是：" + ex.toString());
			ex.printStackTrace();
			return false;
		}
		// ----------------------------------------------------------------------

		// 建立任务开始信息
		if (!logTransferTask(nTotalTaskImages, nTotalSuccImages, false)) {
			CError.buildErr(this, "建立任务开始的临时记录出现异常！");
			logger.debug("\t DocMoveThread.java -> error: 建立任务开始的临时记录出现异常");
			return false;
		}

		// 循环每个目录进行处理
		if (tSSRS != null && tSSRS.getMaxRow() > 0) {
			for (int i = 0; i < tSSRS.getMaxRow(); i++) {
				String sRemotePicPath = tSSRS.GetText(i + 1, 1);

				// 进入指定目录
				try {
					if (!tFTPClient.changeWorkingDirectory("/")
							|| !tFTPClient
									.changeWorkingDirectory(sRemotePicPath)) {
						logger.debug("\t DocMoveThread.java -> error: moveScanedImages() : 进入远程 FTP 目录失败");
						logger.debug("\t DocMoveThread.java -> error: 引发错误的目录是："
										+ sRemotePicPath);
						logTransferError(sRemotePicPath, null);
						continue;
					}
				} catch (Exception ex) {
					CError.buildErr(this, "进入远程 FTP 目录失败，可能的原因是 FTP 连接超时！");
					logger.debug("\t DocMoveThread.java -> error: 进入远程 FTP 目录失败，可能的原因是 FTP 连接超时！");
					return false;
				}

				// 检查本地目录
				String sLocalPicPath = sNewBasePath + sRemotePicPath;
				if (sLocalPicPath != null && !sLocalPicPath.endsWith("/")) {
					sLocalPicPath += "/";
				}
				logger.debug("本地路径是 " + sLocalPicPath);

				File tLocalFile = new File(sLocalPicPath);
				if (!tLocalFile.exists() || !tLocalFile.isDirectory()) {
					try {
						tLocalFile.mkdirs();
					} catch (Exception ex) {
						logger.debug("\t DocMoveThread.java -> error: moveScanedImages() : 创建本地 FTP 目录失败");
						logger.debug("\t DocMoveThread.java -> error: 引发错误的目录是："
										+ sRemotePicPath);
						logTransferError(sRemotePicPath, null);
						continue;
					}
				}

				// 首先得到该目录下的MD5文件并进行校验,
				// 然后拆分到Hashtable中作为图片校验依据
				if (!getAndCheckMD5File(tFTPClient, sLocalPicPath)) {
					logger.debug("\t DocMoveThread.java -> error: moveScanedImages() : 从本目录下取得并校验MD5文件失败!");
					logger.debug("\t DocMoveThread.java -> error: 引发错误的目录是："
									+ sRemotePicPath);
					logTransferError(sRemotePicPath, null);
					continue;
				}

				// 进入一个目录后清掉向量
				// 使之不至于造成数据积累而可能导致效率下降或系统资源耗尽内存溢出（吓唬人的？）
				vct.clear();

				// 遍历目录传输
				String sImageFileName = new String("");
				String sImageFullPath = new String("");
				FTPFile[] arrFTPFiles;
				try {
					arrFTPFiles = tFTPClient.listFiles();
				} catch (Exception ex) {
					logger.debug("\t DocMoveThread.java -> error: moveScanedImages() : 列出远程 FTP 目录文件列表失败");
					logger.debug("\t DocMoveThread.java -> error: 引发错误的目录是："
									+ sRemotePicPath);
					logTransferError(sRemotePicPath, null);
					continue;
				}
				for (int k = 0; k < arrFTPFiles.length; k++) {
					sImageFileName = arrFTPFiles[k].getName();

					// 屏蔽垃圾文件,和文件列表的前面2个: "."和"..",只传允许格式
					if (!BqNameFun.fileFilterByName(fileFilter, sImageFileName)) {
						logger.debug("\t DocMoveThread.java -> moveScanedImages() : 文件 "
										+ sImageFileName + " 不是允许的格式,跳过！");
						continue;
					}
					sImageFullPath = sLocalPicPath + sImageFileName;
					logger.debug("当前文件名是 " + sImageFileName);
					tLocalFile = new File(sImageFullPath);
					// 本地不存在该文件才传输
					if (tLocalFile.exists() && tLocalFile.isFile()) {
						// 如果文件存在则进行MD5校验
						if (compareByMD5(sImageFullPath)) {
							logger.debug("\t DocMoveThread.java -> moveScanedImages() : 文件 "
											+ sImageFileName
											+ " 已存在并且通过MD5校验，跳过！");
							continue;
						}
					} else {
						try {
							FileOutputStream tFileOutputStream = new FileOutputStream(
									sImageFullPath);
							tFTPClient.retrieveFile(sImageFileName,
									tFileOutputStream);
							tFileOutputStream.close();
							tFileOutputStream = null;
							Date tDate = arrFTPFiles[k].getTimestamp()
									.getTime();
							tLocalFile.setLastModified(tDate.getTime());
							tDate = null;

							if (!compareByMD5(sImageFullPath)) {
								logTransferError(sRemotePicPath, sImageFileName);
								continue;
							}

							// 在向量中找不到说明是个未更新的，更新并加入向量
							// 找到了则说明是已经更新过了，不用再处理
							if (!findInVector(sImageFileName)) {
								updateDocPageInfo(sRemotePicPath,
										sImageFileName);
								addToVector(sImageFileName);
							}

							nTotalSuccImages += 1;
							// 传输成功删除源文件; 还需要校验文件是否完整, 暂不放开
							// if (isDeleteOnFinish)
							// {
							// try
							// {
							// tFTPClient.deleteFile(sRemotePicPath +
							// sImageFileName);
							// }
							// catch (Exception ex)
							// {
							// logger.debug("\t@>
							// DocMoveThread.moveScanedImages() : 文件 " +
							// sImageFileName + " 传送成功, 但删除失败！");
							// }
							// }
						} catch (Exception ex) {
							logger.debug("\t DocMoveThread.java -> error: moveScanedImages() : 文件 "
											+ sImageFileName + " 传送失败！");
							logger.debug("\t DocMoveThread.java -> error: 引发错误的目录是："
											+ sRemotePicPath);
							logTransferError(sRemotePicPath, sImageFileName);
							continue;
						}
					}
				} // end for arrFTPFiles
			} // end for tSSRS
		}
		tExeSQL = null;
		tSSRS = null;

		// ----------------------------------------------------------------------

		// 记录任务信息
		if (!logTransferTask(nTotalTaskImages, nTotalSuccImages, true)) {
			CError.buildErr(this, "建立任务完成的记录出现异常！");
			logger.debug("\t DocMoveThread.java -> error: 迁移任务 "
					+ sMoveTaskID + " 建立任务完成的记录出现异常！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 断开 FTP 连接
		if (tFTPClient != null && tFTPClient.isConnected()) {
			try {
				tFTPClient.logout();
				tFTPClient.disconnect();
			} catch (Exception ex) {
			}
		}
		logger.debug("\t@> DocMoveThread.moveScanedImages() : Disconnected from FTP server "
						+ sFtpHostName + " .");
		logger.debug("\t\t\t DocMoveThread.java -> 迁移任务 " + sMoveTaskID
				+ " 全部完成！");
		tFTPClient = null;

		return true;
	} // function moveScanedImages end

	// ==========================================================================

	/**
	 * 更新传输成功的影像信息并进行备份
	 * 
	 * @param String
	 * @param String
	 * @return boolean
	 */
	private boolean updateDocPageInfo(String sRemotePath, String sFileName) {
		if (sFileName == null || sFileName.trim().equals("")) {
			CError.buildErr(this, "已传输成功需更新影像信息的文件名不能为空！");
			logger.debug("\t DocMoveThread.java -> error: 已传输成功需更新影像信息的文件名不能为空");
			return false;
		} else {
			// 数据库里面 PageName 只记录了文件名, 没有 .tif 后缀
			sFileName = sFileName.substring(0, sFileName.lastIndexOf("."));
			// logger.debug("sFileName = "+sFileName);
		}

		// ----------------------------------------------------------------------

		MMap tMMap = new MMap();

		// ----------------------------------------------------------------------

		String QuerySQL = new String("");
		QuerySQL = "select * " + "from ES_DOC_PAGES a " + "where 1 = 1 "
				+ "and exists "
				+ "(select 'X' from ES_DOC_MAIN where DocID = a.DocID)";
		if (sStartDate != null && !sStartDate.trim().equals("")) {
			QuerySQL += " and a.MakeDate >= '" + "?sStartDate?" + "'";
		}
		if (sEndDate != null && !sEndDate.trim().equals("")) {
			QuerySQL += " and a.MakeDate <= '" + "?sEndDate?" + "'";
		}
		if (sRemotePath != null && !sRemotePath.trim().equals("")) {
			QuerySQL += " and a.PicPath like concat(?sRemotePath?,'%')";
		}
		QuerySQL += " and a.ManageCom like concat(?sOldManageCom?,'%') ";
		if (sFileName != null && !sFileName.trim().equals("")) {
			QuerySQL += " and a.PageName = '" + "?sFileName?" + "'";
		}

		// ----------------------------------------------------------------------
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(QuerySQL);
		sqlbv6.put("sStartDate", sStartDate);
		sqlbv6.put("sEndDate", sEndDate);
		sqlbv6.put("sRemotePath", sRemotePath);
		sqlbv6.put("sOldManageCom", sOldManageCom);
		sqlbv6.put("sFileName", sFileName);
		// 查询 ES_DOC_PAGES
		ES_DOC_PAGESDB tES_DOC_PAGESDB = new ES_DOC_PAGESDB();
		ES_DOC_PAGESSet tES_DOC_PAGESSet = new ES_DOC_PAGESSet();
		try {
			tES_DOC_PAGESSet = tES_DOC_PAGESDB.executeQuery(sqlbv6);
		} catch (Exception ex) {
			CError.buildErr(this, "查询以更新已成功迁移的影像信息发生异常！");
			logger.debug("\t DocMoveThread.java -> error: 查询以更新已成功迁移的影像信息发生异常");
			return false;
		}
		if (tES_DOC_PAGESSet != null && tES_DOC_PAGESSet.size() > 0) {
			ES_DOC_PAGES_BSet tES_DOC_PAGES_BSetNew = new ES_DOC_PAGES_BSet();
			ES_DOC_PAGESSet tES_DOC_PAGESSetNew = new ES_DOC_PAGESSet();
			for (int i = 1; i <= tES_DOC_PAGESSet.size(); i++) {
				ES_DOC_PAGESSchema tES_DOC_PAGESSchema = new ES_DOC_PAGESSchema();
				tES_DOC_PAGESSchema = tES_DOC_PAGESSet.get(i);

				// 拷贝 ES_DOC_PAGES 到 ES_DOC_PAGES_B
				ES_DOC_PAGES_BSchema tES_DOC_PAGES_BSchemaNew = new ES_DOC_PAGES_BSchema();
				PubFun
						.copySchema(tES_DOC_PAGES_BSchemaNew,
								tES_DOC_PAGESSchema);
				tES_DOC_PAGES_BSchemaNew.setMoveID(sMoveTaskID);
				tES_DOC_PAGES_BSchemaNew.setMoveType("0");
				tES_DOC_PAGES_BSchemaNew.setOperator(rGlobalInput.Operator);
				tES_DOC_PAGES_BSchemaNew.setModifyDate(sCurrentDate);
				tES_DOC_PAGES_BSchemaNew.setModifyTime(sCurrentTime);
				tES_DOC_PAGES_BSetNew.add(tES_DOC_PAGES_BSchemaNew);
				tES_DOC_PAGES_BSchemaNew = null;

				// 更新 ES_DOC_PAGES
				ES_DOC_PAGESSchema tES_DOC_PAGESSchemaNew = new ES_DOC_PAGESSchema();
				PubFun.copySchema(tES_DOC_PAGESSchemaNew, tES_DOC_PAGESSchema);
				String sPicPathFTP = new String("");
				sPicPathFTP = tES_DOC_PAGESSchemaNew.getPicPathFTP();
				if (sPicPathFTP != null && !sPicPathFTP.trim().equals("")) {
					if (sPicPathFTP.indexOf(":") != -1) {
						sPicPathFTP = sNewServerIP
								+ ":"
								+ sPicPathFTP.substring(sPicPathFTP
										.indexOf(":") + 1);
						tES_DOC_PAGESSchemaNew.setPicPathFTP(sPicPathFTP);
					}
				}
				tES_DOC_PAGESSchemaNew.setHostName(sNewHostName);
				tES_DOC_PAGESSchemaNew.setOperator(rGlobalInput.Operator);
				tES_DOC_PAGESSchemaNew.setModifyDate(sCurrentDate);
				tES_DOC_PAGESSchemaNew.setModifyTime(sCurrentTime);
				tES_DOC_PAGESSetNew.add(tES_DOC_PAGESSchemaNew);
				tES_DOC_PAGESSchemaNew = null;
				tES_DOC_PAGESSchema = null;
			}
			tMMap.put(tES_DOC_PAGES_BSetNew, "DELETE&INSERT");
			tMMap.put(tES_DOC_PAGESSetNew, "UPDATE");
			tES_DOC_PAGES_BSetNew = null;
			tES_DOC_PAGESSetNew = null;
		}

		if (!pubSubmit(tMMap)) {
			CError.buildErr(this, "备份并更新迁移批次信息表失败！");
			logger.debug("\t DocMoveThread.java -> error: updateDocPageInfo() : 插入 ES_DOC_PAGES_B 或更新 ES_DOC_PAGES 失败！");
			logger.debug("\t DocMoveThread.java -> error: 引发错误的文件是："
					+ sFileName);
			return false;
		}
		tMMap = null;

		return true;
	} // function updateDocPageInfo end

	// ==========================================================================

	/**
	 * 记录传输失败的影像信息
	 * 
	 * @param String
	 * @param String
	 * @return boolean
	 */
	private boolean logTransferError(String sRemotePath, String sFileName) {
		if (sFileName != null && !sFileName.trim().equals("")) {
			// 数据库里面 PageName 只记录了文件名, 没有 .tif 后缀
			sFileName = sFileName.substring(0, sFileName.lastIndexOf("."));
		}

		MMap tMMap = new MMap();

		String QuerySQL = new String("");
		QuerySQL = "select distinct docid,picpath,makedate"
				+ " from ES_DOC_PAGES a" + " where 1 = 1 " + " and exists "
				+ "     (select 'X' from ES_DOC_MAIN where DocID = a.DocID)";
		if (sStartDate != null && !sStartDate.trim().equals("")) {
			QuerySQL += " and a.MakeDate >= '" + "?sStartDate?" + "'";
		}
		if (sEndDate != null && !sEndDate.trim().equals("")) {
			QuerySQL += " and a.MakeDate <= '" + "?sEndDate?" + "'";
		}
		if (sRemotePath != null && !sRemotePath.trim().equals("")) {
			QuerySQL += " and a.PicPath like concat(?sRemotePath?,'%')";
		}
		QuerySQL += " and a.ManageCom like concat(?sOldManageCom?,'%') ";
		if (sFileName != null && !sFileName.trim().equals("")) {
			QuerySQL += " and a.PageName = '" + "?sFileName?" + "'";
		}
		// logger.debug(QuerySQL);

		// ----------------------------------------------------------------------
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(QuerySQL);
		sqlbv7.put("sStartDate", sStartDate);
		sqlbv7.put("sEndDate", sEndDate);
		sqlbv7.put("sRemotePath", sRemotePath);
		sqlbv7.put("sOldManageCom", sOldManageCom);
		sqlbv7.put("sFileName", sFileName);
		// 查询 ES_DOC_PAGES
		ExeSQL tes = new ExeSQL();
		SSRS ss = new SSRS();
		try {
			ss = tes.execSQL(sqlbv7);
		} catch (Exception ex) {
			CError.buildErr(this, "查询传输失败的影像信息发生异常！");
			logger.debug("\t DocMoveThread.java -> error: 查询传输失败的影像信息发生异常");
			return false;
		}
		if (ss == null || ss.getMaxRow() < 1) {
			CError.buildErr(this, "查询传输失败的影像信息发生异常！");
			logger.debug("\t DocMoveThread.java -> error: 查询传输失败的影像信息发生异常");
			return false;
		} else {
			ES_DOCMOVE_LOGSet tES_DOCMOVE_LOGSetNew = new ES_DOCMOVE_LOGSet();
			for (int i = 1; i <= ss.getMaxRow(); i++) {
				ES_DOCMOVE_LOGSchema tES_DOCMOVE_LOGSchemaNew = new ES_DOCMOVE_LOGSchema();
				tES_DOCMOVE_LOGSchemaNew.setMoveID(sMoveTaskID);
				tES_DOCMOVE_LOGSchemaNew.setDocID(ss.GetText(i, 1));
				tES_DOCMOVE_LOGSchemaNew.setFilePath(ss.GetText(i, 2));
				tES_DOCMOVE_LOGSchemaNew.setFileDate(ss.GetText(i, 3));
				tES_DOCMOVE_LOGSchemaNew.setFlag("1");
				tES_DOCMOVE_LOGSchemaNew.setLastDate(sCurrentDate);
				tES_DOCMOVE_LOGSchemaNew.setLastTime(sCurrentTime);
				tES_DOCMOVE_LOGSchemaNew.setManageCom(sOldManageCom);
				tES_DOCMOVE_LOGSchemaNew.setToManageCom(sNewManageCom);
				tES_DOCMOVE_LOGSetNew.add(tES_DOCMOVE_LOGSchemaNew);
				tES_DOCMOVE_LOGSchemaNew = null;
			}
			tMMap.put(tES_DOCMOVE_LOGSetNew, "DELETE&INSERT");
			tES_DOCMOVE_LOGSetNew = null;
		}
		tes = null;
		ss = null;

		if (!pubSubmit(tMMap)) {
			CError.buildErr(this, "记录传输失败的影像信息失败！");
			logger.debug("\t DocMoveThread.java -> error: logTransferError() : 插入 ES_DOCMOVE_LOG 失败！");
			logger.debug("\t DocMoveThread.java -> error: 引发错误的目录是："
					+ sRemotePath);
			return false;
		}
		tMMap = null;

		return true;
	} // function logTransferError end

	// ==========================================================================

	/**
	 * 记录传输任务信息
	 * 
	 * @param int
	 *            任务总数
	 * @param int
	 *            成功总数
	 * @param boolean
	 *            是否是完成后记录
	 * @return boolean
	 */
	private boolean logTransferTask(int nTotalCount, int nSuccessCount,
			boolean end) {
		String sql = "delete from ES_DOCMOVE_TASK where MoveID = '"
				+ "?sMoveTaskID?" + "' or TaskType = '1'";
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(sql);
		sqlbv8.put("sMoveTaskID", sMoveTaskID);
		MMap tMMap = new MMap();
		tMMap.put(sqlbv8, "DELETE");
		if (!pubSubmit(tMMap)) {
			CError.buildErr(this, "删除迁移批次任务起始信息失败！");
			logger.debug("\t DocMoveThread.java -> error: logTransferTask() : 删除 ES_DOCMOVE_TASK 失败！");
			return false;
		}
		tMMap = null;

		// 插入 ES_DOCMOVE_TASK
		ES_DOCMOVE_TASKSchema tES_DOCMOVE_TASKSchema = new ES_DOCMOVE_TASKSchema();
		tES_DOCMOVE_TASKSchema.setMoveID(sMoveTaskID);
		tES_DOCMOVE_TASKSchema.setManageCom(sOldManageCom);
		tES_DOCMOVE_TASKSchema.setToManageCom(sNewManageCom);
		tES_DOCMOVE_TASKSchema.setStartDate(sStartDate);
		tES_DOCMOVE_TASKSchema.setStartTime(sCurrentTime);
		tES_DOCMOVE_TASKSchema.setEndDate(sEndDate);
		if (end) {// 结束了才写上传送完成时间
			tES_DOCMOVE_TASKSchema.setEndTime(PubFun.getCurrentTime());
			tES_DOCMOVE_TASKSchema.setTaskType("0");
		} else {// 刚开始建立,结束时间留空,任务类型置1,为防止页面重复点击
			tES_DOCMOVE_TASKSchema.setEndTime("00:00:00");
			tES_DOCMOVE_TASKSchema.setTaskType("1");
		}
		tES_DOCMOVE_TASKSchema.setTaskCode("0");
		tES_DOCMOVE_TASKSchema.setTaskNumber(nTotalCount);
		tES_DOCMOVE_TASKSchema.setSuccNumber(nSuccessCount);
		MMap ttMMap = new MMap();
		ttMMap.put(tES_DOCMOVE_TASKSchema, "DELETE&INSERT");
		tES_DOCMOVE_TASKSchema = null;

		if (!pubSubmit(ttMMap)) {
			CError.buildErr(this, "插入迁移批次任务信息表失败！");
			logger.debug("\t DocMoveThread.java -> error: logTransferTask() : 插入 ES_DOCMOVE_TASKS 失败！");
			return false;
		}
		ttMMap = null;

		return true;
	} // function logTransferTask end

	// ==========================================================================

	/**
	 * 提交处理结果到数据库
	 * 
	 * @param MMap
	 * @return boolean
	 */
	private boolean pubSubmit(MMap tMMap) {
		if (tMMap == null) {
			CError.buildErr(this, "需要提交到数据库的信息不能为空！");
			logger.debug("\t DocMoveThread.java -> error: pubSubmit() : 参数 MMap 不能为空！");
			return false;
		}

		VData tVData = new VData();
		tVData.add(rGlobalInput);
		tVData.add(tMMap);
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(tVData, "OPERATION")) {
			CError.buildErr(this, "保存提交的信息到数据库失败！");
			logger.debug("\t DocMoveThread.java -> error: pubSubmit() : PubSubmit.submitData() 失败！");
			return false;
		}
		tPubSubmit = null;
		tVData = null;

		return true;
	} // function pubSubmit end

	// ==========================================================================

	/**
	 * 返回本类运行时产生的错误信息
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	} // function getErrors end

	protected void jbInit() throws Exception {
	}
	// ==========================================================================

	/**
	 * 调试主程序业务方法
	 * 
	 * @param String[]
	 */
	// public static void main(String[] args)
	// {
	// DocMoveThread tDocMoveThread = new DocMoveThread();
	// } //function main end
	// ==========================================================================

} // class DocMoveThread end
