/*
* <p>ClassName: LAComToAgentBL </p>
* <p>Description: LAContBL类文件 </p>
* <p>Copyright: Copyright (c) 2002</p>
* <p>Company: sinosoft </p>
* @Database: 销售管理
* @CreateDate：2003-02-27
 */
package com.sinosoft.lis.wagecal;
import org.apache.log4j.Logger;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;

import com.sinosoft.utility.*;

import java.sql.*;
public class LAComToAgentBL
{
private static Logger logger = Logger.getLogger(LAComToAgentBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();
    /** 往后面传输数据的容器 */
    private VData mInputData;
    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    /*时间变量*/
    private String currentDate = PubFun.getCurrentDate();
    private String currentTime = PubFun.getCurrentTime();
    private int flag = 0;
    /** 数据操作字符串 */
    private String mOperate;
    /** 业务处理相关变量 */
    private LAComToAgentSchema mLAComToAgentSchema = new LAComToAgentSchema();
    private LAComToAgentSchema aLAComToAgentSchema = new LAComToAgentSchema();
    private LAComToAgentSet mLAComToAgentSet = new LAComToAgentSet();

    //当更新和删除的时候备份表
    private LAComToAgentBSchema mLAComToAgentBSchema = new LAComToAgentBSchema();
    private LAComToAgentBSet mLAComToAgentBSet = new LAComToAgentBSet();

    public LAComToAgentBL()
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
        throws Exception
    {
        boolean tReturn = false;

        //将操作数据拷贝到本类中
        this.mOperate = cOperate;
        logger.debug("come in BL");
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
        {
            return false;
        }
        logger.debug("come to check!");
        if (!changeValiable())
        {
            return false;
        }
        logger.debug("end check");
        //备份操作要提前
        logger.debug(" mOperate:" + this.mOperate);
        if (!this.mOperate.equals("INSERT||MAIN")) //修改操作
        {
            this.backup(); //备份操作要提前
        }
        logger.debug("end backup");
        //进行业务处理
        if (!dealData())
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LAComToAgentBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据处理失败LAContBL-->dealData!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //准备往后台的数据
        if (!prepareOutputData())
        {
            return false;
        }
        if (this.mOperate.equals("QUERY||MAIN"))
        {
            this.submitquery();
        }
        else
        {
            if (flag == 0)
            {
                logger.debug("Start LAComToAgentBL Submit...");
                LAComToAgentBLS tLAComToAgentBLS = new LAComToAgentBLS();
                tReturn = tLAComToAgentBLS.submitData(mInputData, cOperate);
                logger.debug("End LAComToAgentBL Submit...");
                //如果有需要处理的错误，则返回
                if (tLAComToAgentBLS.mErrors.needDealError())
                {
                    // @@错误处理
                    this.mErrors.copyAllErrors(tLAComToAgentBLS.mErrors);
                    CError tError = new CError();
                    tError.moduleName = "LAComToAgentBL";
                    tError.functionName = "submitDat";
                    tError.errorMessage = "数据提交失败!";
                    this.mErrors.addOneError(tError);
                    return false;
                }
            }
            else
            {
                return true;
            }
        }

        mInputData = null;
        if (!tReturn)
        {
            Exception ex = new Exception("程序抛出错误：保存失败！");
            throw ex;
        }
        return tReturn;
    }
    /**
     * 根据前面的输入数据，进行BL逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData()
    {
        if (flag == 1)
        {
            return true;
        }
        boolean tReturn = true;
        for (int i = 1; i <= this.mLAComToAgentSet.size(); i++)
        {
            this.mLAComToAgentSchema = this.mLAComToAgentSet.get(i);
            logger.debug("中介机构：" + this.mLAComToAgentSchema.getAgentCom());
            if (this.mLAComToAgentSchema.getRelaType().equals("0"))
            {
                LABranchGroupSet tLABranchGroupSet = new LABranchGroupSet();
                LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();

                //判断对应渠道组是否为空
                if ((this.mLAComToAgentSchema.getAgentGroup() != null)
                        && !this.mLAComToAgentSchema.getAgentGroup().equals(""))
                {
                    tLABranchGroupDB.setBranchAttr(this.mLAComToAgentSchema
                        .getAgentGroup());
                    tLABranchGroupSet = tLABranchGroupDB.query();
                    if (tLABranchGroupSet.size() > 0)
                    {
                        this.mLAComToAgentSchema.setAgentGroup(tLABranchGroupSet.get(
                                1).getAgentGroup());
                    }
                    this.mLAComToAgentSchema.setAgentCode("");
                    //检验是否有改动
                    logger.debug(
                        "mLAComToAgentSet.get(1).getRelaType().trim()=="
                        + mLAComToAgentSet.get(1).getRelaType().trim());
                    logger.debug(
                        "mLAComToAgentSchema.getAgentGroup().trim()=="
                        + mLAComToAgentSet.get(1).getAgentGroup().trim());
                    if (aLAComToAgentSchema.getAgentGroup() != null)
                    {
                        if (mLAComToAgentSchema.getAgentGroup().trim().equals(aLAComToAgentSchema.getAgentGroup()
                                                                                                     .trim()))
                        {
                            flag = 1;
                            logger.debug("不做lacomtoagent表的相应操作，flag＝1");
                            return true;
                        }
                    }
                }

                //若为空，则将branchattr＝“”；
                else
                {
                    LAComToAgentDB aa = new LAComToAgentDB();
                    aa.setAgentCom(mLAComToAgentSchema.getAgentCom());
                    aa.setRelaType("0");
                    aa.query();
                    tLABranchGroupDB.setAgentGroup(aa.getAgentGroup());
                    tLABranchGroupDB.setBranchAttr("");

                    Connection conn;
                    conn = null;
                    conn = DBConnPool.getConnection();
                    if (conn == null)
                    {
                        // @@错误处理
                        CError tError = new CError();
                        tError.moduleName = "LAComToAgentBLS";
                        tError.functionName = "saveData";
                        tError.errorMessage = "数据库连接失败!";
                        this.mErrors.addOneError(tError);
                        return false;
                    }
                    try
                    {
                        conn.setAutoCommit(false);
                        logger.debug("删除branchattr……");

                        if (!tLABranchGroupDB.update())
                        {
                            // @@错误处理
                            this.mErrors.copyAllErrors(tLABranchGroupDB.mErrors);
                            CError tError = new CError();
                            tError.moduleName = "LAComToAgentBLS";
                            tError.functionName = "saveData";
                            tError.errorMessage = "数据保存失败!";
                            this.mErrors.addOneError(tError);
                            conn.rollback();
                            conn.close();
                            return false;
                        }
                        conn.commit();
                        conn.close();
                    }
                    catch (Exception ex)
                    {
                        // @@错误处理
                        CError tError = new CError();
                        tError.moduleName = "LAComToAgentBL--tLABranchGroupDB";
                        tError.functionName = "submitData";
                        tError.errorMessage = ex.toString();
                        this.mErrors.addOneError(tError);
                        tReturn = false;
                        try
                        {
                            conn.rollback();
                            conn.close();
                        }
                        catch (Exception e)
                        {
                        }
                    }
                }
            }
            else
            {
                if (aLAComToAgentSchema.getAgentCode() != null)
                {
                    if (mLAComToAgentSchema.getAgentCode().trim().equals(aLAComToAgentSchema.getAgentCode()
                                                                                                .trim()))
                    {
                        flag = 1;
                        logger.debug("不做lacomtoagent表的相应操作，flag＝1-----");
                        return true;
                    }
                }
                LAAgentDB tLAAgentDB = new LAAgentDB();
                tLAAgentDB.setAgentCode(this.mLAComToAgentSchema.getAgentCode());
                tLAAgentDB.getInfo();
                this.mLAComToAgentSchema.setAgentGroup(tLAAgentDB.getAgentGroup());
            }
            this.mLAComToAgentSchema.setModifyDate(currentDate);
            this.mLAComToAgentSchema.setModifyTime(currentTime);
            this.mLAComToAgentSchema.setMakeDate(currentDate);
            this.mLAComToAgentSchema.setMakeTime(currentTime);
            this.mLAComToAgentSchema.setStartDate(currentDate);
            this.mLAComToAgentSchema.setEndDate("");
            this.mLAComToAgentSchema.setOperator(mGlobalInput.Operator);
            this.mLAComToAgentSet.set(i, this.mLAComToAgentSchema);
        }
        this.mLAComToAgentSet.add(aLAComToAgentSchema);
        tReturn = true;
        return tReturn;
    }
    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData)
    {
        this.mLAComToAgentSet.set((LAComToAgentSet) cInputData
            .getObjectByObjectName("LAComToAgentSet", 1));
        this.mGlobalInput.setSchema((GlobalInput) cInputData
            .getObjectByObjectName("GlobalInput", 0));
        return true;
    }
    /**
     * 准备往后层输出所需要的数据
     * 输出：如果准备数据时发生错误则返回false,否则返回true
     */
    private boolean submitquery()
    {
        this.mResult.clear();
        logger.debug("Start LAContBLQuery Submit...");
        LAComToAgentDB tLAComToAgentDB = new LAComToAgentDB();
        tLAComToAgentDB.setSchema(this.mLAComToAgentSchema);
        this.mLAComToAgentSet = tLAComToAgentDB.query();
        this.mResult.add(this.mLAComToAgentSet);
        logger.debug("End LAContBLQuery Submit...");
        //如果有需要处理的错误，则返回
        if (tLAComToAgentDB.mErrors.needDealError())
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLAComToAgentDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LAComToAgentBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        mInputData = null;
        return true;
    }
    private boolean prepareOutputData()
    {
        try
        {
            this.mInputData = new VData();
            this.mInputData.add(this.mGlobalInput);
            this.mInputData.add(this.mLAComToAgentSet);
            this.mInputData.add(this.mLAComToAgentBSet);
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LAComToAgentBL";
            tError.functionName = "prepareData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }
    public VData getResult()
    {
        return this.mResult;
    }
    private boolean backup()
    {
        LAComToAgentSchema tLAComToAgentSchema;
        String currentDate = PubFun.getCurrentDate();
        String currentTime = PubFun.getCurrentTime();
        LAComToAgentDB tLAComToAgentDB = new LAComToAgentDB();
        tLAComToAgentDB.setAgentCom(this.mLAComToAgentSet.get(1).getAgentCom());
        tLAComToAgentDB.setRelaType(this.mLAComToAgentSet.get(1).getRelaType());
        LAComToAgentSet tLAComToAgentSet;
        tLAComToAgentSet = tLAComToAgentDB.query();

        if (tLAComToAgentSet.size() > 0)
        {
            aLAComToAgentSchema.setSchema(tLAComToAgentSet.get(1));
            this.mLAComToAgentBSchema.setAgentCode(this.aLAComToAgentSchema
                .getAgentCode());
            this.mLAComToAgentBSchema.setAgentCom(this.aLAComToAgentSchema
                .getAgentCom());
            this.mLAComToAgentBSchema.setAgentGroup(this.aLAComToAgentSchema
                .getAgentGroup());
            this.mLAComToAgentBSchema.setRelaType(this.aLAComToAgentSchema
                .getRelaType());
            this.mLAComToAgentBSchema.setEdorNo(PubFun1.CreateMaxNo("EdorNo", 20));
            this.mLAComToAgentBSchema.setEdorType("1");
            this.mLAComToAgentBSchema.setMakeDate(aLAComToAgentSchema
                .getMakeDate());
            this.mLAComToAgentBSchema.setMakeTime(aLAComToAgentSchema
                .getMakeTime());
            this.mLAComToAgentBSchema.setModifyDate(currentDate);
            this.mLAComToAgentBSchema.setModifyTime(currentTime);
            this.mLAComToAgentBSchema.setStartDate(aLAComToAgentSchema
                .getStartDate());
            this.mLAComToAgentBSchema.setEndDate(currentDate);
            this.mLAComToAgentBSchema.setOperator(mGlobalInput.Operator);
            mLAComToAgentBSet.add(mLAComToAgentBSchema);
        }
        return true;
    }
    private boolean changeValiable()
    {
        logger.debug("come to check in");
        logger.debug("relatype:"
            + this.mLAComToAgentSet.get(1).getRelaType());
        if (this.mLAComToAgentSet.get(1).getRelaType().equals("0"))
        {
            String AgentGroup = "";
            ExeSQL tExeSQL = new ExeSQL();
            String tSQL =
                "select AgentGroup from LABranchGroup where BranchAttr='"
                + "?BranchAttr?"
                + "' and BranchLevel='3' and (EndFlag is null or EndFlag ='N')";
            SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
      	    sqlbv1.sql(tSQL);
      	    sqlbv1.put("BranchAttr", this.mLAComToAgentSet.get(1).getAgentGroup());
            AgentGroup = tExeSQL.getOneValue(sqlbv1);
            if ((AgentGroup == null) || (AgentGroup.length() == 0))
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LAComToAgentBL";
                tError.functionName = "prepareData";
                tError.errorMessage = "负责机构不存在！";
                this.mErrors.addOneError(tError);
                return false;
            }
        }
        else
        {
            LAAgentDB tLAAgentDB = new LAAgentDB();
            tLAAgentDB.setAgentCode(this.mLAComToAgentSet.get(1).getAgentCode());
            tLAAgentDB.getInfo();
            if ((tLAAgentDB.getAgentGroup() == null)
                    || (tLAAgentDB.getAgentGroup().length() == 0))
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LAComToAgentBL";
                tError.functionName = "prepareData";
                tError.errorMessage = "不存在该代理人！";
                this.mErrors.addOneError(tError);
                return false;
            }
        }
        return true;
    }
}
