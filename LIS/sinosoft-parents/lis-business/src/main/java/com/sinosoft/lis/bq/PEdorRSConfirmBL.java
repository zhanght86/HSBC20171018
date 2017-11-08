package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全－保单加密
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author pst
 * @version 1.0
 */
public class PEdorRSConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorRSConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private MMap map = new MMap();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LCContSchema tLCContSchema = new LCContSchema();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public PEdorRSConfirmBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate() {
		return this.mOperate;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 数据校验
		if (!checkData()) {
			return false;
		}

		// 数据操作
		if (!dealData()) {
			return false;
		}

		// 输出数据准备
		if (!prepareOutputData()) {

			return false;
		}

		this.setOperate("");

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mLPEdorItemSchema == null || mGlobalInput == null) {
			mErrors.addOneError(new CError("传入数据不完全！"));
			return false;
		}

		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() != 1) {
			mErrors.addOneError(new CError("查询批改项目信息失败！"));
			return false;
		}
		// 得到保全批改信息
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		LCContSet tLCContSet = new LCContSet();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		tLCContSet = tLCContDB.query();
		if (tLCContSet.size() < 1 || tLCContSet == null) {
			mErrors.addOneError(new CError("查询保单信息失败！"));
			return false;
		} else {
			tLCContSchema = tLCContSet.get(1);
		}
		tLCContSchema.setPrintCount("5");
		return true;
	}

	private boolean prepareOutputData() {
		map.put(mLPEdorItemSchema, "UPDATE");
		map.put(tLCContSchema, "UPDATE");
		mResult.add(map);
		return true;
	}

	public static void main(String[] args) {
		logger.debug("test start:");

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setPolNo("000000");
		tLPEdorItemSchema.setEdorAcceptNo("86000000003558");
		tLPEdorItemSchema.setContNo("230110000002635");
		tLPEdorItemSchema.setEdorNo("420000000000460");
		tLPEdorItemSchema.setEdorType("PL");
		tLPEdorItemSchema.setInsuredNo("000000");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tLPEdorItemSchema);
		try {
			Class tClass = Class
					.forName("com.sinosoft.lis.bq.PEdorRSConfirmBL");
			EdorConfirm tEdorConfirm = (EdorConfirm) tClass.newInstance();
			if (!tEdorConfirm.submitData(tVData, "")) {
				logger.debug("== fail to PEdorRSConfirmBL ==");
			} else {
				logger.debug("== after PEdorRSConfirmBL ==");
			}
			VData rVData = tEdorConfirm.getResult();
			MMap tMap = (MMap) rVData.getObjectByObjectName("MMap", 0);
			MMap map = new MMap();
			map.add(tMap);
			VData mInputData = new VData();
			mInputData.add(map);
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(mInputData, "")) {
				logger.debug("pusumit errors:"
						+ tPubSubmit.mErrors.getError(0).errorMessage);
			}

		} catch (Exception ex) {
			logger.debug("error:" + ex.toString());
		}

		logger.debug("test end");
	}

}
