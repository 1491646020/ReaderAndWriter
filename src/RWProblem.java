import java.util.concurrent.Semaphore;
public class RWProblem {


    /**
     * 读优先
     */

    //初始化线程赋值许可证唯一
    //在获取到许可证、或者被其他线程调用中断之前线程一直处于阻塞状态。
    //读和写均获取该信号量的许可证，任意一方获取，则另外一方进行阻塞等待释放
    //写着的逻辑很简单，就不解释了
    /**
     *  读者的逻辑为：先判定当前有没有读线程在运行，无读线程在运行有两种情况：
     *  1.前面没有任何线程（获取信号量的许可证时直接获取，然后开始当前进程）
     *  2.前面有写的线程运行（获取信号量的许可证时等待释放然后获取，然后再开始当前进程）
     *  两种问题直接用获取信号量的许可证即可解决
     *
     *  有读线程在运行则直接则直接进行读（）
     *
     */

    private final Semaphore wr = new Semaphore(1);
    private final Semaphore x = new Semaphore(1);
    private int readCount = 0;

    /**
     * 读者
     */
    class Reader implements Runnable {
        /**
         * 线程的序号
         */
        private final String num;
        /**
         * 线程操作申请时间
         */
        private final long startTime;
        /**
         * 线程操作申请时间
         */
        private final long workTime;

        Reader(String num, long startTime, long workTime) {
            this.num = num;
            this.startTime = startTime * 1000;
            this.workTime = workTime * 1000;
            System.out.println(num + "号读进程被创建");
        }

        /**
         * 读过程
         */
        private void read() {
            System.out.println(num + "号线程开始读操作");
            try {
                Thread.sleep(workTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(num + "号线程结束读操作");
        }

        @Override


        public void run() {
            try {
                Thread.sleep(startTime);
                System.out.println(num + "号线程发出读操作申请");
                if (readCount == 0) {
                    wr.acquire();
                }
                readCount++;
                read(); //读过程
                readCount--;
                if (readCount == 0) {
                    wr.release();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 写者
     */
    class Writer implements Runnable {

        private final String num; //线程的序号
        private final long startTime; //线程操作申请时间
        private final long workTime; //线程的执行时间

        Writer(String num, long startTime, long workTime) {
            this.num = num;
            this.startTime = startTime * 1000;
            this.workTime = workTime * 1000;
            System.out.println(num + "号写进程被创建");

        }

        /**
         * 写过程
         */
        private void write() {
            System.out.println(num + "号线程开始写操作");
            try {
                Thread.sleep(workTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(num + "号线程结束写操作");
        }

        @Override
        public void run() {
            try {
                Thread.sleep(startTime);
                System.out.println(num + "号线程发出写操作申请");
                wr.acquire();
                x.acquire();
                write(); //写过程
                x.release();
                wr.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}