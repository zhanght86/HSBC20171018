<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：UWManuRReport.jsp
//程序功能：新契约人工核保提起再保呈报
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
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="UWManuUpReportReply.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> 新契约生存调查报告 </title>
  <%@include file="UWManuUpReportReplyInit.jsp"%>
</head>
<body  onload="initForm('<%=tContNo%>','<%=tFlag%>');" >
  <form method=post name=fm target="fraSubmit" action= "./UWManuUpReportChk.jsp">
    <!-- 以往核保记录部分（列表） -->
     <table class= common border=0 width=100%>
    	<tr>
		<td class= titleImg align= center>契调基本信息</td>
	</tr>
    </table>
    	<table  class= common align=center>
    	    
    	 <td class= title>   投保单号 </td>
       <TD  class= input>  <Input class= "readonly" name=ContNo >  </TD>	  
    	            
      <td class="title">销售方式</td>
　　　<td class="input">
      <input class="codeno" name="SellType" verify="销售方式|notnull" verifyorder="1"  ondblclick="showCodeList('sellType',[this,sellTypeName],[0,1])" onkeyup="return showCodeListKey('sellType',[this,sellTypeName],[0,1])"><input name="sellTypeName" class="codename" readonly="readonly">
      </td>   
       <TD  class = title> 业务员代码 </TD>
       <TD  ><Input class="readonly"  name=AgentCode ></TD>     
    	</tr>
    	<tr>                     
       <TD  class = title>	地域 </TD>
       <TD  ><Input class="readonly"  name=ManageCom ></TD>
       <td class= title>   银行网点  </td>
       <TD  class= input>   <Input class= "readonly" name=BankCode >  </TD>
       <td class= title>    再保结论  </td>               
                
       <td class=input>
       <Input class=codeno name=ReinsuredResult ondblclick="return showCodeList('uqreportstate',[this,uwStatename],[0,1]);" onkeyup="return showCodeListKey('uqreportstate',[this,uwStatename],[0,1]);"><Input class=codename name=uwStatename readonly elementtype=nacessary>                    
       </td>
     </TR>  
     <tr  class= common>
          <td class=title>发送人</td><td class=common><Input  class= Common name= 'Operator' readonly></td>
          <td class=title>发送时间</td><td class=common><Input  class= Common name= 'MakeDate' readonly></td>
       <!--/tr>
       <tr  class= common-->
          <td class=title>回复时间</td><td class=common><Input  class= Common name= 'ReplyDate' readonly></td>
       </tr>   
    	</tr>
    </table>
    
  
    </div>
     <!--div id ="divOperation" style = "display : ''">
    <table class=common>
       <tr  class= common>
          <td class=title>发送人</td><td class=common><Input  class= Common name= 'Operator' readonly></td>
          <td class=title>发送时间</td><td class=common><Input  class= Common name= 'MakeDate' readonly></td>
       <!--/tr>
       <tr  class= common-->
          <!--td class=title>回复时间</td><td class=common><Input  class= Common name= 'ReplyDate' readonly></td>
       </tr>
    </table>
    </div-->
      <table class=common>
         <TR  class= common> 
           <TD  class= common> 呈报原因描述 </TD>
         </TR>
         <TR  class= common>
           <TD  class= common>
             <textarea name="ReportRemark" cols="120" rows="3" class="common" >
             </textarea>
           </TD>
         </TR>
      </table>
       <table class=common>
         <TR  class= common> 
           <TD  class= common> 原因描述 </TD>
         </TR>
         <TR  class= common>
           <TD  class= common>
             <textarea name="ReinsuDesc" cols="120" rows="3" class="common" >
             </textarea>
           </TD>
         </TR>
      </table>         
    
       <table class=common>
         <TR  class= common> 
           <TD  class= common> 再保备注 </TD>
         </TR>
         <TR  class= common>
           <TD  class= common>
             <textarea name="ReinsuRemark" cols="120" rows="3" class="common" >
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
     	
		
    <!--读取信息-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
