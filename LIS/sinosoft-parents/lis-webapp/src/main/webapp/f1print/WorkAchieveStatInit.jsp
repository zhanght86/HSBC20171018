<%
//�������ƣ�WorkAchieveStatInit.jsp
//�����ܣ�������Чͳ���嵥
//�������ڣ�2005-11-29 17:20:22
//������  ��liurx
//���¼�¼��  ������    ��������      ����ԭ��/���� 
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.BqNameFun"%>

<%
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	String strOperator = globalInput.Operator;
	String strManageCom = globalInput.ComCode;
	////Ĭ��ͳ����ֹ��Ϊ��һ�����·�
	//String[] dateArr = BqNameFun.getPreFinaMonth(PubFun.getCurrentDate());
	//Ĭ�������ָ�Ϊ��Ȼ�·ݣ�2006-05-08�ų����
    String[] dateArr = BqNameFun.getNomalMonth(PubFun.getCurrentDate());
%>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
    document.all('StartDate').value = '<%=dateArr[0]%>';
	document.all('EndDate').value = '<%=dateArr[1]%>';
	document.all('ManageCom').value = <%=strManageCom%>;
	
  }
  catch(ex)
  {
    alert("��WorkAchieveStatInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initSelBox()
{  
  try                 
  {
 
  }
  catch(ex)
  {
    alert("��WorkAchieveStatInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox(); 
    //initDate(); //ͳ�����ں�ֹ����Ĭ��ֵ
    initEdorType(); //��ʼ����ȫ��Ŀ������
  }
  catch(re)
  {
    alert("WorkAchieveStatInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

</script>