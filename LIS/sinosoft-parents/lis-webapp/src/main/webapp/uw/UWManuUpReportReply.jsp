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
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="UWManuUpReportReply.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> 新契约生存调查报告 </title>
  <%@include file="UWManuUpReportReplyInit.jsp"%>
  <script src="../common/javascript/MultiCom.js"></script>
</head>
<body  onload="initForm('<%=tContNo%>','<%=tPrtNo%>');" >
  <form method=post name=fm id="fm" target="fraSubmit" action= "./UWManuUpReportDealChk.jsp">
    <!-- 以往核保记录部分（列表） -->
    
<DIV id=Divtrace STYLE="display:''">
    <table class= common border=0 width=100%>
    	 <tr>
	        <td class=common>
              <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" >
            </td>
            <td class= titleImg align= center>再保轨迹：</td>
	     </tr>
    </table>
    <table  class= common>
       <tr  class= common>
      	 <td text-align: left colSpan=1>
  					<span id="spanTraceGrid" >
  					</span>
  			 </td>
  		 </tr>
    </table>
</DIV>
    
    <table class=common border=0 width=100%>
    	<tr>
		    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);">
            </td>
            <td colspan="6" class= titleImg >契调基本信息</td>
	    </tr>
     </table>
    <div class="maxbox1" id="maxbox">
     <table class="common">
   	  <tr class="common">
    	 <td class= title>
    	   印刷号 
    	 </td>
       <td class="input">
         <Input class="readonly wid" readonly name="PrtNo" id="PrtNo">  
       </TD>	  
       <td class="title">
         销售渠道
       </td>
　　　 <td class="input">
        <input class="readonly wid" readonly name="SaleChnl" id="SaleChnl">
       </td>   
       <TD class="title">
         业务员代码
       </TD>
       <TD class="input">
         <Input class="readonly wid" readonly name="AgentCode" id="AgentCode">
       </TD>     
      </tr>
    	<tr class="common">                     
       <TD class="title">
         管理机构
       </TD>
       <TD class="input">
         <Input class="readonly wid" readonly name=ManageCom id="ManageCom" >
       </TD>
       <td class="title">
         发送人
       </td>
       <td class="input">
         <Input  class="readonly wid" readonly name='Operator' id="Operator">
       </td>
       <td class=title>发送时间</td>
       <td class=input>
         <Input  class="coolDatePicker" readonly name='MakeDate'id="MakeDate" onClick="laydate({elem:'#MakeDate'});"><span class="icon"><a onClick="laydate({elem: '#MakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       </td>
     </tr>  
     <tr  class=common>
       <td class="title">
         再保结论
       </td>               
       <td class="input">
         <Input class="readonly wid" readonly name=ReinsuredResult id="ReinsuredResult" >                   
       </td>
       <td class=title>回复人</td>
       <td class=input>
         <Input  class="readonly wid" readonly name='ReplyPerson' id="ReplyPerson">
       </td>
       <td class=title>回复时间</td>
       <td class=input>
         <Input  class="coolDatePicker" name= 'ReplyDate'  readonly id="ReplyDate" onClick="laydate({elem:'#ReplyDate'});"><span class="icon"><a onClick="laydate({elem: '#ReplyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       </td>
     </tr>   
   </table>
   <table class=common>
         <TR  class= common> 
           <TD  class= title> 呈报原因描述 </TD>
         </TR>
         <TR  class= common>
           <TD  class= title>
             <textarea name="ReportRemark" cols="120" rows="3" class= common >
             </textarea>
           </TD>
         </TR>
      </table>
       <table class=common>
         <TR  class= common> 
           <TD  class= title> 原因描述 </TD>
         </TR>
         <TR  class= common>
           <TD  class= title>
             <textarea name="ReinsuDesc" cols="120" rows="3" class= common >
             </textarea>
           </TD>
         </TR>
      </table>         
    
       <table class=common>
         <TR  class= common> 
           <TD  class= title> 再保备注 </TD>
         </TR>
         <TR  class= common>
           <TD  class= title>
             <textarea name="ReinsuRemark" cols="120" rows="3" class= common  >
             </textarea>
           </TD>
         </TR>
      </table>
      </div>
      <!-- modified by liuyuxiao 2011-05-24 隐去返回，在tab中无用 -->
<!--       <input type="button" class="cssButton" value="保  存" onClick="submitForm();" > -->
      <input type="button" class="cssButton" value="返  回" onClick="top.close();" style= "display: 'none'">
   
      <INPUT type= "hidden" name= "ProposalNoHide" value= "">
      <INPUT type= "hidden" name= "ContNo" value= "">
      <INPUT type= "hidden" name= "MissionID" value= "">
      <INPUT type= "hidden" name= "SubMissionID" value= "">
      <INPUT type= "hidden" name= "SubNoticeMissionID" value= "">
      <INPUT type= "hidden" name= "Flag"  value = "">
     	
		
    <!--读取信息-->
  </form>
  <br><br><br><br><br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
