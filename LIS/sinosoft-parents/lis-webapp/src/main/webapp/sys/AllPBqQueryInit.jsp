<%
//�������ƣ�AllPBqQueryInit.jsp
//�����ܣ�
//�������ڣ�2002-12-16
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

    <%@ page import="com.sinosoft.lis.pubfun.*" %>

    <%
        String sYesterday = PubFun.calDate(PubFun.getCurrentDate(), -1, "D", null);
        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput = (GlobalInput)session.getValue("GI");
        String sManageCom = tGlobalInput.ManageCom;
        tGlobalInput = null;
    %>

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{

  try
  {
    // ������ѯ����
//    if (tflag=="0")
 //     document.all('PolNo').value = tPolNo;
//      alert(tPolNo);
//    else
//    {
//      document.all('PolNo').value = '';
//      document.all('EdorNo').value = '';
//      document.all('EdorType').value = '';
//      document.all('RiskCode').value = '';
//      document.all('InsuredNo').value = '';
//      document.all('EdorAppDate').value = '';
//      document.all('ManageCom').value = '<%--=tG.ManageCom--%>';

        document.all('EdorAcceptNo').value = '';
        document.all('OtherNo').value = '';
        document.all('OtherNoType').value = '';
        document.all('EdorAppName').value = '';
        document.all('AppType').value = '';
        document.all('EdorAppDate').value = '';
        document.getElementsByName("LoginManageCom")[0].value = "<%=sManageCom%>";
//    }
  }
  catch(ex)
  {
    alert("��AllPBqQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

// ������ĳ�ʼ��
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
    alert("��AllPBqQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initPolGrid();
    //initManageCom(); //��ʼ��������������ȡ6λ
        //if(tflag=="0") easyQueryClick();
  }
  catch(re)
  {
    alert("AllGBqQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    alert(re);
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initPolGrid()
  {
    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                  //�п�
      iArray[0][2]=10;                      //�����ֵ
      iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�������";
      iArray[1][1]="130px";
      iArray[1][2]=100;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="�ͻ�/������";
      iArray[2][1]="130px";
      iArray[2][2]=100;
      iArray[2][3]=0;

//      iArray[3]=new Array();
//      iArray[3][0]="���ֱ���";
//      iArray[3][1]="80px";
//      iArray[3][2]=100;
//      iArray[3][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="��������";
      iArray[3][1]="80px";
      iArray[3][2]=100;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="����������";
      iArray[4][1]="0px";
      iArray[4][2]=100;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="��/�˷ѽ��";
      iArray[5][1]="80px";
      iArray[5][2]=100;
      iArray[5][3]=0;

      iArray[6]=new Array();
      iArray[6][0]="������";
      iArray[6][1]="0px";
      iArray[6][2]=100;
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="ȷ����";
      iArray[7][1]="0px";
      iArray[7][2]=100;
      iArray[7][3]=0;

      iArray[8]=new Array();
      iArray[8][0]="����״̬";
      iArray[8][1]="80px";
      iArray[8][2]=100;
      iArray[8][3]=0;

      iArray[9]=new Array();
      iArray[9][0]="������λ";
      iArray[9][1]="120px";
      iArray[9][2]=100;
      iArray[9][3]=0;

      iArray[10]=new Array();
      iArray[10][0]="����Ա";
      iArray[10][1]="80px";
      iArray[10][2]=100;
      iArray[10][3]=0;

      iArray[11]=new Array();
      iArray[11][0]="��������";
      iArray[11][1]="120px";
      iArray[11][2]=100;
      iArray[11][3]=0;

      iArray[12]=new Array();
      iArray[12][0]="�������";
      iArray[12][1]="0px";
      iArray[12][2]=100;
      iArray[12][3]=3;

      iArray[13]=new Array();
      iArray[13][0]="���ʱ��";
      iArray[13][1]="0px";
      iArray[13][2]=100;
      iArray[13][3]=3;

      iArray[14]=new Array();
      iArray[14][0]="�������";
      iArray[14][1]="140px";
      iArray[14][2]=100;
      iArray[14][3]=0;

      PolGrid = new MulLineEnter("fm", "PolGrid");
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 10;
      PolGrid.displayTitle = 1;
      PolGrid.hiddenSubtraction=1;
      PolGrid.hiddenPlus = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.loadMulLine(iArray);
      PolGrid. selBoxEventFuncName = "PBqQueryClick";

      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
