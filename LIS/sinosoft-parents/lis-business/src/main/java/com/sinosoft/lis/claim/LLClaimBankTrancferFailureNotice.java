package com.sinosoft.lis.claim;
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

public class LLClaimBankTrancferFailureNotice {
private static Logger logger = Logger.getLogger(LLClaimBankTrancferFailureNotice.class);

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
	
	public LLClaimBankTrancferFailureNotice() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		
		mInputData = (VData) cInputData.clone();
		
		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}
	    //日志监控,过程监控        
    	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"理赔转账失败通知书批处理开始");
		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}
	    //日志监控,过程监控        
    	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"理赔转账失败通知书批处理结束");
    	
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
		int fnum=0;
		
		//循环处理退费转账失败问题件
		//a.GetMoney,a.bankcode,a.bankaccno,b.accname ljspay.getnoticeno
		//modify by jiaqiangli 2009-11-04 a.managecom
		String tGetSQL = "select a.actugetno,c.rgtno,c.endcasedate,a.managecom,a.agentcode,a.Drawer,a.Sumgetmoney  "
				    + "from LJaget a , LLRegister c where c.rgtno = a.OtherNo and 1 = 1 and a.ManageCom like "
				    // 银行转帐三次失败生成通知书 ljspay.payform是否应该取lpedorapp.PayForm
				    + "concat('?ManageCom?','%') and a.OtherNoType = '5'  and a.sendbankcount=3 and a.paymode='4' "
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
				    + "from LJaget a , LLRegister c where c.rgtno = a.OtherNo and 1 = 1 and a.ManageCom like "
				    // 银行转帐三次失败生成通知书 ljspay.payform是否应该取lpedorapp.PayForm
				    + "concat('?ManageCom?','%') and a.OtherNoType = '5' and a.sendbankcount=3 and a.paymode='4'"
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
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tGetSQL);
		sqlbv.put("ManageCom", mGlobalInput.ManageCom);
		SSRS tGetSSRS = new SSRS();
		tGetSSRS = tExeSQL.execSQL(sqlbv);
		String tGetDateReasonSQL = "";
		SSRS tGetDateReasonSSRS = new SSRS();
		for (int i=1;i<=tGetSSRS.getMaxRow();i++) {
			LOPRTManagerSchema tLOPRTManagerSchema = null;
			try {
				

				tLOPRTManagerSchema = new LOPRTManagerSchema();
				String strNoLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
				String sPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // 生成打印流水号
				tLOPRTManagerSchema.setPrtSeq(sPrtSeq);
				
				tLOPRTManagerSchema.setOtherNo(tGetSSRS.GetText(i, 2));//受理号 contno
				tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_REGTNO);
				
				//转账失败通知书
				tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_LPBankTransferFailure);
				
				tLOPRTManagerSchema.setManageCom(tGetSSRS.GetText(i, 4));
				tLOPRTManagerSchema.setAgentCode(tGetSSRS.GetText(i, 5));
				
				tLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
				tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
				
				tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
				tLOPRTManagerSchema.setStateFlag("0"); // 打印状态
				tLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
				
				tLOPRTManagerSchema.setMakeDate(mCurrentDate);
				tLOPRTManagerSchema.setMakeTime(mCurrentTime);
				tLOPRTManagerSchema.setOldPrtSeq(sPrtSeq);
				
				//其他重要字段
				tLOPRTManagerSchema.setStandbyFlag1(tGetSSRS.GetText(i, 1));//实付号
				tLOPRTManagerSchema.setStandbyFlag2(tGetSSRS.GetText(i, 3));//结案日期
				tLOPRTManagerSchema.setStandbyFlag3(tGetSSRS.GetText(i, 6));//领款人
				tLOPRTManagerSchema.setStandbyFlag4(tGetSSRS.GetText(i, 7));//领款人
				tLOPRTManagerSchema.setStandbyFlag5("2");//2表退费转账失败
				
				//转账时间及转账失败原因 最后一条记录
				//lyreturnfrombankb.paycode=ljaget.actgetno
				if(SysConst.DBTYPE_ORACLE.equals(SysConst.DBTYPE)){
					tGetDateReasonSQL = "select bankdealdate,failreason from (select bankdealdate,(select codename from ldcode1 where codetype='bankerror' and code=a.bankcode and code1=banksuccflag) failreason from lyreturnfrombankb a "
							+ "where paycode='" + "?paycode?" + "' order by serialno desc) where rownum<=1 ";
				}else if(SysConst.DBTYPE_MYSQL.equals(SysConst.DBTYPE)){
					tGetDateReasonSQL = "select bankdealdate,failreason from (select bankdealdate,(select codename from ldcode1 where codetype='bankerror' and code=a.bankcode and code1=banksuccflag) failreason from lyreturnfrombankb a "
							+ "where paycode='" + "?paycode?" + "' order by serialno desc) g limit 1 ";
				}
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(tGetDateReasonSQL);
				sqlbv1.put("paycode", tGetSSRS.GetText(i, 1));
				tGetDateReasonSSRS = tExeSQL.execSQL(sqlbv1);
				if (tGetDateReasonSSRS.getMaxRow() <= 0) {
					CError.buildErr(this, "查询转账时间及转账失败原因出错!");
					continue;
				}
				tLOPRTManagerSchema.setStandbyFlag6(tGetDateReasonSSRS.GetText(1, 1));
				tLOPRTManagerSchema.setStandbyFlag7(tGetDateReasonSSRS.GetText(1, 2));				

				mLOPRTManagerSet.add(tLOPRTManagerSchema);
				fnum++;
//				 日志监控,状态监控                 		
     			PubFun.logState(mGlobalInput,tGetSSRS.GetText(i, 1), "生成付费转账失败通知书（实付号： "+tGetSSRS.GetText(i, 1)+"）","1");  
			}
			catch (Exception e) {
				e.printStackTrace();
				CError.buildErr(this, "插入保全拒绝通知书失败!");
				return false;
			}
		}
//		日志监控,结果监控
		PubFun.logResult (mGlobalInput,mGlobalInput.LogID[1],"共生成" + fnum + "件付费转账失败通知书");
		
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
		LLClaimBankTrancferFailureNotice tLLClaimBankTrancferFailureNotice = new LLClaimBankTrancferFailureNotice();
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";
		tGlobalInput.ComCode = "86";
		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tLLClaimBankTrancferFailureNotice.submitData(tVData, "");
		logger.debug("ss");
	}
}
