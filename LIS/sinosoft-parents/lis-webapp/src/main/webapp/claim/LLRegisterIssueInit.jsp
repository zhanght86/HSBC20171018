<%
//�������ƣ�LLIssueMissInit.jsp
//�����ܣ�
//�������ڣ�2005-6-6 11:30
//������  ��zl
//���¼�¼��
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

//���ղ���
function initParam()
{
    document.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
    document.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
    document.all('ManageCom').value = nullToEmpty("<%= tGlobalInput.ManageCom %>");
    document.all('RgtNo').value = nullToEmpty("<%= tRgtNo %>");
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
    alert("��LLIssueMissInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��LLIssueMissInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initParam();
   
    initInpBox();
 
    initSelBox();  

    initLLIssueGrid();
   
   // queryGrid();          wjw 20120518
    
//  initSelfLLReportGrid();
//	initSelfPolGrid();
//	easyQueryClickSelf();
	
  }
  catch(re)
  {
    alert("LLIssueMissInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
function initLLIssueGrid()
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
    iArray[2][0]="�ͻ���";             //����
    iArray[2][1]="100px";                //�п�
    iArray[2][2]=100;                  //�����ֵ
    iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="�������";             //����
    iArray[3][1]="100px";                //�п�
    iArray[3][2]=200;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="��������";             //����
    iArray[4][1]="200px";                //�п�
    iArray[4][2]=500;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="�������������";             //����
    iArray[5][1]="150px";                //�п�
    iArray[5][2]=200;                  //�����ֵ
    iArray[5][3]=0; 

    iArray[6]=new Array();
    iArray[6][0]="�������";             //����
    iArray[6][1]="100px";                //�п�
    iArray[6][2]=200;                  //�����ֵ
    iArray[6][3]=3; 

    iArray[7]=new Array();
    iArray[7][0]="�������������";             //����
    iArray[7][1]="100px";                //�п�
    iArray[7][2]=200;                  //�����ֵ
    iArray[7][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

 /*   iArray[7]=new Array();
    iArray[7][0]="���յص�";             //����
    iArray[7][1]="80px";                //�п�
    iArray[7][2]=200;                  //�����ֵ
    iArray[7][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="������������";             //����
    iArray[8][1]="100px";                //�п�
    iArray[8][2]=200;                  //�����ֵ
    iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[9]=new Array();
    iArray[9][0]="��Ͻ����";             //����
    iArray[9][1]="100px";                //�п�
    iArray[9][2]=200;                  //�����ֵ
    iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������            
    
    iArray[10]=new Array();
    iArray[10][0]="����ԭ��";             //����AccidentReason
    iArray[10][1]="0px";                //�п�
    iArray[10][2]=200;                  //�����ֵ
    iArray[10][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������         
    */
    LLIssueGrid = new MulLineEnter( "document" , "LLIssueGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LLIssueGrid.mulLineCount = 5;   
    LLIssueGrid.displayTitle = 1;
    LLIssueGrid.locked = 0;
//    LLReportGrid.canChk = 1;
    LLIssueGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    LLIssueGrid.selBoxEventFuncName ="LLIssueGridClick"; //��Ӧ�ĺ���������������
//        LLReportGrid.selBoxEventFuncParm =""; //����Ĳ���,����ʡ�Ը���     
    LLIssueGrid.hiddenPlus=1;
    LLIssueGrid.hiddenSubtraction=1;
    LLIssueGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}


</script>

