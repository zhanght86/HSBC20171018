<%
//�ļ�����: LLClaimCertiPrtControlInit.jsp
//�ļ����ܣ����ⵥ֤��ӡ����
//�������ڣ�2005-10-15  ������ ��                   
//���¼�¼��
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">
var turnPage = new turnPageClass();
//������ҳ��������� 
function initParam()
{
	document.all('ClmNo').value =nullToEmpty("<%=tClmNo%>");
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
 	initClaimCertiGrid();
 	initParam();
 	showCertiPrtControl();
    }
    catch(re)
    {
        alert("LLClaimCertiPrtControlInit.jsp->InitForm�����з����쳣:��ʼ���������!");
    }
}


//��ӡ������и��ⰸ�¿��Դ�ӡ�ĵ�֤��Ϣ��Ϣ
function initClaimCertiGrid()
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
        iArray[1][0]="ӡˢ��ˮ��";
        iArray[1][1]="100px";
        iArray[1][2]=10;
        iArray[1][3]=0;
        
        iArray[2]=new Array();
        iArray[2][0]="���ʹ���";
        iArray[2][1]="60px";
        iArray[2][2]=300;
        iArray[2][3]=0;
      
        iArray[3]=new Array();
        iArray[3][0]="������������";
        iArray[3][1]="250px";
        iArray[3][2]=10;
        iArray[3][3]=0;        
                
        iArray[4]=new Array();
        iArray[4][0]="�������";
        iArray[4][1]="0px";
        iArray[4][2]=10;
        iArray[4][3]=3;   
        
        iArray[5]=new Array();
        iArray[5][0]="�������";
        iArray[5][1]="0px";
        iArray[5][2]=10;
        iArray[5][3]=3;    
                
        iArray[6]=new Array();
        iArray[6][0]="������";
        iArray[6][1]="0px";
        iArray[6][2]=10;
        iArray[6][3]=3;  
        
        iArray[7]=new Array();
        iArray[7][0]="��ӡ��ʽ";
        iArray[7][1]="0px";
        iArray[7][2]=10;
        iArray[7][3]=3;  
                
        iArray[8]=new Array();
        iArray[8][0]="��ӡ״̬";
        iArray[8][1]="60px";
        iArray[8][2]=50;
        iArray[8][3]=0; 

        ClaimCertiGrid = new MulLineEnter("fm","ClaimCertiGrid");
        ClaimCertiGrid.mulLineCount = 0;
        ClaimCertiGrid.displayTitle = 1;
        ClaimCertiGrid.locked = 0;
        ClaimCertiGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
        ClaimCertiGrid.canSel =0;              //��ѡ��ť��1��ʾ��0����
        ClaimCertiGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        ClaimCertiGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        ClaimCertiGrid.loadMulLine(iArray);
        
    }
    catch(ex)
    {
        alter(ex);
    }
}

</script> 
