package com.sinosoft.lis.xbcheck;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 核保记事本</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * @version 1.0
 */
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class RnewNotePadBL {
private static Logger logger = Logger.getLogger(RnewNotePadBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 传出数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 额外传递的参数 */

	// private TransferData mTransferData = null;
	/** 传入的业务数据 */
	private RnewNotePadSet inRnewNotePadSet = new RnewNotePadSet();

	/** 传出的业务数据 */
	private RnewNotePadSet outRnewNotePadSet = new RnewNotePadSet();

	public RnewNotePadBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT"
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

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("---End dealData---");

		// 需要传到后台处理
		if (mOperate.equals("INSERT")) {
			// 准备往后台的数据
			if (!prepareOutputData()) {
				return false;
			}

			logger.debug("---End prepareOutputData---");

			logger.debug("Start RnewNotePad BLS Submit...");

			RnewNotePadBLS tUWNotePadBLS = new RnewNotePadBLS();

			if (tUWNotePadBLS.submitData(mInputData, cOperate) == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tUWNotePadBLS.mErrors);
				mResult.clear();
				mResult.add(mErrors.getFirstError());

				return false;
			} else {
				mResult = tUWNotePadBLS.getResult();
			}

			logger.debug("End RnewNotePad BLS Submit...");
		}

		// 不需要传到后台处理
		else if (mOperate.equals("")) {
		}

		return true;
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
			inRnewNotePadSet = (RnewNotePadSet) mInputData.getObjectByObjectName(
					"RnewNotePadSet", 0);
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();

			CError tError = new CError();
			tError.moduleName = "UWNotePadBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);

			return false;
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
			if (mOperate.equals("INSERT")) {
				for (int i = 0; i < inRnewNotePadSet.size(); i++) {
					RnewNotePadSchema tRnewNotePadSchema = inRnewNotePadSet
							.get(i + 1);

					LCContDB tLCContDB = new LCContDB();
					tLCContDB.setContNo(tRnewNotePadSchema.getContNo());

					LCContSet tLCContSet = tLCContDB.query();

					if (tLCContSet.size() == 0) {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "UWReportBL";
						tError.functionName = "getInputData";
						tError.errorMessage = "无法查到合同号为:"
								+ tRnewNotePadSchema.getContNo() + " 的合同记录！";
						this.mErrors.addOneError(tError);

						return false;
					}
					logger.debug("记事本长度为:"
							+ tRnewNotePadSchema.getNotePadCont().length());
					if (tRnewNotePadSchema.getNotePadCont().length() > 800) {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "UWNotePadBL";
						tError.functionName = "DealData";
						tError.errorMessage = "输入记事本内容过长!";
						this.mErrors.addOneError(tError);
						return false;
					}

					tRnewNotePadSchema.setGrpContNo(tLCContSet.get(1)
							.getGrpContNo());
					tRnewNotePadSchema.setProposalContNo(tLCContSet.get(1)
							.getProposalContNo());

					String tSerialNo = PubFun1.CreateMaxNo("RnewNotePad", 20);
					tRnewNotePadSchema.setSerialNo(tSerialNo);
					tRnewNotePadSchema.setOperator(mGlobalInput.Operator);
					tRnewNotePadSchema.setMakeDate(PubFun.getCurrentDate());
					tRnewNotePadSchema.setMakeTime(PubFun.getCurrentTime());
					tRnewNotePadSchema.setModifyDate(PubFun.getCurrentDate());
					tRnewNotePadSchema.setModifyTime(PubFun.getCurrentTime());
				}

				outRnewNotePadSet = inRnewNotePadSet;
			} else if (mOperate.equals("QUERY")) {
				for (int i = 0; i < inRnewNotePadSet.size(); i++) {
					RnewNotePadSchema tRnewNotePadSchema = inRnewNotePadSet
							.get(i + 1);

					outRnewNotePadSet.add(tRnewNotePadSchema.getDB().query());
				}

				mResult.add(outRnewNotePadSet);
			}
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();

			CError tError = new CError();
			tError.moduleName = "UWNotePadBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误! " + e.getMessage();
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return 如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();

			mInputData.add(outRnewNotePadSet);
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();

			CError tError = new CError();
			tError.moduleName = "UWNotePadBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错! ";
			this.mErrors.addOneError(tError);

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

	/**
	 * 主函数，测试用
	 */
	public static void main(String[] args) {
		RnewNotePadBL UWNotePadBL1 = new RnewNotePadBL();
	}
}
