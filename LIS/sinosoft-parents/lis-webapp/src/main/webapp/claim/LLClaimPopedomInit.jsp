<%
//Name:LLClaimPopedomInit.jsp
//function??
//author:����
//Date:2005.06.16
%>

<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>


<script language="JavaScript">

/**=========================================================================
    Form����ҳ��ʱ���г�ʼ��
   =========================================================================
*/
function initForm()
{
    try
    {
    		
        initInpBox();                //��ʼ�����
        initClaimPopedomGrid();      //����Ȩ��¼��
        
    }
    catch(re)
    {
        alter("LLClaimPopedomInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}

/**=========================================================================
    ҳ���ʼ��
   =========================================================================
*/
function initInpBox()
{
  try
  {

    //����Ȩ����Ϣ
    fm.reset( );
    fm.editPopedomButton.disabled = true;
  
  }
  catch(ex)
  {
    alter("LLClaimPopedomInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}





/**=========================================================================
����Ȩ��¼����Ϣ
=========================================================================
*/
function initClaimPopedomGrid()
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
    iArray[1][0]="Ȩ�޼���";
    iArray[1][1]="40px";
    iArray[1][2]=10;
    iArray[1][3]=0;

    iArray[2]=new Array();
    iArray[2][0]="�������ͱ���";
    iArray[2][1]="60px";
    iArray[2][2]=10;
    iArray[2][3]=3;

    iArray[3]=new Array();
    iArray[3][0]="��������";
    iArray[3][1]="50px";
    iArray[3][2]=10;
    iArray[3][3]=0;

    iArray[4]=new Array();
    iArray[4][0]="Ȩ�޼�������";
    iArray[4][1]="80px";
    iArray[4][2]=10;
    iArray[4][3]=0;
    
    iArray[5]=new Array();
    iArray[5][0]="��������";
    iArray[5][1]="40px";
    iArray[5][2]=10;
    iArray[5][3]=3;        

    iArray[6]=new Array();
    iArray[6][0]="�����������";
    iArray[6][1]="40px";
    iArray[6][2]=10;
    iArray[6][3]=3;        
    
    iArray[7]=new Array();
    iArray[7][0]="��С���";
    iArray[7][1]="40px";
    iArray[7][2]=10;
    iArray[7][3]=0;
    
    iArray[8]=new Array();
    iArray[8][0]="�����";
    iArray[8][1]="40px";
    iArray[8][2]=10;
    iArray[8][3]=0;
    
    iArray[9]=new Array();
    iArray[9][0]="��������";
    iArray[9][1]="60px";
    iArray[9][2]=10;
    iArray[9][3]=0;
            

    iArray[10]=new Array();
    iArray[10][0]="��������";
    iArray[10][1]="60px";
    iArray[10][2]=10;
    iArray[10][3]=0;                

    iArray[11]=new Array();
    iArray[11][0]="�������";
    iArray[11][1]="40px";
    iArray[11][2]=10;
    iArray[11][3]=0;  
    
    iArray[12]=new Array();
    iArray[12][0]="����Ա";
    iArray[12][1]="30px";
    iArray[12][2]=10;
    iArray[12][3]=0;
    
    
    iArray[13]=new Array();
    iArray[13][0]="�������";
    iArray[13][1]="0px";
    iArray[13][2]=10;
    iArray[13][3]=3;  
    
    iArray[14]=new Array();
    iArray[14][0]="���ʱ��";
    iArray[14][1]="0px";
    iArray[14][2]=10;
    iArray[14][3]=3;
            
    iArray[15]=new Array();
    iArray[15][0]="���һ���޸�����";
    iArray[15][1]="0px";
    iArray[15][2]=10;
    iArray[15][3]=3;  
    
    iArray[16]=new Array();
    iArray[16][0]="���һ���޸�ʱ��";
    iArray[16][1]="0px";
    iArray[16][2]=10;
    iArray[16][3]=3; 
                            
    ClaimPopedomGrid = new MulLineEnter("LLClaimPopedom","ClaimPopedomGrid");
    ClaimPopedomGrid.mulLineCount = 5;
    ClaimPopedomGrid.displayTitle = 1;
    ClaimPopedomGrid.locked = 0;
//  ClaimPopedomGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
    ClaimPopedomGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
    ClaimPopedomGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
    ClaimPopedomGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
    ClaimPopedomGrid.selBoxEventFuncName = "getClaimPopedomGrid"; //��������
    ClaimPopedomGrid.selBoxEventFuncParm ="divLLInqCourse";          //����        
    ClaimPopedomGrid.loadMulLine(iArray);
}
catch(ex)
{
    alter(ex);
}
}


</script>

