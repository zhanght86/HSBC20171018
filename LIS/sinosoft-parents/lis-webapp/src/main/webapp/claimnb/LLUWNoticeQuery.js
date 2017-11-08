//程序名称：LLUWNoticeQuery.js
//程序功能：核保通知书查询
//创建日期：2005-12-05 
//创建人  ：wanzh


var turnPage = new turnPageClass();
var mySql = new SqlClass();
function NoticeQuery()
{
	var tContNo = ContNo ;
	var strSQL = "";
//	strSQL = " (select a.prtseq,a.otherno,a.code,b.codename ,a.agentcode,a.managecom "
//           + " from loprtmanager a,ldcode b where a.code = b.code and b.codetype = "
//           + " 'lluwnotice' and b.code like 'LP%%'  and a.otherno ='"+tContNo+"') union ("
//           + " select a.prtseq,a.otherno,a.code ,(case a.code when 'LP03' "
//           + " then '核保体检通知书' when 'LP04' then '核保身调通知书' when 'LP81' "
//           + " then '核保索要问卷通知书' end),a.agentcode,a.managecom from loprtmanager a"
//           + " where a.otherno ='"+tContNo+"' and a.code in ('LP03','LP04','LP81'))";
//Modify by zhaorx 2006-08-22,2006-09-01
	/*strSQL = " select a.prtseq,a.otherno,a.code,b.codename ,a.agentcode,a.managecom "
           + " from loprtmanager a,ldcode b where a.code = b.code and b.codetype ='lluwnotice' "
           + " and b.code like 'LP%%'  and a.otherno ='"+tContNo+"' "
           + " and (a.oldprtseq is null or a.prtseq = a.oldprtseq) ";*/
     mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWNoticeQueryInputSql");
	mySql.setSqlId("LLUWNoticeQuerySql1");
	mySql.addSubPara(tContNo);       
    turnPage.queryModal(mySql.getString(),NoticeGrid);

}