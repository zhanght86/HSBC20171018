/*
 * 创建日期 2006-2-20
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * @author Administrator
 * 
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class NBWMissionReassignUI {
private static Logger logger = Logger.getLogger(NBWMissionReassignUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;

	/** 数据操作字符串 */
	public NBWMissionReassignUI() {
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
		// 首先将传入的数据和操作符号在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		NBWMissionReassignBL tNBWMissionReassignBL = new NBWMissionReassignBL();
		if (tNBWMissionReassignBL.submitData(mInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tNBWMissionReassignBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "tNBWMissionReassignUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult = tNBWMissionReassignBL.getResult();
		}

		mInputData = null;
		return true;

	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		CErrors tError = null;
		String FlagStr = "Fail";
		String Content = "";

		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "001";
		tGI.ManageCom = "86";
		tGI.ComCode = "86";
		String transact = "User||UPDATE";
		LWMissionSchema tLWMissionSchema = new LWMissionSchema();
		NBWMissionReassignUI tNBWMissionReassignUI = new NBWMissionReassignUI();
		tLWMissionSchema.setMissionID("00000000000000056644");
		tLWMissionSchema.setSubMissionID("1");
		tLWMissionSchema.setActivityID("0000001001");
		tLWMissionSchema.setDefaultOperator("kangxj");

		VData tVData = new VData();
		tVData.add(tGI);
		tVData.add(tLWMissionSchema);

		if (!tNBWMissionReassignUI.submitData(tVData, transact)) {
			Content = "提交tLWMissionReassignUI失败，原因是: "
					+ tNBWMissionReassignUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		} else {
			Content = "数据提交成功";
			FlagStr = "Succ";
		}
	}
}
