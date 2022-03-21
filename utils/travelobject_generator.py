import os
import csv
import json
import random
import datetime
from uuid import uuid4

# TODO Add flight features

POSSIBLE_HOTEL_COMPANIES = [
    "Wyndham",
    "Marriott",
    "Choice",
    "Hilton",
    "InterContinental",
    "Accor",
    "Red Lion",
]

POSSIBLE_ROOM_BED_INFO = {
    "2 Double Beds": 4,
    "2 Queen Beds": 4,
    "1 King Bed": 2,
    "1 Queen Bed": 1,
}

POSSIBLE_HOTEL_FEATURES = [
    "Restaurant",
    "Bar",
    "Free Wifi",
    "Lounge Area",
    "Pet friendly",
    "Gym",
    "Pool",
    "Parking",
]

POSSIBLE_PLANE_CLASSES = ["First", "Business", "Economy"]
POSSIBLE_FLIGHT_FEATURES = [
    "Free Wifi",
    "Drinks",
    "Snacks",
    "Meals",
    "Movies",
    "Outlets",
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
    "1/9/22",
    "1/10/22",
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

    h["check_out_time"] = str(random.randrange(10, 13)) + ":00 UTS"
    h["rating"] = round(random.uniform(0, 5), 1)

    h["features"] = random.sample(
        POSSIBLE_HOTEL_FEATURES, random.randint(3, len(POSSIBLE_HOTEL_FEATURES))
    )

    h["filters"] = ["HOTEL"]

    h["rooms"] = []
    room_num = 0
    for i in range(random.randrange(3, 20)):
        room_num += 1
        room_id = str(uuid4())
        write_room(create_room_dict(room_id, room_num))
        h["rooms"].append(room_id)

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
    f["plane_type"] = random.choice(POSSIBLE_PLANE_TYPES)

    f["features"] = random.sample(
        POSSIBLE_FLIGHT_FEATURES, random.randint(3, len(POSSIBLE_FLIGHT_FEATURES))
    )

    f["filters"] = ["FLIGHT"]

    f["seats"] = []
    for i in range(1, random.randint(3, 20)):
        uuid = str(uuid4())
        price = random.randint(100, 1000)
        seat = create_seat_dict(
            uuid, 1, i, price, random.choice(POSSIBLE_PLANE_CLASSES)
        )
        write_seat(seat)
        f["seats"].append(uuid)

    return f


def create_hotels_json():
    amount = len(airports) * 2

    for i in range(amount):
        entry = create_hotel_entry()

        with open("database/hotels/" + str(entry["id"]) + ".json", "w") as f:
            f.write(json.dumps([entry]))


def write_seat(seat_dict):
    with open("database/seats/" + str(seat_dict["id"]) + ".json", "w") as f:
        f.write(json.dumps([seat_dict]))


def write_room(room_dict):
    with open("database/rooms/" + str(room_dict["id"]) + ".json", "w") as f:
        f.write(json.dumps([room_dict]))


def create_room_dict(uuid, number):
    r = {}

    r["id"] = str(uuid)
    r["bedInfo"] = random.choice(list(POSSIBLE_ROOM_BED_INFO.keys()))
    r["sleepingCapacity"] = POSSIBLE_ROOM_BED_INFO[r["bedInfo"]]
    r["price"] = random.randrange(50, 300)
    r["roomNum"] = number

    r["bookings"] = []
    for day in POSSIBLE_DATES:
        if random.randint(1, 100) < 20:
            r["bookings"].append(day)

    return r


def create_seat_dict(uuid, row, col, price, class_type):
    s = {}

    s["id"] = str(uuid)
    s["price"] = price
    s["row"] = row
    s["column"] = col
    s["isBooked"] = random.randint(1, 100) < 20
    s["class"] = class_type

    return s


def create_flights_json():
    amount = len(POSSIBLE_DATES) * len(airports) * 4

    for i in range(amount):
        entry = create_flight_entry()

        with open("database/flights/" + str(entry["id"]) + ".json", "w") as f:
            f.write(json.dumps([entry]))


def delete_json_in_dir(path):
    for file in os.scandir(path):
        if str(file.path).endswith(".json"):
            os.remove(file.path)


def main():
    delete_json_in_dir("database/flights")
    delete_json_in_dir("database/seats")
    delete_json_in_dir("database/hotels")
    delete_json_in_dir("database/rooms")
    # exit()
    get_texas_flights()
    create_flights_json()
    create_hotels_json()


if __name__ == "__main__":
    main()
