<%
//�������ƣ�BankDataQuery.jsp
//�����ܣ����������ݲ�ѯ
//�������ڣ�2004-10-20
//������  ��wentao
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
    alert("BankDataQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initInpBox()
{ 
  try
  {
		document.all('ManageCom').value = <%=tG1.ComCode%>;
//		document.all('PolNo').value = '';
//		document.all('StartDate').value = '';	
//		document.all('EndDate').value = '';
  }
  catch(ex)
  {
    alert("BankDataQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}    

function initCodeGrid()    							//������Ϊinit+MulLine�Ķ�����ObjGrid
{                           						
	var iArray = new Array(); 						//������ø�����
	try
	{
    	iArray[0]=new Array();
    	iArray[0][0]="���";         			//����������У���1�У�
    	iArray[0][1]="50px";         			//�п�
    	iArray[0][2]=10;	         				//�����ֵ
    	iArray[0][3]=0;         					//1��ʾ�����������룬0��ʾֻ���Ҳ���ӦTab��
    																		//2��ʾΪ������������ɫ����

	    iArray[1]=new Array();
	    iArray[1][0]="������";    				//����
	    iArray[1][1]="120px";  						//�п�
	    iArray[1][2]=30;      						//�����ֵ
	    iArray[1][3]=0;   								//1��ʾ�����������룬0��ʾֻ���Ҳ���ӦTab��
	                   										//2��ʾΪ������������ɫ����.

	    iArray[2]=new Array();          	
	    iArray[2][0]="���ֱ���";  				//��������2�У�
	    iArray[2][1]="60px";  						//�п�
	    iArray[2][2]=10;        					//�����ֵ
	    iArray[2][3]=2;       						//�Ƿ���������,1��ʾ������0��ʾ������
			iArray[2][4]="RiskCode"; 
      iArray[2][9]="���ֱ���|code:RiskCode&NOTNULL";
	
	    iArray[3]=new Array();          	
	    iArray[3][0]="Ͷ����";  				//��������2�У�
	    iArray[3][1]="60px";  						//�п�
	    iArray[3][2]=10;        					//�����ֵ
	    iArray[3][3]=0;       						//�Ƿ���������,1��ʾ������0��ʾ������
	       																//������������N�У���������

	    iArray[4]=new Array();          	
	    iArray[4][0]="��������";  				//��������2�У�
	    iArray[4][1]="60px";  						//�п�
	    iArray[4][2]=10;        					//�����ֵ
	    iArray[4][3]=0;       						//�Ƿ���������,1��ʾ������0��ʾ������
	       																//������������N�У���������

	    iArray[5]=new Array();          	
	    iArray[5][0]="Ӧ������";  				//��������2�У�
	    iArray[5][1]="80px";  						//�п�
	    iArray[5][2]=10;        					//�����ֵ
	    iArray[5][3]=0;       						//�Ƿ���������,1��ʾ������0��ʾ������
	       																//������������N�У���������

	    iArray[6]=new Array();          	
	    iArray[6][0]="�ѷ��̴���";  				//��������2�У�
	    iArray[6][1]="60px";  						//�п�
	    iArray[6][2]=10;        					//�����ֵ
	    iArray[6][3]=0;       						//�Ƿ���������,1��ʾ������0��ʾ������
	       																//������������N�У���������

	    iArray[7]=new Array();          	
	    iArray[7][0]="�ɷ�֪ͨ���";
	    iArray[7][1]="0px";
	    iArray[7][2]=20; 
	    iArray[7][3]=0; 


	    //���ɶ����������򣺶�����=new MulLineEnter(����������,����������); 
	    CodeGrid= new MulLineEnter( "fm" , "CodeGrid" ); 
	    //���������� (��Ҫ�������ԣ��ڴ�������������)
	    CodeGrid.mulLineCount = 5 ; 		//�����ԣ���������=10    
	    CodeGrid.displayTitle = 1;   		//�������ԣ�1��ʾ����(ȱʡֵ), 0���ر���        
	    //�����ʼ���������ö����ʼ�����������Ա����ڴ�ǰ����
	    CodeGrid.hiddenSubtraction=1;
	    CodeGrid.hiddenPlus=1;
	    CodeGrid.canSel=1;
	    CodeGrid.loadMulLine(iArray);  
	}
	catch(ex)
	{ alert(ex); }
}

</script>