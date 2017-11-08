/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XMLDatasets;

/*
 * <p>ClassName: LCGrpContF1PBL </p> <p>Description: LCPolF1BL类文件 </p> <p>Copyright:
 * Copyright (c) 2002</p> <p>Company: sinosoft </p> @Database: LIS
 * @CreateDate：2002-11-04
 */
public class ReLCGrpContF1PBL {
private static Logger logger = Logger.getLogger(ReLCGrpContF1PBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCGrpContSet mOldLCGrpContSet = new LCGrpContSet();
	private LCGrpContSet mNewLCGrpContSet = new LCGrpContSet();
	private XMLDatasets mXMLDatasets = new XMLDatasets();
	private String mOperate = "";
	private MMap mMap = new MMap();

	/*
	 * 对于同时传入主险和附加险保单号的情况，如果它们是同一个印刷号的， 将被存在同一个保单数据块中。所以将打印过的保单号存放在这个Vector中。
	 */
	// private Vector m_vPolNo = new Vector();
	public ReLCGrpContF1PBL() {
		mXMLDatasets.createDocument();
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {
			if (!cOperate.equals("PRINT") && !cOperate.equals("CONFIRM")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}

			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData)) {
				return false;
			}
			if (cOperate.equals("PRINT"))
			// 准备所有要打印的数据
			{
				if (!getPrintData()) {
					return false;
				}
			} else if (cOperate.equals("CONFIRM")) {
				if (!getConfirm()) {
					return false;
				}
			}
			mResult.clear();			
			mResult.add(mMap);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("submit", "发生异常");
			return false;
		}
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 获取前台传入的数据
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mOldLCGrpContSet.set((LCGrpContSet) cInputData.getObjectByObjectName(
				"LCGrpContSet", 0));
		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		return true;
	}

	/**
	 * 获取返回信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "LCGrpContF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
   /**补打,不再进行查询*/
	private boolean getPrintData() {
		LCGrpContDB tLCGrpContDB = null;
		for (int nIndex = 1; nIndex <= mOldLCGrpContSet.size(); nIndex++) {
			tLCGrpContDB = new LCGrpContDB();
			tLCGrpContDB.setGrpContNo(mOldLCGrpContSet.get(nIndex)
					.getGrpContNo());
			tLCGrpContDB.getInfo();
	
			tLCGrpContDB.setPrintCount(-1);
			mNewLCGrpContSet.add(tLCGrpContDB.getSchema());

		}
		mMap.put(mNewLCGrpContSet,"UPDATE");

		return true;
	}

	/**
	 * 重新生成打印数据处理
	 * 
	 * @return boolean
	 */
	private boolean getConfirm() {
		LCGrpContDB tLCGrpContDB = null;
		// 循环外部传入的数据，获取每个个单合同的详细信息
		for (int nIndex = 1; nIndex <= mOldLCGrpContSet.size(); nIndex++) {
			tLCGrpContDB = new LCGrpContDB();
			tLCGrpContDB.setGrpContNo(mOldLCGrpContSet.get(nIndex)
					.getGrpContNo());
			tLCGrpContDB.getInfo();
			// 将打印记录置为0
			tLCGrpContDB.setPrintCount(0);
			mNewLCGrpContSet.add(tLCGrpContDB);
		}
		mMap.put(mNewLCGrpContSet,"UPDATE");
		return true;
	}
}
