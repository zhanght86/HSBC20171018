<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%> 
<%
//�������ƣ�LLDealUWsecondSendAllNoticeMain.jsp
//�����ܣ�������˷��ͺ˱�֪ͨ��
//�������ڣ�2009-01-15 09:54:36
//���¼�¼��  ������    ��������     ����ԭ��/����

	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tCaseNo = request.getParameter("CaseNo");
	loggerDebug("LLDealUWsecondSendAllNotice","tCaseNo:"+tCaseNo+",tMissionID:"+tMissionID+",tSubMissionID:"+tSubMissionID);

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="LLDealUWsecondSendAllNotice.js"></SCRIPT>
  <%@include file="LLDealUWsecondSendAllNoticeInit.jsp"%>
  <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>�˱������¼��</title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit" action= "./LLDealUWsecondSendAllNoticeChk.jsp">
  	    <div class=maxbox1>
  		<table>
		<tr>
			<td><input class="mulinetitle" value="�˱�֪ͨ����Ϣ"  name="InsuredSequencename" id=InsuredSequencename readonly ></td>
		</tr>
	</table>
	</div>
	<!--
   <table  class= common align=center>
    	<TR  class= common>
    		  <TD  class= title>
            ֪ͨ������
          </TD>
          <TD>
          <Input class= "codeno" name = NoticeType ondblclick="return showCodeList('uwnoticetype',[this,NoticeTypeName],[0,1]);" onkeyup="return showCodeListKey('noticetype',[this,NoticeTypeName],[0,1]);"><Input class = codename name=NoticeTypeName readonly = true>
          </TD> 
    
  </table>
  -->
    
    <div id= "divnoticecontent" style= "display: 'none'" >	
  <table class= common align=center width="121%" height="37%">
    <TR  class= common > 
      <TD width="100%" height="13%"  class= title> ֪ͨ������ </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="Content" id=Content cols="135" rows="10" class="common"></textarea></TD>
    </TR>
  </table>
</div>

    <Div  id= "divUWSpec1" style= "display: 'none'">
    <table>
    	<tr>
        	<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divQuestionType);"></td>
    		<td class= titleImg>	 �ʾ�����ѡ��</td>                            
    	</tr>	
    </table>
  </div>
    <Div  id= "divQuestionType" style= "display: 'none'">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style="text-align: left" colSpan=1 >
  							<span id="spanQuestionTypeGrid"></span> 
  		  			</td>
  				</tr>
    	</table>
   </Div>        
   <Div  id= "divDone" style= "display: ''">
     <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butCollapse.gif" style= "cursor:hand;" OnClick= "showPage(this,divAll);">
    		</td>
    		<td class= titleImg>
    			�ѷ�����Ϣ
    		</td>
    	    </tr>
      </table>
      <table>      
	    	<tr>
	        	<td class=common>
	  &nbsp;&nbsp;</td><td class=common>
	  <Div  id= "divAll" style= "display: ''">
	      <!-- <table>
	    	<tr>
	        	<td class=common>
				    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divQuestD);">
	    		</td>
	    		<td class= titleImg>
	    			�ѷ����������Ϣ
	    		</td>
	    	    </tr>
	      </table>
	      <Div  id= "divQuestD" style= "display: ''">	
	      	<table  class= common>
	       		<tr  class= common>
	      	  		<td style="text-align: left" colSpan=1 >
	  					<span id="spanQuestDGrid" >
	  					</span> 
	  			  	</td>
	  			</tr>
	    	</table>
	    	<div align=center>
			    	<INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPageQuestD.firstPage();">
					<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPageQuestD.previousPage();">
					<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPageQuestD.nextPage();">
					<INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPageQuestD.lastPage();">
			</div>
	      </Div>	       
	      <table>
	    	<tr>
	        	<td class=common>
				    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpecD);">
	    		</td>
	    		<td class= titleImg>
	    			�ѷ�����Լ��Ϣ
	    		</td>
	    	    </tr>
	      </table>
	      <Div  id= "divUWSpecD" style= "display: ''">	
	      	<table  class= common>
	       		<tr  class= common>
	      	  		<td style="text-align: left" colSpan=1 >
	  					<span id="spanUWSpecDGrid" >
	  					</span> 
	  			  	</td>
	  			</tr>
	    	</table>
	      </Div>  -->
	      <table>
	    	<tr>
	        	<td class=common>
				    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWHealthD);">
	    		</td>
	    		<td class= titleImg>
	    			�ѷ��������Ϣ
	    		</td>
	    	    </tr>
	      </table>
	      <Div  id= "divUWHealthD" style= "display: ''">	
	      	<table  class= common>
	       		<tr  class= common>
	      	  		<td style="text-align: left" colSpan=1 >
	  					<span id="spanUWHealthDGrid" >
	  					</span> 
	  			  	</td>
	  			</tr>
	    	</table>
	      </Div>
		  <!--
	      <table>
	    	<tr>
	        	<td class=common>
				    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWExistD);">
	    		</td>
	    		<td class= titleImg>
	    			�ѷ���������Ϣ
	    		</td>
	    	    </tr>
	      </table>
	      <Div  id= "divUWExistD" style= "display: ''">	
	      	<table  class= common>
	       		<tr  class= common>
	      	  		<td style="text-align: left" colSpan=1 >
	  					<span id="spanUWExistDGrid" >
	  					</span> 
	  			  	</td>
	  			</tr>
	    	</table>
	      </Div>  -->	      
	    <!-- 
        <table>
	    	<tr>
	        <td class=common>
	          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid);">
	    	</td>
	    	<td class= titleImg>
	    	 �ѷ�֪ͨ��
	    	</td>
	    </tr>
	    </table>    
		<div id= "divPolGrid" style= "display: ''" >
	        <table  class= common>
	       		<tr  class= common>
	      	  		<td style="text-align: left" colSpan=1>
	  					<span id="spanPolGrid" >
	  					</span> 
	  			  	</td>
	  			</tr>
	    	</table>
	    	<div align=center>
		        <INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage2.firstPage();">
				<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.previousPage();">
				<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.nextPage();">
				<INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage2.lastPage();">
			</div>
	  </div>
	  --> 
	 </Div>
	                 </td>
	    	    </tr>
	      </table>
  </Div>  
  <hr class=line>
  <Div  id= "divWait" style= "display: ''">
     <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAll2);">
    		</td>
    		<td class= titleImg>
    			��������Ϣ
    		</td>
    	    </tr>
      </table> 
      <table>      
	    	<tr>
	        	<td class=common>
	  &nbsp;&nbsp;</td><td class=common>    
      <Div  id= "divAll2" style= "display: ''">      
	      <!-- <table>
	    	<tr>
	        	<td class=common>
				    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divQuest);">
	    		</td>
	    		<td class= titleImg>
	    			�������������Ϣ
	    		</td>
	    	    </tr>
	        </table>
	        <div id= "divQuest" style= "display: ''" >	
		      	<table  class= common>
		       		<tr  class= common>
		      	  		<td style="text-align: left" colSpan=1 >
		  					<span id="spanQuestGrid" >
		  					</span> 
		  			  	</td>
		  			</tr>
		    	</table>
		    	<div align=center>
			    	<INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPageQuest.firstPage();">
					<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPageQuest.previousPage();">
					<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPageQuest.nextPage();">
					<INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPageQuest.lastPage();">
				</div>
			</div>
			<table>
	    	<tr>
	        	<td class=common>
				    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec);">
	    		</td>
	    		<td class= titleImg>
	    			��������Լ��Ϣ
	    		</td>
	    	    </tr>
	      </table>
	      <Div  id= "divUWSpec" style= "display: ''">	
	      	<table  class= common>
	       		<tr  class= common>
	      	  		<td style="text-align: left" colSpan=1 >
	  					<span id="spanUWSpecGrid" >
	  					</span> 
	  			  	</td>
	  			</tr>
	    	</table>
	      </Div>   -->
	      <table>
	    	<tr>
	        	<td class=common>
				    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWHealth);">
	    		</td>
	    		<td class= titleImg>
	    			�����������Ϣ
	    		</td>
	    	    </tr>
	      </table>
	      <Div  id= "divUWHealth" style= "display: ''">	
	      	<table  class= common>
	       		<tr  class= common>
	      	  		<td style="text-align: left" colSpan=1 >
	  					<span id="spanUWHealthGrid" >
	  					</span> 
	  			  	</td>
	  			</tr>
	    	</table>
	      </Div>
		   <!--
	      <table>
	    	<tr>
	        	<td class=common>
				    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWExist);">
	    		</td>
	    		<td class= titleImg>
	    			������������Ϣ
	    		</td>
	    	    </tr>
	      </table>
	      <Div  id= "divUWExist" style= "display: ''">	
	      	<table  class= common>
	       		<tr  class= common>
	      	  		<td style="text-align: left" colSpan=1 >
	  					<span id="spanUWExistGrid" >
	  					</span> 
	  			  	</td>
	  			</tr>
	    	</table>
	      </Div>-->
	      <!-- <table>
	    	<tr>
	        	<td class=common>
				    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWAddFee);">
	    		</td>
	    		<td class= titleImg>
	    			�����ͼӷ���Ϣ
	    		</td>
	    	    </tr>
	      </table>	
	      <Div  id= "divUWAddFee" style= "display: ''">	
	      	<table  class= common>
	       		<tr  class= common>
	      	  		<td style="text-align: left" colSpan=1 >
	  					<span id="spanUWAddFeeGrid" >
	  					</span> 
	  			  	</td>
	  			</tr>
	    	</table>
	     </Div>
	    <table>
	    	<tr>
	        	<td class=common>
				    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWPlan);">
	    		</td>
	    		<td class= titleImg>
	    			�����ͳб��ƻ������Ϣ
	    		</td>
	    	    </tr>
	      </table>
	      <Div  id= "divUWPlan" style= "display: ''">	
	      	<table  class= common>
	       		<tr  class= common>
	      	  		<td style="text-align: left" colSpan=1 >
	  					<span id="spanUWPlanGrid" >
	  					</span> 
	  			  	</td>
	  			</tr>
	    	</table>
	      </Div> --> 
	 </Div>
	                </td>
	    	    </tr>
	      </table>
  </Div>
  <p>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
    <input type= "button" name= "sure" id=sure class= cssButton  value="����֪ͨ��" onClick="submitForm()">
  </P>
    <div id= "divresult" style= "display: 'none'" >	
  <table class= common align=center width="121%" height="37%">
    <TR  class= common > 
      <TD width="100%" height="13%"  class= title> ��ѯ��� </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="result" id=result cols="135" rows="10" class="common" readonly = true></textarea></TD>
    </TR>
  </table>
</div>
  
  <p> 
    <!--��ȡ��Ϣ-->
    <input type= "hidden" name= "CaseNo" value= <%= tCaseNo%>>
    <input type= "hidden" name= "MissionID" value= "">
    <input type= "hidden" name= "SubMissionID" value= "">

    <input type= "hidden" name= "ContNo" value= "">
    <input type= "hidden" name= "EdorNo" value= "">
    <input type= "hidden" name= "EdorType" value= "">
    <input type= "hidden" name= "hiddenNoticeType" value= "UWSENDALL">
  </p>
</form>
  

</body>
</html>