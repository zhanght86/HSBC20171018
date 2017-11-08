/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LJAGetClaimDB;
import com.sinosoft.lis.db.LJSPayClaimDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LJAGetClaimSchema;
import com.sinosoft.lis.schema.LJAPayClaimSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.schema.LJSPayClaimSchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.vschema.LJAGetClaimSet;
import com.sinosoft.lis.vschema.LJAPayClaimSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LJSPayClaimSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 理赔回退暂交费核销处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zl
 * @version 1.0
 */
public class LLTempFeeConfBL {
private static Logger logger = Logger.getLogger(LLTempFeeConfBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	// 全局变量
	private MMap map = new MMap();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private GlobalInput mG = new GlobalInput();
	TransferData mTransferData = new TransferData();
	private Reflections mReflections = new Reflections();

	private String mClmNo = "";

	public LLTempFeeConfBL() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------LLTempFeeConfBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("----------LLTempFeeConfBL after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------LLTempFeeConfBL after checkInputData----------");
		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("----------LLTempFeeConfBL after dealData----------");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------LLTempFeeConfBL after prepareOutputData----------");
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, cOperate)) {
			// @@错误处理
			CError.buildErr(this, "数据提交失败,原因是"+tPubSubmit.mErrors.getLastError());

			return false;
		}
		mInputData = null;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("---LLTempFeeConfBL start getInputData()...");
		// 获取页面报案信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); // 按类取值
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mClmNo = (String) mTransferData.getValueByName("ClmNo");

		return true;
	}

	/**
	 * 校验传入的报案信息是否合法
	 * 
	 * @return：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		return true;
	}

	/**
	 * 数据操作类业务处理
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		double tSumJSPay = 0;
		double tSumJSFee = 0;

		/**
		 * ---------------------------------------------------------------------BEG
		 * NO.1: 分别查询暂交费与应收记录
		 * -----------------------------------------------------------------------
		 */
		// 查询回退产生的应收记录
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setOtherNo(mClmNo);
		LJSPaySet tLJSPaySet = new LJSPaySet();
		tLJSPaySet = tLJSPayDB.query();
		if (tLJSPaySet == null || tLJSPaySet.size() == 0) {
			// @@错误处理
			CError.buildErr(this, "没有找到所涉及应收记录!");
			return false;
		}
		for (int j = 1; j <= tLJSPaySet.size(); j++) {
			tSumJSPay = tSumJSPay + tLJSPaySet.get(j).getSumDuePayMoney();
		}
		logger.debug("所涉及应收记录数为:" + tLJSPaySet.size());

		// 查询已到帐的暂交费记录
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		String tSql = "select * from LJTempFee where EnterAccDate is not null"
				+ " and confdate is null " + " and otherno = '" + "?clmno?" + "' and tempfeenotype='2' and tempfeetype='6'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("clmno", mClmNo);
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv);
		LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
		
		if (tLJTempFeeSet == null || tLJTempFeeSet.size() == 0) {
			// @@错误处理
			CError.buildErr(this, "没有找到相关的暂交费记录，请确认是否已经缴费!");
			return false;
		}
		for (int j = 1; j <= tLJTempFeeSet.size(); j++) {
			tSumJSFee = tSumJSFee + tLJTempFeeSet.get(j).getPayMoney();
		}
		logger.debug("找到相关的暂交费记录为:" + tLJTempFeeSet.size());

		/**
		 * ---------------------------------------------------------------------BEG
		 * NO.2: 判断暂交费与应收记录是否一一对应（暂不考虑银行转帐） 前提: 财务收取暂交费时,已经对每笔暂交费做和应收记录齐平的校验 校验:
		 * 分别根据条数和总额来判断是否已经
		 * -----------------------------------------------------------------------
		 */
		logger.debug("应收金额为:" + tSumJSPay);
		logger.debug("暂交费金额为:" + tSumJSFee);
		
		if(tSumJSPay != tSumJSFee)
		{
			// @@错误处理
			CError.buildErr(this, "暂交费金额与应收金额不符，无法核销!");
			return false;
		}
		

		/**
		 * ---------------------------------------------------------------------BEG
		 * NO.3: 应收记录转为实收,并删除应收
		 * -----------------------------------------------------------------------
		 */
		// 更新暂交费表和暂交费分类表
		for (int j = 1; j <= tLJTempFeeSet.size(); j++) {
			tLJTempFeeSet.get(j).setConfDate(CurrentDate);
			tLJTempFeeSet.get(j).setConfFlag("1");
			tLJTempFeeSet.get(j).setModifyDate(this.CurrentDate);
			tLJTempFeeSet.get(j).setModifyTime(this.CurrentTime);
		}
		//zy 查询所有的需要核销的暂收据号
		String tempSql = "select distinct tempfeeno from LJTempFee where EnterAccDate is not null"
			+ " and confdate is null " + " and otherno = '" + "?clmno?" + "' and tempfeenotype='2' and tempfeetype='6'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tempSql);
		sqlbv1.put("clmno", mClmNo);
		ExeSQL tempExeSQL = new ExeSQL();
		SSRS tempSSRS = new SSRS();

		tempSSRS = tempExeSQL.execSQL(sqlbv1);
		for (int t=1;t<=tempSSRS.MaxRow;t++)
		{
			LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
//			tLJTempFeeClassDB.setTempFeeNo(tLJTempFeeSet.get(1).getTempFeeNo());
			tLJTempFeeClassDB.setTempFeeNo(tempSSRS.GetText(t, 1));
			LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();
			mLJTempFeeClassSet = tLJTempFeeClassDB.query();
			if (mLJTempFeeClassSet == null || mLJTempFeeClassSet.size() == 0) {
						
				// @@错误处理
				CError.buildErr(this, "查找暂交费分类失败!");
				return false;
			}
			for (int j = 1; j <= mLJTempFeeClassSet.size(); j++) {
				LJTempFeeClassSchema mLJTempFeeClassSchema = mLJTempFeeClassSet.get(j);
				mLJTempFeeClassSchema.setConfDate(CurrentDate);
				mLJTempFeeClassSchema.setConfFlag("1");
				mLJTempFeeClassSchema.setModifyDate(this.CurrentDate);
				mLJTempFeeClassSchema.setModifyTime(this.CurrentTime);
				tLJTempFeeClassSet.add(mLJTempFeeClassSchema);
			}
		}
		/**
		 * ---------------------------------------------------------------------BEG
		 * NO.3.1: 应收总表转实收总表,并删除应收总表记录
		 * ----------------------------------------------------------------------
		 */
		LJAPaySet tLJAPaySet = new LJAPaySet();
		LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
		LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();

		LJSPayClaimSet tLJSPayClaimSet = new LJSPayClaimSet();
		LJAPayClaimSet tLJAPayClaimSet = new LJAPayClaimSet();
		


		LJAGetClaimSet mLJAGetClaimSet = new LJAGetClaimSet();
		for (int j = 1; j <= tLJSPaySet.size(); j++) {
			
			//String tPayNo = PubFun1.CreateMaxNo("PayNo", tLJTempFeeClassSet.get(1).getManageCom());
			
//			String tPayNo = tLJSPaySet.get(j).getSerialNo();
			String tPayNo = PubFun1.CreateMaxNo("GETNO", PubFun.getNoLimit(tLJSPaySet.get(j).getManageCom())); // 生成实付号
			
			LJAPaySchema tLJAPaySchema = new LJAPaySchema();
			mReflections.transFields(tLJAPaySchema, tLJSPaySet.get(j));

			tLJAPaySchema.setPayNo(tPayNo);
			tLJAPaySchema.setIncomeNo(tLJSPaySet.get(j).getOtherNo());
			tLJAPaySchema.setIncomeType(tLJSPaySet.get(j).getOtherNoType());
			tLJAPaySchema.setSumActuPayMoney(tLJSPaySet.get(j)
					.getSumDuePayMoney());
			tLJAPaySchema.setEnterAccDate(tLJTempFeeSet.get(1)
					.getEnterAccDate());
			tLJAPaySchema.setConfDate(this.CurrentDate);
			tLJAPaySchema.setOperator(mG.Operator);
			tLJAPaySchema.setMakeDate(this.CurrentDate);
			tLJAPaySchema.setMakeTime(this.CurrentTime);
			tLJAPaySchema.setModifyDate(this.CurrentDate);
			tLJAPaySchema.setModifyTime(this.CurrentTime);
			tLJAPaySchema.setTax(tLJSPaySet.get(j).getTax());
			tLJAPaySchema.setTaxAmount(tLJSPaySet.get(j).getTaxAmount());
			tLJAPaySchema.setNetAmount(tLJSPaySet.get(j).getNetAmount());

			tLJAPaySet.add(tLJAPaySchema);

			/**
			 * -----------------------------------------------------------------BEG
			 * NO.3.2: 个人应收表转个人实收表,并删除个人应收表记录 如果有记录才回销,没有则不做回销 回退交费不用,主要为二核加费使用
			 * ------------------------------------------------------------------
			 */
			//回滚不处理二核记录
/*			LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
			tLJSPayPersonDB.setGetNoticeNo(tLJSPaySet.get(j).getGetNoticeNo());
			tLJSPayPersonSet = tLJSPayPersonDB.query();
			if (tLJSPayPersonSet != null && tLJSPayPersonSet.size() != 0) {
				for (int i = 1; i <= tLJSPayPersonSet.size(); i++) {
					LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
					mReflections.transFields(tLJAPayPersonSchema,
							tLJSPayPersonSet.get(i));

					tLJAPayPersonSchema.setPayNo(tPayNo);
					tLJAPayPersonSchema.setEnterAccDate(tLJTempFeeSet.get(1)
							.getEnterAccDate());
					tLJAPayPersonSchema.setConfDate(this.CurrentDate);
					tLJAPayPersonSchema.setOperator(mG.Operator);
					tLJAPayPersonSchema.setMakeDate(this.CurrentDate);
					tLJAPayPersonSchema.setMakeTime(this.CurrentTime);
					tLJAPayPersonSchema.setModifyDate(this.CurrentDate);
					tLJAPayPersonSchema.setModifyTime(this.CurrentTime);

					tLJAPayPersonSet.add(tLJAPayPersonSchema);
				}
			}
*/
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.1 核销理赔收费表(应收)
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tGetNoticeNo = StrTool.cTrim(tLJSPaySet.get(j)
					.getGetNoticeNo());
			LJSPayClaimDB tLJSPayClaimDB = new LJSPayClaimDB();
			tLJSPayClaimDB.setGetNoticeNo(tGetNoticeNo);
			tLJSPayClaimSet = tLJSPayClaimDB.query();

			if (tLJSPayClaimDB.mErrors.needDealError()) {
				mErrors.addOneError("通知书号[" + tGetNoticeNo
						+ "]的理赔应收收费信息没有取到!!!"
						+ tLJSPayClaimDB.mErrors.getError(0).errorMessage);
				return false;
			}

			for (int i = 1; i <= tLJSPayClaimSet.size(); i++) {
				LJSPayClaimSchema tLJSPayClaimSchema = tLJSPayClaimSet.get(i);
				LJAPayClaimSchema tLJAPayClaimSchema = new LJAPayClaimSchema();
				mReflections
						.transFields(tLJAPayClaimSchema, tLJSPayClaimSchema);
				tLJAPayClaimSchema.setActuGetNo(tPayNo);
				tLJAPayClaimSchema.setEnterAccDate(tLJTempFeeSet.get(1)
						.getEnterAccDate());
				tLJAPayClaimSchema.setConfDate(this.CurrentDate);
				tLJAPayClaimSchema.setOperator(mG.Operator);
				tLJAPayClaimSchema.setMakeDate(this.CurrentDate);
				tLJAPayClaimSchema.setMakeTime(this.CurrentTime);
				tLJAPayClaimSchema.setModifyDate(this.CurrentDate);
				tLJAPayClaimSchema.setModifyTime(this.CurrentTime);
				tLJAPayClaimSet.add(tLJAPayClaimSchema);
			}			

			// 回置确认日期到ljagetclaim
//			LJAGetClaimDB tLJAGetClaimDB = new LJAGetClaimDB();
//			tLJAGetClaimDB.setActuGetNo(tLJSPaySet.get(j).getSerialNo());
//			LJAGetClaimSet tLJAGetClaimSet = tLJAGetClaimDB.query();
//			if (tLJAGetClaimDB.mErrors.needDealError()) {
//				mErrors.addOneError("实付号[" + tGetNoticeNo + "]的理赔实付明细信息没有取到!"
//						+ tLJAGetClaimDB.mErrors.getError(0).errorMessage);
//				return false;
//			}
//			if (tLJAGetClaimSet != null) {
//				// 置上确认日期
//				for (int i = 1; i <= tLJAGetClaimSet.size(); i++) {
//					tLJAGetClaimSet.get(i).setOPConfirmCode(mG.Operator);
//					tLJAGetClaimSet.get(i).setOPConfirmDate(CurrentDate);
//					tLJAGetClaimSet.get(i).setEnterAccDate(this.CurrentDate);
//					tLJAGetClaimSet.get(i).setConfDate(this.CurrentDate);
//					tLJAGetClaimSet.get(i).setModifyDate(this.CurrentDate);
//					tLJAGetClaimSet.get(i).setModifyTime(this.CurrentTime);
//				}
//			}

			// 循环赔付实付表数据，将财务信息冲成一笔负记录 
			LJAGetClaimDB tLJAGetClaimDB = new LJAGetClaimDB();
			tLJAGetClaimDB.setActuGetNo(tLJSPaySet.get(j).getSerialNo()); // 实收号
			tLJAGetClaimDB.setOtherNo(mClmNo); // 赔案号
			tLJAGetClaimDB.setOtherNoType("5");
			LJAGetClaimSet tLJAGetClaimSet = tLJAGetClaimDB.query();
			
			for (int k = 1; k <= tLJAGetClaimSet.size(); k++) {
				LJAGetClaimSchema tLJAGetClaimSchema = tLJAGetClaimSet.get(k);
				
				tLJAGetClaimSchema.setGetNoticeNo(tLJAGetClaimSchema.getActuGetNo()); // 给付通知书号码--用于保存应收汇总号
				tLJAGetClaimSchema.setActuGetNo(tPayNo); // 明细表的实付号
				
				tLJAGetClaimSchema.setPay(-tLJAGetClaimSchema.getPay());
				tLJAGetClaimSchema.setTaxAmount(-tLJAGetClaimSchema.getTaxAmount());
				tLJAGetClaimSchema.setNetAmount(-tLJAGetClaimSchema.getNetAmount());
				tLJAGetClaimSchema.setTax(tLJAGetClaimSchema.getTax());
				
				tLJAGetClaimSchema.setOperator(mG.Operator);				
				tLJAGetClaimSchema.setOPConfirmCode(mG.Operator);
				tLJAGetClaimSchema.setOPConfirmDate(CurrentDate);
				tLJAGetClaimSchema.setOPConfirmTime(CurrentTime);
				
				tLJAGetClaimSchema.setEnterAccDate(CurrentDate);
				tLJAGetClaimSchema.setConfDate(CurrentDate);
				
				tLJAGetClaimSchema.setMakeDate(CurrentDate);
				tLJAGetClaimSchema.setMakeTime(CurrentTime);
				tLJAGetClaimSchema.setModifyDate(CurrentDate);
				tLJAGetClaimSchema.setModifyTime(CurrentTime);
				
				mLJAGetClaimSet.add(tLJAGetClaimSchema);
			}
		}
		
		map.put(mLJAGetClaimSet, "DELETE&INSERT");
		
		map.put(tLJTempFeeSet, "DELETE&INSERT");
		map.put(tLJTempFeeClassSet, "DELETE&INSERT");

		map.put(tLJSPaySet, "DELETE");
		map.put(tLJSPayPersonSet, "DELETE");

		map.put(tLJAPaySet, "DELETE&INSERT");
		map.put(tLJAPayPersonSet, "DELETE&INSERT");

		map.put(tLJSPayClaimSet, "DELETE");
		map.put(tLJAPayClaimSet, "DELETE&INSERT");

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错!");
			return false;
		}
		return true;
	}

}
