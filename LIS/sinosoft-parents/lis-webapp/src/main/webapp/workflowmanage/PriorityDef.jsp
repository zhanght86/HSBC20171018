<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	
/*******************************************************************************
 * <p>Title       : 任务优先级别管理</p>
 * <p>Description : 用来将系统中的任务分配优先级别</p>
 * <p>Copyright   : Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company     : 中科软科技股份有限公司</p>
 * <p>WebSite     : http://www.sinosoft.com.cn</p>
 * @author        : 
 * @version       : 1.00
 * @date          : 2012-5-23
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
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT  src="./PriorityDef.js"></SCRIPT>
  <%@include file="./PriorityDefInit.jsp"%>
  <title>优先级别管理</title>
</head>
<body  onload="initWorkFlowGridP();initWorkGridPa();" >
 
  <table>
    <tr>
      <td class=common>
       <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divQuery);">
      </td>
      <td class= titleImg>
          请选择级别管理类型
      </td>
    </tr>
   </table>
    <form action="#" method=post name= "fma" id= "fma" target="fraSubmit" >
   <Div  id= "divQuery" style= "display: ''">
   <div class="maxbox1" >
   <table class=common>
     <TR>
   	   <TD class="title5">业务类型</TD>
	       <TD class="input5">
               <Input class=codeno name=BusiType  id=BusiType 
                style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                 onclick="return showCodeList('busitype',[this,BusiTypeName],[0,1]);"
               onDblClick="return showCodeList('busitype',[this,BusiTypeName],[0,1]);"
                onKeyUp="return showCodeList('busitype',[this,BusiTypeName],[0,1]);" ><input class=codename name=BusiTypeName >
	      </TD>	
	      <TD class=title5>过程名称</TD>
	      <TD class=input5>
               <Input class=codeno id=ProcessID  
                style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                 onclick="checkBusiType();return showCodeList('busiproxml',[this,ProcessName],[0,1],null,fma.BusiType.value,'BusiType','1');"
               ondblclick="checkBusiType();return showCodeList('busiproxml',[this,ProcessName],[0,1],null,fma.BusiType.value,'BusiType','1');"
                onKeyUp="checkBusiType();return showCodeList('busiproxml',[this,ProcessName],[0,1],null,fma.BusiType.value,'BusiType','1');" ><input class=codename name='ProcessName' >
	     </TD>  
      </TR> 
    </Table>
    </div>
    <!-- <table> 
      <tr>
       <td>
         <INPUT VALUE="查  询" TYPE=button class="cssButton" onClick="query()">
        

       </td>     
      </tr>    
    </table>-->
    <br>
     <a href="javascript:void(0);" class="button"onClick="query()">查   询</a>
   </Div>
   </form>
  <form action="#" method=post name=fm id=fm target="fraSubmit">
    <table>
    <tr>
      <td class=common>
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMul);">
    	</td>
      <td class= titleImg>
    	        级别对应条件信息
      </td>
    </tr>
    </table>
    <Div  id= "divMul" style= "display: ''">
     <table  class= common>
        <tr>
    	  	<td colSpan=1>
	         <span id="spanWorkFlowGridP" ></span>
	       	</td>
	      </tr>
     </table>  
       <!-- <INPUT VALUE="首页"   class="cssButton90" TYPE=button onClick="turnPage.firstPage();">
        <INPUT VALUE="上一页" class="cssButton91" TYPE=button onClick="turnPage.previousPage();">
        <INPUT VALUE="下一页" class="cssButton92" TYPE=button onClick="turnPage.nextPage();">
        <INPUT VALUE="尾页"   class="cssButton93" TYPE=button onClick="turnPage.lastPage();">-->
  </div>
<br>
  
    
       <!--<INPUT VALUE="添  加" class="cssButton" TYPE=button onClick="Addto()"/>
       
        <INPUT VALUE="修  改" TYPE=button class="cssButton" onClick="updateclick()">
       
        <INPUT VALUE="删  除" TYPE=button class="cssButton" onClick="deleteClick()">-->
        <a href="javascript:void(0);" class="button"onClick="Addto()">添  加</a>
<a href="javascript:void(0);" class="button"onClick="updateclick()">修  改</a>
<a href="javascript:void(0);" class="button" onClick="deleteClick()">删  除</a>

      
  </form>
 <form action="#" method=post name=fmb target="fraSubmit">
    <table>
    <tr>
      <td class=common>
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMula);">
    	</td>
      <td class= titleImg>
    	       优先级别信息管理
      </td>
    </tr>
    </table>
    <Div  id= "divMula" style= "display: ''">
     <table  class= common>
        <tr>
    	  	<td colSpan=1>
	         <span id="spanWorkGridPa" ></span>
	       	</td>
	      </tr>
     </table>  
       <!-- <INPUT VALUE="首页"   class="cssButton90" TYPE=button onClick="turnPage1.firstPage();">
        <INPUT VALUE="上一页" class="cssButton91" TYPE=button onClick="turnPage1.previousPage();">
        <INPUT VALUE="下一页" class="cssButton92" TYPE=button onClick="turnPage1.nextPage();">
        <INPUT VALUE="尾页"   class="cssButton93" TYPE=button onClick="turnPage1.lastPage();">-->
  </div>

 
       <!-- <INPUT VALUE="提  交" class="cssButton" TYPE=button onClick="updatepriority()"/>
        <INPUT VALUE="查  询" class="cssButton" TYPE=button onClick="search()"/>-->
        <br>
        <a href="javascript:void(0);" class="button"onClick="updatepriority()">提  交</a>
        <a href="javascript:void(0);" class="button"onClick="search()">查  询</a>

  <br><br><br><br>    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
