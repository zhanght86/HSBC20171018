package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

import org.jdom.*;

import java.io.*;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.certify.*;

import java.util.*;
import java.lang.reflect.*;

import com.sinosoft.lis.tb.TBPubFun;
import com.sinosoft.lis.tb.BPODataContainer;
import com.sinosoft.lis.finfee.FineRiskBasicInfo;
import com.sinosoft.lis.finfee.TempFeeUI;
import com.sinosoft.lis.finfee.FineTBXMLTransfer;

/**
 * <p>Title: FineBPODealInputDataBL</p>
 * <p>Description: 财务外包数据导入处理</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: SinoSoft</p>
 * @author ln
 * @version 1.0
 */

public class FineBPODealInputDataBL
{
private static Logger logger = Logger.getLogger(FineBPODealInputDataBL.class);

  /**数据处理类*/
  public CErrors mErrors = new CErrors();
  /**需要处理的数据文件集合*/
  private String[] mNeedDealFiles;
  
  /**如果没有定义外包方：外包返回财务收费单数据路径集合
   * added 2008*/
  private String[] mBPOInputPaths;

  /**如果定义外包方：外包返回财务收费单数据路径*/
  private String mBPOInputPath = "";
  
  /**外包方
   * added 2008*/
  private String tOperator = "";
  
  /**一批最大处理文件数*/
  private int mBPOMaxCount = 1;

  /**XML标签常量*/
  private static final String XML_ONEPOLDATA = "OnePolData";

  public FineBPODealInputDataBL()
  {
  }
  
  public void setBPOInputPath(String tBPOInputPath)
  {
	  this.mBPOInputPath = tBPOInputPath;
  }
  
  /**
   * 传输数据的公共方法
   * @param: cInputData 输入的数据
   *         cOperate 数据操作
   * @return:
   */
  public boolean submitData(VData cInputData,String cOperate)
  {
	//modified by ln 2008.5.22 2008-06-26
	 /**
	  * tOperator为自动运行定义的参数
	  * 如果定义了参数则执行else:读取该外包方下的文件
	  * 如果没有定义则执行if:循环执行所有外保方下的文件
	  */
	  TransferData tTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData",0); 
	  tOperator = (String) tTransferData.getValueByName("tOperator");
      //test
//	  tOperator = "WB002";	  
	  	 
		  if(tOperator == null || "".equals(tOperator))
	      {
			  logger.debug("******BPO:N ");
		    if(!getConfigDataN())
			     return false;  
			  
			if(!DealInputDataN())
			     return false;	
	         
	      }
		  else
		  {
			if(!getInputData())
			      return false;		
			
			//处理数据文件
			if(!dealData())
		  	      return false;
		  } 
		  
     return true;
  
	
   //modified by ln 2008.5.22 2008-06-26
  }
  
  
  /**
   * DealInputDataN added by ln 2008.5.23
   * 实现对所有的外包方进行循环处理的功能
   * 从输入数据中得到所有外包方路径
   * 输出：如果所有路径下都没有需要处理的文件或处理过程出错，则返回false,否则返回true
   */
  public boolean DealInputDataN()
  {
	//added by ln 2008.5.23	 
	  try
		{
			//是否有需要处理的数据文件的标志
			boolean state1 = false;
			//是否有需要处理的数据的标志
			boolean state2 = false;
			
		    //取得一定数量的外包方返回的文件
			for( int i = 0; i < mBPOInputPaths.length; i++ )
		    {
				mBPOInputPath = mBPOInputPaths[i];
				//test
//				mBPOInputPath = "D:/inputdata/WB001/";
				mNeedDealFiles = TBPubFun.getFilesList(mBPOInputPath,mBPOMaxCount);
				if( mNeedDealFiles != null )
			    {
			      state1 = true;
			    }
				else 
				  continue;
				
				if( mNeedDealFiles.length !=0 )
			    {
			      state2 = true;
			      
			      //处理数据
			      logger.debug("****系统定义外包方数据返回的路径：  "+mBPOInputPath);
			      //处理数据文件
				  if(!dealData())
				        return false;		      
			      
			    }
		    }
				
		    if( state1 == false )
			{
		    	CError.buildErr(this, "没有需要处理的数据文件或者系统定义外包方数据返回的路径错误!") ;
		        
		        return false;
			 }
		    
		    if( state2 == false )
		    {
		    	CError.buildErr(this, "没有需要处理的数据!") ;
		      
		      return false;
		    }      
	     		    
		    return true;
		    
		}
		catch(Exception ex)
	    {
			CError.buildErr(this, ex.toString()) ;
	      
	      return false;
	    }        
	    	    
  }
  //added by ln 2008.5.23
 
  
  /**
   * 数据操作类业务处理
   * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  private boolean dealData()
  {
    try
    {
      //循环处理每一个数据文件
      logger.debug("******需要处理的文件数量 : "+mNeedDealFiles.length);
      for(int i=0;i<mNeedDealFiles.length;i++)
      {
        String tFileName = mNeedDealFiles[i];
        logger.debug("******FileName: "+tFileName);
        if(tFileName == null || "".equals(tFileName))
        {
          continue;
        }
        //产生每一个数据文件对应的Document
        Document tOneFileData = TBPubFun.produceXmlDoc(mBPOInputPath+tFileName);
        if(tOneFileData != null)
        {
          if(!DealOneFile(tOneFileData,tFileName))
              return false;
        }
      }
     }
     catch(Exception ex)
     {
    	 CError.buildErr(this, ex.toString()) ;
       
       return false;
     }
     return true;
  }

  
  /**
   *
   * @param tAllBOPPol
   * @param tOneFileData
   * @return
   */
  private boolean getAllBOPPolData(BPODataContainer tAllBOPPol,Document tOneFileData)
  {
    try
    {
      FineTBXMLTransfer TBXMLTransfer1 = new FineTBXMLTransfer();  //将XML转换为VData
      BPOMissionStateSchema tBPOMissionStateSchema = new BPOMissionStateSchema();
      LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
      VData schemaData = new VData();

      //转化并保存每一张财务收费单的数据
      List tAllPolData = tOneFileData.getRootElement().getChildren(XML_ONEPOLDATA);
      int tPolCount = tAllPolData.size();
      logger.debug("***财务收费单数量： "+tPolCount+"");
      //1.循环处理每一张财务收费单的数据
      for(int i=0;i<tPolCount;i++)
      {
        Element tOnePolData = (Element)tAllPolData.get(i);
        if( tOnePolData != null )
        {
          schemaData = new VData();
          String tBussNo = ""; //业务号,单证印刷号          
          schemaData = TBXMLTransfer1.XMLToVData(tOnePolData);
          if(schemaData == null)
          {
            mErrors.copyAllErrors(TBXMLTransfer1.mErrors);
            return false;
          }        
          
          tBPOMissionStateSchema = (BPOMissionStateSchema)schemaData.getObjectByObjectName("BPOMissionStateSchema",0);
          tBussNo = tBPOMissionStateSchema.getBussNo();         
          
          //保存每一个财务收费单的VData和XML
          tAllBOPPol.add(tBussNo,schemaData,tOnePolData,"");          
        }
      }
    }catch(Exception ex)
    {
    	CError.buildErr(this, ex.toString()) ;
      logger.debug(ex.toString());
      
      return false;
    }

    return true;
  }

  
  /**
   * 得到所有已经在核心业务系统中存在的财务收费单（可能是手工录入）
   * @param tAllBOPPol
   * @param tImportedBOPPol
   * @return
   */
  private boolean getImportedBOPPolData(BPODataContainer tAllBOPPol,BPODataContainer tImportedBOPPol)
  {
    try
    {
      //获得所有的业务号码集合（承保时是印刷号）
      Vector tBussNoData = tAllBOPPol.getBussNoData();
      String []tDelBussNoSet = new String[tBussNoData.size()];  //记录需要删除的财务收费单的印刷号
      int k=0; //计数
      logger.debug("***所有财务收费单数量(处理getImportedBOPPolData前)： "+tBussNoData.size());

      if(tBussNoData.size()==0)
      {
        logger.debug("***所有财务收费单数量为0，无需处理已经导入的财务收费单!");
        return true;
      }
      for(int i=0;i<tBussNoData.size();i++)
      {
        String tBussNo = (String)tBussNoData.get(i);
        logger.debug("tBussNo: "+i+"  "+tBussNo);
        
       //如果该单返回之前已经被人手工录入
        ExeSQL tExeSQL = new ExeSQL();
        String SQL = "select 1 from LJTempFee where TempFeeNo='?tBussNo?'"
                   +" union "
                   +"select 1 from LJTempFeeClass where TempFeeNo='?tBussNo?'"
                   +" union "
                   +" select 1 from BPOMissionState where BussNo='?tBussNo?' and BussNoType='OF' and State <>'2'";  //不考虑已删除的异常件
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(SQL);
        sqlbv.put("tBussNo", tBussNo);
        SSRS tSSRS = tExeSQL.execSQL(sqlbv);
        int max=tSSRS.getMaxRow();
        logger.debug("***"+tBussNo+"已经被处理过的记录数：" + max);
        if(max>0)
        {
          tImportedBOPPol.add(tBussNo,tAllBOPPol.getschemaData(tBussNo),tAllBOPPol.getelementData(tBussNo),"");
//          tAllBOPPol.removeByName(tBussNo);
          tDelBussNoSet[k++] = tBussNo;
        }
      }
      //从全体财务收费单数据中删除已经导入的财务收费单
      for(int m=0;m<tDelBussNoSet.length;m++)
      {
        if(!"".equals(tDelBussNoSet[m]))
        {
          tAllBOPPol.removeByName(tDelBussNoSet[m]);
        }
      }

      tBussNoData = tAllBOPPol.getBussNoData();
      logger.debug("***所有财务收费单数量getImportedBOPPolData(处理后)： "+tBussNoData.size());
      Vector tBussNoData1 = tImportedBOPPol.getBussNoData();
      logger.debug("***已经在系统存在的财务收费单数量(getImportedBOPPolData)： "+tBussNoData1.size());
    }catch (Exception ex)
    {
    	CError.buildErr(this, ex.toString()) ;
      logger.debug(ex.toString());
      
      return false;
    }
    return true;
  }

  
  /**
   * 从原始得到异常件
   * @param tAllBOPPol
   * @param tQuestBOPPol
   * @return
   */
  private boolean getQuestBOPPolData(BPODataContainer tAllBOPPol,BPODataContainer tQuestBOPPol)
  {
    try
    {
      //获得所有的业务号码集合（承保时是印刷号）
      Vector tBussNoData = tAllBOPPol.getBussNoData();
      if(tBussNoData.size()==0)
      {
        logger.debug("***所有财务收费单数量为0，无需处理异常件(getQuestBOPPolData处理前)： "+tBussNoData.size());
        return true;
      }
      String []tDelBussNoSet = new String[tBussNoData.size()];  //记录需要删除的财务收费单的印刷号
      int k=0; //计数
      VData tVData ;
      BPOMissionStateSchema tBPOMissionStateSchema;
      logger.debug("***所有财务收费单数量(getQuestBOPPolData处理前)： "+tBussNoData.size());
      for(int i=0;i<tBussNoData.size();i++)
      {
        //校验是否是异常件
        String tBussNo = (String)tBussNoData.get(i);
        tVData = (VData)tAllBOPPol.getschemaData(tBussNo);
        tBPOMissionStateSchema = (BPOMissionStateSchema)tVData.getObjectByObjectName("BPOMissionStateSchema",0);
        //外包方返回可处理异常件 和 外包方返回不可处理的异常件
        if("02".equals(tBPOMissionStateSchema.getDealType()) || "03".equals(tBPOMissionStateSchema.getDealType()))
        {
          tQuestBOPPol.add(tBussNo,tAllBOPPol.getschemaData(tBussNo),tAllBOPPol.getelementData(tBussNo),"");
//          tAllBOPPol.removeByName(tBussNo);
          tDelBussNoSet[k++] = tBussNo;
        }
      }
      //从全体财务收费单数据中删除异常件
      for(int m=0;m<tDelBussNoSet.length;m++)
      {
        if(!"".equals(tDelBussNoSet[m]))
        {
        tAllBOPPol.removeByName(tDelBussNoSet[m]);
      }
      }
      tBussNoData = tAllBOPPol.getBussNoData();
      logger.debug("***所有财务收费单数量(getQuestBOPPolData处理后)： "+tBussNoData.size());
      Vector tBussNoData1 = tQuestBOPPol.getBussNoData();
      logger.debug("***原始异常件数量： "+tBussNoData1.size());
    }catch (Exception ex)
    {
    	CError.buildErr(this, ex.toString()) ;
      logger.debug(ex.toString());
      
      return false;
    }
    return true;
  }

  
  /**
   *
   * @param tAllBOPPol
   * @param tQuestBOPPol
   * @return
   */
  private boolean getNeedCheckBOPPolData(String tBPOID,BPODataContainer tAllBOPPol,BPODataContainer tNeedCheckBOPPol)
  {
    try
    {
      //此函数处理分两部：1。准备满足抽检条件的财务收费单数据    2。按照抽检规则从满足抽检条件的财务收费单中确定抽检件
      //1.准备满足抽检条件的财务收费单数据 :如果抽检条件中没有险种的限制，则取全部传入的清洁件；如果有险种限制，将包含该险种的财务收费单挑选出来
      //判断是否存在复核抽检规则
      BPODataContainer tAllCheckBOPPol = new BPODataContainer();  //可以抽检的全部财务收费单集合 ，其含义为：可以抽检的全部财务收费单集合 ＝ 清洁件－已经在核心业务系统中的财务收费单
      BPOCheckCalModeSchema tBPOCheckCalModeSchema = new BPOCheckCalModeSchema();
      BPOCheckCalModeDB tBPOCheckCalModeDB = new BPOCheckCalModeDB();
      tBPOCheckCalModeDB.setBPOID(tBPOID);
     // tBPOCheckCalModeDB.setBussNoType("OF");
      //如果没有抽检规则，则不需要抽检
      if(!tBPOCheckCalModeDB.getInfo())
      {
        logger.debug("***"+tBPOID+"没有抽检规则，本次不需要抽检");
        return true;
      }
      tBPOCheckCalModeSchema.setSchema(tBPOCheckCalModeDB.getSchema());

      //判断当天抽检财务收费单数量是否已经达到最大抽检数量
      int tMaxNum = tBPOCheckCalModeSchema.getMaxMum();  //抽检上限
      float tRate = (float)tBPOCheckCalModeSchema.getRate();  //抽检比例
      logger.debug("***抽检上限 : "+tMaxNum);
      logger.debug("***抽检比例 : "+tRate);
      //如果抽检上限为0，则不需要抽检
      if(tMaxNum<1)
      {
        logger.debug("***抽检上限小于1，本次不需要抽检");
        return true;
      }
      ExeSQL tExeSQL = new ExeSQL();
      String tSQL = "select (case when count(1) is not null then count(1) else 0 end) from BPOMissionState where DealType = '01' and MakeDate = '?MakeDate?' and bussnotype='OF' and bpoid='?tBPOID?'";
      SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
      sqlbv1.sql(tSQL);
      sqlbv1.put("MakeDate", PubFun.getCurrentDate());
      sqlbv1.put("tBPOID", tBPOID);
      int tAlreadyCheckCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv1));//当天已经确定为抽检件的财务收费单数量
      logger.debug("***当天已经确定为抽检件的财务收费单数量 : "+tAlreadyCheckCount);
      if(tAlreadyCheckCount >= tMaxNum)
      {
        logger.debug("***当天已经确定为抽检件的财务收费单数量达到抽检上限，本次不需要抽检");
        return true;
      }
      //当天最大还需要抽检的财务收费单数量
      int tNeedCheckCount = tMaxNum - tAlreadyCheckCount;

      //获得所有的业务号码集合（承保时是印刷号）
      Vector tBussNoData = tAllBOPPol.getBussNoData();
      logger.debug("***所有财务收费单数量(getNeedCheckBOPPolData处理前)： "+tBussNoData.size());
      if(tBussNoData.size()==0)
      {
        logger.debug("***所有财务收费单数量为0，无需处理异常件(getQuestBOPPolData处理前)： "+tBussNoData.size());
        return true;
      }
      logger.debug("***tBPOCheckCalModeSchema.ManageCom : "+StrTool.cTrim(tBPOCheckCalModeSchema.getManageCom()));
      logger.debug("***tBPOCheckCalModeSchema.RiskCode : "+StrTool.cTrim(tBPOCheckCalModeSchema.getRiskCode()));
      //如果抽检不区分管理机构
      if("".equals(StrTool.cTrim(tBPOCheckCalModeSchema.getManageCom())))
      {
        //不区分险种，则抽检财务收费单全集为此函数参数传入的所有清洁件
        
          for(int i=0;i<tBussNoData.size();i++)
          {
            String tBussNo = (String)tBussNoData.get(i);
            tAllCheckBOPPol.add(tBussNo,tAllBOPPol.getschemaData(tBussNo),tAllBOPPol.getelementData(tBussNo),"");
          }  
          
      }
      else
      {
        String tManageCom = StrTool.cTrim(tBPOCheckCalModeSchema.getManageCom());

          for(int i=0;i<tBussNoData.size();i++)
           {
             String tBussNo = (String)tBussNoData.get(i);
             VData tVData = (VData)tAllBOPPol.getschemaData(tBussNo);
             LJTempFeeSchema tLJTempFeeSchema = (LJTempFeeSchema)tVData.getObjectByObjectName("LJTempFeeSchema",0);
             logger.debug("***tLJTempFeeSchema.ManageCom : "+StrTool.cTrim(tLJTempFeeSchema.getManageCom()));
             if(StrTool.cTrim(tLJTempFeeSchema.getManageCom()).startsWith(tManageCom))
             {
               tAllCheckBOPPol.add(tBussNo,tAllBOPPol.getschemaData(tBussNo),tAllBOPPol.getelementData(tBussNo),"");
             }
          }
        
      }

      //2。按照抽检规则从满足抽检条件的财务收费单中确定抽检件
      Vector tCheckBussNoData = tAllCheckBOPPol.getBussNoData();
      int tAllCheckCount = tCheckBussNoData.size();
      logger.debug("***所有满足抽检条件的财务收费单数量(getNeedCheckBOPPolData处理前)： "+tAllCheckCount);
      if(tAllCheckCount == 0)
      {
        logger.debug("***本次不需要抽检");
        return true;
      }
      //如果满足抽检条件的保单数量为1，则扩大一百倍进行抽检
	  if (tAllCheckCount == 1) {
		tAllCheckCount = tAllCheckCount*100;
	  }

      String []tDelBussNoSet = new String[tBussNoData.size()];  //记录需要删除的财务收费单的印刷号
      int k=0; //计数

      //确定抽检件
      int tCheckLevel = Math.round(tAllCheckCount * tRate);  //随即抽检基准 ＝ 抽检数×抽检比例 ，并且按照四舍五入取整
      logger.debug("tCheckLevel: "+tCheckLevel);
      Random tRandom = new Random();

      for(int i=0;i<tCheckBussNoData.size();i++)
      {
        int j = tRandom.nextInt(tAllCheckCount);  //随机产生一个0 到 所有满足抽检条件的财务收费单数量的值，如果该值在 0 与 随即抽检基准 之间，确定为抽检件
        logger.debug("***tBussNo： "+(String)tCheckBussNoData.get(i) + "   RandNo: "+ j +"   已确定的抽检数:  "+k);
        if( j<= tCheckLevel && (k+1)<= tNeedCheckCount && (k+1)<= tCheckLevel)  //如果抽检数量已经大于”随即抽检基准“，直接跳出
        {
          String tBussNo = (String)tCheckBussNoData.get(i);
          tNeedCheckBOPPol.add(tBussNo,tAllCheckBOPPol.getschemaData(tBussNo),tAllCheckBOPPol.getelementData(tBussNo),"");
//          tAllBOPPol.removeByName(tBussNo);
          tDelBussNoSet[k++] = tBussNo;
        }
      }
      //从全体财务收费单数据中删除抽检件
      for(int m=0;m<tDelBussNoSet.length;m++)
      {
        if(!"".equals(tDelBussNoSet[m]))
        {
         tAllBOPPol.removeByName(tDelBussNoSet[m]);
        }
      }

      tBussNoData = tAllBOPPol.getBussNoData();
      logger.debug("***所有财务收费单数量(getNeedCheckBOPPolData处理后)： "+tBussNoData.size());
      Vector tBussNoData1 = tNeedCheckBOPPol.getBussNoData();
      logger.debug("***抽检件数量： "+tBussNoData1.size());
    }catch (Exception ex)
    {
    	CError.buildErr(this, ex.toString()) ;
      logger.debug(ex.toString());
      
      return false;
    }
    return true;
  }

  
  /**
   * 处理已经导入件的财务收费单和抽检件财务收费单
   * @param tImportedBOPPol
   * @param tNeedCheckBOPPol
   * @param serNo
   * @param tGI
   * @param tBPOID
   * @return 如果处理出现异常，返回false,否则true
   */
  private boolean dealImportedAndNeedCheckData(BPODataContainer tImportedBOPPol,BPODataContainer tNeedCheckBOPPol,String serNo,GlobalInput tGI,String tBPOID)
  {
    try
    {
      //1。先处理已经导入过的财务收费单数据
      Vector tBussNoData = tImportedBOPPol.getBussNoData();
      for(int i=0;i<tBussNoData.size();i++)
      {
        VData tVData = new VData();
        FineBPODealInputDataBLS tBPODealInputDataBLS = new FineBPODealInputDataBLS();  //数据提交类
        BPOMissionStateSchema tBPOMissionStateSchema = new BPOMissionStateSchema();
        BPOMissionDetailStateSchema tBPOMissionDetailStateSchema = new BPOMissionDetailStateSchema();
        String tBussNo = (String)tBussNoData.get(i);
        tVData = (VData)tImportedBOPPol.getschemaData(tBussNo);
        Element tOnePolData = (Element)tImportedBOPPol.getelementData(tBussNo);
        tBPOMissionStateSchema = (BPOMissionStateSchema)tVData.getObjectByObjectName("BPOMissionStateSchema",0);
        tBPOMissionStateSchema.setDealType("05");  //05－重复导入
        tBPOMissionStateSchema.setState("1");    //1-成功导入系统
        tBPOMissionStateSchema.setOperator(tGI.Operator);
        tBPOMissionStateSchema.setBPOID(tBPOID);
        tBPOMissionStateSchema.setSerialNo(serNo);
        tBPOMissionStateSchema.setMakeDate(PubFun.getCurrentDate());
        tBPOMissionStateSchema.setMakeTime(PubFun.getCurrentTime());
        tBPOMissionStateSchema.setModifyDate(PubFun.getCurrentDate());
        tBPOMissionStateSchema.setModifyTime(PubFun.getCurrentTime());

        Reflections tReflections = new Reflections();
        //把对象tBPOMissionStateSchema的值赋给对象tBPOMissionDetailStateSchema
        tReflections.transFields(tBPOMissionDetailStateSchema,tBPOMissionStateSchema);
        ExeSQL tExeSQL = new ExeSQL();
        String tSQL = "select (case when max(DealCount) is not null then max(DealCount) else 0 end) from BPOMissionDetailState where BussNo = '?BussNo?' and BussNoType = 'OF'";
        SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
        sqlbv2.sql(tSQL);
        sqlbv2.put("BussNo", tBPOMissionStateSchema.getBussNo());
        int tDealCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv2));
        logger.debug("tDealCount: "+tDealCount);
        tBPOMissionDetailStateSchema.setDealCount(tDealCount+1);
        tVData.add(tBPOMissionDetailStateSchema);

        //分每一条提交数据库，目的是不要因为其中一条有问题，导致所有的财务收费单无法提交
        if(!tBPODealInputDataBLS.submitData(tVData,tOnePolData))
        {
        	CError.buildErr(this, tBPOMissionStateSchema.getBussNo()+"已导入的财务收费数据保存失败："+tBPODealInputDataBLS.mErrors.getFirstError()) ;
        }
      }


      //2.保存抽检件
      Vector tCheckBussNoData = tNeedCheckBOPPol.getBussNoData();
      for(int i=0;i<tCheckBussNoData.size();i++)
      {
        VData tVData = new VData();
        BPOMissionStateSchema tBPOMissionStateSchema = new BPOMissionStateSchema();
        BPOMissionDetailStateSchema tBPOMissionDetailStateSchema = new BPOMissionDetailStateSchema();
        String tBussNo = (String)tCheckBussNoData.get(i);
        tVData = (VData)tNeedCheckBOPPol.getschemaData(tBussNo);
        Element tOnePolData = (Element)tNeedCheckBOPPol.getelementData(tBussNo);
        tBPOMissionStateSchema = (BPOMissionStateSchema)tVData.getObjectByObjectName("BPOMissionStateSchema",0);
        tBPOMissionStateSchema.setDealType("01"); //01－抽检件
        tBPOMissionStateSchema.setState("0"); //0－未完成
        tBPOMissionStateSchema.setOperator(tGI.Operator);
        tBPOMissionStateSchema.setBPOID(tBPOID);
        tBPOMissionStateSchema.setSerialNo(serNo);
        tBPOMissionStateSchema.setMakeDate(PubFun.getCurrentDate());
        tBPOMissionStateSchema.setMakeTime(PubFun.getCurrentTime());
        tBPOMissionStateSchema.setModifyDate(PubFun.getCurrentDate());
        tBPOMissionStateSchema.setModifyTime(PubFun.getCurrentTime());

        Reflections tReflections = new Reflections();
        //把对象tBPOMissionStateSchema的值赋给对象tBPOMissionDetailStateSchema
        tReflections.transFields(tBPOMissionDetailStateSchema,tBPOMissionStateSchema);
        ExeSQL tExeSQL = new ExeSQL();
        String tSQL = "select (case when max(DealCount) is not null then max(DealCount) else 0 end) from BPOMissionDetailState where BussNo = '?BussNo?' and BussNoType = 'OF'";
        SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
        sqlbv3.sql(tSQL);
        sqlbv3.put("BussNo", tBPOMissionStateSchema.getBussNo());
        int tDealCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv3));
        logger.debug("tDealCount: "+tDealCount);
        tBPOMissionDetailStateSchema.setDealCount(tDealCount+1);
        tVData.add(tBPOMissionDetailStateSchema);
        //分每一条提交数据库，目的是不要因为其中一条有问题，导致所有的财务收费单无法提交
        FineBPODealInputDataBLS tBPODealInputDataBLS = new FineBPODealInputDataBLS();  //数据提交类
        if(!tBPODealInputDataBLS.submitData(tVData,tOnePolData))
        {
        	CError.buildErr(this, tBPOMissionStateSchema.getBussNo()+"抽检件财务收费单数据保存失败："+tBPODealInputDataBLS.mErrors.getFirstError()) ;
        }
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      CError.buildErr(this, "处理已经导入件的财务收费单和抽检件财务收费单发生异常！") ;
      
      return false;
    }
    return true;
  }

  
  /**
   * 处理异常件财务收费单
   * @param tImportedBOPPol
   * @param tNeedCheckBOPPol
   * @param serNo
   * @param tGI
   * @param tBPOID
   * @return 如果处理出现异常，返回false,否则true
   */
  private boolean dealQuestData(BPODataContainer tQuestBOPPol,String serNo,GlobalInput tGI,String tBPOID)
  {
    try
    {
      Vector tQuestBussNoData = tQuestBOPPol.getBussNoData();
      for(int i=0;i<tQuestBussNoData.size();i++)
      {
        VData tVData = new VData();
        FineBPODealInputDataBLS tBPODealInputDataBLS = new FineBPODealInputDataBLS();  //数据提交类
        BPOMissionStateSchema tBPOMissionStateSchema = new BPOMissionStateSchema();
        BPOMissionDetailStateSchema tBPOMissionDetailStateSchema = new BPOMissionDetailStateSchema();
        BPOMissionDetailErrorSet tBPOMissionDetailErrorSet = new BPOMissionDetailErrorSet();
        String tBussNo = (String)tQuestBussNoData.get(i);
        tVData = (VData)tQuestBOPPol.getschemaData(tBussNo);
        Element tOnePolData = (Element)tQuestBOPPol.getelementData(tBussNo);

        //1.BPOMissionState
        tBPOMissionStateSchema = (BPOMissionStateSchema)tVData.getObjectByObjectName("BPOMissionStateSchema",0);
        tBPOMissionStateSchema.setState("0");    //0－未完成处理
        //如果是外包方无法处理的异常件（如整个扫描件无法识别），记录扫描件的主键 Docid
        if("03".equals(tBPOMissionStateSchema.getDealType()))
        {
          ExeSQL tExeSQL1 = new ExeSQL();
          String tSQL1 = "select min(Docid) from es_doc_main where DocCode = '?DocCode?' and BussType = 'OF'" ;
          SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
          sqlbv4.sql(tSQL1);
          sqlbv4.put("DocCode", tBPOMissionStateSchema.getBussNo());
          tBPOMissionStateSchema.setRemark(tExeSQL1.getOneValue(sqlbv4));
        }
        tBPOMissionStateSchema.setOperator(tGI.Operator);
        tBPOMissionStateSchema.setBPOID(tBPOID);
        tBPOMissionStateSchema.setSerialNo(serNo);
        tBPOMissionStateSchema.setMakeDate(PubFun.getCurrentDate());
        tBPOMissionStateSchema.setMakeTime(PubFun.getCurrentTime());
        tBPOMissionStateSchema.setModifyDate(PubFun.getCurrentDate());
        tBPOMissionStateSchema.setModifyTime(PubFun.getCurrentTime());

        //2.BPOMissionDetailState
        Reflections tReflections = new Reflections();
        //把对象tBPOMissionStateSchema的值赋给对象tBPOMissionDetailStateSchema
        tReflections.transFields(tBPOMissionDetailStateSchema,tBPOMissionStateSchema);
        ExeSQL tExeSQL = new ExeSQL();
        String tSQL = "select (case when max(DealCount) is not null then max(DealCount) else 0 end) from BPOMissionDetailState where BussNo = '?BussNo?' and BussNoType = 'OF'" ;
        SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
        sqlbv5.sql(tSQL);
        sqlbv5.put("BussNo", tBPOMissionStateSchema.getBussNo());
        int tDealCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv5));
        logger.debug("tDealCount: "+tDealCount);
        tBPOMissionDetailStateSchema.setDealCount(tDealCount+1);
        tVData.add(tBPOMissionDetailStateSchema);

        //3.BPOMissionDetailError
        tBPOMissionDetailErrorSet = (BPOMissionDetailErrorSet)tVData.getObjectByObjectName("BPOMissionDetailErrorSet",0);

        if(tBPOMissionDetailErrorSet != null)
        {
          for(int j=1;j<=tBPOMissionDetailErrorSet.size();j++)
          {
            tBPOMissionDetailErrorSet.get(j).setSerialNo(serNo);
            tBPOMissionDetailErrorSet.get(j).setOperator(tGI.Operator);
            tBPOMissionDetailErrorSet.get(j).setMakeDate(PubFun.getCurrentDate());
            tBPOMissionDetailErrorSet.get(j).setMakeTime(PubFun.getCurrentTime());
          }
        }

        //分每一条提交数据库，目的是不要因为其中一条有问题，导致所有的财务收费单无法提交
        if(!tBPODealInputDataBLS.submitData(tVData,tOnePolData))
        {
        	CError.buildErr(this, tBPOMissionStateSchema.getBussNo()+"已导入的财务收费单数据保存失败："+tBPODealInputDataBLS.mErrors.getFirstError()) ;
          
        }
      }
    }
    catch (Exception ex)
    {
    	CError.buildErr(this, "处理异常件财务收费单发生异常！") ;
      
      return false;
    }
    return true;
  }

  
  /**
   * 删除一个单证下已经导入的险种
   * @param tBussNo 单证号
   * @return 如果处理出现异常，则返回false,否则返回true
   */
  public boolean redoInputedPol(String tBussNo)
  {
	  try
	    {
	      String tSQL ="select (case when count(1) is not null then count(1) else 0 end) from LJTempFee where TempFeeNo='?tBussNo?'";
	      SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
	      sqlbv6.sql(tSQL);
	      sqlbv6.put("tBussNo", tBussNo);
	      ExeSQL tExeSQL = new ExeSQL();
	      int tExistCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv6));

	      tSQL ="select (case when count(1) is not null then count(1) else 0 end) from LJTempFeeClass where TempFeeNo='?tBussNo?'";
	      SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
	      sqlbv7.sql(tSQL);
	      sqlbv7.put("tBussNo", tBussNo);
	      tExeSQL = new ExeSQL();
	      int tExistClassCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv7));
	      logger.debug("***本次导入的暂收据记录个数："+ String.valueOf(tExistCount));
	      logger.debug("***本次导入的暂收据分类记录个数："+ String.valueOf(tExistClassCount));

	      if((tExistCount==0) && (tExistClassCount==0))
	      {
	        logger.debug("***本次没有导入任何数据，无需撤销"+tBussNo);
	        return true;
	      }
	      
	        logger.debug("***准备删除部分导入的数据："+tBussNo);
	        MMap tDelMap = prepareDelMap(tBussNo);
	        if(tDelMap == null)  //准备数据出现异常
	        {
	          logger.debug("准备删除部分导入的数据发生异常："+tBussNo);
	          return false;
	        }
	        VData sVData = new VData();
	        sVData.add(tDelMap);
	        PubSubmit ps = new PubSubmit();
	        if(!ps.submitData(sVData, ""))
	        {
	        	CError.buildErr(this, tBussNo+"删除部分导入的财务收费单数据失败："+ps.mErrors.getFirstError()) ;
	          
	          return false;
	        }
	    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      return false;
    }
    return true;
  }
  
  
  /**
   * 处理清洁件的数据
   * @param tAllBOPPol
   * @param tQuestBOPPol
   * @param serNo
   * @param tGI
   * @param tBPOID
   * @return 如果处理出现异常，则返回false,否则返回true
   */
  private boolean dealRightData(BPODataContainer tAllBOPPol,BPODataContainer tQuestBOPPol,String serNo,GlobalInput tGI,String tBPOID,String tBACKDATE)
  {
    try
    {
      Vector tRightBussNoData = tAllBOPPol.getBussNoData();
      for(int i=0;i<tRightBussNoData.size();i++)
      {
        String tBussNo = (String)tRightBussNoData.get(i);
        VData tVData = (VData)tAllBOPPol.getschemaData(tBussNo);
        tVData.add(tGI);
        BPOMissionStateSchema tBPOMissionStateSchema = new BPOMissionStateSchema();
        tBPOMissionStateSchema = (BPOMissionStateSchema)tVData.getObjectByObjectName("BPOMissionStateSchema",0);
        //1.导入数据
        if(!DealOneRightPol(tVData,tBACKDATE,0))   //如果导入失败，则将该清洁件视为导入时的异常件
        {
          logger.debug("***导入数据失败，原因："+mErrors.getLastError());
          //判断是否有部分险种已经导入，如果有，则删除
          LJTempFeeSchema tLJTempFeeSchema = (LJTempFeeSchema)tVData.getObjectByObjectName("LJTempFeeSchema",0);
          tBussNo = tLJTempFeeSchema.getTempFeeNo();
          if(!redoInputedPol(tBussNo))
          {
            return false;
          }
          //准备异常件的数据
          tBPOMissionStateSchema.setDealType("04");
          tBPOMissionStateSchema.setState("0");    //0－未完成处理
          tBPOMissionStateSchema.setOperator(tGI.Operator);
          tBPOMissionStateSchema.setBPOID(tBPOID);
          tBPOMissionStateSchema.setSerialNo(serNo);
          tBPOMissionStateSchema.setMakeDate(PubFun.getCurrentDate());
          tBPOMissionStateSchema.setMakeTime(PubFun.getCurrentTime());
          tBPOMissionStateSchema.setModifyDate(PubFun.getCurrentDate());
          tBPOMissionStateSchema.setModifyTime(PubFun.getCurrentTime());

          BPOMissionDetailErrorSchema tBPOMissionDetailErrorSchema = new BPOMissionDetailErrorSchema();
          BPOMissionDetailErrorSet tBPOMissionDetailErrorSet = new BPOMissionDetailErrorSet();
          tBPOMissionDetailErrorSchema.setBussNo(tBPOMissionStateSchema.getBussNo());
          tBPOMissionDetailErrorSchema.setBussNoType("OF");
          tBPOMissionDetailErrorSchema.setErrorCount(1);
          tBPOMissionDetailErrorSchema.setErrorTag("");
          tBPOMissionDetailErrorSchema.setErrorCode("");
          tBPOMissionDetailErrorSchema.setErrorContent(StrTool.cTrim(mErrors.getLastError()).indexOf("NullPointerException")!=-1?"导入时发生异常，请检查缴费金额等值是否正确！":StrTool.cTrim(mErrors.getLastError()));
          tBPOMissionDetailErrorSet.add(tBPOMissionDetailErrorSchema);
          tVData.add(tBPOMissionDetailErrorSet);

          tQuestBOPPol.add(tBussNo,tAllBOPPol.getschemaData(tBussNo),tAllBOPPol.getelementData(tBussNo),"");
        }
        else  //如果成功，则记录该财务收费单的状态
        {
          Element tOnePolData = (Element)tAllBOPPol.getelementData(tBussNo);
          FineBPODealInputDataBLS tBPODealInputDataBLS = new FineBPODealInputDataBLS();  //数据提交类
          BPOMissionDetailStateSchema tBPOMissionDetailStateSchema = new BPOMissionDetailStateSchema();
          tBPOMissionStateSchema.setDealType("00");  //00－清洁件导入
          tBPOMissionStateSchema.setState("1");    //1-成功导入系统
          tBPOMissionStateSchema.setOperator(tGI.Operator);
          tBPOMissionStateSchema.setBPOID(tBPOID);
          tBPOMissionStateSchema.setSerialNo(serNo);
          tBPOMissionStateSchema.setMakeDate(PubFun.getCurrentDate());
          tBPOMissionStateSchema.setMakeTime(PubFun.getCurrentTime());
          tBPOMissionStateSchema.setModifyDate(PubFun.getCurrentDate());
          tBPOMissionStateSchema.setModifyTime(PubFun.getCurrentTime());

          Reflections tReflections = new Reflections();
          //把对象tBPOMissionStateSchema的值赋给对象tBPOMissionDetailStateSchema
          tReflections.transFields(tBPOMissionDetailStateSchema,tBPOMissionStateSchema);
          ExeSQL tExeSQL = new ExeSQL();
          String tSQL = "select (case when max(DealCount) is not null then max(DealCount) else 0 end) from BPOMissionDetailState where BussNo = '?BussNo?' and BussNoType = '"+"OF"+"'" ;
          SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
          sqlbv8.sql(tSQL);
          sqlbv8.put("BussNo", tBPOMissionStateSchema.getBussNo());
          int tDealCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv8));
          logger.debug("tDealCount: "+tDealCount);
          tBPOMissionDetailStateSchema.setDealCount(tDealCount+1);
          tVData.add(tBPOMissionDetailStateSchema);

          if(!tBPODealInputDataBLS.submitData(tVData,tOnePolData))
          {
        	  CError.buildErr(this, tBPOMissionStateSchema.getBussNo()+"已导入的财务收费单数据保存失败："+tBPODealInputDataBLS.mErrors.getFirstError()) ;
        	  logger.debug("***导入数据失败，原因："+mErrors.getLastError());
              //判断是否有部分险种已经导入，如果有，则删除
              LJTempFeeSchema tLJTempFeeSchema = (LJTempFeeSchema)tVData.getObjectByObjectName("LJTempFeeSchema",0);
              tBussNo = tLJTempFeeSchema.getTempFeeNo();
              if(!redoInputedPol(tBussNo))
              {
                return false;
              }
          }
        }
      }
    }catch(Exception ex)
    {
    	CError.buildErr(this, "处理清洁件财务收费单发生异常！") ;
      
      return false;
    }
    return true;
  }

  
  private MMap prepareDelMap(String tTempFeeNo)
  {
    MMap mapDel = new MMap();
    try
    {
      String tWherePart = " TempFeeNo ='?tTempFeeNo?'";
      logger.debug("***tWherePart: "+tWherePart); 
      SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
      sqlbv9.sql("delete from LJTempFee where "+tWherePart);
      sqlbv9.put("tTempFeeNo", tTempFeeNo);
      SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
      sqlbv10.sql("delete from LJTempFeeClass where "+tWherePart);
      sqlbv10.put("tTempFeeNo", tTempFeeNo);
      SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
      sqlbv11.sql("delete from LockTable where trim(NoLimit)='?tTempFeeNo?'");
      sqlbv11.put("tTempFeeNo", tTempFeeNo);
      mapDel.put(sqlbv9, "DELETE");      
      mapDel.put(sqlbv10,"DELETE");    
      mapDel.put(sqlbv11,"DELETE");
    }
    catch(Exception ex)
    {
    	CError.buildErr(this, ex.toString()) ;
      
      ex.printStackTrace();
      return null;
    }
    return mapDel;
  }
  
  
  /**
   *  处理一个数据文件
   * @param Document tOneFileData
   * @param String tDataFile
   * @return 如果处理出现异常，则返回false,否则返回true
   */
  public boolean DealOneFile(Document tOneFileData,String tDataFile)
  {
    try
    {
      logger.debug("Start "+tDataFile+" ...");
      BPODataContainer tAllBOPPol = new BPODataContainer();//保存所有财务收费单的信息
      BPODataContainer tQuestBOPPol = new BPODataContainer();//保存异常件的信息
      BPODataContainer tImportedBOPPol = new BPODataContainer();//已经在系统中导入过的财务收费单信息
      BPODataContainer tNeedCheckBOPPol = new BPODataContainer(); //按照抽检规则,需要抽检的财务收费单集合

      String tBPOID = getBOPID(tOneFileData);//得到外包公司的编码
      String tBACKDATE = getBACKDATE(tOneFileData);//得到外包公司的返回日期
      BPOServerInfoDB tBPOServerInfoDB = new BPOServerInfoDB();
      tBPOServerInfoDB.setBPOID(tBPOID);
      if(!tBPOServerInfoDB.getInfo())
      {
        CError.buildErr(this, "外包公司返回数据错误：错误的外包方编码！") ;
        return false;
      }

      String tDataBackupPath = tBPOServerInfoDB.getBackDataBackupPath();
    	  
      if( tDataBackupPath == null || "".equals(tDataBackupPath) )
      {
        CError.buildErr(this, "外包公司"+tBPOID+"数据备份路径未描述！") ;
        return false;
      }

      if(tBPOServerInfoDB.getLisOperator() == null || "".equals(tBPOServerInfoDB.getLisOperator()))
      {
        CError.buildErr(this, "外包公司"+tBPOID+"操作员未描述！") ;
        return false;
      }
      
      if( tBACKDATE == null || "".equals(tBACKDATE) )
      {
        CError.buildErr(this, "外包公司返回日期为空！") ;
        return false;
      }
      
      GlobalInput tGI = new GlobalInput();
      tGI.Operator = tBPOServerInfoDB.getLisOperator();
      tGI.ManageCom = "86";
      tGI.ComCode = "86";

      logger.debug("...Start getAllBOPPolData ...");
      //1。获取所有财务收费单信息
      if(!getAllBOPPolData(tAllBOPPol,tOneFileData))
      {
        return false;
      }

      logger.debug("...Start getImportedBOPPolData ...");
      //2.ImportedBOPPol
      if(!getImportedBOPPolData(tAllBOPPol,tImportedBOPPol))
      {
         return false;
      }

      logger.debug("...Start getQuestBOPPolData ...");
      //3.QuestBOPPol
      if(!getQuestBOPPolData(tAllBOPPol,tQuestBOPPol))
      {
        return false;
      }

      logger.debug("...Start getNeedCheckBOPPolData ...");
      //4.NeedCheckBOPPol
      if(!getNeedCheckBOPPolData(tBPOID,tAllBOPPol,tNeedCheckBOPPol))
      {
        return false;
      }

     //5.处理已导入件和抽检件

      //产生流水号
      String tLimit = PubFun.getNoLimit(tGI.ManageCom);
      String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
      logger.debug("...Start dealImportedAndNeedCheckData ...");
      if(!dealImportedAndNeedCheckData(tImportedBOPPol,tNeedCheckBOPPol,serNo,tGI,tBPOID))
      {
        return false;
      }

     //6.清洁件系统导入功能
      logger.debug("...Start dealRightData ...");
      if(!dealRightData(tAllBOPPol,tQuestBOPPol,serNo,tGI,tBPOID,tBACKDATE))
      {
        return false;
      }

      //7.处理异常件
      logger.debug("...Start dealQuestData ...");
      if(!dealQuestData(tQuestBOPPol,serNo,tGI,tBPOID))
      {
        return false;
      }

      //8。备份该数据文件
      logger.debug("...Start 准备移动数据文件 ...");
      String tDate = PubFun.getCurrentDate();      
      String path = tDataBackupPath + PubFun.getStr(tDate, 1, "-") + "/" 
                + PubFun.getStr(tDate, 2, "-") + "/"
                + PubFun.getStr(tDate, 3, "-") + "/";
      logger.debug("...备份路径 ..." + path);
      if(!TBPubFun.moveFiles(tDataFile,mBPOInputPath,path))
      {
        CError.buildErr(this, "备份"+tDataFile+"文件时出错！") ;
        return false;
      }
      logger.debug("... 移动数据文件成功 ...");
    }
    catch(Exception ex)
    {
      CError.buildErr(this, ex.toString()) ;
      return false;
    }
    return true;
  }

  
  /**
   * 将一个清洁件导入到核心业务系统中
   * @param tVData
   * @param tFlag  处理标志，0－表示系统正常导入，1－异常件、抽检件
   * @return 如果导入出错，则返回false,否则，返回true
   */
  public boolean DealOneRightPol(VData tVData,String tBACKDATE,int tFlag)
  {
    VData tOldRiskBasicInfoSet = new VData();   //原险种集合
    LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
    LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
    Vector tSuccPolSet = new Vector();  //处理成功的财务收费单号码集合
    VData newVData = new VData(); 
    TransferData tTD = new TransferData();
    try
    {
      tOldRiskBasicInfoSet = (VData)tVData.getObjectByObjectName("VData",0);
      LJTempFeeSchema tLJTempFeeSchema = (LJTempFeeSchema)tVData.getObjectByObjectName("LJTempFeeSchema",0);
      LJTempFeeClassSchema tLJTempFeeClassSchema = (LJTempFeeClassSchema)tVData.getObjectByObjectName("LJTempFeeClassSchema",0);
      GlobalInput tGI = (GlobalInput)tVData.getObjectByObjectName("GlobalInput",0);
      TransferData tTransferData = (TransferData) tVData.getObjectByObjectName("TransferData",0);    
	  double tSumMoney = 0;  
	  
	  tLJTempFeeSchema.setOtherNoType("4");
	  logger.debug("Start check...");
      //校验规则
      if(!checkSpec(tLJTempFeeClassSchema,tLJTempFeeSchema,tTransferData))
      {
        return false;
      } 
      logger.debug("End check...");    
      
    //代理人/业务员
      LAAgentDB tLAAgentDB = new LAAgentDB();
      tLAAgentDB.setAgentCode(tLJTempFeeSchema.getAgentCode());
      if(!tLAAgentDB.getInfo())
      {
        CError.buildErr(this, "业务员编码不存在"+tLJTempFeeSchema.getAgentCode()) ;
        return false;
      } 
      
      //2008-12-27 ln add --代理人展业类型为1时校验管理机构是否一致
      //2009-1-21 ln modify --代理人展业类型不为中介时校验管理机构是否一致
      if(tLAAgentDB.getBranchType() == null || "".equals(tLAAgentDB.getBranchType()))
      {
        CError.buildErr(this, "代理人的展业类型未知!") ;
        return false;
      }
      
      if(tLAAgentDB.getManageCom() == null || "".equals(tLAAgentDB.getManageCom()))
      {
        CError.buildErr(this, "业务员编码对应数据库中的管理机构为空!") ;
        return false;
      }
      
      //if(tLAAgentDB.getBranchType().equals("1"))
      if(!tLAAgentDB.getBranchType().equals("6") && !tLAAgentDB.getBranchType().equals("7") 
    		  && !tLAAgentDB.getBranchType().equals("9"))
      {
    	  if(!tLJTempFeeSchema.getManageCom().equals(tLAAgentDB.getManageCom()))
	      {
	        CError.buildErr(this, "业务员编码对应数据库中的管理机构与录入的管理机构不符！") ;
	        return false;
	      }
      }
      //end 2008-12-27  add 
      
      if(tLAAgentDB.getAgentState()==null||tLAAgentDB.getAgentState().equals(""))
      {
        CError.buildErr(this, "业务员不存在或状态未知！") ;
        return false;
      }
      else if(Integer.parseInt(tLAAgentDB.getAgentState())>=3)
      {
          CError.buildErr(this, "业务员已经离职，不能录入新单暂交费!") ;
          return false;
      } 
    
      if(tOldRiskBasicInfoSet == null || tOldRiskBasicInfoSet.size()== 0)
      {
        CError.buildErr(this, "得到的险种暂交费信息为空") ;
        return false;
      }
      
      if(isDate(tBACKDATE) != null)
      {
    	 if (PubFun.calInterval(tBACKDATE,
    			 PubFun.getCurrentDate(), "D") < 0)
    	 {
    		 CError.buildErr(this, "交费日期不能超过今天！") ;
             return false; 
    	 }
    	 if("1".equals(tLJTempFeeClassSchema.getPayMode())||"6".equals(tLJTempFeeClassSchema.getPayMode()))
    	 {
    		 tLJTempFeeSchema.setEnterAccDate(tBACKDATE);
    		 tLJTempFeeClassSchema.setEnterAccDate(tBACKDATE);
    	 }    	     
         tLJTempFeeSchema.setPayDate(tBACKDATE);         
         tLJTempFeeClassSchema.setPayDate(tBACKDATE);
      } 
      else
      {
          CError.buildErr(this, "交费日期（或外包公司返回日期）错误！") ;          
          return false;
      }
      
      //operator tempfeeclass' operator is not null;
      String operator = tGI.Operator;
      tLJTempFeeSchema.setOperator(operator);
      tLJTempFeeClassSchema.setOperator(operator);
      
      tLJTempFeeSchema.setPolicyCom(tLAAgentDB.getManageCom());
      tLJTempFeeClassSchema.setPolicyCom(tLAAgentDB.getManageCom());
      
      //循环处理每一个险种
      for(int i=0;i<tOldRiskBasicInfoSet.size();i++)
      {
        FineRiskBasicInfo tRiskBasicInfo = (FineRiskBasicInfo)tOldRiskBasicInfoSet.get(i);
        
        logger.debug("Start checkRisk...");
        //校验规则
        if(!checkSpecRisk(tRiskBasicInfo))
        {
          return false;
        } 
        logger.debug("End checkRisk...");
        
        LJTempFeeSchema tLJTempFeeSchema1 = new LJTempFeeSchema(); 
        tLJTempFeeSchema1.setSchema(tLJTempFeeSchema);
        double money = tRiskBasicInfo.getMoney();  //记录传入的交费金额
        int years = tRiskBasicInfo.getYears();  
        int intv = tRiskBasicInfo.getIntv();
        
        tLJTempFeeSchema1.setRiskCode(tRiskBasicInfo.getRiskCode());
        tLJTempFeeSchema1.setPayMoney(money);
        tLJTempFeeSchema1.setPayYears(years);
        tLJTempFeeSchema1.setPayIntv(intv);
        tLJTempFeeSchema1.setPayIntv(intv);
        
        tSumMoney = tSumMoney + money;
        tSumMoney = PubFun.setPrecision( tSumMoney+0.000000001, "0.00");

        tLJTempFeeSchema1.setAgentGroup(tLAAgentDB.getAgentGroup());
//        tLJTempFeeSchema.setAgentName(tLAAgentDB.getAgentName());
        tLJTempFeeSet.add(tLJTempFeeSchema1);

      }//end for      

      if(tLJTempFeeClassSchema.getPayMoney() != tSumMoney )
      {
    	  CError.buildErr(this, "交费总金额必须等于险种交费金额之和！") ;
         
         return false;
      }      
      
      String tempFeeNo = tLJTempFeeClassSchema.getTempFeeNo();           
      tLJTempFeeClassSet.add(tLJTempFeeClassSchema);     
      tTD.setNameAndValue("Type", "WB");
      
      newVData.addElement(tGI);
      newVData.addElement(tTD);
      newVData.addElement(tLJTempFeeSet);
      newVData.addElement(tLJTempFeeClassSet);

      TempFeeUI tTempFeeUI = new TempFeeUI();
      if( !tTempFeeUI.submitData( newVData, "INSERT" ) )
      {
    	//mErrors.copyAllErrors(tTempFeeUI.mErrors);
    	  CError.buildErr(this, " 保存失败，原因是: " + tTempFeeUI.mErrors.getError(0).errorMessage) ;
          
        return false;
	  }      
      
      logger.debug("tLJTempFeeClassSchema.getTempFeeNo(): "+tempFeeNo);
      
      //记录导入成功的单证号
      tSuccPolSet.clear();
      tSuccPolSet.add(tempFeeNo);

      //如果异常件或者抽检件处理成功，修改任务处理表的状态
      if(tFlag == 1)
      {
        String tDealType = (String)tTransferData.getValueByName("DealType");
        logger.debug("***tFlag :  "+tFlag+"  tDealType :  "+tDealType);
        //按原来的单证号进行保存
        if(!updateBPOState(tempFeeNo,tDealType,"1"))
        {
          return false;
        }
      }           
      
    }
    catch(Exception ex)
    {
    	CError.buildErr(this, ex.toString()) ;
      
      ex.printStackTrace();
      return false;
    }
    finally
    {
      logger.debug("****准备装入已经导入险种的财务收费单*****");
      logger.debug("****已导入单证印刷号:暂收据号： "+tSuccPolSet.toString());
      logger.debug("****已导入险种数: "+tLJTempFeeSet.size());
    }
    return true;
  }

  
  /**
   * 修改任务表的状态：当异常件、抽检件界面处理成功后，修改BPOMissionDetailState的状态为已处理状态
   * @param tBussNo   业务号码（印刷号）
   * @param tDealType  处理类型   01－抽检件，02－外包方返回可处理异常件，03－外包方无法处理的异常件（如整个扫描件无法识别），04－清洁件导入系统出错所致的异常件
   * @param tState  1-成功导入系统，2－删除
   * @return 如果状态更新发生异常，返回false,否则，返回true
   */
  public boolean updateBPOState(String tBussNo,String tDealType,String tState)
  {
    try
    {
      BPOMissionStateSchema tBPOMissionStateSchema = new BPOMissionStateSchema();
      BPOMissionDetailStateSchema tBPOMissionDetailStateSchema = new BPOMissionDetailStateSchema();
      BPOMissionStateDB tBPOMissionStateDB = new BPOMissionStateDB();
      tBPOMissionStateDB.setBussNo(tBussNo);
      tBPOMissionStateDB.setBussNoType("OF");
      if(tDealType == null || "".equals(tDealType))
      {
    	  CError.buildErr(this, " 任务处理状态表修改失败，原因是DealType传入为空 ") ;
        
        return false;
      }
      tBPOMissionStateDB.setDealType(tDealType);
      if(!tBPOMissionStateDB.getInfo())
      {
    	  CError.buildErr(this, " 任务处理状态主表查询失败 "+tBussNo) ;
        
        return false;
      }
      tBPOMissionStateSchema = tBPOMissionStateDB.getSchema();
      tBPOMissionStateSchema.setState(tState);   //修改处理状态
      tBPOMissionStateSchema.setModifyDate(PubFun.getCurrentDate());
      tBPOMissionStateSchema.setModifyTime(PubFun.getCurrentTime());

      Reflections tReflections = new Reflections();

      //把对象tBPOMissionStateSchema的值赋给对象tBPOMissionDetailStateSchema
      tReflections.transFields(tBPOMissionDetailStateSchema,tBPOMissionStateSchema);
      ExeSQL tExeSQL = new ExeSQL();
      String tSQL = "select (case when max(DealCount) is not null then max(DealCount) else 0 end) from BPOMissionDetailState where BussNo = '?BussNo?' and BussNoType = 'OF'" ;
      SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
      sqlbv12.sql(tSQL);
      sqlbv12.put("BussNo", tBPOMissionStateSchema.getBussNo());
      int tDealCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv12));
      logger.debug("tDealCount: "+tDealCount);
      tBPOMissionDetailStateSchema.setDealCount(tDealCount+1);

      VData tUpdateVData = new VData();
      logger.debug("tBPOMissionStateSchema.encode(): "+tBPOMissionStateSchema.encode());
      logger.debug("tBPOMissionDetailStateSchema.encode(): "+tBPOMissionDetailStateSchema.encode());
      tUpdateVData.add(tBPOMissionStateSchema);
      tUpdateVData.add(tBPOMissionDetailStateSchema);

      FineBPODealInputDataBLS tBPODealInputDataBLS = new FineBPODealInputDataBLS();  //数据提交类
      if(!tBPODealInputDataBLS.submitData(tUpdateVData,"UPDATE"))
      {
    	  CError.buildErr(this, tBPOMissionStateSchema.getBussNo()+"已导入的财务收费单数据保存失败："+tBPODealInputDataBLS.mErrors.getFirstError()) ;
        
        return false;
      }
    }
    catch(Exception ex)
    {
    	CError.buildErr(this, ex.toString()) ;
      
      ex.printStackTrace();
      return false;
    }
   return true;
  }

  /**
   *校验输入的暂收据或授权书录入一般信息是否正确
   * */
  private boolean checkSpec(LJTempFeeClassSchema tLJTempFeeClassSchema,LJTempFeeSchema tLJTempFeeSchema, TransferData tTransferData)
  {
	  try{  
		  String tempFeeNo = tLJTempFeeSchema.getTempFeeNo();
	      logger.debug(tempFeeNo);
	    //zy 2009-10-09 银行划款协议书调整编码为532002开头
	      if(tempFeeNo==null||tempFeeNo.equals("")||!(tempFeeNo.length()>=6 &&(tempFeeNo.substring(0, 6).equals("863101")
				  ||(tempFeeNo.substring(0, 6).equals("863201")) || (tempFeeNo.substring(0, 6).equals("532002")) )))
	      {			
	    	  CError.buildErr(this, "单证印刷号码错误!") ;
	        
	        return false;
	      }
		  
		   String tPayMoney = (String) tTransferData.getValueByName("PayMoney");
		   if("error".equals(tPayMoney))
	          {
			   CError.buildErr(this, "交费总金额错误！") ;
	            
	            return false;
	          }
		  else if(!(tLJTempFeeClassSchema.getPayMoney() > 0 ))
	      {
			  CError.buildErr(this, "交费总金额必须大于零！") ;
	         
	         return false;
	      }		   	  
			 
		  if(tLJTempFeeSchema.getOtherNo() == null || "".equals(tLJTempFeeSchema.getOtherNo()))
          {
			  CError.buildErr(this, "投保单印刷号码为空") ;
            
            return false;
          }		  
		  else if((tLJTempFeeSchema.getOtherNo().length()!=14 && tLJTempFeeSchema.getOtherNo().length()!=20)
				  ||tLJTempFeeSchema.getOtherNo().indexOf("?")!=-1 )
          {
			  CError.buildErr(this, tLJTempFeeSchema.getOtherNo()+"投保单印刷号码错误!") ;
	        
	        return false;
	      }		  
		  
		  if(tLJTempFeeSchema.getManageCom() == null || "".equals(tLJTempFeeSchema.getManageCom()))
          {
			  CError.buildErr(this, "机构代码为空") ;
            
            return false;
          }		  
		  else if(tLJTempFeeSchema.getManageCom().length()!=8 
				  ||tLJTempFeeSchema.getManageCom().indexOf("?")!=-1 )
          {
			  CError.buildErr(this, "机构代码错误!") ;
	        
	        return false;
	      }
		  
		  if(tLJTempFeeSchema.getAgentCode() == null || "".equals(tLJTempFeeSchema.getAgentCode()))
          {
			  CError.buildErr(this, "业务员编码为空") ;
            
            return false;
          }		  
		  else if(tLJTempFeeSchema.getAgentCode().length()!=10 
				  ||tLJTempFeeSchema.getAgentCode().indexOf("?")!=-1 )
          {
			  CError.buildErr(this, "业务员编码错误!") ;
	        
	        return false;
	      }	   
	      
	      PubCertifyTakeBack tPubCertifyTakeBack = new PubCertifyTakeBack();
	      /*String tSQL ="select count(*) from LZCardTrack where Startno<='"+tLJTempFeeSchema.getTempFeeNo()+"' and Endno>='"+ tLJTempFeeSchema.getTempFeeNo()+"' and Receivecom = 'D"+tLJTempFeeSchema.getAgentCode()+"' and StateFlag='3' and CertifyCode ='"+ tPubCertifyTakeBack.CERTIFY_TempFee+"'";
	      ExeSQL tExeSQL = new ExeSQL();
	      int tExistCount = Integer.parseInt(tExeSQL.getOneValue(tSQL));		        
	      if(tExistCount==0 && tLJTempFeeSchema.getTempFeeNoType()!=null && tLJTempFeeSchema.getTempFeeNoType().equals("1")) 
	      {
	    	  CError.buildErr(this, "该单证（单证编码为："+tLJTempFeeSchema.getTempFeeNo()+" ）没有发放给该业务员（"+tLJTempFeeSchema.getAgentCode()+"）!") ;
	          
	          return false;
	      }*/
	      if(tLJTempFeeSchema.getTempFeeNoType()!=null && tLJTempFeeSchema.getTempFeeNoType().equals("1"))
	      {
	    	  if(!verifyTempfeeNoNew(tPubCertifyTakeBack.CERTIFY_TempFee,tLJTempFeeSchema.getTempFeeNo(),tLJTempFeeSchema.getAgentCode()))
	    		  return false;
	      }
		  if(tLJTempFeeClassSchema.getPayMode() == null || "".equals(tLJTempFeeClassSchema.getPayMode()))
          {
			  CError.buildErr(this, "交费方式为空!") ;
            
            return false;
          }		    
		  //银行转账
		  else if("4".equals(tLJTempFeeClassSchema.getPayMode()))
		  {
			  if(!tLJTempFeeSchema.getTempFeeNoType().equals("2"))
	          {
				  CError.buildErr(this, "暂收费类型与交费方式不匹配!") ;
	            
	            return false;
	          }
			  if(tLJTempFeeClassSchema.getBankAccNo() == null || "".equals(tLJTempFeeClassSchema.getBankAccNo()))
	          {
				  CError.buildErr(this, "授权账户号码为空!") ;
	            
	            return false;
	          }
			  else if(tLJTempFeeClassSchema.getBankAccNo().indexOf("?")!=-1)
	          {
				  CError.buildErr(this, tLJTempFeeClassSchema.getBankAccNo()+"授权账户号码错误!") ;
	            
	            return false;
	          }
			  else if("0101".equals(tLJTempFeeClassSchema.getBankCode())
					  ||"0301".equals(tLJTempFeeClassSchema.getBankCode()))
			  {
				  if(!(tLJTempFeeClassSchema.getBankAccNo().length()==19&&tLJTempFeeClassSchema.getBankAccNo().indexOf("*")!=(tLJTempFeeClassSchema.getBankAccNo().length()-1)))
	              {
					  CError.buildErr(this, "授权账户号码错误!") ;
		            
		            return false;
		          }
			  }
			  //zy 2010-06-11 应财务要求取消15位的校验
//			  else if("0401".equals(tLJTempFeeClassSchema.getBankCode()))
//			  {
//				  if(tLJTempFeeClassSchema.getBankAccNo().length()!=15||(tLJTempFeeClassSchema.getBankAccNo().indexOf("-")!=-1))
//	              {
//					  CError.buildErr(this, "授权账户号码错误!") ;
//		            
//		            return false;
//		          }
//			  }	
			  else if("0401".equals(tLJTempFeeClassSchema.getBankCode()))
			  {
				  if((tLJTempFeeClassSchema.getBankAccNo().indexOf("-")!=-1))
	              {
					  CError.buildErr(this, "授权账户号码错误!") ;
		            
		            return false;
		          }
			  }	
			  if(tLJTempFeeClassSchema.getIDNo()==null||tLJTempFeeClassSchema.getIDNo().equals("") )
	          {
				  CError.buildErr(this, "账户所有人证件号码为空!") ;
	            
	            return false;
	          }
			  else if(tLJTempFeeClassSchema.getIDNo().indexOf("?")!=-1)
	          {
				  CError.buildErr(this, tLJTempFeeClassSchema.getIDNo()+"账户所有人证件号码错误!") ;
	            
	            return false;
	          }	
			  
			  if(tLJTempFeeClassSchema.getAccName()==null||tLJTempFeeClassSchema.getAccName().equals("") )
	          {
				  CError.buildErr(this, "账户所有人姓名为空!") ;
	            
	            return false;
	          }
			  else if(tLJTempFeeClassSchema.getAccName().indexOf("?")!=-1)
	          {
				  CError.buildErr(this, "账户所有人姓名错误!") ;
	            
	            return false;
	          }				  
			 
		  }
		  else
		  {
			  if(!tLJTempFeeSchema.getTempFeeNoType().equals("1"))
	          {
				  CError.buildErr(this, "暂收费类型与交费方式不匹配!") ;
	            
	            return false;
	          }
			//转账支票
			  if("3".equals(tLJTempFeeClassSchema.getPayMode()))
			  {
				  if(tLJTempFeeClassSchema.getChequeNo() == null || "".equals(tLJTempFeeClassSchema.getChequeNo()))
		          {
					  CError.buildErr(this, "支票号码为空!") ;
		            
		            return false;
		          }
				  else if(tLJTempFeeClassSchema.getChequeNo().indexOf("?")!=-1)
		          {
					  CError.buildErr(this, "支票号码错误!") ;
		            
		            return false;
		          }		  
			  }	
			  else if("1".equals(tLJTempFeeClassSchema.getPayMode())				  
					  ||"6".equals(tLJTempFeeClassSchema.getPayMode()))
	          {           
	          }	
			  else {
				  CError.buildErr(this, "交费方式错误!") ;
	            
	            return false;
	          }		
			  
		  }		  
		  
		  
		  if(!("1".equals(tLJTempFeeClassSchema.getPayMode())))
		  {
			  if(tLJTempFeeClassSchema.getBankCode() == null || "".equals(tLJTempFeeClassSchema.getBankCode()))
	          {
				  CError.buildErr(this, "银行代码为空") ;
	            
	            return false;
	          }		  
			  else if(!(tLJTempFeeClassSchema.getBankCode().length()==4 
					  ||tLJTempFeeClassSchema.getBankCode().length()==6 )
					  ||tLJTempFeeClassSchema.getBankCode().indexOf("?")!=-1 )
	          {
				  CError.buildErr(this, "银行代码错误!") ;
		        
		        return false;
		      } 
		  }	  	 
		  
	  }
	  catch(Exception ex)
	    {
		  CError.buildErr(this, ex.toString()) ;
	      
	      ex.printStackTrace();
	      return false;
	    }
	   return true;
  }
  

  /**
   *校验输入的险种相关录入信息是否正确
   * */
  private boolean checkSpecRisk(FineRiskBasicInfo tRiskBasicInfo)
  {
	  try{
		  if(tRiskBasicInfo.getRiskCode() == null || "".equals(tRiskBasicInfo.getRiskCode()))
          {
			  CError.buildErr(this, "险种代码为空") ;
            
            return false;
          }	
		  else if(tRiskBasicInfo.getRiskCode().indexOf("?")!=-1)
          {
			  CError.buildErr(this, "险种代码错误!") ;
            
            return false;
          }
		  //代码是否正确？
		  String tSQL ="select (case when count(*) is not null then count(*) else 0 end) from LMRisk where RiskCode='?RiskCode?'";
		  SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
		  sqlbv13.sql(tSQL);
		  sqlbv13.put("RiskCode", tRiskBasicInfo.getRiskCode());
	      ExeSQL tExeSQL = new ExeSQL();
	      int tExistCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv13));		  
          if(tExistCount==0)
          {
        	  CError.buildErr(this, "险种代码错误!") ;
              
              return false;
          }
		  
		  if(tRiskBasicInfo.getPayMoney() == null || "".equals(tRiskBasicInfo.getPayMoney()))
          {
			  CError.buildErr(this, "险种交费金额为空！") ;
            
            return false;
          }
		  else if("?".equals(tRiskBasicInfo.getPayMoney()))
          {
			  CError.buildErr(this, "险种交费金额错误！") ;
            
            return false;
          }
		  else if(!(tRiskBasicInfo.getMoney() > 0 ))
          {
			  CError.buildErr(this, "险种交费金额必须大于零！") ;
            
            return false;
          }	
		  
		  if(tRiskBasicInfo.getPayIntv() == null || "".equals(tRiskBasicInfo.getPayIntv()))
          {
			  CError.buildErr(this, "险种交费间隔为空") ;
            
            return false;
          }	
		  else if(tRiskBasicInfo.getPayIntv().indexOf("?")!=-1)
          {
			  CError.buildErr(this, "险种交费间隔错误!") ;
            
            return false;
          }
		  else if(!(tRiskBasicInfo.getIntv()==-1
				  ||tRiskBasicInfo.getIntv()==0
				  ||tRiskBasicInfo.getIntv()==1
				  ||tRiskBasicInfo.getIntv()==3
				  ||tRiskBasicInfo.getIntv()==6
				  ||tRiskBasicInfo.getIntv()==12))
          {
			  CError.buildErr(this, "险种交费间隔错误!") ;
            
            return false;
          }
		  
		  if(tRiskBasicInfo.getPayYears() == null || "".equals(tRiskBasicInfo.getPayYears()))
          {
			  CError.buildErr(this, "险种交费年期为空") ;
            
            return false;
          }	
		  else if(tRiskBasicInfo.getPayYears().indexOf("?")!=-1)
          {
			  CError.buildErr(this, "险种交费年期错误!") ;
            
            return false;
          }
		  
	  }
	  catch(Exception ex)
	    {
		  CError.buildErr(this, ex.toString()) ;
	      
	      ex.printStackTrace();
	      return false;
	    }
	   return true;
  }
  
  /**
   *校验输入的日期格式是否正确
   * */
  private String [] isDate(String Date)
  {
   String[] dateArray = new String[3];
   try
   {
    dateArray = Date.split("-");
    if(dateArray.length!=3)
      return null;
    int Year=Integer.parseInt(dateArray[0]);
    int Month=Integer.parseInt(dateArray[1]);
    int Day=Integer.parseInt(dateArray[2]);
    if( Year<0 || Year>9999 || Month<0 || Month>12 || Day<0 || Day>31 )
     return null;
   }catch(Exception ex)
   {
    return null;
   }
   return dateArray;
  }


  /**
  * 从输入数据中得到所有对象
  * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
  */
  private String getBOPID(Document doc)
  {
    String tBOPID ="";
    Element root = doc.getRootElement();
    tBOPID = root.getChild("Company").getTextTrim();
    return tBOPID;
  }
   

  /**
  * 从输入数据中得到所有对象
  * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
  */
  private String getBACKDATE(Document doc)
  {
    String tBACKDATE ="";
    Element root = doc.getRootElement();
    tBACKDATE = root.getChild("BackDate").getTextTrim();
    return tBACKDATE;
  }
   
  /**
  * 从输入数据中得到所有对象
  * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
  */
  private boolean getInputData()
  {	 
//    mGI.setSchema((GlobalInput) mInputData.getObjectByObjectName("GlobalInput",0));
    //得到一些常量的配置信息
    if(!getConfigData())
      return false;

    //得到需要处理数据文件信息
    if(!getNeedDealFiles())
      return false;

    return true;
  }
  
  
  /**
   *  根据系统确定的路径和处理文件最大数取得数据文件名称列表
   *  如果返回文件名称列表时发生异常，返回false,否则，返回true;
   */
  private boolean getNeedDealFiles()
  {
    //取得一定数量的外包方返回的文件
    mNeedDealFiles = TBPubFun.getFilesList(mBPOInputPath,mBPOMaxCount);
    if( mNeedDealFiles == null )
    {
    	CError.buildErr(this, "没有需要处理的数据文件或者系统定义外包方数据返回的路径错误!") ;
      
      return false;
    }

    if( mNeedDealFiles.length ==0 )
    {
    	CError.buildErr(this, "没有需要处理的数据!") ;
      
      return false;
    }

    return true;
  }

  
  /**
  * 查询数据库中一些配置信息
  * 输出：如果出现异常，则返回false,否则返回true
  */
  private boolean getConfigData()
  {
    //1。查询系统定义外包方数据返回的路径
    ExeSQL tExeSQL = new ExeSQL();
    String tSQL = "select backdatapath from bposerverinfo where  lisoperator ='?tOperator?' and bussnotype='OF'";
    SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
    sqlbv14.sql(tSQL);
    sqlbv14.put("tOperator", tOperator);
    //logger.debug("****系统定义外包方数据返回的路径tSQL：  "+tSQL);
    mBPOInputPath = tExeSQL.getOneValue(sqlbv14);
    //test
//    mBPOInputPath = "D:/inputdata/Fine001/";
    
    logger.debug("****系统定义外包方数据返回的路径：  "+mBPOInputPath);
    if(mBPOInputPath == null || "".equals(mBPOInputPath))
    {
    	CError.buildErr(this, "系统未定义外包方数据返回的路径!") ;
      
      return false;
    }

    //2。查询系统定义处理外包方数据文件数量限制
    tExeSQL = new ExeSQL();
    tSQL = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='FineBPOMaxCount'";
    SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
    sqlbv15.sql(tSQL);
    String tBPOMaxCount = tExeSQL.getOneValue(sqlbv15);
    //logger.debug("****系统定义外包方数据返回的路径：  "+mBPOInputPath);
    if(tBPOMaxCount != null && !"".equals(tBPOMaxCount) && Integer.parseInt(tBPOMaxCount)>1)
    {
      mBPOMaxCount=Integer.parseInt(tBPOMaxCount);
    }

    return true;
  }

  
  /**
   * getConfigDataN added by ln 2008.5.22
   * 查询数据库中一些配置信息
   * 输出：如果出现异常，则返回false,否则返回true
   */
   private boolean getConfigDataN()
   {
	   try
       {   		   
		     //1 查询系统定义外包方数据返回的路径集合
		     ExeSQL tExeSQL = new ExeSQL();
		     String tSQL = "select backdatapath from bposerverinfo where bussnotype='OF' order by lisoperator desc";
		     SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
		     sqlbv16.sql(tSQL);
//		     logger.debug("****系统定义外包方数据返回的路径tSQL：  "+tSQL);
		     SSRS tSSRS =  tExeSQL.execSQL(sqlbv16);
		     int max=tSSRS.getMaxRow();
		     
		     //是否有定义的外保方路径标志
		     boolean state = false;
		     
			 if(tSSRS == null || max ==0)
			 {
				 CError.buildErr(this, "系统未定义任何外包方!") ;
			       
			       return false;	     
			 }			 
			 
			//取得外包方返回的路径集合
			 int i= 0;
			 mBPOInputPaths = new String[max];
			 
			 while( tSSRS.getMaxRow() > i )
			 {					 
				 if(!(tSSRS.GetText(max, 1) == null || "".equals(tSSRS.GetText(max, 1))))
			     {					
					mBPOInputPaths[i] = tSSRS.GetText(max, 1) ; 
					state = true ;
			     }	
				 
				 i++;
				 max--;
			 }
		  
		     if( state == false )
		     {
		    	 CError.buildErr(this, "系统未定义外包方数据返回的路径!") ;
		       
		       return false;
		     }
		     
		     logger.debug("******可处理的外保方数目 : "+mBPOInputPaths.length);

		     //2 查询系统定义处理外包方数据文件数量限制
		     tExeSQL = new ExeSQL();
		     tSQL = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='BPOMaxCount'";
		     SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
		     sqlbv17.sql(tSQL);
		     String tBPOMaxCount = tExeSQL.getOneValue(sqlbv17);
		     if(tBPOMaxCount != null && !"".equals(tBPOMaxCount) && Integer.parseInt(tBPOMaxCount)>1)
		     {
		       mBPOMaxCount=Integer.parseInt(tBPOMaxCount);
		     }

		     return true;  

       }
       catch(Exception ex)
       {
    	   CError.buildErr(this, ex.toString()) ;
         
         return false;
       }        
    
  }
   
   /**
    * //校验暂收据：
	//1、单证需要回收
	//2、单证处于可用状态
	//3、单证已经发放至该代理人或者其所在机构下
    */
    private boolean verifyTempfeeNoNew(String CertifyCode,String TempfeeNo,String AgentCode)
    {
    	//1、单证需要回收
      ExeSQL tExeSQL = new ExeSQL();
      String tSQL = "select a.tackbackflag from lmcertifydes a where a.certifycode='?CertifyCode?'";
      SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
      sqlbv18.sql(tSQL);
      sqlbv18.put("CertifyCode", CertifyCode);
      String tNeedTackBack = tExeSQL.getOneValue(sqlbv18);
      if(tNeedTackBack != null && !"Y".equals(tNeedTackBack))
      {
      	CError.buildErr(this, "该单证("+CertifyCode+")不用核销!") ;        
        return true;
      }
      tSQL = "select a.stateflag,a.receivecom from lzcard a where a.certifycode='?CertifyCode?' and a.startno='?TempfeeNo?'";
      SQLwithBindVariables sqlbv19=new SQLwithBindVariables();
      sqlbv19.sql(tSQL);
      sqlbv19.put("CertifyCode", CertifyCode);
      sqlbv19.put("TempfeeNo", TempfeeNo);
      SSRS tSSRS =  tExeSQL.execSQL(sqlbv19);
	  int rownum=tSSRS.getMaxRow();
	     
	  if(tSSRS == null || rownum ==0)
      {
      	CError.buildErr(this, "未查询到单证编码("+CertifyCode+")单证号码("+TempfeeNo+")的记录，请检查录入数据!") ;        
        return false;
      }
      else
      {
    	 String stateflag = tSSRS.GetText(1, 1);
    	 String receivecom = tSSRS.GetText(1, 2);
    	//2、单证处于可用状态
    	 if(stateflag!=null && !stateflag.equals("3"))
    	 {
    		 CError.buildErr(this, "单证编码("+CertifyCode+")单证号码("+TempfeeNo+")不是处于已发放未核销状态，不能核销!") ;        
    	     return false;
    	 }
    	//3、单证已经发放至该代理人或者其所在机构下	
    	 if(receivecom!=null && receivecom.substring(0, 1).equals("D")&&!receivecom.substring(1).equals(AgentCode))
    	 {
    		 CError.buildErr(this, "单证编码("+CertifyCode+")单证号码("+TempfeeNo+")未发放至代理人("+AgentCode+")，不能核销!") ;        
    	     return false;
    	 }
    	 if(receivecom!=null && receivecom.substring(0, 1).equals("A"))//发放至8位机构
    	 {
    		  String tSQL1 = "select 1 from laagent a where a.agentcode='?AgentCode?' and a.managecom like concat('?managecom?','%')";
    	      SQLwithBindVariables sqlbv20=new SQLwithBindVariables();
    	      sqlbv20.sql(tSQL1);
    	      sqlbv20.put("AgentCode", AgentCode);
    	      sqlbv20.put("managecom", receivecom.substring(1));
    		  String tResult = tExeSQL.getOneValue(sqlbv20);
    	      if(tResult == null || tResult.equals(""))
    	      {
    	    	  CError.buildErr(this, "单证编码("+CertifyCode+")单证号码("+TempfeeNo+")未发放至代理人("+AgentCode+")所在机构，不能核销!") ;        
    	    	  return false;
    	      }
    	 }
    	 if(receivecom!=null && receivecom.substring(0, 1).equals("B"))//发放至部门
    	 {
    		  String tSQL1 = "select 1 from laagent a where a.agentcode='?AgentCode?' and a.managecom like concat('?managecom?','%')";
    	      SQLwithBindVariables sqlbv21=new SQLwithBindVariables();
    	      sqlbv21.sql(tSQL1);
    	      sqlbv21.put("AgentCode", AgentCode);
    	      sqlbv21.put("managecom", receivecom.substring(1,(receivecom.length()-2)));
    		  String tResult = tExeSQL.getOneValue(sqlbv21);
    	      if(tResult == null || tResult.equals(""))
    	      {
    	    	  CError.buildErr(this, "单证编码("+CertifyCode+")单证号码("+TempfeeNo+")未发放至代理人("+AgentCode+")所在部门，不能核销!") ;        
    	    	  return false;
    	      }
    	 }
    	  
      }

      return true;
    }
   
  public static void main(String[] args)
  {
    FineBPODealInputDataBL FineBPODealInputDataBL1 = new FineBPODealInputDataBL();
    
    VData tVData = new VData();
    FineBPODealInputDataBL1.submitData(tVData,"");
  }
}
