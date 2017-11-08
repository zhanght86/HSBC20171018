/*
 * <p>ClassName: LBPOPolBL </p>
 * <p>Description: LCPolSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-07-09
 * @modify: YT 2002-11-29
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LBPolDB;
import com.sinosoft.lis.db.LBPOPolDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LBPolSchema;
import com.sinosoft.lis.schema.LBPOPolSchema;
import com.sinosoft.lis.vschema.LBPolSet;
import com.sinosoft.lis.vschema.LBPOPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;

public class LBPOPolBL extends LBPOPolSchema
{
private static Logger logger = Logger.getLogger(LBPOPolBL.class);

    // @Constructor
    public LBPOPolBL()
    {

    }

    public void setDefaultFields()
    {
        this.setMakeDate(PubFun.getCurrentDate());
        this.setMakeTime(PubFun.getCurrentTime());
        this.setModifyDate(PubFun.getCurrentDate());
        this.setModifyTime(PubFun.getCurrentTime());
    }

    /**
     *从保单表和保单备份表读取信息
     * 返回true或false
     */
    public boolean getInfo()
    {
        Reflections tR = new Reflections();
        LBPOPolDB tDB = new LBPOPolDB();
        tDB.setSchema(this);
        if (!tDB.getInfo()) //如果查询失败，查询B表
        {
            LBPolDB tDBB = new LBPolDB();
            LBPolSchema tLBPolSchema = new LBPolSchema();
            tR.transFields(tLBPolSchema, this.getSchema());
            tDBB.setSchema(tLBPolSchema);
            if (!tDBB.getInfo())
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LBPOPolBL";
                tError.functionName = "getInfo";
                tError.errorMessage = "没有查询到保单表";
                this.mErrors.addOneError(tError);
                return false;
            }
            else
            {
                LBPOPolSchema tS = new LBPOPolSchema();
                tR.transFields(tS, tDBB.getSchema());
                this.setSchema(tS);
            }
        }
        else
        {
            this.setSchema(tDB.getSchema());
        }
        return true;
    }

    /**
     *从保单表和保单备份表读取信息
     * 返回LBPOPolSet
     *
     */
    public LBPOPolSet query()
    {
        Reflections tR = new Reflections();
        LBPOPolSet tLBPOPolSet = new LBPOPolSet();
        LBPOPolDB tLBPOPolDB = new LBPOPolDB();
        tLBPOPolDB.setSchema(this);
        tLBPOPolSet = tLBPOPolDB.query();
        if (tLBPOPolSet.size() == 0)
        {
            LBPolSet tLBPolSet = new LBPolSet();
            LBPolDB tLBPolDB1 = new LBPolDB();
            LBPolSchema tLBPolSchema = new LBPolSchema();
            tR.transFields(tLBPolSchema, this.getSchema());
            tLBPolDB1.setSchema(tLBPolSchema);
            tLBPolSet = tLBPolDB1.query();
            if (tLBPolSet.size() == 0)
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LBPOPolBL";
                tError.functionName = "query";
                tError.errorMessage = "没有查询到保单表";
                this.mErrors.addOneError(tError);
                return tLBPOPolSet;
            }
            else
            {
                tLBPOPolSet.add(this.getSchema());
                tR.transFields(tLBPOPolSet, tLBPolSet);
            }
        }
        return tLBPOPolSet;
    }

    /**
     *从保单表和保单备份表读取信息
     * 返回LBPOPolSet
     *
     */
    public LBPOPolSet executeQuery(SQLwithBindVariables sql)
    {
        Reflections tR = new Reflections();
        LBPOPolSet tLBPOPolSet = new LBPOPolSet();
        LBPOPolDB tLBPOPolDB = new LBPOPolDB();
        tLBPOPolDB.setSchema(this.getSchema());
        tLBPOPolSet = tLBPOPolDB.executeQuery(sql);
        if (tLBPOPolSet.size() == 0)
        {
            LBPolSet tLBPolSet = new LBPolSet();
            LBPolDB tLBPolDB1 = new LBPolDB();
            tR.transFields(tLBPolDB1.getSchema(), this.getSchema());
            tLBPolSet = tLBPolDB1.executeQuery(sql);
            if (tLBPolSet.size() == 0)
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LBPOPolBL";
                tError.functionName = "query";
                tError.errorMessage = "没有查询到保单表";
                this.mErrors.addOneError(tError);
                return tLBPOPolSet;
            }
            else
            {
                tLBPOPolSet.add(this.getSchema());
                tR.transFields(tLBPOPolSet, tLBPolSet);
            }
        }
        return tLBPOPolSet;
    }

    public static void main(String[] args)
    {
        LBPOPolBL t = new LBPOPolBL();
        LBPOPolSet tSet = new LBPOPolSet();
        t.setProposalNo("86110020030110001802");
//    t.setPolNo("86110020020210300207");
//测试getInfo
//    t.setPolNo("86110020020110000680");
//    logger.debug(t.getInfo());
//    logger.debug(t.mErrors.getFirstError());
//    logger.debug(t.encode());
//测试query
        tSet = t.query();
        logger.debug(tSet.size());
//测试executeQuery
//    String tSQL ="select * from lcpol where proposalno='86110020030110001802'";
//    tSet=t.executeQuery(tSQL);
//    logger.debug(tSet.size());
//    logger.debug(tSet.encode());
//    logger.debug(t.mErrors.getFirstError());
    }

}
