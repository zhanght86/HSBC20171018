<%
//��������:���ⱨ���ӡ(��ʼֵ��
//������:��ģ����嵥���͵ı����ӡ  
//�������ڣ�2003-8-27
//������  ��guoxiang
//���¼�¼��  ������    ��������     ����ԭ��/����
//            guoxiang    2003-10-15   �б�ͳ�Ƶı���Ҳ�ɹ��ø��ļ���
//                                     ���ļ���claimcalculate������
//                                     ָ���ⱨ������������ӡ���� 
//���У�claim ָ����  uw ָ�б�
// * <p>claim3�������⸶״��</p>
// * <p>claim12�������˹�����ͳ��</p>
// * <p>claim13�������½᰸�嵥��������ˣ�</p>
// * <p>claim14��������δ�᰸�嵥��������ˣ�</p>
// * <p>claim2���������⸶��</p>
// * <p>claim8����ʱ��������״��</p>
// * <p>claim9�����ձ�������״��</p>
// * <p>claim5�����ս᰸��</p>
// * <p>claim6������������������</p>
// * <p>claim10���˾�����״����������ˣ�</p>
// * <p>claim11��������������±���������ˣ�</p>
// * <p>uw1����������ͳ�Ʊ���</p>
// * <p>uw2: ������ͳ�Ʊ���</p>
// * <p>uw3: �ܱ����ڼ�ͳ�Ʊ���</p>
// * <p>uw4: ְҵ�ֲ�ͳ�Ʊ���</p>
// * <p>uw5: �߱�����ֲ�״��ͳ�Ʊ���</p>
// * <p>uw6: �߱������ϸ�嵥</p>
// * <p>uw7: �б�����Ч��ͳ�Ʊ�</p>
// * <p>uw8: �µ�ͳ�Ʊ�</p>
// * <p>uw9: �����ͳ�Ʊ�</p>
// * <p>uw10: �˱�ʦ������ͳ�Ʊ�</p>
%>

<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.db.*" %>
<%@page import="com.sinosoft.lis.vdb.*" %>
<%@page import="com.sinosoft.lis.schema.*" %>
<%@page import="com.sinosoft.lis.vschema.*" %>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>  
<%
    GlobalInput tG1 = (GlobalInput)session.getValue("GI");
    String Branch =tG1.ComCode;
    String Code = request.getParameter("Code");
    String CodeName ="";
    System.out.println("url�Ĳ���Ϊ:"+Code);
    String CurrentDate = PubFun.getCurrentDate();
    String mDay[]=PubFun.calFLDate(CurrentDate);
    System.out.println(mDay[0]+"--"+mDay[1]);
    LDMenuDB tLDMenuDB = new LDMenuDB();
    String name="../f1print/claimcalculate.jsp?Code="+Code;
    tLDMenuDB.setRunScript(name);
    LDMenuSet mLDMenuSet=tLDMenuDB.query();
    for (int i=1;i<=mLDMenuSet.size();i++){
      LDMenuSchema tLDMenuSchema = mLDMenuSet.get(i);
       CodeName=tLDMenuSchema.getNodeName();
       System.out.println("CodeName:"+CodeName);
    }  
      
%>

<html> 
<head>
 <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <script language="javascript">
   function submitForm(){
      var code="<%=Code%>";
      
      if(code=="claim1"||code=="claim2"||code=="claim3"||code=="claim4"
         ||code=="claim5"||code=="claim6"||code=="claim7"
         ||code=="claim8"||code=="claim9"||code=="claim12"
         ||code=="claim13"||code=="claim14"){
	  
	     fm.target = "f1jprint";
	     fm.action="./ClaimReportF1Print.jsp";
      }
      if(code=="claim10"||code=="claim11"||code=="claim15"){
		 if(fm.ManageCom.value.length==""){
             alert("�������ѡ�񣡣�����");
    	   	 return false;
    	 }
	     /*
		 if (fm.StationCode.value.length ==0){
			 alert("ͳ������ûѡ�񣡣�����");
		     return false;
	     }
	     */
	     if ((fm.StartTime.value.length ==0)||(fm.EndTime.value.length ==0)){
	         alert("ʱ��û��ѡ�񣡣�����");
	         return false;
	     }
    	  
	     fm.target = "f1jprint";
	     fm.action="./ClaimStatisticF1Print.jsp";
      }
      if(code=="uw1"||code=="uw2"||code=="uw3"||code=="uw4"||code=="uw5"||code=="uw6"||code=="uw7"||code=="uw8"||code=="uw9"||code=="uw10"||code=="uw11"||code=="uw12"){
          fm.target = "f1jprint";
          fm.action="./UWF1Print.jsp";
      }
      fm.submit();
   }  
   function changeMode(objCheck){

	if(objCheck.checked == true) 
	   fm.RiskCode.disabled = true;
    else 
	   fm.RiskCode.disabled = false;
   }
  
</script>
</head>
<body>
<form method=post name=fm id=fm>
<div class="maxbox">
   <Table class= common>
     <TR class= common> 
          <TD  class= title5>
            ��������
          </TD>  
          <TD class=input5>
             <input class="wid" readonly class=common name=CodeName id=CodeName value=<%=CodeName%>>
          </TD>
          <TD class= input style="display: none">
            <Input class=common name=Code value = <%=Code%> >
          </TD>
           <TD  class= title5>
            ���˹���
          </TD>  
          <TD class=input5>
		<input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" type="text" class="code" name=UserCode id=UserCode onDblClick="showCodeList('uwpopedomcode',[this],null,null,codeSql1,'1',null,250);" onclick="showCodeList('uwpopedomcode',[this],null,null,codeSql1,'1',null,250);"  onKeyUp="return showCodeListKey('uwpopedomcode',[this],null,null,codeSql1,'1',null,250);">		
          </TD>        
      </TR>
<%if(Code.equals("uw12")){
%>
<TR class= common> 
           
     </TR>
<%}%>
<%     
if(Code.equals("claim10")||Code.equals("claim11")||Code.equals("claim15")){  
%>      
     <TR class = common disabled>    
          <TD class=title5>
            ͳ������
          </TD>          
          <TD class=input5>    
              <input class="wid" type="text" class="common" name=StationCode id=StationCode value="86" disabled>
          </TD>
           <TD class= title5>
            ��ʼʱ��
          </TD>          
          <TD class=input5> 
            
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartTime'});" verify="��ʼʱ��|NOTNULL" dateFormat="short" name=StartTime value="<%=mDay[0]%>" id="StartTime"><span class="icon"><a onClick="laydate({elem: '#StartTime'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
      </TR>
      <TR class =common>    
         
          <TD class =title5> 
            ����ʱ��
          </TD>
          <TD class=input5>  
            
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndTime'});" verify="����ʱ��|NOTNULL" dateFormat="short" name=EndTime value="<%= mDay[1]%>" id="EndTime"><span class="icon"><a onClick="laydate({elem: '#EndTime'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
           <TD class=title5>�������</TD>
				<TD class=input5>
					<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ManageCom id=ManageCom verify="�������|code:comcode&notnull" onclick="�������|code:comcode&notnull" ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'#1#','1');" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,'#1#','1');"><input class=codename name=ManageComName id=ManageComName readonly=true elementtype=nacessary>
				</TD>  
      </TR>
<%
}else{
     if(Code.equals("uw1")||Code.equals("uw2")||Code.equals("uw3")||Code.equals("uw4")||
        Code.equals("uw5")||Code.equals("uw6")||Code.equals("uw8")||Code.equals("uw9")||Code.equals("uw10")||Code.equals("uw11")||Code.equals("uw12")){
%>    
     <TR class = common>    
          
          <TD  class= title5>
            ���ֱ���
          </TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name=RiskCode id=RiskCode ondblclick="showCodeList('RiskInd',[this]);" onclick="showCodeList('RiskInd',[this]);" onkeyup="return showCodeListKey('RiskInd',[this]);">
          </TD>
           <TD class= title5>
            ��ʼʱ��
          </TD>          
          <TD class=input5> 
            
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartTime'});" verify="��ʼʱ��|NOTNULL" dateFormat="short" name=StartTime id="StartTime"><span class="icon"><a onClick="laydate({elem: '#StartTime'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
      </TR>
      <TR class =common>    
         
          <TD class =title5> 
            ����ʱ��
          </TD>
          <TD class=input5>  
            
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndTime'});" verify="����ʱ��|NOTNULL" dateFormat="short" name=EndTime id="EndTime"><span class="icon"><a onClick="laydate({elem: '#EndTime'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
           <TD  class= title5>��������</TD>
	          <TD  class= input5>
	          <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name="state" id="state" 
	            CodeData="0|^35|����^16|����^21|�н�^51|��ͥ��^11|����^61|������"
	            ondblClick="showCodeListEx('ModeSelect_0',[this],[0,1]);"
	            onkeyup="showCodeListKeyEx('ModeSelect_0',[this],[0,1]);">
	          </TD> 
      </TR>
      <TR class=common>
       
	   <TD  class= title5>
            ��������
          </TD>
			<td class="input5" >
				<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name="SaleChnl" id="SaleChnl" verify="��������|notnull&CODE:salechnl"  verifyorder="1"  ondblclick="showCodeList('salechnl',[this,SaleChnlName],[0,1],null,'1 and code <> #01#',1,null,'150');" onclick="showCodeList('salechnl',[this,SaleChnlName],[0,1],null,'1 and code <> #01#',1,null,'150');" onkeyup="showCodeListKey('salechnl',[this,SaleChnlName],[0,1],null,'1 and code <> #01#',1,null,'150');" ><input class="codename" name="SaleChnlName" id="SaleChnlName" readonly>
			</td>
            <TD class= title5>
            ͳ��ȫ������
          </TD>  
          <TD class=input5>
            <Input type="checkbox" name=RiskFlag onclick="changeMode(this)">��
          </TD>  
      </TR>


<%
    }else{
         if(Code.equals("uw7")){    
            GlobalInput tG = new GlobalInput();
            tG = (GlobalInput)session.getValue("GI"); 
%>
      <TR class = common>    
          <TD class=title>
            ͳ������
          </TD>          
          <TD class=common>    
              <input type="text" readonly class="common" name=StationCode value="<%=tG.ManageCom%>" >
          </TD>       
          <Input class="code" name=RiskCode style="display: none" value="000000">
      </TR>
      
      
      
<%
         }else{    
%>     
     <TR class = common>    
          <TD class=title>
            ͳ������
          </TD>          
          <TD class=input>    
              <input type="text" class="code" name=StationCode onDblClick="showCodeList('station',[this],null,null,codeSql,'1',null,250);"  onKeyUp="return showCodeListKey('station',[this],null,null,codeSql,'1',null,250);">
          </TD>       
      </TR>
<%     
      }
%>      
     <TR class =common>    
          <TD class= title>
            ��ʼʱ��
          </TD>          
          <TD class=input> 
            <Input class="coolDatePicker" dateFormat="short" verify="��ʼʱ��|NOTNULL" name=StartTime> 
          </TD>
          <TD class =title> 
           ����ʱ��
          </TD>
          <TD class=input>  
            <Input class="coolDatePicker" dateFormat="short" verify="����ʱ��|NOTNULL" name=EndTime>
          </TD>
      </TR>
<%        
   }
}
%>    
    </Table> </div> 

  <!--<INPUT class=cssButton  VALUE="�� ӡ �� ��" TYPE=button onclick="submitForm()"> -->  
  <!--<a href="javascript:void(0);" class="button" onClick="submitForm();">��ӡ����</a>-->
  <a href="javascript:void(0);" class="button" onClick="submitForm();">��ӡ����</a>
 </form>
<span id="spanCode"  style="display: none; position:absolute; slategray"> </span> 
</body>
</html>
<script>
var codeSql = "1  and code like #"+<%=Branch%>+"%#";
var codeSql1="1 and  comcode=#86# and (userstate=#0# or userstate is null) and EdorPopedom is null and uwpopedom>#A# ";
</script>