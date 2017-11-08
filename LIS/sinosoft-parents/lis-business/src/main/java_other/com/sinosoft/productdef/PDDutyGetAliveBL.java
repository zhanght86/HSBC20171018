

/**
 * <p>Title: PDDutyGetAlive</p>
 * <p>Description: 责任给付生存</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-16
 */

package com.sinosoft.productdef;

import java.util.ArrayList;
import java.util.Hashtable;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.sys.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

public class PDDutyGetAliveBL  implements BusinessService{
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
	private MMap map = new MMap();
	
	private VData mMaps = new VData();

	private TransferData mTransferData;
	
	private String mGetDutyCode2;
	private String mGetDutyCode;
	
	private java.util.ArrayList mList;
	private String mGetDutyKind;
	private String mDutyType2;
	private RiskState mRiskState;
	
	 private String mCalCode = "";
	 private String mCalCodeType = "";
	 private String mTableName = "";
	 private String mRiskCode = "";
	 
	public PDDutyGetAliveBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		
		try
		{
			if(!getInputData(cInputData, cOperate))
			{
				return false;
			}
			
			if (!check()) {
				return false;
			}
			
			if(!deal())
			{
				return false;
			}

			prepareData();
			
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(mMaps, "")) {
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
		catch(Exception ex)
		{
			this.mErrors.addOneError(ex.getMessage());
			ex.printStackTrace();
			return false;
		}
		//String tRiskCode = (String) ((TransferData) cInputData.getObjectByObjectName("TransferData", 0)).getValueByName("RiskCode");
		//mRiskState.setState(tRiskCode, "理赔业务控制->保障责任赔付明细","1");
		RiskState.setState(this.mRiskCode, "产品条款定义->责任生存给付信息", "1");
		return true;
	}
	
	private boolean deal() throws Exception
	{
		CommonBase commonBase = new CommonBase();
		if(!commonBase.submitData(this.mInputData, this.mOperate))
		{
			this.mErrors.addOneError("PDDutyGetAliveBL.submitData处理失败，"
					+ commonBase.mErrors.getFirstError());
			return false;
		}
		VData tResult = commonBase.getResult();
		int count = tResult.size();
		//for(int i = 0; i < count; i++)
		{
			//MMap tMap = (MMap)(((VData)tResult.get(i)).getObjectByObjectName("MMap", 0));
			MMap tMap = (MMap)(tResult).getObjectByObjectName("MMap", 0);
			this.mMaps.add(tMap);
		}
//		
//		this.mMaps.add(this.map);
		//this.mResult.add(commonBase.getResult());
		
		//  更新PD_DutyLibMap
		if(this.mGetDutyCode2.equals(""))
		{
			if(this.mOperate.equals("del"))
			{
				deleteMap();
			}
		}
		else
		{
			if(this.mOperate.equals("del"))
			{
				deleteMap();
			}
			else
			{
				String sql = "select dutylibcode,id from PD_DutyLibMap where concdutycode = '" + this.mGetDutyCode + "'"
							+ " and dutytype = '" + this.mGetDutyKind + "' and standbyflag3 = " + mDutyType2;
				ExeSQL exec = new ExeSQL();
				SSRS ssrs = exec.execSQL(sql);
				if(ssrs.getMaxRow() == 1)
				{
					if(!(ssrs.GetText(1, 1).equals(this.mGetDutyCode2)))
					{
						String update = "update PD_DutyLibMap set dutylibcode = '" + this.mGetDutyCode2 + "' where id = '" + ssrs.GetText(1, 2) + "'";
						this.map.put(update, "");
					}
				}
				else if(ssrs.getMaxRow() == 0)
				{
					PDGetMaxNo tPDGetMaxNo = new PDGetMaxNo();
					String maxNo = tPDGetMaxNo.getMaxNo("PDLibTableNo","PD_DutyLibMap");
					if(maxNo == null)
					{
						this.mErrors.addOneError(tPDGetMaxNo.mErrors.getFirstError());
						return false;
					}
					
					PD_DutyLibMapSchema tPD_DutyLibMapSchema = new PD_DutyLibMapSchema();
					tPD_DutyLibMapSchema.setId(maxNo);
					tPD_DutyLibMapSchema.setConcDutyCode(this.mGetDutyCode);
					tPD_DutyLibMapSchema.setDutyType(this.mGetDutyKind);
					tPD_DutyLibMapSchema.setSubKind("0");
					tPD_DutyLibMapSchema.setDutyLibCode(this.mGetDutyCode2);
					tPD_DutyLibMapSchema.setOperator(this.mGlobalInput.Operator);
					tPD_DutyLibMapSchema.setMakeDate(PubFun.getCurrentDate());
					tPD_DutyLibMapSchema.setMakeTime(PubFun.getCurrentTime());
					tPD_DutyLibMapSchema.setModifyDate(PubFun.getCurrentDate());
					tPD_DutyLibMapSchema.setModifyTime(PubFun.getCurrentTime());
					tPD_DutyLibMapSchema.setStandbyflag3(mDutyType2);
					
					this.map.put(tPD_DutyLibMapSchema, "INSERT");
				}
			}
		}
		
		return true;
	}
	
	private void deleteMap() {
		String sql = "select id from PD_DutyLibMap where concdutycode = '" + this.mGetDutyCode + "'"
					+ " and dutytype = '" + this.mGetDutyKind + "' and standbyflag3 = " + mDutyType2;
		ExeSQL exec = new ExeSQL();
		SSRS ssrs = exec.execSQL(sql);
		if(ssrs.getMaxRow() == 1)
		{
			String update = "delete from PD_DutyLibMap where id = '" + ssrs.GetText(1, 1) + "'";
			this.map.put(update, "");
		}
	}
	
	private boolean getInputData(VData cInputData, String cOperator) throws Exception
	{
		this.mInputData = cInputData;
		this.mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
		this.mGetDutyCode2 = (String)mTransferData.getValueByName("getDutyCode2");
		this.mGetDutyCode = (String)mTransferData.getValueByName("getDutyCode");
		this.mOperate = cOperator;
		this.mGlobalInput = (GlobalInput)cInputData.getObjectByObjectName("GlobalInput", 0);
		//this.mList = (java.util.ArrayList)mTransferData.getValueByName("list");
		this.mDutyType2 = (String)mTransferData.getValueByName("dutyType2");
		
		String tTableName = (String)mTransferData.getValueByName("tableName");
		this.mGetDutyKind = (String)mTransferData.getValueByName("GETDUTYKIND");
		//getDutyKind(tTableName, "GetDutyKind");
		
		
		this.mRiskCode  = (String)mTransferData.getValueByName("RiskCode");
 		Hashtable tHashtable = new Hashtable();
 		String tGetDutyCode = (String)mTransferData.getValueByName("getDutyCode");
 		tHashtable.put("GetDutyCode", tGetDutyCode);
 		this.mCalCodeType = (String)mTransferData.getValueByName("CalCodeType");
 		String tSQL_RiskName = "select getdutyname from pd_lmdutyget where getdutycode='"+tGetDutyCode+"'";
 		String tGetDutyName = (new ExeSQL()).getOneValue(tSQL_RiskName);
 		tHashtable.put("GetDutyName",tGetDutyName );
 		mTransferData.setNameAndValue("Hashtable", tHashtable);
 		//tongmeng 2011-07-13 modify
 		String tCalCode = mTransferData.getValueByName("CALCODE")==null?"":(String)mTransferData.getValueByName("CALCODE");
 		if(tCalCode.equals(""))
 		{
 			tCalCode = PubFun1.CreateRuleCalCode("PD",mCalCodeType);
 			mTransferData.removeByName("CALCODE");
 			mTransferData.setNameAndValue("CALCODE", tCalCode);
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
 		/*
 		 */
 		//为了复用CommonBase,在这里还是先准备个List
 		ArrayList tList = new ArrayList();
 		tList.add(tGetDutyCode);
 		tList.add((String)mTransferData.getValueByName("GETDUTYKIND"));
 		tList.add(tGetDutyName);
 		
 		tList.add((String)mTransferData.getValueByName("CALCODE"));
 		tList.add((String)mTransferData.getValueByName("GETINTV"));
 		tList.add((String)mTransferData.getValueByName("GETSTARTPERIOD"));
 		
 		tList.add((String)mTransferData.getValueByName("GETSTARTUNIT"));
 		tList.add((String)mTransferData.getValueByName("STARTDATECALREF"));
 		tList.add((String)mTransferData.getValueByName("STARTDATECALMODE"));
 		
 		tList.add((String)mTransferData.getValueByName("GETENDPERIOD"));
 		tList.add((String)mTransferData.getValueByName("GETENDUNIT"));
 		
 		tList.add((String)mTransferData.getValueByName("ENDDATECALREF"));
 		tList.add((String)mTransferData.getValueByName("ENDDATECALMODE"));

 		tList.add((String)mTransferData.getValueByName("AFTERGET"));
 		tList.add((String)mTransferData.getValueByName("GETACTIONTYPE"));
 		
 		tList.add((String)mTransferData.getValueByName("URGEGETFLAG"));
 		tList.add((String)mTransferData.getValueByName("MAXGETCOUNTTYPE"));
 		tList.add((String)mTransferData.getValueByName("NeedReCompute"));
 		
 		mTransferData.setNameAndValue("list", tList);
		
		
		return true;
	}
	
	private String getDutyKind(String tTableName, String tFieldCode)
	{
		String sql = "select p.r from (select rownum r,t.fieldcode from (select fieldcode from Pd_Basefield where tablecode = upper('" + tTableName + "') and isdisplay = '1' order by displayorder) t) p"
			+ " where p.fieldcode = upper('" + tFieldCode +"')";
		int index = Integer.parseInt(new ExeSQL().getOneValue(sql));
		
		return (String)this.mList.get(index - 1);
	}
	
	private boolean prepareData() throws Exception 
	{
		
		
		return true;
	}

	private boolean check() {
		return true;
	}

	public static void main(String[] args) {
		
		try
		{
//			System.out.println(new ExeSQL().getOneValue("select * from ldmaxno where notype= 'PD_DutyLibMap' and nolimit = '2' "));
//			System.out.println(new ExeSQL().getOneValue("select * from ldmaxno where notype= 'PD_DutyLibMap' and nolimit = '0' "));
//			PDDutyGetAliveBL tPDDutyGetAliveBL = new PDDutyGetAliveBL();
//			String maxNo = tPDDutyGetAliveBL.getMaxNo("PD_DutyLibMap", "0");
//			System.out.println(maxNo);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	 public CErrors getErrors() {
			// TODO Auto-generated method stub
			return mErrors;
		}

		public VData getResult() {
			// TODO Auto-generated method stub
			return this.mResult;
		}
		
}
