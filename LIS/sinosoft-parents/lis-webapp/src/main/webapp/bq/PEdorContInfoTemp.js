/*******************************************************************************
 * <p>Title: Lis 6.5</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : pst <pengst@sinosoft.com.cn>
 * @version  : 1.00, 
 * @date     : 2008-12-3
 * @direction: �ͻ���Ҫ���ϱ��
 ******************************************************************************/

var turnPage = new turnPageClass();
var turnPageContGrid = new turnPageClass();
var turnPageSelfGrid = new turnPageClass();
var mySql=new SqlClass();

/*********************************************************************
 *  ��ѯ�ҵ��������
 *  ����: ��ѯ�ҵ��������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function easyQueryClickCont()
{
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorContInfoTemp");
    mySql.setSqlId("PEdorContInfoTempSql1");
    mySql.addSubPara(curDay);
    mySql.addSubPara(manageCom);
    mySql.addSubPara(fm.AppntNo.value);
    mySql.addSubPara(fm.ContNo.value);
    mySql.addSubPara(fm.InsuredNo.value);
    
	  turnPageContGrid.queryModal(mySql.getString(), ContGrid);
	  HighlightAllRow();
	  return true;
}

/*********************************************************************
 *  ��ת�������ҵ����ҳ��
 *  ����: ��ת�������ҵ����ҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 function goToBusiDeal()
{
	
	var selno = PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ����ı�����");
	      return;
	}
	var tEdorAcceptNo = PrivateWorkPoolGrid.getRowColData(selno, 1);
	var tMissionID = PrivateWorkPoolGrid.getRowColData(selno, 19);
	var tSubMissionID = PrivateWorkPoolGrid.getRowColData(selno, 20);
	var tActivityID = PrivateWorkPoolGrid.getRowColData(selno, 22);
	var tDefaultOperator = PrivateWorkPoolGrid.getRowColData(selno, 15);
	//�������������
	var tLoadFlag = "CM";

	var varSrc="&ActivityID=" + tActivityID + "&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
	var newWindow = OpenWindowNew("../bq/PEdorAppInputFrame.jsp?Interface=../bq/PEdorAppInput.jsp" + varSrc,"��ȫ��ɨ����������","left");

} 
 /*
function goToBusiDeal()
{
	
	var selno = SelfGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ����ı�����");
	      return;
	}
	var tEdorAcceptNo = SelfGrid.getRowColData(selno, 1);
	var tMissionID = SelfGrid.getRowColData(selno, 6);
	var tSubMissionID = SelfGrid.getRowColData(selno, 7);
	var tActivityID = SelfGrid.getRowColData(selno, 8);
	var tDefaultOperator = SelfGrid.getRowColData(selno, 9);
	//�������������
	var tLoadFlag = "CM";

	var varSrc="&ActivityID=" + tActivityID + "&EdorAcceptNo=" + tEdorAcceptNo + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&LoadFlag=" + tLoadFlag;
	var newWindow = OpenWindowNew("../bq/PEdorAppInputFrame.jsp?Interface=../bq/PEdorAppInput.jsp" + varSrc,"��ȫ��ɨ����������","left");

} 
*/
function HighlightAllRow(){
		for(var i=0; i<ContGrid.mulLineCount; i++){
  	var days = ContGrid.getRowColData(i,9);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		ContGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
/*
function HighlightSelfRow(){
		for(var i=0; i<SelfGrid.mulLineCount; i++){
  	var days = SelfGrid.getRowColData(i,12);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		SelfGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
*/
function HighlightSelfRow(){
		for(var i=0; i<PrivateWorkPoolGrid.mulLineCount; i++){
  	var days = PrivateWorkPoolGrid.getRowColData(i,12);  	
  	if(days != null && days != ""){	
    	var ret = Arithmetic(days,'-','5','2');
    	if(ret>=0)
    	{
    		PrivateWorkPoolGrid.setRowClass(i,'warn');
    	}
    
  	}
  }
}
function applyMission()
{
		//alert("111111");
	  var selno = ContGrid.getSelNo()-1;
	  if (selno <0)
	  {
	      alert("��ѡ��Ҫ����ı�����");
	      return;
	  }
	  var tCow=ContGrid.getSelNo()
	  tCow=tCow-1;  	
    var tContNo=ContGrid.getRowColData(tCow,2);		
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorContInfoTemp");
    mySql.setSqlId("PEdorContInfoTempSql3");
    mySql.addSubPara(tContNo);    
    //alert("222222");
			var brr = easyExecSql(mySql.getString());
			if ( brr )
			{
				alert("�Ѿ����뱣���,���ȴ���δ�������");
				return false;
			}

	
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();  fm.ICFlag.value="IC";
  //alert("333333");
	fm.action = "../bq/WFEdorNoscanAppSave.jsp";
	//alert("444444");
	document.getElementById("fm").submit(); //�ύ
	//alert("555555");
}

function easyQueryClickSelf()
{
	
		var strSQL = "";
 
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorContInfoTemp");
    mySql.setSqlId("PEdorContInfoTempSql2");
    mySql.addSubPara(curDay);
    mySql.addSubPara(operator);
    mySql.addSubPara(fm.ContNoSelf.value);
    mySql.addSubPara(fm.EdorAcceptNoSelf.value);
    
	  turnPageSelfGrid.queryModal(mySql.getString(), SelfGrid);
	
	  HighlightSelfRow();
	  return true;
}

function afterSubmit( FlagStr, content )
{
	 
	if (FlagStr == "Succ" )
	{	  		  
		
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorContInfoTemp");
    mySql.setSqlId("PEdorContInfoTempSql4");
    mySql.addSubPara(fm.EdorAcceptNo.value);
 
			var brr = easyExecSql(mySql.getString());

      var tContNo="";
			if ( brr )
			{
				//alert("�Ѿ����뱣���");
				brr[0][0]==null||brr[0][0]=='null'?'0':fm.MissionID.value    = brr[0][0];
				brr[0][1]==null||brr[0][1]=='null'?'0':fm.SubMissionID.value = brr[0][1];
				brr[0][2]==null||brr[0][2]=='null'?'0':tContNo = brr[0][2];				
			}
			else
			{
				alert("�����������ѯʧ�ܣ�");
				return;
			}
			//���ڴ�ҳ����ύ�Ķ����ص�afterSubmit��Ƕ���Succ����һ������������ţ��ڶ�����Ϊ�˽������ź�����Ű� 
			//Ϊ�˱����ظ��ύ����������
			if(tContNo=="" || tContNo==null)
			{
	       fm.OtherNo.value =fm.ContNo.value ;
         fm.OtherNoType.value ='3';
         fm.fmtransact.value = "INSERT||EDORAPP";
         fm.LoadFlag.value="edorApp";
     	   fm.action = "../bq/PEdorAppSave.jsp";
         document.getElementById("fm").submit(); 
      }
      else
      {    		
       	 var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		     //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");    		
			 var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
      }          
	 }
   else if(FlagStr == "Fail" )
   {
		  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
   }
    showInfo.close();
   // easyQueryClickSelf();
   jQuery("#privateSearch").click();
}
function displayInfoSelfGrid()
{
	  var tCow=SelfGrid.getSelNo()
	  tCow=tCow-1;  	
    fm.ContNo.value =SelfGrid.getRowColData(tCow,2);	
}

function displayInfo()
{
	  var tCow=ContGrid.getSelNo()
	  tCow=tCow-1;  	
    fm.ContNo.value =ContGrid.getRowColData(tCow,2);	
    fm.AppntNo.value =ContGrid.getRowColData(tCow,3);	
    fm.InsuredNo.value =ContGrid.getRowColData(tCow,4);	
    fm.EdorAcceptNo.value =ContGrid.getRowColData(tCow,1);	
    
		var strSQL;
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorContInfoTemp");
    mySql.setSqlId("PEdorContInfoTempSql5");
    mySql.addSubPara(fm.EdorAcceptNo.value);		
 
			var brr = easyExecSql(mySql.getString());

			if ( brr )
			{
				//alert("�Ѿ����뱣���");
				brr[0][0]==null||brr[0][0]=='null'?'0':fm.EdorAppName.value  = brr[0][0];
				brr[0][1]==null||brr[0][1]=='null'?'0':fm.AppType.value  = brr[0][1];
			}
			
			 HighlightAllRow();
	}
	//�жϿͻ��㱣ȫ����ʧ���Ƿ���Ҫ��ӡ��ȫҵ�������Ѻ�(�ͻ��ŵ��´��ڱ���������������ʧ�ܵ�ʱ���ж�)
function CusPrintcheck(tModuleName)
{
  if(document.all('OtherNoType').value=='1')
  {
    if(tModuleName=='ContHangUpBL')
    {
       CusBQPrintFlag="1";
    }
  }
  return true;
}