<%
//�������ƣ�AccQueryInit.jsp
//�����ܣ��˻���Ϣ��ѯ
//�������ڣ�2008-03-17 15:10
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>                  
<script language="JavaScript">


function initForm() {
  try {
  	 initCodeGrid(); 
  	
  } catch(re) {
    alert("InitForm�����з����쳣:��ʼ���������!"+re.message);
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
	    iArray[1][0]="�ͻ��˺�";    			//����������У���1�У�
	    iArray[1][1]="150px";  						//�п�
	    iArray[1][2]=30;      						//�����ֵ
	    iArray[1][3]=0;   								//1��ʾ����������룬0��ʾֻ���Ҳ���ӦTab��
	                   										

	    iArray[2]=new Array();          	
	    iArray[2][0]="�ͻ���";  				//��������2�У�
	    iArray[2][1]="100px";  						//�п�
	    iArray[2][2]=10;        					//�����ֵ
	    iArray[2][3]=0;       						//�Ƿ���������,1��ʾ����0��ʾ������

	    
	    
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

    
</script>
