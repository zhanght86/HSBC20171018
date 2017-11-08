package com.sinosoft.lis.logmanage;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.CError;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;

public class LogComponentUI implements BusinessService {
private static Logger logger = Logger.getLogger(LogComponentUI.class);
	public CErrors mErrors = new CErrors();
	private VData mInputData = new VData();
	private MMap map = new MMap();
	private String mOperate = "";
	private GlobalInput mGlobalInput = new GlobalInput();
	private LogSubjectSchema mLogSubjectSchema = new LogSubjectSchema();
	private LogItemSchema mLogItemSchema = new LogItemSchema();
	private LogDomainToItemSet mLogDomainToItemSet = new LogDomainToItemSet();
	private String mFlag = "";
	private String tCodeString = "000000";
	private String subjectIdToItem = "";

	public LogComponentUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {

		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		logger.debug("mOperate:.:.:.:.:" + mOperate);
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		PubSubmit tPubSubmit = new PubSubmit();
       if (!tPubSubmit.submitData(mInputData, mOperate)) {
           this.mErrors.copyAllErrors(tPubSubmit.mErrors);
           CError tError = new CError();
           tError.moduleName = "LogComponentUI";
           tError.functionName = "submitData";
           tError.errorMessage = "数据提交失败!";
           this.mErrors.addOneError(tError);
           return false;
       }
		return true;
	}

	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		TransferData mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
		if(mTransferData != null && !"".equals(mTransferData)){
			mFlag = (String)mTransferData.getValueByName("Flag");
		}else {
			CError tError = new CError();
	        tError.moduleName = "LogComponentUI";
	        tError.functionName = "getInputData";
	        tError.errorMessage = "数据传输Flag参数失败!";
	        this.mErrors.addOneError(tError);
			return false;
		}
		String chkFlag = "";
		String chkSQL = "";
		ExeSQL es = new ExeSQL();
		if("LOG".equals(mFlag)){
			mLogSubjectSchema.setSchema((LogSubjectSchema)cInputData.getObjectByObjectName("LogSubjectSchema", 0));
			chkSQL = "Select 'X' From logSubject Where subjectid = '?subjectid?'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(chkSQL);
			sqlbv.put("subjectid", mLogSubjectSchema.getSubjectID());
			chkFlag = es.getOneValue(sqlbv);
		}else if("ITEM".equals(mFlag)){
			mLogItemSchema.setSchema((LogItemSchema)cInputData.getObjectByObjectName("LogItemSchema", 0));
			chkSQL = "Select 'X' From Logitem Where itemid = '?itemid?'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(chkSQL);
			sqlbv.put("itemid", mLogItemSchema.getItemID());
			chkFlag = es.getOneValue(sqlbv);
			subjectIdToItem = (String)mTransferData.getValueByName("SubjectID");
		}else if("DOMAIN".equals(mFlag)){
			mLogDomainToItemSet.set((LogDomainToItemSet)cInputData.getObjectByObjectName("LogDomainToItemSet", 0));
			//chkSQL = "Select 'X' From LogDomainToItem Where subjectid = '"+mLogDomainToItemSchema.getSubjectID()+"' And itemid = '"+mLogDomainToItemSchema.getItemID()+"'";
			//chkFlag = es.getOneValue(chkSQL);
		}
		if("INSERT".equals(mOperate) && "DOMAIN".equals(mFlag)){
			if(chkFlag != null && !"".equals(chkFlag)){
				CError tError = new CError();
				tError.moduleName = "LogComponentUI";
				tError.functionName = "getInputData";
				tError.errorMessage = "该信息已经存在，不能重复添加！";
				this.mErrors.addOneError(tError);
				return false;
			}
		}else if("UPDATE".equals(mOperate)||"DELETE".equals(mOperate)){
			if(chkFlag == null || "".equals(chkFlag)){
				CError tError = new CError();
				tError.moduleName = "LogComponentUI";
				tError.functionName = "getInputData";
				tError.errorMessage = "需要修改或删除的信息不存在，请重新查询后再操作！";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		
		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LogComponentUI";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的信息！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean dealData() {
		if("LOG".equals(mFlag)){
			if(mOperate.equals("DELETE")){
				SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
				sqlbv1.sql("delete from logsubject where subjectid = '?subjectid?'");
				sqlbv1.put("subjectid", mLogSubjectSchema.getSubjectID());
				SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				sqlbv2.sql("delete from LogDomainToItem where subjectid = '?subjectid?'");
				sqlbv2.put("subjectid", mLogSubjectSchema.getSubjectID());
				SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
				sqlbv3.sql("delete from logitem where itemid like concat('?itemid?','%')");
				sqlbv3.put("itemid", mLogSubjectSchema.getSubjectID());
				map.put(sqlbv1, mOperate);
				map.put(sqlbv2, mOperate);
				map.put(sqlbv3, mOperate);
			}else if("UPDATE".equals(mOperate)){
				map.put(mLogSubjectSchema, mOperate);
			}else if("INSERT".equals(mOperate)){
				if(mLogSubjectSchema.getSubjectID()==null||mLogSubjectSchema.getSubjectID().equals(""))
				{
				int tCodeInt = 0;
				LogSubjectDB tLogSubjectDB = new LogSubjectDB();
				SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
				sqlbv4.sql("select * from logSubject where subjectid = (select MAX(subjectid) from logSubject)");
				LogSubjectSet tLogSubjectSet = tLogSubjectDB
						.executeQuery(sqlbv4); // 取最大序列号

				if ((tLogSubjectSet != null) && (tLogSubjectSet.size() > 0)) {
					tCodeString = tLogSubjectSet.get(1).getSubjectID();
				}

				try {
					tCodeInt = Integer.parseInt(tCodeString);
					tCodeInt++;
					tCodeString = Integer.toString(tCodeInt);
					tCodeString = "000000".substring(1, 6 - tCodeString.length() + 1)
							+ tCodeString; // 生成新日志计划编码
				} catch (Exception ex) {
					CError tError = new CError();
			        tError.moduleName = "LogComponentUI";
			        tError.functionName = "dealData";
			        tError.errorMessage = "生成监控主题ID失败!";
			        this.mErrors.addOneError(tError);
					return false;
				}
				mLogSubjectSchema.setSubjectID(tCodeString);
				}
				map.put(mLogSubjectSchema, mOperate);
			}else{
				CError tError = new CError();
		        tError.moduleName = "LogComponentUI";
		        tError.functionName = "dealData";
		        tError.errorMessage = "操作类型传输错误!";
		        this.mErrors.addOneError(tError);
				return false;
			}
		}else if("ITEM".equals(mFlag)){
			if(mOperate.equals("DELETE")){
				SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
				sqlbv5.sql("delete from logitem where itemid = '?itemid?'");
				sqlbv5.put("itemid", mLogItemSchema.getItemID());
				map.put(sqlbv5, mOperate);
			}else if("UPDATE".equals(mOperate)){
				map.put(mLogItemSchema, mOperate);
			}else if("INSERT".equals(mOperate)){
				int tCodeInt = 0;
				ExeSQL es = new ExeSQL();
				String temp = "Select substr(itemid,7,4) From Logitem Where itemid = (Select Max(itemid) From logitem Where itemid Like concat('?itemid?','%'))";
				SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
				sqlbv6.sql(temp);
				sqlbv6.put("itemid", subjectIdToItem);
				tCodeString = es.getOneValue(sqlbv6);
				if (tCodeString == null || "".equals(tCodeString)) {
					tCodeString = "0000";
				}

				try {
					tCodeInt = Integer.parseInt(tCodeString);
					tCodeInt++;
					tCodeString = Integer.toString(tCodeInt);
					tCodeString = subjectIdToItem+"0000".substring(1, 4 - tCodeString.length() + 1)
							+ tCodeString; // 生成新日志计划编码
				} catch (Exception ex) {
					CError tError = new CError();
			        tError.moduleName = "LogComponentUI";
			        tError.functionName = "dealData";
			        tError.errorMessage = "生成监控点ID失败!";
			        this.mErrors.addOneError(tError);
					return false;
				}
				mLogItemSchema.setItemID(tCodeString);
				map.put(mLogItemSchema, mOperate);
			}else{
				CError tError = new CError();
		        tError.moduleName = "LogComponentUI";
		        tError.functionName = "dealData";
		        tError.errorMessage = "操作类型传输失败!";
		        this.mErrors.addOneError(tError);
				return false;
			}
		}else if("DOMAIN".equals(mFlag)){
			map.put(this.mLogDomainToItemSet, "DELETE&INSERT");
			/*if(mOperate.equals("DELETE")){
				map.put("delete from LogDomainToItem where subjectid = '"+mLogDomainToItemSchema.getSubjectID()+"' and itemid = '"+mLogDomainToItemSchema.getItemID()+"'", mOperate);
			}else{
				map.put(mLogDomainToItemSchema, mOperate);
			}*/
		}
		
		return true;
	}

	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LogComponentUI";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return mInputData;
	}
}
