<%
//�������ƣ�LLSecondUWInit.jsp
//�����ܣ���ͬ���˹��˱�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">
	
function initParm()
{	
	fm.all('ManageCom').value = nullToEmpty("<%= tG.ManageCom %>");	
	fm.all('CaseNo').value = nullToEmpty("<%= tCaseNo%>");	
  	fm.all('InsuredNo').value =nullToEmpty("<%= tInsuredNo %>");	
  	fm.all('InsuredName').value =nullToEmpty("<%= tInsuredName %>");	
}

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
       
  }
  catch(ex)
  {
    alter("��SecondUWInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("��SecondUWInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm(tCaseNo,tInsuredNo)
{
  try
  { 
    initInpBox();
    initLCContGrid();
    initParm();
    initLCContGridQuery();
  }
  catch(re)
  {
    alter("��LLReportInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
function initLCContGrid()
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
    iArray[1][0]="��ͬ����";
    iArray[1][1]="120px";
    iArray[1][2]=100;
    iArray[1][3]=0;
    
    iArray[2]=new Array();
    iArray[2][0]="Ͷ���˿ͻ�����";
    iArray[2][1]="100px";
    iArray[2][2]=100;
    iArray[2][3]=0;
    
    iArray[3]=new Array();
    iArray[3][0]="Ͷ��������";
    iArray[3][1]="80px";
    iArray[3][2]=120;
    iArray[3][3]=0;

    iArray[4]=new Array();
    iArray[4][0]="��������";
    iArray[4][1]="80px";
    iArray[4][2]=120;
    iArray[4][3]=0;
    
    iArray[5]=new Array();
    iArray[5][0]="�ⰸ��ر�־";
    iArray[5][1]="80px";
    iArray[5][2]=120;
    iArray[5][3]=3;
        
    iArray[6]=new Array();
    iArray[6][0]="Ͷ���齡����֪��ѯ�ʺ�";
    iArray[6][1]="120px";
    iArray[6][2]=60;
    iArray[6][3]=1;
    
    iArray[7]=new Array();
    iArray[7][0]="��콡����֪��ѯ�ʺ�";
    iArray[7][1]="100px";
    iArray[7][2]=120;
    iArray[7][3]=1;

    iArray[8]=new Array();
    iArray[8][0]="��Ӧδ��֪���";
    iArray[8][1]="100px";
    iArray[8][2]=120;
    iArray[8][3]=1;
    
    LCContGrid = new MulLineEnter("fm","LCContGrid");
    LCContGrid.mulLineCount =10;
    LCContGrid.displayTitle = 1;
    LCContGrid.locked = 1;
    LCContGrid.canSel =0
    LCContGrid.canChk = 1;;
    LCContGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    LCContGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    LCContGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alter(ex);
  }
}


 </script>