package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMCalModeDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LMCalModeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * @author Administrator
 *
 */
public class CashValueTablePrintBL {
private static Logger logger = Logger.getLogger(CashValueTablePrintBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors=new CErrors();
	private VData mResult = new VData();
	
	private ListTable tlistTable;
	
	private String mContNo = null;

    private GlobalInput mGlobalInput = new GlobalInput();

	public CashValueTablePrintBL() {
		// TODO Auto-generated constructor stub
	}

	  /**
    传输数据的公共方法
    */
    public boolean submitData(VData cInputData, String cOperate)
    {
//    	 得到外部传入的数据，将数据备份到本类中
    	if( !getInputData(cInputData) ) {
    	 return false;
    	}
    	
    	mResult.clear();
//    	 准备所有要打印的数据
    	if( !queryData() ) {
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
    	mContNo = (String) cInputData.get(0);
    	
        mGlobalInput.setSchema( (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 1));
        
        if (mContNo == null && mContNo.equals("")) {
        	CError.buildErr(this, "数据传输得到保单号失败! ");
        }
        
        return true;
    }
    
    /**
     * 生成保全变更后的现金价值表
     * @param tLPEdorMainSchema
     * @return
     */
     @SuppressWarnings("unchecked")
	private boolean setCashXml(String tContNo)
     {
         try
         {
             logger.debug("\n\nStart Set Cash Value");

             XmlExportNew xmlExport = new XmlExportNew(); //新建一个XmlExport的实例
//             xmlexport.createDocument("PrtEndorseCashValue.vts", "printer"); //最好紧接着就初始化xml文档
             xmlExport.createDocument("现金价值表");
             
             xmlExport.addDisplayControl("displayHead");

             TextTag textTag = new TextTag(); //新建一个TextTag的实例
             textTag.add("ContNo", tContNo);

             textTag.add("ConfirmOperator", mGlobalInput.Operator);

             xmlExport.addDisplayControl("displayTail");

             xmlExport.addDisplayControl("displayIC");

             //得到所有主附险的现价和
             LCPolDB tLCPolDB = new LCPolDB();
             SQLwithBindVariables sqlbv = new SQLwithBindVariables();
             String tCashValueSQL = "select * from lcpol where contno='"+"?tContNo?"+"' and appflag = '1' ";
             sqlbv.sql(tCashValueSQL);
             sqlbv.put("tContNo", tContNo);
             LCPolSet tLCPolSet = tLCPolDB.executeQuery(sqlbv);

             String[] strCashValue = new String[110];
             int maxnum = 0;
             String strRiskName = "";
             for (int i = 1; i <= tLCPolSet.size(); i++)
             {
            	 LCPolSchema tLCPolSchema = new LCPolSchema();
            	 tLCPolSchema = tLCPolSet.get(i);

                 LMCalModeDB tLMCalModeDB = new LMCalModeDB();
                 tLMCalModeDB.setRiskCode(tLCPolSchema.getRiskCode());
                 tLMCalModeDB.setType("X");
                 LMCalModeSet tLMCalModeSet = tLMCalModeDB.query();

                 if ((tLMCalModeSet.size() != 1)
                         || (tLMCalModeSet.get(1).getCalSQL() == null)
                         || tLMCalModeSet.get(1).getCalSQL().equals(""))
                 {
                     logger.debug("保单(" + tLCPolSchema.getPolNo() + ")没有现价!");
                     continue;
                 }
                 logger.debug("开始计算保单(" + tLCPolSchema.getPolNo() + ")现价!");

                 Calculator calculator = new Calculator();

                 // 设置基本的计算参数,2005-1-19,重新更新与保单打印同步
				calculator.addBasicFactor("InsuredSex", tLCPolSchema.getInsuredSex());
				calculator.addBasicFactor("InsuredAppAge", String.valueOf(tLCPolSchema.getInsuredAppAge()));
				calculator.addBasicFactor("PayIntv", String.valueOf(tLCPolSchema.getPayIntv()));
				calculator.addBasicFactor("PayEndYear", String.valueOf(tLCPolSchema.getPayEndYear()));
				calculator.addBasicFactor("PayEndYearFlag", String.valueOf(tLCPolSchema.getPayEndYearFlag()));
				calculator.addBasicFactor("CValiDate", String.valueOf(tLCPolSchema.getCValiDate()));
				calculator.addBasicFactor("SignDate", String.valueOf(tLCPolSchema.getSignDate()));
				calculator.addBasicFactor("PayYears", String.valueOf(tLCPolSchema.getPayYears()));
				calculator.addBasicFactor("InsuYear", String.valueOf(tLCPolSchema.getInsuYear()));
				calculator.addBasicFactor("InsuYearFlag", String.valueOf(tLCPolSchema.getInsuYearFlag()));
				calculator.addBasicFactor("GetYear", String.valueOf(tLCPolSchema.getGetYear()));
				calculator.addBasicFactor("Prem", String.valueOf(tLCPolSchema.getPrem()));
				calculator.addBasicFactor("StandPrem", String.valueOf(tLCPolSchema.getStandPrem()));
				calculator.addBasicFactor("PolNo", tLCPolSchema.getPolNo());
				calculator.addBasicFactor("Amnt", String.valueOf(tLCPolSchema.getAmnt()));
				calculator.addBasicFactor("FloatRate", String.valueOf(tLCPolSchema.getFloatRate()));
				calculator.addBasicFactor("GetYearFlag", String.valueOf(tLCPolSchema.getGetYearFlag()));
				
				calculator.setCalCode(tLMCalModeSet.get(1).getCalCode());
				
				String strSQL = calculator.getCalSQL() + " order by curyear ";
				logger.debug(strSQL);
				
				TransferData CalSQLParams = calculator.getCalSQLParams();
				VData tVData = new VData();
				tVData.add(0,strSQL);
				tVData.add(1,CalSQLParams);
                 try
                 {
                     SSRS tSSRS = new SSRS();
                     ExeSQL tExeSQL = new ExeSQL();
                     tSSRS = tExeSQL.execSQL(tVData);

                     int num = 0;
                     for (int k=1;k<=tSSRS.getMaxRow();k++)
                     {
                         if ((strCashValue[num] == null)
                                 || strCashValue[num].equals(""))
                         {
                             strCashValue[num] = tSSRS.GetText(k, 2);
                         }
                         else
                         {
                             strCashValue[num] = String.valueOf(Double.parseDouble(tSSRS.GetText(k, 2))+Double.parseDouble(strCashValue[num]));
                         }
                         //格式化处理
                         strCashValue[num] = String.valueOf(PubFun.round(Double.parseDouble(strCashValue[num]),2));
                         num++;
                     }

                     //取得现价记录数
                     if (num > maxnum)
                     {
                         maxnum = num;
                     }
                 }
                 catch (Exception ex)
                 {
                	 ex.printStackTrace();
                 }

                 //显示险种名称
                 LMRiskDB tLMRiskDB = new LMRiskDB();
                 tLMRiskDB.setRiskCode(tLCPolSchema.getRiskCode());

                 if (!tLMRiskDB.getInfo())
                 {
                     throw new Exception("获取险种数据失败！");
                 }
                 strRiskName = strRiskName + tLMRiskDB.getRiskName();
             }
             
             if (maxnum <= 0 ) {
            	 CError.buildErr(this, "此保单下无现价表! ");
            	 return false;
             }

             int averageCount = ((maxnum - 3) / 5) + 1;
             int col = 0;
             String[] strArr = null;

             //modify by jiaqiangli 2009-04-20 从curyear=1开始计算
             textTag.add("ICYear1", strCashValue[1]);
             textTag.add("ICYear2", strCashValue[2]);
             tlistTable = new ListTable();
             tlistTable.setName("IC0");

             for (int i = 3; i < maxnum; i++)
             {
                 strArr = new String[2];
                 strArr[0] = Integer.toString(i);
                 strArr[1] = strCashValue[i];

                 //增加一行
                 tlistTable.add(strArr);
                 if (((i - 1) > ((col + 1) * averageCount)) && (col < 4))
                 {
                     col = col + 1;

                     String[] strArrTitle = {"保险单年度末", "现金价值"};
                     int blankLineNum = 20 - averageCount;

                     for (int j = 0; j < blankLineNum; j++)
                     {
                         strArr = new String[2];
                         strArr[0] = " ";
                         strArr[1] = " ";

                         //增加一行
                         tlistTable.add(strArr);
                     }

                     xmlExport.addListTable(tlistTable, strArrTitle);

                     tlistTable = new ListTable();
                     tlistTable.setName("IC" + col);
                 }
             }

             String[] strArrTitle = {"保险单年度末", "现金价值"};
             xmlExport.addListTable(tlistTable, strArrTitle);

             //记录险种名称
             textTag.add("ICRiskName", strRiskName);
             xmlExport.addTextTag(textTag);
             
             if (textTag.size()>0)
     	    	xmlExport.addTextTag(textTag);
             
     	     mResult.addElement(xmlExport);
         }
         catch (Exception e)
         {
             e.printStackTrace();

             // @@错误处理
             CError.buildErr(this, "数据提交失败! " + e.getMessage());
             return false;
         }

         return true;
     }

    private boolean queryData()
    {
    	try {
    		if (setCashXml(this.mContNo) == false) {
    			return false;
    		}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AgentDetailBL";
			tError.functionName = "queryData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			return false;
		}

    	return true;
    }

    public VData getResult()
    {
      return this.mResult;
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}
}
