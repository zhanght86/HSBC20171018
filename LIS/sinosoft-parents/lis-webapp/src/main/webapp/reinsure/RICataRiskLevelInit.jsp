<%@include file="/i18n/language.jsp"%>
<html>
<%
//name :LRProfitLossCalInit.jsp
//function : 
//Creator :zhangbin
//date :2007-3-14
%>

<!--�û�У����-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<%
	GlobalInput tG = new GlobalInput(); 
  	tG=(GlobalInput)session.getAttribute("GI");
  	String Operator=tG.Operator;
  	String Comcode=tG.ManageCom;
 	String CurrentDate= PubFun.getCurrentDate();   
    String tCurrentYear=StrTool.getVisaYear(CurrentDate);    	               	
 %>
<script type="text/javascript">

function initInpBox()
{
	try
	{
		fm.CalYear.value="2010";
	}
	catch(ex)
	{
		myAlert("���г�ʼ���ǳ��ִ���");
	}
}
;

// �����б�ĳ�ʼ��
function initSelBox(){
  try{
  }
  catch(ex){
    myAlert("2��CertifyDescInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
	try
	{
		initInpBox();
		initSelBox();
		initCatastropheGrid();
	}
	catch(re)
	{
		myAlert("3CertifyDescInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
	}
}

function initCatastropheGrid() {
  var iArray = new Array();
  try{
    iArray[0]=new Array();
    iArray[0][0]="���";
    iArray[0][1]="30px";
    iArray[0][2]=10;
    iArray[0][3]=0;

    iArray[1]=new Array();
    iArray[1][0]="���ս��";    //����
    iArray[1][1]="50px";            	//�п�
    iArray[1][2]=200;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[2]=new Array();
    iArray[2][0]="��������";
    iArray[2][1]="50px";
    iArray[2][2]=100;
    iArray[2][3]=0;

    iArray[3]=new Array();
    iArray[3][0]="�����ܶ�";     //����
    iArray[3][1]="50px";            	//�п�
    iArray[3][2]=200;            //�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[4]=new Array();
    iArray[4][0]="������";     //����
    iArray[4][1]="50px";            	//�п�
    iArray[4][2]=200;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
       
    CatastropheGrid = new MulLineEnter( "fm" , "CatastropheGrid" );
    CatastropheGrid.mulLineCount =0;
    CatastropheGrid.displayTitle = 1;
    CatastropheGrid.hiddenPlus=1;   
    CatastropheGrid.hiddenSubtraction=1; 
    //CatastropheGrid.canSel=1;
    CatastropheGrid.loadMulLine(iArray);
    CatastropheGrid.detailInfo="������ʾ��ϸ��Ϣ";
  }
  catch(ex)
  {
    myAlert("��ʼ��ʱ����:"+ex);
  }
}

</script>


