<%
//�������ƣ�ContInit.jsp
//�����ܣ�
//�������ڣ�2005-05-12 08:49:52
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>


<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String CurrentDate = PubFun.getCurrentDate();
  String CurrentTime = PubFun.getCurrentTime();
%>  
<script language="JavaScript">
var loadFlag=<%=tLoadFlag%>;
var tCurrentDate=<%=CurrentDate%>;

//��ʼ�������
function initInpBox() {

}

// ������ĳ�ʼ��
function initSelBox(){

}

var d;
//����ʼ��
function initForm(){
//  try{
  	d = new Date();

  
    //Q:scantype���ж��Ƿ���ɨ��������ڶ����涯
    if(scantype=="scan")
    {
      
    }  
    
    else
    {
      fm.appntfinaimpart.disabled=true;
      fm.appnthealimpart.disabled=true;
      fm.insufinaimpart.disabled=true;
      fm.insuhealimpart.disabled=true;
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
     *ScanFlag=0--��ɨ��  
     *ScanFlag=1--��ɨ��  
     **********************
     */
     
    if(this.ScanFlag == "0")
    {
      //alert(1421412321312);
      //��������PrtNo�д�Ͷ�����ţ��ʽ�Ͷ�������ֶθ�ֵ

      document.all('PrtNo').value = prtNo;
      document.all('PrtNo2').value = prtNo;
      
      document.all('ProposalContNo').value = prtNo;
      document.all('ContNo').value = prtNo;
      document.all('ManageCom').value = ManageCom;
      document.all('ActivityID').value = ActivityID;
      document.all('NoType').value = NoType;
      
      fm.PrtNo.readOnly=true; 
      // 2005-08-14 zhouping
      // �����е��õĵط�
      // showCodeName();
     
      //showOneCodeName('comcode','ManageCom','ManageComName');  //���뺺��
   
      //showOneCodeName('comcode','AgentManageCom','AgentManageComName');
     
      //detailInit(prtNo);
      //getImpartInitInfo(); 
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
      // 2005-08-14 zhouping
      // �����е��õĵط�
      // showCodeName();
      //getImpartInitInfo();
      //showOneCodeName('comcode','ManageCom','ManageComName');  //���뺺��
      //alert(2)
      //showOneCodeName('comcode','AgentManageCom','AgentManageComName');
      //alert(3)
    }
 
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
  
    //�µ�¼��
    if(this.loadFlag=="1")
    {
		//Q:�µ�¼��Ϊ��Ҫ��PolNo��ѯ�� hl 20050505
		//detailInit(mSwitch.getVar( "PolNo" ));
		fm.SellType.value="01";//���۷�ʽ������¼��Ĭ��Ϊ��01��ҵ��Ա��
		
		
		fm.AppntIDType.value="";
		fm.AppntNativePlace.value="CHN";
		fm.NativePlace.value="CHN";
		divButton.style.display="";
		divAddDelButton.style.display = "";
		divInputContButton.style.display = "";  
		detailInit(prtNo,ContNo); 
		getImpartInitInfo();
		//�жϼ��±��еļ�¼����
		checkNotePad(prtNo,loadFlag);
		setFocus();
      return;
    }
    
    //������޸�
    if(this.loadFlag=="3"){

      //��ϸ��Ϣ��ʼ��
      detailInit(prtNo,ContNo); 
     //alert("");
			divButton.style.display="";
			divInputContButton.style.display = "none";
			divApproveContButton.style.display = "none";
			divProblemModifyContButton.style.display = "";
			operateButton.style.display="";
			divAddDelButton.style.display="";
			//alert();
			AppntChkNew();
			//alert(1);
			
      //�жϼ��±��еļ�¼����
      checkNotePad(prtNo,loadFlag);
			setFocus();
			//alert(2);
      return;  
    }
    
    //�µ�����  
    if(this.LoadFlag=="5"){    
     
   
      divButton.style.display="none";
		  divInputContButton.style.display = "none";
		  divApproveModifyContButton.style.display = "none";
      divApproveContButton.style.display = "";
      //��ϸ��Ϣ��ʼ��
	
      detailInit(prtNo,ContNo);    
      //�ж��Ƿ����ظ��ͻ�
      AppntChkNew();

      //�жϼ��±��еļ�¼����
      checkNotePad(prtNo,LoadFlag);
      
      //��ť��ѡ�ж�
      ctrlButtonDisabled(prtNo,LoadFlag);
      
      
      setFocus();

      return;  
      
    }
    
    //Ͷ����Ϣ��ѯ�˱�
    if (this.loadFlag == "6"){
      detailInit(prtNo,ContNo); 
      divButton.style.display="none";
      //document.all("Donextbutton1").style.display="none";
      //document.all("Donextbutton2").style.display="none";
      //document.all("Donextbutton3").style.display="";
      //document.all("Donextbutton5").style.display="none";
      //document.all("butBack").style.display="none";
      //divAddDelButton.style.display="none";
      //divInputContButton.style.display="";
      document.all("riskbutton3").style.display="";  //�������ֽ���
      //��ѯͶ������ϸ��Ϣ                 
	setFocus();
  	  return;
    }

    //��ȫ�±�����
    if(this.loadFlag == "7"){


      //�ж��Ƿ����ظ��ͻ�
      AppntChkNew();
	setFocus();
      return;  
    }    
    
    //��ȫ����������
    if(this.loadFlag == "8"){

      //�ж��Ƿ����ظ��ͻ�
      AppntChkNew();
	setFocus();
      return;  
    }    
    
    //������������
    if(this.loadFlag == "9"){

      //�ж��Ƿ����ظ��ͻ�
      AppntChkNew();
	setFocus();
      return;  
    }    

    //������������
    if(this.loadFlag == "10"){

      //�ж��Ƿ����ظ��ͻ�
      AppntChkNew();
	setFocus();
      return;  
    }    

    
    //�˹��˱��޸�Ͷ����
    if(this.loadFlag=="25"){      
      //��ϸ��Ϣ��ʼ��
      //alert(1);
      detailInit(prtNo,ContNo);    
		  divAddDelButton.style.display = "none";
      divchangplan.style.display = "";
      divButton.style.display="none";
      return;  
    }
    
    //�涯����
    if(this.loadFlag == "99"){
      divInputContButton.style.display="none";
      autoMoveButton.style.display="";    

      //�ж��Ƿ����ظ��ͻ�
      AppntChkNew();
	setFocus();
      return;  
    }    
     if (this.loadFlag == "17"){
      detailInit(prtNo,ContNo); 
      divButton.style.display="none";
      //document.all("Donextbutton1").style.display="none";
      //document.all("Donextbutton2").style.display="none";
      //document.all("Donextbutton3").style.display="";
      //document.all("Donextbutton5").style.display="none";
      //document.all("butBack").style.display="none";
      //divAddDelButton.style.display="none";
      divInputQuery.style.display="";
      document.all("riskbutton3").style.display="";  //�������ֽ���
      //��ѯͶ������ϸ��Ϣ                 
	setFocus();
  	  return;
    }

    //Q:�����г��������´���û�����ù�ע�͵�
    /**********
    var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
    //��ϸ��Ϣ��ʼ��
    detailInit(tPolNo,ContNo); 
    AppntChkNew();
    ***********/
    //alert("ContNo=="+document.all("ContNo").value);
    
    document.all('ProposalContNo').value = prtNo;//add by yaory
//  }
//  catch(ex){}
}

// ��֪��Ϣ�б�ĳ�ʼ��
function initImpartGrid() {  
    var iArray = new Array();
      
    try {
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
      iArray[1][4]="impartver";
      iArray[1][9]="��֪���|len<=5";
      iArray[1][10]="AppntImpart";
      iArray[1][11]="0|^01|����������֪^02|������֪";
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
      iArray[2][9]="��֪����|len<=6";
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

      ImpartGrid = new MulLineEnter( "document" , "ImpartGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ImpartGrid.mulLineCount = 5;   
      ImpartGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      ImpartGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
}
function initMultiAgentGrid(){
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
      iArray[1][7]="queryAgentGrid";    //˫�����ò�ѯҵ��Ա�ĺ���          

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


      MultiAgentGrid = new MulLineEnter( "document" , "MultiAgentGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      MultiAgentGrid.mulLineCount = 5;   
      MultiAgentGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      MultiAgentGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
	
}
// ��֪��Ϣ�б�ĳ�ʼ��
function initAgentImpartGrid() {  
    var iArray = new Array();
      
    try {
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
      iArray[1][4]="impver_agent_sig";
      iArray[1][9]="��֪���|len<=5";
      iArray[1][10]="AgentImpart";
      iArray[1][11]="0|^05|ҵ��Ա��֪";
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
      iArray[2][9]="��֪����|len<=6";
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

      AgentImpartGrid = new MulLineEnter( "document" , "AgentImpartGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      AgentImpartGrid.mulLineCount = 5;
      //AgentImpartGrid.hiddenPlus = 1;
      //AgentImpartGrid.hiddenSubtraction = 1;
      AgentImpartGrid.canChk = 0;   
      AgentImpartGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      AgentImpartGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //ImpartGrid.setRowColData(1,1,"asdf");

    }
    catch(ex) {
      alert(ex);
    }
}



/*****************************************************
 *   ��ContInsuredInit2.jsp�е����ݼ��뵽��ҳ��
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

    initInpBox2();
    initSelBox2();    
    initInsuredGrid(); 
    initImpartGrid2();
    initPolGrid();   
    //��ѯ��������Ϣ��
    getInsuredInfo();  
    DivGrpNoname.style.display="none";
    //�ж��Ƿ��Ǽ�ͥ���������������ʾ�������б�

   /********  
    if(document.all('FamilyType').value==''||document.all('FamilyType').value==null||document.all('FamilyType').value=='0'||document.all('FamilyType').value=='false'){  
      document.all('FamilyType').value='0';
      divTempFeeInput.style.display="none";
      getProposalInsuredInfo();          //��ø��˵���Ϣ����д�����˱�
    }
  *********/

  
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

// 	alert(new Date()-d);


  //�����µ�¼��
  if(LoadFlag=="1")
  { 
    divLCInsuredPerson.style.display="none";
    divFamilyType.style.display="";
    //divPrtNo.style.display="";
    divSamePerson.style.display="";
    DivGrpNoname.style.display="none";  
    fm.PolTypeFlag.value='0';
    if(InsuredGrid.mulLineCount==0)
    {
	    fm.SequenceNo.value="1";//�ڵ�һ��¼�뱻������ʱĬ��Ϊ��1���������ˡ�
	    fm.SequenceNoName.value="��������";//�ڵ�һ��¼�뱻������ʱĬ��Ϊ��1���������ˡ�
	    fm.RelationToMainInsured.value="00";//���һ�������˹�ϵ:���������˿ͻ��ڲ���Ϊ��1���������ˡ�ʱ��Ĭ��Ϊ��00�����ˡ�
	    fm.RelationToAppnt.value="";	
    }
    return;     
  }
  
  //�����¸���Ͷ����¼��
  if(LoadFlag=="2"){ 
    fm.InsuredSequencename.value="������������";   
    divFamilyType.style.display="none";
    divSamePerson.style.display="none";
    //divPrtNo.style.display="none";
    divTempFeeInput.style.display="none";
    divInputContButton.style.display="none";
    divGrpInputContButton.style.display="";
    divLCInsuredPerson.style.display="none";   
    DivRelation.style.display="none";
    DivAddress.style.display="none";
    DivClaim.style.display="none";       
    return;     
  }
  
  //����Ͷ������ϸ��ѯ
  if(LoadFlag=="3"){
    divTempFeeInput.style.display="";
    //getInsuredInfo(); ǰ���Ѿ����й�
    InsuredChkNew();
    return;     
  }
  
  //�����¸���Ͷ������ϸ��ѯ
  if(LoadFlag=="4"){
    divFamilyType.style.display="none";
    //divPrtNo.style.display="none";
    divInputContButton.style.display="none";
    divApproveContButton.style.display="";
    divTempFeeInput.style.display="none";
    divAddDelButton.style.display="none";
    divSamePerson.style.display="none";
    divLCInsuredPerson.style.display="none";
    divCheckInsuredButton.style.display="none";        
    //getInsuredInfo();  ǰ���Ѿ����й�
    return;     
  }
  
  //����
  if(LoadFlag=="5"){
   // alert("����")
    divTempFeeInput.style.display="";
    //getInsuredInfo();  ǰ���Ѿ����й�
    divFamilyType.style.display="";
    divAddDelButton.style.display="none";
    fm.butBack.style.display="none";
    divSamePerson.style.display="none";   
    
    divInputContButton.style.display="none";
    divApproveContButton.style.display="";
    divLCInsuredPerson.style.display="none";
    //alert("����1")
    return;     
  }

  //��ѯ
  if(LoadFlag=="6"){
  	//�������б�
    divTempFeeInput.style.display="";
    //divPrtNo.style.display="";    
    //¼����ť
    divInputContButton.style.display="none";
    
    //divQueryButton.style.display="";
    divFamilyType.style.display="";
    //��ӡ�ɾ�����޸ı�����
    divAddDelButton.style.display="none";
    //��������Ϣ����ѯ����ť
    fm.InsuredButBack.style.display="none";
    //����Ͷ����Ϊ�������˱��ˣ����������checkbox
    divSamePerson.style.display="none";
    divInputQuery.style.display="";
    return;     
  }

  //��ȫ�±�����
  if(LoadFlag=="7"){
    divFamilyType.style.display="none";
    divSamePerson.style.display="none";
    //divPrtNo.style.display="none";
    divTempFeeInput.style.display="none";
    divInputContButton.style.display="none";
    divGrpInputContButton.style.display="";
    divLCInsuredPerson.style.display="none";   
    fm.InsuredSequencename.value="������������"; 
    return;     
  }

  //��ȫ����������
  if(LoadFlag=="8"){
    return;	
  }

  //������������
  if(LoadFlag=="9"){
    return;	
  }

  //��������
  if(LoadFlag=="10"){
    return;	
  }

  //������Ͷ���������޸�
  if(LoadFlag=="13"){ 
    divFamilyType.style.display="none";
    divSamePerson.style.display="none";
    //divPrtNo.style.display="none";
    divTempFeeInput.style.display="none";
    divInputContButton.style.display="none";
    divLCInsuredPerson.style.display="none"; 
    divApproveModifyContButton.style.display="";
    //getInsuredInfo();  ǰ���Ѿ����й�
    return;     
  }

  //������Ͷ������ѯ
  if(LoadFlag=="16"){
    fm.InsuredSequencename.value="������������";     
    divTempFeeInput.style.display="none";
    divInputContButton.style.display="none";
    divQueryButton.style.display="";
    divFamilyType.style.display="none";
    divAddDelButton.style.display="none";
    fm.butBack.style.display="none";
    divSamePerson.style.display="none"; 
    divLCInsuredPerson.style.display="none";
    DivRelation.style.display="none";
    DivAddress.style.display="none";
    DivClaim.style.display="none";    
    return;     
  }

  //�����˱��б��ƻ����
  if(LoadFlag=="25"){
    divTempFeeInput.style.display="";
    //getInsuredInfo(); //ǰ���Ѿ����й�
    divFamilyType.style.display="";
    divAddDelButton.style.display="none";
    fm.butBack.style.display="none";
    divSamePerson.style.display="none";   
    divInputContButton.style.display="none";
    divApproveContButton.style.display="none";
    divLCInsuredPerson.style.display="none";
    divchangplan.style.display="";
//    riskbutton34.style.display="none";
    return;     
  }
  
  //�涯����
  if(this.LoadFlag == "99"){
  	if(checktype=="1"){
      divAddDelButton.style.display="none";
      divInputContButton.style.display="none";
      autoMoveButton.style.display="";    
      return;     
    }
    if(checktype=="2"){
      divAddDelButton.style.display="none";
      divInputContButton.style.display="none";
      autoMoveButton.style.display="";    
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
      iArray[4][2]=80;            			//�����ֵ
      iArray[4][3]=8; 
      
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

      InsuredGrid = new MulLineEnter( "document" , "InsuredGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      InsuredGrid.mulLineCount = 5;   
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
function initImpartGrid2() {                               
    var iArray = new Array();
      
    try {
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
	  iArray[1][4]="impartver";
      iArray[1][9]="��֪���|len<=5";
      iArray[1][10]="InsuredImpart";
      iArray[1][11]="0|^01|����������֪^02|������֪";
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
      iArray[2][9]="��֪����|len<=6";
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

      ImpartInsuredGrid = new MulLineEnter( "document" , "ImpartInsuredGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ImpartInsuredGrid.mulLineCount = 0;   
      ImpartInsuredGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      ImpartInsuredGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
}
// ��֪��ϸ��Ϣ�б�ĳ�ʼ��
function initImpartDetailGrid2() {                               
    var iArray = new Array();
      
    try {
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
			iArray[1][4]="impver_insu_sig";
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
      iArray[2][9]="��֪����|len<=6";
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
 
      ImpartDetailGrid = new MulLineEnter( "document" , "ImpartDetailGrid" ); 
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
      iArray[3][1]="80px";            		        //�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[4]=new Array();
      iArray[4][0]="�����ڼ�";         		//����
      iArray[4][1]="40px";            		        //�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[5]=new Array();
      iArray[5][0]="��������";         		//����
      iArray[5][1]="40px";            		        //�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[6]=new Array();
      iArray[6][0]="���ս��";         		//����
      iArray[6][1]="40px";            		        //�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[6][23]="0";
      
      iArray[7]=new Array();
      iArray[7][0]="Ͷ������";         		//����
      iArray[7][1]="40px";            		        //�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="�ϼƱ���(Ԫ)";         		//����
      iArray[8][1]="60px";            		        //�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      iArray[8][23]="0";
      
      iArray[9]=new Array();
      iArray[9][0]="ְҵ�ӷ�(Ԫ)";         		//����
      iArray[9][1]="60px";            		        //�п�
      iArray[9][2]=60;            			//�����ֵ
      iArray[9][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      iArray[9][23]="0";
      
      iArray[10]=new Array();
      iArray[10][0]="����";         		//����
      iArray[10][1]="40px";            		        //�п�
      iArray[10][2]=60;            			//�����ֵ
      iArray[10][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      iArray[10][4]="Currency";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[10][9]="����|code:Currency";
      
      PolGrid = new MulLineEnter( "document" , "PolGrid" ); 
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

function initPolGrid2()
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
      iArray[3][1]="80px";            		        //�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[4]=new Array();
      iArray[4][0]="�����ڼ�";         		//����
      iArray[4][1]="40px";            		        //�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[5]=new Array();
      iArray[5][0]="��������";         		//����
      iArray[5][1]="40px";            		        //�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[6]=new Array();
      iArray[6][0]="���ս��";         		//����
      iArray[6][1]="40px";            		        //�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="Ͷ������";         		//����
      iArray[7][1]="40px";            		        //�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="����(Ԫ)";         		//����
      iArray[8][1]="40px";            		        //�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      
      PolGrid = new MulLineEnter( "document" , "PolGrid" ); 
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

function initPolGrid3()
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
      iArray[3][1]="80px";            		        //�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[4]=new Array();
      iArray[4][0]="�����ڼ�";         		//����
      iArray[4][1]="40px";            		        //�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[5]=new Array();
      iArray[5][0]="��������";         		//����
      iArray[5][1]="40px";            		        //�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[6]=new Array();
      iArray[6][0]="���ս��";         		//����
      iArray[6][1]="40px";            		        //�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="Ͷ������";         		//����
      iArray[7][1]="40px";            		        //�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="����(Ԫ)";         		//����
      iArray[8][1]="40px";            		        //�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      
      PolGrid = new MulLineEnter( "document" , "PolGrid" ); 
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
 
      InvestPlanRate = new MulLineEnter( "document" , "InvestPlanRate" );
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
