<%@include file="/i18n/language.jsp"%>
<html>
<%
//name :ReComManageInit.jsp
//function :Manage 
//Creator :
//date :2006-08-15
%>

<!--�û�У����-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/CCodeOperate.js"></SCRIPT>
<%
 		GlobalInput tG = new GlobalInput(); 
  	tG=(GlobalInput)session.getAttribute("GI");
  	String Operator=tG.Operator;
  	String Comcode=tG.ManageCom;
 		String CurrentDate= PubFun.getCurrentDate();   
    String tCurrentYear=StrTool.getVisaYear(CurrentDate);
    String tCurrentMonth=StrTool.getVisaMonth(CurrentDate);
    String tCurrentDate=StrTool.getVisaDay(CurrentDate);                	               	
 %>
<script type="text/javascript">
function initInpBox()
{
  try
  {
    fm.ReComCode.value="";
    fm.ReComName.value="";
    fm.PostalCode.value="";
    fm.Address.value="";
    fm.FaxNo.value="";
    fm.ComType.value="";
    fm.ComTypeName.value="";
    fm.Note.value	="";
    
    
  }
  catch(ex)
  {
    myAlert("��ʼ���������!");
  }
}
;

// ������ĳ�ʼ��
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    myAlert("2��CertifyDescInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initRelateGrid();
  }
  catch(re)
  {
    myAlert("3CertifyDescInit.jsp-->"+"��ʼ���������!");
  }
}

function initRelateGrid() 
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
    iArray[1][0]="��ϵ��";
    iArray[1][1]="70px";
    iArray[1][2]=100;
    iArray[1][3]=1;
    

    iArray[2]=new Array();
    iArray[2][0]="����";         	//����
    iArray[2][1]="100px";            	//�п�
    iArray[2][2]=200;            			//�����ֵ
    iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="ְ��";         			//����
    iArray[3][1]="80px";            	//�п�
    iArray[3][2]=60;            			//�����ֵ
    iArray[3][3]=1;              			//2��ʾ�Ǵ���ѡ��

    iArray[4]=new Array();
    iArray[4][0]="��ϵ�绰";         				//����
    iArray[4][1]="100px";            //�п�
    iArray[4][2]=60;            		//�����ֵ
    iArray[4][3]=1;              		//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[5]=new Array();
    iArray[5][0]="�ֻ�";         				//����
    iArray[5][1]="100px";            //�п�
    iArray[5][2]=60;            		//�����ֵ
    iArray[5][3]=1;              		//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[6]=new Array();
    iArray[6][0]="����";         				//����
    iArray[6][1]="100px";            //�п�
    iArray[6][2]=60;            		//�����ֵ
    iArray[6][3]=1;              		//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[7]=new Array();
    iArray[7][0]="��������";         				//����
    iArray[7][1]="200px";            //�п�
    iArray[7][2]=100;            		//�����ֵ
    iArray[7][3]=1;              		//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="��ϵ�˱���";
    iArray[8][1]="70px";
    iArray[8][2]=100;
    iArray[8][3]=3;
    
    RelateGrid = new MulLineEnter( "fm" , "RelateGrid" );
    RelateGrid.mulLineCount = 0;
    RelateGrid.displayTitle = 1;
    //RelateGrid.canSel=1;
    RelateGrid.loadMulLine(iArray);
    RelateGrid.detailInfo="������ʾ��ϸ��Ϣ";
  }
  catch(ex)
  {
    myAlert("��ʼ��ʱ����:"+ex);
  }
}

</script>


