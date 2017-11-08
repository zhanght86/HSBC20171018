package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;


/**
 * <p>Title: Lis_New</p>
 * <p>Description: 邮保通对帐</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Sinosoft
 * @version 1.0
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.jdom.JDOMException;

import com.f1j.ss.BookModelImpl;
import com.f1j.util.F1Exception;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LJAPayDB;
import com.sinosoft.lis.db.LJAPayPersonDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.YBTEnterLogDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
public class YBTFinfeeBL implements BusinessService
{
private static Logger logger = Logger.getLogger(YBTFinfeeBL.class);

    /** 错误处理类 */
    public  CErrors mErrors = new CErrors();
    /** 往前面传输数据的容器 */
    private VData mResults = new VData();
    /** 往后面传输数据的容器 */
    private VData mInputData;
    /**内存文件暂存*/
    private org.jdom.Document myDocument;
    private org.jdom.Element myElement;
    private GlobalInput mGlobalInput = new GlobalInput();
    private TransferData mTransferData = new TransferData();
    private String mOperateType="";//操作类型
    private String mDutyFlag="0";
    private FileInputStream fi = null;
    private String mFileName="";//文件名
    private String mImportPath="";//文件保存路径
    private double prem=0;//保费
    private String polno="";//保单号
    private String dflag="";//撤单标志
    private String tempfeeno="";//暂交费号
    private String enterdate="";//到帐日期
    private double sumprem = 0 ;//业务系统总金额
    private double FeeSum=0;//前台传入的交费总金额
    private String mBankCode="";//前台传入的收款银行
    private String tCurrentDate;//系统当前日期
    private boolean flag = false;
    private String tSerialno="";

    public YBTFinfeeBL()
    {
    }

    public boolean submitData(VData cInputData,String cOperate) 
    {

        mInputData = (VData)cInputData.clone();
        mOperateType=cOperate;

        //得到前台传入的数据
        if(!getInputData())
        {
            return false;
        }

        //进行业务逻辑验证
        if(!this.checkData())
        {
            return false;
        }

        //进行业务处理，解析excel文件，进行对帐操作
        logger.debug("解析excel文件");
        try {
			if(!parseExcelToDB())
			{
			    return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (F1Exception e) {
			e.printStackTrace();
			return false;
		}


        return true;
    }

//获取前台传递的数据
    private boolean getInputData()
    {
        mGlobalInput = (GlobalInput)mInputData.getObjectByObjectName("GlobalInput",0);
        mTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData",0);
        mFileName = (String)mTransferData.getValueByName("FileName");
        mBankCode = (String)mTransferData.getValueByName("BankCode");
        logger.debug("获得的上传文件的名称是"+mFileName);
        mImportPath= (String)mTransferData.getValueByName("ImportPath");
        logger.debug("获得的上传文件的保存路径是"+mImportPath);
        FeeSum=Double.parseDouble((String)mTransferData.getValueByName("FeeSum"));
        logger.debug("获得的FeeSum是"+FeeSum);
        logger.debug("获得的BankCode是"+mBankCode);

        return true;
    }


    /**
     * 进行业务逻辑验证
     * @return
     */
    private boolean checkData()
    {

        if (mGlobalInput==null)
        {
            // @@错误处理
            CError.buildErr(this, "没有全局变量，请重新登录");
            return false;
        }

        if (mTransferData==null)
        {
        	CError.buildErr(this, "无导入文件信息，请重新导入!");
            return false;
        }


        if (mFileName==null||mFileName=="")
        {
            // @@错误处理
        	CError.buildErr(this, "没有传入上传文件的名称!");
            return false;
        }


        if (mImportPath==null||mImportPath=="")
        {
            // @@错误处理
        	CError.buildErr(this, "没有传入文件的保存的路径!");
            return false;
        }
        
        if (mBankCode==null||mBankCode=="")
        {
            // @@错误处理
        	CError.buildErr(this, "没有传入收款银行代码!");
            return false;
        }

        //校验导入的物理源Xml或EXCEL文件已到指定目的地存在
        if(!this.checkImportFile())
        {
            return false;
        }


        return true;
    }

    //校验上传的文件是否已经保存到对应的路径中
    private boolean checkImportFile()
    {

        logger.debug("上传文件的存储路径是"+(mImportPath+mFileName));
        File tFile = new File(mImportPath+mFileName);
        if (!tFile.exists())
        {
            // @@错误处理
            CError.buildErr(this, "文件并没有存储到制定的路径中,上传失败");
             return false;
        }
        return true;
    }



    //进行业务处理，解析excel文件，将信息存储到数据库中
    private boolean parseExcelToDB()throws IOException, F1Exception
    {
        logger.debug("开始执行Excel文件的解析");
        BookModelImpl bmTemplate = new com.f1j.ss.BookModelImpl();
        bmTemplate.read(mImportPath+mFileName, new com.f1j.ss.ReadParams());

        //循环从Excel文件中获得基本信息
        int allrow=bmTemplate.getLastRow();
        logger.debug("Excel文件一共有"+allrow+"行");
        VData tv = new VData();
        MMap mp = new MMap();
        for (int i = 1; i <=allrow; i++)
        {
            logger.debug("现在是第"+i+"行");
            if(CheckEmpty(bmTemplate,i)==0)
                break;
//            if(bmTemplate.getText(i, 0).substring(0,6).equalsIgnoreCase("MS富贵盈门") )
//         {           
            String sprem = bmTemplate.getText(i,3).trim();
            polno = bmTemplate.getText(i,6).trim();
            dflag = bmTemplate.getText(i,7).trim();
            tempfeeno = bmTemplate.getText(i,10).trim();
            enterdate = bmTemplate.getText(i,12).trim();
            tCurrentDate= PubFun.getCurrentDate();
            if(new FDate().getDate(enterdate)== null)
            {
                CError.buildErr(this,"Excel中第"+i+"条记录的到帐日期格式不符，请使用文本格式,并采用'YYYY-MM-DD'格式");
                InsertLog(mFileName,enterdate,"10","Excel表第"+i+"条记录的到帐日期格式不符",PubFun.getCurrentDate(),PubFun.getCurrentTime());
                return false;
            }
            logger.debug("polno:"+polno+"-------tempfeeno:"+tempfeeno);
            logger.debug("-------enterdate:---------------"+enterdate);
            logger.debug("tCurrentDate:----------------"+tCurrentDate);
            if(dflag.toString().equals("是"))
            {
                prem = Double.parseDouble(sprem);
                logger.debug("撤单的操作");
                LCContDB yLCContDB = new LCContDB();
                yLCContDB.setContNo(polno);
                LCContSet yPolSet = yLCContDB.query();
                if(yPolSet.size()>0)
                {
                    buildError("parseExcelToDB","Excel中第"+i+"条记录是已撤单的保单，但业务系统还有相关记录");
                    InsertLog(mFileName,enterdate,"10","Excel中第"+i+"条记录是已撤单的保单，但业务系统还有相关记录",PubFun.getCurrentDate(),PubFun.getCurrentTime());
                    return false;
                }

            }
            else if(dflag.toString().equals("否"))
            {
                prem = Double.parseDouble(sprem);
                sumprem = sumprem + prem;
                if(enterdate==null || enterdate.equals(""))
                {
                    buildError("parseExcelToDB","Excel中第"+i+"条记录的到帐日期为空");
                    InsertLog(mFileName,enterdate,"10","Excel中第"+i+"条记录的到帐日期为空",PubFun.getCurrentDate(),PubFun.getCurrentTime());
                    return false;
                }
                logger.debug("不撤单的操作");
              //2008-8-6 zy 到帐导入模板中保单号放的是合同号，但是实收个人表中polno字段存放的是lcpol中的polno，所以要进行转换;此外万能险目前险种是一对一
                LCContDB nPolDB = new LCContDB();
                LCContSet nPolSet = new LCContSet();
                nPolDB.setContNo(polno);
                nPolSet = nPolDB.query();
                if(nPolSet.size()<=0)
                {
                	CError.buildErr(this, "Excel中第"+i+"条记录在业务系统没有相关记录");
                	return false;
                }
                String sql1="select * from ljtempfee where tempfeeno='?tempfeeno?' and otherno='?polno?'";
                SQLwithBindVariables sqlbv=new SQLwithBindVariables();
                sqlbv.sql(sql1);
                sqlbv.put("tempfeeno", tempfeeno);
                sqlbv.put("polno", polno);
                logger.debug("查找ljtempfee中记录－－－－－－－－－－－"+sql1);
                LJTempFeeSet nLJTempFeeSet = new LJTempFeeSet();
                LJTempFeeSet uLJTempFeeSet = new LJTempFeeSet();
                LJTempFeeDB nLJTempFeeDB = new LJTempFeeDB();
                nLJTempFeeSet = nLJTempFeeDB.executeQuery(sqlbv);
                if(nLJTempFeeSet.size()<=0)
                {
                    buildError("parseExcelToDB","业务系统中不存在Excel表第"+i+"条记录的相关数据");
                    InsertLog(mFileName,enterdate,"10","LJTempFee不存在Excel表第"+i+"条记录的相关数据",PubFun.getCurrentDate(),PubFun.getCurrentTime());
                    return false;
                }
                double totalprem=0;
                //循环暂收费的记录
                for(int j=1;j<=nLJTempFeeSet.size();j++)
                {
	                LJTempFeeSchema nLJTempFeeSchema = new LJTempFeeSchema();
	                nLJTempFeeSchema = nLJTempFeeSet.get(j);
	                totalprem= totalprem+nLJTempFeeSchema.getPayMoney();
	                if(!(nLJTempFeeSchema.getEnterAccDate()== null ||nLJTempFeeSchema.getEnterAccDate().equals("")))
	                {
	                    buildError("parseExcelToDB","Excel表第"+i+"条记录保单的保费已经进行过核销");
	                    InsertLog(mFileName,enterdate,"10","Excel表第"+i+"条记录在LJTempFee已经进行过核销",PubFun.getCurrentDate(),PubFun.getCurrentTime());
	                    return false;
	                 }
	                 if(!(nLJTempFeeSchema.getConfMakeDate()== null ||nLJTempFeeSchema.getConfMakeDate().equals("")))
	                 {
	                     buildError("parseExcelToDB","Excel表第"+i+"条记录保单的保费已经进行过核销");
	                     InsertLog(mFileName,enterdate,"10","Excel表第"+i+"条记录在LJTempFee已经进行过核销",PubFun.getCurrentDate(),PubFun.getCurrentTime());
	                     return false;
	                 }
	
	                    flag= true;	                   
	                    //将LJTempFee表中到帐日期置为Excel中到帐日期
	                    nLJTempFeeSchema.setEnterAccDate(enterdate);
	                    nLJTempFeeSchema.setConfMakeDate(tCurrentDate);
	                    nLJTempFeeSchema.setConfMakeTime(PubFun.getCurrentTime());
	                    uLJTempFeeSet.add(nLJTempFeeSchema);	                    
                }
                if(Math.abs((prem-totalprem)) >= 0.001)
                {
                	CError.buildErr(this, "Excel表第"+i+"条记录的保费与业务系统中交费金额不符");
                	return false;
                }
                else
                mp.put(uLJTempFeeSet,"UPDATE");
                //将LJTempFeeClass表中到帐日期置为Excel中到帐日期
                logger.debug("查找ljtempfeeclass中记录－－－－－－－－－－－");
                LJTempFeeClassDB nLJTempFeeClassDB = new LJTempFeeClassDB();
                nLJTempFeeClassDB.setTempFeeNo(tempfeeno);
                LJTempFeeClassSet nLJTempFeeClassSet = new LJTempFeeClassSet();
                LJTempFeeClassSet uLJTempFeeClassSet = new LJTempFeeClassSet();
                nLJTempFeeClassSet = nLJTempFeeClassDB.query();
                if(nLJTempFeeClassSet.size()<=0)
                {
                    buildError("parseExcelToDB","业务系统中不存在Excel表第"+i+"条记录的相关数据");
                    InsertLog(mFileName,enterdate,"10","LJTempFeeClass中不存在Excel表第"+i+"条记录的相关数据",PubFun.getCurrentDate(),PubFun.getCurrentTime());
                    return false;
                }
                for(int j=1;j<=nLJTempFeeClassSet.size();j++)
                {
	                LJTempFeeClassSchema nLJTempFeeClassSchema = new LJTempFeeClassSchema();
	                nLJTempFeeClassSchema = nLJTempFeeClassSet.get(j);
	                if(!(nLJTempFeeClassSchema.getEnterAccDate()== null ||nLJTempFeeClassSchema.getEnterAccDate().equals("")))
	                {
	                    buildError("parseExcelToDB","Excel表第"+i+"条记录保单的保费已经进行过核销");
	                    InsertLog(mFileName,enterdate,"10","Excel表第"+i+"条记录在LJTempFeeClass已经进行过核销",PubFun.getCurrentDate(),PubFun.getCurrentTime());
	                    return false;
	                }
	                if(!(nLJTempFeeClassSchema.getConfMakeDate()== null ||nLJTempFeeClassSchema.getConfMakeDate().equals("")))
	                {
	                    buildError("parseExcelToDB","Excel表第"+i+"条记录保单的保费已经进行过核销");
	                    InsertLog(mFileName,enterdate,"10","Excel表第"+i+"条记录在LJTempFeeClass已经进行过核销",PubFun.getCurrentDate(),PubFun.getCurrentTime());
	                    return false;
	                }
	                nLJTempFeeClassSchema.setEnterAccDate(enterdate);
	                nLJTempFeeClassSchema.setConfMakeDate(tCurrentDate);
	                nLJTempFeeClassSchema.setConfMakeTime(PubFun.getCurrentTime());
	                nLJTempFeeClassSchema.setBankCode(mBankCode);
	                uLJTempFeeClassSet.add(nLJTempFeeClassSchema);
	               
                }
                mp.put(uLJTempFeeClassSet,"UPDATE");
                logger.debug("查找LJAPayPerson中记录－－－－－－－－－－－");
                //将LJAPayPerson表中到帐日期置为Excel中到帐日期
                LJAPayPersonDB nLJAPayPersonDB = new LJAPayPersonDB();
                nLJAPayPersonDB.setContNo(polno);
                LJAPayPersonSet nLJAPayPersonSet = new LJAPayPersonSet();
                LJAPayPersonSet uLJAPayPersonSet = new LJAPayPersonSet();
                nLJAPayPersonSet = nLJAPayPersonDB.query();
                if(nLJAPayPersonSet.size()<=0)
                {
                    buildError("parseExcelToDB","业务系统中不存在Excel表第"+i+"条记录的相关数据");
                    InsertLog(mFileName,enterdate,"10","LJAPayPerson中不存在Excel表第"+i+"条记录的相关数据",PubFun.getCurrentDate(),PubFun.getCurrentTime());
                    return false;
                }
                for(int j=1;j<=nLJAPayPersonSet.size();j++)
                {
	                LJAPayPersonSchema nLJAPayPersonSchema = new LJAPayPersonSchema();
	                nLJAPayPersonSchema = nLJAPayPersonSet.get(j);
	                if(!(nLJAPayPersonSchema.getEnterAccDate()==null ||nLJAPayPersonSchema.getEnterAccDate().equals("")))
	                {
	                	CError.buildErr(this, "业务系统中Excel表第"+i+"条记录的个人实收表的到账日期不为空");
	                	return false;
	                }
	                nLJAPayPersonSchema.setEnterAccDate(enterdate);
	                uLJAPayPersonSet.add(nLJAPayPersonSchema);
                }
                mp.put(uLJAPayPersonSet,"UPDATE");
                //将LJAPay表中到帐日期置为Excel中到帐日期
                logger.debug("查找ljapay中记录－－－－－－－－－－－");
                LJAPayDB nLJAPayDB = new LJAPayDB();
                nLJAPayDB.setPayNo(nLJAPayPersonSet.get(1).getPayNo());
                LJAPaySet nLJAPaySet = new LJAPaySet();
                nLJAPaySet = nLJAPayDB.query();
                if(nLJAPaySet.size()<=0)
                {
                    buildError("parseExcelToDB","业务系统中不存在Excel表第"+i+"条记录的相关数据");
                    InsertLog(mFileName,enterdate,"10","LJAPay中不存在Excel表第"+i+"条记录的相关数据",PubFun.getCurrentDate(),PubFun.getCurrentTime());
                    return false;
                }
                LJAPaySchema nLJAPaySchema = new LJAPaySchema();
                nLJAPaySchema = nLJAPaySet.get(1);//payno是主键
                nLJAPaySchema.setEnterAccDate(enterdate);
                nLJAPaySchema.setBankCode(mBankCode);
                mp.put(nLJAPaySchema,"UPDATE");
                
                logger.debug("查找LCcont中记录－－－－－－－－－－－");
                //修改保单表中的客户送达日期为MS财务核销日期
                LCContDB nLCContDB = new LCContDB();
                nLCContDB.setContNo(polno);
                if(nLCContDB.query().size()<=0)
                {
                    
                   buildError("parseExcelToDB","保单表中不存在Excel表第"+i+"条记录的相关数据");
                   InsertLog(mFileName,enterdate,"10","LCcont中不存在Excel表第"+i+"条记录的相关数据",PubFun.getCurrentDate(),PubFun.getCurrentTime());
                   return false;
                }
                LCContSchema nLCContSchema = new LCContSchema();
                nLCContSchema = nLCContDB.query().get(1);
                nLCContSchema.setCustomGetPolDate(tCurrentDate);
                mp.put(nLCContSchema, "UPDATE");
                tv.add(mp);

            }
            else
            {
                logger.debug("没有撤单标志的判断");
                buildError("parseExcelToDB","Excel中第"+i+"条记录的当日撤单一列没有标识");
                InsertLog(mFileName,enterdate,"10","Excel中第"+i+"条记录的当日撤单一列没有标识",PubFun.getCurrentDate(),PubFun.getCurrentTime());
                return false;
            }
//        }
        }
        if(flag)
        {
            if(Math.abs((FeeSum-sumprem)) >= 0.001)
            {
                InsertLog(mFileName,enterdate,"10","Excel中总金额与界面上输入的总金额不一致",PubFun.getCurrentDate(),PubFun.getCurrentTime());
                buildError("parseExcelToDB","Excel中总金额与界面上输入的总金额不一致");
                return false;
            }
            else
            {
                PubSubmit ps = new PubSubmit();
                if(!ps.submitData(tv,null))
                {
                    InsertLog(mFileName,enterdate,"10",ps.mErrors.getFirstError(),PubFun.getCurrentDate(),PubFun.getCurrentTime());
                    CError.buildErr(this, "更新数据失败!"+ps.mErrors.getFirstError());
                }
                else
                {
                    InsertLog(mFileName,enterdate,"11","邮保通到帐导入成功",PubFun.getCurrentDate(),PubFun.getCurrentTime());
                }
            }
        }
        return true;
    }



    private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError( );
        cError.moduleName = "YBTFinfeeBL";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }

    private int CheckEmpty(BookModelImpl bookModelImpl,int i) throws IOException, F1Exception
    {
        int t = bookModelImpl.getLastCol();
        int flg = 0;
        for (int j=0;j<=t;j++)
        {
            String s = bookModelImpl.getText(i,j);
            if(s == null || s.equals(""))
            {
                continue;
            }
            else
            {
                flg=1;
                break;
            }
        }
        return flg;
    }

    public VData getResult()
    {
        return mResults;
    }

    private boolean InsertLog(String tFilename,String tEnterDate ,String tFlag,String tOperDesc,String tMakeDate,String tMakeTime)
    {
        tSerialno = PubFun1.CreateMaxNo("SERIALNO",20);
        YBTEnterLogDB tYBTEnterLogDB = new YBTEnterLogDB();
        tYBTEnterLogDB.setSERIALNO(tSerialno);
        tYBTEnterLogDB.setENTERDATE(tEnterDate);
        tYBTEnterLogDB.setFILENAME(tFilename);
        tYBTEnterLogDB.setFLAG(tFlag);
        tYBTEnterLogDB.setOPERDESC(tOperDesc);
        tYBTEnterLogDB.setMAKEDATE(tMakeDate);
        tYBTEnterLogDB.setMAKETIME(tMakeTime);
//        tYBTEnterLogDB.setSerialno(tSerialno);
//        tYBTEnterLogDB.setEnterDate(tEnterDate);
//        tYBTEnterLogDB.setFilename(tFilename);
//        tYBTEnterLogDB.setFlag(tFlag);
//        tYBTEnterLogDB.setOperDesc(tOperDesc);
//        tYBTEnterLogDB.setMakeDate(tMakeDate);
//        tYBTEnterLogDB.setMakeTime(tMakeTime);
        if(!tYBTEnterLogDB.insert())
        {
            logger.debug("插入YBTEnterLog轨迹表出错");
        }
        return true;
    }


    public static void main(String[] args) throws JDOMException, IOException, F1Exception
    {
        BookModelImpl bmTemplate = new com.f1j.ss.BookModelImpl();
        bmTemplate.read("C:/Documents and Settings/Administrator/桌面/复件 09-13.xls");
        String s=bmTemplate.getFormattedText(1,12).trim();
        logger.debug(s);
        java.util.Date d=null;
        try{
        d=new SimpleDateFormat("MM/dd/yyyy").parse(s);
        logger.debug("date"+d);
        }catch(Exception e){
          e.printStackTrace();
        }
        if(1==1)
            return;

        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput.Operator = "Test";
        tGlobalInput.ManageCom = "8611";
        tGlobalInput.ComCode = "86";

        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("FileName","Book2");
        tTransferData.setNameAndValue("ImportPath","e:/temp/");
        tTransferData.setNameAndValue("FeeSum","305000");

        VData tVData = new VData();
        tVData.add(tGlobalInput);
        tVData.add(tTransferData);

        YBTFinfeeBL tYBTFinfeeBL = new YBTFinfeeBL();
        tYBTFinfeeBL.submitData(tVData,"");
    }


	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
