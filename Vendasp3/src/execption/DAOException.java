/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package execption;

public class DAOException extends Exception {

    public DAOException(String message, Exception e) {
        super(message, e);
    }
}