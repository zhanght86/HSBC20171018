<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
//�������ƣ�UWManuHealth.jsp
//�����ܣ��б��˹��˱��������¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
//tongmeng 2008-10-10 add
//��ʼ������
//LLUWAutoHealthQueryBL tLLUWAutoHealthQueryBL = new LLUWAutoHealthQueryBL();
//tLLUWAutoHealthQueryBL.submitData(new VData(),"INIT");
String busiName="LLUWAutoHealthQueryBL";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(tBusinessDelegate.submitData(new VData(),"INIT",busiName))
	loggerDebug("LLUWManuHealth","��ʼ���ɹ�...");
VData mInitData = new VData();
//mInitData = tLLUWAutoHealthQueryBL.getResult();
mInitData = tBusinessDelegate.getResult();
SSRS tSSRS_Main = (SSRS)mInitData.get(0);
SSRS tSSRS_Sub = (SSRS)mInitData.get(1);

loggerDebug("LLUWManuHealth","@@tSSRS_Main:"+tSSRS_Main.getMaxRow()+":"+tSSRS_Sub.getMaxRow());
%>
<html>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="./LLUWManuHealth.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <%@include file="LLUWManuHealthInit.jsp"%>
  <title> ����Լ�������¼�� </title>

  <script language="JavaScript">

  </script>

</head>
<body  onload="initForm('<%=tContNo%>','<%=tMissionID%>','<%=tSubMissionID%>','<%=tPrtNo%>','<%=tFlag%>','<%=tBatchNo %>','<%=tRgtNo %>');" >
  <form method=post name=fm id="fm" target="fraSubmit" action= "./LLUWManuHealthChk.jsp">
    <!-- ���б� -->
    <DIV id=DivLCContButton STYLE="display:''">
    <table >
        <tr>
        <td class="common">
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,BasicInfo);">
			</td>
        <td class= titleImg>��������Ϣ</td>
    </tr>
    </table>
  </div>
  <div id="BasicInfo" class="maxbox1">
    <table  class= common align=center>
        <TR  class= common >
          <TD  class= title>  Ͷ������  </TD>
          <TD  class= input> <Input class="readonly wid" name=ContNo id="ContNo" > </TD>
           <INPUT  type= "hidden" class= Common id="MissionID" name= MissionID value= ""><!-- ������������� -->
           <INPUT  type= "hidden" class= Common id="SubMissionID" name= SubMissionID value= "">
           <INPUT  type= "hidden" class= Common id="PrtNo" name= PrtNo value= "">
          <TD  class= title>  ��ӡ״̬ </TD>
          <TD  class= input>  <Input class="readonly wid" id="PrintFlag" name=PrintFlag > </TD>
           <TD  class= title>  �����  </TD>

          <TD  class= input> <Input class=code name=InsureNo id="InsureNo" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" ondblClick="showCodeListEx('InsureNo',[this,this,'',CustomerType],[0,1,2,3],null,null,null,1);" 
          onkeyup="showCodeListKeyEx('InsureNo',[this,this,'',CustomerType],[0,1,2,3],null,null,null,1);"></TD>
        </TR>
        <TR  class= common>
          <TD  class= title> �������   </TD>
          <TD  class= input>  <Input class="coolDatePicker" dateFormat="short"  name=EDate verify="�������|date"id="EDate" onClick="laydate({elem:'#EDate'});"><span class="icon"><a onClick="laydate({elem: '#EDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>  </TD>

           <input type=hidden id="Hospital" name="Hospital">
          <TD  class= title>  �Ƿ�ո� </TD>
          <TD  class= input>   <Input class=code name=IfEmpty id="IfEmpty" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" onClick="return showCodeList('YesNo',[this]);" onDblClick="return showCodeList('YesNo',[this]);" onKeyUp="return showCodeListKey('YesNo',[this]);">  </TD>
          <TD  class= title>
              ���ԭ��
          </TD>
          <TD  class= input>
              <Input class="codeno" name=PEReason id="PEReason" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" onClick="return showCodeList('pereason',[this,PEReasonName],[0,1]);" onDblClick="return showCodeList('pereason',[this,PEReasonName],[0,1]);" onKeyUp="return showCodeListKey('pereason',[this,PEReasonName],[0,1]);"><input class="codename" name="PEReasonName" id="PEReasonName" readonly>
          </TD>
        </TR>
      </table>
  </div>
<DIV id=DivLCContButton STYLE="display:''">
	<table id="table1">
			<tr>
				<td class="common">
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCPE);">
				</td>
				<td class="titleImg">�ѱ��������Ϣ
				</td>
			</tr>
	</table>
	</DIV>
<Div id="DivLCPE" style="display: ''" align=center>
        <table class=common>
            <tr class=common>
                <td text-align: left colSpan=1>
  					<span id="spanPEGrid">
  					</span>
                </td>
            </tr>
        </table>
        <INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onClick="getFirstPage();">
        <INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onClick="getPreviousPage();">
        <INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onClick="getNextPage();">
        <INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onClick="getLastPage();">
</div>
<DIV id=DivLCContButton STYLE="display:''">
      <table  >
        <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
        </td>
        <td class= titleImg>�������ѡ��</td>
    	</tr>
        </table>
 </DIV>
 <div id="HealthType" class="maxbox">
 	<table class= common align=center>
    	<%
    	//ֱ��д��������
    	  for(int i=1;i<=tSSRS_Main.getMaxRow();i++)
    	  {
    		  String tMainHealthCode = tSSRS_Main.GetText(i,1);
    		  String tMainHealthName = tSSRS_Main.GetText(i,2);
    		  String tSubHealthCode = tSSRS_Main.GetText(i,3);
    		 // loggerDebug("LLUWManuHealth","tSubHealthCode:"+tSubHealthCode);
    		  %>
    		 <tr class= common>
          		<td class= common>
          		   <input type=radio name=HealthCode id="<%=tMainHealthCode%>" value="<%=tMainHealthCode%>" OnClick= "showBodyCheck('<%=tSubHealthCode%>');"><%=tMainHealthName%></td>
    		 </tr>
    		  <%
    	  }
    	%>
  </table>
</div>
       <table class= common border=0 width=100%>
        <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
        </td>
        <td class= titleImg align= center>�����Ŀѡ��</td>
        </tr>
       </table>

       <div id = "bodyCheckType1" class="maxbox" style= "display: ''">
          <table  class= common>
             <% 
                //��ʼ���������Ŀ����
                for(int i=1;i<=tSSRS_Sub.getMaxRow();i++)
                {
                	String tSubCode = tSSRS_Sub.GetText(i,1);
                	String tSubName = tSSRS_Sub.GetText(i,2);
                	if(i==1)
                	{
                		
                		%>
                		  <tr class=common>
                		<% 
                	}
                	%>
                		<td class= common width=25%> <input type=checkbox name='<%=tSubCode%>' id='<%=tSubCode%>' value='<%=tSubCode%>' ><%=tSubName %>
                		</td>
                	<%
                	//ÿ4�������д���.
                	if(i%3==0)
                	{
                		%>
                		</tr>
                		<tr class=common>
                		<%
                	}
                	if(i==tSSRS_Sub.getMaxRow())
                	{
                		%>
                		</tr>
                		<%

                	}
                }
             %>
         </table>   
       </div>

       <div id = "otheritem" style= "display: ''">
       <table class=title>
         <TR  class= common>
           <TD  class= title> ���������Ŀ </TD>
         </TR>
         <TR  class= common>
           <TD  class= title>
             <textarea name="otheritem2" cols="120" rows="3" class="common" value=""></textarea>
           </TD>
         </TR>
      </table>
      </div>

       <p>
       <table class=title>
         <TR  class= common>
           <TD  class= title> �˱�Ա˵�� </TD>
         </TR>
         <TR  class= common>
           <TD  class= title>
             <textarea name="Note" cols="120" rows="3" class="common" >
             </textarea>
           </TD>
         </TR>
      </table>

      <input type="button" class="cssButton" value=" ȷ �� " onClick="addData()">
      <input type="button" class="cssButton" value=" �� �� " onClick="updateData()">
      <input type="button" class="cssButton" value=" ɾ �� " onClick="deleteData()">
      <input type="button" class="cssButton" value=" �� �� " onClick="returnParent()">

        <!-- XinYQ added on 2006-07-19 : ��ȫ��������Լ��� : BGN -->
        <input type="hidden" name="EdorNo">
        <!-- XinYQ added on 2006-07-19 : ��ȫ��������Լ��� : END -->

      <input type=hidden id="Flag" name="Flag">
      <input type=hidden id="Action" name="Action">
      <input type=hidden id="PrtSeq" name="PrtSeq">
      <input class=input type=hidden id="CheckedItem" name="CheckedItem" >
      <input type=hidden id="CustomerType" name="CustomerType" >
      <input type=hidden id="BatchNo" name="BatchNo" >
      <input type=hidden id="RgtNo" name="RgtNo" >
    <!--��ȡ��Ϣ-->
  </form>
  <br><br><br><br><br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
