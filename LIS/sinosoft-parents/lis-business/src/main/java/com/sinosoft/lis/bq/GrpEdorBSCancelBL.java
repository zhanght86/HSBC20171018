package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;
/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保险期间中断删除</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author 万泽辉 2006-04-21
 * @version 1.0
 */
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.bq.EdorCancel;
import com.sinosoft.lis.pubfun.*;

public class GrpEdorBSCancelBL implements EdorCancel
{
private static Logger logger = Logger.getLogger(GrpEdorBSCancelBL.class);

    /** 传入数据的容器 */
    private VData mInputData = new VData();
    /** 传出数据的容器 */
    private MMap mMap = new MMap();
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    /** 错误处理类 */
    public CErrors mErrors = new CErrors();
    /** 全局基础数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();

    public GrpEdorBSCancelBL()
    {
    }
    /**
     * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串，主要包括"INSERT"
     * @return 布尔值（true--提交成功, false--提交失败）
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        this.mInputData = (VData) cInputData.clone();

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

        //准备往后台的数据
        if (!prepareOutputData())
        {
            return false;
        }

        return true;
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
            mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
                    "GlobalInput", 0);
            mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData.
                                   getObjectByObjectName("LPGrpEdorItemSchema",
                    0);
        }
        catch (Exception e)
        {
            // @@错误处理
            e.printStackTrace();
            CError tError = new CError();
            tError.moduleName = "GrpEdorNRCancelBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接收数据失败!!";
            this.mErrors.addOneError(tError);
            return false;
        }

        if (mGlobalInput == null || mLPGrpEdorItemSchema == null)
        {
            CError tError = new CError();
            tError.moduleName = "GrpEdorNRCancelBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "输入数据有误!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }


    /**
     * 根据前面的输入数据，进行逻辑处理
     * @return 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData()
    {
        String tGrpContNo = mLPGrpEdorItemSchema.getGrpContNo();
        String tEdorNo = mLPGrpEdorItemSchema.getEdorNo();
        mMap.put("delete from lpgrpcontstate where grpcontno='" + tGrpContNo + "'"
                 +" and edorno = '"+tEdorNo+"'",
                         "DELETE");
        mMap.put("delete from lpcontstate where edorno = '"+tEdorNo+"'","DELETE");
        return true;
    }
    /**
     * 准备往后层输出所需要的数据
     * @return 如果准备数据时发生错误则返回false,否则返回true
     */
    private boolean prepareOutputData()
    {
        try
        {
            mResult.clear();
            mResult.add(mMap);
        }
        catch (Exception e)
        {
            // @@错误处理
            e.printStackTrace();
            CError tError = new CError();
            tError.moduleName = "GrpEdorNICancelBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错! ";
            this.mErrors.addOneError(tError);
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
	public CErrors getErrors() {
		// TODO 自动生成方法存根
		return mErrors;
	}

}
