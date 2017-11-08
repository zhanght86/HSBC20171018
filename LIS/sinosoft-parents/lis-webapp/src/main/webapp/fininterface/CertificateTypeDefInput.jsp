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
	loggerDebug("CertificateTypeDefInput","1"+tGI.Operator);
	loggerDebug("CertificateTypeDefInput","2"+tGI.ManageCom);
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
  <SCRIPT src="CertificateTypeDefInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <%@include file="CertificateTypeDefInputInit.jsp"%>
</head>

<body  onload="initForm();initElementtype(); " >

<form name=fm id=fm   target=fraSubmit method=post>
	 <table>
    	<tr> 
        	 <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,dftzxcop);">
                </td>   		 
    		 <td class= titleImg>
        		 凭证类型定义
       	 </td>   		 
    	</tr>
    </table>

    <Div id= "dftzxcop" style= "display: ''" class="maxbox1">
	<!--<td class=button width="10%" align=right>
				
	</td> -->   

  <table class= common border=0 width=100%>			  	
			<tr class= common>       
         <TD class= title5>
					  版本编号
				 </TD>
				 <TD class=input5>
					 <Input class= wid name=VersionNo id=VersionNo readonly=true value = '00000000000000000001'>   					 	 
				 </TD>
				 
         <TD class= title5>
					  版本状态
				 </TD>
				 <TD class=input5>
					 <Input class= wid name=VersionState2 id=VersionState2 readonly=true value = '01'>
				 </TD>				 								
			</tr>  
  </table>       
</div>
    <!--<INPUT class=cssButton name="querybutton" id="querybutton" VALUE="版本信息查询"  TYPE=button onclick="return queryClick1();"> -->
     <a href="javascript:void(0);" name="querybutton" id="querybutton" class="button" onClick="return queryClick1();">版本信息查询</a><br><br> 
  <div class="maxbox1">
	<!--<td class=button width="10%" align=right>
				
	</td>-->
	
<table class= common border=0 width=100%>
  <table class= common>			
  	
			<tr class= common>
				<TD class= title5>
		   	   凭证类型编号
		    </TD>
		    <TD class= input5>
		  		<Input class=wid name=CertificateID id=CertificateID elementtype=nacessary verify="凭证类型编号|len<=7" >		  																					 
		    </TD>
				<TD class= title5>
					  凭证类型名称
				</TD>
				<TD class=input5>
					<Input class=wid name=CertificateName id=CertificateName verify="凭证类型名称|len<=50" >
				</TD>
			</tr>
			   
			<tr class= common>				
				<TD class= title5>
		   	   明细索引代码
		    </TD>
		    <TD class= input5>
		  		<Input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" class=codeno name=DetailIndexID id=DetailIndexID verify="明细索引代码|NOTNULL" 
		  		ondblclick="showCodeList('DetailIndexID',[this,DetailIndexName],[0,1],null,null,null);" 
                onMouseDown="showCodeList('DetailIndexID',[this,DetailIndexName],[0,1],null,null,null);"
		  		onkeyup="return showCodeListKey('DetailIndexID',[this,DetailIndexName],[0,1],null,null,null);"><input class=codename name=DetailIndexName id=DetailIndexName readonly=true elementtype=nacessary> 
		    </TD>				

				<TD class= title5>
					  数据采集类型
				 </TD>
				 <TD class=input5>
				 	 <Input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" class=codeno name= AcquisitionType id= AcquisitionType verify="数据采集类型|NOTNULL"  
				 	 ondblClick="showCodeList('AcquisitionType',[this,AcquisitionTypeName],[0,1],null,null,null,1);" 
                     onMouseDown="showCodeList('AcquisitionType',[this,AcquisitionTypeName],[0,1],null,null,null,1);"
				 	 onkeyup="showCodeListKey('AcquisitionType',[this,AcquisitionTypeName],[0,1],null,null,null,1);"><input class=codename name=AcquisitionTypeName id=AcquisitionTypeName readonly=true elementtype=nacessary>
				</TD>	    						       
			</tr>
			
			<tr class= common>
				<TD class= title5>
		   	   说明
		    </TD>
		    <TD class= input5>
		  		<Input class=wid name=Remark id=Remark verify="说明|len<=500" >
		    </TD>  		    
			</tr>			
		  
    </table>         
    <INPUT class=cssButton name="querybutton" id="querybutton" VALUE="凭证类型定义查询"  TYPE=button onclick="return queryClick2();">					 
   	
    <INPUT VALUE="添  加" TYPE=button class= cssButton name="addbutton" id="addbutton" onclick="return addClick();">   
    <INPUT VALUE="修  改" TYPE=button class= cssButton name="updatebutton" id="updatebutton" onclick="return updateClick();">
    <INPUT VALUE="删  除" TYPE=button class= cssButton name="deletebutton" id="deletebutton" onclick="return deleteClick();">   
    <INPUT VALUE="重  置" TYPE=button class= cssButton name= resetbutton id= resetbutton onclick="return resetAgain()">
    </p>
    <br>
   <!-- <a href="javascript:void(0);" name="querybutton" id="querybutton" class="button" onClick="return queryClick2();">凭证类型定义查询</a>
    <a href="javascript:void(0);" class="button" name="addbutton" onClick="return addClick();">添    加</a>
    <a href="javascript:void(0);" class="button" name="updatebutton" onClick="return updateClick();">修    改</a>
    <a href="javascript:void(0);" class="button" name="deletebutton" onClick="return deleteClick();">删    除</a>
    <a href="javascript:void(0);" class="button" name="resetbutton" onClick="return resetAgain();">重    置</a>-->
    </table>
    </div>
    
    <hr class="line">
    <!--<INPUT VALUE="凭证费用类型定义" TYPE=button class= cssButton name="intobutton1" id="intobutton1" onclick="return intoCostTypeDef();"> --><br>
    <a href="javascript:void(0);" class="button" name="intobutton1" id="intobutton1" onClick="return intoCostTypeDef();">凭证费用类型定义</a>
    
    
    <INPUT type=hidden name=hideOperate id=hideOperate value=''>
		<INPUT type=hidden name=VersionState id=VersionState value=''> <!-- VersionState用来存版本状态：01，02，03；而VersionState2存版本状态：正常、维护、删除-->    
		
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</form>


</body>
</html>
