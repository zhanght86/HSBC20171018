

package com.sinosoft.lis.reinsure.calculate.exercise;

import java.applet .*;
import java.awt.*;

public class HelloWorld extends Applet{
    public HelloWorld() {
    }
    private void aa(){
        System.out.println(" aa: "+this.getClass().getResource("/").getPath().substring(1).replace('\\','/'));
        System.out.println(" aa: "+Class.class.getResource("/").getPath());
    }
    public void paint(Graphics g)
    {
        g.drawString("你好,Java世界！", 2, 20);
    }
    public static void main(String[] args) {
        HelloWorld hw = new HelloWorld();
        hw.aa();
    }
}
