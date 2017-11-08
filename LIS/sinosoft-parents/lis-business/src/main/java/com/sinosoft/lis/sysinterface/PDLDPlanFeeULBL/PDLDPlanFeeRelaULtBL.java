

package com.sinosoft.lis.sysinterface.PDLDPlanFeeULBL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.enterprisedt.util.debug.Logger;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.PD_LCalculatorFactorSchema;
import com.sinosoft.lis.schema.PD_LCalculatorFeeCodeSchema;
import com.sinosoft.lis.schema.PD_LCalculatorInsuranceTimeSchema;
import com.sinosoft.lis.schema.PD_LCalculatorMainSchema;
import com.sinosoft.lis.schema.PD_LCalculatorNatureTimeSchema;
import com.sinosoft.lis.schema.PD_LCalculatorOrderSchema;
import com.sinosoft.lis.schema.PD_LDPlanFeeRelaSchema;
import com.sinosoft.lis.sysinterface.PDLCalculatorULBL.PDLCalculatorULtBL;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.vschema.PD_LCalculatorFactorSet;
import com.sinosoft.lis.vschema.PD_LCalculatorFeeCodeSet;
import com.sinosoft.lis.vschema.PD_LCalculatorInsuranceTimeSet;
import com.sinosoft.lis.vschema.PD_LCalculatorMainSet;
import com.sinosoft.lis.vschema.PD_LCalculatorNatureTimeSet;
import com.sinosoft.lis.vschema.PD_LCalculatorOrderSet;
import com.sinosoft.lis.vschema.PD_LDPlanFeeRelaSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;


public class PDLDPlanFeeRelaULtBL{

	//公用變量
	private ExeSQL exeSQL = new ExeSQL();
	List<String> checkEdorTypes = new ArrayList<String>();
	PD_LDPlanFeeRelaSchema tPD_LDPlanFeeRelaSchema=new PD_LDPlanFeeRelaSchema();
	PD_LCalculatorFeeCodeSchema tPD_LCalculatorFeeCodeSchema=new PD_LCalculatorFeeCodeSchema();
	
	PD_LDPlanFeeRelaSet tPD_LDPlanFeeRelaSet=new PD_LDPlanFeeRelaSet();
	PD_LCalculatorFeeCodeSet tPD_LCalculatorFeeCodeSet=new PD_LCalculatorFeeCodeSet();
	
	PubSubmit tSubmit = new PubSubmit();
	
	/** 系统时间 */
	String mCurrentDate = PubFun.getCurrentDate();
	String mCurrentTime = PubFun.getCurrentTime();
	String mCurrentDealDate ;
	
	private Map<String,String> currencyMap = new HashMap<String,String>();
    private Map<String,String> currencyMap1 = new HashMap<String,String>();
    protected MMap fileMap = new MMap();
    public Logger logger=Logger.getLogger(PDLCalculatorULtBL.class);
    private int count=0;
    private int count1=0;
	
    private VData mResult = new VData();
    public CErrors mErrors = new CErrors();
    public GlobalInput mGlobalInput=new GlobalInput();
    protected MMap map = new MMap();
    protected VData mInputData = new VData();
    
	public PDLDPlanFeeRelaULtBL() {
		
	}
	
	public boolean submitData(String crnFilePath, String crnFileName,VData cInputData){
		if (!getInputData(cInputData)) {
			CError tError = new CError();
			tError.moduleName = "LDRiskCommissionULBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "数据接收失败！";
			this.mErrors.addOneError(tError);
			logger.debug("------getInputData Failed----");
			return false;
		}
//		if (!initData()) {
//			buildError("initData", "初始化數據失敗");
//			log.debug("- Init Data Failed -");
//			addIFProcessLogInfo(false);
//			return false;
//		}
		if (!saveCrnMessage(crnFilePath, crnFileName)) {
			CError tError = new CError();
			tError.moduleName = "LDRiskCommissionULBL";
			tError.functionName = "saveCrnMessage";
			tError.errorMessage = "解析数据失败！";
			this.mErrors.addOneError(tError);
			logger.debug("------saveCrnMessage Failed----");
			return false;
		}
		if(!dealData()){
        	buildError("dealData", "處理數據失敗");
        	logger.debug("--dealData Failed--");
			return false;
		}
		if (!prepareOutputData()) {
			return false;
		}
//		PubSubmit tSubmit = new PubSubmit();
//		if (!tSubmit.submitData(mInputData, "")) {
//			CError tError = new CError();
//			tError.moduleName = "PDLCalculatorULtBL";
//			tError.functionName = "prepareOutputData";
//			tError.errorMessage = "数据提交失败。";
//			this.mErrors.addOneError(tError);
//			return false;
//		}
		return true;
	}
	
	public Boolean saveCrnMessage(String crnFilePath, String crnFileName) {
		String toPage = new String();
		StringBuilder sb2 = new StringBuilder();
		
		List<String> tempStrList = new ArrayList<String>();
		List<String> errorStrList = new ArrayList<String>();
		
		StringBuilder result = new StringBuilder();
		result.append("upload file save path: [" + crnFilePath + "]\r\n");
		toPage = toPage + "name: [" + crnFilePath + crnFileName + "]";
		result.append("upload file save name: [" + crnFileName + "]\r\n");
		
		String currentDay = PubFun.getCurrentDate();
		String currentTime = PubFun.getCurrentTime();
		
        String id = String.valueOf(System.nanoTime());

		MMap sqlMap = new MMap();
		
		try {
			DecimalFormat df = new DecimalFormat("#.##"); 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			File file = new File(crnFilePath + crnFileName);
			try {

				if ("xls".equals(crnFileName.split("\\.")[1])) {
					// 创建对Excel工作簿文件的引用 
					HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file)); 
					/** 
				     *获取sheet的个数 
				     *workbook.getNumberOfSheets() 
				     **/ 
				    //System.out.println("===SheetsNum===" + workbook.getNumberOfSheets());//获取sheet数
					for (int numSheets = 0; numSheets < workbook.getNumberOfSheets(); numSheets++) {
						if (null != workbook.getSheetAt(numSheets)) { 
							HSSFSheet aSheet = workbook.getSheetAt(numSheets);//获得一个sheet 
							//System.out.println("+++getFirstRowNum+++" + 
							//aSheet.getFirstRowNum());// 
							//System.out.println("+++getLastRowNum+++" + 
							//aSheet.getLastRowNum());
							for (int rowNumOfSheet = 0; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) { 
								if (null != aSheet.getRow(rowNumOfSheet)) {
									StringBuilder tempStringRow = new StringBuilder();
									
									HSSFRow aRow = aSheet.getRow(rowNumOfSheet); 
									//System.out.println(">>>getFirstCellNum<<<"+ 
									// aRow.getFirstCellNum()); 
									//System.out.println(">>>getLastCellNum<<<"+ 
									// aRow.getLastCellNum()); 
									for (short cellNumOfRow = 0; cellNumOfRow <= aRow.getLastCellNum(); cellNumOfRow++) { 
										if (null != aRow.getCell(cellNumOfRow)) {
											HSSFCell aCell = aRow.getCell(cellNumOfRow); 
											int cellType = aCell.getCellType();
											//System.out.println(cellType);
											//System.out.println(cellNumOfRow);
											String strCell = "";
											switch (cellType) {
												case HSSFCell.CELL_TYPE_NUMERIC://Numeric
													if (aCell.getCellStyle().getDataFormat() == 180) {
														strCell = sdf.format(aCell.getDateCellValue());
													}else {
														strCell = df.format(aCell.getNumericCellValue());
														if (strCell == null || "".equals(strCell)) {
															strCell = "0";
														}
													}
													tempStringRow.append(strCell);
													break; 
												case HSSFCell.CELL_TYPE_STRING://String
													strCell = aCell.getStringCellValue();
													tempStringRow.append(strCell);
													break;
												case HSSFCell.CELL_TYPE_BLANK://
													strCell = aCell.getStringCellValue();
													break;
												default:
													//System.out.println("格式不对不读");//其它格式的数据 
											}
											tempStringRow.append("|");
										}else{
											tempStringRow.append(" ");
											tempStringRow.append("|");
										}
									}
									tempStrList.add(tempStringRow.toString());
								}
							}
						}
					}
				}else{
			        return false;
				}
			} catch (Exception e) { 
				//System.out.println("ReadError:" + e);
				this.logger.error("ReadError:" + e);
			} 

			for (String listStr : tempStrList) {
				sb2.append(listStr);
				sb2.append("\r\n");
			}
			
			int i = 0;
			try{
			for (String tempRowStr : tempStrList) {
				int i1=1;
				if (i > 0) {
					String[] tempCellStrs = tempRowStr.split("\\|");
					if (tempCellStrs.length > 5){
						tPD_LDPlanFeeRelaSchema =new PD_LDPlanFeeRelaSchema();
						tPD_LCalculatorFeeCodeSchema=new PD_LCalculatorFeeCodeSchema();
						
						/*pd_ldplanfeerela*/
						tPD_LDPlanFeeRelaSchema.setPlanCode(tempCellStrs[i1].trim());
						tPD_LDPlanFeeRelaSchema.setRiskCode(tempCellStrs[++i1].trim());
						tPD_LDPlanFeeRelaSchema.setDutyCode(tempCellStrs[++i1].trim());
						tPD_LDPlanFeeRelaSchema.setGetDutyCode(tempCellStrs[++i1].trim());
						tPD_LDPlanFeeRelaSchema.setStandFlag1(tempCellStrs[++i1].trim());//是否扣除自付比例
						tPD_LDPlanFeeRelaSchema.setStandFlag2(tempCellStrs[++i1].trim());//自付比例
						tPD_LDPlanFeeRelaSchema.setPreAuthFlag(tempCellStrs[++i1].trim());//免赔额
						tPD_LDPlanFeeRelaSchema.setFeeCode(tempCellStrs[++i1].trim());
						tPD_LDPlanFeeRelaSchema.setFeeType(tempCellStrs[++i1].trim());
						tPD_LDPlanFeeRelaSchema.setMoneyLimitAnnual(tempCellStrs[++i1].trim());
						tPD_LDPlanFeeRelaSchema.setCountLimitAnnual(tempCellStrs[++i1].trim());
						tPD_LDPlanFeeRelaSchema.setDaysLimitAnnual(tempCellStrs[++i1].trim());
						tPD_LDPlanFeeRelaSchema.setMoneyLimitEveryTime(tempCellStrs[++i1].trim());
						tPD_LDPlanFeeRelaSchema.setDaysLimitEveryTime(tempCellStrs[++i1].trim());
						tPD_LDPlanFeeRelaSchema.setMoneyLimitEveryDay(tempCellStrs[++i1].trim());
						tPD_LDPlanFeeRelaSchema.setPayMoneyEveryDay(tempCellStrs[++i1].trim());
						tPD_LDPlanFeeRelaSchema.setObsPeriod(tempCellStrs[++i1].trim());
						tPD_LDPlanFeeRelaSchema.setObsPeriodUn(tempCellStrs[++i1].trim());
						tPD_LDPlanFeeRelaSchema.setManageCom(tempCellStrs[++i1].trim());
						tPD_LDPlanFeeRelaSchema.setOperator(mGlobalInput.Operator);
						tPD_LDPlanFeeRelaSchema.setMakeDate(currentDay);
						tPD_LDPlanFeeRelaSchema.setMakeTime(currentTime);
						tPD_LDPlanFeeRelaSchema.setModifyDate(currentDay);
						tPD_LDPlanFeeRelaSchema.setModifyTime(currentTime);
						
						
						tPD_LDPlanFeeRelaSet.add(tPD_LDPlanFeeRelaSchema);
						
			        	count1++;
					}
					}
//				}else {
//				}
				i++;
				}
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "submit";
			tError.functionName = "submitData";
			tError.errorMessage = "數據提交時失敗!";
			this.mErrors.addOneError(tError);
			
			StringBuilder sb = new StringBuilder();
			for (String errorStr : errorStrList) {
				sb.append(errorStr);
				sb.append("\r\n");
			}
		} catch (Exception ex) {
        	Writer wResult = new StringWriter();
        	PrintWriter printWriter = new PrintWriter(wResult);
        	ex.printStackTrace(printWriter);
	    	ex.printStackTrace();
	    	return false;
		}
		return true;
	}
	

	/**
	   * 生成错误信息
	   * @param FuncName
	   * @param ErrMsg
	   */
	private void dealError(String FuncName,String ErrMsg)
	{
		CError tError = new CError();
	    tError.moduleName = "PDLCalculatorULtBL";
	    tError.functionName = FuncName;
	    tError.errorMessage = ErrMsg;	  
		mErrors.addOneError(tError);		
	}
	
	private boolean dealData() {
		/*pd_ldplanfeerela*/
		map.put(tPD_LDPlanFeeRelaSet, "INSERT");
		if (!prepareOutputData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PDLCalculatorULtBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "数据处理失败StudentBL-->prepareOutputData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		VData mResult = new VData();
		mResult.add(map);
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) { // 数据提交
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PDLCalculatorULtBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {
		this.mInputData = cInputData;
		try {		
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败！");
			return false;
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
			tError.moduleName = "PDLCalculatorULtBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
	
	
	/** 編輯錯誤信息 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "PDLCalculatorULtBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
	
	public static void main(String[] args) {
//		PDLCalculatorULtBL tPDLCalculatorULtBL = new PDLCalculatorULtBL();
//		tPDLCalculatorULtBL.saveCrnMessage("D:\\test\\", "YYCB.xls");
//		
//		PD_LACommissionRateSchema tLACommissionRateSchema = new PD_LACommissionRateSchema();
//		tLACommissionRateSchema.setIDNo("0000012304");
		//System.out.println(tLACommissionRateSchema.getIDNo());
	}

	public boolean submitData(VData cInputData, String tOperate) {
		return false;
	}

	public VData getResult() {
		TransferData inputdate = new TransferData();
		inputdate.setNameAndValue("count", count1);
		this.mResult.add(inputdate);
		return mResult;
	}

//	@Override
//	public boolean logicBL() {
//		return false;
//	}
	
	
}

