package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.operfee.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;

import com.sinosoft.utility.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author HZM
 * @version 1.0
 */
public class NormPayGrpChooseOperBL
{
private static Logger logger = Logger.getLogger(NormPayGrpChooseOperBL.class);

    //错误处理类，每个需要错误处理的类中都放置该类
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData;

    /** 数据操作字符串 */
    private String mOperate;
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private String serNo = ""; //流水号
    private String tLimit = "";
    private String payNo = ""; //交费收据号
    private String mPayType = ""; //add by liuqh
    private String mPayTypeleft ="";//add by liuqh
    private String mMoneyType = ""; //add by liuqh
    private VData mResult = new VData(); //用于提交数据 add by liuqh 2009-03-23
    private GlobalInput mGlobalInput = new GlobalInput();

    //暂收费表
    private LJTempFeeBL mLJTempFeeBL = new LJTempFeeBL();
    private LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();

    //暂收费分类表
    private LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();
    private LJTempFeeClassSet mLJTempFeeClassNewSet = new LJTempFeeClassSet();

    //应收个人交费表
    private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
    private LJSPayPersonSet mLJSPayPersonNewSet = new LJSPayPersonSet();

    //实收个人交费表
    private LJAPayPersonSet mLJAPayPersonSet = new LJAPayPersonSet();

    //实收集体交费表
    private LJAPayGrpBL mLJAPayGrpBL = new LJAPayGrpBL();
    private LJAPayGrpSet mLJAPayGrpSet = new LJAPayGrpSet();

    //实收总表
    private LJAPayBL mLJAPayBL = new LJAPayBL();
    private LJAPaySet mLJAPaySet = new LJAPaySet();

    //个人保单表
    private LCPolSet mLCPolSet = new LCPolSet();

    //集体保单表
    private LCGrpPolBL mLCGrpPolBL = new LCGrpPolBL();
    private LCGrpPolSet mLCGrpPolSet = new LCGrpPolSet();
    
    //团体合同表
    private LCGrpContSet mLCGrpContSet =new LCGrpContSet();

    //保费项表
    private LCPremSet mLCPremSet = new LCPremSet();
    private LCPremSet mLCPremNewSet = new LCPremSet();

    //保险责任表LCDuty
    private LCDutySet mLCDutySet = new LCDutySet();

    //保险帐户表
    private LCInsureAccSet mLCInsureAccSet = new LCInsureAccSet();

    //保险帐户表记价履历表
    private LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();
    //账户管理履历表
    private LCInsureAccFeeTraceSet mLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();

    //业务处理相关变量
    public NormPayGrpChooseOperBL()
    {
    }

    public static void main(String[] args)
    {
        GlobalInput mGI = new GlobalInput();
        mGI.Operator = "001";
        mGI.ComCode = "86110000";
        mGI.ManageCom = "86110000";

        LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
        tLCGrpPolSchema.setGrpPolNo("86110020030220000054");
        tLCGrpPolSchema.setPaytoDate("2003-9-14");
        tLCGrpPolSchema.setManageCom("86110000");
        tLCGrpPolSchema.setOperator("001");
        tLCGrpPolSchema.setManageFeeRate(0);

        NormPayGrpChooseOperBL tNormPayGrpChooseOperBL = new NormPayGrpChooseOperBL();
        VData tVData = new VData();
        tVData.add(tLCGrpPolSchema);
        tNormPayGrpChooseOperBL.submitData(tVData, "VERIFY");
    }

    //传输数据的公共方法
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        //this.mOperate = cOperate;
       //add by guo xiang
        this.mOperate = verifyOperate(cOperate);

        if (mOperate.equals(""))
        {
            CError tError = new CError();
            tError.moduleName = "NormPayGrpChooseOperBL";
            tError.functionName = "submitData";
            tError.errorMessage = "传输数据失败!";
            this.mErrors.addOneError(tError);

            return false;
        }

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
        {
            return false;
        }
        logger.debug("After getinputdata");

        //进行业务处理
        if (!dealData())
        {
            return false;
        }
        logger.debug("After dealData！");

        //准备往后台的数据
        if (!prepareOutputData())
        {
            return false;
        }
        logger.debug("After prepareOutputData");

//        logger.debug("Start NormPayGrpChooseOper BL Submit...");
//
//        NormPayGrpChooseOperBLS tNormPayGrpChooseOperBLS = new NormPayGrpChooseOperBLS();
//        tNormPayGrpChooseOperBLS.submitData(mInputData, cOperate);
//
//        logger.debug("End LJNormPayGrpChooseOper BL Submit...");

        //如果有需要处理的错误，则返回
//        if (tNormPayGrpChooseOperBLS.mErrors.needDealError())
//        {
//            this.mErrors.copyAllErrors(tNormPayGrpChooseOperBLS.mErrors);
//        }
        PubSubmit tPubSubmit = new PubSubmit();

		if (!tPubSubmit.submitData(mResult, cOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			return false;
		}

        mInputData = null;

        return true;
    }

    //根据前面的输入数据，进行逻辑处理
    //如果在处理过程中出错，则返回false,否则返回true
    private boolean dealData()
    {
        boolean tReturn = false;
        String sqlStr = "";
        String GetNoticeNo = ""; //通知书号
//        String GrpPolNo = mLCGrpPolBL.getGrpPolNo();
        String GrpContNo = mLCGrpPolBL.getGrpContNo();
        String Operator = mLCGrpPolBL.getOperator();
        String ManageCom = mLCGrpPolBL.getManageCom();
        String PayDate = mLCGrpPolBL.getPaytoDate(); //实际是页面接受的交费日期传进来
        
        String RCsql = "select riskcode from lcgrppol where grpcontno='"+mLCGrpPolBL.getGrpContNo()+"'";
        ExeSQL tExeSQL=new ExeSQL();
        String RiskCode=tExeSQL.getOneValue(RCsql);
        logger.debug("RiskCode : "+RiskCode);
        //健康险新增
        LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
//        tLMRiskAppDB.setRiskType("H");
//        tLMRiskAppDB.setHealthType("1");
        tLMRiskAppDB.setRiskCode(RiskCode);
//        tLMRiskAppSet = tLMRiskAppDB.query();
        if(tLMRiskAppDB.getInfo())
        {
        	if("1".equals(tLMRiskAppDB.getHealthType()))
        	{
	        	mPayType = "TM";
	        	mPayTypeleft = "YETTM";
	        	mMoneyType = "TM";
        	}
	        else
	        {
	        	mPayType = "ZC";
	        	mPayTypeleft = "YET";
	        	mMoneyType = "BF";
	        }
        }

        try
        {
            //产生流水号
            tLimit = PubFun.getNoLimit(ManageCom);
            serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);

            //产生交费收据号
            //tLimit=PubFun.getNoLimit(mLJTempFeeBL.getManageCom());
            payNo = PubFun1.CreateMaxNo("PAYNO", tLimit);

            //step one-查询数据
            //1-查询集体保单表
            LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
            LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();

            sqlStr = "select * from LCGrpPol where grpcontno='" + GrpContNo
                     + "' ";
            sqlStr = sqlStr
                     + "and grpcontno not in (select grpcontno from LJSPayGrp where grpcontno='"
                     + GrpContNo + "' )";
            logger.debug("sqlStr=" + sqlStr);
            tLCGrpPolSet = tLCGrpPolDB.executeQuery(sqlStr);
            if (tLCGrpPolDB.mErrors.needDealError() == true)
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tLCGrpPolDB.mErrors);

                CError tError = new CError();
                tError.moduleName = "NormPayGrpChooseOperBL";
                tError.functionName = "dealData";
                tError.errorMessage = "集体保单表查询失败!";
                this.mErrors.addOneError(tError);
                tLCGrpPolSet.clear();

                return false;
            }
            if (tLCGrpPolSet.size() < 1)
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "NormPayGrpChooseOperBL";
                tError.functionName = "dealData";
                tError.errorMessage = "集体保单表没有查询到相关纪录!";
                this.mErrors.addOneError(tError);
                tLCGrpPolSet.clear();

                return false;
            }

            //保存查询出来的集体保单表後用
            mLCGrpPolBL.setSchema(tLCGrpPolSet.get(1));
            mLCGrpPolBL.setPaytoDate(CurrentDate);
            mLCGrpPolBL.setModifyDate(CurrentDate);
            mLCGrpPolBL.setModifyTime(CurrentTime);

            logger.debug("查询应收表");

            //2查询应收表：两个集合：1 符合集体保单号的应收纪录 2 符合集体保单号和录入标记的应收纪录
            //对集合2循环处理:更新对应的个人保单表，保费项表，填充实收表
            //对集合1添加到VData中，最后全部删除
            //2-1 得到应收个人集合1
            LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
            sqlStr = "select * from LJSPayPerson where polno in (select polno from lcpol where GrpContNo='" + GrpContNo
                     + "') and paytype='"+mPayType+"'";
            mLJSPayPersonSet = tLJSPayPersonDB.executeQuery(sqlStr);
            if (tLJSPayPersonDB.mErrors.needDealError() == true)
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tLJSPayPersonDB.mErrors);

                CError tError = new CError();
                tError.moduleName = "NormPayGrpChooseOperBL";
                tError.functionName = "dealData";
                tError.errorMessage = "应收个人表查询失败!";
                this.mErrors.addOneError(tError);
                mLJSPayPersonSet.clear();

                return false;
            }
            if ((mLJSPayPersonSet.size() == 0) || (mLJSPayPersonSet == null))
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "NormPayGrpChooseOperBL";
                tError.functionName = "dealData";
                tError.errorMessage = "应收个人表没有查询到相关纪录!";
                this.mErrors.addOneError(tError);
                mLJSPayPersonSet.clear();

                return false;
            }
            logger.debug("2-2 得到应收个人集合2");

            //2-2 得到应收个人集合2
            tLJSPayPersonDB = new LJSPayPersonDB();

            LJSPayPersonSet tempLJSPayPersonSet = new LJSPayPersonSet();
            sqlStr = "select * from LJSPayPerson where polno in (select polno from lcpol where GrpContNo='" + GrpContNo
                     + "')  and paytype='"+mPayType+"'";
            sqlStr = sqlStr + "and InputFlag='1'";
            logger.debug("2-2 sqlStr:" + sqlStr);
            tempLJSPayPersonSet = tLJSPayPersonDB.executeQuery(sqlStr);
            if (tLJSPayPersonDB.mErrors.needDealError() == true)
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tLJSPayPersonDB.mErrors);

                CError tError = new CError();
                tError.moduleName = "NormPayGrpChooseOperBL";
                tError.functionName = "dealData";
                tError.errorMessage = "应收个人表查询失败!";
                this.mErrors.addOneError(tError);
                tempLJSPayPersonSet.clear();

                return false;
            }
            if ((tempLJSPayPersonSet.size() == 0)
                    || (tempLJSPayPersonSet == null))
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "NormPayGrpChooseOperBL";
                tError.functionName = "dealData";
                tError.errorMessage = "请确认是否点击“保存页面上选中数据”！";
                this.mErrors.addOneError(tError);
                tempLJSPayPersonSet.clear();

                return false;
            }

            logger.debug("后面对集合2循环处理");

            DealAccount tDealAccount = new DealAccount();
            VData tempVData = new VData();

            //保险帐户表
            LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();

            //保险帐户表记价履历表
            LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
            //账户管理履历表
            LCInsureAccFeeTraceSet tLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();

            //后面对集合2循环处理
            LJSPayPersonBL tLJSPayPersonBL;
            double sumTotalMoney = 0;
            double balanceMoney = 0;
            logger.debug("2-2 num:" + tempLJSPayPersonSet.size());
            for (int i = 1; i <= tempLJSPayPersonSet.size(); i++)
            {
                tLJSPayPersonBL = new LJSPayPersonBL();
                tLJSPayPersonBL.setSchema(tempLJSPayPersonSet.get(i));
                sumTotalMoney = sumTotalMoney
                                + tLJSPayPersonBL.getSumActuPayMoney();
            }
			sumTotalMoney = PubFun.setPrecision(sumTotalMoney,"0.00");
             //end for 2-2

            logger.debug("查询暂交费表");

            //3 查询暂交费表，比较所交金额是否相等 --保存后用
            mLJTempFeeBL = new LJTempFeeBL();
            mLJTempFeeSet = new LJTempFeeSet();

            LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();

            sqlStr = "select * from LJTempFee where OtherNo='" + GrpContNo
                     + "' ";//GrpPolNo没有变 暂交费表录入的是签单后重新生成的团体合同号
            sqlStr = sqlStr + " and ConfFlag='0' ";
            mLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlStr);
            if (tLJTempFeeDB.mErrors.needDealError() == true)
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tLJTempFeeDB.mErrors);

                CError tError = new CError();
                tError.moduleName = "NormPayGrpChooseOperBL";
                tError.functionName = "dealData";
                tError.errorMessage = "查询暂交费表失败!";
                this.mErrors.addOneError(tError);
                mLJTempFeeSet.clear();

                return false;
            }
            if ((mLJTempFeeSet.size() == 0) || (mLJTempFeeSet == null))
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "NormPayGrpChooseOperBL";
                tError.functionName = "dealData";
                tError.errorMessage = "查询暂交费表时未发现相关数据!请去财务核对";
                this.mErrors.addOneError(tError);
                mLJTempFeeSet.clear();

                return false;
            }
            mLJTempFeeBL.setSchema(mLJTempFeeSet.get(1).getSchema());

            //保存集体保单的余额
            //balanceMoney = mLCGrpPolBL.getDif();
            //集体保单余额已改成取整个团体保单的余额 即LCGrpCont表中的余额 update by liuqh 2009-03-21
            LCGrpContDB tLCGrpContDB = new LCGrpContDB();
            LCGrpContSchema tLCGrpContSchema=new LCGrpContSchema();
            tLCGrpContDB.setGrpContNo(GrpContNo);
            if(!tLCGrpContDB.getInfo()){
            	CError.buildErr(this, "查询集体合同表错误!");
            	return false;
            }
            tLCGrpContSchema =tLCGrpContDB.getSchema();
            balanceMoney=tLCGrpContDB.getDif();
            logger.debug("集体保单余额：" + balanceMoney);
            logger.debug("暂交费金额：" + mLJTempFeeBL.getPayMoney());
            logger.debug("缴纳金额：" + sumTotalMoney);

            //比较交费金额  暂交费金额+余额>=应收金额
            if ((mLJTempFeeBL.getPayMoney() + balanceMoney) < sumTotalMoney)
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "NormPayGrpChooseOperBL";
                tError.functionName = "dealData";
                tError.errorMessage = "交费金额不足!";
                this.mErrors.addOneError(tError);
                mLJTempFeeSet.clear();

                return false;
            }
            if (mLJTempFeeBL.getEnterAccDate() == null)
            {
                CError tError = new CError();
                tError.moduleName = "NormPayGrpChooseOperBL";
                tError.functionName = "dealData";
                tError.errorMessage = "财务缴费还没有到帐，不能签单!（暂交费收据号："
                                      + mLJTempFeeBL.getTempFeeNo().trim()
                                      + "）";
                this.mErrors.addOneError(tError);

                return false;
            }

            GetNoticeNo = mLJTempFeeBL.getTempFeeNo();

            logger.debug("暂交费金额+余额>=应收金额，查询相关表并处理");

            //保存余额
            mLCGrpPolBL.setDif(0);
            mLCGrpPolSet.add(mLCGrpPolBL);
            //LCGrpPol表中的余额不准确 
            tLCGrpContSchema.setModifyDate(CurrentDate);
            tLCGrpContSchema.setModifyTime(CurrentTime);
            tLCGrpContSchema.setDif((mLJTempFeeBL.getPayMoney() + balanceMoney)
                               - sumTotalMoney);
            mLCGrpContSet.add(tLCGrpContSchema);
            
            //-查询相关表并处理
            //4-循环查询录入标志=1的应收纪录，个人保单表，保费项表，填充实收个人表
            LCPremSet tLCPremSet;
            LCPremBL tLCPremBL;
            LCPremDB tLCPremDB;
            LJAPayPersonBL tLJAPayPersonBL;
            int payCount = 0;
            for (int i = 1; i <= tempLJSPayPersonSet.size(); i++)
            {
                tLJSPayPersonBL = new LJSPayPersonBL();
                tLJSPayPersonBL.setSchema(tempLJSPayPersonSet.get(i));
                logger.debug("查询保费项表");

                //4-2  查询保费项表,根据应收个人交费表查询该表项
                tLCPremDB = new LCPremDB();
                sqlStr = "select * from LCPrem where PolNo='"
                         + tLJSPayPersonBL.getPolNo() + "' ";
                sqlStr = sqlStr + " and DutyCode='"
                         + tLJSPayPersonBL.getDutyCode() + "' ";
                sqlStr = sqlStr + " and PayPlanCode='"
                         + tLJSPayPersonBL.getPayPlanCode() + "'";
                logger.debug("Sql=" + sqlStr);
                tLCPremSet = new LCPremSet();
                tLCPremSet = tLCPremDB.executeQuery(sqlStr);
                if (tLCPremDB.mErrors.needDealError() == true)
                {
                    // @@错误处理
                	CError.buildErr(this, "保费项表查询失败!");
                    return false;
                }
                if ((tLCPremSet.size() == 0) || (tLCPremSet == null))
                {
                    // @@错误处理
                	CError.buildErr(this, "保费项表没有查询到相关纪录!");
                    return false;
                }
                tLCPremBL = new LCPremBL();
                tLCPremBL.setSchema(tLCPremSet.get(1).getSchema());
                tLCPremBL.setPayTimes(tLCPremBL.getPayTimes() + 1); //已交费次数
                tLCPremBL.setPrem(tLJSPayPersonBL.getSumActuPayMoney()); //实际保费
                tLCPremBL.setSumPrem(tLCPremBL.getSumPrem()
                                     + tLJSPayPersonBL.getSumActuPayMoney()); //累计保费
                tLCPremBL.setPaytoDate(CurrentDate); //交至日期
                tLCPremBL.setModifyDate(CurrentDate); //最后一次修改日期
                tLCPremBL.setModifyTime(CurrentTime); //最后一次修改时间
                mLCPremSet.add(tLCPremBL);

                //处理帐户
                if (mOperate.equals("VERIFY"))
                {
                    //普通账户
//                    tempVData = tDealAccount.addPrem(tLCPremBL.getSchema(),
//                                                     "2", tLCPremBL.getPolNo(),
//                                                     "1", mMoneyType, null);
                    tempVData = tDealAccount.getLCInsuAccInfo(tLCPremBL.getSchema(),
                    		"2", tLCPremBL.getPolNo(),
                    		"1", mMoneyType, null,mLJTempFeeBL.getEnterAccDate());
//                    tempVData = 

                    //tempVData = tDealAccount.addPrem(tLCPremSet.get(1).getSchema(),"2",GetNoticeNo,"2","BF",null);
                }
                if (mOperate.equals("HEATHVERIFY"))
                {
                    //for health 健康险
                    logger.debug(tLCPremBL.getPolNo()+"向账户注入保费余额");
                    tempVData = tDealAccount.addPremHealth(tLCPremBL.getSchema(),
                                                           "2",
                                                           tLCPremBL.getPolNo(),
                                                           "1", "BF", null);
                }
                if (tempVData != null)
                {
//                    tempVData = tDealAccount.updateLCInsureAccTraceDate(mLJTempFeeBL.getEnterAccDate(), //将到帐日期记入帐户－精算确认
//                                                                        tempVData);
//                    if (tempVData == null)
//                    {
//                        // @@错误处理
//                        CError tError = new CError();
//                        tError.moduleName = "IndiFinUrgeVerifyBL";
//                        tError.functionName = "dealData";
//                        tError.errorMessage = "修改保险帐户表记价履历表纪录的交费日期时出错!";
//                        this.mErrors.addOneError(tError);
//
//                        return false;
//                    }
                    tLCInsureAccSet = (LCInsureAccSet) tempVData
                                      .getObjectByObjectName("LCInsureAccSet", 0);
                    tLCInsureAccFeeTraceSet = (LCInsureAccFeeTraceSet) tempVData
                            .getObjectByObjectName("LCInsureAccFeeTraceSet", 0);
                    tLCInsureAccTraceSet = (LCInsureAccTraceSet) tempVData
                                           .getObjectByObjectName("LCInsureAccTraceSet",
                                                                  0);
                    	for(int k=1;k<=tLCInsureAccFeeTraceSet.size();k++){
                    		String rLimit = PubFun.getNoLimit(this.mGlobalInput.ComCode);
                    		String temSerialno = PubFun1.CreateMaxNo("SerialNo", rLimit);
                    		tLCInsureAccFeeTraceSet.get(k).setSerialNo(temSerialno);
                    	}
                    mLCInsureAccSet.add(tLCInsureAccSet);
                    mLCInsureAccTraceSet.add(tLCInsureAccTraceSet);
                    mLCInsureAccFeeTraceSet.add(tLCInsureAccFeeTraceSet);
                }

                logger.debug("填充实收个人表");

                //4-3填充实收个人表
                tLJAPayPersonBL = new LJAPayPersonBL();
                tLJAPayPersonBL.setPolNo(tLJSPayPersonBL.getPolNo()); //保单号码
                tLJAPayPersonBL.setPayCount(tLJSPayPersonBL.getPayCount()); //第几次交费
                tLJAPayPersonBL.setGrpPolNo(tLJSPayPersonBL.getGrpPolNo()); //集体保单号码
                tLJAPayPersonBL.setGrpContNo(tLJSPayPersonBL.getGrpContNo());//6.5新增
                tLJAPayPersonBL.setContNo(tLJSPayPersonBL.getContNo());//6.5新增
                logger.debug("总单/合同号码 :ContNo不能为空");
//                if ((mLCGrpPolBL.getContNo() != "")
//                        && (mLCGrpPolBL.getContNo() != null))
//                {
//                    tLJAPayPersonBL.setContNo(mLCGrpPolBL.getContNo()); //总单/合同号码
//                }
//                else
//                {
//                    tLJAPayPersonBL.setContNo("00000000000000000000");
//                }

                tLJAPayPersonBL.setAppntNo(tLJSPayPersonBL.getAppntNo()); //投保人客户号码
                tLJAPayPersonBL.setPayNo(payNo); //交费收据号码
                tLJAPayPersonBL.setPayAimClass(tLJSPayPersonBL.getPayAimClass()); //交费目的分类
                tLJAPayPersonBL.setDutyCode(tLJSPayPersonBL.getDutyCode()); //责任编码
                tLJAPayPersonBL.setPayPlanCode(tLJSPayPersonBL.getPayPlanCode()); //交费计划编码
                tLJAPayPersonBL.setSumDuePayMoney(tLJSPayPersonBL
                                                  .getSumDuePayMoney()); //总应交金额
                tLJAPayPersonBL.setSumActuPayMoney(tLJSPayPersonBL
                                                   .getSumActuPayMoney()); //总实交金额
                tLJAPayPersonBL.setPayIntv(tLJSPayPersonBL.getPayIntv()); //交费间隔
                tLJAPayPersonBL.setPayDate(tLJSPayPersonBL.getPayDate()); //交费日期
                tLJAPayPersonBL.setPayType(mPayType); //交费类型
                tLJAPayPersonBL.setEnterAccDate(mLJTempFeeBL.getEnterAccDate()); //到帐日期
                tLJAPayPersonBL.setConfDate(CurrentDate); //确认日期
                tLJAPayPersonBL.setLastPayToDate(tLJSPayPersonBL
                                                 .getLastPayToDate()); //原交至日期
                tLJAPayPersonBL.setCurPayToDate(tLJSPayPersonBL.getCurPayToDate()); //现交至日期
                tLJAPayPersonBL.setInInsuAccState(tLJSPayPersonBL
                                                  .getInInsuAccState()); //转入保险帐户状态
                tLJAPayPersonBL.setApproveCode(tLJSPayPersonBL.getApproveCode()); //复核人编码
                tLJAPayPersonBL.setApproveDate(tLJSPayPersonBL.getApproveDate()); //复核日期
                tLJAPayPersonBL.setSerialNo(serNo); //流水号
                tLJAPayPersonBL.setOperator(Operator); //操作员
                tLJAPayPersonBL.setMakeDate(CurrentDate); //入机日期
                tLJAPayPersonBL.setMakeTime(CurrentTime); //入机时间
                tLJAPayPersonBL.setGetNoticeNo(GetNoticeNo); //通知书号码
                tLJAPayPersonBL.setModifyDate(CurrentDate); //最后一次修改日期
                tLJAPayPersonBL.setModifyTime(CurrentTime); //最后一次修改时间
                tLJAPayPersonBL.setAgentGroup(mLCGrpPolBL.getAgentGroup());
                tLJAPayPersonBL.setAgentCode(mLCGrpPolBL.getAgentCode());

                tLJAPayPersonBL.setManageCom(tLJSPayPersonBL.getManageCom()); //管理机构
                tLJAPayPersonBL.setAgentCom(tLJSPayPersonBL.getAgentCom()); //代理机构
                tLJAPayPersonBL.setAgentType(tLJSPayPersonBL.getAgentType()); //代理机构内部分类
                tLJAPayPersonBL.setRiskCode(tLJSPayPersonBL.getRiskCode()); //险种编码

                mLJAPayPersonSet.add(tLJAPayPersonBL);

                if (payCount < tLJSPayPersonBL.getPayCount())
                {
                    payCount = tLJSPayPersonBL.getPayCount();
                }
            }
             //end for 4
            logger.debug("查询暂交费分类表");

            //5-查询暂交费分类表
            LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
            sqlStr = "select * from LJTempFeeClass where TempFeeNo='"
                     + mLJTempFeeBL.getTempFeeNo()+ "'";
            sqlStr = sqlStr + " and ConfFlag='0'";
            logger.debug("sqlStr:" + sqlStr);
            mLJTempFeeClassSet = tLJTempFeeClassDB.executeQuery(sqlStr);
            if (tLJTempFeeClassDB.mErrors.needDealError() == true)
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tLJTempFeeClassDB.mErrors);

                CError tError = new CError();
                tError.moduleName = "NormPayGrpChooseOperBL";
                tError.functionName = "dealData";
                tError.errorMessage = "暂交费分类表表查询失败!";
                this.mErrors.addOneError(tError);
                mLJTempFeeClassSet.clear();

                return false;
            }
            logger.debug("num:" + mLJTempFeeClassSet.size());
            if ((mLJTempFeeClassSet.size() == 0)
                    || (mLJTempFeeClassSet == null))
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "NormPayGrpChooseOperBL";
                tError.functionName = "dealData";
                tError.errorMessage = "暂交费分类表表没有查询到相关纪录!";
                this.mErrors.addOneError(tError);
                mLJTempFeeClassSet.clear();

                return false;
            }

            logger.debug("暂交费表核销标志置为1");

            //-暂交费表核销标志置为1
            mLJTempFeeBL.setConfFlag("1"); //核销标志置为1
            mLJTempFeeBL.setConfDate(CurrentDate);
            mLJTempFeeBL.setModifyDate(CurrentDate);
            mLJTempFeeBL.setModifyTime(CurrentTime);
            mLJTempFeeSet.add(mLJTempFeeBL);

            //-暂交费分类表，核销标志置为1
            LJTempFeeClassBL tLJTempFeeClassBL;
            for (int i = 1; i <= mLJTempFeeClassSet.size(); i++)
            {
                tLJTempFeeClassBL = new LJTempFeeClassBL();
                tLJTempFeeClassBL.setSchema(mLJTempFeeClassSet.get(i).getSchema());
                tLJTempFeeClassBL.setConfFlag("1"); //核销标志置为1
                tLJTempFeeClassBL.setConfDate(CurrentDate);
                tLJTempFeeClassBL.setModifyDate(CurrentDate);
                tLJTempFeeClassBL.setModifyTime(CurrentTime);
                mLJTempFeeClassNewSet.add(tLJTempFeeClassBL);
            }

            logger.debug("6-集体保单表和暂交费表数据填充实收总表");

            //6-集体保单表和暂交费表数据填充实收总表
            mLJAPayBL = new LJAPayBL();
            mLJAPayBL.setPayNo(payNo); //交费收据号码
            mLJAPayBL.setIncomeNo(mLJTempFeeBL.getOtherNo()); //应收/实收编号
            mLJAPayBL.setIncomeType(mLJTempFeeBL.getOtherNoType()); //应收/实收编号类型
            mLJAPayBL.setAppntNo(mLCGrpPolBL.getGrpProposalNo()); //  投保人客户号码
            mLJAPayBL.setSumActuPayMoney(sumTotalMoney); // 总实交金额
            mLJAPayBL.setGetNoticeNo(GetNoticeNo); //交费通知书号
            mLJAPayBL.setEnterAccDate(mLJTempFeeBL.getEnterAccDate()); // 到帐日期
            mLJAPayBL.setPayDate(PayDate); //交费日期
            mLJAPayBL.setConfDate(mLJTempFeeBL.getConfDate()); //确认日期
            mLJAPayBL.setApproveCode(mLCGrpPolBL.getApproveCode()); //复核人编码
            mLJAPayBL.setApproveDate(mLCGrpPolBL.getApproveDate()); //  复核日期
            mLJAPayBL.setSerialNo(serNo); //流水号
            mLJAPayBL.setOperator(Operator); // 操作员
            mLJAPayBL.setMakeDate(CurrentDate); //入机时间
            mLJAPayBL.setMakeTime(CurrentTime); //入机时间
            mLJAPayBL.setModifyDate(CurrentDate); //最后一次修改日期
            mLJAPayBL.setModifyTime(CurrentTime); //最后一次修改时间

            //mLJAPayBL.setBankCode(mLCGrpPolBL.getBankCode()); //银行编码
            //mLJAPayBL.setBankAccNo(mLCGrpPolBL.getBankAccNo()); //银行帐号
            mLJAPayBL.setRiskCode(mLCGrpPolBL.getRiskCode()); // 险种编码
            mLJAPayBL.setManageCom(mLCGrpPolBL.getManageCom());
            mLJAPayBL.setAgentCode(mLCGrpPolBL.getAgentCode());
            mLJAPayBL.setAgentGroup(mLCGrpPolBL.getAgentGroup());
            mLJAPaySet.add(mLJAPayBL);

            logger.debug("7-集体保单表和暂交费表数据填充实收集体表");

            //7-集体保单表和暂交费表数据填充实收集体表
            mLJAPayGrpBL = new LJAPayGrpBL();
            mLJAPayGrpBL.setGrpPolNo(mLCGrpPolBL.getGrpPolNo());
            mLJAPayGrpBL.setPayCount(payCount); //交费次数
            //mLJAPayGrpBL.setContNo(mLCGrpPolBL.getContNo());
            mLJAPayGrpBL.setAppntNo(mLCGrpPolBL.getGrpProposalNo());
            mLJAPayGrpBL.setPayNo(payNo);
            mLJAPayGrpBL.setGrpContNo(mLCGrpPolBL.getGrpContNo());//6.5新增团体合同号

            //     mLJAPayGrpBL.setEndorsementNo(); //批单号码?
            mLJAPayGrpBL.setSumDuePayMoney(mLCGrpPolBL.getPrem()); //总应交金额
            mLJAPayGrpBL.setSumActuPayMoney(sumTotalMoney); //总实交金额
            mLJAPayGrpBL.setPayIntv(mLCGrpPolBL.getPayIntv());
            mLJAPayGrpBL.setPayDate(PayDate);
            mLJAPayGrpBL.setPayType(mPayType); //repair:交费类型
            mLJAPayGrpBL.setEnterAccDate(mLJTempFeeBL.getEnterAccDate());
            mLJAPayGrpBL.setConfDate(CurrentDate); //确认日期
            mLJAPayGrpBL.setLastPayToDate(mLCGrpPolBL.getPaytoDate()); //原交至日期
            mLJAPayGrpBL.setCurPayToDate(CurrentDate);
            mLJAPayGrpBL.setApproveCode(mLCGrpPolBL.getApproveCode());
            mLJAPayGrpBL.setApproveDate(mLCGrpPolBL.getApproveDate());
            mLJAPayGrpBL.setSerialNo(serNo);
            mLJAPayGrpBL.setOperator(Operator);
            mLJAPayGrpBL.setMakeDate(CurrentDate);
            mLJAPayGrpBL.setMakeTime(CurrentTime);
            mLJAPayGrpBL.setGetNoticeNo(GetNoticeNo); //通知书号码
            mLJAPayGrpBL.setModifyDate(CurrentDate);
            mLJAPayGrpBL.setModifyTime(CurrentTime);

            mLJAPayGrpBL.setManageCom(mLCGrpPolBL.getManageCom()); //管理机构
            mLJAPayGrpBL.setAgentCom(mLCGrpPolBL.getAgentCom()); //代理机构
            mLJAPayGrpBL.setAgentType(mLCGrpPolBL.getAgentType()); //代理机构内部分类
            mLJAPayGrpBL.setRiskCode(mLCGrpPolBL.getRiskCode()); //险种编码
            mLJAPayGrpBL.setAgentCode(mLCGrpPolBL.getAgentCode());
            mLJAPayGrpBL.setAgentGroup(mLCGrpPolBL.getAgentGroup());
            mLJAPayGrpSet.add(mLJAPayGrpBL);

            //8-更新保单表字段，取第一个应收个人交费纪录
            LCPolDB tLCPolDB = new LCPolDB();
            LCPolSet tLCPolSet = new LCPolSet();

            //查出在应收纪录中录入标记为1的集体下个人保单项
            sqlStr = " select * from LCPol where grpcontno ='"+mLCGrpPolBL.getGrpContNo()+"' " +
            		 " and exists (select 1 from ljspayperson where polno = lcpol.polno and InputFlag='1' and paytype='"+mPayType+"') ";
            logger.debug("sqlStr=" + sqlStr);
            tLCPolSet = tLCPolDB.executeQuery(sqlStr);
            if (tLCPolDB.mErrors.needDealError() == true)
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tLCPolDB.mErrors);

                CError tError = new CError();
                tError.moduleName = "NormPayCollOperBL";
                tError.functionName = "dealData";
                tError.errorMessage = "个人保单表查询失败!";
                this.mErrors.addOneError(tError);
                tLCPolSet.clear();

                return false;
            }
            if (tLCPolSet.size() < 1)
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "NormPayCollOperBL";
                tError.functionName = "dealData";
                tError.errorMessage = "个人保单表没有查询到相关纪录!";
                this.mErrors.addOneError(tError);
                tLCPolSet.clear();

                return false;
            }

            LCPolBL tLCPolBL;
            LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
            tLJSPayPersonBL = new LJSPayPersonBL();

            double sumMoney = 0;
            for (int i = 1; i <= tLCPolSet.size(); i++)
            {
                tLCPolBL = new LCPolBL();
                tLCPolBL.setSchema(tLCPolSet.get(i));

                //下面循环得到个人保单下应收款的总和
                sqlStr = "select * from LJSPayPerson where PolNo='"
                         + tLCPolBL.getPolNo() + "' and paytype='"+mPayType+"'";
                logger.debug("sqlStr:" + sqlStr);
                tLJSPayPersonDB = new LJSPayPersonDB();
                tLJSPayPersonSet = tLJSPayPersonDB.executeQuery(sqlStr);
                if (tLJSPayPersonDB.mErrors.needDealError() == true)
                {
                    // @@错误处理
                    this.mErrors.copyAllErrors(tLJSPayPersonDB.mErrors);

                    CError tError = new CError();
                    tError.moduleName = "NormPayCollOperBL";
                    tError.functionName = "dealData";
                    tError.errorMessage = "个人保单表查询失败!";
                    this.mErrors.addOneError(tError);
                    tLJSPayPersonSet.clear();

                    return false;
                }
                sumMoney = 0;
                for (int n = 1; n <= tLJSPayPersonSet.size(); n++)
                {
                    tLJSPayPersonBL.setSchema(tLJSPayPersonSet.get(n));
                    sumMoney = sumMoney + tLJSPayPersonBL.getSumActuPayMoney();
                }
                logger.debug("sumMoney 2 =" + sumMoney);
                tLCPolBL.setSumPrem(tLCPolBL.getSumPrem() + sumMoney);
                tLCPolBL.setPaytoDate(CurrentDate);
                tLCPolBL.setModifyDate(CurrentDate); //最后一次修改日期
                tLCPolBL.setModifyTime(CurrentTime); //最后一次修改时间
                mLCPolSet.add(tLCPolBL);
            }
            tReturn = true;

            //9-更新保险责任表
            LCDutyBL tLCDutyBL;
            LCDutyDB tLCDutyDB = new LCDutyDB();
            LCDutySet tLCDutySet = new LCDutySet();

            //查出在应收纪录中录入标记为1的集体下保险责任表项
            sqlStr = "select * from LCDuty where PolNo in (select PolNo from LJSPayPerson where GrpContNo='"
                     + mLCGrpPolBL.getGrpContNo() + "' and InputFlag='1' and paytype='"+mPayType+"')";
            logger.debug("sqlStr=" + sqlStr);
            tLCDutySet = tLCDutyDB.executeQuery(sqlStr);
            if (tLCDutyDB.mErrors.needDealError() == true)
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tLCDutyDB.mErrors);

                CError tError = new CError();
                tError.moduleName = "NormPayCollOperBL";
                tError.functionName = "dealData";
                tError.errorMessage = "保险责任表查询失败!";
                this.mErrors.addOneError(tError);
                tLCDutySet.clear();

                return false;
            }
            if (tLCDutySet.size() < 1)
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "NormPayCollOperBL";
                tError.functionName = "dealData";
                tError.errorMessage = "保险责任表没有查询到相关纪录!";
                this.mErrors.addOneError(tError);
                tLCDutySet.clear();

                return false;
            }
            tLJSPayPersonSet = new LJSPayPersonSet();
            tLJSPayPersonBL = new LJSPayPersonBL();
            for (int i = 1; i <= tLCDutySet.size(); i++)
            {
                tLCDutyBL = new LCDutyBL();
                tLCDutyBL.setSchema(tLCDutySet.get(i));

                //下面循环得到保险责任表下应收款的总和
                sqlStr = "select * from LJSPayPerson where PolNo='"
                         + tLCDutyBL.getPolNo() + "' and DutyCode='"
                         + tLCDutyBL.getDutyCode() + "' and paytype='"+mPayType+"'";
                ;
                tLJSPayPersonDB = new LJSPayPersonDB();
                tLJSPayPersonSet = tLJSPayPersonDB.executeQuery(sqlStr);
                if (tLJSPayPersonDB.mErrors.needDealError() == true)
                {
                    // @@错误处理
                    this.mErrors.copyAllErrors(tLJSPayPersonDB.mErrors);

                    CError tError = new CError();
                    tError.moduleName = "NormPayCollOperBL";
                    tError.functionName = "dealData";
                    tError.errorMessage = "保险责任表查询失败!";
                    this.mErrors.addOneError(tError);
                    tLJSPayPersonSet.clear();

                    return false;
                }
                sumMoney = 0;
                for (int n = 1; n <= tLJSPayPersonSet.size(); n++)
                {
                    tLJSPayPersonBL.setSchema(tLJSPayPersonSet.get(n));
                    sumMoney = sumMoney + tLJSPayPersonBL.getSumActuPayMoney();
                }
                tLCDutyBL.setSumPrem(tLCDutyBL.getSumPrem() + sumMoney);

                tLCDutyBL.setPaytoDate(CurrentDate);
                tLCDutyBL.setModifyDate(CurrentDate); //最后一次修改日期
                tLCDutyBL.setModifyTime(CurrentTime); //最后一次修改时间
                mLCDutySet.add(tLCDutyBL);
            }
            tReturn = true;
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "NormPayGrpChooseOperBL";
            tError.functionName = "dealData";
            tError.errorMessage = "在处理后层所需要的数据时出错。";
            this.mErrors.addOneError(tError);

            return false;
        }

        return true;
    }

    /**
      * 从输入数据中得到所有对象
      *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
      */
    private boolean getInputData(VData mInputData)
    {
        //应收总表
        logger.debug("in getinputdata");
        mLCGrpPolBL.setSchema((LCGrpPolSchema) mInputData.getObjectByObjectName("LCGrpPolSchema",
                                                                                0));
        mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput",
                0);
        if (mLCGrpPolBL == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "NormPayGrpChooseOperBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "没有得到足够的数据，请您确认!";
            this.mErrors.addOneError(tError);

            return false;
        }

        return true;
    }

    //准备往后层输出所需要的数据
    //输出：如果准备数据时发生错误则返回false,否则返回true
    private boolean prepareOutputData()
    {
        mInputData = new VData();
        MMap tMap =new MMap();
        try
        {
//            mInputData.add(mLJAPayBL); //实收总表
//            mInputData.add(mLJAPayGrpBL); //实收集体表
//            mInputData.add(mLCGrpPolBL); //集体保单表
//            mInputData.add(mLJTempFeeBL); //暂交费表
//            mInputData.add(mLJTempFeeClassNewSet); //暂交费分类表
//            mInputData.add(mLJAPayPersonSet); //实收个人表
//            mInputData.add(mLJSPayPersonSet); //应收个人交费表
//            mInputData.add(mLCPolSet); //个人保单表
//            mInputData.add(mLCPremSet); //保费项表
//            mInputData.add(mLCDutySet); //保险责任表
//            mInputData.add(mLCInsureAccSet); //保险帐户表
//            mInputData.add(mLCInsureAccTraceSet); //保险帐户表记价履历表
//            mInputData.add(mLCInsureAccFeeTraceSet); //保险帐户管理表记价履历表
        	tMap.put(mLJAPaySet, "INSERT");
        	tMap.put(mLJAPayGrpSet, "INSERT");
        	tMap.put(mLCGrpPolSet, "UPDATE");
        	tMap.put(mLCGrpContSet, "UPDATE");
        	tMap.put(mLJTempFeeSet, "UPDATE");
        	tMap.put(mLJTempFeeClassNewSet, "UPDATE");
        	tMap.put(mLJAPayPersonSet, "INSERT");
        	tMap.put(mLJSPayPersonSet, "DELETE");
        	tMap.put(mLCPolSet, "UPDATE");
        	tMap.put(mLCPremSet, "UPDATE");
        	tMap.put(mLCDutySet, "UPDATE");
//        	tMap.put(mLCInsureAccSet, "UPDATE"); //一般不操作账户表、账户分类表
        	tMap.put(mLCInsureAccTraceSet, "INSERT");
        	tMap.put(mLCInsureAccFeeTraceSet, "INSERT");
        	mResult.add(tMap);
            logger.debug("prepareOutputData:");
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "NormPayGrpChooseOperBL";
            tError.functionName = "prepareData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);

            return false;
        }

        return true;
    }

    private String verifyOperate(String szOperate)
    {
        String szReturn = "";
        String[] szOperates = {"HEATHVERIFY", "VERIFY"};

        for (int nIndex = 0; nIndex < szOperates.length; nIndex++)
        {
            if (szOperate.equals(szOperates[nIndex]))
            {
                szReturn = szOperate;
            }
        }

        return szReturn;
    }
}
