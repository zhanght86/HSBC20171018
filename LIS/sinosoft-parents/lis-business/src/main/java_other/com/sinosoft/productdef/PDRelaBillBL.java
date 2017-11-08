

/**
 * <p>Title: PDRelaBill</p>
 * <p>Description: 责任给付账单关联</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-16
 */

package com.sinosoft.productdef;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.sys.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

public class PDRelaBillBL implements BusinessService {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private VData mResult1 = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate;
	/** 业务处理相关变量 */
	private MMap map = new MMap();

	/** 前台页面操作的表名，可能为空 */
	private String mTableName;

	private TransferData mTransferData = new TransferData();

	/** 业务处理相关变量 */
	// private $tableName$Schema m$tableName$Schema=new $tableName$Schema();
	private java.text.SimpleDateFormat tempDate = new java.text.SimpleDateFormat(
			"yyyy-MM-dd");

	private String currDate = tempDate.format(new java.util.Date());

	private java.text.SimpleDateFormat tempTimeFormat = new java.text.SimpleDateFormat(
			"hh:mm:ss");

	private String currTime = tempTimeFormat.format(new java.util.Date());
	private ExeSQL exec = new ExeSQL();

	private Schema mSchema = null;
	private PD_LMDutyGetFeeRelaSet mSet = new PD_LMDutyGetFeeRelaSet();
	private Object mObject = null;
	private String mIsSubmitData = "1";
	private String mGetDutyCode ="";
	private String mGetDutyKind ="";
	private String mFeeCode ="";
	private RiskState mRiskState;
	
	private String mCalCodeType = "";
	private String mcOperate = "";
	public PDRelaBillBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mcOperate = cOperate;
	
		// if (!check()) {
		// return false;
		// }
		if (!prepareSubmitData(cInputData, cOperate)) {
			CError tError = new CError();
			tError.moduleName = "CommonBase";
			tError.functionName = "prepareSubmitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();

		if (mIsSubmitData == null || mIsSubmitData.equals("1")) {
			if (!tSubmit.submitData(mResult1, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "CommonBase";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		String tRiskCode = (String) ((TransferData) cInputData.getObjectByObjectName("TransferData", 0)).getValueByName("RiskCode");
		mRiskState.setState(tRiskCode, "理赔业务控制->关联账单定义","1");
		// mInputData = null;
		return true;
		// 进行业务处理
		// CommonBase commonBase = new CommonBase();
		// boolean result = commonBase.submitData(cInputData, cOperate);
		//
//		 if (!result) {
//		 this.mErrors.addOneError("PDRelaBillBL.submitData处理失败，"
//		 + commonBase.mErrors.getFirstError());
//		 return false;
//		 }
//		
//		 return true;
	}

	public static void main(String[] args) {
	}

	/**
	 * 准备数据，但不提交执行
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean prepareSubmitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mOperate = cOperate;
		mInputData = (VData) cInputData.clone();
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 进行业务处理
		if (!dealData(cOperate)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "$tableName$BL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败$tableName$BL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// // 记录操作轨迹
		// if ("update".equals(mOperate) || "del".equals(mOperate)) {
		// PDRiskOperationTrackBL bl = new PDRiskOperationTrackBL();
		// boolean flag = bl.submitData(mInputData, "");
		// if (flag) {
		// PD_LBRiskInfoSchema schema = (PD_LBRiskInfoSchema) bl.saveResult
		// .getObjectByObjectName("PD_LBRiskInfoSchema", 0);
		// schema.setStandByFlag50(mOperate);
		// map.put(schema, "INSERT");
		// }
		// }

		mResult1.clear();
		mResult1.addElement(map);

		// mInputData = null;
		return true;
	}

	private boolean check() {
		if(this.mcOperate.equals("save"))
		{
			for (int i = 1; i <= mSet.size(); i++) {

				PD_LMDutyGetFeeRelaDB tPDDB = new PD_LMDutyGetFeeRelaDB();
				tPDDB.setSchema(mSet.get(i));
				if(tPDDB.getInfo())
				{
					CError.buildErr(this,"该记录已存在,不允许保存!");
					return false;
				}
				
			}
		}
//		Class[] parameterC = new Class[1]; // 调用方法的参数数组
//		Object[] parameterO = new Object[1]; // 调用方法的对象数组
//		String keysSelect = "select fieldcode,isprimary from PD_BASEFIELD where isdisplay = 1 and tablecode = upper('"
//				+ this.mTableName + "') order by displayorder";
//		SSRS ssrs = exec.execSQL(keysSelect);
//
//		if (ssrs != null) {
//			// for (int i = 1; i <= ssrs.MaxRow; i++) {
//			// if (ssrs.GetText(i, 2).equals("1")) {
//			// this.mSchema.setV(ssrs.GetText(i, 1), String.valueOf(mList1
//			// .get(i - 1)));
//			// }
//			// }
//
//			try {
//				parameterC[0] = mSchema.getClass();
//				Method m = mObject.getClass()
//						.getMethod("setSchema", parameterC);
//				parameterO[0] = mSchema;
//				m.invoke(mObject, parameterO);
//
//				m = mObject.getClass().getMethod("getInfo", null);
//				Boolean b = (Boolean) m.invoke(mObject, null);
//				if (b.booleanValue()) {
//					m = mObject.getClass().getMethod("getMakeDate", null);
//					String tMakeDate = (String) m.invoke(mObject, null);
//					this.mSchema.setV("MakeDate", tMakeDate);
//					m = mObject.getClass().getMethod("getMakeTime", null);
//					String tMakeTime = (String) m.invoke(mObject, null);
//					this.mSchema.setV("MakeTime", tMakeTime);
//					return true;
//				} else {
//					return false;
//				}
//			} catch (Exception ex) {
//				System.out.println(ex.toString());
//				return false;
//			}
//		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData(String cOperate) {
		boolean tReturn = false;
		System.out.println("PDRelaBillBL begin...");
		System.out.println(" operator : " + cOperate);
		if(cOperate.equals("del"))
		{
			this.map.put(this.mSet, "DELETE");
			this.mResult.add(0,"");
			
			return true;
		}
		else
		{
			if (insert()) {
				return true;
			}
		}
		return tReturn;
	}

	private boolean insert() {
		if (!check()) {
			this.mErrors.addOneError("数据库中存在该记录，不能再重复增加");
			return false;
		}		
		ExeSQL exeSQL = new ExeSQL();
//		String tsql = "select getdutyname from PD_LMDutyGetClm where getdutycode='"+(String) mTransferData.getValueByName("GetDutyCode")+"' and rownum<2";
//		SSRS tssrs = exeSQL.execSQL(tsql);
//		String getDutyName = "";
//		 if(tssrs!=null && tssrs.MaxRow>0){
//			 getDutyName = tssrs.GetText(1, 1);
//		 }
		PD_LMDutyGetFeeRelaSet tPDSet = new PD_LMDutyGetFeeRelaSet();
		for (int i = 1; i <= mSet.size(); i++) {

			PD_LMDutyGetFeeRelaSchema tPDSchema = new PD_LMDutyGetFeeRelaSchema();
			
			tPDSchema = (PD_LMDutyGetFeeRelaSchema)mSet.get(i);
			mGetDutyCode = tPDSchema.getGetDutyCode();
			mGetDutyKind = tPDSchema.getGetDutyKind();
			mFeeCode = tPDSchema.getClmFeeCode();
			String sql = "select fieldcode,FIELDINITVALUE from PD_BASEFIELD where isdisplay = 0 and tablecode = upper('"
					+ this.mTableName + "') order by displayorder ";
			SSRS ssrs = exec.execSQL(sql);
			int rowCount = ssrs.MaxRow;
			for (int j = 1; j <= rowCount; j++) {
				tPDSchema.setV(ssrs.GetText(j, 1), ssrs.GetText(j, 2));
			}
			String tCalType = tPDSchema.getClmFeeCalType();
			String tCalCode = tPDSchema.getClmFeeCalCode();
			if(tCalType!=null&&!tCalType.equals("03"))
			{
				if(tCalCode!=null&&!tCalCode.equals(""))
				{
					CError.buildErr(this,"费用计算方式不是公式,不能录入计算编码");
					return false;
				}
				this.mResult.add(0,"");
			}
			else
			{
				if(tCalCode.equals(""))
				{
					tCalCode = PubFun1.CreateRuleCalCode("PD",mCalCodeType);
					tPDSchema.setClmFeeCalCode(tCalCode);
					this.mResult.add(0,tCalCode);
				}
				
				else
				{
					//校验算法类型和算法编码的关系
					if((tCalCode.substring(0,2).toUpperCase().equals("RU")&&mCalCodeType.equals("N"))
					||!tCalCode.substring(0,2).toUpperCase().equals("RU")&&mCalCodeType.equals("Y"))
					{
						CError.buildErr(this, "算法编码与算法类型不一致,请删除后重新添加!");
						return false;
					}
					this.mResult.add(0,tCalCode);
				}
			}
			//tPDSchema.setGetDutyName(getDutyName);
			tPDSchema.setV("Operator", this.mGlobalInput.Operator);
			tPDSchema.setV("MakeDate", this.currDate);
			tPDSchema.setV("MakeTime", this.currTime);
			tPDSchema.setV("ModifyDate", this.currDate);
			tPDSchema.setV("ModifyTime", this.currTime);
			tPDSet.add(tPDSchema);
		}
//		StringBuffer tsb = new StringBuffer();
//		tsb.append( "delete from PD_LMDutyGetFeeRela where GetDutyCode ='")
//		.append(mGetDutyCode)
//		.append("' and GetDutyKind ='")
//		.append(mGetDutyKind)
//		.append("' and clmFeeCode ")
//		//.append("' and substr(clmFeeCode,1,1) = '")
//		//.append(mFeeCode.substring(0, 1))
//		.append("'")
//		;

		//String sql = tsb.toString();
		//this.map.put(sql, "DELETE");
		this.map.put(tPDSet, "DELETE&INSERT");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		 mCalCodeType = (String)mTransferData.getValueByName("CalCodeType");
		 
		mTableName = (String) mTransferData.getValueByName("tableName");
		this.mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		this.mIsSubmitData = (String) mTransferData
				.getValueByName("IsSubmitData");
		mSet = (PD_LMDutyGetFeeRelaSet) mTransferData.getValueByName("PD_LMDutyGetFeeRelaSet");
		if(mSet == null)  return false;
		try {

			Class tDBClass = Class.forName("com.sinosoft.lis.db." + mTableName
					+ "DB");
			mObject = (Object) tDBClass.newInstance();

		} catch (Exception ex) {
			System.out.println(ex.toString());
			return false;
		}
		return true;
	}
	public CErrors getErrors() {
		return this.mErrors;
	}


	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return
	 */
	public VData getResult() {
		return this.mResult;
	}

}
