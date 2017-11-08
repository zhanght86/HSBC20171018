<html> 
<%
//程序名称：
//程序功能：分红险系数输入界面
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
  <SCRIPT src="BonusRateInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="BonusRateInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./BonusRateSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <table>
      <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInsuBonusRate);">
      </td>
      <td class= titleImg>
        分红系数录入
      </td>
    	</tr>
    </table>
    <Div  id= "divInsuBonusRate" style= "display: ''" class="maxbox1">
      <table  class= common>
        <TR  class= common>
          <TD  class= title5>
            可分配盈余
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=DistributeValue id=DistributeValue verify="可分配盈余|notnull" >
          </TD>
          <TD  class= title5>
            分配比例
          </TD>
          <TD  class= input5>
          	<Input class="wid" class= common  name=DistributeRate id=DistributeRate  verify="分配比例|notnull">
          </TD>                   
        </TR>   
          <TR  class= common>
          <TD  class= title5>
            红利系数和 
          </TD>
          <TD  class= input5>
          	<Input class="wid" class= common  name=BonusCoefSum id=BonusCoefSum  verify="红利系数和|notnull">
          </TD>   
           <TD  class= title5>
            会计年度
          </TD>
          <TD  class= input5>
          	<Input class="wid" class= common   name=FiscalYear id=FiscalYear  verify="会计年度 |notnull">
          </TD>                   
        </TR>              
   </table>       
    </Div>   
                 
  <Div id= "divOperate" style= "display: ''">
			<!--<INPUT class=cssButton name="updatebutton" VALUE="维     护"  TYPE=button onclick="return updateClick();">-->
            <a href="javascript:void(0);" name="updatebutton" class="button" onClick="return updateClick();">维    护</a>
  </Div>  
           
      <table>
    	<tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLMBonusMainRate);">
            </td>
            <td class= titleImg>
                     系数明细
       </td>
    	</tr>
     </table>
    <Div  id= "divLMBonusMainRate" style= "display: ''">
    <table  class= common>
            <tr  class= common>
                    <td text-align: left colSpan=1>
                                    <span id="spanLOBonusMainGrid" >
                                    </span>
                            </td>
                    </tr>
            </table>
    </div>

    <input type="hidden" name="transact">      
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
