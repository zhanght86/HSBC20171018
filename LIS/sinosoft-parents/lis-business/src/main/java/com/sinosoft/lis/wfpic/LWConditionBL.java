package com.sinosoft.lis.wfpic;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LWConditionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LWConditionSchema;
import com.sinosoft.lis.vschema.LWConditionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
/*
*@author:lk
*@date:20080904 
*/

public class LWConditionBL 
{
private static Logger logger = Logger.getLogger(LWConditionBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();
    private MMap map = new MMap();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 全局数据 */
    private GlobalInput mGlobalInput;


    private LWConditionSet mLWConditionSet = new LWConditionSet();
    
//    private String StrErr="";

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate)
    {

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData)) 
        {
            CError.buildErr(this,"获取数据失败");
            return false;
        }

        //进行业务处理
        if (!dealData())
        {
            // @@错误处理
            CError.buildErr(this,"数据处理失败");
            return false;
        }
        
        logger.debug("over dealData");
        //准备往后台的数据
        if (!prepareOutputData()) 
        {
            return false;
        }
        PubSubmit tSubmit = new PubSubmit();
        if (!tSubmit.submitData(mInputData, "")) 
        { //数据提交
            // @@错误处理
            this.mErrors.copyAllErrors(tSubmit.mErrors);
            return false;
        }
        return true;
    }

    /**
     * 根据前面的输入数据，进行BL逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData() 
    {
    	for(int i=1;i<=this.mLWConditionSet.size();i++)
    	{
    		LWConditionSchema tLWConditionSchema = mLWConditionSet.get(i);
    		LWConditionDB tLWConditionDB = new LWConditionDB();
    		tLWConditionDB.setSchema(tLWConditionSchema);
    		if(tLWConditionDB.getInfo())
    		{
    			this.map.put(tLWConditionSchema,"UPDATE");
    		}
    		else
    		{
    			this.map.put(tLWConditionSchema,"INSERT");
    		}
    		
    	}
        return true;
    }   
    
    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData) 
    {
        //全局变量
        mGlobalInput=(GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
       
        this.mLWConditionSet = (LWConditionSet)cInputData.getObjectByObjectName("LWConditionSet", 0);
        
        return true;

    }

    /**
     * 准备往后层输出所需要的数据
     * 输出：如果准备数据时发生错误则返回false,否则返回true
     */
    private boolean prepareOutputData() 
    {
        try 
        {
            mInputData.clear();
            mInputData.add(map);
        } 
        catch (Exception ex)
        {
            // @@错误处理
            CError.buildErr(this,"准备数据失败");
            return false;
        }
        return true;
    }

    public VData getResult()
    {
    	//mResult.add(RebuildXML);
        return this.mResult;
    }
    
    
}
