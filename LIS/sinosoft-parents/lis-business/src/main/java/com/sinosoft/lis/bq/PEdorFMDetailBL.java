package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDiscountDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.finfee.FinFeePubFun;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPCSpecSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDiscountSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LCDiscountSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPCSpecSet;
import com.sinosoft.lis.vschema.LPDiscountSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PEdorFMDetailBL {
private static Logger logger = Logger.getLogger(PEdorFMDetailBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = null;
	/** 封装要操作的数据，以便一次提交 */
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 全局数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPPolSchema mLPPolSchema = new LPPolSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private LJSGetEndorseSchema mLJSGetEndorseSchema = new LJSGetEndorseSchema();
	private LJSGetEndorseSet aLJSGetEndorseSet = new LJSGetEndorseSet();
	private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	
	private LPPolSet mLPPolSet = new LPPolSet();
	private LPPremSet mLPPremSet = new LPPremSet();
	private LPGetSet mLPGetSet = new LPGetSet();
	private LPDutySet mLPDutySet = new LPDutySet();
	/**保单保费折扣表*/
	private LPDiscountSet mLPDiscountSet = new LPDiscountSet();
	// add by jiaqiangli 2009-02-07 update lccont.prem
	private double mChgPrem = 0.0;
	
    //当前要变更的终交年龄年期可能和原来的不属于同一类型，需要进行转换，以便dealdate()中查询处理
	private int mPayEndYear =0;  
	
	private int mPayCount;
	private Reflections mReflections = new Reflections();

	public PEdorFMDetailBL() {
	}
	
	public static void main(String[] args) {
		PEdorFMDetailBL tPEdorFMDetailBL = new PEdorFMDetailBL();
		VData tVData = new VData();
		String tOperate = new String();
		tPEdorFMDetailBL.submitData(tVData, tOperate);
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		// 数据准备操作
		if (!dealData()) {
			return false;
		}
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	/** 获取前台数据 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData.getObjectByObjectName("LPEdorItemSchema", 0);
			// 围绕主险进行修改 借用lcpol来保存变更后的交费年期
			mLPPolSchema = (LPPolSchema) mInputData.getObjectByObjectName("LPPolSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
			if (mGlobalInput == null || mLPEdorItemSchema == null) {
				return false;
			}
		}
		catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}

		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		boolean flag = true;
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet.size() < 1) {
			CError tError = new CError();
			tError.moduleName = "PEdorGCDetailBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "无保全申请数据!";
			this.mErrors.addOneError(tError);
			return false;

		}
		// 获取保全主表数据，节省查询次数
		mLPEdorItemSchema = tLPEdorItemSet.get(1);
		if (!mLPEdorItemSchema.getEdorState().trim().equals("1")
				&& !mLPEdorItemSchema.getEdorState().trim().equals("3")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorIODetailBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "该保全已经申请确认不能修改!";
			this.mErrors.addOneError(tError);
			return false;
		}
	    //校验：只处理交费年期由长变短
		//目前处理A，Y两种类型
		int xInsuredAppAge=0;
		int xPayEndYear=0;
		String xPayEndYearFlag="";
		String check_sql="select insuredappage,PayEndYear,PayEndYearFlag from lcpol where contno='?contno?' and polno=mainpolno and appflag='1'";
        SQLwithBindVariables sbv1=new SQLwithBindVariables();
        sbv1.sql(check_sql);
        sbv1.put("contno", mLPEdorItemSchema.getContNo());
		ExeSQL xExeSQL = new ExeSQL();
		SSRS checkSSRS = xExeSQL.execSQL(sbv1);
		if(checkSSRS.MaxRow==0)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorIODetailBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "查找保单主险终交年龄年期信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		xInsuredAppAge=Integer.parseInt(checkSSRS.GetText(1, 1));
		xPayEndYear=Integer.parseInt(checkSSRS.GetText(1, 2));
		xPayEndYearFlag=checkSSRS.GetText(1, 3);
		logger.debug("InsuredAppAge="+xInsuredAppAge+"~~PayEndYear="+xPayEndYear+"~~~PayEndYearFlag="+xPayEndYearFlag);
		if(xPayEndYearFlag.equals(mLPPolSchema.getPayEndYearFlag())) //终交年龄年期标志相同，则直接判断大小
		{
			if(mLPPolSchema.getPayEndYear()>xPayEndYear)
			{
	            //@@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorIODetailBL";
				tError.functionName = "Preparedata";
				tError.errorMessage = "只允许进行交费年期由长变短的操作!";
				this.mErrors.addOneError(tError);
				return false;
			}
			mPayEndYear=mLPPolSchema.getPayEndYear();
		}
		else
		{
			if("A".equals(xPayEndYearFlag)) //原保单信息单位是岁
			{
				int pre_payyears =xPayEndYear-xInsuredAppAge;
				if(mLPPolSchema.getPayEndYear()>pre_payyears)
				{
		            //@@错误处理
					CError tError = new CError();
					tError.moduleName = "PEdorIODetailBL";
					tError.functionName = "Preparedata";
					tError.errorMessage = "只允许进行交费年期由长变短的操作!";
					this.mErrors.addOneError(tError);
					return false;
				}
				mPayEndYear = mLPPolSchema.getPayEndYear() + xInsuredAppAge;
			}
			else //原保单信息单位是年
			{
			   int pre_paytoage=xPayEndYear+xInsuredAppAge;  //计算出原交至年龄
			   if(mLPPolSchema.getPayEndYear()>pre_paytoage)
				{
		            //@@错误处理
					CError tError = new CError();
					tError.moduleName = "PEdorIODetailBL";
					tError.functionName = "Preparedata";
					tError.errorMessage = "只允许进行交费年期由长变短的操作!";
					this.mErrors.addOneError(tError);
					return false;
				}
			   mPayEndYear = mLPPolSchema.getPayEndYear() - xInsuredAppAge;
			}
		}
		return flag;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		logger.debug("dealData");
		LPPolSet insrtLPPolSet = new LPPolSet();
		LPPremSet insrtLPPremSet = new LPPremSet();
		LPGetSet insrtLPGetSet = new LPGetSet();
		LPDutySet insrtLPDutySet = new LPDutySet();

		boolean flag = true;

		// 准备LJSGetEndorse表同时处理mLPEdorItemSchema.setGetMoney
		double getMoney = 0;
		LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();

		// 保存保费与利息
		double[] payPrem = { 0.00, 0.00 };

		LPPolSchema tLPPolSchema = null;
		LCPolSchema tLCPolSchema = null;

		// 查询要变更的lcpolset 此处要求交费年期由长变短 对不支持的交费年期报错
//		String tAllPolSet = "select * from lcpol where appflag='1' and payintv > 0 "
//				// order by 确保先处理非附加豁免险
//				// 能保证豁免险是最后一个处理么？
//				+ "and contno = '" + mLPEdorItemSchema.getContNo() + "' and payendyear > "
//				+ mLPPolSchema.getPayEndYear() + " order by polno,mainpolno";
		//modify by jiaqiangli 2009-04-18 重新设置排序方式，确保豁免险最后进行联动处理
		String tAllPolSet = "select * from lcpol where appflag='1' and payintv > 0 "
			// order by 确保先处理非附加豁免险
			// 能保证豁免险是最后一个处理么？
			+ "and contno = '?contno?' and payendyear > ?mPayEndYear? order by (case when exists(select 1 from lmriskapp where riskcode=lcpol.riskcode and risktype7 in ('1','2')) then 1 else 0 end) ";
		LCPolDB tLCPolDB = new LCPolDB();
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(tAllPolSet);
		sbv2.put("contno", mLPEdorItemSchema.getContNo());
		sbv2.put("mPayEndYear", mPayEndYear);
		LCPolSet tLCPolSet = tLCPolDB.executeQuery(sbv2);

		for (int j = 1; j <= tLCPolSet.size(); j++) {
			
			insrtLPPolSet = new LPPolSet();
			insrtLPPremSet = new LPPremSet();
			insrtLPGetSet = new LPGetSet();
			insrtLPDutySet = new LPDutySet();
			
			tLCPolSchema = tLCPolSet.get(j);
			tLPPolSchema = new LPPolSchema();
			PubFun.copySchema(tLPPolSchema, tLCPolSchema);
			
			LCPolSchema tSourceLCPolSchema = new LCPolSchema();
			PubFun.copySchema(tSourceLCPolSchema, tLCPolSchema);

			// 为调用重算 需要暂时替换lpedoritem.polno(000000)为lcpol.polno
			logger.debug("before" + mLPEdorItemSchema.getPolNo());
			mLPEdorItemSchema.setPolNo(tLPPolSchema.getPolNo());
			logger.debug("after" + mLPEdorItemSchema.getPolNo());

			// 重算前的数据
			int tSourceIntv = tSourceLCPolSchema.getPayIntv();
			// 重算前的数据即为最后一期的保费结构
			double tSourcePrem = tSourceLCPolSchema.getPrem();

			logger.debug("mLPPolSchema.getPayYears()" + mLPPolSchema.getPayYears());
			tLPPolSchema.setPayEndYear(this.mLPPolSchema.getPayEndYear());
			tLPPolSchema.setPayEndYearFlag(this.mLPPolSchema.getPayEndYearFlag());
			logger.debug("重算前的Prem:" + tLPPolSchema.getPrem());

			// 豁免险处理
			if (judgeExempt(tLPPolSchema.getPolNo()) == true) {
				//保险期间要处理被豁免的交费期间
				tLPPolSchema.setInsuYear(this.mLPPolSchema.getPayEndYear());
				tLPPolSchema.setInsuYearFlag(this.mLPPolSchema.getPayEndYearFlag());
				// 保费变化
				tLPPolSchema.setAmnt(this.getExemptAMNT(mLPPolSet, tLPPolSchema.getRiskCode()));
				logger.debug("tLPPolSchema.amnt" + tLPPolSchema.getAmnt());
				
				if (tLPPolSchema.getAmnt() <= 0) {
					CError.buildErr(this, "计算豁免保额失败");
					return false;
				}
			}

			ReCalBL tReCalBL = new ReCalBL(tLPPolSchema, mLPEdorItemSchema);
			// 准备重算需要的保单表数据
			LCPolBL tLCPolBL = tReCalBL.getRecalPol(tLPPolSchema);
			// 准备重算需要的责任表数据
			LCDutyBLSet tLCDutyBLSet = tReCalBL.getRecalDuty(mLPEdorItemSchema);
			// 准备重算需要的保费项表数据
			LCPremSet tLCPremSet = tReCalBL.getRecalPrem(mLPEdorItemSchema);
			// 准备重算需要的领取项表数据
			LCGetBLSet tLCGetBLSet = tReCalBL.getRecalGet(mLPEdorItemSchema);
			for (int i = 1; i <= tLCDutyBLSet.size(); i++) {
				// comment by jiaqiangli 主要是豁免联动需要处理这个条件
				tLCDutyBLSet.get(i).setAmnt(tLPPolSchema.getAmnt());
				tLCDutyBLSet.get(i).setPayEndYear(tLPPolSchema.getPayEndYear());
				tLCDutyBLSet.get(i).setPayEndYearFlag(tLPPolSchema.getPayEndYearFlag());
			}

			// 重算 得到保费结构
			if (!tReCalBL.recalWithData(tLCPolBL, tLCDutyBLSet, tLCPremSet, tLCGetBLSet, mLPEdorItemSchema)) {
				this.mErrors.copyAllErrors(tReCalBL.mErrors);
				CError.buildErr(this, "保费重算失败,可能是有险种不支持此变更的交费年期!");
				return false;
			}
			
			//重算后的保费结构
			tLPPolSchema.setSchema(tReCalBL.aftLPPolSet.get(1));
			insrtLPDutySet.add(tReCalBL.aftLPDutySet);
			insrtLPGetSet.add(tReCalBL.aftLPGetSet);
			
			// 重新计算加费金额
			AddPremReCalBQInterface tAddPremReCalBQInterface = new AddPremReCalBQInterface(mGlobalInput);
			LPPremSet afterLPPremSet = tAddPremReCalBQInterface.recalAddPrem(tLPPolSchema, tReCalBL.aftLPPremSet, tLCDutyBLSet);
			if (afterLPPremSet == null || afterLPPremSet.size() == 0) {
				return false;
			}
			
			tLPPolSchema.setPrem(tLPPolSchema.getPrem()+tAddPremReCalBQInterface.getAddFeeMinus());
			insrtLPPremSet.add(afterLPPremSet);

			// 变动保费 汇总lcpol的差值至lccont
			mChgPrem += tLPPolSchema.getPrem() - tSourceLCPolSchema.getPrem();
			
			LCPolSchema tempLCPolSchema = new LCPolSchema();
			mReflections.transFields(tempLCPolSchema, tLPPolSchema);

			// payIntv 重算前的交费间隔
			payPrem = calPayPrem(tempLCPolSchema, tSourceIntv, tSourcePrem);
			logger.debug("补交的保险费差额:" + payPrem);

			tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPolSchema.setModifyDate(PubFun.getCurrentDate());
			tLPPolSchema.setModifyTime(PubFun.getCurrentTime());
			
			insrtLPPolSet.add(tLPPolSchema.getSchema());
			
			//lcduty
			for (int i = 1; i <= insrtLPDutySet.size(); i++) {
				//LCDUTY的standprem重算已经置上 此处prem需要累加加费重算的差额 同lcpol的处理
				insrtLPDutySet.get(i).setPrem(insrtLPDutySet.get(i).getPrem()+tAddPremReCalBQInterface.getAddFeeMinus());
				insrtLPDutySet.get(i).setModifyDate(PubFun.getCurrentDate());
				insrtLPDutySet.get(i).setModifyTime(PubFun.getCurrentTime());			
			}

			// add by jiaqiangli 2009-04-03 此处有可能是预约生效的新增附加险 做归0处理
			String tpaycountsql = "select (case when max(paycount) is not null then max(paycount) else 0 end) from LJAPayPerson where polno = '?polno?' "
					+ "and substr(trim(PayPlanCode),1,6) <> '000000' and paytype = 'ZC' ";
			SQLwithBindVariables sbv3=new SQLwithBindVariables();
			sbv3.sql(tpaycountsql);
			sbv3.put("polno", mLPEdorItemSchema.getPolNo());
			ExeSQL zExeSQL = new ExeSQL();
			String tPayCount = zExeSQL.getOneValue(sbv3);

			if (tPayCount == null || tPayCount.equals("")) {
				CError.buildErr(this, "查询应收缴费表失败!");
				return false;
			}
			// 当前缴费次数
			mPayCount = Integer.parseInt(tPayCount);

			LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
			tLJSPayPersonSchema.setPolNo(tLPPolSchema.getPolNo());
			tLJSPayPersonSchema.setGrpContNo(tLPPolSchema.getGrpContNo());
			tLJSPayPersonSchema.setGrpPolNo(tLPPolSchema.getGrpPolNo());
			tLJSPayPersonSchema.setGrpContNo(tLPPolSchema.getGrpContNo());
			tLJSPayPersonSchema.setGrpPolNo(tLPPolSchema.getGrpPolNo());
			tLJSPayPersonSchema.setContNo(tLPPolSchema.getContNo());
			tLJSPayPersonSchema.setManageCom(tLPPolSchema.getManageCom());
			tLJSPayPersonSchema.setOperator(mGlobalInput.Operator);
			tLJSPayPersonSchema.setRiskCode(tLPPolSchema.getRiskCode());
			tLJSPayPersonSchema.setAppntNo(tLPPolSchema.getAppntNo());
			tLJSPayPersonSchema.setPayAimClass("1");
			tLJSPayPersonSchema.setDutyCode(insrtLPPremSet.get(1).getDutyCode());
			tLJSPayPersonSchema.setPayPlanCode(insrtLPPremSet.get(1).getPayPlanCode());
			tLJSPayPersonSchema.setPayIntv(tLPPolSchema.getPayIntv());
			tLJSPayPersonSchema.setPayDate(mLPEdorItemSchema.getEdorValiDate());
			tLJSPayPersonSchema.setPayType(mLPEdorItemSchema.getEdorType());
			tLJSPayPersonSchema.setLastPayToDate(mLPEdorItemSchema.getEdorValiDate());
			tLJSPayPersonSchema.setCurPayToDate(tLPPolSchema.getPaytoDate());
			tLJSPayPersonSchema.setAgentCode(tLPPolSchema.getAgentCode());
			tLJSPayPersonSchema.setAgentGroup(tLPPolSchema.getAgentGroup());
			tLJSPayPersonSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());

			// 保费项的保存 SumActuPayMoney = SumDuePayMoney
			tLJSPayPersonSchema.setSumActuPayMoney(payPrem[0]);
			tLJSPayPersonSchema.setSumDuePayMoney(payPrem[0]);

			tLJSPayPersonSchema.setPayCount(mPayCount);
			tLJSPayPersonSchema.setMakeDate(PubFun.getCurrentDate());
			tLJSPayPersonSchema.setMakeTime(PubFun.getCurrentTime());
			tLJSPayPersonSchema.setModifyDate(PubFun.getCurrentDate());
			tLJSPayPersonSchema.setModifyTime(PubFun.getCurrentTime());
			tLJSPayPersonSchema.setBankAccNo("");
			tLJSPayPersonSchema.setBankCode("");
			tLJSPayPersonSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
			tLJSPayPersonSchema.setCurrency(insrtLPPremSet.get(1).getCurrency());
			// comment by jiaqiangli 2009-07-09 财务核销方式不再需要存储这个中间表
			// add by jiaqiangli 2009-01-04 附加豁免金额为负时不处理
//			if (tLJSPayPersonSchema.getSumDuePayMoney() > 0)
//				mLJSPayPersonSet.add(tLJSPayPersonSchema);

			// 添加保费LJSPayPerson和LJSGetEndorse
			if (!createLJSGetEndorseSchema(tLJSPayPersonSchema)) {
				mErrors.addOneError("添加保费LJSPayPerson和LJSGetEndorse!");
				return false;
			}

//			logger.debug("payPrem[1]" + payPrem[1]);
//			// 添加利息到LJSGetEndorse
//			if (!createLJSGetEndorseSchema(tLJSPayPersonSchema, payPrem[1])) {
//				CError.buildErr(this, "添加利息到LJSGetEndorse!");
//				return false;
//			}

			mLPPolSet.add(insrtLPPolSet);
			mLPDutySet.add(insrtLPDutySet);
			mLPPremSet.add(insrtLPPremSet);
			mLPGetSet.add(insrtLPGetSet);
			//************增加折扣处理***gaoph 20110127 ****start******************
			/*
			 * 交费频次变更折扣保费: 变更前折扣保费*期数，变更后折扣部分保费*至交至日期数 计算出一个值
			 * 如果变更前存在某期没有折扣保费，或者折扣保费不同的情况，此处需要单独处理折扣部分计算：
			 * 取变更前的所有折扣保费之和，变更后的计算根据需要算期数。
			 * 这里的处理都是险种层的，是不是补退费表的dutycode和payplancode可置为000000
			 */
			LCDiscountSet tLCDiscountSet = new LCDiscountSet();
			LCDiscountDB tLCDiscountDB = new LCDiscountDB();
			tLCDiscountDB.setPolNo(tLPPolSchema.getPolNo());
			tLCDiscountSet = tLCDiscountDB.query();
			if(tLCDiscountSet!=null && tLCDiscountSet.size()>0)
			{
				//得到折扣
				LPDiscountSet tLPDiscountSet = new LPDiscountSet();
				for(int i=1;i<=tLCDiscountSet.size();i++)
				{
					LPDiscountSchema tLPDiscountSchema = new LPDiscountSchema();
					mReflections.transFields(tLPDiscountSchema, tLCDiscountSet.get(i));
					tLPDiscountSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPDiscountSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					tLPDiscountSet.add(tLPDiscountSchema);
				}
				/*
				 * 1、第一步：取保单折扣的保费信息
				 */
				
				String sourcePremSql = "select (case when sum(sumactupaymoney) is not null then sum(sumactupaymoney) else 0 end) from LJAPayPerson where polno = '?polno?' "
					+ "and paycount='?mPayCount?' and paytype in (select code from ldcode where codetype='discounttype')";
				SQLwithBindVariables sbv4=new SQLwithBindVariables();
				sbv4.sql(sourcePremSql);
				sbv4.put("polno", mLPEdorItemSchema.getPolNo());
				sbv4.put("mPayCount", mPayCount);
				String sourceZKPrem = zExeSQL.getOneValue(sbv4);
				double tsourceZKPrem = Double.parseDouble(sourceZKPrem); 
				logger.debug("折扣的保险费金额:" + tsourceZKPrem);
				/*
				 *2、第二步：取保单重算后的折扣保费 
				 */
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("PayCount",String.valueOf(this.mPayCount+1));
				tTransferData.setNameAndValue("PayIntv", String.valueOf(tLPPolSchema.getPayIntv()));
				tTransferData.setNameAndValue("Operator",this.mGlobalInput.Operator);
				PEdorDiscountCalBL tDiscountCalBL = new PEdorDiscountCalBL();
				VData tzkVData = new VData();
				tzkVData.add(tLPPolSchema);
				tzkVData.add(insrtLPPremSet);
				tzkVData.add(tLPDiscountSet);
				tzkVData.add(this.mLPEdorItemSchema);
				tzkVData.add(tTransferData);
				//得到该保单折扣减去的钱 ，为负值
				if(!tDiscountCalBL.calculate(tzkVData))
				{
					CError.buildErr(this, "折扣计算失败！");
					return false;
				}
				double tempZKPrem = 0.00;
				//得到折扣和应收子表数据
				VData rVData = tDiscountCalBL.getResult();
				LJSPayPersonSet tLJSPayPersonSet = (LJSPayPersonSet)rVData.getObjectByObjectName("LJSPayPersonSet", 0);
				if(tLJSPayPersonSet!=null)
				{
					for(int i=1;i<=tLJSPayPersonSet.size();i++)
					{
						tempZKPrem += tLJSPayPersonSet.get(i).getSumActuPayMoney();
					}
				}
				logger.debug("重算后折扣的保险费金额:" + tempZKPrem);
				LPDiscountSet insrtLPDiscountSet = (LPDiscountSet)rVData.getObjectByObjectName("LPDiscountSet", 0);
				if(insrtLPDiscountSet!=null)
				{
					mLPDiscountSet.add(insrtLPDiscountSet);
				}
				/*
				 * 3、第三步：计算折扣的补退费及重新计算相关利息
				 */
				tempLCPolSchema = new LCPolSchema();
				
				mReflections.transFields(tempLCPolSchema, tLPPolSchema);
				tempLCPolSchema.setPrem(tempZKPrem);
				double[] zkPayPrem = new double[]{0.00,0.00};
				// payIntv 重算前的交费间隔
				zkPayPrem = calPayPrem(tempLCPolSchema, tSourceIntv, tsourceZKPrem);
				if(tLJSPayPersonSet!=null){
					createLJSGetEndorseSchemaZK(tLJSPayPersonSet.get(1),zkPayPrem[0]);
				}
				
				tempLCPolSchema.setPrem(tLPPolSchema.getPrem()+tempZKPrem);
				double[] lxPayPrem = new double[]{0.00,0.00};
				lxPayPrem = calPayPrem(tempLCPolSchema,tSourceIntv,tsourceZKPrem+tSourcePrem,true);
				
				// 添加利息到LJSGetEndorse
				if (!createLJSGetEndorseSchema(tLJSPayPersonSchema, lxPayPrem[1])) {
					CError.buildErr(this, "添加利息到LJSGetEndorse!");
					return false;
				}
			}		
			//************增加折扣处理***gaoph 20110127 ****end******************
		}

		mMap.put(mLPPolSet, "DELETE&INSERT");
		mMap.put(mLPDutySet, "DELETE&INSERT");
		mMap.put(mLPPremSet, "DELETE&INSERT");
		mMap.put(mLPGetSet, "DELETE&INSERT");

		// 方便此处mLPEdorItemSchema.setGetMoney(getMoney);
		logger.debug("aLJSGetEndorseSet.size()" + aLJSGetEndorseSet.size());
		for (int i = 1; i <= aLJSGetEndorseSet.size(); i++) {
			LJSGetEndorseSchema tLJS = new LJSGetEndorseSchema();
			tLJS.setSchema(aLJSGetEndorseSet.get(i));
			getMoney += tLJS.getGetMoney();
			tLJSGetEndorseSet.add(tLJS);
		}
		mMap.put(tLJSGetEndorseSet, "DELETE&INSERT");

		mMap.put(mLJSPayPersonSet, "DELETE&INSERT");

		// lpcontschema
		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		LPContSchema tLPContSchema = new LPContSchema();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("查询保单信息失败!");
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();
		mReflections.transFields(tLPContSchema, tLCContSchema);
		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		// 只需要处理prem字段即可 sumprem字段含义不明确 = 当前prem*期数 补退费保全已经平衡
		tLPContSchema.setPrem(tLCContSchema.getPrem() + mChgPrem);
		tLPContSchema.setModifyDate(PubFun.getCurrentDate());
		tLPContSchema.setModifyTime(PubFun.getCurrentTime());
		mMap.put(tLPContSchema, "DELETE&INSERT");

		// 循环处理的时候lpedoritem.polno置成了lcpol.polno
		// 现在重置这个字段
		mLPEdorItemSchema.setPolNo("000000");

		// mLPEdorItemSchema相当于5.3系统中lpedormain表
		// 但是6.0中的lpedormain表是在申请确认中处理的
		mLPEdorItemSchema.setEdorState("3");
		mLPEdorItemSchema.setGetMoney(getMoney);
		mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		mMap.put(mLPEdorItemSchema, "UPDATE");
		
		//为核保准备数据
		//投保人
		LCAppntDB tLCAppntDB = new LCAppntDB();
		LCAppntSchema tLCAppntSchema = null;
		LPAppntSet tLPAppntSet = new LPAppntSet();
		LPAppntSchema tLPAppntSchema = null;
		
		tLCAppntDB.setContNo(mLPEdorItemSchema.getContNo());		
		tLCAppntSchema=tLCAppntDB.query().get(1);
		tLPAppntSchema = new LPAppntSchema();
		mReflections.transFields(tLPAppntSchema, tLCAppntSchema);
		tLPAppntSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAppntSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPAppntSet.add(tLPAppntSchema);
		
		//被保人
		// 查询客户所有相关保单（客户为投保人）
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		LCInsuredSchema tLCInsuredSchema = null;
		LPInsuredSet tLPInsuredSet = new LPInsuredSet();
		LPInsuredSchema tLPInsuredSchema = null;
		
		
		tLCInsuredDB.setContNo(mLPEdorItemSchema.getContNo());		
		tLCInsuredSchema=tLCInsuredDB.query().get(1);
		tLPInsuredSchema = new LPInsuredSchema();
		mReflections.transFields(tLPInsuredSchema, tLCInsuredSchema);
		tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsuredSet.add(tLPInsuredSchema);
		
		mMap.put(tLPInsuredSet, "DELETE&INSERT");
		mMap.put(tLPAppntSet, "DELETE&INSERT");
		
	    //告知
		LPCSpecSet mLPCSpecSet = new LPCSpecSet();
		LCCSpecDB tLCCSpecDB = new LCCSpecDB();
		LCCSpecSet tLCCSpecSet = new LCCSpecSet();
		tLCCSpecDB.setContNo(mLPEdorItemSchema.getContNo());
		tLCCSpecSet = tLCCSpecDB.query();
		if(tLCCSpecSet.size()>0)
		{
			for(int k=1;k<=tLCCSpecSet.size();k++)
			{
				LPCSpecSchema mLPCSpecSchema = new LPCSpecSchema();
				mReflections.transFields(mLPCSpecSchema, tLCCSpecSet.get(k));	
				mLPCSpecSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				mLPCSpecSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				mLPCSpecSet.add(mLPCSpecSchema);				
			}
			mMap.put(mLPCSpecSet, "DELETE&INSERT");
		}

		return flag;
	}
	/**
	 * 由LJSPayPerson信息生成当期保费的折扣批改补退费信息
	 * 
	 * @param cLJSPayPersonSchema
	 * @return
	 */
	// 添加保费至aLJSGetEndorseSet
	private boolean createLJSGetEndorseSchemaZK(
			LJSPayPersonSchema cLJSPayPersonSchema, double zkPrem) {
		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		tLJSGetEndorseSchema = this.initLJSGetEndorse("ZK");
		mReflections.transFields(tLJSGetEndorseSchema, cLJSPayPersonSchema);
		tLJSGetEndorseSchema.setGetMoney(zkPrem);

		// 处理SubFeeOperationType
		tLJSGetEndorseSchema.setSubFeeOperationType(cLJSPayPersonSchema
				.getPayType()
				+ String.valueOf(cLJSPayPersonSchema.getPayCount()));
        //营改增 add zhangyingfeng 2016-07-14
        //价税分离 计算器
        TaxCalculator.calBySchema(tLJSGetEndorseSchema);
        //end zhangyingfeng 2016-07-14
		aLJSGetEndorseSet.add(tLJSGetEndorseSchema);

		logger.debug("aLJSGetEndorseSet.size()"
				+ aLJSGetEndorseSet.size());
		return true;
	}
	private boolean judgeExempt(String tPolNo) {
		// add by jiaqiangli 附加豁免联动处理 2008-12-22
		LCPolDB tExemptLCPolDB = new LCPolDB();
		LCPolSet tExemptLCPolSet = new LCPolSet();
		// 判断是否有附加豁免关联
		String tExemptSQL = "select * from lcpol a where a.appflag='1' and polno = '?tPolNo?' and contno = '?contno?' and riskcode in (select riskcode from lmriskapp where risktype7 in ('1','2')) "
				// exists 作关联的判断逻辑
				+ "and payintv > 0 and exists (select 1 from lcpol b,ldcode1 c where contno = '?contno?' and c.code=a.riskcode and b.riskcode=c.code1)";
		SQLwithBindVariables sbv5=new SQLwithBindVariables();
		sbv5.sql(tExemptSQL);
		sbv5.put("tPolNo", tPolNo);
		sbv5.put("contno", mLPEdorItemSchema.getContNo());
		tExemptLCPolSet = tExemptLCPolDB.executeQuery(sbv5);
		logger.debug("tExemptLCPolSet.size()" + tExemptLCPolSet.size());
		if (tExemptLCPolSet.size() == 1)
			return true;
		else
			return false;
	}

	private double getExemptAMNT(LPPolSet insrtLPPolSet, String tRiskCode) {
		// 累加豁免险保费
		double tAllExemptPrem = 0.00;
		// 豁免累加
		String tExemptSQL = "select polno from lcpol where appflag='1' and  contno = '?contno?' and payintv > 0 and riskcode in (select code1 from ldcode1 where codetype = 'freerisk' and code='?tRiskCode?')";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tExemptSQL);
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
		sqlbv.put("tRiskCode", tRiskCode);
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv);
		// 查询重算后的保费
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			for (int j = 1; j <= insrtLPPolSet.size(); j++) {
				if (tSSRS.GetText(i, 1).equals(insrtLPPolSet.get(j).getPolNo())) {
					tAllExemptPrem += insrtLPPolSet.get(j).getPrem();
				}
			}
		}
		return tAllExemptPrem;
	}

	private boolean createLJSGetEndorseSchema(LJSPayPersonSchema tmpLJSPayPersonSchema, double tInterest) {
		// 生成保全RE保费复利记录
		// comment by jiaqiangli 2008-12-24 利息为为负值的不插表
		if (tInterest > 0) {
			LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
			tLJSGetEndorseSchema = this.initLJSGetEndorse("LX");
			mReflections.transFields(tLJSGetEndorseSchema, tmpLJSPayPersonSchema);
			// 0为补费1为退费
			tLJSGetEndorseSchema.setGetFlag("0");
			// GetMoney保存利息
			tLJSGetEndorseSchema.setGetMoney(tInterest);
			tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_PremInterest + String.valueOf(mPayCount));
			tLJSGetEndorseSchema.setPolNo(mLPEdorItemSchema.getPolNo());
	          //营改增 add zhangyingfeng 2016-07-14
	          //价税分离 计算器
	          TaxCalculator.calBySchema(tLJSGetEndorseSchema);
	          //end zhangyingfeng 2016-07-14
			aLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		}
		return true;
	}
	/**
	 * 计算补退费方法，返回保费差额及利息
	 * @param xLCPolSchema
	 * @param xPayIntv
	 * @param xStandPrem
	 * @return
	 */
	public double[] calPayPrem(LCPolSchema xLCPolSchema, int xPayIntv, double xStandPrem) 
	{
		return calPayPrem(xLCPolSchema,xPayIntv,xStandPrem,false);
	}
	// 参数 xLCPolSchema表示为重算后的保单信息
	// 参数 xPayIntv表示为重算前的PayIntv
	// 参数 xStandPrem表示为重算前的StandPrem
	// 参数 lxFlag 表示是否要计算利息，因方法多次调用，利息计算比较复杂根据条件判断是否需要处理
	public double[] calPayPrem(LCPolSchema xLCPolSchema, int xPayIntv, double xStandPrem,boolean lxFlag) {
		// 保费和利息分开
		double banlancePrem[] = { 0.0000, 0.0000 };
		double GPS = xLCPolSchema.getPrem(); // 变更后的保费

		// 保存已交保费和
		double tHasPayPrem = 0.00;
		// 补交利息
		double tBJInterest = 0.00;

		// 等效成交费的间隔月份数除以当前的payintv
		int PayCount = PubFun.calInterval(xLCPolSchema.getCValiDate(), xLCPolSchema.getPaytoDate(), "M") / xPayIntv;

		// 每次的补交保费的利息
		double xInterest = 0.00;

		// 每次循环的计息的起期 相当于lastpaytodate
		String tTepStartDate = "";

		// 这里应该是循环累加每一次缴费，因为每次的保费都有可能不一样
		// 年缴保单每年交的保费不一样???均衡保费（level premium)
		for (int i = 0; i <= PayCount - 1; i++) {
			// 以最后的当前保费来看历史的每一期交费处理
			logger.debug((i + 1) + "次交保费" + xStandPrem);

			// 累计已交保费
			tHasPayPrem += xStandPrem;

			tTepStartDate = FinFeePubFun.calOFDate(xLCPolSchema.getCValiDate(), i * xPayIntv, "M", xLCPolSchema
					.getCValiDate());
			if(lxFlag)
			{
				// 补交保费利息为贷款利息 L
				xInterest = (GPS - xStandPrem)
						* AccountManage.calMultiRateMS(tTepStartDate, mLPEdorItemSchema.getEdorValiDate(), xLCPolSchema
								.getRiskCode(), mLPEdorItemSchema.getEdorType(), "L", "C", "Y",xLCPolSchema.getCurrency());
	
				// 累计补交保费的利息
				tBJInterest += xInterest;
			}
		}

		// 补交保费
		banlancePrem[0] = GPS * PayCount - tHasPayPrem;
		// 补交保费产生的利息
		banlancePrem[1] = tBJInterest;

		logger.debug("应补交的保费:" + banlancePrem[0]);
		logger.debug("应补交的利息:" + banlancePrem[1]);

		return banlancePrem;
	}

	/**
	 * 生成交退费记录
	 * 
	 * @return
	 */
	private LJSGetEndorseSchema initLJSGetEndorse(String strfinType) {
		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		mLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
		mLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
		mLJSGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema.getEdorType());
		// 从描述表中获取财务接口类型，modify by Minim at 2003-12-23
		String finType = BqCalBL.getFinType(mLPEdorItemSchema.getEdorType(), strfinType, mLPEdorItemSchema.getPolNo());
		if (finType.equals("")) {
			// @@错误处理
			CError.buildErr(this, "在LDCode1中缺少保全财务接口转换类型编码!");
			return null;
		}
		mLJSGetEndorseSchema.setFeeFinaType(finType);
		mLJSGetEndorseSchema.setDutyCode("0");
		mLJSGetEndorseSchema.setPayPlanCode("0");
		// 走保全交费财务流程
		mLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo()); // 保全给付用批单号
		mLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付
		mReflections.transFields(mLJSGetEndorseSchema, mLCPolSchema);
		mLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
		mLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
		mLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
		mLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
		mLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
		mLJSGetEndorseSchema.setGetDate(mLPEdorItemSchema.getEdorValiDate());
		// 0为补费1为退费
		mLJSGetEndorseSchema.setGetFlag("0");
		tLJSGetEndorseSchema.setSchema(mLJSGetEndorseSchema);

		return tLJSGetEndorseSchema;
	}

	/**
	 * 由LJSPayPerson信息生成当期保费的批改补退费信息
	 * 
	 * @param cLJSPayPersonSchema
	 * @return
	 */
	// 添加保费至aLJSGetEndorseSet
	private boolean createLJSGetEndorseSchema(LJSPayPersonSchema cLJSPayPersonSchema) {

		// add by jiaqiangli 2009-01-04 附加豁免金额为负时不处理
		if (cLJSPayPersonSchema.getSumDuePayMoney() > 0) {
			LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
			tLJSGetEndorseSchema = this.initLJSGetEndorse("BF");
			mReflections.transFields(tLJSGetEndorseSchema, cLJSPayPersonSchema);
			tLJSGetEndorseSchema.setGetMoney(cLJSPayPersonSchema.getSumDuePayMoney());
			
			tLJSGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema.getEdorType());
			tLJSGetEndorseSchema.setFeeFinaType(mLJSGetEndorseSchema.getFeeFinaType());
			// 走保全交费财务流程
			tLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo()); // 保全给付用批单号
			tLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付
			tLJSGetEndorseSchema.setGetDate(mLPEdorItemSchema.getEdorValiDate());
			// 0为补费1为退费
			tLJSGetEndorseSchema.setGetFlag("0");

			// 处理SubFeeOperationType
			if (cLJSPayPersonSchema.getPayPlanCode() != null
					&& cLJSPayPersonSchema.getPayPlanCode().startsWith("000000")) {
				LCPremDB tLCPremDB = new LCPremDB();
				tLCPremDB.setPolNo(cLJSPayPersonSchema.getPolNo());
				tLCPremDB.setDutyCode(cLJSPayPersonSchema.getDutyCode());
				tLCPremDB.setPayPlanCode(cLJSPayPersonSchema.getPayPlanCode());
				if (!tLCPremDB.getInfo()) {
					CError.buildErr(this, "查询交费计划类型失败!");
					return false;
				}
				if ("01".equals(tLCPremDB.getPayPlanType()) || "03".equals(tLCPremDB.getPayPlanType())) {
					tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_InsurAddPremHealth
							+ String.valueOf(cLJSPayPersonSchema.getPayCount()));
				}
				else if ("02".equals(tLCPremDB.getPayPlanType()) || "04".equals(tLCPremDB.getPayPlanType())) {
					tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_InsurAddPremOccupation
							+ String.valueOf(cLJSPayPersonSchema.getPayCount()));
				}
				else {
					CError.buildErr(this, "加费交费计划类型错误!");
					return false;
				}
			}
			else {
				tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_Prem
						+ String.valueOf(cLJSPayPersonSchema.getPayCount()));
			}
			tLJSGetEndorseSchema.setCurrency(cLJSPayPersonSchema.getCurrency());
			aLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		}
		return true;
	}

	/**
	 * getResult
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
