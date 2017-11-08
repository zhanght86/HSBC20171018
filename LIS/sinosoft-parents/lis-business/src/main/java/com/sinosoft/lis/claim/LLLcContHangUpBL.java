/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LLCaseDB;
import com.sinosoft.lis.operfee.IndiDueFeeCancelBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.LCContHangUpBL;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContHangUpStateSchema;
import com.sinosoft.lis.vschema.LCContHangUpStateSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LLCaseSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 保单挂起
 * </p>
 * <p>
 * Description: 解除保单挂起逻辑处理类
 * </p>
 * Copyright (c) 2005 sinosoft Co. Ltd.
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author: zl
 * @version 1.0
 */

public class LLLcContHangUpBL {
private static Logger logger = Logger.getLogger(LLLcContHangUpBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private MMap map = new MMap();

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private LCContHangUpStateSet mLCContHangUpStateSet = new LCContHangUpStateSet();
	private GlobalInput mG = new GlobalInput();

	private String mClmNo = ""; // 赔案号

	private TransferData mTransferData = new TransferData();

	public LLLcContHangUpBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
		logger.debug("----------LLLcContHangUpBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		if (!getInputData())// 得到外部传入的数据,将数据备份到本类中
		{
			return false;
		}
		logger.debug("----------after getInputData----------");
		if (!checkInputData()) // 检查数据合法性
		{
			return false;
		}
		logger.debug("----------after checkInputData----------");
		if (!dealData())// 进行业务处理
		{
			return false;
		}
		logger.debug("----------after dealData----------");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------after prepareOutputData----------");

		mInputData = null;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("--start getInputData()");

		// 获取页面报案信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);// 按类取值

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mClmNo = (String) mTransferData.getValueByName("RptNo"); // 赔案号

		if (mClmNo == null)// @@错误处理
		{
			CError tError = new CError();
			tError.moduleName = "LLLcContHangUpBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接受的赔案号为空!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		logger.debug("----------begin checkInputData----------");
		try {
			// 检测数据
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLLcContHangUpBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "在校验输入的数据时出错!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("------start dealData-----");

		String strSQL = "";
		LCContSet tLCContSet = new LCContSet();

		// 查询出险人集合
		strSQL = "select * from llcase where caseno = '" + "?clmno?" + "'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strSQL);
		sqlbv.put("clmno", mClmNo);
		LLCaseDB tLLCaseDB = new LLCaseDB();
		LLCaseSet tSet = tLLCaseDB.executeQuery(sqlbv);
		if (tSet == null || tSet.size() <= 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLLcContHangUpBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询出险人信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 判断是否死亡类案件
		strSQL = "";
		strSQL = "select count(*) from llappclaimreason b where"
				+ " substr(b.ReasonCode,2,2) = '02' " + " and b.caseno = '"
				+ "?clmno?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strSQL);
		sqlbv1.put("clmno", mClmNo);
		ExeSQL tExeSQL = new ExeSQL();
		String tFlag = tExeSQL.getOneValue(sqlbv1);
		if (tFlag == "" || tFlag == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLLcContHangUpBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询理赔类型信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// ===by zl====为防止联生险按出险人查询时重复提交挂起导致插入失败====2006-3-22 15:12====beg
		// 组成被保人集合
		String tNo = "";
		for (int i = 1; i <= tSet.size(); i++) {
			tNo = tNo + "'" + tSet.get(1).getCustomerNo() + "'";
			if (i < tSet.size()) {
				tNo = tNo + ",";
			}
		}

		// 提取需要挂起合同集合
		if (tFlag.equals("0")) {
			// //非死亡类案件查找所有作为被保人及与赔案相关的保单
			// for (int i = 1; i <= tSet.size(); i++)
			// {
			// strSQL = "select * from LcCont where "
			// + " insuredno = '" + tSet.get(i).getCustomerNo() + "'"
			// + " union "
			// + "select * from lccont where contno in "
			// + " (select distinct contno from llclaimpolicy "
			// + " where clmno = '" + mClmNo + "')";
			// LCContSet tCSet = new LCContSet();
			// LCContDB tCDB = new LCContDB();
			// tCSet = tCDB.executeQuery(strSQL);
			// tLCContSet.add(tCSet);
			// }

			// 非死亡类案件查找所有作为被保人及与赔案相关的保单
			strSQL = "select * from LcCont where contno in "
					+ "(select distinct contno from lcinsured where "
					+ " insuredno in (" + "?customerno?" + ")" + " union "
					+ " select distinct contno from llclaimpolicy where "
					+ " clmno = '" + "?clmno?" + "')";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(strSQL);
			sqlbv2.put("customerno", tNo);
			sqlbv2.put("clmno", mClmNo);
			LCContSet tCSet = new LCContSet();
			LCContDB tCDB = new LCContDB();
			tCSet = tCDB.executeQuery(sqlbv2);
			tLCContSet.add(tCSet);
		} else {
			// 死亡类案件,查找所有作为投保和被保人的合同
			strSQL = "(select * from LcCont where AppntNo in (" + "?customerno?" + "))"
					+ " union" + "(select * from LcCont where contno in "
					+ " (select distinct contno from lcinsured where "
					+ " insuredno in (" + "?customerno?" + ")))";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(strSQL);
			sqlbv3.put("customerno", tNo);
			sqlbv3.put("clmno", mClmNo);
			LCContSet tCSet = new LCContSet();
			LCContDB tCDB = new LCContDB();
			tCSet = tCDB.executeQuery(sqlbv3);
			tLCContSet.add(tCSet);
		}			
			VData CResult = new VData();
			MMap cmap = null;
			
			for (int j = 1; j <= tLCContSet.size(); j++) {				
				LJSPaySet tLJSPaySet = new LJSPaySet();
				LJSPayDB tLJSPayDB = new LJSPayDB();
				tLJSPayDB.setOtherNo(tLCContSet.get(j).getContNo());
				tLJSPaySet = tLJSPayDB.query();
				if (tLJSPaySet.size() <= 0) {
					continue;
				}
				
				//撤销续期催收
				VData ppVData= new VData();
				ppVData.add(tLCContSet.get(j));
				ppVData.add(mG);			
				IndiDueFeeCancelBL IndiDueFeeCancelBL = new IndiDueFeeCancelBL();
				if(!IndiDueFeeCancelBL.submitData(ppVData, "LiPeiDelete"))
				{
					mErrors.copyAllErrors(IndiDueFeeCancelBL.mErrors);
					CError.buildErr(this, "续期催收撤销失败！");
					return false;
				}
				else
				{
					CResult = IndiDueFeeCancelBL.getResult();
					cmap = new MMap();
					
					if(CResult.size()>0)
					{
						cmap =(MMap)CResult.get(0);
						//mResult.add(cmap);
						map.add(cmap);
						cmap=null;
					}
				}
				
				ppVData=null;
			}

		// 团险不需要保单挂起，故封掉保单挂起功能
		// ===by zl====为防止联生险按出险人查询时重复提交挂起导致插入失败====2006-3-22 15:12====beg
		

		// 提交给挂起处理类
		if (tLCContSet != null && tLCContSet.size() != 0) {
			for (int j = 1; j <= tLCContSet.size(); j++) {
				LCContHangUpStateSchema tLCContHangUpStateSchema = new LCContHangUpStateSchema(); // 个人保单表
				tLCContHangUpStateSchema.setContNo(tLCContSet.get(j)
						.getContNo()); // 合同号
				tLCContHangUpStateSchema.setInsuredNo("000000"); // 被保险人号码
				tLCContHangUpStateSchema.setPolNo("000000"); // 保单险种号
				tLCContHangUpStateSchema.setPosFlag("1"); // 保全挂起状态
				tLCContHangUpStateSchema.setRNFlag("1"); // 续期挂起状态
				mLCContHangUpStateSet.add(tLCContHangUpStateSchema);
			}

			// 保单挂起处理
			if (!dealHangUp()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LLLcContHangUpBL";
				tError.functionName = "dealData";
				tError.errorMessage = "保单挂起处理失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealHangUp() {
		// 调用公用挂起方法
		LCContHangUpStateSet tLCContHangUpStateSaveSet = new LCContHangUpStateSet(); // 保存

		for (int i = 1; i <= mLCContHangUpStateSet.size(); i++) {
			String tContNo = mLCContHangUpStateSet.get(i).getContNo();

			// 判断该合同是否被挂起
			LCContHangUpBL tLCContHangUpBL = new LCContHangUpBL(mG, mClmNo,
					"4", tContNo);
			if (!tLCContHangUpBL.queryHungUpForContNo()) {
				mErrors.copyAllErrors(tLCContHangUpBL.mErrors);
				return false;
			}

			String tPosFlag = mLCContHangUpStateSet.get(i).getPosFlag();
			String tRNFlag = mLCContHangUpStateSet.get(i).getRNFlag();
			tLCContHangUpBL.setPosFlag(tPosFlag); // 1挂起,0正常
			tLCContHangUpBL.setRnFlag(tRNFlag);
			tLCContHangUpBL.setClmFlag("1");//2008-11-22 zhangzheng 挂起时也必须同时挂起理赔,即不能同时对同一个合同进行交叉理赔
			LCContHangUpStateSchema tLCContHangUpStateSchema = tLCContHangUpBL
					.getHungUp();

			tLCContHangUpStateSaveSet.add(tLCContHangUpStateSchema);

		}

		map.put(tLCContHangUpStateSaveSet, "DELETE&INSERT");

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			//mResult.clear();
			mResult.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLLcContHangUpBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private void jbInit() throws Exception {
		//
	}
}
