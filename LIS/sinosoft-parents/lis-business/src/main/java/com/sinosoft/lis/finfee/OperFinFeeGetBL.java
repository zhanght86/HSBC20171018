package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LJAGetBL;
import com.sinosoft.lis.bl.LJFIGetBL;
import com.sinosoft.lis.db.LJABonusGetDB;
import com.sinosoft.lis.db.LJAGetClaimDB;
import com.sinosoft.lis.db.LJAGetDB;
import com.sinosoft.lis.db.LJAGetDrawDB;
import com.sinosoft.lis.db.LJAGetEndorseDB;
import com.sinosoft.lis.db.LJAGetOtherDB;
import com.sinosoft.lis.db.LJAGetTempFeeDB;
import com.sinosoft.lis.db.LJAPayPersonDB;
import com.sinosoft.lis.db.LJFIGetDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LJABonusGetSchema;
import com.sinosoft.lis.schema.LJAGetClaimSchema;
import com.sinosoft.lis.schema.LJAGetDrawSchema;
import com.sinosoft.lis.schema.LJAGetEndorseSchema;
import com.sinosoft.lis.schema.LJAGetOtherSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJAGetTempFeeSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LJFIGetSchema;
import com.sinosoft.lis.vschema.LJABonusGetSet;
import com.sinosoft.lis.vschema.LJAGetClaimSet;
import com.sinosoft.lis.vschema.LJAGetDrawSet;
import com.sinosoft.lis.vschema.LJAGetEndorseSet;
import com.sinosoft.lis.vschema.LJAGetOtherSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJAGetTempFeeSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJFIGetSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HZM
 * @version 1.0
 */

public class OperFinFeeGetBL {
private static Logger logger = Logger.getLogger(OperFinFeeGetBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap map = new MMap();
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private GlobalInput tGI = new GlobalInput();
	// 标志位
	String flag = "";
	// 实付总表
	private LJAGetBL mLJAGetBL = new LJAGetBL();
	// 财务给付表
	private LJFIGetBL mLJFIGetBL = new LJFIGetBL();
	// 给付表(生存领取_实付)
	private LJAGetDrawSet mLJAGetDrawSet = new LJAGetDrawSet();
	// 批改补退费表(实收/实付)
	private LJAGetEndorseSet mLJAGetEndorseSet = new LJAGetEndorseSet();
	// 暂交费退费实付表
	private LJAGetTempFeeSet mLJAGetTempFeeSet = new LJAGetTempFeeSet();
	// 其他退费实付表
	private LJAGetOtherSet mLJAGetOtherSet = new LJAGetOtherSet();
	// 赔付实付表
	private LJAGetClaimSet mLJAGetClaimSet = new LJAGetClaimSet();
	// 红利给付实付表
	private LJABonusGetSet mLJABonusGetSet = new LJABonusGetSet();
	private PubConcurrencyLock mLock = new PubConcurrencyLock();//并发控制
	// 个人实收表
//	private LJAPayPersonSet mLJAPayPersonSet = new LJAPayPersonSet();

	// 业务处理相关变量

	public OperFinFeeGetBL() {

	}

	public static void main(String[] args) {

		CErrors tError = null;
		String FlagStr = "";
		String Content = "";
		String ActuGetNo = "370000000000051";
		;
		String PolNo = "";
		String PayMode = "1";
		String GetMoney = "100000";
		String AgentCode = "001";
		String Drawer = "tt";
		String AgentGroup = "0101011";
		String DrawerID = "55555555555555555";
		String EnterAccDate = "2005-08-09";
		// String ConfDate = request.getParameter("ConfDate");
		String Operator = "001";
		String ModifyDate = "2005-08-09";
		String BankCode = "";
		String ChequeNo = "";
		String BankAccNo = "";
		String AccName = "";
		String ActualDrawer = "";
		String ActualDrawerID = "";

		LJFIGetSchema tLJFIGetSchema; // 财务给付表
		LJAGetSchema tLJAGetSchema; // 实付总表
		LJAGetSet tLJAGetSet;

		// 准备传输数据 VData
		VData tVData = new VData();
		tLJAGetSchema = new LJAGetSchema();
		tLJAGetSchema.setActuGetNo(ActuGetNo);
		tVData.clear();
		tVData.add(tLJAGetSchema);
		logger.debug("Start 查询实付总表");
		LJAGetQueryUI tLJAGetQueryUI = new LJAGetQueryUI();
		if (!tLJAGetQueryUI.submitData(tVData, "QUERY")) {
			Content = " 查询实付总表失败，原因是: "
					+ tLJAGetQueryUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		} else { // 第一个括号
			tVData.clear();
			tLJAGetSet = new LJAGetSet();
			tLJAGetSchema = new LJAGetSchema();
			tVData = tLJAGetQueryUI.getResult();
			tLJAGetSet.set((LJAGetSet) tVData.getObjectByObjectName(
					"LJAGetSet", 0));
			tLJAGetSchema = (LJAGetSchema) tLJAGetSet.get(1);
			logger.debug("更新实付总表Schema");
			// tLJAGetSchema.setSumGetMoney(Double.parseDouble(GetMoney)+tLJAGetSchema.getSumGetMoney());
			tLJAGetSchema.setEnterAccDate(EnterAccDate);
			// tLJAGetSchema.setConfDate(ConfDate);
			tLJAGetSchema.setDrawer(Drawer);
			tLJAGetSchema.setDrawerID(DrawerID);
			tLJAGetSchema.setOperator(Operator);
			tLJAGetSchema.setActualDrawer(ActualDrawer);
			tLJAGetSchema.setActualDrawerID(ActualDrawerID);

			// 2-构造财务给付表的新纪录
			tLJFIGetSchema = new LJFIGetSchema();
			tLJFIGetSchema.setActuGetNo(ActuGetNo);
			tLJFIGetSchema.setPayMode(PayMode);
			tLJFIGetSchema.setOtherNo(tLJAGetSchema.getOtherNo());
			tLJFIGetSchema.setOtherNoType(tLJAGetSchema.getOtherNoType());
			tLJFIGetSchema.setGetMoney(GetMoney);
			tLJFIGetSchema.setShouldDate(tLJAGetSchema.getShouldDate());
			tLJFIGetSchema.setEnterAccDate(EnterAccDate);
			tLJFIGetSchema.setSaleChnl(tLJAGetSchema.getSaleChnl());
			tLJFIGetSchema.setAgentGroup(AgentGroup);
			tLJFIGetSchema.setAgentCode(AgentCode);
			tLJFIGetSchema.setSerialNo(tLJAGetSchema.getSerialNo());
			tLJFIGetSchema.setDrawer(ActualDrawer);
			tLJFIGetSchema.setDrawerID(ActualDrawerID);
			tLJFIGetSchema.setOperator(Operator);
			tLJFIGetSchema.setBankCode(BankCode);
			tLJFIGetSchema.setChequeNo(ChequeNo);
			tLJFIGetSchema.setBankAccNo(BankAccNo);
			tLJFIGetSchema.setAccName(AccName);

			// 入机时间等在BL层设置
			// 3-事务操作 插入纪录到财务给付表 更新实付总表
			logger.debug("start 事务操作");
			tVData.clear();
			tVData.add(tLJFIGetSchema);
			tVData.add(tLJAGetSchema);
			OperFinFeeGetUI tOperFinFeeGetUI = new OperFinFeeGetUI();
			tOperFinFeeGetUI.submitData(tVData, "VERIFY");
			tError = tOperFinFeeGetUI.mErrors;
			if (tError.needDealError()) {
				Content = " 失败，原因:" + tError.getFirstError();
				FlagStr = "Fail";
			}

		}
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		try
		{
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		logger.debug("OperateData:  " + cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
			return false;
		logger.debug("After getinputdata");

		// 进行业务处理
		if (!dealData())
			return false;
		logger.debug("After dealData！");
		// 准备往后台的数据
		if (!prepareOutputData())
			return false;
		logger.debug("After prepareOutputData");
		mInputData = null;

		}
		catch(Exception ex)
		{
			CError.buildErr(this, ex.toString());
			return false;	
		}
		finally
		{
			mLock.unLock();
		}
		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData() {
//		int i, iMax;
		boolean tReturn = false;
//		String tNo = "";

		// 处理集体信息数据
		// 添加纪录
		if (this.mOperate.equals("VERIFY")) {
			// zy  2009-4-8 进行并发组的控制
			if(!mLock.lock(mLJAGetBL.getActuGetNo(), "LF0003", tGI.Operator))
			{
				CError tError = new CError(mLock.mErrors.getLastError());
				CError.buildErr(this, "该号码处于业务其他操作中，请稍后再试！");
				this.mErrors.addOneError(tError);
				return false;
		
			}
			// 首先查询财务给付表中是否有该纪录
			if (!QyeryLJFIGet())
				return false;
			// zy  2009-4-8  增加对实付信息的查询和校验
			if(!QueryLjaget())
				return false;

			// 1-处理财务给付表
			mLJFIGetBL.setConfDate(CurrentDate);
			mLJFIGetBL.setConfMakeDate(CurrentDate);
			mLJFIGetBL.setMakeDate(CurrentDate); // 入机日期
			mLJFIGetBL.setMakeTime(CurrentTime); // 入机时间
			mLJFIGetBL.setModifyDate(CurrentDate); // 最后一次修改日期
			mLJFIGetBL.setModifyTime(CurrentTime); // 最后一次修改时间
			mLJFIGetBL.setManageCom(tGI.ManageCom);
			mLJFIGetBL.setShouldDate(mLJAGetBL.getMakeDate());
			mLJFIGetBL.setPolicyCom(mLJAGetBL.getManageCom());
			mLJFIGetBL.setAgentCom(mLJAGetBL.getAgentCom());
			mLJFIGetBL.setAgentType(mLJAGetBL.getAgentType());
			mLJFIGetBL.setAgentGroup(mLJAGetBL.getAgentGroup());
			mLJFIGetBL.setAgentCode(mLJAGetBL.getAgentCode());
			mLJFIGetBL.setOperator(tGI.Operator);
			mLJFIGetBL.setConfMakeTime(PubFun.getCurrentTime());

			// 2-实付总表，单项纪录
//			mLJAGetBL.setPayMode(mLJFIGetBL.getPayMode());
			mLJAGetBL.setOperator(tGI.Operator);
			mLJAGetBL.setPolicyCom(tGI.ComCode);
			// mLJAGetBL.setConfDate(CurrentDate);
//			if (mLJFIGetBL.getChequeNo() != null) {
//				mLJAGetBL.setBankAccNo(mLJFIGetBL.getChequeNo());
//			}
//			if (mLJFIGetBL.getBankCode() != null) {
//				mLJAGetBL.setBankCode(mLJFIGetBL.getBankCode());
//			}
			mLJAGetBL.setModifyDate(CurrentDate);
			mLJAGetBL.setModifyTime(CurrentTime);
			
			if("".equals(mLJAGetBL.getActualDrawer())||mLJAGetBL.getActualDrawer()==null)
				mLJAGetBL.setActualDrawer(mLJAGetBL.getDrawer());
			if("".equals(mLJAGetBL.getActualDrawerID()) || mLJAGetBL.getActualDrawerID()==null)
				mLJAGetBL.setActualDrawerID(mLJAGetBL.getDrawerID());
			mLJFIGetBL.setDrawer(mLJAGetBL.getActualDrawer());
			mLJFIGetBL.setDrawerID(mLJAGetBL.getActualDrawerID());

			// 3-查询实付总表:得到实付号码，其它号码类型
//			String ActuGetNo = mLJAGetBL.getActuGetNo();
			String OtherNoType = mLJAGetBL.getOtherNoType();
			logger.debug("LJAGet.getOtherNoType()=" + OtherNoType);

			// othernotype 对应类型
			// 2 生存领取 -- 保单号
			// 5 理赔 -- 赔案号
			// 10 保全 -- 受理号
			// 更新 给付表(生存领取_实付) LJAGetDraw

			if (OtherNoType.equals("1") || OtherNoType.equals("2")
					|| OtherNoType.equals("0")) {
				mLJAGetDrawSet = updateLJAGetDraw(mLJAGetBL);
				if (mLJAGetDrawSet == null)
					return false;
				else
					flag = "1";
			}

//			if (OtherNoType.equals("4") || OtherNoType.equals("YC")
//					|| OtherNoType.equals("3")) { // 更新 暂交费退费实付表
			if (OtherNoType.equals("4")){
				mLJAGetTempFeeSet = updateLJAGetTempFee(mLJAGetBL);
				if (mLJAGetTempFeeSet == null)
					return false;
				else
					flag = "4";
			}

			if (OtherNoType.equals("5")) { // 更新 赔付实付表 LJAGetClaim
				mLJAGetClaimSet = updateLJAGetClaim(mLJAGetBL);
				if (mLJAGetClaimSet == null)
					return false;
				else
					flag = "5";
			}

//			if (OtherNoType.equals("6") || OtherNoType.equals("8")) { // 更新
																		// 其他退费实付表
																		// LJAGetOther
			if (OtherNoType.equals("6")){
				mLJAGetOtherSet = updateLJAGetOther(mLJAGetBL);
				if (mLJAGetOtherSet == null)
					return false;
				else
					flag = "6";
			}

			if (OtherNoType.equals("7")) { // 更新 红利给付实付表 LJABonusGet
				mLJABonusGetSet = updateLJABonusGet(mLJAGetBL);
				if (mLJABonusGetSet == null)
					return false;
				else
					flag = "7";
			}
			if (OtherNoType.equals("10")) {
				mLJAGetDrawSet = updateLJAGetDraw(mLJAGetBL);
				mLJABonusGetSet = updateLJABonusGet(mLJAGetBL);
				mLJAGetEndorseSet = updateLJAGetEndorse(mLJAGetBL);
				if (mLJAGetEndorseSet == null && mLJABonusGetSet == null
						&& mLJAGetEndorseSet == null)
					return false;
				else
					flag = "10";
			}

			//MS暂不开发
//			if (OtherNoType.equals("9")) { // 更新 个人实收表 续期回退
//				mLJAPayPersonSet = updateLJAPayPerson(mLJAGetBL);
//				if (mLJAPayPersonSet == null)
//					return false;
//				else
//					flag = "9";
//			}
			tReturn = true;
		}
		return tReturn;
	}

	// 从输入数据中得到所有对象
	// 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData mInputData) {
		// 实付总表
		mLJAGetBL.setSchema((LJAGetSchema) mInputData.getObjectByObjectName(
				"LJAGetSchema", 0));
		// 财务给付表
		mLJFIGetBL.setSchema((LJFIGetSchema) mInputData.getObjectByObjectName(
				"LJFIGetSchema", 0));
		tGI = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		if (mLJAGetBL == null || mLJFIGetBL == null) {
			// @@错误处理
			CError.buildErr(this, "没有得到足够的数据，请您确认!");
			return false;
		}

		if (mLJAGetBL.getBankOnTheWayFlag() != null) {
			if (mLJAGetBL.getBankOnTheWayFlag().equals("1")) {
				// @@错误处理
				CError.buildErr(this, "正在送银行途中，不能财务付费!");
				return false;
			}
		}
		return true;
	}

	// 准备往后层输出所需要的数据
	// 输出：如果准备数据时发生错误则返回false,否则返回true
	private boolean prepareOutputData() {
		// mInputData = new VData();
		try {
			// 1-处理财务给付表
			LJFIGetSet tLJFIGetSet = new LJFIGetSet();
			tLJFIGetSet.add(mLJFIGetBL);
			map.put(tLJFIGetSet, "INSERT");
			// 2-实付总表，单项纪录
			LJAGetSet tLJAGetSet = new LJAGetSet();
			tLJAGetSet.add(mLJAGetBL);
			map.put(tLJAGetSet, "UPDATE");
			// 3-检验标志位
			if (flag.equals("1"))
				map.put(mLJAGetDrawSet, "UPDATE");
			if (flag.equals("4"))
				map.put(mLJAGetTempFeeSet, "UPDATE");
			// mInputData.add(mLJAGetTempFeeSet);
			if (flag.equals("5"))
				map.put(mLJAGetClaimSet, "UPDATE");
			if (flag.equals("6"))
				map.put(mLJAGetOtherSet, "UPDATE");
			if (flag.equals("7"))
				map.put(mLJABonusGetSet, "UPDATE");
			if (flag.equals("10")) {
				map.put(mLJAGetDrawSet, "UPDATE");
				map.put(mLJABonusGetSet, "UPDATE");
				map.put(mLJAGetEndorseSet, "UPDATE");
			}

//			if (flag.equals("9")) {
//				map.put(mLJAPayPersonSet, "UPDATE");
//			}

			// 4-加上标志位
			mResult.add(map);

			logger.debug("prepareOutputData:");
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this,  "在准备往后层处理所需要的数据时出错!");
			return false;
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public MMap getMap() {
		return map;
	}

	private boolean QyeryLJFIGet() {

		String ActuGetNo = mLJFIGetBL.getActuGetNo();
//		String PayMode = mLJFIGetBL.getPayMode();
		if (ActuGetNo == null) {
			CError.buildErr(this, "实付号码不能为空!");
			return false;
		}
		String sqlStr = "select * from LJFIGet where ActuGetNo='?ActuGetNo?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sqlStr);
		sqlbv.put("ActuGetNo", ActuGetNo);
		// sqlStr=sqlStr+" and PayMode='"+PayMode+"'";
		logger.debug("查询财务给付表:" + sqlStr);
		LJFIGetSet tLJFIGetSet = new LJFIGetSet();
//		LJFIGetSchema tLJFIGetSchema = new LJFIGetSchema();
		LJFIGetDB tLJFIGetDB = new LJFIGetDB();
		tLJFIGetSet = tLJFIGetDB.executeQuery(sqlbv);
		if (tLJFIGetDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJFIGetDB.mErrors);
			CError.buildErr(this, "财务给付表查询失败!");
			tLJFIGetSet.clear();
			return false;
		}
		if (tLJFIGetSet.size() > 0) {
			// @@错误处理
			CError.buildErr(this, "实付号码为：" + ActuGetNo + " 的记录已经存在！");
			return false;
		}

		return true;
	}

	/**
	 * 查询给付表(生存领取_实付)
	 * 
	 * @param LJAGetBL
	 * @return
	 */
	private LJAGetDrawSet updateLJAGetDraw(LJAGetBL mLJAGetBL) {
		if (mLJAGetBL == null) {
			// @@错误处理
			CError.buildErr(this, "传入参数不能为空！");
			return null;
		}
		String sqlStr = "select * from LJAGetDraw where ActuGetNo='?ActuGetNo?'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sqlStr);
		sqlbv1.put("ActuGetNo", mLJAGetBL.getActuGetNo());
		logger.debug("查询给付表(生存领取_实付):" + sqlStr);
		LJAGetDrawSet tLJAGetDrawSet = new LJAGetDrawSet();
		LJAGetDrawSchema tLJAGetDrawSchema = new LJAGetDrawSchema();
		LJAGetDrawDB tLJAGetDrawDB = new LJAGetDrawDB();
		tLJAGetDrawSet = tLJAGetDrawDB.executeQuery(sqlbv1);
		if (tLJAGetDrawDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJAGetDrawDB.mErrors);
			CError.buildErr(this, "给付表(生存领取_实付)查询失败!");
			tLJAGetDrawSet.clear();
			return null;
		}
		LJAGetDrawSet newLJAGetDrawSet = new LJAGetDrawSet();
		for (int n = 1; n <= tLJAGetDrawSet.size(); n++) {
			tLJAGetDrawSchema = new LJAGetDrawSchema();
			tLJAGetDrawSchema = tLJAGetDrawSet.get(n);
			tLJAGetDrawSchema.setConfDate(mLJAGetBL.getConfDate());
			tLJAGetDrawSchema.setEnterAccDate(mLJAGetBL.getEnterAccDate());
			tLJAGetDrawSchema.setModifyDate(CurrentDate);
			tLJAGetDrawSchema.setModifyTime(CurrentTime);
			newLJAGetDrawSet.add(tLJAGetDrawSchema);
		}
		return newLJAGetDrawSet;
	}

	/**
	 * 查询批改补退费表(实收/实付)
	 * 
	 * @param LJAGetBL
	 * @return
	 */
	private LJAGetEndorseSet updateLJAGetEndorse(LJAGetBL mLJAGetBL) {
		if (mLJAGetBL == null) {
			// @@错误处理
			CError.buildErr(this, "传入参数不能为空！");
			return null;
		}
		String sqlStr = "select * from LJAGetEndorse where ActuGetNo='?ActuGetNo?'";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(sqlStr);
		sqlbv2.put("ActuGetNo", mLJAGetBL.getActuGetNo());
		logger.debug("查询批改补退费:" + sqlStr);
		LJAGetEndorseSet tLJAGetEndorseSet = new LJAGetEndorseSet();
		LJAGetEndorseSchema tLJAGetEndorseSchema = new LJAGetEndorseSchema();
		LJAGetEndorseDB tLJAGetEndorseDB = new LJAGetEndorseDB();
		tLJAGetEndorseSet = tLJAGetEndorseDB.executeQuery(sqlbv2);
		if (tLJAGetEndorseDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJAGetEndorseDB.mErrors);
			CError.buildErr(this, "批改补退费表(实收/实付)查询失败!");
			tLJAGetEndorseSet.clear();
			return null;
		}
		LJAGetEndorseSet newLJAGetEndorseSet = new LJAGetEndorseSet();
		for (int n = 1; n <= tLJAGetEndorseSet.size(); n++) {
			tLJAGetEndorseSchema = new LJAGetEndorseSchema();
			tLJAGetEndorseSchema = tLJAGetEndorseSet.get(n);
			tLJAGetEndorseSchema.setGetConfirmDate(mLJAGetBL.getConfDate());
			tLJAGetEndorseSchema.setEnterAccDate(mLJAGetBL.getEnterAccDate());
			tLJAGetEndorseSchema.setModifyDate(CurrentDate);
			tLJAGetEndorseSchema.setModifyTime(CurrentTime);
			// tLJAGetEndorseSchema.setContNo(mLJAGetBL.getEnterAccDate());
			newLJAGetEndorseSet.add(tLJAGetEndorseSchema);
		}
		return newLJAGetEndorseSet;

	}

	/**
	 * 查询其他退费实付表
	 * 
	 * @param LJAGetBL
	 * @return
	 */
	private LJAGetOtherSet updateLJAGetOther(LJAGetBL mLJAGetBL) {
		if (mLJAGetBL == null) {
			// @@错误处理
			CError.buildErr(this, "传入参数不能为空！");
			return null;
		}
		String sqlStr = "select * from LJAGetOther where ActuGetNo='?ActuGetNo?'";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sqlStr);
		sqlbv3.put("ActuGetNo", mLJAGetBL.getActuGetNo());
		logger.debug("其他退费实付表:" + sqlStr);
		LJAGetOtherSet tLJAGetOtherSet = new LJAGetOtherSet();
		LJAGetOtherSchema tLJAGetOtherSchema = new LJAGetOtherSchema();
		LJAGetOtherDB tLJAGetOtherDB = new LJAGetOtherDB();
		tLJAGetOtherSet = tLJAGetOtherDB.executeQuery(sqlbv3);
		if (tLJAGetOtherDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJAGetOtherDB.mErrors);
			CError.buildErr(this, "其他退费实付表查询失败!");
			tLJAGetOtherSet.clear();
			return null;
		}
		LJAGetOtherSet newLJAGetOtherSet = new LJAGetOtherSet();
		for (int n = 1; n <= tLJAGetOtherSet.size(); n++) {
			tLJAGetOtherSchema = new LJAGetOtherSchema();
			tLJAGetOtherSchema = tLJAGetOtherSet.get(n);
			tLJAGetOtherSchema.setConfDate(mLJAGetBL.getConfDate());
			tLJAGetOtherSchema.setEnterAccDate(mLJAGetBL.getEnterAccDate());
			tLJAGetOtherSchema.setModifyDate(CurrentDate);
			tLJAGetOtherSchema.setModifyTime(CurrentTime);
			newLJAGetOtherSet.add(tLJAGetOtherSchema);
		}
		if (newLJAGetOtherSet.size() == 0) {
			// @@错误处理
			CError.buildErr(this, "没有可以更新的其他退费实付表！");
			return null;
		}
		return newLJAGetOtherSet;

	}

	/**
	 * 赔付实付表
	 * 
	 * @param LJAGetBL
	 * @return
	 */
	private LJAGetClaimSet updateLJAGetClaim(LJAGetBL mLJAGetBL) {
		if (mLJAGetBL == null) {
			// @@错误处理
			CError.buildErr(this, "传入参数不能为空！");
			return null;
		}
		String sqlStr = "select * from LJAGetClaim where ActuGetNo='?ActuGetNo?'";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(sqlStr);
		sqlbv4.put("ActuGetNo", mLJAGetBL.getActuGetNo());
		logger.debug("赔付实付表:" + sqlStr);
		LJAGetClaimSet tLJAGetClaimSet = new LJAGetClaimSet();
		LJAGetClaimSchema tLJAGetClaimSchema = new LJAGetClaimSchema();
		LJAGetClaimDB tLJAGetClaimDB = new LJAGetClaimDB();
		tLJAGetClaimSet = tLJAGetClaimDB.executeQuery(sqlbv4);
		if (tLJAGetClaimDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJAGetClaimDB.mErrors);
			CError.buildErr(this, "赔付实付表查询失败!");
			tLJAGetClaimSet.clear();
			return null;
		}
		LJAGetClaimSet newLJAGetClaimSet = new LJAGetClaimSet();
		for (int n = 1; n <= tLJAGetClaimSet.size(); n++) {
			tLJAGetClaimSchema = new LJAGetClaimSchema();
			tLJAGetClaimSchema = tLJAGetClaimSet.get(n);
			tLJAGetClaimSchema.setConfDate(mLJAGetBL.getConfDate());
			tLJAGetClaimSchema.setEnterAccDate(mLJAGetBL.getEnterAccDate());
			tLJAGetClaimSchema.setModifyDate(CurrentDate);
			tLJAGetClaimSchema.setModifyTime(CurrentTime);
			newLJAGetClaimSet.add(tLJAGetClaimSchema);
		}
		if (newLJAGetClaimSet.size() == 0) {
			// @@错误处理
			CError.buildErr(this, "没有可以更新的赔付实付表！");
			return null;
		}
		return newLJAGetClaimSet;

	}

	/**
	 * 暂交费退费实付表
	 * 
	 * @param LJAGetBL
	 * @return
	 */
	private LJAGetTempFeeSet updateLJAGetTempFee(LJAGetBL mLJAGetBL) {
		if (mLJAGetBL == null) {
			// @@错误处理
			CError.buildErr(this, "传入参数不能为空！");
			return null;
		}
		String sqlStr = "select * from LJAGetTempFee where ActuGetNo='?ActuGetNo?'";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(sqlStr);
		sqlbv5.put("ActuGetNo", mLJAGetBL.getActuGetNo());
		logger.debug("暂交费退费实付表:" + sqlStr);
		LJAGetTempFeeSet tLJAGetTempFeeSet = new LJAGetTempFeeSet();
		LJAGetTempFeeSchema tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
		LJAGetTempFeeDB tLJAGetTempFeeDB = new LJAGetTempFeeDB();
		tLJAGetTempFeeSet = tLJAGetTempFeeDB.executeQuery(sqlbv5);
		if (tLJAGetTempFeeDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJAGetTempFeeDB.mErrors);
			CError.buildErr(this, "暂交费退费实付表查询失败!");
			tLJAGetTempFeeSet.clear();
			return null;
		}
		LJAGetTempFeeSet newLJAGetTempFeeSet = new LJAGetTempFeeSet();
		for (int n = 1; n <= tLJAGetTempFeeSet.size(); n++) {
			tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
			tLJAGetTempFeeSchema = tLJAGetTempFeeSet.get(n);
			tLJAGetTempFeeSchema.setConfDate(mLJAGetBL.getConfDate());
			tLJAGetTempFeeSchema.setEnterAccDate(mLJAGetBL.getEnterAccDate());

			tLJAGetTempFeeSchema.setModifyDate(CurrentDate);
			tLJAGetTempFeeSchema.setModifyTime(CurrentTime);
			newLJAGetTempFeeSet.add(tLJAGetTempFeeSchema);
		}
		if (newLJAGetTempFeeSet.size() == 0) {
			// @@错误处理
			CError.buildErr(this, "没有可以更新的暂交费退费实付表！");
			return null;
		}
		return newLJAGetTempFeeSet;

	}

	/**
	 * 红利给付实付表
	 * 
	 * @param LJAGetBL
	 * @return
	 */
	private LJABonusGetSet updateLJABonusGet(LJAGetBL mLJAGetBL) {
		if (mLJAGetBL == null) {
			// @@错误处理
			CError.buildErr(this, "传入参数不能为空！");
			return null;
		}
		String sqlStr = "select * from LJABonusGet where ActuGetNo='?ActuGetNo?'";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(sqlStr);
		sqlbv6.put("ActuGetNo", mLJAGetBL.getActuGetNo());
		logger.debug("红利给付实付表:" + sqlStr);
		LJABonusGetSet tLJABonusGetSet = new LJABonusGetSet();
		LJABonusGetSchema tLJABonusGetSchema = new LJABonusGetSchema();
		LJABonusGetDB tLJABonusGetDB = new LJABonusGetDB();
		tLJABonusGetSet = tLJABonusGetDB.executeQuery(sqlbv6);
		if (tLJABonusGetDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJABonusGetDB.mErrors);
			CError.buildErr(this, "红利给付实付表查询失败!");
			tLJABonusGetSet.clear();
			return null;
		}
		LJABonusGetSet newLJABonusGetSet = new LJABonusGetSet();
		for (int n = 1; n <= tLJABonusGetSet.size(); n++) {
			tLJABonusGetSchema = new LJABonusGetSchema();
			tLJABonusGetSchema = tLJABonusGetSet.get(n);
			tLJABonusGetSchema.setConfDate(mLJAGetBL.getConfDate());
			tLJABonusGetSchema.setEnterAccDate(mLJAGetBL.getEnterAccDate());
			tLJABonusGetSchema.setModifyDate(CurrentDate);
			tLJABonusGetSchema.setModifyTime(CurrentTime);
			newLJABonusGetSet.add(tLJABonusGetSchema);
		}
		return newLJABonusGetSet;

	}

	private LJAPayPersonSet updateLJAPayPerson(LJAGetBL tLJAGetBL) {
		if (tLJAGetBL == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "OperFinFeeGetBL";
			tError.functionName = "updateLJABonusGet";
			tError.errorMessage = "传入参数不能为空！";
			this.mErrors.addOneError(tError);
			return null;
		}
		String Sql = "select * from ljapayperson where payno='?payno?'";
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(Sql);
		sqlbv7.put("payno", tLJAGetBL.getActuGetNo());
		logger.debug("个人实收表表:" + Sql);
		LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
		LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
		tLJAPayPersonSet = tLJAPayPersonDB.executeQuery(sqlbv7);
		if (tLJAPayPersonDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJAPayPersonDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "OperFinFeeGetBL";
			tError.functionName = "updateLJABonusGet";
			tError.errorMessage = "续收回退实付表查询失败!";
			this.mErrors.addOneError(tError);
			tLJAPayPersonSet.clear();
			return null;
		}
		LJAPayPersonSet NewLJAPayPersonSet = new LJAPayPersonSet();
		for (int i = 1; i <= tLJAPayPersonSet.size(); i++) {
			LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
			tLJAPayPersonSchema = tLJAPayPersonSet.get(i);
			tLJAPayPersonSchema.setEnterAccDate(tLJAGetBL.getEnterAccDate());
			tLJAPayPersonSchema.setConfDate(tLJAGetBL.getConfDate());
			tLJAPayPersonSchema.setModifyDate(CurrentDate);
			tLJAPayPersonSchema.setModifyTime(CurrentTime);
			NewLJAPayPersonSet.add(tLJAPayPersonSchema);
		}
		return NewLJAPayPersonSet;
	}
	private boolean QueryLjaget()
	{
		LJAGetDB tLJAGetDB = new LJAGetDB();
		String strSql = "select * from ljaget where actugetno='?actugetno?' ";
		SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
		sqlbv8.sql(strSql);
		sqlbv8.put("actugetno", mLJAGetBL.getActuGetNo());
		//EnterAccDate is null and ConfDate is null  and (bankonthewayflag='0' or bankonthewayflag is null) ";		 
		LJAGetSet mLJAGetSet = tLJAGetDB.executeQuery(sqlbv8);
	
		if (tLJAGetDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJAGetDB.mErrors);
			CError.buildErr(this, "实付查询失败!");
			mLJAGetSet.clear();
			return false;
		}
		if (mLJAGetSet.size() == 0) {
			// @@错误处理
			CError.buildErr(this, "未找到相关数据");
			mLJAGetSet.clear();
			return false;
		}

		LJAGetSchema tLJAGetSchema = new LJAGetSchema();
		tLJAGetSchema = mLJAGetSet.get(1);
		if (!(tLJAGetSchema.getEnterAccDate() == null	|| "".equals(tLJAGetSchema.getEnterAccDate()))) 
		{
		// @@错误处理
			CError.buildErr(this, "该笔付费记录已经付费确定，请核实！");
			return false;
		}
		if ("1".equals(tLJAGetSchema.getBankOnTheWayFlag())) 
		{
					// @@错误处理
			CError.buildErr(this, "该笔付费记录目前处于银行代付途中，请核实！");
			return false;
				
		}

		if(!("".equals(tLJAGetSchema.getPayMode()) || tLJAGetSchema.getPayMode()==null))
		{
			if(!("1".equals(tLJAGetSchema.getPayMode()) || "2".equals(tLJAGetSchema.getPayMode()) 
					|| "3".equals(tLJAGetSchema.getPayMode()) || "9".equals(tLJAGetSchema.getPayMode())) )
			{
				CError.buildErr(this, "该笔付费记录原付费方式不为现金、支票或网银代付，不允许在此确认，请核实！");
				return false;
			}
		}
		return true;
	}

}
