<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuSpec.jsp
//程序功能：人工核保条件承保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
   String tQueryFlag = request.getParameter("QueryFlag");
   String tInsuredNo = request.getParameter("InsuredNo");
  String tContNo = "";
  String tMissionID = "";
  String tSubMissionID = "";
  tContNo = request.getParameter("ContNo");
  tMissionID = request.getParameter("MissionID");
  tSubMissionID = request.getParameter("SubMissionID");  
  String tInsuredSumLifeAmnt = request.getParameter("InsuredSumLifeAmnt");
  String tInsuredSumHealthAmnt = request.getParameter("InsuredSumHealthAmnt"); 
  String tEdorNo = request.getParameter("EdorNo"); 
  String tEdorAcceptNo = request.getParameter("EdorAcceptNo"); 
  String tEdorType = request.getParameter("EdorType"); 
   loggerDebug("BQChangeRiskPlanInput","tInsuredNo:"+tInsuredNo);
%>
 <script language="JavaScript">
	var tQueryFlag = "<%=tQueryFlag%>";
	var tContNo = "<%=tContNo%>";
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tInsuredNo = "<%=tInsuredNo%>";
	var tInsuredSumLifeAmnt = "<%=tInsuredSumLifeAmnt%>";
    var tInsuredSumHealthAmnt = "<%=tInsuredSumHealthAmnt%>";
    var tEdorNo = "<%=tEdorNo%>";
    var tEdorAcceptNo = "<%=tEdorAcceptNo%>";
    //alert(tEdorAcceptNo);
    var tEdorType = "<%=tEdorType%>";
 </script>                          

<html> 
<head >

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <%--<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT> --%>
  <script src="../common/laydate/laydate.js"></script>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="BQChangeRiskPlan.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> 客户承保结论变更录入 </title>
  <%@include file="BQChangeRiskPlanInit.jsp"%>
</head>
<body  onload="initForm();" >

  <form method=post name=fm id=fm target="fraSubmit" action= "./BQChangeRiskPlanChk.jsp">
    <!-- 以往核保记录部分（列表） -->
    <!--待增加被保人信息-->
    <Div   style= "display: ''">
    	<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsuredBasic);">
    		</td>
    		<td class= titleImg>
    			被保人基本信息
    		</td>
    	 </tr>
       </table>	
    </Div>
    
    <DIV id=DivLCInsuredBasic STYLE="display:''" class="maxbox">
    <table  class= common>
	        <tr  class= common>
	          <td  class= title>
	            被保险人姓名
	          </TD>
	          <td  class= input>
	            <Input CLASS="readonly wid" readonly name="InsuredName" id="InsuredName" value="">
	          </TD>

	          <td  class= title>
	            性别
	          </TD>
	          <td  class= input>
	            <Input CLASS="readonly wid" readonly name="InsuredSex" id="InsuredSex" value="">
	          </TD>
	          <td  class= title>
	            年龄
	          </TD>
	          <td  class= input>
	            <Input CLASS="readonly wid" readonly name="InsuredAge" id="InsuredAge" value="">
	          </TD>
	        </tr>
	        <tr>
	        	<TD  class= title>
                与投保人关系</TD>             
            <TD  class= input>
                <Input class="codeno" name="RelationToAppnt" id="RelationToAppnt"  readonly ><Input class="codename" name="RelationToAppntName" id="RelationToAppntName" readonly  >
            </TD>                      
            
	        	<TD  class= title>
                与第一被保险人关系</TD>             
            <TD  class= input>
                <Input class="codeno" name="RelationToMainInsured" id="RelationToMainInsured" readonly ><Input class="codename" name="RelationToMainInsuredName" id="RelationToMainInsuredName" readonly  >
            </TD>  
            <td class="title">&nbsp;</td>
            <td class="input">&nbsp;</td>                     
	      	</tr>
	        <tr>
	          <td  class= title>
	            职业
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly" readonly id="InsuredOccupationCode" name="InsuredOccupationCode" type="hidden">
	          <input CLASS="readonly wid" readonly name="InsuredOccupationCodeName" id="InsuredOccupationCodeName">
	          </TD>
	          <td  class= title>
	            职业类别
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly" readonly id="InsuredOccupationType" name="InsuredOccupationType" type="hidden">
	          <input CLASS="readonly wid" readonly name="InsuredOccupationTypeName" id="InsuredOccupationTypeName">
	          </TD>
	          <td  class= title>
	            兼职
	          </TD>
	          <td  class= input>
	            <Input CLASS="readonly wid" readonly name="InsuredPluralityType" id="InsuredPluralityType" value="">
	          </TD>
	        </tr>
	        </table>
	   </DIV>
 		  
     <!--特约信息-->
     <Div  id= "divSpec" style= "display: ''">
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSpecAll);">
    		</td>
    		<td class= titleImg>
    			特约录入
    		</td>
    	    </tr>
        </table>	
    </Div>
    <Div  id= "divSpecAll" style= "display: ''">
		     <Div  id= "divLCPol" style= "display: ''">
		      <table>
		    	<tr>
		        	<td class=common>
					    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPolSpec);">
		    		</td>
		    		<td class= titleImg>
		    			已保存特约信息
		    		</td>
		    	    </tr>
		        </table>	
		    </Div>
		    <div id="divLCPolSpec" style= "display: ''">
		      	<table  class= common>
		       		<tr  class= common>
		      	  		<td text-align: left colSpan=1 >
		  					<span id="spanUWSpecGrid" >
		  					</span> 
		  			  	</td>
		  			</tr>
		    	</table>     
		
		      	<table  class= common>
		       		<tr  class= common>
		      	  		<td text-align: left colSpan=1 >
		  					<span id="spanUWResultSpecGrid">
		  					</span> 
		  			  	</td>
		  			</tr>
		    	</table>
		    	
		    	 <table  style= "display: none">
		    	<tr>
		        	<td class=common>
					    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
		    		</td>
		    		<td class= titleImg>
		    			特约信息
		    		</td>
		    	    </tr>
		        </table>	
		      	<table  class= common  style= "display: none">
		       		<tr  class= common>
		      	  		<td text-align: left colSpan=1 >
		  					<span id="spanUWSpecContGrid" >
		  					</span> 
		  			  	</td>
		  			</tr>
		    	</table>
		    	
				<table>
		    	<tr>
		        	<td class=common>
					    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"" OnClick= "showPage(this,divTemp);">
		    		</td>
		    		<td class= titleImg>
		    			健康特约模板
		    		</td>
		    	    </tr>
		        </table>	
		    	<Div  id= "divTemp" style= "display: ''" class="maxbox1">
		    		<table class=common>
		    		<tr class=common>
		    		<td class=title>
		         	选择疾病系统
		      	</td>
		      	<td class=input>
		            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=HealthSpecTemp id=HealthSpecTemp ondblclick="return showCodeList('healthspcetemp',[this,HealthSpecTempName],[0,1]);"  onclick="return showCodeList('healthspcetemp',[this,HealthSpecTempName],[0,1]);" 
		            onkeyup="return showCodeListKey('healthspcetemp',[this,HealthSpecTempName,],[0,1]);"><Input class="codename" name=HealthSpecTempName id=HealthSpecTempName readonly elementtype=nacessary>
		        </td>
		    		<td class=title>
		         	选择特约内容
		      	</td>
		      	<td class=input>
		            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=SpecTemp id=SpecTemp ondblclick="return getClickSpecTemp();" onclick="return getClickSpecTemp();" onkeyup="return getClickUpSpecTemp();"><Input class="codename" name=SpecTempname id=SpecTempname readonly elementtype=nacessary>
		        </td>
                <TD  class= title>
    	  		是否下发
    			</TD>
	        	<TD class=input>
	            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=NeedPrintFlag id=NeedPrintFlag CodeData = "0|^Y|下发^N|不下发" ondblclick= "showCodeListEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" onclick= "showCodeListEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" onkeyup="showCodeListKeyEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" ><Input class=codename  name=IFNeedFlagName id=IFNeedFlagName readonly = true>
	        	</TD>	
		        </tr>
                
		       
		        </table>
		    	</Div>
		    <table style= "display: none">
		    	<tr>
		    	    <td class=common>
					    &nbsp;&nbsp;
		    		</td>
		        	<td class=common>
					    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"" OnClick= "showPage(this,divPayPlan1);">
		    		</td>
		    		<td class= titleImg>
		    			特约内容
		    		</td>
		    	    </tr>
		    </table>	
		        <Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
		        <table class = common  style= "display: none">
		    	<TR  class= common>
		          <TD  class= title>
		            特约原因
		          </TD>
		         </tr> <tr>         
		      <TD  colspan="6" style="padding-left:16px"> <textarea name="SpecReason" id="SpecReason" cols="224%" rows="4" witdh=100% class="common"></textarea></TD>
		        </TR>
		    </table>
		
		
		    	
		    	<table class = common>
		    	<TR  class= common>
		          <TD  class= title>
		            特别约定
		          </TD>
		          </tr><tr>
		          
		      <TD  colspan="6" style="padding-left:16px"> <textarea name="Remark" id="Remark" cols="224%" rows="4" witdh=100% class="common"></textarea></TD>
		        </TR>
		    	</table></Div>
		     <table>
		     	<!--记录特约编码和特约类型-->
		       <INPUT   type= "hidden" id="SpecType" name= "SpecType" value= "">
		       <INPUT  type= "hidden" id="SpecCode" name= "SpecCode" value= "">
		       <INPUT  type= "hidden" id="InsuredNo" name= "InsuredNo" value= "">
		      <INPUT type= "hidden" id="ContNo" name= "ContNo" value= "">
		 
		      <INPUT type= "hidden" id="MissionID" name= "MissionID" value= "">
		      <INPUT type= "hidden" id="SubMissionID" name= "SubMissionID" value="">
		      <INPUT type= "hidden" id="operate" name= "operate" value="">
		      <INPUT type= "hidden" id="proposalContNo" name= "proposalContNo" value="">
		      <INPUT type= "hidden" id="serialno" name= "serialno" value="">
		      <INPUT type= "hidden" id="PolNo" name= "PolNo" value="">
		      <INPUT type= "hidden" id="uwstate" name= "uwstate" value="">
		      <INPUT type= "hidden" id="flag" name= "flag" value="">
		      <INPUT type= "hidden" id="EdorNo" name= "EdorNo" value="">
		      <INPUT type= "hidden" id="EdorAcceptNo" name= "EdorAcceptNo" value="">
		      <INPUT type= "hidden" id="EdorType" name= "EdorType" value="">
		      <INPUT  type= "hidden" class= Common id="UpReporFlag" name= UpReporFlag value= "N">
		      
		      
		      
		      <INPUT type= "button" id="button1" name= "sure" value="添  加" class= cssButton  onclick="submitForm(1)">			
		      <INPUT type= "button" id="button3" name= "sure" value="修  改" class= cssButton  onclick="submitForm(2)">			
		      <INPUT type= "button" id="button4" name= "sure" value="删  除" class= cssButton  onclick="submitForm(3)">
		      <!-- <INPUT type= "button" id="button5" name= "sure" value="修改下发标记" class= cssButton  onclick="submitForm(4)">			
		      INPUT type= "button" id="button2" name= "back" value="返  回" class= cssButton  onclick="top.close();"-->					
			 </table>
			 </div>
	 </Div>
	  <!--分页-->
		
	  <!--保险计划变更-->
	  <Div  id= "ManuUWChangePlanShow" style= "display: ''" >
	  <table>
	  <jsp:include page="ManuUWChangePlanShow.jsp"/>
	  </table>
	  </Div>
	  
	  <!--end 保险计划变更-->
	  <!--加费录入-->
	  
	  <table>
    	<tr>
          <td class=common>
	    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);" alt="">
          </td>
    	  <td class= titleImg>
    	       加费信息（如果加费起期等于交至日期说明该笔加费已终止）
    	  </td>
    	</tr>
        </table>
      	<Div  id= "divLCPolApp" style= "display: ''" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanPolAddGrid" >
  					</span>
  			  	</td>
  			  	
  			</tr>
  			<!-- <tr class=common>
  				<td><Input class="code" name="addFormMethod" CodeData="0|^0|追溯^1|当期^2|下期"
            								 ondblClick="verifyChoose();return showCodeListEx('addFormMethod',[this],[0,1]);"
            								 onkeyup="return showCodeListKeyEx('addFormMethod',[this],[0,1]);">
            	</td>
  			</tr> -->
    	  </table>
    	<!--tr class=common align=center>
        	<INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage_AddFee.firstPage();">
        	<INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage_AddFee.previousPage();">
        	<INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage_AddFee.nextPage();">
        	<INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage_AddFee.lastPage();">
    	</tr-->
        </Div>

      <INPUT type= "button" id="button1" name= "sure" value="保  存" class= cssButton  onclick="addFeeSave();">			
      <!--INPUT type= "button" id="button3" name= "sure" value="修  改" class= cssButton  onclick=""-->			
      <INPUT type= "button" id="button4" name= "sure" value="删  除" class= cssButton  onclick="deleteData()">			

	  <!---end 加费录入-->
    <!--读取信息-->
    
    <Div  id= "divReason" style= "display: ''" >
        <table class="common">
    	<TR  class= common>
          <TD  class= title>
            承保计划变更结论及加费原因录入
          </TD>
          </tr><tr>

      <TD  colspan="6" style="padding-left:16px"> <textarea name="UWIdea" id="UWIdea" cols="224%" rows="4" witdh=100% class="common"></textarea></TD>
      </TR>
     </table>
    </Div>
    <!-- <INPUT type= "button" id="button6" name= "cancleGiven" disabled value="取消提前给付特约" class= cssButton  onclick="cancleGiven1()"> -->
    <INPUT type= "button" id="button5" name= "sure" value="保  存" class= cssButton  onclick="makeRiskUWIdea();">
    <input class= cssButton type= "button" value="返  回" class= Common onClick="top.close();">
    <!-- 保险计划变更结论 -->
    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
