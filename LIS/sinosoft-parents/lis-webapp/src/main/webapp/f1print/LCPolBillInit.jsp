<%
//�������ƣ�LCPolBillInit.jsp
//�����ܣ�
//�������ڣ�2003-1-15
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	PubFun tpubFun = new PubFun();
	String strCurDay = tpubFun.getCurrentDate();
	
	//����ҳ��ؼ��ĳ�ʼ����
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		loggerDebug("LCPolBillInit","session has expired");
		return;
	}
	
	String strOperator = globalInput.Operator;
%>
<%
     //����ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
    document.all('StartDay').value = "<%=strCurDay%>";
    document.all('EndDay').value = "<%=strCurDay%>";
    document.all('StartTime').value = '00:00:00';
    document.all('EndTime').value = '23:59:59';
    document.all('ManageCom').value = '';    
    document.all('SaleChnl').value = '';
    document.all('StartContNo').value = "";
    document.all('EndContNo').value = "";
    document.all('ManageGrade').value = "4";
    document.all('MangeComGradeName').value = "��������";  
    document.all('ManageCom').value = <%=globalInput.ComCode%>;
  }
  catch(ex)
  {
    alert("��LCPolBillInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=��&1=Ů&2=����");      
//    setOption("sex","0=��&1=Ů&2=����");        
//    setOption("reduce_flag","0=����״̬&1=�����");
//    setOption("pad_flag","0=����&1=�潻");   
  }
  catch(ex)
  {
    alert("��LCPolBillInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
  }
  catch(re)
  {
    alert("LCPolBillInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>