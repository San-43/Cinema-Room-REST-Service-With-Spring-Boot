package cinema.services;

import cinema.dto.*;
import cinema.exception.AlreadyPurchasedException;
import cinema.exception.OutOfBoundsException;
import cinema.exception.WrongPasswordException;
import cinema.exception.WrongTokenException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class CinemaService {

    private static final int ROWS = 9;
    private static final int COLUMNS = 9;
    private final ConcurrentMap<String, SeatDto> available = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, SeatDto> purchasedByToken = new ConcurrentHashMap<>();

    public CinemaResponse getSeats() {
        List<SeatDto> seats = new ArrayList<>(ROWS * COLUMNS);
        int price = 0;
        for (int r = 1; r <= ROWS; r++) {
            for (int c = 1; c <= COLUMNS; c++) {
                if(r <= 4) price = 10;
                else price = 8;
                seats.add(new SeatDto(r, c, price));
                available.put(r + "-" + c, new SeatDto(r, c, price));
            }
        }
        return new CinemaResponse(ROWS, COLUMNS, seats);
    }

    public PurchaseResponse purchase(int row, int column) {
        if (!inBounds(row, column)) {
            throw new OutOfBoundsException("The number of a row or a column is out of bounds!");
        }

        SeatDto seat = available.remove(row + "-" + column);
        if (seat == null) {
            throw new AlreadyPurchasedException("The ticket has been already purchased!");
        }

        String token = UUID.randomUUID().toString();
        purchasedByToken.put(token, seat);

        return new PurchaseResponse(token, seat);
    }

    public ReturnResponse returnPurchase(ReturnRequest returnRequest) {
        String token = returnRequest.token();

        SeatDto ticket = purchasedByToken.remove(token);
        if (ticket == null) {
            throw new WrongTokenException("Wrong token!");
        }

        available.put(ticket.row() + "-" + ticket.column(), ticket);

        return new ReturnResponse(ticket);
    }

    public StatsResponse getStats(String password) {
        if (!"super_secret".equals(password)) {
            throw new WrongPasswordException("The password is wrong!");
        }

        int income = purchasedByToken.values().stream().mapToInt(SeatDto::price).sum();
        return new StatsResponse(income, available.size(), purchasedByToken.size());
    }

    private static boolean inBounds(int r, int c) {
        return r >= 1 && r <= ROWS && c >= 1 && c <= COLUMNS;
    }
}
