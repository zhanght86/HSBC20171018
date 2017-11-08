package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDSysVarSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LDSysVarSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: Excel数据转换</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author：ck
 * @version 1.0
 */
public class ExcelChange implements BusinessService
{
private static Logger logger = Logger.getLogger(ExcelChange.class);

    private static String PATH_GRPPOLNO = "/DATASET/TABLE/ROW/FIELD/GrpPolNo[position()=1]";

    //错误处理类，每个需要错误处理的类中都放置该类
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData;
    private GlobalInput tG;

    /** 数据操作字符串 */
    private String mOperate;
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private VData mResult = new VData();

    //应收个人交费表
    private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
    private LJSPayPersonSet mLJSPayPersonInSertSet = new LJSPayPersonSet();
    private LJSPayPersonSet mLJSPayPersonUpdateSet = new LJSPayPersonSet();

    //FIleImport
    private TransferData mTransferData = new TransferData();
    private String grpPolNo;
    private String mImportFileName;
    private String mConfigFileName;
    private String XmlFileName;
    private String mFilePath = "C:/Lis/";
    private String ParseRootPath = "/DATASET/BATCHID";
    private String ParsePath = "/DATASET/TABLE/ROW";
    private String sysvar="PrePayPerson_File";
    private String file="";
    
    private String mPayType;
    //业务处理相关变量
    public ExcelChange()
    {
    }
    //得到文件的路径和名称
    public String getFile()
    {
      return file;
    }
    //传输数据的公共方法
    public boolean submitData(VData cInputData, String cOperate)
    {
        if (!getInputData(cInputData))
        {
            return false;
        }

        if (!dealData())
        {
            return false;
        }

        if (!prepareOutputData())
        {
            return false;
        }

        mInputData = null;

        return true;
    }

    //根据前面的输入数据，进行逻辑处理
    //如果在处理过程中出错，则返回false,否则返回true
    private boolean dealData()
    {
        try
        {
            LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
            tLJSPayPersonSchema.setGrpContNo(grpPolNo);
            tLJSPayPersonSchema.setPayType(this.mPayType);

            LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
            tLJSPayPersonDB.setSchema(tLJSPayPersonSchema);

            LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
            tLJSPayPersonSet = tLJSPayPersonDB.query();
            
            if(tLJSPayPersonSet.size()==0)
            {
              CError tError = new CError();
              tError.moduleName = "CircAutoImportBL";
              tError.functionName = "submitData";
              tError.errorMessage = "请先保存团单下所有个人数据!";
              this.mErrors.addOneError(tError);
              return false;
            }
            LDSysVarSchema tLDSysVarSchema=new LDSysVarSchema();
            tLDSysVarSchema.setSysVar(sysvar);
            LDSysVarDB tLDSysVarDB=new LDSysVarDB();
            tLDSysVarDB.setSchema(tLDSysVarSchema);
            LDSysVarSet tLDSysVarSet=new LDSysVarSet();
            tLDSysVarSet=tLDSysVarDB.query();
            if(tLDSysVarSet.size()==0)
            {
              CError tError = new CError();
              tError.moduleName = "ExcelChange";
              tError.functionName = "deal";
              tError.errorMessage = "请在数据表LDSysVar中配置路径参数!";
              this.mErrors.addOneError(tError);
              return false;
            }
            String filePath=tLDSysVarSet.get(1).getSysVarValue();
            String fileName=tLDSysVarSet.get(1).getSysVar();
            file=filePath+fileName+"_"+mOperate+"_"+CurrentDate+"_"+grpPolNo+".xls";
            FileOutputStream fos = new FileOutputStream(file);
            mResult.add(file);
            
//            FileOutputStream fos = new FileOutputStream("e://temp//test.xls");
            
            //            InputStreamReader read = new InputStreamReader (new FileInputStream(""),"UTF-8");
            //            File tempFile=new File("d:/temp/output.xls");
            //            WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet s = wb.createSheet();
            wb.setSheetName(0, "PrePayPerson 模板");

            //写入文件头
            HSSFRow row = s.createRow(0);

            //第一个字段
            HSSFCell cell0 = row.createCell(Integer.parseInt("0"));
//            cell0.setEncoding(HSSFWorkbook.ENCODING_UTF_16); //支持中文
            cell0.setCellValue(new String("个单号"));
            
            HSSFCell cell1 = row.createCell(Integer.parseInt("1"));
//            cell1.setEncoding(HSSFWorkbook.ENCODING_UTF_16); //支持中文
            cell1.setCellValue(new String("个单险种号"));

            //            cell0.setCellValue("GrpPolNo");
            //第二个字段
            HSSFCell cell2 = row.createCell(Integer.parseInt("2"));
//            cell2.setEncoding(HSSFWorkbook.ENCODING_UTF_16); //支持中文
            cell2.setCellValue("责任编码");

            //第三个字段
            HSSFCell cell3 = row.createCell(Integer.parseInt("3"));
//            cell3.setEncoding(HSSFWorkbook.ENCODING_UTF_16); //支持中文
            cell3.setCellValue("交费计划编码");

            //第四个字段
            HSSFCell cell4 = row.createCell(Integer.parseInt("4"));
//            cell4.setEncoding(HSSFWorkbook.ENCODING_UTF_16); //支持中文
            cell4.setCellValue("交费计划编码名称");

            //第五个字段
            HSSFCell cell5 = row.createCell(Integer.parseInt("5"));
//            cell5.setEncoding(HSSFWorkbook.ENCODING_UTF_16); //支持中文
            cell5.setCellValue("被保人姓名");

            //第六个字段
            HSSFCell cell6 = row.createCell(Integer.parseInt("6"));
//            cell6.setEncoding(HSSFWorkbook.ENCODING_UTF_16); //支持中文
            cell6.setCellValue("应交金额");

            //第七个字段
            HSSFCell cell7 = row.createCell(Integer.parseInt("7"));
//            cell7.setEncoding(HSSFWorkbook.ENCODING_UTF_16); //支持中文
            cell7.setCellValue("实交金额");

            //第八个字段
            HSSFCell cell8 = row.createCell(Integer.parseInt("8"));
//            cell8.setEncoding(HSSFWorkbook.ENCODING_UTF_16); //支持中文
            cell8.setCellValue("保存标记");

            //第九个字段
            HSSFCell cell9 = row.createCell(Integer.parseInt("9"));
//            cell9.setEncoding(HSSFWorkbook.ENCODING_UTF_16); //支持中文
            cell9.setCellValue("被保人性别");

            //第十个字段
            HSSFCell cell10 = row.createCell(Integer.parseInt("10"));
//            cell10.setEncoding(HSSFWorkbook.ENCODING_UTF_16); //支持中文
            cell10.setCellValue("出生日期");

            //第十一个字段
            HSSFCell cell11 = row.createCell(Integer.parseInt("11"));
//            cell11.setEncoding(HSSFWorkbook.ENCODING_UTF_16); //支持中文
            cell11.setCellValue("客户内部号码");

            LJSPayPersonSet tempLJSPayPersonSet = new LJSPayPersonSet();
            String strSql;
            strSql = "select LCPrem.ContNo,LCPrem.PolNo,LCPrem.DutyCode,LCPrem.PayPlanCode,(select PayPlanName from lmdutypay where payplancode = lcprem.payplancode),LCPol.InsuredName,LCPrem.Prem,LJSPayPerson.Sumactupaymoney,LJSPayPerson.InputFlag,(case LCPol.InsuredSex when '0' then '男' when '1' then '女' when '2' then '不祥' else LCPol.InsuredSex end),LCPol.InsuredBirthday from LCPrem,LJSPayPerson,LCPol where LCPol.GrpContNo='?grpPolNo?' ";
            strSql = strSql +
                     " and (LCPrem.UrgePayFlag='N' or LCPrem.UrgePayFlag is null) ";
            strSql = strSql + " and LCPrem.PolNo=LJSPayPerson.PolNo";
            strSql = strSql + " and LCPrem.PolNo=LCPol.PolNo";
            strSql = strSql + " and LCPrem.DutyCode=LJSPayPerson.DutyCode";
            strSql = strSql +
                     " and LCPrem.PayPlanCode=LJSPayPerson.PayPlanCode";
           // strSql = strSql + " and LCPrem.PayPlanCode=LMDutyPay.PayPlanCode";
            strSql = strSql + " and LCPol.appflag='1'";
            strSql = strSql + " and LCPol.paytodate<LCPol.payenddate and ljspayperson.paytype='?paytype?'";
            strSql = strSql + " UNION ";
            strSql = strSql +
                     "select LCPrem.ContNo,LCPrem.PolNo,LCPrem.DutyCode,LCPrem.PayPlanCode,(select PayPlanName from lmdutypay where payplancode = lcprem.payplancode),LCPol.InsuredName,LCPrem.Prem,LCPrem.Prem ,'0',(case LCPol.InsuredSex when '0' then '男' when '1' then '女' when '2' then '不祥' else LCPol.InsuredSex end),LCPol.InsuredBirthday from LCPrem,lcpol where LCPol.GrpContNo='?grpPolNo??' ";
            strSql = strSql +
                     " and (LCPrem.UrgePayFlag='N' or LCPrem.UrgePayFlag is null) ";
            strSql = strSql + " and LCPrem.PolNo=LCPol.PolNo";
           // strSql = strSql + " and LCPrem.PayPlanCode=LMDutyPay.PayPlanCode";
            strSql = strSql +
                     " and 0=(select count(*) from LJSPayPerson where PolNo=LCPrem.PolNo and DutyCode=LCPrem.DutyCode and PayPlanCode=LCPrem.PayPlanCode and paytype='?paytype?')";
            strSql = strSql + " and LCPol.appflag='1'";
            strSql = strSql + " and LCPol.paytodate<LCPol.payenddate";
            //strSql = strSql + " order by 11,5,3";

            SQLwithBindVariables sqlbv=new SQLwithBindVariables();
            sqlbv.sql(strSql);
            sqlbv.put("grpPolNo", grpPolNo);
            sqlbv.put("paytype", this.mPayType);
            ExeSQL mExeSQL = new ExeSQL();
            SSRS mSSRS = new SSRS();
            mSSRS = mExeSQL.execSQL(sqlbv);

            int maxCol = mSSRS.getMaxCol();
            int maxRow = mSSRS.getMaxRow();
            logger.debug("maxCol:" + maxCol);
            logger.debug("maxRow:" + maxRow);

            for (int i = 1; i <= maxRow; i++)
            {
                HSSFRow row_Data = s.createRow(i);

                for (int j = 1; j <= maxCol; j++)
                {
                    HSSFCell cell_Data = row_Data.createCell(Integer.parseInt(String.valueOf(j -
                                                                                                       1)));
                    //个单号最好不要转化，否则在excel上处理会麻烦（能处理）
//                    if(j!=1)
//                      cell_Data.setEncoding(HSSFWorkbook.ENCODING_UTF_16); //支持中文
                    cell_Data.setCellValue(mSSRS.GetText(i, j));
                }
            }

            ///////////////////////////////////////////////////////////////////////////////
            //            if (tLJSPayPersonSet.size() > 0)
            //            {
            //                for (int i = 0; i < tLJSPayPersonSet.size(); i++)
            //                {
            //                    LJSPayPersonSchema mLJSPayPersonSchema = new LJSPayPersonSchema();
            //                    mLJSPayPersonSchema.setSchema(tLJSPayPersonSet.get(i + 1));
            //
            //                    //增加一行的数据
            //                    HSSFRow row_Data = s.createRow(i + 1);
            //                    //个单号
            //                    HSSFCell cell_Data0 = row_Data.createCell(Integer.parseInt(String.valueOf(0)));
            //                    cell_Data0.setEncoding(HSSFWorkbook.ENCODING_UTF_16);//支持中文
            //                    cell_Data0.setCellValue(mLJSPayPersonSchema.getPolNo());
            //                    //责任编码
            //                    HSSFCell cell_Data1 = row_Data.createCell(Integer.parseInt(String.valueOf(1)));
            //                    cell_Data1.setEncoding(HSSFWorkbook.ENCODING_UTF_16);//支持中文
            //                    cell_Data1.setCellValue(mLJSPayPersonSchema.getDutyCode());
            //                    //交费计划编码
            //                    HSSFCell cell_Data2 = row_Data.createCell(Integer.parseInt(String.valueOf(2)));
            //                    cell_Data2.setEncoding(HSSFWorkbook.ENCODING_UTF_16);//支持中文
            //                    cell_Data2.setCellValue(mLJSPayPersonSchema.getPayPlanCode());
            //                    //交费计划编码名称
            //                    HSSFCell cell_Data3 = row_Data.createCell(Integer.parseInt(String.valueOf(3)));
            //                    cell_Data3.setEncoding(HSSFWorkbook.ENCODING_UTF_16);//支持中文
            //                    cell_Data3.setCellValue(mLJSPayPersonSchema.getPayPlanCode());
            //                    //被保人姓名
            //                    HSSFCell cell_Data4 = row_Data.createCell(Integer.parseInt(String.valueOf(4)));
            //                    cell_Data4.setEncoding(HSSFWorkbook.ENCODING_UTF_16);//支持中文
            //                    cell_Data4.setCellValue(mLJSPayPersonSchema.getSumDuePayMoney());
            //                    //应交金额
            //                    HSSFCell cell_Data5 = row_Data.createCell(Integer.parseInt(String.valueOf(5)));
            //                    cell_Data5.setEncoding(HSSFWorkbook.ENCODING_UTF_16);//支持中文
            //                    cell_Data5.setCellValue(mLJSPayPersonSchema.getSumActuPayMoney());
            //                    //实交金额
            //                    HSSFCell cell_Data6 = row_Data.createCell(Integer.parseInt(String.valueOf(5)));
            //                    cell_Data6.setEncoding(HSSFWorkbook.ENCODING_UTF_16);//支持中文
            //                    cell_Data6.setCellValue(mLJSPayPersonSchema.getSumActuPayMoney());
            //                    //保存标记
            //                    HSSFCell cell_Data7 = row_Data.createCell(Integer.parseInt(String.valueOf(5)));
            //                    cell_Data7.setEncoding(HSSFWorkbook.ENCODING_UTF_16);//支持中文
            //                    cell_Data7.setCellValue(mLJSPayPersonSchema.getSumActuPayMoney());
            //                    //被保人性别
            //                    HSSFCell cell_Data8 = row_Data.createCell(Integer.parseInt(String.valueOf(5)));
            //                    cell_Data8.setEncoding(HSSFWorkbook.ENCODING_UTF_16);//支持中文
            //                    cell_Data8.setCellValue(mLJSPayPersonSchema.getSumActuPayMoney());
            //                    //出生年月
            //                    HSSFCell cell_Data9 = row_Data.createCell(Integer.parseInt(String.valueOf(5)));
            //                    cell_Data9.setEncoding(HSSFWorkbook.ENCODING_UTF_16);//支持中文
            //                    cell_Data9.setCellValue(mLJSPayPersonSchema.getSumActuPayMoney());
            //                    //客户内部号码
            //                    HSSFCell cell_Data10 = row_Data.createCell(Integer.parseInt(String.valueOf(5)));
            //                    cell_Data10.setEncoding(HSSFWorkbook.ENCODING_UTF_16);//支持中文
            //                    cell_Data10.setCellValue(mLJSPayPersonSchema.getSumActuPayMoney());
            //                }
            //            }
            wb.write(fos);
            fos.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();

            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "CircAutoImportBL";
            tError.functionName = "submitData";
            tError.errorMessage = "导入文件格式有误!" + ex.getMessage();
            this.mErrors.addOneError(tError);

            return false;
        }

        logger.debug("结束时间:" + PubFun.getCurrentTime());

        return true;
    }

    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData mInputData)
    {
        mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData",
                                                                        0);
        tG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
        mOperate = tG.Operator;

        grpPolNo = (String) mTransferData.getValueByName("GrpPolNo");
        if ((mOperate == null) || mOperate.trim().equals(""))
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ReportJSImportAfterInitService";
            tError.functionName = "checkData";
            tError.errorMessage = "操作员代码丢失！";
            this.mErrors.addOneError(tError);

            return false;
        }

        if ((grpPolNo == null) || grpPolNo.trim().equals(""))
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ReportJSImportAfterInitService";
            tError.functionName = "checkData";
            tError.errorMessage = "请输入团单号码!";
            this.mErrors.addOneError(tError);

            return false;
        }
        
        LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
        tLCGrpPolDB.setGrpContNo(grpPolNo);
        LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
        if(tLCGrpPolSet.size()>0)
        {
        	LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
        	tLMRiskAppDB.setRiskCode(tLCGrpPolSet.get(1).getRiskCode());
        	if(tLMRiskAppDB.getInfo())
        	{
        		if("1".equals(tLMRiskAppDB.getHealthType()))
        		{
        			this.mPayType="TM";
        		}
        		else
        		{
        			this.mPayType="ZC";
        		}
        	}
        }
        
        return true;
    }


    //准备往后层输出所需要的数据
    //输出：如果准备数据时发生错误则返回false,否则返回true
    private boolean prepareOutputData()
    {
        return true;
    }

    public static void main(String[] args) throws Exception
    {
    }
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
	public VData getResult() {
		// TODO Auto-generated method stub
		return mInputData;
	}
}
