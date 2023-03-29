import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Integer flag=-1;
        //用户判定模块
        Scanner scanner=new Scanner(System.in);
        System.out.println("--------请输入你所需要的读写优先问题的数字编号：");
        System.out.println("1.读优先");
        System.out.println("2.写优先");
        flag = scanner.nextInt();
        System.out.println(flag);


        RWProblem readerAndWriter = new RWProblem();
        WRProblem readerAndWriter1 = new WRProblem();
        String filepath = "src/test.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(" ");
                //线程数
                String num = words[0];
                //线程类型
                String type = words[1];
                //线程操作申请时间
                long startTime = Long.parseLong(words[2]);
                //线程的执行时间
                long workTime = Long.parseLong(words[3]);
                if(flag==1) {
                    if ("R".equals(type)) {
                        RWProblem.Reader reader = readerAndWriter.new Reader(num, startTime, workTime);
                        new Thread(reader).start();

                    } else if ("W".equals(type)) {
                        RWProblem.Writer writer = readerAndWriter.new Writer(num, startTime, workTime);
                        new Thread(writer).start();
                    } else {
                        System.out.println("测试文件出错");
                        throw new Exception();
                    }
                }
                    else if(flag==2)
                {
                    if ("R".equals(type)) {
                        WRProblem.Reader reader=readerAndWriter1.new Reader(num,startTime,workTime);
                        new Thread(reader).start();

                    } else if ("W".equals(type)) {
                        WRProblem.Writer writer=readerAndWriter1.new Writer(num,startTime,workTime);
                        new Thread(writer).start();
                    } else {
                        System.out.println("测试文件出错");
                        throw new Exception();
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}