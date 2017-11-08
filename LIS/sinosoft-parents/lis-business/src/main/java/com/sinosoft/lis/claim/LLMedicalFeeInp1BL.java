/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.util.HashMap;

import com.sinosoft.lis.db.LLCaseReceiptDB;
import com.sinosoft.lis.db.LLCommendHospitalDB;
import com.sinosoft.lis.db.LLFeeMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLCaseReceiptSchema;
import com.sinosoft.lis.schema.LLCommendHospitalSchema;
import com.sinosoft.lis.schema.LLFeeMainSchema;
import com.sinosoft.lis.vschema.LLCaseReceiptSet;
import com.sinosoft.lis.vschema.LLCommendHospitalSet;
import com.sinosoft.lis.vschema.LLFeeMainSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:提交理赔医疗单证-门诊信息类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author:续涛
 * @version 1.0
 */

public class LLMedicalFeeInp1BL {
private static Logger logger = Logger.getLogger(LLMedicalFeeInp1BL.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private MMap map = new MMap();
	/** 往后面传输的数据库操作 */
	private MMap mapDel = new MMap();
	/** 执行删除的数据库操作，放在最后 */

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();

	// private LLFeeMainDB mLLFeeMainDB = new LLFeeMainDB();
	// private LLCaseReceiptDB mLLCaseReceiptDB = new LLCaseReceiptDB();

	private LLFeeMainSchema mLLFeeMainSchema = new LLFeeMainSchema();
	private LLCaseReceiptSchema mLLCaseReceiptSchema = new LLCaseReceiptSchema();

	public LLMedicalFeeInp1BL() {
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
		logger.debug("----------LLMedicalFeeInp1BL Begin----------");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 检查数据合法性
		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			CError.buildErr(this, "数据提交失败!");
			return false;
		}
		mInputData = null;

		logger.debug("----------LLMedicalFeeInp1BL End----------");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {

		mInputData = cInputData;
		this.mOperate = cOperate;

		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLLFeeMainSchema = (LLFeeMainSchema) mInputData.getObjectByObjectName(
				"LLFeeMainSchema", 0);
		mLLCaseReceiptSchema = (LLCaseReceiptSchema) mInputData
				.getObjectByObjectName("LLCaseReceiptSchema", 0);

		return true;
	}

	/**
	 * 校验传入的信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

		if (mGlobalInput == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		String tOperator = mGlobalInput.Operator;
		if (tOperator == null || tOperator.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据失败!");
			return false;
		}

		if (mLLFeeMainSchema == null || mLLCaseReceiptSchema == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输账单信息失败!");
			return false;
		}

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		// 进行医疗费用的录入
		if (this.mOperate.equals("INSERT")) {
			if (!medicalInsert()) {
				return false;
			}
		}
		

		// 进行医疗费用的删除
		if (this.mOperate.equals("DELETE")) {
			
			LLCaseReceiptDB tLLCaseReceiptDB = new LLCaseReceiptDB();
			tLLCaseReceiptDB.setClmNo(mLLCaseReceiptSchema.getClmNo()); // 赔案号
			tLLCaseReceiptDB.setCaseNo(mLLCaseReceiptSchema.getCaseNo()); // 分案号
			tLLCaseReceiptDB.setMainFeeNo(mLLCaseReceiptSchema.getMainFeeNo()); // 账单号
			tLLCaseReceiptDB.setFeeDetailNo(mLLCaseReceiptSchema.getFeeDetailNo()); // 医院编号

			LLCaseReceiptSet tLLCaseReceiptSet = new LLCaseReceiptSet();
			tLLCaseReceiptSet = tLLCaseReceiptDB.query();
			if (tLLCaseReceiptSet == null && tLLCaseReceiptSet.size() == 0) {
				// @@错误处理
				CError.buildErr(this,"查询LLCaseReceipt表失败!");
				return false;
			}
			else {
				map.put(tLLCaseReceiptSet, "DELETE");
			}
			
			/*
			 * 2008-10-13 zhangzheng 
			 * 如果一个案件下同一个被保险人的账单记录在LLCaseReceipt(账单子表)只存在一条记录,则连带着将LLFeeMain(账单主表)相关出险人的记录删除,否则只删除LLCaseReceipt记录
			 */
			LLCaseReceiptDB pLLCaseReceiptDB = new LLCaseReceiptDB();
			pLLCaseReceiptDB.setClmNo(mLLCaseReceiptSchema.getClmNo()); // 赔案号
			pLLCaseReceiptDB.setCaseNo(mLLCaseReceiptSchema.getCaseNo()); // 分案号
			pLLCaseReceiptDB.setMainFeeNo(mLLCaseReceiptSchema.getMainFeeNo()); // 账单号
			pLLCaseReceiptDB.setCustomerNo(mLLCaseReceiptSchema.getCustomerNo());//客户号

			LLCaseReceiptSet pLLCaseReceiptSet = new LLCaseReceiptSet();
			pLLCaseReceiptSet = pLLCaseReceiptDB.query();
			if (pLLCaseReceiptSet == null && pLLCaseReceiptSet.size() == 0) {
				// @@错误处理
				CError.buildErr(this,"查询LLCaseReceipt表失败!");
				return false;
			}
			else if(pLLCaseReceiptSet.size()==1)
			{
				// 进行账单信息主表的数据查询
				LLFeeMainDB pLLFeeMainDB = new LLFeeMainDB();

				pLLFeeMainDB.setClmNo(pLLCaseReceiptDB.getClmNo()); // 赔案号
				pLLFeeMainDB.setCaseNo(pLLCaseReceiptDB.getCaseNo()); // 分案号
				pLLFeeMainDB.setCustomerNo(pLLCaseReceiptDB.getCustomerNo()); // 客户号
				pLLFeeMainDB.setMainFeeNo(pLLCaseReceiptDB.getMainFeeNo()); // 账单号

				LLFeeMainSet pLLFeeMainSet = new LLFeeMainSet();
				pLLFeeMainSet = pLLFeeMainDB.query();
				// 或者是 tLLFeeMainSet.set(tLLFeeMainDB.query());

				if (pLLFeeMainDB.mErrors.needDealError() == true) {
					// @@错误处理
					CError.buildErr(this,"账单信息主表查询失败!");
					return false;
				}
				else{
					map.put(pLLFeeMainSet, "DELETE");
				}
				
				
				/*
				 *释放建立的强引用，方便jvm的GC 
				 */
				pLLFeeMainDB=null;
				pLLFeeMainSet=null;
			}

			pLLCaseReceiptSet=null;
			tLLCaseReceiptSet=null;
			pLLCaseReceiptDB=null;
			tLLCaseReceiptDB=null;
		}
		return true;
	}
	
	

	/**
	 * 进行医疗费用的录入
	 * 
	 * @return
	 */
	private boolean medicalInsert() {
		boolean tReturn = false;
		// 进行账单信息主表的数据查询
		LLFeeMainDB tLLFeeMainDB = new LLFeeMainDB();

		tLLFeeMainDB.setClmNo(mLLFeeMainSchema.getClmNo()); // 赔案号
		tLLFeeMainDB.setCaseNo(mLLFeeMainSchema.getCaseNo()); // 分案号
		tLLFeeMainDB.setCustomerNo(mLLFeeMainSchema.getCustomerNo()); // 客户号
		tLLFeeMainDB.setMainFeeNo(mLLFeeMainSchema.getMainFeeNo().trim()); // 账单号

		LLFeeMainSet tLLFeeMainSet = new LLFeeMainSet();
		tLLFeeMainSet = tLLFeeMainDB.query();
		// 或者是 tLLFeeMainSet.set(tLLFeeMainDB.query());

		if (tLLFeeMainDB.mErrors.needDealError() == true) {
			// @@错误处理
			CError.buildErr(this,"账单信息主表查询失败!");
			return false;
		}

		// 如果账单信息主表无数据，则添加主表数据
		if (tLLFeeMainSet == null || tLLFeeMainSet.size() == 0) {
			LLFeeMainSchema tLLFeeMainSchema = new LLFeeMainSchema();

			tLLFeeMainSchema.setClmNo(mLLFeeMainSchema.getClmNo());
			tLLFeeMainSchema.setCaseNo(mLLFeeMainSchema.getCaseNo());
			tLLFeeMainSchema.setCustomerNo(mLLFeeMainSchema.getCustomerNo());
			tLLFeeMainSchema
					.setHospitalCode(mLLFeeMainSchema.getHospitalCode());
			tLLFeeMainSchema.setMainFeeNo(mLLFeeMainSchema.getMainFeeNo().trim());

			// 查询医院名称和等级
			LLCommendHospitalDB tLLCommendHospitalDB = new LLCommendHospitalDB();
			tLLCommendHospitalDB.setHospitalCode(mLLFeeMainSchema.getHospitalCode());
			
			LLCommendHospitalSet tLLCommendHospitalSet = tLLCommendHospitalDB.query();
			LLCommendHospitalSchema tLLCommendHospitalSchema = tLLCommendHospitalSet.get(1);
			
			tLLFeeMainSchema.setHospitalName(tLLCommendHospitalSchema.getHospitalName());
			tLLFeeMainSchema.setHospitalGrade(tLLCommendHospitalSchema.getHosAtti());

			// // 生成帐单号更改为手工录入
			// String strNoLimit = PubFun.getNoLimit(this.mGlobalInput.ComCode);
			// String tMainFeeNo = PubFun1.CreateMaxNo("MainFeeNo", 10);
			// tLLFeeMainSchema.setMainFeeNo(tMainFeeNo);

			tLLFeeMainSchema.setOperator(this.mGlobalInput.Operator);
			tLLFeeMainSchema.setMngCom(this.mGlobalInput.ManageCom);
			tLLFeeMainSchema.setMakeDate(this.CurrentDate);
			tLLFeeMainSchema.setMakeTime(this.CurrentTime);
			tLLFeeMainSchema.setModifyDate(this.CurrentDate);
			tLLFeeMainSchema.setModifyTime(this.CurrentTime);

			map.put(tLLFeeMainSchema, "INSERT");

			// 如果账单信息主表无数据，则添加明细表数据
			LLCaseReceiptSchema tLLCaseReceiptSchema = new LLCaseReceiptSchema();
			tLLCaseReceiptSchema.setClmNo(mLLFeeMainSchema.getClmNo());
			tLLCaseReceiptSchema.setCaseNo(mLLFeeMainSchema.getCaseNo());
			tLLCaseReceiptSchema.setRgtNo(mLLFeeMainSchema.getCaseNo());
			tLLCaseReceiptSchema.setMainFeeNo(mLLFeeMainSchema.getMainFeeNo().trim());

			// 生成账单费用明细流水号
			String tFeeDetailNo = PubFun1.CreateMaxNo("FeeDetailNo", 10);
			tLLCaseReceiptSchema.setFeeDetailNo(tFeeDetailNo);

			tLLCaseReceiptSchema.setFeeItemType(mLLCaseReceiptSchema.getFeeItemType());//项目类型
			tLLCaseReceiptSchema.setFeeItemCode(mLLCaseReceiptSchema.getFeeItemCode());//费用类型编码
			tLLCaseReceiptSchema.setFeeItemName(mLLCaseReceiptSchema.getFeeItemName());//费用类型名称
			tLLCaseReceiptSchema.setFee(mLLCaseReceiptSchema.getFee());//费用
			tLLCaseReceiptSchema.setStartDate(mLLCaseReceiptSchema.getStartDate());//开始日期
			tLLCaseReceiptSchema.setEndDate(mLLCaseReceiptSchema.getEndDate());//结束日期
			tLLCaseReceiptSchema.setCustomerNo(mLLFeeMainSchema.getCustomerNo());//账单客户号

			// 设置住院天数
			int tDay = PubFun.calInterval(mLLCaseReceiptSchema.getStartDate(),
					mLLCaseReceiptSchema.getEndDate(), "D");
			logger.debug("案件:"+mLLFeeMainSchema.getClmNo()+"住院天数:"+tDay);

			if (tDay == 0) {
				tDay = 1;
			}
			tLLCaseReceiptSchema.setDayCount(String.valueOf(tDay));

			tLLCaseReceiptSchema.setDealFlag(mLLCaseReceiptSchema.getDealFlag());
			
			tLLCaseReceiptSchema.setCurrency(mLLCaseReceiptSchema.getCurrency());
			tLLCaseReceiptSchema.setAdjSum(mLLCaseReceiptSchema.getFee());//初始化时设置调整金额为初始金额
			tLLCaseReceiptSchema.setSelfAmnt(mLLCaseReceiptSchema.getSelfAmnt());//自费/自付金额
			tLLCaseReceiptSchema.setAdjReason(mLLCaseReceiptSchema.getAdjReason());
			tLLCaseReceiptSchema.setAdjRemark(mLLCaseReceiptSchema.getAdjRemark());

			tLLCaseReceiptSchema.setOperator(this.mGlobalInput.Operator);
			tLLCaseReceiptSchema.setMngCom(this.mGlobalInput.ManageCom);
			tLLCaseReceiptSchema.setMakeDate(this.CurrentDate);
			tLLCaseReceiptSchema.setMakeTime(this.CurrentTime);
			tLLCaseReceiptSchema.setModifyDate(this.CurrentDate);
			tLLCaseReceiptSchema.setModifyTime(this.CurrentTime);
			

			map.put(tLLCaseReceiptSchema, "INSERT");
			tReturn = true;
		}

		else if (tLLFeeMainSet.size() == 1) {
			// 如果账单信息主表有数据，则添加明细表数据
			LLCaseReceiptSchema tLLCaseReceiptSchema = new LLCaseReceiptSchema();
			tLLCaseReceiptSchema.setClmNo(mLLCaseReceiptSchema.getClmNo());
			tLLCaseReceiptSchema.setCaseNo(mLLCaseReceiptSchema.getCaseNo());
			tLLCaseReceiptSchema.setRgtNo(mLLCaseReceiptSchema.getCaseNo());
			tLLCaseReceiptSchema.setMainFeeNo(mLLCaseReceiptSchema.getMainFeeNo().trim());

			// 生成账单费用明细流水号
			String tFeeDetailNo = PubFun1.CreateMaxNo("FeeDetailNo", 10);
			tLLCaseReceiptSchema.setFeeDetailNo(tFeeDetailNo);

			tLLCaseReceiptSchema.setFeeItemType(mLLCaseReceiptSchema.getFeeItemType());//项目类型
			tLLCaseReceiptSchema.setFeeItemCode(mLLCaseReceiptSchema.getFeeItemCode());//费用类型编码
			tLLCaseReceiptSchema.setFeeItemName(mLLCaseReceiptSchema.getFeeItemName());//费用类型名称
			tLLCaseReceiptSchema.setFee(mLLCaseReceiptSchema.getFee());//费用

			tLLCaseReceiptSchema.setStartDate(mLLCaseReceiptSchema.getStartDate());//开始日期
			tLLCaseReceiptSchema.setEndDate(mLLCaseReceiptSchema.getEndDate());//结束日期
			tLLCaseReceiptSchema.setCustomerNo(mLLCaseReceiptSchema.getCustomerNo());//账单客户号

			// 设置住院天数
			int tDay = PubFun.calInterval(mLLCaseReceiptSchema.getStartDate(),
					mLLCaseReceiptSchema.getEndDate(), "D");
			if (tDay == 0) {
				tDay = 1;
			}
			tLLCaseReceiptSchema.setDayCount(String.valueOf(tDay));

			tLLCaseReceiptSchema.setDealFlag(mLLCaseReceiptSchema.getDealFlag());
			tLLCaseReceiptSchema.setCurrency(mLLCaseReceiptSchema.getCurrency());
			tLLCaseReceiptSchema.setAdjSum(mLLCaseReceiptSchema.getFee());//初始化时设置调整金额为初始金额
			tLLCaseReceiptSchema.setSelfAmnt(mLLCaseReceiptSchema.getSelfAmnt());//自费/自付金额
			tLLCaseReceiptSchema.setAdjReason(mLLCaseReceiptSchema.getAdjReason());
			tLLCaseReceiptSchema.setAdjRemark(mLLCaseReceiptSchema.getAdjRemark());
			
			tLLCaseReceiptSchema.setOperator(this.mGlobalInput.Operator);
			tLLCaseReceiptSchema.setMngCom(this.mGlobalInput.ManageCom);
			tLLCaseReceiptSchema.setMakeDate(this.CurrentDate);
			tLLCaseReceiptSchema.setMakeTime(this.CurrentTime);
			tLLCaseReceiptSchema.setModifyDate(this.CurrentDate);
			tLLCaseReceiptSchema.setModifyTime(this.CurrentTime);

			map.put(tLLCaseReceiptSchema, "DELETE&INSERT");
			tReturn = true;
		}
		return tReturn;
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
			// mResult.add(mLLAccidentSchema);//供前台界面使用
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this,"在准备往后层处理所需要的数据时出错!");
			return false;
		}
		
		return true;
	}

	/**
	 * 提供返回前台界面的数据
	 * 
	 * @return
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 用于测试
	 * 
	 * @return
	 */
	public static void main(String[] args) {
	}
}
