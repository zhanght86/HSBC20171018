<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	
/*******************************************************************************
 * <p>Title       : 任务分配</p>
 * <p>Description : 用来将系统中已经申请的任务分配给其它员工</p>
 * <p>Copyright   : Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company     : 中科软科技股份有限公司</p>
 * <p>WebSite     : http://www.sinosoft.com.cn</p>
 * @author        : 
 * @version       : 1.00
 * @date          : 2007-12-25
 ******************************************************************************/
%>
<script>

</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./ProcessDef.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./ProcessDefInit.jsp"%>
  <title>过程定义</title>
</head>
<body  onload="initForm();initElementtype();" >
  <form action="#" method=post name=fm id=fm target="fraSubmit">
  <table>
    <tr>
      <td class=common>
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divQuery);">
    	</td>
      <td class= titleImg>
    	       请输入查询条件
      </td>
    </tr>
   </table>
   <Div  id= "divQuery" style= "display: ''">
   <div class="maxbox1">
   <table class=common>
   <TR>
   	   <TD class=title5>业务类型</TD>
	       <TD class=input5>
               <Input class=codeno name=BusiType  id=BusiType 
               style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" 
                onclick="return showCodeList('busitype',[this,BusiTypeName],[0,1]);"
               onDblClick="return showCodeList('busitype',[this,BusiTypeName],[0,1]);" 
               onKeyUp="return showCodeList('busitype',[this,BusiTypeName],[0,1]);" ><input class=codename name=BusiTypeName readonly=true>
	      </TD>	
	      <TD class=title5>过程名称</TD>
	      <TD class=input5>
               <Input class=codeno name=ProcessID
               style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" 
                 onclick="checkBusiType();return showCodeList('busiproxml',[this,ProcessName],[0,1],null,fm.BusiType.value,'BusiType','1');"
                ondblclick="checkBusiType();return showCodeList('busiproxml',[this,ProcessName],[0,1],null,fm.BusiType.value,'BusiType','1');"
                 onKeyUp="checkBusiType();return showCodeList('busiproxml',[this,ProcessName],[0,1],null,fm.BusiType.value,'BusiType','1');" ><input class=codename name=ProcessName readonly=true>
	     </TD>  

	  
      </TR> 
    </Table>
    </div>
      </Div>
   <!-- <table> 
      <tr>
       <td>
         <INPUT VALUE="查  询" TYPE=button class="cssButton" onClick="query()">
       </td>
       
      </tr>    
    </table>-->
    <br> 
    <a href="javascript:void(0);" class="button"onClick="query()">查   询</a>

 
    <table>
    <tr>
      <td class=common>
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMul);">
    	</td>
      <td class= titleImg>
    	        过程信息
      </td>
    </tr>
    </table>
    <Div  id= "divMul" style= "display: ''">
     <table  class= common>
        <tr>
    	  	<td text-align: left colSpan=1>
	         <span id="spanWorkFlowGrid" ></span>
	       	</td>
	      </tr>
     </table>  
       <!-- <INPUT VALUE="首页"   class="cssButton90" TYPE=button onClick="turnPage.firstPage();">
        <INPUT VALUE="上一页" class="cssButton91" TYPE=button onClick="turnPage.previousPage();">
        <INPUT VALUE="下一页" class="cssButton92" TYPE=button onClick="turnPage.nextPage();">
        <INPUT VALUE="尾页"   class="cssButton93" TYPE=button onClick="turnPage.lastPage();">-->
  </div>
<br>

         <!--<INPUT VALUE="查  看" TYPE=button class="cssButton" onClick="queryClick()">
     
         <INPUT VALUE="新  建" TYPE=button class="cssButton" onClick="newClick()">
      
         <INPUT VALUE="修  改" TYPE=button class="cssButton" onClick="updateClick()">
      
         <INPUT VALUE="复  制" TYPE=button class="cssButton" onClick="copyClick()">
       
         <INPUT VALUE="删  除" TYPE=button class="cssButton" onClick="deleteClick()">
       
         <INPUT VALUE="流程 发布" TYPE=button class="cssButton" onClick="ReleaseClick()">-->
         <a href="javascript:void(0);" class="button"onClick="queryClick()">查  看</a>
<a href="javascript:void(0);" class="button"onClick="newClick()">新  建</a>
<a href="javascript:void(0);" class="button"onClick="updateClick()">修  改</a>
<a href="javascript:void(0);" class="button"onClick="updateClick()">复  制</a>
<a href="javascript:void(0);" class="button"onClick="copyClick()">删  除</a>
<a href="javascript:void(0);" class="button"onClick="ReleaseClick()">流程 发布</a>

  <br><br><br><br>      

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
