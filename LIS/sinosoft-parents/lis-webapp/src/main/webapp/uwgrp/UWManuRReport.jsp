<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：UWManuRReport.jsp
//程序功能：新契约人工核保生存调查报告录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="UWManuRReport.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> 新契约生存调查报告 </title>
  <%@include file="UWManuRReportInit.jsp"%>
</head>
<body  onload="initForm('<%=tContNo%>','<%=tPrtNo%>','<%=tMissionID%>','<%=tSubMissionID%>');" >
  <form method=post name=fm target="fraSubmit" action= "./UWManuRReportChk.jsp">
    <!-- 以往核保记录部分（列表） -->
     <table class= common border=0 width=100%>
    	<tr>
		<td class= titleImg align= center>契调基本信息</td>
	</tr>
    </table>
    	<table  class= common align=center>
    	    <td class= title>   投保单号 </td>
            <TD  class= input>  <Input class= "readonly" name=ContNo >  </TD>
    	     
    	    <td class= title>   核保人  </td>
            <TD  class= input>   <Input class= "readonly" name=Operator >  </TD>
             <TD  class= title> 生调原因  </TD>
        
           <td class=input>
                    <Input class=codeno name=RReportReason ondblclick="return showCodeList('rreportreason',[this,RReportReasonname],[0,1]);" onkeyup="return showCodeListKey('rreportreason',[this,RReportReasonname],[0,1]);"><Input class=codename name=RReportReasonname >                    
                </td>
    	</tr>
    	<tr>
    	  <TD class=title> 接收对象 </TD>  
          <TD  ><Input class= "codeno" name = RReport ondblclick="return showCodeList('rreport',[this,RReportTypeName],[0,1]);" onkeyup="return showCodeListKey('rreport',[this,RReportTypeName],[0,1]);" onFocus= "easyQueryClickSingle();"><Input class = codename name=RReportTypeName readonly = true> </TD>
                     
          <TD  class = title> 客户号码 </TD>
          <TD  ><Input class=common  name=CustomerNo ></TD>
           
          <TD  class = title>	客户姓名 </TD>
          <TD  ><Input class=common  name=CustomerName ></TD>
          </TR>     
    	</tr>
    </table>
    
    
    
    <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);"></td>
    		<td class= titleImg>	 契调项目录入</td>                            
    	</tr>	
    </table>
    <Div  id= "divUWSpec" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  							<span id="spanInvestigateGrid"></span> 
  		  			</td>
  				</tr>
    	</table>
    </div>
      <table class=common>
         <TR  class= common> 
           <TD  class= common> 其他契调信息 </TD>
         </TR>
         <TR  class= common>
           <TD  class= common>
             <textarea name="Contente" cols="120" rows="3" class="common" >
             </textarea>
           </TD>
         </TR>
      </table>
      <INPUT type= "hidden" name= "ProposalNoHide" value= "">
      <INPUT type= "hidden" name= "PrtNo" value= "">
      <INPUT type= "hidden" name= "MissionID" value= "">
      <INPUT type= "hidden" name= "SubMissionID" value= "">
      <INPUT type= "hidden" name= "SubNoticeMissionID" value= "">
      <INPUT type= "hidden" name= "Flag"  value = "">
      <INPUT type= "button" name= "sure" value="确 认" class= cssButton onclick="submitForm()">			
		
    <!--读取信息-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
