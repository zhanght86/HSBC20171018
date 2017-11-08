package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import java.util.Hashtable;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LZCardAppDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LZCardAppSchema;
import com.sinosoft.lis.schema.LZCardSchema;
import com.sinosoft.lis.vschema.LAAgentSet;
import com.sinosoft.lis.vschema.LABranchGroupSet;
import com.sinosoft.lis.vschema.LZCardAppSet;
import com.sinosoft.lis.vschema.LZCardSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:单证管理单证发放操作
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author 周平
 * @version 1.0
 */

public class CertSendOutBL extends CertifyBO {
private static Logger logger = Logger.getLogger(CertSendOutBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/* 私有成员 */
	private String mszOperate = "";

	private String mszTakeBackNo = "";

	/* 业务相关的数据 */
	private GlobalInput globalInput = new GlobalInput();

	private LZCardSet mLZCardSet = new LZCardSet();

	private LZCardAppSet mLZCardAppSet = new LZCardAppSet();

	private boolean m_bLimitFlag = true;;

	private Hashtable mParams = null;

	private VData mResult = new VData();

	// 记录下当前操作到哪一条记录，如果操作没有成功完成，给用户返回所有未能成功处理的数据。
	private int m_nOperIndex = 0;

	public CertSendOutBL() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		mszOperate = cOperate;

		if (!getInputData(cInputData))
		{
			buildFailSet();
			return false;
		}

		if (!dealData()) {
			buildFailSet();
			return false;
		}

		return true;
	}

	// 从输入数据中得到所有对象
	// 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	private boolean getInputData(VData vData) {
		if (mszOperate.equals("INSERT")) {
			globalInput.setSchema((GlobalInput) vData.getObjectByObjectName("GlobalInput", 0));
			mLZCardSet.set((LZCardSet) vData.getObjectByObjectName("LZCardSet", 0));
		} else if (mszOperate.equals("ADD")) {// 增领单证，将页面LZCardAppSet转化为mLZCardSet
			globalInput.setSchema((GlobalInput) vData.getObjectByObjectName("GlobalInput", 0));
			mLZCardSet.set((LZCardSet) vData.getObjectByObjectName("LZCardSet", 0));

			for (int i = 1; i <= mLZCardSet.size(); i++) {
				LZCardSchema tLZCardSchema = mLZCardSet.get(i);
				LZCardAppDB tLZCardAppDB = new LZCardAppDB();
				tLZCardAppDB.setApplyNo(tLZCardSchema.getApplyNo());
				if (tLZCardAppDB.getInfo()) {
					if (tLZCardSchema.getStartNo() != null && !tLZCardSchema.getStartNo().equals("")
							&& tLZCardSchema.getEndNo() != null && !tLZCardSchema.getEndNo().equals("")) {
						long inCount = CertifyFunc.bigIntegerDiff(tLZCardSchema.getEndNo(), tLZCardSchema
								.getStartNo()) + 1;// 页面输入起止号之差
						if (inCount != tLZCardAppDB.getSumCount()) {
							buildError("getInputData", "输入实际领取数量【" + inCount + "】不等于申请增领单证数量【"
									+ tLZCardAppDB.getSumCount() + "】");
							return false;
						}
					}
				}
			}
		} else if (mszOperate.equals("BATCH")) {
			globalInput.setSchema((GlobalInput) vData.getObjectByObjectName("GlobalInput", 0));
			mLZCardSet.set((LZCardSet) vData.getObjectByObjectName("LZCardSet", 0));
		} else {
			buildError("getInputData", "不支持的操作字符串");
			return false;
		}
		long cardnum=0;
		for (int nIndex = 1; nIndex <= mLZCardSet.size(); nIndex++)
		{
			LZCardSchema tLZCardSchema =mLZCardSet.get(nIndex);
			char cFlag = tLZCardSchema.getReceiveCom().charAt(0);
			if ((cFlag == 'B' || cFlag == 'D' || tLZCardSchema.getReceiveCom().length() == 9))
			{
			//20101-02-01增加单证数量的控制
			if( !"".equals(tLZCardSchema.getEndNo()) && !"".equals(tLZCardSchema.getStartNo()))
			cardnum=cardnum+ CertifyFunc.bigIntegerDiff(tLZCardSchema.getEndNo(), tLZCardSchema.getStartNo()) + 1;	
			else
			cardnum=cardnum+tLZCardSchema.getSumCount();
			}
		}
		if(cardnum>10000)
		{
			CError.buildErr(this, "一次单证操作的单证数量不能超过10000张，请减少单证数量");
			return false;
		}

		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData() {
		if (mszOperate.equals("INSERT") || mszOperate.equals("ADD")) {
			// 产生回收清算单号
			mszTakeBackNo = PubFun1.CreateMaxNo("TAKEBACKNO", PubFun.getNoLimit(globalInput.ComCode));

			m_nOperIndex = 0;
			for (int nIndex = 0; nIndex < mLZCardSet.size(); nIndex++) {
				LZCardSchema tLZCardSchema = mLZCardSet.get(nIndex + 1);

				// 校验输入的发放者和接收者
				if (!CertifyFunc.verifyComs(globalInput, tLZCardSchema.getSendOutCom(), tLZCardSchema
						.getReceiveCom())) {
					mErrors.copyAllErrors(CertifyFunc.mErrors);
					return false;
				}

				// 校验代理人最大发放数量
				if (mszOperate.equals("INSERT") && !CertifyFunc.verifyMaxCount(tLZCardSchema)) {
					mErrors.copyAllErrors(CertifyFunc.mErrors);
					return false;
				}

				// 对传入的单证信息进行格式化。
				LZCardSet newLZCardSet = new LZCardSet();
				newLZCardSet = CertifyFunc.formatCardList(tLZCardSchema);
				if (newLZCardSet == null) {
					mErrors.copyAllErrors(CertifyFunc.mErrors);
					return false;
				}

				// 处理数据
				VData vResult = new VData();
				for (int nCount = 0; nCount < newLZCardSet.size(); nCount++) {
					LZCardSchema newLZCardSchema = newLZCardSet.get(nCount + 1);
					newLZCardSchema.setTakeBackNo(mszTakeBackNo);
					newLZCardSchema.setApplyNo(tLZCardSchema.getApplyNo());

					VData vOneResult = new VData();
					// 进行单证拆分操作
					if (!CertifyFunc.splitCertifySendOut(globalInput, newLZCardSchema, vOneResult)) {
						mErrors.copyAllErrors(CertifyFunc.mErrors);
						return false;
					}
					vResult.add(vOneResult);
				}

				// 增领单证更新其申请状态
				if (mszOperate.equals("ADD")) {
					LZCardAppDB tLZCardAppDB = new LZCardAppDB();
					tLZCardAppDB.setApplyNo(tLZCardSchema.getApplyNo());
					tLZCardAppDB.getInfo();
					tLZCardAppDB.setStateFlag("3");
					tLZCardAppDB.setStartNo(tLZCardSchema.getStartNo());
					tLZCardAppDB.setEndNo(tLZCardSchema.getEndNo());

					VData vOneResult = new VData();
					vOneResult.add(0, null);
					vOneResult.add(1, null);
					vOneResult.add(2, null);
					vOneResult.add(3, null);
					vOneResult.add(4, null);
					vOneResult.add(5, null);
					vOneResult.add(6, null);
					vOneResult.add(7, null);
					vOneResult.set(7, tLZCardAppDB.getSchema());
					vResult.add(vOneResult);
				}

				// 保存数据
				CertSendOutBLS tCertSendOutBLS = new CertSendOutBLS();
				if (!tCertSendOutBLS.submitData(vResult, "INSERT")) {
					if (tCertSendOutBLS.mErrors.needDealError()) {
						mErrors.copyAllErrors(tCertSendOutBLS.mErrors);
						return false;
					} else {
						buildError("dealOne", "CertSendOutBL出错，但是没有提供详细的信息");
						return false;
					}
				}
				m_nOperIndex++; // 记录下当前操作到哪一条记录
			}
		} else if (mszOperate.equals("BATCH")) {
			// 清空日志表
			clearLog();

			// 产生回收清算单号
			mszTakeBackNo = PubFun1.CreateMaxNo("TAKEBACKNO", PubFun.getNoLimit(globalInput.ComCode));
			mResult.clear();
			mResult.add(mszTakeBackNo);

			m_nOperIndex = 0;
			for (int nIndex = 0; nIndex < mLZCardSet.size(); nIndex++) {
				LZCardSchema tLZCardSchema = mLZCardSet.get(nIndex + 1);
				tLZCardSchema.setTakeBackNo(mszTakeBackNo);
				if (!batchSendOut(tLZCardSchema)) {
					continue;
				}
				m_nOperIndex++; // 记录下当前操作到哪一条记录
			}
		} else {
			buildError("dealData", "不支持的操作字符串");
			return false;
		}
		return true;
	}

	/**
	 * 处理一条单证发放的信息。
	 * 
	 * @param aLZCardSchema
	 * @return
	 */
	private boolean dealOne(LZCardSchema aLZCardSchema, VData vResult) {
		// 进行单证拆分操作
		if (!CertifyFunc.splitCertifySendOut(globalInput, aLZCardSchema, vResult)) {
			mErrors.copyAllErrors(CertifyFunc.mErrors);
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 对于每一个发放者、接收者、单证类型。执行下面的函数。 此时的接收者，应该是一个展业机构的代码。
	 * 
	 * @param aLZCardSchema
	 * @return
	 */
	private boolean batchSendOut(LZCardSchema aLZCardSchema) {
		String szReceiveCom = aLZCardSchema.getReceiveCom();
		boolean bReturn = true;
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql("SELECT * FROM LABranchGroup WHERE BranchAttr LIKE concat('?ReceiveCom?','%')");
		sqlbv.put("ReceiveCom", szReceiveCom);
		LABranchGroupSet tLABranchGroupSet = new LABranchGroupDB()
				.executeQuery(sqlbv);
		if (tLABranchGroupSet.size() == 0) {
			logError(aLZCardSchema, "展业机构" + szReceiveCom + "不存在");
			bReturn = false;
		} else {
			for (int nIndex = 0; nIndex < tLABranchGroupSet.size(); nIndex++) {
				bReturn = batchGroupSendOut(globalInput, aLZCardSchema, tLABranchGroupSet.get(nIndex + 1)
						.getAgentGroup());
				if (!bReturn) {
					break;
				}
			}
		}
		return bReturn;
	}

	/**
	 * Kevin 2003-05-28 往一个代理人组中所有代理人发放指定类型的单证，使所有代理人手中的该种单证数量达到所能 执有的最大数量。
	 * 
	 * @param aGlobalInput
	 * @param aLZCardSchema
	 * @param strAgentBranchCode
	 * @param strErr
	 * @return
	 */
	private boolean batchGroupSendOut(GlobalInput aGlobalInput, LZCardSchema aLZCardSchema,
			String strAgentBranchCode) {
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql("SELECT * FROM LAAgent WHERE BranchCode = '"
				+ "?BranchCode?" + "'");
		sqlbv1.put("BranchCode", strAgentBranchCode);
		LAAgentSet tLAAgentSet = new LAAgentDB().executeQuery(sqlbv1);

		for (int nIndex = 0; nIndex < tLAAgentSet.size(); nIndex++) {
			LAAgentSchema tLAAgentSchema = tLAAgentSet.get(nIndex + 1);

			// 构造一个LZCardSchema
			LZCardSchema tLZCardSchema = new LZCardSchema();

			tLZCardSchema.setCertifyCode(aLZCardSchema.getCertifyCode());
			tLZCardSchema.setSendOutCom(aLZCardSchema.getSendOutCom());
			tLZCardSchema.setReceiveCom("D" + tLAAgentSchema.getAgentCode());
			tLZCardSchema.setTakeBackNo(aLZCardSchema.getTakeBackNo());

			int nMaxCount = CertifyFunc.getMaxCount(tLZCardSchema.getReceiveCom().substring(1), tLZCardSchema
					.getCertifyCode());

			if (nMaxCount < 0) {
				logError(tLZCardSchema, "找不到这个代理人的最大领用数配置信息");
				continue; // 缺少某个代理人的最大领用数配置，直接到下一个代理人
			}

			// 得到代理人现有单证数量
			String strSQL = "SELECT SUM(SUMCOUNT) FROM LZCard WHERE StateFlag = '0'" + " AND CertifyCode = '"
					+ "?CertifyCode?" + "'" + " AND ReceiveCom = '"
					+ "?ReceiveCom?" + "'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(strSQL);
			sqlbv2.put("CertifyCode", tLZCardSchema.getCertifyCode());
			sqlbv2.put("ReceiveCom", tLZCardSchema.getReceiveCom());
			ExeSQL exeSQL = new ExeSQL();
			SSRS ssrs = exeSQL.execSQL(sqlbv2);
			if (exeSQL.mErrors.needDealError()) {
				logError(tLZCardSchema, exeSQL.mErrors.getFirstError());
				continue;
			}

			// 代理人手中的单证已经达到最大数量
			int nCurCount = Integer.parseInt(ssrs.GetText(1, 1));
			if (nCurCount >= nMaxCount) {
				logError(tLZCardSchema, "代理人执有单证数" + String.valueOf(nCurCount) + "已经达到最大数"
						+ String.valueOf(nMaxCount));
				continue;
			}

			// 本次需要发放的单证数
			tLZCardSchema.setSumCount(nMaxCount - nCurCount);

			CertifyFunc.mErrors.clearErrors();
			LZCardSet tLZCardSet = CertifyFunc.findNo(tLZCardSchema);

			if (tLZCardSet == null) {
				logError(tLZCardSchema, CertifyFunc.mErrors.getFirstError());
				mErrors.copyAllErrors(CertifyFunc.mErrors);
				return false;
			}

			VData vResult = new VData();
			for (int nInnerIndex = 0; nInnerIndex < tLZCardSet.size(); nInnerIndex++) {
				VData vOneResult = new VData();

				mErrors.clearErrors();
				CertifyFunc.mErrors.clearErrors();
				if (!dealOne(tLZCardSet.get(nInnerIndex + 1), vOneResult)) {
					logError(tLZCardSet.get(nInnerIndex + 1), mErrors.getFirstError());
					continue;
				}
				vResult.add(vOneResult);
			}

			// 保存数据
			CertSendOutBLS tCertSendOutBLS = new CertSendOutBLS();
			if (!tCertSendOutBLS.submitData(vResult, "INSERT")) {
				if (tCertSendOutBLS.mErrors.needDealError()) {
					mErrors.copyAllErrors(tCertSendOutBLS.mErrors);
					return false;
				} else {
					buildError("dealOne", "CertSendOutBL出错，但是没有提供详细的信息");
					return false;
				}
			}
		} // end of for

		return true;
	}

	// 准备往后层输出所需要的数据
	// 输出：如果准备数据时发生错误则返回false,否则返回true
	private boolean prepareOutputData(VData vData) {
		try {
			if (mszOperate.equals("INSERT")) {
				vData.clear();
				vData.addElement(globalInput);
				vData.addElement(mLZCardSet);
			}
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "CertSendOutBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/*
	 * add by kevin, 2002-09-23
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "CertSendOutBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private void buildFailSet() {
		LZCardSet tLZCardSet = new LZCardSet();
		for (int nIndex = m_nOperIndex; nIndex < mLZCardSet.size(); nIndex++) {
			tLZCardSet.add(mLZCardSet.get(nIndex + 1));
		}
		mResult.add(tLZCardSet);
	}
}
