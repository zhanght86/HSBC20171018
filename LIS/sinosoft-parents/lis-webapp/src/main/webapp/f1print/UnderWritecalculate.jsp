<%
//��������:�б���ع�����ͳ��(��ʼֵ��
//������:�б������������ӡ  
//�������ڣ�2009-1-24
//������  ��liuqh
//���¼�¼��  ������    ��������     ����ԭ��/����
//���У�UnderWirte ָ�б�ģ��  app ָ¼�� uw ָ�˱�
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
// * <p>app1: ��Լ������ͳ�Ʊ���</p>
// * <p>app2: ����ɨ�蹤����ͳ��</p>
// * <p>app3: Ͷ��������δ¼����Ϣ��ѯ</p>

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
    String name="../f1print/UnderWritecalculate.jsp?Code="+Code;
    tLDMenuDB.setRunScript(name);
    LDMenuSet mLDMenuSet=tLDMenuDB.query();
    for (int i=1;i<=mLDMenuSet.size();i++){
      LDMenuSchema tLDMenuSchema = mLDMenuSet.get(i);
       CodeName=tLDMenuSchema.getNodeName();
       System.out.println("CodeName:"+CodeName);
       System.out.println("Code:"+Code);
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
      //if (fm.StationCode.value.length ==0){
	  //     alert("ͳ������ûѡ�񣡣�����");
	  //     return false;
      //}
      if ((fm.StartTime.value.length ==0)||(fm.EndTime.value.length ==0)){
          alert("ʱ��û��ѡ�񣡣�����");
          return false;
      }
      if(code=="claim1"||code=="claim2"||code=="claim3"||code=="claim4"
         ||code=="claim5"||code=="claim6"||code=="claim7"
         ||code=="claim8"||code=="claim9"||code=="claim12"
         ||code=="claim13"||code=="claim14"){
	  
	     fm.target = "f1jprint";
	     fm.action="./ClaimReportF1Print.jsp";
      }
      if(code=="claim10"||code=="claim15"){
	     fm.target = "f1jprint";
	     fm.action="./ClaimStatisticF1Print.jsp";
      }
      if(code=="uw1"||code=="uw2"||code=="uw3"||code=="uw4"||code=="uw5"||code=="uw6"||code=="uw7"||code=="uw8"||code=="uw9"||code=="uw10"||code=="uw11"||code=="uw12"){
          fm.target = "f1jprint";
          fm.action="./UWF1Print.jsp";
      }
      if(code=="app1"||code=="app2"){
      	if ((fm.ManageCom.value.length ==0)){
      	    if(confirm("������ѡ�������������������������Ƿ�ѡ��������")==true){
      	       return false;
      	    }
     	 }
          fm.target = "f1jprint";
          fm.action="./AppF1Print.jsp";
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
             <input readonly class="wid" name=CodeName id=CodeName value=<%=CodeName%>>
          </TD>
          <TD class= input style="display: none">
            <Input class=common class="wid" name=Code value = <%=Code%> >
          </TD>
      </TR>
<%if(Code.equals("uw12")){
%>
<TR class= common> 
          <TD  class= title5>
            ���˹���
          </TD>  
          <TD class=input5>
		<input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" type="text" class="code" name=UserCode id=UserCode onDblClick="showCodeList('uwpopedomcode',[this],null,null,codeSql1,'1',null,250);" onclick= "showCodeList('uwpopedomcode',[this],null,null,codeSql1,'1',null,250);" onKeyUp="return showCodeListKey('uwpopedomcode',[this],null,null,codeSql1,'1',null,250);">		
          </TD>          
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
              <input type="text" class="common wid" name=StationCode id=StationCode value="86" disabled>
          </TD>
      </TR>
      <TR class =common>    
          <TD class= title5>
            ��ʼʱ��
          </TD>          
          <TD class=input5> 
           
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��ʼ����|notnull" dateFormat="short" name=StartDate value="<%=mDay[0]%>" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD class =title5> 
            ����ʱ��
          </TD>
          <TD class=input5>  
            
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="��ֹ����|notnull" dateFormat="short" name=EndDate value="<%= mDay[1]%>" id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
      </TR>
<%
}else{
     if(Code.equals("app1")){
%>    
     <TR class=common>
           <!--TD  class= title>ͳ������</TD>      
                <TD  class= Input> <Input class="codeno" name=StatisticType CodeData="0|^1|ͳ������|^2|��ɨ��|^3|֪ͨ����|^4|ɨ������������|^5|��ִ��|^6|�������������|" ondblclick="showCodeListEx('StatisticType',[this,StatisticTypeName],[0,1]);" onkeyup="showCodeListEx('StatisticType',[this,StatisticTypeName],[0,1]);"><input class=codename name=StatisticTypeName readonly=true  elementtype=nacessary>
          </TD-->
           <TD  class= title5>
            ����Ա����
          </TD>
          <TD  class= input5><Input class='common wid' name=OperaterNo id=OperaterNo></TD>
           <TD  class= title5>�������</TD>      
                <TD  class= Input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ManageCom id=ManageCom verify="�������|code:station" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName id=ManageComName readonly=true  elementtype=nacessary>
          </TD>
     </TR>


<%
    }else{
         if(Code.equals("app2")){    
            GlobalInput tG = new GlobalInput();
            tG = (GlobalInput)session.getValue("GI"); 
%>
      <TR class = common>    
          <TD  class= title5>
            ����Ա����
          </TD>
          <TD  class= input5><Input class='common wid' name=OperaterNo id=OperaterNo></TD>
          <TD  class= title5>�������</TD>      
                <TD  class= Input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ManageCom id=ManageCom verify="�������|code:station" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName id=ManageComName readonly=true  elementtype=nacessary>
          </TD>
      </TR>
      
      
      
<%
         }else{
        	 if(Code.equals("app3")){
        		 
%>

<%
        	 }else{
%>     
     <TR class = common>    
          <TD class=title5>
            ͳ������
          </TD>          
          <TD class=input5>    
              <input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" type="text" class="code" name=StationCode id=StationCode onDblClick="showCodeList('station',[this],null,null,codeSql,'1',null,250);" onclick="showCodeList('station',[this],null,null,codeSql,'1',null,250);"  onKeyUp="return showCodeListKey('station',[this],null,null,codeSql,'1',null,250);">
          </TD>       
      </TR>
<%     
      }
   }
}
}
%>    
	<TR class =common>    
          <TD class= title5>
            ��ʼʱ��
          </TD>          
          <TD class=input5> 
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��ʼʱ��|notnull" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span> 
          </TD>
          <TD class =title5> 
           ����ʱ��
          </TD>
          <TD class=input5>  
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="����ʱ��|notnull" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
      </TR>
    </Table>  </div>

       <!--<INPUT class=cssButton  VALUE="�� ӡ �� ��" TYPE=button onclick="submitForm()"> -->
       <a href="javascript:void(0);" class="button" onClick="submitForm();">��ӡ����</a>   

    
 </form>
<span id="spanCode"  style="display: none; position:absolute; slategray"> </span> 
</body>
</html>
<script>
var codeSql = "1  and code like #"+<%=Branch%>+"%#";
var codeSql1="1 and  comcode=#86# and (userstate=#0# or userstate is null) and EdorPopedom is null and uwpopedom>#A# ";
</script>