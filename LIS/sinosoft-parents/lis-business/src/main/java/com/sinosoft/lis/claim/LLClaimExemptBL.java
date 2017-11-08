/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.util.Vector;

import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LJAPayPersonDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LLClaimDetailDB;
import com.sinosoft.lis.db.LLExemptDB;
import com.sinosoft.lis.db.LOLoanDB;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubCalculator;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LLBalanceSchema;
import com.sinosoft.lis.schema.LLClaimDetailSchema;
import com.sinosoft.lis.schema.LLExemptSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.schema.LLReportSchema;
import com.sinosoft.lis.schema.LMDutyGetClmCalSchema;
import com.sinosoft.lis.schema.LMDutyGetClmSchema;
import com.sinosoft.lis.schema.LOLoanSchema;
import com.sinosoft.lis.schema.LPLoanSchema;
import com.sinosoft.lis.schema.LPReturnLoanSchema;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LLClaimDetailSet;
import com.sinosoft.lis.vschema.LLExemptSet;
import com.sinosoft.lis.vschema.LOLoanSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:豁免处理提交信息类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author: yuejw
 * @version 1.0
 */
public class LLClaimExemptBL {
private static Logger logger = Logger.getLogger(LLClaimExemptBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap mMMap = new MMap();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();

	// 理赔--立案信息
	private LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();
	private LLClaimDetailSchema mLLClaimDetailSchema = new LLClaimDetailSchema();
	private LCPremSchema mLCPremSchema = new LCPremSchema();
	private LLExemptSchema mLLExemptSchema = new LLExemptSchema();
	private LCPremSet mLCPremSet = new LCPremSet();
	private LLClaimDetailSet tLLClaimDetailSet = null;
	private String mInsDate = ""; // 出险时间

	private String mClmNo = ""; // 赔案号

	public LLClaimExemptBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("--------------------------豁免处理-----LLClaimExemptBL测试-----开始--------------------------");

		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		if (!checkInputData()) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("--------------------------豁免处理-----LLClaimExemptBL测试-----结束--------------------------");

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, cOperate)) {
			// @@错误处理
			CError.buildErr(this, "数据提交失败,原因是:"+tPubSubmit.mErrors.getLastError());
			return false;
		}

		mInputData = null;
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 获取页面信息
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);// 按类取值
		mLLClaimDetailSchema = (LLClaimDetailSchema) mInputData
				.getObjectByObjectName("LLClaimDetailSchema", 0);
		mLLExemptSchema = (LLExemptSchema) mInputData.getObjectByObjectName(
				"LLExemptSchema", 0);


		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		
		logger.debug("----------begin checkInputData----------");
		try 
		{
			// 非空字段检验
			if (mGlobalInput == null) {
				// @@错误处理
				CError.buildErr(this, "接受的全局信息全部为空!");
				return false;
			}
			if (mOperate.equals("LLExempt||Get") && mLLClaimDetailSchema == null) {
				
				CError.buildErr(this, "用于提取豁免信息的数据全部为空!");
				return false;

			}
			if (mOperate.equals("LLExempt||Save") && mLLExemptSchema == null) {
				
				CError.buildErr(this, "用于提取豁免信息的数据全部为空!");
				return false;
			}
			
			//获得立案信息
			mLLRegisterSchema=mLLClaimPubFunBL.getLLRegister(mLLClaimDetailSchema.getClmNo());
			
			if(mLLRegisterSchema==null)
			{
				CError.buildErr(this, "查询立案信息失败!");
				return false;
			}
		
			
			return true;
			
		} 
		catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "在校验输入的数据时出错!");
			return false;
		}

	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		// 获取缴费项信息
		if (mOperate.equals("LLExempt||Get")) {
			if (!getLCPrem()) {
				return false;
			}

		}

		// 修改保存豁免信息
		if (mOperate.equals("LLExempt||Save")) {
			if (!getLLExemptSave()) {
				return false;
			}

		}
		return true;
	}

	/**
	 * 获取缴费项信息
	 * 
	 * @return
	 */
	private boolean getLCPrem() {
		logger.debug("-------------------获取LCPrem缴费项豁免信息-------开始-------------------");
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 1.0 声明变量 删除已经保存的豁免信息
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tClmNo = mLLClaimDetailSchema.getClmNo();
		String tSqlDel = "delete from LLExempt where ClmNo='" + "?ClmNo?" + "'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSqlDel);
		sqlbv.put("ClmNo", tClmNo);
		logger.debug("删除豁免信息sql:"+tSqlDel);
		mMMap.put(sqlbv, "DELETE");

		String mSql = "";
		
	    //如果一单无法豁免，其它保单不受影响，继续进行豁免操作
		if (mOperate.equals("LLExempt||Get")) {
			
			String tSql = "";

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 2.0 根据赔案号获取该赔案的所有豁免信息
			 * [提取豁免信息]按钮动作----根据LLClaimDetail中理赔类型为109--意外豁免，209--疾病豁免的赔案
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			tSql = "select * from llclaimdetail where 1=1 " + " and clmno='"
					+ "?clmno?" + "'"
					+ " and getdutykind in('109','209') and GiveType='0'";
			logger.debug("提取豁免信息sql:"+tSql);
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSql);
			sqlbv1.put("clmno", mLLClaimDetailSchema.getClmNo());
			LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
			tLLClaimDetailSet = new LLClaimDetailSet();
			tLLClaimDetailSet = tLLClaimDetailDB
					.executeQuery(sqlbv1);
			logger.debug("--获取保项豁免信息[" + tLLClaimDetailSet.size() + "]--"
					+ tSql);
			
			if (tLLClaimDetailDB.mErrors.needDealError()) {
				CError.buildErr(this, "赔案号[" + mLLClaimDetailSchema.getClmNo() + "]的豁免保项信息获取失败!!!"
						+ tLLClaimDetailDB.mErrors.getError(0).errorMessage);
				return false;
			}
			
			if(tLLClaimDetailSet.size()==0){
				CError.buildErr(this, "赔案不涉及豁免责任,无需豁免处理!");
				return false;
			}
			else
			{
				//获得出险日期
				mInsDate = mLLClaimPubFunBL.getInsDate(mLLClaimDetailSchema.getClmNo());
				
				/**
				 * 2009-01-15 zhangzheng
				 * 被保险人于本附加合同生效（或复效）之日起一年内（包括一年）初次患有本附加合同附表中约定的重大疾病，本公司无息返还本附加合同所交保险费，本附加合同效力终止
				 * 只有距离出险日期满一年才可以执行豁免责任
				 */

				for (int i = 1; i <= tLLClaimDetailSet.size(); i++) {
					
					//同方去哪求的产品没有此校验，故注释 modift by yhy 2016年11月30日14:54:57 start
//					LMDutyGetClmCalSchema tLMDutyGetClmCalSchema = mLLClaimPubFunBL.
//						getLMDutyGetClmCal(tLLClaimDetailSet.get(i).getGetDutyKind(), tLLClaimDetailSet.get(i).getGetDutyCode());
//		
//					//当查询到记录时才进行校验
//					if (!(tLMDutyGetClmCalSchema.getGetDutyCode()==null||tLMDutyGetClmCalSchema.getGetDutyCode().equals(""))) {
//						
//						//取出校验算法
//						String tCalCode = StrTool.cTrim(tLMDutyGetClmCalSchema.getCalCode4());
//						
//						if (tCalCode==null||tCalCode.equals("")) {
//							
//							CError.buildErr(this, "查询不到获取豁免险的出险日期距合同生效（或复效）日的间隔信息的算法!");
//							continue;
//						}
//						else
//						{
//							int result = Integer.parseInt(executeCalCode(tCalCode,tLLClaimDetailSet.get(i).getSchema()));
//							
//							logger.debug("result:"+result);
//							
//							//等于0表示出险日期是距离合同生效(或复效)满一年,大于0表示未满一年,所以
//							if(result>0)
//							{
//								CError.buildErr(this, "出险日期距离本附加合同生效（或复效）之日不足一年,不能执行豁免责任!");
//								continue;
//							}
//						}
//						
//						//释放强应用
//						tCalCode=null;
//						
//					}
//					else
//					{
//						CError.buildErr(this, "获取不到豁免险的出险日期距合同生效（或复效）日的间隔信息!");
//						continue;
//					}
//					
//					tLMDutyGetClmCalSchema=null;
					//同方去哪求的产品没有此校验，故注释 modift by yhy 2016年11月30日14:54:57 end
					/**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 3.0 根据豁免信息，及豁免的指向提取所有缴费项信息
					 * 
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					 */
					
					
					mLLClaimDetailSchema = tLLClaimDetailSet.get(i);

					LMDutyGetClmSchema tLMDutyGetClmSchema = mLLClaimPubFunBL
							.getLMDutyGetClm(tLLClaimDetailSet.get(i).getGetDutyKind(), tLLClaimDetailSet.get(i).getGetDutyCode());
					if (tLMDutyGetClmSchema == null) {
						continue;
					}

					/**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 3.2
					 * 获取豁免的缴费项信息
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					 */
//					tSql = "select a.* from LCPrem a,llclaimdetail b where 1=1 "
//							+ " and a.contno = b.contno  and a.polno = b.polno"
//							+ " and b.clmno = '"+mLLClaimDetailSchema.getClmNo()+"'"
//							+ " and (trim(FreeFlag) = '0' or trim(FreeFlag) is null) " // 0不免交，1免交
//							+ " and b.riskcode in (select code1  from ldcode1,lcpol c  where codetype = 'freerisk'"
//							+ " and code = c.riskcode and c.polno = '" + tLLClaimDetailSet.get(i).getPolNo() + "')";
					
					
					mSql = "   select e.* from lcpol d,lcprem e where  d.polno=e.polno  and d.contno='"
						+"?contno?"+"' "
						+" and riskcode in (select code1 from  lcpol b,ldcode1 c where b.polno='"
						+"?polno?"+"'"
						+" and b.riskcode=c.code and c.codetype = 'freerisk'  ) "
						+" and appflag in ('1','4') and  (trim(e.FreeFlag) = '0' or trim(e.FreeFlag) is null) "
						;
					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					//sqlbv2.sql(tSql);
					//start whc:2016-5-30
					sqlbv2.sql(mSql);
					//end
					sqlbv2.put("contno", tLLClaimDetailSet.get(i).getContNo());
					sqlbv2.put("polno", tLLClaimDetailSet.get(i).getPolNo());
					LCPremDB tLCPremDB = new LCPremDB();
					LCPremSet tLCPremSet = tLCPremDB.executeQuery(sqlbv2);
					logger.debug("--获取豁免的缴费项信息[" + tLCPremSet.size() + "]--"
							+ mSql);
					if (tLCPremDB.mErrors.needDealError()) {
						mErrors.addOneError("保单号[" + tLLClaimDetailSet.get(i).getPolNo() + "]的缴费信息没有取到!!!"
								+ tLCPremDB.mErrors.getError(0).errorMessage);
						continue;
					}

					mLCPremSet.add(tLCPremSet);

				}
	
			}
		}
		
		


	

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 4.0 进行豁免信息保存处理
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLExemptSet tLLExemptSet = new LLExemptSet();
		boolean sameflag=false;
		for (int j = 1; j <= mLCPremSet.size(); j++) {
			LLExemptSchema tLLExemptSchema = new LLExemptSchema();
			LCPremSchema tLCPremSchema = mLCPremSet.get(j);

			if (tLCPremSchema.getPayIntv() == 0) {
				mErrors.addOneError("--险种号[" + tLCPremSchema.getPolNo()
						+ "]的缴费方式为趸交,豁免被过滤!!!");
				continue;
			}
			
			/**
			 * 2009-01-13 zhangzheng
			 * 如果多个豁免险在同一个赔案中理赔,有可能同一张单子被查询出来多次,从第2次开始被过滤掉
			 */
			if(tLLExemptSet.size()>0)
			{
				sameflag=false;
				
				for (int k = 1; k <= tLLExemptSet.size(); k++) {
					
					if(tLLExemptSet.get(k).getPolNo().equals(tLCPremSchema.getPolNo()))
					{
						sameflag=true;
						break;
					}
				}
				
				if(sameflag==true)
				{
					continue;
				}
			}


			Reflections tRef = new Reflections();
			tRef.transFields(tLLExemptSchema, tLCPremSchema);

			tLLExemptSchema.setClmNo(tClmNo); // [赔案号]
			tLLExemptSchema.setOperator(mGlobalInput.Operator);
			tLLExemptSchema.setMakeDate(CurrentDate);
			tLLExemptSchema.setMakeTime(CurrentTime);
			tLLExemptSchema.setModifyDate(CurrentDate);
			tLLExemptSchema.setModifyTime(CurrentTime);
			tLLExemptSchema.setManageCom(mGlobalInput.ManageCom);
			tLLExemptSet.add(tLLExemptSchema);
		}

		logger.debug("--获取豁免信息[" + mLCPremSet.size() + "]--" + mSql);
		mMMap.put(tLLExemptSet, "DELETE&INSERT");

		logger.debug("-------------------获取LCPrem缴费项豁免信息-------结束-------------------");

		return true;
	}

	/**
	 * 目的：执行产品指向的算法公式
	 */
	private String executeCalCode(String pCalCode,LLClaimDetailSchema pLLClaimDetailSchema) {
		String tReturn = "";

		if (pCalCode == null || pCalCode.length() == 0) {
			return tReturn;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 设置各种参数
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		logger.debug("----------开始---------执行产品指向的算法公式-------------------------------");

		logger.debug("===========================================================================");

		// 增加基本要素,计算给付金
		TransferData tTransferData = new TransferData();
		// 合同号
		tTransferData.setNameAndValue("ContNo", String
				.valueOf(mLLClaimDetailSchema.getContNo()));
		
		// 出险时已保年期
		tTransferData.setNameAndValue("RgtYears", String.valueOf(PubFun
				.calInterval(getPolCValiDate(pLLClaimDetailSchema.getPolNo()), this.mInsDate, "Y")));

		 //保单险种号
		 tTransferData.setNameAndValue("PolNo",String.valueOf(
				 pLLClaimDetailSchema.getPolNo()));
		 
		// 出险原因
		tTransferData.setNameAndValue("AccidentReason", String.valueOf(mLLRegisterSchema.getAccidentReason()));

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0
		 * 将所有设置的参数加入到PubCalculator.addBasicFactor()中
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		Vector tVector = tTransferData.getValueNames();
		PubCalculator tPubCalculator = new PubCalculator();

		logger.debug("===========================================================================");
		for (int i = 0; i < tVector.size(); i++) {
			String tName = (String) tVector.get(i);
			String tValue = (String) tTransferData.getValueByName(tName);
			logger.debug("计算要素--tName:" + tName + "  tValue:" + tValue);
			tPubCalculator.addBasicFactor(tName, tValue);
		}
		logger.debug("===========================================================================");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0
		 * 将所有设置的参数加入到Calculator.addBasicFactor()中
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		Calculator tCalculator = new Calculator();
		tCalculator.setCalCode(pCalCode);

		tVector = tTransferData.getValueNames();
		for (int i = 0; i < tVector.size(); i++) {
			String tName = (String) tVector.get(i);
			String tValue = (String) tTransferData.getValueByName(tName);
			// logger.debug("executeCalCode--tName:" + tName + " tValue:"
			// + tValue);
			tCalculator.addBasicFactor(tName, tValue);
		}

		
		logger.debug("计算公式==[" + tCalculator.getCalSQL() + "]");
		
		logger.debug("\n----------------------------------------------------------------------------------");
		
		tReturn = tCalculator.calculate();

		if (tCalculator.mErrors.needDealError()) {
			this.mErrors.addOneError("计算发生错误!原公式:" + pCalCode + "||,解析后的公式:"
					+ tCalculator.getCalSQL());
		}
		logger.debug("\n=========================================进行公式运算=====结束=========================================\n");
		logger.debug("----------------------------------------------------------------------------------\n");
		logger.debug("计算公式==[" + tReturn + "]");
		logger.debug("\n----------------------------------------------------------------------------------");

		logger.debug("\n----------结束---------执行产品指向的算法公式-------------------------------");

		return tReturn;

	}
	
	/**
	 * 2009-01-15 zhangzheng
	 * 根据豁免信息查询保单险种的生效日期
	 */
	private String getPolCValiDate(String nPolno) {
		
		
		LCPolSchema ttLCPolSchema=mLLClaimPubFunBL.getLCPol(nPolno);
		String cvalidate="";
		
		if (!(ttLCPolSchema.getPolNo()==null||ttLCPolSchema.getPolNo().equals(""))) {
			cvalidate=ttLCPolSchema.getCValiDate();
		}
		
		ttLCPolSchema=null;
		
		return cvalidate;
	}

	/**
	 * 修改保存豁免信息
	 * 
	 * @return
	 */
	private boolean getLLExemptSave() {
		mClmNo = StrTool.cTrim(mLLExemptSchema.getClmNo());
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 1.0 进行豁免信息提取处理
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLExemptSchema tLLExemptSchema = new LLExemptSchema();
		LLExemptDB tLLExemptDB = new LLExemptDB();
		tLLExemptDB.setClmNo(mLLExemptSchema.getClmNo()); // 传入赔案号
		tLLExemptDB.setPolNo(mLLExemptSchema.getPolNo()); // 保单险种号码
		tLLExemptDB.setDutyCode(mLLExemptSchema.getDutyCode()); // 责任编码
		tLLExemptDB.setPayPlanCode(mLLExemptSchema.getPayPlanCode()); // 交费计划编码

		if (tLLExemptDB.getInfo() == false) {
			mErrors.addOneError("赔案号[" + mLLExemptSchema.getClmNo()
					+ "]的豁免信息获取失败!!!"
					+ tLLExemptDB.mErrors.getError(0).errorMessage);
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 2.0 删除豁免的结算信息
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql1 = "delete from LLBalance where ClmNo='" + "?ClmNo?"
				+ "' and substr(FeeOperationType,1,3) in ('C08') and PolNo = '"
				+ "?PolNo?" + "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSql1);
		sqlbv3.put("ClmNo", this.mClmNo);
		sqlbv3.put("PolNo", mLLExemptSchema.getPolNo());
		this.mMMap.put(sqlbv3, "DELETE");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 3.0 进行豁免信息保存处理
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		tLLExemptSchema.setSchema(tLLExemptDB.getSchema());
		tLLExemptSchema.setFreeFlag(mLLExemptSchema.getFreeFlag());// 免交标志
		tLLExemptSchema.setFreeRate("1");// 免交比率
		tLLExemptSchema.setFreeStartDate(mLLExemptSchema.getFreeStartDate());// 免交起期
		tLLExemptSchema.setFreeEndDate(mLLExemptSchema.getFreeEndDate());// 免交止期
		tLLExemptSchema.setExemptReason(mLLExemptSchema.getExemptReason());// 豁免原因
		tLLExemptSchema.setExemptDesc(mLLExemptSchema.getExemptDesc());// 豁免描述
		tLLExemptSchema.setModifyDate(CurrentDate);
		tLLExemptSchema.setModifyTime(CurrentTime);

		if (tLLExemptSchema.getFreeFlag().equals("0"))// 0--不免交
		{
			tLLExemptSchema.setExemptPeriod("0");
		} else {

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 3.3
			 * 计算“豁免期数----ExemptPeriod
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tFreeStartDate = tLLExemptSchema.getFreeStartDate();
			String tFreeEndDate = tLLExemptSchema.getFreeEndDate();
			String tPayIntv = String.valueOf(tLLExemptSchema.getPayIntv());
			double tExemptPeriod = mLLClaimPubFunBL.getLCPremPeriodTimes(
					tFreeStartDate, tFreeEndDate, tPayIntv, tFreeEndDate);

			tLLExemptSchema.setExemptPeriod(String.valueOf(tExemptPeriod));

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 3.4 进行对豁免“起始时间”之后 的
			 * 退费处理 包括应收，实收 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LCPolSchema tLCPolSchema = mLLClaimPubFunBL
					.getLCPol(mLLExemptSchema.getPolNo());
			//主险进行不退费计算
			
			if (!getRecedeFee(tLCPolSchema)) {
				return false;
			}
	
			if (!getLJAPayPerson(tLCPolSchema, tFreeStartDate)) {
				return false;
			}
			
			//对于主险，处理出险点在垫交之前的情况
			if(tLCPolSchema.getPolNo().equals(tLCPolSchema.getMainPolNo()))
			{
				String accdate = mLLClaimPubFunBL.getInsDate(mLLClaimDetailSchema.getClmNo());
				String loan_sql="select * from loloan where contno='"+"?contno?"+"'"
				+" and loantype='1' and loandate>'"+"?accdate?"+"' and payoffflag='0'";
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql(loan_sql);
				sqlbv4.put("contno", tLCPolSchema.getContNo());
				sqlbv4.put("accdate", accdate);
				LOLoanDB tLOLoanDB = new LOLoanDB();
				LOLoanSet tLOLoanSet = tLOLoanDB.executeQuery(sqlbv4);
				if(tLOLoanSet.size()>0)
				{
					double sum_money = 0.0;
					for(int i=1;i<=tLOLoanSet.size();i++ )
					{
						LOLoanSchema tLOLoanSchema = tLOLoanSet.get(i);
						LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
						if("".equals(tLOLoanSchema.getActuGetNo()))
						{
							CError.buildErr(this, "获取垫交的实付号码失败！");
							return false;
						}
						tLJTempFeeClassDB.setChequeNo(tLOLoanSchema.getActuGetNo());
						LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB.query();
						
						if(tLJTempFeeClassSet.size()<=0)
						{
							CError.buildErr(this, "查询暂收记录失败！");
							return false;
						}
						String ljapayperson_sql = "select * from ljapayperson where getnoticeno ='"
							+"?getnoticeno?"+"' and riskcode in (  "
							+" select code1 from ldcode1 where codetype='freerisk'   and code in ( "
							+" select distinct b.riskcode from lcpol a,lmrisksort b  where contno='"
							+"?contno?"+"'"
							+" and a.riskcode=b.riskcode and risksorttype in ('18','19') ) "
							+" union select distinct b.riskcode from lcpol a,lmrisksort b  where contno='"
							+"?contno?"+"'"
							+" and a.riskcode=b.riskcode and risksorttype in ('18','19') ) ";
						SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
						sqlbv5.sql(ljapayperson_sql);
						sqlbv5.put("getnoticeno", tLJTempFeeClassSet.get(1).getTempFeeNo());
						sqlbv5.put("contno", tLCPolSchema.getContNo());
						LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
						LJAPayPersonSet tLJAPayPersonSet = tLJAPayPersonDB.executeQuery(sqlbv5);
						double money=0.0;
						for(int j=1;j<=tLJAPayPersonSet.size();j++)
						{
							money =money+ tLJAPayPersonSet.get(j).getSumActuPayMoney();
							sum_money = sum_money+tLJAPayPersonSet.get(j).getSumActuPayMoney();
						}
						double leavingmoney =  PubFun.round(tLOLoanSchema.getLeaveMoney()-money,2);
						LPLoanSchema  tLPLoanSchema = new LPLoanSchema();
						PubFun.copySchema(tLPLoanSchema, tLOLoanSchema);
						tLPLoanSchema.setEdorType("HM");
						tLPLoanSchema.setModifyDate(this.CurrentDate);
						tLPLoanSchema.setModifyTime(this.CurrentTime);
						tLPLoanSchema.setOperator(this.mGlobalInput.Operator);
						tLPLoanSchema.setLeaveMoney(leavingmoney);
						tLPLoanSchema.setClmNo(this.mClmNo);
						if(leavingmoney==0)//判断是否还清
						{
							tLPLoanSchema.setPayOffFlag("1");
						}
						else if(leavingmoney<0)
						{
							CError.buildErr(this, "问题数据：垫交保费小于应交保费，请上报核实！");
							return false;
						}
						else
						{
							tLPLoanSchema.setPayOffFlag("0");
						}
						mMMap.put(tLPLoanSchema, "DELETE&INSERT");
						
						LPReturnLoanSchema tLPReturnLoanSchema = new LPReturnLoanSchema();
						tLPReturnLoanSchema.setContNo(tLCPolSchema.getContNo());
						tLPReturnLoanSchema.setPolNo(tLCPolSchema.getPolNo());
						tLPReturnLoanSchema
								.setEdorType("HM");
						tLPReturnLoanSchema.setSerialNo(tLOLoanSchema.getSerialNo()); 
						tLPReturnLoanSchema.setActuGetNo(tLOLoanSchema.getActuGetNo()); 
						tLPReturnLoanSchema.setLoanType(tLOLoanSchema.getLoanType()); 
						tLPReturnLoanSchema.setOrderNo(tLOLoanSchema.getOrderNo()); 
						tLPReturnLoanSchema.setLoanDate(tLOLoanSchema.getLoanDate()); 
			
						tLPReturnLoanSchema.setSumMoney(tLOLoanSchema.getSumMoney()); 
						tLPReturnLoanSchema.setInputFlag(tLOLoanSchema.getInputFlag()); 
						tLPReturnLoanSchema.setInterestType(tLOLoanSchema
								.getInterestType()); 
						tLPReturnLoanSchema.setInterestRate(tLOLoanSchema
								.getInterestRate()); 
						tLPReturnLoanSchema.setInterestMode(tLOLoanSchema
								.getInterestMode()); 
						tLPReturnLoanSchema.setRateCalType(tLOLoanSchema
								.getRateCalType()); 
						tLPReturnLoanSchema.setRateCalCode(tLOLoanSchema
								.getRateCalCode()); 
						tLPReturnLoanSchema.setSpecifyRate(tLOLoanSchema
								.getSpecifyRate()); 
						tLPReturnLoanSchema.setLeaveMoney(0); 
						tLPReturnLoanSchema.setOperator(this.mGlobalInput.Operator);
						tLPReturnLoanSchema.setMakeDate(this.CurrentDate);
						tLPReturnLoanSchema.setMakeTime(CurrentTime);
						tLPReturnLoanSchema.setModifyDate(CurrentDate);
						tLPReturnLoanSchema.setModifyTime(CurrentTime);
						//存储赔案号
						tLPReturnLoanSchema.setEdorNo(this.mClmNo);
						tLPReturnLoanSchema.setClmNo(this.mClmNo);
						
						 tLPReturnLoanSchema.setLoanNo(tLOLoanSchema.getEdorNo());
						// //原批单号，界面传入
						 tLPReturnLoanSchema.setReturnMoney(money); //还款金额，为需要退给客户的金额
						 tLPReturnLoanSchema.setReturnInterest(0); //利息为0
						 if(leavingmoney==0)
						 {
							 tLPReturnLoanSchema.setPayOffFlag("1");
						 }
						 else 
						 {
							 tLPReturnLoanSchema.setPayOffFlag("0");
						 }
						 tLPReturnLoanSchema.setPayOffDate(this.CurrentDate);//??	
						 mMMap.put(tLPReturnLoanSchema, "DELETE&INSERT");
						 
					}
					
					LLBalanceSchema tLLBalanceSchema = new LLBalanceSchema();
					tLLBalanceSchema.setClmNo(this.mClmNo);
					tLLBalanceSchema.setFeeOperationType("C08"); // 业务类型
					tLLBalanceSchema.setSubFeeOperationType("C0801"); // 子业务类型
					tLLBalanceSchema.setFeeFinaType("TF"); // 财务类型
	
					tLLBalanceSchema.setBatNo("0"); // 批次号
					tLLBalanceSchema.setOtherNo("0"); // 其它号码
					tLLBalanceSchema.setOtherNoType("0"); // 财务类型
	
					tLLBalanceSchema.setGrpContNo(tLCPolSchema.getGrpContNo()); // 集体合同号码
					tLLBalanceSchema.setContNo(tLCPolSchema.getContNo()); // 合同号码
					tLLBalanceSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo()); // 集体保单号码
					tLLBalanceSchema.setPolNo(tLCPolSchema.getPolNo()); // 保单号码
	
					tLLBalanceSchema.setDutyCode("0"); // 责任编码
					tLLBalanceSchema.setGetDutyKind("0"); // 给付责任类型
					tLLBalanceSchema.setGetDutyCode("0"); // 给付责任编码
	
					tLLBalanceSchema.setKindCode(tLCPolSchema.getKindCode()); // 险类编码
					tLLBalanceSchema.setRiskCode(tLCPolSchema.getRiskCode()); // 险种编码
					tLLBalanceSchema.setRiskVersion(tLCPolSchema.getRiskVersion()); // 险种版本
	
					tLLBalanceSchema.setAgentCode(tLCPolSchema.getAgentCode()); // 代理人编码
					tLLBalanceSchema.setAgentGroup(tLCPolSchema.getAgentGroup()); // 代理人组别
	
					tLLBalanceSchema.setGetDate(this.CurrentDate); // 给付日期
					tLLBalanceSchema.setPay(sum_money); // 赔付金额
	
					tLLBalanceSchema.setPayFlag("0"); // 支付标志,0未支付,1已支付
					tLLBalanceSchema.setState("0"); // 状态,0有效
					tLLBalanceSchema.setDealFlag("0"); // 处理标志,0未处理
	
					tLLBalanceSchema.setManageCom(tLCPolSchema.getManageCom()); // 管理机构
					tLLBalanceSchema.setAgentCom(tLCPolSchema.getAgentCom()); // 代理机构
					tLLBalanceSchema.setAgentType(tLCPolSchema.getAgentType()); // 代理机构内部分类
					tLLBalanceSchema.setSaleChnl(tLCPolSchema.getSaleChnl()); // 销售渠道
	
					tLLBalanceSchema.setOperator(this.mGlobalInput.Operator); // 操作员
					tLLBalanceSchema.setMakeDate(this.CurrentDate); // 入机日期
					tLLBalanceSchema.setMakeTime(this.CurrentTime); // 入机时间
					tLLBalanceSchema.setModifyDate(this.CurrentDate); // 入机日期
					tLLBalanceSchema.setModifyTime(this.CurrentTime); // 入机时间
					tLLBalanceSchema.setOriPay(sum_money); // 原始金额
					tLLBalanceSchema.setCustomerNo(tLCPolSchema.getInsuredNo());
					
					mMMap.put(tLLBalanceSchema, "DELETE&INSERT");
					
					tLLBalanceSchema = new LLBalanceSchema();
					tLLBalanceSchema.setClmNo(this.mClmNo);
					tLLBalanceSchema.setFeeOperationType("C08"); // 业务类型
					tLLBalanceSchema.setSubFeeOperationType("C0802"); // 子业务类型
					tLLBalanceSchema.setFeeFinaType("HD"); // 财务类型
	
					tLLBalanceSchema.setBatNo("0"); // 批次号
					tLLBalanceSchema.setOtherNo("0"); // 其它号码
					tLLBalanceSchema.setOtherNoType("0"); // 财务类型
	
					tLLBalanceSchema.setGrpContNo(tLCPolSchema.getGrpContNo()); // 集体合同号码
					tLLBalanceSchema.setContNo(tLCPolSchema.getContNo()); // 合同号码
					tLLBalanceSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo()); // 集体保单号码
					tLLBalanceSchema.setPolNo(tLCPolSchema.getPolNo()); // 保单号码
	
					tLLBalanceSchema.setDutyCode("0"); // 责任编码
					tLLBalanceSchema.setGetDutyKind("0"); // 给付责任类型
					tLLBalanceSchema.setGetDutyCode("0"); // 给付责任编码
	
					tLLBalanceSchema.setKindCode(tLCPolSchema.getKindCode()); // 险类编码
					tLLBalanceSchema.setRiskCode(tLCPolSchema.getRiskCode()); // 险种编码
					tLLBalanceSchema.setRiskVersion(tLCPolSchema.getRiskVersion()); // 险种版本
	
					tLLBalanceSchema.setAgentCode(tLCPolSchema.getAgentCode()); // 代理人编码
					tLLBalanceSchema.setAgentGroup(tLCPolSchema.getAgentGroup()); // 代理人组别
	
					tLLBalanceSchema.setGetDate(this.CurrentDate); // 给付日期
					tLLBalanceSchema.setPay(-sum_money); // 赔付金额
	
					tLLBalanceSchema.setPayFlag("0"); // 支付标志,0未支付,1已支付
					tLLBalanceSchema.setState("0"); // 状态,0有效
					tLLBalanceSchema.setDealFlag("0"); // 处理标志,0未处理
	
					tLLBalanceSchema.setManageCom(tLCPolSchema.getManageCom()); // 管理机构
					tLLBalanceSchema.setAgentCom(tLCPolSchema.getAgentCom()); // 代理机构
					tLLBalanceSchema.setAgentType(tLCPolSchema.getAgentType()); // 代理机构内部分类
					tLLBalanceSchema.setSaleChnl(tLCPolSchema.getSaleChnl()); // 销售渠道
	
					tLLBalanceSchema.setOperator(this.mGlobalInput.Operator); // 操作员
					tLLBalanceSchema.setMakeDate(this.CurrentDate); // 入机日期
					tLLBalanceSchema.setMakeTime(this.CurrentTime); // 入机时间
					tLLBalanceSchema.setModifyDate(this.CurrentDate); // 入机日期
					tLLBalanceSchema.setModifyTime(this.CurrentTime); // 入机时间
					tLLBalanceSchema.setOriPay(-sum_money); // 原始金额
					tLLBalanceSchema.setCustomerNo(tLCPolSchema.getInsuredNo());
					mMMap.put(tLLBalanceSchema, "DELETE&INSERT");
				}
			}
		}

		mMMap.put(tLLExemptSchema, "DELETE&INSERT");

		return true;
	}

	/**
	 * 退财务的应收记录
	 * 
	 * @param tLCPolSchema
	 * @return
	 */
	private boolean getRecedeFee(LCPolSchema tLCPolSchema) {
		logger.debug("----------退财务的应收记录-----getRecedeFee-----开始----------");

		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ClmNo", mLLExemptSchema.getClmNo()); // 赔案号
		tTransferData.setNameAndValue("CalDate", mLLExemptSchema
				.getFreeStartDate());// 计算时点
		tTransferData.setNameAndValue("DealDate", "1"); // 处理时间,1-包括计算时点,2不包括计算时点
		tTransferData.setNameAndValue("DealType", "1"); // 处理方式,1-指定日期之后,2-指定日期之前
		tTransferData.setNameAndValue("DealMode", "1"); // 处理模式,1-直接删除,2-不删除

		tVData.add(tTransferData);
		tVData.add(tLCPolSchema);

		LLBalRecedeFeeBL tLLBalRecedeFeeBL = new LLBalRecedeFeeBL();
		if (tLLBalRecedeFeeBL.submitData(tVData, mOperate) == false) {
			this.mErrors.copyAllErrors(tLLBalRecedeFeeBL.mErrors);
			return false;
		} else {
			VData tempVData = tLLBalRecedeFeeBL.getResult();
			MMap tMMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
			this.mMMap.add(tMMap);
		}
		logger.debug("----------退财务的应收记录-----getRecedeFee-----结束----------");
		return true;
	}

	/**
	 * 进行个人实收表退费处理
	 * 
	 * @param tLLExemptSchema
	 * @return
	 */
	private boolean getLJAPayPerson(LCPolSchema pLCPolSchema, String pDate) {

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("LCPolSchema", pLCPolSchema);

		tTransferData.setNameAndValue("ClmNo", mClmNo); // 赔案号
		tTransferData.setNameAndValue("Date", pDate); // 指定日期
		tTransferData.setNameAndValue("DealType", "1"); // 处理方式,1-指定日期之后
		tTransferData.setNameAndValue("DealDate", "1"); // 处理时间,1-包括计算的时点
		tTransferData.setNameAndValue("Operator", mGlobalInput.Operator); // 操作人员

		tTransferData.setNameAndValue("FeeType", "C08"); // 业务类型
		tTransferData.setNameAndValue("SubFeeType", "C0801"); // 子业务类型
		tTransferData.setNameAndValue("FinaType", "TF"); // 财务类型

		LLBalanceSchema tLLBalanceSchema = mLLClaimPubFunBL
				.getLJAPayPerson(tTransferData);

		if (tLLBalanceSchema == null) {
			mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
		} else {
			mMMap.put(tLLBalanceSchema, "DELETE&INSERT");
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(mMMap);
			return true;
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimExemptBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
	}
	
	

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

		LLReportSchema tLLReportSchema = new LLReportSchema();

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		String tClmNo = "90000001810";
		String tOperate = "LLExempt||Get";

		LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
		tLLClaimDetailDB.setCaseNo(tClmNo);
		LLClaimDetailSet tLLClaimDetailSet = tLLClaimDetailDB.query();

		if (tLLClaimDetailSet.size() == 0) {
			logger.debug("----------------------无事故信息-----------------------");
			return;
		}
		LLClaimDetailSchema tLLClaimDetailSchema = tLLClaimDetailSet.get(1);

		GlobalInput tG = new GlobalInput();
		tG.Operator = tLLClaimDetailSchema.getOperator();
		tG.ManageCom = tLLClaimDetailSchema.getMngCom();
		tG.ComCode = tLLClaimDetailSchema.getMngCom();

		LLExemptSchema tLLExemptSchema = new LLExemptSchema();

		VData tVData = new VData();
		tVData.addElement(tG);
		tVData.addElement(tLLClaimDetailSchema);
		tVData.addElement(tLLExemptSchema);

		LLClaimExemptBL tLLClaimExemptBL = new LLClaimExemptBL();
		tLLClaimExemptBL.submitData(tVData, tOperate);
		int n = tLLClaimExemptBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "提示信息: "
					+ tLLClaimExemptBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}

	}

}
