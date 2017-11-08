<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<title>暂交费信息修改</title>
  
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
 
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT> 

  <script src="./TempFeeUpdate.js"></script>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="TempFeeUpdateInit.jsp"%>

</head>
<body  onload="initForm();">
<!--登录画面表格-->
<form name=fm id="fm" action="./TempFeeUpdateResult.jsp" target=fraSubmit method=post>
    <table>
    	<tr>
    		<td class="common">
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);">
    		</td>
    		 <td class= titleImg>
        		 请输入查询条件：
       		 </td> 
    	</tr>
    </table>
    <div class="maxbox" id="maxbox">
    <table  class= common align=center>
      	
      	<TR  class= common>
          <TD  class= title>
          暂收据号
          </TD>
          <TD  class= input>
            <Input class="common wid" name=TempFeeNo id="TempFeeNo" >
          </TD>
          <td class= title></td>
          <td class= input></td>
          <td class= title></td>
          <td class= input></td>
        </TR>  
        <TR  class= common>
          <TD  class= title>
          投保单印刷号
          </TD>
          <TD  class= input>
            <Input class="common wid" name=OtherNo id="OtherNo" >
          </TD>
          <td class= title></td>
          <td class= input></td>
          <td class= title></td>
          <td class= input></td>
        </TR>  
    </table>
    <br>
    <br>
  <Div  id= "Frame1" style= "display: none">    
      <table  class= common align=center>    	
      <TR  class= common>
          <TD  class= title>
            暂交费类型
          </TD>          
          <TD  class= input>
            <Input class="code" name=TempFeeType id="TempFeeType" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;"
 verify="暂交费类型|code:TempFeeType"  onMouseDown="return showCodeList('TempFeeType',[this]);" onDblClick="return showCodeList('TempFeeType',[this]);" onKeyUp="return showCodeListKey('TempFeeType',[this]);" readonly>
          </TD>  
          <td class= title></td>
          <td class= input></td>
          <td class= title></td>
          <td class= input></td>      
      </TR>	 
      
      <TR  class= common> 
          <TD  class= title>
            管理机构
          </TD>          
          <TD  class= input>
	     <Input class="readonly wid" name=ManageCom id="ManageCom" readonly=true > 
	  </TD>
          <TD  class= title>
            交费日期
          </TD>
          <TD  class= input>
            <Input class="common wid"  verify="交费日期|DATE&NOTNULL" name=PayDate id="PayDate">
          </TD>                                         
       </TR>         
       <TR  class= common>
          <TD  class= title>
            代理人编码
          </TD>
          <TD  class= input>
             <Input class="common wid" name=AgentCode id="AgentCode"  >          
          </TD>           
          <TD  class= title>
            代理人组别
          </TD>
          <TD  class= input>
            <Input class="common wid"  tabindex=-1 name=AgentGroup id="AgentGroup"  >
          </TD>
          </TR>
          <TR>
          <input type=hidden name="Opt" id="Opt">   
          </TR>	   
   </Table> 

   </Div> 
      	<Input class=cssButton type=Button  value="查  询" onClick="submitForm()" name="Query" id="Query">

 </div>
  <Div  id= "Frame2" style= "display: none">     
  <!--暂交费表部分 -->  
    <Table  class= common>
      	<tr>
    	 <td style="text-align: left" colSpan=1>
	 <span id="spanTempGrid" >
	 </span> 
	</td>
       </tr>
    </Table>
    <Table>
    <tr>  </tr>
    </Table>




  <!--暂交费分类表部分 -->
    <Table  class= common>
      	<tr>
    	 <td style="text-align: left" colSpan=1>
	 <span id="spanTempClassToGrid" >
	 </span> 
	</td>
       </tr>
    </Table>
    <br>
    <table>
    <TR class=input>     
     <TD class=common>
     
      <input type =button class=cssButton value="提交修改" onClick="submitForm1();" name="Update" id="Update">        
      <input type =button class=cssButton value="返  回" onClick="clearFormData();">   
     </TD>
    </TR>
    </table>
</Div>
<br><br><br><br><br>
<input type=hidden name=bComCode id="bComCode">
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</Form>
</body>
</html>










