<%
//�������ƣ�BankSerialNoQueryInit.jsp
//�����ܣ����з��̻������β�ѯ
//�������ڣ�2010-04-12
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
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
    alert("InputErrQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
	iArray[1][1]="60px";  						//�п�
	iArray[1][2]=30;      						//�����ֵ
	iArray[1][3]=0;   								//1��ʾ����������룬0��ʾֻ���Ҳ���ӦTab��
	               										

	iArray[2]=new Array();          	
	iArray[2][0]="���д���";  				//��������2�У�
	iArray[2][1]="60px";  						//�п�
	iArray[2][2]=10;        					//�����ֵ
	iArray[2][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������

	iArray[3]=new Array();
	iArray[3][0]="��������";    			//����������У���1�У�
	iArray[3][1]="160px";  						//�п�
	iArray[3][2]=30;      						//�����ֵ
	iArray[3][3]=0;   								//1��ʾ����������룬0��ʾֻ���Ҳ���ӦTab��


	iArray[4]=new Array();
	iArray[4][0]="���κ�";    			//����������У���1�У�
	iArray[4][1]="140px";  						//�п�
	iArray[4][2]=30;      						//�����ֵ
	iArray[4][3]=0;   								//1��ʾ����������룬0��ʾֻ���Ҳ���ӦTab��
	
	iArray[5]=new Array();
	iArray[5][0]="����״̬";    			//����������У���1�У�
	iArray[5][1]="60px";  						//�п�
	iArray[5][2]=30;      						//�����ֵ
	iArray[5][3]=0;   								//1��ʾ����������룬0��ʾֻ���Ҳ���ӦTab��	    	    
	
	iArray[6]=new Array();          	
	iArray[6][0]="��������";  				//��������2�У�
	iArray[6][1]="80px";  						//�п�
	iArray[6][2]=10;        					//�����ֵ
	iArray[6][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������

	iArray[7]=new Array();
	iArray[7][0]="��������";    			//����������У���1�У�
	iArray[7][1]="80px";  						//�п�
	iArray[7][2]=30;      						//�����ֵ
	iArray[7][3]=0;   								//1��ʾ����������룬0��ʾֻ���Ҳ���ӦTab��


	iArray[8]=new Array();
	iArray[8][0]="�ܽ��";    			//����������У���1�У�
	iArray[8][1]="80px";  						//�п�
	iArray[8][2]=30;      						//�����ֵ
	iArray[8][3]=0;   								//1��ʾ����������룬0��ʾֻ���Ҳ���ӦTab��
	
	iArray[9]=new Array();
	iArray[9][0]="�ܱ���";    			//����������У���1�У�
	iArray[9][1]="60px";  						//�п�
	iArray[9][2]=30;      						//�����ֵ
	iArray[9][3]=0;   								//1��ʾ����������룬0��ʾֻ���Ҳ���ӦTab��	    	    
	
	iArray[10]=new Array();
	iArray[10][0]="�ɹ����";    			//����������У���1�У�
	iArray[10][1]="80px";  						//�п�
	iArray[10][2]=30;      						//�����ֵ
	iArray[10][3]=0;   								//1��ʾ����������룬0��ʾֻ���Ҳ���ӦTab��
	
	iArray[11]=new Array();
	iArray[11][0]="�ɹ�����";    			//����������У���1�У�
	iArray[11][1]="60px";  						//�п�
	iArray[11][2]=30;      						//�����ֵ
	iArray[11][3]=0;   								//1��ʾ����������룬0��ʾֻ���Ҳ���ӦTab��	    	    
	
	iArray[12]=new Array();
	iArray[12][0]="���ո���־";    			//����������У���1�У�
	iArray[12][1]="60px";  						//�п�
	iArray[12][2]=30;      						//�����ֵ
	iArray[12][3]=0;   								//1��ʾ����������룬0��ʾֻ���Ҳ���ӦTab��	    	    
	
	
	    //���ɶ����������򣺶�����=new MulLineEnter(��������,����������); 
	    CodeGrid= new MulLineEnter( "fm" , "CodeGrid" ); 
	    //���������� (��Ҫ�������ԣ��ڴ�������������)
	    CodeGrid.mulLineCount = 10 ; 		//�����ԣ���������=10    
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
    
		document.all('Managecom').value = '';
                document.all('Bankcode').value = '';
		document.all('Sdate').value = '';
		document.all('Edate').value = '';
		document.all('YStatus').value = '';
		

  }
  catch(ex)
  {
    alert("InputErrQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}    
    
</script>
