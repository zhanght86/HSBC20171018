<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2003-03-26 15:39:06
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
   <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="FinDayCheck.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="FinDayCheckInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form  method=post name=fm id="fm" target="fraSubmit">
    <table>
    	<tr>
    		<td class=common>
    		 <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
    		</td>
    		 <td class= titleImg>
        		����Ա�ս�
       		 </td>
    	</tr>
    </table>
    <div class="maxbox1" >
     <Div  id= "divFCDay" style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class= title5>
            ��ʼʱ��
          </TD>
          <TD  class= input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDay'});" verify="��ʼʱ��|NOTNULL" dateFormat="short" name=StartDay id="StartDay"><span class="icon"><a onClick="laydate({elem: '#StartDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>
            ����ʱ��
          </TD>
          <TD  class= input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDay'});" verify="����ʱ��|NOTNULL" dateFormat="short" name=EndDay id="EndDay"><span class="icon"><a onClick="laydate({elem: '#EndDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR>
        <tr class=common>
          <TD CLASS=title5>
            ������� 
          </TD>
          <TD CLASS=input5>
            <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" NAME=ManageCom id="ManageCom" VALUE="" MAXLENGTH=10 CLASS=code onMouseDown="return showCodeList('comcode',[this],null,null,null,null,1);" onDblClick="return showCodeList('comcode',[this],null,null,null,null,1);" onKeyUp="return showCodeListKey('comcode',[this],null,null,null,null,1);" verify="�������|code:comcode&notnull"  readonly>
          
          </tr>
        </table>
     </Div>
    </div>
      <Div  id= "divOperator" style= "display: ''">
        <table  class= common>
          <TR class= common style="text-align:center;">
		        <TD  class= input>
			         <!-- <input class= cssButton type=Button name="ORiskPrint" id="ORiskPrint" value="    �����ս��ӡ    " onclick="ORPrint()"> -->
               <a href="javascript:void(0)" class=button name="ORiskPrint" id="ORiskPrint" onClick="ORPrint();">&nbsp;&nbsp;&nbsp;�����ս��ӡ&nbsp;&nbsp;&nbsp;</a>
		        </TD>
            <TD  class= input >
                <!-- <input class= cssButton type=Button name="YuShouPrint" id="YuShouPrint" value="     Ԥ���ս��ӡ     " onclick="YSPrint()"> -->
                <a href="javascript:void(0)" class=button name="YuShouPrint" id="YuShouPrint" onClick="YSPrint();">&nbsp;&nbsp;&nbsp;Ԥ���ս��ӡ&nbsp;&nbsp;&nbsp;</a>
            </TD>
            <TD  class= input >
			           <!-- <input class= cssButton type=Button name="OModePrint" id="OModePrint" value="   ���������ս��ӡ  " onclick="HeBao_Print()"> -->
                 <a href="javascript:void(0)" class=button name="OModePrint" id="OModePrint" onClick="HeBao_Print();">&nbsp;&nbsp;���������ս��ӡ&nbsp;&nbsp;</a>
		        </TD>
            <TD  class= input >
               <!-- <input class= cssButton type=Button name="OModePrint" id="OModePrint" value="    ���֧���ս��ӡ  " onclick="PrintPKZC()"> -->
               <a href="javascript:void(0)" class=button name="OModePrint" id="OModePrint" onClick="PrintPKZC();">&nbsp;���֧���ս��ӡ&nbsp;</a>
            </TD>
	        </TR>
	       <tr><td style="height:5px;"></td></tr>
	        <TR class= common style="text-align:center;">
		        <TD  class= input >
			         <!-- <input class= cssButton type=Button name="ORiskPrint" id="ORiskPrint" value="  ��ȫӦ���ս��ӡ  " onclick="PrintBQYF()"> -->
               <a href="javascript:void(0)" class=button name="ORiskPrint" id="ORiskPrint" onClick="PrintBQYF();">&nbsp;��ȫӦ���ս��ӡ&nbsp;</a>
		        </TD>
            <TD  class= input >
                <!-- <input class= cssButton type=Button name="YuShouPrint" id="YuShouPrint" value="   ��ȡ�����ս��ӡ   " onclick="PrintLQJF()" > -->
                <a href="javascript:void(0)" class=button name="YuShouPrint" id="YuShouPrint" onClick="PrintLQJF();">&nbsp;��ȡ�����ս��ӡ&nbsp;</a>
            </TD>
            <TD  class= input >
               <!-- <input class= cssButton type=Button name="OModePrint" id="OModePrint" value="   ����Ӧ���ս��ӡ  " onclick="PrintQTYF()"> -->
               <a href="javascript:void(0)" class=button name="OModePrint" id="OModePrint" onClick="PrintQTYF();">&nbsp;&nbsp;����Ӧ���ս��ӡ&nbsp;&nbsp;</a>
            </TD>
  
            <TD class= input >
                <!-- <input class= cssButton type=Button name="HeBaoPrint" id="HeBaoPrint" value="   ҵ��ʵ���ս��ӡ   " onclick="PrintYWSF()"> -->
                <a href="javascript:void(0)" class=button name="HeBaoPrint" id="HeBaoPrint" onClick="PrintYWSF();">&nbsp;ҵ��ʵ���ս��ӡ&nbsp;</a>
            </TD>
          </TR>
          <tr><td style="height:5px;"></td></tr>
          <TR class= common style="text-align:center;">
            <TD  class= input >
      	       <!-- <input class= cssButton type=Button name="ORiskPrint" id="ORiskPrint" value=" ������Ӧ���ս��ӡ " onclick="AirPrint()"> -->
               <a href="javascript:void(0)" class=button name="ORiskPrint" id="ORiskPrint" onClick="AirPrint();">������Ӧ���ս��ӡ</a>
            </TD>
            <TD class= input >
              <!-- <input class= cssButton type=Button name="BtnPayModePrint" id="BtnPayModePrint" value="  ���շѷ�ʽ�ս��ӡ  " onclick="PayModePrint()"> -->
              <a href="javascript:void(0)" class=button name="BtnPayModePrint" id="BtnPayModePrint" onClick="PayModePrint();">���շѷ�ʽ�ս��ӡ</a>
            </TD>
            <TD class= input >
              <!-- <input class= cssButton type=Button name="BtnGetModePrint" id="BtnGetModePrint" value=" ҵ��֧����ʽ�ս��ӡ" onclick="GetModePrint()"> -->
              <a href="javascript:void(0)" class=button name="BtnGetModePrint" id="BtnGetModePrint" onClick="GetModePrint();">ҵ��֧����ʽ�ս��ӡ</a>
            </TD>
            <TD class= input >
              <!-- <input class= cssButton type=Button name="BtnGetModePrint" id="BtnGetModePrint" value="  ����������ս��ӡ  " onclick="PrintGLFY()"> -->
              <a href="javascript:void(0)" class=button name="BtnGetModePrint" id="BtnGetModePrint" onClick="PrintGLFY();">����������ս��ӡ</a>
            </TD>
          </TR> 
        </table>
      </Div>
       <input type=hidden id="fmtransact" name="fmtransact">
       <input type=hidden name="Opt" id="Opt">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
