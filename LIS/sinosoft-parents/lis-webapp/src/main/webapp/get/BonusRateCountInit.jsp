<%
//�������ƣ�
//�����ܣ��ֺ���ϵ������
//�������ڣ�2008-11-09 17:55:57
//������  ������ͥ
%>
<!--�û�У����-->

<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {                                   
    fm.FiscalYear.value = getCurrentDate("-").substring(0,4)-1; 
  }
  catch(ex)
  {
    alert("��BonusRateCountInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
                                       
// ��������Ϣ�б�ĳ�ʼ��
function initLOBonusPolGrid()
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
      iArray[1][0]="������";    	//����
      iArray[1][1]="150px";            		//�п�
      iArray[1][2]=80;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��ͬ��";         			//����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=80;            			//�����ֵ
      iArray[2][3]=0;     
               			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3]=new Array();
      iArray[3][0]="������";         			//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=80;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�������";         			//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��ȡ��־";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="����ϵ��";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      iArray[7]=new Array();
      iArray[7][0]="Ӧ������";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="��ȡ��ʽ";         		//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;       
       
      

      LOBonusPolGrid = new MulLineEnter( "document" , "LOBonusPolGrid" );
      LOBonusPolGrid.mulLineCount = 5;
      LOBonusPolGrid.displayTitle = 1;
      LOBonusPolGrid.canSel = 1;
      LOBonusPolGrid.canChk=0;
      LOBonusPolGrid.hiddenPlus=1;
      LOBonusPolGrid.hiddenSubtraction=1;

      LOBonusPolGrid.loadMulLine(iArray);

      }
      catch(ex)
      {
        alert(ex);
      }
}
function initForm()
{
  try
  {
    initInpBox();
    initLOBonusPolGrid();
    //queryClick();
  }
  catch(re)
  {
    alert("BonusRateCountInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


</script>
