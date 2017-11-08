/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 钱林燕 <QianLy@sinosoft.com.cn>
 * @version  : 1.01
 * @date     : 2007-01-22
 * @direction: 影像迁移单DocID传输和日志记录,方便重传,错误日志更新,特为单张重传设计
 *             继承自DocMoveThread,为了方便维护,逻辑清晰,不在DocMoveThread
 *             中写判断是单张还是整个重传,而是在DocMoveBL中根据调用入口不同选择调用的
 *             迁移线程.(注:这里的"单张"是指DocID而言,可能包含多个Page)
 * @Modified By QianLy on 2007-02-07
 ******************************************************************************/

package com.sinosoft.lis.easyscan;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Vector;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.sinosoft.lis.db.ES_DOC_PAGESDB;
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
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class DocMovePerDocThread extends DocMoveThread {
private static Logger logger = Logger.getLogger(DocMovePerDocThread.class);
	public DocMovePerDocThread() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 需要单传的DocID
	private String sDocID = null;
	// 该张是否处理结束
	private boolean isOver = false;
	// 保存计划迁移总数
	private int nTotalTaskImages = 0;
	// 统计成功数目
	private int nTotalSuccImages = 0;
	// 保存文件名列表
	private Vector vector;

	// ==========================================================================

	/**
	 * 设置需要传送的DocID
	 * 
	 * @param String
	 */
	public void setDocID(String DocID) {
		this.sDocID = DocID;
	}

	/**
	 * 获取需要传送的DocID
	 * 
	 * @retutn String
	 */
	public String getDocID() {
		return this.sDocID;
	}

	/**
	 * 设置传送完成标志
	 * 
	 * @param boolean
	 */
	public void setOver(boolean over) {
		this.isOver = over;
	}

	/**
	 * 获取传送完成标志
	 * 
	 * @retutn boolean
	 */
	public boolean getOver() {
		return this.isOver;
	}

	/**
	 * 本类的核心业务处理过程
	 */
	public void run() {
		logger.debug("\t@> DocMovePerDocThread.run() 开始");

		if (!chkRunParameters())
			return;
		if (!moveScanedImages())
			return;
		this.setOver(true);

		if (this.getOver()) {
			try {
				Thread.currentThread().yield();
			} catch (Exception ex) {
				logger.debug("\t@> DocMovePerDocThread.yield() 异常");
			}
		}
		logger.debug("\t@> DocMovePerDocThread.run() 结束");
	} // function run end

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
			logger.debug("\t DocMovePerDocThread.java -> error: 需要迁移的迁出管理机构不能为空");
			return false;
		}

		if (sNewManageCom == null || sNewManageCom.trim().equals("")) {
			CError.buildErr(this, "需要迁移的迁入管理机构不能为空！");
			logger.debug("\t DocMovePerDocThread.java -> error: 需要迁移的迁入管理机构不能为空");
			return false;
		}

		if (sDocID == null || sDocID.trim().equals("")) {
			CError.buildErr(this, "需要迁移的单张DocID不能为空！");
			logger.debug("\t DocMovePerDocThread.java -> error: 需要迁移的单张DocID不能为空");
			return false;
		}

		sNewHostName = getManageComHostName(sNewManageCom);
		if (sNewHostName == null || sNewHostName.trim().equals("")) {
			CError.buildErr(this, "迁入管理机构的机构名称不能为空！");
			logger.debug("\t DocMovePerDocThread.java -> error: 迁入管理机构的机构名称不能为空");
			return false;
		}

		sNewServerIP = getManageComServerIP(sNewManageCom);
		if (sNewServerIP == null || sNewServerIP.trim().equals("")) {
			CError.buildErr(this, "迁入管理机构的 IP 地址不能为空！");
			logger.debug("\t DocMovePerDocThread.java -> error: 迁入管理机构的 IP 地址不能为空");
			return false;
		}

		sNewBasePath = getManageComBasePath(sNewManageCom);
		if (sNewBasePath == null || sNewBasePath.trim().equals("")) {
			CError.buildErr(this, "迁入管理机构的图片路径不能为空！");
			logger.debug("\t DocMovePerDocThread.java -> error: 迁入管理机构的图片路径不能为空");
			return false;
		}

		sMoveTaskID = sCurrentDate.replaceAll("-", "") + "_"
				+ PubFun1.CreateMaxNo("MOVEID", 1);
		if (sMoveTaskID == null || sMoveTaskID.trim().equals("")) {
			CError.buildErr(this, "计算迁移任务批次号失败！");
			logger.debug("\t DocMovePerDocThread.java -> error: 计算迁移任务批次号失败");
			return false;
		}

		return true;
	} // function chkRunParameters end

	// 传输单张Page并与得到的MD5进行校验
	private boolean getOnePageAndCheck(FTPClient mFTPClient,
			String sRemotePicPath, String pagename) {
		// 进入FTP的指定目录
		try {
			if (!mFTPClient.changeWorkingDirectory("/")
					|| !mFTPClient.changeWorkingDirectory(sRemotePicPath)) {
				logger.debug("\t DocMovePerDocThread.java -> error: moveScanedImages() : 进入远程 FTP 目录失败");
				logger.debug("\t DocMovePerDocThread.java -> error: 引发错误的目录是："
								+ sRemotePicPath);
				logTransferError(sRemotePicPath, null);
				return false;
			}
		} catch (Exception ex) {
			CError.buildErr(this, "进入远程 FTP 目录失败，可能的原因是 FTP 连接超时！");
			logger.debug("\t DocMovePerDocThread.java -> error: 进入远程 FTP 目录失败，可能的原因是 FTP 连接超时");
			return false;
		}

		String sLocalPicPath = sNewBasePath + sRemotePicPath;
		if (sLocalPicPath != null && !sLocalPicPath.endsWith("/")) {
			sLocalPicPath += "/";
		}
		// logger.debug("本地目录是 " + sLocalPicPath);
		File tLocalFile = new File(sLocalPicPath);

		if (!tLocalFile.exists() || !tLocalFile.isDirectory()) {
			try {
				tLocalFile.mkdirs();
			} catch (Exception ex) {
				logger.debug("\t@> DocMovePerDocThread.moveScanedImages() : 创建本地目录失败");
				logger.debug("\t   引发错误的目录是：" + sRemotePicPath);
				logTransferError(sRemotePicPath, null);
				return false;
			}
		}

		// 只传需要的DocID对应的文件
		String sImageFileName = pagename;
		String sImageFullPath = new String("");

		sImageFullPath = sLocalPicPath + sImageFileName;
		logger.debug("当前文件名是 " + sImageFileName);
		tLocalFile = new File(sImageFullPath);
		// 本地不存在该文件才传输
		if (tLocalFile.exists() && tLocalFile.isFile()) {
			// 如果文件存在则进行MD5校验
			if (compareByMD5(sImageFullPath)) {
				logger.debug("\t DocMovePerDocThread.java -> moveScanedImages() : 文件 "
								+ sImageFileName + " 已存在并且通过MD5校验，跳过！");
				return true;
			}
		} else {
			try {
				FileOutputStream tFileOutputStream = new FileOutputStream(
						sImageFullPath);
				mFTPClient.retrieveFile(sImageFileName, tFileOutputStream);
				tFileOutputStream.close();
				tFileOutputStream = null;

				if (!compareByMD5(sImageFullPath)) {
					logTransferError(sRemotePicPath, sImageFileName);
					return false;
				}

				// 在向量中找不到说明是个未更新的，更新并加入向量
				// 找到了则说明是已经更新过了，不用再处理
				if (!findInVector(sImageFileName)) {
					updateDocPageInfo(sRemotePicPath, sImageFileName);
					addToVector(sImageFileName);
				}
				nTotalSuccImages += 1;
				// 传输成功删除源文件; 还需要校验文件是否完整, 暂不放开
				// if (isDeleteOnFinish)
				// {
				// try
				// {
				// mFTPClient.deleteFile(sRemotePicPath + sImageFileName);
				// }
				// catch (Exception ex)
				// {
				// logger.debug("\t@>
				// DocMovePerDocThread.moveScanedImages() : 文件 " +
				// sImageFileName + " 传送成功, 但删除失败！");
				// }
				// }
			} catch (Exception ex) {
				logger.debug("\t DocMovePerDocThread.java -> error: 文件 "
						+ sImageFileName + " 传送失败！");
				logger.debug("\t DocMovePerDocThread.java -> error: 引发错误的目录是："
								+ sRemotePicPath);
				logTransferError(sRemotePicPath, sImageFileName);
				return false;
			}
		}
		return true;
	}

	/**
	 * 迁移已经扫描的影像资料,针对单张DocID
	 * 
	 * @return boolean
	 */
	private boolean moveScanedImages() {
		// 查询迁移总数
		String QuerySQL = new String("");
		QuerySQL = "select count(*) " + "from ES_DOC_PAGES a " + "where 1 = 1 "
				+ "and exists "
				+ "(select 'X' from ES_DOC_MAIN where DocID = a.DocID) "
				+ "and a.ManageCom like concat(?sOldManageCom?,'%')"
				+ "and a.docid = '" + "?sDocID?" + "'";
		
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(QuerySQL);
		sqlbv1.put("sOldManageCom", sOldManageCom);
		sqlbv1.put("sDocID", sDocID);
		ExeSQL tExeSQL = new ExeSQL();
		try {
			String sTotalTaskImages = tExeSQL.getOneValue(sqlbv1);
			if (sTotalTaskImages != null && !sTotalTaskImages.trim().equals("")) {
				nTotalTaskImages = Integer.parseInt(sTotalTaskImages);
				// 图片可能有Gif,Tif,Jpg等，数目没有准确对应关系，翻倍显得无意义
				// nTotalTaskImages = 2 * nTotalTaskImages;
			}
			logger.debug("\t DocMovePerDocThread.java -> DocID = "
					+ sDocID + " 需要迁移的影像总数是 " + nTotalTaskImages);
		} catch (Exception ex) {
			CError.buildErr(this, "查询计算需要迁移的影像总数出现异常！");
			logger.debug("\t DocMovePerDocThread.java -> error: 查询计算需要迁移的影像总数出现异常");
			return false;
		}
		if (nTotalTaskImages <= 0) {
			CError.buildErr(this, "单证号 " + sDocID + " 下没有需要迁移的影像！");
			logger.debug("\t DocMovePerDocThread.java -> error: 单证号 "
					+ sDocID + " 下没有需要迁移的影像！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 查询DocID对应的Page名称和存放路径
		QuerySQL = "select distinct pagename,PicPath " + "from ES_DOC_PAGES a "
				+ "where 1 = 1 " + "and exists "
				+ "(select 'X' from ES_DOC_MAIN where DocID = a.DocID) "
				+ "and a.ManageCom like concat(?sOldManageCom?,'%') "
				+ "and a.docid = '" + "?sDocID?" + "'";

		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(QuerySQL);
		sqlbv2.put("sOldManageCom", sOldManageCom);
		sqlbv2.put("sDocID", sDocID);
		SSRS tSSRS = new SSRS();
		try {
			tSSRS = tExeSQL.execSQL(sqlbv2);
		} catch (Exception ex) {
			CError.buildErr(this, "查询远程服务器影像名称和存放路径出现异常！");
			return false;
		}

		// 建立 FTP 连接，改到下面是防止上面的查询时间过长导致FTP超时
		FTPClient mFTPClient = new FTPClient();
		try {
			logger.debug("\t DocMovePerDocThread.java -> moveScanedImages() : Connecting to FTP server "
							+ sFtpHostName + " ...");
			mFTPClient.connect(sFtpHostName);
			logger.debug("\t DocMovePerDocThread.java -> moveScanedImages() : "
							+ mFTPClient.getReplyString());
			if (!FTPReply.isPositiveCompletion(mFTPClient.getReplyCode())) {
				mFTPClient.disconnect();
				CError.buildErr(this, "尝试连接 FTP 服务器 " + sFtpHostName + " 被拒绝。");
				logger.debug("\t DocMovePerDocThread.java -> error: 尝试连接 FTP 服务器 "
								+ sFtpHostName + " 被拒绝。");
				return false;
			}
			if (!mFTPClient.login(sFtpUserName, sFtpPassword)) {
				CError.buildErr(this, "尝试登录 FTP 服务器 " + sFtpHostName + " 被拒绝。");
				logger.debug("\t DocMovePerDocThread.java -> error: 尝试登录 FTP 服务器 "
								+ sFtpHostName + " 被拒绝。");
				return false;
			}
			mFTPClient.setFileType(FTP.BINARY_FILE_TYPE); // 设置文件传输格式
			logger.debug("\t DocMovePerDocThread.java -> moveScanedImages() : "
							+ mFTPClient.getReplyString());
			mFTPClient.enterLocalPassiveMode(); // 设置为被动模式
			logger.debug("\t DocMovePerDocThread.java -> moveScanedImages() : "
							+ mFTPClient.getReplyString());
		} catch (Exception ex) {
			CError.buildErr(this, "尝试登录 FTP 服务器 " + sFtpHostName
					+ " 失败。可能的原因是：" + ex.toString());
			logger.debug("\t DocMovePerDocThread.java -> error: 尝试登录 FTP 服务器 "
							+ sFtpHostName + " 失败。可能的原因是：" + ex.toString());
			ex.printStackTrace();
			return false;
		}
		// ----------------------------------------------------------------------

		// 建立任务开始信息
		if (!logTransferTask(nTotalTaskImages, nTotalSuccImages, false)) {
			return false;
		}

		// 循环每个图片进行处理
		if (tSSRS != null && tSSRS.getMaxRow() > 0) {
			for (int i = 0; i < tSSRS.getMaxRow(); i++) {
				String sPageName = tSSRS.GetText(i + 1, 1);
				String sRemotePicPath = tSSRS.GetText(i + 1, 2);

				// 进入一个目录后清掉向量，其实重传后由于数据量小，这个处理没必要，但是加上也没影响
				vct.clear();

				// 进入FTP的指定目录,否则传不过来 『因为这个调了一下午:( 』
				try {
					if (!mFTPClient.changeWorkingDirectory("/")
							|| !mFTPClient
									.changeWorkingDirectory(sRemotePicPath)) {
						logger.debug("\t DocMovePerDocThread.java -> error: moveScanedImages() : 进入远程 FTP 目录失败");
						logger.debug("\t DocMovePerDocThread.java -> error: 引发错误的目录是："
										+ sRemotePicPath);
						logTransferError(sRemotePicPath, null);
						return false;
					}
				} catch (Exception ex) {
					CError.buildErr(this, "进入远程 FTP 目录失败，可能的原因是 FTP 连接超时！");
					logger.debug("\t DocMovePerDocThread.java -> error: 进入远程 FTP 目录失败，可能的原因是 FTP 连接超时");
					return false;
				}
				// 检查本地目录
				String sLocalPicPath = sNewBasePath + sRemotePicPath;
				if (sLocalPicPath != null && !sLocalPicPath.endsWith("/")) {
					sLocalPicPath += "/";
				}
				// logger.debug("本地目录是 " + sLocalPicPath);

				File tLocalFile = new File(sLocalPicPath);
				if (!tLocalFile.exists() || !tLocalFile.isDirectory()) {
					try {
						tLocalFile.mkdirs();
					} catch (Exception ex) {
						logger.debug("\t DocMovePerDocThread.java -> error: getPathAndHstReady() : 创建本地目录失败");
						logger.debug("\t DocMovePerDocThread.java -> error: 引发错误的目录是："
										+ sRemotePicPath);
						logTransferError(sRemotePicPath, null);
						return false;
					}
				}

				// 首先得到该目录下的MD5文件并进行校验,
				// 然后拆分到Hashtable中作为图片校验依据
				if (!getAndCheckMD5File(mFTPClient, sLocalPicPath)) {
					logger.debug("\t DocMovePerDocThread.java -> error: getPathAndHstReady() : 从本目录下取得并校验MD5文件失败!");
					logger.debug("\t DocMovePerDocThread.java -> error: 引发错误的目录是："
									+ sRemotePicPath);
					logTransferError(sRemotePicPath, null);
					return false;
				}
				// 将文件名放到vector中，作为查找之用
				vector = new Vector(tHashtable.keySet());

				// 最多的可能是几种图片格式都存在，但是必须是在fileFilter中定义的格式
				for (int j = 0; j < fileFilter.length; j++) {
					String sPageNameWithExt = sPageName + "."
							+ fileFilter[j].toLowerCase();
					// 由于大部分文件名是小写后缀的，这个分支要在前面
					if (vector.contains(sPageNameWithExt)) {
						if (!getOnePageAndCheck(mFTPClient, sRemotePicPath,
								sPageNameWithExt)) {
							CError.buildErr(this, "传送文件名为: " + sPageNameWithExt
									+ " 的图片出错");
							logger.debug("\t DocMovePerDocThread.java -> error: 传送文件名为: "
											+ sPageNameWithExt + " 的图片出错");
							return false;
						}
					} else {// 大部分情况不会走到这里，但是出现F1234.Gif这种扩展名大小写混写的情况也可以处理
						for (int k = 0; k < vector.size(); k++) {
							if (vector.get(k).toString().toLowerCase().equals(
									sPageNameWithExt.toLowerCase())) {
								if (!getOnePageAndCheck(mFTPClient,
										sRemotePicPath, vector.get(k)
												.toString())) {
									CError.buildErr(this, "传送文件名为: "
											+ vector.get(k).toString()
											+ " 的图片出错");
									logger.debug("\t DocMovePerDocThread.java -> error: 传送文件名为: "
													+ vector.get(k).toString()
													+ " 的图片出错");
									return false;
								}
							}
						}
					}
				}
			}
		}
		tExeSQL = null;
		tSSRS = null;

		// ----------------------------------------------------------------------

		// 记录任务信息
		if (!logTransferTask(nTotalTaskImages, nTotalSuccImages, true)) {
			CError.buildErr(this, "记录重传任务信息异常");
			logger.debug("\t DocMovePerDocThread.java -> error: 记录重传任务信息异常.");
			return false;
		}

		// ----------------------------------------------------------------------

		// 断开 FTP 连接
		if (mFTPClient != null && mFTPClient.isConnected()) {
			try {
				mFTPClient.logout();
				mFTPClient.disconnect();
			} catch (Exception ex) {
			}
		}
		logger.debug("\t DocMovePerDocThread.java -> Disconnected from FTP server "
						+ sFtpHostName + " .");
		mFTPClient = null;

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
			logger.debug("\t DocMovePerDocThread.java -> error: 已传输成功需更新影像信息的文件名不能为空");
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
				+ "(select 'X' from ES_DOC_MAIN where DocID = a.DocID) "
				+ "and a.ManageCom like concat(?sOldManageCom?,'%') "
				+ "and a.docid = '" + "?sDocID?" + "'";
		if (sRemotePath != null && !sRemotePath.trim().equals("")) {
			QuerySQL += " and a.PicPath like concat(?sRemotePath?,'%')";
		}
		if (sFileName != null && !sFileName.trim().equals("")) {
			QuerySQL += " and a.PageName = '" + "?sFileName?" + "'";
		}
		// logger.debug(QuerySQL);

		// ----------------------------------------------------------------------

		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(QuerySQL);
		sqlbv3.put("sOldManageCom", sOldManageCom);
		sqlbv3.put("sDocID", sDocID);
		sqlbv3.put("sRemotePath", sRemotePath);
		sqlbv3.put("sFileName", sFileName);
		// 查询 ES_DOC_PAGES
		ES_DOC_PAGESDB tES_DOC_PAGESDB = new ES_DOC_PAGESDB();
		ES_DOC_PAGESSet tES_DOC_PAGESSet = new ES_DOC_PAGESSet();
		try {
			tES_DOC_PAGESSet = tES_DOC_PAGESDB.executeQuery(sqlbv3);
		} catch (Exception ex) {
			CError.buildErr(this, "查询以更新已成功迁移的影像信息发生异常！");
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
			logger.debug("\t DocMovePerDocThread.java -> error: updateDocPageInfo() : 插入 ES_DOC_PAGES_B 或更新 ES_DOC_PAGES 失败！");
			logger.debug("\t DocMovePerDocThread.java -> error: 引发错误的文件是："
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
				+ " from ES_DOC_PAGES a" + " where 1 = 1 "
				+ " and a.ManageCom like concat(?sOldManageCom?,'%') "
				+ " and exists "
				+ "     (select 'X' from ES_DOC_MAIN where DocID = a.DocID)"
				+ " and a.docid = '" + "?sDocID?" + "'";
		if (sRemotePath != null && !sRemotePath.trim().equals("")) {
			QuerySQL += " and a.PicPath like concat(?sRemotePath?,'%')";
		}
		if (sFileName != null && !sFileName.trim().equals("")) {
			QuerySQL += " and a.PageName = '" + "?sFileName?" + "'";
		}

		// ----------------------------------------------------------------------

		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(QuerySQL);
		sqlbv4.put("sOldManageCom", sOldManageCom);
		sqlbv4.put("sDocID", sDocID);
		sqlbv4.put("sRemotePath", sRemotePath);
		sqlbv4.put("sFileName", sFileName);
		// 查询 ES_DOC_PAGES
		ExeSQL tes = new ExeSQL();
		SSRS ss = new SSRS();
		try {
			ss = tes.execSQL(sqlbv4);
		} catch (Exception ex) {
			CError.buildErr(this, "查询传输失败的影像信息发生异常！");
			logger.debug("\t DocMovePerDocThread.java -> error: logTransferError() : 查询传输失败的影像信息发生异常！");
			return false;
		}
		if (ss == null || ss.getMaxRow() < 1) {
			CError.buildErr(this, "查询传输失败的影像信息发生异常！");
			logger.debug("\t DocMovePerDocThread.java -> error: logTransferError() : 查询传输失败的影像信息发生异常！");
			return false;
		} else {
			ES_DOCMOVE_LOGSet tES_DOCMOVE_LOGSetNew = new ES_DOCMOVE_LOGSet();
			for (int i = 1; i <= ss.getMaxRow(); i++) {
				ES_DOCMOVE_LOGSchema tES_DOCMOVE_LOGSchemaNew = new ES_DOCMOVE_LOGSchema();
				tES_DOCMOVE_LOGSchemaNew.setMoveID(sMoveTaskID);
				tES_DOCMOVE_LOGSchemaNew.setDocID(sDocID);
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
			logger.debug("\t DocMovePerDocThread.java -> error: logTransferError() : 插入 ES_DOCMOVE_LOG 失败！");
			logger.debug("\t DocMovePerDocThread.java -> error: 引发错误的目录是："
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
				+ "?sMoveTaskID?" + "' and TaskType = '1'";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(sql);
		sqlbv5.put("sMoveTaskID", sMoveTaskID);
		String sql2 = "delete from ES_DOCMOVE_LOG where DocID = '" + "?sDocID?"
				+ "' and Flag = '2'";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(sql2);
		sqlbv6.put("sDocID", sDocID);
		MMap tMMap = new MMap();
		tMMap.put(sqlbv5, "DELETE");
		tMMap.put(sqlbv6, "DELETE");
		if (!pubSubmit(tMMap)) {
			CError.buildErr(this, "删除迁移批次任务起始信息失败！");
			logger.debug("\t DocMovePerDocThread.java -> error: logTransferTask() : 删除 ES_DOCMOVE_TASK 或ES_DOCMOVE_LOG中的临时任务信息失败！");
			return false;
		}
		tMMap = null;

		ExeSQL tes = new ExeSQL();
		SSRS ss = new SSRS();
		String ssql = "";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			ssql = "select picpath,makedate,min(makedate),max(makedate) from ES_DOC_PAGES a"
					+ " where 1 = 1"
					+ " and docid = '"
					+ "?sDocID?"
					+ "'"
					+ " and rownum = 1" + " group by picpath,makedate";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			ssql = "select picpath,makedate,min(makedate),max(makedate) from ES_DOC_PAGES a"
					+ " where 1 = 1"
					+ " and docid = '"
					+ "?sDocID?"
					+ "'"
					+ "" + " group by picpath,makedate limit 1";
		}
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(ssql);
		sqlbv7.put("sDocID", sDocID);
		try {
			ss = tes.execSQL(sqlbv7);
		} catch (Exception ex) {
			CError.buildErr(this, "计算该DocID的扫描路径，起止日期有误！");
			logger.debug("\t DocMovePerDocThread.java -> error: logTransferTask() : 计算该DocID的扫描起止日期有误！");
		}
		String startdate = "";
		String enddate = "";
		String filepath = "";
		String filedate = "";
		if (ss != null && ss.getMaxRow() > 0) {
			filepath = ss.GetText(1, 1);
			filedate = ss.GetText(1, 2);
			startdate = ss.GetText(1, 3);
			enddate = ss.GetText(1, 4);
		}

		// 插入 ES_DOCMOVE_TASK
		ES_DOCMOVE_TASKSchema tES_DOCMOVE_TASKSchema = new ES_DOCMOVE_TASKSchema();
		tES_DOCMOVE_TASKSchema.setMoveID(sMoveTaskID);
		tES_DOCMOVE_TASKSchema.setManageCom(sOldManageCom);
		tES_DOCMOVE_TASKSchema.setToManageCom(sNewManageCom);
		tES_DOCMOVE_TASKSchema.setStartDate(startdate);
		tES_DOCMOVE_TASKSchema.setStartTime(sCurrentTime);
		tES_DOCMOVE_TASKSchema.setEndDate(enddate);
		if (end) {// 结束了才写上传送完成时间,TaskType = 2 标记此次为重传
			tES_DOCMOVE_TASKSchema.setEndTime(PubFun.getCurrentTime());
			tES_DOCMOVE_TASKSchema.setTaskType("2");
		} else {// 刚开始建立,结束时间留空,任务类型置1,为防止页面重复点击
			tES_DOCMOVE_TASKSchema.setEndTime("00:00:00");
			tES_DOCMOVE_TASKSchema.setTaskType("1");
		}
		tES_DOCMOVE_TASKSchema.setTaskCode("0");
		tES_DOCMOVE_TASKSchema.setTaskNumber(nTotalCount);
		tES_DOCMOVE_TASKSchema.setSuccNumber(nSuccessCount);

		// 插入ES_DOCMOVE_LOG 作为临时任务之用,任务完成修改Flag = 0
		ES_DOCMOVE_LOGSchema tES_DOCMOVE_LOGSchema = new ES_DOCMOVE_LOGSchema();
		tES_DOCMOVE_LOGSchema.setDocID(sDocID);
		if (end) {
			tES_DOCMOVE_LOGSchema.setLastDate(sCurrentDate);
			tES_DOCMOVE_LOGSchema.setLastTime(sCurrentTime);
			tES_DOCMOVE_LOGSchema.setFileDate(filedate);
			tES_DOCMOVE_LOGSchema.setFlag("0"); // 0:正确 1:错误 2:重传中
		} else {
			tES_DOCMOVE_LOGSchema.setFlag("2");
		}
		tES_DOCMOVE_LOGSchema.setFilePath(filepath);
		tES_DOCMOVE_LOGSchema.setMoveID(sMoveTaskID);
		tES_DOCMOVE_LOGSchema.setManageCom(sOldManageCom);
		tES_DOCMOVE_LOGSchema.setToManageCom(sNewManageCom);

		MMap ttMMap = new MMap();
		ttMMap.put(tES_DOCMOVE_TASKSchema, "DELETE&INSERT");
		ttMMap.put(tES_DOCMOVE_LOGSchema, "DELETE&INSERT");
		tES_DOCMOVE_TASKSchema = null;
		tES_DOCMOVE_LOGSchema = null;

		if (!pubSubmit(ttMMap)) {
			CError.buildErr(this, "插入迁移批次任务信息表失败！");
			logger.debug("\t DocMovePerDocThread.java -> error: logTransferTask() : 插入 ES_DOCMOVE_TASKS 或 ES_DOCMOVE_LOG 失败！");
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
			logger.debug("\t DocMovePerDocThread.java -> error: pubSubmit() : 参数 MMap 不能为空！");
			return false;
		}

		VData tVData = new VData();
		tVData.add(rGlobalInput);
		tVData.add(tMMap);
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(tVData, "OPERATION")) {
			CError.buildErr(this, "保存提交的信息到数据库失败！");
			logger.debug("\t DocMovePerDocThread.java -> error: pubSubmit() : PubSubmit.submitData() 失败！");
			return false;
		}
		tPubSubmit = null;
		tVData = null;

		return true;
	} // function pubSubmit end

	// ==========================================================================

	/**
	 * 调试主程序业务方法
	 * 
	 * @param String[]
	 */
	// public static void main(String[] args)
	// {
	// DocMovePerDocThread tDocMovePerDocThread = new DocMovePerDocThread();
	// } //function main end
	// ==========================================================================

} // class DocMovePerDocThread end
