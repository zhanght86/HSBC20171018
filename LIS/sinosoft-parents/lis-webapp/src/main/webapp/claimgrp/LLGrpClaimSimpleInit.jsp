<%
//�������ƣ�LLGrpClaimSimpleInit.jsp
//�����ܣ�
//�������ڣ�2005-10-21
//������  ��pd
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
    alert("��LLClaimSimpleMissInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��LLClaimSimpleMissInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initParam();
    initInpBox();
    initSelBox();  
    initSelfLLClaimSimpleGrid();
    initLLClaimSimpleGrid();
    //��½������������,��½��������С��4λ�Ĳ��������в���
   /*if( fm.ManageCom.value.length < 4){
    fm.riskbutton.disabled=true;
    fm.Inputbutton.disabled=true;
   }else{
    fm.riskbutton.disabled=false;
    fm.Inputbutton.disabled=false;
   }*/
  //  querySelfGrid();
  queryGrid();
  
  }
  catch(re)
  {
    alert("LLClaimSimpleMissInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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


// ������Ϣ�б��ĳ�ʼ��
function initSelfLLClaimSimpleGrid()
{                               
    var iArray = new Array();
    
    try
    {
    iArray[0]=new Array();
    iArray[0][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";                //�п�
    iArray[0][2]=10;                  //�����ֵ
    iArray[0][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="�ⰸ��";             //����
    iArray[1][1]="150px";                //�п�
    iArray[1][2]=100;                  //�����ֵ
    iArray[1][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="״̬";             //����
    iArray[2][1]="80px";                //�п�
    iArray[2][2]=80;                  //�����ֵ
    iArray[2][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="����ͻ���";             //����
    iArray[3][1]="100px";                //�п�
    iArray[3][2]=200;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="��λ����";             //����
    iArray[4][1]="200px";                //�п�
    iArray[4][2]=500;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="��������";             //����
    iArray[5][1]="80px";                //�п�
    iArray[5][2]=200;                  //�����ֵ
    iArray[5][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������
    
    iArray[6]=new Array();
    iArray[6][0]="Missionid";             //����
    iArray[6][1]="80px";                //�п�
    iArray[6][2]=200;                  //�����ֵ
    iArray[6][3]=3;                    //�Ƿ���������,1��ʾ������0��ʾ������
    
    iArray[7]=new Array();
    iArray[7][0]="submissionid";             //����
    iArray[7][1]="100px";                //�п�
    iArray[7][2]=200;                  //�����ֵ
    iArray[7][3]=3;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[8]=new Array();
    iArray[8][0]="activityid";             //����
    iArray[8][1]="100px";                //�п�
    iArray[8][2]=200;                  //�����ֵ
    iArray[8][3]=3;                    //�Ƿ���������,1��ʾ������0��ʾ������  
    
    iArray[9]=new Array();
    iArray[9][0]="����������";             //����
    iArray[9][1]="80px";                //�п�
    iArray[9][2]=200;                  //�����ֵ
    iArray[9][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������
    
    iArray[10]=new Array();
    iArray[10][0]="����";             //����
    iArray[10][1]="40px";                //�п�
    iArray[10][2]=200;                  //�����ֵ
    iArray[10][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������   
    
    iArray[11]=new Array();
    iArray[11][0]="ɨ���";             //����
    iArray[11][1]="60px";                //�п�
    iArray[11][2]=200;                  //�����ֵ
    iArray[11][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������ 
    
    iArray[12]=new Array();
    iArray[12][0]="����״̬";             //����
    iArray[12][1]="60px";                //�п�
    iArray[12][2]=200;                  //�����ֵ
    iArray[12][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������   
    
    iArray[13]=new Array();
    iArray[13][0]="��������";             //����AccidentReason
    iArray[13][1]="80px";                //�п�
    iArray[13][2]=80;                  //�����ֵ
    iArray[13][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������       
    

    SelfLLClaimSimpleGrid = new MulLineEnter( "document" , "SelfLLClaimSimpleGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    SelfLLClaimSimpleGrid.mulLineCount = 5;   
    SelfLLClaimSimpleGrid.displayTitle = 1;
    SelfLLClaimSimpleGrid.locked = 0;
//    SelfLLClaimSimpleGrid.canChk = 1;
    SelfLLClaimSimpleGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    SelfLLClaimSimpleGrid.selBoxEventFuncName ="SelfLLClaimSimpleGridClick"; //��Ӧ�ĺ���������������
//        SelfLLClaimSimpleGrid.selBoxEventFuncParm =""; //����Ĳ���,����ʡ�Ը���    
    SelfLLClaimSimpleGrid.hiddenPlus=1;
    SelfLLClaimSimpleGrid.hiddenSubtraction=1;
    SelfLLClaimSimpleGrid.loadMulLine(iArray);  

    }
    catch(ex)
    {
        alert(ex);
    }
}

function initLLClaimSimpleGrid()
{                               
    var iArray = new Array();
    
    try
    {
    iArray[0]=new Array();
    iArray[0][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";                //�п�
    iArray[0][2]=10;                  //�����ֵ
    iArray[0][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="�ⰸ��";             //����
    iArray[1][1]="150px";                //�п�
    iArray[1][2]=100;                  //�����ֵ
    iArray[1][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="״̬";             //����
    iArray[2][1]="80px";                //�п�
    iArray[2][2]=80;                  //�����ֵ
    iArray[2][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="����ͻ���";             //����
    iArray[3][1]="100px";                //�п�
    iArray[3][2]=200;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="��λ����";             //����
    iArray[4][1]="200px";                //�п�
    iArray[4][2]=500;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="��������";             //����
    iArray[5][1]="80px";                //�п�
    iArray[5][2]=200;                  //�����ֵ
    iArray[5][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������
    
    iArray[6]=new Array();
    iArray[6][0]="Missionid";             //����
    iArray[6][1]="80px";                //�п�
    iArray[6][2]=200;                  //�����ֵ
    iArray[6][3]=3;                    //�Ƿ���������,1��ʾ������0��ʾ������
    
    iArray[7]=new Array();
    iArray[7][0]="submissionid";             //����
    iArray[7][1]="100px";                //�п�
    iArray[7][2]=200;                  //�����ֵ
    iArray[7][3]=3;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[8]=new Array();
    iArray[8][0]="activityid";             //����
    iArray[8][1]="100px";                //�п�
    iArray[8][2]=200;                  //�����ֵ
    iArray[8][3]=3;                    //�Ƿ���������,1��ʾ������0��ʾ������  
    
    iArray[9]=new Array();
    iArray[9][0]="����������";             //����
    iArray[9][1]="80px";                //�п�
    iArray[9][2]=200;                  //�����ֵ
    iArray[9][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������
    
    iArray[10]=new Array();
    iArray[10][0]="����";             //����
    iArray[10][1]="40px";                //�п�
    iArray[10][2]=200;                  //�����ֵ
    iArray[10][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������ 
    
    iArray[11]=new Array();
    iArray[11][0]="ɨ���";             //����
    iArray[11][1]="60px";                //�п�
    iArray[11][2]=200;                  //�����ֵ
    iArray[11][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������  
    
    iArray[12]=new Array();                                                   
    iArray[12][0]="����״̬";             //����                              
    iArray[12][1]="60px";                //�п�                               
    iArray[12][2]=200;                  //�����ֵ                            
    iArray[12][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������     

    iArray[13]=new Array();                                                   
    iArray[13][0]="��������";             //����                              
    iArray[13][1]="60px";                //�п�                               
    iArray[13][2]=200;                  //�����ֵ                            
    iArray[13][3]=3;                    //�Ƿ���������,1��ʾ������0��ʾ������     
    

    LLClaimSimpleGrid = new MulLineEnter( "document" , "LLClaimSimpleGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LLClaimSimpleGrid.mulLineCount = 5;   
    LLClaimSimpleGrid.displayTitle = 1;
    LLClaimSimpleGrid.locked = 0;
//    LLClaimSimpleGrid.canChk = 1;
    LLClaimSimpleGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    LLClaimSimpleGrid.selBoxEventFuncName ="LLClaimSimpleGridClick"; //��Ӧ�ĺ���������������
//        LLClaimSimpleGrid.selBoxEventFuncParm =""; //����Ĳ���,����ʡ�Ը���    
    LLClaimSimpleGrid.hiddenPlus=1;
    LLClaimSimpleGrid.hiddenSubtraction=1;
    LLClaimSimpleGrid.loadMulLine(iArray);  

    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>