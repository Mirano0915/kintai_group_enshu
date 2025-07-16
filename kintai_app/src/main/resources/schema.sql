CREATE TABLE attendances(
 name_id INT NOT NULL AUTO_INCREMENT,
 name VARCHAR (30) NOT NULL,
 checkin_time TIME,
 checkout_time TIME,
 date DATE,
 PRIMARY KEY(name_id)
);


CREATE TABLE stamps (
    stamp_id INT NOT NULL AUTO_INCREMENT,
    name_id INT NOT NULL,
    pre_checkin_time TIME DEFAULT NULL,
    pre_checkout_time TIME DEFAULT NULL,
    comment VARCHAR(100) DEFAULT NULL,
    PRIMARY KEY (stamp_id),
    FOREIGN KEY (name_id) REFERENCES attendances(name_id)
);


CREATE TABLE hourly_wages(
 name_id INT NOT NULL,
 hourly_wage INT NOT NULL DEFAULT 1000,
 PRIMARY KEY(name_id)
);