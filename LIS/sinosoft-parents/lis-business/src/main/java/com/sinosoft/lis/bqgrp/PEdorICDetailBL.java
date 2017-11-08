package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.bq.AddPremCalBL;
import com.sinosoft.lis.bq.BqCalBL;
import com.sinosoft.lis.bq.BqCode;
import com.sinosoft.lis.bq.EdorDetail;
import com.sinosoft.lis.bq.ReCalBL;
import com.sinosoft.lis.bq.UpdateEdorState;
import com.sinosoft.utility.*;

import java.util.Date;
import java.text.DecimalFormat;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vbl.LCPremBLSet;
import com.sinosoft.lis.vbl.LCDutyBLSet;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 个单/团单下个单被保人重要资料变更项目明细</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Tjj
 * @ReWrite ZhangRong,Alex,sunsx
 * @version 1.0
 */
public class PEdorICDetailBL implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorICDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();


	/** 往后面传输数据的容器 */
	private VData mInputData;


	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();


	/** 数据操作字符串 */
	private String mOperate;
	/**新保险法算法标记*/
	private boolean isNewCal = false;
	/**职业类别改变标记*/
	private boolean isIOChg = false;


	/**  */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
	private LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();
	private LPInsuredSchema mLPInsuredSchema = new LPInsuredSchema();
	private LPInsuredSet mLPInsuredSet = new LPInsuredSet();
	private LPAppntSet mLPAppntSet = new LPAppntSet();
	private LPPersonSet mLPPersonSet = new LPPersonSet();
	private LPPremSet mLPPremSet = new LPPremSet();
	private LPGetSet mLPGetSet = new LPGetSet();
	private LPDutySet mLPDutySet = new LPDutySet();
	private LPPolSet mLPPolSet = new LPPolSet();
	private LPContSet mLPContSet = new LPContSet();
	private LPEngBonusPolSet mLPEngBonusPolSet = new LPEngBonusPolSet();
	private double mGetMoney = 0.0;
	private double mGetInterest = 0.0;
	private double mChgPrem = 0.0;
	private double mChgPremend = 0.0;
	private double mChgAmnt = 0.0;
	private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();
	private BqCalBL mBqCalBL = new BqCalBL();
	private Reflections mRef = new Reflections();


	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public PEdorICDetailBL() {
	}


	/**
	 * 传输数据的公共方法
	 * @param: cInputData 输入的数据
	 *         cOperate 数据操作
	 * @return:
	 */
	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate() {
		return this.mOperate;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		//将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);

		//得到外部传入的数据,将数据备份到本类中
		getInputData(cInputData);
		logger.debug("EdorTypeCal========" +
				mLPEdorItemSchema.getEdorTypeCal());
		//数据查询业务处理(queryData())
		if (cOperate.equals("QUERY||MAIN") || cOperate.equals("QUERY||DETAIL")) {
			if (!queryData()) {
				return false;
			} else {
				return true;
			}
		}
		

		//数据校验操作（checkdata)
		if (!checkData()) {
			return false;
		}
		// 数据操作业务处理(新增insertData();修改updateData();删除deletedata())
		if (!dealData()) {
			return false;
		}
		if (!prepareOutputData()) {
			return false;
		}
		if (!(cOperate != null && cOperate.equals("INSERT||CM"))) { //如果是客户资料变更CM调用IC时，不提交数据库
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(mResult, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "PEdorICDetailBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		mResult.clear();
		mResult.add(String.valueOf(mChgPremend));
		return true;
	}

	public boolean getSubmitData(VData cInputData, String cOperate) { //不含数据提交的数据处理
		//将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);

		//得到外部传入的数据,将数据备份到本类中
		getInputData(cInputData);

		//数据查询业务处理(queryData())
		if (cOperate.equals("QUERY||MAIN") || cOperate.equals("QUERY||DETAIL")) {
			logger.debug("=============================00========" +
					mLPEdorItemSchema.getEdorTypeCal());
			if (!queryData()) {
				return false;
			} else {
				return true;
			}
		}
		logger.debug("==================================01===" + mLPEdorItemSchema.getEdorTypeCal());
		//数据校验操作（checkdata)
		// 数据操作业务处理(新增insertData();修改updateData();删除deletedata())
		if (!dealData()) {
			return false;
		}
		logger.debug("=============================02=======" + mLPEdorItemSchema.getEdorTypeCal());
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("=============================03========" + mLPEdorItemSchema.getEdorTypeCal());
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}


	/**数据查询业务处理(queryData())
	 *
	 */
	private boolean queryData() {
		if (this.getOperate().equals("QUERY||MAIN")) {
			LPInsuredBL tLPInsuredBL = new LPInsuredBL();
			LPInsuredSet tLPInsuredSet = new LPInsuredSet();
			tLPInsuredBL.setSchema(mLPInsuredSchema);

			tLPInsuredSet = tLPInsuredBL.queryAllLPInsured(mLPEdorItemSchema);
			String tReturn;
			tReturn = tLPInsuredSet.encode();
			tReturn = "0|" + tLPInsuredSet.size() + "^" + tReturn;

			mInputData.clear();
			mInputData.add(tLPInsuredSet);

			mResult.clear();
			mResult.add(tReturn);
		} else if (this.getOperate().equals("QUERY||DETAIL")) {
			LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
			LPInsuredBL tLPInsuredBL = new LPInsuredBL();
			if (!tLPInsuredBL.queryLPInsured(mLPInsuredSchema)) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorICDetailBL";
				tError.functionName = "QueryData";
				tError.errorMessage = "明细查询失败!";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				tLPInsuredSchema = tLPInsuredBL.getSchema();
			}

			String tReturn = tLPInsuredSchema.encode();
			mInputData.clear();
			mInputData.add(tLPInsuredSchema);

			mResult.clear();
			mResult.add(tReturn);
		}
		return true;
	}


	/**
	 * 从输入数据中得到所有对象
	 *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mLPInsuredSchema = (LPInsuredSchema) cInputData.getObjectByObjectName("LPInsuredSchema", 0);
		mLPEdorItemSchema = (LPEdorItemSchema) cInputData.getObjectByObjectName("LPEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
		if (mLPInsuredSchema == null || mLPEdorItemSchema == null || mGlobalInput == null)
		{
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		return true;
	}


	/**
	 * 校验传入的数据的合法性
	 * 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		boolean flag = true;
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(mLPEdorItemSchema);
		if (this.getOperate().equals("INSERT||MAIN")) {
			LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
			if (tLPEdorItemSet == null || tLPEdorItemSet.size() <= 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorICDetailBL";
				tError.functionName = "Preparedata";
				tError.errorMessage = "无保全申请数据!";
				this.mErrors.addOneError(tError);
				return false;
			}

			//获取保全主表数据，节省查询次数
			mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1).getSchema());

			if (mLPEdorItemSchema.getEdorState().trim().equals("2")) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorICDetailBL";
				tError.functionName = "Preparedata";
				tError.errorMessage = "该保全已经申请确认不能修改!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}


		if(!mLPInsuredSchema.getOccupationCode().equals(""))
		{
			String tSql="select * from LDOccupation where worktype = 'GR' and occupationcode='"+mLPInsuredSchema.getOccupationCode()+"'";
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(tSql);
			if (tSSRS == null || tSSRS.MaxRow==0)
			{
				CError tError = new CError();
				tError.errorMessage = "被保险人职业代码输入不符合数据库规定的取值范围，请双击输入框选择！";
				mErrors.addOneError(tError);
				return false;
			}
		}
		return flag;
	}


	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		//准备个人批改主表的信息
		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();

		//查询主险保单数据
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setContNo(mLPEdorItemSchema.getContNo());
		//tLCPolDB.setAppFlag("1"); //只查询有效险种
		//tLCPolDB.setPolState("1");
		LCPolSet tLCPolSet = tLCPolDB.query(); //从C表获得该合同下该被保人的所有险种保单信息
		if (tLCPolSet == null || tLCPolSet.size() <= 0) {
			mErrors.copyAllErrors(tLCPolDB.mErrors);
			mErrors.addOneError(new CError("查询个人险种保单失败或该被保人下已没有有效险种！"));
			return false;
		}

		mLPEdorItemSchema.setEdorState("3"); //设置状态为申请中（明细录入中）
		mLPEdorItemSchema.setOperator(mGlobalInput.Operator);
		mLPEdorItemSchema.setMakeDate(PubFun.getCurrentDate());
		mLPEdorItemSchema.setMakeTime(PubFun.getCurrentTime());
		mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());

		LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
		Reflections tReflections = new Reflections();
		tReflections.transFields(tLPEdorMainSchema, mLPEdorItemSchema);
		tLPEdorMainSchema.setChgAmnt(0);
		tLPEdorMainSchema.setChgPrem(0);
		tLPEdorMainSchema.setGetMoney(0);
		tLPEdorMainSchema.setGetInterest(0);
		tLPEdorMainSchema.setMakeDate(PubFun.getCurrentDate());
		tLPEdorMainSchema.setMakeTime(PubFun.getCurrentTime());
		mLPEdorMainSet.add(tLPEdorMainSchema);



		//准备cOperate
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(mLPInsuredSchema.getContNo());
		tLCInsuredDB.setInsuredNo(mLPInsuredSchema.getInsuredNo());
		if (!tLCInsuredDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorICDetailBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "被保人资料查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		boolean tReCal = false;
		tReflections.transFields(tLPInsuredSchema, tLCInsuredDB.getSchema());
		logger.debug("tLPInsuredSchema.getBirthday()" + tLPInsuredSchema.getBirthday());
		logger.debug("mLPInsuredSchema.getBirthday()" + mLPInsuredSchema.getBirthday());
		logger.debug("mLPInsuredSchema.getOccupationType()" + mLPInsuredSchema.getOccupationType());
		
		String tGrpContNo = tLPInsuredSchema.getGrpContNo();
		
		String tSQL = "select sysvarvalue  from ldsysvar where sysvar='CTVersionDate'";
		ExeSQL sExeSQL = new ExeSQL();
		String tCTVersionDate = sExeSQL.getOneValue(tSQL);
		if(tCTVersionDate == null || tCTVersionDate.equals("")){
			CError.buildErr(this, "算法日期分枝点查询失败！");
			return false; 
		}
		
		tSQL = "select nvl(polapplydate,cvalidate) from lcgrpcont where grpcontno = '"+tGrpContNo+"'";
		String tGrpApplyDate = sExeSQL.getOneValue(tSQL);
		int newIntv = PubFun.calInterval(tGrpApplyDate, tCTVersionDate, "D");//新保险法退保算法,以团单申请日为基准
		if(newIntv <=0){
			isNewCal = true;//采用新算法 
		}
		
		
		if (!StrTool.compareString(tLPInsuredSchema.getSex(),mLPInsuredSchema.getSex()) || !StrTool.compareString(tLPInsuredSchema.getBirthday(), mLPInsuredSchema.getBirthday()) || !StrTool.compareString(tLPInsuredSchema.getOccupationType(), mLPInsuredSchema.getOccupationType()))
		{ //如果性别,年龄,职业改变则重算保额保费
			tReCal = true;
			if(!StrTool.compareString(tLPInsuredSchema.getOccupationType(), mLPInsuredSchema.getOccupationType())){
				//职业类别变化收取未满期保险费或退还未满期静保险费，MS新保险法规定。
				isIOChg = true;
			}
		}

		//准备保单的客户相关信息
		LPPolSchema tLPPolSchema = new LPPolSchema();
		LPContSchema tLPContSchema = new LPContSchema();
		LPPolBL tLPPolBL = new LPPolBL();
		LPContBL tLPContBL = new LPContBL();
		LPEdorItemSchema tLPEdorItemSchema = null;
		for (int i = 1; i <= tLCPolSet.size(); i++) {
			ExeSQL tExeSQL = new ExeSQL();
			String tStr = tExeSQL.getOneValue("select riskcode  from LMRiskEdorItem where riskcode='" + tLCPolSet.get(i).getRiskCode() + "' and edorcode='" + mLPEdorItemSchema.getEdorType() + "'");
			if (tStr == null || tStr.equals("")) {
				continue; //对于没有IC保全项目的险种要跳过
			}
			String tReCalRisk=tExeSQL.getOneValue("select risktype6 from lmriskapp where riskcode='"+
					tStr+"'");
			tLPEdorItemSchema = mLPEdorItemSchema.getSchema();
			tLPEdorItemSchema.setPolNo(tLCPolSet.get(i).getPolNo());

			tLPPolBL.queryOtherLPPol(tLPEdorItemSchema);
			tLPPolSchema = tLPPolBL.getSchema();
			tLPPolSchema = tLPPolBL.getSchema();
			int tAppAge = PubFun.calInterval(mLPInsuredSchema.getBirthday(),tLCPolSet.get(i).getCValiDate(),"Y");
			tLPPolSchema.setInsuredSex(mLPInsuredSchema.getSex());
			tLPPolSchema.setInsuredBirthday(mLPInsuredSchema.getBirthday());
			tLPPolSchema.setInsuredName(mLPInsuredSchema.getName());
			tLPPolSchema.setInsuredAppAge(tAppAge);
			tLPPolSchema.setOccupationType(mLPInsuredSchema.getOccupationType());

			if (tReCal&&(tLCPolSet.get(i).getAppFlag().equals("1"))&&(tReCalRisk.equals("0")||tReCalRisk.equals("")))
			{ //如果性别,年龄,职业改变则重算保额保费
				if (!ReCalculate(tLPPolSchema, tLPEdorItemSchema))
				{ //重算保费、保额，并计算各期保费的补退费和利息。
					return false;
				}
			}
			mLPPolSet.add(tLPPolSchema);
		}

		tLPContBL.queryLPCont(tLPEdorItemSchema);
		tLPContSchema = tLPContBL.getSchema();
		if (mLPInsuredSchema.getInsuredNo().equals(tLPContSchema.getInsuredNo()))
		{ //如果是主被保人，则更改合同单中被保人信息
			tLPContSchema.setInsuredSex(mLPInsuredSchema.getSex());
			tLPContSchema.setInsuredBirthday(mLPInsuredSchema.getBirthday());
			tLPContSchema.setInsuredName(mLPInsuredSchema.getName());
			tLPContSchema.setInsuredIDType(mLPInsuredSchema.getIDType());
			tLPContSchema.setInsuredIDNo(mLPInsuredSchema.getIDNo());
		}
		tLPContSchema.setPrem(Double.parseDouble(new DecimalFormat("0.00").format(mChgPrem + tLPContSchema.getPrem())));
		tLPContSchema.setSumPrem(Double.parseDouble(new DecimalFormat("0.00").format(mChgPrem + tLPContSchema.getSumPrem())));
		tLPContSchema.setAmnt(Double.parseDouble(new DecimalFormat("0.00").format(mChgAmnt + tLPContSchema.getAmnt())));

		//保全暂定为不强制人工核保
		tLPContSchema.setForceUWFlag("0"); //1为强制人核
		tLPContSchema.setForceUWReason("0");

		mLPContSet.add(tLPContSchema);


		LPPersonBL tLPPersonBL = new LPPersonBL();
		LPPersonSchema tLPPersonSchema = new LPPersonSchema();
		tLPPersonSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
		tLPPersonSchema.setEdorType(tLPEdorItemSchema.getEdorType());
		tLPPersonSchema.setCustomerNo(tLPEdorItemSchema.getInsuredNo());
		if (!tLPPersonBL.queryLPPerson(tLPPersonSchema)) {
			mErrors.copyAllErrors(tLPPersonBL.mErrors);
			mErrors.addOneError(new CError("查询投保人失败！"));
			return false;
		}
		tLPPersonSchema = tLPPersonBL.getSchema();
		tLPPersonSchema.setName(mLPInsuredSchema.getName());
		tLPPersonSchema.setSex(mLPInsuredSchema.getSex());
		tLPPersonSchema.setBirthday(mLPInsuredSchema.getBirthday());
		tLPPersonSchema.setIDNo(mLPInsuredSchema.getIDNo());
		tLPPersonSchema.setIDType(mLPInsuredSchema.getIDType());
		tLPPersonSchema.setOccupationCode(mLPInsuredSchema.getOccupationCode());
		tLPPersonSchema.setOccupationType(mLPInsuredSchema.getOccupationType());
		mLPPersonSet.add(tLPPersonSchema);

		tLPEdorItemSchema.setGetMoney(Double.parseDouble(new DecimalFormat("0.00").format(mGetMoney)));
		tLPEdorItemSchema.setChgPrem(Double.parseDouble(new DecimalFormat("0.00").format(mChgPrem)));
		tLPEdorItemSchema.setChgAmnt(Double.parseDouble(new DecimalFormat("0.00").format(mChgAmnt)));

		mLPEdorItemSet = new LPEdorItemSet();

		for (int i = 1; i <= mLPPolSet.size(); i++)
		{
			mLPEdorItemSchema.setInsuredNo(mLPInsuredSchema.getInsuredNo());
			mLPEdorItemSchema.setPolNo(mLPPolSet.get(i).getPolNo());
			mLPEdorItemSchema.setGrpContNo(mLPPolSet.get(1).getGrpContNo());
			mLPEdorItemSchema.setEdorState("1");
			mLPEdorItemSchema.setUWFlag("0");
			mLPEdorItemSchema.setGetMoney(mGetMoney);
			mLPEdorItemSchema.setGetInterest(mGetInterest);
			LCPolDB aLCPolDB = new LCPolDB();
			aLCPolDB.setPolNo(mLPPolSet.get(i).getPolNo());
			double tPrem = aLCPolDB.query().get(1).getPrem();
			mLPEdorItemSchema.setChgPrem(mLPPolSet.get(i).getPrem() - tPrem);
			mLPEdorItemSchema.setChgAmnt(0.00);
			mLPEdorItemSchema.setOperator(mGlobalInput.Operator);
			//mLPEdorItemSchema.setManageCom(mGlobalInput.ManageCom);
			mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
			mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
			LPEdorItemSchema aLPEdorItemSchema = new LPEdorItemSchema();
			mRef.transFields(aLPEdorItemSchema, mLPEdorItemSchema);
			mLPEdorItemSet.add(aLPEdorItemSchema);
		}

		tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsuredSchema.setName(mLPInsuredSchema.getName());
		tLPInsuredSchema.setSex(mLPInsuredSchema.getSex());
		tLPInsuredSchema.setBirthday(mLPInsuredSchema.getBirthday());
		tLPInsuredSchema.setIDType(mLPInsuredSchema.getIDType());
		tLPInsuredSchema.setIDNo(mLPInsuredSchema.getIDNo());
		tLPInsuredSchema.setOccupationCode(mLPInsuredSchema.getOccupationCode());
		tLPInsuredSchema.setOccupationType(mLPInsuredSchema.getOccupationType());
		tLPInsuredSchema.setSocialInsuFlag(mLPInsuredSchema.getSocialInsuFlag());
		tLPInsuredSchema.setOperator(mGlobalInput.Operator);
		tLPInsuredSchema.setModifyDate(PubFun.getCurrentDate());
		tLPInsuredSchema.setModifyTime(PubFun.getCurrentTime());
		mLPInsuredSchema.setSchema(tLPInsuredSchema);

		mLPInsuredSet.add(mLPInsuredSchema);

		if (!this.getOperate().equals("INSERT||GRPMAIN")) {
			LPInsuredDB tLPInsuredDB = new LPInsuredDB();
			tLPInsuredDB.setSchema(mLPInsuredSchema);
			this.setOperate("INSERT||MAIN");
		}

		return true;
	}

	private boolean ReCalculate(LPPolSchema aLPPolSchema, LPEdorItemSchema aLPEdorItemSchema)
	{
		FDate tD = new FDate();
		ExeSQL tExeSQL = new ExeSQL();

		// 校验被保人投保年龄
		int tInsuredAge = PubFun.calInterval(aLPPolSchema.getInsuredBirthday(),aLPPolSchema.getCValiDate(),"Y");
		int tInsuredYear = PubFun.calInterval2(aLPPolSchema.getCValiDate(),aLPEdorItemSchema.getEdorValiDate(),"Y");

		LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
		tLMRiskAppDB.setRiskCode(aLPPolSchema.getRiskCode());
		if (!tLMRiskAppDB.getInfo())
		{
			CError.buildErr(this, "查询险种承保定义表失败！");
			return false;
		}

		if ((tInsuredAge < tLMRiskAppDB.getMinInsuredAge()) || ((tInsuredAge > tLMRiskAppDB.getMaxInsuredAge()) && (tLMRiskAppDB.getMaxInsuredAge() > 0))) 
		{ 
			//如果变更后投保年龄在承保范围之外
			if (tInsuredYear <= 2)
			{ 
				// 如果承保两年内
				CError.buildErr(this, "保单" + aLPPolSchema.getContNo() + "下险种" + aLPPolSchema.getRiskCode() + " 要件变更后被保人投保年龄和当前年龄均不符合承保年龄要求，且投保期限在两年内，请对保单做解约处理！");
				return false;
			}
		}

		String tStr = tExeSQL.getOneValue("select a.GetStartDate from LCGet a inner join LMDutyGet b on a.GetDutyCode = b.GetDutyCode where b.GetType2 = '1' and PolNo = '" + aLPPolSchema.getPolNo() + "'");
		String tStr1 = tExeSQL.getOneValue("select getnoticeno from Ljsgetdraw where PolNo = '" + aLPPolSchema.getPolNo() + "'");
		if ((tD.getDate(tStr) != null && tD.getDate(tStr).before(tD.getDate(aLPEdorItemSchema.getEdorValiDate())))&&tStr1!="") {
			logger.debug("养老金领取期后不进行保费、保额重算和补退费");
			//对于进入养老金领取期后不进行保费、保额重算和补退费。
			return true;
		}

		ReCalBL tReCalBL = new ReCalBL(aLPPolSchema, aLPEdorItemSchema);

		LCPolSchema aLCPolSchema = new LCPolSchema();
		mRef.transFields(aLCPolSchema, aLPPolSchema);
		LCPolSchema cLCPolSchema = new LCPolSchema();
		cLCPolSchema.setSchema(aLCPolSchema);
		LCPolBL cLCPolBL = new LCPolBL();

		LCDutySet cLCDutySet = new LCDutySet();
		LCDutyBLSet cLCDutyBLSet = new LCDutyBLSet();
		LCDutyDB cLCDutyDB = new LCDutyDB();
		cLCDutyDB.setPolNo(aLPPolSchema.getPolNo());
		cLCDutySet = cLCDutyDB.query();
		for (int i = 1; i <= cLCDutySet.size(); i++) {
			//长险险种保费不变保额变 
			String tCalRule=cLCDutySet.get(i).getCalRule(); 
			//区分长险和短险
			if (tCalRule != null && !tCalRule.equals("") && !tCalRule.equals("3")&&!tCalRule.equals("1")  )
			{
				cLCDutySet.get(i).setPrem(0.00);
				cLCPolSchema.setPrem(0.00);
			}
			cLCDutyBLSet.add(cLCDutySet.get(i));
		}
		cLCPolBL.setSchema(cLCPolSchema);
		tReCalBL.preLCPolSchema.setSchema(cLCPolSchema);
		tReCalBL.preLCDutySet.add(cLCDutyBLSet);

		LCPremBLSet cLCPremBLSet = new LCPremBLSet();
		cLCPremBLSet.clear();
		LCPremSet cLCPremSet = new LCPremSet();

		LCPremDB cLCPremDB = new LCPremDB();
		cLCPremDB.setPolNo(aLPPolSchema.getPolNo());
		cLCPremSet = cLCPremDB.query();
		cLCPremBLSet.add(cLCPremSet);
		tReCalBL.preLCPremSet.clear();
		tReCalBL.preLCPremSet.add(cLCPremSet);

		LCGetBLSet cLCGetBLSet = new LCGetBLSet();
		LCGetDB tLCGetDB = new LCGetDB();
		LCGetSet tLCGetSet = new LCGetSet();
		tLCGetDB.setPolNo(aLPPolSchema.getPolNo());
		tLCGetSet = tLCGetDB.query();
		cLCGetBLSet.clear();
		cLCGetBLSet.add(tLCGetSet);
		tReCalBL.preLCGetSet.clear();
		tReCalBL.preLCGetSet.add(tLCGetSet);
		
		//重算开始

		if (!tReCalBL.recalWithData(cLCPolBL,cLCDutyBLSet, cLCPremBLSet, cLCGetBLSet, mLPEdorItemSchema))
		{
			CError tError = new CError();
			tError.moduleName = "PEdorPTDetailBL";
			tError.functionName = "dealData";
			tError.errorMessage = "变更的信息不符合投保条件!";
			this.mErrors.addOneError(tError);
			return false;
		}

		aLPPolSchema.setSchema(tReCalBL.aftLPPolSet.get(1));
		mLPDutySet.add(tReCalBL.aftLPDutySet);
		mLPPremSet.add(tReCalBL.aftLPPremSet);
		mLPGetSet.add(tReCalBL.aftLPGetSet);

		Reflections tReflections = new Reflections();
		LCPolSchema tLCPolSchema = new LCPolSchema();
		LCDutySchema tLCDutySchema = new LCDutySchema();
		LCPremSchema tLCPremSchema = new LCPremSchema();
		tReflections.transFields(tLCPolSchema, aLPPolSchema);
		AddPremCalBL tAddPremCalBL = new AddPremCalBL();
		for (int i = 1; i <= mLPDutySet.size(); i++) {
			tReflections.transFields(tLCDutySchema, mLPDutySet.get(i).getSchema());
			for (int j = 1; j <= mLPPremSet.size(); j++) {
				if (mLPPremSet.get(j).getDutyCode() != null
					&& mLPPremSet.get(j).getDutyCode().equals(mLPDutySet.get(i).getDutyCode())
					&& mLPPremSet.get(j).getPayPlanType() != null &&
					  !mLPPremSet.get(j).getPayPlanType().equals("0")) {
					
					LMDutyPayAddFeeSchema tLMDutyPayAddFeeSchema = new LMDutyPayAddFeeSchema();
					tLMDutyPayAddFeeSchema.setAddFeeObject(mLPPremSet.get(j).getAddFeeDirect());
					if (tLMDutyPayAddFeeSchema.getAddFeeObject() == null || (!tLMDutyPayAddFeeSchema.getAddFeeObject().equals("00") && !tLMDutyPayAddFeeSchema.getAddFeeObject().equals("01")))
					{
						tLMDutyPayAddFeeSchema.setAddFeeObject("01");
					}
					tLMDutyPayAddFeeSchema.setAddFeeType(mLPPremSet.get(j).getPayPlanType());

					tReflections.transFields(tLCPremSchema,mLPPremSet.get(j).getSchema());
					VData tInputData = new VData();
					tInputData.add(tLCPolSchema);
					tInputData.add(tLCPremSchema);
					tInputData.add(tLCDutySchema);
					tInputData.add(tLMDutyPayAddFeeSchema);
					tInputData.add(mGlobalInput);
					if (!tAddPremCalBL.submitData(tInputData, ""))
					{ //重算加费 add by zhangrong
						CError.buildErr(this, "加费计算失败！");
					return false;
					}
					TransferData tTransferData = (TransferData) ((VData) tAddPremCalBL.getResult()).getObjectByObjectName("TransferData",0);
					aLPPolSchema.setPrem(aLPPolSchema.getPrem() - mLPPremSet.get(j).getPrem());
					mLPPremSet.get(j).setPrem((String) tTransferData.getValueByName("mValue"));
					//因为在保费保额重算中并不重算加费，所以需要在重算加费后更新Pol表中的保费数据。
					aLPPolSchema.setPrem(aLPPolSchema.getPrem() + mLPPremSet.get(j).getPrem()); 
				}
			}
		}
		
		LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
		if (aLPPolSchema.getAppFlag() == null || !aLPPolSchema.getAppFlag().equals("9"))
		{
			tLJSPayPersonDB.setPolNo(aLPPolSchema.getPolNo());
		}
		else 
		{
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setInsuredNo(aLPPolSchema.getInsuredNo());
			tLCPolDB.setRiskCode(aLPPolSchema.getRiskCode());
			tLCPolDB.setAppFlag("1");
			//tLCPolDB.setPolState("1");
			LCPolSet tLCPolSet = tLCPolDB.query();
			if (tLCPolSet == null || tLCPolSet.size() != 1)
			{
				CError.buildErr(this, "查询待续保的险种保单失败！");
				return false;
			}
			tLJSPayPersonDB.setPolNo(tLCPolSet.get(1).getPolNo()); 
			//如果是续保险种保单，则查询其当前生效险种保单的保单号

		}
		LJSPayPersonSet tLJSPayPersonSet = tLJSPayPersonDB.query();
		if (tLJSPayPersonSet != null && tLJSPayPersonSet.size() > 0)
		{
			for (int i = 1; i <= tLJSPayPersonSet.size(); i++)
			{
				for (int j = 1; j <= mLPPremSet.size(); j++)
				{
					if (tLJSPayPersonSet.get(i).getDutyCode().equals(mLPPremSet.get(j).getDutyCode())
							&& tLJSPayPersonSet.get(i).getPayPlanCode().equals( mLPPremSet.get(j).getPayPlanCode()))
					{
						tLJSPayPersonSet.get(i).setSumActuPayMoney(mLPPremSet.get(j).getPrem());
						//更新已抽档的续期收费数据
								break;
					}
				}
			}
		}
		
		
		if (aLPPolSchema.getAppFlag() != null && aLPPolSchema.getAppFlag().equals("9")) 
		{ //续保保单不参与补退费计算 为什么?
			return true;
		}
 
		/*
                 if (aLPPolSchema.getPolNo() != aLPPolSchema.getMainPolNo() && tLMRiskAppDB.getRiskPeriod() != null &&
            (tLMRiskAppDB.getRiskPeriod().equals("M") || (tLMRiskAppDB.getRiskPeriod().equals("S"))))
                 {
           
                 }
		 */
		//按照最新交费间隔计算交费期数
		int premNum = 0;

		if (aLPPolSchema.getPayIntv() == 0)
		{
			premNum = 1;
		} else {
			premNum = PubFun.calInterval2(aLPPolSchema.getCValiDate(),
					aLPPolSchema.getPaytoDate(), "M") /
					aLPPolSchema.getPayIntv();
		}

		double intervalMoney = 0; //保费差额变量
		double intervalMoneyend = 0; //保费差额变量
		//double interestMoney = 0; //总利息变量
		Date tPayDate = tD.getDate(aLPPolSchema.getCValiDate()); //各期交费对应日，初始化为首期交费日

		LDCode1DB tLDCode1DB = new LDCode1DB();
		tLDCode1DB.setCodeType(aLPEdorItemSchema.getEdorType());
		tLDCode1DB.setCode("BF");
		tLDCode1DB.setCode1("000000");
		if (!tLDCode1DB.getInfo())
		{
			//获取补费会计科目
			mErrors.copyAllErrors(tLDCode1DB.mErrors);
			mErrors.addOneError(new CError("获取财务代码失败！"));
			return false;
		}
		String BFSubject = tLDCode1DB.getCodeName();



		tLDCode1DB.setCode("TF");
		if (!tLDCode1DB.getInfo())
		{ 
			//获取退费会计科目
			mErrors.copyAllErrors(tLDCode1DB.mErrors);
			mErrors.addOneError(new CError("获取财务代码失败！"));
			return false;
		}
		String TFSubject = tLDCode1DB.getCodeName();

		int polIntv = PubFun.calInterval(aLPPolSchema.getCValiDate(), aLPPolSchema.getPaytoDate(), "D");//保单保险期间
		int hasPolIntv = PubFun.calInterval(aLPPolSchema.getCValiDate(), mLPEdorItemSchema.getEdorValiDate(), "D");//保单已经经过天数



		LCPremDB tLCPremDB = new LCPremDB();
		LCPremSet tLCPremSet = new LCPremSet();
		
		for (int i = 0; i < premNum; i++) {
			try {
				intervalMoney = 0; //保费差额变量
				//interestMoney = 0; //总利息变量

				String tSql = "select * from LCPrem where PolNo = '" + aLPPolSchema.getPolNo() + "' ";

				if (aLPPolSchema.getPayIntv() == 1)
				{

					tSql += " and PayStartDate <= '" + tD.getString(tPayDate) +
					"' and PayEndDate >= '" + tD.getString(tPayDate) +
					"'";
				}
				else if (aLPPolSchema.getPayIntv() == 12)
				{

					tSql += " and PayStartDate <= '" + tD.getString(tPayDate) +
					"' and PayEndDate >= '" + tD.getString(tPayDate) +
					"'";
				}
				double NewPrem = 0;
				double Prem = 0;
				tLCPremSet = tLCPremDB.executeQuery(tSql);
				for (int j = 1; j <= mLPPremSet.size(); j++) {
					for (int k = 1; k <= tLCPremSet.size(); k++) {
						if (mLPPremSet.get(j).getPayPlanCode().substring(0, 6).
								equals(tLCPremSet.get(k).getPayPlanCode().substring(
										0, 6))) {
							intervalMoney = PubFun.round(mLPPremSet.get(j).getPrem() - tLCPremSet.get(k).getPrem(),2);
							NewPrem = mLPPremSet.get(j).getPrem();
							Prem = tLCPremSet.get(k).getPrem();
							logger.debug("------------------新保费---------" + NewPrem);
							logger.debug("------------------原保费----------" + Prem);
							logger.debug("-----------------保费差额-----------" + intervalMoney);
							break;
						}
					}

					String tSubOperType = "";
					if (mLPPremSet.get(j).getPayPlanType() != null &&
							(mLPPremSet.get(j).getPayPlanType().equals("01") ||
									mLPPremSet.get(j).getPayPlanType().equals("03"))) {
						if (intervalMoney > 0) {
							if (mLPPremSet.get(j).getAddFeeDirect() != null &&
									mLPPremSet.get(j).getAddFeeDirect().equals("00")) {
								tSubOperType = BqCode.Pay_AppntAddPremHealth +
								Integer.toString(i + 1);
							} else {
								tSubOperType = BqCode.Pay_InsurAddPremHealth +
								Integer.toString(i + 1);
							}
						} else if (intervalMoney < 0) {
							tSubOperType = BqCode.Get_AddPremHealth +
							Integer.toString(i + 1);
						}
					} else if (mLPPremSet.get(j).getPayPlanType() != null &&
							(mLPPremSet.get(j).getPayPlanType().equals("02") ||
									mLPPremSet.get(j).getPayPlanType().equals("04"))) {
						if (intervalMoney > 0) {
							if (mLPPremSet.get(j).getAddFeeDirect() != null &&
									mLPPremSet.get(j).getAddFeeDirect().equals("00")) {
								tSubOperType = BqCode.Pay_AppntAddPremOccupation +
								Integer.toString(i + 1);
							} else {
								tSubOperType = BqCode.Pay_InsurAddPremOccupation +
								Integer.toString(i + 1);
							}

						} else {
							tSubOperType = BqCode.Get_AddPremOccupation +
							Integer.toString(i + 1);
						}
					} else {
						if (intervalMoney >= 0) {
							tSubOperType = BqCode.Pay_Prem +
							Integer.toString(i + 1);

						} else if (intervalMoney < 0) {
							tSubOperType = BqCode.Get_Prem +
							Integer.toString(i + 1);
						}
					}
					
					if (intervalMoney != 0) {

						if (intervalMoney > 0) {
							//补交保费
							intervalMoney = Double.parseDouble(new DecimalFormat("0.00").format(intervalMoney));
							intervalMoneyend = Double.parseDouble(new DecimalFormat("0.00").format(intervalMoneyend));
							//新算法
							if(isNewCal&&isIOChg){
								double dNetPrem = intervalMoney*(1-((double)hasPolIntv/polIntv));
								intervalMoney = PubFun.round(dNetPrem,2);
							}
							addBFLJSGetEndorse(aLPEdorItemSchema,aLPPolSchema,mLPPremSet.get(j).getDutyCode(),mLPPremSet.get(j).getPayPlanCode(), BFSubject,tSubOperType, intervalMoney); //在批改补退费表中增加单期补交保费记录

						} else { 
							//实交保费多于应交保费，则退还保费
							intervalMoney = Double.parseDouble(new DecimalFormat("0.00").format(intervalMoney));
							intervalMoneyend = Double.parseDouble(new DecimalFormat("0.00").format(intervalMoneyend));
							if (intervalMoney != 0) {
								if(isNewCal&&isIOChg){
									double dNetPrem = intervalMoney*(1-0.25)*(1-((double)hasPolIntv/polIntv));
									intervalMoney = PubFun.round(dNetPrem,2);;
								}
								addTFLJSGetEndorse(aLPEdorItemSchema,aLPPolSchema,mLPPremSet.get(j).getDutyCode(), mLPPremSet.get(j).getPayPlanCode(), TFSubject,tSubOperType, intervalMoney); //在批改补退费表中增加单期退还保费记录
							}
						}

						mChgPrem += intervalMoney;
						mChgPremend += intervalMoney;
						mGetMoney += intervalMoney;
						//mGetInterest += interestMoney;
					}
				}
			} catch (Exception ex) {
				CError.buildErr(this, "保费补退费计算异常！");
			}

			if (aLPPolSchema.getPayIntv() == 1) {
				tPayDate = PubFun.calDate(tPayDate, aLPPolSchema.getPayIntv(),
						"M", null);
			} else if (aLPPolSchema.getPayIntv() == 12) {
				tPayDate = PubFun.calDate(tPayDate, aLPPolSchema.getPayIntv(),
						"M", null);
			}
		}

		return true;
	}


	/**
	 * 往批改补退费表（应收/应付）新增数据
	 * @return
	 */
	private boolean addBFLJSGetEndorse(LPEdorItemSchema aLPEdorItemSchema,
			LPPolSchema aLPPolSchema,
			String aDutyCode, String aPayPlanCode,
			String feeType, String subType,
			double feeMoney) {
		LJSGetEndorseSchema tLJSGetEndorseSchema = mBqCalBL.initLJSGetEndorse(
				aLPEdorItemSchema, aLPPolSchema, aDutyCode, aPayPlanCode,
				subType,
				feeType, feeMoney,
				mGlobalInput);
		tLJSGetEndorseSchema.setSubFeeOperationType(tLJSGetEndorseSchema.
				getFeeFinaType());
		tLJSGetEndorseSchema.setSerialNo(String.valueOf(feeMoney));
		if (tLJSGetEndorseSchema != null) {
			mLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		}

		return true;
	}
	
	/**
	 * 往批改补退费表（应收/应付）新增数据
	 * @return
	 */
	private boolean addTFLJSGetEndorse(LPEdorItemSchema aLPEdorItemSchema,
			LPPolSchema aLPPolSchema,
			String aDutyCode, String aPayPlanCode,
			String feeType, String subType,
			double feeMoney) {
		LJSGetEndorseSchema tLJSGetEndorseSchema = mBqCalBL.initLJSGetEndorse(
				aLPEdorItemSchema, aLPPolSchema, aDutyCode, aPayPlanCode,
				subType,
				feeType, feeMoney,
				mGlobalInput);
		tLJSGetEndorseSchema.setSubFeeOperationType(tLJSGetEndorseSchema.
				getFeeFinaType());
		tLJSGetEndorseSchema.setGetMoney(-feeMoney);
		tLJSGetEndorseSchema.setSerialNo(String.valueOf(-feeMoney));
		tLJSGetEndorseSchema.setGetFlag("1");
		if (tLJSGetEndorseSchema != null) {
			mLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		}

		return true;
	}

	private boolean prepareOutputData() {
		mMap.put(UpdateEdorState.getUpdateEdorStateSql(mLPEdorItemSchema),
				"UPDATE");

		if (mOperate.equals("INSERT||MAIN") ||
				mOperate.equals("INSERT||GRPMAIN")) {
			mMap.put(mLPEdorMainSet, "DELETE&INSERT");
			mMap.put("Delete from lpedoritem where edorno='" +
					mLPEdorItemSet.get(1).getEdorNo() +
					"' and edortype='IC' and contno='" +
					mLPEdorItemSet.get(1).getContNo() + "' and polno='000000'",
			"DELETE");
			mMap.put(mLPEdorItemSet, "DELETE&INSERT");
			mMap.put(mLPInsuredSet, "DELETE&INSERT");
			mMap.put(mLPPersonSet, "DELETE&INSERT");
			mMap.put(mLPAppntSet, "DELETE&INSERT");
		}
		if (mOperate.equals("UPDATE||MAIN")) {
			mMap.put(mLPInsuredSet, "UPDATE");
			mMap.put(mLPEdorItemSet, "UPDATE");
			mMap.put(mLPAppntSet, "UPDATE");
			mMap.put(mLPPersonSet, "UPDATE");
		}
		mMap.put(mLPContSet, "DELETE&INSERT");
		mMap.put(mLPPolSet, "DELETE&INSERT");
		mMap.put(mLPDutySet, "DELETE&INSERT");
		mMap.put(mLPPremSet, "DELETE&INSERT");
		mMap.put(mLPGetSet, "DELETE&INSERT");
		mMap.put("delete from LJSGetEndorse where EndorsementNo = '" + mLPEdorMainSet.get(1).getEdorNo() + "' and OtherNo = '"+mLPEdorMainSet.get(1).getEdorNo()+"' and ContNo = '"+mLPEdorMainSet.get(1).getContNo()+"'", "DELETE");
		mMap.put(mLJSGetEndorseSet, "DELETE&INSERT");
		mMap.put(mLPEngBonusPolSet, "DELETE&INSERT");

		String tSql = "update LPEdorItem set getmoney = nvl((select SUM(getmoney) from ljsgetendorse where endorsementno='" +
		mLPEdorMainSet.get(1).getEdorNo() +
		"' AND FEEOPERATIONTYPE='IC' and PolNo=LPEdorItem.PolNo),0) where EdorNo='" +
		mLPEdorMainSet.get(1).getEdorNo() +
		"' and EdorType='IC'";
		mMap.put(tSql, "UPDATE");
		String strSQL1 = "update LPGrpEdorItem set GetMoney = (select sum(GetMoney) from LPEdorItem where EdorNo = '"
			+ mLPEdorMainSet.get(1).getEdorNo() +
			"' and EdorType = '" +
			mLPEdorItemSet.get(1).getEdorType() + "'), "
			+
			"GetInterest = (select sum(GetInterest) from LPEdorItem where EdorNo = '"
			+ mLPEdorMainSet.get(1).getEdorNo() +
			"' and EdorType = '" +
			mLPEdorItemSet.get(1).getEdorType() + "'), "
			+
			"ChgPrem = (select sum(ChgPrem) from LPEdorItem where EdorNo = '"
			+ mLPEdorMainSet.get(1).getEdorNo() +
			"' and EdorType = '" +
			mLPEdorItemSet.get(1).getEdorType() + "'), "
			+
			"ChgAmnt = (select sum(ChgAmnt) from LPEdorItem where EdorNo = '"
			+ mLPEdorMainSet.get(1).getEdorNo() +
			"' and EdorType = '" +
			mLPEdorItemSet.get(1).getEdorType() +
			"') where EdorNo = '"
			+ mLPEdorMainSet.get(1).getEdorNo() +
			"' and EdorType = '" +
			mLPEdorItemSet.get(1).getEdorType() + "'";
		mMap.put(strSQL1, "UPDATE");
		String strSQL2 = "update LPEdorMain set GetMoney = (select sum(GetMoney) from LPEdorItem where EdorNo = '"
			+ mLPEdorMainSet.get(1).getEdorNo() +
			"'), GetInterest = (select sum(GetInterest) from LPEdorItem where EdorNo = '"
			+ mLPEdorMainSet.get(1).getEdorNo() +
			"') where EdorNo = '" +
			mLPEdorMainSet.get(1).getEdorNo() +
			"'";
		mMap.put(strSQL2, "UPDATE");

		String strSQL3 = "update LPGrpEdorItem set edorstate='1'" +
		" where EdorNo = '"
		+ mLPEdorMainSet.get(1).getEdorNo() +
		"' and EdorType = '" +
		mLPEdorItemSet.get(1).getEdorType() + "'";
		logger.debug("-------------------------------" + strSQL3);
		mMap.put(strSQL3, "UPDATE");

		String strSQL4 = "update LPGrpEdorMain set edorstate='1'" +
		" where EdorNo = '"
		+ mLPEdorMainSet.get(1).getEdorNo() + "'";
		logger.debug("----------------------------------" + strSQL4);
		mMap.put(strSQL4, "UPDATE");
		String tUpdateSql = "update LPCont a set "
			+ "	a.prem = (select sum(Prem) from LPPol where ContNo = a.ContNo and EdorNo = a.EdorNo and EdorType = a.EdorType) ,"		
			+ "	a.SumPrem = (select sum(SumPrem) from LPPol where ContNo = a.ContNo and EdorNo = a.EdorNo and EdorType = a.EdorType) "
			+ " where a.ContNo = '"+mLPEdorMainSet.get(1).getContNo()+"' and a.EdorNo = '"+mLPEdorMainSet.get(1).getEdorNo()+"' and a.EdorType = '"+mLPEdorItemSet.get(1).getEdorType()+"'";
		mMap.put(tUpdateSql, "UPDATE");
		mResult.clear();
		mResult.add(mMap);

		return true;
	}

}
