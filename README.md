# 📚 BaseX Library Management

Welcome to **BaseX Library Management**! 🎉  
A JavaFX desktop application that uses **BaseX** (an XML-native database) to manage a library's catalog, patrons, and lending operations.

---

## 🎯 Objectives
- Store library data in XML collections via BaseX 📁  
- Perform CRUD operations on books, users, and loans 🛠️  
- Query and filter data using XQuery 🌐  
- Build a responsive UI with JavaFX and FXML 🎨

---

## ✨ Features
- 📖 **Book Catalog**: add, edit, delete, and list books with metadata  
- 👤 **User Management**: register, update, and remove patrons  
- 🔄 **Loan Processing**: check out and return books, track due dates  
- 🔍 **Advanced Search**: XQuery-based filters by author, title, or year  
- 📂 **XML Storage**: leveraging BaseX for performant XML data management  
- 🚀 **Modern UI**: JavaFX controls, layouts, and FXML views

---

## 🚀 Technologies & Tools
- **Java 21**  
- **JavaFX 22** (controls, FXML)  
- **BaseX 10.0** (Java API)  
- **Maven**  
- **JUnit 5** for testing  

---

## 🔧 Prerequisites
1. JDK 21 or higher installed  
2. BaseX Server or embedded BaseX library  
3. Maven 3.x  

---

## 🛠️ Installation & Setup

### 1. Clone the repository
```bash
git clone https://github.com/AndresLapSol/GestionBiblioteca_BaseX.git
cd GestionBiblioteca_BaseX
```

### 2. Configure BaseX (if using external server)
- Ensure BaseX Server is running on `localhost:1984` (default)  
- Update connection settings in `src/main/java/org/example/gestionbiblioteca_basex/BaseXUtil.java`:
```java
String host = "localhost";
int port = 1984;
String user = "admin";
String password = "admin";
```

### 3. Build the project
```bash
mvn clean install
```

### 4. Run the application
```bash
mvn javafx:run
```
- The main window will launch. Use the menu to navigate between Books, Users, and Loans.  

---

## 📂 Project Structure
```
GestionBiblioteca_BaseX/
├── src/
│   ├── main/
│   │   ├── java/org/example/gestionbiblioteca_basex/
│   │   │   ├── model/           # Data models (Book, User, Loan)
│   │   │   ├── util/            # BaseX connection and helpers
│   │   │   ├── controller/      # JavaFX controllers
│   │   │   └── MainApplication.java # JavaFX entry point
│   │   └── resources/
│   │       ├── fxml/            # FXML view files
│   │       └── css/             # Styling (optional)
│   └── test/                    # JUnit test cases
├── pom.xml
├── mvnw, mvnw.cmd               # Maven wrappers
└── LICENSE
```

---

## 🤝 Contributing
Contributions are welcome!  
1. Fork the repo  
2. Create a branch: `git checkout -b feature/YourFeature`  
3. Commit your changes: `git commit -m "Add new functionality"`  
4. Push and open a Pull Request 🚀

---

## ⚖️ License
This project is licensed under **MIT License**. See the `LICENSE` file for details.

---

## 📫 Contact
Developed by **AndresLapSol**  
GitHub: [AndresLapSol](https://github.com/AndresLapSol)  
Email: `andreslapsol@gmail.com`
