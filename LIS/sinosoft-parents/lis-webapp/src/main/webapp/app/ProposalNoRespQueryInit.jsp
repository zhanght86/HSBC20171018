<%
//�������ƣ�ProposalNoRespQueryInit.jsp
//�����ܣ���ѯ����δ�ظ��嵥
//�������ڣ�2007-03-20 18:02
//������  ��Fuqx
//���¼�¼��  ������    ��������     ����ԭ��/����
%>                         
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	String curDate = PubFun.getCurrentDate();
	//loggerDebug("ProposalNoRespQueryInit",curDate);
%> 

<script language="JavaScript">

function initForm()
{
  try
  {
    initInpBox();
    initCodeGrid();
  }
  catch(re)
  {
    alert("ProposalNoRespQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initCodeGrid()    							//������Ϊinit+MulLine�Ķ�����ObjGrid
{                           						
	var iArray = new Array(); 						//������ø�����
	try
	{
    	iArray[0]=new Array();
    	iArray[0][0]="���";         			//����
    	iArray[0][1]="30px";         			//�п�
    	iArray[0][2]=100;         				//�����ֵ
    	iArray[0][3]=0;         					//1��ʾ����������룬0��ʾֻ���Ҳ���ӦTab��
    																		//2��ʾΪ������������ɫ����

	    iArray[1]=new Array();
	    iArray[1][0]="�������";    			//����������У���1�У�
	    iArray[1][1]="100px";  						//�п�
	    iArray[1][2]=30;      						//�����ֵ
	    iArray[1][3]=0;   								//1��ʾ����������룬0��ʾֻ���Ҳ���ӦTab��
	                   										

	    iArray[2]=new Array();          	
	    iArray[2][0]="ӡˢ��";  				//��������2�У�
	    iArray[2][1]="100px";  						//�п�
	    iArray[2][2]=10;        					//�����ֵ
	    iArray[2][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������

	    iArray[3]=new Array();
	    iArray[3][0]="Ͷ������";    			//����������У���1�У�
	    iArray[3][1]="0px";  						//�п�
	    iArray[3][2]=30;      						//�����ֵ
	    iArray[3][3]=0;   								//1��ʾ����������룬0��ʾֻ���Ҳ���ӦTab��


	    iArray[4]=new Array();
	    iArray[4][0]="Ͷ����";    			//����������У���1�У�
	    iArray[4][1]="60px";  						//�п�
	    iArray[4][2]=30;      						//�����ֵ
	    iArray[4][3]=0;   								//1��ʾ����������룬0��ʾֻ���Ҳ���ӦTab��	    
	    
	    iArray[5]=new Array();
	    iArray[5][0]="������";    			//����������У���1�У�
	    iArray[5][1]="60px";  						//�п�
	    iArray[5][2]=30;      						//�����ֵ
	    iArray[5][3]=0;   								//1��ʾ����������룬0��ʾֻ���Ҳ���ӦTab��

	                                    	
	    iArray[6]=new Array();          	
	    iArray[6][0]="���ձ���";  			//��������2�У�
	    iArray[6][1]="80px";  						//�п�
	    iArray[6][2]=30;        					//�����ֵ
	    iArray[6][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������


	    iArray[7]=new Array();          	
	    iArray[7][0]="�ܱ���";  					//��������2�У�
	    iArray[7][1]="70px";  						//�п�
	    iArray[7][2]=30;        					//�����ֵ
	    iArray[7][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������

	    iArray[8]=new Array();          	
	    iArray[8][0]="����λ��";  					//��������2�У�
	    iArray[8][1]="70px";  						//�п�
	    iArray[8][2]=30;        					//�����ֵ
	    iArray[8][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������	    
	    iArray[8][4]="OperatePos";
	       																

	    iArray[9]=new Array();          	
	    iArray[9][0]="ҵ��Ա����";  				//��������2�У�
	    iArray[9][1]="90px";  						//�п�
	    iArray[9][2]=10;        					//�����ֵ
	    iArray[9][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������
		    	    
		iArray[10]=new Array();          	
	    iArray[10][0]="ҵ��Ա����";  				//��������2�У�
	    iArray[10][1]="90px";  						//�п�
	    iArray[10][2]=10;        					//�����ֵ
	    iArray[10][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������

	    iArray[11]=new Array();          	
	    iArray[11][0]="��������";  				//��������2�У�
	    iArray[11][1]="115px";  						//�п�
	    iArray[11][2]=10;        					//�����ֵ
	    iArray[11][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������

	    iArray[12]=new Array();          	
	    iArray[12][0]="��������";  				//��������2�У�
	    iArray[12][1]="115px";  						//�п�
	    iArray[12][2]=10;        					//�����ֵ
	    iArray[12][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������	    
	    
	    iArray[13]=new Array();          	
	    iArray[13][0]="ɨ��ʱ��";  				//��������2�У�
	    iArray[13][1]="150px";  						//�п�
	    iArray[13][2]=10;        					//�����ֵ
	    iArray[13][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������

	    
	    iArray[14]=new Array();          	
	    iArray[14][0]="������·�ʱ��";  				//��������2�У�
	    iArray[14][1]="150px";  						//�п�
	    iArray[14][2]=10;        					//�����ֵ
	    iArray[14][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������

	    
	    iArray[15]=new Array();          	
	    iArray[15][0]="���������";  				//��������2�У�
	    iArray[15][1]="115px";  						//�п�
	    iArray[15][2]=10;        					//�����ֵ
	    iArray[15][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������

	    iArray[16]=new Array();          	
	    iArray[16][0]="������·��󾭹�ʱ��";  				//��������2�У�
	    iArray[16][1]="115px";  						//�п�
	    iArray[16][2]=10;        					//�����ֵ
	    iArray[16][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������
	    

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
		document.all('ManageCom').value = '';
		//document.all('PrtNo').value = '';
		document.all('AgentCode').value = '';
		document.all('SaleChnl').value = '';
		document.all('InsuredName').value = '';
		document.all('RiskCode').value = '';
		//document.all('StartDate').value = '<%=curDate%>';
		//document.all('EndDate').value = '<%=curDate%>';
		document.all('IssueStartDate').value = '<%=curDate%>';
		document.all('IssueEndDate').value = '<%=curDate%>';
  }
  catch(ex)
  {
    alert("ProposalNoRespQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}    
    
</script>
