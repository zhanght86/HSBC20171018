<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  loggerDebug("NBErrorModifyInput","werererererrrerwer");
%>
<head >
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <script src="../common/javascript/Common.js"></script>
  <script src="../common/cvar/CCodeOperate.js"></script>
  <script src="../common/javascript/MulLine.js"></script>
  <script src="../common/laydate/laydate.js"></script>
  <script src="../common/javascript/EasyQuery.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <script src="../common/javascript/VerifyInput.js"></script>
  <script src="../common/javascript/MultiCom.js"></script>
    
  <SCRIPT src="NBErrorModifyInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="NBErrorModifyInputInit.jsp"%>

</head>
<body  onload="initForm();" >
  <form action="NBErrorModifyInputSave.jsp" method=post name=fm id="fm" target="fraSubmit">	
<Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divQuery);">
    		</TD>
    		<TD class= titleImg>
    			 请输入查询条件
    		</TD>
    	</TR>
  </Table>
   <Div  id= "divQuery" style= "display: ''" >
    <div class="maxbox1" >
    <table  class= common >
      	<TR  class= common>
          <TD  class= title5>
          印刷号
          </TD>
          <TD  class= input5>
            <input class="common wid"  name=QureyPrtNo id=QureyPrtNo>
          </TD>
          <TD  class= title5>
          合同号
          </TD>
          <TD  class= input5>
              <input class="common wid"  name=QureyContNo id=QureyContNo >   
          </TD>
       </TR> 
 </Table>
 </Div>       
</Div>   

  <!--<Table>
  	<TR>        
       	<TD> <INPUT VALUE="查  询" class= cssButton TYPE=button onClick="easyQueryClick();"> </TD>           
       </TR>
    </Table>-->
    <br>
    <a href="javascript:void(0);" class="button"onClick="easyQueryClick();">查   询</a>
    </div>
    
    <Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid);">
    		</TD>
    		<TD class= titleImg>
    			 保单险种信息
    		</TD>
    	</TR>
  </Table> 
 <Div  id= "divPolGrid" style= "display: ''" >
   <Table  class= common>
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanPolGrid" ></span> 
  	</TD>
      </TR>
    </Table>						
 </Div>
    
    
  	<Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divOldPlanGrid);">
    		</TD>
    		<TD class= titleImg>
    			 原投资比例信息
    		</TD>
    	</TR>
  </Table> 
 <Div  id= "divOldPlanGridGrid" style= "display: ''" >
   <Table  class= common>
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanOldPlanGrid" ></span> 
  	</TD>
      </TR>
    </Table>						
 </Div>
 
 <Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divNewPlanGrid);">
    		</TD>
    		<TD class= titleImg>
    			 新投资比例信息
    		</TD>
    	</TR>
  </Table> 
 <Div  id= "divNewPlanGrid" style= "display: ''" >
   <Table  class= common>
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanNewPlanGrid" ></span> 
  	</TD>
      </TR>
    </Table>						
 </Div>		
 
 	
  <!-- <table>
      <tr>
        <td><input type="button" class=cssButton value="保  存" onClick="submitFrom()"></td>
      </tr>
 </table>-->
 <br>
 <a href="javascript:void(0);" class="button" onClick="submitFrom()">保  存</a>
        <input type=hidden name=Transact >
        <input type=hidden name=State >
        <input type=hidden  name=PolNo > 
        <input type=hidden  name=RiskCode >     
        </div>
        <br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>
