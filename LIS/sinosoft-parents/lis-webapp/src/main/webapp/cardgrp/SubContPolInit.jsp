<%
//�������ƣ�SubContPolInput.jsp
//�����ܣ�
//�������ڣ�2002-08-15 11:48:43
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	String tGrpContNo = "";
	String tPrtNo = "";
	tGrpContNo = request.getParameter("GrpContNo");
	tPrtNo = request.getParameter("PrtNo");
	loggerDebug("SubContPolInit","grp:" + tGrpContNo);
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
}

function initSelBox()
{  
}                                        

function initForm(tGrpContNo, tPrtNo)
{
  try
  {
    initInpBox();
    initSelBox();    
	getGrpCont(tGrpContNo, tPrtNo);
	initGeneralGrid();
	queryGeneralInfo();
	fm.ExecuteCom.value = tManageCom;
	//fm.all("addbutton").style.display = "none";
	//fm.all("modibutton").style.display = "none";
	//fm.all("delbutton").style.display = "none";
  if(LoadFlag=="4" || LoadFlag=="16")
	  {
	    fm.GrpContNo.value=GrpContNo;
	    //���ز�����ť
	    divSeperateSave.style.display="none";
	  }
	  if(LoadFlag=="13"){
	    fm.GrpContNo.value=GrpContNo;
	  }
  }
  catch(re)
  {
    alert("SubContPolInputInit.jsp-->InitForm1�����з����쳣:��ʼ���������!");
  }
}

function initGeneralGrid()
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
      iArray[1][0]="�������";          		//����
      iArray[1][1]="60px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=2;                      //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      iArray[1][4]="comcode";             //�Ƿ����ô���:null||""Ϊ������
      iArray[1][5]="1";              	   //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[1][6]="0";              	   //��������з������ô����еڼ�λֵ

      iArray[2]=new Array();
      iArray[2][0]="�������";         			//����
      iArray[2][1]="60px";            			//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][4]="comcode";             //�Ƿ����ô���:null||""Ϊ������
      iArray[2][5]="1";              	   //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[2][6]="0";              	   //��������з������ô����еڼ�λֵ

      iArray[3]=new Array();
      iArray[3][0]="��λ����";      	   		//����
      iArray[3][1]="100px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=1; 

      iArray[4]=new Array();
      iArray[4][0]="��ַ";      	   		//����
      iArray[4][1]="160px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="����Ա";      	   		//����
      iArray[5][1]="40px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0; 

      iArray[6]=new Array();
      iArray[6][0]="�ͻ�����";      	   		//����
      iArray[6][1]="0px";            			//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=0; 

      GeneralGrid = new MulLineEnter( "fm" , "GeneralGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      GeneralGrid.mulLineCount = 0;   
      GeneralGrid.displayTitle = 1;
      GeneralGrid.canSel =1;
      GeneralGrid. hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      GeneralGrid. hiddenSubtraction=1;
	  GeneralGrid. selBoxEventFuncName = "onGeneralSelected";

      GeneralGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}


function getGrpCont(tGrpContNo, tPrtNo){

//	fm.all( 'PrtNo' ).value = "200411240001";
//	fm.all( 'GrpContNo' ).value = "86110020040990000041";
//	fm.all( 'ProposalGrpContNo' ).value = "86110020040990000041";
//    try { fm.all( 'PrtNo' ).value = mSwitch.getVar( "PrtNo" ); } catch(ex) { };
//    try { fm.all( 'GrpContNo' ).value = mSwitch.getVar( "GrpContNo" ); } catch(ex) { };
//    try { fm.all( 'ProposalGrpContNo' ).value = mSwitch.getVar( "ProposalGrpContNo" ); } catch(ex) { };
    try { fm.all( 'GrpContNo' ).value = tGrpContNo; } catch(ex) { };
    try { fm.all( 'PrtNo' ).value = tPrtNo; } catch(ex) { };

}
</script>
