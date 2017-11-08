

package com.sinosoft.lis.reinsure;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIItemCalSchema;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;



public class RIItemCalDelete implements BusinessService
{
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap mMap = new MMap();

    private PubSubmit tPubSubmit = new PubSubmit();

	
	private String mVersionNo = "";
	private String mFinItemID = "";
	private String mJudgementNo = "";
	private GlobalInput globalInput = new GlobalInput();
	private RIItemCalSchema cRIItemCalSchema = new RIItemCalSchema();

	public RIItemCalDelete()
	{}
	
	public String getVersionNo()
	{
		return mVersionNo;
	}	

	public String getFinItemID()
	{
		return mFinItemID;
	}
	
	public String getJudgementNo()
	{
		return mJudgementNo;
	}
	

	/**
	 * 传输数据的公共方法
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate)
	{
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		System.out.println("getInputData");
		if (!getInputData(cInputData))
		{
			return false;
		}

		System.out.println("dealData");
		if (!dealData())
		{
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * @param cInputData VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData)
	{
		globalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
        this.cRIItemCalSchema.setSchema((RIItemCalSchema) cInputData.getObjectByObjectName("RIItemCalSchema", 0));
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * @return
	 */
	private boolean dealData()
	{
		mMap.put(cRIItemCalSchema,"DELETE");
		this.mInputData.clear();
        this.mInputData.add(mMap);
        if (!tPubSubmit.submitData(this.mInputData, ""))
        {
            if (tPubSubmit.mErrors.needDealError()) {
                // @@错误处理
                buildError("insertData", "保存再保合同信息时出现错误!");
                return false;
            }
        }
        mMap = null;
        tPubSubmit = null;
		return true;
	}
	
	private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError();

        cError.moduleName = "ReComManageBL";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }

	public VData getResult()
	{
		return this.mResult;

	}

	public static void main(String[] args)
	{
		
	}

	public CErrors getErrors() {
		
		// TODO Auto-generated method stub
		return mErrors;
		
	}

}
