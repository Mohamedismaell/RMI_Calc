# RMI_Calc

A simple **Remote Method Invocation (RMI) Calculator** built with Java.  
This project demonstrates how to use Java RMI to perform distributed computations, allowing a client to invoke methods on a remote server object as if it were local.

---

## ✨ Features
- Basic arithmetic operations: `add`, `subtract`, `multiply`, `divide`
- Client-server architecture using Java RMI
- Clear separation of interface, server, and client code
- Easy to extend with new operations
- Lightweight and beginner-friendly example of distributed systems

---

## 📂 Project Structure
RMI_Calc/ │── src/ │ ├── Calculator.java # Remote interface 
│ ├── CalculatorImpl.java # Implementation of remote methods 
│ ├── CalculatorServer.java # Server setup and binding
│ └── CalculatorClient.java # Client that calls remote methods
│── README.md


---

## ⚙️ Requirements
- Java JDK 8 or higher
- Basic knowledge of RMI concepts
- Command line or IDE (e.g., IntelliJ, Eclipse, VS Code)

---

## 🚀 How to Run
1. **Compile all files**  
   ```bash
   javac *.java
rmiregistry
java CalculatorServer
java CalculatorClient

Connected to RMI Calculator
Addition: 5 + 3 = 8
Subtraction: 10 - 4 = 6
Multiplication: 7 * 2 = 14
Division: 20 / 5 = 4

🛠️ Future Improvements

Add advanced operations (square root, power, modulus)
Build a simple GUI client with JavaFX or Swing
Add authentication for secure remote calls

## 👥 Credits
This project was developed by:
- **Mohamed Ismail**
- **Saif Yehia Mosaad** 


   
🤝 Contributing
Contributions are welcome! Feel free to fork this repo, open issues, or submit pull requests to improve functionality or documentation.



Co-authored-by: Mohamed Ismail
Co-authored-by: Saif Yehia Mosaad 
