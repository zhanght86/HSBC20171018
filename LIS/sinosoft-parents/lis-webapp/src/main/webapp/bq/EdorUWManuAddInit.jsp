<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�EdorUWManuAddInit.jsp
//�����ܣ��˹��˱��ӷ�
//�������ڣ�2005-07-16 11:10:36
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
<%
  String tContNo = "";
  String tPolNo = "";
  String tMissionID = "";
  String tSubMissionID = "";
  String tProposalNo = "";
  String tInsuredNo="";
  String tEdorNo="";
  String tEdorAcceptNo="";
tContNo = request.getParameter("ContNo");
tEdorNo = request.getParameter("EdorNo");
//tPolNo = request.getParameter("PolNo");
tMissionID = request.getParameter("MissionID");
tSubMissionID = request.getParameter("SubMissionID");
tInsuredNo = request.getParameter("InsuredNo");
tEdorAcceptNo = request.getParameter("EdorAcceptNo");
loggerDebug("EdorUWManuAddInit",tEdorAcceptNo);

%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

var str = "";
var tRow = "";

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
try
  {                                   
	document.all('AddReason').value = '';
   }
  catch(ex)
  {
    alert("��EdorUWManuAddInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��EdorUWManuAddInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm(tEdorNo,tPolNo,tContNo, tMissionID, tSubMissionID,tInsuredNo,tEdorAcceptNo)
{
  var str = "";
  try
  {
  fm.EdorAcceptNo.value = tEdorAcceptNo;
	initInpBox();
	initPolAddGrid();
	initHide(tEdorNo,tPolNo,tContNo, tMissionID, tSubMissionID,tInsuredNo);
	QueryPolAddGrid(tEdorNo,tContNo,tInsuredNo);	
 }
  catch(re)
  {
   alert("��EdorUWManuAddInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
// ������Ϣ�б�ĳ�ʼ��
function initUWSpecGrid(str)
{                              
    var iArray = new Array();
      
	try
	{
		//�ж�������ӷѻ��Ǹ��ռӷѣ���ʾ�ֶβ�ͬ
		if (fm.OtherNoType.value == '4') //����
		{

      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

      iArray[1]=new Array();
      iArray[1][0]="���α���";    	        //����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=2;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
      iArray[1][10] = "DutyCode";             			
      iArray[1][11] = str;
      iArray[1][12] = "1";
      iArray[1][19] = 1;	

      iArray[2]=new Array();
      iArray[2][0]="�ӷ�����";    	        //����
      iArray[2][1]="50px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=3;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��   
      iArray[2][10] = "PlanPay";             			
      iArray[2][11] = "0|^01|�����ӷ�^02|ְҵ�ӷ�^03|��Ч�����ӷ�^04|��Чְҵ�ӷ�";
      iArray[2][12] = "2";
      iArray[2][13] = "0";
      iArray[2][14] = "01";
      iArray[2][19]= 1 ;
             
      iArray[3]=new Array();
      iArray[3][0]="�ӷѷ�ʽ";         		//����
      iArray[3][1]="50px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
      //iArray[3][4]="addmode";
      iArray[3][10] = "AddMode";             			
      iArray[3][11] = "0|^01|׷�ݼӷ�|^02|���ڼӷ�";
      iArray[3][12] = "3";
      iArray[3][13] = "0";
      iArray[3][19]= 1 ;

      iArray[4]=new Array();
      iArray[4][0]="�ӷ�����";         		//����
      iArray[4][1]="50px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ�� 
      
      iArray[5]=new Array();
      iArray[5][0]="�ڶ��������˼ӷ�����";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ�� 

      iArray[6]=new Array();
      iArray[6][0]="�ӷѶ���";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=3;
      iArray[6][10] = "PayObject";             			
      iArray[6][11] = "0|^01|Ͷ����^02|��������^03|�౻������^04|�ڶ���������";
      iArray[6][12] = "6";
      iArray[6][13] = "0";
      iArray[6][19]= 1 ;     
     
      iArray[7]=new Array();
      iArray[7][0]="�ӷѽ��";         		//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ�� 
      iArray[7][7]="";
         
      iArray[8]=new Array();
      iArray[8][0]="�ӷѱ���";         		//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ�� 
         
      iArray[9]=new Array();
      iArray[9][0]="�ӷ�����";         		//����
      iArray[9][1]="80px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ�� 
         
      iArray[10]=new Array();
      iArray[10][0]="��������";         	//����
      iArray[10][1]="80px";            		//�п�
      iArray[10][2]=100;            		//�����ֵ
      iArray[10][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ�� 
         
      iArray[11]=new Array();
      iArray[11][0]="�ӷ�ֹ��";         	//����
      iArray[11][1]="80px";            		//�п�
      iArray[11][2]=100;            		//�����ֵ
      iArray[11][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ�� 
		
		}
		else
		{
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

      iArray[1]=new Array();
      iArray[1][0]="���α���";    	        //����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=2;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
      iArray[1][10] = "DutyCode";             			
      iArray[1][11] = str;
      iArray[1][12] = "1";
      iArray[1][19] = 1;	

      iArray[2]=new Array();
      iArray[2][0]="�ӷ�����";    	        //����
      iArray[2][1]="50px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=2;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��   
      iArray[2][10] = "PlanPay";             			
      iArray[2][11] = "0|^01|�����ӷ�^02|ְҵ�ӷ�^03|��Ч�����ӷ�^04|��Чְҵ�ӷ�";
      iArray[2][12] = "2";
      iArray[2][13] = "0";
      iArray[2][19]= 1 ;
             
      iArray[3]=new Array();
      iArray[3][0]="�ӷѷ�ʽ";         		//����
      iArray[3][1]="50px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
      //iArray[3][4]="addmode";
      iArray[3][10] = "AddMode";             			
      iArray[3][11] = "0|^01|׷�ݼӷ�|^02|���ڼӷ�";
      iArray[3][12] = "3";
      iArray[3][13] = "0";
      iArray[3][19]= 1 ;

      iArray[4]=new Array();
      iArray[4][0]="�ӷ�����";         		//����
      iArray[4][1]="50px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ�� 
      
      iArray[5]=new Array();
      iArray[5][0]="�ڶ��������˼ӷ�����";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ�� 

      iArray[6]=new Array();
      iArray[6][0]="�ӷѶ���";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=2;
      iArray[6][10] = "PayObject";             			
      iArray[6][11] = "0|^01|Ͷ����^02|��������^03|�౻������^04|�ڶ���������";
      iArray[6][12] = "6";
      iArray[6][13] = "0";
      iArray[6][19]= 1 ;     
     
      iArray[7]=new Array();
      iArray[7][0]="�ӷѽ��";         		//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ�� 
      iArray[7][7]="CalHealthAddFee";
         
      iArray[8]=new Array();
      iArray[8][0]="�ӷѱ���";         		//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ�� 
         
      iArray[9]=new Array();
      iArray[9][0]="�ӷ�����";         		//����
      iArray[9][1]="80px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ�� 
         
      iArray[10]=new Array();
      iArray[10][0]="��������";         	//����
      iArray[10][1]="80px";            		//�п�
      iArray[10][2]=100;            		//�����ֵ
      iArray[10][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ�� 
         
      iArray[11]=new Array();
      iArray[11][0]="�ӷ�ֹ��";         	//����
      iArray[11][1]="80px";            		//�п�
      iArray[11][2]=100;            		//�����ֵ
      iArray[11][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ�� 
		}
         

      SpecGrid = new MulLineEnter( "fm" , "SpecGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      SpecGrid.mulLineCount = 1;
      SpecGrid.canSel = 0;
      SpecGrid.displayTitle = 1;
      SpecGrid.loadMulLine(iArray);  
       //��Щ����������loadMulLine����
       
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ������Ϣ�б�ĳ�ʼ��
function initPolAddGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="������";         		//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="������";         		//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[4]=new Array();
      iArray[4][0]="�������ֺ�";         		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="ӡˢ��";         		//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="���ֱ���";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=80;            			//�����ֵ
      iArray[6][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[6][4]="RiskCode";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[6][5]="4";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[6][9]="���ֱ���|code:RiskCode&NOTNULL";
      iArray[6][18]=250;
      iArray[6][19]=0 ;

      iArray[7]=new Array();
      iArray[7][0]="���ְ汾";         		//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="Ͷ����";         		//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="������";         		//����
      iArray[9][1]="80px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="����";         		//����
      iArray[10][1]="0px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[11]=new Array();
      iArray[11][0]="���պ�";         		//����
      iArray[11][1]="0px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      PolAddGrid = new MulLineEnter( "fm" , "PolAddGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolAddGrid.mulLineCount = 3;   
      PolAddGrid.displayTitle = 1;
      //PolAddGrid.locked = 1;
      PolAddGrid.canSel = 1;
      PolAddGrid.hiddenPlus = 1;
      PolAddGrid.hiddenSubtraction = 1;
      PolAddGrid.loadMulLine(iArray);       
      PolAddGrid.selBoxEventFuncName = "getPolGridCho";
     
      //��Щ����������loadMulLine����
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tEdorNo,tPolNo,tContNo,tMissionID, tSubMissionID,tInsuredNo)
{        
  document.all('EdorNo').value = tEdorNo;
	document.all('PolNo').value = tPolNo;
	document.all('ContNo').value = tContNo;
	document.all('MissionID').value = tMissionID;
	document.all('SubMissionID').value = tSubMissionID;
	document.all('InsuredNo').value = tInsuredNo;
	
//     var strSQL;
//     strSQL =  " select othernotype from lpedorapp where edoracceptno = '" + document.all('EdorAcceptNo').value + "' ";
    var strSQL = "";
    var sqlid1="EdorUWManuAddInitSql1";
    var mySql1=new SqlClass();
    mySql1.setResourceName("bq.EdorUWManuAddInitSql"); //ָ��ʹ�õ�properties�ļ���
    mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
    mySql1.addSubPara(tGrpContNo);//ָ������Ĳ���
    strSQL=mySql1.getString();    
    var brr = easyExecSql(strSQL);

    if ( brr )
    {
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.OtherNoType.value  = brr[0][0];
    }


}

</script>


