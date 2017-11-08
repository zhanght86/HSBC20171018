<%
//程序名称：ProposalComPrintInput.jsp
//程序功能：
//创建日期：2006-10-26 17:13
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
   <%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 

<%!
  InputStream ins = null;
  String PrintContent=""; 	
  String FlagStr = "";
  String Content = "";
  int tFail=0;
  int tSucc=0;
  boolean handleFunction(HttpSession session, HttpServletRequest request) 
  {
    loggerDebug("ProposalComPrintSave_IDGF","111");
    int nIndex = 0;
    String strSQL = "";
    
    String tLCPolGrids[] = request.getParameterValues("PolGridNo");
    String tManageComs[] = request.getParameterValues("PolGrid1");
    String tSumCount[] = request.getParameterValues("PolGrid2");
    String tChecks[] = request.getParameterValues("InpPolGridChk");

    GlobalInput globalInput = new GlobalInput();
		
    if( (GlobalInput)session.getValue("GI") == null ) 
    {
      	 Content+="网页超时或者是没有操作员信息，请重新登录";
         return false;
    } 
    else 
    {
      globalInput.setSchema((GlobalInput)session.getValue("GI"));
    }
    
    String tManageCom = request.getParameter("ManageCom");
    String tComCode = globalInput.ComCode;
		
    if( tLCPolGrids == null ) 
    {
   	 Content+="没有输入需要的打印参数";
     return false;
    }

    LCContSet tLCContSet = new LCContSet();
   // LCPolF1P_IDGFUI tLCPolF1P_IDGFUI = new LCPolF1P_IDGFUI();
	
    
    String busiName="f1printLCPolF1P_IDGFUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    
    for(nIndex = 0; nIndex < tChecks.length; nIndex++ ) 
    {
      loggerDebug("ProposalComPrintSave_IDGF","nIndex="+nIndex);
      // If this line isn't selected, continue
      if( !tChecks[nIndex].equals("1") ) 
      {
        continue;
      }
      String tCount=tSumCount[nIndex];
      
      loggerDebug("ProposalComPrintSave_IDGF","前台查出分公司保单数："+tCount);
  
      String  tContNoSQL=" and (a.signdate <= now() and a.signdate >= substr(subdate(now() , 10), 1, 10))"; //or exists (select 'X' from lccontprinttrace r where r.contno = a.contno and r.makedate <= sysdate and r.makedate >= substr((sysdate - 30), 0, 10)))";
      String  tTempSQL = "";
      if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
    	  tTempSQL=" with familycont as ( select prtno,(select contno from lccont "
                  +" where prtno = familyprt.prtno limit 1) contno from "   
      +  " (SELECT PrtNo FROM LCCont a where grpcontno = '00000000000000000000' "
    	+ " AND AppFlag = '1'   "
    	+ " AND ( PrintCount <= 0 OR PrintCount = 10  )" 
     + " and ManageCom like '" + tManageComs[nIndex] + "%%' "
     + " and ManageCom like '" + tComCode + "%%' "
     + tContNoSQL
    	+" union  "
      +  " (SELECT PrtNo FROM LCCont A where grpcontno = '00000000000000000000' "
    	+ " AND AppFlag = '1' and FamilyType in ('1','2')   "
    	+ " AND ( PrintCount <= 0 OR PrintCount = 10  )"
    	+ " and ManageCom like '" + tManageComs[nIndex] + "%%'"
    	+ " and ManageCom like '" + tComCode + "%%' "
    	+ tContNoSQL
     +" )"
     + "  union  (SELECT (SELECT distinct c.PrtNo from lccont  c where  c.contno=a.contno )"
	  +" FROM lpedoritem  a where 1=1  and edortype='LR' and edorstate='0' "
     + " and ManageCom like '" + tManageComs[nIndex] + "%%' "
     + " and ManageCom like '" + tComCode + "%%' "
	  +" and  exists (select 'X' from lccont d where  d.contno=a.contno and (d.printcount=10 or  d.printcount<=0))"
	  +" and (a.edorappdate <= now() and a.edorappdate >= substr((subdate(now() , 10)), 1, 10))) " 
     +" ) familyprt )"	;	
      }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
    	  tTempSQL=" with familycont as ( select prtno,(select contno from lccont "
                  +" where prtno = familyprt.prtno limit 1) contno from "   
      +  " (SELECT PrtNo FROM LCCont a where grpcontno = '00000000000000000000' "
    	+ " AND AppFlag = '1'   "
    	+ " AND ( PrintCount <= 0 OR PrintCount = 10  )" 
     + " and ManageCom like '" + tManageComs[nIndex] + "%%' "
     + " and ManageCom like '" + tComCode + "%%' "
     + tContNoSQL
    	+" union  "
      +  " (SELECT PrtNo FROM LCCont A where grpcontno = '00000000000000000000' "
    	+ " AND AppFlag = '1' and FamilyType in ('1','2')   "
    	+ " AND ( PrintCount <= 0 OR PrintCount = 10  )"
    	+ " and ManageCom like '" + tManageComs[nIndex] + "%%'"
    	+ " and ManageCom like '" + tComCode + "%%' "
    	+ tContNoSQL
     +" )"
     + "  union  (SELECT (SELECT distinct c.PrtNo from lccont  c where  c.contno=a.contno )"
	  +" FROM lpedoritem  a where 1=1  and edortype='LR' and edorstate='0' "
     + " and ManageCom like '" + tManageComs[nIndex] + "%%' "
     + " and ManageCom like '" + tComCode + "%%' "
	  +" and  exists (select 'X' from lccont d where  d.contno=a.contno and (d.printcount=10 or  d.printcount<=0))"
	  +" and (a.edorappdate <= now() and a.edorappdate >= substr((subdate(now() , 10)), 1, 10))) " 
     +" ) familyprt )"	;	
      }
         
    // + " union "
    // +" (SELECT f.PrtNo "
	  // +" FROM lccontprinttrace f where 1=1 and RePrintFlag=0 "
	  // +" and ManageCom like '" + tManageComs[nIndex] + "%%' "
	  // + " and ManageCom like '" + tComCode + "%%' "
	  // +" and (f.makedate <= sysdate and f.makedate >= substr((sysdate - 100), 0, 10)) "
    // +" and  exists (select 'X' from lccont d where  d.prtno=f.prtno and d.printcount='-1'))" 					

      
      strSQL =tTempSQL+ "select a.* from LCCont a,familycont b "
               + "where a.contno=b.contno and a.prtno=b.prtno and AppFlag = '1'  "
               + " and EXISTS (SELECT 'X' from lcpol c where  c.contno=a.contno and c.mainpolno=c.polno and riskcode IN (select riskcode from LMRiskApp where NotPrintPol = '0' and riskcode not in ('311603') ) and isvipcont(prtno,payintv,payyears,prem)= '0' )  " //and riskcode<>'112401'
               + "";
      loggerDebug("ProposalComPrintSave_IDGF","strSQL:"+strSQL);
      LCContDB tLCContDB = new LCContDB();
      tLCContSet = tLCContDB.executeQuery(strSQL);
      
		  loggerDebug("ProposalComPrintSave_IDGF","后台查出分公司保单数："+tLCContSet.size());
      // Prepare data for submiting
      VData vData = new VData();
		
      vData.addElement(tLCContSet);
      vData.add(globalInput);
		
      try 
      {
        if(!tBusinessDelegate.submitData(vData, "PRINT",busiName))
       {       
	     PrintContent=tBusinessDelegate.getCErrors().getFirstError();   
	     return false;    
       }
        else
        {
        ins = (InputStream)(tBusinessDelegate.getResult().get(0));
				tSucc+=Integer.parseInt(tBusinessDelegate.getResult().get(tBusinessDelegate.getResult().size()-2).toString());
				tFail+=Integer.parseInt(tBusinessDelegate.getResult().get(tBusinessDelegate.getResult().size()-1).toString());
          
          if(Integer.parseInt(tBusinessDelegate.getResult().get(tBusinessDelegate.getResult().size()-2).toString())>0) //只有处理成功才进行XML 的传输
          {
          String tSN = "";
          tSN = PubFun1.CreateMaxNo("SNPolPrint",10);
          if (tSN.equals(""))
          {
             return false;
          }         
          //生成打印XML文件
          LDSysVarDB tLDSysVarDB = new LDSysVarDB();
          tLDSysVarDB.setSysVar("printdatanew");
          if((!tLDSysVarDB.getInfo()) || (tLDSysVarDB.getSchema().getSysVarValue() == null)  || (tLDSysVarDB.getSchema().getSysVarValue().trim() == "") )
          {
             return false;
          }
                
          String strTemplatePath = tLDSysVarDB.getSchema().getSysVarValue().trim();
  		  //strTemplatePath = "c:/XML/"; //便于测试暂时放在C盘
          // It is important to use buffered streams to enhance performance
          BufferedInputStream bufIs = new BufferedInputStream(ins);
	
          String strTime = PubFun.getCurrentDate() + "_" + PubFun.getCurrentTime();
          strTime = strTime.replace(':', '_').replace('-', '_') ;
          strTime = strTime +"_"+tSN.trim();
          loggerDebug("ProposalComPrintSave_IDGF","***************************************"+strTime);
//        add by ssx 2008-04-24 建立分级目录
          String[] step = strTime.split("_");
          String year =  step[0];
          String month = step[1];
          String day = step[2];
          strTemplatePath += year+"/"+month+"/"+day+"/";
          File file = new File(strTemplatePath);
          if(!file.exists()){
        	  loggerDebug("ProposalComPrintSave_IDGF","开始新建文件夹!");
        	  file.mkdirs();
          }else{
        	  loggerDebug("ProposalComPrintSave_IDGF","目录已经存在: ");
          } //add end
  		  loggerDebug("ProposalComPrintSave_IDGF","保单XML文件保存在应用服务器的路径是："
				+ strTemplatePath);
          FileOutputStream fos = new FileOutputStream(strTemplatePath + "LCPolData" + strTime + ".xml");
          BufferedOutputStream bufOs = new BufferedOutputStream(fos);
          int n = 0;
		
          while( ( n = bufIs.read() ) != -1 ) 
          {
            bufOs.write(n);
          }
          bufOs.flush();
          bufOs.close();
          
          //FTP的打印服务器
          LisFtpClient tLisFtpClient = new LisFtpClient();
          if (!tLisFtpClient.UpLoadFile(strTemplatePath + "LCPolData" + strTime + ".xml", "LCPolData" + strTime + ".xml"))
          {
                 return false;    
          }          
          }

        }
      } 
      catch (Exception ex) 
      {
        ex.printStackTrace();
        Content+=ex.getMessage();
        return false;
      }
    }
    return true;
  } //End handleFunction
%>

<%
	
  //add by yt 20040426，为了解决同时多个窗口打印会导致产生同一个文件名的问题。

    try 
    {
      loggerDebug("ProposalComPrintSave_IDGF","Begin Print");			
      if(handleFunction(session, request)) 
      {
        FlagStr = "Succ";
      } 
      else 
      {
        FlagStr = "Fail";
      }
    } 
    catch (Exception ex)
    {
      FlagStr = "Fail";
      ex.printStackTrace();
    }
   PrintContent="操作成功完成,本次批打保单共成功打印"+tSucc+"单,失败"+tFail+"单!"; 
   tSucc=0;
   tFail=0;
  String strTime1 = PubFun.getCurrentDate() + "_" + PubFun.getCurrentTime();
  loggerDebug("ProposalComPrintSave_IDGF","保单打印："+PrintContent + strTime1);
%>

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=PrintContent%>");
</script>
</html>

