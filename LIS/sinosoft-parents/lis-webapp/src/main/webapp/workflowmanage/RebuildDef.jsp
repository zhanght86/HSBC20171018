<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	
/*******************************************************************************
 * <p>Title       : �������</p>
 * <p>Description : ������ϵͳ���Ѿ������������������Ա��</p>
 * <p>Copyright   : Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company     : �п���Ƽ��ɷ����޹�˾</p>
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
  <SCRIPT src="./RebuildDef.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./RebuildDefInit.jsp"%>
  <title>���̶���</title>
</head>
<body  onload="initForm();initElementtype();" >
  <form action="#" method=post name=fm  id=fm  target="fraSubmit">
  <table>
    <tr>
      <td class="common">
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divQuery);">
    	</td>
      <td class= titleImg>
    	       �������ѯ����
      </td>
    </tr>
   </table>
   <Div  id= "divQuery" style= "display: ''">
   <div class="maxbox1" >
   <table class=common>
   <TR>
	     <TD class=title5>ҵ������</TD>
	       <TD class=input5>
               <Input class=codeno name=BusiType  id=BusiType
                style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" 
                 onclick="return showCodeList('busitype',[this,BusiTypeName],[0,1]);" 
               onDblClick="return showCodeList('busitype',[this,BusiTypeName],[0,1]);"
                onKeyUp="return showCodeList('busitype',[this,BusiTypeName],[0,1]);" ><input class=codename name=BusiTypeName readonly=true>
	      </TD>	   
	      <TD class=title5>��������</TD>
	      <TD class=input5>
               <Input class=codeno name=ProcessID  id=ProcessID 
               style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" 
                onclick="checkBusiType();return showCodeList('busipro',[this,ProcessName],[0,1],null,fm.BusiType.value,'BusiType','1');"
               ondblclick="checkBusiType();return showCodeList('busipro',[this,ProcessName],[0,1],null,fm.BusiType.value,'BusiType','1');"
                onKeyUp="checkBusiType();return showCodeList('busipro',[this,ProcessName],[0,1],null,fm.BusiType.value,'BusiType','1');" ><input class=codename name=ProcessName readonly=true>
	     </TD>  
	  
      </TR> 
    </Table>
    </div>
    <!--<table> 
      <tr>
       <td>
         <INPUT VALUE="��  ѯ" TYPE=button class="cssButton" onClick="query()">
       </td>
       
      </tr>    
    </table>-->
    <br>
    <a href="javascript:void(0);" class="button"onClick="query()">��   ѯ</a>

   </Div>
    <table>
    <tr>
      <td class="common">
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMul);">
    	</td>
      <td class= titleImg>
    	        ������Ϣ
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
        <!--<INPUT VALUE="��ҳ"   class="cssButton90" TYPE=button onClick="turnPage.firstPage();">
        <INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onClick="turnPage.previousPage();">
        <INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onClick="turnPage.nextPage();">
        <INPUT VALUE="βҳ"   class="cssButton93" TYPE=button onClick="turnPage.lastPage();">	-->
  </div>
 <br>
<!-- <INPUT VALUE="��  ��" TYPE=button class="cssButton" onClick="rebuildClick()">-->
<a href="javascript:void(0);" class="button"onClick="rebuildClick()">��  ��</a>

 <br><br><br><br>     

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
