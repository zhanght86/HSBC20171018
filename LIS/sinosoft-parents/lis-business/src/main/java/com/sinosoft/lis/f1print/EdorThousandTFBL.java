
package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

import java.text.*;
import java.util.*;

/**
 * <p>Title: lis</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: sinosoft</p>
 * @author tm
 * @version 2.0 by jiaqiangli   
 * 2010-03-03 modify  by mslife
 */


public class EdorThousandTFBL {
private static Logger logger = Logger.getLogger(EdorThousandTFBL.class);

  public EdorThousandTFBL() {
  }
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public CErrors mErrors=new CErrors();
  private VData mResult = new VData();

  //取得的时间
  private String StartDay="";
  private String EndDay="";
  private String CalManageCom="";

  //含M00 1表示是 0表示否
  private String tIsHaveM00 = "";

  private String mFilePathDesc = "TKReportCreat"; //生成文件的路径,待修改
  private String mFilePath = ""; //生成文件路径
  private String mModePath = "";//模版路径
  private String mTemplatePath="";

  String strArr[] = new String[23];
//  private String strArr[] = new String[15];
  //业务处理相关变量
  /** 全局数据 */
  private GlobalInput mGlobalInput =new GlobalInput() ;
  /**
  传输数据的公共方法
  */
  public boolean submitData(VData cInputData, String cOperate)
  {
    // 得到外部传入的数据，将数据备份到本类中
    if( !getInputData(cInputData) ) {
      return false;
    }
    mResult.clear();
    // 准备所有要打印的数据
    if( !queryDataOther() ) {
      return false;
    }
    return true;
  }
  private void dealError(String FuncName,String ErrMsg) {
    CError error = new CError();
    error.moduleName = "SummitMarketingTeam";
    error.errorMessage = ErrMsg.trim();
    error.functionName = FuncName.trim();
    this.mErrors.addOneError(error);
  }
  /**
     * 取模版存放路径
     * @return
     */
    private boolean checkDesc()
    {
      logger.debug("@@@@@@@@@@@@@@@@@@@@@");
      LDSysVarDB tLDSysVarDB = null;

      //生成文件的存放路径
      tLDSysVarDB = new LDSysVarDB();
      tLDSysVarDB.setSysVar(mFilePathDesc);
      if (tLDSysVarDB.getInfo() == false)
      {
        dealError("checkDesc", "LDSysVar取文件路径(" + mFilePathDesc + ")描述失败");
        return false;
      }
      //生成文件路径
      mFilePath = tLDSysVarDB.getSysVarValue();
      // mFileName = getFileName();
      //mFilePath="d:/zhujc/";
      logger.debug("mFilePath:"+mFilePath);
  
		//模版路径
	  this.mModePath=mTemplatePath;
      logger.debug("mModePath:"+mModePath);
      return true;
  }
  private boolean getTempPath()
  {
    //获取生成文件路径
    if(!checkDesc())
    {
      return false;
    }
    return true;
  }
  /**
   * 从输入数据中得到所有对象
   * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  private boolean getInputData(VData cInputData)
  {
    //全局变量
    StartDay=(String)cInputData.get(0);
    EndDay=(String)cInputData.get(1);
    CalManageCom=(String)cInputData.get(2);
    tIsHaveM00=(String)cInputData.get(3);
    logger.debug("BL是否含M00["+tIsHaveM00+"]");
    mGlobalInput=(GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0) ;
    this.mTemplatePath=(String)cInputData.get(5);
    //获取模版和生成文件的路径
    if(!getTempPath())
    {
      logger.debug("获取模版和生成文件的路径出错");
      return false;
    }

    return true;
  }
  public VData getResult()
  {
    return this.mResult;
  }

  private boolean queryDataOther()
  {
	  
	String  mSaleChlSQL ="";
		if (tIsHaveM00 != null && !"".equals(tIsHaveM00)) {
			mSaleChlSQL = " and exists (select 'X'" + " from lccont p "
					+ "where p.contno = a.contno "
					+ " and p.salechnl = '" + tIsHaveM00 + "') ";
		}

    try{
    	SQLwithBindVariables sqlbv = new SQLwithBindVariables();
      String[] tSQL = new String[7];
      String tSQL_key = "";//(select decode(d.edorappname,b.AppntName,(select decode(w.phone,null,w.mobile,w.phone) from LCAddress w,lcappnt q where  w.customerno = q.appntno and w.addressno = q.addressno  and q.contno=b.contno),b.InsuredName,(select decode(phone,null,mobile,phone) from LCAddress w,lcinsured q where  w.customerno = q.appntno and w.addressno = q.addressno  and q.contno=b.contno),'')  from lccont b where contno=a.contno)
      tSQL_key = "select substr(trim(a.managecom), 1, 6) com3,(select name from ldcom where comcode=a.managecom) com4,(select (select name from lacom where agentcom =b.agentcom) from lccont b where contno=a.contno) agentcom,"
		      +" (select (select codename from ldcode where  codetype='salechnl' and code=b.salechnl)  from lccont b where contno=a.contno) salechnl,"
		      +" (select codename from ldcode where codetype='edorapptype' and code=d.apptype),d.edorappname,"
		      +" d.phone,"
		      +" d.postaladdress,"
		      +" d.zipcode, a.contno,"
		      +" (select (select codename from ldcode where  codetype='edortype' and code=b.edortype) from lpedoritem b where edoracceptno=a.edoracceptno and contno=a.contno ) edortype,abs(a.getmoney),"
		      +" (select (select name from laagent where agentcode=b.agentcode) from lccont b where contno=a.contno) agentname,"
		      +" (select agentcode from lccont where contno=a.contno) agentcode,"
		      +" (select (select (case when mobile is null then phone else mobile end) from laagent where agentcode=b.agentcode) from lccont b where contno=a.contno) agentphone"
		      +" ,(select max(edorappdate) from lpedoritem where edoracceptno=d.edoracceptno),d.BehalfName, (select codename from ldcode where codetype='paymode' and code=(case f.edortype when 'LG' then f.remark when 'XT' then f.remark when 'CT' then f.remark else d.payform end)), (case f.edortype when 'LG' then f.BankAccNo when 'XT' then f.BankAccNo when 'CT' then f.BankAccNo else d.BankAccNo end) "
		      +" from lpedormain a LEFT JOIN lpbnf f ON f.contno  = a.contno AND f.edorno  = a.edorno,lpedorapp d"
		      +" where a.modifydate >= '"+"?StartDay?"+"'"
		      +" and a.modifydate <= '"+"?EndDay?"+"'"
		      +" and a.managecom like concat('"+"?CalManageCom?"+"','%')"
		      +" and a.edorstate ='0' and d.apptype not in ('1','6')"
		      +" and not exists (select '1' from lccont where contno=a.contno and conttype='2')"
		      +" and a.getmoney<-1000"
				+" and exists (select 'X' from lpedoritem p"
				+" where p.contno = d.otherno and p.edoracceptno = d.edoracceptno"
				+" and p.edortype in (select code from ldcode where codetype = 'edortype' and othersign = '1')) " 
		      +" and a.edoracceptno=d.edoracceptno";
		      sqlbv.sql(tSQL_key);
		      sqlbv.put("StartDay", StartDay);
		      sqlbv.put("EndDay", EndDay);
		      sqlbv.put("CalManageCom", CalManageCom);
              ZHashReport tZHashReport = new ZHashReport(sqlbv);
//            for (int i = 0; i < tSQL.length; i++) {
//              tZHashReport.addSql(tSQL[i]);
//            }
            tZHashReport.setColumnType(1,tZHashReport.StringType);//0机构代码 1机构名称
//            tZHashReport.setColumnType(2,tZHashReport.IntType);//期初人力
//            tZHashReport.setColumnType(3,tZHashReport.IntType);//期末人力
            tZHashReport.setColumnType(1,tZHashReport.StringType);
            tZHashReport.setColumnType(2,tZHashReport.StringType);
            tZHashReport.setColumnType(3,tZHashReport.StringType);
            tZHashReport.setColumnType(4,tZHashReport.StringType);
            tZHashReport.setColumnType(5,tZHashReport.StringType);
            tZHashReport.setColumnType(6,tZHashReport.StringType);
            tZHashReport.setColumnType(7,tZHashReport.StringType);
            tZHashReport.setColumnType(8,tZHashReport.StringType);
            tZHashReport.setColumnType(9,tZHashReport.StringType);
            tZHashReport.setColumnType(10,tZHashReport.StringType);
            tZHashReport.setColumnType(11,tZHashReport.StringType);
            tZHashReport.setColumnType(12,tZHashReport.StringType);
            tZHashReport.setColumnType(13,tZHashReport.StringType);
            tZHashReport.setColumnType(14,tZHashReport.StringType);
            tZHashReport.setColumnType(15,tZHashReport.StringType);
            tZHashReport.setColumnType(16,tZHashReport.StringType);
            tZHashReport.setColumnType(17,tZHashReport.StringType);
            tZHashReport.setColumnType(18,tZHashReport.StringType);
            tZHashReport.setColumnType(19,tZHashReport.StringType);
            
            tZHashReport.setDoformat(false);
            tZHashReport.setSumColumn(false);

            String[][] result = tZHashReport.calItem();
            
//            if (result==null||result.length==0)
//            {
//              // @@错误处理
//              CError tError = new CError();
//              tError.moduleName = "EdorThousandTFBL";
//              tError.functionName = "queryDataOther()";
//              tError.errorMessage = "没有查询到数据!";
//              this.mErrors .addOneError(tError) ;
//              return false;
//            }
			for (int jj = 0; jj < result.length; jj++) {
				for (int kk = 0; kk < result[jj].length; kk++) {
					logger.debug(result[jj][kk] + ",");
				}
			}
               //表头
               TransferData tTitleTransferData = new TransferData();
               tTitleTransferData.setNameAndValue("ManageCom",this.CalManageCom);
               tTitleTransferData.setNameAndValue("StartDate",this.StartDay);
               tTitleTransferData.setNameAndValue("EndDate",this.EndDay);
               

            tTitleTransferData.setNameAndValue("Operator",
					this.mGlobalInput.Operator);// 制表人
			tTitleTransferData.setNameAndValue("CurrentDT", PubFun
					.getCurrentDate()
					+ " " + PubFun.getCurrentTime());// 制表日期时间

			HashReport tHashReport = new HashReport();
			// 生成路径，模版路径，多维数组
			String tCreatePath = "";
			String tModelPath = "";
			// 生成文件名命名规范：报表名_级别_月份.xls
			// 注意：此处还需要在生成文件名加些参数。。。。
			tCreatePath = this.mFilePath + "EdorthousandTF_"+this.CalManageCom + "_"
					+ this.StartDay + "_" + this.EndDay + "_"
					+ tIsHaveM00 + ".xls";
			tModelPath = this.mModePath + "EdorThousandTFQuery.xls";
			logger.debug("[" + tModelPath + "]");
			logger.debug(tCreatePath);
			logger.debug(tModelPath);
			try {
				// 有表头
				tHashReport.outputArrayToFile1(tCreatePath, tModelPath,
						result, tTitleTransferData);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
      logger.debug("data end");
    }
    catch(Exception ex)
    {
    	ex.printStackTrace();
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="LAAgentMakePolBL";
      tError.functionName="queryDataother";
      tError.errorMessage=ex.toString();
      this.mErrors .addOneError(tError);
      return false;
    }
    logger.debug("end");
    return true;
  }
  public static void main(String[] args) {
	  EdorThousandTFBL tLAAgentTempfeePolBL = new EdorThousandTFBL();
    /*StartDay=(String)cInputData.get(0);
    EndDay=(String)cInputData.get(1);
    CalManageCom=(String)cInputData.get(2);
    tIsHaveM00=(String)cInputData.get(3);
    mGlobalInput=(GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0) ;*/
    String tStartDay = "2010-04-01";
    String tEndDay = "2010-04-30";
    String tCalManageCom = "86";
    String tIsHaveM00 = "";
    String tm="D:/zhujc/lis6.5/ui/f1print/exceltemplate/";
    GlobalInput tGI = new GlobalInput();
    tGI.ManageCom = "8613";
    tGI.Operator = "AutoPrt";
    VData tVData = new VData();

    tVData.addElement(tStartDay);
    tVData.addElement(tEndDay);
    tVData.addElement(tCalManageCom);
    tVData.addElement(tIsHaveM00);
    tVData.addElement(tGI);
    tVData.addElement(tm);

    tLAAgentTempfeePolBL.submitData(tVData,"");
    
    //logger.debug(new DecimalFormat("0.00%").format((double)0.2525)); // 活动率);
  }
}


