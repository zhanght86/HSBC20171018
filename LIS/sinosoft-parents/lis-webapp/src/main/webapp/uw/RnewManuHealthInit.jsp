<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuHealthInit.jsp
//�����ܣ���ȫ�˹��˱��������¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
//              liurx      2005-8-30       ���Flag��־��Ϊ"1"ʱ��ʾ��ȫ����
%>
<!--�û�У����-->
  <%@page import="java.util.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String tContNo = "";
  String tMissionID = "";
  String tSubMissionID = "";
  String tFlag = "";
  String tUWIdea = "";
  String tPrtNo = "";
  String tEdorNo = "";
  Date today = new Date();
  today = PubFun.calDate(today,30,"D",null);
  String tday = UWPubFun.getFixedDate(today);
  tContNo = request.getParameter("ContNo2");
  loggerDebug("RnewManuHealthInit","tContNo:"+tContNo);
  tMissionID = request.getParameter("MissionID");
  tSubMissionID = request.getParameter("SubMissionID");
  tPrtNo = request.getParameter("PrtNo");
  tFlag = request.getParameter("Flag");
  tEdorNo = request.getParameter("EdorNo");
 %>

<script language="JavaScript">
var mContNo = '<%=tContNo%>';
var mPrtNo = '<%=tPrtNo%>';
var mFlag = '<%=tFlag%>';
var tEdorNo = '<%=tEdorNo%>'


function initForm(tContNo,tMissionID,tSubMission,tPrtNo,tFlag,tEdorNo)
{
  try
  {
    initInpBox();
	initPEGrid(); //alert(45);
    initHide(tContNo,tMissionID,tSubMission,tPrtNo,tFlag,tEdorNo); //alert(46);
    initHospital(tContNo); //alert(47);
    initInsureNo(tContNo); //alert(48);
    querySavePEInfo(); //alert(49);
    //�������ѡ����Ŀ
   	clearAllCheck(); //alert(51);
 }
  catch(re) {
    alert("�� UWManuHealthInit.jsp --> InitForm �����з����쳣:��ʼ��������� ");
  }
}

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{
try
  {
    document.all('EdorNo').value = NullToEmpty('<%=request.getParameter("EdorNo")%>');
    document.all('ContNo').value = '';
    document.all('MissionID').value = '';
    document.all('SubMissionID').value = '';
    document.all('EDate').value = '<%=tday%>';
    document.all('PrintFlag').value = '';
    document.all('Hospital').value = '';
    document.all('IfEmpty').value = 'Y';
    document.all('InsureNo').value = '';
    document.all('Note').value = '';
   // document.all('bodyCheck').value = '';
  }
  catch(ex)
  {
    alert("�� UWManuHealthInit.jsp --> InitInpBox �����з����쳣:��ʼ��������� ");
  }
}

function initHide(tContNo,tMissionID,tSubMission,tPrtNo,tFlag,tEdorNo)
{
    document.all('ContNo').value = tContNo;
    document.all('MissionID').value = tMissionID;
    document.all('SubMissionID').value = tSubMission ;
    document.all('PrtNo').value = tPrtNo ;
    document.all('Flag').value = tFlag ;
    fm.EdorNo.value = tEdorNo;
    //alert(fm.EdorNo.value);
}

// �����Ϣ�б�ĳ�ʼ��
function initPEGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�����";         		//����
      iArray[1][1]="40px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���������";         		//����
      iArray[2][1]="40px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��ӡ���";         		//����
      iArray[3][1]="40px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="¼����Ա";         		//����
      iArray[4][1]="40px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="��ӡ��ˮ��";         		//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="��ӡ���";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
  
      iArray[7]=new Array();
      iArray[7][0]="�ͻ���";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="�ͻ�������";         		//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="�����Ŀ";         		//����
      iArray[9][1]="100px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      PEGrid = new MulLineEnter( "fm" , "PEGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PEGrid.mulLineCount = 0;   
      PEGrid.displayTitle = 1;
      PEGrid.locked = 1;
      PEGrid.canSel = 1;
      PEGrid.canChk = 0;
      PEGrid.hiddenPlus = 1;
      PEGrid.hiddenSubtraction = 1;
      PEGrid.loadMulLine(iArray);  
      
      PEGrid. selBoxEventFuncName = "easyQueryAddClick";
      
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
