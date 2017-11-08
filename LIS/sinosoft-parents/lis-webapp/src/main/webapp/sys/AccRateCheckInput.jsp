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
  <SCRIPT src="AccRateCheckInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="AccRateCheckInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./AccRateCheckSave.jsp" method=post name=fm id=fm target="fraSubmit">
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
            <Input class="wid" class= common name=RiskCode id=RiskCode readonly >
          </TD>
          <TD  class= title>
            帐户编码
          </TD>
          <TD  class= input>
          	<Input class="wid" class= common  name=InsuAccNo id=InsuAccNo readonly >
          </TD> 
           <TD  class= title>
            结算日期
          </TD>
          <TD  class= input> 
           <Input class="coolDatePicker" onClick="laydate({elem: '#BalaDate'});" verify="有效开始日期|DATE" dateFormat="short" name=BalaDate id="BalaDate"><span class="icon"><a onClick="laydate({elem: '#BalaDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>   
          </TR>
          <TR  class= common>
         
                                
        </TR>               
  </table>       
       
<Div id= "divAccRate" style= "display: ''">  
	      <table  class= common>
          <TR  class= common>                                          
          <TD  class= title>          	     
          结算利率                        
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=Rate id=Rate  readonly>
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
<Div id= "divAccRateG" style= "display: none">                        
         <table  class= common>
          <TR  class= common>
           <TD  class= title>
            结算利率
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=GruRate id=GruRate  readonly>
          </TD>  	
          <TD  class= title>
            开始日期
          </TD>
          <TD  class= input>
            <Input class="coolDatePicker" onClick="laydate({elem: '#RateStartDate'});" verify="有效开始日期|DATE" dateFormat="short" name=RateStartDate id="RateStartDate"><span class="icon"><a onClick="laydate({elem: '#RateStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title>
            结束日期
          </TD>
          <TD  class= input>
             <Input class="coolDatePicker" onClick="laydate({elem: '#RateEndDate'});" verify="有效开始日期|DATE" dateFormat="short" name=RateEndDate id="RateEndDate"><span class="icon"><a onClick="laydate({elem: '#RateEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
         
        </TR>             
       </table>
</Div> </Div>    

  <Div id= "divOperate" style= "display: ''">
  	<!--<INPUT class=cssButton name="updatebutton" VALUE="上线确认"  TYPE=button onclick="return updateClick();">-->
    <a href="javascript:void(0);" name="updatebutton" class="button" onClick="return updateClick();">上线确认</a>
<!--				<INPUT class=cssButton name="addbutton" VALUE=" 添  加"  TYPE=button onclick="return addClick();">
			<INPUT class=cssButton name="deletebutton" VALUE="删  除"  TYPE=button onclick="return deleteClick();">--> 
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
 <!--   <Div  id= "divWordsRemakeByInfo" style= "display: ''">
			  <table class= common>
			    <TR  class= common> 
			      <TD width="100%" height="15%"  class= title> 备注:</TD>
			    </TR>
			    <TR  class= common>
			      <TD height="85%"  class= title><textarea name="Remark" verify="备注内容|len<1000" verifyorder="1" cols="80" rows="3" class="common" ></textarea></TD>
			    </TR>
			  </table>
    </Div>
</Div>   --> 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
