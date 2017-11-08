package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.utility.SQLwithBindVariables;

/**
 * <p>Title: 通用函数</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author WHN
 * @version 1.0
 */
public class UWPubFun
{
private static Logger logger = Logger.getLogger(UWPubFun.class);

    public UWPubFun()
    {
    }

    public static void main(String[] args)
    {
        PubFun pubfun = new PubFun();
    }

    /**
     * 得到规定格式日期 author: WHN
     * @return 日期格式为"yyyy-MM-dd"
     */
    public static String getFixedDate(Date today)
    {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        String tString = df.format(today);
        return tString;
    }

    /**
     * 复核修改问题件校验
     * input: ProposalNo
     * return: true or false
     */
    public static boolean checkQues(String tProposalNo)
    {
        LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
        LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();

        String tsql = "select * from lcissuepol where proposalno = '" +
                      "?proposalno?" +
                "' and replyresult is null and operatepos in ('1','5') and backobjtype = '1'";
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        sqlbv.sql(tsql);
        sqlbv.put("proposalno", tProposalNo);
        tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv);

        if (tLCIssuePolSet.size() > 0)
        {
            return false;
        }

        return true;
    }
}
