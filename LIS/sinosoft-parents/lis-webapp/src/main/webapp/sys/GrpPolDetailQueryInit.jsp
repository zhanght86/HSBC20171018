<%
//�������ƣ�PolQueryInit.jsp
//�����ܣ�
//�������ڣ�2002-12-16
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
                           
<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
 
  try
  {                                   
	// ������ѯ����
  //  document.all('CustomerNo').value = tCustomerNo;
  //  document.all('Name').value = tName;
  }
  catch(ex)
  {
    alert("��PolQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
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
    alert("��PolQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
	initPolGrid();
	returnParent();
  }
  catch(re)
  {
    alert("PolQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

var PolGrid; 
// ������Ϣ�б�ĳ�ʼ��
function initPolGrid()
  {    
   
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="40px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
     if(flag=="1"){
      iArray[1][0]="���ֺ���";         		//����
     }else{
      iArray[1][0]="�������ֺ�";         		//����
     }
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      iArray[2]=new Array();
      iArray[2][0]="ӡˢ����";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][3]=3; 
      
      
      iArray[3]=new Array();
      iArray[3][0]="Ͷ���˱���";         		//����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="Ͷ��������";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="���ֱ���";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=0; 

      
      
      iArray[6]=new Array();
      iArray[6][0]="��������";         		//����
      iArray[6][1]="200px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="��������";         		//����
      iArray[7][1]="200px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0; 


        
      iArray[8]=new Array();
      iArray[8][0]="����";         		//����
      iArray[8][1]="120px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  

      iArray[9]=new Array();
      iArray[9][0]="����";         		//����
      iArray[9][1]="120px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  
      
      iArray[10]=new Array();
      iArray[10][0]="����״̬";         		//����
      iArray[10][1]="120px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      
      
      iArray[11]=new Array();
      iArray[11][0]="����״̬����ԭ��";         		//����
      iArray[11][1]="0px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

	iArray[12]=new Array();
	iArray[12][0]="����";         		
	iArray[12][1]="60px";            		 
	iArray[12][2]=60;            			
	iArray[12][3]=2;              		
	iArray[12][4]="Currency";              	  
	iArray[12][9]="����|code:Currency";    

      PolGrid = new MulLineEnter( "document" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 5;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
