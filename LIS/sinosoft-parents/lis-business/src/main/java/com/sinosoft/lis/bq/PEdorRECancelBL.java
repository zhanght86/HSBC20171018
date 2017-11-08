package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 保全业务处理系统
 * </p>
 * 
 * <p>
 * Description: 复效的撤销处理类
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Lizhuo
 * @version 1.0
 */
public class PEdorRECancelBL implements EdorCancel {
private static Logger logger = Logger.getLogger(PEdorRECancelBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	private MMap map = new MMap();
	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	// 需要撤销的保全项目
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	public PEdorRECancelBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param cInputData
	 *            传入的数据对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (!getInputData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareData()) {
			return false;
		}

		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLPEdorItemSchema = // 需要撤销的保全项目
			(LPEdorItemSchema) mInputData.getObjectByObjectName(
					"LPEdorItemSchema", 0);
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, "接收传入数据失败!");
			return false;
		}
		if (mGlobalInput == null || mLPEdorItemSchema == null) {
			CError.buildErr(this, "传入数据有误!");
			return false;
		}

		return true;
	}

	private boolean dealData() {
		String info = " edorno = '?edorno?'"
				+ " and edortype = '?edortype?'"
				+ " and contno = '?contno?'";
		String edorinfo = " edorno = '?edorno?'"
				+ " and edortype = '?edortype?'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql("delete from lppol where " + info);
		sbv1.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv1.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv1.put("contno", mLPEdorItemSchema.getContNo());
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql("delete from lpduty where " + info);
		sbv2.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv2.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv2.put("contno", mLPEdorItemSchema.getContNo());
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql("delete from lpprem where " + info);
		sbv3.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv3.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv3.put("contno", mLPEdorItemSchema.getContNo());
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql("delete from lpget where " + info);
		sbv4.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv4.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv4.put("contno", mLPEdorItemSchema.getContNo());
		SQLwithBindVariables sbv5=new SQLwithBindVariables();
		sbv5.sql("delete from lpcontstate where " + info);
		sbv5.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv5.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv5.put("contno", mLPEdorItemSchema.getContNo());
		map.put(sbv1, "DELETE");
		map.put(sbv2, "DELETE");
		map.put(sbv3, "DELETE");
		map.put(sbv4, "DELETE");
		map.put(sbv5, "DELETE");

		ExeSQL tES = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String QueryPayCount = "select (case when min(paycount) is not null then min(paycount) else 0 end) from ljspayperson where contno = '?contno?' and paytype = 'RE'";
		SQLwithBindVariables sbv6=new SQLwithBindVariables();
		sbv6.sql(QueryPayCount);
		sbv6.put("contno", mLPEdorItemSchema.getContNo());
		tSSRS = tES.execSQL(sbv6);
		if (tSSRS != null && tSSRS.getMaxRow() > 0) {
			if (tSSRS.GetText(1, 1) != null
					&& !tSSRS.GetText(1, 1).trim().equals("")
					&& !tSSRS.GetText(1, 1).trim().equals("0")) {
				int minPayCount = Integer.parseInt(tSSRS.GetText(1, 1));
				tSSRS.Clear();
				String QueryStr = "select 1 from ljspayperson a where paytype = 'RE'"
						+ " and exists (select 2 from ljspayperson "
						+ " where contno = a.contno "
						+ " and polno = a.polno"
						+ " and paycount = a.paycount"
						+ " and riskcode = a.riskcode"
						+ " and paytype = 'ZC')"
						+ " and contno = '?contno?'" + " and paycount = ?minPayCount?";
				SQLwithBindVariables sbv7=new SQLwithBindVariables();
				sbv7.sql(QueryStr);
				sbv7.put("contno", mLPEdorItemSchema.getContNo());
				sbv7.put("minPayCount", minPayCount);
				tSSRS = tES.execSQL(sbv7);
				if (tSSRS != null && tSSRS.MaxRow > 0) {
					String delpayperson = "delete from ljspayperson where 1=1 "
							+ " and contno = '?contno?'" + " and paytype = 'RE'";
					SQLwithBindVariables sbv8=new SQLwithBindVariables();
					sbv8.sql(delpayperson);
					sbv8.put("contno", mLPEdorItemSchema.getContNo());
					map.put(sbv8, "DELETE");
				} else {
					// 兼容以前数据
					String updatepayperson = "update ljspayperson set paytype = 'ZC',paydate = lastpaytodate where paytype = 'RE'"
							+ " and contno = '?contno?'" + " and paycount = ?minPayCount?";
					SQLwithBindVariables sbv9=new SQLwithBindVariables();
					sbv9.sql(updatepayperson);
					sbv9.put("contno", mLPEdorItemSchema.getContNo());
					sbv9.put("minPayCount", minPayCount);
					map.put(sbv9, "UPDATE");
					String delpayperson = "delete from ljspayperson where 1=1"
							+ " and contno = '?contno?'" + " and paycount > ?minPayCount? and paytype = 'RE'";
					SQLwithBindVariables sbv10=new SQLwithBindVariables();
					sbv10.sql(delpayperson);
					sbv10.put("contno", mLPEdorItemSchema.getContNo());
					sbv10.put("minPayCount", minPayCount);
					map.put(sbv10, "DELETE");
				}
			}
		}

		SQLwithBindVariables sbv11=new SQLwithBindVariables();
		sbv11.sql("delete from LPCustomerImpart where " + info);
		sbv11.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv11.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv11.put("contno", mLPEdorItemSchema.getContNo());
		SQLwithBindVariables sbv12=new SQLwithBindVariables();
		sbv12.sql("delete from LPCustomerImpartParams where " + info);
		sbv12.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv12.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv12.put("contno", mLPEdorItemSchema.getContNo());
		SQLwithBindVariables sbv13=new SQLwithBindVariables();
		sbv13.sql("delete from lpcont where " + info);
		sbv13.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv13.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv13.put("contno", mLPEdorItemSchema.getContNo());
		SQLwithBindVariables sbv14=new SQLwithBindVariables();
		sbv14.sql("delete from LPIssuePol where " + edorinfo);
		sbv14.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv14.put("edortype", mLPEdorItemSchema.getEdorType());
		SQLwithBindVariables sbv15=new SQLwithBindVariables();
		sbv15.sql("delete from LPCUWSub where " + info);
		sbv15.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv15.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv15.put("contno", mLPEdorItemSchema.getContNo());
		SQLwithBindVariables sbv16=new SQLwithBindVariables();
		sbv16.sql("delete from LPCUWMaster where " + info);
		sbv16.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv16.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv16.put("contno", mLPEdorItemSchema.getContNo());
		SQLwithBindVariables sbv17=new SQLwithBindVariables();
		sbv17.sql("delete from LPCUWError where " + info);
		sbv17.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv17.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv17.put("contno", mLPEdorItemSchema.getContNo());
		SQLwithBindVariables sbv18=new SQLwithBindVariables();
		sbv18.sql("delete from LPSpec where " + info);
		sbv18.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv18.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv18.put("contno", mLPEdorItemSchema.getContNo());
		map.put(sbv11, "DELETE");
		map.put(sbv12, "DELETE");
		map.put(sbv13, "DELETE");
		map.put(sbv14, "DELETE");
		map.put(sbv15, "DELETE");
		map.put(sbv16, "DELETE");
		map.put(sbv17, "DELETE");
		map.put(sbv18, "DELETE");

		String delgetendorse = "delete from ljsgetendorse where 1=1"
				+ " and endorsementno = '?endorsementno?'" + " and FeeOperationType = '?FeeOperationType?'" + " and contno = '?contno?'";
		SQLwithBindVariables sbv19=new SQLwithBindVariables();
		sbv19.sql(delgetendorse);
		sbv19.put("endorsementno", mLPEdorItemSchema.getEdorNo());
		sbv19.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		sbv19.put("contno", mLPEdorItemSchema.getContNo());
		map.put(sbv19, "DELETE");

		SQLwithBindVariables sbv20=new SQLwithBindVariables();
		sbv20.sql("delete from LPEngBonusPol where " + info);
		sbv20.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv20.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv20.put("contno", mLPEdorItemSchema.getContNo());
		SQLwithBindVariables sbv21=new SQLwithBindVariables();
		sbv21.sql("delete from lpedoritem where " + info);
		sbv21.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv21.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv21.put("contno", mLPEdorItemSchema.getContNo());
		map.put(sbv20, "DELETE");
		map.put(sbv21, "DELETE");

		String deledor = "delete from lpedormain where 1=1 "
				+ " and edorno = '?endorsementno?'"
				+ " and contno = '?contno?'";
		SQLwithBindVariables sbv22=new SQLwithBindVariables();
		sbv22.sql(deledor);
		sbv22.put("endorsementno", mLPEdorItemSchema.getEdorNo());
		sbv22.put("contno", mLPEdorItemSchema.getContNo());
		map.put(sbv22, "DELETE");

		logger.debug("after PEdorRECancelBL =========");

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareData() {
		mResult.clear();
		mResult.add(map);
		return true;
	}

	/**
	 * 数据输出，供外层获取数据处理结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 错误返回，供外层获取处理错误信息
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		VData tVD = new VData();
		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "lee";
		tGI.ManageCom = "86";
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorNo("6020051229000095");
		tLPEdorItemSchema.setEdorType("RE");
		tLPEdorItemSchema.setContNo("NJ020126011000957");
		tVD.add(tGI);
		tVD.add(tLPEdorItemSchema);
		PEdorRECancelBL tLPEdorRECancelBL = new PEdorRECancelBL();
		tLPEdorRECancelBL.submitData(tVD, "");

		PubSubmit tPubSubmit = new PubSubmit();
		tPubSubmit.submitData(tLPEdorRECancelBL.getResult(), "");

	}

}
