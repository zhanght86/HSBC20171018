

/**
 * Copyright (c) 2006 sinosoft  Co. Ltd.
 * All right reserved.
 */

/*
 * <p>ClassName: OrderDescUI </p>
 * <p>Description: OrderDescUI类文件 </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: sinosoft </p>
 * @Database: Zhang Bin
 * @CreateDate：2006-07-30
 */
package com.sinosoft.lis.reinsure;

import com.sinosoft.lis.db.RIComInfoDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIComInfoSchema;
import com.sinosoft.lis.schema.RIComLinkManInfoSchema;
import com.sinosoft.lis.vschema.RIComLinkManInfoSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class ReComManageBL {

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 前台传入的公共变量 */
	private GlobalInput globalInput = new GlobalInput();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 数据操作字符串 */
	private String strOperate;

	private RIComInfoSchema mRIComInfoSchema = new RIComInfoSchema();
	private RIComLinkManInfoSet mRIComLinkManInfoSet = new RIComLinkManInfoSet();
	private String currentDate = PubFun.getCurrentDate();
	private String currentTime = PubFun.getCurrentTime();
	private MMap mMap = new MMap();

	private PubSubmit tPubSubmit = new PubSubmit();

	// 业务处理相关变量
	/** 全局数据 */

	public ReComManageBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 提交数据处理方法
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (cOperate == null || "".equals(cOperate)) {
			buildError("verifyOperate", "不支持的操作字符串");
			return false;
		}
		this.strOperate = cOperate;
		if (!getInputData(cInputData)) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		if (!prepareOutputData()) {
			return false;
		}
		if (!tPubSubmit.submitData(this.mInputData, "")) {
			if (tPubSubmit.mErrors.needDealError()) {
				buildError(strOperate, "保存数据时出现错误!");
				return false;
			}
		}
		mMap = null;
		tPubSubmit = null;
		return true;
	}

	public static void main(String[] args) {
		GlobalInput globalInput = new GlobalInput();
		globalInput.ComCode = "86";
		globalInput.Operator = "001";

		// prepare main plan
		// 准备传输数据 VData
		VData vData = new VData();
		ReComManageBL tReComManageBL = new ReComManageBL();

		RIComInfoSchema mRIComInfoSchema = new RIComInfoSchema();
		RIComLinkManInfoSet mRIComLinkManInfoSet = new RIComLinkManInfoSet();
		RIComLinkManInfoSchema mRIComLinkManInfoSchema = new RIComLinkManInfoSchema();
		RIComLinkManInfoSchema tRIComLinkManInfoSchema = new RIComLinkManInfoSchema();
		RIComLinkManInfoSchema sRIComLinkManInfoSchema = new RIComLinkManInfoSchema();
		RIComLinkManInfoSchema eRIComLinkManInfoSchema = new RIComLinkManInfoSchema();

		// 手动输入再保公司信息

		mRIComInfoSchema.setComPanyNo("R000000148");
		mRIComInfoSchema.setComPanyName("zhongyi");
		mRIComInfoSchema.setOperator("li");
		mRIComInfoSchema.setMakeDate("2007-06-15");
		mRIComInfoSchema.setMakeTime("11:44:00");

		// 手动输入再保公司联系人信息1
		mRIComLinkManInfoSchema.setReComCode("R000000148");
		mRIComLinkManInfoSchema.setRelaCode("L000004054");
		mRIComLinkManInfoSchema.setRelaName("l5");
		mRIComLinkManInfoSchema.setRelaTel("12351565");
		mRIComLinkManInfoSchema.setEmail("li@yahoo.com");
		mRIComLinkManInfoSchema.setFaxNo("0102315");

		mRIComLinkManInfoSet.add(mRIComLinkManInfoSchema);

		// 手动输入再保公司联系人信息2
		tRIComLinkManInfoSchema.setReComCode("R000000148");
		tRIComLinkManInfoSchema.setRelaCode("L000004055");
		tRIComLinkManInfoSchema.setRelaName("lp");
		tRIComLinkManInfoSchema.setRelaTel("145351565");
		tRIComLinkManInfoSchema.setEmail("l23i@yahoo.com");
		tRIComLinkManInfoSchema.setFaxNo("0102255");

		mRIComLinkManInfoSet.add(tRIComLinkManInfoSchema);

		// 手动输入再保公司联系人信32
		sRIComLinkManInfoSchema.setReComCode("R000000148");
		sRIComLinkManInfoSchema.setRelaCode("L000004056");
		sRIComLinkManInfoSchema.setRelaName("l45");
		sRIComLinkManInfoSchema.setRelaTel("145351565");
		sRIComLinkManInfoSchema.setEmail("l23i@yahoo.com");
		sRIComLinkManInfoSchema.setFaxNo("0102255");

		mRIComLinkManInfoSet.add(sRIComLinkManInfoSchema);

		// 手动输入再保公司联系人信4

		eRIComLinkManInfoSchema.setRelaName("52");
		eRIComLinkManInfoSchema.setRelaTel("234235");
		eRIComLinkManInfoSchema.setEmail("rt@yahoo.com");
		eRIComLinkManInfoSchema.setFaxNo("56745");

		mRIComLinkManInfoSet.add(eRIComLinkManInfoSchema);

		// 将输入信息全部封装到vdata中
		vData.addElement(globalInput);
		vData.addElement(mRIComInfoSchema);
		vData.addElement(mRIComLinkManInfoSet);

		try {
			tReComManageBL.submitData(vData, "UPDATE");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("完毕");

	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			this.mInputData.add(mMap);

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDComBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (this.strOperate.equals("INSERT")) {
			if (!insertData()) {
				buildError("insertData", "增加再保公司信息时出错！");
				return false;
			}
		}
		if (this.strOperate.equals("UPDATE")) {
			if (!updateData()) {
				buildError("updateData", "修改再保公司信息时出错！");
				return false;
			}
		}
		if (this.strOperate.equals("DELETE")) {
			if (!deleteData()) {
				buildError("deletetData", "删除再保公司信息时出错！");
				return false;
			}
		}
		return true;
	}

	/**
	 * deleteData
	 * 
	 * @return boolean
	 */
	private boolean deleteData() {
		RIComInfoDB tRIComInfoDB = new RIComInfoDB();
		tRIComInfoDB.setComPanyNo(mRIComInfoSchema.getComPanyNo());
		if (!tRIComInfoDB.getInfo()) {
			buildError("deleteData", "该再保公司不存在!");
			return false;
		}
		String strSQL = "select 1 from dual where exists (select * from RIIncomeCompany where IncomeCompanyNo='"
				+ mRIComInfoSchema.getComPanyNo() + "')";
		ExeSQL tExeSQL = new ExeSQL();
		String str = tExeSQL.getOneValue(strSQL);
		if (str != null && !str.equals("")) {
			buildError("deleteData", "该再保公司正在被引用,不能删除!");
			return false;
		}

		RIComInfoSchema tRIComInfoSchema = tRIComInfoDB.getSchema();
		mMap.put(tRIComInfoSchema, "DELETE");
		strSQL = "delete from RIComLinkManInfo where ReComCode= '"
				+ mRIComInfoSchema.getComPanyNo() + "'";
		mMap.put(strSQL, "DELETE");
		return true;
	}

	/**
	 * updateData
	 * 
	 * @return boolean
	 */

	private boolean updateData() {
		RIComInfoDB tRIComInfoDB = new RIComInfoDB();
		tRIComInfoDB.setComPanyNo(mRIComInfoSchema.getComPanyNo());

		System.out.println(mRIComInfoSchema.getComPanyNo());

		System.out.println(tRIComInfoDB.getComPanyNo());
		if (!tRIComInfoDB.getInfo()) {
			buildError("updateData", "该再保公司不存在!");
			return false;
		}
		RIComInfoSchema tRIComInfoSchema = tRIComInfoDB.getSchema();
		tRIComInfoSchema.setComPanyNo(mRIComInfoSchema.getComPanyNo());
		tRIComInfoSchema.setComPanyName(mRIComInfoSchema.getComPanyName());
		tRIComInfoSchema.setGrpZipCode(mRIComInfoSchema.getGrpZipCode());
		tRIComInfoSchema.setGrpAddress(mRIComInfoSchema.getGrpAddress());
		tRIComInfoSchema.setFax(mRIComInfoSchema.getFax());
		tRIComInfoSchema.setRemark(mRIComInfoSchema.getRemark());
		tRIComInfoSchema.setInformation(mRIComInfoSchema.getInformation());

		for (int i = 1; i <= mRIComLinkManInfoSet.size(); i++) {
			System.out.println("mRIComLinkManInfoSet.get(i).getRelaCode() "
					+ mRIComLinkManInfoSet.get(i).getRelaCode());
			if (mRIComLinkManInfoSet.get(i).getRelaCode() == null
					|| mRIComLinkManInfoSet.get(i).getRelaCode().equals("")) {
				mRIComLinkManInfoSet.get(i).setRelaCode(
						"L" + PubFun1.CreateMaxNo("RELATION", 9));
			}
			mRIComLinkManInfoSet.get(i).setReComCode(
					mRIComInfoSchema.getComPanyNo());
			mRIComLinkManInfoSet.get(i).setOperator(globalInput.Operator);
			mRIComLinkManInfoSet.get(i).setMakeDate(currentDate);
			mRIComLinkManInfoSet.get(i).setMakeTime(currentTime);
		}

		String strSQL = "delete from RIComLinkManInfo where ReComCode='"
				+ mRIComInfoSchema.getComPanyNo() + "'";
		mMap.put(strSQL, "DELETE");
		if (mRIComLinkManInfoSet.size() > 0) {
			mMap.put(mRIComLinkManInfoSet, "INSERT");
		}
		mMap.put(tRIComInfoSchema, "UPDATE");
		return true;
	}

	/**
	 * insertData
	 * 
	 * @return boolean
	 */
	private boolean insertData() {
		mRIComInfoSchema.setOperator(globalInput.Operator);
		mRIComInfoSchema.setMakeDate(currentDate);
		mRIComInfoSchema.setMakeTime(currentTime);

		RIComLinkManInfoSchema tRIComLinkManInfoSchema;
		int length = mRIComLinkManInfoSet.size();
		for (int i = 1; i <= length; i++) {

			tRIComLinkManInfoSchema = mRIComLinkManInfoSet.get(i);
			tRIComLinkManInfoSchema.setReComCode(mRIComInfoSchema
					.getComPanyNo());
			System.out.println(" 公司编码： " + mRIComInfoSchema.getComPanyNo());
			tRIComLinkManInfoSchema.setRelaCode("L"
					+ PubFun1.CreateMaxNo("RELATION", 9));
			System.out.println(" 联系人编码： "
					+ tRIComLinkManInfoSchema.getRelaCode());
			tRIComLinkManInfoSchema.setOperator(globalInput.Operator);
			tRIComLinkManInfoSchema.setMakeDate(currentDate);
			tRIComLinkManInfoSchema.setMakeTime(currentTime);

		}
		mMap.put(mRIComInfoSchema, "INSERT");
		mMap.put(mRIComLinkManInfoSet, "INSERT");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		globalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		this.mRIComInfoSchema.setSchema((RIComInfoSchema) cInputData
				.getObjectByObjectName("RIComInfoSchema", 0));
		this.mRIComLinkManInfoSet.set((RIComLinkManInfoSet) cInputData
				.getObjectByObjectName("RIComLinkManInfoSet", 0));
		return true;
	}

	public VData getResult() {
		TransferData t = new TransferData();
		t.setNameAndValue("CompanyNo", mRIComInfoSchema.getComPanyNo());
		mResult.addElement(t);
		return mResult;
	}

	/*
	 * add by kevin, 2002-10-14
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "ReComManageBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private void jbInit() throws Exception {
	}
}
