/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.pubfun;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.sinosoft.ibrms.RuleUI;
import com.sinosoft.ibrms.SQLTaskResult;
import com.sinosoft.ibrms.bom.BOMAppnt;
import com.sinosoft.ibrms.bom.BOMCont;
import com.sinosoft.ibrms.bom.BOMInsured;
import com.sinosoft.lis.config.CalCodeCacheService;
import com.sinosoft.lis.db.LMCalModeDB;
import com.sinosoft.lis.schema.LMCalFactorSchema;
import com.sinosoft.lis.schema.LMCalModeSchema;
import com.sinosoft.lis.vschema.LMCalFactorSet;
import com.sinosoft.service.CovBase;
import com.sinosoft.service.ServiceA;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/*
 * <p>Title: 保费计算类 </p> <p>Description: 通过传入的保单信息和责任信息构建出保费信息和领取信息 </p> <p>Copyright:
 * Copyright (c) 2002</p> <p>Company: sinosoft</p> @author HST
 * 
 * @version 1.0 @date 2002-07-01
 */
public class Calculator extends CovBase {
private static Logger logger = Logger.getLogger(Calculator.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 计算需要用到的保单号码 */
	public String PolNo;

	
	/**
	 * 各种要素存放的琏表 1--基本要素、和常量要素相同，但是优先级最低 2--扩展要素，根据SQL语句从新计算 3--常量要素（只取默认值）
	 */
	private LMCalFactorSet mCalFactors1 = new LMCalFactorSet(); // 存放基本要素

	public LMCalFactorSet mCalFactors = new LMCalFactorSet();

	// @Field
	// 计算编码
	private String CalCode = "";
	// 算法对应SQL语句所在表结构
	private LMCalModeSchema mLMCalMode = new LMCalModeSchema();

	private CalCodeCacheService calCodeCacheService = new CalCodeCacheService();
	
	//tongmeng 2010-12-15 modify
	//支持计算型规整引擎的调用
	private List mBOMList = new ArrayList();
	
	//tongmeng 2011-05-23 modify
	//Calculator修改成支持批量调用
	//批量调用的算法编码
	private List mBatchCalCodeList = new ArrayList();
	//tongmeng 2011-05-25
	private HashMap mCalculatorResult =  new HashMap();
	private VData mInputData = new VData();
	
	
	private TransferData mParams = new TransferData();
	
	/** 数据库连接 * */
	private Connection conn = null;
	/**
	 * mflag = true: 传入Connection mflag = false: 不传入Connection
	 */
	private boolean mflag = false;
	
	/*
	 * 多线程调用时使用
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		if(this.mInputData.get(0)!=null)
		{
			this.mBOMList = (List)mInputData.get(0);
		}
		if(this.mInputData.get(1)!=null)
		{
			this.mCalFactors1 = (LMCalFactorSet)this.mInputData.get(1);
		}
		String tCalCode = (String)this.mInputData.get(2);
		this.setCalCode(tCalCode);
		String tValue = this.calculate();
		if(tValue==null)
		{
			tValue = "";
		}
		//Map tResultMap = new HashMap();
		//tResultMap.put(tCalCode, tValue);
		
		this.mServiceResult.setReturnList(tCalCode,tValue);
		this.close();		
	}
	
	/**
	 * 多线程调用时使用
	 */
	public void setObject(Object tObject) {
		//多线程的外部参数条件
		//mBatchCalCodeList = (ArrayList) tObject;
		mInputData = (VData)tObject;
	}
	/**
	 * 增加基本要素
	 * 
	 * @param cFactorCode
	 *            要素的编码
	 * @param cFactorValue
	 *            要素的数据值
	 */
	public void addBasicFactor(String cFactorCode, String cFactorValue) {
		LMCalFactorSchema tS = new LMCalFactorSchema();
		tS.setFactorCode(cFactorCode);
		tS.setFactorDefault(cFactorValue);
		tS.setFactorType("1");
		mCalFactors1.add(tS);
	}
	
	private LMCalFactorSet getBasicCalFactor()
	{
		return this.mCalFactors1;
	}

	// @Method
	public void setCalCode(String tCalCode) {
		CalCode = tCalCode;
	}
	
	/**
	 * 设置BOM对象
	 * @param tBOMList
	 */
	public void setBOMList(List tBOMList) {
		this.mBOMList = tBOMList;
	}
	
	/**
	 * 设置批量调用的算法编码
	 * @param tBatchList
	 */
	public void setBatchCalCodeList(List tBatchList)
	{
		this.mBatchCalCodeList = tBatchList;
	}

	/**
	 * 返回计算的结果
	 * @return
	 */
	public HashMap getCalResult()
	{
		return this.mCalculatorResult;
	}
	/**
	 * 公式计算函数
	 * 
	 * @return: String 计算的结果，只能是单值的数据（数字型的转换成字符型）
	 * @author: YT
	 */
	public String calculate() {

		logger.debug("start calculate++++++++++++++");
		if (!checkCalculate()) {
			return "0";
		}
		
		try {
			//tongmeng 2010-12-15 modify 修改为支持解析规整引擎的SQL
			//暂时定义根据CalCode 以RU开头的为需要调用规整引擎的SQL
			if(this.CalCode.substring(0,2).equals("RU")&&this.mBOMList.size()>0)
			{
				String res = calByRuleEngine();
				return res;
			}
			
			
			return calByCalculator();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			CError.buildErr(this, e.toString());
			e.printStackTrace();
			return "0";
		}
	}
	/**
	 * 调用计算引擎计算
	 * @return
	 */
	private String calByCalculator() {
		// 取得数据库中计算要素
		LMCalFactorSet tLMCalFactorSet = calCodeCacheService.getLMCalFactorSet(CalCode);
		logger.debug("calCode :" + CalCode);

		LMCalFactorSet tNewLMCalFactorSet = new LMCalFactorSet();
		if(tLMCalFactorSet != null && tLMCalFactorSet.size() > 0){
			for (int i = 1; i <= tLMCalFactorSet.size(); i++) {
				try {
					LMCalFactorSchema tLMCalFactorSchema = 
						(LMCalFactorSchema) tLMCalFactorSet.get(i).clone();
					tNewLMCalFactorSet.add(tLMCalFactorSchema);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
					return "0";
				}
			}
		}

		mCalFactors.add(tNewLMCalFactorSet);
		// 增加基本要素
		mCalFactors.add(mCalFactors1);
		// 解释计算要素
		if (!interpretFactors()) {
			return "0";
		}

		// 读取SQL语句
		if (!getSQL()) {
			return "0";
		}

		// 解释SQL语句中的变量
		if (!interpretFactorInSQL()) {
			return "0";
		}
		// 执行SQL语句
		logger.debug("start execute SQL.....");
		return executeSQL();
	}
	
	/**
	 * 调用规则引擎计算
	 * @return
	 */
	private String calByRuleEngine() {
		RuleUI rule = new RuleUI();
		//tongmeng 2011-06-08 modify
		//校验型规则也可以通过Calculator调用
		//String res = rule.AutoUWIndUIForList(this.mBOMList, "99",this.CalCode);
		String res = rule.AutoUWIndUIForList(this.mBOMList, "",this.CalCode);
		SQLTaskResult rs=null;
		List results = rule.getResultList();
		for(int i=0;i<results.size();i++)
		{
			 rs=(SQLTaskResult)results.get(i);
			if(rs.getErrors().getErrorCount()!=0)
			{
				//t.add(rs);
				results.remove(i);
				
			}
		}
		this.mCalculatorResult.put(CalCode, results);
		
		return res;
	}

	/**
	 * 执行SQL语句
	 * 
	 * @return String
	 */
	private String executeSQL() {
		String tReturn = "0";
		ExeSQL tExeSQL = null;
		
		if(mflag)
		{
			tExeSQL = new ExeSQL(conn);
		}
		else
		{
			tExeSQL = new ExeSQL();
		}
		VData tParseResult = new VData();
		String mSQL = mLMCalMode.getCalSQL();
		tParseResult.add(mSQL);
		
		tParseResult.add(mParams);
		//tReturn = tExeSQL.getOneValue(mLMCalMode.getCalSQL());
		tReturn = tExeSQL.getOneValue(tParseResult);
		if (tExeSQL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tExeSQL.mErrors);
			CError.buildErr(this,"执行SQL语句：" + mLMCalMode.getCalCode() + "失败!");
			return "0";
		}
		return tReturn;
	}

	/**
	 * 解释SQL语句中的变量
	 * 
	 * @return boolean
	 */
	private boolean interpretFactorInSQL() {
		String tSql, tStr = "", tStr1 = "";
		tSql = mLMCalMode.getCalSQL();
		logger.debug(tSql);
		// tSql=tSql.toLowerCase() ;
		
		
		//add by jiaqiangli 2009-02-02 问号的不匹配时（个数应该为偶数）下面代码会陷入死循环
		//注意此处不要用split方法
		int tQuestionMarkCount = 0;
		tQuestionMarkCount = tSql.length() - tSql.replaceAll("\\?","").length();
		if (tQuestionMarkCount % 2 != 0) {
			CError.buildErr(this,"lmcalmode中SQL语句的?问号个数不匹配，请核查");
			return false;
		}
		//add by jiaqiangli 2009-02-02 问号的不匹配时（个数应该为偶数）下面代码会陷入死循环
		
		//修改成绑定变量执行的方式
		//使用正则表达式进行替换
		String tSourceSQL = tSql;
		Pattern pattern = Pattern.compile("(\\'*\\?.+?\\?\\'*)");
		Matcher matcher = pattern.matcher(tSourceSQL);
		int i = 0;
		TransferData tParams = new TransferData();
		while (matcher.find()) {
			//当前查到的内容
			String tCurrentGroup = matcher.group();
			logger.debug("tCurrentGroup:"+tCurrentGroup);
			//
			String tKeyCode = " & ";
			String tKeyIdx = tCurrentGroup.replaceAll("([\\'\\?])", "");
			String tReplaceStr = "";
			String tReplaceCode = "";
			
			String tParamType = "";
			String tParamValue = "";
			tParamValue = getValueByName(tKeyIdx);
			if(tCurrentGroup.indexOf("'")!=-1)
			{
				tParamType = "String";
			}
			else
			{
				try
				{
					Integer.parseInt(tParamValue);
					tParamType = "int";
				}
				catch(Exception e)
				{
					tParamType = "double";
				}
				
			}
			
			
			tReplaceCode = tKeyCode;
			tReplaceStr = matcher.replaceFirst(tReplaceCode);
			
			tParams.setNameAndValue(String.valueOf(i), tParamType + ":"
						+ tParamValue);
			
			// String test = matcher.replaceFirst("test");
			// logger.debug("tReplaceStr:" + tReplaceStr);
			matcher = pattern.matcher(tReplaceStr);
			tSql = tReplaceStr;
			i++;
		}
		tSql = tSql.replaceAll("&","?");
		logger.debug("final tSql:"+tSql);
		
		for (int m = 0; m < tParams.getValueNames().size(); m++) {
			logger.debug(tParams.getValueByName((String) tParams
					.getValueNames().get(m)));
		}
		logger.debug(tSql);
		mLMCalMode.setCalSQL(tSql);
		this.mParams = tParams;
		return true;
	}

	/**
	 * 计算子要素的值
	 * 
	 * @param cF
	 *            LMCalFactorSchema
	 * @return String
	 */
	private String calSubFactors(LMCalFactorSchema cF) {
		int i, iMax;
		String tReturn = "";
		LMCalFactorSchema tC = new LMCalFactorSchema();
		Calculator tNewC = new Calculator();
		iMax = mCalFactors.size();
		for (i = 1; i <= iMax; i++) {
			tC = mCalFactors.get(i);
			// 如果是基本要素或常量要素，则传入下一个要素中
			if (tC.getFactorType().toUpperCase().equals("1")
					|| tC.getFactorType().toUpperCase().equals("3")) {
				tNewC.mCalFactors.add(tC);
			}
		}
		tNewC.setCalCode(cF.getFactorCalCode());
		logger.debug("----SubFactor---calcode = " + cF.getFactorCalCode());
		tReturn = String.valueOf(tNewC.calculate());
		logger.debug("----SubFactor = " + tReturn);
		// 如果有错误，则将错误拷贝到上一要素,并且返回"0"
		if (tNewC.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tNewC.mErrors);
			tReturn = "0";
		}
		return tReturn;
	}

	/**
	 * 读取SQL语句
	 * 
	 * @return boolean
	 */
	private boolean getSQL() {
		LMCalModeDB lmCalModeDB = new LMCalModeDB();
		lmCalModeDB.setCalCode(CalCode);
		lmCalModeDB.getInfo();
		LMCalModeSchema tLMCalModeSchema = lmCalModeDB.getSchema();
		if (tLMCalModeSchema == null) {
			CError.buildErr(this,"得到" + CalCode + "的SQL语句时出错。");
			return false;
		}
		mLMCalMode.setSchema(tLMCalModeSchema);
		return true;
	}

	/**
	 * 解释要素连表中的非变量要素
	 * 
	 * @return boolean
	 */
	private boolean interpretFactors() {
		int i, iMax;
		LMCalFactorSchema tC = new LMCalFactorSchema();
		iMax = mCalFactors.size();
		for (i = 1; i <= iMax; i++) {
			tC = mCalFactors.get(i);
			// 如果是扩展要素，则解释该扩展要素
			if (tC.getFactorType().toUpperCase().equals("2")) {
				tC.setFactorDefault(calSubFactors(tC));
				// 如果在计算子要素的时候发生错误，则返回false
				if (this.mErrors.needDealError()) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 校验计算的输入是否足够
	 * 
	 * @return boolean 如果不正确返回false
	 */
	private boolean checkCalculate() {
		if (CalCode == null || CalCode.equals("")) {
			// @@错误处理
			CError.buildErr(this,"计算时必须有计算编码。");
			return false;
		}
		return true;
	}

	/**
	 * 根据变量名得到变量的值
	 * 
	 * @param cVarName
	 *            String
	 * @return String 如果不正确返回"",否则返回变量值
	 */
	private String getValueByName(String cVarName) {
		cVarName = cVarName.toLowerCase();
		int i, iMax;
		String tReturn = "";
		LMCalFactorSchema tC = new LMCalFactorSchema();
		iMax = mCalFactors.size();
		for (i = 1; i <= iMax; i++) {
			tC = mCalFactors.get(i);
			if (tC.getFactorCode().toLowerCase().equals(cVarName)) {
				tReturn = tC.getFactorDefault();
				// 如果替换的值本身含有?，则需要将其去掉，以免替换后形成新的问号变量 hezy
				if (tReturn.indexOf("?") != -1) {
					return tReturn.replaceAll("?", "");
				}
				// hezy 2005-07-13
				break;
			}
		}
		return tReturn;
	}

	/**
	 * Kevin 2003-08-20 得到解析过的SQL语句，而不是SQL语句执行后的值。
	 * 
	 * @return String
	 */
	public String getCalSQL() {
		if (!checkCalculate()) {
			return "0";
		}

		LMCalFactorSet tLMCalFactorSet = calCodeCacheService.getLMCalFactorSet(CalCode);

		if (tLMCalFactorSet == null) {
			return "0";
		}

		mCalFactors.add(tLMCalFactorSet);
		// 增加基本要素
		mCalFactors.add(mCalFactors1);
		// 解释计算要素
		if (!interpretFactors()) {
			return "0";
		}

		// 读取SQL语句
		if (!getSQL()) {
			return "0";
		}

		// 解释SQL语句中的变量
		if (!interpretFactorInSQL()) {
			return "0";
		}

		// 返回解析过的SQL语句
		return mLMCalMode.getCalSQL();
	}
	
	
	//tongmeng 2012-02-10 add
	//返回绑定变量的参数
	public TransferData getCalSQLParams()
	{
		return this.mParams;
	}

	
	/**
	 * 批量调用计算引擎计算 
	 * @return
	 */
	public List batchComputing()
	{
		//tongmeng 2011-05-24 modify
		//修改成支持多线程调用
		List tArrayList = new ArrayList();
		//没有需要批量调用的算法
		if(mBatchCalCodeList.size()<=0)
		{
			return null;
		}
		
		
		Vector mTaskWaitList = new Vector();
		for (int i = 0; i < mBatchCalCodeList.size(); i++) {
			
                // 准备传输数据 VData
				VData tVData = new VData();
				tVData.add(this.mBOMList);
				tVData.add(this.mCalFactors1);
				tVData.add((String)mBatchCalCodeList.get(i));
				mTaskWaitList.add(tVData);
		}
		int tThreadCount = 5; 
		if(mBatchCalCodeList.size()<tThreadCount)
		{
			tThreadCount = mBatchCalCodeList.size();
		}
		logger.debug("tThreadCount:"+tThreadCount);
		ServiceA tService = new ServiceA("com.sinosoft.lis.pubfun.Calculator", mTaskWaitList, tThreadCount, 1);
		
		tService.start();
		Map tResultMap = tService.getResultList();
		for(int i=0;i<mBatchCalCodeList.size();i++)
		{
			String tCurrentCalCode =  (String)mBatchCalCodeList.get(i);
			String tValue = (String)tResultMap.get(tCurrentCalCode);
			String[] tReturnValue = new String[2];
			tReturnValue[0] = tCurrentCalCode;
			tReturnValue[1] = tValue;
			tArrayList.add(tReturnValue);
		}
		
		//tongmeng 2011-05-25 
		//因为执行效率问题,暂时先不使用多线程
		/*
		for(int i=0;i<mBatchCalCodeList.size();i++)
		{
			try {
				String tCurrentCalCode =  (String)mBatchCalCodeList.get(i);
				this.setCalCode(tCurrentCalCode);
				
				String tValue = this.calculate();
				
				String[] tReturnValue = new String[2];
				tReturnValue[0] = tCurrentCalCode;
				tReturnValue[1] = tValue;
				
				tArrayList.add(tReturnValue);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
		}*/
		
		
		return tArrayList;
	}
	
	/**
	 * 批量计算,顺序执行
	 * @return
	 */
	public List batchComputingSeq()
	{
		//tongmeng 2011-05-24 modify
				//修改成支持多线程调用
		List tArrayList;
		tArrayList = new ArrayList();
		try {
			conn = DBConnPool.getConnection();
			mflag = true;
			
			if (conn == null) {
				// @@错误处理
				CError.buildErr(this, "数据库连接失败");
				return null;
			}
			
			
			//没有需要批量调用的算法
			if(mBatchCalCodeList.size()<=0)
			{
				return null;
			}
			
			
			
			//tongmeng 2011-05-25 
			//传入连接,则使用单线程调用
			
			for(int i=0;i<mBatchCalCodeList.size();i++)
			{
				try {
					String tCurrentCalCode =  (String)mBatchCalCodeList.get(i);
					this.setCalCode(tCurrentCalCode);
					
					String tValue = this.calculate();
					
					String[] tReturnValue = new String[2];
					tReturnValue[0] = tCurrentCalCode;
					tReturnValue[1] = tValue;
					
					tArrayList.add(tReturnValue);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
				
			}
			
			conn.close();
			conn=null;
			mflag = false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		finally
		{
			try {
				if(conn!=null)
				{
					conn.close();
					conn=null;
					mflag = false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return tArrayList;
	}
	
	
	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		 Calculator tC=new Calculator();
		// LMCalFactorSchema tLMCalFactorSchema=new LMCalFactorSchema();
		// tC.addBasicFactor("PolNo","00000120021100000000");
		// tC.setCalCode("001001") ;
		// logger.debug(tC.calculate());
		// if(tC.mErrors.needDealError())
		// logger.debug(tC.mErrors.getFirstError());
		
		String tSQL_test = "select rulename from lrtemplate where 1=1 " + " and rulename like 'RU%'";
		SQLwithBindVariables sqlbv_test = new SQLwithBindVariables();
		sqlbv_test.sql(tSQL_test);
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		List tCalList = new ArrayList();
		tSSRS = tExeSQL.execSQL(sqlbv_test);
		for(int i=1;i<=tSSRS.getMaxRow();i++)
		{
			tCalList.add(tSSRS.GetText(i, 1));
		}
		tC.setBatchCalCodeList(tCalList);
		
		BOMCont cont = new BOMCont();
		cont.setContNo("ew34");
		cont.setAmnt(new Double(2345));
		cont.setAccoBodyCheck("RRR");
		
		BOMAppnt tAppnt = new BOMAppnt();
		tAppnt.setAppntAge(new Double(20));
		BOMInsured[] insureds = new  BOMInsured[3];
		
		BOMInsured insured = new BOMInsured();
		insured.setInsuredAppAge(new Double(22));
		insureds[0] = insured;
		
		List list = new ArrayList();
		list.add(cont);
		list.add(tAppnt);
		list.add(insureds);
		
		tC.setBOMList(list);
		Date start = new Date();
		List tResult = tC.batchComputing();
		if(tResult!=null)
		{
			for(int i=0;i<tResult.size();i++)
			{
				String[] t = (String[])tResult.get(i);
				logger.debug("calcode:"+t[0]+":Result:"+t[1]);
				SQLTaskResult tt = (SQLTaskResult)((ArrayList)tC.getCalResult().get(t[0])).get(0);
				logger.debug(tt.getMessageByLanguage("zh")+":"+tt.getMessageByLanguage("en"));
			}
		}
		
		logger.debug("$$$$:"+tC.getCalResult().size());
		Date end = new Date();
		logger.debug("Time:"+(end.getTime() - start.getTime()));
	}
}
