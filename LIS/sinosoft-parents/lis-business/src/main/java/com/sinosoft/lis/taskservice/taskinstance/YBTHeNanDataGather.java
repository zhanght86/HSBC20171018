package com.sinosoft.lis.taskservice.taskinstance;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
//import com.enterprisedt.net.ftp.FTPClient;
//import com.enterprisedt.net.ftp.FTPConnectMode;
//import com.enterprisedt.net.ftp.FTPException;
//import com.enterprisedt.net.ftp.FTPMessageCollector;
//import com.enterprisedt.net.ftp.FTPTransferType;
//import com.sinosoft.lis.test3.Hello;

/**
 * 河南邮储数据采集，每天自动生成三个数据采集文件，并上传到指定服务器
 * 2010-03-02 修改为河南邮政数据采集
 * @author Administrator
 *
 */

public class YBTHeNanDataGather extends TaskThread{
private static Logger logger = Logger.getLogger(YBTHeNanDataGather.class);
	
	ExeSQL tExeSql = new ExeSQL();
	String Currenday = PubFun.getCurrentDate();		
	String pattern = "yyyyMMdd";
	SimpleDateFormat df = new SimpleDateFormat(pattern);
	Date today = new Date();
	String tString = df.format(today);
	
    /**文件存放的路径*/
    String FilePath = "/datafile6/YBT/HeNanData/";	
    String FileName1 = "0013_1_"+tString+".txt";
    String FileName2 = "0013_2_"+tString+".txt";
    String FileName3 = "0013_3_"+tString+".txt";
	
	public YBTHeNanDataGather() {
	  }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		YBTHeNanDataGather tYBTHeNanDataGather = new YBTHeNanDataGather();
		if(tYBTHeNanDataGather.dealMain())
		{
			logger.debug("finish");
		}
	}

	public boolean dealMain() {
		// TODO Auto-generated method stub
		logger.debug("YBTHeNanDataGather");
		String tYBTSQL = " and exists (select 1 from lccont b where contno = a.contno and managecom like '8641%'"
        +" and salechnl='03' and selltype = '08'"
        +" and b.agentcom in (select agentcom from lkcodemapping where bankcode = '1201' and zoneno = '4101'))"
        +" and exists(select 1 from lktransstatus where polno=a.contno and bankcode='1201' and funcflag='00' and temp='1')";
		//实时出单业务犹豫期退保 文件名 0013_1_20080318.txt		
		//总退保笔数 
		String sql=" select count(*) from LPEdorItem a where a.EdorType = 'WT' and a.EdorState='0' and a.EdorValiDate = '"+"?Currenday?"+"' "
		          + tYBTSQL ;
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("Currenday", Currenday);
		String count1= tExeSql.getOneValue(sqlbv1);
		if(count1.equals("0"))
		{
			logger.debug("当天没有实时出单业务犹豫期撤单信息");
			FileWriter fw = null;
			try {
				fw = new FileWriter(FilePath+FileName1);			
			    BufferedWriter bw=new BufferedWriter(fw);			
				bw.write("0,0.00");				
				bw.flush();//将数据更新至文件
				fw.close();//关闭文件流
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		else
		{
            //总退保笔数 、总退保金额
			String sql1=" select concat(concat(count(*),','),trim(to_char(sum(c.prem),99999999.99))) a,'1' b from LPEdorItem a, lccont b,lcpol c"
                       +" where a.contno = b.contno and b.contno=c.contno and a.EdorType = 'WT' and a.EdorState='0' and a.EdorValiDate = '"+"?Currenday?"+"' and b.managecom like '8641%'"
                       +" and b.agentcom in (select agentcom from lkcodemapping where bankcode = '1201' and zoneno = '4101')"
                       +" and b.salechnl='03' and b.selltype = '08'"  
                       +" and exists(select 1 from lktransstatus where polno=a.contno and bankcode='1201' and funcflag='00' and temp='1')"
                       +" union"   
                       +" select concat(concat(trim(b.contno) , ',') , trim(to_char(c.prem, 99999999.99))) a, '2' b from LPEdorItem a, lccont b,lcpol c"
                       +" where a.contno = b.contno and b.contno=c.contno and a.EdorType = 'WT' and a.EdorState='0' and a.EdorValiDate = '"+"?Currenday?"+"' and b.managecom like '8641%'"
                       +" and b.agentcom in (select agentcom from lkcodemapping where bankcode = '1201' and zoneno = '4101')"
                       +" and b.salechnl='03' and b.selltype = '08'"  
                       +" and exists(select 1 from lktransstatus where polno=a.contno and bankcode='1201' and funcflag='00' and temp='1') order by b";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(sql1);
			sqlbv2.put("Currenday", Currenday);
			SSRS ssrs1 = tExeSql.execSQL(sqlbv2);
			FileWriter fw = null;
			try {
				fw = new FileWriter(FilePath+FileName1);			
			    BufferedWriter bw=new BufferedWriter(fw);				
				for(int i=1 ; i <= ssrs1.getMaxRow() ; i++)
				{					
					bw.write(ssrs1.GetText(i,1));
					bw.write("\r\n");
					//bw.newLine();//断行	
				}			
				bw.flush();//将数据更新至文件
				fw.close();//关闭文件流
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}			
		}
		
//		//非实时出单承保信息 文件名0013_2_20080318.txt		
//		String sql_a=" select count(*) from lccont where managecom like '8641%' and prtno like '861501%' and appflag = '1' and selltype<>'08'"
//                    +" and agentcom in (select agentcom from lkcodemapping where bankcode = '1200' and zoneno = '4101') and signdate = '"+Currenday+"'";
//	    String count2= tExeSql.getOneValue(sql_a);
//	    if(count2.equals("0"))
//	    {
//		   logger.debug("当天没有非实时出单业务承保信息");
//		   FileWriter fw = null;
//		   try {
//			  fw = new FileWriter(FilePath+FileName2);			
//		      BufferedWriter bw=new BufferedWriter(fw);			
//			  bw.write("0,0.00");				
//			  bw.flush();//将数据更新至文件
//			  fw.close();//关闭文件流
//		      } catch (IOException e) {
//			 // TODO Auto-generated catch block
//			  e.printStackTrace();
//			  return false;
//		    }
//	    }
//	    else
//	    {
//          //总承保笔数 、总承保金额
//		   String sql2=" select count(*)||','||trim(to_char(sum(Prem),99999999.99)) a, '1' b from lccont where managecom like '8641%'"
//                      +" and prtno like '861501%' and signdate = '"+Currenday+"' and appflag = '1' and selltype<>'08'"
//                      +" and agentcom in (select agentcom from lkcodemapping where bankcode = '1200' and zoneno = '4101')"	
//                      +" union"
//                      +" select trim(PrtNo)||','||contno a, '2' b from lccont where managecom like '8641%' and prtno like '861501%'"
//                      +" and signdate = '"+Currenday+"' and appflag = '1' and selltype<>'08' and agentcom in (select agentcom from lkcodemapping"
//                      +" where bankcode = '1200' and zoneno = '4101')  order by b";
//		   SSRS ssrs2 = tExeSql.execSQL(sql2);
//		   FileWriter fw = null;
//		   try {
//			  fw = new FileWriter(FilePath+FileName2);			
//		      BufferedWriter bw=new BufferedWriter(fw);			
//			  for(int i=1 ; i <= ssrs2.getMaxRow() ; i++)
//			  {				
//				 bw.write(ssrs2.GetText(i,1));	
//				 bw.write("\r\n");
//			  }			
//			  bw.flush();//将数据更新至文件
//			  fw.close();//关闭文件流
//		    } catch (IOException e) {
//			// TODO Auto-generated catch block
//			 e.printStackTrace();
//			 return false;
//		   }		
//	     }
//	    
//	    //非实时出单退保信息
//	    String sql_b=" select count(*) from LPEdorItem where edortype = 'WT' and EdorState='0' and EdorValiDate = '"+Currenday+"'"
//                    +" and contno in (select contno from lccont where prtno like '861501%' and managecom like '8641%'"
//                    +" and agentcom in (select agentcom from lkcodemapping where bankcode = '1200' and zoneno = '4101') and selltype<>'08' and appflag = '4')";
//        String count3= tExeSql.getOneValue(sql_b);
//        if(count3.equals("0"))
//        {
//        	   logger.debug("当天没有非实时出单业务退保信息");
//        	   FileWriter fw = null;
//        	   try {
//        		  fw = new FileWriter(FilePath+FileName3);			
//        	      BufferedWriter bw=new BufferedWriter(fw);			
//        		  bw.write("0,0.00");				
//        		  bw.flush();//将数据更新至文件
//        		  fw.close();//关闭文件流
//        	      } catch (IOException e) {
//        		 // TODO Auto-generated catch block
//        		  e.printStackTrace();
//        		  return false;
//        	    }
//        }
//        else
//        {
//               //总退保笔数 、总退保金额
//        	   String sql3=" select count(*)||','||trim(to_char(sum(b.prem), 99999999.99)) a, '1' b from LPEdorItem a, lcpol b"
//                          +" where a.contno = b.contno and a.edortype = 'WT' and a.EdorState='0' and a.EdorValiDate = '"+Currenday+"'"
//                          +" and b.managecom like '8641%' and b.agentcom in (select agentcom from lkcodemapping where bankcode = '1200' and zoneno = '4101')"
//                          +" and b.prtno like '861501%' and b.appflag = '4' and exists(select 1 from lccont where contno=b.contno and selltype<>'08')"
//                          +" union"
//                          +" select trim(b.PrtNo)||','||'2'||','|| trim(to_char(b.prem, 99999999.99)) a,'2' b from LPEdorItem a, lcpol b"
//                          +" where a.contno = b.contno and a.edortype = 'WT' and a.EdorState='0' and a.EdorValiDate = '"+Currenday+"'"
//                          +" and b.managecom like '8641%' and b.agentcom in (select agentcom from lkcodemapping where bankcode = '1200' and zoneno = '4101')"
//                          +" and b.prtno like '861501%' and b.appflag = '4' and exists(select 1 from lccont where contno=b.contno and selltype<>'08')order by b";
//        	   SSRS ssrs3 = tExeSql.execSQL(sql3);
//        	   FileWriter fw = null;
//        	   try {
//        		  fw = new FileWriter(FilePath+FileName3);			
//        	      BufferedWriter bw=new BufferedWriter(fw);	        		
//        		  for(int i=1 ; i <= ssrs3.getMaxRow() ; i++)
//        		  {        			
//        			 bw.write(ssrs3.GetText(i,1));
//        			 bw.write("\r\n");
//        		  }			
//        		  bw.flush();//将数据更新至文件
//        		  fw.close();//关闭文件流
//        	    } catch (IOException e) {
//        		// TODO Auto-generated catch block
//        		 e.printStackTrace();
//        		 return false;
//        	   }		
//        }
        logger.debug("文件生成完毕。。。");
        //文件生成完毕，开始上传到指定服务器：10.247.0.30
//        if(!FileUpload())
//        {
//        	logger.debug("File Upload Failed.....");
//        	return false;
//        }
//        logger.debug("deal over");
		
		return true;
	}

	/**
	 * 文件上传
	 * @return
	 */
//	private boolean FileUpload() {
//		// TODO Auto-generated method stub
//		String FTPSERVER = "10.0.22.9";
//	     
//	      /**ftp server 端口,ftp默认的端口都是21*/
//	      int FTPPORT = 21;
//	     
//	      /**ftp 用户名 */
//	      String FTPUSER ="bea";
//	     
//	      /**ftp 用户密码 */
//	      String FTPPSWD ="msweb9";
//	      
//	      try { 
//	    	    FTPClient ftp= new FTPClient();
//	    	    ftp.setRemoteHost(FTPSERVER);
//	    	    ftp.setRemotePort(FTPPORT);
//	    	    ftp.setControlEncoding("GB2312");
//	    	    FTPMessageCollector listener = new FTPMessageCollector();
//	    	             ftp.setMessageListener(listener);   
//	    	             ftp.connect();
//	    	             ftp.login(FTPUSER,FTPPSWD);
//	    	             /**设置连接模式 */
//	    	             ftp.setConnectMode(FTPConnectMode.ACTIVE);
//	    	             /**设置传送模式  为二进制模式*/
//	    	             ftp.setType(FTPTransferType.BINARY);
//	    	             /**put(源，目的)这里的路径可以用相对路径或绝对路径*/
//	    	             ftp.put(FilePath+FileName1,"/app/test/"+FileName1);
//	    	             ftp.put(FilePath+FileName2,"/app/test/"+FileName2);
//	    	             ftp.put(FilePath+FileName3,"/app/test/"+FileName3);
//	    	             ftp.quit();	    	            
//	    	    } catch (IOException e) { 
//	    	     e.printStackTrace();
//	    	    } catch (FTPException e) {
//	    	     e.printStackTrace();
//	    	    }	     
//	    	   
//	    	  logger.debug("upload ......");
//	     
//		return true;
//	}

}
