<%@include file="../i18n/language.jsp"%>
<%
//�������ƣ�FinPayDayInit.jsp
//�����ܣ�
//�������ڣ�2007-11-15 15:39:06
//������  ��sunyu���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput globalInput = (GlobalInput)session.getAttribute("GI");	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	//String strOperator = globalInput.Operator;
%>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script type="text/javascript">
function initInpBox()
{ 
  try
  {     
    fm.all('StartDay').value = '';
    fm.all('EndDay').value = '';

  }
  catch(ex)
  {
    myAlert("��FinGetDayInit.jsp-->"+"��ʼ���������!");
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
    myAlert("��FinGetDayInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
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
    myAlert("FinGetDayInit.jsp-->"+"��ʼ���������!");
  }
}
</script>