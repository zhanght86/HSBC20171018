package com.sinosoft.lis.pubfun;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
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
public class QueryLargeDataExportBL {
	
	private static Logger logger = Logger.getLogger(QueryLargeDataExportBL.class);

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
	private StringBuffer mStrBuff = new StringBuffer();
	
	/** sheet名称 */
	private String[] mSheetName;
	/** sheet标题 */
	private String[] mSheetTitle;
	/** sheet查询SQL */
	private String[] mSheetSql;
	/** 查询记录数 */
	private int[] mCountRow;
	/** 文件路径 */
	private String mFilePath;
	/** 文件名称 */
	private String mFileName;
	
	public QueryLargeDataExportBL() {
		
	}
	
	public boolean submitData(VData cInputData, String cOperate) {
		
		if (!getInputData(cInputData, cOperate)) {
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
		mSheetTitle = (String[])tTransferData.getValueByName("SheetTitle");
		mSheetSql = (String[])tTransferData.getValueByName("SheetSql");
		mCountRow = (int[])tTransferData.getValueByName("CountRow");
		
		return true;
	}
	
	/**
	 * 数据校验
	 * @return 如果校验失败，则返回false，否则返回true
	 */
	private boolean checkData() {
		
		return true;
	}
	
	/**
	 * 业务处理
	 * @return 如果处理失败，则返回false，否则返回true
	 */
	private boolean dealData() {
		
		if (!createFileName()) {
			return false;
		}
		
		//生成Excel
		SXSSFWorkbook wb = new SXSSFWorkbook(5000);//内存中保留 5000 条数据，以免内存溢出，其余写入硬盘
		/**每次处理记录条数*/
		int tPerDealRow = 0;
		
		mStrBuff = new StringBuffer();
		mStrBuff.append("select sysvarvalue from ldsysvar where sysvar='PerDealRow' ");
		
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(mStrBuff.toString());
		
		String tSysVarValue = mExeSQL.getOneValue(sqlbv1);
		tPerDealRow = Integer.parseInt(tSysVarValue);
		
		//循环Sheet个数
		for (int i=0;i<mSheetName.length;i++) {
			
			Sheet tSheet = wb.createSheet(String.valueOf(i));
			wb.setSheetName(i, mSheetName[i]);
			writeSheetTitle(tSheet, mSheetTitle[i]);
			
			for (int j = 0; j < mCountRow[i]; j = j+tPerDealRow) {
				
				mStrBuff = new StringBuffer();
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
					mStrBuff.append("select * from (");
					mStrBuff.append("select rownum datanum, s.* from (");
					mStrBuff.append(mSheetSql[i]);
					mStrBuff.append(") s ");
					mStrBuff.append("where rownum<="+ "?rownum?" +" ) t where t.datanum>"+ j +" and t.datanum<="+ "?rownum?" +" ");
		        }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
		        	mStrBuff.append("select * from (");
					mStrBuff.append("select (select @rownum:=@rownum+1 from (select @rownum := 0) ax) datanum, s.* from (");
					mStrBuff.append(mSheetSql[i]);
					mStrBuff.append(") s ");
					mStrBuff.append(" limit 0,"+ "?rownum?" +" ) t where t.datanum>"+ j +" and t.datanum<="+ "?rownum?" +" ");
		        }
						
				SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				sqlbv2.sql(mStrBuff.toString());
				sqlbv2.put("rownum", (j+tPerDealRow));
				SSRS tSSRS = new SSRS();
				tSSRS = mExeSQL.execSQL(sqlbv2);
				
				for (int k = 0; k < tSSRS.MaxRow; k++) {
					
					Row row = tSheet.createRow(j+k+1);
					
					for (int l = 0; l < tSSRS.MaxCol-1; l++) {
						
						row.createCell(l).setCellValue(tSSRS.GetText(k+1, l+2));
					}
				}
			}
		}
		
		try {
			
			FileOutputStream fos = new FileOutputStream(mFilePath + mFileName);
			wb.write(fos);
			fos.close();
		} catch (Exception e) {
			
			e.printStackTrace();
			buildError("dealData", "文件生成出现异常："+ e.toString() +"！");
			return false;
		}
		
		mResult.add(mFilePath + mFileName);
		
		return true;
	}
	
	/**
	 * 数据提交
	 * @return 如果提交数据失败，则返回false，否则返回true
	 */
	private boolean saveData() {
		
		return true;
	}
	
	/**
	 * 构造文件名称
	 * @return 如果失败，则返回false，否则返回true
	 */
	private boolean createFileName() {
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql("select sysvarvalue from ldsysvar where sysvar='ExportPath'");
		mFilePath = mExeSQL.getOneValue(sqlbv3);
		
		mFilePath = mFilePath + PubFun.getCurrentDate().replaceAll("-", "/") + "/";
		
		File tFile = new File(mFilePath);
		if (!tFile.exists()) {
			tFile.mkdirs();
		}
		
		DateFormat tDF = new SimpleDateFormat("yyyyMMddhhmmss");
		String tFileTime = tDF.format(new Date());
		mFileName = tFileTime + ".xlsx";
		
		return true;
	}
	
	/**
	 * 将标题写入
	 * @param tSheet Excel中Sheet对像
	 * @param tTitle 报表标题
	 * @return 如果失败，则返回false，否则返回true
	 */
	private boolean writeSheetTitle(Sheet tSheet, String tSheetTitle) {
		
		String[] tTitleArray = tSheetTitle.split("\\^\\|");
		Row row = tSheet.createRow(0); //标题：第一行
		
		for (int i = 0; i < tTitleArray.length; i++) {
			
			row.createCell(i).setCellValue(tTitleArray[i]);
		}
		
		return true;
	}
	
	/**
	 * 错误构造方法
	 * @param tFunctionName
	 * @param tErrorMessage
	 */
	private void buildError(String tFunctionName, String tErrorMessage) {
		
		CError tCError = new CError();
		tCError.moduleName = this.getClass().getSimpleName();
		tCError.functionName = tFunctionName;
		tCError.errorMessage = tErrorMessage;
		mErrors.addOneError(tCError);
		logger.error(tErrorMessage);
	}
	
	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
