

import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Client extends JFrame implements ActionListener
{
    JLabel l,l2;
    JButton b;
    JTextField t,l1;
    BufferedWriter br;
    JPasswordField p1;
    public Client()
    {
        setLayout(null);
        l=new JLabel("Name");
        l1=new JTextField("");
        t=new JTextField();
        b=new JButton("Sign In");
        l2=new JLabel("Password");
         p1=new JPasswordField();
        add(l1);
        add(l);
        add(t);
        add(l2);
        add(p1);
        add(b);
        l1.setBounds(250, 70, 150, 25);
        l.setBounds(200,100,40,25);
        t.setBounds(250,100,150,25);
        b.setBounds(250,175,80,25);
        p1.setBounds(250,140,150,25);
        l2.setBounds(170,140,40,25);
        l1.setEditable(false);
        b.addActionListener(this);

    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==b)
        {
            try{
            
            String in=t.getText();
            if(in.equals("") || in.equals(" ") || (in.charAt(0)>=48 && in.charAt(0)<=57)){
                l1.setText("enter valid name");
                 return;
            }
            String pass=p1.getText();
            if(!pass.equals("1234"))
            {
                l1.setText("enter valid password");
                return;
            }
            this.dispose();
            c f=new c(in);
            f.setVisible(true);
            f.setTitle(in+"'s Chat WIndow");
            }
            catch(Exception g){}
        }
    }
    
    public static void main(String[] args) 
    {
        Client a=new Client();
        a.setSize(600,400);
        a.setVisible(true);
        a.setTitle("Chat Server");
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}