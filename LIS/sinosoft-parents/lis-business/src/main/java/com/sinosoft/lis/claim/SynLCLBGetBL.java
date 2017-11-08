/*
 * <p>ClassName: LCGetBL </p>
 * <p>Description: LCGetSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2005-06-25 续涛
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;
import java.sql.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

public class SynLCLBGetBL extends LCGetSchema
{
private static Logger logger = Logger.getLogger(SynLCLBGetBL.class);


	public SynLCLBGetBL(){}

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
        
        LCGetDB tLCGetDB = new LCGetDB();
        tLCGetDB.setSchema(this);
        
        //得到C表信息
        if (!tLCGetDB.getInfo())//如果查询失败，查询B表
        {
            // 如果C表为空查B表
            LBGetDB tLBGetDB = new LBGetDB();
            LBGetSchema tLBGetSchema=new LBGetSchema();                
            
            tR.transFields(tLBGetSchema,this.getSchema());                        
            tLBGetDB.setSchema(tLBGetSchema);
            
            // 如果B表为空则错误            
            if (! tLBGetDB.getInfo())
            {
                // @@错误处理
                CError tError =new CError();
                tError.moduleName="SynLCLBGetBL";
                tError.functionName="getInfo";
                tError.errorMessage="没有查询到保险责任表";
                this.mErrors .addOneError(tError) ;
                return false;
            }
            else
            {
                //  如果B表不空则将数据赋值到C表
                LCGetSchema tLCGetSchema = new LCGetSchema();
                tR.transFields(tLCGetSchema,tLBGetDB.getSchema());
                this.setSchema(tLCGetSchema);
            }
        }
        else
        {
            this.setSchema(tLCGetDB.getSchema());
        }
        return true;
    }
    

    /**
     *从领取项表和领取项备份表读取信息
     * 返回LCGetSet
     *
     */
    public LCGetSet query()
    {
        Reflections tR = new Reflections();
        
        LCGetSet tLCGetSet = new LCGetSet();
        
        LCGetDB tLCGetDB = new LCGetDB();        
        tLCGetDB.setSchema(this.getSchema());
        
        tLCGetSet=tLCGetDB.query();
        
        // 如果C表为空查B表        
        if (tLCGetSet.size()==0)
        {
            LBGetSet tLBGetSet = new LBGetSet();
            
            LBGetDB tLBGetDBTemp = new LBGetDB();
            LBGetSchema tLBGetSchema = new LBGetSchema();
            
            tR.transFields(tLBGetSchema,this.getSchema());
            
            tLBGetDBTemp.setSchema(tLBGetSchema);
            
            tLBGetSet=tLBGetDBTemp.query();
            
            // 如果B表为空则错误            
            if (tLBGetSet.size()==0)
            {
                // @@错误处理
                CError tError =new CError();
                tError.moduleName="SynLCLBGetBL";
                tError.functionName="query";
                tError.errorMessage="没有查询到领取项表";
                this.mErrors .addOneError(tError) ;
                return tLCGetSet;
            }
            else
            {

                tLCGetSet.add(this.getSchema());
                tR.transFields(tLCGetSet,tLBGetSet);
            }
        }
        return tLCGetSet;
    }

    
    /**
     *从保险责任表和保险责任备份表读取信息
     * 返回LCGetSet
     *
     */
    public LCGetSet executeQuery(String pSql)
    {
        Reflections tR=new Reflections();
        
        LCGetSet tLCGetSet =new LCGetSet();
        LCGetDB tLCGetDB=new LCGetDB();
        
        tLCGetDB.setSchema(this.getSchema());
        
        tLCGetSet=tLCGetDB.executeQuery(pSql);
        
        // 如果C表为空查B表   
        if (tLCGetSet.size()==0)
        {
            LBGetSet tLBGetSet   = new LBGetSet();            
            LBGetDB tLBGetDBTemp = new LBGetDB();
            
            tR.transFields(tLBGetDBTemp.getSchema(),this.getSchema());
            
            tLBGetSet=tLBGetDBTemp.executeQuery(pSql);
            
            // 如果B表为空则错误     
            if (tLBGetSet.size()==0)
            {
                // @@错误处理
                CError tError =new CError();
                tError.moduleName="SynLCLBGetBL";
                tError.functionName="executeQuery";
                tError.errorMessage="没有查询到保险责任表";
                this.mErrors .addOneError(tError) ;
                return tLCGetSet;
            }
            else
            {
                tLCGetSet.add(this.getSchema());
                tR.transFields(tLCGetSet,tLBGetSet);
            }
        }
        return tLCGetSet;
    }
    
    
    
    /**
     *从领取项表和领取项备份表读取信息
     * 返回LCGetSet
     *
     */
    public LCGetSet executeQuery(String pSsqlC,String pSsqlB)
    {
        Reflections tR=new Reflections();
        
        LCGetSet tLCGetSet =new LCGetSet();
        LCGetDB tLCGetDB=new LCGetDB();
        
        tLCGetSet=tLCGetDB.executeQuery(pSsqlC);
        
        // 如果C表为空查B表        
        if (tLCGetSet.size()==0)
        {
            LBGetSet tLBGetSet   = new LBGetSet();            
            LBGetDB tLBGetDBTemp = new LBGetDB();            
            LBGetSchema tLBGetSchema = new LBGetSchema();
      
            tLBGetSet = tLBGetDBTemp.executeQuery(pSsqlB);
            
            // 如果B表为空则错误            
            if (tLBGetSet.size()==0)
            {
                // @@错误处理
                CError tError =new CError();
                tError.moduleName="SynLCLBGetBL";
                tError.functionName="executeQuery";
                tError.errorMessage="没有查询到领取项表";
                this.mErrors .addOneError(tError) ;
                return tLCGetSet;
            }
            else
            {
                tLCGetSet.add(this.getSchema());
                tR.transFields(tLCGetSet,tLBGetSet);
            }
        }
        return tLCGetSet;
    }

    public static void main(String[] args)
    {
    //    String a = "";
    //      //添加测试代码
    //    SynLCLBGetBL aSynLCLBGetBL = new SynLCLBGetBL();
    //    aSynLCLBGetBL.setPolNo("86110020040210055727");
    //    aSynLCLBGetBL.setGetDutyCode("207201");
    //    LCGetSet bLCGetSet = new LCGetSet();
    //    bLCGetSet.set(aSynLCLBGetBL.query());
    //    logger.debug(bLCGetSet.size());
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
