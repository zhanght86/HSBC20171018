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
public class SaveAllPersonInput
{
private static Logger logger = Logger.getLogger(SaveAllPersonInput.class);

    //错误处理类，每个需要错误处理的类中都放置该类
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData;
    private String mOperate; //操作类型
    private String mOperator; //操作员
    private GlobalInput tGI;
    private String GrpNo;
    private VData mResult = new VData();
    private LJSPayPersonSet mLJSPayPersonInSertSet = new LJSPayPersonSet();
    private LJSPayPersonSet mLJSPayPersonUpdateSet = new LJSPayPersonSet();
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private String mPayType;

    //业务处理相关变量
    public SaveAllPersonInput()
    {
    }

    public static void main(String[] args)
    {
    }

    //传输数据的公共方法
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
        {
            return false;
        }

        if (!checkData())
        {
            return false;
        }

        //进行业务处理
        if (!dealData())
        {
            return false;
        }

        return true;
    }

    private boolean checkData()
    {
        if (mOperate.equals("GrpChoose_INSERT"))
        {
            LJSPayPersonSchema mLJSPayPersonSchema = new LJSPayPersonSchema();
//            mLJSPayPersonSchema.setGrpPolNo(GrpNo);
            mLJSPayPersonSchema.setGrpContNo(GrpNo);
            mLJSPayPersonSchema.setPayType(this.mPayType);

            LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
            LJSPayPersonDB mLJSPayPersonDB = new LJSPayPersonDB();
            mLJSPayPersonDB.setSchema(mLJSPayPersonSchema);
            mLJSPayPersonSet = mLJSPayPersonDB.query();

            if (mLJSPayPersonSet.size() > 0)
            {
            	CError.buildErr(this, "个人应收表中已有数据，请进行生成模板操作!");
                return false;
            }
        }

        return true;
    }

    private boolean dealData()
    {
        if (mOperate.equals("GrpChoose_INSERT"))
        {
            LJSPayPersonSet tempLJSPayPersonSet = new LJSPayPersonSet();
            String strSql = "";

            /*
            strSql = "select LCPrem.PolNo,LCPrem.DutyCode,LCPrem.PayPlanCode,LMDutyPay.PayPlanName,LCPol.InsuredName,LCPrem.Prem,LJSPayPerson.Sumactupaymoney,LJSPayPerson.InputFlag,decode(LCPol.InsuredSex,0,'男',1,'女',2,'不祥',LCPol.InsuredSex),LCPol.InsuredBirthday,LCPol.SequenceNo from LCPrem,LJSPayPerson,LCPol,LMDutyPay where LCPrem.GrpPolNo='" +
                     GrpNo + "' ";
            strSql = strSql +
                     " and (LCPrem.UrgePayFlag='N' or LCPrem.UrgePayFlag is null) ";
            strSql = strSql + " and LCPrem.PolNo=LJSPayPerson.PolNo";
            strSql = strSql + " and LCPrem.PolNo=LCPol.PolNo";
            strSql = strSql + " and LCPrem.DutyCode=LJSPayPerson.DutyCode";
            strSql = strSql +
                     " and LCPrem.PayPlanCode=LJSPayPerson.PayPlanCode";
            strSql = strSql + " and LCPrem.PayPlanCode=LMDutyPay.PayPlanCode";
            strSql = strSql + " and LCPol.appflag='1'";
            strSql = strSql + " and LCPol.paytodate<LCPol.payenddate";

            strSql = strSql + " UNION ";
            */
            strSql = strSql +
                     "select LCPrem.PolNo,LCPrem.DutyCode,LCPrem.PayPlanCode,(select payplanname from lmdutypay where payplancode=lcprem.payplancode ),LCPol.InsuredName,LCPrem.Prem,LCPrem.Prem ,'0',(case LCPol.InsuredSex when '0' then '男' when '1' then '女' when '2' then '不祥' else LCPol.InsuredSex end),LCPol.InsuredBirthday from LCPrem,lcpol where LCPol.grpcontno='?GrpNo?' ";
            strSql = strSql +
                     " and (LCPrem.UrgePayFlag='N' or LCPrem.UrgePayFlag is null) ";
            strSql = strSql + " and LCPrem.PolNo=LCPol.PolNo";

            //            strSql = strSql +
            //                     " and 0=(select count(*) from LJSPayPerson where PolNo=LCPrem.PolNo and DutyCode=LCPrem.DutyCode and PayPlanCode=LCPrem.PayPlanCode)";
            strSql = strSql + " and LCPol.appflag='1'";
            strSql = strSql + " and LCPol.paytodate<LCPol.payenddate";
            SQLwithBindVariables sqlbv=new SQLwithBindVariables();
            sqlbv.sql(strSql);
            sqlbv.put("GrpNo", GrpNo);


            logger.debug("strSql:" + strSql);

            ExeSQL mExeSQL = new ExeSQL();
            SSRS mSSRS = new SSRS();
            mSSRS = mExeSQL.execSQL(sqlbv);

            int maxCol = mSSRS.getMaxCol();
            int maxRow = mSSRS.getMaxRow();
            logger.debug("maxCol:" + maxCol);
            logger.debug("maxRow:" + maxRow);

            //根据行来进行循环       
            for (int i = 1; i <= maxRow; i++)
            {
//                LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
                LJSPayPersonBL tLJSPayPersonBL = new LJSPayPersonBL();
                String PolNo = mSSRS.GetText(i, 1);
                String DutyCode = mSSRS.GetText(i, 2);
                String PayPlanCode = mSSRS.GetText(i, 3);
//                String PayPlanName = mSSRS.GetText(i, 4);
//                String InsuredName = mSSRS.GetText(i, 5);
                String Prem = mSSRS.GetText(i, 6);
                String Sumactupaymoney = mSSRS.GetText(i, 7);
//                String InputFlag = "1"; //录入标志 
//                String InsuredSex = mSSRS.GetText(i, 9);
//                String InsuredBirthday = mSSRS.GetText(i, 10);
               // String SequenceNo = mSSRS.GetText(i, 11);
//                String ContNo = "00000000000000000000";
//                String PayAimClass = "2"; //集体下的个人交费
//                String PayType = "ZC"; //交费方式：正常交费 

                LCPremDB tLCPremDB = new LCPremDB();
                tLCPremDB.setPolNo(PolNo);
                tLCPremDB.setDutyCode(DutyCode);
                tLCPremDB.setPayPlanCode(PayPlanCode);
                
                if (tLCPremDB.getInfo() == false)
                {
                	CError.buildErr(this, "没有找到对应的保费项，请您确认!");
                    return false;
                }

                LCPolDB tLCPolDB = new LCPolDB();
                tLCPolDB.setPolNo(PolNo);

                if (tLCPolDB.getInfo() == false)
                {
                	CError.buildErr(this, "没有找到对应的保单表，请您确认!");
                    return false;
                }
                
                LCContDB tLCContDB = new LCContDB();
                tLCContDB.setContNo(tLCPolDB.getContNo());
                if(tLCContDB.getInfo() == false)
                {
                	CError.buildErr(this, "查询合同表失败!");
                	return false;
                }

                logger.debug("测试3");
                tLJSPayPersonBL.setGrpPolNo(tLCPolDB.getGrpPolNo());
                tLJSPayPersonBL.setGrpContNo(tLCContDB.getGrpContNo());
                tLJSPayPersonBL.setPolNo(PolNo);
                tLJSPayPersonBL.setDutyCode(DutyCode);
                tLJSPayPersonBL.setPayPlanCode(PayPlanCode);
                tLJSPayPersonBL.setSumDuePayMoney(Prem);
                tLJSPayPersonBL.setSumActuPayMoney(Sumactupaymoney);
                tLJSPayPersonBL.setContNo(tLCContDB.getContNo());
                tLJSPayPersonBL.setPayAimClass("2"); //集体下的个人交费     
                tLJSPayPersonBL.setOperator(mOperator);
                tLJSPayPersonBL.setPayType(this.mPayType); //交费方式：正常交费    modify by wk 根据险种来确定类型 ，在getinputdate中体现
                tLJSPayPersonBL.setInputFlag("1");

                tLJSPayPersonBL.setPayCount(tLCPremDB.getPayTimes() + 1);
                tLJSPayPersonBL.setPayIntv(tLCPremDB.getPayIntv());
                tLJSPayPersonBL.setPayDate(CurrentDate);
                tLJSPayPersonBL.setLastPayToDate(tLCPremDB.getPaytoDate());
                tLJSPayPersonBL.setCurPayToDate(CurrentDate);
                tLJSPayPersonBL.setAppntNo(tLCPremDB.getAppntNo());
                tLJSPayPersonBL.setBankAccNo(tLCContDB.getBankAccNo());
                tLJSPayPersonBL.setBankCode(tLCContDB.getBankCode());
                tLJSPayPersonBL.setApproveCode(tLCPolDB.getApproveCode());
                tLJSPayPersonBL.setApproveDate(tLCPolDB.getApproveDate());
                tLJSPayPersonBL.setRiskCode(tLCPolDB.getRiskCode());
                tLJSPayPersonBL.setManageCom(tLCPolDB.getManageCom());
                tLJSPayPersonBL.setAgentCode(tLCPolDB.getAgentCode());
                tLJSPayPersonBL.setAgentGroup(tLCPolDB.getAgentGroup());
                tLJSPayPersonBL.setMakeDate(CurrentDate);
                tLJSPayPersonBL.setMakeTime(CurrentTime);
                tLJSPayPersonBL.setModifyDate(CurrentDate);
                tLJSPayPersonBL.setModifyTime(CurrentTime);
                logger.debug(tLJSPayPersonBL.encode());

                //                LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
                //                String sqlStr = "select * from LJSPayPerson where PolNo='" +
                //                                PolNo + "'";
                //                sqlStr = sqlStr + "and DutyCode='" + DutyCode + "'";
                //                sqlStr = sqlStr + "and PayPlanCode='" + PayPlanCode + "'";
                //                sqlStr = sqlStr + "and PayAimClass='" + PayAimClass + "'";
                //
                //                tempLJSPayPersonSet = tLJSPayPersonDB.executeQuery(sqlStr);
                //
                //                if (tempLJSPayPersonSet.size() == 0)
                //                {
                //                    mLJSPayPersonInSertSet.add(tLJSPayPersonBL);
                //                    logger.debug("insert");
                //                }
                //                else
                //                {
                //                    mLJSPayPersonUpdateSet.add(tLJSPayPersonBL);
                //                    logger.debug("update");
                //                }
                mLJSPayPersonInSertSet.add(tLJSPayPersonBL);

            }
            LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
            String sqlStr = "select * from LJSPayPerson where polno in (select polno from lcpol where grpcontno ='?GrpNo?') and paytype='?paytype?'";
            SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
            sqlbv1.sql(sqlStr);
            sqlbv1.put("GrpNo", GrpNo);
            sqlbv1.put("paytype", this.mPayType);
            tempLJSPayPersonSet = tLJSPayPersonDB.executeQuery(sqlbv1);

            //for
            MMap map = new MMap();

            try
            {
                map.put(tempLJSPayPersonSet, "DELETE");
                map.put(mLJSPayPersonInSertSet, "INSERT");
                mResult.add(map);
                logger.debug("prepareOutputData:");
            }
            catch (Exception ex)
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "PreParePayPersonBL";
                tError.functionName = "prepareData";
                tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
                this.mErrors.addOneError(tError);

                return false;
            }

            SaveAllPersonInputBLS tSaveAllPersonInputBLS = new SaveAllPersonInputBLS();

            if (tSaveAllPersonInputBLS.submitData(mResult, mOperate) == false)
            {
                CError tError = new CError();
                tError.moduleName = "SaveAllPersonInput";
                tError.functionName = "BLS";
                tError.errorMessage = "数据更新错误!";
                this.mErrors.addOneError(tError);

                return false;
            }
        }
        else
        {
            String sqlstr =
                "update ljspayperson set inputflag='1' where polno in (select polno from lcpol where grpcontno='?GrpNo?') and paytype='?paytype?'";
            logger.debug("sql:" + sqlstr);
            SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
            sqlbv2.sql(sqlstr);
            sqlbv2.put("GrpNo", GrpNo);
            sqlbv2.put("paytype", this.mPayType);

            ExeSQL tExeSQL = new ExeSQL();

            if (tExeSQL.execUpdateSQL(sqlbv2) == false)
            {
                CError tError = new CError();
                tError.moduleName = "SaveAllPersonInput";
                tError.functionName = "dealData";
                tError.errorMessage = "更新应收个人表失败!";
                this.mErrors.addOneError(tError);

                return false;
            }
        }

        return true;
    }

    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData mInputData)
    {
        tGI = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
        mOperator = tGI.Operator;
        logger.debug("Operator:" + mOperator);
        GrpNo = (String) mInputData.getObjectByObjectName("String", 0);

        if (mOperator == null)
        {
            CError tError = new CError();
            tError.moduleName = "SaveAllPersonInput";
            tError.functionName = "getInputData";
            tError.errorMessage = "操作员信息丢失！";
            this.mErrors.addOneError(tError);

            return false;
        }

        if (GrpNo == null)
        {
            CError tError = new CError();
            tError.moduleName = "SaveAllPersonInput";
            tError.functionName = "getInputData";
            tError.errorMessage = "没有得到集体保单号";
            this.mErrors.addOneError(tError);

            return false;
        }
        
        LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
        tLCGrpPolDB.setGrpContNo(GrpNo);
        LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
        if(tLCGrpPolSet.size()>0)
        {
        	LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
        	tLMRiskAppDB.setRiskCode(tLCGrpPolSet.get(1).getRiskCode());
        	if(tLMRiskAppDB.getInfo())
        	{
        		if("1".equals(tLMRiskAppDB.getHealthType()))
        		{
        			this.mPayType="TM";
        		}
        		else
        		{
        			this.mPayType="ZC";
        		}
        	}
        }
        

        return true;
    }
}
