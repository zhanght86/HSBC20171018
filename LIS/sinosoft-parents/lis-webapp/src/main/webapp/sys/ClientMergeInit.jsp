<%
//�������ƣ�ClientMergeInit.jsp
//�����ܣ�
//�������ڣ�2002-08-19
//������  ��Kevin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {                                   
    document.all('Name').value = '1';
    document.all('AppntSex').value = '';
    document.all('Birthday').value = '1971-1-1';
    document.all('Marriage').value = '';
    document.all('OccupationType').value = '';
    document.all('Health').value = '';

    document.all('AppntIDType').value = '';
    document.all('Proterty').value = '';
    document.all('IDNo').value = '';
    document.all('ICNo').value = '';
    document.all('OthIDType').value = '';
    document.all('OthIDNo').value = '';

   document.all('GrpName').value = '';
    document.all('GrpAddress').value = '';
    document.all('HomeAddressCode').value = '';
    document.all('HomeAddress').value = '';
    document.all('Phone').value = '';
    document.all('BP').value = '';
    document.all('Mobile').value = '';
    document.all('EMail').value = '';

  }
  catch(ex)
  {
    //alert("��ClientMergeInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    //initInpBox();
    initClientList( );
    initOPolGrid();
  }
  catch(re)
  {
    //alert("ClientMergeInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initClientList()
{                               
    var iArray = new Array();
      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         		    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";        			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�ͻ���";    	        //����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="����";    	        //����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      

      iArray[3]=new Array();
      iArray[3][0]="�Ա�"; 	            //����
      iArray[3][1]="40px";            		//�п�
      iArray[3][2]=10;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="��������"; 	            //����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=1;  

      iArray[5]=new Array();
      iArray[5][0]="֤������"; 	            //����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="֤������"; 	            //����
      iArray[6][1]="150px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="����֤������"; 	            //����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=1;  
      
      iArray[8]=new Array();
      iArray[8][0]="����֤������"; 	            //����
      iArray[8][1]="150px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=1;  

      ClientList = new MulLineEnter( "fm" , "ClientList" ); 
      //��Щ���Ա�����loadMulLineǰ
      ClientList.mulLineCount = 0;   
      ClientList.displayTitle = 1;
      ClientList.canSel = 1;
      ClientList.loadMulLine(iArray);  
      
      ClientList.selBoxEventFuncName = "customerUnion";
       } 
      catch(ex) {
      alert(ex);
    }
}

function initOPolGrid() {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�ͻ���";         		//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="����";         		//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="�Ա�";         		//����
      iArray[3][1]="40px";            		//�п�
      iArray[3][2]=10;            			//�����ֵ
      iArray[3][3]=0; 
      
      
      iArray[4]=new Array();
      iArray[4][0]="��������";         		//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="֤������";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="֤������";         		//����
      iArray[6][1]="150px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="����֤������";         		//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="����֤������";         		//����
      iArray[8][1]="150px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      OPolGrid = new MulLineEnter( "fm" , "OPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      OPolGrid.mulLineCount = 1;   
      OPolGrid.displayTitle = 1;
      OPolGrid.locked = 1;
      OPolGrid.canSel = 1;
      OPolGrid.hiddenPlus = 1;
      OPolGrid.hiddenSubtraction = 1;
      OPolGrid.loadMulLine(iArray); 
      
      OPolGrid. selBoxEventFuncName = "customerInfoequels"; 
      
      //��Щ����������loadMulLine����
      //OPolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}


</script>
