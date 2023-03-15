package com.bgc.poc;

import org.junit.*;

import static org.junit.Assert.assertEquals;
/**
 * @author rohitdhingra
 */
public class ConnectedTest {

    private Connected connected;

    @Before
    public void setUp(){
    	 connected = new Connected(ClassLoader.getSystemResource("cities.txt").getFile());
    }
 
    @Test
    public void isConnectedShouldReturnTrueForConnectedCities(){
        boolean isConnected1 = connected.isConnected("New York","Istanbul");
        assertEquals(true, isConnected1 ); // assert New York and Istanbul are connected
    }

    @Test
    public void isConnectedShouldReturnTrueForConnectedCities2(){
        boolean isConnected = connected.isConnected("Boston","Hartford");
        System.out.println("Boston & Hartford is connected >> " + isConnected);
        assertEquals(true, isConnected ); // assert Boston and Hartford are connected
    }

    @Test
    public void isConnectedShouldReturnFalseForNotConnectedCities(){
        boolean isConnected = connected.isConnected("Boston","Tampa");
        System.out.println("Boston & Tampa is connected >> " + isConnected);
        assertEquals(false,isConnected);
    }    

    
    @Test(expected=IllegalArgumentException.class)
    public void isConnectedShouldThrowExceptionforEmptyFromCity(){

        boolean isConnected = connected.isConnected("","Lima");
        assertEquals(false,isConnected);
    }    
    
    @Test(expected=IllegalArgumentException.class)
    public void isConnectedShouldThrowExceptionforEmptyToCity(){

        boolean isConnected = connected.isConnected("New York","");
        assertEquals(false,isConnected);
    }    

}
