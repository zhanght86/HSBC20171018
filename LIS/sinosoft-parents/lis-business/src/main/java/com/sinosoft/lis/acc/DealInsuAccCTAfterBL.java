package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.bq.BqCode;
import com.sinosoft.lis.bq.EdorCalZT;

/**
 * <p>Title: </p>
 * <p>Description:投连后续处理投连险退保CT实现 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company:sinosoft </p>
 * 本次修改针对多币种改造，根据币种生成多条ljaget
 * @author:ck
 * @version 1.0
 */
public class DealInsuAccCTAfterBL extends DealInsuAccAfter {
private static Logger logger = Logger.getLogger(DealInsuAccCTAfterBL.class);
    public DealInsuAccCTAfterBL() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private VData mResult = new VData();
    /** 传出数据的容器 */
//    private LCInsureAccSet _LCInsureAccSet = new LCInsureAccSet();
//    private LCInsureAccClassSet _LCInsureAccClassSet = new LCInsureAccClassSet();
//    private LCInsureAccTraceSet _LCInsureAccTraceSet = new LCInsureAccTraceSet();
    /** 错误处理类 */
    public CErrors mErrors = new CErrors();
    //批改补退费记录
    private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
    private LJAGetSchema mLJAGetSchema = new LJAGetSchema();
    private LJAGetSet mLJAGetSet = new LJAGetSet();
    private LJAGetEndorseSet mLJAGetEndorseSet = new LJAGetEndorseSet();
//    private LJAGetEndorseSet moldLJAGetEndorseSet = new LJAGetEndorseSet();
    private LCPolSchema mLCPolSchema = new LCPolSchema();
    private LOPolAfterDealSchema mLOPolAfterDealSchema = new LOPolAfterDealSchema();
    private LCInsureAccSet mLCInsureAccSet=new LCInsureAccSet();
    private LCInsureAccClassSet  mLCInsureAccClassSet= new LCInsureAccClassSet();
//    private LOPolAfterDealSet mLOPolAfterDealSet=new LOPolAfterDealSet();

    private String mCurrentDate = PubFun.getCurrentDate();
    private String mCurrentTime = PubFun.getCurrentTime();
    private GlobalInput mGlobalInput = new GlobalInput();
    private MMap map = new MMap();
    private ExeSQL mExeSql = new ExeSQL();
    private LDExch ex = new LDExch();
    /*根据LOPolAfterDeal表中的账户信息进行处理*/
    public boolean dealAfter(GlobalInput tGlobalInput,
                             LOPolAfterDealSchema tLOPolAfterDealSchema) {
        /*退保后续处理逻辑*/
        mGlobalInput = tGlobalInput;
        mLOPolAfterDealSchema = tLOPolAfterDealSchema;
        String _DealDate = mLOPolAfterDealSchema.getDealDate();

        LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
        LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
        tLCInsureAccTraceDB.setAccAlterNo(mLOPolAfterDealSchema.getAccAlterNo());
        tLCInsureAccTraceDB.setAccAlterType(mLOPolAfterDealSchema.
                                            getAccAlterType());
        tLCInsureAccTraceDB.setBusyType(mLOPolAfterDealSchema.getBusyType());
        tLCInsureAccTraceDB.setState("0");
        tLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
        if (tLCInsureAccTraceSet.size() > 0) {
            CError.buildErr(this, "存在待计价的记录!");
            return false;
        }
        tLCInsureAccTraceDB.setState("1");
        tLCInsureAccTraceSet.clear();
        tLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
        if (tLCInsureAccTraceSet.size() == 0) {
            CError.buildErr(this, "不存在投连险退保记录!");
            return false;
        }

        if (!getInfo(tLCInsureAccTraceSet.get(1))) {
            return false;
        }

        String sEdorType = mLPEdorItemSchema.getEdorType();
        //计算保单年度
        int iInterval = PubFun.calInterval(mLCPolSchema.getCValiDate(),
                                           mLPEdorItemSchema.getEdorValiDate(),"Y");
        int iIntervalM = PubFun.calInterval(mLCPolSchema.getCValiDate(),
                                            mLPEdorItemSchema.getEdorValiDate(),"M");

        EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);
        double summoney = 0.00;
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        if ("1".equals(mLPEdorItemSchema.getStandbyFlag1())) { //犹豫期内
        	sqlbv.sql("Select distinct Currency From lcinsureacctrace a Where a.accalterno = '"+"?accalterno?"
                	+"' And a.accaltertype = '"+"?accaltertype?"+"' And a.busytype = '"+"?busytype?"+"' And state = '1'");
        	sqlbv.put("accalterno", mLOPolAfterDealSchema.getAccAlterNo());
        	sqlbv.put("accaltertype", mLOPolAfterDealSchema.getAccAlterType());
        	sqlbv.put("busytype", mLOPolAfterDealSchema.getBusyType());
			SSRS curSSRS = mExeSql.execSQL(sqlbv);//根据币种生成多条ljaget
			for(int c =1;c<=curSSRS.getMaxRow();c++){
				double curMoney = 0.0;
//				mLJAGetSchema = createLJAGet();
			   LJAGetSchema _LJAGetSchema = createLJAGet(curSSRS.GetText(c, 1));
			   //帐户价值
	            //下面的做法只是跟着表结构走，但是不知道这样有没有意义.
	            LCDutyDB tLCDutyDB = new LCDutyDB();
	            tLCDutyDB.setPolNo(mLCPolSchema.getPolNo());
	            LCDutySet tLCDutySet = tLCDutyDB.query();
	            if (tLCDutyDB.mErrors.needDealError()) {
	                CError.buildErr(this, "查询险种责任失败!");
	            }
	            if (tLCDutySet == null || tLCDutySet.size() < 1) {
	                CError.buildErr(this, "查询险种责任失败!");
	            }
	            for (int i = 1; i <= tLCDutySet.size(); i++) { //循环保单的责任项计算
	                LMDutyPayRelaSet tLMDutyPayRelaSet = new LMDutyPayRelaSet();
	                LMDutyPayRelaDB tLMDutyPayRelaDB = new LMDutyPayRelaDB();
	                String strdutycode = tLCDutySet.get(i).getDutyCode();
	                SQLwithBindVariables tsqlbv = new SQLwithBindVariables();
	                tsqlbv.sql("select * from LMDutyPayRela where dutycode='" + "?strdutycode?" + "'");
	                tsqlbv.put("strdutycode", strdutycode);
	                tLMDutyPayRelaSet = tLMDutyPayRelaDB.executeQuery(tsqlbv);
	                for (int j = 1; j <= tLMDutyPayRelaSet.size(); j++) { //该PayPlanCode下的帐户价值，
	                	SQLwithBindVariables ttsqlbv = new SQLwithBindVariables();
	                	String strpayplancode = tLMDutyPayRelaSet.get(j).getPayPlanCode();
	                	String strcurrency = curSSRS.GetText(c, 1);
	                	ttsqlbv.sql("Select 'X' From lcinsureaccclass Where polno = '"+"?polno?"
	    	                	+"' And payplancode = '"+"?strpayplancode?"+"' And currency = '"+"?strcurrency?"+"'");
	                	ttsqlbv.put("strpayplancode", strpayplancode);
	                	ttsqlbv.put("strcurrency", strcurrency);
	                	ttsqlbv.put("polno", mLCPolSchema.getPolNo());
	                	String tCur = mExeSql.getOneValue(ttsqlbv);
	                	if(tCur == null || "".equals(tCur) || !"X".equals(tCur)){
	                		continue;
	                	}
	                    LCInsureAccTraceDB sLCInsureAccTraceDB = new LCInsureAccTraceDB();
	                    sLCInsureAccTraceDB.setAccAlterNo(mLOPolAfterDealSchema.getAccAlterNo());
	                    sLCInsureAccTraceDB.setAccAlterType(mLOPolAfterDealSchema.getAccAlterType());
	                    sLCInsureAccTraceDB.setBusyType(mLOPolAfterDealSchema.getBusyType());
	                    sLCInsureAccTraceDB.setState("1");
	                    sLCInsureAccTraceDB.setPayPlanCode(tLMDutyPayRelaSet.get(j).getPayPlanCode());
	                    LCInsureAccTraceSet sLCInsureAccTraceSet = new LCInsureAccTraceSet();
	                    sLCInsureAccTraceSet = sLCInsureAccTraceDB.query();
	                    double TLAccValue = 0.0; //该PayPlanCode下的帐户帐户价值
	                    double tLastFee = 0.0; //该PayPlanCode下的帐户初始费用
	                    for (int k = 1; k <= sLCInsureAccTraceSet.size(); k++) {
	                        TLAccValue += sLCInsureAccTraceSet.get(k).getMoney();
	                        tLastFee += -getLastFee(sLCInsureAccTraceSet.get(k));
	                    }
	                    double riskfee = 0; //该PayPlanCode下的帐户风险管理费
	                    String strPayPlanCode = tLMDutyPayRelaSet.get(j).getPayPlanCode();
	                    SQLwithBindVariables tsqlbv1 = new SQLwithBindVariables();
	                    tsqlbv1.sql("select * from LCInsureAccFeeTrace where PolNo ='" + "?polno?" +
	                            "' and feecode in (select feecode from LMriskfee where feetakeplace='09') and PayPlanCode='" +
	                            "?strPayPlanCode?" + "'");
	                    tsqlbv1.put("polno", mLCPolSchema.getPolNo());
	                    tsqlbv1.put("strPayPlanCode", strPayPlanCode);
	                    LCInsureAccFeeTraceSet tLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
	                    LCInsureAccFeeTraceDB tLCInsureAccFeeTraceDB = new LCInsureAccFeeTraceDB();
	                    tLCInsureAccFeeTraceSet = tLCInsureAccFeeTraceDB.executeQuery(tsqlbv1);
	                    for (int k = 1; k <= tLCInsureAccFeeTraceSet.size(); k++) {
	                        riskfee += -tLCInsureAccFeeTraceSet.get(k).getFee();
	                    }
	                    // 该PayPlanCode下的总退费
	                    double Allvalue = 0.0;
	                    Allvalue = riskfee + TLAccValue + tLastFee;
	                    //  Allvalue =  TLAccValue + tLastFee;
	                    LJAGetEndorseSchema sLJAGetEndorseSchema = new
	                            LJAGetEndorseSchema();
	                    if (TLAccValue != 0.00) {
	                        sLJAGetEndorseSchema = createLJAGetEndorse(TLAccValue,
	                        		_LJAGetSchema, "TF",
	                                tLMDutyPayRelaSet.get(j).getPayPlanCode(),
	                                tLCDutySet.get(i).getDutyCode(),BqCode.Get_InvestAccValue,sLCInsureAccTraceSet.get(1).getCurrency());
	                        mLJAGetEndorseSet.add(sLJAGetEndorseSchema);
	                    }
	                    if (riskfee != 0.00) {
	                       sLJAGetEndorseSchema = createLJAGetEndorse(riskfee,
	                    		   _LJAGetSchema, "TF",
	                               tLMDutyPayRelaSet.get(j).getPayPlanCode(),
	                               tLCDutySet.get(i).getDutyCode(),BqCode.Get_InsuAccRiskFee,sLCInsureAccTraceSet.get(1).getCurrency());
	                       mLJAGetEndorseSet.add(sLJAGetEndorseSchema);
	                   }
	                   if (tLastFee != 0.00) {
	                     sLJAGetEndorseSchema = createLJAGetEndorse(tLastFee,
	                    		 _LJAGetSchema, "TF",
	                             tLMDutyPayRelaSet.get(j).getPayPlanCode(),
	                             tLCDutySet.get(i).getDutyCode(),BqCode.Get_InsuAccInFee,sLCInsureAccTraceSet.get(1).getCurrency());
	                     mLJAGetEndorseSet.add(sLJAGetEndorseSchema);
	                 }
	                   curMoney = curMoney + ( -Allvalue);
//	                   summoney = summoney + ( -Allvalue);

	                double IPfmPrem=0.00;
		              IPfmPrem =  getIPitemTF( tLMDutyPayRelaSet.get(j).getPayPlanCode(),  tLCDutySet.get(i).getDutyCode());
		              if(IPfmPrem!=0.00){
		                 LJAGetEndorseSchema tIPLJAGetEndorseSchema = new LJAGetEndorseSchema();
		                 tIPLJAGetEndorseSchema = createLJAGetEndorse(-IPfmPrem,
		            		 _LJAGetSchema, "TF", tLMDutyPayRelaSet.get(j).getPayPlanCode(), tLCDutySet.get(i).getDutyCode(),BqCode.Get_Prem,sLCInsureAccTraceSet.get(1).getCurrency());
		                 mLJAGetEndorseSet.add(tIPLJAGetEndorseSchema);
		             }
		              curMoney = curMoney + IPfmPrem;
//	                 summoney=summoney+IPfmPrem;

	               }
	            }
	            
	            //工本费
	          /*  LJAGetEndorseSchema tLJAGetEndorseSchema = new
	                    LJAGetEndorseSchema();
	            //计算工本费（犹豫期退保）
	            tEdorCalZT.mLPEdorItemSchema.setEdorType(sEdorType);
	            mLCPolSchema.setManageCom(mLPEdorItemSchema.getManageCom());
	            tEdorCalZT.setLCPolSchema(mLCPolSchema);
	            double dNoteMoney = tEdorCalZT.getWorkNoteMoney();
	            if (dNoteMoney != 0.00) {
	                tLJAGetEndorseSchema = createLJAGetEndorse(dNoteMoney,
	                        mLJAGetSchema, "GB", "000000", "000000",BqCode.Pay_WorkNoteFee);
	                mLJAGetEndorseSet.add(tLJAGetEndorseSchema);
	                summoney = summoney - dNoteMoney;
	            }*/
	            
	            summoney += ex.toBaseCur(curSSRS.GetText(c, 1), mLCPolSchema.getCurrency(), _DealDate, curMoney);
	            _LJAGetSchema.setSumGetMoney(curMoney);
	            if(curMoney !=0 ){
	            	mLJAGetSet.add(_LJAGetSchema);
	            }
	            
	            //对未计价的追加保费和续期保费进行处理
			}
        } else { //犹豫期外
        	SQLwithBindVariables tsqlbv0 = new SQLwithBindVariables();
        	tsqlbv0.sql("Select distinct Currency From lcinsureacctrace a Where a.accalterno = '"+"?accalterno?"
                	+"' And a.accaltertype = '"+"?accaltertype?"+"' And a.busytype = '"+"?busytype?"+"' And state = '1'");
        	tsqlbv0.put("accalterno", mLOPolAfterDealSchema.getAccAlterNo());
        	tsqlbv0.put("accaltertype", mLOPolAfterDealSchema.getAccAlterType());
        	tsqlbv0.put("busytype", mLOPolAfterDealSchema.getBusyType());
			SSRS curSSRS = mExeSql.execSQL(tsqlbv0);
			for(int c =1;c<=curSSRS.getMaxRow();c++){
				double curMoney = 0.0;
//				mLJAGetSchema = createLJAGet(curSSRS.GetText(c, 1));
			   LJAGetSchema _LJAGetSchema = createLJAGet(curSSRS.GetText(c, 1));
	           LCDutyDB tLCDutyDB = new LCDutyDB();
	           tLCDutyDB.setPolNo(mLCPolSchema.getPolNo());
	           LCDutySet tLCDutySet = tLCDutyDB.query();
	           if (tLCDutyDB.mErrors.needDealError()) {
	               CError.buildErr(this, "查询险种责任失败!");
	           }
	           if (tLCDutySet == null || tLCDutySet.size() < 1) {
	               CError.buildErr(this, "查询险种责任失败!");
	           }
	            for (int i = 1; i <= tLCDutySet.size(); i++) { //循环保单的责任项计算
	                LMDutyPayRelaSet tLMDutyPayRelaSet = new LMDutyPayRelaSet();
	                LMDutyPayRelaDB tLMDutyPayRelaDB = new LMDutyPayRelaDB();
	                String strdutycode = tLCDutySet.get(i).getDutyCode();
	                SQLwithBindVariables tsqlbv1 = new SQLwithBindVariables();
	                tsqlbv1.sql("select * from LMDutyPayRela where dutycode='" +
                            "?strdutycode?" + "'");
	                tsqlbv1.put("strdutycode", strdutycode);
	                tLMDutyPayRelaSet = tLMDutyPayRelaDB.executeQuery(tsqlbv1);
	                for (int j = 1; j <= tLMDutyPayRelaSet.size(); j++) { //该PayPlanCode下的帐户价值，
	                	String strpayplancode = tLMDutyPayRelaSet.get(j).getPayPlanCode();
	                	String strcurrency = curSSRS.GetText(c, 1);
	                	SQLwithBindVariables tsqlbv2 = new SQLwithBindVariables();
	                	tsqlbv2.sql("Select 'X' From lcinsureaccclass Where polno = '"+"?polno?"
	    	                	+"' And payplancode = '"+"?strpayplancode?"+"' And currency = '"+"?strcurrency?"+"'");
	                	tsqlbv2.put("polno", mLCPolSchema.getPolNo());
	                	tsqlbv2.put("strpayplancode", strpayplancode);
	                	tsqlbv2.put("strcurrency", strcurrency);
	                	String tCur = mExeSql.getOneValue(tsqlbv2);
	                	if(tCur == null || "".equals(tCur) || !"X".equals(tCur)){
	                		continue;
	                	}
	                    LCInsureAccTraceDB sLCInsureAccTraceDB = new LCInsureAccTraceDB();
	                    sLCInsureAccTraceDB.setAccAlterNo(mLOPolAfterDealSchema.getAccAlterNo());
	                    sLCInsureAccTraceDB.setAccAlterType(mLOPolAfterDealSchema.getAccAlterType());
	                    sLCInsureAccTraceDB.setBusyType(mLOPolAfterDealSchema.getBusyType());
	                    sLCInsureAccTraceDB.setState("1");
	                    sLCInsureAccTraceDB.setPayPlanCode(tLMDutyPayRelaSet.get(j).getPayPlanCode());
	                    LCInsureAccTraceSet sLCInsureAccTraceSet = new LCInsureAccTraceSet();
	                    sLCInsureAccTraceSet = sLCInsureAccTraceDB.query();
	                    double TLAccValue = 0.0; //该PayPlanCode下的帐户帐户价值
	                    for (int k = 1; k <= sLCInsureAccTraceSet.size(); k++) {
	                        TLAccValue += sLCInsureAccTraceSet.get(k).getMoney();
	                        // tLastFee+=-getLastFee(sLCInsureAccTraceSet.get(k));
	                    }
	                    // 该PayPlanCode下的总退保费用
	                    double Allvalue = 0.0;
	                    double dActuMoney = tEdorCalZT.getActuTLTBFee(
	                             TLAccValue, //账户价值
	                            mLCPolSchema.getRiskCode(), //险种
	                            sEdorType, //保全项目
	                            mLCPolSchema.getContNo(), //险种保单号
	                            //mLPEdorItemSchema.getEdorValiDate(),
	                          mLCPolSchema.getCValiDate(), //保单生效日期
	                            iInterval+1, //保单年度
	                           iIntervalM, //保单经过月份
	                            "CalFee");
	                    if (dActuMoney == -1) {
	                        mErrors.copyAllErrors(tEdorCalZT.mErrors);
	                        return false;
	                    }
	                    if(dActuMoney!=0.00){//dActuMoney是一个负数
	                    LJAGetEndorseSchema feeLJAGetEndorseSchema = new LJAGetEndorseSchema();
	                    feeLJAGetEndorseSchema= createLJAGetEndorse(-dActuMoney,
	                    		_LJAGetSchema, "TB", tLMDutyPayRelaSet.get(j).getPayPlanCode(),
	                                tLCDutySet.get(i).getDutyCode(),BqCode.Pay_TBFee,sLCInsureAccTraceSet.get(1).getCurrency());
	                      mLJAGetEndorseSet.add(feeLJAGetEndorseSchema);
	                    }
	                    Allvalue = TLAccValue;
	                    curMoney = curMoney + (-Allvalue)+dActuMoney;
//	                    summoney=summoney+(-Allvalue)+dActuMoney;
	                    LJAGetEndorseSchema sLJAGetEndorseSchema = new LJAGetEndorseSchema();
	                    if (Allvalue != 0.00) {
	                        sLJAGetEndorseSchema = createLJAGetEndorse(Allvalue,_LJAGetSchema, "TB",
	                                tLMDutyPayRelaSet.get(j).getPayPlanCode(),
	                                tLCDutySet.get(i).getDutyCode(),BqCode.Get_InvestAccValue,sLCInsureAccTraceSet.get(1).getCurrency());
	                        mLJAGetEndorseSet.add(sLJAGetEndorseSchema);
	                    }

	                    //对未计价的追加保费和续期保费进行处理
		               double IPfmPrem=0.00;
		               IPfmPrem =  getIPitemTF(tLMDutyPayRelaSet.get(j).getPayPlanCode(), tLCDutySet.get(i).getDutyCode());
		               if(IPfmPrem!=0.00){
		                    LJAGetEndorseSchema tIPLJAGetEndorseSchema = new
		                       LJAGetEndorseSchema();
		                  tIPLJAGetEndorseSchema = createLJAGetEndorse(-IPfmPrem,
		                		  _LJAGetSchema, "TB",  tLMDutyPayRelaSet.get(j).getPayPlanCode(), tLCDutySet.get(i).getDutyCode(),BqCode.Get_Prem,sLCInsureAccTraceSet.get(1).getCurrency());
		                  mLJAGetEndorseSet.add(tIPLJAGetEndorseSchema);
		                 }
		               curMoney = curMoney + IPfmPrem;
//		                summoney=summoney+IPfmPrem;

	                }
	            }
	            summoney += ex.toBaseCur(curSSRS.GetText(c, 1), mLCPolSchema.getCurrency(), _DealDate, curMoney);
	            _LJAGetSchema.setSumGetMoney(curMoney);
	            if(curMoney !=0 ){
	            	mLJAGetSet.add(_LJAGetSchema);
	            }
			}

			
        	}
        	SQLwithBindVariables tsqlbv3 = new SQLwithBindVariables();
            //附加险处理，为了使实付号码保持一致，把在复核时，附加险的财务数据的实付号码更新
        	tsqlbv3.sql("select * from Lcpol where contno='" + "?contno?" 
                	+ "' and riskcode<>'" + "?riskcode?" + "' ");
        	tsqlbv3.put("contno", mLCPolSchema.getContNo());
        	tsqlbv3.put("riskcode", mLCPolSchema.getRiskCode());
            LCPolSet tLCPolSet=new LCPolSet();
            LCPolDB  tLCPolDB=new LCPolDB();
            tLCPolSet=tLCPolDB.executeQuery(tsqlbv3);
            for(int i=1;i<=tLCPolSet.size();i++){
            	SQLwithBindVariables tsqlbv4 = new SQLwithBindVariables();
            	String strpolno = tLCPolSet.get(i).getPolNo();
            	String JaSQL = "select * from ljagetendorse where polno='" + "?strpolno?" +
                        "' and Endorsementno='" + "?endorsementno?" +
                        "'and FeeOperationType='"+ "?feeOperationtype?" + "'";
            	tsqlbv4.sql(JaSQL);
            	tsqlbv4.put("strpolno", strpolno);
            	tsqlbv4.put("endorsementno", mLPEdorItemSchema.getEdorNo());
            	tsqlbv4.put("feeOperationtype", mLPEdorItemSchema.getEdorType());
                LJAGetEndorseSet toldLJAGetEndorseSet = new LJAGetEndorseSet();
                LJAGetEndorseDB toldLJAGetEndorseDB = new LJAGetEndorseDB();
                toldLJAGetEndorseSet = toldLJAGetEndorseDB.executeQuery(tsqlbv4);
                logger.debug(JaSQL);
             if(toldLJAGetEndorseSet.size()>0){
            	 boolean hasLjaGet = false;
            	 SQLwithBindVariables tsqlbv5 = new SQLwithBindVariables();
            	 String stpolno = tLCPolSet.get(i).getPolNo();
            	 tsqlbv5.sql("Delete from ljagetendorse where polno='" + "?stpolno?" +
                         "' and Endorsementno='" + "?endorsementno?" + "'and FeeOperationType='"
                         + "?feeOperationtype?" + "'");
            	 tsqlbv5.put("stpolno", stpolno);
            	 tsqlbv5.put("endorsementno", mLPEdorItemSchema.getEdorNo());
            	 tsqlbv5.put("feeOperationtype", mLPEdorItemSchema.getEdorType());
                   map.put(tsqlbv5, "DELETE");
              //    moldLJAGetEndorseSet.add(toldLJAGetEndorseSet);
                  for(int j=1;j<= toldLJAGetEndorseSet.size();j++){
                    LJAGetEndorseSchema sLJAGetEndorseSchema = new LJAGetEndorseSchema();
                    sLJAGetEndorseSchema=toldLJAGetEndorseSet.get(j);
                    sLJAGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo());
                    sLJAGetEndorseSchema.setOperator(mGlobalInput.Operator);//mLJAGetSchema.getOperator());
                    for(int k =1;k<=mLJAGetSet.size();k++){
                    	if(mLJAGetSet.get(k).getCurrency().equals(sLJAGetEndorseSchema.getCurrency())){
                            sLJAGetEndorseSchema.setActuGetNo(mLJAGetSet.get(k).getActuGetNo());//mLJAGetSchema.getActuGetNo());//附加险随主险
                            mLJAGetSet.get(k).setSumGetMoney(mLJAGetSet.get(k).getSumGetMoney()+(-toldLJAGetEndorseSet.get(j).getGetMoney()));
                            hasLjaGet = true;
                            break;
                    	}
                    }
                    if(!hasLjaGet){
                    	LJAGetSchema _LJAGetSchema = createLJAGet(sLJAGetEndorseSchema.getCurrency());
                    	_LJAGetSchema.setSumGetMoney(sLJAGetEndorseSchema.getCurrency());
                    	sLJAGetEndorseSchema.setActuGetNo(_LJAGetSchema.getActuGetNo());
                    	mLJAGetSet.add(_LJAGetSchema);
                    }
                    summoney=summoney + ex.toBaseCur(sLJAGetEndorseSchema.getCurrency(), mLCPolSchema.getCurrency(), _DealDate, -toldLJAGetEndorseSet.get(j).getGetMoney());
                    mLJAGetEndorseSet.add(sLJAGetEndorseSchema);
                  }
             }
          }
            SQLwithBindVariables tsqlbv6 = new SQLwithBindVariables();
            tsqlbv6.sql("Delete From ljaget Where otherno = '"+"?otherno?"+"' and othernotype = '10' and EnterAccDate is not null");
            tsqlbv6.put("otherno", mLPEdorAppSchema.getEdorAcceptNo());
            map.put(tsqlbv6, "DELETE");

//            mLJAGetSchema.setSumGetMoney(summoney);
            mLPEdorItemSchema.setGetMoney(-summoney);
            mLPEdorAppSchema.setGetMoney( -summoney);
            mLOPolAfterDealSchema.setDealDate(PubFun.getCurrentDate());
            mLOPolAfterDealSchema.setState("2"); //手续处理完成
           //由于数据精度的问题，LCInsureAcc和LCInsureAccClass表的数据有残余数据。所以把单位数置0；
            LCInsureAccDB tLCInsureAccDB=new LCInsureAccDB();
            tLCInsureAccDB.setPolNo(mLCPolSchema.getPolNo());
            mLCInsureAccSet=tLCInsureAccDB.query();
            double unitCount=0.000000;
            double InsuAccBala=0.000;
            for(int i=1;i<=mLCInsureAccSet.size();i++)
            {
                mLCInsureAccSet.get(i).setUnitCount(unitCount);
                mLCInsureAccSet.get(i).setInsuAccBala(InsuAccBala);
            }
            LCInsureAccClassDB tLCInsureAccClassDB=new LCInsureAccClassDB();
            tLCInsureAccClassDB.setPolNo(mLCPolSchema.getPolNo());
             mLCInsureAccClassSet=tLCInsureAccClassDB.query();
            for(int i=1;i<=mLCInsureAccClassSet.size();i++)
            {
                mLCInsureAccClassSet.get(i).setUnitCount(unitCount);
                mLCInsureAccClassSet.get(i).setInsuAccBala(InsuAccBala);
            }
//            LOPolAfterDealDB  tLOPolAfterDealDB=new LOPolAfterDealDB();
//            tLOPolAfterDealDB.setContNo(mLCPolSchema.getContNo());
//             mLOPolAfterDealSet=tLOPolAfterDealDB.query();
//            for(int i=1;i<=mLOPolAfterDealSet.size();i++)
//            {
//                mLOPolAfterDealSet.get(i).setState("2");
//            }

            if (!updateAccInfo()) {
                CError.buildErr(this, "账户信息更新失败!");
                return false;
            }
            return true;
    }
    /*更新账户信息*/
    public boolean updateAccInfo() {
        VData tVData = new VData();
//        map.put(mLOPolAfterDealSet, "UPDATE");
        map.put(mLCInsureAccSet, "UPDATE");
        map.put(mLCInsureAccClassSet, "UPDATE");
//        map.put(moldLJAGetEndorseSet, "DELETE");
//        map.put(mLJAGetSchema, "DELETE&INSERT");
        map.put(mLJAGetSet, "DELETE&INSERT");
        map.put(mLJAGetEndorseSet, "DELETE&INSERT");
        map.put(mLOPolAfterDealSchema, "UPDATE"); //更新轨迹状态
        map.put(mLPEdorItemSchema, "UPDATE"); //更新保全项目表
        map.put(mLPEdorAppSchema, "UPDATE"); //更新保全申请表
        if (map != null && map.keySet().size() > 0)
            tVData.add(map);
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(tVData, "")) {
            return false;
        }
        return true;
    }

    /**
     * 计算没有进入帐户的追加保费和续期保费等的退费。
     * 在退保确认时为了不影响计价把没有计价的纪录放到P表
     *
     */
    private double getIPitemTF(String spayplancode,String sdutycode ){
          double IPfmPrem = 0.00; //
          SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
        String SQL = "select * from  LPInsureAccTrace where edorno='" + "?edorno?"
                     + "' and EdorType='" + "?edortype?" +
                     "' and PolNo ='" + "?polno?" +
                     "' and payplancode='"+"?spayplancode?"+"' and moneytype='BF' and state='0' ";
        sqlbv1.sql(SQL);
        sqlbv1.put("edorno", mLPEdorItemSchema.getEdorNo());
        sqlbv1.put("edortype", mLPEdorItemSchema.getEdorType());
        sqlbv1.put("polno", mLCPolSchema.getPolNo());
        sqlbv1.put("spayplancode", spayplancode);
        LPInsureAccTraceSet tLPInsureAccTraceSet = new LPInsureAccTraceSet();
        LPInsureAccTraceDB tLPInsureAccTraceDB = new LPInsureAccTraceDB();
        tLPInsureAccTraceSet = tLPInsureAccTraceDB.executeQuery(sqlbv1);
        // 在退保确认时有未计价的纪录
        if (tLPInsureAccTraceSet.size() > 0) {
            //取出放在LPInsureAccTrace表的资金
            double IPprem = 0.00;

            for (int i = 1; i <= tLPInsureAccTraceSet.size(); i++) {
                IPprem = IPprem + tLPInsureAccTraceSet.get(i).getMoney();
            }
            IPfmPrem = IPfmPrem + IPprem;
            //取出放在LPInsureAccFeeTrace表的初始费用。

            SQL = "select * from  LPInsureAccFeeTrace where edorno='" +
                  "?edorno?"
                  + "' and EdorType='" + "?edortype?" +
                  "' and PolNo ='" +
                  "?polno?" +
                    "' and payplancode='"+"?spayplancode?"+"' and otherno in (select otherno from  LPInsureAccTrace where edorno='" +
                  "?mLPEdorItemSchema?" + "' and EdorType='" +
                  "?mLPEdorItem?" + "' and PolNo ='" +
                  "?mLCPolSchema?" +
                  "'and payplancode='"+"?spayplan?"+"'  and moneytype='BF' and state='0' )";
            SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
            sqlbv11.sql(SQL);
            sqlbv11.put("edorno", mLPEdorItemSchema.getEdorNo());
            sqlbv11.put("edortype", mLPEdorItemSchema.getEdorType());
            sqlbv11.put("polno", mLCPolSchema.getPolNo());
            sqlbv11.put("spayplancode", spayplancode);
            sqlbv11.put("mLPEdorItemSchema", mLPEdorItemSchema.getEdorNo());
            sqlbv11.put("mLPEdorItem", mLPEdorItemSchema.getEdorType());
            sqlbv11.put("mLCPolSchema", mLCPolSchema.getPolNo());
            sqlbv11.put("spayplan", spayplancode);
            logger.debug(SQL);
            LPInsureAccFeeTraceSet tLPInsureAccFeeTraceSet = new
                    LPInsureAccFeeTraceSet();
            LPInsureAccFeeTraceDB tLPInsureAccFeeTraceDB = new
                    LPInsureAccFeeTraceDB();
            tLPInsureAccFeeTraceSet = tLPInsureAccFeeTraceDB.executeQuery(sqlbv11);
            double IPstarteFee = 0.00;
            for (int i = 1; i <= tLPInsureAccFeeTraceSet.size(); i++) {
                IPstarteFee = IPstarteFee + tLPInsureAccFeeTraceSet.get(i).getFee();
            }
            IPfmPrem = IPfmPrem + IPstarteFee;
        }
        return IPfmPrem;
    }


    /**
     * 根据前面的输入数据，进行逻辑处理
     ** @param pLCInsureAccTraceSchema LCInsureAccTraceSchema
     * @return boolean
     * @return boolean
     */
    private boolean getInfo(LCInsureAccTraceSchema pLCInsureAccTraceSchema) {
        //查询保全项目信息
        LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
        tLPEdorItemDB.setEdorNo(pLCInsureAccTraceSchema.getAccAlterNo());
        tLPEdorItemDB.setEdorType(pLCInsureAccTraceSchema.getBusyType());
        tLPEdorItemDB.setContNo(pLCInsureAccTraceSchema.getContNo());
        LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
        if (tLPEdorItemDB.mErrors.needDealError()) {
            mErrors.copyAllErrors(tLPEdorItemDB.mErrors);
            mErrors.addOneError(new CError("查询批改项目信息失败!"));
            return false;
        }
        if (tLPEdorItemSet == null || tLPEdorItemSet.size() < 1) {
            CError.buildErr(this, "查询批改项目信息失败!");
            return false;
        }
        mLPEdorItemSchema = tLPEdorItemSet.get(1);

        //查询险种保单详细信息
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(pLCInsureAccTraceSchema.getPolNo());
        LCPolSet tLCPolSet = tLCPolDB.query();
        if (tLCPolDB.mErrors.needDealError()) {
            CError.buildErr(this, "险种信息查询失败!");
            return false;
        }
        if (tLCPolSet == null || tLCPolSet.size() != 1) {
            CError.buildErr(this, "没有查到险种信息!");
            return false;
        }
        mLCPolSchema.setSchema(tLCPolSet.get(1));

        LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
        tLPEdorAppDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
        if (!tLPEdorAppDB.getInfo()) {
            // @@错误处理
            mErrors.copyAllErrors(tLPEdorAppDB.mErrors);
            CError tError = new CError();
            tError.errorMessage = "保全受理查询失败!";
            mErrors.addOneError(tError);
            return false;
        }
        mLPEdorAppSchema = tLPEdorAppDB.getSchema();

        //创建或更新付费总表 根据币种查询结果为Set集合，循环更新太复杂，直接删除重新生成，对于enteraccdate非空的没有处理，逻辑上应为错误数据
//        LJAGetDB tLJAGetDB = new LJAGetDB();
//        tLJAGetDB.setOtherNo(mLPEdorAppSchema.getEdorAcceptNo());
//        LJAGetSet tLJAGetSet = tLJAGetDB.query();
//        if (tLJAGetDB.mErrors.needDealError()) {
//            CError.buildErr(this, "保全实付总表查询失败!");
//            return false;
//        }
//        if (tLJAGetSet == null || tLJAGetSet.size() < 1) { //创建
//            createLJAGetSchema();
//        } else if (tLJAGetSet.get(1).getEnterAccDate() != null &&
//                   !tLJAGetSet.get(1).getEnterAccDate().trim().equals("")) { //已经到账，创建
//            createLJAGetSchema();
//        } else { //更新
//            mLJAGetSchema = tLJAGetSet.get(1);
//        }

        return true;
    }

    /**
     * 查询帐户期初扣费
     * @param sInsuAccNo
     * @param sPolNo
     * @return double
     */
    public double getLastFee(LCInsureAccTraceSchema tLCInsureAccTraceSchema) {
    	SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        String sql = " select (case when sum(fee) is not null then sum(fee)  else 0 end) from lcinsureaccfeetrace m " +
                " where feecode in (select feecode from lmriskfee where feetakeplace = '01' and insuaccno=m.insuaccno and payplancode=m.payplancode) " +
                " and polno = '" + "?polno?" +
                "' and insuaccno='" + "?insuaccno?" +
                "' and payplancode='" + "?payplancode?" +
                "'";
        sqlbv.sql(sql);
        sqlbv.put("polno", tLCInsureAccTraceSchema.getPolNo());
        sqlbv.put("insuaccno", tLCInsureAccTraceSchema.getInsuAccNo());
        sqlbv.put("payplancode", tLCInsureAccTraceSchema.getPayPlanCode());
        ExeSQL tExeSQL = new ExeSQL();
        String sVPU = tExeSQL.getOneValue(sqlbv);
        if (tExeSQL.mErrors.needDealError()) {
            CError.buildErr(this, "查询账户期初扣费失败!");
            return -1;
        }
        if (sVPU == null || sVPU.trim().equals("")) {
            CError.buildErr(this, "VPU为空!");
            return -1;
        }
        double dFee = 0.0;
        try {
            dFee = Double.parseDouble(sVPU);
        } catch (Exception e) {
            CError.buildErr(this, "账户期初扣费查询结果错误!" +
                            "错误结果：" + sVPU);
            return -1;
        }

        return dFee;
    }


    /*创建批改补退费表结构*/
    public LJAGetEndorseSchema createLJAGetEndorse(
            LCInsureAccTraceSchema aLCInsureAccTraceSchema,
            LJAGetSchema aLJAGetSchema,double money,
            String aFeeType, String sSubFeeOperationType,String tCurrency) {
        LJAGetEndorseSchema tLJAGetEndorseSchema = new LJAGetEndorseSchema();
        try {
            //生成批改交退费表
            tLJAGetEndorseSchema.setActuGetNo(aLJAGetSchema.getActuGetNo());
            tLJAGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo()); //给付通知书号码
            tLJAGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
            tLJAGetEndorseSchema.setGrpContNo(mLCPolSchema.getGrpContNo());
            tLJAGetEndorseSchema.setContNo(mLCPolSchema.getContNo());
            tLJAGetEndorseSchema.setGrpPolNo(mLCPolSchema.getGrpPolNo());
            tLJAGetEndorseSchema.setPolNo(mLCPolSchema.getPolNo());
            tLJAGetEndorseSchema.setGetDate(mLPEdorItemSchema.getEdorValiDate());
            tLJAGetEndorseSchema.setGetMoney(money);
            tLJAGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema.getEdorType()); //补退费业务类型
            tLJAGetEndorseSchema.setSubFeeOperationType(sSubFeeOperationType); //补退费子业务类型
            tLJAGetEndorseSchema.setFeeFinaType(aFeeType); //补退费财务类型
           // tLJAGetEndorseSchema.setPayPlanCode(aLCInsureAccTraceSchema.getPayPlanCode()); //无作用
              tLJAGetEndorseSchema.setPayPlanCode("000000");
            tLJAGetEndorseSchema.setDutyCode(aLCInsureAccTraceSchema.getInsuAccNo());
            tLJAGetEndorseSchema.setOtherNo(aLCInsureAccTraceSchema.getOtherNo()); //其他号码置为保全批单号
            tLJAGetEndorseSchema.setOtherNoType("3"); //保全给付
            tLJAGetEndorseSchema.setGetFlag("0");
            tLJAGetEndorseSchema.setAgentCode(mLCPolSchema.getAgentCode());
            tLJAGetEndorseSchema.setAgentCom(mLCPolSchema.getAgentCom());
            tLJAGetEndorseSchema.setAgentGroup(mLCPolSchema.getAgentGroup());
            tLJAGetEndorseSchema.setAgentType(mLCPolSchema.getAgentType());
            tLJAGetEndorseSchema.setKindCode(mLCPolSchema.getKindCode());
            tLJAGetEndorseSchema.setAppntNo(mLCPolSchema.getAppntNo());
            tLJAGetEndorseSchema.setRiskCode(mLCPolSchema.getRiskCode());
            tLJAGetEndorseSchema.setRiskVersion(mLCPolSchema.getRiskVersion());
            tLJAGetEndorseSchema.setApproveCode(mLCPolSchema.getApproveCode());
            tLJAGetEndorseSchema.setApproveDate(mLCPolSchema.getApproveDate());
            tLJAGetEndorseSchema.setApproveTime(mLCPolSchema.getApproveTime());
            tLJAGetEndorseSchema.setManageCom(mLCPolSchema.getManageCom());
            tLJAGetEndorseSchema.setOperator(aLJAGetSchema.getOperator());
            tLJAGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
            tLJAGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
            tLJAGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
            tLJAGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
            tLJAGetEndorseSchema.setCurrency(tCurrency);
        } catch (Exception ex) {
            mErrors.addOneError(new CError("建立批改补退费信息异常！"));
            return null;
        }
        return tLJAGetEndorseSchema;
    }
    /*创建批改补退费表结构*/
       /*public LJAGetEndorseSchema createLJAGetEndorse(
               LCInsureAccFeeTraceSchema aLCInsureAccTraceFeeSchema,
               LJAGetSchema aLJAGetSchema,double money,
               String aFeeType, String sSubFeeOperationType) {
           LJAGetEndorseSchema tLJAGetEndorseSchema = new LJAGetEndorseSchema();
           try {
               //生成批改交退费表
               tLJAGetEndorseSchema.setActuGetNo(aLJAGetSchema.getActuGetNo());
               tLJAGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo()); //给付通知书号码
               tLJAGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.
                                                     getEdorNo());
               tLJAGetEndorseSchema.setGrpContNo(mLCPolSchema.getGrpContNo());
               tLJAGetEndorseSchema.setContNo(mLCPolSchema.getContNo());
               tLJAGetEndorseSchema.setGrpPolNo(mLCPolSchema.getGrpPolNo());
               tLJAGetEndorseSchema.setPolNo(mLCPolSchema.getPolNo());
               tLJAGetEndorseSchema.setGetDate(mLPEdorItemSchema.
                                               getEdorValiDate());
               tLJAGetEndorseSchema.setGetMoney(money);
               tLJAGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema.
                       getEdorType()); //补退费业务类型
               tLJAGetEndorseSchema.setSubFeeOperationType(sSubFeeOperationType); //补退费子业务类型
               tLJAGetEndorseSchema.setFeeFinaType(aFeeType); //补退费财务类型
               tLJAGetEndorseSchema.setPayPlanCode("000000"); //无作用
               tLJAGetEndorseSchema.setDutyCode(aLCInsureAccTraceFeeSchema.
                                                getInsuAccNo());
               tLJAGetEndorseSchema.setOtherNo(aLCInsureAccTraceFeeSchema.getOtherNo()); //其他号码置为保全批单号
               tLJAGetEndorseSchema.setOtherNoType("3"); //保全给付
               tLJAGetEndorseSchema.setGetFlag("0");
               tLJAGetEndorseSchema.setAgentCode(mLCPolSchema.getAgentCode());
               tLJAGetEndorseSchema.setAgentCom(mLCPolSchema.getAgentCom());
               tLJAGetEndorseSchema.setAgentGroup(mLCPolSchema.getAgentGroup());
               tLJAGetEndorseSchema.setAgentType(mLCPolSchema.getAgentType());
               tLJAGetEndorseSchema.setKindCode(mLCPolSchema.getKindCode());
               tLJAGetEndorseSchema.setAppntNo(mLCPolSchema.getAppntNo());
               tLJAGetEndorseSchema.setRiskCode(mLCPolSchema.getRiskCode());
               tLJAGetEndorseSchema.setRiskVersion(mLCPolSchema.getRiskVersion());
               tLJAGetEndorseSchema.setApproveCode(mLCPolSchema.getApproveCode());
               tLJAGetEndorseSchema.setApproveDate(mLCPolSchema.getApproveDate());
               tLJAGetEndorseSchema.setApproveTime(mLCPolSchema.getApproveTime());
               tLJAGetEndorseSchema.setManageCom(mLCPolSchema.getManageCom());
               tLJAGetEndorseSchema.setOperator(aLJAGetSchema.getOperator());
               tLJAGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
               tLJAGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
               tLJAGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
               tLJAGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
           } catch (Exception ex) {
               mErrors.addOneError(new CError("建立批改补退费信息异常！"));
               return null;
           }
           return tLJAGetEndorseSchema;
       }
       */

    /*创建批改补退费表结构*/
    public LJAGetEndorseSchema createLJAGetEndorse(double Money,
                                           LJAGetSchema aLJAGetSchema,String FeeFinaType,String PlanCode,String DutyCode,String SubFeeOperationType,String tCurrency) {
        LJAGetEndorseSchema tLJAGetEndorseSchema = new LJAGetEndorseSchema();
        try {
            //生成批改交退费表
            tLJAGetEndorseSchema.setActuGetNo(aLJAGetSchema.getActuGetNo());
            tLJAGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo()); //给付通知书号码
            tLJAGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
            tLJAGetEndorseSchema.setGrpContNo(mLCPolSchema.getGrpContNo());
            tLJAGetEndorseSchema.setContNo(mLCPolSchema.getContNo());
            tLJAGetEndorseSchema.setGrpPolNo(mLCPolSchema.getGrpPolNo());
            tLJAGetEndorseSchema.setPolNo(mLCPolSchema.getPolNo());
            tLJAGetEndorseSchema.setGetDate(mLPEdorItemSchema.getEdorValiDate());
            tLJAGetEndorseSchema.setGetMoney(Money);
            tLJAGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema.getEdorType()); //补退费业务类型
            tLJAGetEndorseSchema.setSubFeeOperationType(SubFeeOperationType); //补退费子业务类型
            tLJAGetEndorseSchema.setFeeFinaType(FeeFinaType); //补退费财务类型
            tLJAGetEndorseSchema.setPayPlanCode(PlanCode);
            tLJAGetEndorseSchema.setDutyCode(DutyCode);
            tLJAGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo()); //其他号码置为保全批单号
            tLJAGetEndorseSchema.setOtherNoType("3"); //保全给付
            tLJAGetEndorseSchema.setGetFlag("0");
            tLJAGetEndorseSchema.setAgentCode(mLCPolSchema.getAgentCode());
            tLJAGetEndorseSchema.setAgentCom(mLCPolSchema.getAgentCom());
            tLJAGetEndorseSchema.setAgentGroup(mLCPolSchema.getAgentGroup());
            tLJAGetEndorseSchema.setAgentType(mLCPolSchema.getAgentType());
            tLJAGetEndorseSchema.setKindCode(mLCPolSchema.getKindCode());
            tLJAGetEndorseSchema.setAppntNo(mLCPolSchema.getAppntNo());
            tLJAGetEndorseSchema.setRiskCode(mLCPolSchema.getRiskCode());
            tLJAGetEndorseSchema.setRiskVersion(mLCPolSchema.getRiskVersion());
            tLJAGetEndorseSchema.setApproveCode(mLCPolSchema.getApproveCode());
            tLJAGetEndorseSchema.setApproveDate(mLCPolSchema.getApproveDate());
            tLJAGetEndorseSchema.setApproveTime(mLCPolSchema.getApproveTime());
            tLJAGetEndorseSchema.setManageCom(mLCPolSchema.getManageCom());
            tLJAGetEndorseSchema.setOperator(aLJAGetSchema.getOperator());
            tLJAGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
            tLJAGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
            tLJAGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
            tLJAGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
            tLJAGetEndorseSchema.setCurrency(tCurrency);
        } catch (Exception ex) {
            mErrors.addOneError(new CError("建立批改补退费信息异常！"));
            return null;
        }
        return tLJAGetEndorseSchema;
    }

    /*创建实付表结构*/
    public LJAGetSchema createLJAGet(String tCurrency) {
        LJAGetSchema mLJAGetSchema = new LJAGetSchema();
        String tLimit = PubFun.getNoLimit(mLPEdorItemSchema.getManageCom());
        String sActNoticeNo = PubFun1.CreateMaxNo("GETNOTICENO", tLimit);
        String sActuGetNo = PubFun1.CreateMaxNo("GETNO", tLimit);
        mLJAGetSchema.setGetNoticeNo(sActNoticeNo);
        mLJAGetSchema.setActuGetNo(sActuGetNo);
        mLJAGetSchema.setOtherNo(mLPEdorItemSchema.getEdorAcceptNo());
        mLJAGetSchema.setOtherNoType(BqCode.LJ_OtherNoType);
        mLJAGetSchema.setBankCode(mLPEdorAppSchema.getBankCode());
        mLJAGetSchema.setBankAccNo(mLPEdorAppSchema.getBankAccNo());
        mLJAGetSchema.setAccName(mLPEdorAppSchema.getAccName());
        mLJAGetSchema.setPayMode(mLPEdorAppSchema.getGetForm());
        mLJAGetSchema.setOperator(mGlobalInput.Operator);
        mLJAGetSchema.setMakeDate(mCurrentDate);
        mLJAGetSchema.setMakeTime(mCurrentTime);
        mLJAGetSchema.setModifyDate(mCurrentDate);
        mLJAGetSchema.setModifyTime(mCurrentTime);
        mLJAGetSchema.setManageCom(mLCPolSchema.getManageCom());
        mLJAGetSchema.setDrawer(mLPEdorAppSchema.getPayGetName()); //领取人
        mLJAGetSchema.setDrawerID(mLPEdorAppSchema.getPersonID()); //身份证号
        mLJAGetSchema.setShouldDate(mCurrentDate);
        mLJAGetSchema.setCurrency(tCurrency);
        return mLJAGetSchema;
    }

    /**
     * 准备付费总表数据
     * @param aGetMoney
     * @param sManageCom
     * @return LJSGetSchema
     */
    public void createLJAGetSchema() {
        mLJAGetSchema = new LJAGetSchema();

        String tLimit = PubFun.getNoLimit(mLPEdorItemSchema.getManageCom());
        String sActNoticeNo = PubFun1.CreateMaxNo("GETNOTICENO", tLimit);
        String sActuGetNo = PubFun1.CreateMaxNo("GETNO", tLimit);

        mLJAGetSchema.setGetNoticeNo(sActNoticeNo);
        mLJAGetSchema.setActuGetNo(sActuGetNo);
        mLJAGetSchema.setOtherNo(mLPEdorAppSchema.getEdorAcceptNo());
        mLJAGetSchema.setOtherNoType(BqCode.LJ_OtherNoType);
        mLJAGetSchema.setBankCode(mLPEdorAppSchema.getBankCode());
        mLJAGetSchema.setBankAccNo(mLPEdorAppSchema.getBankAccNo());
        mLJAGetSchema.setAccName(mLPEdorAppSchema.getAccName());
        mLJAGetSchema.setPayMode(mLPEdorAppSchema.getGetForm());
        mLJAGetSchema.setOperator(mGlobalInput.Operator);
        mLJAGetSchema.setMakeDate(mCurrentDate);
        mLJAGetSchema.setMakeTime(mCurrentTime);
        mLJAGetSchema.setModifyDate(mCurrentDate);
        mLJAGetSchema.setModifyTime(mCurrentTime);
        mLJAGetSchema.setManageCom(mLCPolSchema.getManageCom());

        mLJAGetSchema.setDrawer(mLPEdorAppSchema.getPayGetName()); //领取人
        mLJAGetSchema.setDrawerID(mLPEdorAppSchema.getPersonID()); //身份证号
        mLJAGetSchema.setShouldDate(mCurrentDate);
        mLJAGetSchema.setCurrency(mLCPolSchema.getCurrency());

        return;
    }


    public VData getResult() {
        return mResult;
    }

    public CErrors getErrors() {
        return null;
    }

    private void jbInit() throws Exception {
    }
}
