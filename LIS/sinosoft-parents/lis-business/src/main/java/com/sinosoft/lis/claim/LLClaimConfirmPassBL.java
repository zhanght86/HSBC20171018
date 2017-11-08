/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

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
public class LLClaimConfirmPassBL {
private static Logger logger = Logger.getLogger(LLClaimConfirmPassBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	// 全局变量
	private MMap mMMap = new MMap();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private GlobalInput mG = new GlobalInput();
	TransferData mTransferData = new TransferData();
	private double tax  =0.0;  //税率
	private double taxAmount = 0.0;   //税费
	private double netAmount = 0.0;  //净费

	private LLClaimDetailSet mLLClaimDetailSet = new LLClaimDetailSet();
	// private LJAGetSet mLJAGetSet = new LJAGetSet();
	// private LJSGetSet mLJSGetSet = new LJSGetSet();
	// private LJSGetClaimSet mLJSGetClaimSet = new LJSGetClaimSet();
	// private LJAGetClaimSet mLJAGetClaimSet = new LJAGetClaimSet();
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	private Reflections mReflections = new Reflections();
	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();// 理赔公用方法类
	private LLClaimPubCheckBL mLLClaimPubCheckBL = new LLClaimPubCheckBL();
	private LLExemptSet mLLExemptHMSet = new LLExemptSet(); // 保存被豁免的记录，用于对应收记录的处理
	private List mBomList = new ArrayList();

	private PrepareBOMClaimBL mPrepareBOMClaimBL = new PrepareBOMClaimBL();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();

	private String mClmNo = "";
	private String mGrpClmNo = "";
	private String mPolNo = "";
	private String mDutyCode = "";
	private String mGetDutyCode = "";
	private String mCusNo = ""; // 客户号码
	private String mMngCom = ""; // 立案管理机构
	private String mInsDate = ""; // 出险时间
	private String mRgtDate = ""; // 立案日期
	private String mGetMode = ""; // 赔付金领取方式,:1一次统一给付,2按年金方式领取

	public LLClaimConfirmPassBL() {
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

		logger.debug("--------------------审批通过-----LLClaimConfirmPassBL测试-----开始--------------------");

		mInputData = (VData) cInputData.clone();// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		if (!getInputData()) {
			return false;
		}
		logger.debug("----------审批通过----数据校验开始----LLClaimConfirmPassBL测试---------------");

		if (!checkData()) {
			return false;
		}

		logger.debug("----------审批通过----保额校验开始----LLClaimConfirmPassBL测试---------------");

		if (!checkBalAmnt()) {
			return false;
		}

		logger.debug("----------审批通过----业务处理开始----LLClaimConfirmPassBL测试---------------");

		if (!dealData()) {
			return false;
		}
		logger.debug("----------审批通过----业务处理结束----LLClaimConfirmPassBL测试---------------");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("--------------------审批通过-----LLClaimConfirmPassBL测试-----结束--------------------");
		mInputData = null;
		return true;
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
	public boolean dealQuickClaim(VData cInputData, String cOperate) {
		logger.debug("--------------------复核确认-----LLClaimConfirmPassBL测试-----开始--------------------");

		mInputData = (VData) cInputData.clone();// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		mGrpClmNo = (String) mInputData.getObjectByObjectName("String", 0);// 获取团体赔案号
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		if (mGrpClmNo == null || mGrpClmNo.equals("")) {
			mErrors.addOneError("在LLClaimConfirmPassBL中获取团体赔案号失败!!!");
			return false;
		}
		mGetMode = "1";// 赔付金领取方式,:1一次统一给付,2按年金方式领取
		String tSQLSubRgtNo = " select rgtno from llregister where rgtobjno='"
				+ "?rgtobjno?" + "' ";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSQLSubRgtNo);
		sqlbv.put("rgtobjno", mGrpClmNo);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv);

		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			mClmNo = tSSRS.GetText(i, 1);
			logger.debug("----------复核确认-------第" + i + "次循环处理分赔="
					+ mClmNo + "---------开始----------------");
			logger.debug("----------复核确认----数据校验开始----LLClaimConfirmPassBL测试---------------");
			if (!checkData()) {
				return false;
			}

			logger.debug("----------复核确认----保额校验开始----LLClaimConfirmPassBL测试---------------");

			if (!checkBalAmnt()) {
				return false;
			}

			logger.debug("----------复核确认----业务处理开始----LLClaimConfirmPassBL测试---------------");

			this.mInsDate = mLLClaimPubFunBL.getInsDate(mClmNo);

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.1
			 * 处理LCPol，LCDuty承保的数据 续涛加,2005.11.08
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (!dealNBData()) {
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.1 进行保额冲减
			 * 赔付金领取方式:1一次统一给付,2按年金方式领取 按年金方式领取时只有一条记录，不用进行保额冲减
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (!getDealAmnt()) {
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.1
			 * 遍历LLClaimDetail表中每一个给付标志GiveType为0（给付）的保项，然后根据
			 * 给付责任编码到LMDutyGetClm表中去找AfterGet，给付后动作这个信息做如下操作
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (!getDealDutyCode()) {
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.1 生成财务数据处理
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (!getDealFinance(this.mClmNo)) {
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No9.1 删除财务备份表对应数据
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (!dealeTempData()) {
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No10.1 产生的数据插入到打印管理表
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (!dealData09()) {
				return false;
			}
			logger.debug("----------复核确认----第" + i + "次循环处理分赔=" + mClmNo
					+ "---------结束！--------------");
		}

		logger.debug("----------复核确认----业务处理结束----LLClaimConfirmPassBL测试---------------");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("--------------------复核确认-----LLClaimConfirmPassBL测试-----结束--------------------");
		mInputData = null;
		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("---LLClaimConfirmPassBL start getInputData()...");
		// 获取页面报案信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); // 按类取值
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		mClmNo = (String) mTransferData.getValueByName("RptNo");

		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－－－保额冲减校验－－－续涛加－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 对数据进行校验，校验赔案层面是否有结论，保项层面的结论是否为空等
	 */
	private boolean checkData() {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 判断该赔案的给付结论是否为空
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimDB tLLClaimDB = new LLClaimDB();
		tLLClaimDB.setClmNo(mClmNo);
		LLClaimSet tLLClaimSet = tLLClaimDB.query();

		if (tLLClaimSet.size() == 1) {
			LLClaimSchema tLLClaimSchema = tLLClaimSet.get(1);
			String tGiveType = StrTool.cTrim(tLLClaimSchema.getGiveType());
			if (tGiveType.equals("") || tGiveType.length() == 0) {
				mErrors.addOneError("赔案的赔付结论为空，不能通过审批!!!");
				return false;
			}
		} else {
			mErrors.addOneError("赔案层面无赔付数据，不能通过审批!!!");
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 判断该赔案的保项层面的给付结论是否为空
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
		tLLClaimDetailDB.setClmNo(mClmNo);
		LLClaimDetailSet tLLClaimDetailSet = tLLClaimDetailDB.query();
		for (int i = 1; i <= tLLClaimDetailSet.size(); i++) {
			LLClaimDetailSchema tLLClaimDetailSchema = tLLClaimDetailSet.get(i);
			String tGiveType = StrTool
					.cTrim(tLLClaimDetailSchema.getGiveType());
			if (tGiveType.equals("") || tGiveType.length() == 0) {
				mErrors.addOneError("赔案保项层面赔付结论为空，不能通过审批!!!");
				return false;
			}
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.1 取出立案信息 及
		 * 赔付金领取方式:1一次统一给付,2按年金方式领取
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		mLLRegisterSchema = mLLClaimPubFunBL.getLLRegister(mClmNo);
		if (mLLRegisterSchema == null) {
			mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.2 如果按年金方式领取，判断是否符合领取条件
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		mGetMode = StrTool.cTrim(mLLRegisterSchema.getGetMode());

		if (mGetMode.equals("2")) {
			if (!mLLClaimPubCheckBL.checkClmToPension(mClmNo)) {
				mErrors.copyAllErrors(mLLClaimPubCheckBL.mErrors);
				return false;
			}
		}
		
		
		/**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 实付信息校验
         * 主表和子表记录必须一致
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
		
        /**
		 * 2009-06-25 zhangzheng 
		 * 校验受益人分配产生的应付子表和总表金额必须保持一致
		 */
		LJSGetSet tLJSGetSet=new LJSGetSet();
		LJSGetDB tLJSGetDB= new LJSGetDB();
		tLJSGetDB.setOtherNo(mClmNo);
		tLJSGetSet=tLJSGetDB.query();		
		
		String sql="";
		SSRS tSSRS= null;
		ExeSQL tExeSQL = new ExeSQL();
		
		for(int i=1;i<=tLJSGetSet.size();i++)
		{
			sql = "select sum(pay) from ljsgetclaim where getnoticeno='"+"?getnoticeno?"+"'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(sql);
			sqlbv1.put("getnoticeno", tLJSGetSet.get(i).getGetNoticeNo());
			tSSRS = new SSRS();
			
			tSSRS = tExeSQL.execSQL(sqlbv1);
			
			if(tSSRS.getMaxRow()==0)
			{
				tSSRS=null;
				sql=null;
				CError.buildErr(this, "给付通知书号:"+tLJSGetSet.get(i).getGetNoticeNo()+"查询不到子表的应付信息,不能结案保存,请先查明原因!");
				return false;
			}
			
			if(tLJSGetSet.get(i).getSumGetMoney()!=Double.parseDouble(tSSRS.GetText(1,1)))
			{
				tSSRS=null;
				sql=null;
				CError.buildErr(this, "给付通知书号:"+tLJSGetSet.get(i).getGetNoticeNo()+"的主子表金额不一致,不能结案保存,请先查明原因!");
				return false;
			}			
			
			tSSRS=null;
			sql=null;
		}
		
		tLJSGetSet=null;
		tExeSQL=null;
		tLJSGetDB=null;

		return true;
	}

	/**
	 * 保额冲减前进行校验，判断是否够冲，主要针对医疗附加险
	 * 
	 * @return
	 */
	private boolean checkBalAmnt() {
		logger.debug("---------------------------------进行保额校验----开始---------------------------------\n");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 判断该赔案是否有给付记录
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
		String tQuerySql = "";
		tQuerySql = "select a.* from LLClaimDetail a,LLClaim b "
				+ "where a.clmno = b.clmno "
				+ "and a.givetype = '0' and b.givetype = '0'"
				+ "and a.clmno = '" + "?clmno?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tQuerySql);
		sqlbv2.put("clmno", mClmNo);
		mLLClaimDetailSet = tLLClaimDetailDB.executeQuery(sqlbv2);

		logger.debug("---------------------------------------------------------------------");
		logger.debug("--处理赔案的给付保项[" + mLLClaimDetailSet.size() + "]:["
				+ tQuerySql + "]");
		logger.debug("---------------------------------------------------------------------");

		if (tLLClaimDetailDB.mErrors.needDealError()) {
			mErrors.addOneError("查询赔案" + mClmNo + "给付保项发生错误，不能执行审批通过处理!!!"
					+ tLLClaimDetailDB.mErrors.getError(0).errorMessage);
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 和LCGet表的有效保额进行比较
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		for (int i = 1; i <= mLLClaimDetailSet.size(); i++) {
			LLClaimDetailSchema tLLClaimDetailSchema = mLLClaimDetailSet.get(i);

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.2 查询领取项表
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LCGetDB tLCGetDB = new LCGetDB();
			String tSql1 = "select * from LCGet where 1=1 " + " and PolNo = '"
					+ "?PolNo?" + "'"
					+ " and DutyCode = '" + "?DutyCode?"
					+ "'" + " and GetDutyCode = '"
					+ "?GetDutyCode?" + "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tSql1);
			sqlbv3.put("PolNo", tLLClaimDetailSchema.getPolNo());
			sqlbv3.put("DutyCode", tLLClaimDetailSchema.getDutyCode());
			sqlbv3.put("GetDutyCode", tLLClaimDetailSchema.getGetDutyCode());
			LCGetSet tLCGetSet = tLCGetDB.executeQuery(sqlbv3);

			logger.debug("---------------------------------------------------------------------");
			logger.debug("--查询承保领取项[" + tLCGetSet.size() + "]:[" + tSql1
					+ "]");
			logger.debug("---------------------------------------------------------------------");

			if (tLCGetSet.size() != 1 || tLCGetDB.mErrors.needDealError()) {
				mErrors.addOneError("查询赔案" + mClmNo
						+ "承保领取项数据时发生错误，不能执行审批通过处理!!!");
				return false;
			}

			LCGetSchema tLCGetSchema = tLCGetSet.get(1);

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.3
			 * 取出各种标志位,进行保额冲减的判断
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tRiskCode = StrTool
					.cTrim(tLLClaimDetailSchema.getRiskCode());
			String tPosFlag = StrTool.cTrim(tLLClaimDetailSchema.getPosFlag());// 0未做保全,1做过保全
			String tPosPosEdorNo = StrTool.cTrim(tLLClaimDetailSchema
					.getPosEdorNo());// 保全批单号
			String tNBPolNo = StrTool.cTrim(tLLClaimDetailSchema.getNBPolNo());// 承保时的保单号

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.4 判断保项剩余的有效保额
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (mLLClaimPubFunBL.getLMRiskSort(tRiskCode, "7")) {
				double tRealPay = tLLClaimDetailSchema.getRealPay();// 赔案给付金额
				double tBalAmnt = 0;

				String tGetDutyName = mLLClaimPubFunBL
						.getGetDutyName(tLCGetSchema);
				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.4 对做过保全的进行处理
				 * 0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化 如果tNBAmnt > tPosAmnt
				 * {[做保全前]--5万 > 减保后的保额[做保全后]--2万}
				 * 
				 * tRealPay > tBalAmnt {用给付金额 与 做保全前的有效保额相比较}
				 * 
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				if (tPosFlag.equals("2")) {
					LPGetSchema tLPGetSchema = null;
					tLPGetSchema = mLLClaimPubFunBL.getLPGet(tPosPosEdorNo,
							tNBPolNo, tLCGetSchema);
					if (tLPGetSchema == null) {
						return false;
					}
					double tNBAmnt = Arith.round(tLPGetSchema.getStandMoney(),
							2); // 承保时的保额[做保全前]--5万
					double tPosAmnt = Arith.round(tLCGetSchema.getStandMoney(),
							2); // 减保后的保额[做保全后]--2万
					tBalAmnt = Arith.round(tLPGetSchema.getStandMoney()
							- tLPGetSchema.getSumMoney(), 2);// 做保全前的有效保额

					if ((tNBAmnt > tPosAmnt) && (tRealPay > tBalAmnt)) {
						mErrors.addOneError("赔案" + mClmNo + "保单险种号:["
								+ tLPGetSchema.getPolNo()
								+ "]做过保全减保,减保前的有效保额为:[" + tBalAmnt + "],不足以支付"
								+ tGetDutyName
								+ "的给付金额,请先到审核阶段进行金额调整,然后再进行审批确认操作!");
						return false;
					}
				} else {
					tBalAmnt = Arith.round(tLCGetSchema.getStandMoney()
							- tLCGetSchema.getSumMoney(), 2);// 做保全前的有效保额
					if (tRealPay > tBalAmnt) {
						mErrors.addOneError("赔案" + mClmNo + "有效保额不足以支付"
								+ tGetDutyName + "的给付金额!");
						return false;
					}
				}
			}

		}

		logger.debug("\n---------------------------------进行保额校验----结束---------------------------------");
		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－－－保额冲减校验－－－续涛加－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 数据操作类业务处理
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		this.mInsDate = mLLClaimPubFunBL.getInsDate(mClmNo);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.1 处理LCPol，LCDuty承保的数据
		 * 续涛加,2005.11.08 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!dealNBData()) {
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.1 进行保额冲减
		 * 赔付金领取方式:1一次统一给付,2按年金方式领取 按年金方式领取时只有一条记录，不用进行保额冲减
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!mGetMode.equals("2")) {
			if (!getDealAmnt()) {
				return false;
			}
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.1
		 * 遍历LLClaimDetail表中每一个给付标志GiveType为0（给付）的保项，然后根据
		 * 给付责任编码到LMDutyGetClm表中去找AfterGet，给付后动作这个信息做如下操作
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!getDealDutyCode()) {
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.1 对于帐户型的险种处理方式【暂时先不作】
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.1 生成财务数据处理
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!getDealFinance(this.mClmNo)) {
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.1 合同处理
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!getDealLCContState()) {
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No7.1
		 * 按年金方式领取时,将理赔金转年金的数据同步到LCDuty,LCGet表中
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (mGetMode.equals("2")) {
			if (!getDealClmToPension()) {
				return false;
			}
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.1 豁免处理
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!getDealLLExempt()) {
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No9.1 删除财务备份表对应数据
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!dealeTempData()) {
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No10.1 产生的数据插入到打印管理表
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!dealData09()) {
			return false;
		}

		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－过程中－－－－非帐户保额冲减－－－－－续涛加－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 处理LCPol承保的数据
	 * 
	 */
	private boolean dealNBData() {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 更新LCPol的理赔次数
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql = "select distinct PolNo from LLClaimDetail  where 1=1 "
				+ " and clmno = '" + "?clmno?" + "'"
				+ " and GiveType in ('0','1')";
		logger.debug("------------------------------------------------------");
		logger.debug("--审批通过时处理LCPol时的SQL语句:[" + tSql + "]");
		logger.debug("------------------------------------------------------");
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tSql);
		sqlbv4.put("clmno", mClmNo);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv4);
		if (tExeSQL.mErrors.needDealError()) {
			mErrors.addOneError("查询赔案" + mClmNo
					+ "涉及的所有险种保单号出现问题,不能进行审批通过处理!!!"
					+ tExeSQL.mErrors.getError(0).errorMessage);
			return false;
		}

		LCPolSet tLCPolSaveSet = new LCPolSet();
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			String tPolNo = tSSRS.GetText(i, 1); // 保单险种号
			LCPolSchema tLCPolSchema = mLLClaimPubFunBL.getLCPol(tPolNo);
			if (tLCPolSchema == null) {
				return false;
			}
			tLCPolSchema.setClaimTimes(tLCPolSchema.getClaimTimes() + 1);
			tLCPolSaveSet.add(tLCPolSchema);
		}

		mMMap.put(tLCPolSaveSet, "UPDATE");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 更新LCDuty上的保额
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		return true;
	}

	/**
	 * 进行保额冲减 续涛,2005.08.10
	 */
	private boolean getDealAmnt() {
		logger.debug("---------------------------------进行保额冲减处理----开始---------------------------------\n");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 查询赔案给付的保项数据
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql = "select b.* from llclaim a,LLClaimDetail b where 1=1"
				+ " and a.clmno = b.clmno " + " and a.clmno = '" + "?clmno?" + "'"
				+ " and a.givetype='0'" + " and b.givetype='0'"
				+ " and substr(b.GetDutyKind,2,2) <> '09'";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tSql);
		sqlbv5.put("clmno", mClmNo);
		LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
		LLClaimDetailSet tLLClaimDetailSet = tLLClaimDetailDB
				.executeQuery(sqlbv5);

		logger.debug("------------------------------------------------------------");
		logger.debug("--处理赔案的给付保项[" + tLLClaimDetailSet.size() + "]:["
				+ tSql + "]");
		logger.debug("------------------------------------------------------------");

		if (tLLClaimDetailDB.mErrors.needDealError()) {
			this.mErrors.addOneError("查询赔案" + mClmNo + "给付保项发生错误!"
					+ tLLClaimDetailDB.mErrors.getError(0).errorMessage);
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.1 循环赔案给付的保项数据,进行保额冲减
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimDetailSchema tLLClaimDetailSchema = null;
		LCGetSet tLCGetSet = new LCGetSet();
		for (int i = 1; i <= tLLClaimDetailSet.size(); i++) {
			tLLClaimDetailSchema = tLLClaimDetailSet.get(i);

			String tRiskCode = StrTool
					.cTrim(tLLClaimDetailSchema.getRiskCode());
			String tPosFlag = StrTool.cTrim(tLLClaimDetailSchema.getPosFlag());// 0未做保全,1做过保全
			String tPosPosEdorNo = StrTool.cTrim(tLLClaimDetailSchema
					.getPosEdorNo());// 保全批单号
			String tNBPolNo = StrTool.cTrim(tLLClaimDetailSchema.getNBPolNo());// 承保时的保单号

			String tPolNo = tLLClaimDetailSchema.getPolNo();
			String tGetDutyKind = tLLClaimDetailSchema.getGetDutyKind();
			String tGetDutyCode = tLLClaimDetailSchema.getGetDutyCode();

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.2
			 * 7.理赔保额冲减，如果不冲减，继续循环 14.审批通过时冲减LCGet上的SumMoney已领金额
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (!mLLClaimPubFunBL.getLMRiskSort(tRiskCode, "14")) {
				logger.debug("------------------------------------------------------------");
				logger.debug("--险种[" + tRiskCode + "]无须冲减保额，被过滤掉");
				logger.debug("------------------------------------------------------------");
				continue;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.3 非帐户型保额冲减
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tAcctFlag = StrTool
					.cTrim(tLLClaimDetailSchema.getAcctFlag());
			if (!tAcctFlag.equals("1")) {
				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.4 本责任保额冲减
				 * 
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				LCGetSchema tLCGetSchema = new LCGetSchema();
				tLCGetSchema = getLCGet(tLLClaimDetailSchema);
				if (tLCGetSchema == null) {
					return false;
				} else {
					tLCGetSet.add(tLCGetSchema);
				}

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.5
				 * 判断是否冲减其他保项的保额
				 * 必须用CnterCalCode判断，因为豁免用到了OthCalCode,没有用到CnterCalCode。
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				LMDutyGetClmSchema tLMDutyGetClmSchema = mLLClaimPubFunBL
						.getLMDutyGetClm(tGetDutyKind, tGetDutyCode);

				if (tLMDutyGetClmSchema == null) {
					mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
					return false;
				}

				String tCnterCalCode = StrTool.cTrim(tLMDutyGetClmSchema
						.getCnterCalCode());
				if (tCnterCalCode.length() > 0) {
					/**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.6
					 * 计算出准备冲减其他责任的LCGet信息
					 * 
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					 */
					LCGetSet tOtherLCGetSet = new LCGetSet();
					tOtherLCGetSet = getOtherLCGet(tLLClaimDetailSchema,
							tLMDutyGetClmSchema);
					if (tOtherLCGetSet == null) {
						return false;
					} else {
						tLCGetSet.add(tOtherLCGetSet);
					}
				}
			}
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 帐户型保额冲减
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			else {
				/*if (!dealAccount(tLLClaimDetailSchema)) {
					return false;
				}*/
			}
		}

		mMMap.put(tLCGetSet, "UPDATE");

		logger.debug("\n---------------------------------进行保额冲减处理----结束---------------------------------");
		return true;
	}

	/**
	 * 进行本责任保额冲减
	 * 
	 * @param pLLClaimDetailSchema
	 * @return
	 */
	private LCGetSchema getLCGet(LLClaimDetailSchema pLLClaimDetailSchema) {

		logger.debug("---------------------------------进行本责任保额冲减----开始---------------------------------");

		String tPolNo = pLLClaimDetailSchema.getPolNo();
		String tDutyCode = pLLClaimDetailSchema.getDutyCode();
		String tGetDutyCode = pLLClaimDetailSchema.getGetDutyCode();

		LCGetDB tLCGetDB = new LCGetDB();
		tLCGetDB.setPolNo(tPolNo);
		tLCGetDB.setDutyCode(tDutyCode);
		tLCGetDB.setGetDutyCode(tGetDutyCode);

		LCGetSet tLCGetSet = tLCGetDB.query();
		if (tLCGetDB.mErrors.needDealError()) {
			this.mErrors.addOneError("查询保单险种号[" + tPolNo + "],责任:[" + tDutyCode
					+ "],给付责任:[" + tDutyCode + "],领取项信息失败!"
					+ tLCGetDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLCGetSet.size() != 1) {
			this.mErrors.addOneError("查询保单险种号[" + tPolNo + "],责任:[" + tDutyCode
					+ "],给付责任:[" + tDutyCode + "],查询出的记录数为:["
					+ tLCGetSet.size() + "],领取项信息不唯一!");
			return null;
		}

		LCGetSchema tLCGetSchema = tLCGetSet.get(1);

		double t_SumMoney = 0;
		t_SumMoney = tLCGetSchema.getSumMoney();

		t_SumMoney = Arith.round(tLCGetSchema.getSumMoney()
				+ pLLClaimDetailSchema.getRealPay(), 2);

		logger.debug("------------------------------开始---------正常冲减保额------------------------------");
		logger.debug("保单险种号PolNo:[" + tPolNo + "],责任DutyCode:["
				+ tDutyCode + "],给付责任GetDutyCode:[" + tGetDutyCode + "]");
		logger.debug("基本保额StandMoney:[" + tLCGetSchema.getStandMoney()
				+ "],已经冲减的金额SumMoney:[" + tLCGetSchema.getSumMoney()
				+ "],本次给付金额RealPay:[" + pLLClaimDetailSchema.getRealPay()
				+ "],最终冲减后金额:[" + t_SumMoney + "]");
		logger.debug("------------------------------结束---------正常冲减保额------------------------------");

		tLCGetSchema.setSumMoney(t_SumMoney);
		tLCGetSchema.setOperator(mG.Operator);
		tLCGetSchema.setMakeDate(this.CurrentDate);
		tLCGetSchema.setMakeTime(this.CurrentTime);

		logger.debug("---------------------------------进行本责任保额冲减----结束---------------------------------");

		return tLCGetSchema;
	}

	/**
	 * 进行其他起责任保额冲减
	 * 
	 * @param pLLClaimDetailSchema
	 * @return
	 */
	private LCGetSet getOtherLCGet(LLClaimDetailSchema pLLClaimDetailSchema,
			LMDutyGetClmSchema pLMDutyGetClmSchema) {

		logger.debug("---------------------------------进行其他责任保额冲减----开始---------------------------------");

		double t_OthMoney = 0;
		t_OthMoney = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_OthMoney));

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.1 查询LCPol的信息,用于计算给付金额
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tPolNo = pLLClaimDetailSchema.getPolNo();
		String tDutyCode = pLLClaimDetailSchema.getDutyCode();

		mLCPolSchema = mLLClaimPubFunBL.getLCPol(tPolNo);
		if (mLCPolSchema == null) {
			this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
			return null;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.2 通过公式计算出冲减其他责任的金额
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tCnterCalCode = StrTool.cTrim(pLMDutyGetClmSchema
				.getCnterCalCode());// 获取LCGet的公式
		String tOthCalCode = StrTool.cTrim(pLMDutyGetClmSchema.getOthCalCode()); // 计算金额的公式
		String tGetDutyCode = tCnterCalCode;

		String tSql = executeCalCode(tCnterCalCode, pLLClaimDetailSchema); // 解析后的LCGet语句
		SQLwithBindVariables sqlbva = new SQLwithBindVariables();
		sqlbva.sql(tSql);
		t_OthMoney = executePay(tOthCalCode, pLLClaimDetailSchema);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.3 查询其他责任
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		LCGetDB tLCGetDB = new LCGetDB();
		LCGetSet tLCGetSet = tLCGetDB.executeQuery(sqlbva);
		if (tLCGetDB.mErrors.needDealError()) {
			this.mErrors.addOneError("查询保单险种号[" + tPolNo + "],责任:[" + tDutyCode
					+ "],领取项信息失败!" + tLCGetDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLCGetSet.size() == 0) {
			this.mErrors.addOneError("查询保单险种号[" + tPolNo + "],责任:[" + tDutyCode
					+ "],查询出的记录数为:[" + tLCGetSet.size() + "],领取项信息不能为空!");
			return null;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.5 对其他责任的保额进行冲减
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LCGetSet tLCGetSaveSet = new LCGetSet();
		for (int i = 1; i <= tLCGetSet.size(); i++) {
			LCGetSchema tLCGetSchema = tLCGetSet.get(i);

			double t_SumMoney = 0;
			t_SumMoney = tLCGetSchema.getSumMoney();

			t_SumMoney = Arith
					.round(tLCGetSchema.getSumMoney() + t_OthMoney, 2);

			logger.debug("------------------------------开始第[" + i
					+ "]个---------其他责任冲减保额------------------------------");
			logger.debug("保单险种号PolNo:[" + tPolNo + "],责任DutyCode:["
					+ tDutyCode + "],给付责任GetDutyCode:[" + tGetDutyCode + "]");
			logger.debug("基本保额StandMoney:["
					+ tLCGetSchema.getStandMoney() + "],已经冲减的金额SumMoney:["
					+ tLCGetSchema.getSumMoney() + "],本次给付金额t_OthMoney:["
					+ t_OthMoney + "],最终冲减后金额:[" + t_SumMoney + "]");
			logger.debug("------------------------------结束第[" + i
					+ "]个---------其他责任冲减保额------------------------------");

			tLCGetSchema.setSumMoney(t_SumMoney);
			tLCGetSchema.setOperator(mG.Operator);
			tLCGetSchema.setMakeDate(this.CurrentDate);
			tLCGetSchema.setMakeTime(this.CurrentTime);
			tLCGetSaveSet.add(tLCGetSchema);
		}

		logger.debug("---------------------------------进行其他责任保额冲减----结束---------------------------------");
		return tLCGetSaveSet;
	}

	/**
	 * 目的：执行产品指向的算法公式
	 */
	private String executeCalCode(String pCalCode,
			LLClaimDetailSchema pLLClaimDetailSchema) {
		String tReturn = "";

		if (pCalCode == null || pCalCode.length() == 0) {
			return tReturn;
		}
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 设置各种参数
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		logger.debug("----------开始---------执行产品指向的算法公式-------------------------------");

		logger.debug("===========================================================================");

		// 增加基本要素,计算给付金
		TransferData tTransferData = new TransferData();
		// 合同号
		tTransferData.setNameAndValue("ContNo", String
				.valueOf(pLLClaimDetailSchema.getContNo()));

		// 保单险种号
		tTransferData.setNameAndValue("PolNo", String
				.valueOf(pLLClaimDetailSchema.getPolNo()));

		// 责任
		tTransferData.setNameAndValue("DutyCode", String
				.valueOf(pLLClaimDetailSchema.getDutyCode()));

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0
		 * 将所有设置的参数加入到PubCalculator.addBasicFactor()中
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		Vector tVector = tTransferData.getValueNames();
		PubCalculator tPubCalculator = new PubCalculator();

		logger.debug("===========================================================================");
		for (int i = 0; i < tVector.size(); i++) {
			String tName = (String) tVector.get(i);
			String tValue = (String) tTransferData.getValueByName(tName);
			logger.debug("计算要素--tName:" + tName + "  tValue:" + tValue);
			tPubCalculator.addBasicFactor(tName, tValue);
		}
		logger.debug("===========================================================================");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0
		 * 将所有设置的参数加入到Calculator.addBasicFactor()中
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		Calculator tCalculator = new Calculator();
		mBomList=mPrepareBOMClaimBL.dealData(tTransferData);
		tCalculator.setBOMList(mBomList);
		tCalculator.setCalCode(pCalCode);

		tVector = tTransferData.getValueNames();
		for (int i = 0; i < tVector.size(); i++) {
			String tName = (String) tVector.get(i);
			String tValue = (String) tTransferData.getValueByName(tName);
			// logger.debug("executeCalCode--tName:" + tName + " tValue:"
			// + tValue);
			tCalculator.addBasicFactor(tName, tValue);
		}

		tReturn = tCalculator.getCalSQL();
		logger.debug("----------------------------------------------------------------------------------\n");
		logger.debug("解析前的计算公式==[" + pCalCode + "]");
		logger.debug("解析后的计算公式==[" + tReturn + "]");
		logger.debug("\n----------------------------------------------------------------------------------");

		logger.debug("\n----------结束---------执行产品指向的算法公式-------------------------------");

		return tReturn;

	}

	/**
	 * 目的：理赔计算
	 * 
	 * @param pCalSum
	 *            计算金额
	 * @param pCalCode
	 *            计算公式
	 * @return double
	 */
	private double executePay(String pCalCode,
			LLClaimDetailSchema pLLClaimDetailSchema) {
		double rValue;

		if (null == pCalCode || "".equals(pCalCode)) {
			return 0;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 设置各种计算要素 ?Amnt?
		 * \?PolNo??\?GetDutyCode?\?GetDutyKind?\?ClmNO?
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		logger.debug("==================================================================");

		// 增加基本要素,计算给付金
		TransferData tTransferData = new TransferData();

		// 基本保额,取自出险时点,需要考虑保全
		tTransferData.setNameAndValue("Amnt", String
				.valueOf(getAmnt(pLLClaimDetailSchema)));

		// 保单号
		tTransferData.setNameAndValue("PolNo", String
				.valueOf(pLLClaimDetailSchema.getPolNo()));

		// 给付责任类型
		tTransferData.setNameAndValue("GetDutyCode", String
				.valueOf(pLLClaimDetailSchema.getGetDutyCode()));

		// 赔案号
		tTransferData.setNameAndValue("ClmNO", String.valueOf(this.mClmNo));

		// 出险时已保年期
		tTransferData.setNameAndValue("RgtYears", String.valueOf(PubFun
				.calInterval(mLCPolSchema.getCValiDate(), this.mInsDate, "Y")));

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0
		 * 将所有设置的参数加入到PubCalculator.addBasicFactor()中
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		Vector tVector = tTransferData.getValueNames();
		PubCalculator tPubCalculator = new PubCalculator();

		for (int i = 0; i < tVector.size(); i++) {
			String tName = (String) tVector.get(i);
			String tValue = (String) tTransferData.getValueByName(tName);
			// logger.debug("PubCalculator.addBasicFactor--tName:" + tName
			// + " tValue:" + tValue);
			tPubCalculator.addBasicFactor(tName, tValue);
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0
		 * 将所有设置的参数加入到Calculator.addBasicFactor()中
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		Calculator tCalculator = new Calculator();
		tCalculator.setCalCode(pCalCode);

		tVector = tTransferData.getValueNames();
		logger.debug("==================================================================");
		for (int i = 0; i < tVector.size(); i++) {
			String tName = (String) tVector.get(i);
			String tValue = (String) tTransferData.getValueByName(tName);
			logger.debug("计算其他责任计算要素名称--tName:" + tName + "  tValue:"
					+ tValue);
			tCalculator.addBasicFactor(tName, tValue);
		}
		logger.debug("==================================================================");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.0
		 * 进行计算，Calculator.calculate()
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tStr = "";
		logger.debug("计算公式=" + tCalculator.getCalSQL());
		tStr = tCalculator.calculate();
		if (tStr.trim().equals("")) {
			rValue = 0;
		} else {
			rValue = Arith.round(Double.parseDouble(tStr), 2);
		}

		if (tCalculator.mErrors.needDealError()) {
			mErrors.addOneError("计算公式错误!!!"
					+ tCalculator.mErrors.getError(0).errorMessage);
		}
		logger.debug("==================================================================");
		return rValue;
	}

	/**
	 * 得到基本保额,取自出险时点,需要考虑保全
	 * 
	 * @return
	 */
	private double getAmnt(LLClaimDetailSchema pLLClaimDetailSchema) {
		String tReturn = "";
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));

		ExeSQL tExeSQL = new ExeSQL();
		String tSql = "";

		String tPosFlag = StrTool.cTrim(pLLClaimDetailSchema.getPosFlag());

		/* 0未做保全,1已做保全 */
		if (tPosFlag.equals("0") || tPosFlag.equals("1")) {
			tSql = "select (case when StandMoney is null then 0 else StandMoney end) from LCGet where 1=1 "
					+ " and PolNo ='" + "?PolNo?" + "'"
					+ " and DutyCode ='" + "?DutyCode?"
					+ "'" + " and GetDutyCode ='"
					+ "?GetDutyCode?" + "'";
		} else {

			tSql = "select (case when StandMoney is null then 0 else StandMoney end) from LPGet where 1=1 "
					+ " and EdorNo ='" + "?EdorNo?"
					+ "'" + " and PolNo ='" + "?PolNo?"
					+ "'" + " and DutyCode ='"
					+ "?DutyCode?" + "'"
					+ " and GetDutyCode ='"
					+ "?GetDutyCode?" + "'";
		}

		logger.debug("------------------------------------------------------------");
		logger.debug("--要素Amnt[基本保额]：[" + tSql + "]");
		logger.debug("------------------------------------------------------------");
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(tSql);
		sqlbv8.put("PolNo", pLLClaimDetailSchema.getPolNo());
		sqlbv8.put("DutyCode", pLLClaimDetailSchema.getDutyCode());
		sqlbv8.put("PolNo", pLLClaimDetailSchema.getGetDutyCode());
		sqlbv8.put("EdorNo", pLLClaimDetailSchema.getPosEdorNo());
		tReturn = StrTool.cTrim(tExeSQL.getOneValue(sqlbv8));
		if (tExeSQL.mErrors.needDealError()) {
			this.mErrors.addOneError("得到基本保额发生错误!"
					+ tExeSQL.mErrors.getError(0).errorMessage);
		}

		if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
			t_Sum_Return = Double.parseDouble(tReturn);
		}

		return t_Sum_Return;

	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－过程中－－－－－帐户保额冲减－－－－－续涛加－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */
	/**
	 * 处理帐户型的保额冲减
	 */
	private boolean dealAccount(LLClaimDetailSchema pLLClaimDetailSchema) {
		logger.debug("---------------------------------进行帐户型保额冲减----开始---------------------------------");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 定义变量
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tPol = pLLClaimDetailSchema.getPolNo();

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
		 * 根据赔案号，从LLRegister表中取出立案登记信息
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLRegisterSchema tLLRegisterSchema = mLLClaimPubFunBL
				.getLLRegister(mClmNo);

		if (tLLRegisterSchema == null) {
			mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
			return false;
		}

		String tRgtDate = tLLRegisterSchema.getRgtDate().substring(0, 10);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.1 查出该保单的所有帐户
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		String strSql = "select * from LCInsureAcc where 1=1" + " and PolNo ='"
				+ "?PolNo?" + "'";
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(strSql);
		sqlbv9.put("PolNo", tPol);
		LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.executeQuery(sqlbv9);
		if (tLCInsureAccDB.mErrors.needDealError()) {
			CError.buildErr(this, "保单号:[" + tPol + "]保险帐户信息查询失败!");
			return false;
		}
		if (tLCInsureAccSet == null || tLCInsureAccSet.size() < 1) {
			CError.buildErr(this, "保单号:[" + tPol + "]保险帐户信息查询为空!");
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.1
		 * 查出该保单的所有帐户信息LCInsureAcc
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LCInsureAccSet tLCInsureAccSaveSet = new LCInsureAccSet();
		for (int i = 1; i <= tLCInsureAccSet.size(); i++) {

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.2 定义业务处理变量
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LCInsureAccSchema tLCInsureAccSchema = tLCInsureAccSet.get(i);

			double tRealPay = 0.0; // 最后赔付金额
			double tSumPaym = 0.0; // 帐户已领金额
			double tInsuAccGetMoney = 0;// 账户可领金额
			double tClmAccBal = 0.0; // 理赔帐户差额

			tRealPay = pLLClaimDetailSchema.getRealPay(); // 最后赔付金额
			tSumPaym = tLCInsureAccSchema.getSumPaym(); // 帐户已领金额
			tInsuAccGetMoney = tLCInsureAccSchema.getInsuAccGetMoney();// 账户可领金额
			tClmAccBal = tInsuAccGetMoney - tRealPay; // 账户可领金额 - 最后赔付金额

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.5 账户可领金额 >=
			 * 最后赔付金额, 则累计领取 = 原值 + 本次赔付;可领金额 = 原值 - 本次赔付
			 * 
			 * 账户可领金额 < 最后赔付金额, 则累计领取 = 原值 + 本次可领;可领金额 = 原值 - 本次可领 = 0
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (tClmAccBal >= 0) {
				tLCInsureAccSchema.setSumPaym(tSumPaym + tRealPay); // 累计领取
				tLCInsureAccSchema.setInsuAccGetMoney(tInsuAccGetMoney
						- tRealPay);// 设置账户可领金额
			} else {
				tLCInsureAccSchema.setSumPaym(tSumPaym + tInsuAccGetMoney);// 累计领取
				tLCInsureAccSchema.setInsuAccGetMoney(0); // 设置账户可领金额

			}

			tLCInsureAccSchema.setModifyDate(this.CurrentDate);
			tLCInsureAccSchema.setModifyTime(this.CurrentTime);
			tLCInsureAccSaveSet.add(tLCInsureAccSchema);

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.6
			 * 查出保险账户分类表信息LCInsureAccClass
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
			tLCInsureAccClassDB.setPolNo(tLCInsureAccSchema.getPolNo());
			tLCInsureAccClassDB.setInsuAccNo(tLCInsureAccSchema.getInsuAccNo());
			LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB
					.query();

			LCInsureAccClassSet tLCInsureAccClassSaveSet = new LCInsureAccClassSet();
			for (int m = 1; m <= tLCInsureAccClassSet.size(); m++) {
				LCInsureAccClassSchema tLCInsureAccClassSchema = tLCInsureAccClassSet
						.get(m);

				if (tClmAccBal >= 0) {
					tLCInsureAccClassSchema.setSumPaym(tSumPaym + tRealPay); // 累计领取
					tLCInsureAccClassSchema.setInsuAccGetMoney(tInsuAccGetMoney
							- tRealPay);// 设置账户可领金额
				} else {
					tLCInsureAccClassSchema.setSumPaym(tSumPaym
							+ tInsuAccGetMoney);// 累计领取
					tLCInsureAccClassSchema.setInsuAccGetMoney(0); // 设置账户可领金额

				}
				tLCInsureAccClassSchema.setModifyDate(this.CurrentDate);
				tLCInsureAccClassSchema.setModifyTime(this.CurrentTime);
				tLCInsureAccClassSaveSet.add(tLCInsureAccClassSchema);
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.7
			 * 对保险帐户表记价履历表LCInsureAccTrace进行操作 加一条正的利息信息 加一条负的本金 + 利息信息
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();

			LCInsureAccTraceSchema tLCInsureAccTraceLPSchema = new LCInsureAccTraceSchema();
			String sLimit = PubFun
					.getNoLimit(tLCInsureAccSchema.getManageCom());
			String tserNo = "CLM" + PubFun1.CreateMaxNo("CLMACC", 10);
			tLCInsureAccTraceLPSchema.setGrpContNo(tLCInsureAccSchema
					.getContNo()); /* 集体合同号码 */
			tLCInsureAccTraceLPSchema.setGrpPolNo(tLCInsureAccSchema
					.getGrpPolNo());/* 集体保单险种号码 */
			tLCInsureAccTraceLPSchema.setContNo(tLCInsureAccSchema.getContNo()); /* 合同号码 */
			tLCInsureAccTraceLPSchema.setPolNo(tLCInsureAccSchema.getPolNo()); /* 保单险种号码 */
			tLCInsureAccTraceLPSchema.setSerialNo(tserNo); /* 流水号 */

			tLCInsureAccTraceLPSchema.setInsuAccNo(tLCInsureAccSchema
					.getInsuAccNo()); /* 保险帐户号码 */
			tLCInsureAccTraceLPSchema.setRiskCode(tLCInsureAccSchema
					.getRiskCode()); /* 险种编码 */
			tLCInsureAccTraceLPSchema.setPayPlanCode("902020"); /* 交费计划编码 */
			tLCInsureAccTraceLPSchema.setOtherNo(mClmNo); /* 对应赔案号 */
			tLCInsureAccTraceLPSchema.setOtherType("5");

			tLCInsureAccTraceLPSchema.setAccAscription("0");
			tLCInsureAccTraceLPSchema.setMoneyType("LP");

			if (tClmAccBal >= 0) {
				tLCInsureAccTraceLPSchema.setMoney(-tRealPay);
			} else {
				tLCInsureAccTraceLPSchema.setMoney(-tInsuAccGetMoney);
			}

			tLCInsureAccTraceLPSchema.setUnitCount("0");/* 本次单位数 */
			tLCInsureAccTraceLPSchema.setPayDate(tRgtDate);/* 交费日期 */
			tLCInsureAccTraceLPSchema.setState(tLCInsureAccSchema.getState());/* 状态 */
			tLCInsureAccTraceLPSchema.setManageCom(tLCInsureAccSchema
					.getManageCom());
			tLCInsureAccTraceLPSchema.setOperator(tLCInsureAccSchema
					.getOperator());
			tLCInsureAccTraceLPSchema.setMakeDate(this.CurrentDate);
			tLCInsureAccTraceLPSchema.setMakeTime(this.CurrentTime);
			tLCInsureAccTraceLPSchema.setModifyDate(this.CurrentDate);
			tLCInsureAccTraceLPSchema.setModifyTime(this.CurrentTime);
			tLCInsureAccTraceLPSchema.setFeeCode("100001");
			tLCInsureAccTraceLPSchema.setAccAlterNo(mClmNo);
			tLCInsureAccTraceLPSchema.setAccAlterType("2");
			tLCInsureAccTraceSet.add(tLCInsureAccTraceLPSchema);

			mMMap.put(tLCInsureAccClassSaveSet, "DELETE&INSERT");
			mMMap.put(tLCInsureAccTraceSet, "DELETE&INSERT");
		}

		mMMap.put(tLCInsureAccSaveSet, "DELETE&INSERT");

		logger.debug("---------------------------------进行帐户型保额冲减----结束---------------------------------");

		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－－－保额冲减－－－－－续涛加－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－－－终止责任－－－－－续涛修改－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 处理3：遍历LLClaimDetail表中每一个给付标志GiveType为0（给付）的保项，然后根据
	 * 给付责任编码到LMDutyGetClm表中去找AfterGet，给付后动作这个信息做如下操作
	 */
	private boolean getDealDutyCode() {
		logger.debug("---------------------------------进行AfterGet给付后动作处理----开始---------------------------------");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 循环保项信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		for (int i = 1; i <= mLLClaimDetailSet.size(); i++) {
			LLClaimDetailSchema tLLClaimDetailSchema = mLLClaimDetailSet.get(i);

			LMDutyGetClmDB tLMDutyGetClmDB = new LMDutyGetClmDB();
			LMDutyGetClmSet tLMDutyGetClmSet = new LMDutyGetClmSet();
			String tSql = "";

			tSql = "select * from LMDutyGetClm where 1 = 1 "
					+ " and getdutycode = '"
					+ "?getdutycode?" + "'"
					+ " and GetDutyKind = '"
					+ "?GetDutyKind?" + "'";
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(tSql);
			sqlbv10.put("getdutycode", tLLClaimDetailSchema.getGetDutyCode());
			sqlbv10.put("GetDutyKind", tLLClaimDetailSchema.getGetDutyKind());
			tLMDutyGetClmSet = tLMDutyGetClmDB.executeQuery(sqlbv10);

			logger.debug("------------------------------------------------------");
			logger.debug("--LMDutyGetClm[" + tLMDutyGetClmSet.size()
					+ "]:[" + tSql + "]");
			logger.debug("------------------------------------------------------");

			if (tLMDutyGetClmSet == null && tLMDutyGetClmSet.size() == 0) {
				this.mErrors.addOneError("查询赔案" + mClmNo + "责任给付赔付数据时出错!");
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 进行终止判断
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			for (int j = 1; j <= tLMDutyGetClmSet.size(); j++) {

				LMDutyGetClmSchema tLMDutyGetClmSchema = tLMDutyGetClmSet
						.get(j);
				String tAfterGet = StrTool.cTrim(tLMDutyGetClmSchema
						.getAfterGet());

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.1 AfterGet＝000
				 * 无动作 AfterGet＝001 [审批通过]保额递减，只冲减保额 AfterGet＝003
				 * 无条件销户时,终止该合同(包括所有的险种) AfterGet＝004
				 * 最后一次给付销户,判断保额是否冲减完毕，如果冲减完毕执行003的动作 AfterGet＝005 [待定]
				 * AfterGet＝006 [审批通过]终止该责任给付时,终止LCGet的数据 AfterGet＝007
				 * [待定]终止该责任时：根据DutyCode的前六位，在LCDuty表中查找总数，如果与总数相等，
				 * 则终止LCContState表中的状态,终止本险种. AfterGet＝008 终止本险种
				 * 
				 * 
				 * LMRiskApp.SubRiskFlag='S'[附险],
				 * 第一种情况:附加险只是扩展主险责任,赔付也是赔付主险责任的情况:如老系统的两个附加提前给付险种 LMDutyGetClm.EffectOnMainRisk='01',终止本身及主险;
				 * 第二种情况:附加险赔付自身,即附加险的给付责任在系统中存在描述:如新系统的MS附加提前给付重大疾病保险（2009）lmdutygetclm的EffectOnMainRisk需描述为'02',终止本身及主险;；
			
				 * 
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */

				if (tAfterGet.equals("006")) {
					setLCGetEndState(tLLClaimDetailSchema);

				} else if (tAfterGet.equals("007")) {

				}

			}// for end
		}

		logger.debug("---------------------------------进行AfterGet给付后动作处理----结束---------------------------------");
		return true;
	}

	/**
	 * 在LCGet表中找出相应记录并将其GetEndState状态置为1终止
	 */
	private boolean setLCGetEndState(LLClaimDetailSchema pLLClaimDetailSchema) {

		LCGetDB tLCGetDB = new LCGetDB();
		tLCGetDB.setPolNo(pLLClaimDetailSchema.getPolNo());
		tLCGetDB.setDutyCode(pLLClaimDetailSchema.getDutyCode());
		tLCGetDB.setGetDutyCode(pLLClaimDetailSchema.getGetDutyCode());
		LCGetSet tLCGetSet = tLCGetDB.query();

		if (tLCGetDB.mErrors.needDealError()) {
			this.mErrors.addOneError("查询领取项信息失败!"
					+ tLCGetDB.mErrors.getError(0).errorMessage);
			return false;
		}

		if (tLCGetSet.size() != 1) {
			this.mErrors.addOneError("查询领取项信息不唯一!");
			return false;
		}

		LCGetSchema tLCGetSchema = new LCGetSchema();
		tLCGetSchema = tLCGetSet.get(1);
		tLCGetSchema.setGetEndState("1");
		tLCGetSchema.setOperator(mG.Operator);
		tLCGetSchema.setModifyDate(this.CurrentDate);
		tLCGetSchema.setModifyTime(this.CurrentTime);

		mMMap.put(tLCGetSchema, "UPDATE");

		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－－－终止责任－－－－－续涛修改－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－－－财务数据处理－－－－－续涛修改－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 处理5.生成财务数据处理
	 * 
	 */
	private boolean getDealFinance(String pClmNo) {

		logger.debug("---------------------------------进行财务应付转实付数据处理----开始---------------------------------");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 根据给付通知书号取出应付总表的分组数据
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql = "select trim(getnoticeno),sum(sumgetmoney),sum(NetAmount),sum(TaxAmount),tax from LJSGet where 1=1"
				+ " and OtherNo = '"
				+ "?clmno?"
				+ "'"
				+ " and OtherNoType = '5'"
				+ " group by Trim(getnoticeno),tax";

		logger.debug("----------------------------------------------------------------------------");
		logger.debug("--审批通过，查询应付记录[" + tSql + "]");
		logger.debug("----------------------------------------------------------------------------");
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql(tSql);
		sqlbv11.put("clmno", pClmNo);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv11);
		if (tExeSQL.mErrors.needDealError()) {
			this.mErrors.addOneError("查询赔案" + mClmNo + "应付记录时错误!!!"
					+ tExeSQL.mErrors.getError(0).errorMessage);
			return false;
		}

		logger.debug("----------------------------------------------------------------------------");
		logger.debug("--审批通过，查询应付记录数[" + tSSRS.getMaxRow() + "]");
		logger.debug("----------------------------------------------------------------------------");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 循环应付总表的分组数据
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {

			String tGetNoticeNo = StrTool.cTrim(tSSRS.GetText(i, 1));// 给付通知书号
			double tSumPay = Arith.round(Double.parseDouble(StrTool.cTrim(tSSRS
					.GetText(i, 2))), 2);// 支付总金额
			 netAmount = Arith.round(Double.parseDouble(StrTool.cTrim(tSSRS
					.GetText(i, 3))), 2);// 净值
			 taxAmount = Arith.round(Double.parseDouble(StrTool.cTrim(tSSRS
					.GetText(i, 4))), 2);// 税费
			 tax = Arith.round(Double.parseDouble(StrTool.cTrim(tSSRS
					.GetText(i, 5))), 4);// 税率
			
			if (tSumPay < 0) {
				this.mErrors.addOneError("赔案" + mClmNo + "给付通知书号:[" + tGetNoticeNo
						+ "]的受益人的领款金额小于0,审批不能通过!!!");
				return false;
			}

			logger.debug("----------------------------------------------------------------------------");
			logger.debug("--审批通过,第[" + i + "]条,金额:[" + tSumPay + "],给付通知书号["
					+ tGetNoticeNo + "]");
			logger.debug("----------------------------------------------------------------------------");

			if (!getDealFinanceLJAGet(pClmNo, tGetNoticeNo, tSumPay)) {
				return false;
			}
			logger.debug("------------------------------------------------------");
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0
		 * LLBalance--PayFlag金支付标志置为1，已支付
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tUpdateSqlA = "update LLBalance a set a.Payflag = '1' where 1=1 "
				+ " and a.clmno = '" + "?clmno?" + "'";
		SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
		sqlbv12.sql(tUpdateSqlA);
		sqlbv12.put("clmno", pClmNo);
		mMMap.put(sqlbv12, "UPDATE");

		String tSql3 = "delete from LJSGet where OtherNo='" + "?clmno?"
				+ "' and  OtherNoType='5' ";
		SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
		sqlbv13.sql(tSql3);
		sqlbv13.put("clmno", pClmNo);
		mMMap.put(sqlbv13, "DELETE");

		String tSql4 = "delete from LJSGetClaim where OtherNo='" + "?clmno?"
				+ "' and  OtherNoType='5' ";
		SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
		sqlbv14.sql(tSql4);
		sqlbv14.put("clmno", pClmNo);
		mMMap.put(sqlbv14, "DELETE");

		logger.debug("---------------------------------进行财务应付转实付数据处理----结束---------------------------------");
		return true;
	}

	/**
	 * 根据赔案，给付通知书号，查询财务的应付记录，以便生成实付数据
	 * 
	 * @param pDrawerID
	 * @return
	 */
	private boolean getDealFinanceLJAGet(String pClmNo, String pGetNoticeNo,
			double pSumPay) {

		double tDSumPay = pSumPay;// 实付总金额
		tDSumPay = Double.parseDouble(new DecimalFormat("0.00")
				.format(tDSumPay));

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0
		 * 根据赔案号，给付通知书号查询财务应付总表的数据
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LJSGetDB tLJSGetDB = new LJSGetDB();
		tLJSGetDB.setOtherNo(pClmNo);
		tLJSGetDB.setOtherNoType("5");
		tLJSGetDB.setGetNoticeNo(pGetNoticeNo);
		LJSGetSet tLJSGetSet = tLJSGetDB.query();

		logger.debug("----------------------------------------------------------------------------");
		logger.debug("--LJSGet应付总表记录数：[" + tLJSGetSet.size() + "]");
		logger.debug("----------------------------------------------------------------------------");

		if (tLJSGetDB.mErrors.needDealError()) {
			this.mErrors.addOneError("审批通过时，查询该赔案给付通知书号:[" + pGetNoticeNo
					+ "]的应付汇总记录数为[" + tLJSGetSet.size() + "]，发生错误!!!!"
					+ tLJSGetDB.mErrors.getError(0).errorMessage);
			return false;
		}

		if (tLJSGetSet.size() == 0) {
			this.mErrors.addOneError("审批通过时，查询该赔案给付通知书号:[" + pGetNoticeNo
					+ "]的应付汇总记录数为[" + tLJSGetSet.size() + "]，发生错误!!!!");
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 根赔赔案号，应付总金额生成一笔实付数据
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LJAGetSchema tLJAGetSchema = new LJAGetSchema();
		mReflections.transFields(tLJAGetSchema, tLJSGetSet.get(1));
		String tGetNo = PubFun1.CreateMaxNo("GETNO", PubFun.getNoLimit(mG.ManageCom)); // 生成实付号
		tLJAGetSchema.setActuGetNo(tGetNo); // 实付号
		tLJAGetSchema.setGetNoticeNo(pGetNoticeNo); // 给付通知书号码
		tLJAGetSchema.setSumGetMoney(pSumPay); // 实付金额
		tLJAGetSchema.setShouldDate(CurrentDate); // 应付日期
		tLJAGetSchema.setOperator(mG.Operator);
		tLJAGetSchema.setMakeDate(CurrentDate);
		tLJAGetSchema.setMakeTime(CurrentTime);
		tLJAGetSchema.setModifyDate(CurrentDate);
		tLJAGetSchema.setModifyTime(CurrentTime);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0 根据赔案号，给付通知书号查询受益人表中的记录 `
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLBnfDB tLLBnfDB = new LLBnfDB();
		tLLBnfDB.setClmNo(pClmNo);
		tLLBnfDB.setOtherNo(pGetNoticeNo); // 给付通知书号
		tLLBnfDB.setBnfKind("A");
		LLBnfSet tLLBnfSet = tLLBnfDB.query();
		if (tLLBnfDB.mErrors.needDealError()) {
			this.mErrors.addOneError("审批通过时，查询该赔案给付通知书号:[" + pGetNoticeNo
					+ "]的受益人记录数为[" + tLLBnfSet.size() + "]，发生错误!!!!"
					+ tLLBnfDB.mErrors.getError(0).errorMessage);
			return false;
		}

		if (tLLBnfSet.size() == 0) {
			this.mErrors.addOneError("审批通过时，查询该赔案给付通知书号:[" + pGetNoticeNo
					+ "]的受益人记录数为[" + tLLBnfSet.size() + "]，发生错误!!!!");
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.1 将受益人表中的记录 置值 实付号 `
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLBnfSet tLLBnfSaveSet = new LLBnfSet();
		for (int i = 1; i <= tLLBnfSet.size(); i++) {
			
			LLBnfSchema tLLBnfSchema = tLLBnfSet.get(i);
			tLLBnfSchema.setOtherNo(tGetNo);
			tLLBnfSchema.setOtherNoType("5");
			tLLBnfSchema.setCasePayFlag("1");// 保险金支付标志,0未支付,1已支付
			tLLBnfSchema.setOperator(mG.Operator);
			tLLBnfSchema.setModifyDate(CurrentDate);
			tLLBnfSchema.setModifyTime(CurrentTime);
			tLLBnfSaveSet.add(tLLBnfSchema);
			
			tLLBnfSchema=null;
		}
		mMMap.put(tLLBnfSaveSet, "DELETE&INSERT");
		
		
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0 根据赔案号，给付通知书号查询受益人汇总表中的记录 `
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLBnfGatherDB tLLBnfGatherDB = new LLBnfGatherDB();
		tLLBnfGatherDB.setClmNo(pClmNo);
		tLLBnfGatherDB.setBnfKind("A");
		tLLBnfGatherDB.setOtherNo(pGetNoticeNo); // 给付通知书号
		
		LLBnfGatherSet tLLBnfGatherSet = tLLBnfGatherDB.query();
		
		if (tLLBnfGatherDB.mErrors.needDealError()) {
			this.mErrors.addOneError("审批通过时，查询该赔案给付通知书号:[" + pGetNoticeNo
					+ "]的受益人汇总记录数为[" + tLLBnfSet.size() + "]，发生错误!!!!"
					+ tLLBnfDB.mErrors.getError(0).errorMessage);
			return false;
		}

		if (tLLBnfGatherSet.size() == 0) {
			this.mErrors.addOneError("审批通过时，查询该赔案给付通知书号:[" + pGetNoticeNo
					+ "]的受益人汇总记录数为[" + tLLBnfSet.size() + "]，发生错误!!!!");
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.1 将受益人汇总表中的记录 置值 实付号 `
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLBnfGatherSet tLLBnfGatherSaveSet = new LLBnfGatherSet();
		LLBnfGatherSchema tLLBnfGatherSchema =null;
		for (int i = 1; i <= tLLBnfGatherSet.size(); i++) {
			
			tLLBnfGatherSchema = tLLBnfGatherSet.get(i);
			tLLBnfGatherSchema.setOtherNo(tGetNo);
			tLLBnfGatherSchema.setOtherNoType("5");
			tLLBnfGatherSchema.setCasePayFlag("1");// 保险金支付标志,0未支付,1已支付
			tLLBnfGatherSchema.setOperator(mG.Operator);
			tLLBnfGatherSchema.setModifyDate(CurrentDate);
			tLLBnfGatherSchema.setModifyTime(CurrentTime);
			tLLBnfGatherSaveSet.add(tLLBnfGatherSchema);
			
			tLLBnfGatherSchema=null;
		}
		mMMap.put(tLLBnfGatherSaveSet, "DELETE&INSERT");
		

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.0
		 * 根据总表的应付号，赔案号，找出其对应的应付明细记录 `
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql = "select PolNo,FeeOperationType,SubFeeOperationType,FeeFinaType,"
				+ " DutyCode,GetDutyKind,GetDutyCode,sum(Pay),sum(taxAmount),sum(netAmount) "
				+ " from LJSGetClaim where 1=1 "
				+ " and GetNoticeNo ='"+"?GetNoticeNo?"+"'" 
				+ " group by PolNo,FeeOperationType,SubFeeOperationType,FeeFinaType,DutyCode,GetDutyKind,GetDutyCode ";

		logger.debug("----------------------------------------------------------------------------");
		logger.debug("--LJSGetClaim应付明细记录:[" + tSql + "]");
		logger.debug("----------------------------------------------------------------------------");
		SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
		sqlbv15.sql(tSql);
		sqlbv15.put("GetNoticeNo", pGetNoticeNo);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv15);
		if (tExeSQL.mErrors.needDealError()) {
			this.mErrors.addOneError("查询应付明细记录时错误!!!"
					+ tExeSQL.mErrors.getError(0).errorMessage);
			return false;
		}

		logger.debug("----------------------------------------------------------------------------");
		logger.debug("--通过分组SQL取得应付明细记录数[" + tSSRS.getMaxRow() + "]");
		logger.debug("----------------------------------------------------------------------------");

		LJAGetClaimSet tLJAGetClaimSaveSet = new LJAGetClaimSet();

		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.0
			 * 取出保单号，业务类型，子业务类型，财务类型，支付总金额
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tPolNo = StrTool.cTrim(tSSRS.GetText(i, 1));// 取应付明细的保单号
			String tType = StrTool.cTrim(tSSRS.GetText(i, 2));// 业务类型
			String tSubType = StrTool.cTrim(tSSRS.GetText(i, 3));// 子业务类型
			String tFinaType = StrTool.cTrim(tSSRS.GetText(i, 4));// 财务类型

			String tDutyCode = StrTool.cTrim(tSSRS.GetText(i, 5)); // 责任编码
			String tGetDutyKind = StrTool.cTrim(tSSRS.GetText(i, 6));// 给付责任类型
			String tGetDutyCode = StrTool.cTrim(tSSRS.GetText(i, 7));// 给付责任编码

			double tSumPay = Arith.round(Double.parseDouble(StrTool.cTrim(tSSRS
					.GetText(i, 8))), 2);// 支付总金额
			double tTaxAmount = Arith.round(Double.parseDouble(StrTool.cTrim(tSSRS
					.GetText(i, 9))), 2);// 总税额
			double tNetAmount = Arith.round(Double.parseDouble(StrTool.cTrim(tSSRS
					.GetText(i, 10))), 2);// 总净额

			String tMsg = "第[" + i + "]条,保单号:[" + tPolNo + "],业务类型:[" + tType
					+ "],子业务类型:[" + tSubType + "],财务类型:[" + tFinaType
					+ "],金额:[" + tSumPay + "]";

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.2 找出其对应的明细记录
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LJSGetClaimDB tLJSGetClaimDB = new LJSGetClaimDB();
			tLJSGetClaimDB.setOtherNo(pClmNo);
			tLJSGetClaimDB.setOtherNoType("5");

			tLJSGetClaimDB.setPolNo(tPolNo);
			tLJSGetClaimDB.setFeeOperationType(tType);
			tLJSGetClaimDB.setSubFeeOperationType(tSubType);
			tLJSGetClaimDB.setFeeFinaType(tFinaType);

			tLJSGetClaimDB.setDutyCode(tDutyCode);
			tLJSGetClaimDB.setGetDutyKind(tGetDutyKind);
			tLJSGetClaimDB.setGetDutyCode(tGetDutyCode);

			LJSGetClaimSet tLJSGetClaimSet = tLJSGetClaimDB.query();

			logger.debug("----------------------------------------------------------------------------");
			logger.debug("--应付明细," + tMsg);
			logger.debug("--应付明细记录数：[" + tLJSGetClaimSet.size() + "]");
			logger.debug("----------------------------------------------------------------------------");

			if (tLJSGetClaimDB.mErrors.needDealError()) {
				this.mErrors.addOneError("审批通过时，查询应付明细," + tMsg + "，发生错误!!!!"
						+ tLJSGetClaimDB.mErrors.getError(0).errorMessage);
				return false;
			}

			if (tLJSGetClaimSet.size() == 0) {
				this.mErrors.addOneError("审批通过时，查询应付明细," + tMsg
						+ "，记录数为0，发生错误!!!!");
				return false;
			}

			LJSGetClaimSchema tLJSGetClaimSchema = tLJSGetClaimSet.get(1);
			LJAGetClaimSchema tLJAGetClaimSchema = new LJAGetClaimSchema();

			mReflections.transFields(tLJAGetClaimSchema, tLJSGetClaimSchema);
			tLJAGetClaimSchema.setActuGetNo(tGetNo); // 明细表的实付号
			tLJAGetClaimSchema.setGetNoticeNo(pGetNoticeNo);// 给付通知书号码
			tLJAGetClaimSchema.setPay(tSumPay);
			tLJAGetClaimSchema.setOPConfirmCode(mG.Operator);
			tLJAGetClaimSchema.setOPConfirmDate(CurrentDate);
			tLJAGetClaimSchema.setOPConfirmTime(CurrentTime);
			tLJAGetClaimSchema.setOperator(mG.Operator);
			tLJAGetClaimSchema.setMakeDate(CurrentDate);
			tLJAGetClaimSchema.setMakeTime(CurrentTime);
			tLJAGetClaimSchema.setModifyDate(CurrentDate);
			tLJAGetClaimSchema.setModifyTime(CurrentTime);

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.5 进行转年金处理
			 * 赔付金领取方式:1一次统一给付,2按年金方式领取 按年金方式领取时，赔款金额必须置为0
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (mGetMode.equals("2") && tType.equals("A")) {
				tLJAGetClaimSchema.setPay(0);
				tDSumPay = tDSumPay - tSumPay;// 实付总金额
				taxAmount = taxAmount-tTaxAmount; 
				netAmount = netAmount-tNetAmount;
			}

			tLJAGetClaimSaveSet.add(tLJAGetClaimSchema);
		}
		tLJAGetSchema.setTaxAmount(taxAmount);
		tLJAGetSchema.setNetAmount(netAmount);
		tLJAGetSchema.setSumGetMoney(tDSumPay); // 实付金额
		mMMap.put(tLJAGetSchema, "DELETE&INSERT");

		mMMap.put(tLJAGetClaimSaveSet, "DELETE&INSERT");
		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－－－财务数据处理－－－－－续涛修改－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 处理6.合同处理: 将LLContState表中的数据更新到LCContState表中
	 * 
	 * @return boolean
	 */
	private boolean getDealLCContState() {
		logger.debug("---------------------------------进行合同处理----开始---------------------------------");
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 查询理赔合同状态暂存表数据
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLContStateDB tLLContStateQueryDB = new LLContStateDB();
		tLLContStateQueryDB.setClmNo(mClmNo);
		LLContStateSet tLLContStateQuerySet = tLLContStateQueryDB.query();
		if (tLLContStateQueryDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLContStateQueryDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimConfirmPassBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询理赔合同终止记录暂存表时出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 循环理赔合同状态暂存表数据,进行处理
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LCContStateSet tLCContStateSaveSet = new LCContStateSet(); // 合同状态正式表
		LLContStateSet tLLContStateSaveSet = new LLContStateSet(); // 合同状态暂存表
		LCContStateSet tLCContStateNoTermSaveSet = new LCContStateSet();//
		LCContSet tLCContSaveSet = new LCContSet(); // 合同表
		LCPolSet tLCPolSaveSet = new LCPolSet(); // 保单险种表

		for (int j = 1; j <= tLLContStateQuerySet.size(); j++) {

			LLContStateSchema tLLContStateQuerySchema = tLLContStateQuerySet
					.get(j);

			LCContStateSchema tLCContStateSaveSchema = new LCContStateSchema();

			String tContNo = tLLContStateQuerySchema.getContNo();
			String tPolNo = tLLContStateQuerySchema.getPolNo();

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0
			 * 将准备删掉的合同状态正式表中旧的终止记录,同步复制到暂存表
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LCContStateDB tLCContStateExpDB = new LCContStateDB();
			tLCContStateExpDB.setContNo(tContNo);
			tLCContStateExpDB.setPolNo(tPolNo);
			tLCContStateExpDB.setStateType("Terminate");
			tLCContStateExpDB.setState("1");
			LCContStateSet tLCContStateExpSet = tLCContStateExpDB.query();

			if (tLCContStateExpDB.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCContStateExpDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "LLClaimConfirmPassBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询合同状态C表的终止记录时出错!";
				this.mErrors.addOneError(tError);
				return false;
			}

			LCContStateSchema tLCContStateExpSchema = new LCContStateSchema();
			LLContStateSchema tLLContStateSaveSchema = new LLContStateSchema();

			for (int m = 1; m <= tLCContStateExpSet.size(); m++) {
				tLCContStateExpSchema = tLCContStateExpSet.get(m);
				String tStartDate = tLCContStateExpSchema.getStartDate()
						.substring(0, 10);
				String tInsDate = this.mInsDate.substring(0, 10);

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.3
				 * 合同状态正式表中的终止记录 的时间 与暂存表相同,更改暂存表的状态信息
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				mReflections.transFields(tLLContStateSaveSchema,
						tLCContStateExpSchema);
				tLLContStateSaveSchema.setClmNo(this.mClmNo);

				if (tStartDate.equals(tInsDate)) {
					tLLContStateSaveSchema.setClmState("8");
					tLLContStateSaveSchema
							.setRemark("8--审批通过时从LCContState中转来已经删除的数据,终止的开始时间相同,在本表中已存在,回退时需要替换C表终止原因StateReason");
				} else {
					tLLContStateSaveSchema.setClmState("9");
					tLLContStateSaveSchema
							.setRemark("9--审批通过时从LCContState中转来已经删除的数据,终止的开始时间不相同,在本表中不存在,暂时存放,用于回退时恢复C表数据");
				}
				tLLContStateSaveSet.add(tLLContStateSaveSchema);
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0 删除合同状态正式表中旧的终止记录
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tSql = "delete from LCContState where 1=1 "
					+ " and ContNo='" + "?ContNo?" + "'" + " and PolNo='" + "?PolNo?"
					+ "'" + " and StateType='Terminate' and state='1'";
			SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
			sqlbv16.sql(tSql);
			sqlbv16.put("ContNo", tContNo);
			sqlbv16.put("PolNo", tPolNo);
			mMMap.put(sqlbv16, "DELETE");

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0 插入新的合同状态终止记录
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			mReflections.transFields(tLCContStateSaveSchema,
					tLLContStateQuerySchema);

			tLCContStateSaveSchema.setInsuredNo("000000");
			tLCContStateSaveSchema.setEndDate("");
			tLCContStateSaveSchema.setMakeDate(CurrentDate);
			tLCContStateSaveSchema.setMakeTime(CurrentTime);
			tLCContStateSaveSchema.setModifyDate(CurrentDate);
//			tLCContStateSaveSchema.setMakeTime(CurrentTime);
			tLCContStateSaveSchema.setModifyTime(CurrentTime);
			tLCContStateSaveSchema.setOperator(mG.Operator);
			tLCContStateSaveSet.add(tLCContStateSaveSchema);

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.0 把原来有效的记录的终止时间置值
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LCContStateSet tLCContStateNoTermSet = mLLClaimPubFunBL
					.getLCContState(tContNo, tPolNo, "Terminate", "0");

			if (tLCContStateNoTermSet != null) {
				String tEndDate = tLLContStateQuerySchema.getStartDate();
				tEndDate = PubFun.calDate(tEndDate, -1, "D", tEndDate);

				for (int z = 1; z <= tLCContStateNoTermSet.size(); z++) {
					LCContStateSchema tLCContStateNoTermSchema = tLCContStateNoTermSet
							.get(z);

					tLCContStateNoTermSchema.setInsuredNo("000000");
					tLCContStateNoTermSchema.setEndDate(tEndDate);
					tLCContStateNoTermSchema.setModifyDate(CurrentDate);
//					tLCContStateNoTermSchema.setMakeTime(CurrentTime);
					tLCContStateNoTermSchema.setModifyTime(CurrentTime);
					tLCContStateNoTermSchema.setOperator(mG.Operator);
					tLCContStateNoTermSaveSet.add(tLCContStateNoTermSchema);
				}
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No7.0
			 * 更新LCPol的AppFlag置为4 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = mLLClaimPubFunBL.getLCPol(tPolNo);
			if (tLCPolSchema == null) {
				this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
				return false;
			} else {
				tLCPolSchema.setAppFlag("4");
				tLCPolSchema.setModifyDate(CurrentDate);
//				tLCPolSchema.setMakeTime(CurrentTime);
				tLCPolSchema.setModifyTime(CurrentTime);
				tLCPolSchema.setOperator(mG.Operator);
				tLCPolSaveSet.add(tLCPolSchema);
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.0
			 * 更新LCCont的AppFlag置为4 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tMainPolNo = tLCPolSchema.getMainPolNo();

			if (tMainPolNo.equals(tPolNo)) {
				//主险终止附加险必须终止 09-04-07
				//由于多条合同，所以可能会导致之前的合同被clear
//				tLCPolSaveSet.clear();
				LCPolDB tLCPolDB = new LCPolDB();
				tLCPolDB.setMainPolNo(tPolNo);
//				tLCPolSaveSet = tLCPolDB.query();
				//zy 2010-01-07 
				 LCPolSet mainLCPolSet= tLCPolDB.query();
				for(int i=1;i<=mainLCPolSet.size();i++){
//					tLCPolSaveSet.get(i).setAppFlag("4");
//					tLCPolSaveSet.get(i).setModifyDate(CurrentDate);
//					tLCPolSaveSet.get(i).setMakeTime(CurrentTime);
//					tLCPolSaveSet.get(i).setOperator(mG.Operator);
					mainLCPolSet.get(i).setAppFlag("4");
					mainLCPolSet.get(i).setModifyDate(CurrentDate);
//					mainLCPolSet.get(i).setMakeTime(CurrentTime);
					mainLCPolSet.get(i).setModifyTime(CurrentTime);
					mainLCPolSet.get(i).setOperator(mG.Operator);
				}
				tLCPolSaveSet.add(mainLCPolSet);
				LCContSchema tLCContSchema = new LCContSchema();
				tLCContSchema = mLLClaimPubFunBL.getLCCont(tContNo);
				if (tLCContSchema == null) {
					this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
					return false;
				} else {
					tLCContSchema.setAppFlag("4");
					tLCContSchema.setModifyDate(CurrentDate);
//					tLCContSchema.setMakeTime(CurrentTime);
					tLCContSchema.setModifyTime(CurrentTime);
					// tLCContSchema.setOperator(mG.Operator);
					tLCContSaveSet.add(tLCContSchema);
				}
			}
		}

		if (tLCContSaveSet.size() > 0) {
			mMMap.put(tLCContSaveSet, "UPDATE");
		}

		if (tLCPolSaveSet.size() > 0) {
			mMMap.put(tLCPolSaveSet, "UPDATE");
		}

		// 调整提交顺序,以为对于344新增附加险当天做终止时,会发生重复删除的情况,把有效记录提前牺牲掉 周磊 2006-07-25
		if (tLCContStateNoTermSaveSet.size() > 0) {
			mMMap.put(tLCContStateNoTermSaveSet, "DELETE&INSERT");
		}

		if (tLCContStateSaveSet.size() > 0) {
			mMMap.put(tLCContStateSaveSet, "DELETE&INSERT");
		}

		if (tLLContStateSaveSet.size() > 0) {
			mMMap.put(tLLContStateSaveSet, "DELETE&INSERT");
		}

		logger.debug("---------------------------------进行合同处理----结束---------------------------------");

		return true;
	}

	/**
	 * 处理7.将理赔金转年金的数据同步到LCDuty,LCGet表中
	 * 
	 * @return
	 */
	private boolean getDealClmToPension() {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 将LLDuty同步到LCDuty表
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLDutyDB tLLDutyDB = new LLDutyDB();
		tLLDutyDB.setClmNo(mClmNo);
		tLLDutyDB.setOperationType("A");// 理赔金转年金
		LLDutySet tLLDutySet = tLLDutyDB.query();

		LCDutySet tLCDutySaveSet = new LCDutySet();
		for (int i = 1; i <= tLLDutySet.size(); i++) {
			LCDutySchema tLCDutySchema = new LCDutySchema();
			mReflections.transFields(tLCDutySchema, tLLDutySet.get(i));
			tLCDutySaveSet.add(tLCDutySchema);
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 将LLGet同步到LCGet表
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLGetDB tLLGetDB = new LLGetDB();
		tLLGetDB.setClmNo(mClmNo);
		tLLDutyDB.setOperationType("A");// 理赔金转年金
		LLGetSet tLLGetSet = tLLGetDB.query();

		LCGetSet tLCGetSaveSet = new LCGetSet();
		for (int i = 1; i <= tLLGetSet.size(); i++) {
			LCGetSchema tLCGetSchema = new LCGetSchema();
			mReflections.transFields(tLCGetSchema, tLLGetSet.get(i));
			tLCGetSaveSet.add(tLCGetSchema);
		}

		mMMap.put(tLCDutySaveSet, "DELETE&INSERT");
		mMMap.put(tLCGetSaveSet, "DELETE&INSERT");

		return true;
	}

	/**
	 * 处理8.豁免处理: 将LLExempt表的FreeFlag[免交标志], FreeStartDate[免交起期], FreeEndDate
	 * [免交止期],更新到LCPrem表中对应的数据
	 */
	private boolean getDealLLExempt() {
		logger.debug("---------------------------------进行豁免处理----开始---------------------------------");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 查询理赔处理的豁免信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLExemptDB tLLExemptDB = new LLExemptDB();
		tLLExemptDB.setClmNo(mClmNo);
		LLExemptSet tLLExemptSet = new LLExemptSet();
		tLLExemptSet = tLLExemptDB.query();
		if (tLLExemptDB.mErrors.needDealError()) {
			this.mErrors.addOneError("查询保费豁免记录时出错!");
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
		 * 根据查询理赔处理的豁免信息对LCPrem表进行操作 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LCPremSet tLCPremSet = new LCPremSet();
		for (int j = 1; j <= tLLExemptSet.size(); j++) {

			LLExemptSchema tLLExemptSchema = tLLExemptSet.get(j);
			String tFreeFlag = StrTool.cTrim(tLLExemptSchema.getFreeFlag());

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0
			 * 如果免交标志不为1[1表示免交]，继续循环 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (!tFreeFlag.equals("1")) {
				continue;
			}

			LCPremDB tLCPremDB = new LCPremDB();
			tLCPremDB.setPolNo(tLLExemptSet.get(j).getPolNo());
			tLCPremDB.setDutyCode(tLLExemptSet.get(j).getDutyCode());
			tLCPremDB.setPayPlanCode(tLLExemptSet.get(j).getPayPlanCode());

			if (tLCPremDB.getInfo() == false) {
				this.mErrors.addOneError("进行豁免处理时查询缴费信息失败!"
						+ tLCPremDB.mErrors.getError(0).errorMessage);
				return false;
			}

			LCPremSchema tLCPremSchema = new LCPremSchema();
			tLCPremSchema.setSchema(tLCPremDB.getSchema());

			tLCPremSchema.setFreeFlag(tLLExemptSet.get(j).getFreeFlag());
			tLCPremSchema.setFreeRate("1");
			tLCPremSchema.setFreeStartDate(tLLExemptSet.get(j)
					.getFreeStartDate());
			tLCPremSchema.setFreeEndDate(tLLExemptSet.get(j).getFreeEndDate());
			tLCPremSchema.setOperator(mG.Operator);
			tLCPremSchema.setModifyDate(CurrentDate);
			tLCPremSchema.setModifyTime(CurrentTime);
			tLCPremSet.add(tLCPremSchema);
			mLLExemptHMSet.add(tLLExemptSchema); // 保存被豁免的记录，用于对应收记录的处理
		}

		if (tLCPremSet.size() > 0) {
			mMMap.put(tLCPremSet, "DELETE&INSERT");
		}

		logger.debug("---------------------------------进行豁免处理----结束---------------------------------");
		return true;
	}

	/**
	 * 处理9.删除财务应收应付备份表对应数据:
	 */
	private boolean dealeTempData() {
		logger.debug("--------------------进行赔案暂存数据删除处理-----开始--------------------");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0
		 * 根据理赔应收总表暂存表LLJSPay对应应收总表LJSPay 1-如果总应收金额 = 0 在审批通过时需要删除 2-如果总应收金额 > 0
		 * 在审批通过时需要更新 3-如果是豁免的险种，不允许删除，生成一条负记录，同时冲减主记录
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		LLJSPayDB tLLJSPayDB = new LLJSPayDB();
		tLLJSPayDB.setClmNo(mClmNo);
		LLJSPaySet tLLJSPaySet = new LLJSPaySet();
		tLLJSPaySet = tLLJSPayDB.query();

		if (tLLJSPaySet != null && tLLJSPaySet.size() != 0) {
			LJSPaySet tLJSPayDeleteSet = new LJSPaySet(); // 用于保存删除的结果集
			LJSPayPersonSet tLJSPayPersonDeleteSet = new LJSPayPersonSet(); // 用于保存删除的结果集

			for (int j = 1; j <= tLLJSPaySet.size(); j++) {
				LLJSPaySchema tLLJSPaySchema = tLLJSPaySet.get(j);
				String tGetNoticeNo = StrTool.cTrim(tLLJSPaySchema
						.getGetNoticeNo());// 通知书号码

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.1
				 * 根据通知书号码查询财务应收总表LJSPay
				 * 
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				LJSPayDB tLJSPayDB = new LJSPayDB();
				tLJSPayDB.setGetNoticeNo(tGetNoticeNo);
				LJSPaySet tLJSPaySet = tLJSPayDB.query();
				if (tLJSPayDB.mErrors.needDealError() == true) {
					this.mErrors.addOneError("查询通知书号码为[" + tGetNoticeNo
							+ "]的个人应收汇总信息发生问题,查询出来的记录数[" + tLJSPaySet.size()
							+ "],此操作被终止!!!");
					return false;
				}

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.2
				 * 查询应收总表LJSPay的记录数为 1 ，认为已经回销了
				 * 
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				if (tLJSPaySet.size() != 1) {
					logger.debug("------------------------------------------------------");
					logger.debug("--通知书号码为[" + tGetNoticeNo
							+ "]的个人应收汇总信息为[" + tLJSPaySet.size()
							+ "],可能已经被回销!!!");
					logger.debug("------------------------------------------------------");
					this.mErrors.addOneError("查询通知书号码为[" + tGetNoticeNo
							+ "]的个人应收汇总信息发生问题,查询出来的记录数[" + tLJSPaySet.size()
							+ "],数据不唯一,数据可能已经被回销或被删除!!!");
					continue;
				}

				LJSPaySchema tLJSPaySchema = tLJSPaySet.get(1);

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.3 处理银行在途
				 * 
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				String tContNo = tLJSPaySchema.getOtherNo();
				String tBankOnTheWayFlag = StrTool.cTrim(tLJSPaySchema
						.getBankOnTheWayFlag());// 1--银行在途标志，不允许删除
				if (tBankOnTheWayFlag.equals("1")) {
					mErrors.addOneError("通知书号码[" + tGetNoticeNo + "],合同号["
							+ tContNo + "]的交费信息银行在途,不允许删除其应收记录,等回销后在进行此操作!!!");
					return false;
				}

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.4
				 * 处理暂交费，如果为空，可以删除应收汇总记录
				 * 
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				LJTempFeeSet tLJTempFeeSet = mLLClaimPubFunBL
						.getLJTempFee(tGetNoticeNo);
				if (tLJTempFeeSet != null) {
					mErrors.addOneError("通知书号码[" + tGetNoticeNo + "],合同号["
							+ tContNo
							+ "]存在暂交费记录,不允许删除其应收记录，请先进行暂交费退费,然后再进行此操作!!!");
					return false;
				}

				tLJSPayDeleteSet.add(tLJSPaySchema);

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.1
				 * 获取该通知书号下的理赔暂存应收个人明细信息LLJSPayPerson
				 * 
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				LLJSPayPersonDB tLLJSPayPersonDB = new LLJSPayPersonDB();
				tLLJSPayPersonDB.setGetNoticeNo(tGetNoticeNo);
				LLJSPayPersonSet tLLJSPayPersonSet = tLLJSPayPersonDB.query();

				if (tLLJSPayPersonSet == null && tLLJSPayPersonSet.size() == 0) {
					this.mErrors.addOneError("查询通知书号码为[" + tGetNoticeNo
							+ "]的个人应收明细表信息发生问题,查询出来的记录数["
							+ tLLJSPayPersonSet.size() + "],此操作被终止!!!");
					return false;
				}

				for (int m = 1; m <= tLLJSPayPersonSet.size(); m++) {
					/**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.4
					 * 计算理赔暂存应收个人明细表信息 总实交金额 的合计
					 * 
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					 */
					LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
					LLJSPayPersonSchema tLLJSPayPersonSchema = tLLJSPayPersonSet
							.get(m);
					mReflections.transFields(tLJSPayPersonSchema,
							tLLJSPayPersonSchema);
					tLJSPayPersonDeleteSet.add(tLJSPayPersonSchema);
				}

			}// for

			mMMap.put(tLJSPayDeleteSet, "DELETE");
			mMMap.put(tLJSPayPersonDeleteSet, "DELETE");

		}// if

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0
		 * 理赔应付总表暂存表LLJSGet对应付总表LJSGet
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLJSGetDB tLLJSGetDB = new LLJSGetDB();
		tLLJSGetDB.setClmNo(mClmNo);
		LLJSGetSet tLLJSGetSet = tLLJSGetDB.query();

		if (tLLJSGetSet != null && tLLJSGetSet.size() != 0) {
			LJSGetSet tLJSGetSaveSet = new LJSGetSet();
			for (int j = 1; j <= tLLJSGetSet.size(); j++) {
				LLJSGetSchema tLLJSGetSchema = tLLJSGetSet.get(j);
				LJSGetSchema tLJSGetSchema = new LJSGetSchema();

				mReflections.transFields(tLJSGetSchema, tLLJSGetSchema);
				tLJSGetSaveSet.add(tLJSGetSchema);

			}
			mMMap.put(tLJSGetSaveSet, "DELETE");
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0
		 * 理赔给付表(生存领取_应付)暂存表LLJSGetDraw对应给付表(生存领取_应付)表LJSGetDraw
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLJSGetDrawDB tLLJSGetDrawDB = new LLJSGetDrawDB();
		tLLJSGetDrawDB.setClmNo(mClmNo);
		LLJSGetDrawSet tLLJSGetDrawSet = tLLJSGetDrawDB.query();

		if (tLLJSGetDrawSet != null && tLLJSGetDrawSet.size() != 0) {
			LJSGetDrawSet tLJSGetDrawSaveSet = new LJSGetDrawSet();
			for (int j = 1; j <= tLLJSGetDrawSet.size(); j++) {
				LLJSGetDrawSchema tLLJSGetDrawSchema = tLLJSGetDrawSet.get(j);
				LJSGetDrawSchema tLJSGetDrawSchema = new LJSGetDrawSchema();

				mReflections.transFields(tLJSGetDrawSchema, tLLJSGetDrawSchema);
				tLJSGetDrawSaveSet.add(tLJSGetDrawSchema);

			}
			mMMap.put(tLJSGetDrawSaveSet, "DELETE");
		}

		logger.debug("--------------------进行赔案暂存数据删除处理-----结束--------------------");
		return true;
	}

	/**
	 * 处理10. 产生的数据插入到打印管理表:
	 */
	private boolean dealData09() {
		ExeSQL tExeSQL = new ExeSQL();

		// 查询立案信息
		LLCaseDB tLLCaseDB = new LLCaseDB();
		String tSSql = "select * from llcase where 1=1 " + " and caseno = '"
				+ "?clmno?" + "'";
		SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
		sqlbv17.sql(tSSql);
		sqlbv17.put("clmno", mClmNo);
		LLCaseSet tLLCaseSet = tLLCaseDB.executeQuery(sqlbv17);
		mCusNo = tLLCaseSet.get(1).getCustomerNo();
		mMngCom = tLLCaseSet.get(1).getMngCom();

		LLRegisterDB tLLRegisterDB = new LLRegisterDB();
		LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
		tLLRegisterDB.setRgtNo(mClmNo);
		if (tLLRegisterDB.getInfo()) {
			tLLRegisterSchema.setSchema(tLLRegisterDB.getSchema());
		}
		mRgtDate = tLLRegisterSchema.getRgtDate(); // 立案日期

		// tSSql = "select to_date(rgtdate,'yyyy-mm-dd') from llregister where
		// 1=1 "
		// + " and rgtno = '" + mClmNo + "'";
		// mmRgtDate = tExeSQL.getOneValue(tSSql); //立案日期

		logger.debug("################################################");
		logger.debug("#mmRgtDate:" + mRgtDate);
		logger.debug("################################################");

		// 查询赔付结论
		LLClaimDB tLLClaimDB = new LLClaimDB();
		tLLClaimDB.setClmNo(mClmNo);
		LLClaimSet tLLClaimSet = tLLClaimDB.query();
		String tGType = tLLClaimSet.get(1).getGiveType();
		if (tGType.equals("1")) // 赔案拒付
		{
			if (!insertLOPRTManager("PCT005")) // 插入理赔决定通知书－拒付[赔案层面]
			{
				return false;
			}
			String tSn = "select count(1) from llbalance a where 1=1"
					+ " and a.ClmNo  = '" + "?ClmNo?" + "'"
					+ " and a.feeoperationtype not in ('A','B') ";
			SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
			sqlbv18.sql(tSSql);
			sqlbv18.put("ClmNo", mClmNo);
			String tRn = tExeSQL.getOneValue(sqlbv18);
			if (!tRn.equals("0")) {
				if (!insertLOPRTManager("PCT010")) // 插入批单-理赔给付批注[个人]
				{
					return false;
				}
			}
		} else // 赔案给付
		{
			if (!insertLOPRTManager("PCT010")) // 插入批单-理赔给付批注[个人]
			{
				return false;
			}
			if (!insertLOPRTManager("PCT020")) //理赔给付通知书
			{
				return false;
			}
		}

		// 插入理赔决定通知书－拒付[保项层面] llclaimdetail
		String tSSql06 = "select count(1) from llclaimdetail a where 1=1"
				+ " and a.ClmNo  = '" + "?ClmNo?" + "'" + " and a.GiveType = '1' ";
		SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
		sqlbv19.sql(tSSql06);
		sqlbv19.put("ClmNo", mClmNo);
		String tCount06 = tExeSQL.getOneValue(sqlbv19);
		if (tCount06 != null && !tCount06.equals("0")) {
			if (!insertLOPRTManager("PCT006")) // 插入理赔决定通知书－拒付[保项层面]
			{
				return false;
			}
		}

		/**
		 * 2009-02-27 zhangzheng 取消医疗给付清单
		 * 
		 */
//		// 插入医疗理赔给付清单[个人] llclaimdetail
//		String tSSql15 = "select count(1) from llclaimdetail a where 1=1"
//				+ " and a.ClmNo  = '" + mClmNo + "'" + " and a.GiveType = '0' "
//				+ " and substr(a.GetDutyKind,2,2) = '00' ";
//		String tCount15 = tExeSQL.getOneValue(tSSql15);
//		if (tCount15 != null && !tCount15.equals("0")) {
//			if (!insertLOPRTManager("PCT015")) // 插入医疗理赔给付清单[个人] llclaimdetail
//			{
//				return false;
//			}
//		}

		/**
		 * 2009-02-27 zhangzheng 取消合同处理批注
		 * 
		 */
		// 插入批单-合同处理批注 llcontstate
//		String tSSql13 = "select count(1) from llcontstate a where 1=1"
//				+ " and a.ClmNo  = '" + mClmNo + "'";
//		// String tSSql13 = "select count(1) from LLBalance a where 1=1"
//		// + " and a.ClmNo = '" + mClmNo + "'"
//		// + " and substr(a.FeeOperationType,1,1) in ('C') ";
//		String tCount13 = tExeSQL.getOneValue(tSSql13);
//		if (tCount13 != null && !tCount13.equals("0")) {
//			if (!insertLOPRTManager("PCT013")) // 插入批单-合同处理批注
//			{
//				return false;
//			}
//		}

		/**
		 * 2009-02-27 zhangzheng 取消豁免保费批注
		 * 
		 */
//		// 插入批单-豁免保费批注 LLExempt
//		String tSSql12 = "select count(1) from LLExempt where 1=1 "
//				+ " and ClmNo = '" + mClmNo + "'";
//		String tCount12 = tExeSQL.getOneValue(tSSql12);
//		if (tCount12 != null && !tCount12.equals("0")) {
//			if (!insertLOPRTManager("PCT012"))// 插入批单-豁免保费批注
//			{
//				return false;
//			}
//		}

		// //插入批单-理赔预付保险金批注 LLPrepayClaim-----------------------已在预付确认时生成 周磊
		// 2005-8-30 1:15
		// String tSSql14 = "select count(1) from LLPrepayClaim where 1=1 "
		// + " and ClmNo = '" + mClmNo + "'";
		// String tCount14 = tExeSQL.getOneValue(tSSql14);
		// if (tCount14 != null && !tCount14.equals("0"))
		// {
		// if (!insertLOPRTManager("PCT014")) //插入批单-理赔预付保险金批注
		// {
		// return false;
		// }
		// }

		mMMap.put(mLOPRTManagerSet, "DELETE&INSERT");
		return true;
	}

	/**
	 * 添加打印数据 2005-8-16 14:49
	 * 
	 * @return boolean
	 */
	private boolean insertLOPRTManager(String tCode) {

		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

		// 生成印刷流水号
		String strNolimit = PubFun.getNoLimit(mMngCom);
		logger.debug("strlimit=" + strNolimit);
		String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNolimit);
		logger.debug("-----生成的LOPRTManager的印刷流水号= " + tPrtSeq);

		tLOPRTManagerSchema.setPrtSeq(tPrtSeq); // 印刷流水号
		tLOPRTManagerSchema.setOtherNo(mClmNo); // 对应其它号码
		tLOPRTManagerSchema.setOtherNoType("05"); // 其它号码类型--05赔案号
		tLOPRTManagerSchema.setCode(tCode); // 单据编码--单证通知书
		tLOPRTManagerSchema.setManageCom(mMngCom); // 立案管理机构
		tLOPRTManagerSchema.setReqCom(mG.ComCode); // 发起机构
		tLOPRTManagerSchema.setReqOperator(mG.Operator); // 发起人
		tLOPRTManagerSchema.setPrtType("0"); // 打印方式

		// 打印状态：0都可用，1在线已打，2批打已打，3批打不打,4批打未开放
		if (!tCode.equals("PCT015")) {
			tLOPRTManagerSchema.setStateFlag("4"); // 控制单证提交批打的时点
		} else {
			tLOPRTManagerSchema.setStateFlag("3"); // 医疗给付清单置为批打不打状态
		}

		tLOPRTManagerSchema.setMakeDate(CurrentDate); // 入机日期(报案日期)
		tLOPRTManagerSchema.setMakeTime(CurrentTime); // 入机时间
		tLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
		tLOPRTManagerSchema.setStandbyFlag1(CurrentDate); // 批打检索日期
		tLOPRTManagerSchema.setStandbyFlag4(mCusNo); // 客户号码
		tLOPRTManagerSchema.setStandbyFlag5(mRgtDate); // 立案日期
		tLOPRTManagerSchema.setStandbyFlag6("50"); // 赔案状态

		mLOPRTManagerSet.add(tLOPRTManagerSchema);
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
			mInputData.add(mMMap);
			mResult.add(mMMap);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimConfirmPassBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 提交数据
	 * 
	 * @return
	 */
	private boolean pubSubmit() {
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimCalCheckBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * [废弃不用]删除财务应收应付备份表对应数据:
	 */
	private boolean dealeTempDataStop() {
		logger.debug("--------------------进行赔案暂存数据删除处理-----开始--------------------");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0
		 * 根据理赔应收总表暂存表LLJSPay对应应收总表LJSPay 1-如果总应收金额 = 0 在审批通过时需要删除 2-如果总应收金额 > 0
		 * 在审批通过时需要更新 3-如果是豁免的险种，不允许删除，生成一条负记录，同时冲减主记录
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLJSPayDB tLLJSPayDB = new LLJSPayDB();
		tLLJSPayDB.setClmNo(mClmNo);
		LLJSPaySet tLLJSPaySet = new LLJSPaySet();
		tLLJSPaySet = tLLJSPayDB.query();

		if (tLLJSPaySet != null && tLLJSPaySet.size() != 0) {
			for (int j = 1; j <= tLLJSPaySet.size(); j++) {
				LLJSPaySchema tLLJSPaySchema = tLLJSPaySet.get(j);
				String tGetNoticeNo = StrTool.cTrim(tLLJSPaySchema
						.getGetNoticeNo());// 通知书号码

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.1
				 * 根据通知书号码查询财务应收总表LJSPay
				 * 
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				LJSPayDB tLJSPayDB = new LJSPayDB();
				tLJSPayDB.setGetNoticeNo(tGetNoticeNo);
				LJSPaySet tLJSPaySet = tLJSPayDB.query();
				if (tLJSPayDB.mErrors.needDealError() == true) {
					this.mErrors.addOneError("查询通知书号码为[" + tGetNoticeNo
							+ "]的个人应收汇总信息发生问题,查询出来的记录数[" + tLJSPaySet.size()
							+ "],此操作被终止!!!");
					return false;
				}

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.2
				 * 查询应收总表LJSPay的记录数为 1 ，认为已经回销了
				 * 
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				if (tLJSPaySet.size() != 1) {
					logger.debug("------------------------------------------------------");
					logger.debug("--通知书号码为[" + tGetNoticeNo
							+ "]的个人应收汇总信息为[" + tLJSPaySet.size()
							+ "],可能已经被回销!!!");
					logger.debug("------------------------------------------------------");
					this.mErrors.addOneError("查询通知书号码为[" + tGetNoticeNo
							+ "]的个人应收汇总信息发生问题,查询出来的记录数[" + tLJSPaySet.size()
							+ "],数据不唯一，不能进行审批通过处理!!!");
					return false;
				}

				LJSPaySchema tLJSPaySchema = tLJSPaySet.get(1);

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.3 处理银行在途
				 * 
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				String tContNo = tLJSPaySchema.getOtherNo();
				String tBankOnTheWayFlag = StrTool.cTrim(tLJSPaySchema
						.getBankOnTheWayFlag());// 1--银行在途标志，不允许删除
				if (tBankOnTheWayFlag.equals("1")) {
					mErrors.addOneError("通知书号码[" + tGetNoticeNo + "],合同号["
							+ tContNo + "]的交费信息银行在途,不允许删除其应收记录,等回销后在进行此操作!!!");
					return false;
				}

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.4
				 * 处理暂交费，如果为空，可以删除应收汇总记录
				 * 
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				LJTempFeeSet tLJTempFeeSet = mLLClaimPubFunBL
						.getLJTempFee(tGetNoticeNo);
				if (tLJTempFeeSet != null) {
					mErrors.addOneError("通知书号码[" + tGetNoticeNo + "],合同号["
							+ tContNo
							+ "]存在暂交费记录,不允许删除其应收记录，请先进行暂交费退费,然后再进行此操作!!!");
					return false;
				}

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.1
				 * 获取该通知书号下的理赔暂存应收个人明细信息LLJSPayPerson
				 * 
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				LLJSPayPersonDB tLLJSPayPersonDB = new LLJSPayPersonDB();
				tLLJSPayPersonDB.setGetNoticeNo(tGetNoticeNo);
				LLJSPayPersonSet tLLJSPayPersonSet = tLLJSPayPersonDB.query();

				if (tLLJSPayPersonSet == null && tLLJSPayPersonSet.size() == 0) {
					this.mErrors.addOneError("查询通知书号码为[" + tGetNoticeNo
							+ "]的个人应收明细表信息发生问题,查询出来的记录数["
							+ tLLJSPayPersonSet.size() + "],此操作被终止!!!");
					return false;
				}

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.2 定义变量
				 * 
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				double tDSumDuePayMoney = Arith.round(tLLJSPaySchema
						.getSumDuePayMoney(), 0);// 总表数据的金额

				boolean tBHM = false;// 豁免标志
				double tDSumActuPayMoney = 0;
				tDSumActuPayMoney = Double
						.parseDouble(new DecimalFormat("0.00")
								.format(tDSumActuPayMoney));

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.3
				 * 计算理赔暂存应收个人明细表信息 总实交金额 的合计
				 * 
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				LJSPayPersonSet tLJSPayPersonSaveSet = new LJSPayPersonSet(); // 用于保存UPDATE
				LJSPayPersonSet tLJSPayPersonDeleSet = new LJSPayPersonSet(); // 用于保存DELETE

				for (int m = 1; m <= tLLJSPayPersonSet.size(); m++) {
					/**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.4
					 * 计算理赔暂存应收个人明细表信息 总实交金额 的合计
					 * 
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					 */
					LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
					LLJSPayPersonSchema tLLJSPayPersonSchema = tLLJSPayPersonSet
							.get(m);
					tDSumActuPayMoney = tDSumActuPayMoney
							+ tLLJSPayPersonSchema.getSumActuPayMoney();

					mReflections.transFields(tLJSPayPersonSchema,
							tLLJSPayPersonSchema);

					/**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.5
					 * 判断该保单是否被豁免,如果被豁免需要生成一条负的应收明细记录
					 * 
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					 */
					String tPolNo = StrTool.cTrim(tLLJSPayPersonSchema
							.getPolNo());

					if (mLLExemptHMSet.size() > 0) {
						/**
						 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.6
						 * 如果有豁免，放入准备更新的集合
						 * 
						 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
						 */
						for (int k = 1; k <= mLLExemptHMSet.size(); k++) {
							if (tPolNo.equals(StrTool.cTrim(mLLExemptHMSet.get(
									k).getPolNo()))) {
								tBHM = true;// 表示有豁免信息了。

								tLJSPayPersonSchema
										.setSumDuePayMoney(-tLJSPayPersonSchema
												.getSumDuePayMoney());
								tLJSPayPersonSchema
										.setSumActuPayMoney(-tLJSPayPersonSchema
												.getSumActuPayMoney());
								tLJSPayPersonSchema.setPayType("HM");

								tLJSPayPersonSaveSet.add(tLJSPayPersonSchema);
							} else {
								tLJSPayPersonDeleSet.add(tLJSPayPersonSchema);
							}

						}
					}
					/**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.7
					 * 如果无豁免，放入准备删除的集合
					 * 
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					 */
					else {
						tLJSPayPersonDeleSet.add(tLJSPayPersonSchema);
					}
				}
				// mMMap.put(tLJSPayPersonSaveSet, "DELETE&INSERT");
				mMMap.put(tLJSPayPersonSaveSet, "DELETE");
				mMMap.put(tLJSPayPersonDeleSet, "DELETE");

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.7 总表的金额 -
				 * 明细表的总金额 如果金额 = 0 在审批通过时需要删除 如果金额 > 0 在审批通过时需要更新
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				double tDSum = 0;
				tDSum = Arith.round(tDSumDuePayMoney - tDSumActuPayMoney, 0);
				logger.debug("---------------------------------------------------------------------------");
				logger.debug("--通知书号码为[" + tGetNoticeNo + "],差值:["
						+ tDSum + "],总应收金额:[" + tDSumDuePayMoney
						+ "],个人总应收金额:[" + tDSumActuPayMoney + "]");

				tLJSPaySchema.setSumDuePayMoney(tDSum);

				if (tBHM == true) {
					// mMMap.put(tLJSPaySchema, "DELETE&INSERT");
					mMMap.put(tLJSPaySchema, "DELETE");
					logger.debug("--执行更新");
				} else {
					mMMap.put(tLJSPaySchema, "DELETE");
					logger.debug("--执行删除");
				}
				logger.debug("---------------------------------------------------------------------------");

			}// for
		}// if

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
		 * 理赔应收个人交费暂存表LLJSPayPerson对应收个人交费表LJSPayPerson
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 * 
		 * LLJSPayPersonDB tLLJSPayPersonDB = new LLJSPayPersonDB();
		 * tLLJSPayPersonDB.setClmNo(mClmNo); LLJSPayPersonSet tLLJSPayPersonSet =
		 * tLLJSPayPersonDB.query();
		 * 
		 * if (tLLJSPayPersonSet != null && tLLJSPayPersonSet.size() != 0) {
		 * LJSPayPersonSet tLJSPayPersonSaveSet = new LJSPayPersonSet(); for
		 * (int j = 1; j <= tLLJSPayPersonSet.size(); j++) { LLJSPayPersonSchema
		 * tLLJSPayPersonSchema = tLLJSPayPersonSet.get(j); LJSPayPersonSchema
		 * tLJSPayPersonSchema = new LJSPayPersonSchema();
		 * 
		 * mReflections.transFields(tLJSPayPersonSchema,tLLJSPayPersonSchema);
		 * tLJSPayPersonSaveSet.add(tLJSPayPersonSchema);
		 *  } mMMap.put(tLJSPayPersonSaveSet, "DELETE"); }
		 */

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0
		 * 理赔应付总表暂存表LLJSGet对应付总表LJSGet
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLJSGetDB tLLJSGetDB = new LLJSGetDB();
		tLLJSGetDB.setClmNo(mClmNo);
		LLJSGetSet tLLJSGetSet = tLLJSGetDB.query();

		if (tLLJSGetSet != null && tLLJSGetSet.size() != 0) {
			LJSGetSet tLJSGetSaveSet = new LJSGetSet();
			for (int j = 1; j <= tLLJSGetSet.size(); j++) {
				LLJSGetSchema tLLJSGetSchema = tLLJSGetSet.get(j);
				LJSGetSchema tLJSGetSchema = new LJSGetSchema();

				mReflections.transFields(tLJSGetSchema, tLLJSGetSchema);
				tLJSGetSaveSet.add(tLJSGetSchema);

			}
			mMMap.put(tLJSGetSaveSet, "DELETE");
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0
		 * 理赔给付表(生存领取_应付)暂存表LLJSGetDraw对应给付表(生存领取_应付)表LJSGetDraw
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLJSGetDrawDB tLLJSGetDrawDB = new LLJSGetDrawDB();
		tLLJSGetDrawDB.setClmNo(mClmNo);
		LLJSGetDrawSet tLLJSGetDrawSet = tLLJSGetDrawDB.query();

		if (tLLJSGetDrawSet != null && tLLJSGetDrawSet.size() != 0) {
			LJSGetDrawSet tLJSGetDrawSaveSet = new LJSGetDrawSet();
			for (int j = 1; j <= tLLJSGetDrawSet.size(); j++) {
				LLJSGetDrawSchema tLLJSGetDrawSchema = tLLJSGetDrawSet.get(j);
				LJSGetDrawSchema tLJSGetDrawSchema = new LJSGetDrawSchema();

				mReflections.transFields(tLJSGetDrawSchema, tLLJSGetDrawSchema);
				tLJSGetDrawSaveSet.add(tLJSGetDrawSchema);

			}
			mMMap.put(tLJSGetDrawSaveSet, "DELETE");
		}

		logger.debug("--------------------进行赔案暂存数据删除处理-----结束--------------------");
		return true;
	}

	public static void main(String[] args) {

		LLReportSchema tLLReportSchema = new LLReportSchema();

		String tClmNo = "90000034771"; /* 赔案号 */

		LLRegisterDB tLLRegisterDB = new LLRegisterDB();
		tLLRegisterDB.setRgtNo(tClmNo);
		if (tLLRegisterDB.getInfo() == false) {
			logger.debug("------------------------------------------------------");
			logger.debug("--查询立案信息失败!");
			logger.debug("------------------------------------------------------");
		}

		String tClmState = tLLRegisterDB.getClmState();
		String tRgtClass = tLLRegisterDB.getRgtClass();

		GlobalInput tG = new GlobalInput();
		tG.Operator = tLLRegisterDB.getOperator();
		tG.ManageCom = tLLRegisterDB.getMngCom();
		tG.ComCode = tLLRegisterDB.getMngCom();

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("RptNo", tClmNo);

		VData tVData = new VData();
		tVData.addElement(tG);
		tVData.addElement(tTransferData);

		LLClaimConfirmPassBL tLLClaimConfirmPassBL = new LLClaimConfirmPassBL();
		tLLClaimConfirmPassBL.submitData(tVData, "");
		// tLLClaimConfirmPassBL.pubSubmit();
		int n = tLLClaimConfirmPassBL.mErrors.getErrorCount();
		logger.debug("===============================================================");
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLClaimConfirmPassBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}

}
