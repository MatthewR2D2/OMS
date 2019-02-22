
/*
*This is the main tester class for the macthing engine
 */
import HelperClasses.PrettyPrinterUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainTestClass {

    public static void main(String[] args) {

        //Help with printing
        PrettyPrinterUtil ppu = new PrettyPrinterUtil();

        //Sample trade objects
        TradeObject to1 = new TradeObject(
                1,
                1000000,
                0,
                1.02,
                2.01,
                1234,
                2,
                0,
                0,
                0);

        TradeObject ob1 = new TradeObject(
                2,
                200,
                1.01,
                1.2,
                0.01,
                1234,
                2,
                1,
                0,
                0);
        TradeObject ob2 = new TradeObject(
                2,
                200,
                1.31,
                1.2,
                0.01,
                1234,
                2,
                1,
                0,
                0);
        TradeObject ob3 = new TradeObject(
                3,
                200,
                100.01,
                1.2,
                0.01,
                1234,
                2,
                1,
                0,
                0);

        TradeObject ob4 = new TradeObject(
                3,
                200,
                0.95,
                1.2,
                0.01,
                1234,
                2,
                1,
                0,
                0);


        List<TradeObject> orderBook = new ArrayList<>();
        orderBook.add(ob1);
        orderBook.add(ob2);
        orderBook.add(ob3);
        orderBook.add(ob4);


        MatchingEngine me = new MatchingEngine();
        String result = me.orderMatch(to1, orderBook);
        ppu.print(result);


    }

}
