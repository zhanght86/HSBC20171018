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
    document.all('PrtNo').value =PtrNo;
    initClientList();
    initOPolGrid();
    displayCustomer();
    fm.CustomerNo_OLD.value = Hole_CustomerNo;
    //initCustomerContGrid();
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
        iArray[7][1]="0px";            		//�п�
        iArray[7][2]=100;            			//�����ֵ
        iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

        iArray[8]=new Array();
        iArray[8][0]="����֤������";         		//����
        iArray[8][1]="150px";            		//�п�
        iArray[8][2]=100;            			//�����ֵ
        iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[9]=new Array();
        iArray[9][0]="ͨ�ŵ�ַ";         		//����
        iArray[9][1]="150px";            		//�п�
        iArray[9][2]=100;            			//�����ֵ
        iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[10]=new Array();
        iArray[10][0]="��ѡ��ϵ�绰";         		//����
        iArray[10][1]="100px";            		//�п�
        iArray[10][2]=100;            			//�����ֵ
        iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[11]=new Array();
        iArray[11][0]="������ϵ�绰";         		//����
        iArray[11][1]="100px";            		//�п�
        iArray[11][2]=100;            			//�����ֵ
        iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[12]=new Array();
        iArray[12][0]="ְҵ";         		//����
        iArray[12][1]="0px";            		//�п�
        iArray[12][2]=100;            			//�����ֵ
        iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[13]=new Array();
        iArray[13][0]="�������";         		//����
        iArray[13][1]="100px";            		//�п�
        iArray[13][2]=100;            			//�����ֵ
        iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[14]=new Array();
        iArray[14][0]="ҵ��Ա����";         		//����
        iArray[14][1]="0px";            		//�п�
        iArray[14][2]=100;            			//�����ֵ
        iArray[14][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      ClientList = new MulLineEnter( "fm" , "ClientList" ); 
      //��Щ���Ա�����loadMulLineǰ
      ClientList.mulLineCount = 0;   
      ClientList.displayTitle = 1;
      ClientList.canSel = 1;
      ClientList.locked = 1;
      ClientList.hiddenPlus = 1;
      ClientList.hiddenSubtraction = 1;
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
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="����֤������";         		//����
      iArray[8][1]="150px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="ͨ�ŵ�ַ";         		//����
      iArray[9][1]="150px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="��ѡ��ϵ�绰";         		//����
      iArray[10][1]="100px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="������ϵ�绰";         		//����
      iArray[11][1]="100px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[12]=new Array();
      iArray[12][0]="ְҵ";         		//����
      iArray[12][1]="0px";            		//�п�
      iArray[12][2]=100;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[13]=new Array();
      iArray[13][0]="�������";         		//����
      iArray[13][1]="100px";            		//�п�
      iArray[13][2]=100;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[14]=new Array();
      iArray[14][0]="ҵ��Ա����";         		//����
      iArray[14][1]="0px";            		//�п�
      iArray[14][2]=100;            			//�����ֵ
      iArray[14][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      OPolGrid = new MulLineEnter( "fm" , "OPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      OPolGrid.mulLineCount = 1;   
      OPolGrid.displayTitle = 1;
      OPolGrid.locked = 1;
      OPolGrid.canSel = 1;
      OPolGrid.hiddenPlus = 1;
      OPolGrid.hiddenSubtraction = 1;
      OPolGrid.loadMulLine(iArray); 
      
      //OPolGrid. selBoxEventFuncName = "customerInfoequels"; 
      
      //��Щ����������loadMulLine����
      //OPolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

//tongmeng 2009-03-03 add
//���ƿͻ��ı�����Ϣ
function initCustomerContGrid() {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���屣����";         		//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="����/Ͷ������";         		//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="ӡˢ��";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=10;            			//�����ֵ
      iArray[3][3]=0;       
      
      iArray[4]=new Array();
      iArray[4][0]="������";         		//����
      iArray[4][1]="40px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;
      
      iArray[5]=new Array();
      iArray[5][0]="�Ա�";         		//����
      iArray[5][1]="10px";            		//�п�
      iArray[5][2]=10;            			//�����ֵ
      iArray[5][3]=3;  
      
      iArray[6]=new Array();
      iArray[6][0]="��������";         		//����
      iArray[6][1]="10px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=3;

      iArray[7]=new Array();
      iArray[7][0]="֤������";         		//����
      iArray[7][1]="10px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="֤������";         		//����
      iArray[8][1]="10px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="��ַ";         		//����
      iArray[9][1]="150px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="��ϵ�绰1";         		//����
      iArray[10][1]="100px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="��ϵ�绰2";         		//����
      iArray[11][1]="100px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[12]=new Array();
      iArray[12][0]="ְҵ";         		//����
      iArray[12][1]="150px";            		//�п�
      iArray[12][2]=100;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[13]=new Array();
      iArray[13][0]="Ͷ����";         		//����
      iArray[13][1]="40px";            		//�п�
      iArray[13][2]=100;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[14]=new Array();
      iArray[14][0]="�Ա�";         		//����
      iArray[14][1]="0px";            		//�п�
      iArray[14][2]=10;            			//�����ֵ
      iArray[14][3]=3;       
      
      iArray[15]=new Array();
      iArray[15][0]="��������";         		//����
      iArray[15][1]="0px";            		//�п�
      iArray[15][2]=100;            			//�����ֵ
      iArray[15][3]=3;

      iArray[16]=new Array();
      iArray[16][0]="֤������";         		//����
      iArray[16][1]="0px";            		//�п�
      iArray[16][2]=100;            			//�����ֵ
      iArray[16][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[17]=new Array();
      iArray[17][0]="֤������";         		//����
      iArray[17][1]="0px";            		//�п�
      iArray[17][2]=200;            			//�����ֵ
      iArray[17][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[18]=new Array();
      iArray[18][0]="��ַ";         		//����
      iArray[18][1]="150px";            		//�п�
      iArray[18][2]=100;            			//�����ֵ
      iArray[18][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[19]=new Array();
      iArray[19][0]="��ѡ��ϵ�绰";         		//����
      iArray[19][1]="100px";            		//�п�
      iArray[19][2]=100;            			//�����ֵ
      iArray[19][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[20]=new Array();
      iArray[20][0]="������ϵ�绰";         		//����
      iArray[20][1]="100px";            		//�п�
      iArray[20][2]=100;            			//�����ֵ
      iArray[20][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[21]=new Array();
      iArray[21][0]="ְҵ";         		//����
      iArray[21][1]="150px";            		//�п�
      iArray[21][2]=100;            			//�����ֵ
      iArray[21][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[22]=new Array();
      iArray[22][0]="�������";         		//����
      iArray[22][1]="100px";            		//�п�
      iArray[22][2]=100;            			//�����ֵ
      iArray[22][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[23]=new Array();
      iArray[23][0]="ҵ��Ա����";         		//����
      iArray[23][1]="0px";            		//�п�
      iArray[23][2]=100;            			//�����ֵ
      iArray[23][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[24]=new Array();
      iArray[24][0]="��Ч����";         		//����
      iArray[24][1]="40px";            		//�п�
      iArray[24][2]=200;            			//�����ֵ
      iArray[24][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[25]=new Array();
      iArray[25][0]="��ͬ����";         		//����
      iArray[25][1]="0px";            		//�п�
      iArray[25][2]=100;            			//�����ֵ
      iArray[25][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
/*
      iArray[26]=new Array();
      iArray[26][0]="����֤������";         		//����
      iArray[26][1]="150px";            		//�п�
      iArray[26][2]=100;            			//�����ֵ
      iArray[26][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

  */            
      CustomerContGrid = new MulLineEnter( "fm" , "CustomerContGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      CustomerContGrid.mulLineCount = 1;   
      CustomerContGrid.displayTitle = 1;
      CustomerContGrid.locked = 1;
      CustomerContGrid.canSel = 1;
      CustomerContGrid.hiddenPlus = 1;
      CustomerContGrid.hiddenSubtraction = 1;
      CustomerContGrid.loadMulLine(iArray); 
      
      //OPolGrid. selBoxEventFuncName = "customerInfoequels"; 
      
      //��Щ����������loadMulLine����
      //OPolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
