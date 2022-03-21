import code
import csv
import json
import random
import datetime
from uuid import uuid4

POSSIBLE_HOTEL_COMPANIES = [
    "Wyndham",
    "Marriott",
    "Choice",
    "Hilton",
    "InterContinental",
    "Accor",
    "Red Lion",
]

POSSIBLE_PLANE_COMPANIES = [
    "Allegiant Air",
    "American Airlines",
    "Delta Air Lines",
    "Frontier Airlines",
    "JetBlue",
    "Breeze Airways",
    "Spirit Airlines",
]

POSSIBLE_PLANE_TYPES = [
    "Airbus A330",
    "Boeing 737",
    "Douglas DC-3",
]

POSSIBLE_DATES = [
    "1/1/22",
    "1/2/22",
    "1/3/22",
    "1/4/22",
    "1/5/22",
    "1/6/22",
    "1/7/22",
    "1/8/22",
]

airports = []


def get_texas_flights():
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

            airports.append(
                {
                    "name": airport_name,
                    "x": pos_x,
                    "y": pos_y,
                    "state": state,
                    "city": city,
                    "code": code,
                }
            )


def dist_to_min(dist):
    return int((1 / (dist * 0.24)) * 60)


def dist_between(x1, y1, x2, y2):
    return ((x1 - x2) ** 2 + (y1 - y2) ** 2) ** 0.5


def create_hotel_entry():
    h = {}
    h["id"] = str(uuid4())
    h["company"] = random.choice(POSSIBLE_HOTEL_COMPANIES)

    airport = random.choice(airports)
    h["location"] = airport["city"]

    day = int(random.uniform(1, 10))
    hour = int(random.uniform(1, 23))
    date = datetime.datetime(2022, 1, day, hour)

    h["check_out_time"] = str(random.randrange(10, 13)) + " :00 UTS"

    h["date"] = date.strftime("%m/%d/%y")
    h["rating"] = round(random.uniform(0, 5), 1)

    return h


def create_flight_entry():
    f = {}
    f["id"] = str(uuid4())
    f["company"] = random.choice(POSSIBLE_PLANE_COMPANIES)

    a_from, a_to = random.sample(airports, 2)

    f["airport_code_from"] = a_from["code"]
    f["airport_code_to"] = a_to["code"]

    f["city_from"] = a_from["city"]
    f["city_to"] = a_to["city"]

    day = int(random.uniform(1, 10))
    hour = int(random.uniform(1, 23))
    date = datetime.datetime(2022, 1, day, hour)

    f["date"] = date.strftime("%m/%d/%y")
    f["time_depart"] = date.strftime("%H:%M UTC")

    dist = dist_between(a_from["x"], a_from["y"], a_to["x"], a_to["y"])
    date = date + datetime.timedelta(minutes=dist_to_min(dist))

    f["time_arrive"] = date.strftime("%H:%M UTC")

    f["rating"] = round(random.uniform(0, 5), 1)

    return f


def create_hotels_json():
    created = []

    amount = len(airports) * 2

    for i in range(amount):
        created.append(create_hotel_entry())

    with open("database/hotels.json", "w") as f:
        f.write(json.dumps(created))

    # TODO: create seats


def create_flights_json():
    created = []

    amount = len(POSSIBLE_DATES) * len(airports) * 4

    for i in range(amount):
        created.append(create_flight_entry())

    with open("database/flights.json", "w") as f:
        f.write(json.dumps(created))

    # TODO: create seats


def main():
    get_texas_flights()
    create_flights_json()
    create_hotels_json()


if __name__ == "__main__":
    main()
