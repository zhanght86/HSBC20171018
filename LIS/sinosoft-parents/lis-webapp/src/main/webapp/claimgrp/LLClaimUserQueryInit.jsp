<%
//ҳ������: LLClaimUserQueryInit.jsp
//ҳ�湦�ܣ������û���ѯҳ���ʼ��
//�����ˣ�yuejw
//�������ڣ�2005.07.14
//�޸�ԭ��/���ݣ�
%>
<!--�û�У����-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">

function initForm()
{
    try
    {
        initLLClaimUserGrid();
    }
    catch(re)
    {
        alert("LLClaimUserQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}

//�����û��б�
function initLLClaimUserGrid()
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
        iArray[1][0]="�û�����";
        iArray[1][1]="60px";
        iArray[1][2]=10;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="�û�����";
        iArray[2][1]="60px";
        iArray[2][2]=10;
        iArray[2][3]=0;
        
        iArray[3]=new Array();
        iArray[3][0]="��������";
        iArray[3][1]="60px";
        iArray[3][2]=10;
        iArray[3][3]=0;        
        
        iArray[4]=new Array();
        iArray[4][0]="����Ȩ��";
        iArray[4][1]="60px";
        iArray[4][2]=10;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="����Ȩ��";
        iArray[5][1]="60px";
        iArray[5][2]=10;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="Ԥ��Ȩ��";
        iArray[6][1]="60px";
        iArray[6][2]=10;
        iArray[6][3]=0;  
        
		iArray[7]=new Array();
        iArray[7][0]="���Ȩ��";
        iArray[7][1]="60px";
        iArray[7][2]=10;
        iArray[7][3]=0;
                
        iArray[8]=new Array();
        iArray[8][0]="��˼���";
        iArray[8][1]="60px";
        iArray[8][2]=10;
        iArray[8][3]=0;  
        
        iArray[9]=new Array();
        iArray[9][0]="����Ȩ��";
        iArray[9][1]="60px";
        iArray[9][2]=10;
        iArray[9][3]=0;
                
        iArray[10]=new Array();
        iArray[10][0]="��������";
        iArray[10][1]="70px";
        iArray[10][2]=10;
        iArray[10][3]=0;  

        iArray[11]=new Array();
        iArray[11][0]="���װ���Ȩ��";
        iArray[11][1]="80px";
        iArray[11][2]=10;
        iArray[11][3]=0;
        
        iArray[12]=new Array();
        iArray[12][0]="�᰸Ȩ��";
        iArray[12][1]="80px";
        iArray[12][2]=10;
        iArray[12][3]=0;
        
        iArray[13]=new Array();
        iArray[13][0]="��Ч��ʶ";
        iArray[13][1]="70px";
        iArray[13][2]=10;
        iArray[13][3]=0;          
                                
        LLClaimUserGrid = new MulLineEnter("fm","LLClaimUserGrid");
        LLClaimUserGrid.mulLineCount = 0;
        LLClaimUserGrid.displayTitle = 1;
        LLClaimUserGrid.locked = 0;
        LLClaimUserGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        LLClaimUserGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        LLClaimUserGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        LLClaimUserGrid.selBoxEventFuncName = "LLClaimUserGridClick"; //��������
        LLClaimUserGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alter(ex);
    }
}
</script>
