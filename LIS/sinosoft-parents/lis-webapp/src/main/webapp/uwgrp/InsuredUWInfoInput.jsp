<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：InsuredUWInfoInput.jsp
//程序功能：被保人核保信息界面
//创建日期：2005-01-06 11:10:36
//创建人  ：HYQ
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<%
  //个人下个人
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录管理机构
	var ComCode = "<%=tGI.ComCode%>"; //记录登陆机构
	var ContNo = "<%=request.getParameter("ContNo")%>";
	var InsuredNo = "<%=request.getParameter("InsuredNo")%>";
	var PrtNo = "<%=request.getParameter("PrtNo")%>";
	var MissionID = "<%=request.getParameter("MissionID")%>";
	var SubMissionID = "<%=request.getParameter("SubMissionID")%>";
	var SendFlag = "<%=request.getParameter("SendFlag")%>";
	
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/javascript/VerifyInput.js"></script>
  <SCRIPT src="InsuredUWInfo.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="InsuredUWInfoInit.jsp"%>
  <title>被保人信息 </title>
</head>
<body  onload="initForm();" >
<form method=post name=fm target="fraSubmit" action= "./InsuredUWInfoChk.jsp">
<DIV id=DivLCInsured STYLE="display:''">
    <table class= common border=0 width=100%>
    	 <tr>
	        <td class= titleImg align= center>被保人信息：</td>
	     </tr>
    </table>
    <table  class= common>
            <TR  class= common>
          <TD  class= title>
            客户号码
          </TD>
          <TD  class= input>
            <Input class= common name=InsuredNo elementtype=nacessary verify="被保险人姓名|notnull&len<=20" >
          </TD>
          <TD  class= title>
            姓名
          </TD>
          <TD  class= input>
            <Input class= common name=Name elementtype=nacessary verify="被保险人姓名|notnull&len<=20" >
          </TD>

          <TD  class= title>
            性别
          </TD>
          <TD  class= input>
            <Input class="code" name=Sex elementtype=nacessary verify="被保险人性别|notnull&code:Sex" ondblclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);">
          </TD>
        </TR>
        <TR  class= common>
            <TD CLASS=title>
                被保人年龄
            </TD>
            <TD CLASS=input COLSPAN=1>
                <Input NAME=InsuredAppAge VALUE="" readonly=true CLASS=common verify="被保人年龄|num" >
            </TD>  
            <TD CLASS=title>
                国籍
            </TD>
            <TD CLASS=input COLSPAN=1>
                <Input NAME=NativePlace VALUE="" readonly=true CLASS=common >
            </TD>                    
           
           
            
              
	        <TD  class= title id=MainInsured style="display:">与主被保险人关系</TD>
            <TD  class= input id=MainInsuredInput style="display:">    
           <Input class="code" name="RelationToMainInsured"  verify="主被保险人关系|code:Relation" ondblclick="return showCodeList('Relation', [this]);" onkeyup="return showCodeListKey('Relation', [this]);"></TD>
	      
	      
	       
       </TR>
	    <TR class= common>
            <TD  class= title>
                职业代码
            </TD>
            <TD  class= input>
                <Input class="code" name="OccupationCode"  elementtype=nacessary verify="被保险人职业代码|code:OccupationCode&notnull" ondblclick="return showCodeList('OccupationCode',[this]);" onkeyup="return showCodeListKey('OccupationCode',[this]);" onfocus="getdetailwork();">
            </TD>
            <TD  class= title>
                职业类别
            </TD>
            <TD  class= input>
                <Input class="code" name="OccupationType"  verify="被保险人职业类别|code:OccupationType" ondblclick="return showCodeList('OccupationType',[this]);" onkeyup="return showCodeListKey('OccupationType',[this]);">
            </TD>
            
             <TD  class= title id=MainAppnt   style="display:">与投保人关系</TD>
            <TD  class= input id=MainAppntInput   style="display:">
           <Input class="code" name="RelationToAppnt"  ondblclick="return showCodeList('Relation', [this]);" onkeyup="return showCodeListKey('Relation', [this]);"></TD>    
    
	    </TR>
	   
		</Table>
</DIV>
<TABLE class= common>
    <TR class= common>
        <TD  class= title>
            <DIV id="divContPlan" style="display:'none'" >
	            <TABLE class= common>
		            <TR class= common>
			            <TD  class= title>
                            保险计划
                    </TD>
                    <TD  class= input>
                        <Input class="code" name="ContPlanCode" ondblclick="showCodeListEx('ContPlanCode',[this],[0],'', '', '', true);" onkeyup="showCodeListKeyEx('ContPlanCode',[this],[0],'', '', '', true);">
                    </TD>

                    </TR>
	            </TABLE>
            </DIV>
        </TD>
        <TD  class= title>
            <DIV id="divExecuteCom" style="display:'none'" >
	            <TABLE class= common>
		            <TR class= common>
			            <TD  class= title>
                            处理机构
                        </TD>
                        <TD  class= input>
                            <Input class="code" name="ExecuteCom" ondblclick="showCodeListEx('ExecuteCom',[this],[0],'', '', '', true);" onkeyup="showCodeListKeyEx('ExecuteCom',[this],[0],'', '', '', true);">
                        </TD>
		            </TR>
	            </TABLE>
            </DIV>
        </TD>
        <TD  class= title>
        </TD>
    </TR>
</TABLE>
<DIV id="divLCInsuredPerson" style="display:'none'">
  <TABLE class= common>
        <TR  class= common>
          <TD  class= title>
            婚姻状况
          </TD>
          <TD  class= input>
            <Input class="code" name="Marriage"  ondblclick="return showCodeList('Marriage',[this]);" onkeyup="return showCodeListKey('Marriage',[this]);"></TD>
           <!-- <Input class="code" name="Marriage" verify="被保险人婚姻状况|code:Marriage" ondblclick="return showCodeList('Marriage',[this]);" onkeyup="return showCodeListKey('Marriage',[this]);"></TD>-->
          <TD  class= title>
            与被保人关系</TD>
          <TD  class= input>
            <Input class="code" name="TheRelationToAppnt"  ondblclick="return showCodeList('Relation', [this]);" onkeyup="return showCodeListKey('Relation', [this]);"></TD>
           <!-- <Input class="code" name="RelationToAppnt" verify="与被保人关系|code:Relation" ondblclick="return showCodeList('Relation', [this]);" onkeyup="return showCodeListKey('Relation', [this]);"></TD>-->
          <TD  class= title>
            国籍
          </TD>
          <TD  class= input>
          <input class="code" name="TheNativePlace"  ondblclick="return showCodeList('NativePlace',[this]);" onkeyup="return showCodeListKey('NativePlace',[this]);">
          <!--<input class="code" name="NativePlace" verify="被保险人国籍|code:NativePlace" ondblclick="return showCodeList('NativePlace',[this]);" onkeyup="return showCodeListKey('NativePlace',[this]);">-->
          </TD>
        </TR>

        <TR class= common>
          <TD  class= title>
            户口所在地
          </TD>
          <TD  class= input>
            <Input class= common name="RgtAddress" >
            <!--<Input class= common name="RgtAddress" verify="被保险人户口所在地|len<=80" >-->
          </TD>
          <TD  class= title>
            民族
          </TD>
          <TD  class= input>
          <input class="code" name="Nationality"  ondblclick="return showCodeList('Nationality',[this]);" onkeyup="return showCodeListKey('Nationality',[this]);">
         <!-- <input class="code" name="Nationality" verify="被保险人民族|code:Nationality" ondblclick="return showCodeList('Nationality',[this]);" onkeyup="return showCodeListKey('Nationality',[this]);">-->
          </TD>
          <TD  class= title>
            学历
          </TD>
          <TD  class= input>
            <Input class="code" name="Degree"  ondblclick="return showCodeList('Degree',[this]);" onkeyup="return showCodeListKey('Degree',[this]);">
            <!--<Input class="code" name="Degree" verify="被保险人学历|code:Degree" ondblclick="return showCodeList('Degree',[this]);" onkeyup="return showCodeListKey('Degree',[this]);">-->
          </TD>
        </TR>

        <TR  class= common>
          <TD  class= title>
            工作单位
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common3 name="GrpName"  >
            <!--<Input class= common3 name="GrpName" verify="被保险人工作单位|len<=60" >-->
          </TD>
          <TD  class= title>
            单位电话
          </TD>
          <TD  class= input>
            <Input class= common name="GrpPhone"  >
            <!--<Input class= common name="GrpPhone" verify="被保险人单位电话|len<=18" >-->
          </TD>
        </TR>

        <TR  class= common>
          <TD  class= title>
            单位地址
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common3 name="GrpAddress"  >
            <!--<Input class= common3 name="GrpAddress" verify="被保险人单位地址|len<=80" >-->
          </TD>
          <TD  class= title>
            单位邮政编码
          </TD>
          <TD  class= input>
            <Input class= common name="GrpZipCode"  >
            <!-- <Input class= common name="GrpZipCode" verify="被保险人单位邮政编码|zipcode" >-->
          </TD>
        </TR>

        <TR  class= common>
          <TD  class= title>
            职业（工种）
          </TD>
          <TD  class= input>
            <Input class= common name="WorkType"  >
            <!--<Input class= common name="WorkType" verify="被保险人职业（工种）|len<=10" >-->
          </TD>
          <TD  class= title>
            兼职（工种）
          </TD>
          <TD  class= input>
            <Input class= common name="PluralityType"  >
            <!--<Input class= common name="PluralityType" verify="被保险人兼职（工种）|len<=10" >-->
          </TD>
          <TD  class= title>
            是否吸烟
          </TD>
          <TD  class= input>
            <Input class="code" name="SmokeFlag"  ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
            <!--<Input class="code" name="SmokeFlag" verify="被保险人是否吸烟|code:YesNo" ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">-->
          </TD>
       </TR>
       <Input name=PolNo type=hidden>
       <Input name=ContNo type=hidden>
       <Input name=MissionID type=hidden>
       <Input name=SubMissionID type=hidden>
       <Input name=flag type=hidden>
       <Input name=PrtNo type=hidden>
       <Input name=Stature type=hidden>
       <Input name=Avoirdupois type=hidden>
       <Input name=BMI type=hidden>
      </table>
</DIV>
<DIV id=DivOldInfo STYLE="display:'none'">
    <table class= common border=0 width=100%>
    	 <tr>
	        <td class= titleImg align= center>既往信息：</td>
	     </tr>
    </table>
    <table  class= common>
       <tr  class= common>
      	 <td text-align: left colSpan=1>
  					<span id="spanOldInfoGrid" >
  					</span>
  			 </td>
  		 </tr>
    </table>
</DIV>

<hr>
<table>
  <tr>
    <td>
    <!--INPUT VALUE="  被保人既往投保信息  " class=cssButton TYPE=button onclick="showApp(1);"--> 
    <!--<INPUT VALUE="  被保人影像资料查询  " class=cssButton TYPE=button onclick=""> -->
    <INPUT VALUE="  被保人健康告知查询  " class=cssButton TYPE=button onclick="queryHealthImpart()">  
    <INPUT VALUE="  被保人体检资料查询  " class=cssButton TYPE=button onclick="queryHealthReportResult();"> 
    <INPUT VALUE="被保人保额累计提示信息" class=cssButton TYPE=button onclick="amntAccumulate();"> 
    </td>
  </tr>
  <tr>
    <td>
    <INPUT VALUE=" 被保人已承保保单查询 " class=cssButton TYPE=button onclick="queryProposal();"> 
    <INPUT VALUE="被保人未承保投保单查询" class=cssButton TYPE=button onclick="queryNotProposal();"> 
    <INPUT VALUE="被保人既往保全信息查询" class=cssButton TYPE=button onclick="queryEdor()"> 
    <INPUT VALUE="被保人既往理赔信息查询" class=cssButton TYPE=button onclick="queryClaim()"> 
    </td>
  </tr>
  <tr>
    <td>
    </td>
  </tr>
</table>
<hr>
<table>
<tr>
  <td>
    <input value="     加费承保录入     " class=cssButton type=button name= "AddFee"  onclick="showAdd();">
    <input value="     特约承保录入     " class=cssButton type=button onclick="showSpec();">
  </td>
</tr>
</table>

<hr></hr>
<DIV id=DivLCPol STYLE="display:''">
    <table class= common border=0 width=100%>
    	 <tr>
	        <td class= titleImg align= center>险种信息：</td>
	     </tr>
    </table>
    <table  class= common>
       <tr  class= common>
      	 <td text-align: left colSpan=1>
  					<span id="spanRiskGrid" >
  					</span>
  			 </td>
  		 </tr>
    </table>
</DIV>

<div id = "divUWResult" style = "display: ''">
    	  <!-- 核保结论 -->
    	  <table class= common border=0 width=100%>
    	  	<tr>
			<td class= titleImg align= center>险种核保结论：</td>
	  	</tr>
	  </table>
	   		 	   	
  	  <table  class="common">
    	  			
    	  	<TR >

          		<TD class= title>
          		 	<!--span id= "UWResult"> 保全核保结论 <Input class="code" name=uwstate value= "1" CodeData= "0|^1|本级保单^2|下级保单" ondblClick="showCodeListEx('uwstate',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('uwstate',[this,''],[0,1]);">  </span-->
          		 	核保结论
          		 </td>
          		 <td class=input>
          		 <Input class="codeno" name=uwstate ondblclick="return showCodeList('uwstate',[this,uwstatename],[0,1]);" onkeyup="return showCodeListKey('uwstate',[this,uwstatename],[0,1]);"><input type="text" class="codename" readonly="readonly" name="uwstatename" elementtype=nacessary>
	   		 	   </TD>
	   		 	  
	   		 	  
	   		 	   </div>
	   		 	   <td class=input>
	   		 	   </td>
	   		 	   <td class=input>
	   		 	   </td>
          	</TR>
          </table>
         <table class=common>
		
		<tr>
		      	<TD height="24"  class= title>
            		核保意见
          	</TD>
          	<td></td>
          	</tr>
		<tr>
      		<TD  class= input> <textarea name="UWIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
      		<td></td>
      		</tr>
	  </table>
	  <div id =divUWButton1 style="display:''">
	  <p>
    		<INPUT VALUE="确  定" class=cssButton TYPE=button onclick="submitForm(1);">
    		<INPUT VALUE="取  消" class=cssButton TYPE=button onclick="cancelchk();">
    		 <INPUT VALUE="返  回" class=cssButton TYPE=button onclick="InitClick();">
  	</p>
  </div>
</div>

<DIV id=DivButton STYLE="display:''">
<!--table>
<tr>
	<td>
          <input value="体检资料查询" class=cssButton type=button onclick="showHealthQ();" >
          <INPUT VALUE="生存调查查询" class=cssButton TYPE=button onclick="RReportQuery();">
          <input value="体检资料录入" class=cssButton type=button onclick="showHealth();" width="200">
          <input value="生调请求说明" class=cssButton type=button onclick="showRReport();">

  </td>
</tr>
</table-->


</DIV>
	
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
