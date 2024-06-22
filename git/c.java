import abc.*;
import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.*;
import javax.swing.text.DefaultCaret;

class c extends JFrame implements WindowListener
{
    JButton b,sr;
    JScrollPane jScrollPane1;
    JScrollPane jScrollPane2;
    JLabel l;
    JTextArea t1;
    JTextArea t2;
    Socket s;
    DataInputStream din;
    DataOutputStream dout;
    String in;
    Thread th1,th2;
    BufferedWriter br;
    public void windowClosing(WindowEvent e)
    {
        try
        {
            Calendar calen = Calendar.getInstance();
            int year = calen.get(Calendar.YEAR);
            int month = calen.get(Calendar.MONTH);
            int day = calen.get(Calendar.DAY_OF_MONTH);
            int hour = calen.get(Calendar.HOUR);
            int min=calen.get(Calendar.MINUTE);
            int am=calen.get(Calendar.AM_PM);
            String pm=am==Calendar.AM ? "AM" : "PM";
            
            String str="*************************************** "+in+" left at "+hour+":"+min+" "+pm+"  "+day+"/"+month+"/"+year+" ***************************************";
            dout.writeUTF(str);
           // br.write(str);
        }
        catch(IOException f)
        {
            f.printStackTrace();
        }
    }
    
    public void windowOpened(WindowEvent e){}
    public void windowClosed(WindowEvent e){}
    public void windowIconified(WindowEvent e){}
    public void windowDeiconified(WindowEvent e){}
    public void windowActivated(WindowEvent e){}
    public void windowDeactivated(WindowEvent e){}
    
    public c(String a)
    {
        
        initComponents();
        try
	{
            DefaultCaret caret = (DefaultCaret)t1.getCaret();
            caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
            
            s=new Socket("127.0.0.1",1111);
            din=new DataInputStream(s.getInputStream());
            dout=new DataOutputStream(s.getOutputStream());
            
            addWindowListener(this);
            
            Calendar caln = Calendar.getInstance();
            int year = caln.get(Calendar.YEAR);
            int month = caln.get(Calendar.MONTH);
            int day = caln.get(Calendar.DAY_OF_MONTH);
            int hour = caln.get(Calendar.HOUR);
            int min=caln.get(Calendar.MINUTE);
            int am=caln.get(Calendar.AM_PM);
            String pm=am==Calendar.AM ? "AM" : "PM";
            
            in=a;
            String str="*************************************** "+in+" joined at "+hour+":"+min+" "+pm+"  "+day+"/"+month+"/"+year+" ***************************************";
            dout.writeUTF(in);
            dout.writeUTF(str);
            str=(String)din.readUTF();
            t1.append(str+"\n");
            //br.write(str);
            
            read r=new read(s,din,t1,in,dout);
            th1=new Thread(r);
            th1.start();
		
            write w=new write(dout,in,t2,b,sr);
            th2=new Thread(w);
            th2.start();
                
            t2.requestFocusInWindow();
	}
	catch(Exception e)
	{
            e.printStackTrace();
	}
    }
    
    @SuppressWarnings("unchecked")                        
    private void initComponents() 
    {
        jScrollPane1 = new javax.swing.JScrollPane();
        t1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        t2 = new javax.swing.JTextArea();
        b = new javax.swing.JButton();
        l = new javax.swing.JLabel();
         sr=new JButton();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        t1.setColumns(20);
        t1.setRows(5);
        t1.setEditable(false);
        jScrollPane1.setViewportView(t1);

        t2.setColumns(20);
        t2.setRows(5);
        jScrollPane2.setViewportView(t2);

        b.setFont(new java.awt.Font("Times New Roman", 1, 10)); // NOI18N
        b.setText("Send");

        sr.setFont(new java.awt.Font("Times New Roman", 1, 10)); // NOI18N
        sr.setText("srch");

        l.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        l.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        l.setText("Broadcast messages from all online users");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(b)
                        .addComponent(sr))
                    .addComponent(l, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(l, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(b, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                    .addComponent(sr,javax.swing.GroupLayout.DEFAULT_SIZE,75,Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }                              
}

class read extends JFrame implements Runnable
{
    Socket s;
    DataInputStream din;
    JTextArea t1;
    String temp=new String();
    String in=new String();
    String str=new String();
    DataOutputStream dout;
    int year,month,day,hour,min,am;
    String pm;
   // BufferedWriter br;
        
    read(Socket x,DataInputStream y,JTextArea z,String tem,DataOutputStream h)
    {
	s=x;
	din=y;
        t1=z;
        temp=tem;
        dout=h;
        //this.br=br;
    }
        
    public void run()
    {
        Calendar cal = Calendar.getInstance();
        while(true)
        {
            try
            {
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);
                hour = cal.get(Calendar.HOUR);
                min=cal.get(Calendar.MINUTE);
                am=cal.get(Calendar.AM_PM);
                pm=am==Calendar.AM ? "AM" : "PM";
                
                str="*************************************** "+temp+" left at "+hour+":"+min+" "+pm+"  "+day+"/"+month+"/"+year+" ***************************************";
                in=din.readUTF();
                if(in.equals(str))
                {
                    s.close();
                   // br.close();
                    break;
                }
                t1.append(in+"\n");
                //br.write(in);
            }
            catch(IOException e)
            {
              //  e.printStackTrace();
            }
        }
        try
        {
            din.close();
            dout.close();
            
        }
        catch(IOException f)
        {
           // f.printStackTrace();
        }
    }
}

class write extends JFrame implements Runnable,ActionListener 
{
    static int flag=0;
    DataOutputStream dout;
    String in,out;
    JTextArea t2;
    JButton b,sr;
   // BufferedWriter br;
    write(DataOutputStream z,String a,JTextArea p,JButton o,JButton sr)
    { 
        dout=z;
        in=a;
        t2=p;
        b=o;
        this.sr=sr;
    }
    
    public void run()
    {
        b.addActionListener(this);
        sr.addActionListener(this);
        while(true)
        {
            out=t2.getText();
        }
    }
    
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            if(e.getSource()==b)
            {
                dout.writeUTF(in+": "+out);
            // br.write(in+" "+out);
                t2.setText("");
                t2.requestFocusInWindow();
            }
            if(e.getSource()==sr)
            {
                JTextField tf1=new JTextField(20);
                String text=JOptionPane.showInputDialog(tf1, "Enter your text");
                JFrame frame=new JFrame();
                JPanel panel=new JPanel();
               // JButton bt1=new JButton("okay");
                JTextArea te=new JTextArea();

                // panel.setSize(200,300);
                 //panel.setLayout(null);
                 //panel.setLayout(new FlowLayout(FlowLayout.CENTER,150,10));
                // panel.setVisible(true);
                // setDefaultCloseOperation(EXIT_ON_CLOSE);
                 panel.add(te);
                 //panel.add(bt1);
                 frame.add(panel);
                 
                 frame.setVisible(true);
                // frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                  frame.setSize(300,300);
                  frame.setLayout(new FlowLayout(FlowLayout.CENTER,150,50)); 
                 // A.close(text);
                 /*  te.append("Shruti");
                  te.append("\n");
                  te.append("chaturvedi");*/
                  //A a=new A();
                 // a.close();
                 te.setEditable(false);
                String stri="fine";
                if(text.equals(stri))
                {
                    te.setText("");
                    te.append("abc: i am fine");
                    te.append("\n");
                    te.append("def:i am also fine");
                    //flag++;
                }
                else if(text.equals("how"))
                {
                    te.setText("");
                    te.append("ghi: how r u?");
                }
                else if(text.equals("i am fine"))
                {
                    te.setText("");
                    te.append("abc: i am fine");
                }
                 else
                 {
                    te.setText("");
                    te.append("No Result Found");
                 }
                    
            }

        }
        catch(IOException f)
        {
            //f.printStackTrace();
        }
    }
}