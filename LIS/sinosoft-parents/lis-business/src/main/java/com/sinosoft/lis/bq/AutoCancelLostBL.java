package com.sinosoft.lis.bq;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LBPEdorItemSchema;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统 Description: 保全－保单自解挂
 * </p>
 */
public class AutoCancelLostBL {
private static Logger logger = Logger.getLogger(AutoCancelLostBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 传输数据的容器 */
	private VData mInputData;
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private int num = 0; // 自解挂保单数目（其实细到了险种级）
	private int bnum = 0; // 补发保单数目
	private List mBomList = new ArrayList();

	private PrepareBOMBQEdorBL mPrepareBOMBQEdorBL = new PrepareBOMBQEdorBL();

	private boolean mBomListFlag = false;
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();
	private String mRemark = "该保单挂失后于第8日自动解挂";
	private int mDay = 0;
	private GlobalInput mGlobalInput = new GlobalInput();
	
	public AutoCancelLostBL() {
	}

	public boolean submitData(VData cInputData) {
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		
        SQLwithBindVariables sbv=new SQLwithBindVariables();
		String strSqlday = " select sysvarvalue from ldsysvar where 1=1 and sysvar='autoCancelLostDate'";
		sbv.sql(strSqlday);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sbv);
		if(tSSRS.getMaxRow() > 0)
		{
			mDay = Integer.parseInt(tSSRS.GetText(1, 1));			
		}
		else
		{
			mErrors.addOneError(new CError("自动解挂天数限制查询数据失败！"));
		      //日志监控,过程监控        
	    	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"没有设置自动解挂天数限制");
			return false;
		}
		
//		//add by jiaqiangli 2009-05-04
//		//处理日期的8天改成描述规则
//		mDay = getLostCancelLimit();
//		if (mDay ==0 || mDay == -1) {
//			mErrors.addOneError(new CError("自动解挂天数限制查询数据失败！"));
//		      //日志监控,过程监控        
//	    	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"没有设置自动解挂天数限制");
//			return false;
//		}
//		//add by jiaqiangli 2009-05-04
		
		String CancelDay = PubFun.calDate(mCurrentDate, mDay * -1, "D", null);
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String strSql = "select * from lccontstate "
				+ " where startdate <= '"
				+ "?CancelDay?"
				+ "' "
				+ " and enddate is null and state = '1' and statetype = 'Lost' "
				// add by jiaqiangli 2009-06-10 目前只有个单有此批处理
				+ " and exists(select 1 from lccont where contno=lccontstate.contno and conttype='1') "
				// test as use case
				//+ " and contno in ('86320320080210021952','86321220080210004120','86350220080210004631','86371320080210008139','86510520080210001919','86512020080210008787')"
				;
		LCContStateSet tLCContStateSet = new LCContStateSet();
		RSWrapper rsWrapper = new RSWrapper();
		// 传入结果集合,SQL
		sqlbv.sql(strSql);
		sqlbv.put("CancelDay", CancelDay);
		if (!rsWrapper.prepareData(tLCContStateSet, sqlbv)) {
			this.mErrors.copyAllErrors(rsWrapper.mErrors);
			logger.debug(rsWrapper.mErrors.getFirstError());
			return false;
		}
		do {
			rsWrapper.getData();
			if (tLCContStateSet != null && tLCContStateSet.size() > 0) {
				unLostData(tLCContStateSet);
			}
		} while (tLCContStateSet != null && tLCContStateSet.size() > 0);

		rsWrapper.close();
		logger.debug("自解挂处理结束，共" + num + "个保单(险种)自解挂");
//		日志监控,结果监控
		PubFun.logResult (mGlobalInput,mGlobalInput.LogID[1],"自解挂处理结束，共" + num + "个保单(险种)自解挂");
		PubFun.logResult (mGlobalInput,mGlobalInput.LogID[1],"自解挂处理结束，共" + bnum + "个保单因申请补发未解挂");		
		return true;
	}

	private boolean unLostData(LCContStateSet tLCContStateSet) {
		if (tLCContStateSet == null || tLCContStateSet.size() < 1) {
			return true;
		}
		for (int i = 1; i <= tLCContStateSet.size(); i++) {
			if (!approveLR(tLCContStateSet.get(i))) {
				if (changeState(tLCContStateSet.get(i))) {
					num++;
				}
			}
		}
		return true;
	}
	
	private int getLostCancelLimit() {
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String tLostCancelLimit = "0";
		String tQuerySQL = "select * from LMEdorCal where CalType = 'CancelLost' ";
		sqlbv.sql(tQuerySQL);
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		LMEdorCalSet tLMEdorCalSet = new LMEdorCalSet();
		try {
			tLMEdorCalSet = tLMEdorCalDB.executeQuery(sqlbv);
		}
		catch (Exception ex) {
			logger.debug("\t   错误原因 : " + tLMEdorCalDB.mErrors.getFirstError());
			return -1;
		}
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0) {
			logger.debug("\t@> getLostCancelLimit 未知的保全类型！ 查询逾期天数失败！");
			return -1;
		}
		else {
			String sTempCalCode = tLMEdorCalSet.get(1).getCalCode();
			if (sTempCalCode == null || sTempCalCode.trim().equals("")) {
				logger.debug("\t@> getLostCancelLimit 查询逾期天数计算代码失败！");
				return -1;
			}
			else {
				Calculator tCalculator = new Calculator();
				BqCalBase mBqCalBase=new BqCalBase();
				// 根据计算代码计算逾期天数
				if (!prepareBOMList(mBqCalBase)) {
					CError.buildErr(this, "Prepare BOMLIST Failed...");
					return -1;
				}
				tCalculator.setBOMList(this.mBomList);// 添加BOMList
				tCalculator.setCalCode(sTempCalCode);
				tLostCancelLimit = tCalculator.calculate();
				if (tLostCancelLimit == null || tLostCancelLimit.equals("")) {
					logger.debug("getLostCancelLimit 根据计算代码计算逾期天数失败！");
					return -1;
				}
				tCalculator = null;
			}
		}
		tLMEdorCalDB = null;
		tLMEdorCalSet = null;
		return Integer.parseInt(tLostCancelLimit);
	}

	// 判断有没有申请保单补发
	private boolean approveLR(LCContStateSchema tLCContStateSchema) {
		if (tLCContStateSchema != null) {
			String tStartDate = tLCContStateSchema.getStartDate();
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			String tCancelDay = PubFun.calDate(tStartDate, mDay, "D", null);
			String strsql = "select edorno from lpedoritem "
					+ " where edorappdate between '" + "?tStartDate?" + "' and '"
					+ "?tCancelDay?" + "' " + " and edortype = 'LR' and contno = '"
					+ "?contno?" + "'";
			sqlbv.sql(strsql);
			sqlbv.put("tStartDate", tStartDate);
			sqlbv.put("tCancelDay", tCancelDay);
			sqlbv.put("contno", tLCContStateSchema.getContNo());
			ExeSQL q_exesql = new ExeSQL();
			SSRS tssrs = new SSRS();
			tssrs = q_exesql.execSQL(sqlbv);
			if (tssrs != null && tssrs.getMaxRow() >= 1) {
//				 日志监控,状态监控                 		
      			PubFun.logState(mGlobalInput,tLCContStateSchema.getContNo(), "保单"+tLCContStateSchema.getContNo()+"因申请补发未解挂","0");  
				bnum++;
				return true;
			}
		}
		return false;
	}

	private boolean changeState(LCContStateSchema tLCContStateSchema) {
		if (tLCContStateSchema != null) {
			String tStartDate = tLCContStateSchema.getStartDate();
			String tEndDate = PubFun.calDate(tStartDate, mDay - 1, "D", null);
			String newStartDate = PubFun.calDate(tStartDate, mDay, "D", null);
			LCContStateSchema newLCContStateSchema = new LCContStateSchema();
			Reflections tRef = new Reflections();
			tRef.transFields(newLCContStateSchema, tLCContStateSchema);
			// 结束旧状态
			tLCContStateSchema.setEndDate(tEndDate);
			tLCContStateSchema.setModifyDate(mCurrentDate);
			tLCContStateSchema.setModifyTime(mCurrentTime);
			// 生成新状态
			newLCContStateSchema.setStateReason(null);
			newLCContStateSchema.setInsuredNo("000000");
			newLCContStateSchema.setPolNo("000000");
			newLCContStateSchema.setState("0");
			newLCContStateSchema.setRemark(mRemark);
			newLCContStateSchema.setOperator("000");
			newLCContStateSchema.setStartDate(newStartDate);
			newLCContStateSchema.setMakeDate(mCurrentDate);
			newLCContStateSchema.setMakeTime(mCurrentTime);
			newLCContStateSchema.setModifyDate(mCurrentDate);
			newLCContStateSchema.setModifyTime(mCurrentTime);
			// 提交
			MMap tMap = new MMap();
			VData tData = new VData();
			PubSubmit tPubSubmit = new PubSubmit();
			tMap.put(tLCContStateSchema, "DELETE&INSERT");
			tMap.put(newLCContStateSchema, "DELETE&INSERT");
			tData.add(tMap);
			if (!tPubSubmit.submitData(tData, "")) {
				mErrors.addOneError(new CError(tLCContStateSchema.getContNo()
						+ "提交数据失败！"));
				logger.debug(tLCContStateSchema.getContNo() + "提交数据失败！");
				return false;
			}
//			 日志监控,状态监控                 		
  			PubFun.logState(mGlobalInput,tLCContStateSchema.getContNo(), "保单"+tLCContStateSchema.getContNo()+"解挂成功","1");  

		}
		return true;
	}

	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate() {
		return this.mOperate;
	}

	public VData getResult() {
		return mResult;
	}
	/**
	 * 为全局变量mBomList赋值，如果已经赋值过，则不再赋值
	 * 
	 * @return
	 */
	private boolean prepareBOMList(BqCalBase mBqCalBase) {
		try {
			if (!this.mBomListFlag) {
				this.mPrepareBOMBQEdorBL.setBqCalBase(mBqCalBase);	
				LPEdorItemSchema tLPEdorItemSchema=new LPEdorItemSchema();
				this.mBomList = this.mPrepareBOMBQEdorBL.dealData(tLPEdorItemSchema);
				this.mBomListFlag = true;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			this.mBomListFlag = false;
			return false;
		}
	}

	public static void main(String[] args) {
		logger.debug("test start:");

		AutoCancelLostBL tAutoCancelLostBL = new AutoCancelLostBL();
//		if (!tAutoCancelLostBL.submitData()) {
//			logger.debug("test failed.");
//		}

		logger.debug("test end");
	}
}
