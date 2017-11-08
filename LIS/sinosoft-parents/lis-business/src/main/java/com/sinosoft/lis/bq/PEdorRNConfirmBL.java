package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全－重新出单
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author liurx
 * @version 1.0
 * @creatdate 2005-07-21
 */
public class PEdorRNConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorRNConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LCContSchema mLCContSchema = new LCContSchema();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public PEdorRNConfirmBL() {
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
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		logger.debug("PL dealing data......... ");

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError(new CError("相关的保单表中没有记录！"));
			return false;
		}
		mLCContSchema = tLCContDB.getSchema();
		mLCContSchema.setPrintCount(10);// 与新契约区分 modify by lizhuo at 2005-10-31
		mLCContSchema.setModifyDate(PubFun.getCurrentDate());
		mLCContSchema.setModifyTime(PubFun.getCurrentTime());

		return true;
	}

	private boolean prepareOutputData() {
		mMap.put(mLCContSchema, "UPDATE");

		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	public static void main(String[] args) {
		logger.debug("test start:");

		GlobalInput tG = new GlobalInput();
		tG.Operator = "bq";
		tG.ManageCom = "86110000";

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setPolNo("000000");
		tLPEdorItemSchema.setEdorAcceptNo("86000000002639");
		tLPEdorItemSchema.setContNo("230110000002320");
		tLPEdorItemSchema.setEdorNo("420000000000376");
		tLPEdorItemSchema.setEdorType("RN");
		tLPEdorItemSchema.setInsuredNo("000000");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tLPEdorItemSchema);
		try {
			Class tClass = Class
					.forName("com.sinosoft.lis.bq.PEdorRNConfirmBL");
			EdorConfirm tEdorConfirm = (EdorConfirm) tClass.newInstance();
			if (!tEdorConfirm.submitData(tVData, "")) {
				logger.debug("== fail to PEdorRNConfirmBL ==");

			} else {
				logger.debug("== after PEdorRNConfirmBL ==");
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
