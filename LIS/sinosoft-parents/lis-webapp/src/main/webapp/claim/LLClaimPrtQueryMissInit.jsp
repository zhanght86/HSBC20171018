<%
//�������ƣ�LLClaimPrtQueryMissInit.jsp
//�����ܣ�
//�������ڣ�2005-8-22 10:16
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
    alert("��LLClaimQueryMissInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��LLClaimQueryMissInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initParam();
    initInpBox();
    initSelBox();  
    initLLClaimQueryGrid();
  }
  catch(re)
  {
    alert("LLClaimQueryMissInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
function initLLClaimQueryGrid()
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
    iArray[1][0]="�ⰸ��";             //����
    iArray[1][1]="100px";                //�п�
    iArray[1][2]=100;                  //�����ֵ
    iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="״̬";             //����
    iArray[2][1]="50px";                //�п�
    iArray[2][2]=100;                  //�����ֵ
    iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="�����˱���";             //����
    iArray[3][1]="100px";                //�п�
    iArray[3][2]=200;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="����������";             //����
    iArray[4][1]="100px";                //�п�
    iArray[4][2]=500;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="�Ա�";             //����
    iArray[5][1]="50px";                //�п�
    iArray[5][2]=200;                  //�����ֵ
    iArray[5][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[6]=new Array();
    iArray[6][0]="��������";             //����
    iArray[6][1]="100px";                //�п�
    iArray[6][2]=200;                  //�����ֵ
    iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

//    iArray[7]=new Array();
//    iArray[7][0]="Missionid";             //����
//    iArray[7][1]="80px";                //�п�
//    iArray[7][2]=200;                  //�����ֵ
//    iArray[7][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
//    
//    iArray[8]=new Array();
//    iArray[8][0]="submissionid";             //����
//    iArray[8][1]="100px";                //�п�
//    iArray[8][2]=200;                  //�����ֵ
//    iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
//
//    iArray[9]=new Array();
//    iArray[9][0]="activityid";             //����
//    iArray[9][1]="100px";                //�п�
//    iArray[9][2]=200;                  //�����ֵ
//    iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������            
//    
//    iArray[10]=new Array();
//    iArray[10][0]="Ԥ����־";             //����
//    iArray[10][1]="60px";                //�п�
//    iArray[10][2]=200;                  //�����ֵ
//    iArray[10][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
//    
//    iArray[11]=new Array();
//    iArray[11][0]="��������";             //����
//    iArray[11][1]="60px";                //�п�
//    iArray[11][2]=200;                  //�����ֵ
//    iArray[11][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
//    
//    iArray[12]=new Array();
//    iArray[12][0]="����";             //����
//    iArray[12][1]="60px";                //�п�
//    iArray[12][2]=200;                  //�����ֵ
//    iArray[12][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
//
//    iArray[13]=new Array();
//    iArray[13][0]="Ȩ��";             //����
//    iArray[13][1]="50px";                //�п�
//    iArray[13][2]=200;                  //�����ֵ
//    iArray[13][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
//    
//    iArray[14]=new Array();
//    iArray[14][0]="Ԥ��ֵ";             //����
//    iArray[14][1]="120px";                //�п�
//    iArray[14][2]=200;                  //�����ֵ
//    iArray[14][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
//   
//    iArray[15]=new Array();
//    iArray[15][0]="��������";             //����
//    iArray[15][1]="80px";                //�п�
//    iArray[15][2]=200;                  //�����ֵ
//    iArray[15][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������ 
//    
//    iArray[16]=new Array();
//    iArray[16][0]="����������";             //����
//    iArray[16][1]="80px";                //�п�
//    iArray[16][2]=200;                  //�����ֵ
//    iArray[16][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������ 
//    
//    iArray[17]=new Array();
//    iArray[17][0]="����";             //����
//    iArray[17][1]="80px";                //�п�
//    iArray[17][2]=200;                  //�����ֵ
//    iArray[17][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������ 
    
    LLClaimQueryGrid = new MulLineEnter( "fm" , "LLClaimQueryGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LLClaimQueryGrid.mulLineCount = 5;   
    LLClaimQueryGrid.displayTitle = 1;
    LLClaimQueryGrid.locked = 0;
//    LLClaimQueryGrid.canChk = 1;
    LLClaimQueryGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    LLClaimQueryGrid.selBoxEventFuncName ="LLClaimQueryGridClick"; //��Ӧ�ĺ���������������
//        LLClaimQueryGrid.selBoxEventFuncParm =""; //����Ĳ���,����ʡ�Ը���     
    LLClaimQueryGrid.hiddenPlus=1;
    LLClaimQueryGrid.hiddenSubtraction=1;
    LLClaimQueryGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
