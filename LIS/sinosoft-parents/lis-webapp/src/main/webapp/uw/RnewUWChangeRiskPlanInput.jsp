<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuSpec.jsp
//�����ܣ��˹��˱������б�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
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
  <title> �ͻ��б����۱��¼�� </title>
  <%@include file="RnewUWChangeRiskPlanInit.jsp"%>
</head>
<body  onload="initForm('<%=tContNo%>','<%=tMissionID%>','<%=tSubMissionID%>','<%=tPrtNo%>','<%=tInsuredNo%>');" >

  <form method=post name=fm id="fm" target="fraSubmit" action= "./RnewUWChangeRiskPlanChk.jsp">
    <!-- �����˱���¼���֣��б� -->
    <!--�����ӱ�������Ϣ-->
    <Div   style= "display:  ">
    	<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsuredBasic);">
    		</td>
    		<td class= titleImg>
    			�����˻�����Ϣ
    		</td>
    	 </tr>
       </table>	
    </Div>
    
    <DIV id=DivLCInsuredBasic class="maxbox1" STYLE="display:">
    <table  class= common>
	        <tr  class= common>
	          <td  class= title>
	            ������������
	          </TD>
	          <td  class= input>
	            <Input CLASS="readonly wid" readonly name="InsuredName" id="InsuredName" value="">
	          </TD>

	          <td  class= title>
	            �Ա�
	          </TD>
	          <td  class= input>
	            <Input CLASS="readonly wid" readonly name="InsuredSex" id="InsuredSex" value="">
	          </TD>
	          <td  class= title>
	            ����
	          </TD>
	          <td  class= input>
	            <Input CLASS="readonly wid" readonly name="InsuredAge" id="InsuredAge" value="">
	          </TD>
	        </tr>
	        <tr>
	        	<TD  class= title>
                ��Ͷ���˹�ϵ</TD>             
            <TD  class= input>
                <Input class="codeno" name="RelationToAppnt" id="RelationToAppnt" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" readonly ><Input class="codename" name="RelationToAppntName" id="RelationToAppntName" readonly  >
            </TD>                      
            
	        	<TD  class= title>
                ���һ�������˹�ϵ</TD>             
            <TD  class= input>
                <Input class="codeno" id="RelationToMainInsured" name="RelationToMainInsured" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" id="RelationToMainInsured" readonly ><Input class="codename" name="RelationToMainInsuredName" id="RelationToMainInsuredName" readonly  >
            </TD>  
            <td class="title">&nbsp;</td>
            <td class="input">&nbsp;</td>                     
	      	</tr>
	        <tr>
	          <td  class= title>
	            ְҵ
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="InsuredOccupationCode" id="InsuredOccupationCode" type="hidden">
	          <input CLASS="readonly wid" readonly name="InsuredOccupationCodeName" id="InsuredOccupationCodeName">
	          </TD>
	          <td  class= title>
	            ְҵ���
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="InsuredOccupationType" id="InsuredOccupationType" type="hidden">
	          <input CLASS="readonly wid" readonly name="InsuredOccupationTypeName" id="InsuredOccupationTypeName">
	          </TD>
	          <td  class= title>
	            ��ְ
	          </TD>
	          <td  class= input>
	            <Input CLASS="readonly wid" readonly name="InsuredPluralityType" id="InsuredPluralityType" value="">
	          </TD>
	        </tr>
	        </table>
	   </DIV>
 		  
     <!--��Լ��Ϣ-->
     <Div  id= "divSpec" style= "display: ">
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSpecAll);">
    		</td>
    		<td class= titleImg>
    			��Լ¼��
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
		    			�ѱ�����Լ��Ϣ
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
		    			��Լ��Ϣ
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
		    			������Լģ��
		    		</td>
		    	    </tr>
		        </table>	
		    	<Div  id= "divTemp" class="maxbox1" style= "display:  ">
		    		<table class=common>
		    		<tr>
		    		<td class=title5>
		         	ѡ�񼲲�ϵͳ
		      	</td>
		      	<td class=input5>
		            <Input class="codeno" name=HealthSpecTemp id="HealthSpecTemp" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" onClick="return showCodeList('healthspcetemp',[this,HealthSpecTempName],[0,1]);" onDblClick="return showCodeList('healthspcetemp',[this,HealthSpecTempName],[0,1]);" 
		            onkeyup="return showCodeListKey('healthspcetemp',[this,HealthSpecTempName,],[0,1]);"><Input class="codename" name=HealthSpecTempName id="HealthSpecTempName" readonly elementtype=nacessary>
		        </td>
		    		<td class=title5>
		         	ѡ����Լ����
		      	</td>
		      	<td class=input5>
		            <Input class="codeno" name=SpecTemp id="SpecTemp" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" onClick="return getClickSpecTemp();" onDblClick="return getClickSpecTemp();" onKeyUp="return getClickUpSpecTemp();"><Input class="codename" name=SpecTempname id="SpecTempname" readonly elementtype=nacessary>
		        </td>
		        </tr>
		        <tr>
		    	<TD  class= title5>
    	  		�Ƿ��·�
    			</TD>
	        	<TD class=input5>
	            <Input class="codeno" name=NeedPrintFlag id="NeedPrintFlag" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" CodeData = "0|^Y|�·�^N|���·�" onClick="showCodeListEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" ondblclick= "showCodeListEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" onKeyUp="showCodeListKeyEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" ><Input class=codename  name=IFNeedFlagName id="IFNeedFlagName" readonly = true>
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
		    			��Լ����
		    		</td>
		    	    </tr>
		    </table>	
		     <div class="maxbox1"> 
		        <table class = common  style= "display: none">
		    	<TR  class= common>
		          <TD  class= title>
		            ��Լԭ��
		          </TD>
		         </tr> <tr>         
		      <TD  class= title> <textarea name="SpecReason" id=SpecReason cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
		        </TR>
		    </table>
		
		
		    	
		    	<table class = common>
		    	<TR  class= common>
		          <TD  class= title>
		            �ر�Լ��
		          </TD>
		          </tr><tr>
		          
		      <TD  class= title> <textarea name="Remark" id=Remark cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
		        </TR>
		    	</table>
		     <table>
           </div>
		     	<!--��¼��Լ�������Լ����-->
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
		      
		      
		      
		      <INPUT type= "button" id="button1" name= "sure" value="��  ��" class= cssButton  onclick="submitForm(1)">			
		      <INPUT type= "button" id="button3" name= "sure" value="��  ��" class= cssButton  onclick="submitForm(2)">			
		      <INPUT type= "button" id="button4" name= "sure" value="ɾ  ��" class= cssButton  onclick="submitForm(3)">
		      <INPUT type= "button" id="button5" name= "sure" value="�޸��·����" class= cssButton  onclick="submitForm(4)">			
		      <!--INPUT type= "button" id="button2" name= "back" value="��  ��" class= cssButton  onclick="top.close();"-->					
			 </table>
			 </div>
	 </Div>
	  <!--��ҳ-->
	  <!--���ռƻ����-->
	  <jsp:include page="RnewManuUWChangePlanShow.jsp"/>
	  
	  <!--end ���ռƻ����-->
	  <!--�ӷ�¼��-->
	  <table>
    	<tr>
          <td class=common>
	    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);" alt="">
          </td>
    	  <td class= titleImg>
    	       �ӷ���Ϣ
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
        	<INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage_AddFee.firstPage();">
        	<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage_AddFee.previousPage();">
        	<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage_AddFee.nextPage();">
        	<INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage_AddFee.lastPage();">
    	</tr-->
        </Div>

      <INPUT type= "button" id="button1" name= "sure" value="��  ��" class= cssButton  onclick="addFeeSave();">			
      <!--INPUT type= "button" id="button3" name= "sure" value="��  ��" class= cssButton  onclick=""-->			
      <INPUT type= "button" id="button4" name= "sure" value="ɾ  ��" class= cssButton  onclick="deleteData()">			

	  <!---end �ӷ�¼��-->
    <!--��ȡ��Ϣ-->
    <hr class="line">
        <table class="common">
    	<TR  class= common>
          <TD  class= title>
            �б��ƻ�������ۼ��ӷ�ԭ��¼��
          </TD>
          </tr><tr>

      <TD  class= title> <textarea id="UWIdea" name="UWIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
      </TR>
     </table>
      <!--
    <INPUT type= "button" id="button6" name= "cancleGiven" value="ȡ����ǰ������Լ" class= cssButton  onclick="cancleGiven1()">
      -->
    <INPUT type= "button" id="button5" name= "sure" value="����˱�����" class= cssButton  onclick="makeRiskUWIdea();">			
    <input class= cssButton type= "button" value="��  ��" class= Common onClick="top.close();">
    <!-- ���ռƻ�������� -->
    
  </form>
  <br><br><br><br><br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
