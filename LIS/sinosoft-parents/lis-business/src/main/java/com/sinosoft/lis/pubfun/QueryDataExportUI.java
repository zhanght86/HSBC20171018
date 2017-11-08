package com.sinosoft.lis.pubfun;

import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.service.SqlParse;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>Title：后台查询数据导出</p>
 * 
 * <p>Description：后台查询数据导出</p>
 * 
 * <p>Copyright：Copyright (c) 2012</p>
 * 
 * <p>Company: Sinosoft</p>
 * 
 * @author 杨治纲
 * @version 8.0
 */
public class QueryDataExportUI implements BusinessService {
	
	private static Logger logger = Logger.getLogger(QueryDataExportUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private CErrors mErrors = new CErrors();
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 操作变量 */
	private String mOperate;
	
	/** 全局变量 */
	private GlobalInput mGlobalInput;
	
	private ExeSQL mExeSQL = new ExeSQL();
	
	/** 报表标题 */
	private String mTitle;
	/** 查询SQL */
	private String mQuerySQL;
	/** 查询记录数 */
	private int mCountRow;
	
	public QueryDataExportUI() {
		
	}
	
	public boolean submitData(VData vData, String operate) {
		// TODO Auto-generated method stub
		
		if (!getInputData(vData, operate)) {
			return false;
		}
		
		if (!checkData()) {
			return false;
		}
		
		if (!dealData()) {
			return false;
		}
		
		if (!saveData()) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 从输入数据中得到所有对象
	 * @param cInputData 传入数据对象
	 * @param cOperate 操作类型
	 * @return 如果没有得到足够的业务数据对象，则返回false，否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		
		mInputData = (VData)cInputData.clone();
		mOperate = cOperate;
		
		mGlobalInput = (GlobalInput)mInputData.getObjectByObjectName("GlobalInput", 0);
		TransferData tTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
		if (mGlobalInput==null || tTransferData==null) {
			buildError("getInputData", "传入信息为空！");
			return false;
		}
		
		mTitle = (String)tTransferData.getValueByName("Title");
		if (mTitle==null || mTitle.trim().equals("")) {
			buildError("getInputData", "获取标题信息失败！");
			return false;
		}
		
		mQuerySQL = (String)tTransferData.getValueByName("QuerySQL");
		if (mQuerySQL==null || mQuerySQL.trim().equals("")) {
			buildError("getInputData", "获取查询SQL信息失败！");
			return false;
		}
		
		if (!getSQL()) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 数据校验
	 * @return 如果校验失败，则返回false，否则返回true
	 */
	private boolean checkData() {
		
		try {
			
			String tCountRowSQL = "select count(1) from ("+ mQuerySQL +") ax ";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tCountRowSQL);
			mCountRow = Integer.parseInt(mExeSQL.getOneValue(sqlbv));
			
			if (mCountRow<=0) {
				buildError("checkData", "没有查询到数据！");
				return false;
			} else if (mCountRow>10000) {
				buildError("checkData", "查询记录超出Excel导出的最大行数，请按条件查询导出！");
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			buildError("checkData", "查询SQL错误！");
			return false;
		}
		
		return true;
	}
	
	/**
	 * 业务处理
	 * @return 如果处理失败，则返回false，否则返回true
	 */
	private boolean dealData() {
		
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("Title", mTitle);
		tTransferData.setNameAndValue("QuerySQL", mQuerySQL);
		tTransferData.setNameAndValue("CountRow", String.valueOf(mCountRow));
		
		mInputData.clear();
		mInputData.add(mGlobalInput);
		mInputData.add(tTransferData);
		
		QueryDataExportBL tQueryDataExportBL = new QueryDataExportBL();
		if (!tQueryDataExportBL.submitData(mInputData, mOperate)) {
			mErrors = tQueryDataExportBL.getErrors();
			return false;
		} else {
			mResult = tQueryDataExportBL.getResult();
		}
		
		return true;
	}
	
	/**
	 * 数据提交
	 * @return 如果提交失败，则返回false，否则返回true
	 */
	private boolean saveData() {
		
		return true;
	}
	
	/**
	 * 解析SQL
	 * @return 如果失败，则返回false，否则返回true
	 */
	private boolean getSQL() {
		
		try {
			
			SqlParse tSqlParse = new SqlParse();
			mQuerySQL = tSqlParse.parseSQL(mQuerySQL);
			//mQuerySQL = new String(mQuerySQL.getBytes("ISO8859-1"), "UTF-8");
		} catch (Exception e) {
			buildError("getSQL", "解析SQL出错！");
			return false;
		}
		
		return true;
	}
	
	public void buildError(String functionName, String errorMessage) {
		// TODO Auto-generated method stub
		CError tCError = new CError();
		tCError.moduleName = this.getClass().getSimpleName();
		tCError.functionName = functionName;
		tCError.errorMessage = errorMessage;
		mErrors.addOneError(tCError);
		logger.error(errorMessage);
	}
	
	public VData getResult() {
		// TODO Auto-generated method stub
		return mResult;
	}
	
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
