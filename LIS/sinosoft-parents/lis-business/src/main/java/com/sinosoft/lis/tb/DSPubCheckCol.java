package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import com.sinosoft.lis.pubfun.CodeQueryUI;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LBPOContSchema;
import com.sinosoft.lis.schema.LDCodeSchema;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.SchemaSet;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 双岗录入信息字段规则校验类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author tongmeng
 * @version 1.0
 */
public class DSPubCheckCol {
private static Logger logger = Logger.getLogger(DSPubCheckCol.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LBPOContSchema tLBPOContSchema = new LBPOContSchema();
		tLBPOContSchema.setManageCom("86110000");
		tLBPOContSchema.setAgentCode("a1?");
		//tLBPOContSchema.setAgentCom("1");
		tLBPOContSchema.setSaleChnl("99?");
		tLBPOContSchema.setMult("13");
		tLBPOContSchema.setPayIntv("11");
		// tLCContSchema.setAppntBirthday("00-13-01");
		// String test = "2";
		// String[] te = test.split("&");
		// logger.debug(te.length);
		DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
		VData tVData = new VData();
		// String tRule = "客户性别|code:Sex#num&len<=1#NULL#DATE#Notnull&value=2";
		// String tRes = tDSPubCheckCol.checkValueByRule("3", tRule);
		// logger.debug(tRes);
		tVData.add(tLBPOContSchema);
		TransferData tTransferData = tDSPubCheckCol.verifyValue(tVData,tLBPOContSchema,
		"02");
		
//		TransferData tTransferData = tDSPubCheckCol.verifyValue(tLBPOContSchema,
//				"02");
		Vector tV = tTransferData.getValueNames();
		for (int i = 0; i < tV.size(); i++) {
			logger.debug((String) tTransferData.getValueByName(tV.get(i)));
		}

	}

	private TransferData mAllDataTransferData = new TransferData();
	/**
	 * 按照传入的VData进行校验 其中VData中只能放Schema
	 * @param tVData
	 * @param tType
	 * @return
	 */
	public TransferData verifyValue(VData tVData,Schema tSchema ,String tType)
	{
		TransferData tResTransferData = new TransferData();
		//首先获得VData中的所有Schema,并将数据缓存起来
		for(int i=0;i<tVData.size();i++)
		{
			try {
				Schema tCurrSchema = (Schema)tVData.getObject(i);
				String tClassName = tCurrSchema.getClass().getName();
				String tCheckName = tClassName
						.substring(tClassName.lastIndexOf(".") + 1);
				logger.debug("tClassName:"+tClassName);
				
				for (int n = 1; n <= tCurrSchema.getFieldCount(); n++) {
					String tName = tCheckName+"."+ tCurrSchema.getFieldName(n);
					String tValue = tCurrSchema.getV(n);
					logger.debug("tName:"+tName + ":tValue:"+tValue);
					this.mAllDataTransferData.setNameAndValue(tName, tValue);
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				tResTransferData.setNameAndValue("Exception ", e.toString());
			}
		}

		tResTransferData = this.verifyValue(tSchema, tType);
			
		return tResTransferData;
	}
	
	/**
	 * 按照界面传入Set进行校验 待开发
	 * 
	 * @param tSchemaSet
	 * @return
	 */
	private TransferData verifyValue(SchemaSet tSchemaSet, String tType) {
		TransferData tResTransferData = new TransferData();
		// 缓存校验字段和规则
		HashMap tRuleHashMap = new HashMap();
		// 获得传入的类名
		// String tCheckName = tSchemaSet.getClass().getSimpleName();
		String tClassName = tSchemaSet.getClass().getName();
		String tCheckName = tClassName
				.substring(tClassName.lastIndexOf(".") + 1);
		// 获得需要校验的数据表名
		tCheckName = tCheckName.substring(0, tCheckName.lastIndexOf("Set"));
		// 获得双岗录入校验表的校验规则.
		String tSQL = "select contrascol,msg,errorcode,checkrule from lbpodatadictionary "
				+ " where checktype='02' and "
				+ " tablename='"
				+ "?tablename?" + "' ";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSQL);
		sqlbv.put("tablename", tCheckName.toLowerCase());
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv);
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			tRuleHashMap.put(tSSRS.GetText(i, 1), tSSRS.GetText(i, 4));
		}
		// 循环校验

		return tResTransferData;
	}

	/**
	 * 校验Schema的数值
	 * 
	 * @param tSchema
	 * @return
	 */
	public TransferData verifyValue(Schema tSchema, String tType) {
		// 返回信息
		TransferData tResTransferData = new TransferData();
		// 缓存校验字段和规则
		HashMap tRuleHashMap = new HashMap();
		HashMap tRuleHashMapNsg = new HashMap();
		// 获得传入的类名
		// String tCheckName = tSchema.getClass().getSimpleName();
		String tClassName = tSchema.getClass().getName();
		String tCheckName = tClassName
				.substring(tClassName.lastIndexOf(".") + 1);
		// 获得需要校验的数据表名
		tCheckName = tCheckName.substring(0, tCheckName.lastIndexOf("Schema"));
		// 获得双岗录入校验表的校验规则.
		String tSQL = "select contrascol,msg,errorcode,checkrule from lbpodatadictionary "
				+ " where checktype='"
				+ "?tablename?"
				+ "' and "
				+ " lower(tablename)='" + "?tablename?" + "' ";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSQL);
		sqlbv1.put("checktype", tType);
		sqlbv1.put("tablename", tCheckName.toLowerCase());
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv1);
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			tRuleHashMap.put(tSSRS.GetText(i, 1), tSSRS.GetText(i, 4));
			tRuleHashMapNsg.put(tSSRS.GetText(i, 1), tSSRS.GetText(i, 2));
		}
		//tongmeng 2009-02-05 add
		//增加支持JAVA运算符
		//首先先把当前的Schema内的数据都缓存起来.
		//tongmeng 2009-02-18 modify
		//使用全局的缓存
		//TransferData checkTransferData = new TransferData();
		for (int i = 0; i < tSchema.getFieldCount(); i++) {
			String tName = tCheckName+"."+ tSchema.getFieldName(i);
			String tValue = tSchema.getV(i);
			logger.debug("tName:"+tName + ":tValue:"+tValue);
			this.mAllDataTransferData.removeByName(tName);
			this.mAllDataTransferData.setNameAndValue(tName, tValue);
			//checkTransferData.setNameAndValue(tName, tValue);
		}
		// 循环校验
		for (int i = 0; i < tSchema.getFieldCount(); i++) {
			String tFieldName = tSchema.getFieldName(i);
			String tFieldValue = tSchema.getV(i);
			if(tFieldName.equals("PayLocation"))
			{
				//AppntName
				logger.debug("tFieldValue:"+tFieldValue);
			}
			
			if (tRuleHashMap.containsKey(tFieldName)) {
				String tRule = (String) tRuleHashMap.get(tFieldName);
				String tErrInfo = this.checkValueByRule(tFieldValue, tRule,this.mAllDataTransferData,"0");
				if (tErrInfo == null || tErrInfo.equals("")) {
					// 没有错误信息,跳过
					continue;
				} else if (tErrInfo.equals("-1")) {
					// 规则描述错误
					return null;
				} else {
					// tErrInfo = tRuleHashMapNsg.get(tFieldName) + tErrInfo;
					// 有错误信息,记录错误信息
					tResTransferData.setNameAndValue(tFieldName, tErrInfo);
				}
			}
		}
		// logger.debug(tCheckName+"@@@");

		return tResTransferData;
	}

	/**
	 * 校验Schema的必要信息是否都为空，如果必要信息都为空，认为该条信息为空信息，返回true
	 * schema为空，需要移除，返回true,不需要移除返回false
	 * @param tSchema
	 * @return true OR flase
	 */
	public boolean ifRemove(Schema tSchema, String tType) {
		boolean ifRemove=true;//是否需要移除标志
		// 缓存校验字段和规则
		HashMap tRuleHashMap = new HashMap();
		// 获得传入的类名
		// String tCheckName = tSchema.getClass().getSimpleName();
		String tClassName = tSchema.getClass().getName();
		String tCheckName = tClassName
				.substring(tClassName.lastIndexOf(".") + 1);
		// 获得需要校验的数据表名
		tCheckName = tCheckName.substring(0, tCheckName.lastIndexOf("Schema"));
		// 获得双岗录入校验表的校验规则.
		String tSQL = "select contrascol,msg from lbpodatadictionary "
				+ " where checktype='"
				+ "?checktype?"
				+ "' and "
				+ " lower(tablename)='" + "?tablename?" + "' ";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSQL);
		sqlbv2.put("checktype", tType);
		sqlbv2.put("tablename", tCheckName.toLowerCase());
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv2);
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			tRuleHashMap.put(tSSRS.GetText(i, 1), tSSRS.GetText(i, 2));
		}
		// 循环校验 只要有一个字段不为空该条schema就不需要移除
		for (int i = 1; i <= tSchema.getFieldCount(); i++) {
			String tFieldName = tSchema.getFieldName(i);
			String tFieldValue = tSchema.getV(i);
			if (tRuleHashMap.containsKey(tFieldName)) {
				if(tFieldValue==null||"null".equals(tFieldValue)||"".equals(tFieldValue.trim())){
					ifRemove=true;
				}else{
					ifRemove=false;
					break;
				}
			}
		}
		// logger.debug(tCheckName+"@@@");

		return ifRemove;
	}
	
	/**
	 * 分解规则,校验数据
	 * 支持校验规则格式：
	 * 运算符: & 并 
	 *        # 或
	 * 校验时,或运算符优先
	 * 校验规则:
	 * 		  NULL 输入值可为空
	 *        NOTNULL 输入值不可为空
	 *        INT 输入值为整形
	 *        NUM 输入值为数值型
	 *        DATE 输入值为日期型
	 *        CODE:XX 输入值符合CodeQueryBL 中显示类型为XX的 查询出来的结果
	 *        LEN=XX LEN>=XX LEN<=XX LEN<XX LEN>XX  输入值的长度符合XX
	 *        VALUE>=XX VALUE<=XX 等等 输入值的内容符合XX
	 *        WITHOUT:XX^YY 输入值的内容不包含 XX或YY 
	 * @param tValue
	 * @param tRule
	 * @return
	 */
	public String checkValueByRule(String tValue, String tRule,TransferData checkTransferData,String tType) {
		String tErrInfo = "";
		if (tRule.indexOf("|") == -1) {
			// 没有错误信息
			return "-1";
		}
		// 截取错误信息
		tErrInfo = tRule.substring(0, tRule.indexOf("|"));
		String tRuleInfo = tRule.substring(tRule.indexOf("|") + 1);
		// 分解校验规则
		String[] tRuleArr;
		// 处理"或"运算
		tRuleArr = tRuleInfo.split("#");
		// verify="客户性别|code:Sex|num&len=1"
		// verify="客户性别|code:Sex&len=1"

		String tCheckRes = "";
		boolean tORFlag = false;
		for (int i = 0; i < tRuleArr.length; i++) {
			// tCheckRes = "";
			// 分解"与"运算
			String[] tRuleArrAnd = tRuleArr[i].split("&");
			String tCheckOr = "";
			for (int n = 0; n < tRuleArrAnd.length; n++) {
				String tRuleCurr = tRuleArrAnd[n];
				// tCheckOr = "";
				String tCheckInfo = this.verifyType(tRuleCurr, tValue,checkTransferData);
				if (!tCheckInfo.equals("") && !tCheckInfo.equals("-1")) {
					if (tCheckOr.equals("")) {
						tCheckOr = tCheckInfo + "";
					} else {
						tCheckOr = tCheckOr + " 并且 " + tCheckInfo;
					}
				} else {
					// tORFlag = true;
				}
				if (n == tRuleArrAnd.length - 1 && !tCheckOr.equals("")) {
					tCheckOr = "(" + tCheckOr + ")";
				}
			}
			if (tCheckOr.equals("")) {
				tORFlag = true;
			}
			if (tCheckRes.equals("")) {
				if (!tORFlag) {
					tCheckRes = tCheckOr + "";
				}
			} else {
				if (!tORFlag) {
					tCheckRes = tCheckRes + " 或者 " + tCheckOr;
				} else {
					tCheckRes = "";
					// tORFlag = false;
				}
			}

		}
		if (!tCheckRes.equals("")) {
			// tErrInfo = "原因是:";
			tErrInfo = tErrInfo + tCheckRes;
		} else {
			tErrInfo = "";
		}
		if(!tType.equals("0"))
		{
			if(tErrInfo.indexOf("(")!=-1)
			{
				tErrInfo = tErrInfo.substring(tErrInfo.indexOf("(")+1,tErrInfo.lastIndexOf(")"));
			}
		}

		return tErrInfo;
	}

	/**
	 * 按照每个规则的校验调用相关函数
	 * 
	 * @param tRule
	 * @param tValue
	 * @return
	 */
	private String verifyType(String tRule, String tValue,TransferData checkTransferData) {
		String tInfo = "";
		if (tValue != null && tValue.equals("null")) {
			tValue = null;
		}
		if (tRule.toUpperCase().equals("NULL")) {
			return this.verifyNULL(tValue);
		}
		if (tRule.toUpperCase().equals("NOTNULL")) {
			return this.verifyNOTNULL(tValue);
		} else if (tRule.toUpperCase().equals("DATE")) {
			return this.verifyDATE(tValue);
		} else if (tRule.toUpperCase().equals("EMAIL")) {

		} else if (tRule.toUpperCase().equals("INT")) {
			return this.verifyINT(tValue);
		} else if (tRule.toUpperCase().equals("NUM")) {
			return this.verifyDouble(tValue);
		} else if (tRule.toUpperCase().equals("TEL")) {

		} else if (tRule.toUpperCase().equals("TEL")) {

		}

		else if (tRule.toUpperCase().indexOf("CODE:") != -1) {
			return this.verifyCode(tRule, tValue);
		} else if (tRule.toUpperCase().indexOf("LEN") != -1) {
			return this.verifyLEN(tRule, tValue);
		} else if (tRule.toUpperCase().indexOf("VALUE") != -1) {
			return this.verifyVALUE(tRule, tValue);
		}
		else if (tRule.toUpperCase().indexOf("WITHOUT") != -1) {
			return this.verifyWITHOUT(tRule, tValue);
		}
		
		//tongmeng 2009-03-09 add
		//支持字母类型的校验
		else if (tRule.toUpperCase().indexOf("CHAR") != -1) {
			return this.verifyCHAR(tRule, tValue);
		}
		
		//tongmeng 2009-02-05 add
		//支持JAVA运算符
		else if (tRule.toUpperCase().indexOf("JAVA:") != -1) {
			return this.verifyJAVA(tRule,tValue,checkTransferData);
		}
		
		tInfo = tRule;
		return tInfo;
	}
	/**
	 * 校验结果值不能包含 
	 * 格式：
	 * @param tRule
	 * @param tValue
	 * @return
	 */
	private String verifyJAVA(String tRule, String tValue,TransferData checkTransferData)
	{
		
		String tRes = "";
		/*if(tValue==null)
		{
			return tRes;
		}*/
			
		try {
			//获得需要反射的JAVA类和方法
			String tJavaString = tRule;
			String tClassPath = tJavaString.substring(5,tJavaString.indexOf("@"));
			String tJavaMethod = tJavaString.substring(tJavaString.indexOf("@")+1);
			
			Object tObject = null;
			Class tClass = Class.forName(tClassPath);
			Class[] sClass = new Class[1];
			Object[] dObject = new Object[1];
			//得到tClass中的方法,在后面循环调用
			Method[] tMethod = tClass.getMethods();
			VData sVData = new VData();
			sVData.add(0,checkTransferData);
			Constructor constructor = null; //构造函数
			sClass[0] = VData.class;
			dObject[0] = sVData;
			constructor = tClass.getConstructor(sClass);
			tObject = constructor.newInstance(dObject);
			for(int m=0;m<tMethod.length;m++)
			{
			   Method sMethod = tMethod[m];
			   if(sMethod.getName().equals(tJavaMethod))
			   {
			      logger.debug("当前执行方法名:"+sMethod.getName());
			      Method mMethod = tObject.getClass().getMethod(sMethod.getName(),null);
			      logger.debug("invoke method...");
			      //执行该方法
			      String sResult = (String)mMethod.invoke(tObject,null);
			      logger.debug("invoke method;;;");
			      logger.debug("sResult:"+sResult);
			      //增加查询出错的判断
//			      if(sResult == null || sResult.equals("") || sResult.toUpperCase().equals("ERROR"))
//			      {
//			          logger.debug("计算基础指标错误发生在方法:"+sMethod.getName()+"上");
//			          //return false;
//			      }

			      if(sResult == null)
			          sResult = "";
			      
			      tRes = sResult;
			      break;
			    }
			  }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tRes;
	}
	
	/**
	 * 校验输入内容一定为字母
	 * @param tRule
	 * @param tValue
	 * @return
	 */
	private String verifyCHAR(String tRule, String tValue)
	{
		String tRes = "";
		if(tValue==null)
		{
			return tRes;
		}
		String tReplace = tValue.toUpperCase().replaceAll("[A-Z,0-9]", "");
		logger.debug("tReplace:"+tReplace);
		if(!tReplace.equals(""))
		{
			tRes = "输入值不为字母";
		}
		
		return tRes;
	}
	
	/**
	 * 校验结果值不能包含 
	 * 格式：WITHOUTVALUE:?^?^?
	 * @param tRule
	 * @param tValue
	 * @return
	 */
	private String verifyWITHOUT(String tRule, String tValue)
	{
		String tRes = "";
		if(tValue==null)
		{
			return tRes;
		}
		String[] tCheckIndex;
		String tRuleOperator = tRule.substring(8);
		if(tRuleOperator.indexOf("^")==-1)
		{
			tCheckIndex = new String[1];
			tCheckIndex[0] = tRuleOperator;
		}
		else
		{
			tCheckIndex = PubFun.split(tRuleOperator, "^");
		}
		//String[] tCheckIndex = tRuleOperator.split("^");
		for(int i=0;i<tCheckIndex.length;i++)
		{
			String tCurrIndex = tCheckIndex[i];
			if(tValue.indexOf(tCurrIndex)!=-1)
			{
				tRes = "输入值" + tValue + "包含"+tCurrIndex;
				break;
			}
		}
		return tRes;
	}
	
	/**
	 * 校验结果值是否在规定的范围内
	 * @param tRule
	 * @param tValue
	 * @return
	 */
	private String verifyVALUE(String tRule, String tValue) {
		String tRes = "";
		String tRuleOperatorAndNum = tRule.substring(5);
		// logger.debug(tRuleOperatorAndNum);
		String oper = "";
		String compareValue = "";
		if (tRuleOperatorAndNum.indexOf("=") == -1) {
			// 没有等号
			oper = tRuleOperatorAndNum.substring(0, 1);
			compareValue = tRuleOperatorAndNum.substring(1);
		} else {
			// 包含等号
			if (tRuleOperatorAndNum.substring(1).indexOf("=") == -1) {
				// 等号操作
				oper = tRuleOperatorAndNum.substring(0, 1);
				compareValue = tRuleOperatorAndNum.substring(1);
			} else {
				oper = tRuleOperatorAndNum.substring(0, 2);
				compareValue = tRuleOperatorAndNum.substring(2);
			}
		}
		// logger.debug(oper+":"+len);
		// 首先校验是否是数值,如果不是数值,返回错误信息
		String tCheck = this.verifyDouble(tValue);
		if (!tCheck.equals("")) {
			tRes = "输入值" + tValue + "不符合规定的取值";
			return tRes;
		}
		// logger.debug(oper+":"+len+":"+tValueLenth);
		if (oper.equals("=")
				&& Double.parseDouble(tValue) != Double
						.parseDouble(compareValue)) {
			tRes = "输入值" + tValue + "不等于" + compareValue;
		} else if (oper.equals("<")
				&& Double.parseDouble(tValue) >= Double
						.parseDouble(compareValue)) {
			tRes = "输入值" + tValue + "不小于" + compareValue;
		} else if (oper.equals("<=")
				&& Double.parseDouble(tValue) > Double
						.parseDouble(compareValue)) {
			tRes = "输入值" + tValue + "不小于等于" + compareValue;
		} else if (oper.equals(">")
				&& Double.parseDouble(tValue) <= Double
						.parseDouble(compareValue)) {
			tRes = "输入值" + tValue + "不大于" + compareValue;
		} else if (oper.equals(">=")
				&& Double.parseDouble(tValue) < Double
						.parseDouble(compareValue)) {
			tRes = "输入值" + tValue + "不大于等于" + compareValue;
		}
		return tRes;
	}

	/**
	 * 校验结果是否整数
	 * @param tValue
	 * @return
	 */
	private String verifyINT(String tValue) {
		String tRes = "";
		try {
			if (tValue.indexOf(".") != -1) {
				tRes = "不为整数";
			} else {
				Integer.parseInt(tValue);
			}

		} catch (Exception e) {
			tRes = "不为整数";
		}
		return tRes;
	}

	/**
	 * 校验输入值是否合法
	 * @param tRule
	 * @param tValue
	 * @return
	 */
	private String verifyCode(String tRule, String tValue) {
		String tRes = "";
		String sCodeType = "";
		String sCodeValue = tValue;
		String strResult = "";
		sCodeType = tRule.substring(tRule.indexOf(":") + 1).toLowerCase();
		// logger.debug("sCodeValue:"+sCodeValue+"sCodeType:"+sCodeType);
		// 收集整理数据
		// GlobalInput
		//tongmeng 2008-10-28 add
		//如果输入值为空,直接返回错误信息
		//tongmeng 2009-03-23 modify
		//如果为空,不做校验
		if(tValue==null||tValue.equals("")||tValue.equals("null"))
		{
			//tValue = "空";
			//tRes = "输入数值:" + tValue + "非法";
			return tRes;
		}
		
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";
		tGlobalInput.ComCode = "86";

		TransferData tTransferData = new TransferData();
		CodeQueryUI tCodeQueryUI = new CodeQueryUI();
		VData tData = new VData();
		LDCodeSchema tLDCodeSchema = new LDCodeSchema();
		tLDCodeSchema.setCodeType(sCodeType);
		tData.add(tGlobalInput);
		tTransferData.setNameAndValue("codeValue", sCodeValue);
		tData.add(tTransferData);
		tData.add(tLDCodeSchema);
		tCodeQueryUI.submitData(tData, "QUERY||MAIN");

		if (tCodeQueryUI.mErrors.needDealError()) {
			logger.debug(tCodeQueryUI.mErrors.getFirstError());
		} else {
			tData.clear();
			tData = tCodeQueryUI.getResult();
			strResult = (String) tData.get(0);
			// strValue=StrTool.unicodeToGBK(strResult);
			// logger.debug(strResult);
		}
		if (!strResult.equals("") && strResult.indexOf("未查询到相关数据") == -1) {
			boolean has = false;
			// for(strResult.)
			// strResult = strResult.replaceFirst("|","#");
			// strResult = strResult.replaceAll("^","-");
			// String[] arrRecord = strResult.split("-"); //拆分记录，形成返回的数组
			String[] arrRecord = PubFun.split(strResult, "^");
			for (int i = 1; i < arrRecord.length; i++) {
				// 0#3-0#男###-1#女###-2#不详###
				if (arrRecord[i].substring(0, arrRecord[i].indexOf("|"))
						.equals(tValue)) {
					has = true;
					break;
				}
			}
			if (!has) {
				if (tValue == null) {
					tValue = "空";
				}
				tRes = "输入数值:" + tValue + "非法";
			}

		} else {
			if (tValue == null) {
				tValue = "空";
			}
			tRes = "输入数值:" + tValue + "非法";
		}
		return tRes;
	}

	/**
	 * 校验结果长度是否合法
	 * 
	 * @param tRule
	 * @param tValue
	 * @return
	 */
	private String verifyLEN(String tRule, String tValue) {
		String tRes = "";
		String tRuleOperatorAndNum = tRule.substring(3);
		// logger.debug(tRuleOperatorAndNum);
		//tongmeng 2009-03-23 modify
		//如果为空,直接返回.
		if(tValue==null||tValue.equals("")||tValue.equals("null"))
		{
			return "";
		}
		String oper = "";
		String len = "";
		if (tRuleOperatorAndNum.indexOf("=") == -1) {
			// 没有等号
			oper = tRuleOperatorAndNum.substring(0, 1);
			len = tRuleOperatorAndNum.substring(1);
		} else {
			// 包含等号
			if (tRuleOperatorAndNum.substring(1).indexOf("=") == -1) {
				// 等号操作
				oper = tRuleOperatorAndNum.substring(0, 1);
				len = tRuleOperatorAndNum.substring(1);
			} else {
				oper = tRuleOperatorAndNum.substring(0, 2);
				len = tRuleOperatorAndNum.substring(2);
			}
		}
		// logger.debug(oper+":"+len);
		int tValueLenth = 0;
		if (tValue == null) {
			tValueLenth = 0;
		} else {
			tValueLenth = tValue.length();
		}
		// logger.debug(oper+":"+len+":"+tValueLenth);
		if (oper.equals("=") && tValueLenth != Integer.parseInt(len)) {
			tRes = "长度不等于" + len;
		} else if (oper.equals("<") && tValueLenth >= Integer.parseInt(len)) {
			tRes = "长度不小于" + len;
		} else if (oper.equals("<=") && tValueLenth > Integer.parseInt(len)) {
			tRes = "长度不小于等于" + len;
		} else if (oper.equals(">") && tValueLenth <= Integer.parseInt(len)) {
			tRes = "长度不大于" + len;
		} else if (oper.equals(">=") && tValueLenth < Integer.parseInt(len)) {
			tRes = "长度不大于等于" + len;
		}

		return tRes;
	}

	/**
	 * 校验结果是否为空
	 * 
	 * @param tValue
	 * @return
	 */
	private String verifyNULL(String tValue) {
		String tRes = "";
		if (!(tValue == null || (!tValue.equals("") && tValue.equals("null")))) {
			tRes = "不为空";
		}
		return tRes;
	}

	/**
	 * 校验结果是否不为空
	 * 
	 * @param tValue
	 * @return
	 */
	private String verifyNOTNULL(String tValue) {
		String tRes = "";
		if (tValue == null || tValue.equals("")) {
			tRes = "为空";
		}
		return tRes;
	}

	/**
	 * 校验结果是否日期型
	 * 
	 * @param tValue
	 * @return
	 */
	private String verifyDATE(String tValue) {
		String tRes = "";
		Date tDate = null;
		SimpleDateFormat df;
		ExeSQL tExeSQL=new ExeSQL();
		try {
			if (tValue.indexOf("-") != -1) {
				df = new SimpleDateFormat("yyyy-MM-dd");
				tDate = df.parse(tValue);
				String dateStr2=df.format(tDate);
				String tFormatDateSQL ="select to_char(to_date('"+"?tValue?"+"','yyyy-MM-dd'),'yyyy-MM-dd') from dual";
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv3.sql();
				sqlbv3.put("tValue", tValue);
				String tFormatDate=tExeSQL.getOneValue(sqlbv3);
				if(!dateStr2.equals(tFormatDate)){
					tRes = "日期不合法";
				}
			} else {
				df = new SimpleDateFormat("yyyyMMdd");
				tDate = df.parse(tValue);
			}
		} catch (Exception e) {
			// @@错误处理
			tRes = "不为日期类型";
		}

		return tRes;
	}

	/**
	 * 校验结果是否Double
	 * 
	 * @param tValue
	 * @return
	 */
	private String verifyDouble(String tValue) {
		String tRes = "";
		try {
			Double.parseDouble(tValue);
		} catch (Exception e) {
			tRes = "不为数字类型";
		}

		return tRes;
	}
}
