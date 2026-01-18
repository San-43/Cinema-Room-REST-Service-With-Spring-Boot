package cinema.web;


import cinema.dto.*;
import cinema.services.CinemaService;
import org.springframework.web.bind.annotation.*;

@RestController
public class CinemaController {
    private final CinemaService cinemaService;

    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping("/seats")
    public CinemaResponse getSeats() {
        return cinemaService.getSeats();
    }

    @GetMapping("/stats")
    public StatsResponse getStats(@RequestParam(required = false) String password) {
        return cinemaService.getStats(password);
    }

    @PostMapping("/purchase")
    public PurchaseResponse purchase(@RequestBody PurchaseRequest purchaseRequest) {
        return cinemaService.purchase(purchaseRequest.row(), purchaseRequest.column());
    }

    @PostMapping("/return")
    public ReturnResponse returnSeat(@RequestBody ReturnRequest returnRequest) {
        return cinemaService.returnPurchase(returnRequest);
    }
}
