
<%
//�������ƣ�RnewManuUWInput.jsp
//�����ܣ������˹��˱�
//�������ڣ�2005-05-04 16:20:22
//������  ��zhangtao
//���¼�¼��  ������    ��������      ����ԭ��/����
//            liurx     2005-06-17      ����һ�¡���ť���ܿ���
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>

<%
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");

//=====�ӹ������˹��˱�ҳ�洫�ݹ����Ĳ���=====BGN===================================

    String tPrtNo = request.getParameter("PrtNo");  
    String tContNo = request.getParameter("ContNo");          //����ID
    loggerDebug("RnewManuUWInput","tContNo:"+tContNo);

//=====�ӹ������˹��˱�ҳ�洫�ݹ����Ĳ���=====END===================================
%>

<script>
	var operator = "<%=tG.Operator%>";   //��¼����Ա
	var manageCom = "<%=tG.ManageCom%>"; //��¼�������
	var comcode = "<%=tG.ComCode%>"; //��¼��½����
</script>

<html>
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="./RnewManuUW.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<%@include file="RnewManuUWInit.jsp"%>

</head>
<body  onload="initForm();" >
  <form  action="./RnewManuUWSave.jsp" method=post name=fm id="fm" target="fraSubmit"> 

    <Div  id= "divEdorMainInfo" style= "display:  ">
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorMainGrid);">
                </td>
                <td class= titleImg> ���������б���Ϣ </td>
            </tr>
        </table>
        <Div id="divEdorMainGrid" style="display: ">
            <table  class=common>
                <tr  class=common>
                    <td><span id="spanEdorMainGrid"></span></td>
                </tr>
            </table>
            <div id="divTurnPageEdorMainGrid" align="center" style="display:none;">
<!--                <input type="button" class="cssButton90" value="��  ҳ" onClick="turnPageEdorMainGrid.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onClick="turnPageEdorMainGrid.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onClick="turnPageEdorMainGrid.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onClick="turnPageEdorMainGrid.lastPage()">
-->            </div>
        </div>

    </Div>
    <!-- ��ʾ��ȫ�������ϸ��Ϣ -->

      <div id=DivDetailInfo style="display: ">
          <!--
           <DIV id=DivLPContButton STYLE="display: ">
              <table id="table1">
               <tr>
                <td>
                <img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLPCont);">
                </td>
                <td class="titleImg">��ͬ��Ϣ
                </td>
               </tr>
             </table>
           </DIV>
          <div id="DivLPCont" STYLE="display: ">
            <table class="common" id="table2">
              <tr CLASS="common">
                <td CLASS="title" nowrap>��������
                </td>
                <td CLASS="input" COLSPAN="1">
                <input NAME="ContNo" CLASS="readonly wid" readonly TABINDEX="-1" MAXLENGTH="14">
                </td>
                <td CLASS="title" nowrap>�������
                </td>
                <td CLASS="input" COLSPAN="1">
                <input NAME="lpManageCom"  MAXLENGTH="10" CLASS="readonly wid" readonly>
                </td>
                <td CLASS="title" nowrap>��������
                </td>
                <td CLASS="input" COLSPAN="1">
                <input NAME="SaleChnl" CLASS="readonly wid" readonly MAXLENGTH="2">
                </td>
              </tr>
              <tr CLASS="common">
                <td CLASS="title">�����˱���
                </td>
                <td CLASS="input" COLSPAN="1">
                <input NAME="AgentCode" MAXLENGTH="10" CLASS="readonly wid" readonly>
                </td>
                <td CLASS="title">��������
                </td>
                <td CLASS="input" COLSPAN="1">
                    <input NAME="Remark" CLASS="readonly wid" readonly MAXLENGTH="255">
                </td>
                <td CLASS="title">
                </td>
                <td CLASS="input" COLSPAN="1">
                </td>
              </tr>
           </table>
         </div>
        -->         
        <!--
         <table  >
           <tr>
            <td nowrap>
                <INPUT VALUE="��ȫӰ���ѯ" class=cssButton  TYPE=button onclick="ScanDetail()">
                <INPUT VALUE="��ȫ�˱�Ӱ��" class=cssButton  TYPE=button onclick="UWScanQuery()">
                <INPUT VALUE="�Զ��˱���Ϣ" class=cssButton TYPE=button onclick="showNewUWSub()">
                <INPUT VALUE=" �˱���ѯ " class=cssButton TYPE=button onclick="UWQuery()">
                <INPUT VALUE="��ȫ��ϸ��ѯ" class=cssButton TYPE=button onclick="EdorDetailQuery()">
                <INPUT VALUE="Ͷ����Ӱ���ѯ" class=cssButton  TYPE=button onclick="ScanQuery()">
                <INPUT VALUE="������Ϣ��ѯ" class=cssButton  TYPE=button onclick="ContInfoQuery()">
                <INPUT VALUE="��ȫ�����켣" class=cssButton TYPE=button onclick="MissionQuery()">
                <INPUT VALUE="Ͷ������������ѯ" class=cssButton TYPE=button onclick="QueryRecord()">
           -->
            </td>
           </tr>
         </table>
						<DIV id=DivLCAutoUWButton STYLE="display: ">
						<!-- �Զ��˱���ʾ��Ϣ���� -->
						<table>
						<tr>
						<td class="common">
						<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAutoUW);">
						</td>
						<td class= titleImg>
						�Զ��˱���ʾ��Ϣ
						</td>
						</tr>
						</table>
						</DIV>
						
						<DIV id=DivLCAutoUW STYLE="display: ">
							<table  class= common>
					        	<tr  class= common>
					    	  		<td style=" text-align: left" colSpan=1 >
										<span id="spanUWErrGrid" >
										</span> 
									</td>
								</tr>
							</table>
                        <center>
					     <INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onClick="turnPage2.firstPage();">
							<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onClick="turnPage2.previousPage();">
							<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onClick="turnPage2.nextPage();">
							<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onClick="turnPage2.lastPage();">
                        </center>
						</div>
						  <p>
						  	  <INPUT VALUE="�������" class= cssButton TYPE=button name='AutoUWButton' onClick="submitFormUW();"> 					
					    </P>

        <DIV id=DivLPAppntIndButton STYLE="display: ">
           <!-- Ͷ������Ϣ���� -->
           <table>
             <tr>
                  <td class="common">
                     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLPAppntInd);">
                  </td>
                  <td class= titleImg>
                       Ͷ������Ϣ
                  </td>
             </tr>
           </table>
        </DIV>
        <DIV id=DivLPAppntInd class="maxbox1" STYLE="display: ">
          <table  class= common>
            <TR  class= common>
              <TD  class= title>
                ����
              </TD>
              <TD  class= input>
                <Input CLASS="readonly wid" readonly name="AppntName" id="AppntName" value="">
              </TD>
              <TD  class= title>
                �Ա�
              </TD>
              <TD  class= input>
                <Input CLASS="readonly wid" readonly type=hidden name="AppntSex" id="AppntSex" >
                <Input CLASS="readonly wid" readonly name="AppntSexName" id="AppntSexName" >
                
              </TD>
              <TD  class= title>
                ����
              </TD>
              <TD  class= input>
              <input CLASS="readonly wid" readonly name="AppntBirthday" id="AppntBirthday">
              </TD>
            </TR>
 
            <tr  class= common>
	            <Input CLASS="readonly wid" readonly type=hidden name="AppntNo" id="AppntNo" value="">
	            <Input CLASS="readonly wid" readonly  type=hidden name="AddressNo" id="AddressNo">
	          <td  class= title>
	            ְҵ
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="OccupationCode" id="OccupationCode" type="hidden">
	          <input CLASS="readonly wid" readonly name="OccupationCodeName" id="OccupationCodeName">
	          </TD>
	          <td  class= title>
	            ְҵ���
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="OccupationType" id="OccupationType" type="hidden">
	          <input CLASS="readonly wid" readonly name="OccupationTypeName" id="OccupationTypeName">
	          </TD>
	         	<td  class= title>
           	 ������
          	</TD>
          	<td  class= input>
            	<Input CLASS="readonly wid" readonly name=income id="income" >
          	</TD>
	        </TR>
	        
          <tr>
	        	<td  class= title>
           	 ���
          	</TD>
          	<td  class= input>
            	<Input CLASS="readonly wid" readonly name=Stature id="Stature" > 
          	</TD>
	        	<td  class= title>
           	 ����
          	</TD>
          	<td  class= input>
            	<Input CLASS="readonly wid" readonly name=Weight id="Weight" >
          	</TD>
          	<td  class= title>
           	 BMIֵ
          	</TD>
          	<td  class= input>
            	<Input CLASS="readonly wid" readonly name=BMI id="BMI" >
          	</TD>
	        </TR>
	        <tr>
	        	<td  class= title>
	            ����
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="NativePlace" id="NativePlace" type="hidden">
	          <input CLASS="readonly wid" readonly name="NativePlaceName" id="NativePlaceName">
	          </TD>
	          <td  class= title>
	            �ۼ����ձ���
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="AppntSumLifeAmnt" id="AppntSumLifeAmnt" >
	          </TD>
	        	<td  class= title>
           	 �ۼ��ش󼲲�����
          	</TD>
          	<td  class= input>
            	<Input CLASS="readonly wid" readonly name=AppntSumHealthAmnt id="AppntSumHealthAmnt" >
          	</TD>	        	
	        </TR>
	        
	        <tr>        	
	         <td  class="title" nowrap>
            	�ۼ�ҽ���ձ���
           </TD>
         	 <td  class= input>
            	<Input CLASS="readonly wid" readonly name=AppntMedicalAmnt  id="AppntMedicalAmnt">
           </TD>        
           <td  class= title>
	            �ۼ������ձ���
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="AppntAccidentAmnt" id="AppntAccidentAmnt" >
	          </TD>
	        	<td  class= title>
           	 �ۼƷ��ձ���
          	</TD>
          	<td  class= input>
            	<Input CLASS="readonly wid" readonly name=AppntSumAmnt id="AppntSumAmnt" >
          	</TD>
	      	</tr>	
          </table>
        </DIV>

        <table>
           <tr>
             <td nowrap>
               <INPUT VALUE=" Ͷ���˽�����֪��ѯ " class=cssButton TYPE=button onClick="queryHealthImpart();">
               <INPUT VALUE=" Ͷ���˼���Ͷ�����ϲ�ѯ " class=cssButton TYPE=button onClick="showApp(1);">
               <!--
               <INPUT VALUE=" Ͷ����������ϲ�ѯ " class=cssButton TYPE=button onclick="showBeforeHealthQ();">
               <INPUT VALUE=" Ͷ���˱����ۼ���Ϣ " class=cssButton TYPE=button onclick="amntAccumulate();">
               <INPUT VALUE=" Ͷ����Ӱ�����ϲ�ѯ " class=cssButton TYPE=button onclick=""> 
               -->
              </td>
           </tr>
           <!--
           <tr>
               <td nowrap>
               <INPUT VALUE="Ͷ�����ѳб�������ѯ" class=cssButton TYPE=button onclick="queryProposal();">
               <INPUT VALUE="Ͷ����δ�б�������ѯ" class=cssButton TYPE=button onclick="queryNotProposal();">
               <INPUT VALUE=" Ͷ���˼�����ȫ��ѯ " class=cssButton TYPE=button onclick="queryEdor();">
              <INPUT VALUE=" Ͷ���˼��������ѯ " class=cssButton TYPE=button onclick="queryClaim();">
               </td>
          </tr>
          -->
          <tr>
               <td>
               </td>
          </tr>
        </table>

        <Div  id= "divLPPolButton" style= "display:  " >
               <table>
                 <tr>
                    <td class=common>
                       <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPPol);">
                    </td>
                    <td class= titleImg>
                        ��������Ϣ
                    </td>
                 </tr>
                 </table>
        </div>
        <div id="divLPPol"  style="display: ">
                <table class=common>
                   <tr class=common>
                     <td><span id="spanPolAddGrid"></span></td>
                   </tr>
                </table>
         </div>
          <jsp:include page="RnewUWInsuredShow.jsp"/>

         <table>
           <tr>
             <td>
               <input value="���֪ͨ¼��" class=cssButton type=button onClick="showHealth();" width="200">
               <input value="��������¼��" class=cssButton type=button onClick="showRReport();">
               <INPUT VALUE="  �����¼��  " class=cssButton TYPE=button onClick="QuestInput();" name="uwButtonQU">
               <input VALUE="�˱���������"  class=cssButton name="ReportButton" TYPE=button onClick="showUWReport();">
               <input value=" �� �� �� " class=cssButton type=button onClick="showNotePad();" width="200">
               <!--input value="�ӷѳб�¼��" class=cssButton type=button name= "AddFee"  onclick="showAdd();">
               <input value="��Լ�б�¼��" class=cssButton type=button onclick="showSpec();"-->        
             </td>
           </tr>
           <tr>
             <td >
               <input value=" ��ѯ����� "  class="cssButton" type="button" name=uwButton21 onClick="queryHealthReportResult();">
               <input value=" ��ѯ������� "  class="cssButton" type="button" name=uwButton22 onClick="queryRReportResult();">
               <input value="  �������ѯ  " class=cssButton type=button onClick="QuestQuery();">
               <!--INPUT VALUE=" Ͷ������Ϣ��ѯ " class=cssButton TYPE=button name="uwButton1" onclick="showPolDetail();"-->
               <INPUT VALUE="������Ϣ��ѯ" class=cssButton  TYPE=button onClick="ContInfoQuery()">
               <INPUT VALUE="  Ӱ�����ϲ�ѯ  " class=cssButton TYPE=button name="uwButton2" onClick="ScanQuery();">
               <INPUT VALUE="����������ѯ" class=cssButton TYPE=button name="uwButton6" onClick="QueryRecord()">                           
             </td>           
           </tr>
           <tr>
             <td>
             	 <INPUT VALUE="�Զ��˱���Ϣ��ѯ" class=cssButton TYPE=hidden name="AutoUWDetail" onClick="alert('�ù���δ�ṩ(QueryAutoUWDetail())');"> 
               <input value="���ҽԺƷ�ʹ���"   class=cssButton type=button name="uwButtonHospital" onClick="HospitalQuality();" >
  		         <input value="ҵ��ԱƷ�ʹ���"   class=cssButton type=button name="uwButton26" onClick="AgentQuality();" >
  		         <input value=" �ͻ�Ʒ�ʹ��� "   class=cssButton type=button name="uwButton25" onClick="CustomerQuality();" >
  		         <input value="  ��������  " class=cssButton type=button name="wButtonQuestErr" onClick="IssueMistake();">
  		         <input value=" �˱�֪ͨ�鷢��״̬��ѯ " class="cssButton" type="button" name="uwButton23" onClick="SendAllNotice_MS()">
               <!--input value="ҵ��ԱƷ�ʹ���" class=cssButton type=button onclick="AgentQuality();" -->
             </td>
           </tr>
          </table>

    </div>
         <Div  id= "divFormerContUWInfo" style= "display: none">
               <table>
                <tr>
                    <td class="common">
                        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFormerContUW);">
                    </td>
                    <td class= titleImg> ԭ�����˱����� </td>
                </tr>
               </table>
           <Div  id= "divFormerContUW" class="maxbox1" style= "display:  ">
             <table  class= common>
                <tr class=common>
                    <td class=title> ԭ�����˱����� </td>
                    <td class=input><Input class="codeno" name=FormerUWState id="FormerUWState" readonly=true><input class=codename name=FormerUWStateName id="FormerUWStateName" readonly=true></td>
                    <td class=title></td>
                    <td class=input></td>
                    <td class=title></td>
                    <td class=input><Input class="common wid" type=hidden name=FormerPopedomCode id="FormerPopedomCode"></td>
                </tr>
                <tr class=common>
                    <TD class=title colspan=6 > ԭ�����˱���� </TD>
                </tr>
                <tr class=common>
                    <TD  class=title colspan=6 ><textarea name="FormerUWIdea" readonly cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
                </tr>
              </table>
            </DIV>
         </DIV>
         <Div  id= "divRnewUWResultInfo" style= "display:  ">
          <Div  id= "divContUWLable" style= "display:  ">
               <table>
                <tr>
                    <td class="common">
                        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divRnewUWInfo);">
                    </td>
                    <td class= titleImg> �����˱����� </td>
                </tr>
               </table>
           </Div>
           <Div  id= "divAppUWLable" style= "display: none">
               <table>
                <tr>
                    <td class= common>
                        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divRnewUWInfo);">
                    </td>
                    <td class= titleImg> ��ȫ����˱����� </td>
                </tr>
               </table>
           </DIV>
           <Div  id= "divRnewUWInfo" class="maxbox1" style= "display:  ">
             <table  class= common>
                <tr class=common>
                    <td class=title5> �˱����� </td>
                    <td class=input5><Input class="codeno" name=EdorUWState id="EdorUWState" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="initUWState(this, edoruwstateName)" 
                    	ondblclick="initUWState(this, edoruwstateName)" 
                    	onkeyup="onKeyUpUWState(this, edoruwstateName);">
                    	<input class=codename name=EdorUWStateName id="EdorUWStateName" readonly=true></td>
                    <td class=title5>
                    <div id = "divUwDelayTitle" style= "display: none">
                    �ӳ�ʱ��
                    </div>
                    </td>
                    <td class=input>
                    <div id = "divUwDelay" style= "display: none">
                    <Input class="coolDatePicker" name=UWDelay id="UWDelay" onClick="laydate({elem:'#UWDelay'});"><span class="icon"><a onClick="laydate({elem: '#UWDelay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </div>
                    </td>
                    <!--td class=title> �ϱ��˱� </td-->
                    <td class=input5><Input class="common wid" type=hidden name=UWPopedomCode id="UWPopedomCode"></td>
                </tr>
                <tr class=common>
                    <TD class=title colspan=6 > �˱���� </TD>
                </tr>
                <tr class=common>
                    <TD  class=title colspan=6 ><textarea name="UWIdea" id="UWIdea" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
                </tr>
              </table>
            </DIV>
             <br>
	            <INPUT VALUE=" ȷ �� " class=cssButton TYPE=button onClick="UWSubmit();">
	            <INPUT VALUE=" �� �� " class=cssButton TYPE=button onClick="UWcancel();">
	            <INPUT VALUE=" �º˱����� " class=cssButton TYPE=hidden onClick="window.location.reload();">
             </br>
             <br>
	            <INPUT VALUE=" �˱���� " class=cssButton TYPE=button onClick="FinishSubmit();">
	            <INPUT VALUE=" �� �� " class=cssButton TYPE=button onClick="returnParent();">
            </br>
         </DIV>

    <!-- ������-->
    <input type=hidden id="ContNo"       name= "ContNo">
    <input type=hidden id="ProposalNo"       name= "ProposalNo">
    <input type=hidden id="RiskCode"       name= "RiskCode">
    <input type=hidden id="MissionID"       name= "MissionID">
    <input type=hidden id="SubMissionID"    name= "SubMissionID">
    <input type=hidden id="ActivityStatus"  name= "ActivityStatus">
    <input type=hidden id="UWType"          name= "UWType">
    <input type=hidden id="EdorNo"          name= "EdorNo">
    <input type=hidden id="ActionFlag"      name= "ActionFlag">
    <input type=hidden id="PrtNo"           name= "PrtNo">
    <input type=hidden id="ProposalContNo"  name= "ProposalContNo">
    <input type=hidden id="OtherNoType"     name= "OtherNoType">
    <input type=hidden id="Apptype"         name= "Apptype">
    <input type=hidden id="ManageCom"       name= "ManageCom">
    <input type=hidden id="AppntNamew"      name= "AppntNamew">
    <input type=hidden id="PaytoDate"       name= "PaytoDate">
    <input type=hidden id="EdorType"        name= "EdorType">
    <input type= "hidden" name= "hiddenNoticeType" value= "UWSENDALL">
  </form>
<br><br><br><br><br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

</body>
</html>
