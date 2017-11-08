package com.sinosoft.lis.pubfun;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
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
public class QueryDataExportBL {
	
	private static Logger logger = Logger.getLogger(QueryDataExportBL.class);

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
	
	/** 报表标题 */
	private String mTitle;
	/** 查询SQL */
	private String mQuerySQL;
	/** 查询记录数 */
	private int mCountRow;
	/** 文件路径 */
	private String mFilePath;
	/** 文件名称 */
	private String mFileName;
	
	public QueryDataExportBL() {
		
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
		
		mTitle = (String)tTransferData.getValueByName("Title");
		mQuerySQL = (String)tTransferData.getValueByName("QuerySQL");
		mCountRow = Integer.parseInt((String)tTransferData.getValueByName("CountRow"));
		
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
		
		int tMaxRow_Sheet = 5000; //Excel中每个sheet的最大行数
		
		//获取需要写入的Sheet数
		int tCountSheet = mCountRow/tMaxRow_Sheet; //将数据分Sheet写入
		if (mCountRow%tMaxRow_Sheet>0) {
			tCountSheet = tCountSheet + 1;
		}
		
		int tMaxRow_SSRS = 5000; //SSRS中的数据存放太多会导致内存溢出
		
		int tCount_SSRSSheet = tMaxRow_Sheet/tMaxRow_SSRS; //必须能够整除
		
		//生成Excel
		HSSFWorkbook wb = new HSSFWorkbook();
		
		//先循环Sheet个数
		for (int i=0;i<tCountSheet;i++) {
			
			//创建一个新的工作表
			HSSFSheet tSheet = wb.createSheet("DataExport"+(i+1));
			
			//将标题写入
			writeSheetTitle(tSheet, mTitle);
			
			for (int j=0;j<tCount_SSRSSheet;j++) {
				
				int tStartRowNum = i*tMaxRow_Sheet + j*tMaxRow_SSRS;
				int tEndRowNum = i*tMaxRow_Sheet + (j+1)*tMaxRow_SSRS;
				
				mStrBuff = new StringBuffer();
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
					mStrBuff.append("select * from (");
					mStrBuff.append("select rownum datanum, s.* from (");
					mStrBuff.append(mQuerySQL);
					mStrBuff.append(") s ");
					mStrBuff.append("where rownum<="+ "?tEndRowNum?" +" ) t where t.datanum>"+ "?tStartRowNum?" +" and t.datanum<="+ "?datanum?" +" ");
		        }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
		        	mStrBuff.append("select * from (");
					mStrBuff.append("select (select @rownum:=@rownum+1 from (select @rownum := 0) ax) datanum, s.* from (");
					mStrBuff.append(mQuerySQL);
					mStrBuff.append(") s ");
					mStrBuff.append(" limit 0,"+ "?tEndRowNum?" +" ) t where t.datanum>"+ "?tStartRowNum?" +" and t.datanum<="+ "?datanum?" +" ");
		        }
								
				SSRS tSSRS = new SSRS();
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(mStrBuff.toString());
				sqlbv.put("tEndRowNum", tEndRowNum);
				sqlbv.put("tStartRowNum", tStartRowNum);
				sqlbv.put("datanum", tEndRowNum);
				tSSRS = mExeSQL.execSQL(sqlbv);
				writeSheetData(tSheet, j*tMaxRow_SSRS+1, tSSRS);
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
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql("select sysvarvalue from ldsysvar where sysvar='ExportPath'");
		mFilePath = mExeSQL.getOneValue(sqlbv1);
		
		mFilePath = mFilePath + PubFun.getCurrentDate().replaceAll("-", "/") + "/";
		
		File tFile = new File(mFilePath);
		if (!tFile.exists()) {
			tFile.mkdirs();
		}
		
		DateFormat tDF = new SimpleDateFormat("yyyyMMddhhmmss");
		String tFileTime = tDF.format(new Date());
		mFileName = tFileTime + ".xls";
		
		return true;
	}
	
	/**
	 * 将标题写入
	 * @param tSheet Excel中Sheet对像
	 * @param tTitle 报表标题
	 * @return 如果失败，则返回false，否则返回true
	 */
	private boolean writeSheetTitle(HSSFSheet tSheet, String tTitle) {
		
		tTitle = "序号^|" + tTitle;
		
		String[] tTitleArray = tTitle.split("\\^\\|");
		
		HSSFRow tRow = tSheet.createRow(0); //标题：第一行
		writeRowData(tRow, tTitleArray);
		
		return true;
	}
	
	/**
	 * 写入数据
	 * @param tSheet Excel中Sheet对像
	 * @param tRowNum 数据定入行数
	 * @param tSSRS 数据集
	 * @return
	 */
	private boolean writeSheetData(HSSFSheet tSheet, int tRowNum, SSRS tSSRS) {
		
		int tCurrentRow = tRowNum;
		HSSFRow tRow = null;
		
		for (int i=1;i<=tSSRS.getMaxRow();i++) {
			
			tRow = tSheet.createRow(tCurrentRow);
			writeRowData(tRow, tSSRS.getRowData(i));
			tCurrentRow++;
		}
		
		return true;
	}
	
	/**
	 * 写入数据
	 * @param tRow Excel中Sheet中行对像
	 * @param tRowData 写入一行数据
	 * @return 如果失败，则返回false，否则返回true
	 */
	private boolean writeRowData(HSSFRow tRow, String[] tRowData) {
		
		HSSFCell tCell = null;
		for (int i=0;i<tRowData.length;i++) {
			
			tCell = tRow.createCell(i);
			//tCell.setEncoding(HSSFCell.ENCODING_UTF_16);//设置cell编码解决中文高位字节截断
			tCell.setCellValue(tRowData[i]);
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
