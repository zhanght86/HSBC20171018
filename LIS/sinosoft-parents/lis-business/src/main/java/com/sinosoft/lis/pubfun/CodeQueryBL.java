/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import java.util.Hashtable;

import com.sinosoft.lis.config.CodeCacheService;
import com.sinosoft.lis.schema.LDCodeSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 1.0
 */

public class CodeQueryBL {
private static Logger logger = Logger.getLogger(CodeQueryBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往后面传输数据的容器 */
	private VData mResult = new VData();


	/** 存储查询语句 */
	private String mSQL = "";
	private StringBuffer mSBql = new StringBuffer(256);

	/** 存储全局变量 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 存储查询条件 */
	private String mCodeCondition = "";
	private String mConditionField = "";

	/** 存数VerifyInput传过来的值 */
	private String mCodeValue = "";

	/** 业务处理相关变量 */
	private LDCodeSchema mLDCodeSchema = new LDCodeSchema();

	// private LDCodeSet mLDCodeSet = new LDCodeSet();
	private ExeSQL mExeSQL = new ExeSQL();

	/** 返回的数据 */
	private String mResultStr = "";

	// 表示某一个Code是否可以从缓存中取出
	private boolean m_bCanBeCached = false;
	@SuppressWarnings("rawtypes")
	private static Hashtable m_hashResultStr = new Hashtable();
	private static String TOO_LONG_FLAG = "Too long";
	private CodeCacheService codeCacheService = new CodeCacheService();
	public CodeQueryBL() {
	}

	/**
	 * 传输数据的公共方法, 本处理没有后续的BLS层，故该方法无用
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 进行业务处理
		if (!queryData()) {
			return false;
		}

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		// 代码查询条件
		try {
			mLDCodeSchema.setSchema((LDCodeSchema) mInputData
					.getObjectByObjectName("LDCodeSchema", 0));
			try {
				mGlobalInput.setSchema((GlobalInput) mInputData
						.getObjectByObjectName("GlobalInput", 0));
			} catch (Exception e) {
				mGlobalInput.ComCode = "";
				mGlobalInput.ManageCom = "";
				mGlobalInput.Operator = "";
			}
			// logger.debug("GGGGGGGGGGGGGGGGG" + mGlobalInput.ManageCom);
			// logger.debug(mGlobalInput.ComCode);
			//logger.debug("ManageCom : " + mGlobalInput.ManageCom);
			TransferData tTransferData = (TransferData) mInputData
					.getObjectByObjectName("TransferData", 0);
			// mCodeValue
			mCodeValue = tTransferData.getValueByName("codeValue") == null ? ""
					: (String) tTransferData.getValueByName("codeValue");
			//logger.debug("mCodeValue:" + mCodeValue);
			// 暂时默认为字符串类型
			// mCodeCondition = "'" +
			// (String)tTransferData.getValueByName("codeCondition") + "'";
			mCodeCondition = (String) tTransferData
					.getValueByName("codeCondition");

			if (mCodeCondition == null || mCodeCondition.equals("") || mCodeCondition.equals("null")) {
				this.m_bCanBeCached = true; //没有条件列，表示Code所对应的查询语句是固定的，所以可以使用缓存。
				mCodeCondition="";
			}

			if (mCodeCondition.indexOf('#') == -1) {
				mCodeCondition = "'" + mCodeCondition + "'";
			} else {
				mCodeCondition = mCodeCondition.replace('#', '\'');
			}
			mConditionField = (String) tTransferData
					.getValueByName("conditionField");

			if (mConditionField==null || mConditionField.equals("") || mConditionField.equals("null")) {
				mCodeCondition = "1";
				mConditionField = "1";
				this.m_bCanBeCached = true; //没有条件列，表示Code所对应的查询语句是固定的，所以可以使用缓存。
			}
			
		} catch (Exception e) {
			logger.debug("CodeQueryBL throw Errors at getInputData: can not get GlobalInput!");
			mCodeCondition = "1";
			mConditionField = "1";
		}
		return true;
	}

	public void setGlobalInput(GlobalInput cGlobalInput) {
		mGlobalInput.setSchema(cGlobalInput);
	}

	/**
	 * 查询符合条件的保单 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	private boolean queryData() {
		mSQL = "";
		int executeType = 0;

		if (mGlobalInput.ManageCom == null
				|| mGlobalInput.ManageCom.trim().equals("")) {
			mGlobalInput.ManageCom = "86";
		}

		//增加下面查询
		/*再保再保再保再保再保再保再保再保再保再保*/
		/*---------------------------------------------------------------------------------------------------------*/
        //再保合同
	    if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
	            "riaccmucode") == 0) {
	          mSQL = "select a.AccumulateDefNO,a.AccumulateDefName from RIAccumulateDef a where a.state='01' order by a.AccumulateDefNO";
	        }
	        
	          // 再保算法
	        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
	    				"riarithmetic") == 0) {
	    			mSQL = " select distinct a.arithmeticdefid,a.arithmeticdefname from RICalDef a where a.Arithsubtype='"
	    					+ mConditionField + "' order by ArithmeticDefID";
	    		}
	        
	        // 再保合同
	    				if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
	    						"ricontno") == 0) {
	    					mSQL = "select RIContNo, RIContName from RIBarGainInfo order by RIContNo";
	    				}	
	       // 盈余佣金下拉
	    				if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
	    						"riprofit") == 0) {
	    					mSQL = "select a.RIProfitNo, a.RIProfitName, b.ComPanyNo, b.ComPanyName,c.RIContNo, c.RIContName from RIProfitDef a, RIComInfo b, RIBarGainInfo c where a.ReComCode=b.ComPanyNo and a.RIContNo=c.RIContNo";
	    				}
	    				// 再保帳單下拉
	    				if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
	    						"ribill") == 0) {
	    					mSQL = "select a.billno,a.billname,a.currency,(select x.currname from ldcurrency x where x.currcode=a.currency),b.ricontno,b.ricontname,c.ComPanyNo,c.ComPanyName from RIBsnsBillDef a, RIBarGainInfo b, RIComInfo c where a.ricontno=b.ricontno and a.IncomeCompanyNo = c.ComPanyNo order by a.billno";
	    				}
	    				// 再保方案
	    				if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
	    						"riprecept") == 0) {
	    					mSQL = "select a.RIPreceptNo,a.RIPreceptName from RIPrecept a where a.state='01' order by RIPreceptNo";
	    				}

	    				if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
	    						"riquerycont") == 0) {
	    					mSQL = "select RIContNo,(select b.RIContName from RIBarGainInfo b where a.RIContNo=b.RIContNo) from RIProfitDef a where "
	    							+ mConditionField + " = " + mCodeCondition;
	    				}
	    				// 再保分入公司
	    				if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
	    						"riincompany") == 0) {
	    					mSQL = "select companyno,companyname from RIComInfo where remark = '02' order by companyno";
	    				}
	    				// 再保帳單编号
	    				if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
	    						"ribillno") == 0) {
	    					mSQL = "select distinct a.billno,a.billname from RIBsnsBillDef a order by a.billno";
	    				}
	    				if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
	    						"ribillcurrency") == 0) {
	    					mSQL = "select t.currency,(select currname from ldcurrency where currcode=t.Currency) from Ribsnsbilldef t where "
	    							+ mConditionField + "=" + mCodeCondition;
	    				}
	    				// 再保公司代码
	    				if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
	    						"ricompanycode") == 0) {
	    					mSQL = "select distinct ComPanyNo, ComPanyName from ricominfo order by ComPanyNo";
	    				}
	    				// 分保费率表
	    				if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
	    						"rifeerate") == 0) {
	    					mSQL = "select * from (select FeeTableNo a,FeeTableName b from RIFeeRateTableDef where state='01' union select '' a,'' b from dual) order by a";
	    				}
	    				if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
	    						"rifeebatch") == 0) {
	    					mSQL = "select batchno,batchno from RIFeeRateTableTrace where feetableno="
	    							+ mCodeCondition + " union select '','' from dual";
	    				}	
		
        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).equalsIgnoreCase("lrcontno")) {
            mSQL = "select RIContNo, RIContName from RIBarGainInfo order by RIContNo ";
        }
        //分保方式
        if(StrTool.cTrim(mLDCodeSchema.getCodeType()).equalsIgnoreCase("reinsuresubtype"))
        {
        	mSQL = "select code,codename from ldcode where codetype = 'reinsuresubtype' and  "+mConditionField+"="+mCodeCondition+" ";
        }
        if(StrTool.cTrim(mLDCodeSchema.getCodeType()).equalsIgnoreCase("hierarchynumtype"))
        {
        	mSQL = "select code,codename from ldcode where codetype = 'hierarchynumtype' and  "+mConditionField+"="+mCodeCondition+" ";
        }
        //再保合同要素
        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("lrfactor") == 0) {
            mSQL = "select factorname,factor from RICalFactor";
        }
        //再保公司代码
        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).equalsIgnoreCase("lrcompanysta")) {
            mSQL = "select distinct a.Incomecompanyno, (select b.companyname from Ricominfo b where a.incomecompanyno=b.companyno) from RIIncomeCompany a where a.incomecompanytype = '01' order by a.Incomecompanyno";

        }
        //再保公司代码
        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("lrcompanycode") == 0) {
            mSQL = "select distinct ComPanyNo, ComPanyName from ricominfo order by ComPanyNo";
        }
        //再保责任代码
        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("lrduty") == 0) {
            mSQL = "select dutycode,dutyname from lmduty ";
        }
        //再保累计风险方案
        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("lraccmucode") == 0) {
            mSQL = "select a.AccumulateDefNO,a.AccumulateDefName from RIAccumulateDef a where a.state='01' order by a.AccumulateDefNO ";
        }
        //再保联合累计风险险种代码查询
        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("lrrecode")==0){
        	mSQL ="select a.associatedcode,a.associatedname from RIAccumulateRDCode a where "
        	       + mConditionField + " = " + mCodeCondition+" order by a.associatedcode";
        }
        //再保方案
        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("lrriprecept") == 0)
        {
            mSQL = "select a.RIPreceptNo,a.RIPreceptName from RIPrecept a where a.state='01' order by RIPreceptNo ";
        }
        //再保算法
        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("lrarithmetic") == 0)
        {
            logger.debug(" mCodeCondition: "+mCodeCondition);
            logger.debug(" mConditionField: "+mConditionField);
            mSQL = " select distinct a.arithmeticdefid,a.arithmeticdefname from RICalDef a where a.ArithContType='02' and a.standbystring1='P' and (a."+mConditionField+" ="+mCodeCondition+" ) order by ArithmeticDefID";
        }
        //再保合同
        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("lrricont") == 0)
        {
            mSQL = " select * from (select ricontno A1,'再保合同1' A2 from RIBarGainInfo  union select 'ALL' A1,'全部' A2 from dual) x order by x.A1 desc";
        }
        /*** 分保费率表 ***/
        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("feerate") == 0)
        {
                mSQL = "select * from (select FeeTableNo a,FeeTableName b from RIFeeRateTableDef where state='01' union select '' a,'' b from dual) order by a";
        }
        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("feebatch") == 0)
        {
                mSQL = "select batchno,batchno from RIFeeRateTableTrace where feetableno="+mCodeCondition+" union select '','' from dual ";
        }
        //团单下责任
        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).equalsIgnoreCase("lrgrpcontduty"))
        {
            mSBql.append("select distinct b.dutycode,(select dutyname from lmduty where trim(dutycode)=trim(b.dutycode)) from lcpol a,lcduty b where trim(a.polno)=trim(b.polno) and trim(a.grpcontno)='" +mConditionField + "' and trim(b.dutycode) in (select distinct trim(associatedcode) from RIAccumulateRDCode) ");
            mSQL = mSBql.toString();
        }
        //团单下险种
        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).equalsIgnoreCase("lrgrpcontrisk"))
        {
            mSBql.append(" select distinct a.riskcode,(select riskname from lmrisk where trim(riskcode)=trim(a.riskcode)) from lcpol a,lcduty b where trim(a.polno)=trim(b.polno) and trim(a.grpcontno)='" + mConditionField +
                    "' and trim(b.dutycode) in (select distinct trim(associatedcode) from RIAccumulateRDCode)  ");
            mSQL = mSBql.toString();
        }
        //分保险种
        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).equalsIgnoreCase("lrriskcode"))
        {
            mSBql.append(" select associatedcode,associatedname from riaccumulaterdcode where 1=1");
            mSQL = mSBql.toString();
        }
        //团单下保障计划
        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).equalsIgnoreCase("lrgrpcontplan"))
        {
            mSBql.append(" select distinct a.contplancode,(select ContPlanName from lccontplan where trim(grpcontno)=trim(a.GrpContNo) and trim(ContPlanCode)=trim(a.ContPlanCode)) from lcinsured a where exists (select * from lccont where trim(a.contno)=trim(contno) and trim(grpcontno)='" +
                         mConditionField + "') ");
            mSQL = mSBql.toString();
        }
        //批次号
        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).equalsIgnoreCase("lrbatchno"))
        {
            mSBql.append(" select * from (select distinct BatchNo, startdate,enddate from riwflog a ) where  order by batchno desc limit 0,10");
            mSQL = mSBql.toString();
        }
        //再保业务帐单类型
        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).equalsIgnoreCase("lraccountstyle")) 
        {
          if (mConditionField.trim().equals("1")) 
          {
              mSBql.append("select code,codename from ldcode where codetype='accouttype' order by code");
          } 
          else 
          {
              mSBql.append( "select code,codename from ldcode where codetype='accouttype' and othersign='" + mConditionField + "' order by code");
          }
          mSQL = mSBql.toString();	            
        }
        //再保报表类型
        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).equalsIgnoreCase("lreport")) 
        {
        	if (mConditionField.trim().equals("1")) 
        	{
        		mSBql.append("select code,codename from ldcode where codetype ='rireporttype' order by code ");
        	} 
        	else 
        	{
        		mSBql.append("select code,codename from ldcode where codetype ='rireporttype' and othersign='" + mConditionField + "' order by code ");
        	}
        	mSQL = mSBql.toString();
        }
     /*--------再保 end -------------------------------------------------------------------------------------------------*/	
        
		// 报表批处理参数
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"reporttype") == 0) {
			mSQL = "select code,codename from LOReportCode where 1=1 ";

			if (mCodeValue != null && !mCodeValue.equals("")) {
				mSQL = mSQL + " and code='" + mCodeValue + "' ";
			}
		}

		// 从BOM查出
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("leafibrms") == 0) {
			
			mSQL = "select name,cnname from lrbom where valid='1'";

		}

		// 投连计价报告
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"tlreport") == 0) {
			mSQL = "select code,codename from LOReportCode where code like 'tl_%'";

			if (mCodeValue != null && !mCodeValue.equals("")) {
				mSQL = mSQL + " and code='" + mCodeValue + "' ";
			}
		}

		/** 财务收付单证类型 Added by guanwei 2006-04-13 */
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"certifycode1") == 0) {
			mSQL = "select Code,CodeName from LDCode "
					+ " where CodeType = 'certifycode1' and OtherSign = '1'"
					+ " union " + "select Code,CodeName from LDCode"
					+ " where CodeType = 'cardtype' and OtherSign = '1'";

			if (mCodeValue != null && !mCodeValue.equals("")) {
				mSQL = mSQL + " and code='" + mCodeValue + "' ";
			}

		}
		
		// 查询币种
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
			"currency") == 0)
		{
			mSQL = "select currcode,currname from ldcurrency order by currcode";

		}

		// 根据收费员职级取得佣金项
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"wagecode") == 0) {
			mSQL = "select Wagecode,Wagename from laindexvscomm where "
					+ mConditionField + " = " + mCodeCondition;
		}

		// 根据收费员佣金项取得参数项
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"paracode") == 0) {
			mSQL = "select Paracode,Paraname from lawagevsparam where "
					+ mConditionField + " = " + mCodeCondition;
		}
		
		// 根据功能ID查询涉及到的业务表  jiyongtian 2012-6-25
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
						"businesstable") == 0) {
			mSQL = "select businesstable,businesstablename from ldwftobusiness where "
				  + mConditionField + " = " + mCodeCondition;
			mSQL = mSQL + " order by businesstable";
		}
		// 根据功能ID和业务表查询业务字段 jiyongtian 2012-6-25
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"fieldcode") == 0) {
	         mSQL = "select fieldcode,fieldcodename from ldwfbusifield where "
		               + mConditionField + " = " + mCodeCondition;
	         mSQL = mSQL + " order by fieldcode";
        }


		// 核保师查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"uwper") == 0) {
			mSQL = "select a.usercode,'' from LDUWUser a where 1=1 and "
					+ mConditionField + " = " + mCodeCondition + " ";

			if (mCodeValue != null && !mCodeValue.equals("")) {
				mSQL = mSQL + " and a.usercode='" + mCodeValue + "' ";
			}

			mSQL = mSQL + " order by a.usercode";
		}

		/**
		 * 健康管理相关查询
		 */
		// 查询对应机构下的用户
		// logger.debug("come here queryData" +
		// mLDCodeSchema.getCodeType());
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"usercode") == 0) {
			mSQL = "select  Usercode,Username from LDUser where "
					+ mConditionField + " = " + mCodeCondition
					+ " order by Usercode";
		}

		// 咨询专家
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"familydoctorno") == 0) {
			mSQL = "select  DoctNo,DoctName from LDDoctor where 1=1 and CExportFlag='1' and "
					// + " and a.caseno='" + mCodeCondition +"'"
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " order by DoctNo";
		}

		// 咨询专家
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"lluwlevel") == 0) {
			mSQL = "select  popedomlevel,popedomname from LLClaimPopedom where popedomlevel like 'A%' or popedomlevel like 'B%' ";
		}

		// 查询对应机构下的核保师
		// logger.debug("come here queryData" +
		// mLDCodeSchema.getCodeType());
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"uwcode") == 0) {
			mSQL ="select UserCode,(select UserName from lduser where UserCode =a.usercode),ManageCom,Uwpopedom,'1' from lduwuser a"
				 + " where uwtype ='1'";
//			
//			mSQL = " select Usercode,Username,ComCode,UWPopedom,'1'  from LDUser "
//					+ " where Usercode in " +
//
//					" (select usercode from lduwuser where uwtype='1' ) ";

		}
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"uwcode2") == 0) {
			// mSQL = " select Usercode,Username,ComCode from LDUser " +
			// " where Usercode in " +
			// " (select usercode from lduwuser) and " +
			// mConditionField + "= substr(" + mCodeCondition + ",0,4)";
			mSQL = " select Usercode, Username, ComCode" + " from LDUser"
					+ " where Usercode in (select usercode from lduwuser)"
					+ " and (trim(comcode) = substr(" + mCodeCondition
					+ ", 0, 4) or comcode = '86')";

		}

		/**
		 * 2006-03-29 保障计划险种初始化
		 */
		// logger.debug("CodeType===" + mLDCodeSchema.getCodeType());
		if (mLDCodeSchema.getCodeType().indexOf("*") != -1
				&& mLDCodeSchema.getCodeType().substring(0, 4).equals("****")) {
			String yWay = StrTool.cTrim(mLDCodeSchema.getCodeType()).substring(
					0, 4);
			String ycontno = StrTool.cTrim(mLDCodeSchema.getCodeType())
					.toLowerCase().substring(4,
							mLDCodeSchema.getCodeType().indexOf("&"));
			String yplancode = StrTool.cTrim(mLDCodeSchema.getCodeType())
					.substring(mLDCodeSchema.getCodeType().indexOf("&") + 1,
							mLDCodeSchema.getCodeType().length());
			String yupplancode = StrTool.cTrim(mLDCodeSchema.getCodeType())
					.toUpperCase().substring(
							mLDCodeSchema.getCodeType().indexOf("&") + 1,
							mLDCodeSchema.getCodeType().length());

			if (yWay.compareTo("****") == 0) {
				logger.debug("团险保障计划险种初始化");
				mSQL = "select riskcode,riskname from lmrisk where riskcode in (select riskcode from lccontplanrisk where contplancode='"
						+ yplancode
						+ "' and grpcontno='"
						+ ycontno
						+ "') union select riskcode,riskname from lmrisk where riskcode in (select riskcode from lccontplanrisk where contplancode='"
						+ yupplancode + "' and grpcontno='" + ycontno + "')";
			}
		}
	   if(StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().toLowerCase().compareTo("grpriskcode") == 0)
	            mSQL = "select riskcode,riskname from lmriskapp where "+ mConditionField + "=" + mCodeCondition +"and riskprop in ('G','D','A') order by riskcode";
	 
		// /////////////////附加险界面增加的功能 write by yaory////////////////
		if (mLDCodeSchema.getCodeType().indexOf("-") != -1) {
			String yWay = StrTool.cTrim(mLDCodeSchema.getCodeType()).substring(
					0, mLDCodeSchema.getCodeType().indexOf("-"));
			String yriskcode = StrTool.cTrim(mLDCodeSchema.getCodeType())
					.toLowerCase().substring(
							mLDCodeSchema.getCodeType().indexOf("-") + 1,
							mLDCodeSchema.getCodeType().length());

			// logger.debug("附加险界面的标志===="+yWay);
			// logger.debug("附加险界面的主险编码===="+yriskcode);
			String yApprisk = yriskcode.substring(0, 2);
			// logger.debug("如果险种前两位是01则是险种组合查询所有01开头的险种=="+yApprisk);
			if (yWay.compareTo("***") == 0 && !yApprisk.equals("01")
					&& !yApprisk.equals("02") && !yApprisk.equals("03"))

			{
				// tongmeng 2008-07-02 modify
				// 除了查询自身的主险外,还查询出其他主险
				// logger.debug("ok!");
				mSQL = "select relariskcode,b.riskname From lmriskrela a,lmrisk b where a.riskcode='"
						+ yriskcode.toUpperCase()
						+ "' and a.relariskcode=b.riskcode"
						+ " union "
						+ " select riskcode,riskname from lmriskapp where subriskflag='M' and riskcode<>'"
						+ yriskcode.toUpperCase() + "' "
//						+ " union "
//						+ " select riskcode,riskname from pd_lmriskapp "
						;
			}
			if (yWay.compareTo("***") == 0
					&& (yApprisk.equals("01") || yApprisk.equals("02") || yApprisk
							.equals("03"))) {
				// logger.debug("ok!");
				mSQL = "select riskcode,riskname from lmrisk where riskcode like '"
						+ yApprisk + "%' and riskcode!='" + yriskcode + "'";
			}

			/** 团险附加险显示 */
			if (yWay.toUpperCase().compareTo("GAI") == 0
					&& !yApprisk.equals("01") && !yApprisk.equals("02")
					&& !yApprisk.equals("03"))

			{
				// logger.debug("ok!");
				mSQL = "select relariskcode,b.riskname From lmriskrela a,lmrisk b where a.riskcode='"
						+ yriskcode.toUpperCase()
						+ "' and a.relariskcode=b.riskcode  and a.relacode='01' and a.relariskcode in"
						+ "(select riskcode from lcgrppol where "
						+ mConditionField + "=" + mCodeCondition + ")";
				this.m_bCanBeCached = false;
			}
			if (yWay.toUpperCase().compareTo("GAI") == 0
					&& (yApprisk.equals("01") || yApprisk.equals("02") || yApprisk
							.equals("03"))) {
				mSQL = "select riskcode,riskname from lmrisk where riskcode like '"
						+ yApprisk
						+ "%' and riskcode!='"
						+ yriskcode
						+ "' and riskcode in"
						+ "(select riskcode from lcgrppol where "
						+ mConditionField + "=" + mCodeCondition + ")";
				this.m_bCanBeCached = false;
			}

		}
		// /////////////////卡单险种初始化//////////////////
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"cardriskcode") == 0) {
			// mSQL =
			// "select riskcode,riskname from lmriskapp where poltype='C' and
			// RiskProp<>'G' order by riskcode";
			mSQL = "select riskcode,riskname from lmriskapp where poltype='C' order by riskprop,riskcode";
		}
		// 卡单交费的险种查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"cardriskcodefin") == 0) {
			mSQL = "select riskcode aa,riskname from lmriskapp where poltype='C' "
					+ "union select contplancode aa,contplanname from ldplan "
					+ "where state = '9'  and (trim(managecom) = substr('"
					+ this.mGlobalInput.ManageCom
					+ "',1,2) or  managecom like concat(substr('"
					+ this.mGlobalInput.ManageCom + "',1,4),'%')) order by aa";

		}

		// /////////////////套餐险种初始化//////////////////
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"planriskcode") == 0) {
			String tManageCom = this.mGlobalInput.ManageCom;
			// mSQL =
			// "select riskcode,riskname from lmriskapp where poltype='C' and
			// RiskProp<>'G' order by riskcode";
			// 对于分公司可以销售本公司和总公司定义的产品组合
			if (tManageCom.length() >= 4) {
				mSQL = "select contplancode,contplanname from ldplan where state='9' "
						+ "and managecom = '"
						+ tManageCom.substring(0, 4)
						+ "' "
						+ "union "
						+ "select contplancode,contplanname from ldplan where state='9' "
						+ "and managecom = '86' " + "order by contplancode";
			} else {
				mSQL = "select contplancode,contplanname from ldplan where state='9' "
						+ "and managecom like '86%' " + "order by contplancode";
			}
		}

		// /////////////////套餐投保规则计算编码//////////////////
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"planrulefactor") == 0) {
			// mSQL =
			// "select riskcode,riskname from lmriskapp where poltype='C' and
			// RiskProp<>'G' order by riskcode";
			mSQL = "select factorycode,factoryname,calremark,params,factorytype,factorysubcode from lmfactorymode "
					+ "where riskcode='000000' and factorytype='000009'";
		}

		// 可以进行产品组合的险种代码
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"riskgrpplan") == 0) {
			mSQL = "select a.RiskCode, a.RiskName,a.SubRiskFlag from LMRiskApp a,LMRiskSort b where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " and RiskProp in ('G','A','B','D')"
					+ " and b.riskcode=a.riskcode and b.risksorttype='22'"
					+ " order by RiskCode";
		}

		// 保险套餐RiskPlan
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"riskplan") == 0) {
			mSQL = "select ContPlanCode,ContPlanName from LDPlan"
					+ " where concat(managecom ='86' or managecom like '" + mGlobalInput.ManageCom
					+ "%' or '"+mGlobalInput.ManageCom+"' like managecom,'%') and state='9' order by ContPlanCode";
		}

		// 产品组合定义销售渠道
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"plansalechnl") == 0) {
			mSQL = "select code,codename from ldcode where codetype='plansalechnl'";
		}

		// 产品组合保险期间单位
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"insuyearflag") == 0) {
			mSQL = "select code,codename from ldcode where codetype='insuyearflag' order by code";
		}

		// 产品组合状态
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"planstate") == 0) {
			mSQL = "select code,codename from ldcode where codetype='planstate'"
					+ " order by char_length(code)";
		}

		// /////////////////团险界面增加的功能 write by yaory////////////////
		if (mLDCodeSchema.getCodeType().indexOf("-") != -1) {
			String yWay = StrTool.cTrim(mLDCodeSchema.getCodeType()).substring(
					0, mLDCodeSchema.getCodeType().indexOf("-"));
			String ycontno = StrTool.cTrim(mLDCodeSchema.getCodeType())
					.toLowerCase().substring(
							mLDCodeSchema.getCodeType().indexOf("-") + 1,
							mLDCodeSchema.getCodeType().length());

			// logger.debug("附加险界面的标志====" + yWay);
			// logger.debug("合同号====" + ycontno);
			if (yWay.compareTo("**") == 0) {
				// logger.debug("ok!");
				mSQL = "select riskcode,riskname from lmrisk where riskcode in (select riskcode from lcgrppol where prtno='"
						+ ycontno + "')";
			}
		}

		if (mLDCodeSchema.getCodeType().indexOf("-") != -1
				&& mLDCodeSchema.getCodeType().indexOf("*") != -1
				&& mLDCodeSchema.getCodeType().indexOf("&") != -1) {
			// /////////////////险种界面定义 write by yaory
			//logger.debug("哈哈:"+StrTool.cTrim(mLDCodeSchema.getCodeType()
			// ).substring(0,mLDCodeSchema.getCodeType().indexOf("-")));
			// /////险种参数名-payintv;payendyear等///////////
			String yWay = StrTool.cTrim(mLDCodeSchema.getCodeType()).substring(
					0, mLDCodeSchema.getCodeType().indexOf("-"));
			// ////险种编码////////////
			String yRiskcode = StrTool.cTrim(mLDCodeSchema.getCodeType())
					.toUpperCase().substring(
							mLDCodeSchema.getCodeType().indexOf("-") + 1,
							mLDCodeSchema.getCodeType().indexOf("*"));
			// ///责任代码//////
			String yDutycode = StrTool.cTrim(mLDCodeSchema.getCodeType())
					.toLowerCase().substring(
							mLDCodeSchema.getCodeType().indexOf("*") + 1,
							mLDCodeSchema.getCodeType().indexOf("&"));
			// logger.debug("yDutycode==="+yDutycode);
			// ////其他编码////////
			String yOthercode = StrTool.cTrim(mLDCodeSchema.getCodeType())
					.toLowerCase().substring(
							mLDCodeSchema.getCodeType().indexOf("&") + 1,
							mLDCodeSchema.getCodeType().length());
			// logger.debug(Othercode==="+yOthercode);
			// ////标记-如果是“！”则是这个分支///////
			String yRemark = StrTool.cTrim(mLDCodeSchema.getCodeType())
					.toLowerCase().substring(0, 1);

			if (yRemark.compareTo("!") == 0) {
				
				if (yRemark.compareTo("!") == 0) {
					
					if(mLDCodeSchema.getCodeType().toLowerCase().indexOf("currency")==-1)
					{
						mSQL = "select distinct ParamsCode,ParamsName from LMRiskParamsDef "
								+ " where lower(riskcode)='" + yRiskcode.toLowerCase() + "' "
								+ " and lower(ParamsType)='"
								+ yWay.substring(1, yWay.length()).toLowerCase() + "'";
						if (!yDutycode.equals("0")) {
							mSQL = mSQL + " and DutyCode='" + yDutycode + "'";
						}
						if (!yOthercode.equals("0")) {
							mSQL = mSQL + " and OtherCode='" + yOthercode + "'";
						}
						
						mSQL = mSQL + " order by char_length(ParamsCode),ParamsCode ASC";
					}
					else
					{
						//tongmeng 2011-02-28 modify
						//支持险种的多币种的处理
						mSQL = "select currcode,codealias from ldcurrency where currcode in "
							 + " ( "
							 + " select risksortvalue from lmrisksort where riskcode ='"+yRiskcode+"' and risksorttype='CU' "
							 + " union "
							 //如果没有描述,默认为人民币
							 + " select '01' from dual where not exists (select 1 from lmrisksort where riskcode ='"+yRiskcode+"' and risksorttype='CU') "
							 + " ) ";
					}
				}
//				mSQL = "select ParamsCode,ParamsName from LMRiskParamsDef where riskcode='"
//						+ yRiskcode
//						+ "' and ParamsType='"
//						+ yWay.substring(1, yWay.length()) + "'";
//				if (!yDutycode.equals("0")) {
//					mSQL = mSQL + " and DutyCode='" + yDutycode + "'";
//				}
//				if (!yOthercode.equals("0")) {
//					mSQL = mSQL + " and OtherCode='" + yOthercode + "'";
//				}
//				mSQL = mSQL + " order by length(ParamsCode),ParamsCode ASC";
			}
		}
		//logger.debug("$$$mSQL:" + mSQL);
		// 险种参数查询
		if (mLDCodeSchema.getCodeType().indexOf("-") != -1
				&& mLDCodeSchema.getCodeType().indexOf("*") != -1
				&& mLDCodeSchema.getCodeType().indexOf("#") != -1) {
			String SCodeType = StrTool.cTrim(mLDCodeSchema.getCodeType());
			// /////险种参数名-payintv;payendyear等///////////
			String yWay = SCodeType.substring(0, SCodeType.indexOf("-"));
			// ////险种编码////////////
			String yRiskcode = SCodeType.substring(SCodeType.indexOf("-") + 1,
					SCodeType.indexOf("*"));
			// ///责任代码//////
			String yDutycode = SCodeType.substring(SCodeType.indexOf("*") + 1,
					SCodeType.indexOf("#"));
			// ////其他编码////////
			String yOthercode = SCodeType.substring(SCodeType.indexOf("#") + 1,
					SCodeType.length());
			// ////标记-如果是“！”则是这个分支///////
			String yRemark = SCodeType.substring(0, 1);
			
			if (yRemark.compareTo("!") == 0) {
				mSQL = "select ParamsCode,ParamsName from LMRiskParamsDef "
						+ " where riskcode='" + yRiskcode + "' "
						+ " and ParamsType='"
						+ yWay.substring(1, yWay.length()) + "'";
				if (!yDutycode.equals("0")) {
					mSQL = mSQL + " and DutyCode='" + yDutycode + "'";
				}
				if (!yOthercode.equals("0")) {
					mSQL = mSQL + " and OtherCode='" + yOthercode + "'";
				}
			}
		}
		
		
		//09-08-24 lixiang 卡单录入界面的关系下拉可选项  141812-金如意
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
		"cardrelation1") == 0) {
//			mSQL = "select code,codename from ldcode where codetype='relation'"
//				+ " and othersign is not null order by othersign/1";
			mSQL = "select code,codename from ldcode where codetype='cardrelation'"
				+ "and codealias like '%141812%'"
				+ " and othersign is not null order by othersign/1";
		}
		//09-08-24 lixiang 卡单录入界面的身故受益人关系下拉可选项 141812-金如意
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
		"cardbnfrelation1") == 0) {
			mSQL = "select code,codename from ldcode where codetype='relation'"
				+ " and othersign is not null and code!='00' order by othersign/1";
		}
		//09-08-25 lixiang 卡单录入界面的关系下拉可选项  111603-学生计划
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
		"cardrelation2") == 0) {
			mSQL = " select code,codename from ldcode where codealias like '%111603%'"
				+ " and codetype = 'cardrelation' order by othersign/1";
		}
		//09-08-25 lixiang 卡单录入界面的身故受益人关系下拉可选项 111603-学生计划
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
		"cardbnfrelation2") == 0) {
			mSQL = "select code,codename from ldcode where codetype='cardrelation'"
				+ " and code!='00' and codealias like '%111603%'"
				+ " order by othersign/1";
		}
		
		// 投联账户类型查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"insuacc") == 0) {
			mSQL = "select InsuAccNo, InsuAccName from LMRiskInsuAcc where "
					+ mConditionField + " = " + mCodeCondition
					+ " and acckind = '2' order by InsuAccNo asc";
		}

		// 认证级别
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"ldattestleve") == 0) {
			mSQL = "select  AttestLevelCode,AttestLevel from LDAttestLeve where 1=1 and "
					// + " and a.caseno='" + mCodeCondition +"'"
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " order by AttestLevelCode";
		}
		// 卫生机构类别查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"ldhealthorganclass") == 0) {
			mSQL = "select  HealthOrganClass,HealthOrganClassName from LDHealthOrganClass where 1=1 and "
					// + " and a.caseno='" + mCodeCondition +"'"
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " order by HealthOrganClass";
		}
		// 单位隶属关系查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"ldorganisubjec") == 0) {
			mSQL = "select  SubjecCode,SubjecName from LDOrganiSubjec where 1=1 and "
					// + " and a.caseno='" + mCodeCondition +"'"
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " order by SubjecCode";
		}
		// 业务类型代码查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"ldbusitype") == 0) {
			mSQL = "select  BusiTypeCode,BusiType from LDBusiType where 1=1 and "
					// + " and a.caseno='" + mCodeCondition +"'"
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " order by BusiTypeCode";
		}

		/** *******************理赔相关查询*************************************** */
		// 理赔要约录入计算要素查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"clmfactorycode") == 0) {
			mSQL = "select a.factorycode,a.factoryname "
					+ "from lmfactorymode a where a.factorytype = '000004' "
					+ "and riskcode = '" + mConditionField + "'"
					+ " order by factorycode";
			executeType = 1;
		}

		// 理赔要约录入子计算要素查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"clmimpartcode") == 0) {
			mSQL = "select a.factorysubcode,a.calremark,CreateDH(a.paramsnum),FactorySubName "
					+ "from lmfactorymode a where a.factorytype = '000004' "
					+ " and riskcode = "
					+ mConditionField
					+ " = "
					+ mCodeCondition + " order by factorysubcode";
			executeType = 1;
		}

		// 单证的类型查询，es_doc_def表，2005-09-13,zw
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"busstypedetail") == 0) {
			mSQL = "select distinct busstype , busstypename from es_doc_def ";
			executeType = 1;
		}

		// 单证的具体类型查询，es_doc_def表，2005-09-13,zw
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"subtypedetail") == 0) {
			mSQL = "select SubType,SubTypeName from es_doc_def where 1=1 and "
					+ mConditionField + " = " + mCodeCondition.trim()
					+ " order by SubType";
			executeType = 1;
		}

		// 出险结果1查询,icd10表,2005-6-21,zl
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"lldiseases1") == 0) {
			if (mConditionField.equals("1")) { // 意外
				mSQL = "select ICDCode,ICDName from LDDisease where "
						+ " icdlevel = 0 and ASCII(icdcode) < 86"
						+ " and ASCII(icdcode) > 82" + " order by ICDCode";
				executeType = 1;
			}
			if (mConditionField.equals("2")) { // 疾病
				mSQL = "select ICDCode,ICDName from LDDisease where "
						+ " icdlevel = 0 and (ASCII(icdcode) < 83 or ASCII(icdcode)=90 )"
						+ " order by ICDCode";
				executeType = 1;
			}
		}

		// 出险结果2查询,icd10表,2005-6-21,zl
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"lldiseases2") == 0) {
			mSQL = "select  ICDCode,ICDName from LDDisease where "
					+ " icdlevel = 1 and ASCII(icdcode) <= 90 and trim("
					+ mConditionField + ") = " + mCodeCondition.trim()
					+ " order by ICDCode";
			executeType = 1;
		}

		// 出险细节查询,icd10表中V01-Z99,2005-6-21,zl
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"llaccidentdetail") == 0) {
			mSQL = "select  ICDCode,ICDName from LDDisease where "
					+ " icdlevel <= 1 and ASCII(icdcode) >= 86"
					+ " order by ICDCode";
			executeType = 1;
		}

		// 医疗单证录入中推荐医院查询,2005-6-22,zl “order by hospitalcode desc”
		// ZHaoRx，2005-12-23添加
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"commendhospital") == 0) {
			mSQL = "select HospitalCode,HospitalName from LLCommendHospital order by hospitalcode desc ";
			executeType = 1;
		}

		// 医疗单证录入中伤残代码查询,2005-7-04,zl
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"lldefocode") == 0) {
			mSQL = "select defocode,defoname from LLParaDeformity where 1=1 "
					+ " and DefoType = " + mCodeCondition.trim()
					+ " and defograde = '" + mConditionField.trim() + "' "
					+ " order by defocode ";
			executeType = 1;
		}

		// 医疗单证录入中伤残级别查询,2005-7-07,zl Modify by zhaorx 2006-09-13
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"lldefograde") == 0) {
			mSQL = "select distinct defograde,defogradename from LLParaDeformity where "
					+ mConditionField
					+ " = "
					+ mCodeCondition.trim()
					+ " order by defograde";
			executeType = 1;
		}

		// 出险疾病查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"lldiseas") == 0) {
			mSQL = "select  ICDName,ICDCode from LDDisease where 1=1 and "
					// + " and a.caseno='" + mCodeCondition +"'"
					+ mConditionField
					+ " like '%"
					+ mCodeCondition
							.substring(1, (mCodeCondition.length() - 1)).trim()
					+ "%' order by ICDName";
			executeType = 1;
		}

		// 理赔材料名称
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"llmaffix") == 0) {
			mSQL = "select  affixcode,affixname from llmaffix where 1=1 and "
					// + " and a.caseno='" + mCodeCondition +"'"
					+ mConditionField + " = " + mCodeCondition
					+ " order by affixcode";
			executeType = 1;
		}

		// 理赔材料类型
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"llmaffixtype") == 0) {
			mSQL = "select distinct affixtypecode,affixtypename from llmaffix where 1=1 and "
					// + " and a.caseno='" + mCodeCondition +"'"
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " order by affixtypecode";
			executeType = 1;
		}

		// 理赔保单查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"llclaimpolicy") == 0) {
			mSQL = "select a.contno,'' from llcasepolicy a where 1=1 and "
					// + " and a.caseno='" + mCodeCondition +"'"
					+ mConditionField + " = " + mCodeCondition
					+ " order by a.contno";
			//executeType = 1;
		}


		
		// 查询理赔权限表中(llclaimuser)某机构及其下级机构中具有调查权限(surveyflag='1')的用户
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"llclaimuser") == 0) {

			mSQL = "select usercode,username from llclaimuser a where "
					+ " surveyflag='1' and " + mConditionField + " like "
					+ mCodeCondition.trim() + " order by userCode";
			executeType = 1;
		}
		
		// 查询团险理赔权限表中(llgrpclaimuser)某机构及其下级机构中具有调查权限(surveyflag='1')的用户
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"llgrpclaimuser") == 0) {

			mSQL = "select usercode,username from llgrpclaimuser a where "
					+ " surveyflag='1' and " + mConditionField + " like "
					+ mCodeCondition.trim() + " order by userCode";
			executeType = 1;
		}
		

		// 查询理赔师权限表中(LLClaimPopedom)的理赔权限级别
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"popedomlevel") == 0) {
			mSQL = "select popedomlevel,popedomlevel from llclaimpopedom where "
					+ mConditionField
					+ " like "
					+ mCodeCondition.trim()
					+ "  group by popedomlevel";
			executeType = 1;
		}

		// 转年金给付责任按保单号查询（理赔组）,2006-03-08,zl
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"llclmtopension") == 0) {
			mSQL = "select a.getdutycode,a.getdutyname from lmdutygetrela a "
					+ " where dutycode in ("
					+ " select dutycode from lmriskduty where choflag = 'L'"
					+ " and riskcode in ("
					+ " select riskcode from lcpol where " + mConditionField
					+ " = " + mCodeCondition.trim() + " ))";
			executeType = 1;
		}

		// （理赔）业务员品质管理-业务员品质录入-建议等级区分 zhaorx 2006-03-08
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"laagqualitysubr") == 0) {
			mSQL = "select trim(code),trim(codename) from ldcode  where codetype = 'laagqualitysub' and code "
					+ " in ('101','102','199') order by code ";
		}
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"laagqualitysubu") == 0) {
			mSQL = "select trim(code),trim(codename) from ldcode  where codetype = 'laagqualitysub' and code "
					+ " in ('201') order by code ";
		}
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"laagqualitysubbl") == 0) {
			mSQL = "select trim(code),trim(codename) from ldcode  where codetype = 'laagqualitysub' and code "
					+ " in ('301','302','303','304','305','306','307','308','309','399') order by code ";
		}
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"laagqualitysubb") == 0) {
			mSQL = "select trim(code),trim(codename) from ldcode  where codetype = 'laagqualitysub' and code "
					+ " in ('401','402','403','404','405','406','407','408','409','499') order by code ";
		}

		// 险种查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"llclaimrisk") == 0) {
			mSQL = "select distinct a.riskcode,a.riskname from LMRisk a where 1=1 and "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " order by a.riskcode";
		}

		// 责任给付类别
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"llgetdutykind") == 0) {
			mSQL = "select distinct a.GetDutyKind,b.codeName from LMDutyGetClm a , ldcode b where getdutycode in (select getdutycode from lmdutygetrela where dutycode in (select dutycode from lmriskduty where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " )) and b.codetype='getdutykind' and code=a.getdutykind order by getdutykind";
		}

		// 给付类型
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"llclaimdecision_1") == 0) {
			mSQL = "select a.Code, a.CodeName, a.CodeAlias, a.ComCode, a.OtherSign from ldcode a where  trim(a.codetype)=(select trim(bcodeaLias) from ldcode b where b.codetype='llclaimdecision' and b.code="
					+ mCodeCondition + ")";
		}
		/*
		 * 累加器增加险种编码下拉 whc 2016-5-12*/
		//获取同层次的累加器 pd_aftercode
		if(StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("pd_aftercode") == 0){
			String[] tCodeCondition =mCodeCondition.replace("|", "-").split("-");
			String[] tConditionField=mConditionField.split(",");
			
			mSQL ="select CalculatorCode,CalculatorName from PD_LCalculatorMain where CALCULATORCODE in (select calculatorcode from PD_LCalculatorFactor where riskcode = "+tCodeCondition[0]
                            +" and dutycode = "+tCodeCondition[1]
                            +" and getdutycode = "+tCodeCondition[2]
                            +" and GetDutyKind = "+tCodeCondition[3]+")"
                            +" and CTRLLEVEL = "+tCodeCondition[4];
		}
		
		//费用项  tfeetype
		if(StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("tpd_llmedfeetype") == 0){
			mSQL="select code,codename from ldcode where codetype='pd_llmedfeetype' union all select '0000','其他' from dual";
		}
		//账单与累加器进行管理
		if(StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("feecode") == 0){
			mSQL="select feecode from pd_ldplanfeerela where getdutycode='"+mCodeCondition+"'";
		}
		
		// 获取累加器编码 tcalculatorcode
		if(StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("tcalculatorcode") == 0){
			mSQL="select CALCULATORCODE,CALCULATORNAME from Pd_Lcalculatormain where 1=1";
		}
		// 手术
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"lloperation") == 0) {
			mSQL = "select ICDOPSName, ICDOPSCode, OpsGrag from LDICDOPS order by ICDOPSCode";
		}

		// 赔案阶段
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"activityid") == 0) {
			mSQL = "select activityid,activityname from lwactivity where activityid like '00000050%' or activityid like '00000090%' ";
			executeType = 1;
		}

		// 其它录入要素类型
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"llfactor") == 0) {
			mSQL = "select a.codename, a.code from ldcode a where a.codetype =( select CODEALIAS from ldcode where codetype='llotherfactor' and code="
					+ mCodeCondition + ") order by a.code";
		}

		// 核保师编码
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"uwusercode") == 0) {
			mSQL = "select UserCode, trim(UserName) from LDUser order by UserCode";
		}

		// 团单客户编码
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"supcustomerno") == 0) {
			mSQL = "select CustomerNo, trim(GrpName) from  LDGrp order by CustomerNo";
		}

		// 健康险要素目标编码
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"healthfactoryno") == 0) {

			if (mCodeCondition.substring(1, 7).equals("000000")) { // 基于保单的计算
				mSQL = "select '__','请录入保单号' from dual";
			} else if (mCodeCondition.substring(1, 7).equals("000001")) { // 基于保单的计算
				mSQL = "select DutyCode,DutyName from LMDuty where DutyCode in(select DutyCode from LMRiskDuty "
						+ " where RiskCode='"
						+ mCodeCondition.substring(7)
						+ ") order by DutyCode";
			} else if (mCodeCondition.substring(1, 7).equals("000002")) { // 基于给付的计算
				mSQL = "select getdutycode,getdutyname from lmdutygetrela where dutycode in (select dutycode from lmriskduty where riskcode ='"
						+ mCodeCondition.substring(7)
						+ ") order by getdutycode";
			} else if (mCodeCondition.substring(1, 7).equals("000003")) { // 基于账户的计算
				mSQL = "select insuaccno,insuaccname from LMRiskToAcc where RiskCode='"
						+ mCodeCondition.substring(7) + " order by insuaccno";
			} else if (mCodeCondition.substring(1, 7).equals("000004")) { // 基于理赔责任的计算
				mSQL = "select getdutycode,getdutyname from lmdutygetrela where dutycode in (select dutycode from lmriskduty where riskcode ='"
						+ mCodeCondition.substring(7)
						+ ") order by getdutycode";
			}
		}

		// 疾病代码查询ICDCode
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"icdcode") == 0) {
			mSQL = "select a.icdcode, a.icdname from  lddisease a where "
					+ mConditionField
					+ " like '%"
					+ mCodeCondition
							.substring(1, (mCodeCondition.length() - 1)).trim()
					+ "%' order by a.icdcode";
			executeType = 1;
		}

		// 体检项目结果查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"itemresult") == 0) {
			String code = "";
			logger.debug(mCodeCondition.trim());
			if (mCodeCondition.trim().equals("'011'")
					|| mCodeCondition.trim().equals("'012'")
					|| mCodeCondition.trim().equals("'013'")) {
				code = "peitemc";
			} else if (mCodeCondition.trim().equals("'060'")) {
				code = "peitemb";
			} else {
				code = "peitema";
			}
			mSQL = "select code,codename from ldcode where codetype = '" + code
					+ "'";
			executeType = 1;
		}

		// 其他险种信息参数查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"queryriskother") == 0) {
			logger.debug(mCodeCondition.trim());
			mSQL = "select code,codename from ldcode where codetype = "
					+ mCodeCondition.trim().toLowerCase();
			executeType = 1;
		}

		// 疾病代码查询ICDCode
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"diseascode") == 0) {
			mSQL = "select icdcode, icdname from lddisease order by a.icdcode";
			executeType = 1;
		}

		// 疾病代码查询ICDCode
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"diseasname") == 0) {
			mSQL = "select icdname,icdcode from lddisease order by icdname";
			executeType = 1;
		}

		// 疾病代码查询ICDCode
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"hospital") == 0) {
			mSQL = "select a.hospitcode,a.hospitalname,b.codename from  LDHospital a ,ldcode b where b.codetype='llhospiflag' and b.code=a.fixflag order by a.hospitcode";
			executeType = 1;
		}

		// 意外代码查询ICDCode
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"llacci") == 0) {
			mSQL = "select a.accidentno, a.accname from  llaccidenttype a where "
					+ mConditionField
					+ " like '%"
					+ mCodeCondition
							.substring(1, (mCodeCondition.length() - 1)).trim()
					+ "%' order by a.accidentno";
			executeType = 1;
		}

		// 医生代码
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"doctor") == 0) {
			mSQL = "select a.doctname,a.doctno from  lddoctor a where  "
					+ mConditionField + " = " + mCodeCondition
					+ " order by a.doctname";
		}

		// 健康险计算要素编码
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"healthfactory") == 0) {
			mSQL = "select concat(FactoryCode,to_char(FactorySubCode),CalRemark,Params) from LMFactoryMode where FactoryType = '"
					+ mCodeCondition.substring(1, 7)
					+ "' and RiskCode='"
					+ mCodeCondition.substring(7)
					+ " order by FactoryCode,FactorySubCode ";
		}

		// 保监会Excel表数据信息导入配置模板相对文件名
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"circreport_config") == 0) {
			mSQL = "select code,codeName,codealias from ldcode where codetype='circreport_config' order by code";
		}

		// 保监会管理机构信息
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"itemcode") == 0) {
			mSQL = "select itemcode,trim(ItemName) from lfItemRela order by itemcode";
			executeType = 1;
		}
		// 保监会管理机构信息
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"outitemcode") == 0) {
			mSQL = "select outitemcode,trim(ItemName) from lfItemRela order by outitemcode";
			executeType = 1;
		}
		// ffffffffffffffffffffffffffeeeeeeeeeeeeeeeeeeeeeeeeeeeiiiiiiiiiiiiiii
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"stati") == 0
				|| StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase()
						.compareTo("allstation") == 0) {
			if (mCodeCondition.toLowerCase().indexOf("comcode") == -1) {
				mCodeCondition = mCodeCondition.toLowerCase().replaceAll(
						"code", "comcode");
			}
			mSQL = "select comcode,name from ldcom where " + mConditionField
					+ " = " + mCodeCondition + " order by comcode";

			m_bCanBeCached = false;
			executeType = 1;
		}

		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"optname") == 0) {
			mSQL = "select username,usercode,claimpopedom from llclaimuser where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " order by claimpopedom desc";
		}

		// 本级以及本级以下的管理机构
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"managecom1") == 0) {
			mSQL = "select comcode,name from ldcom where 1=1 "
					+ " and comcode like '" + mGlobalInput.ManageCom + "%'";
		}

		// 保监会管理机构信息
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"comcodeisc") == 0) {
			mSQL = "select comcodeisc,trim(name) from LFComISC order by comcodeisc";
			executeType = 1;
		}

		// 保单状态导致原因PolState
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"polstate2") == 0) {
			mSQL = "select code,codename,codealias from ldcode where codetype = 'polstate' order by code";
		}

		// 保单状态导致原因PolStateReason
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"polstatereason") == 0) {
			mSQL = "select code,codename,codealias from ldcode where "
					+ mConditionField + " = " + mCodeCondition
					+ " and codetype = 'polstatereason' order by code";
		}

		// 险种
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"rilmrisk") == 0) {
			String a= mExeSQL.getOneValue("select ORA_LAN.GET_LAN() from dual");
			if("cn".equals(a)){
				a="";
			}
			System.out.println(a);
			mSQL = "select riskcode, risk"+a+"name from lmrisk order by riskcode ";
		}
		// 责任
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"rilmduty") == 0) {
			mSQL = "select dutycode,(select dutyname from lmduty where dutycode = a.dutycode)from lmriskduty a where 1=1 and  "
					+ mConditionField
					+ "="
					+ mCodeCondition
					+ " order by dutycode ";
		}
		
		// 责任领取类型DutyKind
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"dutykind") == 0) {
			mSQL = "select GetDutyKind, GetDutyName from LMDutyGetAlive where "
					+ mConditionField + " = " + mCodeCondition
					// + " and GetDutyCode in ( select GetDutyCode from
					// LMDutyGetRela where dutycode in "
					// + " ( select dutycode from LMRiskDuty where
					// riskcode='212401' ) ) "
					+ " order by GetDutyKind";
		}
		//add zhangyingfeng 2016-07-26
		//保全 GM 领取方式下拉 年金
		// 责任领取类型DutyKind
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"livegetmode_gm") == 0) {
			mSQL = "SELECT a.paramscode,paramsname FROM lmriskparamsdef a,lcget b,lmdutyget c  WHERE  "+ mConditionField + " = " + mCodeCondition
					+ " and a.paramstype ='livegetmode' AND a.dutycode=b.dutycode  AND b.getdutycode=c.getdutycode AND c.GetType1='1' ";
		}
		//end zhangyingfeng 2016-07-26
		// <!-- XinYQ added on 2006-04-06 : 保全项目下拉代码 : BGN -->

		// 所有保全项目: EdorType
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"edortype") == 0) {
			mSQL = "select distinct EdorCode, EdorName " + "from LMEdoritem "
					+ "where " + mConditionField + " = " + mCodeCondition + " "
					+ "order by EdorCode asc";
		}

		// 个险保全项目: PEdorType
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"pedortype") == 0) {
			mSQL = "select distinct EdorCode, EdorName " + "from LMEdorItem "
					+ "where " + mConditionField + " = " + mCodeCondition + " "
					+ "and AppObj in ('I','B') " + "order by EdorCode asc";
		}

		// 团险保全项目: GEdorType
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"gedortype") == 0) {
			mSQL = "select distinct EdorCode, EdorName " + "from LMEdoritem "
					+ "where " + mConditionField + " = " + mCodeCondition + " "
					+ "and AppObj = 'G' " + "order by EdorCode asc";
		}

		// 个人银代险种项目: IYRiskCode
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"iyriskcode") == 0) {
			mSQL = "select distinct RiskCode, (select RiskStatName from LMRisk where RiskCode = a.RiskCode) "
					+ "from LMRiskApp a "
					+ "where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " "
					+ "and a.RiskProp in ('I', 'Y') "
					+ "order by a.RiskCode asc";
		}

		// <!-- XinYQ added on 2006-04-06 : 保全项目下拉代码 : END -->

		// 操作岗位OperateType
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"operatetype") == 0) {
			mSQL = "select distinct OperateType,Remark from LDRiskComOperate where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " order by OperateType";
		}
		
	  if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("casetypes") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select a.code, a.codename from ldcode a ";
				mSQL += " where 1=1 and codetype ='casetype' and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += " order by a.code";
			}
		}

		// 核保上报级别UWPopedomCode
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"uwpopedomcode") == 0) {
			mSQL = "select m.usercode,(select username from lduser where trim(usercode)=trim(m.usercode)),m.uwpopedom from lduwuser m "
					+ " where  uwtype='1' and uwpopedom>'A' ";

		}

//zhaojiawei		
		// 查询用户
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"workloaduser") == 0) {
			mSQL = "select usercode,username from lduser";

		}

		// 核保上报级别UWPopedomCode1
		// tongmeng 2007-12-14 modify
		// 修改取上级核保师的规则
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"uwpopedomcode1") == 0) {
			/*
			 * mSQL = "select usercode, username from lduser where usercode =
			 * (select UpUserCode from LDUWUser where usercode = '" +
			 * mGlobalInput.Operator.trim() + "') order by usercode";
			 */
			mSQL = "select m.usercode,(select username from lduser where trim(usercode)=trim(m.usercode)),m.uwpopedom from lduwuser m "
					+ " where trim(uwpopedom) in  (select trim(a.code) from ldcode a,ldcode b where a.codetype='uwpopedom' "
					+ " and a.codetype=b.codetype and a.comcode>b.comcode and b.code= (select uwpopedom from lduwuser where "
					+ " usercode='"
					+ mGlobalInput.Operator.trim()
					+ "' and uwtype='1')) " + " and uwtype='1' ";
		}
		//tongmeng 2008-11-24 add
		//机构组编码查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
		"comgroup") == 0) {
					mSQL = "select comgroup,comgroupname from LDComGroup where 1=1 and "
							// + " and a.caseno='" + mCodeCondition +"'"
							+ mConditionField + " = " + mCodeCondition
							+ " order by comgroup";
					executeType = 0;
			}
		
		//tongmeng 2008-10-08 modify
		//特约模板
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"spectemp") == 0) {
			mSQL = "select templetcode,noti,specreason,speccontent,temptype from LCCSpecTemplet "
				 + "where " + mConditionField
					+ " = " + mCodeCondition 
					+ " order by templetcode "
					;
		}
		// 银行分行渠道channel
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"channel") == 0) {
			mSQL = "select agentcom,name from lacom where " + mConditionField
					+ " = " + mCodeCondition
					+ "and  banktype ='01' ";
			if (mCodeValue != null && !mCodeValue.equals("")) {
				mSQL = mSQL + " and agentcom = '"+mCodeValue+"' ";
			}
					
			mSQL = mSQL + " order by agentcom";
		}

		// 工种代码引用StaticGroup
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"staticgroup") == 0) {
			// mSQL = "select comcode,shortname from ldcom where "
			// + mConditionField + " = " + mCodeCondition
			// + "and comcode like '"
			// + mGlobalInput.ManageCom +
			// "%' union select branchattr,name from labranchgroup where
			// ManageCom like '"
			// + mGlobalInput.ManageCom +
			// "%' and branchlevel='03' and branchtype='1'";
			mSBql.append("select comcode,shortname from ldcom where ");
			mSBql.append(mConditionField);
			mSBql.append(" = ");
			mSBql.append(mCodeCondition);
			mSBql.append(" and comcode like '");
			mSBql.append(mGlobalInput.ManageCom);
			mSBql
					.append("%' union select branchattr,name from labranchgroup where ManageCom like '");
			mSBql.append(mGlobalInput.ManageCom);
			mSBql.append("%' and branchlevel='03' and branchtype='1'");

			mSQL = mSBql.toString();

			executeType = 1;
		}

		// 工种代码引用Depart
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"depart") == 0) {
			// mSQL = "select branchattr,name from labranchgroup where "
			// + mConditionField + " = " + mCodeCondition
			// + " and ManageCom like '"
			// + mGlobalInput.ManageCom +
			// "%'and branchlevel>='02' and branchtype='1' order by branchattr";
			mSBql.append("select branchattr,name from labranchgroup where ");
			mSBql.append(mConditionField);
			mSBql.append(" = ");
			mSBql.append(mCodeCondition);
			mSBql.append(" and ManageCom like '");
			mSBql.append(mGlobalInput.ManageCom);
			mSBql
					.append("%'and branchlevel>='02' and branchtype='1' order by branchattr");

			mSQL = mSBql.toString();
			executeType = 1;
		}

		// 工种代码引用Occupation
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"occupation") == 0) {
			mSQL = "select code,codename from ldcode where " + mConditionField
					+ " = " + mCodeCondition + " and codetype='occupation'";

			if (mCodeValue != null && !mCodeValue.equals("")) {
				mSQL = mSQL + " and code='" + mCodeValue + "' ";
			}
		}

		// 引用BranchAttr
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"branchattr") == 0) {
			// mSQL = "select BranchAttr, Name from LABranchGroup where "
			// + mConditionField + " = " + mCodeCondition
			// + " and ManageCom like '"
			// + mGlobalInput.ManageCom + "%' order by BranchAttr";
			mSBql.append("select BranchAttr, Name from LABranchGroup where ");
			mSBql.append(mConditionField);
			mSBql.append(" = ");
			mSBql.append(mCodeCondition);
			mSBql.append(" and ManageCom like '");
			mSBql.append(mGlobalInput.ManageCom);
			mSBql.append("%' order by BranchAttr");

			mSQL = mSBql.toString();
		}
		// 服务机构 by yusen
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"zipbranch") == 0) {
			mSBql
					.append("select branchattr, name, branchmanagername from labranchgroup where ");
			// mSBql.append(mConditionField);
			// mSBql.append(" = ");
			// mSBql.append(mCodeCondition);
			mSBql.append("ManageCom like '");
			mSBql.append(mGlobalInput.ManageCom);
			mSBql.append("%' and branchtype = '1' and EndFlag = 'N'");

			mSQL = mSBql.toString();
			executeType = 1;
		}

		// 服务机构 by cuiwei 20060301
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"zipbankbranch") == 0) {
			mSBql
					.append("select branchattr, name, branchmanagername from labranchgroup where ");
			// mSBql.append(mConditionField);
			// mSBql.append(" = ");
			// mSBql.append(mCodeCondition);
			mSBql.append("ManageCom like '");
			mSBql.append(mGlobalInput.ManageCom);
			mSBql.append("%' and branchtype = '3' and EndFlag = 'N'");

			mSQL = mSBql.toString();
			executeType = 1;
		}

		// 代理人组别引用BranchCode
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"branchcode") == 0) {
			mSQL = "select BranchAttr, name from labranchgroup where "
					+ mConditionField + " = " + mCodeCondition
					+ " and ManageCom like '" + mGlobalInput.ManageCom
					+ "%' order by branchattr";
			executeType = 1;
		}

		// 代理人组别引用BranchCode
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"tollbranch") == 0) {
			mSQL = "select BranchAttr,name,AgentGroup,managecom from labranchgroup where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " and ManageCom like '"
					+ mGlobalInput.ManageCom
					+ "%' and endflag = 'N' order by branchattr";
			executeType = 1;
		}

		// //业务员兼职收费员的代码选择
		// if
		// (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
		// "tollbranch2") == 0)
		// {
		// mSQL =
		// "select BranchAttr,name,AgentGroup,ManageCom from labranchgroup where
		// "
		// + mConditionField + " = " + mCodeCondition
		// + " branchtype = '4' and branchlevel = '61' and ManageCom like '"
		// + mGlobalInput.ManageCom +
		// "%' and endflag = 'N' order by branchattr";
		// executeType = 1;
		// }
		// 代理人组别引用BranchCode
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("allbranch") == 0){
	            if (mGlobalInput.ManageCom.trim().equals("86"))
	            {
	                mSQL = "select comcode,name from ldcom where " +
	                       mConditionField + " = " + mCodeCondition +
	                       "and comcode like '" + mGlobalInput.ManageCom + "%'" +
	                       " order by comcode";
	            }

	            else
	            {
	                mSQL = "select comcode,name from ldcom where " +
	                       mConditionField + " = " + mCodeCondition +
	                       "and comcode like '" + mGlobalInput.ManageCom + "%'" +
	                       " union select branchattr,name from labranchgroup where branchtype='1' " +
	                       "and (branchlevel='03' or branchlevel='02') and managecom like '" +
	                       mGlobalInput.ManageCom +
	                       "%' and (state<>1 or state is null) order by comcode";
	            }

	            executeType = 1;
	    }
		// 员工属性引用BranchCodeType
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"branchcodetype") == 0) {
			mSQL = "select gradecode, gradename from laagentgrade where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " and branchtype='1' and gradeproperty6='1' order by gradecode";
		}

		// 代理人组别引用HealthCode
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"healthcode") == 0) {
			mSQL = "select distinct HealthCode, HealthName from LDHealth where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " order by HealthCode";
		}

		// 个险契调引用RReportCode
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"rreportcode1") == 0) {
			mSQL = "select rreportcode, RReportName from LDRReport where "
					+ mConditionField + " = " + mCodeCondition
					+ " and rreportclass = '1' order by rreportcode";
		}

		// 团险契调引用RReportCode
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"rreportcode2") == 0) {
			mSQL = "select rreportcode, RReportName from LDRReport where "
					+ mConditionField + " = " + mCodeCondition
					+ " and rreportclass = '2' order by rreportcode";
		}

		// 代理人组别引用AgentGroup
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"agentgroup") == 0) {
			// mSQL = "select AgentGroup, Name from LABranchGroup where "
			// + mConditionField + " = " + mCodeCondition
			// + " and BranchLevel = '01' and ManageCom like '"
			// + mGlobalInput.ManageCom + "%' order by AgentGroup";

			mSBql.append("select AgentGroup, Name from LABranchGroup where ");
			mSBql.append(mConditionField);
			mSBql.append(" = ");
			mSBql.append(mCodeCondition);
			mSBql.append(" and BranchLevel = '01' and ManageCom like '");
			mSBql.append(mGlobalInput.ManageCom);
			mSBql.append("%' order by AgentGroup");

			mSQL = mSBql.toString();
		}

		// 团体代理人组别引用AgentGroup
		// by niuzj,2006-07-18
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"grpagentgroup") == 0) {

			mSBql.append("select AgentGroup, Name from LABranchGroup where ");
			mSBql.append(mConditionField);
			mSBql.append(" = ");
			mSBql.append(mCodeCondition);
			mSBql
					.append(" and BranchLevel = '11' and branchtype='2' and ManageCom like '");
			mSBql.append(mGlobalInput.ManageCom);
			mSBql.append("%' order by AgentGroup");

			mSQL = mSBql.toString();
		}

		// 退保类型引用EdorCode
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"edorcode") == 0) {
			// LDEdorUserSchema tLDEdorUserSchema = new LDEdorUserSchema();
			// try
			// {
			// LDEdorUserDB tLDEdorUserDB = new LDEdorUserDB();
			// tLDEdorUserDB.setUserCode(this.mGlobalInput.Operator);
			// if (!tLDEdorUserDB.getInfo())
			// {
			// logger.debug("select error");
			// }
			//
			// tLDEdorUserSchema = tLDEdorUserDB.getSchema();
			// }
			// catch (Exception ex)
			// {
			// ex.printStackTrace();
			// }
			// if (tLDEdorUserSchema.getEdorPopedom() == null)
			// {
			// return false;
			// }

			mSQL = "select distinct b.EdorCode, b.EdorName,b.edorlevel,b.valdateflag from LMRiskEdoritem  a,LMEdorItem b where b.edorcode=a.edorcode and "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " order by EdorCode";
			// while (mSQL.indexOf("@") != -1)
			// {
			// int indexAsterisk = mSQL.indexOf("@");
			// String tPreStr = mSQL.substring(0, indexAsterisk);
			// String tPostStr = mSQL.substring(indexAsterisk + 1);
			// mSQL = tPreStr + tLDEdorUserSchema.getEdorPopedom() + tPostStr;
			// }
		}

		// 录单外包差错记录中差错内容
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"errcontent") == 0) {
			mSQL = "select Code, CodeName, CodeAlias, ComCode, OtherSign from ldcode1 where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " and codetype = 'errcontent' order by Code";
			executeType = 1;
		}

		// 代理机构引用AgentCom
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"agentcom") == 0) {
			// mSQL =
			// "select AgentCom, Name, UpAgentCom, AreaType, ChannelType from
			// LACom where "
			// + mConditionField + " = " + mCodeCondition
			// + " and ManageCom like '" + mGlobalInput.ManageCom + "%' order by
			// AgentCom";

			mSBql
					.append("select AgentCom, Name, UpAgentCom, AreaType, ChannelType from LACom where ");
			mSBql.append(mConditionField);
			mSBql.append(" = ");
			mSBql.append(mCodeCondition);
			mSBql.append(" and ManageCom like '");
			mSBql.append(mGlobalInput.ManageCom);
			mSBql.append("%' ");
			
			if (mCodeValue != null && !mCodeValue.equals("")) {
				mSBql.append(" and AgentCom='" + mCodeValue + "' ");
			}
			mSBql.append(" order by AgentCom");

			mSQL = mSBql.toString();

			executeType = 1;
		}

		// 团险中介机构引用GrpAgentCom
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"grpagentcom") == 0) {
			mSBql
					.append("select AgentCom, Name, UpAgentCom, AreaType, ChannelType from LACom where ");
			mSBql.append(mCodeCondition);
			mSBql.append(" and ManageCom like '");
			mSBql.append(mGlobalInput.ManageCom);
			mSBql.append("%' order by AgentCom");

			mSQL = mSBql.toString();

			executeType = 1;
		}

		// 银行分行渠道BankCharge
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"BankCharge") == 0) {
			mSQL = "select Code, CodeName, CodeAlias, ComCode, OtherSign from ldcode where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ "and codetype = 'bankcharge' order by Code";
		}

		// 险种编码引用RiskCode
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase()
				.toLowerCase().compareTo("riskcode") == 0) {
			 mSQL = "select RiskCode,RiskName from LMRiskapp where "
			 + mConditionField + " = " + mCodeCondition
			 + " and riskver >= '2016' order by RiskCode";
			// 初始化所有主险险种编码
			//mSQL = "select riskcode,riskname from lmriskapp where subriskflag='M' and riskprop in ('I','C','D','A','Y') and poltype='P' order by riskcode";
		}
		// 银代险种编码引用BankRiskCode
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase()
				.toLowerCase().compareTo("bankriskcode") == 0) {
			mSQL = "select riskcode,riskname from lmriskapp where subriskflag='M' and riskprop in ('Y','B','C','D') order by riskcode";
		}
		// 直销险种编码引用directriskcode
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase()
				.toLowerCase().compareTo("directriskcode") == 0) {
			mSQL = "select riskcode,riskname from lmriskapp where subriskflag='M' and riskprop in ('T','E','F','H') order by riskcode";
		}
		// 财务险种查询11111
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase()
				.toLowerCase().compareTo("riskcodefin") == 0) {
			// mSQL = "select riskcode,riskname from lmriskapp where 1=1 and
			// (PolType<>'C' or RiskProp<>'G') order by riskcode";
			mSQL = "select riskcode aa,riskname from lmriskapp union select contplancode aa,contplanname from ldplan where state = '9' order by aa";
			executeType = 1;
		}
		
		//riskcodechnl
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase()
				.toLowerCase().compareTo("riskcodechnl") == 0) {
			mSQL = "select riskcode,riskname from lmriskapp where "
					+ mConditionField + " = " + mCodeCondition
					+ " order by riskcode";
		}
		//询价险种信息
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
		"askpricerisk") == 0) {
	logger.debug("询价询价单险种！");
	mSBql
			.append("select a.RiskCode,(select riskname from lmrisk where riskcode=a.RiskCode ),(case when a.RewardRatio is not null then a.RewardRatio else 0 end),"
					+ "(case when a.ChargesRatio is not null then a.ChargesRatio else 0 end) from AskPriceRadio a where  ");
	mSBql.append(mConditionField);
	mSBql.append(" = ");
	mSBql.append(mCodeCondition);
	mSQL = mSBql.toString();

}
		// 险种分渠道查询riskcodechnl1
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase()
				.toLowerCase().compareTo("riskcodechnl1") == 0) {
			mSQL = "select riskcode,riskname from lmriskapp where mngcom='I' order by riskcode";
		} // 险种分渠道查询riskcodechnl2
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase()
				.toLowerCase().compareTo("riskcodechnl2") == 0) {
			mSQL = "select riskcode,riskname from lmriskapp where mngcom='G' order by riskcode";
		} // 险种分渠道查询riskcodechnl3
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase()
				.toLowerCase().compareTo("riskcodechnl3") == 0) {
			mSQL = "select riskcode,riskname from lmriskapp where mngcom='B' order by riskcode";
		} // 险种分渠道查询riskcodechnl5
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase()
				.toLowerCase().compareTo("riskcodechnl5") == 0) {
			mSQL = "select riskcode,riskname from lmriskapp where mngcom='T' order by riskcode";
		}

		// 险种编码引用RiskCode1
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase()
				.toLowerCase().compareTo("riskcode1") == 0) {
			mSQL = "select a.RiskCode, a.RiskName,b.SubRiskFlag,b.SubRiskFlag from LMRisk a,LMRiskApp b where a.RiskCode=b.RiskCode order by a.RiskCode";
		}
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase()
				.toLowerCase().compareTo("riskcode2") == 0) {
			mSQL = "select RiskCode, RiskName from LMRiskApp where RiskProp in ('I','A','C','D')"
					+ " order by RiskCode";
		}

		// 险种版本引用RiskVersion
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"riskversion") == 0) {
			mSQL = "select RiskVer from LMRisk where " + mConditionField
					+ " = " + mCodeCondition + " order by RiskVer";
		}

		// 机构编码引用ComCode
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"comcode") == 0) {
			// mSQL =
			// "select ComCode, Name, ShortName, Address, Sign from ldcom where
			// "
			// + mConditionField + " = " + mCodeCondition
			// + " and comcode like '"
			// + mGlobalInput.ManageCom + "%' order by comcode";
			mSBql
					.append("select ComCode, Name, ShortName, Address, Sign from ldcom where ");
			mSBql.append(mConditionField);
			mSBql.append(" = ");
			mSBql.append(mCodeCondition);
			mSBql.append(" and comcode like '");
			mSBql.append(mGlobalInput.ManageCom);
			mSBql.append("%'");
			if (mCodeValue != null && !mCodeValue.equals("")) {
				mSBql.append(" and comcode='" + mCodeValue + "' ");
			}
			mSBql.append("order by comcode");

			mSQL = mSBql.toString();
			// logger.debug("登陆的机构============" + mGlobalInput.ManageCom);
			// logger.debug(mSQL);
			/** @todo 取消对于下拉框行数的限制 add by HYQ */
			executeType = 1;
		}
		
		// ln 2008-12-16 add
		// 个险核保级别
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"uwpopedom") == 0) {
			mSQL = "select Code, CodeName from ldcode where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " and codetype='uwpopedom' order by ComCode";
		}

		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"comcode4") == 0) {
			// mSQL =
			// "select ComCode, Name, ShortName, Address, Sign from ldcom where
			// "
			// + mConditionField + " = " + mCodeCondition
			// + " and comcode like '"
			// + mGlobalInput.ManageCom + "%' and length(trim(comcode))=4 order
			// by comcode";

			mSBql
					.append("select ComCode, Name, ShortName, Address, Sign from ldcom where ");
			mSBql.append(mConditionField);
			mSBql.append(" = ");
			mSBql.append(mCodeCondition);
			mSBql.append(" and comcode like '");
			mSBql.append(mGlobalInput.ManageCom);
			mSBql.append("%' and char_length(trim(comcode))=4 order by comcode");

			mSQL = mSBql.toString();
		}

		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"comcode8") == 0) {
			mSBql
					.append("select ComCode, Name, ShortName, Address, Sign from ldcom where ");
			mSBql.append(mConditionField);
			mSBql.append(" = ");
			mSBql.append(mCodeCondition);
			mSBql.append(" and comcode like '");
			mSBql.append(mGlobalInput.ManageCom);
			mSBql.append("%' and char_length(trim(comcode))=8 order by comcode");
			mSQL = mSBql.toString();
		}

		// 机构编码引用ComCodeAll
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"comcodeall") == 0) {
			mSQL = "select ComCode, Name, ShortName, Address, Sign from ldcom where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " order by comcode";
		}

		// 银行险编码引用Riskbank
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"riskbank") == 0) {
			if (mCodeCondition.trim().equals("'1' and branchtype=2")) {
				mCodeCondition = "1";
				mSQL = "select RiskCode, RiskName from LMRiskApp where "
						+ mConditionField
						+ " = "
						+ mCodeCondition
						+ " and RiskProp in ('A','B','G','D') order by RiskCode";
			} else {
				mCodeCondition = "1";
				mSQL = "select RiskCode, RiskName from LMRiskApp where "
						+ mConditionField
						+ " = "
						+ mCodeCondition
						+ " and RiskProp in ('Y','B','C','D') order by RiskCode";
			}
		}

		// 团险编码引用RiskGrp
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"riskgrp") == 0) {
			mSQL = "select RiskCode, RiskName,SubRiskFlag from LMRiskApp where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " and RiskProp in ('G','A','B','D') and poltype in ('P','D') "
					+ " order by RiskCode";
		}

		// 个险编码引用RiskInd
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"riskind") == 0) {
			mSQL = "select RiskCode, RiskName from LMRiskApp where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " and RiskProp in ('I','A','C','D','Y') order by RiskCode";
		}

		// 普通单证编码引用CertifyCode
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"certifycode") == 0) {
			mSQL = "SELECT CertifyCode, CertifyName, CertifyClass FROM LMCertifyDes WHERE "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " and CertifyClass in ('D','B','P') AND State = '0' order by CertifyCode";
			m_bCanBeCached = false;
			executeType = 1;
		}

		// 定额单证编码引用CardCode
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"cardcode") == 0) {
			mSQL = "SELECT CertifyCode, CertifyName FROM LMCertifyDes WHERE "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " and CertifyClass = 'D' AND State = '0' order by CertifyCode";
			m_bCanBeCached = false;
			executeType = 1;
		}

		// 系统单证编码引用SysCertCode
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"syscertcode") == 0) {
			mSQL = "SELECT CertifyCode, CertifyName FROM LMCertifyDes WHERE "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " and CertifyClass = 'S' AND State = '0' order by CertifyCode";
			m_bCanBeCached = false;
			executeType = 1;
		}

		// 告知编码引用ImpartCode
		// tongmeng 2007-09-07 add
		// 采用5.3的告知信息
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"impartcode") == 0) {
			// mSQL =
			// "SELECT ImpartCode, ImpartContent ,CreateDH((select count(*)"
			// + " from ldimpartparam b where b.impartcode = c.impartcode and
			// b.impartver = c.impartver))"
			// + " FROM LDImpart c WHERE c."
			// + mConditionField + " = " + mCodeCondition
			// + " order by ImpartCode";

			mSQL = "SELECT ImpartCode, ImpartContent FROM LDImpart WHERE "
					+ mConditionField + " = " + mCodeCondition
					+ " order by ImpartCode";
		}
		//tongmeng 2009-02-11 add
		//增加问题件编码
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
		"quest") == 0) {
				mSQL = "SELECT code,cont from ldcodemod where codetype='Question' and  "
					+ mConditionField + " = " + mCodeCondition
					+ " order by code";
}

		// //告知编码引用ImpartVer
		// if
		// (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
		// "impartver") == 0)
		// {
		// mSQL = "SELECT Code, CodeName FROM LDCode WHERE "
		// + mConditionField + " = " + mCodeCondition
		// + " order by ImpartCode";
		// }

		// 管理机构编码引用Station，已不再使用
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"station") == 0) {
			logger.debug("mCodeCondition:" + mCodeCondition);
			if (mCodeCondition.toLowerCase().indexOf("comcode") == -1) {
				mCodeCondition = mCodeCondition.toLowerCase().replaceAll(
						"code", "comcode");
			}
			mSQL = "select comcode,name from ldcom where " + mConditionField
					+ " = " + mCodeCondition + " and comcode like '"
					+ mGlobalInput.ManageCom + "%' order by comcode";
			executeType = 1;
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
		"hospital1") == 0) {
	        mSQL = "select a.hospitcode,a.hospitalname from LDHospital a where 1=1 and HosState='1' ";
	       executeType = 1;
        }
		
		// add by yaory for relatedpeople
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"insuredpeople") == 0) {
			logger.debug("被保人关系！");
			mSBql
					.append("select insuredno,name,sequenceno from lcinsured where ");
			mSBql.append(mConditionField);
			mSBql.append(" = ");
			mSBql.append(mCodeCondition);
			mSQL = mSBql.toString();

		}
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"hebaoquanxian") == 0) {
			logger.debug("核保结论代码！");
			mSBql.append("select code,codename from ldcode where ");
			mSBql.append(mConditionField);
			mSBql.append(" = ");
			mSBql.append(mCodeCondition);
			mSQL = mSBql.toString();

		}

		// 录单外包查询外包商编码
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"querybpoid") == 0) {
			mSQL = "select Bpoid,Bponame from BPOServerInfo where "
					+ mConditionField + " = " + mCodeCondition
					+ "  order by Bpoid";
			executeType = 1;
		}

		// 查询险种编码，附加一项“全部险种”
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"selrisk") == 0) {
			mSQL = "select '全部' A,'全部' from dual union all select riskcode A,riskname from lmriskapp  "
					+ "  order by A";
			executeType = 1;
		}
		
		// 工种代码引用OccupationCode
		// tongmeng 2007-09-06 modify 查询职业代码后带出职业类别
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"occupationcode") == 0) {
			mSQL = "select OccupationCode, OccupationName, OccupationType, "
					+ " (select codename from ldcode where codetype='occupationtype' "
					+ " and code=OccupationType) from LDOccupation where "
					+ mConditionField + " = " + mCodeCondition + "";

			if (mCodeValue != null && !mCodeValue.equals("")) {
				mSQL = mSQL + " and OccupationCode='" + mCodeValue + "' ";
			}
			mSQL = mSQL + " and occupationver = '002' order by OccupationCode";

			executeType = 1;
		}
		// tongmeng 2007-09-06 add
		// 投保单告知版别
		// 新版告知版别
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"impartver1") == 0) {
			mSQL = "select Code, CodeName from ldcode where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " and codetype='impartver' and othersign='1' order by Code";
			executeType = 1;
		}
		// tongmeng 2007-09-06 add
		// 投保单告知编码引用
		// 告知编码引用ImpartCode
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"impartcode") == 0) {
			mSQL = "SELECT ImpartCode, ImpartContent,IMPARTPARAMMODLE FROM LDImpart WHERE "
					+ mConditionField + " = " + mCodeCondition
					+ " order by ImpartCode";
		}

		// 交费方式代码引用PayYears
		/*"concat只使用两个参数，改造样例如下：
	  Oracle：select 'a'||'b'||'c'||'d' from dual
		改造为：select concat(concat(concat('a','b'),'c'),'d') from dual"
*/
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"payyears") == 0) {
			/*mSQL = "select trim(PayEndYearFlag)||PayEndYear||'*'||PayIntv,ShowInfo from LMPayMode where "
					+ mConditionField + " = " + mCodeCondition;*/
			mSQL = "select concat(concat(concat(trim(PayEndYearFlag),PayEndYear),'*'),PayIntv),ShowInfo from LMPayMode where "
					+ mConditionField + " = " + mCodeCondition;
			executeType = 1;
		}

		// 交费期限代码引用PayEndYear
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"payendyear") == 0) {
			mSQL = "select ParamsCode, ParamsName from LMRiskParamsDef where Paramstype = 'payendyear' and "
					+ mConditionField + " = " + mCodeCondition;

			executeType = 1;
		}

		// 交费期限代码引用(中介用)GrpPayEndYear
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"grppayendyear") == 0) {
			mSQL = "select ParamsCode, ParamsName from LMRiskParamsDef where Paramstype = 'payendyear' and paramscode<>'1000' and "
					+ mConditionField + " = " + mCodeCondition;

			executeType = 1;
		}

		// 领取年龄代码引用GetYear
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"getyear") == 0) {
			mSQL = "select ParamsCode, ParamsName from LMRiskParamsDef where Paramstype = 'getyear' and "
					+ mConditionField + " = " + mCodeCondition;

			// executeType = 1;
			// logger.debug("mSQL=" + mSQL);
		}

		// 领取间隔代码引用GetIntv
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"getintv") == 0) {
			mSQL = "select ParamsCode, ParamsName from LMRiskParamsDef where Paramstype = 'getintv' and "
					+ mConditionField + " = " + mCodeCondition;

			// executeType = 1;
			// logger.debug("mSQL=" + mSQL);
		}
		// 兼业单证作废原因查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"zycancelreason") == 0) {
			String tConditionField1 = mConditionField.substring(0, 8);
			String tConditionField2 = mConditionField.substring(9);
			String tCodeCondition1 = mCodeCondition.substring(0, 15) + "'";
			String tCodeCondition2 = "'" + mCodeCondition.substring(16);
			mSQL = "select code,codename from ldcode where " + tConditionField1
					+ " = " + tCodeCondition1 + " and " + tConditionField2
					+ " = " + tCodeCondition2;
			logger.debug("mSQL=" + mSQL);
		}
		// 国家信息(中文名称，英文名称，风险类别)查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"country") == 0) {
			mSQL = "select CountryName,CountryEnName,RiskType from Country";
			logger.debug(mSQL);
		}
		// 兼业协议类型查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"protocoltype") == 0) {
			mSQL = "select Code,CodeName from ldcode where codetype='protocoltype'";
			logger.debug(mSQL);
		}

		// 兼业险种查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"protrisk") == 0) {
			mSQL = "select a.RiskCode, a.RiskName,b.ProtocolNo,a.SubRiskFlag from LMRiskApp a,LXComRiskRela b where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " and a.RiskCode = b.RiskCode order by a.RiskCode";
		}
		// 兼业险种查询ProtMainRisk主险
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"protmainrisk") == 0) {
			mSQL = "select a.RiskCode, a.RiskName,b.ProtocolNo from LMRiskApp a,LXComRiskRela b where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " and a.RiskCode = b.RiskCode and a.SubRiskFlag <> 'S' order by a.RiskCode";
		}

		// 团单险种查询GrpRisk
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"grprisk") == 0) {
			mSQL = "select a.RiskCode, a.RiskName,b.GrpPolNo,a.SubRiskFlag from LMRiskApp a,LCGrpPol b where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " and a.RiskCode = b.RiskCode order by a.RiskCode";
		}

		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
		"grpriskq") == 0) {
	mSQL = "select a.RiskCode, a.RiskName, a.SubRiskFlag from LMRiskApp a,askpriceradio b where "
			+ mConditionField
			 
			+ " and a.RiskCode = b.RiskCode order by a.RiskCode";
}
		//censusno
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
		"censusno") == 0) {
			 mSQL = "select b.riskrating,b.jobtype from askpricerating b where "
					+ mConditionField
					 
					+ "   order by b.riskrating";
       } 
       
     //benefitno
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
		"benefitno") == 0) {
	      mSQL = "select b.contplancode,b.contplanname from Lqbenefit b where "
			+ mConditionField
			 
			+ "   order by b.contplancode";
      }
		
		
		// 团单险种查询GrpMainRisk主险
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"grpmainrisk") == 0) {
			mSQL = "select a.RiskCode, a.RiskName,b.GrpPolNo from LMRiskApp a,LCGrpPol b where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " and a.RiskCode = b.RiskCode and a.SubRiskFlag <> 'S' order by a.RiskCode";
		}

		// 团单险种查询GrpMainRisk主险
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"grpmainrisk1") == 0) {
			mSQL = "select a.RiskCode, a.RiskName from LMRiskApp a where a.SubRiskFlag <> 'S' order by a.RiskCode";
		}

		// 取得分红险种
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"onusrsk") == 0) {
			mSQL = "select RiskCode,RiskName from LMRiskApp where BonusFlag='Y'";
		}

		// 查询缴费规则
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"payrulecode") == 0) {
			mSQL = "select distinct PayRuleCode,PayRuleName from LCPayRuleFactory"
					+ " where " + mConditionField + " = " + mCodeCondition;
		}

		// 查询归属规则
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"ascriptionrulecode") == 0) {
			mSQL = "select distinct AscriptionRuleCode,AscriptionRuleName from LCAscriptionRuleFactory"
					+ " where " + mConditionField + " = " + mCodeCondition;
		}

		// 团单险种缴费规则查询RiskRuleFactoryType，Type编码默认为000005
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"riskrulefactorytype") == 0) {
			mSQL = "select distinct a.FactoryType,b.FactoryTypeName,concat(trim(a.FactoryType),trim(a.RiskCode)) from LMFactoryMode a,LMFactoryType b where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " and a.FactoryType = b.FactoryType and a.FactoryType = '000005'";
		}

		// 团单险种归属规则查询RiskAscriptionRuleFactoryType，Type编码默认为000006
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"riskascriptionrulefactorytype") == 0) {
			mSQL = "select distinct a.FactoryType,b.FactoryTypeName,concat(trim(a.FactoryType),trim(a.RiskCode)) from LMFactoryMode a,LMFactoryType b where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " and a.FactoryType = b.FactoryType and a.FactoryType = '000006'";
			// logger.debug("in mSQL:" + mSQL);
		}
		// 团单险种归属规则查询riskascriptionrulefactoryno
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"riskascriptionrulefactoryno") == 0) {
			if (mCodeCondition.substring(1, 7).equals("000006")) { //没有加入任何限制条件，
																	// 以后扩展
				mSQL = "select PayPlanCode,PayPlanName from LMDutyPay where "
						+ "payplancode in (select payplancode from lmdutypayrela where "
						+ "dutycode in (select dutycode from lmriskduty where "
						+ "riskcode" + " = '" + mCodeCondition.substring(7, 13)
						+ "' and SpecFlag='N'))" // 过滤掉公共帐户对应的交费项
						// caihy add 归属规则仅对单位交费有效
						// + " and payaimclass='2'";
						+ " and PubFlag='Y'";

			}
			// logger.debug("in mSQL:" + mSQL);

		}
		// 团单险种归属规则查询riskascriptionrulefactory
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"riskascriptionrulefactory") == 0) {
			mSQL = "select concat(FactoryCode,to_char(FactorySubCode)),CalRemark,Params,FactoryName from LMFactoryMode  "
					+ " where FactoryType = '"
					+ mCodeCondition.substring(1, 7)
					+ "' and RiskCode='"
					+ mCodeCondition.substring(7)
					+ ""
					+ " order by FactoryCode,FactorySubCode ";
			// logger.debug("in mSQL:" + mSQL);
		}

		// 团单险种缴费规则查询RiskRuleFactoryNo
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"riskrulefactoryno") == 0) {
			if (mCodeCondition.substring(1, 7).equals("000005")) {
				// 没有加入任何限制条件，以后扩展
				mSQL = "select PayPlanCode,PayPlanName from LMDutyPay where payplancode in (select payplancode from lmdutypayrela where dutycode in (select dutycode from lmriskduty where riskcode = '"
						+ mCodeCondition.substring(7, 13)
						+ "' and SpecFlag='N'))";
			}
		}

		// 团单险种缴费规则查询RiskRuleFactory
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"riskrulefactory") == 0) {
			mSQL = "select concat(FactoryCode,to_char(FactorySubCode)),CalRemark,Params,FactoryName from LMFactoryMode  "
					+ " where FactoryType = '"
					+ mCodeCondition.substring(1, 7)
					+ "' and RiskCode='"
					+ mCodeCondition.substring(7)
					+ " order by FactoryCode,FactorySubCode ";
		}

		// 查询子账户的险种账户编码
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"subriskacccode") == 0) {
			mSQL = "select payplancode||insuaccno RiskAccCode,RiskAccPayName from lmriskaccpay where "
					+ mConditionField + "=" + mCodeCondition;
		}
		// 查询子账户的险种账户编码,仅缴费帐户（承保帐户触发器）
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"subriskacccodepay") == 0) {
			mSQL = "select RiskAccPayName,payplancode||insuaccno RiskAccCode from lmriskaccpay where "
					+ mConditionField
					+ "="
					+ mCodeCondition
					+ " and InsuAccNo='000001'";

		}
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"subriskacccodepayname") == 0) {
			mSQL = "select Code,CodeName RiskAccCode from ldcode where 1=1"
			// + mConditionField + "=" + mCodeCondition

					+ " and codetype='toobjecttype'";
			logger.debug(mConditionField);
			logger.debug(mCodeCondition);
//			logger.debug(mCodeCondition.length());
			if (mConditionField.toLowerCase().equals("payplancode")) {
				if ((mCodeCondition.trim().substring(1, 7).equals("692102"))
						|| (mCodeCondition.trim().substring(1, 7)
								.equals("692103"))
						|| (mCodeCondition.trim().substring(1, 7)
								.equals("692104"))) {
					mSQL = mSQL + " and code in('00','01')";
				}
			}
		}

		// 团单保险计划下险种查询ImpRiskCode
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"impriskcode") == 0) {
			String a = mCodeCondition.substring(0, 2); // 保险计划编码
			String b = mCodeCondition.substring(2); // 合同号

			mSQL = "select a.RiskCode, a.RiskName, a.RiskVer,b.MainRiskCode,b.MainRiskVersion from LMRiskApp a,LCContPlanRisk b where "
					+ mConditionField
					+ " = "
					+ a
					+ "' and GrpContNo = '"
					+ b
					+ " and a.RiskCode = b.RiskCode order by a.RiskCode";
		}

		// 团单保险计划下险种对应要素类别ImpFactoryType
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"impfactorytype") == 0) {
			mSQL = "select distinct a.FactoryType,b.FactoryTypeName,concat(trim(a.FactoryType),trim(a.RiskCode)) from LMFactoryMode a,LMFactoryType b where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " and a.FactoryType = b.FactoryType and a.FactoryType < '000005'";
		}
		// 团单保险计划下险种对应要素目标编码ImHealthFactoryNo
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"imhealthfactoryno") == 0) {
			if (mCodeCondition.substring(1, 7).equals("000000")) { // 基于保单的计算
				mSQL = "select '__','请录入保单号' from dual";
			} else if (mCodeCondition.substring(1, 7).equals("000001")) { // 基于保单的计算
				mSQL = "select DutyCode,DutyName from LMDuty where DutyCode in(select DutyCode from LMRiskDuty where RiskCode='"
						+ mCodeCondition.substring(7) + ") order by DutyCode";
			} else if (mCodeCondition.substring(1, 7).equals("000002")) {
				// 基于给付的计算
				mSQL = "elect gedutycode,getdutyname fom lmdutygetrela where dutycode in (select dutycode from lmriskduty where riskcode ='"
						+ mCodeCondition.substring(7)
						+ ") order by getdutycode";
			} else if (mCodeCondition.substring(1, 7).equals("000003")) {
				// 基于账户的计算
				mSQL = "select insuaccno,insuaccname from LMRiskToAcc where RiskCode='"
						+ mCodeCondition.substring(7) + " order by insuaccno";
			} else if (mCodeCondition.substring(1, 7).equals("000004")) {
				// 基于理赔责任的计算
				mSQL = "select getdutycode,getdutyname from lmdutygetrela where dutycode in ( select dutycode from lmriskduty where riskcode ='"
						+ mCodeCondition.substring(7)
						+ ") order by getdutycode";
			}
		}

		// 团单保险计划下险种对应要素计算编码ImHealthFactory
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"imhealthfactory") == 0) {
			mSQL = "select concat(FactoryCode,to_char(FactorySubCode)),CalRemark,Params,FactoryName from LMFactoryMode where FactoryType = '"
					+ mCodeCondition.substring(1, 7)
					+ "' and RiskCode='"
					+ mCodeCondition.substring(7)
					+ ""
					+ " order by FactoryCode,FactorySubCode ";
		}

		// 代理人编码引用AgentCode
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"agentcode") == 0) {
			ExeSQL tExeSQL = new ExeSQL();
			String agent = tExeSQL
					.getEncodedResult(
							"select Sysvarvalue from ldsysvar where Sysvar = 'LAAgent'",
							1);
			agent = agent.substring(agent.indexOf("^") + 1);

			// mSQL = "select AgentCode, Name, BranchCode from LAAgent where "
			// + mConditionField + " = " + mCodeCondition
			// + " and ManageCom like '" + mGlobalInput.ManageCom +
			// "%' and " +
			// agent + " order by AgentCode";
			mSBql
					.append("select AgentCode, Name, BranchCode from LAAgent where ");
			mSBql.append(mConditionField);
			mSBql.append(" = ");
			mSBql.append(mCodeCondition);
			mSBql.append(" and ManageCom like '");
			mSBql.append(mGlobalInput.ManageCom);
			mSBql.append("%' and ");
			mSBql.append(agent);
			
			if (mCodeValue != null && !mCodeValue.equals("")) {
				mSBql.append( " and agentcode = '"+mCodeValue+"' ");
			}
			mSBql.append(" order by AgentCode");

			mSQL = mSBql.toString();

			executeType = 1;
			// mResultStr = mExeSQL.getEncodedResult(mSQL);
		}

		// 代理人编码引用AgentCode2 --liujw
		// 修改于2005-6-02 by liuyy
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"agentcode2") == 0) {
			mSBql
					.append("select b.AgentCode,(select Name from laagent where agentcode = b.agentcode) name,b.AgentGroup,(select trim(BranchAttr) from laBranchGroup where agentgroup = (select branchcode from laagent where agentcode = b.agentcode)) branchattr,(slect name from lABranchGroup where agentgroup = (select branchcode from laagent where agentcode = b.agentcode)) ComName,b.AgentSeries,b.AgentGrade from latree b where 1=1 and exists (select agentcode from laagent a where a.agentstate in ('01','02') and a.agentcode=b.agentcode)) ");
			mSBql.append(" and ManageCom like '");
			mSBql.append(mGlobalInput.ManageCom);
			mSBql.append("%'");

			// +" And Branchtype = '1') And "
			// mSBql.append("+ mCodeCondition");
			// +mConditionField + " = " + mCodeCondition
			mSBql.append("ORDER BY b.AgentCode");
			mSQL = mSBql.toString();
			executeType = 1;
		}

		// add by ln 2008-11-5 --体检医院
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"pehospital") == 0) {
			mSBql.append("select hospitcode,hospitalname,mngcom from  LDHospital where 1=1 ");
			mSBql.append(" and mngcom like '");
			mSBql.append(mGlobalInput.ManageCom);
			mSBql.append("%' ");

			mSBql.append("and hosstate='0'");
			mSBql.append(" order by mngcom,hospitcode");
			mSQL = mSBql.toString();
//			mSQL = "select hospitcode,hospitalname,mngcom from  LDHospital order by mngcom,hospitcode";
			executeType = 1;
		}
		
		// add by ln 2008-11-6 --体检项目细分
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"pedivcode") == 0) {
			mSQL = "SELECT Code, CodeName FROM LDCode WHERE "
				+ mConditionField + " = " + mCodeCondition
				+ " order by Code";
			//logger.debug(mSQL);
			executeType = 1;
		}	
		// add by ln 2008-11-6 --体检项目
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"healthsubcode") == 0) {
			mSQL = "SELECT SubHealthCode, SubHealthName FROM ldhealthsub order by SubHealthCode";
		}
		
		// 网点专管员编码AgentCode3
		// add by liuyy
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"agentcode3") == 0) {
			mSBql
					.append("select b.AgentCode,(select Name from laagent where agentcode = b.agentcode) name,b.AgentGroup,(select trim(BranchAttr) from laBranchGroup where agentgroup = (select branchcode from laagent where agentcode = b.agentcode)) branchattr,(select name from lABranchGroup where agentgroup = (select branchcode from laagent where agentcode = b.agentcode)) ComName,b.AgentSeries,b.AgentGrade from latree b where   "
							+ mConditionField
							+ " = "
							+ mCodeCondition
							+ " and exists(select agentcode from laagent a where a.agentstate in('01','02') and a.branchtype='3' and a.agentcode=b.agentcode)");
			mSBql.append(" and ManageCom like '");
			mSBql.append(mGlobalInput.ManageCom);
			mSBql.append("%'");
			mSBql.append("ORDER BY b.AgentCode");
			mSQL = mSBql.toString();
			executeType = 1;
		}
		// 网点专管员编码AgentCode4
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"agentcode4") == 0) {
			mSQL = "select b.AgentCode,(select Name from laagent where agentcode = b.agentcode) name,b.AgentGroup,(select trim(BranchAttr) from laBranchGroup where agentgroup = (select branchcode from laagent where agentcode = b.agentcode)) branchattr,(select name from lABranchGroup where agentgroup = (select branchcode from laagent where agentcode = b.agentcode)) ComName,b.AgentSeries,b.AgentGrade from latree b where b.ManageCom like '"
					+ mGlobalInput.ManageCom
					+ "%' and exists(select agentcode from laagent a where a.ManageCom like '"
					+ mGlobalInput.ManageCom
					+ "%' and a.agentstate in ('01','02') and a.branchtype='3' and a.agentcode=b.agentcode) order by b.agentcode";
		}
		// 网点专管员编码AgentCode4简化
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"agentcode41") == 0) {
			mSQL = "select select a.agentcode,a.name from laagent a where a.ManageCom like '"
					+ mGlobalInput.ManageCom
					+ "%' and a.agentstate in ('01','02') and a.branchtype='3' order by a.agentcode";
		}
		// 团险AgentCode5
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"agentcode5") == 0) {
			mSQL = "select b.AgentCode,b.name,(select codename from ldcode where trim(code) = b.branchtype2 and codetype = 'branchtype2'),b.managecom,(select trim(BranchAttr) from laBranchGroup where agentgroup = b.branchcode) branchattr,(select trim(name) from labranchgroup where agentgroup=(select upbranch from labranchgroup where agentgroup=b.branchcode))||(select name from lABranchGroup where agentgroup = b.branchcode) ComName from laagent b where b.ManageCom like '"
					+ mGlobalInput.ManageCom
					+ "%' and b.agentstate in ('01', '02') and b.branchtype = '2' order by b.agentcode";
		}
//		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
//				"agentcode7") == 0) {
//			mSQL = "select b.AgentCode,b.name,(select codename from ldcode where trim(code) = b.branchtype2 and codetype = 'branchtype2'),b.managecom,(select trim(BranchAttr) from laBranchGroup where agentgroup = b.branchcode) branchattr,(select trim(name) from labranchgroup where agentgroup=(select upbranch from labranchgroup where agentgroup=b.branchcode))||(select name from lABranchGroup where agentgroup = b.branchcode) ComName from laagent b where b.ManageCom like '"
//					+ mGlobalInput.ManageCom
//					+ "%' and b.agentstate in ('01', '02') and b.branchtype = '7' order by b.agentcode";
//		}

		 if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("agentcode7") == 0)
	        {
	              ExeSQL tExeSQL = new ExeSQL();
	              String agent = tExeSQL.getEncodedResult("select Sysvarvalue from ldsysvar where Sysvar = 'LAAgent'",
	                          1);
	              agent = agent.substring(agent.indexOf("^") + 1);
             
	              //logger.debug("agent:" + agent);
	              mSQL = " select a.agentcode,name,(select agentgrade from latree where agentcode=a.agentcode),"
	                   + " (select branchattr from labranchgroup where agentgroup=a.agentgroup), "
	                   + " (select name from labranchgroup where agentgroup=a.agentgroup) "
	                   + " from laagent a where branchtype='7' "
	                   + " and ManageCom like '" + mGlobalInput.ManageCom + "%'"
	                   + " and " + agent + " order by AgentCode";

	              executeType = 1;

	              //      mResultStr = mExeSQL.getEncodedResult(mSQL);
	        }
		// 团险AgentCode5中介专管员
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"agentcode51") == 0) {
			mSQL = "select b.AgentCode,b.name,(select codename from ldcode where trim(code) = b.branchtype2 and codetype = 'branchtype2'),b.managecom,(select trim(BranchAttr) from laBranchGroup where agentgroup = b.branchcode) branchattr,(select name from lABranchGroup where agentgroup = b.branchcode) ComName from laagent b where b.ManageCom like '"
					+ mGlobalInput.ManageCom
					+ "%' and b.agentstate in ('01', '02') and b.branchtype = '2' and branchtype2 = '03' order by b.agentcode";
		}
		// 团险AgentCode5直销
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"agentcode52") == 0) {
			mSQL = "select b.AgentCode,b.name,(select codename from ldcode where trim(code) = b.branchtype2 and codetype = 'branchtype2'),b.managecom,(select trim(BranchAttr) from laBranchGroup where agentgroup = b.branchcode) branchattr,(select name from lABranchGroup where agentgroup = b.branchcode) ComName from laagent b where b.ManageCom like '"
					+ mGlobalInput.ManageCom
					+ "%' and b.agentstate in ('01', '02') and b.branchtype = '2' and branchtype2 = '01' order by b.agentcode";
		}

		// 团险交叉销售专员代码AgentCode6
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"agentcode6") == 0) {
			mSBql
					.append("select b.AgentCode,(select Name from laagent where agentcode = b.agentcode) name,b.AgentGroup,(select trim(BranchAttr) from laBranchGroup where agentgroup = (select branchcode from laagent where agentcode = b.agentcode)) branchattr,(select name from lABranchGroup where agentgroup = (select branchcode from laagent where agentcode = b.agentcode)) ComName,b.AgentSeries,b.managecom from latree b where   "
							+ mConditionField
							+ " = "
							+ mCodeCondition
							+ " and exists(select agentcode from laagent a where a.agentstate in('01','02') and a.branchtype='2' and a.branchtype2='02' and a.agentcode=b.agentcode)");
			mSBql.append(" and ManageCom like '");
			mSBql.append(mGlobalInput.ManageCom);
			mSBql.append("%'");
			mSBql.append("ORDER BY b.AgentCode");
			mSQL = mSBql.toString();
			executeType = 1;
		}
		// 团险交叉销售专员代码AgentCode6简化
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"agentcode61") == 0) {
			mSBql
					.append("select b.agentcode,b.name from laagent b where b.agentstate in('01','02') and b.branchtype='2' and b.branchtype2='02'");
			mSBql.append(" and b.ManageCom like '");
			mSBql.append(mGlobalInput.ManageCom);
			mSBql.append("%'");
			mSBql.append("ORDER BY b.AgentCode");
			mSQL = mSBql.toString();
			executeType = 1;
		}

		// 员工制待遇级别查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"employeegrade") == 0) {
			mSQL = "select gradecode,gradename from laagentgrade where GradeProperty6 = '1' order by gradecode";
		}
		// 员工制待遇级别查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"employeegrade2") == 0) {
			mSQL = "select lawelfareradix.agentgrade,ldcode.codename,100 from lawelfareradix ,ldcode,laagentgrade where  ldcode.codetype = 'employeeaclass' and ldcode.code = lawelfareradix.aclass and lawelfareradix.branchtype = '1' and laagentgrade.gradecode = lawelfareradix.agentgrade and laagentgrade.gradeproperty6 = '1' and lawelfareradix.aclass = "
					+ mCodeCondition + " order by lawelfareradix.agentgrade";
		}
		// 个单合同无扫描件录入账号查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"accnum") == 0) {
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				mSQL = "select trim(BankAccNo),trim(AccName) from LCAccount where "
						+ mConditionField + " = " + mCodeCondition;
			}
		}
		// 用户地址代码条件查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"getaddressno") == 0) {
			mSQL = "select AddressNo,PostalAddress from LCAddress where "
					+ mConditionField + " = " + mCodeCondition;
		}
		// 团体用户地址代码条件查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"getgrpaddressno") == 0) {
			mSQL = "select AddressNo,GrpAddress from LCGrpAddress where "
					+ mConditionField + " = " + mCodeCondition;
		}
		// 团单险种查询交费间隔payintv
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"riskpayintv") == 0) {
			// mSQL =
			// "select a.PayIntv, b.CodeName from LMRiskPayIntv a,LDCode b where
			// "
			// + mConditionField + " = " + mCodeCondition
			// +
			// " and a.ChooseFlag = '1' and b.CodeType = 'payintv' and a.PayIntv
			// = b.Code order by a.PayIntv";
			// by niuzj,20060720, LMRiskPayIntv表已废弃不用

			if (mCodeCondition.equals("") || mCodeCondition == null) {
				mCodeCondition = "0";
			}
			mSQL = " select trim(b.code) as spayintv,b.codename as spayintvname "
					+ " from lmdutypay a, ldcode b "
					+ " where a.payintv=b.code and b.codetype='payintv' "
					+ " and a.payplancode in "
					+ " (select payplancode from lmdutypayrela where dutycode in "
					+ " (select dutycode from lmriskduty where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " ) ) "
					+ " union "
					+ " select trim(paramscode) as spayintv ,(select codename from ldcode where codetype = 'payintv' and paramscode = trim(code)) as spayintvname "
					+ " from lmriskparamsdef "
					+ " where paramstype='payintv' and "
					+ mConditionField
					+ " = " + mCodeCondition + " order by spayintv ";
		}
		// 查询险种代码
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"dutycode") == 0) {
			mSQL = "select DutyCode,DutyName from LMDuty where  dutycode in (select dutycode from lmriskduty where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ ") order by DutyCode";
		}
		// 产品定义平台查询责任编码
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"pd_dutycode") == 0) {
			mSQL = "select DutyCode,DutyName from PD_LMDuty where  dutycode in (select dutycode from pd_lmriskduty where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ ") order by DutyCode";
		}

		// 查询给付类型 Nicholas modify for PU,2005/8/4
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"getdutykind") == 0) {
			// mSQL = "select GetDutyKind,GetDutyName from LMDutyGetAlive where
			// getdutycode in (select getdutycode from lmdutygetrela where
			// dutycode in (select dutycode from lmriskduty where "
			// + mConditionField + " = " + mCodeCondition +
			// " )) order by getdutykind";
			mSQL = "select ParamsCode,ParamsName from LMRiskParamsDef where ParamsType = 'getdutykind' and "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " order by char_length(ParamsCode),ParamsCode";
		}

		// 查询保险期间 Nicholas modify for PU,2005/8/4
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"insuyear") == 0) {
			// mSQL = "select GetDutyKind,GetDutyName from LMDutyGetAlive where
			// getdutycode in (select getdutycode from lmdutygetrela where
			// dutycode in (select dutycode from lmriskduty where "
			// + mConditionField + " = " + mCodeCondition +
			// " )) order by getdutykind";
			mSQL = "select ParamsCode,ParamsName from LMRiskParamsDef where ParamsType = 'insuyear' and "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " order by char_length(ParamsCode),ParamsCode";
		}

		// xjh Add,2005/02/18
		// 机构级别 branchlevel
//		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
//				"branchlevel") == 0) {
//			mSQL = "select BranchLevelCode,BranchLevelName from LABranchLevel where "
//					+ mConditionField
//					+ " = "
//					+ mCodeCondition
//					+ " order by BranchLevelID";
//		}
//
//		// xjh Modify 2005/3/22
//		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
//				"agentgrade") == 0) {
//			mSQL = "select GradeCode,GradeName from LAAgentGrade where "
//					+ mConditionField + " = " + mCodeCondition
//					// + " and trim(gradecode) > '00'"
//					+ " order by GradeID";
//			// " and substr(rtrim(gradecode),length(rtrim(gradecode))-1) >'00'
//			// order by GradeID";
//		}

		// zxs 2006-06-16
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"agentgradebranchtype1") == 0) {
			// mSQL = "select GradeCode,GradeName from LAAgentGrade where "
			// + mConditionField + " = " + mCodeCondition
			// //+ " and trim(gradecode) > '00'"
			// +
			// " and substr(rtrim(gradecode),length(rtrim(gradecode))-1) >'00'
			// order by GradeID";
			mSQL = "select gradecode,gradename from laagentgrade where "
					+ mConditionField + " = "
					+ mCodeCondition
					// + " and trim(gradecode) > '00'"
					+ "and branchtype = '1' union all select '00' gradecode, '清退' gradename from dual order by gradecode ";
		}

		// 工单管理
		// 小组机构信息编码
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"acceptcom") == 0) {
			mSQL = "select GroupNo, GroupName from LGGroup order by GroupNo";
		}

		// 业务分类编号
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"tasktoptypeno") == 0) { // 顶级分类
			mSQL = "select WorkTypeNo, WorkTypeName from LGWorkType where SuperTypeNo = '00' order by WorkTypeNo ";
		}

		// 业务分类编号
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"tasktypeno") == 0) { // 子分类
			mSQL = "select WorkTypeNo, WorkTypeName from LGWorkType where SuperTypeNo != '00' and "
					+ mConditionField
					+ "="
					+ mCodeCondition
					+ " order by WorkTypeNo ";
		}

		// 人员编码
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"taskmemberno") == 0) {
			mSQL = "select UserCode, UserName from LDUser order by UserCode";
		}

		// xjh Add,2005/02/24
		// 特殊险种 SpecRisk
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"specrisk") == 0) {
			mSQL = "select riskcode,riskname from lmriskapp ";
		}
		// 团单客户服务需求
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"serverinfotype") == 0) {
			mSQL = "select ServKind,ServKindRemark from LDServKindInfo order by servkind";
		}
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"serverinfocode") == 0) {
			mSQL = "select ServDetail,concat(concat(ServDetailRemark,trim(servkind)),'-'),trim(servdetail) from LDServDetailInfo where "
					+ mConditionField
					+ "="
					+ mCodeCondition
					+ "order by ServDetail";
		}
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"serverinfochoosecode") == 0) {
			int c = mCodeCondition.indexOf("-");
			String a = mCodeCondition.substring(0, c); // 保险计划编码
			String b = mCodeCondition.substring(c + 1); // 合同号
			mSQL = "select ServChoose,ServChooseRemark from LDServChooseInfo where "
					+ mConditionField
					+ "= "
					+ a
					+ "' and  servdetail= '"
					+ b
					+ " order by ServChoose";

		}

		// 银代总行HeadOffice --没有区分银行和中介代理
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"headoffice") == 0) {
			mSQL = "select AgentCom,Name from LACom where " + mConditionField
					+ "= " + mCodeCondition
					+ " and BankType='00' order by AgentCom";
		}
		// 银代总行BankHeadOffice added by zhanggl 2006-04-17
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"bankheadoffice") == 0) {
			mSQL = "select AgentCom, Name, UpAgentCom, AreaType, ChannelType from LACom where "
					+ mConditionField
					+ "= "
					+ mCodeCondition
					+ " and BankType='00' and branchtype='3' order by AgentCom";
		}

		// hanlin
		// 查询地址省、市、区代码
		////////////////////////////////////////////////////////////////////////
		// //////
		// 查询省份代码
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"province") == 0) {
			mSQL = "select placecode,placename from LDAddress where placetype='01' order by placecode";

		}
		// 查询城市代码
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"city") == 0) {
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				mSQL = "select placecode,placename from LDAddress where placetype='02' and "
						+ mConditionField
						+ "="
						+ mCodeCondition
						+ "order by placecode";
			} else {
				mSQL = "seletplacecod,placenamefrom LDAddess where placetype='2' order by placecode";
			}
		}
		// 查询区县代码
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"district") == 0) {
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				mSQL = "select placecode,placename from LDAddress where placetype='03'  and "
						+ mConditionField
						+ "="
						+ mCodeCondition
						+ "order by placecode";
			} else {
				mSQL = "select placecode,placename from LDAddress where placetype='03' order by placecode";
			}

		}

		// 保单对应的客户查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"customer") == 0) {
			mSQL = "select AppntNo,AppntName from lcappnt where "
					+ mConditionField + "=" + mCodeCondition
					+ "union select InsuredNo,Name from lcinsured where "
					+ mConditionField + "=" + mCodeCondition;

		}

		// 查询银行代理网点代码
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"bankcode") == 0) {
			mSBql
					.append("select BankCode, Name, AgentCom, UpAgentCom, AreaType, ChannelType  from LACom a where ");
			mSBql.append(mConditionField);
			mSBql.append(" = ");
			mSBql.append(mCodeCondition);
			mSBql
					.append("  and a.State='N' and BankType in('05','03','04') and SellFlag='Y' ");
			mSBql
					.append("  order by a.bankcode,a.managecom,substr(a.agentcom||substr('000000000000',1,12),1,12)");
			mSQL = mSBql.toString();

			executeType = 1; // add by oys 解决网点无法全部查出的问题
		}
	      
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("staticthreegroup") == 0)
	    {
	            mSQL = "select comcode,shortname from ldcom where " +
	                   mConditionField + " = " + mCodeCondition +
	                   " and comcode like '" + mGlobalInput.ManageCom + "%' and comcode <> '8699' " +
	                   " union select branchattr,name from labranchgroup where ManageCom like '" +
	                   mGlobalInput.ManageCom + "%'" +
	                   " and (branchlevel='03' or branchlevel='04') and branchtype='1' and char_length(trim(branchattr))<=("+mGlobalInput.ManageCom.length()+"+6) "
	                   + "and (state <> '1' or state is null) ";
	            executeType = 1;
	     }
		
		// 财务交费录入交费年度
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"standyear") == 0) {
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				mSQL = "select year,'' from LARateStandPrem where "
						+ mConditionField + "=" + mCodeCondition
						+ "order by year";
			}
		}

		// 查询银行专管员
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"bankagentcode") == 0) {
			mSQL = "select a.AgentCode , b.Name from LAComToAgent a ,LAAgent b where b.AgentCode = a.AgentCode and a.RelaType ='1' and "
					+ mConditionField + "=" + mCodeCondition;

		}
		// 查询银行代扣的银行
		// if
		// (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
		// "bank") == 0)
		// {
		// mSQL = "select a.bankcode , a.bankname from ldbank a where 1=1 and "+
		// mConditionField + "=" + mCodeCondition +
		// " order by bankcode" ;
		// 11
		// }
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"bank") == 0) {

			mSBql
					.append("select distinct bankcode,bankname from ldbank a where ");
			mSBql.append(mConditionField);
			mSBql.append(" = ");
			mSBql.append(mCodeCondition);
			// tongmeng 2007-09-10 modify
			// mSBql.append(" and trim(comcode) like '");
			// if (mGlobalInput.ManageCom.length() >= 6)
			// {
			// mSBql.append(mGlobalInput.ManageCom.substring(0, 6));
			// }
			// else
			// {
			// mSBql.append(mGlobalInput.ManageCom);
			// }
			// mSBql.append("%' order by bankcode");
			mSBql.append(" order by bankcode");
			mSQL = mSBql.toString();
			executeType=1;
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("finabank") == 0)
        {
        	mSQL = "select Code, CodeName, CodeAlias, ComCode, OtherSign from ldcode where " +
            mConditionField + " = " + mCodeCondition +
            " and codetype = '" +
            "bank" +
            "'" + " order by Code";
        	executeType = 1;

        }
		// 查询收付费银行
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"finbank") == 0) {
			mSQL = " select agentcom,name from lacom where banktype='00' order by agentcom ";

		}

		// 查询影像资料类型。

		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"imagetype") == 0) {
			mSQL = "select subtype,subtypename from es_doc_def where busstype like 'TB%' and validflag in ('0','2') order by subtype";

		}
		// 结案单证打印 ------从“单证打印参数表（LLParaPrint）”中
		// 查询出“打印阶段（prtphase='50'）”的单证
		// and 在打印管理表《loprtmanager》中存在的单证
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"llparaprtcode") == 0) {
			mSQL = "select t.prtcode,t.prtname from llparaprint t where t.prtphase='50'"
					+ " and t.prtcode in ( select code from loprtmanager  where "
					+ mConditionField
					+ " = "
					+ mCodeCondition.trim()
					+ ")"
					+ " order by t.prtcode";

		}

		// 险种核保结论选项
		/*"Oracle：select decode(count(prtno),0,'0','1') from laagentascription 
		改造为：select (case count(prtno) when 0 then '0' else '1' end)from laagentascription "
*/
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"uwstate") == 0) {
			mSQL = "select trim(Code), trim(CodeName), trim(CodeAlias), trim(ComCode), trim(OtherSign)"
					+ " from ldcode where codetype = 'uwstate'"
					+ "  order by (case trim(code) when '9' then 1 when '3' then 2 when '4' then 3 when" +
					" 'd' then 4 when '1' then 5 when '2' then 6 when 'a' then 7 end)";
		}

		// 保单核保结论选项
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"contuwstate") == 0) {
			mSQL = "select trim(Code), trim(CodeName), trim(CodeAlias), trim(ComCode), trim(OtherSign)"
					+ " from ldcode where codetype = 'contuwstate'"
					+ " order by (case trim(code) when '9' then 1 when '4' then 2 when '1' then 3 when '2' then 4 end)";

		}

		// 核保级别
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"lduwuser") == 0) {
			mSQL = "select uwpopedom,uwtype from lduwuser ";
		}

		// 理赔综合查询中的“理赔打印单证代码查询”
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"llallprtcode") == 0) {
			mSQL = "select t.prtcode,t.prtname from llparaprint t where t.prtmode='0'"
					+ " union "
					+ " select t.prtcode,t.prtname from llparaprint t where t.prtcode in ( select code from loprtmanager  where "
					+ mConditionField + " = " + mCodeCondition.trim() + ")";

		}

		// 收付费银行查询 ///
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"comtobank") == 0) {

			mSBql
					.append("select distinct bankcode,BankName from LDComToBank where ");
			mSBql.append(mConditionField);
			mSBql.append(" = ");
			mSBql.append(mCodeCondition);
			mSBql.append(" and comcode like '");
			if (mGlobalInput.ManageCom.length() >= 6) {
				mSBql.append(mGlobalInput.ManageCom.substring(0, 6));
			} else {
				mSBql.append(mGlobalInput.ManageCom);
			}
			mSBql.append("%' and acctype='1' order by bankcode");
			mSQL = mSBql.toString();
		}

		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"banktocom") == 0) {

			mSBql
					.append("select distinct bankcode,BankName from LDComToBank where ");
			mSBql.append(mConditionField);
			mSBql.append(" = ");
			mSBql.append(mCodeCondition);
			mSBql.append(" and comcode like '");
			if (mGlobalInput.ManageCom.length() >= 6) {
				mSBql.append(mGlobalInput.ManageCom.substring(0, 6));
			} else {
				mSBql.append(mGlobalInput.ManageCom);
			}
			mSBql.append("%' and acctype='2' order by bankcode");
			mSQL = mSBql.toString();
		}

		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"accno") == 0) {
			mSBql.append("select AccNo,'' from LDComToBank where ");
			mSBql.append(mConditionField);
			mSBql.append(" = ");
			mSBql.append(mCodeCondition);
			mSBql.append(" and comcode like '");
			if (mGlobalInput.ManageCom.length() >= 6) {
				mSBql.append(mGlobalInput.ManageCom.substring(0, 6));
			} else {
				mSBql.append(mGlobalInput.ManageCom);
			}
			mSBql.append("%' and acctype = '1' ");
			mSBql.append(" order by comcode");
			mSQL = mSBql.toString();
		}

		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"accno2") == 0) {
			mSBql.append("select AccNo,'' from LDComToBank where ");
			mSBql.append(mConditionField);
			mSBql.append(" = ");
			mSBql.append(mCodeCondition);
			mSBql.append(" and comcode like '");
			if (mGlobalInput.ManageCom.length() >= 6) {
				mSBql.append(mGlobalInput.ManageCom.substring(0, 6));
			} else {
				mSBql.append(mGlobalInput.ManageCom);
			}
			mSBql.append("%' and acctype = '2' ");
			mSBql.append(" order by comcode");
			mSQL = mSBql.toString();
		}
		/**
		 * 
		 */

		// 理赔人工核保险种核保结论 add by wanzh 2005/12/19
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"lluwstate") == 0) {
			logger.debug("mConditionField:" + mConditionField);
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				String tSql = "select subriskflag from lmriskapp where riskcode = "
						+ mCodeCondition;
				ExeSQL tExeSQL = new ExeSQL();
				String tRiskFlag = tExeSQL.getOneValue(tSql);
				logger.debug("tRiskFlag:" + tRiskFlag);
				if (tRiskFlag != null && tRiskFlag.equals("S")) {
					logger.debug("tRiskFlagjinru:" + tRiskFlag);
					mSQL = " select trim(Code), trim(CodeName) from ldcode where 1 = 1"
							+ " and codetype='lluwstate' and code in ('1','2','9','b')"
							+ " order by othersign";

				} else {
					mSQL = " select trim(Code), trim(CodeName) from ldcode where 1 = 1"
							+ " and codetype='lluwstate' and code in ('1','2','3','4','9')"
							+ " order by othersign";
				}
			}
		}

		// 理赔人工核保通知书发出 add by wanzh 2005/12/19
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"lluwnoticesend") == 0) {
			mSQL = "select trim(code),trim(codename) from ldcode  where codetype = 'lluwnotice' and code "
					+ " in('LP00','LP06','LP81','LP82','LP83','LP86','LP89','LP90') order by code ";
		}
		// 理赔人工核保通知书打印 add by wanzh 2005/12/19
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"lluwnoticeprint") == 0) {
			mSQL = "select code,codename from ldcode  where codetype = 'lluwnotice' and code "
					+ " in('LP00','LP06','LP81','LP82','LP83','LP86','LP88','LP89','LP90') order by code ";
		}

		////////////////////////////////////////////////////////////////////////
		// ///////

		// 承保单整单删除原因 add by chenrong 2006/07/11
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"proposaldelreason") == 0) {
			mSQL = "select code,codename from ldcode  where codetype = 'proposaldelreason'"
					+ " order by length(code),code";
		}

		// 扫描件删除原因 add by chenrong 2006/07/20
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"scandelreason") == 0) {
			mSQL = "select code,codename from ldcode  where codetype = 'scandelreason'"
					+ " order by length(code),code";
		}
		// 并发控制组查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"lockgroup") == 0) {
			mSQL = "select LockGroup,LockGroupName,Remark from LockGroup where "
					+ mConditionField
					+ " = "
					+ mCodeCondition
					+ " order by LockGroup";
			executeType = 1;
		}
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"lockmodule") == 0) {
			mSQL = "select lockmodule,modulename,remark from LockBase where "
					+ mConditionField + " = " + mCodeCondition
					+ " order by lockmodule ";
			executeType = 1;
		}
		
		/**
		 * 理赔系列
		 * zhangzheng 
		 */
		
		//团险根据合同号查询险种代码
        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
        "llgetriskcode") == 0) {
        	mSQL = " select distinct LCGrpPol.Riskcode ,lmrisk.riskname from LCGrpPol ,lmrisk where"
            + " GrpContno = " + mCodeCondition.trim() +
            " and LCGrpPol.Riskcode = lmrisk.Riskcode ";
        }
		   /**
         * 规则引擎相关查询
         */
        
        //查询规则级别 zhangyf add
        if(StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("TempalteLevel") == 0)
        {
        	 //mSQL = "select code,codename from ldcode where codetype='coursetype' and codealias="+ mCodeCondition;
        	mSQL = "select TempalteLevel,TempalteLevelname from ldcode where 1=1 and "
                + mConditionField + " = " + mCodeCondition;
        }
        
        if(StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("Business") == 0)
        {
        	 //mSQL = "select code,codename from ldcode where codetype='coursetype' and codealias="+ mCodeCondition;
        	mSQL = "select Business,Businessname from ldcode where 1=1 and "
                + mConditionField + " = " + mCodeCondition;

        }
        
    	// 理赔责任控制保障计划编码查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"lldutyctrlplan") == 0) {
			mSQL = "select ContPlanCode,ContPlanName from LCContPlan  "
					+ "  where GrpContNo="+ mCodeCondition
					+ "  and ContPlanCode <> '00'"
					+ " order by ContPlanCode ";
		}
		
		// 理赔责任控制保险责任编码查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"griskduty") == 0) {
			mSQL = "select a.DutyCode, (select dutyname from lmduty where dutycode=a.dutycode) from LMRiskDuty a  "
					+ " where a.riskcode="+ mCodeCondition
					+ " order by a.DutyCode ";
		}
		
		//tongmeng 2011-07-22 add
		//产品定义平台投保规则 定义界面控制
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
		"pd_lc_checkfield") == 0) {
			logger.debug("%%%%%%%%%%%%%%%%%%%%%%%%:"+mCodeCondition+":"+mConditionField);
			if(mCodeCondition.toUpperCase().equals("1")||mCodeCondition.toUpperCase().equals("TB"))
			{
				mSQL = "select code,codename from ldcode where codetype='pd_lc_checkfield' ";
			}
			else
			{
//				mSQL = "select '"+mConditionField+"'||'Insert',(select max(edorname) from lmedoritem where edorcode='"+mConditionField+"') from dual  "
//				     + " union "
//				     + " select '"+mConditionField+"'||'Detail',(select max(edorname) from lmedoritem where edorcode='"+mConditionField+"') from dual ";
				
				mSQL = " select distinct concat(upper(edorcode),'Insert'),edorname from lmedoritem where 1=1 " ;
				if(mConditionField!=null&&!mConditionField.equals(""))
				{
					mSQL =  mSQL + " and upper(edorcode)= '"+ mConditionField.toUpperCase()+"'";
				}
				mSQL =  mSQL + " union "
				     + " select distinct concat(upper(edorcode),'Detail'),edorname from lmedoritem where 1=1 " ;
				if(mConditionField!=null&&!mConditionField.equals(""))
				{
					mSQL =  mSQL + " and upper(edorcode)= '"+ mConditionField.toUpperCase()+"'";
				}
			}
			
		}
		//核保关联保单类型
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
		"pd_relapoltype") == 0) {
			logger.debug("%%%%%%%%%%%%%%%%%%%%%%%%:"+mCodeCondition+":"+mConditionField);
			mSQL = "select code,codename from ldcode where codetype='pd_relapoltype' ";
			
			if(mCodeCondition!=null&&!mCodeCondition.equals("1"))
			{
				mSQL  = mSQL + " and upper(othersign)="+mCodeCondition.toUpperCase()+"";
			}	
		}
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
		"pd_uwtype") == 0) {
			logger.debug("%%%%%%%%%%%%%%%%%%%%%%%%:"+mCodeCondition+":"+mConditionField);
			if(mCodeCondition.toUpperCase().equals("1")||mCodeCondition.toUpperCase().equals("'TB'"))
			{
				mSQL = "select code,codename from ldcode where codetype='pd_uwtype' order by  code/1 ";
			}
			else
			{
				mSQL = " select distinct upper(edorcode),edorname from lmedoritem where 1=1 " ;
				if(mConditionField!=null&&!mConditionField.equals("1"))
				{
					mSQL =  mSQL + " and upper(edorcode)= '"+ mConditionField.toUpperCase()+"'";
				}
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
		"pd_payplancode") == 0) {
			logger.debug("%%%%%%%%%%%%%%%%%%%%%%%%:"+mCodeCondition+":"+mConditionField);
			
				mSQL = "select payplancode,payplanname from pd_lmdutypay where dutycode in "
					 + " ( "
					 + " select dutycode from pd_lmriskduty where riskcode='"+mConditionField+"' "
					 + " )";
		}
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
		"pd_riskinsuacc") == 0) {
			mSQL = "select insuaccno,insuaccname from pd_lmrisktoacc where riskcode='"+mConditionField+"'";
			
		}
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
		"pd_getdutycode") == 0) {
			logger.debug("%%%%%%%%%%%%%%%%%%%%%%%%:"+mCodeCondition+":"+mConditionField);
			
				mSQL = "select getdutycode,getdutyname from pd_lmdutyget where dutycode in "
					 + " ( "
					 + " select dutycode from pd_lmriskduty where riskcode='"+mConditionField+"' "
					 + " )";
		}
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
		"pd_dutypaygetcode") == 0) {
			logger.debug("%%%%%%%%%%%%%%%%%%%%%%%%:"+mCodeCondition+":"+mConditionField);
			mSQL = "select payplancode,payplanname from pd_lmdutypay where dutycode in "
				 + " ( "
				 + " select dutycode from pd_lmriskduty where riskcode='"+mConditionField+"' "
				 + " )";
			mSQL = mSQL + "  union " + 
					"select getdutycode,getdutyname from pd_lmdutyget where dutycode in "
					 + " ( "
					 + " select dutycode from pd_lmriskduty where riskcode='"+mConditionField+"' "
					 + " )";
				
		}
		//
		
		//tongmeng 2009-03-14 add
		//理赔责任控制给付责任查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
		"griskgetduty") == 0) {
			mSQL = "select distinct c.getdutycode, c.getdutyname "
				 + " from lmdutygetrela a, lmdutyget c "
				 + " where a.getdutycode = c.getdutycode "
				 + " and a.dutycode = "+mCodeCondition+" "
				 + " order by c.getdutycode ";
		}
        
        /**
         * 预付审批结论
         * 
         */
        if(StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("llpreexamconclusion") == 0)
        {
        	 //mSQL = "select code,codename from ldcode where codetype='coursetype' and codealias="+ mCodeCondition;
        	mSQL = "select COMCODE,CodeName from ldcode where "
                + mConditionField + " = " + mCodeCondition
                + " and Codetype='llpreExamconclusion'  and OTHERSIGN='"+mGlobalInput.ComCode.length()+"'";
        }
        
        //险种级别的红利领取方式 add by jiaqiangli 2009-01-21
        if(StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("bonusgetriskmode") == 0)
        {
        	mSQL = "select paramscode,paramsname from LMRiskParamsDef where paramstype='bonusgetmode' and "
                + mConditionField + " = " + mCodeCondition;
        }
        //产品组合查询 add by zy 2010-01-28
        if(StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("portfolio") == 0)
        {
        	mSQL = "select contplancode,contplanname from ldplan where portfolioflag='1' and "
                + mConditionField + " = " + mCodeCondition;
        }
        //查询多语言的证件
        if(StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("mulidtype") == 0)
        {
        	mSQL = "select Code,CodeName from LDCode1 where CodeType='idtype' and "
                + mConditionField + " = " + mCodeCondition;
        }
        ////////////////////////////////////////工作流升级////////////////////////////////////////////////////////
        //计划流程定制选择业务流程
        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
                "busiproxml") == 0) {
            mSQL =
                    "select processid,processname from lwprocessxml   where "
                    + mConditionField + " = " + mCodeCondition
                    + " order by processid"
                    ;
        }
//zhaojiawei
       if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
        "prioritysql") == 0) {
             mSQL ="select   distinct a.activityid,a.activityname from lwactivity a inner join lwprocesstrans d on d.transitionstart =  a.activityid and "
            	 +mConditionField+" = " + mCodeCondition
             + " order by a.activityname";            
       }
       if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
        "priority") == 0) {
        	  mSQL =
                  "select priorityname,priorityid from lwpriority  where "
                  + mConditionField + " = " + mCodeCondition
                  + " order by priorityname"
                  ;          
       }
       if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
                "prioritycolor") == 0) {
                	  mSQL =
                          "select color,colorid from lwprioritycolor  where "
                          + mConditionField + " = " + mCodeCondition
                          + " order by colorid"
                          ;          
        }

        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
        "busipro") == 0) {
            mSQL =
                "select processid,processname from lwprocess   where  "
                + mConditionField + " = " + mCodeCondition
                + " and not exists (select 1 from lwprocessxml where  "+mConditionField + " = " + mCodeCondition+" and lwprocessxml.processid=lwprocess.processid) order by processid"
                ;
         }        

		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("queryactivityid") == 0)
		{
			mSQL = "select  a.ActivityID,a.ActivityName from LWActivity a where  "+mConditionField + " = " + mCodeCondition+" Order by a.ActivityID";
			executeType = 1;
		}
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("queryactivityid2") == 0)
		{
			mSQL = "select  a.ActivityID,a.ActivityName from LWActivity a where 1=1 and BusiType='"+mConditionField+"' Order by a.ActivityID";
			executeType = 1;
		}
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("queryprocessid") == 0)
		{
			mSQL = "select  a.ProcessID,a.ProcessName from LWProcess a where 1=1 Order by a.ProcessID";
			executeType = 1;
		}
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("queryprocessid2") == 0)
		{
		    mSQL = "select a.ProcessID,a.ProcessName from LWProcess a where "+mConditionField + " = " + mCodeCondition+" and valuedflag='1'";
		}
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("jactivityid") == 0)
		{
			if(null==this.mConditionField||"".equals(this.mConditionField)||"1".equalsIgnoreCase(this.mConditionField))
			{
				mSQL = "select  a.ActivityID,a.ActivityName from LWActivity a where 1=1 ";
				executeType = 1;
			}
			else
			{
				mSQL = "select  a.ActivityID,a.ActivityName from LWActivity a where 1=1 and a.ActivityID in (select TransitionStart from LWProcessTrans where ProcessID='"+this.mConditionField+"') and a.ActivityFlag='1' Order by a.ActivityID";
			}

		}
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("queryuser") == 0)
		{
			mSQL = "select a.UserCode,a.UserName from LDUser a where 1=1 Order by a.UserCode";
		} 
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("taskcode") == 0)
		{
			mSQL = " select taskcode,concat('任务_',taskdescribe) from ldtask "
				 + " union "
				 + " select taskgroupcode,concat('队列_',taskdescribe) from ldtaskgroup ";
		} 
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("taskserver") == 0)
		{
			mSQL = " select concat(concat(serverip,':'),serverport),'有效结点' from LDTaskServer a where validflag='1' "
				 + " and not exists (select 1 from ldtaskserverparam where serverip=a.serverip and serverport=a.serverport "
				 + " and paramname='BakNode' and paramvalue='Y')";
		} 
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("hour") == 0)
		{
			mSQL = " select code,codename from ldcode where codetype='hour' order by code/1 "
				 ;
		} 
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("day") == 0)
		{
			mSQL = " select code,codename from ldcode where codetype='day' order by code/1 "
				 ;
		} 
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("week") == 0)
		{
			mSQL = " select code,codename from ldcode where codetype='week' order by code/1 "
				 ;
		} 
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("taskmonth") == 0)
		{
			mSQL = " select code/1,codename from ldcode where codetype='month' order by code/1 "
				 ;
		} 
		//投联账户类型关联险种查询
        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
                      "findinsuacc") == 0)
        {
            mSQL = "select distinct InsuAccNo, InsuAccName from LMRisktoAcc a,lmriskapp b"
                   +" where a.riskcode = b.riskcode and b."
                   + mConditionField + " = " + mCodeCondition
                   + "  order by InsuAccNo asc";
        }
        
//        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
//		        "taskcode") == 0)
//		{
//        	mSQL = "Select taskcode,taskdescribe From ldtask Where 1=1 Order By taskcode";
//		}

        
        //==========================================打印模板名称查询===============================      
        //打印定义号码及名称查询
        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("printid")==0)
        {
        	mSQL = "select a.printid,a.printname from lprtdefinition a order by a.printid";
        }
        
		//add by liuyuxiao 2011-05-23
        if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("querymenu")==0){
        	mSQL = "select nodecode,nodename from ldMenu where childFlag = '0' order by nodecode";
        	executeType = 1;
        }
        
        //tongmeng 2011-06-27 add
      //问题件
		if (mLDCodeSchema.getCodeType().equalsIgnoreCase("issuepost")) {
			if(StrTool.cTrim(mConditionField).equals("10") ||
					StrTool.cTrim(mConditionField).equals("11") ||
					StrTool.cTrim(mConditionField).equals("12") ||
					StrTool.cTrim(mConditionField).equals("13") 
					){
				mSBql
				.append("select code,codename from ldcode where codetype='pd_issuepost' and code<10 and code<>14 order by code");
			}else if(StrTool.cTrim(mConditionField).equals("20") ||
					StrTool.cTrim(mConditionField).equals("30")
					){
				mSBql
				.append("select code,codename from ldcode where codetype='pd_issuepost' and code<20 and code<>14 order by code");
			}else if(StrTool.cTrim(mConditionField).equals("40")
					){
				mSBql
				.append("select code,codename from ldcode where codetype='pd_issuepost' and code<20 and code not in (14) order by code");
			}
			mSQL = mSBql.toString();
		}
		
		//合并统一应用平台功能
		//系统统一编码类型查询
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).compareTo("codetype") == 0) {
			
			mSQL = "select codetype,codetypename from ldunifycodetype where "
						+ mConditionField + " = " + mCodeCondition
						+ " order by syscode,codetype";
			//System.out.println(mSQL);
		}
		
		/** 团险合并 **/
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("precustomerno") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select a.precustomerno,a.precustomername from ldpregrp a where 1 = 1 and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += " order by a.precustomerno";
			} else {
				mSQL = "select a.precustomerno,a.precustomername from ldpregrp a where 1 = 1 order by a.precustomerno";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("queryexp") == 0) {
			
			mSQL = "select code,codename from ldcodeexp a where 1 = 1 and ";
			mSQL += mConditionField + "=" + mCodeCondition ;
			mSQL += " order by orderno,code";
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("occupmidtype") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select a.occupmidcode,a.occupationname from ldoccupation a where  a.occuplevel = '2' ";
				mSQL += " and exists (select 1 from ldoccupation b where a.occupbigcode = b.occupbigcode  and a.occupmidcode = b.occupmidcode ";
				mSQL += " and  b.occuplevel = '3' and  ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += " ) order by a.occupmidcode";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("occupcode") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select a.occupationcode,a.occupationname from ldoccupation a where a.occuplevel='3' and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += " order by a.occupmidcode";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("quotplan") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select a.plancode,a.plandesc,a.sysplancode,a.plantype,a.premcaltype,a.planflag,a.occuptypeflag from lsquotplan a ";
				mSQL += " where 1=1 and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += " order by a.plancode";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("quotrisk") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select a.riskcode,a.riskname from lmrisk a,lmriskapp b ";
				mSQL += " where 1=1 and a.riskcode=b.riskcode and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += " order by a.riskcode";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("quotduty") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select b.dutycode,b.dutyname from lmriskduty a, lmduty b ";
				mSQL += " where 1=1 and a.dutycode=b.dutycode and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += " order by b.dutycode";
			}
		}
		

		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("quotprodrisk") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select distinct a.riskcode,b.riskname from lsquotplandetail a,lmriskapp b ";
				mSQL += " where 1=1 and a.riskcode = b.riskcode and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += " order by a.riskcode";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("risktoacc") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select a.insuaccno,a.insuaccname from lmrisktoacc a ";
				mSQL += " where 1=1 and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += " order by a.insuaccno";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("conditioncomcode") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select a.comcode, a.name from ldcom a ";
				mSQL += " where 1=1 and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += " order by a.comcode";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("managecom") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select a.comcode, a.name from ldcom a ";
				mSQL += " where 1=1 and comcode like '"+ mGlobalInput.ComCode +"%'";
				mSQL += " order by a.comcode";
			}
		}

		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("contplan") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select a.plancode,a.contplanname,a.contplancode,a.plantype,a.premcaltype,a.planflag,a.occuptypeflag,a.GrpContNo from lccontplan a ";
				mSQL += " where 1=1 and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += " order by a.contplancode,a.plancode ";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("contplanrisk") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select distinct a.riskcode,b.riskname from lccontplandetail a,lmriskapp b ";
				mSQL += " where 1=1 and a.riskcode = b.riskcode and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += " order by a.riskcode ";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("position") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select positioncode,positioncodename from lcposition ";
				mSQL += " where 1=1 and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += " order by positioncode ";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("positionpos") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select positioncode,positioncodename from lpposition ";
				mSQL += " where 1=1 and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += " order by positioncode ";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("headbank") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select headbankcode, headbankname from ldheadbank ";
				mSQL += " where state='1' and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += " order by length(headbankcode),headbankcode ";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("infinbank") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select a.finbankcode,a.finbankname,a.accno from finbank a ";
				mSQL += " where a.finbanknature='02'and a.state='1' and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += " order by a.finbankcode ";
			} else {
				
				mSQL = "select a.finbankcode,a.finbankname,a.accno from finbank a ";
				mSQL += " where a.finbanknature='02'and a.state='1' ";
				mSQL += " order by a.finbankcode ";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("popedomlevel") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select a.popedomlevel,a.popedomname from lduwpopedom a ";
				mSQL += " where 1=1 and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += " order by a.popedomlevel ";
			} else {
				
				mSQL = "select a.popedomlevel,a.popedomname from lduwpopedom a ";
				mSQL += " where 1=1 ";
				mSQL += " order by a.popedomlevel ";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("ulriskcode") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select a.riskcode,a.riskname from lmrisk a  ";
				mSQL += " where 1=1 and a.insuaccflag='Y' and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += " order by a.riskcode ";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("fundcode") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select a.insuaccno,a.insuaccname from lmrisktoacc a  ";
				mSQL += " where 1=1 and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += "order by a.insuaccno ";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("accitem") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select accitemcode, accitemname from finaccitem  ";
				mSQL += " where 1=1 and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += "order by accitemcode ";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("fincode1") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select distinct code1, codename1 from fincode ";
				mSQL += " where 1=1 and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += "order by code1 ";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("fincode2") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select distinct code2, codename2 from fincode  ";
				mSQL += " where 1=1 and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += "order by code2 ";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("fincode3") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select distinct code3, codename3 from fincode ";
				mSQL += " where 1=1 and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += "order by code3 ";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("finaccount") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select fincode, finname from finaccount ";
				mSQL += " where 1=1 and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += "order by fincode ";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("outfinbank") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select a.finbankcode,a.finbankname,a.accno from finbank a ";
				mSQL += " where a.finbanknature='03'and a.state='1' ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += " order by a.finbankcode ";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("edorplan") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select a.plancode,a.contplanname,a.contplancode,a.plantype,a.premcaltype,a.planflag,a.occuptypeflag from lpcontplan a ";
				mSQL += " where 1=1 and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += " order by a.contplancode,a.plancode ";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("queryclmreasontype") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select distinct a.reasonno, b.codename from llmodifyconfig a, ldcode b ";
				mSQL += " where 1=1 and b.codetype = 'clmreasontype' and a.reasonno = b.code and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += " order by a.reasonno ";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("queryclmruletype") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select distinct a.subvalue, b.codename from llmodifyconfig a, ldcode b";
				mSQL += " where 1=1 and b.codetype='clmruletype' and a.subvalue=b.code and a.subtype='00' and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += " order by a.subvalue ";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("queryclmriskcode") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select distinct a.subvalue, b.riskname from llmodifyconfig a, lmrisk b ";
				mSQL += " where 1=1 and a.subtype='01' and a.subvalue=b.riskcode and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += " order by a.subvalue ";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("queryclmadjust") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select distinct a.subvalue, b.codename from llmodifyconfig a, ldcode b ";
				mSQL += " where 1=1 and b.codetype='clmadjust' and a.subvalue=b.code and a.subtype='02' and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += " order by a.subvalue ";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("quotnode") == 0) {
			
			if (mCodeCondition != null && !mCodeCondition.equals("")) {
				
				mSQL = "select activityid,activityname from lwactivity ";
				mSQL += " where busitype = '8001' and ";
				mSQL += mConditionField + "=" + mCodeCondition ;
				mSQL += " order by activityid ";
			}
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("esbusstype") == 0) {
			
			mSQL = "select distinct busstype,busstypename from es_doc_def ";
			mSQL += " where 1=1 and ";
			mSQL += mConditionField + "=" + mCodeCondition ;
			mSQL += " order by busstype ";
			
			executeType = 1;
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("claimusercode") == 0) {
			
			mSQL = "select a.usercode,a.username from lduser a,llclaimuser b ";
			mSQL += " where 1=1 and a.usercode=b.usercode and ";
			mSQL += mConditionField + "=" + mCodeCondition ;
			mSQL += " order by a.usercode ";
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("nbdutyfactortype") == 0) {
			
			mSQL = "select distinct a.factortype, a.factortypename from lmclaimfactorconfig a, lmclaimdutyfactorrela b, lccontplandetail c ";
			mSQL += " where a.factortype=b.factortype and a.factorcode=b.factorcode and b.riskcode=c.riskcode and b.dutycode=c.dutycode and a.factorattribute in ('0','1') and  ";
			mSQL += mConditionField + "=" + mCodeCondition ;
			mSQL += " order by a.factortype ";
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("quotdutyfactortype") == 0) {
			
			mSQL = "select distinct a.factortype, a.factortypename from lmclaimfactorconfig a, lmclaimdutyfactorrela b, lsquotplandetail c  ";
			mSQL += " where a.factortype=b.factortype and a.factorcode=b.factorcode and b.riskcode=c.riskcode and b.dutycode=c.dutycode  and a.factorattribute in ('0','1') and  ";
			mSQL += mConditionField + "=" + mCodeCondition ;
			mSQL += " order by a.factortype ";
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("nbriskcode") == 0) {
			
			mSQL = "select distinct a.riskcode, b.riskname from lccontplandetail a, lmrisk b  ";
			mSQL += " where a.riskcode=b.riskcode and  ";
			mSQL += mConditionField + "=" + mCodeCondition ;
			mSQL += " order by a.riskcode ";
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("quotriskcode") == 0) {
			
			mSQL = "select distinct a.riskcode, b.riskname from lsquotplandetail a, lmrisk b  ";
			mSQL += " where a.riskcode=b.riskcode and  ";
			mSQL += mConditionField + "=" + mCodeCondition ;
			mSQL += " order by a.riskcode ";
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("nbdutycode") == 0) {
			
			mSQL = "select distinct a.dutycode, b.dutyname from lccontplandetail a, lmduty b  ";
			mSQL += " where a.dutycode=b.dutycode and  ";
			mSQL += mConditionField + "=" + mCodeCondition ;
			mSQL += " order by a.dutycode ";
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("quotdutycode") == 0) {
			
			mSQL = "select distinct a.dutycode, b.dutyname from lsquotplandetail a, lmduty b  ";
			mSQL += " where a.dutycode=b.dutycode and  ";
			mSQL += mConditionField + "=" + mCodeCondition ;
			mSQL += " order by a.dutycode ";
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("factorcode") == 0) {
			
			mSQL = "select a.factorcode, a.factorname, a.calremark, a.params from lmclaimfactorconfig a, lmclaimdutyfactorrela b  ";
			mSQL += " where a.factortype=b.factortype and a.factorcode=b.factorcode and  ";
			mSQL += mConditionField + "=" + mCodeCondition ;
			mSQL += " order by a.factororder ";
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("jobcategoryinfo") == 0) {
			
			mSQL = "select distinct Jobcategory,Popedomname from LLClaimPopedom   ";
			mSQL += " where 1=1 and  ";
			mSQL += mConditionField + "=" + mCodeCondition ;
			mSQL += " order by Jobcategory ";
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("sharefactortypequot") == 0) {
			
			mSQL = "select distinct factortype, factortypename, factorattribute from (select a.factortype, a.factortypename, a.factorattribute from lmclaimfactorconfig a   ";
			mSQL += " where a.factorattribute='0' and exists(select 1 from lsquotclaimfactorcontrol c where c.factortype=a.factortype and c.factorcode=a.factorcode  and ";
			mSQL += mConditionField + "=" + mCodeCondition ;
			mSQL += " ) union all select a.factortype, a.factortypename, a.factorattribute from lmclaimfactorconfig a, lmclaimdutyfactorrela b, lsquotplandetail c where a.factortype=b.factortype and a.factorcode=b.factorcode and b.riskcode=c.riskcode and b.dutycode=c.dutycode and ";
			mSQL += mConditionField + "=" + mCodeCondition ;
			mSQL += " and a.factorattribute='2') order by factortype ";
		}
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("sharefactortypenb") == 0) {
	
			mSQL = "select distinct factortype, factortypename, factorattribute from (select a.factortype, a.factortypename, a.factorattribute from lmclaimfactorconfig a where a.factorattribute='0' and exists(select 1 from lcclaimfactorcontrol c where c.factortype=a.factortype and c.factorcode=a.factorcode and ";
			mSQL += mConditionField + "=" + mCodeCondition ;
			mSQL += " ) union all select a.factortype, a.factortypename, a.factorattribute from lmclaimfactorconfig a, lmclaimdutyfactorrela b, lccontplandetail c where a.factortype=b.factortype and a.factorcode=b.factorcode and b.riskcode=c.riskcode and b.dutycode=c.dutycode and ";
			mSQL += mConditionField + "=" + mCodeCondition ;
			mSQL += " and a.factorattribute='2') order by factortype ";
		} 
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"diseasecode") == 0) {
			mSQL = "select icdcode,icdname from LDDisease a where 1= "+mCodeCondition.trim();
			executeType = 1;
		}
		
		//指定调查人下拉
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"adjustper") == 0) {
			mSQL = "select a.usercode,b.username from  LLClaimUser a,lduser b where 1=1 and a.usercode=b.usercode and "
					
			+ mConditionField + " = " + mCodeCondition;
					
			executeType = 1;
		}
		
		//残疾级别下拉
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"llparadeformity1") == 0) {
			mSQL = "select distinct defoclass ,defoclassname from llparadeformity where "
					+ mConditionField
					+ "= 1 and defotype = "
					+ mCodeCondition.trim()
					+ " order by defoclass";
			executeType = 1;
		}
		
		//伤残下拉
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"llparadeformity3") == 0) {
			mSQL = "select distinct defocode ,defoname from llparadeformity where "
					+ mConditionField
					+ "= "
					+ mCodeCondition.trim()
					+ " order by defoclass";
			executeType = 1;
		}
		
		//黑名单调整原因
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"claimrule") == 0) {
			mSQL = "select a.ruleno,a.ruleremark from llclaimrule a where 1=1 order by a.ruleno";
					
			executeType = 1;		
		}
		
		//准客户所属上级客户
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo(
				"upcustomerno") == 0) {
			mSQL = "select precustomerno,precustomername from ldpregrp where "
					+ mConditionField
					+ "= "
					+ mCodeCondition.trim()
					+ " order by precustomerno";
			executeType = 1;
		}
		
		// 营改增
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).equalsIgnoreCase("LDVATFeeTypeCodeConfig")) {
            mSQL = "select code,codename from ldcode where codetype='LDVATFeeTypeCodeConfig'";
        }
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).equalsIgnoreCase("LDVATGrp")) {
            mSQL = "select RiskCode,RiskGrp from LDVATGrp where RiskGrp in (select RiskGrp from LDVATConfig where ";
            mSQL+="LDVATGrp.RiskGrp = LDVATConfig.RiskGrp and IsTaxable='Y' and ";
            mSQL+=mConditionField;
            mSQL+=" = ";
            mSQL+=mCodeCondition;
            mSQL+=") order by RiskCode ";
        }
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).equalsIgnoreCase("RiskCodeAPP")) {
            mSQL = "select riskcode,riskname from lmrisk";
        }
		
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).equalsIgnoreCase("RiskGrpCode")) {
            mSQL = "select riskgrp,'name' from LDVATGrp";
        }

		///////////////////产品定义险种代码//////////////////
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("pdriskdefing") == 0) {
			mSQL = "select MissionProp2,(select riskname from pd_lmrisk where riskcode=a.missionprop2) "
					+ "from lwmission a  where activityid='pd00000001' ";
			// 契约信息定义
			if(StrTool.cTrim(mConditionField).toLowerCase().compareTo("cont") == 0){
				mSQL += " and (MissionProp5 != 1 or MissionProp5 is null) order by missionprop1 desc";
			// 保全信息定义
			}else if(StrTool.cTrim(mConditionField).toLowerCase().compareTo("edor") == 0){
				mSQL += " and (MissionProp7 != 1 or MissionProp7 is null) order by missionprop1 desc";
			// 理赔信息定义
			}else if(StrTool.cTrim(mConditionField).toLowerCase().compareTo("claim") == 0){
				mSQL += " and (MissionProp6 != 1 or MissionProp6 is null) order by missionprop1 desc";
			// 保监会信息定义
			}else if(StrTool.cTrim(mConditionField).toLowerCase().compareTo("lfrisk") == 0){
				mSQL += " and (MissionProp8 != 1 or MissionProp8 is null) order by missionprop1 desc";
			}
			
		}
		///////////////////产品审核险种代码//////////////////
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("pdriskaudit") == 0) {
			mSQL = "select MissionProp2,(select riskname from pd_lmrisk where riskcode=a.missionprop2) "
					+ "from lwmission a  where activityid='pd00000002' order by missionprop1 desc";
		}
		///////////////////产品测试发布险种代码//////////////////
		if (StrTool.cTrim(mLDCodeSchema.getCodeType()).toLowerCase().compareTo("pdrisktest") == 0) {
			mSQL = "select MissionProp2,(select riskname from pd_lmrisk where riskcode=a.missionprop2) "
					+ "from lwmission a  where activityid='pd00000003' order by missionprop1 desc";
		}
		
		/** 团险合并 END **/
		// 其他LDCODE表中定义的引用
		if (mSQL.equals("")) {
			//
			mSBql.append("select Code, CodeName, CodeAlias, ComCode, OtherSign from ldcode where ");
			mSBql.append(mConditionField);
			mSBql.append(" = ");
			mSBql.append(mCodeCondition);
			mSBql.append(" and codetype = '");
			mSBql.append(StrTool.cTrim(mLDCodeSchema.getCodeType())
					.toLowerCase()
					+ "'");

			if (mCodeValue != null && !mCodeValue.equals("")) {
				mSBql.append(" and code='" + mCodeValue + "' ");
			}

			mSBql.append(" order by Code");

			mSQL = mSBql.toString();
		}
		
		//logger.debug("CodeQueryBL SQL : " + mSQL);
		
		
		if (executeType == 0) {
			// 使用截取方式查询
			mResultStr = codeCacheService.getCodeList(mLDCodeSchema.getCodeType());
			if(mResultStr==null||mResultStr.equals("")){
				logger.debug("CodeQueryBL SQL : " + mSQL);
				mResultStr = mExeSQL.getEncodedResult(mSQL, 1);
			}
		} else {
			// 如果不是使用截取方式查询，并且没有其它的条件列z
			if (this.m_bCanBeCached == true) {
				// 如果SQL中包含有8621%之类的语句，表示有管理机构之类的条件，也不能使用缓存
				if (mSQL.matches(".*86.*\\%.*")) {
					logger.debug("CodeQueryBL SQL : " + mSQL);
					mResultStr = mExeSQL.getEncodedResult(mSQL);
					m_bCanBeCached=false;
				} else {
					mResultStr = codeCacheService.getCodeList(mLDCodeSchema.getCodeType());
					if(mResultStr==null||mResultStr.equals("")){
						logger.debug("CodeQueryBL SQL : " + mSQL);
						mResultStr = (String) this.m_hashResultStr.get(mSQL);
						if (mResultStr == null) {
							mResultStr = mExeSQL.getEncodedResult(mSQL);
							if (mResultStr.length() > 500) {
								this.m_hashResultStr.put(mSQL, TOO_LONG_FLAG);
							} else {
								this.m_hashResultStr.put(mSQL, mResultStr);
							}
						} else if (mResultStr.equals(TOO_LONG_FLAG)) {
							mResultStr = mExeSQL.getEncodedResult(mSQL);
						}
					}
				}
			} else {
				mResultStr = mExeSQL.getEncodedResult(mSQL);
			}
			
			
			
			// else if (executeType == 1)
			// 全部数据查询
			// mResultStr = mExeSQL.getEncodedResult(mSQL);
		}

		// logger.debug("CodeQuery mResultStr:"+mResultStr);
		if (mExeSQL.mErrors.needDealError()) {
			// @@错误处理,在ExeSQL中已进行错误处理，这里直接返回即可。
			this.mErrors.copyAllErrors(mExeSQL.mErrors);
			// 如果sql执行错误，则返回sql描述
			logger.debug("Code Query Error Sql:" + mSQL);
		}
		mResult.clear();
		mResult.add(mResultStr);
		return true;
	}

    public String transI18NMenu(String str)
    {
    	if("".equals(str)){
    		return "";
    	}
		//String[] str1 = str.split("\\"+SysConst.RECORDSPLITER); // ?????逸?????????????????
		//Locale locale = this.mGlobalInput.locale;
		//StringManager mes = null;
		try
		{
			/*
			if(locale!=null)
			{
				mes =StringManager.getManager("i18n",locale);
			}
			else
			{
				mes = StringManager.getManager("i18n",Locale.ENGLISH);
			}
			*/
		}
		catch(Throwable e)
		{
			return str;
		}
		/*
		String tRet =mes.getString(str);
		if(tRet==null||tRet.equals("")){
			tRet = str;
		}
		*/
		return str;//tRet
    }

	// private String test()
	// {
	//    	
	// return (new com.sinosoft.lis.pubfun.CodeQueryUI()).test(this);
	// }

	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		// String t = "1111:2222:3333";
		// logger.debug(t.substring(0,t.lastIndexOf(":")));
		// logger.debug(t.substring(t.lastIndexOf(":")+1));
		// logger.debug((new CodeQueryBL()).test());
		// CodeQueryBL codeQueryBL1 = new CodeQueryBL();
		// codeQueryBL1.mLDCodeSchema.setCodeType("comcode");
		// codeQueryBL1.queryData();
		// VData tVData = codeQueryBL1.getResult();
		// logger.debug(codeQueryBL1.mSQL);
		// logger.debug(tVData.get(0));
	}
}
