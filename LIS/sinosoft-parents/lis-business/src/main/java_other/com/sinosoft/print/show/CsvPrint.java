package com.sinosoft.print.show;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.print.func.PrintFunc;
import com.sinosoft.print.func.XmlFunc;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class CsvPrint implements BusinessService {
	/**
	 * 缓存输入流
	 */
	private InputStream tInputStream;
	/**
	 * 字符标识位
	 */
	private final char FLAG_FLAG = '$';

	/**
	 * 输入流解析类
	 */
	private XmlFunc mXmlFunc;

	/**
	 * 默认的变量模式，唯一变量
	 */
	protected static final String FLAG_ONE_RECORD = "$=";

	/**
	 * 循环变量数据，不唯一变量
	 */
	private final String FLAG_MULTI_RECORD = "$*";

	/**
	 * 行列结束符号
	 */
	protected static final String FLAG_MODEL_END = "$/";

	/**
	 * 日志输出类
	 */
	private Logger mLogger = Logger.getLogger(CsvPrint.class);

	private String mAppPath;

	private TransferData mTransferData;

	private GlobalInput mGlobalInput = new GlobalInput();

	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/**
	 * 缺省的构造函数
	 */
	public CsvPrint() {
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @param cInputData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		tInputStream = (InputStream) cInputData.get(0);
		mGlobalInput = (GlobalInput) cInputData.get(1);
		mAppPath = (String) cInputData.get(2);
		mTransferData = (TransferData) cInputData.get(3);
		return true;
	}

	/**释放资源
	 * @return
	 */
	private boolean gc(){
		try{
			this.mXmlFunc = null;
			this.tInputStream = null;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return true;
	}
	/**
	 * 解析传入流的xml信息
	 * 
	 * @return
	 */
	private boolean parserIS() {
		try {
			mLogger.debug("开始解析传入的xml数据。。。");
			mXmlFunc = new XmlFunc(tInputStream);
			if(tInputStream!=null){
				tInputStream.close();
				tInputStream = null;
			}
			mLogger.debug("传入的xml数据解析完毕。。。");
		} catch (Exception e) {
			mLogger.error("传入的xml信息异常！");
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 读取模板，调用数据，生成文件
	 * 
	 * @param cGlobalInput
	 *            全局信息-操作员等信息
	 * @param cAppPath
	 *            应用文件路径
	 * @param cXmlFunc
	 *            解析
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean printCSV() {
		// 获取全局变量
		String cAppPath = this.mAppPath;
		XmlFunc cXmlFunc = this.mXmlFunc;
		// 获取pdf模板的信息
		String tOutputType = (String) mTransferData.getValueByName("Output");// 输出类型，csv
		String tLanguage = (String) mTransferData.getValueByName("Language");// 语言
		String tTemplateFile = PrintFunc.getTempleteFile(cXmlFunc
				.getPrintName(), tOutputType, tLanguage);// 模板文件名
		String tCSVFileName = PrintFunc.getCsvFileName(cXmlFunc.getPrintName())
				+ ".csv";// 生成文件名
		File csvFile = new File(cAppPath + "vtsfile/" + tCSVFileName);// 生成文件
		BufferedWriter bw;
		try {
			// 单元格对象
			Cell tXlsCell = null;
			Workbook tWorkbook = Workbook.getWorkbook(new File(cAppPath
					+ tTemplateFile));// 解析excel模板
			Sheet tSheet = tWorkbook.getSheet(0);// 只解析第一个工作簿
			// 模板的总行数
			int tMaxRow = tSheet.getRows();
			// 表格的总列数，使用默认模式获得，或者根据$/判断结束
			int tMaxCol = tSheet.getColumns();
			// 读取的当前行
			int tRow;
			// 读取的当前列
			int tCol;
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(csvFile), "GBK"));// 临时文件写入流
			String tStrText = null;// 读取单元格文本
			String tSubText = null;// 单元格前两个字符
			final String SPLIT_B = "\"";
			final String SPLIT_A = ",";
			// 准备完成，开始生成文件
			// 循环模板中的行记录信息
			for (tRow = 0; tRow < tMaxRow; tRow++) {
				// 循环模板中的列记录信息
				/** 当前行的列数 */
				int tCurrentColNum = tSheet.getRow(tRow).length;
				int sizeOfStringBuffer = tCurrentColNum * 8;
				int lengthOfString = 0;
				StringBuffer strRow = new StringBuffer(sizeOfStringBuffer);
				for (tCol = 0; tCol < tMaxCol; tCol++) {
					tXlsCell = tSheet.getCell(tCol, tRow);
					tStrText = tXlsCell.getContents().trim();
					if ("".equalsIgnoreCase(tStrText) || tStrText.length() == 0) {
						// 空白格跳过
					}
					if (tStrText.length() <= 1)// 获取单元格的描述标志
					{
						tSubText = tStrText;
					} else {
						tSubText = tStrText.substring(0, 2);
					}
					if (FLAG_MULTI_RECORD.equals(tSubText))// ||
					// FLAG_DATA_LIST.equals(tSubText))
					{
						tStrText = tStrText.substring(2);
						int tChildPos = tStrText.lastIndexOf("/");
						String tStrXPath = "/DATASET"
								+ tStrText.substring(0, tChildPos);
						String tStrNodeName;
						// 查询循环体数据信息
						cXmlFunc.query(tStrXPath);
						// 确定每个列的在xml中的索引值
						int[] tColIndex = new int[tCurrentColNum];
						for (int i = tCol; i < tCurrentColNum; i++) {
							tStrText = tSheet.getCell(i, tRow).getContents()
									.trim();
							mLogger.debug("-- " + tStrText + " -- " + i);
							if (FLAG_MODEL_END.equals(tStrText)) {
								tCurrentColNum = i;
								break;
							}
							if (!"".equals(tStrText))
								tStrText = tStrText.substring(2);
							if (!"".equals(tStrText)) {
								tStrNodeName = tStrText
										.substring(tChildPos + 1);
							} else {
								tStrNodeName = tStrText;
							}
							tColIndex[i] = cXmlFunc.getColIndex(tStrNodeName);
						}
						while (cXmlFunc.next()) {
							// 循环生成表格列的信息
							for (int i = tCol; i < tCurrentColNum; i++) {
								strRow.append(SPLIT_B).append(
										cXmlFunc.getString(tColIndex[i]))
										.append(SPLIT_B).append(SPLIT_A);
							}
							if (strRow.length() >= 1) {
								lengthOfString = strRow.length() - 1;
								bw.write(strRow.substring(0, lengthOfString)
										.toString());
								if (sizeOfStringBuffer < lengthOfString)
									sizeOfStringBuffer = lengthOfString;
							}
							strRow = new StringBuffer(sizeOfStringBuffer);
							bw.newLine();
							for (int i = 0; i < tCol; i++) {
								strRow.append(SPLIT_B).append("").append(
										SPLIT_B).append(SPLIT_A);
							}
						}
						strRow = new StringBuffer(sizeOfStringBuffer);
						break;// 不再遍历该行
					} else if (FLAG_MODEL_END.equals(tSubText)) {// 一行结束
						bw.write(strRow.toString());
						bw.newLine();
						strRow = new StringBuffer(sizeOfStringBuffer);
						break;
					} else {
						String tTitle = "";
						tTitle += parseString(tStrText, cXmlFunc);
						strRow.append(SPLIT_B).append(tTitle).append(SPLIT_B)
								.append(SPLIT_A);
					}
				}
				if (strRow.length() >= 1)
					bw.write(strRow.substring(0, strRow.length() - 1)
							.toString());
				bw.newLine();
				if (0 == tCol && FLAG_MODEL_END.equals(tSubText)) {// 如果第0列为结束符，则结束遍历
					break;
				}
			}
			bw.close();
		} catch (Exception e) {
			mLogger.error("生成csv文件异常……");
			e.printStackTrace();
			return false;
		} finally {
			this.mResult.add(tCSVFileName);
		}
		return true;
	}

	/**
	 * 解析字符串信息，并整合要替换的数据项
	 * 
	 * @param cStrText
	 * @param cXmlFunc
	 * @return String
	 */
	private String parseString(String cStrText, XmlFunc cXmlFunc) {
		String tStrText = "";
		String tStrTemp = "";
		int tFlag = 0;

		for (int i = 0; i < cStrText.length(); i++) {
			if (FLAG_FLAG == cStrText.charAt(i)) {
				switch (tFlag) {
				case 0: // ordinary string
					tFlag = 1;
					tStrText += tStrTemp;
					tStrTemp = "";
					break;
				case 1: // special string
					tFlag = 0;
					if (tStrTemp.equals("")) {
						tStrText += FLAG_FLAG;
					} else {
						if (tStrTemp.length() > 1 && '=' == tStrTemp.charAt(0)) {
							tStrTemp = tStrTemp.substring(1);
							tStrTemp = cXmlFunc.getNodeValue("/DATASET"
									+ tStrTemp);
						}
						tStrText += tStrTemp;
					}
					tStrTemp = "";
					break;
				}
			} else {
				tStrTemp += cStrText.charAt(i);
			}
		}

		if (1 == tFlag) {
			if (tStrTemp.length() > 1 && '=' == tStrTemp.charAt(0)) {
				tStrTemp = tStrTemp.substring(1);
				tStrTemp = cXmlFunc.getNodeValue("/DATASET" + tStrTemp);
			}
		}

		return tStrText + tStrTemp;
	}

	public boolean submitData(VData vData, String Operater) {
		boolean tFlag = true;
		if (!getInputData(vData)) {
			CError tError = new CError();
			tError.moduleName = "CsvPrint";
			tError.functionName = "getInputData";
			tError.errorMessage = "获得信息异常！";
			mErrors.addOneError(tError);
			tFlag = false;
		}
		// 得到外部传入的数据，将数据备份到本类中
		if (tFlag && !parserIS()) {
			CError tError = new CError();
			tError.moduleName = "CsvPrint";
			tError.functionName = "parserIS";
			tError.errorMessage = "传入的xml信息异常！";
			mErrors.addOneError(tError);
			tFlag = false;
		}
		if (tFlag && !printCSV()) {
			CError tError = new CError();
			tError.moduleName = "CsvPrint";
			tError.functionName = "printCSV";
			tError.errorMessage = "打印异常！";
			mErrors.addOneError(tError);
			tFlag = false;
		}
		if (tFlag && gc()) {
			// 打印成功
			PrintFunc.addPrintLog(mTransferData, "0", "打印成功",
					mGlobalInput.Operator);
		} else {
			// 打印失败，错误信息放到第三个参数
			PrintFunc.addPrintLog(mTransferData, "1", mErrors.getFirstError(),
					mGlobalInput.Operator);
		}
		return tFlag;
	}

}
