<%
//**************************************************************************************************
//ҳ������: LWMissionReassignInit.jsp
//ҳ�湦�ܣ��������·�������ʼ��
//������: yuejw    �������ڣ�2005-7-14   
//�޸����ڣ�  �޸�ԭ��/���ݣ�
//**************************************************************************************************
%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">
    
var mCurrentDate = "";
var mNowYear = "";
var mNowMonth = "";
var mNowDay = "";

//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
   fm.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
   fm.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
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

//��ʼ����
function initForm()
{
    try
    {
//    	initParam();
//    	initInpBox();
    	initLWMissionGrid();
    }
    catch(ex)
    {
        alert("��LWMissionReassignInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}

//ҳ���������ʼ��
function initInpBox()
{
    try
    {
		fm.DefaultOperator.value="";
		fm.DesignateOperator.value="";	
    }
    catch(ex)
    {
        alert("��LWMissionReassignInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }
}

function initLWMissionGrid()
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
        iArray[1][1]="120px";
        iArray[1][2]=10;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="����״̬";
        iArray[2][1]="0px";
        iArray[2][2]=10;
        iArray[2][3]=3;
        
        iArray[3]=new Array();
        iArray[3][0]="�����˱���";
        iArray[3][1]="80px";
        iArray[3][2]=10;
        iArray[3][3]=0;        
        
        iArray[4]=new Array();
        iArray[4][0]="����������";
        iArray[4][1]="80px";
        iArray[4][2]=10;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="�������Ա�";
        iArray[5][1]="60px";
        iArray[5][2]=10;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="��������";
        iArray[6][1]="80px";
        iArray[6][2]=10;
        iArray[6][3]=0;
                
        iArray[7]=new Array();
        iArray[7][0]="��������";
        iArray[7][1]="60px";
        iArray[7][2]=10;
        iArray[7][3]=0;                
//		iArray[7][4]="llrgtclass";   
//		iArray[7][18]=100; 
//		iArray[7][19]=1; 
//        iArray[7][20]=1; 

        iArray[8]=new Array();
        iArray[8][0]="����Ա";
        iArray[8][1]="60px";
        iArray[8][2]=10;
        iArray[8][3]=0;     
        
        iArray[9]=new Array();
        iArray[9][0]="MissionID";
        iArray[9][1]="0px";
        iArray[9][2]=10;
        iArray[9][3]=3;     
        
        iArray[10]=new Array();
        iArray[10][0]="SubMissionID";
        iArray[10][1]="0px";
        iArray[10][2]=10;
        iArray[10][3]=3;                     

        iArray[11]=new Array();
        iArray[11][0]="ActivityID";
        iArray[11][1]="0px";
        iArray[11][2]=10;
        iArray[11][3]=3;     
                                       
        LWMissionGrid = new MulLineEnter("fm","LWMissionGrid");
        LWMissionGrid.mulLineCount = 0;
        LWMissionGrid.displayTitle = 1;
        LWMissionGrid.locked = 0;
//      LWMissionGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
        LWMissionGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        LWMissionGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        LWMissionGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        LWMissionGrid.selBoxEventFuncName = "LWMissionGridClick"; //��������
        LWMissionGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alter(ex);
    }
}

</script>
