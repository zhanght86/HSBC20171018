<%
//�������ƣ�FIDataBaseLinkInit.jsp
//�����ܣ����ݽӿ����ù���
//�������ڣ�2008-08-04
//������  �����  
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.vdb.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/CCodeOperate.js"></SCRIPT>
<%
  GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("��ҳ��ʱ�������µ�¼");
		return;
	}
	String strOperator = globalInput.Operator;
%>                          
<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
  	fm.reset();
  	document.all('InterfaceCode').value = '';
		fm.Operator.value = '<%= strOperator %>';
  	document.all('InterfaceName').value = '';
  	document.all('DBType').value = '';
  	document.all('IP').value = '';
  	document.all('Port').value = '';
  	document.all('DBName').value = '';
  	document.all('ServerName').value = '';
  	document.all('UserName').value = '';
    document.all('PassWord').value = '';
    document.all('ManageCom').value = '86';
  }
  catch(ex)
  {
    alert("��FIDataBaseLinkInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��FIDataBaseLinkInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initFIDataBaseLinkGrid();
  }
  catch(re)
  {
    alert("��FIDataBaseLinkInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initFIDataBaseLinkGrid()
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
      iArray[1][0]="�ӿڱ���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="60px";         			//�п�
      iArray[1][2]=10;          			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�ӿ�����";    	//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="���ݿ�����";         			//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="IP";         			//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="�˿ں�";         			//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
      iArray[6]=new Array();
      iArray[6][0]="���ݿ�����";         			//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="��������";         			//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="�û���";         		//����
      iArray[8][1]="60px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
      
      iArray[9]=new Array();
      iArray[9][0]="����";         		//����
      iArray[9][1]="60px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
      


      FIDataBaseLinkGrid = new MulLineEnter( "document" , "FIDataBaseLinkGrid" ); 
      FIDataBaseLinkGrid.mulLineCount = 5;   
      FIDataBaseLinkGrid.displayTitle = 1;
      FIDataBaseLinkGrid.canSel=1;
      FIDataBaseLinkGrid.locked = 1;	
	  	FIDataBaseLinkGrid.hiddenPlus = 1;
	  	FIDataBaseLinkGrid.hiddenSubtraction = 1;
      FIDataBaseLinkGrid.loadMulLine(iArray);  
      FIDataBaseLinkGrid.detailInfo="������ʾ��ϸ��Ϣ";
     
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
