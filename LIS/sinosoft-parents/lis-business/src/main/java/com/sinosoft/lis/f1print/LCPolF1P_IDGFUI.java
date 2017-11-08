package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/*
 * <p>ClassName: LCPolF1PUI </p>
 * <p>Description: LCPolF1PUI类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-11-04
 */
public class LCPolF1P_IDGFUI implements BusinessService {
private static Logger logger = Logger.getLogger(LCPolF1P_IDGFUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 返回结果容器 */
	private VData mResult = new VData();

    public String mContent;

	// 业务处理相关变量

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCContSet mLCContSet = new LCContSet();
	/**记录本次保单成功和是失败总计*/
	public int tSucc=0,tFail=0;
	public LCPolF1P_IDGFUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {
			if (!cOperate.equals("PRINT") // 正常打印
					&& !cOperate.equals("REPRINT") // 保单遗失补发
					&& !cOperate.equals("PRINTEX")) { // 前台保单打印
				CError.buildErr(this, "不支持的操作字符串!");
				return false;
			}

			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData)) {
				return false;
			}

			// 进行业务处理
			if (!dealData()) {
				return false;
			}

			// 准备传往后台的数据
			VData vData = new VData();

			if (!prepareOutputData(vData)) {
				return false;
			}

			LCPolF1P_IDGFBL tLCPolF1P_IDGFBL = new LCPolF1P_IDGFBL();
			logger.debug("Start LCPolF1P UI Submit ...");

			if (!tLCPolF1P_IDGFBL.submitData(vData, cOperate)) {
				if (tLCPolF1P_IDGFBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tLCPolF1P_IDGFBL.mErrors);
					return false;
				} else {
					CError.buildErr(this, "LCPolF1P_IDGFBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tLCPolF1P_IDGFBL.getResult();
				mResult.add(tLCPolF1P_IDGFBL.tSucc);
				mResult.add(tLCPolF1P_IDGFBL.tFail);
				tSucc=tLCPolF1P_IDGFBL.tSucc;
				tFail=tLCPolF1P_IDGFBL.tFail;
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "发生异常");
			return false;
		}
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData(VData vData) {
		try {
			vData.clear();
			vData.add(mGlobalInput);
			vData.add(mLCContSet);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "准备数据发生异常");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLCContSet.set((LCContSet) cInputData.getObjectByObjectName(
				"LCContSet", 0));

		if (mGlobalInput == null) {
			CError.buildErr(this, "没有得到足够的信息！");
			return false;
		}

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	// 测试
	public static void main(String[] args) {
		// Vector m_vPolNo = new Vector();
		// m_vPolNo.add("1");
		// m_vPolNo.add("2");
		// m_vPolNo.add("3");
		// m_vPolNo.add("4");
		// logger.debug(m_vPolNo.get(2));
		// m_vPolNo.remove(2);
		// logger.debug(m_vPolNo.get(2));
		// logger.debug(m_vPolNo.size());

		LCPolF1P_IDGFUI tLCPolF1P_IDGFUI = new LCPolF1P_IDGFUI();

		LCPolSet tLCPolSet = new LCPolSet();
		LCPolSchema tLCPolSchema = new LCPolSchema();

		tLCPolSchema.setPolNo("86331020060210002726");
		tLCPolSet.add(tLCPolSchema);

		GlobalInput tGlobalInput = new GlobalInput();

		tGlobalInput.Operator = "DEV";
		tGlobalInput.ManageCom = "86";

		VData vData = new VData();

		vData.addElement(tGlobalInput);
		vData.addElement(tLCPolSet);

		if (!tLCPolF1P_IDGFUI.submitData(vData, "PRINT")) {
			logger.debug(tLCPolF1P_IDGFUI.mErrors.getFirstError());
		} else {
			vData = tLCPolF1P_IDGFUI.getResult();
			try {
				InputStream ins = (InputStream) vData.get(0);
				//
				// DOMBuilder myBuilder = new DOMBuilder();
				// Document myDocument = myBuilder.build(ins);
				//
				// logger.debug(getDocumentToString(myDocument.getRootElement()));
				FileOutputStream fos = new FileOutputStream("D:\\LCPolData.xml");
				int n = 0;

				while ((n = ins.read()) != -1) {
					fos.write(n);
				}

				fos.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 显示JDOM对象，用XML的方式，add by Minim at 2004-01-07
	 * 
	 * @param root
	 *            Document对象的Root节点
	 */
	public static int num = 0;

	public static String getDocumentToString(Element root) {
		num += 2;

		String result = "\r\n";

		for (int j = 0; j < num; j++) {
			result = result + " ";
		}

		result = result + "<" + root.getName() + ">";

		List childList = root.getChildren();
		Iterator it = childList.iterator();

		while (it.hasNext()) {
			Element el = (Element) it.next();

			result = result + getDocumentToString(el);
		}

		if (!root.getTextTrim().equals("")) {
			result = result + root.getTextTrim();
			result = result + "</" + root.getName() + ">";
		} else {
			result = result + "\r\n";

			for (int j = 0; j < num; j++) {
				result = result + " ";
			}

			result = result + "</" + root.getName() + ">";
		}

		num -= 2;

		if (num == 0) {
			result = result + "\r\n";
		}

		return result;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}
}
