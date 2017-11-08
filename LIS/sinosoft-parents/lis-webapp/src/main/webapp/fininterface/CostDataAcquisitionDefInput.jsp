<html>
<%
//程序名称 :CostDataAcquisitionDefInput.jsp
//程序功能 :凭证费用数据采集定义
//创建人 :范昕
//创建日期 :2008-08-18
//
%>


	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
	<!--用户校验类-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.pubfun.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	<%
	
  GlobalInput tGI1 = new GlobalInput();
  tGI1=(GlobalInput)session.getValue("GI");//添加页面控件的初始化。
  String tAcquisitionType = "";
 	%>
<script>
  var comcode = "<%=tGI1.ComCode%>"; 
  var ttAcquisitionType = "<%=tAcquisitionType%>"
</script>
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
<SCRIPT src = "CostDataAcquisitionDefInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<jsp:include page="CostDataAcquisitionDefInputInit.jsp"  flush='true'/>
</head>
<body  onload="initForm();initElementtype();">
  <form action="./CostDataAcquisitionDefSave.jsp" method=post name=fm id=fm target="fraSubmit">
  	  <Div id= "divCostDataAcquisitionDef" style= "display: ''">
  <table>
    	<tr>
        	 <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCostTypeDefQ);">
                </td>
    		 <td class= titleImg>
        		凭证费用数据采集
       		 </td>   		 
    	</tr>
    </table>
    
     <Div  id= "divCostTypeDefQ" style= "display: ''" class="maxbox1">
    <!--<td class=button width="10%" align=right>
				
	</td>-->
<table class= common border=0 width=100%>
  <table class= common>
	<tr class= common>
				 <TD class= title5>
					  版本编号
				 </TD>
				 <TD class=input5>
				 	<input class=wid name=VersionNo id=VersionNo readonly=true>
				</TD>
				<TD class= title5>
		   	   	版本状态
		    	</TD>
		    	<TD class= input5>
		    	<input class=wid name=VersionState2 id=VersionState2 readonly=true>
		   		 </TD>
	</tr>
	</table>
    </table>
    </div>
	<!--<INPUT class=cssButton name="querybutton" id="querybutton" VALUE="版本信息查询"  TYPE=button onclick="return VersionStateQuery();">-->
    <a href="javascript:void(0);" name="querybutton" id="querybutton" class="button" onClick="return VersionStateQuery();">版本信息查询</a><br><br>
    <div class="maxbox">
	
	
	<table class= common>
		<tr class= common>
				 <TD class= title5>
					  数据采集编号
				 </TD>
				 <TD class=input5>
				 	 <Input class=wid name=AcquisitionID id=AcquisitionID readonly=true>
				</TD>
				 	 
				<TD class= title5>
					  数据采集类型
				 </TD>
				 <TD class=input5>
				 	 <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name= AcquisitionType id= AcquisitionType verify="数据采集类型|NOTNULL"   ondblClick="showCodeList('AcquisitionType',[this,AcquisitionTypeName],[0,1],null,'AcquisitionType','codetype',[1]);" onMouseDown="showCodeList('AcquisitionType',[this,AcquisitionTypeName],[0,1],null,'AcquisitionType','codetype',[1]);" onkeyup="showCodeListKey('AcquisitionType',[this,AcquisitionTypeName],[0,1],null,'AcquisitionType','codetype',[1]);"><input class=codename name=AcquisitionTypeName readonly=true elementtype=nacessary>
				</TD>
		</tr>
		<tr class= common>
				 <TD class= title5>
					  费用或数据类型编码
				 </TD>
				 <td class=input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=CostOrDataID id=CostOrDataID verify="费用或数据类型编码|NOTNULL" ondblClick="showCodeList('CostOrDataID',[this,CostOrDataName],[0,1],null,fm.AcquisitionType.value,fm.VersionNo.value,[1]);" onMouseDown="showCodeList('CostOrDataID',[this,CostOrDataName],[0,1],null,fm.AcquisitionType.value,fm.VersionNo.value,[1]);" onkeyup="showCodeListKey('CostOrData',[this,CostOrDataName],[0,1],null,fm.AcquisitionType.value,fm.VersionNo.value,[1]);"><input class=codename name=CostOrDataName readonly=true elementtype=nacessary></TD>
          </TD>
				<TD class= title5>
					  数据采集方式
				 </TD>
				 <TD class=input5>
				 	 <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name= DistillMode id= DistillMode verify="数据采集方式|NOTNULL" readonly=true  CodeData="0|^1|通过Sql^2|通过类" ondblClick="showCodeListEx('DistillMode',[this,DistillModeName],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('DistillMode',[this,DistillModeName],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('DistillMode',[this,DistillModeName],[0,1],null,null,null,[1]);"><input class=codename name=DistillModeName readonly=true elementtype = nacessary >
				</TD>
	 </tr>
	 <tr class= common>
	 	<TD class= title5>
	 		采集数据库类型
	 	</TD>
	 	<TD class= input5>
          	<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name= DataSourceType id= DataSourceType verify="采集数据库类型|NOTNULL" readonly=true  CodeData="0^01|本地数据源|^02|外地数据源" ondblClick="showCodeListEx('DataSourceType',[this,DataSourceTypeName],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('DataSourceType',[this,DataSourceTypeName],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('DataSourceType',[this,DataSourceTypeName],[0,1],null,null,null,[1]);"><input class=codename name=DataSourceTypeName readonly=true elementtype = nacessary >
    </TD>
    <TD class= title5>
					  描述
				 </TD>
				 <TD class=input5>
				 	 <Input class=wid name=Remark id=Remark >
				</TD>
   </tr>

	</table>
	
<Div  id= "classdiv" style= "display: none" align=left>
	<table class=common>
	 <tr class= common>
				<TD class= title5>
					  采集处理类
				 </TD>
				 <TD class=input5>
				 	 <Input class=wid name=DistillClass id=DistillClass >
				</TD>
				<TD class= title5>
					  单一采集处理类
				 </TD>
				 <TD class=input5>
				 	 <Input class=wid name=DistillClassForOne id=DistillClassForOne >
				</TD>	
	</tr>
 </table> 	  
</div>	
<Div  id= "sqldiv" style= "display: none" align=left>
	<table class=common>
			<tr  class= common>
				<TD  class= common>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;采集SQL（主）</TD>
				<TD  class= common>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单一采集数SQL</TD>
			</tr>
            
			<tr  class= common>
				<TD  class= common style="padding-left:18px; padding-top:10px">
					<textarea name="DistillSQL" id="DistillSQL" verify="采集SQL（主）|len<4000" verifyorder="1" cols="66%" rows="5" witdh=50% class="common" ></textarea>
				</TD>
				<TD  class= common style="padding-left:18px; padding-top:10px">
					<textarea name="DistillSQLForOne" id="DistillSQLForOne" verify="单一采集数SQL|len<4000" verifyorder="1" cols="66%" rows="5" witdh=50% class="common" ></textarea>
				</TD>
			</tr>
			
		</table>
</div>
	<INPUT VALUE="凭证费用数据采集信息查询" TYPE=button class= cssButton onclick="queryClick()">
	<INPUT VALUE="申请采集" TYPE=button class= cssButton onclick="AcquisitionClick()">	
  <INPUT VALUE="添加" TYPE=button class= cssButton name= addbutton id= addbutton onclick="submitForm()">   
  <INPUT VALUE="修改" TYPE=button class= cssButton name= updatebutton id= updatebutton onclick="updateClick()">
  <INPUT VALUE="删除" TYPE=button class= cssButton name= deletebutton id= deletebutton onclick="deleteClick()">
  <INPUT VALUE="重置" TYPE=button class= cssButton name= resetbutton id= resetbutton onclick="resetAgain()">
  <br><br>
  <!--  <a href="javascript:void(0);" class="button" onClick="queryClick();">凭证费用数据采集信息查询</a>
    <a href="javascript:void(0);" class="button" onClick="AcquisitionClick();">申请采集</a>
    <a href="javascript:void(0);" class="button" name="addbutton" onClick="submitForm();">添    加</a>
    <a href="javascript:void(0);" class="button" name="updatebutton" onClick="updateClick();">修    改</a>
    <a href="javascript:void(0);" class="button" name="deletebutton" onClick="deleteClick();">删    除</a>
    <a href="javascript:void(0);" class="button" name="resetbutton" onClick="resetAgain();">重    置</a>-->
	</table>
    </div>
</Div>
<hr class="line">
			 <!--<INPUT class=cssButton name="CostDataBaseDefInputbutton" id="CostDataBaseDefInputbutton" VALUE="数据采集数据源定义"  TYPE=button onclick="return CostDataBaseDefInputClick();">
			 <INPUT class=cssButton name="CostDataKeyDefInputbutton" id="CostDataKeyDefInputbutton" VALUE="凭证费用数据主键定义"  TYPE=button onclick="return CostDataKeyDefInputClick();">--><br>
             <a href="javascript:void(0);" name="CostDataBaseDefInputbutton" id="CostDataBaseDefInputbutton" class="button" onClick="return CostDataBaseDefInputClick();">数据采集数据源定义</a>
             <a href="javascript:void(0);" name="CostDataKeyDefInputbutton" id="CostDataKeyDefInputbutton" class="button" onClick="return CostDataKeyDefInputClick();">凭证费用数据主键定义</a>
             
 <input type=hidden id="OperateType" name="OperateType">
 <INPUT TYPE=hidden NAME=tempAcquisitionID id=tempAcquisitionID VALUE=''>
 <INPUT TYPE=hidden NAME=tempAcquisitionType id=tempAcquisitionType VALUE=''>
 <INPUT TYPE=hidden NAME=tempCostOrDataID id=tempCostOrDataID VALUE=''>
 <INPUT type=hidden name=VersionState id=VersionState value=''>
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br> <br> <br> <br>
</body>
</html>
