package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;
import com.sinosoft.lis.cbcheck.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.taskservice.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.agentprint.*;
import com.sinosoft.utility.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.sinosoft.lis.otof.*;

import java.sql.*;

import java.util.*;

/**
 * <p>Title: lis</p>
 * <p>Description: 航意险财务凭证自动提取</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: sinosoft</p>
 * @author tongmeng
 * @version 1.0
 */


public class AutoOtoFAirPol extends TaskThread{
private static Logger logger = Logger.getLogger(AutoOtoFAirPol.class);

  public AutoOtoFAirPol() {
  }

  public CErrors mErrors = new CErrors();
  private String mDate=null;
  private LDComSet mLDComSet = new LDComSet();

  public boolean dealMain()
  {
    /*业务处理逻辑*/
    logger.debug("开始航意险财务凭证自动提取...");
    OtoFAirPolBL tOtoFAirPolBL = new OtoFAirPolBL();
    VData vData = new VData();
    GlobalInput tG = new GlobalInput();
    String tCurrentDate = PubFun.getCurrentDate();
	tG = new GlobalInput();
	vData = new VData();
	tG.Operator="001";
	tG.ManageCom = "86";
	String bdate=PubFun.calDate(tCurrentDate,-1,"D",tCurrentDate);
	String edate=tCurrentDate;
	Integer itemp = new Integer(13);
	TransferData tTransferData = new TransferData();
	vData.addElement(tG);
	tTransferData.setNameAndValue("bdate", bdate);
	tTransferData.setNameAndValue("edate", edate);
	tTransferData.setNameAndValue("itemp", itemp);
	tTransferData.setNameAndValue("DateFlag", "1");//自动提取
	tTransferData.setNameAndValue("accountdate", "");//记帐日期
	tTransferData.setNameAndValue("managecom", tG.ManageCom);//管理机构
	vData.addElement(tTransferData);
  
	if(!tOtoFAirPolBL.submitData(vData, "AirPol"))
	{
		logger.debug("发生错误在提取："+tG.ManageCom+"的航意险财务凭证.");
	}
      logger.debug("结束提取："+tG.ManageCom +" "+bdate + "至"+edate+"的航意险财务凭证..");
    logger.debug("结束航意险财务凭证自动提取...");
    return true;
  }
  public static void main(String[] args)
  {
    AutoOtoFAirPol tAutoOtoFAirPol = new AutoOtoFAirPol();
    tAutoOtoFAirPol.dealMain();
  }
}
