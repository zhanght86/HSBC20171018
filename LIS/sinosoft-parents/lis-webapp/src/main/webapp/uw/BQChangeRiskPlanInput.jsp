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
  <title> �ͻ��б����۱��¼�� </title>
  <%@include file="BQChangeRiskPlanInit.jsp"%>
</head>
<body  onload="initForm();" >

  <form method=post name=fm id=fm target="fraSubmit" action= "./BQChangeRiskPlanChk.jsp">
    <!-- �����˱���¼���֣��б� -->
    <!--�����ӱ�������Ϣ-->
    <Div   style= "display: ''">
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
    
    <DIV id=DivLCInsuredBasic STYLE="display:''" class="maxbox">
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
                <Input class="codeno" name="RelationToAppnt" id="RelationToAppnt"  readonly ><Input class="codename" name="RelationToAppntName" id="RelationToAppntName" readonly  >
            </TD>                      
            
	        	<TD  class= title>
                ���һ�������˹�ϵ</TD>             
            <TD  class= input>
                <Input class="codeno" name="RelationToMainInsured" id="RelationToMainInsured" readonly ><Input class="codename" name="RelationToMainInsuredName" id="RelationToMainInsuredName" readonly  >
            </TD>  
            <td class="title">&nbsp;</td>
            <td class="input">&nbsp;</td>                     
	      	</tr>
	        <tr>
	          <td  class= title>
	            ְҵ
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly" readonly id="InsuredOccupationCode" name="InsuredOccupationCode" type="hidden">
	          <input CLASS="readonly wid" readonly name="InsuredOccupationCodeName" id="InsuredOccupationCodeName">
	          </TD>
	          <td  class= title>
	            ְҵ���
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly" readonly id="InsuredOccupationType" name="InsuredOccupationType" type="hidden">
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
     <Div  id= "divSpec" style= "display: ''">
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
    <Div  id= "divSpecAll" style= "display: ''">
		     <Div  id= "divLCPol" style= "display: ''">
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
					    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"" OnClick= "showPage(this,divTemp);">
		    		</td>
		    		<td class= titleImg>
		    			������Լģ��
		    		</td>
		    	    </tr>
		        </table>	
		    	<Div  id= "divTemp" style= "display: ''" class="maxbox1">
		    		<table class=common>
		    		<tr class=common>
		    		<td class=title>
		         	ѡ�񼲲�ϵͳ
		      	</td>
		      	<td class=input>
		            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=HealthSpecTemp id=HealthSpecTemp ondblclick="return showCodeList('healthspcetemp',[this,HealthSpecTempName],[0,1]);"  onclick="return showCodeList('healthspcetemp',[this,HealthSpecTempName],[0,1]);" 
		            onkeyup="return showCodeListKey('healthspcetemp',[this,HealthSpecTempName,],[0,1]);"><Input class="codename" name=HealthSpecTempName id=HealthSpecTempName readonly elementtype=nacessary>
		        </td>
		    		<td class=title>
		         	ѡ����Լ����
		      	</td>
		      	<td class=input>
		            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=SpecTemp id=SpecTemp ondblclick="return getClickSpecTemp();" onclick="return getClickSpecTemp();" onkeyup="return getClickUpSpecTemp();"><Input class="codename" name=SpecTempname id=SpecTempname readonly elementtype=nacessary>
		        </td>
                <TD  class= title>
    	  		�Ƿ��·�
    			</TD>
	        	<TD class=input>
	            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=NeedPrintFlag id=NeedPrintFlag CodeData = "0|^Y|�·�^N|���·�" ondblclick= "showCodeListEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" onclick= "showCodeListEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" onkeyup="showCodeListKeyEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" ><Input class=codename  name=IFNeedFlagName id=IFNeedFlagName readonly = true>
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
		    			��Լ����
		    		</td>
		    	    </tr>
		    </table>	
		        <Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
		        <table class = common  style= "display: none">
		    	<TR  class= common>
		          <TD  class= title>
		            ��Լԭ��
		          </TD>
		         </tr> <tr>         
		      <TD  colspan="6" style="padding-left:16px"> <textarea name="SpecReason" id="SpecReason" cols="224%" rows="4" witdh=100% class="common"></textarea></TD>
		        </TR>
		    </table>
		
		
		    	
		    	<table class = common>
		    	<TR  class= common>
		          <TD  class= title>
		            �ر�Լ��
		          </TD>
		          </tr><tr>
		          
		      <TD  colspan="6" style="padding-left:16px"> <textarea name="Remark" id="Remark" cols="224%" rows="4" witdh=100% class="common"></textarea></TD>
		        </TR>
		    	</table></Div>
		     <table>
		     	<!--��¼��Լ�������Լ����-->
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
		      
		      
		      
		      <INPUT type= "button" id="button1" name= "sure" value="��  ��" class= cssButton  onclick="submitForm(1)">			
		      <INPUT type= "button" id="button3" name= "sure" value="��  ��" class= cssButton  onclick="submitForm(2)">			
		      <INPUT type= "button" id="button4" name= "sure" value="ɾ  ��" class= cssButton  onclick="submitForm(3)">
		      <!-- <INPUT type= "button" id="button5" name= "sure" value="�޸��·����" class= cssButton  onclick="submitForm(4)">			
		      INPUT type= "button" id="button2" name= "back" value="��  ��" class= cssButton  onclick="top.close();"-->					
			 </table>
			 </div>
	 </Div>
	  <!--��ҳ-->
		
	  <!--���ռƻ����-->
	  <Div  id= "ManuUWChangePlanShow" style= "display: ''" >
	  <table>
	  <jsp:include page="ManuUWChangePlanShow.jsp"/>
	  </table>
	  </Div>
	  
	  <!--end ���ռƻ����-->
	  <!--�ӷ�¼��-->
	  
	  <table>
    	<tr>
          <td class=common>
	    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);" alt="">
          </td>
    	  <td class= titleImg>
    	       �ӷ���Ϣ������ӷ����ڵ��ڽ�������˵���ñʼӷ�����ֹ��
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
  				<td><Input class="code" name="addFormMethod" CodeData="0|^0|׷��^1|����^2|����"
            								 ondblClick="verifyChoose();return showCodeListEx('addFormMethod',[this],[0,1]);"
            								 onkeyup="return showCodeListKeyEx('addFormMethod',[this],[0,1]);">
            	</td>
  			</tr> -->
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
    
    <Div  id= "divReason" style= "display: ''" >
        <table class="common">
    	<TR  class= common>
          <TD  class= title>
            �б��ƻ�������ۼ��ӷ�ԭ��¼��
          </TD>
          </tr><tr>

      <TD  colspan="6" style="padding-left:16px"> <textarea name="UWIdea" id="UWIdea" cols="224%" rows="4" witdh=100% class="common"></textarea></TD>
      </TR>
     </table>
    </Div>
    <!-- <INPUT type= "button" id="button6" name= "cancleGiven" disabled value="ȡ����ǰ������Լ" class= cssButton  onclick="cancleGiven1()"> -->
    <INPUT type= "button" id="button5" name= "sure" value="��  ��" class= cssButton  onclick="makeRiskUWIdea();">
    <input class= cssButton type= "button" value="��  ��" class= Common onClick="top.close();">
    <!-- ���ռƻ�������� -->
    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
