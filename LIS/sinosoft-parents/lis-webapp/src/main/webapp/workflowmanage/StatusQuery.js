var turnPage = new turnPageClass();
var BusiNo="";
var sqlresourcename = "workflow.StatusQuerySql";


//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}


// 查询按钮
function easyQueryClick()
{
	
  if(!verifyInput2()) 
    return false;
	 /* 
    var strSql="";
		if(document.all('BusiType').value=="01")
		{
			strSql = "select (select codename from ldcode where codetype='busitype' and code='"+document.all('BusiType').value+"') ,ProposalGrpContNo,'',ManageCom from LCGrpCont where 1=1 " 
				       + " and appflag='0' " 
               + getWherePart( 'ProposalGrpContNo','NO','like' );
		}	
		if(document.all('BusiType').value=="02")
		{
			strSql = "select (select codename from ldcode where codetype='busitype' and code='"+document.all('BusiType').value+"') ,EdorNo,'', ManageCom from lpgrpedormain where 1=1 "
	             + getWherePart( 'EdorNo','NO','like' );

		}	
		if(document.all('BusiType').value=="03")
		{
			strSql = "select (select codename from ldcode where codetype='busitype' and code='"+document.all('BusiType').value+"') ,clmno,'', ManageCom from llclaim where 1=1 "
	            + getWherePart( 'ClmNo','NO','like' );

		}
		*/	
	  var strSQL = "";
	  var mySql=new SqlClass();
	  mySql.setResourceName(sqlresourcename);
	  mySql.setSqlId("StatusQuerySql1");
	  mySql.addSubPara(fm.NO.value);
	 // mySql.addSubPara(fm.BusiType.value);
	 // mySql.addSubPara(fm.ContNo.value);
	 // mySql.addSubPara(fm.ComCode.value);
	 // mySql.addSubPara(fm.StartDate.value);
	 // mySql.addSubPara(fm.EndDate.value);
	  
	  
 //   mySql.setJspName("../../workflowmanage/StatusQuerySql.jsp");
 //   mySql.setSqlId("StatusQuerySql1");
//    mySql.addPara("BusiType",fm.BusiType.value);
//    mySql.addPara("NO",fm.NO.value);		
//    mySql.addPara("ContNo",fm.ContNo.value);	
//    mySql.addPara("StartDate",fm.StartDate.value);	
//    mySql.addPara("EndDate",fm.EndDate.value);
//    mySql.addPara("ComCode",fm.ComCode.value);
    
    initNoGrid();
	  turnPage.queryModal(mySql.getString(), NoGrid);	
}

function GetStatus()
{
	
	var tSel = NoGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		 alert( "请先选择一条记录" );
		 return;
	}
		
  try
  {
         var ProcessId=NoGrid.getRowColData(tSel-1,3); 
         var MainMissionId=NoGrid.getRowColData(tSel-1,2);
         window.open("../workflow/workflow.jsp?action=status&flowId="+ProcessId+"&mainMissionId="+MainMissionId);
         //iframeView.getFlow(ProcessId);
         //iframeView.showStatus(MainMissionId,ProcessId);   
	}
	catch(ex)
	{
				alert(ex);
	}   
}
