/*
 * <p>ClassName: LCAppntBL </p>
 * <p>Description: LCAppntSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2008-07-30
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LBAppntDB;
import com.sinosoft.lis.db.LBPOAppntDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LBAppntSchema;
import com.sinosoft.lis.schema.LBPOAppntSchema;
import com.sinosoft.lis.vschema.LBAppntSet;
import com.sinosoft.lis.vschema.LBPOAppntSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;

public class LBPOAppntBL extends LBPOAppntSchema
{
private static Logger logger = Logger.getLogger(LBPOAppntBL.class);

    // @Constructor
    public LBPOAppntBL()
    {
        this.setModifyDate(PubFun.getCurrentDate());
        this.setModifyTime(PubFun.getCurrentTime());
    }

    /**
     * 设置默认的字段属性
     */
    public void setDefaultFields()
    {
        this.setModifyDate(PubFun.getCurrentDate());
        this.setModifyTime(PubFun.getCurrentTime());
    }

    /**
     * 通过客户号码查询数据库，得到该客户的信息，放到投保人对应的字段中。
     * @param: cCustomerNo 客户号 ,cType 查询类型，1，表示对于输入的字段不覆盖
     * @return: no
     * @author: YT
     **/
    public void queryCustomerDataByDB(String cCustomerNo, String cType)
    {
        boolean isGrp = false;

        if (!isGrp) //处理个人客户
        {
            LDPersonBL tBL = new LDPersonBL();
            tBL.queryDataByDB(cCustomerNo);
//    this.setAppntGrade();//不清楚是否需要从界面录入
//*    this.setRelationToInsured();
//*    this.setCustomerNo();
              this.setAppntBirthday(tBL.getBirthday());
            this.setAppntName(tBL.getName());
            this.setAppntSex(tBL.getSex());
            this.setAppntNo(tBL.getCustomerNo());

            //this.setState(tBL.getState() );
        }
    }


    /**
     *从多投保人（个人）表和多投保人（个人）备份表读取信息
     * 返回true或false
     */
    public boolean getInfo()
    {
        Reflections tR = new Reflections();
        LBPOAppntDB tDB = new LBPOAppntDB();
        tDB.setSchema(this);
        if (!tDB.getInfo()) //如果查询失败，查询B表
        {
            LBAppntDB tDBB = new LBAppntDB();
            LBAppntSchema tLBAppntSchema = new LBAppntSchema();
            tR.transFields(tLBAppntSchema, this.getSchema());
            tDBB.setSchema(tLBAppntSchema);
            if (!tDBB.getInfo())
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LCAppntBL";
                tError.functionName = "getInfo";
                tError.errorMessage = "没有查询到多投保人（个人）表";
                this.mErrors.addOneError(tError);
                return false;
            }
            else
            {
                LBPOAppntSchema tS = new LBPOAppntSchema();
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
     *从多投保人（个人）表和多投保人（个人）备份表读取信息
     * 返回LCAppntSet
     *
     */
    public LBPOAppntSet query()
    {
        Reflections tR = new Reflections();
        LBPOAppntSet tLCAppntSet = new LBPOAppntSet();
        LBPOAppntDB tLCAppntDB = new LBPOAppntDB();
        tLCAppntDB.setSchema(this.getSchema());
        tLCAppntSet = tLCAppntDB.query();
        if (tLCAppntSet.size() == 0)
        {
            LBAppntSet tLBAppntSet = new LBAppntSet();
            LBAppntDB tLBAppntDB1 = new LBAppntDB();
            tR.transFields(tLBAppntDB1.getSchema(), this.getSchema());
            tLBAppntSet = tLBAppntDB1.query();
            if (tLBAppntSet.size() == 0)
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LCAppntBL";
                tError.functionName = "query";
                tError.errorMessage = "没有查询到多投保人（个人）表";
                this.mErrors.addOneError(tError);
                return tLCAppntSet;
            }
            else
            {
                tLCAppntSet.add(this.getSchema());
                tR.transFields(tLCAppntSet, tLBAppntSet);
            }
        }
        return tLCAppntSet;
    }

    /**
     *从多投保人（个人）表和多投保人（个人）备份表读取信息
     * 返回LCAppntSet
     *
     */
    public LBPOAppntSet executeQuery(SQLwithBindVariables sql)
    {
        Reflections tR = new Reflections();
        LBPOAppntSet tLCAppntSet = new LBPOAppntSet();
        LBPOAppntDB tLCAppntDB = new LBPOAppntDB();
        tLCAppntDB.setSchema(this.getSchema());
        tLCAppntSet = tLCAppntDB.executeQuery(sql);
        if (tLCAppntSet.size() == 0)
        {
            LBAppntSet tLBAppntSet = new LBAppntSet();
            LBAppntDB tLBAppntDB1 = new LBAppntDB();
            tR.transFields(tLBAppntDB1.getSchema(), this.getSchema());
            tLBAppntSet = tLBAppntDB1.executeQuery(sql);
            if (tLBAppntSet.size() == 0)
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LCAppntBL";
                tError.functionName = "query";
                tError.errorMessage = "没有查询到多投保人（个人）表";
                this.mErrors.addOneError(tError);
                return tLCAppntSet;
            }
            else
            {
                tLCAppntSet.add(this.getSchema());
                tR.transFields(tLCAppntSet, tLBAppntSet);
            }
        }
        return tLCAppntSet;
    }

    public static void main(String[] args)
    {
        //添加测试代码
    }


}
