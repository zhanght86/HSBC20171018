/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContPrintDB;
import com.sinosoft.lis.schema.LCContPrintSchema;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContPrintTraceSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.vschema.LCContPrintTraceSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XMLDatasets;

/*
 * <p>ClassName: LCContF1PBL </p> <p>Description: LCPolF1BL类文件 </p> <p>Copyright:
 * Copyright (c) 2002</p> <p>Company: sinosoft </p> @Database: LIS
 * @CreateDate：2002-11-04
 */
public class ReLCContF1PBL {
private static Logger logger = Logger.getLogger(ReLCContF1PBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mVData = new VData();
	private VData mResult = new VData();
	
	private MMap mMap = new MMap();

	// 业务处理相关变量
	/** 全局数据 */          
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCContSet mOldLCContSet = new LCContSet();
	private LCContSet mNewLCContSet = new LCContSet();
	
	private LCContPrintTraceSet mOldLCContPrintTraceSet = new LCContPrintTraceSet();
	private LCContPrintTraceSet mNewLCContPrintTraceSet = new LCContPrintTraceSet();
	private XMLDatasets mXMLDatasets = new XMLDatasets();
	private String mOperate = "";

	/*
	 * 对于同时传入主险和附加险保单号的情况，如果它们是同一个印刷号的， 将被存在同一个保单数据块中。所以将打印过的保单号存放在这个Vector中。
	 */
	// private Vector m_vPolNo = new Vector();
	public ReLCContF1PBL() {
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
			if(cOperate.equals("PRINT"))
			{
				if (!getPrint()) {
					return false;
				}	
			}
            if(cOperate.equals("CONFIRM"))
            {
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
		mOldLCContSet.set((LCContSet) cInputData.getObjectByObjectName(
				"LCContSet", 0));       
		mOldLCContPrintTraceSet.set((LCContPrintTraceSet) cInputData.getObjectByObjectName(
				"LCContPrintTraceSet", 0));
		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		return true;
	}

	/**
	 * 修改保单打印状态
	 * 
	 * @return boolean
	 */
	private boolean getPrint() {
		LCContDB tLCContDB = null;
		// 查询前台传入的数据信息
		for (int nIndex = 1; nIndex <= mOldLCContSet.size(); nIndex++) {
			tLCContDB = new LCContDB();
			LCContSchema tLCContSchema = new LCContSchema();
			tLCContSchema=mOldLCContSet.get(nIndex);
			// 查询 家庭单号,跟新此保单下所有的保单
			String rContNo = "";
			String rSQLAdd = "";
			if (("2".equals(tLCContSchema.getFamilyType())
					&& !"".equals(tLCContSchema.getFamilyContNo()) && tLCContSchema
					.getFamilyContNo() != null)
					|| ("1".equals(tLCContSchema.getFamilyType())
							&& !"".equals(tLCContSchema.getFamilyContNo()) && tLCContSchema
							.getFamilyContNo() != null)) {
				rContNo = tLCContSchema.getFamilyContNo();
				
				tLCContDB.setFamilyContNo(rContNo);
				tLCContDB.setAppFlag("1");
				LCContSet  mLCContSet =new LCContSet();
				mLCContSet=tLCContDB.query();
				for(int k=1;k<=mLCContSet.size();k++)
				{
					LCContSchema rLCContSchema = new LCContSchema();
					rLCContSchema=mLCContSet.get(k);
					rLCContSchema.setPrintCount(-1);
					rLCContSchema.setOperator(mGlobalInput.Operator);
					rLCContSchema.setModifyDate(PubFun.getCurrentDate());
					rLCContSchema.setModifyTime(PubFun.getCurrentTime());
					// 将新数据放入到准备传入后台的容器中
					mNewLCContSet.add(rLCContSchema);
				}
			} else {
				tLCContDB.setContNo(tLCContSchema.getContNo());
				tLCContDB.getInfo();
				
				LCContSchema rLCContSchema = new LCContSchema();
				rLCContSchema=tLCContDB.getSchema();
				rLCContSchema.setPrintCount(-1);
				rLCContSchema.setOperator(mGlobalInput.Operator);
				rLCContSchema.setModifyDate(PubFun.getCurrentDate());
				rLCContSchema.setModifyTime(PubFun.getCurrentTime());
				// 将新数据放入到准备传入后台的容器中
				mNewLCContSet.add(rLCContSchema);
			}			         
		}

		mMap.put(mNewLCContSet,"UPDATE");
		for (int i = 1; i <= mOldLCContPrintTraceSet.size(); i++) {
			LCContPrintTraceSchema tLCContPrintTraceSchema = mOldLCContPrintTraceSet
					.get(i);
			tLCContDB.setContNo(tLCContPrintTraceSchema.getContNo());
			tLCContDB.getInfo();

			LCContSchema tLCContSchema = new LCContSchema();
			tLCContSchema = tLCContDB.getSchema();
			String rContNo = "";
			// 如果是家庭单，则需要将期保单号置为家庭单号
			if (("2".equals(tLCContSchema.getFamilyType())
					&& !"".equals(tLCContSchema.getFamilyContNo()) && tLCContSchema
					.getFamilyContNo() != null)
					|| ("1".equals(tLCContSchema.getFamilyType())
							&& !"".equals(tLCContSchema.getFamilyContNo()) && tLCContSchema
							.getFamilyContNo() != null)) {
				rContNo = tLCContSchema.getFamilyContNo();
			} else {
				rContNo = tLCContSchema.getContNo();
			}
			LCContPrintDB tLCContPrintDB = new LCContPrintDB();
			tLCContPrintDB.setContNo(tLCContPrintTraceSchema.getContNo());
			tLCContPrintDB.getInfo();

			LCContPrintSchema tLLCContPrintSchema = new LCContPrintSchema();
			tLLCContPrintSchema = tLCContPrintDB.getSchema();
			tLCContPrintTraceSchema.setFMakeDate(tLLCContPrintSchema.getMakeDate());
			tLCContPrintTraceSchema.setFMakeTime(tLLCContPrintSchema.getMakeTime());
			tLCContPrintTraceSchema.setContNo(rContNo);
			tLCContPrintTraceSchema.setContType(tLLCContPrintSchema.getContType());
			tLCContPrintTraceSchema.setRePrintAppFlag("1");
			tLCContPrintTraceSchema.setRePrintFlag("0");
			tLCContPrintTraceSchema.setOperator(mGlobalInput.Operator);
			tLCContPrintTraceSchema.setMakeDate(PubFun.getCurrentDate());
			tLCContPrintTraceSchema.setMakeTime(PubFun.getCurrentTime());
			mNewLCContPrintTraceSet.add(tLCContPrintTraceSchema);
		}
		mMap.put(mNewLCContPrintTraceSet,"DELETE&INSERT");

		return true;
	}

	/**
	 * 修改保单打印状态
	 * 
	 * @return boolean
	 */
	private boolean getConfirm() {
		LCContDB tLCContDB = null;
		// 查询前台传入的数据信息
		for (int nIndex = 1; nIndex <= mOldLCContSet.size(); nIndex++) {
			tLCContDB = new LCContDB();
			LCContSchema tLCContSchema = new LCContSchema();
			tLCContSchema=mOldLCContSet.get(nIndex);
			// 查询 家庭单号,跟新此保单下所有的保单
			String rContNo = "";
			String rSQLAdd = "";
			if (("2".equals(tLCContSchema.getFamilyType())
					&& !"".equals(tLCContSchema.getFamilyContNo()) && tLCContSchema
					.getFamilyContNo() != null)
					|| ("1".equals(tLCContSchema.getFamilyType())
							&& !"".equals(tLCContSchema.getFamilyContNo()) && tLCContSchema
							.getFamilyContNo() != null)) {
				rContNo = tLCContSchema.getFamilyContNo();
				
				tLCContDB.setFamilyContNo(rContNo);
				tLCContDB.setAppFlag("1");
				LCContSet  mLCContSet =new LCContSet();
				mLCContSet=tLCContDB.query();
				for(int k=1;k<=mLCContSet.size();k++)
				{
					LCContSchema rLCContSchema = new LCContSchema();
					rLCContSchema=mLCContSet.get(k);
					rLCContSchema.setPrintCount(0);
					rLCContSchema.setOperator(mGlobalInput.Operator);
					rLCContSchema.setModifyDate(PubFun.getCurrentDate());
					rLCContSchema.setModifyTime(PubFun.getCurrentTime());
					// 将新数据放入到准备传入后台的容器中
					mNewLCContSet.add(rLCContSchema);
				}
			} else {
				tLCContDB.setContNo(tLCContSchema.getContNo());
				tLCContDB.getInfo();
				
				LCContSchema rLCContSchema = new LCContSchema();
				rLCContSchema=tLCContDB.getSchema();
				rLCContSchema.setPrintCount(0);
				rLCContSchema.setOperator(mGlobalInput.Operator);
				rLCContSchema.setModifyDate(PubFun.getCurrentDate());
				rLCContSchema.setModifyTime(PubFun.getCurrentTime());
				// 将新数据放入到准备传入后台的容器中
				mNewLCContSet.add(rLCContSchema);
			}			         
		}

		mMap.put(mNewLCContSet,"UPDATE");
		for (int i = 1; i <= mOldLCContPrintTraceSet.size(); i++) {
			LCContPrintTraceSchema tLCContPrintTraceSchema = mOldLCContPrintTraceSet
					.get(i);
			tLCContDB.setContNo(tLCContPrintTraceSchema.getContNo());
			tLCContDB.getInfo();

			LCContSchema tLCContSchema = new LCContSchema();
			tLCContSchema = tLCContDB.getSchema();
			String rContNo = "";
			// 如果是家庭单，则需要将期保单号置为家庭单号
			if (("2".equals(tLCContSchema.getFamilyType())
					&& !"".equals(tLCContSchema.getFamilyContNo()) && tLCContSchema
					.getFamilyContNo() != null)
					|| ("1".equals(tLCContSchema.getFamilyType())
							&& !"".equals(tLCContSchema.getFamilyContNo()) && tLCContSchema
							.getFamilyContNo() != null)) {
				rContNo = tLCContSchema.getFamilyContNo();
			} else {
				rContNo = tLCContSchema.getContNo();
			}
			LCContPrintDB tLCContPrintDB = new LCContPrintDB();
			tLCContPrintDB.setContNo(tLCContPrintTraceSchema.getContNo());
			tLCContPrintDB.getInfo();

			LCContPrintSchema tLLCContPrintSchema = new LCContPrintSchema();
			tLLCContPrintSchema = tLCContPrintDB.getSchema();
			tLCContPrintTraceSchema.setFMakeDate(tLLCContPrintSchema.getMakeDate());
			tLCContPrintTraceSchema.setFMakeTime(tLLCContPrintSchema.getMakeTime());
			tLCContPrintTraceSchema.setContNo(rContNo);
			tLCContPrintTraceSchema.setContType(tLLCContPrintSchema.getContType());
			tLCContPrintTraceSchema.setRePrintAppFlag("1");
			tLCContPrintTraceSchema.setRePrintFlag("0");
			tLCContPrintTraceSchema.setOperator(mGlobalInput.Operator);
			tLCContPrintTraceSchema.setMakeDate(PubFun.getCurrentDate());
			tLCContPrintTraceSchema.setMakeTime(PubFun.getCurrentTime());
			mNewLCContPrintTraceSet.add(tLCContPrintTraceSchema);
		}
		mMap.put(mNewLCContPrintTraceSet,"DELETE&INSERT");

		return true;
	}
	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}
	
	/**
	 * 构建错误信息
	 * 
	 * @param cFunc
	 *            String
	 * @param cErrMsg
	 *            String
	 */
	private void buildError(String cFunc, String cErrMsg) {
		CError tError = new CError();
		tError.moduleName = "LCContF1PBL";
		tError.functionName = cFunc;
		tError.errorMessage = cErrMsg;
		this.mErrors.addOneError(tError);
	}
}
