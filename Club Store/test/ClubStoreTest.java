import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Program to test ClubStore methods
 * @author Michelle Glatz
 * @author William Baldwin
 */
public class ClubStoreTest {

    //
    // in test program

 
    /**
     * valid date bound
     */
    @Test
    public void testIsValidDateValidOctNonBoundary() {

        assertTrue(ClubStore.isValidDate(10, 23), "ClubStore.isValidDate(10, 23)");

    }
    
    /**
     * invalidoctbound
     */
    @Test
    public void testIsValidDateInvalidOctBoundary() {

        assertFalse(ClubStore.isValidDate(10, 14), "ClubStore.isValidDate(10, 14)");

    }
    
    /**
     * invalid month
     */
    @Test
    public void testIsValidDateInvalidMonth() {

        assertFalse(ClubStore.isValidDate(9, 30), "ClubStore.isValidDate(9, 30)");

    } 
    
    /**
     * negative
     */
    @Test
    public void testIsValidDateNovDayNegative() {

        assertFalse(ClubStore.isValidDate(11, -1), "ClubStore.isValidDate(11, -1)");

    }

    /**
     * zero
     */
    @Test
    public void testIsValidDateMonthZero() {

        assertFalse(ClubStore.isValidDate(0, 1), "ClubStore.isValidDate(0, 1)");

    }  


    /**
     * negative
     */
    @Test
    public void testIsValidDateMonthNegative() {

        assertFalse(ClubStore.isValidDate(-1, 1), "ClubStore.isValidDate(-1, 1)");

    }        
    

    /**
     * nov1
     */
    @Test
    public void testIsValidDateNov1() {
            
        assertTrue(ClubStore.isValidDate(11, 1), "ClubStore.isValidDate(11, 1)"); 

    }

    /**
     * oct31
     */
    @Test
    public void testIsValidDateOct31() {
        
        assertTrue(ClubStore.isValidDate(10, 31), "ClubStore.isValidDate(10, 31)");

    } 

    /**
     * nov30
     */
    @Test
    public void testIsValidDateNov30() {
            
        assertTrue(ClubStore.isValidDate(10, 31), "ClubStore.isValidDate(10, 31)"); 

    }

    
    /**
     * normal nov
     */
    @Test
    public void testIsValidDateNovNonBoundary() {
        
        assertTrue(ClubStore.isValidDate(11, 23), "ClubStore.isValidDate(11, 23)");

    }
     

    /**
     * oct 15
     */
    @Test
    public void testIsValidDateOctober15() {
        
        assertTrue(ClubStore.isValidDate(10, 15), "ClubStore.isValidDate(10, 15)"); 

    } 

    /**
     * nov 0 invlaid
     */
    @Test
    public void testIsValidDateNovDayZero() {
        
        assertFalse(ClubStore.isValidDate(11, 0), "ClubStore.isValidDate(11, 0)"); 

    }  


    /**
     * Nov 31
     */
    @Test
    public void testIsValidDateNov31() {
        
        assertFalse(ClubStore.isValidDate(11, 31), "ClubStore.isValidDate(11, 31)"); 

    }        

    /**
     * subtotal 1 bottle
     */
    @Test
    public void testGetSubtotalOneBottle() {
        
        assertEquals(10, ClubStore.getSubtotal(1, 0, 0),
                     "ClubStore.getSubtotal(1, 0, 0)");
        
    }

    /**
     * subtotal 0
     */
    @Test
    public void testGetSubtotalNoItems() {
        
        assertEquals(0, ClubStore.getSubtotal(0, 0, 0), 
                     "ClubStore.getSubtotal(0, 0, 0)");
        
    }
   

    /**
     * subtotal 1 mug
     */
    @Test
    public void testGetSubtotalOneMug() {
         
        assertEquals(12, ClubStore.getSubtotal(0, 1, 0),"ClubStore.getSubtotal(0, 1, 0)");
    }
    
 
    /**
     * subtotal 1 bag
     */
    @Test
    public void testGetSubtotalOneToteBag() {
         
        assertEquals(18, ClubStore.getSubtotal(0, 0, 1), "ClubStore.getSubtotal(0, 0, 1)"); 
    }  


    /**
     * subtotal 1 of each
     */
    @Test
    public void testGetSubtotalOneOfEachItem() {
       
        assertEquals(40, ClubStore.getSubtotal(1, 1, 1), "ClubStore.getSubtotal(1, 1, 1)"); 
    }

    /**
     * shipping cost 0
     */
    @Test
    public void testGetShippingCostSubtotal0() {
        
        assertEquals(0, ClubStore.getShippingCost(0, false, false), 
                     "ClubStore.getShippingCost(0, false, false)");
        
    }

    /**
     * shippingcost 10 + coupon
     */
    @Test
    public void testGetShippingCostSubtotal10CouponStandard() {
        
        assertEquals(0, ClubStore.getShippingCost(10, false, true), 
                     "ClubStore.getShippingCost(10, false, true)");       
        
    }
    
    /**
     * 25 standard shipping
     */
    @Test
    public void testGetShippingCostSubtotal25Standard() { 
        assertEquals(0, ClubStore.getShippingCost(25, false, false),
                     "ClubStore.getShippingCost(25, false, false)");
        
    }
    
 
    /**
     * 20 no coupon
     */
    @Test
    public void testGetShippingCostSubtotal20NoCouponStandard() {
      
        assertEquals(3, ClubStore.getShippingCost(20, false, false),
                     "ClubStore.getShippingCost(20, false, false)");
        
    } 

 
    /**
     * 15 two day
     */
    @Test
    public void testGetShippingCostSubTotal15TwoDay() {
         
        assertEquals(5, ClubStore.getShippingCost(15, true, false),
                     "ClubStore.getShippingCost(15, true, false)");
    }   
    
    /**
     * 45 two day
     */
    @Test
    public void testGetShippingCostSubTotal45TwoDay() {
        assertEquals(5, ClubStore.getShippingCost(45, true, false),
                     "ClubStore.getShippingCost(45, true, false)");
    }

    /**
     * 15 three day
     */
    @Test
    public void testGetArrivalDateOct15ThreeDays() {
        
        assertEquals("Tue, 10/18/2022", ClubStore.getArrivalDate(10, 15, 3), 
                     "ClubStore.getArrivalDate(10, 15, 3)");       
   
    }

    /**
     * 30 four day
     */
    @Test
    public void testGetArrivalDateOct30FourDays() {
        
        assertEquals("Thu, 11/3/2022", ClubStore.getArrivalDate(10, 30, 4), 
                     "ClubStore.getArrivalDate(10, 30, 4)");       
                
    }
    
    

    /**
     * 30 one day
     */
    @Test
    public void testGetArrivalDateNov30OneDay() {
    
        assertEquals("Thu, 12/1/2022", ClubStore.getArrivalDate(11, 30, 1), 
                     "ClubStore.getArrivalDate(11, 30, 1)");      
            
    }

    /**
     * 25 5 days
     */
    @Test
    public void testGetArrivalDateNov25FiveDays() {

        assertEquals("Wed, 11/30/2022", ClubStore.getArrivalDate(11, 25, 5), 
                     "ClubStore.getArrivalDate(11, 25, 5)");
  
    }    
    

    /**
     * 29 two days
     */
    @Test
    public void testGetArrivalDateOct29TwoDays() { 
        assertEquals("Mon, 10/31/2022", ClubStore.getArrivalDate(10, 29, 2), 
                     "ClubStore.getArrivalDate(10, 29, 2)");
    }

    
    /**
     * nov1 five day
     */
    @Test
    public void testGetArrivalDateNov1FiveDays() { 
        assertEquals("Sun, 11/6/2022", ClubStore.getArrivalDate(11, 1, 5), 
                     "ClubStore.getArrivalDate(11, 1, 5)");
    }    
       


    /**
     * Test the ClubStore methods with invalid values
     */
    @Test
    public void testInvalidMethods() {

        // Invalid test cases are provided for you below - You do NOT
        // need to add additional invalid tests. Just make sure these
        // pass!

        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> ClubStore.getSubtotal(-1, 0, 0),
                                 "ClubStore.getSubtotal(-1, 0, 0)");
        assertEquals("Invalid number", exception.getMessage(),
                     "Testing ClubStore.getSubtotal(-1, 0, 0) - " +
                     "exception message");
        
        exception = assertThrows(IllegalArgumentException.class,
            () -> ClubStore.getSubtotal(1, -1, 0),
                                 "ClubStore.getSubtotal(1, -1, 0)");
        assertEquals("Invalid number", exception.getMessage(),
                     "Testing ClubStore.getSubtotal(1, -1, 0) - " +
                     "exception message");
                     
        exception = assertThrows(IllegalArgumentException.class,
            () -> ClubStore.getSubtotal(1, 1, -1),
                                 "ClubStore.getSubtotal(1, 1, -1)");
        assertEquals("Invalid number", exception.getMessage(),
                     "Testing ClubStore.getSubtotal(1, 1, -1) - " +
                     "exception message");
                     
        exception = assertThrows(IllegalArgumentException.class,
            () -> ClubStore.getShippingCost(-1, false, false),
                                 "ClubStore.getShippingCost(-1, false, false)");
        assertEquals("Invalid subtotal", exception.getMessage(),
                     "Testing ClubStore.getShippingCost(-1, false, false) - " +
                     "exception message");                     
        
        exception = assertThrows(IllegalArgumentException.class,
            () -> ClubStore.getArrivalDate(10, 1, 1),
                                           "ClubStore.getArrivalDate(10, 1, 1)");
        assertEquals("Invalid date", exception.getMessage(),
                     "Testing ClubStore.getArrivalDate(10, 1, 1) - exception message");
                     
        exception = assertThrows(IllegalArgumentException.class,
            () -> ClubStore.getArrivalDate(2, 1, 0),
                                           "ClubStore.getArrivalDate(2, 1, 0)");
        assertEquals("Invalid date", exception.getMessage(),
                     "Testing ClubStore.getArrivalDate(2, 1, 0) - " + 
                     "exception message");                     
                     
        exception = assertThrows(IllegalArgumentException.class,
            () -> ClubStore.getArrivalDate(10, 20, 0),
                                           "ClubStore.getArrivalDate(10, 20, 0)");
        assertEquals("Invalid days", exception.getMessage(),
                     "Testing ClubStore.getArrivalDate(10, 20, 0) - exception message"); 

        exception = assertThrows(IllegalArgumentException.class,
            () -> ClubStore.getArrivalDate(10, 20, 6),
                                           "ClubStore.getArrivalDate(10, 20, 6)");
        assertEquals("Invalid days", exception.getMessage(),
                     "Testing ClubStore.getArrivalDate(10, 20, 6) - " + 
                     "exception message");                       
    }
}