/*
 * <p>ClassName: GrpPersonAgeBL </p>
 * <p>Description: GrpPersonAgeBL </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: testcompany </p>
 * @Database:
 * @CreateDate：2005-01-19 14:47:24
 */
package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPersonAgeDisItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCPersonAgeDisInfoSchema;
import com.sinosoft.lis.schema.LCPersonAgeDisItemSchema;
import com.sinosoft.lis.schema.LCPersonClassDisInfoSchema;
import com.sinosoft.lis.vschema.LCPersonAgeDisItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class GrpPersonAgeBL {
private static Logger logger = Logger.getLogger(GrpPersonAgeBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate;
	/** 业务处理相关变量 */
	private LCPersonAgeDisItemSchema mLCPersonAgeDisItemSchema = new LCPersonAgeDisItemSchema();
	private LCPersonAgeDisItemSet mLCPersonAgeDisItemSet = new LCPersonAgeDisItemSet();
	private LCPersonClassDisInfoSchema mLCPersonClassDisInfoSchema = new LCPersonClassDisInfoSchema();
	private LCPersonAgeDisInfoSchema mLCPersonAgeDisInfoSchema = new LCPersonAgeDisInfoSchema();
	private MMap map = new MMap();

	// private LDDiseaseSet mLDDiseaseSet=new LDDiseaseSet();
	public GrpPersonAgeBL() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		logger.debug("BL中传送" + cOperate);
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
			return false;
		logger.debug("getInputData结束");
		// 进行业务处理
		if (!dealData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpPersonAgeBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败OLDDiseaseBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData())
			return false;
		if (this.mOperate.equals("QUERY||MAIN")) {
			this.submitquery();
		} else {
			logger.debug("Start GrpPersonAgeBL Submit...");

			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(mInputData, mOperate)) {
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "GrpPersonAgeBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";

				this.mErrors.addOneError(tError);
				return false;
			}
			logger.debug(mOperate);

			logger.debug("End GrpPersonAgeBL Submit...");
			// 如果有需要处理的错误，则返回

		}
		mInputData = null;
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		String tPerAgeSerlNo = PubFun1.CreateMaxNo("PerAgeSerlNo", 20);

		int tOnWorkMCount = 0;
		int tOnWorkFCount = 0;
		int tOffWorkMCount = 0;
		int tOffWorkFCount = 0;
		int tMateMCount = 0;
		int tMateFCount = 0;
		int tYoungMCount = 0;
		int tYoungFCount = 0;
		int tOtherMCount = 0;
		int tOtherFCount = 0;
		int tMaleCount = 0;
		int tFemalCount = 0;

		for (int i = 1; i <= mLCPersonAgeDisItemSet.size(); i++) {
			logger.debug("dealData" + mLCPersonAgeDisItemSet.size());
			// mLCPersonAgeDisItemSet.get(i).setPrtNo(mLCGrpContSchema.getPrtNo());
			mLCPersonAgeDisItemSet.get(i).setSerialNo(tPerAgeSerlNo);
			mLCPersonAgeDisItemSet.get(i).setMakeDate(PubFun.getCurrentDate());
			mLCPersonAgeDisItemSet.get(i).setMakeTime(PubFun.getCurrentTime());
			mLCPersonAgeDisItemSet.get(i)
					.setModifyDate(PubFun.getCurrentDate());
			mLCPersonAgeDisItemSet.get(i)
					.setModifyTime(PubFun.getCurrentTime());
			tOnWorkMCount = tOnWorkMCount
					+ mLCPersonAgeDisItemSet.get(i).getOnWorkMCount();
			tOnWorkFCount = tOnWorkFCount
					+ mLCPersonAgeDisItemSet.get(i).getOnWorkFCount();
			tOffWorkMCount = tOffWorkMCount
					+ mLCPersonAgeDisItemSet.get(i).getOffWorkMCount();
			tOffWorkFCount = tOffWorkFCount
					+ mLCPersonAgeDisItemSet.get(i).getOffWorkFCount();
			tMateMCount = tMateMCount
					+ mLCPersonAgeDisItemSet.get(i).getMateMCount();
			tMateFCount = tMateFCount
					+ mLCPersonAgeDisItemSet.get(i).getMateFCount();
			tYoungMCount = tYoungMCount
					+ mLCPersonAgeDisItemSet.get(i).getYoungMCount();
			tYoungFCount = tYoungFCount
					+ mLCPersonAgeDisItemSet.get(i).getYoungFCount();
			tOtherMCount = tOtherMCount
					+ mLCPersonAgeDisItemSet.get(i).getOtherMCount();
			tOtherFCount = tOtherFCount
					+ mLCPersonAgeDisItemSet.get(i).getOtherFCount();
			tMaleCount = tOnWorkMCount + tOffWorkMCount + tMateMCount
					+ tOtherMCount + tYoungMCount;
			tFemalCount = tOnWorkFCount + tOffWorkFCount + tMateFCount
					+ tYoungFCount + tOtherFCount;
		}

		if (tOnWorkMCount == 0)
			tOnWorkMCount = 1;
		if (tOnWorkFCount == 0)
			tOnWorkFCount = 1;
		if (tOffWorkMCount == 0)
			tOffWorkMCount = 1;
		if (tOffWorkFCount == 0)
			tOffWorkFCount = 1;
		if (tMateMCount == 0)
			tMateMCount = 1;
		if (tMateFCount == 0)
			tMateFCount = 1;
		if (tYoungMCount == 0)
			tYoungMCount = 1;
		if (tYoungFCount == 0)
			tYoungFCount = 1;
		if (tOtherMCount == 0)
			tOtherMCount = 1;
		if (tOtherFCount == 0)
			tOtherFCount = 1;

		mLCPersonClassDisInfoSchema.setSerialNo(tPerAgeSerlNo);
		mLCPersonClassDisInfoSchema.setMakeDate(PubFun.getCurrentDate());
		mLCPersonClassDisInfoSchema.setMakeTime(PubFun.getCurrentTime());
		mLCPersonClassDisInfoSchema.setModifyDate(PubFun.getCurrentDate());
		mLCPersonClassDisInfoSchema.setModifyTime(PubFun.getCurrentTime());

		mLCPersonAgeDisInfoSchema.setSerialNo(tPerAgeSerlNo);
		mLCPersonAgeDisInfoSchema.setMakeDate(PubFun.getCurrentDate());
		mLCPersonAgeDisInfoSchema.setMakeTime(PubFun.getCurrentTime());
		mLCPersonAgeDisInfoSchema.setModifyDate(PubFun.getCurrentDate());
		mLCPersonAgeDisInfoSchema.setModifyTime(PubFun.getCurrentTime());

		map.put(mLCPersonAgeDisItemSet, "DELETE&INSERT");
		// map.put(mLCPersonClassDisInfoSchema,"INSERT");
		String strSQL = "insert into LCPersonClassDisInfo select '"
				+ tPerAgeSerlNo + "','"
				+ mLCPersonClassDisInfoSchema.getGrpContNo() + "','"
				+ mLCPersonClassDisInfoSchema.getPrtNo() + "','"
				+ mLCPersonClassDisInfoSchema.getClassCode() + "',"
				+ "sum(MaleCount),sum(FemalCount),sum(OnWorkMCount),"
				+ "sum(OnWorkFCount),sum(OffWorkMCount),"
				+ "sum(OffWorkFCount),sum(MateMCount),sum(MateFCount),"
				+ "sum(YoungMCount),sum(YoungFCount),sum(OtherMCount),"
				+ "sum(OtherFCount)" + ",'"
				+ mLCPersonClassDisInfoSchema.getMakeDate() + "','"
				+ mLCPersonClassDisInfoSchema.getMakeTime() + "','"
				+ mLCPersonClassDisInfoSchema.getModifyDate() + "','"
				+ mLCPersonClassDisInfoSchema.getModifyTime() + "'"
				+ " from LCPersonAgeDisItem where SerialNo='" + tPerAgeSerlNo
				+ "'";
		String strSQL2 = "insert into LCPersonAgeDisInfo select '"
				+ tPerAgeSerlNo + "','"
				+ mLCPersonAgeDisInfoSchema.getGrpContNo() + "','"
				+ mLCPersonAgeDisInfoSchema.getPrtNo() + "','"
				+ mLCPersonAgeDisInfoSchema.getClassCode() + "',"
				+ "sum(MaleCount*((StartAge+EndAge)/2))/" + tMaleCount + ","
				+ "sum(FemalCount*((StartAge+EndAge)/2))/" + tFemalCount + ","
				+ "sum(OnWorkMCount*((StartAge+EndAge)/2))/" + tOnWorkMCount
				+ "," + "sum(OnWorkFCount*((StartAge+EndAge)/2))/"
				+ tOnWorkFCount + ","
				+ "sum(OffWorkMCount*((StartAge+EndAge)/2))/" + tOffWorkMCount
				+ "," + "sum(OffWorkFCount*((StartAge+EndAge)/2))/"
				+ tOffWorkFCount + ","
				+ "sum(MateMCount*((StartAge+EndAge)/2))/" + tMateMCount + ","
				+ "sum(MateFCount*((StartAge+EndAge)/2))/" + tMateFCount + ","
				+ "sum(YoungMCount*((StartAge+EndAge)/2))/" + tYoungMCount
				+ "," + "sum(YoungFCount*((StartAge+EndAge)/2))/"
				+ tYoungFCount + ","
				+ "sum(OtherMCount*((StartAge+EndAge)/2))/" + tOtherMCount
				+ "," + "sum(OtherFCount*((StartAge+EndAge)/2))/"
				+ tOtherFCount + ",'" + mLCPersonAgeDisInfoSchema.getMakeDate()
				+ "','" + mLCPersonAgeDisInfoSchema.getMakeTime() + "','"
				+ mLCPersonAgeDisInfoSchema.getModifyDate() + "','"
				+ mLCPersonAgeDisInfoSchema.getModifyTime() + "'"
				+ " from LCPersonAgeDisItem where SerialNo='" + tPerAgeSerlNo
				+ "'";
		String strSQL3 = "update LCPersonAgeDisItem set MaleCount="
				+ "OnWorkMCount+OffWorkMCount+MateMCount"
				+ "+MateMCount+YoungMCount+OtherMCount,FemalCount="
				+ "OnWorkFCount+OffWorkFCount+MateFCount+YoungFCount+"
				+ "OtherFCount where SerialNo='" + tPerAgeSerlNo + "'";

		map.put(strSQL3, "INSERT");
		map.put(strSQL, "INSERT");
		map.put(strSQL2, "INSERT");

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean updateData() {
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean deleteData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {

		mLCPersonAgeDisItemSet.set((LCPersonAgeDisItemSet) cInputData
				.getObjectByObjectName("LCPersonAgeDisItemSet", 0));
		mLCPersonClassDisInfoSchema
				.setSchema((LCPersonClassDisInfoSchema) cInputData
						.getObjectByObjectName("LCPersonClassDisInfoSchema", 0));
		mLCPersonAgeDisInfoSchema
				.setSchema((LCPersonAgeDisInfoSchema) cInputData
						.getObjectByObjectName("LCPersonAgeDisInfoSchema", 0));

		// this.mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean submitquery() {
		this.mResult.clear();
		LCPersonAgeDisItemDB tLCPersonAgeDisItemDB = new LCPersonAgeDisItemDB();
		tLCPersonAgeDisItemDB.setSchema(this.mLCPersonAgeDisItemSchema);
		// 如果有需要处理的错误，则返回
		if (tLCPersonAgeDisItemDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPersonAgeDisItemDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpPersonBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		return true;
	}

	private boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			this.mInputData.add(this.mLCPersonAgeDisItemSchema);
			mInputData.add(map);
			mResult.clear();
			mResult.add(this.mLCPersonAgeDisItemSchema);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpPersonAgeBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}
}
