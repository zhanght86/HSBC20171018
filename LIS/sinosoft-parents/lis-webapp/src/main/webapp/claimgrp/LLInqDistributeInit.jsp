<%
//Name:LLInqDistributeInit.jsp
//function����������������ĳ�ʼ��
//author:
//���¼�¼��  ������:yuejw    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
<script language="JavaScript">
//���ղ���
function initParam()
{
	fm.all('ManageCom').value = nullToEmpty("<%= tGlobalInput.ManageCom %>");	      
    fm.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
    fm.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
    fm.all('tComCode').value =fm.all('ComCode').value+"%"; //���ڸ��ݻ�����ѯ������
   
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
function initInpBox()
{
  try
  {
    fm.all('ClmNo').value = '';
    fm.all('CustomerName').value = '';
    fm.all('CustomerNo').value = '';
    
  }
  catch(ex)
  {
    alter("��LLSuveryDistributInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("��LLSuveryDistributInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}
function initForm()
{
  try
  {
    
    initInpBox();
    initInqApplyGrid();
    initParam();

  }
  catch(re)
  {
    alter("��LLReportInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
function initInqApplyGrid()
{
  var iArray = new Array();
  try
  {
    iArray[0]=new Array();
    iArray[0][0]="���";
    iArray[0][1]="30px";
    iArray[0][2]=10;
    iArray[0][3]=0;

    iArray[1]=new Array();
    iArray[1][0]="�ⰸ��";
    iArray[1][1]="100px";
    iArray[1][2]=100;
    iArray[1][3]=0;
    
    iArray[2]=new Array();
    iArray[2][0]="�������";
    iArray[2][1]="100px";
    iArray[2][2]=100;
    iArray[2][3]=0;
    
    iArray[3]=new Array();
    iArray[3][0]="�������κ�";
    iArray[3][1]="100px";
    iArray[3][2]=120;
    iArray[3][3]=3;

    iArray[4]=new Array();
    iArray[4][0]="�����˿ͻ���";
    iArray[4][1]="100px";
    iArray[4][2]=60;
    iArray[4][3]=0;
    
    iArray[5]=new Array();
    iArray[5][0]="����������";
    iArray[5][1]="100px";
    iArray[5][2]=120;
    iArray[5][3]=0;

    iArray[6]=new Array();
    iArray[6][0]="����׶�";
    iArray[6][1]="100px";
    iArray[6][2]=60;
    iArray[6][3]=0;

    iArray[7]=new Array();
    iArray[7][0]="����ԭ��";
    iArray[7][1]="100px";
    iArray[7][2]=60;
    iArray[7][3]=3;
    
    iArray[8]=new Array();
    iArray[8][0]="�������";
    iArray[8][1]="100px";
    iArray[8][2]=60;
    iArray[8][3]=0;
    
    iArray[9]=new Array();
    iArray[9][0]="Missionid";             //����
    iArray[9][1]="100px";                //�п�
    iArray[9][2]=200;                  //�����ֵ
    iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[10]=new Array();
    iArray[10][0]="submissionid";             //����
    iArray[10][1]="100px";                //�п�
    iArray[10][2]=200;                  //�����ֵ
    iArray[10][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������             
     		     
    iArray[11]=new Array();
    iArray[11][0]="activityid";             //����
    iArray[11][1]="80px";                //�п�
    iArray[11][2]=200;                  //�����ֵ
    iArray[11][3]=3;     
    
    InqApplyGrid = new MulLineEnter("fm","InqApplyGrid");
    InqApplyGrid.mulLineCount =0;
    InqApplyGrid.displayTitle = 1;
    InqApplyGrid.locked = 1;
    InqApplyGrid.canSel =1;
    InqApplyGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    InqApplyGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    InqApplyGrid.selBoxEventFuncName = "InqApplyGridSelClick";
    InqApplyGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alter(ex);
  }
}

 </script>
