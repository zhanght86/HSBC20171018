<%
//Name:LLMedicalFeeInpInit.jsp
//function??
//author:����
//Date:2005.07.13
%>
<html>
 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
</html>

<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>


<script language="JavaScript">
/**=========================================================================
    Form����ҳ��ʱ���г�ʼ��
   =========================================================================
*/
function initForm()
{
    try
    {
        initInpBox();                   //��ʼ�����
        initContNoReferGrid()           //�ⰸδ�漰�ĺ�ͬ
        initContReferGrid()             //�ⰸ�漰�ĺ�ͬ
        initContPolReferGrid()          //�ⰸ�漰�ĺ�ͬ����
        initContCalResultGrid()         //������
        initLLContStateGrid()           //�����ͬ��ֹ��Ϣ        
        initQuery()                     //��ʼ��ѯ        

    }
    catch(re)
    {
        alter("LLClaimContDealInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
//      MedFeeInHosInp.style.display="none";
    }
    catch(ex)
    {
        alter("LLClaimContDealInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }
}




/**=========================================================================
    �ⰸδ�漰�ı���
   =========================================================================
*/
function initContNoReferGrid()
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
        iArray[1][0]="�����ͬ����";
        iArray[1][1]="100px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        
        iArray[2]=new Array();
        iArray[2][0]="��ͬ��";
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;        
        
        iArray[3]=new Array();
        iArray[3][0]="�ܵ�����";
        iArray[3][1]="60px";
        iArray[3][2]=60;
        iArray[3][3]=3;
        
        iArray[4]=new Array();
        iArray[4][0]="Ͷ���˿ͻ���";
        iArray[4][1]="80px";
        iArray[4][2]=80;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="Ͷ��������";
        iArray[5][1]="80px";
        iArray[5][2]=80;
        iArray[5][3]=0;
                

        iArray[6]=new Array();
        iArray[6][0]="Ͷ�����Ա�";
        iArray[6][1]="0px";
        iArray[6][2]=10;
        iArray[6][3]=3; 
        
        
        iArray[7]=new Array();
        iArray[7][0]="Ͷ���˳�������";
        iArray[7][1]="0px";
        iArray[7][2]=10;
        iArray[7][3]=3;  
        
        iArray[8]=new Array();
        iArray[8][0]="�����˿ͻ���";
        iArray[8][1]="80px";
        iArray[8][2]=80;
        iArray[8][3]=0;
        
        
        iArray[9]=new Array();
        iArray[9][0]="����������";
        iArray[9][1]="80px";
        iArray[9][2]=80;
        iArray[9][3]=0;  
        
        iArray[10]=new Array();
        iArray[10][0]="�������Ա�";
        iArray[10][1]="0px";
        iArray[10][2]=10;
        iArray[10][3]=3;
                
        iArray[11]=new Array();
        iArray[11][0]="�����˳�������";
        iArray[11][1]="0px";
        iArray[11][2]=10;
        iArray[11][3]=3;  
        
        iArray[12]=new Array();
        iArray[12][0]="Ͷ������";
        iArray[12][1]="80px";
        iArray[12][2]=80;
        iArray[12][3]=0;

        iArray[13]=new Array();
        iArray[13][0]="ǩ������";
        iArray[13][1]="80px";
        iArray[13][2]=80;
        iArray[13][3]=0;        

        iArray[14]=new Array();
        iArray[14][0]="ǩ������";
        iArray[14][1]="80px";
        iArray[14][2]=80;
        iArray[14][3]=0;  

        iArray[15]=new Array();
        iArray[15][0]="������Ч��";
        iArray[15][1]="80px";
        iArray[15][2]=80;
        iArray[15][3]=0;  

        iArray[16]=new Array();
        iArray[16][0]="Ͷ����־";
        iArray[16][1]="60px";
        iArray[16][2]=60;
        iArray[16][3]=0;  
        
                                        
        ContNoReferGrid = new MulLineEnter("fm","ContNoReferGrid");
        ContNoReferGrid.mulLineCount = 0;
        ContNoReferGrid.displayTitle = 1;
        ContNoReferGrid.locked = 0;
//      ContNoReferGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
        ContNoReferGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        ContNoReferGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        ContNoReferGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
//      ContNoReferGrid.selBoxEventFuncName = "getMedFeeClinicInpGrid"; //��������
//      ContNoReferGrid.selBoxEventFuncParm ="divLLInqCourse";          //����        
        ContNoReferGrid.loadMulLine(iArray);
        
    }
    catch(ex)
    {
        alter("LLClaimContDealInit.jsp-->initContNoReferGrid�����з����쳣:��ʼ���������!");
    }
}

/**=========================================================================
    �ⰸ�漰�ĺ�ͬ
   =========================================================================
*/
function initContReferGrid()
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
        iArray[1][0]="�����ͬ����";
        iArray[1][1]="120px";
        iArray[1][2]=120;
        iArray[1][3]=0;

        
        iArray[2]=new Array();
        iArray[2][0]="��ͬ��";
        iArray[2][1]="120px";
        iArray[2][2]=120;
        iArray[2][3]=0;        
        
        iArray[3]=new Array();
        iArray[3][0]="�ܵ�����";
        iArray[3][1]="60px";
        iArray[3][2]=60;
        iArray[3][3]=3;
        
        iArray[4]=new Array();
        iArray[4][0]="Ͷ���˿ͻ���";
        iArray[4][1]="80px";
        iArray[4][2]=80;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="Ͷ��������";
        iArray[5][1]="80px";
        iArray[5][2]=80;
        iArray[5][3]=0;
                

        iArray[6]=new Array();
        iArray[6][0]="Ͷ�����Ա�";
        iArray[6][1]="0px";
        iArray[6][2]=10;
        iArray[6][3]=3; 
        
        
        iArray[7]=new Array();
        iArray[7][0]="Ͷ���˳�������";
        iArray[7][1]="0px";
        iArray[7][2]=10;
        iArray[7][3]=3;  
        
        iArray[8]=new Array();
        iArray[8][0]="�����˿ͻ���";
        iArray[8][1]="80px";
        iArray[8][2]=80;
        iArray[8][3]=0;
        
        
        iArray[9]=new Array();
        iArray[9][0]="����������";
        iArray[9][1]="80px";
        iArray[9][2]=80;
        iArray[9][3]=0;  
        
        iArray[10]=new Array();
        iArray[10][0]="�������Ա�";
        iArray[10][1]="0px";
        iArray[10][2]=10;
        iArray[10][3]=3;
                
        iArray[11]=new Array();
        iArray[11][0]="�����˳�������";
        iArray[11][1]="0px";
        iArray[11][2]=10;
        iArray[11][3]=3;  
        
        iArray[12]=new Array();
        iArray[12][0]="Ͷ������";
        iArray[12][1]="80px";
        iArray[12][2]=80;
        iArray[12][3]=0;

        iArray[13]=new Array();
        iArray[13][0]="ǩ������";
        iArray[13][1]="80px";
        iArray[13][2]=80;
        iArray[13][3]=0;        

        iArray[14]=new Array();
        iArray[14][0]="ǩ������";
        iArray[14][1]="80px";
        iArray[14][2]=80;
        iArray[14][3]=0;  

        iArray[15]=new Array();
        iArray[15][0]="������Ч��";
        iArray[15][1]="80px";
        iArray[15][2]=80;
        iArray[15][3]=0;  

        iArray[16]=new Array();
        iArray[16][0]="Ͷ����־";
        iArray[16][1]="60px";
        iArray[16][2]=60;
        iArray[16][3]=0;  
        
        iArray[17]=new Array();
        iArray[17][0]="�����־";
        iArray[17][1]="60px";
        iArray[17][2]=60;
        iArray[17][3]=3;  
        
        iArray[18]=new Array();
        iArray[18][0]="�����־";
        iArray[18][1]="80px";
        iArray[18][2]=80;
        iArray[18][3]=0; 
                
                                                
        ContReferGrid = new MulLineEnter("fm","ContReferGrid");
        ContReferGrid.mulLineCount = 0;
        ContReferGrid.displayTitle = 1;
        ContReferGrid.locked = 0;
//      ContReferGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
        ContReferGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        ContReferGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        ContReferGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        ContReferGrid.selBoxEventFuncName = "getContReferGrid"; //��������
        ContReferGrid.selBoxEventFuncParm ="ContDeal";          //����        
        ContReferGrid.loadMulLine(iArray);
        
    }
    catch(ex)
    {
        alter("LLClaimContDealInit.jsp-->initContReferGrid�����з����쳣:��ʼ���������!");
    }
}


/**=========================================================================
    �ⰸ�漰�ĺ�ͬ
   =========================================================================
*/
function initContPolReferGrid()
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
        iArray[1][0]="�����ͬ����";
        iArray[1][1]="100px";
        iArray[1][2]=100;
        iArray[1][3]=3;

        
        iArray[2]=new Array();
        iArray[2][0]="���屣�����ֺ�";
        iArray[2][1]="120px";
        iArray[2][2]=120;
        iArray[2][3]=0;        
        
        iArray[3]=new Array();
        iArray[3][0]="��ͬ����";
        iArray[3][1]="120px";
        iArray[3][2]=120;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="�������ֺ�";
        iArray[4][1]="120px";
        iArray[4][2]=120;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="�ܵ�����";
        iArray[5][1]="80px";
        iArray[5][2]=80;
        iArray[5][3]=3;
                

        iArray[6]=new Array();
        iArray[6][0]="���ձ�������";
        iArray[6][1]="0px";
        iArray[6][2]=10;
        iArray[6][3]=3; 
        
        
        iArray[7]=new Array();
        iArray[7][0]="���ֱ���";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=0;  
        
        iArray[8]=new Array();
        iArray[8][0]="�����˿ͻ���";
        iArray[8][1]="80px";
        iArray[8][2]=80;
        iArray[8][3]=0;
        
        
        iArray[9]=new Array();
        iArray[9][0]="����������";
        iArray[9][1]="80px";
        iArray[9][2]=80;
        iArray[9][3]=0;  
        
        iArray[10]=new Array();
        iArray[10][0]="�������Ա�";
        iArray[10][1]="0px";
        iArray[10][2]=10;
        iArray[10][3]=3;
                
        iArray[11]=new Array();
        iArray[11][0]="�����˳�������";
        iArray[11][1]="0px";
        iArray[11][2]=10;
        iArray[11][3]=3;  
        
        iArray[12]=new Array();
        iArray[12][0]="Ͷ������";
        iArray[12][1]="80px";
        iArray[12][2]=80;
        iArray[12][3]=3;

        iArray[13]=new Array();
        iArray[13][0]="ǩ������";
        iArray[13][1]="80px";
        iArray[13][2]=80;
        iArray[13][3]=0;        

        iArray[14]=new Array();
        iArray[14][0]="ǩ������";
        iArray[14][1]="80px";
        iArray[14][2]=80;
        iArray[14][3]=0;  

        iArray[15]=new Array();
        iArray[15][0]="������Ч��";
        iArray[15][1]="80px";
        iArray[15][2]=80;
        iArray[15][3]=0;  

        iArray[16]=new Array();
        iArray[16][0]="Ͷ����־";
        iArray[16][1]="60px";
        iArray[16][2]=60;
        iArray[16][3]=0;  
        
                
                                                
        ContPolReferGrid = new MulLineEnter("fm","ContPolReferGrid");
        ContPolReferGrid.mulLineCount = 0;
        ContPolReferGrid.displayTitle = 1;
        ContPolReferGrid.locked = 0;
//      ContPolReferGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
        ContPolReferGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        ContPolReferGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        ContPolReferGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        ContPolReferGrid.selBoxEventFuncName = "getContPolReferGrid"; //��������
        ContPolReferGrid.selBoxEventFuncParm ="ContDeal";          //����        
        ContPolReferGrid.loadMulLine(iArray);
        
    }
    catch(ex)
    {
        alter("LLClaimContDealInit.jsp-->initContReferGrid�����з����쳣:��ʼ���������!");
    }
}

/**=========================================================================
    ��ͬ��ֹ��Ϣ
   =========================================================================
*/
function initLLContStateGrid()
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
        iArray[1][0]="��ͬ��";
        iArray[1][1]="120px";
        iArray[1][2]=120;
        iArray[1][3]=0;

        
        iArray[2]=new Array();
        iArray[2][0]="�����˱��";
        iArray[2][1]="150px";
        iArray[2][2]=150;
        iArray[2][3]=3;        
        
        iArray[3]=new Array();
        iArray[3][0]="���ֱ�����";
        iArray[3][1]="120px";
        iArray[3][2]=120;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="״̬����";
        iArray[4][1]="150px";
        iArray[4][2]=150;
        iArray[4][3]=3;
        
        iArray[5]=new Array();
        iArray[5][0]="";
        iArray[5][1]="100px";
        iArray[5][2]=100;
        iArray[5][3]=3;
                

        iArray[6]=new Array();
        iArray[6][0]="״̬";
        iArray[6][1]="80px";
        iArray[6][2]=80;
        iArray[6][3]=0; 
        
                              
        iArray[7]=new Array();
        iArray[7][0]="��Чʱ��";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=0;  
        
        iArray[8]=new Array();
        iArray[8][0]="����ʱ��";
        iArray[8][1]="80px";
        iArray[8][2]=80;
        iArray[8][3]=0;
                                        
        iArray[9]=new Array();
        iArray[9][0]="";
        iArray[9][1]="80px";
        iArray[9][2]=80;
        iArray[9][3]=3; 
        
        iArray[10]=new Array();
        iArray[10][0]="��ͬ�������";
        iArray[10][1]="100px";
        iArray[10][2]=100;
        iArray[10][3]=0;
                
        iArray[11]=new Array();
        iArray[11][0]="";
        iArray[11][1]="0px";
        iArray[11][2]=10;
        iArray[11][3]=3;
        
        iArray[12]=new Array();
        iArray[12][0]="���⴦��״̬";
        iArray[12][1]="100px";
        iArray[12][2]=100;
        iArray[12][3]=0;
        
                                                                                
        LLContStateGrid = new MulLineEnter("fm","LLContStateGrid");
        LLContStateGrid.mulLineCount = 0;
        LLContStateGrid.displayTitle = 1;
        LLContStateGrid.locked = 0;
//      LLContStateGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
        LLContStateGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        LLContStateGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        LLContStateGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
//        LLContStateGrid.selBoxEventFuncName = "getContReferGrid"; //��������
//        LLContStateGrid.selBoxEventFuncParm ="ContDeal";          //����        
        LLContStateGrid.loadMulLine(iArray);
        
    }
    catch(ex)
    {
        alter("LLClaimContDealInit.jsp-->initContCalResultGrid�����з����쳣:��ʼ���������!");
    }
}


/**=========================================================================
    �ⰸ�漰�ĺ�ͬ����Ľ��
   =========================================================================
*/
function initContCalResultGrid()
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
        iArray[1][3]=0;

        
        iArray[2]=new Array();
        iArray[2][0]="ҵ����������";
        iArray[2][1]="150px";
        iArray[2][2]=150;
        iArray[2][3]=3;        
        
        iArray[3]=new Array();
        iArray[3][0]="��ҵ������";
        iArray[3][1]="60px";
        iArray[3][2]=60;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="��ҵ����������";
        iArray[4][1]="150px";
        iArray[4][2]=150;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="��������";
        iArray[5][1]="100px";
        iArray[5][2]=100;
        iArray[5][3]=0;
                

        iArray[6]=new Array();
        iArray[6][0]="���ֱ���";
        iArray[6][1]="80px";
        iArray[6][2]=80;
        iArray[6][3]=0; 
        
                              
        iArray[7]=new Array();
        iArray[7][0]="��������";
        iArray[7][1]="0px";
        iArray[7][2]=10;
        iArray[7][3]=3;  
        
        iArray[8]=new Array();
        iArray[8][0]="������";
        iArray[8][1]="80px";
        iArray[8][2]=80;
        iArray[8][3]=0;
        
        
                                                                        
        ContCalResultGrid = new MulLineEnter("fm","ContCalResultGrid");
        ContCalResultGrid.mulLineCount = 0;
        ContCalResultGrid.displayTitle = 1;
        ContCalResultGrid.locked = 0;
//      ContCalResultGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
        ContCalResultGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        ContCalResultGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        ContCalResultGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
//        ContCalResultGrid.selBoxEventFuncName = "getContReferGrid"; //��������
//        ContCalResultGrid.selBoxEventFuncParm ="ContDeal";          //����        
        ContCalResultGrid.loadMulLine(iArray);
        
    }
    catch(ex)
    {
        alter("LLClaimContDealInit.jsp-->initContCalResultGrid�����з����쳣:��ʼ���������!");
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
