<!--�û�У����-->

<script language="JavaScript">


// �����ĳ�ʼ��
function initInpBox()
{
  try
  {
    
  }
  catch(ex)
  {
    alert("��VersionRuleQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��VersionRuleQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}


function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
    initRulesVersionGrid();
  }
  catch(re)
  {
    alert("VersionRuleQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


function initRulesVersionGrid()
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
      iArray[1][0]="�汾���";    	//����
      iArray[1][1]="200px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
      iArray[2]=new Array();
      iArray[2][0]="��������";         			//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��Ч����";         			//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      
      
      iArray[4]=new Array();
      iArray[4][0]="ʧЧ����";         			//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�汾״̬";         		//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
      
      iArray[6]=new Array();
      iArray[6][0]="�汾״̬";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
      
      iArray[7]=new Array();
      iArray[7][0]="�汾����";         			//����
      iArray[7][1]="250px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      

      

      RulesVersionGrid = new MulLineEnter( "document" , "RulesVersionGrid" ); 
      RulesVersionGrid.mulLineCount = 5;   
      RulesVersionGrid.displayTitle = 1;
      RulesVersionGrid.canSel=1;
      RulesVersionGrid.locked = 1;	
			RulesVersionGrid.hiddenPlus = 1;
			RulesVersionGrid.hiddenSubtraction = 1;
      RulesVersionGrid.loadMulLine(iArray);  
      RulesVersionGrid.detailInfo="������ʾ��ϸ��Ϣ";
      //RulesVersionGrid.detailClick=reportDetailClick;
     
      }
      
      catch(ex)
      {
        alert("��ʼ��RulesVersionGridʱ����"+ ex);
      }

}

</script>
