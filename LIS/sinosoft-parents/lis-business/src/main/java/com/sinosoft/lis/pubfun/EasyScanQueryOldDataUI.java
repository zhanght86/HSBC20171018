/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 扫描件处理类
 * </p>
 * <p>
 * Description: UI功能类
 * </p>

 * 涉及到的表为es_doc_main_old/es_doc_pages_old/es_doc_relation_old
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author: Niuzj
 * @version 1.0
 * @date 2006-09-26
 */

public class EasyScanQueryOldDataUI {
private static Logger logger = Logger.getLogger(EasyScanQueryOldDataUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public EasyScanQueryOldDataUI() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"QUERY||MAIN"和"QUERY||DETAIL"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		logger.debug("---EasyScanQueryOldDataUI BL BEGIN---");
		try {
			EasyScanQueryOldDataBL tEasyScanQueryOldDataBL = new EasyScanQueryOldDataBL();
			if (tEasyScanQueryOldDataBL.submitData(cInputData, mOperate)) {
				mResult = tEasyScanQueryOldDataBL.getResult();
			} else {
				// @@错误处理
				this.mErrors.copyAllErrors(tEasyScanQueryOldDataBL.mErrors);
				mResult.clear();
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.debug("---EasyScanQueryOldDataUI BL END---");

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		EasyScanQueryOldDataUI tEasyScanQueryOldDataUI = new EasyScanQueryOldDataUI();
		VData tVData = new VData();
		VData tVData1 = new VData();
		// docid test
		// tVData.add("532");
		// tEasyScanQueryUI.submitData(tVData, "QUERY||0");
		// tVData.add("1980000002");
		// tVData.add("11");
		// tVData.add("TB");
		// tVData.add("TB1005");
		// tEasyScanQueryUI.submitData(tVData, "QUERY||1");

		tVData.add("1");
		tVData.add("http://10.1.8.3:9001/");
		tEasyScanQueryOldDataUI.submitData(tVData, "QUERY||9999");

	}
}
