package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

/**
 * <p>Title: 自垫批处理</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: SinoSoft</p>
 * @author Nicholas pst
 * @version 1.0 2.0
 */

import com.sinosoft.lis.autopay.*;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LJSPaySet;

import java.util.HashMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;


public class AutoPayTask extends TaskThread
{
private static Logger logger = Logger.getLogger(AutoPayTask.class);
//全局变量
private GlobalInput mGlobalInput = new GlobalInput();

    public AutoPayTask()
    {
    }
	/**选数SQL，循环处理，筛选一部分保单，也可以指定某一保单,时间控制，
	 * 即选出交至日期在传入时间之前的保单，由于保单存在宽限期，这样每次
	 * 会多选出不必要处理的保单，只得在AutoPayBL内去排除判断。
	 * */ 
    private String tSql="";
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();	
	private ExeSQL tExeSQL = new ExeSQL();
	private int tSuc = 0,tFail = 0;
	
    public boolean dealMain(VData aVData)
    {
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  AutoPayPrem Start!!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
		mGlobalInput=(GlobalInput) aVData.getObjectByObjectName(
				"GlobalInput", 0);	
	    //日志监控,过程监控        
	  	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"自动垫交批处理开始");
		try {
			logger.debug("参数个数:" + mParameters.size());
			GlobalInput tGlobalInput = null;

			// 处理的机构
			tGlobalInput = (GlobalInput) mParameters.get("GlobalInput");
			// 保单机构
			String tManageCom = (String) mParameters.get("ManageCom");
			logger.debug("The manageCom is :" + tManageCom);
			// 进行自垫批处理的时间
			String tCalDate = (String) mParameters.get("CalDate");
			logger.debug("The CalDate is :" + tCalDate);
			// 保单号
			String tContNo = (String) mParameters.get("ContNo");
			logger.debug("The ContNo is :" + tContNo);

			if (tManageCom == null || "".equals(tManageCom)) {
				tManageCom = "86";
			}

			if (tCalDate == null || "".equals(tCalDate)) {
				tCalDate = PubFun.getCurrentDate();
			}

			if (tGlobalInput == null) {
				tGlobalInput = new GlobalInput();
				tGlobalInput.Operator = "001";
				tGlobalInput.ManageCom = "86";
			}
		
            // 查询逻辑由保单，变为续期催收数据
			tSql =" SELECT * " 
				+" FROM ljspay a "
				  +" WHERE 1 = 1 "
					+" and exists (select 'X' "
									+ " from LCPol b "
								    + " where b.ContNo = a.otherno "
									+ " and AppFlag = '1' "
									+ " and exists (select 'C' from lccont where contno = b.contno and AutoPayFlag = '1') and polno=mainpolno and "
									+" exists (select 'A' from LMRiskApp where RiskCode=b.RiskCode and AutoPayFlag = '1')) "
					+" and a.ManageCom like concat('"+"?tManageCom?"+"','%') "
					+"?a?"
				 	+" and not exists "
				    +" (select 'X' from lcconthangupstate d where d.ContNo = a.otherno) "
					+ " and exists( select 'Y'"
					+ " from LJSPayPerson c"
					+ " where "
					+" c.GetNoticeNo=a.GetNoticeNo"
					+ " and c.PayType='ZC'"
					+ " )" //排除失效的记录
					+ " and not exists (select 'B' from LCContState where  contno=a.otherno and StateType='Available' and State='1' and EndDate is null)"
					+" and a.othernotype = '2' "
					+" and a.bankonthewayflag <> '1' "
					//宽末期当前不允许自垫
					+" and a.paydate < to_date('"+"?tCalDate?"+"', 'YYYY-MM-DD') "
					+" and not exists (select 'Z' "
									+" from LJTempFee "
								  +" where TempFeeNo = a.GetNoticeNo "
									+" and TempFeeType = '2') " ; 
			// 可以确定处于保全挂起的不要进行自垫
			logger.debug(tSql);
			LJSPaySet tLJSPaySet = null;
			tLJSPaySet = new LJSPaySet();

			RSWrapper rsWrapper = new RSWrapper();
			// 采取长连接的方式
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("a", BqNameFun.getWherePart("a.otherno", tContNo));
			sqlbv.put("tCalDate", tCalDate);
			sqlbv.put("tManageCom", tManageCom);
			if (!rsWrapper.prepareData(tLJSPaySet, sqlbv)) {
				this.mErrors.copyAllErrors(rsWrapper.mErrors);
				logger.debug(rsWrapper.mErrors.getFirstError());
				return false;
			}
			do {
				rsWrapper.getData();
				if (tLJSPaySet == null || tLJSPaySet.size() <= 0) {
					break;
				}
				String LockNo="";
				for (int i = 1; i <= tLJSPaySet.size(); i++) {
					
				   	
					LockNo = "";
					LockNo= tLJSPaySet.get(i).getOtherNo();
					if (!mPubLock.lock(LockNo, "LI0001", tGlobalInput.Operator)) 
					{
						CError tError = new CError(mPubLock.mErrors.getLastError());
						this.mErrors.addOneError(tError);
						continue;
					}
										
					// 数据准备操作
					LCContSet tLCContSet = new LCContSet();
					LCContDB tLCContDB = new LCContDB();
					LCContSchema tLCContSchema = new LCContSchema();
					tLCContDB.setContNo(tLJSPaySet.get(i).getOtherNo());
					tLCContDB.setAppFlag("1");
					tLCContSet=tLCContDB.query();
					if(tLCContSet.size()<1 || tLCContSet==null)
					{
						continue;
					}else
					{
					   tLCContSchema = tLCContSet.get(1);
					}
					VData tVData = new VData();
					VData mResult = new VData();
					tVData.add(mGlobalInput);
					tVData.add(tLCContSchema);
					tVData.add(tCalDate);
					AutoPayBL tAutoPayBL = new AutoPayBL();
					// 业务逻辑处理
					if (!tAutoPayBL.submitData(tVData, "")) {
						this.mErrors.copyAllErrors(tAutoPayBL.getErrors());
						logger.debug(tAutoPayBL.getErrors()
								.getFirstError()
								+ " 自动垫交处理失败，保单号：" + tLCContSchema.getContNo());
//						日志监控,状态监控          
		  		    	PubFun.logState(mGlobalInput,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"自动垫交失败，原因是："+tAutoPayBL.getErrors()
								.getFirstError(),"0");
						tFail++;
						continue;
					}
					// 数据提交
					mResult.clear();
					mResult = tAutoPayBL.getResult();
					PubSubmit tSubmit = new PubSubmit();
					if (!tSubmit.submitData(mResult, "")) {
						// @@错误处理
						this.mErrors.copyAllErrors(tSubmit.mErrors);
						continue;
					}
					tSuc++;
					logger.debug("*********************保单自垫完成！保单号："
							+ tLCContSchema.getContNo()
							+ "*********************");
//					日志监控,状态监控          
	  		    	PubFun.logState(mGlobalInput,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"自动垫交成功","1");	  		    
					mPubLock.unLock();
				}
			} while (tLJSPaySet != null && tLJSPaySet.size() > 0);
			rsWrapper.close();
		        }
			catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				mPubLock.unLock();				
			}
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  AutoPayPrem End!!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
//		日志监控,结果监控
		PubFun.logResult (mGlobalInput,mGlobalInput.LogID[1],"共有"+tSuc+"个保单自动垫交成功");
		PubFun.logResult (mGlobalInput,mGlobalInput.LogID[1],"共有"+tFail+"个保单自动垫交失败");
		logger.debug("成功:" + tSuc + "单，失败: " + tFail + "单");
	    //日志监控,过程监控        
	  	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"自动垫交批处理结束");
		return true;
	}
	
    public boolean dealMain()
    {
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  AutoPayPrem Start!!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
		try {
			logger.debug("参数个数:" + mParameters.size());
			GlobalInput tGlobalInput = null;

			// 处理的机构
			tGlobalInput = (GlobalInput) mParameters.get("GlobalInput");
			// 保单机构
			String tManageCom = (String) mParameters.get("ManageCom");
			logger.debug("The manageCom is :" + tManageCom);
			// 进行自垫批处理的时间
			String tCalDate = (String) mParameters.get("CalDate");
			logger.debug("The CalDate is :" + tCalDate);
			// 保单号
			String tContNo = (String) mParameters.get("ContNo");
			logger.debug("The ContNo is :" + tContNo);

			if (tManageCom == null || "".equals(tManageCom)) {
				tManageCom = "86";
			}

			if (tCalDate == null || "".equals(tCalDate)) {
				tCalDate = PubFun.getCurrentDate();
			}

			if (tGlobalInput == null) {
				tGlobalInput = new GlobalInput();
				tGlobalInput.Operator = "001";
				tGlobalInput.ManageCom = "86";
			}
		
            // 查询逻辑由保单，变为续期催收数据
			tSql =" SELECT * " 
				+" FROM ljspay a "
				  +" WHERE 1 = 1 "
					+" and exists (select 'X' "
									+ " from LCPol b "
								    + " where b.ContNo = a.otherno "
									+ " and AppFlag = '1' "
									+ " and exists (select 'C' from lccont where contno = b.contno and AutoPayFlag = '1') and polno=mainpolno and "
									+" exists (select 'A' from LMRiskApp where RiskCode=b.RiskCode and AutoPayFlag = '1')) "
					+" and a.ManageCom like concat('"+"?tManageCom?"+"','%') "
					+"?b?"
				 	+" and not exists "
				    +" (select 'X' from lcconthangupstate d where d.ContNo = a.otherno) "
					+ " and exists( select 'Y'"
					+ " from LJSPayPerson c"
					+ " where "
					+" c.GetNoticeNo=a.GetNoticeNo"
					+ " and c.PayType='ZC'"
					+ " )" //排除失效的记录
					+ " and not exists (select 'B' from LCContState where  contno=a.otherno and StateType='Available' and State='1' and EndDate is null)"
					+" and a.othernotype = '2' "
					+" and a.bankonthewayflag <> '1' "
					//宽末期当前不允许自垫
					+" and a.paydate < to_date('"+"?tCalDate?"+"', 'YYYY-MM-DD') "
					+" and not exists (select 'Z' "
									+" from LJTempFee "
								  +" where TempFeeNo = a.GetNoticeNo "
									+" and TempFeeType = '2') " ; 
			// 可以确定处于保全挂起的不要进行自垫
			logger.debug(tSql);
			LJSPaySet tLJSPaySet = null;
			tLJSPaySet = new LJSPaySet();

			RSWrapper rsWrapper = new RSWrapper();
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tSql);
			sqlbv1.put("tManageCom", tManageCom);
			sqlbv1.put("b", BqNameFun.getWherePart("a.otherno", tContNo));
			sqlbv1.put("tCalDate", tCalDate);
			// 采取长连接的方式
			if (!rsWrapper.prepareData(tLJSPaySet, sqlbv1)) {
				this.mErrors.copyAllErrors(rsWrapper.mErrors);
				logger.debug(rsWrapper.mErrors.getFirstError());
				return false;
			}
			do {
				rsWrapper.getData();
				if (tLJSPaySet == null || tLJSPaySet.size() <= 0) {
					break;
				}
				String LockNo="";
				for (int i = 1; i <= tLJSPaySet.size(); i++) {
					
				   	
					LockNo = "";
					LockNo= tLJSPaySet.get(i).getOtherNo();
					if (!mPubLock.lock(LockNo, "LI0001", tGlobalInput.Operator)) 
					{
						CError tError = new CError(mPubLock.mErrors.getLastError());
						this.mErrors.addOneError(tError);
						continue;
					}
										
					// 数据准备操作
					LCContSet tLCContSet = new LCContSet();
					LCContDB tLCContDB = new LCContDB();
					LCContSchema tLCContSchema = new LCContSchema();
					tLCContDB.setContNo(tLJSPaySet.get(i).getOtherNo());
					tLCContDB.setAppFlag("1");
					tLCContSet=tLCContDB.query();
					if(tLCContSet.size()<1 || tLCContSet==null)
					{
						continue;
					}else
					{
					   tLCContSchema = tLCContSet.get(1);
					}
					VData tVData = new VData();
					VData mResult = new VData();
					tVData.add(tGlobalInput);
					tVData.add(tLCContSchema);
					tVData.add(tCalDate);
					AutoPayBL tAutoPayBL = new AutoPayBL();
					// 业务逻辑处理
					if (!tAutoPayBL.submitData(tVData, "")) {
						this.mErrors.copyAllErrors(tAutoPayBL.getErrors());
						logger.debug(tAutoPayBL.getErrors()
								.getFirstError()
								+ " 自动垫交处理失败，保单号：" + tLCContSchema.getContNo());
						tFail++;
						continue;
					}
					// 数据提交
					mResult.clear();
					mResult = tAutoPayBL.getResult();
					PubSubmit tSubmit = new PubSubmit();
					if (!tSubmit.submitData(mResult, "")) {
						// @@错误处理
						this.mErrors.copyAllErrors(tSubmit.mErrors);
						continue;
					}
					tSuc++;
					logger.debug("*********************保单自垫完成！保单号："
							+ tLCContSchema.getContNo()
							+ "*********************");
					mPubLock.unLock();
				}
			} while (tLJSPaySet != null && tLJSPaySet.size() > 0);
			rsWrapper.close();
		        }
			catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				mPubLock.unLock();				
			}
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  AutoPayPrem End!!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
		logger.debug("成功:" + tSuc + "单，失败: " + tFail + "单");
		return true;
	}

       
	public static void main(String str[]) {
		String tContArry[]={
		};
//		for(int k=0;k<tContArry.length;k++)
        for(int k=0;k<1;k++)
		{
			AutoPayTask tAutoPayTask = new AutoPayTask();
			
			HashMap aParameters =new HashMap();
			aParameters.put("ManageCom", "86");
			aParameters.put("CalDate", "2009-08-18");
			//aParameters.put("ContNo", "86130620070210009821");
			tAutoPayTask.setParameter(aParameters);
			tAutoPayTask.dealMain();			
		}

	}
}
