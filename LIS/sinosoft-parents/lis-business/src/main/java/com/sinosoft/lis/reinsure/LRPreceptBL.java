

/*
 * <p>ClassName: LRContManageBL </p>
 * <p>Description: LRContManageBL类文件 </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: sinosoft </p>
 * @Database: Zhang Bin
 * @CreateDate：2006-07-30
 */
package com.sinosoft.lis.reinsure;

import com.sinosoft.lis.db.RIAccumulateDefDB;
import com.sinosoft.lis.db.RIPreceptDB;
import com.sinosoft.lis.db.RITempContLinkDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.reinsure.tools.RIPubFun;
import com.sinosoft.lis.schema.RIAccumulateDefSchema;
import com.sinosoft.lis.schema.RICalFactorValueSchema;
import com.sinosoft.lis.schema.RIPreceptSchema;
import com.sinosoft.lis.vschema.RICalFactorValueSet;
import com.sinosoft.lis.vschema.RIDivisionLineDefSet;
import com.sinosoft.lis.vschema.RIIncomeCompanySet;
import com.sinosoft.lis.vschema.RIRiskDivideSet;
import com.sinosoft.lis.vschema.RITempContLinkSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class LRPreceptBL implements BusinessService {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 前台传入的公共变量 */
	private GlobalInput globalInput = new GlobalInput();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/***/
	private TransferData mTransferData = new TransferData();
	private RIPreceptSchema mRIPreceptSchema = new RIPreceptSchema();
	private RIDivisionLineDefSet mRIDivisionLineDefSet = new RIDivisionLineDefSet();
	private RIIncomeCompanySet mRIIncomeCompanySet = new RIIncomeCompanySet();
	private RIRiskDivideSet mRIRiskDivideSet = new RIRiskDivideSet();
	private RIRiskDivideSet testRIRiskDivideSet = new RIRiskDivideSet();
	private RICalFactorValueSet mRICalFactorValueSet = new RICalFactorValueSet();
	/** 判断update时是否删除溢额线设置，分保比例线设置 **/
	private String mUpDe = ""; // 用于标记溢额线，公司数是否修改
	/** 数据操作字符串 */
	private String strOperate;
	private String mPreceptNo;
	private MMap mMap = new MMap();

	private PubSubmit tPubSubmit = new PubSubmit();

	public LRPreceptBL() {
	}

	/**
	 * 提交数据处理方法
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		this.strOperate = cOperate;
		if (strOperate.equals("")) {
			buildError("verifyOperate", "不支持的操作字符串");
			return false;
		}
		if (!getInputData(cInputData))
			return false;

		if (!dealData()) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		GlobalInput globalInput = new GlobalInput();
		globalInput.ComCode = "8611";
		globalInput.ManageCom = "8611";
		globalInput.Operator = "001";
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			this.mInputData.add(mMap);
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "LDComBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 进行插入数据
		if (this.strOperate.equals("SCHMINSERT")) {
			if (!insertData()) {
				return false;
			}
		}
		// 对数据进行修改操作
		if (this.strOperate.equals("UPDATE")) {
			if (!updateData()) {
				return false;
			}
		}
		// 进行溢额线设置核和分保比例线设置
		if (this.strOperate.equals("DIVCOMINSERT")) {
			if (!insertDIVCOMData()) {
				return false;
			}
		}
		// 进行分保比例设置
		if (this.strOperate.equals("SCALEINSERT")) {
			if (!insertScaleData()) {
				return false;
			}
		}
		if (this.strOperate.equals("FACTORINSERT")) {
			if (!insertFactorData()) {
				return false;
			}
		}
		// 对数据进行删除操作
		if (this.strOperate.equals("DELETE")) {
			if (!deleteData()) {
				return false;
			}
		}
		// 对数据进行删除操作
		if (this.strOperate.equals("FEERATEINSERT")) {
			if (!UpdateFeeData()) {
				return false;
			}
		}
		//
		if (this.strOperate.equals("SCHFEEDIV")) {
			if (!insertFir()) {
				return false;
			}
		}

		return true;
	}

	private boolean insertFir() {
		RIPreceptDB tRIPreceptDB = new RIPreceptDB();
		tRIPreceptDB.setRIPreceptNo(mRIPreceptSchema.getRIPreceptNo());
		if (tRIPreceptDB.getInfo()) {
			buildError("insertData", "该再保方案号码已存在!");
			return false;
		}
		// 插入再保方案表
		mMap.put(mRIPreceptSchema, "INSERT");
		mMap.put(mRIDivisionLineDefSet, "INSERT");
		mMap.put(mRIIncomeCompanySet, "INSERT");
		mMap.put(mRIRiskDivideSet, "INSERT");
		// mMap.put(testRIRiskDivideSet, "UPDATE");

		if (!prepareOutputData()) {
			return false;
		}
		if (!tPubSubmit.submitData(this.mInputData, "")) {
			if (tPubSubmit.mErrors.needDealError()) {
				buildError("insertData", "保存再保合同信息时出现错误!");
				return false;
			}
		}
		mMap = null;
		tPubSubmit = null;

		return true;
	}

	/**
	 * insertData
	 * 
	 * @return boolean
	 */
	private boolean insertData() {
		RIPreceptDB tRIPreceptDB = new RIPreceptDB();
		tRIPreceptDB.setRIPreceptNo(mRIPreceptSchema.getRIPreceptNo());
		if (tRIPreceptDB.getInfo()) {
			buildError("insertData", "该再保方案号码已存在!");
			return false;
		}
		// 插入再保方案表
		mMap.put(mRIPreceptSchema, "INSERT");
		if (!prepareOutputData()) {
			return false;
		}
		if (!tPubSubmit.submitData(this.mInputData, "")) {
			if (tPubSubmit.mErrors.needDealError()) {
				buildError("insertData", "保存再保合同信息时出现错误!");
				return false;
			}
		}
		mMap = null;
		tPubSubmit = null;

		return true;
	}

	/**
	 * updateData
	 * 
	 * @return boolean
	 */
	private boolean updateData() {
		RIPreceptDB tRIPreceptDB = new RIPreceptDB();
		tRIPreceptDB.setRIPreceptNo(mRIPreceptSchema.getRIPreceptNo());
		if (!tRIPreceptDB.getInfo()) {
			buildError("deleteData", "该再保方案号码不存在!");
			return false;
		}
		RIPreceptSchema tRIPreceptSchema = tRIPreceptDB.getSchema();
		tRIPreceptSchema.setRIPreceptName(mRIPreceptSchema.getRIPreceptName());
		tRIPreceptSchema.setBonus(mRIPreceptSchema.getBonus());
		tRIPreceptSchema.setAccumulateDefNO(mRIPreceptSchema
				.getAccumulateDefNO());
		tRIPreceptSchema.setArithmeticID(mRIPreceptSchema.getArithmeticID());
		tRIPreceptSchema.setCompanyNum(mRIPreceptSchema.getCompanyNum());
		tRIPreceptSchema.setDivisionNum(mRIPreceptSchema.getDivisionNum());
		tRIPreceptSchema.setPreceptType(mRIPreceptSchema.getPreceptType());
		tRIPreceptSchema
				.setStandbyString1(mRIPreceptSchema.getStandbyString1());
		tRIPreceptSchema
				.setStandbyString2(mRIPreceptSchema.getStandbyString2());
		tRIPreceptSchema
				.setStandbyString3(mRIPreceptSchema.getStandbyString3());
		tRIPreceptSchema.setState(mRIPreceptSchema.getState());
		tRIPreceptSchema.setRIMainPreceptNo(mRIPreceptSchema
				.getRIMainPreceptNo());
		tRIPreceptSchema.setAttachFalg(mRIPreceptSchema.getAttachFalg());
		tRIPreceptSchema.setRIPreceptNo(mRIPreceptSchema.getRIPreceptNo());
		tRIPreceptSchema.setRIPreceptName(mRIPreceptSchema.getRIPreceptName());
		tRIPreceptSchema.setHierarchyNumType(mRIPreceptSchema
				.getHierarchyNumType());
		mMap.put(tRIPreceptSchema, "UPDATE");
		if (mUpDe.equals("1")) {
			String strSQL1 = "delete from RIDivisionLineDef where RIPreceptNo='"
					+ mRIPreceptSchema.getRIPreceptNo() + "'";
			mMap.put(strSQL1, "DELETE");
			String strSQL2 = "delete from RIIncomeCompany where RIPreceptNo='"
					+ mRIPreceptSchema.getRIPreceptNo() + "'";
			mMap.put(strSQL2, "DELETE");
			String strSQL3 = "delete from RIRiskDivide where RIPreceptNo='"
					+ mRIPreceptSchema.getRIPreceptNo() + "'";
			mMap.put(strSQL3, "DELETE");
			String strSQL4 = "delete from RIAssociateFeeTable where RIPreceptNo='"
					+ mRIPreceptSchema.getRIPreceptNo() + "'";
			mMap.put(strSQL4, "DELETE");
		}
		if (mRIPreceptSchema.getAttachFalg().equals("01")) {
			String strSQL1 = "delete from RIDivisionLineDef where RIPreceptNo='"
					+ mRIPreceptSchema.getRIPreceptNo() + "'";
			mMap.put(strSQL1, "DELETE");
			String strSQL2 = "delete from RIIncomeCompany where RIPreceptNo='"
					+ mRIPreceptSchema.getRIPreceptNo() + "'";
			mMap.put(strSQL2, "DELETE");
			String strSQL3 = "delete from RIRiskDivide where RIPreceptNo='"
					+ mRIPreceptSchema.getRIPreceptNo() + "'";
			mMap.put(strSQL3, "DELETE");
			String strSQL4 = "delete from RIAssociateFeeTable where RIPreceptNo='"
					+ mRIPreceptSchema.getRIPreceptNo() + "'";
			mMap.put(strSQL4, "DELETE");
			mMap.put(mRIDivisionLineDefSet, "INSERT");
			mMap.put(mRIIncomeCompanySet, "INSERT");
			mMap.put(mRIRiskDivideSet, "INSERT");
		}
		if (!prepareOutputData()) {
			return false;
		}
		if (!tPubSubmit.submitData(this.mInputData, "")) {
			System.out.println(" 保存失败： ");
			if (tPubSubmit.mErrors.needDealError()) {
				buildError("updateData", "修改信息时出现错误!");
				return false;
			}
		}
		// mMap = null;
		// mMap=new MMap();
		// mMap.put(mRIDivisionLineDefSet, "INSERT");
		// mMap.put(mRIIncomeCompanySet, "INSERT");
		// mMap.put(mRIRiskDivideSet, "INSERT");
		// if (!prepareOutputData()) {
		// return false;
		// }
		// if (!tPubSubmit.submitData(this.mInputData, "")) {
		// System.out.println(" 保存失败： ");
		// if (tPubSubmit.mErrors.needDealError()) {
		// buildError("updateData", "修改信息时出现错误!");
		// return false;
		// }
		// }
		mMap = null;
		tPubSubmit = null;
		return true;
	}

	/**
	 * deleteData
	 * 
	 * @return boolean
	 */
	private boolean deleteData() {
		RIPreceptDB tRIPreceptDB = new RIPreceptDB();
		tRIPreceptDB.setRIPreceptNo(mRIPreceptSchema.getRIPreceptNo());
		if (!tRIPreceptDB.getInfo()) {
			buildError("deleteData", "该再保方案号码不存在!");
			return false;
		}
		RITempContLinkDB tRITempContLinkDB = new RITempContLinkDB();
		tRITempContLinkDB.setRIPreceptNo(mRIPreceptSchema.getRIPreceptNo());
		RITempContLinkSet tRITempContLinkSet = tRITempContLinkDB.query();
		if (tRITempContLinkSet.size() != 0) {
			buildError("deleteData", "该再保方案有保单关联，请删除关联后再删除再保方案!");
			return false;
		}

		RIPreceptSchema tRIPreceptSchema = tRIPreceptDB.getSchema();
		mMap.put(tRIPreceptSchema, "DELETE");

		String strSQL1 = "delete from RIDivisionLineDef where RIPreceptNo='"
				+ mRIPreceptSchema.getRIPreceptNo() + "'";
		mMap.put(strSQL1, "DELETE");
		String strSQL2 = "delete from RIIncomeCompany where RIPreceptNo='"
				+ mRIPreceptSchema.getRIPreceptNo() + "'";
		mMap.put(strSQL2, "DELETE");
		String strSQL3 = "delete from RIRiskDivide where RIPreceptNo='"
				+ mRIPreceptSchema.getRIPreceptNo() + "'";
		mMap.put(strSQL3, "DELETE");
		String strSQL4 = "delete from RICalFactorValue where RIPreceptNo='"
				+ mRIPreceptSchema.getRIPreceptNo() + "'";
		mMap.put(strSQL4, "DELETE");
		// 删除险种关联费率表 add by lijian
		String strSQL5 = "delete from RIAssociateFeeTable where RIPreceptNo='"
				+ mRIPreceptSchema.getRIPreceptNo() + "'";
		mMap.put(strSQL5, "DELETE");

		if (!prepareOutputData()) {
			return false;
		}
		if (!tPubSubmit.submitData(this.mInputData, "")) {
			if (tPubSubmit.mErrors.needDealError()) {
				buildError("updateData", "修改时出现错误!");
				return false;
			}
		}
		mMap = null;
		tPubSubmit = null;
		return true;
	}

	/**
	 * insertDivData
	 * 
	 * @return boolean
	 */
	private boolean insertDIVCOMData() {
		// 插入溢额线设置
		if (mRIDivisionLineDefSet.size() > 0) {
			mMap.put(mRIDivisionLineDefSet, "DELETE&INSERT");
		}
		// 插入分保公司定义
		if (mRIIncomeCompanySet.size() > 0) {
			mMap.put(mRIIncomeCompanySet, "DELETE&INSERT");
		}
		String tSQL = " delete From RIRiskDivide where RIContNo = '"
				+ mRIDivisionLineDefSet.get(1).getRIContNo()
				+ "' and RIPreceptNo = '" + mPreceptNo + "'";
		String tSQL1 = " delete From RIAssociateFeeTable where  RIPreceptNo = '"
				+ mPreceptNo + "'";
		mMap.put(tSQL, "DELETE");
		mMap.put(tSQL1, "DELETE");
		if (!prepareOutputData()) {
			return false;
		}
		if (!tPubSubmit.submitData(this.mInputData, "")) {
			if (tPubSubmit.mErrors.needDealError()) {
				buildError("insertData", "保存再保合同信息时出现错误!");
				return false;
			}
		}

		mMap = null;
		tPubSubmit = null;
		return true;
	}

	/**
	 * insertDivData
	 * 
	 * @return boolean
	 */
	private boolean insertScaleData() {
		// 删除原比例
		String strSQL1 = "delete from RIRiskDivide where RIPreceptNo='"
				+ mPreceptNo + "'";
		mMap.put(strSQL1, "DELETE");
		// 插入再保
		if (mRIRiskDivideSet.size() > 0) {
			mRIPreceptSchema.setRIPreceptNo(mRIRiskDivideSet.get(1)
					.getRIPreceptNo()); // 用于getResult()返回
			mMap.put(mRIRiskDivideSet, "INSERT");
		}
		if (!prepareOutputData()) {
			return false;
		}
		if (!tPubSubmit.submitData(this.mInputData, "")) {
			if (tPubSubmit.mErrors.needDealError()) {
				buildError("insertData", "保存再保合同信息时出现错误!");
				return false;
			}
		}
		mMap = null;
		tPubSubmit = null;
		return true;
	}

	/**
	 * 费率表定义
	 * 
	 * @return boolean
	 */
	private boolean UpdateFeeData() {
		if (mRIRiskDivideSet.size() <= 0) {
			buildError("UpdateFeeData", "费率信息为空!");
			return false;
		}
		if (mRIRiskDivideSet.get(1).getRIPreceptNo() == null
				|| mRIRiskDivideSet.get(1).getRIPreceptNo().equals("")) {
			buildError("UpdateFeeData", "再保编号为空!");
			return false;
		}
		/*
		 * //备份分保比例 LRPreceptBakeBL tLRPreceptBakeBL = new LRPreceptBakeBL(); if
		 * (
		 * !tLRPreceptBakeBL.getScaleBake(mRIRiskDivideSet.get(1).getRIPreceptNo
		 * ())) { mErrors.copyAllErrors(tLRPreceptBakeBL.mErrors); return false;
		 * } VData tVData = tLRPreceptBakeBL.getResult(); if
		 * (!getMapInfo(tVData)) { buildError("updateData",
		 * "备份分保比例时出错，错误编号：S00017!"); return false; }
		 */
		// 判断是否已经配置再保算法
		boolean calConfFlag = true;
		RIPreceptSchema tRIPreceptSchema = getRIPrecept(mRIRiskDivideSet.get(1)
				.getRIPreceptNo());
		if (tRIPreceptSchema == null) {
			return false;
		}
		if (tRIPreceptSchema.getArithmeticID() == null
				|| tRIPreceptSchema.getArithmeticID().equals("")) {
			calConfFlag = false;
		}
		// 获取累计风险定义
		RIAccumulateDefSchema tRIAccumulateDefSchema = getRIAccumulateDef(tRIPreceptSchema);
		if (tRIAccumulateDefSchema == null) {
			return false;
		}
		RIFeeRateCalRela tRIFeeRateCalRela = new RIFeeRateCalRela();

		// 向分配额设置表中配置费率表
		String tSql[] = new String[mRIRiskDivideSet.size()];
		for (int i = 1; i <= mRIRiskDivideSet.size(); i++) {
			String tt = "update RIRiskDivide a set a.PremFeeTableNo = '"
					+ mRIRiskDivideSet.get(i).getPremFeeTableNo()
					+ "', a.ComFeeTableNo = '"
					+ mRIRiskDivideSet.get(i).getComFeeTableNo()
					+ "' where a.RIPreceptNo = '"
					+ mRIRiskDivideSet.get(i).getRIPreceptNo()
					+ "' and  a.AreaID = "
					+ mRIRiskDivideSet.get(i).getAreaID();
			tSql[i - 1] = new String(tt);
		}

		if (calConfFlag) {
			// 配置费率表算法
			if (!tRIFeeRateCalRela.deal(mRIRiskDivideSet, tRIPreceptSchema,
					tRIAccumulateDefSchema)) {
				return false;
			}

			VData tVData = tRIFeeRateCalRela.getResult();
			if (!getMapInfo(tVData)) {
				buildError("UpdateFeeData", "配置费率表算法时出错。");
				return false;
			}
		}
		for (int j = 0; j < tSql.length; j++) {
			mMap.put(tSql[j], "UPDATE");
		}
		if (!prepareOutputData()) {
			return false;
		}
		if (!tPubSubmit.submitData(this.mInputData, "")) {
			if (tPubSubmit.mErrors.needDealError()) {
				buildError("UpdateFeeData", "保存费率信息时出现错误!");
				return false;
			}
		}
		mMap = null;
		tPubSubmit = null;
		return true;
	}

	/**
	 * insertDivData
	 * 
	 * @return boolean
	 */
	private boolean insertFactorData() {
		// 删除原比例
		String strSQL1 = "delete from RICalFactorValue where RIPreceptNo='"
				+ mRIPreceptSchema.getRIPreceptNo() + "' and ReContCode='"
				+ mRIPreceptSchema.getRIContNo() + "'";
		mMap.put(strSQL1, "DELETE");

		if (mRICalFactorValueSet.size() > 0) {
			RICalFactorValueSchema tRICalFactorValueSchema = new RICalFactorValueSchema();
			for (int i = 1; i <= mRICalFactorValueSet.size(); i++) {
				tRICalFactorValueSchema = mRICalFactorValueSet.get(i);
				RIPubFun.fillDefaultField(tRICalFactorValueSchema, globalInput);
				if (tRICalFactorValueSchema.getSerialNo() == null
						|| tRICalFactorValueSchema.getSerialNo().equals("")) {
					tRICalFactorValueSchema.setSerialNo(PubFun1.CreateMaxNo(
							"RICALFACTOR", 10));
					mMap.put(tRICalFactorValueSchema, "INSERT");
				} else {
					mMap.put(tRICalFactorValueSchema, "INSERT");
				}
			}
		}
		if (!prepareOutputData()) {
			return false;
		}
		if (!tPubSubmit.submitData(this.mInputData, "")) {
			if (tPubSubmit.mErrors.needDealError()) {
				buildError("insertData", "保存再保合同信息时出现错误!");
				return false;
			}
		}
		mMap = null;
		tPubSubmit = null;
		return true;
	}

	/**
	 * 获取再保方案对象
	 * 
	 * @param tRIPreceptNo
	 *            String
	 * @return RIPreceptSchema
	 */
	public RIPreceptSchema getRIPrecept(String tRIPreceptNo) {
		try {
			RIPreceptDB tRIPreceptDB = new RIPreceptDB();
			tRIPreceptDB.setRIPreceptNo(tRIPreceptNo);
			if (!tRIPreceptDB.getInfo()) {
				buildError("UpdateFeeData", "获取再保方案时出错!");
				return null;
			}
			if (tRIPreceptDB.mErrors.needDealError()) {
				buildError("UpdateFeeData", "获取再保方案时出错!");
				return null;
			}
			RIPreceptSchema tRIPreceptSchema = tRIPreceptDB.getSchema();
			return tRIPreceptSchema;
		} catch (Exception ex) {
			buildError("UpdateFeeData", "获取再保方案时出现异常!");
			return null;
		}
	}

	/**
	 * 获取累计风险对象
	 * 
	 * @param tRIPreceptSchema
	 *            RIPreceptSchema
	 * @return RIAccumulateDefSchema
	 */
	public RIAccumulateDefSchema getRIAccumulateDef(
			RIPreceptSchema tRIPreceptSchema) {
		try {
			RIAccumulateDefDB tRIAccumulateDefDB = new RIAccumulateDefDB();
			tRIAccumulateDefDB.setAccumulateDefNO(tRIPreceptSchema
					.getAccumulateDefNO());
			if (!tRIAccumulateDefDB.getInfo()) {
				buildError("UpdateFeeData", "获取累计风险时出错!");
				return null;
			}
			if (tRIAccumulateDefDB.mErrors.needDealError()) {
				buildError("UpdateFeeData", "获取累计风险时出错!");
				return null;
			}
			RIAccumulateDefSchema tRIAccumulateDefSchema = tRIAccumulateDefDB
					.getSchema();
			return tRIAccumulateDefSchema;
		} catch (Exception ex) {
			buildError("UpdateFeeData", "获取再保方案时出现异常!");
			return null;
		}
	}

	/**
	 * 分保记录添加到MMap
	 * 
	 * @param tVData
	 *            VData
	 * @return boolean
	 */
	private boolean getMapInfo(VData tVData) {
		try {
			MMap tmap = (MMap) tVData.getObjectByObjectName("MMap", 0);
			System.out.println(" bbbbbbbbb size: " + tmap.keySet().size());
			mMap.add(tmap);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		globalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		this.mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mPreceptNo = (String) mTransferData.getValueByName("RIPreceptNo");
		if (strOperate.equals("SCHMINSERT") || strOperate.equals("DELETE")) {
			this.mRIPreceptSchema.setSchema((RIPreceptSchema) cInputData
					.getObjectByObjectName("RIPreceptSchema", 0));
		}
		if (strOperate.equals("UPDATE")) {
			this.mRIPreceptSchema.setSchema((RIPreceptSchema) cInputData
					.getObjectByObjectName("RIPreceptSchema", 0));
			mRIRiskDivideSet.set((RIRiskDivideSet) cInputData
					.getObjectByObjectName("RIRiskDivideSet", 0));
			mRIDivisionLineDefSet.set((RIDivisionLineDefSet) cInputData
					.getObjectByObjectName("RIDivisionLineDefSet", 0));
			mRIIncomeCompanySet.set((RIIncomeCompanySet) cInputData
					.getObjectByObjectName("RIIncomeCompanySet", 0));
			mUpDe = (String) cInputData.getObjectByObjectName("String", 0);
		}
		if (strOperate.equals("DIVCOMINSERT")) {
			mRIDivisionLineDefSet.set((RIDivisionLineDefSet) cInputData
					.getObjectByObjectName("RIDivisionLineDefSet", 0));
			mRIIncomeCompanySet.set((RIIncomeCompanySet) cInputData
					.getObjectByObjectName("RIIncomeCompanySet", 0));
		}
		if (strOperate.equals("SCALEINSERT")) {
			mRIRiskDivideSet.set((RIRiskDivideSet) cInputData
					.getObjectByObjectName("RIRiskDivideSet", 0));
		}

		if (strOperate.equals("FEERATEINSERT")) {
			mRIRiskDivideSet.set((RIRiskDivideSet) cInputData
					.getObjectByObjectName("RIRiskDivideSet", 0));
			mRIDivisionLineDefSet.set((RIDivisionLineDefSet) cInputData
					.getObjectByObjectName("RIDivisionLineDefSet", 0));
			mRIIncomeCompanySet.set((RIIncomeCompanySet) cInputData
					.getObjectByObjectName("RIIncomeCompanySet", 0));
			// this.mRIPreceptSchema.setSchema((RIPreceptSchema) cInputData
			// .getObjectByObjectName("RIPreceptSchema", 0));

			// testRIRiskDivideSet.set((RIRiskDivideSet) cInputData
			// .getObjectByObjectName("RIRiskDivideSet", 6));
		}

		if (strOperate.equals("SCHFEEDIV")) {
			mRIRiskDivideSet.set((RIRiskDivideSet) cInputData
					.getObjectByObjectName("RIRiskDivideSet", 0));
			mRIDivisionLineDefSet.set((RIDivisionLineDefSet) cInputData
					.getObjectByObjectName("RIDivisionLineDefSet", 0));
			mRIIncomeCompanySet.set((RIIncomeCompanySet) cInputData
					.getObjectByObjectName("RIIncomeCompanySet", 0));
			this.mRIPreceptSchema.setSchema((RIPreceptSchema) cInputData
					.getObjectByObjectName("RIPreceptSchema", 0));
			testRIRiskDivideSet.set((RIRiskDivideSet) cInputData
					.getObjectByObjectName("RIRiskDivideSet", 6));
		}
		if (strOperate.equals("FACTORINSERT")) {
			mRICalFactorValueSet.set((RICalFactorValueSet) cInputData
					.getObjectByObjectName("RICalFactorValueSet", 0));
			this.mRIPreceptSchema.setSchema((RIPreceptSchema) cInputData
					.getObjectByObjectName("RIPreceptSchema", 0));
		}
		return true;
	}

	public VData getResult() {
		TransferData t = new TransferData();
		t.setNameAndValue("PreceptNo", mPreceptNo);
		this.mResult.add(t);
		return this.mResult;
	}

	public CErrors getErrors() {
		return this.mErrors;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "ReComManageBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
}

