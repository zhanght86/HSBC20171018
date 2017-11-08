

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.reinsure;

import java.text.DecimalFormat;
import java.util.Date;

import com.sinosoft.lis.bl.LCGrpPolBL;
import com.sinosoft.lis.bl.LCPremBL;
import com.sinosoft.lis.bq.EdorCalZT;
import com.sinosoft.lis.bqgrp.GrpEdorCalZT;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LREdorMainDB;
import com.sinosoft.lis.db.LRPolDB;
import com.sinosoft.lis.db.LRRiskDB;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LREdorMainSchema;
import com.sinosoft.lis.schema.LRPolSchema;
import com.sinosoft.lis.schema.LRRiskSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LREdorMainSet;
import com.sinosoft.lis.vschema.LRPolSet;
import com.sinosoft.lis.vschema.LRRiskSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 分保系统计算类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @version 1.0
 */
public class CalRetentBL {
	public CErrors mErrors = new CErrors();
	// private VData mInputData;
	private VData mResult = new VData();
	private String mOperate;
	private String mReinsureCom = ""; // 再保险公司
	private String mItemFlag = ""; // 再保险项目：法定或商业
	private String mToday = ""; // 计算时间
	private LRPolSet mLRPolSet = new LRPolSet();
	private LRPolSet mRetLRPolSet = new LRPolSet();

	private LRPolSchema mLRPolSchema = new LRPolSchema();
	// 个人承保信息
	private LCPolSchema mLCPolSchema = new LCPolSchema();

	// 再保险险种定义
	private LRRiskSchema mLRRiskSchema = new LRRiskSchema();
	private double mCessionRate = 0; // 分保比例
	private double mRiskAmnt = 0; // 保单风险保额

	public CalRetentBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		// mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);
		System.out.println("------Operate:" + this.getOperate());

		// 得到外部传入的数据,将数据备份到本类中
		getInputData(cInputData);
		System.out.println("after GetInputData.....");

		// 从处理日期取得理论计算日期
		if(!setCalDate())
			return false;
		
		this.mLRRiskSchema = new LRRiskSchema();
		this.mRetLRPolSet = new LRPolSet();
		LRRiskDB tLRRiskDB = new LRRiskDB();
		tLRRiskDB.setRiskCode(this.mLCPolSchema.getRiskCode());
		tLRRiskDB.setReinsurItem(this.mItemFlag);
//    tLRRiskDB.setReinsureCom(this.mReinsureCom);
		LRRiskSet tLRRiskSet = new LRRiskSet();
		tLRRiskSet = tLRRiskDB.query();
		for (int RiskCount = 1; RiskCount <= tLRRiskSet.size(); RiskCount++) {
			this.mLRPolSchema = new LRPolSchema();
			this.mLRRiskSchema = new LRRiskSchema();
			mLRRiskSchema.setSchema(tLRRiskSet.get(RiskCount));
			this.mReinsureCom=mLRRiskSchema.getReinsureCom();
			//检验是否有按责任分保
			if(!checkDutyCode())
				continue;
			// 数据复杂业务处理(dealData())
			if (!getCessRateAndRiskAmount()) {
				continue;
			}
			System.out.println("after dealData......");
			// 数据准备操作（preparedata())
      if(!this.prepareData())
          continue;
		}
		this.mResult.addElement(this.mRetLRPolSet);
		return true;
	}

	private boolean checkDutyCode() {
		if (mLRRiskSchema.getDutyCode().equals("000000"))
			return true;
		LCDutyDB tLCDutyDB=new LCDutyDB();
		tLCDutyDB.setPolNo(mLCPolSchema.getPolNo());
		tLCDutyDB.setDutyCode(mLRRiskSchema.getDutyCode());
		if(tLCDutyDB.getInfo()){
			mLCPolSchema.setAmnt(tLCDutyDB.getAmnt());
			mLCPolSchema.setRiskAmnt(tLCDutyDB.getRiskAmnt());
			mLCPolSchema.setPrem(tLCDutyDB.getPrem());
			mLCPolSchema.setStandPrem(tLCDutyDB.getStandPrem());
			return true;
		}else{
			return false;
		}
	}

	private boolean prepareData(){
		this.mLRPolSchema.setReinsurItem(this.mItemFlag);
		this.mLRPolSchema.setReinsureCom(this.mReinsureCom);
		this.mLRPolSchema.setRiskCalSort(this.mLRRiskSchema.getRiskCalSort());
		this.mLRPolSchema.setInsuredYear(PubFun.calInterval(this.mLCPolSchema
				.getCValiDate(), this.mToday, "Y") + 1);
		this.mLRPolSchema.setCessionRate(this.mCessionRate);
		this.mLRPolSchema.setCessionAmount(this.mCessionAmount);
		this.mLRPolSchema.setCessPrem(this.getCessionPrem(mLRPolSchema
				.getCessPremRate()));
		this.mLRPolSchema.setCessPremRate(this.getCessionPremRate());
		this.mLRPolSchema.setCessComm(this.getCommsion(this.mLRPolSchema
				.getCessPrem(), this.mLRRiskSchema.getCommsionRate()));
		this.mLRPolSchema.setCessCommRate(this.getCommsionRate());

		this.mLRPolSchema.setPolStat("0");
		this.mLRPolSchema.setCessStart(this.mToday);
		this.mLRPolSchema.setCessEnd(PubFun.calDate(this.mToday, 1, "Y", ""));
		this.mLRPolSchema.setMakeDate(PubFun.getCurrentDate());
		this.mLRPolSchema.setMakeTime(PubFun.getCurrentTime());
		this.mLRPolSchema.setModifyDate(PubFun.getCurrentDate());
		this.mLRPolSchema.setModifyTime(PubFun.getCurrentTime());
		double tAddPrem = getPolAddPrem();
		double tAddRate = tAddPrem / this.mLCPolSchema.getStandPrem();
		double tAddCessPrem = this.mLRPolSchema.getCessPrem() * tAddRate;
		this.mLRPolSchema.setExCessPremRate(tAddRate);
		this.mLRPolSchema.setExCessPrem(tAddCessPrem);
		this.mLRPolSchema.setExPrem(tAddPrem);
		this.mLRPolSchema
				.setExcessCommRate(this.mLRPolSchema.getCessCommRate());
		double tAddComm = this.mLRPolSchema.getExcessCommRate()
				* this.mLRPolSchema.getExCessPrem();
		this.mLRPolSchema.setExCessComm(tAddComm);
		this.mLRPolSchema.setSignDate(this.mLCPolSchema.getSignDate());
	    this.mLRPolSchema.setRiskAmnt(this.mRiskAmnt); //取当前保单的再保计算保额作为风险保额
	    String tOldPolNo = getRenewPolNo(this.mLCPolSchema.getPrtNo(),this.mLRPolSchema.getRiskCode());
	    if(tOldPolNo.equals(""))
	        tOldPolNo = this.mLCPolSchema.getPolNo();
	    this.mLRPolSchema.setOldPolNo(tOldPolNo);
    	this.mLRPolSchema.setTransSaleChnl(calTransSaleChnl());
    	this.mLRPolSchema.setDutyCode(mLRRiskSchema.getDutyCode());

		LRPolSchema tLRPolSchema = new LRPolSchema();
		tLRPolSchema.setSchema(this.mLRPolSchema);
		this.mRetLRPolSet.add(tLRPolSchema);
    	return true;
	}


	/**
	 * 计算联办渠道和专业代理渠道
	 * @return
	 */
	private String calTransSaleChnl() {
		String TransSaleChnl = mLRPolSchema.getSaleChnl();
		if ("01".equals(TransSaleChnl)) {
			String sql = "select count(*) from laagent where branchtype = '2' and 'LB' = substr(name, 1, 2) and agentcode='"
					+ mLCPolSchema.getAgentCode() + "'";
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mLCPolSchema.getAgentCode());
			String r = new ExeSQL().getOneValue(sql);
			if (!"0".equals(r)) {
				TransSaleChnl = "07";
			}
		} else if ("00000000000000000000".equals(mLCPolSchema.getGrpPolNo())
				&& "05,06,08,09,".indexOf(mLCPolSchema.getSaleChnl() + ",") >= 0) {
			if (mLCPolSchema.getAgentCom() != null
					&& (mLCPolSchema.getAgentCom().length() == 13 || mLCPolSchema
							.getAgentCom().length() == 16)
					&& mLCPolSchema.getAgentCom().startsWith("86")
					&& mLCPolSchema.getAgentCom().substring(8, 9).equals("8"))
				TransSaleChnl = "07";
		}
		return TransSaleChnl;
	}

/**
   * 获得续保保单的原始保单号（如果没有续保的则返回本身的保单号）
   * @param cPrtNo 印刷号
   * @param cRiskCode 险种编码
   * @return 返回最近一次的续保保单号
   */
  private String getRenewPolNo(String cPrtNo,String cRiskCode)
  {
      String sql = "select proposalno from lcrnewstatelog "
                 + "where prtno = '" + cPrtNo + "' and riskcode = '" + cRiskCode + "' and state = '6'"
                 + "order by makedate desc ";
      System.out.println("Query ReNewPol : " + sql);
      SSRS tssrs = new ExeSQL().execSQL(sql);
      if(tssrs == null || tssrs.getMaxRow() <= 0)
          return "";

      return tssrs.GetText(1,1);
  }
	public VData getResult() {
		return mResult;
	}

	// 保单该年度的分保保额
	private double mCessionAmount = 0;

	private boolean getCessRateAndRiskAmount() {

		this.mRiskAmnt = setRiskAmnt(this.mLRRiskSchema, this.mLCPolSchema);
		Reflections tReflections = new Reflections();
		tReflections.transFields(mLRPolSchema, mLCPolSchema);
		// tongmeng 2007-08-30 modify
		EdorCalZT tEdorCalZT = new EdorCalZT();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorType("");
		tEdorCalZT.setEdorInfo(tLPEdorItemSchema);

		double CVT;
		try{
			if("2".equals(mLCPolSchema.getContType())){//团险现价都是0，LIS5.3就已是算的为0了
				CVT = 0;
			}else{
				CVT = tEdorCalZT.getCashValue(this.mLCPolSchema.getPolNo(),
					this.mToday); // 保单t年度的现金价值
			}
			if(CVT<0)
				CVT=0;
		}catch(Exception ex){
			ex.printStackTrace();
			CError.buildErr(this, ex.getMessage());
			CVT=0;
		}
      this.mLRPolSchema.setNowRiskAmount((mRiskAmnt-CVT)>0?(mRiskAmnt-CVT):0);
		double CVT1 = 0;
		// 保单生效日到当前计算日的年度
		int tYear = PubFun.calInterval(this.mLCPolSchema.getCValiDate(),
				this.mToday, "Y");
		// 计算tYear保单年度对应日
		String tCvalidate = PubFun.calDate(this.mLCPolSchema.getCValiDate(),
				tYear, "Y", "");
		// 计算日时在该保单年度已经经过的月数
		int tPassMonth = PubFun.calInterval(tCvalidate, this.mToday, "M");
		// 插值法计算计算日时的现金价值
		if (tPassMonth > 0) {
			String tNextPeriDate = PubFun.calDate(tCvalidate, 1, "Y", "");
			if("2".equals(mLCPolSchema.getContType())){//团险现价都是0，LIS5.3就已是算的为0了
				CVT1 = 0;
			}else{
				CVT1 = tEdorCalZT.getCashValue(this.mLCPolSchema.getPolNo(),
						tNextPeriDate);
			}
			CVT = CVT + (CVT1 - CVT) * tPassMonth / 12;
		}

		this.mLRPolSchema.setEnterCA(CVT);

		if (mLRRiskSchema.getReinsurItem().equals("L")) // 法定分保
		{
			LDCodeDB tLDCodeDB = new LDCodeDB();
			tLDCodeDB.setCodeType("cessionrate");
			tLDCodeDB.setCode(this.mLCPolSchema.getCValiDate().substring(0, 4));
			if (!tLDCodeDB.getInfo()) {
				CError tError = new CError();
				tError.moduleName = "CalRetentBL";
				tError.functionName = "getCessRateAndRiskAmount";
				tError.errorMessage = "缺少法定分保分保比例描述!";
				return false;
			}
			this.mCessionRate = Double.parseDouble(tLDCodeDB.getCodeName());
			this.mCessionAmount = this.mRiskAmnt * this.mCessionRate;
			this.mLRPolSchema.setProtItem("T");
			return true;
		}
		if (mLRRiskSchema.getReinsurItem().equals("C")) // 商业分保
		{
			// 个人寿险 个人重大疾病，采用溢额算法，首先判断是否是首年分保，如果是，计算首年分保金额和分保比例；
			// 如果不是首年分保，计算该年度的风险保额，取出首年的分保比例，计算该年度的分保金额
			if (mLRRiskSchema.getCessionMode().equals("S")) {
				LRPolDB tLRPolDB = new LRPolDB();
				tLRPolDB.setPolNo(this.mLCPolSchema.getPolNo());
				tLRPolDB.setReinsureCom(this.mReinsureCom);
				tLRPolDB.setReinsurItem(mLRRiskSchema.getReinsurItem());
				tLRPolDB.setInsuredYear(1);
	          tLRPolDB.setRiskCalSort(this.mLRRiskSchema.getRiskCalSort());
	          if (!isLargeCvt(mLRRiskSchema) && tLRPolDB.getInfo()) //不是首年分保，取出分保比例
			  {
				if (tLRPolDB.getCValiDate().equals(this.mLCPolSchema.getCValiDate())) {
							this.mCessionRate = tLRPolDB.getCessionRate(); // 分保比例
							double DEATHAMNT = this.mRiskAmnt; // 死亡保额
	              double tRSAT= (DEATHAMNT-CVT)>0?(DEATHAMNT-CVT):0; //第t保单年度的风险保额
							this.mCessionAmount = tRSAT * mCessionRate; // 第t保单年度的分保风险保额
							this.mLRPolSchema.setSumRiskAmount(tLRPolDB
									.getSumRiskAmount());
	              int tProtFlag = needToNegociate(mCessionAmount);
	              if (tProtFlag == 2)
	                  this.mLRPolSchema.setProtItem("F");
	              else if (tProtFlag == 1)
	                  this.mLRPolSchema.setProtItem("T");
	              else
	                  return false;
				  return true;
				}
			 }
					// //首年分保
				double RSA0 = this.mRiskAmnt; // 本保单首年风险保额，个人寿险与个人重大疾病无法定分保，不用考虑法定分保部分
				double tSum[] = { 0, 0 };
				tSum = getSumRetain();
				this.mLRPolSchema.setSumRiskAmount(tSum[0]);
				double SUMALLRET = tSum[1]; // 现有有效保单累计自留
				double RETAINAMNT = this.mLRRiskSchema.getRetainLine();
		       	double RESA0= RSA0+SUMALLRET-CVT-RETAINAMNT; //首年分保金额
				if (RESA0 <= 0) {
					return false;
				}
				// 首年分保金额如果大于自动接收限额，则作临时分保
		          int tProtFlag = needToNegociate(RESA0);
		          if (tProtFlag == 2)
		          {
		            this.mLRPolSchema.setProtItem("F");
		            this.mCessionAmount=0;
		            this.mCessionRate=0;
		          }
		          else if(tProtFlag == 1)
		          {
		            this.mLRPolSchema.setProtItem("T");
		            this.mCessionAmount=RESA0;
		            //this.mCessionRate=RESA0/RSA0;
		            this.mCessionRate=RESA0/this.mRiskAmnt;
		          }
		          else     //自留的情况不作分保
		              return false;
					return true;
			}
			// 意外险，采用溢额＋成数分保方式
			if (mLRRiskSchema.getCessionMode().equals("M")) {
				double tLegalAmt = getThisLegalCessAmt(); // 法定分保保额
				double tRemainAmt = this.mRiskAmnt - tLegalAmt; // 剩余风险保额
				double tSum[] = { 0, 0 };
				tSum = getSumRetain();
				this.mLRPolSchema.setSumRiskAmount(tSum[0]);
				double SUMALLRET = tSum[1]; // 现有有效保单累计自留
				double tRetainLine = this.mLRRiskSchema.getRetainLine()
						- SUMALLRET; // 本次分保的自留限额
				double tRetentAmtCal = getThisRetain(tRemainAmt); // 计算自留额
				// 计算自留额大于自留额上限，作溢额处理
				if (tRetentAmtCal > tRetainLine) {
					double RSA0 = tRemainAmt; // 本保单首年风险保额，扣除法定分保
					double RETAINAMNT = tRetainLine;
					double RESA0 = RSA0 - RETAINAMNT; // 首年分保金额

					if (RESA0 <= 0) {
						RESA0 = 0;
						return false;
					}
            //if (RESA0>this.mLRRiskSchema.getCessionLimit()) //临时分保处理
            int tProtFlag = needToNegociate(RESA0);
            if (tProtFlag == 2)
            {
              this.mLRPolSchema.setProtItem("F");
              this.mCessionRate=0;
              this.mCessionAmount=0;
            }
            else if (tProtFlag == 1)
            {
              this.mLRPolSchema.setProtItem("T");
              this.mCessionRate=RESA0/mRiskAmnt;
              this.mCessionAmount=RESA0;
            }
            else
                return false;
					return true;
				} else // 成数分保
				{
					double tRamainAmt = tRemainAmt
							* this.mLRRiskSchema.getRetentRate(); // 自留额
					double tCessAmt = tRemainAmt - tRamainAmt;
					if (tCessAmt <= 0) {
						tCessAmt = 0;
						return false;
					}
					// 意外伤害保险成数分保分出额<自动接收限额
					// this.mCessionRate=tCessAmt/tRemainAmt;
					this.mCessionRate = tCessAmt / mRiskAmnt;
					this.mCessionAmount = tCessAmt;
					this.mLRPolSchema.setProtItem("T");
					return true;
				}
			}
			// 个人短期健康险、团体险、附加险
			if (mLRRiskSchema.getCessionMode().equals("Q")) {
				double tLegalAmt = getThisLegalCessAmt(); // 法定分保保额
				double tRemainAmt = mRiskAmnt - tLegalAmt;
				double tRamainAmt = tRemainAmt
						* this.mLRRiskSchema.getRetentRate(); // 自留额
				double tCessAmt = tRemainAmt - tRamainAmt;
				if (tCessAmt <= 0) {
					tCessAmt = 0;
					return false;
				}
				// 处理接收限额
	          int tProtFlag = needToNegociate(tCessAmt);
	          if (tProtFlag == 2)
	          {
	            this.mLRPolSchema.setProtItem("F");
	            this.mCessionRate=0;
	            this.mCessionAmount=0;
	          }
	          else if (tProtFlag == 1)
	          {
	            this.mLRPolSchema.setProtItem("T");
	            this.mCessionRate=tCessAmt/mRiskAmnt;
	            this.mCessionAmount=tCessAmt;
	          }
	          else
	              return false;
	          return true;
			}
      }

    return false;
	}

  /**
   * 判断是否为现金价值较高的险种
 * @param riskSchema
 * @return
 */
	private boolean isLargeCvt(LRRiskSchema riskSchema) {
		if(riskSchema.getRiskCode().equals("312202"))
			return true;
		if(riskSchema.getRiskCode().equals("312203"))
			return true;
		return false;
	}

	// 取得本张保单法定分保分出的保额
	private double getThisLegalCessAmt() {
		LRPolDB tLRPolDB = new LRPolDB();
		// 法定分保只可能出现一次
		tLRPolDB.setPolNo(this.mLCPolSchema.getPolNo());
		tLRPolDB.setReinsureCom(this.mReinsureCom);
		tLRPolDB.setReinsurItem("L");
		LRPolSet tLRPolSet = new LRPolSet();
		tLRPolSet = tLRPolDB.query();
		double tLegalCessAmnt = 0;
		for (int i = 1; i <= tLRPolSet.size(); i++) {
			LRPolSchema tLRPolSchema = new LRPolSchema();
			tLRPolSchema = tLRPolSet.get(i);
			tLegalCessAmnt = tLegalCessAmnt + tLRPolSchema.getCessionAmount();
		}
		// 计算保全导致该保单发生的分出保额变化
		LREdorMainDB tLREdorMainDB = new LREdorMainDB();
		tLREdorMainDB.setPolNo(this.mLCPolSchema.getPolNo());
		tLREdorMainDB.setReinsureCom(this.mReinsureCom);
		tLREdorMainDB.setReinsurItem("L");
		tLREdorMainDB.setInsuredYear(PubFun.calInterval(this.mLCPolSchema
				.getCValiDate(), this.mToday, "Y") + 1);
		LREdorMainSet tLREdorMainSet = new LREdorMainSet();
		tLREdorMainSet = tLREdorMainDB.query();
		for (int i = 1; i <= tLREdorMainSet.size(); i++) {
			LREdorMainSchema tLREdorMainSchema = new LREdorMainSchema();
			tLREdorMainSchema = tLREdorMainSet.get(i);
			FDate tFDate = new FDate();
			Date tDate = tFDate.getDate(tLREdorMainSchema.getCessStart());
			Date tDate1 = tFDate.getDate(this.mToday);
			if (tDate1.before(tDate)) {
				continue;
			}
			tLegalCessAmnt = tLegalCessAmnt - tLREdorMainSchema.getChgCessAmt();
		}
		return tLegalCessAmnt;
	}

	// 取得该客户作为被保险人的所有保单的累计风险保额与累计自留保额
	// 每张保单风险保额在再保中的定义、加入现金价值的计算要素
	private double[] getSumRetain() {
		// 计算该被保险人在上个保单年度保全分保保额累计
		// 计算所有生效日期小于当前保单的保单
		double tSum[] = { 0, 0 };
        /**
         * 注释 add by Fuqx 2009-12-11（此版本未修改程序）
         * 以下累积客户所有保单风险保额与自留额有潜在的问题，分别如下：
         * 1.多累积风险保额
         *  如果客户有A保单的终止日和B保单签单日在同一日，并且对A保单终止操作（将A的LCPol.AppFlag由1变为4的动作）
         *  晚于B保单分保处理的时间，会出现多累积A风险保额和自留额；
         *  解决方案：将分保处理的自动运行时间延后至凌晨3:30，保证在保单终止操作完成后再做分保
         * 2.用签单日去比较不太合理 ：enddate >= '" + this.mLCPolSchema.getSignDate() 
         *  应该是用生效日去判断是否有未终止的保单，并且是大于，即enddate > '" + this.mLCPolSchema.getCvalidate() 
         *  解决方案：修改条件后可能会对现有数据有影响，暂时不改此条件
         */
		
		String tSql = "select * from lcpol ";
//		String tBSql = "select * from lbpol ";
		String tWhereSql = " where insuredno='"
				+ this.mLCPolSchema.getInsuredNo()
				+ "' and appflag='1' and polno<>'"
				+ this.mLCPolSchema.getPolNo() + "' ";
		tSql = tSql + tWhereSql;
//		tBSql = tBSql + tWhereSql;
    String tDateSql = " and (signdate < '" + this.mLCPolSchema.getSignDate()
                    + "' or (signdate = '" + this.mLCPolSchema.getSignDate()
                    + "' and (makedate < '" + this.mLCPolSchema.getMakeDate()+"' or (makedate='"+this.mLCPolSchema.getMakeDate()
                    + "' and maketime < '" + this.mLCPolSchema.getMakeTime()+"')))) "
                    + "and enddate >= '" + this.mLCPolSchema.getSignDate() + "'" ;
		tSql = tSql + tDateSql;
//		tBSql = tBSql + tDateSql;
		String tCalKind = "'" + this.mLRRiskSchema.getRiskCalSort() + "'";
		String tRiskSql = " and riskcode in(select riskcode from lrrisk where riskcalsort in("
				+ tCalKind
				+ ") and reinsuritem='"
				+ this.mLRRiskSchema.getReinsurItem() + "') ";
		tSql = tSql + tRiskSql;
//		tBSql = tBSql + tRiskSql;
//		tBSql = tBSql + " and modifydate>'" + this.mToday + "'";

		System.out.println(tSql);
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet = tLCPolDB.executeQuery(tSql);

		double tSumRetainAmount = 0;
		double tSumRiskAmnt = 0;
		for (int i = 1; i <= tLCPolSet.size(); i++) {
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolSet.get(i);

			LRPolDB tLRPolDB = new LRPolDB();
			tLRPolDB.setPolNo(tLCPolSchema.getPolNo());
			int tInsuredYear = PubFun.calInterval(tLCPolSchema.getCValiDate(),
					this.mToday, "Y") + 1;
			tLRPolDB.setInsuredYear(tInsuredYear);
			LRPolSet tLRPolSet = new LRPolSet();
			tLRPolSet = tLRPolDB.query();
			double tSumCess = 0;

			// 查询本次提数处理中的保单
			for (int m = 1; m <= this.mLRPolSet.size(); m++) {
				LRPolSchema tLRPolSchema = new LRPolSchema();
				tLRPolSchema = mLRPolSet.get(m);
				if (tLRPolSchema.getPolNo().equals(tLCPolSchema.getPolNo())
						&& tLRPolSchema.getInsuredYear() == tInsuredYear) {
					tLRPolSet.add(tLRPolSchema);
				}
			}

			for (int j = 1; j <= tLRPolSet.size(); j++) {
				LRPolSchema tLRPolSchema = new LRPolSchema();
				tLRPolSchema = tLRPolSet.get(j);
				if (tLRPolSchema.getReinsurItem().equals("C")
						&& !tLRPolSchema.getRiskCalSort().equals(
								this.mLRRiskSchema.getRiskCalSort())) {
					continue;
				}
				tSumCess = tSumCess + tLRPolSchema.getCessionAmount();
			}
			System.out.println("tSumCess:" + tSumCess);
			// get the risk amount of the policy
			double tRiskAmnt = 0;
      if(PubFun.calInterval(this.mToday,tLCPolSchema.getEndDate(),"D") > 0)     //能对当前分保计算产生影响的还在责任期间的历史保单
      {
          LRRiskDB tLRRiskDB = new LRRiskDB();
          tLRRiskDB.setRiskCode(tLCPolSchema.getRiskCode());
          LRRiskSet tLRRiskSet = new LRRiskSet();
          tLRRiskSet=tLRRiskDB.query();
          if (tLRRiskSet.size()>0) //有分保描述，可以按照分保的保额定义进行保额计算
          {
              LRRiskSchema tLRRiskSchema = new LRRiskSchema();
              tLRRiskSchema=tLRRiskSet.get(1);
              tRiskAmnt=setRiskAmnt(tLRRiskSchema,tLCPolSchema);
          }
          else //否则取保单的承保风险保额
          {
              tRiskAmnt=tLCPolSchema.getRiskAmnt();
          }
          double CVT=new EdorCalZT().getCashValue(tLCPolSchema.getPolNo(),this.mToday); //保单t年度的现金价值
          tRiskAmnt-=CVT;
          tRiskAmnt=tRiskAmnt>0?tRiskAmnt:0;
      }
			// end get
			double tPolRetainAmt = tRiskAmnt - tSumCess; // how to decide the
															// risk amount of
															// the dealing
															// policy?
			tSumRiskAmnt = tSumRiskAmnt + tRiskAmnt;
			System.out.println("RiskAmnt:" + tRiskAmnt);

			System.out.println("tPolRetainAmt:" + tPolRetainAmt);
			// 查询保全导致分保保额变化
			LREdorMainDB tLREdorMainDB = new LREdorMainDB();
			int tYear = PubFun.calInterval(tLCPolSchema.getCValiDate(),
					this.mToday, "Y");
			String tSql1 = "select * from LREdorMain where polno='"
					+ tLCPolSchema.getPolNo() + "' and InsuredYear=" + tYear
					+ " and Cessstart<='" + this.mToday + "' and Cessend>='"
					+ this.mToday + "' ";
			LREdorMainSet tLREdorMainSet = new LREdorMainSet();
			tLREdorMainSet = tLREdorMainDB.executeQuery(tSql1);
			for (int k = 1; k <= tLREdorMainSet.size(); k++) {
				LREdorMainSchema tLREdorMainSchema = new LREdorMainSchema();
				tLREdorMainSchema = tLREdorMainSet.get(k);
				tPolRetainAmt = tPolRetainAmt
						+ tLREdorMainSchema.getChgCessAmt();
			}
			tSumRetainAmount = tSumRetainAmount + tPolRetainAmt;

		}
		tSum[0] = tSumRiskAmnt; // 累计风险保额
		tSum[1] = tSumRetainAmount; // 累计自留保额
		return tSum;
	}

	// 取得本次保单分保的计算自留额
	private double getThisRetain(double aRemainAmnt) {
		double tRetainCal = 0;
		tRetainCal = aRemainAmnt * this.mLRRiskSchema.getRetentRate();
		return tRetainCal;
	}

	// 取得分保保费
	private double getCessionPrem(double aCessionPremRate) {
		double tCessionPrem = 0;
		if (this.mLRRiskSchema.getCessPremCalCode() != null
				&& !this.mLRRiskSchema.getCessPremCalCode().equals("")) {
			Calculator mCalculator = new Calculator();
			mCalculator.setCalCode(this.mLRRiskSchema.getCessPremCalCode());
			// 增加基本要素
	    	mCalculator.addBasicFactor("Prem", String.valueOf(mLCPolSchema.getPrem()));
			// 保费
	    	String sql="SELECT NVL(SUM(prem), 0) from lcprem WHERE polno = '"+mLCPolSchema.getPolNo()
	    		+"' AND payplancode not like '000000%' AND length(dutycode)=6";
			mCalculator.addBasicFactor("StandPrem", String.valueOf(new ExeSQL().getOneValue(sql)));
			// 分保比例
			mCalculator.addBasicFactor("CessionRate", String
					.valueOf(mCessionRate));
			// 分保保额
			mCalculator.addBasicFactor("CessionAmnt", String
					.valueOf(mCessionAmount));
			// 性别
			mCalculator.addBasicFactor("Sex", String.valueOf(this.mLCPolSchema
					.getInsuredSex()));
			// 被保人当前年龄
			int tYear = PubFun.calInterval(this.mLCPolSchema.getCValiDate(),
					this.mToday, "Y")
					+ this.mLCPolSchema.getInsuredAppAge();
			mCalculator.addBasicFactor("InsuredAge", String.valueOf(tYear));
			// 保单号
			mCalculator.addBasicFactor("PolNo", String
					.valueOf(this.mLCPolSchema.getPolNo()));

			String tStr = "";
			tStr = mCalculator.calculate();
			if (tStr == null || tStr.trim().equals("")) {
				tCessionPrem = 0;
			} else {
				tCessionPrem = Double.parseDouble(tStr);
			}
		} else {
			tCessionPrem = 0;
		}

		// tCessionPrem = Double.parseDouble(new
		// DecimalFormat("0.00").format(tCessionPrem));
		return tCessionPrem;
	}

	// 取得分保的佣金
	private double getCommsion(double aPremium, double aCommRate) {
		double tCommsion = 0;
		if (this.mLRRiskSchema.getCessCommCalCode2() != null
				&& !this.mLRRiskSchema.getCessCommCalCode2().equals("")) {
			Calculator mCalculator = new Calculator();
			mCalculator.setCalCode(this.mLRRiskSchema.getCessCommCalCode2());
			// 增加基本要素
			// 保费
			mCalculator.addBasicFactor("CessionPrem", String.valueOf(aPremium));
			// 分保手续费比例
			mCalculator.addBasicFactor("CommRate", String.valueOf(aCommRate));
			// 保单年度
			int tYear = PubFun.calInterval(this.mLCPolSchema.getCValiDate(),
					this.mToday, "Y")+1;
			mCalculator.addBasicFactor("InsuredYear", String.valueOf(tYear));
			// 保单号
			mCalculator.addBasicFactor("PolNo", String
					.valueOf(this.mLCPolSchema.getPolNo()));
			// 标准保费
			mCalculator.addBasicFactor("StandPrem", String
					.valueOf(this.mLCPolSchema.getStandPrem()));

			String tStr = "";
			tStr = mCalculator.calculate();
			if (tStr == null || tStr.trim().equals("")) {
				tCommsion = 0;
			} else {
				tCommsion = Double.parseDouble(tStr);
			}
		} else {
			tCommsion = 0;
		}

		tCommsion = Double.parseDouble(new DecimalFormat("0.00")
				.format(tCommsion));
		return tCommsion;
	}

  /**
   * 判断是否需要作临时分保：
   * 1。超过商业合同分保自动接受限额上线的一部分保单；
   * 2。根据字段specflag来判断（其他、需要临分、自留）；
   * 3。根据加点数判断；
   * 返回：
   * 1 ： 其他（目前当作合同分保处理）
   * 2 ： 临时分保
   * 3 ： 不作分保
   */
  private int needToNegociate(double aCessAmount)
  {
      SSRS tssrs = new SSRS();
      ExeSQL tExeSQL = new ExeSQL();

      //根据团单特殊表示判断是否需要临分
      if(!this.mLCPolSchema.getGrpPolNo().equals("00000000000000000000"))
      {
          String sql = "select specflag from lcgrppol "
                     + "where grppolno = '" + this.mLCPolSchema.getGrpPolNo() + "' "; // 没有B表了
          tssrs = tExeSQL.execSQL(sql);
          if(tExeSQL.mErrors.needDealError() == false && tssrs != null && tssrs.getMaxRow() > 0)
          {
              //specflag - 2 需要临分；（1－其他；3－自留）－针对后两种可能还需要继续判断下面的逻辑
              if(tssrs.GetText(1,1).equals("2"))
                  return 2;
          }
      }

      //个人寿险次标准体加点超过175点也需做临分；个人重疾次标准体加点超过125点也需做临分；
      if(this.mItemFlag.equals("C"))
      {
          int tAddScore = 0;
          String sql =  "select sum(suppriskscore) from lcprem "
                     + "where polno = '" + this.mLCPolSchema.getPolNo() + "' and payplantype = '1' "; //只是次标准体加点
          tssrs = tExeSQL.execSQL(sql);
          if(tExeSQL.mErrors.needDealError() == false && tssrs != null && tssrs.getMaxRow() > 0 && tssrs.GetText(1,1)!=null &&!tssrs.GetText(1,1).equals("")&&!tssrs.GetText(1,1).equals("null")) 
              tAddScore = Integer.parseInt(tssrs.GetText(1,1));

          if(this.mLRRiskSchema.getRiskCalSort().equals("1"))                   //个人寿险
          {
              if(tAddScore > 175)
                  return 2;
          }
          else if(this.mLRRiskSchema.getRiskCalSort().equals("2"))              //个人重疾
          {
              if(tAddScore > 125)
                  return 2;
          }
      }

      //根据分保额判断是否需要临分
      if (this.mLRRiskSchema.getNegoCalCode()!=null && !this.mLRRiskSchema.getNegoCalCode().equals(""))
      {
          Calculator mCalculator = new Calculator();
          mCalculator.setCalCode( this.mLRRiskSchema.getNegoCalCode() );
          //增加基本要素
          //计算分出保额
          mCalculator.addBasicFactor("CessionAmount", String.valueOf(aCessAmount));
          //接收限额
          mCalculator.addBasicFactor("CessionLimit", String.valueOf(this.mLRRiskSchema.getCessionLimit()));
          //团体内个人保额上限
          mCalculator.addBasicFactor("GrpPerAmtLimit", String.valueOf(this.mLRRiskSchema.getGrpPerAmtLimit()));
          //团体人均保额上限
          mCalculator.addBasicFactor("GrpAverAmntLimit", String.valueOf(this.mLRRiskSchema.getGrpAverAmntLimit()));
          //个人保额
          int tInsuredNum=1;
          if (this.mLCPolSchema.getInsuredPeoples()!=0)
          {
              tInsuredNum=this.mLCPolSchema.getInsuredPeoples();
          }
          double tPersonalAmnt=this.mRiskAmnt/tInsuredNum;
          mCalculator.addBasicFactor("PersonalAmnt", String.valueOf(tPersonalAmnt));
          //团体人均保额
          if (!this.mLCPolSchema.getGrpPolNo().equals("00000000000000000000"))
          {
              int tNum=1;
              LCGrpPolBL tLCGrpPolBL = new LCGrpPolBL();
              tLCGrpPolBL.setGrpPolNo(this.mLCPolSchema.getGrpPolNo());
              if (tLCGrpPolBL.getInfo())
              {
                  tNum=tLCGrpPolBL.getPeoples2();
              }
              if(mLRRiskSchema.getDutyCode().equals("000000")){
	              if (tNum>0)
	              {
	                  mCalculator.addBasicFactor("GrpAverAmnt", String.valueOf(tLCGrpPolBL.getAmnt()/tNum));
	              }
	              else
	              {
	                  mCalculator.addBasicFactor("GrpAverAmnt", String.valueOf(tLCGrpPolBL.getAmnt()));
	              }
			} else {// 按责任分保
					String sql = "select nvl(sum(amnt),0) from lcduty a where exists(select 1 from lcpol where grppolno = '"
							+ mLCPolSchema.getGrpPolNo() + "' and polno=a.polno) and dutycode='"
							+ mLRRiskSchema.getDutyCode() + "'";
					String sumstr = new ExeSQL().getOneValue(sql);
					double sum = Double.parseDouble(sumstr);
					if (tNum > 0) {
						mCalculator.addBasicFactor("GrpAverAmnt", String
								.valueOf(sum / tNum));
					} else {
						mCalculator.addBasicFactor("GrpAverAmnt", String
								.valueOf(sum));
					}
			}
          }
          else //团体险种有个人销售,所以在统一的描述下应该准备数据
          {
              mCalculator.addBasicFactor("GrpAverAmnt", String.valueOf(this.mLCPolSchema.getRiskAmnt()));
          }
          //计算
          String tStr = mCalculator.calculate() ;
          if (tStr==null || tStr.trim().equals("")||tStr.equals("0"))
              return 1;
          else
              return 2;
      }
      return 1;
  }

	private double getCommsionRate() {
		if (this.mLRRiskSchema.getCommsionRate() > 0) // 针对佣金比率恒定
		{
			return this.mLRRiskSchema.getCommsionRate();
		} else {
			if (this.mLRPolSchema.getCessPrem() == 0) {
				return 0;
			}
			double trate = 0;
			trate = this.mLRPolSchema.getCessComm()
					/ this.mLRPolSchema.getCessPrem();
			return trate;
		}
	}


	// 获得第t个保单年度对应的费率
	private double getCessionPremRate() {
		double trate = 0;
		if (this.mLRPolSchema.getCessionAmount() == 0) {
			trate = 0;
		} else {
			trate = this.mLRPolSchema.getCessPrem()
					/ this.mLRPolSchema.getCessionAmount();
		}
		return trate;
	}

	private double getPolAddPrem() {
		LCPremBL tLCPremBL = new LCPremBL();
		tLCPremBL.setPolNo(this.mLCPolSchema.getPolNo());
		tLCPremBL.setPayPlanType("1");
		tLCPremBL.setModifyDate("");
		tLCPremBL.setModifyTime("");
		LCPremSet tLCPremSet = new LCPremSet();
		tLCPremSet.set(tLCPremBL.query());
		double tAddHePrem = 0;
		for (int i = 1; i <= tLCPremSet.size(); i++) {
			LCPremSchema tLCPremSchema = new LCPremSchema();
			tLCPremSchema = tLCPremSet.get(i);
			tAddHePrem = tAddHePrem + tLCPremSchema.getPrem();
		}
		return tAddHePrem;
	}

	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate() {
		return this.mOperate;
	}

	private void getInputData(VData cInputData) {
		mLCPolSchema = (LCPolSchema) cInputData.getObjectByObjectName(
				"LCPolSchema", 0);
		this.mItemFlag = (String) cInputData.get(1);
		// mReinsureCom=(String)cInputData.get(2);
		mToday = (String) cInputData.get(2);
		this.mLRPolSet = (LRPolSet) cInputData.get(3);
	}

	private boolean setCalDate() {
		int tyear = PubFun.calInterval(this.mLCPolSchema.getSignDate(),
				this.mToday, "Y");
		String tnewDate = PubFun.calDate(this.mLCPolSchema.getCValiDate(),
				tyear, "Y", "");
		if (this.mLCPolSchema.getCValiDate().equals(
				this.mLCPolSchema.getEndDate()))
		// 生效日＝结束日
		{
			if (!tnewDate.equals(this.mLCPolSchema.getCValiDate())) // 观察日!＝生效日
			{
				return false;
			}
		} else // 生效日!＝结束日
		{
			FDate tFDate = new FDate();
			Date tDateEnd = tFDate.getDate(this.mLCPolSchema.getEndDate());
			Date tDateObserve = tFDate.getDate(tnewDate);
			if (!tDateObserve.before(tDateEnd)) // 观察日>=结束日
			{
				return false;
			}
		}
		this.mToday = tnewDate;
		return true;
	}

	private double setRiskAmnt(LRRiskSchema tLRRiskSchema,
			LCPolSchema tLCPolSchema) {
		double tRiskAmnt = 0;
		if (tLRRiskSchema.getRiskAmntCalCode() != null
				&& !tLRRiskSchema.getRiskAmntCalCode().equals("")) {
			Calculator mCalculator = new Calculator();
			mCalculator.setCalCode(tLRRiskSchema.getRiskAmntCalCode());
			// 增加基本要素
			mCalculator.addBasicFactor("RiskAmnt", String.valueOf(tLCPolSchema
					.getRiskAmnt()));
			mCalculator.addBasicFactor("Amnt", String.valueOf(tLCPolSchema
					.getAmnt()));
			mCalculator.addBasicFactor("PolNo", String.valueOf(tLCPolSchema
					.getPolNo()));
			// 被保人当前年龄
			int tYear = PubFun.calInterval(tLCPolSchema.getCValiDate(),
					this.mToday, "Y")
					+ tLCPolSchema.getInsuredAppAge();
			mCalculator.addBasicFactor("InsuredAge", String.valueOf(tYear));
			// 交费满期
			mCalculator.addBasicFactor("PayEndDate", String
					.valueOf(tLCPolSchema.getPayEndDate()));
			// 计算日期
			mCalculator.addBasicFactor("CalDate", String.valueOf(this.mToday));
			String tStr = "";

			tStr = mCalculator.calculate();
			if (tStr == null || tStr.trim().equals("")) {
				tRiskAmnt = 0;
			} else {
				tRiskAmnt = Double.parseDouble(tStr);
			}
		} else {
			tRiskAmnt = 0;
		}
		return tRiskAmnt;
	}

}
