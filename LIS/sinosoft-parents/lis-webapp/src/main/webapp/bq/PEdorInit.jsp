<%
//�������ƣ�LLReportInput.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->

<%
     %>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {                                   
    document.all('PolNo').value = '';
    document.all('RptorName').value = '';
    document.all('RptorAddress').value = '';
    document.all('RptorPhone').value = '';
    document.all('RptorMobile').value = '';
    document.all('Relation').value = '';
    document.all('RptDate').value = '';
    document.all('RptMode').value = '';
    document.all('AccidentSite').value = '';
    document.all('AccidentReason').value = '';
    document.all('AccidentCourse').value = '';
    document.all('AccidentDate').value = '';
    document.all('MngCom').value = '';
    document.all('NoRgtReason').value = '';
    document.all('AgencyGroup').value = '';
    document.all('AgencyCode').value = '';
    document.all('Remark').value = '';
  }
  catch(ex)
  {
    alert("��PEdorInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��PEdorInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
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
    alert("PEdorInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

</script>