<%
//�������ƣ�LLClaimConfirmMissInit.jsp
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
    alert("��LLClaimConfirmMissInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��LLClaimConfirmMissInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initParam();
    initInpBox();
    initSelBox();  
    initLLClaimConfirmGrid();
    initSelfLLClaimConfirmGrid();
    //��½������������,��½��������С��4λ�Ĳ�������в���
/*
   if( fm.ManageCom.value.length < 4){
      fm.riskbutton.disabled=true;
      
    }else{
      fm.riskbutton.disabled=false;
   
     }
*/
    querySelfGrid();
//  initSelfPolGrid();
//  easyQueryClickSelf();
  
  }
  catch(re)
  {
    alert("LLClaimConfirmMissInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
function initLLClaimConfirmGrid()
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
    iArray[2][0]="״̬";             //����
    iArray[2][1]="50px";                //�п�
    iArray[2][2]=100;                  //�����ֵ
    iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="�ͻ�����";             //����
    iArray[3][1]="100px";                //�п�
    iArray[3][2]=200;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="�ͻ�����";             //����
    iArray[4][1]="120px";                //�п�
    iArray[4][2]=200;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="�Ա�";             //����
    iArray[5][1]="50px";                //�п�
    iArray[5][2]=200;                  //�����ֵ
    iArray[5][3]=3; 


    iArray[6]=new Array();
    iArray[6][0]="��������";             //����
    iArray[6][1]="100px";                //�п�
    iArray[6][2]=200;                  //�����ֵ
    iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="Missionid";             //����
    iArray[7][1]="80px";                //�п�
    iArray[7][2]=200;                  //�����ֵ
    iArray[7][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="submissionid";             //����
    iArray[8][1]="100px";                //�п�
    iArray[8][2]=200;                  //�����ֵ
    iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[9]=new Array();
    iArray[9][0]="activityid";             //����
    iArray[9][1]="100px";                //�п�
    iArray[9][2]=200;                  //�����ֵ
    iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������            
    
    iArray[10]=new Array();
    iArray[10][0]="Ԥ����־";             //����
    iArray[10][1]="60px";                //�п�
    iArray[10][2]=200;                  //�����ֵ
    iArray[10][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[11]=new Array();
    iArray[11][0]="���Ȩ��";             //����
    iArray[11][1]="60px";                //�п�
    iArray[11][2]=200;                  //�����ֵ
    iArray[11][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[12]=new Array();
    iArray[12][0]="�����";             //����
    iArray[12][1]="120px";                //�п�
    iArray[12][2]=200;                  //�����ֵ
    iArray[12][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[13]=new Array();						//��ʾ�Ƿ����ϱ�
    iArray[13][0]="Ȩ��";             //����
    iArray[13][1]="60px";                //�п�
    iArray[13][2]=200;                  //�����ֵ
    iArray[13][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[14]=new Array();
    iArray[14][0]="��������";             //����
    iArray[14][1]="80px";                //�п�
    iArray[14][2]=200;                  //�����ֵ
    iArray[14][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������ 
    
    iArray[15]=new Array();
    iArray[15][0]="���˲�����";             //����
    iArray[15][1]="80px";                //�п�
    iArray[15][2]=200;                  //�����ֵ
    iArray[15][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������ 

    iArray[16]=new Array();
    iArray[16][0]="����";             //����
    iArray[16][1]="80px";                //�п�
    iArray[16][2]=200;                  //�����ֵ
    iArray[16][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������ 
    
    iArray[17]=new Array();
    iArray[17][0]="����״̬";             //����
    iArray[17][1]="60px";                //�п�
    iArray[17][2]=200;                  //�����ֵ
    iArray[17][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������     
    
    LLClaimConfirmGrid = new MulLineEnter( "document" , "LLClaimConfirmGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LLClaimConfirmGrid.mulLineCount = 5;   
    LLClaimConfirmGrid.displayTitle = 1;
    LLClaimConfirmGrid.locked = 0;
//    LLClaimConfirmGrid.canChk = 1;
    LLClaimConfirmGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    LLClaimConfirmGrid.selBoxEventFuncName ="LLClaimConfirmGridClick"; //��Ӧ�ĺ���������������
//        LLClaimConfirmGrid.selBoxEventFuncParm =""; //����Ĳ���,����ʡ�Ը���     
    LLClaimConfirmGrid.hiddenPlus=1;
    LLClaimConfirmGrid.hiddenSubtraction=1;
    LLClaimConfirmGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

// ������Ϣ�б�ĳ�ʼ��
function initSelfLLClaimConfirmGrid()
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
    iArray[2][0]="״̬";             //����
    iArray[2][1]="50px";                //�п�
    iArray[2][2]=100;                  //�����ֵ
    iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="�ͻ�����";             //����
    iArray[3][1]="100px";                //�п�
    iArray[3][2]=200;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="�ͻ�����";             //����
    iArray[4][1]="120px";                //�п�
    iArray[4][2]=200;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="�Ա�";             //����
    iArray[5][1]="50px";                //�п�
    iArray[5][2]=200;                  //�����ֵ
    iArray[5][3]=3; 

    iArray[6]=new Array();
    iArray[6][0]="��������";             //����
    iArray[6][1]="100px";                //�п�
    iArray[6][2]=200;                  //�����ֵ
    iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="Missionid";             //����
    iArray[7][1]="80px";                //�п�
    iArray[7][2]=200;                  //�����ֵ
    iArray[7][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="submissionid";             //����
    iArray[8][1]="100px";                //�п�
    iArray[8][2]=200;                  //�����ֵ
    iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[9]=new Array();
    iArray[9][0]="activityid";             //����
    iArray[9][1]="100px";                //�п�
    iArray[9][2]=200;                  //�����ֵ
    iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������            
    
    iArray[10]=new Array();
    iArray[10][0]="Ԥ����־";             //����
    iArray[10][1]="60px";                //�п�
    iArray[10][2]=200;                  //�����ֵ
    iArray[10][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[11]=new Array();
    iArray[11][0]="���Ȩ��";             //����
    iArray[11][1]="60px";                //�п�
    iArray[11][2]=200;                  //�����ֵ
    iArray[11][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[12]=new Array();
    iArray[12][0]="�����";             //����
    iArray[12][1]="120px";                //�п�
    iArray[12][2]=200;                  //�����ֵ
    iArray[12][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������    
    
    iArray[13]=new Array();
    iArray[13][0]="Ȩ��";             //����
    iArray[13][1]="60px";                //�п�
    iArray[13][2]=200;                  //�����ֵ
    iArray[13][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[14]=new Array();
    iArray[14][0]="��������";             //����
    iArray[14][1]="80px";                //�п�
    iArray[14][2]=200;                  //�����ֵ
    iArray[14][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������ 
    
    iArray[15]=new Array();
    iArray[15][0]="���˲�����";             //����
    iArray[15][1]="80px";                //�п�
    iArray[15][2]=200;                  //�����ֵ
    iArray[15][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������ 

    iArray[16]=new Array();
    iArray[16][0]="����";             //����
    iArray[16][1]="80px";                //�п�
    iArray[16][2]=200;                  //�����ֵ
    iArray[16][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������ 
    
    iArray[17]=new Array();
    iArray[17][0]="����״̬";             //����
    iArray[17][1]="60px";                //�п�
    iArray[17][2]=200;                  //�����ֵ
    iArray[17][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������    

    iArray[18]=new Array();
    iArray[18][0]="��������";             //����
    iArray[18][1]="60px";                //�п�
    iArray[18][2]=200;                  //�����ֵ
    iArray[18][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������    
    
    SelfLLClaimConfirmGrid = new MulLineEnter( "document" , "SelfLLClaimConfirmGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    SelfLLClaimConfirmGrid.mulLineCount = 5;   
    SelfLLClaimConfirmGrid.displayTitle = 1;
    SelfLLClaimConfirmGrid.locked = 0;
//    SelfLLClaimConfirmGrid.canChk = 1;
    SelfLLClaimConfirmGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    SelfLLClaimConfirmGrid.selBoxEventFuncName ="SelfLLClaimConfirmGridClick"; //��Ӧ�ĺ���������������
//        SelfLLClaimConfirmGrid.selBoxEventFuncParm =""; //����Ĳ���,����ʡ�Ը���    
    SelfLLClaimConfirmGrid.hiddenPlus=1;
    SelfLLClaimConfirmGrid.hiddenSubtraction=1;
    SelfLLClaimConfirmGrid.loadMulLine(iArray);  

    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>
