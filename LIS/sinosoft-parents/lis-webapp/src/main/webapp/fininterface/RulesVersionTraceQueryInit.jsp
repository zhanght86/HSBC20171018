<%
//Creator :FanXin
//Date :2008-08-21
%>
<!--�û�У����-->

<script language="JavaScript">

// �����ĳ�ʼ��
function initInpBox()
{


  try
  {
  	fm.reset();
    document.all('Maintenanceno').value = '';
    document.all('VersionNo').value = VersionNo;
    document.all('MaintenanceState').value = '';
    document.all('MaintenanceReMark').value = '';
  }
  catch(ex)
  {
    alert("��RulesVersionTraceQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��RulesVersionTraceQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}


function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
    initRulesVersionTraceGrid();
  }
  catch(re)
  {
    alert("RulesVersionTraceQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


function initRulesVersionTraceGrid()
{
	var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="ά�����";    	//����
      iArray[1][1]="150px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
      iArray[2]=new Array();
      iArray[2][0]="�汾���";         			//����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="ά��״̬";         			//����
      iArray[3][1]="40px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      
      
      iArray[4]=new Array();
      iArray[4][0]="ά������";         			//����
      iArray[4][1]="150px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="����Ա";         		//����
      iArray[5][1]="40px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
      
      iArray[6]=new Array();
      iArray[6][0]="�������";         			//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      

			iArray[7]=new Array();
      iArray[7][0]="���ʱ��";         			//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      

      RulesVersionTraceGrid = new MulLineEnter( "document" , "RulesVersionTraceGrid" ); 
      RulesVersionTraceGrid.mulLineCount = 5;   
      RulesVersionTraceGrid.displayTitle = 1;
      RulesVersionTraceGrid.canSel=1;
      RulesVersionTraceGrid.locked = 1;	
			RulesVersionTraceGrid.hiddenPlus = 1;
			RulesVersionTraceGrid.hiddenSubtraction = 1;
      RulesVersionTraceGrid.loadMulLine(iArray);  
      RulesVersionTraceGrid.detailInfo="������ʾ��ϸ��Ϣ";
     
      }
      
      catch(ex)
      {
        alert("��ʼ��RulesVersionTraceGridʱ����"+ ex);
      }

}

</script>
