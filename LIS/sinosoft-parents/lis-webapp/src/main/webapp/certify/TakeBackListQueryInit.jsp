<%//�������ƣ�TakeBackListQueryInit.jsp
//�����ܣ���ѯ������ִ�嵥�ʹ�ӡ
//�������ڣ�2005-3-31
//������  ��weikai
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
    alert("TakeBackListQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
	    iArray[1][0]="������";  			//����������У���1�У�
	    iArray[1][1]="80px";  						//�п�
	    iArray[1][2]=10;      						//�����ֵ
	    iArray[1][3]=0;   								//1��ʾ����������룬0��ʾֻ���Ҳ���ӦTab��
	                   										//2��ʾΪ������������ɫ����.
	                                    	
	    iArray[2]=new Array();          	
	    iArray[2][0]="�������";				//��������2�У�
	    iArray[2][1]="60px";  						//�п�
	    iArray[2][2]=20;        					//�����ֵ
	    iArray[2][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������
	       																//�����������N�У���������
	    iArray[3]=new Array();          	
	    iArray[3][0]="�������";					//��������2�У�
	    iArray[3][1]="60px";  						//�п�
	    iArray[3][2]=10;        					//�����ֵ
	    iArray[3][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������
	       																//�����������N�У���������
	
	    iArray[4]=new Array();          	
	    iArray[4][0]="ҵ��Ա����";		//��������2�У�
	    iArray[4][1]="60px";  						//�п�
	    iArray[4][2]=10;        					//�����ֵ
	    iArray[4][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������
	       																//�����������N�У���������
		iArray[5]=new Array();          	
	    iArray[5][0]="ҵ��Ա����";		//��������2�У�
	    iArray[5][1]="50px";  						//�п�
	    iArray[5][2]=10;        					//�����ֵ
	    iArray[5][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������
	       																//�����������N�У���������		
		
	    iArray[6]=new Array();          	
	    iArray[6][0]="Ͷ��������";				  //��������2�У�
	    iArray[6][1]="50px";  						//�п�
	    iArray[6][2]=10;        					//�����ֵ
	    iArray[6][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������
	       																//�����������N�У���������
	       																
	    iArray[7]=new Array();          	
	    iArray[7][0]="��������";				  //��������2�У�
	    iArray[7][1]="60px";  						//�п�
	    iArray[7][2]=10;        					//�����ֵ
	    iArray[7][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������
	       																//�����������N�У���������
	
	    iArray[8]=new Array();          	
	    iArray[8][0]="���һ��������ظ�ʱ��";				  //��������2�У�
	    iArray[8][1]="60px";  						//�п�
	    iArray[8][2]=10;        					//�����ֵ
	    iArray[8][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������


	    iArray[9]=new Array();          	
		iArray[9][0]="����ǩ��ʱ��";				  //��������2�У�
		iArray[9][1]="60px";  						//�п�
	    iArray[9][2]=10;        					//�����ֵ
	    iArray[9][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������


	    iArray[10]=new Array();          	
	    iArray[10][0]="ǩ������";				  //��������2�У�
	    iArray[10][1]="60px";  						//�п�
	    iArray[10][2]=10;        					//�����ֵ
	    iArray[10][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������

	    iArray[11]=new Array();          	
	    iArray[11][0]="����ʱ��";				  //��������2�У�
		iArray[11][1]="60px";  						//�п�
		iArray[11][2]=10;        					//�����ֵ
		iArray[11][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������		

		iArray[12]=new Array();          	
		iArray[12][0]="����Ա��";				  //��������2�У�
		iArray[12][1]="40px";  						//�п�
		iArray[12][2]=10;        					//�����ֵ
		iArray[12][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������	

		iArray[13]=new Array();          	
		iArray[13][0]="�ӳ�ԭ��";				  //��������2�У�
		iArray[13][1]="60px";  						//�п�
		iArray[13][2]=10;        					//�����ֵ
		iArray[13][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������			       																
	
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
//		document.all('SaleChnl').value = '';
//		document.all('TakeBackDateB').value = '';
//		document.all('TakeBackDateE').value = '';
//		document.all('Operator').value = '';
  }
  catch(ex)
  {
    alert("TakeBackListQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}    
    
</script>
