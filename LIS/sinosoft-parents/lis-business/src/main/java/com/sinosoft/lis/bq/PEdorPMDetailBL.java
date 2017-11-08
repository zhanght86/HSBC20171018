package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.bl.LPPolBL;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDiscountDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LJAPayPersonDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.finfee.FinFeePubFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
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
import com.sinosoft.lis.vschema.LJAPayPersonSet;
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
import com.sinosoft.utility.VData;

public class PEdorPMDetailBL {
private static Logger logger = Logger.getLogger(PEdorPMDetailBL.class);

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
	private LPDiscountSet mLPDiscountSet = new LPDiscountSet();
	
	//add by jiaqiangli 2009-02-07 update lccont.prem
	private double mChgPrem = 0.0;
	
	private int mPayCount;
	private Reflections mReflections = new Reflections();

	public PEdorPMDetailBL() {
	}
	
	public static void main(String[] args) {
		PEdorPMDetailBL tPEdorPMDetailBL = new PEdorPMDetailBL();
		VData tVData = new VData();
		String tOperate = new String();
		tPEdorPMDetailBL.submitData(tVData, tOperate);
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
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mLPPolSchema = (LPPolSchema) mInputData.getObjectByObjectName("LPPolSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			if (mGlobalInput == null || mLPEdorItemSchema == null) {
				return false;
			}
		} catch (Exception e) {
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

		return flag;

	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		LPPolSet insrtLPPolSet = new LPPolSet();
		LPPremSet insrtLPPremSet = new LPPremSet();
		LPGetSet insrtLPGetSet = new LPGetSet();
		LPDutySet insrtLPDutySet = new LPDutySet();
		
		boolean flag = true;

		// 准备LJSGetEndorse表同时处理mLPEdorItemSchema.setGetMoney
		double getMoney = 0;
		LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();

		double payPrem = 0.00;

		//add by jiaqiangli 2008-09-01 循环每一个polno
		//应该首先查询出polno来，明细保存的lpedoritem中的polno是000000
		LCPolDB tLCPolDB = new LCPolDB();
		//已签单的非趸交保单
//		LCPolSet tLCPolSet = tLCPolDB.executeQuery("select * from lcpol where contno = '"
//				+ mLPEdorItemSchema.getContNo()
//				//order by polno,mainpolno保证豁免险最后处理，因为要取被豁免的保额
//				+ "' and appflag ='1' and payintv > 0 and payintv <> 12 order by polno,mainpolno");
		//modify by jiaqiangli 2009-04-18 重新设置排序方式，确保豁免险最后进行联动处理
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql("select * from lcpol where contno = '"
				+ "?contno?"
				//order by polno,mainpolno保证豁免险最后处理，因为要取被豁免的保额
				+ "' and appflag ='1' and payintv > 0 and payintv <> 12 order by (case when exists(select 1 from lmriskapp where riskcode=lcpol.riskcode and risktype7 in ('1','2')) then 1 else 0 end) ");
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
		LCPolSet tLCPolSet = tLCPolDB.executeQuery(sqlbv);
				
		//附加豁免自动联动处理 此处需要排除豁免险
		//附加豁免险先重算后联动处理
		//and riskcode not in (select riskcode from lmriskapp where risktype7 in ('1','2'))
		logger.debug("tLCPolSet.size()" + tLCPolSet.size());
		for (int k = 1; k <= tLCPolSet.size(); k++) {
			insrtLPPolSet = new LPPolSet();
			insrtLPPremSet = new LPPremSet();
			insrtLPGetSet = new LPGetSet();
			insrtLPDutySet = new LPDutySet();
			
			logger.debug("before" + mLPEdorItemSchema.getPolNo());
			mLPEdorItemSchema.setPolNo(tLCPolSet.get(k).getPolNo());
			logger.debug("after" + mLPEdorItemSchema.getPolNo());

			LPPolBL tLPPolBL = new LPPolBL();
			LPPolSchema tLPPolSchema = new LPPolSchema();
			tLPPolBL.queryLPPol(mLPEdorItemSchema);
			tLPPolSchema = tLPPolBL.getSchema();
			//保存变更前的lcpol信息
			LCPolSchema tSourceLCPolSchema = new LCPolSchema();
			//进行赋值，此处不能用对象间的=赋值，因为是对象引用，都指向同一个堆地址
			mReflections.transFields(tSourceLCPolSchema, tLPPolSchema);

			tLPPolSchema.setPayIntv(mLPPolSchema.getPayIntv()); //只处理非年缴变年缴，不能是年缴变非年缴 此处相当于置年交12
			logger.debug("重算前的Prem:" + tLPPolSchema.getPrem());
			
			//豁免险处理
			if (judgeExempt(tLPPolSchema.getPolNo()) == true) {
				//保费变化
				tLPPolSchema.setAmnt(this.getExemptAMNT(mLPPolSet, tLPPolSchema.getRiskCode()));
				logger.debug("tLPPolSchema.amnt"+tLPPolSchema.getAmnt());
				
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
				//comment by jiaqiangli 主要是豁免联动需要处理这个条件
				tLCDutyBLSet.get(i).setAmnt(tLPPolSchema.getAmnt());
				tLCDutyBLSet.get(i).setPayIntv(tLPPolSchema.getPayIntv());
			}
			for (int i = 1; i <= tLCPremSet.size(); i++) {
				tLCPremSet.get(i).setPayIntv(tLPPolSchema.getPayIntv());
			}

			// 重算
			if (!tReCalBL.recalWithData(tLCPolBL, tLCDutyBLSet, tLCPremSet, tLCGetBLSet, mLPEdorItemSchema)) {
				this.mErrors.copyAllErrors(tReCalBL.mErrors);
				CError.buildErr(this, "保费重算失败");
				return false;
			}
			
			tLPPolSchema = tReCalBL.aftLPPolSet.get(1);
			logger.debug("重算后的Prem:" + tLPPolSchema.getPrem());
			
			insrtLPDutySet.add(tReCalBL.aftLPDutySet);
			insrtLPGetSet.add(tReCalBL.aftLPGetSet);
			
			// 重新计算加费金额
			AddPremReCalBQInterface tAddPremReCalBQInterface = new AddPremReCalBQInterface(mGlobalInput);
			LPPremSet afterLPPremSet = tAddPremReCalBQInterface.recalAddPrem(tLPPolSchema, tReCalBL.aftLPPremSet,tLCDutyBLSet);
			if (afterLPPremSet == null || afterLPPremSet.size() == 0) {
				CError.buildErr(this, "重新计算加费金额失败");
				return false;
			}
			
			tLPPolSchema.setPrem(tLPPolSchema.getPrem()+tAddPremReCalBQInterface.getAddFeeMinus());
			insrtLPPremSet.add(afterLPPremSet);			
			
			//变动保费 汇总lcpol的差值至lccont
			mChgPrem += tLPPolSchema.getPrem() - tSourceLCPolSchema.getPrem();

			tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPolSchema.setModifyDate(PubFun.getCurrentDate());
			tLPPolSchema.setModifyTime(PubFun.getCurrentTime());
			
			//paytodate如何置 cvalidate+N年（向上取整）
			int tIntvYears = 0;
			// add by jiaqiangli 2009-04-03 特殊处理预约生效的新增附加险
			if (tSourceLCPolSchema.getCValiDate().compareTo(tSourceLCPolSchema.getPaytoDate()) < 0) {
				//modify by jiaqiangli 2009-04-11 此处应该不会溢出
				tIntvYears = (int)Math.ceil(PubFun.calInterval(tSourceLCPolSchema
						.getCValiDate(), tSourceLCPolSchema.getPaytoDate(), "M") / 12.00);
				tLPPolSchema.setPaytoDate(FinFeePubFun.calOFDate(tSourceLCPolSchema.getCValiDate(), tIntvYears, "Y", tSourceLCPolSchema.getCValiDate()));
			}
			
			//修改lppol.paytodate
			insrtLPPolSet.add(tLPPolSchema.getSchema());
			
			//变更lcduty lcprem.paytodate
			for (int ii=1;ii<=insrtLPDutySet.size();ii++) {
				insrtLPDutySet.get(ii).setPaytoDate(tLPPolSchema.getPaytoDate());
				insrtLPDutySet.get(ii).setPrem(insrtLPDutySet.get(ii).getPrem()+tAddPremReCalBQInterface.getAddFeeMinus());
			}
			
			for (int ii=1;ii<=insrtLPPremSet.size();ii++) {
				insrtLPPremSet.get(ii).setPayIntv(tLPPolSchema.getPayIntv());
				insrtLPPremSet.get(ii).setPaytoDate(tLPPolSchema.getPaytoDate());
			}
			
			//补交保费
			payPrem = calPayPrem(tSourceLCPolSchema, tLPPolSchema.getPaytoDate());
			logger.debug("补交的保险费差额:" + payPrem);
			
			//lis6.0中催收数据是不会删除的??
			// add by jiaqiangli 2009-04-03 此处有可能是预约生效的新增附加险 做归0处理
			String tpaycountsql = "select (case when max(paycount) is not null then max(paycount) else 0 end) from LJAPayPerson where polno = '?polno?' "
					+ "and substr(trim(PayPlanCode),1,6) <> '000000' and paytype = 'ZC' ";
			ExeSQL zExeSQL = new ExeSQL();
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql(tpaycountsql);
			sbv.put("polno", mLPEdorItemSchema.getPolNo());
			String tPayCount = zExeSQL.getOneValue(sbv);

			if (tPayCount == null || tPayCount.equals("")) {
				CError.buildErr(this, "查询应收缴费表失败!");
				return false;
			}
            //当前缴费次数
			mPayCount = Integer.parseInt(tPayCount);

			//此处参照edortype = 'AA'
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
			//此处的paytintv应该置多少 是否应该lcpol.payintv还是12还是0
			tLJSPayPersonSchema.setPayIntv(tSourceLCPolSchema.getPayIntv());
			tLJSPayPersonSchema.setPayDate(mLPEdorItemSchema.getEdorValiDate());
			tLJSPayPersonSchema.setPayType(mLPEdorItemSchema.getEdorType());

			tLJSPayPersonSchema.setLastPayToDate(mLPEdorItemSchema.getEdorValiDate());
			tLJSPayPersonSchema.setCurPayToDate(tLPPolSchema.getPaytoDate());

			tLJSPayPersonSchema.setAgentCode(tLPPolSchema.getAgentCode());
			tLJSPayPersonSchema.setAgentGroup(tLPPolSchema.getAgentGroup());
			tLJSPayPersonSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
			
			//保费项的保存 SumActuPayMoney = SumDuePayMoney
			tLJSPayPersonSchema.setSumActuPayMoney(payPrem);
			tLJSPayPersonSchema.setSumDuePayMoney(payPrem);
			
			tLJSPayPersonSchema.setPayCount(mPayCount+1);
			tLJSPayPersonSchema.setMakeDate(PubFun.getCurrentDate());
			tLJSPayPersonSchema.setMakeTime(PubFun.getCurrentTime());
			tLJSPayPersonSchema.setModifyDate(PubFun.getCurrentDate());
			tLJSPayPersonSchema.setModifyTime(PubFun.getCurrentTime());
			tLJSPayPersonSchema.setBankAccNo("");
			tLJSPayPersonSchema.setBankCode("");
			tLJSPayPersonSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
			tLJSPayPersonSchema.setCurrency(tLPPolSchema.getCurrency());
			tLJSPayPersonSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
			
			//add by jiaqiangli 2009-04-07 预约新增附加险的金额为0的记录不产生
			if (tLJSPayPersonSchema.getSumDuePayMoney() > 0) {
				mLJSPayPersonSet.add(tLJSPayPersonSchema);

				// 添加其他信息
				// 添加保费LJSPayPerson和LJSGetEndorse
				if (!createLJSGetEndorseSchema(tLJSPayPersonSchema)) {
					mErrors.addOneError("添加保费LJSPayPerson和LJSGetEndorse!");
					return false;
				}
			}
			
			//add by jiaqiangli 2008-09-01 循环每一个polno
			mLPPolSet.add(insrtLPPolSet);
			mLPDutySet.add(insrtLPDutySet);
			mLPPremSet.add(insrtLPPremSet);
			mLPGetSet.add(insrtLPGetSet);
			
			//********************2011-1-24*********************************
			//************增加折扣处理 start
			LCDiscountSet tLCDiscountSet = new LCDiscountSet();
			LCDiscountDB tLCDiscountDB = new LCDiscountDB();
			tLCDiscountDB.setPolNo(tLPPolSchema.getPolNo());
			tLCDiscountSet = tLCDiscountDB.query();
			if(tLCDiscountSet!=null && tLCDiscountSet.size()>0)
			{
				//得到折扣
				for(int i=1;i<=tLCDiscountSet.size();i++)
				{
					LPDiscountSchema tLPDiscountSchema = new LPDiscountSchema();
					mReflections.transFields(tLPDiscountSchema, tLCDiscountSet.get(i));
					tLPDiscountSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPDiscountSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					mLPDiscountSet.add(tLPDiscountSchema);
				}
				
				String sql = "select * from LJAPayPerson where polno = '?polno?' "
					+ "and paycount='?mPayCount?' and paytype in (select code from ldcode where codetype='discounttype')";
				SQLwithBindVariables sbv1=new SQLwithBindVariables();
				sbv1.sql(sql);
				sbv1.put("polno", mLPEdorItemSchema.getPolNo());
				sbv1.put("mPayCount", mPayCount);
				LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
				LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();				
				tLJAPayPersonSet = tLJAPayPersonDB.executeQuery(sbv1);
				for(int j=1;j<=tLJAPayPersonSet.size();j++)
				{
					LJSPayPersonSchema zkLJSPayPersonSchema = new LJSPayPersonSchema();
					//补交保费
					tSourceLCPolSchema.setPrem(tLJAPayPersonSet.get(j).getSumActuPayMoney());
					payPrem = calPayPrem(tSourceLCPolSchema, tLPPolSchema.getPaytoDate());
					logger.debug("补交的保险费折扣差额:" + payPrem);
					
					zkLJSPayPersonSchema = PreperaLjspayperson(tLPPolSchema,tLJAPayPersonSet.get(j),payPrem);
					// 添加保费LJSPayPerson和LJSGetEndorse
			          //营改增 add zhangyingfeng 2016-07-14
			          //价税分离 计算器
			          TaxCalculator.calBySchema(tLJSPayPersonSchema);
			          //end zhangyingfeng 2016-07-14
					mLJSPayPersonSet.add(zkLJSPayPersonSchema);
					if (!createLJSGetEndorseSchemaZK(zkLJSPayPersonSchema)) {
						mErrors.addOneError("添加保费LJSPayPerson和LJSGetEndorse!");
						return false;
					}
				}											
			}		
			//************增加折扣处理 end			
		}

		mMap.put(mLPPolSet, "DELETE&INSERT");
		mMap.put(mLPDutySet, "DELETE&INSERT");
		mMap.put(mLPPremSet, "DELETE&INSERT");
		mMap.put(mLPGetSet, "DELETE&INSERT");		
		mMap.put(mLPDiscountSet, "DELETE&INSERT");
		
		//add by jiaqiangli 2008-09-01 循环每一个polno
		//方便此处mLPEdorItemSchema.setGetMoney(getMoney);
		logger.debug("aLJSGetEndorseSet.size()"+aLJSGetEndorseSet.size());
		logger.debug("mLJSPayPersonSet.size()"+mLJSPayPersonSet.size());
		for (int i = 1; i <= aLJSGetEndorseSet.size(); i++) {
			LJSGetEndorseSchema tLJS = new LJSGetEndorseSchema();
			tLJS.setSchema(aLJSGetEndorseSet.get(i));
			getMoney += tLJS.getGetMoney();
	          //营改增 add zhangyingfeng 2016-07-14
	          //价税分离 计算器
	          TaxCalculator.calBySchema(tLJS);
	          //end zhangyingfeng 2016-07-14
			tLJSGetEndorseSet.add(tLJS);
		}
		
		//不一定需要补费 非跨两个保单年度之间
		if (tLJSGetEndorseSet.size() >= 1) 
			mMap.put(tLJSGetEndorseSet, "DELETE&INSERT");
		if (mLJSPayPersonSet.size() >= 1) 
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

		//循环处理的时候lpedoritem.polno置成了lcpol.polno
		//现在重置这个字段
		mLPEdorItemSchema.setPolNo("000000");

		//mLPEdorItemSchema相当于5.3系统中lpedormain表
		//但是6.0中的lpedormain表是在申请确认中处理的
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
	
	private LJSPayPersonSchema PreperaLjspayperson(LPPolSchema tLPPolSchema,LJAPayPersonSchema tLJAPayPersonSchema,double tZK) 
	{		
		LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
		tLJSPayPersonSchema.setPolNo(tLJAPayPersonSchema.getPolNo());
		tLJSPayPersonSchema.setGrpContNo(tLJAPayPersonSchema.getGrpContNo());
		tLJSPayPersonSchema.setGrpPolNo(tLJAPayPersonSchema.getGrpPolNo());
		tLJSPayPersonSchema.setGrpContNo(tLJAPayPersonSchema.getGrpContNo());
		tLJSPayPersonSchema.setGrpPolNo(tLJAPayPersonSchema.getGrpPolNo());
		tLJSPayPersonSchema.setContNo(tLJAPayPersonSchema.getContNo());
		tLJSPayPersonSchema.setManageCom(tLJAPayPersonSchema.getManageCom());
		tLJSPayPersonSchema.setOperator(mGlobalInput.Operator);
		tLJSPayPersonSchema.setRiskCode(tLJAPayPersonSchema.getRiskCode());
		tLJSPayPersonSchema.setAppntNo(tLJAPayPersonSchema.getAppntNo());
		tLJSPayPersonSchema.setPayAimClass("1");
		tLJSPayPersonSchema.setDutyCode(tLJAPayPersonSchema.getDutyCode());
		tLJSPayPersonSchema.setPayPlanCode(tLJAPayPersonSchema.getPayPlanCode());
		//此处的paytintv应该置多少 是否应该lcpol.payintv还是12还是0
		tLJSPayPersonSchema.setPayIntv(tLJAPayPersonSchema.getPayIntv());
		tLJSPayPersonSchema.setPayDate(mLPEdorItemSchema.getEdorValiDate());
		tLJSPayPersonSchema.setPayType(tLJAPayPersonSchema.getPayType());

		tLJSPayPersonSchema.setLastPayToDate(mLPEdorItemSchema.getEdorValiDate());
		tLJSPayPersonSchema.setCurPayToDate(tLPPolSchema.getPaytoDate());

		tLJSPayPersonSchema.setAgentCode(tLJAPayPersonSchema.getAgentCode());
		tLJSPayPersonSchema.setAgentGroup(tLJAPayPersonSchema.getAgentGroup());
		tLJSPayPersonSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
		
		//保费项的保存 SumActuPayMoney = SumDuePayMoney
		tLJSPayPersonSchema.setSumActuPayMoney(tZK);
		tLJSPayPersonSchema.setSumDuePayMoney(tZK);
		
		tLJSPayPersonSchema.setPayCount(mPayCount+1);
		tLJSPayPersonSchema.setMakeDate(PubFun.getCurrentDate());
		tLJSPayPersonSchema.setMakeTime(PubFun.getCurrentTime());
		tLJSPayPersonSchema.setModifyDate(PubFun.getCurrentDate());
		tLJSPayPersonSchema.setModifyTime(PubFun.getCurrentTime());
		tLJSPayPersonSchema.setBankAccNo("");
		tLJSPayPersonSchema.setBankCode("");
		tLJSPayPersonSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());	
		tLJSPayPersonSchema.setCurrency(tLJAPayPersonSchema.getCurrency());

		return tLJSPayPersonSchema;
	}
	
	private boolean judgeExempt(String tPolNo) {
		//add by jiaqiangli 附加豁免联动处理 2008-12-22
		LCPolDB tExemptLCPolDB = new LCPolDB();
		LCPolSet tExemptLCPolSet = new LCPolSet();
		//判断是否有附加豁免关联
		//更简洁的判断只需要第一个条件即可
		String tExemptSQL = "select * from lcpol a where a.appflag='1' and  polno = '?tPolNo?' and contno = '?contno?' and riskcode in (select riskcode from lmriskapp where risktype7 in ('1','2')) "
				// exists 作关联的判断逻辑
				+ "and payintv > 0 and exists (select 1 from lcpol b,ldcode1 c where contno = '?contno?' and c.code=a.riskcode and b.riskcode=c.code1)";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tExemptSQL);
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
		sqlbv.put("tPolNo", tPolNo);
		tExemptLCPolSet = tExemptLCPolDB.executeQuery(sqlbv);
		logger.debug("tExemptLCPolSet.size()"+tExemptLCPolSet.size());
		if (tExemptLCPolSet.size() == 1)
			return true;
		else
			return false;		
	}
	
	private double getExemptAMNT(LPPolSet insrtLPPolSet,String tRiskCode) {
		//累加豁免险保费
		double tAllExemptPrem = 0.00;
		//豁免累加
		String tExemptSQL = "select polno from lcpol where appflag='1' and contno = '?contno?' and payintv > 0 and riskcode in (select code1 from ldcode1 where codetype = 'freerisk' and code='?code?')";
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tExemptSQL);
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
		sqlbv.put("code", tRiskCode);
		tSSRS = tExeSQL.execSQL(sqlbv);
		//查询重算后的保费
		for (int i=1;i<=tSSRS.getMaxRow();i++) {
			for (int j=1;j<=insrtLPPolSet.size();j++) {
				if (tSSRS.GetText(i, 1).equals(insrtLPPolSet.get(j).getPolNo())) {
					//取prem而非standprem
					tAllExemptPrem += insrtLPPolSet.get(j).getPrem();
				}
			}
		}
		return tAllExemptPrem;
	}
	
	//参数 变更前lcpol
	//参数 变更后lcpol.PaytoDate
	public double calPayPrem(LCPolSchema xLCPolSchema, String yPaytoDate) {
		double banlancePrem = 0.0000;
		// 先计算出变更后的交至日期和上一次的交至日期的月差
		int interval = PubFun.calInterval(xLCPolSchema.getPaytoDate(),yPaytoDate, "M");
		logger.debug("interval:" + interval);
		// 根据月差计算原交费间隔的倍数，用来补齐
		// 补交的保费
		interval = interval / xLCPolSchema.getPayIntv();
		banlancePrem = xLCPolSchema.getPrem() * interval;

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
		mLJSGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema
				.getEdorType());
		// 从描述表中获取财务接口类型，modify by Minim at 2003-12-23
		String finType = BqCalBL.getFinType(mLPEdorItemSchema.getEdorType(),
				strfinType, mLPEdorItemSchema.getPolNo());
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
		//0为补费1为退费
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
	//添加保费至aLJSGetEndorseSet
	private boolean createLJSGetEndorseSchema(
			LJSPayPersonSchema cLJSPayPersonSchema) {
		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		tLJSGetEndorseSchema = this.initLJSGetEndorse("BF");
		mReflections.transFields(tLJSGetEndorseSchema, cLJSPayPersonSchema);
		tLJSGetEndorseSchema.setGetMoney(cLJSPayPersonSchema
				.getSumDuePayMoney());

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
			if ("01".equals(tLCPremDB.getPayPlanType())
					|| "03".equals(tLCPremDB.getPayPlanType())) {
				tLJSGetEndorseSchema
						.setSubFeeOperationType(BqCode.Pay_InsurAddPremHealth
								+ String.valueOf(cLJSPayPersonSchema
										.getPayCount()));
			} else if ("02".equals(tLCPremDB.getPayPlanType())
					|| "04".equals(tLCPremDB.getPayPlanType())) {
				tLJSGetEndorseSchema
						.setSubFeeOperationType(BqCode.Pay_InsurAddPremOccupation
								+ String.valueOf(cLJSPayPersonSchema
										.getPayCount()));
			} else {
				CError.buildErr(this, "加费交费计划类型错误!");
				return false;
			}
		} else {
			tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_Prem
					+ String.valueOf(cLJSPayPersonSchema.getPayCount()));
		}
		aLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		logger.debug("aLJSGetEndorseSet.size()"+aLJSGetEndorseSet.size());
		return true;
	}
	
	/**
	 * 由LJSPayPerson信息生成当期保费的折扣批改补退费信息
	 * 
	 * @param cLJSPayPersonSchema
	 * @return
	 */
	//添加保费至aLJSGetEndorseSet
	private boolean createLJSGetEndorseSchemaZK(
			LJSPayPersonSchema cLJSPayPersonSchema) {
			LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
			tLJSGetEndorseSchema = this.initLJSGetEndorse("ZK");
			mReflections.transFields(tLJSGetEndorseSchema, cLJSPayPersonSchema);
			tLJSGetEndorseSchema.setGetMoney(cLJSPayPersonSchema
					.getSumDuePayMoney());

			// 处理SubFeeOperationType			
			tLJSGetEndorseSchema.setSubFeeOperationType(cLJSPayPersonSchema.getPayType()
					+ String.valueOf(cLJSPayPersonSchema.getPayCount()));
			aLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		
		logger.debug("aLJSGetEndorseSet.size()"+aLJSGetEndorseSet.size());
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
