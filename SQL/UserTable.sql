
CREATE TABLE user (
                      userID INT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(50) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL, -- Remember to hash passwords!
                      firstName VARCHAR(50),
                      lastName VARCHAR(50),
                      email VARCHAR(100),
                      contactNo VARCHAR(20),
                      profilePicture VARCHAR(255)
);


CREATE TABLE role (
                      roleID INT PRIMARY KEY,
                      roleName VARCHAR(20) NOT NULL
);


CREATE TABLE user_roles (
                            userID INT,
                            roleID INT,
                            PRIMARY KEY (userID, roleID),
                            FOREIGN KEY (userID) REFERENCES user(userID) ON DELETE CASCADE,
                            FOREIGN KEY (roleID) REFERENCES role(roleID) ON DELETE CASCADE
);


INSERT INTO role (roleID, roleName) VALUES (1, 'ADMIN');
INSERT INTO role (roleID, roleName) VALUES (2, 'LECTURER');
INSERT INTO role (roleID, roleName) VALUES (3, 'UNDERGRADUATE');
INSERT INTO role (roleID, roleName) VALUES (4, 'TO');


DROP TABLE IF EXISTS lecturer;

CREATE TABLE lecturer (
                          userID INT PRIMARY KEY,
                          lecturerID VARCHAR(20) UNIQUE NOT NULL,
                          department VARCHAR(100),
                          qualifications VARCHAR(255),
                          specialization VARCHAR(100),

                          FOREIGN KEY (userID) REFERENCES user(userID) ON DELETE CASCADE
);




CREATE TABLE technical_officer (
                                   userID INT PRIMARY KEY,
                                   techOfficerID VARCHAR(20) UNIQUE NOT NULL,
                                   department VARCHAR(100),
                                   technicalSkills VARCHAR(255),
                                   assignedLab VARCHAR(100),

                                   FOREIGN KEY (userID) REFERENCES user(userID) ON DELETE CASCADE
);

CREATE TABLE undergraduate (
                               userID INT PRIMARY KEY,
                               studentID VARCHAR(50) UNIQUE NOT NULL,
                               degreeProgram VARCHAR(100),
                               level INT,
                               gpa DECIMAL(3,2),

                               FOREIGN KEY (userID) REFERENCES user(userID) ON DELETE CASCADE
);

CREATE TABLE course (
                        courseID INT AUTO_INCREMENT PRIMARY KEY,
                        courseCode VARCHAR(20) UNIQUE NOT NULL,
                        courseName VARCHAR(100) NOT NULL,
                        credits INT NOT NULL
);


CREATE TABLE IF NOT EXISTS medical (
                                       medicalID INT AUTO_INCREMENT PRIMARY KEY,
                                       undergraduateId VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    validFrom DATE NOT NULL,
    validTo DATE NOT NULL,
    status ENUM('Pending', 'Approved', 'Rejected') DEFAULT 'Pending',
    submittedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP

    );

INSERT INTO medical (undergraduateId, description, validFrom, validTo, status)
VALUES
    ('TG/2023/1753', 'Severe Viral Fever and Migraine', '2026-04-01', '2026-04-05', 'Pending'),
    ('TG/2023/1820', 'Sprained Ankle during Sports Meet', '2026-03-15', '2026-03-20', 'Approved'),
    ('TG/2023/1100', 'Food Poisoning', '2026-04-10', '2026-04-12', 'Rejected'),
    ('TG/2023/1550', 'Eye Infection', '2026-04-05', '2026-04-08', 'Pending');

CREATE TABLE attendance (
                            attendanceID INT AUTO_INCREMENT PRIMARY KEY,
                            undergraduateId VARCHAR(50) NOT NULL,
                            courseCode VARCHAR(20) NOT NULL,
                            sessionDate DATE NOT NULL,
                            status VARCHAR(20) NOT NULL,
                            markedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO attendance (undergraduateId, courseCode, sessionDate, status)
VALUES
    ('TG/2023/1753', 'ICT1234', '2026-04-01', 'Present'),
    ('TG/2023/1753', 'ICT1122', '2026-04-02', 'Absent'),
    ('TG/2023/1820', 'ICT1234', '2026-04-01', 'Present'),
    ('TG/2023/1100', 'ICT1234', '2026-04-01', 'Late');

CREATE TABLE IF NOT EXISTS marks (
                                     markID INT AUTO_INCREMENT PRIMARY KEY,
                                     undergraduateId VARCHAR(50) NOT NULL,
    courseCode VARCHAR(20) NOT NULL,
    caMarks DOUBLE DEFAULT 0,
    midExam DOUBLE DEFAULT 0,
    endExam DOUBLE DEFAULT 0,
    finalMarks DOUBLE DEFAULT 0,
    grade VARCHAR(5) DEFAULT 'F'
    );

INSERT INTO marks (undergraduateId, courseCode, caMarks, midExam, endExam, finalMarks, grade)
VALUES
    ('TG/2023/1753', 'ICT1234', 15.5, 20.0, 45.0, 80.5, 'A'),
    ('TG/2023/1753', 'ICT1122', 10.0, 15.0, 30.0, 55.0, 'C'),
    ('TG/2023/1820', 'ICT1234', 18.0, 22.0, 50.0, 90.0, 'A+'),
    ('TG/2023/1100', 'ICT1234', 12.0, 10.0, 20.0, 42.0, 'D');