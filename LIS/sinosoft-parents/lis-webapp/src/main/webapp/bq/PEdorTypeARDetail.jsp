<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.acc.*"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>

<% 
//�������ƣ�PEdorTypeARDetail.jsp
//�����ܣ������˻����
//�������ڣ�2007-05-23 16:49:22
//������  ��ck
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
	String PolNo=request.getParameter("PolNo");
	String EdorType=request.getParameter("EdorType");
	String ContNo=request.getParameter("ContNo");
	String strEdorNo = request.getParameter("EdorNo");
	System.out.println("���찡:::"+strEdorNo+";;"+EdorType);
%>
<html> 
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT> 

  <SCRIPT src="./PEdorTypeARDetail.js"></SCRIPT>
  <%@include file="PEdorTypeARDetailInit.jsp"%>
  
 <html> 

  
  <%

String strNowDate = PubFun.getCurrentDate();
//ͳ�Ʊ�ȫ����

	 
	 //����������
 		LPEdorItemSchema mainLPEdorItemSchema = new LPEdorItemSchema();
 	LCPolSchema mainLCPolSchema = new LCPolSchema();
 	LPEdorItemDB mainLPEdorItemDB = new LPEdorItemDB();
 	LCPolDB mainLCPolDB = new LCPolDB();
 	mainLPEdorItemDB.setEdorNo(strEdorNo);
 	mainLPEdorItemSchema = mainLPEdorItemDB.query().get(1);
 	String SQL0 = "select * from lcpol where contno = '"+mainLPEdorItemSchema.getContNo()+"' and riskcode in (select riskcode from lmriskapp where risktype3 = '3')";
 	System.out.println(SQL0);
 	
 	mainLCPolSchema =mainLCPolDB.executeQuery(SQL0).get(1);
 	TLbqForFee tTLbqForFee =new TLbqForFee();
 	double douCalFee = -1;
 	String CalFee= "";
 	String strEdorItemAppDate = "";
 	
 	try {
 			tTLbqForFee.GetStartDate(mainLCPolSchema.getCValiDate(),mainLPEdorItemSchema.getEdorValiDate());
// 			strEdorItemAppDate = tTLbqForFee.GetEdorItemAppDate(mainLCPolSchema.getCValiDate(),mainLPEdorItemSchema.getEdorValiDate());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	
 	
 			String ChangeCount = "12";
	 String SQL = "select count(*) from lpedoritem where ContNo = '"+ContNo+"' "
			+" and edortype = 'AR' and EdorState = '0' and EdorValiDate >='"+strEdorItemAppDate+"' and EdorValiDate<='"+mainLPEdorItemSchema.getEdorValiDate()+"'";	 
	 ExeSQL ex = new ExeSQL();
	 ChangeCount = ex.getOneValue(SQL);
	 ChangeCount = String.valueOf((Integer.parseInt(ChangeCount)+1));
 	
 	
 	
 	
 	
 	
		String strMessage ="";
		if(mainLCPolSchema.getRiskCode().equals("RPUL"))//�����޸�
		{
		  try {
			douCalFee = tTLbqForFee.GetCalFee(-1000,strEdorNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(douCalFee == 0)
		{
			CalFee = "������ȡ��������";
		}
		else
		{
			CalFee = "������ȡ������Ϊ"+String.valueOf(douCalFee)+"Ԫ";
		}
			strMessage = strMessage+"���Ǳ�������ȵĵ�"+ChangeCount+"����ȡ��"+CalFee;
		}
		else//���ɲ�Ʒ
		{
			String MessHead = "";
    			String MessMid = "";
    			if(Integer.parseInt(ChangeCount)>1)
        			{
        				MessHead = "����������й���ȡ�����Ǳ�������ȵĵ�"+ChangeCount+"����ȡ��";
        			}
        			else
        			{
        				MessHead = "���������û��������ȡ�����Ǳ�������ȵĵ�1����ȡ��";
        				}
    			try {
        			douCalFee = tTLbqForFee.GetCalFee(1000,strEdorNo);
		} catch (Exception e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
    			
    			if(douCalFee==0)
    			{
    				MessMid = "������ȡ��������";
    			}
    			else
    			{
    				double bili = douCalFee/10;
    				MessMid = "������ȡ�������Ѱ�����ȡ��λ������ȡ�������һ���˻������յļ�ֵ��"+bili+"%��ȡ";
        			
    			}
    			strMessage = MessHead+MessMid;
		}

%>
  
  
  
  <title>�����˻����</title> 
</head>

<body  onload="initForm();" >
  <form action="./PEdorTypeARDetailSubmit.jsp" method=post name=fm id=fm target="fraSubmit">    
    <div class=maxbox1>
    <table class=common>
      <TR  class= common> 
        <TD  class= title > ������</TD>
        <TD  class= input > 
          <input class="readonly wid" readonly name=EdorNo id=EdorNo >
        </TD>
        <TD class = title > �������� </TD>
        <TD class = input >
        <Input class=codeno  value=<%=EdorType%> readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true>
        </TD>
        <TD class = title > �������ֺ��� </TD>
        <TD class = input >
        	<input class="readonly wid" value=<%=PolNo%> readonly name=PolNo id=PolNo>
        </TD>   
      </TR>
      <TR class=common>
    	<TD class =title>��������</TD>
    	<TD class = input>    		
    		<Input class= "coolDatePicker" readonly name=EdorItemAppDate onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
    	</TD>
    	<TD class =title>��Ч����</TD>
    	<TD class = input>
    		<Input class= "coolDatePicker" readonly name=EdorValiDate onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
    	</TD>
    	<TD class = title></TD>
    	<TD class = input></TD>
    </TR>
    </TABLE> 
    </div>


<table  class= common>    	
    		<tr  class= common>
    		 <TD  class= title>
            <font color=red><%=strMessage%></font>
          </TD>		
        </TR>	
    </table>



    <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGrpInsuAcc);">
        </td>
        <td class= titleImg>
          �˻���Ϣ
        </td>
      </tr>
    </table>

    <Div  id= "divLCGrpInsuAcc" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanLCGrpInsuAccGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<Div  id= "divPage1" align=center style= "display: none ">
        <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage1.firstPage();"> 
        <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage1.previousPage();"> 					
        <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage1.nextPage();"> 
        <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage1.lastPage();"> 			
      </Div>		
  	</div>
  <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPInsuAcc);">
        </td>
        <td class= titleImg>
          ��ʷ������Ϣ
        </td>
      </tr>      
    </table>

	<Div  id= "divLPInsuAcc" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanLPInsuAccGrid" >
  					</span> 
  			</td>
  			</tr>
    	</table>
    	<Div  id= "divPage3" align=center style= "display: none ">
        <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage3.firstPage();"> 
        <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage3.previousPage();"> 					
        <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage3.nextPage();"> 
        <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage3.lastPage();">					
      </Div>
  	</div>
	<br>  
	<Div  id= "userinputbutton" style= "display: '' ">
	  <Input type=Button value="������ȡ" class = cssButton onclick="GEdorRDDetail()">
	  <Input type=Button value="���׳���" class = cssButton onclick="cancelGEdor()">  
	</div>  
	<br>
	<br>
	<Input type=Button value="��  ��" class = cssButton onclick="returnParent()">
    <input type=hidden id="EdorAcceptNo" name="EdorAcceptNo">
    <input type=hidden id="CustomerNo" name="CustomerNo">
     <input type=hidden id="ContNo" name="ContNo" value=<%=ContNo%>>
    <input type=hidden id="ContNoBak" name="ContNoBak">
    <input type=hidden id="CustomerNoBak" name="CustomerNoBak">
    <input type=hidden id="ContType" name="ContType">
    <input type=hidden id="Transact" name="Transact">
    <input type=hidden id="EdorTypeCal" name="EdorTypeCal">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br /><br /><br /><br />
</body>
</html>

<script>
  window.focus();
</script>
