<%
//�������ƣ�UWNoticeInit.jsp
//�����ܣ�
//�������ڣ�2002-08-16 15:39:06
//������  ��CrtHtml���򴴽�
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
	String strManageCom = globalInput.ComCode;
%>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
    document.all('PolNo').value = '';
	document.all('ManageCom').value = <%=strManageCom%>;
  }
  catch(ex)
  {
    alert("��UWNoticeInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=��&1=Ů&2=����");      
//    setOption("sex","0=��&1=Ů&2=����");        
//    setOption("reduce_flag","0=����״̬&1=�����");
//    setOption("pad_flag","0=����&1=�潻");   
  }
  catch(ex)
  {
    alert("��UWNoticeInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox(); 
    initPolGrid();   
  }
  catch(re)
  {
    alert("PEdorUWNoticeInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
	  iArray[1][0]="��ˮ��";         		//����
	  iArray[1][1]="140px";            	//�п�
	  iArray[1][2]=100;            			//�����ֵ
	  iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[2]=new Array();
	  iArray[2][0]="��������";       		//����
	  iArray[2][1]="140px";            	//�п�
	  iArray[2][2]=100;            			//�����ֵ
	  iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[3]=new Array();
	  iArray[3][0]="�����˱���";         	//����
	  iArray[3][1]="100px";            	//�п�
	  iArray[3][2]=100;            			//�����ֵ
	  iArray[3][3]=2; 
      iArray[3][4]="AgentCode";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[3][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[3][9]="�����˱���|code:AgentCode&NOTNULL";
      iArray[3][18]=250;
      iArray[3][19]= 0 ;
	
	  iArray[4]=new Array();
	  iArray[4][0]="���������";        //����
	  iArray[4][1]="100px";            	//�п�
	  iArray[4][2]=200;            			//�����ֵ
	  iArray[4][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      iArray[5]=new Array();
	  iArray[5][0]="չҵ����";        //����
	  iArray[5][1]="160px";            	//�п�
	  iArray[5][2]=200;            			//�����ֵ
	  iArray[5][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
	  
      iArray[6]=new Array();
	  iArray[6][0]="�������";         	//����
	  iArray[6][1]="100px";            	//�п�
	  iArray[6][2]=100;            			//�����ֵ
	  iArray[6][3]=2; 
      iArray[6][4]="station";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[6][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[6][9]="�������|code:station&NOTNULL";
      iArray[6][18]=250;
      iArray[6][19]= 0 ;
      
      iArray[7]=new Array();
	  iArray[7][0]="��ȫ�����";         	//����
	  iArray[7][1]="100px";            	//�п�
	  iArray[7][2]=100;            			//�����ֵ
	  iArray[7][3]=0; 
	  
      iArray[8]=new Array();
	  iArray[8][0]="�������������";         	//����
	  iArray[8][1]="0px";            	//�п�
	  iArray[8][2]=100;            			//�����ֵ
	  iArray[8][3]=0; 
	  
	  iArray[9]=new Array();
	  iArray[9][0]="���������������";         	//����
	  iArray[9][1]="0px";            	//�п�
	  iArray[9][2]=100;            			//�����ֵ
	  iArray[9][3]=0; 
      
      
	
			
      PolGrid = new MulLineEnter( "fmSave" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 10;   
      PolGrid.displayTitle = 1;
      //PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus=1;
      PolGrid.hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>