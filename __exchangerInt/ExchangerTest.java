package __exchangerInt;

import java.util.ArrayList;
import java.util.concurrent.Exchanger;

/**
 *
 * @author tohir
 */
public class ExchangerTest {

    public static void main(String[] args) {
        Exchanger<ArrayList<Integer>> exchanger = new Exchanger<>();
        ExchangerProducer producer = new ExchangerProducer(exchanger);
        ExchangerConsumer consumer = new ExchangerConsumer(exchanger);
        producer.start();
        consumer.start();
    }
}
