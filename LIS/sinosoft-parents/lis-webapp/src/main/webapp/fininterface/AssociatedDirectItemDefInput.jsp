 <html> 
 	<%
//创建日期：2008-09-16
//创建人  ：Fanxin
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
	loggerDebug("AssociatedDirectItemDefInput","1"+tGI.Operator);
	loggerDebug("AssociatedDirectItemDefInput","2"+tGI.ManageCom);
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
  <SCRIPT src="AssociatedDirectItemDefInput.js"></SCRIPT>  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <%@include file="AssociatedDirectItemDefInputInit.jsp"%>
</head>

<body  onload="initForm();initElementtype();" >

<form name=fm id=fm   target=fraSubmit method=post>
	 <table>
    	<tr> 
        	 <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,dftVerop);">
                </td>   		 
    		 <td class= titleImg>
        		 分录固定数据定义
       	 </td>   		 
    	</tr>
    </table>

    <Div id= "dftVerop" style= "display: ''" class="maxbox1">
	<!--<td class=button width="10%" align=right>
				
	</td>  -->  

  <table class= common border=0 width=100%>			  	
			<tr class= common>       
         <TD class= title5>
					  版本编号
				 </TD>
				 <TD class=input5>
					 <Input class= wid name=VersionNo id=VersionNo readonly=true>   					 	 
				 </TD>
				 
         <TD class= title5>
					  版本状态
				 </TD>
				 <TD class=input5>
					 <Input class= wid name=VersionState2 id=VersionState2 readonly=true>
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
		   	   专项表字段标识
		    </TD>
		    <TD class= input5>
		  		<Input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" class=codeno name=ColumnID id=ColumnID verify="专项表字段标识|NOTNULL" ondblClick="showCodeList('columnid',[this,columnidName],[0,1],null,'FIDataTransResult','TableID');" onMouseDown="showCodeList('columnid',[this,columnidName],[0,1],null,'FIDataTransResult','TableID');" onkeyup="showCodeListKey('columnid',[this,columnidName],[0,1],null,'FIDataTransResult','TableID');"><input class=codename name=columnidName id=columnidName readonly=true elementtype=nacessary>					
		    </TD>	    		     	
				<TD class= title5>
		   	   上游数据来源字段
		    </TD>
		    <TD class= input5>
		  		<Input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" class=codeno name=SourceColumnID id=SourceColumnID verify="上游数据来源字段|NOTNULL" ondblClick="showCodeList('sourcecolumnid',[this,sourcecolumnName],[0,1],null,'FIAboriginalData','TableID');" onMouseDown="showCodeList('sourcecolumnid',[this,sourcecolumnName],[0,1],null,'FIAboriginalData','TableID');" onkeyup="showCodeListKey('sourcecolumnid',[this,sourcecolumnName],[0,1],null,'FIAboriginalData','TableID');"><input class=codename name=sourcecolumnName id=sourcecolumnName readonly=true elementtype=nacessary>
					<!--Input class=codeno name=AssociatedID ondblclick="return showCodeList('AssociatedID', [this,AssociatedName],[0,1],null,fm.VersionNo.value,'VersionNo');" onkeyup="return showCodeListKey('AssociatedID', [this,AssociatedName],[0,1],null,fm.VersionNo.value,'VersionNo');" verify="专项编号|NOTNULL"><input class=codename name=AssociatedName readonly=true elementtype=nacessary-->		  		
		    </TD>		    	    		          
			</tr>

			<tr class= common>		
        <TD  class= title5>
          描述
        </TD>
        <TD  class= input5>
        	<Input class=wid name=ReMark id=ReMark verify="描述|len<=500" >
        </TD>         	
			</tr>			
	</table> 
			   
  <INPUT class=cssButton name="querybutton" id="querybutton" VALUE="分录固定数据定义查询"  TYPE=button onclick="return queryClick2();">
    <INPUT VALUE="添  加" TYPE=button class= cssButton name="addbutton" onclick="return addClick();">   
    <INPUT VALUE="修  改" TYPE=button class= cssButton name="updatebutton" onclick="return updateClick();">
    <INPUT VALUE="删  除" TYPE=button class= cssButton name="deletebutton" onclick="return deleteClick();">             
    <INPUT VALUE="重  置" TYPE=button class= cssButton name= resetbutton onclick="return resetAgain()"><br>
    <!-- <a href="javascript:void(0);" name="querybutton" id="querybutton" class="button" onClick="return queryClick2();">分录固定数据定义查询</a>
    <a href="javascript:void(0);" class="button" name="addbutton" onClick="return addClick();">添    加</a>
    <a href="javascript:void(0);" class="button" name="updatebutton" onClick="return updateClick();">修    改</a>
    <a href="javascript:void(0);" class="button" name="deletebutton" onClick="return deleteClick();">删    除</a>
    <a href="javascript:void(0);" class="button" name="resetbutton" onClick="return resetAgain();">重    置</a> --> 
   
    </table>
    </div>
    <INPUT type=hidden name=hideOperate id=hideOperate value=''>
    <INPUT type=hidden name=SourceTableID id=SourceTableID value=''>    
    <INPUT type=hidden name=ColumnID1 id=ColumnID1 value=''>    
		<INPUT type=hidden name=VersionState id=VersionState value=''> <!-- VersionState用来存版本状态：01，02，03；而VersionState2存版本状态：正常、维护、删除-->    
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</form>


</body>
</html>
