/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.ActivityOperatorNode;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 理赔创建理赔二核工作流起始节点
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author LIJian
 * @version 1.0
 */
public class CreateLLScondUWNodeBL implements BusinessService{
private static Logger logger = Logger.getLogger(CreateLLScondUWNodeBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	// 全局变量
	private MMap map = new MMap();
	private GlobalInput mG = new GlobalInput();
	TransferData mTransferData = new TransferData();

	public CreateLLScondUWNodeBL() {
	}

	public static void main(String[] args) {
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
		logger.debug("----------CreateLLScondUWNodeBL begin submitData----------");
		mInputData = cInputData;
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("----------CreateLLScondUWNodeBL after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------CreateLLScondUWNodeBL after checkInputData----------");
		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("----------CreateLLScondUWNodeBL after dealData----------");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------CreateLLScondUWNodeBL after prepareOutputData----------");
		
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, cOperate)) {
			// @@错误处理
			CError.buildErr(this, "数据提交失败,"+tPubSubmit.mErrors.getLastError());
			return false;
		}
		mInputData = null;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("---CreateLLScondUWNodeBL start getInputData()...");
		// 获取页面报案信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); // 按类取值
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		return true;
	}

	/**
	 * 校验传入的报案信息是否合法
	 * 
	 * @return：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		return true;
	}

	/**
	 * 数据操作类业务处理
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("------创建[发起二核]节点  Begin------");
		boolean tReturn = false;
		VData cInputData = new VData();
		VData tVData = new VData();
		// //取出“个人理赔合同批次表”中记录，用于建立“体检”等节点
		// LLCUWBatchSet tLLCUWBatchSet = new LLCUWBatchSet();
		// tLLCUWBatchSet = (LLCUWBatchSet)
		// mInputData.getObjectByObjectName("LLCUWBatchSet", 0);
		
		// 调用业务逻辑处理类，返回处理完数据
		LLSecondUWBL tLLSecondUWBL = new LLSecondUWBL();
		if (tLLSecondUWBL.submitData(mInputData, mOperate)) {
			VData tempVData1 = new VData();
			tempVData1 = tLLSecondUWBL.getResult();
			cInputData.add(tempVData1);
			mTransferData = null;
			mTransferData = (TransferData) tempVData1.getObjectByObjectName(
					"TransferData", 0);
			String tClaimRelFlag = (String) mTransferData
					.getValueByName("ClaimRelFlag");
			// 首先生成节点，由于第一次创建，没有触发服务类，在下面直接调用业务处理类
			try {
				// 只有0---相关的合同号 或者 //只有1---无关的合同号--------创建一条任务
				if (tClaimRelFlag.equals("0") || tClaimRelFlag.equals("1")) {
					logger.debug("------创建一条任务,相关标志------"
							+ mTransferData.getValueByName("ClaimRelFlag"));
					// 产生[发起二核]节点
					ExeSQL tExeSQL = new ExeSQL();
					SQLwithBindVariables sqlbv = new SQLwithBindVariables();
					sqlbv.sql("select busitype,activityid from lwactivity where functionid='10030018'");
					SSRS tSSRS = tExeSQL.execSQL(sqlbv);
					if(tSSRS == null || tSSRS.MaxRow == 0){
						CError.buildErr(this, "查询工作流节点失败!");
						return false;
					}
					String BusiType = tSSRS.GetText(1, 1);
					String ActivityID = tSSRS.GetText(1, 2);
					SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
					sqlbv1.sql("select processid from LWCorresponding where busitype='"
                            + "?busitype?" +"'");
					sqlbv1.put("busitype", BusiType);
					String ProcessID = tExeSQL.getOneValue(sqlbv1);
					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					sqlbv2.sql("select version from LWCorresponding where busitype='"
                            + "?busitype?" +"'");
					sqlbv2.put("busitype", BusiType);
					String Version = tExeSQL.getOneValue(sqlbv2);
					if(ProcessID == null || ProcessID.equals("")){
						CError.buildErr(this, "创建理赔二核工作流节点信息失败!");
						return false;
					}
					String tMissionID = PubFun1.CreateMaxNo("MissionID", 20);
					String tSubMissionID = "1";
					ActivityOperatorNode tActivityOperatorNode = new ActivityOperatorNode(ActivityID,ProcessID,Version);
					if(tActivityOperatorNode.Create(tMissionID, tSubMissionID, tempVData1)){
						VData tempVData2 = new VData();
						tempVData2 = tActivityOperatorNode.getResult();
						cInputData.add(tempVData2);
						tempVData2 = null;
						tReturn = true;
					}else{
						// @@错误处理
						this.mErrors.copyAllErrors(tActivityOperatorNode.mErrors);
						tReturn = false;
					}
//					ActivityOperator tActivityOperator = new ActivityOperator();
//					if (tActivityOperator.CreateStartMission(ProcessID,
//							ActivityID, tempVData1)) {
//						VData tempVData2 = new VData();
//						tempVData2 = tActivityOperator.getResult();
//						cInputData.add(tempVData2);
//						tempVData2 = null;
//						tReturn = true;
//					} else {
//						// @@错误处理
//						this.mErrors.copyAllErrors(tActivityOperator.mErrors);
//						tReturn = false;
//					}

				}
				// //“0---相关”“1---无关”都存在，-创建两条任务
				// if (tClaimRelFlag.equals("2"))
				// {
				// logger.debug("------创建两条任务,相关标志------");
				// logger.debug("------创建第一条任务-------------");
				// ActivityOperator tActivityOperator = new ActivityOperator();
				// mTransferData.removeByName("ClaimRelFlag");
				// mTransferData.setNameAndValue("ClaimRelFlag", "0");
				// //产生[发起二核]节点
				// logger.debug("------相关标志------" +
				// mTransferData.
				// getValueByName("ClaimRelFlag"));
				// if (tActivityOperator.CreateStartMission("0000000005",
				// "0000005505", tempVData1)) {
				// VData tempVData2 = new VData();
				// tempVData2 = tActivityOperator.getResult();
				// cInputData.add(tempVData2);
				// tempVData2 = null;
				// tReturn = true;
				// } else {
				// // @@错误处理
				// this.mErrors.copyAllErrors(mActivityOperator.
				// mErrors);
				// tReturn = false;
				// }
				// logger.debug("------创建第二条任务------");
				// ActivityOperator ttActivityOperator = new ActivityOperator();
				// mTransferData.removeByName("ClaimRelFlag");
				// mTransferData.setNameAndValue("ClaimRelFlag", "1");
				// logger.debug("------相关标志------" +
				// mTransferData.
				// getValueByName("ClaimRelFlag"));
				// //产生[发起二核]节点
				// if (ttActivityOperator.CreateStartMission("0000000005",
				// "0000005505", tempVData1)) {
				// VData tempVData3 = new VData();
				// tempVData3 = ttActivityOperator.getResult();
				// cInputData.add(tempVData3);
				// tempVData3 = null;
				// tReturn = true;
				// } else {
				// // @@错误处理
				// this.mErrors.copyAllErrors(mActivityOperator.
				// mErrors);
				// tReturn = false;
				// }
				//
				// }

			} catch (Exception ex) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "CreateLLScondUWNodeBL";
				tError.functionName = "dealData";
				tError.errorMessage = "创建理赔二核工作流出现异常!";
				this.mErrors.addOneError(tError);
				tReturn = false;
			}

			tempVData1 = null;
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLSecondUWBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "CreateLLScondUWNodeBL";
			tError.functionName = "dealData";
			tError.errorMessage = "创建理赔二核相关信息表失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			tReturn = false;
		}
		// //为本批次发起二核的每个合同生成一个体检节点
		// VData TJVData = new VData();
		// TJVData = CreateTJNode(tLLCUWBatchSet);
		// if (TJVData != null)
		// {
		// for (int j = 0; j < TJVData.size(); j++)
		// {
		// VData tTJVData = new VData();
		// tTJVData = (VData) TJVData.get(j);
		// cInputData.add(tTJVData);
		// }
		// }
		// else
		// {
		// CError tError = new CError();
		// tError.moduleName = "CreateLLScondUWNodeBL";
		// tError.functionName = "CreateTJNode";
		// tError.errorMessage = "生成体检节点发生错误!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }

		mInputData.clear();
		mInputData = cInputData;
		logger.debug("------创建[发起二核]节点  end------");
		return tReturn;
	}

	/**
	 * 更改工作流LWMission表中当前节点的相关属性
	 * 
	 * @return boolean
	 */
	

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			// 把所有需要提交的map融合到一个map，统一使用pubsubmit提交
			for (int i = 0; i < mInputData.size(); i++) {
				VData tData = new VData();
				tData = (VData) mInputData.get(i);
				MMap tmap = new MMap();
				tmap = (MMap) tData.getObjectByObjectName("MMap", 0);
				map.add(tmap);
			}
			mInputData.clear();
			mInputData.add(map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
