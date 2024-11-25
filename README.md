# iyzico coding challenge solution.

Thank you for applying to work in Iyzico Engineering.

As part of our interview process, we expect you to complete a coding challenge in order for us to understand your coding skills. 
The challenge is a Java11 + Spring Boot project which uses H2 as the database.


# Answer 1: Flight Booking System


* [FlightController](src/main/java/com/iyzico/challenge/controller/FlightController.java), consist of API's that support adding, removing and updating for Flight Entity, also returns the list of flights with available seats.
* [SeatController](src/main/java/com/iyzico/challenge/controller/SeatController.java) consist of API's that support adding, removing and updating for Seat Entity, For seat to be created There should be a Flight registered on the system.
* [SeatPurchaseController](src/main/java/com/iyzico/challenge/controller/SeatPurchaseController.java) consist of an API that  helps user by their selected seat. It has an integration with the Iyzico Payment Systems. In request body we take all the necessary information.
Because currently this application doesn't have a user saving functionality. 
* With the help of [@Lock](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/Lock.html) annotation, we can disable the access to specific entity on Database Level. By doing so we prevent the sale of the same seat to sold in 2 different transactions.
* Iyzico Payment Integration can be Found in  [IyzicoPaymentIntegrationService](src/main/java/com/iyzico/challenge/service/IyzicoPaymentIntegrationService.java), IyzicoPaymentClient is a client class so that TestCases can mock the behavior and not fail.


## ANSWER 2: Latency Management
The all you have to do is to remove the transactional annotation at class definition.
By doing this you tell Hibernate to not open connection to database when method is called.
It opens connection when save method called.
So the Only change should be this

````java
@Service
//@Transactional This is for demonstration purposes. Original Code has it removed completely.
public class IyzicoPaymentService {

    private Logger logger = LoggerFactory.getLogger(IyzicoPaymentService.class);

    private BankService bankService;
    private PaymentRepository paymentRepository;

    public IyzicoPaymentService(BankService bankService, PaymentRepository paymentRepository) {
        this.bankService = bankService;
        this.paymentRepository = paymentRepository;
    }

    public void pay(BigDecimal price) {
        //pay with bank
        BankPaymentRequest request = new BankPaymentRequest();
        request.setPrice(price);
        BankPaymentResponse response = bankService.pay(request);

        //insert records
        Payment payment = new Payment();
        payment.setBankResponse(response.getResultCode());
        payment.setPrice(price);
        paymentRepository.save(payment);
        logger.info("Payment saved successfully!");
    }

````
[IyzicoPaymentService](src/main/java/com/iyzico/challenge/service/IyzicoPaymentService.java) contains the final code. All Tests that are written are passing.




