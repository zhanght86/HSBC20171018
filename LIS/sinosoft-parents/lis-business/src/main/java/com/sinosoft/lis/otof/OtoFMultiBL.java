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

import com.sinosoft.lis.db.LAStatSegmentDB;
import com.sinosoft.lis.db.OFinaLogDB;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.reagent.LATaskAimCal;
import com.sinosoft.service.CovBase;
import com.sinosoft.utility.*;

public class OtoFMultiBL extends CovBase{
private static Logger logger = Logger.getLogger(OtoFMultiBL.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors=new CErrors();

    private VData mResult = new VData();

    private VData mInputData=null;

    /** 全局数据 */
    private GlobalInput mGlobalInput =new GlobalInput() ;

    public OtoFMultiBL ()
    {
    }

    public void setObject(Object tObject)
    {
    	mInputData = (VData)tObject;
    }

    public void run()
    {
    	try
    	{
		OtoFUI tOtoFUI = new OtoFUI();
		if (!tOtoFUI.submitData(mInputData, "Buss")) {
			CErrors cerr = tOtoFUI.mErrors;
		} else {
		}
    	}
    	catch(Exception ex){
    		logger.debug(ex.toString());
    		
    	}
    	finally
    	{
          this.close();
    	}
    }



    public static void main(String[] args) throws Exception
    {
//        OtoFUI tOtoFUI = new OtoFUI();
//        VData vData = new VData();
//        String bdate = "2005-06-06";
//        String edate = "2005-06-06";
//        GlobalInput tG = new GlobalInput();
//        tG.Operator = "001";
//        tG.ManageCom = "861301";
//
//        vData.addElement(tG);
//        vData.addElement(bdate);
//        vData.addElement(edate);
//        Integer itemp = new Integer(7) ;
//        vData.addElement(itemp);
//        vData.addElement("0");
//
//        if( tOtoFUI.submitData(vData, "PRINT") )
//        {
//        }
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		FDate fd = new FDate();
		String mStartDate="2009-03-04";
		logger.debug("fd.getDate(mStartDate)"+fd.getDate(mStartDate));
    	logger.debug(df.format(fd.getDate(mStartDate)));
		logger.debug("fd.getDate(mStartDate)"+fd.getDate(PubFun.getCurrentDate()));
    	logger.debug(df.format(fd.getDate(PubFun.getCurrentDate())));
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date bTime = formatter.parse(PubFun.getCurrentTime());
       	Date eTime = formatter.parse(PubFun.getCurrentTime());
    	logger.debug("提取需要时间为-----"+(eTime.getTime() - bTime.getTime()));

    }
}
