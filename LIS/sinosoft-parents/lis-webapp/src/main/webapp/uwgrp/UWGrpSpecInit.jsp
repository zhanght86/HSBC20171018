<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWGrpSpecInit.jsp
//�����ܣ������˹��˱���Լ¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
<%
  String tProposalNo = "";
  String tFlag = "";
  String tUWIdea = "";
  String str = "";
  tProposalNo = request.getParameter("ProposalNo2");
  tFlag = request.getParameter("Flag");
  tUWIdea = request.getParameter("UWIdea");
  loggerDebug("UWGrpSpecInit","Flag:"+tFlag);
  loggerDebug("UWGrpSpecInit","UWIdea:"+tUWIdea);
%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

var str = "";

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
try
  {                                   
	// �ӳ���������
    //fm.all('Prem').value = '';
    //fm.all('SumPrem').value = '';
    //fm.all('Mult').value = '';
    //fm.all('RiskAmnt').value = '';
    fm.all('Remark').value = '';
    fm.all('Reason').value = '';
    fm.all('SpecReason').value = '';
  }
  catch(ex)
  {
    alert("��UWManuDateInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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

function initForm(tProposalNo,tFlag,tUWIdea)
{
  var str = "";
  try
  {
	initInpBox();
	str = initlist(tProposalNo);
	initUWSpecGrid(str);
	initHide(tProposalNo,tFlag,tUWIdea);
	QueryGrid(tProposalNo);
	QuerySpec(tProposalNo);
	QuerySpecReason(tProposalNo);

  }
  catch(re)
  {
    alert("UWSubInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
// ������Ϣ�б�ĳ�ʼ��
function initUWSpecGrid(str)
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
      iArray[1][0]="������Ŀ";    	//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=2;
      iArray[1][10] = "DutyCode";             			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][11] = str;
      iArray[1][12] = "1|3|4";
      iArray[1][13] = "0|1|2";

      iArray[2]=new Array();
      iArray[2][0]="������Ŀ";    	//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=2;
      iArray[2][10] = "PlanPay";             			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][11] = "0|^1|�����ӷ�^2|ְҵ�ӷ�";
      iArray[2][12] = "2";
      iArray[2][13] = "0";
             
      iArray[3]=new Array();
      iArray[3][0]="��ʼ����";         			//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��ֹ����";         			//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�ӷѽ��";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������


      SpecGrid = new MulLineEnter( "fm" , "SpecGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      SpecGrid.mulLineCount = 1;
      SpecGrid.displayTitle = 1;
      SpecGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //SpecGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tProposalNo,tFlag,tUWIdea)
{
	fm.all('ProposalNoHide').value=tProposalNo;
	fm.all('Flag').value=tFlag;
	fm.all('UWIdea').value= tUWIdea;
	//alert("pol:"+tProposalNo);
}

</script>


