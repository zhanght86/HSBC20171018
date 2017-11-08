

package com.sinosoft.lis.reinsure;

import com.sinosoft.lis.db.RIFeeRateTableDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIFeeRateTableSchema;
import com.sinosoft.lis.vschema.RIFeeRateTableSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 再保费率表磁盘导入类
 * </p>
 * <p>
 * Description: 把从磁盘导入的费率表添加到数据库
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author ZhangBin
 * @version 1.0
 */

public class LRFeeRateImport implements BusinessService {
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 全局变量 */
	private GlobalInput mGlobalInput;
	private VData mResult = new VData();
	private MMap mMap = new MMap();
	/** 批次号 */
	private String mBatchNo = "";
	private String mFeeTableNo = "";
	private String mFileName = null;
	private String mPath = null;

	private RIFeeRateTableSet mRIFeeRateTableSet = new RIFeeRateTableSet();
	/** 当前日期 */
	private String mCurrentDate = PubFun.getCurrentDate();
	/** 当前时间 */
	private String mCurrentTime = PubFun.getCurrentTime();
	/** 节点名 */
	private String sheetName = "Sheet1";
	/** 配置文件名 */
	private String configName = "LRFeeRateImport.xml";

	/**
	 * 构造函数
	 */
	public LRFeeRateImport() {
	}

	public boolean submitData(VData vData, String Operater) {
		this.mGlobalInput = (GlobalInput) vData.getObjectByObjectName(
				"GlobalInput", 0);
		TransferData tTransferData = new TransferData();
		tTransferData = (TransferData) vData.getObjectByObjectName(
				"TransferData", 0);
		mBatchNo = (String) tTransferData.getValueByName("BatchNo");
		mFeeTableNo = (String) tTransferData.getValueByName("FeeTableNo");
		mPath = (String) tTransferData.getValueByName("Path");
		mFileName = (String) tTransferData.getValueByName("FileName");
		doAdd();
		return true;
	}

	private boolean doAdd() {
		// 处理批次号：批次号码为文件名（不含扩展名）

		// 从磁盘导入数据
		LRImportAct tLRImportAct = new LRImportAct(mPath + mFileName, mPath
				+ configName, sheetName);
		System.out.println(" path + fileName " + " |  " + mPath + configName);
		if (!tLRImportAct.doImport()) {
			this.mErrors.copyAllErrors(tLRImportAct.mErrrors);
			return false;
		}
		// 得到RIFeeRateTableSet
		RIFeeRateTableSet tRIFeeRateTableSet = (RIFeeRateTableSet) tLRImportAct
				.getSchemaSet();
		// 校验导入的数据
		if (!checkData(tRIFeeRateTableSet)) {
			return false;
		}
		RIFeeRateTableDB tRIFeeRateTableDB = new RIFeeRateTableDB();
		// 存放Insert Into语句的容器
		String sqlHead = "insert into RIFeeRateTable (FeeTableNo,FeeTableName,BatchNo,NumOne,NumTwo,NumThree,NumFour,NumFive,NumSix,NumSeven,NumEight,NumNine,NumTen,StrOne,StrTwo,StrThree,StrFour,StrFive,FeeRate) values (";
		StringBuffer sqlStrBuf;
		RIFeeRateTableSet aRIFeeRateTableSet;
		for (int i = 1; i <= tRIFeeRateTableSet.size(); i++) {
			if (!mFeeTableNo.equals(tRIFeeRateTableSet.get(i).getFeeTableNo())) {
				buildError("doAdd", "导入文件费率表编号错误");
				return false;
			}
			if (!mBatchNo.equals(tRIFeeRateTableSet.get(i).getBatchNo())) {
				buildError("doAdd", "导入文件批次号错误");
				return false;
			}
			tRIFeeRateTableDB.setFeeTableNo(tRIFeeRateTableSet.get(i)
					.getFeeTableNo());
			tRIFeeRateTableDB
					.setBatchNo(tRIFeeRateTableSet.get(i).getBatchNo());
			aRIFeeRateTableSet = tRIFeeRateTableDB.query();
			if (aRIFeeRateTableSet.size() > 0) {
				buildError("doAdd", "该批次费率表已存在，请先删除后在从新导入");
				return false;
			}
			sqlStrBuf = new StringBuffer();
			sqlStrBuf.append(sqlHead);
			sqlStrBuf.append("'" + tRIFeeRateTableSet.get(i).getFeeTableNo()
					+ "',");
			sqlStrBuf.append("'" + tRIFeeRateTableSet.get(i).getFeeTableName()
					+ "',");
			sqlStrBuf.append("'" + tRIFeeRateTableSet.get(i).getBatchNo()
					+ "',");

			sqlStrBuf.append(tRIFeeRateTableSet.get(i).getNumOne() + ",");
			sqlStrBuf.append(tRIFeeRateTableSet.get(i).getNumTwo() + ",");
			sqlStrBuf.append(tRIFeeRateTableSet.get(i).getNumThree() + ",");
			sqlStrBuf.append(tRIFeeRateTableSet.get(i).getNumFour() + ",");
			sqlStrBuf.append(tRIFeeRateTableSet.get(i).getNumFive() + ",");
			sqlStrBuf.append(tRIFeeRateTableSet.get(i).getNumSix() + ",");
			sqlStrBuf.append(tRIFeeRateTableSet.get(i).getNumSeven() + ",");
			sqlStrBuf.append(tRIFeeRateTableSet.get(i).getNumEight() + ",");
			sqlStrBuf.append(tRIFeeRateTableSet.get(i).getNumNine() + ",");
			sqlStrBuf.append(tRIFeeRateTableSet.get(i).getNumTen() + ",");
			if (tRIFeeRateTableSet.get(i).getStrOne() == null) {
				sqlStrBuf.append(null + ",");
			} else {
				sqlStrBuf.append("'" + tRIFeeRateTableSet.get(i).getStrOne()
						+ "',");
			}
			if (tRIFeeRateTableSet.get(i).getStrTwo() == null) {
				sqlStrBuf.append(null + ",");
			} else {
				sqlStrBuf.append("'" + tRIFeeRateTableSet.get(i).getStrTwo()
						+ "',");
			}
			if (tRIFeeRateTableSet.get(i).getStrThree() == null) {
				sqlStrBuf.append(null + ",");
			} else {
				sqlStrBuf.append("'" + tRIFeeRateTableSet.get(i).getStrThree()
						+ "',");
			}
			if (tRIFeeRateTableSet.get(i).getStrFour() == null) {
				sqlStrBuf.append(null + ",");
			} else {
				sqlStrBuf.append("'" + tRIFeeRateTableSet.get(i).getStrFour()
						+ "',");
			}
			if (tRIFeeRateTableSet.get(i).getStrFive() == null) {
				sqlStrBuf.append(null + ",");
			} else {
				sqlStrBuf.append("'" + tRIFeeRateTableSet.get(i).getStrFive()
						+ "',");
			}
			sqlStrBuf.append(tRIFeeRateTableSet.get(i).getFeeRate() + ")");
			mMap.put(sqlStrBuf.toString(), "INSERT");
		}
		if (!submitData(mMap)) {
			buildError("doAdd", "导入费率表失败");
			return false;
		}
		return true;
	}

	/**
	 * 校验导入数据
	 * 
	 * @return boolean
	 */
	private boolean checkData(RIFeeRateTableSet aRIFeeRateTableSet) {
		// String
		// tSql="select tagetclumn,tagetulclumn,tagetdlclumn from rifeeratetableclumndef where feetableno='"+mFeeTableNo+"'";
		// ExeSQL tExeSQL = new ExeSQL();
		// SSRS clumnDef = tExeSQL.execSQL(tSql);
		// StringBuffer tSqlBuffer = new StringBuffer();
		// tSqlBuffer.append("select * from rifeeratetable");
		//
		// for (int i = 1; i <= clumnDef.getMaxRow(); i++) {
		// if (clumnDef.GetText(i, 1)==null) {
		// return false;
		// }
		// }
		String tSql = "select * from (select tagetclumn  from rifeeratetableclumndef where feetableno='"
				+ mFeeTableNo
				+ "' union  select tagetulclumn from rifeeratetableclumndef where feetableno='"
				+ mFeeTableNo
				+ "' union  select tagetdlclumn from rifeeratetableclumndef where feetableno='"
				+ mFeeTableNo + "') where tagetclumn is not null";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS clumnDef = tExeSQL.execSQL(tSql);
		for (int j = 1; j <= clumnDef.getMaxRow(); j++) {
			String clumnName = clumnDef.GetText(j, 1);
			// 如果是数值类型
			if (clumnName.startsWith("Num")) {
				for (int i = 1; i <= aRIFeeRateTableSet.size(); i++) {
					RIFeeRateTableSchema tRIFeeRateTableSchema = aRIFeeRateTableSet
							.get(i);
					if (!tRIFeeRateTableSchema.getV(clumnName).equals("0.0")) {
						break;
					}
					if (i == aRIFeeRateTableSet.size()) {// 如果到最后一条记录仍然是0
						buildError("checkData", clumnName
								+ "数值字段全部为零，请检查后重新导入！");
						return false;
					}
				}
			} else {// 字符类型
				for (int i = 1; i <= aRIFeeRateTableSet.size(); i++) {
					RIFeeRateTableSchema tRIFeeRateTableSchema = aRIFeeRateTableSet
							.get(i);
					if (tRIFeeRateTableSchema.getV(clumnName).equals("null")) {
						buildError("checkData", clumnName
								+ "字符字段存在为空的费率，请检查后重新导入！");
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * @param map
	 *            MMap
	 * @param aLCInsuredSchema
	 *            LCInsuredSchema
	 */
	private boolean addOneRIFeeRateTableSet(
			RIFeeRateTableSchema aRIFeeRateTableSchema) {
		mRIFeeRateTableSet.add(aRIFeeRateTableSchema);
		return true;
	}

	/**
	 * 提交数据到数据库
	 * 
	 * @param map
	 *            MMap
	 * @return boolean
	 */
	private boolean submitData(MMap map) {
		VData data = new VData();
		data.add(map);
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(data, "")) {
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			return false;
		}
		return true;
	}

	/**
	 * 返回执行结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return this.mResult;
	}

	public CErrors getErrors() {
		return this.mErrors;
	}

	/*
	 * add by kevin, 2002-10-14
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "LRFeeRateImport";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 主函数，测试用
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
	}

}
