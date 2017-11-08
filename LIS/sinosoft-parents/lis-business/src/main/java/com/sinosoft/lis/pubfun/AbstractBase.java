package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class AbstractBase {
private static Logger logger = Logger.getLogger(AbstractBase.class);
	/** 传入数据的容器 */
	protected VData mInputData = new VData();

	/** 传出数据的容器 */
	protected VData mResult = new VData();

	/** 数据操作字符串 */
	protected String mOperate;

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public final boolean submitData(VData cInputData, String cOperate) {
		mInputData = cInputData;
		mOperate = cOperate;
		String clazz = this.getClass().getName();
		if (!getInputData()) {
			return false;
		}
		if (!prepareData()) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		return true;
	}

	public final boolean submitData(VData cInputData) {
		return submitData(cInputData, "");
	}

	protected boolean dealData() {
		return true;
	}

	protected boolean getInputData() {
		return true;
	}

	protected boolean prepareData() {
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
