<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%> 

<%
	String tMissionID = "";
	String tSubMissionID = "";
	String tClmNo = "";
	String tBatNo = "";
	String tContNo = "";
	tContNo = request.getParameter("ContNo");
	
	tMissionID = request.getParameter("MissionID");
	tSubMissionID =  request.getParameter("SubMissionID");
	tClmNo =  request.getParameter("ClmNo");
	tBatNo =  request.getParameter("BatNo");
	loggerDebug("LLSendAllNotice","tContNo===================  "+tContNo);
%>
<SCRIPT>
	var tMissionID = '<%=tMissionID%>';
	var tSubMissionID = '<%=tSubMissionID%>';
</SCRIPT>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="LLSendAllNotice.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>�˱������¼��</title>
  <%@include file="LLSendAllNoticeInit.jsp"%>
</head>
<body  onload="initForm('<%=tContNo%>','<%=tMissionID%>','<%=tSubMissionID%>','<%=tClmNo%>','<%=tBatNo%>');" >
  <form method=post name=fm id=fm target="fraSubmit" action= "./LLSendAllNoticeChk.jsp">
  	
  		<!--<table>
		<tr>
			<td><input class="mulinetitle" value="�˱�֪ͨ����Ϣ"  name="InsuredSequencename" readonly ></td>
		</tr>
	</table>-->
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
    <br>
    <div id= "divnoticecontent" style= "display: none" >	
  <table class= common>
    <TR  class= common > 
      <TD class= title> ֪ͨ������ </TD>
    </TR>
    <TR  class= common>
      <TD colspan="6" style="padding-left:16px"><textarea name="Content" id="Content" cols="200" rows="4" class="common"></textarea></TD>
    </TR>
  </table>
</div><br>
<a href="javascript:void(0);" class="button" onClick="InsuredSequencename();">�˱�֪ͨ����Ϣ</a>
    <Div  id= "divUWSpec1" style= "display: none">
    <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divQuestionType);"></td>
    		<td class= titleImg>	 �ʾ�����ѡ��</td>                            
    	</tr>	
    </table>
  </div>
    <Div  id= "divQuestionType" style= "display: none">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
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
	      <table>
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
	      	  		<td text-align: left colSpan=1 >
	  					<span id="spanQuestDGrid" >
	  					</span> 
	  			  	</td>
	  			</tr>
	    	</table>
	    	<div align=center>
			    	<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPageQuestD.firstPage();">
					<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPageQuestD.previousPage();">
					<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPageQuestD.nextPage();">
					<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPageQuestD.lastPage();">
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
	      	  		<td text-align: left colSpan=1 >
	  					<span id="spanUWSpecDGrid" >
	  					</span> 
	  			  	</td>
	  			</tr>
	    	</table>
	      </Div>  
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
	      	  		<td text-align: left colSpan=1 >
	  					<span id="spanUWHealthDGrid" >
	  					</span> 
	  			  	</td>
	  			</tr>
	    	</table>
	      </Div>
	      <!-- <table>
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
	      	  		<td text-align: left colSpan=1 >
	  					<span id="spanUWExistDGrid" >
	  					</span> 
	  			  	</td>
	  			</tr>
	    	</table>
	      </Div> -->	      
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
	      	  		<td text-align: left colSpan=1>
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
	      <table>
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
		      	  		<td text-align: left colSpan=1 >
		  					<span id="spanQuestGrid" >
		  					</span> 
		  			  	</td>
		  			</tr>
		    	</table>
		    	<div align=center>
			    	<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPageQuest.firstPage();">
					<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPageQuest.previousPage();">
					<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPageQuest.nextPage();">
					<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPageQuest.lastPage();">
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
	      	  		<td text-align: left colSpan=1 >
	  					<span id="spanUWSpecGrid" >
	  					</span> 
	  			  	</td>
	  			</tr>
	    	</table>
	      </Div>   
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
	      	  		<td text-align: left colSpan=1 >
	  					<span id="spanUWHealthGrid" >
	  					</span> 
	  			  	</td>
	  			</tr>
	    	</table>
	      </Div>
	      <!-- <table>
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
	      	  		<td text-align: left colSpan=1 >
	  					<span id="spanUWExistGrid" >
	  					</span> 
	  			  	</td>
	  			</tr>
	    	</table>
	      </Div> -->
	      <table style= "display:none">
	    	<tr>
	        	<td class=common>
				    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWAddFee);">
	    		</td>
	    		<td class= titleImg>
	    			�����ͼӷ���Ϣ
	    		</td>
	    	    </tr>
	      </table>	
	      <Div  id= "divUWAddFee" style= "display: none">	
	      	<table  class= common>
	       		<tr  class= common>
	      	  		<td text-align: left colSpan=1 >
	  					<span id="spanUWAddFeeGrid" >
	  					</span> 
	  			  	</td>
	  			</tr>
	    	</table>
	     </Div>
	   <!--  <table>
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
	      	  		<td text-align: left colSpan=1 >
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
    <input type= "button" id="sure" name= "sure" class= cssButton  value="����֪ͨ��" onClick="submitForm()">
  </P>
    <div id= "divresult" style= "display: 'none'" >	
  <table class= common>
    <TR  class= common > 
      <TD class= title> ��ѯ��� </TD>
    </TR>
    <TR  class= common>
      <TD colspan="6" style="padding-left:16px"><textarea name="result" id="result" cols="200" rows="4" class="common" readonly = true></textarea></TD>
    </TR>
  </table>
</div>
  
  <p> 
    <!--��ȡ��Ϣ-->
    <input type= "hidden" id="ContNo" name= "ContNo" value= "">
    <input type= "hidden" id="MissionID" name= "MissionID" value= "">
    <input type= "hidden" id="SubMissionID" name= "SubMissionID" value= "">
    <input type= "hidden" id="ClmNo" name= "ClmNo" value= "">
    <input type= "hidden" id="BatNo" name= "BatNo" value= "">
    <input type= "hidden" id="hiddenNoticeType" name= "hiddenNoticeType" value= "UWSENDALL">
  </p>
</form>
  
<br><br><br><br>
</body>
</html>
