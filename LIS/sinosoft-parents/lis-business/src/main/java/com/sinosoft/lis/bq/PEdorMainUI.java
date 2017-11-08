package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:承保暂交费功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 */
public class PEdorMainUI {
private static Logger logger = Logger.getLogger(PEdorMainUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	public PEdorMainUI() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		PEdorMainBL tPEdorMainBL = new PEdorMainBL();

		if (tPEdorMainBL.submitData(cInputData, mOperate) == false) {
			CError.buildErr(this, "数据查询失败", tPEdorMainBL.mErrors);
			mResult.clear();

			return false;
		} else {
			mResult = tPEdorMainBL.getResult();
		}

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

	public static void main(String[] args) {
		LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();

		tLPEdorAppSchema.setOtherNo("230110000000178"); // 申请号码
		tLPEdorAppSchema.setOtherNoType("3"); // 申请号码类型
		tLPEdorAppSchema.setEdorAppName("1"); // 申请人名称
		tLPEdorAppSchema.setAppType("1"); // 申请方式
		// tLPEdorAppSchema.setManageCom(""); //管理机构
		tLPEdorAppSchema.setEdorAppDate("2005-01-24"); // 批改申请日期

		LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
		LPEdorMainSet tLPEdorMainSet = new LPEdorMainSet();

		tLPEdorMainSchema.setContNo("230110000000178"); // 合同号码
		tLPEdorMainSchema.setEdorAppNo(""); // 批改申请号
		tLPEdorMainSchema.setEdorAppDate("2005-01-24"); // 批改申请日期
		tLPEdorMainSchema.setEdorValiDate("2005-01-24"); // 批改生效日期

		tLPEdorMainSet.add(tLPEdorMainSchema);

		VData tVData = new VData();

		// 保存个人保单信息(保全)
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ComCode = "86";
		tGlobalInput.ManageCom = "86";

		tVData.addElement(tLPEdorAppSchema);
		tVData.addElement(tLPEdorMainSet);
		tVData.addElement(tGlobalInput);

		tVData.addElement(tGlobalInput);

		PEdorMainUI tPEdorMainUI = new PEdorMainUI();
		tPEdorMainUI.submitData(tVData, "INSERT||EDOR");

		CErrors tError = tPEdorMainUI.mErrors;
		String Content = "";
		String FlagStr = "";

		if (tError.getErrType().equals(CError.TYPE_NONEERR)) {
			Content = " 保存成功";
			FlagStr = "Success";
		} else {
			String ErrorContent = tError.getErrContent();

			if (tError.getErrType().equals(CError.TYPE_ALLOW)) {
				Content = " 保存成功，但是：" + ErrorContent;
				FlagStr = "Success";
			} else {
				Content = "保存失败，原因是:" + ErrorContent;
				FlagStr = "Fail";
			}
		}

		logger.debug("Content: " + Content);
	}
}
