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
<%@page import = "com.sinosoft.lis.claimgrp.*"%>
<%
String accidentDetailName = StrTool.unicodeToGBK(request.getParameter("accidentDetailName"));
String AccResultName =StrTool.unicodeToGBK(request.getParameter("AccResultName"));
String occurReason = request.getParameter("occurReason");

%>

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
  try
  {                                   
   // ������ѯ����
      fm.accidentDetailName.value = '<%=accidentDetailName%>';

      fm.AccResultName.value = '<%=AccResultName%>';      
      fm.occurReason.value = '<%=occurReason%>';      

 

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
    initAccidentGrid();
    afterQuery();
  }
  catch(re)
  {

    alert("LDPersonQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initAccidentGrid()
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
      iArray[1][0]="���մ���";               //����
      iArray[1][1]="100px";                  //�п�
      iArray[1][2]=100;                     //�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";               //����
      iArray[2][1]="400px";                  //�п�
      iArray[2][2]=400;                     //�����ֵ
      iArray[2][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

      AccidentGrid = new MulLineEnter( "fm" , "AccidentGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      AccidentGrid.mulLineCount = 10;   
      AccidentGrid.displayTitle = 1;
      AccidentGrid.locked = 1;
      AccidentGrid.canSel = 1;
      AccidentGrid.hiddenPlus=1;
      AccidentGrid.hiddenSubtraction=1;
      AccidentGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
