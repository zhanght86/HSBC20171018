<html> 
<%
//程序名称：
//程序功能：分红险系数计算
//创建日期：2008-11-09 17:55:57
//创建人  ：彭送庭
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT> 
  <SCRIPT src="BonusRateCount.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="BonusRateCountInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./BonusRateCountSave.jsp" method=post name=fm target="fraSubmit">
    <table>
      <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInsuAccRate);">
      </td>
      <td class= titleImg>
        分红险系数计算
      </td>
    	</tr>
    </table>
    <Div  id= "divInsuAccRate" style= "display: ''" class="maxbox1">	
      <table  class= common>
        <TR  class= common>
         <TD  class= title5>
            红利分配会计年度
          </TD>  
        <TD class= input5>    
           <Input class="wid" class=common  name=FiscalYear id=FiscalYear verify="红利分配会计年度|notnull&INT&len==4"><font color=red>*</font>
          </TD> 
        <TD class=title5></TD>
           <TD class=input5></TD>              
      <!--    <TD class= input>    
           <Input class=common  name=FiscalYear verify="红利分配会计年度|notnull&INT&len==4"><font color=red>*</font>
          </TD> 
          <TD class=title>管理机构</TD>
           <TD class=input><Input class="codeno" name=ManageCom ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly>
        </TD>-->                   
        </TR>               
   </table>       
    </Div>     

  <Div id= "divOperate" style= "display: ''">
  	<!--<INPUT class=cssButton name="updatebutton" VALUE="计    算"  TYPE=button onclick="return updateClick();">-->
    <a href="javascript:void(0);" name="updatebutton" class="button" onClick="return updateClick();">计    算</a>
  </Div>  
   
   <Div id= "divGirD" style= "display: none">        
      <table>
    	<tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLOBonusPol);">
            </td>
            <td class= titleImg>
                     红利系数明细
            </td>
    	</tr>
     </table>
    <Div  id= "divLOBonusPol" style= "display: ''">
    <table  class= common>
            <tr  class= common>
                    <td text-align: left colSpan=1>
                                    <span id="spanLOBonusPolGrid" >
                                    </span>
                            </td>
                    </tr>
            </table>
    </div>

  <Div id= "divOperate" style= "display: ''">
    <input type="hidden" name="transact">     

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
