<%
//�������ƣ�LLGrpReportMissInit.jsp
//�����ܣ�
//�������ڣ�2008-10-25 18:13
//������  ��zhangzheng
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
    alert("��LLReportMissInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��LLReportMissInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initParam();
    initInpBox();
    initSelBox();  
    initSelfLLReportGrid();
    querySelfGrid();

  }
  catch(re)
  {
    alert("LLReportMissInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
  spanDutyGrid.innerHTML="";
}


// ������Ϣ�б�ĳ�ʼ��
function initSelfLLReportGrid()
{                               
    var iArray = new Array();
    
    try
    {
    iArray[0]=new Array();
    iArray[0][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";                //�п�
    iArray[0][2]=8;                  //�����ֵ
    iArray[0][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="�ⰸ��";             //����
    iArray[1][1]="160px";                //�п�
    iArray[1][2]=160;                  //�����ֵ
    iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="����������";             //����
    iArray[2][1]="120px";                //�п�
    iArray[2][2]=120;                  //�����ֵ
    iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="����ͻ���";             //����
    iArray[3][1]="120px";                //�п�
    iArray[3][2]=200;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="��λ����";             //����
    iArray[4][1]="160px";                //�п�
    iArray[4][2]=300;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="���屣����";             //����
    iArray[5][1]="160px";                //�п�
    iArray[5][2]=160;                  //�����ֵ
    iArray[5][3]=0; 


    iArray[6]=new Array();
    iArray[6][0]="��������";             //����
    iArray[6][1]="80px";                //�п�
    iArray[6][2]=200;                  //�����ֵ
    iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="Missionid";             //����
    iArray[7][1]="60px";                //�п�
    iArray[7][2]=200;                  //�����ֵ
    iArray[7][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="submissionid";             //����
    iArray[8][1]="80px";                //�п�
    iArray[8][2]=200;                  //�����ֵ
    iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[9]=new Array();
    iArray[9][0]="activityid";             //����
    iArray[9][1]="80px";                //�п�
    iArray[9][2]=200;                  //�����ֵ
    iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������            
    
    iArray[10]=new Array();
    iArray[10][0]="��������";             //����AccidentReason
    iArray[10][1]="70px";                //�п�
    iArray[10][2]=70;                  //�����ֵ
    iArray[10][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������     
    
    iArray[11]=new Array();
    iArray[11][0]="ɨ���";             //����AccidentReason
    iArray[11][1]="60px";                //�п�
    iArray[11][2]=200;                  //�����ֵ
    iArray[11][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������   
    
    iArray[12]=new Array();
    iArray[12][0]="����״̬";             //����AccidentReason
    iArray[12][1]="60px";                //�п�
    iArray[12][2]=200;                  //�����ֵ
    iArray[12][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������            
    
    SelfLLReportGrid = new MulLineEnter( "document" , "SelfLLReportGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    SelfLLReportGrid.mulLineCount = 5;   
    SelfLLReportGrid.displayTitle = 1;
    SelfLLReportGrid.locked = 0;
//    SelfLLReportGrid.canChk = 1;
    SelfLLReportGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    SelfLLReportGrid.selBoxEventFuncName ="SelfLLReportGridClick"; //��Ӧ�ĺ���������������
//        SelfLLReportGrid.selBoxEventFuncParm =""; //����Ĳ���,����ʡ�Ը���    
    SelfLLReportGrid.hiddenPlus=1;
    SelfLLReportGrid.hiddenSubtraction=1;
    SelfLLReportGrid.loadMulLine(iArray);  

    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>
