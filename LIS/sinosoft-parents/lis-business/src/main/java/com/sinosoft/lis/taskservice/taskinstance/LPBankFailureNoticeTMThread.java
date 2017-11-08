package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.db.LOBonusPolDB;
import com.sinosoft.lis.get.BonusAssignBL;
import com.sinosoft.lis.get.BonusAssignManuBL;
import com.sinosoft.lis.operfee.IndiDueFeePartQuery;
import com.sinosoft.lis.operfee.RnDealBL;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LOBonusPolSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.service.ServiceA;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.bq.EdorWorkFlowUI;
/**
 * <p>Title: 理赔转账失败通知书批处理</p>
 * <p>Description: 
 * </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: SinoSoft</p>
 * @author  zhangfh
 * @version 1.0
 */
public class LPBankFailureNoticeTMThread extends TaskThread {
	/** 公共锁定号码类 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();	
	private static Logger logger = Logger.getLogger(ProIndSignMThread.class);
	public CErrors mErrors = new CErrors();
    private GlobalInput tG = new GlobalInput();
    private  int tSuc = 0, tFail = 0;
    private String mCurrentDate = PubFun.getCurrentDate();
	/**红利分配组号*/
	private String tGroupID;
	private String tCurMakeDate = PubFun.getCurrentDate();
	private String 	tCom;
	private int xLength;
	public LPBankFailureNoticeTMThread() {
	}

	public boolean dealMain() {
		
		/* 业务处理逻辑 */
		logger.debug("进入业务逻辑处理!--理赔转账失败通知书批处理" + PubFun.getCurrentDate()
				+ "**" + PubFun.getCurrentTime());
		tG.ComCode = "86";
		tG.Operator = "AUTO";
		tG.ManageCom = "86";

		 //日志监控,初始化           	
		tG.LogID[0]="TASK"+(String)mParameters.get("TaskCode"); 		
		tG.LogID[1]=(String)mParameters.get("SerialNo"); 	
        //日志监控,过程监控
		PubFun.logPerformance (tG,tG.LogID[1],"自动垫交批处理开始","1");

		
		//启动时间        	
    	Date  dNow =  new Date();         	
    	long initTime =dNow.getTime();   
		
		// 每次最大取数量,默认是100条
		int tConutMax = 100;
		String tResult = "";
		String tSQL_Count = "select sysvarvalue from ldsysvar where sysvar='LPBankFailureNoticeTMCount' ";
		  SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tSQL_Count);
		ExeSQL tExeSQL = new ExeSQL();
		tResult = tExeSQL.getOneValue(sqlbv1);
		// 如果有描述的话,取描述的数据
		if (tResult != null && !tResult.equals("")) {
			tConutMax = Integer.parseInt(tResult);
		}
		Vector mTaskWaitList = new Vector();

//		处理的机构
//        tGlobalInput = (GlobalInput)mParameters.get("GlobalInput");

        //分红年度
        String tFiscalYear = (String)mParameters.get("FiscalYear");
        logger.debug("The CalDate is :"+tFiscalYear);
        
		// 红利分配保单号
		String tContNo = (String) mParameters.get("ContNo");
		logger.debug("The ContNo is :" + tContNo);
		
        //如果会计年度录入直接将其返回
        if(tFiscalYear==null || "".equals(tFiscalYear))
        {        	
        	return false;
        }
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("FiscalYear", tFiscalYear);
		tTransferData.setNameAndValue("ContNo", tContNo);
		VData tVData = new VData();
		tVData.addElement(tTransferData);
		tVData.addElement(tG);
        String tComSQL = "";
      
		try {
//			循环处理退费转账失败问题件
			//a.GetMoney,a.bankcode,a.bankaccno,b.accname ljspay.getnoticeno
			//modify by jiaqiangli 2009-11-04 a.managecom
			String tGetSQL = "select a.actugetno,c.rgtno,c.endcasedate,a.managecom,a.agentcode,a.Drawer,a.Sumgetmoney  "
					    + "from LJaget a, LLRegister c where 1 = 1 and a.ManageCom like concat('"
					    // 银行转帐三次失败生成通知书 ljspay.payform是否应该取lpedorapp.PayForm
					    + "?ManageCom?" + "','%') and a.OtherNoType = '5' and c.rgtno = a.OtherNo  and a.sendbankcount=3 and a.paymode='4' "
					    //3次转账失败的问题件
					    + "and EnterAccDate is null and a.ConfDate is null "
					    //+ "and a.actugetno = '86370020090370006586' "
				        + "and (BankOnTheWayFlag = '0' or BankOnTheWayFlag is null) "
				        + "and a.BankAccNo is not null "
				        //防止重复插入loprtmanager
				        //要么是没有打印管理表
				        + "and not exists (select 1 from loprtmanager where code='LP01' and otherno=c.rgtno and standbyflag1=a.actugetno) "
				        + "union "
				        //modify by jiaqiangli 2009-11-04 a.managecom
				        + "select a.actugetno,c.rgtno,c.endcasedate,a.managecom,a.agentcode,a.Drawer,a.Sumgetmoney "
					    + "from LJaget a ,LLRegister c where 1 = 1 and a.ManageCom like concat('"
					    // 银行转帐三次失败生成通知书 ljspay.payform是否应该取lpedorapp.PayForm
					    + "?ManageCom?" + "','%') and a.OtherNoType = '5' and c.rgtno = a.OtherNo  and a.sendbankcount=3 and a.paymode='4'"
					    //3次转账失败的问题件
					    + "and EnterAccDate is null and a.ConfDate is null "
					    //+ "and a.actugetno = '86370020090370006586' "
				        + "and (BankOnTheWayFlag = '0' or BankOnTheWayFlag is null) "
				        + "and a.BankAccNo is not null "
				        //防止重复插入loprtmanager
				        //要么是打印管理表已经回复 表示新一轮转帐失败
				        //强制回收不需要ljaget.sendbankcount清0(否则可能导致以后转账成功) and remark is null表强制回销 3表变更修改
				        + "and exists (select 1 from loprtmanager where code='LP01' and otherno=c.rgtno and standbyflag1=a.actugetno and stateflag='3') "
				        + "and not exists (select 1 from loprtmanager where code='LP01' and otherno=c.rgtno and standbyflag1=a.actugetno and stateflag < '3') "
					    + "order by rgtno asc ";
			
			logger.debug("tGetSQL"+tGetSQL);
			  SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				sqlbv2.sql(tGetSQL);
				sqlbv2.put("ManageCom", tG.ManageCom);
				
			SSRS tGetSSRS = new SSRS();
			tGetSSRS = tExeSQL.execSQL(sqlbv2);
			String tGetDateReasonSQL = "";
			SSRS tGetDateReasonSSRS = new SSRS();
			String LockNo= "";
    		if (tGetSSRS == null ||tGetSSRS.MaxRow <= 0) {
    			return false;
    		}
			xLength=tGetSSRS.MaxRow;
			VData hVData = new VData();
			for (int i = 1; i <= tGetSSRS.MaxRow; i++) {
								
				// 日志监控,过程监控
				String ssrsData[][]=null;
//				PubFun.logTrack (tG,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"红利派发开始");
				ssrsData=tGetSSRS.getAllData();
//				TransferData hTransferData = new TransferData();
				
				VData mResult = new VData();
				Map map = new HashMap();
				map.put("ssrsData", ssrsData);
				map.put("GlobalInput",tG);
				hVData.add(map);
				
				// 业务逻辑处理
				if(i%tConutMax==0)
				{
				    mTaskWaitList.add(hVData);
				    hVData = new VData();
				}
								
				if(i%tConutMax!=0&&i==xLength)
				{
					mTaskWaitList.add(hVData);
					hVData = new VData();
				} 
			  }
			
			} catch (Exception ex) {
				ex.printStackTrace();
				CError.buildErr(this, ex.getMessage());
			} finally {

				mPubLock.unLock();
			}
		  
				
			String TaskCode = (String) this.mParameters.get("TaskGroupCode") + ":"
					+ (String) this.mParameters.get("TaskCode") + ":"
					+ (String) this.mParameters.get("TaskPlanCode");
			// tongmeng 2009-05-21 add
			// 自适应线程数
			int tThreadCount = 5;
			tExeSQL = new ExeSQL();
			String tSQL_ThreadCount = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='LPBankFailureNoticeTMThread'";
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(tSQL_ThreadCount);
			String tSignMThreadCount = tExeSQL.getOneValue(sqlbv2);
			if (tSignMThreadCount != null && !"".equals(tSignMThreadCount)
					&& Integer.parseInt(tSignMThreadCount) > 0) {
				tThreadCount = Integer.parseInt(tSignMThreadCount);
			}
			if (xLength < tThreadCount) {
				tThreadCount =xLength;
			}
			
			this.mServiceA = new ServiceA(
					"com.sinosoft.lis.get.LLClaimBankTrancferFailureNoticeTMThread", mTaskWaitList,
					tThreadCount, 10, TaskCode);
			this.mServiceA.start();

			 //日志监控,过程监控	
			PubFun.logTrack(tG, tG.LogID[1], "自动垫交批处理完毕");
	        //完成时间
	    	Date  dend = new Date();         	
	    	long  endTime = dend.getTime(); 
	    	long tt=(endTime-initTime)/1000; 
	    	//日志监控,结果监控 
	    	
	    	PubFun.logResult(tG,tG.LogID[1], "理赔转账失败通知书批处理"+String.valueOf(xLength)+"笔,共花费"+tt+"秒");
			return true;
	}

   
    //==========================================================================

	public static void main(String[] args) {
		LPBankFailureNoticeTMThread tLPBankFailureNoticeTMThread = new LPBankFailureNoticeTMThread();
		tLPBankFailureNoticeTMThread.dealMain();
	}
}
