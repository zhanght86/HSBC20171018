<%
//�������ƣ�AgentTrussInit.jsp
//�����ܣ�
//�������ڣ�2003-05-28
//������  ��Kevin
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
	String strManageCom = globalInput.ManageCom;
%>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
    document.all('BranchAttr').value = '';
		document.all('ManageCom').value = '<%=strManageCom%>';
  }
  catch(ex)
  {
    alert("��AgentTrussPInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
    initPolGrid();   
  }
  catch(re)
  {
    alert("AgentTrussInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


var PolGrid;          //����Ϊȫ�ֱ������ṩ��displayMultilineʹ��


// Ͷ������Ϣ�б�ĳ�ʼ��
function initPolGrid()
{                               
	var iArray = new Array();
	
  try {
	  iArray[0]=new Array();
	  iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	  iArray[0][1]="30px";            		//�п�
	  iArray[0][2]=10;            			//�����ֵ
	  iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[1]=new Array();
	  iArray[1][0]="���������";         		//����
	  iArray[1][1]="100px";            		//�п�
	  iArray[1][2]=100;            			//�����ֵ
	  iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		
	  iArray[2]=new Array();
	  iArray[2][0]="����";         		//����
	  iArray[2][1]="200px";            		//�п�
	  iArray[2][2]=100;            			//�����ֵ
	  iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[3]=new Array();
	  iArray[3][0]="�������";      		//����
	  iArray[3][1]="100px";            		//�п�
	  iArray[3][2]=200;            			//�����ֵ
	  iArray[3][3]=2; 
      iArray[3][4]="station";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[3][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[3][9]="�������|code:station&NOTNULL";
      iArray[3][18]=250;
      iArray[3][19]= 0 ;
	
	  iArray[4]=new Array();
	  iArray[4][0]="չҵ��������";         		//����
	  iArray[4][1]="200px";            		//�п�
	  iArray[4][2]=200;            			//�����ֵ
	  iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[5]=new Array();
	  iArray[5][0]="����";         		//����
	  iArray[5][1]="100px";            		//�п�
	  iArray[5][2]=100;            			//�����ֵ
	  iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[6]=new Array();
	  iArray[6][0]="������Ա";         		//����
	  iArray[6][1]="100px";            		//�п�
	  iArray[6][2]=100;            			//�����ֵ
	  iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
		
	  PolGrid = new MulLineEnter( "fmSave" , "PolGrid" ); 
	  //��Щ���Ա�����loadMulLineǰ
	  PolGrid.mulLineCount = 10;   
	  PolGrid.displayTitle = 1;
	  //PolGrid.locked = 1;
	  PolGrid.canSel = 1;
	  PolGrid.hiddenPlus=1;
	  PolGrid.hiddenSubtraction=1;
	  PolGrid.loadMulLine(iArray);  
	  
  } catch(ex) {
		alert(ex);
	}
}
</script>
