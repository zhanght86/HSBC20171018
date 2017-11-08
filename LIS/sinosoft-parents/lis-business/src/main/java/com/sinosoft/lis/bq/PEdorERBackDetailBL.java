package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 满期降低保额续保回退BL
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Nicholas
 * @version 1.0
 */

public class PEdorERBackDetailBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorERBackDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	// private double mAmntByMult = 0.0; //存储根据份数算保额的结果。

	/** 用户输入新的保额 */
	// private double mNewAmnt = 0.0;
	/** 记录变动的保额，存入保全项目表 */
	// private double mChangeAmnt = 0.0;

	/** 计算要素 */
	// private BqCalBase mBqCalBase = new BqCalBase();
	/** 返回到界面结果，操作后要显示的信息，如果没有就不传 */
	// private TransferData mTransferData;
	// private Reflections mReflections = new Reflections();
	public PEdorERBackDetailBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括""和""
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 得到外部传入的数据,将数据备份到本类中
		if (!checkData()) {
			return false;
		}
		logger.debug("---End checkData---");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---End dealData---");

		// 准备往后台的数据
		if (!prepareData()) {
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
		return mResult;
	}

	public CErrors getErrors() {
		return this.mErrors;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			// 需要回退的保全项目
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
		} catch (Exception e) {
			e.printStackTrace();
			return this.makeError("getInputData", "接收前台数据失败！");
		}
		if (mGlobalInput == null || mLPEdorItemSchema == null) {
			return this.makeError("getInputData", "传入数据有误！");
		}

		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(mLPEdorItemSchema);
		if (!tLPEdorItemDB.getInfo()) {
			// @@错误处理
			return this.makeError("checkData", "无保全数据！");
		}

		mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());
		if (!"0".equals(tLPEdorItemDB.getEdorState())) {
			// @@错误处理
			return this.makeError("checkData", "此项目尚未确认生效！");
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		try {
			logger.debug("---满期降额续保保全回退处理---");
		} catch (Exception e) {
			// @@错误处理
			return this.makeError("dealData", "数据处理错误！" + e.getMessage());
		}
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareData() {
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	/**
	 * 根据份数计算保额。结果存在mAmntByMult中。
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	// private boolean getAmntByMult(String tPolNo,double tMult)
	// {
	// try
	// {
	// //获得记入保额的险种下的所有GetDutyCode的CalCode
	// mAmntByMult = 0.0;
	// String tSql = "SELECT distinct OthCalCode"
	// + " FROM LMDutyGet a"
	// + " WHERE upper(AddAmntFlag)='Y'"
	// + " and exists(select 'X' from LCGet where GetDutyCode=a.GetDutyCode and
	// PolNo='" + tPolNo + "')";
	// SSRS tSSRS = new SSRS();
	// ExeSQL tExeSQL = new ExeSQL();
	// tSSRS = tExeSQL.execSQL(tSql);
	// if (tSSRS == null || tSSRS.MaxRow <= 0)
	// {
	// return this.makeError("getAmntByMult","根据份数计算保额时查询算法编码错误！");
	// }
	// for (int i=1;i<=tSSRS.MaxRow;i++)
	// {
	// //计算-->
	// Calculator tCalculator = new Calculator();
	// tCalculator.setCalCode(tSSRS.GetText(i,1));
	// //组织要素
	// tCalculator.addBasicFactor("Mult", String.valueOf(tMult)); //份数
	// //结果
	// String tResult = tCalculator.calculate();
	// if (tResult == null || tResult.equals(""))
	// {
	// mAmntByMult = 0.0;
	// return this.makeError("getAmntByMult","根据份数计算保额失败！");
	// }
	// mAmntByMult += Double.parseDouble(tResult);
	// }
	// }
	// catch (Exception e)
	// {
	// return this.makeError("getAmntByMult","根据份数计算保额时产生错误！");
	// }
	// return true;
	// }
	/**
	 * 创建一个错误
	 * 
	 * @param vFuncName
	 *            发生错误的函数名
	 * @param vErrMsg
	 *            错误信息
	 * @return 布尔值（false--永远返回此值）
	 */
	private boolean makeError(String vFuncName, String vErrMsg) {
		CError tError = new CError();
		tError.moduleName = "PEdorERBackDetailBL";
		tError.functionName = vFuncName;
		tError.errorMessage = vErrMsg;
		this.mErrors.addOneError(tError);
		return false;
	}

	public static void main(String[] args) {
		PEdorERBackDetailBL tPEdorERBackDetailBL = new PEdorERBackDetailBL();
	}
}
