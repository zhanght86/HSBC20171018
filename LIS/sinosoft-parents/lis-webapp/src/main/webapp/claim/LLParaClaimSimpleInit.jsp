<%
//�������ƣ�LLParaClaimSimple.jsp
//�����ܣ��򵥰�����׼
//�������ڣ�2005-9-19
//������  ��wuhao
//���¼�¼��  ������ wuhao    ��������     ����ԭ��/����
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
        initLLParaClaimSimpleGrid();      
        
    }
    catch(re)
    {
        alter("LLParaClaimSimpleInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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

    fm.reset();
    fm.editSimpleButton.disabled = true;
  
  }
  catch(ex)
  {
    alter("LLParaClaimSimpleInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}





function initLLParaClaimSimpleGrid()
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
        iArray[1][0]="��������";
        iArray[1][1]="60px";
        iArray[1][2]=10;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="��������";
        iArray[2][1]="120px";
        iArray[2][2]=10;
        iArray[2][3]=0;
        
        iArray[3]=new Array();
        iArray[3][0]="�ϼ�����";
        iArray[3][1]="120px";
        iArray[3][2]=10;
        iArray[3][3]=0;        
        
        iArray[4]=new Array();
        iArray[4][0]="��С���";
        iArray[4][1]="40px";
        iArray[4][2]=10;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="�����";
        iArray[5][1]="40px";
        iArray[5][2]=10;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="��������";
        iArray[6][1]="60px";
        iArray[6][2]=10;
        iArray[6][3]=0;
                

        iArray[7]=new Array();
        iArray[7][0]="��������";
        iArray[7][1]="60px";
        iArray[7][2]=10;
        iArray[7][3]=0;                

        iArray[8]=new Array();
        iArray[8][0]="����Ա";
        iArray[8][1]="30px";
        iArray[8][2]=10;
        iArray[8][3]=0;  
        
        iArray[9]=new Array();
        iArray[9][0]="�������";
        iArray[9][1]="0px";
        iArray[9][2]=10;
        iArray[9][3]=3;
        
        
        iArray[10]=new Array();
        iArray[10][0]="���ʱ��";
        iArray[10][1]="0px";
        iArray[10][2]=10;
        iArray[10][3]=3;  
        
        iArray[11]=new Array();
        iArray[11][0]="���һ���޸�����";
        iArray[11][1]="0px";
        iArray[11][2]=10;
        iArray[11][3]=3;
                
        iArray[12]=new Array();
        iArray[12][0]="���һ���޸�ʱ��";
        iArray[12][1]="0px";
        iArray[12][2]=10;
        iArray[12][3]=3;  
        
        LLParaClaimSimpleGrid = new MulLineEnter("document","LLParaClaimSimpleGrid");
        LLParaClaimSimpleGrid.mulLineCount = 5;
        LLParaClaimSimpleGrid.displayTitle = 1;
        LLParaClaimSimpleGrid.locked = 0;
        LLParaClaimSimpleGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        LLParaClaimSimpleGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        LLParaClaimSimpleGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        LLParaClaimSimpleGrid.selBoxEventFuncName = "LLParaClaimSimpleGridClick"; //��������
        LLParaClaimSimpleGrid.loadMulLine(iArray);

    }
    catch(ex)
    {
        alter(ex);
    }
}

</script>

