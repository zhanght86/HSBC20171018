<html>
<%
	//�������ƣ�IndiFinFeeUrgeGet.jsp
	//�����ܣ����˼�ʱ���Ѻ��������ѣ������˵����ڴ��ս��Ѻ�����
	//�������ڣ�2002-10-3 
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="IndiFinFeeUrgeGet.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="IndiFinFeeUrgeGetInit.jsp"%>
</head>

<body>
<Form name=fm id="fm" action="./IndiFinVerifyUrgeGet.jsp" method=post
	target=fraSubmit>
<table class=common>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divIndiFin1);"></td>
		<td class=titleImg>�����ڸ��˼�ʱ����</td>
	</tr>
</table>
<div class="maxbox1">
<Div id="divIndiFin1" style="display: ''">
<TABLE class=common>
	<TR class=common>
		<TD class=title5>�ݽ����վݺ���</TD>
		<TD class=input5><Input class="common wid" name=TempFeeNo id="TempFeeNo"><a href="javascript:void(0)" class=button onclick="fmSubmit();">��  ��</a> <!-- <Input
			class=cssButton type=Button value="��  ��" onclick="fmSubmit()"> -->
		</TD>
		<TD class=title5></TD>
		<TD class=input5></TD>
	</TR>
</TABLE>
</Div>
</div>
</Form>
<hr class="line">

<Form name=fm2 id="fm2" action="./IndiFinVerifyUrgeGetByPolNo.jsp" method=post
	target=fraSubmit>
<table class=common>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divIndiFin2);"></td>
		<td class=titleImg>���˼�ʱ����</td>
	</tr>
</table>
<div class="maxbox1">
<Div id="divIndiFin2" style="display: ''">
<TABLE class=common>
	<TR class=common>
		<TD class=title5>��������</TD>
		<TD class=input5><Input class="common wid" name=ContNo id="ContNo"><a href="javascript:void(0)" class=button onclick="fmSubmit2();">��  ��</a>  <!-- <Input
			class=cssButton type=Button value="����" onclick="fmSubmit2()"> -->
		</TD>
		<TD class=title5></TD>
		<TD class=input5></TD>
	</TR>
</TABLE>
</Div>
</div>
</Form>
<hr class='line'>

<Form name=fm3 id="fm3" action="./GrpFinFeeUrgeGetSave.jsp" method=post
	target=fraSubmit>
<table class=common>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divIndiFin3);"></td>
		<td class=titleImg>���弴ʱ����</td>
	</tr>
</table>
<div class="maxbox1">
<Div id="divIndiFin3" style="display: ''">
<TABLE class=common>
	<TR class=common>
		<TD class=title5>�ŵ�����</TD>
		<TD class=input5><Input class="common wid" name=GrpContNo id="GrpContNo"><a href="javascript:void(0)" class=button onclick="fmSubmit3();">��  ��</a> <!-- <Input
			class=cssButton type=Button value="����" onclick="fmSubmit3()"> -->
		</TD>
		<TD class=title5></TD>
		<TD class=input5></TD>
	</TR>
</TABLE>
</Div>
</div>
</Form>
<hr class="line">

<Form name=fmMult id="fmMult" action=./MultFinVerifyUrgeGet.jsp target=fraSubmit
	method=post>
<table class=common>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divMultFinFeeUrge)">
		</td>
		<td class=titleImg>������������(��¼���ݽ���)</td>
	</tr>
</table>
<div class="maxbox1">
<Div id="divMultFinFeeUrge" style="display: ''">
<Table class=common>
	<TR class=common>
		<TD class=title style="width:9%">�ݽ��ѽ������ڷ�Χ(���켰֮ǰ)</TD>
		<TD class=input><a href="javascript:void(0)" class=button onclick="fmMultsubmit();">��  ��</a><!-- <INPUT class=cssButton VALUE="����" TYPE=button
			name=magan onclick="fmMultsubmit();"> --></TD>
		<!-- <TD class=title5></TD>
		<TD class=input5></TD> -->
	</TR>
</Table>
</Div>
</div>
</Form>

<Form name=fmMultAuto id="fmMultAuto" action=./MultFinVerifyUrgeGetAuto.jsp
	target=fraSubmit method=post><!-- ��ʾ��������Ϣ >      	
    <table class= common>
      <tr>
      <td>
        <IMG  src= "../common/images/butCollapse.gif" style= "cursor:hand;" OnClick= "showPage(this,divMultFinFeeUrge);">
      </td>
      <td class= titleImg>�����������(���ֽ����ڱ���,����¼���ݽ���)</td>
     </tr>
    </table>
    <Div  id= "divMultFinFeeUrge" style= "display: ''">
      <Table  class= common>
        <TR  class= common>
          <TD  class= title>
            Ӧ�ձ�ɷ����ڷ�Χ(������2���º�)
          </TD>
        <TD class= input>
          <INPUT class= cssButton VALUE="����" TYPE=button onclick= "fmMultsubmitAuto();" >           
        </TD>          
        </TR> 
        <!-- 
        <TR class= common>
         <TD  class= title>
            ��ʼ���� 
          </TD>
          <TD  class= input>
            <input class="coolDatePicker" dateFormat="short" name="StartDate" >           
          </TD>
        </TR> 
        <TR class= common>
         <TD  class= title>
          ��ֹ���� 
          </TD>        
          <TD  class= input>
            <input class="coolDatePicker" dateFormat="short" name="EndDate" >                                 
          </TD>
        </TR> 
        
      </Table>    
    </Div>  --></Form>
<br>
<br>
<br>
<br>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
