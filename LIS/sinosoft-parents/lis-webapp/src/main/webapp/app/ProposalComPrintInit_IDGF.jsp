<%
//�������ƣ�ProposalComPrintInit_IDGF.jsp
//�����ܣ�
//�������ڣ�2006-10-26 12:03
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.utility.*"%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
  GlobalInput globalInput = (GlobalInput)session.getValue("GI");
  String strManageCom = globalInput.ComCode;
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
  try
  {
  }
  catch(ex)
  {
    alert("��ProposalComPrintInit_IDGF.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
  	
    initInpBox();
    initPolGrid();
    document.all('ManageCom').value = <%=strManageCom%>;
    var tManageCode=<%=strManageCom%>;
    //alert(tManageCode);
    if(tManageCode!="86")
    {
    	 document.all("divErrorMInfo").style.display="none";
    	}
  }
  catch(re)
  {
    alert("ProposalComPrintInit_IDGF.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
 var PolGrid;          //����Ϊȫ�ֱ������ṩ��displayMultilineʹ��
// ������Ϣ�б�ĳ�ʼ��
function initPolGrid()
{                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="����";         		//����
      iArray[1][1]="200px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[2]=new Array();
      iArray[2][0]="����";         		//����
      iArray[2][1]="200px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0; 

      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 5;   
      PolGrid.displayTitle = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.canSel = 0;
      PolGrid.canChk = 1;
 
      PolGrid.loadMulLine(iArray);
      
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
// ������Ϣ�б�ĳ�ʼ��
function initErrorGrid()
{
	var iArray = new Array();

	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";	//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";	//�п�
		iArray[0][2]=10;	//�����ֵ
		iArray[0][3]=0;	//�Ƿ���������,1��ʾ����0��ʾ������

		iArray[1]=new Array();
		iArray[1][0]="������";
		iArray[1][1]="140px";           
		iArray[1][2]=100;            	
		iArray[1][3]=0;
		
		
		iArray[2]=new Array();
		iArray[2][0]="������Ϣ";
		iArray[2][1]="400px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="��������";
		iArray[3][1]="80px";
		iArray[3][2]=200;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="����ʱ��";
		iArray[4][1]="80px";
		iArray[4][2]=200;
		iArray[4][3]=0;
		
		
		iArray[5]=new Array();
		iArray[5][0]="�������";
		iArray[5][1]="80px";
		iArray[5][2]=200;
		iArray[5][3]=0;

		ErrorGrid = new MulLineEnter( "fm" , "ErrorGrid" );
		//��Щ���Ա�����loadMulLineǰ
		ErrorGrid.mulLineCount = 5;
		ErrorGrid.displayTitle = 1;
		ErrorGrid.hiddenPlus = 1;
		ErrorGrid.hiddenSubtraction = 1;
		ErrorGrid.canSel = 0;
		ErrorGrid.canChk = 0;
		ErrorGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>
