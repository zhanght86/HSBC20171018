<%
//Name: LLReportInit.jsp
//function��
//author: 
//Date: 2005-7-26 16:33
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
    fm.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
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
    initSubReportGrid();
    initClaimGrid();
    initDutyKindGrid();
    initPolDutyKindGrid();
    initPolDutyCodeGrid();
    queryAppeal();
  }
  catch(re)
  {
    alert("��LLRegisterInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

/**
	ҳ���ʼ��
*/
function initInpBox()
{
  try
  {
    fm.addUpdate.disabled = true; //����޸�
    fm.saveUpdate.disabled = true; //�����޸�
  }
  catch(ex)
  {
    alert("��LLReportInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}


/**
	��������Ϣ��ʼ��
*/
function initSubReportGrid()
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
        iArray[1][0]="�����˱���"; //ԭ�¹��߿ͻ���
        iArray[1][1]="150px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="����������"; //�¹�������
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="�Ա�";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=0;
    
        iArray[4]=new Array();
        iArray[4][0]="��������";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;
    
        iArray[5]=new Array()
        iArray[5][0]="VIP��־";
        iArray[5][1]="50px";
        iArray[5][2]=100;
        iArray[5][3]=0;

//        iArray[6]=new Array();
//        iArray[6][0]="����ϸ��";
//        iArray[6][1]="0px";
//        iArray[6][2]=100;
//        iArray[6][3]=3;
//    
//        iArray[7]=new Array();
//        iArray[7][0]="����ҽԺ";
//        iArray[7][1]="0px";
//        iArray[7][2]=100;
//        iArray[7][3]=3;
//    
//        iArray[8]=new Array();
//        iArray[8][0]="�������";
//        iArray[8][1]="0px";
//        iArray[8][2]=100;
//        iArray[8][3]=3;
//
//        iArray[9]=new Array();
//        iArray[9][0]="������ʶ";
//        iArray[9][1]="0px";
//        iArray[9][2]=100;
//        iArray[9][3]=3;
//    
//        iArray[10]=new Array();
//        iArray[10][0]="��ע��Ϣ";
//        iArray[10][1]="0px";
//        iArray[10][2]=100;
//        iArray[10][3]=3;

        SubReportGrid = new MulLineEnter("fm","SubReportGrid");
        SubReportGrid.mulLineCount = 0;
        SubReportGrid.displayTitle = 1;
        SubReportGrid.locked = 0;
//        SubReportGrid.canChk =1;
        SubReportGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        SubReportGrid.selBoxEventFuncName ="SubReportGridClick"; //��Ӧ�ĺ���������������
        SubReportGrid.selBoxEventFuncParm ="1"; //����Ĳ���,����ʡ�Ը���  
        SubReportGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        SubReportGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        SubReportGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert(ex);
  }
}


/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ������Ϊ�������Ľ�������ֱ���ʾΪ�ⰸ���������ͣ��������������ͣ������������͵ı���
    �� �� �ˣ�����
    �޸����ڣ�2005.06.25
   =========================================================================
**/

//�ⰸ�ܽ��
function initClaimGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";
        iArray[0][1]="30px";
        iArray[0][2]=30;
        iArray[0][3]=0;

        iArray[1]=new Array();
        iArray[1][0]="�⸶���";
        iArray[1][1]="80px";
        iArray[1][2]=80;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="Ԥ�����";
        iArray[2][1]="80px";
        iArray[2][2]=80;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="������";
        iArray[3][1]="80px";
        iArray[3][2]=80;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="�����⸶���";
        iArray[4][1]="80px";
        iArray[4][2]=80;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="������";
        iArray[5][1]="80px";
        iArray[5][2]=80;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="";
        iArray[6][1]="80px";
        iArray[6][2]=80;
        iArray[6][3]=3;

        iArray[7]=new Array();
        iArray[7][0]="";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=3;

        iArray[8]=new Array();
        iArray[8][0]="";
        iArray[8][1]="80px";
        iArray[8][2]=80;
        iArray[8][3]=3;
        

        ClaimGrid = new MulLineEnter("fm","ClaimGrid");
        ClaimGrid.mulLineCount = 0;       // ��ʾ����
        ClaimGrid.displayTitle = 1;
        ClaimGrid.locked = 0;
    //  ClaimGrid.canChk =1;              // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
        ClaimGrid.canSel =1;                  // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
        ClaimGrid.hiddenPlus=1;           //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        ClaimGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        ClaimGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

//�ⰸ����������
function initDutyKindGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";
        iArray[0][1]="30px";
        iArray[0][2]=30;
        iArray[0][3]=0;

        iArray[1]=new Array();
        iArray[1][0]="�������ʹ���";
        iArray[1][1]="80px";
        iArray[1][2]=80;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="������������";
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="�˵����";
        iArray[3][1]="80px";
        iArray[3][2]=80;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="���������";      //Ҳ����������
        iArray[4][1]="80px";
        iArray[4][2]=80;
        iArray[4][3]=3;

        iArray[5]=new Array();
        iArray[5][0]="�����⸶���";
        iArray[5][1]="80px";
        iArray[5][2]=80;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="�籣����";
        iArray[6][1]="80px";
        iArray[6][2]=80;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="����������";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="�����⸶���";
        iArray[8][1]="80px";
        iArray[8][2]=80;
        iArray[8][3]=0;
        

        DutyKindGrid = new MulLineEnter("fm","DutyKindGrid");
        DutyKindGrid.mulLineCount = 0;       // ��ʾ����
        DutyKindGrid.displayTitle = 1;
        DutyKindGrid.locked = 0;
    //  DutyKindGrid.canChk =1;              // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
        DutyKindGrid.canSel =1;                  // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
        DutyKindGrid.hiddenPlus=1;           //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        DutyKindGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        DutyKindGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}


//�������������ͼ����ʼ��
function initPolDutyKindGrid()
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
	    iArray[1][0]="��ͬ��";
	    iArray[1][1]="105px";
	    iArray[1][2]=105;
	    iArray[1][3]=0;

	    iArray[2]=new Array();
	    iArray[2][0]="������";
	    iArray[2][1]="105px";
	    iArray[2][2]=105;
	    iArray[2][3]=0;

	    iArray[3]=new Array();
	    iArray[3][0]="��������";
	    iArray[3][1]="60px";
	    iArray[3][2]=60;
	    iArray[3][3]=0;

	    iArray[4]=new Array();
	    iArray[4][0]="��Ч����";
	    iArray[4][1]="70px";
	    iArray[4][2]=60;
	    iArray[4][3]=0;

	    iArray[5]=new Array();
	    iArray[5][0]="��������";
	    iArray[5][1]="70px";
	    iArray[5][2]=60;
	    iArray[5][3]=0;


      iArray[6]=new Array();
      iArray[6][0]="���ִ���";
      iArray[6][1]="60px";
      iArray[6][2]=60;
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="��������";
      iArray[7][1]="140px";
      iArray[7][2]=140;
      iArray[7][3]=0;

      iArray[8]=new Array();
      iArray[8][0]="������";
      iArray[8][1]="60px";
      iArray[8][2]=60;
      iArray[8][3]=0;


	    PolDutyKindGrid = new MulLineEnter("fm","PolDutyKindGrid");
	    PolDutyKindGrid.mulLineCount = 0;       // ��ʾ����
	    PolDutyKindGrid.displayTitle = 1;
	    PolDutyKindGrid.locked = 0;
	//  PolDutyKindGrid.canChk =1;              // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
	    PolDutyKindGrid.canSel =1;                  // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
	    PolDutyKindGrid.hiddenPlus=1;           //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
	    PolDutyKindGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
	    PolDutyKindGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
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
	    iArray[1][1]="110px";
	    iArray[1][2]=110;
	    iArray[1][3]=0;

	    iArray[2]=new Array();
	    iArray[2][0]="��������";
	    iArray[2][1]="60px";
	    iArray[2][2]=60;
	    iArray[2][3]=3;

	    iArray[3]=new Array();
	    iArray[3][0]="���ֱ���";
	    iArray[3][1]="60px";
	    iArray[3][2]=600;
	    iArray[3][3]=3;

	    iArray[4]=new Array();
	    iArray[4][0]="�������";
	    iArray[4][1]="100px";
	    iArray[4][2]=100;
	    iArray[4][3]=3;

	    iArray[5]=new Array();
	    iArray[5][0]="��������";
	    iArray[5][1]="110px";
	    iArray[5][2]=110;
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
      iArray[13][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
      iArray[13][4]="llpayconclusion";    //�Ƿ����ô���: null����" "Ϊ������
      iArray[13][5]="13|14";              //���ô�����Ϣ�ֱ���ڵ�13�к͵�14�У�'|'Ϊ�ָ��
      iArray[13][6]="0|1";                //���ô�������ĵ�0����룩���ڵ�1��,��1����ƣ����ڵ�2��


      iArray[14]=new Array();
      iArray[14][0]="��������";
      iArray[14][1]="60px";
      iArray[14][2]=60;
      iArray[14][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����

      iArray[15]=new Array();
      iArray[15][0]="�ܸ�ԭ�����";
      iArray[15][1]="100px";
      iArray[15][2]=100;
      iArray[15][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
      iArray[15][4]="llprotestreason";    //�Ƿ����ô���: null����" "Ϊ������
      iArray[15][5]="15|16";              //���ô�����Ϣ�ֱ���ڵ�13�к͵�14�У�'|'Ϊ�ָ��
      iArray[15][6]="0|1";                //���ô�������ĵ�0����룩���ڵ�1��,��1����ƣ����ڵ�2��

      iArray[16]=new Array();
      iArray[16][0]="�ܸ�ԭ������";
      iArray[16][1]="100px";
      iArray[16][2]=100;
      iArray[16][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
      
      iArray[17]=new Array();
      iArray[17][0]="�ܸ�����";
      iArray[17][1]="60px";
      iArray[17][2]=60;
      iArray[17][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����

      iArray[18]=new Array();
      iArray[18][0]="���ⱸע";
      iArray[18][1]="60px";
      iArray[18][2]=60;
      iArray[18][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
            

      iArray[19]=new Array();
      iArray[19][0]="Ԥ�����";
      iArray[19][1]="60px";
      iArray[19][2]=60;
      iArray[19][3]=0;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
      
      iArray[20]=new Array();
      iArray[20][0]="";
      iArray[20][1]="60px";
      iArray[20][2]=60;
      iArray[20][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
         
      iArray[21]=new Array();
      iArray[21][0]="�������";
      iArray[21][1]="60px";
      iArray[21][2]=60;
      iArray[21][3]=0;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����

      iArray[22]=new Array();
      iArray[22][0]="����ԭ�����";
      iArray[22][1]="100px";
      iArray[22][2]=100;
      iArray[22][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
                              
      iArray[23]=new Array();
      iArray[23][0]="����ԭ������";
      iArray[23][1]="100px";
      iArray[23][2]=100;
      iArray[23][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
            
      iArray[24]=new Array();
      iArray[24][0]="������ע";
      iArray[24][1]="60px";
      iArray[24][2]=60;
      iArray[24][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
      
      iArray[25]=new Array();
      iArray[25][0]="Ԥ����־����";
      iArray[25][1]="60px";
      iArray[25][2]=60;
      iArray[25][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
      
      iArray[26]=new Array();
      iArray[26][0]="Ԥ����־";
      iArray[26][1]="60px";
      iArray[26][2]=60;
      iArray[26][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
      
      iArray[27]=new Array();
      iArray[27][0]="������Դ";
      iArray[27][1]="60px";
      iArray[27][2]=60;
      iArray[27][3]=0;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����                  
                  
        iArray[28]=new Array();
        iArray[28][0]="dutycode";
        iArray[28][1]="60px";
        iArray[28][2]=60;
        iArray[28][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����



	    PolDutyCodeGrid = new MulLineEnter("fm","PolDutyCodeGrid");
	    PolDutyCodeGrid.mulLineCount = 0;        // ��ʾ����
	    PolDutyCodeGrid.displayTitle = 1;
	    PolDutyCodeGrid.locked = 0;
//	      PolDutyCodeGrid.canChk =1;               // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
	    PolDutyCodeGrid.canSel =1;                   // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
        PolDutyCodeGrid.selBoxEventFuncName ="PolDutyCodeGridClick"; //��Ӧ�ĺ���������������
//        PolDutyCodeGrid.selBoxEventFuncParm ="1"; //����Ĳ���,����ʡ�Ը���  
	    PolDutyCodeGrid.hiddenPlus=1;        //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
	    PolDutyCodeGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
	    PolDutyCodeGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert(ex);
  }  
}
/**=========================================================================
    �޸�״̬������
    �޸�ԭ������Ϊ�������Ľ�������ֱ���ʾΪ�ⰸ�����⣬�������������ͣ������������͵ı���
    �� �� �ˣ�����
    �޸����ڣ�2005.06.25
   =========================================================================
**/
</script>
