<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuSpecInit.jsp
//�����ܣ��˹��˱������б�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
 
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
                       
<%

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
    //document.all('Prem').value = '';
    //document.all('SumPrem').value = '';
    //document.all('Mult').value = '';
    //document.all('RiskAmnt').value = '';
    //document.all('Remark').value = '';
    //document.all('Reason').value = '';
    //document.all('SpecReason').value = '';
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


function initForm()
{
  var str = "";
  try
  {
  	//alert("tInsuredNo:"+tInsuredNo);
    //alert('1');
   
    initHide();
    initGrpGrid();
   // easyQueryClick();
    initBtnDisable();
  }
  catch(re)
  {
    alert("UWSubInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initBtnDisable(){
 	var tteFlag = "<%=request.getParameter("teFlag")%>";
  //  alert(tteFlag);
	if(tteFlag != null && tteFlag == "1"){
   	 	document.getElementById("showBtn").style.display = "none";
   	 	easyQueryClick1();
    }else{
		document.getElementById("showBtn").style.display = "";  
		easyQueryClick(); 	 	
    }
}

function initHide(){
	fm.PrtSeq.value = tPrtSeq;
	fm.ContNo.value = tContNo;
	fm.MissionID.value = tMissionID;
	fm.SubMissionID.value = tSubMissionID;
	fm.ActivityID.value = tActivityID;
	fm.EdorAcceptNo.value = tEdorAcceptNo;
	fm.EdorNo.value = tEdorNo;
	fm.EdorType.value = tEdorType;
	initUWIdea();
}

function initGrpGrid()
  {     
                             
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            	//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��ȫ�����";         	//����
      iArray[1][1]="120px";            	//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         	//����
      iArray[2][1]="80px";            	//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="������";         	//����
      iArray[3][1]="80px";            	//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

	  iArray[4]=new Array();
      iArray[4][0]="��ӡ��ˮ��";         		//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������    
      
      iArray[5]=new Array();
      iArray[5][0]="�������";         		//����
      iArray[5][1]="140px";            		//�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������    

      iArray[6]=new Array();
      iArray[6][0]="�����������";      //����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[7]=new Array();
      iArray[7][0]="�������������";    //����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[8]=new Array();
      iArray[8][0]="�������Id";      //����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������   

	  iArray[9]=new Array();
      iArray[9][0]="�ͻ��Ƿ�ͬ��";      //����
      iArray[9][1]="100px";            		//�п�
      iArray[9][2]=60;            			//�����ֵ
      iArray[9][3]=2;
      iArray[9][10]="ForYN";              			//�Ƿ���������,1��ʾ����0��ʾ������   
      iArray[9][11]="0|^0|�� ^1|��";    //����ѡ��
      
      iArray[10]=new Array();
      iArray[10][0]="������";    //����
      iArray[10][1]="0px";            		//�п�
      iArray[10][2]=200;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 


      GrpGrid = new MulLineEnter( "fm" , "GrpGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      GrpGrid.mulLineCount =0;   
      GrpGrid.displayTitle = 1;
      GrpGrid.locked = 1;
      GrpGrid.canSel = 1;
      GrpGrid.canChk = 0;
      GrpGrid.hiddenPlus = 1;
      GrpGrid.hiddenSubtraction = 1;        
      GrpGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
