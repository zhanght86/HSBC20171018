/*
 * <p>ClassName: SysCertSendOutBL </p>
 * <p>Description: SysCertSendOutBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-10-29
 */
package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;

import com.sinosoft.utility.*;

public class SysCertSendOutBL
{
private static Logger logger = Logger.getLogger(SysCertSendOutBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();

    /** 全局数据 */
    private GlobalInput globalInput = new GlobalInput();

    /** 数据操作字符串 */
    private String mOperation = "";
    private static final String VALID_AGENT_STATE = " IN ('01', '02') ";


    /** 业务处理相关变量 */
    private LZSysCertifySet mLZSysCertifySet = new LZSysCertifySet();
    private LZSysCertifySet mDeleteSet = new LZSysCertifySet(); // 将被删除的记录
    private LZSysCertifySet mInsertSet = new LZSysCertifySet(); // 将将增加的记录

    public SysCertSendOutBL()
    {
    }

    public static void main(String[] args)
    {
    }

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
    *         cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        mOperation = verifyOperate(cOperate);

        if (mOperation.equals(""))
        {
            buildError("submitData", "不支持的操作字符串");

            return false;
        }

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
        {
            return false;
        }

        if (mOperation.equals("QUERY||MAIN"))
        {
            submitquery();

            return true;
        }

        //进行业务处理
        if (!dealData())
        {
            buildError("submitData", "数据处理失败SysCertSendOutBL-->dealData!");

            return false;
        }

        //准备往后台的数据
        VData vData = new VData();

        if (!prepareOutputData(vData))
        {
            return false;
        }

        if (mOperation.equals("INSERT||MAIN"))
        {
            SysCertSendOutBLS sysCertSendOutBLS = new SysCertSendOutBLS();

            if (!sysCertSendOutBLS.submitData(vData, mOperation))
            {
                if (sysCertSendOutBLS.mErrors.needDealError())
                {
                    mErrors.copyAllErrors(sysCertSendOutBLS.mErrors);
                }
                else
                {
                    buildError("submitData",
                               "数据提交失败SysCertSendOutBLS-->sutmitData，但是没有提供详细信息");
                }

                return false;
            }
        }

        return true;
    }

    /**
      * 根据前面的输入数据，进行BL逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData()
    {
        logger.debug("Start saving ...");

        try
        {
            if (mLZSysCertifySet.size() < 1)
            {
                return true;
            }

            String szTakeBackNo = PubFun1.CreateMaxNo("TAKEBACKNO",
                                                      PubFun.getNoLimit(globalInput.ComCode
                                                                        .substring(1)));
            String szSendNo = PubFun1.CreateMaxNo("TAKEBACKNO",
                                                  PubFun.getNoLimit(globalInput.ComCode
                                                                    .substring(1)));
            String szCurDate = PubFun.getCurrentDate();
            String szCurTime = PubFun.getCurrentTime();

            // 校验单证编码
            String strSql;

            strSql = "SELECT * FROM LMCertifyDes";
            strSql += (" WHERE CertifyCode = '"
            + "?CertifyCode?" + "'");
            strSql += (" AND CertifyClass = '" + CertifyFunc.CERTIFY_CLASS_SYS
            + "'");

            logger.debug(strSql);
            SQLwithBindVariables sqlbv = new SQLwithBindVariables() ;
            sqlbv.sql(strSql);
            sqlbv.put("CertifyCode", mLZSysCertifySet.get(1).getCertifyCode());
            if (new LMCertifyDesDB().executeQuery(sqlbv).size() != 1)
            {
                buildError("dealData", "错误的单证编码信息");

                return false;
            }


            //校验是否是个人保单-------------------------------------------
           logger.debug("In SysCertSendOutBL --- CertifyCode : " + mLZSysCertifySet.get(1).getCertifyCode());

           String sql;

            sql = "SELECT * FROM LMCertifyDes";
            sql += (" WHERE CertifyCode = '"
            + "?CertifyCode?" + "'");
            sql += (" AND CertifyClass = '" + CertifyFunc.CERTIFY_CLASS_SYS
            + "'")+(" AND JugAgtFlag in (2,3)");

            logger.debug("sql:"+sql);

            SQLwithBindVariables sqlbv1 = new SQLwithBindVariables() ;
            sqlbv1.sql(strSql);
            sqlbv1.put("CertifyCode", mLZSysCertifySet.get(1).getCertifyCode());
            if (new LMCertifyDesDB().executeQuery(sqlbv1).size() == 1)
            {
              //校验保单的代理人和录入的代理人是否一致
/*
              if(!CertifyFunc.verifyAgentCode(mLZSysCertifySet.get(1).getCertifyNo(),
                                              mLZSysCertifySet.get(1).getSendOutCom(),
                                              mLZSysCertifySet.get(1).getReceiveCom()))
              {
                buildError("saveLZSysCertify", CertifyFunc.mErrors.getFirstError());
                return false;
              }
*/
              //个人保单的情况下则不校验业务员的状态
              if (!CertifyFunc.isComsExist2(mLZSysCertifySet.get(1).getSendOutCom(),
                                       mLZSysCertifySet.get(1).getReceiveCom()))
              {
                buildError("dealData", CertifyFunc.mErrors.getFirstError());

                return false;
              }

              //
              if(mLZSysCertifySet.get(1).getReceiveCom().charAt(0)=='D')
              {
                  sql = "SELECT * FROM LAAgent" +
                  " WHERE AgentCode = '" + "?AgentCode?" + "'" +
                  " AND AgentState " +VALID_AGENT_STATE;

                  logger.debug("sql:"+sql);
                  SQLwithBindVariables sqlbv2 = new SQLwithBindVariables() ;
                  sqlbv2.sql(strSql);
                  sqlbv2.put("AgentCode", mLZSysCertifySet.get(1).getReceiveCom().substring(1));
                  if( new LAAgentDB().executeQuery(sqlbv2).size() != 1 )
                  {
                       logger.debug("--------------------------------------");
                      buildError("isComsExist", "已成功发放！注意："+mLZSysCertifySet.get(1).getReceiveCom().substring(1) + "该业务员不存在或者已经失效!");
                  }
              }
            }
            else
            {
              if (!CertifyFunc.isComsExist(mLZSysCertifySet.get(1).getSendOutCom(),
                                       mLZSysCertifySet.get(1).getReceiveCom()))
              {
                buildError("dealData", CertifyFunc.mErrors.getFirstError());

                return false;
              }
            }

            // 清空缓存
            mDeleteSet.clear();
            mInsertSet.clear();

            LZSysCertifySchema tLZSysCertifySchema = null;

            for (int nIndex = 0; nIndex < mLZSysCertifySet.size(); nIndex++)
            {
                tLZSysCertifySchema = new LZSysCertifySchema();
                tLZSysCertifySchema.setSchema(mLZSysCertifySet.get(nIndex + 1));

                strSql = "SELECT * FROM LZSysCertify";
                strSql += (" WHERE CertifyCode = '"
                + "?CertifyCode?" + "'");
                strSql += (" AND CertifyNo = '"
                + "?CertifyNo?" + "'");
                SQLwithBindVariables sqlbv3 = new SQLwithBindVariables() ;
                sqlbv3.sql(strSql);
                sqlbv3.put("CertifyCode", tLZSysCertifySchema.getCertifyCode());
                sqlbv3.put("CertifyNo", tLZSysCertifySchema.getCertifyNo());
                LZSysCertifySet tLZSysCertifySet = new LZSysCertifyDB()
                                                   .executeQuery(sqlbv3);

                // 数据库中有这样的记录
                if (tLZSysCertifySet.size() > 0)
                {
                    if (tLZSysCertifySet.get(1).getStateFlag().equals("0"))
                    {
                        mDeleteSet.add(tLZSysCertifySchema);
                    }
                    else
                    {
                        throw new Exception("要发放的单证已经发放过了，并且不处于发放状态！");
                    }
                }

                tLZSysCertifySchema = new LZSysCertifySchema();
                tLZSysCertifySchema.setSchema(mLZSysCertifySet.get(nIndex + 1));

                tLZSysCertifySchema.setSendNo(szSendNo);
                tLZSysCertifySchema.setTakeBackNo(szTakeBackNo);
                tLZSysCertifySchema.setMakeDate(szCurDate);
                tLZSysCertifySchema.setMakeTime(szCurTime);
                tLZSysCertifySchema.setModifyDate(szCurDate);
                tLZSysCertifySchema.setModifyTime(szCurTime);
                tLZSysCertifySchema.setOperator(globalInput.Operator);
                tLZSysCertifySchema.setStateFlag("0");

                mInsertSet.add(tLZSysCertifySchema);
            }
             // end of for( nIndex = 0; nIndex < setLZSysCertify; nIndex ++ )
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            buildError("dealData", ex.toString());

            return false;
        }

        logger.debug("End of saving ...");

        return true;
    }

    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData)
    {
        globalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput",
                                                                             0));
        mLZSysCertifySet.set((LZSysCertifySet) cInputData.getObjectByObjectName("LZSysCertifySet",
                                                                                0));

        logger.debug("SysCertSendOutBL : size is "
                           + String.valueOf(mLZSysCertifySet.size()));

        return true;
    }

    /**
     * 准备往后层输出所需要的数据
     * 输出：如果准备数据时发生错误则返回false,否则返回true
     */
    private boolean submitquery()
    {
        mResult.clear();

        logger.debug("Start query in SysCertSendOutBL ...");

        if (mLZSysCertifySet.size() < 1)
        {
            buildError("submitquery", "没有设置查询的条件");

            return false;
        }

        LZSysCertifyDB dbLZSysCertify = new LZSysCertifyDB();

        dbLZSysCertify.setSchema(mLZSysCertifySet.get(1));

        mResult.add(dbLZSysCertify.query());

        logger.debug("End query in SysCertSendOutBL ...");

        //如果有需要处理的错误，则返回
        if (dbLZSysCertify.mErrors.needDealError())
        {
            buildError("submitquery", "查询失败");

            return false;
        }

        return true;
    }

    private boolean prepareOutputData(VData vData)
    {
        try
        {
            vData.add(globalInput);

            vData.add(mDeleteSet);
            vData.add(mInsertSet);

            return true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            buildError("prepareOutputData", "在准备往后层处理所需要的数据时出错");

            return false;
        }
    }

    public VData getResult()
    {
        return this.mResult;
    }

    /*
     * add by kevin, 2002-09-23
     */
    private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError();

        cError.moduleName = "SysCertSendOutBL";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }

    private String verifyOperate(String szOperate)
    {
        String szReturn = "";
        String[] szOperates =
                              {
                                  "INSERT||MAIN", "DELETE||MAIN", "UPDATE||MAIN",
                                  "QUERY||MAIN"
                              };

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
