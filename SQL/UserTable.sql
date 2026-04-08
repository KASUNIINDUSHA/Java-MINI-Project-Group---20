
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