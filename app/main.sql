CREATE TABLE point (
    point_id INT NOT NULL AUTO_INCREMENT,
    latitude FLOAT(11,8),
    longitude FLOAT(11,8),
    device_token VARCHAR(200),
    PRIMARY KEY (`point_id`),
    INDEX `point_latitude_idx` (`latitude` DESC),
    INDEX `point_longitude_idx` (`longitude` DESC)
)