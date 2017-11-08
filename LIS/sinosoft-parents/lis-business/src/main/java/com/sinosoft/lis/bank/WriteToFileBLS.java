package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.vbl.*;
import java.sql.*;
import com.sinosoft.lis.pubfun.*;
import java.io.*;
import java.lang.reflect.*;
import java.text.DecimalFormat;

import javax.xml.transform.Source;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 银行数据转换到文件模块</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * @version 1.0
 */

public class WriteToFileBLS {
private static Logger logger = Logger.getLogger(WriteToFileBLS.class);
  //错误处理类，每个需要错误处理的类中都放置该类
  public  CErrors mErrors=new CErrors();
  /** 传入数据的容器 */
  private VData mInputData = new VData();
  /** 数据操作字符串 */
  private String mOperate;

  //业务数据
  private LYSendToBankSet inLYSendToBankSet = new LYSendToBankSet();
  private LYBankLogSet inLYBankLogSet = new LYBankLogSet();
  private TransferData inTransferData = new TransferData();

  private String fileName = "";
  private String bankCode = "";
  private String busstype = "";             //业务类型：S－代收、F－代付
  private String appPath=null;

  public WriteToFileBLS() {
  }

  /**
   * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
   * @param cInputData 传入的数据,VData对象
   * @param cOperate 数据操作字符串，主要包括"WRITE"和""
   * @return 布尔值（true--提交成功, false--提交失败）
   */
  public boolean submitData(VData cInputData, String cOperate) {
    boolean tReturn = false;

    //将操作数据拷贝到本类中
    this.mInputData = (VData)cInputData.clone();
    this.mOperate =cOperate;

    //得到外部传入的数据,将数据备份到本类中
    if (!getInputData()) return false;
    //logger.debug("---End getInputData---");

    //信息保存
    if(this.mOperate.equals("WRITE")) {
      //获取文件名和文件路径
      fileName = (String)inTransferData.getValueByName("fileName") + ".xml";
      String basePath = (String)inTransferData.getValueByName("basePath");
      String subPath = (String)inTransferData.getValueByName("subPath");
      File filePath=new File(basePath,subPath);
      bankCode = (String)inTransferData.getValueByName("bankCode");
      busstype = (String)inTransferData.getValueByName("busstype");
      appPath = (String)inTransferData.getValueByName("appPath");
      logger.debug("filePath: " + filePath + " | bankCode: " + bankCode + " | busstype : " + busstype);
      filePath.mkdirs();

      //生成送银行文件
      if (!writeBankFile(new File(filePath, fileName))) {
    	  return false;
      }

      //转换xml
      String zipFileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".z";
      if (!xmlTransform(new File(filePath, fileName),
    		  new File(filePath, zipFileName))) {
    	  return false;
      }

      //保存银行操作日志
      tReturn = save(cInputData);
    }

    if (tReturn)
      logger.debug("Save sucessful");
    else
      logger.debug("Save failed");

    return tReturn;
  }

  /**
   * 将外部传入的数据分解到本类的属性中
   * @param: 无
   * @return: boolean
   */
  private boolean getInputData()	{
    try {
      inLYSendToBankSet = (LYSendToBankSet)mInputData.getObjectByObjectName("LYSendToBankSet", 0);
      inLYBankLogSet = (LYBankLogSet)mInputData.getObjectByObjectName("LYBankLogSet", 0);
      inTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData", 0);
    }
    catch (Exception e) {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "WriteToFileBLS";
      tError.functionName = "getInputData";
      tError.errorMessage = "接收数据失败!!";
      this.mErrors .addOneError(tError) ;
      return false;
    }

    return true;
  }

  /**
   * 生成送银行文件
   * @param fileName
   * @return
   */
  private boolean writeBankFile(File file) {
    int i;
    PrintWriter out = null;

    try {
      logger.debug("Start creating file ......");
      logger.debug("File Name : " + file.getAbsolutePath());

	out = new PrintWriter(
          new FileWriter(
          file), true);

      out.println("<?xml version=\"1.0\" encoding=\"gbk\"?>");
      out.println("<?xml-stylesheet type=\"text/xsl\" href=\"BankDataFormat.xsl\"?>");
      out.println("");

      // 生成文件头信息
      out.println("<!--");
      out.println(" * <p>FileName: " + fileName + " </p>");
      out.println(" * <p>Description: 业务系统数据转银行系统文件 </p>");
      out.println(" * <p>Copyright: Copyright (c) 2002</p>");
      out.println(" * <p>Company: sinosoft </p>");
      out.println(" * @Author：Minim's WriteToFileBLS");
      out.println(" * @CreateDate：" + PubFun.getCurrentDate());
      out.println("-->");
      out.println("");

      out.println("<BANKDATA>");
      for (i=0; i<inLYSendToBankSet.size(); i++) {
        LYSendToBankSchema tLYSendToBankSchema = inLYSendToBankSet.get(i + 1);

        Class c = tLYSendToBankSchema.getClass();
        Field f[] = c.getDeclaredFields();

        out.println("  <ROW>");

        for(int elementsNum=0; elementsNum<f.length; elementsNum++) {
          out.print("    <" + f[elementsNum].getName() + ">");

          if (f[elementsNum].getName().equals("PayMoney")) {
//            double d = Double.parseDouble(tLYSendToBankSchema.getV(f[elementsNum].getName()));
//            d = Double.parseDouble((new DecimalFormat("0000000000.00")).format(d));
            out.print((new DecimalFormat("0.00")).format(Double.parseDouble(tLYSendToBankSchema.getV(f[elementsNum].getName()))));
          }
          else {
            out.print(tLYSendToBankSchema.getV(f[elementsNum].getName()));
          }

          out.println("</" + f[elementsNum].getName() + ">");
        }

        out.println("  </ROW>");
        out.println("");

        //out.println(tLYSendToBankSchema.encode());
      }
      out.println("</BANKDATA>");

      out.println("");
      out.println("<!-- Create BankFile Success! -->");
      out.close();
    }
    catch(Exception e) {
    	CError.buildErr(this, "Create File Error!");
    	return false;
    }

    return true;
  }

  /**
   * 获取xsl文件路径
   * @return
   */
  public String getXslPath()
  {
      String xslpath = "";

      LDBankDB tLDBankDB = new LDBankDB();
      tLDBankDB.setBankCode(bankCode);
      //如果有该银行数据，则根据业务类型选择对应的发盘模板路径（默认打开代收的路径）
      LDBankSet tLDBankSet= tLDBankDB.query();
      if(tLDBankSet.size()!=1)
    	  throw new RuntimeException("ldbank中的银行配置信息错误");
      LDBankSchema tLDBankSchema = tLDBankSet.get(1);
      xslpath = (busstype.equals("F")) ? tLDBankSchema.getAgentGetSendF() : tLDBankSchema.getAgentPaySendF();
//      logger.debug("xsl : " + xslpath);
      return new File(appPath,xslpath).getAbsolutePath();
  }

  /**
   * Simple sample code to show how to run the XSL processor
   * from the API.
   */
  public boolean xmlTransform(File fSource, File fTarget) {
    // Have the XSLTProcessorFactory obtain a interface to a
    // new XSLTProcessor object.
    try {
      String xslPath = getXslPath();
      logger.debug("xslPath:" + xslPath);

      File fStyle = new File(xslPath);

      Source source = new StreamSource(new InputStreamReader(new FileInputStream(fSource)));
      Result result = new StreamResult(new FileOutputStream(fTarget));
      Source style = new StreamSource(fStyle);

      //Create the Transformer
      TransformerFactory transFactory = TransformerFactory.newInstance();
      Transformer transformer = transFactory.newTransformer(style);

      //Transform the Document
      transformer.transform(source, result);

      logger.debug("Transform Success!");
    }
    catch(Exception e) {
      e.printStackTrace();
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "WriteToFileBLS";
      tError.functionName = "SimpleTransform";
      tError.errorMessage = "Xml处理失败 - " + e.toString();
      this.mErrors .addOneError(tError);
      return false;
    }
    return true;
  }

  /**
   * 保存操作
   * @param mInputData
   * @return
   */
  private boolean save(VData mInputData) {
    int i;
    boolean tReturn =true;
    logger.debug("Start Save...");

    //建立数据库连接
    Connection conn=DBConnPool.getConnection();
    if (conn == null) {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "WriteToFileBLS";
      tError.functionName = "saveData";
      tError.errorMessage = "数据库连接失败!";
      this.mErrors .addOneError(tError) ;
      return false;
    }

    try {
      //开始事务，锁表
      conn.setAutoCommit(false);

      //记录银行操作日志
      LYBankLogDBSet tLYBankLogDBSet = new LYBankLogDBSet(conn);
      tLYBankLogDBSet.set(inLYBankLogSet);
      if (!tLYBankLogDBSet.update()) {
        try{ conn.rollback() ;} catch(Exception e){}
        conn.close();
        logger.debug("LYBankLog Insert Failed");
        CError.buildErr(this, "LYBankLog Insert Failed");
        return false;
      }

      conn.commit();
      conn.close();
      logger.debug("End Committed");
    }
    catch (Exception ex) {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "WriteToFileBLS";
      tError.functionName = "submitData";
      tError.errorMessage = ex.toString();
      this.mErrors .addOneError(tError);
      try{ conn.rollback() ;} catch(Exception e){}
      tReturn=false;
    }
    return tReturn;
  }

  public static void main(String[] args) {
    WriteToFileBLS writeToFileBLS1 = new WriteToFileBLS();
    writeToFileBLS1.bankCode = "0341";
    writeToFileBLS1.getXslPath();
  }
}
