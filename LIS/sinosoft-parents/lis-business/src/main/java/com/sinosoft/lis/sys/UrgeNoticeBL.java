package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: BL层业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author SXY
 * @version 1.0
 * @date 2003-07-18
 */
public class UrgeNoticeBL {
private static Logger logger = Logger.getLogger(UrgeNoticeBL.class);

	/** 传入数据的容器 */
	private VData mInputData;
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate = new String();

	/** 业务处理相关变量 */
	/** 全局数据 */
	private GlobalInput mGI = new GlobalInput();
	/** 待催发通知书的数据集合 */
	private LOPRTManagerSet OldLOPRTManagerSet = new LOPRTManagerSet();
	/** 催发的新通知书的数据集合 */
	private LOPRTManagerSet NewLOPRTManagerSet = new LOPRTManagerSet();
	/**
	 * 只记录了所选待催发通知书的流水号的通知书数据集合 (当处理业务是为所选定的通知书催发通知书时会从前台获得该数据集合)
	 */
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	/** 日期时间处理类 */
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private String note;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	// @Constructor
	public UrgeNoticeBL() {
	}

	public static void main(String[] args) {

	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将传入的数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 将外部传入的数据分解到本类的属性中，准备处理
		if (!getInputData(cOperate)) {
			return false;
		}

		// 进行业务处理
		if (!dealData(cOperate)) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("Start UrgeNotice BL Submit...");

		UrgeNoticeBLS tUrgeNoticeBLS = new UrgeNoticeBLS();
		tUrgeNoticeBLS.submitData(mInputData, cOperate);

		logger.debug("End UrgeNotice BL Submit...");

		// 如果有需要处理的错误，则返回
		if (tUrgeNoticeBLS.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tUrgeNoticeBLS.mErrors);
		}

		mInputData = null;
		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData(String cOperate) {
		boolean tReturn = false;
		String tNo = "";

		// 催发前台所选定的待催发的通知书
		if (cOperate.equals("UPDATE")) {
			// 待催发的通知书计数变量
			int i;
			// 所选的待催发的通知书信息变量
			LOPRTManagerSchema tLOPRTManagerSchema;
			// 新催发的通知书信息变量(相应与所选的待催发的通知书)
			LOPRTManagerSchema nLOPRTManagerSchema;

			logger.debug("所选待催发通知书数量:  " + mLOPRTManagerSet.size());

			OldLOPRTManagerSet.clear();
			NewLOPRTManagerSet.clear();
			for (i = 1; i <= mLOPRTManagerSet.size(); i++) {
				// 查询一条所选的待催发的通知书信息
				tLOPRTManagerSchema = new LOPRTManagerSchema();
				LOPRTManagerDB tLOPRTManagerDB;
				tLOPRTManagerDB = new LOPRTManagerDB();
				tLOPRTManagerDB.setPrtSeq(mLOPRTManagerSet.get(i).getPrtSeq());
				if (tLOPRTManagerDB.getInfo() == false) {
					// @@错误处理
					this.mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "UrgeNoticeBL";
					tError.functionName = "getInputData";
					tError.errorMessage = "通知书信息查询失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				tLOPRTManagerSchema.setSchema(tLOPRTManagerDB);

				// 形成新催发的通知书信息(相应与所选的待催发的通知书)
				nLOPRTManagerSchema = new LOPRTManagerSchema();
				// 获取流水号
				String strNoLimit = PubFun.getNoLimit(mGI.ComCode);
				nLOPRTManagerSchema.setPrtSeq(PubFun1.CreateMaxNo("PRTSEQNO",
						strNoLimit));
				// 形成其他必需字段数据
				nLOPRTManagerSchema
						.setOtherNo(tLOPRTManagerSchema.getOtherNo());
				nLOPRTManagerSchema.setOtherNoType(tLOPRTManagerSchema
						.getOtherNoType());
				if (tLOPRTManagerSchema.getCode().equals("03")) {
					nLOPRTManagerSchema.setCode("11");
				} else if (tLOPRTManagerSchema.getCode().equals("05")) {
					nLOPRTManagerSchema.setCode("12");
				} else if (tLOPRTManagerSchema.getCode().equals("07")) {
					nLOPRTManagerSchema.setCode("10");
				} else if (tLOPRTManagerSchema.getCode().equals("04")) {
					nLOPRTManagerSchema.setCode("14");
				}
				nLOPRTManagerSchema.setManageCom(tLOPRTManagerSchema
						.getManageCom());
				nLOPRTManagerSchema.setAgentCode(tLOPRTManagerSchema
						.getAgentCode());
				nLOPRTManagerSchema.setReqCom(mGI.ManageCom);
				nLOPRTManagerSchema.setReqOperator(mGI.Operator);
				nLOPRTManagerSchema.setPrtType("0");
				nLOPRTManagerSchema.setStateFlag("0");
				nLOPRTManagerSchema.setMakeDate(CurrentDate);
				nLOPRTManagerSchema.setMakeTime(CurrentTime);
				nLOPRTManagerSchema.setActMakeDate(CurrentDate);
				nLOPRTManagerSchema.setActMakeTime(CurrentTime);
				nLOPRTManagerSchema.setStandbyFlag1(tLOPRTManagerSchema
						.getStandbyFlag1());
				nLOPRTManagerSchema.setStandbyFlag2(tLOPRTManagerSchema
						.getStandbyFlag2());
				nLOPRTManagerSchema.setOldPrtSeq(tLOPRTManagerSchema
						.getPrtSeq());
				NewLOPRTManagerSet.add(nLOPRTManagerSchema);
				tLOPRTManagerSchema.setStateFlag("3");
				OldLOPRTManagerSet.add(tLOPRTManagerSchema);
			}
			tReturn = true;
			note = String.valueOf(i - 1);
		}

		// 催发所有待催发的通知书
		if (cOperate.equals("INSERT")) {
			OldLOPRTManagerSet.clear();
			NewLOPRTManagerSet.clear();

			// 查询出所有待催发的通知书信息
			LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
			LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();
			String tsql;
			// tsql = "select * from loprtmanager where prtseq in "
			// + "("
			// + "select prtseq from loprtmanager where stateflag = '1' and code
			// in ('03','05','07') and sysdate >= loprtmanager.formakedate "
			// + ") "
			// + "and otherno in (select ContNo from LCCont where appflag='0'
			// and uwflag not in ('1','2','a'))"
			// + "and ManageCom like '" + mGI.ManageCom + "%%'"
			// + "order by code,makedate";
			tsql = "select * from loprtmanager a where "
					+ "exists (select 1  from lccont where appflag = '0'  and uwflag not in ('1', '2', 'a') and contno = a.otherno) "
					+ "and stateflag = '1' "
					+ "and code in ('03', '05', '07') "
					+ "and now() >= formakedate and ManageCom like concat('"
					+ "?ManageCom?" + "','%')" + "order by code, makedate";
            SQLwithBindVariables sqlbv=new SQLwithBindVariables();
            sqlbv.sql(tsql);
            sqlbv.put("ManageCom", mGI.ManageCom);
			logger.debug("tsql:" + tsql);
			tLOPRTManagerSet.set(tLOPRTManagerDB.executeQuery(sqlbv));
			if (tLOPRTManagerDB.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UrgeNoticeBL";
				tError.functionName = "updateData";
				tError.errorMessage = "查询信息失败!";
				this.mErrors.addOneError(tError);
				tReturn = false;
			}

			logger.debug("tLOPRTManagerSet.size:  Yeah!"
					+ tLOPRTManagerSet.size());

			// 待催发的通知书计数变量
			int i;
			// 所选的待催发的通知书信息变量
			LOPRTManagerSchema tLOPRTManagerSchema;
			// 新催发的通知书信息变量(相应与所选的待催发的通知书)
			LOPRTManagerSchema nLOPRTManagerSchema;

			for (i = 1; i <= tLOPRTManagerSet.size(); i++) {
				// 查询当前要处理的通知书信息
				tLOPRTManagerSchema = new LOPRTManagerSchema();
				tLOPRTManagerDB = new LOPRTManagerDB();
				tLOPRTManagerDB.setPrtSeq(tLOPRTManagerSet.get(i).getPrtSeq());
				if (tLOPRTManagerDB.getInfo() == false) {
					// @@错误处理
					this.mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "UrgeNoticeBL";
					tError.functionName = "getInputData";
					tError.errorMessage = "通知书单查询失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				tLOPRTManagerSchema.setSchema(tLOPRTManagerDB);

				// 形成新催发的通知书信息(相应与当前要处理的待催发的通知书)
				nLOPRTManagerSchema = new LOPRTManagerSchema();
				// 获取流水号
				String strNoLimit = PubFun.getNoLimit(mGI.ComCode);
				nLOPRTManagerSchema.setPrtSeq(PubFun1.CreateMaxNo("PRTSEQNO",
						strNoLimit));
				// 形成其他必需字段数据
				nLOPRTManagerSchema
						.setOtherNo(tLOPRTManagerSchema.getOtherNo());
				nLOPRTManagerSchema.setOtherNoType(tLOPRTManagerSchema
						.getOtherNoType());
				if (tLOPRTManagerSchema.getCode().equals("03")) {
					nLOPRTManagerSchema.setCode("11");
				} else if (tLOPRTManagerSchema.getCode().equals("05")) {
					nLOPRTManagerSchema.setCode("12");
				} else if (tLOPRTManagerSchema.getCode().equals("07")) {
					nLOPRTManagerSchema.setCode("10");
				} else if (tLOPRTManagerSchema.getCode().equals("04")) {
					nLOPRTManagerSchema.setCode("14");
				}
				nLOPRTManagerSchema.setManageCom(tLOPRTManagerSchema
						.getManageCom());
				nLOPRTManagerSchema.setAgentCode(tLOPRTManagerSchema
						.getAgentCode());
				nLOPRTManagerSchema.setReqCom(mGI.ManageCom);
				nLOPRTManagerSchema.setReqOperator(mGI.Operator);
				nLOPRTManagerSchema.setPrtType("0");
				nLOPRTManagerSchema.setStateFlag("0");
				nLOPRTManagerSchema.setMakeDate(CurrentDate);
				nLOPRTManagerSchema.setMakeTime(CurrentTime);
				nLOPRTManagerSchema.setActMakeDate(CurrentDate);
				nLOPRTManagerSchema.setActMakeTime(CurrentTime);
				nLOPRTManagerSchema.setStandbyFlag1(tLOPRTManagerSchema
						.getStandbyFlag1());
				nLOPRTManagerSchema.setStandbyFlag2(tLOPRTManagerSchema
						.getStandbyFlag2());
				nLOPRTManagerSchema.setOldPrtSeq(tLOPRTManagerSchema
						.getPrtSeq());
				NewLOPRTManagerSet.add(nLOPRTManagerSchema);
				tLOPRTManagerSchema.setStateFlag("3");
				OldLOPRTManagerSet.add(tLOPRTManagerSchema);
			}
			tReturn = true;
			note = String.valueOf(i - 1);
		}
		return tReturn;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(String tOperate) {
		mGI = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		// 催发所选的通知书时会传入所选通知书流水号集合 tOperate=="UPDATE"
		if (tOperate == "UPDATE") {
			mLOPRTManagerSet = (LOPRTManagerSet) mInputData
					.getObjectByObjectName("LOPRTManagerSet", 0);
		}

		// 催发所有的通知书时不会传入所选通知书流水号集合 tOperate=="INSERT"
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mResult.clear();
			mInputData.add(NewLOPRTManagerSet);
			mInputData.add(OldLOPRTManagerSet);
			mResult.add(NewLOPRTManagerSet);
			mResult.add(OldLOPRTManagerSet);
			mResult.add(note);
			logger.debug("prepareOutputData:");
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UrgeNoticeBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 返回业务处理结果
	 */
	public VData getResult() {
		return this.mResult;
	}

}
