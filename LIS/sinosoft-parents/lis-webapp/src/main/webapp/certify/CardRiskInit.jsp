<%
//name :CardRiskInit.jsp
//function :������ֶ���
//Creator :mw
//date :2009-05-13
%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.vdb.*"%>
<%@page import = "com.sinosoft.lis.bl.*"%>
<%@page import = "com.sinosoft.lis.vbl.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">
function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initCardRiskGrid();
  }
  catch(re)
  {
    alert("CardRiskInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initInpBox()
{
  try
  {       
    document.all('CertifyCode').value = '';
    document.all('CertifyName').value = '';
    document.all('RiskCode').value = '';
    document.all('RiskName').value = '';
  }
  catch(ex)
  {
    alert("���г�ʼ���ǳ��ִ��󣡣�����");
  }
}

// ������ĳ�ʼ��
function initSelBox()
{
  try {
  }
  catch(ex)
  {
    alert("��CardRiskInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initCardRiskGrid()
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
    iArray[1][0]="��֤����";
    iArray[1][1]="100px";
    iArray[1][2]=100;
    iArray[1][3]=2;
    iArray[1][4]="CertifyCode";
    iArray[1][9]="������Ϣ:��֤����|NOTNULL&INT"; 		//�����ʽ

    iArray[2]=new Array();
    iArray[2][0]="���ֱ���";
    iArray[2][1]="100px";
    iArray[2][2]=100;
    iArray[2][3]=2;
    iArray[2][4]="RiskCode";
    //iArray[2][9]="������Ϣ:���ִ���|NOTNULL&INT"; 		//�����ʽ
    
    iArray[3]=new Array();
    iArray[3][0]="��Ʒ����";
    iArray[3][1]="100px";
    iArray[3][2]=100;
    iArray[3][3]=2;
    iArray[3][4]="portfolio";


    iArray[4]=new Array();
    iArray[4][0]="����";         			//����
    iArray[4][1]="100px";            		//�п�
    iArray[4][2]=200;            			//�����ֵ
    iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	iArray[4][9]="������Ϣ:����|NOTNULL&NUM";   		//�����ʽ

    iArray[5]=new Array();
    iArray[5][0]="��������";         	//����
    iArray[5][1]="100px";            	//�п�
    iArray[5][2]=60;            			//�����ֵ
    iArray[5][3]=2;              			//2��ʾ�Ǵ���ѡ��
    iArray[5][10]="PremProp";					//��������
    iArray[5][11]="0|^1|�̶�����|^2|���ѱ���|^3|���Ᵽ��";

    iArray[6]=new Array();
    iArray[6][0]="���ѷݶ�";         			//����
    iArray[6][1]="100px";            		//�п�
    iArray[6][2]=60;            			//�����ֵ
    iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

    CardRiskGrid = new MulLineEnter( "document" , "CardRiskGrid" );
    CardRiskGrid.mulLineCount = 5;
    CardRiskGrid.displayTitle = 1;
    CardRiskGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert("CardRiskInit.jsp-->��ʼ�����������Ϣʱ����"+ex);
  }
}

</script>


