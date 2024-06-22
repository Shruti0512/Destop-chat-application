
import java.net.*;
import java.io.*;
import java.util.*;
    
 class s
{
    static ArrayList<Cli> v=new ArrayList<>();
    //static ArrayList<PrintWriter> temp=new ArrayList<>();
    
   /*  public static ArrayList<PrintWriter> retu()
    {
       return temp;
    }*/
    public void trial() throws IOException
    {
        
        ServerSocket ss=new ServerSocket(1111);
        FileWriter fw=null;
          BufferedWriter bw=null;
          PrintWriter pw=null;
        File f=new File("Chat.txt");
        fw =new FileWriter(f);
        fw.write("");
          try{
            fw.flush();
            fw.close();
          }catch(IOException e){}
        fw =new FileWriter(f,true);
        bw=new BufferedWriter(fw);
        pw=new PrintWriter(bw);
       // temp.add(pw);
       // new A(pw, bw, fw);
        // pw.write("hmm");
        while(true)
        {
            Socket soc=ss.accept();
            DataInputStream din=new DataInputStream(soc.getInputStream());
            DataOutputStream dout=new DataOutputStream(soc.getOutputStream());  
                
            String str=(String)din.readUTF();
            Cli cl=new Cli(soc,din,dout,str,pw);
            Thread t=new Thread(cl);
            v.add(cl);
            t.start();
        }
    }
}

class Cli extends s implements Runnable 
{
    Socket soc;
    DataInputStream din;
    DataOutputStream dout;
    String name=new String();
    String in=new String();
    String temp=new String();
    int year,month,day,hour,min,am;
    String pm;
    PrintWriter pw;
    //BufferedWriter bw;
        
    Cli(Socket x,DataInputStream y, DataOutputStream z,String str,PrintWriter pw)
    {
	soc=x;
	din=y;
	dout=z;
        name=str;
        this.pw=pw;
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
                
                in=din.readUTF();
              //  pw.write("hello");
		//System.out.println(in);
        pw.println(in);
		for(Cli hi:v)
		{
                    hi.dout.writeUTF(in);
                   // bw.write(in);
                    pw.flush();
        }
                temp="*************************************** "+name+" left at "+hour+":"+min+" "+pm+"  "+day+"/"+month+"/"+year+" ***************************************";
               
               // pw.println(temp);
                
                if(in.equals(temp))
		        {
                   // pw.println(temp);
                    v.remove(this);
                    this.soc.close();
                    break;
	        	}
            }
            catch(IOException e)
            {
               // e.printStackTrace();
            }
        }
        if(v.isEmpty())
        {
            try{
                pw.close();
            this.din.close();
            this.dout.close();
       
        }
	catch(IOException e)
	{
           // e.printStackTrace();
        }
    }
}
}
public class St {
    public static void main(String[] args) {
        try{
            s ob =new s();
          ob.trial();
        }
        catch(Exception e){}
    }
}
