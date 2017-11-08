package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.f1print.*;

/**
 * <p>Title: Web业务系统 </p>
 * <p>Description: 万能缓缴期解除回退BL</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author QianLy
 * @version 1.0
 */

public class PEdorTSBackDetailBL implements EdorBack
{
private static Logger logger = Logger.getLogger(PEdorTSBackDetailBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData;
    private MMap mMap = new MMap();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    /** 业务数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

    public PEdorTSBackDetailBL()
    {
    }

    /**
     * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串，主要包括""和""
     * @return 布尔值（true--提交成功, false--提交失败）
     */
    public boolean submitData(VData cInputData, String cOperate){
        //将操作数据拷贝到本类中
        this.mInputData = (VData) cInputData.clone();
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData()){
            return false;
        }

        //得到外部传入的数据,将数据备份到本类中
        if (!checkData()){
            return false;
        }
        
        mResult.add(mMap);

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

    public CErrors getErrors()
    {
        return this.mErrors;
    }

    /**
     * 将外部传入的数据分解到本类的属性中
     * @param: 无
     * @return: boolean
     */
    private boolean getInputData(){
        try{
            mGlobalInput = (GlobalInput)mInputData.getObjectByObjectName("GlobalInput", 0);
            //需要回退的保全项目
            mLPEdorItemSchema = (LPEdorItemSchema)mInputData.getObjectByObjectName("LPEdorItemSchema", 0);
        }catch (Exception e){
            e.printStackTrace();
            return this.makeError("getInputData","接收前台数据失败！");
        }
        if (mGlobalInput == null || mLPEdorItemSchema == null){
            return this.makeError("getInputData","传入数据有误！");
        }

        return true;
    }

    /**
     * 校验传入的数据的合法性
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean checkData(){
        LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
        tLPEdorItemDB.setSchema(mLPEdorItemSchema);
        if (!tLPEdorItemDB.getInfo()){
            // @@错误处理
            return this.makeError("checkData","无保全数据！");
        }

        mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());
        SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
		sqlbv15.put("contno",mLPEdorItemSchema.getContNo());
        String sflag = BqNameFun.getAnother(sqlbv15,"LMRiskApp", "riskcode in (select riskcode from lcpol where contno = '?contno?') and risktype3", "4", "'X'");
        if(sflag == null || !"X".equals(sflag)){
        	return this.makeError("checkData","投连缓缴期解除不能回退。");
        }
        
        if(mLPEdorItemSchema.getGetMoney() > 0){
        	 SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
        	 sqlbv16.put("accalterno",mLPEdorItemSchema.getEdorNo());
            String mflag = BqNameFun.getAnother(sqlbv16,"LCInsureAccTrace c", "busytype = 'TS' and accalterno = '?accalterno?' and paydate = (select max(paydate) from lcinsureacctrace where polno = c.polno) and contno",mLPEdorItemSchema.getContNo(),"'X'");
            if(mflag == null || !"X".equals(mflag)){
        	    return this.makeError("checkData","万能续期保费变更后帐户又进行了其他操作，不能回退。");
            }      
        }
        
        if (!"0".equals(tLPEdorItemDB.getEdorState())){
            // @@错误处理
            return this.makeError("checkData","此项目尚未确认生效！");
        }
        return true;
    }

    /**
     * 创建一个错误
     * @param vFuncName 发生错误的函数名
     * @param vErrMsg 错误信息
     * @return 布尔值（false--永远返回此值）
     */
    private boolean makeError(String vFuncName,String vErrMsg)
    {
        CError tError = new CError();
        tError.moduleName = "PEdorTSBackDetailBL";
        tError.functionName = vFuncName;
        tError.errorMessage = vErrMsg;
        this.mErrors.addOneError(tError);
        return false;
    }
}
