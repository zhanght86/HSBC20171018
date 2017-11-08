package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

/**
 * <p>Title: Lis </p>
 * <p>Description: Life Insurance System</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Sinosoft Co. Ltd.</p>
 * @author WUJS
 * @version 6.0
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.sinosoft.ibrms.bom.BOMAgent;
import com.sinosoft.ibrms.bom.BOMAppnt;
import com.sinosoft.ibrms.bom.BOMCont;
import com.sinosoft.ibrms.bom.BOMElement;
import com.sinosoft.ibrms.bom.BOMInsured;
import com.sinosoft.ibrms.bom.BOMPol;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpFeeDB;
import com.sinosoft.lis.db.LCGrpFeeParamDB;
import com.sinosoft.lis.db.LCGrpIvstPlanDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPerInvestPlanDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LMRiskAccPayDB;
import com.sinosoft.lis.db.LMRiskFeeDB;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGrpFeeParamSchema;
import com.sinosoft.lis.schema.LCGrpFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccClassFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeTraceSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LCPremToAccSchema;
import com.sinosoft.lis.schema.LMRiskAccPaySchema;
import com.sinosoft.lis.schema.LMRiskFeeSchema;
import com.sinosoft.lis.vschema.LCGrpFeeParamSet;
import com.sinosoft.lis.vschema.LCGrpFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeTraceSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCPremToAccSet;
import com.sinosoft.lis.vschema.LMRiskAccPaySet;
import com.sinosoft.lis.vschema.LMRiskFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

// import com.sinosoft.lis.f1print.AccountBillMoney;

public class CManageFee {
private static Logger logger = Logger.getLogger(CManageFee.class);
	/** 存放结果 */
	public VData mVResult = new VData();

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 管理费描述 */
	private com.sinosoft.lis.vschema.LCGrpFeeSet mLCGrpFeeSet;
	private List tBomList = new ArrayList();
	
	/** 保费项表 */
	private com.sinosoft.lis.vschema.LCPremSet mLCPremSet;

	/** 险种 */
	private com.sinosoft.lis.schema.LCPolSchema mLCPolSchema;

	/** 保险账户管理费分类表 */
	private com.sinosoft.lis.vschema.LCInsureAccClassFeeSet mLCInsureAccClassFeeSet;

	private LCInsureAccFeeSet mLCInsureAccFeeSet;
	private LCInsureAccFeeTraceSet mLCInsureAccFeeTraceSet;
	private LCInsureAccSet mLCInsureAccSet;
	private LCPremToAccSet mLCPremToAccSet;
	private LCInsureAccClassSet mInsureAccClassSet;
	private LCPremSchema mLCPremSchema=new LCPremSchema();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String theDate = "";
	// private LCGetToAccSet mLCGetToAccSet;
	private LCInsureAccTraceSet mLCInsureAccTraceSet;
	private String moutPayFlag = "0";
    private String mDutyCode = "";
    private int mAppAge = 0;
    private String mSuppRiskScore = "0";
    private Hashtable m_VPUInfo = new Hashtable(); //保存责任VPU值

	public CManageFee() {

	}

	/**
	 * 初始化
	 * 
	 * @param cInputData
	 *            VData
	 */
	private void Initialize(VData cInputData) {
		mInputData = (VData) cInputData;
		// mLCGrpFeeSet = (LCGrpFeeSet) mInputData.getObjectByObjectName(
		// "LCGrpFeeSet", 0);
		mLCPolSchema = (LCPolSchema) mInputData.getObjectByObjectName(
				"LCPolSchema", 0);
		mLCInsureAccClassFeeSet = (LCInsureAccClassFeeSet)mInputData.getObjectByObjectName("LCInsureAccClassFeeSet", 0);
		mLCInsureAccFeeTraceSet = (LCInsureAccFeeTraceSet) mInputData
				.getObjectByObjectName("LCInsureAccFeeTraceSet", 0);
		if (mLCInsureAccFeeTraceSet == null) {
			mLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
		}
		mLCInsureAccFeeSet = (LCInsureAccFeeSet) mInputData
				.getObjectByObjectName("LCInsureAccFeeSet", 0);
		mLCPremSet = (LCPremSet) mInputData.getObjectByObjectName("LCPremSet",
				0);
		// 得到生成的保险帐户表
		mLCInsureAccSet = (LCInsureAccSet) (mInputData.getObjectByObjectName(
				"LCInsureAccSet", 0));

		// 得到生成的缴费帐户关联表
		mLCPremToAccSet = (LCPremToAccSet) (mInputData.getObjectByObjectName(
				"LCPremToAccSet", 0));

		mInsureAccClassSet = (LCInsureAccClassSet) mInputData
				.getObjectByObjectName("LCInsureAccClassSet", 0);

		// 得到领取帐户关联表--目前不用
		// mLCGetToAccSet = (LCGetToAccSet) (mInputData
		// .getObjectByObjectName(
		// "LCGetToAccSet",
		// 0));
		mLCInsureAccTraceSet = (LCInsureAccTraceSet) mInputData
				.getObjectByObjectName("LCInsureAccTraceSet", 0);

		// if ( mLCInsureAccTraceSet==null ) mLCInsureAccTraceSet = new
		// LCInsureAccTraceSet();
		if (mLCPolSchema == null) {
			CError.buildErr(this, "传入保单信息不能为空");
			// return false;
		} else {
			LCGrpFeeDB tLCGrpFeeDB = new LCGrpFeeDB();
			tLCGrpFeeDB.setGrpPolNo(mLCPolSchema.getGrpPolNo());
			tLCGrpFeeDB.setRiskCode(mLCPolSchema.getRiskCode());
			mLCGrpFeeSet = tLCGrpFeeDB.query();
		}

        String tSQL = "";
        ExeSQL tExeSQL = new ExeSQL();

        tSQL = "select max(suppriskscore) from lcprem a where a.contno='"
        	 + "?q1?" + "'"
        	 + " and a.polno='" + "?q2?" + "'"
        	 + " and exists(select 'X' from lcduty "
        	 + "where dutycode=a.dutycode and polno=a.polno and payenddate=a.payenddate)";
        SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
        sqlbv1.sql(tSQL);
        sqlbv1.put("q1", mLCPolSchema.getContNo());
        sqlbv1.put("q2", mLCPolSchema.getProposalNo());
        mSuppRiskScore = tExeSQL.getOneValue(sqlbv1);
        if (mSuppRiskScore == null || "".equals(mSuppRiskScore)) {
        	mSuppRiskScore = "0";
        }

        mAppAge = PubFun.calAppAge(mLCPolSchema.getInsuredBirthday(),
				mLCPolSchema.getCValiDate(), "Y");
	}
	public VData getResult() {
		// mInputData.add(mLCInsureAccTraceSet);
		// return mInputData;
		return mInputData;
	}

	/**
	 * 数据校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {

		if (mInputData == null) {
			logger.debug("请先调用初始化函数:Initialize");
			CError.buildErr(this, "请先调用初始化函数:Initialize");
			return false;
		}
		if (mLCPolSchema == null || mLCInsureAccClassFeeSet == null) {
			CError.buildErr(this, "初始化信息不正确!");
			return false;
		}

		if (mLCPremSet == null) {
			CError.buildErr(this, "必须传入保费信息");
			return false;
		}

		if (mLCGrpFeeSet == null) {
			CError.buildErr(this, "初始化集体险种管理费描述信息");
			return false;
		}

		return true;

	}

	/**
	 * 从数据苦中查询保单对应需要处理账户的所有保费项
	 * 
	 * @param tLCPolSchema
	 *            LCPolSchema
	 * @return LCPremSet
	 */
	private LCPremSet queryLCPremSet(LCPolSchema tLCPolSchema) {

		LCPremSet tLCPremSet = new LCPremSet();
		LCPremDB tLCPremDB = new LCPremDB();
		tLCPremDB.setPolNo(tLCPolSchema.getPolNo());
		tLCPremDB.setNeedAcc("1");
		tLCPremSet = tLCPremDB.query();
		if (tLCPremDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPremDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLCPrem";
			tError.errorMessage = "保费项表查询失败!";
			this.mErrors.addOneError(tError);
			tLCPremSet.clear();

			return null;
		}
		if (tLCPremSet.size() == 0) {
			return null;
		}

		return tLCPremSet;

	}

	/**
	 * 查询匹配的保费项
	 * 
	 * @param payPlanCode
	 *            String
	 * @return LCPremSchema
	 */
	private LCPremSchema getLCPremSchema(String payPlanCode) {
		if (mLCPremSet == null) {
			CError.buildErr(this, "没有保费项信息!");
			return null;
		}
		for (int i = 1; i <= mLCPremSet.size(); i++) {
			if (mLCPremSet.get(i).getPayPlanCode().equals(payPlanCode)) {
				return mLCPremSet.get(i);
			}
		}
		return null;
	}

	/**
	 * 查询匹配的保险账户管理费分类表
	 * 
	 * @param InsureAccNo
	 *            String
	 * @param payPlanCode
	 *            String
	 * @return LCInsureAccClassFeeSchema
	 */
    private LCInsureAccClassFeeSchema getLCInsureAccClassFee(String InsureAccNo,
        String payPlanCode,String cFeeCode) {
        LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema = null;
        for (int i = 1; i <= mLCInsureAccClassFeeSet.size(); i++) {
            tLCInsureAccClassFeeSchema = mLCInsureAccClassFeeSet.get(i);
            if (tLCInsureAccClassFeeSchema.getInsuAccNo().equals(InsureAccNo)
                &&
                tLCInsureAccClassFeeSchema.getPayPlanCode().equals(payPlanCode)
                &&
                tLCInsureAccClassFeeSchema.getFeeCode().equals(cFeeCode)) {
                break;
            }

        }
        return tLCInsureAccClassFeeSchema;
    }
    /**
     * 查询匹配的保险账户管理费分类表
     * @param InsureAccNo String
     * @param payPlanCode String
     * @return LCInsureAccClassFeeSchema
     */
    private LCInsureAccClassFeeSchema getLCInsureAccClassFee(String InsureAccNo,
        String payPlanCode) {
        LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema = null;
        for (int i = 1; i <= mLCInsureAccClassFeeSet.size(); i++) {
            tLCInsureAccClassFeeSchema = mLCInsureAccClassFeeSet.get(i);
            if (tLCInsureAccClassFeeSchema.getInsuAccNo().equals(InsureAccNo)
                &&
                tLCInsureAccClassFeeSchema.getPayPlanCode().equals(payPlanCode)) {
                break;
            }

        }
        return tLCInsureAccClassFeeSchema;
    }

	/**
	 * 查询匹配的保险账户管理费分类表
	 * 
	 * @param InsureAccNo
	 *            String
	 * @param payPlanCode
	 *            String
	 * @return LCInsureAccClassFeeSchema
	 */
	private LCInsureAccFeeSchema getLCInsureAccFee(String InsureAccNo) {
		LCInsureAccFeeSchema tLCInsureAccFeeSchema = null;
		for (int i = 1; i <= mLCInsureAccFeeSet.size(); i++) {
			tLCInsureAccFeeSchema = mLCInsureAccFeeSet.get(i);
			if (tLCInsureAccFeeSchema.getInsuAccNo().equals(InsureAccNo)) {
				break;
			}

		}
		return tLCInsureAccFeeSchema;
	}

	/**
	 * 查询团单管理费计算参数
	 * 
	 * @param InsureAccNo
	 *            String
	 * @param payPlanCode
	 *            String
	 * @param feebase
	 *            double
	 * @return LCGrpFeeParamSchema
	 */
	private LCGrpFeeParamSchema queryLCGrpFeeParamSchema(String InsureAccNo,
			String payPlanCode, double feebase) {
		// String sql = "select * from LCGrpFeeParam where ";
		StringBuffer sb = new StringBuffer();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sb.append(" select * from LCGrpFeeParam where ");
		sb.append("GrpPolNo='").append("?GrpPolNo?").append("'");
		sb.append(" and InsuAccNo='").append("?InsureAccNo?").append("'");
		sb.append(" and PayPlanCode='").append("?payPlanCode?").append("'");
		sb.append(" and FeeMin<").append("?feebase?");
		sb.append(" and feemax>").append("?feebase?");
		sqlbv.sql(sb.toString());
		sqlbv.put("GrpPolNo", mLCPolSchema.getGrpPolNo());
		sqlbv.put("InsureAccNo", InsureAccNo);
		sqlbv.put("payPlanCode", payPlanCode);
		sqlbv.put("feebase", feebase);
		LCGrpFeeParamDB tLCGrpFeeParamDB = new LCGrpFeeParamDB();
		LCGrpFeeParamSet tLCGrpFeeParamSet = tLCGrpFeeParamDB.executeQuery(sqlbv);
		if (tLCGrpFeeParamDB.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLCGrpFeeParamDB.mErrors);
			return null;
		}
		if (tLCGrpFeeParamSet == null || tLCGrpFeeParamSet.size() != 1) {
			CError.buildErr(this, "参数表描述取值不唯一!");
			return null;
		}

		return tLCGrpFeeParamSet.get(1);

	}

	/**
	 * 计算管理费内扣费率
	 * 
	 * @param manaFee
	 *            double
	 * @param prem
	 *            double
	 * @return double
	 */
	private double calInnerRate(double manaFee, double prem) {
		return manaFee / prem;
	}

	/**
	 * 计算管理费外缴费率
	 * 
	 * @param manaFee
	 *            double
	 * @param prem
	 *            double
	 * @return double
	 */
	private double calOutRate(double manaFee, double prem) {
		return manaFee / (prem - manaFee);
	}

	/**
	 * 计算管理费 － 内扣
	 * 
	 * @param prem
	 *            double
	 * @param rate
	 *            double
	 * @return double
	 */
	private double calInnerManaFee(double prem, double rate) {
		return prem * rate;
	}

	/**
	 * 计算管理费 -外缴
	 * 
	 * @param prem
	 *            double
	 * @param rate
	 *            double
	 * @return double
	 */
	private double calOutManaFee(double prem, double rate) {
		return (prem * rate) / (1 + rate);
	}

	/**
	 * 固定值和比例结合，取较小值
	 * 
	 * @param calMode
	 *            String
	 * @param basePrem
	 *            double
	 * @param fixValue
	 *            double
	 * @param rate
	 *            double
	 * @param refValue
	 *            double
	 * @return double
	 */
	private double calManaFeeMinRate(double basePrem, double fixValue,
			double rate) {
		double manaFee = 0;
		manaFee = this.calInnerManaFee(basePrem, rate);
		if (fixValue < manaFee) {
			return fixValue;
		}
		return manaFee;
	}

	/**
	 * 固定值和比例结合，取较大值
	 * 
	 * @param calMode
	 *            String
	 * @param basePrem
	 *            double
	 * @param fixValue
	 *            double
	 * @param rate
	 *            double
	 * @param refValue
	 *            double
	 * @return double
	 */
	private double calManaFeeMaxRate(double basePrem, double fixValue,
			double rate) {
		double manaFee = 0;
		manaFee = this.calInnerManaFee(basePrem, rate);
		if (manaFee < fixValue) {
			return fixValue;
		}
		return manaFee;
	}

    /**
     * @param inVData       使用了 createInsureAcc()方法后，得到的VData数据
     * @param pLCPremSet    保费项集合
     * @param AccCreatePos  参见 险种保险帐户缴费 LMRiskAccPay
     * @param OtherNo       参见 保险帐户表 LCInsureAcc
     * @param OtherNoType   号码类型
     * @param MoneyType     参见 保险帐户表记价履历表 LCInsureAccTrace
     * @param Rate          费率
     * @return VData(tLCInsureAccSet:update or insert ,tLCInsureAccTraceSet: insert)
     */
    public boolean calPremManaFee(VData tVData, String AccCreatePos,
                                  String OtherNo,double Amnt,
                                  String OtherNoType, String MoneyType,String cTakePlace
        ) {
        if ( (tVData == null)
            || (AccCreatePos == null)
            || (OtherNo == null)
            || (OtherNoType == null)
            || (MoneyType == null)
            || (cTakePlace == null)) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "DealAccount";
            tError.functionName = "addPrem";
            tError.errorMessage = "传入数据不能为空";
            this.mErrors.addOneError(tError);

            return false;
        }
        this.Initialize(tVData);
        if (!checkData()) {
            return false;
        }

        double tBaseAmnt = Amnt;

        LCInsureAccClassSchema tClassSchema = null;

        for (int n = 1; n <= mLCPremSet.size(); n++) {
            LCPremSchema tLCPremSchema = mLCPremSet.get(n);

            //判断是否帐户相关
            if (!"1".equals(tLCPremSchema.getNeedAcc())) {
                continue;
            }

            for (int m = 1; m <= mLCPremToAccSet.size(); m++) {
                LCPremToAccSchema tLCPremToAccSchema = mLCPremToAccSet.get(
                    m);
                mDutyCode = tLCPremToAccSchema.getDutyCode();

                //如果当前保费项和当前的缴费帐户关联表的保单号，责任编码，交费计划编码相同
                if (!tLCPremSchema.getPolNo().equals(tLCPremToAccSchema
                    .getPolNo())
                    ||
                    !tLCPremSchema.getDutyCode().equals(tLCPremToAccSchema
                    .getDutyCode())
                    ||
                    !tLCPremSchema.getPayPlanCode().equals(
                        tLCPremToAccSchema
                        .getPayPlanCode())) {
                    continue;
                }

                //只有注入比例大于0的时候才注入账户金额,否则不做
                if (tLCPremToAccSchema.getRate() <= 0) {
                    continue;
                }
                double inputMoney = 0;
                double tInitManaFee = 0;
                double baseMoney = tLCPremSchema.getPrem() *
                    tLCPremToAccSchema.getRate();

                //直接计算，不需要管理费
                if (tLCPremToAccSchema.getCalFlag() == null
                    || "0".equals(tLCPremToAccSchema.getCalFlag())) {
                    inputMoney = baseMoney;

                }
                else {
                    if ("1".equals(tLCPremToAccSchema.getCalFlag())) {
                    	tInitManaFee = computeManaFee(baseMoney, tLCPremToAccSchema);
                    }
                    if (tInitManaFee < 0) {
                        CError.buildErr(this, "管理费计算失败!");
                        return false;
                    }

                }

                /*对投资计划的处理*/
                double rate = getInvestRate(mLCPolSchema, tLCPremToAccSchema.getInsuAccNo(),
                                            tLCPremToAccSchema.getPayPlanCode());
                // baseMoney = baseMoney * rate;


                //计算初始管理费
                if (tInitManaFee == 0)
                {
                	tInitManaFee = computeManaFee(tLCPremToAccSchema.getInsuAccNo(),
                                             tLCPremToAccSchema.getPayPlanCode(),
                                             inputMoney,tBaseAmnt,cTakePlace,rate);
                }

                PubFun.setPrecision(tInitManaFee, "0.00");
                inputMoney = tLCPremSchema.getPrem()*rate - tInitManaFee;
                if (inputMoney < 0) {
                    CError.buildErr(this, "管理费计算错误,不能大于保费");
                    return false;
                }
                PubFun.setPrecision(inputMoney, "0.00");

                //计算生效对应日产生的管理费
                double tCvalManaFee = 0;
                tCvalManaFee = computeManaFee(tLCPremToAccSchema.getInsuAccNo(),
                        tLCPremToAccSchema.getPayPlanCode(),inputMoney,tBaseAmnt,"03");

                if (tCvalManaFee < 0) {
                    CError.buildErr(this, "管理费计算错误,不能大于保费");
                    return false;
                }
                PubFun.setPrecision(tCvalManaFee, "0.00");

                double tManaFee = 0;
                tManaFee = tInitManaFee+tCvalManaFee;
                PubFun.setPrecision(tManaFee, "0.00");

                double tAccValue = inputMoney - tCvalManaFee;;

                //更新管理费账户表
                updateLCInsureAccFee(tLCPremToAccSchema.getInsuAccNo(),tManaFee);

                //更新账户分类表
                updateLCInsuerAccClass(tClassSchema, tAccValue, tLCPremSchema,
                                       tLCPremToAccSchema);
                //更新账户表
                updateInsureAcc(OtherNo, OtherNoType, MoneyType, inputMoney,tAccValue,
                                tLCPremSchema, tLCPremToAccSchema);



            }
        }

        return true;
    }
	/**
	 * 该方法有缺陷，不能用于签单程序，因为传入的数据中的得到的是保单号，可是库中的数据是 尚未签单的数据，只有投保单号。 保险账户资金注入(类型3
	 * 针对保费项,注意没有给出注入资金，内部会调用计算金额的函数) 适用于：在生成帐户结构后，此时数据尚未提交到数据库，又需要执行帐户的资金注入。
	 * 即在使用了 createInsureAcc()方法后，得到VData数据，接着修改VData中帐户的金额
	 * 
	 * @param inVData
	 *            使用了 createInsureAcc()方法后，得到的VData数据
	 * @param pLCPremSet
	 *            保费项集合
	 * @param AccCreatePos
	 *            参见 险种保险帐户缴费 LMRiskAccPay
	 * @param OtherNo
	 *            参见 保险帐户表 LCInsureAcc
	 * @param OtherNoType
	 *            号码类型
	 * @param MoneyType
	 *            参见 保险帐户表记价履历表 LCInsureAccTrace
	 * @param Rate
	 *            费率
	 * @return VData(tLCInsureAccSet:update or insert ,tLCInsureAccTraceSet:
	 *         insert)
	 */
	public boolean NewcalPremManaFee(VData tVData, String AccCreatePos,
			String OtherNo, String OtherNoType, String MoneyType,
			String mUsePubAcc) {
		if ((tVData == null) || (AccCreatePos == null) || (OtherNo == null)
				|| (OtherNoType == null) || (MoneyType == null)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "addPrem";
			tError.errorMessage = "传入数据不能为空";
			this.mErrors.addOneError(tError);

			return false;
		}
		this.Initialize(tVData);
		if (!checkData()) {
			return false;
		}

		LCInsureAccClassSchema tClassSchema = null;

		for (int n = 1; n <= mLCPremSet.size(); n++) {
			LCPremSchema tLCPremSchema = mLCPremSet.get(n);

			// 判断是否帐户相关
			if (!"1".equals(tLCPremSchema.getNeedAcc())) {
				continue;
			}

			for (int m = 1; m <= mLCPremToAccSet.size(); m++) {
				LCPremToAccSchema tLCPremToAccSchema = mLCPremToAccSet.get(m);

				// 如果当前保费项和当前的缴费帐户关联表的保单号，责任编码，交费计划编码相同
				if (!tLCPremSchema.getPolNo().equals(
						tLCPremToAccSchema.getPolNo())
						|| !tLCPremSchema.getDutyCode().equals(
								tLCPremToAccSchema.getDutyCode())
						|| !tLCPremSchema.getPayPlanCode().equals(
								tLCPremToAccSchema.getPayPlanCode())) {
					continue;
				}

				// 只有注入比例大于0的时候才注入账户金额,否则不做
				if (tLCPremToAccSchema.getRate() <= 0) {
					continue;
				}
				double inputMoney = 0;
				double manaFee = 0;
				double baseMoney = tLCPremSchema.getPrem()
						* tLCPremToAccSchema.getRate();
				logger.debug("baseMOney ====帐户===" + baseMoney);
				// 直接计算，不需要管理费
				if (tLCPremToAccSchema.getCalFlag() == null
						|| "0".equals(tLCPremToAccSchema.getCalFlag())) {
					inputMoney = baseMoney;

				} else {
					if ("1".equals(tLCPremToAccSchema.getCalFlag())) {
						if (mUsePubAcc.equals("N")) {
							manaFee = Arith.round(computeManaFee(baseMoney,
									tLCPremToAccSchema), 2);
						} else {
							manaFee = 0;
						}
					}
					if (manaFee < 0) {
						CError.buildErr(this, "管理费计算失败!");
						return false;
					}

				}

				// 如果是外缴这的INPUTMONEY不用减少。
				if (moutPayFlag.equals("1")) {
					inputMoney = Arith.round(tLCPremSchema.getPrem(), 2);
				} else {
					inputMoney = Arith.round(tLCPremSchema.getPrem() - manaFee,
							2);
				}

				if (inputMoney < 0) {
					CError.buildErr(this, "管理费计算错误,不能大于保费");
					return false;
				}
				// 更新管理费明细表
				// 更新管理费账户表
				updateLCInsureAccFee(tLCPremToAccSchema.getInsuAccNo(), manaFee);
				// 更新账户分类表
				updateLCInsuerAccClass(tClassSchema, inputMoney, tLCPremSchema,
						tLCPremToAccSchema);
				// 更新账户表
				updateInsureAcc(OtherNo, OtherNoType, MoneyType, inputMoney,
						tLCPremSchema, tLCPremToAccSchema);

			}
		}

		return true;

	}

	/**
	 * 该方法有缺陷，不能用于签单程序，因为传入的数据中的得到的是保单号，可是库中的数据是 尚未签单的数据，只有投保单号。 保险账户资金注入(类型3
	 * 针对保费项,注意没有给出注入资金，内部会调用计算金额的函数) 适用于：在生成帐户结构后，此时数据尚未提交到数据库，又需要执行帐户的资金注入。
	 * 即在使用了 createInsureAcc()方法后，得到VData数据，接着修改VData中帐户的金额
	 * 
	 * @param inVData
	 *            使用了 createInsureAcc()方法后，得到的VData数据
	 * @param pLCPremSet
	 *            保费项集合
	 * @param AccCreatePos
	 *            参见 险种保险帐户缴费 LMRiskAccPay
	 * @param OtherNo
	 *            参见 保险帐户表 LCInsureAcc
	 * @param OtherNoType
	 *            号码类型
	 * @param MoneyType
	 *            参见 保险帐户表记价履历表 LCInsureAccTrace
	 * @param Rate
	 *            费率
	 * @return VData(tLCInsureAccSet:update or insert ,tLCInsureAccTraceSet:
	 *         insert)
	 */
	public boolean calPremManaFee(VData tVData, String AccCreatePos,
			String OtherNo, String OtherNoType, String MoneyType) {
		if ((tVData == null) || (AccCreatePos == null) || (OtherNo == null)
				|| (OtherNoType == null) || (MoneyType == null)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "addPrem";
			tError.errorMessage = "传入数据不能为空";
			this.mErrors.addOneError(tError);

			return false;
		}
		this.Initialize(tVData);
		if (!checkData()) {
			return false;
		}

		LCInsureAccClassSchema tClassSchema = null;

		for (int n = 1; n <= mLCPremSet.size(); n++) {
			LCPremSchema tLCPremSchema = mLCPremSet.get(n);
			mLCPremSchema=tLCPremSchema;
			// 判断是否帐户相关
			if (!"1".equals(tLCPremSchema.getNeedAcc())) {
				continue;
			}

			for (int m = 1; m <= mLCPremToAccSet.size(); m++) {
				LCPremToAccSchema tLCPremToAccSchema = mLCPremToAccSet.get(m);

				// 如果当前保费项和当前的缴费帐户关联表的保单号，责任编码，交费计划编码相同
				if (!tLCPremSchema.getPolNo().equals(
						tLCPremToAccSchema.getPolNo())
						|| !tLCPremSchema.getDutyCode().equals(
								tLCPremToAccSchema.getDutyCode())
						|| !tLCPremSchema.getPayPlanCode().equals(
								tLCPremToAccSchema.getPayPlanCode())) {
					continue;
				}

				// 只有注入比例大于0的时候才注入账户金额,否则不做
				if (tLCPremToAccSchema.getRate() <= 0) {
					continue;
				}
				double inputMoney = 0;
				double manaFee = 0;

				double baseMoney = tLCPremSchema.getPrem()
						* tLCPremToAccSchema.getRate();
         /*对投资计划的处理*/
            double rate = getInvestRate(mLCPolSchema, tLCPremToAccSchema.getInsuAccNo(),
                                        tLCPremToAccSchema.getPayPlanCode());
            baseMoney = baseMoney * rate;
				// 直接计算，不需要管理费
				if (tLCPremToAccSchema.getCalFlag() == null
						|| "0".equals(tLCPremToAccSchema.getCalFlag())) {
					inputMoney = baseMoney;

				} else {
					// 万能险不走此处。
					// 此处暂时是给团先使用的。
					if ("1".equals(tLCPremToAccSchema.getCalFlag())) {
						manaFee = computeManaFee(baseMoney, tLCPremToAccSchema);
					}
					if (manaFee < 0) {
						CError.buildErr(this, "管理费计算失败!");
						return false;
					}
					inputMoney = baseMoney;

				}

				
				double tFeeValue = 0; // 管理费比例
				if (manaFee == 0) {
					manaFee = computeManaFee(tLCPremToAccSchema.getInsuAccNo(),
							tLCPremToAccSchema.getPayPlanCode(), inputMoney);
				}
				
				PubFun.setPrecision(manaFee, "0.00");
				inputMoney = inputMoney - manaFee;
				// inputMoney ： 帐户最终注入资金。
				if (inputMoney < 0) {
					CError.buildErr(this, "管理费计算错误,不能大于保费");
					return false;
				}
				PubFun.setPrecision(inputMoney, "0.00");
				// 更新管理费账户表
				updateLCInsureAccFee(tLCPremToAccSchema.getInsuAccNo(), manaFee);
				// 更新管理费分类表账户表
				updateLCInsureAccClassFee(tLCPremToAccSchema.getInsuAccNo(),
						manaFee, tLCPremToAccSchema.getPayPlanCode());
				// 更新账户分类表
				updateLCInsuerAccClass(tClassSchema, inputMoney, tLCPremSchema,
						tLCPremToAccSchema);
				// 更新账户表
				// tongmeng 2007-09-27 modify

				updateInsureAcc(OtherNo, OtherNoType, MoneyType, inputMoney,
						tLCPremSchema, tLCPremToAccSchema);
			}
		}

		return true;
	}

	/**
	 * 计算账户管理费
	 * 
	 * @param tLCInsureAccClassSChema
	 *            LCInsureAccClassSchema
	 * @param tLCGrpFeeSchema
	 *            LCGrpFeeSchema
	 * @param calFeeDate
	 *            String 计算管理费的日期
	 * @return boolean
	 */
	/*
	 * public boolean calAccFee(LCInsureAccClassSchema tLCInsureAccClassSchema,
	 * LCInsureAccTraceSchema tLCInsureAccTraceSchema, LCGrpFeeSchema
	 * tLCGrpFeeSchema, String calFeeDate) { AccountBillMoney tAccountBillMoney =
	 * new AccountBillMoney(); double baseMoney =
	 * tAccountBillMoney.getBalance(tLCInsureAccClassSchema, calFeeDate, "1");
	 * //得到账户余额+利息. double manaFee = this.computeAccFee(baseMoney,
	 * tLCGrpFeeSchema); //计算账户管理费 if (manaFee > 0) {
	 * tLCInsureAccTraceSchema.setSchema(createAccTrace("GL", -manaFee,
	 * tLCInsureAccClassSchema));
	 * tLCInsureAccClassSchema.setInsuAccBala(tLCInsureAccClassSchema.
	 * getInsuAccBala() - manaFee);
	 * tLCInsureAccClassSchema.setSumPay(tLCInsureAccClassSchema.getSumPay() -
	 * manaFee); } return true; }
	 */

	/**
	 * 计算账户的管理费
	 * 
	 * @param baseMoney
	 *            double
	 * @param LCGrpFeeSchema
	 *            tFeeSchema
	 * @return double
	 */
	private double computeAccFee(double baseMoney, LCGrpFeeSchema tFeeSchema) {
		double manaFee = 0;
		double manaFeeRate = 0;

		String calMode = tFeeSchema.getFeeCalMode();

		if (calMode == null || "01".equals(calMode)) { // 内扣固定值
			manaFee = tFeeSchema.getFeeValue();
			manaFeeRate = this.calInnerRate(manaFee, baseMoney);
		} else if (calMode.equals("02")) { // 内扣比例
			manaFeeRate = tFeeSchema.getFeeValue();
			manaFee = this.calInnerManaFee(baseMoney, manaFeeRate);

		} else if (calMode.equals("03")) { // 外缴-固定值
			manaFee = tFeeSchema.getFeeValue();
			manaFeeRate = this.calOutRate(manaFee, baseMoney);
		} else if (calMode.equals("04")) { // 外缴-比例值
			manaFeeRate = tFeeSchema.getFeeValue();
			manaFee = this.calOutManaFee(baseMoney, manaFeeRate);
		} else if (calMode.equals("05")) { // 固定值，比例计算后取较小值
			manaFeeRate = tFeeSchema.getFeeValue();
			manaFee = this.calManaFeeMinRate(baseMoney, tFeeSchema
					.getCompareValue(), manaFeeRate);
		} else if (calMode.equals("06")) { // 固定值，比例计算后取较大值
			manaFeeRate = tFeeSchema.getFeeValue();
			manaFee = this.calManaFeeMaxRate(baseMoney, tFeeSchema
					.getCompareValue(), manaFeeRate);
		} else if (calMode.equals("07")) { // 分档计算
			LCGrpFeeParamSchema tLCGrpFeeParamSchema = this
					.queryLCGrpFeeParamSchema(tFeeSchema.getInsuAccNo(),
							tFeeSchema.getPayPlanCode(), baseMoney);
			manaFeeRate = tLCGrpFeeParamSchema.getFeeRate();
			manaFee = this.calInnerManaFee(baseMoney, manaFeeRate);
		} else if (calMode.equals("08")) { // 累计分党计算
			// 需求不明确，尚未完成
			manaFeeRate = 0;
			manaFee = 0;
		}

		return manaFee;
	}
    /**
     * 计算管理费(万能险)
     * @param pLCInsureAccClassSchema 子帐户信息
     * @param pLMRiskFeeSchema  管理费描述信息
     * @return double
     */
    private double computeManaFee(String sInsuAccNo, String sPayPlanCode, double dSumPrem,double cBaseAmnt,String cTakePlace)
    {
        double dClassFee = 0.0;

        //查询管理费
        LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB();
        tLMRiskFeeDB.setInsuAccNo(sInsuAccNo);
        tLMRiskFeeDB.setPayPlanCode(sPayPlanCode);
        tLMRiskFeeDB.setFeeKind("03"); //03-个单管理费
        tLMRiskFeeDB.setFeeTakePlace(cTakePlace); //管理费产生位置
        LMRiskFeeSet tLMRiskFeeSet = tLMRiskFeeDB.query();
        if (tLMRiskFeeDB.mErrors.needDealError())
        {
            CError.buildErr(this, "账户管理费查询失败!");
            return -1;
        }
        if (tLMRiskFeeSet != null && tLMRiskFeeSet.size() > 0)
        {
            double dRiskFee;
            for (int k = 1; k <= tLMRiskFeeSet.size(); k++) //循环计算每条管理费
            {
                String tFeeCode = tLMRiskFeeSet.get(k).getFeeCode();
            	//计算管理费金额
                dRiskFee = calRiskFee(tLMRiskFeeSet.get(k), dSumPrem,cBaseAmnt);
                dRiskFee = PubFun.setPrecision(dRiskFee, "0.00");

                if (dRiskFee == -1)
                {
                    return -1;
                }

                LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema =
                    getLCInsureAccClassFee(sInsuAccNo, sPayPlanCode,tFeeCode);

                tLCInsureAccClassFeeSchema.setFee(PubFun.setPrecision(
                        tLCInsureAccClassFeeSchema.
                        getFee() + dRiskFee, "0.00"));

                //创建保险帐户管理费轨迹记表
                createFeeTrace(tLCInsureAccClassFeeSchema, dRiskFee, "GL", tLMRiskFeeSet.get(k).getFeeCode());

                dClassFee += dRiskFee;
            }
        }
        return dClassFee;
    }

    /**
     * 计算管理费(投连险)
     * @param pLCInsureAccClassSchema 子帐户信息
     * @param pLMRiskFeeSchema  管理费描述信息
     * @return double
     */
    private double computeManaFee(String sInsuAccNo, String sPayPlanCode, double dSumPrem,double cBaseAmnt,String cTakePlace,double cRate)
    {
        double dClassFee = 0.0;
		
//		准备计算信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLCPolSchema.getContNo());
		if(!tLCContDB.getInfo()){
			CError.buildErr(this, "查询合同信息失败！");
		}
		
		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(mLCPolSchema.getContNo());
		if(!tLCAppntDB.getInfo()){
			CError.buildErr(this, "查询投保人信息失败！");
		}
		
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mLCPolSchema.getAgentCode());
		if(!tLAAgentDB.getInfo()){
			CError.buildErr(this, "查询代理人信息失败！");
		}
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setInsuredNo(mLCPolSchema.getInsuredNo());
		if(!tLAAgentDB.getInfo()){
			CError.buildErr(this, "查询代理人信息失败！");
		}
//		准备BOMCont数据
		BOMCont cont = new BOMCont();
		cont = DealBOMCont(tLCContDB.getSchema());
		
		//准备被保人BOMAppnt数据
		BOMAppnt appnt = new BOMAppnt();		
		appnt = DealBOMAppnt(tLCAppntDB.getSchema());
		
		//准备代理人BOMAgent数据
		BOMAgent agent = new BOMAgent();
		agent = DealBOMAgent(tLAAgentDB.getSchema());
		
		//准备被保人BOMInsured数据
		BOMInsured insured = new BOMInsured();
		insured = DealBOMInsured(tLCInsuredDB.getSchema());
		
		//准备险种BOMPol数据
		BOMPol pol = new BOMPol();//一个险种
		pol = DealBOMPol(mLCPolSchema,dSumPrem,cBaseAmnt);
		
		//准备受益人BOMBnf数据
		BOMElement element = new BOMElement();
		tBomList.add(cont);
		tBomList.add(appnt);
		tBomList.add(agent);
		tBomList.add(insured);
		tBomList.add(pol);

        //查询管理费
        LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB();
        tLMRiskFeeDB.setInsuAccNo(sInsuAccNo);
        tLMRiskFeeDB.setPayPlanCode(sPayPlanCode);
        tLMRiskFeeDB.setFeeKind("03"); //03-个单管理费
        tLMRiskFeeDB.setFeeTakePlace(cTakePlace); //管理费产生位置
        LMRiskFeeSet tLMRiskFeeSet = tLMRiskFeeDB.query();
        if (tLMRiskFeeDB.mErrors.needDealError())
        {
            CError.buildErr(this, "账户管理费查询失败!");
            return -1;
        }
        if (tLMRiskFeeSet != null && tLMRiskFeeSet.size() > 0)
        {
            double dRiskFee;
            for (int k = 1; k <= tLMRiskFeeSet.size(); k++) //循环计算每条管理费
            {
                String tFeeCode = tLMRiskFeeSet.get(k).getFeeCode();
                //计算管理费金额
                dRiskFee = cRate*calRiskFee(tLMRiskFeeSet.get(k), dSumPrem,cBaseAmnt);
                dRiskFee = PubFun.setPrecision(dRiskFee, "0.00");

                if (dRiskFee == -1)
                {
                    return -1;
                }

                LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema =
                    getLCInsureAccClassFee(sInsuAccNo, sPayPlanCode,tFeeCode);

                tLCInsureAccClassFeeSchema.setFee(PubFun.setPrecision(
                        tLCInsureAccClassFeeSchema.
                        getFee() + dRiskFee, "0.00"));

                //创建保险帐户管理费轨迹记表
                createFeeTrace(tLCInsureAccClassFeeSchema, dRiskFee, "GL", tLMRiskFeeSet.get(k).getFeeCode());

                dClassFee += dRiskFee;
            }
        }
        return dClassFee;
    }

	/**
	 * 计算管理费(万能险)
	 * 
	 * @param pLCInsureAccClassSchema
	 *            子帐户信息
	 * @param pLMRiskFeeSchema
	 *            管理费描述信息
	 * @return double
	 */
	private double computeManaFee(String sInsuAccNo, String sPayPlanCode,
			double dSumPrem) {
		double dClassFee = 0.0;

		// 查询管理费
		LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB();
		tLMRiskFeeDB.setInsuAccNo(sInsuAccNo);
		tLMRiskFeeDB.setPayPlanCode(sPayPlanCode);
		tLMRiskFeeDB.setFeeKind("03"); // 03-个单管理费
		tLMRiskFeeDB.setFeeTakePlace("01"); // 01－交费时
		LMRiskFeeSet tLMRiskFeeSet = tLMRiskFeeDB.query();
		if (tLMRiskFeeDB.mErrors.needDealError()) {
			CError.buildErr(this, "账户管理费查询失败!");
			return -1;
		}
		if (tLMRiskFeeSet != null && tLMRiskFeeSet.size() > 0) {
			
//			准备计算信息
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(mLCPolSchema.getContNo());
			if(!tLCContDB.getInfo()){
				CError.buildErr(this, "查询合同信息失败！");
			}
			
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCPolSchema.getContNo());
			if(!tLCAppntDB.getInfo()){
				CError.buildErr(this, "查询投保人信息失败！");
			}
			
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mLCPolSchema.getAgentCode());
			if(!tLAAgentDB.getInfo()){
				CError.buildErr(this, "查询代理人信息失败！");
			}
			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			tLCInsuredDB.setInsuredNo(mLCPolSchema.getInsuredNo());
			if(!tLAAgentDB.getInfo()){
				CError.buildErr(this, "查询代理人信息失败！");
			}
//			准备BOMCont数据
			BOMCont cont = new BOMCont();
			cont = DealBOMCont(tLCContDB.getSchema());
			
			//准备被保人BOMAppnt数据
			BOMAppnt appnt = new BOMAppnt();		
			appnt = DealBOMAppnt(tLCAppntDB.getSchema());
			
			//准备代理人BOMAgent数据
			BOMAgent agent = new BOMAgent();
			agent = DealBOMAgent(tLAAgentDB.getSchema());
			
			//准备被保人BOMInsured数据
			BOMInsured insured = new BOMInsured();
			insured = DealBOMInsured(tLCInsuredDB.getSchema());
			
			//准备险种BOMPol数据
			BOMPol pol = new BOMPol();//一个险种
			pol = DealBOMPol(mLCPolSchema,dSumPrem,0.0);
			
			//准备受益人BOMBnf数据
			BOMElement element = new BOMElement();
			tBomList.add(cont);
			tBomList.add(appnt);
			tBomList.add(agent);
			tBomList.add(insured);
			tBomList.add(pol);
			
			double dRiskFee;
			for (int k = 1; k <= tLMRiskFeeSet.size(); k++) // 循环计算每条管理费
			{
				// 计算管理费金额
				dRiskFee = calRiskFee(tLMRiskFeeSet.get(k), dSumPrem);
				if (dRiskFee == -1) {
					return -1;
				}
				LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema = getLCInsureAccClassFee(
						sInsuAccNo, sPayPlanCode);

				// 创建保险帐户管理费轨迹记表
				createFeeTrace(tLCInsureAccClassFeeSchema, dRiskFee, "GL",
						tLMRiskFeeSet.get(k).getFeeCode());

				dClassFee += dRiskFee;
			}
		}
		return dClassFee;
	}
	private BOMCont DealBOMCont(LCContSchema tLCContSchema){
		BOMCont cont = new BOMCont();
		try{			
			cont.setSellType(tLCContSchema.getSellType());//出单方式	
			cont.setAutoPayFlag(tLCContSchema.getAutoPayFlag());
			cont.setBankAccNo(tLCContSchema.getBankAccNo());
			cont.setBankCode(tLCContSchema.getBankCode());
			cont.setInterval(1.00);
			String tPayIntv =String.valueOf(tLCContSchema.getPayIntv()) ;
			if(tPayIntv==null||tPayIntv.equals(""))
			{
				cont.setPayIntv("0");
			}
			else
			{
				cont.setPayIntv(tPayIntv);
			}
			cont.setAmnt(new Double(tLCContSchema.getAmnt()));
			cont.setAutoPayFlag(tLCContSchema.getAutoPayFlag());
			cont.setBankAccNo(tLCContSchema.getBankAccNo());
			cont.setBankCode(tLCContSchema.getBankCode());
			cont.setCardFlag(tLCContSchema.getCardFlag());
			cont.setCardFlag(tLCContSchema.getCardFlag());
			cont.setContNo(tLCContSchema.getContNo());			
			cont.setManageCom(tLCContSchema.getManageCom());			
			cont.setOutPayFlag(tLCContSchema.getOutPayFlag());			
		}catch(Exception e){
			CError.buildErr(this, "准备BOMCont时出错！");
			e.printStackTrace();
		}
		return cont;
	}
	
	private BOMAppnt DealBOMAppnt(LCAppntSchema tLCAppntSchema){
		BOMAppnt appnt = new BOMAppnt();
		ExeSQL tempExeSQL = new ExeSQL();
		try{			
			appnt.setAppntName(tLCAppntSchema.getAppntName());
			appnt.setAppntNo(tLCAppntSchema.getAppntNo());
			appnt.setAppntSex(tLCAppntSchema.getAppntSex());		
			
			appnt.setNationality(tLCAppntSchema.getNationality());
			appnt.setNativePlace(tLCAppntSchema.getNativePlace());
			appnt.setOccupationCode(tLCAppntSchema.getOccupationCode());
			appnt.setOccupationType(tLCAppntSchema.getOccupationType());
			appnt.setPosition(tLCAppntSchema.getPosition());
			appnt.setRelationToInsured(tLCAppntSchema.getRelationToInsured());
			appnt.setRgtAddress(tLCAppntSchema.getRgtAddress());
			appnt.setSalary(new Double(tLCAppntSchema.getSalary()));
			
		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return appnt;
	}
	
	private BOMAgent DealBOMAgent(LAAgentSchema tLAAgentSchema){
		BOMAgent agent = new BOMAgent();
		String tBlackList="select blacklisflag from latree where agentcode='"+"?w1?"+"'";
		   SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
	        sqlbv2.sql(tBlackList);
	        sqlbv2.put("w1", tLAAgentSchema.getAgentCode());
		ExeSQL tempExeSQL = new ExeSQL();
		String tBlackFlag = tempExeSQL.getOneValue(sqlbv2);
		agent.setAgentBlankFlag(tBlackFlag);//黑名单标记
		agent.setAgentCode(tLAAgentSchema.getAgentCode());
		agent.setAgentKind(tLAAgentSchema.getAgentKind());
		agent.setAgentState(tLAAgentSchema.getAgentState());
		agent.setBranchType(tLAAgentSchema.getBranchType());
		agent.setInsideFlag(tLAAgentSchema.getInsideFlag());
		agent.setManageCom(tLAAgentSchema.getManageCom());
		agent.setQuafNo(tLAAgentSchema.getQuafNo());
		agent.setSaleQuaf(tLAAgentSchema.getSaleQuaf());		
		return agent;
	}
	
	private BOMInsured DealBOMInsured(LCInsuredSchema tLCInsuredSchema){
		BOMInsured Insured = new BOMInsured();
		ExeSQL tempExeSQL = new ExeSQL();
		try{
			//参考AutoUWCheckBL.DealBOMInsured
			Insured.setInsuredNo(tLCInsuredSchema.getInsuredNo());
			
			String polApplyDateSql = "select PolApplyDate from lccont where contno='"+"?s3?"+"'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(polApplyDateSql);
			sqlbv1.put("s3", tLCInsuredSchema.getContNo());
			String tpolApplyDate = tempExeSQL.getOneValue(sqlbv1);
			int tInsAge = PubFun.calInterval(tLCInsuredSchema.getBirthday(), tpolApplyDate, "Y");
			Insured.setInsuredAppAge(Double.valueOf(String.valueOf(tInsAge)));//投保年龄
			Insured.setOccupationType(tLCInsuredSchema.getOccupationType());
			Insured.setOccupationCode(tLCInsuredSchema.getOccupationCode());
			Insured.setInsuredStat(tLCInsuredSchema.getInsuredStat());
			Insured.setMarriage(tLCInsuredSchema.getMarriage());
			Insured.setRelationToAppnt(tLCInsuredSchema.getRelationToAppnt());
			Insured.setSalary(Double.valueOf(String.valueOf(tLCInsuredSchema.getSalary())));
			Insured.setSex(tLCInsuredSchema.getSex());
			Insured.setEM(Double.valueOf(mSuppRiskScore));
		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return Insured;
	}
	
	private BOMPol DealBOMPol(LCPolSchema tLCPolSchema,double dSumPrem,double cBaseAmnt){
		BOMPol Pol = new BOMPol();
		ExeSQL tempExeSQL = new ExeSQL();
		try{
			if(cBaseAmnt>0)
			{
				Pol.setAmnt(Double.valueOf(cBaseAmnt));
			}
			Pol.setUWFlag(tLCPolSchema.getUWFlag());
			
			Pol.setPrem(new Double(mLCPremSchema.getPrem()));
			Pol.setAccValue(new Double(dSumPrem));
			Pol.setInsuredNo(tLCPolSchema.getInsuredNo());
			Pol.setMainPolNo(tLCPolSchema.getMainPolNo());
			Pol.setMult(new Double(tLCPolSchema.getMult()));
			Pol.setPayYears(new Double(tLCPolSchema.getPayYears()));
			Pol.setPolNo(tLCPolSchema.getPolNo());
			Pol.setInsuYear(Double.valueOf(String.valueOf(tLCPolSchema.getInsuYear())));
			Pol.setInsuYearFlag(tLCPolSchema.getInsuYearFlag());
			Pol.setCurrency(tLCPolSchema.getCurrency());
			Pol.setLiveGetMode(tLCPolSchema.getLiveGetMode());
			Pol.setBonusGetMode(tLCPolSchema.getBonusGetMode());
			Pol.setRiskCode(tLCPolSchema.getRiskCode());
			//终交年龄年期
			Pol.setPayEndYear(new Double(tLCPolSchema.getPayEndYear()));
			//终交年龄年期标志
			Pol.setPayEndYearFlag(tLCPolSchema.getPayEndYearFlag());
			//起领期间
			Pol.setGetYear(new Double(tLCPolSchema.getGetYear()));
			//起领期间单位
			Pol.setGetYearFlag(tLCPolSchema.getGetYearFlag());
			if(!(tLCPolSchema.getCValiDate()==null||"".equals(tLCPolSchema.getCValiDate()))){
				theDate=tLCPolSchema.getCValiDate()+" 00:00:00";
				Pol.setCValiDate(sdf.parse(theDate));
			}
			
		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return Pol;
	}
	
	/**
	 * 计算管理费(万能险)
	 * 
	 * @param pLCInsureAccClassSchema
	 *            子帐户信息
	 * @param pLMRiskFeeSchema
	 *            管理费描述信息
	 * @return double
	 */
	private double calRiskFee(LMRiskFeeSchema pLMRiskFeeSchema, double dSumPrem) {
		double dRiskFee = 0.0;
		if (pLMRiskFeeSchema.getFeeCalModeType().equals("0")) // 0-直接取值
		{
			if (pLMRiskFeeSchema.getFeeCalMode().equals("01")) // 固定值内扣
			{
				dRiskFee = pLMRiskFeeSchema.getFeeValue();
			} else if (pLMRiskFeeSchema.getFeeCalMode().equals("02")) // 固定比例内扣
			{
				dRiskFee = dSumPrem * pLMRiskFeeSchema.getFeeValue();
			} else {
				dRiskFee = pLMRiskFeeSchema.getFeeValue(); // 默认情况
			}
		} else if (pLMRiskFeeSchema.getFeeCalModeType().equals("1")) // 1-SQL算法描述
		{
			
			// 准备计算要素
			Calculator tCalculator = new Calculator();
			tCalculator.setBOMList(tBomList);
			tCalculator.setCalCode(pLMRiskFeeSchema.getFeeCalCode());
			// 累计已交保费
            tCalculator.addBasicFactor("InsuredNo", mLCPolSchema.getInsuredNo());
			tCalculator.addBasicFactor("SumPrem", String.valueOf(dSumPrem));
            tCalculator.addBasicFactor("AccValue", String.valueOf(dSumPrem));

            tCalculator.addBasicFactor("PayIntv", String.valueOf(mLCPolSchema.getPayIntv()));
            tCalculator.addBasicFactor("Interval", String.valueOf(1));
			String sCalResultValue = tCalculator.calculate();
			if (tCalculator.mErrors.needDealError()) {
				CError.buildErr(this, "管理费计算失败!");
				return -1;
			}

			try {
				dRiskFee = Double.parseDouble(sCalResultValue);
			} catch (Exception e) {
				CError.buildErr(this, "管理费计算结果错误!" + "错误结果：" + sCalResultValue);
				return -1;
			}
		}

		return dRiskFee;
	}

    /**
     * 计算管理费(万能险)
     * @param pLCInsureAccClassSchema 子帐户信息
     * @param pLMRiskFeeSchema  管理费描述信息
     * @return double
     */
    private double calRiskFee(LMRiskFeeSchema pLMRiskFeeSchema, double dSumPrem,double cBaseAmnt)
    {
        double dRiskFee = 0.0;
        if (pLMRiskFeeSchema.getFeeCalModeType().equals("0")) //0-直接取值
        {
            if (pLMRiskFeeSchema.getFeeCalMode().equals("01")) //固定值内扣
            {
                dRiskFee = pLMRiskFeeSchema.getFeeValue();
            }
            else if (pLMRiskFeeSchema.getFeeCalMode().equals("02")) //固定比例内扣
            {
                dRiskFee = dSumPrem * pLMRiskFeeSchema.getFeeValue();
            }
            else
            {
                dRiskFee = pLMRiskFeeSchema.getFeeValue(); //默认情况
            }
        }
        else if (pLMRiskFeeSchema.getFeeCalModeType().equals("1")) //1-SQL算法描述
        {
        	String tVPU = "0";

        	tVPU = this.getVPU(mDutyCode);

        	if (tVPU == null || "".equals(tVPU)) {
        		String tSQL = "";
            	ExeSQL tExeSQL = new ExeSQL();
            	tSQL = "select vpu from lmduty where dutycode='" + "?s4?" + "'";
            	SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
    			sqlbv1.sql(tSQL);
    			sqlbv1.put("s4", mDutyCode);
            	tVPU = tExeSQL.getOneValue(sqlbv1);
            	if (tVPU == null || "".equals(tVPU)) {
            		tVPU = "0";
            	}
            	this.setVPU(mDutyCode, tVPU);
        	}



        	//准备计算要素
            Calculator tCalculator = new Calculator();
            tCalculator.setCalCode(pLMRiskFeeSchema.getFeeCalCode());
            //累计已交保费
            tCalculator.addBasicFactor("InsuredNo", mLCPolSchema.getInsuredNo());
            tCalculator.addBasicFactor("SumPrem", String.valueOf(dSumPrem));
            tCalculator.addBasicFactor("AccValue", String.valueOf(dSumPrem));
            tCalculator.addBasicFactor("Amnt", String.valueOf(cBaseAmnt));
            tCalculator.addBasicFactor("SuppRiskScore", mSuppRiskScore);
            tCalculator.addBasicFactor("AppAge", String.valueOf(mAppAge));
            tCalculator.addBasicFactor("VPU", tVPU);
            tCalculator.addBasicFactor("Sex", mLCPolSchema.getInsuredSex());
            tCalculator.addBasicFactor("OccupationType",mLCPolSchema.getOccupationType());
            //投连管理费计算要素
            tCalculator.addBasicFactor("PayIntv", String.valueOf(mLCPolSchema.getPayIntv()));
            tCalculator.addBasicFactor("Interval", String.valueOf(1));
            String  sCalResultValue = tCalculator.calculate();
            if (tCalculator.mErrors.needDealError())
            {
                CError.buildErr(this, "管理费计算失败!");
                return -1;
            }

            try
            {
                dRiskFee = Double.parseDouble(sCalResultValue);
            }
            catch (Exception e)
            {
                CError.buildErr(this, "管理费计算结果错误!" +
                                "错误结果：" + sCalResultValue);
                return -1;
            }
        }

        return dRiskFee;
    }

	/**
	 * 计算管理费
	 * 
	 * @param tLCPremSchema
	 *            LCPremSchema
	 * @param tLCPremToAccSchema
	 *            LCPremToAccSchema
	 * @return boolean
	 */
	private double computeManaFee(double baseMoney,
			LCPremToAccSchema tLCPremToAccSchema) {
		double manaFee = 0;
		double manaFeeRate = 0;
		if (mLCGrpFeeSet == null) {
			logger.debug("目前只支持团单管理费计算");
			return 0;
		}
		// 需要计算管理非
		for (int t = 1; t <= mLCInsureAccClassFeeSet.size(); t++) {
			LCInsureAccClassFeeSchema tClsFeeSchema = mLCInsureAccClassFeeSet
					.get(t);
			// 查找相关的管理费账户分类表
			if (tClsFeeSchema.getPolNo().equals(tLCPremToAccSchema.getPolNo())
					&& tClsFeeSchema.getInsuAccNo().equals(
							tLCPremToAccSchema.getInsuAccNo())
					&& tClsFeeSchema.getPayPlanCode().equals(
							tLCPremToAccSchema.getPayPlanCode())) {
				// for (int u = 1; u <= mLCGrpFeeSet.size(); u++)
				// {
				// LCGrpFeeSchema tFeeSchema =
				// mLCGrpFeeSet.get(u);

				// 查找相关的管理费描述描述
				/*
				 * if (tFeeSchema.getGrpPolNo().equals(
				 * tClsFeeSchema.getGrpPolNo()) && tFeeSchema.getPayPlanCode().
				 * equals(tClsFeeSchema.getPayPlanCode()) &&
				 * tFeeSchema.getInsuAccNo().equals( tClsFeeSchema.
				 * getInsuAccNo())) {
				 */
				/** *****常亮修改，查出一个和当前日期最近的管理费******* */
				String sql = "select lc.* from lcgrpfee lc,lmriskfee lm where grppolno='"
						+ "?s6?"
						+ "' and lc.insuaccno='"
						+ "?s7?"
						+ "' "
						+ "and lc.payplancode='"
						+ "?s8?"
						+ "' and lc.makedate <='"
						+ "?s9?"
						+ " ' and lc.insuaccno=lm.insuaccno and lc.feecode=lm.feecode"
						+ " and lc.payplancode=lm.payplancode "
						//tongmeng 2008-09-10 modify
						//取消计算方式匹配 
						//" and lc.feecalmode=lm.feecalmode"
						+ " and lm.FeeTakePlace='01' order by lc.makedate desc";
				SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
    			sqlbv1.sql(sql);
    			sqlbv1.put("s6", tClsFeeSchema.getGrpPolNo());
    			sqlbv1.put("s7", tClsFeeSchema.getInsuAccNo());
    			sqlbv1.put("s8", tClsFeeSchema.getPayPlanCode());
    			sqlbv1.put("s9", PubFun.getCurrentDate());
				logger.debug(sql);
				LCGrpFeeDB tLCGrpFeeDB = new LCGrpFeeDB();
				LCGrpFeeSet tLCGrpFeeSet = tLCGrpFeeDB.executeQuery(sqlbv1);
				/** ***************************************************** */
				if (tLCGrpFeeSet.size() > 0) {
					LCGrpFeeSchema tFeeSchema = tLCGrpFeeSet.get(1);

					String calMode = tFeeSchema.getFeeCalMode();

					if (calMode == null || "01".equals(calMode)) { // 内扣固定值
						manaFee = tFeeSchema.getFeeValue();
						manaFeeRate = this.calInnerRate(manaFee, baseMoney);
					} else if (calMode.equals("02")) { // 内扣比例
						manaFeeRate = tFeeSchema.getFeeValue();
						manaFee = this.calInnerManaFee(baseMoney, manaFeeRate);

					} else if (calMode.equals("03")) { // 外缴-固定值
						manaFee = tFeeSchema.getFeeValue();
						manaFeeRate = this.calOutRate(manaFee, baseMoney);
					} else if (calMode.equals("04")) { // 外缴-比例值
						manaFeeRate = tFeeSchema.getFeeValue();
						manaFee = this.calOutManaFee(baseMoney, manaFeeRate);
					} else if (calMode.equals("05")) { // 固定值，比例计算后取较小值
						manaFeeRate = tFeeSchema.getFeeValue();
						manaFee = this.calManaFeeMinRate(baseMoney, tFeeSchema
								.getCompareValue(), manaFeeRate);
					} else if (calMode.equals("06")) { // 固定值，比例计算后取较大值
						manaFeeRate = tFeeSchema.getFeeValue();
						manaFee = this.calManaFeeMaxRate(baseMoney, tFeeSchema
								.getCompareValue(), manaFeeRate);
					} else if (calMode.equals("07")) { // 分档计算
						LCGrpFeeParamSchema tLCGrpFeeParamSchema = this
								.queryLCGrpFeeParamSchema(tFeeSchema
										.getInsuAccNo(), tFeeSchema
										.getPayPlanCode(), baseMoney);
						manaFeeRate = tLCGrpFeeParamSchema.getFeeRate();
						manaFee = this.calInnerManaFee(baseMoney, manaFeeRate);
					} else if (calMode.equals("08")) { // 累计分党计算
						// 需求不明确，尚未完成
						manaFeeRate = 0;
						manaFee = 0;
					}
					// break;

					// }
					// }
					// tClsFeeSchema.setFee(PubFun.setPrecision(tClsFeeSchema.
					// getFee() + manaFee, "0.00")); //常亮修改 2005-08-02
					tClsFeeSchema.setFeeRate(PubFun.setPrecision(manaFeeRate,
							"0.00"));
					LCInsureAccFeeTraceSchema tLCInsureAccFeeTraceSchema = this
							.createAccFeeTrace(manaFee, tFeeSchema,
									tClsFeeSchema);
					mLCInsureAccFeeTraceSet.add(tLCInsureAccFeeTraceSchema);
					break;
				}
			}

		}
		return manaFee;
	}

    private void
        updateInsureAcc(String OtherNo, String OtherNoType,
                        String MoneyType, double inputMoney,double cAccValue,
                        LCPremSchema tLCPremSchema,
                        LCPremToAccSchema tLCPremToAccSchema) {
        for (int j = 1; j <= mLCInsureAccSet.size();
             j++) {
            //如果当前缴费帐户关联表的保单号，账户号和当前的账户表的保单号，账户号相同并且资金不为0，将资金注入
            LCInsureAccSchema tLCInsureAccSchema =
                mLCInsureAccSet
                .get(j);
            if (tLCPremToAccSchema.getPolNo().
                equals(
                    tLCInsureAccSchema
                    .getPolNo())
                &&
                tLCPremToAccSchema.getInsuAccNo().
                equals(
                    tLCInsureAccSchema
                    .getInsuAccNo())
                ) {
                //修改保险帐户金额
                tLCInsureAccSchema.setInsuAccBala(
                    PubFun.setPrecision(
                        tLCInsureAccSchema
                        .getInsuAccBala()
                        + cAccValue, "0.00"));
                tLCInsureAccSchema.setSumPay(
                    PubFun.setPrecision(
                        tLCInsureAccSchema
                        .getSumPay()
                        + tLCPremSchema.getPrem()*getInvestRate(mLCPolSchema, tLCPremToAccSchema.getInsuAccNo(),
                                            tLCPremToAccSchema.getPayPlanCode()), "0.00"));
                tLCInsureAccSchema.setLastAccBala(
                    PubFun.setPrecision(tLCInsureAccSchema.getLastAccBala() +
                    		cAccValue, "0.00"));


                //生成初始保费轨迹数据
                LCInsureAccTraceSchema
                    tmpLCInsureAccTraceSchema =
                    createAccTrace(OtherNo,
                                   OtherNoType, MoneyType,
                                   inputMoney, tLCPremSchema,
                                   tLCInsureAccSchema,"000000");
                mLCInsureAccTraceSet.add(
                    tmpLCInsureAccTraceSchema);

                //生成生效对应日扣除管理费用轨迹
                LCInsureAccTraceSet
                tLCInsureAccTraceSet =
                createAccTraceSet(OtherNo,OtherNoType, "GL",tLCPremSchema,
                               tLCInsureAccSchema,"03");
                mLCInsureAccTraceSet.add(tLCInsureAccTraceSet);

            }
        }

    }
	private void updateInsureAcc(String OtherNo, String OtherNoType,
			String MoneyType, double inputMoney, LCPremSchema tLCPremSchema,
			LCPremToAccSchema tLCPremToAccSchema) {
		for (int j = 1; j <= mLCInsureAccSet.size(); j++) {
			// 如果当前缴费帐户关联表的保单号，账户号和当前的账户表的保单号，账户号相同并且资金不为0，将资金注入
			LCInsureAccSchema tLCInsureAccSchema = mLCInsureAccSet.get(j);
			if (tLCPremToAccSchema.getPolNo().equals(
					tLCInsureAccSchema.getPolNo())
					&& tLCPremToAccSchema.getInsuAccNo().equals(
							tLCInsureAccSchema.getInsuAccNo())) {
				// 修改保险帐户金额
				tLCInsureAccSchema.setInsuAccBala(PubFun.setPrecision(
						tLCInsureAccSchema.getInsuAccBala() + inputMoney,
						"0.00"));
				tLCInsureAccSchema.setSumPay(PubFun.setPrecision(
						tLCInsureAccSchema.getSumPay()
								+ tLCPremSchema.getPrem()*getInvestRate(mLCPolSchema, tLCPremToAccSchema.getInsuAccNo(),
                                        tLCPremToAccSchema.getPayPlanCode()), "0.00"));
				tLCInsureAccSchema.setLastAccBala(PubFun.setPrecision(
						tLCInsureAccSchema.getLastAccBala() + inputMoney,
						"0.00"));

				// 查询险种保险帐户缴费
				// LMRiskAccPaySchema
				// tLMRiskAccPaySchema =
				// queryLMRiskAccPay3(
				// mLCPolSchema.getRiskCode(),
				// tLCPremToAccSchema);
				// if (tLMRiskAccPaySchema == null)
				// {
				// // return null;
				// logger.debug("查询描述表错误");
				// return;
				// }
				// if (tLMRiskAccPaySchema.
				// getPayNeedToAcc()
				// .equals("1"))
				// {
            //生成初始保费轨迹数据
            LCInsureAccTraceSchema
                tmpLCInsureAccTraceSchema =
                createAccTrace(OtherNo,
                               OtherNoType, MoneyType,
                               inputMoney, tLCPremSchema,
                               tLCInsureAccSchema,"000000");
            
            //tongmeng 2010-11-17 新契约特别设置字段
            tmpLCInsureAccTraceSchema.setBusyType("NB");
            tmpLCInsureAccTraceSchema.setAccAlterType("1");
            tmpLCInsureAccTraceSchema.setAccAlterNo(this.mLCPolSchema.getPrtNo());
            //tongmeng 2010-11-30 modify
            tmpLCInsureAccTraceSchema.setCurrency(tLCInsureAccSchema.getCurrency());
            mLCInsureAccTraceSet.add(
                tmpLCInsureAccTraceSchema);

            //生成生效对应日扣除管理费用轨迹
            LCInsureAccTraceSet
            tLCInsureAccTraceSet =
            createAccTraceSet(OtherNo,OtherNoType, "GL",tLCPremSchema,
                           tLCInsureAccSchema,"03");
            mLCInsureAccTraceSet.add(tLCInsureAccTraceSet);

				// }

				// break;
			}
		}

	}
/**
    *计算投资比例
    *
    */
   private double getInvestRate(LCPolSchema tLCPolSchema,String InsuAccNo,String PayPlanCode)
   {
       double tRate=1.00;
       if (tLCPolSchema.getGrpContNo().equals("00000000000000000000")) {
              LCPerInvestPlanDB tLCPerInvestPlanDB = new
                      LCPerInvestPlanDB();
              tLCPerInvestPlanDB.setPolNo(mLCPolSchema.getProposalNo());
              tLCPerInvestPlanDB.setPayPlanCode(PayPlanCode);
              tLCPerInvestPlanDB.setInsuAccNo(InsuAccNo);
              logger.debug("tLCPerInvestPlanDB:" +
                                 tLCPerInvestPlanDB.getPolNo() + "A");
              logger.debug("tLCPerInvestPlanDB:" +
                                 tLCPerInvestPlanDB.getPayPlanCode() + "A");
              logger.debug("tLCPerInvestPlanDB:" +
                                 tLCPerInvestPlanDB.getInsuAccNo() + "A");

              if(tLCPerInvestPlanDB.getInfo())
              {
                  logger.debug("tLCPerInvestPlanDB.getInputMode():"+tLCPerInvestPlanDB.getInputMode());
                  if (tLCPerInvestPlanDB.getInputMode().equals("1")) //按照比例
                      tRate =tLCPerInvestPlanDB.getInvestRate();
                  else//按照金额
                  {
                	  SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
                	  sqlbv1.sql("select sum(InvestMoney) from LCPerInvestPlan where polno='"+"?a1?"+"' and payplancode='"+"?a2?"+"'");
                	  sqlbv1.put("s8", tLCPerInvestPlanDB.getPolNo());
                	  sqlbv1.put("s9", PayPlanCode);
                      ExeSQL tExeSQL=new ExeSQL();
                      tRate=tLCPerInvestPlanDB.getInvestMoney()/Double.parseDouble(tExeSQL.getOneValue(sqlbv1));
                  }
              }

          } else {
              LCPolDB tLCPolDB=new LCPolDB();
              tLCPolDB.setPolNo(mLCPolSchema.getProposalNo());
              if(tLCPolDB.getInfo())
              {
                  LCGrpIvstPlanDB tLCGrpIvstPlanDB = new LCGrpIvstPlanDB();
                  tLCGrpIvstPlanDB.setGrpPolNo(mLCPolSchema.getGrpPolNo());
                  tLCGrpIvstPlanDB.setPayPlanCode(PayPlanCode);
                  tLCGrpIvstPlanDB.setInsuAccNo(InsuAccNo);
                  tLCGrpIvstPlanDB.setInvestRuleCode(tLCPolDB.getInvestRuleCode());
                  if(tLCGrpIvstPlanDB.getInfo())
                  {
                      if (tLCGrpIvstPlanDB.getInputMode().equals("1")) //按照比例
                          tRate = tLCGrpIvstPlanDB.getInvestRate();
                      else { //按照金额
                    	  SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
                    	  sqlbv2.sql("select sum(InvestMoney) from LCGrpIvstPlan where grppolno='" +
                                  "?z1?" +
                                  "' and payplancode='" +
                                  "?z1?" + "' and InvestRuleCode='"+"?z3?"+"'");
                    	  sqlbv2.put("z1", tLCGrpIvstPlanDB.getGrpPolNo());
                    	  sqlbv2.put("z2", tLCGrpIvstPlanDB.getPayPlanCode());
                    	  sqlbv2.put("z3", tLCGrpIvstPlanDB.getInvestRuleCode());
                          ExeSQL tExeSQL = new ExeSQL();
                          tRate = tLCGrpIvstPlanDB.getInvestMoney() /
                                  Double.
                                  parseDouble(tExeSQL.getOneValue(sqlbv2
                                  ));
                          
                      }

                  }
              }
          }

       return tRate;
    }

	/**
	 * 更新分类表
	 * 
	 * @param tClassSchema
	 *            LCInsureAccClassSchema
	 * @param manaFee
	 *            double
	 * @param tLCPremToAccSchema
	 *            LCPremToAccSchema
	 */
	private void updateLCInsuerAccClass(LCInsureAccClassSchema tClassSchema,
			double inputMoney, LCPremSchema tLCPremSchema,
			LCPremToAccSchema tLCPremToAccSchema) {
		// 累计账户分类ss
        for (int tt = 1; tt <= mInsureAccClassSet.size(); tt++) {
            tClassSchema = mInsureAccClassSet.get(tt);
            if (tClassSchema.getInsuAccNo().equals(
                tLCPremToAccSchema.getInsuAccNo())
                &&
                tClassSchema.getPayPlanCode().
                equals(tLCPremToAccSchema.
                       getPayPlanCode())
                &&
                tClassSchema.getPolNo().equals(
                    tLCPremToAccSchema.
                    getPolNo())) {
                tClassSchema.setSumPay(
                    PubFun.setPrecision(
                        tClassSchema.
                        getSumPay() +
                        tLCPremSchema.getPrem()*getInvestRate(mLCPolSchema, tLCPremToAccSchema.getInsuAccNo(),
                                            tLCPremToAccSchema.getPayPlanCode()), "0.00"));
                tClassSchema.setInsuAccBala(
                    PubFun.setPrecision(
                        tClassSchema.
                        getInsuAccBala() + inputMoney, "0.00"));
                tClassSchema.setLastAccBala(PubFun.setPrecision(tClassSchema.
                    getLastAccBala() + inputMoney, "0.00"));
                // break;
            }

        }
    }

	/**
	 * 更新管理非账户表
	 * 
	 * @param tClsFeeSchema
	 *            LCInsureAccClassFeeSchema
	 */
	private void updateLCInsureAccFee(String InsuAccNo, double fee) {
		// 管理非费账户
		LCInsureAccFeeSchema tLCInsureAccFeeSchema = getLCInsureAccFee(InsuAccNo);
		if (tLCInsureAccFeeSchema == null) {
			logger.debug("没有从查找到对应的管理费账户");
			tLCInsureAccFeeSchema.setFee(PubFun.setPrecision(fee, "0.00"));
			return;
		}
		tLCInsureAccFeeSchema.setFee(PubFun.setPrecision(tLCInsureAccFeeSchema
				.getFee()
				+ fee, "0.00"));
	}

	/**
	 * updateLCInsureAccClassFee
	 * 
	 * @param tInsuAccNo
	 *            String
	 * @param manaFee
	 *            double
	 */
	private void updateLCInsureAccClassFee(String tInsuAccNo, double manaFee,
			String tPayPlanCode) {
		// 管理非费账户
		LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema = getLCInsureAccClassFee(
				tInsuAccNo, tPayPlanCode);
		if (tLCInsureAccClassFeeSchema == null) {
			logger.debug("没有从查找到对应的管理费账户");
			// return null;
		}
		tLCInsureAccClassFeeSchema.setFee(PubFun.setPrecision(
				tLCInsureAccClassFeeSchema.getFee() + manaFee, "0.00"));

	}

	/**
	 * getLCInsureAccClassFee
	 * 
	 * @param tInsuAccNo
	 *            String
	 * @return LCInsureAccClassFeeSchema
	 */
	private LCInsureAccClassFeeSchema getLCInsureAccClassFee(String tInsuAccNo) {
		LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema = null;
		for (int i = 1; i <= mLCInsureAccClassFeeSet.size(); i++) {
			tLCInsureAccClassFeeSchema = mLCInsureAccClassFeeSet.get(i);
			if (tLCInsureAccClassFeeSchema.getInsuAccNo().equals(tInsuAccNo)) {
				break;
			}

		}
		return tLCInsureAccClassFeeSchema;
	}

	/**
	 * 创建结算轨迹记录 add by zhangtao 2006-11-21
	 * 
	 * @param pLCInsureAccClassFeeSchema
	 * @param dMoney
	 *            结算金额
	 * @param sMoneyType
	 *            金额类型
	 * @param sFeeCode
	 *            管理费编码
	 * @return boolean
	 */
	private boolean createFeeTrace(
			LCInsureAccClassFeeSchema pLCInsureAccClassFeeSchema,
			double dMoney, String sMoneyType, String sFeeCode) {
		Reflections ref = new Reflections();
		// 创建帐户轨迹记录
		LCInsureAccFeeTraceSchema tLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
		ref.transFields(tLCInsureAccFeeTraceSchema, pLCInsureAccClassFeeSchema);
		String tLimit = PubFun.getNoLimit(pLCInsureAccClassFeeSchema
				.getManageCom());
		String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);

		tLCInsureAccFeeTraceSchema.setSerialNo(serNo);
        tLCInsureAccFeeTraceSchema.setState("1");
		tLCInsureAccFeeTraceSchema.setMoneyType(sMoneyType);
		tLCInsureAccFeeTraceSchema.setFee(dMoney);
		tLCInsureAccFeeTraceSchema.setPayDate(pLCInsureAccClassFeeSchema
				.getBalaDate());
		tLCInsureAccFeeTraceSchema.setFeeCode(sFeeCode);
		//tongmeng 2010-11-30 modify
		tLCInsureAccFeeTraceSchema.setCurrency(pLCInsureAccClassFeeSchema.getCurrency());
		mLCInsureAccFeeTraceSet.add(tLCInsureAccFeeTraceSchema);
		return true;
	}

	/**
	 * 得到管理费账户轨迹表信息.
	 * 
	 * @param fee
	 *            double 管理费
	 * @param tLCGrpFeeSchema
	 *            LCGrpFeeSchema
	 * @param tLCInsureAccClassFeeSchema
	 *            LCInsureAccClassFeeSchema
	 * @return LCInsureAccFeeTraceSchema
	 */
	private LCInsureAccFeeTraceSchema createAccFeeTrace(double fee,
			LCGrpFeeSchema tLCGrpFeeSchema,
			LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema) {
		String currentDate = PubFun.getCurrentDate();
		String currentTime = PubFun.getCurrentTime();
		String tLimit = PubFun.getNoLimit(tLCInsureAccClassFeeSchema
				.getManageCom());
		String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
		LCInsureAccFeeTraceSchema tLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
		tLCInsureAccFeeTraceSchema.setSerialNo(serNo);
		tLCInsureAccFeeTraceSchema.setPolNo(tLCInsureAccClassFeeSchema
				.getPolNo());
		tLCInsureAccFeeTraceSchema.setGrpContNo(tLCInsureAccClassFeeSchema
				.getGrpContNo());
		tLCInsureAccFeeTraceSchema.setGrpPolNo(tLCInsureAccClassFeeSchema
				.getGrpPolNo());
		tLCInsureAccFeeTraceSchema.setContNo(tLCInsureAccClassFeeSchema
				.getContNo());
		tLCInsureAccFeeTraceSchema.setInsuAccNo(tLCInsureAccClassFeeSchema
				.getInsuAccNo());
		tLCInsureAccFeeTraceSchema.setRiskCode(tLCInsureAccClassFeeSchema
				.getRiskCode());
		tLCInsureAccFeeTraceSchema.setPayPlanCode(tLCInsureAccClassFeeSchema
				.getPayPlanCode());
		tLCInsureAccFeeTraceSchema.setOtherNo(tLCInsureAccClassFeeSchema
				.getOtherNo());
		tLCInsureAccFeeTraceSchema.setOtherType(tLCInsureAccClassFeeSchema
				.getOtherType());
		tLCInsureAccFeeTraceSchema.setAccAscription(tLCInsureAccClassFeeSchema
				.getAccAscription());
		tLCInsureAccFeeTraceSchema.setMoneyType("GL");
		tLCInsureAccFeeTraceSchema.setFee(fee);
		tLCInsureAccFeeTraceSchema.setFeeRate(tLCGrpFeeSchema.getFeeValue());
		tLCInsureAccFeeTraceSchema.setPayDate(currentDate);
		tLCInsureAccFeeTraceSchema.setFeeCode(tLCGrpFeeSchema.getFeeCode());
		// tLCInsureAccFeeTraceSchema.setInerSerialNo();
		tLCInsureAccFeeTraceSchema.setManageCom(tLCInsureAccClassFeeSchema
				.getManageCom());
		tLCInsureAccFeeTraceSchema.setOperator(tLCInsureAccClassFeeSchema
				.getOperator());
		tLCInsureAccFeeTraceSchema.setModifyDate(currentDate);
		tLCInsureAccFeeTraceSchema.setModifyTime(currentTime);
		tLCInsureAccFeeTraceSchema.setMakeDate(currentDate);
		tLCInsureAccFeeTraceSchema.setMakeTime(currentTime);
		return tLCInsureAccFeeTraceSchema;
	}

	/**
	 * 创建账户轨迹表
	 * 
	 * @param OtherNo
	 *            String
	 * @param OtherNoType
	 *            String
	 * @param MoneyType
	 *            String
	 * @param tLCInsureAccTraceSet
	 *            LCInsureAccTraceSet
	 * @param tLCInsureAccTraceSchema
	 *            LCInsureAccTraceSchema
	 * @param inputMoney
	 *            double
	 * @param tLCPremSchema
	 *            LCPremSchema
	 * @param tLCInsureAccSchema
	 *            LCInsureAccSchema
	 */
    private LCInsureAccTraceSchema createAccTrace(String OtherNo,
                                                  String OtherNoType,
                                                  String MoneyType,
                                                  double inputMoney,
                                                  LCPremSchema tLCPremSchema,
                                                  LCInsureAccSchema
                                                  tLCInsureAccSchema,String cFeeCode) {

		// 填充保险帐户表记价履历表
		String tLimit = PubFun.getNoLimit(tLCPremSchema.getManageCom());
		String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
		LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
		tLCInsureAccTraceSchema.setSerialNo(serNo);
		// tLCInsureAccTraceSchema.setInsuredNo(tLCInsureAccSchema
		// .getInsuredNo());
		tLCInsureAccTraceSchema.setPolNo(tLCInsureAccSchema.getPolNo());
		tLCInsureAccTraceSchema.setMoneyType(MoneyType);
		tLCInsureAccTraceSchema.setRiskCode(tLCInsureAccSchema.getRiskCode());
		tLCInsureAccTraceSchema.setOtherNo(OtherNo);
		tLCInsureAccTraceSchema.setOtherType(OtherNoType);
		tLCInsureAccTraceSchema.setMoney(inputMoney);
		tLCInsureAccTraceSchema.setContNo(tLCInsureAccSchema.getContNo());
		tLCInsureAccTraceSchema.setGrpPolNo(tLCInsureAccSchema.getGrpPolNo());
		tLCInsureAccTraceSchema.setInsuAccNo(tLCInsureAccSchema.getInsuAccNo());

		tLCInsureAccTraceSchema.setPolNo(tLCInsureAccSchema.getPolNo());
		tLCInsureAccTraceSchema.setGrpContNo(tLCInsureAccSchema.getGrpContNo());
		tLCInsureAccTraceSchema.setState(tLCInsureAccSchema.getState());
		tLCInsureAccTraceSchema.setManageCom(tLCInsureAccSchema.getManageCom());
		tLCInsureAccTraceSchema.setOperator(tLCInsureAccSchema.getOperator());
		/** 和账户期限一致 */
		tLCInsureAccTraceSchema.setPayDate(tLCInsureAccSchema.getBalaDate());

		tLCInsureAccTraceSchema.setPolNo(tLCInsureAccSchema.getPolNo());
		tLCInsureAccTraceSchema.setGrpContNo(tLCInsureAccSchema.getGrpContNo());
		String CurrentDate = PubFun.getCurrentDate();
		String CurrentTime = PubFun.getCurrentTime();
		tLCInsureAccTraceSchema.setPayPlanCode(tLCPremSchema.getPayPlanCode());
		tLCInsureAccTraceSchema.setState("0");
		String payPlanCode = tLCInsureAccTraceSchema.getPayPlanCode();
		if (this.getPubFlag(payPlanCode).equals("Y")
				&& this.getPayaimClass(payPlanCode).equals("2")) {
			tLCInsureAccTraceSchema.setAccAscription("0");
		} else {
			tLCInsureAccTraceSchema.setAccAscription("1");
		}

        tLCInsureAccTraceSchema.setFeeCode(cFeeCode);
		tLCInsureAccTraceSchema.setMakeDate(CurrentDate);
        tLCInsureAccTraceSchema.setMakeTime(CurrentTime);
		tLCInsureAccTraceSchema.setModifyDate(CurrentDate);
		tLCInsureAccTraceSchema.setModifyTime(CurrentTime);
		tLCInsureAccTraceSchema.setOperator(tLCPremSchema.getOperator());

		return tLCInsureAccTraceSchema;
	}

    /**
     * 创建账户轨迹表
     * @param OtherNo String
     * @param OtherNoType String
     * @param MoneyType String
     * @param tLCInsureAccTraceSet LCInsureAccTraceSet
     * @param tLCInsureAccTraceSchema LCInsureAccTraceSchema
     * @param inputMoney double
     * @param tLCPremSchema LCPremSchema
     * @param tLCInsureAccSchema LCInsureAccSchema
     */
    private LCInsureAccTraceSet createAccTraceSet(String OtherNo,
                                                  String OtherNoType,
                                                  String MoneyType,
                                                  LCPremSchema tLCPremSchema,
                                                  LCInsureAccSchema
                                                  tLCInsureAccSchema,String cTakePlace) {

    	LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();

    	//查询管理费
        LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB();
        tLMRiskFeeDB.setInsuAccNo(tLCInsureAccSchema.getInsuAccNo());
        tLMRiskFeeDB.setPayPlanCode(tLCPremSchema.getPayPlanCode());
        tLMRiskFeeDB.setFeeKind("03"); //03-个单管理费
        tLMRiskFeeDB.setFeeTakePlace(cTakePlace); //管理费产生位置
        LMRiskFeeSet tLMRiskFeeSet = tLMRiskFeeDB.query();
        if (tLMRiskFeeDB.mErrors.needDealError())
        {
            CError.buildErr(this, "账户管理费查询失败!");
            return null;
        }

        for (int i = 1; i <= tLMRiskFeeSet.size(); i++) {
        	for (int j = 1; j <= mLCInsureAccFeeTraceSet.size(); j++) {
        		String tFeeCode = mLCInsureAccFeeTraceSet.get(j).getFeeCode();
        		if (!tLMRiskFeeSet.get(i).getFeeCode().equals(tFeeCode)) {
        			continue;
        		}
        		LCInsureAccTraceSchema tLCInsureAccTraceSchema = new
        		LCInsureAccTraceSchema();
        		tLCInsureAccTraceSchema = createAccTrace(OtherNo,
                        OtherNoType, MoneyType,
                        -mLCInsureAccFeeTraceSet.get(j).getFee(), tLCPremSchema,
                        tLCInsureAccSchema,tFeeCode);
        		//tongmeng 2010-11-30 modify
        		tLCInsureAccTraceSchema.setCurrency(mLCInsureAccFeeTraceSet.get(j).getCurrency());
        		tLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
        	}
        }

        return tLCInsureAccTraceSet;
    }

	/**
	 * 查询管理费表
	 * 
	 * @param tLCInsureAccTraceSchema
	 *            LCInsureAccTraceSchema
	 * @return LMRiskFeeSchema
	 */
	public LMRiskFeeSchema queryLMRiskFee(
			LCInsureAccTraceSchema tLCInsureAccTraceSchema) {
		LMRiskFeeSet tLMRiskFeeSet = new LMRiskFeeSet();
		LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB();
		String sqlStr = "select * from lmriskfee where 1=1"
				+ " and InsuAccNo = '" + "?d1?"
				+ "'" + " and payplancode = '"
				+ "?d2?" + "'";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sqlStr);
		sqlbv3.put("d1", tLCInsureAccTraceSchema.getInsuAccNo());
		sqlbv3.put("d2", tLCInsureAccTraceSchema.getPayPlanCode());

		logger.debug(sqlStr);
		tLMRiskFeeSet = tLMRiskFeeDB.executeQuery(sqlbv3);
		if (tLMRiskFeeDB.mErrors.needDealError() == true) {
			// @@错误处理
			buildError("queryLMRiskFee", "险种保险帐户管理费表查询失败！");

			return null;
		}
		if (tLMRiskFeeSet.size() == 0) {
			// @@错误处理
			buildError("queryLMRiskFee", "险种保险帐户管理费表没有查询到相关数据！");
			return null;
		}

		return tLMRiskFeeSet.get(1);
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "DealAccount";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 查询险种保险帐户缴费表3
	 * 
	 * @param riskcode
	 * @param LCPremToAccSchema
	 * @return LMRiskAccPaySchema
	 */
	public LMRiskAccPaySchema queryLMRiskAccPay3(String riskCode,
			LCPremToAccSchema pLCPremToAccSchema) {
		if (riskCode == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskAccPay3";
			tError.errorMessage = "传入数据不能为空!";
			this.mErrors.addOneError(tError);

			return null;
		}

		String payPlanCode = pLCPremToAccSchema.getPayPlanCode();
		String InsuAccNo = pLCPremToAccSchema.getInsuAccNo();

		// 查询险种保险帐户缴费表
		String sqlStr = "select * from LMRiskAccPay where RiskCode='"
				+ "?d3?" + "' and payPlanCode='" + "?d5?"
				+ "' and InsuAccNo='" + "?d6?" + "'";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(sqlStr);
		sqlbv4.put("d3", riskCode);
		sqlbv4.put("d5", payPlanCode);
		sqlbv4.put("d6", InsuAccNo);
		
		LMRiskAccPaySchema tLMRiskAccPaySchema = new LMRiskAccPaySchema();
		LMRiskAccPaySet tLMRiskAccPaySet = new LMRiskAccPaySet();
		LMRiskAccPayDB tLMRiskAccPayDB = tLMRiskAccPaySchema.getDB();
		tLMRiskAccPaySet = tLMRiskAccPayDB.executeQuery(sqlbv4);
		if (tLMRiskAccPayDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMRiskAccPayDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskAccPay2";
			tError.errorMessage = "险种保险帐户缴费表查询失败!";
			this.mErrors.addOneError(tError);
			tLMRiskAccPaySet.clear();

			return null;
		}
		if (tLMRiskAccPaySet.size() == 0) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMRiskAccPayDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "DealAccount";
			tError.functionName = "queryLMRiskAccPay2";
			tError.errorMessage = "险种保险帐户缴费表没有查询到相关数据!";
			this.mErrors.addOneError(tError);
			tLMRiskAccPaySet.clear();

			return null;
		}

		return tLMRiskAccPaySet.get(1);
	}

	/**
	 * 
	 * @param MoneyType
	 *            String
	 * @param inputMoney
	 *            double
	 * @param tLCInsureAccClassSchema
	 *            LCInsureAccClassSchema
	 * @return LCInsureAccTraceSchema
	 */
	private LCInsureAccTraceSchema createAccTrace(String MoneyType,
			double inputMoney, LCInsureAccClassSchema tLCInsureAccClassSchema) {

		// 填充保险帐户表记价履历表
		String tLimit = PubFun.getNoLimit(tLCInsureAccClassSchema
				.getManageCom());
		String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
		LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
		tLCInsureAccTraceSchema.setSerialNo(serNo);
		// tLCInsureAccTraceSchema.setInsuredNo(tLCInsureAccSchema
		// .getInsuredNo());
		tLCInsureAccTraceSchema.setPolNo(tLCInsureAccClassSchema.getPolNo());
		tLCInsureAccTraceSchema.setMoneyType(MoneyType);
		tLCInsureAccTraceSchema.setRiskCode(tLCInsureAccClassSchema
				.getRiskCode());
		tLCInsureAccTraceSchema
				.setOtherNo(tLCInsureAccClassSchema.getOtherNo());
		tLCInsureAccTraceSchema.setOtherType(tLCInsureAccClassSchema
				.getOtherType());
		tLCInsureAccTraceSchema.setMoney(inputMoney);
		tLCInsureAccTraceSchema.setContNo(tLCInsureAccClassSchema.getContNo());
		tLCInsureAccTraceSchema.setGrpPolNo(tLCInsureAccClassSchema
				.getGrpPolNo());
		tLCInsureAccTraceSchema.setInsuAccNo(tLCInsureAccClassSchema
				.getInsuAccNo());

		tLCInsureAccTraceSchema.setPolNo(tLCInsureAccClassSchema.getPolNo());
		tLCInsureAccTraceSchema.setGrpContNo(tLCInsureAccClassSchema
				.getGrpContNo());
		tLCInsureAccTraceSchema.setState(tLCInsureAccClassSchema.getState());
		tLCInsureAccTraceSchema.setManageCom(tLCInsureAccClassSchema
				.getManageCom());
		tLCInsureAccTraceSchema.setOperator(tLCInsureAccClassSchema
				.getOperator());

		tLCInsureAccTraceSchema.setPolNo(tLCInsureAccClassSchema.getPolNo());
		tLCInsureAccTraceSchema.setGrpContNo(tLCInsureAccClassSchema
				.getGrpContNo());
		String CurrentDate = PubFun.getCurrentDate();
		String CurrentTime = PubFun.getCurrentTime();
		tLCInsureAccTraceSchema.setPayPlanCode(tLCInsureAccClassSchema
				.getPayPlanCode());
		tLCInsureAccTraceSchema.setState("0");

		tLCInsureAccTraceSchema.setPayDate(CurrentDate);
		tLCInsureAccTraceSchema.setAccAscription(tLCInsureAccClassSchema
				.getAccAscription());

		tLCInsureAccTraceSchema.setMakeDate(CurrentDate);
		tLCInsureAccTraceSchema.setMakeTime(CurrentTime);
		tLCInsureAccTraceSchema.setModifyDate(CurrentDate);
		tLCInsureAccTraceSchema.setModifyTime(CurrentTime);
		tLCInsureAccTraceSchema.setOperator(tLCInsureAccClassSchema
				.getOperator());

		return tLCInsureAccTraceSchema;
	}

	/**
	 * get PayaimClass by payPlanCode
	 * 
	 * @param payPlanCode
	 *            String
	 * @return String
	 */
	private String getPayaimClass(String payPlanCode) {
		String strSQL = "select PayaimClass from lmdutypay where PayPlanCode='"
				+ "?PayPlanCode?" + "'";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(strSQL);
		sqlbv5.put("PayPlanCode", payPlanCode);
		ExeSQL exeSQL = new ExeSQL();
		String PayaimClass = exeSQL.getOneValue(sqlbv5);
		if (PayaimClass == null) {
			return "";
		}
		return PayaimClass;
	}

	/**
	 * get pubFlg by payPlanCode
	 * 
	 * @param payPlanCode
	 *            String
	 * @return String
	 */
	private String getPubFlag(String payPlanCode) {
		String strSQL = "select PubFlag from lmdutypay where PayPlanCode='"
				+ "?PayPlanCode1?" + "'";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(strSQL);
		sqlbv6.put("PayPlanCode1", payPlanCode);
		ExeSQL exeSQL = new ExeSQL();
		String pubFlag = exeSQL.getOneValue(sqlbv6);
		if (pubFlag == null) {
			return "";
		}
		return pubFlag;
	}

    private String getVPU(String cDutyCode) {
        String str = (String) m_VPUInfo.get(cDutyCode);
        return str;
    }

    private void setVPU(String cDutyCode, String cValue) {
    	m_VPUInfo.put(cDutyCode, String.valueOf(cValue));
    }
}
