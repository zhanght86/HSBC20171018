<%
//�������ƣ�LLClaimScanInputInit.jsp
//�����ܣ�
//�������ڣ�2005-8-26 15:50
//������  ��zw
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
    alert("��LLAppealMissInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��LLAppealMissInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
  }
  catch(re)
  {
    alert("LLAppealMissInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initLLAppealGrid()
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
    iArray[1][1]="150px";                //�п�
    iArray[1][2]=100;                  //�����ֵ
    iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="������";             //����
    iArray[2][1]="100px";                //�п�
    iArray[2][2]=100;                  //�����ֵ
    iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="�������뱻���˹�ϵ";             //����
    iArray[3][1]="120px";                //�п�
    iArray[3][2]=200;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="����������";             //����
    iArray[4][1]="100px";                //�п�
    iArray[4][2]=500;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="�������Ա�";             //����
    iArray[5][1]="100px";                //�п�
    iArray[5][2]=200;                  //�����ֵ
    iArray[5][3]=0; 


    iArray[6]=new Array();
    iArray[6][0]="��������";             //����
    iArray[6][1]="100px";                //�п�
    iArray[6][2]=200;                  //�����ֵ
    iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="�᰸����";             //����
    iArray[7][1]="80px";                //�п�
    iArray[7][2]=200;                  //�����ֵ
    iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
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
    
//    iArray[10]=new Array();
//    iArray[10][0]="����ԭ��";             //����AccidentReason
//    iArray[10][1]="0px";                //�п�
//    iArray[10][2]=200;                  //�����ֵ
//    iArray[10][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    LLAppealGrid = new MulLineEnter( "document" , "LLAppealGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LLAppealGrid.mulLineCount = 5;   
    LLAppealGrid.displayTitle = 1;
    LLAppealGrid.locked = 0;
//    LLAppealGrid.canChk = 1;
    LLAppealGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    LLAppealGrid.selBoxEventFuncName ="LLAppealGridClick"; //��Ӧ�ĺ���������������
//        LLAppealGrid.selBoxEventFuncParm =""; //����Ĳ���,����ʡ�Ը���     
    LLAppealGrid.hiddenPlus=1;
    LLAppealGrid.hiddenSubtraction=1;
    LLAppealGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

// ������Ϣ�б�ĳ�ʼ��
function initSelfLLAppealGrid()
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
    iArray[1][0]="���ߺ�";             //����
    iArray[1][1]="120px";                //�п�
    iArray[1][2]=100;                  //�����ֵ
    iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="������";             //����
    iArray[2][1]="100px";                //�п�
    iArray[2][2]=100;                  //�����ֵ
    iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="�Ա�";             //����
    iArray[3][1]="80px";                //�п�
    iArray[3][2]=200;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="�뱻���˹�ϵ";             //����
    iArray[4][1]="120px";                //�п�
    iArray[4][2]=500;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="֤������";             //����
    iArray[5][1]="100px";                //�п�
    iArray[5][2]=200;                  //�����ֵ
    iArray[5][3]=0; 

    iArray[6]=new Array();
    iArray[6][0]="֤������";             //����
    iArray[6][1]="120px";                //�п�
    iArray[6][2]=200;                  //�����ֵ
    iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="�ڴ��������";             //����
    iArray[7][1]="100px";                //�п�
    iArray[7][2]=200;                  //�����ֵ
    iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
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
    
//    iArray[10]=new Array();
//    iArray[10][0]="����ԭ��";             //����AccidentReason
//    iArray[10][1]="0px";                //�п�
//    iArray[10][2]=200;                  //�����ֵ
//    iArray[10][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������         
    
    SelfLLAppealGrid = new MulLineEnter( "document" , "SelfLLAppealGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    SelfLLAppealGrid.mulLineCount = 5;   
    SelfLLAppealGrid.displayTitle = 1;
    SelfLLAppealGrid.locked = 0;
//    SelfLLAppealGrid.canChk = 1;
    SelfLLAppealGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    SelfLLAppealGrid.selBoxEventFuncName ="SelfLLAppealGridClick"; //��Ӧ�ĺ���������������
//        SelfLLAppealGrid.selBoxEventFuncParm =""; //����Ĳ���,����ʡ�Ը���    
    SelfLLAppealGrid.hiddenPlus=1;
    SelfLLAppealGrid.hiddenSubtraction=1;
    SelfLLAppealGrid.loadMulLine(iArray);  

    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>
