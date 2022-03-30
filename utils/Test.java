public class SearchPreferences {
	@Override
	public String toString() {
		 return "{" + "\"fpref\": \"" + fpref + "\", "
		 + "\"hPref\": \"" + hPref + "\"}";
	}
}

public class User {
	@Override
	public String toString() {
		 return "{" + "\"id\": \"" + id + "\", "
		 + "\"username\": \"" + username + "\", "
		 + "\"password\": \"" + password + "\", "
		 + "\"email\": \"" + email + "\", "
		 + "\"preferences\": \"" + preferences + "\", "
		 + "\"specialReq\": \"" + specialReq + "\", "
		 + "\"travelers\": \"" + travelers + "\", "
		 + "\"bookingHistory\": \"" + bookingHistory + "\", "
		 + "\"person\": \"" + person + "\"}";
	}
}

public class Bookable {
	@Override
	public String toString() {
		 return "{" + "\"id\": \"" + id + "\", "
		 + "\"row\": \"" + row + "\", "
		 + "\"col\": \"" + col + "\", "
		 + "\"price\": \"" + price + "\", "
		 + "\"travelObject\": \"" + travelObject + "\"}";
	}
}

public class TravelObject {
	@Override
	public String toString() {
		 return "{" + "\"id\": \"" + id + "\", "
		 + "\"bookables\": \"" + bookables + "\", "
		 + "\"company\": \"" + company + "\", "
		 + "\"rating\": \"" + rating + "\", "
		 + "\"features\": \"" + features + "\", "
		 + "\"filters\": \"" + filters + "\"}";
	}
}

public class FlightTrip {
	@Override
	public String toString() {
		 return "{" + "\"flights\": \"" + flights + "\", "
		 + "\"id\": \"" + id + "\", "
		 + "\"bookables\": \"" + bookables + "\", "
		 + "\"company\": \"" + company + "\", "
		 + "\"rating\": \"" + rating + "\", "
		 + "\"features\": \"" + features + "\", "
		 + "\"filters\": \"" + filters + "\"}";
	}
}

public class Flight {
	@Override
	public String toString() {
		 return "{" + "\"airportFrom\": \"" + airportFrom + "\", "
		 + "\"airportTo\": \"" + airportTo + "\", "
		 + "\"cityFrom\": \"" + cityFrom + "\", "
		 + "\"cityTo\": \"" + cityTo + "\", "
		 + "\"departureTime\": \"" + departureTime + "\", "
		 + "\"arrivalTime\": \"" + arrivalTime + "\", "
		 + "\"startX\": \"" + startX + "\", "
		 + "\"startY\": \"" + startY + "\", "
		 + "\"stopX\": \"" + stopX + "\", "
		 + "\"stopY\": \"" + stopY + "\", "
		 + "\"id\": \"" + id + "\", "
		 + "\"bookables\": \"" + bookables + "\", "
		 + "\"company\": \"" + company + "\", "
		 + "\"rating\": \"" + rating + "\", "
		 + "\"features\": \"" + features + "\", "
		 + "\"filters\": \"" + filters + "\"}";
	}
}

public class Seat {
	@Override
	public String toString() {
		 return "{" + "\"whichClass\": \"" + whichClass + "\", "
		 + "\"owner\": \"" + owner + "\", "
		 + "\"isBooked\": \"" + isBooked + "\", "
		 + "\"id\": \"" + id + "\", "
		 + "\"row\": \"" + row + "\", "
		 + "\"col\": \"" + col + "\", "
		 + "\"price\": \"" + price + "\", "
		 + "\"travelObject\": \"" + travelObject + "\"}";
	}
}

public class Booking {
	@Override
	public String toString() {
		 return "{" + "\"id\": \"" + id + "\", "
		 + "\"booked\": \"" + booked + "\", "
		 + "\"user\": \"" + user + "\", "
		 + "\"from\": \"" + from + "\", "
		 + "\"to\": \"" + to + "\"}";
	}
}

public class Hotel {
	@Override
	public String toString() {
		 return "{" + "\"location\": \"" + location + "\", "
		 + "\"id\": \"" + id + "\", "
		 + "\"bookables\": \"" + bookables + "\", "
		 + "\"company\": \"" + company + "\", "
		 + "\"rating\": \"" + rating + "\", "
		 + "\"features\": \"" + features + "\", "
		 + "\"filters\": \"" + filters + "\"}";
	}
}

public class Room {
	@Override
	public String toString() {
		 return "{" + "\"info\": \"" + info + "\", "
		 + "\"bookedDays\": \"" + bookedDays + "\", "
		 + "\"sleepingCapacity\": \"" + sleepingCapacity + "\", "
		 + "\"hotel\": \"" + hotel + "\", "
		 + "\"id\": \"" + id + "\", "
		 + "\"row\": \"" + row + "\", "
		 + "\"col\": \"" + col + "\", "
		 + "\"price\": \"" + price + "\", "
		 + "\"travelObject\": \"" + travelObject + "\"}";
	}
}

public class Passport {
	@Override
	public String toString() {
		 return "{" + "\"id\": \"" + id + "\", "
		 + "\"person\": \"" + person + "\", "
		 + "\"dateOfBirth\": \"" + dateOfBirth + "\", "
		 + "\"expirationDate\": \"" + expirationDate + "\", "
		 + "\"number\": \"" + number + "\", "
		 + "\"gender\": \"" + gender + "\"}";
	}
}

public class Person {
	@Override
	public String toString() {
		 return "{" + "\"firstName\": \"" + firstName + "\", "
		 + "\"lastName\": \"" + lastName + "\"}";
	}
}

