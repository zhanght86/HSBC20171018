<%
//�������ƣ�LLGrpClaimConfirmInit.jsp
//�����ܣ�
//�������ڣ�2005-11-16
//������  ��pd
//���¼�¼��
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

//���ղ���
function initParam()
{
    fm.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
    fm.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
    fm.all('ManageCom').value = nullToEmpty("<%= tGlobalInput.ManageCom %>");
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
    querySelfGrid();
  
  }
  catch(re)
  {
    alert("LLClaimSimpleMissInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


// ������Ϣ�б�ĳ�ʼ��
function initSelfLLClaimSimpleGrid()
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
    iArray[2][1]="100px";                //�п�
    iArray[2][2]=100;                  //�����ֵ
    iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="�����";             //����
    iArray[3][1]="100px";                //�п�
    iArray[3][2]=200;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="��λ����";             //����
    iArray[4][1]="100px";                //�п�
    iArray[4][2]=500;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="Ͷ��������";             //����
    iArray[5][1]="80px";                //�п�
    iArray[5][2]=200;                  //�����ֵ
    iArray[5][3]=0; 


    iArray[6]=new Array();
    iArray[6][0]="��������";             //����
    iArray[6][1]="100px";                //�п�
    iArray[6][2]=200;                  //�����ֵ
    iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[7]=new Array();
    iArray[7][0]="����������";             //����
    iArray[7][1]="100px";                //�п�
    iArray[7][2]=200;                  //�����ֵ
    iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="����";             //����
    iArray[8][1]="80px";                //�п�
    iArray[8][2]=200;                  //�����ֵ
    iArray[8][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������   
    

    SelfLLClaimSimpleGrid = new MulLineEnter( "fm" , "SelfLLClaimSimpleGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    SelfLLClaimSimpleGrid.mulLineCount = 0;   
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
</script>
