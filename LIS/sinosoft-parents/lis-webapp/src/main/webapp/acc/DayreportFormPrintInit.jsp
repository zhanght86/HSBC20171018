<%
//�������ƣ�PEdorFormPrint.jsp
//�����ܣ���ȫ����������ӡ����̨
//�������ڣ�2006-09-25 09:10:00
//������  ��wangyan
//���¼�¼��  ������    ��������      ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.BqNameFun"%>

<%
  //���ҳ��ؼ��ĳ�ʼ����
  GlobalInput globalInput = (GlobalInput)session.getAttribute("GI");
  if(globalInput == null) {
    out.println("session has expired");
    return;
  }
  String strOperator = globalInput.Operator;
  String comcode = globalInput.ComCode;
  String[] dateArr = BqNameFun.getNomalMonth(PubFun.getCurrentDate());
%>

<script language="JavaScript">
function initInpBox()
{
  try
  {
    document.all('StartDate').value = '<%=dateArr[0]%>';
    document.all('EndDate').value = '<%=dateArr[1]%>';
    //document.all('SaleChnl').value = '';
    document.all('BillType').value = '';
    document.all('BillTypeName').value = '';
  //  document.all('ManageCom').value = '<%=comcode%>';
  //  document.all('ChnlType').value = '';
//    document.all('ChnlSel').value = '';
//    document.all('RiskFlag').value = "NO";
  }
  catch(ex)
  {
    alert("��DayreportFormPrintInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}
function initSelBox()
{
  try{}
  catch(ex)
  {
    alert("��PEdorFormPrintInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    var NoticeType = fm.BillType.value;
    initNoticeGrid(NoticeType);
 //   initManageCom();//��ʼ��������������ȡ6λ
  }
  catch(ex)
  {
    alert("DayreportFormPrintInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

var NoticeGrid;          //����Ϊȫ�ֱ������ṩ��displayMultilineʹ��
// Ͷ������Ϣ�б�ĳ�ʼ��
function initNoticeGrid(tNoticeType)
{
    var iArray = new Array();
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";              //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";              //�п�
      iArray[0][2]=10;                  //�����ֵ
      iArray[0][3]=0;                   //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="Ͷ���˻�����";
      iArray[1][1]="80px";
      iArray[1][2]=100;
      iArray[1][3]=2;
      iArray[1][4]="insuaccno";
      iArray[1][15]="riskcode";

      iArray[2]=new Array();
      iArray[2][0]="�Ƽ���";
      iArray[2][1]="100px";
      iArray[2][2]=100;
      iArray[2][3]=8;
      iArray[2][17]="1";
      
      iArray[3]=new Array();
      iArray[3][0]="�����";
      iArray[3][1]="80px";
      iArray[3][2]=100;
      iArray[3][3]=7;
      iArray[3][21]=3;
      iArray[3][23]="0";

      iArray[4]=new Array();
      iArray[4][0]="������";
      iArray[4][1]="80px";
      iArray[4][2]=100;
      iArray[4][3]=7;
      iArray[4][21]=3;
      iArray[4][23]="0";
      

      NoticeGrid = new MulLineEnter( "fm" , "NoticeGrid" );
      //��Щ���Ա�����loadMulLineǰ
      NoticeGrid.mulLineCount = 10;
      NoticeGrid.displayTitle = 1;
      NoticeGrid.canSel = 1;
      NoticeGrid.hiddenPlus=1;
      NoticeGrid.hiddenSubtraction=1;
      NoticeGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
