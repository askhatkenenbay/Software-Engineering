delete from SEAT_RESERVATION;
delete from LEG_INSTANCE;
delete from FLIGHT_LEG;
delete from FARE;
delete from FLIGHT;
delete from CAN_LAND;
delete from AIRPLANE;
delete from AIRPLANE_TYPE;
delete from AIRPORT;

insert into AIRPORT (Code,Name,City,Country) values ('BWI', 'Baltimore-Washington International', 'Baltimore', 'US');
insert into AIRPORT (Code,Name,City,Country) values ('IAD', 'Dulles International', 'Washington DC', 'US');
insert into AIRPORT (Code,Name,City,Country) values ('TSE', 'Astana International', 'Astana', 'KZ');
insert into AIRPORT (Code,Name,City,Country) values ('FRA', 'Frankfurt International', 'Frankfurt', 'DE');
insert into AIRPORT (Code,Name,City,Country) values ('ORF', 'Norfolk International', 'Norfolk', 'US');

insert into AIRPLANE_TYPE (Name, MaxSeats, Company) values ('Boeing747', 350, 'Boeing');
insert into AIRPLANE_TYPE (Name, MaxSeats, Company) values ('Boeing727', 200, 'Boeing');
insert into AIRPLANE_TYPE (Name, MaxSeats, Company) values ('MD20', 200, 'McDonnell Douglas');

insert into CAN_LAND (AirplaneTypeName, AirportCode) values ('MD20', 'BWI');
insert into CAN_LAND (AirplaneTypeName, AirportCode) values ('MD20', 'IAD');
insert into CAN_LAND (AirplaneTypeName, AirportCode) values ('MD20', 'FRA');
insert into CAN_LAND (AirplaneTypeName, AirportCode) values ('MD20', 'TSE');
insert into CAN_LAND (AirplaneTypeName, AirportCode) values ('Boeing727', 'BWI');
insert into CAN_LAND (AirplaneTypeName, AirportCode) values ('Boeing727', 'IAD');
insert into CAN_LAND (AirplaneTypeName, AirportCode) values ('Boeing727', 'FRA');
insert into CAN_LAND (AirplaneTypeName, AirportCode) values ('Boeing727', 'TSE');
insert into CAN_LAND (AirplaneTypeName, AirportCode) values ('Boeing747', 'BWI');
insert into CAN_LAND (AirplaneTypeName, AirportCode) values ('Boeing747', 'IAD');
insert into CAN_LAND (AirplaneTypeName, AirportCode) values ('Boeing747', 'FRA');

insert into AIRPLANE (ID, TotalSeats, AirplaneTypeName) values (234567, 300, 'Boeing747');
insert into AIRPLANE (ID, TotalSeats, AirplaneTypeName) values (123456, 320, 'Boeing747');
insert into AIRPLANE (ID, TotalSeats, AirplaneTypeName) values (345678, 200, 'Boeing727');
insert into AIRPLANE (ID, TotalSeats, AirplaneTypeName) values (456789, 190, 'Boeing727');
insert into AIRPLANE (ID, TotalSeats, AirplaneTypeName) values (567890, 175, 'MD20');

insert into FLIGHT (FlightNum, Airline, Weekdays) values ('AA123', 'Air Astana', 'MoWe');
insert into FLIGHT (FlightNum, Airline, Weekdays) values ('LH001', 'Lufthansa', 'TuSa');
insert into FLIGHT (FlightNum, Airline, Weekdays) values ('LH007', 'Lufthansa', 'MoTuWeThFrSaSu');

insert into FARE (FlightNum, FareCode, Amount, Restrictions) values ('LH007', 'B', 3200, 'Refunds Allowed');
insert into FARE (FlightNum, FareCode, Amount, Restrictions) values ('LH007', 'F', 7900, 'Refunds Allowed');
insert into FARE (FlightNum, FareCode, Amount, Restrictions) values ('LH007', 'E', 1800, 'No Refunds');
insert into FARE (FlightNum, FareCode, Amount, Restrictions) values ('AA123', 'B', 3200, 'Refunds Allowed');
insert into FARE (FlightNum, FareCode, Amount, Restrictions) values ('AA123', 'F', 7800, 'Refunds Allowed');
insert into FARE (FlightNum, FareCode, Amount, Restrictions) values ('AA123', 'E', 1800, 'No Refunds');

insert into FLIGHT_LEG (FlightNum,LegNum,DeptAirportCode,SchedDeptTime,ArrAirportCode,SchedArrTime) values
                       ('LH007', 1,'TSE', '02:50', 'FRA', '7:50');
insert into FLIGHT_LEG (FlightNum,LegNum,DeptAirportCode,SchedDeptTime,ArrAirportCode,SchedArrTime) values
                       ('LH007', 2,'FRA', '13:20', 'IAD', '19:00');
insert into FLIGHT_LEG (FlightNum,LegNum,DeptAirportCode,SchedDeptTime,ArrAirportCode,SchedArrTime) values
                       ('LH001', 1,'IAD', '07:00', 'BWI', '14:30');
                       
insert into LEG_INSTANCE (FlightNum,LegNum,Date,DeptAirportCode,ActualDeptTime,ArrAirportCode,ActualArrTime,AirplaneID,AvailableSeats)
			values ('LH007', 1, '2016-10-20','TSE', '02:50', 'FRA', '7:50', 345678, 17);
insert into LEG_INSTANCE (FlightNum,LegNum,Date,DeptAirportCode,ActualDeptTime,ArrAirportCode,ActualArrTime,AirplaneID,AvailableSeats)
			values ('LH007', 2, '2016-10-20','FRA', '13:20', 'IAD', '19:00', 345678, 9);
insert into LEG_INSTANCE (FlightNum,LegNum,Date,DeptAirportCode,ActualDeptTime,ArrAirportCode,ActualArrTime,AirplaneID,AvailableSeats)
			values ('LH007', 1, '2016-10-12','TSE', '02:55', 'FRA', '7:50', 345678, 17);
insert into LEG_INSTANCE (FlightNum,LegNum,Date,DeptAirportCode,ActualDeptTime,ArrAirportCode,ActualArrTime,AirplaneID,AvailableSeats)
			values ('LH007', 2, '2016-10-12','FRA', '13:39', 'IAD', '20:02', 345678, 9);
            
insert into SEAT_RESERVATION (FlightNum,LegNum,Date,SeatNum,CustomerName,CustomerPhone) values
			('LH007', 1, '2016-10-12', '36A', 'Mona Rizvi', '777-777-7777');
insert into SEAT_RESERVATION (FlightNum,LegNum,Date,SeatNum,CustomerName,CustomerPhone) values
			('LH007', 1, '2016-10-12', '36B', 'Mariya Rizvi', '777-777-7777');
insert into SEAT_RESERVATION (FlightNum,LegNum,Date,SeatNum,CustomerName,CustomerPhone) values
			('LH007', 1, '2016-10-12', '36C', 'Faraz Rizvi', '777-777-7777');
