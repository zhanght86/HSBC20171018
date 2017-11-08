package com.sinosoft.print.show;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.print.func.PrintFunc;
import com.sinosoft.print.func.XmlFunc;
import com.sinosoft.report.f1report.BarCode;
import com.sinosoft.report.f1report.DBarCode;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * ClassName: PdfPrint
 * </p>
 * <p>
 * Description: Pdf模板打印控制类
 * </p>
 * 
 * @author ZhuXF
 * @version 1.0
 * @modify 2011-1-26
 * @depict 人生五十載，去事恍如夢幻，普天之下，豈有長生不滅者！
 */
public class PdfPrint implements BusinessService {
	/**
	 * 缓存输入流的byte数组
	 */
	private byte[] mbyte;

	/**
	 * 字符标识位
	 */
	protected static final char FLAG_FLAG = '$';

	/**
	 * 输入流解析类
	 */
	private XmlFunc mXmlFunc;

	/**
	 * 日志输出类
	 */
	private static Logger mLogger = Logger.getLogger(PdfPrint.class);

	/**
	 * 临时文件名
	 */
	private String mTempFileName;

	private String mAppPath;

	private TransferData mTransferData;

	private GlobalInput mGlobalInput = new GlobalInput();

	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/**
	 * 缺省的构造函数
	 */
	public PdfPrint() {
	}

	/**
	 * 解析传入流的xml信息
	 * 
	 * @param cInputStream
	 * @return
	 */
	private boolean parserIS(InputStream cInputStream) {
		try {
			mLogger.debug("开始解析传入的xml数据。。。");
			mXmlFunc = new XmlFunc(cInputStream);
			mLogger.debug("传入的xml数据解析完毕。。。");
		} catch (Exception e) {
			mLogger.error("传入的xml信息异常！");
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 有模板的pdf打印
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean printPDF(GlobalInput cGlobalInput, String cAppPath) {

		try {
			// 获取pdf模板的信息
			String tOutputType = (String) mTransferData
					.getValueByName("OutputType");
			String tLanguage = (String) mTransferData
					.getValueByName("Language");
			String tTemplateFile = PrintFunc.getTempleteFile(mXmlFunc
					.getPrintName(), tOutputType, tLanguage);

			ByteArrayOutputStream ba = new ByteArrayOutputStream();
			PdfReader reader = new PdfReader(cAppPath + tTemplateFile);
			PdfStamper stamp = new PdfStamper(reader, ba);
			PdfContentByte under = stamp.getUnderContent(1);
			BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
					BaseFont.NOT_EMBEDDED);
			// BaseFont bf =
			// BaseFont.createFont("c:\\windows\\fonts\\simsun.ttc,1",
			// BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

			Font FontChinese = new Font(bf, 14, Font.NORMAL);
			AcroFields form = stamp.getAcroFields();

			HashMap acroFieldMap = form.getFields();
			Iterator i = acroFieldMap.keySet().iterator();

			// 可以得到全部的控件名，这个东西需要考虑一下实现。。。
			while (i.hasNext()) {
				// 获得块名
				String fieldName = (String) i.next();
				// 通过块名获得块
				AcroFields.Item item = form.getFieldItem(fieldName);
				// 通过块名获得里面的值
				String value = form.getField(fieldName);
				mLogger.debug("fieldName is " + fieldName + "____"
						+ item.values + " ___ value is " + value);

				String tTextName = "";
				if (fieldName.length() < 1) {
					continue;
				} else if (fieldName.length() == 1) {
					continue;
				} else {
					tTextName = fieldName.substring(0, 2);
				}

				if (tTextName.equals("$=")) {
					String tValue = parseString(fieldName, mXmlFunc);
					form.setField(fieldName, tValue);
				}

			}

			/**
			 * 条形码处理
			 */
			// 设置循环处理的BarCodeInfo逻辑
			String tBarCodePath = "/DATASET/BarCodeInfo/BarCode";

			mXmlFunc.query(tBarCodePath);
			int tCodeIndex = mXmlFunc.getColIndex("Code");
			int tNameIndex = mXmlFunc.getColIndex("Name");
			int tBarTypeIndex = mXmlFunc.getColIndex("Type");

			// 条形码生成逻辑判断
			String tCode = "";
			String tName = "";
			String tBarType = "";

			String tStrPosition = "";
			String[] tPosition = null;
			Image tImage = null;
			while (mXmlFunc.next()) {
				tName = mXmlFunc.getString(tNameIndex);
				tCode = mXmlFunc.getString(tCodeIndex);
				tBarType = mXmlFunc.getString(tBarTypeIndex);

				// 获取要打印的条形码对象的坐标字符串
				tStrPosition = (String) mTransferData.getValueByName("B"
						+ tName);
				// 根据，对字符串进行拆分，与获得实际坐标
				tPosition = tStrPosition.split(",");

				// 使用什么条形码类型
				if (tBarType.equals("DBarCode")) {
					// 二维条码处理
					DBarCode tDBarCode = new DBarCode(tCode);
					tImage = Image.getInstance(tDBarCode.getBytes());
					// 设置图片的绝对位置
					tImage.setAbsolutePosition(Float.valueOf(tPosition[0]),
							Float.valueOf(tPosition[1]));
					under.addImage(tImage);
				} else {
					// 一维条码处理
					BarCode tBarCode = new BarCode(tCode);
					tImage = Image.getInstance(tBarCode.getBytes());
					// 设置图片的绝对位置
					tImage.setAbsolutePosition(Float.valueOf(tPosition[0]),
							Float.valueOf(tPosition[1]));
					under.addImage(tImage);
				}
			}
			stamp.setFormFlattening(true);
			stamp.close();

			mTempFileName = PrintFunc.getTempFileName(cGlobalInput) + ".pdf";

			FileOutputStream of = new FileOutputStream(cAppPath + "vtsfile/"
					+ mTempFileName);
			of.write(ba.toByteArray());
			of.close();

			mResult.add(mTempFileName);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static String parseString(String str, XmlFunc cXmlFunc) {
		String strNew = "";
		String strTemp = "";
		int nFlag = 0;

		for (int nIndex = 0; nIndex < str.length(); nIndex++) {
			if (FLAG_FLAG == str.charAt(nIndex)) {
				switch (nFlag) {
				case 0: // ordinary string
					nFlag = 1;
					strNew += strTemp;
					strTemp = "";
					break;
				case 1: // special string
					nFlag = 0;
					if (strTemp.equals("")) {
						strNew += FLAG_FLAG;
					} else {
						if (strTemp.length() > 1 && '=' == strTemp.charAt(0)) {
							strTemp = strTemp.substring(1);
							strTemp = cXmlFunc.getNodeValue("/DATASET"
									+ strTemp);
						}
						strNew += strTemp;
					}
					strTemp = "";
					break;
				}
			} else {
				strTemp += str.charAt(nIndex);
			}
		} // end of for

		if (1 == nFlag) { // special string
			if (strTemp.length() > 1 && '=' == strTemp.charAt(0)) {
				strTemp = strTemp.substring(1);
				strTemp = cXmlFunc.getNodeValue("/DATASET" + strTemp);
			}
		}

		return strNew + strTemp;
	}

	/**
	 * 测试函数
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "001";

		File tFile = new File("c:/printtest/1.xml");
		FileInputStream tFis = new FileInputStream(tFile);

		Object tObj = tFis;

		@SuppressWarnings("unused")
		InputStream is = (InputStream) tObj;

		@SuppressWarnings("unused")
		PdfPrint tSP = new PdfPrint();
	}

	public boolean submitData(VData cVData, String cOperater) {
		boolean tFlag = true;

		if (!getInputData(cVData)) {
			CError tError = new CError();
			tError.moduleName = "PdfPrint";
			tError.functionName = "getInputData";
			tError.errorMessage = "获得信息异常！";
			mErrors.addOneError(tError);
			tFlag = false;
		}
		// 得到外部传入的数据，将数据备份到本类中
		if (tFlag && !parserIS(new ByteArrayInputStream(mbyte))) {
			CError tError = new CError();
			tError.moduleName = "PdfPrint";
			tError.functionName = "parserIS";
			tError.errorMessage = "传入的xml信息异常！";
			mErrors.addOneError(tError);
			tFlag = false;
		}

		if (tFlag && !printPDF(mGlobalInput, mAppPath)) {
			CError tError = new CError();
			tError.moduleName = "PdfPrint";
			tError.functionName = "printPDF";
			tError.errorMessage = "打印异常！";
			mErrors.addOneError(tError);
			tFlag = false;
		}
		if (tFlag) {
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

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		InputStream tInputStream = (InputStream) cInputData.get(0);
		mbyte = PrintFunc.transformInputstream(tInputStream);
		mGlobalInput = (GlobalInput) cInputData.get(1);
		mAppPath = (String) cInputData.get(2);
		mTransferData = (TransferData) cInputData.get(3);

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
