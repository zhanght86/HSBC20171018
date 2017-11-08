package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LJAGetDB;
import com.sinosoft.lis.db.LJAGetDrawDB;
import com.sinosoft.lis.db.LPAGetDrawDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.pubfun.FinInsuAcc;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LJAGetDrawSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LJAGetDrawSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LPAGetDrawSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:保全
 * </p>
 * <p>
 * Description:转养老金ConfirmBL层
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Nicholas
 * @version 1.0
 */

public class PEdorTAConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorTAConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();
	/** 用户登陆信息 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 传输数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	public PEdorTAConfirmBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作符
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}

		logger.debug("after getInputData...");

		// 数据校验
		if (!checkData()) {
			return false;
		}

		logger.debug("after checkData...");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("after dealData...");

		// 准备提交后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("after prepareOutputData...");

		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {
		// 更新已经被选择转成养老金的领取项目
		if (!updUserChoose()) {
			return false;
		}

		// C、P表数据互换
		if (!exchangCPData()) {
			return false;
		}

		return true;
	}

	/**
	 * 更新已经被选择转成养老金的领取项目－实付总表
	 * 
	 * @return: boolean
	 */
	private boolean updUserChoose() {
		// =====DEL===zhangtao===2006-02-08===原处理方式，将转过养老金的领取实付记录删除===BGN==========
		// try
		// {
		// //获得此时的日期和时间
		// String strCurrentDate = PubFun.getCurrentDate();
		// String strCurrentTime = PubFun.getCurrentTime();
		//
		// LPAGetDrawSet tLPAGetDrawSet = new LPAGetDrawSet();
		//
		// LPAGetDrawDB tLPAGetDrawDB = new LPAGetDrawDB();
		// tLPAGetDrawDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		// tLPAGetDrawDB.setEdorType(mLPEdorItemSchema.getEdorType());
		// tLPAGetDrawSet.set(tLPAGetDrawDB.query());
		// if (tLPAGetDrawSet == null || tLPAGetDrawSet.size() <= 0)
		// {
		// logger.debug("---Error:未找到用户选择要转成养老金的领取项目！---");
		// return false;
		// }
		// /*判断是否总表数据对应的所有明晰数据都被选择了，
		// *如果是，则将总表数据删除；否则更新总表数据。*/
		// //存储 实付号，用户选择总金额
		// String[][] tAGetNoMoney = new String[tLPAGetDrawSet.size()][2];
		// //数组行指针
		// int tPointer = -1;
		// //新增控制开关
		// boolean tHaveKey;
		// //删除语句
		// String tSql = "";
		// for (int i = 1; i <= tLPAGetDrawSet.size(); i++)
		// {
		// tHaveKey = false;
		// for (int j = 0; j <= tPointer; j++)
		// {
		// if
		// (StrTool.cTrim(tLPAGetDrawSet.get(i).getActuGetNo()).equals(StrTool.cTrim(tAGetNoMoney[j][0])))
		// {
		// tAGetNoMoney[j][1] = String.valueOf(Double.parseDouble(tAGetNoMoney[j][1]) +
		// tLPAGetDrawSet.get(i).getGetMoney());
		// tHaveKey = true;
		// break;
		// }
		// }
		//
		// //顺便申请将LJA表数据删除
		// tSql = "DELETE FROM LJAGetDraw"
		// + " WHERE ActuGetNo='" + tLPAGetDrawSet.get(i).getActuGetNo() + "'"
		// + " and PolNo='" + tLPAGetDrawSet.get(i).getPolNo() + "'"
		// + " and DutyCode='" + tLPAGetDrawSet.get(i).getDutyCode() + "'"
		// + " and GetDutyKind='" + tLPAGetDrawSet.get(i).getGetDutyKind() + "'"
		// + " and GetDutyCode='" + tLPAGetDrawSet.get(i).getGetDutyCode() + "'";
		// map.put(tSql, "DELETE");
		//
		// if (tHaveKey)
		// {
		// continue;
		// }
		// //指针后移
		// tPointer++;
		// tAGetNoMoney[tPointer][0] =
		// StrTool.cTrim(tLPAGetDrawSet.get(i).getActuGetNo());
		// tAGetNoMoney[tPointer][1] =
		// String.valueOf(tLPAGetDrawSet.get(i).getGetMoney());
		//
		// }
		// //操作总表
		// SSRS tSSRS = null;
		// ExeSQL tExeSQL = new ExeSQL();
		// for (int i = 0; i <= tPointer; i++)
		// {
		// tSSRS = new SSRS();
		// tSSRS = tExeSQL.execSQL(
		// "SELECT SumGetMoney FROM LJAGet WHERE ActuGetNo='" + tAGetNoMoney[i][0] +
		// "'");
		// if (tSSRS != null && tSSRS.MaxRow > 0)
		// {
		// if (Double.parseDouble(tSSRS.GetText(1, 1)) >
		// Double.parseDouble(tAGetNoMoney[i][1]))
		// {
		// tSql = "UPDATE LJAGet SET SumGetMoney=" +
		// String.valueOf(Double.parseDouble(tSSRS.GetText(1, 1)) -
		// Double.parseDouble(tAGetNoMoney[i][1])) + ","
		// + " Operator='" + this.mGlobalInput.Operator + "',"
		// + " ModifyDate=to_date('" + strCurrentDate + "','YYYY-MM-DD'),"
		// + " ModifyTime='" + strCurrentTime + "'"
		// + " WHERE ActuGetNo='" + tAGetNoMoney[i][0] + "'";
		// map.put(tSql, "UPDATE");
		// }
		// else
		// {
		// tSql = "DELETE FROM LJAGet WHERE ActuGetNo='" + tAGetNoMoney[i][0] + "'";
		// map.put(tSql, "DELETE");
		// }
		// }
		// }
		// }
		// catch (Exception e)
		// {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "PEdorTAConfirmBL";
		// tError.functionName = "updUserChoose";
		// tError.errorMessage = "处理领取数据时出错:" + e.toString();
		// mErrors.addOneError(tError);
		// return false;
		// }
		// =====DEL===zhangtao===2006-02-08===原处理方式，将转过养老金的领取实付记录删除===END==========

		// =====ADD===zhangtao===2006-02-08======新处理方式===================BGN==========
		// ******************************************************
		// 新处理方式将需要转养老金的领取实付记录置为到帐而不是删除
		// 同时生成一笔负的记录进行财务对冲
		// ******************************************************

		// 获得此时的日期和时间
		String strCurrentDate = PubFun.getCurrentDate();
		String strCurrentTime = PubFun.getCurrentTime();

		// 查询P表中保存的需要转养老金的领取记录
		LPAGetDrawDB tLPAGetDrawDB = new LPAGetDrawDB();
		tLPAGetDrawDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAGetDrawDB.setEdorType(mLPEdorItemSchema.getEdorType());
		LPAGetDrawSet tLPAGetDrawSet = tLPAGetDrawDB.query();
		if (tLPAGetDrawDB.mErrors.needDealError()) {
			CError.buildErr(this, "转养老金的领取项目查询失败!");
			return false;
		}
		if (tLPAGetDrawSet == null || tLPAGetDrawSet.size() < 1) {
			CError.buildErr(this, "没有要转养老金的领取项目!");
			return false;
		}

		// 查询C表中需要转养老金的领取记录
		String sql = " select * from LJAGetDraw where actugetno in ( "
				+ " select actugetno from lpagetdraw " + " where edorno = '?edorno?' and edortype = '?edortype?' and contno = '?contno?' )";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("edorno", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("edortype", mLPEdorItemSchema.getEdorType());
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
		LJAGetDrawDB tLJAGetDrawDB = new LJAGetDrawDB();
		LJAGetDrawSet tLJAGetDrawSet = tLJAGetDrawDB.executeQuery(sqlbv);
		if (tLJAGetDrawDB.mErrors.needDealError()) {
			CError.buildErr(this, "转养老金的领取项目查询失败!");
			return false;
		}
		if (tLJAGetDrawSet == null || tLJAGetDrawSet.size() < 1) {
			CError.buildErr(this, "没有要转养老金的领取项目!");
			return false;
		}

		// 查询C表中实付总表记录
		sql = " select * from ljaget where actugetno in ( "
				+ " select actugetno from lpagetdraw " + " where edorno = '?edorno?' and edortype = '?edortype?' and contno = '?contno?' )";
		sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("edorno", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("edortype", mLPEdorItemSchema.getEdorType());
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
		LJAGetDB tLJAGetDB = new LJAGetDB();
		LJAGetSet tLJAGetSet = tLJAGetDB.executeQuery(sqlbv);
		if (tLJAGetDB.mErrors.needDealError()) {
			CError.buildErr(this, "转养老金的领取项目实付记录查询失败!");
			return false;
		}
		if (tLJAGetSet == null || tLJAGetSet.size() < 1) {
			CError.buildErr(this, "没有转养老金的领取项目对应的实付记录!");
			return false;
		}

		// <-- XinYQ added on 2006-02-11 : 新的业务处理方式 : BGN -->
		String sCurrentDate = PubFun.getCurrentDate();
		String sCurrentTime = PubFun.getCurrentTime();
		Reflections tReflections = new Reflections();
		// 当前处理的前提是一条领取记录对应一条实付总表记录
		for (int j = 1; j <= tLPAGetDrawSet.size(); j++) {
			// 生成新的实付号码
			String sNewActuGetNo = PubFun1.CreateMaxNo("GetNo", PubFun
					.getNoLimit(tLPAGetDrawSet.get(j).getManageCom()));
			// 修改 LJAGet 实付总表
			for (int i = 1; i <= tLJAGetSet.size(); i++) {
				if (tLPAGetDrawSet.get(j).getActuGetNo().equals(
						tLJAGetSet.get(i).getActuGetNo())) {
					// 将原有领取记录置为到帐
					tLJAGetSet.get(i).setEnterAccDate(sCurrentDate);
					tLJAGetSet.get(i).setConfDate(sCurrentDate);
					// 常规字段的赋值
					tLJAGetSet.get(i).setModifyDate(sCurrentDate);
					tLJAGetSet.get(i).setModifyTime(sCurrentTime);
					// 放入 Map 准备更新
					map.put(tLJAGetSet.get(i), "UPDATE");
					// 生成一笔负的对冲纪录
					LJAGetSchema tLJAGetSchema = new LJAGetSchema();
					tReflections.transFields(tLJAGetSchema, tLJAGetSet.get(i));
					// 修改新字段的值
					tLJAGetSchema.setActuGetNo(sNewActuGetNo);
					tLJAGetSchema.setSumGetMoney(0 - tLJAGetSet.get(i)
							.getSumGetMoney());
			          //营改增 add zhangyingfeng 2016-07-14
			          //净额 税额 税率 
					tLJAGetSchema.setNetAmount(0 - tLJAGetSet.get(i).getNetAmount());
					tLJAGetSchema.setTaxAmount(0-tLJAGetSet.get(i).getTaxAmount());
					tLJAGetSchema.setTax(tLJAGetSet.get(i).getTax());
			          //end zhangyingfeng 2016-07-14
					// 常规字段的赋值
					tLJAGetSchema.setMakeDate(sCurrentDate);
					tLJAGetSchema.setMakeTime(sCurrentTime);
					tLJAGetSchema.setModifyDate(sCurrentDate);
					tLJAGetSchema.setModifyTime(sCurrentTime);
					// 放入 Map 准备更新
					map.put(tLJAGetSchema, "DELETE&INSERT");
					// 垃圾处理
					tLJAGetSchema = null;
				}
			}
			// 修改 LJAGetDraw 实付分表
			for (int i = 1; i <= tLJAGetDrawSet.size(); i++) {
				if (tLPAGetDrawSet.get(j).getActuGetNo().equals(
						tLJAGetDrawSet.get(i).getActuGetNo())) {
					// 将原有领取记录置为到帐
					tLJAGetDrawSet.get(i).setFeeFinaType("T");
					tLJAGetDrawSet.get(i).setEnterAccDate(sCurrentDate);
					tLJAGetDrawSet.get(i).setConfDate(sCurrentDate);
					tLJAGetDrawSet.get(i).setFeeOperationType("TA");
					// 常规字段的赋值
					tLJAGetDrawSet.get(i).setModifyDate(sCurrentDate);
					tLJAGetDrawSet.get(i).setModifyTime(sCurrentTime);
					// 放入 Map 准备更新
			          //营改增 add zhangyingfeng 2016-07-14
			          //价税分离 计算器
			          TaxCalculator.calBySchemaSet(tLJAGetDrawSet);
			          //end zhangyingfeng 2016-07-14
					map.put(tLJAGetDrawSet.get(i), "UPDATE");
					// 生成一笔负的对冲纪录
					LJAGetDrawSchema tLJAGetDrawSchema = new LJAGetDrawSchema();
					tReflections.transFields(tLJAGetDrawSchema, tLJAGetDrawSet
							.get(i));
					// 修改新字段的值
					tLJAGetDrawSchema.setActuGetNo(sNewActuGetNo);
					tLJAGetDrawSchema.setGetMoney(0 - tLJAGetDrawSet.get(i)
							.getGetMoney());
			          //营改增 add zhangyingfeng 2016-07-14
			          //净额 税额 税率 
					tLJAGetDrawSchema.setNetAmount(0 - tLJAGetDrawSet.get(i).getNetAmount());
					tLJAGetDrawSchema.setTaxAmount(0-tLJAGetDrawSet.get(i).getTaxAmount());
					tLJAGetDrawSchema.setTax(tLJAGetDrawSet.get(i).getTax());
			          //end zhangyingfeng 2016-07-14
					// 常规字段的赋值
					tLJAGetDrawSchema.setMakeDate(sCurrentDate);
					tLJAGetDrawSchema.setMakeTime(sCurrentTime);
					tLJAGetDrawSchema.setModifyDate(sCurrentDate);
					tLJAGetDrawSchema.setModifyTime(sCurrentTime);
					// 放入 Map 准备更新
					map.put(tLJAGetDrawSchema, "DELETE&INSERT");
					// 垃圾处理
					tLJAGetDrawSchema = null;
				}
			}
		}
		// 垃圾处理
		tReflections = null;
		// <-- XinYQ added on 2006-02-11 : 新的业务处理方式 : END -->

		// =====ADD===zhangtao===2006-02-08======新处理方式===================END==========
		return true;
	}

	/**
	 * 生成客户账户
	 * 
	 * @return boolean
	 */
	private boolean crtFinAcc() {
		// 返回的客户账户map
		MMap tMap = new MMap();

		LPAGetDrawSet tLPAGetDrawSet = new LPAGetDrawSet();
		LPAGetDrawDB tLPAGetDrawDB = new LPAGetDrawDB();
		tLPAGetDrawDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAGetDrawDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPAGetDrawSet = tLPAGetDrawDB.query();
		if (tLPAGetDrawSet == null || tLPAGetDrawSet.size() <= 0) {
			logger.debug("---Error:未找到用户选择要转成养老金的领取项目！---");
			return false;
		}

		int rowNum = tLPAGetDrawSet.size();
		String tCustomerNo = tLPAGetDrawSet.get(1).getInsuredNo(); // 被保人客户号
		String tCustomerType = "1"; // 客户类型
		// １－个人，２－团体
		String[] tOperationType = new String[rowNum]; // 业务类型 "TA"
		String tOtherNo = mLPEdorItemSchema.getEdorAcceptNo();
		String tOtherNoType = "10"; // 其他号码类型
		// 10-保全受理号
		// ２－生存领取
		String[] tMoneyType = new String[rowNum]; // 财务类型
		double[] tMoney = new double[rowNum]; // 本次记入实付金额
		for (int i = 1; i <= rowNum; i++) {
			tOperationType[i - 1] = tLPAGetDrawSet.get(i).getFeeOperationType();
			tMoneyType[i - 1] = tLPAGetDrawSet.get(i).getFeeFinaType();
			tMoney[i - 1] = -Math.abs(tLPAGetDrawSet.get(i).getGetMoney());
		}
		FinInsuAcc tFinInsuAcc = new FinInsuAcc();

		tMap = tFinInsuAcc.createInsuAcc(mGlobalInput, tCustomerNo,
				tCustomerType, tOperationType, tOtherNo, tOtherNoType,
				tMoneyType, tMoney);
		map.add(tMap);
		return true;
	}

	/**
	 * C、P表数据互换
	 * 
	 * @return: boolean
	 */
	private boolean exchangCPData() {
		Reflections tRef = new Reflections();

		LCGetSet aLCGetSet = new LCGetSet();
		LPGetSet aLPGetSet = new LPGetSet();

		// 查询P表数据[给付项信息]
		LPGetDB tLPGetDB = new LPGetDB();
		LPGetSet tLPGetSet = new LPGetSet();
		tLPGetDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPGetDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPGetSet = tLPGetDB.query();
		for (int j = 1; j <= tLPGetSet.size(); j++) {
			// 将P表中数据放到C表中[给付项信息]
			LCGetSchema tLCGetSchema = new LCGetSchema();
			tRef.transFields(tLCGetSchema, tLPGetSet.get(j).getSchema());
			aLCGetSet.add(tLCGetSchema);

			// 查询C表数据[给付项]
			LCGetDB tLCGetDB = new LCGetDB();
			tLCGetDB.setPolNo(tLCGetSchema.getPolNo());
			tLCGetDB.setDutyCode(tLCGetSchema.getDutyCode());
			tLCGetDB.setGetDutyCode(tLCGetSchema.getGetDutyCode());
			if (tLCGetDB.getInfo()) {
				// 将C表中数据放到P表中[给付项]
				LPGetSchema tLPGetSchema = new LPGetSchema();
				tRef.transFields(tLPGetSchema, tLCGetSchema.getSchema());
				tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				aLPGetSet.add(tLPGetSchema);
			}
		}

		map.put(aLCGetSet, "UPDATE");
		map.put(aLPGetSet, "UPDATE");

		return true;
	}

	/**
	 * 数据校验
	 * 
	 * @return: boolean
	 */
	private boolean checkData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
		if (!tLPEdorItemDB.getInfo()) {
			mErrors.addOneError(new CError("查询批改项目信息失败!"));
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());

		return true;
	}

	/**
	 * 得到外部传入的数据
	 * 
	 * @return: boolean
	 */
	private boolean getInputData() {
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mLPEdorItemSchema == null || mGlobalInput == null) {
			mErrors.addOneError(new CError("传入数据不完全!"));
			return false;
		}
		return true;
	}

	/**
	 * 准备提交后台的数据
	 * 
	 * @return: boolean
	 */
	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorTAConfirmBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 返回处理结果
	 * 
	 * @return: VData
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "bq";
		tG.ManageCom = "86";

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorAcceptNo("6120050801000041");
		tLPEdorItemSchema.setEdorNo("6020050810000052");
		tLPEdorItemSchema.setEdorType("TA");
		tLPEdorItemSchema.setContNo("230110000002962");
		tLPEdorItemSchema.setInsuredNo("0000523290");
		tLPEdorItemSchema.setPolNo("210110000002585");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tLPEdorItemSchema);
		// tVData.add(tTransferData);

		PEdorTAConfirmBL tPEdorTAConfirmBL = new PEdorTAConfirmBL();

		if (!tPEdorTAConfirmBL.submitData(tVData, "")) {
			logger.debug(tPEdorTAConfirmBL.mErrors.getError(0).errorMessage);
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(tPEdorTAConfirmBL.mResult, "")) {
			// @@错误处理
			tPEdorTAConfirmBL.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorBLDetailBLF";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			tPEdorTAConfirmBL.mErrors.addOneError(tError);
			// return false;
		} else
			logger.debug("ok!");
	}

}
