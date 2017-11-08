<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.acc.*"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>

<% 
//程序名称：PEdorTypeARDetail.jsp
//程序功能：个人账户赎回
//创建日期：2007-05-23 16:49:22
//创建人  ：ck
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String PolNo=request.getParameter("PolNo");
	String EdorType=request.getParameter("EdorType");
	String ContNo=request.getParameter("ContNo");
	String strEdorNo = request.getParameter("EdorNo");
	System.out.println("苍天啊:::"+strEdorNo+";;"+EdorType);
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
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT> 

  <SCRIPT src="./PEdorTypeARDetail.js"></SCRIPT>
  <%@include file="PEdorTypeARDetailInit.jsp"%>
  
 <html> 

  
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
 	String SQL0 = "select * from lcpol where contno = '"+mainLPEdorItemSchema.getContNo()+"' and riskcode in (select riskcode from lmriskapp where risktype3 = '3')";
 	System.out.println(SQL0);
 	
 	mainLCPolSchema =mainLCPolDB.executeQuery(SQL0).get(1);
 	TLbqForFee tTLbqForFee =new TLbqForFee();
 	double douCalFee = -1;
 	String CalFee= "";
 	String strEdorItemAppDate = "";
 	
 	try {
 			tTLbqForFee.GetStartDate(mainLCPolSchema.getCValiDate(),mainLPEdorItemSchema.getEdorValiDate());
// 			strEdorItemAppDate = tTLbqForFee.GetEdorItemAppDate(mainLCPolSchema.getCValiDate(),mainLPEdorItemSchema.getEdorValiDate());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	
 	
 			String ChangeCount = "12";
	 String SQL = "select count(*) from lpedoritem where ContNo = '"+ContNo+"' "
			+" and edortype = 'AR' and EdorState = '0' and EdorValiDate >='"+strEdorItemAppDate+"' and EdorValiDate<='"+mainLPEdorItemSchema.getEdorValiDate()+"'";	 
	 ExeSQL ex = new ExeSQL();
	 ChangeCount = ex.getOneValue(SQL);
	 ChangeCount = String.valueOf((Integer.parseInt(ChangeCount)+1));
 	
 	
 	
 	
 	
 	
		String strMessage ="";
		if(mainLCPolSchema.getRiskCode().equals("RPUL"))//测试修改
		{
		  try {
			douCalFee = tTLbqForFee.GetCalFee(-1000,strEdorNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(douCalFee == 0)
		{
			CalFee = "本次领取无手续费";
		}
		else
		{
			CalFee = "本次领取手续费为"+String.valueOf(douCalFee)+"元";
		}
			strMessage = strMessage+"这是本保单年度的第"+ChangeCount+"次领取，"+CalFee;
		}
		else//趸缴产品
		{
			String MessHead = "";
    			String MessMid = "";
    			if(Integer.parseInt(ChangeCount)>1)
        			{
        				MessHead = "本保单年度有过领取，这是本保单年度的第"+ChangeCount+"次领取，";
        			}
        			else
        			{
        				MessHead = "本保单年度没有做过领取，这是本保单年度的第1次领取，";
        				}
    			try {
        			douCalFee = tTLbqForFee.GetCalFee(1000,strEdorNo);
		} catch (Exception e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
    			
    			if(douCalFee==0)
    			{
    				MessMid = "本次领取无手续费";
    			}
    			else
    			{
    				double bili = douCalFee/10;
    				MessMid = "本次领取的手续费按照领取单位数在领取申请后下一个账户评估日的价值的"+bili+"%收取";
        			
    			}
    			strMessage = MessHead+MessMid;
		}

%>
  
  
  
  <title>个人账户赎回</title> 
</head>

<body  onload="initForm();" >
  <form action="./PEdorTypeARDetailSubmit.jsp" method=post name=fm id=fm target="fraSubmit">    
    <div class=maxbox1>
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
    </div>


<table  class= common>    	
    		<tr  class= common>
    		 <TD  class= title>
            <font color=red><%=strMessage%></font>
          </TD>		
        </TR>	
    </table>



    <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGrpInsuAcc);">
        </td>
        <td class= titleImg>
          账户信息
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
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPInsuAcc);">
        </td>
        <td class= titleImg>
          历史交易信息
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
	<Div  id= "userinputbutton" style= "display: '' ">
	  <Input type=Button value="部分领取" class = cssButton onclick="GEdorRDDetail()">
	  <Input type=Button value="交易撤销" class = cssButton onclick="cancelGEdor()">  
	</div>  
	<br>
	<br>
	<Input type=Button value="返  回" class = cssButton onclick="returnParent()">
    <input type=hidden id="EdorAcceptNo" name="EdorAcceptNo">
    <input type=hidden id="CustomerNo" name="CustomerNo">
     <input type=hidden id="ContNo" name="ContNo" value=<%=ContNo%>>
    <input type=hidden id="ContNoBak" name="ContNoBak">
    <input type=hidden id="CustomerNoBak" name="CustomerNoBak">
    <input type=hidden id="ContType" name="ContType">
    <input type=hidden id="Transact" name="Transact">
    <input type=hidden id="EdorTypeCal" name="EdorTypeCal">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br /><br /><br /><br />
</body>
</html>

<script>
  window.focus();
</script>
