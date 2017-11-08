package com.sinosoft.lis.bq;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.service.CovBase;
import com.sinosoft.utility.CErrors;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.tb.TbWorkFlowBL;
import com.sinosoft.workflow.tb.TbWorkFlowBLS;

/**
 * <p>
 * Title:个单保全转账失败通知书批处理
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author zhangfh
 * @version 6.5
 */

public class BQBankTransferFailureNoticeTMThread extends CovBase {
	private static Logger logger = Logger.getLogger(BQBankTransferFailureNoticeTMThread.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private Vector mInputDataNew;
	/** 数据操作字符串 */
	private String mOperate;
	private GlobalInput tG = new GlobalInput();
	int snum=0;
	int fnum=0;
	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();
	private HttpServletRequest httprequest;
	private MMap map = new MMap();
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
    private  int tSuc = 0, tFail = 0;
	public BQBankTransferFailureNoticeTMThread() {
	}

	public static void main(String[] args) {

		
	}
	public void setObject(Object tObject) {
		logger.debug(tObject);
		//多线程的外部参数条件
		mInputDataNew = (Vector) tObject;
	}
	
	public void run() {
		
		VData mResult = new VData(); 
		MMap tMMap = new MMap();
		for(int i=0;i<this.mInputDataNew.size();i++)
		{
			
			Map tMap = new HashMap();
			tMap = (Map)mInputDataNew.get(i);
			VData mInputData = new VData();
			mInputData.add(tMap.get("tLPEdorAppSchema"));
			mInputData.add(tMap.get("TransferData"));
			mInputData.add(tMap.get("GlobalInput"));
			
			
			int ErrCount = 0;
	
			String ssrsData[][]=(String[][])tMap.get("ssrsData");
			tG=(GlobalInput)tMap.get("GlobalInput");
			if (!insertLoprt(ssrsData,tG)) {

				continue;
			}  
	    	
			//put set into map
			map.put(mLOPRTManagerSet, "DELETE&INSERT");
			mResult.clear();
			mResult.add(map);
			
			// 数据提交
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(mResult, "")) {
				CError.buildErr(this, "保全逾期终止数据提交失败");
//				return false;
			}

		}
		logger.debug("结束业务逻辑处理!--收费转账失败通知书批处理" + PubFun.getCurrentDate() + "**"
				+ PubFun.getCurrentTime());
		 //日志监控,过程监控	
		PubFun.logTrack(tG, tG.LogID[1], "收费转账失败通知书批处理完毕");
		PubFun.logResult (tG,tG.LogID[1],"共生成" + snum + "件收费转账失败通知书");
		this.close();
	}

	public boolean insertLoprt(String [][]ssrsData,GlobalInput tGI)
	{

		LOPRTManagerSchema tLOPRTManagerSchema = null;
		try {
			
			LCContDB tLCContDB = new LCContDB();
	        tLCContDB.setContNo(ssrsData[0][1]);
	        if (!tLCContDB.getInfo()) {
	        	CError.buildErr(this, "保单查询失败");
				return false;
	        }

			tLOPRTManagerSchema = new LOPRTManagerSchema();
			String strNoLimit = PubFun.getNoLimit(tGI.ManageCom);
			String sPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // 生成打印流水号
			tLOPRTManagerSchema.setPrtSeq(sPrtSeq);
			
			tLOPRTManagerSchema.setOtherNo(ssrsData[0][1]);//受理号 contno
			tLOPRTManagerSchema.setOtherNoType("02");
			
			//转账失败通知书
			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_BQBankTransferFailure);
			
			tLOPRTManagerSchema.setManageCom(tLCContDB.getManageCom());
			tLOPRTManagerSchema.setAgentCode(tLCContDB.getAgentCode());
			
			tLOPRTManagerSchema.setReqCom(tGI.ComCode);
			tLOPRTManagerSchema.setReqOperator(tGI.Operator);
			
			tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
			tLOPRTManagerSchema.setStateFlag("0"); // 打印状态
			tLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
			
			tLOPRTManagerSchema.setMakeDate(mCurrentDate);
			tLOPRTManagerSchema.setMakeTime(mCurrentTime);
			tLOPRTManagerSchema.setOldPrtSeq(sPrtSeq);
			
			//其他重要字段
			tLOPRTManagerSchema.setStandbyFlag1(ssrsData[0][0]);//contno 受理号
			tLOPRTManagerSchema.setStandbyFlag2(ssrsData[0][2]);//edortype
			tLOPRTManagerSchema.setStandbyFlag3(ssrsData[0][4]);//GetNoticeNo
			tLOPRTManagerSchema.setStandbyFlag4(ssrsData[0][3]);//edorappdate
			tLOPRTManagerSchema.setStandbyFlag5("1");//1表收费转账失败
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			//转账时间及转账失败原因 最后一条记录
			//lyreturnfrombankb.paycode=ljaget.GetNoticeNo
			String tPayDateReasonSQL ="";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			tPayDateReasonSQL = "select bankdealdate,failreason from (select bankdealdate,(select codename from ldcode1 where codetype='bankerror' and code=a.bankcode and code1=banksuccflag) failreason from lyreturnfrombankb a "
					+ "where paycode='" + "?paycode?" + "' order by serialno desc) b where rownum<=1 ";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			tPayDateReasonSQL = "select bankdealdate,failreason from (select bankdealdate,(select codename from ldcode1 where codetype='bankerror' and code=a.bankcode and code1=banksuccflag) failreason from lyreturnfrombankb a "
						+ "where paycode='" + "?paycode?" + "' order by serialno desc) b limit 0,1 ";	
			}
			sqlbv.sql(tPayDateReasonSQL);
			sqlbv.put("paycode", ssrsData[0][4]);
			ExeSQL tExeSQL =new ExeSQL();
			SSRS tPayDateReasonSSRS = new SSRS();
			tPayDateReasonSSRS = tExeSQL.execSQL(sqlbv);
			if (tPayDateReasonSSRS.getMaxRow() <= 0) {
				CError.buildErr(this, "查询转账时间及转账失败原因出错!");
//				continue;
			}
			tLOPRTManagerSchema.setStandbyFlag6(tPayDateReasonSSRS.GetText(1, 1));
			tLOPRTManagerSchema.setStandbyFlag7(tPayDateReasonSSRS.GetText(1, 2));	

			mLOPRTManagerSet.add(tLOPRTManagerSchema);
			snum++;
//			 日志监控,状态监控                 		
  			PubFun.logState(tGI,ssrsData[0][4], "生成收费转账失败通知书（通知书号： "+ssrsData[0][4]+"）","0");  
		}
		catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, "插入保全拒绝通知书失败!");
			return false;
		}
		return true;
	}
	/**
	 * 传输数据的公共方法
	 */
	public VData getResult() {
		return mResult;
	}

}
