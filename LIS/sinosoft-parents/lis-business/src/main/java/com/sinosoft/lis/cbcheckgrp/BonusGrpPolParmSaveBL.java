package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class BonusGrpPolParmSaveBL
{
private static Logger logger = Logger.getLogger(BonusGrpPolParmSaveBL.class);

    public  CErrors mErrors = new CErrors();

    private GlobalInput mGlobalInput = new GlobalInput();

    private String mFiscalYear = "";
    private String mGrpPolNo = "";
    private String mGrpContNo = "";
    private String mRiskCode = "";
    private String mBDate = "";
    private String mEDate = "";
    private String mActuRate = "";
    private String mEnsuRateDefault = "";
    private String mAssignRate = "";

    public BonusGrpPolParmSaveBL(){}

    public static void main(String[] args)
    {
        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput.ComCode = "86";
        tGlobalInput.ManageCom = "8611";
        tGlobalInput.Operator = "Debug";

        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("FiscalYear","2004");
        tTransferData.setNameAndValue("GrpPolNo","86110020040220000376");
        tTransferData.setNameAndValue("RiskCode","212401");
        tTransferData.setNameAndValue("BDate","");
        tTransferData.setNameAndValue("EDate","");
        tTransferData.setNameAndValue("ActuRate","0.035");
        tTransferData.setNameAndValue("EnsuRateDefault","0.025");
        tTransferData.setNameAndValue("AssignRate","0.7");

        VData tVData = new VData();
        tVData.add(tGlobalInput);
        tVData.add(tTransferData);

        BonusGrpPolParmSaveBL bonusGrpPolParmSaveBL1 = new BonusGrpPolParmSaveBL();
        bonusGrpPolParmSaveBL1.submitData(tVData,"UPDATE||ALL");
        logger.debug("Error : " + bonusGrpPolParmSaveBL1.mErrors.getFirstError());
    }

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData,String cOperate)
    {
        if(getInputData(cInputData)==false)
            return false;

        String tLimit = PubFun.getNoLimit( mGlobalInput.ManageCom);
        String tSerialNo = PubFun1.CreateMaxNo( "SERIALNO", tLimit );           //产生流水号码

        boolean retFlag = false;
        if(cOperate.equals("UPDATE"))
            retFlag = updateParmData(tSerialNo);
        else if(cOperate.equals("INSERT||ALL"))
            retFlag = saveParmData(tSerialNo);
        else if(cOperate.equals("UPDATE||ALL"))
            retFlag = updateAllParmData(tSerialNo);
        else if(cOperate.equals("DELETE"))
            retFlag = deleteParmData(tSerialNo);
        else
            CError.buildErr(this,"不能识别的操作类型：" + cOperate);

        return retFlag;
    }

    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData)
    {
        mGlobalInput = ((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
        TransferData tTransferData = ((TransferData)cInputData.getObjectByObjectName("TransferData",0));
        if (mGlobalInput == null || tTransferData == null)
        {
            CError.buildErr(this, "传入后台数据缺失！");
            return false;
        }

        mFiscalYear = (String)tTransferData.getValueByName("FiscalYear");
        mGrpContNo = (String)tTransferData.getValueByName("GrpContNo");
        mRiskCode = (String)tTransferData.getValueByName("RiskCode");
        mBDate = (String)tTransferData.getValueByName("BDate");
        mEDate = (String)tTransferData.getValueByName("EDate");
        mActuRate = (String)tTransferData.getValueByName("ActuRate");
        mEnsuRateDefault = (String)tTransferData.getValueByName("EnsuRateDefault");
        mAssignRate = (String)tTransferData.getValueByName("AssignRate");

        if(mFiscalYear.equals("") || mRiskCode.equals(""))
        {
            CError.buildErr(this, "关键数据为空，请确认是否填写！");
            return false;
        }

        return true;
    }

    /**
     * 保存红利参数设置
     * @return
     */
    private boolean saveParmData(String cSerialNo)
    {
        boolean retFlag = true;
        String tCurrDate = PubFun.getCurrentDate();
        String tCurrTime = PubFun.getCurrentTime();
        
        if(mGrpContNo != null && !mGrpContNo.equals(""))
        {
            
        	String tSql = "select grppolno from lcgrppol where grpcontno = '"+mGrpContNo+"' and appflag = '1' and exists (select 1 from lmrisk where InsuAccFlag = 'Y' and riskcode = lcgrppol.riskcode)";
        	
        	ExeSQL tExeSQL = new ExeSQL();
        	mGrpPolNo = tExeSQL.getOneValue(tSql);
        	if (mGrpPolNo == null || mGrpPolNo.equals("")) {
        		CError.buildErr(this, "没有查询到该保单("+mGrpContNo+")的信息！请检查！");
        		return false;
        	}
        }
        
        //tongmeng 2007-10-15 modify
        //增加对生效时间的限制。
        String sql = "select grppolno,cvalidate,bonusrate,grpcontno from lcgrppol "
                   + "where riskcode = '"+ mRiskCode +"' and appflag = '1' "
                   + " and CValiDate <='" + mFiscalYear + "-12-31' "
//                   + "and cvalidate <= '" + mFiscalYear + "-12-31' and payenddate >= '" + mFiscalYear + "-12-31' "
                   + ReportPubFun.getWherePart("grppolno",mGrpPolNo)
                   + ReportPubFun.getWherePart("cvalidate",mBDate,mEDate,1)
                   + " and grppolno not in (select grppolno from LOBonusGrpPolParm where FiscalYear='" + mFiscalYear + "') "
                   + " order by grppolno"
        ;
        logger.debug("insert : " + sql);
        SSRS tssrs = new ExeSQL().execSQL(sql);
        for(int i = 1 ; i <= tssrs.getMaxRow() ; i ++)
        {
            LOBonusGrpPolParmDB tLOBonusGrpPolParmDB = new LOBonusGrpPolParmDB();
            if(mFiscalYear.compareTo(tssrs.GetText(i,2).substring(0,4)) > 0)    //如果该团单可以计算2年的红利，则查询上一年是否已经计算
            {
                tLOBonusGrpPolParmDB.setFiscalYear(Integer.parseInt(mFiscalYear) - 1); //查询前一年的参数是否设置
                tLOBonusGrpPolParmDB.setGrpPolNo(tssrs.GetText(i,1));
                if(tLOBonusGrpPolParmDB.getInfo() && !tLOBonusGrpPolParmDB.getComputeState().equals("2"))
                {
                    retFlag = false;
                    insertErrLog(cSerialNo,mFiscalYear,tLOBonusGrpPolParmDB.getGrpPolNo(),"此操作不能跨年度进行，请核实后再试！",tssrs.GetText(i,4));
                    CError.buildErr(this,"此操作不能跨年度进行，请核实后再试！");
                    logger.debug("此操作不能跨年度进行，请核实后再试！");
                    continue;
                }
            }

            tLOBonusGrpPolParmDB.setFiscalYear(mFiscalYear);
            tLOBonusGrpPolParmDB.setGrpPolNo(tssrs.GetText(i,1));
            if(tLOBonusGrpPolParmDB.getInfo())
            {
                retFlag = false;
                insertErrLog(cSerialNo,mFiscalYear,tLOBonusGrpPolParmDB.getGrpPolNo(),"团单" + tLOBonusGrpPolParmDB.getGrpPolNo() + "参数设置重复保存！",tssrs.GetText(i,4));
                CError.buildErr(this,"团单" + tLOBonusGrpPolParmDB.getGrpPolNo() + "参数设置重复保存！");
                logger.debug("团单" + tLOBonusGrpPolParmDB.getGrpPolNo() + "参数设置重复保存！");
                continue;
            }

            //参数全部保存时，如果传入的分红比例为空，就取团单中的分红比率信息
            if(mAssignRate.equals(""))
                mAssignRate = tssrs.GetText(i,3);
            else
            {
                //如果该比例录入并且和团单中该比例不一致，以团单信息为准，并做提示
                if(!mAssignRate.equals(tssrs.GetText(i,3)))
                {
                    insertErrLog(cSerialNo,mFiscalYear,tLOBonusGrpPolParmDB.getGrpPolNo(),"团单" + tLOBonusGrpPolParmDB.getGrpPolNo() + "参数设置" + mAssignRate + "与保单中分红比例信息不一致！",tssrs.GetText(i,4));
                    CError.buildErr(this,"团单" + tLOBonusGrpPolParmDB.getGrpPolNo() + "参数设置" + mAssignRate + "与保单中分红比例信息不一致！");
                    logger.debug("团单" + tLOBonusGrpPolParmDB.getGrpPolNo() + "参数设置" + mAssignRate + "与保单中分红比例信息不一致！");
                    mAssignRate = tssrs.GetText(i,3);
                }
            }

            LOBonusGrpPolParmSchema tLOBonusGrpPolParmSchema = new LOBonusGrpPolParmSchema();
            tLOBonusGrpPolParmSchema.setFiscalYear(mFiscalYear);
            tLOBonusGrpPolParmSchema.setGrpPolNo(tssrs.GetText(i,1));
            tLOBonusGrpPolParmSchema.setComputeState("0");
            tLOBonusGrpPolParmSchema.setActuRate(mActuRate);
            tLOBonusGrpPolParmSchema.setEnsuRate(0);
            tLOBonusGrpPolParmSchema.setEnsuRateDefault(mEnsuRateDefault);
            tLOBonusGrpPolParmSchema.setAssignRate(mAssignRate);
            tLOBonusGrpPolParmSchema.setOperator(mGlobalInput.Operator);
            tLOBonusGrpPolParmSchema.setRiskCode(mRiskCode);
            tLOBonusGrpPolParmSchema.setMakeDate(tCurrDate);
            tLOBonusGrpPolParmSchema.setMakeTime(tCurrTime);
            tLOBonusGrpPolParmSchema.setModifyDate(tCurrDate);
            tLOBonusGrpPolParmSchema.setModifyTime(tCurrTime);
            tLOBonusGrpPolParmSchema.setGrpContNo(tssrs.GetText(i,4));
//            insertOperLog(cSerialNo,tssrs.GetText(i,1),"INS||ALL","团单红利参数设置！");
            tLOBonusGrpPolParmDB.setSchema(tLOBonusGrpPolParmSchema);
            if(!tLOBonusGrpPolParmDB.insert())
            {
                insertErrLog(cSerialNo,mFiscalYear,tLOBonusGrpPolParmDB.getGrpPolNo(),tLOBonusGrpPolParmDB.mErrors.getFirstError(),tssrs.GetText(i,4));
                CError.buildErr(this,tLOBonusGrpPolParmDB.mErrors.getFirstError());
                return false;
            }
        }
        return retFlag;
    }

    /**
     * 修改红利参数设置
     * @return
     */
    private boolean updateParmData(String cSerialNo)
    {
        String tCurrDate = PubFun.getCurrentDate();
        String tCurrTime = PubFun.getCurrentTime();

        if(mGrpContNo.equals(""))
        {
            CError.buildErr(this, "团单号不能为空，请选择一条记录或者录入团单号！");
            return false;
        }
        String tSql = "select grppolno from lcgrppol where grpcontno = '"+mGrpContNo+"' and appflag = '1' and exists (select 1 from lmrisk where InsuAccFlag = 'Y' and riskcode = lcgrppol.riskcode)";
        
        ExeSQL tExeSQL = new ExeSQL();
        mGrpPolNo = tExeSQL.getOneValue(tSql);
		if (mGrpPolNo == null || mGrpPolNo.equals("")) {
			CError.buildErr(this, "没有查询到该保单的信息！请检查！");
			return false;
		}
        LOBonusGrpPolParmDB tLOBonusGrpPolParmDB = new LOBonusGrpPolParmDB();
        tLOBonusGrpPolParmDB.setFiscalYear(mFiscalYear);
        tLOBonusGrpPolParmDB.setGrpPolNo(mGrpPolNo);
        if(!tLOBonusGrpPolParmDB.getInfo())
        {
            CError.buildErr(this, "查询不到该团单的参数设置信息，无法进行修改，请确认参数是否已设置！");
            return false;
        }

       // insertOperLog(cSerialNo,mGrpPolNo,"UPDATE","团单红利参数设置修改！");

        LOBonusGrpPolParmSchema tLOBonusGrpPolParmSchema = (LOBonusGrpPolParmSchema)tLOBonusGrpPolParmDB.getSchema();
        tLOBonusGrpPolParmSchema.setActuRate(mActuRate);
        tLOBonusGrpPolParmSchema.setEnsuRateDefault(mEnsuRateDefault);
        tLOBonusGrpPolParmSchema.setAssignRate(mAssignRate);
        tLOBonusGrpPolParmSchema.setOperator(mGlobalInput.Operator);
        tLOBonusGrpPolParmSchema.setModifyDate(tCurrDate);
        tLOBonusGrpPolParmSchema.setModifyTime(tCurrTime);
        tLOBonusGrpPolParmDB.setSchema(tLOBonusGrpPolParmSchema);
        if(!tLOBonusGrpPolParmDB.update())
        {
            insertErrLog(cSerialNo,mFiscalYear,tLOBonusGrpPolParmDB.getGrpPolNo(),tLOBonusGrpPolParmDB.mErrors.getFirstError(),mGrpContNo);
            CError.buildErr(this,tLOBonusGrpPolParmDB.mErrors.getFirstError());
            return false;
        }
        return true;
    }

    /**
     * 修改红利参数设置
     * @return
     */
    private boolean updateAllParmData(String cSerialNo)
    {
        boolean retFlag = true;
        String tCurrDate = PubFun.getCurrentDate();
        String tCurrTime = PubFun.getCurrentTime();
        
        if(mGrpContNo != null && !mGrpContNo.equals(""))
        {
            
        	String tSql = "select grppolno from lcgrppol where grpcontno = '"+mGrpContNo+"' and appflag = '1' and exists (select 1 from lmrisk where InsuAccFlag = 'Y' and riskcode = lcgrppol.riskcode)";
        	
        	ExeSQL tExeSQL = new ExeSQL();
        	mGrpPolNo = tExeSQL.getOneValue(tSql);
        	if (mGrpPolNo == null || mGrpPolNo.equals("")) {
        		CError.buildErr(this, "没有查询到该保单("+mGrpContNo+")的信息！请检查！");
        		return false;
        	}
        }

        String sql = "select * from lobonusgrppolparm a "
                   + "where fiscalyear = '" + mFiscalYear + "' and riskcode = '"+ mRiskCode +"' "
                   + "and computestate in ('0','3','4') "     //必须只能是已设置状态和团单下个单已计算和分配的才能修改，已经团单计算和分配的均不能再修改
                   + ReportPubFun.getWherePart("grppolno",mGrpPolNo)
                   + "and exists(select 1 from lcgrppol where grppolno = a.grppolno and appflag = '1' "
//                   + "and cvalidate <= '" + mFiscalYear + "-12-31' and payenddate >= '" + mFiscalYear + "-12-31' "
                   + ReportPubFun.getWherePart("cvalidate",mBDate,mEDate,1)
                   + " ) order by grppolno "
        ;
        logger.debug("update : " + sql);
        LOBonusGrpPolParmDB tLOBonusGrpPolParmDB = new LOBonusGrpPolParmDB();
        LOBonusGrpPolParmSet tLOBonusGrpPolParmSet = (LOBonusGrpPolParmSet)tLOBonusGrpPolParmDB.executeQuery(sql);
//        if(tLOBonusGrpPolParmSet.size() == 0)
//        {
//            CError.buildErr(this,"没有待修改的参数设置！");
//            return false;
//        }

        for(int i = 1 ; i <= tLOBonusGrpPolParmSet.size() ; i ++)
        {
            LOBonusGrpPolParmSchema tLOBonusGrpPolParmSchema = (LOBonusGrpPolParmSchema)tLOBonusGrpPolParmSet.get(i);
//            tLOBonusGrpPolParmSchema.setComputeState("0");
            tLOBonusGrpPolParmSchema.setActuRate(mActuRate);
//            tLOBonusGrpPolParmSchema.setEnsuRate(0);
            tLOBonusGrpPolParmSchema.setEnsuRateDefault(mEnsuRateDefault);
            tLOBonusGrpPolParmSchema.setAssignRate(mAssignRate);
            tLOBonusGrpPolParmSchema.setOperator(mGlobalInput.Operator);
            tLOBonusGrpPolParmSchema.setModifyDate(tCurrDate);
            tLOBonusGrpPolParmSchema.setModifyTime(tCurrTime);

           // insertOperLog(cSerialNo,tLOBonusGrpPolParmSchema.getGrpPolNo(),"UPD||ALL","团单红利参数设置修改！");

            tLOBonusGrpPolParmDB.setSchema(tLOBonusGrpPolParmSchema);
            if(!tLOBonusGrpPolParmDB.update())
            {
                retFlag = false;
                insertErrLog(cSerialNo,mFiscalYear,tLOBonusGrpPolParmDB.getGrpPolNo(),tLOBonusGrpPolParmDB.mErrors.getFirstError(),tLOBonusGrpPolParmDB.getGrpContNo());
                CError.buildErr(this,tLOBonusGrpPolParmDB.mErrors.getFirstError());
                continue;
            }
        }
        return retFlag;
    }

    /**
     * 删除红利参数设置 （回滚参数设置）
     * @return
     */
    private boolean deleteParmData(String cSerialNo)
    {
        boolean retFlag = true;
        String tCurrDate = PubFun.getCurrentDate();
        String tCurrTime = PubFun.getCurrentTime();

        String sql = "select * from lobonusgrppolparm a "
                   + "where fiscalyear = '" + mFiscalYear + "' and riskcode = '"+ mRiskCode +"' "
                   + "and computestate = '0' "     //必须只能是已设置状态才能删除，团单下个单已计算和分配的、已经团单计算和分配的均不能再删除设置
                   + ReportPubFun.getWherePart("grppolno",mGrpPolNo)
                   + "and exists(select 1 from lcgrppol where grppolno = a.grppolno and appflag = '1' "
//                   + "and cvalidate <= '" + mFiscalYear + "-12-31' and payenddate >= '" + mFiscalYear + "-12-31' "
                   + ReportPubFun.getWherePart("cvalidate",mBDate,mEDate,1)
                   + " ) order by grppolno "
        ;
        logger.debug("delete : " + sql);
        LOBonusGrpPolParmDB tLOBonusGrpPolParmDB = new LOBonusGrpPolParmDB();
        LOBonusGrpPolParmSet tLOBonusGrpPolParmSet = (LOBonusGrpPolParmSet)tLOBonusGrpPolParmDB.executeQuery(sql);
        for(int i = 1 ; i <= tLOBonusGrpPolParmSet.size() ; i ++)
        {
           // insertOperLog(cSerialNo,tLOBonusGrpPolParmSet.get(i).getGrpPolNo(),"DELETE","团单红利参数设置删除！");

            tLOBonusGrpPolParmDB.setSchema(tLOBonusGrpPolParmSet.get(i));
            if(!tLOBonusGrpPolParmDB.delete())
            {
                retFlag = false;
                insertErrLog(cSerialNo,mFiscalYear,tLOBonusGrpPolParmDB.getGrpPolNo(),tLOBonusGrpPolParmDB.mErrors.getFirstError(),tLOBonusGrpPolParmDB.getGrpContNo());
                CError.buildErr(this,tLOBonusGrpPolParmDB.mErrors.getFirstError());
                continue;
            }
        }
        return retFlag;
    }

    /**
     * 纪录错误信息
     * @param tSerialNo    流水号
     * @param tFiscalYear  会计年度
     * @param tGrpPolNo    团单号
     * @param errMsg       错误信息
     * @return
     */
    private boolean insertErrLog(String tSerialNo,String tFiscalYear,String tGrpPolNo,String errMsg,String tGrpContNo)
    {
        LOBonusAssignGrpErrLogDB tLOBonusAssignGrpErrLogDB= new LOBonusAssignGrpErrLogDB();
        tLOBonusAssignGrpErrLogDB.setSerialNo(tSerialNo);
        tLOBonusAssignGrpErrLogDB.setGrpPolNo(tGrpPolNo);
        tLOBonusAssignGrpErrLogDB.setFiscalYear(tFiscalYear);
        tLOBonusAssignGrpErrLogDB.setType("0");                                 //0~~参数设置；1~~红利计算
        tLOBonusAssignGrpErrLogDB.setErrMsg(errMsg);
        tLOBonusAssignGrpErrLogDB.setMakeDate(PubFun.getCurrentDate());
        tLOBonusAssignGrpErrLogDB.setMakeTime(PubFun.getCurrentTime());
        tLOBonusAssignGrpErrLogDB.setGrpContNo(tGrpContNo);
        if(!tLOBonusAssignGrpErrLogDB.insert())
        {
            CError tError = new CError();
            tError.moduleName = "BonusGrpPolParmSaveBL";
            tError.functionName = "insertErrLog";
            tError.errorMessage = "纪录错误日志时发生错误："+tLOBonusAssignGrpErrLogDB.mErrors.getFirstError()+"；解决该问题后，请再次分配当日红利";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 记录操作轨迹
     * @param cSerialNo
     * @param cGrpPolNo
     * @param cType
     * @param cMsgInfo
     * @return
     */
//    private boolean insertOperLog(String cSerialNo,String cBussNo,String cType,String cMsgInfo)
//    {
//        LSRunDataInfoSchema tLSRunDataInfoSchema = new LSRunDataInfoSchema();
//        tLSRunDataInfoSchema.setSerialNo(cSerialNo);
//        tLSRunDataInfoSchema.setRunType("1");                   //人工执行
//        tLSRunDataInfoSchema.setBussNo(cBussNo);
//        tLSRunDataInfoSchema.setBussType("GRPBONUS");           //团体保单号
//        tLSRunDataInfoSchema.setAssiType(cType);
//        tLSRunDataInfoSchema.setOtherNo(cBussNo);
//        tLSRunDataInfoSchema.setContent(cMsgInfo);
//        tLSRunDataInfoSchema.setProgName(this.getClass().getName());            //com.sinosoft.lis.cbcheck.BonusGrpPolParmSaveBL
//        tLSRunDataInfoSchema.setOperator(mGlobalInput.Operator);
//        tLSRunDataInfoSchema.setStandByFlag1(mFiscalYear);
//        tLSRunDataInfoSchema.setStandByFlag2("");
//        tLSRunDataInfoSchema.setMakeDate(PubFun.getCurrentDate());
//        tLSRunDataInfoSchema.setMakeTime(PubFun.getCurrentTime());
//
//        LSRunDataInfoDB tLSRunDataInfoDB = new LSRunDataInfoDB();
//        tLSRunDataInfoDB.setSchema(tLSRunDataInfoSchema);
//        if(!tLSRunDataInfoDB.insert())
//        {
//            CError.buildErr(this,tLSRunDataInfoDB.mErrors.getFirstError());
//            return false;
//        }
//
//        return true;
//    }
}
