package TradePackages;

import java.util.Comparator;

public class TradeObject{

    //Variables
    float orderId;           //trade id
    float quantity;     //number of sharse to buy
    double price;       //price to buy at (Limit order)
    double asset;        //Coin or stock to buy
    double base;         //The base coin that user has
    float userID;       //The ID of the client
    int excahngeID;     //ID number of the exchange
    int side;           //Buy/Sell 0:Buy 1:Sell
    int orderType;      //Type of order new, amend, cancel [0 : New, 1: Amend, 2 :Cancel]
    int timeInForce;    //0:GTC(Good Til Cancel),1:(IOC)Immediate Fill or Cancel,2:(FOK)Fill or Kill,3:(GTD)Good for Day

    //Contructor Needs all variables at start no default constructor
    public TradeObject(float orderId,
                       float quantity,
                       double price,
                       double asset,
                       double base,
                       float userID,
                       int excahngeID,
                       int side,
                       int orderType,
                       int timeInForce)
    {
        this.orderId = orderId;
        this.quantity = quantity;
        this.price = price;
        this.asset = asset;
        this.base = base;
        this.userID = userID;
        this.excahngeID = excahngeID;
        this.side = side;
        this.orderType = orderType;
        this.timeInForce = timeInForce;
    }

    /**+
     * Getter for getting the Order id
     * @return
     */
    public float getOrderId(){return this.orderId; }

    /**+
     * Setter for the Order ID
     * @param orderId
     */
    public void setOrderId(float orderId){this.orderId = orderId;}

    /**+
     * Getter for Quantity
     * @return
     */
    public float getQuantity(){return this.quantity;}

    /**+
     * Setter for Quantity
     * @param quantity
     */
    public void setQuantity(float quantity){this.quantity = quantity;}

    /**+
     * Getter for price
     * @return
     */
    public double getPrice(){return this.price;}

    /**+
     * Setter for price
     * @param price
     */
    public void setPrice(double price) {this.price = price; }

    /**+
     * Getter for asset
     * @return
     */
    public double getasset(){return this.asset;}

    /**+
     * Setter for asset
     * @param asset
     */
    public void setasset(float asset) {this.asset = asset;}

    /**+
     * Getter for base
     * @param base
     */
    public void setBase(float base) {this.base = base;}

    /**+
     * Setter for base
     * @return
     */
    public double getBase() { return this.base;}

    /**+
     * Getter for usderID
     * @return
     */
    public float getUserId(){return this.userID; }

    /**+
     * Setter for User ID
     * @param userId
     */
    public void setUserId(float userId){this.userID = userId;}

    /**+
     * Getter for ExchangeID
     * @return
     */
    public int getExcahngeID(){return this.excahngeID;}

    /**+
     * Setter for exchenge ID
     * @param excahngeID
     */
    public void setExcahngeID(int excahngeID){this.excahngeID = excahngeID;}

    /**+
     * Getter for the Side of the order
     * @return
     */
    public int getSide() { return this.side; }

    /**+
     * Setter for the Side
     * @param side
     */
    public void setSide(int side) {this.side = side;}

    /**+
     * Getter for ordertype
     * @return
     */
    public int getOrderType(){return this.orderType;}

    /**+
     * Setter for ordertype
     * @param orderType
     */
    public void setOrderType(int orderType){this.orderType = orderType;}

    /**+
     * Getter for Time in force
     * @return
     */
    public int getTimeInForce(){return this.timeInForce;}

    /**+
     * Setter for time in force
     * @param timeInForce
     */
    public void setTimeInForce(int timeInForce){this.timeInForce = timeInForce;}



} // End of class

