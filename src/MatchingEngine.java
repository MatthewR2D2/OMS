import com.sun.deploy.util.ArrayUtil;

import java.lang.reflect.Array;
import java.util.List;

public class MatchingEngine {

    // Constructor Needs no optiion
    public MatchingEngine(){}

    public String orderMatch(TradeObject to, List<TradeObject> orderBook)
    {
        String result = "";
        //Check what type of order it is
        // [0 : New, 1: Amend, 2 :Cancel]
        if(to.orderType == 2) //Cancel
        {
            result = "Canceling order";
            //Send cancel request to the DB
            // and remove exsiting orderr from order book
        }else if(to.orderType == 1) //Amend Order
        {
            result = "Amending Order";
            //Find order in array
            //update all variables
            //Run matching engine with new ordere
        }else if (to.orderType == 0) //New Order
        {
            //Find side then send to the right matching method
            if(validateOrder(to))
            {
                result = "New Order and valid";
                //Check to see what side the order is one
                //0 = buy
                //1 = Sell
                if(to.side == 0){ buyOrderMatching(to, orderBook);}
                else { sellOrderMatching(to, orderBook); }

            }else{result = "New Order but not valid and rejected";}
        }
        else {result = "Unknow Order Type";}



        return result;
    }

    private Boolean validateOrder(TradeObject to)
    {
        boolean valid = false;
        float baseMinAmount = (float) 0.001;
        float assetMinAmount = (float) 0.01;
        if (to.getQuantity() > 0 &&
            to.base >= baseMinAmount &&
            to.asset >= assetMinAmount &&
            to.price >= 0)
        {
            // pass basic check
            valid = true;

        }else {valid = false;} //Return flase if it fails and is not valid order
        return valid;
    }

    private void sellOrderMatching(TradeObject to, List<TradeObject> orderBook)
    {
        System.out.println("Sell Order");
    }

    private void buyOrderMatching(TradeObject to, List<TradeObject> orderBook)
    {
        System.out.println("Buy Order");
        //Check to see if there is a limit price or not
        //If 0 then its a market order
        if(to.price > 0.00)
        {
            //Limit Order

        }else
        {
            // Market order
            //Loop through sell side orderbook
            //Assuming that it is sorted by price
            // First price should be best/lowest price
            System.out.println(orderBook.size());
            fillMarketOrder(to, orderBook, 0); //Start out at 0 or the first and lowest price best price
            if (to.quantity == 0)
            {
                System.out.println("Order Fully filled");
            }
        }

    }

    // These will fill solong as there is a opposite order
    private void fillMarketOrder(TradeObject to, List<TradeObject> orderBook, int index)
    {
        System.out.println("Starting to Fill order");
        System.out.println("Index:" + index + "Size:" + orderBook.size());
        if(to.quantity > orderBook.get(index).quantity)
        {
            //Partial Fill set the new quantitiy for the order
            //TODO: Make a fill method to actaully fill the order
            to.setQuantity(to.quantity - orderBook.get(index).quantity);
            //Remove Orderbook from array
            orderBook.remove(index);
            //Get the next order
            if(to.quantity > 0 && !orderBook.isEmpty())
            {
                fillMarketOrder(to, orderBook, index); //Recursive call back to start
            }else
                {
                    //Add the unfill order to the order book.
                    System.out.println("Order is not filled ans will be sent to the order book");
                    orderBook.add(to);
                }

        }else if(to.quantity == orderBook.get(index).quantity)
        {
            //Fill the order
            //TODO: Make a fill method to actaully fill the order
            to.setQuantity(0);
            //remove order from order book
            orderBook.remove(index);
        }else  //Orderbook has more quantity than the order
        {
            //TODO: Make a fill method to actaully fill the order
            //remove volume from the orderbook
            orderBook.get(index).setQuantity(orderBook.get(index).quantity - to.quantity);
            to.setQuantity(0);
        }
    }



}
