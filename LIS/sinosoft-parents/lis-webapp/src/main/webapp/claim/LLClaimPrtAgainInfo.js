var showInfo;
var turnPage = new turnPageClass();
var mySql = new SqlClass();

//返回
function returnParent()
{
	top.close();
}


//初始化查询
function querySelfGrid()
{
	initLLClaimPrtAgainInfoGrid();
//	var strSQL =" Select t.prtseq,t.otherno,t.code,a.prtname from loprtmanager t,llparaprint a "
//             +" where t.code=a.prtcode "
//             +" and t.patchflag='1' "
//             +" and t.otherno='"+ document.all('ClmNo').value +"' "
	
      mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimPrtAgainInfoInputSql");
		mySql.setSqlId("LLClaimPrtAgainInfoSql1");
		mySql.addSubPara(document.all('ClmNo').value ); 
		
	 turnPage.queryModal(mySql.getString(),LLClaimPrtAgainInfoGrid);
}

//选中某条记录,则出现单证补打信息
function LLClaimPrtAgainInfoGridClick()
{
	var i = LLClaimPrtAgainInfoGrid.getSelNo();
  if (i != '0')
  {
    i = i - 1;
    var tPrtSeq = LLClaimPrtAgainInfoGrid.getRowColData(i,1);
    /*var strSQL =" select t.remark from loprtmanager t "
               +" where t.prtseq='" + tPrtSeq + "'"*/
    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimPrtAgainInfoInputSql");
		mySql.setSqlId("LLClaimPrtAgainInfoSql2");
		mySql.addSubPara(tPrtSeq); 
    var arr = easyExecSql( mySql.getString() );
    fm.PrAgInfo.value = arr[0][0];
  }

}