<%
//�������ƣ�TempFeeInputPrintInit.jsp
//�����ܣ����շ�Ʊ�ݴ�ӡ
//�������ڣ�2005-12-21 14:55
//������  ����Ρ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");

	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	String strOperator = globalInput.Operator;
%>

<script language="JavaScript">
function initForm()
{
	//alert("initForm");
  try
  {
    initChequeGrid();
    initCustomertGrid();
    initRNPremGrid();
		initElementtype();
  }
  catch(re)
  {
    alert("TempFeeInputPrintInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

//֧Ʊ��Ʊ���վݺ�
function initChequeGrid()
{
	//alert("initChequeGrid11");
    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            	//�п�
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�ͻ�����";         	//����
      iArray[1][1]="60px";            	//�п�
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="֧Ʊ/��Ʊ����";     //����
      iArray[2][1]="130px";            	//�п�
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="������/Ͷ������/��ȫ��";     //����
      iArray[3][1]="130px";            	//�п�
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��Ʊ��λ";         	//����
      iArray[4][1]="120px";            	//�п�
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��������";         	//����
      iArray[5][1]="80px";            	//�п�
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�ɷѽ��";         	//����
      iArray[6][1]="60px";            	//�п�
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="ҵ��Ա����";         //����
      iArray[7][1]="80px";            	  //�п�
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="ҵ��Ա����";        //����
      iArray[8][1]="60px";            	 //�п�
      iArray[8][3]=0;              		 //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="�շѻ���";        //����
      iArray[9][1]="100px";            	 //�п�
      iArray[9][3]=0;              		 //�Ƿ���������,1��ʾ����0��ʾ������      

      ChequeGrid = new MulLineEnter( "fm" , "ChequeGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ChequeGrid.mulLineCount = 3;
      ChequeGrid.displayTitle = 1;
      ChequeGrid.locked = 1;
      ChequeGrid.canSel = 1;
      ChequeGrid.canChk = 0;
      ChequeGrid.loadMulLine(iArray);
      ChequeGrid.selBoxEventFuncName = "easyQueryAddClick";
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }	
}

//�ͻ�Ԥ���վ�
function initCustomertGrid()
{
	//alert("initCustomertGrid");
    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            	//�п�
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�ͻ����";         	//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="100px";            	//�п�
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�ͻ�����";         	//����
      iArray[2][1]="150px";            	//�п�
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�ɷѽ��";         	//����
      iArray[3][1]="120px";            	//�п�
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������?

      iArray[4]=new Array();
      iArray[4][0]="�ɷѷ�ʽ";         	//����
      iArray[4][1]="60px";            	//�п�
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="Ԥ���";       	   	//����
      iArray[5][1]="130px";            	//�п�
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="Ԥ��ʵ����";   	   	//����
      iArray[6][1]="130px";            	//�п�
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="��������";         	//����
      iArray[7][1]="80px";            	//�п�
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="����Ա";         	  //����
      iArray[8][1]="60px";             	//�п�
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      CustomertGrid = new MulLineEnter( "fm" , "CustomertGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      CustomertGrid.mulLineCount = 3;
      CustomertGrid.displayTitle = 1;
      CustomertGrid.locked = 1;
      CustomertGrid.canSel = 1;
      CustomertGrid.canChk = 0;
      CustomertGrid.loadMulLine(iArray);
      CustomertGrid.selBoxEventFuncName = "easyQueryAddClick";
      }
      catch(ex)
      {
        alert(ex);
      }	
}

//Ԥ�����ڱ����վ�
function initRNPremGrid()
{
    var iArray = new Array();

      try
      {
     // alert("faint");
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            	//�п�
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="Ͷ��������";        //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="50px";            	//�п�
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         	//����
      iArray[2][1]="70px";            	//�п�
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="������";            //����
      iArray[3][1]="150px";            	//�п�
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="Ԥ���";            //����
      iArray[4][1]="150px";            	//�п�
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="���ѽ��";          //����
      iArray[5][1]="60px";            	//�п�
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="����Ա";         	//����
      iArray[6][1]="60px";            	//�п�
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="������";       		//����
      iArray[7][1]="50px";            	//�п�
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="���֤��";         	//����
      iArray[8][1]="160px";            	//�п�
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      RNPremGrid = new MulLineEnter( "fm" , "RNPremGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      RNPremGrid.mulLineCount = 3;
      RNPremGrid.displayTitle = 1;
      RNPremGrid.locked = 1;
      RNPremGrid.canSel = 1;
      RNPremGrid.canChk = 0;
      RNPremGrid.loadMulLine(iArray);
      RNPremGrid.selBoxEventFuncName = "easyQueryAddClick";
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
