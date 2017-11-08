package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.bl.LPAppntBL;
import com.sinosoft.lis.bl.LPContBL;
import com.sinosoft.lis.bl.LPInsuredBL;
import com.sinosoft.lis.bl.LPPersonBL;
import com.sinosoft.lis.bl.LPPolBL;
import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDiscountDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LDOccupationDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPCSpecSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LPCustomerImpartSchema;
import com.sinosoft.lis.schema.LPDiscountSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LCCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDiscountSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPCSpecSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LPCustomerImpartSet;
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
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.tb.CustomerImpartBL;

/**
 * @author Administrator
 * IO保全不产生不退费，核保有可能产生不退费
 */
public class PEdorIODetailBL {
private static Logger logger = Logger.getLogger(PEdorIODetailBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();

	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	private GlobalInput mGlobalInput = new GlobalInput();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private LPContSchema mLPContSchema = new LPContSchema();

	private LCPolSchema mLCPolSchema = new LCPolSchema();

	private LPPersonSchema mLPPersonSchema = new LPPersonSchema();

	private LPInsuredSchema mLPInsuredSchema = new LPInsuredSchema();
	
	private LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet();
	
	private LPCustomerImpartSet mLPCustomerImpartSet = new LPCustomerImpartSet();
	private LPCustomerImpartParamsSet mLPCustomerImpartParamsSet = new LPCustomerImpartParamsSet();
	private LPDiscountSet mLPDiscountSet = new LPDiscountSet();
	private LPAppntSet mLPAppntSet = new LPAppntSet();

	private LPPremSet mLPPremSet = new LPPremSet();

	private LPGetSet mLPGetSet = new LPGetSet();

	private LPDutySet mLPDutySet = new LPDutySet();

	private LPPolSet mLPPolSet = new LPPolSet();

	private LPContSet mLPContSet = new LPContSet();

	private LCContSchema mLCContSchema = new LCContSchema();
	
	private double mChgPrem = 0.0;

	private double mChgAmnt = 0.0;
	
	private double mSourcePrem = 0.0;

	private double mSourceAmnt = 0.0;
	
	private Reflections tRef = new Reflections();

	public PEdorIODetailBL() {
	}

	public String getOperate() {
		return this.mOperate;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 数据校验操作
		if (!checkData()) {
			return false;
		}

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mInputData = (VData) cInputData.clone();
		mLPInsuredSchema = (LPInsuredSchema) cInputData.getObjectByObjectName(
				"LPInsuredSchema", 0);
		mLPEdorItemSchema = (LPEdorItemSchema) cInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		
		mLCCustomerImpartSet = (LCCustomerImpartSet) mInputData.getObjectByObjectName("LCCustomerImpartSet", 0);
		
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (mLPInsuredSchema == null || mLPEdorItemSchema == null
				|| mGlobalInput == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("查询保单信息失败!");
			return false;
		}
		mLCContSchema.setSchema(tLCContDB.getSchema());

		tRef.transFields(mLPContSchema, mLCContSchema);
		mLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		mLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet.size() < 1) {
			mErrors.addOneError("传入数据不合法!");
			return false;
		}
		mLPEdorItemSchema = tLPEdorItemSet.get(1);

		// 校验职业代码
		LDOccupationDB tLDOccupationDB = new LDOccupationDB();
		tLDOccupationDB.setOccupationCode(mLPInsuredSchema.getOccupationCode());
		if (!tLDOccupationDB.getInfo()) {
			CError.buildErr(this, "查询职业代码表失败！");
			return false;
		}
		if (tLDOccupationDB.getOccupationType() != null
				&& tLDOccupationDB.getOccupationType().equals("z")) {
			CError.buildErr(this, "职业类别变更为拒保类职业，请对该保单进行退保！");
			return false;
		}
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		// 处理之前先删除上次保存数据
		String sDelLPPol = " delete from lppol where edorno = '?edorno?' and edortype = '?edortype?' and contno = '?contno?' ";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(sDelLPPol);
		sbv1.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv1.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv1.put("contno", mLPEdorItemSchema.getContNo());
		String sDelLPDuty = " delete from lpduty where edorno = '?edorno?' and edortype = '?edortype?' and contno = '?contno?' ";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(sDelLPDuty);
		sbv2.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv2.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv2.put("contno", mLPEdorItemSchema.getContNo());
		String sDelLPPrem = " delete from lpprem where edorno = '?edorno?' and edortype = '?edortype?' and contno = '?contno?' ";
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(sDelLPPrem);
		sbv3.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv3.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv3.put("contno", mLPEdorItemSchema.getContNo());
		String sDelLPGet = " delete from lpget where edorno = '?edorno?' and edortype = '?edortype?' and contno = '?contno?' ";
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql(sDelLPGet);
		sbv4.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv4.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv4.put("contno", mLPEdorItemSchema.getContNo());
		String sLPCustomerImpart = " delete from LPCustomerImpart where edorno = '?edorno?' and edortype = '?edortype?' and contno = '?contno?' ";
		SQLwithBindVariables sbv5=new SQLwithBindVariables();
		sbv5.sql(sLPCustomerImpart);
		sbv5.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv5.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv5.put("contno", mLPEdorItemSchema.getContNo());
		String sLPCustomerImpartParams = " delete from LPCustomerImpartParams where edorno = '?edorno?' and edortype = '?edortype?' and contno = '?contno?' ";
		SQLwithBindVariables sbv6=new SQLwithBindVariables();
		sbv6.sql(sLPCustomerImpartParams);
		sbv6.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv6.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv6.put("contno", mLPEdorItemSchema.getContNo());
		mMap.put(sbv1, "DELETE");
		mMap.put(sbv2, "DELETE");
		mMap.put(sbv3, "DELETE");
		mMap.put(sbv4, "DELETE");
		mMap.put(sbv5, "DELETE");
		mMap.put(sbv6, "DELETE");
		
		//为核保准备数据
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
				tRef.transFields(mLPCSpecSchema, tLCCSpecSet.get(k));	
				mLPCSpecSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				mLPCSpecSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				mLPCSpecSet.add(mLPCSpecSchema);				
			}
			mMap.put(mLPCSpecSet, "DELETE&INSERT");
		}

		boolean flag = true;
		// 查询保单数据
		LCPolDB tLCPolDB = new LCPolDB();
		String QueryPol = "select * from lcpol where contno = '?contno?' and insuredno = '"
				+ "?insuredno?"
				//cvalidate可能排除了新增附加险的预约生效的polno 此处的逻辑需要讨论
				+ "' and appflag in ('1') "
				//modify by jiaqiangli 2009-11-10 保全与催收不共存 
			    //另外也别限制了cvalidate和enddate 只需要appflag='1'就行了 前者可能多限制了预约新增附加险，后者排除了宽末申请的保全项的一年期短期险
				//类同于js中的注释与修改
				//order by 确保豁免险最后处理
				//+ mLPEdorItemSchema.getEdorValiDate() + "' order by polno,mainpolno";
				//modify by jiaqiangli 2009-04-18 重新设置排序方式，确保豁免险最后进行联动处理
				+ " order by (case when exists(select 1 from lmriskapp where riskcode=lcpol.riskcode and risktype7 in ('1','2')) then 1 else 0 end) ";
		SQLwithBindVariables sbv7=new SQLwithBindVariables();
		sbv7.sql(QueryPol);
		sbv7.put("contno", mLPEdorItemSchema.getContNo());
		sbv7.put("insuredno", mLPEdorItemSchema.getInsuredNo());
		LCPolSet tLCPolSet = tLCPolDB.executeQuery(sbv7); // 从C表获得该合同下该被保人以主被保人身份的所有险种信息

		// 处理客户表信息
		mLPPersonSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		mLPPersonSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		mLPPersonSchema.setCustomerNo(mLPInsuredSchema.getInsuredNo());
		LPPersonBL aLPPersonBL = new LPPersonBL();
		if (!aLPPersonBL.queryLPPerson(mLPPersonSchema)) {
			CError.buildErr(this, "查询个人客户表失败!");
			return false;
		}
		LPPersonSchema aLPPersonSchema = new LPPersonSchema();
		aLPPersonSchema = aLPPersonBL.getSchema();
		aLPPersonSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		aLPPersonSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		aLPPersonSchema.setOccupationCode(mLPInsuredSchema.getOccupationCode());
		aLPPersonSchema.setOccupationType(mLPInsuredSchema.getOccupationType());
		aLPPersonSchema.setWorkType(mLPInsuredSchema.getWorkType());
		mMap.put(aLPPersonSchema, "DELETE&INSERT");

		// 准备个人保单LPInsuredSchema的信息
		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		tLPInsuredSchema.setSchema(mLPInsuredSchema);
		LPInsuredBL tLPInsuredBL = new LPInsuredBL();
		LPInsuredSet tLPInsuredSet = new LPInsuredSet();
		tLPInsuredSet = tLPInsuredBL.queryLPInsured(mLPEdorItemSchema);
		if (tLPInsuredSet == null || tLPInsuredSet.size() <= 0) {
			CError.buildErr(this, "被保人资料查询失败!");
			return false;
		} else {
			for (int i = 1; i <= tLPInsuredSet.size(); i++) {
				if (tLPInsuredSet.get(i).getInsuredNo().equals(
						mLPInsuredSchema.getInsuredNo())
						&& tLPInsuredSet.get(i).getContNo().equals(
								mLPInsuredSchema.getContNo())) {
					tLPInsuredSchema.setSchema(tLPInsuredSet.get(i));
				}
			}
		}
		tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsuredSchema.setOccupationCode(mLPInsuredSchema.getOccupationCode());
		tLPInsuredSchema.setOccupationType(mLPInsuredSchema.getOccupationType());
		tLPInsuredSchema.setWorkType(mLPInsuredSchema.getWorkType());
		tLPInsuredSchema.setMakeDate(PubFun.getCurrentDate());
		tLPInsuredSchema.setMakeTime(PubFun.getCurrentTime());
		tLPInsuredSchema.setModifyDate(PubFun.getCurrentDate());
		tLPInsuredSchema.setModifyTime(PubFun.getCurrentTime());
		mMap.put(tLPInsuredSchema, "DELETE&INSERT");
		
		mLPInsuredSchema.setSchema(tLPInsuredSchema);

		// 比较P表和C表，如果职业类别改变则重算保额保费
		boolean isReCal = false; // 是否需要重新计算
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		tLCInsuredDB.setContNo(mLPEdorItemSchema.getContNo());
		tLCInsuredDB.setInsuredNo(mLPInsuredSchema.getInsuredNo());
		tLCInsuredSchema = tLCInsuredDB.query().get(1);
		if (!StrTool.compareString(tLCInsuredSchema.getOccupationCode(),
				mLPInsuredSchema.getOccupationCode())
				|| !StrTool.compareString(tLCInsuredSchema.getOccupationType(),
						mLPInsuredSchema.getOccupationType())) {
			isReCal = true;
		}

		// 准备保单的客户相关信息
		LPPolSchema tLPPolSchema = new LPPolSchema();
		LPContSchema tLPContSchema = new LPContSchema();
		LPPolBL tLPPolBL = new LPPolBL();
		LPContBL tLPContBL = new LPContBL();
		LPEdorItemSchema tLPEdorItemSchema = null;
		
		if (tLCPolSet.size() == 0) {
			CError.buildErr(this, "没有查询到需要职业变更的险种！");
			return false;
		}

		for (int i = 1; i <= tLCPolSet.size(); i++) {
		
			mLCPolSchema = tLCPolSet.get(i);
			mLCPolSchema.setOccupationType(mLPInsuredSchema.getOccupationType());
			
			//保存原结构
			mSourcePrem = mLCPolSchema.getPrem();
			mSourceAmnt = mLCPolSchema.getAmnt();
			
			tLPEdorItemSchema = mLPEdorItemSchema.getSchema();
			tLPEdorItemSchema.setPolNo(tLCPolSet.get(i).getPolNo());
			tLPPolBL.queryOtherLPPol(tLPEdorItemSchema);
			tLPPolSchema = tLPPolBL.getSchema();
			tLPPolSchema.setOccupationType(mLPInsuredSchema.getOccupationType());

			// add by jiaqiangli 2009-02-16 豁免联动处理
			if (judgeExempt(tLPPolSchema.getPolNo()) == true) {
				//保费变化
				tLPPolSchema.setAmnt(this.getExemptAMNT(mLPPolSet, tLPPolSchema.getRiskCode()));
				logger.debug("tLPPolSchema.amnt"+tLPPolSchema.getAmnt());
				
				if (tLPPolSchema.getAmnt() <= 0) {
					CError.buildErr(this, "计算豁免保额失败");
					return false;
				}
			}
			// add by jiaqiangli 2009-02-16 豁免联动处理

			if (isReCal) {// 如果职业类别变更则重算保额保费
				try {
					// 重算保费、保额，并计算各期保费的补退费和利息,更新LPDutySet,LPPremSet，LPGetSet
					if (!ReCalculate(tLPPolSchema, tLPEdorItemSchema)) {
						mErrors.addOneError(new CError("重算保额、保费失败！"));
						return false;
					}
				} 
				catch (Exception e) {
					CError.buildErr(this, "数据处理失败! " + e.getMessage());
					return false;
				}
			}
			//保存变化值
			mChgPrem += tLPPolSchema.getPrem() - mSourcePrem;
			mChgAmnt += tLPPolSchema.getAmnt() - mSourceAmnt;
			
			tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPolSchema.setModifyDate(PubFun.getCurrentDate());
			tLPPolSchema.setModifyTime(PubFun.getCurrentTime());	
			mLPPolSet.add(tLPPolSchema);
		}
		
		//由于重算的时候处理lcprem时，只取 payenddate >= CurrentDate and paystartdate <= CurrentDate
		//因此如果是预约加费的话，lcprem需要另外备份生成lpprem，否则可能会导致此种加费无法删除或者终止。
		if (isReCal) 
		{
			if(!bakYYAddPrem(mLPEdorItemSchema.getContNo()))
			{
				mErrors.addOneError(new CError("预约加费记录生成保费项P表数据失败！"));
				return false;
			}
		}

		//重算后的保费结构
		mMap.put(mLPPolSet, "DELETE&INSERT");
		mMap.put(mLPDutySet, "DELETE&INSERT");
		mMap.put(mLPPremSet, "DELETE&INSERT");
		mMap.put(mLPGetSet, "DELETE&INSERT");
		
		// 健康告知处理
		if (mLCCustomerImpartSet != null && mLCCustomerImpartSet.size() > 0) {
			LCCustomerImpartSchema aLCCustomerImpartSchema = new LCCustomerImpartSchema();
			for (int k = 1; k <= mLCCustomerImpartSet.size(); k++) {
				aLCCustomerImpartSchema = mLCCustomerImpartSet.get(k);
				aLCCustomerImpartSchema.setGrpContNo(mLCContSchema
						.getGrpContNo());
				if (mLCContSchema.getGrpContNo() == null) {
					aLCCustomerImpartSchema
							.setGrpContNo("00000000000000000000");
				}
				aLCCustomerImpartSchema.setPrtNo(mLCContSchema.getPrtNo());
				if (mLCContSchema.getPrtNo() == null) {
					mErrors.addOneError("个人保单印刷号码查询失败!");
					return false;
				}
				logger.debug("===================== PrtNo ==========="
						+ mLCContSchema.getPrtNo());
				aLCCustomerImpartSchema.setCustomerNo(mLCContSchema
						.getInsuredNo());
				aLCCustomerImpartSchema.setCustomerNoType("1");
				mLCCustomerImpartSet.set(k, aLCCustomerImpartSchema);
				logger.debug(mLCCustomerImpartSet.get(k).getCustomerNo());
			}
		}

		if (mLCCustomerImpartSet != null && mLCCustomerImpartSet.size() > 0) {
			VData cVData = new VData();
			cVData.add(mLCCustomerImpartSet);
			cVData.add(mGlobalInput);
			CustomerImpartBL tCustomerImpartBL = new CustomerImpartBL();
			tCustomerImpartBL.submitData(cVData, "IMPART||DEAL");
			mErrors.copyAllErrors(tCustomerImpartBL.mErrors);
			if (tCustomerImpartBL.mErrors.needDealError()) {
				CError tError = new CError();
				tError.moduleName = "ContInsuredBL";
				tError.functionName = "dealData";
				tError.errorMessage = tCustomerImpartBL.mErrors.getFirstError().toString();
				this.mErrors.addOneError(tError);
				return false;
			}
			VData tempVData = new VData();
			tempVData = tCustomerImpartBL.getResult();
			LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
			LCCustomerImpartParamsSet tLCCustomerImpartParamsSet = new LCCustomerImpartParamsSet();
			try {
				tLCCustomerImpartSet = (LCCustomerImpartSet) tempVData.getObjectByObjectName("LCCustomerImpartSet", 0);
				tLCCustomerImpartParamsSet = (LCCustomerImpartParamsSet) tempVData.getObjectByObjectName("LCCustomerImpartParamsSet", 0);
			} 
			catch (Exception e) {
				CError.buildErr(this, "接受数据失败!");
				return false;
			}

			if (tLCCustomerImpartSet != null && tLCCustomerImpartSet.size() > 0) {
				for (int i = 1; i <= tLCCustomerImpartSet.size(); i++) {
					LPCustomerImpartSchema tLPCustomerImpartSchema = new LPCustomerImpartSchema();
					tRef.transFields(tLPCustomerImpartSchema,tLCCustomerImpartSet.get(i));
					tLPCustomerImpartSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPCustomerImpartSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					tLPCustomerImpartSchema.setGrpContNo("00000000000000000000");
					tLPCustomerImpartSchema.setPrtNo(mLCContSchema.getPrtNo());
					if (mLCContSchema.getPrtNo() == null) {
						mErrors.addOneError("个人保单印刷号码查询失败!");
						return false;
					}
					mLPCustomerImpartSet.add(tLPCustomerImpartSchema);
				}
			}
			if (tLCCustomerImpartParamsSet != null && tLCCustomerImpartParamsSet.size() > 0) {
				for (int i = 1; i <= tLCCustomerImpartParamsSet.size(); i++) {
					LPCustomerImpartParamsSchema tLPCustomerImpartParamsSchema = new LPCustomerImpartParamsSchema();
					tRef.transFields(tLPCustomerImpartParamsSchema,tLCCustomerImpartParamsSet.get(i));
					tLPCustomerImpartParamsSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPCustomerImpartParamsSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					tLPCustomerImpartParamsSchema.setGrpContNo("00000000000000000000");
					tLPCustomerImpartParamsSchema.setPrtNo(mLCContSchema.getPrtNo());
					if (mLCContSchema.getPrtNo() == null) {
						mErrors.addOneError("个人保单印刷号码查询失败!");
						return false;
					}
					mLPCustomerImpartParamsSet.add(tLPCustomerImpartParamsSchema);
				}
			}
			if (mLPCustomerImpartSet.size() > 0) {
				mMap.put(mLPCustomerImpartSet, "DELETE&INSERT");
			}
			if (mLPCustomerImpartParamsSet.size() > 0) {
				mMap.put(mLPCustomerImpartParamsSet, "DELETE&INSERT");
			}
		}

		// 处理LPCont表中信息
		tLPContBL.queryLPCont(mLPEdorItemSchema);
		tLPContSchema = tLPContBL.getSchema();
		tLPContSchema.setPrem(PubFun.round((mChgPrem + tLPContSchema.getPrem()),2));
		tLPContSchema.setAmnt(PubFun.round((mChgAmnt + tLPContSchema.getAmnt()),2));
		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		mLPContSet.add(tLPContSchema);
		mMap.put(mLPContSet, "DELETE&INSERT");

		// 处理LPAppnt表中信息
		if (tLPContSchema.getContType().equals("1")
				&& mLPInsuredSchema.getInsuredNo().equals(
						tLPContSchema.getAppntNo())) { // 若投保人和被保人相同，则修改投保人信息
			LPAppntBL tLPAppntBL = new LPAppntBL();
			LPAppntSet tLPAppntSet = tLPAppntBL.queryLPAppnt(mLPEdorItemSchema);
			if (tLPAppntSet == null || tLPAppntSet.size() <= 0) {
				mErrors.copyAllErrors(tLPAppntSet.mErrors);
				mErrors.addOneError(new CError("查询投保人失败！"));
				return false;
			} else {
				LPAppntSchema tLPAppntSchema = tLPAppntSet.get(1);
				tLPAppntSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPAppntSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPAppntSchema.setOccupationCode(mLPInsuredSchema.getOccupationCode());
				tLPAppntSchema.setOccupationType(mLPInsuredSchema.getOccupationType());
				mLPAppntSet.add(tLPAppntSchema);
			}
			mMap.put(mLPAppntSet, "DELETE&INSERT");
		}else  //为核保准备数据 add by pst 2009-05-06
		{
			
			LPAppntBL tLPAppntBL = new LPAppntBL();
			LPAppntSet tLPAppntSet = tLPAppntBL.queryLPAppnt(mLPEdorItemSchema);
			if (tLPAppntSet == null || tLPAppntSet.size() <= 0) {
				mErrors.copyAllErrors(tLPAppntSet.mErrors);
				mErrors.addOneError(new CError("查询投保人失败！"));
				return false;
			} else {
				LPAppntSchema tLPAppntSchema = tLPAppntSet.get(1);
				mLPAppntSet.add(tLPAppntSchema);
			}
			mMap.put(mLPAppntSet, "DELETE&INSERT");			
		}
		if(mLPDiscountSet != null &&mLPDiscountSet.size()>0){
			
			mMap.put(mLPDiscountSet, "DELETE&INSERT");
		}
		mLPEdorItemSchema.setEdorState("3");
		mLPEdorItemSchema.setOperator(mGlobalInput.Operator);
		mLPEdorItemSchema.setChgPrem(mChgPrem);
		mLPEdorItemSchema.setChgAmnt(mChgAmnt);
		mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemSet.add(mLPEdorItemSchema.getSchema());
		mMap.put(tLPEdorItemSet, "UPDATE");

		mResult.clear();
		mResult.add(mMap);
		return flag;
	}
	
	private boolean bakYYAddPrem(String tContNo)
	{	
		LCPremSet xLCPremSet = new LCPremSet();
		LCPremDB xLCPremDB = new LCPremDB();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql("select * from lcprem a where contno='?tContNo?' and payplancode like '000000%' " 
				+" and exists(select 1 from lcpol b where b.contno=a.contno and b.polno=a.polno and b.appflag='1' and a.paystartdate>=b.paytodate) ");
		sqlbv.put("tContNo", tContNo);
		xLCPremSet = xLCPremDB.executeQuery(sqlbv);
		if(xLCPremSet.size()==0)
		{
			return true;
		}
		for (int b = 1; b <= xLCPremSet.size(); b++) 
		{
			LPPremSchema xLPPremSchema = new LPPremSchema();
			tRef.transFields(xLPPremSchema, xLCPremSet.get(b));
			xLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			xLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			mLPPremSet.add(xLPPremSchema);
		}
		return true;
	}

	private double getExemptAMNT(LPPolSet insrtLPPolSet,String tRiskCode) {
		//累加豁免险保费
		double tAllExemptPrem = 0.00;
		//豁免累加
		String tExemptSQL = "select polno from lcpol where appflag='1' and contno = '?contno?' and payintv > 0 and riskcode in (select code1 from ldcode1 where codetype = 'freerisk' and code='?tRiskCode?')";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tExemptSQL);
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
		sqlbv.put("tRiskCode", tRiskCode);
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
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
		sqlbv.put("tPolNo", tPolNo);
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
		tExemptLCPolSet = tExemptLCPolDB.executeQuery(sqlbv);
		logger.debug("tExemptLCPolSet.size()"+tExemptLCPolSet.size());
		if (tExemptLCPolSet.size() == 1)
			return true;
		else
			return false;		
	}

	private boolean ReCalculate(LPPolSchema aLPPolSchema,LPEdorItemSchema aLPEdorItemSchema) {

		logger.debug("EndDate=" + aLPPolSchema.getEndDate());
		String aEndDate = aLPPolSchema.getEndDate();
		String aPayEndDate = aLPPolSchema.getPayEndDate();
		String aPaytoDate = aLPPolSchema.getPaytoDate();

		FDate fd = new FDate();
		TransferData pTransferData = new TransferData();
		// 传入变更后的被保人职业代码
		pTransferData.setNameAndValue("Occupation", mLPInsuredSchema.getOccupationCode());

		ReCalBL tReCalBL = new ReCalBL(aLPPolSchema, aLPEdorItemSchema,pTransferData);
		
		// modify by jiaqiangli 2009-04-18 此处不光只有lcpol中的要素，还有lcduty.amnt豁免保额联动
		
		// 准备重算需要的保单表数据
		LCPolBL tLCPolBL = tReCalBL.getRecalPol(aLPPolSchema);
		// 准备重算需要的责任表数据
		LCDutyBLSet tRLCDutyBLSet = tReCalBL.getRecalDuty(aLPEdorItemSchema);
		// 准备重算需要的保费项表数据
		LCPremSet tLCPremSet = tReCalBL.getRecalPrem(aLPEdorItemSchema);
		// 准备重算需要的领取项表数据
		LCGetBLSet tLCGetBLSet = tReCalBL.getRecalGet(aLPEdorItemSchema);
		for (int i = 1; i <= tRLCDutyBLSet.size(); i++) {
			//comment by jiaqiangli 主要是豁免联动需要处理这个条件
			tRLCDutyBLSet.get(i).setAmnt(aLPPolSchema.getAmnt());
		}
		// 重算withdata
		if (!tReCalBL.recalWithData(tLCPolBL, tRLCDutyBLSet, tLCPremSet, tLCGetBLSet, aLPEdorItemSchema)) {
			this.mErrors.copyAllErrors(tReCalBL.mErrors);
			CError.buildErr(this, "保费重算失败");
			return false;
		}
//		if (!tReCalBL.recal()) { // 保额，保费重算
//			mErrors.addOneError(new CError("重算保额、保费失败！"));
//			return false;
//		}
		aLPPolSchema.setSchema(tReCalBL.aftLPPolSet.get(1)); // 将计算结果返回

		LPDutySet mInsertLPDutySet = new LPDutySet();
		LPPremSet mInsertLPPremSet = new LPPremSet();
		LPGetSet mInsertLPGetSet = new LPGetSet();

		mInsertLPDutySet.add(tReCalBL.aftLPDutySet);
		mInsertLPPremSet.add(tReCalBL.aftLPPremSet);
		mInsertLPGetSet.add(tReCalBL.aftLPGetSet);
		
		//职业加费需要重算，健康加费不需要重算
		LPPremSet tOccuAddFeePremSet = new LPPremSet();
		LPPremSchema tLPPremSchema = null;
		for (int i=1;i<=mInsertLPPremSet.size();i++) {
			//排除健康加费后的prem
			if (mInsertLPPremSet.get(i).getPayPlanType() != null && !mInsertLPPremSet.get(i).getPayPlanType().equals("01")) {
				tLPPremSchema = new LPPremSchema();
				PubFun.copySchema(tLPPremSchema, mInsertLPPremSet.get(i));
				tOccuAddFeePremSet.add(tLPPremSchema);
			}
		}
		
		LCDutyDB tLCDutyDB = new LCDutyDB();
		LCDutySet tLCDutySet = new LCDutySet();
		tLCDutyDB.setPolNo(aLPPolSchema.getPolNo());
		tLCDutySet = tLCDutyDB.query();
		if (tLCDutySet == null || tLCDutySet.size() < 1) {
			mErrors.addOneError("查询险种责任表失败!");
			return false;
		}
		LCDutyBLSet tLCDutyBLSet = new LCDutyBLSet();
		tLCDutyBLSet.set(tLCDutySet);
		
		// 重新计算加费金额
		AddPremReCalBQInterface tAddPremReCalBQInterface = new AddPremReCalBQInterface(mGlobalInput);
		LPPremSet afterLPPremSet = tAddPremReCalBQInterface.recalAddPrem(aLPPolSchema, tOccuAddFeePremSet,tLCDutyBLSet);
		if (afterLPPremSet == null || afterLPPremSet.size() == 0) {
			CError.buildErr(this, "重新计算加费金额失败");
			return false;
		}
		
		//将重算后的职业加费prem更新到mLPPremSet
		for (int i=1;i<=afterLPPremSet.size();i++) {
			if (afterLPPremSet.get(i).getPayPlanType() != null && afterLPPremSet.get(i).getPayPlanType().equals("02")) {
				for (int j = 1; j <= mInsertLPPremSet.size(); j++) {
					if (afterLPPremSet.get(i).getPolNo().equals(mInsertLPPremSet.get(j).getPolNo())
						&& afterLPPremSet.get(i).getDutyCode().equals(mInsertLPPremSet.get(j).getDutyCode())
						&& afterLPPremSet.get(i).getPayPlanCode().equals(mInsertLPPremSet.get(j).getPayPlanCode())) {
						//进行替换更新
						mInsertLPPremSet.set(j, afterLPPremSet.get(i));
					}
				}
			}
		}
		
		//将职业加费的变动反映到lcpol上
		aLPPolSchema.setPrem(aLPPolSchema.getPrem()+tAddPremReCalBQInterface.getAddFeeMinus());
		
		//将职业加费的变动反映到lcduty上
		for (int i = 1; i <= mInsertLPDutySet.size(); i++) {
			mInsertLPDutySet.get(i).setPrem(mInsertLPDutySet.get(i).getPrem()+tAddPremReCalBQInterface.getAddFeeMinus());
			mInsertLPDutySet.get(i).setModifyDate(PubFun.getCurrentDate());
			mInsertLPDutySet.get(i).setModifyTime(PubFun.getCurrentTime());			
		}

		if (fd.getDate(aEndDate).before(fd.getDate(aLPPolSchema.getEndDate()))) {
			aLPPolSchema.setEndDate(aEndDate);
			aLPPolSchema.setPayEndDate(aPayEndDate);
			aLPPolSchema.setPaytoDate(aPaytoDate);
			for (int p = 1; p <= mInsertLPDutySet.size(); p++) {
				mInsertLPDutySet.get(p).setEndDate(aEndDate);
				mInsertLPDutySet.get(p).setPayEndDate(aPayEndDate);
				mInsertLPDutySet.get(p).setPaytoDate(aPaytoDate);
			}
			for (int p = 1; p <= mInsertLPPremSet.size(); p++) {
				mInsertLPPremSet.get(p).setPayEndDate(aPayEndDate);
				mInsertLPPremSet.get(p).setPaytoDate(aPaytoDate);
			}
		}
		mLPDutySet.add(mInsertLPDutySet);
		mLPPremSet.add(mInsertLPPremSet);
		mLPGetSet.add(mInsertLPGetSet);
		
		LCDiscountSet tLCDiscountSet = new LCDiscountSet();
		LCDiscountDB tLCDiscountDB = new LCDiscountDB();
		tLCDiscountDB.setPolNo(aLPPolSchema.getPolNo());
		tLCDiscountSet = tLCDiscountDB.query();
		if(tLCDiscountSet!=null && tLCDiscountSet.size()>0){
			LPDiscountSet tLPDiscountSet = new LPDiscountSet();
			for(int i=1;i<=tLCDiscountSet.size();i++){
				LPDiscountSchema tLPDiscountSchema = new LPDiscountSchema();
				tRef.transFields(tLPDiscountSchema, tLCDiscountSet.get(i));
				tLPDiscountSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPDiscountSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPDiscountSet.add(tLPDiscountSchema);
			}
			mLPDiscountSet.add(tLPDiscountSet);
		}
		
		return true;
	}
}
