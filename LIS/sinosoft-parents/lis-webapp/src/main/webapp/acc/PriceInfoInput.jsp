<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  loggerDebug("PriceInfoInput","werererererrrerwer");
%>
<head >
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  
  	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="PriceInfoInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="PriceInfoInputInit.jsp"%>

</head>
<body  onload="initForm();" onclick="myCheckFiled();" >
  <form action="PriceInfoIputSave.jsp" method=post name=fm id="fm" target="fraSubmit">	
<Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divQuery);">
    		</TD>
    		<TD class= titleImg>
    			 �������ѯ����
    		</TD>
    	</TR>
  </Table>
   <Div  id= "divQuery" style= "display: ''" >
      <div class="maxbox1" >
    <table  class= common >
      	<TR  class= common>
          <TD  class= title5>
          Ͷ���ʻ�����
          </TD>
          <TD  class= input5>
            <input class=codeno name=QureyInsuAccNo  id=QureyInsuAccNo 
            style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
            onclick="return showCodeList('findinsuacc',[this,QueryName],[0,1],null,[3],['risktype3'],1,'200');" 
            onDblClick="return showCodeList('findinsuacc',[this,QueryName],[0,1],null,[3],['risktype3'],1,'200');"
             onKeyUp="return showCodeListKey('findinsuacc',[this,QueryName],[0,1],null,[3],['risktype3'],1,'200');"><input class=codename name=QueryName readonly=true elementtype=nacessary>
          </TD>
          <TD  class= title5>
          �۸�����
          </TD>
          <TD  class= input5>
               <Input class="coolDatePicker" dateFormat="short"   name=QueryStartDate id="QueryStartDate" onClick="laydate({elem:'#QueryStartDate'});"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>         
          </TD>
       </TR> 
       <TR  class= common>
       	<td class="title5">��¼״̬</td>
         <td class="input5"><Input class=codeno name=QueryState id=QueryState readonly CodeData='0|^1|¼��^0|��Ч' value=5 
         style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
          onclick="return showCodeListEx('QueryState',[this,QueryStateName],[0,1]);"
           onDblClick="return showCodeListEx('QueryState',[this,QueryStateName],[0,1]);" 
           onKeyUp="return showCodeListKeyEx('QueryState',[this,QueryStateName],[0,1]);"><input class=codename name=QueryStateName readonly=true elementtype=nacessary><font></font></td>
        </TR> 
 </Table>
 <!--<Table>
  	<TR>   
       <td width="10%">&nbsp;&nbsp;</td>        
       	<TD> <INPUT VALUE="��  ѯ" class= cssButton TYPE=button onClick="easyQueryClick();"> </TD>           
       </TR>
    </Table>-->
    <a href="javascript:void(0);" class="button"onClick="easyQueryClick();">��   ѯ</a>
    </div>
    </div>
  	<Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCollectivityGrid);">
    		</TD>
    		<TD class= titleImg>
    			 �۸���Ϣ
    		</TD>
    	</TR>
  </Table> 
 <Div  id= "divCollectivityGrid" style= "display: ''" >
   <Table  class= common>
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanCollectivityGrid" ></span> 
  	</TD>
      </TR>
    </Table>					
      <!--<INPUT VALUE="��  ҳ" class=cssButton TYPE=button onClick="turnPage.firstPage();"> 
	 	<INPUT VALUE="��һҳ" class=cssButton TYPE=button onClick="turnPage.previousPage();"> 					
		<INPUT VALUE="��һҳ" class=cssButton TYPE=button onClick="turnPage.nextPage();"> 
		<INPUT VALUE="β  ҳ" class=cssButton TYPE=button onClick="turnPage.lastPage();"> 	-->	
 </Div>		
<Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPrice);">
    		</TD>
    		<TD class= titleImg>
    			 ������Ƽ���Ϣ
    		</TD>
    	</TR>
  </Table>
    <div  id= "divPrice" style= "display: ''">
     <div class="maxbox1" >
      <table  class=common>
          <tr class=common>
          <td class="title5">Ͷ���ʻ�����</td>
         <td class="input5"><input class=codeno name=InsuAccNo 
         style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
         onclick="return showCodeList('findinsuacc',[this,InsuAccNoName],[0,1],null,[3],['risktype3'],1,'500');" 
         onDblClick="return showCodeList('findinsuacc',[this,InsuAccNoName],[0,1],null,[3],['risktype3'],1,'500');" 
         onKeyUp="return showCodeListKey('findinsuacc',[this,InsuAccNoName],[0,1],null,[3],['risktype3'],1,'500');" ><input class=codename name=InsuAccNoName readonly=true elementtype=nacessary ><font size=1 color='#ff0000'><b>*</b></font></td>
       <td class="title5">�ʲ���������</td>
          <td class="input5"><input class="multiDatePicker laydate-icon" dateFormat="short"  name=StartDate id="StartDate" onClick="laydate({elem:'#StartDate'});"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>  <font size=1 color='#ff0000'><b>*</b></font></td>
          
       </tr>

      <tr class=common >

          <td class="title5">�˻����ʲ�(��λ��Ԫ)</td>
          <td class="input5"><Input class="common wid" name=InsuTotalMoney ><font size=1 color='#ff0000'><b>*</b></font></td>
          <td class="title5">��ծ(��λ��Ԫ)</td>
          <td class="input5"><Input class="common wid" name=Liabilities ><font size=1 color='#ff0000'><b>*</b></font></td>
      </tr>
      <tr class=common >
      <td class="title5">��˾Ͷ�ʵ�λ��</td>
      <td class="input5"><Input class="common wid" name=CompanyUnitCount readonly></td>
      <td class="title5">���α䶯��λ��</td>
      <td class="input5"><Input class="common wid" name=ComChgUnitCount ><font size=1 color='#ff0000'><b>*</b></font></td>
			</tr>	
        <tr class=common >
        	<td class="title5">�ͻ�Ͷ�ʵ�λ��</td>
      		<td class="input5"><Input class="common wid" name=CustomersUnitCount readonly></td>
					<td class="title5">�������ű�־</td>
         <td class="input5"><Input class=codeno name=SKFlag readonly CodeData='0|^1|������^0|������'
         style="background:url(../common/images/select--bg_03.png) no-repeat right center"  
         onclick="return showCodeListEx('SKFlag',[this,SKFlagName],[0,1]);" 
         onDblClick="return showCodeListEx('SKFlag',[this,SKFlagName],[0,1]);" 
         onKeyUp="return showCodeListKeyEx('SKFlag',[this,SKFlagName],[0,1]);" onBlur="QueryCompanyUnitCount();" ><input class=codename name=SKFlagName readonly=true elementtype=nacessary><font size=1 color='#ff0000'><b>*</b></font></td>

       </tr>
       <td class="title5">��¼״̬</td>
          <td class="input5"><Input class="common wid" name=StateName readonly></td>
        <tr class=common >        	

       </tr>        
      </table> 
  <!-- <table>
      <tr>
        <td >&nbsp;&nbsp;</td>
        <td><input type="button" class=cssButton value="&nbsp;����&nbsp;" onClick="submitFrom()"></td>
        <td><input type="button" class=cssButton value="&nbsp;�޸�&nbsp;" onClick="updateClick()"></td>
        <td><input type="button" class=cssButton value="&nbsp;ɾ��&nbsp;" onClick="deleteClick()"></td>
        <td><input type="button" class=cssButton value="&nbsp;����&nbsp;" onClick="resetForm()"></td>
      </tr>
 </table>-->
 <br>
 <a href="javascript:void(0);" class="button"onClick="submitFrom()">��   ��</a>
<a href="javascript:void(0);" class="button"onClick="updateClick()">��   ��</a>
 <a href="javascript:void(0);" class="button"onClick="deleteClick()">ɾ  ��</a>
<a href="javascript:void(0);" class="button" onClick="resetForm()">��   ��</a>

        <input type=hidden name=Transact >
        <input type=hidden name=State >
        </div>
        </div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>
