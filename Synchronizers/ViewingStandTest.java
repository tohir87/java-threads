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
public class ViewingStandTest {
         public static void main(String[] args){
        ViewingStand picasso = new ViewingStand(2);
        for (int i =1; i <= 10; i++){
            Visitor c = new Visitor(picasso, i);
            c.start();
        }
    }
}
