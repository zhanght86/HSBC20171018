<%
//Name:LCInsureAccontInit.jsp
//Function�����ʻ�������ʼ�� 
//author: 
//Date:
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">
	
//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
    fm.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
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

function initForm()
{
  try
  {
    initParam();
    initInpBox();
    initLCInsureAccClassGrid(); //	�����˻������
    initLCInsureAccTraceGrid(); //�����ʻ���Ǽ�������
    LCInsureAccClassGridQuery(); //��ѯ��ʼ��ҳ���ϲ��ı����˻������

  }
  catch(re)
  {
    alert("��LCInsureAccTraceGrid-->InitForm�����з����쳣:��ʼ���������!");
  }
}


//ҳ���ʼ��
function initInpBox()
{
  try
  {

  }
  catch(ex)
  {
    alert("��LCInsureAccontInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

//	�����˻������
function initLCInsureAccClassGrid()
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
        iArray[1][0]="��������"; //
        iArray[1][1]="100px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="�����ʻ�����"; //InsuAccNo
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="���Ѽƻ�����";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=0;
    
        iArray[4]=new Array();
        iArray[4][0]="��Ӧ��������";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;
    
        iArray[5]=new Array()
        iArray[5][0]="��Ӧ������������";
        iArray[5][1]="50px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="�˻���������";
        iArray[6][1]="50px";
        iArray[6][2]=100;
        iArray[6][3]=0;
    
        iArray[7]=new Array();
        iArray[7][0]="�����˿ͻ�����";
        iArray[7][1]="80px";
        iArray[7][2]=100;
        iArray[7][3]=0;
    
        iArray[8]=new Array();
        iArray[8][0]="�˻�����";
        iArray[8][1]="60px";
        iArray[8][2]=100;
        iArray[8][3]=0;

        iArray[9]=new Array();
        iArray[9][0]="���㷽ʽ";
        iArray[9][1]="60px";
        iArray[9][2]=100;
        iArray[9][3]=0;
    
        iArray[10]=new Array();
        iArray[10][0]="��������";
        iArray[10][1]="60px";
        iArray[10][2]=100;
        iArray[10][3]=0;

        LCInsureAccClassGrid = new MulLineEnter("fm","LCInsureAccClassGrid");
        LCInsureAccClassGrid.mulLineCount = 1;
        LCInsureAccClassGrid.displayTitle = 1;
        LCInsureAccClassGrid.locked = 0;
//        LCInsureAccClassGrid.canChk =1;
        LCInsureAccClassGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        LCInsureAccClassGrid.selBoxEventFuncName ="LCInsureAccClassGridClick"; //��Ӧ�ĺ���������������
        LCInsureAccClassGrid.selBoxEventFuncParm ="1"; //����Ĳ���,����ʡ�Ը���  
        LCInsureAccClassGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        LCInsureAccClassGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        LCInsureAccClassGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alter(ex);
  }
}


//�����ʻ���Ǽ�������
function initLCInsureAccTraceGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";
        iArray[0][1]="30px";
        iArray[0][2]=30;
        iArray[0][3]=0;

        iArray[1]=new Array();
        iArray[1][0]="�������ֺ���";
        iArray[1][1]="80px";
        iArray[1][2]=80;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="�����ʻ�����";
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="��ˮ��";
        iArray[3][1]="80px";
        iArray[3][2]=80;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="�������";      //Ҳ����������
        iArray[4][1]="80px";
        iArray[4][2]=80;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="���ν��";
        iArray[5][1]="80px";
        iArray[5][2]=80;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="���ε�λ��";
        iArray[6][1]="80px";
        iArray[6][2]=80;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="��������";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="״̬";
        iArray[8][1]="80px";
        iArray[8][2]=80;
        iArray[8][3]=0;
        

        LCInsureAccTraceGrid = new MulLineEnter("fm","LCInsureAccTraceGrid");
        LCInsureAccTraceGrid.mulLineCount = 1;       // ��ʾ����
        LCInsureAccTraceGrid.displayTitle = 1;
        LCInsureAccTraceGrid.locked = 0;
//        LCInsureAccTraceGrid.canChk =1;              // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
        LCInsureAccTraceGrid.canSel =0;                  // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
        LCInsureAccTraceGrid.hiddenPlus=1;           //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        LCInsureAccTraceGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        LCInsureAccTraceGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alter(ex);
    }
}

</script>
