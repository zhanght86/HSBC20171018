
<%
	//**************************************************************************************************
	//�������ƣ�LDUserInfoInputInit.jsp
	//�����ܣ���֤��Ա��Ϣ��ѯ
	//�������ڣ� 2009-02-18
	//������  �� mw
	//���¼�¼��  ������    ��������     ����ԭ��/����
	//**************************************************************************************************
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">
<%
String strCurDate = PubFun.getCurrentDate();
%>

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{
  try
  {
  	fm.reset();
	fm.ManageCom.value = ''
  	fm.grade.value = '';
  	fm.validstartdate.value = '';
  }
  catch(ex)
  {
    alert("��CertifyInfoInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}


function initForm()
{
  try
  {
    initInpBox();
    initUserInfoGrid();    
  }
  catch(re)
  {
    alert("CertifyInfoInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ��֤��Ϣ�б�ĳ�ʼ��
function initUserInfoGrid()
{                               
    var iArray = new Array();   
    try
    {
		iArray[0]=new Array();
		iArray[0][0]="���";         			  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";            		//�п�
		iArray[0][2]=10;            			  //�����ֵ
		iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������   			     
		
		iArray[1]=new Array();
		iArray[1][0]="��������";         		 
		iArray[1][1]="40px";            		 
		iArray[1][2]=100;            			   
		iArray[1][3]=0;              			   
		
		iArray[2]=new Array();
		iArray[2][0]="��������";          		 
		iArray[2][1]="120px";            		 
		iArray[2][2]=85;            			   
		iArray[2][3]=0;  
		
		iArray[3]=new Array();
		iArray[3][0]="����";          		 
		iArray[3][1]="60px";            		 
		iArray[3][2]=85;            			   
		iArray[3][3]=0;              			   
		
		iArray[4]=new Array();
		iArray[4][0]="����";        		   
		iArray[4][1]="60px";            		 
		iArray[4][2]=85;            			   
		iArray[4][3]=0;              			   
		
		iArray[5]=new Array();
		iArray[5][0]="��Ȩʱ��";          		 
		iArray[5][1]="60px";            		 
		iArray[5][2]=100;            		 	   
		iArray[5][3]=0;    
		
		iArray[6]=new Array();
		iArray[6][0]="���ʱ��";          		 
		iArray[6][1]="60px";            		 
		iArray[6][2]=100;            		 	   
		iArray[6][3]=0;    
		
	    UserInfoGrid = new MulLineEnter( "document" , "UserInfoGrid" ); 
	    UserInfoGrid.mulLineCount = 5;   
	    UserInfoGrid.displayTitle = 1;
	    UserInfoGrid.locked = 1;
	    UserInfoGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
	    //UserInfoGrid.selBoxEventFuncName ="CardInfoGridClick"; //��Ӧ�ĺ���������������   
	    UserInfoGrid.hiddenPlus=1;
	    UserInfoGrid.hiddenSubtraction=1;
	    UserInfoGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
