/**
 * Copyright 2010 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpAddressDB;
import com.sinosoft.lis.db.LDGrpDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGrpAddressSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * 子公司类
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 根据操作类型不同，对数据进行校验、准备处理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author 朱向峰
 * @version 1.0
 */
public class LCAccountInfoBL {
private static Logger logger = Logger.getLogger(LCAccountInfoBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	
	private TransferData mTransferData = new TransferData();
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();
	private MMap map = new MMap();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private LCGrpAddressSchema mLCGrpAddressSchema = new LCGrpAddressSchema();
	private LDGrpSchema mLDGrpSchema = new LDGrpSchema();

	// private String mGrpContNo = ""; //团体合同号
	// private String mContPlanCode = ""; //保险计划或默认值编码
	// private String mProposalGrpContNo = "";
	// private String mPlanType = "";
	// private String mPlanSql = "";
	// private String mPeoples3 = "";
	private String mSql = "";

	public LCAccountInfoBL() {
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
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 数据校验
		if (!checkData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAccountInfoBL";
			tError.functionName = "checkData";
			tError.errorMessage = "数据处理失败LCAccountInfoBL-->checkData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 根据业务逻辑对数据进行处理
		if (!mOperate.equals("DELETE||ACCOUNT")) {
			if (this.dealData() == false){
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCAccountInfoBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据处理失败LCAccountInfoBL-->dealData!";
				this.mErrors.addOneError(tError);
				return false;
			}
		} else {
			if (this.deleteData() == false)
				return false;
		}

		// 装配处理好的数据，准备给后台进行保存
		logger.debug("---prepareOutputData---");
		this.prepareOutputData();

		// 数据提交、保存
		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start LCAccountInfoBL Submit...");

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
		
		mInputData = null;
		return true;
	}

	/**
	 * 数据校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		boolean tReturn = true;
		//基本信息    
		String tSupCustoemrNo = (String) mTransferData.getValueByName("SupGrpNo");
	
	
		// 新增处理，修改处理
		if (this.mOperate.compareTo("DELETE||MAIN") != 0) {
	    	//子公司信息      	
	    	LDGrpSchema tLDGrpSchema = new LDGrpSchema();
	    	LCGrpAddressSchema tLCGrpAddressSchema = new LCGrpAddressSchema();
	    		    	
	    	//生成客户号
	    	String tCustomerNO = "";
	    	if ( mLDGrpSchema.getCustomerNo() == null || mLDGrpSchema.getCustomerNo().trim().equals("")) {
		    	tCustomerNO= PubFun1.CreateMaxNo("CUSTOMERNO", "SN");
				if (!tCustomerNO.equals("")) {
					tLDGrpSchema.setCustomerNo(tCustomerNO);
					//tLCGrpAppntSchema.setCustomerNo(tCustomerNO);
					tLCGrpAddressSchema.setCustomerNo(tCustomerNO);
	
				} else {
					mErrors.addOneError(new CError("客户号码生成失败"));
					return false;
				}
	    	}
	    	// ADDRESSNO 生成地址号码
			String tAddressNo = "";
			if ( this.mLCGrpAddressSchema.getAddressNo().equals("")  || this.mLCGrpAddressSchema.getAddressNo() == null ) {
				try {	
					SSRS tSSRS = new SSRS();
					String sql = "Select Case When max(AddressNo) Is Null Then '0' Else max(AddressNo) End from LCGrpAddress where CustomerNo='"
							+ tCustomerNO + "'";
					ExeSQL tExeSQL = new ExeSQL();
					tSSRS = tExeSQL.execSQL(sql);
					Integer firstinteger = Integer.valueOf(tSSRS.GetText(1, 1));
					int ttNo = firstinteger.intValue() + 1;
					Integer integer = new Integer(ttNo);
					tAddressNo = integer.toString();
					logger.debug("得到的地址码是：" + tAddressNo);
					if (!tAddressNo.equals("")) {
						tLCGrpAddressSchema.setAddressNo(tAddressNo);
					} else {
						mErrors.addOneError(new CError("客户地址号码生成失败"));
						return false;
					}
				} catch (Exception e) {
					CError tError = new CError();
					tError.moduleName = "LCAccountInfoBL";
					tError.functionName = "createAddressNo";
					tError.errorMessage = "地址码超长,生成号码失败,请先删除原来的超长地址码!";
					this.mErrors.addOneError(tError);
				}
			}else
			{
				tLCGrpAddressSchema.setAddressNo(mLCGrpAddressSchema.getAddressNo());
			}
			
	   					
	    	//二. 投保单位信息  LCGrpAppnt
			/** ldgrp
			 * 1. CUSTOMERNO 
			 * 2. GRPNAME -- tGRPNAME , supcustoemrno
			 * 3. OPERATOR、MAKEDATE、MAKETIME、MODIFYDATE、MODIFYTIME 
			 * 4. 其他: BUSINESSTYPE,  GrpNature, Peoples , Asset, Corporation, Fax
			 * */
	    	tLDGrpSchema.setGrpName(mLDGrpSchema.getGrpName());
	    	//关键就是这个总公司代码
	    	tLDGrpSchema.setSupCustoemrNo(tSupCustoemrNo);
	    	tLDGrpSchema.setOperator(mGlobalInput.Operator);
	    	tLDGrpSchema.setMakeDate(theCurrentDate);
	    	tLDGrpSchema.setMakeTime(theCurrentTime);
	    	tLDGrpSchema.setModifyDate(theCurrentDate);
	    	tLDGrpSchema.setModifyTime(theCurrentTime);    	
	    	//其他信息
	    	tLDGrpSchema.setBusinessType( this.mLDGrpSchema.getBusinessType() );
	    	tLDGrpSchema.setGrpNature( this.mLDGrpSchema.getGrpNature() );
	    	tLDGrpSchema.setPeoples(this.mLDGrpSchema.getPeoples());
	    	tLDGrpSchema.setAsset(this.mLDGrpSchema.getAsset());
	    	tLDGrpSchema.setCorporation(this.mLDGrpSchema.getCorporation());
	    	tLDGrpSchema.setFax(this.mLDGrpSchema.getFax());

			if (tLDGrpSchema != null) {
				map.put(tLDGrpSchema, "INSERT");
			}
			
			/**LCGrpAddress
			 * 1. CUSTOMERNO 、ADDRESSNO 已生成并赋值
			 * 2. OPERATOR、MAKEDATE、MAKETIME、MODIFYDATE、MODIFYTIME 
			 * 3. 其他信息  
			 */
			//2. 关键信息
	    	//3. 
	    	tLCGrpAddressSchema.setOperator(mGlobalInput.Operator);
	    	tLCGrpAddressSchema.setMakeDate(theCurrentDate);
	    	tLCGrpAddressSchema.setMakeTime(theCurrentTime);
	    	tLCGrpAddressSchema.setModifyDate(theCurrentDate);
	    	tLCGrpAddressSchema.setModifyTime(theCurrentTime); 
	    	//4. 其他信息
	    	tLCGrpAddressSchema.setGrpAddress( this.mLCGrpAddressSchema.getGrpAddress() );
	    	tLCGrpAddressSchema.setGrpZipCode( this.mLCGrpAddressSchema.getGrpZipCode() );
	    	tLCGrpAddressSchema.setLinkMan1( this.mLCGrpAddressSchema.getLinkMan1() );
	    	tLCGrpAddressSchema.setDepartment1( this.mLCGrpAddressSchema.getDepartment1() );
	    	tLCGrpAddressSchema.setPhone1( this.mLCGrpAddressSchema.getPhone1() );
	    	tLCGrpAddressSchema.setE_Mail1( this.mLCGrpAddressSchema.getE_Mail1() );
	    	tLCGrpAddressSchema.setLinkMan2( this.mLCGrpAddressSchema.getGrpAddress() );
	    	tLCGrpAddressSchema.setDepartment2( this.mLCGrpAddressSchema.getDepartment2() );
	    	tLCGrpAddressSchema.setPhone2( this.mLCGrpAddressSchema.getPhone2() );
	    	tLCGrpAddressSchema.setE_Mail2( this.mLCGrpAddressSchema.getE_Mail2() );
	    	
	    	
			if (tLCGrpAddressSchema != null) {
				map.put(tLCGrpAddressSchema, "INSERT");
			}
			
//			if ( true ) {
//				mErrors.addOneError(new CError("调试退出"));
//				return false;			
//			}
			
			return true;
		}

		tReturn = true;
		return tReturn;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		this.mInputData = cInputData;
		this.mGlobalInput.setSchema((GlobalInput) cInputData
				.getObjectByObjectName("GlobalInput", 0));
		this.mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
		this.mLCGrpAddressSchema.setSchema((LCGrpAddressSchema) cInputData
				.getObjectByObjectName("LCGrpAddressSchema", 0));
		this.mLDGrpSchema.setSchema((LDGrpSchema) cInputData
				.getObjectByObjectName("LDGrpSchema", 0));
		// this.mProposalGrpContNo = (String) cInputData.get(2);
		// this.mGrpContNo = (String) cInputData.get(3);
		// this.mContPlanCode = (String) cInputData.get(4);
		// this.mPlanType = (String) cInputData.get(5);
		// this.mPlanSql = (String) cInputData.get(6);
		// this.mPeoples3 = (String) cInputData.get(7);

		return true;
	}

	/**
	 * 删除传入的子公司
	 * 
	 * @param: 无
	 * @return: void
	 */
	private boolean deleteData() {
		LDGrpDB tLDGrpDB = new LDGrpDB();
		LDGrpSchema tLDGrpSchema = new LDGrpSchema();
		LCGrpAddressDB tLCGrpAddressDB = new LCGrpAddressDB();
		LCGrpAddressSchema tLCGrpAddressSchema = new LCGrpAddressSchema(); 
		//LDGrpSet tLDGrpSet = new LDGrpSet();
		
		String tCustomerNo = (String) this.mTransferData.getValueByName( "CustomerNo" );
		String tAddressNo = (String) this.mTransferData.getValueByName( "AddressNo" );
		
		tLDGrpSchema.setCustomerNo( tCustomerNo );
		tLDGrpDB.setSchema(tLDGrpSchema);
		
		tLCGrpAddressSchema.setCustomerNo(tCustomerNo);
		tLCGrpAddressSchema.setAddressNo(tAddressNo);
		tLCGrpAddressDB.setSchema(tLCGrpAddressSchema);
		
		//tLDGrpSet = tLDGrpDB.query();
			
		if ( tLDGrpDB.getInfo() && tLCGrpAddressDB.getInfo() ) {
			
			String wherepart1 = "customerno ='" + tCustomerNo + "'";			
			String wherepart2 = "customerno ='" + tCustomerNo + "' and AddressNo = '"
			+ tAddressNo + "'";			

			map.put("delete from LDGrp where " + wherepart1, "DELETE");
			map.put("delete from LCGrpAddress where "+wherepart2,"DELETE");
		}else if (tLDGrpDB.mErrors.needDealError()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCAccountInfoBL";
				tError.functionName = "deleteData";
				tError.errorMessage = "删除子公司信息时失败!";
				this.mErrors.addOneError(tError);
				return false;			
		}
			
		return true;
	}


	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: void
	 */
	private void prepareOutputData() {
		mResult.clear();
//		mResult.add(mLCGrpPolSet);
//		mResult.add(tTransferData);
		mInputData.clear();
		mInputData.add(map);

	}

	/**
	 * 得到处理后的结果集
	 * 
	 * @return 结果集
	 */

	public VData getResult() {
		return mResult;
	}
}
