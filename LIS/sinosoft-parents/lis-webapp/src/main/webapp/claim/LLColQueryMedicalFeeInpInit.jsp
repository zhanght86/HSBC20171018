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

var mCurrentDate = "";

//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
	var sysdatearr=easyExecSql("select to_date(sysdate) from dual"); //ȡ��������ʱ����Ϊϵͳ��ǰ����
	mCurrentDate=sysdatearr[0][0];
    
    document.all('claimNo').value = nullToEmpty("<%= tclaimNo %>");
    document.all('caseNo').value = nullToEmpty("<%= tcaseNo %>");
    document.all('custNo').value = nullToEmpty("<%= tcustNo %>");
    document.all('accDate').value = nullToEmpty("<%= tAccDate1 %>"); //�¹�����
    document.all('medAccDate').value = nullToEmpty("<%= tMedAccDate %>"); //ҽ�Ƴ�������
    document.all('otherAccDate').value = nullToEmpty("<%= tOtherAccDate %>"); //������������

    //��ҽ�Ƴ������ں������������ڶ���Ϊ��ʱ,ȡ����С���Ǹ�������Ϊ�Ƚ�����
    if((!(document.all('medAccDate').value==null||document.all('medAccDate').value==""))&&(!(document.all('otherAccDate').value==null||document.all('otherAccDate').value.value=="")))
    {
    	
    	//ȡ���������н�С���Ǹ�
        if (dateDiff(document.all('otherAccDate').value,document.all('medAccDate').value,'D') > 0)
        {
        	checkAccDate=document.all('otherAccDate').value;
        }
    	else
    	{
    		checkAccDate=document.all('medAccDate').value;
    	}
    }
    else if(!(document.all('medAccDate').value==null||document.all('medAccDate').value=="")){
    	
    	checkAccDate=document.all('medAccDate').value;
    }
    else
    {
    	checkAccDate=document.all('otherAccDate').value;
    }

    //alert("����Ƚϵĳ�������:"+checkAccDate);
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
        initInpBox();                   //��ʼ�����
        initMedFeeClinicInpGrid();      //������Ϣ¼��
        initMedFeeInHosInpGrid();       //סԺ��Ϣ¼��
        initMedFeeCompensateInpGrid();  //���ò�����Ŀ��Ϣ¼��
        initMedFeeCaseInfoGrid();       //��������(ԭ�˲�)
        initMedFeeOperGrid();           //����
        initMedFeeOtherGrid();          //����
        initMedFeeThreeGrid();          //�籣����������
        queryHospitalxx("<%= tcaseNo %>","<%= tcustNo %>"); //��ѯ���������Ѿ�¼���ҽԺ��Ϣ
    }
    catch(re)
    {
        alert("LLMedicalFeeInpInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
   
    //������Ϣ
    fm.ClinicHosID.value = '';
    fm.ClinicHosName.value = '';
    fm.ClinicHosGrade.value = '';
          
    fm.ClinicMedFeeType.value = '';
    fm.ClinicMedFeeTypeName.value = '';
    fm.ClinicMedFeeSum.value = '';
        
    fm.ClinicStartDate.value = '';
    fm.ClinicEndDate.value = '';
    fm.ClinicDayCount1.value = '';  
            
//    MedFeeInHosInp.style.display="none";
  }
  catch(ex)
  {
    alert("LLMedicalFeeInpInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

/**=========================================================================
    ���ﵥ֤¼����Ϣ
   =========================================================================
*/
function initMedFeeClinicInpGrid()
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
        iArray[1][0]="ҽԺ����";
        iArray[1][1]="0px";
        iArray[1][2]=10;
        iArray[1][3]=3;

        
        iArray[2]=new Array();
        iArray[2][0]="ҽԺ����";
        iArray[2][1]="140px";
        iArray[2][2]=140;
        iArray[2][3]=0;        
        
        iArray[3]=new Array();
        iArray[3][0]="ҽԺ�ȼ�";
        iArray[3][1]="40px";
        iArray[3][2]=10;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="��ʼ����";
        iArray[4][1]="80px";
        iArray[4][2]=10;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="��������";
        iArray[5][1]="80px";
        iArray[5][2]=10;
        iArray[5][3]=0;
                

        iArray[6]=new Array();
        iArray[6][0]="����";
        iArray[6][1]="30px";
        iArray[6][2]=20;
        iArray[6][3]=0;                

        iArray[7]=new Array();
        iArray[7][0]="���ô���";
        iArray[7][1]="0px";
        iArray[7][2]=10;
        iArray[7][3]=3;  
        
        iArray[8]=new Array();
        iArray[8][0]="��������";
        iArray[8][1]="80px";
        iArray[8][2]=10;
        iArray[8][3]=0;
        
        
        iArray[9]=new Array();
        iArray[9][0]="���ý��";
        iArray[9][1]="40px";
        iArray[9][2]=40;
        iArray[9][3]=0;  
        
        iArray[10]=new Array();
        iArray[10][0]="";
        iArray[10][1]="0px";
        iArray[10][2]=10;
        iArray[10][3]=3;
//                
        iArray[11]=new Array();
        iArray[11][0]="�ⰸ��";
        iArray[11][1]="0px";
        iArray[11][2]=10;
        iArray[11][3]=3;  
        
        iArray[12]=new Array();
        iArray[12][0]="�ְ���";
        iArray[12][1]="0px";
        iArray[12][2]=10;
        iArray[12][3]=3;
        
        iArray[13]=new Array();
        iArray[13][0]="�����˿ͻ���";
        iArray[13][1]="0px";
        iArray[13][2]=10;
        iArray[13][3]=3;        

        iArray[14]=new Array();
        iArray[14][0]="�˵���";
        iArray[14][1]="0px";
        iArray[14][2]=10;
        iArray[14][3]=3;  

        iArray[15]=new Array();
        iArray[15][0]="�˵�������ϸ��";
        iArray[15][1]="0px";
        iArray[15][2]=10;
        iArray[15][3]=3;  
                                
        iArray[16]=new Array();
        iArray[16][0]="�Է�/�Ը����";
        iArray[16][1]="65px";
        iArray[16][2]=50;
        iArray[16][3]=0;  

        iArray[17]=new Array();
        iArray[17][0]="�籣��־";
        iArray[17][1]="40px";
        iArray[17][2]=10;
        iArray[17][3]=0;  

        iArray[18]=new Array();
        iArray[18][0]="�����־";
        iArray[18][1]="0px";
        iArray[18][2]=0;
        iArray[18][3]=0;  

        iArray[19]=new Array();
        iArray[19][0]="�����¹���������";
        iArray[19][1]="65px";
        iArray[19][2]=20;
        iArray[19][3]=0;  

        iArray[20]=new Array();
        iArray[20][0]="�ۼ�ԭ��";
        iArray[20][1]="60px";
        iArray[20][2]=20;
        iArray[20][3]=0;  

        iArray[21]=new Array();
        iArray[21][0]="�ۼ�ԭ�����";
        iArray[21][1]="100px";
        iArray[21][2]=20;
        iArray[21][3]=3;  

        iArray[22]=new Array();
        iArray[22][0]="��ע";
        iArray[22][1]="100px";
        iArray[22][2]=20;
        iArray[22][3]=0;  
                        
        MedFeeClinicInpGrid = new MulLineEnter("document","MedFeeClinicInpGrid");
        MedFeeClinicInpGrid.mulLineCount = 5;
        MedFeeClinicInpGrid.displayTitle = 1;
        MedFeeClinicInpGrid.locked = 0;
//      MedFeeClinicInpGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
        MedFeeClinicInpGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        MedFeeClinicInpGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        MedFeeClinicInpGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        MedFeeClinicInpGrid.selBoxEventFuncName = "getMedFeeClinicInpGrid"; //��������
//        MedFeeClinicInpGrid.selBoxEventFuncParm ="";          //����        
        MedFeeClinicInpGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

/**=========================================================================
    סԺ¼����Ϣ
   =========================================================================
*/
function initMedFeeInHosInpGrid()
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
        iArray[1][0]="ҽԺ����";
        iArray[1][1]="0px";
        iArray[1][2]=10;
        iArray[1][3]=3;

        
        iArray[2]=new Array();
        iArray[2][0]="ҽԺ����";
        iArray[2][1]="150px";
        iArray[2][2]=150;
        iArray[2][3]=0;        
        
        iArray[3]=new Array();
        iArray[3][0]="ҽԺ�ȼ�";
        iArray[3][1]="40px";
        iArray[3][2]=10;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="��ʼ����";
        iArray[4][1]="80px";
        iArray[4][2]=10;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="��������";
        iArray[5][1]="80px";
        iArray[5][2]=10;
        iArray[5][3]=0;
                

        iArray[6]=new Array();
        iArray[6][0]="ʵ��סԺ����";
        iArray[6][1]="50px";
        iArray[6][2]=10;
        iArray[6][3]=0;                

        iArray[7]=new Array();
        iArray[7][0]="���ô���";
        iArray[7][1]="0px";
        iArray[7][2]=10;
        iArray[7][3]=3;  
        
        iArray[8]=new Array();
        iArray[8][0]="��������";
        iArray[8][1]="80px";
        iArray[8][2]=10;
        iArray[8][3]=0;
        
        
        iArray[9]=new Array();
        iArray[9][0]="���ý��";
        iArray[9][1]="50px";
        iArray[9][2]=10;
        iArray[9][3]=0;  

        iArray[10]=new Array();
        iArray[10][0]="�Է�/�Ը����";
        iArray[10][1]="65px";
        iArray[10][2]=50;
        iArray[10][3]=0;  
              
        iArray[11]=new Array();
        iArray[11][0]="�ⰸ��";
        iArray[11][1]="0px";
        iArray[11][2]=10;
        iArray[11][3]=3;  
        
        iArray[12]=new Array();
        iArray[12][0]="�ְ���";
        iArray[12][1]="0px";
        iArray[12][2]=10;
        iArray[12][3]=3;
        
        iArray[13]=new Array();
        iArray[13][0]="�����˿ͻ���";
        iArray[13][1]="0px";
        iArray[13][2]=10;
        iArray[13][3]=3; 
        
        iArray[14]=new Array();
        iArray[14][0]="�˵���";
        iArray[14][1]="0px";
        iArray[14][2]=10;
        iArray[14][3]=3;  

        iArray[15]=new Array();
        iArray[15][0]="�˵�������ϸ��";
        iArray[15][1]="0px";
        iArray[15][2]=10;
        iArray[15][3]=3;  
        
        iArray[16]=new Array();
        iArray[16][0]="�籣��־";
        iArray[16][1]="40px";
        iArray[16][2]=10;
        iArray[16][3]=0;  

        iArray[17]=new Array();
        iArray[17][0]="�����־";
        iArray[17][1]="0px";
        iArray[17][2]=0;
        iArray[17][3]=0;  

        iArray[18]=new Array();
        iArray[18][0]="�����¹���������";
        iArray[18][1]="65px";
        iArray[18][2]=20;
        iArray[18][3]=0;  

        iArray[19]=new Array();
        iArray[19][0]="�ۼ�ԭ��";
        iArray[19][1]="60px";
        iArray[19][2]=20;
        iArray[19][3]=0;  

        iArray[20]=new Array();
        iArray[20][0]="�ۼ�ԭ�����";
        iArray[20][1]="100px";
        iArray[20][2]=20;
        iArray[20][3]=3;  

        iArray[21]=new Array();
        iArray[21][0]="��ע";
        iArray[21][1]="100px";
        iArray[21][2]=20;
        iArray[21][3]=0;  
        
        MedFeeInHosInpGrid = new MulLineEnter("document","MedFeeInHosInpGrid");
        MedFeeInHosInpGrid.mulLineCount = 5;
        MedFeeInHosInpGrid.displayTitle = 1;
        MedFeeInHosInpGrid.locked = 0;
//      MedFeeInHosInpGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
        MedFeeInHosInpGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        MedFeeInHosInpGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        MedFeeInHosInpGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        MedFeeInHosInpGrid.selBoxEventFuncName = "getMedFeeInHosInpGrid"; //��������
//        MedFeeInHosInpGrid.selBoxEventFuncParm ="";          //����        
        MedFeeInHosInpGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}




/**=========================================================================
    �˲�¼����Ϣ
   =========================================================================
*/
function initMedFeeCaseInfoGrid()
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
        iArray[1][0]="�м�����";
        iArray[1][1]="80px";
        iArray[1][2]=10;
        iArray[1][3]=0;

        
        iArray[2]=new Array();
        iArray[2][0]="�˲м���";
        iArray[2][1]="50px";
        iArray[2][2]=10;
        iArray[2][3]=3;        
        
        iArray[3]=new Array();
        iArray[3][0]="�˲м�������";
        iArray[3][1]="100px";
        iArray[3][2]=10;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="�˲д���";
        iArray[4][1]="80px";
        iArray[4][2]=10;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="�˲д�������";
        iArray[5][1]="100px";
        iArray[5][2]=10;
        iArray[5][3]=0;
                
        iArray[6]=new Array();
        iArray[6][0]="�м���������";
        iArray[6][1]="80px";
        iArray[6][2]=10;
        iArray[6][3]=0;                

        iArray[7]=new Array();
        iArray[7][0]="�����������";
        iArray[7][1]="80px";
        iArray[7][2]=10;
        iArray[7][3]=0;  
        
        iArray[8]=new Array();
        iArray[8][0]="ʵ�ʸ�������";
        iArray[8][1]="80px";
        iArray[8][2]=10;
        iArray[8][3]=3;
        
        iArray[9]=new Array();
        iArray[9][0]="�ⰸ��";
        iArray[9][1]="50px";
        iArray[9][2]=10;
        iArray[9][3]=3;  
        
        iArray[10]=new Array();
        iArray[10][0]="�ְ���";
        iArray[10][1]="0px";
        iArray[10][2]=10;
        iArray[10][3]=3;
//                
        iArray[11]=new Array();
        iArray[11][0]="���";
        iArray[11][1]="0px";
        iArray[11][2]=10;
        iArray[11][3]=3;  
        
        iArray[12]=new Array();
        iArray[12][0]="�����˿ͻ���";
        iArray[12][1]="0px";
        iArray[12][2]=10;
        iArray[12][3]=3;
        
        iArray[13]=new Array();
        iArray[13][0]="������������";
        iArray[13][1]="100px";
        iArray[13][2]=10;
        iArray[13][3]=0;
        
        iArray[14]=new Array();
        iArray[14][0]="����ʱ��";
        iArray[14][1]="80px";
        iArray[14][2]=10;
        iArray[14][3]=0;  
        
        MedFeeCaseInfoGrid = new MulLineEnter("document","MedFeeCaseInfoGrid");
        MedFeeCaseInfoGrid.mulLineCount = 5;
        MedFeeCaseInfoGrid.displayTitle = 1;
        MedFeeCaseInfoGrid.locked = 0;
//      MedFeeCaseInfoGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
        MedFeeCaseInfoGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        MedFeeCaseInfoGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        MedFeeCaseInfoGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        MedFeeCaseInfoGrid.selBoxEventFuncName = "getMedFeeCaseInfoGrid"; //��������
//        MedFeeCaseInfoGrid.selBoxEventFuncParm ="divLLInqCourse";          //����        
        MedFeeCaseInfoGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

/**=========================================================================
    �������ּ�����Ϣ
   =========================================================================
*/
function initMedFeeOperGrid()
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
        iArray[1][0]="����";
        iArray[1][1]="120px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        
        iArray[2]=new Array();
        iArray[2][0]="����";
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;        
        
        iArray[3]=new Array();
        iArray[3][0]="����";
        iArray[3][1]="150px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="����";
        iArray[4][1]="80px";
        iArray[4][2]=100;
        iArray[4][3]=3;
        
        iArray[5]=new Array();
        iArray[5][0]="�ȼ�";
        iArray[5][1]="80px";
        iArray[5][2]=100;
        iArray[5][3]=3;
                

        iArray[6]=new Array();
        iArray[6][0]="���";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=0;     

        iArray[7]=new Array();
        iArray[7][0]="�Է�/�Ը����";
        iArray[7][1]="75px";
        iArray[7][2]=75;
        iArray[7][3]=0;             

        iArray[8]=new Array();
        iArray[8][0]="���пڱ�־";
        iArray[8][1]="80px";
        iArray[8][2]=10;
        iArray[8][3]=3;  
             
        iArray[9]=new Array();
        iArray[9][0]="�ⰸ��";
        iArray[9][1]="80px";
        iArray[9][2]=10;
        iArray[9][3]=3;
        
        iArray[10]=new Array();
        iArray[10][0]="�ְ���";
        iArray[10][1]="50px";
        iArray[10][2]=10;
        iArray[10][3]=3;  
        
        iArray[11]=new Array();
        iArray[11][0]="���";
        iArray[11][1]="0px";
        iArray[11][2]=10;
        iArray[11][3]=3;
                
        iArray[12]=new Array();
        iArray[12][0]="�����˿ͻ���";
        iArray[12][1]="0px";
        iArray[12][2]=10;
        iArray[12][3]=3;  
        
        iArray[13]=new Array();
        iArray[13][0]="ҽ�ƻ�������";
        iArray[13][1]="150px";
        iArray[13][2]=100;
        iArray[13][3]=0;
        
        iArray[14]=new Array();
        iArray[14][0]="ȷ������";
        iArray[14][1]="100px";
        iArray[14][2]=100;
        iArray[14][3]=0;  
        
        MedFeeOperGrid = new MulLineEnter("document","MedFeeOperGrid");
        MedFeeOperGrid.mulLineCount = 5;
        MedFeeOperGrid.displayTitle = 1;
        MedFeeOperGrid.locked = 0;
//      MedFeeOperGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
        MedFeeOperGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        MedFeeOperGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        MedFeeOperGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        MedFeeOperGrid.selBoxEventFuncName = "getMedFeeOperGrid"; //��������
//        MedFeeOperGrid.selBoxEventFuncParm ="divLLInqCourse";          //����        
        MedFeeOperGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

/**=========================================================================
    ����¼����Ϣ
   =========================================================================
*/
function initMedFeeOtherGrid()
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
        iArray[1][1]="100px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="���ַ��ô���";
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;        
        
        iArray[3]=new Array();
        iArray[3][0]="���ַ�������";
        iArray[3][1]="120px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="���ַ��ý��";
        iArray[4][1]="75px";
        iArray[4][2]=65;
        iArray[4][3]=0;


        iArray[5]=new Array();
        iArray[5][0]="�Է�/�Ը����";
        iArray[5][1]="80px";
        iArray[5][2]=80;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="�ⰸ��";
        iArray[6][1]="80px";
        iArray[6][2]=10;
        iArray[6][3]=3;
                

        iArray[7]=new Array();
        iArray[7][0]="�ְ���";
        iArray[7][1]="30px";
        iArray[7][2]=10;
        iArray[7][3]=3;                

        iArray[8]=new Array();
        iArray[8][0]="���";
        iArray[8][1]="0px";
        iArray[8][2]=10;
        iArray[8][3]=3;  
        
        iArray[9]=new Array();
        iArray[9][0]="�����˿ͻ���";
        iArray[9][1]="80px";
        iArray[9][2]=10;
        iArray[9][3]=3;
        
        iArray[10]=new Array();
        iArray[10][0]="�����������";
        iArray[10][1]="120px";
        iArray[10][2]=10;
        iArray[10][3]=0;  
        
        iArray[11]=new Array();
        iArray[11][0]="��ʼ����";
        iArray[11][1]="80px";
        iArray[11][2]=100;
        iArray[11][3]=0;
                
        iArray[12]=new Array();
        iArray[12][0]="��������";
        iArray[12][1]="80px";
        iArray[12][2]=100;
        iArray[12][3]=0;  

        iArray[13]=new Array();
        iArray[13][0]="�ۼ�ԭ��";
        iArray[13][1]="60px";
        iArray[13][2]=20;
        iArray[13][3]=0;  

        iArray[14]=new Array();
        iArray[14][0]="�ۼ�ԭ�����";
        iArray[14][1]="100px";
        iArray[14][2]=20;
        iArray[14][3]=3;  

        iArray[15]=new Array();
        iArray[15][0]="��ע";
        iArray[15][1]="100px";
        iArray[15][2]=20;
        iArray[15][3]=0; 
//        
//        iArray[13]=new Array();
//        iArray[13][0]="�����˿ͻ���";
//        iArray[13][1]="0px";
//        iArray[13][2]=10;
//        iArray[13][3]=3;  
        
        MedFeeOtherGrid = new MulLineEnter("document","MedFeeOtherGrid");
        MedFeeOtherGrid.mulLineCount = 5;
        MedFeeOtherGrid.displayTitle = 1;
        MedFeeOtherGrid.locked = 0;
//      MedFeeOtherGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
        MedFeeOtherGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        MedFeeOtherGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        MedFeeOtherGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        MedFeeOtherGrid.selBoxEventFuncName = "getMedFeeOtherGrid"; //��������
//        MedFeeOtherGrid.selBoxEventFuncParm ="divLLInqCourse";          //����        
        MedFeeOtherGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

/**=========================================================================
    �籣����������
   =========================================================================
*/
function initMedFeeThreeGrid()
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
        iArray[1][1]="100px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="���ô���";
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;        
        
        iArray[3]=new Array();
        iArray[3][0]="��������";
        iArray[3][1]="120px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="���ý��";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="�ⰸ��";
        iArray[5][1]="80px";
        iArray[5][2]=10;
        iArray[5][3]=3;
                

        iArray[6]=new Array();
        iArray[6][0]="�ְ���";
        iArray[6][1]="30px";
        iArray[6][2]=10;
        iArray[6][3]=3;                

        iArray[7]=new Array();
        iArray[7][0]="���";
        iArray[7][1]="0px";
        iArray[7][2]=10;
        iArray[7][3]=3;  
        
        iArray[8]=new Array();
        iArray[8][0]="�����˿ͻ���";
        iArray[8][1]="80px";
        iArray[8][2]=10;
        iArray[8][3]=3;
        
        iArray[9]=new Array();
        iArray[9][0]="�����������";
        iArray[9][1]="120px";
        iArray[9][2]=10;
        iArray[9][3]=0;  
        
        iArray[10]=new Array();
        iArray[10][0]="��ע";
        iArray[10][1]="80px";
        iArray[10][2]=100;
        iArray[10][3]=3;
//                
//        iArray[11]=new Array();
//        iArray[11][0]="��������";
//        iArray[11][1]="80px";
//        iArray[11][2]=10;
//        iArray[11][3]=0;  
////        
//        iArray[12]=new Array();
//        iArray[12][0]="�ְ���";
//        iArray[12][1]="0px";
//        iArray[12][2]=10;
//        iArray[12][3]=3;
//        
//        iArray[13]=new Array();
//        iArray[13][0]="�����˿ͻ���";
//        iArray[13][1]="0px";
//        iArray[13][2]=10;
//        iArray[13][3]=3;  
        
        MedFeeThreeGrid = new MulLineEnter("document","MedFeeThreeGrid");
        MedFeeThreeGrid.mulLineCount = 5;
        MedFeeThreeGrid.displayTitle = 1;
        MedFeeThreeGrid.locked = 0;
//      MedFeeThreeGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
        MedFeeThreeGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
        MedFeeThreeGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        MedFeeThreeGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        MedFeeThreeGrid.selBoxEventFuncName = "getMedFeeThreeGrid"; //��������
//        MedFeeThreeGrid.selBoxEventFuncParm ="divLLInqCourse";          //����        
        MedFeeThreeGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

/**=========================================================================
���ò�����Ŀ¼����Ϣ
=========================================================================
*/
function initMedFeeCompensateInpGrid()
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
    iArray[1][0]="ҽԺ����";
    iArray[1][1]="0px";
    iArray[1][2]=10;
    iArray[1][3]=3;

    
    iArray[2]=new Array();
    iArray[2][0]="ҽԺ����";
    iArray[2][1]="150px";
    iArray[2][2]=150;
    iArray[2][3]=0;        
    
    iArray[3]=new Array();
    iArray[3][0]="ҽԺ�ȼ�";
    iArray[3][1]="40px";
    iArray[3][2]=10;
    iArray[3][3]=0;
    
    iArray[4]=new Array();
    iArray[4][0]="��ʼ����";
    iArray[4][1]="80px";
    iArray[4][2]=10;
    iArray[4][3]=0;
    
    iArray[5]=new Array();
    iArray[5][0]="��������";
    iArray[5][1]="80px";
    iArray[5][2]=10;
    iArray[5][3]=0;
            

    iArray[6]=new Array();
    iArray[6][0]="����";
    iArray[6][1]="30px";
    iArray[6][2]=10;
    iArray[6][3]=0;                

    iArray[7]=new Array();
    iArray[7][0]="���ô���";
    iArray[7][1]="0px";
    iArray[7][2]=10;
    iArray[7][3]=3;  
    
    iArray[8]=new Array();
    iArray[8][0]="��������";
    iArray[8][1]="120px";
    iArray[8][2]=120;
    iArray[8][3]=0;
    
    
    iArray[9]=new Array();
    iArray[9][0]="���ý��";
    iArray[9][1]="50px";
    iArray[9][2]=10;
    iArray[9][3]=0;  
    
    iArray[10]=new Array();
    iArray[10][0]="";
    iArray[10][1]="0px";
    iArray[10][2]=10;
    iArray[10][3]=3;
//            
    iArray[11]=new Array();
    iArray[11][0]="�ⰸ��";
    iArray[11][1]="0px";
    iArray[11][2]=10;
    iArray[11][3]=3;  
    
    iArray[12]=new Array();
    iArray[12][0]="�ְ���";
    iArray[12][1]="0px";
    iArray[12][2]=10;
    iArray[12][3]=3;
    
    iArray[13]=new Array();
    iArray[13][0]="�����˿ͻ���";
    iArray[13][1]="0px";
    iArray[13][2]=10;
    iArray[13][3]=3; 
    
    iArray[14]=new Array();
    iArray[14][0]="�˵���";
    iArray[14][1]="0px";
    iArray[14][2]=10;
    iArray[14][3]=3;  

    iArray[15]=new Array();
    iArray[15][0]="�˵�������ϸ��";
    iArray[15][1]="0px";
    iArray[15][2]=10;
    iArray[15][3]=3;  
    
    iArray[16]=new Array();
    iArray[16][0]="�Է�/�Ը����";
    iArray[16][1]="65px";
    iArray[16][2]=50;
    iArray[16][3]=0;  

    iArray[17]=new Array();
    iArray[17][0]="�籣��־";
    iArray[17][1]="40px";
    iArray[17][2]=10;
    iArray[17][3]=0;  

    iArray[18]=new Array();
    iArray[18][0]="�����־";
    iArray[18][1]="0px";
    iArray[18][2]=0;
    iArray[18][3]=0;  

    iArray[19]=new Array();
    iArray[19][0]="�����¹���������";
    iArray[19][1]="65px";
    iArray[19][2]=20;
    iArray[19][3]=0; 

    iArray[20]=new Array();
    iArray[20][0]="�ۼ�ԭ��";
    iArray[20][1]="60px";
    iArray[20][2]=20;
    iArray[20][3]=0;  

    iArray[21]=new Array();
    iArray[21][0]="�ۼ�ԭ�����";
    iArray[21][1]="100px";
    iArray[21][2]=20;
    iArray[21][3]=3;  

    iArray[22]=new Array();
    iArray[22][0]="��ע";
    iArray[22][1]="100px";
    iArray[22][2]=20;
    iArray[22][3]=0;   
    
    MedFeeCompensateInpGrid = new MulLineEnter("document","MedFeeCompensateInpGrid");
    MedFeeCompensateInpGrid.mulLineCount = 5;
    MedFeeCompensateInpGrid.displayTitle = 1;
    MedFeeCompensateInpGrid.locked = 0;
//  MedFeeCompensateInpGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
    MedFeeCompensateInpGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
    MedFeeCompensateInpGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
    MedFeeCompensateInpGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
    MedFeeCompensateInpGrid.selBoxEventFuncName = "getMedFeeCompensateInpGrid"; //��������
//    MedFeeCompensateInpGrid.selBoxEventFuncParm ="";          //����        
    MedFeeCompensateInpGrid.loadMulLine(iArray);
}
catch(ex)
{
    alert(ex);
}
}
</script>

