<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�EdorMissionInit.jsp
//�����ܣ���ȫ�����켣��ѯ
//�������ڣ�2005-11-24 19:10:36
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
    //���ҳ��ؼ��ĳ�ʼ����
    GlobalInput globalInput = (GlobalInput)session.getValue("GI");

    if(globalInput == null) {
        out.println("session has expired");
        return;
    }

    String strOperator = globalInput.Operator;
%>

<script language="JavaScript">

function initForm()
{
  try
  {
        initInpBox();
        initEdorMissionGrid();        
        queryEdorMission();

  }
  catch(re)
  {
    alert("��EdorAppUWQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initInpBox()
{

  try
  {

  }
  catch(ex)
  {
    alert("��EdorAppUWQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

//������Ϣ��ʼ��
function initEdorMissionGrid()
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
          iArray[1][0]="������λ";
          iArray[1][1]="180px";
          iArray[1][2]=200;
          iArray[1][3]=0;

          iArray[2]=new Array();
          iArray[2][0]="����Ա";
          iArray[2][1]="100px";
          iArray[2][2]=150;
          iArray[2][3]=0;

          iArray[3]=new Array();
          iArray[3][0]="������תʱ��";
          iArray[3][1]="120px";
          iArray[3][2]=150;
          iArray[3][3]=0;
          iArray[3][21]=3;

          iArray[4]=new Array();
          iArray[4][0]="��������ʱ��";
          iArray[4][1]="120px";
          iArray[4][2]=150;
          iArray[4][3]=0;
          iArray[4][21]=3;

          iArray[5]=new Array();
          iArray[5][0]="�������ʱ��";
          iArray[5][1]="120px";
          iArray[5][2]=150;
          iArray[5][3]=0;
          iArray[5][21]=3;

          iArray[6]=new Array();
          iArray[6][0]="�������ڵ�";
          iArray[6][1]="0px";
          iArray[6][2]=100;
          iArray[6][3]=3;

          iArray[7]=new Array();
          iArray[7][0]="��������";
          iArray[7][1]="0px";
          iArray[7][2]=0;
          iArray[7][3]=3;

          iArray[8]=new Array();
          iArray[8][0]="����ʱ��";
          iArray[8][1]="0px";
          iArray[8][2]=0;
          iArray[8][3]=3;

          EdorMissionGrid = new MulLineEnter("fm", "EdorMissionGrid");
          //��Щ���Ա�����loadMulLineǰ
          EdorMissionGrid.mulLineCount = 3;
          EdorMissionGrid.displayTitle = 1;
          EdorMissionGrid.locked = 1;
          EdorMissionGrid.canSel = 0;
          EdorMissionGrid.hiddenPlus = 1;
          EdorMissionGrid.hiddenSubtraction = 1;
          EdorMissionGrid.loadMulLine(iArray);
          //��Щ����������loadMulLine����
          //EdorMissionGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
