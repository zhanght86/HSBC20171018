<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
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
    alert("mulLineInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
  function initCodeGrid()    //������Ϊinit+MulLine�Ķ�����ObjGrid
   {
	var iArray = new Array(); //������ø�����
	try
	{
	      iArray[0]=new Array();
	      iArray[0][0]="���";      //����������У���1�У�
	      iArray[0][1]="30px";  	//�п�
	      iArray[0][2]=10;      	//�����ֵ
	      iArray[0][3]=0;   	//1��ʾ����������룬0��ʾֻ���Ҳ���ӦTab��
	                     		// 2 ��ʾΪ������������ɫ����.
	                           
	      iArray[1]=new Array();
	      iArray[1][0]="��˾����";  //��������2�У�
	      iArray[1][1]="30px";  	//�п�
	      iArray[1][2]=10;        	//�����ֵ
	      iArray[1][3]=1;       	//�Ƿ���������,1��ʾ����0��ʾ������
	         			//�����������N�У���������
	      iArray[2]=new Array();
	      iArray[2][0]="����������";  //��������2�У�
	      iArray[2][1]="30px";  	//�п�
	      iArray[2][2]=10;        	//�����ֵ
	      iArray[2][3]=1;       	//�Ƿ���������,1��ʾ����0��ʾ������
	         			//�����������N�У���������
	
	      //���ɶ����������򣺶�����=new MulLineEnter(��������,����������); 
	      CodeGrid= new MulLineEnter( "fm" , "CodeGrid" ); 
	      //���������� (��Ҫ�������ԣ��ڴ�������������)
	      CodeGrid.mulLineCount = 5 ; 	//�����ԣ���������=5    
	      CodeGrid.displayTitle = 1;   	//�������ԣ�1��ʾ���� (ȱʡֵ) ,0���ر���        
	      //�����ʼ���������ö����ʼ�����������Ա����ڴ�ǰ����
	      CodeGrid.hiddenSubtraction=1;
	      CodeGrid.hiddenPlus=1;
	      CodeGrid.canChk=1;
	      CodeGrid.loadMulLine(iArray);  
	}
	catch(ex)
	{ alert(ex); }
    }
function initInpBox()
{ 
  try
  {
	document.all('ManageCom').value = ''	;
	document.all('Host_Name').value = '';
  }
  catch(ex)
  {
    alert("��mulLineInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}    
    
</script>
