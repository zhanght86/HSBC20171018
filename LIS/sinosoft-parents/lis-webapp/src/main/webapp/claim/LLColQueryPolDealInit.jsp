<%
//**************************************************************************************************
//Name��
//Function��
//Author��
//Date: 
//**************************************************************************************************
%>
<html>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
</html>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

//����ҳ�洫�ݹ����Ĳ���
function initParam()
{
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
}

/**=========================================================================
    Form����ҳ��ʱ���г�ʼ��
   =========================================================================
*/
function initForm()
{
    try
    {
        initParam();
        initInpBox();                   //��ʼ�����
        initPolCalResultGrid()         //������
        initQuery()                     //��ʼ��ѯ        
    }
    catch(re)
    {
        alert("LLClaimPolDealInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
        alert("LLClaimPolDealInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }
}

//��������Ľ��
function initPolCalResultGrid()
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
        iArray[1][0]="ҵ������";
        iArray[1][1]="40px";
        iArray[1][2]=40;
        iArray[1][3]=3;
        
        iArray[2]=new Array();
        iArray[2][0]="ҵ����������";
        iArray[2][1]="150px";
        iArray[2][2]=150;
        iArray[2][3]=0;        
        
        iArray[3]=new Array();
        iArray[3][0]="��ҵ������";
        iArray[3][1]="60px";
        iArray[3][2]=60;
        iArray[3][3]=3;
        
        iArray[4]=new Array();
        iArray[4][0]="��ҵ����������";
        iArray[4][1]="150px";
        iArray[4][2]=150;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="��ͬ����";
        iArray[5][1]="100px";
        iArray[5][2]=100;
        iArray[5][3]=0;
                
       iArray[6]=new Array();
        iArray[6][0]="��������";
        iArray[6][1]="120px";
        iArray[6][2]=80;
        iArray[6][3]=0; 
                              
        iArray[7]=new Array();
        iArray[7][0]="���ֱ���";
        iArray[7][1]="0px";
        iArray[7][2]=10;
        iArray[7][3]=3;  
        
        iArray[8]=new Array();
        iArray[8][0]="��������";
        iArray[8][1]="80px";
        iArray[8][2]=80;
        iArray[8][3]=0;
        
        iArray[9]=new Array();
        iArray[9][0]="������";
        iArray[9][1]="80px";
        iArray[9][2]=80;
        iArray[9][3]=0;
                                                                        
        PolCalResultGrid = new MulLineEnter("document","PolCalResultGrid");
        PolCalResultGrid.mulLineCount = 5;
        PolCalResultGrid.displayTitle = 1;
        PolCalResultGrid.locked = 0;
//      PolCalResultGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
        PolCalResultGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        PolCalResultGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        PolCalResultGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
//        PolCalResultGrid.selBoxEventFuncName = "getContReferGrid"; //��������
//        PolCalResultGrid.selBoxEventFuncParm ="ContDeal";          //����        
        PolCalResultGrid.loadMulLine(iArray);
        
    }
    catch(ex)
    {
        alert("LLClaimPolDealInit.jsp-->initPolCalResultGrid�����з����쳣:��ʼ���������!");
    }
}

function nullToEmpty(string)
{
    if ((string == "null") || (string == "undefined"))
    {
        string = "";
    }
    return string;
}

</script>
