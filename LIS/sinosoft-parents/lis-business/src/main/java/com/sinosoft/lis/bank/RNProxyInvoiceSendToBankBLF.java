package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.text.DecimalFormat;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.sinosoft.lis.db.LDBankDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LYBankBillSchema;
import com.sinosoft.lis.vschema.LYBankBillSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 银行代打发票
 * </p>
 * 
 * <p>
 * Description: 银行代打发票生成数据库文件模块
 * </p>
 * 
 * <p>
 * 在生成送银行的发票时,由于数据量过大所以在此处的BLF不是用来进行统一提交的类,
 * 
 * 而是一个被BL调用,用来循环生成xml以及解析文件的类
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author : guanwei
 * 
 * @version 1.0
 */
public class RNProxyInvoiceSendToBankBLF {
private static Logger logger = Logger.getLogger(RNProxyInvoiceSendToBankBLF.class);
	public RNProxyInvoiceSendToBankBLF() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// 错误处理
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private VData tVData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private GlobalInput mGI = new GlobalInput();
	/** 业务数据 */
	private LYBankBillSet mLYBankBillSet = new LYBankBillSet();
	private TransferData mTransferData = new TransferData();
	private String mBankCode = "";
	private String FileName = "";
	private String FilePath = "";
	private int temp = 0;
	PrintWriter out = null;

	public VData getResult() {
		return mResult;
	}

	/**
	 * ====================下面的方法是用来处理得到的输入数据=========== 在此处得到的值是一些基本数据
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	public boolean getInputData(TransferData tTransferData, GlobalInput tGI) {
		try {
			this.mTransferData = tTransferData;
			mGI = tGI;
			FileName = (String) mTransferData.getValueByName("FileName")
					+ ".xml";
			FilePath = FileName.substring(0, FileName.lastIndexOf("/"));
			mBankCode = (String) mTransferData.getValueByName("BankCode");
			logger.debug("filePath: " + FilePath + " | bankCode: "
					+ mBankCode);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RNProxyInvoiceSendToBankBLF";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 下面的处理是得到每次得到的需要打印的数据
	 * 
	 * @return void
	 */
	public void setLYBankBillSet(LYBankBillSet tLYBankBillSet) {
		// this.mLYBankBillSet.clear();
		this.mLYBankBillSet = tLYBankBillSet;
	}

	/**
	 * ======================下面是基本的处理方法===================
	 * ==========下面的两个方法是调用javax.xml包进行数据解析的========= 获取xsl文件路径
	 * 
	 * @return
	 */
	public String getXslPath() {
		LDBankDB tLDBankDB = new LDBankDB();

		tLDBankDB.setBankCode(mBankCode);
		tLDBankDB.getInfo();

		return tLDBankDB.getChkSendF();
	}

	/**
	 * Simple sample code to show how to run the XSL processor from the API.
	 */
	public boolean xmlTransform() {
		try {
			if (mBankCode != null && !mBankCode.equals("null")
					&& !mBankCode.equals("")) {
				int i = 0;
				String xslPath = getXslPath();

				xslPath = xslPath.substring(0, xslPath.length() - 4) + ".xsl";

				logger.debug("xslPath:" + xslPath);

				File fSource = new File(FileName);
				File fStyle = new File(xslPath);

				Source source = new StreamSource(fSource);
				Result result = new StreamResult(new FileOutputStream(FileName
						.substring(0, FileName.lastIndexOf("."))
						+ ".z"));
				Source style = new StreamSource(fStyle);

				// Create the Transformer
				TransformerFactory transFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transFactory.newTransformer(style);

				// Transform the Document
				transformer.transform(source, result);
			}
			String xslPath = getXslPath();
			logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			logger.debug("xslPath:" + xslPath);

			File fSource = new File(FileName);
			File fStyle = new File(xslPath);

			Source source = new StreamSource(fSource);
			Result result = new StreamResult(new FileOutputStream(FileName
					.substring(0, FileName.lastIndexOf("."))
					+ ".d"));
			Source style = new StreamSource(fStyle);

			// Create the Transformer
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer(style);

			// Transform the Document
			transformer.transform(source, result);
			logger.debug("Transform Success!");
		} catch (Exception e) {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RNProxyInvoiceSendToBankBLF";
			tError.functionName = "SimpleTransform";
			tError.errorMessage = "Xml处理失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * ======================开始通过IO包写文件=============================== 因为
	 * 在传入是一次5000的传入,所以要设定一个统一的写文件方法,这样可以增量写入 下面涉及三个 方法 ,写文件头, 文件体, 写文件尾
	 * 
	 * @throws Exception
	 */
	public boolean WriterFileHead() {
		try {
			logger.debug("Start creating file ......");
			logger.debug("File Name : " + FileName);
			logger.debug("头标志为:" + temp);

			// 生成文件头信息
			out = new PrintWriter(new FileWriter(new File(FileName)), true);
			out.println("<?xml version=\"1.0\" encoding=\"gbk\"?>");
			out
					.println("<?xml-stylesheet type=\"text/xsl\" href=\"BankDataFormat.xsl\"?>");
			out.println("");
			out.println("<!--");
			out.println(" * <p>FileName: " + FileName + " </p>");
			out.println(" * <p>Description: 送银行发票打印文件 </p>");
			out.println(" * <p>Copyright: Copyright (c) 2006</p>");
			out.println(" * <p>Company: sinosoft </p>");
			out.println(" * @Author：guanwei's RNProxyInvoiceSendToBankBLF");
			out.println(" * @CreateDate：" + PubFun.getCurrentDate());
			out.println("-->");
			out.println("");
			out.println("<BANKDATA>");
		} catch (Exception e) {
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "RNProxyInvoiceSendToBankBLF";
			tError.functionName = "submitData";
			tError.errorMessage = e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public boolean WriterFileBody() {
		try {
			for (int i = 0; i < mLYBankBillSet.size(); i++) {
				LYBankBillSchema tLYBankBillSchema = mLYBankBillSet.get(i + 1);
				Class c = tLYBankBillSchema.getClass();
				Field f[] = c.getDeclaredFields();
				out.println("<ROW>");
				for (int elementsNum = 0; elementsNum < f.length; elementsNum++) {
					out.print("    <" + f[elementsNum].getName() + ">");
					if (f[elementsNum].getName().equals("PayMoney")) {
						out.print((new DecimalFormat("0.00")).format(Double
								.parseDouble(tLYBankBillSchema
										.getV(f[elementsNum].getName()))));
					} else {
						out.print(tLYBankBillSchema.getV(f[elementsNum]
								.getName()));
					}
					out.println("</" + f[elementsNum].getName() + ">");
				}
				out.println("  </ROW>");
				out.println("");
				out.println(tLYBankBillSchema.encode());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "RNProxyInvoiceSendToBankBLF";
			tError.functionName = "submitData";
			tError.errorMessage = ex.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public void WriterFileEnd() {
		out.println("</BANKDATA>");
		out.println("");
		out.println("<!-- Create BankFile Success! -->");
		out.close();
	}

	private void jbInit() throws Exception {
	}
}
