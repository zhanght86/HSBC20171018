package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.util.Date;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vschema.LCCustomerImpartDetailSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class GrpCardInsuredBL {
private static Logger logger = Logger.getLogger(GrpCardInsuredBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private VData rResult = new VData(); // add
	private SSRS tSSRS = new SSRS(); // 备用
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private MMap map = new MMap();

	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();
	private FDate fDate = new FDate();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();
	private LCContSchema mLCContSchema = new LCContSchema();

	/** 更新后的变量 */
	LCContSchema mLCNewContSchema = new LCContSchema();
	LCInsuredSchema mNewLCInsuredSchema = new LCInsuredSchema();
	LDPersonSchema mNewLDPersonSchema = new LDPersonSchema();
	LCAppntSchema mNewLCAppntSchema = new LCAppntSchema();
	LCPolSet newLCPolSet = new LCPolSet();
	LCDutySet newLCDutySet = new LCDutySet();
	LCGetSet newLCGetSet = new LCGetSet();
	LCPremSet newLCPremSet = new LCPremSet();

	/** 得到前台传输的重要信息 */
	private String ContNo;// 激活卡号
	private String CValiDate;// 生效日期

	// private String ProposalNo;//投保单号

	public GrpCardInsuredBL() {
	}

	/**
	 * 处理数据，不提交后台执行
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean preparesubmitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 进行业务处理
		if (!dealData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpCardInsuredBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败GrpCardInsuredBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		mInputData = null;
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {

		try {
			mGlobalInput.setSchema((GlobalInput) cInputData
					.getObjectByObjectName("GlobalInput", 0));

			this.mLCContSchema.setSchema((LCContSchema) cInputData
					.getObjectByObjectName("LCContSchema", 0));

			this.mLCInsuredSchema.setSchema((LCInsuredSchema) cInputData
					.getObjectByObjectName("LCInsuredSchema", 0));

			this.mTransferData = (TransferData) cInputData
					.getObjectByObjectName("TransferData", 0);

			if (mTransferData == null) {
				CError tError = new CError();
				tError.moduleName = "ContInsuredBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "未得到传送数据!";
				this.mErrors.addOneError(tError);
				return false;

			}

		}

		catch (Exception ex) {

			CError tError = new CError();
			tError.moduleName = "ContInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			return false;

		}
		return true;
	}

	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
			rResult.clear();
			rResult.add(map);
			mResult.clear();
			LCInsuredSchema tempLCInsuredSchema = new LCInsuredSchema();
			tempLCInsuredSchema.setSchema(mNewLCInsuredSchema);
			mResult.add(tempLCInsuredSchema);
			mResult.add(this.mLCNewContSchema);
			mResult.add(map);

			// mResult.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
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

	public VData getCardResult() {
		return this.rResult;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {

		ContNo = mLCContSchema.getContNo(); // 得到卡单号
		CValiDate = mLCContSchema.getCValiDate(); // 得到生效日期
		LCContDB mLCContDB = new LCContDB();
		mLCContDB.setContNo(ContNo);
		mLCContDB.getInfo();
		mLCNewContSchema.setSchema(mLCContDB);// 取出数据库中的原始记录
		// 更新LCCont表信息
		if (mLCInsuredSchema.getName() != null
				&& !mLCInsuredSchema.getName().equals(""))
			mLCNewContSchema.setInsuredName(mLCInsuredSchema.getName());// 姓名
		if (mLCInsuredSchema.getSex() != null
				&& !mLCInsuredSchema.getSex().equals(""))
			mLCNewContSchema.setInsuredSex(mLCInsuredSchema.getSex());// 性别
		if (mLCInsuredSchema.getIDType() != null
				&& !mLCInsuredSchema.getIDType().equals(""))
			mLCNewContSchema.setInsuredIDType(mLCInsuredSchema.getIDType());// 证件类型
		if (mLCInsuredSchema.getIDNo() != null
				&& !mLCInsuredSchema.getIDNo().equals(""))
			mLCNewContSchema.setInsuredIDNo(mLCInsuredSchema.getIDNo());// 证件号码
		if (mLCInsuredSchema.getBirthday() != null
				&& !mLCInsuredSchema.getBirthday().equals(""))
			mLCNewContSchema.setInsuredBirthday(mLCInsuredSchema.getBirthday());// 生日
		mLCNewContSchema.setPolType("0");// 无名单标志改为有名单
		mLCNewContSchema.setCValiDate(CValiDate);// 设定新生效日
		// 更新LCCont表信息完毕
		LCInsuredDB mOLDLCInsuredDB = new LCInsuredDB();
		String InsuredNo = mLCNewContSchema.getInsuredNo();// 获得被保人客户号
		mOLDLCInsuredDB.setInsuredNo(InsuredNo);// LCInsured表是InsuredNo、ContNo联合主键
		mOLDLCInsuredDB.setContNo(ContNo);// LCInsured表是InsuredNo、ContNo联合主键
		mOLDLCInsuredDB.getInfo();
		mNewLCInsuredSchema.setSchema(mOLDLCInsuredDB);
		// 更新LCInsured表信息
		if (mLCInsuredSchema.getName() != null
				&& !mLCInsuredSchema.getName().equals(""))
			mNewLCInsuredSchema.setName(mLCInsuredSchema.getName());// 姓名
		if (mLCInsuredSchema.getSex() != null
				&& !mLCInsuredSchema.getSex().equals(""))
			mNewLCInsuredSchema.setSex(mLCInsuredSchema.getSex());// 性别
		if (mLCInsuredSchema.getIDType() != null
				&& !mLCInsuredSchema.getIDType().equals(""))
			mNewLCInsuredSchema.setIDType(mLCInsuredSchema.getIDType());// 证件类型
		if (mLCInsuredSchema.getIDNo() != null
				&& !mLCInsuredSchema.getIDNo().equals(""))
			mNewLCInsuredSchema.setIDNo(mLCInsuredSchema.getIDNo());// 证件号码
		if (mLCInsuredSchema.getBirthday() != null
				&& !mLCInsuredSchema.getBirthday().equals(""))
			mNewLCInsuredSchema.setBirthday(mLCInsuredSchema.getBirthday());// 生日
		// 更新LCInsured表信息完毕
		LDPersonDB mLDPerson = new LDPersonDB();
		// mLDPerson.setCustomerNo(InsuredNo);//取出原客户信息
		// mLDPerson.getInfo();

		// mNewLDPersonSchema.setSchema(mLDPerson);
		// 更新LDPerson表信息
		// String opFlag="";
		// if(mNewLDPersonSchema.getOperator()==null||mNewLDPersonSchema.getOperator().equals("")){
		// opFlag="INSERT";
		mNewLDPersonSchema.setCustomerNo(mNewLCInsuredSchema.getInsuredNo());
		// }else{
		// opFlag="UPDATE";
		// }
		mNewLDPersonSchema.setOperator(mGlobalInput.Operator);
		// mNewLDPersonSchema.setOperator("001");
		mNewLDPersonSchema.setMakeDate(PubFun.getCurrentDate());
		mNewLDPersonSchema.setMakeTime(PubFun.getCurrentTime());
		mNewLDPersonSchema.setModifyDate(PubFun.getCurrentDate());
		mNewLDPersonSchema.setModifyTime(PubFun.getCurrentTime());

		if (mLCInsuredSchema.getName() != null
				&& !mLCInsuredSchema.getName().equals("")) {
			mNewLDPersonSchema.setName(mLCInsuredSchema.getName());// 姓名
		} else {
			mNewLDPersonSchema.setName("");// 姓名
		}
		if (mLCInsuredSchema.getSex() != null
				&& !mLCInsuredSchema.getSex().equals("")) {
			mNewLDPersonSchema.setSex(mLCInsuredSchema.getSex());// 性别
		} else {
			if (mLCInsuredSchema.getIDType().equals("0"))
				mNewLDPersonSchema.setSex(PubFun.getSexFromId(mLCInsuredSchema
						.getIDNo()));
			else
				mNewLDPersonSchema.setSex("");
		}
		if (mLCInsuredSchema.getIDType() != null
				&& !mLCInsuredSchema.getIDType().equals(""))
			mNewLDPersonSchema.setIDType(mLCInsuredSchema.getIDType());// 证件类型
		if (mLCInsuredSchema.getIDNo() != null
				&& !mLCInsuredSchema.getIDNo().equals(""))
			mNewLDPersonSchema.setIDNo(mLCInsuredSchema.getIDNo());// 证件号码
		if (mLCInsuredSchema.getBirthday() != null
				&& !mLCInsuredSchema.getBirthday().equals("")) {
			mNewLDPersonSchema.setBirthday(mLCInsuredSchema.getBirthday());// 生日
		} else {
			if (mLCInsuredSchema.getIDType().equals("0"))
				mNewLDPersonSchema.setBirthday(PubFun
						.getBirthdayFromId(mLCInsuredSchema.getIDNo()));// 生日
			else
				mNewLDPersonSchema.setBirthday("");
		}
		if (mNewLDPersonSchema.getBirthday() == null
				|| mNewLDPersonSchema.getBirthday().equals("null")) {
			mNewLDPersonSchema.setBirthday("");
		}
		if (mNewLDPersonSchema.getSex() == null
				|| mNewLDPersonSchema.getSex().equals("null")) {
			mNewLDPersonSchema.setSex("");
		}
		// LDPersonDB mLDPerson1 = new LDPersonDB();
		// mLDPerson1.setSchema(mNewLDPersonSchema);
		// mLDPerson1.insert();
		// 更新LDPerson表信息完毕
		LCAppntDB mLCAppntDB = new LCAppntDB();
		mLCAppntDB.setContNo(ContNo);
		mLCAppntDB.getInfo();
		mNewLCAppntSchema.setSchema(mLCAppntDB);
		// 更新LCAppntDB表信息
		if (mLCInsuredSchema.getName() != null
				&& !mLCInsuredSchema.getName().equals(""))
			mNewLCAppntSchema.setAppntName(mLCInsuredSchema.getName());
		if (mLCInsuredSchema.getSex() != null
				&& !mLCInsuredSchema.getSex().equals(""))
			mNewLCAppntSchema.setAppntSex(mLCInsuredSchema.getSex());
		if (mLCInsuredSchema.getIDType() != null
				&& !mLCInsuredSchema.getIDType().equals(""))
			mNewLCAppntSchema.setIDType(mLCInsuredSchema.getIDType());
		if (mLCInsuredSchema.getIDNo() != null
				&& !mLCInsuredSchema.getIDNo().equals(""))
			mNewLCAppntSchema.setIDNo(mLCInsuredSchema.getIDNo());
		if (mLCInsuredSchema.getBirthday() != null
				&& !mLCInsuredSchema.getBirthday().equals(""))
			mNewLCAppntSchema.setAppntBirthday(mLCInsuredSchema.getBirthday());
		// 更新LCAppntDB表信息完毕
		LCPolDB mLCPolDB = new LCPolDB();
		mLCPolDB.setContNo(ContNo);// 保单号查询对应
		mLCPolDB.setPolTypeFlag("1");// PolTypeFlag为1为无名单
		mLCPolDB.setGrpContNo(mLCContSchema.getGrpContNo());
		newLCPolSet = mLCPolDB.query();
		// 更新LCPol,lcduty,lcget,lcprem表信息
		if (newLCPolSet != null) {
			if (newLCPolSet.size() > 0) {
				for (int i = 1; i <= newLCPolSet.size(); i++) {
					newLCPolSet.get(i).setInsuredName(
							mLCInsuredSchema.getName());
					newLCPolSet.get(i).setInsuredSex(mLCInsuredSchema.getSex());
					newLCPolSet.get(i).setAppntName(mLCInsuredSchema.getName());
					newLCPolSet.get(i).setCValiDate(
							mLCContSchema.getCValiDate());
					newLCPolSet.get(i).setPolTypeFlag("0");// 改无名单标志为有名单
					// 查找对应的lcduty的记录
					LCDutyDB mLCDutyDB = new LCDutyDB();
					mLCDutyDB.setPolNo(newLCPolSet.get(i).getPolNo());
					mLCDutyDB.setContNo(ContNo);

					LCDutySet tmpLCDutySet = new LCDutySet();// 临时存储LCDutySet
					tmpLCDutySet = mLCDutyDB.query();
					// //////////////////////////////
					CalBL tCalBL;
					LCPolBL tLCPolBL = new LCPolBL();
					LCDutyBLSet tLCDutyBLSet = new LCDutyBLSet();
					LCGetBLSet tLCGetBLSet = new LCGetBLSet();
					tLCPolBL.setSchema(newLCPolSet.get(i));//
					// //////////////////////////////
					LCGetSet tmpLCGetSet = new LCGetSet();// 临时存储LCGetSet
					LCPremSet tmpLCPremSet = new LCPremSet();// 临时存储LCPremSet
					LCGetSet sumLCGetSet = new LCGetSet();// 临时存储LCGetSet
					LCPremSet sumLCPremSet = new LCPremSet();// 临时存储LCPremSet

					// //////////////////////////////
					if (tmpLCDutySet != null && tmpLCDutySet.size() > 0) {

						for (int j = 1; j <= tmpLCDutySet.size(); j++) {
							// ////////////////////////////////////////
							tmpLCDutySet.get(j).setCValiDate(
									mLCContSchema.getCValiDate());
							// 查找对应的lcget的记录
							LCGetDB mLCGetDB = new LCGetDB();
							mLCGetDB.setPolNo(tmpLCDutySet.get(j).getPolNo());
							mLCGetDB.setContNo(ContNo);
							mLCGetDB.setDutyCode(tmpLCDutySet.get(j)
									.getDutyCode());
							tmpLCGetSet = mLCGetDB.query();
							// 查找对应的lcprem的记录
							LCPremDB mLCPremDB = new LCPremDB();
							mLCPremDB.setPolNo(tmpLCDutySet.get(j).getPolNo());
							mLCPremDB.setContNo(ContNo);
							mLCPremDB.setDutyCode(tmpLCDutySet.get(j)
									.getDutyCode());
							tmpLCPremSet = mLCPremDB.query();
							// ///////////////////////////////////////
							sumLCGetSet.add(tmpLCGetSet);
							sumLCPremSet.add(tmpLCPremSet);
							// ///////////////////////////////////////
						}
						tLCDutyBLSet.set(tmpLCDutySet);
						// //////////////根据新的生效日重新计算保单相关时间信息//////////////////////////
						if (sumLCGetSet != null && sumLCGetSet.size() > 0) {
							tLCGetBLSet.set(sumLCGetSet);//
							tCalBL = new CalBL(tLCPolBL, tLCDutyBLSet,
									tLCGetBLSet, "");
						} else {
							tCalBL = new CalBL(tLCPolBL, tLCDutyBLSet, "");
						}
						if (tCalBL.calPol() == false) {
							CError.buildErr(this, "险种保单重新计算时失败:"
									+ tCalBL.mErrors.getFirstError());

							return false;
						}
						// ///////////////////////////////////////////////////////////////////////////
						LCPremSet sumLCPremSet1 = new LCPremSet();// 临时存储LCPremSet

						// //////////////////结束计算///////////////////////////////////////////////////
						// 取出计算的结果
						newLCPolSet.get(i).setSchema(
								tCalBL.getLCPol().getSchema());
						sumLCPremSet1 = (LCPremSet) tCalBL.getLCPrem();
						if (sumLCPremSet1 != null && sumLCPremSet1.size() > 0) {
							for (int n = 1; n <= sumLCPremSet1.size(); n++) {
								// /////////////////////////////////////////
								sumLCPremSet.get(n).setPayStartDate(
										sumLCPremSet1.get(n).getPayStartDate());
								sumLCPremSet.get(n).setPayEndDate(
										sumLCPremSet1.get(n).getPayEndDate());
								// /////////////////////////////////////////
								LCPremSchema ttLCPremSchema = sumLCPremSet1
										.get(n);
								// 计算交至日期
								Date baseDate = fDate.getDate(ttLCPremSchema
										.getPayStartDate());
								Date paytoDate = null;

								if (ttLCPremSchema.getPayIntv() == 0) { // 趸交按照交费终止日期计算
									paytoDate = fDate.getDate(ttLCPremSchema
											.getPayEndDate());
								} else {
									int interval = ttLCPremSchema.getPayIntv();

									if (interval == -1) {
										interval = 0; // 不定期缴费按照交费日期计算
									}

									String unit = "M";
									paytoDate = PubFun.calDate(baseDate,
											interval, unit, null);

								}
								// /////////////////////////////////////
								sumLCPremSet.get(n).setPaytoDate(paytoDate);
								sumLCPremSet.get(n).setModifyDate(
										PubFun.getCurrentDate());
								sumLCPremSet.get(n).setModifyTime(
										PubFun.getCurrentTime());
								// /////////////////////////////////////
							}
						}

						sumLCGetSet = (LCGetSet) tCalBL.getLCGet();
						tmpLCDutySet = (LCDutySet) tCalBL.getLCDuty();
						// ///////////////////////////////////
						newLCDutySet.add(tmpLCDutySet);
						newLCPremSet.add(sumLCPremSet);
						newLCGetSet.add(sumLCGetSet);
					}
					// /////////////////////////////////////////////////////////////////////////////////
				}
			}
		}
		// 更新LCPol表信息准备完毕
		map.put(mLCNewContSchema, "UPDATE");
		map.put(mNewLCInsuredSchema, "UPDATE");

		logger.debug("***********************:::::"
				+ mNewLDPersonSchema.getCustomerNo());
		logger.debug("***********************:::::"
				+ mNewLDPersonSchema.getOperator());
		logger.debug("***********************:::::"
				+ mNewLDPersonSchema.getMakeDate());
		logger.debug("***********************:::::"
				+ mNewLDPersonSchema.getMakeTime());
		logger.debug("***********************:::::"
				+ mNewLDPersonSchema.getModifyDate());
		logger.debug("***********************:::::"
				+ mNewLDPersonSchema.getModifyTime());
		logger.debug("***********************:::::"
				+ mNewLDPersonSchema.getName());
		logger.debug("***********************:::::"
				+ mNewLDPersonSchema.getSex());
		logger.debug("***********************:::::"
				+ mNewLDPersonSchema.getBirthday() + "]");
		logger.debug("***********************:::::"
				+ mNewLDPersonSchema.getIDType());
		logger.debug("***********************:::::"
				+ mNewLDPersonSchema.getIDNo());

		map.put(mNewLCAppntSchema, "UPDATE");
		map.put(newLCPolSet, "UPDATE");
		map.put(newLCDutySet, "UPDATE");
		map.put(newLCGetSet, "UPDATE");
		map.put(newLCPremSet, "UPDATE");
		map.put(mNewLDPersonSchema, "INSERT");
		return true;
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
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		if (!getInputData(cInputData)) {
			return false;
		}
		if (!dealData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpCardInsuredBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败GrpCardInsuredBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start GrpCardInsuredBL Submit...");

		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "GrpCardInsuredBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);
			return false;
		}

		mInputData = null;
		return true;
	}

	public static void main(String[] args) {
		GlobalInput tGI = new GlobalInput();
		VData tVData = new VData();
		GlobalInput tg = new GlobalInput();
		tGI.ManageCom = "8621";
		tGI.Operator = "001";
		String strContno = "20070427000040";
		// String strContno="20070424000031";
		String strGrpContno = "00000000000000000000";

		LCContSchema tLCContSchema = new LCContSchema();
		LDPersonSchema tLDPersonSchema = new LDPersonSchema();

		LCInsuredDB tOLDLCInsuredDB = new LCInsuredDB();
		LCAddressSchema tLCAddressSchema = new LCAddressSchema();

		LCInsuredSchema tmLCInsuredSchema = new LCInsuredSchema();

		LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
		LCCustomerImpartDetailSet tLCCustomerImpartDetailSet = new LCCustomerImpartDetailSet();
		TransferData tTransferData = new TransferData();
		// //////////////////////////////////////////////////
		tLCContSchema.setGrpContNo(strGrpContno);
		tLCContSchema.setContNo(strContno);
		tLCContSchema.setPrtNo(strContno);
		tLCContSchema.setManageCom("8621");
		tLCContSchema.setCValiDate("2007-4-28");
		// //////////////////////////////////////////////////

		// //////////////////////////////////////////////////
		tOLDLCInsuredDB.setContNo(strContno);
		String ss = "";
		ExeSQL ttExeSQL = new ExeSQL();
		String s1, s2, s3, s4, s5;
		s1 = "yxy";
		s2 = null;
		s3 = "10108197804050019";
		s4 = "0";
		s5 = null;
		tOLDLCInsuredDB.getInfo();
		// //////////////////////////////////////////////////
		tmLCInsuredSchema.setInsuredNo(tOLDLCInsuredDB.getInsuredNo());
		tmLCInsuredSchema.setRelationToMainInsured("00");
		tmLCInsuredSchema.setRelationToAppnt("");
		tmLCInsuredSchema.setContNo(strContno);
		tmLCInsuredSchema.setGrpContNo(strGrpContno);
		tmLCInsuredSchema.setContPlanCode("");
		tmLCInsuredSchema.setExecuteCom("");
		tmLCInsuredSchema.setJoinCompanyDate("");
		tmLCInsuredSchema.setInsuredPeoples("");
		tmLCInsuredSchema.setSalary("");
		tmLCInsuredSchema.setBankCode("");
		tmLCInsuredSchema.setBankAccNo("");
		tmLCInsuredSchema.setAccName("");
		tmLCInsuredSchema.setLicenseType("");

		tmLCInsuredSchema.setBirthday(s5);
		tmLCInsuredSchema.setIDNo(s3);
		tmLCInsuredSchema.setIDType(s4);
		tmLCInsuredSchema.setName(s1);
		tmLCInsuredSchema.setSex(s2);
		// //////////////////////////////////////////////////
		tLDPersonSchema.setCustomerNo(ss);
		tLDPersonSchema.setName(s1);
		tLDPersonSchema.setSex(s2);
		tLDPersonSchema.setBirthday(s5);
		tLDPersonSchema.setIDType(s4);
		tLDPersonSchema.setIDNo(s3);
		tLDPersonSchema.setPassword("");
		tLDPersonSchema.setNativePlace("");
		tLDPersonSchema.setNationality("");
		tLDPersonSchema.setRgtAddress("");
		tLDPersonSchema.setMarriage("");
		tLDPersonSchema.setMarriageDate("");
		tLDPersonSchema.setHealth("");
		tLDPersonSchema.setStature("");
		tLDPersonSchema.setAvoirdupois("");
		tLDPersonSchema.setDegree("");
		tLDPersonSchema.setCreditGrade("");
		tLDPersonSchema.setOthIDType("");
		tLDPersonSchema.setOthIDNo("");
		tLDPersonSchema.setICNo("");
		tLDPersonSchema.setGrpNo("");
		tLDPersonSchema.setJoinCompanyDate("");
		tLDPersonSchema.setStartWorkDate("");
		tLDPersonSchema.setPosition("");
		tLDPersonSchema.setSalary("");
		tLDPersonSchema.setOccupationType("");
		tLDPersonSchema.setOccupationCode("");
		tLDPersonSchema.setWorkType("");

		tLDPersonSchema.setPluralityType(strContno);
		tLDPersonSchema.setDeathDate("");
		tLDPersonSchema.setSmokeFlag("");
		tLDPersonSchema.setBlacklistFlag("");
		tLDPersonSchema.setProterty("");
		tLDPersonSchema.setRemark("");
		tLDPersonSchema.setState("");
		tLDPersonSchema.setLicenseType("");

		// //////////////////////////////////////////////////
		tLCAddressSchema.setCustomerNo(ss);
		// //////////////////////////////////////////////////
		tLCAddressSchema.setPostalAddress("");
		tLCAddressSchema.setZipCode("");
		tLCAddressSchema.setPhone("");
		tLCAddressSchema.setFax("");
		tLCAddressSchema.setMobile("");
		tLCAddressSchema.setEMail("");

		tLCAddressSchema.setHomeAddress("");
		tLCAddressSchema.setHomeZipCode("");
		tLCAddressSchema.setHomePhone("");
		tLCAddressSchema.setHomeFax("");
		tLCAddressSchema.setCompanyPhone("");
		tLCAddressSchema.setCompanyAddress("");
		tLCAddressSchema.setCompanyZipCode("");
		tLCAddressSchema.setCompanyFax("");
		tLCAddressSchema.setGrpName("");
		tLCAddressSchema.setProvince("");
		tLCAddressSchema.setCity("");
		tLCAddressSchema.setCounty("");
		// ///////////////////////////////////////////////////////////////////
		tTransferData.setNameAndValue("SavePolType", "0"); // 保全保存标记，默认为0，标识非保全
		tTransferData.setNameAndValue("ContNo", strContno);
		tTransferData.setNameAndValue("FamilyType", "");// 家庭单标记
		tTransferData.setNameAndValue("ContType", "2");// 团单，个人单标记
		tTransferData.setNameAndValue("PolTypeFlag", "1");// 无名单标记
		tTransferData.setNameAndValue("InsuredPeoples", "");// 被保险人人数
		tTransferData.setNameAndValue("InsuredAppAge", "");// 被保险人年龄
		tTransferData.setNameAndValue("SequenceNo", "");// 内部客户号
		// //////////////////////////////////////

		tVData.add(tLCContSchema);
		tVData.add(tLDPersonSchema);
		tVData.add(tLCCustomerImpartSet);
		tVData.add(tLCCustomerImpartDetailSet);
		tVData.add(tmLCInsuredSchema);
		tVData.add(tLCAddressSchema);
		tVData.add(tOLDLCInsuredDB);

		tVData.add(tTransferData);
		tVData.add(tGI);

		GrpCardInsuredBL tGrpCardInsuredBL = new GrpCardInsuredBL();
		// 执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
		if (tGrpCardInsuredBL.submitData(tVData, "UPDATE||CONTINSURED")) {
			VData tempVData = tGrpCardInsuredBL.getResult();
			LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();
			mLCInsuredSchema = (LCInsuredSchema) tempVData
					.getObjectByObjectName("LCInsuredSchema", 0);

		}
	}
}
