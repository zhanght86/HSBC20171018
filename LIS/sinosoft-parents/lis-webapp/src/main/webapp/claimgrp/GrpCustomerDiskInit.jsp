<%
//�������ƣ�ProposalCopyInput.jsp
//�����ܣ�
//�������ڣ�2002-08-21 09:25:18
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<script language="JavaScript">
function initInpBox()
{
  try
  {
    fm.all('FileName').value = '';
    fm.all('RgtNo').value = "<%=request.getParameter("RgtNo")%>";
    fm.all('Operator').value = "<%= tGlobalInput.Operator %>";
  }
  catch(ex)
  {
    alert("��ProposalCopyInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initDiskErrQueryGrid();
  }
  catch(re)
  {
    alert("ProposalCopyInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initDiskErrQueryGrid()
{
  var iArray = new Array();
  try
  {
    iArray[0]=new Array();
    iArray[0][0]="���";  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";  //�п�
    iArray[0][2]=10;    //�����ֵ
    iArray[0][3]=0;      //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="�����";
    iArray[1][1]="80px";
    iArray[1][2]=100;
    iArray[1][3]=0;

    iArray[2]=new Array();
    iArray[2][0]="�ͻ���";
    iArray[2][1]="80px";
    iArray[2][2]=100;
    iArray[2][3]=0;

    iArray[3]=new Array();
    iArray[3][0]="������";
    iArray[3][1]="60px";
    iArray[3][2]=100;
    iArray[3][3]=0;

    iArray[4]=new Array();
    iArray[4][0]="������Ϣ";
    iArray[4][1]="200px";
    iArray[4][2]=100;
    iArray[4][3]=0;

    iArray[5]=new Array();
    iArray[5][0]="���κ�";
    iArray[5][1]="10px";
    iArray[5][2]=100;
    iArray[5][3]=3;

    iArray[6]=new Array();
    iArray[6][0]="�������";
    iArray[6][1]="60px";
    iArray[6][2]=100;
    iArray[6][3]=0;
    
    iArray[7]=new Array();
    iArray[7][0]="�������Ա";
    iArray[7][1]="80px";
    iArray[7][2]=100;
    iArray[7][3]=0;
    
    iArray[8]=new Array();
    iArray[8][0]="��������";
    iArray[8][1]="80px";
    iArray[8][2]=100;
    iArray[8][3]=0;        

    iArray[9]=new Array();
    iArray[9][0]="����ʱ��";
    iArray[9][1]="80px";
    iArray[9][2]=100;
    iArray[9][3]=0;       

    DiskErrQueryGrid = new MulLineEnter( "fmquery" , "DiskErrQueryGrid" );
    //��Щ���Ա�����loadMulLineǰ
    DiskErrQueryGrid.mulLineCount = 10;
    DiskErrQueryGrid.displayTitle = 1;
    DiskErrQueryGrid.locked = 0;
    DiskErrQueryGrid.hiddenPlus=1;
    DiskErrQueryGrid.canChk =0; //��ѡ��ť��1��ʾ��0����
    DiskErrQueryGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    DiskErrQueryGrid.hiddenSubtraction=1;
    DiskErrQueryGrid.loadMulLine(iArray);
    //��Щ����������loadMulLine����
  }
  catch(ex)
  {
    alert(ex);
  }
}
/*
function initDiv()
{
  divImport.style.display='none';
}
*/
</script>
