import csv
import json
import random
from uuid import uuid4

POSSIBLE_COMPANIES = [
    "Allegiant Air",
    "American Airlines",
    "Delta Air Lines",
    "Frontier Airlines",
    "JetBlue",
    "Breeze Airways",
    "Spirit Airlines",
]

PLANE_TYPES = {"Airbus A330", "Boeing 737", "Boeing 777", "Airbus A320", "Douglas DC-3"}

airports = []


def parse_flights_csv_to_texas_airports():
    texas_airports = []

    with open("utils/us-airports.csv", newline="") as f:
        reader = csv.reader(f)
        for row in reader:
            try:
                airport_name = row[3]
                pos_x = float(row[4])
                pos_y = float(row[5])
                state = row[10]
                city = row[13]
                code = row[16]
            except:
                continue

            if state != "Texas" or code == "":
                continue

            texas_airports.append(
                {
                    "name": airport_name,
                    "x": pos_x,
                    "y": pos_y,
                    "state": state,
                    "city": city,
                    "code": code,
                }
            )

    return texas_airports


def dist_to_hr(dist):
    return 1 / (dist * 0.24)


def dist_between(x1, y1, x2, y2):
    return ((x1 - x2) ** 2 + (y1 - y2) ** 2) ** 0.5


def create_flight_entry():
    created = {}
    created["id"] = str(uuid4())
    created["company"] = random.choice(POSSIBLE_COMPANIES)

    a_from, a_to = random.sample(airports, 2)

    created["rating"] = round(random.uniform(0, 5), 1)

    return created


def create_json():
    created = []

    created.append(create_flight_entry())

    return json.dumps(created)


def main():
    print(dist_to_hr(dist_between(1, 101.782, 0, 98.613)))
    print(create_json())


if __name__ == "__main__":
    main()
