

import java.util.List;

public class MatchingEngine {

    // Constructor Needs no optiion
    public MatchingEngine(){}

    public String orderMatch(TradeObject to, List<TradeObject> buyBook, List<TradeObject> sellBook )
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
                if(to.side == 0){ buyOrderMatching(to, buyBook, sellBook);}
                else { sellOrderMatching(to, buyBook, sellBook); }

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

    private void sellOrderMatching(TradeObject to, List<TradeObject> buyBook, List<TradeObject> sellBook)
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
            // First price should be best price
            System.out.println(buyBook.size());
            fillSellMarketOrder(to, buyBook, sellBook); //Start out at 0 or the first and lowest price best price
            if (to.quantity == 0)
            {
                System.out.println("Order Fully filled");
            }
        }
    }

    private void buyOrderMatching(TradeObject to, List<TradeObject> buyBook, List<TradeObject> sellBook)
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
            System.out.println(sellBook.size());
            fillBuyMarketOrder(to, buyBook, sellBook); //Start out at 0 or the first and lowest price best price
            if (to.quantity == 0)
            {
                System.out.println("Order Fully filled");
            }
        }

    }

    // These will fill solong as there is a opposite order
    private void fillBuyMarketOrder(TradeObject to, List<TradeObject> buyBook, List<TradeObject> sellBook)
    {
        System.out.println("Starting to Fill order");
        System.out.println("Index:" + 0 + "Size:" + sellBook.size());
        if(to.quantity > sellBook.get(0).quantity)
        {
            //Partial Fill set the new quantitiy for the order
            //TODO: Make a fill method to actaully fill the order
            to.setQuantity(to.quantity - sellBook.get(0).quantity);
            //Remove Orderbook from array
            sellBook.remove(0);
            //Get the next order
            if(to.quantity > 0 && !sellBook.isEmpty())
            {
                fillBuyMarketOrder(to, buyBook, sellBook); //Recursive call back to start
            }else
                {
                    //Add the unfill order to the order book.
                    System.out.println("Order is not filled ans will be sent to the order book");
                    buyBook.add(to);
                }

        }else if(to.quantity == sellBook.get(0).quantity)
        {
            //Fill the order
            //TODO: Make a fill method to actaully fill the order
            to.setQuantity(0);
            //remove order from order book
            sellBook.remove(0);
        }else  //Orderbook has more quantity than the order
        {
            //TODO: Make a fill method to actually fill the order
            //remove volume from the orderbook
            sellBook.get(0).setQuantity(sellBook.get(0).quantity - to.quantity);
            to.setQuantity(0);
        }
    }

    // These will fill solong as there is a opposite order
    private void fillSellMarketOrder(TradeObject to, List<TradeObject> buyBook, List<TradeObject> sellBook)
    {
        System.out.println("Starting to Fill order");
        System.out.println("Index:" + 0 + "Size:" + buyBook.size());
        if(to.quantity > buyBook.get(0).quantity)
        {
            //Partial Fill set the new quantitiy for the order
            //TODO: Make a fill method to actaully fill the order
            to.setQuantity(to.quantity - buyBook.get(0).quantity);
            //Remove Orderbook from array
            buyBook.remove(0);
            //Get the next order
            if(to.quantity > 0 && !buyBook.isEmpty())
            {
                fillSellMarketOrder(to, buyBook, sellBook); //Recursive call back to start
            }else
            {
                //Add the unfill order to the order book.
                System.out.println("Order is not filled ans will be sent to the order book");
                sellBook.add(to);
            }

        }else if(to.quantity == buyBook.get(0).quantity)
        {
            //Fill the order
            //TODO: Make a fill method to actaully fill the order
            to.setQuantity(0);
            //remove order from order book
            buyBook.remove(0);
        }else  //Orderbook has more quantity than the order
        {
            //TODO: Make a fill method to actually fill the order
            //remove volume from the orderbook
            buyBook.get(0).setQuantity(buyBook.get(0).quantity - to.quantity);
            to.setQuantity(0);
        }
    }



}
