package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.schema.FIDataTransGatherSchema;
import com.sinosoft.lis.fininterface.utility.FinCreateSerialNo;
import com.sinosoft.lis.db.FIAssociatedItemDefDB;
import com.sinosoft.lis.db.FIPeriodManagementDB;
import com.sinosoft.lis.db.FIRulesVersionDB;

/**
 * 凭证汇总
 * @author lijs
 * @create date 2006-11-05
 */
public class FIMoveDataFromGRPToPRO {
private static Logger logger = Logger.getLogger(FIMoveDataFromGRPToPRO.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    private String mBatchNo;
    private final String enter = "\r\n"; // 换行符
    public LogInfoDeal mLogInfoDeal ;
    private FIAssociatedDataTrans mFIAssociatedDataTrans[];
    private FIAssociatedItemDefSet mFIAssociatedItemDefSet;
    private String VersionNo;

    public static void main(String[] args)
    {
        FIMoveDataFromGRPToPRO tFIMoveDataFromGRPToPRO = new FIMoveDataFromGRPToPRO();
        tFIMoveDataFromGRPToPRO.moveData("00000000000000000041","jianan");
    }

    public boolean moveData(String sBatchNo, String cOperator)
    {
        mBatchNo = sBatchNo;

    	//第一步判断是否已经提取过数据
    	if(!Check())
        {
             return false;
    	}
        if(!init(cOperator))
        {
             return false;
        }
    	if(!moveDataFromGRPToPRO())
        {
             return false;
    	}
        mLogInfoDeal.Complete(true);
    	return true;
    }

    public boolean Check()
    {

       try
       {
           String rSQL = "select * from FIOperationLog a,FIOperationParameter b where a.eventno = b.eventno and a.Performstate = '0' and a.eventtype = '11'"
                   + " and b.ParameterValue = '" + mBatchNo + "' and b.parametertype = 'BatchNo'";
           SSRS oSSRS = new SSRS();
           ExeSQL rExeSQL = new ExeSQL();
           oSSRS = rExeSQL.execSQL(rSQL);
           if(rExeSQL.mErrors.needDealError())
           {
               buildError("FIMoveDataFromGRPToPRO", "Check","是否已处理校验SQL执行出错" + rExeSQL.mErrors.getFirstError());
               return false;
           }
           if(oSSRS.MaxRow>0)
           {
               buildError("FIMoveDataFromGRPToPRO", "Check","该批次数据已经导入接口表");
               return false;
           }



           ExeSQL tExeSQL = new ExeSQL();
           SSRS tSSRS = new SSRS();
           tSSRS = tExeSQL.execSQL(
                   "select min(AccountDate),max(AccountDate) from FIAboriginalData where batchno='" +
                   mBatchNo +
                   "'");
           if(tExeSQL.mErrors.needDealError())
           {
               buildError("FIMoveDataFromGRPToPRO","Check","查询该批次数据对应时间区间出错");
               return false;
           }

           String StartDate = tSSRS.GetText(1, 1);
           String EndDate = tSSRS.GetText(1, 2);

           if(StartDate == null || EndDate == null || StartDate.equals("") || EndDate.equals(""))
           {
               buildError("FIMoveDataFromGRPToPRO","Check","未查询到该批次数据对应时间区间，该批次数据可能为空");
               return false;
           }

           //校验该批数据账务日期是否对应开启的会计区间
           if(!getPeriodManagement(StartDate, EndDate))
           {
               return false;
           }

           //校验该批数据是否对应多个账务规则版本，并获得规则版本号码
           if (!getRuleVersion(StartDate, EndDate)) {
               return false;
            }

       }
       catch (Exception ex)
       {
           buildError("FIMoveDataFromGRPToPRO", "infoCheck","执行条件检测出现异常，信息为" + ex.getMessage());
           return false;
       }

        return true;
    }


    public boolean init(String cOperator)
    {
        try
        {

            mLogInfoDeal = new LogInfoDeal(cOperator,"11");
            mLogInfoDeal.AddLogParameter("BatchNo","提数批次号",mBatchNo);

            if(!mLogInfoDeal.SaveLogParameter())
            {
                buildError("FIMoveDataFromGRPToPRO","setBigCertificate","日志参数保存出错" + mLogInfoDeal.mErrors.getFirstError());
                return false;
            }
            StringBuffer oStringBuffer = new StringBuffer(1024);
            oStringBuffer.append("成功获得明细凭证导出相关的参数，参数列表如下：" + enter);
            oStringBuffer.append("操作员 = " + cOperator + ",数据批次号 = " + mBatchNo + enter);
            mLogInfoDeal.WriteLogTxt(oStringBuffer.toString());

            if(!InitAssociated())
            {
                return false;
            }

            //开始校验更新凭证类型数据
            String tSQL = "select a.CertificateID from (select distinct CertificateID as CertificateID from FIDataTransResult where BatchNo = '" + mBatchNo + "' ) a where not exists (select * from FICodeTrans b where b.codetype = 'BigCertificateID' and b.code = a.certificateid and b.codealias is not null)" ;
            ExeSQL tExeSQL = new ExeSQL();
            SSRS oSSRS = new SSRS();
            oSSRS = tExeSQL.execSQL(tSQL);
            if(tExeSQL.mErrors.needDealError())
            {
                buildError("FIMoveDataFromGRPToPRO", "Check","明细凭证与大类影射定义校验SQL执行出错" + tExeSQL.mErrors.getFirstError());
                return false;
            }
            if(oSSRS.MaxRow>0)
            {
                String tErrInfo = "明细凭证类型：";
                 for(int i=1;i<=oSSRS.MaxRow;i++)
                 {
                      tErrInfo += "[" + oSSRS.GetText(i, 1) + "]";
                 }
                 tErrInfo += "不存在大类转换定义";
                 buildError("FIMoveDataFromGRPToPRO", "Check",tErrInfo);
                 return false;
            }

            if(!checkAssociatedData())
            {
                return false;
            }

            //开始进行编码转换
            String tSql =  "update FIDataTransResult a set a.upcertificate = (select b.codealias from FICodeTrans b where b.codetype = 'BigCertificateID' and b.code = a.certificateid) where a.batchno = '" + mBatchNo + "'";
            tExeSQL = new ExeSQL();
            if(!tExeSQL.execUpdateSQL(tSql))
            {
                buildError("FIMoveDataFromGRPToPRO","setBigCertificate","执行设置凭证大类数据SQL出错，信息为：" + tExeSQL.mErrors.getFirstError());
                mLogInfoDeal.WriteLogTxt("执行设置凭证大类数据出错，信息为：" + tExeSQL.mErrors.getFirstError());
                return false;
            }
            if(tExeSQL.mErrors.needDealError())
            {
                buildError("FIMoveDataFromGRPToPRO","setBigCertificate","执行设置凭证大类数据SQL出错，信息为：" + tExeSQL.mErrors.getFirstError());
                mLogInfoDeal.WriteLogTxt("执行设置凭证大类数据出错，信息为：" + tExeSQL.mErrors.getFirstError());
                return false;
            }

            if(!DealAssociatedData())
            {
                return false;
            }

            return true;
        }
        catch (Exception ex)
        {
            buildError("FIMoveDataFromGRPToPRO","setBigCertificate","执行初始化异常，信息为：" + ex.getMessage());
            mLogInfoDeal.WriteLogTxt("执行初始化异常，信息为：" + ex.getMessage());
            return false;
        }
    }


    private boolean InitAssociated()
    {
        try
        {
           FIAssociatedItemDefDB tFIAssociatedItemDefDB = new FIAssociatedItemDefDB();
           tFIAssociatedItemDefDB.setVersionNo(VersionNo);
           mFIAssociatedItemDefSet = tFIAssociatedItemDefDB.query();
           if (tFIAssociatedItemDefDB.mErrors.needDealError())
           {
               buildError("FiDistillForCertMain","InitAssociated", "专项定义查询失败");
               mLogInfoDeal.WriteLogTxt("专项定义查询失败" + enter);
               return false;
           }
           if (mFIAssociatedItemDefSet.size() < 1)
           {
               buildError("FiDistillForCertMain","InitAssociated", "未查询到任何专项定义");
               mLogInfoDeal.WriteLogTxt("未查询到任何专项定义" + enter);
               return false;
           }

           mFIAssociatedDataTrans = new FIAssociatedDataTrans[mFIAssociatedItemDefSet.size()];
           for (int i = 1; i <= mFIAssociatedItemDefSet.size(); i++)
           {
               mFIAssociatedDataTrans[i -1] = new FIAssociatedDataTrans(mLogInfoDeal);
               if (!mFIAssociatedDataTrans[i -1].initInfo(mFIAssociatedItemDefSet.get(i)))
               {
                   this.mErrors.copyAllErrors(mFIAssociatedDataTrans[i -1].mErrors);
                   return false;
               }
            }
        }
        catch (Exception ex)
        {
            buildError("FiDistillForCertMain","InitAssociated", "查询专项定义出现异常，信息为：" + ex.getMessage());
            mLogInfoDeal.WriteLogTxt("查询专项定义出现异常，信息为：" + ex.getMessage() + enter);
            return false;
        }
        return true;
    }


    private boolean getPeriodManagement(String sDate, String eDate)
    {
            String sql = "  select * from FIPeriodManagement where StartDate<=to_date('" +
                         eDate + "','yyyy-mm-dd') " +
                         " and EndDate>=to_date('" + sDate +
                         "','yyyy-mm-dd') and state='1' order by StartDate asc";
            FIPeriodManagementSet tFIPeriodManagementSet = new FIPeriodManagementSet();
            FIPeriodManagementDB tFIPeriodManagementDB = new FIPeriodManagementDB();
            tFIPeriodManagementSet = tFIPeriodManagementDB.executeQuery(sql);
            if (tFIPeriodManagementSet.size() ==0)
            {
                buildError("FIMoveDataFromGRPToPRO","getPeriodManagement", "您输入的时间区间所对应的会计区间尚未开启，请重新输入起始日期！");
                return false;
            }
            else if(tFIPeriodManagementSet.size() !=1)
            {
                String tInfo = "您输入的时间区间内查询到"+tFIPeriodManagementSet.size()+"个有效会计区间！分别是";
                for(int i =0;i<tFIPeriodManagementSet.size();i++)
                {
                    if(i==0)
                    {
                        tInfo += "[" + tFIPeriodManagementSet.get(i + 1).getstartdate() + "][" +  tFIPeriodManagementSet.get(i + 1).getenddate() + "]";
                    }
                    else
                    {
                        tInfo += "," + "[" + tFIPeriodManagementSet.get(i + 1).getstartdate() + "][" +  tFIPeriodManagementSet.get(i + 1).getenddate() + "]";
                    }
                }
                buildError("FIMoveDataFromGRPToPRO","getPeriodManagement", tInfo);
                return false;
            }
            return true;
    }

    private boolean getRuleVersion(String sDate, String eDate)
    {

        try
        {
            String sql = " select * from FIRulesVersion where  versionstate ='01' and StartDate<=to_date('" + eDate + "','yyyy-mm-dd') "
                         + " and EndDate>to_date('" + sDate + "','yyyy-mm-dd') " + " order by startdate asc ";

            FIRulesVersionDB tFIRulesVersionDB = new FIRulesVersionDB();
            FIRulesVersionSet tFIRulesVersionSet = tFIRulesVersionDB.executeQuery(sql);
            if (tFIRulesVersionSet.size() == 0)
            {
                buildError("FIMoveDataFromGRPToPRO","getRuleVersion", "您输入的时间区间内提数规则无版本，请重新输入起始日期！");
                return false;
            }
            else if (tFIRulesVersionSet.size() != 1)
            {
                String tInfo = "您输入的时间区间内查询到" + tFIRulesVersionSet.size() + "个提数有效版本！版本分别是：";
                for (int i = 0; i < tFIRulesVersionSet.size(); i++) {
                    if (i == 0) {
                        tInfo += "[" + tFIRulesVersionSet.get(i + 1).getStartDate() + "][" +  tFIRulesVersionSet.get(i + 1).getEndDate()+ "]";
                    } else {
                        tInfo += "," +  "[" + tFIRulesVersionSet.get(i + 1).getStartDate() + "][" +  tFIRulesVersionSet.get(i + 1).getEndDate()+ "]";
                    }
                }
                buildError("FIMoveDataFromGRPToPRO","getRuleVersion", tInfo);
                return false;
            }
            VersionNo = tFIRulesVersionSet.get(1).getVersionNo();
            return true;
        }
        catch (Exception e)
        {
            buildError("FIMoveDataFromGRPToPRO","getRuleVersion", "提数规则版本核对出现异常，异常信息:" + e.getMessage());
            return false;
        }
    }


	/**
	 * 通过批次号查询财务接口表LIDataTransResult,把相应数据放入业务系统中总帐接口表OF_INTERFACE_DETAIL
	 * @param sBatchNoNo 批次号
	 * @throws SQLException
	 */
	public  boolean moveDataFromGRPToPRO()
        {
            try
            {

                String oSQL = "";
                oSQL = "select upcertificate,AccountCode,FinItemType,sum(SumMoney),AccountDate,SaleChnl,ManageCom,ExecuteCom,RiskCode,CostCenter,NotesNo,CustomerID,Budget,CashFlow,Currency,(select max(codename) from FICodeTrans  where codetype = 'BigCertificateID' and codealias = upcertificate) from FIDataTransResult where Batchno = '"
                       + mBatchNo + "' group by upcertificate,AccountCode,FinItemType,AccountDate,SaleChnl,ManageCom,ExecuteCom,RiskCode,CostCenter,NotesNo,CustomerID,Budget,CashFlow,Currency";

                SSRS aSSRS = new SSRS();
                ExeSQL aExeSQL = new ExeSQL();
                aSSRS = aExeSQL.execSQL(oSQL);
                if (aExeSQL.mErrors.needDealError()) {
                    buildError("FIMoveDataFromGRPToPRO", "setBigCertificate",
                               "执行设置凭证大类数据SQL出错，信息为：" + aExeSQL.mErrors.getFirstError());
                    mLogInfoDeal.WriteLogTxt("执行设置凭证大类数据出错，信息为：" +
                                             aExeSQL.mErrors.getFirstError());
                    return false;
                }

                FinCreateSerialNo tFinCreateSerialNo = new FinCreateSerialNo();
                String[] aSerailNo = tFinCreateSerialNo.getSerialNo("InterFaceNO", aSSRS.getMaxRow(), 20);
                FIDataTransGatherSet tFIDataTransGatherSet = new FIDataTransGatherSet();

                for (int i = 1; i <= aSSRS.getMaxRow(); i++)
                {
                    FIDataTransGatherSchema tFIDataTransGatherSchema = new FIDataTransGatherSchema();
                    tFIDataTransGatherSchema.setFSerialNo(aSerailNo[i - 1]);
                    tFIDataTransGatherSchema.setBatchNo(mBatchNo);
                    tFIDataTransGatherSchema.setCertificateID(aSSRS.GetText(i, 1));
                    tFIDataTransGatherSchema.setAccountCode(aSSRS.GetText(i, 2));
                    tFIDataTransGatherSchema.setFinItemType(aSSRS.GetText(i, 3));
                    tFIDataTransGatherSchema.setSumMoney(aSSRS.GetText(i, 4));
                    tFIDataTransGatherSchema.setAccountDate(aSSRS.GetText(i, 5));
                    tFIDataTransGatherSchema.setSaleChnl(aSSRS.GetText(i, 6));
                    tFIDataTransGatherSchema.setManageCom(aSSRS.GetText(i, 7));
                    tFIDataTransGatherSchema.setExecuteCom(aSSRS.GetText(i, 8));
                    tFIDataTransGatherSchema.setRiskCode(aSSRS.GetText(i, 9));
                    tFIDataTransGatherSchema.setCostCenter(aSSRS.GetText(i, 10));
                    tFIDataTransGatherSchema.setNotesNo(aSSRS.GetText(i, 11));
                    tFIDataTransGatherSchema.setCustomerID(aSSRS.GetText(i, 12));
                    tFIDataTransGatherSchema.setBudget(aSSRS.GetText(i, 13));
                    tFIDataTransGatherSchema.setCashFlow(aSSRS.GetText(i, 14));
                    tFIDataTransGatherSchema.setCurrency(aSSRS.GetText(i, 15));
                    tFIDataTransGatherSchema.setReMark(aSSRS.GetText(i, 16));

                    tFIDataTransGatherSchema.setStandByString1("0");
                    tFIDataTransGatherSchema.setStandByString2("0");
                    tFIDataTransGatherSchema.setStandByString3("0");
                    tFIDataTransGatherSchema.setStandByString4("0");
                    tFIDataTransGatherSchema.setStandByString5("0");
                    tFIDataTransGatherSchema.setStandByString6("0");
                    tFIDataTransGatherSchema.setStandByString7("0");
                    tFIDataTransGatherSchema.setStandByString8("0");
                    tFIDataTransGatherSchema.setStandByString9("0");
                    tFIDataTransGatherSchema.setStandByString10("0");
                    tFIDataTransGatherSchema.setStandByString11("0");
                    tFIDataTransGatherSchema.setStandByString12("0");
                    tFIDataTransGatherSchema.setStandByString13("0");
                    tFIDataTransGatherSchema.setStandByString14("0");
                    tFIDataTransGatherSchema.setStandByString15("0");

                    if(tFIDataTransGatherSchema.getSumMoney()==0)
                    {
                        tFIDataTransGatherSchema.setVoucherNo("N001");
                        tFIDataTransGatherSchema.setYearMonth("000000");
                        tFIDataTransGatherSchema.setReturnFlag("01");
                        tFIDataTransGatherSchema.setVerifyFlag("01");
                        tFIDataTransGatherSchema.setReturnDate(aSSRS.GetText(i, 5));
                    }

                    if(tFIDataTransGatherSchema.getSumMoney()<0)
                    {
                        if(tFIDataTransGatherSchema.getFinItemType().equals("D"))
                        {
                            tFIDataTransGatherSchema.setFinItemType("C");
                        }
                        else
                        {
                            tFIDataTransGatherSchema.setFinItemType("D");
                        }
                        tFIDataTransGatherSchema.setSumMoney(-tFIDataTransGatherSchema.getSumMoney());
                    }


                    tFIDataTransGatherSet.add(tFIDataTransGatherSchema);
                }

                MMap tmap = new MMap();
                VData tInputData = new VData();
                tmap.put(tFIDataTransGatherSet, "INSERT");
                tInputData.add(tmap);
                PubSubmit tPubSubmit = new PubSubmit();
                if (!tPubSubmit.submitData(tInputData, ""))
                {
                    this.mErrors.copyAllErrors(tPubSubmit.mErrors);
                    buildError("FIMoveDataFromGRPToPRO", "moveDataFromGRPToPRO",
                               "批次号码为" + mBatchNo + "的汇总凭证数据保存在接口表时出错，提示信息为：" +
                               tPubSubmit.mErrors.getFirstError());
                    mLogInfoDeal.WriteLogTxt("批次号码为" + mBatchNo +
                                             "的汇总凭证数据保存在接口表时出错，提示信息为：" +
                                             tPubSubmit.mErrors.getFirstError() + enter);
                    return false;
                }
                return true;
            }
            catch (Exception ex)
            {
                buildError("FIMoveDataFromGRPToPRO", "setBigCertificate",
                           "批次号码为" + mBatchNo + "的汇总凭证数据处理过程出现异常,信息为：" + ex.getMessage());
                mLogInfoDeal.WriteLogTxt("批次号码为" + mBatchNo + "的汇总凭证数据处理过程出现异常,信息为：" +
                                         ex.getMessage());
                return false;
            }

        }

    private boolean DealAssociatedData()
    {
        try
        {
            mLogInfoDeal.WriteLogTxt("开始对该批次凭证数据进行专项数值转换" + enter);
            for (int i = 0; i < mFIAssociatedItemDefSet.size(); i++)
            {
                if(!mFIAssociatedDataTrans[i].upDateClumn(mBatchNo))
                {
                     this.mErrors.copyAllErrors(mFIAssociatedDataTrans[i].mErrors);
                     return false;
                }

            }
            return true;
        }
        catch (Exception ex)
        {
            buildError("FIMoveDataFromGRPToPRO","DealAssociatedData", "专项数值转换异常,信息为：" + ex.getMessage());
            mLogInfoDeal.WriteLogTxt("专项数值转换异常,信息为：" + ex.getMessage() + enter);
            return false;
        }
    }



    private boolean checkAssociatedData()
    {
        try
        {
            mLogInfoDeal.WriteLogTxt("开始对该批次凭证数据进行专项数值转换映射编码完整性校验" + enter);
            for (int i = 0; i < mFIAssociatedItemDefSet.size(); i++)
            {
                if(!mFIAssociatedDataTrans[i].checkDataClumn(mBatchNo))
                {
                     this.mErrors.copyAllErrors(mFIAssociatedDataTrans[i].mErrors);
                     return false;
                }

            }
            return true;
        }
        catch (Exception ex)
        {
            buildError("FiDistillForCertMain","checkAssociatedData", "专项数值转换映射编码完整性校异常,信息为：" + ex.getMessage());
            mLogInfoDeal.WriteLogTxt("专项数值转换映射编码完整性校异常,信息为：" + ex.getMessage() + enter);
            return false;
        }
    }



    private void buildError(String szModuleName, String szFunc, String szErrMsg)
    {
        CError cError = new CError();
        cError.moduleName = szModuleName;
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
        logger.debug(szErrMsg);
    }

}
