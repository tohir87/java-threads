/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Synchronizers;

/**
 *
 * @author 2950574
 */
public class RestaurantTest {
    public static void main(String[] args){
        Restaurant restuarant = new Restaurant(2);
        for (int i =1; i <= 5; i++){
            RestuarantCustomer c = new RestuarantCustomer(restuarant, i);
            c.start();
        }
    }
}
