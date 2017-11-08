package com.sinosoft.lis.bq;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.f1print.PrintTool;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorPrintSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 团体保全受理申请确认
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhangtao
 * @version 1.0
 */
public class GEdorAcceptAppConfirmBL {
private static Logger logger = Logger.getLogger(GEdorAcceptAppConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	@SuppressWarnings("unused")
	private String mOperate;

	/** 全局数据 */
	MMap map = new MMap();
	private GlobalInput mGlobalInput;
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
	@SuppressWarnings("unused")
	private TransferData mTransferData = new TransferData();

	private String mEdorAcceptNo = "";

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public GEdorAcceptAppConfirmBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		if (!checkData()) {
			return false;
		}
		logger.debug("---End checkData---");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---End dealData---");

		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("---End prepareOutputData---");

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return
	 */
	public boolean dealData() {

		// 统计变动保费等
		if (!sumChang()) {
			return false;
		}

		// 统一更新批改状态与核保状态
		if (!updState()) {
			return false;
		}

		// Q:是否和个单保全一样对续期状态解挂

		// 与个险一样生成申请批单数据 
		try {
			if (!printGrpData()) {
				CError.buildErr(this, "生成批单数据失败!");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("生成批单数据异常!");
		}

		// 备份并删除保全受理和无扫描受理节点任务 挪至工作流服务类里实现 

		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		try {
			mLPEdorAppSchema = (LPEdorAppSchema) mInputData
					.getObjectByObjectName("LPEdorAppSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);

		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}

		return true;
	}

	/**
	 * 校验保全受理是否可以申请确认
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		// 校验收付费方式信息
		String sBankCode = mLPEdorAppSchema.getBankCode();
		String sBankAccNo = mLPEdorAppSchema.getBankAccNo();
		String sAccName = mLPEdorAppSchema.getAccName();
		String sPayGetName = mLPEdorAppSchema.getPayGetName();
		String sPersonID = mLPEdorAppSchema.getPersonID();
		String sPayForm = mLPEdorAppSchema.getPayForm();
		String sGetForm = mLPEdorAppSchema.getGetForm();
		if (sPayForm == null || sPayForm.trim().equals("") || sGetForm == null
				|| sGetForm.trim().equals("")) {
			CError.buildErr(this, "对不起，您还没有录入收付费方式!");
			return false;
		}
		if (sPayForm.trim().equals("4") || sPayForm.trim().equals("7")) {
			// 银行划款或网上支付
			if (sBankCode == null || sBankCode.trim().equals("")
					|| sBankAccNo == null || sBankAccNo.trim().equals("")
					|| sAccName == null || sAccName.trim().equals("")) {
				CError.buildErr(this, "银行收付费信息不完整!");
				return false;
			}
		} else {
			sBankCode = "";
			sBankAccNo = "";
			sAccName = "";
		}
		// 查询保全受理信息
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mLPEdorAppSchema.getEdorAcceptNo());
		if (!tLPEdorAppDB.getInfo()) {
			mErrors.copyAllErrors(tLPEdorAppDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "查询保全受理失败";
			mErrors.addOneError(tError);
			return false;
		}

		mLPEdorAppSchema.setSchema(tLPEdorAppDB.getSchema());

		mEdorAcceptNo = mLPEdorAppSchema.getEdorAcceptNo();

		// 申请确认前还可以修改银行账户信息以及领取人信息
		mLPEdorAppSchema.setGetForm(sGetForm);
		mLPEdorAppSchema.setPayForm(sPayForm);
		mLPEdorAppSchema.setBankCode(sBankCode);
		mLPEdorAppSchema.setBankAccNo(sBankAccNo);
		mLPEdorAppSchema.setAccName(sAccName);
		mLPEdorAppSchema.setPayGetName(sPayGetName);
		mLPEdorAppSchema.setPersonID(sPersonID);

		// 校验补退费领取人信息是否录入完整
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setEdorAcceptNo(mEdorAcceptNo);
		LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
		for(int i= 1;i<=tLPGrpEdorItemSet.size();i++){
			LPGrpEdorItemSchema tLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(i);
			String tEdorType = tLPGrpEdorItemSchema.getEdorType();
			if(tEdorType.equals("AZ")|| tEdorType.equals("AT")||tEdorType.equals("AX")){

				String tSql = "SELECT COUNT(ContNo) FROM LJSGetEndorse WHERE OtherNo = '"+"?OtherNo?"+"'";
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("OtherNo", tLPGrpEdorItemSchema.getEdorNo());
				ExeSQL rExeSQL = new ExeSQL();
				String tTBNum = rExeSQL.getOneValue(sqlbv);
				int tArrLen = Integer.parseInt(tTBNum);
				if(tArrLen >0){
					ExeSQL bnfExeSQL = new ExeSQL();
					String nBnfSql = "SELECT COUNT(ContNo) FROM LPBNF WHERE EdorNo = '"+"?EdorNo?"+"'";
					SQLwithBindVariables sbv=new SQLwithBindVariables();
					sbv.sql(nBnfSql);
					sbv.put("EdorNo", tLPGrpEdorItemSchema.getEdorNo());
					String tBnfNum = bnfExeSQL.getOneValue(sbv);
					int dBnfNum = Integer.parseInt(tBnfNum);
					if(dBnfNum == 0)
					{
						CError.buildErr(this, "请导入分帐户给付的信息!");
						return false;
					}
					if(dBnfNum != tArrLen)
					{
						CError.buildErr(this, "已录入的给付的帐户数与实际需要给付的帐户数不等!");
						return false;
					}
				}
			}
		}
		/*if (!checkPayGetPersonInfo(mLPEdorAppchema)) {
			return false;
		}*/

		// 校验该保全受理下是否还有未录入的保全项目
		String sql = " select 1 from dual where exists "
				+ "("
				+ "  select 'X' from lpgrpedoritem where edorstate = '3' and edoracceptno = '"
				+ "?edoracceptno?"
				+ "'"
				+ "  union "
				+ "  select 'X' from lpedoritem where edorstate = '3' and edoracceptno = '"
				+ "?edoracceptno?" + "'" + ")";
		SQLwithBindVariables sqlbvs=new SQLwithBindVariables();
		sqlbvs.sql(sql);
		sqlbvs.put("edoracceptno", mLPEdorAppSchema.getEdorAcceptNo());
		ExeSQL tExeSQL = new ExeSQL();
		String sEdorStateFlag = tExeSQL.getOneValue(sqlbvs);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保全项目批改状态查询失败!");
			return false;
		}
		if (sEdorStateFlag != null && sEdorStateFlag.trim().equals("1")) {
			CError.buildErr(this, "该保全受理下尚有保全项目明细没有录入!");
			return false;
		}

		map.put(mLPEdorAppSchema, "UPDATE");
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(map);
		mResult.clear();
		mResult.add(map);
		return true;
	}

	/**
	 * 统计变动保费、变动保额、补/退费金额、补/退费利息
	 * 
	 * @return boolean
	 */
	private boolean sumChang() {
		String tEdorAcceptNo = mLPEdorAppSchema.getEdorAcceptNo();
		String wherePartApp = " where EdorAcceptNo = '" + "?tEdorAcceptNo?" + "'";
		String wherePartMain = " where m.contno = i.contno and  m.edorno = i.edorno and EdorAcceptNo = '"
				+ "?tEdorAcceptNo?" + "'";
		String wherePartGrpMain = " where m.edorno = i.edorno and m.grpcontno = i.grpcontno and EdorAcceptNo = '"
				+ "?tEdorAcceptNo?" + "'";
		StringBuffer sbSQL = new StringBuffer();

		// 个人批单层
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorMain m set ").append(
				" ChgPrem = (select sum(ChgPrem) from LPEdorItem i ").append(
				wherePartMain).append("), ").append(
				" ChgAmnt = (select sum(ChgAmnt) from LPEdorItem i ").append(
				wherePartMain).append("), ").append(
				" GetMoney = (select sum(GetMoney) from LPEdorItem i ").append(
				wherePartMain).append("), ").append(
				" GetInterest = (select sum(GetInterest) from LPEdorItem i ")
				.append(wherePartMain).append(") ").append(wherePartApp);
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(sbSQL.toString());
		sbv1.put("tEdorAcceptNo", tEdorAcceptNo);
		map.put(sbv1, "UPDATE");

		// Q: 各个团体保全项目自己更新？ 
		// A: 在团体保全项目录入完毕时实现 
		// //团体项目层
		// sbSQL.setLength(0);
		// sbSQL
		// .append(" UPDATE LPGrpEdorItem m set ")
		// .append(" ChgPrem = (select sum(ChgPrem) from LPEdorItem i ")
		// .append(wherePartGrpItem)
		// .append("), ")
		// .append(" ChgAmnt = (select sum(ChgAmnt) from LPEdorItem i ")
		// .append(wherePartGrpItem)
		// .append("), ")
		// .append(" GetMoney = (select sum(GetMoney) from LPEdorItem i ")
		// .append(wherePartGrpItem)
		// .append("), ")
		// .append(" GetInterest = (select sum(GetInterest) from LPEdorItem i ")
		// .append(wherePartGrpItem)
		// .append(") ")
		// .append(wherePartApp);
		// map.put(sbSQL.toString(), "UPDATE");

		// 团体批单层
		sbSQL.setLength(0);
		sbSQL
				.append(" UPDATE LPGrpEdorMain m set ")
				.append(" ChgPrem = (select sum(ChgPrem) from LPGrpEdorItem i ")
				.append(wherePartGrpMain)
				.append("), ")
				.append(" ChgAmnt = (select sum(ChgAmnt) from LPGrpEdorItem i ")
				.append(wherePartGrpMain)
				.append("), ")
				.append(
						" GetMoney = (select sum(GetMoney) from LPGrpEdorItem i ")
				.append(wherePartGrpMain)
				.append("), ")
				.append(
						" GetInterest = (select sum(GetInterest) from LPGrpEdorItem i ")
				.append(wherePartGrpMain).append(") ").append(wherePartApp);
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(sbSQL.toString());
		sbv2.put("tEdorAcceptNo", tEdorAcceptNo);
		map.put(sbv2, "UPDATE");

		// 保全申请层
		sbSQL.setLength(0);
		sbSQL
				.append(" UPDATE LPEdorApp a set ")
				.append(" ChgPrem = (select sum(ChgPrem) from LPGrpEdorItem ")
				.append(wherePartApp)
				.append("), ")
				.append(" ChgAmnt = (select sum(ChgAmnt) from LPGrpEdorItem ")
				.append(wherePartApp)
				.append("), ")
				.append(" GetMoney = (select sum(GetMoney) from LPGrpEdorItem ")
				.append(wherePartApp)
				.append("), ")
				.append(
						" GetInterest = (select sum(GetInterest) from LPGrpEdorItem ")
				.append(wherePartApp).append(") ").append(wherePartApp);
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(sbSQL.toString());
		sbv3.put("tEdorAcceptNo", tEdorAcceptNo);
		map.put(sbv3, "UPDATE");

		
		// 判断本次保全是否付费
		String sql = " select (case when sum(getmoney) is not null then sum(getmoney) else 0 end) from ljsgetendorse where endorsementno in "
				+ " (select edorno from lpgrpedoritem where edoracceptno = '"
				+ "?edoracceptno?" + "') and getflag = '1'";
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("edoracceptno", mLPEdorAppSchema.getEdorAcceptNo());
		String sSumPayMoney = tExeSQL.getOneValue(sqlbv);
		double dSumPayMoney = 0.0;//付费的
		try {
			dSumPayMoney = -Double.parseDouble(sSumPayMoney);
		} catch (Exception e) {
			return false;
		}
		
		sql = " select (case when sum(getmoney) is not null then sum(getmoney) else 0 end) from ljsgetendorse where endorsementno in "
			+ " (select edorno from lpgrpedoritem where edoracceptno = '"
			+ "?edoracceptno?" + "') and getflag = '0'";
		sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("edoracceptno", mLPEdorAppSchema.getEdorAcceptNo());
		tExeSQL = new ExeSQL();
		String sSumGetMoney = tExeSQL.getOneValue(sqlbv);
		double dSumGetMoney = 0.0;//收费的
		try {
			dSumGetMoney = Double.parseDouble(sSumGetMoney);
		} catch (Exception e) {
			return false;
		}
		
		double dGetMoney = dSumPayMoney+dSumGetMoney;
		
		sbSQL.setLength(0);
		sbSQL
				.append(" UPDATE LPEdorApp a set ")
				.append(" ChgPrem = (select sum(ChgPrem) from LPGrpEdorItem ")
				.append(wherePartApp)
				.append("), ")
				.append(" ChgAmnt = (select sum(ChgAmnt) from LPGrpEdorItem ")
				.append(wherePartApp)
				.append("), ")
				.append(" GetMoney = "+"?dGetMoney?"+", ")
				.append(
						" GetInterest = (select sum(GetInterest) from LPGrpEdorItem ")
				.append(wherePartApp).append(") ").append(wherePartApp);
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql(sql);
		sbv4.put("tEdorAcceptNo", tEdorAcceptNo);
		sbv4.put("dGetMoney", dGetMoney);
		map.put(sbv4, "UPDATE");
		
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPGrpEdorItem set ")
			 .append(" GetMoney = "+"?dGetMoney?"+" ")
			 .append(wherePartApp);
		SQLwithBindVariables sbv5=new SQLwithBindVariables();
		sbv5.sql(sql);
		sbv5.put("tEdorAcceptNo", tEdorAcceptNo);
		sbv5.put("dGetMoney", dGetMoney);
		logger.debug(sbSQL.toString());
		map.put(sbv5, "UPDATE");
		return true;
	}

	/**
	 * 统一更新保全受理、保全申请批单、保全项目的 批改状态为 2-申请确认，核保状态为 0-未核保
	 * 
	 * @return boolean
	 */
	private boolean updState() {
		String tEdorAcceptNo = mLPEdorAppSchema.getEdorAcceptNo();
		String wherePart = " where EdorAcceptNo='" + "?tEdorAcceptNo?" + "'";

		StringBuffer sbSQL = new StringBuffer();

		// 个人保全项目
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorItem set EdorState = '2' , UWFlag = '0', ")
				.append(" Operator = '").append("?Operator?").append(
						"', ModifyDate = '").append("?mCurrentDate?").append(
						"', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(sbSQL.toString());
		sbv1.put("tEdorAcceptNo", tEdorAcceptNo);
		sbv1.put("Operator", mGlobalInput.Operator);
		sbv1.put("mCurrentDate", mCurrentDate);
		sbv1.put("mCurrentTime", mCurrentTime);
		map.put(sbv1, "UPDATE");

		// 个人保全批单
		sbSQL.setLength(0);
		sbSQL
				.append(
						" UPDATE LPEdorMain set EdorState = '2' , UWState = '0', ")
				.append(" Operator = '").append("?Operator?").append(
						"', ModifyDate = '").append("?mCurrentDate?").append(
						"', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(sbSQL.toString());
		sbv2.put("tEdorAcceptNo", tEdorAcceptNo);
		sbv2.put("Operator", mGlobalInput.Operator);
		sbv2.put("mCurrentDate", mCurrentDate);
		sbv2.put("mCurrentTime", mCurrentTime);
		map.put(sbv2, "UPDATE");

		// 团体保全项目
		sbSQL.setLength(0);
		sbSQL.append(
				" UPDATE LPGrpEdorItem set EdorState = '2' , UWFlag = '0', ")
				.append(" Operator = '").append("?Operator?").append(
						"', ModifyDate = '").append("?mCurrentDate?").append(
						"', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(sbSQL.toString());
		sbv3.put("tEdorAcceptNo", tEdorAcceptNo);
		sbv3.put("Operator", mGlobalInput.Operator);
		sbv3.put("mCurrentDate", mCurrentDate);
		sbv3.put("mCurrentTime", mCurrentTime);
		map.put(sbv3, "UPDATE");

		// 团体保全批单
		sbSQL.setLength(0);
		sbSQL.append(
				" UPDATE LPGrpEdorMain set EdorState = '2' , UWState = '0', ")
				.append(" Operator = '").append("?Operator?").append(
						"', ModifyDate = '").append("?mCurrentDate?").append(
						"', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql(sbSQL.toString());
		sbv4.put("tEdorAcceptNo", tEdorAcceptNo);
		sbv4.put("Operator", mGlobalInput.Operator);
		sbv4.put("mCurrentDate", mCurrentDate);
		sbv4.put("mCurrentTime", mCurrentTime);
		map.put(sbv4, "UPDATE");

		// 保全申请
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorApp set EdorState = '2' , UWState = '0', ")
				.append(" Operator = '").append("?Operator?").append(
						"', ModifyDate = '").append("?mCurrentDate?").append(
						"', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv5=new SQLwithBindVariables();
		sbv5.sql(sbSQL.toString());
		sbv5.put("tEdorAcceptNo", tEdorAcceptNo);
		sbv5.put("Operator", mGlobalInput.Operator);
		sbv5.put("mCurrentDate", mCurrentDate);
		sbv5.put("mCurrentTime", mCurrentTime);
		map.put(sbv5, "UPDATE");

		return true;
	}

	/**
	 * 生成批单数据
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	private boolean printGrpData() {
		XmlExportNew xmlExport = new XmlExportNew();
		logger.debug("\n\nStart Write Print Data\n\n");
		logger.debug("EdorAcceptNo ================= > " + mEdorAcceptNo);

		// 按不同项目内容进行打印（一个批单号打印）
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		LPGrpEdorItemSet tLPGrpEdorItemSet = new LPGrpEdorItemSet();
		LPGrpEdorItemSchema tLPGrpEdorItemSchema;
		ExeSQL aExeSQL = new ExeSQL();
		SSRS aSSRS = new SSRS();
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql("select distinct edorno from lpgrpedoritem where edoracceptno = '" + "?mEdorAcceptNo?" + "' group by edorno");
		sbv.put("mEdorAcceptNo", mEdorAcceptNo);
		aSSRS = aExeSQL
				.execSQL(sbv);
		if (aSSRS == null || aSSRS.MaxRow == 0) {
			CError.buildErr(this, "查询批改项目表失败!");
			return false;
		}

		String EdorNo = "";
		for (int j = 1; j <= aSSRS.getMaxRow(); j++) {
			EdorNo = aSSRS.GetText(j, 1);
			xmlExport.createDocument("团体保全申请书");
			String customerNo = "";
			if("3".equals(mLPEdorAppSchema.getOtherNoType())){
				LCContDB tLCContDB = new LCContDB();
				tLCContDB.setContNo(mLPEdorAppSchema.getOtherNo());
				tLCContDB.getInfo();
				customerNo = tLCContDB.getAppntNo();
			}else{
				customerNo = mLPEdorAppSchema.getOtherNo();
			}
			String uLanguage = PrintTool.getCustomerLanguage(customerNo);
			if (uLanguage != null && !"".equals(uLanguage)) 
				xmlExport.setUserLanguage(uLanguage);//用户语言
			xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言

			// 一个保单出一张批单
			tLPGrpEdorItemDB.setEdorAcceptNo(mEdorAcceptNo);
			tLPGrpEdorItemDB.setEdorNo(aSSRS.GetText(j, 1));
			tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
			if (tLPGrpEdorItemSet.size() < 1) {
				CError.buildErr(this, "查询保全项目表失败，未找到保全项目!");
				return false;
			}
			boolean mClassflag = true;
			for (int i = 1; i <= tLPGrpEdorItemSet.size(); i++) {
				// 批单打印有各个项目的单独生成打印数据
				tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
				tLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(i);
				ExeSQL tExeSQL = new ExeSQL();
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql("select edorname from lmedoritem where edorcode = '"
						+ "?edorcode?"
						+ "' and appobj = 'G'");
				sqlbv.put("edorcode", tLPGrpEdorItemSchema.getEdorType());
				String EdorName = tExeSQL
						.getOneValue(sqlbv);
				try {
					Class tClass = Class.forName("com.sinosoft.lis.bqgrp.GrpEdor"
							+ tLPGrpEdorItemSchema.getEdorType() + "PrintBL");
					EdorPrint tEdorPrint = (EdorPrint) tClass.newInstance();
					VData aVData = new VData();
					aVData.add(tLPGrpEdorItemSchema);
					aVData.add(xmlExport);
					aVData.add(mGlobalInput);
					if (!tEdorPrint.submitData(aVData, "PRINT"
							+ tLPGrpEdorItemSchema.getEdorType())) {
						mErrors.copyAllErrors(tEdorPrint.mErrors);
						mErrors.addOneError("保全项目" + EdorName + "打印处理失败!");
						return false;
					}
					VData cVData = new VData();
					cVData = tEdorPrint.getResult();
					xmlExport = (XmlExportNew) cVData.getObjectByObjectName(
							"XmlExportNew", 0);
					// 可能存在批单的附属清单
					MMap tMap = (MMap) cVData.getObjectByObjectName("MMap", 0);
					if (tMap != null) {
						map.add(tMap);
					}
				} catch (ClassNotFoundException ex) {
					mClassflag = false;
					logger.debug("未找到" + EdorName + "保全项目打印处理!");
				} catch (Exception ex) {
					mClassflag = false;
					CError.buildErr(this, "保全项目" + EdorName + "打印处理失败!");
					return false;
				}
				logger.debug("成功完成" + EdorName + "保全项目打印处理!");
			}
			if (mClassflag) {
				// 生成主打印批单schema
				LPEdorPrintSchema tLPEdorPrintSchemaMain = new LPEdorPrintSchema();
				tLPEdorPrintSchemaMain.setEdorNo(EdorNo);
				tLPEdorPrintSchemaMain.setManageCom(mLPEdorAppSchema
						.getManageCom());
				tLPEdorPrintSchemaMain.setPrtFlag("N");
				tLPEdorPrintSchemaMain.setPrtTimes(0);
				tLPEdorPrintSchemaMain.setMakeDate(PubFun.getCurrentDate());
				tLPEdorPrintSchemaMain.setMakeTime(PubFun.getCurrentTime());
				tLPEdorPrintSchemaMain.setOperator(mGlobalInput.Operator);
				tLPEdorPrintSchemaMain.setModifyDate(PubFun.getCurrentDate());
				tLPEdorPrintSchemaMain.setModifyTime(PubFun.getCurrentTime());
				InputStream ins = xmlExport.getInputStream();

				tLPEdorPrintSchemaMain.setEdorInfo(ins);
				SQLwithBindVariables sqlbvs=new SQLwithBindVariables();
				sqlbvs.sql("delete from LPEdorPrint where EdorNo='" + "?EdorNo?" + "'");
				sqlbvs.put("EdorNo", EdorNo);
				map
						.put(sqlbvs, "DELETE");
				map.put(tLPEdorPrintSchemaMain, "BLOBINSERT");
			}
		}
		return true;
	}

	public static void main(String[] args) {

	}

}
