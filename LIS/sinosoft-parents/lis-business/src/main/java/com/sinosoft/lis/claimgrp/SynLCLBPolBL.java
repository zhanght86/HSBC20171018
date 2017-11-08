/*
 * <p>ClassName: LCGetBL </p>
 * <p>Description: LCGetSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2005-06-25 续涛
 */

package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;
import java.sql.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

public class SynLCLBPolBL extends LCPolSchema
{
private static Logger logger = Logger.getLogger(SynLCLBPolBL.class);


	public SynLCLBPolBL(){}

    public void setDefaultFields()
    {
        this.setModifyDate(PubFun.getCurrentDate());
        this.setModifyTime(PubFun.getCurrentTime()) ;
    }


    /**
     *从保险责任表和保险责任备份表读取信息
     * 返回true或false
     */
    public boolean getInfo()
    {
        Reflections tR=new Reflections();
        
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setSchema(this);
        
        //得到C表信息
        if (!tLCPolDB.getInfo())//如果查询失败，查询B表
        {
            // 如果C表为空查B表
            LBPolDB tLBPolDB = new LBPolDB();
            LBPolSchema tLBPolSchema=new LBPolSchema();                
            
            tR.transFields(tLBPolSchema,this.getSchema());                        
            tLBPolDB.setSchema(tLBPolSchema);
            
            // 如果B表为空则错误            
            if (! tLBPolDB.getInfo())
            {
                // @@错误处理
                CError tError =new CError();
                tError.moduleName="SynLCLBPolBL";
                tError.functionName="getInfo";
                tError.errorMessage="没有查询到保险责任表";
                this.mErrors .addOneError(tError) ;
                return false;
            }
            else
            {
                //  如果B表不空则将数据赋值到C表
                LCPolSchema tLCPolSchema = new LCPolSchema();
                tR.transFields(tLCPolSchema,tLBPolDB.getSchema());
                this.setSchema(tLCPolSchema);
            }
        }
        else
        {
            this.setSchema(tLCPolDB.getSchema());
        }
        return true;
    }
    

    /**
     *从领取项表和领取项备份表读取信息
     * 返回LCPolSet
     *
     */
    public LCPolSet query()
    {
        Reflections tR = new Reflections();
        
        LCPolSet tLCPolSet = new LCPolSet();
        
        LCPolDB tLCPolDB = new LCPolDB();        
        tLCPolDB.setSchema(this.getSchema());
        
        tLCPolSet=tLCPolDB.query();
        
        // 如果C表为空查B表        
        if (tLCPolSet.size()==0)
        {
            LBPolSet tLBPolSet = new LBPolSet();
            
            LBPolDB tLBPolDBTemp = new LBPolDB();
            LBPolSchema tLBPolSchema = new LBPolSchema();
            
            tR.transFields(tLBPolSchema,this.getSchema());
            
            tLBPolDBTemp.setSchema(tLBPolSchema);
            
            tLBPolSet=tLBPolDBTemp.query();
            
            // 如果B表为空则错误            
            if (tLBPolSet.size()==0)
            {
                // @@错误处理
                CError tError =new CError();
                tError.moduleName="SynLCLBPolBL";
                tError.functionName="query";
                tError.errorMessage="没有查询到领取项表";
                this.mErrors .addOneError(tError) ;
                return tLCPolSet;
            }
            else
            {

                tLCPolSet.add(this.getSchema());
                tR.transFields(tLCPolSet,tLBPolSet);
            }
        }
        return tLCPolSet;
    }

    
    /**
     *从保险责任表和保险责任备份表读取信息
     * 返回LCPolSet
     *
     */
    public LCPolSet executeQuery(String pSql)
    {
        Reflections tR=new Reflections();
        
        LCPolSet tLCPolSet =new LCPolSet();
        LCPolDB tLCPolDB=new LCPolDB();
        
        tLCPolDB.setSchema(this.getSchema());
        
        tLCPolSet=tLCPolDB.executeQuery(pSql);
        
        // 如果C表为空查B表   
        if (tLCPolSet.size()==0)
        {
            LBPolSet tLBPolSet   = new LBPolSet();            
            LBPolDB tLBPolDBTemp = new LBPolDB();
            
            tR.transFields(tLBPolDBTemp.getSchema(),this.getSchema());
            
            tLBPolSet=tLBPolDBTemp.executeQuery(pSql);
            
            // 如果B表为空则错误     
            if (tLBPolSet.size()==0)
            {
                // @@错误处理
                CError tError =new CError();
                tError.moduleName="SynLCLBPolBL";
                tError.functionName="executeQuery";
                tError.errorMessage="没有查询到保险责任表";
                this.mErrors .addOneError(tError) ;
                return tLCPolSet;
            }
            else
            {
                tLCPolSet.add(this.getSchema());
                tR.transFields(tLCPolSet,tLBPolSet);
            }
        }
        return tLCPolSet;
    }
    
    
    
    /**
     *从领取项表和领取项备份表读取信息
     * 返回LCPolSet
     *
     */
    public LCPolSet executeQuery(String pSsqlC,String pSsqlB)
    {
        Reflections tR=new Reflections();
        
        LCPolSet tLCPolSet =new LCPolSet();
        LCPolDB tLCPolDB=new LCPolDB();
        
        tLCPolSet=tLCPolDB.executeQuery(pSsqlC);
        
        // 如果C表为空查B表        
        if (tLCPolSet.size()==0)
        {
            LBPolSet tLBPolSet   = new LBPolSet();            
            LBPolDB tLBPolDBTemp = new LBPolDB();            
            LBPolSchema tLBPolSchema = new LBPolSchema();
      
            tLBPolSet = tLBPolDBTemp.executeQuery(pSsqlB);
            
            // 如果B表为空则错误            
            if (tLBPolSet.size()==0)
            {
                // @@错误处理
                CError tError =new CError();
                tError.moduleName="SynLCLBPolBL";
                tError.functionName="executeQuery";
                tError.errorMessage="没有查询到领取项表";
                this.mErrors .addOneError(tError) ;
                return tLCPolSet;
            }
            else
            {
                tLCPolSet.add(this.getSchema());
                tR.transFields(tLCPolSet,tLBPolSet);
            }
        }
        return tLCPolSet;
    }

    public static void main(String[] args)
    {
    //    String a = "";
    //      //添加测试代码
    //    SynLCLBPolBL aSynLCLBPolBL = new SynLCLBPolBL();
    //    aSynLCLBPolBL.setPolNo("86110020040210055727");
    //    aSynLCLBPolBL.setGetDutyCode("207201");
    //    LCPolSet bLCPolSet = new LCPolSet();
    //    bLCPolSet.set(aSynLCLBPolBL.query());
    //    logger.debug(bLCPolSet.size());
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo("86110020040210055727");
        boolean t = tLCPolDB.getInfo();
        logger.debug("t的值是"+t);
    
        LBPolDB tLBPolDB = new LBPolDB();
        tLBPolDB.setPolNo("86110020040210055727");
        boolean tb = tLBPolDB.getInfo();
        logger.debug("t的值是"+tb);

    }


}
