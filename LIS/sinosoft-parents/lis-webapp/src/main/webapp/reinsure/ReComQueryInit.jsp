<%@include file="/i18n/language.jsp"%>
<%
//Creator :�ű�
//Date :2006-08-18
%>
<!--�û�У����-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.vdb.*"%>
<%@page import = "com.sinosoft.lis.vbl.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/CCodeOperate.js"></SCRIPT>
<%
	
%>
<script type="text/javascript">
function initInpBox()
{
  try
  { 
  }
  catch(ex)
  {
    myAlert("��ʼ���������!");
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
    myAlert("2��CertifyDescInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initReComGrid();
  }
  catch(re)
  {
    myAlert("3CertifyDescInit.jsp-->"+"��ʼ���������!");
  }
}

function initReComGrid()
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
	      iArray[1][0]="��˾����";    	//����
	      iArray[1][1]="150px";            		//�п�
	      iArray[1][2]=100;            			//�����ֵ
	      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	      iArray[2]=new Array();
	      iArray[2][0]="��˾����";         			//����
	      iArray[2][1]="100px";            		//�п�
	      iArray[2][2]=100;            			//�����ֵ
	      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	 
	      iArray[3]=new Array();
	      iArray[3][0]="��˾��ַ";         			//����
	      iArray[3][1]="60px";            		//�п�
	      iArray[3][2]=60;            			//�����ֵ
	      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	      
	      iArray[4]=new Array();
	      iArray[4][0]="��������";         			//����
	      iArray[4][1]="60px";            		//�п�
	      iArray[4][2]=60;            			//�����ֵ
	      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	      
	      iArray[5]=new Array();
	      iArray[5][0]="����";         			//����
	      iArray[5][1]="100px";            		//�п�
	      iArray[5][2]=1000;            			//�����ֵ
	      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
				
	      ReComGrid = new MulLineEnter( "fm" , "ReComGrid" ); 
	      ReComGrid.mulLineCount = 0;   
	      ReComGrid.displayTitle = 1;
	      ReComGrid.canSel=1;
	      ReComGrid.locked = 1;	
				ReComGrid.hiddenPlus = 1;
				ReComGrid.hiddenSubtraction = 1;
	      ReComGrid.loadMulLine(iArray);  
	      ReComGrid.detailInfo="������ʾ��ϸ��Ϣ";
     
      }
      catch(ex)
      {
        myAlert(ex);
      }
}
</script>