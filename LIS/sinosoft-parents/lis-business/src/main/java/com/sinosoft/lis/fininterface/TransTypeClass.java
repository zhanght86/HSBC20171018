package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.utility.VData;
import com.sinosoft.utility.CErrors;

/**
 * <p>Title: DistillType</p>
 *
 * <p>Description: 处理数据提取特殊处理类接口</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface TransTypeClass
{
   /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    public boolean DealInfo(VData cInputData);
    public boolean CheckInfo(VData cInputData);
    public String getClumnValue();
}
