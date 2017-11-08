/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLClaimDetailDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLClaimDetailSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.vschema.LLClaimDetailSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 理赔立案结论逻辑处理类
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
public class LLClaimAuditGiveTypeBL {
private static Logger logger = Logger.getLogger(LLClaimAuditGiveTypeBL.class);
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

	// 立案相关
	private LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();
	private LLClaimDetailSet mLLClaimDetailSet = new LLClaimDetailSet();

	private String mAccNo = "";
	private String mClmNo = "";
    private String mGiveType = "";

	public LLClaimAuditGiveTypeBL() {
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
		logger.debug("----------LLClaimAuditGiveTypeBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("----------LLClaimAuditGiveTypeBL after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------LLClaimAuditGiveTypeBL after checkInputData----------");
		// 进行业务处理
		if (!dealData(cOperate)) {
			return false;
		}
		logger.debug("----------LLClaimAuditGiveTypeBL after dealData----------");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------LLClaimAuditGiveTypeBL after prepareOutputData----------");
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, cOperate)) {
			// @@错误处理
			CError.buildErr(this, "数据提交失败,"+tPubSubmit.mErrors.getLastError());
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
		logger.debug("---LLClaimAuditGiveTypeBL start getInputData()...");
		// 获取页面报案信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); // 按类取值
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mLLClaimDetailSet = (LLClaimDetailSet) mInputData
				.getObjectByObjectName("LLClaimDetailSet", 0);
		mClmNo = (String) mTransferData.getValueByName("ClmNo");
		mAccNo = (String) mTransferData.getValueByName("AccNo");
        mGiveType = (String) mTransferData.getValueByName("GiveType");
        
		return true;
	}

	/**
	 * 校验传入的报案信息是否合法
	 * 
	 * @return：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		logger.debug("----------begin checkInputData----------");
		// logger.debug(mLLRegisterSchema.getAccDate());
		// try
		// {
		// //非空字段检验
		// if (mLLRegisterSchema.getAccDate() == null)//意外事故发生日期
		// {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "LLClaimAuditGiveTypeBL";
		// tError.functionName = "checkInputData";
		// tError.errorMessage = "接受的意外事故发生日期为空!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// if (mLLCaseSchema.getCustomerNo() == null)//出险人编码
		// {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "LLClaimAuditGiveTypeBL";
		// tError.functionName = "checkInputData";
		// tError.errorMessage = "接受的出险人编码为空!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// }
		// catch (Exception ex)
		// {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "LLClaimAuditGiveTypeBL";
		// tError.functionName = "checkInputData";
		// tError.errorMessage = "在校验输入的数据时出错!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		return true;
	}

	/**
	 * 数据操作类业务处理
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(String cOperate) {

		// 更新立案结论
		if (cOperate.equals("UPDATE")) {
			// 更改给付结论
			if (mLLClaimDetailSet.size() >= 1) {
				LLClaimDetailSet tLLClaimDetailSet = new LLClaimDetailSet();
				for (int i = 1; i <= mLLClaimDetailSet.size(); i++) {
					
		          	//2009-04-21 zhangzheng 保项为拒付时同时修改将给付责任表的金额置0
                	if(mGiveType.equals("1"))
                	{
                   		LLClaimDetailSchema tLLClaimDetailSchema = new LLClaimDetailSchema();
    					LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
    					tLLClaimDetailDB.setClmNo(mClmNo);
    					tLLClaimDetailDB.setCaseRelaNo(mAccNo);
    					tLLClaimDetailDB.setCaseNo(mClmNo);
    					tLLClaimDetailDB.setPolNo(mLLClaimDetailSet.get(i)
    							.getPolNo());
    					tLLClaimDetailDB.setGetDutyKind(mLLClaimDetailSet.get(i)
    							.getGetDutyKind());
    					tLLClaimDetailDB.setGetDutyCode(mLLClaimDetailSet.get(i)
    							.getGetDutyCode());
    					tLLClaimDetailDB.setDutyCode(mLLClaimDetailSet.get(i)
    							.getDutyCode());
    					tLLClaimDetailDB.setCustomerNo(mLLClaimDetailSet.get(i)
    							.getCustomerNo());
    					if(!tLLClaimDetailDB.getInfo()){
    						CError.buildErr(this, "未查询到LLClaimDetail");
    					}
    					tLLClaimDetailSchema = tLLClaimDetailDB.getSchema();
    					tLLClaimDetailSchema.setGiveType(mLLClaimDetailSet.get(i)
    							.getGiveType());
    					tLLClaimDetailSchema.setDeclineAmnt(mLLClaimDetailSet.get(i)
    							.getRealPay());
    					tLLClaimDetailSchema.setRealPay(0);
    					tLLClaimDetailSchema.setAdjReason(mLLClaimDetailSet.get(i)
    							.getAdjReason());
    					tLLClaimDetailSchema.setAdjRemark(mLLClaimDetailSet.get(i)
    							.getAdjRemark());
    					tLLClaimDetailSchema.setGiveReasonDesc(mLLClaimDetailSet
    							.get(i).getGiveReasonDesc());
    					tLLClaimDetailSchema.setGiveReason(mLLClaimDetailSet.get(i)
    							.getGiveReason());
    					tLLClaimDetailSchema.setSpecialRemark(mLLClaimDetailSet
    							.get(i).getSpecialRemark());
    					tLLClaimDetailSchema.setMngCom(mG.ManageCom);
    					tLLClaimDetailSchema.setOperator(mG.Operator);
    					tLLClaimDetailSchema.setMakeDate(CurrentDate);
    					tLLClaimDetailSchema.setMakeTime(CurrentTime);
    					tLLClaimDetailSchema.setModifyDate(CurrentDate);
    					tLLClaimDetailSchema.setModifyTime(CurrentTime);
    					tLLClaimDetailSet.add(tLLClaimDetailSchema);
                	}
                	else
                	{
                		LLClaimDetailSchema tLLClaimDetailSchema = new LLClaimDetailSchema();
    					LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
    					tLLClaimDetailDB.setClmNo(mClmNo);
    					tLLClaimDetailDB.setCaseRelaNo(mAccNo);
    					tLLClaimDetailDB.setCaseNo(mClmNo);
    					tLLClaimDetailDB.setPolNo(mLLClaimDetailSet.get(i)
    							.getPolNo());
    					tLLClaimDetailDB.setGetDutyKind(mLLClaimDetailSet.get(i)
    							.getGetDutyKind());
    					tLLClaimDetailDB.setGetDutyCode(mLLClaimDetailSet.get(i)
    							.getGetDutyCode());
    					tLLClaimDetailDB.setDutyCode(mLLClaimDetailSet.get(i)
    							.getDutyCode());
    					tLLClaimDetailDB.setCustomerNo(mLLClaimDetailSet.get(i)
    							.getCustomerNo());
    					if(!tLLClaimDetailDB.getInfo()){
    						CError.buildErr(this, "未查询到LLClaimDetail");
    					}
    					tLLClaimDetailSchema = tLLClaimDetailDB.getSchema();
    					tLLClaimDetailSchema.setGiveType(mLLClaimDetailSet.get(i)
    							.getGiveType());
    					tLLClaimDetailSchema.setRealPay(mLLClaimDetailSet.get(i)
    							.getRealPay());
    					tLLClaimDetailSchema.setAdjReason(mLLClaimDetailSet.get(i)
    							.getAdjReason());
    					tLLClaimDetailSchema.setAdjRemark(mLLClaimDetailSet.get(i)
    							.getAdjRemark());
    					tLLClaimDetailSchema.setGiveReasonDesc(mLLClaimDetailSet
    							.get(i).getGiveReasonDesc());
    					tLLClaimDetailSchema.setGiveReason(mLLClaimDetailSet.get(i)
    							.getGiveReason());
    					tLLClaimDetailSchema.setSpecialRemark(mLLClaimDetailSet
    							.get(i).getSpecialRemark());
    					tLLClaimDetailSchema.setMngCom(mG.ManageCom);
    					tLLClaimDetailSchema.setOperator(mG.Operator);
    					tLLClaimDetailSchema.setMakeDate(CurrentDate);
    					tLLClaimDetailSchema.setMakeTime(CurrentTime);
    					tLLClaimDetailSchema.setModifyDate(CurrentDate);
    					tLLClaimDetailSchema.setModifyTime(CurrentTime);
    					tLLClaimDetailSet.add(tLLClaimDetailSchema);
                	}
					
				}
				map.put(tLLClaimDetailSet, "DELETE&INSERT");
			}
		}
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
