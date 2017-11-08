<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.acc.*"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>

<% 
//程序名称：PEdorTypeTI.jsp
//程序功能：团体转个人账户
//创建日期：2007-05-23 16:49:22
//创建人  ：ck
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String PolNo=request.getParameter("PolNo");
	String EdorType=request.getParameter("EdorType");
	String ContNo=request.getParameter("ContNo");
	String strEdorNo = request.getParameter("EdorNo");
%>
<html> 
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./PEdorTypeTIDetail.js"></SCRIPT>
  <%@include file="PEdorTypeTIDetailInit.jsp"%>
<script>
  //document.all('EdorNo').value = top.opener.document.all('EdorNo').value;	
</script>

<%

String strNowDate = PubFun.getCurrentDate();
//统计保全次数
	
	 
	 //计算手续费
 		LPEdorItemSchema mainLPEdorItemSchema = new LPEdorItemSchema();
 	LCPolSchema mainLCPolSchema = new LCPolSchema();
 	LPEdorItemDB mainLPEdorItemDB = new LPEdorItemDB();
 	LCPolDB mainLCPolDB = new LCPolDB();
 	mainLPEdorItemDB.setEdorNo(strEdorNo);
 	mainLPEdorItemSchema = mainLPEdorItemDB.query().get(1);
 	mainLCPolDB.setContNo(mainLPEdorItemSchema.getContNo());
 	mainLCPolSchema = mainLCPolDB.query().get(1);
 	TLbqForFee tTLbqForFee =new TLbqForFee();
 	double douCalFee = -1;
 	String CalFee= "";
	String strEdorItemAppDate = "";
 	
 	try {
			strEdorItemAppDate = tTLbqForFee.GetStartDate(mainLCPolSchema.getCValiDate(),mainLPEdorItemSchema.getEdorValiDate());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	
 	
	String ChangeCount = "12";
	 String SQL = "select count(*) from lpedoritem where ContNo = '"+ContNo+"' "
			+" and edortype = 'TI' and EdorState = '0' and EdorValiDate >='"+strEdorItemAppDate+"' and EdorValiDate<='"+mainLPEdorItemSchema.getEdorValiDate()+"'";	  
	 ExeSQL ex = new ExeSQL();
	 ChangeCount = ex.getOneValue(SQL);
	 ChangeCount = String.valueOf((Integer.parseInt(ChangeCount)+1));
 	
 	
 	try {
			douCalFee = tTLbqForFee.GetCalFee(-1000,strEdorNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(douCalFee == 0)
		{
		CalFee = "转换无手续费";
		}
		else
		{
		CalFee = "转换手续费为"+String.valueOf(douCalFee)+"元";
		}
		

%>
  
  <title>个人账户转换</title> 
</head>

<body  onload="initForm();" >
  <form action="./PEdorTypeTIDetailSubmit.jsp" method=post name=fm id=fm target="fraSubmit">    
  
    <table class=common>
      <TR  class= common> 
        <TD  class= title > 批单号</TD>
        <TD  class= input > 
          <input class="readonly wid" readonly name=EdorNo id=EdorNo >
        </TD>
        <TD class = title > 批改类型 </TD>
        <TD class = input >
        <Input class=codeno  value=<%=EdorType%> readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true>
        </TD>
        <TD class = title > 保单险种号码 </TD>
        <TD class = input >
        	<input class="readonly wid" value=<%=PolNo%> readonly name=PolNo id=PolNo>
        </TD>   
      </TR>
      <TR class=common>
    	<TD class =title>申请日期</TD>
    	<TD class = input>    		
    		<Input class= "coolDatePicker" readonly name=EdorItemAppDate onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
    	</TD>
    	<TD class =title>生效日期</TD>
    	<TD class = input>
    		<Input class= "coolDatePicker" readonly name=EdorValiDate onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
    	</TD>
    	<TD class = title></TD>
    	<TD class = input></TD>
    </TR>
    </TABLE> 



 <table  class= common>    	
    		<tr  class= common>
    		 <TD  class= title>
            <font color=red>这是本保单年度的第<%=ChangeCount%>次转换，本次<%=CalFee%></font>
          </TD>		
        </TR>	
    </table>



    <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGrpInsuAcc);">
        </td>
        <td class= titleImg>
          转出账户信息
        </td>
      </tr>
    </table>

    <Div  id= "divLCGrpInsuAcc" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanLCGrpInsuAccGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<Div  id= "divPage1" align=center style= "display: none ">
        <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage1.firstPage();"> 
        <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage1.previousPage();"> 					
        <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage1.nextPage();"> 
        <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage1.lastPage();"> 			
      </Div>		
  	</div>
  	
    <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivTempMoney);">
        </td>
        <td class= titleImg>
          历史转出信息
        </td>
      </tr>
    </table>
    <Div  id= "DivTempMoney" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanTempOutInsuAccGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	</Div>		
    
    <Input type=Button value="转  出" class = cssButton onclick="DealAccOut()">
	  <Input type=Button value="撤  销" class = cssButton onclick="cancelAccOut()">  
	  <Input type=Button value="返  回" class = cssButton onclick="returnParent()">   
    
    
    
        <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsuAcc);">
        </td>
        <td class= titleImg>
          转入账户信息
        </td>
      </tr>
    </table>
    
    <Div  id= "divLCInsuAcc" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanLCInsuAccGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<Div  id= "divPage2" align=center style= "display: none ">
        <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage2.firstPage();"> 
        <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage2.previousPage();"> 					
        <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage2.nextPage();"> 
        <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage2.lastPage();"> 			
      </Div>		
  	</div>
	  <hr> 
        <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPInsuAcc);">
        </td>
        <td class= titleImg>
          历史转入信息
        </td>
      </tr>      
    </table>

	<Div  id= "divLPInsuAcc" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanLPInsuAccGrid" >
  					</span> 
  			</td>
  			</tr>
    	</table>
    	<Div  id= "divPage3" align=center style= "display: none ">
        <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage3.firstPage();"> 
        <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage3.previousPage();"> 					
        <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage3.nextPage();"> 
        <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage3.lastPage();">					
      </Div>
  	</div>
	<br>  
	  <Input type=Button value="转  入" class = cssButton onclick="DealAccIn()">
	  <Input type=Button value="撤  销" class = cssButton onclick="cancelAccIn()">  
	  <Input type=Button value="返  回" class = cssButton onclick="returnParent()">
	  
    <input type=hidden id="EdorAcceptNo" name="EdorAcceptNo">
    
    <input type=hidden id="OutUnit" name="OutUnit">
    
    <input type=hidden id="CustomerNo" name="CustomerNo">
    <input type=hidden id="ContNoBak" name="ContNoBak">
    <input type=hidden id="CustomerNoBak" name="CustomerNoBak">
    <input type=hidden id="ContType" name="ContType">
    <input type=hidden id="Transact" name="Transact">
    <input type=hidden id="EdorTypeCal" name="EdorTypeCal">
    
    <input type=hidden id="PayPlanCode" name="PayPlanCode">
    <input type=hidden id="InsuAccNo" name="InsuAccNo">
    <input type=hidden id="PolNoDO" name="PolNoDO">
    
    <input type=hidden id="ContNo" name="ContNo" value=<%=ContNo%>>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
   <br ><br > <br > <br >  
</body>
</html>

<script>
  window.focus();
</script>
