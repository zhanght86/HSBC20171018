<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<%
//==========BGN
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI"); 
    String tClmNo = request.getParameter("ClmNo");           //�ⰸ��
    String tCustNo = request.getParameter("CustNo");         //�ͻ���
    String tConType = request.getParameter("ConType");       //��������1���գ�2����
//    loggerDebug("LLClaimContDealInput",tCustNo);
//==========END	
%>      
    <title>��ͬ����</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <script src="./LLClaimContDeal.js"></script>     
    <%@include file="LLClaimContDealInit.jsp"%>

</head>

<body  onload="initForm();">
<form name=fm id=fm target=fraSubmit method=post>
         
<Div  id= "divMain" style= "display:''">
                                          
    <!--=========================================================================
        �ⰸδ�漰�ĺ�ͬ
        =========================================================================
    -->      
    <table>
         <tr><td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,ContNoRefer);"></td>
              <td class= titleImg> �ⰸδ�漰�ĺ�ͬ </td>
         </tr>
    </table>
                
    <Div  id= "ContNoRefer" style= "display:''">
        <Table  class= common>
            <tr><td text-align: left colSpan=1>
                <span id="spanContNoReferGrid"></span>
            </td></tr>
        </Table>
    </Div>
   
    
    <!--=========================================================================
        �ⰸ�漰�ĺ�ͬ
        =========================================================================
    -->    
    <!--<INPUT class=cssButton name="saveContAutoClickButton" VALUE="��ͬ״̬�Զ�����"  TYPE=button onclick="saveContAutoClick()" >--><br> 
     <a href="javascript:void(0);" name="saveContAutoClickButton" class="button" onClick="saveContAutoClick();">��ͬ״̬�Զ�����</a>
    <table>
         <tr><td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,ContRefer);"></td>
              <td class= titleImg> �ⰸ�漰�ĺ�ͬ </td>
         </tr>
    </table>                   
    <Div  id= "ContRefer" style= "display:''">
        <Table  class= common>
            <tr><td text-align: left colSpan=1>
                <span id="spanContReferGrid"></span>
            </td></tr>
        </Table>
    </Div>        
        
    <!--=========================================================================
        �Ժ�ͬ���в���
        =========================================================================
    -->         
    <Div  id= "divContDeal" style= "display:none">
                       
        <table  class= common>
            <tr class= common>          
                <TD  class= title>��ֹ����</TD>                       
                <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=StateReason id=StateReason  verify="��ͬ��ֹԸ��|code:llcontdealtype" ondblclick="return showCodeList('llcontdealtype',[this,StateReasonName],[0,1]);" onclick="return showCodeList('llcontdealtype',[this,StateReasonName],[0,1]);" onkeyup="return showCodeListKey('llcontdealtype',[this,StateReasonName],[0,1]);"><input class=codename name=StateReasonName id=StateReasonName readonly=true></TD>                
                <!--<td><INPUT class=cssButton name="saveClinicButton" VALUE="��ͬ���۱��沢����"  TYPE=button onclick="saveAndCalClick()" ></td>-->
                <TD  class= title></TD>
                <TD  class= input></TD>
                <TD  class= title></TD>
                <TD  class= input></TD>
            </tr>
        </table>                                       
    </div>
  <br>               
 <a href="javascript:void(0);" class="button" name="saveClinicButton" onClick="saveAndCalClick();">��ͬ���۱��沢����</a>

    <!--=========================================================================
        �ⰸ�漰�ĺ�ͬ����
        =========================================================================
    -->     
                   
    <Div  id= "divContPolRefer" style= "display:''"><!-- modify ��ʾ ȥnone wyc-->
	    <table>
	         <tr><td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divContPolRefer);"></td>
	              <td class= titleImg> �ⰸ�漰������ </td>
	         </tr>
	    </table>    
        <Table  class= common>
            <tr><td text-align: left colSpan=1>
                <span id="spanContPolReferGrid"></span>
            </td></tr>
        </Table>
    </Div>
    
    <!--=========================================================================
        �Ժ�ͬ���ֽ��в���
        =========================================================================
    -->         
    <Div  id= "divContPolDeal" style= "display:none">
                       
        <table  class= common>
            <tr class= common>          
                <TD  class= title>��ֹ����</TD>                       
                <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=PolStateReason id=PolStateReason  verify="������ֹ|code:llcontpoldealtype" ondblclick="return showCodeList('llcontpoldealtype',[this,PolStateReasonName],[0,1]);" onclick="return showCodeList('llcontpoldealtype',[this,PolStateReasonName],[0,1]);" onkeyup="return showCodeListKey('llcontdealtype',[this,PolStateReasonName],[0,1]);"><input class=codename name=PolStateReasonName id=PolStateReasonName readonly=true></TD>                
               <!-- <td><INPUT class=cssButton name="saveClinicButton" VALUE="���ֽ��۱��沢����"  TYPE=button onclick="saveContPolClick()" ></td> -->      
               <TD  class= title></TD>
                <TD  class= input></TD>
                <TD  class= title></TD>
                <TD  class= input></TD>         
            </tr>
        </table>                                       
    </div>
<br> 
    <a href="javascript:void(0);" class="button" name="saveClinicButton" onClick="saveContPolClick();">���ֽ��۱��沢����</a>
         
    <!--=========================================================================
        ��ͬ��ֹ��Ϣ
        =========================================================================
    -->         
    
    <Div  id= "divLLContState" style= "display:''"> <!-- modify ��ʾ ȥnone wyc-->  
        <table>
            <tr><td class=common>
                <td class= titleImg> ��ͬ��ֹ��Ϣ </td>
            </tr>
        </table>                        
        <Table  class= common>
            <tr><td text-align: left colSpan=1>
                <span id="spanLLContStateGrid"></span>
            </td></tr>
        </Table>
        
        <!--<Table  class= common>
            <TR  class= common>            
                <td><INPUT class=cssButton name="saveClinicButton" VALUE="�����ͬ��ֹ��ʼʱ��"  TYPE=button onclick="saveLLContStateAdjustDate()" ></td>                
            </TR>
        </Table>-->
         
    </div><br> 
    <a href="javascript:void(0);" name="saveClinicButton" class="button" onClick="saveLLContStateAdjustDate();">�����ͬ��ֹ��ʼʱ��</a>
    <!--=========================================================================
        ��ͬ��ֹ����Ľ��
        =========================================================================
    -->         
    
    <Div  id= "divContCalResult" style= "display:''"> <!-- modify ��ʾ ȥnone wyc-->  
        <table>
            <tr><td class=common>
                <td class= titleImg> �����ͬ������ </td>
            </tr>
        </table>                        
        <Table  class= common>
            <tr><td text-align: left colSpan=1>
                <span id="spanContCalResultGrid"></span>
            </td></tr>
        </Table>
    </div>
    
    
    <!--=========================================================================
        ��ͬ��ֹ����Ľ�����
        =========================================================================
    -->         
    
    <Div  id= "divContCalResultAdjust" style= "display:none">   
        <table>
            <tr><td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
                <td class= titleImg> ��ͬ��������� </td>
            </tr>
        </table>  
        <Div  id= "divPayPlan1" style= "display: ''" class="maxbox">                      
        <Table  class= common>
	        <TR  class= common>
	        			<td class=title>����</td>
    					<td class=input><Input class="wid" class=common name=Currency id=Currency readonly=true></td>
                        <TD  class= title> ����ǰ��� </TD>
                        <TD  class= input> <Input class= "readonly wid" readonly  name=OldPay id=OldPay ></TD>
                        <td class=title></td>
                        <TD  class= input></TD>
                
    		</TR>
            <!--<TR  class= common>
                <TD  class= title> ����ǰ��� </TD>
                <TD  class= input> <Input class= "readonly" readonly  name=OldPay ></TD>
            </TR>-->
            <TR  class= common>            
                <TD  class= title> �������� </TD>
                <TD  class= input> <Input class="wid" class= common  name=NewPay id=NewPay ></TD>
                <td colspan="2"><INPUT class=cssButton name="saveClinicButton" VALUE="���������������"  TYPE=button onclick="saveContCalResultGridAdjust()" >
                <!--<a href="javascript:void(0);" name="saveClinicButton" class="button" onClick="saveContCalResultGridAdjust();">���������������</a>-->
                </td>                
            </TR>
         </Table>
         <Table  class= common>
            <TR  class= common>
                <TD  class= title> �µ���ԭ�� </TD>
            </tr>                        
            <TR  class= common>                        
                <TD colspan="6" style="padding-left:16px">
                    <textarea name="NewAdjRemark" id="NewAdjRemark" cols="224" rows="4" witdh=25% class="common"></textarea>
                </TD>                                  
            </TR>          
            <TR  class= common>
                <TD  class= title> ��ʷ����ԭ�� </TD>
            </tr>
            <TR  class= common>                        
                <TD colspan="6" style="padding-left:16px">
                    <textarea disabled name="OldAdjRemark" id="OldAdjRemark" cols="224" rows="4" witdh=25% class="common"></textarea>
                </TD>                        
            </TR>                     
        </Table>
    </div></Div>
    <!--<table>
        <tr>
            <td><Input class=cssButton name="goBack" value="��  ��" type=button onclick="top.close();"></td>
        </tr>
    </table> --> <br>
    <a href="javascript:void(0);" name="goBack" class="button" onClick="top.close();">��    ��</a>
         
    
	<input type=hidden name=ClmNo value=<%=tClmNo%>>      <!--�ⰸ��-->
	<input type=hidden name=CustNo value=<%=tCustNo%>>    <!--�ͻ���-->
	<input type=hidden name=ConType value=<%=tConType%>>  <!--��������1���գ�2����-->
    <input type=hidden name=ContNo value="">              <!--��ͬ��-->
    <input type=hidden name=PolNo value="">               <!--���ֺ�-->    
    <input type=hidden name=ContStaDate value="">         <!--��ͬ��ֹ��ʼ����-->    
    <input type=hidden name=ContEndDate value="">         <!--��ͬ��ֹ��������-->

    <input type=hidden name=FeeOperationType value="">    <!--ҵ������-->    
    <input type=hidden name=SubFeeOperationType value=""> <!--��ҵ������-->                        
    
     
    <!--ϵͳ����-->
    <input type=hidden name=hideOperate value=''>         <!--�����Ĳ�������-->

    <!--
    <textarea name="memo" cols="100%" rows="10" witdh=50% class="common"></textarea>
    -->
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>  <br><br><br><br>        
</Form>
</body>
</html>
