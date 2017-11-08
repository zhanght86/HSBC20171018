 <html> 
 	<%
//创建日期：2008-08-11
//创建人  ：ZhongYan
//更新记录：  更新人    更新日期     更新原因/内容
%>
  <%@page import="java.util.*"%> 
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	String mStartDate = "";
	String mSubDate = "";
	String mEndDate = "";
        GlobalInput tGI = new GlobalInput();
        //PubFun PubFun=new PubFun();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("FinItemDefInput","1"+tGI.Operator);
	loggerDebug("FinItemDefInput","2"+tGI.ManageCom);
%>

<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>  
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="FinItemDefInput.js"></SCRIPT>  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <%@include file="FinItemDefInputInit.jsp"%>
</head>

<body  onload="initForm();initElementtype();" >

<form name=fm   target=fraSubmit method=post>
	 <table>
    	<tr> 
        	 <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divVerer);">
                </td>   		 
    		 <td class= titleImg>
        		 科目信息定义
       	 </td>   		 
    	</tr>
    </table>

     <Div id= "divVerer" style= "display: ''" class="maxbox1">
	<!--<td class=button width="10%" align=right>
				
	</td>    
-->
  <table class= common border=0 width=100%>			  	
			<tr class= common>       
         <TD class= title5>
					  版本编号
				 </TD>
				 <TD class=input5>
					 <Input class= wid name=VersionNo readonly=true>   					 	 
				 </TD>
				 
         <TD class= title5>
					  版本状态
				 </TD>
				 <TD class=input5>
					 <Input class= wid name=VersionState2 readonly=true>
				 </TD>				 								
			</tr>  
  </table>       
</div>
    <!--<INPUT class=cssButton name="querybutton" VALUE="版本信息查询"  TYPE=button onclick="return queryClick1();">  -->
    <a href="javascript:void(0);" name="querybutton" class="button" onClick="return queryClick1();">版本信息查询</a><br><br>
  <div class="maxbox1">
	<td class=button width="10%" align=right>
				
	</td>
	
<table class= common border=0 width=100%>
  <table class= common>
			
  	
			<tr class= common>
				<!--TD class= title>
					  版本编号
				 </TD>
				 <TD class=input>
				 	 <Input class=common name=VersionNo  elementtype=nacessary>
				</TD-->
				<TD class= title5>
					  科目编号
				 </TD>
				 <TD class=input5>
				 	 <Input class=wid name=FinItemID  elementtype=nacessary verify="科目编号|len<=20">
					 <!--Input class= readonly name=FinItemID readonly=true-->				 	 
				</TD>
				<TD class= title5>
		   	   科目名称
		    </TD>
		    <TD class= input5>                                            
		  		 <Input class=wid name=FinItemName  elementtype=nacessary verify="科目名称|len<=40">
		    </TD>				
			</tr>
			   
			<tr class= common>
				<TD class= title5>
		   	   科目类型
		    </TD>
		    <TD class= input5>
					 <!--Input class=codeno name=accountCodeType CodeData="0|^1|资产|M^2|负债|M^6|损益" verify="科目类型|notnull" ondblclick="showCodeListEx('accountCodeType',[this,accountCodeTypeName],[0,1]);" onkeyup="showCodeListKeyEx('accountCodeType',[this,accountCodeTypeName],[0,1]);"><input class=codename name=accountCodeTypeName readonly=true elementtype=nacessary -->	
		  		 <Input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" class=codeno name= FinItemType readonly=true verify="科目类型|NOTNULL" CodeData="0|^1|资产^2|负债^6|损益" ondblClick="showCodeListEx('FinItemType',[this,FinItemTypeMame],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('FinItemType',[this,FinItemTypeMame],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('FinItemType',[this,FinItemTypeMame],[0,1],null,null,null,[1]);"><input class=codename name=FinItemTypeMame readonly=true elementtype=nacessary>
		    </TD>
				<TD class= title5>
					  科目代码（一级）
				 </TD>
				 <TD class=input5>
				 	 <Input class=wid name=ItemMainCode  elementtype=nacessary verify="科目代码（一级）|len<=20">
				</TD>		    		    
			</tr>
			
			<tr class= common>
				<!--TD class= title>
		   	   科目处理方式
		    </TD>
		    <TD class= input>		    	
		  		<Input class=codeno name= DealMode verify="科目处理方式|NOTNULL" CodeData="0|^1|正常处理^2|特殊处理" ondblClick="showCodeListEx('DealMode',[this,DealMame],[0,1]);" onkeyup="showCodeListKeyEx('DealMode',[this,DealMame],[0,1]);"><input class=codename name=DealMame readonly=true elementtype=nacessary>
		    </TD-->
		    
				<TD class= title5>
		   	   描述
		    </TD>
		    <TD class= input5>
		  		<Input class=wid name=ReMark verify="描述|len<=500" >
		    </TD>		    		    
			</tr>
			
			<tr class= common>
				 <!--TD class= title>
					  特殊处理类
				 </TD>
				 <TD class=input>
				 	 <Input class=common name=DealSpecialClass >
				</TD-->
			</tr>
		  
    </table>         
    <!--<INPUT class=cssButton name="querybutton" VALUE="科目类型定义查询"  TYPE=button onclick="return queryClick2();">--><br>
    <a href="javascript:void(0);" name="querybutton" class="button" onClick="return queryClick2();">科目类型定义查询</a>				 
    <INPUT VALUE="添  加" TYPE=button class= cssButton name="addbutton" onclick="return addClick();">   
    <INPUT VALUE="修  改" TYPE=button class= cssButton name="updatebutton" onclick="return updateClick();">
    <INPUT VALUE="删  除" TYPE=button class= cssButton name="deletebutton" onclick="return deleteClick();">
    <INPUT VALUE="重  置" TYPE=button class= cssButton name="resetbutton" onclick="return resetAgain();"> 
    <!--  <a href="javascript:void(0);" class="button" name="addbutton" onClick="addClick();">添    加</a>
    <a href="javascript:void(0);" class="button" name="updatebutton" onClick="updateClick();">修    改</a>
    <a href="javascript:void(0);" class="button" name="deletebutton" onClick="deleteClick();">删    除</a>
    <a href="javascript:void(0);" class="button" name="resetbutton" onClick="resetAgain();">重    置</a> -->
   
    
    </div>
    <hr class="line">
<!--    <INPUT VALUE="科目关联专项定义" TYPE=button class= cssButton name="intobutton1" onclick="return intoAssociatedDef();">   
    <INPUT VALUE="明细科目判断条件定义" TYPE=button class= cssButton name="intobutton2" onclick="return intoDetailDef();">--> <br>
    <a href="javascript:void(0);" class="button" name="intobutton1" onClick="return intoAssociatedDef();">科目关联专项定义</a>
    <a href="javascript:void(0);" class="button" name="intobutton2" onClick="return intoDetailDef();">明细科目判断条件定义</a>   
  
    
    <INPUT type=hidden name=hideOperate value=''>
    <INPUT type=hidden name=VersionState value=''> <!-- VersionState用来存版本状态：01，02，03；而VersionState2存版本状态：正常、维护、删除-->
    <INPUT type=hidden name=DealMode value=''>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</form>


</body>
</html>
