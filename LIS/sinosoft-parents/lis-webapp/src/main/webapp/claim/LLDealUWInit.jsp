
<%
	//�ļ�����LLDealUWsecondInit.jsp
	//���ܣ����˽���
	//���ڣ�2004-12-23 16:49:22
	//�����ˣ�zhangxing  ���ģ�yuejw
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
//���ҳ��ؼ��ĳ�ʼ����
%>

<script language="JavaScript">

function initForm()
{
  try
  { 
 	initParam();
 	
  	initLLUnfinishedContGrid();	// δ���κ˱���ɵĺ�ͬ���б�ĳ�ʼ��   	
    initLLContGrid();	// �Ѿ����κ˱���ɵĺ�ͬ���б�ĳ�ʼ��  
    
    initQueryLLUnfinishedContGrid(); 
    initQueryLLContGrid();
  }
  catch(ex)
  {
    alter("��LLDealUWsecondInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

//�������ҳ�洫�ݹ����Ĳ���
function initParam()
{
    document.all('Operator').value = nullToEmpty("<%= tG.Operator %>");       
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

// ����������δ���κ˱���ɵĺ�ͬ���б�ĳ�ʼ�� 
function initLLUnfinishedContGrid()
{                               
      var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";              //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";              //�п�
      iArray[0][2]=10;                  //�����ֵ
      iArray[0][3]=0;                   //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�ⰸ��";             //����
      iArray[1][1]="130px";              //�п�
      iArray[1][2]=130;                  //�����ֵ
      iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�����˺���";             //����
      iArray[2][1]="80px";              //�п�
      iArray[2][2]=80;                  //�����ֵ
      iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����������";           //����
      iArray[3][1]="80px";              //�п�
      iArray[3][2]=100;                  //�����ֵ
      iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������  

      iArray[4]=new Array();
      iArray[4][0]="�������";         //����
      iArray[4][1]="80px";               //�п�
      iArray[4][2]=100;                  //�����ֵ
      iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="����Ա";         //����
      iArray[5][1]="80px";               //�п�
      iArray[5][2]=200;                  //�����ֵ
      iArray[5][3]=0; 

      LLUnfinishedContGrid = new MulLineEnter( "document" , "LLUnfinishedContGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LLUnfinishedContGrid.mulLineCount      = 5;   
      LLUnfinishedContGrid.displayTitle      = 1;
      LLUnfinishedContGrid.locked            = 1;
      LLUnfinishedContGrid.canSel            = 0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
      LLUnfinishedContGrid.hiddenPlus        = 1;
      LLUnfinishedContGrid.hiddenSubtraction = 1;
      LLUnfinishedContGrid.loadMulLine(iArray); 
      }
      catch(ex)
      {
        alert(ex);
      }
}


// ���������Ѿ����κ˱���ɵĺ�ͬ���б�ĳ�ʼ�� 
function initLLContGrid()
{                               
      var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";              //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";              //�п�
      iArray[0][2]=10;                  //�����ֵ
      iArray[0][3]=0;                   //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�ⰸ��";             //����
      iArray[1][1]="130px";              //�п�
      iArray[1][2]=130;                  //�����ֵ
      iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���κ�";             //����
      iArray[2][1]="80px";              //�п�
      iArray[2][2]=80;                  //�����ֵ
      iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�������";           //����
      iArray[3][1]="80px";              //�п�
      iArray[3][2]=100;                  //�����ֵ
      iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������  

      iArray[4]=new Array();
      iArray[4][0]="����Ա";         //����
      iArray[4][1]="80px";               //�п�
      iArray[4][2]=100;                  //�����ֵ
      iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="״̬";         //����
      iArray[5][1]="80px";               //�п�
      iArray[5][2]=200;                  //�����ֵ
      iArray[5][3]=0; 
      
      LLContGrid = new MulLineEnter( "document" , "LLContGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LLContGrid.mulLineCount = 5;   
      LLContGrid.displayTitle = 1;
      LLContGrid.locked = 1;
      LLContGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
      LLContGrid.selBoxEventFuncName = "showSecondUWInput"; //���RadioBoxʱ��Ӧ�ĺ�����
      LLContGrid.hiddenPlus=1;
      LLContGrid.hiddenSubtraction=1;
      LLContGrid.loadMulLine(iArray); 
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
