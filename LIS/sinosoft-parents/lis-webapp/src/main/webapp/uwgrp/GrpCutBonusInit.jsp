<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GrpCalBonusInit.jsp
//�����ܣ��ֺ촦��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
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
    document.all('FiscalYear').value = '';
    document.all('GrpContNo').value = '';
    document.all('RiskCode').value = '212401';
  }
  catch(ex)
  {
    alert("��GrpCalBonusInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��GrpCalBonusInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();

    initSelBox();

	initPolGrid();
  }
  catch(re)
  {
    alert("GrpCalBonusInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б��ĳ�ʼ��
function initPolGrid()
{                               
	  var iArray = new Array();

    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            	//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���屣����";        //����
      iArray[1][1]="120px";            	//�п�
      iArray[1][2]=30;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�ŵ�����";         	//����
      iArray[2][1]="120px";            	//�п�
      iArray[2][2]=30;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="������";         	//����
      iArray[3][1]="60px";            	//�п�
      iArray[3][2]=30;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��������";         	//����
      iArray[4][1]="60px";            	//�п�
      iArray[4][2]=30;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��Ч����";         	//����
      iArray[5][1]="60px";            	//�п�
      iArray[5][2]=30;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�ֺ����"         	//����
      iArray[6][1]="60px";            	//�п�
      iArray[6][2]=30;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="�ֺ�״̬";         		//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=30;            			//�����ֵ
      iArray[7][3]=2;              			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[7][10] = "Status";
      iArray[7][11] = "0|^1|�ŵ������������^3|���ָ����Ѽ���^4|���ָ����ѷ���";
      iArray[7][12] = "7";
      iArray[7][19] = "0";

      PolGrid = new MulLineEnter( "document" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 5;
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.hiddenPlus = 1;
	    PolGrid.hiddenSubtraction = 1;
      PolGrid.canSel = 1;
      PolGrid.canChk = 0;
      PolGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
    }
    catch(ex)
    {
      alert(ex);
    }
}

</script>