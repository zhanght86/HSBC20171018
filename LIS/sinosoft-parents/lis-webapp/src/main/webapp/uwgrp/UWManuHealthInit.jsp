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
  <%@page import="java.lang.Math.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String tContNo = "";
  String tMissionID = "";
  String tSubMissionID = "";
  String tFlag = "";
  String tUWIdea = "";  
  String tPrtNo = "";
  Date today = new Date();
  today = PubFun.calDate(today,15,"D",null);
  String tday = UWPubFun.getFixedDate(today);
  tContNo = request.getParameter("ContNo2");
  tMissionID = request.getParameter("MissionID");
  tSubMissionID = request.getParameter("SubMissionID");
  tPrtNo = request.getParameter("PrtNo");
  tFlag = request.getParameter("Flag");
 %>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
try
  {                                   
	
    fm.all('ContNo').value = '';
    fm.all('MissionID').value = '';
    fm.all('SubMissionID').value = '';
    fm.all('EDate').value = '<%=tday%>';
    fm.all('PrintFlag').value = '';
    fm.all('Hospital').value = '';
    fm.all('IfEmpty').value = '';
    fm.all('InsureNo').value = '';
    fm.all('Note').value = '';
    fm.all('bodyCheck').value = '';
  }
  catch(ex)
  {
    alert("��UWManuHealthInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��UWSubInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm(tContNo,tMissionID,tSubMission,tPrtNo,tFlag)
{
  try
  {
	initInpBox();
	//initUWHealthGrid();
	//initUWErrGrid();
	initHide(tContNo,tMissionID,tSubMission,tPrtNo,tFlag);
	initHospital(tContNo);
	initInsureNo(tPrtNo);
	easyQueryClickSingle();
	//easyQueryClick();
 }
  catch(re) {
    alert("UWManuHealthInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}



// ������Ϣ�б�ĳ�ʼ��
function initUWHealthGrid()
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
      iArray[1][0]="�����Ŀ���";    	//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][4] = "HealthCode";             			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][5]="1|2";     //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[1][6]="0|1";    //��������з������ô����еڼ�λֵ
      iArray[1][18] = 500;

      iArray[2]=new Array();
      iArray[2][0]="�����Ŀ����";         			//����
      iArray[2][1]="140px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      HealthGrid = new MulLineEnter( "fm" , "HealthGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      HealthGrid.mulLineCount = 0;
      HealthGrid.displayTitle = 1;
      HealthGrid.canChk = 0;
      
      HealthGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //HealthGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tContNo,tMissionID,tSubMission,tPrtNo,tFlag)
{
	fm.all('ContNO').value = tContNo;
    fm.all('MissionID').value = tMissionID;
	fm.all('SubMissionID').value = tSubMission ;
	fm.all('PrtNo').value = tPrtNo ;
	fm.all('Flag').value = tFlag ;
	//alert("pol:"+tContNo);
}

</script>


