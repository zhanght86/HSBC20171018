<%
//�������ƣ�ScanErrorLisInit.jsp
//�����ܣ��ۺϴ�ӡ���б������嵥--ɨ������ͳ�Ʊ���
//�������ڣ�2010-06-02
//������  ��hanabin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.lis.pubfun.PubFun"%><script language="JavaScript">
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    PubFun tpubFun = new PubFun();
	String strCurDay = tpubFun.getCurrentDate();
%>
function initForm()
{
  try
  {
    initInpBox();
    initCodeGrid();
  }
  catch(re)
  {
    alert("ReceiveAccountInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initCodeGrid()    							//������Ϊinit+MulLine�Ķ�����ObjGrid
{                           						
	var iArray = new Array(); 						//������ø�����
	try
	{
    	iArray[0]=new Array();
    	iArray[0][0]="���";         			//����
    	iArray[0][1]="40px";         			//�п�
    	iArray[0][2]=30;           				//�����ֵ
    	iArray[0][3]=0;         					//1��ʾ����������룬0��ʾֻ���Ҳ���ӦTab��
    																		//2��ʾΪ������������ɫ����

	    iArray[1]=new Array();
	    iArray[1][0]="�������";  			//����������У���1�У�
	    iArray[1][1]="60px";  						//�п�
	    iArray[1][2]=10;      						//�����ֵ
	    iArray[1][3]=0;   								//1��ʾ����������룬0��ʾֻ���Ҳ���ӦTab��
	                   										//2��ʾΪ������������ɫ����.
	                                    	
	    iArray[2]=new Array();          	
	    iArray[2][0]="������";				//��������2�У�
	    iArray[2][1]="100px";  						//�п�
	    iArray[2][2]=20;        					//�����ֵ
	    iArray[2][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������
	       																//�����������N�У���������
	    iArray[3]=new Array();          	
	    iArray[3][0]="ɨ���ܼ���";					//��������2�У�
	    iArray[3][1]="60px";  						//�п�
	    iArray[3][2]=10;        					//�����ֵ
	    iArray[3][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������
	       											//�����������N�У���������
	       																
	    iArray[4]=new Array();          	
	    iArray[4][0]="Ͷ����ɨ�����";					//��������2�У�
	    iArray[4][1]="60px";  						//�п�
	    iArray[4][2]=10;        					//�����ֵ
	    iArray[4][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������
	       											//�����������N�У���������	   
	
	
	    iArray[5]=new Array();          	
	    iArray[5][0]="������ɨ�����";			    //��������2�У�
	    iArray[5][1]="60px";  						//�п�
	    iArray[5][2]=10;        					//�����ֵ
	    iArray[5][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������
	       											//�����������N�У���������		
	       											
	    iArray[6]=new Array();          	
	    iArray[6][0]="ɨ������";					//��������2�У�
	    iArray[6][1]="60px";  						//�п�
	    iArray[6][2]=10;        					//�����ֵ
	    iArray[6][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������
	       											//�����������N�У���������		       											       												       											    																
	
	    //���ɶ����������򣺶�����=new MulLineEnter(��������,����������); 
	    CodeGrid= new MulLineEnter( "fm" , "CodeGrid" ); 
	    //���������� (��Ҫ�������ԣ��ڴ�������������)
	    CodeGrid.mulLineCount = 5 ; 		//�����ԣ���������=10    
	    CodeGrid.displayTitle = 1;   		//�������ԣ�1��ʾ����(ȱʡֵ), 0���ر���        
	    //�����ʼ���������ö����ʼ�����������Ա����ڴ�ǰ����
	    CodeGrid.hiddenSubtraction=1;
	    CodeGrid.hiddenPlus=1;
	    //CodeGrid.canChk=1;
	    CodeGrid.loadMulLine(iArray);  
	}
	catch(ex)
	{ alert(ex); }
}

function initInpBox()
{ 
  try
  {
		document.all('ManageCom').value = comCode;
		//document.all('StartDate').value ="<%=strCurDay%>";
		//document.all('EndDate').value ="<%=strCurDay%>";
		//document.all('takebackdate').value ="<%=strCurDay%>";
		//document.all('takebackdatebegin').value ="<%=strCurDay%>";
		//document.all('takebacktime').value = '23:59:59';

  }
  catch(ex)
  {
    alert("ReceiveAccountInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}    
    
</script>