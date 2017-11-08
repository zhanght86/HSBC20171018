package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.schema.FIDataDealErrSchema;
import com.sinosoft.lis.vschema.FIDataDealErrSet;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;

public class DataDealErr
{
private static Logger logger = Logger.getLogger(DataDealErr.class);

	private VData mInputData;
	private MMap map = new MMap();
	public CErrors mErrors = new CErrors();
	private FIDataDealErrSet mFIDataDealErrSet = new FIDataDealErrSet();
	public DataDealErr()
	{
		
	}
	public boolean submitData(VData cInputData)
	{
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
		{
			return false;
		}
		// 进行业务处理
		if (!dealData())
		{
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData())
		{
			return false;
		}
		else
		{
			logger.debug("Start DataDealErr Submit...");
			String cOperate = "INSERT||MAIN";
			PubSubmit tPubSubmit = new PubSubmit();
            if (!tPubSubmit.submitData(mInputData, cOperate))
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tPubSubmit.mErrors);

                CError tError = new CError();
                tError.moduleName = "DataDealErr";
                tError.functionName = "submitData";
                tError.errorMessage = "数据提交失败!";
                this.mErrors.addOneError(tError);
                return false;
            }
    		logger.debug("End DataDealErr Submit...");		
		}
		mInputData = null;
		return true;
	}
	
	private boolean getInputData(VData cInputData)
	{
		this.mFIDataDealErrSet = (FIDataDealErrSet) cInputData.getObjectByObjectName("FIDataDealErrSet", 0);
		return true;
	}
	
	private boolean dealData()
	{
		boolean tReturn = true;
		map.put(mFIDataDealErrSet, "INSERT");
		return  tReturn;
	}
	
	private boolean prepareOutputData()
	{
		try
		{
			this.mInputData = new VData();
			this.mInputData.add(map);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "DataDealErr";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
	
	public static void main(String[] args)
	{
		boolean ttDataDealErr;
		FIDataDealErrSchema tFIDataDealErrSchema1 = new FIDataDealErrSchema();
		FIDataDealErrSchema tFIDataDealErrSchema2 = new FIDataDealErrSchema();
		FIDataDealErrSet tFIDataDealErrSet = new FIDataDealErrSet();
		tFIDataDealErrSchema1.setEeeSerialNo("13802");
		tFIDataDealErrSchema1.setErrStage("A10");
		tFIDataDealErrSchema1.setErrType("Err1");
		tFIDataDealErrSchema1.setBatchNo("123");
		tFIDataDealErrSchema1.setAFSerialNo("456");
		tFIDataDealErrSchema1.setBussDate("1999-08-09");
		tFIDataDealErrSchema1.setErrInfo("This is a Error");
		tFIDataDealErrSchema1.setErrDealState("B20");
		tFIDataDealErrSchema1.setDealDescription("C30");
		tFIDataDealErrSchema1.setMakeDate(PubFun.getCurrentDate());
		tFIDataDealErrSchema1.setMakeTime(PubFun.getCurrentTime());
		tFIDataDealErrSchema2.setEeeSerialNo("13803");
		tFIDataDealErrSchema2.setErrStage("A10");
		tFIDataDealErrSchema2.setErrType("Err1");
		tFIDataDealErrSchema2.setBatchNo("123");
		tFIDataDealErrSchema2.setAFSerialNo("456");
		tFIDataDealErrSchema2.setBussDate("1999-08-09");
		tFIDataDealErrSchema2.setErrInfo("This is a Error");
		tFIDataDealErrSchema2.setErrDealState("B20");
		tFIDataDealErrSchema2.setDealDescription("C30");
		tFIDataDealErrSchema2.setMakeDate(PubFun.getCurrentDate());
		tFIDataDealErrSchema2.setMakeTime(PubFun.getCurrentTime());
		tFIDataDealErrSet.add(tFIDataDealErrSchema1);
		tFIDataDealErrSet.add(tFIDataDealErrSchema2);
		DataDealErr tDataDealErr = new DataDealErr();
		VData tVData = new VData();
		tVData.addElement(tFIDataDealErrSet);
		ttDataDealErr=tDataDealErr.submitData(tVData);			
	}
}
