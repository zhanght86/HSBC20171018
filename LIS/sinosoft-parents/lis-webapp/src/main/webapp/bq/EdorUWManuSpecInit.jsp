<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�EdorUWManuSpecInit.jsp
//�����ܣ��˹��˱���Լ�б�
//�������ڣ�2005-06-24 15:10:36
//������  ��liurongxiao
//���¼�¼��  ������    ��������     ����ԭ��/����
%>


<%
  String tEdorNo = "";
  String tContNo = "";
  String tPrtNo = "";
  String tMissionID = "";
  String tSubMissionID = "";
  String tPolNo = "";
  String tEdorType = "";

  tEdorType = request.getParameter("EdorType");
  tPolNo = request.getParameter("PolNo");
  tEdorNo = request.getParameter("EdorNo");
  tContNo = request.getParameter("ContNo");
  tPrtNo = request.getParameter("PrtNo");
  tMissionID = request.getParameter("MissionID");
  tSubMissionID = request.getParameter("SubMissionID");

  loggerDebug("EdorUWManuSpecInit","polno="+tPolNo);

%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">



// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{
try
  {
   document.all('SpecReason').value="";
   document.all('Remark').value="";

  }
  catch(ex)
  {
    alert("��EdorUWManuSpecInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��EdorUWManuSpecInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}


function initForm(tContNo,tMissionID,tSubMission,tEdorNo,tEdorType,tPrtNo,tPolNo)
{
var tQueryFlag = fm.QueryFlag.value;
/*
alert("tContNo="+tContNo
      +"tMissionID="+tMissionID
      +"tSubMission="+tSubMission
      +"tEdorNo="+tEdorNo
      +"tPrtNo="+tPrtNo
      +"tPolNo="+tPolNo
      +"tQueryFlag="+tQueryFlag);
      */

  try
  {
    initInpBox();
    initSelBox();
    initPolGrid();
    initUWSpecGrid();
    initUWLPSpecGrid();
    initUWModelGrid();
    initHide(tContNo,tMissionID,tSubMission,tEdorNo,tEdorType,tPrtNo,tPolNo);
    if(tQueryFlag !=null && tQueryFlag=="1"){
      fm.button1.style.display="none";
      fm.button3.style.display="none";
      fm.button4.style.display="none";
      }
      QueryPolGrid(tContNo,tEdorNo,tPolNo);
  }
    catch(re)
  {
    alert("EdorUWManuSpecInit.jsp-->InitForm�����з����쳣:���溯������!");
  }
  try{
    QueryUWSpecGrid(tPolNo);
      QueryUWLPSpecGrid(tEdorNo,tPolNo);
    QueryUWModelGrid(tContNo);
  }
  catch(re)
  {
    alert("EdorUWManuSpecInit.jsp-->InitForm�����з����쳣:���溯������!");
  }
}

// �����������б�ĳ�ʼ��
function initPolGrid()
  {
    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                  //�п�
      iArray[0][2]=30;                      //�����ֵ
      iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������


      iArray[1]=new Array();
      iArray[1][0]="������";
      iArray[1][1]="120px";
      iArray[1][2]=100;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="������";
      iArray[2][1]="120px";
      iArray[2][2]=100;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="���ֺ�";
      iArray[3][1]="100px";
      iArray[3][2]=100;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="���ֱ���";
      iArray[4][1]="60px";
      iArray[4][2]=80;
      iArray[4][3]=2;
      iArray[4][4]="RiskCode";                          //�Ƿ����ô���:null||""Ϊ������
      iArray[4][5]="4";                                 //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[4][9]="���ֱ���|code:RiskCode&NOTNULL";
      iArray[4][18]=250;
      iArray[4][19]= 0 ;

      iArray[5]=new Array();
      iArray[5][0]="���ְ汾";
      iArray[5][1]="60px";
      iArray[5][2]=100;
      iArray[5][3]=0;

      iArray[6]=new Array();
      iArray[6][0]="Ͷ����";
      iArray[6][1]="80px";
      iArray[6][2]=100;
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="������";
      iArray[7][1]="80px";
      iArray[7][2]=100;
      iArray[7][3]=0;

      iArray[8]=new Array();
      iArray[8][0]="��������";
      iArray[8][1]="80px";
      iArray[8][2]=100;
      iArray[8][3]=0;

      PolGrid = new MulLineEnter( "fm" , "PolGrid" );
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 1;
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 0;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);
      //PolGrid.selBoxEventFuncName = "";

      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
//LCSpec��Ϣ����ʾ��ʼ��
function initUWSpecGrid()
  {
    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                  //�п�
      iArray[0][2]=30;                      //�����ֵ
      iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��Լ����";
      iArray[1][1]="710px";
      iArray[1][2]=600;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="ӡˢ��";
      iArray[2][1]="0px";
      iArray[2][2]=0;
      iArray[2][3]=3;

      iArray[3]=new Array();
      iArray[3][0]="״̬";
      iArray[3][1]="50px";
      iArray[3][2]=100;
      iArray[3][3]=3;

      iArray[4]=new Array();
      iArray[4][0]="��ӡ״̬";
      iArray[4][1]="0px";
      iArray[4][2]=0;
      iArray[4][3]=3;

      iArray[5]=new Array();
      iArray[5][0]="Ͷ������";
      iArray[5][1]="0px";
      iArray[5][2]=0;
      iArray[5][3]=3;

      iArray[6]=new Array();
      iArray[6][0]="serialno";
      iArray[6][1]="0px";
      iArray[6][2]=0;
      iArray[6][3]=3;

      UWSpecGrid = new MulLineEnter( "fm" , "UWSpecGrid" );
      //��Щ���Ա�����loadMulLineǰ
      UWSpecGrid.mulLineCount = 0;
      UWSpecGrid.displayTitle = 1;
      UWSpecGrid.locked = 1;
      UWSpecGrid.canSel = 0;
      UWSpecGrid.hiddenPlus = 1;
      UWSpecGrid.hiddenSubtraction = 1;
      UWSpecGrid.loadMulLine(iArray);
      //UWSpecGrid.selBoxEventFuncName = "getOneToChg";

      //��Щ����������loadMulLine����
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

//LPSpec��Ϣ����ʾ��ʼ��
function initUWLPSpecGrid()
  {
    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                  //�п�
      iArray[0][2]=30;                      //�����ֵ
      iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��Լ����";
      iArray[1][1]="700px";
      iArray[1][2]=800;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="ӡˢ��";
      iArray[2][1]="0px";
      iArray[2][2]=0;
      iArray[2][3]=3;

      iArray[3]=new Array();
      iArray[3][0]="״̬";
      iArray[3][1]="0px";
      iArray[3][2]=0;
      iArray[3][3]=3;

      iArray[4]=new Array();
      iArray[4][0]="״̬";
      iArray[4][1]="0px";
      iArray[4][2]=0;
      iArray[4][3]=3;

      iArray[5]=new Array();
      iArray[5][0]="Ͷ������";
      iArray[5][1]="0px";
      iArray[5][2]=0;
      iArray[5][3]=3;

      iArray[6]=new Array();
      iArray[6][0]="serialno";
      iArray[6][1]="0px";
      iArray[6][2]=0;
      iArray[6][3]=3;

      UWLPSpecGrid = new MulLineEnter( "fm" , "UWLPSpecGrid" );
      //��Щ���Ա�����loadMulLineǰ
      UWLPSpecGrid.mulLineCount = 0;
      UWLPSpecGrid.displayTitle = 1;
      UWLPSpecGrid.locked = 0;
      UWLPSpecGrid.canSel = 1;
      UWLPSpecGrid.hiddenPlus = 1;
      UWLPSpecGrid.hiddenSubtraction = 1;
      UWLPSpecGrid.loadMulLine(iArray);
      UWLPSpecGrid.selBoxEventFuncName = "getOneToChg";

      //��Щ����������loadMulLine����
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

//��Լģ����ʾ��ʼ��
function initUWModelGrid()
  {
    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                  //�п�
      iArray[0][2]=30;                      //�����ֵ
      iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��Լ����";
      iArray[1][1]="738px";
      iArray[1][2]=100;
      iArray[1][3]=0;

      UWModelGrid = new MulLineEnter( "fm" , "UWModelGrid" );
      //��Щ���Ա�����loadMulLineǰ
      UWModelGrid.mulLineCount = 4;
      UWModelGrid.displayTitle = 1;
      //PolAddGrid.locked = 1;
      UWModelGrid.canSel = 1;
      UWModelGrid.hiddenPlus = 1;
      UWModelGrid.hiddenSubtraction = 1;
      UWModelGrid.loadMulLine(iArray);
      UWModelGrid.selBoxEventFuncName = "getOneModel";

      //��Щ����������loadMulLine����
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
function initHide(tContNo,tMissionID,tSubMission,tEdorNo,tEdorType,tPrtNo,tPolNo)
{
    document.all('ContNo').value=tContNo;
    document.all('MissionID').value=tMissionID;
    document.all('SubMissionID').value=tSubMission;
    document.all('EdorNo').value=tEdorNo ;
    document.all('EdorType').value=tEdorType ;
    document.all('PrtNo').value=tPrtNo ;
    document.all('PolNo').value=tPolNo ;
}
</script>


