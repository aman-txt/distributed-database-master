package parser;

import crypto.Security;

import java.io.*;
import java.security.MessageDigest;
import java.util.*;
public class LoginSignup {
    //"User_Profile"

    boolean login() throws IOException {
        //Id, pw , ask any one security que
        String userId="";
        String pwd="",orgpwd="";

        Scanner sc = new Scanner(System.in);
        String temp="";

        System.out.println("type your unique user Id:");
        userId = sc.nextLine();
        System.out.println("Type password:");
        pwd=sc.nextLine();



        FileReader fr =  new FileReader("./src/main/java/parser/UserData/User_Profile.txt");
        BufferedReader br= new BufferedReader(fr);


        String st;
        // Condition holds true till
        // there is character in a string
        while ((st = br.readLine()) != null)
        {
            // Print the string
            temp+=st;

        }
        int errorType=-1;
        String byUser[] = temp.split("]");
        for(int i=0; i<byUser.length; i++){
            byUser[i]= byUser[i].substring(1,byUser[i].length()-1);

            String[] parts = byUser[i].split(",");
                if(parts[0].equalsIgnoreCase(userId)){
                    orgpwd = parts[1];
                    orgpwd = Security.decrypt(orgpwd);
                    if(orgpwd.equals(pwd)){

                        List<Integer> givenList = Arrays.asList(2, 4, 6);
                        Random rand = new Random();
                        int randomElement = givenList.get(rand.nextInt(givenList.size()));

                        System.out.println("Q:"+ parts[randomElement]);
                        //Random gen
                        System.out.println("A:");
                        String tempAns= sc.nextLine();
                        if(tempAns.equalsIgnoreCase(parts[randomElement+1])){

                            return true;
                        }else {
                            errorType=1;
                        }


                    }else {
                        errorType=2;
                    }
                }
                else {
                    errorType=3;
                    }
        }
        if(errorType==3){
            System.out.println("Username not exist :(");

        }
        if(errorType==2){
            System.out.println("Password not matched :(");

        }
        if(errorType==1){
            System.out.println("Answer not matched :(");
        }
        if(errorType==-1){
            System.out.println("Algorithm issue :(");
        }


        return false;
    }
    boolean register() throws IOException {
        String userId="";
        String pwd="";
        Scanner sc = new Scanner(System.in);
        ArrayList<String> que = new ArrayList<>();
        ArrayList<String> ans = new ArrayList<>();
        //userId, password, 3 security ques
        System.out.println("====REGISTER====");
        System.out.println("type your unique user Id:");
        userId = sc.nextLine();
        System.out.println("Type password:");
        pwd=sc.nextLine();
        pwd = Security.encrypt(pwd);
     //   String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(pwd);
        System.out.println("Q1:");
        que.add(sc.nextLine());

        System.out.println("A1:");
        ans.add(sc.nextLine());

        System.out.println("Q2:");
        que.add(sc.nextLine());

        System.out.println("A2:");
        ans.add(sc.nextLine());

        System.out.println("Q3:");
        que.add(sc.nextLine());

        System.out.println("A3:");
        ans.add(sc.nextLine());

        File f = new File("./src/main/java/parser/UserData/User_Profile.txt");



        FileReader fr =  new FileReader("./src/main/java/parser/UserData/User_Profile.txt");
        BufferedReader br= new BufferedReader(fr);


        String st,tempp="";
        // Condition holds true till
        // there is character in a string
        while ((st = br.readLine()) != null)
        {
            // Print the string
            tempp+=st;

        }


        FileWriter ff = new FileWriter(f);
        BufferedWriter fw = new BufferedWriter(ff);
        System.out.println(que.size()+"  "+ans.size());
        fw.append(tempp);
        fw.append("{");
        //one users detail
        fw.append(userId).append(",");
        fw.append(pwd).append(",");
        fw.append(que.get(0)).append(",");
        fw.append(ans.get(0)).append(",");
        fw.append(que.get(1)).append(",");
        fw.append(ans.get(1)).append(",");
        fw.append(que.get(2)).append(",");
        fw.append(ans.get(2));

        fw.append("}]\n");
        fw.close();
        ff.close();
        if(login()){
            return true;
        }
        return false;
        }

    Boolean runHere() throws IOException {
        System.out.println("1.Login");
        System.out.println("2.Register");
        Scanner sc = new Scanner(System.in);
        int choice= sc.nextInt();

        if(choice==1){
            boolean chk=login();
            return chk;
        }
        if(choice==2){
            boolean chk=register();
            return chk;
        }
        if(choice>2 || choice <1){
            runHere();
        }
        sc.close();
        return false;
    }
}
