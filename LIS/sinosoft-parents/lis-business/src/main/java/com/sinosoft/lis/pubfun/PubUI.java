package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 公用UI
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: ChinaSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */
public class PubUI {
private static Logger logger = Logger.getLogger(PubUI.class);
	public PubUI() {
	}

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/**
	 * 传输数据的公共方法
	 * <p>
	 * <b>Example: </b>
	 * <p>
	 * <p>
	 * CodeQueryUI tCodeQueryUI=new CodeQueryUI();
	 * <p>
	 * <p>
	 * VData tData=new VData();
	 * <p>
	 * <p>
	 * LDCodeSchema tLDCodeSchema =new LDCodeSchema();
	 * <p>
	 * <p>
	 * tLDCodeSchema.setCodeType("sex");
	 * <p>
	 * <p>
	 * tData.add(tLDCodeSchema);
	 * <p>
	 * <p>
	 * tCodeQueryUI.submitData(tData,"QUERY||MAIN");
	 * <p>
	 * 
	 * @param cInputData
	 *            作为数据容器的VData对象
	 * @param cOperate
	 *            操作符标志
	 * @param cBLForBL
	 *            后来逻辑处理类名称(需要传入包路径,如:com.sinosoft.lis.finfee.TempFeeBL)
	 * @return 如果后台数据处理操作成功，将结果存在内部VData对象中，返回true；处理失败则返回false
	 */
	public boolean submitData(VData cInputData, String cOperate, String cBLForBL)
			throws ClassNotFoundException, IllegalAccessException,
			InstantiationException {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		Class tClass = Class.forName(cBLForBL);
		PubBLInterface tPubBL = (PubBLInterface) tClass.newInstance();

		if (tPubBL.submitData(cInputData, mOperate)) {
			mInputData = tPubBL.getResult();
		} else {
			this.mErrors = tPubBL.getErrors();
			mInputData.clear();
			return false;
		}

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mInputData;
	}

	public static void main(String arg[]) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "hyq";
		tG.ManageCom = "86";
		tG.ComCode = "86";

		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		LJSPaySchema tLJSPaySchema = new LJSPaySchema();
		tLJSPaySchema.setGetNoticeNo("310110000001087");
		tLJSPaySchema.setOtherNo("230110000003435");

		tLOPRTManagerSchema.setPrtSeq("23");

		VData tVData = new VData();
		VData mResult = new VData();

		tVData.addElement(tG);
		tVData.addElement(tLOPRTManagerSchema);
		tVData.addElement(tLJSPaySchema);
		PubUI tPubUI = new PubUI();
		try {
			if (!tPubUI.submitData(tVData, "Print",
					"com.sinosoft.lis.operfee.ExtendInvoiceBL")) {
				logger.debug("---error----");
			}
		} catch (Exception ex) {

		}
	}

}
