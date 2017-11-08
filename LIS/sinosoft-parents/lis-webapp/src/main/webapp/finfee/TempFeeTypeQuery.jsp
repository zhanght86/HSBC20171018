<%
//创建日期：
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
//         
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<!--用户校验类--> 
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.finfee.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>  
  <%@page import="com.sinosoft.service.*" %>
<%
  
  //保存保单号  
  String ContNo = request.getParameter("QueryNo");
  String ContNoType = request.getParameter("QueryType"); 
  
// 输出参数
   CErrors tError = null;          
   String FlagStr = "";
   String Content = "";   
   String RiskCode = "";
      
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp
  if(tGI==null)
  {
    loggerDebug("TempFeeTypeQuery","页面失效,请重新登陆");   
    FlagStr = "Fail";        
    Content = "页面失效,请重新登陆";  
  }
  else //页面有效
  {   
     VData tVData = new VData();
     if(ContNoType.equals("2")) //个人保单
     {
        loggerDebug("TempFeeTypeQuery","GGGGGGGGG################3"+ContNo);
        LCContSchema  tLCContSchema = new LCContSchema();
        tLCContSchema.setContNo(ContNo);
        //tLCContSchema.setManageCom(tGI.ComCode);
        loggerDebug("TempFeeTypeQuery","GGGGGGGGG"+tGI.ComCode);
        tVData.add(tLCContSchema);
     }
     else
     {
        LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
        tLCGrpContSchema.setGrpContNo(ContNo);
        tLCGrpContSchema.setManageCom(tGI.ComCode);
        tVData.add(tLCGrpContSchema);
     }  
     //TempFeeTypeQueryUI tTempFeeTypeQueryUI = new TempFeeTypeQueryUI();
     
     BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
     
     tBusinessDelegate.submitData(tVData,"QUERY","TempFeeTypeQueryUI");
     tError = tBusinessDelegate.getCErrors();
     if (!tError.needDealError())
     {                          
        Content = " 查询成功! ";
       	FlagStr = "Succ";
        tVData.clear();
        if(ContNoType.equals("2"))
        {
          loggerDebug("TempFeeTypeQuery","个单查询成功1");
          LCPolSet pLCPolSet = new LCPolSet();
          tVData = tBusinessDelegate.getResult();               
          pLCPolSet=(LCPolSet)tVData.getObjectByObjectName("LCPolSet",0);
          
          //RiskCode=pLCPolSet.getRiskCode();         
          loggerDebug("TempFeeTypeQuery","个单查询成功2");
          for(int i=1;i<=pLCPolSet.size();i++)
          {
        	  RiskCode=pLCPolSet.get(i).getRiskCode();
 %>
 <script language="javascript">		 
		       var RiskCode="";  
		       var RiskName = new Array();
		       var row=<%=i%>;
		       if("<%=RiskCode%>"==null)
		       {
		         RiskName[0]= new Array();
		         RiskName[0][0]= "";
		       }
		       else
		       {
		         RiskCode="<%=RiskCode%>";
		         //var strSql = "select RiskName from LMRisk where RiskCode='"+"<%=RiskCode%>"+"'"; 
		         var tResourceName="finfee.TempFeeTypeQuerySql";
				var strSql = wrapSql(tResourceName,"querysqldes1",["<%=RiskCode%>"]);         
		         var strResult=easyQueryVer3(strSql, 1, 1, 1);
		         if(!strResult)
		         {
		           alert("险种编码表中没有查询到险种编码对应的险种名称！");
		           RiskName[0]= new Array();
		           RiskName[0][0]= "";
		         }
		         else
		         {       
		          RiskName=decodeEasyQueryResult(strResult);
		         }
		       } 
		       if(row==1)
		       {
			      parent.fraInterface.initTempGridYS();
			      parent.fraInterface.TempGrid.lock();
		       }
		         parent.fraInterface.TempGrid.addOne("TempGrid");
		         parent.fraInterface.TempGrid.setRowColData(row-1,1,RiskCode);
		         parent.fraInterface.TempGrid.setRowColData(row-1,2,RiskName[0][0]);
		         parent.fraInterface.TempGrid.setRowColData(row-1,5,parent.fraInterface.document.all('InputNo5').value);             
 </script>
 <%
          }
%>
<script>
                 parent.fraInterface.document.all('TempFeeNo').value=parent.fraInterface.document.all('InputNo5').value;  
                // alert(parent.fraInterface.document.all('TempFeeNo').value);
</script>
<%
        }
        else
        {
           LCGrpPolSet pLCGrpPolSet = new LCGrpPolSet();
           tVData = tBusinessDelegate.getResult();               
           pLCGrpPolSet=(LCGrpPolSet)tVData.getObjectByObjectName("LCGrpPolSet",0);
           loggerDebug("TempFeeTypeQuery","团单查询成功");
           //RiskCode=pLCGrpPolSchema.getRiskCode();   
           for(int i=1;i<=pLCGrpPolSet.size();i++)
           {
             RiskCode=pLCGrpPolSet.get(i).getRiskCode();
             loggerDebug("TempFeeTypeQuery","RiskCode"+RiskCode);
%>
<script language="javascript">		 
	 		 var RiskCode="";  
	         var RiskName = new Array();
	         var row=<%=i%>;
             if("<%=RiskCode%>"==null)
	         {
		         RiskName[0]= new Array();
		         RiskName[0][0]= "";
	         }
	         else
	         {
		         RiskCode="<%=RiskCode%>";
		         //var strSql = "select RiskName from LMRisk where RiskCode='"+"<%=RiskCode%>"+"'";
		         var tResourceName="finfee.TempFeeTypeQuerySql";
				var strSql = wrapSql(tResourceName,"querysqldes1",["<%=RiskCode%>"]);          
		         var strResult=easyQueryVer3(strSql, 1, 1, 1);
		         if(!strResult)
		         {
		           alert("险种编码表中没有查询到险种编码对应的险种名称！");
		           RiskName[0]= new Array();
		           RiskName[0][0]= "";
		         }
		         else
		         {       
		          RiskName=decodeEasyQueryResult(strResult);
		         }
	        } 
	        if(row==1)
	         {
		         parent.fraInterface.initTempGridYS(); 
		         parent.fraInterface.TempGrid.lock();
	         }
	         parent.fraInterface.TempGrid.addOne("TempGrid");
	         parent.fraInterface.TempGrid.setRowColData(row-1,1,RiskCode);
	         parent.fraInterface.TempGrid.setRowColData(row-1,2,RiskName[0][0]);           
	         parent.fraInterface.TempGrid.setRowColData(row-1,5,parent.fraInterface.document.all('InputNo5').value);                              	           
 </script>
<%  
          }
%>
<script>
           parent.fraInterface.document.all('TempFeeNo').value=parent.fraInterface.document.all('InputNo5').value;  
//           alert(parent.fraInterface.document.all('TempFeeNo').value);
</script>
<%
        }     	
      }
      else                                                                           
      {
     	Content = " 失败，原因:" + tError.getFirstError();
     	FlagStr = "Fail";
      }     

  } //页面有效
     
%>


<script language="javascript"> 
    parent.fraInterface.afterQuery("<%=FlagStr%>","<%=Content%>");       
</script>
