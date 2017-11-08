/*
* <p>ClassName: CertifySearchBL </p>
* <p>Description: 单证查询打印的实现文件 </p>
* <p>Copyright: Copyright (c) 2002</p>
* <p>Company: sinosoft </p>
* @Database: lis
* @CreateDate：2002-06-16
 */
package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import java.sql.*;

public class CardBackCancelReportBL  
{
private static Logger logger = Logger.getLogger(CardBackCancelReportBL.class);


  public  CErrors mErrors = new CErrors();
  private VData mResult = new VData();

  /** 全局数据 */
  private GlobalInput m_GlobalInput = new GlobalInput() ;

  /** 数据操作字符串 */
  private String m_strOperate;

  /** 业务处理相关变量 */
  private LZCardTrackSet m_LZCardTrackSet = new LZCardTrackSet();
  private String m_strWhere = "";
  private Hashtable m_hashParams = null;
  private TransferData mTransferData=new TransferData();
  private String operFlag="";//
  private String CalculationDateB="";//统计时间(起始)
  private String CalculationDateE="";//统计时间(终止)
  private String StateFlag;//单证的状态标志：  0:未用  1:正常回收(现缴销) 2:作废(正常作废) 3:挂失(遗失被盗) 4:销毁（新增）
  private String sqlStateA="";//拼sql的单证状态的核销或作废部分(分子)
  private String sqlStateB="";//拼sql的单证状态的已发放部分(分母)
  private String constitutionFlag="";//判断是否以管理机构的进行查询


  public CardBackCancelReportBL() 
  {}

  public static void main(String[] args)
  {
  }

  /**
   * 传输数据的公共方法
   * @param: cInputData 输入的数据
   *         cOperate 数据操作
   * @return:
   */
  public boolean submitData(VData cInputData,String cOperate)
  {
    m_strOperate = verifyOperate(cOperate);
    if( m_strOperate.equals("") ) 
    {
      buildError("submitData", "不支持的操作字符串");
      return false;
    }

    if (!getInputData(cInputData))
      return false;

    if (!dealData())
      return false;

    VData tVData = new VData();
    if( !prepareOutputData(tVData) )
      return false;

    return true;
  }

  /**
   * 根据前面的输入数据，进行BL逻辑处理
   * 如果在处理过程中出错，则返回false,否则返回true
   */
  private boolean dealData()
  {
    try 
    {
      if( m_strOperate.equals("SEARCH||BACK") ) 
      {
        return submitSearchBack();
      } 
      else if( m_strOperate.equals("SEARCH||CONSTITUTION") ) 
      {
          return sumitSearchConstitution();
      } 
      else 
      {
        buildError("dealData", "不支持的操作字符串");
        return false;
      }
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
      return false;
    }
  }
  
  
  
  /**
   * 以管理机构查询：核销率(作废率)的函数
   * @return
   * @throws Exception
   */
  private boolean sumitSearchConstitution() throws Exception
  {
	  mResult.clear();
	    
	  //记录两个的最大数量
	  int tCount=0;
	  int pCount=0;
	    
	  //输出信息，做为验证
	  logger.debug("**********************************");
	  logger.debug("统计日期(开始)是:"+CalculationDateB);
	  logger.debug("统计日期(结束)是:"+CalculationDateE);  
	  
	  //调用函数拼成sql语句
	  VData vData = parseParamsConstitution(m_LZCardTrackSet);
	    
	 
	  SQLwithBindVariables sqlbva = new SQLwithBindVariables();
	  if(vData.getObjectByObjectName("String", 0) instanceof SQLwithBindVariables){
		  sqlbva = (SQLwithBindVariables)vData.getObjectByObjectName("String", 0);
	  }else{
		  //得到拼好的完整sql
		  String strSQL = (String)vData.getObjectByObjectName("String", 0);
		  sqlbva.sql(strSQL);
	  }
//	  logger.debug("*************查询已核销或作废的SQL语句是"+strSQL);
	  SQLwithBindVariables sqlbvb = new SQLwithBindVariables();
      if(vData.getObjectByObjectName("String",1) instanceof SQLwithBindVariables){
		  sqlbvb = (SQLwithBindVariables)vData.getObjectByObjectName("String",1);
	  }else{
		  String strSql=(String)vData.getObjectByObjectName("String",1);
		  sqlbvb.sql(strSql);
	  }
//	  logger.debug("*************查询统计时段内已发放的SQL语句是"+strSql);
	  
	  ExeSQL tExeSQL = new ExeSQL();
	  //执行第一个SQL
	  SSRS tSSRS=tExeSQL.execSQL(sqlbva);
	  tCount=tSSRS.getMaxRow();
	  logger.debug("查询已核销或已作废的记录有" + tCount +"条");
	  //根据返回的数据记录数创建第一个二维数组
	  String[][] str1=new String[tCount][3];
		
	  if(tCount==0)
	  {
		 logger.debug("这段时间内没有已核销或作废的数据,tCount=0");
	  }
	  else
	  {
		//返回的对象的值放入到数组中
		for(int i=0;i<tCount;i++)
	    {
			int s=i+1;
			//logger.debug("第"+s+"条记录");
			str1[i][0]=tSSRS.GetText(s,1);
			//logger.debug("第"+s+"条记录的单证编码是"+str1[i][0]);
			str1[i][1]=tSSRS.GetText(s,2);
			//logger.debug("第"+s+"条记录的管理机构是"+str1[i][1]);
			str1[i][2]=tSSRS.GetText(s,3);
			//logger.debug("第"+s+"条记录的单证数量是"+str1[i][2]);
		}
	  }
		
	  //执行第二个SQL
	  tSSRS=tExeSQL.execSQL(sqlbvb);
	  pCount=tSSRS.getMaxRow();
	  logger.debug("查询未核销或未作废的记录有" + pCount +"条");
	  //根据返回的数据记录数创建第一个二维数组
	  String[][] str2=new String[pCount][3];
	  //返回的对象的值放入到数组中
	  for(int i=0;i<pCount;i++)
	  {
		    int s=i+1;
			//logger.debug("第"+s+"条记录");
			str2[i][0]=tSSRS.GetText(s,1);
			//logger.debug("第"+s+"条记录的单证编码是"+str2[i][0]);
			str2[i][1]=tSSRS.GetText(s,2);
			//logger.debug("第"+s+"条记录的管理机构是"+str2[i][1]);
			str2[i][2]=tSSRS.GetText(s,3);
			//logger.debug("第"+s+"条记录的单证数量是"+str2[i][2]);
			
	  }
		
	  //创建一个ArrayList对象
      ArrayList list=new ArrayList(6);
      //创建第二个ArrayList对象，用来记录所有str1中和str2中匹配的记录在str2中的数组下标，则在循环第二条记录的时候，就可以省去很多记录
      ArrayList save=new ArrayList();
		
	  if(pCount==0)
	  {
			
		 //logger.debug("这段时间内没有已发放的数据,pCount=0，所有查询到的单证的核销率或作废率为特殊字符*");
	     ArrayList array=new ArrayList(6);
		 for(int j=0;j<2;j++)
		 {
		     array.add(str1[0][j]);
		 }
		 //添加日期
		 array.add(CalculationDateB);
		 array.add(CalculationDateE);
		 if(operFlag.equals("0"))
		 {
			    array.add("*");
			    array.add("");
		 }
		 else if(operFlag.equals("1"))
		 {
				array.add("");
			    array.add("*");
		 }
		 else
		 {}
		    
		 list.add(array);
			
	  }
	  //如果没有已核销的单证，所有已核销的单证的核销率为0
	  else if(tCount==0)
	  {
		  logger.debug("在统计时间内没有核销或作废数据，所以所有查询到的单证的核销率或作废率为0");
          //logger.debug("开始计算核销(作废)率：第"+s+"条记录");
          ArrayList array=new ArrayList(6);
          for(int j=0;j<2;j++)
		  {
		       array.add(str2[0][j]);
		   }
		   //添加日期
		  array.add(CalculationDateB);
		  array.add(CalculationDateE);
		  if(operFlag.equals("0"))
		  {
			   array.add("0%");
			   array.add("");
		  }
		  else if(operFlag.equals("1"))
		  {
			   array.add("");
			   array.add("0%");
		  }
		  else
		  {}
		  list.add(array);
	 }
	 else
	 {
		   //循环str1中的每一条记录，根据前三项指标进行比对，如果相同，证实是同种类同一批单证
		   for(int i=0;i<tCount;i++)
		   {
				//首先判断单证编码，然后再判断管理机构，接收者，层层判断
				ArrayList array=new ArrayList(7);
				for(int j=0;j<pCount;j++)
				{
					if(str1[i][0].equals(str2[j][0])&&str1[i][1].equals(str2[j][1]))
					{
						array.add(str1[i][0]);
						array.add(str1[i][1]);
						//添加日期
					    array.add(CalculationDateB);
					    array.add(CalculationDateE);
					        
					    //计算核销率,
					    double a1=Double.parseDouble(str1[i][2]);
					    //logger.debug("a1="+a1);
					    double a2=Double.parseDouble(str2[j][2]);
					    //logger.debug("a2="+a2);
					    String temp=ReportPubFun.functionDivision(a1,a2,"0%");
					    //logger.debug(temp);
					    //将这一条记录添加到ArrayList中
					    if(operFlag.equals("0"))
				        {
					    	array.add(temp);
					        array.add("");
				        }
				        else if(operFlag.equals("1"))
				        {
						    array.add("");
						    array.add(temp);
				        }
				        else
				        {}
					    list.add(array);
					    
					    //将匹配记录在str2中的数组下标记录在Arraylist中
					    logger.debug("匹配记录的数组下标是"+j);
					    String y=Integer.toString(j);
					    //logger.debug(y);
					    save.add(y);
					    break;
					}
					else
					{  
						//到了这里，就证明这一条记录在str2中没有对应的记录，则这条记录的核销率或作废率为%100
						if(j==pCount-1)
						{
							//添加单证编码，管理机构，接收者，统计日期，核销率
							array.add(str1[i][0]);
							array.add(str1[i][1]);
						    array.add(CalculationDateB);
						    array.add(CalculationDateE);
						    if(operFlag.equals("0"))
					        {
						        array.add("*");
						        array.add("");
					        }
					        else if(operFlag.equals("1"))
					        {
							    array.add("");
						        array.add("*");
					        }
					        else
					        {}
						    list.add(array);
						    break;
						}
						else
						{
							continue;
						}						
					}			
				}

			}
			
			//将save这个Arraylist转换为数组
			logger.debug("一共有"+save.size()+"条记录");
			String[] s=new String[save.size()];
			s=(String [])save.toArray(new String[0]);
			
			//将字符数组转换为int数组，为了在下边进行重复元素的匹配时存储的已经被加进来的数组下标
			int[] t=new int[save.size()];
			for(int i=0;i<save.size();i++)
			{
				t[i]=Integer.parseInt(s[i]);
				//logger.debug("t["+i+"]="+t[i]);
			}
			
			
			//再循环str2中的所有记录，只要不是和str1重复的，也把它加进来
			boolean flag=false;
			//logger.debug("save.Size()="+save.size());
		    for (int i = 0; i <pCount; i++) 
		    {
		    	//logger.debug("i="+i);
		    	flag=false;//每次都初始化为false
				for(int j=0;j<save.size();j++)
				{
					//logger.debug("t["+j+"]="+t[j]);
					if(i==t[j])
					{
						//logger.debug("str2中数组下标为"+t[j]+"的元素在上面匹配过，此处不在重复添加");
						flag=true;
						break;
					}

				}
				
				if(flag==false)
				{
					 //表明无法匹配元素
					//logger.debug("单证编码为"+str2[i][0]+"管理机构为"+str2[i][1]+"的数据都未核销或未作废了，核销（作废）率为0");
					ArrayList array=new ArrayList(7);
					//添加单证编码，管理机构，接收者，统计日期，核销率
					array.add(str2[i][0]);
					array.add(str2[i][1]);
				    array.add(CalculationDateB);
				    array.add(CalculationDateE);
				    if(operFlag.equals("0"))
			        {
				        array.add("0%");
				        array.add("");
			        }
			        else if(operFlag.equals("1"))
			        {
					    array.add("");
				        array.add("0%");
			        }
			        else
			        {}
				    list.add(array);
				}   
			}
		}
		
		
		//输出整合后的数据
		int max=list.size();
		//logger.debug("max="+max);
		if(max==0)
		{
			logger.debug("该单证没有任何记录");
			buildError("sumitSearchConstitution", "该单证没有任何核销(作废)和未核销(未作废)的记录");
		    return false;
		}	
		else
		{
			String[][] tfinal=new String[max][7];
			
		    //将ArrayList转换成数据
			for(int i=0;i<max;i++)
			{
			   for(int j=0;j<6;j++)
			   {
				   try
				   {
					   ArrayList array=(ArrayList)list.get(i);
					   tfinal[i]=(String[])array.toArray(new String[0]);
					   if(operFlag.equals("0"))
				        {
						   //证明是核销率,最后一位作废率是""
						   tfinal[i][5]="";
				        }
				        else if(operFlag.equals("1"))
				        {
				        	//证明是作废率,第六位核销率是""
				        	tfinal[i][4]="";
				        }
				        else
				        {}
					    //logger.debug("tfinal["+i+"]["+j+"]="+tfinal[i][j]);
				   }
				   catch(Exception e)
				   {   
		               System.err.println(e.getMessage());   
		           }   

			   }
			}

		    mResult.add( tfinal);
			
		}
	  
	  return true;
  }
  
  
  
  /**
   * 查询核销率和作废率所对应的参数解析函数
   * @param aLZCardTrackSet
   * @return
   * @throws Exception
   */
  private VData parseParamsConstitution(LZCardTrackSet aLZCardTrackSet)
      throws Exception
  {
    LZCardTrackSchema tLZCardTrackSchema = null;

    String strWhere = "";
    String sendOutCom="A";
    String receiveCom="D";
    String strSQL = "";//已核销或已作废的单证sql
    String strSql = "";//未核销或未作废的单证sql
    
    //strSQL和strSql是用来查询具体列的数据
  	//logger.debug("*******所选操作是核销率查询");
  	strSQL = "SELECT CertifyCode,Receivecom,Sum(SumCount) FROM LZCardTrack WHERE StateFlag "+sqlStateA;
  	strSql = "SELECT CertifyCode,SendOutCom,Sum(SumCount) FROM LZCardTrack WHERE OperateFlag "+sqlStateB;

    //获得从Schema中传过来的数据
    tLZCardTrackSchema = aLZCardTrackSet.get(1);
    
    //在数据库中机构代码开头为A,业务员代码开头为D
    sendOutCom +=tLZCardTrackSchema.getSendOutCom();
    logger.debug("组合成的发送机构是"+sendOutCom);
    receiveCom +=tLZCardTrackSchema.getReceiveCom();
    logger.debug("组合成的接收者是"+receiveCom);
    
    strWhere += getWherePart("CertifyCode", sfun(tLZCardTrackSchema.getCertifyCode(),"CertifyCode"));
    
    strSQL += getWherePart("receiveCom",sfun(sendOutCom,"sendOutCom") )+" and SendOutCom like concat('?receiveCom?','%')";
    strSql += getWherePart("SendOutCom",sfun(sendOutCom,"sendOutCom") )+" and receiveCom like concat('?receiveCom?','%')";
    

    strSQL += " and MakeDate>='"+"?date1?"+"' and MakeDate<='"+"?date2?"+"'";
    strSQL += " group by CertifyCode,Receivecom ORDER BY CertifyCode,ReceiveCom";
    
    strSql += strWhere;
    strSql += " and MakeDate>='"+"?date1?"+"' and MakeDate<='"+"?date2?"+"'";

    strSql += " group by CertifyCode,SendOutCom ORDER BY CertifyCode,SendOutCom";
    
    //输入sql
    logger.debug("parseParamsPrint*********strSQL : " + strSQL);
	logger.debug("parseParamsPrint*********strSql : " + strSql);
	
	SQLwithBindVariables sqlbv = new SQLwithBindVariables();
	sqlbv.sql(strSQL);
	sqlbv.put("CertifyCode", tLZCardTrackSchema.getCertifyCode());
	sqlbv.put("sendOutCom", sendOutCom);
	sqlbv.put("receiveCom", receiveCom);
	sqlbv.put("date1", CalculationDateB);
	sqlbv.put("date2", CalculationDateE);
	
	SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
	sqlbv1.sql(strSql);
	sqlbv1.put("CertifyCode", tLZCardTrackSchema.getCertifyCode());
	sqlbv1.put("sendOutCom", sendOutCom);
	sqlbv1.put("receiveCom", receiveCom);
	sqlbv1.put("date1", CalculationDateB);
	sqlbv1.put("date2", CalculationDateE);
    VData tVData = new VData();
    tVData.add(0,sqlbv);
    tVData.add(1,sqlbv1);

    return tVData;
  }

  
  /**
   * 查询所有接收者：核销率(作废率)的函数
   * @return
   * @throws Exception
   */
  private boolean submitSearchBack() throws Exception
  {
    mResult.clear();
    
    //记录两个的最大数量
    int tCount=0;
    int pCount=0;
    
    //输出信息，做为验证
    logger.debug("**********************************");
    logger.debug("统计日期(开始)是:"+CalculationDateB);
    logger.debug("统计日期(结束)是:"+CalculationDateE);  

    //调用函数拼成sql语句
    VData vData = parseParamsPrint(m_LZCardTrackSet);
    
    
    SQLwithBindVariables sqlbva = new SQLwithBindVariables();
    if(vData.getObjectByObjectName("String", 0) instanceof SQLwithBindVariables){
    	sqlbva = (SQLwithBindVariables)vData.getObjectByObjectName("String", 0);
    }else{
    	//得到拼好的完整sql
        String strSQL = (String)vData.getObjectByObjectName("String", 0);
        sqlbva.sql(strSQL);
    }
//    logger.debug("*************查询已核销或作废的SQL语句是"+strSQL);
    
    SQLwithBindVariables sqlbvb = new SQLwithBindVariables();
    if(vData.getObjectByObjectName("String",1) instanceof SQLwithBindVariables){
    	sqlbvb = (SQLwithBindVariables)vData.getObjectByObjectName("String",1);
    }else{
    	String strSql=(String)vData.getObjectByObjectName("String",1);
    	sqlbvb.sql(strSql);
    }
//    logger.debug("*************查询统计时段内已发放的SQL语句是"+strSql);
    
	ExeSQL tExeSQL = new ExeSQL();
	//执行第一个SQL
	SSRS tSSRS=tExeSQL.execSQL(sqlbva);
	tCount=tSSRS.getMaxRow();
	logger.debug("查询已核销或已作废的记录有" + tCount +"条");
	//根据返回的数据记录数创建第一个二维数组
	String[][] str1=new String[tCount][4];
	
	if(tCount==0)
	{
		logger.debug("这段时间内没有已核销或作废的数据,tCount=0");
	}
	else
	{
		//返回的对象的值放入到数组中
		for(int i=0;i<tCount;i++)
		{
			int s=i+1;
			//logger.debug("第"+s+"条记录");
			str1[i][0]=tSSRS.GetText(s,1);
			//logger.debug("第"+s+"条记录的单证编码是"+str1[i][0]);
			str1[i][1]=tSSRS.GetText(s,2);
			//logger.debug("第"+s+"条记录的管理机构是"+str1[i][1]);
			str1[i][2]=tSSRS.GetText(s,3);
			//logger.debug("第"+s+"条记录的接收者是"+str1[i][2]);
			str1[i][3]=tSSRS.GetText(s,4);
			//logger.debug("第"+s+"条记录的单证数量是"+str1[i][3]);
		}
	}
	
	//执行第二个SQL
	tSSRS=tExeSQL.execSQL(sqlbvb);
	pCount=tSSRS.getMaxRow();
	logger.debug("查询未核销或未作废的记录有" + pCount +"条");
	//根据返回的数据记录数创建第一个二维数组
	String[][] str2=new String[pCount][4];
	//返回的对象的值放入到数组中
	for(int i=0;i<pCount;i++)
	{
		int s=i+1;
		//logger.debug("第"+s+"条记录");
		str2[i][0]=tSSRS.GetText(s,1);
		//logger.debug("第"+s+"条记录的单证编码是"+str2[i][0]);
		str2[i][1]=tSSRS.GetText(s,2);
		//logger.debug("第"+s+"条记录的管理机构是"+str2[i][1]);
		str2[i][2]=tSSRS.GetText(s,3);
		//logger.debug("第"+s+"条记录的接收者是"+str2[i][2]);
		str2[i][3]=tSSRS.GetText(s,4);
		//logger.debug("第"+s+"条记录的单证数量是"+str2[i][3]);
		
	}
	
	//创建一个ArrayList对象
	ArrayList list=new ArrayList(7);
	//创建第二个ArrayList对象，用来记录所有str1中和str2中匹配的记录在str2中的数组下标，则在循环第二条记录的时候，就可以省去很多记录
	ArrayList save=new ArrayList();
	//Integer y=null;
	
	//如果没有已发放的单证，所有已核销的单证的核销率为1循环的次数是集合1所含有的记录数量
	if(pCount==0)
	{
		
		//logger.debug("这段时间内没有已发放的数据,pCount=0，所有查询到的单证的都不存在核销率或作废率");
		for(int i=0;i<tCount;i++)
		{
			//int s=i+1;
			//logger.debug("开始计算核销率或作废率：第"+s+"条记录");
            ArrayList array=new ArrayList(7);
	        for(int j=0;j<3;j++)
	        {
	        	array.add(str1[i][j]);
	        }
	        //添加日期
	        array.add(CalculationDateB);
	        array.add(CalculationDateE);
	        if(operFlag.equals("0"))
	        {
	        	//*代表分母为0的核销率或作废率
		        array.add("*");
		        array.add("");
	        }
	        else if(operFlag.equals("1"))
	        {
			    array.add("");
		        array.add("*");
	        }
	        else
	        {}
	        list.add(array);
		}
		
	}
	//如果没有已核销的单证，所有已核销的单证的核销率为0
	else if(tCount==0)
	{
		logger.debug("在统计时间内没有核销或作废数据，所以所有查询到的单证的核销率或作废率为0");
		for(int i=0;i<pCount;i++)
		{
			//int s=i+1;
			//logger.debug("开始计算核销(作废)率：第"+s+"条记录");
            ArrayList array=new ArrayList(7);
	        for(int j=0;j<3;j++)
	        {
	        	array.add(str2[i][j]);
	        }
	        //添加日期
	        array.add(CalculationDateB);
	        array.add(CalculationDateE);
	        if(operFlag.equals("0"))
	        {
		        array.add("0%");
		        array.add("");
	        }
	        else if(operFlag.equals("1"))
	        {
			    array.add("");
		        array.add("0%");
	        }
	        else
	        {}
	        list.add(array);
		}
	}
	else
	{
		//循环str1中的每一条记录，根据前三项指标进行比对，如果相同，证实是同种类同一批单证
		for(int i=0;i<tCount;i++)
		{
			//首先判断单证编码，然后再判断管理机构，接收者，层层判断
			ArrayList array=new ArrayList(7);
			for(int j=0;j<pCount;j++)
			{
				if(str1[i][0].equals(str2[j][0]))
				{
					if(str1[i][1].equals(str2[j][1]))
					{
						if(str1[i][2].equals(str2[j][2]))
						{
							//logger.debug("两条记录的接收者也相同，证明是同种类同一批的单证");
							array.add(str1[i][0]);
							array.add(str1[i][1]);
							array.add(str1[i][2]);
							//添加日期
						    array.add(CalculationDateB);
						    array.add(CalculationDateE);
						        
						    //计算核销率,
						    double a1=Double.parseDouble(str1[i][3]);
						    //logger.debug("a1="+a1);
						    double a2=Double.parseDouble(str2[j][3]);
						    //logger.debug("a2="+a2);
						    String temp=ReportPubFun.functionDivision(a1,a2,"0%");
						    //logger.debug(temp);
						    //将这一条记录添加到ArrayList中
						    if(operFlag.equals("0"))
					        {
						    	array.add(temp);
						        array.add("");
					        }
					        else if(operFlag.equals("1"))
					        {
							    array.add("");
							    array.add(temp);
					        }
					        else
					        {}
						    list.add(array);
						    
						    //将匹配记录在str2中的数组下标记录在Arraylist中
						    logger.debug("匹配记录的数组下标是"+j);
						    String y=Integer.toString(j);
						    //logger.debug(y);
						    save.add(y);
						    break;
					     }
						else
						{   
							//到了这里，就证明这一条记录在str2中没有对应的记录，核销率或作废率为*
							if(j==pCount-1)
							{
								//添加单证编码，管理机构，接收者，统计日期，核销率
								array.add(str1[i][0]);
								array.add(str1[i][1]);
								array.add(str1[i][2]);
							    array.add(CalculationDateB);
							    array.add(CalculationDateE);
							    if(operFlag.equals("0"))
						        {
							        array.add("*");
							        array.add("");
						        }
						        else if(operFlag.equals("1"))
						        {
								    array.add("");
							        array.add("*");
						        }
						        else
						        {}
							    list.add(array);
							    break;
							}
							else
							{
								continue;
							}
						}
					}
				}
				else
				{
						
				}
			}

		}
		
		//将save这个Arraylist转换为数组
		logger.debug("一共有"+save.size()+"条记录");
		String[] s=new String[save.size()];
		s=(String [])save.toArray(new String[0]);
		
		//将字符数组转换为int数组
		int[] t=new int[save.size()];
		for(int i=0;i<save.size();i++)
		{
			t[i]=Integer.parseInt(s[i]);
			//logger.debug("t["+i+"]="+t[i]);
		}
		
		
		//再循环str2中的所有记录，只要不是和str1重复的，也把它加进来
		boolean flag=false;
		logger.debug("save.Size()="+save.size());
	    for (int i = 0; i <pCount; i++) 
	    {
	    	//logger.debug("i="+i);
	    	flag=false;//每次都初始化未false
			for(int j=0;j<save.size();j++)
			{
				//logger.debug("t["+j+"]="+t[j]);
				if(i==t[j])
				{
					//logger.debug("str2中数组下标为"+t[j]+"的元素在上面匹配过，此处不在重复添加");
					flag=true;
					break;
				}

			}
			
			if(flag==false)
			{
				 //表明无法匹配元素
				//logger.debug("单证编码为"+str2[i][0]+"管理机构为"+str2[i][1]+"接收者为"+str2[i][2]+"的数据都未核销或未作废了，核销（作废）率为0");
				ArrayList array=new ArrayList(7);
				//添加单证编码，管理机构，接收者，统计日期，核销率
				array.add(str2[i][0]);
				array.add(str2[i][1]);
				array.add(str2[i][2]);
			    array.add(CalculationDateB);
			    array.add(CalculationDateE);
			    
			    if(operFlag.equals("0"))
		        {
			        array.add("0%");
			        array.add("");
		        }
		        else if(operFlag.equals("1"))
		        {
				    array.add("");
			        array.add("0%");
		        }
		        else
		        {}
			    list.add(array);
			}   
		}
	}
	
	
	//输出整合后的数据
	int max=list.size();
	//logger.debug("max="+max);
	if(max==0)
	{
		logger.debug("该单证没有任何记录");
		buildError("submitSearchBack", "该单证没有任何核销(作废)和已发放的记录");
	    return false;
	}	
	else
	{
		String[][] tfinal=new String[max][7];
		
	    //将ArrayList转换成数据
		for(int i=0;i<max;i++)
		{
		   for(int j=0;j<7;j++)
		   {
			   try
			   {
				   ArrayList array=(ArrayList)list.get(i);
				   tfinal[i]=(String[])array.toArray(new String[0]);
				   if(operFlag.equals("0"))
			        {
					   //证明是核销率,最后一位作废率是""	
					    tfinal[i][6]="";
			        }
			        else if(operFlag.equals("1"))
			        {
			        	//证明是作废率,第六位核销率是""
			        	tfinal[i][5]="";
			        }
			        else
			        {}
				    //logger.debug("tfinal["+i+"]["+j+"]="+tfinal[i][j]);
			   }
			   catch(Exception e)
			   {   
	               System.err.println(e.getMessage());   
	           }   

		   }
		}
		
	    mResult.add( tfinal);
		
	}
	
    
    
    return true;
  }
  
  
  /**
   * 查询核销率和作废率所对应的参数解析函数
   * @param aLZCardTrackSet
   * @return
   * @throws Exception
   */
  private VData parseParamsPrint(LZCardTrackSet aLZCardTrackSet)
      throws Exception
  {
    LZCardTrackSchema tLZCardTrackSchema = null;

    String strWhere = "";
    String sendOutCom="A";
    String receiveCom="D";
    String strSQL = "";//已核销或已作废的单证sql
    String strSql = "";//未核销或未作废的单证sql
    
    //strSQL和strSql是用来查询具体列的数据
  	//logger.debug("*******所选操作是核销率查询");
  	strSQL = "SELECT CertifyCode,Receivecom,SendOutCom,Sum(SumCount) FROM LZCardTrack WHERE StateFlag "+sqlStateA;
  	strSql = "SELECT CertifyCode,SendOutCom,Receivecom,Sum(SumCount) FROM LZCardTrack WHERE OperateFlag "+sqlStateB;

    //获得从Schema中传过来的数据
    tLZCardTrackSchema = aLZCardTrackSet.get(1);
    
    //在数据库中机构代码开头为A,业务员代码开头为D
    sendOutCom +=tLZCardTrackSchema.getSendOutCom();
    logger.debug("组合成的发送机构是"+sendOutCom);
    receiveCom +=tLZCardTrackSchema.getReceiveCom();
    logger.debug("组合成的接收者是"+receiveCom);
    
    strWhere += getWherePart("CertifyCode", sfun(tLZCardTrackSchema.getCertifyCode(),"CertifyCode"));
    
    strSQL += getWherePart("receiveCom",sendOutCom )+" and SendOutCom like concat('?receiveCom?','%')";
    strSql += getWherePart("SendOutCom",sendOutCom )+" and receiveCom like concat('?receiveCom?','%')";
    

    strSQL += " and MakeDate>='"+"?date1?"+"' and MakeDate<='"+"?date2?"+"'";
    strSQL += " group by CertifyCode,SendOutCom,Receivecom ORDER BY CertifyCode, ReceiveCom,Sum(SumCount) desc";
    
    strSql += strWhere;
    strSql += " and MakeDate>='"+"?date1?"+"' and MakeDate<='"+"?date2?"+"'";
    //strSql += " and MakeDate<='"+CalculationDateE+"'";
    //strSql += " and MakeDate>='2005-01-01' and MakeDate<='"+CalculationDateE+"'";
    strSql += " group by CertifyCode,SendOutCom,Receivecom ORDER BY CertifyCode, SendOutCom, Sum(SumCount) desc";
    
    //输入sql
    logger.debug("parseParamsPrint*********strSQL : " + strSQL);
	logger.debug("parseParamsPrint*********strSql : " + strSql);

    VData tVData = new VData();
    SQLwithBindVariables sqlbv = new SQLwithBindVariables();
	sqlbv.sql(strSQL);
	sqlbv.put("CertifyCode", tLZCardTrackSchema.getCertifyCode());
	sqlbv.put("sendOutCom", sendOutCom);
	sqlbv.put("receiveCom", receiveCom);
	sqlbv.put("date1", CalculationDateB);
	sqlbv.put("date2", CalculationDateE);
	
	SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
	sqlbv1.sql(strSql);
	sqlbv1.put("CertifyCode", tLZCardTrackSchema.getCertifyCode());
	sqlbv1.put("sendOutCom", sendOutCom);
	sqlbv1.put("receiveCom", receiveCom);
	sqlbv1.put("date1", CalculationDateB);
	sqlbv1.put("date2", CalculationDateE);
    tVData.add(0,sqlbv);
    tVData.add(1,sqlbv1);

    return tVData;
  }
 

  /**
   * 从输入数据中得到所有对象
   * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   * 组成sql的变化部分
   */
  private boolean getInputData(VData cInputData)
  {
    try 
    {
	      //获得从页面传入的数据
    	  this.m_GlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
	      m_LZCardTrackSet = (LZCardTrackSet)cInputData.getObjectByObjectName("LZCardTrackSet", 0);
	      m_hashParams = (Hashtable)cInputData.getObjectByObjectName("Hashtable", 0);
	      mTransferData=(TransferData)cInputData.getObjectByObjectName("TransferData",0);
	      
          //从容器中获得页面传入的数据
	      CalculationDateB= (String)mTransferData.getValueByName("CalculationDateB");
	      CalculationDateE= (String)mTransferData.getValueByName("CalculationDateE");
	      operFlag= (String)mTransferData.getValueByName("operFlag");
	      constitutionFlag=(String)mTransferData.getValueByName("constitutionFlag");
	      
	      //输出信息，做为验证
	      logger.debug("统计日期(开始)是:"+CalculationDateB);
	      logger.debug("统计日期(结束)是:"+CalculationDateE);  
	      logger.debug("所选操作标志是:"+operFlag); 
	      logger.debug("*************是否以管理机构查询的标志是"+constitutionFlag); 
	      

	      if( m_LZCardTrackSet == null ) 
	      {
	        buildError("getInputData", "没有传入所需要的查询条件");
	        return false;
	      }

	      if( operFlag== null || operFlag.equals("") ) 
	      {
	        buildError("getInputData", "请选择操作类型");
	        return false;
	      }
	      
	      //根据operFlag的值进行判断
	      if(operFlag.equals("0"))
	      {
	    	  logger.debug("*******所选操作是核销率查询");
	    	  sqlStateA="in('1','2','3')";
	    	  sqlStateB=" = '0' ";
	      }
	      else if(operFlag.equals("1"))
	      {
	    	  logger.debug("*******所选操作是作废率查询");
	    	  sqlStateA="in('2','3')";
	    	  sqlStateB=" = '0' ";
	      }
	      else
	      {
	    	  logger.debug("*******所选操作未定义处理办法");
	    	  buildError("getInputData", "没有选择任何操作类型");
	  	      return false;
	      }

    } 
    catch(Exception ex) 
    {
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

  /*
  * add by kevin, 2002-10-14
   */
  private void buildError(String szFunc, String szErrMsg)
  {
    CError cError = new CError( );

    cError.moduleName = "CertifySearchBL";
    cError.functionName = szFunc;
    cError.errorMessage = szErrMsg;
    this.mErrors.addOneError(cError);
  }

  private String verifyOperate(String szOperate)
  {
    String szReturn = "";
    String szOperates[] = {"SEARCH||BACK","SEARCH||CONSTITUTION"};

    for(int nIndex = 0; nIndex < szOperates.length; nIndex ++) {
      if( szOperate.equals(szOperates[nIndex]) ) {
        szReturn = szOperate;
      }
    }

    return szReturn;
  }

  private String getWherePart(String strColName, String strColValue)
  {
    if( strColValue == null || strColValue.equals("") ) 
    {
      return "";
    }
    return " AND " + strColName + " = '" + strColValue + "'";
  }

  private String getWherePart(String strCol1, String strCol2, String strCol3, int nFlag)
  {
    if( nFlag == 0 ) 
    {
      if( strCol3 == null || strCol3.equals("") ) 
      {
        return "";
      }

      return " AND "  + strCol1 + " >= '" + strCol3 + "'";
    } 
    else if(nFlag == 2 )
    {
    	if( strCol3 == null || strCol3.equals("") ) 
        {
          return "";
        }

        return " AND " + strCol2 + " <= '" + strCol3 + "'";
    }
    else 
    {
      String str = "";

      if( strCol2 == null || strCol2.equals("") ) 
      {
        str += "";
      } 
      else 
      {
        str += " AND " + strCol1 + " >= '" + strCol2 + "'";
      }

      if( strCol3 == null || strCol3.equals("") ) 
      {
        str += "";
      } 
      else 
      {
        str += " AND " + strCol1 + " <= '" + strCol3 + "'";
      }

      return str;
    }
  }
  
  private String sfun(String str1, String str2){
	  if(str2 == null || str2.equals("")) return null;
	  return "?"+str2+"?";
  }
   
 
}
