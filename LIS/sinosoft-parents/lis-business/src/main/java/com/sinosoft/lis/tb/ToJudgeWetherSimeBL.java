package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.util.Hashtable;

import com.sinosoft.lis.db.LBPOAppntDB;
import com.sinosoft.lis.db.LBPOBnfDB;
import com.sinosoft.lis.db.LBPOContDB;
import com.sinosoft.lis.db.LBPOCustomerImpartDB;
import com.sinosoft.lis.db.LBPODataDictionaryDB;
import com.sinosoft.lis.db.LBPOInsuredDB;
import com.sinosoft.lis.db.LBPOPerInvestPlanDB;
import com.sinosoft.lis.db.LBPOPolDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LBPOAppntSchema;
import com.sinosoft.lis.schema.LBPOBnfSchema;
import com.sinosoft.lis.schema.LBPOContSchema;
import com.sinosoft.lis.schema.LBPOCustomerImpartSchema;
import com.sinosoft.lis.schema.LBPODataDetailErrorSchema;
import com.sinosoft.lis.schema.LBPODataDictionarySchema;
import com.sinosoft.lis.schema.LBPOInsuredSchema;
import com.sinosoft.lis.schema.LBPOPerInvestPlanSchema;
import com.sinosoft.lis.schema.LBPOPolSchema;
import com.sinosoft.lis.vschema.LBPOAppntSet;
import com.sinosoft.lis.vschema.LBPOBnfSet;
import com.sinosoft.lis.vschema.LBPOContSet;
import com.sinosoft.lis.vschema.LBPOCustomerImpartSet;
import com.sinosoft.lis.vschema.LBPODataDictionarySet;
import com.sinosoft.lis.vschema.LBPOInsuredSet;
import com.sinosoft.lis.vschema.LBPOPerInvestPlanSet;
import com.sinosoft.lis.vschema.LBPOPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class ToJudgeWetherSimeBL {
private static Logger logger = Logger.getLogger(ToJudgeWetherSimeBL.class);

	private String mContNo = "";

	private String SameFlag = "3";

	private MMap map = new MMap();

	private String theCurrentDate = PubFun.getCurrentDate();

	private String theCurrentTime = PubFun.getCurrentTime();

	private String mOperator = "";

	public CErrors mErrors = new CErrors();

	private VData mInputData = new VData();

	private TransferData mTransferData = new TransferData();
	//每次校验只产生一个序列号
	//private String mSerNo = PubFun1.CreateMaxNo("BPOSN", 20);
	public ToJudgeWetherSimeBL() {
	}
	/**
	 * 功能：校验一码二码的录入内容是否一致，如果一致SameFlag=1生成XML文件
	 * 不一致SameFlag=2，将不一致的字段名、内容、操作员等信息插入LBPODataDetailError中，工作流流入三码
	 * 
	 * @param 
	 *            
	 * @return 
	 */
	public boolean submitData(String contNo, String Operator) {

		if (getInputData(contNo, Operator) == false) {
			return false;
		}

		if (checkData() == false) {
			return false;
		}

		if (dealData() == false) {
			return false;
		}

		this.prepareOutputData();

		return true;
	}

	private boolean getInputData(String contNo, String Operator) {
		mContNo = contNo;
		mOperator = Operator;
		return true;
	}

	private boolean checkData() {

		return true;
	}
	/** 
	 * 对两次录入的信息进行对比，将不一致的信息插入到LBPODataDetailError表中，并将SimeFlag置为“1”
	 * */
	private boolean dealData() {
		SameFlag = "1";
		Hashtable tHashtable = new Hashtable();
	//	Hashtable tHashtable2 = new Hashtable();
		LBPODataDictionaryDB tLBPODataDictionaryDB = new LBPODataDictionaryDB();
		LBPODataDictionarySet tLBPODataDictionarySet = new LBPODataDictionarySet();
		String tSql = "select * from LBPODataDictionary where checktype='01' ";
		SQLwithBindVariables sqlbva = new SQLwithBindVariables();
		sqlbva.sql(tSql);
		tLBPODataDictionarySet = tLBPODataDictionaryDB.executeQuery(sqlbva);
		
		/**查询字典表，将需要校验的字段及错误信息放入Hashtable中*/
		for (int i = 1; i <= tLBPODataDictionarySet.size(); i++) {
			LBPODataDictionarySchema tLBPODataDictionarySchema = new LBPODataDictionarySchema();
			tLBPODataDictionarySchema = tLBPODataDictionarySet.get(i);
			String tKey = tLBPODataDictionarySchema.getTableName()+"."+tLBPODataDictionarySchema.getContrasCol();
			tHashtable.put(tKey, tLBPODataDictionarySchema);
		//	tHashtable2.put(tKey, 
		//			tLBPODataDictionarySchema.getErrorCode());
		}
		
		
		//开始校验两次录入的一致性.
		LBPOContSet tLBPOContSet1 = new LBPOContSet();
		LBPOContDB tLBPOContDB1 = new LBPOContDB();
		tLBPOContDB1.setContNo(this.mContNo);
		tLBPOContDB1.setInputNo("1");
		tLBPOContSet1 = tLBPOContDB1.query();
		if(tLBPOContSet1.size()<=0)
		{
			CError.buildErr(this,"保单数据结构缺失");
			return false;
		}
		for(int i=1;i<=tLBPOContSet1.size();i++)
		{
			LBPOContSchema tLBPOContSchema1 = new LBPOContSchema();
			tLBPOContSchema1.setSchema(tLBPOContSet1.get(i));
			
			LBPOContDB tLBPOContDB2 = new LBPOContDB();
			LBPOContSet tLBPOContSet2 = new LBPOContSet();
			LBPOContSchema tLBPOContSchema2 = new LBPOContSchema();
			tLBPOContDB2.setContNo(tLBPOContSchema1.getContNo());
			tLBPOContDB2.setFillNo(tLBPOContSchema1.getFillNo());
			tLBPOContDB2.setInputNo("2");
			tLBPOContSet2 = tLBPOContDB2.query();
			if(tLBPOContSet2.size()<=0||tLBPOContSet2.size()>1)
			{
				CError.buildErr(this,"保单数据结构缺失");
				return false;
			}
			tLBPOContSchema2 = tLBPOContSet2.get(1);
			for(int n=1;n<=tLBPOContSchema1.getFieldCount();n++)
			{
				String tFieldName = tLBPOContSchema1.getFieldName(n);
				//String tErrCode = (String)tHashtable2.get(tFieldName);
				//String tErrMsg = (String)tHashtable.get(tFieldName);
				String tClassName = tLBPOContSchema1.getClass().getName();
				String tCheckName = tClassName .substring(tClassName.lastIndexOf(".") + 1);
				tCheckName = tCheckName.substring(0, tCheckName.lastIndexOf("Schema"));
				String tFieldKey =  tCheckName + "." + tFieldName ;
				LBPODataDictionarySchema tLBPODataDictionarySchema = new LBPODataDictionarySchema();
				String tErrCode;
				String tErrMsg;
				if(tHashtable.containsKey(tFieldKey))
				{
					tLBPODataDictionarySchema = (LBPODataDictionarySchema)tHashtable.get(tFieldKey);
					tErrCode = tLBPODataDictionarySchema.getErrorCode();
					tErrMsg = (String)tLBPODataDictionarySchema.getMSG();
					String tFieldValue1 = tLBPOContSchema1.getV(n);
					String tFieldValue2 = tLBPOContSchema2.getV(n);
					boolean equalFlag = true;
					if(tFieldValue1==null&&tFieldValue2==null)
					{
						//两个都为空,认为录入一致.
					}
					else
					{
						//至少有一个不为空
						if(tFieldValue1==null||tFieldValue2==null)
						{
							//如果有一个为空,那么认为不等
							equalFlag = false;
						}
						else if(!tFieldValue1.equals(tFieldValue2))
						{
							//两个都不为空,比较是否相等
							equalFlag = false;
						}
							
					}
					
					//如果比较结果是不等,记录错误信息
					if(!equalFlag)
					{
						this.makeLBPODataDetailError("1", tErrCode, tLBPOContSchema1.getOperator(),
								tFieldValue1, tFieldName, tErrMsg,"LBPOCont",tLBPOContSchema1.getFillNo());
						this.makeLBPODataDetailError("2", tErrCode, tLBPOContSchema2.getOperator(),
								tFieldValue2, tFieldName, tErrMsg,"LBPOCont",tLBPOContSchema2.getFillNo());
					}
				}
			}
		}
		
		//校验投保人信息
		LBPOAppntSet tLBPOAppntSet1 = new LBPOAppntSet();
		LBPOAppntDB tLBPOAppntDB1 = new LBPOAppntDB();
		tLBPOAppntDB1.setContNo(this.mContNo);
		tLBPOAppntDB1.setInputNo("1");
		tLBPOAppntSet1 = tLBPOAppntDB1.query();
		
		for(int i=1;i<=tLBPOAppntSet1.size();i++)
		{
			LBPOAppntSchema tLBPOAppntSchema1 = new LBPOAppntSchema();
			tLBPOAppntSchema1.setSchema(tLBPOAppntSet1.get(i));
			
			LBPOAppntDB tLBPOAppntDB2 = new LBPOAppntDB();
			LBPOAppntSet tLBPOAppntSet2 = new LBPOAppntSet();
			LBPOAppntSchema tLBPOAppntSchema2 = new LBPOAppntSchema();
			tLBPOAppntDB2.setContNo(tLBPOAppntSchema1.getContNo());
			tLBPOAppntDB2.setFillNo(tLBPOAppntSchema1.getFillNo());
			tLBPOAppntDB2.setInputNo("2");
			tLBPOAppntSet2 = tLBPOAppntDB2.query();
			if(tLBPOAppntSet2.size()<=0||tLBPOAppntSet2.size()>1)
			{
				CError.buildErr(this,"保单投保人数据结构缺失");
				return false;
			}
			tLBPOAppntSchema2 = tLBPOAppntSet2.get(1);
			for(int n=1;n<=tLBPOAppntSchema1.getFieldCount();n++)
			{
				String tFieldName = tLBPOAppntSchema1.getFieldName(n);
				//String tErrCode = (String)tHashtable2.get(tFieldName);
				//String tErrMsg = (String)tHashtable.get(tFieldName);
				String tClassName = tLBPOAppntSchema1.getClass().getName();
				String tCheckName = tClassName .substring(tClassName.lastIndexOf(".") + 1);
				tCheckName = tCheckName.substring(0, tCheckName.lastIndexOf("Schema"));
				String tFieldKey =  tCheckName + "." + tFieldName ;
				LBPODataDictionarySchema tLBPODataDictionarySchema = new LBPODataDictionarySchema();
				tLBPODataDictionarySchema = (LBPODataDictionarySchema)tHashtable.get(tFieldKey);
				String tErrCode;
				String tErrMsg;
				if(tHashtable.containsKey(tFieldKey))
				{
					tLBPODataDictionarySchema = (LBPODataDictionarySchema)tHashtable.get(tFieldKey);
					tErrCode = tLBPODataDictionarySchema.getErrorCode();
					tErrMsg = (String)tLBPODataDictionarySchema.getMSG();
					String tFieldValue1 = tLBPOAppntSchema1.getV(n);
					String tFieldValue2 = tLBPOAppntSchema2.getV(n);
					boolean equalFlag = true;
					if(tFieldValue1==null&&tFieldValue2==null)
					{
						//两个都为空,认为录入一致.
					}
					else
					{
						//至少有一个不为空
						if(tFieldValue1==null||tFieldValue2==null)
						{
							//如果有一个为空,那么认为不等
							equalFlag = false;
						}
						else if(!tFieldValue1.equals(tFieldValue2))
						{
							//两个都不为空,比较是否相等
							equalFlag = false;
						}
							
					}
					
					//如果比较结果是不等,记录错误信息
					if(!equalFlag)
					{
						this.makeLBPODataDetailError("1", tErrCode, tLBPOAppntSchema1.getOperator(),
								tFieldValue1, tFieldName, tErrMsg,"LBPOAppnt",tLBPOAppntSchema1.getFillNo());
						this.makeLBPODataDetailError("2", tErrCode, tLBPOAppntSchema2.getOperator(),
								tFieldValue2, tFieldName, tErrMsg,"LBPOAppnt",tLBPOAppntSchema2.getFillNo());
					}
				}
			}
		}
		
		//校验被保人信息
		LBPOInsuredSet tLBPOInsuredSet1 = new LBPOInsuredSet();
		LBPOInsuredDB tLBPOInsuredDB1 = new LBPOInsuredDB();
		tLBPOInsuredDB1.setContNo(this.mContNo);
		tLBPOInsuredDB1.setInputNo("1");
		tLBPOInsuredSet1 = tLBPOInsuredDB1.query();
		
		for(int i=1;i<=tLBPOInsuredSet1.size();i++)
		{
			LBPOInsuredSchema tLBPOInsuredSchema1 = new LBPOInsuredSchema();
			tLBPOInsuredSchema1.setSchema(tLBPOInsuredSet1.get(i));
			
			LBPOInsuredDB tLBPOInsuredDB2 = new LBPOInsuredDB();
			LBPOInsuredSet tLBPOInsuredSet2 = new LBPOInsuredSet();
			LBPOInsuredSchema tLBPOInsuredSchema2 = new LBPOInsuredSchema();
			tLBPOInsuredDB2.setContNo(tLBPOInsuredSchema1.getContNo());
			tLBPOInsuredDB2.setFillNo(tLBPOInsuredSchema1.getFillNo());
			tLBPOInsuredDB2.setInputNo("2");
			tLBPOInsuredSet2 = tLBPOInsuredDB2.query();
			if(tLBPOInsuredSet2.size()<=0||tLBPOInsuredSet2.size()>1)
			{
				CError.buildErr(this,"保单投保人数据结构缺失");
				return false;
			}
			tLBPOInsuredSchema2 = tLBPOInsuredSet2.get(1);
			for(int n=1;n<=tLBPOInsuredSchema1.getFieldCount();n++)
			{
				String tFieldName = tLBPOInsuredSchema1.getFieldName(n);
				//String tErrCode = (String)tHashtable2.get(tFieldName);
				//String tErrMsg = (String)tHashtable.get(tFieldName);
				String tOperator1 = tLBPOInsuredSchema1.getOperator();
				String tOperator2 = tLBPOInsuredSchema2.getOperator();
				String tClassName = tLBPOInsuredSchema1.getClass().getName();
				String tCheckName = tClassName .substring(tClassName.lastIndexOf(".") + 1);
				tCheckName = tCheckName.substring(0, tCheckName.lastIndexOf("Schema"));
				String tFieldKey =  tCheckName + "." + tFieldName ;
				logger.debug("校验的字段为"+tFieldKey);
				logger.debug(tHashtable.containsKey(tFieldKey));
				LBPODataDictionarySchema tLBPODataDictionarySchema = new LBPODataDictionarySchema();
				tLBPODataDictionarySchema = (LBPODataDictionarySchema)tHashtable.get(tFieldKey);
				String tErrCode;
				String tErrMsg;
				
				if(tHashtable.containsKey(tFieldKey))
				{
					tLBPODataDictionarySchema = (LBPODataDictionarySchema)tHashtable.get(tFieldKey);
					tErrCode = tLBPODataDictionarySchema.getErrorCode();
					tErrMsg = (String)tLBPODataDictionarySchema.getMSG();
					//tongmeng 2008-10-27 add
					//增加记录第几被保人的错误信息
					if(tLBPOInsuredSet1.size()>1)
					{
						tErrMsg = "第"+tLBPOInsuredSchema1.getSequenceNo()+""+tErrMsg;
					}
					String tFieldValue1 = tLBPOInsuredSchema1.getV(n);
					String tFieldValue2 = tLBPOInsuredSchema2.getV(n);
					boolean equalFlag = true;
					if(tFieldValue1==null&&tFieldValue2==null)
					{
						//两个都为空,认为录入一致.
					}
					else
					{
						//至少有一个不为空
						if(tFieldValue1==null||tFieldValue2==null)
						{
							//如果有一个为空,那么认为不等
							equalFlag = false;
						}
						else if(!tFieldValue1.equals(tFieldValue2))
						{
							//两个都不为空,比较是否相等
							equalFlag = false;
						}
							
					}
					
					//如果比较结果是不等,记录错误信息
					if(!equalFlag)
					{
						//被保人记录被保人序列号
						this.makeLBPODataDetailError("1", tErrCode, tOperator1,
								tFieldValue1, tFieldName, tErrMsg,"LBPOInsured",tLBPOInsuredSchema1.getSequenceNo());
						this.makeLBPODataDetailError("2", tErrCode, tOperator2,
								tFieldValue2, tFieldName, tErrMsg,"LBPOInsured",tLBPOInsuredSchema2.getSequenceNo());
					}
				}
			}
		}
	
		//校验险种信息
		LBPOPolSet tLBPOPolSet1 = new LBPOPolSet();
		LBPOPolDB tLBPOPolDB1 = new LBPOPolDB();
		tLBPOPolDB1.setContNo(this.mContNo);
		tLBPOPolDB1.setInputNo("1");
		tLBPOPolSet1 = tLBPOPolDB1.query();
		
		for(int i=1;i<=tLBPOPolSet1.size();i++)
		{
			LBPOPolSchema tLBPOPolSchema1 = new LBPOPolSchema();
			tLBPOPolSchema1.setSchema(tLBPOPolSet1.get(i));
			
			LBPOPolDB tLBPOPolDB2 = new LBPOPolDB();
			LBPOPolSet tLBPOPolSet2 = new LBPOPolSet();
			LBPOPolSchema tLBPOPolSchema2 = new LBPOPolSchema();
			tLBPOPolDB2.setContNo(tLBPOPolSchema1.getContNo());
			tLBPOPolDB2.setFillNo(tLBPOPolSchema1.getFillNo());
			//tLBPOPolDB2.setInsuredNo(tLBPOPolSchema1.getInsuredNo());
			tLBPOPolDB2.setInputNo("2");
			tLBPOPolSet2 = tLBPOPolDB2.query();
			if(tLBPOPolSet2.size()<=0||tLBPOPolSet2.size()>1)
			{
				CError.buildErr(this,"保单险种数据结构缺失");
				return false;
			}
			tLBPOPolSchema2 = tLBPOPolSet2.get(1);
			for(int n=1;n<=tLBPOPolSchema1.getFieldCount();n++)
			{
				String tFieldName = tLBPOPolSchema1.getFieldName(n);
				//String tErrCode = (String)tHashtable2.get(tFieldName);
				//String tErrMsg = (String)tHashtable.get(tFieldName);
				String tOperator1 = tLBPOPolSchema1.getOperator();
				String tOperator2 = tLBPOPolSchema2.getOperator();
				String tClassName = tLBPOPolSchema1.getClass().getName();
				String tCheckName = tClassName .substring(tClassName.lastIndexOf(".") + 1);
				tCheckName = tCheckName.substring(0, tCheckName.lastIndexOf("Schema"));
				String tFieldKey =  tCheckName + "." + tFieldName ;
				LBPODataDictionarySchema tLBPODataDictionarySchema = new LBPODataDictionarySchema();
				tLBPODataDictionarySchema = (LBPODataDictionarySchema)tHashtable.get(tFieldKey);
				
				String tErrCode;
				String tErrMsg;
				if(tHashtable.containsKey(tFieldKey))
				{
					tLBPODataDictionarySchema = (LBPODataDictionarySchema)tHashtable.get(tFieldKey);
					tErrCode = tLBPODataDictionarySchema.getErrorCode();
					tErrMsg = (String)tLBPODataDictionarySchema.getMSG();
					//tongmeng 2008-10-27 add
					//增加记录第几险种的错误信息
					if(tLBPOPolSet1.size()>1)
					{
						tErrMsg = "第"+tLBPOPolSchema1.getFillNo()+"条"+tErrMsg;
					}
					String tFieldValue1 = tLBPOPolSchema1.getV(n);
					String tFieldValue2 = tLBPOPolSchema2.getV(n);
					boolean equalFlag = true;
					if(tFieldValue1==null&&tFieldValue2==null)
					{
						//两个都为空,认为录入一致.
					}
					else
					{
						//至少有一个不为空
						if(tFieldValue1==null||tFieldValue2==null)
						{
							//如果有一个为空,那么认为不等
							equalFlag = false;
						}
						else if(!tFieldValue1.equals(tFieldValue2))
						{
							//两个都不为空,比较是否相等
							equalFlag = false;
						}
							
					}
					
					//如果比较结果是不等,记录错误信息
					if(!equalFlag)
					{
						//险种记录填单号
						this.makeLBPODataDetailError("1", tErrCode, tOperator1,
								tFieldValue1, tFieldName, tErrMsg,"LBPOPol",tLBPOPolSchema1.getFillNo());
						this.makeLBPODataDetailError("2", tErrCode, tOperator2,
								tFieldValue2, tFieldName, tErrMsg,"LBPOPol",tLBPOPolSchema2.getFillNo());
					}
				}
			}
		}
		
		//校验告知信息
		LBPOCustomerImpartSet tLBPOCustomerImpartSet1 = new LBPOCustomerImpartSet();
		LBPOCustomerImpartDB tLBPOCustomerImpartDB1 = new LBPOCustomerImpartDB();
		tLBPOCustomerImpartDB1.setContNo(this.mContNo);
		tLBPOCustomerImpartDB1.setInputNo("1");
		tLBPOCustomerImpartSet1 = tLBPOCustomerImpartDB1.query();
		
		for(int i=1;i<=tLBPOCustomerImpartSet1.size();i++)
		{
			LBPOCustomerImpartSchema tLBPOCustomerImpartSchema1 = new LBPOCustomerImpartSchema();
			tLBPOCustomerImpartSchema1.setSchema(tLBPOCustomerImpartSet1.get(i));
			
			LBPOCustomerImpartDB tLBPOCustomerImpartDB2 = new LBPOCustomerImpartDB();
			LBPOCustomerImpartSet tLBPOCustomerImpartSet2 = new LBPOCustomerImpartSet();
			LBPOCustomerImpartSchema tLBPOCustomerImpartSchema2 = new LBPOCustomerImpartSchema();
			tLBPOCustomerImpartDB2.setContNo(tLBPOCustomerImpartSchema1.getContNo());
			tLBPOCustomerImpartDB2.setFillNo(tLBPOCustomerImpartSchema1.getFillNo());
			tLBPOCustomerImpartDB2.setInputNo("2");
			tLBPOCustomerImpartSet2 = tLBPOCustomerImpartDB2.query();
			if(tLBPOCustomerImpartSet2.size()<=0||tLBPOCustomerImpartSet2.size()>1)
			{
				CError.buildErr(this,"保单告知数据结构缺失");
				return false;
			}
			tLBPOCustomerImpartSchema2 = tLBPOCustomerImpartSet2.get(1);
			for(int n=1;n<=tLBPOCustomerImpartSchema1.getFieldCount();n++)
			{
				String tFieldName = tLBPOCustomerImpartSchema1.getFieldName(n);
				//String tErrCode = (String)tHashtable2.get(tFieldName);
				//String tErrMsg = (String)tHashtable.get(tFieldName);
				String tOperator1 = tLBPOCustomerImpartSchema1.getOperator();
				String tOperator2 = tLBPOCustomerImpartSchema2.getOperator();
				String tClassName = tLBPOCustomerImpartSchema1.getClass().getName();
				String tCheckName = tClassName .substring(tClassName.lastIndexOf(".") + 1);
				tCheckName = tCheckName.substring(0, tCheckName.lastIndexOf("Schema"));
				String tFieldKey =  tCheckName + "." + tFieldName ;
				LBPODataDictionarySchema tLBPODataDictionarySchema = new LBPODataDictionarySchema();
				tLBPODataDictionarySchema = (LBPODataDictionarySchema)tHashtable.get(tFieldKey);
				
				
				
				String tErrCode;
				String tErrMsg;
				if(tHashtable.containsKey(tFieldKey))
				{
					tLBPODataDictionarySchema = (LBPODataDictionarySchema)tHashtable.get(tFieldKey);
					tErrCode = tLBPODataDictionarySchema.getErrorCode();
					tErrMsg = (String)tLBPODataDictionarySchema.getMSG();
					//tongmeng 2008-10-27 add
					//增加记录第几告知的错误信息
					if(tLBPOCustomerImpartSet1.size()>1)
					{
						tErrMsg = "第"+tLBPOCustomerImpartSchema1.getFillNo()+"条"+tErrMsg;
					}
					
					String tFieldValue1 = tLBPOCustomerImpartSchema1.getV(n);
					String tFieldValue2 = tLBPOCustomerImpartSchema2.getV(n);
					boolean equalFlag = true;
					if(tFieldValue1==null&&tFieldValue2==null)
					{
						//两个都为空,认为录入一致.
					}
					else
					{
						//至少有一个不为空
						if(tFieldValue1==null||tFieldValue2==null)
						{
							//如果有一个为空,那么认为不等
							equalFlag = false;
						}
						else if(!tFieldValue1.equals(tFieldValue2))
						{
							//两个都不为空,比较是否相等
							equalFlag = false;
						}
							
					}
					
					//如果比较结果是不等,记录错误信息
					if(!equalFlag)
					{
						//告知记录填单号
						this.makeLBPODataDetailError("1", tErrCode, tOperator1,
								tFieldValue1, tFieldName, tErrMsg,"LBPOCustomerImpart",tLBPOCustomerImpartSchema1.getFillNo());
						this.makeLBPODataDetailError("2", tErrCode, tOperator2,
								tFieldValue2, tFieldName, tErrMsg,"LBPOCustomerImpart",tLBPOCustomerImpartSchema2.getFillNo());
					}
				}
			}
		}
		
		//校验受益人
		LBPOBnfSet tLBPOBnfSet1 = new LBPOBnfSet();
		LBPOBnfDB tLBPOBnfDB1 = new LBPOBnfDB();
		tLBPOBnfDB1.setContNo(this.mContNo);
		tLBPOBnfDB1.setInputNo("1");
		tLBPOBnfSet1 = tLBPOBnfDB1.query();
		
		for(int i=1;i<=tLBPOBnfSet1.size();i++)
		{
			LBPOBnfSchema tLBPOBnfSchema1 = new LBPOBnfSchema();
			tLBPOBnfSchema1.setSchema(tLBPOBnfSet1.get(i));
			
			LBPOBnfDB tLBPOBnfDB2 = new LBPOBnfDB();
			LBPOBnfSet tLBPOBnfSet2 = new LBPOBnfSet();
			LBPOBnfSchema tLBPOBnfSchema2 = new LBPOBnfSchema();
			tLBPOBnfDB2.setContNo(tLBPOBnfSchema1.getContNo());
			tLBPOBnfDB2.setFillNo(tLBPOBnfSchema1.getFillNo());
			tLBPOBnfDB2.setInputNo("2");
			tLBPOBnfSet2 = tLBPOBnfDB2.query();
			if(tLBPOBnfSet2.size()<=0||tLBPOBnfSet2.size()>1)
			{
				CError.buildErr(this,"保单受益人数据结构缺失");
				return false;
			}
			tLBPOBnfSchema2 = tLBPOBnfSet2.get(1);
			for(int n=1;n<=tLBPOBnfSchema1.getFieldCount();n++)
			{
				String tFieldName = tLBPOBnfSchema1.getFieldName(n);
				//String tErrCode = (String)tHashtable2.get(tFieldName);
				//String tErrMsg = (String)tHashtable.get(tFieldName);
				String tOperator1 = tLBPOBnfSchema1.getOperator();
				String tOperator2 = tLBPOBnfSchema2.getOperator();
				String tClassName = tLBPOBnfSchema1.getClass().getName();
				String tCheckName = tClassName .substring(tClassName.lastIndexOf(".") + 1);
				tCheckName = tCheckName.substring(0, tCheckName.lastIndexOf("Schema"));
				String tFieldKey =  tCheckName + "." + tFieldName ;
				LBPODataDictionarySchema tLBPODataDictionarySchema = new LBPODataDictionarySchema();
				tLBPODataDictionarySchema = (LBPODataDictionarySchema)tHashtable.get(tFieldKey);

				
				String tErrCode;
				String tErrMsg;
				if(tHashtable.containsKey(tFieldKey))
				{
					tLBPODataDictionarySchema = (LBPODataDictionarySchema)tHashtable.get(tFieldKey);
					tErrCode = tLBPODataDictionarySchema.getErrorCode();
					tErrMsg = (String)tLBPODataDictionarySchema.getMSG();
					//tongmeng 2008-10-27 add
					//增加记录第几受益人的错误信息
					if(tLBPOBnfSet1.size()>1)
					{
						tErrMsg = "第"+tLBPOBnfSchema1.getFillNo()+"条"+tErrMsg;
					}
					String tFieldValue1 = tLBPOBnfSchema1.getV(n);
					String tFieldValue2 = tLBPOBnfSchema2.getV(n);
					boolean equalFlag = true;
					if(tFieldValue1==null&&tFieldValue2==null)
					{
						//两个都为空,认为录入一致.
					}
					else
					{
						//至少有一个不为空
						if(tFieldValue1==null||tFieldValue2==null)
						{
							//如果有一个为空,那么认为不等
							equalFlag = false;
						}
						else if(!tFieldValue1.equals(tFieldValue2))
						{
							//两个都不为空,比较是否相等
							equalFlag = false;
						}
							
					}
					
					//如果比较结果是不等,记录错误信息
					if(!equalFlag)
					{
						//受益人记录填单号
						this.makeLBPODataDetailError("1", tErrCode, tOperator1,
								tFieldValue1, tFieldName, tErrMsg,"LBPOBnf",tLBPOBnfSchema1.getFillNo());
						this.makeLBPODataDetailError("2", tErrCode, tOperator2,
								tFieldValue2, tFieldName, tErrMsg,"LBPOBnf",tLBPOBnfSchema2.getFillNo());
					}
				}
			}
		}
		
		//校验投资计划
		
		LBPOPerInvestPlanSet tLBPOPerInvestPlanSet1 = new LBPOPerInvestPlanSet();
		LBPOPerInvestPlanDB tLBPOPerInvestPlanDB1 = new LBPOPerInvestPlanDB();
		tLBPOPerInvestPlanDB1.setContNo(this.mContNo);
		tLBPOPerInvestPlanDB1.setInputNo("1");
		tLBPOPerInvestPlanSet1 = tLBPOPerInvestPlanDB1.query();
		
		for(int i=1;i<=tLBPOPerInvestPlanSet1.size();i++)
		{
			LBPOPerInvestPlanSchema tLBPOPerInvestPlanSchema1 = new LBPOPerInvestPlanSchema();
			tLBPOPerInvestPlanSchema1.setSchema(tLBPOPerInvestPlanSet1.get(i));
			
			LBPOPerInvestPlanDB tLBPOPerInvestPlanDB2 = new LBPOPerInvestPlanDB();
			LBPOPerInvestPlanSet tLBPOPerInvestPlanSet2 = new LBPOPerInvestPlanSet();
			LBPOPerInvestPlanSchema tLBPOPerInvestPlanSchema2 = new LBPOPerInvestPlanSchema();
			tLBPOPerInvestPlanDB2.setContNo(tLBPOPerInvestPlanSchema1.getContNo());
			tLBPOPerInvestPlanDB2.setFillNo(tLBPOPerInvestPlanSchema1.getFillNo());
			tLBPOPerInvestPlanDB2.setInputNo("2");
			tLBPOPerInvestPlanSet2 = tLBPOPerInvestPlanDB2.query();
			if(tLBPOPerInvestPlanSet2.size()<=0||tLBPOPerInvestPlanSet2.size()>1)
			{
				CError.buildErr(this,"保单投资计划数据结构缺失");
				return false;
			}
			tLBPOPerInvestPlanSchema2 = tLBPOPerInvestPlanSet2.get(1);
			for(int n=1;n<=tLBPOPerInvestPlanSchema1.getFieldCount();n++)
			{
				String tFieldName = tLBPOPerInvestPlanSchema1.getFieldName(n);
				//String tErrCode = (String)tHashtable2.get(tFieldName);
				//String tErrMsg = (String)tHashtable.get(tFieldName);
				String tOperator1 = tLBPOPerInvestPlanSchema1.getOperator();
				String tOperator2 = tLBPOPerInvestPlanSchema2.getOperator();
				String tClassName = tLBPOPerInvestPlanSchema1.getClass().getName();
				String tCheckName = tClassName .substring(tClassName.lastIndexOf(".") + 1);
				tCheckName = tCheckName.substring(0, tCheckName.lastIndexOf("Schema"));
				String tFieldKey =  tCheckName + "." + tFieldName ;
				LBPODataDictionarySchema tLBPODataDictionarySchema = new LBPODataDictionarySchema();
				tLBPODataDictionarySchema = (LBPODataDictionarySchema)tHashtable.get(tFieldKey);


				String tErrCode;
				String tErrMsg;
				if(tHashtable.containsKey(tFieldKey))
				{
					tLBPODataDictionarySchema = (LBPODataDictionarySchema)tHashtable.get(tFieldKey);
					tErrCode = tLBPODataDictionarySchema.getErrorCode();
					tErrMsg = (String)tLBPODataDictionarySchema.getMSG();
					//tongmeng 2008-10-27 add
					//增加记录第几投资计划的错误信息
					if(tLBPOPerInvestPlanSet1.size()>1)
					{
						tErrMsg = "第"+tLBPOPerInvestPlanSchema1.getFillNo()+"条"+tErrMsg;
					}
					String tFieldValue1 = tLBPOPerInvestPlanSchema1.getV(n);
					String tFieldValue2 = tLBPOPerInvestPlanSchema2.getV(n);
					boolean equalFlag = true;
					if(tFieldValue1==null&&tFieldValue2==null)
					{
						//两个都为空,认为录入一致.
					}
					else
					{
						//至少有一个不为空
						if(tFieldValue1==null||tFieldValue2==null)
						{
							//如果有一个为空,那么认为不等
							equalFlag = false;
						}
						else if(!tFieldValue1.equals(tFieldValue2))
						{
							//两个都不为空,比较是否相等
							equalFlag = false;
						}
							
					}
					
					//如果比较结果是不等,记录错误信息
					if(!equalFlag)
					{
						//投资计划记录填单号
						this.makeLBPODataDetailError("1", tErrCode, tOperator1,
								tFieldValue1, tFieldName, tErrMsg,"LBPOPerInvestPlan",tLBPOPerInvestPlanSchema1.getFillNo());
						this.makeLBPODataDetailError("2", tErrCode, tOperator2,
								tFieldValue2, tFieldName, tErrMsg,"LBPOPerInvestPlan",tLBPOPerInvestPlanSchema2.getFillNo());
					}
				}
			}
		}
		
		return true;
	}

	private boolean prepareOutputData() {
		if(this.map.keySet().size()>=1)
		{
			SameFlag = "0";
		}
		else
		{
			SameFlag = "1";
		}
		mTransferData.setNameAndValue("SameFlag", SameFlag);
		mInputData.clear();
		mInputData.add(mTransferData);
		mInputData.add(map);
		return true;
	}

	public VData getResult() {
		return mInputData;
	}

	
	private void makeLBPODataDetailError(String tErrorCount,String tErrorCode,String tOperator,String tValue,
			String tErrorTag,String tErrorContent,String tTableName,String tOtherNo)
	{
		String serNo = PubFun1.CreateMaxNo("BPOSN", 20);
		LBPODataDetailErrorSchema tLBPODataDetailErrorSchema = new LBPODataDetailErrorSchema();
		tLBPODataDetailErrorSchema.setBussNo(mContNo);
		tLBPODataDetailErrorSchema.setBussNoType("TB");
		tLBPODataDetailErrorSchema.setSerialNo(serNo);
		tLBPODataDetailErrorSchema.setErrorCount(tErrorCount);
		tLBPODataDetailErrorSchema.setErrorCode(tErrorCode);
		tLBPODataDetailErrorSchema.setOperator(tOperator);
		tLBPODataDetailErrorSchema.setContent(tValue);
		
		tLBPODataDetailErrorSchema.setErrorTag(tErrorTag);
		tLBPODataDetailErrorSchema.setErrorContent(tErrorContent);
		
		//tongmeng 2008-10-27 add
		//增加记录 错误表名 和其他号码
		tLBPODataDetailErrorSchema.setTableName(tTableName);
		tLBPODataDetailErrorSchema.setOtherNo(tOtherNo);
		
		tLBPODataDetailErrorSchema.setMakeDate(theCurrentDate);
		tLBPODataDetailErrorSchema.setMakeTime(theCurrentTime);
		
		map.put(tLBPODataDetailErrorSchema,"INSERT");
	}
	public static void main(String args[]) {
		ToJudgeWetherSimeBL tToJudgeWetherSimeBL = new ToJudgeWetherSimeBL();
		tToJudgeWetherSimeBL.submitData("86519100000002", "001");
		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start tPRnewManualDunBLS Submit...");
		VData tvdata = tToJudgeWetherSimeBL.getResult();

		if (!tPubSubmit.submitData(tvdata, "INSERT")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			logger.debug(tError.errorMessage);
		}
		logger.debug("错误信息提交到数据库提交完毕！");

	}
}
