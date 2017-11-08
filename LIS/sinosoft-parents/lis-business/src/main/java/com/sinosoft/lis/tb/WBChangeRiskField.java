package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.sinosoft.lis.db.BPORiskPlanDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDImpartDB;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.CheckFieldCom;
import com.sinosoft.lis.pubfun.FieldCarrier;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.BPORiskPlanSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LMCheckFieldSchema;
import com.sinosoft.lis.vschema.BPORiskPlanSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LMCheckFieldSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 双岗录入和外包对于险种信息的转换使用的统一数据转换类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author tongmeng
 * @version 6.5
 */
public class WBChangeRiskField {
	private LCInsuredSchema tLCInsuredSchema=new LCInsuredSchema();
private static Logger logger = Logger.getLogger(WBChangeRiskField.class);
	public CErrors mErrors = new CErrors();
	private List mBomList = new ArrayList();
	public WBChangeRiskField() {

	}

	/**
	 * 字段转换公共类
	 * @param tSchema
	 * @param tTransferData
	 * @param tType
	 */
	public void changeRiskField(Schema tSchema, TransferData tTransferData,
			String tType) {
		//采用指向引用的方式,直接修改自动的值
		Schema tCurrentSchema = tSchema;
		if (tCurrentSchema == null) {
			return;
		}
		//field RiskCode,PayEndYear,PayEndYearFlag,PayIntv,InsuYear,InsuYearFlag 
		TransferData transTrandferData = new TransferData();
		for (int i = 1; i < tSchema.getFieldCount(); i++) {
			String tFieldName = tSchema.getFieldName(i);
			String tFieldValue = tSchema.getV(i);
			if(tFieldValue.equals("null"))
				tFieldValue = "";
			if (tFieldName.equals("RiskCode")
					|| tFieldName.equals("PayEndYear")
					|| tFieldName.equals("PayEndYearFlag")
					|| tFieldName.equals("PayIntv")
					|| tFieldName.equals("InsuYear")
					|| tFieldName.equals("InsuYearFlag")
					|| tFieldName.equals("LiveGetMode")
					|| tFieldName.equals("BonusGetMode")
					|| tFieldName.equals("RnewFlag")
					|| tFieldName.equals("PayYears")
					|| tFieldName.equals("Prem")
					|| tFieldName.equals("Amnt")
					|| tFieldName.equals("Mult")
					|| tFieldName.equals("ImpartParamModle")
					|| tFieldName.equals("PrtFlag")
					|| tFieldName.equals("Insured1")
					|| tFieldName.equals("ImpartCode")
					|| tFieldName.equals("ImpartVer")) {
				transTrandferData.setNameAndValue(tFieldName, tFieldValue);
			}
		}
		//字段转换
		if (tType.equals("WB")) {
			//tongmeng 2009-03-24 双岗不做险种级别的转换
			
			if (!this.changeRiskDetail(transTrandferData, tTransferData, tType)) {
				return;
			}
		} else if (tType.equals("JM")) {
			//tongmeng 2009-03-24 双岗不做险种级别的转换
			/*
			if (!this.changeRiskDetailForJM(transTrandferData, tTransferData,
					tType)) {
				return;
			}
			*/
			if (!this.changeImpartDetailForJM(transTrandferData, tTransferData,
					tType)) {
				return;
			}
		}
		//反置相应的数据
		Vector tFieldVec = new Vector();
		tFieldVec = transTrandferData.getValueNames();
		for (int i = 0; i < tFieldVec.size(); i++) {
			String tName = (String) tFieldVec.get(i);
			String tValue = (String) transTrandferData.getValueByName(tName);
			for (int n = 1; n < tSchema.getFieldCount(); n++) {
				String tFieldName = tSchema.getFieldName(n);
				if (tFieldName.equals(tName)) {
					tSchema.setV(tName, tValue);
				}
			}
			//tTransferData中也要处理,防止有getDutyKind之类的数据
			//简化处理办法
			//			tTransferData.removeByName(tName);
			//			tTransferData.setNameAndValue(tName, tValue);
			//复杂处理办法
			Vector transFieldVec = new Vector();
			transFieldVec = tTransferData.getValueNames();
			for (int n = 0; n < transFieldVec.size(); n++) {
				String transName = (String) transFieldVec.get(n);
				if (transName.equals(tName)) {
					tTransferData.removeByName(tName);
					tTransferData.setNameAndValue(tName, tValue);
				}

			}
		}
	}
	
	/**
	 * 字段转换公共类
	 * @param tSchema
	 * @param tTransferData
	 * @param tType
	 */
	public boolean changeRiskFieldWB(Schema tSchema, TransferData tTransferData,
			String tType) {
		//采用指向引用的方式,直接修改自动的值
		Schema tCurrentSchema = tSchema;
		if (tCurrentSchema == null) {
			return false;
		}
		//field RiskCode,PayEndYear,PayEndYearFlag,PayIntv,InsuYear,InsuYearFlag 
		TransferData transTrandferData = new TransferData();
		for (int i = 1; i < tSchema.getFieldCount(); i++) {
			String tFieldName = tSchema.getFieldName(i);
			String tFieldValue = tSchema.getV(i);
			if(tFieldValue.equals("null"))
				tFieldValue = "";
			if (tFieldName.equals("RiskCode")
					|| tFieldName.equals("PayEndYear")
					|| tFieldName.equals("PayEndYearFlag")
					|| tFieldName.equals("PayIntv")
					|| tFieldName.equals("InsuYear")
					|| tFieldName.equals("InsuYearFlag")
					|| tFieldName.equals("LiveGetMode")
					|| tFieldName.equals("BonusGetMode")
					|| tFieldName.equals("RnewFlag")
					|| tFieldName.equals("PayYears")
					|| tFieldName.equals("Prem")
					|| tFieldName.equals("Amnt")
					|| tFieldName.equals("Mult")
					|| tFieldName.equals("ImpartParamModle")
					|| tFieldName.equals("PrtFlag")
					|| tFieldName.equals("Insured1")
					|| tFieldName.equals("ImpartCode")
					|| tFieldName.equals("ImpartVer")) {
				transTrandferData.setNameAndValue(tFieldName, tFieldValue);
			}
		}
		//字段转换
		if (tType.equals("WB")) {
			//tongmeng 2009-03-24 双岗不做险种级别的转换
			
			if (!this.changeRiskDetail(transTrandferData, tTransferData, tType)) {
				return false;
			}
		} 
		//反置相应的数据
		Vector tFieldVec = new Vector();
		tFieldVec = transTrandferData.getValueNames();
		for (int i = 0; i < tFieldVec.size(); i++) {
			String tName = (String) tFieldVec.get(i);
			String tValue = (String) transTrandferData.getValueByName(tName);
			for (int n = 1; n < tSchema.getFieldCount(); n++) {
				String tFieldName = tSchema.getFieldName(n);
				if (tFieldName.equals(tName)) {
					tSchema.setV(tName, tValue);
				}
			}
			//tTransferData中也要处理,防止有getDutyKind之类的数据
			//简化处理办法
			//			tTransferData.removeByName(tName);
			//			tTransferData.setNameAndValue(tName, tValue);
			//复杂处理办法
			Vector transFieldVec = new Vector();
			transFieldVec = tTransferData.getValueNames();
			for (int n = 0; n < transFieldVec.size(); n++) {
				String transName = (String) transFieldVec.get(n);
				if (transName.equals(tName)) {
					tTransferData.removeByName(tName);
					tTransferData.setNameAndValue(tName, tValue);
				}

			}
		}
		
		return true;
	}

	/**
	 * 对tTransferData内部的字段进行转换
	 * @param tTransferData
	 * @param tType
	 * @return
	 */
	private boolean changeRiskDetail(TransferData transTransferData,
			TransferData tTransferData, String tType) {
		boolean tFlag = true;
		//field RiskCode,PayEndYear,PayEndYearFlag,PayIntv,InsuYear,InsuYearFlag 
		String tRiskCode = transTransferData.getValueByName("RiskCode") == null ? ""
				: (String) transTransferData.getValueByName("RiskCode");
		int tPayEndYear = Integer.parseInt(transTransferData
				.getValueByName("PayEndYear") == null ? "0"
				: (String) transTransferData.getValueByName("PayEndYear"));
		String tPayEndYearFlag = transTransferData
				.getValueByName("PayEndYearFlag") == null ? ""
				: (String) transTransferData.getValueByName("PayEndYearFlag");
		int tPayIntv = Integer.parseInt(transTransferData
				.getValueByName("PayIntv") == null ? ""
				: (String) transTransferData.getValueByName("PayIntv"));
		int tInsuYear = Integer.parseInt(transTransferData
				.getValueByName("InsuYear") == null ? "0"
				: (String) transTransferData.getValueByName("InsuYear"));
		String tInsuYearFlag = transTransferData.getValueByName("InsuYearFlag") == null ? ""
				: (String) transTransferData.getValueByName("InsuYearFlag");
		String tLiveGetMode = transTransferData.getValueByName("LiveGetMode") == null ? ""
				: (String) transTransferData.getValueByName("LiveGetMode");
		String tBonusGetMode = transTransferData.getValueByName("BonusGetMode") == null ? ""
				: (String) transTransferData.getValueByName("BonusGetMode");
		String tPayYears = transTransferData.getValueByName("PayYears") == null ? ""
				: (String) transTransferData.getValueByName("PayYears");
		int tRnewFlag = Integer.parseInt(transTransferData
				.getValueByName("RnewFlag") == null ? "-2"
				: (String) transTransferData.getValueByName("RnewFlag"));
		//外包转换规则
		//if(tType.equals("WB"))
		{
			try {   								
				//如果是个人长瑞/长泰/附加提前给付重大疾病险/长顺/长宁/金榜题名/如意年年/长乐/安康
				if ((tRiskCode.equals("112401")
						|| tRiskCode.equals("112208")
						|| tRiskCode.equals("121501")
						|| tRiskCode.equals("112101")
						|| tRiskCode.equals("112201")
						|| tRiskCode.equals("112202")
						|| tRiskCode.equals("112203")
						|| tRiskCode.equals("112206")
						|| tRiskCode.equals("112207")
						|| tRiskCode.equals("112204")
						|| tRiskCode.equals("111301")
						|| tRiskCode.equals("122201")
						|| tRiskCode.equals("122202")
						|| tRiskCode.equals("112211") //add by wk 2008-1-17  久久同瑞两全保险 
						|| tRiskCode.equals("121504") //add by wk 2008-1-17  附加久久同康额外给付重大疾病保险
						|| tRiskCode.equals("121602") //add by wk 2008-1-17  附加久久同祥意外伤害保险
						|| tRiskCode.equals("121303") //add by wk 2008-1-17  附加久久同寿定期寿险
						//add by tm 2008-04-03
						|| tRiskCode.equals("112212")
						|| tRiskCode.equals("121305")
						|| tRiskCode.equals("121505")
						//add by tm 2008-04-16
						|| tRiskCode.equals("112213")
						//add by ln 2008-05-07
						|| tRiskCode.equals("112214")
						//add by ln 2008-08-28 久久同安和金镶玉
						|| tRiskCode.equals("312205")
						//add by ln 2008-08-28
						|| tRiskCode.equals("312202")
						|| tRiskCode.equals("312203")
						|| tRiskCode.equals("312204")
						|| tRiskCode.equals("121507")
						|| tRiskCode.equals("312204")
						//add by ln 2008-10-28 年年红两全
						|| tRiskCode.equals("112215")
						//add by ln 2009-3-24 附加提前给付重疾
						|| tRiskCode.equals("121508")
						//add by ln 2009-5-26
				        || tRiskCode.equals("112216")
				        //add by hanbin 2010-04-30 金玉良缘 || 金康附加额外给付重大疾病
				        || tRiskCode.equals("312207")
				        || tRiskCode.equals("321501")
				        || tRiskCode.equals("121509")
				        || tRiskCode.equals("121510")
				        || tRiskCode.equals("112217")
				)
						&& ((tPayEndYear == 1000 && "A".equals(tPayEndYearFlag)) || (tPayEndYear == 1 && "Y"
								.equals(tPayEndYearFlag))) //长险交费期间“趸交”录入为“1年”
						&& tPayIntv == 0) {
					transTransferData.removeByName("PayEndYear");
					transTransferData.removeByName("PayEndYearFlag");
					transTransferData.setNameAndValue("PayEndYear", String
							.valueOf(tInsuYear));
					transTransferData.setNameAndValue("PayEndYearFlag",
							tInsuYearFlag);
					tPayEndYear = tInsuYear;
					tPayEndYearFlag = tInsuYearFlag;
				}

				//初始化短险的交费期间
				if ((tRiskCode.equals("111601") || tRiskCode.equals("111602")
						|| tRiskCode.equals("111603")
						|| tRiskCode.equals("121801")
						|| tRiskCode.equals("131602")
						|| tRiskCode.equals("121704")
						|| tRiskCode.equals("121705")
						|| tRiskCode.equals("121601")
						|| tRiskCode.equals("111801") || tRiskCode
						.equals("111802"))
						&& (tPayEndYear == 1000 && "A".equals(tPayEndYearFlag))
						&& tPayIntv == 0) {
					transTransferData.removeByName("PayEndYear");
					transTransferData.removeByName("PayEndYearFlag");
					transTransferData.setNameAndValue("PayEndYear", String
							.valueOf(tInsuYear));
					transTransferData.setNameAndValue("PayEndYearFlag",
							tInsuYearFlag);
					tPayEndYear = tInsuYear;
					tPayEndYearFlag = tInsuYearFlag;
				}

				//险种无“生存保险金/年金处理方式”责任，无论录入有、无内容时，均按“空”转换，无需导入系统，无需给出问题件。
				if (tRiskCode.equals("112211")) //add by wk 2008-1-17  久久同瑞两全保险 
				{
					transTransferData.removeByName("LiveGetMode");
					transTransferData.setNameAndValue("LiveGetMode", "");
				}
				
				if (tRiskCode.equals("111602")
						|| tRiskCode.equals("111802")) //add by ln 2009-9-15
				{
					transTransferData.removeByName("LiveGetMode");
					transTransferData.setNameAndValue("LiveGetMode", "");
				}

				//对于趸交的MS康泰一生重大疾病保险的交费年期做特殊转换
				if (tRiskCode.equals("111504"))
				{
					if(((tPayEndYear == 1000 && "A".equals(tPayEndYearFlag)) || (tPayEndYear == 1 && "Y"
								.equals(tPayEndYearFlag))) && tPayIntv == 0) {
						transTransferData.removeByName("PayEndYear");
						transTransferData.setNameAndValue("PayEndYear", String
								.valueOf(105));
						tPayEndYear = 105;
					}
					transTransferData.removeByName("LiveGetMode");
					transTransferData.setNameAndValue("LiveGetMode", "");
					
				}
				
				//MS幸福360少儿两全保险（分红型）
				if(tRiskCode.equals("112219")){
					if(((tPayEndYear == 1000 && "A".equals(tPayEndYearFlag))|| (tPayEndYear == 1 && "Y"
							.equals(tPayEndYearFlag))) && tPayIntv == 0){
						transTransferData.removeByName("PayEndYear");
						transTransferData.removeByName("PayEndYearFlag");
						transTransferData.setNameAndValue("PayEndYear", String
								.valueOf(tInsuYear));
						transTransferData.setNameAndValue("PayEndYearFlag",
								tInsuYearFlag);
					}
				}
				if(tRiskCode.equals("121511")){
					if(((tPayEndYear == 1000 && "A".equals(tPayEndYearFlag))|| (tPayEndYear == 1 && "Y"
							.equals(tPayEndYearFlag))) && tPayIntv == 0){
						transTransferData.removeByName("PayEndYear");
						transTransferData.removeByName("PayEndYearFlag");
						transTransferData.setNameAndValue("PayEndYear", String
								.valueOf(tInsuYear));
						transTransferData.setNameAndValue("PayEndYearFlag",
								tInsuYearFlag);
					}
				}

				//如果是银代险种，保险期间与交费期间相同
				if (tRiskCode.equals("312202") || tRiskCode.equals("312203")
						//|| tRiskCode.equals("312204")
						) {
					if ((tPayEndYear > 0 && !"".equals(StrTool
							.cTrim(tPayEndYearFlag)))
							&& (tInsuYear == 0 && "".equals(StrTool
									.cTrim(tInsuYearFlag)))) {
						transTransferData.removeByName("InsuYear");
						transTransferData.removeByName("InsuYearFlag");
						transTransferData.setNameAndValue("InsuYear", String
								.valueOf(tPayEndYear));
						transTransferData.setNameAndValue("InsuYearFlag",
								tPayEndYearFlag);
						tInsuYear = tPayEndYear;
						tInsuYearFlag = tPayEndYearFlag;
					}

					if (((tPayEndYear == 0 && "".equals(StrTool
							.cTrim(tPayEndYearFlag))) || (tPayEndYear == 1
							&& "Y".equals(StrTool.cTrim(tPayEndYearFlag)) && tPayIntv == 0)) //交费间隔为趸交，交费年期填写1年时需要转换
							&& (tInsuYear > 0 && !"".equals(StrTool
									.cTrim(tInsuYearFlag)))) {
						transTransferData.removeByName("PayEndYear");
						transTransferData.removeByName("PayEndYearFlag");
						transTransferData.setNameAndValue("PayEndYear", String
								.valueOf(tInsuYear));
						transTransferData.setNameAndValue("PayEndYearFlag",
								tInsuYearFlag);
						tPayEndYear = tInsuYear;
						tPayEndYearFlag = tInsuYearFlag;
					}
				}	
				
				//金玉满堂D
				if (tRiskCode.equals("312204")) {					
					if ((tPayEndYear > 0 && !"".equals(StrTool
							.cTrim(tPayEndYearFlag)))
							&& (tInsuYear == 0 && "".equals(StrTool
									.cTrim(tInsuYearFlag)))) {
						transTransferData.removeByName("InsuYear");
						transTransferData.removeByName("InsuYearFlag");
						transTransferData.setNameAndValue("InsuYear", String
								.valueOf(tPayEndYear));
						transTransferData.setNameAndValue("InsuYearFlag",
								tPayEndYearFlag);
						tInsuYear = tPayEndYear;
						tInsuYearFlag = tPayEndYearFlag;
					}

					if ((tPayEndYear == 0 && "".equals(StrTool
							.cTrim(tPayEndYearFlag))) //--交费间隔为趸交，交费年期填写1年时需要转换
							&& (tInsuYear > 0 && !"".equals(StrTool
									.cTrim(tInsuYearFlag)))) {
						transTransferData.removeByName("PayEndYear");
						transTransferData.removeByName("PayEndYearFlag");
						transTransferData.setNameAndValue("PayEndYear", String
								.valueOf(tInsuYear));
						transTransferData.setNameAndValue("PayEndYearFlag",
								tInsuYearFlag);
						tPayEndYear = tInsuYear;
						tPayEndYearFlag = tInsuYearFlag;
					}
					
					tTransferData.removeByName("GetDutyKind");
					tTransferData.setNameAndValue("GetDutyKind", String
							.valueOf(tPayEndYear));					
					
				}

				//金玉满堂E 2008-12-18 ln add
				if (tRiskCode.equals("312206")) {
					if ((tPayEndYear == 1000 && "A".equals(tPayEndYearFlag))
							|| (tPayEndYear == 1 && "Y".equals(tPayEndYearFlag))) {
						if (tPayIntv == 0)//长险交费期间“趸交”录入为“1年”
						{
							transTransferData.removeByName("PayEndYear");
							transTransferData.removeByName("PayEndYearFlag");
							transTransferData.setNameAndValue("PayEndYear",
									String.valueOf(tInsuYear));
							transTransferData.setNameAndValue("PayEndYearFlag",
									tInsuYearFlag);
							tPayEndYear = tInsuYear;
							tPayEndYearFlag = tInsuYearFlag;
						}
					} else {
						CError.buildErr(this, "交费年期错误!");
						return false;
					}

				}

				//如果长裕或者长顺/金榜题目/
				if (tRiskCode.equals("112203") || tRiskCode.equals("112202")
						|| tRiskCode.equals("112201")
						|| tRiskCode.equals("112206")) {
					/*if ("".equals(StrTool.cTrim(tLiveGetMode)) //如果录入为空或现金，转换为累计生息
							|| "2".equals(StrTool.cTrim(tLiveGetMode))) {
						transTransferData.removeByName("LiveGetMode");
						transTransferData.setNameAndValue("LiveGetMode", "1");
					}*/
					//默认为累计生息
					transTransferData.removeByName("LiveGetMode");
					transTransferData.setNameAndValue("LiveGetMode", "1");

					if ("".equals(StrTool.cTrim(tBonusGetMode))) {
						transTransferData.removeByName("BonusGetMode");
						transTransferData.setNameAndValue("BonusGetMode", "1");
					}
				}

				//added by ln 2008.4.24
				if (tRiskCode.equals("112401")) {
					if ("".equals(StrTool.cTrim(tLiveGetMode)) //如果录入为空或现金，转换为累计生息
							|| "2".equals(StrTool.cTrim(tLiveGetMode))) {
						transTransferData.removeByName("LiveGetMode");
						transTransferData.setNameAndValue("LiveGetMode", "1");
					}
				}
				//*********end added by ln 208.4.24

				//如果长宁
				if ((tRiskCode.equals("112101"))) {
					transTransferData.removeByName("LiveGetMode");
					transTransferData.setNameAndValue("LiveGetMode", "");
					if ("".equals(StrTool.cTrim(tBonusGetMode))) {
						transTransferData.removeByName("BonusGetMode");
						transTransferData.setNameAndValue("BonusGetMode", "1");
					}
				}

				if (tRiskCode.equals("112212")) {
					/*if ("".equals(StrTool.cTrim(tLiveGetMode)) // 如果录入为空或现金，转换为累计生息
							|| "2".equals(StrTool.cTrim(tLiveGetMode))) {
						transTransferData.removeByName("LiveGetMode");
						transTransferData.setNameAndValue("LiveGetMode", "1");

					}*/
					//默认为累积生息
					transTransferData.removeByName("LiveGetMode");
					transTransferData.setNameAndValue("LiveGetMode", "1");
					//added by liuning 2008.4.2
					if (tInsuYear == 60 && "A".equals(tInsuYearFlag)) {
						tTransferData.removeByName("GetDutyKind");
						tTransferData.setNameAndValue("GetDutyKind", "1");
					}
					if (tInsuYear == 80 && "A".equals(tInsuYearFlag)) {
						tTransferData.removeByName("GetDutyKind");
						tTransferData.setNameAndValue("GetDutyKind", "2");
					}
					//end added
					//	

				}
				// 富贵年年附加险
				if (tRiskCode.equals("121305") || tRiskCode.equals("121505")) {
					transTransferData.removeByName("LiveGetMode");
					transTransferData.setNameAndValue("LiveGetMode", "");
					transTransferData.removeByName("BonusGetMode");
					transTransferData.setNameAndValue("BonusGetMode", "");
				}
				// MS如意相伴两全保险（分红型）added by ln 2008.4.7
				if (tRiskCode.equals("112213")) {
					transTransferData.removeByName("LiveGetMode");
					transTransferData.setNameAndValue("LiveGetMode", "");

				}
				
				//2009-5-7 ln add
				if (tRiskCode.equals("112216")) {
					/*if ("".equals(StrTool.cTrim(tLiveGetMode)) // 如果录入为空，转换为领取现金
							) {
						transTransferData.removeByName("LiveGetMode");
						transTransferData.setNameAndValue("LiveGetMode", "2");
					}*/
					transTransferData.removeByName("LiveGetMode");
					transTransferData.setNameAndValue("LiveGetMode", "");
					if ("".equals(StrTool.cTrim(tBonusGetMode))) {
						transTransferData.removeByName("BonusGetMode");
						transTransferData.setNameAndValue("BonusGetMode", "1");
					}
				}

				// 久久至尊终身寿险 added by ln 2008-05-07
				if (tRiskCode.equals("112214")) {
					transTransferData.removeByName("LiveGetMode");
					transTransferData.setNameAndValue("LiveGetMode", "");
				}

				// 长顺
				if ((tRiskCode.equals("112202"))) {
					if ((tPayEndYear == 1000 && "A".equals(tPayEndYearFlag))
							|| (tPayEndYear == 60 && "A"
									.equals(tPayEndYearFlag))) {
						tTransferData.removeByName("GetDutyKind");
						tTransferData.setNameAndValue("GetDutyKind", "0");
					}

					if ((tPayEndYear == 10 && "Y".equals(tPayEndYearFlag))) {
						tTransferData.removeByName("GetDutyKind");
						tTransferData.setNameAndValue("GetDutyKind", "1");
					}

					if ((tPayEndYear == 20 && "Y".equals(tPayEndYearFlag))) {
						tTransferData.removeByName("GetDutyKind");
						tTransferData.setNameAndValue("GetDutyKind", "2");
					}
				}

				//如意年年
				if ((tRiskCode.equals("112207"))) {
					if ((tPayEndYear == 70 && "A".equals(tPayEndYearFlag))
							|| (tPayEndYear == 60 && "A"
									.equals(tPayEndYearFlag))) {
						tTransferData.removeByName("GetDutyKind");
						tTransferData.setNameAndValue("GetDutyKind", "0");
					}

					if ((tPayEndYear == 5 && "Y".equals(tPayEndYearFlag))) {
						tTransferData.removeByName("GetDutyKind");
						tTransferData.setNameAndValue("GetDutyKind", "1");
					}

					if ((tPayEndYear == 10 && "Y".equals(tPayEndYearFlag))) {
						tTransferData.removeByName("GetDutyKind");
						tTransferData.setNameAndValue("GetDutyKind", "2");
					}

					if ((tPayEndYear == 15 && "Y".equals(tPayEndYearFlag))) {
						tTransferData.removeByName("GetDutyKind");
						tTransferData.setNameAndValue("GetDutyKind", "3");
					}

					if ((tPayEndYear == 20 && "Y".equals(tPayEndYearFlag))) {
						tTransferData.removeByName("GetDutyKind");
						tTransferData.setNameAndValue("GetDutyKind", "4");
					}
					
					transTransferData.removeByName("LiveGetMode");//默认为累计生息
					transTransferData.setNameAndValue("LiveGetMode", "1");
				}

				//长泰
				if ((tRiskCode.equals("112208"))) {
					/*if ("".equals(StrTool.cTrim(tLiveGetMode))) //如果录入为空，转换为现金
					{
						transTransferData.removeByName("LiveGetMode");
						transTransferData.setNameAndValue("LiveGetMode", "2");
					}*/
					transTransferData.removeByName("LiveGetMode");
					transTransferData.setNameAndValue("LiveGetMode", "");//默认为空
					if ("".equals(StrTool.cTrim(tBonusGetMode))) {
						transTransferData.removeByName("BonusGetMode");
						transTransferData.setNameAndValue("BonusGetMode", "1");
					}
				}

				//长乐   //年年红两全112215 added by ln 2008-10-28
				if ((tRiskCode.equals("112204"))
						|| (tRiskCode.equals("112215"))) {
					transTransferData.removeByName("LiveGetMode");
					transTransferData.setNameAndValue("LiveGetMode", "");
				}
				
				//富贵盈门A  交费年期无论录入有、无内容，均按“空”转换，无需导入系统，无需给出问题件。 add by weikai
				//富贵盈门B  交费年期无论录入有、无内容，均按“空”转换，无需导入系统，无需给出问题件。 add by weikai 2008-01-09
				//modify by ln 2009-05-08 交费年期可以为“趸交”、“1年”或“空”，如判断不为以上几种时，需给出问题件
			      if(tRiskCode.equals("314301"))
			      {
			    	  if (tPayIntv != 0)
			    	  {
			    		  CError.buildErr(this, "富贵盈门A款交费方式必须为趸交！");
			    		  return false;
			    	  }
			    	  if (((tPayEndYear == 0 )||(tPayEndYear == 1000 && "A".equals(tPayEndYearFlag)) || (tPayEndYear == 1 && "Y"
								.equals(tPayEndYearFlag))) //交费期间“趸交”录入为“1年”
						)
			    	  {
			    		    transTransferData.removeByName("PayEndYear");
							transTransferData.removeByName("PayEndYearFlag");
							transTransferData.setNameAndValue("PayEndYear", String
									.valueOf(tInsuYear));
							 
							transTransferData.setNameAndValue("PayEndYearFlag",
									tInsuYearFlag);
							tPayEndYear = tInsuYear;
							tPayEndYearFlag = tInsuYearFlag;
			    	  }			    	  
			    	 /* //此险种必须是75A,直接设置为75A
			    	  transTransferData.removeByName("PayEndYear");
			    	  transTransferData.setNameAndValue("PayEndYear","75");
			    	  transTransferData.removeByName("PayEndYearFlag");
			    	  transTransferData.setNameAndValue("PayEndYearFlag","A");*/
			      } 
			      
			      if(tRiskCode.equals("314302"))
			      {
			    	  if (tPayIntv != 0)
			    	  {
			    		  CError.buildErr(this, "富贵盈门B款交费方式必须为趸交！");
			    		  return false;
			    	  }
			    	  if (((tPayEndYear == 0 )||(tPayEndYear == 1000 && "A".equals(tPayEndYearFlag)) || (tPayEndYear == 1 && "Y"
								.equals(tPayEndYearFlag))) //交费期间“趸交”录入为“1年”
						)
			    	  {
			    		    transTransferData.removeByName("PayEndYear");
							transTransferData.removeByName("PayEndYearFlag");
							transTransferData.setNameAndValue("PayEndYear", String
									.valueOf(tInsuYear));
							transTransferData.setNameAndValue("PayEndYearFlag",
									tInsuYearFlag);
							tPayEndYear = tInsuYear;
							tPayEndYearFlag = tInsuYearFlag;
			    	  }			    	  
			    	 /* //此险种必须是75A,直接设置为75A
			    	  transTransferData.removeByName("PayEndYear");
			    	  transTransferData.setNameAndValue("PayEndYear","75");
			    	  transTransferData.removeByName("PayEndYearFlag");
			    	  transTransferData.setNameAndValue("PayEndYearFlag","A");*/
			      }

				//如果投保单上没有续保选项，则默认是自动续保-后台判断：如果该险种可以续保，则不变；如不能续保，则改为-2不续保
				if (tRnewFlag == 0) {
					transTransferData.removeByName("RnewFlag");
					transTransferData.setNameAndValue("RnewFlag", "-1");
				}

				//久久同安 added by ln 2008-08-28
				 //附加提前给付重疾121508 added by ln 2009-3-23
			    if((tRiskCode.equals("121507"))
			    		  ||(tRiskCode.equals("121508")))
				{
					transTransferData.removeByName("LiveGetMode");
					transTransferData.setNameAndValue("LiveGetMode", "");
					transTransferData.removeByName("BonusGetMode");
					transTransferData.setNameAndValue("BonusGetMode", "");
				}
				//康顺个人意外伤害111601，121601 added by ln 2008-09-16
				if ((tRiskCode.equals("111601"))
						|| (tRiskCode.equals("121601"))) {
					transTransferData.removeByName("LiveGetMode");
					transTransferData.setNameAndValue("LiveGetMode", "");
					transTransferData.removeByName("BonusGetMode");
					transTransferData.setNameAndValue("BonusGetMode", "");
				}
				if (tRiskCode.equals("111301")) {
					transTransferData.removeByName("LiveGetMode");
					transTransferData.setNameAndValue("LiveGetMode", "");
				}

				//MS幸福360少儿两全保险（分红型）112219  add by lixiang 09-12-02
				if(tRiskCode.equals("112219")){
					if("".equals(tBonusGetMode)){
						transTransferData.removeByName("BonusGetMode");
						transTransferData.setNameAndValue("BonusGetMode", "1");
					}
				}
				//金玉良缘两全保险（分红型）
				if(tRiskCode.equals("312207")){
					if(tInsuYear==15){
						tTransferData.removeByName("GetDutyKind");
						tTransferData.setNameAndValue("GetDutyKind", "1");
					} else {
						tTransferData.removeByName("GetDutyKind");
						tTransferData.setNameAndValue("GetDutyKind", "2");
					}
				}
			} catch (Exception ex) {
				return false;
			}
		}
		return tFlag;
	}

	/**
	 * 校验错误信息
	 * @param transTransferData
	 * @param tTransferData
	 * @param tType
	 * @return
	 */
	private boolean checkRiskDetail(TransferData transTransferData,
			TransferData tTransferData, String tType) {
		boolean tFlag = true;
		//field RiskCode,PayEndYear,PayEndYearFlag,PayIntv,InsuYear,InsuYearFlag 
		String tRiskCode = transTransferData.getValueByName("RiskCode") == null ? ""
				: (String) transTransferData.getValueByName("RiskCode");
		int tPayEndYear = Integer.parseInt(transTransferData
				.getValueByName("PayEndYear") == null ? "0"
				: (String) transTransferData.getValueByName("PayEndYear"));
		String tPayEndYearFlag = transTransferData
				.getValueByName("PayEndYearFlag") == null ? ""
				: (String) transTransferData.getValueByName("PayEndYearFlag");
		int tPayIntv = Integer.parseInt(transTransferData
				.getValueByName("PayIntv") == null ? ""
				: (String) transTransferData.getValueByName("PayIntv"));
		int tInsuYear = Integer.parseInt(transTransferData
				.getValueByName("InsuYear") == null ? "0"
				: (String) transTransferData.getValueByName("InsuYear"));
		String tInsuYearFlag = transTransferData.getValueByName("InsuYearFlag") == null ? ""
				: (String) transTransferData.getValueByName("InsuYearFlag");
		String tLiveGetMode = transTransferData.getValueByName("LiveGetMode") == null ? ""
				: (String) transTransferData.getValueByName("LiveGetMode");
		String tBonusGetMode = transTransferData.getValueByName("BonusGetMode") == null ? ""
				: (String) transTransferData.getValueByName("BonusGetMode");
		String tPayYears = transTransferData.getValueByName("PayYears") == null ? ""
				: (String) transTransferData.getValueByName("PayYears");
		int tRnewFlag = Integer.parseInt(transTransferData
				.getValueByName("RnewFlag") == null ? "-2"
				: (String) transTransferData.getValueByName("RnewFlag"));
		//外包转换规则
		//if(tType.equals("WB"))
		{
			try {
			} catch (Exception ex) {
				return false;
			}
		}
		return tFlag;
	}
	
	/**
	 * 双岗告知信息转换
	 * */
	
	private boolean changeImpartDetailForJM(TransferData transTransferData,
			TransferData tTransferData, String tType) {
		String tImpartParamModle = transTransferData.getValueByName("ImpartParamModle") == null ? ""
				: (String) transTransferData.getValueByName("ImpartParamModle");
		String tPrtFlag = transTransferData.getValueByName("PrtFlag") == null ? ""
				: (String) transTransferData.getValueByName("PrtFlag");
		String tInsured1 = transTransferData.getValueByName("Insured1") == null ? ""
				: (String) transTransferData.getValueByName("Insured1");
		String tImpartCode = transTransferData.getValueByName("ImpartCode") == null ? ""
				: (String) transTransferData.getValueByName("ImpartCode");
		String tImpartVer = transTransferData.getValueByName("ImpartVer") == null ? ""
				: (String) transTransferData.getValueByName("ImpartVer");
		//健康告知
		if("A01".equals(tImpartVer)){
			//有无驾驶执照
			if("A0104".equals(tImpartCode)){
				//录入的具体内容中如有“无或没有”字时，华道后台将选择的“是”字转换为“否”
				if(tImpartParamModle!=null&&!"null".equals(tImpartParamModle)&&!"".equals(tImpartParamModle)){
					if(tImpartParamModle.indexOf("无")!=-1||tImpartParamModle.indexOf("没有")!=-1){
						if(tPrtFlag!=null&&!"null".equals(tPrtFlag)&&!"".equals(tPrtFlag)&&tPrtFlag.equals("0")){
							transTransferData.removeByName("PrtFlag");
							transTransferData.setNameAndValue("PrtFlag", "1");
						}
						if(tInsured1!=null&&!"null".equals(tInsured1)&&!"".equals(tInsured1)&&tInsured1.equals("0")){
							transTransferData.removeByName("Insured1");
							transTransferData.setNameAndValue("Insured1", "1");
						}
						transTransferData.removeByName("ImpartParamModle");
						transTransferData.setNameAndValue("ImpartParamModle", "");
					}
				}
			}
		}
		//保险及财务
		if("A02".equals(tImpartVer)){
			
		}
		//业务员
		if("A03".equals(tImpartVer)){
			
		}
		boolean tFlag =true;
		return tFlag;
		
	}
	/* 对tTransferData内部的字段进行转换 双岗录入使用
	 * @param tTransferData
	 * @param tType
	 * @return
	 */
	private boolean changeRiskDetailForJM(TransferData transTransferData,
			TransferData tTransferData, String tType) {
		boolean tFlag = true;
		// field
		// RiskCode,PayEndYear,PayEndYearFlag,PayIntv,InsuYear,InsuYearFlag
		String tRiskCode = (String) transTransferData
				.getValueByName("RiskCode");
		String tPayYears = transTransferData.getValueByName("PayYears") == null ? ""
				: (String) transTransferData.getValueByName("PayYears");
		String tRemark = transTransferData.getValueByName("Remark") == null ? ""
				: (String) transTransferData.getValueByName("Remark");
		String tPrem = transTransferData.getValueByName("Prem") == null? ""
				: (String) transTransferData.getValueByName("Prem");
		String tAmnt = transTransferData.getValueByName("Amnt") == null? ""
				: (String) transTransferData.getValueByName("Amnt");
		String tMult = transTransferData.getValueByName("Mult") == null? ""
				: (String) transTransferData.getValueByName("Mult");
		//if (tType.equals("JM")) 
		{
			try {
				//外包解析时会自动转换终生等字段..不需要修改,直接将tPayYears赋值给PayEndYear即可
				//	transTransferData.removeByName("PayEndYear");
				//	transTransferData.setNameAndValue("PayEndYear", tPayYears);

				//险种级别的转换开始了.....
				if (tRiskCode.equals("112203")) {
					if (tPayYears.equals("1000") || tPayYears.equals("一次交清")
							|| tPayYears.equals("趸交")) {
						tPayYears = "1000A";
					}
				} else if (tRiskCode.equals("112101")) {
					if (tPayYears.equals("1000") || tPayYears.equals("一次交清")
							|| tPayYears.equals("趸交")) {
						tPayYears = "1000A";
					}
				} else if (tRiskCode.equals("112202")) {
					if (tPayYears.equals("1000") || tPayYears.equals("一次交清")
							|| tPayYears.equals("趸交") || tRemark.equals("A计划")) {
						tPayYears = "1000A";
					}
				} else if (tRiskCode.equals("112201")) {
					if (tPayYears.equals("25") || tPayYears.equals("一次交清")
							|| tPayYears.equals("趸交")) {
						tPayYears = "25A";
					}
				} else if (tRiskCode.equals("111301")) {
					//if(tPayYears.equals("25")||tPayYears.equals("趸交"))
					{
						tPayYears = "70A";
					}
				} else if (tRiskCode.equals("112401")) {
					if (tPayYears.equals("1000") || tPayYears.equals("一次交清")
							|| tPayYears.equals("趸交")) {
						tPayYears = "88A";
					}
				} else if (tRiskCode.equals("112206")) {
					if (tPayYears.equals("25") || tPayYears.equals("一次交清")
							|| tPayYears.equals("趸交")) {
						tPayYears = "25A";
					}
				} else if (tRiskCode.equals("112207")) {
					if (tPayYears.equals("70") || tRemark.equals("A计划")) {
						tPayYears = "70A";
					}
					if (tPayYears.equals("60") || tRemark.equals("B计划")) {
						tPayYears = "60A";
					}
				} else if (tRiskCode.equals("111504")) {
					if (tPayYears.equals("105") || tPayYears.equals("一次交清")
							|| tPayYears.equals("趸交")) {
						tPayYears = "105A";
					}
				} else if (tRiskCode.equals("112208")) {
					if (tPayYears.equals("1000") || tPayYears.equals("一次交清")
							|| tPayYears.equals("趸交")) {
						tPayYears = "1000A";
					}
				} else if (tRiskCode.equals("112204")) {
					//if(tPayYears.equals("1000")||tPayYears.equals("一次交清"))
					{
						tPayYears = "A";
					}
				} else if (tRiskCode.equals("111601")
						|| tRiskCode.equals("111602")
						|| tRiskCode.equals("111603")
						|| tRiskCode.equals("121801")
						|| tRiskCode.equals("131602")
						|| tRiskCode.equals("121705")
						|| tRiskCode.equals("121704")) {
					if (tPayYears.equals("1年") || tPayYears.equals("一年")) {
						tPayYears = "1Y";
					}
				} else if (tRiskCode.equals("122202")) {
					if (tPayYears.equals("17") || tPayYears.equals("一次交清")
							|| tPayYears.equals("趸交")) {
						tPayYears = "17A";
					}
				} else if (tRiskCode.equals("122201")) {
					if (tPayYears.equals("14") || tPayYears.equals("一次交清")
							|| tPayYears.equals("趸交")) {
						tPayYears = "14A";
					}
				} else if (tRiskCode.equals("121501")) {
					if (tPayYears.equals("1000") || tPayYears.equals("一次交清")
							|| tPayYears.equals("趸交")) {
						tPayYears = "1000A";
					}
				}
				transTransferData.removeByName("PayEndYear");
				transTransferData.setNameAndValue("PayEndYear", tPayYears);
				
				//tongmeng 2009-03-11 add
				//增加险种转换规则
				if (tRiskCode.equals("111602"))
				{
					//录入为“计划一、计划二、计划三”或“计划1、计划2、计划3”时，后台校验如无“计划”两字给出问题件。
					//分别按对应的保额（计划一转换为30000、计划二转换为75000、计划三转换为160000）导入系统
					if(tAmnt.equals("计划一")||tAmnt.equals("计划二")||tAmnt.equals("计划三")
							||tAmnt.equals("计划1")||tAmnt.equals("计划2")||tAmnt.equals("计划3")
							)
					{
						if(tAmnt.indexOf("一")!=-1||tAmnt.indexOf("1")!=-1)
						{
							tAmnt = "30000";
						}
						else if(tAmnt.indexOf("二")!=-1||tAmnt.indexOf("2")!=-1)
						{
							tAmnt = "75000";
						}
						else if(tAmnt.indexOf("三")!=-1||tAmnt.indexOf("3")!=-1)
						{
							tAmnt = "160000";
						}
							
					}
				}
				
				
				//转换保费、保额、份数
				transTransferData.removeByName("Prem");
				transTransferData.setNameAndValue("Prem", tPrem.replaceAll("[^0-9]", ""));
				transTransferData.removeByName("Amnt");
				transTransferData.setNameAndValue("Amnt", tAmnt.replaceAll("[^0-9]", ""));
				transTransferData.removeByName("Mult");
				transTransferData.setNameAndValue("Mult", tMult.replaceAll("[^0-9]", ""));
			} catch (Exception ex) {

			}
		}

		return tFlag;
	}

	/**
	 * tongmeng 2009-02-26 add
	 * 外包校验集中处理
	 */
	/**
	 * 校验以及处理特殊险种
	 * @param tLCPolSchema
	 * @param tLAAgentSchema
	 * @param tTransferData
	 * @return
	 */
	public boolean checkSpecRisk(LCAppntSchema tLCAppntSchema, LCInsuredSet tLCInsuredSet, LCPolSchema tLCPolSchema,
			TransferData tTransferData,	RiskBasicInfo tRiskBasicInfo, int tFlag) {
		try {
			if (tLCPolSchema.getRiskCode().equals("112212")) {
				
				if(!(tLCPolSchema.getInsuYear() == 60 && "A"
						.equals(tLCPolSchema.getInsuYearFlag()))
						&&!(tLCPolSchema.getInsuYear() == 80 && "A"
								.equals(tLCPolSchema.getInsuYearFlag()))
				)
				{
					CError.buildErr(this,"富贵年年保险期间错误！");
					return false;
				}	
				
			}
			
			//如果是正常导入，则需要初始化一些信息以及做一些校验
			if (tFlag == 0) {				
				//
				if (!checkRiskPlan(tLCPolSchema, tTransferData, tRiskBasicInfo)) {
					return false;
				}

				//对停售的险种做特殊校验
				if (tLCPolSchema.getRiskCode().equals("121501"))
					//	|| tLCPolSchema.getRiskCode().equals("112208")) 
				{
					CError.buildErr(this,"此险种已停止销售，请撤单！");
					return false;
				}

				if (tLCPolSchema.getRiskCode().equals("312204")
						&& tLCPolSchema.getInsuYear() == 5
						&& "Y".equals(tLCPolSchema.getInsuYearFlag())) {
					CError.buildErr(this,"该险种5年期产品已停售，请撤单！");
					return false;
				}

				//对长顺做险种备注中的信息做特殊校验
				if (tLCPolSchema.getRiskCode().equals("112202")) {
					if (tRiskBasicInfo.getRemark() != null
							&& "A计划".equals(StrTool.cTrim(tRiskBasicInfo
									.getRemark()))) {
						if (!(tLCPolSchema.getInsuYear() == 1000
								&& "A".equals(tLCPolSchema.getInsuYearFlag()) && tLCPolSchema
								.getPayIntv() == 0)
								|| !(tLCPolSchema.getInsuYear() == 60
										&& "A".equals(tLCPolSchema
												.getInsuYearFlag()) && tLCPolSchema
										.getPayIntv() == 0)) {
							CError.buildErr(this,"长顺保险期间、交费期间、交费方式与A计划不符！");
							return false;
						}
					}

					if (tRiskBasicInfo.getRemark() != null
							&& "B计划".equals(StrTool.cTrim(tRiskBasicInfo
									.getRemark()))) {
						if (!(tLCPolSchema.getInsuYear() == 10
								&& "Y".equals(tLCPolSchema.getInsuYearFlag()) && tLCPolSchema
								.getPayIntv() > 0)
								|| !(tLCPolSchema.getInsuYear() == 20
										&& "Y".equals(tLCPolSchema
												.getInsuYearFlag()) && tLCPolSchema
										.getPayIntv() > 0)) {
							CError.buildErr(this,"长顺保险期间、交费期间、交费方式与B计划不符！");
							return false;
						}
					}
				}			

				//对富贵年年备注中的信息做特殊校验
				//		        if(tLCPolSchema.getRiskCode().equals("112212"))
				//		        {
				//		        	if(tRiskBasicInfo.getRemark() != null && "B计划".equals(StrTool.cTrim(tRiskBasicInfo.getRemark())))
				//		        	{
				//		        		if(!(tLCPolSchema.getInsuYear() == 60 && "A".equals(tLCPolSchema.getInsuYearFlag()) && tLCPolSchema.getPayIntv()==0 )
				//		        				||!(tLCPolSchema.getInsuYear() == 80 && "A".equals(tLCPolSchema.getInsuYearFlag()) && tLCPolSchema.getPayIntv()==0 )
				//		        				||!(tLCPolSchema.getInsuYear() == 88 && "A".equals(tLCPolSchema.getInsuYearFlag()) && tLCPolSchema.getPayIntv()==0))
				//		        		{
				//		        			CError tError = new CError();
				//		        			tError.moduleName = "BPODealInputDataBL";
				//		        			tError.functionName = "checkSpecRisk";
				//		        			tError.errorMessage = "长顺保险期间、交费期间、交费方式与A计划不符！";
				//		        			this.mErrors.addOneError(tError);
				//		        			return false;
				//		        		}
				//		        	}
				//		        	
				//		        	if(tRiskBasicInfo.getRemark() != null && "A计划".equals(StrTool.cTrim(tRiskBasicInfo.getRemark())))
				//		        	{
				//		        		if(!(tLCPolSchema.getInsuYear() == 20 && "Y".equals(tLCPolSchema.getInsuYearFlag()) && tLCPolSchema.getPayIntv()>0 )
				//		        				||!(tLCPolSchema.getInsuYear() == 30 && "Y".equals(tLCPolSchema.getInsuYearFlag()) && tLCPolSchema.getPayIntv()>0 ) 
				//		        				||!(tLCPolSchema.getInsuYear() == 40 && "Y".equals(tLCPolSchema.getInsuYearFlag()) && tLCPolSchema.getPayIntv()>0))
				//		        		{
				//		        			CError tError = new CError();
				//		        			tError.moduleName = "BPODealInputDataBL";
				//		        			tError.functionName = "checkSpecRisk";
				//		        			tError.errorMessage = "长顺保险期间、交费期间、交费方式与B计划不符！";
				//		        			this.mErrors.addOneError(tError);
				//		        			return false;
				//		        		}
				//		        	}
				//		        }

				//modified by liuning 2008.3.19
				if (tLCPolSchema.getRiskCode().equals("112212")) {							
							
					if (tRiskBasicInfo.getRemark() != null
							&& ("B计划".equals(StrTool.cTrim(tRiskBasicInfo
									.getRemark())) || "计划B".equals(StrTool
									.cTrim(tRiskBasicInfo.getRemark())))
							&& !(tLCPolSchema.getInsuYear() == 60 && "A"
									.equals(tLCPolSchema.getInsuYearFlag()))) {
						CError.buildErr(this,"富贵年年保险期间、交费期间、交费方式与B计划不符！");
						return false;
					}

					if (tRiskBasicInfo.getRemark() != null
							&& ("A计划".equals(StrTool.cTrim(tRiskBasicInfo
									.getRemark())) || "计划A".equals(StrTool
									.cTrim(tRiskBasicInfo.getRemark())))
							&& !(tLCPolSchema.getInsuYear() == 80 && "A"
									.equals(tLCPolSchema.getInsuYearFlag()))) {
						CError.buildErr(this,"富贵年年保险期间、交费期间、交费方式与A计划不符！");
						return false;
					}
				}

				//added by ln 2008.4.7
				if (tLCPolSchema.getRiskCode().equals("112213")) {
					if (tRiskBasicInfo.getRemark() != null
							&& ("A计划".equals(StrTool.cTrim(tRiskBasicInfo
									.getRemark())) || "计划A".equals(StrTool
									.cTrim(tRiskBasicInfo.getRemark())))) {
						if (!((tLCPolSchema.getInsuYear() == 30 || tLCPolSchema
								.getInsuYear() == 40) && "Y"
								.equals(tLCPolSchema.getInsuYearFlag()))) {
							CError.buildErr(this,"MS如意相伴两全保险（分红型）保险期间与A计划不符！");
							return false;
						}
					}

					if (tRiskBasicInfo.getRemark() != null
							&& ("B计划".equals(StrTool.cTrim(tRiskBasicInfo
									.getRemark())) || "计划B".equals(StrTool
									.cTrim(tRiskBasicInfo.getRemark())))) {
						if (!((tLCPolSchema.getInsuYear() == 60 || tLCPolSchema
								.getInsuYear() == 88) && "A"
								.equals(tLCPolSchema.getInsuYearFlag()))) {
							CError.buildErr(this,"MS如意相伴两全保险（分红型）保险期间与B计划不符！");
							return false;
						}
					}
				}
				
				//added by liuning 2009.5.26
		        if(tLCPolSchema.getRiskCode().equals("112216"))
		        {
		        	if(tRiskBasicInfo.getRemark() != null && ("A计划".equals(StrTool.cTrim(tRiskBasicInfo.getRemark()))||"计划A".equals(StrTool.cTrim(tRiskBasicInfo.getRemark())))
		        	&& !((tLCPolSchema.getInsuYear() == 30 || tLCPolSchema.getInsuYear() == 40) && "Y".equals(tLCPolSchema.getInsuYearFlag())))       				
		        		{
		        			CError.buildErr(this, "MS如意相伴B款两全保险（分红型）保险期间与A计划不符！") ;
		        			return false;
		        		}   
		        	
		        	if(tRiskBasicInfo.getRemark() != null && ("B计划".equals(StrTool.cTrim(tRiskBasicInfo.getRemark()))||"计划B".equals(StrTool.cTrim(tRiskBasicInfo.getRemark())))
		            && !((tLCPolSchema.getInsuYear() == 60 || tLCPolSchema.getInsuYear() == 88) && "A".equals(tLCPolSchema.getInsuYearFlag())))       				
		                {
		                	CError.buildErr(this, "MS如意相伴B款两全保险（分红型）保险期间与B计划不符！") ;
		                	return false;
		                }
		        }

				//added by ln 2008.3.26
				if (tLCPolSchema.getRiskCode().equals("121305")) {
					if (tRiskBasicInfo.getRemark() != null
							&& ("A计划".equals(StrTool.cTrim(tRiskBasicInfo
									.getRemark())) || "计划A".equals(StrTool
									.cTrim(tRiskBasicInfo.getRemark())))) {
						if (!((tLCPolSchema.getInsuYear() == 20
								|| tLCPolSchema.getInsuYear() == 30 || tLCPolSchema
								.getInsuYear() == 40) && "Y"
								.equals(tLCPolSchema.getInsuYearFlag()))) {
							CError.buildErr(this,"MS附加定期寿险保险期间与A计划不符！");
							return false;
						}
					}

					if (tRiskBasicInfo.getRemark() != null
							&& ("B计划".equals(StrTool.cTrim(tRiskBasicInfo
									.getRemark())) || "计划B".equals(StrTool
									.cTrim(tRiskBasicInfo.getRemark())))) {
						if (!((tLCPolSchema.getInsuYear() == 60
								|| tLCPolSchema.getInsuYear() == 80 || tLCPolSchema
								.getInsuYear() == 88) && "A"
								.equals(tLCPolSchema.getInsuYearFlag()))) {
							CError.buildErr(this,"MS附加定期寿险保险期间与B计划不符！");
							return false;
						}
					}
				}
				if (tLCPolSchema.getRiskCode().equals("121505")) {
					if (tRiskBasicInfo.getRemark() != null
							&& ("A计划".equals(StrTool.cTrim(tRiskBasicInfo
									.getRemark())) || "计划A".equals(StrTool
									.cTrim(tRiskBasicInfo.getRemark())))) {
						if (!((tLCPolSchema.getInsuYear() == 20
								|| tLCPolSchema.getInsuYear() == 30 || tLCPolSchema
								.getInsuYear() == 40) && "Y"
								.equals(tLCPolSchema.getInsuYearFlag()))) {
							CError.buildErr(this,"MS附加额外给付重疾保险期间与A计划不符！");
							return false;
						}
					}

					if (tRiskBasicInfo.getRemark() != null
							&& ("B计划".equals(StrTool.cTrim(tRiskBasicInfo
									.getRemark())) || "计划B".equals(StrTool
									.cTrim(tRiskBasicInfo.getRemark())))) {
						if (!((tLCPolSchema.getInsuYear() == 60
								|| tLCPolSchema.getInsuYear() == 80 || tLCPolSchema
								.getInsuYear() == 88) && "A"
								.equals(tLCPolSchema.getInsuYearFlag()))) {
							CError.buildErr(this,"MS附加额外给付重疾保险期间与B计划不符！");
							return false;
						}
					}
				}
				/////end added by ln
				
				//查询系统定义校验时间
				//2010-4-27modify 应业管要求，将该校验日期改为按投保申请日期校验
				ExeSQL tExeSQL =new ExeSQL();
//				 String tSQL = "select sysvarvalue from ldsysvar where sysvar ='tbcheckscandate'";
				String tSQL = "select sysvarvalue from ldsysvar where sysvar ='ServiceSpecification'";
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(tSQL);
		  		  String checkdate = tExeSQL.getOneValue(sqlbv);
		  		  if (checkdate == null || checkdate.equals("")) {
		  			  checkdate = "";
		  			  logger.debug("系统没有定义校验时间（tbcheckscandate）");
		  		  }
		  		
				String tScanDate = tLCPolSchema.getPolApplyDate();//投保申请日期
				if (tScanDate == null || "".equals(tScanDate)) {
					tScanDate = "";
				}
			    if(!checkdate.equals("")&&!tScanDate.equals("")&&PubFun.checkDate(checkdate, tScanDate))//投保申请日期大于等于开始校验日期		    			  
			    {
			    	//modify by ln 2009-2-6 只在导入时进行校验打到异常件处理
					//20100305校验分红险种万能险种--是否有新型产品说明书
					String sql = "select count(1) from lmriskapp where RiskType3 in ('2','4') and riskcode='"
							+ "?riskcode?" + "'";
					SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
					sqlbv1.sql(sql);
					sqlbv1.put("riskcode",tLCPolSchema.getRiskCode());
					String r = new ExeSQL().getOneValue(sqlbv1);
					String sql1 = "select count(1) from es_doc_main where doccode='"
							+ "?doccode?" + "' and subtype='UR211'";
					SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
					sqlbv2.sql(sql1);
					sqlbv2.put("doccode",tLCPolSchema.getPrtNo());
					String r1 = new ExeSQL().getOneValue(sqlbv2);
					if (!"0".equals(r) && r1.equals("0")) {
						CError.buildErr(this, "存在分红型险种或万能型险种，无《新型产品说明书》");
						return false;
					}
					
					// ln add 20100305 校验人身保险投保提示
					sql1 = "select count(1) from es_doc_main where doccode='"
							+ "?doccode1?" + "' and subtype='UR200'";
					SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
					sqlbv3.sql(sql1);
					sqlbv3.put("doccode1",tLCPolSchema.getPrtNo());
					r1 = new ExeSQL().getOneValue(sqlbv3);
					if("Y".equals(tLCPolSchema.getInsuYearFlag()))
					{
						//校验                  
		                if(r1.equals("0")&&(1<tLCPolSchema.getInsuYear()))
		                {   
			                //出现错误
			              	CError.buildErr(this, "投保资料明细提供不齐全，无《人身保险投保提示》") ;
			                return false;
		                }
					}
					else if("A".equals(tLCPolSchema.getInsuYearFlag()))
					{
						if(tLCInsuredSet != null && tLCInsuredSet.size() >0 )
				        {
				      	
				          for(int k=1;k<=tLCInsuredSet.size();k++)
				          {
					         LCInsuredSchema tLCInsuredSchema = tLCInsuredSet.get(k);			           

					        	//added by ln 2008.5.15
					             String tBirthday = "";
					             tBirthday = tLCInsuredSchema.getBirthday();
					             if(tLCInsuredSchema.getBirthday()==null || "".equals(tLCInsuredSchema.getBirthday()))
					             {
					            	 tBirthday = tLCAppntSchema.getAppntBirthday() ;
					             }
					            //end added 2008.5.15			            
					            tSQL = "select get_age('"+ "?get?" + "','"+"?age?"+"')  from dual ";
					            SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
								sqlbv4.sql(tSQL);
								sqlbv4.put("get", tBirthday);
								sqlbv4.put("age",tLCPolSchema.getPolApplyDate());
					            logger.debug("tSQL:"+tSQL);
					                 String tReturn="0";
					                 tReturn=tExeSQL.getOneValue(sqlbv4);
					               //计算结果
					                if(tReturn==null||tReturn.equals(""))
					                {              
					                    //出现错误
					              	    CError.buildErr(this, "计算被保人投保年龄出错") ;
					                    return false;
					                }
					                
					                //校验                  
					                if(r1.equals("0")&&(Integer.parseInt(tReturn)+1)<tLCPolSchema.getInsuYear())
					                {   
						                //出现错误
						              	CError.buildErr(this, "投保资料明细提供不齐全，无《人身保险投保提示》") ;
						                return false;
					                }
				           }
				        }
					}					
			    }
		    		  
		    }
				
			
			//common common common common common common common common common common common
			
			// ln 2008-10-15 add 一年期短险交费方式必须为趸交         
	        ExeSQL txExeSQL = new ExeSQL();
	        SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
			sqlbv5.sql("select RiskPeriod from LMRiskApp where RiskCode='"+"?RiskCode?"+"'");
			sqlbv5.put("RiskCode", tLCPolSchema.getRiskCode());
	        String txRiskPeriod = txExeSQL.getOneValue(sqlbv5);
	        if("M".equals(txRiskPeriod) && tLCPolSchema.getPayIntv()!=0)  
	        {
	        	CError.buildErr(this, "交费方式有误！");
	        	return false;        	       	
	        }
			
	      //处理被保险人与险种信息
	        logger.debug("Start checkBirthday...");  
	        if(tLCInsuredSet != null && tLCInsuredSet.size() >0)
	        {
	      	
	          for(int k=1;k<=tLCInsuredSet.size();k++)
	          {
		         LCInsuredSchema tLCInsuredSchema = tLCInsuredSet.get(k);        
		           
		          //**************被保险人选择交费年期校验 ln 2008.5.5
		         if( tLCPolSchema.getRiskCode().equals("112201")
		          	         || tLCPolSchema.getRiskCode().equals("112202")
		          	         || tLCPolSchema.getRiskCode().equals("112203")
		          	         || tLCPolSchema.getRiskCode().equals("112101"))
		          {
		        	//added by ln 2008.5.15
		             String tBirthday = "";
		             tBirthday = tLCInsuredSchema.getBirthday();
		             if(tLCInsuredSchema.getBirthday()==null || "".equals(tLCInsuredSchema.getBirthday()))
		             {
		            	 tBirthday = tLCAppntSchema.getAppntBirthday() ;
		             }
		            //end added 2008.5.15
		            //select 1 from dual where (get_age('?birthday?','?polapplydate?')+'?PayEndYear?')<=75 and '?PayEndYearFlag?'='Y'
		            String tSQL = "select get_age('"+ "?a1?" + "','"+"?a2?"+"')  from dual ";
		            SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
                    sqlbv6.sql(tSQL);
                    sqlbv6.put("a1",tBirthday);
                    sqlbv6.put("a2",tLCPolSchema.getPolApplyDate());

		            logger.debug("tSQL:"+tSQL);
		                 String tReturn="0";
		                 ExeSQL tExeSQL=new ExeSQL();
		                 tReturn=tExeSQL.getOneValue(sqlbv6);
		               //计算结果
		                if(tReturn==null||tReturn.equals(""))
		                {              
		                    //出现错误
		              	    CError.buildErr(this, "计算被保人投保年龄出错") ;
		                    return false;
		                 }
		                
		                //校验                  
		                if((Integer.parseInt(tReturn)+tLCPolSchema.getPayEndYear())>75&&(tLCPolSchema.getPayEndYearFlag().equals("Y"))&&tLCPolSchema.getPayIntv()==12)
		                {              
		                    //出现错误
		              	    CError.buildErr(this, "被保人投保年龄不能选择此交费年期") ;
		                    return false;
		                 }
		            
	            //**********end added by ln 2008.5.5
	           }
		         //2009-5-26 ln add
		         if( tLCPolSchema.getRiskCode().equals("121509")
		      	         || tLCPolSchema.getRiskCode().equals("121510"))
			      {
			    	//added by ln 2009.5.8 对被保人性别进行校验
			         String tSex = "";
			         tSex = tLCInsuredSchema.getSex();
			         if(tLCInsuredSchema.getSex()==null || "".equals(tLCInsuredSchema.getSex()))
			         {
			        	 tSex = tLCAppntSchema.getAppntSex() ;
			         }
			        
		            if(tLCPolSchema.getRiskCode().equals("121509") && !tSex.equals("0") )
		            {              
		                //出现错误
		          	    CError.buildErr(this, "被保险人性别有误!") ;
		                return false;
		            }
		            
		            if(tLCPolSchema.getRiskCode().equals("121510") && !tSex.equals("1") )
		            {              
		                //出现错误
		          	    CError.buildErr(this, "被保险人性别有误!") ;
		                return false;
		            }
			      }
	         }
	        }
	        
			//附加豁免特殊处理
			if(tLCPolSchema.getRiskCode().equals("121301"))
		      {
				//特殊处理交费期间
	            String tSQL="select PayEndYear,PayEndYearFlag,InsuredAppAge from LCPol a where PrtNo = '"+"?PrtNo?"+"' "
	                    + " and PolNo=MainPolNo and exists(select 1 from lcinsured where contno=a.contno and sequenceno is null or sequenceno in ('-1','1'))";
	            SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
                sqlbv7.sql(tSQL);
                sqlbv7.put("PrtNo",tLCPolSchema.getPrtNo());
	            ExeSQL tExeSQL = new ExeSQL();
	            SSRS tSSRS =  tExeSQL.execSQL(sqlbv7);
	            if(tSSRS == null || tSSRS.getMaxRow()==0)
	            {
	              CError.buildErr(this, "主险保单查询失败") ;
	              return false;
	            }

	            int tPayEndYear =0;
	            if("A".equals(tSSRS.GetText(1,2)))
	            {
	              //PayEndYear-InsuredAppAge
	              tPayEndYear = Integer.parseInt(tSSRS.GetText(1,1)) - Integer.parseInt(tSSRS.GetText(1,3));
	            }
	            else
	            {
	              tPayEndYear = Integer.parseInt(tSSRS.GetText(1,1));
	            }

	            //校验主附险的交费年期，保险年期等是否一致
	            if(tLCPolSchema.getPayEndYear()!=tPayEndYear)
	            {
	              CError.buildErr(this, "附加豁免与主险的交费期间不一致") ;
	              return false;
	            }

	            if(tLCPolSchema.getInsuYear()!=tPayEndYear)
	            {
	              CError.buildErr(this, "附加豁免保险期间与主险的交费期间不一致") ;
	              return false;
	            }

	            if(!"Y".equals(tLCPolSchema.getPayEndYearFlag()))
	            {
	              CError.buildErr(this, "附加豁免交费年期/年龄标志错误（应该为“年”）！") ;
	              return false;
	            }

	            if(!"Y".equals(tLCPolSchema.getInsuYearFlag()))
	            {
	              CError.buildErr(this, "附加豁免保险年期/年龄标志错误（应该为“年”）！") ;
	              return false;
	            }
		      }	
			
			//处理富贵盈门A款   	
		      if(tLCPolSchema.getRiskCode().equals("314301"))
		      {
		      	//校验份数一档，如录入有内容，给出问题件。
		      	if((tRiskBasicInfo.getStrMult()!=null && !"".equals(StrTool.cTrim(tRiskBasicInfo.getStrMult())))||(tRiskBasicInfo.getMult()!=0))
		      	{
		      		CError.buildErr(this, "富贵盈门A款投保份数必须为空！") ;
		            return false;
		      	}
		      	if(tRiskBasicInfo.getAmnt()!=0)
		      	{
		      		if(Math.abs(tRiskBasicInfo.getAmnt()-tRiskBasicInfo.getPrem())>0.001)
		      		{
		      			CError.buildErr(this, "富贵盈门A款录入保额与保费不相等！") ;
		                return false;
		      		}
		      	}
		      	if(!"75A".equals((tLCPolSchema.getInsuYear()+tLCPolSchema.getInsuYearFlag())))
		      	{
		      		CError.buildErr(this, "富贵盈门A款录入的保险期间必须填写75岁！") ;
		            return false;
		      	}
		      	
		      	if(!"75A".equals((tLCPolSchema.getPayEndYear()+tLCPolSchema.getPayEndYearFlag())))		      	
		    	{
			        CError.buildErr(this, "富贵盈门A款交费年期不为“趸交”、“1年”或“空”！") ;
			        return false;
			    }
//		      	if(tRiskBasicInfo.getStrInsuYear()!=null&&tRiskBasicInfo.getStrInsuYear().indexOf("至")==-1)
//		      	{
//		      		CError.buildErr(this, "富贵盈门A款录入的保险期间必须填写至75岁！") ;
//		            return false;
//		      	}
		      }	      
		      
		      //处理富贵盈门A款   	
		      if(tLCPolSchema.getRiskCode().equals("314302"))
		      {
		      	//校验份数一档，如录入有内容，给出问题件。
		      	if((tRiskBasicInfo.getStrMult()!=null && !"".equals(StrTool.cTrim(tRiskBasicInfo.getStrMult())))||(tRiskBasicInfo.getMult()!=0))
		      	{
		      		CError.buildErr(this, "富贵盈门B款投保份数必须为空！") ;
		            return false;
		      	}
		      	if(tRiskBasicInfo.getAmnt()!=0)
		      	{
		      		if(Math.abs(tRiskBasicInfo.getAmnt()-tRiskBasicInfo.getPrem())>0.001)
		      		{
		      			CError.buildErr(this, "富贵盈门B款录入保额与保费不相等！") ;
		                return false;
		      		}
		      	}
		      	if(!"75A".equals((tLCPolSchema.getInsuYear()+tLCPolSchema.getInsuYearFlag())))
		      	{
		      		CError.buildErr(this, "富贵盈门B款录入的保险期间必须填写75岁！") ;
		            return false;
		      	}
		      	
		      	if(!"75A".equals((tLCPolSchema.getPayEndYear()+tLCPolSchema.getPayEndYearFlag())))		      	
		    	{
			        CError.buildErr(this, "富贵盈门B款交费年期不为“趸交”、“1年”或“空”！") ;
			        return false;
			    }
		      	
//		      	if(tRiskBasicInfo.getStrInsuYear()!=null&&tRiskBasicInfo.getStrInsuYear().indexOf("至")==-1)
//		      	{
//		      		CError.buildErr(this, "富贵盈门B款录入的保险期间必须填写至75岁！") ;
//		            return false;
//		      	}
		      }

			//校验附加豁免保险费重大疾病保险2008-06-16 added by ln
			if (tLCPolSchema.getRiskCode().equals("121506")) {
				//校验份数一档，如录入有内容，给出问题件。
				if ((tRiskBasicInfo.getStrMult() != null && !"".equals(StrTool
						.cTrim(tRiskBasicInfo.getStrMult())))
						|| (tRiskBasicInfo.getMult() != 0)) {
					CError.buildErr(this,"附加豁免保险费重大疾病保险的保额有误!");
					return false;
				}
			}			

			//校验久久系列险种
			if (tLCPolSchema.getRiskCode().equals("112211")
					|| tLCPolSchema.getRiskCode().equals("121504")
					|| tLCPolSchema.getRiskCode().equals("121602")
					|| tLCPolSchema.getRiskCode().equals("121303")) {
				//校验份数一档，如录入有内容，给出问题件。
				if ((tRiskBasicInfo.getStrMult() != null && !"".equals(StrTool
						.cTrim(tRiskBasicInfo.getStrMult())))
						|| (tRiskBasicInfo.getMult() != 0)) {
					CError.buildErr(this,"久久系列险种不能录入份数!");
					return false;
				}
			}

			//校验富贵年年系列险种
			if (tLCPolSchema.getRiskCode().equals("112212")) {
				//校验份数一档，如录入有内容，给出问题件。
				if ((tRiskBasicInfo.getStrMult() != null && !"".equals(StrTool
						.cTrim(tRiskBasicInfo.getStrMult())))
						|| (tRiskBasicInfo.getMult() != 0)) {
					CError.buildErr(this,"富贵年年系列险种不能录入份数!");
					return false;
				}
			}

			//校验久久至尊终身寿险 added by ln 2008-05-07
			if (tLCPolSchema.getRiskCode().equals("112214")) {
				//校验份数一档，如录入有内容，给出问题件。
				if ((tRiskBasicInfo.getStrMult() != null && !"".equals(StrTool
						.cTrim(tRiskBasicInfo.getStrMult())))
						|| (tRiskBasicInfo.getMult() != 0)) {
					CError.buildErr(this,"久久同盛终身寿险不能录入份数!");
					return false;
				}
			}

			//校验MS长悦两全险种2008.4.7
			if (tLCPolSchema.getRiskCode().equals("112213")) {
				//校验份数一档，如录入有内容，给出问题件。
				if ((tRiskBasicInfo.getStrMult() != null && !"".equals(StrTool
						.cTrim(tRiskBasicInfo.getStrMult())))
						|| (tRiskBasicInfo.getMult() != 0)) {
					CError.buildErr(this,"MS如意相伴两全保险（分红型）系列险种不能录入份数!");
					return false;
				}
			}
			
			if(tLCPolSchema.getRiskCode().equals("112216"))
	        {
	      	  //校验份数一档，如录入有内容，给出问题件。
	      	  if((tRiskBasicInfo.getStrMult()!=null && !"".equals(StrTool.cTrim(tRiskBasicInfo.getStrMult())))||(tRiskBasicInfo.getMult()!=0))
	      	  {
	      		  CError.buildErr(this, "MS如意相伴B款两全保险（分红型）险种不能录入份数!") ;
	      		  return false;
	      	  }     	 
	        }

			//校验金镶玉 added by ln 2008-09-08
			if (tLCPolSchema.getRiskCode().equals("312205")) {
				//校验份数不能为空。
				if ((tRiskBasicInfo.getStrMult() == null || "".equals(StrTool
						.cTrim(tRiskBasicInfo.getStrMult())))
						&& (tRiskBasicInfo.getMult() == 0)) {
					CError.buildErr(this, "份数不能为空!");
					return false;
				}
				//校验保费不能为空。
				if (tRiskBasicInfo.getPrem() == 0) {
					CError.buildErr(this, "保费不能为空!");
					return false;
				}
			}

			//校验久久同安 added by ln 2008-08-28
			if (tLCPolSchema.getRiskCode().equals("121507")) {
				//校验份数一档，如录入有内容，给出问题件。
				if ((tRiskBasicInfo.getStrMult() != null && !"".equals(StrTool
						.cTrim(tRiskBasicInfo.getStrMult())))
						|| (tRiskBasicInfo.getMult() != 0)) {
					CError.buildErr(this, "MS附加久久同安重大疾病保险不能录入份数!");
					return false;
				}
			}

			//校验年年红两全 added by ln 2008-10-28
			if (tLCPolSchema.getRiskCode().equals("112215")) {
				//校验份数一档，如录入有内容，给出问题件。
				if ((tRiskBasicInfo.getStrMult() != null && !"".equals(StrTool
						.cTrim(tRiskBasicInfo.getStrMult())))
						|| (tRiskBasicInfo.getMult() != 0)) {
					CError.buildErr(this, "MS年年红两全保险不能录入份数!");
					return false;
				}
			}
			
			 //校验长泰两全 added by ln 2009-3-23
	        if(tLCPolSchema.getRiskCode().equals("112208"))
	        {
	      	  //校验份数一档，如录入有内容，给出问题件。
	        	if((tRiskBasicInfo.getStrMult()!=null && !"".equals(StrTool.cTrim(tRiskBasicInfo.getStrMult())))||(tRiskBasicInfo.getMult()!=0))
	        	{
	      		  CError.buildErr(this, "MS长泰两全保险不能录入份数!");    		  
	      		  return false;
	      	    }     	 
	        }

			//校验外包录入的信息
			logger.debug("Start BPODealInputDataBL CheckTBField ...");
			LCInsuredDB tLCInsuredDB=new LCInsuredDB();
			tLCInsuredDB.setContNo(tLCPolSchema.getContNo());
			tLCInsuredDB.setInsuredNo(tLCPolSchema.getInsuredNo());
			for(int k=1;k<=tLCInsuredSet.size();k++)
			{
				if(tLCInsuredSet.get(k).equals(tLCPolSchema.getInsuredNo()))
				{
					tLCInsuredSchema=tLCInsuredSet.get(k);
					break;
				}
			}
			if (!CheckTBField(tLCPolSchema, tTransferData, "INSERT")) {
				return false;
			}
			logger.debug("End BPODealInputDataBL CheckTBField ...");

			//校验保单的自动垫交标志
			if (tLCPolSchema.getRiskCode().equals("112203")
					|| tLCPolSchema.getRiskCode().equals("112101")
					|| tLCPolSchema.getRiskCode().equals("112202")
					|| tLCPolSchema.getRiskCode().equals("112204")
					|| tLCPolSchema.getRiskCode().equals("112205")
					|| tLCPolSchema.getRiskCode().equals("112201")
					|| tLCPolSchema.getRiskCode().equals("111301")
					|| tLCPolSchema.getRiskCode().equals("111501")
					|| tLCPolSchema.getRiskCode().equals("111502")
					|| tLCPolSchema.getRiskCode().equals("111503")
					|| tLCPolSchema.getRiskCode().equals("112401")
					|| tLCPolSchema.getRiskCode().equals("112206")
					|| tLCPolSchema.getRiskCode().equals("112207")) {

				if (tLCPolSchema.getAutoPayFlag() == null
						|| "".equals(tLCPolSchema.getAutoPayFlag())) {
					CError.buildErr(this,"外包方传入的自动垫交标志为空");
					return false;
				}
				if (!"0".equals(tLCPolSchema.getAutoPayFlag())
						&& !"1".equals(tLCPolSchema.getAutoPayFlag())) {
					CError.buildErr(this,"外包方传入的自动垫交标志值错误");
					return false;
				}
			}
			
			//对与银代的险种，代理机构不应为综合银行， 临时校验，modify by fuqx 2007-02-15 14:21

			if (("312202".equals(tLCPolSchema.getRiskCode()) || "312203"
					.equals(tLCPolSchema.getRiskCode()))
					&& tLCPolSchema.getAgentCom() != null
					&& !"".equals(tLCPolSchema.getAgentCom())) {
				if (tLCPolSchema.getAgentCom().substring(0, 2) == "44") {
					CError.buildErr(this,"此代理机构为直销机构，请撤单！");
					return false;
				}
			}

			//如意年年
			if (tLCPolSchema.getRiskCode().equals("112207")) {
				if (tLCPolSchema.getPrem() > 0
						&& (int) tLCPolSchema.getPrem() % 1000 != 0) {
					CError.buildErr(this,"保费不为1000元整数倍");
					return false;
				}
			}

			//附加住院医疗
			if (tLCPolSchema.getRiskCode().equals("121705")) {
				if (tLCPolSchema.getAmnt() > 0
						&& (int) tLCPolSchema.getAmnt() < 5000) {
					CError.buildErr(this,"附加住院医疗保额保额低于5000");
					return false;
				}
				if (tLCPolSchema.getAmnt() > 0
						&& (int) tLCPolSchema.getAmnt() % 1000 != 0) {
					CError.buildErr(this,"附加住院医疗保额不为1000元整数倍");
					return false;
				}
			}
			
			//附加住院医疗
			if (tLCPolSchema.getRiskCode().equals("112401")) {
				int tGetYear = tLCPolSchema.getGetYear();
				String tGetYears = (String)tTransferData.getValueByName("GetYears");
				if(tGetYear == 0 || tGetYears == null || tGetYears.equals(""))
				{
					CError.buildErr(this,"险种112401的开始领取年龄或领取期间都不能为空！");
					return false;
				}
				int tGetYears1 = Integer.parseInt(tGetYears);
				int sInsuYear = tLCPolSchema.getInsuYear();
				if( (tGetYear + tGetYears1 - sInsuYear) != 0 )
				{
					CError.buildErr(this,"险种112401的开始领取年龄、领取期间与保险期间不一致！");
					return false;
				}
				
			}
			//add by lixiang 09-10-21
			if(tLCPolSchema.getRiskCode().equals("112217")){
				String tInsuYearStr = tLCPolSchema.getInsuYear()+tLCPolSchema.getInsuYearFlag();
				if(!"30Y".equals(tInsuYearStr)&&!"40Y".equals(tInsuYearStr)&&!"60A".equals(tInsuYearStr)) {
		      		CError.buildErr(this, "富贵双盈A款保险期间不为30年、40年、至60周岁中的一种！") ;
		            return false;
		      	}
		      	String tPayEndYearStr = tLCPolSchema.getPayEndYear()+tLCPolSchema.getPayEndYearFlag();
		      	if(!"0".equals(String.valueOf(tLCPolSchema.getPayIntv()))&&!"5Y".equals(tPayEndYearStr)
		      			&&!"10Y".equals(tPayEndYearStr)) {
			        CError.buildErr(this, "富贵双盈A款缴费年期不为一次交清、5年、10年中的一种！") ;
			        return false;
			    }
		      	if((tRiskBasicInfo.getStrMult()!=null 
		      			&& !"".equals(StrTool.cTrim(tRiskBasicInfo.getStrMult())))
		      			||(tRiskBasicInfo.getMult()!=0)) {
		      		CError.buildErr(this, "富贵双盈A款投保份数必须为空！") ;
		            return false;
		      	}
			}
			if(tLCPolSchema.getRiskCode().equals("112218")){
				String tInsuYearStr = tLCPolSchema.getInsuYear()+tLCPolSchema.getInsuYearFlag();
				if(!"30Y".equals(tInsuYearStr)&&!"40Y".equals(tInsuYearStr)&&!"60A".equals(tInsuYearStr)) {
		      		CError.buildErr(this, "富贵双盈B款保险期间不为30年、40年、至60周岁中的一种！") ;
		            return false;
		      	}
				String tPayEndYearStr = tLCPolSchema.getPayEndYear()+tLCPolSchema.getPayEndYearFlag();
				if(!"15Y".equals(tPayEndYearStr)&&!"20Y".equals(tPayEndYearStr)) {
					CError.buildErr(this, "富贵双盈B款缴费年期不为15年或20年中的一种！") ;
					return false;
				}
				if((tRiskBasicInfo.getStrMult()!=null 
		      			&& !"".equals(StrTool.cTrim(tRiskBasicInfo.getStrMult())))
		      			||(tRiskBasicInfo.getMult()!=0)) {
		      		CError.buildErr(this, "富贵双盈B款投保份数必须为空！") ;
		            return false;
		      	}
			}
			//MS幸福360少儿两全保险（分红型） add by lixiang 09-12-02
			if(tLCPolSchema.getRiskCode().equals("112219")){
				if("4".equals(tLCPolSchema.getBonusGetMode())){
					CError.buildErr(this, "红利领取方式为其他！");
					return false;
				}
				if(tLCPolSchema.getBonusGetMode()!=null&&!"".equals(tLCPolSchema.getBonusGetMode())&&!"1".equals(tLCPolSchema.getBonusGetMode())
						&&!"2".equals(tLCPolSchema.getBonusGetMode())&&!"3".equals(tLCPolSchema.getBonusGetMode())
						&&!"4".equals(tLCPolSchema.getBonusGetMode())&&!"5".equals(tLCPolSchema.getBonusGetMode())){
							CError.buildErr(this, "红利领取方式为多选！");
							return false;
						}
			}
			//312207 MS金玉良缘两全保险（分红型）
			if(tLCPolSchema.getRiskCode().equals("312207")){
				String tInsuYearStr = tLCPolSchema.getInsuYear()+tLCPolSchema.getInsuYearFlag();
				if(!"15Y".equals(tInsuYearStr)&&!"20Y".equals(tInsuYearStr)) {
		      		CError.buildErr(this, "金玉良缘保险期间必须为15年或20年中的一种！") ;
		            return false;
		      	}
				String tPayEndYearStr = tLCPolSchema.getPayEndYear()+tLCPolSchema.getPayEndYearFlag();
				if(!"15Y".equals(tPayEndYearStr)&&!"20Y".equals(tPayEndYearStr)) {
					CError.buildErr(this, "金玉良缘缴费年期必须为一次交清(1000A)、1年中的一种！") ;
					return false;
				}
			}
			
		} catch (Exception ex) {
			CError.buildErr(this,ex.toString());
			return false;
		}
		return true;
	}
	
	/**
	 * 校验以及处理附加险相关特殊规则
	 * @param tLCPolSchema
	 * @param tLAAgentSchema
	 * @param tTransferData
	 * @return
	 */
	public boolean checkSubSpecRisk(LCAppntSchema tLCAppntSchema, LCInsuredSet tLCInsuredSet, LCPolSchema tLCPolSchema,
			LCPolSchema tMainLCPolSchema, TransferData tTransferData,	RiskBasicInfo tRiskBasicInfo, int tFlag) {
		try {			
			//如果是正常导入，则需要初始化一些信息以及做一些校验
			if (tFlag == 0) {			

			}		
			
			//common common common common common common common common common common common	
////////////////////////////////added by ln 2008.4.10
			//initial
			if(tLCPolSchema.getRiskCode().equals("122201") || tLCPolSchema.getRiskCode().equals("122202"))
			{
				//tLCPolSchema.setLiveGetMode(tMainLCPolSchema.getLiveGetMode());
				tLCPolSchema.setLiveGetMode("1");//默认为1
				tLCPolSchema.setBonusGetMode(tMainLCPolSchema.getBonusGetMode());
			}
			
			  //check
			  if("121501".equals(tLCPolSchema.getRiskCode()))  //MS附加提前给付特殊处理
			  {          
					//特殊处理交费期间
					String tSQL="select PayEndYear,PayEndYearFlag,InsuYear,InsuYearFlag from LCPol where PolNo='"+"?a3?"+"'";
					SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
					sqlbv8.sql(tSQL);
					sqlbv8.put("a3", tMainLCPolSchema.getPolNo());
					ExeSQL tExeSQL = new ExeSQL();
					SSRS tSSRS =  tExeSQL.execSQL(sqlbv8);
					if(tSSRS == null || tSSRS.getMaxRow()==0)
					{
						CError.buildErr(this, "主险保单查询失败") ;
						return false;
					}
					
					int tPayEndYear = Integer.parseInt(tSSRS.GetText(1,1)) ;
					String tPayEndYearFlag = tSSRS.GetText(1,2);
					
					int tInsuYear = Integer.parseInt(tSSRS.GetText(1,3)) ;
					String tInsuYearFlag = tSSRS.GetText(1,4);           
					
					
					//校验主附险的交费年期，保险年期等是否一致
					if(!((tLCPolSchema.getPayEndYear()==tPayEndYear)&&(tLCPolSchema.getPayEndYearFlag().equals(tPayEndYearFlag))))
					{
						CError.buildErr(this, "MS附加提前给付的交费年期与主险的交费年期不一致") ;
						return false;
					}
					
					if(!((tLCPolSchema.getInsuYear()==tInsuYear)&&(tLCPolSchema.getInsuYearFlag().equals(tInsuYearFlag))))
					{
						CError.buildErr(this, "MS附加提前给付保险期间与主险的保险期间不一致") ;
						return false;
					}
					
				}
				//////////////////////////////////////////////end added by ln 2008.4.10 
				
				          
	          if("121501".equals(tLCPolSchema.getRiskCode()))
	          {
	            if(tMainLCPolSchema.getAmnt()!= tLCPolSchema.getAmnt())
	            {
	              CError.buildErr(this, "险种"+121501+"的保额与主险不一致！") ;
	              return false;
	            }
	          }

		} catch (Exception ex) {
			CError.buildErr(this,ex.toString());
			return false;
		}
		return true;
	}
	
	/**
	 * 所有数据未导入前的校验，失败仍要删除已导入数据
	 * @param tLCPolSchema
	 * @param tLAAgentSchema
	 * @param tTransferData
	 * @return
	 */
	public boolean checkAllunSaveSpec(VData tVData , int tFlag) {
		try {		
			logger.debug("Start checkAllunSaveSpec ...");			
			LCPolSchema tOldLCPolSchema = (LCPolSchema) tVData
					.getObjectByObjectName("LCPolSchema", 0);
			LCCustomerImpartSet tOldLCCustomerImpartSet = (LCCustomerImpartSet) tVData
					.getObjectByObjectName("LCCustomerImpartSet", 0);	
			TransferData tTransferData = (TransferData) tVData
					.getObjectByObjectName("TransferData", 0);
			String tPrtNo = tOldLCPolSchema.getPrtNo();
		      if(tFlag == 0)//只在导入时校验
		      {		    	  
		    	//个单\中介投保单的告知必须录入项1.被保人身高/体重 A0101 2.投保人收入/来源 A0120 3.业务员报告书1(2) A0152 4.业务员报告书2(1) A0155
		    	 /* if(tPrtNo!=null && tPrtNo.trim().length()==14
		                  && ("11".equals(tPrtNo.substring(2,4)) || "21".equals(tPrtNo.substring(2,4))))
		          {
		    		  boolean flag1 = false;
		    		  boolean flag2 = false;
		    		  boolean flag3 = false;
		    		  boolean flag4 = false;
		    		  
		  			if (tOldLCCustomerImpartSet != null
		  					&& tOldLCCustomerImpartSet.size() > 0) {
		  				for (int i = 0; i < tOldLCCustomerImpartSet.size(); i++) {
		  					LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();
		  					tLCCustomerImpartSchema = tOldLCCustomerImpartSet
		  							.get(i + 1);
		  					String impartCode = tLCCustomerImpartSchema.getImpartCode();
		  					
		  					if (impartCode!=null 
		  							&& !impartCode.equals("")) {	
		  						  if(impartCode.equals("A0101"))
								  {
		  							flag1 = true;
								  }
		  						  else if(impartCode.equals("A0120"))
								  {
			  						flag2 = true;
								  }
		  						  else if(impartCode.equals("A0152"))
								  {
			  						flag3 = true;
								  }
		  						  else if(impartCode.equals("A0155"))
								  {
			  						flag4 = true;
								  }
		  					}		  					
		  				}		  				
		  			}
		  			if(flag1 == false)
					  {
						  CError.buildErr(this,"没有录入告知(编码:A0101)！");
						  return false;
					  }
					  if(flag2 == false)
					  {
						  CError.buildErr(this,"没有录入告知(编码:A0120)！");
						  return false;
					  }
					  if(flag3 == false)
					  {
						  CError.buildErr(this,"没有录入告知(编码:A0152)！");
						  return false;
					  }
					  if(flag4 == false)
					  {
						  CError.buildErr(this,"没有录入告知(编码:A0155)！");
						  return false;
					  }
		          }*/
		    	  
		    	  //所有保单类型的“投保申请日期”如在录入日期之后，保存时给出问题件，但能强行保存 
		    	  //ln 2009-07-21 add
		    	  String tPolApplyDate = tOldLCPolSchema.getPolApplyDate();
		    	  String tCurrentDate = PubFun.getCurrentDate();
		    	  if(!PubFun.checkDate(tPolApplyDate , tCurrentDate)) 
		    	  {
		    		  CError.buildErr(this,"投保申请日期晚于录入日期！");
					  return false;
		    	  }		    	
		    	  
		    	  //在系统定义的开始校验日期之后开始校验
		    	  String today = PubFun.getCurrentDate();
		  		  logger.debug("today: " + today);
		  		  ExeSQL tExeSQL = new ExeSQL();
		  		  String tSQL = "select sysvarvalue from ldsysvar where sysvar ='tbstartcheckdate'";
		  		  SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		  		  sqlbv.sql(tSQL);
		  		  String startdate = tExeSQL.getOneValue(sqlbv);
		  		  if (startdate == null || startdate.equals("")) {
		  			  startdate = "";
		  			  logger.debug("系统没有定义开始校验时间");
		  		  }
		  		//所有保单类型的“投保申请日期”如在系统定义的校验日期之后，保存时给出问题件，但能强行保存 
		    	  //ln 2009-09-15 add
		  		  tSQL = "select sysvarvalue from ldsysvar where sysvar ='tbcheckdate'";
		  		  SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		  		  sqlbv1.sql(tSQL);
		  		  String checkdate = tExeSQL.getOneValue(sqlbv1);
		  		  if (checkdate == null || checkdate.equals("")) {
		  			  checkdate = "";
		  			  logger.debug("系统没有定义校验时间");
		  		  }
		    	  if(!startdate.equals("")&&PubFun.checkDate(startdate, today)//当前日期大于等于10月3日
		    			  &&!checkdate.equals("")&&!PubFun.checkDate(checkdate , tPolApplyDate))//投保申请日期小于10月1日
		    	  {
		    		  CError.buildErr(this,"投保申请日期早于"+checkdate+"！");
					  return false;
		    	  }
		  		//印刷号前六位必须为“861102、861602、862102、861502”
		    	  //ln 2009-09-22 add
		    	  tExeSQL = new ExeSQL();
		    	  tSQL = "select sysvarvalue from ldsysvar where sysvar ='checkPrtNoDate'";
		    	  SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		  		  sqlbv2.sql(tSQL);
		  		  String tdate = tExeSQL.getOneValue(sqlbv2);
		  		  if (tdate == null || tdate.equals("")) {
		  			  tdate = "";
		  			  logger.debug("系统没有定义投保申请日期校验时间");
		  		  }
		    	  String theadPrtNo = tPrtNo.substring(0, 6);
		    	  String tSpliPrtNo = tPrtNo.substring(0, 4);
		    	  if(!startdate.equals("")&&PubFun.checkDate(startdate, today)//当前日期大于等于10月3日
		    			  &&!tdate.equals("")&&!theadPrtNo.equals("861102")&&!theadPrtNo.equals("861602")
		    			  &&!theadPrtNo.equals("862102")&&!theadPrtNo.equals("861502")
		    			  &&!tSpliPrtNo.equals("3110")
		    			  && PubFun.checkDate(tdate, tPolApplyDate)) //投保申请日期大于等于2009年10月1日
		    	  {
		    		  CError.buildErr(this,"印刷号前六位或前四位不是861102、861602、862102、861502、3110之一!");
					  return false;
		    	  }		
		    	  //校验[初审日期] -2010-03-19 -hanbin 
		    	  //增加该校验的生效日期的限制 2010-4-27 
		    	  String tCheckDateSQL = "select sysvarvalue from ldsysvar where sysvar ='ServiceSpecification'";
		    	  SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		  		  sqlbv3.sql(tCheckDateSQL);
		  		  checkdate = tExeSQL.getOneValue(sqlbv3);
		  		  if (checkdate == null || checkdate.equals("")) {
		  			  checkdate = "";
		  			  logger.debug("系统没有定义校验时间（tbcheckscandate）");
		  		  }
		  		
				String tScanDate = tPolApplyDate;//投保申请日期
				if (tScanDate == null || "".equals(tScanDate)) {
					tScanDate = "";
				}
			    if(!checkdate.equals("")&&!tScanDate.equals("")&&PubFun.checkDate(checkdate, tScanDate))//投保申请日期大于等于开始校验日期		    			  
			    {
		    	  String tFirstTrialDate = (String)tTransferData.getValueByName("FirstTrialDate");
		    	  if(tFirstTrialDate.compareTo(tPolApplyDate) < 0){
		    		  CError.buildErr(this,"初审日期在投保单申请日期之前！");
					  return false;
		    	  }
		    	  tExeSQL = new ExeSQL();
		    	  tSQL = "select max(makedate) from es_doc_main a  where a.doccode = '"+"?a3?"+"' and a.busstype = 'TB' and a.subtype = 'UA001'";
		    	  SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
					sqlbv9.sql(tSQL);
					sqlbv9.put("a4", tPrtNo);		  		
		    	  String scandate = tExeSQL.getOneValue(sqlbv9);
		  		  if (scandate == null || scandate.equals("")) {
		  			  scandate = "";
		  			  logger.debug("未查询到扫描件");
		  		  }else{
		  			if(tFirstTrialDate.compareTo(scandate) > 0){
			    		  CError.buildErr(this,"初审日期在投保单扫描日期之后！");
						  return false;
			    	  }
		  		  }
			    }
		    	  
		      }//只导入时校验	end	      
		    
		        logger.debug("End checkAllunSaveSpec ...");	

		} catch (Exception ex) {
			CError.buildErr(this,ex.toString());
			return false;
		}
		return true;
	}
	
	/**
	 * 所有数据导入完成后的校验，失败仍要删除已导入数据
	 * @param tLCPolSchema
	 * @param tLAAgentSchema
	 * @param tTransferData
	 * @return
	 */
	public boolean checkAllSaveSpec(LCPolSchema tOldLCPolSchema, TransferData tTransferData , int tFlag) {
		try {		
			logger.debug("Start CheckWBALLField ...");
			//2009-3-3 ln add 对个险和中介进行首续期交费形式校验
			String tPrtNo = tOldLCPolSchema.getPrtNo();			
		      if(tFlag == 0)//2009-3-5 只在导入时校验
		      {
		    	  if(tPrtNo!=null && tPrtNo.trim().length()==14
		                  && ("11".equals(tPrtNo.substring(2,4)) || "21".equals(tPrtNo.substring(2,4))))
		          {
		        	  ExeSQL tExeSQL = new ExeSQL();
		        	  //String sql = "select max(sumprem) from "
		        		//  + " (select nvl(sum(prem),'0') from lcpol where prtno='"+tPrtNo+"' group by mainpolno)";
		              //double tSumPrem = Double.parseDouble(tExeSQL.getOneValue(sql));
		        	 /* "Oracle：select nvl(sum(prem),0) from ljtempfee_lmriskapp
		        	  改造为：select (case when sum(prem) is not null then sum(prem)  else 0 end)  from ljtempfee_lmriskapp;"
                     */
		        	  SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
						sqlbv10.sql("select (case when sum(prem) is not null then sum(prem) else 0 end) from lcpol where prtno='"+"?a5?"+"'");
						sqlbv10.put("a5", tPrtNo);		  		
		        	  double tSumPrem = Double.parseDouble(tExeSQL.getOneValue(sqlbv10));
		              if((tSumPrem - 1000) >0)
		              {
		            	  String strPayMode = StrTool.cTrim((String) tTransferData
		      					.getValueByName("NewPayMode"));
		      			  String strPayLocation = StrTool.cTrim((String) tTransferData
		      					.getValueByName("PayLocation"));
		            	  if((strPayMode!=null && !strPayMode.equals("") && (strPayMode.equals("0") || strPayMode.equals("5") || strPayMode.equals("b")))
		            			  && (strPayLocation!=null && !strPayLocation.equals("") && (strPayLocation.equals("0") || strPayLocation.equals("4") || strPayLocation.equals("5") || strPayLocation.equals("b"))))
		            	  {    		  
		            	  }
		            	  else
		            	  {
		            		  CError.buildErr(this, "首、续期交费应为银行转账、客户自交或其它！");
		            		  return false;
		            	  }
		              }
		              
		             /* //个单\中介投保单的告知必须录入项1.被保人身高/体重 A0101 2.投保人收入/来源 A0120 3.业务员报告书1(2) A0152 4.业务员报告书2(1) A0155
		              String impartSql = "select (select nvl(count(*),0) from lccustomerimpart where prtno='"+tPrtNo+"' and impartver='A01' and impartcode='A0101' and customernotype='1') count1"
		                                  + " ,(select nvl(count(*),0) from lccustomerimpart where prtno='"+tPrtNo+"' and impartver='A02' and impartcode='A0120' and customernotype='0') count2"
		                                  + " ,(select nvl(count(*),0) from lccustomerimpart where prtno='"+tPrtNo+"' and impartver='A03' and impartcode='A0152' and customernotype='2') count3"
		                                  + " ,(select nvl(count(*),0) from lccustomerimpart where prtno='"+tPrtNo+"' and impartver='A03' and impartcode='A0155' and customernotype='2') count4"
		                                  + " from dual where 1=1 ";
		              tExeSQL = new ExeSQL();
					  SSRS tSSRS = tExeSQL.execSQL(impartSql);
					  if (tSSRS == null || tSSRS.getMaxRow() == 0) {
						  CError.buildErr(this, "投保单"+tPrtNo+"告知信息查询失败！");
						  return false;
					  }
					  else
					  {
						  int count1 = Integer.parseInt(tSSRS.GetText(1, 1));
						  int count2 = Integer.parseInt(tSSRS.GetText(1, 2));
						  int count3 = Integer.parseInt(tSSRS.GetText(1, 3));
						  int count4 = Integer.parseInt(tSSRS.GetText(1, 4));
						  if(count1 < 1)
						  {
							  CError.buildErr(this,"没有录入被保人告知(版别:A01编码:A0101)！");
							  return false;
						  }
						  if(count2 < 1)
						  {
							  CError.buildErr(this,"没有录入投保人告知(版别:A02编码:A0120)！");
							  return false;
						  }
						  if(count3 < 1)
						  {
							  CError.buildErr(this,"没有录入业务员告知(版别:A03编码:A0152)！");
							  return false;
						  }
						  if(count4 < 1)
						  {
							  CError.buildErr(this,"没有录入业务员告知(版别:A03编码:A0155)！");
							  return false;
						  }
					  }*/
		          }
		    	  
		    	  //2010-3-11 ln add 身份证明校验
		    	  double cMoney = 0;
		    	  double cPrem = 0;
		    	  int tPayIntv = tOldLCPolSchema.getPayIntv();
		    	  int tpayendyear = 0;		    	  
		    	  String strPayMode = StrTool.cTrim((String) tTransferData
	      					.getValueByName("NewPayMode"));
	      			  String strPayLocation = StrTool.cTrim((String) tTransferData
	      					.getValueByName("PayLocation"));	      			  
	              
	              //查询整单保费
	      		/*	"Oracle：select decode(count(prtno),0,'0','1') from laagentascription 
	      			改造为：select (case count(prtno) when 0 then '0' else '1' end)from laagentascription "
                */
		    	  /*String sql = "select decode(payintv,1,prem*12,3,prem*4,6,prem*2,prem),payendyear," +
		    	  		"payendyearflag,(select min(insuredappage) from lcinsured b where a.contno=b.contno and " +
		    	  		"a.insuredno=b.insuredno),polapplydate from lcpol a where prtno='"+"?a6?"+"' ";*/
	      			String sql ="select (case payintv when 1 then prem*12 when 3 then prem*4 when 6 then prem*2 else prem end),payendyear," +
			    	  		"payendyearflag,(select min(insuredappage) from lcinsured b where a.contno=b.contno and " +
			    	  		"a.insuredno=b.insuredno),polapplydate from lcpol a where prtno='"+"?a6?"+"' ";
		    	  SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
					sqlbv11.sql(sql);
					sqlbv11.put("a6", tPrtNo);	
		    	  ExeSQL tExeSQL = new ExeSQL();
					SSRS tSSRS = tExeSQL.execSQL(sqlbv11);
					if (tSSRS == null || tSSRS.getMaxRow() == 0) {
						CError.buildErr(this, "保单"+tPrtNo+"信息查询失败！");
						return false;
					}
					//查询系统定义校验时间
					 sql = "select sysvarvalue from ldsysvar where sysvar ='tbcheckhaveiddate'";
					 SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
						sqlbv13.sql(sql);
			  		  String checkdate = tExeSQL.getOneValue(sqlbv13);
			  		  if (checkdate == null || checkdate.equals("")) {
			  			  checkdate = "";
			  			  logger.debug("系统没有定义校验时间（tbcheckhaveiddate）");
			  		  }
			  		
					String tpolapplydate = tSSRS.GetText(1, 5);//投保申请日期
					if (tpolapplydate == null || "".equals(tpolapplydate)) {
						tpolapplydate = "";
					}
					sql = "select count(1) from es_doc_main where doccode='"+ "?a7?" + "' and subtype='UR208'";
					SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
					sqlbv12.sql(sql);
					sqlbv12.put("a7", tPrtNo);	
					String r1 = new ExeSQL().getOneValue(sqlbv12);
				    if(!checkdate.equals("")&&!tpolapplydate.equals("")&&PubFun.checkDate(checkdate, tpolapplydate)&&r1.equals("0"))//投保申请日期大于等于开始校验日期且无身份证明扫描件		    			  
				    {
				    	if(tPayIntv!=0)//期交
		      			  {
		      				  if(strPayMode!=null && !strPayMode.equals("") && strPayMode.equals("0")
			            			  && strPayLocation!=null && !strPayLocation.equals("") && strPayLocation.equals("0"))//如果首续期均为银行转帐，则判断金额20万
			            	      cMoney = 200000;	            	  
			            	  else
			            		  cMoney = 20000;
		      			  }
		      			  else//趸交
		      			  {
		      				if(strPayMode!=null && !strPayMode.equals("") && strPayMode.equals("0"))//如果首期为银行转帐，则判断金额20万
			            	      cMoney = 200000;	            	  
			            	  else
			            		  cMoney = 20000;
		      			  }
						for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
						if (tPayIntv != 0) {
							if (tSSRS.GetText(i, 3).equals("Y"))
								cPrem = cPrem + Double.parseDouble(tSSRS.GetText(i, 1))
										* Integer.parseInt(tSSRS.GetText(i, 2));
							else {
								tpayendyear = Integer.parseInt(tSSRS.GetText(i,2))
										- Integer.parseInt(tSSRS.GetText(i, 4));
								cPrem = cPrem + Double.parseDouble(tSSRS.GetText(i, 1)) * tpayendyear;
							}
						} else {
							cPrem = Double.parseDouble(tSSRS.GetText(i, 1));
						}
					}
						if (cPrem - cMoney >=0) {
							CError.buildErr(this, "不符合反洗钱实施细则规定，未提供客户有效证件复印件");
							return false;
						}
				    }					
		      }
		      
		      //8615、25、35的单子不允许有“MS金康附加额外给付重大疾病保险”--321501
		      if("8615".equals(tPrtNo.substring(0, 4))||"8625".equals(tPrtNo.substring(0, 4))
		    		  ||"8635".equals(tPrtNo.substring(0, 4))){
		    	  String tCheckRiskSql = "select count(1) from lcpol where prtno='"+"?a8?"+"'"
		    	  		+ " and riskcode='321501'";
		    		SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
					sqlbv13.sql(tCheckRiskSql);
					sqlbv13.put("a8", tPrtNo);	
		    	  ExeSQL tExeSQL = new ExeSQL();
		    	  int tCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv13));
		    	  if(tCount>0){
		    		  CError.buildErr(this, "印刷号为8615、8625、8635的投保单不能附加MS金康附加额外给付重大疾病保险");
		    		  return false;
		    	  }
		      }
		      
		    //所有险种导入完毕后的校验
		      //校验外包录入的信息
		        logger.debug("Start CheckWBALLField ...");
		        String sql = "select mainpolno from lcpol where prtno='"+"?a9?"+"' and polno=mainpolno ";
		        SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
				sqlbv13.sql(sql);
				sqlbv13.put("a9", tPrtNo);	
		        String tCheckRiskSql = "select count(1) from lcpol where prtno='"+"?a8?"+"'"
		    	  		+ " and riskcode='321501'";
		    		SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
					sqlbv14.sql(tCheckRiskSql);
					sqlbv14.put("a8", tPrtNo);	
		        ExeSQL tExeSQL = new ExeSQL();
				SSRS tSSRS = tExeSQL.execSQL(sqlbv14);
				if (tSSRS == null || tSSRS.getMaxRow() == 0) {
					CError.buildErr(this, "保单"+tPrtNo+"信息查询失败！");
					return false;
				}
		        for (int i =1;i<=tSSRS.getMaxRow();i++)
		        {
		        	sql = "select (case when count(*) is not null then count(*) else 0 end) from lcpol a where mainpolno='"+"?a10?"+"' and mainpolno<>polno "
			        		+ " and exists (select 1 from lcpol where mainpolno=a.mainpolno and riskcode='121508') "
			        		+ " and exists (select 1 from lmriskapp where riskcode= a.riskcode and riskperiod='L') "
			        		+ " and riskcode not in ('121301','121508')";
		        	SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
					sqlbv15.sql(sql);
					sqlbv15.put("a10", tSSRS.GetText(i, 1));	
		             int tCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv15));
		             if(tCount != 0)
		             {
		            	 CError.buildErr(this, "附加提前给付重疾不能与除“附加豁免保费定期”以外的其他长期附加险同时投保！");
	            		 return false;
		             }
		           /*  "Oracle：select nvl(sum(prem),0) from ljtempfee_lmriskapp
		                                    改造为：select (case when sum(prem) is not null then sum(prem)  else 0 end)  from ljtempfee_lmriskapp;"
                   */
		             sql = " select (case when count(*) is not null then count(*) else 0 end) from lcpol a where mainpolno='"+"?a11?"+"' and mainpolno<>polno "
			        		+ " and exists (select 1 from lcpol where mainpolno=a.mainpolno and riskcode='121509') "
			        		+ " and exists (select 1 from lmriskapp where riskcode= a.riskcode and riskperiod='L') "
			        		+ " and riskcode not in ('121301','121509')";
		             SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
						sqlbv16.sql(sql);
						sqlbv16.put("a11", tSSRS.GetText(i, 1));	
		             int tCount1 = Integer.parseInt(tExeSQL.getOneValue(sqlbv16));
		             if(tCount1 != 0)
		             {
		            	 CError.buildErr(this, "附加提前给付男性重疾不能与除“附加豁免保费定期”以外的其他长期附加险同时投保！");
		            	 return false;
		             }
		             sql = " select (case when count(*) is not null then count(*) else 0 end) from lcpol a where mainpolno='"+"?a12?"+"' and mainpolno<>polno "
			        		+ " and exists (select 1 from lcpol where mainpolno=a.mainpolno and riskcode='121510') "
			        		+ " and exists (select 1 from lmriskapp where riskcode= a.riskcode and riskperiod='L') "
			        		+ " and riskcode not in ('121301','121510')";
		             SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
						sqlbv17.sql(sql);
						sqlbv17.put("a12", tSSRS.GetText(i, 1));	
		             int tCount2 = Integer.parseInt(tExeSQL.getOneValue(sqlbv17));
		             if(tCount2 != 0)
		             {
		            	 CError.buildErr(this, "附加提前给付女性重疾不能与除“附加豁免保费定期”以外的其他长期附加险同时投保！");
		            	 return false;
		             }
		        }
		        logger.debug("End CheckWBALLField ...");	

		} catch (Exception ex) {
			CError.buildErr(this,ex.toString());
			return false;
		}
		return true;
	}

	/**
	 * 校验计划与保险信息是否一致
	 * @param tLCPolSchema
	 * @param tTransferData
	 * @param tRiskBasicInfo
	 * @return
	 */
	private boolean checkRiskPlan(LCPolSchema tLCPolSchema,
			TransferData tTransferData, RiskBasicInfo tRiskBasicInfo) {
		try {
			logger.debug("tRiskBasicInfo.getRiskCode(): "
					+ tRiskBasicInfo.getRiskCode());
			logger.debug("tRiskBasicInfo.getRemark(): "
					+ tRiskBasicInfo.getRemark());
			if (tRiskBasicInfo.getRiskCode() != null
					&& !"".equals(tRiskBasicInfo.getRiskCode())
					&& tRiskBasicInfo.getRemark() != null
					&& !"".equals(tRiskBasicInfo.getRemark())) {
				Method m;
				BPORiskPlanSchema tBPORiskPlanSchema = new BPORiskPlanSchema();
				BPORiskPlanSet tBPORiskPlanSet = new BPORiskPlanSet();
				BPORiskPlanDB tBPORiskPlanDB = new BPORiskPlanDB();
				tBPORiskPlanDB.setRiskCode(tRiskBasicInfo.getRiskCode());
				tBPORiskPlanDB.setPlanName(StrTool.cTrim(tRiskBasicInfo
						.getRemark()));

				tBPORiskPlanSet = tBPORiskPlanDB.query();
				if (tBPORiskPlanSet != null && tBPORiskPlanSet.size() > 0) {
					for (int i = 1; i <= tBPORiskPlanSet.size(); i++) {
						tBPORiskPlanSchema = tBPORiskPlanSet.get(i);
						if (tBPORiskPlanSchema.getPlanFactor() != null
								&& !"".equals(StrTool.cTrim(tBPORiskPlanSchema
										.getPlanFactor()))) {
							Object tObjectValue = new Object();
							String tStringValue = new String();
							m = tRiskBasicInfo.getClass().getMethod(
									"get"
											+ StrTool.cTrim(tBPORiskPlanSchema
													.getPlanFactor()), null);
							logger.debug("m.getName()" + m.getName()
									+ "            m.getReturnType(): "
									+ m.getReturnType());
							tObjectValue = m.invoke(tRiskBasicInfo, null);
							if ("0".equals(tBPORiskPlanSchema.getCalType())) //如果不需要计算
							{
								tStringValue = tBPORiskPlanSchema
										.getDefaultValue();
							} else //需要计算，根据计算编码，去LMCalMode中取算法并计算结果
							{
								Calculator mCalculator = new Calculator();
								mCalculator.addBasicFactor("GetYears", StrTool
										.cTrim((String) tTransferData
												.getValueByName("GetYears")));
								mCalculator.addBasicFactor("GetYear", String
										.valueOf(tLCPolSchema.getGetYear()));
								mCalculator.addBasicFactor("GetYearFlag",
										StrTool.cTrim(tLCPolSchema
												.getGetYearFlag()));
								mCalculator.addBasicFactor("InsuYear", String
										.valueOf(tLCPolSchema.getInsuYear()));
								mCalculator.addBasicFactor("InsuYearFlag",
										StrTool.cTrim(tLCPolSchema
												.getInsuYearFlag()));
								mCalculator.addBasicFactor("PayEndYear", String
										.valueOf(tLCPolSchema.getPayEndYear()));
								mCalculator.addBasicFactor("PayEndYearFlag",
										StrTool.cTrim(tLCPolSchema
												.getPayEndYearFlag()));
								mCalculator.addBasicFactor("RiskCode", StrTool
										.cTrim(tLCPolSchema.getRiskCode()));
								mCalculator.addBasicFactor("Prem", String
										.valueOf(tLCPolSchema.getPrem()));
								mCalculator.addBasicFactor("Amnt", String
										.valueOf(tLCPolSchema.getAmnt()));
								mCalculator.addBasicFactor("Mult", String
										.valueOf(tLCPolSchema.getMult()));
								mCalculator.setCalCode(tBPORiskPlanSchema
										.getCalCode());
								tStringValue = mCalculator.calculate();//得到计算后的结果
							}

							try {
								logger.debug("tObjectValue : "
										+ tObjectValue);
								logger.debug("tStringValue转换后 : "
										+ getNumber(tObjectValue.getClass(),
												tStringValue));
								if (!tObjectValue.equals(getNumber(tObjectValue
										.getClass(), tStringValue))) {
									CError.buildErr(this,"外包方返回的险种备注信息（"
											+ tRiskBasicInfo.getRemark()
											+ "）与险种"
											+ tBPORiskPlanSchema.getRemark()
											+ "不匹配！");
									logger.debug(tRiskBasicInfo
											.getRiskCode()
											+ tRiskBasicInfo.getRemark()
											+ "与"
											+ tBPORiskPlanSchema.getRemark()
											+ "不匹配！");
									return false;
								}
							} catch (Exception ex1) {
								CError.buildErr(this,"校验计划时发生异常");
								return false;
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			CError.buildErr(this,ex.toString());
			return false;
		}
		return true;
	}

	/**
	 * 险种录入信息校验
	 * @param tLCPolSchema  保单信息
	 * @param tTransferData
	 * @param operType 操作类型
	 * @return
	 */
	private boolean CheckTBField(LCPolSchema tLCPolSchema,
			TransferData tTransferData, String operType) {
		String strMsg = "";
		boolean MsgFlag = false;
		String RiskCode = tLCPolSchema.getRiskCode();
		PrepareBOMTBBL tPrepareBOMTBBL = new PrepareBOMTBBL();
		TransferData  mTransferData=new TransferData();
		LCDutySchema tLCDutySchema=new LCDutySchema();
		mTransferData.setNameAndValue("GetDutyKind", (String) tTransferData.getValueByName("GetDutyKind"));
		mTransferData.setNameAndValue("OutPayFlag", (String) tTransferData.getValueByName("OutPayFlag"));
		VData hVData = new VData();
		hVData.add(mTransferData);
		this.mBomList = tPrepareBOMTBBL.dealData(tLCPolSchema,tLCDutySchema,tLCInsuredSchema,hVData);
		try {
			VData tVData = new VData();
			CheckFieldCom tCheckFieldCom = new CheckFieldCom();

			// 计算要素
			FieldCarrier tFieldCarrier = new FieldCarrier();
			tFieldCarrier.setMult(tLCPolSchema.getMult()); // 投保份数
			tFieldCarrier.setPolNo(StrTool.cTrim(tLCPolSchema.getPolNo())); // 投保单号码
			tFieldCarrier.setMainPolNo(StrTool.cTrim(tLCPolSchema
					.getMainPolNo())); // 主险号码
			tFieldCarrier
					.setRiskCode(StrTool.cTrim(tLCPolSchema.getRiskCode())); // 险种编码
			tFieldCarrier.setCValiDate(StrTool.cTrim(tLCPolSchema
					.getCValiDate())); // 生效日期
			tFieldCarrier.setAmnt(tLCPolSchema.getAmnt()); // 保额
			tFieldCarrier.setInsuYear(tLCPolSchema.getInsuYear()); // 保险期间
			tFieldCarrier.setInsuYearFlag(StrTool.cTrim(tLCPolSchema
					.getInsuYearFlag())); // 保险期间单位
			tFieldCarrier.setPayEndYear(tLCPolSchema.getPayEndYear()); // 交费期间
			tFieldCarrier.setPayEndYearFlag(StrTool.cTrim(tLCPolSchema
					.getPayEndYearFlag())); // 交费期间单位
			tFieldCarrier.setPayIntv(tLCPolSchema.getPayIntv()); // 交费方式
			logger.debug("***PayLocation："
					+ tLCPolSchema.getPayLocation());
			tFieldCarrier.setPayLocation(StrTool.cTrim(tLCPolSchema
					.getPayLocation()));
			logger.debug("保单类型为：" + tLCPolSchema.getPolTypeFlag());
			tFieldCarrier.setPrem(tLCPolSchema.getPrem());
			tFieldCarrier.setPolTypeFlag(StrTool.cTrim(tLCPolSchema
					.getPolTypeFlag()));
			tFieldCarrier.setBonusGetMode(StrTool.cTrim(tLCPolSchema
					.getBonusGetMode()));
			tFieldCarrier.setLiveGetMode(StrTool.cTrim(tLCPolSchema
					.getLiveGetMode()));
			tFieldCarrier.setOutPayFlag(StrTool.cTrim((String) tTransferData
					.getValueByName("OutPayFlag")));
			tFieldCarrier.setGetDutyKind(StrTool.cTrim((String) tTransferData
					.getValueByName("GetDutyKind")));
			tFieldCarrier.setGetYear(tLCPolSchema.getGetYear());

			if (tLCPolSchema.getStandbyFlag1() != null) {
				tFieldCarrier.setStandbyFlag1(StrTool.cTrim(tLCPolSchema
						.getStandbyFlag1()));
			}

			if (tLCPolSchema.getStandbyFlag2() != null) {
				tFieldCarrier.setStandbyFlag2(StrTool.cTrim(tLCPolSchema
						.getStandbyFlag2()));
			}

			if (tLCPolSchema.getStandbyFlag3() != null) {
				tFieldCarrier.setStandbyFlag3(StrTool.cTrim(tLCPolSchema
						.getStandbyFlag3()));
			}

			logger.debug("Prepare Data");

			tVData.add(tFieldCarrier);

			LMCheckFieldSchema tLMCheckFieldSchema = new LMCheckFieldSchema();
			tLMCheckFieldSchema.setRiskCode(RiskCode);
			tLMCheckFieldSchema.setFieldName("WB" + operType); // 投保
			tVData.add(tLMCheckFieldSchema);

			if (tCheckFieldCom.CheckField(tVData) == false) {
				this.mErrors.copyAllErrors(tCheckFieldCom.mErrors);

				return false;
			} else {
				logger.debug("Check Data");

				LMCheckFieldSet mLMCheckFieldSet = tCheckFieldCom
						.GetCheckFieldSet();

				for (int n = 1; n <= mLMCheckFieldSet.size(); n++) {
					LMCheckFieldSchema tField = mLMCheckFieldSet.get(n);

					if ((tField.getReturnValiFlag() != null)
							&& tField.getReturnValiFlag().equals("N")) {
						if ((tField.getMsgFlag() != null)
								&& tField.getMsgFlag().equals("Y")) {
							MsgFlag = true;
							strMsg = strMsg + tField.getMsg() + " ; ";

							break;
						}
					}
				}

				if (MsgFlag == true) {
					// @@错误处理
					CError.buildErr(this, "数据有误：" + strMsg);
					return false;
				}
			}
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "发生错误，请检验CheckField模块:" + ex);
			return false;
		}

		return true;
	}

	/**
	 * 通过java反射机制将字符串转换为任意一种数据类型的对像
	 * @param type
	 * @param str
	 * @return
	 * @throws Exception
	 */
	private Object getNumber(Class type, String str) throws Exception {
		Class[] paramsClasses = { str.getClass() };
		Object[] params = { str };
		Constructor c = type.getConstructor(paramsClasses);
		Object o = c.newInstance(params);
		return o;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		WBChangeRiskField rWBChangeRiskField = new WBChangeRiskField();
		TransferData tTransferData = new TransferData();
		LCPolDB tLCPolDB = new LCPolDB();
		//20081127172111
		tLCPolDB.setPolNo("110110000002348");
		tLCPolDB.getInfo();
		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSchema = tLCPolDB.getSchema();
		rWBChangeRiskField.changeRiskField(tLCPolSchema, tTransferData, "WB");

	}

}
