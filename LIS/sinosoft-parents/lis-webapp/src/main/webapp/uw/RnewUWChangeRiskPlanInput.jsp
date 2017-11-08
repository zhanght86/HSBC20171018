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
  String tPrtNo = "";
  String tContNo = "";
  String tMissionID = "";
  String tSubMissionID = "";
  tPrtNo = request.getParameter("PrtNo");
  tContNo = request.getParameter("ContNo");
  tMissionID = request.getParameter("MissionID");
  tSubMissionID = request.getParameter("SubMissionID");  
  String tInsuredSumLifeAmnt = request.getParameter("InsuredSumLifeAmnt");
  String tInsuredSumHealthAmnt = request.getParameter("InsuredSumHealthAmnt"); 
   loggerDebug("RnewUWChangeRiskPlanInput","tInsuredNo:"+tInsuredNo);
%>
 <script language="JavaScript">
	var tQueryFlag = "<%=tQueryFlag%>";
	var mContNo = "<%=tContNo%>";
	var mMissionID = "<%=tMissionID%>";
	var mSubMissionID = "<%=tSubMissionID%>";
	var mInsuredNo = "<%=tInsuredNo%>";
	var tInsuredSumLifeAmnt = "<%=tInsuredSumLifeAmnt%>";
    var tInsuredSumHealthAmnt = "<%=tInsuredSumHealthAmnt%>";
 </script>                          

<html> 
<head >

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="RnewUWChangeRiskPlan.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> 客户承保结论变更录入 </title>
  <%@include file="RnewUWChangeRiskPlanInit.jsp"%>
</head>
<body  onload="initForm('<%=tContNo%>','<%=tMissionID%>','<%=tSubMissionID%>','<%=tPrtNo%>','<%=tInsuredNo%>');" >

  <form method=post name=fm id="fm" target="fraSubmit" action= "./RnewUWChangeRiskPlanChk.jsp">
    <!-- 以往核保记录部分（列表） -->
    <!--待增加被保人信息-->
    <Div   style= "display:  ">
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
    
    <DIV id=DivLCInsuredBasic class="maxbox1" STYLE="display:">
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
                <Input class="codeno" name="RelationToAppnt" id="RelationToAppnt" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" readonly ><Input class="codename" name="RelationToAppntName" id="RelationToAppntName" readonly  >
            </TD>                      
            
	        	<TD  class= title>
                与第一被保险人关系</TD>             
            <TD  class= input>
                <Input class="codeno" id="RelationToMainInsured" name="RelationToMainInsured" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" id="RelationToMainInsured" readonly ><Input class="codename" name="RelationToMainInsuredName" id="RelationToMainInsuredName" readonly  >
            </TD>  
            <td class="title">&nbsp;</td>
            <td class="input">&nbsp;</td>                     
	      	</tr>
	        <tr>
	          <td  class= title>
	            职业
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="InsuredOccupationCode" id="InsuredOccupationCode" type="hidden">
	          <input CLASS="readonly wid" readonly name="InsuredOccupationCodeName" id="InsuredOccupationCodeName">
	          </TD>
	          <td  class= title>
	            职业类别
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="InsuredOccupationType" id="InsuredOccupationType" type="hidden">
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
     <Div  id= "divSpec" style= "display: ">
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
    <Div  id= "divSpecAll"  style= "display: ">
		     <Div  id= "divLCPol" style= "display: ">
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
		    <div id="divLCPolSpec" style= "display: ">
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
					    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"">
		    		</td>
		    		<td class= titleImg>
		    			健康特约模板
		    		</td>
		    	    </tr>
		        </table>	
		    	<Div  id= "divTemp" class="maxbox1" style= "display:  ">
		    		<table class=common>
		    		<tr>
		    		<td class=title5>
		         	选择疾病系统
		      	</td>
		      	<td class=input5>
		            <Input class="codeno" name=HealthSpecTemp id="HealthSpecTemp" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" onClick="return showCodeList('healthspcetemp',[this,HealthSpecTempName],[0,1]);" onDblClick="return showCodeList('healthspcetemp',[this,HealthSpecTempName],[0,1]);" 
		            onkeyup="return showCodeListKey('healthspcetemp',[this,HealthSpecTempName,],[0,1]);"><Input class="codename" name=HealthSpecTempName id="HealthSpecTempName" readonly elementtype=nacessary>
		        </td>
		    		<td class=title5>
		         	选择特约内容
		      	</td>
		      	<td class=input5>
		            <Input class="codeno" name=SpecTemp id="SpecTemp" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" onClick="return getClickSpecTemp();" onDblClick="return getClickSpecTemp();" onKeyUp="return getClickUpSpecTemp();"><Input class="codename" name=SpecTempname id="SpecTempname" readonly elementtype=nacessary>
		        </td>
		        </tr>
		        <tr>
		    	<TD  class= title5>
    	  		是否下发
    			</TD>
	        	<TD class=input5>
	            <Input class="codeno" name=NeedPrintFlag id="NeedPrintFlag" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" CodeData = "0|^Y|下发^N|不下发" onClick="showCodeListEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" ondblclick= "showCodeListEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" onKeyUp="showCodeListKeyEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" ><Input class=codename  name=IFNeedFlagName id="IFNeedFlagName" readonly = true>
	        	</TD>
                <TD  class= title5></TD>
	        	<TD class= input5></TD>		    		
		        </tr>
		        </table>
		    	</Div>
		    <table style= "display: none">
		    	<tr>
		    	    
		        	<td class=common>
					    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"">
		    		</td>
		    		<td class= titleImg>
		    			特约内容
		    		</td>
		    	    </tr>
		    </table>	
		     <div class="maxbox1"> 
		        <table class = common  style= "display: none">
		    	<TR  class= common>
		          <TD  class= title>
		            特约原因
		          </TD>
		         </tr> <tr>         
		      <TD  class= title> <textarea name="SpecReason" id=SpecReason cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
		        </TR>
		    </table>
		
		
		    	
		    	<table class = common>
		    	<TR  class= common>
		          <TD  class= title>
		            特别约定
		          </TD>
		          </tr><tr>
		          
		      <TD  class= title> <textarea name="Remark" id=Remark cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
		        </TR>
		    	</table>
		     <table>
           </div>
		     	<!--记录特约编码和特约类型-->
		       <INPUT   type= "hidden" id="SpecType" name= "SpecType" value= "">
		       <INPUT  type= "hidden" id="SpecCode"  name= "SpecCode" value= "">
		       <INPUT  type= "hidden" id="InsuredNo"  name= "InsuredNo" value= "">
		      <INPUT type= "hidden" id="ContNo" name= "ContNo" value= "">
		 
		      <input type= "hidden" id="PrtNo" name= "PrtNo" value="">
		      <INPUT type= "hidden" id="MissionID" name= "MissionID" value= "">
		      <INPUT type= "hidden" id="SubMissionID" name= "SubMissionID" value="">
		      <INPUT type= "hidden" id="operate" name= "operate" value="">
		      <INPUT type= "hidden" id="proposalContNo" name= "proposalContNo" value="">
		      <INPUT type= "hidden" id="serialno" name= "serialno" value="">
		      <INPUT type= "hidden" id="PolNo" name= "PolNo" value="">
		      <INPUT type= "hidden" id="uwstate" name= "uwstate" value="">
		      <INPUT type= "hidden" id="flag" name= "flag" value="">
		      <INPUT  type= "hidden" class= Common id="UpReporFlag" name= UpReporFlag value= "N">
		      
		      
		      
		      <INPUT type= "button" id="button1" name= "sure" value="添  加" class= cssButton  onclick="submitForm(1)">			
		      <INPUT type= "button" id="button3" name= "sure" value="修  改" class= cssButton  onclick="submitForm(2)">			
		      <INPUT type= "button" id="button4" name= "sure" value="删  除" class= cssButton  onclick="submitForm(3)">
		      <INPUT type= "button" id="button5" name= "sure" value="修改下发标记" class= cssButton  onclick="submitForm(4)">			
		      <!--INPUT type= "button" id="button2" name= "back" value="返  回" class= cssButton  onclick="top.close();"-->					
			 </table>
			 </div>
	 </Div>
	  <!--分页-->
	  <!--保险计划变更-->
	  <jsp:include page="RnewManuUWChangePlanShow.jsp"/>
	  
	  <!--end 保险计划变更-->
	  <!--加费录入-->
	  <table>
    	<tr>
          <td class=common>
	    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);" alt="">
          </td>
    	  <td class= titleImg>
    	       加费信息
    	  </td>
    	</tr>
        </table>
      	<Div  id= "divLCPolApp" style= "display:  " >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanPolAddGrid" >
  					</span>
  			  	</td>
  			  </tr>
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
    <hr class="line">
        <table class="common">
    	<TR  class= common>
          <TD  class= title>
            承保计划变更结论及加费原因录入
          </TD>
          </tr><tr>

      <TD  class= title> <textarea id="UWIdea" name="UWIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
      </TR>
     </table>
      <!--
    <INPUT type= "button" id="button6" name= "cancleGiven" value="取消提前给付特约" class= cssButton  onclick="cancleGiven1()">
      -->
    <INPUT type= "button" id="button5" name= "sure" value="保存核保结论" class= cssButton  onclick="makeRiskUWIdea();">			
    <input class= cssButton type= "button" value="返  回" class= Common onClick="top.close();">
    <!-- 保险计划变更结论 -->
    
  </form>
  <br><br><br><br><br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
