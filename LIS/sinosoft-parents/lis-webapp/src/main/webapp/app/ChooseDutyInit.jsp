<%
//�������ƣ�CaseReceiptInput.jsp
//�����ܣ�
//�������ڣ�2002-07-21 20:09:20
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
<%
  String tRiskCode="";
  String tRiskVersion="";
  tRiskCode = request.getParameter("RiskCode");
  tRiskVersion = request.getParameter("RiskVersion");
%>                            
<SCRIPT src="../common/cvar/CVar.js"></SCRIPT>
<script language="JavaScript">
var _Code_FIELDDELIMITER    = "|";            //��֮��ķָ���
var _Code_RECORDDELIMITER   = "^";            //��¼֮��ķָ���

/*************************************************************
 *  ���ַ���������Ϊһ������
 *  ����  ��  strValue����Ҫ�������ַ���
 *  ����ֵ��  ���ִ�гɹ����򷵻��ַ������飬���ִ�в��ɹ����򷵻�false
 *************************************************************
 */
function decodeString(strValue)
{
	var i,i1,j,j1;
  var strValue;                         //��ŷ������˷��صĴ�������
  var arrField;
  var arrRecord;
  var arrCode = new Array();             //��ų�ʼ������ʱ��
  var t_Str;

  try
  {
    arrRecord = strValue.split(_Code_RECORDDELIMITER);  //����ַ������γɷ��ص�����
  
    t_Str     = getStr(arrRecord[0],1,_Code_FIELDDELIMITER);
  
    if (t_Str!="0")                                     //�����Ϊ0��ʾ��������ִ�з�������
    {
      return false;   
    }
  
    i1=arrRecord.length;
    for (i=1;i<i1;i++)
    {
      arrField  = arrRecord[i].split(_Code_FIELDDELIMITER); //����ַ���,��ÿ����¼���Ϊһ������
      j1=arrField.length;
      arrCode[i-1] = new Array();
      for (j=0;j<j1;j++)
      {
        arrCode[i-1][j] = arrField[j];
      }
    }
  }
  catch(ex)
  {
    return false;
  }
  return arrCode; 
}
gVars =new CVar();
var arrStrReturn = new Array();

var arrDuty;
var arrDutyChoose;
var arrDutyPay;
var arrDutyGet;
var arrDutyGetAlive;
var arrDutyGetClm;
var arrDutyCtrl;

function initInpBox()
{ 

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
    alert("��CaseReceiptInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
    initDutyGrid();
    initPremGrid();
    initGetGrid();
    queryDuty(); //�Ӻ�̨��ѯ���ݣ����ҷ���arrStrReturn ������
    decodeAll(); //�����ص��ַ�����������Ӧ�ı�����
    displayAll(); //�����ص�������ʾ����Ӧ�ı����
  }
  catch(re)
  {
    alert("CaseReceiptInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

//�����ص��ַ�����������Ӧ�ı�����
function decodeAll()
{
   arrDuty=decodeString(arrStrReturn[0]);
   arrDutyChoose=decodeString(arrStrReturn[1]);
   arrDutyPay=decodeString(arrStrReturn[2]);
   arrDutyGet=decodeString(arrStrReturn[3]);
   arrDutyGetAlive=decodeString(arrStrReturn[4]);
   arrDutyGetClm=decodeString(arrStrReturn[5]);
   arrDutyCtrl=decodeString(arrStrReturn[6]);
}

//�����ص�������ʾ����Ӧ�ı����
function displayAll()
{
  fm.RiskCode.value="<%=tRiskCode%>";
  fm.RiskVersion.value="<%=tRiskVersion%>";
  var i;
  var iMax;
  i=0;
  iMax=0;
  //��ʼ�����ε���������
  iMax=arrDuty.length;
  for (i=0;i<iMax;i++)
  {
    DutyGrid.addOne("DutyGrid",1);
    DutyGrid.setRowColData(i,1,arrDuty[i][0]);
    DutyGrid.setRowColData(i,2,arrDuty[i][1]);
    DutyGrid.setRowColData(i,5,arrDuty[i][2]);
    DutyGrid.setRowColData(i,6,arrDuty[i][6]);
  }
  //��ʼ�����������������
  iMax=arrDutyPay.length;
  for (i=0;i<iMax;i++)
  {
    PremGrid.addOne("PremGrid",1);
    PremGrid.setRowColData(i,2,arrDutyPay[i][0]);
    PremGrid.setRowColData(i,3,arrDutyPay[i][1]);
    PremGrid.setRowColData(i,4,arrDutyPay[i][8]);
  }
  //��ʼ�����������������
  iMax=arrDutyGet.length;
  for (i=0;i<iMax;i++)
  {
    GetGrid.addOne("GetGrid",1);
    GetGrid.setRowColData(i,2,arrDutyGet[i][0]);
    GetGrid.setRowColData(i,3,arrDutyGet[i][1]);
    GetGrid.setRowColData(i,4,arrDutyGet[i][4]);
  }
  
}
//�����б�
function initDutyGrid()
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
      iArray[1][0]="���α���";    	//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         			//����
      iArray[2][1]="160px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����";         			//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="����";         			//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��������";         			//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="��ȡ����";         			//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
      DutyGrid = new MulLineEnter( "fm" , "DutyGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      DutyGrid.mulLineCount = 1;   
      DutyGrid.displayTitle = 1;
      DutyGrid.canChk = 1;
//      DutyGrid.detailClick=queryPayAndGet;
      DutyGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //DutyGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
  }

//������Ŀ�б�
function initPremGrid()
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
      iArray[1][0]="���α���";    	//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���ѱ���";    	//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";    	//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="���ѽ��";    	//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      PremGrid = new MulLineEnter( "fm" , "PremGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PremGrid.mulLineCount = 1;   
      PremGrid.displayTitle = 1;
      PremGrid.canChk = 1;
//      DutyGrid.detailClick=queryPayAndGet;
      PremGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //DutyGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
  }

//������Ŀ�б�
function initGetGrid()
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
      iArray[1][0]="���α���";    	//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         			//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";    	//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�������";    	//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      GetGrid = new MulLineEnter( "fm" , "GetGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      GetGrid.mulLineCount = 1;   
      GetGrid.displayTitle = 1;
      GetGrid.canChk = 1;
//      DutyGrid.detailClick=queryPayAndGet;
      GetGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //DutyGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
  }

</script>

<%
	// ��ʼ��ʱ�Ĳ�ѯ���εĺ���
	out.println("<script language=javascript>");
	out.println("function queryDuty()");
	out.println("{");
	//�������
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";
	String tStr="";
	loggerDebug("ChooseDutyInit","start submit...");
	if (!tRiskCode.equals(""))
		{
		// ������Ϣ
		LCPolSchema tLCPolSchema = new LCPolSchema();
    loggerDebug("ChooseDutyInit",tRiskCode);
    loggerDebug("ChooseDutyInit",tRiskVersion);
		tLCPolSchema.setRiskCode( tRiskCode );
		tLCPolSchema.setRiskVersion( tRiskVersion );
	
	  	// ׼���������� VData
		VData tVData = new VData();
	
		tVData.addElement( tLCPolSchema );
	
	  	// ���ݴ���
	  	ProposalQueryUI tProposalQueryUI   = new ProposalQueryUI();
		if (tProposalQueryUI.submitData(tVData,"QUERY||CHOOSEDUTY") == false)
		{
	      Content = " ��ѯʧ�ܣ�ԭ����: " + tProposalQueryUI.mErrors.getError(0).errorMessage;
	      FlagStr = "Fail";
	      loggerDebug("ChooseDutyInit",Content);
		}
		else
		{
			tVData.clear();
			tVData = tProposalQueryUI.getResult();
			int i,iMax;
			iMax=tVData.size();
			//���ص����ݵ�˳��Ϊ��
      //��ѯ��������lmduty
      //��ѯ���ι�ϵ��lmdutychoose
      //��ѯ���ν��ѱ�lmdutypay
      //��ѯ���θ����ܱ�lmdutyget
      //��ѯ���θ�����������ȡ��lmdutyalive
      //��ѯ���θ�����������ȡ��lmdutygetclm
      //��ѯ���ѡ��������������Ƿ�����޸ı�lmdutyctrl
      for (i=0;i<iMax;i++)
      {
        tStr=(String)tVData.get(i);
        out.println("arrStrReturn["+tStr.valueOf(i)+"]=\""+tStr.trim()+"\";");
      }
		} // end of if
	} // end of if
	out.println("}");
	out.println("</script>");
  
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
%>
