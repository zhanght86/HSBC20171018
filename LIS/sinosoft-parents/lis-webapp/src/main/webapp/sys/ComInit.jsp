<%
//�������ƣ�ComInput.jsp
//�����ܣ�
//�������ڣ�2002-08-16 17:44:40
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>

<script language="JavaScript">
function initInpBox()
{
  try
  {
    document.all('ComCode').value = '';
    document.all('OutComCode').value = '';
    document.all('Name').value = '';
    document.all('ShortName').value = '';
    document.all('ComGrade').value = '';
    document.all('ComGradeName').value = '';
    document.all('Address').value = '';
    document.all('ZipCode').value = '';
    document.all('Phone').value = '';
    document.all('Fax').value = '';
    document.all('EMail').value = '';
    document.all('WebAddress').value = '';
    document.all('SatrapName').value = '';
    document.all('Sign').value = '';
    document.all('UpComCode').value = '';
    //document.all('ComAreaType').value = '';
    //document.all('ComAreaTypeName').value = '';
    document.all('ComCitySize').value = '';
    document.all('ComCitySizeName').value = '';
    document.all('IsDirUnder').value = '';
    document.all('IsDirUnderName').value = '';
	}
  catch(ex)
  {
    alert("��ComInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��ComInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
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
    alert("ComInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>
