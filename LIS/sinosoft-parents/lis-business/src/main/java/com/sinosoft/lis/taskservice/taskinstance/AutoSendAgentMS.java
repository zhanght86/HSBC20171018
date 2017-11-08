package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.cbcheck.MSManagerBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 业务系统
 * </p>
 * <p>
 * Description: 核保十日后保费仍未到帐发短信通知业务员－后台自动处理入口
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author hanbin
 * @version 1.0
 */
public class AutoSendAgentMS  extends TaskThread {
private static Logger logger = Logger.getLogger(AutoSendAgentMS.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors ;
	private VData mResult = new VData();

	public AutoSendAgentMS() {

	}

	public boolean dealMain() {
		/* 业务处理逻辑 */
		logger.debug("...AutoSendAgentMS Start..."
				+ PubFun.getCurrentDate() + " " + PubFun.getCurrentTime());
		try {
			String Content;
			// 准备传输数据 VData
			GlobalInput tG = new GlobalInput();
			tG.Operator = "AUTO";
			tG.ComCode = "86";
			tG.ManageCom = "86";
			
			
			String tPrtNo = "";
			String tMSType = "01";
			String tMSContent = "核保10天后保费仍未到帐";
			String tDay = "";

			String strSql = " select sysvarvalue from ldsysvar where 1=1 and sysvar='AutoSendAgentMSDate'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(strSql);			
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv1);
			if (tSSRS.getMaxRow() > 0) {
				tDay = tSSRS.GetText(1, 1);
			} else {
				Content = "系统没有定义自动发业务员短信时间间隔！";
				logger.debug(Content);
				return false;
			}
//			strSql = " select b.contno from lwmission a, lccont b where 1=1 and activityid='0000001150'"
					strSql = " select b.contno from lwmission a, lccont b where 1=1 and activityid in (select activityid from lwactivity  where functionid ='10010042')"
					+ " and a.missionprop2 = b.prtno and b.uwflag in ('9','4') and b.grpcontno = '00000000000000000000'  "
					+ " and exists(select 1 from ljtempfee where otherno = b.prtno and tempfeetype = '1' and enteraccdate is null) "
					+ " and " + "?tDay?"
					+ " < datediff(to_date('"+"?PubFun?"+"','yyyy-mm-dd') ,to_date(a.missionprop6,'yyyy-mm-dd')) "
					+ " and not exists(select 1 from lomsmanager where b.prtno = prtno  and mstype = '"+"?tMSType?"+"') "
					+ " and b.salechnl in ('02','10') "   //只有02、10 这两个渠道提取待发送短信数据
					//+ " and not exists(select 1 from lomserrorlog where b.prtno = prtno) "
					;
					SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
					sqlbv2.sql(strSql);	
					sqlbv2.put("tDay", tDay);
					sqlbv2.put("PubFun", PubFun.getCurrentDate());
					sqlbv2.put("tMSType", tMSType);
			tExeSQL = new ExeSQL();
			tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv2);
			if (tSSRS.getMaxRow() > 0) {
				for(int i=1;i<=tSSRS.getMaxRow();i++){
					
					mErrors = new CErrors();
					VData tVData = new VData();
					TransferData tTransferData = new TransferData();
					tTransferData.setNameAndValue("MSType", "01");
					LCContDB tLCContDB = new LCContDB();
					tLCContDB.setContNo(tSSRS.GetText(i, 1));
					if(!tLCContDB.getInfo()){
						logger.debug("保单信息查询失败！");
						continue;
					}
					tVData.add(tLCContDB.getSchema());
					tVData.add(tG);
					tVData.add(tTransferData);
					logger.debug("----------需要发送短信的投保单号："+tSSRS.GetText(i, 1)+"-----------------------");
					MSManagerBL tMSManagerBL = new MSManagerBL();
					if (!tMSManagerBL.submitData(tVData, "")) {
						mErrors.copyAllErrors(tMSManagerBL.getErrors());
					} else {
						mResult.clear();
						mResult = tMSManagerBL.getResult();
						
						PubSubmit tPubSubmit = new PubSubmit();
						// 将数据放入VData中,调用PubSubmit进行数据库的访问
						if (!tPubSubmit.submitData(mResult, "")) {
							mErrors.copyAllErrors(tPubSubmit.mErrors);
							logger.debug("保存签单错误信息失败！");
							return false;
						}
					}
					logger.debug("*********end***************获取发送业务员短信 完毕*****************end*****************");
					int n = this.mErrors.getErrorCount();
					logger.debug("---ErrCount---" + n);
					if (n == 0) {
						Content = "投保单号【" + tSSRS.GetText(i, 1)
								+ "】 自动发送业务员短信成功！";
					} else {
						String strErr = "\\n";
						for (int j = 0; j < n; j++) {
							strErr += "【" +(j + 1)+"】"
									+ ": "
									+ tMSManagerBL.mErrors.getError(j).errorMessage
									+ "; \\n";
						}
						Content = "投保单(印刷号：" + tSSRS.GetText(i, 1)
								+ ")自动发送业务员短信失败，原因是: " + strErr;
					}
					logger.debug(Content);
				}
			} else {
				Content = "没有需要发送业务员短信的投保单！";
				logger.debug(Content);
				return true;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		logger.debug("...AutoSendAgentMS End..."
				+ PubFun.getCurrentDate() + " " + PubFun.getCurrentTime());
		return true;
		/* end 业务处理逻辑 */
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AutoSendAgentMS tAutoSendAgentMS =  new AutoSendAgentMS();
		tAutoSendAgentMS.dealMain();

	}

}
