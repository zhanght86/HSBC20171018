 <% 
//�������ƣ�FIOperationLogQueryinit.jsp
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">                           

function initInpBox()
{
  try
  {
  	fm.reset();
	document.all('StartDay').value = '';
	document.all('EndDay').value = '';
	document.all('EventNo').value = '';
	document.all('EventType').value = '';
	document.all('EventNo1').value = '';
	document.all('filepath').value = '';
  }
  catch(ex)
  {
    alert("FIOperationLogQueryinit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("FIOperationLogQueryinit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initFIOperationLogGrid();
    initFIOperationParameterGrid();
  }
  catch(re)
  {
    alert("FIOperationLogQueryinit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

//
function initFIOperationLogGrid()
{                                  
	var iArray = new Array();      
	try
	{
    	iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�¼�����";         		//����
      iArray[1][1]="80px";            		//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�¼�����";         		//����
      iArray[2][1]="40px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��־�ļ�����";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[4]=new Array();
      iArray[4][0]="��־�ļ�·��";         		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=0;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[5]=new Array();
      iArray[5][0]="ִ��״̬";         		//����
      iArray[5][1]="30px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[6]=new Array();
      iArray[6][0]="��������";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[7]=new Array();
      iArray[7][0]="����ʱ��";         		//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      FIOperationLogGrid = new MulLineEnter( "document" , "FIOperationLogGrid" ); 
      FIOperationLogGrid.mulLineCount = 5;   
      FIOperationLogGrid.displayTitle = 1;
      FIOperationLogGrid.canSel=1;
      FIOperationLogGrid.locked = 1;	
      FIOperationLogGrid.selBoxEventFuncName = "ReturnData";
      FIOperationLogGrid.hiddenPlus = 1;
      FIOperationLogGrid.hiddenSubtraction = 1;
      FIOperationLogGrid.loadMulLine(iArray);  
      FIOperationLogGrid.detailInfo="������ʾ��ϸ��Ϣ";
      

      //��Щ����������loadMulLine����
	}
	catch(ex)
	{
		alert(ex);
	}
}
	
	function initFIOperationParameterGrid()
{                                  
	var iArray = new Array();      
	try
	{
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�¼�����";         		//����
      iArray[1][1]="50px";            		//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�¼�����";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="40px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[4]=new Array();
      iArray[4][0]="��������";         		//����
      iArray[4][1]="40px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[5]=new Array();
      iArray[5][0]="����ֵ";         		//����
      iArray[5][1]="50px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
    
      FIOperationParameterGrid = new MulLineEnter( "document" , "FIOperationParameterGrid" ); 
      FIOperationParameterGrid.mulLineCount = 5;   
      FIOperationParameterGrid.displayTitle = 1;
      FIOperationParameterGrid.canSel = 1;
      FIOperationParameterGrid.canChk = 0;
      FIOperationParameterGrid.locked = 1;	
      FIOperationParameterGrid.hiddenPlus = 1;
      FIOperationParameterGrid.hiddenSubtraction = 1;
      FIOperationParameterGrid.loadMulLine(iArray);  
      FIOperationParameterGrid.detailInfo="������ʾ��ϸ��Ϣ";
      

      //��Щ����������loadMulLine����
	}
	catch(ex)
	{
		alert(ex);
	}
}

</script>
