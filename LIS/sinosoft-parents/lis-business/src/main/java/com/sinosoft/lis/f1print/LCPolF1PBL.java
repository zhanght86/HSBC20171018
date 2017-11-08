/*
 * <p>ClassName: LCPolF1PBL </p>
 * <p>Description: LCPolF1BL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-11-04
 */
package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Vector;
import java.util.Date;
import java.util.List;
import java.sql.*;
import java.text.*;
import java.util.*;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

import oracle.sql.*;
import oracle.jdbc.*;
import oracle.sql.BLOB;
import oracle.jdbc.OracleResultSet;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.tb.*;

public class LCPolF1PBL {
private static Logger logger = Logger.getLogger(LCPolF1PBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors=new CErrors();

	private VData mResult = new VData();

	//业务处理相关变量
 	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCPolSet mLCPolSet = new LCPolSet();
	private LCContSchema mLCContSchema = new LCContSchema();
	private String mPrtNo = "";
	private XMLDatasets mXMLDatasets = new XMLDatasets();
	private String mOperate="";
	private String mContNo = "";

  /*
   * 对于同时传入主险和附加险保单号的情况，如果它们是同一个印刷号的，
   * 将被存在同一个保单数据块中。所以将打印过的保单号存放在这个Vector中。
   */
  private Vector m_vPolNo = new Vector();

	public LCPolF1PBL ()
	{
    mXMLDatasets.createDocument();
	}

 	/**
		传输数据的公共方法
	*/
	public boolean submitData(VData cInputData, String cOperate)
	{
    try {
      mOperate = cOperate;

      if( !mOperate.equals("PRINT")
          && !mOperate.equals("REPRINT")
          && !mOperate.equals("PRINTEX")) {
        buildError("submitData", "不支持的操作字符串");
        return false;
      }

      // 得到外部传入的数据，将数据备份到本类中
      if( !getInputData(cInputData) ) {
        return false;
      }

      // 打印保单的操作
      if( mOperate.equals("PRINT") ) {
        // 准备所有要打印的数据
        if( !getPrintData() ) {
          return false;
        }
      }

      // 补打保单的操作
      if( mOperate.equals("REPRINT") ) {
        if( !getPrintData() ) {
          return false;
        }
      }

      // 前台保单打印的操作(柜面打印)
      if( mOperate.equals("PRINTEX") ) {
        // 准备所有要打印的数据
        if( !getPrintData() ) {
          return false;
        }

        // 因为数据格式不同，进行一下转换。
        Document doc = mXMLDatasets.getDocument();
        Element ele = doc.getRootElement().getChild("DATASET");
        doc = new Document(ele);
        countPrint(doc);
        mResult.clear();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLOutputter outputter = new XMLOutputter("  ", true,"GBK");
        outputter.output(doc, baos);

        mResult.add(new ByteArrayInputStream(baos.toByteArray()));
      }

      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
      buildError("submit", ex.getMessage());
      return false;
    }
	}

	public static void main(String[] args)
	{
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理
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
	    if( mOperate.equals("PRINT") ) {  // 打印保单
	      mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
	      mLCPolSet.set((LCPolSet)cInputData.getObjectByObjectName("LCPolSet",0));
	
	    } else if( mOperate.equals("REPRINT") ) {  // 补打保单
	      mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
	      mLCPolSet.set((LCPolSet)cInputData.getObjectByObjectName("LCPolSet",0));
	
	    } else if( mOperate.equals("PRINTEX") ) {
	      mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
	      mLCPolSet.set((LCPolSet)cInputData.getObjectByObjectName("LCPolSet",0));
	    }
	    if(mLCPolSet.size()<=0){
	    	buildError("getInputData", "没有传入有效的保单信息");
	    	return false;
	    }
	    LCPolDB tLCPolDB = new LCPolDB();
	    tLCPolDB.setPolNo(mLCPolSet.get(1).getPolNo());
	    if(!tLCPolDB.getInfo()){
	    	buildError("getInputData", "需要打印的保单不存在");
	    	return false;
	    }
	    mContNo = tLCPolDB.getContNo();
	    LCContDB tLCContDB = new LCContDB();
	    tLCContDB.setContNo(mContNo);
	    if(!tLCContDB.getInfo()){
	    	buildError("getInputData", "需要打印的合同不存在");
	    	return false;
	    }
	    mLCContSchema = tLCContDB.getSchema();
		return true;
	}

	public VData getResult()
	{
		return this.mResult;
	}

  private void buildError(String szFunc, String szErrMsg)
  {
    CError cError = new CError( );

    cError.moduleName = "LCPolF1PBL";
    cError.functionName = szFunc;
    cError.errorMessage = szErrMsg;
    this.mErrors.addOneError(cError);
  }

  private boolean getPrintData()
  {
    LCPolSet tLCPolSet = new LCPolSet();
    LCPolSchema tLCPolSchema = null;

    m_vPolNo.clear();  // Clear contents of m_vPolNo

    for(int nIndex = 0; nIndex < mLCPolSet.size(); nIndex++) {
      tLCPolSchema = mLCPolSet.get(nIndex + 1);

      // Have been printed, continue ...
      if( m_vPolNo.contains(tLCPolSchema.getPolNo()) ) {
        continue;
      }

      if( !getPolSet(tLCPolSchema, tLCPolSet) ) {
        return false;
      }

      // 校验并记录下本次打过的所有保单号
      for(int n = 0; n < tLCPolSet.size(); n++) {
        LCPolSchema tempLCPolSchema = tLCPolSet.get(n+1);
        String strPolNo = tempLCPolSchema.getPolNo();

        if( !tempLCPolSchema.getAppFlag().equals("1") ) {
          buildError("getPolSet", strPolNo + "号投保单还没有签单");
          return false;
        }

        if( mLCContSchema.getPrintCount() == 1 ) { // 为兼容保单补发，保单补发的值置为 10
          buildError("getPolSet", mLCContSchema.getContNo() + "号保单已经打印过了");
          return false;
        }

//        LCRnewStateLogDB tLCRnewStateLogDB = new LCRnewStateLogDB();
//        tLCRnewStateLogDB.setPolNo(strPolNo);
//        LCRnewStateLogSet tLCRnewStateLogSet = tLCRnewStateLogDB.query();
//        if(tLCRnewStateLogSet!=null && tLCRnewStateLogSet.size()>=1)
//        {
//            for(int i=1; i<=tLCRnewStateLogSet.size(); i++)
//            {
//                if(tLCRnewStateLogSet.get(i).getState()!=null &&
//                     tLCRnewStateLogSet.get(i).getState().equals("5"))
//                {
//                    tLCPolSet.remove(tempLCPolSchema);
//                    n--;
//                }
//            }
//        }

        m_vPolNo.add(strPolNo);
      }

      boolean bFlag = false;

      // 如果是重打
      if( mLCContSchema.getPrintCount() == -1 ) {
        bFlag = getPolDataSetEx(tLCPolSet);
      } else {  // 如果是正常打印
        bFlag = getPolDataSet(tLCPolSet);
      }

      if( !bFlag ) {
        return false;
      }

    } // end of "for(int nIndex = 0; nIndex < mLCPolSet.size(); nIndex++)"

    LCContSet tempLCContSet = new LCContSet();
//yanglh
    for(int mIndex = 0; mIndex < m_vPolNo.size(); mIndex++) {
      if(mOperate.equals("PRINT") || mOperate.equals("REPRINT"))
         mLCContSchema.setPrintCount(1);
      tempLCContSet.add(mLCContSchema);
    }

    // 准备要保存的数据
    mResult.add(tempLCContSet);
    mResult.add(mXMLDatasets);
    mResult.add(mGlobalInput);

    LCPolF1PBLS  tLCPolF1PBLS = new LCPolF1PBLS();
    tLCPolF1PBLS.submitData(mResult, mOperate);
    if(tLCPolF1PBLS.mErrors.needDealError())
    {
      mErrors.copyAllErrors(tLCPolF1PBLS.mErrors);
      buildError("saveData", "提交数据库出错！");
      return false;
    }
    /**
	if (mOperate.equals("PRINT") || mOperate.equals("PRINTEX")) {
	    VData mInputData = new VData();
	    MMap map = new MMap();
	    mLCContSchema.setPrintCount(1);
	    map.put(mLCContSchema,"UPDATE");
	    
		String strCurDate = PubFun.getCurrentDate();
		String strCurTime = PubFun.getCurrentTime();
		
		LCContPrintSchema tLCContPrintSchema = new LCContPrintSchema();
		tLCContPrintSchema.setContNo(mLCPolSet.get(1).getPolNo());
		tLCContPrintSchema.setPrtTimes(1);
		tLCContPrintSchema.setOperator(this.mGlobalInput.Operator);
		tLCContPrintSchema.setMakeDate(strCurDate);
		tLCContPrintSchema.setMakeTime(strCurTime);
		tLCContPrintSchema.setModifyDate(strCurDate);
		tLCContPrintSchema.setModifyTime(strCurTime);
		tLCContPrintSchema.setContInfo("empty_blob()");
		tLCContPrintSchema.setContType("1");
		
	    map.put(tLCContPrintSchema,"DELETE&INSERT");
	    mInputData.add(map);
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			this.buildError("submitData", "数据提交失败!");
			return false;
		}
	} else if (mOperate.equals("REPRINT")) {

	}     
	*/
    mResult.clear();

    logger.debug("add inputstream to mResult");
    mResult.add(mXMLDatasets.getInputStream());
		return true;
  }

  private void getCashValue(XMLDataset xmlDataset, LCPolSet aLCPolSet)
      throws Exception
  {
    XMLDataList xmlDataList = new XMLDataList();

    xmlDataList.setDataObjectID("CashValue");

    xmlDataList.addColHead("Age");
    xmlDataList.addColHead("Cash");
    xmlDataList.buildColHead();
    Dictionary dict = new Hashtable();
    for (int i=1;i<=aLCPolSet.size();i++)
    {
        LCPolSchema tLCPolSchema = aLCPolSet.get(i);
        // 得到现金价值的算法描述
        LMCalModeDB tLMCalModeDB = new LMCalModeDB();
        tLMCalModeDB.setRiskCode(tLCPolSchema.getRiskCode());
        tLMCalModeDB.setType("X");

        LMCalModeSet tLMCalModeSet = tLMCalModeDB.query();

// 解析得到的SQL语句
        String tstrSQL = "";

        if( tLMCalModeSet.size() == 1 &&
            tLMCalModeSet.get(1).getCalSQL()!=null &&
            tLMCalModeSet.get(1).getCalSQL().length()!=0) {
            tstrSQL = tLMCalModeSet.get(1).getCalSQL();
        }
        else
            continue;

        Calculator calculator = new Calculator();
        // 设置基本的计算参数
        calculator.addBasicFactor("InsuredSex", tLCPolSchema.getInsuredSex());
        calculator.addBasicFactor("InsuredAppAge", String.valueOf(tLCPolSchema.getInsuredAppAge()));
        calculator.addBasicFactor("PayIntv", String.valueOf(tLCPolSchema.getPayIntv()));
        calculator.addBasicFactor("PayEndYear", String.valueOf(tLCPolSchema.getPayEndYear()));
        calculator.addBasicFactor("PayEndYearFlag", String.valueOf(tLCPolSchema.getPayEndYearFlag()));
        calculator.addBasicFactor("PayYears", String.valueOf(tLCPolSchema.getPayYears()));
        calculator.addBasicFactor("InsuYear", String.valueOf(tLCPolSchema.getInsuYear()));
        calculator.addBasicFactor("Prem", String.valueOf(tLCPolSchema.getPrem()));
        calculator.addBasicFactor("Amnt", String.valueOf(tLCPolSchema.getAmnt()));
        calculator.addBasicFactor("FloatRate", String.valueOf(tLCPolSchema.getFloatRate()));
//add by yt 2004-3-10
        calculator.addBasicFactor("InsuYearFlag", String.valueOf(tLCPolSchema.getInsuYearFlag()));
        calculator.addBasicFactor("GetYear", String.valueOf(tLCPolSchema.getGetYear()));
        calculator.addBasicFactor("GetYearFlag", String.valueOf(tLCPolSchema.getGetYearFlag()));
        calculator.addBasicFactor("CValiDate", String.valueOf(tLCPolSchema.getCValiDate()));
        calculator.addBasicFactor("SignDate", String.valueOf(tLCPolSchema.getSignDate()));
        calculator.setCalCode(tLMCalModeSet.get(1).getCalCode());
        tstrSQL = calculator.getCalSQL();
        logger.debug(tstrSQL);

        Connection conn = DBConnPool.getConnection();
        if( null == conn ) {
            throw new Exception("连接数据库失败");
        }

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(tstrSQL);

            while( rs.next() ) {
                String tAge=rs.getString(1).trim();
                String tCash=format(rs.getDouble(2));
                String aCash=(String)dict.get(tAge);
                if (aCash==null){
                    dict.put(tAge,tCash);
                }
                else{
                    tCash=String.valueOf(Double.parseDouble(aCash)+Double.parseDouble(tCash));
                    dict.put(tAge,tCash);
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            if( null != rs )  rs.close();
            stmt.close();
            try{ conn.close(); } catch(Exception e) {};
            throw ex;
        }
    }
    // 这个险种不需要取现金价值的数据
    if (dict.isEmpty()) {
        xmlDataset.addDataObject(new XMLDataTag("CashValueFlag", "N"));
    } else {
        xmlDataset.addDataObject(new XMLDataTag("CashValueFlag", "Y"));
    }
        int nCount = 0;
        Enumeration AE=dict.keys();
        while(AE.hasMoreElements()){
            String tAge=(String)AE.nextElement();
            String tCash=(String)dict.get(tAge);
            tCash =new DecimalFormat("0.00").format(new Double(tCash) );
            xmlDataList.setColValue("Age", tAge);
            xmlDataList.setColValue("Cash", tCash);
            xmlDataList.insertRow(0);
            nCount ++;
        }
        xmlDataset.addDataObject(new XMLDataTag("CashValueCount", nCount));
        xmlDataset.addDataObject(xmlDataList);
  }

  private void getScanPic(XMLDataset xmlDataset, LCPolSchema aLCPolSchema)
      throws Exception
  {
    XMLDataList xmlDataList = new XMLDataList();

    xmlDataList.setDataObjectID("PicFile");

    xmlDataList.addColHead("FileUrl");
    xmlDataList.addColHead("PageIndex");
    xmlDataList.buildColHead();

    VData vData = new VData();
    vData.add(aLCPolSchema.getPrtNo());

    EasyScanQueryBL tEasyScanQueryBL = new EasyScanQueryBL();

    if( !tEasyScanQueryBL.submitData(vData, "QUERY||3") ) {
      logger.debug(tEasyScanQueryBL.mErrors.getFirstError());
    } else {
      vData.clear();
      vData = tEasyScanQueryBL.getResult();

      String strFileName = "";
      for(int nIndex = 0; nIndex < vData.size(); nIndex ++) {

        if( nIndex == 3 ) {
          continue; // 去掉第四页扫描件
        }

        strFileName = (String)vData.get(nIndex);
//      strFileName = strFileName.substring(strFileName.lastIndexOf("/") + 1);
        strFileName = strFileName.substring(0, strFileName.lastIndexOf(".")) + ".tif";
//      strFileName += ".tif";

        xmlDataList.setColValue("FileUrl", strFileName);
        xmlDataList.setColValue("PageIndex", nIndex);
        xmlDataList.insertRow(0);
      }
    }
    xmlDataset.addDataObject(xmlDataList);
  }
  
/**
  private void getClauseFile(XMLDataset xmlDataset, LCPolSchema aLCPolSchema)
      throws Exception
  {
    XMLDataList xmlDataList = new XMLDataList("ClauseFile");

    xmlDataList.addColHead("FileUrl");
    xmlDataList.buildColHead();

    xmlDataset.addDataObject(xmlDataList);
  }
*/
  /**
   * 取得责任
   * @param xmlDataset
   * @param aLCPolSchema
   * @throws Exception
   */
  private void getDutyList(XMLDataset xmlDataset, LCPolSchema aLCPolSchema)
      throws Exception
  {
    XMLDataList xmlDataList = new XMLDataList();

    xmlDataList.setDataObjectID("DutyList");

    xmlDataList.addColHead("COL1");
    xmlDataList.addColHead("COL2");
    xmlDataList.addColHead("COL3");
    xmlDataList.addColHead("COL4");
    xmlDataList.buildColHead();

    String strSQL = "select lmdutyget.getdutyname, standmoney from lcget, lmdutyget"
                + " where lmdutyget.getdutycode = lcget.getdutycode"
                + " and polno = '" + "?polno?" + "'";
    SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
    sqlbv1.sql(strSQL);
    sqlbv1.put("polno", aLCPolSchema.getPolNo());
    ExeSQL exeSQL = new ExeSQL();
    SSRS rs = exeSQL.execSQL(sqlbv1);
    for(int n = 0; n < (rs.getMaxRow() + 1) / 2; n++) {
        xmlDataList.setColValue("COL1", rs.GetText(n*2+1, 1)); 
        xmlDataList.setColValue("COL2", rs.GetText(n*2+1, 2));

        if( 2*n + 2 <= rs.getMaxRow() ) {
          xmlDataList.setColValue("COL3", rs.GetText(n*2+2, 1));
          xmlDataList.setColValue("COL4", rs.GetText(n*2+2, 2));
        }
        xmlDataList.insertRow(0);
    }
    xmlDataset.addDataObject(xmlDataList);
  }

  /**
   * 取得投保人信息
   * @param xmlDataset
   * @param aLCPolSchema
   * @throws Exception
   */
  private void getAppntAndInsuredList(XMLDataset xmlDataset, LCPolSchema aLCPolSchema)
      throws Exception
  {
    XMLDataList xmlDataList = new XMLDataList();

    xmlDataList.setDataObjectID("insured");

    xmlDataList.addColHead("COL1");
    xmlDataList.addColHead("COL2");
    xmlDataList.addColHead("COL3");
    xmlDataList.addColHead("COL4");
    xmlDataList.addColHead("COL5");
    xmlDataList.addColHead("COL6");
    xmlDataList.buildColHead();
    String strSQL = "";
    if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
    	  strSQL = "select a.name,(select codename from ldcode where codetype='sex' and code=a.sex),a.idno,(case when (select b.mobile from lcaddress b where  b.customerno=a.insuredno and b.addressno=a.addressno) is not null then (select b.mobile from lcaddress b where  b.customerno=a.insuredno and b.addressno=a.addressno)  else (select b.phone from lcaddress b where  b.customerno=a.insuredno and rownum=1) end)," //防止查询结果为空
//    		     + "nvl((select b.postaladdress from lcaddress b where  b.customerno=a.insuredno and b.addressno=a.addressno),(select b.postaladdress from lcaddress b where  b.customerno=a.insuredno and rownum=1)),"
    		     + "(select b.postaladdress from lcaddress b where  b.customerno=a.insuredno and b.addressno=a.addressno),"
    		     +" (case when (select b.zipcode from lcaddress b where  b.customerno=a.insuredno and b.addressno=a.addressno) is not null then (select b.zipcode from lcaddress b where  b.customerno=a.insuredno and b.addressno=a.addressno)  else (select b.zipcode from lcaddress b where  b.customerno=a.insuredno and rownum=1) end) from lcinsured a "
    			 +" where a.contno='"+"?mContNo?"+"' ";
    }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
    	  strSQL = "select a.name,(select codename from ldcode where codetype='sex' and code=a.sex),a.idno,(case when (select b.mobile from lcaddress b where  b.customerno=a.insuredno and b.addressno=a.addressno) is not null then (select b.mobile from lcaddress b where  b.customerno=a.insuredno and b.addressno=a.addressno)  else (select b.phone from lcaddress b where  b.customerno=a.insuredno limit 0,1) end)," //防止查询结果为空
//    		     + "nvl((select b.postaladdress from lcaddress b where  b.customerno=a.insuredno and b.addressno=a.addressno),(select b.postaladdress from lcaddress b where  b.customerno=a.insuredno and rownum=1)),"
    		     + "(select b.postaladdress from lcaddress b where  b.customerno=a.insuredno and b.addressno=a.addressno),"
    		     +" (case when (select b.zipcode from lcaddress b where  b.customerno=a.insuredno and b.addressno=a.addressno) is not null then (select b.zipcode from lcaddress b where  b.customerno=a.insuredno and b.addressno=a.addressno)  else (select b.zipcode from lcaddress b where  b.customerno=a.insuredno limit 0,1) end) from lcinsured a "
    			 +" where a.contno='"+"?mContNo?"+"' ";
    }
    SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
    sqlbv2.sql(strSQL);
    sqlbv2.put("mContNo", mContNo);
    ExeSQL exeSQL = new ExeSQL();
    SSRS rs = exeSQL.execSQL(sqlbv2);

    xmlDataList.setColValue("COL1", rs.GetText(1,1));
    xmlDataList.setColValue("COL2", rs.GetText(1,2));
    xmlDataList.setColValue("COL3", rs.GetText(1,3));
    xmlDataList.setColValue("COL4", rs.GetText(1,4));
    xmlDataList.setColValue("COL5", rs.GetText(1,5));
    xmlDataList.setColValue("COL6", rs.GetText(1,6));
    xmlDataList.insertRow(0);
    xmlDataset.addDataObject(xmlDataList);
    
    xmlDataList.setDataObjectID("AppntList");

    xmlDataList.addColHead("COL1");
    xmlDataList.addColHead("COL2");
    xmlDataList.addColHead("COL3");
    xmlDataList.addColHead("COL4");
    xmlDataList.addColHead("COL5");
    xmlDataList.addColHead("COL6");
    xmlDataList.buildColHead();

    if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
    	strSQL = "select a.appntname,(select codename from ldcode where codetype='sex' and code=a.appntsex),a.idno,(case when (select b.mobile from lcaddress b where  b.customerno=a.appntno and b.addressno=a.addressno) is not null then (select b.mobile from lcaddress b where  b.customerno=a.appntno and b.addressno=a.addressno)  else (select b.phone from lcaddress b where  b.customerno=a.appntno and rownum=1) end)," //防止查询结果为空
    		     + "(case when (select b.postaladdress from lcaddress b where  b.customerno=a.appntno and b.addressno=a.addressno) is not null then (select b.postaladdress from lcaddress b where  b.customerno=a.appntno and b.addressno=a.addressno)  else (select b.postaladdress from lcaddress b where  b.customerno=a.appntno and rownum=1) end),"
    		     +" (case when (select b.zipcode from lcaddress b where  b.customerno=a.appntno and b.addressno=a.addressno) is not null then (select b.zipcode from lcaddress b where  b.customerno=a.appntno and b.addressno=a.addressno)  else (select b.zipcode from lcaddress b where  b.customerno=a.appntno and rownum=1) end) from lcappnt a "
    			 +" where a.contno='"+"?mContNo?"+"' ";
    }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
    	strSQL = "select a.appntname,(select codename from ldcode where codetype='sex' and code=a.appntsex),a.idno,(case when (select b.mobile from lcaddress b where  b.customerno=a.appntno and b.addressno=a.addressno) is not null then (select b.mobile from lcaddress b where  b.customerno=a.appntno and b.addressno=a.addressno)  else (select b.phone from lcaddress b where  b.customerno=a.appntno limit 0,1) end)," //防止查询结果为空
    		     + "(case when (select b.postaladdress from lcaddress b where  b.customerno=a.appntno and b.addressno=a.addressno) is not null then (select b.postaladdress from lcaddress b where  b.customerno=a.appntno and b.addressno=a.addressno)  else (select b.postaladdress from lcaddress b where  b.customerno=a.appntno limit 0,1) end),"
    		     +" (case when (select b.zipcode from lcaddress b where  b.customerno=a.appntno and b.addressno=a.addressno) is not null then (select b.zipcode from lcaddress b where  b.customerno=a.appntno and b.addressno=a.addressno)  else (select b.zipcode from lcaddress b where  b.customerno=a.appntno limit 0,1) end) from lcappnt a "
    			 +" where a.contno='"+"?mContNo?"+"' ";
    }
     SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
     sqlbv3.sql(strSQL);
     sqlbv3.put("mContNo", mContNo);
    rs = exeSQL.execSQL(sqlbv3);

    xmlDataList.setColValue("COL1", rs.GetText(1,1));
    xmlDataList.setColValue("COL2", rs.GetText(1,2));
    xmlDataList.setColValue("COL3", rs.GetText(1,3));
    xmlDataList.setColValue("COL4", rs.GetText(1,4));
    xmlDataList.setColValue("COL5", rs.GetText(1,5));
    xmlDataList.setColValue("COL6", rs.GetText(1,6));
    xmlDataList.insertRow(0);
    xmlDataset.addDataObject(xmlDataList);
  } 
  
  
  /**
   * 正常打印的取数流程
   * @param aLCPolSet 主险附加险保单数据。其中，第一个元素是主险保单，其它元素是附加险保单。
   * @return
   */
  private boolean getPolDataSet(LCPolSet aLCPolSet)
  {
    ProposalQueryBL proposalQueryBL = new ProposalQueryBL();

    VData vData = new VData();
    vData.addElement(aLCPolSet.get(1));

    if( !proposalQueryBL.submitData(vData, "QUERY||DETAIL") ) {
      if( proposalQueryBL.mErrors.needDealError() ) {
        mErrors.copyAllErrors(proposalQueryBL.mErrors);
        return false;
      } else {
        buildError("getPrintData", "在调用ProposalQueryBL时发生错误，但是没有提供详细的错误信息!");
        return false;
      }
    }

    // 得到查询结果
    vData = proposalQueryBL.getResult();

    XMLDataset xmlDataset = mXMLDatasets.createDataset();
//    LCAppntIndSchema tLCAppntIndSchema = (LCAppntIndSchema)vData.getObjectByObjectName("LCAppntIndSchema", 0);
//    xmlDataset.addDataObject(new XMLDataTag("LCPol.IDNo", tLCAppntIndSchema.getIDNo()));
//    xmlDataset.addSchema(tLCAppntIndSchema);

    LCPolSchema tLCPolSchema = (LCPolSchema)vData.getObjectByObjectName("LCPolSchema", 0);

    // 获取打印的模板名
    LMRiskFormDB tLMRiskFormDB = new LMRiskFormDB();

    tLMRiskFormDB.setRiskCode(tLCPolSchema.getRiskCode());
    tLMRiskFormDB.setFormType("PP");

    if( !tLMRiskFormDB.getInfo() ) {
      buildError("getPrintData", "没有该险种保单的打印模板信息。险种" + tLCPolSchema.getRiskCode());
      return false;
    }

    // 个单模板为OtherSign的第一个字符
    xmlDataset.setTemplate(tLMRiskFormDB.getFormName());

    // 签单机构为管理机构的前四位
    tLCPolSchema.setSignCom(tLCPolSchema.getManageCom().substring(0, 4));

    // 查询年金型和分红型的标志
    LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();

    tLMRiskAppDB.setRiskCode(tLCPolSchema.getRiskCode());
    tLMRiskAppDB.getInfo();

    if( tLMRiskAppDB.getRiskType2() == null || !tLMRiskAppDB.getRiskType2().equals("Y") ) {
      tLCPolSchema.setGetYear(-100);
      tLCPolSchema.setGetYearFlag(null);
    }

    if( tLMRiskAppDB.getBonusFlag() == null || !tLMRiskAppDB.getBonusFlag().equals("Y") ) {
      tLCPolSchema.setBonusGetMode(null);
    }

    // 对趸交的保单，将交至日期设为保单的生效日期
    if( tLCPolSchema.getPayIntv() == 0 ) {
      tLCPolSchema.setPaytoDate( tLCPolSchema.getCValiDate() );
    } else {
      tLCPolSchema.setPaytoDate(prevDay(tLCPolSchema.getPaytoDate()));
    }

    // 设置保单的签单日期 - modify by wentao 客户要求该日期还原为实际的签发日期
    tLCPolSchema.setSignDate( tLCPolSchema.getSignDate());

    // 设置保单的代理机构
    LAComDB tLAComDB = new LAComDB();
    tLAComDB.setAgentCom( tLCPolSchema.getAgentCom() );
    tLAComDB.getInfo();
    tLCPolSchema.setAgentCom( tLAComDB.getName() );

    xmlDataset.addSchema(tLCPolSchema);
    xmlDataset.addSchema(mLCContSchema);
    // 加入代理人姓名的信息
    LAAgentDB tLAAgentDB = new LAAgentDB();
    tLAAgentDB.setAgentCode( tLCPolSchema.getAgentCode() );
    if( !tLAAgentDB.getInfo() ) {
      mErrors.copyAllErrors(tLAAgentDB.mErrors);
      buildError("getPolDataSet", "在获取代理人数据时发生错误");
      logger.debug(tLAAgentDB.mErrors.getFirstError());
      return false;
    }

    xmlDataset.addDataObject(new XMLDataTag("LCPol.AgentName", tLAAgentDB.getName()));

    xmlDataset.addSchema((LCAppntGrpSchema)vData.getObjectByObjectName("LCAppntGrpSchema", 0));

    xmlDataset.addSchemaSet((LCInsuredSet)vData.getObjectByObjectName("LCInsuredSet", 0), "");
    xmlDataset.addSchemaSet((LCDutySet)vData.getObjectByObjectName("LCDutySet", 0), "");
    //zy 调整获取受益人的逻辑
//    xmlDataset.addSchemaSet((LCBnfSet)vData.getObjectByObjectName("LCBnfSet", 0), "");
    LCBnfSet tLCBnfSet =(LCBnfSet)vData.getObjectByObjectName("LCBnfSet", 0);
    xmlDataset.addSchemaSet((LCCustomerImpartSet)vData.getObjectByObjectName("LCCustomerImpartSet", 0), "");

    /**
     * 如果没有特别约定，打印“此栏空白”
     */
    LCSpecSet tLCSpecSet = (LCSpecSet)vData.getObjectByObjectName("LCSpecSet", 0);
    LCSpecSet tNewLCSpecSet = new LCSpecSet();

    // 将有效的特约信息放到新的Set中
    for(int nIndex = 0; nIndex < tLCSpecSet.size(); nIndex ++) {
      LCSpecSchema tLCSpecSchema = tLCSpecSet.get(nIndex + 1);
      if( tLCSpecSchema.getSpecContent() != null
          && !tLCSpecSchema.getSpecContent().equals("") ) {
        tNewLCSpecSet.add(tLCSpecSchema);
      }
    }

    // 如果没有任何特约，打印“此栏空白”
    if( tNewLCSpecSet.size() == 0 ) {
      LCSpecSchema tLCSpecSchema = new LCSpecSchema();

      tLCSpecSchema.setSpecContent("此栏空白");
      tNewLCSpecSet.add(tLCSpecSchema);
    }

    xmlDataset.addSchemaSet(tNewLCSpecSet, "");
    xmlDataset.addSchemaSet((LMDutySet)vData.getObjectByObjectName("LMDutySet", 0), "");

    // 加入保费合计和伤残责任以及医院的信息
    double dPremSum = 0;

    String strInjuryGetFlag = "N";
    String strHospitalFlag = "N";
    String strRiskCode = "";
    String strTemp = "";

    for(int i = 0; i < aLCPolSet.size(); i++) {
      dPremSum += aLCPolSet.get(i+1).getPrem();

      strRiskCode = aLCPolSet.get(i+1).getRiskCode();

      tLMRiskAppDB = new LMRiskAppDB();

      tLMRiskAppDB.setRiskCode( strRiskCode );
      if( !tLMRiskAppDB.getInfo() ) {
        mErrors.copyAllErrors( tLMRiskAppDB.mErrors );
        return false;
      }

      strTemp = tLMRiskAppDB.getNeedPrintGet();
      if( strTemp != null && strTemp.equals("1") ) {
        strInjuryGetFlag = "Y";
      }

      strTemp = tLMRiskAppDB.getNeedPrintHospital();
      if( strTemp != null && strTemp.equals("1") ) {
        strHospitalFlag = "Y";
      }
    }

    // 加入医院列表和伤残给付责任表的信息
    xmlDataset.addDataObject(new XMLDataTag("InjuryGetFlag", strInjuryGetFlag));
    xmlDataset.addDataObject(new XMLDataTag("HospitalFlag", strHospitalFlag));

    xmlDataset.addDataObject(new XMLDataTag("PremSum", format(dPremSum)));

    // Kevin 2002-03-14
    xmlDataset.addDataObject(new XMLDataTag("LJTempFee.PayMoneySum", format(dPremSum)));
    xmlDataset.addDataObject(new XMLDataTag("LJTempFee.PayMoneySum1", PubFun.getChnMoney(dPremSum).replaceFirst("人民币", "")));//去掉保费开头的“人民币”字样
    if(mLCContSchema.getLostTimes()>0)
    {
      xmlDataset.addDataObject(new XMLDataTag("PrintDateRemark","保单补发日期:"));
      xmlDataset.addDataObject(new XMLDataTag("PrintDate",changeDate(PubFun.getCurrentDate())));
      xmlDataset.addDataObject(new XMLDataTag("LostTimesRemark","保单补发次数:"));
      xmlDataset.addDataObject(new XMLDataTag("LostTimes",mLCContSchema.getLostTimes()));
    }
    try {
    	if(mOperate.equals("PRINTEX")){
    		genRiskInfo(xmlDataset, aLCPolSet);
    		getDutyList(xmlDataset, aLCPolSet.get(1));
    		getAppntAndInsuredList(xmlDataset, aLCPolSet.get(1));
    		getBnfList(xmlDataset,tLCBnfSet);
    	}else{
    		genRiskInfo(xmlDataset, aLCPolSet);
    		getCashValue(xmlDataset, aLCPolSet);
    		getScanPic(xmlDataset, aLCPolSet.get(1));
    		getDutyList(xmlDataset, aLCPolSet.get(1));
    		getAppntAndInsuredList(xmlDataset, aLCPolSet.get(1));
    		getBnfList(xmlDataset,tLCBnfSet);
    	}
     
    } catch (Exception ex) {
      ex.printStackTrace();
      buildError("getPolDataSet", ex.getMessage());
      return false;
    }

    return true;
  }
  //add by ck
  //Format参数"yyyy","MM","dd"
  public String changeDate(String tDate)
  {
    FDate tFDate=new FDate();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy"+"年"+"MM"+"月"+"dd"+"日");
     
    return sdf1.format(tFDate.getDate(tDate));
  }

  private void genXMLFile()
  {
    try {
      FileWriter writer = new FileWriter("LCPolData.xml");
      mXMLDatasets.output(writer);
      writer.close();
    } catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * 获取主险/附加险列表和暂交费数据
   * @param xmlDataset
   * @param aLCPolSet
   * @throws Exception
   */
  private void genRiskInfo(XMLDataset xmlDataset, LCPolSet aLCPolSet)
      throws Exception
  {
    XMLDataList xmlDataList = new XMLDataList("RiskInfo");

    xmlDataList.addColHead("COL9");
    xmlDataList.addColHead("COL90");
    xmlDataList.addColHead("COL10");
    xmlDataList.addColHead("COL111");
    xmlDataList.addColHead("COL110");
    xmlDataList.addColHead("COL109");
    xmlDataList.addColHead("COL108");
    xmlDataList.addColHead("COL43");
    xmlDataList.addColHead("COL40");
    xmlDataList.addColHead("COL33");
    xmlDataList.addColHead("COL1");
    xmlDataList.addColHead("COL57");
    xmlDataList.addColHead("COL58");

    xmlDataList.buildColHead();
    xmlDataList.setAutoCol(false);

    double fPayMoneySum = 0f;

    for(int nIndex = 0; nIndex < aLCPolSet.size(); nIndex++) {
      LCPolSchema tLCPolSchema = aLCPolSet.get(nIndex+1);

      xmlDataList.setColValue("COL58", String.valueOf(nIndex));
      xmlDataList.setColValue("COL9", tLCPolSchema.getRiskCode());

      // 加入主附险标志
      LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
      tLMRiskAppDB.setRiskCode( tLCPolSchema.getRiskCode() );
      if( !tLMRiskAppDB.getInfo() ) {
        throw new Exception("险种描述取数失败");
      }

      xmlDataList.setColValue("COL90", tLMRiskAppDB.getSubRiskFlag());
      xmlDataList.setColValue("COL10", tLCPolSchema.getRiskVersion());

      xmlDataList.setColValue("COL111", tLCPolSchema.getInsuYear());
      xmlDataList.setColValue("COL110", tLCPolSchema.getInsuYearFlag());
      if( tLCPolSchema.getInsuYear() == 1000 && tLCPolSchema.getInsuYearFlag().equals("A") ) {
        xmlDataList.setColValue("COL111", "终身");
        xmlDataList.setColValue("COL110", "");
      }

      xmlDataList.setColValue("COL109", tLCPolSchema.getPayEndYear());
      xmlDataList.setColValue("COL108", tLCPolSchema.getPayEndYearFlag());

      xmlDataList.setColValue("COL43", tLCPolSchema.getAmnt());
      xmlDataList.setColValue("COL40", format(tLCPolSchema.getPrem()));

      // 对趸交的保单，将交费期满日设为保单的生效日期
      if( tLCPolSchema.getPayIntv() != 0 ) {
        xmlDataList.setColValue("COL33", prevDay(tLCPolSchema.getPayEndDate()));
      } else {
        xmlDataList.setColValue("COL33", prevDay(tLCPolSchema.getCValiDate()));
      }

      // 2003-04-28
      // 收据上每一个险种所对应的金额就是保费
      xmlDataList.setColValue("COL1", format(tLCPolSchema.getPrem()));
      xmlDataList.setColValue("COL57", tLCPolSchema.getPayIntv());

      xmlDataList.insertRow(0);
    }

    xmlDataset.addDataObject(xmlDataList);
//签单了居然只差印刷号？
//    String strSQL = "SELECT TempFeeNo, Operator FROM LJTempFee WHERE  OtherNoType = '4'  AND OtherNo = '" +
//                    aLCPolSet.get(1).getPrtNo() + "'";
  String strSQL = "SELECT TempFeeNo, Operator FROM LJTempFee WHERE   OtherNo = '"+"?OtherNo?"+ "'  "
  				+ "union SELECT TempFeeNo, Operator FROM LJTempFee WHERE othernotype='4' and OtherNo='"+"?PrtNo?"+"' ";
  SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
  sqlbv4.sql(strSQL);
  sqlbv4.put("OtherNo", aLCPolSet.get(1).getContNo());
  sqlbv4.put("PrtNo", aLCPolSet.get(1).getPrtNo());
    SSRS ssRs = new ExeSQL().execSQL(sqlbv4);
    if( ssRs.ErrorFlag || ssRs.MaxRow == 0 ) {
      throw new Exception("找不到该个人保单的暂交费数据");
    }

    xmlDataset.addDataObject(new XMLDataTag("LJTempFee.TempFeeNo", ssRs.GetText(1, 1)));
    xmlDataset.addDataObject(new XMLDataTag("LJTempFee.Handler", ssRs.GetText(1, 2)));
  }

  private String prevDay(String strDate) {
    Date dt = PubFun.calDate(new FDate().getDate(strDate), -1, "D", null);
    return new FDate().getString(dt);
  }

  // 获取补打保单的数据
  private boolean getRePrintData() {

    String strCurDate = PubFun.getCurrentDate();
    String strCurTime = PubFun.getCurrentTime();

    ExeSQL exeSQL = new ExeSQL();
    SSRS ssrs = null;

    // 利用LCPolSet来传递数据，其中包含的数据并不是LCPol的数据
    LCPolSet tLCPolSet = new LCPolSet();

    for(int nIndex = 0; nIndex < mLCPolSet.size(); nIndex++) {
      LCPolSchema tLCPolSchema = new LCPolSchema();
      tLCPolSchema.setSchema( mLCPolSet.get(nIndex + 1) );
      SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
      sqlbv5.sql("SELECT PrtTimes + 1 FROM LCPolPrint WHERE MainPolNo = '" +"?MainPolNo?"+ "'");
      sqlbv5.put("MainPolNo", tLCPolSchema.getPolNo());
      ssrs = exeSQL.execSQL(sqlbv5);

      if( exeSQL.mErrors.needDealError() ) {
        mErrors.copyAllErrors(exeSQL.mErrors);
        return false;
      }

      if( ssrs.MaxRow < 1 ) {
        buildError("getRePrintData", "找不到原来的打印数据，可能传入的不是主险保单号！");
        return false;
      }
//      tLCPolSchema.setPrintCount(ssrs.GetText(1, 1));
      tLCPolSchema.setModifyDate(strCurDate);
      tLCPolSchema.setModifyTime(strCurTime);

      tLCPolSet.add(tLCPolSchema);
    }

    mResult.clear();
    mResult.add(tLCPolSet);
//
//    LCPolF1PBLS tLCPolF1PBLS = new LCPolF1PBLS();
//
//    if( !tLCPolF1PBLS.submitData(mResult, "REPRINT") ) {
//      if( !tLCPolF1PBLS.mErrors.needDealError() ) {
//        buildError("getRePrint", "保存数据失败，但是没有提供详细信息");
//        return false;
//      } else {
//        mErrors.copyAllErrors(tLCPolF1PBLS.mErrors);
//        return false;
//      }
//    }

    // 取打印数据
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      DOMBuilder domBuilder = new DOMBuilder();
      Element rootElement = new Element("DATASETS");

      conn = DBConnPool.getConnection();
      stmt = conn.createStatement();

      if( conn == null ) {
        throw new Exception("连接数据库失败！");
      }
      String tSQL ="";
      java.sql.Blob tBlob = null;
      COracleBlob tCOracleBlob = new COracleBlob();
      CMySQLBlob tCMySQLBlob = new CMySQLBlob();
     
      for(int nIndex = 0; nIndex < tLCPolSet.size(); nIndex ++) {
        LCPolSchema tLCPolSchema = tLCPolSet.get(nIndex + 1);
        // modify by yt

        rs = stmt.executeQuery("SELECT PolInfo FROM LCPolPrint WHERE MainPolNo = '" + tLCPolSchema.getPolNo() + "'");
        
        tSQL = " and MainPolNo = '" + tLCPolSchema.getPolNo() + "'";
        if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
        	tBlob = tCOracleBlob.SelectBlob("LCPolPrint","PolInfo",tSQL,conn);
        }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
        	tBlob = tCMySQLBlob.SelectBlob("LCPolPrint","PolInfo",tSQL,conn);	
        }
        if( tBlob == null) {
          throw new Exception("找不到打印数据");
        }
        Element ele = domBuilder.build(tBlob.getBinaryStream()).getRootElement();
        ele = new Element("DATASET").setMixedContent(ele.getMixedContent());
        rootElement.addContent(ele);

        rs.close();
      }

      Document doc = new Document(rootElement);

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      XMLOutputter xmlOutputter = new XMLOutputter("  ", true, "UTF-8");
      xmlOutputter.output(doc, baos);

      mResult.clear();
      mResult.add(new ByteArrayInputStream(baos.toByteArray()));

      if( rs != null )  rs.close();
      if( stmt != null )  stmt.close();
      if( conn != null )  conn.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      try {
        if( rs != null )  rs.close();
        if( stmt != null )  stmt.close();
        if( conn != null )  conn.close();
      } catch (Exception e) {
        // do nothing
      }
      buildError("getRePrintData", ex.getMessage());
      return false;
    }

    return true;
  }

  /**
   * 通过传入的保单号，得到主附险保单列表
   * 其中aLCPolSet中的第一个元素是主险保单，其余的元素是附加险保单
   */
  private boolean getPolSet(LCPolSchema aLCPolSchema, LCPolSet aLCPolSet)
  {
    LCPolDB tLCPolDB = new LCPolDB();

    tLCPolDB.setPolNo( aLCPolSchema.getPolNo() );

    if( !tLCPolDB.getInfo() ) {
      mErrors.copyAllErrors(tLCPolDB.mErrors);
      return false;
    }

    String szPrtNo = tLCPolDB.getPrtNo();

    tLCPolDB = new LCPolDB();
    // tLCPolDB.setPrtNo( szPrtNo );
    // 排序险种列表
    SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
    sqlbv7.sql("SELECT * FROM LCPol WHERE PrtNo = '" +"?szPrtNo?"+ "' and appflag='1' ORDER BY PolNo");
    sqlbv7.put("szPrtNo", szPrtNo);
    LCPolSet tLCPolSet =
        tLCPolDB.executeQuery(sqlbv7);

    if( tLCPolDB.mErrors.needDealError() ) {
      mErrors.copyAllErrors( tLCPolDB.mErrors );
      return false;
    }

    String szMainPolNo = "";

    // 清空要返回的结果集
    aLCPolSet.clear();

    // 第一个元素放的是主险保单，其余元素放的是附加险保单
    for(int nIndex = 0; nIndex < tLCPolSet.size(); nIndex ++) {
      LCPolSchema tLCPolSchema = tLCPolSet.get(nIndex + 1);

      // 如果是主险保单
      if( tLCPolSchema.getPolNo().equals( tLCPolSchema.getMainPolNo() ) ) {
        if( !szMainPolNo.equals("") ) {
          buildError("getPolSet", "同一个印刷号存在两个主险保单");
          return false;
        }
        aLCPolSet.add( tLCPolSchema.getSchema() );
        szMainPolNo = tLCPolSchema.getPolNo();
      }
    }

    // 判断是否找到了主险保单
    if( szMainPolNo.equals("") ) {
      buildError("getPolSet", "找不到要打印的主险保单，印刷号为：" + szPrtNo);
      return false;
    }

    // 加入附加险的保单
    for(int nIndex = 0; nIndex < tLCPolSet.size(); nIndex ++) {
      LCPolSchema tLCPolSchema = tLCPolSet.get(nIndex + 1);

      if( !tLCPolSchema.getPolNo().equals( szMainPolNo ) ) {
        aLCPolSet.add( tLCPolSchema.getSchema() );
      }
    }

    return true;
  }


  /**
   * 保单重打的取数流程
   * @param aLCPolSet
   * @return
   */
  private boolean getPolDataSetEx(LCPolSet aLCPolSet)
  {
    XMLDataset xmlDataset = mXMLDatasets.createDataset();

    // 取打印数据
    Connection conn = null;

    try {
      conn = DBConnPool.getConnection();
      if( conn == null ) {
        throw new Exception("连接数据库失败！");
      }

      LCPolSchema tLCPolSchema = aLCPolSet.get( 1 );
      COracleBlob tCOracleBlob = new COracleBlob();
      CMySQLBlob tCMySQLBlob = new CMySQLBlob();
      String tSQL ="";
      java.sql.Blob tBlob = null;
      tSQL = " and MainPolNo = '" + tLCPolSchema.getPolNo() + "'";
      if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
    	  tBlob = tCOracleBlob.SelectBlob("LCPolPrint","PolInfo",tSQL,conn);
      }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
    	  tBlob = tCMySQLBlob.SelectBlob("LCPolPrint","PolInfo",tSQL,conn);		
      }
      if( tBlob == null) {
        throw new Exception("找不到打印数据");
      }

      //BLOB blob = (oracle.sql.BLOB)tBlob; --Fanym
      logger.debug("Get Blob") ;
      XMLDataBlob xmlDataBlob = new XMLDataBlob( tBlob.getBinaryStream());
      xmlDataset.addDataObject(xmlDataBlob);

      if( conn != null )  conn.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      try {
        if( conn != null )  conn.close();
      } catch (Exception e) {
        // do nothing
      }
      buildError("getPolDataSetEx", ex.getMessage());
      return false;
    }

    return true;
  }

  /**
   * 2003-04-28 Kevin
   * 格式化浮点型数据
   * @param dValue
   * @return
   */
  private String format(double dValue) {
    return new DecimalFormat("0.00").format(dValue);
  }

  private String getName(String strType, String strCode) {
    LDCodeDB tLDCodeDB = new LDCodeDB();

    tLDCodeDB.setCodeType(strType);
    tLDCodeDB.setCode(strCode);

    if( !tLDCodeDB.getInfo() ) {
      return "";
    } else {
      return tLDCodeDB.getCodeName();
    }
  }

  /**
   * some special process for count print
   * @param doc
   */
  private void countPrint(Document doc) {
    Element ele = doc.getRootElement();

    String strOrg = "";

    //
    // 终止日期减一天
    //
    strOrg = ele.getChild("LCPol.EndDate").getText();
    strOrg = strOrg.substring(0, 4) + "-"
           + strOrg.substring(5, 7) + "-"
           + strOrg.substring(8, 10);
    strOrg = prevDay(strOrg);
    strOrg = strOrg.substring(0, 4) + "年"
           + strOrg.substring(5, 7) + "月"
           + strOrg.substring(8, 10) + "日";
    ele.getChild("LCPol.EndDate").setText(strOrg);

    // 代码到名字的转换

    strOrg = ele.getChild("LCPol.InsuredSex").getText();
    ele.getChild("LCPol.InsuredSex").setText(getName("sex", strOrg));
//yanglh
//    List Headlist = ele.getChild("LCBnf").getChildren("HEAD");
//    String tBnflogCol="";
//    String trelationCol="";
//    for(int n = 0; n < Headlist.size(); n++) {
//        Element eleList = (Element)Headlist.get(n);
//        
//        List Collist = eleList.getChildren();
//        for(int k = 0; k < Collist.size(); k++){
//          Element TextList = (Element)Collist.get(k);  
//          if("BnfLot".equals(TextList.getText()))
//          {
//        	  tBnflogCol=TextList.getName();
//        	  
//          }
//          if("RelationToInsured".equals(TextList.getText()))
//          {
//        	  trelationCol=TextList.getName();      	  
//          }
//        }
//    }
//    if("".equals(tBnflogCol)|| tBnflogCol==null || "".equals(trelationCol)|| trelationCol==null)
//    {
//        throw new IllegalArgumentException("找不到受益人信息");
//    }
//    List list = ele.getChild("LCBnf").getChildren("ROW");
//    for(int n = 0; n < list.size(); n++) {
//      Element eleList = (Element)list.get(n);
//
//      // 将受益比例设为百分比的形式
//      strOrg = eleList.getChild(tBnflogCol).getText();
//      strOrg = String.valueOf(Double.parseDouble(strOrg) * 100) + "%";
//      eleList.getChild(tBnflogCol).setText(strOrg);
//
//
//      strOrg = eleList.getChild(trelationCol).getText();
//      
//      eleList.getChild(trelationCol).setText(getName("relation", strOrg));
//    }
//
//    // 如果没有受益人，打印“此栏空白”。
//    if( list.size() == 0 ) {
//      ele.getChild("LCBnf").addContent(
//                                       new Element("ROW").addContent(
//                                       new Element("COL8").addContent("此栏空白")));
//    }

    // 设置管理机构的信息
    strOrg = ele.getChild("LCPol.ManageCom").getText();

    LDComDB tLDComDB = new LDComDB();
    tLDComDB.setComCode(strOrg);
    tLDComDB.getInfo();
    ele.addContent(new Element("ManageCom.Phone").setText(tLDComDB.getPhone()));

    ele.getChild("LCPol.ManageCom").setText(getName("station", strOrg));

    // 添加管理机构的电话

  }
  
  //获取受益人信息
private void getBnfList(XMLDataset xmlDataset, LCBnfSet aLCBnfSet)  throws Exception
{

    XMLDataList xmlDataList = new XMLDataList();

    xmlDataList.setDataObjectID("LCBnf");

    xmlDataList.addColHead("COL9"); //姓名
    xmlDataList.addColHead("COL13");//证件号
    xmlDataList.addColHead("COL6");//关系
    xmlDataList.addColHead("COL7");//比例
    xmlDataList.buildColHead();
    xmlDataList.setAutoCol(false);

    for(int nIndex = 0; nIndex < aLCBnfSet.size(); nIndex++) {
      LCBnfSchema tLCBnfSchema = aLCBnfSet.get(nIndex+1);
      String sBnfLot = String.valueOf(tLCBnfSchema.getBnfLot() * 100) + "%";
      xmlDataList.setColValue("COL9", tLCBnfSchema.getName());
      xmlDataList.setColValue("COL13",tLCBnfSchema.getIDNo());
      xmlDataList.setColValue("COL6",getName("relation", tLCBnfSchema.getRelationToInsured()));
      xmlDataList.setColValue("COL7",sBnfLot);
      xmlDataList.insertRow(0);
    }
    xmlDataset.addDataObject(xmlDataList);
  } 

}
