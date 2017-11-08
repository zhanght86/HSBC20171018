<html> 
<%
//程序名称：
//程序功能：万能利率输入界面
//创建日期：2007-11-09 17:55:57
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
  <SCRIPT src="AccRateInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="AccRateInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./AccRateSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <table>
      <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInsuAccRate);">
      </td>
      <td class= titleImg>
        万能结算利率录入
      </td>
    	</tr>
    </table>
    <Div  id= "divInsuAccRate" style= "display: ''" class="maxbox">
     <input type="radio" name="AccRate" value=1 checked onclick="return displayAccClick();">  结算利率
     <input type="radio" name="AccRate" value=0 onclick="return displayAccGClick();">  保证利率   	
      <table  class= common>
        <TR  class= common>
          <TD  class= title>
            险种编码
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=RiskCode id=RiskCode >
          </TD>
          <TD  class= title>
            帐户编码
          </TD>
          <TD  class= input>
          	<Input class="wid" class= common  name=InsuAccNo id=InsuAccNo >
          </TD>
          <TD  class= title>
            结算日期
          </TD>
          <TD  class= input>
            
            <Input class="coolDatePicker" onClick="laydate({elem: '#BalaDate'});" verify="有效开始日期|DATE" dateFormat="short" name=BalaDate id="BalaDate"><span class="icon"><a onClick="laydate({elem: '#BalaDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>  
          </TR>
                      
  </table>       
      
<Div id= "divAccRate" style= "display: ''">  
	      <table  class= common>
          <TR  class= common>                                          
           <TD  class= title>          	     
          结算利率                        
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=Rate id=Rate >
          </TD>
          <TD  class= title>          	     
          应该公布日期                       
          </TD>
          <TD  class= input>
            
            <Input class="coolDatePicker" onClick="laydate({elem: '#SRateDate'});" verify="有效开始日期|DATE" dateFormat="short" name=SRateDate id="SRateDate"><span class="icon"><a onClick="laydate({elem: '#SRateDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>    
          <TD  class= title>          	     
          实际公布日期                        
          </TD>
          <TD  class= input>
            
            <Input class="coolDatePicker" onClick="laydate({elem: '#ARateDate'});" verify="有效开始日期|DATE" dateFormat="short" name=ARateDate id="ARateDate"><span class="icon"><a onClick="laydate({elem: '#ARateDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>    
          </TR>                       
     </table>
</Div>                 
<Div id= "divAccRateG" style= "display:none ">                        
         <table  class= common>
          <TR  class= common>
           <TD  class= title>
            结算利率
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=GruRate id=GruRate >
          </TD>  	
          <TD  class= title>
            开始日期
          </TD>
          <TD  class= input>
            
            <Input class="coolDatePicker" onClick="laydate({elem: '#RateStartDate'});" verify="有效开始日期|DATE" dateFormat="short" name=RateStartDate id="RateStartDate"><span class="icon"><a onClick="laydate({elem: '#RateStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD> <!--</TR>
           <TR  class= common>-->
          <TD   class= title>
            结束日期
          </TD>
          <TD  class= input>
            <Input class="coolDatePicker" onClick="laydate({elem: '#RateEndDate'});" verify="有效开始日期|DATE" dateFormat="short" name=RateEndDate id="RateEndDate"><span class="icon"><a onClick="laydate({elem: '#RateEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR>             
       </table>
</Div></Div>      

  <Div id= "divOperate" style= "display: ''">
			<!--<INPUT class=cssButton name="addbutton" VALUE="增  加"  TYPE=button onclick="return addClick();">
			<INPUT class=cssButton name="updatebutton" VALUE="修  改"  TYPE=button onclick="return updateClick();">
			<INPUT class=cssButton name="deletebutton" VALUE="删  除"  TYPE=button onclick="return deleteClick();">-->
            <a href="javascript:void(0);" name="addbutton" class="button" onClick="return addClick();">增    加</a>
            <a href="javascript:void(0);" name="updatebutton" class="button" onClick="return updateClick();">修    改</a>
            <a href="javascript:void(0);" name="deletebutton" class="button" onClick="return deleteClick();">删    除</a>
  </Div>  
           
      <table>
    	<tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLMInsuAccRate);">
            </td>
            <td class= titleImg>
                     利率明细
            </td>
    	</tr>
     </table>
    <Div  id= "divLMInsuAccRate" style= "display: ''">
    <table  class= common>
            <tr  class= common>
                    <td text-align: left colSpan=1>
                                    <span id="spanLMInsuAccRateGrid" >
                                    </span>
                            </td>
                    </tr>
            </table>
    </div>
      	<Div  id= "divAllGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanAllGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
					
  	</div>
    </Div>   
    <input type="hidden" name="transact">     
    <input type="hidden" name="AccType">   
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
