
<%
	//�������ƣ�PersonPayPlanInput.jsp
	//�����ܣ�
	//�������ڣ�2002-07-24 08:38:44
	//������  ��CrtHtml���򴴽�
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
//���ҳ��ؼ��ĳ�ʼ����
%>

<script language="JavaScript">
function initInpBox()
{
  try
  {
  	document.all('ManageCom').value = '';
  	document.all('timeEnd').value = '';
  	document.all('ContNo').value = '';
    document.all('PolNo').value = '';
    document.all('InsuredNo').value = '';
    //document.all('AppntNo').value = '';	
	//fm.all('timeStart').value = '';	
	document.all('SerialNo').value = '';
  }
  catch(ex)
  {
    alert("��PersonPayPlanInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

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
    alert("��PersonPayPlanInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initGetDrawGrid()
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
      iArray[1][0]="����֪ͨ���";    	//����
      iArray[1][1]="150px";            		//�п�
      iArray[1][2]=80;            			//�����ֵ
      iArray[1][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="������";         			//����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=80;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ͷ���˺�";         			//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="Ӧ������";         		//����
      iArray[4][1]="120px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="Ӧ�����";         		//����
      iArray[5][1]="120px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      GetGrid = new MulLineEnter( "document" , "GetGrid" );
      //��Щ���Ա�����loadMulLineǰ
      GetGrid.mulLineCount = 5;
      GetGrid.displayTitle = 1;
      GetGrid.canSel = 1;
      GetGrid.canChk = 0;
      GetGrid.hiddenPlus=1;
      GetGrid.hiddenSubtraction=1;
      GetGrid.loadMulLine(iArray);
      //��Щ����������loadMulLine����
      //SubPayGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initGetAccGrid()
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
    iArray[1][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="���ֱ���";         			//����
    iArray[2][1]="50px";            		//�п�
    iArray[2][2]=80;            			//�����ֵ
    iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="��������";         			//����
    iArray[3][1]="150px";            		//�п�
    iArray[3][2]=80;            			//�����ֵ
    iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="���������";         			//����
    iArray[4][1]="120px";            		//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="Ӧ������";         		//����
    iArray[5][1]="120px";            		//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[6]=new Array();
    iArray[6][0]="Ӧ�����";         		//����
    iArray[6][1]="120px";            		//�п�
    iArray[6][2]=100;            			//�����ֵ
    iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

    GetGridInsurAcc = new MulLineEnter( "document" , "GetGridInsurAcc" );
    //��Щ���Ա�����loadMulLineǰ
    GetGridInsurAcc.mulLineCount = 5;
    GetGridInsurAcc.displayTitle = 1;
    GetGridInsurAcc.canSel = 1;
    GetGridInsurAcc.canChk = 0;
    GetGridInsurAcc.hiddenPlus=1;
    GetGridInsurAcc.hiddenSubtraction=1;
    GetGridInsurAcc.loadMulLine(iArray);
    //��Щ����������loadMulLine����
    //SubPayGrid.setRowColData(1,1,"asdf");
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
    //initSelBox();
    initGetDrawGrid();
    initGetAccGrid();
  }
  catch(re)
  {
    alert("PersonPayPlanInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>
