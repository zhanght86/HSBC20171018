<%
//Name: LLMedicalFeeInpInit.jsp
//function: 
//author: ����
//Date: 2005.05.13
%>

<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>


<script language="JavaScript">

//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
    document.all('claimNo').value = nullToEmpty("<%= tClmNo %>");
    document.all('caseNo').value = nullToEmpty("<%= tCaseNo %>");
    document.all('custNo').value = nullToEmpty("<%= tCustNo %>");
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

/**
    Form����ҳ��ʱ���г�ʼ��
*/
function initForm()
{
    try
    {
        initParam();
        initInpBox();                   //��ʼ�����
        initMedFeeCalGrid();            //��ʼ�����ü�����Ϣ
        showMedFeeCalGrid();            //��ʾ���ü�����Ϣ
    }
    catch(re)
    {
        alert("LLMedicalFeeInpInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}


/**
    ҳ���ʼ��
*/
function initInpBox()
{
    try
    {
    }
    catch(ex)
    {
        alert("LLClaimRegisterMedFeeCalInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }
}


/**
    ���ﵥ֤¼����Ϣ
*/
function initMedFeeCalGrid()
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
        iArray[1][0]="������";
        iArray[1][1]="120px";
        iArray[1][2]=120;
        iArray[1][3]=0;

        
        iArray[2]=new Array();
        iArray[2][0]="��������";
        iArray[2][1]="60px";
        iArray[2][2]=60;
        iArray[2][3]=0;        
        
        iArray[3]=new Array();
        iArray[3][0]="����";
        iArray[3][1]="60px";
        iArray[3][2]=60;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="��������";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="��������";
        iArray[5][1]="50px";
        iArray[5][2]=50;
        iArray[5][3]=0;
                

        iArray[6]=new Array();
        iArray[6][0]="��������";
        iArray[6][1]="80px";
        iArray[6][2]=80;
        iArray[6][3]=0;                

        iArray[7]=new Array();
        iArray[7][0]="���";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=3;  
        
        iArray[8]=new Array();
        iArray[8][0]="ҽԺ���";
        iArray[8][1]="60px";
        iArray[8][2]=60;
        iArray[8][3]=3;
        
        
        iArray[9]=new Array();
        iArray[9][0]="ҽԺ����";
        iArray[9][1]="80px";
        iArray[9][2]=80;
        iArray[9][3]=0;  
        
        iArray[10]=new Array();
        iArray[10][0]="��ʼʱ��";
        iArray[10][1]="80px";
        iArray[10][2]=80;
        iArray[10][3]=0;
                
        iArray[11]=new Array();
        iArray[11][0]="����ʱ��";
        iArray[11][1]="80px";
        iArray[11][2]=80;
        iArray[11][3]=0;  
        
                        
        iArray[12]=new Array();
        iArray[12][0]="����";
        iArray[12][1]="30px";
        iArray[12][2]=30;
        iArray[12][3]=0;
        
        iArray[13]=new Array();
      iArray[13][0]="����";      	   		//����
      iArray[13][1]="80px";            			//�п�
      iArray[13][2]=20;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[14]=new Array();
        iArray[14][0]="ԭʼ���";
        iArray[14][1]="80px";
        iArray[14][2]=40;
        iArray[14][3]=0;        
                
        iArray[15]=new Array();
        iArray[15][0]="�������";
        iArray[15][1]="80px";
        iArray[15][2]=40;
        iArray[15][3]=0;
        
                        
        iArray[16]=new Array();
        iArray[16][0]="������";
        iArray[16][1]="80px";
        iArray[16][2]=40;
        iArray[16][3]=0;
                
                
        iArray[17]=new Array();
        iArray[17][0]="������";
        iArray[17][1]="80px";
        iArray[17][2]=40;
        iArray[17][3]=0;
                        
                
        MedFeeCalGrid = new MulLineEnter("document","MedFeeCalGrid");
        MedFeeCalGrid.mulLineCount = 5;
        MedFeeCalGrid.displayTitle = 1;
        MedFeeCalGrid.locked = 0;
//      //MedFeeCalGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
        MedFeeCalGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        MedFeeCalGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        MedFeeCalGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        //MedFeeCalGrid.selBoxEventFuncName = "getMedFeeCalGrid"; //��������
        //MedFeeCalGrid.selBoxEventFuncParm ="divLLInqCourse";          //����        
        MedFeeCalGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}




</script>

