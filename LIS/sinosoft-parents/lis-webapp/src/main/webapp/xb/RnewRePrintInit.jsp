<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�RePrintInit.jsp
//�����ܣ�
//�������ڣ�2003-03-31
//������  ��Kevin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	String strManageCom = globalInput.ManageCom;
%>                            

<script language="JavaScript">
var strManageCom = <%=strManageCom%>;
var PolGrid;          //����Ϊȫ�ֱ������ṩ��displayMultilineʹ��

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
      
}

function initForm()
{
  try
  {
  	//manageCom = '<%= strManageCom %>';
    initInpBox();
	  initPolGrid();
  }
  catch(re)
  {
    alert("RePrintInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initPolGrid()
{                               
  var iArray = new Array();
      
  try {
	  iArray[0]=new Array();
	  iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	  iArray[0][1]="30px";            	//�п�
	  iArray[0][2]=10;            			//�����ֵ
	  iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[1]=new Array();
	  iArray[1][0]="֪ͨ����ˮ��";         		//����
	  iArray[1][1]="160px";            	//�п�
	  iArray[1][2]=100;            			//�����ֵ
	  iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
	  
	  iArray[2]=new Array();
	  iArray[2][0]="��������";        //����
	  iArray[2][1]="0px";            	//�п�
	  iArray[2][2]=100;            			//�����ֵ
	  iArray[2][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������  
	
	  iArray[3]=new Array();
	  iArray[3][0]="������";       		//����
	  iArray[3][1]="160px";            	//�п�
	  iArray[3][2]=100;            			//�����ֵ
	  iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[4]=new Array();
	  iArray[4][0]="�������";         	//����
	  iArray[4][1]="80px";            	//�п�
	  iArray[4][2]=100;            			//�����ֵ
	  iArray[4][3]=0;
	
	  iArray[5]=new Array();
	  iArray[5][0]="�����˱���";        //����
	  iArray[5][1]="100px";            	//�п�
	  iArray[5][2]=200;            			//�����ֵ
	  iArray[5][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������    
	  
	  iArray[6]=new Array();
	  iArray[6][0]="missionid";         	//����
	  iArray[6][1]="0px";            	//�п�
	  iArray[6][2]=100;            			//�����ֵ
	  iArray[6][3]=0;
	
	  iArray[7]=new Array();
	  iArray[7][0]="submissionid";        //����
	  iArray[7][1]="0px";            	//�п�
	  iArray[7][2]=100;            			//�����ֵ
	  iArray[7][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[8]=new Array();
	  iArray[8][0]="activityid";        //����
	  iArray[8][1]="0px";            	//�п�
	  iArray[8][2]=100;            			//�����ֵ
	  iArray[8][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������ 	 

	
	  PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
	  //��Щ���Ա�����loadMulLineǰ
	  PolGrid.mulLineCount = 10;   
	  PolGrid.displayTitle = 1;
	  PolGrid.canSel = 1;
      PolGrid.locked = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
	  PolGrid.loadMulLine(iArray);  
	
	} catch(ex) {
		alert(ex);
	}
}

</script>
