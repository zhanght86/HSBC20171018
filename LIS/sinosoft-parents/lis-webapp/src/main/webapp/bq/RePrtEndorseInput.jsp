<%@page contentType="text/html;charset=GBK" %>
<html>


<%
 GlobalInput tGI1 = new GlobalInput();
     tGI1=(GlobalInput)session.getValue("GI"); //添加页面控件的初始化。
     String ManageCom = tGI1.ManageCom;
   
	String strContNo = request.getParameter("ContNo"); 
	if (strContNo == null)
	    strContNo = "";
	 System.out.println("strContNo :" + strContNo);
 %>    
<script>
	var managecom = "<%=tGI1.ManageCom%>"; //记录管理机构
	var comcode = "<%=tGI1.ComCode%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT> 
  
  <SCRIPT src="./RePrtEndorse.js"></SCRIPT>
  <SCRIPT src="PEdor.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="PrtEndorseInit.jsp"%>
  <%@include file = "ManageComLimit.jsp"%>

  <title>个人批改查询 </title>
</head>
<body  onload="initForm();" >

  <form action="./PEdorQueryOut.jsp" method=post name=fm  id=fm target="fraSubmit" >
    <!-- 个人信息部分 -->
    <table>
      <tr>
      <td class= common> 
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPEdor1);">
      </td>
      <td class= titleImg>
        请您输入查询条件：
      </td>
    	</tr>
    </table>

    <Div  id= "divLPEdor1" style= "display: ''">
    <div class="maxbox1" >
      <table  class= common>

        <TR  class= common>
          <TD  class= title5>  保全受理号  </TD>
          <TD  class= input5>  <Input class="common wid" name=EdorAcceptNo > </TD>
          <TD  class= title5>  批单号 </TD>
          <TD  class= input5> <Input class="common wid" name=EdorNo > </TD>
          </TR>
        <tr class=common>
          <TD  class= title5>  合同号 </TD>
          <TD  class= input5>  <Input class="common wid" name=ContNo value=<%=strContNo%> > </TD>
        
        <TD  class= title5>   管理机构  </TD>
        <!--<TD  class= input><Input class= code name=ManageCom ondblclick="return showCodeList('ComCode',[this]);" onkeyup="return showCodeListKey('ComCode',[this]);"></TD>-->
        <TD  class= input5><Input class="common wid"name=ManageCom 
        style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeListEx('nothis', [this],[0,1],null,null,null,1,'260');" 
        onDblClick="return showCodeListEx('nothis', [this],[0,1],null,null,null,1,'260');" 
        onKeyUp="return showCodeListKeyEx('nothis', [this],[0,1],null,null,null,1,'260');"></TD>
        
        </tr>
      </table>
    </Div></Div>
    
       <!--   <INPUT VALUE="查  询" class= cssButton TYPE=button onClick="easyQueryClick();"> -->
       <a href="javascript:void(0);" class="button"onClick="easyQueryClick();">查   询</a>
          					
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPEdor2);">
    		</td>
    		<td class= titleImg>
    			 个人批改信息
    		</td>
    	</tr>
    	 
    </table>
   
  	<Div  id= "divLPEdor2" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanPEdorMainGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首页" class= cssButton90 TYPE=button onClick="getFirstPage();"> 
      <INPUT VALUE="上一页" class= cssButton91 TYPE=button onClick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class= cssButton92 TYPE=button onClick="getNextPage();"> 
      <INPUT VALUE="尾页" class= cssButton93 TYPE=button onClick="getLastPage();"> 					
  	</div>
  	<br>
     <!--INPUT VALUE="使用文件打印批单" class= common TYPE=button onclick="submitForm();"--> 
     <!--INPUT VALUE="使用会话打印批单" class= common TYPE=button onclick="submitFormold();"-->
    <!-- <INPUT VALUE="补打批单" class= cssButton TYPE=button onClick="print();">-->
 <!--    <INPUT VALUE="打印现金价值表" class= csscommon TYPE=button onclick="prtCashValue();"> -->
     
 <!--    <INPUT VALUE="显示批单XML" class= common TYPE=button onclick="changeEdorPrint();"> -->
<a href="javascript:void(0);" class="button"onClick="print();">补打批单</a>
<br><br><br><br>
  		<input type=hidden id="EdorType" name="EdorType">
  	 <input type=hidden id="ContType" name="ContType">
  <!--	 <input type=hidden id="EdorAcceptNo" name="EdorAcceptNo">  -->
  	 
    </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
