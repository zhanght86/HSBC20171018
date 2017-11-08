package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 扫描件申请
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 1.0
 */

public class ScanApplyBL {
private static Logger logger = Logger.getLogger(ScanApplyBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 业务处理相关变量 */
	private ES_DOC_MAINSchema inES_DOC_MAINSchema = new ES_DOC_MAINSchema();
	private ES_DOC_MAINSchema outES_DOC_MAINSchema = new ES_DOC_MAINSchema();
	private LWMissionSchema mLWMissionSchema = new LWMissionSchema();

	public ScanApplyBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT||MAIN"和"INSERT||DETAIL"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		logger.debug("cOperate:" + cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData())
			return false;
		logger.debug("---End getInputData---");

		// 进行业务处理
		if (!dealData())
			return false;
		logger.debug("---End dealData---");

		// 申请投保单处理
		if (mOperate.equals("INSERT||MAIN")) {
			// 准备往后台的数据
			if (!prepareOutputData())
				return false;
			logger.debug("---End prepareOutputData---");

			logger.debug("Start ScanApply BLS Submit...");
			ScanApplyBLS tScanApplyBLS = new ScanApplyBLS();
			if (tScanApplyBLS.submitData(mInputData, cOperate) == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tScanApplyBLS.mErrors);
				mResult.clear();
				return false;
			}
			logger.debug("End ScanApply BLS Submit...");

			// 如果有需要处理的错误，则返回
			if (tScanApplyBLS.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tScanApplyBLS.mErrors);
			}
		}
		// 查询
		else if (mOperate.equals("QUERY||MAIN")) {
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
			inES_DOC_MAINSchema = (ES_DOC_MAINSchema) mInputData
					.getObjectByObjectName("ES_DOC_MAINSchema", 0);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ScanApplyBL";
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
		int i;

		try {
			// 申请投保单处理
			logger.debug("------------" + mOperate);
			if (mOperate.equals("INSERT||MAIN")) {
				ES_DOC_MAINSchema tES_DOC_MAINSchema = new ES_DOC_MAINSchema();

				// 根据印刷号（DOC_CODE）获取记录的详细信息
				tES_DOC_MAINSchema.setDocCode(inES_DOC_MAINSchema.getDocCode());
				outES_DOC_MAINSchema.setSchema(tES_DOC_MAINSchema.getDB()
						.query().get(1));

				outES_DOC_MAINSchema.setOperator(inES_DOC_MAINSchema
						.getOperator()); // 操作员编码
				outES_DOC_MAINSchema.setInputStartDate(PubFun.getCurrentDate()); // 当前日期
				outES_DOC_MAINSchema.setInputStartTime(PubFun.getCurrentTime()); // 当前时间
				outES_DOC_MAINSchema.setInputState(inES_DOC_MAINSchema
						.getInputState()); // 状态

				// tongmeng 2008-01-21 add
				// 增加判断操作员
//				String tSQL = "select '1' from lwmission where activityid='0000001094' and missionprop1='"
//						+ inES_DOC_MAINSchema.getDocCode()
//						+ "' "
//						+ " and DefaultOperator is not null and DefaultOperator='"
//						+ inES_DOC_MAINSchema.getOperator() + "' ";
//				String tOperator = "";
//				ExeSQL tExeSQL = new ExeSQL();
//				tOperator = tExeSQL.getOneValue(tSQL);
//				if (tOperator == null || tOperator.equals("")) {
//					CError tError = new CError();
//					tError.moduleName = "TempFeeWithdrawBL";
//					tError.functionName = "dealData";
//					tError.errorMessage = "当前用户无权处理该保单!";
//					this.mErrors.addOneError(tError);
//					return false;
//				}
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				// ln 2009-2-9 add
				// 如果是直接生成的特殊投保单录入工作流节点，初始化操作员
				String tSQL = "select * from lwmission where activityid='0000001094' and missionprop1='"
					+ "?missionprop1?" + "' ";
				sqlbv.sql(tSQL);
				sqlbv.put("missionprop1", inES_DOC_MAINSchema.getDocCode());
				LWMissionDB tLWMissionDB = new LWMissionDB();
				LWMissionSet tLWMissionSet = new LWMissionSet();
				tLWMissionSet = tLWMissionDB.executeQuery(sqlbv);
				if (tLWMissionSet != null && tLWMissionSet.size()>0)
				{
					String tOperator = tLWMissionSet.get(1).getDefaultOperator();
					if(tOperator==null || tOperator.equals(""))
					{
						logger.debug("***********初始化DefaultOperator**********");
						tOperator = inES_DOC_MAINSchema.getOperator();
						
						mLWMissionSchema = tLWMissionSet.get(1);
						mLWMissionSchema.setDefaultOperator(tOperator);	
					}
					else if(!tOperator.equals(inES_DOC_MAINSchema.getOperator())) 
					{
						CError.buildErr(this, "当前用户无权处理该保单!") ;
						return false;
					}
				}
				else
				{					
					CError.buildErr(this, "特殊投保单工作流查询失败!") ;
					return false;
				}

			}
			// 完成录入，查询附加险处理
			else if (mOperate.equals("QUERY||MAIN")) {
				LCContSchema tLCContSchema = new LCContSchema();
				LCContSet tLCContSet = new LCContSet();

				// 根据印刷号（DOC_CODE）获取记录的详细信息
				tLCContSchema.setPrtNo(inES_DOC_MAINSchema.getDocCode());
				tLCContSet.set(tLCContSchema.getDB().query());

				// LMRiskSet tLMRiskSet = new LMRiskSet();
				// for (i=0; i<tLCContSet.size(); i++) {
				// tLCContSchema.setSchema(tLCContSet.get(i + 1));
				//
				// //LMRiskSchema tLMRiskSchema = new LMRiskSchema();
				// //tLMRiskSchema.setRiskCode(tLCContSchema.getRiskCode());
				// //tLMRiskSet.add(tLMRiskSchema.getDB().query().get(1));
				// }
				mResult.add(tLCContSet.encode());
			}
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "TempFeeWithdrawBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误!";
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
		mInputData = new VData();
		try {
			mInputData.add(outES_DOC_MAINSchema);
			if(mLWMissionSchema!=null)
				mInputData.add(mLWMissionSchema);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "TempFeeWithdrawBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
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

	public static void main(String[] args) {
		ScanApplyBL scanApplyBL1 = new ScanApplyBL();
	}
}
