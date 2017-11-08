/*
* <p>ClassName: LCPolF1PUI </p>
* <p>Description: LCPolF1PUI类文件 </p>
* <p>Copyright: Copyright (c) 2002</p>
* <p>Company: sinosoft </p>
* @Database: LIS
* @CreateDate：2002-11-04
 */
package com.sinosoft.lis.otof;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sinosoft.lis.db.OFinaLogDB;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

public class OtoFUI {
private static Logger logger = Logger.getLogger(OtoFUI.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors=new CErrors();

    private VData mResult = new VData();
	private String mStartDate = "";
	private String mEndDate = "";
	private String mToday = "";
	private int mTime = 0;
	private String DateFlag = "0";
	private String mInputDate = ""; // 界面传入的记帐日期
	private String cManageCom = ""; // 提取管理机构

    /** 全局数据 */
    private GlobalInput mGlobalInput =new GlobalInput() ;

    public OtoFUI ()
    {
    }

    /**
     传输数据的公共方法
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        logger.debug("--- OtoFUI Begin ---");
        try 
        {
            // 得到外部传入的数据，将数据备份到本类中
            if(!getInputData(cInputData))
                return false;
            // 进行业务处理
            if(!dealData())
                return false;
            //进行财务接口的优化，以三级机构为单位进行凭证提取，减少内存压力
            String mComSql ="";
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            Date bTime = formatter.parse(PubFun.getCurrentTime());

            if(cManageCom.trim().length()<=8)
            {
	        	mComSql = "select distinct trim(comcode) from ldcom where comcode like concat('"+"?f1?"+"','%') "
	        				   +"and char_length(trim(comcode))=8 order by trim(comcode)";
            }
            else
            {
	        	mComSql = "select distinct trim(comcode) from ldcom where comcode like concat('"+"?f2?"+"','%') "
				   +"order by trim(comcode)";
            }
            SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
            sqlbv1.sql(mComSql);
            sqlbv1.put("f1", cManageCom);
            sqlbv1.put("f2", cManageCom);
        	SSRS tSsrs = new SSRS();
        	ExeSQL tExeSQL = new ExeSQL();
        	tSsrs = tExeSQL.execSQL(sqlbv1);
        	for (int j=1;j<=tSsrs.MaxRow;j++)
        	{        	
        		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        		FDate fd = new FDate();
        		Date mDate = fd.getDate(PubFun.getCurrentDate());
        		Date bDate = fd.getDate(mStartDate);
        		OFinaLogDB tOFinaLogDB = new OFinaLogDB();
        		String tRecordNo="";  
        		tRecordNo=tSsrs.GetText(j, 1)+df.format(bDate)+df.format(mDate)+String.valueOf(mTime);
        		tOFinaLogDB.setRecordNo(tRecordNo);
        		tOFinaLogDB.setVoucherType(String.valueOf(mTime));
        		tOFinaLogDB.setState("1");
        		logger.debug("记录号为------"+tRecordNo);
        		//如果已经对该机构进行了凭证提取的处理，无论提取是否成功，都不在重复进行处理
        		if(tOFinaLogDB.getCount()>0)
        			continue;
    		   TransferData tTransferData = new TransferData();
    	   	   tTransferData.setNameAndValue("mStartDate",mStartDate);
    	   	   tTransferData.setNameAndValue("mEndDate",mEndDate);
    	   	   tTransferData.setNameAndValue("itemp",mTime);
    	   	   tTransferData.setNameAndValue("DateFlag",DateFlag);
    	   	   tTransferData.setNameAndValue("mInputDate",mInputDate);
    	   	   tTransferData.setNameAndValue("cManageCom",tSsrs.GetText(j, 1));
    	   	   VData mInputData = new VData();
        	   mInputData.addElement(mGlobalInput);
        	   mInputData.addElement(tTransferData);

                // 准备传往后台的数据
                OtoFBL tOtoFBL = new OtoFBL();
                if(!tOtoFBL.submitData(mInputData, cOperate))
                {
                   mErrors.copyAllErrors(tOtoFBL.mErrors);
                   if("0".equals(DateFlag))//手工提取过程中有问题，直接返回错误
                   return false;
                   else
                	   continue;
                }
        	}
        	Date eTime = formatter.parse(PubFun.getCurrentTime());
        	logger.debug("提取需要时间为-----"+(eTime.getTime() - bTime.getTime()));
            
        } catch (Exception ex)
        {
            ex.printStackTrace();
            buildError("submit", "发生异常");
            return false;
        }        
        return true;
    }

    public static void main(String[] args) throws Exception
    {
        OtoFUI tOtoFUI = new OtoFUI();
        VData vData = new VData();
        String bdate = "2005-06-06";
        String edate = "2005-06-06";
        GlobalInput tG = new GlobalInput();
       tG.Operator = "001";
        tG.ManageCom = "861301";

        vData.addElement(tG);
        vData.addElement(bdate);
        vData.addElement(edate);
        Integer itemp = new Integer(7) ;
        vData.addElement(itemp);
        vData.addElement("0");

        
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		FDate fd = new FDate();
		String mStartDate="2009-03-04";
		logger.debug("fd.getDate(mStartDate)"+fd.getDate(mStartDate));
    	logger.debug(df.format(fd.getDate(mStartDate)));
		logger.debug("fd.getDate(mStartDate)"+fd.getDate(PubFun.getCurrentDate()));
    	logger.debug(df.format(fd.getDate(PubFun.getCurrentDate())));
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date bTime = formatter.parse(PubFun.getCurrentTime());
        
        if( tOtoFUI.submitData(vData, "PRINT") )
        {
        	
        }
       	Date eTime = formatter.parse(PubFun.getCurrentTime());
    	logger.debug("提取需要时间为-----"+(eTime.getTime() - bTime.getTime()));

    }

    /**
     * 根据前面的输入数据，进行UI逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData()
    {
        return true;
    }

    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData)
    {
        //全局变量
    	mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput", 0));
        if( mGlobalInput == null )
        {
            buildError("getInputData", "没有得到足够的信息！");
            return false;
        }
		TransferData mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
		mStartDate = (String)mTransferData.getValueByName("mStartDate");
		cManageCom = (String)mTransferData.getValueByName("cManageCom"); // 提取管理机构
		mEndDate = (String)mTransferData.getValueByName("mEndDate");
		Integer itemp = (Integer)mTransferData.getValueByName("itemp");
		mTime = itemp.intValue();
		DateFlag = (String)mTransferData.getValueByName("DateFlag");
		mInputDate = (String)mTransferData.getValueByName("mInputDate"); // 记账日期


        return true;
    }

    private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError( );

        cError.moduleName = "OtoFUI";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }
}
