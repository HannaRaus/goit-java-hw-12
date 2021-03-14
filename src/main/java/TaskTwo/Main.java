package TaskTwo;


/*
Создайте свою аннотацию Repeat с целочисленным параметром.

Расширьте класс  ThreadPoolExecutor и переопределите метод execute следующим образом:
если экземпляр Runnable имеет аннотацию @Repeat, то его метод run выполняется несколько раз
(количество задается параметром в @Repeat).
 */
public class Main {
    public static void main(String[] args) {

        MyThreadPoolExecutor service = new MyThreadPoolExecutor(3);
        service.execute(new MyRunnable());

        service.shutdown();

    }
}
