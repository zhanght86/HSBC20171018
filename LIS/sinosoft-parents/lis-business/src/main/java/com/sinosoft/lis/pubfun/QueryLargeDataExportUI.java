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
 * <p>Title：后台查询大数据导出</p>
 * 
 * <p>Description：后台查询大数据导出，主要针对把大数据导出在一个sheet中的情况</p>
 * 
 * <p>Copyright：Copyright (c) 2012</p>
 * 
 * <p>Company: Sinosoft</p>
 * 
 * @author zhoufz
 * @version 8.0
 */
public class QueryLargeDataExportUI implements BusinessService {
	
	private static Logger logger = Logger.getLogger(QueryLargeDataExportUI.class);

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

	/** sheet名称 */
	private String[] mSheetName;
	/** sheet标题 */
	private String[] mSheetTitle;
	/** sheet查询SQL */
	private String[] mSheetSql;
	/** 查询记录数 */
	private int[] mCountRow;
	
	public QueryLargeDataExportUI() {
		
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
		
		mSheetName = (String[])tTransferData.getValueByName("SheetName");
		if (mSheetName==null) {
			buildError("getInputData", "获取sheet名称失败！");
			return false;
		}
		
		mSheetTitle = (String[])tTransferData.getValueByName("SheetTitle");
		if (mSheetTitle==null) {
			buildError("getInputData", "获取标题信息失败！");
			return false;
		}
		
		mSheetSql = (String[])tTransferData.getValueByName("SheetSql");
		if (mSheetSql==null) {
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
			
			if (mSheetName.length!=mSheetTitle.length || mSheetName.length!=mSheetSql.length) {
				
				buildError("checkData", "参数数量不一致！");
				return false;
			}
			
			mCountRow = new int[mSheetName.length];
			
			for (int i = 0; i < mSheetSql.length; i++) {
				
				String tCountRowSQL = "select count(1) from ("+ mSheetSql[i] +") t";
				SQLwithBindVariables sqlbv =new SQLwithBindVariables();
				sqlbv.sql(tCountRowSQL);
				mCountRow[i] = Integer.parseInt(mExeSQL.getOneValue(sqlbv));
				
				if (mCountRow[i]>200000) {
					buildError("checkData", "查询数据量过大，请按条件查询导出！");
					return false;
				}
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
		tTransferData.setNameAndValue("SheetName", mSheetName);
		tTransferData.setNameAndValue("SheetTitle", mSheetTitle);
		tTransferData.setNameAndValue("SheetSql", mSheetSql);
		tTransferData.setNameAndValue("CountRow", mCountRow);
		
		mInputData.clear();
		mInputData.add(mGlobalInput);
		mInputData.add(tTransferData);
		
		QueryLargeDataExportBL tQueryLargeDataExportBL = new QueryLargeDataExportBL();
		if (!tQueryLargeDataExportBL.submitData(mInputData, mOperate)) {
			mErrors = tQueryLargeDataExportBL.getErrors();
			return false;
		} else {
			mResult = tQueryLargeDataExportBL.getResult();
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
			
			for (int i = 0; i < mSheetSql.length; i++) {
				
				mSheetSql[i] = tSqlParse.parseSQL(mSheetSql[i]);
			}
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
