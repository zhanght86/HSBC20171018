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


function initForm(tContNo,tMissionID,tSubMission,tPrtNo,tInsuredNo)
{
  var str = "";
  try
  {
  	//alert("tInsuredNo:"+tInsuredNo);
    initInpBox();
    initUWSpecGrid();
    initPolAddGrid();
    //alert('1');
    initHide(tContNo,tMissionID,tSubMission,tPrtNo,tInsuredNo);
    queryInsuredInfo(tContNo,tInsuredNo);    
    //initCancleGiven(tContNo,tInsuredNo); //��ʼ��ȡ����ǰ������Լ��ť
    //alert('2');
    if(tQueryFlag=="1"){
      fm.button1.style.display="none";
      fm.button3.style.display="none";
      fm.button4.style.display="none";
      
	  }
	  
   // initpolno(tContNo);
    //alert('3');
    QueryPolSpecGrid(tContNo,tInsuredNo);
    queryRiskAddFee(tContNo,tInsuredNo);
    initOldRislPlanGrid("");
    queryOldRiskPlan();
    //alert('4');
    //alert();
    //initUWSpecContGrid();
    //QueryPolSpecCont(tContNo);
  }
  catch(re)
  {
    alert("UWSubInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initUWSpecGrid()
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
      iArray[1][0]="��Լ����";         		//����
      iArray[1][1]="430px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��ˮ��";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="״̬����";         		//����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="״̬";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="��ͬ��";         		//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
      iArray[6]=new Array();
      iArray[6][0]="���к�";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

			iArray[7]=new Array();
      iArray[7][0]="�ͻ���";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

			iArray[8]=new Array();
      iArray[8][0]="��Լ����";         		//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

			iArray[9]=new Array();
      iArray[9][0]="��Լ����";         		//����
      iArray[9][1]="0px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="��Լԭ��";         		//����
      iArray[10][1]="0px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="¼��ʱ��";         		//����
      iArray[11][1]="80px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[12]=new Array();
      iArray[12][0]="¼��Ա";         		//����
      iArray[12][1]="80px";            		//�п�
      iArray[12][2]=100;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[13]=new Array();
      iArray[13][0]="�·�״̬��־";         		//����
      iArray[13][1]="0px";            		//�п�
      iArray[13][2]=100;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[14]=new Array();
      iArray[14][0]="�·�״̬";         		//����
      iArray[14][1]="80px";            		//�п�
      iArray[14][2]=100;            			//�����ֵ
      iArray[14][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      UWSpecGrid = new MulLineEnter( "fm" , "UWSpecGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      UWSpecGrid.mulLineCount = 1;   
      UWSpecGrid.displayTitle = 1;
      //PolAddGrid.locked = 1;
      UWSpecGrid.canSel = 1;
      UWSpecGrid.hiddenPlus = 1;
      UWSpecGrid.hiddenSubtraction = 1;
      UWSpecGrid.loadMulLine(iArray);       
      UWSpecGrid.selBoxEventFuncName = "getSpecGridCho2";
     
      //��Щ����������loadMulLine����
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}


function initHide(tContNo,tMissionID,tSubMission,tPrtNo,tInsuredNo)
{
	document.all('ContNo').value=tContNo;
	document.all('MissionID').value=tMissionID;
	document.all('SubMissionID').value=tSubMission;
	document.all('PrtNo').value = tPrtNo ;
	document.all('InsuredNo').value = tInsuredNo ;
}

// ������Ϣ�б�ĳ�ʼ��
function initPolAddGrid()
  {
  var str = initlist(mContNo,mInsuredNo);
 // alert("str:"+str);
  var iArray = new Array();

      try
      {

      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			  //�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
//���ֱ���,��������,�ӷ�����,�ӷ����,�ӷѷ�ʽ,����,�ӷѽ��,�ӷ�ԭ��,����,ֹ��,�·�״̬,������,���ձ�����
   		iArray[1]=new Array();
      iArray[1][0]="���ֱ���";    	        //����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=1;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
      iArray[1][10] = "RiskCode";
      iArray[1][11] = str;
      iArray[1][12] = "1|2|3|9|10|12|13";
      iArray[1][13] = "0|1|2|3|4|5|6";
      iArray[1][19] = 1;
      
			iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=100;    //�����ֵ
      iArray[2][3]=2;         			
      iArray[2][10] = "RiskCode";
      iArray[2][11] = str;
      iArray[2][12] = "1|2|3|9|10|12|13";
      iArray[2][13] = "0|1|2|3|4|5|6";
      iArray[2][19] = 1;


      iArray[3]=new Array();
      iArray[3][0]="�ӷ����α���";    	        //����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

		  iArray[4]=new Array();
      iArray[4][0]="�ӷ����";    	        //����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=2;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
      iArray[4][10] = "PlanPay";
      iArray[4][11] = "0|^01|�����ӷ�||^02|ְҵ�ӷ�|03|^03|��ס�ؼӷ�|03|^04|���üӷ�|03|";
      iArray[4][12] = "4|5|6";
      iArray[4][13] = "0|2|3";
      iArray[4][19] = 1;


		  iArray[5]=new Array();
      iArray[5][0]="�ӷѷ�ʽ";    	        //����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=2;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
      iArray[5][10] = "AddFeeMethod";
      iArray[5][11] = "0|^01|��EMֵ|^02|�����ѱ���|^03|������|";
      iArray[5][12] = "5|6";
      iArray[5][13] = "0|2";
      iArray[5][19] = 1;

/*
			iArray[5]=new Array();
      iArray[5][0]="�ӷѷ�ʽ";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=2;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
      iArray[5][4] = "addfeetypemethod";
      iArray[5][5] = "5";
      iArray[5][6] = "0";
*/      
			iArray[6]=new Array();
      iArray[6][0]="����/����";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

			iArray[7]=new Array();
      iArray[7][0]="�ӷѽ��";         		//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
			iArray[7][7]="AutoCalAddFee";

      iArray[8]=new Array();
      iArray[8][0]="�ӷ�ԭ��";    	        //����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
     

      iArray[9]=new Array();
      iArray[9][0]="�ӷ�����";         		//����
      iArray[9][1]="60px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

      iArray[10]=new Array();
      iArray[10][0]="�ӷ�ֹ��";         		//����
      iArray[10][1]="60px";            		//�п�
      iArray[10][2]=60;            			//�����ֵ
      iArray[10][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

      iArray[11]=new Array();
      iArray[11][0]="�·�״̬";         		//����
      iArray[11][1]="0px";            		//�п�
      iArray[11][2]=60;            			//�����ֵ
      iArray[11][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

      iArray[12]=new Array();
      iArray[12][0]="���ֺ�";         		//����
      iArray[12][1]="0px";            		//�п�
      iArray[12][2]=60;            			//�����ֵ
      iArray[12][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

      iArray[13]=new Array();
      iArray[13][0]="�������ֺ�";         		//����
      iArray[13][1]="0px";            		//�п�
      iArray[13][2]=60;            			//�����ֵ
      iArray[13][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

      iArray[14]=new Array();
      iArray[14][0]="�ɷѼƻ�����";         		//����
      iArray[14][1]="0px";            		//�п�
      iArray[14][2]=60;            			//�����ֵ
      iArray[14][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��


      PolAddGrid = new MulLineEnter( "fm" , "PolAddGrid" );
      //��Щ���Ա�����loadMulLineǰ
      PolAddGrid.mulLineCount = 1;
      PolAddGrid.canSel = 1;
      PolAddGrid.displayTitle = 1;
      PolAddGrid.loadMulLine(iArray);
       //��Щ����������loadMulLine����
      //SpecGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}


function initOldRislPlanGrid(str)
  {
  var iArray = new Array();

      try
      {

      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			  //�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

   		iArray[1]=new Array();
      iArray[1][0]="���ֱ���";         		//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

			iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��


      iArray[3]=new Array();
      iArray[3][0]="����";    	        //����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��


      iArray[4]=new Array();
      iArray[4][0]="����";    	        //����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��


      iArray[5]=new Array();
      iArray[5][0]="����";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

      iArray[6]=new Array();
      iArray[6][0]="�����ڼ�";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

      iArray[7]=new Array();
      iArray[7][0]="�ɷ��ڼ�";         		//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

      iArray[8]=new Array();
      iArray[8][0]="�ɷ�Ƶ��";         		//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

    	iArray[9]=new Array();
      iArray[9][0]="�˱�����";         		//����
      iArray[9][1]="60px";            		//�п�
      iArray[9][2]=200;            			//�����ֵ
      iArray[9][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
	  	iArray[9][4]="uwstate";              	        //�Ƿ����ô���:null||""Ϊ������
    	iArray[9][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��

   		iArray[10]=new Array();
      iArray[10][0]="���ֺ�";         		//����
      iArray[10][1]="0px";            		//�п�
      iArray[10][2]=200;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

   		iArray[11]=new Array();
      iArray[11][0]="�������ֺ�";         		//����
      iArray[11][1]="0px";            		//�п�
      iArray[11][2]=200;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      OldRislPlanGrid = new MulLineEnter( "fm" , "OldRislPlanGrid" );
      //��Щ���Ա�����loadMulLineǰ
      OldRislPlanGrid.mulLineCount = 1;
      OldRislPlanGrid.canSel = 1;
      OldRislPlanGrid.displayTitle = 1;
      OldRislPlanGrid.hiddenPlus = 1;
      OldRislPlanGrid.hiddenSubtraction = 1;
     
      OldRislPlanGrid.loadMulLine(iArray);
       //��Щ����������loadMulLine����
      //SpecGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>


