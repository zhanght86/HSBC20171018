<%
//�������ƣ�LLAccidentDetailQueryInit.jsp
//�����ܣ�
//�������ڣ�2005-10-26
//������  ��pd
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
String HospitalName = StrTool.unicodeToGBK(request.getParameter("HospitalName"));
%>

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
  try
  {                                   
   // ������ѯ����
      fm.HospitalName.value = '<%=HospitalName %>';

  }
  catch(ex)
  {
    alert("��LDPersonQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("��LDPersonQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();  
    initHospitalGrid();
    afterQuery();
  }
  catch(re)
  {
    alert("LDPersonQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initHospitalGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                  //�п�
      iArray[0][2]=10;                     //�����ֵ
      iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="ҽԺ����";               //����
      iArray[1][1]="160px";                  //�п�
      iArray[1][2]=100;                     //�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="ҽԺ����";               //����
      iArray[2][1]="250px";                  //�п�
      iArray[2][2]=100;                     //�����ֵ
      iArray[2][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

      HospitalGrid = new MulLineEnter( "fm" , "HospitalGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      HospitalGrid.mulLineCount = 10;   
      HospitalGrid.displayTitle = 1;
      HospitalGrid.locked = 1;
      HospitalGrid.canSel = 1;
      HospitalGrid.hiddenPlus=1;
      HospitalGrid.hiddenSubtraction=1;
      HospitalGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
