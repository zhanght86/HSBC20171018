package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.utility.*;


/**
 * <p>Title: lis</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: sinosoft</p>
 * @author tm
 * @version 1.0
 */

public class PubCheckLock {
private static Logger logger = Logger.getLogger(PubCheckLock.class);
  public PubCheckLock() {
  }
  public static boolean CheckInsert(String tNoType,String tNoLimit)
  {
    String currentDate=PubFun.getCurrentDate();
    String currentTime=PubFun.getCurrentTime();
    LockTableDB tLockTableDB = new LockTableDB();
    tLockTableDB.setNoType(tNoType);
    tLockTableDB.setNoLimit(tNoLimit);
    tLockTableDB.setMakeDate(currentDate);
    tLockTableDB.setMakeTime(currentTime);
    if(!tLockTableDB.insert())
    {
      logger.debug("插入LockTable表出错！");
      return false;
    }
    return true;
  }
  public static boolean CheckDelete(String tNoType,String tNoLimit)
  {
    LockTableDB tLockTableDB = new LockTableDB();
    tLockTableDB.setNoType(tNoType);
    tLockTableDB.setNoLimit(tNoLimit);
    if(!tLockTableDB.delete())
    {
      return false;
    }
    return true;
  }
  public static void main(String[] args) {
    PubCheckLock pubCheckLock1 = new PubCheckLock();
  }
}
