<%
//Name:LLClaimEndCaseInit.jsp
//function��
//author: zl
//Date: 2005-6-21 9:31
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
<script language="JavaScript">

//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
    document.all('ClaimNo').value = nullToEmpty("<%= tClmNo %>");
    document.all('MissionID').value = nullToEmpty("<%= tMissionID %>");
    document.all('SubMissionID').value = nullToEmpty("<%= tSubMissionID %>");
    document.all('RgtObjNo').value = nullToEmpty("<%= tGrpRptNo %>");
    if (document.all('RgtObjNo').value!=null && document.all('RgtObjNo').value!="")
    {
       spanRgtObjNo1.style.display = "";
       spanRgtObjNo2.style.display = "";
    }
    else
    {
       spanRgtObjNo1.style.display = "none";
       spanRgtObjNo2.style.display = "none";
    }    
    
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
    initInpBox();
//    initLLClaimGrid();
	initLLReCustomerGrid(); //���ⰸ��صĿͻ���Ϣ�б�
    initPolDutyCodeGrid(); //���������Ϣ
    LLClaimQuery();
    LLReCustomerGridQuery();

    
    //fm.BarCodePrint.disabled = true; //��ӡ������
    //fm.ColQueryImage.disabled = true; //Ӱ���ѯ 
    //fm.ReciInfoInp.disabled = true; //�ռ�����Ϣ
    //fm.BatchCreateNote.disabled = true; //��֤������ӡ����
  }
  catch(re)
  {
    alert("��LLClaimEndCaseInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

/**
	ҳ���ʼ��
*/
function initInpBox()
{
  try
  {

  }
  catch(ex)
  {
    alert("��LLClaimEndCaseInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

//��������ʼ��
function initPolDutyCodeGrid()
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
	    iArray[1][0]="������";
	    iArray[1][1]="130px";
	    iArray[1][2]=130;
	    iArray[1][3]=0;

	    iArray[2]=new Array();
	    iArray[2][0]="��������";
	    iArray[2][1]="60px";
	    iArray[2][2]=60;
	    iArray[2][3]=3;

	    iArray[3]=new Array();
	    iArray[3][0]="���ֱ���";
	    iArray[3][1]="60px";
	    iArray[3][2]=60;
	    iArray[3][3]=3;

	    iArray[4]=new Array();
	    iArray[4][0]="�������";
	    iArray[4][1]="100px";
	    iArray[4][2]=100;
	    iArray[4][3]=3;

	    iArray[5]=new Array();
	    iArray[5][0]="��������";
	    iArray[5][1]="200px";
	    iArray[5][2]=200;
	    iArray[5][3]=0;


	    iArray[6]=new Array();
	    iArray[6][0]="��������";
	    iArray[6][1]="80px";
	    iArray[6][2]=80;
	    iArray[6][3]=0;
        
        iArray[7]=new Array();
        iArray[7][0]="����ֹ��";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=0;
     
        iArray[8]=new Array();
        iArray[8][0]="��������";
        iArray[8][1]="60px";
        iArray[8][2]=60;
        iArray[8][3]=0;        

	    iArray[9]=new Array();
	    iArray[9][0]="����";
	    iArray[9][1]="60px";
	    iArray[9][2]=60;
	    iArray[9][3]=0;

	    iArray[10]=new Array();
	    iArray[10][0]="��Ⱥ���";
	    iArray[10][1]="60px";
	    iArray[10][2]=60;
	    iArray[10][3]=0;

	    iArray[11]=new Array();
	    iArray[11][0]="���˺���";
	    iArray[11][1]="60px";
	    iArray[11][2]=60;
	    iArray[11][3]=0;

	    iArray[12]=new Array();
	    iArray[12][0]="������";
	    iArray[12][1]="60px";
	    iArray[12][2]=60;
	    iArray[12][3]=0;

        iArray[13]=new Array();
        iArray[13][0]="��������";
        iArray[13][1]="60px";
        iArray[13][2]=60;
        iArray[13][3]=2;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
        iArray[13][4]="llpayregconclusion"; //�Ƿ����ô���: null����" "Ϊ������
        iArray[13][5]="13|14";              //���ô�����Ϣ�ֱ���ڵ�13�к͵�14�У�'|'Ϊ�ָ��
        iArray[13][6]="0|1";                //���ô�������ĵ�0����룩���ڵ�1��,��1����ƣ����ڵ�2��


        iArray[14]=new Array();
        iArray[14][0]="��������";
        iArray[14][1]="60px";
        iArray[14][2]=60;
        iArray[14][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����

        iArray[15]=new Array();
        iArray[15][0]="�����ڼ��ʶ";
        iArray[15][1]="80px";
        iArray[15][2]=60;
        iArray[15][3]=0;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
        

        iArray[16]=new Array();
        iArray[16][0]="dutycode";
        iArray[16][1]="60px";
        iArray[16][2]=60;
        iArray[16][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����


	    PolDutyCodeGrid = new MulLineEnter("fm","PolDutyCodeGrid");
	    PolDutyCodeGrid.mulLineCount = 0;        // ��ʾ����
	    PolDutyCodeGrid.displayTitle = 1;
	    PolDutyCodeGrid.locked = 0;
	//  PolDutyCodeGrid.canChk =1;               // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
	    PolDutyCodeGrid.canSel =1;                   // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
	    PolDutyCodeGrid.hiddenPlus=1;        //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
	    PolDutyCodeGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
	    PolDutyCodeGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert(ex);
  }  
}

/**
	��������Ϣ��ʼ��
*/
function initLLClaimGrid()
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
        iArray[1][0]="������"; //
        iArray[1][1]="160px";
        iArray[1][2]=160;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="������������"; //
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="�ⰸ״̬";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=0;
    
        iArray[4]=new Array();
        iArray[4][0]="�����⸶���";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;
    
        iArray[5]=new Array()
        iArray[5][0]="�ȸ������";
        iArray[5][1]="100px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="�����⸶���";
        iArray[6][1]="100px";
        iArray[6][2]=100;
        iArray[6][3]=0;
    
        iArray[7]=new Array();
        iArray[7][0]="�⸶����";
        iArray[7][1]="100px";
        iArray[7][2]=100;
        iArray[7][3]=0;
    
        iArray[8]=new Array();
        iArray[8][0]="�⸶��������";
        iArray[8][1]="0px";
        iArray[8][2]=100;
        iArray[8][3]=3;

        iArray[9]=new Array();
        iArray[9][0]="����Ա";
        iArray[9][1]="0px";
        iArray[9][2]=100;
        iArray[9][3]=3;
    
        iArray[10]=new Array();
        iArray[10][0]="�����";
        iArray[10][1]="0px";
        iArray[10][2]=100;
        iArray[10][3]=3;
        
        iArray[11]=new Array();
        iArray[11][0]="�᰸����";
        iArray[11][1]="0px";
        iArray[11][2]=100;
        iArray[11][3]=3;
        
        iArray[12]=new Array();
        iArray[12][0]="������������";
        iArray[12][1]="0px";
        iArray[12][2]=100;
        iArray[12][3]=3;
        
        iArray[13]=new Array();
        iArray[13][0]="�������";
        iArray[13][1]="0px";
        iArray[13][2]=100;
        iArray[13][3]=3;                        

        LLClaimGrid = new MulLineEnter("fm","LLClaimGrid");
        LLClaimGrid.mulLineCount = 5;
        LLClaimGrid.displayTitle = 1;
        LLClaimGrid.locked = 0;
//        LLClaimGrid.canChk = 1;
        LLClaimGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        LLClaimGrid.selBoxEventFuncName ="LLClaimGridClick"; //��Ӧ�ĺ���������������
//        LLClaimGrid.selBoxEventFuncParm =""; //����Ĳ���,����ʡ�Ը���        
        LLClaimGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        LLClaimGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        LLClaimGrid.loadMulLine(iArray);

    }
    catch(ex)
    {
        alert(ex);
    }
}

//���ⰸ��صĳ�������Ϣ�б�
function initLLReCustomerGrid()
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
        iArray[1][0]="�ͻ�����"; //ԭ�¹��߿ͻ���
        iArray[1][1]="230px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="�ͻ�����"; //�¹�������
        iArray[2][1]="150px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="�Ա����";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=3;

        iArray[4]=new Array();
        iArray[4][0]="�Ա�";
        iArray[4][1]="80px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="��������";
        iArray[5][1]="150px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array()
        iArray[6][0]="�籣��־����";
        iArray[6][1]="50px";
        iArray[6][2]=100;
        iArray[6][3]=3;
        
        iArray[7]=new Array()
        iArray[7][0]="�籣��־";
        iArray[7][1]="80px";
        iArray[7][2]=100;
        iArray[7][3]=0;  
        
        LLReCustomerGrid = new MulLineEnter("fm","LLReCustomerGrid");
        LLReCustomerGrid.mulLineCount = 0;
        LLReCustomerGrid.displayTitle = 1;
        LLReCustomerGrid.locked = 0;
        LLReCustomerGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        LLReCustomerGrid.selBoxEventFuncParm ="1"; //����Ĳ���,����ʡ�Ը���
        LLReCustomerGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        LLReCustomerGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        LLReCustomerGrid.loadMulLine(iArray);

    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
