<%
//�������ƣ�LLClaimPrtAgainInfoInit.jsp
//�����ܣ�������Ҫ��֤������Ϣ��ѯ
//�������ڣ�2005-11-01 
//������  ��niuzj
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
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo%>"); //�ⰸ��
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
    alert("��LLClaimPrtAgainInfoInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {       
  }
  catch(ex)
  {
    alert("��LLClaimPrtAgainInfoInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        


function initForm()
{
  try
  {
    initParam();
    initInpBox();
    initSelBox();  
    //��JSҳ���ϵĳ�ʼ����ѯ�ĳ�ʼ��
    initLLClaimPrtAgainInfoGrid();
    querySelfGrid();
  }
  catch(re)
  {
    alert("LLClaimPrtAgainInfoInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
function initLLClaimPrtAgainInfoGrid()
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
    iArray[1][0]="��ӡ��ˮ��";             //����
    iArray[1][1]="100px";                //�п�
    iArray[1][2]=100;                  //�����ֵ
    iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[2]=new Array();
    iArray[2][0]="�ⰸ��";             //����
    iArray[2][1]="100px";                //�п�
    iArray[2][2]=100;                  //�����ֵ
    iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="��֤����";             //����
    iArray[3][1]="100px";                //�п�
    iArray[3][2]=100;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="��֤����";             //����
    iArray[4][1]="300px";                //�п�
    iArray[4][2]=300;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    

//    iArray[4]=new Array();
//    iArray[4][0]="��������";             //����
//    iArray[4][1]="100px";                //�п�
//    iArray[4][2]=100;                  //�����ֵ
//    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
//
//    iArray[5]=new Array();
//    iArray[5][0]="���������";             //����
//    iArray[5][1]="100px";                //�п�
//    iArray[5][2]=100;                  //�����ֵ
//    iArray[5][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
//
    
    LLClaimPrtAgainInfoGrid = new MulLineEnter( "fm" , "LLClaimPrtAgainInfoGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LLClaimPrtAgainInfoGrid.mulLineCount = 0;   
    LLClaimPrtAgainInfoGrid.displayTitle = 1;
    LLClaimPrtAgainInfoGrid.locked = 0;
//    LLClaimPrtAgainInfoGrid.canChk = 1;
    LLClaimPrtAgainInfoGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    LLClaimPrtAgainInfoGrid.selBoxEventFuncName ="LLClaimPrtAgainInfoGridClick"; //��Ӧ�ĺ���������������
//        LLClaimPrtAgainInfoGrid.selBoxEventFuncParm =""; //����Ĳ���,����ʡ�Ը���     
    LLClaimPrtAgainInfoGrid.hiddenPlus=1;
    LLClaimPrtAgainInfoGrid.hiddenSubtraction=1;
    LLClaimPrtAgainInfoGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
