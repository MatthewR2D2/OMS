

import TradePackages.TradeObject;

import java.util.List;

public class MatchingEngine {

    // Constructor Needs no optiion
    public MatchingEngine(){}

    public String orderMatch(TradeObject to, List<TradeObject> buyBook, List<TradeObject> sellBook )
    {
        String result = "";
        //Check what type of order it is
        // [0 : New, 1: Amend, 2 :Cancel]
        if(to.getOrderType() == 2) //Cancel
        {
            result = "Canceling order";
            //Send cancel request to the DB
            // and remove exsiting orderr from order book
        }else if(to.getOrderType() == 1) //Amend Order
        {
            result = "Amending Order";
            //Find order in array
            //update all variables
            //Run matching engine with new ordere
        }else if (to.getOrderType() == 0) //New Order
        {
            //Find side then send to the right matching method
            if(validateOrder(to))
            {
                result = "New Order and valid";
                //Check to see what side the order is one
                //0 = buy
                //1 = Sell
                if(to.getSide() == 0){ buyOrderMatching(to, buyBook, sellBook);}
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
            to.getBase() >= baseMinAmount &&
            to.getasset() >= assetMinAmount &&
            to.getPrice() >= 0)
        {
            // pass basic check
            valid = true;

        }else {valid = false;} //Return flase if it fails and is not valid order
        return valid;
    }

    private void sellOrderMatching(TradeObject to, List<TradeObject> buyBook, List<TradeObject> sellBook)
    {
        System.out.println("Sell Order");
        //Check to see if there is a limit price or not
        //If 0 then its a market order
        if(to.getPrice() > 0.00)
        {
            //Limit Order
            //Find the closest price to the order
            int index = findClosestNumber(buyBook, to.getPrice());
            System.out.println("Best price match: " + index);
            System.out.println("Order Number: " + buyBook.get(index).getId());
            // Fill the order for the found order book order
            fillOrder(to, buyBook, sellBook, index);

        }else
        {
            // Market order
            //Loop through buy side orderbook
            //Assuming that it is sorted by price
            // First price should be best price
            System.out.println(buyBook.size());
            fillSellMarketOrder(to, buyBook, sellBook); //Start out at 0 or the first and lowest price best price
            if (to.getQuantity() == 0)
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
        if(to.getPrice() > 0.00)
        {
            //Limit Order
            //Find the closest price to the order
            int index = findClosestNumber(sellBook, to.getPrice());
            System.out.println("Best price match: " + index);
            System.out.println("Order Number: " + sellBook.get(index).getId());
            // Fill the order for the found order book order
            fillOrder(to, sellBook, buyBook, index);

        }else
        {
            // Market order
            //Loop through sell side orderbook
            //Assuming that it is sorted by price
            // First price should be best/lowest price
            System.out.println(sellBook.size());
            fillBuyMarketOrder(to, buyBook, sellBook); //Start out at 0 or the first and lowest price best price
            if (to.getQuantity() == 0)
            {
                System.out.println("Order Fully filled");
            }
        }

    }

    private void fillOrder(TradeObject to, List<TradeObject> orderBook, List<TradeObject> sameSideBook, int index)
    {
        if(to.getQuantity() > orderBook.get(index).getQuantity())
        {
            //Partial Fill set the new quantitiy for the order
            //TODO: Make a fill method to actaully fill the order
            to.setQuantity(to.getQuantity() - orderBook.get(index).getQuantity());
            //Remove Orderbook from array
            orderBook.remove(index);
            //Get the next order
            if(to.getQuantity() > 0 && !orderBook.isEmpty())
            {
                int newIndex = findClosestNumber(orderBook, to.getPrice());
                System.out.println("Order not fully filled Getting next order");
                fillOrder(to, orderBook, sameSideBook, newIndex); //Recursive call back to start
            }else
            {
                //Add the unfill order to the order book.
                System.out.println("Order is not filled and will be sent to the order book");
                sameSideBook.add(to);
            }

        }else if(to.getQuantity() == orderBook.get(index).getQuantity())
        {
            //Fill the order
            //TODO: Make a fill method to actaully fill the order
            to.setQuantity(0);
            //remove order from order book
            orderBook.remove(index);
            System.out.println("Order Fully filled");
        }else  //Orderbook has more quantity than the order
        {
            //TODO: Make a fill method to actually fill the order
            //remove volume from the orderbook
            orderBook.get(index).setQuantity(orderBook.get(index).getQuantity() - to.getQuantity());
            to.setQuantity(0);
            System.out.println("Order Fully filled Other order being updated");
        }

    }

    // These will fill solong as there is a opposite order
    private void fillBuyMarketOrder(TradeObject to, List<TradeObject> buyBook, List<TradeObject> sellBook)
    {
        System.out.println("Starting to Fill order");
        System.out.println("Index:" + 0 + "Size:" + sellBook.size());
        if(to.getQuantity() > sellBook.get(0).getQuantity())
        {
            //Partial Fill set the new quantitiy for the order
            //TODO: Make a fill method to actaully fill the order
            to.setQuantity(to.getQuantity() - sellBook.get(0).getQuantity());
            //Remove Orderbook from array
            sellBook.remove(0);
            //Get the next order
            if(to.getQuantity() > 0 && !sellBook.isEmpty())
            {
                fillBuyMarketOrder(to, buyBook, sellBook); //Recursive call back to start
            }else
                {
                    //Add the unfill order to the order book.
                    System.out.println("Order is not filled ans will be sent to the order book");
                    buyBook.add(to);
                }

        }else if(to.getQuantity() == sellBook.get(0).getQuantity())
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
            sellBook.get(0).setQuantity(sellBook.get(0).getQuantity() - to.getQuantity());
            to.setQuantity(0);
        }
    }

    // These will fill solong as there is a opposite order
    private void fillSellMarketOrder(TradeObject to, List<TradeObject> buyBook, List<TradeObject> sellBook)
    {
        System.out.println("Starting to Fill order");
        System.out.println("Index:" + 0 + "Size:" + buyBook.size());
        if(to.getQuantity() > buyBook.get(0).getQuantity())
        {
            //Partial Fill set the new quantitiy for the order
            //TODO: Make a fill method to actaully fill the order
            to.setQuantity(to.getQuantity() - buyBook.get(0).getQuantity());
            //Remove Orderbook from array
            buyBook.remove(0);
            //Get the next order
            if(to.getQuantity() > 0 && !buyBook.isEmpty())
            {
                fillSellMarketOrder(to, buyBook, sellBook); //Recursive call back to start
            }else
            {
                //Add the unfill order to the order book.
                System.out.println("Order is not filled ans will be sent to the order book");
                sellBook.add(to);
            }

        }else if(to.getQuantity() == buyBook.get(0).getQuantity())
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
            buyBook.get(0).setQuantity(buyBook.get(0).getQuantity() - to.getQuantity());
            to.setQuantity(0);
        }
    }

    private int findClosestNumber(List<TradeObject> orderBook, double target)
    {
        int n = orderBook.size();

        //Corner cases
        if(target <= orderBook.get(0).getPrice()) return 0;
        if (target >= orderBook.get(n-1).getPrice()) return n-1;

        int i = 0, j = n, mid = 0;
        while(i < j)
        {
            mid = (i + j) /2;
            // Check to see if target is the mid value
            if (orderBook.get(mid).getPrice() == target)
            { return mid; }

            //Check to see if target is less than mid then serach left
            if(target < orderBook.get(mid).getPrice())
            {
                // Get the closest of the two values of the target
                if (mid > 0 && target > orderBook.get(mid - 1).getPrice())
                {
                    //Check mid -1 and mid
                    return getClosest(orderBook,
                            mid,
                            target);
                }
                // Repeat for 2nd half of the array
                j = mid;
            }else // If target is greater than mid
            {
                if(mid < n -1 && target < orderBook.get(mid + 1).getPrice())
                {
                    return getClosest(orderBook,
                            mid,
                            target);
                }
                i = mid +1; //update i
            }
        }
        return mid;
    }

    private int getClosest(List<TradeObject> orderBook, int mid, double target) {

        if (target - orderBook.get(mid-1).getPrice() >= orderBook.get(mid).getPrice() - target)
        {
            return orderBook.indexOf(orderBook.get(mid-1));
        }else{ return orderBook.indexOf(orderBook.get(mid));}

    }


}
