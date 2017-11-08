/*
 * <p>ClassName: LCDutyBL </p>
 * <p>Description: LCDutySchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2005-06-25 续涛
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

public class SynLCLBDutyBL extends LCDutySchema
{
private static Logger logger = Logger.getLogger(SynLCLBDutyBL.class);



    public SynLCLBDutyBL(){}

    /**
     * 设置默认的字段属性
     */
    public void setDefaultFields()
    {
        this.setModifyDate(PubFun.getCurrentDate());
        this.setModifyTime(PubFun.getCurrentTime()) ;
    }


    /**
     *  从保险责任表和保险责任备份表读取信息
     *  根据主键值查找，只有一条记录
     *  返回true或false
     */
    public boolean getInfo()
    {
        Reflections tR=new Reflections();
        
        LCDutyDB tLCDutyDB = new LCDutyDB();
        tLCDutyDB.setSchema(this);
        
        //得到C表信息
        if (!tLCDutyDB.getInfo())//如果查询失败，查询B表
        {
            // 如果C表为空查B表
            LBDutyDB tLBDutyDB = new LBDutyDB();
            LBDutySchema tLBDutySchema=new LBDutySchema();                
            
            tR.transFields(tLBDutySchema,this.getSchema());                        
            tLBDutyDB.setSchema(tLBDutySchema);
            
            // 如果B表为空则错误            
            if (! tLBDutyDB.getInfo())
            {
                // @@错误处理
                CError tError =new CError();
                tError.moduleName="SynLCLBDutyBL";
                tError.functionName="getInfo";
                tError.errorMessage="没有查询到保险责任表";
                this.mErrors .addOneError(tError) ;
                return false;
            }
            else
            {
                //  如果B表不空则将数据赋值到C表
                LCDutySchema tLCDutySchema = new LCDutySchema();
                tR.transFields(tLCDutySchema,tLBDutyDB.getSchema());
                this.setSchema(tLCDutySchema);
            }
        }
        else
        {
            this.setSchema(tLCDutyDB.getSchema());
        }
        return true;
    }

    /**
     *从保险责任表和保险责任备份表读取信息
     * 返回LCDutySet，多条记录
     *
     */
    public LCDutySet query()
    {
        Reflections tR = new Reflections();
        
        LCDutySet tLCDutySet = new LCDutySet();
        
        LCDutyDB tLCDutyDB = new LCDutyDB();        
        tLCDutyDB.setSchema(this.getSchema());
        
        tLCDutySet=tLCDutyDB.query();
                
        // 如果C表为空查B表
        if (tLCDutySet.size()==0)
        {
            LBDutySet tLBDutySet =new LBDutySet();
            
            LBDutyDB tLBDutyDBTemp = new LBDutyDB();
            LBDutySchema tLBDutySchema = new LBDutySchema();
            
            tR.transFields(tLBDutySchema,this.getSchema());
            
            tLBDutyDBTemp.setSchema(tLBDutySchema);
            
            tLBDutySet=tLBDutyDBTemp.query();
            
            // 如果B表为空则错误
            if (tLBDutySet.size()==0)
            {
                // @@错误处理
                CError tError =new CError();
                tError.moduleName="SynLCLBDutyBL";
                tError.functionName="query";
                tError.errorMessage="没有查询到保险责任表";
                this.mErrors .addOneError(tError) ;
                return tLCDutySet;
            }
            else
            {
                tLCDutySet.add(this.getSchema());
                tR.transFields(tLCDutySet,tLBDutySet);
            }
        }
        return tLCDutySet;
    }

    
    /**
     *从保险责任表和保险责任备份表读取信息
     * 返回LCDutySet
     *
     */
    public LCDutySet executeQuery(String pSql)
    {
        Reflections tR=new Reflections();
        
        LCDutySet tLCDutySet =new LCDutySet();
        LCDutyDB tLCDutyDB=new LCDutyDB();
        
        tLCDutyDB.setSchema(this.getSchema());
        
        tLCDutySet=tLCDutyDB.executeQuery(pSql);
        
        // 如果C表为空查B表        
        if (tLCDutySet.size()==0)
        {
            LBDutySet tLBDutySet = new LBDutySet();            
            LBDutyDB tLBDutyDBTemp = new LBDutyDB();
            
            tR.transFields(tLBDutyDBTemp.getSchema(),this.getSchema());
            
            tLBDutySet=tLBDutyDBTemp.executeQuery(pSql);
            
            // 如果B表为空则错误            
            if (tLBDutySet.size()==0)
            {
                // @@错误处理
                CError tError =new CError();
                tError.moduleName="SynLCLBDutyBL";
                tError.functionName="executeQuery";
                tError.errorMessage="没有查询到保险责任表";
                this.mErrors .addOneError(tError) ;
                return tLCDutySet;
            }
            else
            {
                tLCDutySet.add(this.getSchema());
                tR.transFields(tLCDutySet,tLBDutySet);
            }
        }
        return tLCDutySet;
    }

    
    /**
     *从领取项表和领取项备份表读取信息
     * 返回LCGetSet
     *
     */
    public LCDutySet executeQuery(String pSsqlC,String pSsqlB)
    {
        Reflections tR=new Reflections();
        
        LCDutySet tLCDutySet =new LCDutySet();
        LCDutyDB tLCDutyDB=new LCDutyDB();
        
        tLCDutySet=tLCDutyDB.executeQuery(pSsqlC);
        
        // 如果C表为空查B表        
        if (tLCDutySet.size()==0)
        {
            LBDutySet tLBDutySet   = new LBDutySet();            
            LBDutyDB tLBDutyDBTemp = new LBDutyDB();            
            LBDutySchema tLBDutySchema = new LBDutySchema();
      
            tLBDutySet = tLBDutyDBTemp.executeQuery(pSsqlB);
            
            // 如果B表为空则错误            
            if (tLBDutySet.size()==0)
            {
                // @@错误处理
                CError tError =new CError();
                tError.moduleName="SynLCLBDutyBL";
                tError.functionName="executeQuery";
                tError.errorMessage="没有查询到领取项表";
                this.mErrors .addOneError(tError) ;
                return tLCDutySet;
            }
            else
            {
                tLCDutySet.add(this.getSchema());
                tR.transFields(tLCDutySet,tLBDutySet);
            }
        }
        return tLCDutySet;
    }
    
    public static void main(String[] args)
    {
      //添加测试代码
    }

}
