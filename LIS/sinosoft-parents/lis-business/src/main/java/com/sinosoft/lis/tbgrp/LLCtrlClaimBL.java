/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLDutyCtrlDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLDutyCtrlIndexSchema;
import com.sinosoft.lis.schema.LLDutyCtrlSchema;
import com.sinosoft.lis.vschema.LLDutyCtrlIndexSet;
import com.sinosoft.lis.vschema.LLDutyCtrlSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * 理赔控制
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 接收前台传入的数据，作处理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 */
public class LLCtrlClaimBL {
private static Logger logger = Logger.getLogger(LLCtrlClaimBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	private MMap map = new MMap();

	/** 数据操作字符串 */
	private String mOperate;
	private String levelflag;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LLDutyCtrlSchema mLLDutyCtrlSchema = new LLDutyCtrlSchema();

	/** 提交Set*/
	//理赔责任控制表
	private LLDutyCtrlSet mSaveLLDutyCtrlSet = new LLDutyCtrlSet();
	//理赔责任控制索引表
	private LLDutyCtrlIndexSet mLLDutyCtrlIndexSet = new LLDutyCtrlIndexSet();
	
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();
	
	public LLCtrlClaimBL() {
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
	public boolean submitData(VData cInputData, String cOperate,
			String clevelflag) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		this.levelflag = clevelflag;
		if (!getInputData(cInputData)) {
			return false;
		}
		// 进行业务处理
		if (!checkData()) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		PubSubmit tPubSubmit = new PubSubmit();

		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			return false;
		}

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
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		this.mLLDutyCtrlSchema.setSchema((LLDutyCtrlSchema) cInputData
				.getObjectByObjectName("LLDutyCtrlSchema", 0));
		if (mGlobalInput == null) {
			// @@错误处理
			CError.buildErr(this,"没有得到足够的信息！");
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
	}

	private boolean checkData() {
		// 如果是新增的时候，则需要出错处理
		if(this.mOperate.equals("INSERT||MAIN"))
		{
			LLDutyCtrlDB tLLDutyCtrlDB = new LLDutyCtrlDB();
			tLLDutyCtrlDB.setSchema(this.mLLDutyCtrlSchema);
			if(tLLDutyCtrlDB.getInfo())
			{
				CError.buildErr(this,"当前控制信息已经存在,请删除之后再新增!");
				return false;
			}
			//tongmeng 2009-03-17 add
			//为MS版本增加校验,不允许同一个控制字段在多个控制层次中出现.
			//tongmeng 2009-04-10 modify
			//只有限额不允许在多个控制层次出险.
			//String[] tFieldFilter = {"ObserveDate","Exempt","ExemptDate","TotalLimit","ClaimRate","StartPay","EndPay"};
			String[] tFieldFilter = {"TotalLimit"};
			//String[] tFieldFilterName = {"观察期","免赔额","免陪天数","总赔付限额","赔付比例","起付线","封顶线"};
			String[] tFieldFilterName = {"总赔付限额"};

			ExeSQL tExeSQL = new ExeSQL();
			for(int i=1;i<=this.mLLDutyCtrlSchema.getFieldCount();i++)
			{
				String tFieldName = mLLDutyCtrlSchema.getFieldName(i);
				String tFieldValue = mLLDutyCtrlSchema.getV(i);
				boolean tBooleanFlag = false;
				int tValueNameIndex = 0;
				for(int n=0;n<tFieldFilter.length;n++)
				{
					if(tFieldName!=null&&tFieldName.equals(tFieldFilter[n]))
					{
						if(Double.parseDouble(tFieldValue)!=-1)
						{
							tValueNameIndex = n;
							tBooleanFlag = true;
							break;
						}
					}
				}
				if(!tBooleanFlag)
				{
					continue;
				}
				
				String tSQL = "select decode(count(*),0,0,1) from lldutyctrl where 1=1 "
					        + " and "
					        + tFieldName
					        + "<>-1 "
					        + " and grpcontno='"+mLLDutyCtrlSchema.getGrpContNo()+"' ";
				logger.debug("tSQL:"+tSQL);
				String tValue = "";
				tValue = tExeSQL.getOneValue(tSQL);
				if(tValue!=null&&Integer.parseInt(tValue)>0)
				{
					CError.buildErr(this,tFieldFilterName[tValueNameIndex]+"控制规则已经存在,请修改或者删除原规则!");
					return false;
				}
			}
			
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		boolean tReturn = true;
		String StrSql = "";

		Reflections tRef = new Reflections();
		
		LLDutyCtrlSchema tLLDutyCtrlSchema = new LLDutyCtrlSchema();
		tLLDutyCtrlSchema.setSchema(this.mLLDutyCtrlSchema);
	
		
		// 新增处理
		if (this.mOperate.equals("INSERT||MAIN")) {
//			if(tLLDutyCtrlSchema.getCtrlBatchNo()!=null&&!tLLDutyCtrlSchema.getCtrlBatchNo().equals(""))
//			{
//				CError.buildErr(this,"当前理赔责任控制规则已存在,请选择修改或删除操作!");
//				return false;
//			}
			tLLDutyCtrlSchema.setCtrlBatchNo("");
			
			tLLDutyCtrlSchema.setOperator(this.mGlobalInput.Operator);
			tLLDutyCtrlSchema.setMakeDate(this.mCurrentDate);
			tLLDutyCtrlSchema.setMakeTime(this.mCurrentTime);
			tLLDutyCtrlSchema.setModifyDate(this.mCurrentDate);
			tLLDutyCtrlSchema.setModifyTime(this.mCurrentTime);
			//处理控制级别!
			//CtrlLevel
			int tCtrlLevel = 0;
			tCtrlLevel = this.getCtrlLevel(tLLDutyCtrlSchema);
			if(tCtrlLevel==-1)
			{
				CError.buildErr(this,"控制级别计算出错!请确认控制层次录入信息是否有误!");
				return false;
			}
			
			if(tLLDutyCtrlSchema.getTotalLimit()!=-1&&tCtrlLevel!=1&&tCtrlLevel!=4&&tCtrlLevel!=5)
			{
				CError.buildErr(this,"限额只能录入在团单,保障计划,险种三层!");
				return false;
			}
//			if(true)
//			{
//			CError.buildErr(this,String.valueOf(tCtrlLevel));
//			return false;
//			}
			tLLDutyCtrlSchema.setCtrlLevel(tCtrlLevel);
			
			//生成控制流水号
			String tCtrlBatchNo = PubFun1.CreateMaxNo("CTRLBATCHNO",20);
			tLLDutyCtrlSchema.setCtrlBatchNo(tCtrlBatchNo);
			
			//准备控制索引表
			LLDutyCtrlIndexSet tLLDutyCtrlIndexSet = new LLDutyCtrlIndexSet();
			if(!this.prepareLLDutyCtrlIndex(tLLDutyCtrlIndexSet, tLLDutyCtrlSchema))
			{
				CError.buildErr(this,"准备控制索引表出错!请确认录入信息是否有误!");
				return false;
			}
			if(tLLDutyCtrlIndexSet==null||tLLDutyCtrlIndexSet.size()<=0)
			{
				CError.buildErr(this,"准备控制索引表无数据!");
				return false;
			}
			map.put(tLLDutyCtrlSchema, "INSERT");
			map.put(tLLDutyCtrlIndexSet, "INSERT");
			return true;
			
		}
		// 由于删除操作可能没有multine数据，因此需要一些特殊参数，并且根据参数查询要删除的数据
		if (this.mOperate.equals("DELETE||MAIN")) {
			String tBatchNo = tLLDutyCtrlSchema.getCtrlBatchNo();
			if(tBatchNo==null||tBatchNo.equals(""))
			{
				CError.buildErr(this,"不存在要删除的控制信息!");
				return false;
			}
			String tSQL = "select * from LLDutyCtrl where grpcontno='"+tLLDutyCtrlSchema.getGrpContNo()+"' "
			            + " and ctrlbatchno='"+tLLDutyCtrlSchema.getCtrlBatchNo()+"' ";
			
			LLDutyCtrlSet tLLDutyCtrlSet = new LLDutyCtrlSet();
			LLDutyCtrlDB tLLDutyCtrlDB = new LLDutyCtrlDB();
			tLLDutyCtrlSet = tLLDutyCtrlDB.executeQuery(tSQL);
			if(tLLDutyCtrlSet.size()>0)
			{
				if(tLLDutyCtrlSet.size()!=1)
				{
					CError.buildErr(this,"理赔责任控制信息错误,请联系信息人员!");
					return false;
				}
				
				map.put(tLLDutyCtrlSet.get(1), "DELETE");
				String tSQL_Delete = "delete from LLDutyCtrlIndex where grpcontno='"+tLLDutyCtrlSchema.getGrpContNo()+"' "
			            		   + " and ctrlbatchno='"+tLLDutyCtrlSchema.getCtrlBatchNo()+"' ";
				map.put(tSQL_Delete, "DELETE");
			}
			else
			{
				CError.buildErr(this,"不存在要删除的控制信息!");
				return false;
			}
		}

		if (this.mOperate.equals("UPDATE||MAIN")) {

		}
		return true;
	}

	/**
	 * 准备控制索引表
	 * @param tLLDutyCtrlIndexSet
	 * @return
	 */
	private boolean prepareLLDutyCtrlIndex(LLDutyCtrlIndexSet tResLLDutyCtrlIndexSet,LLDutyCtrlSchema tLLDutyCtrlSchema)
	{
		boolean tResFlag = true;
		//按照tLLDutyCtrlSchema的数值初始化LLDutyCtrlIndex
		//主要是准备给付责任层的数据,按照保障计划编码或者险种等控制.
		
		LLDutyCtrlIndexSet tempLLDutyCtrlIndexSet = new LLDutyCtrlIndexSet();
		
		//生成理赔责任控制索引表结构
		tempLLDutyCtrlIndexSet.add(this.getLLDutyCtrlIndexStruct(tLLDutyCtrlSchema));
		
		//最后统一初始化一下其他相关信息
		for(int i=1;i<=tempLLDutyCtrlIndexSet.size();i++)
		{
			LLDutyCtrlIndexSchema tLLDutyCtrlIndexSchema = new LLDutyCtrlIndexSchema();
			tLLDutyCtrlIndexSchema = tempLLDutyCtrlIndexSet.get(i);
			tLLDutyCtrlIndexSchema.setGrpContNo(tLLDutyCtrlSchema.getGrpContNo());
			tLLDutyCtrlIndexSchema.setCtrlBatchNo(tLLDutyCtrlSchema.getCtrlBatchNo());
			tLLDutyCtrlIndexSchema.setCtrlLevel(tLLDutyCtrlSchema.getCtrlLevel());
//			if(!tLLDutyCtrlSchema.getOccupationType().equals("*"))
			{
				tLLDutyCtrlIndexSchema.setOccupationType(tLLDutyCtrlSchema.getOccupationType());
			}
			tLLDutyCtrlIndexSchema.setCtrlProp(tLLDutyCtrlSchema.getCtrlProp());
//			if(!tLLDutyCtrlSchema.getContPlanCode().equals("*"))
			{
				//不为*的才做赋值
				tLLDutyCtrlIndexSchema.setContPlanCode(tLLDutyCtrlSchema.getContPlanCode());
			}
//			if(!tLLDutyCtrlSchema.getRiskCode().equals("*"))
			{
				//不为*的才做赋值
				tLLDutyCtrlIndexSchema.setRiskCode(tLLDutyCtrlSchema.getRiskCode());
			}
			if(!tLLDutyCtrlSchema.getDutyCode().equals("*"))
			{
				//不为*的才做赋值
				tLLDutyCtrlIndexSchema.setDutyCode(tLLDutyCtrlSchema.getDutyCode());
			}
//			if(!tLLDutyCtrlSchema.getGetDutyCode().equals("*"))
//			{
//				//不为*的才做赋值
//				tLLDutyCtrlIndexSchema.setGetDutyCode(tLLDutyCtrlSchema.getGetDutyCode());
//			}
			tLLDutyCtrlIndexSchema.setOperator(this.mGlobalInput.Operator);
			tLLDutyCtrlIndexSchema.setMakeDate(this.mCurrentDate);
			tLLDutyCtrlIndexSchema.setMakeTime(this.mCurrentTime);
			tLLDutyCtrlIndexSchema.setModifyDate(this.mCurrentDate);
			tLLDutyCtrlIndexSchema.setModifyTime(this.mCurrentTime);
			
			//处理标记,0,无,1,有
			//ObserveDateFlag
			if(tLLDutyCtrlSchema.getObserveDate()!=-1)
			{
				tLLDutyCtrlIndexSchema.setObserveDateFlag("1");
			}
			else
			{
				tLLDutyCtrlIndexSchema.setObserveDateFlag("0");
			}
			//ExemptFlag
			if(tLLDutyCtrlSchema.getExempt()!=-1)
			{
				tLLDutyCtrlIndexSchema.setExemptFlag("1");
			}
			else
			{
				tLLDutyCtrlIndexSchema.setExemptFlag("0");
			}
			//ExemptDateFlag
			if(tLLDutyCtrlSchema.getExemptDate()!=-1)
			{
				tLLDutyCtrlIndexSchema.setExemptDateFlag("1");
			}
			else
			{
				tLLDutyCtrlIndexSchema.setExemptDateFlag("0");
			}
			//TotalLimitFlag
			if(tLLDutyCtrlSchema.getTotalLimit()!=-1)
			{
				tLLDutyCtrlIndexSchema.setTotalLimitFlag("1");
			}
			else
			{
				tLLDutyCtrlIndexSchema.setTotalLimitFlag("0");
			}
			//ClaimRateFlag
			if(tLLDutyCtrlSchema.getClaimRate()!=-1)
			{
				tLLDutyCtrlIndexSchema.setClaimRateFlag("1");
			}
			else
			{
				tLLDutyCtrlIndexSchema.setClaimRateFlag("0");
			}
			//StartPayFlag
			if(tLLDutyCtrlSchema.getStartPay()!=-1)
			{
				tLLDutyCtrlIndexSchema.setStartPayFlag("1");
			}
			else
			{
				tLLDutyCtrlIndexSchema.setStartPayFlag("0");
			}
			//EndPayFlag
			if(tLLDutyCtrlSchema.getEndPay()!=-1)
			{
				tLLDutyCtrlIndexSchema.setEndPayFlag("1");
			}
			else
			{
				tLLDutyCtrlIndexSchema.setEndPayFlag("0");
			}
			//tResLLDutyCtrlIndexSet.add(tLLDutyCtrlIndexSchema);
			
			
		}
		tResLLDutyCtrlIndexSet.clear();
		tResLLDutyCtrlIndexSet.add(tempLLDutyCtrlIndexSet);
		
		return tResFlag;
	}
	
	private LLDutyCtrlIndexSet getLLDutyCtrlIndexStruct(LLDutyCtrlSchema tLLDutyCtrlSchema)
	{
		LLDutyCtrlIndexSet tResLLDutyCtrlIndexSet = new LLDutyCtrlIndexSet();
		int tCtrlLevel = tLLDutyCtrlSchema.getCtrlLevel();
		/*
		 1.	团体合同
		 2.	职业类别
	 	 //3.	控制属性(0-共享控制,1-单一控制) 控制属性不能单独做为一个层级!
	     4.	保障计划
		 5.	险种
		 6.	责任
		 7.	给付责任
		 */
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS_Get = new SSRS();
		String tSQL_Get = "";
		if(tCtrlLevel == 7 )
		{
			//已经到给付责任层了.不需要处理了....
			LLDutyCtrlIndexSchema tLLDutyCtrlIndexSchema = new LLDutyCtrlIndexSchema();
			tLLDutyCtrlIndexSchema.setGetDutyCode(tLLDutyCtrlSchema.getGetDutyCode());
			tResLLDutyCtrlIndexSet.add(tLLDutyCtrlIndexSchema);
		}
		else 
		{
			if(tCtrlLevel == 6 )
			{
				//获取所有dutycode下的getdutycode
				tSQL_Get = "select distinct c.getdutycode, c.getdutyname,a.dutycode "
					 	 + " from lmdutygetrela a, lmdutyget c "
					 	 + " where a.getdutycode = c.getdutycode "
					 	 + " and a.dutycode ='"+tLLDutyCtrlSchema.getDutyCode()+"' "
					 	 + " order by c.getdutycode ";
			
			}
			else if(tCtrlLevel == 5 )
			{
				//获取所有riskcode下的getdutycode
				tSQL_Get = "select distinct c.getdutycode,c.getdutyname,a.dutycode "
					     + " from lmriskduty a,lmdutygetrela b,lmdutyget c "
					     + " where " 
					     + " a.dutycode=b.dutycode and " 
					     + " b.getdutycode = c.getdutycode "
					     + " and a.riskcode='"+tLLDutyCtrlSchema.getRiskCode()+"'";
			
			}
			else if(tCtrlLevel == 4 )
			{
				//获取所有contplancode下的getdutycode
				tSQL_Get = "select distinct c.getdutycode,c.getdutyname,a.dutycode "
					     + " from lmriskduty a,lmdutygetrela b,lmdutyget c "
					     + " where " 
					     + " a.dutycode=b.dutycode and " 
					     + " b.getdutycode = c.getdutycode "
					     + " and a.riskcode in ( "
					     + " select riskcode from lccontplanrisk where grpcontno='"+tLLDutyCtrlSchema.getGrpContNo()+"' "
					     + " and contplancode = '"+tLLDutyCtrlSchema.getContPlanCode()+"' "
					     + " )" 
					     //'"+tLLDutyCtrlSchema.getRiskCode()+"'"
					     ;
			
			}
			else if(tCtrlLevel == 3 )
			{
				//暂时不允许出险==3的
			}
			else if(tCtrlLevel == 2 
					|| tCtrlLevel == 1 
					)
			{
				//获取合同下所有险种的getdutycode
				//对于职业类别层,在理赔取控制属性时,需要特殊处理.
				tSQL_Get = "select distinct c.getdutycode,c.getdutyname,a.dutycode "
					     + " from lmriskduty a,lmdutygetrela b,lmdutyget c "
					     + " where " 
					     + " a.dutycode=b.dutycode and " 
					     + " b.getdutycode = c.getdutycode "
					     + " and a.riskcode in ( "
					     + " select riskcode from lcgrppol where grpcontno='"+tLLDutyCtrlSchema.getGrpContNo()+"' "
					     + " )" 
					     //'"+tLLDutyCtrlSchema.getRiskCode()+"'"
					     ;
			}
			
			tSSRS_Get = tExeSQL.execSQL(tSQL_Get);
			for(int i=1;i<=tSSRS_Get.getMaxRow();i++)
			{
				String tGetDutyCode = tSSRS_Get.GetText(i,1);
				String tDutyCode = tSSRS_Get.GetText(i,3);
				logger.debug("i:"+i+":GetDutyCode:"+tGetDutyCode+":DutyCode:"+tDutyCode);
				LLDutyCtrlIndexSchema tLLDutyCtrlIndexSchema = new LLDutyCtrlIndexSchema();
				tLLDutyCtrlIndexSchema.setGetDutyCode(tGetDutyCode);
				tLLDutyCtrlIndexSchema.setDutyCode(tDutyCode);
				tResLLDutyCtrlIndexSet.add(tLLDutyCtrlIndexSchema);
			}
		}
			
		
		return tResLLDutyCtrlIndexSet;
	}
	
	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);

		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this,"在准备往后层处理所需要的数据时出错");
			return false;
		}
		return true;
	}

	/**
	 * 获取控制级别
	 * @param tLLDutyCtrlSchema
	 * @return
	 */
	private int getCtrlLevel(LLDutyCtrlSchema tLLDutyCtrlSchema)
	{
		int tLevel = -1;
		/*
		 1.	团体合同
		 2.	职业类别
	 	 //3.	控制属性(0-共享控制,1-单一控制) 控制属性不能单独做为一个层级!
	     4.	保障计划
		 5.	险种
		 6.	责任
		 7.	给付责任
		 */
		if(tLLDutyCtrlSchema.getGrpContNo()!=null&&!tLLDutyCtrlSchema.getGrpContNo().equals("*"))
		{
			tLevel = 1;
		}
		if(tLLDutyCtrlSchema.getOccupationType()!=null&&!tLLDutyCtrlSchema.getOccupationType().equals("*"))
		{
			tLevel = 2;
		}
//		if(tLLDutyCtrlSchema.getCtrlProp()!=null&&!tLLDutyCtrlSchema.getCtrlProp().equals("*"))
//		{
//			tLevel = 3;
//		}
		if(tLLDutyCtrlSchema.getContPlanCode()!=null&&!tLLDutyCtrlSchema.getContPlanCode().equals("*"))
		{
			tLevel = 4;
		}
		if(tLLDutyCtrlSchema.getRiskCode()!=null&&!tLLDutyCtrlSchema.getRiskCode().equals("*"))
		{
			tLevel = 5;
		}
		if(tLLDutyCtrlSchema.getDutyCode()!=null&&!tLLDutyCtrlSchema.getDutyCode().equals("*"))
		{
			tLevel = 6;
		}
		if(tLLDutyCtrlSchema.getGetDutyCode()!=null&&!tLLDutyCtrlSchema.getGetDutyCode().equals("*"))
		{
			tLevel = 7;
		}
		//控制属性不能单独做为一个层级!,如果发现只有控制属性,那么认为是错误数据
//		if(tLevel==3)
//		{
//			tLevel = -1;
//		}
		return tLevel;
	}
	/**
	 * 获取返回结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return this.mResult;
	}
}
