<%
//�������ƣ�ContInit.jsp
//�����ܣ�
//�������ڣ�2005-05-12 08:49:52
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>


<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.*"%>
<%!
	ExeSQL exeSQL = new ExeSQL();
	SSRS   ssrs   = null; 
	String getEncodedResult(String sql)
	{
		return exeSQL.getEncodedResult(sql);
	}
	SSRS execQuery(String sql)
	{
			ssrs = exeSQL.execSQL(sql);
			return ssrs;
	}	
%>

<%
  String CurrentDate = PubFun.getCurrentDate();
  String CurrentTime = PubFun.getCurrentTime();
%>  
<script language="JavaScript">
var loadFlag=<%=tLoadFlag%>;

//��ʼ�������
function initInpBox()
{ 
}

// ������ĳ�ʼ��
function initSelBox()
{
}

function initForm()
{
	initForm_old();
	getLostInfo();
}

//����ʼ��
function initForm_old()
{
  try
  {  
    //Q:scantype���ж��Ƿ���ɨ��������ڶ����涯
    if(scantype=="scan")
    {
      setFocus();
    }
    else
    {
    fm.insuworkimpart.disabled=true;
    fm.insuhealthmpart.disabled=true;
    }     
    //��ʼ��ҵ��Ա��֪
    initAgentImpartGrid();    
    //��ʼ��Ͷ���˸�֪��Ϣ
    initImpartGrid();
    //��ʼ������������ڵ�
    initMissionID();
    //��ʼ����ҵ��Ա�б�
    initMultiAgentGrid();   
    //�ж��Ƿ�����ɨ��� 
    /**********************
     *ScanFlag=0--��ɨ�衡
     *ScanFlag=1--��ɨ�衡
     **********************
     */
     
    if(this.ScanFlag == "0")
    {
      //��������PrtNo�д�Ͷ�����ţ��ʽ�Ͷ�������ֶθ�ֵ
      document.all('PrtNo').value = prtNo;
      document.all('PrtNo2').value = prtNo;
      document.all('ProposalContNo').value = prtNo;
      document.all('ContNo').value = prtNo;
      document.all('ManageCom').value = ManageCom;
      document.all('ActivityID').value = ActivityID;
      document.all('NoType').value = NoType;
      fm.PrtNo.readOnly=true; 
    }
   
    if(this.ScanFlag == "1")
    {
      document.all('PrtNo').value = prtNo;
      document.all('PrtNo2').value = prtNo;
      document.all('ProposalContNo').value = prtNo;
      document.all('ContNo').value = prtNo;
      document.all('ManageCom').value = ManageCom;
      document.all('ActivityID').value = ActivityID;
      document.all('NoType').value = NoType;
      fm.PrtNo.readOnly=true;
    }

//��Щ��Ϣû�в����,���ô˺���������.
//     getLostInfo();
    
    //���ղ�ͬ��LoadFlag���в�ͬ�Ĵ���
    //�õ�����ĵ���λ��,Ĭ��Ϊ1,��ʾ���˱���ֱ��¼��.
    /**********************************************
     *LoadFlag=1 -- ����Ͷ����ֱ��¼��
     *LoadFlag=2 -- �����¸���Ͷ����¼��
     *LoadFlag=3 -- ����Ͷ������ϸ��ѯ
     *LoadFlag=4 -- �����¸���Ͷ������ϸ��ѯ
     *LoadFlag=5 -- ����
     *LoadFlag=6 -- ��ѯ
     *LoadFlag=7 -- ��ȫ�±�����
     *LoadFlag=8 -- ��ȫ����������
     *LoadFlag=9 -- ������������
     *LoadFlag=10-- ��������
     *LoadFlag=13-- ������Ͷ���������޸�
     *LoadFlag=16-- ������Ͷ������ѯ
     *LoadFlag=25-- �˹��˱��޸�Ͷ����
     *LoadFlag=99-- �涯����
     *
     ************************************************/
     //Q: 7,8,9,10û�з�֧���� 25��֪����ʲô
     //7,8,9
//  document.all('LoadFlag').value = this.LoadFlag;
//  alert(fm.LoadFlag.value);
    //�µ�¼��
    if(this.loadFlag=="1")
    {
    	//Q:�µ�¼��Ϊ��Ҫ��PolNo��ѯ�� hl 20050505
      //detailInit(mSwitch.getVar( "PolNo" ));
        fm.SellType.value="02"; //���۷�ʽ������¼��Ĭ��Ϊ��02�����桱
		fm.AppntIDType.value="0";
		fm.AppntNativePlace.value="CHN";
		fm.NativePlace.value="CHN";
		document.getElementById("divButton").style.display="";
		document.getElementById("divAddDelButton").style.display = "";
		document.getElementById("divInputContButton").style.display = "";  
		detailInit(prtNo);
		getImpartInitInfo();
		//�жϼ��±��еļ�¼����
		checkNotePad(prtNo,loadFlag);
		
      	return;
    }
    
    //������޸�
    if(this.loadFlag=="3")
    {
	��    //��ϸ��Ϣ��ʼ��
	��   detailInit(prtNo); 
	��   AppntChkNew();
		document.getElementById("divButton").style.display="";
		document.getElementById("divInputContButton").style.display = "none";
		document.getElementById("divApproveContButton").style.display = "none";
		document.getElementById("divProblemModifyContButton").style.display = "";
		document.getElementById("operateButton").style.display="";
		document.getElementById("divAddDelButton").style.display="";
		AppntChkNew();
		//�жϼ��±��еļ�¼����
		checkNotePad(prtNo,loadFlag);
		if(scantype=="scan")
	    {
	      setFocus();
	    }
	    else
	    {
	    fm.insuworkimpart.disabled=true;
	    fm.insuhealthmpart.disabled=true;
	    }  
		return;  
    }
    
    //�µ�����  
    if(this.LoadFlag=="5")
    {    
		document.getElementById("divButton").style.display="none";
		document.getElementById("divInputContButton").style.display = "none";
		document.getElementById("divApproveModifyContButton").style.display = "none";
		document.getElementById("divApproveContButton").style.display = "";
��    //��ϸ��Ϣ��ʼ��
		<%--
		com.sinosoft.lis.tb.ContInfoQuery info = new com.sinosoft.lis.tb.ContInfoQuery(request.getParameter("prtNo"));
		%>
		<%=info.getScriptString() --%>
        detailInitServer();
        //�ж��Ƿ����ظ��ͻ�
��      AppntChkNew();
		  //�жϼ��±��еļ�¼����
		  checkNotePad(prtNo,loadFlag);

      //��ť��ѡ�ж�
      ctrlButtonDisabled(prtNo,LoadFlag);
		  
        return;  
    }
    
    //Ͷ����Ϣ��ѯ�˱�
    if (this.loadFlag == "6")
    {
      detailInit(prtNo); 
      document.getElementById("divButton").style.display="none";
      document.getElementById("riskbutton3").style.display="";  //�������ֽ���
      //��ѯͶ������ϸ��Ϣ                 
  	  return;
    }

    //��ȫ�±�����
    if(this.loadFlag == "7")
    {
      //�ж��Ƿ����ظ��ͻ�
��    AppntChkNew();
      return;  
    }    
    
    //��ȫ����������
    if(this.loadFlag == "8")
    {
      //�ж��Ƿ����ظ��ͻ�
��    AppntChkNew();
      return;  
    }    
    
    //������������
    if(this.loadFlag == "9")
    {
      //�ж��Ƿ����ظ��ͻ�
��    AppntChkNew();
      return;  
    }    
    //������������
    if(this.loadFlag == "10")
    {
      //�ж��Ƿ����ظ��ͻ�
��    AppntChkNew();
      return;  
    }    
    
    //�˹��˱��޸�Ͷ����
    if(this.loadFlag=="25")
    {      
��      //��ϸ��Ϣ��ʼ��
		detailInit(prtNo);    
		document.getElementById("divAddDelButton").style.display = "";
		document.getElementById("divchangplan").style.display = "";
		document.getElementById("divButton").style.display="";
        return;  
    }
    
    //�涯����
    if(this.loadFlag == "99")
    {
      document.getElementById("divInputContButton").style.display="none";
      document.getElementById("autoMoveButton").style.display="";    
      //�ж��Ƿ����ظ��ͻ�
��    AppntChkNew();
      return;  
    }       
  }
  catch(ex)
  {}
}

function initInsured()
{
	return ;
	<%--
	com.sinosoft.lis.tb.InsuredInfoQuery info1 = new com.sinosoft.lis.tb.InsuredInfoQuery(request.getParameter("prtNo"));
	%>
	<%=info1.getScriptString() --%>
}

// ��֪��Ϣ�б�ĳ�ʼ��
function initImpartGrid() 
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
      iArray[1][0]="��֪���";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
			iArray[1][4]="impver_appnt_bank";
      //iArray[1][9]="��֪���|len<=5";
      //iArray[1][10]="AppntImpart";
      //iArray[1][11]="0|^01|����������֪^03|������֪";
      iArray[1][18]=300;
      iArray[1][19]=1;

      iArray[2]=new Array();
      iArray[2][0]="��֪����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3";
      iArray[2][6]="0|1";
      iArray[2][9]="��֪����|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="��֪����";         		//����
      iArray[3][1]="250px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��д����";         		//����
      iArray[4][1]="150px";            		//�п�
      iArray[4][2]=150;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][9]="��д����|len<=200";

//      iArray[5]=new Array();
//      iArray[5][0]="��֪�ͻ�����";         		//����
//      iArray[5][1]="90px";            		//�п�
//      iArray[5][2]=90;            			//�����ֵ
//      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
//      iArray[5][4]="CustomerType";
//      iArray[5][9]="��֪�ͻ�����|len<=18";
//      
//      iArray[6]=new Array();
//      iArray[6][0]="��֪�ͻ�����";         		//����
//      iArray[6][1]="90px";            		//�п�
//      iArray[6][2]=90;            			//�����ֵ
//      iArray[6][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
//      iArray[6][4]="CustomerNo";
//      iArray[6][9]="��֪�ͻ�����";
//      iArray[6][15]="Cont";

      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ImpartGrid.mulLineCount = 0;   
      ImpartGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      ImpartGrid.loadMulLine(iArray);  
    }
    catch(ex) 
    {
      alert(ex);
    }
}
function initMultiAgentGrid()
{
    var iArray = new Array();
    try {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ�
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="ҵ��Ա����";         		//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][7]="queryAgentGrid";  ��    //˫�����ò�ѯҵ��Ա�ĺ���          

      iArray[2]=new Array();
      iArray[2][0]="ҵ��Ա����";         		//����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="250px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][4]="station";              			

      iArray[4]=new Array();
      iArray[4][0]="Ӫҵ������";         		//����
      iArray[4][1]="150px";            		//�п�
      iArray[4][2]=150;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
      iArray[5]=new Array();
      iArray[5][0]="Ӷ�����";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=150;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="AgentGroup";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=150;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="BranchAttr";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=150;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������


      MultiAgentGrid = new MulLineEnter( "fm" , "MultiAgentGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      MultiAgentGrid.mulLineCount = 1;   
      MultiAgentGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      MultiAgentGrid.loadMulLine(iArray);  
    }
    catch(ex) 
    {
      alert(ex);
    }
	
}
// ��֪��Ϣ�б�ĳ�ʼ��
function initAgentImpartGrid() 
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
      iArray[1][0]="��֪���";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
			iArray[1][4]="impver_agent_bank";
      //iArray[1][9]="��֪���|len<=5";
      //iArray[1][10]="AgentImpart";
      //iArray[1][11]="0|^05|ҵ��Ա��֪";
      iArray[1][18]=300;
	  	iArray[1][19]=1;

      iArray[2]=new Array();
      iArray[2][0]="��֪����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3";
      iArray[2][6]="0|1";
      iArray[2][9]="��֪����|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="��֪����";         		//����
      iArray[3][1]="250px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��д����";         		//����
      iArray[4][1]="150px";            		//�п�
      iArray[4][2]=150;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][9]="��д����|len<=200";

//      iArray[5]=new Array();
//      iArray[5][0]="��֪�ͻ�����";         		//����
//      iArray[5][1]="90px";            		//�п�
//      iArray[5][2]=90;            			//�����ֵ
//      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
//      iArray[5][4]="CustomerType";
//      iArray[5][9]="��֪�ͻ�����|len<=18";
//      
//      iArray[6]=new Array();
//      iArray[6][0]="��֪�ͻ�����";         		//����
//      iArray[6][1]="90px";            		//�п�
//      iArray[6][2]=90;            			//�����ֵ
//      iArray[6][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
//      iArray[6][4]="CustomerNo";
//      iArray[6][9]="��֪�ͻ�����";
//      iArray[6][15]="Cont";

      AgentImpartGrid = new MulLineEnter( "fm" , "AgentImpartGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      AgentImpartGrid.mulLineCount = 0;
      //AgentImpartGrid.hiddenPlus = 1;
      //AgentImpartGrid.hiddenSubtraction = 1;
      AgentImpartGrid.canChk = 0;   
      AgentImpartGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      AgentImpartGrid.loadMulLine(iArray);  

    }
    catch(ex) 
    {
      alert(ex);
    }
}



/*****************************************************
 *   ��BankContInsuredInit.jsp�е����ݼ��뵽��ҳ��
 *   
 *****************************************************
 */


 
function initInpBox2()
{ 
  try
  { 
  	//SelPolNo��ǰѡ�����ֵı�����
  	fm.SelPolNo.value='';
  	try{mSwitch.deleteVar("PolNo");}catch(ex){};
  	fm.InsuredNo.value='';
  }
  catch(ex)
  {
    alert("��ContInsuredInit2.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initSelBox2()
{  
  try                 
  {
/*********  hanlin 20050416  ��֪���Ǹ�ʲô�õġ�
   if(checktype=="1")
   {
     param="121";
     fm.pagename.value="121";     
   }
   if(checktype=="2")
   {
     param="22";
     fm.pagename.value="22";     
   }   
*********/
  }
  catch(ex)
  {
    alert("��ContInsuredInit2.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm2()
{
//  try
//  { 
//    alert("1");
    initInpBox2();
//     alert("2");
    initSelBox2();  
//     alert("3");  
    initInsuredGrid();
//     alert("4"); 
    initImpartGrid2();
//     alert("5");
    //initImpartDetailGrid2();
    initPolGrid();
//     alert("6");
    // initGrpInfo();
    //getContInfo(); 
    //��ѯ��������Ϣ��
    getInsuredInfo();  
  //   alert("7");
    document.getElementById("DivGrpNoname").style.display="none";
//     alert("8");
    //�ж��Ƿ��Ǽ�ͥ���������������ʾ�������б�
    //alert(document.all('FamilyType').value);
   /********  
    if(document.all('FamilyType').value==''||document.all('FamilyType').value==null||document.all('FamilyType').value=='0'||document.all('FamilyType').value=='false'){  
      document.all('FamilyType').value='0';
      divTempFeeInput.style.display="none";
      getProposalInsuredInfo();          //��ø��˵���Ϣ����д�����˱�
    }
  *********/
//  }
//  catch(re)
//  {
//    alert("ContInsuredInit2.jsp-->InitForm�����з����쳣:��ʼ���������!");
//  }
  
  //hanlin 20050416
  ContType = fm.ContType.value;
  if(fm.ContType.value=="2")
  {
  	initContPlanCode();
	initExecuteCom(); 
  }
  
  //���ղ�ͬ��LoadFlag���в�ͬ�Ĵ���
  //�õ�����ĵ���λ��,Ĭ��Ϊ1,��ʾ���˱���ֱ��¼��.
  /**********************************************
   *
   *LoadFlag=1  -- ����Ͷ����ֱ��¼��
   *LoadFlag=2  -- �����¸���Ͷ����¼��
   *LoadFlag=3  -- ����Ͷ������ϸ��ѯ
   *LoadFlag=4  -- �����¸���Ͷ������ϸ��ѯ
   *LoadFlag=5  -- ����
   *LoadFlag=6  -- ��ѯ
   *LoadFlag=7  -- ��ȫ�±�����
   *LoadFlag=8  -- ��ȫ����������
   *LoadFlag=9  -- ������������
   *LoadFlag=10 -- ��������
   *LoadFlag=13 -- ������Ͷ���������޸�
   *LoadFlag=16 -- ������Ͷ������ѯ
   *LoadFlag=23 -- �ŵ��˱��б��ƻ����
   *LoadFlag=25 -- �����˱��б��ƻ����
   *LoadFlag=99 -- �涯����
   *
   ************************************************/
  showCodeName();  //���뺺��


  //�����µ�¼��
  if(LoadFlag=="1")
  { 
    document.getElementById("divLCInsuredPerson").style.display="none";
    document.getElementById("divFamilyType").style.display="";
    //divPrtNo.style.display="";
    document.getElementById("divSamePerson").style.display="";
    document.getElementById("DivGrpNoname").style.display="none";  
    fm.PolTypeFlag.value='0';
    return;     
  }
  
  //�����¸���Ͷ����¼��
  if(LoadFlag=="2")
  { 
    fm.InsuredSequencename.value="������������";   
    document.getElementById("divFamilyType").style.display="none";
    document.getElementById("divSamePerson").style.display="none";
    //divPrtNo.style.display="none";
    document.getElementById("divTempFeeInput").style.display="none";
    document.getElementById("divInputContButton").style.display="none";
    document.getElementById("divGrpInputContButton").style.display="";
    document.getElementById("divLCInsuredPerson").style.display="none";   
    document.getElementById("DivRelation").style.display="none";
    document.getElementById("DivAddress").style.display="none";
    document.getElementById("DivClaim").style.display="none";       
    return;     
  }
  
  //����Ͷ������ϸ��ѯ
  if(LoadFlag=="3")
  {
    document.getElementById("divTempFeeInput").style.display="";
    //getInsuredInfo(); ǰ���Ѿ����й�
    InsuredChkNew();
    return;     
  }
  
  //�����¸���Ͷ������ϸ��ѯ
  if(LoadFlag=="4")
  {
    document.getElementById("divFamilyType").style.display="none";
    //divPrtNo.style.display="none";
    document.getElementById("divInputContButton").style.display="none";
    document.getElementById("divApproveContButton").style.display="";
    document.getElementById("divTempFeeInput").style.display="none";
    document.getElementById("divAddDelButton").style.display="none";
    document.getElementById("divSamePerson").style.display="none";
    document.getElementById("divLCInsuredPerson").style.display="none";
    document.getElementById("divCheckInsuredButton").style.display="none";        
    //getInsuredInfo();  ǰ���Ѿ����й�
    return;     
  }
  
  //����
  if(LoadFlag=="5")
  {
    document.getElementById("divTempFeeInput").style.display="";
    //getInsuredInfo();  ǰ���Ѿ����й�
    document.getElementById("divFamilyType").style.display="";
    document.getElementById("divAddDelButton").style.display="none";
    document.getElementById("butBack").style.display="none";
    document.getElementById("divSamePerson").style.display="none";   
    document.getElementById("divInputContButton").style.display="none";
    document.getElementById("divApproveContButton").style.display="";
    document.getElementById("divLCInsuredPerson").style.display="none";
//    InsuredChkNew();
    //alert("����1")
    return;     
  }

  //��ѯ
  if(LoadFlag=="6")
  {
  	//�������б�
    document.getElementById("divTempFeeInput").style.display="";
    //divPrtNo.style.display="";    
    //¼����ť
    document.getElementById("divInputContButton").style.display="none";
    
    //divQueryButton.style.display="";
    //divFamilyType.style.display="";
    //��ӡ�ɾ�����޸ı�����
    document.getElementById("divAddDelButton").style.display="none";
    //��������Ϣ����ѯ����ť
    document.getElementById("InsuredButBack").style.display="none";
    //����Ͷ����Ϊ�������˱��ˣ����������checkbox
    document.getElementById("divSamePerson").style.display="none";
    document.getElementById("divInputQuery").style.display="";
    return;     
  }

  //��ȫ�±�����
  if(LoadFlag=="7")
  {
    document.getElementById("divFamilyType").style.display="none";
    document.getElementById("divSamePerson").style.display="none";
    //divPrtNo.style.display="none";
    document.getElementById("divTempFeeInput").style.display="none";
    document.getElementById("divInputContButton").style.display="none";
    document.getElementById("divGrpInputContButton").style.display="";
    document.getElementById("divLCInsuredPerson").style.display="none";   
    fm.InsuredSequencename.value="������������"; 
    return;     
  }

  //��ȫ����������
  if(LoadFlag=="8")
  {
    return;	
  }

  //������������
  if(LoadFlag=="9")
  {
    return;	
  }

  //��������
  if(LoadFlag=="10")
  {
    return;	
  }

  //������Ͷ���������޸�
  if(LoadFlag=="13")
  { 
    document.getElementById("divFamilyType").style.display="none";
    document.getElementById("divSamePerson").style.display="none";
    //divPrtNo.style.display="none";
    document.getElementById("divTempFeeInput").style.display="none";
    document.getElementById("divInputContButton").style.display="none";
    document.getElementById("divLCInsuredPerson").style.display="none"; 
    document.getElementById("divApproveModifyContButton").style.display="";
    //getInsuredInfo();  ǰ���Ѿ����й�
    return;     
  }

  //������Ͷ������ѯ
  if(LoadFlag=="16")
  {
    fm.InsuredSequencename.value="������������";     
    document.getElementById("divTempFeeInput").style.display="none";
    document.getElementById("divInputContButton").style.display="none";
    document.getElementById("divQueryButton").style.display="";
    document.getElementById("divFamilyType").style.display="none";
    document.getElementById("divAddDelButton").style.display="none";
    document.getElementById("butBack").style.display="none";
    document.getElementById("divSamePerson").style.display="none"; 
    document.getElementById("divLCInsuredPerson").style.display="none";
    document.getElementById("DivRelation").style.display="none";
    document.getElementById("DivAddress").style.display="none";
    document.getElementById("DivClaim").style.display="none";    
    return;     
  }

  //�����˱��б��ƻ����
  if(LoadFlag=="25")
  {
    document.getElementById("divTempFeeInput").style.display="";
    //getInsuredInfo(); //ǰ���Ѿ����й�
    document.getElementById("divFamilyType").style.display="";
    document.getElementById("divAddDelButton").style.display="none";
    document.getElementById("butBack").style.display="none";
    document.getElementById("divSamePerson").style.display="none";   
    document.getElementById("divInputContButton").style.display="none";
    document.getElementById("divApproveContButton").style.display="none";
    document.getElementById("divLCInsuredPerson").style.display="none";
    document.getElementById("divchangplan").style.display="";
    return;     
  }
  
  //�涯����
  if(this.LoadFlag == "99")
  {
  	if(checktype=="1")
  	{
      document.getElementById("divAddDelButton").style.display="none";
      document.getElementById("divInputContButton").style.display="none";
      document.getElementById("autoMoveButton").style.display="";    
      return;     
    }
    if(checktype=="2")
    {
����  document.getElementById("divAddDelButton").style.display="none";
      document.getElementById("divInputContButton").style.display="none";
      document.getElementById("autoMoveButton").style.display="";    
      return;     
    }
    return;
  }

}


// ��������Ϣ�б�ĳ�ʼ��
function initInsuredGrid()
  {                               
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="40px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�ͻ�����";          		//����
      iArray[1][1]="80px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="����";         			//����
      iArray[2][1]="60px";            			//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�Ա�";      	   		//����
      iArray[3][1]="40px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=2;              	//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][4]="Sex"; 

      iArray[4]=new Array();
      iArray[4][0]="��������";      	   		//����
      iArray[4][1]="80px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0; 
      
      iArray[5]=new Array();
      iArray[5][0]="���һ�������˹�ϵ";      	   		//����
      iArray[5][1]="100px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=2; 
      iArray[5][4]="Relation";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[5][9]="�����������˹�ϵ|code:Relation&NOTNULL";
      //iArray[5][18]=300;
      
      iArray[6]=new Array();
      iArray[6][0]="�ͻ��ڲ���";      	   		//����
      iArray[6][1]="80px";            			//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=2; 
      iArray[6][10]="MutiSequenceNo";
      iArray[6][11]="0|^1|��һ�������� ^2|�ڶ��������� ^3|������������";      
	  iArray[6][19]=1;

      InsuredGrid = new MulLineEnter( "fm" , "InsuredGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      InsuredGrid.mulLineCount = 0;   
      InsuredGrid.displayTitle = 1;
      InsuredGrid.canSel =1;
      InsuredGrid. selBoxEventFuncName ="getInsuredDetail" ;     //���RadioBoxʱ��Ӧ��JS����
      InsuredGrid. hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      InsuredGrid. hiddenSubtraction=1;
      InsuredGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

// �����˸�֪��Ϣ�б�ĳ�ʼ��
function initImpartGrid2() 
{                               
    var iArray = new Array();     
    try 
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

//      iArray[1]=new Array();
//      iArray[1][0]="��֪���";         		//����
//      iArray[1][1]="60px";            		//�п�
//      iArray[1][2]=60;            			//�����ֵ
//      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
//			iArray[1][4]="impver_insu_bank";
      //iArray[1][9]="��֪���|len<=5";
      //iArray[1][10]="InsuredImpart";
      //iArray[1][11]="0|^13|������֪^14|ְҵ��֪";
//      iArray[1][18]=300;
//      iArray[1][19]=1;

      iArray[1]=new Array();
      iArray[1][0]="��֪���";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][4]="ImpartVer1";                     //�°�Ͷ������Ӧ��֪
      iArray[1][5]="1|6";
      iArray[1][6]="0|0";
      iArray[1][9]="��֪���|len<=5";
      iArray[1][15]="1";
      iArray[1][16]="1 and code not in (#101#,#102#,#103#,#105#,#106#)";
      iArray[1][18]=300;
      iArray[1][19]=1;


      iArray[2]=new Array();
      iArray[2][0]="��֪����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3|4";
      iArray[2][6]="0|1|2";
      //iArray[2][7]="getImpartCode";
      iArray[2][9]="��֪����|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="��֪����";         		//����
      iArray[3][1]="250px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��д����";         		//����
      iArray[4][1]="150px";            		//�п�
      iArray[4][2]=150;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][9]="��д����|len<=200";

//      iArray[5]=new Array();
//      iArray[5][0]="��֪�ͻ�����";         		//����
//      iArray[5][1]="90px";            		//�п�
//      iArray[5][2]=90;            			//�����ֵ
//      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
//      iArray[5][4]="CustomerType";
//      iArray[5][9]="��֪�ͻ�����|len<=18";
//      
//      iArray[6]=new Array();
//      iArray[6][0]="��֪�ͻ�����";         		//����
//      iArray[6][1]="90px";            		//�п�
//      iArray[6][2]=90;            			//�����ֵ
//      iArray[6][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
//      iArray[6][4]="CustomerNo";
//      iArray[6][9]="��֪�ͻ�����";
//      iArray[6][15]="Cont";

      ImpartInsuredGrid = new MulLineEnter( "fm" , "ImpartInsuredGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ImpartInsuredGrid.mulLineCount = 0;   
      ImpartInsuredGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      ImpartInsuredGrid.loadMulLine(iArray);  
    }
    catch(ex) 
    {
      alert(ex);
    }
}
// ��֪��ϸ��Ϣ�б�ĳ�ʼ��
function initImpartDetailGrid2() 
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
      iArray[1][0]="��֪���";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][4]="impver_insu_bank";
      //iArray[1][9]="��֪���|len<=5";
      //iArray[1][10]="InsuredImpartDetail";
      //iArray[1][11]="0|^01|����������֪^02|������֪";
      iArray[1][18]=300;
      iArray[1][19]=1;

      iArray[2]=new Array();
      iArray[2][0]="��֪����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2";
      iArray[2][6]="0";
      //iArray[2][7]="getImpartCode";
      iArray[2][9]="��֪����|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="����";         		//����
      iArray[3][1]="450px";            		//�п�
      iArray[3][2]=2000;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������


/*******************************
      iArray[3]=new Array();
      iArray[3][0]="��֪����";         		//����
      iArray[3][1]="250px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��ʼʱ��";         		//����
      iArray[4][1]="150px";            		//�п�
      iArray[4][2]=150;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][9]="��ʼʱ��|date";

      iArray[5]=new Array();
      iArray[5][0]="����ʱ��";         		//����
      iArray[5][1]="90px";            		//�п�
      iArray[5][2]=90;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][9]="����ʱ��|date";
      
      iArray[6]=new Array();
      iArray[6][0]="֤����";         		//����
      iArray[6][1]="90px";            		//�п�
      iArray[6][2]=90;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="Ŀǰ���";         		//����
      iArray[7][1]="90px";            		//�п�
      iArray[7][2]=90;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[8]=new Array();
      iArray[8][0]="�ܷ�֤��";         		//����
      iArray[8][1]="90px";            		//�п�
      iArray[8][2]=90;            			//�����ֵ
      iArray[8][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[8][4]="yesno";
*******************************/    
 
      ImpartDetailGrid = new MulLineEnter( "fm" , "ImpartDetailGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ImpartDetailGrid.mulLineCount = 0;   
      ImpartDetailGrid.displayTitle = 1;
      ImpartDetailGrid.loadMulLine(iArray);  
      
    }
    catch(ex) {
      alert(ex);
    }
}
//������������Ϣ�б��ʼ��
function initPolGrid()
{
    var iArray = new Array(); 
    try 
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		        //�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���յ����ֺ���";         		//����
      iArray[1][1]="0px";            		        //�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="���ֱ���";         		//����
      iArray[2][1]="40px";            		        //�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      //iArray[2][4]="RiskCode";              			//�Ƿ���������,1��ʾ����0��ʾ������           
      
      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="150px";            		        //�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[4]=new Array();
      iArray[4][0]="�ϼƱ���(Ԫ)";         		//����
      iArray[4][1]="40px";            		        //�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[5]=new Array();
      iArray[5][0]="����(Ԫ)";         		//����
      iArray[5][1]="40px";            		        //�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;            			//�Ƿ���������,1��ʾ����0��ʾ������
      
  		iArray[6]=new Array();
      iArray[6][0]="����";         		//����
      iArray[6][1]="40px";            		        //�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      iArray[6][4]="Currency";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[6][9]="����|code:Currency";
          
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.canSel =1;
      PolGrid. selBoxEventFuncName ="getPolDetail";
      PolGrid. hiddenPlus=1;
      PolGrid. hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);  
    }
    catch(ex) {
      alert(ex);
    }
}


function getContInfo()
{
    try { document.all( 'ContNo' ).value = mSwitch.getVar( "ContNo" ); if(document.all( 'ContNo' ).value=="false"){document.all( 'ContNo' ).value="";} } catch(ex) { };
    try { document.all( 'PrtNo' ).value = mSwitch.getVar( "PrtNo" ); } catch(ex) { };
    try { document.all( 'ProposalContNo' ).value = mSwitch.getVar( "ProposalContNo" ); } catch(ex) { };   
    try { document.all( 'GrpContNo' ).value = mSwitch.getVar( "GrpContNo" ); } catch(ex) { };
    try { document.all( 'FamilyType' ).value = mSwitch.getVar( "FamilyType" ); } catch(ex) {};
    try { document.all( 'PolTypeFlag' ).value = mSwitch.getVar( "PolType" );if(document.all( 'PolTypeFlag' ).value=="false"){document.all( 'PolTypeFlag' ).value="0";} } catch(ex) {};
    try { document.all( 'InsuredPeoples' ).value = mSwitch.getVar( "InsuredPeoples" );if(document.all( 'InsuredPeoples' ).value=="false"){document.all( 'InsuredPeoples' ).value="";} } catch(ex) {};
}

function initContPlanCode() 
{
	 //alert(mSwitch.getVar( "ProposalGrpContNo" ));
	 document.all("ContPlanCode").CodeData=getContPlanCode(mSwitch.getVar( "ProposalGrpContNo" ));	
}

function initExecuteCom()
{
	 document.all("ExecuteCom").CodeData=getExecuteCom(mSwitch.getVar( "ProposalGrpContNo" ));	
}

function initGrpInfo()
{
	//fm.PrtNo.value=mSwitch.getVar('PrtNo');
	//fm.SaleChnl.value=mSwitch.getVar('SaleChnl');
	//fm.ManageCom.value=mSwitch.getVar('ManageCom');
	//fm.AgentCode.value=mSwitch.getVar('AgentCode');
	//fm.AgentGroup.value=mSwitch.getVar('AgentGroup');
//	alert("sfasdf");
	//fm.GrpName.value=mSwitch.getVar('GrpName');
	//fm.CValiDate.value=mSwitch.getVar('CValiDate');
	//fm.ProposalGrpContNo.value = mSwitch.getVar('ProposalGrpContNo');
}




/*****************************************************
 *   ����
 *   
 *****************************************************
 */

/*********************************************************************
 *  Ͷ������Ϣ��ʼ����������loadFlag��־��Ϊ��֧
 *  ����  ��  Ͷ����ӡˢ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function detailInitServer(){
//alert("start : detailInitServer");
<%
	String sql,contNo=null ,customerNo=null ,addressNo=null ;
	
	String PrtNo=request.getParameter("prtNo");
	String ContNo=request.getParameter("ContNo");
	if (null == PrtNo || "".equals(PrtNo))
	{}
	else
	{
%>

		var PrtNo = "<%=PrtNo%>";
		var ContNo = "<%=ContNo%>";

    if(PrtNo==null) return;

    <%
       sql = "select a.BankCode,a.AccName,a.BankAccNo from LJTempFeeClass a,LJTempFee b "
               + "where (a.TempFeeNo)=(b.TempFeeNo) and a.PayMode='7' and b.TempFeeType='1' "
               + "and b.OtherNo='"+ PrtNo +"'"; 
  TransferData sTransferData=new TransferData();
  sTransferData.setNameAndValue("SQL", sql);
  VData sVData = new VData();
  sVData.add(sTransferData);
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  String sOverDueFlag = "";
  SSRS MRSSRS = new SSRS();
  if(tBusinessDelegate.submitData(sVData, "execSQL", "ExeSQLUI"))
  {
	  VData responseVData = tBusinessDelegate.getResult();
	  MRSSRS = (SSRS)responseVData.getObjectByObjectName("SSRS",0);
  }
    %>
         arrResult = decodeResult("<%=MRSSRS.encode()%>");
      
    //alert(arrResult);
    if(arrResult==null)
    {}
    else
    {
    //alert("aaaaa");
      displayFirstAccount();
      //������ڽ��ѷ�ʽΪ����ת�ˣ�����ͬ��Ϊ����ת��
      document.all('PayMode').value="";
    }


//    arrResult=easyExecSql("select * from LCCont where PrtNo='"+PrtNo+"'",1,0);
		<% 
		   sql = "select * from LCCont where PrtNo='"+PrtNo+"'";
		   TransferData sTransferData2=new TransferData();
		   sTransferData2.setNameAndValue("SQL", sql);
		   VData sVData2 = new VData();
		   sVData2.add(sTransferData2);
		   BusinessDelegate tBusinessDelegate2=BusinessDelegate.getBusinessDelegate();
		   SSRS MRSSRS2 = new SSRS();
		   if(tBusinessDelegate2.submitData(sVData2, "execSQL", "ExeSQLUI"))
		   {
		 	  VData responseVData = tBusinessDelegate2.getResult();
		 	  MRSSRS2 = (SSRS)responseVData.getObjectByObjectName("SSRS",0);
		   }
//		execQuery(sql);
			ssrs = MRSSRS2 ;
			
			if(ssrs.getMaxRow()<1)
			{}
			else
			{
				customerNo = ssrs.GetText(1,20);
		%>
		arrResult = decodeResult("<%=ssrs.encode()%>");

    if(arrResult==null){
      alert("δ�õ�Ͷ������Ϣ");
      return;
    }
    else{
      //alert(arrResult);
      displayLCCont();       //��ʾͶ������ϸ����
    }


//    arrResult = easyExecSql("select a.* from LDPerson a where 1=1  and a.CustomerNo = '" + arrResult[0][19] + "'", 1, 0);
		<% sql="select a.* from LDPerson a where 1=1  and a.CustomerNo = '" + customerNo + "'"; 
		   TransferData sTransferData3=new TransferData();
		   sTransferData3.setNameAndValue("SQL", sql);
		   VData sVData3 = new VData();
		   sVData3.add(sTransferData3);
		   BusinessDelegate tBusinessDelegate3=BusinessDelegate.getBusinessDelegate();
		   SSRS MRSSRS3 = new SSRS();
		   if(tBusinessDelegate3.submitData(sVData3, "execSQL", "ExeSQLUI"))
		   {
		 	  VData responseVData = tBusinessDelegate3.getResult();
		 	 MRSSRS3 = (SSRS)responseVData.getObjectByObjectName("SSRS",0);
		   }
 		%>
		arrResult = decodeResult("<%=MRSSRS3.encode()%>");

    if (arrResult == null) {
      alert("δ�鵽Ͷ���˿ͻ�����Ϣ");
    }
    else{
      //��ʾͶ������Ϣ
     // alert("start : displayAppnt");
      displayAppnt(arrResult[0]);
    }

    //arrResult=easyExecSql("select * from LCAppnt where PrtNo='"+PrtNo+"'",1,0);
		<% 
			sql = "select * from LCAppnt where PrtNo='"+PrtNo+"'";
		   TransferData sTransferData4=new TransferData();
		   sTransferData4.setNameAndValue("SQL", sql);
		   VData sVData4 = new VData();
		   sVData4.add(sTransferData4);
		   BusinessDelegate tBusinessDelegate4=BusinessDelegate.getBusinessDelegate();
		   SSRS MRSSRS4 = new SSRS();
		   if(tBusinessDelegate4.submitData(sVData4, "execSQL", "ExeSQLUI"))
		   {
		 	  VData responseVData = tBusinessDelegate4.getResult();
		 	  MRSSRS4 = (SSRS)responseVData.getObjectByObjectName("SSRS",0);
		   }
//			execQuery(sql);
			ssrs = MRSSRS4;
			if(ssrs.getMaxRow()<1)
			{}
			else
			{
			contNo = ssrs.GetText(1,2);
			customerNo = ssrs.GetText(1,4);
			addressNo = ssrs.GetText(1,10);
			
		%>
		arrResult = decodeResult("<%=ssrs.encode()%>");
	  //alert(arrResult[0][0]);
    if(arrResult==null){
      alert("δ�õ�Ͷ���˱�������Ϣ");
      return;
    }else{
      displayContAppnt();       //��ʾͶ���˵���ϸ����
    }
    getAge();
    var tContNo = arrResult[0][1];
    var tCustomerNo = arrResult[0][3];		// �õ�Ͷ���˿ͻ���
    var tAddressNo = arrResult[0][9]; 		// �õ�Ͷ���˵�ַ��


    fm.AppntAddressNo.value=tAddressNo;


    //��ѯ����ʾͶ���˵�ַ��ϸ����
    //getdetailaddress();
		<% 
			sql = "select b.* from LCAddress b where b.AddressNo='"+addressNo+"' and b.CustomerNo='"+customerNo+"'";
		   TransferData sTransferData5=new TransferData();
		   sTransferData5.setNameAndValue("SQL", sql);
		   VData sVData5 = new VData();
		   sVData5.add(sTransferData5);
		   BusinessDelegate tBusinessDelegate5=BusinessDelegate.getBusinessDelegate();
		   SSRS MRSSRS5 = new SSRS();
		   if(tBusinessDelegate5.submitData(sVData5, "execSQL", "ExeSQLUI"))
		   {
		 	  VData responseVData = tBusinessDelegate5.getResult();
		 	  MRSSRS5 = (SSRS)responseVData.getObjectByObjectName("SSRS",0);
		   }
		   ssrs = MRSSRS5;
//		execQuery(sql);		
		%>
		arrResult = decodeResult("<%=ssrs.encode()%>");
    displayDetailAddress(arrResult);

    //alert("zzz");
    //��ѯͶ���˸�֪��Ϣ
//    var strSQL0="select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '1'";
//    var strSQL1="select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '2'";
//		arrResult = easyExecSql(strSQL0,1,0);
//		arrResult1 = easyExecSql(strSQL1,1,0);
	<%
		sql="select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+customerNo+"' and ContNo='"+contNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '1'";
	   TransferData sTransferData6=new TransferData();
	   sTransferData6.setNameAndValue("SQL", sql);
	   VData sVData6 = new VData();
	   sVData6.add(sTransferData6);
	   BusinessDelegate tBusinessDelegate6=BusinessDelegate.getBusinessDelegate();
	   String MRStr6 = new String();
	   if(tBusinessDelegate6.submitData(sVData6, "getOneValue", "ExeSQLUI"))
	   {
	 	  VData responseVData = tBusinessDelegate6.getResult();
	 	  MRStr6 = (String)responseVData.getObjectByObjectName("String",0);
	   }
	%>
		arrResult = decodeResult("<%=MRStr6%>");		

	<%
	 sql="select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+customerNo+"' and ContNo='"+contNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '2'";
	   TransferData sTransferData7=new TransferData();
	   sTransferData7.setNameAndValue("SQL", sql);
	   VData sVData7 = new VData();
	   sVData7.add(sTransferData7);
	   BusinessDelegate tBusinessDelegate7=BusinessDelegate.getBusinessDelegate();
	   String MRStr7 = new String();
	   if(tBusinessDelegate7.submitData(sVData7, "getOneValue", "ExeSQLUI"))
	   {
	 	  VData responseVData = tBusinessDelegate7.getResult();
	 	  MRStr7 = (String)responseVData.getObjectByObjectName("String",0);
	   }
	%>
		arrResult1 = decodeResult("<%=MRStr7%>");		

    try{document.all('Income0').value= arrResult[0][0];}catch(ex){};
    try{document.all('IncomeWay0').value= arrResult1[0][0];}catch(ex){};

//    var strSQL1="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNoType='0' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"' and impartver in ('01','02') and impartcode<>'001'";
//    turnPage.strQueryResult = easyQueryVer3(strSQL1, 1, 0, 1);
	<%
		sql = "select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNoType='0' and CustomerNo='"+customerNo+"' and ContNo='"+contNo+"' and impartver in ('01','03') ";
	   TransferData sTransferData8=new TransferData();
	   sTransferData8.setNameAndValue("SQL", sql);
	   VData sVData8 = new VData();
	   sVData8.add(sTransferData8);
	   BusinessDelegate tBusinessDelegate8=BusinessDelegate.getBusinessDelegate();
	   String MRStr8 = new String();
	   if(tBusinessDelegate8.submitData(sVData8, "getOneValue", "ExeSQLUI"))
	   {
	 	  VData responseVData = tBusinessDelegate8.getResult();
	 	  MRStr8 = (String)responseVData.getObjectByObjectName("String",0);
	   }	
	%>     
	displayML("<%=MRStr8%>",ImpartGrid);
        
    //����з�֧
    if(loadFlag=="5"||loadFlag=="25"){
      //showCodeName();
    }    
    //alert("tContNo=="+tContNo);
         
         
         
//   var aSQL="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNoType='2' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"'";
//   turnPage.queryModal(aSQL, AgentImpartGrid);
	<%
		sql = "select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNoType='2' and CustomerNo='"+customerNo+"' and ContNo='"+contNo+"'";
	   TransferData sTransferData9=new TransferData();
	   sTransferData9.setNameAndValue("SQL", sql);
	   VData sVData9 = new VData();
	   sVData9.add(sTransferData9);
	   BusinessDelegate tBusinessDelegate9=BusinessDelegate.getBusinessDelegate();
	   String MRStr9 = new String();
	   if(tBusinessDelegate9.submitData(sVData9, "getOneValue", "ExeSQLUI"))
	   {
	 	  VData responseVData = tBusinessDelegate9.getResult();
	 	  MRStr9 = (String)responseVData.getObjectByObjectName("String",0);
	   }	
	%>
	displayML("<%=MRStr9%>",AgentImpartGrid);
    
    
<%}
	}
	}
	%>
}        

function initInsuredServer()
{
	<%	if(contNo!=null&&!"".equals(contNo))
	{
	%>
	//alert("document.all(InsuredNo).value1"+document.all("InsuredNo").value);
	<%
	 sql ="select InsuredNo,Name,Sex,Birthday,RelationToMainInsured,SequenceNo from LCInsured where ContNo='"+contNo+"'";
	   TransferData sTransferData10=new TransferData();
	   sTransferData10.setNameAndValue("SQL", sql);
	   VData sVData10 = new VData();
	   sVData10.add(sTransferData10);
	   BusinessDelegate tBusinessDelegate10=BusinessDelegate.getBusinessDelegate();
	   SSRS MRSSRS10 = new SSRS();
	   if(tBusinessDelegate10.submitData(sVData10, "execSQL", "ExeSQLUI"))
	   {
	 	  VData responseVData = tBusinessDelegate10.getResult();
	 	  MRSSRS10 = (SSRS)responseVData.getObjectByObjectName("SSRS",0);
	   }
	   ssrs = MRSSRS10;
//	execQuery(sql);
	 
	 if(ssrs.getMaxRow()<1)
	 {}
	 else
	 {
	 String insuredNo = ssrs.GetText(1,1);
	%>
	//alert("document.all(InsuredNo).value2"+document.all("InsuredNo").value);
	//displayML("<%=ssrs.encode()%>",InsuredGrid);
	try{document.all(InsuredGrid.findSpanID(0)).all('InsuredGridSel').checked =true;} catch(ex) {}
	//alert("document.all(InsuredNo).value3"+document.all("InsuredNo").value);
   var InsuredNo=InsuredGrid.getRowColData(0,1);
   var ContNo = fm.ContNo.value;
   //alert("document.all(InsuredNo).value4"+document.all("InsuredNo").value);
   <% sql ="select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+insuredNo+"'"; %>
    var strSQLtemp1 ="select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+InsuredNo+"'";
    arrResult=easyExecSql(strSQLtemp1);
    //alert(arrResult);
    if(arrResult!=null)
    {
        displayInsuredInfo();
        //alert("document.all(InsuredNo).value4.1"+document.all("InsuredNo").value);
    }

    <% 
    	sql ="select * from LCInsured where ContNo = '"+contNo+"' and InsuredNo='"+insuredNo+"'"; 
       TransferData sTransferData11=new TransferData();
	   sTransferData11.setNameAndValue("SQL", sql);
	   VData sVData11 = new VData();
	   sVData11.add(sTransferData11);
	   BusinessDelegate tBusinessDelegate11=BusinessDelegate.getBusinessDelegate();
	   SSRS MRSSRS11 = new SSRS();
	   if(tBusinessDelegate11.submitData(sVData11, "execSQL", "ExeSQLUI"))
	   {
	 	  VData responseVData = tBusinessDelegate11.getResult();
	 	  MRSSRS11 = (SSRS)responseVData.getObjectByObjectName("SSRS",0);
	   }
	   ssrs = MRSSRS11;
//    execQuery(sql);
    	
    	//���û��¼�뱻���ˣ����ܵ��´���
    	
    	String insuredAddressNo = ssrs.GetText(1,11);
    %>
    //arrResult=decodeResult("<%=ssrs.encode()%>");
    //alert("document.all(InsuredNo).value5"+document.all("InsuredNo").value);
    var strSQLtemp ="select * from LCInsured where ContNo = '"+ContNo+"' and InsuredNo='"+InsuredNo+"'";
	//alert("document.all(InsuredNo).value6"+document.all("InsuredNo").value);
    arrResult=easyExecSql(strSQLtemp);
    //alert(arrResult);
    if(arrResult!=null)
    {
        DisplayInsured();
    }

    var tAddressNo = arrResult[0][10]; 		// �õ������˵�ַ��
    //alert("arrResult[0][10]=="+arrResult[0][10]);
    fm.InsuredAddressNo.value=tAddressNo;

    //��ʾ�����˵�ַ��Ϣ
    
    <% sql ="select b.AddressNo,b.PostalAddress,b.ZipCode,b.Phone,b.Mobile,b.EMail,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode,b.fax,b.HomePhone,b.grpName,b.province,b.city,b.County from LCAddress b where b.AddressNo='"+insuredAddressNo+"' and b.CustomerNo='"+insuredNo+"'";
       TransferData sTransferData12=new TransferData();
	   sTransferData12.setNameAndValue("SQL", sql);
	   VData sVData12 = new VData();
	   sVData12.add(sTransferData12);
	   BusinessDelegate tBusinessDelegate12=BusinessDelegate.getBusinessDelegate();
	   SSRS MRSSRS12 = new SSRS();
	   if(tBusinessDelegate12.submitData(sVData12, "execSQL", "ExeSQLUI"))
	   {
	 	  VData responseVData = tBusinessDelegate12.getResult();
	 	  MRSSRS12 = (SSRS)responseVData.getObjectByObjectName("SSRS",0);
	   }
	   ssrs = MRSSRS12;
//    execQuery(sql);
    %>
    arrResult=decodeResult("<%=ssrs.encode()%>");
    displayDetailAddress2(arrResult)

    <% sql = "select PolNo,RiskCode,(select RiskName from LMRiskApp where RiskCode=LCPol.RiskCode),Prem,Amnt from LCPol where InsuredNo='"+insuredNo+"' and ContNo='"+contNo+"'"; 
       TransferData sTransferData13=new TransferData();
	   sTransferData13.setNameAndValue("SQL", sql);
	   VData sVData13 = new VData();
	   sVData13.add(sTransferData13);
	   BusinessDelegate tBusinessDelegate13=BusinessDelegate.getBusinessDelegate();
	   String MRStr13 = new String();
	   if(tBusinessDelegate13.submitData(sVData13, "getOneValue", "ExeSQLUI"))
	   {
	 	  VData responseVData = tBusinessDelegate13.getResult();
	 	  MRStr13 = (String)responseVData.getObjectByObjectName("String",0);
	   }	
    %>
    displayML("<%=MRStr13%>",PolGrid);


    <% sql="select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='"+insuredNo+"' and ContNo='"+contNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '1'";
       TransferData sTransferData14=new TransferData();
	   sTransferData14.setNameAndValue("SQL", sql);
	   VData sVData14 = new VData();
	   sVData14.add(sTransferData14);
	   BusinessDelegate tBusinessDelegate14=BusinessDelegate.getBusinessDelegate();
	   SSRS MRSSRS14 = new SSRS();
	   if(tBusinessDelegate14.submitData(sVData14, "execSQL", "ExeSQLUI"))
	   {
	 	  VData responseVData = tBusinessDelegate14.getResult();
	 	  MRSSRS14 = (SSRS)responseVData.getObjectByObjectName("SSRS",0);
	   }
	   ssrs = MRSSRS14;
//    execQuery(sql);
    %>
    arrResult=decodeResult("<%=ssrs.encode()%>");
    
	  <% sql="select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='"+insuredNo+"' and ContNo='"+contNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '2'";
	   TransferData sTransferData15=new TransferData();
	   sTransferData15.setNameAndValue("SQL", sql);
	   VData sVData15 = new VData();
	   sVData15.add(sTransferData15);
	   BusinessDelegate tBusinessDelegate15=BusinessDelegate.getBusinessDelegate();
	   SSRS MRSSRS15 = new SSRS();
	   if(tBusinessDelegate15.submitData(sVData15, "execSQL", "ExeSQLUI"))
	   {
	 	  VData responseVData = tBusinessDelegate15.getResult();
	 	  MRSSRS15 = (SSRS)responseVData.getObjectByObjectName("SSRS",0);
	   }
	   ssrs = MRSSRS15;
//	  execQuery(sql);
    %>
    arrResult1=decodeResult("<%=ssrs.encode()%>");

	    try{document.all('Income').value= arrResult[0][0];}catch(ex){};
	    try{document.all('IncomeWay').value= arrResult1[0][0];}catch(ex){};

    <% //sql ="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNo='"+insuredNo+"' and ContNo='"+contNo+"' and CustomerNoType='1' ";
			 //execQuery(sql);
		%>
		
		var aSQL="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and CustomerNoType='1' ";
		   turnPage.queryModal(aSQL, ImpartInsuredGrid);
		//prompt("",aSQL);
 	//alert("document.all(InsuredNo).value7"+document.all("InsuredNo").value);
    //getImpartDetailInfo();
    getAge2();
    //��¼�������в���Ҫ���пͻ��ϲ�����ע�͵���hl 20050505
    //����Ǹ���״̬��������ظ��ͻ�У��
    if(LoadFlag=="5"){
      InsuredChkNew();
      return;
    }
    //InsuredNo
	//alert("document.all(InsuredNo).value8"+document.all("InsuredNo").value);
<%}}%>
	
}


function decodeResult(str)
{        
  if (!str) {
    return null;
  }      
  else if (str.substring(0, str.indexOf("|")) != "0") {
    return null;
  }      
  else { 
    return decodeEasyQueryResult(str);
  }      
}

function displayML(strResult,grid)
{
        turnPage.strQueryResult  = strResult;
        //alert("turnPage==="+turnPage.strQueryResult);
        //�ж��Ƿ��ѯ�ɹ�
        if (!strResult || strResult.substring(0, strResult.indexOf("|")) != "0")
        {
        	grid.clearData();
        	return;
      	}
        
        turnPage.useSimulation = 1;
        
        //��ѯ�ɹ������ַ��������ض�ά����
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //���ó�ʼ������MULTILINE����
        turnPage.pageDisplayGrid = grid;
        //����SQL���
        turnPage.strQuerySql = "";
        //���ò�ѯ��ʼλ��
        turnPage.pageIndex = 0;
        //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //����MULTILINE������ʾ��ѯ���
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
}

//Ͷ�����ֵ�Ͷ�ʼƻ�
function initInvestPlanRate()
{
    var iArray = new Array();

    try {
      iArray[0] = new Array();
      iArray[0][0] = "���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1] = "30px";            		//�п�
      iArray[0][2] = 10;            			//�����ֵ
      iArray[0][3] = 0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1] = new Array();
      iArray[1][0] = "Ͷ���ʻ���";    	        //����
      iArray[1][1] = "80px";            		//�п�
      iArray[1][2] = 100;            			//�����ֵ
      iArray[1][3] = 0;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
     // iArray[1][14]=document.all('PolNo').value;
  
      iArray[2] = new Array();
      iArray[2][0] = "Ͷ���ʻ�����";         		//����
      iArray[2][1] = "100px";            		//�п�
      iArray[2][2] = 60;            			//�����ֵ
      iArray[2][3] = 0;                   			//�Ƿ���������,1��ʾ����0��ʾ������
   //   iArray[2][4] ="findinsuaccno";
   //   iArray[2][15] ="PolNo";
   //   iArray[2][16] =document.all('PolNo').value;
   
   
      
      iArray[3] = new Array();
      iArray[3][0] = "Ͷ�ʱ�������";         		//����
      iArray[3][1] = "0px";            		//�п�
      iArray[3][2] = 60;            			//�����ֵ
      iArray[3][3] = 0;              			//�Ƿ���������,1��ʾ����0��ʾ������
     // iArray[3][4] = "fpayplancode";
   //    iArray[3][15] ="PolNo";
    //   iArray[3][16] =document.all('PolNo').value;

      iArray[4] = new Array();
      iArray[4][0] = "Ͷ�ʱ�������";         		//����
      iArray[4][1] = "0px";            		//�п�
      iArray[4][2] = 50;            			//�����ֵ
      iArray[4][3] = 0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5] = new Array();
      iArray[5][0] = "Ͷ�ʱ���";         		//����
      iArray[5][1] = "80px";            		//�п�
      iArray[5][2] = 50;            			//�����ֵ
      iArray[5][3] = 0;              			//�Ƿ���������,1��ʾ����0��ʾ������

 
      iArray[6] = new Array();
      iArray[6][0] = "�ɷѱ��";         		//����
      iArray[6][1] = "0px";            		//�п�
      iArray[6][2] = 100;            			//�����ֵ
      iArray[6][3] = 0;              			//�Ƿ���������,1��ʾ����0��ʾ������

        
      /*iArray[7] = new Array();
      iArray[7][0] = "";         		//����
      iArray[7][1] = "80px";            		//�п�
      iArray[7][2] = 100;            			//�����ֵ
      iArray[7][3] = 1;


     
      iArray[8] = new Array();
      iArray[8][0] = "";         		//����
      iArray[8][1] = "80px";            		//�п�
      iArray[8][2] = 100;            			//�����ֵ
      iArray[8][3] = 1;*/
 
      InvestPlanRate = new MulLineEnter( "fm" , "InvestPlanRate" );
      //��Щ���Ա�����loadMulLineǰ
      InvestPlanRate.mulLineCount = 0;
     InvestPlanRate.displayTitle = 1;
     InvestPlanRate.hiddenPlus = 1;
      InvestPlanRate.hiddenSubtraction = 1;
      InvestPlanRate.loadMulLine(iArray);
    }
    catch(ex) {
      alert(ex);
    }
}

</script> 
