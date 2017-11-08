package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.VData;

public class BQBankTransferFailureNotice {
private static Logger logger = Logger.getLogger(BQBankTransferFailureNotice.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	
	private MMap map = new MMap();
	/** 用户登陆信息 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();
	
	LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	
	public BQBankTransferFailureNotice() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		
		mInputData = (VData) cInputData.clone();
		
		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}
	    //日志监控,过程监控        
    	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"保全转账失败通知书批处理开始");
		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}
	    //日志监控,过程监控        
    	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"保全转账失败通知书批处理结束");
    	
		//put set into map
		map.put(mLOPRTManagerSet, "DELETE&INSERT");
		mResult.clear();
		mResult.add(map);
		
		// 数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mResult, "")) {
			CError.buildErr(this, "保全逾期终止数据提交失败");
			return false;
		}
		return true;
	}

	private boolean dealData() {
		
		ExeSQL tExeSQL = new ExeSQL();
		int snum=0;
		int fnum=0;
		//循环处理收费转账失败问题件
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		//a.GetMoney,a.bankcode,a.bankaccno,b.accname ljspay.getnoticeno
		String tPaySQL = "select b.EdorAcceptNo,b.ContNo,b.EdorType,b.edorappdate,a.GetNoticeNo "
				    + "from LJSPay a, LPEdorItem b where 1 = 1 and  (BankOnTheWayFlag = '0' or BankOnTheWayFlag is null) and b.ManageCom like concat('"
				    // add by jiaqiangli 2009-05-06 ljspay.payform已另作他用
				    // 银行转帐三次失败生成通知书 ljspay.payform是否应该取lpedorapp.PayForm
		        	+ "?ManageCom?" + "','%') and a.OtherNoType = '10' and b.EdorAcceptNo = a.OtherNo and a.sendbankcount=3 and a.bankcode is not null "
		            + "and not exists (select 1 from loprtmanager where code='BQ99' and otherno=b.contno and standbyflag1=b.edoracceptno) "
				    //个险保全 lpedoritem.grpcontno可判断，但效率低
				    + "and exists (select 1 from lccont where contno=b.contno and conttype='1') "
				    + "union "
				    + "select b.EdorAcceptNo,b.ContNo,b.EdorType,b.edorappdate,a.GetNoticeNo "
				    + "from LJSPay a, LPEdorItem b where 1 = 1 and (BankOnTheWayFlag = '0' or BankOnTheWayFlag is null) and b.ManageCom like concat('"
				    // 银行转帐三次失败生成通知书 ljspay.payform是否应该取lpedorapp.PayForm
				    + "?ManageCom?" + "','%') and a.OtherNoType = '10' and b.EdorAcceptNo = a.OtherNo and a.sendbankcount=3 and a.bankcode is not null "
				    + "and exists (select 1 from lccont where contno=b.contno and conttype='1') "
				    //3表变更修改
				    + "and exists (select 1 from loprtmanager where code='BQ99' and otherno=b.contno and standbyflag1=b.edoracceptno and stateflag='3') "
				    //add by jiaqiangli 2009-08-27 还需要反向判断
				    + "and not exists (select 1 from loprtmanager where code='BQ99' and otherno=b.contno and standbyflag1=b.edoracceptno and stateflag < '3') "
				    //sendbankcount=3后仍为ljspay表转账失败
				    + "order by EdorAcceptNo asc ";
		sqlbv.sql(tPaySQL);
		sqlbv.put("ManageCom", mGlobalInput.ManageCom);
		SSRS tPaySSRS = new SSRS();
		tPaySSRS = tExeSQL.execSQL(sqlbv);
		String tPayDateReasonSQL = "";
		SSRS tPayDateReasonSSRS = new SSRS();
		for (int i=1;i<=tPaySSRS.getMaxRow();i++) {
			LOPRTManagerSchema tLOPRTManagerSchema = null;
			try {
				
				LCContDB tLCContDB = new LCContDB();
		        tLCContDB.setContNo(tPaySSRS.GetText(i, 2));
		        if (!tLCContDB.getInfo()) {
		        	CError.buildErr(this, "保单查询失败");
					return false;
		        }

				tLOPRTManagerSchema = new LOPRTManagerSchema();
				String strNoLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
				String sPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // 生成打印流水号
				tLOPRTManagerSchema.setPrtSeq(sPrtSeq);
				
				tLOPRTManagerSchema.setOtherNo(tPaySSRS.GetText(i, 2));//受理号 contno
				tLOPRTManagerSchema.setOtherNoType("02");
				
				//转账失败通知书
				tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_BQBankTransferFailure);
				
				tLOPRTManagerSchema.setManageCom(tLCContDB.getManageCom());
				tLOPRTManagerSchema.setAgentCode(tLCContDB.getAgentCode());
				
				tLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
				tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
				
				tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
				tLOPRTManagerSchema.setStateFlag("0"); // 打印状态
				tLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
				
				tLOPRTManagerSchema.setMakeDate(mCurrentDate);
				tLOPRTManagerSchema.setMakeTime(mCurrentTime);
				tLOPRTManagerSchema.setOldPrtSeq(sPrtSeq);
				
				//其他重要字段
				tLOPRTManagerSchema.setStandbyFlag1(tPaySSRS.GetText(i, 1));//contno 受理号
				tLOPRTManagerSchema.setStandbyFlag2(tPaySSRS.GetText(i, 3));//edortype
				tLOPRTManagerSchema.setStandbyFlag3(tPaySSRS.GetText(i, 5));//GetNoticeNo
				tLOPRTManagerSchema.setStandbyFlag4(tPaySSRS.GetText(i, 4));//edorappdate
				tLOPRTManagerSchema.setStandbyFlag5("1");//1表收费转账失败
				SQLwithBindVariables sbv=new SQLwithBindVariables();
				//转账时间及转账失败原因 最后一条记录
				//lyreturnfrombankb.paycode=ljaget.GetNoticeNo
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tPayDateReasonSQL = "select bankdealdate,failreason from (select bankdealdate,(select codename from ldcode1 where codetype='bankerror' and code=a.bankcode and code1=banksuccflag) failreason from lyreturnfrombankb a "
						+ "where paycode='" + "?paycode?" + "' order by serialno desc) b where rownum<=1 ";
				}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tPayDateReasonSQL = "select bankdealdate,failreason from (select bankdealdate,(select codename from ldcode1 where codetype='bankerror' and code=a.bankcode and code1=banksuccflag) failreason from lyreturnfrombankb a "
						+ "where paycode='" + "?paycode?" + "' order by serialno desc) b limit 0,1 ";	
				}
				sbv.sql(tPayDateReasonSQL);
				sbv.put("paycode", tPaySSRS.GetText(i, 5));
				tPayDateReasonSSRS = tExeSQL.execSQL(sbv);
				if (tPayDateReasonSSRS.getMaxRow() <= 0) {
					CError.buildErr(this, "查询转账时间及转账失败原因出错!");
					continue;
				}
				tLOPRTManagerSchema.setStandbyFlag6(tPayDateReasonSSRS.GetText(1, 1));
				tLOPRTManagerSchema.setStandbyFlag7(tPayDateReasonSSRS.GetText(1, 2));	

				mLOPRTManagerSet.add(tLOPRTManagerSchema);
				snum++;
//				 日志监控,状态监控                 		
      			PubFun.logState(mGlobalInput,tPaySSRS.GetText(i, 5), "生成收费转账失败通知书（通知书号： "+tPaySSRS.GetText(i, 5)+"）","0");  
			}
			catch (Exception e) {
				e.printStackTrace();
				CError.buildErr(this, "插入保全拒绝通知书失败!");
				return false;
			}
		}
		
//		日志监控,结果监控
		PubFun.logResult (mGlobalInput,mGlobalInput.LogID[1],"共生成" + snum + "件收费转账失败通知书");
		
		//循环处理退费转账失败问题件
		//a.GetMoney,a.bankcode,a.bankaccno,b.accname ljspay.getnoticeno
		SQLwithBindVariables tsqlbv=new SQLwithBindVariables();		
		String tGetSQL = "select b.EdorAcceptNo,b.ContNo,b.EdorType,b.edorappdate,a.actugetNo,a.GetNoticeNo "
				    + "from LJaget a, LPEdorItem b,lpedorapp c where 1 = 1 and b.ManageCom like concat('"
				    // 银行转帐三次失败生成通知书 ljspay.payform是否应该取lpedorapp.PayForm
				    + "?ManageCom?" + "','%') and a.OtherNoType = '10' and c.EDORCONFNO = a.OtherNo and b.EdorAcceptNo = c.EdorAcceptNo and a.sendbankcount=3 and a.paymode='4' "
				    //3次转账失败的问题件
				    + "and EnterAccDate is null and a.ConfDate is null "
			        + "and (BankOnTheWayFlag = '0' or BankOnTheWayFlag is null) "
			        + "and a.BankAccNo is not null "
			        //防止重复插入loprtmanager
			        //要么是没有打印管理表
			        + "and not exists (select 1 from loprtmanager where code='BQ99' and otherno=b.contno and standbyflag1=b.edoracceptno and StandbyFlag3=a.actugetNo) "
			        + "and exists (select 1 from lccont where contno=b.contno and conttype='1') "
			        + "union "
			        + "select b.EdorAcceptNo,b.ContNo,b.EdorType,b.edorappdate,a.actugetNo,a.GetNoticeNo "
				    + "from LJaget a, LPEdorItem b,lpedorapp c where 1 = 1 and b.ManageCom like concat('"
				    // 银行转帐三次失败生成通知书 ljspay.payform是否应该取lpedorapp.PayForm
				    + "?ManageCom?" + "','%') and a.OtherNoType = '10' and c.EDORCONFNO = a.OtherNo and b.EdorAcceptNo = c.EdorAcceptNo and a.sendbankcount=3 and a.paymode='4' "
				    //3次转账失败的问题件
				    + "and EnterAccDate is null and a.ConfDate is null "
			        + "and (BankOnTheWayFlag = '0' or BankOnTheWayFlag is null) "
			        + "and a.BankAccNo is not null "
			        + "and exists (select 1 from lccont where contno=b.contno and conttype='1') "
			        //防止重复插入loprtmanager
			        //要么是打印管理表已经回复 表示新一轮转帐失败
			        //强制回收不需要ljaget.sendbankcount清0(否则可能导致以后转账成功) and remark is null表强制回销 3表变更修改
			        //add by jiaqiangli 2009-08-27 加上实付号
			        + "and exists (select 1 from loprtmanager where code='BQ99' and otherno=b.contno and standbyflag1=b.edoracceptno and stateflag='3' and StandbyFlag3=a.actugetNo) "
			        //add by jiaqiangli 2009-08-27 同时需要加上不能存在小于3的 未核销之前都应该不再生成
			        //comment by jiaqiangli 2009-08-27 其实条件可以合二为一(只需要这个条件即可而不需要union 逆向思维判断)
			        + "and not exists (select 1 from loprtmanager where code='BQ99' and otherno=b.contno and standbyflag1=b.edoracceptno and stateflag < '3' and StandbyFlag3=a.actugetNo) "
				    + "order by EdorAcceptNo asc ";
		tsqlbv.sql(tGetSQL);
		tsqlbv.put("ManageCom", mGlobalInput.ManageCom);
		SSRS tGetSSRS = new SSRS();
		tGetSSRS = tExeSQL.execSQL(tsqlbv);
		String tGetDateReasonSQL = "";
		SSRS tGetDateReasonSSRS = new SSRS();
		for (int i=1;i<=tGetSSRS.getMaxRow();i++) {
			LOPRTManagerSchema tLOPRTManagerSchema = null;
			try {
				
				LCContDB tLCContDB = new LCContDB();
		        tLCContDB.setContNo(tGetSSRS.GetText(i, 2));
		        if (!tLCContDB.getInfo()) {
		        	CError.buildErr(this, "保单查询失败");
					return false;
		        }

				tLOPRTManagerSchema = new LOPRTManagerSchema();
				String strNoLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
				String sPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // 生成打印流水号
				tLOPRTManagerSchema.setPrtSeq(sPrtSeq);
				
				tLOPRTManagerSchema.setOtherNo(tGetSSRS.GetText(i, 2));//受理号 contno
				tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT);
				
				//转账失败通知书
				tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_BQBankTransferFailure);
				
				tLOPRTManagerSchema.setManageCom(tLCContDB.getManageCom());
				tLOPRTManagerSchema.setAgentCode(tLCContDB.getAgentCode());
				
				tLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
				tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
				
				tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
				tLOPRTManagerSchema.setStateFlag("0"); // 打印状态
				tLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
				
				tLOPRTManagerSchema.setMakeDate(mCurrentDate);
				tLOPRTManagerSchema.setMakeTime(mCurrentTime);
				tLOPRTManagerSchema.setOldPrtSeq(sPrtSeq);
				
				//其他重要字段
				tLOPRTManagerSchema.setStandbyFlag1(tGetSSRS.GetText(i, 1));//contno 受理号
				tLOPRTManagerSchema.setStandbyFlag2(tGetSSRS.GetText(i, 3));//edortype
				tLOPRTManagerSchema.setStandbyFlag3(tGetSSRS.GetText(i, 5));//actGetNo
				tLOPRTManagerSchema.setStandbyFlag4(tGetSSRS.GetText(i, 4));//edorappdate
				tLOPRTManagerSchema.setStandbyFlag5("2");//2表退费转账失败
				
				//转账时间及转账失败原因 最后一条记录
//				//lyreturnfrombankb.paycode=ljaget.actgetno
				SQLwithBindVariables tsbv=new SQLwithBindVariables();
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tGetDateReasonSQL = "select bankdealdate,failreason from (select bankdealdate,(select codename from ldcode1 where codetype='bankerror' and code=a.bankcode and code1=banksuccflag) failreason from lyreturnfrombankb a "
						+ "where paycode='" + "?paycode?" + "' order by serialno desc) b where rownum<=1 ";
				}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tGetDateReasonSQL = "select bankdealdate,failreason from (select bankdealdate,(select codename from ldcode1 where codetype='bankerror' and code=a.bankcode and code1=banksuccflag) failreason from lyreturnfrombankb a "
						+ "where paycode='" + "?paycode?" + "' order by serialno desc) b limit 0,1 ";	
				}
				tsbv.sql(tGetDateReasonSQL);
				tsbv.put("paycode", tGetSSRS.GetText(i, 5));
				tGetDateReasonSSRS = tExeSQL.execSQL(tsbv);
				if (tGetDateReasonSSRS.getMaxRow() <= 0) {
					CError.buildErr(this, "查询转账时间及转账失败原因出错!");
					continue;
				}
				tLOPRTManagerSchema.setStandbyFlag6(tGetDateReasonSSRS.GetText(1, 1));
				tLOPRTManagerSchema.setStandbyFlag7(tGetDateReasonSSRS.GetText(1, 2));				

				mLOPRTManagerSet.add(tLOPRTManagerSchema);
				fnum++;
//				 日志监控,状态监控                 		
      			PubFun.logState(mGlobalInput,tGetSSRS.GetText(i, 6), "生成付费转账失败通知书（通知书号： "+tGetSSRS.GetText(i, 6)+"）","1");  

			}
			catch (Exception e) {
				e.printStackTrace();
				CError.buildErr(this, "插入保全拒绝通知书失败!");
				return false;
			}
		}
		
//		日志监控,结果监控
		PubFun.logResult (mGlobalInput,mGlobalInput.LogID[1],"共生成" + fnum + "件退费转账失败通知书");
		return true;
	}

	private boolean getInputData() {
		
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0));
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		BQBankTransferFailureNotice tBQBankTransferFailureNotice = new BQBankTransferFailureNotice();
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";
		tGlobalInput.ComCode = "86";
		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tBQBankTransferFailureNotice.submitData(tVData, "");
		logger.debug("ss");
	}
}
