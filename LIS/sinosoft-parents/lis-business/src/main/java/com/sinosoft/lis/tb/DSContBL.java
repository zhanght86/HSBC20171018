package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.util.*;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.certify.*;
import java.text.*;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: BL层业务逻辑处理类
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
 * @date 2002-09-25
 */
public class DSContBL {
private static Logger logger = Logger.getLogger(DSContBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	private VData rResult = new VData(); // add by yaory

	private String mark = ""; // add by yaory

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 全局数据 */
	private Reflections ref = new Reflections();

	private GlobalInput mGlobalInput = new GlobalInput();

	private MMap map = new MMap();

	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();

	private String theCurrentTime = PubFun.getCurrentTime();

	/** 业务处理相关变量 */
	private LBPOContSchema mLBPOContSchema = new LBPOContSchema();

	private LBPOAppntBL mLBPOAppntBL = new LBPOAppntBL();

	private LBPOInsuredSchema mLBPOInsuredSchema = new LBPOInsuredSchema();

	private LBPOContSchema mOldLBPOCont = new LBPOContSchema();

	private String GrpNo = "";

	private String GrpName = "";

	private LBPOCustomerImpartSet mLBPOCustomerImpartSet;

	private String mDealFlag = "1"; // 程序处理逻辑标志，1为走个险逻辑，2为团险

	private String mFamilyType = "";
	private String mInputNo = "";
	// @Constructor
	public DSContBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将传入的数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		logger.debug("now in ContBL submit");
		// 将外部传入的数据分解到本类的属性中，准备处理
		if (this.getInputData() == false) {
			return false;
		}
		logger.debug("---getInputData---");
		// 校验传入的数据
		// if (!mOperate.equals("DELETE||CONT"))
		// {
		if (this.checkData() == false) {
			return false;
		}
		logger.debug("---checkData---");
		// }

		// 根据业务逻辑对数据进行处理
		logger.debug("---dealData start---");
		if (this.dealData() == false) {
			return false;
		}
		logger.debug("---dealData  ended---");
		// 装配处理好的数据，准备给后台进行保存
		this.prepareOutputData();
		logger.debug("---prepareOutputData---");

		// 数据提交、保存
		if (mark != null) {
			if (mark.equals("card")) {
				logger.debug("in contbl 是卡单！");
				return true;
			}
		}
		logger.debug("卡单不会执行以下程序！");
		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start tPRnewManualDunBLS Submit...");

		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);
			return false;
		}

		logger.debug("---commitData---");
		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			// 全局变量
			mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0));
			// 合同表
			mLBPOContSchema.setSchema((LBPOContSchema) mInputData
					.getObjectByObjectName("LBPOContSchema", 0));
			logger.debug("保单录入:LBPOContSchema.getContNo="
					+ mLBPOContSchema.getContNo());
			logger.debug("保单录入:LBPOContSchema.getInputNo="
					+ mLBPOContSchema.getInputNo());
			logger.debug("保单录入:LBPOContSchema.getContNo="
					+ mLBPOContSchema.getGetPolMode());
			mLBPOAppntBL.setSchema((LBPOAppntSchema) mInputData
					.getObjectByObjectName("LBPOAppntSchema", 0));
			VData tVData = new VData();
			tVData=(VData) mInputData.getObjectByObjectName("VData", 0);
			mLBPOInsuredSchema.setSchema((LBPOInsuredSchema) mInputData
					.getObjectByObjectName("LBPOInsuredSchema", 0));

			TransferData tTransferData = (TransferData) mInputData
					.getObjectByObjectName("TransferData", 0);
			GrpNo = (String) tTransferData.getValueByName("GrpNo");
			GrpName = (String) tTransferData.getValueByName("GrpName");
			mark = (String) tTransferData.getValueByName("mark");
			mFamilyType = (String) tTransferData.getValueByName("FamilyType");
			mInputNo = String.valueOf(mLBPOContSchema.getInputNo());
			logger.debug("卡单标记===in contbl==" + mark);
			if (mLBPOContSchema.getRemark() != null
					&& mLBPOContSchema.getRemark().length() > 800) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ContBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "输入其他声名内容过长!";
				this.mErrors.addOneError(tError);
				return false;
			}
			return true;
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "checkData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			return false;

		}

	}

	/**
	 * 校验传入的数据
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean checkData() {
		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean dealData() {

		// 产生集体投保单号码
		// 设置LBPOCont相关值
		// TakeBackCertifyFalg = true;
//		if(mInputNo.equals("3")){
//			LBPOContDB tLBPOContDB = new LBPOContDB();
//			LBPOInsuredDB tLBPOInsuredDB = new LBPOInsuredDB();
//			LBPOAppntDB tLBPOAppntDB = new LBPOAppntDB();
//			
//			tLBPOContDB.setContNo(mLBPOContSchema.getContNo());
//			tLBPOContDB.setInputNo("3");
//			tLBPOContDB.setFillNo(mLBPOContSchema.getFillNo());
//			tLBPOContDB.getInfo();
//			
//			tLBPOInsuredDB.setContNo(mLBPOContSchema.getContNo());
//			tLBPOInsuredDB.setInputNo("3");
//			tLBPOInsuredDB.setFillNo(mLBPOInsuredSchema.getFillNo());
//			tLBPOInsuredDB.setInsuredNo(mLBPOInsuredSchema.getInsuredNo());
//			tLBPOInsuredDB.getInfo();
//			
//			tLBPOAppntDB.setContNo(mLBPOContSchema.getContNo());
//			tLBPOAppntDB.setInputNo("3");
//			tLBPOAppntDB.setFillNo(mLBPOAppntBL.getFillNo());
//			tLBPOAppntDB.getInfo();
//			
//			Hashtable tHashtable = new Hashtable();
//			LBPODataDetailErrorDB tLBPODataDetailErrorDB = new LBPODataDetailErrorDB();
//			LBPODataDetailErrorSet tLBPODataDetailErrorSet = new LBPODataDetailErrorSet();
//			String tSql = "select * from lbpodatadetailerror where bussno ='"+mLBPOContSchema.getContNo()+"' ";
//			tLBPODataDetailErrorSet = tLBPODataDetailErrorDB
//					.executeQuery(tSql);
//			/**查询字典表，将需要UPDATE的字段放入Hashtable中*/
//			for (int i = 1; i <= tLBPODataDetailErrorSet.size(); i++) {
//				LBPODataDetailErrorSchema tLBPODataDetailErrorSchema = new LBPODataDetailErrorSchema();
//				tLBPODataDetailErrorSchema = tLBPODataDetailErrorSet.get(i);
//				String tKey=tLBPODataDetailErrorSchema.getErrorTag();
//				tHashtable.put(tKey,
//						tLBPODataDetailErrorSchema.getOtherNo());
//			}
//			for(int i=1;i<=tLBPOContDB.getFieldCount();i++){
//				if (tHashtable.containsKey(tLBPOContDB.getFieldName(i))) {
//					if(mLBPOContSchema.getV(i))
//				}
//			}
//			
//		}else{
			logger.debug("aaaa" + mLBPOContSchema.getContNo());
			mLBPOContSchema.setSignCom(mLBPOContSchema.getManageCom());
			mLBPOContSchema.setExecuteCom(mLBPOContSchema.getManageCom());
			// 设置合同信息
			mLBPOContSchema.setModifyDate(theCurrentDate);
			mLBPOContSchema.setModifyTime(theCurrentTime);
			mLBPOContSchema.setOperator(mGlobalInput.Operator);
			
			LBPOContDB tLBPOContDB = new LBPOContDB();
			tLBPOContDB.setContNo(mLBPOContSchema.getContNo());
			tLBPOContDB.setFillNo(mLBPOContSchema.getFillNo());
			tLBPOContDB.setInputNo(mLBPOContSchema.getInputNo());
			if(!tLBPOContDB.getInfo()){
				{
					CError.buildErr(this,"查询合同信息失败！");
					return false;
				}
			}
			LBPOAppntDB tLBPOAppntDB = new LBPOAppntDB();
			LBPOAppntSet tLBPOAppntSet = new LBPOAppntSet();
			LBPOAppntSchema tLBPOAppntSchema = new LBPOAppntSchema();
			tLBPOAppntDB.setAppntNo(mLBPOAppntBL.getAppntNo());
			tLBPOAppntDB.setContNo(mLBPOContSchema.getContNo());
			tLBPOAppntDB.setFillNo(mLBPOAppntBL.getFillNo());
			tLBPOAppntDB.setInputNo(mLBPOContSchema.getInputNo());
			tLBPOAppntSet=tLBPOAppntDB.query();
			if(tLBPOAppntSet.size()<1||tLBPOAppntSet.size()>1){
				CError.buildErr(this, "查询投保人信息失败！");
				return false;
			}
			tLBPOAppntSchema=tLBPOAppntSet.get(1);
			
			LBPOInsuredDB tLBPOInsuredDB = new LBPOInsuredDB();
			tLBPOInsuredDB.setContNo(mLBPOContSchema.getContNo());
			tLBPOInsuredDB.setInputNo(mLBPOContSchema.getInputNo());
			tLBPOInsuredDB.setFillNo(mLBPOInsuredSchema.getFillNo());
			tLBPOInsuredDB.setInsuredNo(mLBPOInsuredSchema.getInsuredNo());
			if(!tLBPOInsuredDB.getInfo()){
				CError.buildErr(this, "查询被保人信息失败！");
				return false;
			}
			
			// 个人投保人部分信息
			tLBPOAppntSchema.setContNo(mLBPOContSchema.getContNo());
			tLBPOAppntSchema.setGrpContNo(mLBPOContSchema.getGrpContNo());
			tLBPOAppntSchema.setPrtNo(mLBPOContSchema.getPrtNo());
			tLBPOAppntSchema.setManageCom(mLBPOAppntBL.getManageCom());
			tLBPOAppntSchema.setModifyDate(theCurrentDate);
			tLBPOAppntSchema.setModifyTime(theCurrentTime);
			tLBPOAppntSchema.setOperator(mGlobalInput.Operator);
			tLBPOAppntSchema.setInputNo(mLBPOContSchema.getInputNo());
			tLBPOAppntSchema.setRelationToInsured(mLBPOAppntBL.getRelationToInsured());
			tLBPOAppntSchema.setAppntName(mLBPOAppntBL.getAppntName());
			tLBPOAppntSchema.setAppntSex(mLBPOAppntBL.getAppntSex());
			tLBPOAppntSchema.setAppntBirthday(mLBPOAppntBL.getAppntBirthday());
			tLBPOAppntSchema.setIDType(mLBPOAppntBL.getIDType());
			tLBPOAppntSchema.setIDNo(mLBPOAppntBL.getIDNo());
			tLBPOAppntSchema.setRgtAddress(mLBPOAppntBL.getRgtAddress());
			tLBPOAppntSchema.setMarriage(mLBPOAppntBL.getMarriage());
			tLBPOAppntSchema.setOccupationType(mLBPOAppntBL.getOccupationType());
			tLBPOAppntSchema.setOccupationCode(mLBPOAppntBL.getOccupationCode());
			tLBPOAppntSchema.setWorkType(mLBPOAppntBL.getWorkType());
			tLBPOAppntSchema.setPluralityType(mLBPOAppntBL.getPluralityType());
			tLBPOAppntSchema.setNativePlace(mLBPOAppntBL.getNativePlace());
			tLBPOAppntSchema.setNationality(mLBPOAppntBL.getNationality());
			tLBPOAppntSchema.setBankCode(mLBPOAppntBL.getBankCode());
			tLBPOAppntSchema.setAccName(mLBPOAppntBL.getAccName());
			tLBPOAppntSchema.setWorkType(mLBPOAppntBL.getWorkType());
			tLBPOAppntSchema.setPluralityType(mLBPOAppntBL.getPluralityType());
			tLBPOAppntSchema.setPostalAddress(mLBPOAppntBL.getPostalAddress());
			tLBPOAppntSchema.setZipCode(mLBPOAppntBL.getZipCode());
			tLBPOAppntSchema.setPhone(mLBPOAppntBL.getPhone());
			tLBPOAppntSchema.setMobile(mLBPOAppntBL.getMobile());
			tLBPOAppntSchema.setEMail(mLBPOAppntBL.getEMail());
			tLBPOAppntSchema.setHomeAddress(mLBPOAppntBL.getHomeAddress());
			tLBPOAppntSchema.setHomePhone(mLBPOAppntBL.getHomePhone());
			tLBPOAppntSchema.setHomeZipCode(mLBPOAppntBL.getHomeZipCode());
			tLBPOAppntSchema.setCompanyPhone(mLBPOAppntBL.getCompanyPhone());
			tLBPOAppntSchema.setCompanyAddress(mLBPOAppntBL.getCompanyAddress());
			tLBPOAppntSchema.setCompanyZipCode(mLBPOAppntBL.getCompanyZipCode());
			tLBPOAppntSchema.setIDExpDate(mLBPOAppntBL.getIDExpDate());
			tLBPOAppntSchema.setSocialInsuFlag(mLBPOAppntBL.getSocialInsuFlag());
			
			if(!(mFamilyType.equals("1")&&(mLBPOInsuredSchema.getSequenceNo().equals("2")||mLBPOInsuredSchema.getSequenceNo().equals("3")))){
				map.put(tLBPOAppntSchema, "UPDATE");
			}
			
			tLBPOInsuredDB.setExecuteCom(mGlobalInput.ManageCom);
			tLBPOInsuredDB.setAppntNo(tLBPOAppntDB.getAppntNo());
			tLBPOInsuredDB.setContNo(mLBPOContSchema.getContNo());
			tLBPOInsuredDB.setGrpContNo(mLBPOContSchema.getGrpContNo());
			tLBPOInsuredDB.setPrtNo(mLBPOContSchema.getPrtNo());
			tLBPOInsuredDB.setManageCom(mLBPOInsuredSchema.getManageCom());
			tLBPOInsuredDB.setOperator(mGlobalInput.Operator);
			tLBPOInsuredDB.setInputNo(mLBPOContSchema.getInputNo());
			tLBPOInsuredDB.setModifyDate(theCurrentDate);
			tLBPOInsuredDB.setModifyTime(theCurrentTime);
			tLBPOInsuredDB.setName(mLBPOInsuredSchema.getName());
			tLBPOInsuredDB.setSex(mLBPOInsuredSchema.getSex());
			tLBPOInsuredDB.setBirthday(mLBPOInsuredSchema.getBirthday());
			tLBPOInsuredDB.setIDType(mLBPOInsuredSchema.getIDType());
			tLBPOInsuredDB.setIDNo(mLBPOInsuredSchema.getIDNo());
			tLBPOInsuredDB.setNativePlace(mLBPOInsuredSchema.getNativePlace());
			tLBPOInsuredDB.setMarriage(mLBPOInsuredSchema.getMarriage());
			tLBPOInsuredDB.setWorkType(mLBPOInsuredSchema.getWorkType());
			tLBPOInsuredDB.setPluralityType(mLBPOInsuredSchema.getPluralityType());
			tLBPOInsuredDB.setOccupationCode(mLBPOInsuredSchema.getOccupationCode());
			tLBPOInsuredDB.setOccupationType(mLBPOInsuredSchema.getOccupationType());
			tLBPOInsuredDB.setRelationToMainInsured(mLBPOInsuredSchema.getRelationToMainInsured());
			tLBPOInsuredDB.setRelationToAppnt(mLBPOInsuredSchema.getRelationToAppnt());
			tLBPOInsuredDB.setContPlanCode(mLBPOInsuredSchema.getContPlanCode());
			tLBPOInsuredDB.setRgtAddress(mLBPOInsuredSchema.getRgtAddress());
			tLBPOInsuredDB.setSequenceNo(mLBPOInsuredSchema.getSequenceNo());
			tLBPOInsuredDB.setPostalAddress(mLBPOInsuredSchema.getPostalAddress());
			tLBPOInsuredDB.setZipCode(mLBPOInsuredSchema.getZipCode());
			tLBPOInsuredDB.setPhone(mLBPOInsuredSchema.getPhone());
			tLBPOInsuredDB.setMobile(mLBPOInsuredSchema.getMobile());
			tLBPOInsuredDB.setEMail(mLBPOInsuredSchema.getEMail());
			tLBPOInsuredDB.setHomeAddress(mLBPOInsuredSchema.getHomeAddress());
			tLBPOInsuredDB.setHomeZipCode(mLBPOInsuredSchema.getHomeZipCode());
			tLBPOInsuredDB.setHomePhone(mLBPOInsuredSchema.getHomePhone());
			tLBPOInsuredDB.setCompanyPhone(mLBPOInsuredSchema.getCompanyPhone());
			tLBPOInsuredDB.setCompanyAddress(mLBPOInsuredSchema.getCompanyAddress());
			tLBPOInsuredDB.setCompanyZipCode(mLBPOInsuredSchema.getCompanyZipCode());
			tLBPOInsuredDB.setIDExpDate(mLBPOInsuredSchema.getIDExpDate());
			tLBPOInsuredDB.setSocialInsuFlag(mLBPOInsuredSchema.getSocialInsuFlag());
			map.put(tLBPOInsuredDB.getSchema(), "UPDATE");
			//map.put(tLBPOContDB.getSchema(), "UPDATE");
//		}
		
		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: void
	 */
	private void prepareOutputData() {
		mInputData.clear();
		mInputData.add(map);
		rResult.clear();
		rResult.add(map);
		mResult.clear();

		mResult.add(mLBPOInsuredSchema);
		mResult.add(mLBPOContSchema);
		mResult.add(mLBPOAppntBL.getSchema());
	}

	/**
	 * 得到处理后的结果集
	 * 
	 * @return 结果集
	 */

	public VData getResult() {
		return mResult;
	}

	public VData getCardResult() {
		return rResult;
	}

	/**
	 * 校验个人投保单的客户
	 * 
	 * @return
	 */
//	private boolean checkLDPerson() {
//		// 如果是无名单或者公共帐户的个人，不校验返回
//		if (mLBPOContSchema.getPolType().equals("1")
//				|| mLBPOContSchema.getPolType().equals("2")) {
//			return true;
//		}
//
//		// 投保人-个人客户--如果是集体下个人，该投保人为空,所以个人时校验个人投保人
//		if (mDealFlag.equals("1")) {
//			if (mLDPersonSchema != null) {
//				if (mLDPersonSchema.getName() != null) { // 去空格
//					mLDPersonSchema.setName(mLDPersonSchema.getName().trim());
//					if (mLDPersonSchema.getSex() != null) {
//						mLDPersonSchema.setSex(mLDPersonSchema.getSex().trim());
//					}
//					if (mLDPersonSchema.getIDNo() != null) {
//						mLDPersonSchema.setIDNo(mLDPersonSchema.getIDNo()
//								.trim());
//					}
//					if (mLDPersonSchema.getIDType() != null) {
//						mLDPersonSchema.setIDType(mLDPersonSchema.getIDType()
//								.trim());
//					}
//					if (mLDPersonSchema.getBirthday() != null) {
//						mLDPersonSchema.setBirthday(mLDPersonSchema
//								.getBirthday().trim());
//					}
//
//				}
//				if (mLDPersonSchema.getCustomerNo() != null) {
//					// 如果有客户号
//					if (!mLDPersonSchema.getCustomerNo().equals("")) {
//						LDPersonDB tLDPersonDB = new LDPersonDB();
//
//						tLDPersonDB.setCustomerNo(mLDPersonSchema
//								.getCustomerNo());
//						if (tLDPersonDB.getInfo() == false) {
//							CError tError = new CError();
//							tError.moduleName = "ContBL";
//							tError.functionName = "checkPerson";
//							tError.errorMessage = "数据库查询失败!";
//							this.mErrors.addOneError(tError);
//
//							return false;
//						}
//						if (mLDPersonSchema.getName() != null) {
//							String Name = StrTool.GBKToUnicode(tLDPersonDB
//									.getName().trim());
//							String NewName = StrTool
//									.GBKToUnicode(mLDPersonSchema.getName()
//											.trim());
//
//							if (!Name.equals(NewName)) {
//								CError tError = new CError();
//								tError.moduleName = "ProposalBL";
//								tError.functionName = "checkPerson";
//								tError.errorMessage = "您输入的投保人客户号对应在数据库中的客户姓名("
//										+ Name + ")与您录入的客户姓名(" + NewName
//										+ ")不匹配！请去掉客户号码以及地址号码，重新操作！";
//								this.mErrors.addOneError(tError);
//
//								return false;
//							}
//						}
//						if (mLDPersonSchema.getSex() != null) {
//							if (!tLDPersonDB.getSex().equals(
//									mLDPersonSchema.getSex())) {
//								CError tError = new CError();
//								tError.moduleName = "ContBL";
//								tError.functionName = "checkPerson";
//								tError.errorMessage = "您输入的投保人客户号对应在数据库中的客户性别("
//										+ tLDPersonDB.getSex() + ")与您录入的客户性别("
//										+ mLDPersonSchema.getSex() + ")不匹配！";
//								this.mErrors.addOneError(tError);
//
//								return false;
//							}
//						}
//					} else { // 如果没有客户号,查找客户信息表是否有相同名字，性别，出生年月，身份证号的纪录，若有，取客户号
//						if ((mLDPersonSchema.getName() != null)
//								&& (mLDPersonSchema.getSex() != null)
//								&& (mLDPersonSchema.getIDNo() != null)) {
//							LDPersonDB tLDPersonDB = new LDPersonDB();
//							tLDPersonDB.setName(mLDPersonSchema.getName());
//							tLDPersonDB.setSex(mLDPersonSchema.getSex());
//							tLDPersonDB.setBirthday(mLDPersonSchema
//									.getBirthday());
//
//							tLDPersonDB.setIDType("0"); // 证件类型为身份证
//							tLDPersonDB.setIDNo(mLDPersonSchema.getIDNo());
//
//							LDPersonSet tLDPersonSet = tLDPersonDB.query();
//							if (tLDPersonSet != null) {
//								if (tLDPersonSet.size() > 0) {
//									mLDPersonSchema.setCustomerNo(tLDPersonSet
//											.get(1).getCustomerNo());
//								}
//							}
//						}
//					}
//				}
//
//			}
//		}
//		return true;
//	}

	/**
	 * 检查保单代理人数据是否正确 如果在处理过程中出错或者数据有错误，则返回false,否则返回true
	 */
//	private boolean checkAgent() {
//		// 2005-12-2添加 增加 业务员作业区域控制
//		// 系统能够自动校验业务员所属机构与操作员登录机构是否同属一个分公司,对于校验不通过的,系统控制不能进行后续保存等相关操作
//		// tongmeng 2007-09-06 modify
//		// 对于MSRS，如果是以总公司登陆的，则不做代理人管理机构和登陆机构的校验
//
//		String tOperator = mGlobalInput.Operator; // 操作员代码
//		String tOperatorCom = mGlobalInput.ManageCom; // 操作员所在机构代码
//		String tOperatorFiliale = ""; // 操作员所在机构所在分公司代码,取代码前四位
//
//		// 查询代理人的信息
//		String tAgentCode = mLBPOContSchema.getAgentCode().trim(); // 业务员代码
//		String tAgentCom = ""; // 业务员所属机构
//		String tAgentFiliale = ""; // 业务员所属机构所在分公司代码,取代码前四位
//		String tAgentSQL = "select agentcode,name,agentgroup,managecom  from laagent where 1=1"
//				+ " and agentcode='" + tAgentCode + "'";
//		ExeSQL tAgentExeSQL = new ExeSQL();
//		SSRS tAgentSSRS = new SSRS();
//		tAgentSSRS = tAgentExeSQL.execSQL(tAgentSQL);
//		if (tAgentSSRS == null || tAgentSSRS.getMaxRow() == 0) {
//			CError tError = new CError();
//			tError.moduleName = "ContBL";
//			tError.functionName = "checkAgent";
//			tError.errorMessage = "查询业务员[" + tAgentCode + "]信息失败！";
//			this.mErrors.addOneError(tError);
//			return false;
//		}
//
//		if (tOperatorCom != null && tOperatorCom.trim().length() == 2) {
//			logger.debug("登陆管理机构为总公司，不做登陆机构校验。");
//		} else {
//			if (tOperatorCom != null && tOperatorCom.trim().length() >= 4) {
//				tOperatorFiliale = tOperatorCom.trim().substring(0, 4); // 取代码前四位
//			}
//
//			tAgentCom = tAgentSSRS.GetText(1, 4);
//			if (tAgentCom != null && !tAgentCom.equals("null")
//					&& tAgentCom.trim().length() >= 4) {
//				tAgentFiliale = tAgentCom.trim().substring(0, 4);
//			}
//			// 校验业务员所属机构与操作员登录机构是否同属一个分公司,不是则不通过，返回错误
//			if (tAgentFiliale.equals("") || tOperatorFiliale.equals("")
//					|| !tAgentFiliale.equals(tOperatorFiliale)) {
//				String ErrInfo = "校验业务员作业区域控制错误可能原因是：1、获取业务员所属机构与操作员登录机构所在分公司代码出错;";
//				ErrInfo = ErrInfo + "2、业务员所属机构与操作员登录机构是不属一个分公司，不允许保存投保单信息";
//				ErrInfo = ErrInfo + " ||业务员代码[" + tAgentCode + "],所属机构代码["
//						+ tAgentCom + "],所在分公司代码[" + tAgentFiliale + "]";
//				ErrInfo = ErrInfo + " ||操作员代码[" + tOperator + "],登录机构代码["
//						+ tOperatorCom + "],所在分公司代码[" + tOperatorFiliale + "]";
//				CError tError = new CError();
//				tError.moduleName = "ContBL";
//				tError.functionName = "checkAgent";
//				tError.errorMessage = ErrInfo;
//				this.mErrors.addOneError(tError);
//				return false;
//			}
//		}
//
//		// 2005-12-31添加校验业务员是否有销售资格，传入业务员代码。
//		String tCalCode = "AgSale"; // 根据字串从 lmcalmode 里获取 校验的SQL字串
//		TransferData tTransferData = new TransferData();
//		tTransferData.setNameAndValue("AgentCode", tAgentCode); // 校验字串所需参数
//		if (PubFun.userDefinedCheck(tCalCode, tTransferData) == false) {
//			String ErrInfo = "校验业务员销售资格失败：业务员[" + tAgentCode + "]没有销售资格。";
//			logger.debug(ErrInfo);
//			CError tError = new CError();
//			tError.moduleName = "ContBL";
//			tError.functionName = "checkAgent";
//			tError.errorMessage = ErrInfo;
//			this.mErrors.addOneError(tError);
//			return false;
//		}
//		// modify 2007-04-27 团体不校验conttype=2 为团体业务
//		if (!mLBPOContSchema.getContType().equals("2")) {
//			// 2005-10-24添加 增加 代理人校验
//			// 根据传入的 合同号 查询 ljtempfee ,将获得的“代理人编码”与 传入的“代理人编码”比较
//			String AgentSymbol = "false"; // 默认不通过
//			String strFeeAgent = ""; // 获取ljtempfee表的代理人编码
//			String strAgentCode = mLBPOContSchema.getAgentCode();
//			String sSQL = "select distinct agentcode from ljtempfee where 1=1 "
//					+ " and confdate is null and confflag='0'"
//					+ " and othernotype in ('6','7') and otherno='"
//					+ mLBPOContSchema.getContNo() + "' ";
//			ExeSQL sExeSQL = new ExeSQL();
//			SSRS strSSRS = new SSRS();
//			strSSRS = sExeSQL.execSQL(sSQL);
//			int m = strSSRS.getMaxRow();
//			if (m == 0) { // 暂交费记录表为零则是没有暂交费，不用校验
//				AgentSymbol = "true";
//				logger.debug("没有暂交费记录信息，无需代理人校验");
//			} else {
//				// 有暂交费，则必须校验代理人，只要有一个相同，则校验通过
//				for (int i = 1; i <= m; i++) {
//					String stra = strSSRS.GetText(i, 1);
//					if (strFeeAgent == null || strFeeAgent.equals("")) {
//						strFeeAgent = stra;
//					} else {
//						strFeeAgent = strFeeAgent + ";" + stra;
//					}
//					if (stra.equals(strAgentCode)) {
//						AgentSymbol = "true";
//						// break;
//					}
//				}
//			}
//			// 判断是否校验成功，若果失败则返回失败信息
//			if (AgentSymbol.equals("false")) {
//				logger.debug("代理人校验失败，此处传入的代理人代码与暂交费记录的代理人代码不符!");
//				CError tError = new CError();
//				tError.moduleName = "ContBL";
//				tError.functionName = "checkData";
//				tError.errorMessage = "代理人校验失败!此处传入的代理人代码[" + strAgentCode
//						+ "]与暂交费记录的代理人代码[" + strFeeAgent + "]不符";
//				this.mErrors.addOneError(tError);
//				return false;
//			}
//		}
//		return true;
//	}

	/**
	 * 检查投保人数据是否正确 如果在处理过程中出错或者数据有错误，则返回false,否则返回true
	 */
//	private boolean checkLBPOAppnt() {
//		// 如果是无名单或者公共帐户的个人，不校验返回
//		if (mLBPOContSchema.getPolType().equals("1")
//				|| mLBPOContSchema.getPolType().equals("2")) {
//			return true;
//		}
//
//		// 投保人-个人客户--如果是集体下个人，该投保人为空,所以个人时校验个人投保人
//		if (mDealFlag.equals("1") && mLBPOAppntBL != null) {
//			// 去空格
//			if (mLBPOAppntBL.getAppntName() != null) {
//				mLBPOAppntBL.setAppntName(mLBPOAppntBL.getAppntName().trim());
//			}
//			if (mLBPOAppntBL.getAppntSex() != null) {
//				mLBPOAppntBL.setAppntSex(mLBPOAppntBL.getAppntSex().trim());
//			}
//			if (mLBPOAppntBL.getIDNo() != null) {
//				mLBPOAppntBL.setIDNo(mLBPOAppntBL.getIDNo().trim());
//			}
//			if (mLBPOAppntBL.getIDType() != null) {
//				mLBPOAppntBL.setIDType(mLBPOAppntBL.getIDType().trim());
//			}
//			if (mLBPOAppntBL.getAppntBirthday() != null) {
//				mLBPOAppntBL.setAppntBirthday(mLBPOAppntBL.getAppntBirthday()
//						.trim());
//			}
//			// 如果客户号存在，那么就利用客户号查询数据，将查询出的客户信息与录入客户信息比较
//			// 如皋重要信息（姓名、证件类型、证件号码、出生日期、性别）有任意一项不同
//			// 去掉客户号码以及地址号码，且客户号需要重新生成。
//		}
//		return true;
//	}

	/**
	 * 检查地址数据是否正确 如果在处理过程中出错或者数据有错误，则返回false,否则返回true
	 */
//	private MMap getRelation(String ContRelationNO, String LDPersonRelationNO) {
//		MMap rMMap = new MMap();
//		ES_DOC_MAINDB mES_DOC_MAINDB = new ES_DOC_MAINDB();
//		mES_DOC_MAINDB.setDocCode(mLBPOContSchema.getPrtNo());
//		ES_DOC_MAINSet mES_DOC_MAINSet = mES_DOC_MAINDB.query();
//		if (mES_DOC_MAINSet.size() == 0) {
//			return null;
//		}
//		for (int i = 0; i < mES_DOC_MAINSet.size(); i++) {
//			ES_DOC_RELATIONSchema mES_DOC_RELATIONSchema = new ES_DOC_RELATIONSchema();
//			mES_DOC_RELATIONSchema.setBussNoType("13");
//			mES_DOC_RELATIONSchema.setBussNo(ContRelationNO);
//			mES_DOC_RELATIONSchema.setBussType(mES_DOC_MAINSet.get(i + 1)
//					.getBussType());
//			mES_DOC_RELATIONSchema.setSubType(mES_DOC_MAINSet.get(i + 1)
//					.getSubType());
//			mES_DOC_RELATIONSchema.setRelaFlag("0");
//			mES_DOC_RELATIONSchema.setDocID(mES_DOC_MAINSet.get(i + 1)
//					.getDocID());
//			rMMap.put(mES_DOC_RELATIONSchema, "DELETE&INSERT");
//		}
//		for (int i = 0; i < mES_DOC_MAINSet.size(); i++) {
//			ES_DOC_RELATIONSchema mES_DOC_RELATIONSchema = new ES_DOC_RELATIONSchema();
//			mES_DOC_RELATIONSchema.setBussNoType("42");
//			mES_DOC_RELATIONSchema.setBussNo(LDPersonRelationNO);
//			mES_DOC_RELATIONSchema.setBussType(mES_DOC_MAINSet.get(i + 1)
//					.getBussType());
//			mES_DOC_RELATIONSchema.setSubType(mES_DOC_MAINSet.get(i + 1)
//					.getSubType());
//			mES_DOC_RELATIONSchema.setRelaFlag("0");
//			mES_DOC_RELATIONSchema.setDocID(mES_DOC_MAINSet.get(i + 1)
//					.getDocID());
//			rMMap.put(mES_DOC_RELATIONSchema, "DELETE&INSERT");
//		}
//		return rMMap;
//	}

//	private boolean checkLBPOAddress() {
//
//		if (mAppntAddressSchema != null) {
//			if (mAppntAddressSchema.getPostalAddress() != null) { // 去空格
//				mAppntAddressSchema.setPostalAddress(mAppntAddressSchema
//						.getPostalAddress().trim());
//				if (mAppntAddressSchema.getZipCode() != null) {
//					mAppntAddressSchema.setZipCode(mAppntAddressSchema
//							.getZipCode().trim());
//				}
//				if (mAppntAddressSchema.getPhone() != null) {
//					mAppntAddressSchema.setPhone(mAppntAddressSchema.getPhone()
//							.trim());
//				}
//
//			}
//			if (mAppntAddressSchema.getAddressNo() != 0
//					&& mLBPOAppntBL.getAppntNo() != null
//					&& !mLBPOAppntBL.getAppntNo().equals("")) {
//				// 如果有地址号
//				if (mAppntAddressSchema.getAddressNo() != 0) {
//					LBPOAddressDB tLBPOAddressDB = new LBPOAddressDB();
//
//					tLBPOAddressDB.setAddressNo(mAppntAddressSchema
//							.getAddressNo());
//					tLBPOAddressDB.setCustomerNo(mAppntAddressSchema
//							.getCustomerNo());
//					if (tLBPOAddressDB.getInfo() == false) {
//						mAppntAddressSchema.setAddressNo(0);
//					}
//					if (!StrTool.compareString(mAppntAddressSchema
//							.getCustomerNo(), tLBPOAddressDB.getCustomerNo())) {
//						mAppntAddressSchema.setAddressNo(0);
//
//					}
//					if (!StrTool.compareString(""
//							+ mAppntAddressSchema.getAddressNo(), ""
//							+ tLBPOAddressDB.getAddressNo())) {
//						mAppntAddressSchema.setAddressNo(0);
//
//					}
//					if (!StrTool.compareString(mAppntAddressSchema
//							.getPostalAddress(), tLBPOAddressDB
//							.getPostalAddress())) {
//						mAppntAddressSchema.setAddressNo(0);
//
//					}
//					if (!StrTool.compareString(
//							mAppntAddressSchema.getZipCode(), tLBPOAddressDB
//									.getZipCode())) {
//						mAppntAddressSchema.setAddressNo(0);
//					}
//					if (!StrTool.compareString(mAppntAddressSchema.getPhone(),
//							tLBPOAddressDB.getPhone())) {
//						mAppntAddressSchema.setAddressNo(0);
//
//					}
//					if (!StrTool.compareString(mAppntAddressSchema.getFax(),
//							tLBPOAddressDB.getFax())) {
//						mAppntAddressSchema.setAddressNo(0);
//					}
//					if (!StrTool.compareString(mAppntAddressSchema
//							.getHomeAddress(), tLBPOAddressDB.getHomeAddress())) {
//						mAppntAddressSchema.setAddressNo(0);
//					}
//					if (!StrTool.compareString(mAppntAddressSchema
//							.getHomeZipCode(), tLBPOAddressDB.getHomeZipCode())) {
//						mAppntAddressSchema.setAddressNo(0);
//
//					}
//					if (!StrTool.compareString(mAppntAddressSchema
//							.getHomePhone(), tLBPOAddressDB.getHomePhone())) {
//						mAppntAddressSchema.setAddressNo(0);
//
//					}
//					if (!StrTool.compareString(
//							mAppntAddressSchema.getHomeFax(), tLBPOAddressDB
//									.getHomeFax())) {
//						mAppntAddressSchema.setAddressNo(0);
//
//					}
//					if (!StrTool.compareString(mAppntAddressSchema
//							.getCompanyAddress(), tLBPOAddressDB
//							.getCompanyAddress())) {
//						mAppntAddressSchema.setAddressNo(0);
//
//					}
//					if (!StrTool.compareString(mAppntAddressSchema
//							.getCompanyZipCode(), tLBPOAddressDB
//							.getCompanyZipCode())) {
//						mAppntAddressSchema.setAddressNo(0);
//					}
//					if (!StrTool.compareString(mAppntAddressSchema
//							.getCompanyPhone(), tLBPOAddressDB
//							.getCompanyPhone())) {
//						mAppntAddressSchema.setAddressNo(0);
//					}
//					if (!StrTool.compareString(mAppntAddressSchema
//							.getCompanyFax(), tLBPOAddressDB.getCompanyFax())) {
//						mAppntAddressSchema.setAddressNo(0);
//					}
//					if (!StrTool.compareString(mAppntAddressSchema.getMobile(),
//							tLBPOAddressDB.getMobile())) {
//						mAppntAddressSchema.setAddressNo(0);
//					}
//					if (!StrTool.compareString(mAppntAddressSchema
//							.getMobileChs(), tLBPOAddressDB.getMobileChs())) {
//						mAppntAddressSchema.setAddressNo(0);
//					}
//					if (!StrTool.compareString(mAppntAddressSchema.getEMail(),
//							tLBPOAddressDB.getEMail())) {
//						mAppntAddressSchema.setAddressNo(0);
//					}
//					if (!StrTool.compareString(mAppntAddressSchema.getBP(),
//							tLBPOAddressDB.getBP())) {
//						mAppntAddressSchema.setAddressNo(0);
//					}
//					if (!StrTool.compareString(
//							mAppntAddressSchema.getMobile2(), tLBPOAddressDB
//									.getMobile2())) {
//						mAppntAddressSchema.setAddressNo(0);
//					}
//					if (!StrTool.compareString(mAppntAddressSchema
//							.getMobileChs2(), tLBPOAddressDB.getMobileChs2())) {
//						mAppntAddressSchema.setAddressNo(0);
//					}
//					if (!StrTool.compareString(mAppntAddressSchema.getEMail2(),
//							tLBPOAddressDB.getEMail2())) {
//						mAppntAddressSchema.setAddressNo(0);
//					}
//					if (!StrTool.compareString(mAppntAddressSchema.getBP2(),
//							tLBPOAddressDB.getBP2())) {
//						mAppntAddressSchema.setAddressNo(0);
//					}
//					if (!StrTool.compareString(
//							mAppntAddressSchema.getGrpName(), tLBPOAddressDB
//									.getGrpName())) {
//						mAppntAddressSchema.setAddressNo(0);
//					}
//
//				}
//			} else {
//				mAppntAddressSchema.setAddressNo(0);
//			}
//
//		}
//
//		return true;
//	}

	// --------------add by haopan 2007-03-08----用verifyinput检验效率太低故放到后来类里----//

	/**
	 * 检查操作员录入的管理机构是否正确 如果在处理过程中出错或者数据有错误，则返回false,否则返回true
	 * 放开此方法并增加校验内容。---yeshu,20071220
	 */
//	private boolean checkInputManageCom() {
//		// 1.校验录入机构是否存在。
//		LDComDB tLDComDB = new LDComDB();
//		tLDComDB.setComCode(mLBPOContSchema.getManageCom().trim());
//		if (!tLDComDB.getInfo()) {
//			CError tError = new CError();
//			tError.moduleName = "ProposalBL";
//			tError.functionName = "checkPerson";
//			tError.errorMessage = "录入的管理机构不存在！";
//			this.mErrors.addOneError(tError);
//			return false;
//		}
//		// 2.校验录入机构与代理人信息是否匹配。
//		LAAgentDB tLAAgentDB = new LAAgentDB();
//		tLAAgentDB.setAgentCode(mLBPOContSchema.getAgentCode());
//		tLAAgentDB.setManageCom(mLBPOContSchema.getManageCom());
//		if (tLAAgentDB.query().size() <= 0) {
//			CError tError = new CError();
//			tError.moduleName = "ProposalBL";
//			tError.functionName = "checkPerson";
//			tError.errorMessage = "录入的管理机构与代理人所属机构不符！";
//			this.mErrors.addOneError(tError);
//			return false;
//		}
//		// 3.存在暂交费时校验是否与暂交费收取机构匹配。（待定）
//
//		// 4.校验录入机构是否有权销售已存的险种。(目前MS可能没有这条规则)
//
//		return true;
//	}

	/**
	 * 检查操作员录入的职业编码是否正确 如果在处理过程中出错或者数据有错误，则返回false,否则返回true
	 */
//	private boolean checkInputOccupationCode() {
//		logger.debug("录入的职业编码是===" + mLBPOAppntBL.getOccupationCode());
//		if ((mLBPOAppntBL.getOccupationCode() == null)
//				|| (mLBPOAppntBL.getOccupationCode().equals(""))
//				|| (mLBPOAppntBL.getOccupationCode() == "")
//				|| ("".equals(mLBPOAppntBL.getOccupationCode()))) {
//			logger.debug("^^^^^^^^^^^^^^^^职业编码不是必录项^^^^^^^^^^^^^^^^^^");
//			return true;
//		} else {
//			String chkSQL = "select 'X' from LDOccupation where 1=1 and worktype = 'GR' and  "
//					+ " OccupationCode ='"
//					+ mLBPOAppntBL.getOccupationCode().trim() + "'";
//			logger.debug("校验录入的职业编码是否正确:::::" + chkSQL);
//			ExeSQL chkocExeSQL = new ExeSQL();
//			SSRS chkocSSRS = new SSRS();
//			chkocSSRS = chkocExeSQL.execSQL(chkSQL);
//			if (chkocSSRS.getMaxRow() == 0 || chkocSSRS == null) {
//				CError tError = new CError();
//				tError.moduleName = "ContBL";
//				tError.functionName = "checkInputManageCom";
//				tError.errorMessage = "录入的职业编码不正确！";
//				this.mErrors.addOneError(tError);
//				return false;
//			}
//		}
//		return true;
//	}

	// ------------------------add end --------------//

	/**
	 * 检查修改的合同信息是否符合业务规则。 如果在处理过程中出错或者数据有错误，则返回false,否则返回true ---yeshu,20071220
	 */
//	private boolean checkCont() {
//		if (mOperate.equals("UPDATE||CONT")) {
//			logger.debug("cont is===============>"
//					+ mLBPOContSchema.getContNo());
//			LBPOContDB tLBPOContDB = new LBPOContDB();
//			tLBPOContDB.setContNo(mLBPOContSchema.getContNo());
//			if (tLBPOContDB.getInfo() == false) {
//				// @@错误处理
//				this.mErrors.copyAllErrors(tLBPOContDB.mErrors);
//				return false;
//			}
//			mOldLBPOCont.setSchema(tLBPOContDB);
//			logger.debug(mOldLBPOCont.getContNo());
//			logger.debug(mOldLBPOCont.getInsuredNo());
//			logger.debug(mOldLBPOCont.getProposalContNo());
//			logger.debug(mOldLBPOCont.getPrtNo());
//			// 若修改了合同投保日期：
//			// 1.校验投保日期是否超出已存险种的在售期间；
//			// 2.取界面传入的投保人出生日期（二者可能同时修改）及数据库中的被保人出生日期重算投被保人年龄，校验是否超过已存险种的允许范围。
//			if (!mOldLBPOCont.getPolApplyDate().equals(
//					mLBPOContSchema.getPolApplyDate())) {
//				int newAppntAge = PubFun.calInterval(mLBPOAppntBL
//						.getAppntBirthday(), mLBPOContSchema.getPolApplyDate(),
//						"Y");
//				logger.debug("newAppntAge=" + newAppntAge);
//				if (newAppntAge < 18) {
//					CError tError = new CError();
//					tError.moduleName = "ContBL";
//					tError.functionName = "checkCont";
//					tError.errorMessage = "合同投保日投保人未满18周岁！";
//					this.mErrors.addOneError(tError);
//					return false;
//				}
//
//				SSRS tSSRS = new ExeSQL()
//						.execSQL("select a.insuredbirthday,nvl(b.startdate,'1900-01-01'),nvl(b.enddate,'3000-01-01'),nvl(b.minappntage,0),nvl(b.maxappntage,105),nvl(b.mininsuredage,0),nvl(b.maxinsuredage,105),b.riskname from LBPOpol a,lmriskapp b where a.riskcode=b.riskcode and a.contno='"
//								+ mLBPOContSchema.getContNo() + "'");
//				if (tSSRS != null && tSSRS.MaxRow >= 1) {
//					for (int i = 1; i <= tSSRS.MaxRow; i++) {
//						// 将来可能出现多被保人的情况，所以在循环内计算被保人投保年龄。
//						int newInsuredAge = PubFun.calInterval(tSSRS.GetText(i,
//								1), mLBPOContSchema.getPolApplyDate(), "Y");
//						logger.debug("newInsuredAge=" + newInsuredAge);
//						if (tSSRS.GetText(i, 2).compareTo(
//								mLBPOContSchema.getPolApplyDate()) > 0
//								|| tSSRS.GetText(i, 3).compareTo(
//										mLBPOContSchema.getPolApplyDate()) < 0) {
//							CError tError = new CError();
//							tError.moduleName = "ContBL";
//							tError.functionName = "checkCont";
//							tError.errorMessage = "投保日期超出险种"
//									+ tSSRS.GetText(i, 8) + "的在售期间！";
//							this.mErrors.addOneError(tError);
//							return false;
//						}
//						logger.debug("tSSRS.GetText(i, 4)="
//								+ tSSRS.GetText(i, 4) + ";tSSRS.GetText(i, 5)="
//								+ tSSRS.GetText(i, 5));
//						if (Integer.parseInt(tSSRS.GetText(i, 4)) > newAppntAge
//								|| Integer.parseInt(tSSRS.GetText(i, 5)) < newAppntAge) {
//							CError tError = new CError();
//							tError.moduleName = "ContBL";
//							tError.functionName = "checkCont";
//							tError.errorMessage = "投保人年龄超出险种"
//									+ tSSRS.GetText(i, 8) + "允许的范围！";
//							this.mErrors.addOneError(tError);
//							return false;
//						}
//						if (Integer.parseInt(tSSRS.GetText(i, 6)) > newInsuredAge
//								|| Integer.parseInt(tSSRS.GetText(i, 7)) < newInsuredAge) {
//							CError tError = new CError();
//							tError.moduleName = "ContBL";
//							tError.functionName = "checkCont";
//							tError.errorMessage = "被保人年龄超出险种"
//									+ tSSRS.GetText(i, 8) + "允许的范围！";
//							this.mErrors.addOneError(tError);
//							return false;
//						}
//					}
//				}
//			}
//			// 若修改了合同的管理机构，执行相关校验。
//			if (!mOldLBPOCont.getManageCom().equals(
//					mLBPOContSchema.getManageCom())) {
//				if (!this.checkInputManageCom()) {
//					return false;
//				}
//			}
//		}
//		return true;
//	}

}
