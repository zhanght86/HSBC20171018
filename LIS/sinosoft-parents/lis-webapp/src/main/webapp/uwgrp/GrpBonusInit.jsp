
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {   
    	
  }
  catch(ex)
  {
    alert("��InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {

  }
  catch(ex)
  {
    alert("��LMYieldInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
		initBonusGrid();
		initCanclePolGrid();
		
  }
  catch(re)
  {
    alert("InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initBonusGrid()
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
      iArray[1][0]="�ŵ���ͬ��";         		//����
      iArray[1][1]="80px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�ͻ�����";         		//����
      iArray[2][1]="50px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[3]=new Array();
      iArray[3][0]="�ͻ���ʶ";         		//����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�ŵ����ֺ�";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

	    iArray[5]=new Array();
      iArray[5][0]="����";         		//����
      iArray[5][1]="30px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
      
	    iArray[6]=new Array();
      iArray[6][0]="�����ڼ�";         		//����
      iArray[6][1]="40px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������      
 
 	    iArray[7]=new Array();
      iArray[7][0]="�ڼ䵥λ";         		//����
      iArray[7][1]="40px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0; 			
      
      iArray[8]=new Array();
      iArray[8][0]="�ر���ʽ";         		//����
      iArray[8][1]="60px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0; 			
      
      iArray[9]=new Array();
      iArray[9][0]="�ر�����/����";         		//����
      iArray[9][1]="90px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0; 			
      
      iArray[10]=new Array();
      iArray[10][0]="��ͬ��ʼ��";         		//����
      iArray[10][1]="90px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0; 			
       
      iArray[11]=new Array();
      iArray[11][0]="��ͬ��ֹ��";         		//����
      iArray[11][1]="90px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=0; 	
      
      iArray[12]=new Array();
      iArray[12][0]="��ͬ�������";         		//����
      iArray[12][1]="60px";            		//�п�
      iArray[12][2]=100;            			//�����ֵ
      iArray[12][3]=0; 
      
      iArray[13]=new Array();
      iArray[13][0]="˵��";         		//����
      iArray[13][1]="60px";            		//�п�
      iArray[13][2]=1000;            			//�����ֵ
      iArray[13][3]=0; 
      BonusGrid = new MulLineEnter( "fm" , "BonusGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      BonusGrid.mulLineCount = 0;   
      BonusGrid.displayTitle = 1;
      BonusGrid.locked = 1;
      BonusGrid.canSel = 1;
      BonusGrid.hiddenPlus=1;
      BonusGrid.loadMulLine(iArray);  
      BonusGrid.selBoxEventFuncName="showMul";

      
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
//����ֺ��˱�
function initCanclePolGrid()
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
      iArray[1][0]="�ŵ���ͬ��";         		//����
      iArray[1][1]="80px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�ͻ�����";         		//����
      iArray[2][1]="50px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[3]=new Array();
      iArray[3][0]="�ŵ����ֺ�";         		//����
      iArray[3][1]="40px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="���ֱ���";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

	    iArray[5]=new Array();
      iArray[5][0]="�ʻ���������";         		//����
      iArray[5][1]="30px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
      
	    iArray[6]=new Array();
      iArray[6][0]="ռ�˻�������";         		//����
      iArray[6][1]="40px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������      

      CanclePolGrid = new MulLineEnter( "fm" , "CanclePolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      CanclePolGrid.mulLineCount = 0;   
      CanclePolGrid.displayTitle = 1;
      CanclePolGrid.locked = 1;
      CanclePolGrid.canSel = 1;
      CanclePolGrid.hiddenPlus=1;
      CanclePolGrid.loadMulLine(iArray);  
      CanclePolGrid.selBoxEventFuncName="showMul2";
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
