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
	loggerDebug("AssociatedItemDefInput","1"+tGI.Operator);
	loggerDebug("AssociatedItemDefInput","2"+tGI.ManageCom);
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
  <SCRIPT src="AssociatedItemDefInput.js"></SCRIPT>  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <%@include file="AssociatedItemDefInputInit.jsp"%>
</head>

<body  onload="initForm();initElementtype();" >

<form name=fm id=fm   target=fraSubmit method=post>
	 <table>
    	<tr>  
        	 <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divVerop);">
                </td>  		 
    		 <td class= titleImg>
        		 科目专项定义
       	 </td>   		 
    	</tr>
    </table>

    <Div id= "divVerop" style= "display: ''" class="maxbox1">
	<!--<td class=button width="10%" align=right>
				
	</td> -->   

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
  <div class="maxbox">
	<!--<td class=button width="10%" align=right>
				
	</td>-->
	
<table class= common border=0 width=100%>
  <table class= common>
			
  	
			<tr class= common>
				<TD class= title5>
		   	   专项编号
		    </TD>
		    <TD class= input5>
		  		<Input class=wid name=AssociatedID id=AssociatedID elementtype=nacessary verify="专项编号|len<=15" >
		    </TD>
				<TD class= title5>
					 专项名称
				</TD>
				<TD class=input5>
					<Input class=wid name=AssociatedName id=AssociatedName elementtype=nacessary verify="专项名称|len<=15" >
				</TD>		
			</tr>
			   
			<tr class= common>
				<!--TD class= title>
		   	   专项表字段标识
		    </TD>
		    <TD class= input>
		  		<Input class=codeno name=ColumnID verify="专项表字段标识|NOTNULL" ondblClick="showCodeList('columnid',[this,columnidName],[0,1]);" onkeyup="showCodeListKey('columnid',[this,columnidName],[0,1]);"><input class=codename name=columnidName readonly=true elementtype=nacessary>
		    </TD-->
				<TD class= title5>
		   	   专项表字段标识
		    </TD>
		    <TD class= input5>
		  		<Input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" class=codeno name=ColumnID id=ColumnID verify="专项表字段标识|NOTNULL" ondblClick="showCodeList('columnid',[this,columnidName],[0,1],null,'FIDataTransResult','TableID');"  onMouseDown="showCodeList('columnid',[this,columnidName],[0,1],null,'FIDataTransResult','TableID');" onkeyup="return showCodeListKey('columnid',[this],[0,1],null,'FIDataTransResult','TableID');"><input class=codename name=columnidName  elementtype=nacessary>					
		    </TD>	 
		    
		    
				<!--TD class= title>
		   	   上游数据来源字段
		    </TD>
		    <TD class= input>
		  		<Input class=codeno name=SourceColumnID verify="上游数据来源字段|NOTNULL" ondblClick="showCodeList('sourcecolumnid',[this,sourcecolumnName],[0,1]);" onkeyup="showCodeListKey('sourcecolumnid',[this,sourcecolumnName],[0,1]);"><input class=codename name=sourcecolumnName readonly=true elementtype=nacessary>
		    </TD-->   	
				<TD class= title5>
		   	   上游数据来源字段 
		    </TD>
		    <TD class= input5>
		  		<Input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" class=codeno name=SourceColumnID id=SourceColumnID verify="上游数据来源字段|NOTNULL" ondblClick="showCodeList('sourcecolumnid',[this,sourcecolumnName],[0,1],null,'FIAboriginalData','TableID');" onMouseDown="showCodeList('sourcecolumnid',[this,sourcecolumnName],[0,1],null,'FIAboriginalData','TableID');" onkeyup="showCodeListKey('sourcecolumnid',[this,sourcecolumnName],[0,1],null,'FIAboriginalData','TableID');"><input class=codename name=sourcecolumnName id=sourcecolumnName  elementtype=nacessary>
		  				    
					<!--Input class=codeno name=AssociatedID ondblclick="return showCodeList('AssociatedID', [this,AssociatedName],[0,1],null,fm.VersionNo.value,'VersionNo');" onkeyup="return showCodeListKey('AssociatedID', [this,AssociatedName],[0,1],null,fm.VersionNo.value,'VersionNo');" verify="专项编号|NOTNULL"><input class=codename name=AssociatedName readonly=true elementtype=nacessary-->		  		
		    </TD>		
		    
        <!--TD  class= title>
          上游数据来源表名
        </TD>
        <TD  class= input>
        	<Input class=codeno name=SourceTableID verify="上游数据来源表名|NOTNULL" CodeData="0|^FIAboriginalData|凭证业务数据表" ondblClick="showCodeListEx('SourceTableID',[this,SourceTableName],[0,1]);" onkeyup="showCodeListKeyEx('SourceTableID',[this,SourceTableName],[0,1]);"><input class=codename name=SourceTableName readonly=true elementtype=nacessary>
        </TD-->        
			</tr>

			<tr class= common>		
        <TD  class= title5>
          转换标志
        </TD>
        <TD  class= input5>
        	<Input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" class=codeno name=TransFlag id=TransFlag verify="转换标志|NOTNULL" readonly=true  CodeData="0|^N|不转换^S|SQL转换^C|程序转换" ondblClick="showCodeListEx('TransFlag',[this,TransFlagName],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('TransFlag',[this,TransFlagName],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('TransFlag',[this,TransFlagName],[0,1],null,null,null,[1]);"><input class=codename name=TransFlagName id=TransFlagName readonly=true elementtype=nacessary>
        </TD>
        <TD  class= title5>
          描述
        </TD>
        <TD  class= input5>
        	<Input class=wid name=ReMark verify="描述|len<=500" >
        </TD>         	
			</tr>			
	</table> 
			   
	<Div  id= "classdiv" style= "display: none" align=left>
	<table class=common>
			<tr class= common>
        <TD  class= title5 >
          转化类型处理类
        </TD>
        <TD  class= input5 >
        	<Input class=wid name=TransClass id=TransClass >
        </TD> 
         
        <TD  class= title5 >  
        </TD> 
        <TD  class= input5 >
        </TD>          	                        	       
			</tr> 						          
	</table> 	  
	</div>		 
    
  <Div  id= "sqldiv" style= "display: none" align=left>
	<table class=common>
			<tr  class= common>
				<TD  class= title>转换SQL（4000字符以内）</TD>
			</tr>
			<tr  class= common>
				<TD  class= common>
					<textarea style="margin-left:16px" name="TransSQL" verify="转换SQL|len<=4000" verifyorder="1" cols="176" rows="4" witdh=100% class="common" ></textarea>
				</TD>
			</tr>
	</table>	
  </div>	 
 <INPUT class=cssButton name="querybutton" id="querybutton" VALUE="科目专项定义查询"  TYPE=button onclick="return queryClick2();">
    <INPUT VALUE="添  加" TYPE=button class= cssButton name="addbutton" id="addbutton" onclick="return addClick();">   
    <INPUT VALUE="修  改" TYPE=button class= cssButton name="updatebutton" id="updatebutton" onclick="return updateClick();">
    <INPUT VALUE="删  除" TYPE=button class= cssButton name="deletebutton" id="deletebutton" onclick="return deleteClick();">
    <INPUT VALUE="重  置" TYPE=button class= cssButton name= resetbutton id= resetbutton onclick="return resetAgain()"><br><br>
   <!--  <a href="javascript:void(0);" name="querybutton" id="querybutton" class="button" onClick="return queryClick2();">科目专项定义查询</a>
    <a href="javascript:void(0);" class="button" name="addbutton" onClick="return addClick();">添    加</a>
    <a href="javascript:void(0);" class="button" name="updatebutton" onClick="return updateClick();">修    改</a>
    <a href="javascript:void(0);" class="button" name="deletebutton" onClick="return deleteClick();">删    除</a>
    <a href="javascript:void(0);" class="button" name="resetbutton" onClick="return resetAgain();">重    置</a> -->
    </div>
    
    <INPUT type=hidden name=hideOperate id=hideOperate value=''>
    <INPUT type=hidden name=SourceTableID id=SourceTableID value=''>    
		<INPUT type=hidden name=VersionState id=VersionState value=''> <!-- VersionState用来存版本状态：01，02，03；而VersionState2存版本状态：正常、维护、删除-->    
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</form>


</body>
</html>
