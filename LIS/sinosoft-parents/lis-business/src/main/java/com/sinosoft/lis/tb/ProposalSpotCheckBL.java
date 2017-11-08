package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.BPOCheckCalModeSchema;
import com.sinosoft.lis.vschema.BPOCheckCalModeSet;
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
 * Description:
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
public class ProposalSpotCheckBL {
private static Logger logger = Logger.getLogger(ProposalSpotCheckBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private BPOCheckCalModeSet mBPOCheckCalModeSet = new BPOCheckCalModeSet();
	private String mOperate;// 操作变量
	private String mOperateType = "";// 操作类型

	public ProposalSpotCheckBL() {

	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("开始执行ProposalSpotCheckBL.java");

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		if (!getInputData(cInputData)) {
			return false;
		}

		// 将操作类型赋值给操作变量
		this.mOperate = mOperateType;

		// 数据操作业务处理
		if (cOperate.equals("INSERT")) {
			if (!checkData()) {
				return false;
			}

			if (!dealData()) {
				return false;
			}

			// logger.debug("---dealData---");
		}
		// if(cOperate.equals("QUERY"))
		// {
		// if(!queryData())
		// {
		// return false;
		// }
		// }

		// if(cOperate.equals("RETURNDATA"))
		// {
		// if(!returnData())
		// return false;
		// }

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		// 首先需要判断是否是修改操作，如果是修改操作，则删除保存到数据库中的数据，再插入本次的数据
		VData tVData = new VData();
		PubSubmit ps = new PubSubmit();
		MMap map = new MMap();

		// 获得第一个Schema，也就是当前的Schema
		BPOCheckCalModeSchema xBPOCheckCalModeSchema = new BPOCheckCalModeSchema();
		xBPOCheckCalModeSchema = mBPOCheckCalModeSet.get(1);
		xBPOCheckCalModeSchema
				.setRate((xBPOCheckCalModeSchema.getRate() / 100));
		logger.debug("******抽查比率是" + xBPOCheckCalModeSchema.getRate());
		mBPOCheckCalModeSet.clear();
		mBPOCheckCalModeSet.add(xBPOCheckCalModeSchema);

		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		String SQL = " select Bpoid from BPOCheckCalMode " 
				+ " where Bpoid='"
				+ "?Bpoid?" + "'";
		sqlbv.sql(SQL);
		sqlbv.put("Bpoid", xBPOCheckCalModeSchema.getBPOID());
		logger.debug("**************判断是否是修改操作的SQL是" + SQL);
		SSRS tSSRS = tExeSQL.execSQL(sqlbv);
		int max = tSSRS.getMaxRow();
		logger.debug("符合条件的记录有" + max + "条");

		// 如果在BPOCheckCalMode(复核抽检算法表)中查寻不到数据，则表明新增操作
		if (max == 0) {
			tVData.clear();
			map.put(mBPOCheckCalModeSet, "INSERT");
			tVData.add(map);
		} else {
			// 删除对应BPOCheckCalMode(复核抽检算法表)中的记录
			logger.debug("需要删除记录的外包方编码是" + tSSRS.GetText(1, 1));
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			String sql = "delete from BPOCheckCalMode where Bpoid='"
					+ "?Bpoid?" + "'";
			sqlbv1.sql(sql);
			sqlbv1.put("Bpoid", tSSRS.GetText(1, 1));
			map.put(sqlbv1, "DELETE");

			// 插入新的记录
			map.put(mBPOCheckCalModeSet, "INSERT");
			tVData.clear();
			tVData.add(map);
		}

		// 将数据放入VData中,调用PubSubmit进行数据库的访问
		if (!ps.submitData(tVData, null)) {

			CError.buildErr(this, "保存数据失败!" + ps.mErrors.getFirstError());
			return false;
		}

		return true;
	}

	private boolean getInputData(VData cInputData) {

		mOperateType = (String) cInputData.get(0);// 接收操作类型
		logger.debug("所要执行的操作类型是*********" + mOperateType);

		// 获得传入的BPOCheckCalModeSet
		mBPOCheckCalModeSet = ((BPOCheckCalModeSet) cInputData
				.getObjectByObjectName("BPOCheckCalModeSet", 0));
		BPOCheckCalModeSchema gBPOCheckCalModeSchema = new BPOCheckCalModeSchema();
		gBPOCheckCalModeSchema = mBPOCheckCalModeSet.get(1);

		logger.debug("BL层获得的外包方编码是" + gBPOCheckCalModeSchema.getBPOID());
		logger.debug("BL层获得的险种编码是" + gBPOCheckCalModeSchema.getRiskCode());
		logger.debug("BL层获得的抽查比率是" + gBPOCheckCalModeSchema.getRate());
		logger.debug("BL层获得的抽查上限是" + gBPOCheckCalModeSchema.getMaxMum());
		logger.debug("BL层获得的备注是" + gBPOCheckCalModeSchema.getRemark());

		return true;
	}

	/**
	 * 校验传入的比率和上限是否为整数 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		// 获得第一个Schema，也就是当前的Schema
		BPOCheckCalModeSchema tBPOCheckCalModeSchema = new BPOCheckCalModeSchema();
		tBPOCheckCalModeSchema = mBPOCheckCalModeSet.get(1);

		// 校验抽查比率是否在0-100之间的整数
		Double d = new Double(tBPOCheckCalModeSchema.getRate());

		if (!isOneToHundred(d.intValue())) {
			CError tError = new CError();
			tError.moduleName = "ProposalSpotCheckBL";
			tError.functionName = "checkData";
			tError.errorMessage = "录入的抽查比率不是0-100之间的整数，请重新录入！！！";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 校验抽查上限是否在0-100之间的整数
		// if(!(String.valueOf(tBPOCheckCalModeSchema.getMaxMum())==null||String.valueOf(tBPOCheckCalModeSchema.getMaxMum())==""))
		// {
		// if (!isOneToHundred(tBPOCheckCalModeSchema.getMaxMum()))
		// {
		// CError tError = new CError();
		// tError.moduleName = "ProposalSpotCheckBL";
		// tError.functionName = "checkData";
		// tError.errorMessage = "录入的抽查上限不是0-100之间的整数，请重新录入！！！";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// }

		return true;
	}

	// 判断是否在0-100之间的整数：
	private boolean isOneToHundred(int input) {
		logger.debug("判断是否为整数的input值=" + input);
		if (input < 0 || input > 100) {
			return false;
		}

		return true;
	}

	public static void main(String[] args) {
		BPOCheckCalModeSet tBPOCheckCalModeSet = new BPOCheckCalModeSet();
		BPOCheckCalModeSchema aBPOCheckCalModeSchema = new BPOCheckCalModeSchema();
		aBPOCheckCalModeSchema.setBPOID("004");
		aBPOCheckCalModeSchema.setRiskCode("111802");
		aBPOCheckCalModeSchema.setRate("11");
		aBPOCheckCalModeSchema.setMaxMum("");
		aBPOCheckCalModeSchema.setRemark("备注");

		String xOperateType = "INSERT";
		// 将Schema放入Set中
		tBPOCheckCalModeSet.add(aBPOCheckCalModeSchema);
		VData tVData = new VData();

		// 将数据提交给后台UI,参数是VData和操作类型
		ProposalSpotCheckBL mProposalSpotCheckBL = new ProposalSpotCheckBL();
		try {
			// 将操作类型，管理机构，操作员添加到容器中
			tVData.addElement(xOperateType);
			tVData.addElement(tBPOCheckCalModeSet);

			mProposalSpotCheckBL.submitData(tVData, xOperateType);
			// mProposalSpotCheckBL.isInteger("x");
		} catch (Exception ex) {
			String Content = "失败，原因是:" + ex.toString();
		}
	}
}
