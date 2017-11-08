package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LJAPayDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LJAGetEndorseSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJAGetEndorseSet;
import com.sinosoft.lis.vschema.LJAPayGrpSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保全签单财务处理</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * @version 1.0
 */
import com.sinosoft.lis.db.LJAGetEndorseDB;

public class EdorSignBL {
private static Logger logger = Logger.getLogger(EdorSignBL.class);
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
	private TransferData mTransferData = null;

	/** 传入的业务数据 */
	private LCPolSchema inLCPolSchema = null;
	private LCPremSet inLCPremSet = null;

	private LCGrpPolSchema inLCGrpPolSchema = null;

	private String inNewPolNo;
	private String inEdorNo;
	private String inEdorType;

	/** 传出的业务数据 */
	private LJAGetEndorseSet outLJAGetEndorseSet = new LJAGetEndorseSet();
	private LJAPayPersonSet outLJAPayPersonSet = new LJAPayPersonSet();
	private LJAPayGrpSet outLJAPayGrpSet = new LJAPayGrpSet();

	public EdorSignBL() {
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

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 需要传到后台处理
		if (mOperate.equals("INSERT")) {
		}
		// 不需要传到后台处理
		else if (mOperate.equals("QUERY")) {
			// 准备往后台的数据
			if (!prepareOutputData()) {
				return false;
			}

			mResult = mInputData;
		}
		// 不需要传到后台处理
		else if (mOperate.equals("QUERY||GRPNS")) {
			// 准备往后台的数据
			if (!prepareGRPNSData()) {
				return false;
			}

			mResult = mInputData;
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

			mTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
			inNewPolNo = (String) mTransferData.getValueByName("NewPolNo");
			inEdorNo = (String) mTransferData.getValueByName("EdorNo");
			inEdorType = (String) mTransferData.getValueByName("EdorType");

			if (mOperate.equals("QUERY")) {
				inLCPolSchema = (LCPolSchema) mInputData.getObjectByObjectName(
						"LCPolSchema", 0);
				inLCPremSet = (LCPremSet) mInputData.getObjectByObjectName(
						"LCPremSet", 0);
			} else if (mOperate.equals("QUERY||GRPNS")) {
				inLCGrpPolSchema = (LCGrpPolSchema) mInputData
						.getObjectByObjectName("LCGrpPolSchema", 0);
			}

		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "EdorSignBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 校验保全收费是否与保费项的数额相等
	 * 
	 * @param tLJAGetEndorseSet
	 * @return
	 */
	private boolean checkFinfee(LJAGetEndorseSet tLJAGetEndorseSet) {
		double totalGetMoney = 0;
		for (int i = 0; i < tLJAGetEndorseSet.size(); i++) {
			totalGetMoney = totalGetMoney
					+ tLJAGetEndorseSet.get(i + 1).getGetMoney();
		}

		LPPremDB tLPPremDB = new LPPremDB();
		tLPPremDB.setEdorNo(inEdorNo);
		tLPPremDB.setEdorType(inEdorType);
		tLPPremDB.setPolNo(inLCPolSchema.getProposalNo());
		LPPremSet tLPPremSet = tLPPremDB.query();
		// add by JL at 2004-9-13
		if (tLPPremSet.size() == 0) {
			return true;
		}

		double totalPrem = 0;
		for (int i = 0; i < tLPPremSet.size(); i++) {
			totalPrem = totalPrem + tLPPremSet.get(i + 1).getPrem();
		}

		return (totalGetMoney == totalPrem);
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		try {
			if (mOperate.equals("INSERT")) {
			}
			// 新保加人(NI),新增附加险(NS)
			else if (mOperate.equals("QUERY")) {
				// 个人保全的处理
				if (inLCPolSchema.getGrpPolNo().equals("00000000000000000000")) {

					// 校验保费是否与ljagetendorse批改补退费表的金额一致
					LJAGetEndorseSet tLJAGetEndorseSet = getLJAGetEndorse();
					if (tLJAGetEndorseSet.size() == 0) {
						CError.buildErr(this, "没有找到批改补退费表数据");
						return false;
					}

					if (!checkFinfee(tLJAGetEndorseSet)) {
						CError.buildErr(this, "批改补退费表金额与保费不一致");
						return false;
					}

					// 校验该保全批单号对应的暂交费（otherno对应批单号）的数据是否与应收总表（otherno对应批单号）的数据一致
					// 核销暂交费表，这两步已经在保全总体流程中进行了控制

					// 删除LJAGetEndorse表中的数据
					outLJAGetEndorseSet.add(tLJAGetEndorseSet); // ?? 为什么要删除？
																// asked by
																// zhangrong

					// 获取实收总表数据
					LJAPayDB tLJAPayDB = new LJAPayDB();
					tLJAPayDB.setPayNo(tLJAGetEndorseSet.get(1).getActuGetNo());
					if (!tLJAPayDB.getInfo()) {
						throw new Exception("没有找到实收总表数据！");
					}

					// 新增实收个人交费表
					setLJAPayPerson(tLJAPayDB);
				}
				// 团体下个人的处理
				else {
					// 校验保费是否与ljagetendorse批改补退费表的金额一致
					LJAGetEndorseSet tLJAGetEndorseSet = getGrpLJAGetEndorse();
					if (tLJAGetEndorseSet.size() == 0) {
						throw new Exception("没有找到批改补退费表数据");
					}
					LJAGetEndorseSchema tLJAGetEndorseSchema = tLJAGetEndorseSet
							.get(1);

					// 删除LJAGetEndorse表中的数据
					outLJAGetEndorseSet.add(tLJAGetEndorseSet);

					// 获取实收总表数据
					LJAPayDB tLJAPayDB = new LJAPayDB();
					tLJAPayDB.setPayNo(tLJAGetEndorseSchema.getActuGetNo());
					if (!tLJAPayDB.getInfo()) {
						throw new Exception("没有找到实收总表数据！");
					}

					// 实交个人表，摘自hzm
					for (int i = 1; i <= inLCPremSet.size(); i++) {
						LCPremSchema tLCPremSchema = inLCPremSet.get(i);

						LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
						tLJAPayPersonSchema.setPolNo(inLCPolSchema.getPolNo());
						tLJAPayPersonSchema.setPayCount(1);
						tLJAPayPersonSchema.setGrpContNo(inLCPolSchema
								.getGrpContNo());
						tLJAPayPersonSchema.setGrpPolNo(inLCPolSchema
								.getGrpPolNo());
						tLJAPayPersonSchema
								.setContNo(inLCPolSchema.getContNo());
						tLJAPayPersonSchema.setAppntNo(inLCPolSchema
								.getAppntNo());
						tLJAPayPersonSchema.setPayNo(tLJAPayDB.getPayNo());
						tLJAPayPersonSchema.setPayAimClass("1");
						tLJAPayPersonSchema.setDutyCode(tLCPremSchema
								.getDutyCode());
						tLJAPayPersonSchema.setPayPlanCode(tLCPremSchema
								.getPayPlanCode());
						tLJAPayPersonSchema.setSumDuePayMoney(tLCPremSchema
								.getPrem());
						tLJAPayPersonSchema.setSumActuPayMoney(tLCPremSchema
								.getPrem());
						tLJAPayPersonSchema.setPayIntv(tLCPremSchema
								.getPayIntv());
						tLJAPayPersonSchema.setPayDate(tLJAPayDB.getPayDate());
						tLJAPayPersonSchema.setPayType("ZC");
						tLJAPayPersonSchema.setEnterAccDate(tLJAPayDB
								.getEnterAccDate());
						tLJAPayPersonSchema
								.setConfDate(PubFun.getCurrentDate());
						tLJAPayPersonSchema.setLastPayToDate("1899-12-31");
						tLJAPayPersonSchema.setCurPayToDate(tLCPremSchema
								.getPaytoDate());
						tLJAPayPersonSchema
								.setSerialNo(tLJAPayDB.getSerialNo());
						tLJAPayPersonSchema.setOperator(mGlobalInput.Operator);
						tLJAPayPersonSchema
								.setMakeDate(PubFun.getCurrentDate());
						tLJAPayPersonSchema
								.setMakeTime(PubFun.getCurrentTime());
						tLJAPayPersonSchema.setModifyDate(PubFun
								.getCurrentDate());
						tLJAPayPersonSchema.setModifyTime(PubFun
								.getCurrentTime());
						tLJAPayPersonSchema.setManageCom(inLCPolSchema
								.getManageCom());
						tLJAPayPersonSchema.setAgentCom(inLCPolSchema
								.getAgentCom());
						tLJAPayPersonSchema.setAgentType(inLCPolSchema
								.getAgentType());
						tLJAPayPersonSchema.setRiskCode(inLCPolSchema
								.getRiskCode());
						tLJAPayPersonSchema.setAgentCode(inLCPolSchema
								.getAgentCode());
						tLJAPayPersonSchema.setAgentGroup(inLCPolSchema
								.getAgentGroup());
				          //营改增 add zhangyingfeng 2016-07-14
				          //价税分离 计算器
				          TaxCalculator.calBySchema(tLJAPayPersonSchema);
				          //end zhangyingfeng 2016-07-14
						outLJAPayPersonSet.add(tLJAPayPersonSchema);
					} // end of for

					if (inEdorType.equals("NI")) {
						// 新增实收集体交费表
						// setLJAPayGrp(tLJAPayDB, inLCPolSchema.getGrpPolNo());
						// //改到团单批单保全确认时再完成此任务 modified by zhangrong
					}
				}

			}
			// 团体新增附加险
			else if (mOperate.equals("QUERY||GRPNS")) {
				// 校验保费是否与ljagetendorse批改补退费表的金额一致
				LJAGetEndorseSet tLJAGetEndorseSet = getGrpLJAGetEndorse();
				if (tLJAGetEndorseSet.size() != 1) {
					throw new Exception("没有找到批改补退费表数据或者数据多于一条！");
				}
				LJAGetEndorseSchema tLJAGetEndorseSchema = tLJAGetEndorseSet
						.get(1);
				if (tLJAGetEndorseSchema.getGetMoney() != inLCGrpPolSchema
						.getPrem()) {
					throw new Exception("批改补退费表金额与保费不一致！");
				}

				// 校验该保全批单号对应的暂交费（otherno对应批单号）的数据是否与应收总表（otherno对应批单号）的数据一致
				// 核销暂交费表，这两步已经在保全总体流程中进行了控制

				// 获取实收总表数据
				LJAPayDB tLJAPayDB = new LJAPayDB();
				tLJAPayDB.setPayNo(tLJAGetEndorseSchema.getActuGetNo());
				if (!tLJAPayDB.getInfo()) {
					throw new Exception("没有找到实收总表数据！");
				}

				// 新增实收集体交费表
				// delete by JL,LJAPayGrp表的数据与LJAPayPerson表的数据对应,
				// 团体新增附加险没有在LJAPayPerson表插入数据，所以也不在LJAPayGrp表插入数据!
				// setLJAPayGrp(tLJAPayDB, inLCGrpPolSchema.getGrpProposalNo());
			}
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "EdorSignBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误! " + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 获取批改补退费表数据
	 * 
	 * @return
	 */
	private LJAGetEndorseSet getLJAGetEndorse() {
		String strSql = "select * from LJAGetEndorse where EndorsementNo='"
				+ "?inEdorNo?" + "'" + " and FeeOperationType='" + "?inEdorType?" + "'"
				+ " and PolNo='" + "?PolNo?" + "'"
				+ " and FeeFinaType='BF'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("inEdorNo", inEdorNo);
		sqlbv.put("inEdorType", inEdorType);
		sqlbv.put("PolNo", inLCPolSchema.getProposalNo());
		LJAGetEndorseDB tLJAGetEndorseDB = new LJAGetEndorseDB();
		return tLJAGetEndorseDB.executeQuery(sqlbv);
	}

	/**
	 * 获取批改补退费表数据
	 * 
	 * @return
	 */
	private LJAGetEndorseSet getGrpLJAGetEndorse() {
		String strSql = "select * from LJAGetEndorse where EndorsementNo='"
				+ "?inEdorNo?" + "'" + " and FeeOperationType='" + "?inEdorType?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("inEdorNo", inEdorNo);
		sqlbv.put("inEdorType", inEdorType);
		// + " and GrpPolNo='" + inLCGrpPolSchema.getGrpPolNo() + "'";
		LJAGetEndorseDB tLJAGetEndorseDB = new LJAGetEndorseDB();
		return tLJAGetEndorseDB.executeQuery(sqlbv);
	}

	/**
	 * 新增实收个人交费表
	 * 
	 * @param tLJAPayDB
	 */
	private void setLJAPayPerson(LJAPayDB tLJAPayDB) {
		for (int i = 1; i <= inLCPremSet.size(); i++) {
			LCPremSchema tLCPremSchema = inLCPremSet.get(i);

			LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
			// inNewPolNo的复用上有问题，NS时传入的是null值
			if (inEdorType.equals("NS")) {
				tLJAPayPersonSchema.setPolNo(tLCPremSchema.getPolNo());
			} else {
				tLJAPayPersonSchema.setPolNo(inNewPolNo);
			}

			tLJAPayPersonSchema.setPayCount(1);
			tLJAPayPersonSchema.setGrpPolNo(inLCPolSchema.getGrpPolNo());
			tLJAPayPersonSchema.setContNo(inLCPolSchema.getContNo());
			tLJAPayPersonSchema.setAppntNo(inLCPolSchema.getAppntNo());
			tLJAPayPersonSchema.setPayNo(tLJAPayDB.getPayNo());
			tLJAPayPersonSchema.setPayAimClass("1");
			tLJAPayPersonSchema.setDutyCode(tLCPremSchema.getDutyCode());
			tLJAPayPersonSchema.setPayPlanCode(tLCPremSchema.getPayPlanCode());
			tLJAPayPersonSchema.setSumDuePayMoney(tLCPremSchema.getPrem());
			tLJAPayPersonSchema.setSumActuPayMoney(tLCPremSchema.getPrem());
			tLJAPayPersonSchema.setPayIntv(tLCPremSchema.getPayIntv());
			tLJAPayPersonSchema.setPayDate(tLJAPayDB.getPayDate());
			tLJAPayPersonSchema.setPayType("ZC");
			tLJAPayPersonSchema.setEnterAccDate(tLJAPayDB.getEnterAccDate());
			tLJAPayPersonSchema.setConfDate(PubFun.getCurrentDate());
			tLJAPayPersonSchema.setLastPayToDate("1899-12-31");
			tLJAPayPersonSchema.setCurPayToDate(tLCPremSchema.getPaytoDate());
			tLJAPayPersonSchema.setSerialNo(tLJAPayDB.getSerialNo());
			tLJAPayPersonSchema.setOperator(mGlobalInput.Operator);
			tLJAPayPersonSchema.setMakeDate(PubFun.getCurrentDate());
			tLJAPayPersonSchema.setMakeTime(PubFun.getCurrentTime());
			tLJAPayPersonSchema.setModifyDate(PubFun.getCurrentDate());
			tLJAPayPersonSchema.setModifyTime(PubFun.getCurrentTime());
			tLJAPayPersonSchema.setManageCom(inLCPolSchema.getManageCom());
			tLJAPayPersonSchema.setAgentCom(inLCPolSchema.getAgentCom());
			tLJAPayPersonSchema.setAgentType(inLCPolSchema.getAgentType());
			tLJAPayPersonSchema.setRiskCode(inLCPolSchema.getRiskCode());
			tLJAPayPersonSchema.setAgentCode(inLCPolSchema.getAgentCode());
			tLJAPayPersonSchema.setAgentGroup(inLCPolSchema.getAgentGroup());
	          //营改增 add zhangyingfeng 2016-07-14
	          //价税分离 计算器
	          TaxCalculator.calBySchema(tLJAPayPersonSchema);
	          //end zhangyingfeng 2016-07-14
			outLJAPayPersonSet.add(tLJAPayPersonSchema);
		}
	}

	/**
	 * 新增实收集体交费表
	 * 
	 * @param tLJAPayDB
	 * @throws Exception
	 */
	// private void setLJAPayGrp(LJAPayDB tLJAPayDB, String grpContNo) throws
	// Exception
	// {
	// LCGrpContDB tLCGrpContDB = new LCGrpContDB();
	// tLCGrpContDB.setGrpContNo(grpContNo);
	// if (!tLCGrpContDB.getInfo())
	// {
	// throw new Exception("没有找到集体保单表数据！");
	// }
	//
	// LJAPayGrpSchema tLJAPayGrpSchema = new LJAPayGrpSchema();
	// if (inEdorType.equals("NI"))
	// {
	// tLJAPayGrpSchema.setGrpContNo(grpContNo);
	// }
	// else if (inEdorType.equals("NS"))
	// {
	// //因为团体保单表中的保单号仍然是投保单号，所以要使用hzm的签单模块GrpSignBL传过来的新保单号
	// tLJAPayGrpSchema.setGrpContNo(inNewPolNo);
	// }
	// //计算总保费
	// double dBF = 0.0;
	// for (int i = 1; i <= inLCPremSet.size(); i++)
	// {
	// dBF += inLCPremSet.get(i).getPrem();
	// }
	// logger.debug("总保费为：" + dBF);
	//
	// tLJAPayGrpSchema.setPayCount(1);
	// tLJAPayGrpSchema.setGrpContNo(tLCGrpContDB.getGrpContNo());
	// tLJAPayGrpSchema.setAppntNo(tLCGrpContDB.getAppntNo());
	// tLJAPayGrpSchema.setPayNo(tLJAPayDB.getPayNo());
	// tLJAPayGrpSchema.setSumDuePayMoney(dBF);
	// tLJAPayGrpSchema.setSumActuPayMoney(dBF);
	// tLJAPayGrpSchema.setPayIntv(tLCGrpContDB.getPayIntv());
	// tLJAPayGrpSchema.setPayDate(tLJAPayDB.getPayDate());
	// tLJAPayGrpSchema.setPayType("ZC");
	// tLJAPayGrpSchema.setEnterAccDate(tLJAPayDB.getEnterAccDate());
	// tLJAPayGrpSchema.setConfDate(PubFun.getCurrentDate());
	// tLJAPayGrpSchema.setLastPayToDate("1899-12-31");
	// tLJAPayGrpSchema.setCurPayToDate(tLCGrpContDB.getPaytoDate());
	// tLJAPayGrpSchema.setSerialNo(inSerialNo);
	// tLJAPayGrpSchema.setOperator(mGlobalInput.Operator);
	// tLJAPayGrpSchema.setMakeDate(PubFun.getCurrentDate());
	// tLJAPayGrpSchema.setMakeTime(PubFun.getCurrentTime());
	// tLJAPayGrpSchema.setModifyDate(PubFun.getCurrentDate());
	// tLJAPayGrpSchema.setModifyTime(PubFun.getCurrentTime());
	// tLJAPayGrpSchema.setManageCom(tLCGrpContDB.getManageCom());
	// tLJAPayGrpSchema.setAgentCom(tLCGrpContDB.getAgentCom());
	// tLJAPayGrpSchema.setAgentType(tLCGrpContDB.getAgentType());
	// tLJAPayGrpSchema.setRiskCode(tLCGrpContDB.getRiskCode());
	// tLJAPayGrpSchema.setAgentCode(tLCGrpContDB.getAgentCode());
	// tLJAPayGrpSchema.setAgentGroup(tLCGrpContDB.getAgentGroup());
	//
	// outLJAPayGrpSet.add(tLJAPayGrpSchema);
	// }
	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return 如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();

			mInputData.add(outLJAGetEndorseSet);
			mInputData.add(outLJAPayPersonSet);
			// mInputData.add(outLJAPayGrpSet);
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "EdorSignBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错! ";
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
	private boolean prepareGRPNSData() {
		try {
			mInputData.clear();

			mInputData.add(outLJAGetEndorseSet);
			mInputData.add(outLJAPayGrpSet);
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "EdorSignBL";
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
		EdorSignBL EdorSignBL1 = new EdorSignBL();
	}
}
