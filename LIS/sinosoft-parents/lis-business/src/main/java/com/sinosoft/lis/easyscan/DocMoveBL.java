/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2006-12-18
 * @direction: 影像迁移提交
 * Modified By QianLy on 2007-02-09
 ******************************************************************************/

package com.sinosoft.lis.easyscan;
import org.apache.log4j.Logger;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.sinosoft.lis.db.ES_DOCMOVE_CONFIGDB;
import com.sinosoft.lis.db.ES_SERVER_INFODB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.ES_SERVER_INFOSchema;
import com.sinosoft.lis.vschema.ES_DOCMOVE_CONFIGSet;
import com.sinosoft.lis.vschema.ES_SERVER_INFOSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class DocMoveBL {
private static Logger logger = Logger.getLogger(DocMoveBL.class);
	// public DocMoveBL() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;
	private GlobalInput rGlobalInput;
	private TransferData rTransferData;
	// 私有变量
	private String sFtpHostName = null;
	private String sFtpUserName = null;
	private String sFtpPassword = null;
	private String sCurrentDate = PubFun.getCurrentDate();

	// ==========================================================================

	/**
	 * 外部调用本类的业务处理接口
	 * 
	 * @param VData
	 * @param String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// logger.debug("\t@> DocMoveBL.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			logger.debug("\t@> DocMoveBL.submitData() : 无法获取 InputData 数据！");
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			return false;
		} else {
			rInputData = new VData();
			rInputData = (VData) cInputData.clone();
		}
		rOperation = (cOperate != null) ? cOperate.trim() : "";

		// ----------------------------------------------------------------------

		// 业务处理
		if (!getInputData())
			return false;
		if (!checkData())
			return false;
		if (!dealData())
			return false;
		// 垃圾处理
		collectGarbage();

		// logger.debug("\t@> DocMoveBL.submitData() 结束");
		return true;
	} // function submitData end

	// ==========================================================================

	/**
	 * 获取外部传入数据和校验必录字段的合法性
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean getInputData() {
		// logger.debug("\t@> DocMoveBL.getInputData() 开始");

		// GlobalInput
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> DocMoveBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		// TransferData
		rTransferData = new TransferData();
		rTransferData = (TransferData) rInputData.getObjectByObjectName(
				"TransferData", 0);
		if (rTransferData == null) {
			CError.buildErr(this, "无法获取影像迁移参数信息！");
			logger.debug("\t@> DocMoveBL.getInputData() : 无法获取 TransferData 数据！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 迁出管理机构
		String sOldManageCom = (String) rTransferData
				.getValueByName("OldManageCom");
		if (sOldManageCom == null || sOldManageCom.trim().equals("")) {
			CError.buildErr(this, "无法获取迁出管理机构信息！");
			return false;
		}

		// 迁入管理机构
		String sNewManageCom = (String) rTransferData
				.getValueByName("NewManageCom");
		if (sNewManageCom == null || sNewManageCom.trim().equals("")) {
			CError.buildErr(this, "无法获取迁入管理机构信息！");
			return false;
		}

		if (rOperation != null && rOperation.trim().equals("RESEND")) {
			String sDocID = (String) rTransferData.getValueByName("DocID");
			if (sDocID == null && sDocID.trim().equals("")) {
				CError.buildErr(this, "重传时无法获得必需的DocID！");
				return false;
			}
		}

		// logger.debug("\t@> DocMoveBL.getInputData() 结束");
		return true;
	} // function getInputData end

	// ==========================================================================

	/**
	 * 根据传入的数据进行业务逻辑层的合法性校验
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean checkData() {
		// logger.debug("\t@> DocMoveBL.checkData() 开始");

		String QuerySQL = new String("");

		// ----------------------------------------------------------------------

		// 接收数据变量
		String sOldManageCom = (String) rTransferData
				.getValueByName("OldManageCom");
		String sNewManageCom = (String) rTransferData
				.getValueByName("NewManageCom");
		String sStartDate = (String) rTransferData.getValueByName("StartDate");
		String sEndDate = (String) rTransferData.getValueByName("EndDate");

		// ----------------------------------------------------------------------

		ES_SERVER_INFODB tES_SERVER_INFODB = new ES_SERVER_INFODB();
		ES_SERVER_INFOSet tES_SERVER_INFOSet = new ES_SERVER_INFOSet();

		// 查询 ES_COM_SERVER、ES_SERVER_INFO 检查迁出管理机构
		QuerySQL = "select * " + "from ES_SERVER_INFO " + "where HostName = "
				+ "(select HostName from ES_COM_SERVER where ManageCom = '"
				+ "?sOldManageCom?" + "')";
		// logger.debug(QuerySQL);
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(QuerySQL);
		sqlbv1.put("sOldManageCom",sOldManageCom );
		try {
			tES_SERVER_INFOSet = tES_SERVER_INFODB.executeQuery(sqlbv1);
		} catch (Exception ex) {
			CError.buildErr(this, "查询管理机构服务器对应关系及配置表发生异常！");
			logger.debug("\t DocMoveBL.java -> error: 查询管理机构服务器对应关系及配置表发生异常");
			return false;
		}
		if (tES_SERVER_INFOSet == null || tES_SERVER_INFOSet.size() <= 0) {
			CError.buildErr(this, "在管理机构服务器对应关系及配置表中找不到迁出管理机构的纪录！");
			logger.debug("\t DocMoveBL.java -> error: 在管理机构服务器对应关系及配置表中找不到迁出管理机构的纪录");
			return false;
		} else {
			ES_SERVER_INFOSchema tES_SERVER_INFOSchema = new ES_SERVER_INFOSchema();
			tES_SERVER_INFOSchema = tES_SERVER_INFOSet.get(1);
			// FTP 地址
			String sFtpHostAddr = tES_SERVER_INFOSchema.getServerPort();
			if (sFtpHostAddr == null || sFtpHostAddr.trim().equals("")) {
				CError.buildErr(this, "迁出管理机构的远程连接地址和端口不能为空！");
				logger.debug("\t DocMoveBL.java -> error: 迁出管理机构的远程连接地址和端口不能为空");
				return false;
			} else if (sFtpHostAddr.indexOf(":") != -1) {
				sFtpHostName = sFtpHostAddr.substring(0, sFtpHostAddr
						.lastIndexOf(":"));
			} else {
				sFtpHostName = sFtpHostAddr;
			}
			// FTP 用户
			sFtpUserName = tES_SERVER_INFOSchema.getLoginNameFTP();
			if (sFtpUserName == null || sFtpUserName.trim().equals("")) {
				sFtpUserName = "Anonymous";
			}
			// FTP 密码
			sFtpPassword = tES_SERVER_INFOSchema.getLoginPwdFTP();
			if ((sFtpPassword == null || sFtpPassword.trim().equals(""))
					&& sFtpUserName.equalsIgnoreCase("Anonymous")) {
				sFtpPassword = "anonymous@sinosoft.com.cn";
			}
			tES_SERVER_INFOSchema = null;
		}

		// 查询 ES_COM_SERVER、ES_SERVER_INFO 检查迁入管理机构
		QuerySQL = "select * " + "from ES_SERVER_INFO " + "where HostName = "
				+ "(select HostName from ES_COM_SERVER where ManageCom = '"
				+ "?sNewManageCom?" + "')";
		// logger.debug(QuerySQL);
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(QuerySQL);
		sqlbv2.put("sNewManageCom",sNewManageCom );
		try {
			tES_SERVER_INFOSet = tES_SERVER_INFODB.executeQuery(sqlbv2);
		} catch (Exception ex) {
			CError.buildErr(this, "查询管理机构服务器对应关系及配置表发生异常！");
			logger.debug("\t DocMoveBL.java -> error: 查询管理机构服务器对应关系及配置表发生异常");
			return false;
		}
		if (tES_SERVER_INFOSet == null || tES_SERVER_INFOSet.size() <= 0) {
			CError.buildErr(this, "在管理机构服务器对应关系及配置表中找不到迁入管理机构的纪录！");
			logger.debug("\t DocMoveBL.java -> error: 在管理机构服务器对应关系及配置表中找不到迁入管理机构的纪录");
			return false;
		}

		tES_SERVER_INFODB = null;
		tES_SERVER_INFOSet = null;

		// ----------------------------------------------------------------------

		// 查询 ES_DOCMOVE_CONFIG 检查迁移配置信息
		QuerySQL = "select * " + "from ES_DOCMOVE_CONFIG " + "where 1 = 1 "
				+ "and ToManageCom = '" + "?sNewManageCom?" + "'"
				+ "and ManageCom = "
				+ "(select HostName from ES_COM_SERVER where ManageCom = '"
				+ "?sOldManageCom?" + "')";
		// logger.debug(QuerySQL);
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(QuerySQL);
		sqlbv3.put("sNewManageCom",sNewManageCom);
		sqlbv3.put("sOldManageCom",sOldManageCom);
		ES_DOCMOVE_CONFIGDB tES_DOCMOVE_CONFIGDB = new ES_DOCMOVE_CONFIGDB();
		ES_DOCMOVE_CONFIGSet tES_DOCMOVE_CONFIGSet = new ES_DOCMOVE_CONFIGSet();
		try {
			tES_DOCMOVE_CONFIGSet = tES_DOCMOVE_CONFIGDB.executeQuery(sqlbv3);
		} catch (Exception ex) {
			CError.buildErr(this, "查询单证转移配置信息表发生异常！");
			logger.debug("\t DocMoveBL.java -> error: 查询单证转移配置信息表发生异常");
			return false;
		}
		if (tES_DOCMOVE_CONFIGSet == null || tES_DOCMOVE_CONFIGSet.size() <= 0) {
			CError.buildErr(this, "在单证转移配置信息表中找不到迁移配置信息！");
			logger.debug("\t DocMoveBL.java -> error: 在单证转移配置信息表中找不到迁移配置信息");
			return false;
		}
		tES_DOCMOVE_CONFIGDB = null;
		tES_DOCMOVE_CONFIGSet = null;

		// ----------------------------------------------------------------------

		// 查询 ES_DOC_MAIN 检查指定时间段内有无影像
		QuerySQL = "select count(*) " + "from ES_DOC_MAIN a " + "where 1 = 1 "
				+ "and exists "
				+ "(select 'X' from ES_DOC_PAGES where DocID = a.DocID) ";
		if (sStartDate != null && !sStartDate.trim().equals("")) {
			QuerySQL += " and a.MakeDate >= '" + "?sStartDate?" + "'";
		}
		if (sEndDate != null && !sEndDate.trim().equals("")) {
			QuerySQL += " and a.MakeDate <= '" + "?sEndDate?" + "'";
		}
		QuerySQL += " and a.ManageCom like concat(?sOldManageCom?,'%')";
		// logger.debug(QuerySQL);

		int nTotalTaskImages = 0;
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(QuerySQL);
		sqlbv4.put("sEndDate",sEndDate);
		sqlbv4.put("sStartDate",sStartDate);
		sqlbv4.put("sOldManageCom",sOldManageCom);
		ExeSQL tExeSQL = new ExeSQL();
		try {
			String sTotalTaskImages = tExeSQL.getOneValue(sqlbv4);
			if (sTotalTaskImages != null && !sTotalTaskImages.trim().equals("")) {
				nTotalTaskImages = Integer.parseInt(sTotalTaskImages);
			}
		} catch (Exception ex) {
			CError.buildErr(this, "查询计算需要迁移的影像总数出现异常！");
			logger.debug("\t DocMoveBL.java -> error: 查询计算需要迁移的影像总数出现异常");
			return false;
		}
		if (nTotalTaskImages <= 0) {
			CError.buildErr(this, "机构 " + sOldManageCom + " 下没有需要迁移的影像！");
			logger.debug("\t DocMoveBL.java -> error: 机构 "
					+ sOldManageCom + " 下没有需要迁移的影像！");
			return false;
		}
		tExeSQL = null;

		// ----------------------------------------------------------------------

		// 检查 FTP 是否可用
		FTPClient tFTPClient = new FTPClient();
		try {
			logger.debug("\t@> DocMoveBL.checkData() : Connecting to FTP server "
							+ sFtpHostName + " ...");
			tFTPClient.connect(sFtpHostName);
			logger.debug("\t@> DocMoveBL.checkData() : "
					+ tFTPClient.getReplyString());
			if (!FTPReply.isPositiveCompletion(tFTPClient.getReplyCode())) {
				tFTPClient.disconnect();
				CError.buildErr(this, "尝试连接 FTP 服务器 " + sFtpHostName + " 被拒绝。");
				logger.debug("\t DocMoveBL.java -> error: 尝试连接 FTP 服务器 "
						+ sFtpHostName + " 被拒绝。");
				return false;
			}
			if (!tFTPClient.login(sFtpUserName, sFtpPassword)) {
				CError.buildErr(this, "尝试登录 FTP 服务器 " + sFtpHostName + " 被拒绝。");
				logger.debug("\t DocMoveBL.java -> error: 尝试登录 FTP 服务器 "
						+ sFtpHostName + " 被拒绝。");
				return false;
			}
			logger.debug("\t@> DocMoveBL.checkData() : "
					+ tFTPClient.getReplyString());
		} catch (Exception ex) {
			CError.buildErr(this, "尝试登录 FTP 服务器 " + sFtpHostName
					+ " 失败。可能的原因是：" + ex.toString());
			logger.debug("\t DocMoveBL.java -> error: 尝试登录 FTP 服务器 "
					+ sFtpHostName + " 失败。可能的原因是：" + ex.toString());
			ex.printStackTrace();
			return false;
		} finally {
			if (tFTPClient.isConnected()) {
				try {
					tFTPClient.disconnect();
				} catch (Exception ex) {
				}
			}
			logger.debug("\t@> DocMoveBL.checkData() : Disconnected from FTP server "
							+ sFtpHostName + " .");
			tFTPClient = null;
		}

		// logger.debug("\t@> DocMoveBL.checkData() 结束");
		return true;
	} // function checkData end

	// ==========================================================================

	/**
	 * 本类的核心业务处理过程
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean dealData() {
		// logger.debug("\t@> DocMoveBL.dealData() 开始");

		if (!rOperation.equals("RESEND")) {
			// 接收数据变量
			String sOldManageCom = (String) rTransferData
					.getValueByName("OldManageCom");
			String sNewManageCom = (String) rTransferData
					.getValueByName("NewManageCom");
			String sStartDate = (String) rTransferData
					.getValueByName("StartDate");
			String sEndDate = (String) rTransferData.getValueByName("EndDate");
			String sMoveTaskID = sCurrentDate.replaceAll("-", "") + "_"
					+ PubFun1.CreateMaxNo("MOVEID", 1);
			// ----------------------------------------------------------------------

			// 启动迁移线程
			DocMoveThread tDocMoveThread = new DocMoveThread();
			tDocMoveThread.setGlobalInput(rGlobalInput);
			tDocMoveThread.setFtpHostName(sFtpHostName);
			tDocMoveThread.setFtpUserName(sFtpUserName);
			tDocMoveThread.setFtpPassword(sFtpPassword);
			tDocMoveThread.setOldManageCom(sOldManageCom);
			tDocMoveThread.setNewManageCom(sNewManageCom);
			tDocMoveThread.setStartDate(sStartDate);
			tDocMoveThread.setEndDate(sEndDate);
			tDocMoveThread.setMoveTaskID(sMoveTaskID);
			tDocMoveThread.setDeleteOnFinish(false);
			Thread tThread = new Thread(tDocMoveThread);
			tThread.setDaemon(false);
			tThread.start();
		} else if (rOperation != null && rOperation.equals("RESEND")) {
			// 接收数据变量
			String sOldManageCom = (String) rTransferData
					.getValueByName("OldManageCom");
			String sNewManageCom = (String) rTransferData
					.getValueByName("NewManageCom");
			String sStartDate = (String) rTransferData
					.getValueByName("StartDate");
			String sEndDate = (String) rTransferData.getValueByName("EndDate");
			String sDocID = (String) rTransferData.getValueByName("DocID");
			String sMoveTaskID = sCurrentDate.replaceAll("-", "") + "_"
					+ PubFun1.CreateMaxNo("MOVEID", 1);
			// ----------------------------------------------------------------------

			// 启动针对单张的迁移线程
			DocMovePerDocThread tDocMovePerDocThread = new DocMovePerDocThread();
			tDocMovePerDocThread.setGlobalInput(rGlobalInput);
			tDocMovePerDocThread.setFtpHostName(sFtpHostName);
			tDocMovePerDocThread.setFtpUserName(sFtpUserName);
			tDocMovePerDocThread.setFtpPassword(sFtpPassword);
			tDocMovePerDocThread.setOldManageCom(sOldManageCom);
			tDocMovePerDocThread.setNewManageCom(sNewManageCom);
			tDocMovePerDocThread.setStartDate(sStartDate);
			tDocMovePerDocThread.setEndDate(sEndDate);
			tDocMovePerDocThread.setMoveTaskID(sMoveTaskID);
			tDocMovePerDocThread.setDocID(sDocID);
			tDocMovePerDocThread.setDeleteOnFinish(false);
			Thread tThread = new Thread(tDocMovePerDocThread);
			tThread.setDaemon(false);
			tThread.start();
		}
		// logger.debug("\t@> DocMoveBL.dealData() 结束");
		return true;
	} // function dealData end

	// ==========================================================================

	/**
	 * 返回传入本类的操作类型
	 * 
	 * @param null
	 * @return String
	 */
	public String getOperation() {
		return rOperation;
	} // function getOperation end

	// ==========================================================================

	/**
	 * 返回本类运行时产生的错误信息
	 * 
	 * @param null
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	} // function getErrors end

	// ==========================================================================

	/**
	 * 处理本类运行时产生的垃圾
	 * 
	 * @param null
	 */
	private void collectGarbage() {
		if (rInputData != null)
			rInputData = null;
		if (rGlobalInput != null)
			rGlobalInput = null;
		if (rTransferData != null)
			rTransferData = null;
	} // function collectGarbage end

	// ==========================================================================

	/**
	 * 调试主程序业务方法
	 * 
	 * @param String[]
	 */
	// public static void main(String[] args)
	// {
	// DocMoveBL tDocMoveBL = new DocMoveBL();
	// } //function main end
	// ==========================================================================

} // class DocMoveBL end
