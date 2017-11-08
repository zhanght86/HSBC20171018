package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 承保暂交费业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 */
public class ProposalFeeBL {
private static Logger logger = Logger.getLogger(ProposalFeeBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private String mPolNo = "";
	private String mPolNoType = "";
	/** 暂交费 */
	private LJTempFeeSchema mLJTempFeeSchema = new LJTempFeeSchema();
	private LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();

	public ProposalFeeBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
			return false;

		// 数据查询业务处理
		if (cOperate.equals("QUERY||MAIN")) {
			if (!queryData())
				return false;
			logger.debug("---queryData---");
		}
		// 数据操作业务处理
		if (cOperate.equals("INSERT||CONT") || cOperate.equals("INSERT||GROUP")
				|| cOperate.equals("INSERT||PROPOSAL")) {
			if (!dealData())
				return false;
			logger.debug("---dealData---");
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (!mLJTempFeeSet.get(1).getTempFeeNo().trim().equals("NULL")) {
			// 校验数据
			if (!checkData())
				return false;

			// 准备给后台的数据
			prepareOutputData();
		}

		// 数据提交
		ProposalFeeBLS tProposalFeeBLS = new ProposalFeeBLS();
		logger.debug("Start ProposalFee BL Submit...");
		if (!tProposalFeeBLS.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tProposalFeeBLS.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalFeeBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 暂交费查询条件
		if (mOperate.equals("QUERY||MAIN")) {
			mLJTempFeeSchema.setSchema((LJTempFeeSchema) cInputData
					.getObjectByObjectName("LJTempFeeSchema", 0));
			mPolNo = mLJTempFeeSchema.getOtherNo().trim();
		}
		if (mOperate.equals("INSERT||CONT") || mOperate.equals("INSERT||GROUP")
				|| mOperate.equals("INSERT||PROPOSAL")) {
			mLJTempFeeSet.set((LJTempFeeSet) cInputData.getObjectByObjectName(
					"LJTempFeeSet", 0));
			mPolNo = mLJTempFeeSet.get(1).getOtherNo().trim();
		}

		if (mOperate.equals("INSERT||CONT"))
			mPolNoType = "2";
		if (mOperate.equals("INSERT||GROUP"))
			mPolNoType = "1";
		if (mOperate.equals("INSERT||PROPOSAL"))
			mPolNoType = "0";

		return true;
	}

	/**
	 * 查询符合条件的暂交费信息 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean queryData() {
		// 查询投保单号关联的暂交费
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		tLJTempFeeDB.setOtherNo(mLJTempFeeSchema.getOtherNo());
		tLJTempFeeDB.setOtherNoType(mLJTempFeeSchema.getOtherNoType());
		tLJTempFeeDB.setTempFeeType("1");
		mLJTempFeeSet = tLJTempFeeDB.query();

		String riskCode = "";
		// 个单查询
		if (mLJTempFeeSchema.getOtherNoType().equals("0")) {
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(mLJTempFeeSchema.getOtherNo());
			if (!tLCPolDB.getInfo()) {
				return false;
			}
			riskCode = tLCPolDB.getRiskCode();
		}
		// 团单查询
		else if (mLJTempFeeSchema.getOtherNoType().equals("1")) {
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			tLCGrpPolDB.setGrpPolNo(mLJTempFeeSchema.getOtherNo());
			if (!tLCGrpPolDB.getInfo()) {
				return false;
			}
			riskCode = tLCGrpPolDB.getRiskCode();
		}

		// 查询印刷号关联的暂交费
		LJTempFeeDB tLJTempFeeDB2 = new LJTempFeeDB();
		tLJTempFeeDB2.setOtherNo(mLJTempFeeSchema.getTempFeeNo());
		tLJTempFeeDB2.setOtherNoType("4");
		// tLJTempFeeDB2.setTempFeeType( "1" );
		tLJTempFeeDB2.setRiskCode(riskCode);
		mLJTempFeeSet.add(tLJTempFeeDB2.query());

		if (tLJTempFeeDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJTempFeeDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalFeeBL";
			tError.functionName = "queryData";
			tError.errorMessage = "暂交费查询失败!";
			this.mErrors.addOneError(tError);
			mLJTempFeeSet.clear();
			return false;
		}

		/*
		 * //补充：查询类型为银行代扣的暂交费(可用) LJTempFeeSet tLJTempFeeSet = new
		 * LJTempFeeSet(); if( mPolNoType.equals( "0" )) // 个人单 { // 是否存在此个人单
		 * LCPolDB tLCPolDB = new LCPolDB(); tLCPolDB.setPolNo( mPolNo ); if
		 * (tLCPolDB.getInfo() == false) { return false; }
		 * tLJTempFeeDB.setOtherNo( tLCPolDB.getPrtNo() );
		 * tLJTempFeeDB.setOtherNoType( "4" ); tLJTempFeeDB.setTempFeeType( "5" ); }
		 * if( mPolNoType.equals( "1" )) // 集体单 { // 是否存在此集体单 LCGrpPolDB
		 * tLCGrpPolDB = new LCGrpPolDB(); tLCGrpPolDB.setGrpPolNo( mPolNo ); if
		 * (tLCGrpPolDB.getInfo() == false) { return false; }
		 * tLJTempFeeDB.setOtherNo( tLCGrpPolDB.getPrtNo() );
		 * tLJTempFeeDB.setOtherNoType( "4" ); tLJTempFeeDB.setTempFeeType( "5" ); }
		 * 
		 * tLJTempFeeSet= tLJTempFeeDB.query(); if
		 * (tLJTempFeeDB.mErrors.needDealError() == true) { // @@错误处理
		 * this.mErrors.copyAllErrors(tLJTempFeeDB.mErrors); CError tError = new
		 * CError(); tError.moduleName = "ProposalFeeBL"; tError.functionName =
		 * "queryData"; tError.errorMessage = "暂交费查询失败!";
		 * this.mErrors.addOneError(tError); mLJTempFeeSet.clear(); return
		 * false; } mLJTempFeeSet.add(tLJTempFeeSet);
		 */

		if (mLJTempFeeSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalFeeBL";
			tError.functionName = "queryData";
			tError.errorMessage = "未找到相关数据!";
			this.mErrors.addOneError(tError);
			mLJTempFeeSet.clear();
			return false;
		}

		mResult.clear();
		mResult.add(mLJTempFeeSet);

		return true;
	}

	/**
	 * 校验传入的暂交费收据号是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		String tRiskCode = "";
		boolean flag = true;
		String tManageCom = "";
		String tAgentCode = "";

		if (mPolNoType.equals("0")) // 个人单
		{
			// 是否存在此个人单
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(mPolNo);
			if (tLCPolDB.getInfo() == false) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ProposalFeeBL";
				tError.functionName = "checkData";
				tError.errorMessage = "保单取数失败";
				this.mErrors.addOneError(tError);
				return false;
			}
			tRiskCode = tLCPolDB.getRiskCode();
			tManageCom = tLCPolDB.getManageCom();
			tAgentCode = tLCPolDB.getAgentCode();
		}
		if (mPolNoType.equals("1")) // 集体单
		{
			// 是否存在此集体单
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			tLCGrpPolDB.setGrpPolNo(mPolNo);
			if (tLCGrpPolDB.getInfo() == false) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ProposalFeeBL";
				tError.functionName = "checkData";
				tError.errorMessage = "保单取数失败";
				this.mErrors.addOneError(tError);
				return false;
			}
			tRiskCode = tLCGrpPolDB.getRiskCode();
			tManageCom = tLCGrpPolDB.getManageCom();
			tAgentCode = tLCGrpPolDB.getAgentCode();
		}

		int n = mLJTempFeeSet.size();
		for (int i = 1; i <= n; i++) {

			LJTempFeeSchema tLJTempFeeSchema = mLJTempFeeSet.get(i);
			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			tLJTempFeeDB.setTempFeeNo(tLJTempFeeSchema.getTempFeeNo());
			tLJTempFeeDB.setTempFeeType("1");
			tLJTempFeeDB.setRiskCode(tRiskCode);

			if (tLJTempFeeDB.getInfo() == false) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ProposalFeeBL";
				tError.functionName = "checkData";
				tError.errorMessage = "没有此暂交费收据!("
						+ tLJTempFeeDB.getTempFeeNo().trim() + ")";
				this.mErrors.addOneError(tError);
				flag = false;
				continue;
			}

			// 收据是否已经核销
			if (tLJTempFeeDB.getConfFlag() != null
					&& tLJTempFeeDB.getConfFlag().trim().equals("1")) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ProposalFeeBL";
				tError.functionName = "checkData";
				tError.errorMessage = "此暂交费收据已经核销，不能关联到此投保单!("
						+ tLJTempFeeDB.getTempFeeNo().trim() + ")";
				this.mErrors.addOneError(tError);
				flag = false;
				continue;
			}

			// 收据是否已经被别的投保单关联
			if ((tLJTempFeeDB.getOtherNo() != null
					&& tLJTempFeeDB.getOtherNo().trim().length() > 0 && !tLJTempFeeDB
					.getOtherNo().trim().equals(mPolNo))
					|| (tLJTempFeeDB.getOtherNoType() != null
							&& tLJTempFeeDB.getOtherNoType().trim().length() > 0 && !tLJTempFeeDB
							.getOtherNoType().trim().equals("0"))) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ProposalFeeBL";
				tError.functionName = "checkData";
				tError.errorMessage = "此暂交费收据已经关联到别的投保单，此处不能使用!("
						+ tLJTempFeeDB.getTempFeeNo().trim() + ")";
				this.mErrors.addOneError(tError);
				flag = false;
				continue;
			}

			// 收据的管理机构是否与投保单相同
			if (!StrTool.cTrim(tLJTempFeeDB.getManageCom()).equals(
					StrTool.cTrim(tManageCom))
					|| StrTool.cTrim(tLJTempFeeDB.getManageCom()).equals("")) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ProposalFeeBL";
				tError.functionName = "checkData";
				tError.errorMessage = "此暂交费收据的管理机构与投保单不符，此处不能使用!("
						+ tLJTempFeeDB.getTempFeeNo().trim() + ")";
				this.mErrors.addOneError(tError);
				flag = false;
				continue;
			}

			// 收据的代理人编码是否与投保单相同
			if (!StrTool.cTrim(tLJTempFeeDB.getAgentCode()).equals(
					StrTool.cTrim(tAgentCode))
					|| StrTool.cTrim(tLJTempFeeDB.getAgentCode()).equals("")) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ProposalFeeBL";
				tError.functionName = "checkData";
				tError.errorMessage = "此暂交费收据的代理人编码与投保单不符，此处不能使用!("
						+ tLJTempFeeDB.getTempFeeNo().trim() + ")";
				this.mErrors.addOneError(tError);
				flag = false;
				continue;
			}

			tLJTempFeeSchema.setSchema(tLJTempFeeDB);
			mLJTempFeeSet.set(i, tLJTempFeeSchema);
		} // end of for

		return flag;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {

		int n = mLJTempFeeSet.size();
		for (int i = 1; i <= n; i++) {
			LJTempFeeSchema tLJTempFeeSchema = mLJTempFeeSet.get(i);

			tLJTempFeeSchema.setOtherNo(mPolNo);
			tLJTempFeeSchema.setOtherNoType(mPolNoType);

			mLJTempFeeSet.set(i, tLJTempFeeSchema);
		}

		mInputData.clear();
		mInputData.add(mLJTempFeeSet);
		mResult.clear();
		mResult.add(mLJTempFeeSet);
	}

}
