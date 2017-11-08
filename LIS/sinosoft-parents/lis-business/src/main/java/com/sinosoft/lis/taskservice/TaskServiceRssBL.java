package com.sinosoft.lis.taskservice;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.dom4j.io.SAXReader;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import com.sinosoft.lis.taskservice.encrypt.EncryptPassword;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * 生成当天任务运行的RSS日志，供订阅使用
 * 生成用户的加密RSSID
 * @author sinosoft
 * 
 */
/**
 * @author sinosoft
 * 
 */
public class TaskServiceRssBL implements BusinessService {
private static Logger logger = Logger.getLogger(TaskServiceRssBL.class);
	/**
	 * RSS最大显示数
	 */
	private int mMaxNum = 100;

	public CErrors mErrors = new CErrors();

	private VData mInputData = new VData();

	private VData mResult = new VData();

	/**
	 * 默认RSS存放路径，保存在工程类内
	 */
	private String mXMLFilePath = this.getClass().getResource("").getPath()
			.substring(1);

	/**
	 * 默认RSS文件名
	 */
	private String mXMLFileName = "RSSTaskService.xml";

	/**
	 * 
	 */
	public TaskServiceRssBL() {
		// super();
		// TODO Auto-generated constructor stub
		initRSSConfig();
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return this.mResult;
	}

	public boolean submitData(VData tVData, String tOperater) {
		// TODO Auto-generated method stub
		if (!check(tVData, tOperater)) {
			return false;
		}
		if (!getInputData(tVData, tOperater)) {
			return false;
		}
		if (!dealData(tOperater)) {
			return false;
		}
		return true;
	}

	/**
	 * 生成XML文件 或者 返回rss订阅文件字符串形式
	 * 
	 * @param tOperater
	 * @return
	 */
	private boolean dealData(String tOperater) {
		if ("GenerateRSS".equalsIgnoreCase(tOperater)) {
			if (!generateRSS()) {
				return false;
			}
		} else if ("OrderRSS".equalsIgnoreCase(tOperater)) {
			if (!orderRSS()) {
				return false;
			}
		} else if ("AddressRSS".equalsIgnoreCase(tOperater)) {
			if (!addressRSS()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 生成XML文件
	 * 
	 * @return
	 */
	private boolean generateRSS() {
		try {
			Element root = new Element("rss");// 根节点
			root.addAttribute("version", "2.0");
			Document tDoc = new Document(root);// 文档
			Element tChannel = new Element("channel");
			String tTitle = "";
			String tDesc = "";
			String[] t = null;

			tTitle = "TaskServiceRSS";
			tChannel.addContent(new Element("title").setText(tTitle));
			tChannel.addContent(new Element("link").setText(""));
			tChannel.addContent(new Element("description").setText(tDesc));
			for (int i = 0; i < this.mInputData.size(); i++) {
				t = (String[]) this.mInputData.get(i);
				Element elements = new Element("item");
				tTitle = t[0] + "， " + t[9];
				tDesc = t[1] + "， " + t[2] + "， " + t[3] + "<br />" + t[4]
						+ "<br />" + t[5] + "， " + t[6] + "<br />" + t[7]
						+ "<br />" + t[8] + "， " + t[10];
				elements.addAttribute("index", "" + i);
				elements.addContent(new Element("title").setText(tTitle));
				elements.addContent(new Element("link").setText(""));
				elements.addContent(new Element("description").setText(tDesc));
				tChannel.addContent(elements);
			}
			root.addContent(tChannel);
			XMLOutputter tXMLOutputter = new XMLOutputter();
			tXMLOutputter.setEncoding("UTF-8");
			tXMLOutputter.output(tDoc, new FileOutputStream(this.mXMLFilePath
					+ this.mXMLFileName));
			logger.debug("RSS Total Num is " + this.mInputData.size()
					+ "." + " RSS File :" + this.mXMLFilePath
					+ this.mXMLFileName);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 返回rss订阅文件字符串形式
	 * 
	 * @return
	 */
	private boolean orderRSS() {
		String tXML = "";
		try {
			String tFile = this.mXMLFilePath + this.mXMLFileName;
			File tXMLFile = new File(tFile);
			SAXReader tSAXReader = new SAXReader();
			// InputStream is = new FileInputStream(tFile);
			// InputStreamReader strInStream = new InputStreamReader(is, "GBK");
			if (tXMLFile.canRead()) {
				org.dom4j.Document tDocument = tSAXReader.read(tXMLFile);
				tXML = tDocument.asXML();
				logger.debug(tXML);
				this.mResult.add(tXML);
			} else {
				this.mErrors.addOneError("RSS文件读取错误");
				return false;
			}
		} catch (Exception e) {
			this.mErrors.addOneError("RSS文件读取错误");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 返回rss订阅链接后缀
	 * 
	 * @return
	 */
	private boolean addressRSS() {
		String tAddress = "";
		try {
			String tUserID = (String) this.mInputData.get(0);
			String tPWD = "";
			String tSQL = "select password from LDUser where usercode='"
					+ "?tUserID?" + "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSQL);
			sqlbv1.put("tUserID", tUserID);
			ExeSQL aExeSQL = new ExeSQL();
			SSRS aSSRS = aExeSQL.execSQL(sqlbv1);
			tPWD = aSSRS.GetText(1, 1);
			tAddress = tPWD + "," + tUserID;// 密码加用户名
			tAddress = new EncryptPassword().encrypt(tAddress); // 调用加密
			this.mResult.add(tAddress);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 获取今天已运行（正运行）的任务计划服务信息和运行信息，保存到mInputData
	 * 
	 * @param cInputData
	 * @return
	 */
	private boolean getInputData(VData cInputData, String tOperater) {
		try {
			if ("GenerateRSS".equalsIgnoreCase(tOperater)) {
				// sql查询了当天运行的任务计划的全部详细运行信息。
				String strSQL = " select t.TaskPlanCode, t.TaskCode, b.TaskDescribe, t.RunFlag, t.StartTime, t.EndTime,"
						+ " t.RunState,'T',a.taskcode,a.executedate,a.executetime,a.finishdate, a.finishtime, a.executestate,"
						+ " a.executeresult,a.serverinfo from LDTaskPlan t, LDTask b , ldtaskrunlog a where"
						+ " t.TaskCode = b.TaskCode and t.TaskPlanCode=a.TaskPlanCode and"
						+ " a.executedate=to_char(now(),'yyyy-mm-dd')"
						+ " union "
						+ " select t.TaskPlanCode, t.TaskCode, b.TaskDescribe, t.RunFlag, t.StartTime, t.EndTime,"
						+ " t.RunState,' G', a.taskcode, a.executedate, a.executetime, a.finishdate, a.finishtime,"
						+ " a.executestate, a.executeresult, a.serverinfo from LDTaskPlan t, LDTaskGroup b,"
						+ " ldtaskrunlog a where t.TaskCode = b.TaskGroupCode and t.TaskPlanCode=a.TaskPlanCode and "
						+ " a.executedate=to_char(now(),'yyyy-mm-dd')";
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(strSQL);
				ExeSQL aExeSQL = new ExeSQL();
				SSRS aSSRS = aExeSQL.execSQL(sqlbv2);
				int tRowNum = aSSRS.getMaxRow();
				int j = tRowNum;
				if (tRowNum > this.mMaxNum)
					j = this.mMaxNum;
				for (int i = tRowNum; i > tRowNum - j; i--) {
					String[] t = aSSRS.getRowData(i);
					String[] tState = new String[12];
					tState[0] = "任务计划编码：" + t[0];
					tState[1] = "任务/任务队列编码：" + t[1];
					tState[2] = "任务描述：" + t[2];
					tState[3] = "是否启用：" + t[3];
					tState[4] = "起始时间：" + t[4] + "， 终止时间：" + t[5];
					tState[5] = "运行状态：" + t[6] + "， 任务T/队列G：" + t[7]
							+ "， 任务进度：";
					tState[6] = "最后执行任务编码：" + t[8];
					tState[7] = "执行时间：" + t[9] + " " + t[10] + "， 完成时间："
							+ t[11] + " " + t[12];
					tState[8] = "执行状态：" + t[13];
					tState[9] = "执行结果：" + t[14];
					tState[10] = "服务器信息：" + t[15];
					this.mInputData.add(tState);
				}
			} else if ("AddressRSS".equalsIgnoreCase(tOperater)) {
				this.mInputData = cInputData;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 校检传入的操作是否正确，解密RSSID后校检用户密码是否正确
	 * 
	 * @param tVData
	 * @param tOperater
	 *            generateRss orderRss addressRss
	 * @return
	 */
	private boolean check(VData tVData, String tOperater) {
		if ("GenerateRSS".equalsIgnoreCase(tOperater)
				|| "AddressRSS".equalsIgnoreCase(tOperater)) {
			return true;
		} else if ("OrderRSS".equalsIgnoreCase(tOperater)) {
			// 订阅信息,校检用户密码
			String tInputID = (String) tVData.get(0);
			tInputID = new EncryptPassword().decrypt(tInputID);// 解密
			logger.debug(tInputID);
			String[] tRSSID = tInputID.split(",");
			if (tRSSID.length != 2) {
				this.mErrors.addOneError("无效请求");
				return false;
			}
			String tUserID = tRSSID[1];
			String tPWD = tRSSID[0];
			String tSQL = "select password from LDUser where usercode='"
					+ "?tUserID?" + "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tSQL);
			sqlbv3.put("tUserID", tUserID);
			ExeSQL aExeSQL = new ExeSQL();
			SSRS aSSRS = aExeSQL.execSQL(sqlbv3);
			int tRowNum = aSSRS.getMaxRow();
			if (tRowNum == 0) {
				this.mErrors.addOneError("无效请求");
				return false;
			} else {
				if (tPWD.equalsIgnoreCase(aSSRS.GetText(1, 1)))
					return true;
			}
		}
		return false;
	}

	/**
	 * 获取RSS文件设置
	 */
	private void initRSSConfig() {
		String tSQL = "select sysvarvalue from LDSysVar where sysvar='TaskRSSPath'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tSQL);
		ExeSQL aExeSQL = new ExeSQL();
		SSRS aSSRS = aExeSQL.execSQL(sqlbv4);
		if (aSSRS.getMaxRow() != 0) {
			String tPath = aSSRS.GetText(1, 1).trim();
			if (tPath != null && tPath != "")
				this.mXMLFilePath = tPath;// 校检路径是否正确
		}
		tSQL = "select sysvarvalue from LDSysVar where sysvar='TaskRSSName'";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tSQL);
		aSSRS = aExeSQL.execSQL(sqlbv5);
		if (aSSRS.getMaxRow() != 0) {
			String tFileName = aSSRS.GetText(1, 1).trim();
			if (tFileName != null && tFileName != "")
				this.mXMLFileName = tFileName;// 校检文件名是否正确
		}
	}

	public static void main(String args[]) {
		TaskServiceRssBL tTaskServiceRssBL = new TaskServiceRssBL();
		tTaskServiceRssBL.submitData(null, "GenerateRSS");
	}
}
