<%
//Creator :������
//Date :2003-04-18
%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.vdb.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">
function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    //initCardRiskGrid();
    initCertifyDescGrid();
  }
  catch(re)
  {
    alert("CertifyDescInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initInpBox()
{
  try
  {       
    document.all('CertifyCode').value = '';
    document.all('CertifyName').value = '';
    document.all('CertifyClass').value = '';
    document.all('CertifyClassName').value = '';
    document.all('CertifyClass2').value = '';
    document.all('CertifyClass2Name').value = '';   
    document.all('State').value = '0';
    document.all('StateName').value = '����';
    document.all('HaveNumber').value = '';
    document.all('HaveNumberName').value = '';
    document.all('CertifyLength').value = '';
    document.all('TackBackFlag').value = '';
    document.all('TackBackFlagName').value = '';
    //document.all('HavePrice').value = '';
    document.all('CertifyPrice').value = '';
    document.all('Unit').value = '';
    document.all('MaxPrintNo').value = '';
    document.all('HaveLimit').value = '';
    document.all('HaveLimitName').value = '';
    document.all('MaxUnit1').value = '';
    document.all('MaxUnit2').value = '';
    document.all('HaveValidate').value = '';
    document.all('HaveValidateName').value = '';
    document.all('Validate1').value = '';
    document.all('Validate2').value = '';
    document.all('Note').value = '';
  }
  catch(ex)
  {
    alert("���г�ʼ���ǳ��ִ��󣡣�����");
  }
}

function RegisterDetailClick(cObj)
{
  	var ex,ey;
  	ex = window.event.clientX+document.body.scrollLeft;  //�õ��¼�������x
  	ey = window.event.clientY+document.body.scrollTop;   //�õ��¼�������y
  	divDetailInfo.style.left=ex;
  	divDetailInfo.style.top =ey;
    divDetailInfo.style.display ='';
}

// ������ĳ�ʼ��
function initSelBox()
{
  try {}
  catch(ex)
  {
    alert("2��CertifyDescInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
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
    iArray[1][0]="���ִ���";
    iArray[1][1]="100px";
    iArray[1][2]=100;
    iArray[1][3]=2;
    iArray[1][4]="RiskCode";
    iArray[1][9]="������Ϣ:���ִ���|NOTNULL&INT"; 		//�����ʽ

    iArray[2]=new Array();
    iArray[2][0]="����";         			//����
    iArray[2][1]="100px";            		//�п�
    iArray[2][2]=200;            			//�����ֵ
    iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	iArray[2][9]="������Ϣ:����|NOTNULL&NUM";   		//�����ʽ

    iArray[3]=new Array();
    iArray[3][0]="��������";         	//����
    iArray[3][1]="100px";            	//�п�
    iArray[3][2]=60;            			//�����ֵ
    iArray[3][3]=2;              			//2��ʾ�Ǵ���ѡ��
    iArray[3][10]="PremProp";					//��������
    iArray[3][11]="0|^1|�̶�����|^2|���ѱ���|^3|���Ᵽ��";

    iArray[4]=new Array();
    iArray[4][0]="���ѷݶ�";         			//����
    iArray[4][1]="100px";            		//�п�
    iArray[4][2]=60;            			//�����ֵ
    iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

    CardRiskGrid = new MulLineEnter( "document" , "CardRiskGrid" );
    CardRiskGrid.mulLineCount = 0;
    CardRiskGrid.displayTitle = 1;
    CardRiskGrid.loadMulLine(iArray);
    CardRiskGrid.detailInfo="������ʾ��ϸ��Ϣ";
  }
  catch(ex)
  {
    alert("��ʼ�����������Ϣʱ����"+ex);
  }
}

function initCertifyDescGrid()
{
	var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��֤����";    	//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��֤����";         			//����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
      iArray[3]=new Array();
      iArray[3][0]="��֤����";         			//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��֤ҵ������";         			//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�Ƿ��м�";         		//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

      iArray[6]=new Array();
      iArray[6][0]="�Ƿ��к�";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="״̬";         		//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      CertifyDescGrid = new MulLineEnter( "document" , "CertifyDescGrid" ); 
      CertifyDescGrid.mulLineCount = 5;   
      CertifyDescGrid.displayTitle = 1;
	  CertifyDescGrid.hiddenSubtraction=1;
	  CertifyDescGrid.hiddenPlus=1;
      CertifyDescGrid.canSel=1;
      CertifyDescGrid.locked = 1;	
      CertifyDescGrid.selBoxEventFuncName = "showDetail";
      CertifyDescGrid.loadMulLine(iArray);  
      CertifyDescGrid.detailInfo="������ʾ��ϸ��Ϣ";
    }
    catch(ex)
    {
      alert("��ʼ����ѯ����б�ʱ"+ex);
    }
}
</script>


