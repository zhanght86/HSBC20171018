<%
//�������ƣ�SRiskOutLisInit.jsp
//�����ܣ�
//�������ڣ�2003-11-04
//������  ��SunXY
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	String strManageCom = globalInput.ComCode;
%>                            

<script language="JavaScript">

var PolGrid;          //����Ϊȫ�ֱ������ṩ��displayMultilineʹ��

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
  try
  {
  	fm.reset();     
  	fm.SubType.vlaue = '';                              
  	fm.ScanType.vlaue = '';                              
  }
  catch(ex)
  {
    alert("��MeetInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
  	initInpBox();
	initPolGrid();
	
	//document.all('ManageCom').value = <%=strManageCom%>;
	 
  }
  catch(re)
  {
    alert("MeetInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// Ͷ������Ϣ�б�ĳ�ʼ��
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
	  iArray[1][0]="ӡˢ��";         		//����
	  iArray[1][1]="140px";            	//�п�
	  iArray[1][2]=100;            			//�����ֵ
	  iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[2]=new Array();
	  iArray[2][0]="��ˮ��";       		//����
	  iArray[2][1]="140px";            	//�п�
	  iArray[2][2]=100;            			//�����ֵ
	  iArray[2][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[3]=new Array(); 
	  iArray[3][0]="�������";         	//���� 
	  iArray[3][1]="100px";            	//�п� iArray[3][2]=100; 
	  iArray[3][2]=200 ;
	  iArray[3][3]= 0 ;
	
	  iArray[4]=new Array();
	  iArray[4][0]="��֤����";        //����
	  iArray[4][1]="60px";            	//�п�
	  iArray[4][2]=200;            			//�����ֵ
	  iArray[4][3]=0; 
     
      
    iArray[5]=new Array();
	  iArray[5][0]="��֤ҳ��";        //����
	  iArray[5][1]="80px";            	//�п�
	  iArray[5][2]=200;            			//�����ֵ
	  iArray[5][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
	  
    iArray[6]=new Array();
	  iArray[6][0]="ɨ������";         	//����
	  iArray[6][1]="120px";            	//�п�
	  iArray[6][2]=200; 
	  iArray[6][3]=0;
	  
    iArray[7]=new Array();
	  iArray[7][0]="����Ա����";         	//����
	  iArray[7][1]="120px";            	//�п�
	  iArray[7][2]=200; 
	  iArray[7][3]=0;
	  
    iArray[8]=new Array();
	  iArray[8][0]="��ע";         	//����
	  iArray[8][1]="120px";            	//�п�
	  iArray[8][2]=200; 
	  iArray[8][3]=0;
	  
	
      
	  PolGrid = new MulLineEnter( "fmSave" , "PolGrid" ); 
	  //��Щ���Ա�����loadMulLineǰ
	  PolGrid.mulLineCount = 10;   
	  PolGrid.displayTitle = 1;
	  PolGrid.canSel = 0;
    PolGrid.locked = 1;
	  PolGrid.loadMulLine(iArray);  
	
	} catch(ex) {
		alert(ex);
	}
}

</script>