<%
//�������ƣ�LFGetNoticeInit.jsp
//�����ܣ�
//�������ڣ�2002-08-16 15:39:06
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
		document.all('FiscalYear').value = '';
		document.all('GrpContNo').value = '';
		document.all('RiskCode').value = '212401';
  }
  catch(ex)
  {
    alert("��GrpInterestInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
    initPolGrid();   
    //document.all('ErrorLog').disabled=true;
  }
  catch(re)
  {
    alert("GrpInterestInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
 var PolGrid;          //����Ϊȫ�ֱ������ṩ��displayMultilineʹ��
// Ͷ������Ϣ�б�ĳ�ʼ��
function initPolGrid()
{                               
  var iArray = new Array();
  try
  {
	  iArray[0]=new Array();
	  iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	  iArray[0][1]="30px";            	//�п�
	  iArray[0][2]=10;            			//�����ֵ
	  iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[1]=new Array();
	  iArray[1][0]="�ŵ���";        		//����
	  iArray[1][1]="120px";            	//�п�
	  iArray[1][2]=100;            			//�����ֵ
	  iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[2]=new Array();
	  iArray[2][0]="�ŵ�����";        	//����
	  iArray[2][1]="200px";            	//�п�
	  iArray[2][2]=100;            			//�����ֵ
	  iArray[2][3]=0; 
	
	  iArray[3]=new Array();
	  iArray[3][0]="�������";            //����
	  iArray[3][1]="80px";            	//�п�
	  iArray[3][2]=100;            			//�����ֵ
	  iArray[3][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
    
	  iArray[4]=new Array();
	  iArray[4][0]="��Ч����";            //����
	  iArray[4][1]="80px";            	//�п�
	  iArray[4][2]=100;            			//�����ֵ
	  iArray[4][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
    
	  iArray[5]=new Array();
	  iArray[5][0]="ӡˢ��";            //����
	  iArray[5][1]="0px";            	//�п�
	  iArray[5][2]=100;            			//�����ֵ
	  iArray[5][3]=2; 									//�Ƿ���������,1��ʾ����0��ʾ������
    
//	  iArray[5]=new Array();
//	  iArray[5][0]="�ŵ���Լ";            //����
//	  iArray[5][1]="120px";            	//�п�
//	  iArray[5][2]=100;            			//�����ֵ
//	  iArray[5][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
    
    PolGrid = new MulLineEnter( "document" , "PolGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    PolGrid.mulLineCount = 5;   
    PolGrid.displayTitle = 1;
    PolGrid.canSel = 0;
    PolGrid.hiddenPlus=1;
    PolGrid.hiddenSubtraction=1;
    PolGrid.loadMulLine(iArray);  
  }
  catch(ex)
  {
    alert("GrpInterestInit.jsp-->initPolGrid�����з����쳣:"+ex);
  }
}
</script>
