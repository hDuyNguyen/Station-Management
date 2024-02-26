package com.main.Controller.Customer;

import com.main.Model.Bill;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerBill implements Initializable {
    @FXML
    private TextField billId;

    @FXML
    private TextField bookingDate;

    @FXML
    private TextField customerName;

    @FXML
    private TextField customerPhone;

    @FXML
    private TextField employeeName;

    @FXML
    private TextField stationName;

    @FXML
    private TextField timeIn;

    @FXML
    private TextField timeOut;

    @FXML
    private TextField totalPrice;

    private int bookingId;

    public CustomerBill(int bookingId){
        this.bookingId = bookingId;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Bill bill = Bill.searchBill(bookingId);
        System.out.println(bookingId);
        String billIdd = String.valueOf(bill.getBillId());
        System.out.println(billIdd);
        billId.setText(String.valueOf(bill.getBillId()));
        customerName.setText(bill.getBooking().getCustomer().getCustomerName());
        customerPhone.setText(bill.getBooking().getCustomer().getCustomerPhone());
        stationName.setText(bill.getBooking().getStation().getStationName());
        bookingDate.setText(String.valueOf(bill.getBooking().getBookingDate()));
        timeIn.setText(bill.getBooking().getTimeIn().toString());
        timeOut.setText(bill.getBooking().getTimeOut().toString());
        totalPrice.setText(String.valueOf(bill.getBooking().getTotalPrice()));
        employeeName.setText(bill.getEmployee().getUsername());
    }
}
