/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.taskservice;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDTaskDB;
import com.sinosoft.lis.db.LDTaskMsgItemDB;
import com.sinosoft.lis.db.LDTaskParamDB;
import com.sinosoft.lis.db.LDTaskPlanDB;
import com.sinosoft.lis.db.LDTaskRunLogDB;
import com.sinosoft.lis.pubfun.LogProcessor;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDTaskMsgItemSchema;
import com.sinosoft.lis.vschema.LDTaskMsgItemSet;
import com.sinosoft.lis.vschema.LDTaskParamSet;
import com.sinosoft.lis.vschema.LDTaskPlanSet;
import com.sinosoft.lis.vschema.LDTaskSet;
//import com.sinosoft.plugin.mail.SendMail;
import com.sinosoft.service.ServiceA;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author ck
 * @version 1.0
 */
public abstract class TaskThread implements Runnable {
private static Logger logger = Logger.getLogger(TaskThread.class);
	protected HashMap mParameters = new HashMap();

	public CErrors mErrors = new CErrors();

	private String ServerInfo = "";

	protected String mContent = "";

	// 任务执行结果
	private String mExecuteResult = "";

	// 任务执行状态
	private String mExecuteState = "";

	/** 公共锁定号码类 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();

	// 任务执行序列号
	private String mSerialNo = "";

	// 任务计划编码
	private String mTaskPlanCode = "";

	// 任务编码
	private String mTaskCode = "";

	// 任务队列编码
	private String mTaskGroupCode = "";

	// 多线程ServiceA
	protected ServiceA mServiceA = null;

	// 进程是否结束标记
	private boolean isAlive = true;

	public TaskThread() {
		LDTaskParamDB tLDTaskParamDB = new LDTaskParamDB();
		tLDTaskParamDB.setTaskCode("000000");
		tLDTaskParamDB.setTaskPlanCode("000000");
		tLDTaskParamDB.setParamName("ServerType");
		if (tLDTaskParamDB.getInfo()) {
			/*
			if (tLDTaskParamDB.getParamValue() != null
					&& tLDTaskParamDB.getParamValue().toLowerCase().equals(
							"weblogic")) {
				ServerInfo = MultiTaskServer.getServerIP() + ":"
						+ String.valueOf(MultiTaskServer.getServerPort());
			} else if (tLDTaskParamDB.getParamValue() != null
					&& tLDTaskParamDB.getParamValue().toLowerCase().equals(
							"tomcat")) {
				InetAddress ServerIPaddress = null;
				try {
					ServerIPaddress = InetAddress.getLocalHost();
					MultiTaskServer.outPrint("ServerIPaddress.getHostAddress():"
							+ ServerIPaddress.getHostAddress());
				} catch (Exception ex) {
					ex.printStackTrace();
					ServerInfo = "";
				}
				ServerInfo = ServerIPaddress.getHostAddress();
			}*/
			ServerInfo = MultiTaskServer.getServerIP()+":"+MultiTaskServer.getServerPort();
		} else {
			ServerInfo = "";
		}
	}

	public void setParameter(HashMap aParameters) {
		mParameters = aParameters;
	}

	public void run() {
		try {
			// 获取任务编码和任务计划编码
			this.mTaskCode = (String) mParameters.get("TaskCode");
			this.mTaskPlanCode = (String) mParameters.get("TaskPlanCode");
			this.mTaskGroupCode = (String) mParameters.get("TaskGroupCode");

			String SerialNo = PubFun1.CreateMaxNo("SERIALNO", 10);
			// 添加SerialNo作为日志组件的参数 add by 日志组件
			mParameters.put("SerialNo", SerialNo);
			this.mSerialNo = SerialNo;

			MultiTaskServer.outPrint("$$$$$$$$$$$$$$$$$$mSerialNo:" + mSerialNo);

			LDTaskRunLogDB tLDTaskRunLogDB = new LDTaskRunLogDB();
			tLDTaskRunLogDB.setSerialNo(SerialNo);
			tLDTaskRunLogDB.setTaskCode(this.mTaskCode);
			tLDTaskRunLogDB.setTaskPlanCode(this.mTaskPlanCode);
			tLDTaskRunLogDB.setTaskGroupCode(this.mTaskGroupCode);

			tLDTaskRunLogDB.setServerInfo(ServerInfo);
			if (BeforeRun()) {
				tLDTaskRunLogDB.setExecuteDate(PubFun.getCurrentDate());
				tLDTaskRunLogDB.setExecuteTime(PubFun.getCurrentTime());
				this.mExecuteState = "1";
				tLDTaskRunLogDB.setExecuteState(mExecuteState);
				this.mExecuteResult = "正在业务逻辑处理";
				tLDTaskRunLogDB.setExecuteResult(mExecuteResult);
				tLDTaskRunLogDB.insert();

				if (dealMain()) {
					if (tLDTaskRunLogDB.getInfo()) {
						tLDTaskRunLogDB.setFinishDate(PubFun.getCurrentDate());
						tLDTaskRunLogDB.setFinishTime(PubFun.getCurrentTime());
						this.mExecuteState = "2";
						tLDTaskRunLogDB.setExecuteState(mExecuteState);
						this.mExecuteResult = "业务逻辑处理成功结束";
						tLDTaskRunLogDB.setExecuteResult(mExecuteResult);
						tLDTaskRunLogDB.update();
					}
				} else {
					if (tLDTaskRunLogDB.getInfo()) {
						tLDTaskRunLogDB.setFinishDate(PubFun.getCurrentDate());
						tLDTaskRunLogDB.setFinishTime(PubFun.getCurrentTime());
						this.mExecuteState = "3";
						tLDTaskRunLogDB.setExecuteState(mExecuteState);
						this.mExecuteResult = "业务逻辑处理失败";
						tLDTaskRunLogDB.setExecuteResult(mExecuteResult);
						// tLDTaskRunLogDB.update();
						MMap tMap = new MMap();
						VData tSubmitData = new VData();
						tMap.put(tLDTaskRunLogDB.getSchema(), "UPDATE");
						// 2011-10-17 add
						// 任务错误,如果是队列批处理的话,需要判断是否挂起任务
						String tTaskGroupCode = this.mTaskGroupCode;
						String tTaskCode = this.mTaskCode;
						if (tTaskGroupCode != null
								&& !tTaskGroupCode.equals("AAAAAA")) {
							// 队列批处理才判断是否挂起任务
							String tSQL = "select actionafterfail from ldtaskgroupdetail where taskgroupcode='"
									+ "?tTaskGroupCode?"
									+ "' "
									+ " and taskcode='"
									+ "?tTaskCode?" + "'";
							SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
							sqlbv1.sql(tSQL);
							sqlbv1.put("tTaskGroupCode", tTaskGroupCode);
							sqlbv1.put("tTaskCode", tTaskCode);
							ExeSQL tExeSQL = new ExeSQL();
							String tActionAfterFail = "";
							tActionAfterFail = tExeSQL.getOneValue(sqlbv1);
							if (tActionAfterFail != null
									&& tActionAfterFail.equals("01")) {
								LDTaskPlanDB tLDTaskPlanDB = new LDTaskPlanDB();
								tLDTaskPlanDB
										.setTaskPlanCode(this.mTaskPlanCode);
								if (tLDTaskPlanDB.getInfo()) {
									tLDTaskPlanDB.setRunState("6");
									tLDTaskPlanDB.setModifyDate(PubFun
											.getCurrentDate());
									tLDTaskPlanDB.setModifyTime(PubFun
											.getCurrentTime());
									tMap.put(tLDTaskPlanDB.getSchema(),
											"UPDATE");
								}
							}
						}
						// 判断该计划出错后是否需要挂起
						LDTaskPlanDB tLDTaskPlanDB = new LDTaskPlanDB();
						tLDTaskPlanDB.setTaskPlanCode(this.mTaskPlanCode);
						if (tLDTaskPlanDB.getInfo()) {
							if (tLDTaskPlanDB.getActionAfterFail() != null
									&& tLDTaskPlanDB.getActionAfterFail()
											.equals("01")) {
								tLDTaskPlanDB.setRunState("6");
								tLDTaskPlanDB.setModifyDate(PubFun
										.getCurrentDate());
								tLDTaskPlanDB.setModifyTime(PubFun
										.getCurrentTime());
								tMap.put(tLDTaskPlanDB.getSchema(), "UPDATE");
							}
						}
						tSubmitData.add(tMap);
						PubSubmit tPubSubmit = new PubSubmit();
						tPubSubmit.submitData(tSubmitData, "");

					}
				}
				AfterRun();
			} else {
				tLDTaskRunLogDB.setExecuteDate(PubFun.getCurrentDate());
				tLDTaskRunLogDB.setExecuteTime(PubFun.getCurrentTime());
				this.mExecuteState = "0";
				tLDTaskRunLogDB.setExecuteState(mExecuteState);
				tLDTaskRunLogDB.setExecuteResult(mExecuteResult);
				tLDTaskRunLogDB.insert();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 处理邮件信息
			dealMail();
			this.mPubLock.unLock();
			this.isAlive = false;//进程结束标记，放在run的最后，希望以后能改进，使用更安全的方法判断
		}
	}

	public boolean BeforeRun() {
		// 先通过并发 锁判
		String tKeyNo = this.mTaskCode;
		if (!mPubLock.lock(tKeyNo, "LD0002")) {
			mExecuteResult = "任务:" + mTaskCode + "上次业务逻辑尚未结束，本次不执行！";
			return false;
		} else {

			// 判断
			LDTaskPlanSet tLDTaskPlanSet = new LDTaskPlanSet();
			LDTaskPlanDB tLDTaskPlanDB = new LDTaskPlanDB();
			String strsql2 = "select * from LDTaskPlan where TaskCode='"
					+ "?mTaskCode?" + "'" + " union "
					+ " select * from LDTaskPlan where TaskCode='"
					+ "?mTaskGroupCode?" + "'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(strsql2);
			sqlbv2.put("mTaskCode",mTaskCode );
			sqlbv2.put("mTaskGroupCode", this.mTaskGroupCode);
			
			tLDTaskPlanSet = tLDTaskPlanDB.executeQuery(sqlbv2);
			String runflag = "1";

			if (tLDTaskPlanSet.size() == 0) {
				MultiTaskServer.outPrint("任务代码已经被删除!");
			} else {
				for (int i = 1; i <= tLDTaskPlanSet.size(); i++) {
					// 如果为运行状态，则不运行业务逻辑
					if (tLDTaskPlanSet.get(i).getRunState().equals("1")) {
						runflag = "0";

						break;
					}
				}
				// 一个任务只能同时进行一次操作
				if (runflag.equals("1")) {

					tLDTaskPlanSet.clear();

					LDTaskPlanDB mLDTaskPlanDB = new LDTaskPlanDB();
					mLDTaskPlanDB.setTaskPlanCode(this.mTaskPlanCode);
					if (mLDTaskPlanDB.getInfo()) {
						if (mLDTaskPlanDB.getRunState().equals("6")) {
							// 任务被挂起,本次不执行
							MultiTaskServer.outPrint("任务:" + mTaskPlanCode
									+ "被挂起,本次不执行");

							mExecuteResult = "任务:" + mTaskPlanCode
									+ "被挂起,本次不执行";
							return false;
						}
						mLDTaskPlanDB.setRunState("1");
						mLDTaskPlanDB.update();
					}

				} else {
					MultiTaskServer.outPrint("上次业务逻辑尚未结束，本次不执行！");
					mExecuteResult = "任务:" + mTaskPlanCode
							+ "上次业务逻辑尚未结束，本次不执行！";
					return false;
				}
			}
		}
		return true;
	}

	public abstract boolean dealMain();
	public boolean dealMain(String Batch) {
		return false;
	}
	public boolean AfterRun() {
		LDTaskPlanDB tLDTaskPlanDB = new LDTaskPlanDB();

		LDTaskParamSet tLDTaskParamSet = new LDTaskParamSet();
		tLDTaskPlanDB.setTaskPlanCode(this.mTaskPlanCode);
		if (tLDTaskPlanDB.getInfo()) {
			if (!tLDTaskPlanDB.getRunState().equals("6")) {
				tLDTaskPlanDB.setRunState("0");
			}
			tLDTaskPlanDB.update();
		}
		//更新任务转移记录
		TransTaskPlan.completeTaskPlan(this.mTaskPlanCode, "T");
		// 日志处理
		// 每次任务执行完毕都直接将日志实时写到数据库.
		LogProcessor.dealQueue();
		// 处理邮件信息
		// dealMail();

		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  AutoTask Execute !!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
		return true;
	}

	private void dealMail() {
		LDTaskParamDB tLDTaskParamDB = new LDTaskParamDB();
		ExeSQL tExeSQL = new ExeSQL();
		String tTaskPlanCode = this.mTaskPlanCode;

		String tSQL_TaskDescrib = "select taskdescribe from ldtask where taskcode= "
				+ " (select taskcode from ldtaskplan where taskplancode='"
				+ "?tTaskPlanCode?"
				+ "' )"
				+ " union "
				+ " select taskdescribe from ldtaskgroup where taskgroupcode= "
				+ " (select taskcode from ldtaskplan where taskplancode='"
				+ "?tTaskPlanCode?" + "' )";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSQL_TaskDescrib);
		sqlbv3.put("tTaskPlanCode", tTaskPlanCode);
		LDTaskParamSet tLDTaskParamSet;
		// 判断是否发送邮件！
		tLDTaskParamDB.setTaskPlanCode(tTaskPlanCode);
		tLDTaskParamSet = tLDTaskParamDB.query();
		LDTaskDB tLDTaskDB = new LDTaskDB();
		LDTaskSet tLDTaskSet = new LDTaskSet();
		tLDTaskDB.setTaskCode(this.mTaskCode);
		tLDTaskSet = tLDTaskDB.query();

		// 任务/队列描述
		String TaskDescribe = tExeSQL.getOneValue(sqlbv3);
		// 当前任务描述

		String tCurrentTaskDescribe = "";
		if (tLDTaskSet.size() > 0) {
			tCurrentTaskDescribe = tLDTaskSet.get(1).getTaskDescribe();
		}

		LDTaskRunLogDB tLDTaskRunLogDB = new LDTaskRunLogDB();
		tLDTaskRunLogDB.setSerialNo(this.mSerialNo);
		tLDTaskRunLogDB.getInfo();

		String tSQL_Params = "select paramvalue from ldtaskparam where taskplancode='"
				+ "?tTaskPlanCode?" + "' and paramname='mailto' ";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tSQL_Params);
		sqlbv4.put("tTaskPlanCode", tTaskPlanCode);
		String tMailAddress = tExeSQL.getOneValue(sqlbv4);
		if (tMailAddress != null && !tMailAddress.equals("")) {
			// 邮件标题
			String tMsg_Title = "自动运行程序提醒邮件";
			// 邮件头

			String tFinishDate = tLDTaskRunLogDB.getFinishDate() == null ? ""
					: tLDTaskRunLogDB.getFinishDate();
			String tFinishTime = tLDTaskRunLogDB.getFinishTime() == null ? ""
					: tLDTaskRunLogDB.getFinishTime();
			String tMsg_Head = "您好：\n" + "     自动运行程序在运行 计划:" + tTaskPlanCode
					+ "(" + TaskDescribe + ")" + " 时给您发送的邮件。\n" + "    "
					+ " 该计划中的 任务:" + this.mTaskCode + "("
					+ tCurrentTaskDescribe
					+ ")已经执行完毕！ 具体信息如下：\n"
					+ "     运行计划编码："
					+ tTaskPlanCode
					+ "；\n"
					// + " 运行功能：" + TaskDescribe + "；\n"
					+ "     启动日期：" + tLDTaskRunLogDB.getExecuteDate() + "；"
					+ "    启动时间：" + tLDTaskRunLogDB.getExecuteTime() + "；\n"
					+ "     完成日期：" + tFinishDate + "；" + "    完成时间："
					+ tFinishTime + "；\n" + "     运行结果: " + this.mExecuteResult
					+ "\n\n\n";

			String tMsg_Final = "     \n\n\n如有问题，请联系系统维护人员！\n\n";
			// 邮件基本提示信息
			String tMsg_Basic = "";
			// 邮件额外提示信息
			String tMsg_Other = " 附加提示信息: \n ";

			//
			String tSQL_LogItem = "select itemid,itemdes from  logitem";
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(tSQL_LogItem);
			SSRS tSSRS_Item = new SSRS();
			tSSRS_Item = tExeSQL.execSQL(sqlbv5);
			Hashtable tItemHashtable = new Hashtable();
			for (int i = 1; i <= tSSRS_Item.getMaxRow(); i++) {
				tItemHashtable.put(tSSRS_Item.GetText(i, 1), tSSRS_Item
						.GetText(i, 2));
			}

			String tSQL_Other = "select * from ldtaskmsgitem where taskplancode='"
					+ "?taskplancode?"
					+ "' "
					+ " and subjectid=concat('TASK','"
					+ "?mTaskCode?" + "') and msgType='01' order by itemid";
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(tSQL_Other);
			sqlbv6.put("taskplancode", this.mTaskPlanCode);
			sqlbv6.put("mTaskCode", this.mTaskCode);
			LDTaskMsgItemSet tLDTaskMsgItemSet = new LDTaskMsgItemSet();
			LDTaskMsgItemDB tLDTaskMsgItemDB = new LDTaskMsgItemDB();
			tLDTaskMsgItemSet = tLDTaskMsgItemDB.executeQuery(sqlbv6);
			for (int i = 1; i <= tLDTaskMsgItemSet.size(); i++) {
				LDTaskMsgItemSchema tempLDTaskMsgItemSchema = new LDTaskMsgItemSchema();
				tempLDTaskMsgItemSchema.setSchema(tLDTaskMsgItemSet.get(i));
				String tItemName = (String) tItemHashtable
						.get(tempLDTaskMsgItemSchema.getItemID());
				String tMsg_Current = "\n日志控制点:" + tItemName + "\n ";

				String tMsg_Log = "select A.x,A.y,A.z,A.m,A.n,A.o from ("
						+ " select keyno x,itemdes y,makedate z,maketime m,to_char(modifydate,'yyyy-mm-dd') n,modifytime o "
						+ " from logbusinessstate where  serialno='"
						+ "?serialno?"
						+ "' "
						+ " and subjectid='"
						+ "?subjectid?"
						+ "' "
						+ " and itemid='"
						+ "?itemid?"
						+ "' "
						+ " union "
						+ " select keyno x,itemdes y,makedate z,maketime m,'' n,'' o "
						+ " from logtrackresult where serialno='"
						+ "?serialno?" + "' "
						+ " and subjectid='"
						+ "?subjectid?" + "' "
						+ " and itemid='" +"?itemid?"
						+ "' " + " ) A order by  A.x,A.y,A.z,A.m,A.n,A.o ";
				SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
				sqlbv7.sql(tMsg_Log);
				sqlbv7.put("serialno", Integer.parseInt(mSerialNo));
				sqlbv7.put("subjectid", tempLDTaskMsgItemSchema.getSubjectID());
				sqlbv7.put("itemid", tempLDTaskMsgItemSchema.getItemID());
				SSRS tempSSRS = new SSRS();
				tempSSRS = tExeSQL.execSQL(sqlbv7);
				for (int m = 1; m <= tempSSRS.getMaxRow(); m++) {
					String tKeyNo = tempSSRS.GetText(m, 1);
					String tItemDes = tempSSRS.GetText(m, 2);
					String tMakeDate = tempSSRS.GetText(m, 3);
					String tMakeTime = tempSSRS.GetText(m, 4);
					String tModifyDate = tempSSRS.GetText(m, 5);
					String tModifyTime = tempSSRS.GetText(m, 6);

					tMsg_Current = tMsg_Current + " 关键号码:" + tKeyNo + " 监控信息:"
							+ tItemDes + " 日志产生日期:" + tMakeDate + " 日志产生时间:"
							+ tMakeTime;
					if (tModifyDate != null && !tModifyDate.equals("")) {
						tMsg_Current = tMsg_Current + " 日志修改日期:" + tModifyDate
								+ " 日志修改时间:" + tModifyTime;
					}

					// 换行
					tMsg_Current = tMsg_Current + " \n\n";
				}

				tMsg_Other = tMsg_Other + tMsg_Current;
			}

			// 截图邮件地址
			// 发送邮件
			String mail[] = tMailAddress.split(";");
			MultiTaskServer.outPrint("有" + mail.length + "个邮箱地址");
			for (int j = 0; j < mail.length; j++) {
				MultiTaskServer.outPrint("第" + (j + 1) + "个邮箱地址是:" + mail[j]);
				try {

					// 在这边直接获取配置文件中的信息
					/*
					 * -attach <attach> 附件 -cc <cc> 抄送者([zzm:]zzm@lis.com)
					 * -charset <charset> 文件编码(GBK) -debug 调试显示信息 -file <file>
					 * 包含内容的文件 -from <from> 发送者([zzm:]zzm@lis.com) -help 打印帮助
					 * -host <host> 服务器 -msg <msg> 内容 -pass <pass> 密码 -port
					 * <port> 端口 -subject <subject> 主题 -to <to>
					 * 接受者([zzm:]zzm@lis.com) -user <user> 用户名
					 */
					String tUser = ResourceBundle.getBundle("email").getString(
							"user");
					String tFrom = ResourceBundle.getBundle("email").getString(
							"from");
					String tHost = ResourceBundle.getBundle("email").getString(
							"host");
					String tPass = ResourceBundle.getBundle("email").getString(
							"pass");

//					SendMail tSendMail = new SendMail();
//					tSendMail.deal(new String[] { "-to", mail[j], "-msg",
//							tMsg_Head + tMsg_Other + tMsg_Final, "-subject",
//							tMsg_Title, "-from", tFrom, "-host", tHost,
//							"-user", tUser, "-pass", tPass });

				} catch (Exception ex) {
					MultiTaskServer.outPrint("SendMail Erorr");
				}
				// SendMail tSendMail =new SendMail();
			}
		}

	}

	/**
	 * 获得从执行结果，返回页面
	 */
	public String getContent() {
		return mContent;
	}

	/**
	 * 停止正在执行的多线程任务
	 */
	public void stopTask() {
		if (this.mServiceA != null)
			this.mServiceA.stopTask();
	}

	/**
	 * 返回进程是否还在运行，add by HuangLiang 2011-12-02
	 * 
	 * @return true，进程还在运行
	 */
	public boolean isAlive() {
		return this.isAlive;
	}
}
