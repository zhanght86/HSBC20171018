package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保全申请确认处理类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * @ReWrite ZhangRong
 * @version 1.0
 */

public class PEdorTIAppConfirmBL implements EdorAppConfirm
{
private static Logger logger = Logger.getLogger(PEdorTIAppConfirmBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData;

    private VData mResult = new VData();
    /** 数据操作字符串 */


    private GlobalInput mGlobalInput = new GlobalInput();
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
    private LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();

    public PEdorTIAppConfirmBL()
    {}

    /**
     * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串，主要包括""和""
     * @return 布尔值（true--提交成功, false--提交失败）
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData())
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

    /**
     * 数据输出方法，供外界获取数据处理结果
     * @return 包含有数据查询结果字符串的VData对象
     */
    public VData getResult()
    {
        return mResult;
    }

    /**
     * 将外部传入的数据分解到本类的属性中
     * @param: 无
     * @return: boolean
     */
    private boolean getInputData()
    {
        try
        {
            mLPEdorItemSchema = (LPEdorItemSchema) mInputData.
                                getObjectByObjectName("LPEdorItemSchema", 0);
            mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
                "GlobalInput", 0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PEdorNIAppConfirmBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接收数据失败!!";
            this.mErrors.addOneError(tError);
            return false;
        }

        if (mLPEdorItemSchema == null || mGlobalInput == null)
        {
            CError tError = new CError();
            tError.moduleName = "PEdorNIAppConfirmBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接收数据失败!!";
            this.mErrors.addOneError(tError);
            return false;
        }

        LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
        tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
        tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
        LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
        if (tLPEdorItemSet == null || tLPEdorItemSet.size() != 1)
        {
            mErrors.copyAllErrors(tLPEdorItemDB.mErrors);
            mErrors.addOneError(new CError("查询保全项目信息失败！"));
            return false;
        }
        mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));

        LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
        tLPEdorMainDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
        tLPEdorMainDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
        tLPEdorMainDB.setContNo(mLPEdorItemSchema.getContNo());
        if (!tLPEdorMainDB.getInfo())
        {
            mErrors.copyAllErrors(tLPEdorMainDB.mErrors);
            mErrors.addOneError(new CError("查询保全信息失败！"));
            return false;
        }
        mLPEdorMainSchema.setSchema(tLPEdorMainDB.getSchema());

        return true;
    }
   

    /**
     * 根据前面的输入数据，进行逻辑处理
     * @return 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData()
    {
        try
        {
        	String strSQL1 = "select count(*) from LPInsuAccOut where edorno = '?edorno?'";
            String strSQL2 = "select count(*) from LPInsuAccIn where edorno = '?edorno?'";
            SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
            SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
            sqlbv1.sql(strSQL1);
            sqlbv1.put("edorno", mLPEdorItemSchema.getEdorNo());
            sqlbv2.sql(strSQL2);
            sqlbv2.put("edorno", mLPEdorItemSchema.getEdorNo());
            ExeSQL ex = new ExeSQL();
            
            String count1 = ex.getOneValue(sqlbv1);
            String count2 = ex.getOneValue(sqlbv2);
            if(Integer.parseInt(count1)*Integer.parseInt(count2)==0)
            {    
            	
            	CError tError = new CError();
                tError.moduleName = "PEdorNIAppConfirmBL";
                tError.functionName = "dealData";
                tError.errorMessage = "转出或者转入帐户信息不全！";
                this.mErrors.addOneError(tError);
                return false;
            	
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PEdorNIAppConfirmBL";
            tError.functionName = "dealData";
            tError.errorMessage = "数据处理错误! " + e.getMessage();
            this.mErrors.addOneError(tError);
            return false;
        }

        return true;
    }

    public static void main(String[] args)
    {
        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput.Operator = "001";
        tGlobalInput.ManageCom = "86110000";

        LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
        tLPEdorItemSchema.setEdorNo("86110020030430000401");
        tLPEdorItemSchema.setGrpContNo("86110020030220000127");
        tLPEdorItemSchema.setEdorType("NI");
        tLPEdorItemSchema = (tLPEdorItemSchema.getDB().query()).get(1);

        VData tVData = new VData();
        tVData.add(tGlobalInput);
        tVData.add(tLPEdorItemSchema);

        PEdorNIAppConfirmBL tPEdorNIAppConfirmBL = new PEdorNIAppConfirmBL();
        if (!tPEdorNIAppConfirmBL.submitData(tVData, "INSERT"))
        {
            VData rVData = tPEdorNIAppConfirmBL.getResult();
            logger.debug("Submit Failed! " + (String) rVData.get(0));
        }
        else
        {
            logger.debug("Submit Succed!");
        }
    }

}
