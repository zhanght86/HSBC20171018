<%
//�������ƣ�LLClaimEndCaseMissInit.jsp
//�����ܣ�
//�������ڣ�2005-6-6 11:30
//������  ��zl
//���¼�¼��
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
   String tCurrentDate = PubFun.getCurrentDate();
%>    
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">
var CurrentDate="<%=tCurrentDate%>";

//���ղ���
function initParam()
{
    document.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
    document.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
    document.all('ManageCom').value = nullToEmpty("<%= tGlobalInput.ManageCom %>");
    document.all('CurDate').value = CurrentDate;
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

// �����ĳ�ʼ��
function initInpBox()
{ 

  try
  {                                   
  }
  catch(ex)
  {
    alert("��LLClaimEndCaseMissInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
//    setOption("sex","0=��&1=Ů&2=����");        
  }
  catch(ex)
  {
    alert("��LLClaimEndCaseMissInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initParam();
    initInpBox();
    initSelBox();  
    initLLClaimEndCaseGrid();
    initSelfLLClaimEndCaseGrid();
    querySelfGrid();
//	initSelfPolGrid();
//	easyQueryClickSelf();
	
  }
  catch(re)
  {
    alert("LLClaimEndCaseMissInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

//���ҳ��
function emptyForm() 
{
	//emptyFormElements();     //���ҳ�������������COMMON.JS��ʵ��
   
	initSubInsuredGrid();
	initBnfGrid();
	initImpartGrid();
	initSpecGrid();
	initDutyGrid();
	
	//SubInsuredGrid.clearData();
	//BnfGrid.clearData();
	//ImpartGrid.clearData();
	//SpecGrid.clearData();
	//DutyGrid.clearData();
	spanDutyGrid.innerHTML="";
}

// ������Ϣ�б�ĳ�ʼ��
function initLLClaimEndCaseGrid()
{                               
    var iArray = new Array();
    
    try
    {
    iArray[0]=new Array();
    iArray[0][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";                //�п�
    iArray[0][2]=10;                  //�����ֵ
    iArray[0][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="������";             //����
    iArray[1][1]="160px";                //�п�
    iArray[1][2]=160;                  //�����ֵ
    iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="�ⰸ״̬";             //����
    iArray[2][1]="100px";                //�п�
    iArray[2][2]=100;                  //�����ֵ
    iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="�ͻ�����";             //����
    iArray[3][1]="100px";                //�п�
    iArray[3][2]=200;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="�ͻ�����";             //����
    iArray[4][1]="100px";                //�п�
    iArray[4][2]=500;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="�Ա�";             //����
    iArray[5][1]="80px";                //�п�
    iArray[5][2]=200;                  //�����ֵ
    iArray[5][3]=0; 


    iArray[6]=new Array();
    iArray[6][0]="��������";             //����
    iArray[6][1]="100px";                //�п�
    iArray[6][2]=200;                  //�����ֵ
    iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="ǩ��ͬ������";             //����
    iArray[7][1]="100px";                //�п�
    iArray[7][2]=200;                  //�����ֵ
    iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[8]=new Array();
    iArray[8][0]="Missionid";             //����
    iArray[8][1]="80px";                //�п�
    iArray[8][2]=200;                  //�����ֵ
    iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[9]=new Array();
    iArray[9][0]="submissionid";             //����
    iArray[9][1]="100px";                //�п�
    iArray[9][2]=200;                  //�����ֵ
    iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[10]=new Array();
    iArray[10][0]="activityid";             //����
    iArray[10][1]="100px";                //�п�
    iArray[10][2]=200;                  //�����ֵ
    iArray[10][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������            
    
    iArray[11]=new Array();
    iArray[11][0]="����������";             //����
    iArray[11][1]="100px";                //�п�
    iArray[11][2]=200;                  //�����ֵ
    iArray[11][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[12]=new Array();
    iArray[12][0]="����";             //����
    iArray[12][1]="80px";                //�п�
    iArray[12][2]=200;                  //�����ֵ
    iArray[12][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������   

    iArray[13]=new Array();
    iArray[13][0]="��������";             //����
    iArray[13][1]="80px";                //�п�
    iArray[13][2]=200;                  //�����ֵ
    iArray[13][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������   
    
    LLClaimEndCaseGrid = new MulLineEnter( "document" , "LLClaimEndCaseGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LLClaimEndCaseGrid.mulLineCount = 5;   
    LLClaimEndCaseGrid.displayTitle = 1;
    LLClaimEndCaseGrid.locked = 0;
//    LLClaimEndCaseGrid.canChk = 1;
    LLClaimEndCaseGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    LLClaimEndCaseGrid.selBoxEventFuncName ="LLClaimEndCaseGridClick"; //��Ӧ�ĺ���������������
//        LLClaimEndCaseGrid.selBoxEventFuncParm =""; //����Ĳ���,����ʡ�Ը���     
    LLClaimEndCaseGrid.hiddenPlus=1;
    LLClaimEndCaseGrid.hiddenSubtraction=1;
    LLClaimEndCaseGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

// ������Ϣ�б�ĳ�ʼ��
function initSelfLLClaimEndCaseGrid()
{                               
    var iArray = new Array();
    
    try
    {
    iArray[0]=new Array();
    iArray[0][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";                //�п�
    iArray[0][2]=10;                  //�����ֵ
    iArray[0][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="������";             //����
    iArray[1][1]="160px";                //�п�
    iArray[1][2]=160;                  //�����ֵ
    iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="�ⰸ״̬";             //����
    iArray[2][1]="100px";                //�п�
    iArray[2][2]=100;                  //�����ֵ
    iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="�ͻ�����";             //����
    iArray[3][1]="100px";                //�п�
    iArray[3][2]=200;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="�ͻ�����";             //����
    iArray[4][1]="100px";                //�п�
    iArray[4][2]=500;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="�Ա�";             //����
    iArray[5][1]="80px";                //�п�
    iArray[5][2]=200;                  //�����ֵ
    iArray[5][3]=0; 


    iArray[6]=new Array();
    iArray[6][0]="��������";             //����
    iArray[6][1]="100px";                //�п�
    iArray[6][2]=200;                  //�����ֵ
    iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="ǩ��ͬ������";             //����
    iArray[7][1]="100px";                //�п�
    iArray[7][2]=200;                  //�����ֵ
    iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[8]=new Array();
    iArray[8][0]="Missionid";             //����
    iArray[8][1]="80px";                //�п�
    iArray[8][2]=200;                  //�����ֵ
    iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[9]=new Array();
    iArray[9][0]="submissionid";             //����
    iArray[9][1]="100px";                //�п�
    iArray[9][2]=200;                  //�����ֵ
    iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[10]=new Array();
    iArray[10][0]="activityid";             //����
    iArray[10][1]="100px";                //�п�
    iArray[10][2]=200;                  //�����ֵ
    iArray[10][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������            
    
    iArray[11]=new Array();
    iArray[11][0]="����������";             //����
    iArray[11][1]="100px";                //�п�
    iArray[11][2]=200;                  //�����ֵ
    iArray[11][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[12]=new Array();
    iArray[12][0]="����";             //����
    iArray[12][1]="80px";                //�п�
    iArray[12][2]=200;                  //�����ֵ
    iArray[12][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������   

    iArray[13]=new Array();
    iArray[13][0]="��������";             //����
    iArray[13][1]="80px";                //�п�
    iArray[13][2]=200;                  //�����ֵ
    iArray[13][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������   
    
    SelfLLClaimEndCaseGrid = new MulLineEnter( "document" , "SelfLLClaimEndCaseGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    SelfLLClaimEndCaseGrid.mulLineCount = 5;   
    SelfLLClaimEndCaseGrid.displayTitle = 1;
    SelfLLClaimEndCaseGrid.locked = 0;
//    SelfLLClaimEndCaseGrid.canChk = 1;
    SelfLLClaimEndCaseGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    SelfLLClaimEndCaseGrid.selBoxEventFuncName ="SelfLLClaimEndCaseGridClick"; //��Ӧ�ĺ���������������
//        SelfLLClaimEndCaseGrid.selBoxEventFuncParm =""; //����Ĳ���,����ʡ�Ը���    
    SelfLLClaimEndCaseGrid.hiddenPlus=1;
    SelfLLClaimEndCaseGrid.hiddenSubtraction=1;
    SelfLLClaimEndCaseGrid.loadMulLine(iArray);  

    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>
