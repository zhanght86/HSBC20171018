package com.sinosoft.print.show;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.f1j.ss.BookModelImpl;
import com.f1j.util.F1Exception;
import com.sinosoft.lis.f1print.AccessVtsFile;
import com.sinosoft.lis.f1print.VtsFileCombine;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.print.func.PrintFunc;
import com.sinosoft.print.func.XmlFunc;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.F1PrintParser;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * ClassName: VtsPrint
 * </p>
 * <p>
 * Description: Vts模板打印控制类，需要兼容以往的Vts打印输出
 * </p>
 * 
 * @author ZhuXF
 * @version 1.0
 * @modify 2011-1-26
 * @depict 人生五十載，去事恍如夢幻，普天之下，豈有長生不滅者！
 */
public class VtsPrint implements BusinessService {

	/**
	 * 缓存输入流的byte数组
	 */
	private byte[] mbyte;

	/**
	 * 输入流解析类
	 */
	private XmlFunc mXmlFunc;

	/**
	 * 日志输出类
	 */
	private Logger mLogger = Logger.getLogger(VtsPrint.class);

	/**
	 * 输出流
	 */
	private ByteArrayOutputStream mOutputStream = new ByteArrayOutputStream();

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
	public VtsPrint() {
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
	 * 测试函数
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String[] tArrBarCode = "".split("!");
		if (tArrBarCode.length > 0) {
			// String[] tArrParam = tArrBarCode[0].split("#", -1);
			// String[] tArrAlocations = tArrBarCode[1].split("#", -1);
			// String[] tArrCode = tArrBarCode[2].split("#", -1);
			// String[] tBarType = tArrBarCode[2].split("#", -1);

			// tMyPrintJob.setBarCode(tArrParam, tArrAlocations, tArrCode,
			// tBarType);
		}
		// tSP.transformInputstream(tFis);
	}

	@SuppressWarnings("unchecked")
	public boolean submitData(VData cVData, String cOperater) {
		boolean tFlag = true;
		if (!getInputData(cVData)) {
			CError tError = new CError();
			tError.moduleName = "VtsPrint";
			tError.functionName = "getInputData";
			tError.errorMessage = "获得信息异常！";
			mErrors.addOneError(tError);
			tFlag = false;
		}

		// 得到外部传入的数据，将数据备份到本类中
		if (tFlag && !parserIS(new ByteArrayInputStream(mbyte))) {
			CError tError = new CError();
			tError.moduleName = "VtsPrint";
			tError.functionName = "parserIS";
			tError.errorMessage = "传入的xml信息异常！";
			mErrors.addOneError(tError);
			tFlag = false;
		}

		// 判断xml的标签中是否含有CreateType属性（兼顾部分打印数据可能出现的该属性的缺失）
		String tCreateType = changeCreateType(mXmlFunc.getPrintName(),mXmlFunc.getCreateType());
	
		mLogger.debug("CreateType is " + tCreateType);

		mAppPath = mAppPath.replace("\\", "/");

		if (tFlag && XmlExportNew.TAG_CREATE_TYPE.equals(tCreateType)) {
			mLogger.info("进入新模式的vts打印分支");
			try {
				// 懒得改了，应该用个VData对象比较好
				VtsShow tVS = new VtsShow(mXmlFunc, mAppPath, mTransferData);
				mLogger.debug("开始处理输出流信息");
				if (!tVS.output(mOutputStream)) {
					mLogger
							.error("F1PrintKernel.jsp : fail to parse print data");
				}
				mLogger.debug("完成输出流信息处理");

				mTempFileName = PrintFunc.getTempFileName(mGlobalInput)
						+ ".vts";
				mResult.add(mOutputStream);
				mResult.add(mTempFileName);

				AccessVtsFile.saveToFile(mOutputStream, mAppPath + "vtsfile/"
						+ mTempFileName);

				// 判断vts是否需要合并
				String strXPath = "/DATASET/CombineInfo/CombineFile";
				mXmlFunc.query(strXPath);
				int tFileNameIndex = mXmlFunc.getColIndex("FileName");
				ArrayList tArry = new ArrayList();// 存放要合并的文件名的集合
				String tFileName = "";// 要合并的子文件名
				tArry.add(mTempFileName);// 添加父文件名
				while (mXmlFunc.next()) {
					tFileName = mXmlFunc.getString(tFileNameIndex);// 得到合并的子文件名
					if (tFileName == null) {
						mLogger.error("模板中没有要合并的文件名");
						break;
					} else {
						tArry.add(tFileName);// 添加子文件名
					}
				}
				// 如果有两个以上的文件，则进行合并
				if (tArry.size() >= 2) {
					try {
						String tPath = mAppPath + "print/templete/combine/";// 子文件路径
						VtsFileCombine vtsfilecombine = new VtsFileCombine();
						BookModelImpl tb = new BookModelImpl();
						String[] tFileNames = new String[tArry.size()];// 存放所有合并文件名路径的数组
						tFileNames[0] = mAppPath + "vtsfile/"
								+ ((String) tArry.get(0));// 得到父文件全路径
						for (int i = 1; i < tArry.size(); i++) {
							// 得到全部子文件的全路径
							tFileNames[i] = tPath + (String) tArry.get(i);
						}
						tb = vtsfilecombine.dataCombine(tFileNames);// 合并文件
						ByteArrayOutputStream tOutput = new ByteArrayOutputStream();
						vtsfilecombine.write(tb, tOutput);// 把合成后的文件写入输出流中
						mTempFileName = PrintFunc.getTempFileName(mGlobalInput)
								+ ".vts";
						mResult.clear();
						mResult.add(tOutput);
						mResult.add(mTempFileName);
						AccessVtsFile.saveToFile(tOutput, mAppPath + "vtsfile/"
								+ mTempFileName);
					} catch (IOException ioe) {
						CError tError = new CError();
						tError.errorMessage = "合并文件读取异常！";
						mErrors.addOneError(tError);
						tFlag = false;
					} catch (F1Exception f1e) {
						CError tError = new CError();
						tError.errorMessage = "合并文件出错！";
						mErrors.addOneError(tError);
						tFlag = false;
					} catch (Exception ex) {
						CError tError = new CError();
						tError.errorMessage = "合并文件异常！";
						mErrors.addOneError(tError);
						tFlag = false;
					}
				}
			}

			catch (FileNotFoundException e) {
				CError tError = new CError();
				tError.errorMessage = "文件没找到！";
				mErrors.addOneError(tError);
				tFlag = false;
			} catch (IOException e) {
				CError tError = new CError();
				tError.errorMessage = "文件读取异常！";
				mErrors.addOneError(tError);
				tFlag = false;
			}
			mLogger.info("完成新模式的vts打印分支");
		} else if (tFlag) {
			mLogger.info("进入旧模式的vts打印分支");
			// 兼容原有的xml格式vts打印
			try {
				// 获取模板文件的路径，使用配置文件定义
				String tTempletePath = PrintFunc.getTempletePath();

				mLogger.debug("通过输入流创建F1PrintParser对象");
				F1PrintParser tF1PrintParser = new F1PrintParser(
						new ByteArrayInputStream(mbyte), mAppPath
								+ tTempletePath);
				mLogger.debug("开始处理输出流信息");
				if (!tF1PrintParser.output(mOutputStream)) {
					mLogger
							.error("F1PrintKernel.jsp : fail to parse print data！");
				}
				mLogger.debug("完成输出流信息处理");
			} catch (FileNotFoundException e) {
				CError tError = new CError();
				tError.errorMessage = "文件没找到！";
				mErrors.addOneError(tError);
				tFlag = false;
			} catch (IOException e) {
				CError tError = new CError();
				tError.errorMessage = "文件读取异常！";
				mErrors.addOneError(tError);
				tFlag = false;
			}
			mLogger.info("完成旧模式的vts打印分支");
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
		// TODO Auto-generated method stub
		return mResult;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	/**
	 * 此函数是为了解决旧打印模板的兼容性，一些打印模板已使用打印引擎工作，但其数据准备中没有准备
	 * 正确的createtype这个属性（早先写入数据库的数据），在此将其调整为最新的打印方式。
	 * 如果数据准备正确或者以下几种打印名称修改打印方式，请从下列条件中删除
	 * 
	 * @param printName 可能出现数据不正确的打印模板名称
	 * @param createType 原有的createType：决定了使用新的vts打印还是旧的vts打印
	 * @return String 处理过的数据类型
	 */
	private String changeCreateType(String printName, String createType) {
		String rCreateType = createType;
		if (printName.equals("个人保全申请书") || printName.equals("团体保全申请书")
				|| printName.equals("个人保全批单") || printName.equals("团体保全批单")) {
			rCreateType = XmlExportNew.TAG_CREATE_TYPE;
		}
		return rCreateType;
	}
}
