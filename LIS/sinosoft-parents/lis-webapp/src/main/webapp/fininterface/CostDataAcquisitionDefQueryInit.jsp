<%
//�������ƣ�CostDataAcquisitionDefQueryInit.jsp
//�����ܣ�ƾ֤�������ݲɼ�����
//�������ڣ�2008-08-18
//������  �����  
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
  GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) 
	{
		out.println("��ҳ��ʱ�������µ�¼");
		return;
	}
	String strOperator = globalInput.Operator;
%>                          
<script language="JavaScript">
function initInpBox()
{
  try
  {     
    fm.reset();
    document.all('VersionNo').value = VersionNo;
    document.all('AcquisitionID').value = '';
    document.all('AcquisitionType').value = '';
    document.all('DataSourceType').value = '';
    document.all('CostOrDataID').value = '';
    document.all('Remark').value = '';
  }
  catch(ex)
  {
    alert("��CostDataAcquisitionDefQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("��CostDataAcquisitionDefQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initCostDataAcquisitionDefQueryGrid();
  }
  catch(re)
  {
    alert("��CostDataAcquisitionDefQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initCostDataAcquisitionDefQueryGrid()
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
      iArray[1][1]="150px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="���ݲɼ����";    	//����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="���ݲɼ�����";         			//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�ɼ����ݿ�����";         			//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="���û��������ͱ���";         			//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="���ݲɼ���ʽ";         			//����
      iArray[6][1]="50px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      iArray[7]=new Array();
      iArray[7][0]="����";         			//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      CostDataAcquisitionDefQueryGrid = new MulLineEnter( "document" , "CostDataAcquisitionDefQueryGrid" ); 
      CostDataAcquisitionDefQueryGrid.mulLineCount = 5;   
      CostDataAcquisitionDefQueryGrid.displayTitle = 1;
      CostDataAcquisitionDefQueryGrid.canSel=1;
      CostDataAcquisitionDefQueryGrid.locked = 1;	
	 	 	CostDataAcquisitionDefQueryGrid.hiddenPlus = 1;
	  	CostDataAcquisitionDefQueryGrid.hiddenSubtraction = 1;
      CostDataAcquisitionDefQueryGrid.loadMulLine(iArray);  
      CostDataAcquisitionDefQueryGrid.detailInfo="������ʾ��ϸ��Ϣ";
     
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
