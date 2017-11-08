package com.sinosoft.lis.wfpic;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LWActivitySchema;
import com.sinosoft.lis.workflowmanage.ServiceFileBL;
class Node {
	private boolean isWasVisited;
	private int dept=-1;
	//private LWActivitySchema tLWActivitySchema;
	private int degree=0;
	private String flowType="";
	private String nodeName="";
	private String nodeType="";
	private String width="";
	private String height="";
	private String x="";
	private String y="";
	private String timeType="";
	private String time="";
	private String nodeID="";
	private static Logger logger = Logger.getLogger(Node.class);

    public  Node()
    {
    	
    }
//    public void setLWActivitySchema(LWActivitySchema tLWActivitySchema)
//    {
//    	this.tLWActivitySchema=tLWActivitySchema;
//    }
//    public LWActivitySchema getLWActivitySchema()
//    {
//    	return tLWActivitySchema;
//    }
    public void setWasVisited(boolean isWasVisited)
    {
    	this.isWasVisited=isWasVisited;
    }
    public boolean isWasVisited()
    {
    	return isWasVisited;
    }
    public void setDept(int dept)
    {
    	this.dept=dept;
    }
    public int getDept()
    {
    	return dept;
    }
    public int getDegree()
    {
    	return degree;
    } 
    public void setDegree(int degree)
    {
    	this.degree=degree;
    }      
    public void setWidth(String width)
    {
    	this.width=width;
    }    
    public String getWidth()
    {
    	return width;
    }     
    public void setHeight(String height)
    {
    	this.height=height;
    }  
    public String getHeight()
    {
    	return height;
    }     
    public void setX(String x)
    {
    	this.x=x;
    }  
    public String getX()
    {
    	return x;
    }     
    public void setY(String y)
    {
    	this.y=y;
    }  
    public String getY()
    {
    	return y;
    }     
    public void setFlowType(String flowType)
    {
    	this.flowType=flowType;
    }
    public String getFlowType()
    {
    	return flowType;
    }   
    public void setNodeName(String nodeName)
    {
    	this.nodeName=nodeName;
    }
    public String getNodeName()
    {
    	return nodeName;
    } 
    public void setNodeType(String nodeType)
    {
    	this.nodeType=nodeType;
    }
    public String getNodeType()
    {
    	return nodeType;
    } 
    public void setTimeType(String timeType)
    {   
    	if(timeType!=null&&timeType.equals(""))
    	{
    	   this.timeType=timeType;
    	}
    }
    public String getTimeType()
    {
    	return timeType;
    } 
    public void setTime(String time)
    {
    	if(time!=null&&time.equals(""))
    	{
    	    this.time=time;
    	}
    }
    public String getTime()
    {
    	return time;
    } 
    public void setNodeID(String nodeID)
    {
    	this.nodeID=nodeID;
    }
    public String getNodeID()
    {
    	return nodeID;
    } 
   
}     
class Edge {
	private static Logger logger = Logger.getLogger(Edge.class);
	private String flowType="";
	private String edgeName="";
	private String lineType="";
	private String from="";
	private String to="";
	private String conditon="";
	private String edgeID="";

    public  Edge()
    {
    	
    }
    public void setEdgeID(String edgeID)
    {
    	this.edgeID=edgeID;
    }
    public String getEdgeID()
    {
    	return edgeID;
    }     
    public void setEdgeName(String edgeName)
    {
    	this.edgeName=edgeName;
    }
    public String getEdgeName()
    {
    	return edgeName;
    }     
    public void setFlowType(String flowType)
    {
    	this.flowType=flowType;
    }
    public String getFlowType()
    {
    	return flowType;
    }    
    public void setLineType(String lineType)
    {
    	this.lineType=lineType;
    }
    public String getLineType()
    {
    	return lineType;
    }  
    public void setFrom(String from)
    {
    	this.from=from;
    }  
    public String getFrom()
    {
    	return from;
    }   
    public void setTo(String to)
    {
    	this.to=to;
    }  
    public String getTo()
    {
    	return to;
    }   
    public void setConditon(String conditon)
    {
    	this.conditon=conditon;
    }  
    public String getConditon()
    {
    	return conditon;
    }         
}
/** 
 * 栈，遵循先进后出的原则，用来保存元素 
 *  
 * @author nishiting 
 *  
 */  
 class Stack {  
	private static Logger logger = Logger.getLogger(Stack.class);
    private int[] st;  
    private int top;  
    private int count;  
  
    /** 
     * 构造一个栈 
     *  
     * @param size 
     *            栈的大小 
     */  
    public Stack(int size) {  
        st = new int[size];  
        top = -1;  
        count = 0;  
    }  
  
    /** 
     * 元素进栈 
     *  
     * @param j 
     *            要进栈的元素 
     */  
  
    public void push(int j) {  
        count++;  
        st[++top] = j;  
    }  
  
    /** 
     * 元素出栈 
     *  
     * @return 出栈的元素 
     */  
  
    public int pop() {  
  
        return st[top--];  
    }  
  
    /** 
     * 查询栈顶元素 
     *  
     * @return 栈顶元素 
     */  
  
    public int peek() {  
        return st[top];  
    }  
  
    /** 
     * 查询栈是否为空 
     *  
     * @return 栈是否为空 
     */  
  
    public boolean isEmpty() {  
        count--;  
        return (top == -1);  
    }  
  
    /** 
     * 查看栈里的所有元素 
     */  
  
    public void list() {  
  
        for (int i = 0; i < count; i++) {  
  
            logger.debug(st[i] + "   ");  
  
        }  
          
    }  
  
    /** 
     * 得到栈里一共有多少元素 
     *  
     * @return 栈里的元素个数 
     */  
    public int getCount() {  
        return count;  
    }  
  
    /** 
     * 查看栈里是否包含某个元素 
     *  
     * @param i 
     *            要查询的元素 
     * @return 是否包含了要查询的元素 
     */  
  
    public boolean isContains(int i) {  
        for (int k = 0; k < st.length; k++) {  
  
            logger.debug("开始比较" + i + "此时的result:");  
            list();  
              
            if (st[k] == i) {  
                return true;  
            }  
        }  
        return false;  
    }  
      
    /** 
     * 得到栈里的元素集 
     * @return 栈里的元素集合 
     */  
    public int[] getSt(){  
        return st;  
    }  
}
  class Queue {  
    	   
        private int[] values;  
        private int begin = -1;  
        private int end = -1;  
          
        Queue(int size){  
            values = new int[size];  
        }  
          
        void push(int value){  
            values[++begin] = value;  
        }  
          
        int pop(){  
            return values[++end];  
        }  
          
        boolean isEmpty(){  
            return begin == end;  
        }  
    }    
      
 
public class Graph {  
private static Logger logger = Logger.getLogger(Graph.class);
    private final int MAX_VERT=500;  
    private final int MAX_DEGREE=3;
    private Node nodelist[];  
    private Edge edgelist[];  
    private int adjMat[][];  
    private int nverts;  
    private int everts; 
//    private int degree1;
//    private int degree2;
//    private int degree3;
    private Stack theStack;  
    private Queue theQuene;  
//    private int degreeList[][];
 
    public Graph(){  
        //顶点数组  
        nodelist=new Node[MAX_VERT];  
        //邻接矩阵  
        adjMat = new int[MAX_VERT][MAX_VERT];  
        //等级链表
//        degreeList = new int[MAX_DEGREE][MAX_VERT];
        //边
        edgelist=new Edge[MAX_VERT*2];  
        
        nverts=0;  
        everts=0;  
        for(int i=0;i<MAX_VERT;i++){  
            for(int j=0;j<MAX_VERT;j++){  
                adjMat[i][j]=0;  
            }  
        }  
//        degree1=0;
//        degree2=0;
//        degree3=0;
//        for(int i=0;i<MAX_DEGREE;i++){  
//            for(int j=0;j<MAX_VERT;j++){  
//            	degreeList[i][j]=-1;  
//            }  
//        }         
        
        theStack=new Stack(MAX_VERT);  
        theQuene=new Queue(MAX_VERT);  
    }  
    public void init()
    {
        for(int i=0;i<nverts;i++){  
        	nodelist[i].setDept(-1);
        	nodelist[i].setWasVisited(false);
        }  
//        degree1=0;
//        degree2=0;
//        degree3=0;
//        for(int i=0;i<MAX_DEGREE;i++){  
//            for(int j=0;j<MAX_VERT;j++){  
//            	degreeList[i][j]=-1;  
//            }  
//        }          	
	}
    /** 
     * 增加一定点 
     * @param node 
     */  
    public void addNode(Node node){  
        nodelist[nverts++]=node;  
    }  
    public void addEdge(Edge edge){  
    	edgelist[everts++]=edge; 
        adjMat[getGraphNodeID(edge.getFrom())][getGraphNodeID(edge.getTo())]=1;  
        //有向图  
        //adjMat[end][start]=1;  
    }        
//    /** 
//     * 增加一边 
//     * @param start 
//     * @param end 
//     */  
//    public void addEdge(int start,int end){  
//        adjMat[start][end]=1;  
//        //有向图  
//        //adjMat[end][start]=1;  
//    }  

//    public void addDegree(int degree,int node){  
//    	switch(degree)
//    	{
//    	  case 1:degreeList[degree-1][degree1++]=node;break;
//    	  case 2:degreeList[degree-1][degree2++]=node;break;
//    	  case 3:degreeList[degree-1][degree3++]=node;break;
//    	}
//    	
//    }     
    public int getGraphNodeID(String nodeID)
    {
    	for(int i=0;i<nverts;i++)
    	{
    		if(nodelist[i].getNodeID().equals(nodeID))
    		{
    			return i;
    		}
    	}
    	return -1;
    }
    public int getGraphEdgeID(String from,String to)
    {
    	for(int i=0;i<everts;i++)
    	{
    		if(edgelist[i].getFrom().equals(from)&&edgelist[i].getTo().equals(to))
    		{
    			return i;
    		}
    	}
    	return -1;
    }    
    public int getAdjUnVisited(int v){  
        for(int j=0;j<nverts;j++){  
            if(adjMat[v][j]==1&&nodelist[j].isWasVisited()==false){  
                return j;  
            }  
        }  
        return -1;  
    }  
 
    public int getAdj(int v){  
        for(int j=0;j<nverts;j++){  
            if(adjMat[v][j]==1){  
                return j;  
            }  
        }  
        return -1;  
    }      
    public int getNodeCount()
    {
    	return this.nverts;
    }
    public Node[] getAllNode()
    {
    	return this.nodelist;
    }
    public int getEdgeCount()
    {
    	return this.everts;
    }
    public Edge[] getAllEdge()
    {
    	return this.edgelist;
    }    
    /** 
     * 深度优先搜索算法 
     */  
    public void dfs(){  
    	int dept=0;
        nodelist[0].setWasVisited(true);  
        nodelist[0].setDept(dept);
       // displayNode(0);  
        theStack.push(0);  
        while(!theStack.isEmpty()){  
            int v= theStack.peek() ;  
            v=getAdjUnVisited(v);  
              
            if(v==-1){  
            	dept--;
                theStack.pop();  
            }else{  
            	dept++;
                nodelist[v].setWasVisited(true);  
                nodelist[v].setDept(dept);
                //displayNode(v);  
                theStack.push(v);  
            }  
        }  
        for(int j=0;j<nverts;j++){  
            nodelist[j].setWasVisited(false);  
        }  
    }  
      
    /** 
     * 广度优先搜索算法 
     */  
    public void bfs(){  
    	int dept=0;
        this.nodelist[0].setWasVisited(true); 
        this.nodelist[0].setDept(dept);
//        addDegree(this.nodelist[0].getDegree(),0);
       /// this.displayNode(0);  
        this.theQuene.push(0);  
        int v2;  
        while(!this.theQuene.isEmpty()){  
            int v1= this.theQuene.pop() ; 
            dept=this.nodelist[v1].getDept()+1;
            while((v2=this.getAdjUnVisited(v1))!=-1){  
                this.nodelist[v2].setWasVisited(true); 
                this.nodelist[v2].setDept(dept);
 //               addDegree(this.nodelist[v2].getDegree(),v2);
                ///isplayNode(v2);  
                this.theQuene.push(v2);  
            }  
        }  
        for(int j=0;j<nverts;j++){  
            nodelist[j].setWasVisited(false);  
        }         
    }  
//    public void mergedegree(int degree)
//    {
//    	switch(degree)
//    	{
//    	   case 1: 
//    		   for(int i=0;i<degree1;i++)
//    	       {
//    			   int v=degreeList[degree-1][i];
//    			   mergeNode(v);
//    	       }
//    		   break;
//    	   case 2: 
//    		   for(int i=0;i<degree2;i++)
//    	       {
//    			   int v=degreeList[degree-1][i];
//    			   mergeNode(v);
//    	       }
//    		   break;
//    	   case 3: 
//    		   for(int i=0;i<degree3;i++)
//    	       {
//    			   int v=degreeList[degree-1][i];
//    			   mergeNode(v);
//    	       }
//    		   break;
//    	}
//    }
    
    public void mergeNode(int v)
    {
		   for(int j=0;j<nverts;j++)
		   {
			   if(adjMat[j][v]==1)
			   {
				   for(int k=0;k<nverts;k++)
				   {
    				   if(adjMat[v][k]==1)
    				   {
    					   adjMat[j][k]=1;
    					   Edge tEdge=new Edge();
    					   tEdge.setEdgeID(nodelist[j].getNodeID()+"and"+nodelist[k].getNodeID());
    					   tEdge.setEdgeName(nodelist[j].getNodeName()+""+nodelist[k].getNodeName());
    					   tEdge.setFlowType("connect");
    					   tEdge.setFrom(nodelist[j].getNodeID());
    					   tEdge.setTo(nodelist[k].getNodeID());
    					   tEdge.setLineType("PolyLine");
    					   tEdge.setConditon("");
    					   this.edgelist[everts++]=tEdge;
    				   }
				   }
			   }
		   }
		   for(int j=0;j<nverts;j++)
		   {
			   if(adjMat[j][v]==1)
			   {
				   adjMat[j][v]=0;
				   int m=this.getGraphEdgeID(nodelist[j].getNodeID(), nodelist[v].getNodeID());
				   edgelist[m]=null;
				   for(int i=m;i<everts;i++)
				   {
					   edgelist[i] =edgelist[i+1];
				   }	  
				   everts--;
			   }
			   

			   if(adjMat[v][j]==1)
			   {
				   adjMat[v][j]=0;
				   int m=this.getGraphEdgeID(nodelist[v].getNodeID(), nodelist[j].getNodeID());
				   edgelist[m]=null;
				   for(int i=m;i<everts;i++)
				   {
					   edgelist[i] =edgelist[i+1];
				   }	  
				   everts--;
			   }			   
		   }
    }
    public void delNode(int v)
    {
    	
		   for(int j=0;j<nverts;j++)
		   {
			   if(adjMat[j][v]==1)
			   {
				   adjMat[j][v]=0;
				   int m=this.getGraphEdgeID(nodelist[j].getNodeID(), nodelist[v].getNodeID());
				   edgelist[m]=null;
				   for(int i=m;i<everts;i++)
				   {
					   edgelist[i] =edgelist[i+1];
				   }	  
				   everts--;
			   }
			   

			   if(adjMat[v][j]==1)
			   {
				   adjMat[v][j]=0;
				   int m=this.getGraphEdgeID(nodelist[v].getNodeID(), nodelist[j].getNodeID());
				   edgelist[m]=null;
				   for(int i=m;i<everts;i++)
				   {
					   edgelist[i] =edgelist[i+1];
				   }	  
				   everts--;
			   }			   
		   }    	
    	
    	
    	//上移
		   for(int i=v;i<nverts;i++)
		   {
			   for(int j=0;j<nverts;j++)
			   {
				   adjMat[i][j]=adjMat[i+1][j];
			   }
		   }
		   for(int i=0;i<nverts;i++)
		   {
			   adjMat[nverts-1][i]=0;
		   }	
         //左移
		   for(int i=v;i<nverts;i++)
		   {
			   for(int j=0;j<nverts;j++)
			   {
				   adjMat[j][i]=adjMat[j][i+1];
			   }
		   }
		   for(int i=0;i<nverts;i++)
		   {
			   adjMat[i][nverts-1]=0;
		   }		   
		   nodelist[v]=null;
		   for(int i=v;i<nverts;i++)
		   {
			   nodelist[i] =nodelist[i+1];
		   }	  
		   nverts--;
    }
              
    
//    /** 
//     * 广度优先搜索所有路径 
//     */  
//    public void bfspath(){ 
// 
//    	int dept=0;
//        this.nodelist[0].setWasVisited(true); 
//        this.nodelist[0].setDept(dept);
//        adddegree(this.nodelist[0].getdegree(),0);
//        //this.displayNode(0);  
//        this.theQuene.push(0);  
//        int v2;  
//        while(!this.theQuene.isEmpty()){  
//            int v1= this.theQuene.pop() ; 
//            dept=this.nodelist[v1].getDept()+1;
//            while((v2=this.getAdj(v1))!=-1){  
//                this.nodelist[v2].setWasVisited(true); 
//                this.nodelist[v2].setDept(dept);
//                adddegree(this.nodelist[v2].getdegree(),v2);
//                //displayNode(v2);  
//                this.theQuene.push(v2);  
//            }  
//        }  
//        for(int j=0;j<nverts;j++){  
//            nodelist[j].setWasVisited(false);  
//        }         
//    }        
 
 
   public void displayNode(int v){  
       logger.debug(nodelist[v].getNodeID()+":"+nodelist[v].getDept());  
   }  
   public void displayGraph(){  
	   logger.debug("节点深度");
	   for(int i=0;i<nverts;i++)
           logger.debug(nodelist[i].getNodeID()+":"+nodelist[i].getDept());	   
	   logger.debug("邻接矩阵");  
	   logger.debug("     ");
	   for(int i=0;i<nverts;i++)
	   {
		   logger.debug(nodelist[i].getNodeID()+":"+nodelist[i].getDept()+"  "); 
	   }
	   logger.debug("");
	   for(int i=0;i<nverts;i++)
	   {
           logger.debug(nodelist[i].getNodeID()+":"+nodelist[i].getDept()+"  ");
	       for(int j=0;j<nverts;j++)
	       {
		       logger.debug(adjMat[i][j]+"     ");  
	       }
	       logger.debug("");
	   }  
	   logger.debug("等级列表");    
	   
//	   for(int i=0;i<degree1;i++)
//	   {
//		   logger.debug(this.degreeList[0][i]);
//		   logger.debug("--->");
//	   }
//	   logger.debug(" ");    
//	   for(int i=0;i<degree2;i++)
//	   {
//		   logger.debug(this.degreeList[1][i]);
//		   logger.debug("--->");
//	   }
//	   logger.debug(" "); 
//	   for(int i=0;i<degree3;i++)
//	   {
//		   logger.debug(this.degreeList[2][i]);
//		   logger.debug("--->");
//	   }	  
	   logger.debug(" "); 
   }     
 
   
   
   public static void main(String[] args)
   {
       Graph g=new Graph();  
       LWActivitySchema A=new LWActivitySchema();
       A.setActivityID("A");
       Node nA=new Node();
       nA.setNodeID("A");
       nA.setDegree(2);
//       nA.setLWActivitySchema(A);
       g.addNode(nA);  
       LWActivitySchema B=new LWActivitySchema();
       B.setActivityID("B");
       Node nB=new Node();
       nB.setNodeID("B");
       nB.setDegree(2);
//       nB.setLWActivitySchema(B);
       g.addNode(nB);
       LWActivitySchema C=new LWActivitySchema();
       C.setActivityID("C");
       Node nC=new Node();
       nC.setNodeID("C");
       nC.setDegree(2);
//       nC.setLWActivitySchema(C);
       g.addNode(nC); 
       LWActivitySchema D=new LWActivitySchema();
       D.setActivityID("D");
       Node nD=new Node();
       nD.setNodeID("D");
       nD.setDegree(1);
//       nD.setLWActivitySchema(D);
       g.addNode(nD);  
       LWActivitySchema E=new LWActivitySchema();
       E.setActivityID("E");
       Node nE=new Node();
       nE.setNodeID("E");
       nE.setDegree(1);
//       nE.setLWActivitySchema(E);
       g.addNode(nE);
       LWActivitySchema F=new LWActivitySchema();
       F.setActivityID("F");
       Node nF=new Node();
       nF.setNodeID("F");
       nF.setDegree(3);
 //      nF.setLWActivitySchema(F);
       g.addNode(nF); 
       LWActivitySchema G=new LWActivitySchema();
       G.setActivityID("G");
       Node nG=new Node();
       nG.setNodeID("G");
       nG.setDegree(3);
//       nG.setLWActivitySchema(G);
       g.addNode(nG);
       LWActivitySchema H=new LWActivitySchema();
       H.setActivityID("H");
       Node nH=new Node();
       nH.setNodeID("H");
       nH.setDegree(1);
//       nH.setLWActivitySchema(H);
       g.addNode(nH);
         
       Edge edge1=new Edge();
       edge1.setEdgeID("AD");
       edge1.setFrom("A");
       edge1.setTo("D");
       g.addEdge(edge1);
       
       Edge edge2=new Edge();
       edge2.setEdgeID("AE");
       edge2.setFrom("A");
       edge2.setTo("E");
       g.addEdge(edge2);
       
       Edge edge3=new Edge();
       edge3.setEdgeID("BE");
       edge3.setFrom("B");
       edge3.setTo("E");
       g.addEdge(edge3);       

       Edge edge4=new Edge();
       edge4.setEdgeID("CF");
       edge4.setFrom("C");
       edge4.setTo("F");
       g.addEdge(edge4);
       
       Edge edge5=new Edge();
       edge5.setEdgeID("EG");
       edge5.setFrom("E");
       edge5.setTo("G");
       g.addEdge(edge5);       
       
       Edge edge6=new Edge();
       edge6.setEdgeID("FH");
       edge6.setFrom("F");
       edge6.setTo("H");
       g.addEdge(edge6);
       
       Edge edge7=new Edge();
       edge7.setEdgeID("FE");
       edge7.setFrom("F");
       edge7.setTo("E");
       g.addEdge(edge7);
       
       Edge edge8=new Edge();
       edge8.setEdgeID("GF");
       edge8.setFrom("G");
       edge8.setTo("F");
       g.addEdge(edge8);       
       
       Edge edge9=new Edge();
       edge9.setEdgeID("DH");
       edge9.setFrom("D");
       edge9.setTo("H");
       g.addEdge(edge9); 
       
//       g.addEdge(0, 3);  
//       g.addEdge(0, 4);  
//       g.addEdge(1, 4);  
//       g.addEdge(2, 5);  
// 
//       g.addEdge(4, 6);  
//       g.addEdge(5, 7);  
//       g.addEdge(5, 4); 
//       g.addEdge(6, 5) ;  
//       g.addEdge(3, 7) ;        
 
       //g.bfs();
       //g.displayGraph();
       //g.mergedegree(3);
       //g.init();
       //g.bfs();
       //g.displayGraph();
        g.mergeNode(g.getGraphNodeID("G"));
        g.delNode(g.getGraphNodeID("G"));
       //g.bfs();
       g.displayGraph();
   }  
}  
