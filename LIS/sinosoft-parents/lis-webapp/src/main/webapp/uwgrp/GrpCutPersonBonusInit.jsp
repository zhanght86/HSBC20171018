<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GrpCutPersonBonusInit.jsp
//�����ܣ��ֺ촦��
//�������ڣ�2005-08-15
//������  ��wentao
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
  try
  {
		//
  }
  catch(ex)
  {
    alert("��GrpCutPersonBonusInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��GrpCutPersonBonusInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();

    initSelBox();

		initGrpPolGrid();
		
		queryGrpPol();
  }
  catch(re)
  {
    alert("GrpCutPersonBonusInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initGrpPolGrid()
{                               
  var iArray = new Array();
    
  try
  {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="50px";          		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���˱�����";      	//����
      iArray[1][1]="120px";           	//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���屣����";     		//����
      iArray[2][1]="120px";            	//�п�
      iArray[2][2]=200;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="������";         	//����
      iArray[3][1]="80px";            	//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�ʻ�����";       		//����
      iArray[4][1]="100px";          		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�������";       		//����
      iArray[5][1]="100px";          		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="Ӧ������";       		//����
      iArray[6][1]="100px";          		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

  
      GrpPolGrid = new MulLineEnter( "document" , "GrpPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
			GrpPolGrid.mulLineCount = 5;   
			GrpPolGrid.displayTitle = 1;
			GrpPolGrid.locked = 1;
			GrpPolGrid.canSel = 1;
			GrpPolGrid.canChk = 0;
			GrpPolGrid.hiddenSubtraction=1;
			GrpPolGrid.hiddenPlus=1;
			GrpPolGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //GrpPolGrid.setRowColData(1,1,"asdf");
  }
  catch(ex)
  {
    alert(ex);
  }
}

</script>
