<%
//Name:LLParaDeformityInit.jsp
//function??
//author:����
//Date:2005.06.24
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
    		
        initInpBox();                	//��ʼ�����
        initParaDeformityGrid();      //�˲еȼ�����¼��
        queryParaDeformity();         //�˲еȼ�������Ϣ��ѯ
        
    }
    catch(re)
    {
        alert("LLParaDeformityInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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

    //�˲еȼ�������Ϣ��ʼ��
    fm.reset( );
    fm.editDeformityButton.disabled = true;
    fm.deleteDeformityButton.disabled = true;
  }
  catch(ex)
  {
    alter("LLParaDeformityInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}





/**=========================================================================
    �˲еȼ�����¼����Ϣ
   =========================================================================
*/
function initParaDeformityGrid()
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
        iArray[1][0]="�˲�����";
        iArray[1][1]="30px";
        iArray[1][2]=10;
        iArray[1][3]=0;


        iArray[2]=new Array();
        iArray[2][0]="�˲м���";
        iArray[2][1]="30px";
        iArray[2][2]=10;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="�˲м�������";
        iArray[3][1]="40px";
        iArray[3][2]=10;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="�˲д���";
        iArray[4][1]="30px";
        iArray[4][2]=10;
        iArray[4][3]=0;        
        
        iArray[5]=new Array();
        iArray[5][0]="�˲д�������";
        iArray[5][1]="180px";
        iArray[5][2]=10;
        iArray[5][3]=0;    
        
        iArray[6]=new Array();
        iArray[6][0]="�м���������";
        iArray[6][1]="40px";
        iArray[6][2]=10;
        iArray[6][3]=0;
                                
        ParaDeformityGrid = new MulLineEnter("document","ParaDeformityGrid");
        ParaDeformityGrid.mulLineCount = 5;
        ParaDeformityGrid.displayTitle = 1;
        ParaDeformityGrid.locked = 0;
//      ParaDeformityGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
        ParaDeformityGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        ParaDeformityGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        ParaDeformityGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        ParaDeformityGrid.selBoxEventFuncName = "getParaDeformityGrid"; //��������
        //ParaDeformityGrid.selBoxEventFuncParm ="divLLInqCourse";          //����        
        ParaDeformityGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alter(ex);
    }
}

</script>

