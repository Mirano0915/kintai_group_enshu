CREATE TABLE hourly_wages (
  name_id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(30) NOT NULL,
  hourly_wage INT NOT NULL DEFAULT 1000,
  PRIMARY KEY (name_id)
);

CREATE TABLE attendances (
  attendance_id BIGINT NOT NULL AUTO_INCREMENT,
  name_id BIGINT NOT NULL,
  checkin_time TIME,
  checkout_time TIME,
  date DATE,
  PRIMARY KEY(attendance_id),
  UNIQUE (name_id, date)
);

CREATE TABLE stamps (
  stamp_id BIGINT NOT NULL AUTO_INCREMENT,
  attendance_id BIGINT NOT NULL,
  name_id BIGINT NOT NULL,
  pre_checkin_time TIME DEFAULT NULL,
  pre_checkout_time TIME DEFAULT NULL,
  comment VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY (stamp_id),
  FOREIGN KEY (name_id) REFERENCES hourly_wages(name_id)
);

