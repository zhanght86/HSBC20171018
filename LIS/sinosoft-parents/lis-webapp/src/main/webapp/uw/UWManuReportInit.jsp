<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuReportInit.jsp
//�����ܣ��˹��˱��˱�����¼��
//�������ڣ�
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String tContNo = "";
  String tFlag = "";
  String tUWIdea = "";
  String str = "";
  String tflag = "";
  tContNo = request.getParameter("ContNo");
  tflag = request.getParameter("flag");
%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	
	String strOperator = globalInput.Operator;
	//loggerDebug("UWManuReportInit","1:"+strOperator);
%>

<script language="JavaScript">

var str = "";

// �����ĳ�ʼ��������¼���֣�
function initInpBox(tContNo)
{ 
try
  {                                   
	// �ӳ���������
    document.all('ContNo').value = tContNo;
    document.all('Operator').value = '<%= strOperator %>';
    document.all('Content').value = '';
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

function initForm(tContNo)
{
  var str = "";
  try
  {
	initInpBox(tContNo);
	//initRReportGrid();
	initHide(tContNo);
	initContent();
  }
  catch(re)
  {
    alert("UWSubInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
// ������Ϣ�б�ĳ�ʼ��
function initRReportGrid()
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
      iArray[1][12] = "1|2|3";
      iArray[1][13] = "0|1|2";

      iArray[2]=new Array();
      iArray[2][0]="��ʼ����";         			//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��ֹ����";         			//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�ӷѽ��";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

                           

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

function initHide(tContNo)
{
	var flag = '<%=tflag%>';
	
	if (flag == "1")
	{
		divButton.style.display = 'none';
	}
	
	document.all('ProposalNoHide').value=tContNo;
}

</script>


