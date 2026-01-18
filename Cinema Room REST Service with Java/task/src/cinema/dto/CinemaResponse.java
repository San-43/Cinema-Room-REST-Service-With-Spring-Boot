package cinema.dto;

import java.util.List;

public record CinemaResponse(int rows, int columns, List<SeatDto> seats) {}
