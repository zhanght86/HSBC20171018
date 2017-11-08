package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import java.util.Vector;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMUWDB;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LMUWSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: ChinaSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */
public class UWFBCal implements BusinessService{
private static Logger logger = Logger.getLogger(UWFBCal.class);
	// 错误处理
	public CErrors mErrors = new CErrors();
	private GlobalInput tGI = new GlobalInput();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 得到前台数据的容器 */
	private VData mResult = new VData();
	private VData m1Result = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 业务逻辑数据* */
	private LCContSchema mLCContSchema;

	public UWFBCal() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		// 数据准备操作
		if (!dealData()) {
			return false;
		}

		return true;
	}

	/**
	 * dealData
	 * 
	 * @return boolean
	 */
	private boolean dealData() {

		TransferData tTransferData;

		// 取被保险人下的所有险种信息作为要素传入
		LCPolSet tLCPolSet = new LCPolSet();
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setContNo(mLCContSchema.getContNo());

		tLCPolSet = tLCPolDB.query();
		if (tLCPolSet == null) {
			CError tError = new CError();
			tError.moduleName = "UWFBCalBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单信息查询失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		for (int i = 1; i <= tLCPolSet.size(); i++) {
			// 计算要素准备
			tTransferData = new TransferData();
			tTransferData.setNameAndValue("ContNo", mLCContSchema.getContNo());
			tTransferData.setNameAndValue("InsuredNo", tLCPolSet.get(i)
					.getInsuredNo());

			// 准备险种代码信息
			tTransferData.setNameAndValue("PolNo", tLCPolSet.get(i).getPolNo());
			tTransferData.setNameAndValue("RiskCode", tLCPolSet.get(i)
					.getRiskCode());

			if (CalFB(tTransferData)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * CalFB
	 * 
	 * @param tTransferData
	 *            TransferData
	 * @return boolean
	 */
	private boolean CalFB(TransferData tTransferData) {
		// 计算
		String tUWType = "LF"; // lmuw中临分算法为LF
		double tValue = 0;
		String tRiskcode = "";
		String tStr = "";

		// 或得临分规则算法
		tRiskcode = (String) tTransferData.getValueByName("RiskCode");
		String tStrSql = "";
		/**
		 * @todo 临分算法分为公共和险种两个层级，公共的针对所有险种，险种的针对单个险种
		 */
		tStrSql = "select * from lmuw where 1=1 " + " and uwtype = '" + "?uwtype?"
				+ "'" + " and riskcode in ('000000','" + "?riskcode?" + "')";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tStrSql);
		sqlbv.put("uwtype", tUWType);
		sqlbv.put("riskcode", tRiskcode);
		LMUWSet tLMUWSet = new LMUWSet();
		LMUWDB tLMUWDB = new LMUWDB();
		tLMUWSet = tLMUWDB.executeQuery(sqlbv);
		if (tLMUWSet == null) {
			CError tError = new CError();
			tError.moduleName = "UWFBCalBL";
			tError.functionName = "CalFB";
			tError.errorMessage = "获得临分算法时失败,请与系统管理员联系！";
			this.mErrors.addOneError(tError);
			return false;
		}
		for (int m = 1; m <= tLMUWSet.size(); m++) {
			tStr = "";
			tValue = 0;
			Calculator mCalculator = new Calculator();
			mCalculator.setCalCode(tLMUWSet.get(m).getCalCode());
			/** @todo 得到TransferData中的要素名 */
			Vector tVector = new Vector();
			tVector = tTransferData.getValueNames();
			if (tVector.size() > 0) {
				// 获得要素名称并赋值到Calculator类中
				for (int i = 0; i < tVector.size(); i++) {
					String tFactorName = new String();
					tFactorName = (String) tVector.get(i);
					logger.debug("==CalFB===factor name===="
							+ tFactorName);
					mCalculator.addBasicFactor(tFactorName,
							(String) tTransferData.getValueByName(tFactorName));
					logger.debug("==CalFB Factor Value======="
							+ (String) tTransferData
									.getValueByName(tFactorName));
				}

				tStr = String.valueOf(mCalculator.calculate());
				logger.debug("==CalFB==Calculate Result==" + tStr);
				// 如果有返回值不为零表示需要临分
				if (tStr.trim().equals("0") || tStr.trim().equals("")
						|| tStr.trim().length() == 0) {
					continue;
				} else {
					return true;
				}
			} else {
				logger.debug("计算要素传输失败！");
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PubPrtConfigure";
				tError.functionName = "CalculatePrt";
				tError.errorMessage = "计算要素传输失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return false;
	}

	/**
	 * getInputData
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		LCContSchema tLCContSchema = (LCContSchema) mInputData
				.getObjectByObjectName("LCContSchema", 0); // 从输入数据中获取合同记录的数据
		if (tLCContSchema.getContNo() == null
				|| tLCContSchema.getContNo().trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "UWFBCalBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "数据传入失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCContSchema = tLCContSchema;

		return true;
	}

	public static void main(String[] args) {
		VData tVData = new VData();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema.setContNo("20061124000001");
		tVData.add(tLCContSchema);
		UWFBCal tUWFBCal = new UWFBCal();
		if (!tUWFBCal.submitData(tVData, "")) {
			logger.debug("不需要临分");
		} else {
			logger.debug("需要临分");
		}
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return null;
	}
}
