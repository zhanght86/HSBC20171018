<%
//�������ƣ�PEdorTypeCTTestInit.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��Supl
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->

<%
  String CurrentDate = PubFun.getCurrentDate();
%>                         

<script language="JavaScript">  
function initInpBox()
{ 

  try
  {        
    fm.ContNo.value = "";
    
  }
  catch(ex)
  {
    alert("��PEdorTypeCTInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

//��null���ַ���תΪ��
function nullToEmpty(string)
{
    if ((string == "null") || (string == "undefined"))
    {
        string = "";
    }
    return string;
}

function initSelBox()
{  
  try                 
  {  
  }
  catch(ex)
  {
    alert("��PEdorTypeCTInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
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
    alert("PEdorTypeCTInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

</script>
