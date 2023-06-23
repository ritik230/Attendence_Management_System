# Attendence_Management_System

This is a Java-based attendance application that utilizes face recognition to track attendance. The application uses JavaFX for the graphical user interface, MySQL for the database management system, and OpenCV for face recognition.

## Features

- User-friendly graphical interface for attendance management
- Face recognition to identify and mark attendance automatically
- Secure storage of attendance records in a MySQL database

## Requirements

- Java Development Kit (JDK) 8 or above
- JavaFX library
- MySQL database
- OpenCV library

## Setup and Installation

1. Clone the repository to your local machine:
   ```
   git clone <repository-url>
   ```

2. Install JDK if it is not already installed on your system. Ensure that the Java executable is added to the system's PATH environment variable.

3. Install JavaFX and set it up in your IDE or build system. Refer to the documentation specific to your IDE or build system for instructions.

4. Install MySQL and set up the database. Create a database for the application and update the database connection details in the configuration file (`config.properties` or similar).

5. Install OpenCV library and configure it for your project. Refer to the OpenCV documentation for installation instructions specific to your operating system.

6. Build and run the application using your preferred IDE or build system. Make sure to set the main class as the entry point of the application.

## Usage

1. Launch the application.
   
3. login in Management.

4. Add student details and their corresponding images to the database.
   
6. login in faculty

7. Start the attendance session.

8. The application will automatically detect and recognize faces in the camera feed, matching them against the stored student images.

9. Mark attendance for recognized students.

10. Stop the attendance session when finished.

    Note- both Management and faculty login id is added in database manually

## Contribution

Contributions to the project are welcome! If you find any issues or have suggestions for improvements, please create an issue or submit a pull request.



## Acknowledgments

- [OpenCV](https://opencv.org/) - Open Source Computer Vision Library
- [JavaFX](https://openjfx.io/) - OpenJFX: JavaFX Open Source Project
- [MySQL](https://www.mysql.com/) - MySQL Database Management System

---

