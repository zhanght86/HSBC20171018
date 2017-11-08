/*
* <p>ClassName: OLZCardBugetPrintBL </p>
* <p>Description: 单证统计查询的实现文件 </p>
* <p>Copyright: Copyright (c) 2002</p>
* <p>Company: sinosoft </p>
* @Database: lis
* @CreateDate：2004-1-6
 */
package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import java.util.Hashtable;
import java.sql.*;
import java.text.*;
public class OLZCardBugetPrintBL {
private static Logger logger = Logger.getLogger(OLZCardBugetPrintBL.class);

  public OLZCardBugetPrintBL() {
  }
  public  CErrors mErrors = new CErrors();
   private VData mResult = new VData();


   /** 全局数据 */
   private GlobalInput m_GlobalInput = new GlobalInput() ;

   /** 数据操作字符串 */
   private String m_strOperate;

   /** 业务处理相关变量 */
   private String  m_sql ="";
   private LZCardBugetSet m_LZCardBugetSet = new LZCardBugetSet();
   /**
    * 传输数据的公共方法
    * @param: cInputData 输入的数据
    * cOperate 数据操作
    * @return:
    */
   public boolean submitData(VData cInputData,String cOperate)
   {
     m_strOperate = verifyOperate(cOperate);
     if( m_strOperate.equals("") ) {
       buildError("submitData", "不支持的操作字符串");
       return false;
     }

     if (!getInputData(cInputData))
       return false;

     if (!dealData())
       return false;
     return true;
   }
   /**
    * 根据前面的输入数据，进行BL逻辑处理
    * 如果在处理过程中出错，则返回false,否则返回true
    */
   private boolean dealData()
   {
     try {
       if(! m_strOperate.equals("") ) {
         return submitPrint(m_strOperate);//申请
       } else {
         buildError("dealData", "不支持的操作字符串");
         return false;
       }
     } catch (Exception ex) {
       ex.printStackTrace();
       return false;
     }
   }

   /**
    * 从输入数据中得到所有对象
    * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
    */
   private boolean getInputData(VData cInputData)
   {
     try {
       this.m_GlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
       this.m_sql=(String)cInputData.get(1);
       if( m_sql == null||m_sql.equals("") ) {
         buildError("getInputData", "没有查询语句");
         return false;
       }
       if( m_GlobalInput== null || m_GlobalInput.equals("") ) {
         buildError("getInputData", "没有登陆信息");
         return false;
       }

     } catch(Exception ex) {
       ex.printStackTrace();
       buildError("getInputData", ex.getMessage());
       return false;
     }
     return true;
   }
   private boolean prepareOutputData(VData aVData)
   {
     return true;
   }

   public VData getResult()
   {
     return this.mResult;
   }

   private void buildError(String szFunc, String szErrMsg)
   {
     CError cError = new CError( );
     cError.moduleName = "OLZCardBugetPrintBL";
     cError.functionName = szFunc;
     cError.errorMessage = szErrMsg;
     this.mErrors.addOneError(cError);
   }
   private String verifyOperate(String szOperate)
   {
     String szReturn = "";
     String szOperates[] = {"PRINT"};
     for(int nIndex = 0; nIndex < szOperates.length; nIndex ++) {
       if( szOperate.equals(szOperates[nIndex]) ) {
         szReturn = szOperate;
       }
     }

     return szReturn;
   }
   /**
    * 统计报表的打印功能
    * @return
    */
   private boolean submitPrint(String codeType)
       throws Exception
   {
     mResult.clear();
     XmlExport xe = new XmlExport();
     logger.debug("sql:"+m_sql);

     LZCardBugetDB tLZCardBugetDB=new LZCardBugetDB();
     m_LZCardBugetSet=tLZCardBugetDB.executeQuery(m_sql);
     printReport(xe, m_LZCardBugetSet);
     mResult.add( xe );
     return true;
   }
   /**
    * 针对于单证统计报表打印
    * @param xe
    * @param aLZCardBugetSet
    * @param
    * @throws Exception
    */
   private void printReport(XmlExport xe, LZCardBugetSet aLZCardBugetSet)
       throws Exception
   {
     xe.createDocument("BugetReport.vts", "printer");
     // put data to xmlexport
     int nLen = 4;  // 列表数据的列数 申请和归档的列数为7，批复的为9  取max
     String[] values = new String[nLen];
     double dMoney = 0.0;  // print cost
     double dSumMoney = 0.0;  // sum print cost
     ListTable lt = new ListTable();
     lt.setName("Buget");
     for(int nIndex = 0; nIndex < aLZCardBugetSet.size(); nIndex ++) {
       LZCardBugetSchema tLZCardBugetSchema = aLZCardBugetSet.get(nIndex + 1);
       values = new String[nLen];
       try{
         values[0] = ReportPubFun.getMngName( tLZCardBugetSchema.getManageCom());
         values[1] = tLZCardBugetSchema.getSDate();
         values[2] = tLZCardBugetSchema.getEDate();
         dMoney=tLZCardBugetSchema.getBuget();
         values[3] = String.valueOf(dMoney);
         dSumMoney += dMoney;
       }catch(Exception ex){
         ex.getMessage();
       }
         lt.add( values );
      }

     values = new String[nLen];
     values[0] = "AppTime";
     values[1] = "AppCom";
     values[2] = "CertifyCode";
     values[3] = "CertifyName";
     xe.addListTable(lt, values);
     TextTag tag = new TextTag();
     tag.add("SumMoney", new DecimalFormat(".00").format(dSumMoney));
     xe.addTextTag(tag);

   }






}
