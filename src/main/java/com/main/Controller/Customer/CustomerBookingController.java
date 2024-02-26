package com.main.Controller.Customer;

import com.main.Model.Booking;
import com.main.Model.Customer;
import com.main.Model.Station;
import com.main.View.Customer.CustomerBooking;
import com.main.View.Customer.CustomerInformation;
import com.main.View.Customer.HistoryBooking;
import com.main.View.Login;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CustomerBookingController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private Button createBookingButton;

    @FXML
    private TextField customerNameField;

    @FXML
    private TextField customerPhoneField;
    @FXML
    private DatePicker dayBookingPicker;

    @FXML
    private ChoiceBox<String> stationBox;

    @FXML
    private ChoiceBox<LocalTime> timeInBox;

    @FXML
    private ChoiceBox<LocalTime> timeOutBox;

    @FXML
    private TextField totalPriceField;

    @FXML
    private Button calculateButton;

    @FXML
    private Button createButton;

    @FXML
    private Button inforButton;

    @FXML
    private Button logOutButton;
    @FXML
    private Button historyBookingButton;

    private Customer customer;
    private Map<String, Integer> stationMap = new HashMap<>();


    public CustomerBookingController(Customer customer) {
        this.customer = customer;

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerNameField.setText(customer.getCustomerName());
        customerPhoneField.setText(customer.getCustomerPhone());
        List<LocalTime> timeSlots = generateTimeSlots();
        timeInBox.getItems().addAll(timeSlots);
        timeOutBox.getItems().addAll(timeSlots);
        dayBookingPicker.setOnAction(e -> updateAvailableStations());
        timeInBox.setOnAction(e -> updateAvailableStations());
        timeOutBox.setOnAction(e -> updateAvailableStations());
    }

    private List<LocalTime> generateTimeSlots() {
        List<LocalTime> timeSlots = new ArrayList<>();
        LocalTime timeIn = LocalTime.of(8, 0);
        LocalTime timeOut = LocalTime.of(18, 0);

        while (timeIn.isBefore(timeOut)) {
            timeSlots.add(timeIn);
            timeIn = timeIn.plusHours(1);
        }
        return timeSlots;
    }

    private void updateAvailableStations() {
        LocalDate date = dayBookingPicker.getValue();
        LocalTime timeIn = timeInBox.getValue();
        LocalTime timeOut = timeOutBox.getValue();

        if (date != null && timeIn != null && timeOut != null) {
            // Lấy danh sách sân còn trống trong khoảng thời gian đã chọn
            List<Station> availableStations = Booking.getAvailableStations(date, timeIn, timeOut);
            // Cập nhật danh sách sân còn trống vào ChoiceBox
            // Xóa tất cả các mục trong ChoiceBox
            stationBox.getItems().clear();
            // Thêm các mục sân còn trống vào ChoiceBox
            for (Station station : availableStations) {
                String stationName = station.getStationName();
                int stationId = station.getStationId();
                stationBox.getItems().add(stationName);
                stationMap.put(stationName, stationId);
            }
        }
    }
    public void calculatePriceOnAction(){
        LocalTime timeIn = timeInBox.getValue();
        LocalTime timeOut = timeOutBox.getValue();
        String selectedStation = stationBox.getValue();
        Duration duration = Duration.between(timeIn,timeOut);
        long hours = duration.toHours();
        double price = Station.getPrice(selectedStation);
        double totalPrice = price*hours;
        totalPriceField.setText(String.valueOf(totalPrice));
        System.out.println(selectedStation);
    }
    public void createBookingOnAction(){
        int customerId = customer.getCustomerId();
        Customer customer = new Customer(customerId);
        String selectedStationName = stationBox.getValue();
        int selectedStationId = stationMap.get(selectedStationName);
        Station station = new Station(selectedStationId);
        LocalDate date = dayBookingPicker.getValue();
        LocalTime timeIn = timeInBox.getValue();
        LocalTime timeOut = timeOutBox.getValue();
        double totalPrice = Double.parseDouble(totalPriceField.getText());
        if(date != null || timeIn != null || timeOut != null|| totalPriceField != null){
            Booking booking = new Booking(customer, station, date, timeIn, timeOut, totalPrice);
            if(booking.createBooking()){
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Tạo Phiếu thành công");
            }else {
                showAlert(Alert.AlertType.ERROR, "Error", "Lỗi");
            }
        }else {
            showAlert(Alert.AlertType.ERROR, "Error", "Phải điền đủ thông tin");
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void createBooking()throws Exception{
        CustomerBooking controller = new CustomerBooking(customer);
        controller.start(new Stage());
        Stage currentStage = (Stage) createButton.getScene().getWindow();
        currentStage.close();
    }
    public void historyBooking() throws Exception{
        HistoryBooking historyBooking = new HistoryBooking(customer);
        historyBooking.start(new Stage());
        Stage currentStage = (Stage) historyBookingButton.getScene().getWindow();
        currentStage.close();
    }
    public void updateInformation() throws Exception {
        CustomerInformation customerInformation = new CustomerInformation(customer);
        customerInformation.start(new Stage());
        Stage currentStage = (Stage) inforButton.getScene().getWindow();
        currentStage.close();
    }
    public void logOut()throws Exception{
        Login login = new Login();
        login.start(new Stage());
        Stage currentStage = (Stage) logOutButton.getScene().getWindow();
        currentStage.close();
    }




}
